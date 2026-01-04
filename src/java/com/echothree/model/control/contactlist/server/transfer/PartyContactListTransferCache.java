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

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.contactlist.common.ContactListOptions;
import com.echothree.model.control.contactlist.common.transfer.PartyContactListTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyContactListTransferCache
        extends BaseContactListTransferCache<PartyContactList, PartyContactListTransfer> {

    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    boolean includeStatus;
    boolean includeComments;
    
    /** Creates a new instance of PartyContactListTransferCache */
    protected PartyContactListTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(ContactListOptions.PartyContactListIncludeUuid));
            includeStatus = options.contains(ContactListOptions.PartyContactListIncludeStatus);
            includeComments = options.contains(ContactListOptions.PartyContactListIncludeComments);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyContactListTransfer getPartyContactListTransfer(UserVisit userVisit, PartyContactList partyContactList) {
        var partyContactListTransfer = get(partyContactList);
        
        if(partyContactListTransfer == null) {
            var partyContactListDetail = partyContactList.getLastDetail();
            var partyTransfer = partyControl.getPartyTransfer(userVisit, partyContactListDetail.getParty());
            var contactList = partyContactListDetail.getContactList();
            var contactListTransfer = contactListControl.getContactListTransfer(userVisit, contactList);
            var preferredContactListContactMechanismPurpose = partyContactListDetail.getPreferredContactListContactMechanismPurpose();
            var preferredContactListContactMechanismPurposeTransfer = preferredContactListContactMechanismPurpose == null ? null : contactListControl.getContactListContactMechanismPurposeTransfer(userVisit, preferredContactListContactMechanismPurpose);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
            
            partyContactListTransfer = new PartyContactListTransfer(partyTransfer, contactListTransfer, preferredContactListContactMechanismPurposeTransfer);
            put(userVisit, partyContactList, partyContactListTransfer, entityInstance);
            
            if(includeStatus) {
                var workflow = contactList.getLastDetail().getDefaultPartyContactListStatus().getLastDetail().getWorkflow();
                
                if(workflow != null) {
                    partyContactListTransfer.setPartyContactListStatus(workflowControl.getWorkflowEntityStatusTransferByEntityInstance(userVisit, workflow,
                            entityInstance));
                }
            }

            if(includeComments) {
                setupComments(userVisit, partyContactList, entityInstance, partyContactListTransfer, CommentConstants.CommentType_PARTY_CONTACT_LIST);
            }
        }
        
        return partyContactListTransfer;
    }
    
}
