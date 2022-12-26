// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.contactlist.common.transfer.ContactListFrequencyTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListGroupTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTypeTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListDetail;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactListTransferCache
        extends BaseContactListTransferCache<ContactList, ContactListTransfer> {
    
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of ContactListTransferCache */
    public ContactListTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ContactListTransfer getContactListTransfer(ContactList contactList) {
        ContactListTransfer contactListTransfer = get(contactList);
        
        if(contactListTransfer == null) {
            ContactListDetail contactListDetail = contactList.getLastDetail();
            String contactListName = contactListDetail.getContactListName();
            ContactListGroupTransfer contactListGroupTransfer = contactListControl.getContactListGroupTransfer(userVisit, contactListDetail.getContactListGroup());
            ContactListTypeTransfer contactListTypeTransfer = contactListControl.getContactListTypeTransfer(userVisit, contactListDetail.getContactListType());
            ContactListFrequency contactListFrequency = contactListDetail.getContactListFrequency();
            ContactListFrequencyTransfer contactListFrequencyTransfer = contactListFrequency == null ? null : contactListControl.getContactListFrequencyTransfer(userVisit, contactListFrequency);
            WorkflowEntranceTransfer defaultPartyContactListStatus = workflowControl.getWorkflowEntranceTransfer(userVisit, contactListDetail.getDefaultPartyContactListStatus());
            Boolean isDefault = contactListDetail.getIsDefault();
            Integer sortOrder = contactListDetail.getSortOrder();
            String description = contactListControl.getBestContactListDescription(contactList, getLanguage());
            
            contactListTransfer = new ContactListTransfer(contactListName, contactListGroupTransfer, contactListTypeTransfer, contactListFrequencyTransfer,
                    defaultPartyContactListStatus, isDefault, sortOrder, description);
            put(contactList, contactListTransfer);
        }
        
        return contactListTransfer;
    }
    
}
