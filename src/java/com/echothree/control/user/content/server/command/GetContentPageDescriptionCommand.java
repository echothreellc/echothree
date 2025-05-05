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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentPageDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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

public class GetContentPageDescriptionCommand
        extends BaseSimpleCommand<GetContentPageDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPage.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentPageDescriptionCommand */
    public GetContentPageDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ContentResultFactory.getGetContentPageDescriptionResult();
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentSectionName = form.getContentSectionName();
            var contentSection = contentControl.getContentSectionByName(contentCollection, contentSectionName);
            
            if(contentSection != null) {
                var contentPageName = form.getContentPageName();
                var contentPage = contentControl.getContentPageByName(contentSection, contentPageName);
                
                if(contentPage != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = form.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        var contentPageDescription = contentControl.getContentPageDescription(contentPage, language);
                        
                        if(contentPageDescription != null) {
                            result.setContentPageDescription(contentControl.getContentPageDescriptionTransfer(getUserVisit(), contentPageDescription));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownContentPageDescription.name(), contentCollectionName, contentSectionName, contentPageName,
                                    languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentPageName.name(), contentCollectionName, contentSectionName, contentPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentSectionName.name(), contentCollectionName, contentSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return result;
    }
    
}
