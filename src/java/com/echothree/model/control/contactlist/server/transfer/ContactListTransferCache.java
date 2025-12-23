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

package com.echothree.model.control.contactlist.server.transfer;

import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContactListTransferCache
        extends BaseContactListTransferCache<ContactList, ContactListTransfer> {

    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of ContactListTransferCache */
    protected ContactListTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public ContactListTransfer getContactListTransfer(UserVisit userVisit, ContactList contactList) {
        var contactListTransfer = get(contactList);
        
        if(contactListTransfer == null) {
            var contactListDetail = contactList.getLastDetail();
            var contactListName = contactListDetail.getContactListName();
            var contactListGroupTransfer = contactListControl.getContactListGroupTransfer(userVisit, contactListDetail.getContactListGroup());
            var contactListTypeTransfer = contactListControl.getContactListTypeTransfer(userVisit, contactListDetail.getContactListType());
            var contactListFrequency = contactListDetail.getContactListFrequency();
            var contactListFrequencyTransfer = contactListFrequency == null ? null : contactListControl.getContactListFrequencyTransfer(userVisit, contactListFrequency);
            var defaultPartyContactListStatus = workflowControl.getWorkflowEntranceTransfer(userVisit, contactListDetail.getDefaultPartyContactListStatus());
            var isDefault = contactListDetail.getIsDefault();
            var sortOrder = contactListDetail.getSortOrder();
            var description = contactListControl.getBestContactListDescription(contactList, getLanguage(userVisit));
            
            contactListTransfer = new ContactListTransfer(contactListName, contactListGroupTransfer, contactListTypeTransfer, contactListFrequencyTransfer,
                    defaultPartyContactListStatus, isDefault, sortOrder, description);
            put(userVisit, contactList, contactListTransfer);
        }
        
        return contactListTransfer;
    }
    
}
