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

package com.echothree.model.control.vendor.common.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class VendorTypeTransfer
        extends BaseTransfer {
    
    private String vendorTypeName;
    private CancellationPolicyTransfer defaultCancellationPolicy;
    private ReturnPolicyTransfer defaultReturnPolicy;
    private GlAccountTransfer defaultApGlAccount;
    private Boolean defaultHoldUntilComplete;
    private Boolean defaultAllowBackorders;
    private Boolean defaultAllowSubstitutions;
    private Boolean defaultAllowCombiningShipments;
    private Boolean defaultRequireReference;
    private Boolean defaultAllowReferenceDuplicates;
    private String defaultReferenceValidationPattern;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of VendorTypeTransfer */
    public VendorTypeTransfer(String vendorTypeName, CancellationPolicyTransfer defaultCancellationPolicy, ReturnPolicyTransfer defaultReturnPolicy,
            GlAccountTransfer defaultApGlAccount, Boolean defaultHoldUntilComplete, Boolean defaultAllowBackorders, Boolean defaultAllowSubstitutions,
            Boolean defaultAllowCombiningShipments, Boolean defaultRequireReference, Boolean defaultAllowReferenceDuplicates,
            String defaultReferenceValidationPattern, Boolean isDefault, Integer sortOrder, String description) {
        this.vendorTypeName = vendorTypeName;
        this.defaultCancellationPolicy = defaultCancellationPolicy;
        this.defaultReturnPolicy = defaultReturnPolicy;
        this.defaultApGlAccount = defaultApGlAccount;
        this.defaultHoldUntilComplete = defaultHoldUntilComplete;
        this.defaultAllowBackorders = defaultAllowBackorders;
        this.defaultAllowSubstitutions = defaultAllowSubstitutions;
        this.defaultAllowCombiningShipments = defaultAllowCombiningShipments;
        this.defaultRequireReference = defaultRequireReference;
        this.defaultAllowReferenceDuplicates = defaultAllowReferenceDuplicates;
        this.defaultReferenceValidationPattern = defaultReferenceValidationPattern;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the vendorTypeName
     */
    public String getVendorTypeName() {
        return vendorTypeName;
    }

    /**
     * @param vendorTypeName the vendorTypeName to set
     */
    public void setVendorTypeName(String vendorTypeName) {
        this.vendorTypeName = vendorTypeName;
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
     * @return the defaultApGlAccount
     */
    public GlAccountTransfer getDefaultApGlAccount() {
        return defaultApGlAccount;
    }

    /**
     * @param defaultApGlAccount the defaultApGlAccount to set
     */
    public void setDefaultApGlAccount(GlAccountTransfer defaultApGlAccount) {
        this.defaultApGlAccount = defaultApGlAccount;
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
