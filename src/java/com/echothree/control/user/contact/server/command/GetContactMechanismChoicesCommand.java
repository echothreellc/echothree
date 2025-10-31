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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetContactMechanismChoicesForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetContactMechanismChoicesCommand
        extends BaseSimpleCommand<GetContactMechanismChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultContactMechanismChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContactMechanismChoicesCommand */
    public GetContactMechanismChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = ContactResultFactory.getGetContactMechanismChoicesResult();
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var contactMechanismTypeName = form.getContactMechanismTypeName();
            var contactMechanismType = contactMechanismTypeName == null ? null : contactControl.getContactMechanismTypeByName(contactMechanismTypeName);
            
            if(contactMechanismTypeName == null || contactMechanismType != null) {
                var defaultContactMechanismChoice = form.getDefaultContactMechanismChoice();
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
                
                result.setContactMechanismChoices(contactMechanismType == null ? contactControl.getContactMechanismChoicesByParty(party, defaultContactMechanismChoice, getPreferredLanguage(), allowNullChoice)
                        : contactControl.getContactMechanismChoicesByPartyAndContactMechanismType(party, contactMechanismType, defaultContactMechanismChoice, getPreferredLanguage(), allowNullChoice));
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismTypeName.name(), contactMechanismType);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}
