// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.contactlist.server.logic;

import com.echothree.model.control.contactlist.common.exception.UnknownContactListGroupNameException;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class ContactListGroupLogic
    extends BaseLogic {
    
    private ContactListGroupLogic() {
        super();
    }
    
    private static class ContactListLogicHolder {
        static ContactListGroupLogic instance = new ContactListGroupLogic();
    }
    
    public static ContactListGroupLogic getInstance() {
        return ContactListLogicHolder.instance;
    }
    
    public ContactListGroup getContactListGroupByName(final ExecutionErrorAccumulator eea, final String contactListGroupName) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListGroup contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

        if(contactListGroup == null) {
            handleExecutionError(UnknownContactListGroupNameException.class, eea, ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
        }

        return contactListGroup;
    }
    
}
