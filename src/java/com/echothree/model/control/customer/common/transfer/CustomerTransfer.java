// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.customer.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;

public class CustomerTransfer
        extends PartyTransfer {
    
    private String customerName;
    private CustomerTypeTransfer customerType;
    private OfferUseTransfer initialOfferUse;
    private GlAccountTransfer arGlAccount;
    private Boolean holdUntilComplete;
    private Boolean allowBackorders;
    private Boolean allowSubstitutions;
    private Boolean allowCombiningShipments;
    private Boolean requireReference;
    private Boolean allowReferenceDuplicates;
    private String referenceValidationPattern;
    private WorkflowEntityStatusTransfer customerStatus;
    private WorkflowEntityStatusTransfer customerCreditStatus;
    
    /** Creates a new instance of CustomerTransfer */
    public CustomerTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency,
            TimeZoneTransfer preferredTimeZone, DateTimeFormatTransfer preferredDateTimeFormat, PersonTransfer person, PartyGroupTransfer partyGroup,
            ProfileTransfer profileTransfer, String customerName, CustomerTypeTransfer customerType, OfferUseTransfer initialOfferUse,
            CancellationPolicyTransfer cancellationPolicy, ReturnPolicyTransfer returnPolicy, GlAccountTransfer arGlAccount, Boolean holdUntilComplete,
            Boolean allowBackorders, Boolean allowSubstitutions, Boolean allowCombiningShipments, Boolean requireReference, Boolean allowReferenceDuplicates,
            String referenceValidationPattern, WorkflowEntityStatusTransfer customerStatus, WorkflowEntityStatusTransfer customerCreditStatus) {
        super(partyName, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, person, partyGroup, profileTransfer);
        
        this.customerName = customerName;
        this.customerType = customerType;
        this.initialOfferUse = initialOfferUse;
        setCancellationPolicy(cancellationPolicy);
        setReturnPolicy(returnPolicy);
        this.arGlAccount = arGlAccount;
        this.holdUntilComplete = holdUntilComplete;
        this.allowBackorders = allowBackorders;
        this.allowSubstitutions = allowSubstitutions;
        this.allowCombiningShipments = allowCombiningShipments;
        this.requireReference = requireReference;
        this.allowReferenceDuplicates = allowReferenceDuplicates;
        this.referenceValidationPattern = referenceValidationPattern;
        this.customerStatus = customerStatus;
        this.customerCreditStatus = customerCreditStatus;
    }

    /**
     * Returns the customerName.
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customerName.
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the customerType.
     * @return the customerType
     */
    public CustomerTypeTransfer getCustomerType() {
        return customerType;
    }

    /**
     * Sets the customerType.
     * @param customerType the customerType to set
     */
    public void setCustomerType(CustomerTypeTransfer customerType) {
        this.customerType = customerType;
    }

    /**
     * Returns the initialOfferUse.
     * @return the initialOfferUse
     */
    public OfferUseTransfer getInitialOfferUse() {
        return initialOfferUse;
    }

    /**
     * Sets the initialOfferUse.
     * @param initialOfferUse the initialOfferUse to set
     */
    public void setInitialOfferUse(OfferUseTransfer initialOfferUse) {
        this.initialOfferUse = initialOfferUse;
    }

    /**
     * Returns the arGlAccount.
     * @return the arGlAccount
     */
    public GlAccountTransfer getArGlAccount() {
        return arGlAccount;
    }

    /**
     * Sets the arGlAccount.
     * @param arGlAccount the arGlAccount to set
     */
    public void setArGlAccount(GlAccountTransfer arGlAccount) {
        this.arGlAccount = arGlAccount;
    }

    /**
     * Returns the holdUntilComplete.
     * @return the holdUntilComplete
     */
    public Boolean getHoldUntilComplete() {
        return holdUntilComplete;
    }

    /**
     * Sets the holdUntilComplete.
     * @param holdUntilComplete the holdUntilComplete to set
     */
    public void setHoldUntilComplete(Boolean holdUntilComplete) {
        this.holdUntilComplete = holdUntilComplete;
    }

    /**
     * Returns the allowBackorders.
     * @return the allowBackorders
     */
    public Boolean getAllowBackorders() {
        return allowBackorders;
    }

    /**
     * Sets the allowBackorders.
     * @param allowBackorders the allowBackorders to set
     */
    public void setAllowBackorders(Boolean allowBackorders) {
        this.allowBackorders = allowBackorders;
    }

    /**
     * Returns the allowSubstitutions.
     * @return the allowSubstitutions
     */
    public Boolean getAllowSubstitutions() {
        return allowSubstitutions;
    }

    /**
     * Sets the allowSubstitutions.
     * @param allowSubstitutions the allowSubstitutions to set
     */
    public void setAllowSubstitutions(Boolean allowSubstitutions) {
        this.allowSubstitutions = allowSubstitutions;
    }

    /**
     * Returns the allowCombiningShipments.
     * @return the allowCombiningShipments
     */
    public Boolean getAllowCombiningShipments() {
        return allowCombiningShipments;
    }

    /**
     * Sets the allowCombiningShipments.
     * @param allowCombiningShipments the allowCombiningShipments to set
     */
    public void setAllowCombiningShipments(Boolean allowCombiningShipments) {
        this.allowCombiningShipments = allowCombiningShipments;
    }

    /**
     * Returns the requireReference.
     * @return the requireReference
     */
    public Boolean getRequireReference() {
        return requireReference;
    }

    /**
     * Sets the requireReference.
     * @param requireReference the requireReference to set
     */
    public void setRequireReference(Boolean requireReference) {
        this.requireReference = requireReference;
    }

    /**
     * Returns the allowReferenceDuplicates.
     * @return the allowReferenceDuplicates
     */
    public Boolean getAllowReferenceDuplicates() {
        return allowReferenceDuplicates;
    }

    /**
     * Sets the allowReferenceDuplicates.
     * @param allowReferenceDuplicates the allowReferenceDuplicates to set
     */
    public void setAllowReferenceDuplicates(Boolean allowReferenceDuplicates) {
        this.allowReferenceDuplicates = allowReferenceDuplicates;
    }

    /**
     * Returns the referenceValidationPattern.
     * @return the referenceValidationPattern
     */
    public String getReferenceValidationPattern() {
        return referenceValidationPattern;
    }

    /**
     * Sets the referenceValidationPattern.
     * @param referenceValidationPattern the referenceValidationPattern to set
     */
    public void setReferenceValidationPattern(String referenceValidationPattern) {
        this.referenceValidationPattern = referenceValidationPattern;
    }

    /**
     * Returns the customerStatus.
     * @return the customerStatus
     */
    public WorkflowEntityStatusTransfer getCustomerStatus() {
        return customerStatus;
    }

    /**
     * Sets the customerStatus.
     * @param customerStatus the customerStatus to set
     */
    public void setCustomerStatus(WorkflowEntityStatusTransfer customerStatus) {
        this.customerStatus = customerStatus;
    }

    /**
     * Returns the customerCreditStatus.
     * @return the customerCreditStatus
     */
    public WorkflowEntityStatusTransfer getCustomerCreditStatus() {
        return customerCreditStatus;
    }

    /**
     * Sets the customerCreditStatus.
     * @param customerCreditStatus the customerCreditStatus to set
     */
    public void setCustomerCreditStatus(WorkflowEntityStatusTransfer customerCreditStatus) {
        this.customerCreditStatus = customerCreditStatus;
    }

}
