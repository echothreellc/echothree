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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismPurposeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyPaymentMethodContactMechanismTransfer
        extends BaseTransfer {
    
    private PartyPaymentMethodTransfer partyPaymentMethod;
    private PartyContactMechanismPurposeTransfer partyContactMechanismPurpose;
    
    private WorkflowEntityStatusTransfer partyPaymentMethodContactMechanismStatus;
    
    /** Creates a new instance of PartyPaymentMethodContactMechanismTransfer */
    public PartyPaymentMethodContactMechanismTransfer(PartyPaymentMethodTransfer partyPaymentMethod,
            PartyContactMechanismPurposeTransfer partyContactMechanismPurpose,
            WorkflowEntityStatusTransfer partyPaymentMethodContactMechanismStatus) {
        this.partyPaymentMethod = partyPaymentMethod;
        this.partyContactMechanismPurpose = partyContactMechanismPurpose;
        this.partyPaymentMethodContactMechanismStatus = partyPaymentMethodContactMechanismStatus;
    }
    
    public PartyPaymentMethodTransfer getPartyPaymentMethod() {
        return partyPaymentMethod;
    }
    
    public void setPartyPaymentMethod(PartyPaymentMethodTransfer partyPaymentMethod) {
        this.partyPaymentMethod = partyPaymentMethod;
    }
    
    public PartyContactMechanismPurposeTransfer getPartyContactMechanismPurpose() {
        return partyContactMechanismPurpose;
    }
    
    public void setPartyContactMechanismPurpose(PartyContactMechanismPurposeTransfer partyContactMechanismPurpose) {
        this.partyContactMechanismPurpose = partyContactMechanismPurpose;
    }

    public WorkflowEntityStatusTransfer getPartyPaymentMethodContactMechanismStatus() {
        return partyPaymentMethodContactMechanismStatus;
    }

    public void setPartyPaymentMethodContactMechanismStatus(WorkflowEntityStatusTransfer partyPaymentMethodContactMechanismStatus) {
        this.partyPaymentMethodContactMechanismStatus = partyPaymentMethodContactMechanismStatus;
    }
    
}
