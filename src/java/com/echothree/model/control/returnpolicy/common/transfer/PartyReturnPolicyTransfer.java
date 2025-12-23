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

package com.echothree.model.control.returnpolicy.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyReturnPolicyTransfer
        extends BaseTransfer {

    private PartyTransfer party;
    private ReturnPolicyTransfer returnPolicy;
    private WorkflowEntityStatusTransfer partyReturnPolicyStatus;

    /** Creates a new instance of PartyReturnPolicyTransfer */
    public PartyReturnPolicyTransfer(PartyTransfer party, ReturnPolicyTransfer returnPolicy, WorkflowEntityStatusTransfer partyReturnPolicyStatus) {
        this.party = party;
        this.returnPolicy = returnPolicy;
        this.partyReturnPolicyStatus = partyReturnPolicyStatus;
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
     * Returns the returnPolicy.
     * @return the returnPolicy
     */
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }

    /**
     * Sets the returnPolicy.
     * @param returnPolicy the returnPolicy to set
     */
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /**
     * Returns the partyReturnPolicyStatus.
     * @return the partyReturnPolicyStatus
     */
    public WorkflowEntityStatusTransfer getPartyReturnPolicyStatus() {
        return partyReturnPolicyStatus;
    }

    /**
     * Sets the partyReturnPolicyStatus.
     * @param partyReturnPolicyStatus the partyReturnPolicyStatus to set
     */
    public void setPartyReturnPolicyStatus(WorkflowEntityStatusTransfer partyReturnPolicyStatus) {
        this.partyReturnPolicyStatus = partyReturnPolicyStatus;
    }

}
