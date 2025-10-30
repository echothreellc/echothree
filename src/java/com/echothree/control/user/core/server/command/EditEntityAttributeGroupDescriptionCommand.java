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
import com.echothree.control.user.core.common.edit.EntityAttributeGroupDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityAttributeGroupDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEntityAttributeGroupDescriptionCommand
        extends BaseAbstractEditCommand<EntityAttributeGroupDescriptionSpec, EntityAttributeGroupDescriptionEdit, EditEntityAttributeGroupDescriptionResult, EntityAttributeGroupDescription, EntityAttributeGroup> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeGroup.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEntityAttributeGroupDescriptionCommand */
    public EditEntityAttributeGroupDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityAttributeGroupDescriptionResult getResult() {
        return CoreResultFactory.getEditEntityAttributeGroupDescriptionResult();
    }

    @Override
    public EntityAttributeGroupDescriptionEdit getEdit() {
        return CoreEditFactory.getEntityAttributeGroupDescriptionEdit();
    }

    @Override
    public EntityAttributeGroupDescription getEntity(EditEntityAttributeGroupDescriptionResult result) {
        var coreControl = getCoreControl();
        EntityAttributeGroupDescription entityAttributeGroupDescription = null;
        var entityAttributeGroupName = spec.getEntityAttributeGroupName();
        var entityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName);

        if(entityAttributeGroup != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    entityAttributeGroupDescription = coreControl.getEntityAttributeGroupDescription(entityAttributeGroup, language);
                } else { // EditMode.UPDATE
                    entityAttributeGroupDescription = coreControl.getEntityAttributeGroupDescriptionForUpdate(entityAttributeGroup, language);
                }

                if(entityAttributeGroupDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeGroupDescription.name(), entityAttributeGroupName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityAttributeGroupName.name(), entityAttributeGroupName);
        }

        return entityAttributeGroupDescription;
    }

    @Override
    public EntityAttributeGroup getLockEntity(EntityAttributeGroupDescription entityAttributeGroupDescription) {
        return entityAttributeGroupDescription.getEntityAttributeGroup();
    }

    @Override
    public void fillInResult(EditEntityAttributeGroupDescriptionResult result, EntityAttributeGroupDescription entityAttributeGroupDescription) {
        var coreControl = getCoreControl();

        result.setEntityAttributeGroupDescription(coreControl.getEntityAttributeGroupDescriptionTransfer(getUserVisit(), entityAttributeGroupDescription, null));
    }

    @Override
    public void doLock(EntityAttributeGroupDescriptionEdit edit, EntityAttributeGroupDescription entityAttributeGroupDescription) {
        edit.setDescription(entityAttributeGroupDescription.getDescription());
    }

    @Override
    public void doUpdate(EntityAttributeGroupDescription entityAttributeGroupDescription) {
        var coreControl = getCoreControl();
        var entityAttributeGroupDescriptionValue = coreControl.getEntityAttributeGroupDescriptionValue(entityAttributeGroupDescription);
        
        entityAttributeGroupDescriptionValue.setDescription(edit.getDescription());
        
        coreControl.updateEntityAttributeGroupDescriptionFromValue(entityAttributeGroupDescriptionValue, getPartyPK());
    }

    
}
