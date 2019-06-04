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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetContactMechanismChoicesForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.GetContactMechanismChoicesResult;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.party.server.entity.Party;
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
    public GetContactMechanismChoicesCommand(UserVisitPK userVisitPK, GetContactMechanismChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        GetContactMechanismChoicesResult result = ContactResultFactory.getGetContactMechanismChoicesResult();
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
            String contactMechanismTypeName = form.getContactMechanismTypeName();
            ContactMechanismType contactMechanismType = contactMechanismTypeName == null ? null : contactControl.getContactMechanismTypeByName(contactMechanismTypeName);
            
            if(contactMechanismTypeName == null || contactMechanismType != null) {
                String defaultContactMechanismChoice = form.getDefaultContactMechanismChoice();
                boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
                
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
