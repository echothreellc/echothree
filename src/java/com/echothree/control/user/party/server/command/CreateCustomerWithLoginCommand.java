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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateCustomerWithLoginForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.logic.ContactEmailAddressLogic;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.party.server.logic.PasswordStringPolicyLogic;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class CreateCustomerWithLoginCommand
        extends BaseSimpleCommand<CreateCustomerWithLoginForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> customerFormFieldDefinitions;
    private final static List<FieldDefinition> otherFormFieldDefinitions;
    
    static {
        // customerFormFieldDefinitions differs from otherFormFieldDefinitions in that when the PartyType
        // executing this command is null, or equal to CUSTOMER, FirstName and LastName are required fields.
        // For all other PartyTypes, that requirement is relaxed.
        customerFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ArGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, true, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, true, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddress", FieldType.EMAIL_ADDRESS, true, null, null),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("Password2", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Answer", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("CustomerStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerCreditStatusChoice", FieldType.ENTITY_NAME, false, null, null)
                ));
        
        otherFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ArGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddress", FieldType.EMAIL_ADDRESS, true, null, null),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("Password2", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Answer", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("CustomerStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerCreditStatusChoice", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCustomerWithLoginCommand */
    public CreateCustomerWithLoginCommand() {
        super(null, null, false);
    }
    
    @Override
    protected ValidationResult validate() {
        var partyTypeName = getPartyTypeName();
        var FORM_FIELD_DEFINITIONS = partyTypeName == null || partyTypeName.equals(PartyTypes.CUSTOMER.name())? customerFormFieldDefinitions: otherFormFieldDefinitions;
        var validator = new Validator(this);
        var validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var result = PartyResultFactory.getCreateCustomerWithLoginResult();
        var customerControl = Session.getModelController(CustomerControl.class);
        Customer customer = null;
        var username = form.getUsername();
        var userLogin = userControl.getUserLoginByUsername(username);

        if(userLogin == null) {
            var password1 = form.getPassword1();
            var password2 = form.getPassword2();

            if(password1.equals(password2)) {
                var partyControl = Session.getModelController(PartyControl.class);
                var partyType = partyControl.getPartyTypeByName(PartyTypes.CUSTOMER.name());
                var partyTypePasswordStringPolicy = PasswordStringPolicyLogic.getInstance().checkStringPassword(session,
                        getUserVisit(), this, partyType, null, null, password1);

                if(!hasExecutionErrors()) {
                    var customerTypeName = form.getCustomerTypeName();
                    var customerType = customerTypeName == null ? customerControl.getDefaultCustomerType() : customerControl.getCustomerTypeByName(customerTypeName);

                    if(customerType != null) {
                        var cancellationPolicyName = form.getCancellationPolicyName();
                        CancellationPolicy cancellationPolicy = null;

                        if(cancellationPolicyName != null) {
                            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                            var returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.CUSTOMER_CANCELLATION.name());

                            cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, cancellationPolicyName);
                        }

                        if(cancellationPolicyName == null || cancellationPolicy != null) {
                            var returnPolicyName = form.getReturnPolicyName();
                            ReturnPolicy returnPolicy = null;

                            if(returnPolicyName != null) {
                                var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                                var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.CUSTOMER_RETURN.name());

                                returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                            }

                            if(returnPolicyName == null || returnPolicy != null) {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                var arGlAccountName = form.getArGlAccountName();
                                var arGlAccount = arGlAccountName == null ? null : accountingControl.getGlAccountByName(arGlAccountName);

                                if(arGlAccountName == null || arGlAccount != null) {
                                    var glAccountCategoryName = arGlAccount == null ? null
                                            : arGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                    if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                                        var termControl = Session.getModelController(TermControl.class);
                                        var customerTypeDetail = customerType.getLastDetail();
                                        var term = customerTypeDetail.getDefaultTerm();

                                        if(term == null) {
                                            term = termControl.getDefaultTerm();
                                        }

                                        if(term != null) {
                                            var initialOfferName = form.getInitialOfferName();
                                            var initialUseName = form.getInitialUseName();
                                            var initialSourceName = form.getInitialSourceName();
                                            OfferUse initialOfferUse = null;
                                            var invalidInitialOfferOrSourceSpecification = false;

                                            if(initialOfferName != null && initialUseName != null && initialSourceName == null) {
                                                var offerControl = Session.getModelController(OfferControl.class);
                                                var initialOffer = offerControl.getOfferByName(initialOfferName);

                                                if(initialOffer != null) {
                                                    var useControl = Session.getModelController(UseControl.class);
                                                    var initialUse = useControl.getUseByName(initialUseName);

                                                    if(initialUse != null) {
                                                        var offerUseControl = Session.getModelController(OfferUseControl.class);
                                                        initialOfferUse = offerUseControl.getOfferUse(initialOffer, initialUse);

                                                        if(initialOfferUse == null) {
                                                            addExecutionError(ExecutionErrors.UnknownInitialOfferUse.name());
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownInitialUseName.name(), initialUseName);
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownInitialOfferName.name(), initialOfferName);
                                                }
                                            } else {
                                                var sourceControl = Session.getModelController(SourceControl.class);

                                                if(initialOfferName == null && initialUseName == null && initialSourceName != null) {
                                                    var source = sourceControl.getSourceByName(initialSourceName);

                                                    if(source != null) {
                                                        initialOfferUse = source.getLastDetail().getOfferUse();
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownInitialSourceName.name(), initialSourceName);
                                                    }
                                                } else {
                                                    initialOfferUse = getUserVisit().getOfferUse();

                                                    if(initialOfferUse == null) {
                                                        // If all three parameters are null, then try to get the default Source and use its OfferUse.
                                                        var source = sourceControl.getDefaultSource();

                                                        if(source != null) {
                                                            initialOfferUse = source.getLastDetail().getOfferUse();
                                                        } else {
                                                            addExecutionError(ExecutionErrors.InvalidInitialOfferOrSourceSpecification.name());
                                                            invalidInitialOfferOrSourceSpecification = true;
                                                        }
                                                    }
                                                }
                                            }

                                            if(initialOfferUse != null) {
                                                var preferredLanguageIsoName = form.getPreferredLanguageIsoName();
                                                var preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                                                if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                                                    var preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                                                    var preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                                                    if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                                        var preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                                                        var preferredDateTimeFormat = preferredDateTimeFormatName == null ? null
                                                                : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                                                        if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                                            var preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                                                            Currency preferredCurrency;

                                                            if(preferredCurrencyIsoName == null) {
                                                                preferredCurrency = null;
                                                            } else {
                                                                preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                                            }

                                                            if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                                                var recoveryQuestionName = form.getRecoveryQuestionName();
                                                                var recoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);

                                                                if(recoveryQuestion != null) {
                                                                    var workflowControl = Session.getModelController(WorkflowControl.class);
                                                                    var soundex = new Soundex();
                                                                    BasePK createdBy = getPartyPK();
                                                                    var personalTitleId = form.getPersonalTitleId();
                                                                    var personalTitle = personalTitleId == null ? null
                                                                            : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                                                                    var firstName = form.getFirstName();
                                                                    var middleName = form.getMiddleName();
                                                                    var lastName = form.getLastName();
                                                                    var nameSuffixId = form.getNameSuffixId();
                                                                    var nameSuffix = nameSuffixId == null ? null
                                                                            : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                                                    var name = form.getName();
                                                                    var emailAddress = form.getEmailAddress();
                                                                    var allowSolicitation = Boolean.valueOf(form.getAllowSolicitation());

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

                                                                    var party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat,
                                                                            createdBy);

                                                                    if(createdBy == null) {
                                                                        createdBy = party.getPrimaryKey();
                                                                    }
                                                                    if(personalTitle != null || firstName != null || middleName != null || lastName != null || nameSuffix != null) {
                                                                        partyControl.createPerson(party, personalTitle, firstName, firstNameSdx, middleName,
                                                                                middleNameSdx, lastName, lastNameSdx, nameSuffix, createdBy);
                                                                    }

                                                                    if(name != null) {
                                                                        partyControl.createPartyGroup(party, name, createdBy);
                                                                    }

                                                                    customer = customerControl.createCustomer(party, customerType, initialOfferUse, cancellationPolicy, returnPolicy, arGlAccount,
                                                                            customerTypeDetail.getDefaultHoldUntilComplete(), customerTypeDetail.getDefaultAllowBackorders(),
                                                                            customerTypeDetail.getDefaultAllowSubstitutions(), customerTypeDetail.getDefaultAllowCombiningShipments(),
                                                                            customerTypeDetail.getDefaultRequireReference(), customerTypeDetail.getDefaultAllowReferenceDuplicates(),
                                                                            customerTypeDetail.getDefaultReferenceValidationPattern(), createdBy);
                                                                    userControl.createUserLogin(party, username, createdBy);

                                                                    ContactEmailAddressLogic.getInstance().createContactEmailAddress(party,
                                                                            emailAddress, allowSolicitation, null,
                                                                            ContactMechanismPurposes.PRIMARY_EMAIL.name(),
                                                                            createdBy);

                                                                    termControl.createPartyTerm(party, term, customerTypeDetail.getDefaultTaxable(), createdBy);

                                                                    for(var customerTypeCreditLimit : termControl.getCustomerTypeCreditLimitsByCustomerType(customerType)) {
                                                                        var currency = customerTypeCreditLimit.getCurrency();
                                                                        var creditLimit = customerTypeCreditLimit.getCreditLimit();
                                                                        var potentialCreditLimit = customerTypeCreditLimit.getPotentialCreditLimit();

                                                                        termControl.createPartyCreditLimit(party, currency, creditLimit, potentialCreditLimit, createdBy);
                                                                    }

                                                                    var userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_STRING);
                                                                    var userLoginPassword = userControl.createUserLoginPassword(party, userLoginPasswordType, createdBy);
                                                                    userControl.createUserLoginPasswordString(userLoginPassword, password1, session.START_TIME_LONG, false, createdBy);

                                                                    if(partyTypePasswordStringPolicy != null && partyTypePasswordStringPolicy.getLastDetail().getForceChangeAfterCreate()) {
                                                                        var userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                                                                        userLoginStatus.setForceChange(true);
                                                                    }

                                                                    var answer = form.getAnswer();
                                                                    userControl.createRecoveryAnswer(party, recoveryQuestion, answer, createdBy);

                                                                    // TODO: error checking for unknown customerStatusChoice
                                                                    var customerStatusChoice = form.getCustomerStatusChoice();
                                                                    var customerStatusWorkflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                                                    var customerStatusWorkflowEntrance = customerStatusChoice == null ? customerTypeDetail.getDefaultCustomerStatus()
                                                                            : workflowControl.getWorkflowEntranceByName(customerStatusWorkflow, customerStatusChoice);

                                                                    if(customerStatusWorkflowEntrance == null) {
                                                                        customerStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerStatusWorkflow);
                                                                    }

                                                                    // TODO: error checking for unknown customerCreditStatusChoice
                                                                    var customerCreditStatusChoice = form.getCustomerCreditStatusChoice();
                                                                    var customerCreditStatusWorkflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                                                    var customerCreditStatusWorkflowEntrance = customerCreditStatusChoice == null
                                                                            ? customerTypeDetail.getDefaultCustomerCreditStatus()
                                                                            : workflowControl.getWorkflowEntranceByName(customerCreditStatusWorkflow, customerCreditStatusChoice);

                                                                    if(customerCreditStatusWorkflowEntrance == null) {
                                                                        customerCreditStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerCreditStatusWorkflow);
                                                                    }

                                                                    var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                                                                    var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
                                                                    workflowControl.addEntityToWorkflow(customerStatusWorkflowEntrance, entityInstance, null, null, createdBy);
                                                                    workflowControl.addEntityToWorkflow(customerCreditStatusWorkflowEntrance, entityInstance, null, null, createdBy);

                                                                    ContactListLogic.getInstance().setupInitialContactLists(this, party, createdBy);
                                                                    
                                                                    // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                                                                    PartyChainLogic.getInstance().createPartyWelcomeChainInstance(null, party, createdBy);
                                                                } else {
                                                                    addExecutionError(ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), preferredCurrencyIsoName);
                                                            }
                                                        } else {
                                                            addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), preferredDateTimeFormatName);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), preferredJavaTimeZoneName);
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), preferredLanguageIsoName);
                                                }
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownDefaultTerm.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.InvalidGlAccountCategory.name(), glAccountCategoryName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownArGlAccountName.name(), arGlAccountName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                        }
                    } else {
                        if(customerTypeName != null) {
                            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDefaultCustomerType.name());
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.MismatchedPasswords.name());
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateUsername.name());

            var party = userLogin.getParty();
            customer = customerControl.getCustomer(party);
        }

        if(customer != null) {
            var party = customer.getParty();

            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setCustomerName(customer.getCustomerName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }

        return result;
    }
    
}
