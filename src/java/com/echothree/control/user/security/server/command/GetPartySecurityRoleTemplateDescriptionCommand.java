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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateDescriptionForm;
import com.echothree.control.user.security.common.result.GetPartySecurityRoleTemplateDescriptionResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPartySecurityRoleTemplateDescriptionCommand
        extends BaseSimpleCommand<GetPartySecurityRoleTemplateDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplate.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartySecurityRoleTemplateDescriptionCommand */
    public GetPartySecurityRoleTemplateDescriptionCommand(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        GetPartySecurityRoleTemplateDescriptionResult result = SecurityResultFactory.getGetPartySecurityRoleTemplateDescriptionResult();
        String partySecurityRoleTemplateName = form.getPartySecurityRoleTemplateName();
        PartySecurityRoleTemplate partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);
        
        if(partySecurityRoleTemplate != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription = securityControl.getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate,
                        language);
                
                if(partySecurityRoleTemplateDescription != null) {
                    result.setPartySecurityRoleTemplateDescription(securityControl.getPartySecurityRoleTemplateDescriptionTransfer(getUserVisit(),
                            partySecurityRoleTemplateDescription));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateDescription.name(), partySecurityRoleTemplateName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
        }
        
        return result;
    }
    
}
