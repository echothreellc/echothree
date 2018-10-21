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

package com.echothree.model.control.party.remote.transfer;

import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.cancellationpolicy.remote.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.remote.transfer.PartyCancellationPolicyTransfer;
import com.echothree.model.control.carrier.remote.transfer.PartyCarrierAccountTransfer;
import com.echothree.model.control.carrier.remote.transfer.PartyCarrierTransfer;
import com.echothree.model.control.communication.remote.transfer.CommunicationEventTransfer;
import com.echothree.model.control.contact.remote.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contactlist.remote.transfer.PartyContactListTransfer;
import com.echothree.model.control.core.remote.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.control.core.remote.transfer.PartyEntityTypeTransfer;
import com.echothree.model.control.document.remote.transfer.PartyDocumentTransfer;
import com.echothree.model.control.employee.remote.transfer.EmploymentTransfer;
import com.echothree.model.control.employee.remote.transfer.LeaveTransfer;
import com.echothree.model.control.employee.remote.transfer.PartyResponsibilityTransfer;
import com.echothree.model.control.employee.remote.transfer.PartySkillTransfer;
import com.echothree.model.control.invoice.remote.transfer.InvoiceTransfer;
import com.echothree.model.control.payment.remote.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.remote.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.printer.remote.transfer.PartyPrinterGroupUseTransfer;
import com.echothree.model.control.returnpolicy.remote.transfer.PartyReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.remote.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.scale.remote.transfer.PartyScaleUseTransfer;
import com.echothree.model.control.subscription.remote.transfer.SubscriptionTransfer;
import com.echothree.model.control.term.remote.transfer.PartyCreditLimitTransfer;
import com.echothree.model.control.term.remote.transfer.PartyTermTransfer;
import com.echothree.model.control.training.remote.transfer.PartyTrainingClassTransfer;
import com.echothree.model.control.user.remote.transfer.RecoveryAnswerTransfer;
import com.echothree.model.control.user.remote.transfer.UserLoginTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;
import com.echothree.util.remote.transfer.ListWrapper;

