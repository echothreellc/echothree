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

package com.echothree.model.control.party.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.PartyCancellationPolicyTransfer;
import com.echothree.model.control.carrier.common.transfer.PartyCarrierAccountTransfer;
import com.echothree.model.control.carrier.common.transfer.PartyCarrierTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contactlist.common.transfer.PartyContactListTransfer;
import com.echothree.model.control.core.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.model.control.employee.common.transfer.EmploymentTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveTransfer;
import com.echothree.model.control.employee.common.transfer.PartyResponsibilityTransfer;
import com.echothree.model.control.employee.common.transfer.PartySkillTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.printer.common.transfer.PartyPrinterGroupUseTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.PartyReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.scale.common.transfer.PartyScaleUseTransfer;
import com.echothree.model.control.shipment.common.transfer.PartyFreeOnBoardTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTransfer;
import com.echothree.model.control.term.common.transfer.PartyCreditLimitTransfer;
import com.echothree.model.control.term.common.transfer.PartyTermTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassTransfer;
import com.echothree.model.control.user.common.transfer.RecoveryAnswerTransfer;
import com.echothree.model.control.user.common.transfer.UserLoginTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

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
    private PartyFreeOnBoardTransfer partyFreeOnBoard;
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
     * Returns the partyName.
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets the partyName.
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Returns the partyType.
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * Sets the partyType.
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * Returns the preferredLanguage.
     * @return the preferredLanguage
     */
    public LanguageTransfer getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Sets the preferredLanguage.
     * @param preferredLanguage the preferredLanguage to set
     */
    public void setPreferredLanguage(LanguageTransfer preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * Returns the preferredCurrency.
     * @return the preferredCurrency
     */
    public CurrencyTransfer getPreferredCurrency() {
        return preferredCurrency;
    }

    /**
     * Sets the preferredCurrency.
     * @param preferredCurrency the preferredCurrency to set
     */
    public void setPreferredCurrency(CurrencyTransfer preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }

    /**
     * Returns the preferredTimeZone.
     * @return the preferredTimeZone
     */
    public TimeZoneTransfer getPreferredTimeZone() {
        return preferredTimeZone;
    }

    /**
     * Sets the preferredTimeZone.
     * @param preferredTimeZone the preferredTimeZone to set
     */
    public void setPreferredTimeZone(TimeZoneTransfer preferredTimeZone) {
        this.preferredTimeZone = preferredTimeZone;
    }

    /**
     * Returns the preferredDateTimeFormat.
     * @return the preferredDateTimeFormat
     */
    public DateTimeFormatTransfer getPreferredDateTimeFormat() {
        return preferredDateTimeFormat;
    }

    /**
     * Sets the preferredDateTimeFormat.
     * @param preferredDateTimeFormat the preferredDateTimeFormat to set
     */
    public void setPreferredDateTimeFormat(DateTimeFormatTransfer preferredDateTimeFormat) {
        this.preferredDateTimeFormat = preferredDateTimeFormat;
    }

    /**
     * Returns the person.
     * @return the person
     */
    public PersonTransfer getPerson() {
        return person;
    }

    /**
     * Sets the person.
     * @param person the person to set
     */
    public void setPerson(PersonTransfer person) {
        this.person = person;
    }

    /**
     * Returns the partyGroup.
     * @return the partyGroup
     */
    public PartyGroupTransfer getPartyGroup() {
        return partyGroup;
    }

    /**
     * Sets the partyGroup.
     * @param partyGroup the partyGroup to set
     */
    public void setPartyGroup(PartyGroupTransfer partyGroup) {
        this.partyGroup = partyGroup;
    }

    /**
     * Returns the profile.
     * @return the profile
     */
    public ProfileTransfer getProfile() {
        return profile;
    }

    /**
     * Sets the profile.
     * @param profile the profile to set
     */
    public void setProfile(ProfileTransfer profile) {
        this.profile = profile;
    }

    /**
     * Returns the userLogin.
     * @return the userLogin
     */
    public UserLoginTransfer getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the userLogin.
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(UserLoginTransfer userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Returns the recoveryAnswer.
     * @return the recoveryAnswer
     */
    public RecoveryAnswerTransfer getRecoveryAnswer() {
        return recoveryAnswer;
    }

    /**
     * Sets the recoveryAnswer.
     * @param recoveryAnswer the recoveryAnswer to set
     */
    public void setRecoveryAnswer(RecoveryAnswerTransfer recoveryAnswer) {
        this.recoveryAnswer = recoveryAnswer;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the partyAliases.
     * @return the partyAliases
     */
    public ListWrapper<PartyAliasTransfer> getPartyAliases() {
        return partyAliases;
    }

    /**
     * Sets the partyAliases.
     * @param partyAliases the partyAliases to set
     */
    public void setPartyAliases(ListWrapper<PartyAliasTransfer> partyAliases) {
        this.partyAliases = partyAliases;
    }

    /**
     * Returns the partyRelationships.
     * @return the partyRelationships
     */
    public ListWrapper<PartyRelationshipTransfer> getPartyRelationships() {
        return partyRelationships;
    }

    /**
     * Sets the partyRelationships.
     * @param partyRelationships the partyRelationships to set
     */
    public void setPartyRelationships(ListWrapper<PartyRelationshipTransfer> partyRelationships) {
        this.partyRelationships = partyRelationships;
    }

    /**
     * Returns the partyContactMechanisms.
     * @return the partyContactMechanisms
     */
    public ListWrapper<PartyContactMechanismTransfer> getPartyContactMechanisms() {
        return partyContactMechanisms;
    }

    /**
     * Sets the partyContactMechanisms.
     * @param partyContactMechanisms the partyContactMechanisms to set
     */
    public void setPartyContactMechanisms(ListWrapper<PartyContactMechanismTransfer> partyContactMechanisms) {
        this.partyContactMechanisms = partyContactMechanisms;
    }

    /**
     * Returns the partyContactLists.
     * @return the partyContactLists
     */
    public ListWrapper<PartyContactListTransfer> getPartyContactLists() {
        return partyContactLists;
    }

    /**
     * Sets the partyContactLists.
     * @param partyContactLists the partyContactLists to set
     */
    public void setPartyContactLists(ListWrapper<PartyContactListTransfer> partyContactLists) {
        this.partyContactLists = partyContactLists;
    }

    /**
     * Returns the billingAccounts.
     * @return the billingAccounts
     */
    public ListWrapper<BillingAccountTransfer> getBillingAccounts() {
        return billingAccounts;
    }

    /**
     * Sets the billingAccounts.
     * @param billingAccounts the billingAccounts to set
     */
    public void setBillingAccounts(ListWrapper<BillingAccountTransfer> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    /**
     * Returns the invoicesFromCount.
     * @return the invoicesFromCount
     */
    public Long getInvoicesFromCount() {
        return invoicesFromCount;
    }

    /**
     * Sets the invoicesFromCount.
     * @param invoicesFromCount the invoicesFromCount to set
     */
    public void setInvoicesFromCount(Long invoicesFromCount) {
        this.invoicesFromCount = invoicesFromCount;
    }

    /**
     * Returns the invoicesFrom.
     * @return the invoicesFrom
     */
    public ListWrapper<InvoiceTransfer> getInvoicesFrom() {
        return invoicesFrom;
    }

    /**
     * Sets the invoicesFrom.
     * @param invoicesFrom the invoicesFrom to set
     */
    public void setInvoicesFrom(ListWrapper<InvoiceTransfer> invoicesFrom) {
        this.invoicesFrom = invoicesFrom;
    }

    /**
     * Returns the invoicesToCount.
     * @return the invoicesToCount
     */
    public Long getInvoicesToCount() {
        return invoicesToCount;
    }

    /**
     * Sets the invoicesToCount.
     * @param invoicesToCount the invoicesToCount to set
     */
    public void setInvoicesToCount(Long invoicesToCount) {
        this.invoicesToCount = invoicesToCount;
    }

    /**
     * Returns the invoicesTo.
     * @return the invoicesTo
     */
    public ListWrapper<InvoiceTransfer> getInvoicesTo() {
        return invoicesTo;
    }

    /**
     * Sets the invoicesTo.
     * @param invoicesTo the invoicesTo to set
     */
    public void setInvoicesTo(ListWrapper<InvoiceTransfer> invoicesTo) {
        this.invoicesTo = invoicesTo;
    }

    /**
     * Returns the partyCreditLimits.
     * @return the partyCreditLimits
     */
    public ListWrapper<PartyCreditLimitTransfer> getPartyCreditLimits() {
        return partyCreditLimits;
    }

    /**
     * Sets the partyCreditLimits.
     * @param partyCreditLimits the partyCreditLimits to set
     */
    public void setPartyCreditLimits(ListWrapper<PartyCreditLimitTransfer> partyCreditLimits) {
        this.partyCreditLimits = partyCreditLimits;
    }

    /**
     * Returns the partyTerm.
     * @return the partyTerm
     */
    public PartyTermTransfer getPartyTerm() {
        return partyTerm;
    }

    /**
     * Sets the partyTerm.
     * @param partyTerm the partyTerm to set
     */
    public void setPartyTerm(PartyTermTransfer partyTerm) {
        this.partyTerm = partyTerm;
    }

    /**
     * Returns the partyFreeOnBoard.
     * @return the partyFreeOnBoard
     */
    public PartyFreeOnBoardTransfer getPartyFreeOnBoard() {
        return partyFreeOnBoard;
    }

    /**
     * Sets the partyFreeOnBoard.
     * @param partyFreeOnBoard the partyFreeOnBoard to set
     */
    public void setPartyFreeOnBoard(final PartyFreeOnBoardTransfer partyFreeOnBoard) {
        this.partyFreeOnBoard = partyFreeOnBoard;
    }

    /**
     * Returns the partyPaymentMethods.
     * @return the partyPaymentMethods
     */
    public ListWrapper<PartyPaymentMethodTransfer> getPartyPaymentMethods() {
        return partyPaymentMethods;
    }

    /**
     * Sets the partyPaymentMethods.
     * @param partyPaymentMethods the partyPaymentMethods to set
     */
    public void setPartyPaymentMethods(ListWrapper<PartyPaymentMethodTransfer> partyPaymentMethods) {
        this.partyPaymentMethods = partyPaymentMethods;
    }

    /**
     * Returns the cancellationPolicy.
     * @return the cancellationPolicy
     */
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    /**
     * Sets the cancellationPolicy.
     * @param cancellationPolicy the cancellationPolicy to set
     */
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * Returns the returnPolicy.
     * @return the returnPolicy
     */
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }

    /**
     * Sets the returnPolicy.
     * @param returnPolicy the returnPolicy to set
     */
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /**
     * Returns the partyCancellationPolicies.
     * @return the partyCancellationPolicies
     */
    public ListWrapper<PartyCancellationPolicyTransfer> getPartyCancellationPolicies() {
        return partyCancellationPolicies;
    }

    /**
     * Sets the partyCancellationPolicies.
     * @param partyCancellationPolicies the partyCancellationPolicies to set
     */
    public void setPartyCancellationPolicies(ListWrapper<PartyCancellationPolicyTransfer> partyCancellationPolicies) {
        this.partyCancellationPolicies = partyCancellationPolicies;
    }

    /**
     * Returns the partyReturnPolicies.
     * @return the partyReturnPolicies
     */
    public ListWrapper<PartyReturnPolicyTransfer> getPartyReturnPolicies() {
        return partyReturnPolicies;
    }

    /**
     * Sets the partyReturnPolicies.
     * @param partyReturnPolicies the partyReturnPolicies to set
     */
    public void setPartyReturnPolicies(ListWrapper<PartyReturnPolicyTransfer> partyReturnPolicies) {
        this.partyReturnPolicies = partyReturnPolicies;
    }

    /**
     * Returns the subscriptions.
     * @return the subscriptions
     */
    public ListWrapper<SubscriptionTransfer> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Sets the subscriptions.
     * @param subscriptions the subscriptions to set
     */
    public void setSubscriptions(ListWrapper<SubscriptionTransfer> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /**
     * Returns the communicationEventsCount.
     * @return the communicationEventsCount
     */
    public Long getCommunicationEventsCount() {
        return communicationEventsCount;
    }

    /**
     * Sets the communicationEventsCount.
     * @param communicationEventsCount the communicationEventsCount to set
     */
    public void setCommunicationEventsCount(Long communicationEventsCount) {
        this.communicationEventsCount = communicationEventsCount;
    }

    /**
     * Returns the communicationEvents.
     * @return the communicationEvents
     */
    public ListWrapper<CommunicationEventTransfer> getCommunicationEvents() {
        return communicationEvents;
    }

    /**
     * Sets the communicationEvents.
     * @param communicationEvents the communicationEvents to set
     */
    public void setCommunicationEvents(ListWrapper<CommunicationEventTransfer> communicationEvents) {
        this.communicationEvents = communicationEvents;
    }

    /**
     * Returns the partyDocuments.
     * @return the partyDocuments
     */
    public ListWrapper<PartyDocumentTransfer> getPartyDocuments() {
        return partyDocuments;
    }

    /**
     * Sets the partyDocuments.
     * @param partyDocuments the partyDocuments to set
     */
    public void setPartyDocuments(ListWrapper<PartyDocumentTransfer> partyDocuments) {
        this.partyDocuments = partyDocuments;
    }

    /**
     * Returns the partyPrinterGroupUses.
     * @return the partyPrinterGroupUses
     */
    public ListWrapper<PartyPrinterGroupUseTransfer> getPartyPrinterGroupUses() {
        return partyPrinterGroupUses;
    }

    /**
     * Sets the partyPrinterGroupUses.
     * @param partyPrinterGroupUses the partyPrinterGroupUses to set
     */
    public void setPartyPrinterGroupUses(ListWrapper<PartyPrinterGroupUseTransfer> partyPrinterGroupUses) {
        this.partyPrinterGroupUses = partyPrinterGroupUses;
    }

    /**
     * Returns the partyScaleUses.
     * @return the partyScaleUses
     */
    public ListWrapper<PartyScaleUseTransfer> getPartyScaleUses() {
        return partyScaleUses;
    }

    /**
     * Sets the partyScaleUses.
     * @param partyScaleUses the partyScaleUses to set
     */
    public void setPartyScaleUses(ListWrapper<PartyScaleUseTransfer> partyScaleUses) {
        this.partyScaleUses = partyScaleUses;
    }

    /**
     * Returns the partyEntityTypes.
     * @return the partyEntityTypes
     */
    public ListWrapper<PartyEntityTypeTransfer> getPartyEntityTypes() {
        return partyEntityTypes;
    }

    /**
     * Sets the partyEntityTypes.
     * @param partyEntityTypes the partyEntityTypes to set
     */
    public void setPartyEntityTypes(ListWrapper<PartyEntityTypeTransfer> partyEntityTypes) {
        this.partyEntityTypes = partyEntityTypes;
    }

    /**
     * Returns the partyApplicationEditorUses.
     * @return the partyApplicationEditorUses
     */
    public ListWrapper<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUses() {
        return partyApplicationEditorUses;
    }

    /**
     * Sets the partyApplicationEditorUses.
     * @param partyApplicationEditorUses the partyApplicationEditorUses to set
     */
    public void setPartyApplicationEditorUses(ListWrapper<PartyApplicationEditorUseTransfer> partyApplicationEditorUses) {
        this.partyApplicationEditorUses = partyApplicationEditorUses;
    }

    /**
     * Returns the partyCarriers.
     * @return the partyCarriers
     */
    public ListWrapper<PartyCarrierTransfer> getPartyCarriers() {
        return partyCarriers;
    }

    /**
     * Sets the partyCarriers.
     * @param partyCarriers the partyCarriers to set
     */
    public void setPartyCarriers(ListWrapper<PartyCarrierTransfer> partyCarriers) {
        this.partyCarriers = partyCarriers;
    }

    /**
     * Returns the partyCarrierAccounts.
     * @return the partyCarrierAccounts
     */
    public ListWrapper<PartyCarrierAccountTransfer> getPartyCarrierAccounts() {
        return partyCarrierAccounts;
    }

    /**
     * Sets the partyCarrierAccounts.
     * @param partyCarrierAccounts the partyCarrierAccounts to set
     */
    public void setPartyCarrierAccounts(ListWrapper<PartyCarrierAccountTransfer> partyCarrierAccounts) {
        this.partyCarrierAccounts = partyCarrierAccounts;
    }

    /**
     * Returns the employments.
     * @return the employments
     */
    public ListWrapper<EmploymentTransfer> getEmployments() {
        return employments;
    }

    /**
     * Sets the employments.
     * @param employments the employments to set
     */
    public void setEmployments(ListWrapper<EmploymentTransfer> employments) {
        this.employments = employments;
    }

    /**
     * Returns the leaves.
     * @return the leaves
     */
    public ListWrapper<LeaveTransfer> getLeaves() {
        return leaves;
    }

    /**
     * Sets the leaves.
     * @param leaves the leaves to set
     */
    public void setLeaves(ListWrapper<LeaveTransfer> leaves) {
        this.leaves = leaves;
    }

    /**
     * Returns the partyResponsibilities.
     * @return the partyResponsibilities
     */
    public ListWrapper<PartyResponsibilityTransfer> getPartyResponsibilities() {
        return partyResponsibilities;
    }

    /**
     * Sets the partyResponsibilities.
     * @param partyResponsibilities the partyResponsibilities to set
     */
    public void setPartyResponsibilities(ListWrapper<PartyResponsibilityTransfer> partyResponsibilities) {
        this.partyResponsibilities = partyResponsibilities;
    }

    /**
     * Returns the partyTrainingClasses.
     * @return the partyTrainingClasses
     */
    public ListWrapper<PartyTrainingClassTransfer> getPartyTrainingClasses() {
        return partyTrainingClasses;
    }

    /**
     * Sets the partyTrainingClasses.
     * @param partyTrainingClasses the partyTrainingClasses to set
     */
    public void setPartyTrainingClasses(ListWrapper<PartyTrainingClassTransfer> partyTrainingClasses) {
        this.partyTrainingClasses = partyTrainingClasses;
    }

    /**
     * Returns the partySkills.
     * @return the partySkills
     */
    public ListWrapper<PartySkillTransfer> getPartySkills() {
        return partySkills;
    }

    /**
     * Sets the partySkills.
     * @param partySkills the partySkills to set
     */
    public void setPartySkills(ListWrapper<PartySkillTransfer> partySkills) {
        this.partySkills = partySkills;
    }

}
