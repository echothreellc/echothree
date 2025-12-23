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

package com.echothree.model.control.cancellationpolicy.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyCancellationPolicyTransfer
        extends BaseTransfer {
    
    private PartyTransfer party;
    private CancellationPolicyTransfer cancellationPolicy;
    private WorkflowEntityStatusTransfer partyCancellationPolicyStatus;
    
    /** Creates a new instance of PartyCancellationPolicyTransfer */
    public PartyCancellationPolicyTransfer(PartyTransfer party, CancellationPolicyTransfer cancellationPolicy, WorkflowEntityStatusTransfer partyCancellationPolicyStatus) {
        this.party = party;
        this.cancellationPolicy = cancellationPolicy;
        this.partyCancellationPolicyStatus = partyCancellationPolicyStatus;
    }

    /**
     * Returns the party.
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * Sets the party.
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * Returns the cancellationPolicy.
     * @return the cancellationPolicy
     */
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    /**
     * Sets the cancellationPolicy.
     * @param cancellationPolicy the cancellationPolicy to set
     */
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * Returns the partyCancellationPolicyStatus.
     * @return the partyCancellationPolicyStatus
     */
    public WorkflowEntityStatusTransfer getPartyCancellationPolicyStatus() {
        return partyCancellationPolicyStatus;
    }

    /**
     * Sets the partyCancellationPolicyStatus.
     * @param partyCancellationPolicyStatus the partyCancellationPolicyStatus to set
     */
    public void setPartyCancellationPolicyStatus(WorkflowEntityStatusTransfer partyCancellationPolicyStatus) {
        this.partyCancellationPolicyStatus = partyCancellationPolicyStatus;
    }
    
}
