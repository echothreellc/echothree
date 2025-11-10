// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import static com.echothree.model.control.customer.common.workflow.CustomerCreditCardPaymentMethodConstants.Workflow_CUSTOMER_CREDIT_CARD_PAYMENT_METHOD;
import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class PartyPaymentMethodTransferCache
        extends BasePaymentTransferCache<PartyPaymentMethod, PartyPaymentMethodTransfer> {
    
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PartyPaymentMethodControl partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
    PaymentMethodControl paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    boolean includeNumber;
    boolean includeSecurityCode;
    boolean includePartyPaymentMethodContactMechanisms;
    boolean includeComments;
    boolean maskNumberAndSecurityCode;

    /** Creates a new instance of PartyPaymentMethodTransferCache */
    public PartyPaymentMethodTransferCache(UserVisit userVisit) {
        super(userVisit);

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PaymentOptions.PartyPaymentMethodIncludeUuid));
            includeNumber = options.contains(PaymentOptions.PartyPaymentMethodIncludeNumber);
            includeSecurityCode = options.contains(PaymentOptions.PartyPaymentMethodIncludeSecurityCode);
            includePartyPaymentMethodContactMechanisms = options.contains(PaymentOptions.PartyPaymentMethodIncludePartyPaymentMethodContactMechanisms);
            includeComments = options.contains(PaymentOptions.PartyPaymentMethodIncludeComments);

            if(includeNumber || includeSecurityCode) {
                var userControl = Session.getModelController(UserControl.class);

                if(!SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, userControl.getPartyFromUserVisit(userVisit),
                        SecurityRoleGroups.PartyPaymentMethod.name(), SecurityRoles.CreditCard.name())) {
                    includeNumber = false;
                    includeSecurityCode = false;
                    maskNumberAndSecurityCode = true;
                }
            }
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public PartyPaymentMethodTransfer getTransfer(PartyPaymentMethod partyPaymentMethod) {
        var partyPaymentMethodTransfer = get(partyPaymentMethod);
        
        if(partyPaymentMethodTransfer == null) {
            var partyPaymentMethodDetail = partyPaymentMethod.getLastDetail();
            var partyPaymentMethodName = partyPaymentMethodDetail.getPartyPaymentMethodName();
            var partyTransfer = partyControl.getPartyTransfer(userVisit, partyPaymentMethodDetail.getParty());
            var description = partyPaymentMethodDetail.getDescription();
            var paymentMethodTransfer = paymentMethodControl.getPaymentMethodTransfer(userVisit, partyPaymentMethodDetail.getPaymentMethod());
            var paymentMethodTypeName = paymentMethodTransfer.getPaymentMethodType().getPaymentMethodTypeName();
            var deleteWhenUnused = partyPaymentMethodDetail.getDeleteWhenUnused();
            var isDefault = partyPaymentMethodDetail.getIsDefault();
            var sortOrder = partyPaymentMethodDetail.getSortOrder();
            WorkflowEntityStatusTransfer partyPaymentMethodStatusTransfer = null;
            String number = null;
            Integer expirationMonth = null;
            Integer expirationYear = null;
            PersonalTitleTransfer personalTitleTransfer = null;
            String firstName = null;
            String middleName = null;
            String lastName = null;
            NameSuffixTransfer nameSuffixTransfer = null;
            String name = null;
            PartyContactMechanismTransfer billingPartyContactMechanismTransfer = null;
            String issuerName = null;
            PartyContactMechanismTransfer issuerPartyContactMechanismTransfer = null;
            String securityCode = null;
            EntityInstance entityInstance = null;
            
            if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                var partyPaymentMethodCreditCard = partyPaymentMethodControl.getPartyPaymentMethodCreditCard(partyPaymentMethod);
                
                if(partyPaymentMethodCreditCard != null) {
                    var partyPaymentMethodCreditCardSecurityCode = partyPaymentMethodControl.getPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod);
                    
                    if(includeNumber || maskNumberAndSecurityCode) {
                        var decodedNumber = partyPaymentMethodControl.decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard);

                        if(decodedNumber != null) {
                            number = includeNumber? partyPaymentMethodControl.decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard): StringUtils.getInstance().mask(decodedNumber, 'X', 4);
                        }
                    }
                    
                    expirationMonth = partyPaymentMethodCreditCard.getExpirationMonth();
                    expirationYear = partyPaymentMethodCreditCard.getExpirationYear();
                    var personalTitle = partyPaymentMethodCreditCard.getPersonalTitle();
                    personalTitleTransfer = personalTitle == null? null: partyControl.getPersonalTitleTransfer(userVisit, personalTitle);
                    firstName = partyPaymentMethodCreditCard.getFirstName();
                    middleName = partyPaymentMethodCreditCard.getMiddleName();
                    lastName = partyPaymentMethodCreditCard.getLastName();
                    var nameSuffix = partyPaymentMethodCreditCard.getNameSuffix();
                    nameSuffixTransfer = nameSuffix == null? null: partyControl.getNameSuffixTransfer(userVisit, nameSuffix);
                    name = partyPaymentMethodCreditCard.getName();
                    var billingPartyContactMechanism = partyPaymentMethodCreditCard.getBillingPartyContactMechanism();
                    billingPartyContactMechanismTransfer = billingPartyContactMechanism == null? null: contactControl.getPartyContactMechanismTransfer(userVisit, billingPartyContactMechanism);
                    issuerName = partyPaymentMethodCreditCard.getIssuerName();
                    var issuerPartyContactMechanism = partyPaymentMethodCreditCard.getIssuerPartyContactMechanism();
                    issuerPartyContactMechanismTransfer = issuerPartyContactMechanism == null? null: contactControl.getPartyContactMechanismTransfer(userVisit, issuerPartyContactMechanism);
                    
                    if(partyPaymentMethodCreditCardSecurityCode != null) {
                        if(includeSecurityCode || maskNumberAndSecurityCode) {
                            var decodedSecurityCode = partyPaymentMethodControl.decodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(partyPaymentMethodCreditCardSecurityCode);

                            if(decodedSecurityCode != null) {
                                securityCode = includeNumber? partyPaymentMethodControl.decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard): StringUtils.getInstance().mask(decodedSecurityCode, 'X');
                            }
                        }
                    }
                    
                    entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyPaymentMethod.getPrimaryKey());
                    partyPaymentMethodStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                            Workflow_CUSTOMER_CREDIT_CARD_PAYMENT_METHOD, entityInstance);
                }
            }
            
            partyPaymentMethodTransfer = new PartyPaymentMethodTransfer(partyPaymentMethodName, partyTransfer, description,
                    paymentMethodTransfer, deleteWhenUnused, isDefault, sortOrder,
                    partyPaymentMethodStatusTransfer, number, expirationMonth, expirationYear, personalTitleTransfer, firstName,
                    middleName, lastName, nameSuffixTransfer, name, billingPartyContactMechanismTransfer, issuerName,
                    issuerPartyContactMechanismTransfer, securityCode);
            put(userVisit, partyPaymentMethod, partyPaymentMethodTransfer);
            
            if(includePartyPaymentMethodContactMechanisms) {
                partyPaymentMethodTransfer.setPartyPaymentMethodContactMechanisms(new ListWrapper<>(partyPaymentMethodControl.getPartyPaymentMethodContactMechanismTransfersByPartyPaymentMethod(userVisit, partyPaymentMethod)));
            }

            if(includeComments) {
                setupComments(userVisit, partyPaymentMethod, entityInstance, partyPaymentMethodTransfer, CommentConstants.CommentType_PARTY_PAYMENT_METHOD);
            }
        }
        
        return partyPaymentMethodTransfer;
    }
    
}
