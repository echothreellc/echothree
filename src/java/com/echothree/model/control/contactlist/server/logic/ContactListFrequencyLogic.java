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

package com.echothree.model.control.contactlist.server.logic;

import com.echothree.model.control.contactlist.common.exception.UnknownContactListFrequencyNameException;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ContactListFrequencyLogic
    extends BaseLogic {

    protected ContactListFrequencyLogic() {
        super();
    }

    public static ContactListFrequencyLogic getInstance() {
        return CDI.current().select(ContactListFrequencyLogic.class).get();
    }
    
    @Inject
    ContactListControl contactListControl;

    public ContactListFrequency getContactListFrequencyByName(final ExecutionErrorAccumulator eea, final String contactListFrequencyName) {
        var contactListFrequency = contactListControl.getContactListFrequencyByName(contactListFrequencyName);

        if(contactListFrequency == null) {
            handleExecutionError(UnknownContactListFrequencyNameException.class, eea, ExecutionErrors.UnknownContactListFrequencyName.name(), contactListFrequencyName);
        }

        return contactListFrequency;
    }
    
}
