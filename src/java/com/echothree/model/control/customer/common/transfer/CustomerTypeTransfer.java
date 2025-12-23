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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.inventory.common.transfer.AllocationPriorityTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.shipment.common.transfer.FreeOnBoardTransfer;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CustomerTypeTransfer
        extends BaseTransfer {

    private String customerTypeName;
    private SequenceTransfer customerSequence;
    private OfferUseTransfer defaultOfferUse;
    private TermTransfer defaultTerm;
    private FreeOnBoardTransfer defaultFreeOnBoardTransfer;
    private CancellationPolicyTransfer defaultCancellationPolicy;
    private ReturnPolicyTransfer defaultReturnPolicy;
    private GlAccountTransfer defaultArGlAccount;
    private Boolean defaultHoldUntilComplete;
    private Boolean defaultAllowBackorders;
    private Boolean defaultAllowSubstitutions;
    private Boolean defaultAllowCombiningShipments;
    private Boolean defaultRequireReference;
    private Boolean defaultAllowReferenceDuplicates;
    private String defaultReferenceValidationPattern;
    private Boolean defaultTaxable;
    private AllocationPriorityTransfer allocationPriority;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    /** Creates a new instance of CustomerTypeTransfer */
    public CustomerTypeTransfer(String customerTypeName, SequenceTransfer customerSequence, OfferUseTransfer defaultOfferUse, TermTransfer defaultTerm,
            FreeOnBoardTransfer defaultFreeOnBoardTransfer, CancellationPolicyTransfer defaultCancellationPolicy, ReturnPolicyTransfer defaultReturnPolicy, GlAccountTransfer defaultArGlAccount,
            Boolean defaultHoldUntilComplete, Boolean defaultAllowBackorders, Boolean defaultAllowSubstitutions, Boolean defaultAllowCombiningShipments,
            Boolean defaultRequireReference, Boolean defaultAllowReferenceDuplicates, String defaultReferenceValidationPattern, Boolean defaultTaxable,
            AllocationPriorityTransfer allocationPriority, Boolean isDefault, Integer sortOrder, String description) {
        this.customerTypeName = customerTypeName;
        this.customerSequence = customerSequence;
        this.defaultOfferUse = defaultOfferUse;
        this.defaultTerm = defaultTerm;
        this.defaultFreeOnBoardTransfer = defaultFreeOnBoardTransfer;
        this.defaultCancellationPolicy = defaultCancellationPolicy;
        this.defaultReturnPolicy = defaultReturnPolicy;
        this.defaultArGlAccount = defaultArGlAccount;
        this.defaultHoldUntilComplete = defaultHoldUntilComplete;
        this.defaultAllowBackorders = defaultAllowBackorders;
        this.defaultAllowSubstitutions = defaultAllowSubstitutions;
        this.defaultAllowCombiningShipments = defaultAllowCombiningShipments;
        this.defaultRequireReference = defaultRequireReference;
        this.defaultAllowReferenceDuplicates = defaultAllowReferenceDuplicates;
        this.defaultReferenceValidationPattern = defaultReferenceValidationPattern;
        this.defaultTaxable = defaultTaxable;
        this.allocationPriority = allocationPriority;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the customerTypeName.
     * @return the customerTypeName
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * Sets the customerTypeName.
     * @param customerTypeName the customerTypeName to set
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    /**
     * Returns the customerSequence.
     * @return the customerSequence
     */
    public SequenceTransfer getCustomerSequence() {
        return customerSequence;
    }

    /**
     * Sets the customerSequence.
     * @param customerSequence the customerSequence to set
     */
    public void setCustomerSequence(SequenceTransfer customerSequence) {
        this.customerSequence = customerSequence;
    }

    /**
     * Returns the defaultOfferUse.
     * @return the defaultOfferUse
     */
    public OfferUseTransfer getDefaultOfferUse() {
        return defaultOfferUse;
    }

    /**
     * Sets the defaultOfferUse.
     * @param defaultOfferUse the defaultOfferUse to set
     */
    public void setDefaultOfferUse(OfferUseTransfer defaultOfferUse) {
        this.defaultOfferUse = defaultOfferUse;
    }

    /**
     * Returns the defaultTerm.
     * @return the defaultTerm
     */
    public TermTransfer getDefaultTerm() {
        return defaultTerm;
    }

    /**
     * Sets the defaultTerm.
     * @param defaultTerm the defaultTerm to set
     */
    public void setDefaultTerm(TermTransfer defaultTerm) {
        this.defaultTerm = defaultTerm;
    }

    public FreeOnBoardTransfer getDefaultFreeOnBoardTransfer() {
        return defaultFreeOnBoardTransfer;
    }

    public void setDefaultFreeOnBoardTransfer(final FreeOnBoardTransfer defaultFreeOnBoardTransfer) {
        this.defaultFreeOnBoardTransfer = defaultFreeOnBoardTransfer;
    }

    /**
     * Returns the defaultCancellationPolicy.
     * @return the defaultCancellationPolicy
     */
    public CancellationPolicyTransfer getDefaultCancellationPolicy() {
        return defaultCancellationPolicy;
    }

    /**
     * Sets the defaultCancellationPolicy.
     * @param defaultCancellationPolicy the defaultCancellationPolicy to set
     */
    public void setDefaultCancellationPolicy(CancellationPolicyTransfer defaultCancellationPolicy) {
        this.defaultCancellationPolicy = defaultCancellationPolicy;
    }

    /**
     * Returns the defaultReturnPolicy.
     * @return the defaultReturnPolicy
     */
    public ReturnPolicyTransfer getDefaultReturnPolicy() {
        return defaultReturnPolicy;
    }

    /**
     * Sets the defaultReturnPolicy.
     * @param defaultReturnPolicy the defaultReturnPolicy to set
     */
    public void setDefaultReturnPolicy(ReturnPolicyTransfer defaultReturnPolicy) {
        this.defaultReturnPolicy = defaultReturnPolicy;
    }

    /**
     * Returns the defaultArGlAccount.
     * @return the defaultArGlAccount
     */
    public GlAccountTransfer getDefaultArGlAccount() {
        return defaultArGlAccount;
    }

    /**
     * Sets the defaultArGlAccount.
     * @param defaultArGlAccount the defaultArGlAccount to set
     */
    public void setDefaultArGlAccount(GlAccountTransfer defaultArGlAccount) {
        this.defaultArGlAccount = defaultArGlAccount;
    }

    /**
     * Returns the defaultHoldUntilComplete.
     * @return the defaultHoldUntilComplete
     */
    public Boolean getDefaultHoldUntilComplete() {
        return defaultHoldUntilComplete;
    }

    /**
     * Sets the defaultHoldUntilComplete.
     * @param defaultHoldUntilComplete the defaultHoldUntilComplete to set
     */
    public void setDefaultHoldUntilComplete(Boolean defaultHoldUntilComplete) {
        this.defaultHoldUntilComplete = defaultHoldUntilComplete;
    }

    /**
     * Returns the defaultAllowBackorders.
     * @return the defaultAllowBackorders
     */
    public Boolean getDefaultAllowBackorders() {
        return defaultAllowBackorders;
    }

    /**
     * Sets the defaultAllowBackorders.
     * @param defaultAllowBackorders the defaultAllowBackorders to set
     */
    public void setDefaultAllowBackorders(Boolean defaultAllowBackorders) {
        this.defaultAllowBackorders = defaultAllowBackorders;
    }

    /**
     * Returns the defaultAllowSubstitutions.
     * @return the defaultAllowSubstitutions
     */
    public Boolean getDefaultAllowSubstitutions() {
        return defaultAllowSubstitutions;
    }

    /**
     * Sets the defaultAllowSubstitutions.
     * @param defaultAllowSubstitutions the defaultAllowSubstitutions to set
     */
    public void setDefaultAllowSubstitutions(Boolean defaultAllowSubstitutions) {
        this.defaultAllowSubstitutions = defaultAllowSubstitutions;
    }

    /**
     * Returns the defaultAllowCombiningShipments.
     * @return the defaultAllowCombiningShipments
     */
    public Boolean getDefaultAllowCombiningShipments() {
        return defaultAllowCombiningShipments;
    }

    /**
     * Sets the defaultAllowCombiningShipments.
     * @param defaultAllowCombiningShipments the defaultAllowCombiningShipments to set
     */
    public void setDefaultAllowCombiningShipments(Boolean defaultAllowCombiningShipments) {
        this.defaultAllowCombiningShipments = defaultAllowCombiningShipments;
    }

    /**
     * Returns the defaultRequireReference.
     * @return the defaultRequireReference
     */
    public Boolean getDefaultRequireReference() {
        return defaultRequireReference;
    }

    /**
     * Sets the defaultRequireReference.
     * @param defaultRequireReference the defaultRequireReference to set
     */
    public void setDefaultRequireReference(Boolean defaultRequireReference) {
        this.defaultRequireReference = defaultRequireReference;
    }

    /**
     * Returns the defaultAllowReferenceDuplicates.
     * @return the defaultAllowReferenceDuplicates
     */
    public Boolean getDefaultAllowReferenceDuplicates() {
        return defaultAllowReferenceDuplicates;
    }

    /**
     * Sets the defaultAllowReferenceDuplicates.
     * @param defaultAllowReferenceDuplicates the defaultAllowReferenceDuplicates to set
     */
    public void setDefaultAllowReferenceDuplicates(Boolean defaultAllowReferenceDuplicates) {
        this.defaultAllowReferenceDuplicates = defaultAllowReferenceDuplicates;
    }

    /**
     * Returns the defaultReferenceValidationPattern.
     * @return the defaultReferenceValidationPattern
     */
    public String getDefaultReferenceValidationPattern() {
        return defaultReferenceValidationPattern;
    }

    /**
     * Sets the defaultReferenceValidationPattern.
     * @param defaultReferenceValidationPattern the defaultReferenceValidationPattern to set
     */
    public void setDefaultReferenceValidationPattern(String defaultReferenceValidationPattern) {
        this.defaultReferenceValidationPattern = defaultReferenceValidationPattern;
    }

    /**
     * Returns the defaultTaxable.
     * @return the defaultTaxable
     */
    public Boolean getDefaultTaxable() {
        return defaultTaxable;
    }

    /**
     * Sets the defaultTaxable.
     * @param defaultTaxable the defaultTaxable to set
     */
    public void setDefaultTaxable(Boolean defaultTaxable) {
        this.defaultTaxable = defaultTaxable;
    }

    /**
     * Returns the allocationPriority.
     * @return the allocationPriority
     */
    public AllocationPriorityTransfer getAllocationPriority() {
        return allocationPriority;
    }

    /**
     * Sets the allocationPriority.
     * @param allocationPriority the allocationPriority to set
     */
    public void setAllocationPriority(AllocationPriorityTransfer allocationPriority) {
        this.allocationPriority = allocationPriority;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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

}