public class PartyTransfer
        extends BaseTransfer {
    
    private String partyName;
    private PartyTypeTransfer partyType;
    private LanguageTransfer preferredLanguage;
    private CurrencyTransfer preferredCurrency;
    private TimeZoneTransfer preferredTimeZone;
    private DateTimeFormatTransfer preferredDateTimeFormat;
    private PersonTransfer person;
    private PartyGroupTransfer partyGroup;
    private ProfileTransfer profile;
    
    private UserLoginTransfer userLogin;
    private RecoveryAnswerTransfer recoveryAnswer;
    private String description;
    
    private ListWrapper<PartyAliasTransfer> partyAliases;
    private ListWrapper<PartyRelationshipTransfer> partyRelationships;
    private ListWrapper<PartyContactMechanismTransfer> partyContactMechanisms;
    private ListWrapper<PartyContactListTransfer> partyContactLists;
    private ListWrapper<BillingAccountTransfer> billingAccounts;
    
    private Long invoicesFromCount;
    private ListWrapper<InvoiceTransfer> invoicesFrom;
    private Long invoicesToCount;
    private ListWrapper<InvoiceTransfer> invoicesTo;

    private ListWrapper<PartyCreditLimitTransfer> partyCreditLimits;
    private PartyTermTransfer partyTerm;
    private ListWrapper<PartyPaymentMethodTransfer> partyPaymentMethods;

    private CancellationPolicyTransfer cancellationPolicy;
    private ReturnPolicyTransfer returnPolicy;
    private ListWrapper<PartyCancellationPolicyTransfer> partyCancellationPolicies;
    private ListWrapper<PartyReturnPolicyTransfer> partyReturnPolicies;

    private ListWrapper<SubscriptionTransfer> subscriptions;

    private Long communicationEventsCount;
    private ListWrapper<CommunicationEventTransfer> communicationEvents;

    private ListWrapper<PartyDocumentTransfer> partyDocuments;

    private ListWrapper<PartyPrinterGroupUseTransfer> partyPrinterGroupUses;
    private ListWrapper<PartyScaleUseTransfer> partyScaleUses;
    private ListWrapper<PartyEntityTypeTransfer> partyEntityTypes;
    private ListWrapper<PartyApplicationEditorUseTransfer> partyApplicationEditorUses;

    private ListWrapper<PartyCarrierTransfer> partyCarriers;
    private ListWrapper<PartyCarrierAccountTransfer> partyCarrierAccounts;

    private ListWrapper<EmploymentTransfer> employments;
    private ListWrapper<LeaveTransfer> leaves;
    
    private ListWrapper<PartyResponsibilityTransfer> partyResponsibilities;
    private ListWrapper<PartyTrainingClassTransfer> partyTrainingClasses;
    private ListWrapper<PartySkillTransfer> partySkills;

    /** Creates a new instance of PartyTransfer */
    public PartyTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency,
            TimeZoneTransfer preferredTimeZone, DateTimeFormatTransfer preferredDateTimeFormat, PersonTransfer person, PartyGroupTransfer partyGroup,
            ProfileTransfer profile) {
        this.partyName = partyName;
        this.partyType = partyType;
        this.preferredLanguage = preferredLanguage;
        this.preferredCurrency = preferredCurrency;
        this.preferredTimeZone = preferredTimeZone;
        this.preferredDateTimeFormat = preferredDateTimeFormat;
        this.person = person;
        this.partyGroup = partyGroup;
        this.profile = profile;
    }

    /**
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * @return the preferredLanguage
     */
    public LanguageTransfer getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * @param preferredLanguage the preferredLanguage to set
     */
    public void setPreferredLanguage(LanguageTransfer preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * @return the preferredCurrency
     */
    public CurrencyTransfer getPreferredCurrency() {
        return preferredCurrency;
    }

    /**
     * @param preferredCurrency the preferredCurrency to set
     */
    public void setPreferredCurrency(CurrencyTransfer preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }

    /**
     * @return the preferredTimeZone
     */
    public TimeZoneTransfer getPreferredTimeZone() {
        return preferredTimeZone;
    }

    /**
     * @param preferredTimeZone the preferredTimeZone to set
     */
    public void setPreferredTimeZone(TimeZoneTransfer preferredTimeZone) {
        this.preferredTimeZone = preferredTimeZone;
    }

    /**
     * @return the preferredDateTimeFormat
     */
    public DateTimeFormatTransfer getPreferredDateTimeFormat() {
        return preferredDateTimeFormat;
    }

    /**
     * @param preferredDateTimeFormat the preferredDateTimeFormat to set
     */
    public void setPreferredDateTimeFormat(DateTimeFormatTransfer preferredDateTimeFormat) {
        this.preferredDateTimeFormat = preferredDateTimeFormat;
    }

    /**
     * @return the person
     */
    public PersonTransfer getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(PersonTransfer person) {
        this.person = person;
    }

    /**
     * @return the partyGroup
     */
    public PartyGroupTransfer getPartyGroup() {
        return partyGroup;
    }

    /**
     * @param partyGroup the partyGroup to set
     */
    public void setPartyGroup(PartyGroupTransfer partyGroup) {
        this.partyGroup = partyGroup;
    }

    /**
     * @return the profile
     */
    public ProfileTransfer getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(ProfileTransfer profile) {
        this.profile = profile;
    }

    /**
     * @return the userLogin
     */
    public UserLoginTransfer getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(UserLoginTransfer userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * @return the recoveryAnswer
     */
    public RecoveryAnswerTransfer getRecoveryAnswer() {
        return recoveryAnswer;
    }

    /**
     * @param recoveryAnswer the recoveryAnswer to set
     */
    public void setRecoveryAnswer(RecoveryAnswerTransfer recoveryAnswer) {
        this.recoveryAnswer = recoveryAnswer;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the partyAliases
     */
    public ListWrapper<PartyAliasTransfer> getPartyAliases() {
        return partyAliases;
    }

    /**
     * @param partyAliases the partyAliases to set
     */
    public void setPartyAliases(ListWrapper<PartyAliasTransfer> partyAliases) {
        this.partyAliases = partyAliases;
    }

    /**
     * @return the partyRelationships
     */
    public ListWrapper<PartyRelationshipTransfer> getPartyRelationships() {
        return partyRelationships;
    }

    /**
     * @param partyRelationships the partyRelationships to set
     */
    public void setPartyRelationships(ListWrapper<PartyRelationshipTransfer> partyRelationships) {
        this.partyRelationships = partyRelationships;
    }

    /**
     * @return the partyContactMechanisms
     */
    public ListWrapper<PartyContactMechanismTransfer> getPartyContactMechanisms() {
        return partyContactMechanisms;
    }

    /**
     * @param partyContactMechanisms the partyContactMechanisms to set
     */
    public void setPartyContactMechanisms(ListWrapper<PartyContactMechanismTransfer> partyContactMechanisms) {
        this.partyContactMechanisms = partyContactMechanisms;
    }

    /**
     * @return the partyContactLists
     */
    public ListWrapper<PartyContactListTransfer> getPartyContactLists() {
        return partyContactLists;
    }

    /**
     * @param partyContactLists the partyContactLists to set
     */
    public void setPartyContactLists(ListWrapper<PartyContactListTransfer> partyContactLists) {
        this.partyContactLists = partyContactLists;
    }

    /**
     * @return the billingAccounts
     */
    public ListWrapper<BillingAccountTransfer> getBillingAccounts() {
        return billingAccounts;
    }

    /**
     * @param billingAccounts the billingAccounts to set
     */
    public void setBillingAccounts(ListWrapper<BillingAccountTransfer> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    /**
     * @return the invoicesFromCount
     */
    public Long getInvoicesFromCount() {
        return invoicesFromCount;
    }

    /**
     * @param invoicesFromCount the invoicesFromCount to set
     */
    public void setInvoicesFromCount(Long invoicesFromCount) {
        this.invoicesFromCount = invoicesFromCount;
    }

    /**
     * @return the invoicesFrom
     */
    public ListWrapper<InvoiceTransfer> getInvoicesFrom() {
        return invoicesFrom;
    }

    /**
     * @param invoicesFrom the invoicesFrom to set
     */
    public void setInvoicesFrom(ListWrapper<InvoiceTransfer> invoicesFrom) {
        this.invoicesFrom = invoicesFrom;
    }

    /**
     * @return the invoicesToCount
     */
    public Long getInvoicesToCount() {
        return invoicesToCount;
    }

    /**
     * @param invoicesToCount the invoicesToCount to set
     */
    public void setInvoicesToCount(Long invoicesToCount) {
        this.invoicesToCount = invoicesToCount;
    }

    /**
     * @return the invoicesTo
     */
    public ListWrapper<InvoiceTransfer> getInvoicesTo() {
        return invoicesTo;
    }

    /**
     * @param invoicesTo the invoicesTo to set
     */
    public void setInvoicesTo(ListWrapper<InvoiceTransfer> invoicesTo) {
        this.invoicesTo = invoicesTo;
    }

    /**
     * @return the partyCreditLimits
     */
    public ListWrapper<PartyCreditLimitTransfer> getPartyCreditLimits() {
        return partyCreditLimits;
    }

    /**
     * @param partyCreditLimits the partyCreditLimits to set
     */
    public void setPartyCreditLimits(ListWrapper<PartyCreditLimitTransfer> partyCreditLimits) {
        this.partyCreditLimits = partyCreditLimits;
    }

    /**
     * @return the partyTerm
     */
    public PartyTermTransfer getPartyTerm() {
        return partyTerm;
    }

    /**
     * @param partyTerm the partyTerm to set
     */
    public void setPartyTerm(PartyTermTransfer partyTerm) {
        this.partyTerm = partyTerm;
    }

    /**
     * @return the partyPaymentMethods
     */
    public ListWrapper<PartyPaymentMethodTransfer> getPartyPaymentMethods() {
        return partyPaymentMethods;
    }

    /**
     * @param partyPaymentMethods the partyPaymentMethods to set
     */
    public void setPartyPaymentMethods(ListWrapper<PartyPaymentMethodTransfer> partyPaymentMethods) {
        this.partyPaymentMethods = partyPaymentMethods;
    }

    /**
     * @return the cancellationPolicy
     */
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    /**
     * @param cancellationPolicy the cancellationPolicy to set
     */
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * @return the returnPolicy
     */
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }

    /**
     * @param returnPolicy the returnPolicy to set
     */
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /**
     * @return the partyCancellationPolicies
     */
    public ListWrapper<PartyCancellationPolicyTransfer> getPartyCancellationPolicies() {
        return partyCancellationPolicies;
    }

    /**
     * @param partyCancellationPolicies the partyCancellationPolicies to set
     */
    public void setPartyCancellationPolicies(ListWrapper<PartyCancellationPolicyTransfer> partyCancellationPolicies) {
        this.partyCancellationPolicies = partyCancellationPolicies;
    }

    /**
     * @return the partyReturnPolicies
     */
    public ListWrapper<PartyReturnPolicyTransfer> getPartyReturnPolicies() {
        return partyReturnPolicies;
    }

    /**
     * @param partyReturnPolicies the partyReturnPolicies to set
     */
    public void setPartyReturnPolicies(ListWrapper<PartyReturnPolicyTransfer> partyReturnPolicies) {
        this.partyReturnPolicies = partyReturnPolicies;
    }

    /**
     * @return the subscriptions
     */
    public ListWrapper<SubscriptionTransfer> getSubscriptions() {
        return subscriptions;
    }

    /**
     * @param subscriptions the subscriptions to set
     */
    public void setSubscriptions(ListWrapper<SubscriptionTransfer> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /**
     * @return the communicationEventsCount
     */
    public Long getCommunicationEventsCount() {
        return communicationEventsCount;
    }

    /**
     * @param communicationEventsCount the communicationEventsCount to set
     */
    public void setCommunicationEventsCount(Long communicationEventsCount) {
        this.communicationEventsCount = communicationEventsCount;
    }

    /**
     * @return the communicationEvents
     */
    public ListWrapper<CommunicationEventTransfer> getCommunicationEvents() {
        return communicationEvents;
    }

    /**
     * @param communicationEvents the communicationEvents to set
     */
    public void setCommunicationEvents(ListWrapper<CommunicationEventTransfer> communicationEvents) {
        this.communicationEvents = communicationEvents;
    }

    /**
     * @return the partyDocuments
     */
    public ListWrapper<PartyDocumentTransfer> getPartyDocuments() {
        return partyDocuments;
    }

    /**
     * @param partyDocuments the partyDocuments to set
     */
    public void setPartyDocuments(ListWrapper<PartyDocumentTransfer> partyDocuments) {
        this.partyDocuments = partyDocuments;
    }

    /**
     * @return the partyPrinterGroupUses
     */
    public ListWrapper<PartyPrinterGroupUseTransfer> getPartyPrinterGroupUses() {
        return partyPrinterGroupUses;
    }

    /**
     * @param partyPrinterGroupUses the partyPrinterGroupUses to set
     */
    public void setPartyPrinterGroupUses(ListWrapper<PartyPrinterGroupUseTransfer> partyPrinterGroupUses) {
        this.partyPrinterGroupUses = partyPrinterGroupUses;
    }

    /**
     * @return the partyScaleUses
     */
    public ListWrapper<PartyScaleUseTransfer> getPartyScaleUses() {
        return partyScaleUses;
    }

    /**
     * @param partyScaleUses the partyScaleUses to set
     */
    public void setPartyScaleUses(ListWrapper<PartyScaleUseTransfer> partyScaleUses) {
        this.partyScaleUses = partyScaleUses;
    }

    /**
     * @return the partyEntityTypes
     */
    public ListWrapper<PartyEntityTypeTransfer> getPartyEntityTypes() {
        return partyEntityTypes;
    }

    /**
     * @param partyEntityTypes the partyEntityTypes to set
     */
    public void setPartyEntityTypes(ListWrapper<PartyEntityTypeTransfer> partyEntityTypes) {
        this.partyEntityTypes = partyEntityTypes;
    }

    /**
     * @return the partyApplicationEditorUses
     */
    public ListWrapper<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUses() {
        return partyApplicationEditorUses;
    }

    /**
     * @param partyApplicationEditorUses the partyApplicationEditorUses to set
     */
    public void setPartyApplicationEditorUses(ListWrapper<PartyApplicationEditorUseTransfer> partyApplicationEditorUses) {
        this.partyApplicationEditorUses = partyApplicationEditorUses;
    }

    /**
     * @return the partyCarriers
     */
    public ListWrapper<PartyCarrierTransfer> getPartyCarriers() {
        return partyCarriers;
    }

    /**
     * @param partyCarriers the partyCarriers to set
     */
    public void setPartyCarriers(ListWrapper<PartyCarrierTransfer> partyCarriers) {
        this.partyCarriers = partyCarriers;
    }

    /**
     * @return the partyCarrierAccounts
     */
    public ListWrapper<PartyCarrierAccountTransfer> getPartyCarrierAccounts() {
        return partyCarrierAccounts;
    }

    /**
     * @param partyCarrierAccounts the partyCarrierAccounts to set
     */
    public void setPartyCarrierAccounts(ListWrapper<PartyCarrierAccountTransfer> partyCarrierAccounts) {
        this.partyCarrierAccounts = partyCarrierAccounts;
    }

    /**
     * @return the employments
     */
    public ListWrapper<EmploymentTransfer> getEmployments() {
        return employments;
    }

    /**
     * @param employments the employments to set
     */
    public void setEmployments(ListWrapper<EmploymentTransfer> employments) {
        this.employments = employments;
    }

    /**
     * @return the leaves
     */
    public ListWrapper<LeaveTransfer> getLeaves() {
        return leaves;
    }

    /**
     * @param leaves the leaves to set
     */
    public void setLeaves(ListWrapper<LeaveTransfer> leaves) {
        this.leaves = leaves;
    }

    /**
     * @return the partyResponsibilities
     */
    public ListWrapper<PartyResponsibilityTransfer> getPartyResponsibilities() {
        return partyResponsibilities;
    }

    /**
     * @param partyResponsibilities the partyResponsibilities to set
     */
    public void setPartyResponsibilities(ListWrapper<PartyResponsibilityTransfer> partyResponsibilities) {
        this.partyResponsibilities = partyResponsibilities;
    }

    /**
     * @return the partyTrainingClasses
     */
    public ListWrapper<PartyTrainingClassTransfer> getPartyTrainingClasses() {
        return partyTrainingClasses;
    }

    /**
     * @param partyTrainingClasses the partyTrainingClasses to set
     */
    public void setPartyTrainingClasses(ListWrapper<PartyTrainingClassTransfer> partyTrainingClasses) {
        this.partyTrainingClasses = partyTrainingClasses;
    }

    /**
     * @return the partySkills
     */
    public ListWrapper<PartySkillTransfer> getPartySkills() {
        return partySkills;
    }

    /**
     * @param partySkills the partySkills to set
     */
    public void setPartySkills(ListWrapper<PartySkillTransfer> partySkills) {
        this.partySkills = partySkills;
    }

}
