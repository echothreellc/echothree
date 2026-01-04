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

package com.echothree.model.control.vendor.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.item.common.transfer.ItemAliasTypeTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class VendorTransfer
        extends PartyTransfer {

    private String vendorName;
    private VendorTypeTransfer vendorType;
    private Integer minimumPurchaseOrderLines;
    private Integer maximumPurchaseOrderLines;
    private Long unformattedMinimumPurchaseOrderAmount;
    private String minimumPurchaseOrderAmount;
    private Long unformattedMaximumPurchaseOrderAmount;
    private String maximumPurchaseOrderAmount;
    private Boolean useItemPurchasingCategories;
    private ItemAliasTypeTransfer defaultItemAliasType;
    private GlAccountTransfer apGlAccount;
    private Boolean holdUntilComplete;
    private Boolean allowBackorders;
    private Boolean allowSubstitutions;
    private Boolean allowCombiningShipments;
    private Boolean requireReference;
    private Boolean allowReferenceDuplicates;
    private String referenceValidationPattern;
    private WorkflowEntityStatusTransfer vendorStatus;
    
    private Long vendorItemsCount;
    private ListWrapper<VendorItemTransfer> vendorItems;
    
    /** Creates a new instance of VendorTransfer */
    public VendorTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency,
            TimeZoneTransfer preferredTimeZone, DateTimeFormatTransfer preferredDateTimeFormat, PersonTransfer person, PartyGroupTransfer partyGroup,
            String vendorName, VendorTypeTransfer vendorType, Integer minimumPurchaseOrderLines, Integer maximumPurchaseOrderLines,
            Long unformattedMinimumPurchaseOrderAmount, String minimumPurchaseOrderAmount, Long unformattedMaximumPurchaseOrderAmount,
            String maximumPurchaseOrderAmount, Boolean useItemPurchasingCategories, ItemAliasTypeTransfer defaultItemAliasType,
            CancellationPolicyTransfer cancellationPolicy, ReturnPolicyTransfer returnPolicy, GlAccountTransfer apGlAccount, Boolean holdUntilComplete,
            Boolean allowBackorders, Boolean allowSubstitutions, Boolean allowCombiningShipments, Boolean requireReference, Boolean allowReferenceDuplicates,
            String referenceValidationPattern, WorkflowEntityStatusTransfer vendorStatus) {
        super(partyName, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, person, partyGroup, null);

        this.vendorName = vendorName;
        this.vendorType = vendorType;
        this.minimumPurchaseOrderLines = minimumPurchaseOrderLines;
        this.maximumPurchaseOrderLines = maximumPurchaseOrderLines;
        this.unformattedMinimumPurchaseOrderAmount = unformattedMinimumPurchaseOrderAmount;
        this.minimumPurchaseOrderAmount = minimumPurchaseOrderAmount;
        this.unformattedMaximumPurchaseOrderAmount = unformattedMaximumPurchaseOrderAmount;
        this.maximumPurchaseOrderAmount = maximumPurchaseOrderAmount;
        this.useItemPurchasingCategories = useItemPurchasingCategories;
        this.defaultItemAliasType = defaultItemAliasType;
        setCancellationPolicy(cancellationPolicy);
        setReturnPolicy(returnPolicy);
        this.apGlAccount = apGlAccount;
        this.holdUntilComplete = holdUntilComplete;
        this.allowBackorders = allowBackorders;
        this.allowSubstitutions = allowSubstitutions;
        this.allowCombiningShipments = allowCombiningShipments;
        this.requireReference = requireReference;
        this.allowReferenceDuplicates = allowReferenceDuplicates;
        this.referenceValidationPattern = referenceValidationPattern;
        this.vendorStatus = vendorStatus;
    }

    /**
     * Returns the vendorName.
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Sets the vendorName.
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Returns the vendorType.
     * @return the vendorType
     */
    public VendorTypeTransfer getVendorType() {
        return vendorType;
    }

    /**
     * Sets the vendorType.
     * @param vendorType the vendorType to set
     */
    public void setVendorType(VendorTypeTransfer vendorType) {
        this.vendorType = vendorType;
    }

    /**
     * Returns the minimumPurchaseOrderLines.
     * @return the minimumPurchaseOrderLines
     */
    public Integer getMinimumPurchaseOrderLines() {
        return minimumPurchaseOrderLines;
    }

    /**
     * Sets the minimumPurchaseOrderLines.
     * @param minimumPurchaseOrderLines the minimumPurchaseOrderLines to set
     */
    public void setMinimumPurchaseOrderLines(Integer minimumPurchaseOrderLines) {
        this.minimumPurchaseOrderLines = minimumPurchaseOrderLines;
    }

    /**
     * Returns the maximumPurchaseOrderLines.
     * @return the maximumPurchaseOrderLines
     */
    public Integer getMaximumPurchaseOrderLines() {
        return maximumPurchaseOrderLines;
    }

    /**
     * Sets the maximumPurchaseOrderLines.
     * @param maximumPurchaseOrderLines the maximumPurchaseOrderLines to set
     */
    public void setMaximumPurchaseOrderLines(Integer maximumPurchaseOrderLines) {
        this.maximumPurchaseOrderLines = maximumPurchaseOrderLines;
    }

    /**
     * Returns the unformattedMinimumPurchaseOrderAmount.
     * @return the unformattedMinimumPurchaseOrderAmount
     */
    public Long getUnformattedMinimumPurchaseOrderAmount() {
        return unformattedMinimumPurchaseOrderAmount;
    }

    /**
     * Sets the unformattedMinimumPurchaseOrderAmount.
     * @param unformattedMinimumPurchaseOrderAmount the unformattedMinimumPurchaseOrderAmount to set
     */
    public void setUnformattedMinimumPurchaseOrderAmount(Long unformattedMinimumPurchaseOrderAmount) {
        this.unformattedMinimumPurchaseOrderAmount = unformattedMinimumPurchaseOrderAmount;
    }

    /**
     * Returns the minimumPurchaseOrderAmount.
     * @return the minimumPurchaseOrderAmount
     */
    public String getMinimumPurchaseOrderAmount() {
        return minimumPurchaseOrderAmount;
    }

    /**
     * Sets the minimumPurchaseOrderAmount.
     * @param minimumPurchaseOrderAmount the minimumPurchaseOrderAmount to set
     */
    public void setMinimumPurchaseOrderAmount(String minimumPurchaseOrderAmount) {
        this.minimumPurchaseOrderAmount = minimumPurchaseOrderAmount;
    }

    /**
     * Returns the unformattedMaximumPurchaseOrderAmount.
     * @return the unformattedMaximumPurchaseOrderAmount
     */
    public Long getUnformattedMaximumPurchaseOrderAmount() {
        return unformattedMaximumPurchaseOrderAmount;
    }

    /**
     * Sets the unformattedMaximumPurchaseOrderAmount.
     * @param unformattedMaximumPurchaseOrderAmount the unformattedMaximumPurchaseOrderAmount to set
     */
    public void setUnformattedMaximumPurchaseOrderAmount(Long unformattedMaximumPurchaseOrderAmount) {
        this.unformattedMaximumPurchaseOrderAmount = unformattedMaximumPurchaseOrderAmount;
    }

    /**
     * Returns the maximumPurchaseOrderAmount.
     * @return the maximumPurchaseOrderAmount
     */
    public String getMaximumPurchaseOrderAmount() {
        return maximumPurchaseOrderAmount;
    }

    /**
     * Sets the maximumPurchaseOrderAmount.
     * @param maximumPurchaseOrderAmount the maximumPurchaseOrderAmount to set
     */
    public void setMaximumPurchaseOrderAmount(String maximumPurchaseOrderAmount) {
        this.maximumPurchaseOrderAmount = maximumPurchaseOrderAmount;
    }

    /**
     * Returns the useItemPurchasingCategories.
     * @return the useItemPurchasingCategories
     */
    public Boolean getUseItemPurchasingCategories() {
        return useItemPurchasingCategories;
    }

    /**
     * Sets the useItemPurchasingCategories.
     * @param useItemPurchasingCategories the useItemPurchasingCategories to set
     */
    public void setUseItemPurchasingCategories(Boolean useItemPurchasingCategories) {
        this.useItemPurchasingCategories = useItemPurchasingCategories;
    }

    /**
     * Returns the defaultItemAliasType.
     * @return the defaultItemAliasType
     */
    public ItemAliasTypeTransfer getDefaultItemAliasType() {
        return defaultItemAliasType;
    }

    /**
     * Sets the defaultItemAliasType.
     * @param defaultItemAliasType the defaultItemAliasType to set
     */
    public void setDefaultItemAliasType(ItemAliasTypeTransfer defaultItemAliasType) {
        this.defaultItemAliasType = defaultItemAliasType;
    }

    /**
     * Returns the apGlAccount.
     * @return the apGlAccount
     */
    public GlAccountTransfer getApGlAccount() {
        return apGlAccount;
    }

    /**
     * Sets the apGlAccount.
     * @param apGlAccount the apGlAccount to set
     */
    public void setApGlAccount(GlAccountTransfer apGlAccount) {
        this.apGlAccount = apGlAccount;
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
     * Returns the vendorStatus.
     * @return the vendorStatus
     */
    public WorkflowEntityStatusTransfer getVendorStatus() {
        return vendorStatus;
    }

    /**
     * Sets the vendorStatus.
     * @param vendorStatus the vendorStatus to set
     */
    public void setVendorStatus(WorkflowEntityStatusTransfer vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

    /**
     * Returns the vendorItemsCount.
     * @return the vendorItemsCount
     */
    public Long getVendorItemsCount() {
        return vendorItemsCount;
    }

    /**
     * Sets the vendorItemsCount.
     * @param vendorItemsCount the vendorItemsCount to set
     */
    public void setVendorItemsCount(Long vendorItemsCount) {
        this.vendorItemsCount = vendorItemsCount;
    }

    /**
     * Returns the vendorItems.
     * @return the vendorItems
     */
    public ListWrapper<VendorItemTransfer> getVendorItems() {
        return vendorItems;
    }

    /**
     * Sets the vendorItems.
     * @param vendorItems the vendorItems to set
     */
    public void setVendorItems(ListWrapper<VendorItemTransfer> vendorItems) {
        this.vendorItems = vendorItems;
    }

}
