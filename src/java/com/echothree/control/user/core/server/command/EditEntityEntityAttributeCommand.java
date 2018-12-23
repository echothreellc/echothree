// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.core.common.edit.EntityEntityAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityEntityAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityEntityAttributeResult;
import com.echothree.control.user.core.common.spec.EntityEntityAttributeSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.EntityEntityAttributeValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityEntityAttributeCommand
        extends BaseEditCommand<EntityEntityAttributeSpec, EntityEntityAttributeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRefAttribute", FieldType.ENTITY_REF, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityEntityAttributeCommand */
    public EditEntityEntityAttributeCommand(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        EditEntityEntityAttributeResult result = CoreResultFactory.getEditEntityEntityAttributeResult();
        String entityRef = spec.getEntityRef();
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance != null) {
            String entityAttributeName = spec.getEntityAttributeName();
            EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);

            if(entityAttribute != null) {
                EntityEntityAttribute entityEntityAttribute = null;
                BasePK basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    entityEntityAttribute = coreControl.getEntityEntityAttribute(entityAttribute, entityInstance);

                    if(entityEntityAttribute != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setEntityEntityAttribute(coreControl.getEntityEntityAttributeTransfer(getUserVisit(), entityEntityAttribute, entityInstance));

                            if(lockEntity(basePK)) {
                                EntityEntityAttributeEdit edit = CoreEditFactory.getEntityEntityAttributeEdit();

                                result.setEdit(edit);
                                edit.setEntityRefAttribute(EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityEntityAttribute.getEntityInstanceAttribute()));
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                        } else { // EditMode.ABANDON
                            unlockEntity(basePK);
                            basePK = null;
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityEntityAttribute.name(), entityRef, entityAttributeName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    String entityRefAttribute = edit.getEntityRefAttribute();
                    EntityInstance entityInstanceAttribute = coreControl.getEntityInstanceByEntityRef(entityRefAttribute);

                    if(entityInstanceAttribute != null) {
                        entityEntityAttribute = coreControl.getEntityEntityAttributeForUpdate(entityAttribute, entityInstance);

                        if(entityEntityAttribute != null) {
                            if(lockEntityForUpdate(basePK)) {
                                try {
                                    EntityEntityAttributeValue entityEntityAttributeValue = coreControl.getEntityEntityAttributeValueForUpdate(entityEntityAttribute);

                                    entityEntityAttributeValue.setEntityInstanceAttributePK(entityInstanceAttribute.getPrimaryKey());

                                    coreControl.updateEntityEntityAttributeFromValue(entityEntityAttributeValue, getPartyPK());
                                } finally {
                                    unlockEntity(basePK);
                                    basePK = null;
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityEntityAttribute.name(), entityRef, entityAttributeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityRefAttribute.name(), entityRefAttribute);
                    }
                }

                if(basePK != null) {
                    result.setEntityLock(getEntityLockTransfer(basePK));
                }

                if(entityEntityAttribute != null) {
                    result.setEntityEntityAttribute(coreControl.getEntityEntityAttributeTransfer(getUserVisit(), entityEntityAttribute, entityInstance));
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
