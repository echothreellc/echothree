// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PaymentMethodTypePartyTypeTransfer
        extends BaseTransfer {

    private PaymentMethodTypeTransfer paymentMethodType;
    private PartyTypeTransfer partyType;
    private WorkflowTransfer partyPaymentMethodWorkflow;
    private WorkflowTransfer partyContactMechanismWorkflow;
    
    /** Creates a new instance of PaymentMethodTypePartyTypeTransfer */
    public PaymentMethodTypePartyTypeTransfer(PaymentMethodTypeTransfer paymentMethodType, PartyTypeTransfer partyType, WorkflowTransfer partyPaymentMethodWorkflow,
            WorkflowTransfer partyContactMechanismWorkflow) {
        this.paymentMethodType = paymentMethodType;
        this.partyType = partyType;
        this.partyPaymentMethodWorkflow = partyPaymentMethodWorkflow;
        this.partyContactMechanismWorkflow = partyContactMechanismWorkflow;
    }

    /**
     * @return the paymentMethodType
     */
    public PaymentMethodTypeTransfer getPaymentMethodType() {
        return paymentMethodType;
    }

    /**
     * @param paymentMethodType the paymentMethodType to set
     */
    public void setPaymentMethodType(PaymentMethodTypeTransfer paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    /**
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * @return the partyPaymentMethodWorkflow
     */
    public WorkflowTransfer getPartyPaymentMethodWorkflow() {
        return partyPaymentMethodWorkflow;
    }

    /**
     * @param partyPaymentMethodWorkflow the partyPaymentMethodWorkflow to set
     */
    public void setPartyPaymentMethodWorkflow(WorkflowTransfer partyPaymentMethodWorkflow) {
        this.partyPaymentMethodWorkflow = partyPaymentMethodWorkflow;
    }

    /**
     * @return the partyContactMechanismWorkflow
     */
    public WorkflowTransfer getPartyContactMechanismWorkflow() {
        return partyContactMechanismWorkflow;
    }

    /**
     * @param partyContactMechanismWorkflow the partyContactMechanismWorkflow to set
     */
    public void setPartyContactMechanismWorkflow(WorkflowTransfer partyContactMechanismWorkflow) {
        this.partyContactMechanismWorkflow = partyContactMechanismWorkflow;
    }
    
}
