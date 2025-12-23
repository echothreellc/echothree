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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import static com.echothree.model.control.customer.common.workflow.CustomerCreditCardContactMechanismConstants.Workflow_CUSTOMER_CREDIT_CARD_CONTACT_MECHANISM;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodContactMechanismTransfer;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodContactMechanism;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyPaymentMethodContactMechanismTransferCache
        extends BasePaymentTransferCache<PartyPaymentMethodContactMechanism, PartyPaymentMethodContactMechanismTransfer> {

    ContactControl contactControl = Session.getModelController(ContactControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyPaymentMethodControl partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PartyPaymentMethodContactMechanismTransferCache */
    protected PartyPaymentMethodContactMechanismTransferCache() {
        super();
    }

    @Override
    public PartyPaymentMethodContactMechanismTransfer getTransfer(UserVisit userVisit, PartyPaymentMethodContactMechanism partyPaymentMethodContactMechanism) {
        var partyPaymentMethodContactMechanismTransfer = get(partyPaymentMethodContactMechanism);
        
        if(partyPaymentMethodContactMechanismTransfer == null) {
            var partyPaymentMethod = partyPaymentMethodContactMechanism.getPartyPaymentMethod();
            var partyPaymentMethodTransfer = partyPaymentMethodControl.getPartyPaymentMethodTransfer(userVisit, partyPaymentMethod);
            var partyContactMechanismPurposeTransfer = contactControl.getPartyContactMechanismPurposeTransfer(userVisit, partyPaymentMethodContactMechanism.getPartyContactMechanismPurpose());
            WorkflowEntityStatusTransfer partyPaymentMethodContactMechanismStatusTransfer = null;

            var paymentMethodTypeName = partyPaymentMethod.getLastDetail().getPaymentMethod().getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyPaymentMethodContactMechanism.getPrimaryKey());
            if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                    partyPaymentMethodContactMechanismStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                            Workflow_CUSTOMER_CREDIT_CARD_CONTACT_MECHANISM, entityInstance);
            }
            
            partyPaymentMethodContactMechanismTransfer = new PartyPaymentMethodContactMechanismTransfer(partyPaymentMethodTransfer,
                    partyContactMechanismPurposeTransfer, partyPaymentMethodContactMechanismStatusTransfer);
            put(userVisit, partyPaymentMethodContactMechanism, partyPaymentMethodContactMechanismTransfer);
        }
        return partyPaymentMethodContactMechanismTransfer;
    }
    
}
