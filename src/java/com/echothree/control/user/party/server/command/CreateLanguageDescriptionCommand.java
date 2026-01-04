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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateLanguageDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateLanguageDescriptionCommand
        extends BaseSimpleCommand<CreateLanguageDescriptionForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DescriptionLanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateLanguageDescriptionCommand */
    public CreateLanguageDescriptionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var languageIsoName = form.getLanguageIsoName();
        var language = partyControl.getLanguageByIsoName(languageIsoName);
        
        if(language != null) {
            var descriptionLanguageIsoName = form.getDescriptionLanguageIsoName();
            var description = form.getDescription();
            var descriptionLanguage = partyControl.getLanguageByIsoName(descriptionLanguageIsoName);

            if(descriptionLanguageIsoName != null) {
                var languageDescription = partyControl.getLanguageDescription(language, descriptionLanguage);

                if(languageDescription == null) {
                    partyControl.createLanguageDescription(language, descriptionLanguage, description, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateLanguageDescription.name(), languageIsoName, descriptionLanguageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDescriptionLanguageIsoName.name(), descriptionLanguageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
        }
        
        return null;
    }
    
}
