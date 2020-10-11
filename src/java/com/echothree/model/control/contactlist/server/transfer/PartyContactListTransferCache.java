// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.contactlist.common.ContactListOptions;
import com.echothree.model.control.contactlist.common.transfer.ContactListContactMechanismPurposeTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.PartyContactListTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.contactlist.server.entity.PartyContactListDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class PartyContactListTransferCache
        extends BaseContactListTransferCache<PartyContactList, PartyContactListTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    boolean includeStatus;
    boolean includeComments;
    
    /** Creates a new instance of PartyContactListTransferCache */
    public PartyContactListTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(ContactListOptions.PartyContactListIncludeKey));
            setIncludeGuid(options.contains(ContactListOptions.PartyContactListIncludeGuid));
            includeStatus = options.contains(ContactListOptions.PartyContactListIncludeStatus);
            includeComments = options.contains(ContactListOptions.PartyContactListIncludeComments);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyContactListTransfer getPartyContactListTransfer(PartyContactList partyContactList) {
        PartyContactListTransfer partyContactListTransfer = get(partyContactList);
        
        if(partyContactListTransfer == null) {
            PartyContactListDetail partyContactListDetail = partyContactList.getLastDetail();
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, partyContactListDetail.getParty());
            ContactList contactList = partyContactListDetail.getContactList();
            ContactListTransfer contactListTransfer = contactListControl.getContactListTransfer(userVisit, contactList);
            ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose = partyContactListDetail.getPreferredContactListContactMechanismPurpose();
            ContactListContactMechanismPurposeTransfer preferredContactListContactMechanismPurposeTransfer = preferredContactListContactMechanismPurpose == null ? null : contactListControl.getContactListContactMechanismPurposeTransfer(userVisit, preferredContactListContactMechanismPurpose);
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
            
            partyContactListTransfer = new PartyContactListTransfer(partyTransfer, contactListTransfer, preferredContactListContactMechanismPurposeTransfer);
            put(partyContactList, partyContactListTransfer, entityInstance);
            
            if(includeStatus) {
                Workflow workflow = contactList.getLastDetail().getDefaultPartyContactListStatus().getLastDetail().getWorkflow();
                
                if(workflow != null) {
                    partyContactListTransfer.setPartyContactListStatus(workflowControl.getWorkflowEntityStatusTransferByEntityInstance(userVisit, workflow,
                            entityInstance));
                }
            }

            if(includeComments) {
                setupComments(partyContactList, entityInstance, partyContactListTransfer, CommentConstants.CommentType_PARTY_CONTACT_LIST);
            }
        }
        
        return partyContactListTransfer;
    }
    
}
