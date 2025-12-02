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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.VendorEdit;
import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.form.EditVendorForm;
import com.echothree.control.user.vendor.common.result.EditVendorResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.VendorUniversalSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.logic.VendorLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditVendorCommand
        extends BaseAbstractEditCommand<VendorUniversalSpec, VendorEdit, EditVendorResult, Party, Party> {

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
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
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
    public EditVendorCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorName = spec.getVendorName();
        
        if(vendorName != null) {
            var vendor = vendorControl.getVendorByNameForUpdate(vendorName);
            
            if(vendor != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                
                validator.setCurrency(partyControl.getPreferredCurrency(vendor.getParty()));
            }
        }
    }

    @Override
    public EditVendorResult getResult() {
        return VendorResultFactory.getEditVendorResult();
    }

    @Override
    public VendorEdit getEdit() {
        return VendorEditFactory.getVendorEdit();
    }

    @Override
    public Party getEntity(EditVendorResult result) {
        var vendorControl = Session.getModelController(VendorControl.class);
        Vendor vendor;

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            vendor = VendorLogic.getInstance().getVendorByUniversalSpec(this, spec);
        } else { // EditMode.UPDATE
            vendor = VendorLogic.getInstance().getVendorByUniversalSpecForUpdate(this, spec);
        }

        if(!hasExecutionErrors()) {
            result.setVendor(vendorControl.getVendorTransfer(getUserVisit(), vendor));
        }

        return vendor.getParty();
    }

    @Override
    public Party getLockEntity(Party party) {
        return party;
    }

    @Override
    public void fillInResult(EditVendorResult result, Party party) {
        var vendorControl = Session.getModelController(VendorControl.class);

        result.setVendor(vendorControl.getVendorTransfer(getUserVisit(), party));
    }

    @Override
    public void doLock(VendorEdit edit, Party party) {
        var partyControl = Session.getModelController(PartyControl.class);
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendor = vendorControl.getVendor(party);
        var amountUtils = AmountUtils.getInstance();
        var vendorPreferredCurrency = getPreferredCurrency(party);
        var partyDetail = party.getLastDetail();
        var partyGroup = partyControl.getPartyGroup(party);
        var apGlAccount = vendor.getApGlAccount();
        var cancellationPolicy = vendor.getCancellationPolicy();
        var returnPolicy = vendor.getReturnPolicy();
        var minimumPurchaseOrderLines = vendor.getMinimumPurchaseOrderLines();
        var maximumPurchaseOrderLines = vendor.getMaximumPurchaseOrderLines();
        var minimumPurchaseOrderAmount = vendor.getMinimumPurchaseOrderAmount();
        var maximumPurchaseOrderAmount = vendor.getMaximumPurchaseOrderAmount();
        var itemAliasType = vendor.getDefaultItemAliasType();
        var preferredLanguage = partyDetail.getPreferredLanguage();
        var preferredCurrency = partyDetail.getPreferredCurrency();
        var preferredTimeZone = partyDetail.getPreferredTimeZone();
        var dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
        var person = partyControl.getPerson(party);
        var personalTitle = person == null ? null : person.getPersonalTitle();
        var nameSuffix = person == null ? null : person.getNameSuffix();

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
    }

    CancellationPolicy cancellationPolicy;
    ReturnPolicy returnPolicy;
    GlAccount apGlAccount;
    ItemAliasType defaultItemAliasType;
    Language preferredLanguage;
    TimeZone preferredTimeZone;
    DateTimeFormat preferredDateTimeFormat;
    Currency preferredCurrency;

    @Override
    public void canUpdate(Party party) {
        var partyControl = Session.getModelController(PartyControl.class);
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendor = vendorControl.getVendorForUpdate(party);
        var vendorName = edit.getVendorName();
        var duplicateVendor = vendorControl.getVendorByName(vendorName);

        if(duplicateVendor == null || duplicateVendor.getPrimaryKey().equals(vendor.getPrimaryKey())) {
            var vendorTypeName = edit.getVendorTypeName();
            var vendorType = vendorControl.getVendorTypeByName(vendorTypeName);

            if(vendorType != null) {
                var cancellationPolicyName = edit.getCancellationPolicyName();

                if(cancellationPolicyName != null) {
                    var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                    var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.VENDOR_CANCELLATION.name());

                    cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                }

                if(cancellationPolicyName == null || cancellationPolicy != null) {
                    var returnPolicyName = edit.getReturnPolicyName();

                    if(returnPolicyName != null) {
                        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                        var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.VENDOR_RETURN.name());

                        returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                    }

                    if(returnPolicyName == null || returnPolicy != null) {
                        var accountingControl = Session.getModelController(AccountingControl.class);
                        var apGlAccountName = edit.getApGlAccountName();

                        apGlAccount = apGlAccountName == null ? null : accountingControl.getGlAccountByName(apGlAccountName);

                        if(apGlAccountName == null || apGlAccount != null) {
                            var glAccountCategoryName = apGlAccount == null ? null : apGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                            if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE)) {
                                var itemControl = Session.getModelController(ItemControl.class);
                                var defaultItemAliasTypeName = edit.getDefaultItemAliasTypeName();

                                defaultItemAliasType = itemControl.getItemAliasTypeByName(defaultItemAliasTypeName);

                                if(defaultItemAliasTypeName == null || defaultItemAliasType != null) {
                                    if(defaultItemAliasType == null || !defaultItemAliasType.getLastDetail().getAllowMultiple()) {
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
    }

    @Override
    public void doUpdate(Party party) {
        var partyControl = Session.getModelController(PartyControl.class);
        var vendorControl = Session.getModelController(VendorControl.class);
        var soundex = new Soundex();
        var vendor = vendorControl.getVendorForUpdate(party);
        var vendorValue = vendorControl.getVendorValue(vendor);
        var partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
        var partyGroup = partyControl.getPartyGroupForUpdate(party);
        var person = partyControl.getPersonForUpdate(party);
        var minimumPurchaseOrderLines = edit.getMinimumPurchaseOrderLines();
        var maximumPurchaseOrderLines = edit.getMaximumPurchaseOrderLines();
        var minimumPurchaseOrderAmount = edit.getMinimumPurchaseOrderAmount();
        var maximumPurchaseOrderAmount = edit.getMaximumPurchaseOrderAmount();
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

        vendorValue.setVendorName(edit.getVendorName());
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
    }

}
