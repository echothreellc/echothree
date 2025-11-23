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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.edit.CustomerEdit;
import com.echothree.control.user.customer.common.edit.CustomerEditFactory;
import com.echothree.control.user.customer.common.form.EditCustomerForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.EditCustomerResult;
import com.echothree.control.user.customer.common.spec.CustomerSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditCustomerCommand
        extends BaseAbstractEditCommand<CustomerSpec, CustomerEdit, EditCustomerResult, Party, Party> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ArGlAccountName", FieldType.ENTITY_NAME, false, null, null),
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
                new FieldDefinition("HoldUntilComplete", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowBackorders", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowSubstitutions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowCombiningShipments", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RequireReference", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowReferenceDuplicates", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("ReferenceValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditCustomerCommand */
    public EditCustomerCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCustomerResult getResult() {
        return CustomerResultFactory.getEditCustomerResult();
    }

    @Override
    public CustomerEdit getEdit() {
        return CustomerEditFactory.getCustomerEdit();
    }

    @Override
    public Party getEntity(EditCustomerResult result) {
        var customerControl = Session.getModelController(CustomerControl.class);
        Customer customer;
        var customerName = spec.getCustomerName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            customer = customerControl.getCustomerByName(customerName);
        } else { // EditMode.UPDATE
            customer = customerControl.getCustomerByNameForUpdate(customerName);
        }

        if(customer != null) {
            result.setCustomer(customerControl.getCustomerTransfer(getUserVisit(), customer));
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerName.name(), customerName);
        }

        return customer.getParty();
    }

    @Override
    public Party getLockEntity(Party party) {
        return party;
    }

    @Override
    public void fillInResult(EditCustomerResult result, Party party) {
        var customerControl = Session.getModelController(CustomerControl.class);

        result.setCustomer(customerControl.getCustomerTransfer(getUserVisit(), party));
    }

    @Override
    public void doLock(CustomerEdit edit, Party party) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var customer = customerControl.getCustomer(party);
        var partyDetail = party.getLastDetail();
        var partyGroup = partyControl.getPartyGroup(party);
        var arGlAccount = customer.getArGlAccount();
        var cancellationPolicy = customer.getCancellationPolicy();
        var returnPolicy = customer.getReturnPolicy();
        var preferredLanguage = partyDetail.getPreferredLanguage();
        var preferredCurrency = partyDetail.getPreferredCurrency();
        var preferredTimeZone = partyDetail.getPreferredTimeZone();
        var dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
        var person = partyControl.getPerson(party);
        var personalTitle = person == null ? null : person.getPersonalTitle();
        var nameSuffix = person == null ? null : person.getNameSuffix();

        edit.setCustomerTypeName(customer.getCustomerType().getLastDetail().getCustomerTypeName());
        edit.setPersonalTitleId(personalTitle == null ? null : personalTitle.getPrimaryKey().getEntityId().toString());
        edit.setFirstName(person == null ? null : person.getFirstName());
        edit.setMiddleName(person == null ? null : person.getMiddleName());
        edit.setLastName(person == null ? null : person.getLastName());
        edit.setNameSuffixId(nameSuffix == null ? null : nameSuffix.getPrimaryKey().getEntityId().toString());
        edit.setName(partyGroup == null ? null : partyGroup.getName());
        edit.setPreferredLanguageIsoName(preferredLanguage == null ? null : preferredLanguage.getLanguageIsoName());
        edit.setPreferredCurrencyIsoName(preferredCurrency == null ? null : preferredCurrency.getCurrencyIsoName());
        edit.setPreferredJavaTimeZoneName(preferredTimeZone == null ? null : preferredTimeZone.getLastDetail().getJavaTimeZoneName());
        edit.setPreferredDateTimeFormatName(dateTimeFormat == null ? null : dateTimeFormat.getLastDetail().getDateTimeFormatName());
        edit.setArGlAccountName(arGlAccount == null ? null : arGlAccount.getLastDetail().getGlAccountName());
        edit.setCancellationPolicyName(cancellationPolicy == null ? null : cancellationPolicy.getLastDetail().getCancellationPolicyName());
        edit.setReturnPolicyName(returnPolicy == null ? null : returnPolicy.getLastDetail().getReturnPolicyName());
        edit.setHoldUntilComplete(customer.getHoldUntilComplete().toString());
        edit.setAllowBackorders(customer.getAllowBackorders().toString());
        edit.setAllowSubstitutions(customer.getAllowSubstitutions().toString());
        edit.setAllowCombiningShipments(customer.getAllowCombiningShipments().toString());
        edit.setRequireReference(customer.getRequireReference().toString());
        edit.setAllowReferenceDuplicates(customer.getAllowReferenceDuplicates().toString());
        edit.setReferenceValidationPattern(customer.getReferenceValidationPattern());
    }

    CustomerType customerType;
    CancellationPolicy cancellationPolicy;
    ReturnPolicy returnPolicy;
    GlAccount arGlAccount;
    Language preferredLanguage;
    TimeZone preferredTimeZone;
    DateTimeFormat preferredDateTimeFormat;
    Currency preferredCurrency;

    @Override
    public void canUpdate(Party party) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var customerTypeName = edit.getCustomerTypeName();

        customerType = customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType != null) {
            var cancellationPolicyName = edit.getCancellationPolicyName();

            if(cancellationPolicyName != null) {
                var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.CUSTOMER_CANCELLATION.name());

                cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
            }

            if(cancellationPolicyName == null || cancellationPolicy != null) {
                var returnPolicyName = edit.getReturnPolicyName();

                if(returnPolicyName != null) {
                    var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                    var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.CUSTOMER_RETURN.name());

                    returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                }

                if(returnPolicyName == null || returnPolicy != null) {
                    var accountingControl = Session.getModelController(AccountingControl.class);
                    var arGlAccountName = edit.getArGlAccountName();

                    arGlAccount = arGlAccountName == null ? null : accountingControl.getGlAccountByName(arGlAccountName);

                    if(arGlAccountName == null || arGlAccount != null) {
                        var glAccountCategoryName = arGlAccount == null ? null : arGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                        if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                            var preferredLanguageIsoName = edit.getPreferredLanguageIsoName();

                            preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                            if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                                var preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();

                                preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                                if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                    var preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();

                                    preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                                    if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                        var preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();

                                        if(preferredCurrencyIsoName == null) {
                                            preferredCurrency = null;
                                        } else {
                                            preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                        }

                                        if(preferredCurrencyIsoName != null && (preferredCurrency == null)) {
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
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
    }

    @Override
    public void doUpdate(Party party) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var soundex = new Soundex();
        var partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
        var customer = customerControl.getCustomerForUpdate(party);
        var customerValue = customerControl.getCustomerValue(customer);
        var partyGroup = partyControl.getPartyGroupForUpdate(party);
        var person = partyControl.getPersonForUpdate(party);
        var personalTitleId = edit.getPersonalTitleId();
        var personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
        var firstName = edit.getFirstName();
        var firstNameSdx = soundex.encode(firstName);
        var middleName = edit.getMiddleName();
        var middleNameSdx = middleName == null ? null : soundex.encode(middleName);
        var lastName = edit.getLastName();
        var lastNameSdx = soundex.encode(lastName);
        var nameSuffixId = edit.getNameSuffixId();
        var nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
        var name = edit.getName();
        var updatedBy = getPartyPK();

        customerValue.setCustomerTypePK(customerType.getPrimaryKey());
        customerValue.setArGlAccountPK(arGlAccount == null ? null : arGlAccount.getPrimaryKey());
        customerValue.setCancellationPolicyPK(cancellationPolicy == null ? null : cancellationPolicy.getPrimaryKey());
        customerValue.setReturnPolicyPK(returnPolicy == null ? null : returnPolicy.getPrimaryKey());
        customerValue.setHoldUntilComplete(Boolean.valueOf(edit.getHoldUntilComplete()));
        customerValue.setAllowBackorders(Boolean.valueOf(edit.getAllowBackorders()));
        customerValue.setAllowSubstitutions(Boolean.valueOf(edit.getAllowSubstitutions()));
        customerValue.setAllowCombiningShipments(Boolean.valueOf(edit.getAllowCombiningShipments()));
        customerValue.setRequireReference(Boolean.valueOf(edit.getRequireReference()));
        customerValue.setAllowReferenceDuplicates(Boolean.valueOf(edit.getAllowReferenceDuplicates()));
        customerValue.setReferenceValidationPattern(edit.getReferenceValidationPattern());

        partyDetailValue.setPreferredLanguagePK(preferredLanguage == null ? null : preferredLanguage.getPrimaryKey());
        partyDetailValue.setPreferredTimeZonePK(preferredTimeZone == null ? null : preferredTimeZone.getPrimaryKey());
        partyDetailValue.setPreferredDateTimeFormatPK(preferredDateTimeFormat == null ? null : preferredDateTimeFormat.getPrimaryKey());
        partyDetailValue.setPreferredCurrencyPK(preferredCurrency == null ? null : preferredCurrency.getPrimaryKey());

        if(name != null) {
            if(partyGroup != null) {
                var partyGroupValue = partyControl.getPartyGroupValue(partyGroup);

                partyGroupValue.setName(name);
                partyControl.updatePartyGroupFromValue(partyGroupValue, updatedBy);
            } else {
                partyControl.createPartyGroup(party, name, updatedBy);
            }
        } else {
            if(partyGroup != null) {
                partyControl.deletePartyGroup(partyGroup, updatedBy);
            }
        }

        if(personalTitle != null || firstName != null || middleName != null || lastName != null || nameSuffix != null) {
            if(person != null) {
                var personValue = partyControl.getPersonValue(person);

                personValue.setPersonalTitlePK(personalTitle == null ? null : personalTitle.getPrimaryKey());
                personValue.setFirstName(firstName);
                personValue.setFirstNameSdx(firstNameSdx);
                personValue.setMiddleName(middleName);
                personValue.setMiddleNameSdx(middleNameSdx);
                personValue.setLastName(lastName);
                personValue.setLastNameSdx(lastNameSdx);
                personValue.setNameSuffixPK(nameSuffix == null ? null : nameSuffix.getPrimaryKey());
                partyControl.updatePersonFromValue(personValue, updatedBy);
            } else {
                partyControl.createPerson(party, personalTitle, firstName, firstNameSdx, middleName, middleNameSdx,
                        lastName, lastNameSdx, nameSuffix, updatedBy);
            }
        } else {
            if(person != null) {
                partyControl.deletePerson(person, updatedBy);
            }
        }

        customerControl.updateCustomerFromValue(customerValue, updatedBy);
        partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
    }

}
