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

package com.echothree.model.control.cancellationpolicy.server.transfer;

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.PartyCancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.PartyCancellationPolicyLogic;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.data.cancellationpolicy.server.entity.PartyCancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyCancellationPolicyTransferCache
        extends BaseCancellationPolicyTransferCache<PartyCancellationPolicy, PartyCancellationPolicyTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyCancellationPolicyTransferCache */
    public PartyCancellationPolicyTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
    }

    public PartyCancellationPolicyTransfer getPartyCancellationPolicyTransfer(PartyCancellationPolicy partyCancellationPolicy) {
        PartyCancellationPolicyTransfer partyCancellationPolicyTransfer = get(partyCancellationPolicy);
        
        if(partyCancellationPolicyTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partyCancellationPolicy.getParty());
            CancellationPolicyTransfer cancellationPolicy = cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, partyCancellationPolicy.getCancellationPolicy());
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(partyCancellationPolicy.getPrimaryKey());
            PartyPK createdBy = getPartyPK();
            WorkflowEntityStatusTransfer partyCancellationPolicyStatusTransfer = PartyCancellationPolicyLogic.getInstance().getPartyCancellationPolicyStatusTransfer(userVisit, entityInstance, createdBy);

            partyCancellationPolicyTransfer = new PartyCancellationPolicyTransfer(party, cancellationPolicy, partyCancellationPolicyStatusTransfer);
            put(partyCancellationPolicy, partyCancellationPolicyTransfer, entityInstance);
        }
        
        return partyCancellationPolicyTransfer;
    }
    
}
