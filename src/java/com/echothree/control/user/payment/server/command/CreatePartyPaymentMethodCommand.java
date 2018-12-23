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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.CreatePartyPaymentMethodForm;
import com.echothree.control.user.payment.common.result.CreatePartyPaymentMethodResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.payment.common.PaymentConstants;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.payment.server.logic.PartyPaymentMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodContactMechanism;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypePartyType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class CreatePartyPaymentMethodCommand
        extends BaseSimpleCommand<CreatePartyPaymentMethodForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_CUSTOMER, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyPaymentMethod.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DeleteWhenUnused", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Number", FieldType.STRING, false, 1L, 20L), // RegExp Validated
                new FieldDefinition("SecurityCode", FieldType.STRING, false, 1L, 10L), // RegExp Validated
                new FieldDefinition("ExpirationMonth", FieldType.CREDIT_CARD_MONTH, false, null, null),
                new FieldDefinition("ExpirationYear", FieldType.CREDIT_CARD_YEAR, false, null, null),
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("BillingContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IssuerName", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("IssuerContactMechanismName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyPaymentMethodCommand */
    public CreatePartyPaymentMethodCommand(UserVisitPK userVisitPK, CreatePartyPaymentMethodForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    private void setupWorkflows(final PaymentControl paymentControl, final PaymentMethodType paymentMethodType,
            final PartyType partyType, final PartyPaymentMethodContactMechanism billingPartyPaymentMethodContactMechanism, final PartyPaymentMethod partyPaymentMethod,
            final PartyPK createdBy) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        PaymentMethodTypePartyType paymentMethodTypePartyType = paymentControl.getPaymentMethodTypePartyType(paymentMethodType, partyType);
        
        if(paymentMethodTypePartyType != null) {
            if(billingPartyPaymentMethodContactMechanism != null) {
                Workflow contactMechanismWorkflow = paymentMethodTypePartyType.getPartyContactMechanismWorkflow();

                if(contactMechanismWorkflow != null) {
                    EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(billingPartyPaymentMethodContactMechanism.getPrimaryKey());

                    if(!workflowControl.isEntityInWorkflow(contactMechanismWorkflow, entityInstance)) {
                        workflowControl.addEntityToWorkflow(contactMechanismWorkflow, entityInstance, null, null, createdBy);
                    }
                }
            }

            Workflow partyPaymentMethodWorkflow = paymentMethodTypePartyType.getPartyPaymentMethodWorkflow();
            
            if(partyPaymentMethodWorkflow != null) {
                EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(partyPaymentMethod.getPrimaryKey());
                
                workflowControl.addEntityToWorkflow(partyPaymentMethodWorkflow, entityInstance, null, null, createdBy);
            }
        }
    }
    
    private PartyPaymentMethodContactMechanism setupPartyPaymentMethodContactMechanism(final ContactControl contactControl,
            final PaymentControl paymentControl, final PartyContactMechanism partyContactMechanism,
            final PartyPaymentMethod partyPaymentMethod, final PartyPK createdBy) {
        ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(ContactMechanismPurposes.PHYSICAL_BILLING.name());
        PartyContactMechanismPurpose partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurpose(partyContactMechanism,
                contactMechanismPurpose);
        
        if(partyContactMechanismPurpose == null) {
            partyContactMechanismPurpose = contactControl.createPartyContactMechanismPurpose(partyContactMechanism,
                    contactMechanismPurpose, Boolean.FALSE, 1, createdBy);
        }
        
        return paymentControl.createPartyPaymentMethodContactMechanism(partyPaymentMethod, partyContactMechanismPurpose, createdBy);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        CreatePartyPaymentMethodResult result = PaymentResultFactory.getCreatePartyPaymentMethodResult();
        Party party = getParty();
        String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

        // If the caller is a CUSTOMER, then they're the Party. If they're not, the PartyName parameter is
        // required, and we'll look them up.
        if(!partyTypeName.equals(PartyConstants.PartyType_CUSTOMER)) {
            String partyName = form.getPartyName();

            if(partyName == null) {
                addExecutionError(ExecutionErrors.PartyNameRequired.name());
            } else {
                party = partyControl.getPartyByName(partyName);

                if(party == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
        }

        if(!hasExecutionErrors()) {
            PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
            String paymentMethodName = form.getPaymentMethodName();
            PaymentMethod paymentMethod = paymentControl.getPaymentMethodByName(paymentMethodName);

            if(paymentMethod != null) {
                PartyPaymentMethodLogic.getInstance().checkPartyPaymentMethod(session, getUserVisit(), this, party, paymentMethod, form);

                if(!hasExecutionErrors()) {
                    PaymentMethodType paymentMethodType = paymentMethod.getLastDetail().getPaymentMethodType();
                    String paymentMethodTypeName = paymentMethodType.getPaymentMethodTypeName();

                    if(paymentMethodTypeName.equals(PaymentConstants.PaymentMethodType_CREDIT_CARD)) {
                        ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                        Soundex soundex = new Soundex();
                        String personalTitleId = form.getPersonalTitleId();
                        PersonalTitle personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                        String firstName = form.getFirstName();
                        String middleName = form.getMiddleName();
                        String lastName = form.getLastName();
                        String nameSuffixId = form.getNameSuffixId();
                        NameSuffix nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                        String name = form.getName();
                        String number = form.getNumber();
                        String securityCode = form.getSecurityCode();
                        String strExpirationMonth = form.getExpirationMonth();
                        Integer expirationMonth = strExpirationMonth != null? Integer.valueOf(strExpirationMonth): null;
                        String strExpirationYear = form.getExpirationYear();
                        Integer expirationYear = strExpirationYear != null? Integer.valueOf(strExpirationYear): null;
                        String billingContactMechanismName = form.getBillingContactMechanismName();
                        ContactMechanism billingContactMechanism = billingContactMechanismName == null ? null : contactControl.getContactMechanismByName(billingContactMechanismName);
                        PartyContactMechanism billingPartyContactMechanism = billingContactMechanism == null? null: contactControl.getPartyContactMechanism(party, billingContactMechanism);
                        String issuerName = form.getIssuerName();
                        String issuerContactMechanismName = form.getIssuerContactMechanismName();
                        ContactMechanism issuerContactMechanism = issuerContactMechanismName == null ? null : contactControl.getContactMechanismByName(issuerContactMechanismName);
                        PartyContactMechanism issuerPartyContactMechanism = issuerContactMechanism == null? null: contactControl.getPartyContactMechanism(party, issuerContactMechanism);

                        String firstNameSdx;
                        try {
                            firstNameSdx = firstName == null ? null : soundex.encode(firstName);
                        } catch(IllegalArgumentException iae) {
                            firstNameSdx = null;
                        }

                        String middleNameSdx;
                        try {
                            middleNameSdx = middleName == null ? null : soundex.encode(middleName);
                        } catch(IllegalArgumentException iae) {
                            middleNameSdx = null;
                        }

                        String lastNameSdx;
                        try {
                            lastNameSdx = lastName == null ? null : soundex.encode(lastName);
                        } catch(IllegalArgumentException iae) {
                            lastNameSdx = null;
                        }

                        PartyPK createdBy = getPartyPK();
                        String description = form.getDescription();
                        Boolean deleteWhenUnused = Boolean.valueOf(form.getDeleteWhenUnused());
                        Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                        Integer sortOrder = Integer.valueOf(form.getSortOrder());

                        PartyPaymentMethod partyPaymentMethod = paymentControl.createPartyPaymentMethod(party, description,
                                paymentMethod, deleteWhenUnused, isDefault, sortOrder, createdBy);

                        paymentControl.createPartyPaymentMethodCreditCard(partyPaymentMethod, number, expirationMonth,
                                expirationYear, personalTitle, firstName, firstNameSdx, middleName, middleNameSdx, lastName,
                                lastNameSdx, nameSuffix, name, billingPartyContactMechanism, issuerName, issuerPartyContactMechanism,
                                createdBy);

                        paymentControl.createPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod, securityCode,
                                createdBy);

                        PartyPaymentMethodContactMechanism billingPartyPaymentMethodContactMechanism = billingPartyContactMechanism == null ? null
                                : setupPartyPaymentMethodContactMechanism(contactControl, paymentControl, billingPartyContactMechanism, partyPaymentMethod,
                                createdBy);

                        setupWorkflows(paymentControl, paymentMethodType, party.getLastDetail().getPartyType(),
                                billingPartyPaymentMethodContactMechanism, partyPaymentMethod, createdBy);

                        result.setEntityRef(partyPaymentMethod.getPrimaryKey().getEntityRef());
                        result.setPartyPaymentMethodName(partyPaymentMethod.getLastDetail().getPartyPaymentMethodName());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
            }
        }
        
        return result;
    }
    
}
