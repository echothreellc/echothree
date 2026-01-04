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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.edit.PartySecurityRoleTemplateEdit;
import com.echothree.control.user.security.common.edit.SecurityEditFactory;
import com.echothree.control.user.security.common.form.EditPartySecurityRoleTemplateForm;
import com.echothree.control.user.security.common.result.EditPartySecurityRoleTemplateResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.control.user.security.common.spec.PartySecurityRoleTemplateSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditPartySecurityRoleTemplateCommand
        extends BaseAbstractEditCommand<PartySecurityRoleTemplateSpec, PartySecurityRoleTemplateEdit, EditPartySecurityRoleTemplateResult, PartySecurityRoleTemplate, PartySecurityRoleTemplate> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplate.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPartySecurityRoleTemplateCommand */
    public EditPartySecurityRoleTemplateCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartySecurityRoleTemplateResult getResult() {
        return SecurityResultFactory.getEditPartySecurityRoleTemplateResult();
    }
    
    @Override
    public PartySecurityRoleTemplateEdit getEdit() {
        return SecurityEditFactory.getPartySecurityRoleTemplateEdit();
    }
    
    @Override
    public PartySecurityRoleTemplate getEntity(EditPartySecurityRoleTemplateResult result) {
        var securityControl = Session.getModelController(SecurityControl.class);
        PartySecurityRoleTemplate partySecurityRoleTemplate;
        var partySecurityRoleTemplateName = spec.getPartySecurityRoleTemplateName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);
        } else { // EditMode.UPDATE
            partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByNameForUpdate(partySecurityRoleTemplateName);
        }

        if(partySecurityRoleTemplate != null) {
            result.setPartySecurityRoleTemplate(securityControl.getPartySecurityRoleTemplateTransfer(getUserVisit(), partySecurityRoleTemplate));
        } else {
            addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
        }

        return partySecurityRoleTemplate;
    }
    
    @Override
    public PartySecurityRoleTemplate getLockEntity(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return partySecurityRoleTemplate;
    }
    
    @Override
    public void fillInResult(EditPartySecurityRoleTemplateResult result, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        var securityControl = Session.getModelController(SecurityControl.class);
        
        result.setPartySecurityRoleTemplate(securityControl.getPartySecurityRoleTemplateTransfer(getUserVisit(), partySecurityRoleTemplate));
    }
    
    @Override
    public void doLock(PartySecurityRoleTemplateEdit edit, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var partySecurityRoleTemplateDescription = securityControl.getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, getPreferredLanguage());
        var partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getLastDetail();
        
        edit.setPartySecurityRoleTemplateName(partySecurityRoleTemplateDetail.getPartySecurityRoleTemplateName());
        edit.setIsDefault(partySecurityRoleTemplateDetail.getIsDefault().toString());
        edit.setSortOrder(partySecurityRoleTemplateDetail.getSortOrder().toString());

        if(partySecurityRoleTemplateDescription != null) {
            edit.setDescription(partySecurityRoleTemplateDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var partySecurityRoleTemplateName = edit.getPartySecurityRoleTemplateName();
        var duplicatePartySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);

        if(duplicatePartySecurityRoleTemplate != null && !partySecurityRoleTemplate.equals(duplicatePartySecurityRoleTemplate)) {
            addExecutionError(ExecutionErrors.DuplicatePartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
        }
    }
    
    @Override
    public void doUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var partyPK = getPartyPK();
        var partySecurityRoleTemplateDetailValue = securityControl.getPartySecurityRoleTemplateDetailValueForUpdate(partySecurityRoleTemplate);
        var partySecurityRoleTemplateDescription = securityControl.getPartySecurityRoleTemplateDescriptionForUpdate(partySecurityRoleTemplate, getPreferredLanguage());
        var description = edit.getDescription();

        partySecurityRoleTemplateDetailValue.setPartySecurityRoleTemplateName(edit.getPartySecurityRoleTemplateName());
        partySecurityRoleTemplateDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        partySecurityRoleTemplateDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        securityControl.updatePartySecurityRoleTemplateFromValue(partySecurityRoleTemplateDetailValue, partyPK);

        if(partySecurityRoleTemplateDescription == null && description != null) {
            securityControl.createPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, getPreferredLanguage(), description, partyPK);
        } else if(partySecurityRoleTemplateDescription != null && description == null) {
            securityControl.deletePartySecurityRoleTemplateDescription(partySecurityRoleTemplateDescription, partyPK);
        } else if(partySecurityRoleTemplateDescription != null && description != null) {
            var partySecurityRoleTemplateDescriptionValue = securityControl.getPartySecurityRoleTemplateDescriptionValue(partySecurityRoleTemplateDescription);

            partySecurityRoleTemplateDescriptionValue.setDescription(description);
            securityControl.updatePartySecurityRoleTemplateDescriptionFromValue(partySecurityRoleTemplateDescriptionValue, partyPK);
        }
    }
    
}
