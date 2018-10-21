// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.customer.remote.edit.CustomerEdit;
import com.echothree.control.user.customer.remote.edit.CustomerEditFactory;
import com.echothree.control.user.customer.remote.form.EditCustomerForm;
import com.echothree.control.user.customer.remote.result.CustomerResultFactory;
import com.echothree.control.user.customer.remote.result.EditCustomerResult;
import com.echothree.control.user.customer.remote.spec.CustomerSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.value.CustomerValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyGroupValue;
import com.echothree.model.data.party.server.value.PersonValue;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class EditCustomerCommand
        extends BaseEditCommand<CustomerSpec, CustomerEdit> {
    
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
    public EditCustomerCommand(UserVisitPK userVisitPK, EditCustomerForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        EditCustomerResult result = CustomerResultFactory.getEditCustomerResult();
        String customerName = spec.getCustomerName();
        Customer customer = customerControl.getCustomerByNameForUpdate(customerName);

        if(customer != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            Party party = customer.getParty();

            if(editMode.equals(EditMode.LOCK)) {
                result.setCustomer(customerControl.getCustomerTransfer(getUserVisit(), customer));

                if(lockEntity(party)) {
                    CustomerEdit edit = CustomerEditFactory.getCustomerEdit();
                    PartyDetail partyDetail = party.getLastDetail();
                    PartyGroup partyGroup = partyControl.getPartyGroup(party);
                    GlAccount arGlAccount = customer.getArGlAccount();
                    CancellationPolicy cancellationPolicy = customer.getCancellationPolicy();
                    ReturnPolicy returnPolicy = customer.getReturnPolicy();
                    Language preferredLanguage = partyDetail.getPreferredLanguage();
                    Currency preferredCurrency = partyDetail.getPreferredCurrency();
                    TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
                    DateTimeFormat dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
                    Person person = partyControl.getPerson(party);
                    PersonalTitle personalTitle = person == null ? null : person.getPersonalTitle();
                    NameSuffix nameSuffix = person == null ? null : person.getNameSuffix();

                    result.setEdit(edit);
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
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }

                result.setEntityLock(getEntityLockTransfer(party));
            } else {
                if(editMode.equals(EditMode.ABANDON)) {
                    unlockEntity(party);
                } else {
                    if(editMode.equals(EditMode.UPDATE)) {
                        CustomerValue customerValue = customerControl.getCustomerValue(customer);

                        if(lockEntityForUpdate(party)) {
                            String customerTypeName = edit.getCustomerTypeName();
                            CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);

                            if(customerType != null) {
                                String cancellationPolicyName = edit.getCancellationPolicyName();
                                CancellationPolicy cancellationPolicy = null;

                                if(cancellationPolicyName != null) {
                                    CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                                    CancellationKind returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);

                                    cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, cancellationPolicyName);
                                }

                                if(cancellationPolicyName == null || cancellationPolicy != null) {
                                    String returnPolicyName = edit.getReturnPolicyName();
                                    ReturnPolicy returnPolicy = null;

                                    if(returnPolicyName != null) {
                                        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                                        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);

                                        returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                                    }

                                    if(returnPolicyName == null || returnPolicy != null) {
                                        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                        String arGlAccountName = edit.getArGlAccountName();
                                        GlAccount arGlAccount = arGlAccountName == null ? null : accountingControl.getGlAccountByName(arGlAccountName);

                                        if(arGlAccountName == null || arGlAccount != null) {
                                            String glAccountCategoryName = arGlAccount == null ? null
                                                    : arGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                            if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                                                String preferredLanguageIsoName = edit.getPreferredLanguageIsoName();
                                                Language preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                                                if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                                                    String preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();
                                                    TimeZone preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                                                    if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                                        String preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();
                                                        DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                                                        if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                                            String preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();
                                                            Currency preferredCurrency;

                                                            if(preferredCurrencyIsoName == null) {
                                                                preferredCurrency = null;
                                                            } else {
                                                                preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                                            }

                                                            if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                                                try {
                                                                    Soundex soundex = new Soundex();
                                                                    PartyDetailValue partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
                                                                    PartyGroup partyGroup = partyControl.getPartyGroupForUpdate(party);
                                                                    Person person = partyControl.getPersonForUpdate(party);
                                                                    String personalTitleId = edit.getPersonalTitleId();
                                                                    PersonalTitle personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId,
                                                                            EntityPermission.READ_ONLY);
                                                                    String firstName = edit.getFirstName();
                                                                    String firstNameSdx = soundex.encode(firstName);
                                                                    String middleName = edit.getMiddleName();
                                                                    String middleNameSdx = middleName == null ? null : soundex.encode(middleName);
                                                                    String lastName = edit.getLastName();
                                                                    String lastNameSdx = soundex.encode(lastName);
                                                                    String nameSuffixId = edit.getNameSuffixId();
                                                                    NameSuffix nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                                                    String name = edit.getName();
                                                                    PartyPK updatedBy = getPartyPK();

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
                                                                            PartyGroupValue partyGroupValue = partyControl.getPartyGroupValue(partyGroup);

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
                                                                            PersonValue personValue = partyControl.getPersonValue(person);

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
                                                                } finally {
                                                                    unlockEntity(party);
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
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }

                        if(hasExecutionErrors()) {
                            result.setCustomer(customerControl.getCustomerTransfer(getUserVisit(), customer));
                            result.setEntityLock(getEntityLockTransfer(party));
                        }
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerName.name(), customerName);
        }

        return result;
    }
    
}
