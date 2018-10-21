// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.edit.CoreEditFactory;
import com.echothree.control.user.core.remote.edit.EntityTimeAttributeEdit;
import com.echothree.control.user.core.remote.form.EditEntityTimeAttributeForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.EditEntityTimeAttributeResult;
import com.echothree.control.user.core.remote.spec.EntityTimeAttributeSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.EntityTimeAttributeValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityTimeAttributeCommand
        extends BaseEditCommand<EntityTimeAttributeSpec, EntityTimeAttributeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TimeAttribute", FieldType.DATE_TIME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityTimeAttributeCommand */
    public EditEntityTimeAttributeCommand(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        EditEntityTimeAttributeResult result = CoreResultFactory.getEditEntityTimeAttributeResult();
        String entityRef = spec.getEntityRef();
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance != null) {
            String entityAttributeName = spec.getEntityAttributeName();
            EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);

            if(entityAttribute != null) {
                EntityTimeAttribute entityTimeAttribute = null;
                BasePK basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);

                    if(entityTimeAttribute != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setEntityTimeAttribute(coreControl.getEntityTimeAttributeTransfer(getUserVisit(),
                                    entityTimeAttribute, entityInstance));

                            if(lockEntity(basePK)) {
                                EntityTimeAttributeEdit edit = CoreEditFactory.getEntityTimeAttributeEdit();

                                result.setEdit(edit);
                                edit.setTimeAttribute(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), entityTimeAttribute.getTimeAttribute()));
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                        } else { // EditMode.ABANDON
                            unlockEntity(basePK);
                            basePK = null;
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTimeAttribute.name(), entityRef, entityAttributeName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    entityTimeAttribute = coreControl.getEntityTimeAttributeForUpdate(entityAttribute, entityInstance);

                    if(entityTimeAttribute != null) {
                        if(lockEntityForUpdate(basePK)) {
                            try {
                                EntityTimeAttributeValue entityTimeAttributeValue = coreControl.getEntityTimeAttributeValueForUpdate(entityTimeAttribute);

                                entityTimeAttributeValue.setTimeAttribute(Long.valueOf(edit.getTimeAttribute()));

                                coreControl.updateEntityTimeAttributeFromValue(entityTimeAttributeValue, getPartyPK());
                            } finally {
                                unlockEntity(basePK);
                                basePK = null;
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTimeAttribute.name(), entityRef, entityAttributeName);
                    }
                }

                if(basePK != null) {
                    result.setEntityLock(getEntityLockTransfer(basePK));
                }

                if(entityTimeAttribute != null) {
                    result.setEntityTimeAttribute(coreControl.getEntityTimeAttributeTransfer(getUserVisit(), entityTimeAttribute, entityInstance));
                }
            } else {
                EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttributeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }

        return result;
    }
    
}
