// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.contact.common.form.GetContactMechanismPurposeChoicesForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.GetContactMechanismPurposeChoicesResult;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.contactlist.server.entity.ContactList;
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

public class GetContactMechanismPurposeChoicesCommand
        extends BaseSimpleCommand<GetContactMechanismPurposeChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultContactMechanismPurposeChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContactMechanismPurposeChoicesCommand */
    public GetContactMechanismPurposeChoicesCommand(UserVisitPK userVisitPK, GetContactMechanismPurposeChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetContactMechanismPurposeChoicesResult result = ContactResultFactory.getGetContactMechanismPurposeChoicesResult();
        String contactMechanismName = form.getContactMechanismName();
        String contactMechanismTypeName = form.getContactMechanismTypeName();
        String contactListName = form.getContactListName();
        int parameterCount = (contactMechanismName == null? 0: 1) + (contactMechanismTypeName == null? 0: 1) + (contactListName == null? 0: 1);
        
        if(parameterCount < 2) {
            var contactControl = Session.getModelController(ContactControl.class);
            ContactMechanismType contactMechanismType = null;
            String defaultContactMechanismPurposeChoice = form.getDefaultContactMechanismPurposeChoice();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            if(contactMechanismName != null) {
                ContactMechanism contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);
                
                if(contactMechanism != null) {
                    contactMechanismType = contactMechanism.getLastDetail().getContactMechanismType();
                } else {
                    addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
                }
            } else if(contactMechanismTypeName != null) {
                contactMechanismType = contactControl.getContactMechanismTypeByName(contactMechanismTypeName);

                if(contactMechanismType == null) {
                    addExecutionError(ExecutionErrors.UnknownContactMechanismTypeName.name(), contactMechanismTypeName);
                }
            } else if(contactListName != null) {
                ContactList contactList = ContactListLogic.getInstance().getContactListByName(this, contactListName);
                
                if(!hasExecutionErrors()) {
                    result.setContactMechanismPurposeChoices(contactControl.getContactMechanismPurposeChoicesByContactList(defaultContactMechanismPurposeChoice,
                            getPreferredLanguage(), allowNullChoice, contactList));
                }
            } else {
                result.setContactMechanismPurposeChoices(contactControl.getContactMechanismPurposeChoices(defaultContactMechanismPurposeChoice,
                        getPreferredLanguage(), allowNullChoice));
            }

            if(contactMechanismType != null) {
                result.setContactMechanismPurposeChoices(contactControl.getContactMechanismPurposeChoicesByContactMechanismType(contactMechanismType,
                        defaultContactMechanismPurposeChoice, getPreferredLanguage(), allowNullChoice));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
