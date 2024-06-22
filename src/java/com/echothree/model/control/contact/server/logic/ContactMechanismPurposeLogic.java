// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.contact.server.logic;

import com.echothree.model.control.contact.common.exception.UnknownContactMechanismPurposeNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class ContactMechanismPurposeLogic
    extends BaseLogic {
    
    private ContactMechanismPurposeLogic() {
        super();
    }
    
    private static class ContactMechanismPurposeLogicHolder {
        static ContactMechanismPurposeLogic instance = new ContactMechanismPurposeLogic();
    }
    
    public static ContactMechanismPurposeLogic getInstance() {
        return ContactMechanismPurposeLogicHolder.instance;
    }
    
    public ContactMechanismPurpose getContactMechanismPurposeByName(final ExecutionErrorAccumulator eea, final String contactMechanismPurposeName) {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);

        if(contactMechanismPurpose == null) {
            handleExecutionError(UnknownContactMechanismPurposeNameException.class, eea, ExecutionErrors.UnknownContactMechanismPurposeName.name(), contactMechanismPurposeName);
        }

        return contactMechanismPurpose;
    }
    
}
