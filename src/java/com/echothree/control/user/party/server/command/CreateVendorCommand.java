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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateVendorForm;
import com.echothree.control.user.party.common.result.CreateVendorResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.logic.ContactEmailAddressLogic;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.control.vendor.common.workflow.VendorStatusConstants;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.vendor.server.logic.VendorLogic;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.entity.VendorTypeDetail;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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

public class CreateVendorCommand
        extends BaseSimpleCommand<CreateVendorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Vendor.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ApGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MinimumPurchaseOrderLines", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumPurchaseOrderLines", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumPurchaseOrderAmount:CurrencyIsoName,PreferredCurrencyIsoName", FieldType.UNSIGNED_COST_LINE, false, null, null),
                new FieldDefinition("MaximumPurchaseOrderAmount:CurrencyIsoName,PreferredCurrencyIsoName", FieldType.UNSIGNED_COST_LINE, false, null, null),
                new FieldDefinition("UseItemPurchasingCategories", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultItemAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
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
                new FieldDefinition("EmailAddress", FieldType.EMAIL_ADDRESS, false, null, null),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateVendorCommand */
    public CreateVendorCommand(UserVisitPK userVisitPK, CreateVendorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateVendorResult result = PartyResultFactory.getCreateVendorResult();
        var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        String vendorName = form.getVendorName();
        Vendor vendor = vendorName == null ? null : vendorControl.getVendorByName(vendorName);

        if(vendor == null) {
            String vendorTypeName = form.getVendorTypeName();
            VendorType vendorType = vendorTypeName == null ? vendorControl.getDefaultVendorType() : vendorControl.getVendorTypeByName(vendorTypeName);

            if(vendorType != null) {
                String cancellationPolicyName = form.getCancellationPolicyName();
                CancellationPolicy cancellationPolicy = null;

                if(cancellationPolicyName != null) {
                    var cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                    CancellationKind returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.VENDOR_CANCELLATION.name());

                    cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, cancellationPolicyName);
                }

                if(cancellationPolicyName == null || cancellationPolicy != null) {
                    String returnPolicyName = form.getReturnPolicyName();
                    ReturnPolicy returnPolicy = null;

                    if(returnPolicyName != null) {
                        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.VENDOR_RETURN.name());

                        returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                    }

                    if(returnPolicyName == null || returnPolicy != null) {
                        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                        String apGlAccountName = form.getApGlAccountName();
                        GlAccount apGlAccount = apGlAccountName == null ? null : accountingControl.getGlAccountByName(apGlAccountName);

                        if(apGlAccountName == null || apGlAccount != null) {
                            String glAccountCategoryName = apGlAccount == null ? null
                                    : apGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                            if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE)) {
                                var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
                                String defaultItemAliasTypeName = form.getDefaultItemAliasTypeName();
                                ItemAliasType defaultItemAliasType = itemControl.getItemAliasTypeByName(defaultItemAliasTypeName);

                                if(defaultItemAliasTypeName == null || defaultItemAliasType != null) {
                                    if(defaultItemAliasType == null || !defaultItemAliasType.getLastDetail().getAllowMultiple()) {
                                        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                                        String preferredLanguageIsoName = form.getPreferredLanguageIsoName();
                                        Language preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                                        if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                                            String preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                                            TimeZone preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                                            if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                                String preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                                                DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                                                if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                                    String preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                                                    Currency preferredCurrency;

                                                    if(preferredCurrencyIsoName == null) {
                                                        preferredCurrency = null;
                                                    } else {
                                                        preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                                    }

                                                    if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                                        var coreControl = getCoreControl();
                                                        var freeOnBoardControl = (FreeOnBoardControl)Session.getModelController(FreeOnBoardControl.class);
                                                        var partyFreeOnBoardControl = (PartyFreeOnBoardControl)Session.getModelController(PartyFreeOnBoardControl.class);
                                                        var termControl = (TermControl)Session.getModelController(TermControl.class);
                                                        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                                        VendorTypeDetail vendorTypeDetail = vendorType.getLastDetail();
                                                        Soundex soundex = new Soundex();
                                                        PartyType partyType = partyControl.getPartyTypeByName(PartyTypes.VENDOR.name());
                                                        BasePK createdBy = getPartyPK();
                                                        String personalTitleId = form.getPersonalTitleId();
                                                        PersonalTitle personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                                                        String firstName = form.getFirstName();
                                                        String middleName = form.getMiddleName();
                                                        String lastName = form.getLastName();
                                                        String nameSuffixId = form.getNameSuffixId();
                                                        NameSuffix nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                                        String name = form.getName();
                                                        String emailAddress = form.getEmailAddress();
                                                        Boolean allowSolicitation = Boolean.valueOf(form.getAllowSolicitation());
                                                        String strMinimumPurchaseOrderLines = form.getMinimumPurchaseOrderLines();
                                                        Integer minimumPurchaseOrderLines = strMinimumPurchaseOrderLines == null ? null : Integer.valueOf(strMinimumPurchaseOrderLines);
                                                        String strMaximumPurchaseOrderLines = form.getMaximumPurchaseOrderLines();
                                                        Integer maximumPurchaseOrderLines = strMaximumPurchaseOrderLines == null ? null : Integer.valueOf(strMaximumPurchaseOrderLines);
                                                        String strMinimumPurchaseOrderAmount = form.getMinimumPurchaseOrderAmount();
                                                        Long minimumPurchaseOrderAmount = strMinimumPurchaseOrderAmount == null ? null : Long.valueOf(strMinimumPurchaseOrderAmount);
                                                        String strMaximumPurchaseOrderAmount = form.getMaximumPurchaseOrderAmount();
                                                        Long maximumPurchaseOrderAmount = strMaximumPurchaseOrderAmount == null ? null : Long.valueOf(strMaximumPurchaseOrderAmount);
                                                        Boolean useItemPurchasingCategories = Boolean.valueOf(form.getUseItemPurchasingCategories());

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

                                                        Party party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency,
                                                                preferredTimeZone, preferredDateTimeFormat, createdBy);

                                                        if(createdBy == null) {
                                                            createdBy = party.getPrimaryKey();
                                                        }

                                                        if(personalTitle != null || firstName != null || middleName != null || lastName != null || nameSuffix != null) {
                                                            partyControl.createPerson(party, personalTitle, firstName, firstNameSdx, middleName, middleNameSdx,
                                                                    lastName, lastNameSdx, nameSuffix, createdBy);
                                                        }

                                                        if(name != null) {
                                                            partyControl.createPartyGroup(party, name, createdBy);
                                                        }

                                                        vendor = VendorLogic.getInstance().createVendor(this, party, vendorName, vendorType, minimumPurchaseOrderLines,
                                                                maximumPurchaseOrderLines, minimumPurchaseOrderAmount, maximumPurchaseOrderAmount,
                                                                useItemPurchasingCategories, defaultItemAliasType, cancellationPolicy, returnPolicy, apGlAccount,
                                                                vendorTypeDetail.getDefaultHoldUntilComplete(), vendorTypeDetail.getDefaultAllowBackorders(),
                                                                vendorTypeDetail.getDefaultAllowSubstitutions(), vendorTypeDetail.getDefaultAllowCombiningShipments(),
                                                                vendorTypeDetail.getDefaultRequireReference(), vendorTypeDetail.getDefaultAllowReferenceDuplicates(),
                                                                vendorTypeDetail.getDefaultReferenceValidationPattern(), null, null, createdBy);

                                                        if(emailAddress != null) {
                                                            ContactEmailAddressLogic.getInstance().createContactEmailAddress(party,
                                                                    emailAddress, allowSolicitation, null,
                                                                    ContactMechanismPurposes.PRIMARY_EMAIL.name(), createdBy);
                                                        }

                                                        termControl.createPartyTerm(party, termControl.getDefaultTerm(), null, createdBy);

                                                        partyFreeOnBoardControl.createPartyFreeOnBoard(party, freeOnBoardControl.getDefaultFreeOnBoard(), createdBy);

                                                        ContactListLogic.getInstance().setupInitialContactLists(this, party, createdBy);

                                                        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(party.getPrimaryKey());
                                                        workflowControl.addEntityToWorkflowUsingNames(null, VendorStatusConstants.Workflow_VENDOR_STATUS,
                                                                VendorStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);
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
                if(vendorTypeName != null) {
                    addExecutionError(ExecutionErrors.UnknownVendorTypeName.name(), vendorTypeName);
                } else {
                    addExecutionError(ExecutionErrors.UnknownDefaultVendorType.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateVendorName.name(), vendorName);
        }

        if(vendor != null) {
            Party party = vendor.getParty();

            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setVendorName(vendor.getVendorName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }

        return result;
    }
    
}
