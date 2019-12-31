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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.payment.common.PaymentConstants;
import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.server.UserControl;
import static com.echothree.model.control.customer.common.workflow.CustomerCreditCardPaymentMethodConstants.Workflow_CUSTOMER_CREDIT_CARD_PAYMENT_METHOD;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCardSecurityCode;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class PartyPaymentMethodTransferCache
        extends BasePaymentTransferCache<PartyPaymentMethod, PartyPaymentMethodTransfer> {
    
    ContactControl contactControl;
    CoreControl coreControl;
    PartyControl partyControl;
    WorkflowControl workflowControl;
    boolean includeKey;
    boolean includeGuid;
    boolean includeNumber;
    boolean includeSecurityCode;
    boolean includePartyPaymentMethodContactMechanisms;
    boolean maskNumberAndSecurityCode;
    boolean includeComments;
    
    /** Creates a new instance of PartyPaymentMethodTransferCache */
    public PartyPaymentMethodTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        maskNumberAndSecurityCode = false;

        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PaymentOptions.PartyPaymentMethodIncludeKey));
            setIncludeGuid(options.contains(PaymentOptions.PartyPaymentMethodIncludeGuid));
            includeNumber = options.contains(PaymentOptions.PartyPaymentMethodIncludeNumber);
            includeSecurityCode = options.contains(PaymentOptions.PartyPaymentMethodIncludeSecurityCode);
            includePartyPaymentMethodContactMechanisms = options.contains(PaymentOptions.PartyPaymentMethodIncludePartyPaymentMethodContactMechanisms);
            includeComments = options.contains(PaymentOptions.PartyPaymentMethodIncludeComments);

            if(includeNumber || includeSecurityCode) {
                UserControl userControl = (UserControl)Session.getModelController(UserControl.class);

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
    
    public PartyPaymentMethodTransfer getPartyPaymentMethodTransfer(PartyPaymentMethod partyPaymentMethod) {
        PartyPaymentMethodTransfer partyPaymentMethodTransfer = get(partyPaymentMethod);
        
        if(partyPaymentMethodTransfer == null) {
            PartyPaymentMethodDetail partyPaymentMethodDetail = partyPaymentMethod.getLastDetail();
            String partyPaymentMethodName = partyPaymentMethodDetail.getPartyPaymentMethodName();
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, partyPaymentMethodDetail.getParty());
            String description = partyPaymentMethodDetail.getDescription();
            PaymentMethodTransfer paymentMethodTransfer = paymentControl.getPaymentMethodTransfer(userVisit, partyPaymentMethodDetail.getPaymentMethod());
            String paymentMethodTypeName = paymentMethodTransfer.getPaymentMethodType().getPaymentMethodTypeName();
            Boolean deleteWhenUnused = partyPaymentMethodDetail.getDeleteWhenUnused();
            Boolean isDefault = partyPaymentMethodDetail.getIsDefault();
            Integer sortOrder = partyPaymentMethodDetail.getSortOrder();
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
            
            if(paymentMethodTypeName.equals(PaymentConstants.PaymentMethodType_CREDIT_CARD)) {
                PartyPaymentMethodCreditCard partyPaymentMethodCreditCard = paymentControl.getPartyPaymentMethodCreditCard(partyPaymentMethod);
                
                if(partyPaymentMethodCreditCard != null) {
                    PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode = paymentControl.getPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod);
                    
                    if(includeNumber || maskNumberAndSecurityCode) {
                        String decodedNumber = paymentControl.decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard);

                        if(decodedNumber != null) {
                            number = includeNumber? paymentControl.decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard): StringUtils.getInstance().mask(decodedNumber, 'X', 4);
                        }
                    }
                    
                    expirationMonth = partyPaymentMethodCreditCard.getExpirationMonth();
                    expirationYear = partyPaymentMethodCreditCard.getExpirationYear();
                    PersonalTitle personalTitle = partyPaymentMethodCreditCard.getPersonalTitle();
                    personalTitleTransfer = personalTitle == null? null: partyControl.getPersonalTitleTransfer(userVisit, personalTitle);
                    firstName = partyPaymentMethodCreditCard.getFirstName();
                    middleName = partyPaymentMethodCreditCard.getMiddleName();
                    lastName = partyPaymentMethodCreditCard.getLastName();
                    NameSuffix nameSuffix = partyPaymentMethodCreditCard.getNameSuffix();
                    nameSuffixTransfer = nameSuffix == null? null: partyControl.getNameSuffixTransfer(userVisit, nameSuffix);
                    name = partyPaymentMethodCreditCard.getName();
                    PartyContactMechanism billingPartyContactMechanism = partyPaymentMethodCreditCard.getBillingPartyContactMechanism();
                    billingPartyContactMechanismTransfer = billingPartyContactMechanism == null? null: contactControl.getPartyContactMechanismTransfer(userVisit, billingPartyContactMechanism);
                    issuerName = partyPaymentMethodCreditCard.getIssuerName();
                    PartyContactMechanism issuerPartyContactMechanism = partyPaymentMethodCreditCard.getIssuerPartyContactMechanism();
                    issuerPartyContactMechanismTransfer = issuerPartyContactMechanism == null? null: contactControl.getPartyContactMechanismTransfer(userVisit, issuerPartyContactMechanism);
                    
                    if(partyPaymentMethodCreditCardSecurityCode != null) {
                        if(includeSecurityCode || maskNumberAndSecurityCode) {
                            String decodedSecurityCode = paymentControl.decodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(partyPaymentMethodCreditCardSecurityCode);

                            if(decodedSecurityCode != null) {
                                securityCode = includeNumber? paymentControl.decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard): StringUtils.getInstance().mask(decodedSecurityCode, 'X');
                            }
                        }
                    }
                    
                    entityInstance = coreControl.getEntityInstanceByBasePK(partyPaymentMethod.getPrimaryKey());
                    partyPaymentMethodStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                            Workflow_CUSTOMER_CREDIT_CARD_PAYMENT_METHOD, entityInstance);
                }
            }
            
            partyPaymentMethodTransfer = new PartyPaymentMethodTransfer(partyPaymentMethodName, partyTransfer, description,
                    paymentMethodTransfer, deleteWhenUnused, isDefault, sortOrder,
                    partyPaymentMethodStatusTransfer, number, expirationMonth, expirationYear, personalTitleTransfer, firstName,
                    middleName, lastName, nameSuffixTransfer, name, billingPartyContactMechanismTransfer, issuerName,
                    issuerPartyContactMechanismTransfer, securityCode);
            put(partyPaymentMethod, partyPaymentMethodTransfer);
            
            if(includePartyPaymentMethodContactMechanisms) {
                partyPaymentMethodTransfer.setPartyPaymentMethodContactMechanisms(new ListWrapper<>(paymentControl.getPartyPaymentMethodContactMechanismTransfersByPartyPaymentMethod(userVisit, partyPaymentMethod)));
            }

            if(includeComments) {
                setupComments(partyPaymentMethod, entityInstance, partyPaymentMethodTransfer, CommentConstants.CommentType_PARTY_PAYMENT_METHOD);
            }
        }
        
        return partyPaymentMethodTransfer;
    }
    
}
