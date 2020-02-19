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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.VendorEdit;
import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.form.EditVendorForm;
import com.echothree.control.user.vendor.common.result.EditVendorResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.VendorSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.party.common.pk.PartyPK;
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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.value.VendorValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class EditVendorCommand
        extends BaseEditCommand<VendorSpec, VendorEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Vendor.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("VendorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MinimumPurchaseOrderLines", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumPurchaseOrderLines", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumPurchaseOrderAmount", FieldType.UNSIGNED_COST_LINE, false, null, null),
                new FieldDefinition("MaximumPurchaseOrderAmount", FieldType.UNSIGNED_COST_LINE, false, null, null),
                new FieldDefinition("UseItemPurchasingCategories", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultItemAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ApGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.STRING, false, null, null),
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
    
    /** Creates a new instance of EditVendorCommand */
    public EditVendorCommand(UserVisitPK userVisitPK, EditVendorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        String vendorName = spec.getVendorName();
        
        if(vendorName != null) {
            Vendor vendor = vendorControl.getVendorByNameForUpdate(vendorName);
            
            if(vendor != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                
                validator.setCurrency(partyControl.getPreferredCurrency(vendor.getParty()));
            }
        }
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        EditVendorResult result = VendorResultFactory.getEditVendorResult();
        String originalVendorName = spec.getVendorName();
        Vendor vendor = vendorControl.getVendorByNameForUpdate(originalVendorName);

        if(vendor != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            Party party = vendor.getParty();

            if(editMode.equals(EditMode.LOCK)) {
                result.setVendor(vendorControl.getVendorTransfer(getUserVisit(), vendor));

                if(lockEntity(party)) {
                    VendorEdit edit = VendorEditFactory.getVendorEdit();
                    AmountUtils amountUtils = AmountUtils.getInstance();
                    Currency vendorPreferredCurrency = getPreferredCurrency(party);
                    PartyDetail partyDetail = party.getLastDetail();
                    PartyGroup partyGroup = partyControl.getPartyGroup(party);
                    GlAccount apGlAccount = vendor.getApGlAccount();
                    CancellationPolicy cancellationPolicy = vendor.getCancellationPolicy();
                    ReturnPolicy returnPolicy = vendor.getReturnPolicy();
                    Integer minimumPurchaseOrderLines = vendor.getMinimumPurchaseOrderLines();
                    Integer maximumPurchaseOrderLines = vendor.getMaximumPurchaseOrderLines();
                    Long minimumPurchaseOrderAmount = vendor.getMinimumPurchaseOrderAmount();
                    Long maximumPurchaseOrderAmount = vendor.getMaximumPurchaseOrderAmount();
                    ItemAliasType itemAliasType = vendor.getDefaultItemAliasType();
                    Language preferredLanguage = partyDetail.getPreferredLanguage();
                    Currency preferredCurrency = partyDetail.getPreferredCurrency();
                    TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
                    DateTimeFormat dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
                    Person person = partyControl.getPerson(party);
                    PersonalTitle personalTitle = person == null ? null : person.getPersonalTitle();
                    NameSuffix nameSuffix = person == null ? null : person.getNameSuffix();

                    result.setEdit(edit);
                    edit.setVendorName(vendor.getVendorName());
                    edit.setVendorTypeName(vendor.getVendorType().getLastDetail().getVendorTypeName());
                    edit.setMinimumPurchaseOrderLines(minimumPurchaseOrderLines == null ? null : minimumPurchaseOrderLines.toString());
                    edit.setMaximumPurchaseOrderLines(maximumPurchaseOrderLines == null ? null : maximumPurchaseOrderLines.toString());
                    edit.setMinimumPurchaseOrderAmount(minimumPurchaseOrderAmount == null ? null : amountUtils.formatCostLine(vendorPreferredCurrency, minimumPurchaseOrderAmount));
                    edit.setMaximumPurchaseOrderAmount(maximumPurchaseOrderAmount == null ? null : amountUtils.formatCostLine(vendorPreferredCurrency, maximumPurchaseOrderAmount));
                    edit.setUseItemPurchasingCategories(vendor.getUseItemPurchasingCategories().toString());
                    edit.setCancellationPolicyName(cancellationPolicy == null ? null : cancellationPolicy.getLastDetail().getCancellationPolicyName());
                    edit.setReturnPolicyName(returnPolicy == null ? null : returnPolicy.getLastDetail().getReturnPolicyName());
                    edit.setApGlAccountName(apGlAccount == null ? null : apGlAccount.getLastDetail().getGlAccountName());
                    edit.setHoldUntilComplete(vendor.getHoldUntilComplete().toString());
                    edit.setAllowBackorders(vendor.getAllowBackorders().toString());
                    edit.setAllowSubstitutions(vendor.getAllowSubstitutions().toString());
                    edit.setAllowCombiningShipments(vendor.getAllowCombiningShipments().toString());
                    edit.setRequireReference(vendor.getRequireReference().toString());
                    edit.setAllowReferenceDuplicates(vendor.getAllowReferenceDuplicates().toString());
                    edit.setReferenceValidationPattern(vendor.getReferenceValidationPattern());
                    edit.setDefaultItemAliasTypeName(itemAliasType == null ? null : itemAliasType.getLastDetail().getItemAliasTypeName());
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
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }

                result.setEntityLock(getEntityLockTransfer(party));
            } else {
                if(editMode.equals(EditMode.ABANDON)) {
                    unlockEntity(party);
                } else {
                    if(editMode.equals(EditMode.UPDATE)) {
                        VendorValue vendorValue = vendorControl.getVendorValue(vendor);
                        String vendorName = edit.getVendorName();
                        Vendor duplicateVendor = vendorControl.getVendorByName(vendorName);

                        if(duplicateVendor == null || duplicateVendor.getPrimaryKey().equals(vendorValue.getPrimaryKey())) {
                            String vendorTypeName = edit.getVendorTypeName();
                            VendorType vendorType = vendorControl.getVendorTypeByName(vendorTypeName);

                            if(vendorType != null) {
                                String cancellationPolicyName = edit.getCancellationPolicyName();
                                CancellationPolicy cancellationPolicy = null;

                                if(cancellationPolicyName != null) {
                                    var cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                                    CancellationKind returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_VENDOR_CANCELLATION);

                                    cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, cancellationPolicyName);
                                }

                                if(cancellationPolicyName == null || cancellationPolicy != null) {
                                    String returnPolicyName = edit.getReturnPolicyName();
                                    ReturnPolicy returnPolicy = null;

                                    if(returnPolicyName != null) {
                                        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                                        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_VENDOR_RETURN);

                                        returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                                    }

                                    if(returnPolicyName == null || returnPolicy != null) {
                                        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                        String apGlAccountName = edit.getApGlAccountName();
                                        GlAccount apGlAccount = apGlAccountName == null ? null : accountingControl.getGlAccountByName(apGlAccountName);

                                        if(apGlAccountName == null || apGlAccount != null) {
                                            String glAccountCategoryName = apGlAccount == null ? null
                                                    : apGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                            if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE)) {
                                                var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
                                                String defaultItemAliasTypeName = edit.getDefaultItemAliasTypeName();
                                                ItemAliasType defaultItemAliasType = itemControl.getItemAliasTypeByName(defaultItemAliasTypeName);

                                                if(defaultItemAliasTypeName == null || defaultItemAliasType != null) {
                                                    if(defaultItemAliasType == null || !defaultItemAliasType.getLastDetail().getAllowMultiple()) {
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
                                                                        if(lockEntityForUpdate(party)) {
                                                                            try {
                                                                                Soundex soundex = new Soundex();
                                                                                PartyDetailValue partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
                                                                                PartyGroup partyGroup = partyControl.getPartyGroupForUpdate(party);
                                                                                Person person = partyControl.getPersonForUpdate(party);
                                                                                String minimumPurchaseOrderLines = edit.getMinimumPurchaseOrderLines();
                                                                                String maximumPurchaseOrderLines = edit.getMaximumPurchaseOrderLines();
                                                                                String minimumPurchaseOrderAmount = edit.getMinimumPurchaseOrderAmount();
                                                                                String maximumPurchaseOrderAmount = edit.getMaximumPurchaseOrderAmount();
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

                                                                                vendorValue.setVendorName(vendorName);
                                                                                vendorValue.setMinimumPurchaseOrderLines(minimumPurchaseOrderLines == null ? null : Integer.valueOf(minimumPurchaseOrderLines));
                                                                                vendorValue.setMaximumPurchaseOrderLines(maximumPurchaseOrderLines == null ? null : Integer.valueOf(maximumPurchaseOrderLines));
                                                                                vendorValue.setMinimumPurchaseOrderAmount(minimumPurchaseOrderAmount == null ? null : Long.valueOf(minimumPurchaseOrderAmount));
                                                                                vendorValue.setMaximumPurchaseOrderAmount(maximumPurchaseOrderAmount == null ? null : Long.valueOf(maximumPurchaseOrderAmount));
                                                                                vendorValue.setUseItemPurchasingCategories(Boolean.valueOf(edit.getUseItemPurchasingCategories()));
                                                                                vendorValue.setDefaultItemAliasTypePK(defaultItemAliasType == null ? null : defaultItemAliasType.getPrimaryKey());
                                                                                vendorValue.setCancellationPolicyPK(cancellationPolicy == null ? null : cancellationPolicy.getPrimaryKey());
                                                                                vendorValue.setReturnPolicyPK(returnPolicy == null ? null : returnPolicy.getPrimaryKey());
                                                                                vendorValue.setApGlAccountPK(apGlAccount == null ? null : apGlAccount.getPrimaryKey());
                                                                                vendorValue.setHoldUntilComplete(Boolean.valueOf(edit.getHoldUntilComplete()));
                                                                                vendorValue.setAllowBackorders(Boolean.valueOf(edit.getAllowBackorders()));
                                                                                vendorValue.setAllowSubstitutions(Boolean.valueOf(edit.getAllowSubstitutions()));
                                                                                vendorValue.setAllowCombiningShipments(Boolean.valueOf(edit.getAllowCombiningShipments()));
                                                                                vendorValue.setRequireReference(Boolean.valueOf(edit.getRequireReference()));
                                                                                vendorValue.setAllowReferenceDuplicates(Boolean.valueOf(edit.getAllowReferenceDuplicates()));
                                                                                vendorValue.setReferenceValidationPattern(edit.getReferenceValidationPattern());

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
                                                                                        partyControl.createPerson(party, personalTitle, firstName, firstNameSdx, middleName,
                                                                                                middleNameSdx, lastName, lastNameSdx, nameSuffix, updatedBy);
                                                                                    }
                                                                                } else {
                                                                                    if(person != null) {
                                                                                        partyControl.deletePerson(person, updatedBy);
                                                                                    }
                                                                                }

                                                                                vendorControl.updateVendorFromValue(vendorValue, updatedBy);
                                                                                partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
                                                                            } finally {
                                                                                unlockEntity(party);
                                                                            }
                                                                        } else {
                                                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
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
                                                        addExecutionError(ExecutionErrors.InvalidItemAliasType.name(), defaultItemAliasTypeName);
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownDefaultItemAliasTypeName.name(), defaultItemAliasTypeName);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.InvalidGlAccountCategory.name(), glAccountCategoryName);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownApGlAccountName.name(), apGlAccountName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownVendorTypeName.name(), vendorTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateVendorName.name(), vendorName);
                        }

                        if(hasExecutionErrors()) {
                            result.setVendor(vendorControl.getVendorTransfer(getUserVisit(), vendor));
                            result.setEntityLock(getEntityLockTransfer(party));
                        }
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownVendorName.name(), originalVendorName);
        }

        return result;
    }
    
}
