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
import com.echothree.control.user.core.common.edit.EntityAliasTypeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityAliasTypeDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAliasTypeDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityAliasTypeDescriptionSpec;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAliasTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<EntityAliasTypeDescriptionSpec, EntityAliasTypeDescriptionEdit, EditEntityAliasTypeDescriptionResult, EntityAliasTypeDescription, EntityAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEntityAliasTypeDescriptionCommand */
    public EditEntityAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityAliasTypeDescriptionResult getResult() {
        return CoreResultFactory.getEditEntityAliasTypeDescriptionResult();
    }

    @Override
    public EntityAliasTypeDescriptionEdit getEdit() {
        return CoreEditFactory.getEntityAliasTypeDescriptionEdit();
    }

    @Override
    public EntityAliasTypeDescription getEntity(EditEntityAliasTypeDescriptionResult result) {
        EntityAliasTypeDescription entityAliasTypeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAliasControl = Session.getModelController(EntityAliasControl.class);
                var entityAliasTypeName = spec.getEntityAliasTypeName();
                var entityAliasType = entityAliasControl.getEntityAliasTypeByName(entityType, entityAliasTypeName);

                if(entityAliasType != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);

                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            entityAliasTypeDescription = entityAliasControl.getEntityAliasTypeDescription(entityAliasType, language);
                        } else { // EditMode.UPDATE
                            entityAliasTypeDescription = entityAliasControl.getEntityAliasTypeDescriptionForUpdate(entityAliasType, language);
                        }

                        if(entityAliasTypeDescription == null) {
                            addExecutionError(ExecutionErrors.UnknownEntityAliasTypeDescription.name(), componentVendorName, entityTypeName, entityAliasTypeName, entityAliasTypeName, languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAliasTypeName.name(), componentVendorName, entityTypeName, entityAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return entityAliasTypeDescription;
    }

    @Override
    public EntityAliasType getLockEntity(EntityAliasTypeDescription entityAliasTypeDescription) {
        return entityAliasTypeDescription.getEntityAliasType();
    }

    @Override
    public void fillInResult(EditEntityAliasTypeDescriptionResult result, EntityAliasTypeDescription entityAliasTypeDescription) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);

        result.setEntityAliasTypeDescription(entityAliasControl.getEntityAliasTypeDescriptionTransfer(getUserVisit(), entityAliasTypeDescription, null));
    }

    @Override
    public void doLock(EntityAliasTypeDescriptionEdit edit, EntityAliasTypeDescription entityAliasTypeDescription) {
        edit.setDescription(entityAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(EntityAliasTypeDescription entityAliasTypeDescription) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var entityAliasTypeDescriptionValue = entityAliasControl.getEntityAliasTypeDescriptionValue(entityAliasTypeDescription);
        entityAliasTypeDescriptionValue.setDescription(edit.getDescription());

        entityAliasControl.updateEntityAliasTypeDescriptionFromValue(entityAliasTypeDescriptionValue, getPartyPK());
    }
    
}
