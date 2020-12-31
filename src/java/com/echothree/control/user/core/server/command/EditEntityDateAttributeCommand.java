// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityDateAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityDateAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityDateAttributeResult;
import com.echothree.control.user.core.common.spec.EntityDateAttributeSpec;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.EntityDateAttributeValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityDateAttributeCommand
        extends BaseEditCommand<EntityDateAttributeSpec, EntityDateAttributeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DateAttribute", FieldType.DATE, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityDateAttributeCommand */
    public EditEntityDateAttributeCommand(UserVisitPK userVisitPK, EditEntityDateAttributeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        EditEntityDateAttributeResult result = CoreResultFactory.getEditEntityDateAttributeResult();
        String entityRef = spec.getEntityRef();
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance != null) {
            String entityAttributeName = spec.getEntityAttributeName();
            EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);

            if(entityAttribute != null) {
                EntityDateAttribute entityDateAttribute = null;
                BasePK basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);

                    if(entityDateAttribute != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setEntityDateAttribute(coreControl.getEntityDateAttributeTransfer(getUserVisit(),
                                    entityDateAttribute, entityInstance));

                            if(lockEntity(basePK)) {
                                EntityDateAttributeEdit edit = CoreEditFactory.getEntityDateAttributeEdit();

                                result.setEdit(edit);
                                edit.setDateAttribute(DateUtils.getInstance().formatDate(getUserVisit(), entityDateAttribute.getDateAttribute()));
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                        } else { // EditMode.ABANDON
                            unlockEntity(basePK);
                            basePK = null;
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityDateAttribute.name(), entityRef, entityAttributeName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    entityDateAttribute = coreControl.getEntityDateAttributeForUpdate(entityAttribute, entityInstance);

                    if(entityDateAttribute != null) {
                        if(lockEntityForUpdate(basePK)) {
                            try {
                                EntityDateAttributeValue entityDateAttributeValue = coreControl.getEntityDateAttributeValueForUpdate(entityDateAttribute);

                                entityDateAttributeValue.setDateAttribute(Integer.valueOf(edit.getDateAttribute()));

                                coreControl.updateEntityDateAttributeFromValue(entityDateAttributeValue, getPartyPK());
                            } finally {
                                unlockEntity(basePK);
                                basePK = null;
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityDateAttribute.name(), entityRef, entityAttributeName);
                    }
                }

                if(basePK != null) {
                    result.setEntityLock(getEntityLockTransfer(basePK));
                }

                if(entityDateAttribute != null) {
                    result.setEntityDateAttribute(coreControl.getEntityDateAttributeTransfer(getUserVisit(), entityDateAttribute, entityInstance));
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
