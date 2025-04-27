// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.core.common.edit.EntityAliasTypeEdit;
import com.echothree.control.user.core.common.form.EditEntityAliasTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAliasTypeResult;
import com.echothree.control.user.core.common.spec.EntityAliasTypeUniversalSpec;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.logic.EntityAliasTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EditEntityAliasTypeCommand
        extends BaseAbstractEditCommand<EntityAliasTypeUniversalSpec, EntityAliasTypeEdit, EditEntityAliasTypeResult, EntityAliasType, EntityAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAliasType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAliasTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditEntityAliasTypeCommand */
    public EditEntityAliasTypeCommand(UserVisitPK userVisitPK, EditEntityAliasTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityAliasTypeResult getResult() {
        return CoreResultFactory.getEditEntityAliasTypeResult();
    }

    @Override
    public EntityAliasTypeEdit getEdit() {
        return CoreEditFactory.getEntityAliasTypeEdit();
    }

    EntityAliasType entityAliasType = null;
    
    @Override
    public EntityAliasType getEntity(EditEntityAliasTypeResult result) {
        entityAliasType = EntityAliasTypeLogic.getInstance().getEntityAliasTypeByUniversalSpec(this,
                spec, editModeToEntityPermission(editMode));

        return entityAliasType;
    }

    @Override
    public EntityAliasType getLockEntity(EntityAliasType entityAliasType) {
        return entityAliasType;
    }

    @Override
    public void fillInResult(EditEntityAliasTypeResult result, EntityAliasType entityAliasType) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);

        result.setEntityAliasType(entityAliasControl.getEntityAliasTypeTransfer(getUserVisit(), entityAliasType, null));
    }

    @Override
    public void doLock(EntityAliasTypeEdit edit, EntityAliasType entityAliasType) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var entityAliasTypeDescription = entityAliasControl.getEntityAliasTypeDescription(entityAliasType, getPreferredLanguage());
        var entityAliasTypeDetail = entityAliasType.getLastDetail();

        edit.setEntityAliasTypeName(entityAliasTypeDetail.getEntityAliasTypeName());
        edit.setValidationPattern(entityAliasTypeDetail.getValidationPattern());
        edit.setIsDefault(entityAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(entityAliasTypeDetail.getSortOrder().toString());

        if(entityAliasTypeDescription != null) {
            edit.setDescription(entityAliasTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EntityAliasType entityAliasType) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var entityAliasTypeName = edit.getEntityAliasTypeName();
        var duplicateEntityAliasType = entityAliasControl.getEntityAliasTypeByName(entityAliasType.getLastDetail().getEntityType(),
                entityAliasTypeName);

        if(duplicateEntityAliasType != null && !entityAliasType.equals(duplicateEntityAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateEntityAliasTypeName.name(), entityAliasTypeName);
        }
    }

    @Override
    public void doUpdate(EntityAliasType entityAliasType) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var partyPK = getPartyPK();
        var entityAliasTypeDetailValue = entityAliasControl.getEntityAliasTypeDetailValueForUpdate(entityAliasType);
        var entityAliasTypeDescription = entityAliasControl.getEntityAliasTypeDescriptionForUpdate(entityAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        entityAliasTypeDetailValue.setEntityAliasTypeName(edit.getEntityAliasTypeName());
        entityAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        entityAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        entityAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        EntityAliasTypeLogic.getInstance().updateEntityAliasTypeFromValue(session, entityAliasTypeDetailValue, partyPK);

        if(entityAliasTypeDescription == null && description != null) {
            entityAliasControl.createEntityAliasTypeDescription(entityAliasType, getPreferredLanguage(), description, partyPK);
        } else if(entityAliasTypeDescription != null) {
            if(description == null) {
                entityAliasControl.deleteEntityAliasTypeDescription(entityAliasTypeDescription, partyPK);
            } else {
                var entityAliasTypeDescriptionValue = entityAliasControl.getEntityAliasTypeDescriptionValue(entityAliasTypeDescription);

                entityAliasTypeDescriptionValue.setDescription(description);
                entityAliasControl.updateEntityAliasTypeDescriptionFromValue(entityAliasTypeDescriptionValue, partyPK);
            }
        }
    }
    
}
