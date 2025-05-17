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
import com.echothree.control.user.core.common.edit.EntityAttributeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityAttributeDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAttributeDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityAttributeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityAttributeDescriptionCommand
        extends BaseAbstractEditCommand<EntityAttributeDescriptionSpec, EntityAttributeDescriptionEdit, EditEntityAttributeDescriptionResult, EntityAttributeDescription, EntityAttribute> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEntityAttributeDescriptionCommand */
    public EditEntityAttributeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityAttributeDescriptionResult getResult() {
        return CoreResultFactory.getEditEntityAttributeDescriptionResult();
    }

    @Override
    public EntityAttributeDescriptionEdit getEdit() {
        return CoreEditFactory.getEntityAttributeDescriptionEdit();
    }

    @Override
    public EntityAttributeDescription getEntity(EditEntityAttributeDescriptionResult result) {
        var coreControl = getCoreControl();
        EntityAttributeDescription entityAttributeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAttributeName = spec.getEntityAttributeName();
                var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);

                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            entityAttributeDescription = coreControl.getEntityAttributeDescription(entityAttribute, language);
                        } else { // EditMode.UPDATE
                            entityAttributeDescription = coreControl.getEntityAttributeDescriptionForUpdate(entityAttribute, language);
                        }

                        if(entityAttributeDescription == null) {
                            addExecutionError(ExecutionErrors.UnknownEntityAttributeDescription.name(), componentVendorName, entityTypeName, entityAttributeName, entityAttributeName, languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), componentVendorName, entityTypeName, entityAttributeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return entityAttributeDescription;
    }

    @Override
    public EntityAttribute getLockEntity(EntityAttributeDescription entityAttributeDescription) {
        return entityAttributeDescription.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityAttributeDescriptionResult result, EntityAttributeDescription entityAttributeDescription) {
        var coreControl = getCoreControl();

        result.setEntityAttributeDescription(coreControl.getEntityAttributeDescriptionTransfer(getUserVisit(), entityAttributeDescription, null));
    }

    @Override
    public void doLock(EntityAttributeDescriptionEdit edit, EntityAttributeDescription entityAttributeDescription) {
        edit.setDescription(entityAttributeDescription.getDescription());
    }

    @Override
    public void doUpdate(EntityAttributeDescription entityAttributeDescription) {
        var coreControl = getCoreControl();
        var entityAttributeDescriptionValue = coreControl.getEntityAttributeDescriptionValue(entityAttributeDescription);
        entityAttributeDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateEntityAttributeDescriptionFromValue(entityAttributeDescriptionValue, getPartyPK());
    }
    
}
