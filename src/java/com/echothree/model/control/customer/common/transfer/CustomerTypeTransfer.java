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

package com.echothree.model.control.customer.common.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.inventory.common.transfer.AllocationPriorityTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CustomerTypeTransfer
        extends BaseTransfer {

    private String customerTypeName;
    private SequenceTransfer customerSequence;
    private OfferUseTransfer defaultOfferUse;
    private TermTransfer defaultTerm;
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
            CancellationPolicyTransfer defaultCancellationPolicy, ReturnPolicyTransfer defaultReturnPolicy, GlAccountTransfer defaultArGlAccount,
            Boolean defaultHoldUntilComplete, Boolean defaultAllowBackorders, Boolean defaultAllowSubstitutions, Boolean defaultAllowCombiningShipments,
            Boolean defaultRequireReference, Boolean defaultAllowReferenceDuplicates, String defaultReferenceValidationPattern, Boolean defaultTaxable,
            AllocationPriorityTransfer allocationPriority, Boolean isDefault, Integer sortOrder, String description) {
        this.customerTypeName = customerTypeName;
        this.customerSequence = customerSequence;
        this.defaultOfferUse = defaultOfferUse;
        this.defaultTerm = defaultTerm;
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
     * @return the customerTypeName
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * @param customerTypeName the customerTypeName to set
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    /**
     * @return the customerSequence
     */
    public SequenceTransfer getCustomerSequence() {
        return customerSequence;
    }

    /**
     * @param customerSequence the customerSequence to set
     */
    public void setCustomerSequence(SequenceTransfer customerSequence) {
        this.customerSequence = customerSequence;
    }

    /**
     * @return the defaultOfferUse
     */
    public OfferUseTransfer getDefaultOfferUse() {
        return defaultOfferUse;
    }

    /**
     * @param defaultOfferUse the defaultOfferUse to set
     */
    public void setDefaultOfferUse(OfferUseTransfer defaultOfferUse) {
        this.defaultOfferUse = defaultOfferUse;
    }

    /**
     * @return the defaultTerm
     */
    public TermTransfer getDefaultTerm() {
        return defaultTerm;
    }

    /**
     * @param defaultTerm the defaultTerm to set
     */
    public void setDefaultTerm(TermTransfer defaultTerm) {
        this.defaultTerm = defaultTerm;
    }

    /**
     * @return the defaultCancellationPolicy
     */
    public CancellationPolicyTransfer getDefaultCancellationPolicy() {
        return defaultCancellationPolicy;
    }

    /**
     * @param defaultCancellationPolicy the defaultCancellationPolicy to set
     */
    public void setDefaultCancellationPolicy(CancellationPolicyTransfer defaultCancellationPolicy) {
        this.defaultCancellationPolicy = defaultCancellationPolicy;
    }

    /**
     * @return the defaultReturnPolicy
     */
    public ReturnPolicyTransfer getDefaultReturnPolicy() {
        return defaultReturnPolicy;
    }

    /**
     * @param defaultReturnPolicy the defaultReturnPolicy to set
     */
    public void setDefaultReturnPolicy(ReturnPolicyTransfer defaultReturnPolicy) {
        this.defaultReturnPolicy = defaultReturnPolicy;
    }

    /**
     * @return the defaultArGlAccount
     */
    public GlAccountTransfer getDefaultArGlAccount() {
        return defaultArGlAccount;
    }

    /**
     * @param defaultArGlAccount the defaultArGlAccount to set
     */
    public void setDefaultArGlAccount(GlAccountTransfer defaultArGlAccount) {
        this.defaultArGlAccount = defaultArGlAccount;
    }

    /**
     * @return the defaultHoldUntilComplete
     */
    public Boolean getDefaultHoldUntilComplete() {
        return defaultHoldUntilComplete;
    }

    /**
     * @param defaultHoldUntilComplete the defaultHoldUntilComplete to set
     */
    public void setDefaultHoldUntilComplete(Boolean defaultHoldUntilComplete) {
        this.defaultHoldUntilComplete = defaultHoldUntilComplete;
    }

    /**
     * @return the defaultAllowBackorders
     */
    public Boolean getDefaultAllowBackorders() {
        return defaultAllowBackorders;
    }

    /**
     * @param defaultAllowBackorders the defaultAllowBackorders to set
     */
    public void setDefaultAllowBackorders(Boolean defaultAllowBackorders) {
        this.defaultAllowBackorders = defaultAllowBackorders;
    }

    /**
     * @return the defaultAllowSubstitutions
     */
    public Boolean getDefaultAllowSubstitutions() {
        return defaultAllowSubstitutions;
    }

    /**
     * @param defaultAllowSubstitutions the defaultAllowSubstitutions to set
     */
    public void setDefaultAllowSubstitutions(Boolean defaultAllowSubstitutions) {
        this.defaultAllowSubstitutions = defaultAllowSubstitutions;
    }

    /**
     * @return the defaultAllowCombiningShipments
     */
    public Boolean getDefaultAllowCombiningShipments() {
        return defaultAllowCombiningShipments;
    }

    /**
     * @param defaultAllowCombiningShipments the defaultAllowCombiningShipments to set
     */
    public void setDefaultAllowCombiningShipments(Boolean defaultAllowCombiningShipments) {
        this.defaultAllowCombiningShipments = defaultAllowCombiningShipments;
    }

    /**
     * @return the defaultRequireReference
     */
    public Boolean getDefaultRequireReference() {
        return defaultRequireReference;
    }

    /**
     * @param defaultRequireReference the defaultRequireReference to set
     */
    public void setDefaultRequireReference(Boolean defaultRequireReference) {
        this.defaultRequireReference = defaultRequireReference;
    }

    /**
     * @return the defaultAllowReferenceDuplicates
     */
    public Boolean getDefaultAllowReferenceDuplicates() {
        return defaultAllowReferenceDuplicates;
    }

    /**
     * @param defaultAllowReferenceDuplicates the defaultAllowReferenceDuplicates to set
     */
    public void setDefaultAllowReferenceDuplicates(Boolean defaultAllowReferenceDuplicates) {
        this.defaultAllowReferenceDuplicates = defaultAllowReferenceDuplicates;
    }

    /**
     * @return the defaultReferenceValidationPattern
     */
    public String getDefaultReferenceValidationPattern() {
        return defaultReferenceValidationPattern;
    }

    /**
     * @param defaultReferenceValidationPattern the defaultReferenceValidationPattern to set
     */
    public void setDefaultReferenceValidationPattern(String defaultReferenceValidationPattern) {
        this.defaultReferenceValidationPattern = defaultReferenceValidationPattern;
    }

    /**
     * @return the defaultTaxable
     */
    public Boolean getDefaultTaxable() {
        return defaultTaxable;
    }

    /**
     * @param defaultTaxable the defaultTaxable to set
     */
    public void setDefaultTaxable(Boolean defaultTaxable) {
        this.defaultTaxable = defaultTaxable;
    }

    /**
     * @return the allocationPriority
     */
    public AllocationPriorityTransfer getAllocationPriority() {
        return allocationPriority;
    }

    /**
     * @param allocationPriority the allocationPriority to set
     */
    public void setAllocationPriority(AllocationPriorityTransfer allocationPriority) {
        this.allocationPriority = allocationPriority;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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

}