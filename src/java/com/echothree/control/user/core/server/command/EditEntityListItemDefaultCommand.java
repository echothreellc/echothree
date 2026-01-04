// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.core.common.edit.EntityListItemAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityListItemDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityListItemDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityListItemDefaultCommand
        extends BaseEditCommand<EntityListItemDefaultSpec, EntityListItemAttributeEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityListItemUuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of EditEntityListItemDefaultCommand */
    public EditEntityListItemDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getEditEntityListItemDefaultResult();
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);

        if(!hasExecutionErrors()) {
            EntityListItemDefault entityListItemDefault = null;

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityListItemDefault = coreControl.getEntityListItemDefault(entityAttribute);

                if(entityListItemDefault != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        result.setEntityListItemDefault(coreControl.getEntityListItemDefaultTransfer(getUserVisit(), entityListItemDefault));

                        if(lockEntity(entityAttribute)) {
                            var edit = CoreEditFactory.getEntityListItemAttributeEdit();

                            result.setEdit(edit);
                            edit.setEntityListItemName(entityListItemDefault.getEntityListItem().getLastDetail().getEntityListItemName());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                    } else { // EditMode.ABANDON
                        unlockEntity(entityAttribute);
                        entityAttribute = null;
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityListItemDefault.name(),
                            entityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var entityListItem = EntityAttributeLogic.getInstance().getEntityListItem(this, entityAttribute, edit);

                if(!hasExecutionErrors()) {
                    entityListItemDefault = coreControl.getEntityListItemDefaultForUpdate(entityAttribute);

                    if(entityListItemDefault != null) {
                        if(lockEntityForUpdate(entityAttribute)) {
                            try {
                                var entityListItemDefaultValue = coreControl.getEntityListItemDefaultValueForUpdate(entityListItemDefault);

                                entityListItemDefaultValue.setEntityListItemPK(entityListItem.getPrimaryKey());

                                coreControl.updateEntityListItemDefaultFromValue(entityListItemDefaultValue, getPartyPK());
                            } finally {
                                unlockEntity(entityAttribute);
                                entityAttribute = null;
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityListItemDefault.name(),
                                entityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                                entityAttribute.getLastDetail().getEntityAttributeName());
                    }
                }
            }

            if(entityAttribute != null) {
                result.setEntityLock(getEntityLockTransfer(entityAttribute));
            }

            if(entityListItemDefault != null) {
                result.setEntityListItemDefault(coreControl.getEntityListItemDefaultTransfer(getUserVisit(), entityListItemDefault));
            }
        }

        return result;
    }
    
}
