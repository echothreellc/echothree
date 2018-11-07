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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.offer.common.transfer.OfferItemTransfer;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.tax.common.transfer.ItemTaxClassificationTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindTransfer;
import com.echothree.model.control.vendor.common.transfer.ItemPurchasingCategoryTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorItemTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;

public class ItemTransfer
        extends BaseTransfer {
    
    private String itemName;
    private ItemTypeTransfer itemType;
    private ItemUseTypeTransfer itemUseType;
    private ItemCategoryTransfer itemCategory;
    private ItemAccountingCategoryTransfer itemAccountingCategory;
    private ItemPurchasingCategoryTransfer itemPurchasingCategory;
    private CompanyTransfer company;
    private ItemDeliveryTypeTransfer itemDeliveryType;
    private ItemInventoryTypeTransfer itemInventoryType;
    private Boolean inventorySerialized;
    private SequenceTransfer serialNumberSequence;
    private Boolean shippingChargeExempt;
    private Long unformattedShippingStartTime;
    private String shippingStartTime;
    private Long unformattedShippingEndTime;
    private String shippingEndTime;
    private Long unformattedSalesOrderStartTime;
    private String salesOrderStartTime;
    private Long unformattedSalesOrderEndTime;
    private String salesOrderEndTime;
    private Long unformattedPurchaseOrderStartTime;
    private String purchaseOrderStartTime;
    private Long unformattedPurchaseOrderEndTime;
    private String purchaseOrderEndTime;
    private Boolean allowClubDiscounts;
    private Boolean allowCouponDiscounts;
    private Boolean allowAssociatePayments;
    private UnitOfMeasureKindTransfer unitOfMeasureKind;
    private ItemPriceTypeTransfer itemPriceType;
    private CancellationPolicyTransfer cancellationPolicy;
    private ReturnPolicyTransfer returnPolicy;
    private String description;
    private WorkflowEntityStatusTransfer itemStatus;
    
    private ListWrapper<ItemShippingTimeTransfer> itemShippingTimes;
    private ListWrapper<ItemAliasTransfer> itemAliases;
    private ListWrapper<ItemPriceTransfer> itemPrices;
    private ListWrapper<ItemUnitOfMeasureTypeTransfer> itemUnitOfMeasureTypes;
    private ListWrapper<ItemDescriptionTransfer> itemDescriptions;
    private ListWrapper<ItemVolumeTransfer> itemVolumes;
    private ListWrapper<ItemWeightTransfer> itemWeights;
    private ListWrapper<OfferItemTransfer> offerItems;
    private ListWrapper<VendorItemTransfer> vendorItems;
    private MapWrapper<ItemCountryOfOriginTransfer> itemCountryOfOrigins;
    private ListWrapper<ItemKitMemberTransfer> itemKitMembers;
    private ListWrapper<ItemPackCheckRequirementTransfer> itemPackCheckRequirements;
    private ListWrapper<ItemUnitCustomerTypeLimitTransfer> itemUnitCustomerTypeLimits;
    private ListWrapper<ItemUnitLimitTransfer> itemUnitLimits;
    private ListWrapper<ItemUnitPriceLimitTransfer> itemUnitPriceLimits;
    private MapWrapper<ListWrapper<RelatedItemTransfer>> relatedItems;
    private ListWrapper<ItemHarmonizedTariffScheduleCodeTransfer> itemHarmonizedTariffScheduleCodes;
    private ListWrapper<ItemTaxClassificationTransfer> itemTaxClassifications;
    
    /** Creates a new instance of ItemTransfer */
    public ItemTransfer(String itemName, ItemTypeTransfer itemType, ItemUseTypeTransfer itemUseType, ItemCategoryTransfer itemCategory,
            ItemAccountingCategoryTransfer itemAccountingCategory, ItemPurchasingCategoryTransfer itemPurchasingCategory, CompanyTransfer company,
            ItemDeliveryTypeTransfer itemDeliveryType, ItemInventoryTypeTransfer itemInventoryType, Boolean inventorySerialized,
            SequenceTransfer serialNumberSequence, Boolean shippingChargeExempt, Long unformattedShippingStartTime, String shippingStartTime,
            Long unformattedShippingEndTime, String shippingEndTime, Long unformattedSalesOrderStartTime, String salesOrderStartTime,
            Long unformattedSalesOrderEndTime, String salesOrderEndTime, Long unformattedPurchaseOrderStartTime, String purchaseOrderStartTime,
            Long unformattedPurchaseOrderEndTime, String purchaseOrderEndTime, Boolean allowClubDiscounts, Boolean allowCouponDiscounts,
            Boolean allowAssociatePayments, UnitOfMeasureKindTransfer unitOfMeasureKind, ItemPriceTypeTransfer itemPriceType,
            CancellationPolicyTransfer cancellationPolicy, ReturnPolicyTransfer returnPolicy, String description, WorkflowEntityStatusTransfer itemStatus) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemUseType = itemUseType;
        this.itemCategory = itemCategory;
        this.itemAccountingCategory = itemAccountingCategory;
        this.itemPurchasingCategory = itemPurchasingCategory;
        this.company = company;
        this.itemDeliveryType = itemDeliveryType;
        this.itemInventoryType = itemInventoryType;
        this.inventorySerialized = inventorySerialized;
        this.serialNumberSequence = serialNumberSequence;
        this.shippingChargeExempt = shippingChargeExempt;
        this.unformattedShippingStartTime = unformattedShippingStartTime;
        this.shippingStartTime = shippingStartTime;
        this.unformattedShippingEndTime = unformattedShippingEndTime;
        this.shippingEndTime = shippingEndTime;
        this.unformattedSalesOrderStartTime = unformattedSalesOrderStartTime;
        this.salesOrderStartTime = salesOrderStartTime;
        this.unformattedSalesOrderEndTime = unformattedSalesOrderEndTime;
        this.salesOrderEndTime = salesOrderEndTime;
        this.unformattedPurchaseOrderStartTime = unformattedPurchaseOrderStartTime;
        this.purchaseOrderStartTime = purchaseOrderStartTime;
        this.unformattedPurchaseOrderEndTime = unformattedPurchaseOrderEndTime;
        this.purchaseOrderEndTime = purchaseOrderEndTime;
        this.allowClubDiscounts = allowClubDiscounts;
        this.allowCouponDiscounts = allowCouponDiscounts;
        this.allowAssociatePayments = allowAssociatePayments;
        this.unitOfMeasureKind = unitOfMeasureKind;
        this.itemPriceType = itemPriceType;
        this.cancellationPolicy = cancellationPolicy;
        this.returnPolicy = returnPolicy;
        this.description = description;
        this.itemStatus = itemStatus;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemType
     */
    public ItemTypeTransfer getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(ItemTypeTransfer itemType) {
        this.itemType = itemType;
    }

    /**
     * @return the itemUseType
     */
    public ItemUseTypeTransfer getItemUseType() {
        return itemUseType;
    }

    /**
     * @param itemUseType the itemUseType to set
     */
    public void setItemUseType(ItemUseTypeTransfer itemUseType) {
        this.itemUseType = itemUseType;
    }

    /**
     * @return the itemCategory
     */
    public ItemCategoryTransfer getItemCategory() {
        return itemCategory;
    }

    /**
     * @param itemCategory the itemCategory to set
     */
    public void setItemCategory(ItemCategoryTransfer itemCategory) {
        this.itemCategory = itemCategory;
    }

    /**
     * @return the itemAccountingCategory
     */
    public ItemAccountingCategoryTransfer getItemAccountingCategory() {
        return itemAccountingCategory;
    }

    /**
     * @param itemAccountingCategory the itemAccountingCategory to set
     */
    public void setItemAccountingCategory(ItemAccountingCategoryTransfer itemAccountingCategory) {
        this.itemAccountingCategory = itemAccountingCategory;
    }

    /**
     * @return the itemPurchasingCategory
     */
    public ItemPurchasingCategoryTransfer getItemPurchasingCategory() {
        return itemPurchasingCategory;
    }

    /**
     * @param itemPurchasingCategory the itemPurchasingCategory to set
     */
    public void setItemPurchasingCategory(ItemPurchasingCategoryTransfer itemPurchasingCategory) {
        this.itemPurchasingCategory = itemPurchasingCategory;
    }

    /**
     * @return the company
     */
    public CompanyTransfer getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(CompanyTransfer company) {
        this.company = company;
    }

    /**
     * @return the itemDeliveryType
     */
    public ItemDeliveryTypeTransfer getItemDeliveryType() {
        return itemDeliveryType;
    }

    /**
     * @param itemDeliveryType the itemDeliveryType to set
     */
    public void setItemDeliveryType(ItemDeliveryTypeTransfer itemDeliveryType) {
        this.itemDeliveryType = itemDeliveryType;
    }

    /**
     * @return the itemInventoryType
     */
    public ItemInventoryTypeTransfer getItemInventoryType() {
        return itemInventoryType;
    }

    /**
     * @param itemInventoryType the itemInventoryType to set
     */
    public void setItemInventoryType(ItemInventoryTypeTransfer itemInventoryType) {
        this.itemInventoryType = itemInventoryType;
    }

    /**
     * @return the inventorySerialized
     */
    public Boolean getInventorySerialized() {
        return inventorySerialized;
    }

    /**
     * @param inventorySerialized the inventorySerialized to set
     */
    public void setInventorySerialized(Boolean inventorySerialized) {
        this.inventorySerialized = inventorySerialized;
    }

    /**
     * @return the serialNumberSequence
     */
    public SequenceTransfer getSerialNumberSequence() {
        return serialNumberSequence;
    }

    /**
     * @param serialNumberSequence the serialNumberSequence to set
     */
    public void setSerialNumberSequence(SequenceTransfer serialNumberSequence) {
        this.serialNumberSequence = serialNumberSequence;
    }

    /**
     * @return the shippingChargeExempt
     */
    public Boolean getShippingChargeExempt() {
        return shippingChargeExempt;
    }

    /**
     * @param shippingChargeExempt the shippingChargeExempt to set
     */
    public void setShippingChargeExempt(Boolean shippingChargeExempt) {
        this.shippingChargeExempt = shippingChargeExempt;
    }

    /**
     * @return the unformattedShippingStartTime
     */
    public Long getUnformattedShippingStartTime() {
        return unformattedShippingStartTime;
    }

    /**
     * @param unformattedShippingStartTime the unformattedShippingStartTime to set
     */
    public void setUnformattedShippingStartTime(Long unformattedShippingStartTime) {
        this.unformattedShippingStartTime = unformattedShippingStartTime;
    }

    /**
     * @return the shippingStartTime
     */
    public String getShippingStartTime() {
        return shippingStartTime;
    }

    /**
     * @param shippingStartTime the shippingStartTime to set
     */
    public void setShippingStartTime(String shippingStartTime) {
        this.shippingStartTime = shippingStartTime;
    }

    /**
     * @return the unformattedShippingEndTime
     */
    public Long getUnformattedShippingEndTime() {
        return unformattedShippingEndTime;
    }

    /**
     * @param unformattedShippingEndTime the unformattedShippingEndTime to set
     */
    public void setUnformattedShippingEndTime(Long unformattedShippingEndTime) {
        this.unformattedShippingEndTime = unformattedShippingEndTime;
    }

    /**
     * @return the shippingEndTime
     */
    public String getShippingEndTime() {
        return shippingEndTime;
    }

    /**
     * @param shippingEndTime the shippingEndTime to set
     */
    public void setShippingEndTime(String shippingEndTime) {
        this.shippingEndTime = shippingEndTime;
    }

    /**
     * @return the unformattedSalesOrderStartTime
     */
    public Long getUnformattedSalesOrderStartTime() {
        return unformattedSalesOrderStartTime;
    }

    /**
     * @param unformattedSalesOrderStartTime the unformattedSalesOrderStartTime to set
     */
    public void setUnformattedSalesOrderStartTime(Long unformattedSalesOrderStartTime) {
        this.unformattedSalesOrderStartTime = unformattedSalesOrderStartTime;
    }

    /**
     * @return the salesOrderStartTime
     */
    public String getSalesOrderStartTime() {
        return salesOrderStartTime;
    }

    /**
     * @param salesOrderStartTime the salesOrderStartTime to set
     */
    public void setSalesOrderStartTime(String salesOrderStartTime) {
        this.salesOrderStartTime = salesOrderStartTime;
    }

    /**
     * @return the unformattedSalesOrderEndTime
     */
    public Long getUnformattedSalesOrderEndTime() {
        return unformattedSalesOrderEndTime;
    }

    /**
     * @param unformattedSalesOrderEndTime the unformattedSalesOrderEndTime to set
     */
    public void setUnformattedSalesOrderEndTime(Long unformattedSalesOrderEndTime) {
        this.unformattedSalesOrderEndTime = unformattedSalesOrderEndTime;
    }

    /**
     * @return the salesOrderEndTime
     */
    public String getSalesOrderEndTime() {
        return salesOrderEndTime;
    }

    /**
     * @param salesOrderEndTime the salesOrderEndTime to set
     */
    public void setSalesOrderEndTime(String salesOrderEndTime) {
        this.salesOrderEndTime = salesOrderEndTime;
    }

    /**
     * @return the unformattedPurchaseOrderStartTime
     */
    public Long getUnformattedPurchaseOrderStartTime() {
        return unformattedPurchaseOrderStartTime;
    }

    /**
     * @param unformattedPurchaseOrderStartTime the unformattedPurchaseOrderStartTime to set
     */
    public void setUnformattedPurchaseOrderStartTime(Long unformattedPurchaseOrderStartTime) {
        this.unformattedPurchaseOrderStartTime = unformattedPurchaseOrderStartTime;
    }

    /**
     * @return the purchaseOrderStartTime
     */
    public String getPurchaseOrderStartTime() {
        return purchaseOrderStartTime;
    }

    /**
     * @param purchaseOrderStartTime the purchaseOrderStartTime to set
     */
    public void setPurchaseOrderStartTime(String purchaseOrderStartTime) {
        this.purchaseOrderStartTime = purchaseOrderStartTime;
    }

    /**
     * @return the unformattedPurchaseOrderEndTime
     */
    public Long getUnformattedPurchaseOrderEndTime() {
        return unformattedPurchaseOrderEndTime;
    }

    /**
     * @param unformattedPurchaseOrderEndTime the unformattedPurchaseOrderEndTime to set
     */
    public void setUnformattedPurchaseOrderEndTime(Long unformattedPurchaseOrderEndTime) {
        this.unformattedPurchaseOrderEndTime = unformattedPurchaseOrderEndTime;
    }

    /**
     * @return the purchaseOrderEndTime
     */
    public String getPurchaseOrderEndTime() {
        return purchaseOrderEndTime;
    }

    /**
     * @param purchaseOrderEndTime the purchaseOrderEndTime to set
     */
    public void setPurchaseOrderEndTime(String purchaseOrderEndTime) {
        this.purchaseOrderEndTime = purchaseOrderEndTime;
    }

    /**
     * @return the allowClubDiscounts
     */
    public Boolean getAllowClubDiscounts() {
        return allowClubDiscounts;
    }

    /**
     * @param allowClubDiscounts the allowClubDiscounts to set
     */
    public void setAllowClubDiscounts(Boolean allowClubDiscounts) {
        this.allowClubDiscounts = allowClubDiscounts;
    }

    /**
     * @return the allowCouponDiscounts
     */
    public Boolean getAllowCouponDiscounts() {
        return allowCouponDiscounts;
    }

    /**
     * @param allowCouponDiscounts the allowCouponDiscounts to set
     */
    public void setAllowCouponDiscounts(Boolean allowCouponDiscounts) {
        this.allowCouponDiscounts = allowCouponDiscounts;
    }

    /**
     * @return the allowAssociatePayments
     */
    public Boolean getAllowAssociatePayments() {
        return allowAssociatePayments;
    }

    /**
     * @param allowAssociatePayments the allowAssociatePayments to set
     */
    public void setAllowAssociatePayments(Boolean allowAssociatePayments) {
        this.allowAssociatePayments = allowAssociatePayments;
    }

    /**
     * @return the unitOfMeasureKind
     */
    public UnitOfMeasureKindTransfer getUnitOfMeasureKind() {
        return unitOfMeasureKind;
    }

    /**
     * @param unitOfMeasureKind the unitOfMeasureKind to set
     */
    public void setUnitOfMeasureKind(UnitOfMeasureKindTransfer unitOfMeasureKind) {
        this.unitOfMeasureKind = unitOfMeasureKind;
    }

    /**
     * @return the itemPriceType
     */
    public ItemPriceTypeTransfer getItemPriceType() {
        return itemPriceType;
    }

    /**
     * @param itemPriceType the itemPriceType to set
     */
    public void setItemPriceType(ItemPriceTypeTransfer itemPriceType) {
        this.itemPriceType = itemPriceType;
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
     * @return the itemStatus
     */
    public WorkflowEntityStatusTransfer getItemStatus() {
        return itemStatus;
    }

    /**
     * @param itemStatus the itemStatus to set
     */
    public void setItemStatus(WorkflowEntityStatusTransfer itemStatus) {
        this.itemStatus = itemStatus;
    }

    /**
     * @return the itemShippingTimes
     */
    public ListWrapper<ItemShippingTimeTransfer> getItemShippingTimes() {
        return itemShippingTimes;
    }

    /**
     * @param itemShippingTimes the itemShippingTimes to set
     */
    public void setItemShippingTimes(ListWrapper<ItemShippingTimeTransfer> itemShippingTimes) {
        this.itemShippingTimes = itemShippingTimes;
    }

    /**
     * @return the itemAliases
     */
    public ListWrapper<ItemAliasTransfer> getItemAliases() {
        return itemAliases;
    }

    /**
     * @param itemAliases the itemAliases to set
     */
    public void setItemAliases(ListWrapper<ItemAliasTransfer> itemAliases) {
        this.itemAliases = itemAliases;
    }

    /**
     * @return the itemPrices
     */
    public ListWrapper<ItemPriceTransfer> getItemPrices() {
        return itemPrices;
    }

    /**
     * @param itemPrices the itemPrices to set
     */
    public void setItemPrices(ListWrapper<ItemPriceTransfer> itemPrices) {
        this.itemPrices = itemPrices;
    }

    /**
     * @return the itemUnitOfMeasureTypes
     */
    public ListWrapper<ItemUnitOfMeasureTypeTransfer> getItemUnitOfMeasureTypes() {
        return itemUnitOfMeasureTypes;
    }

    /**
     * @param itemUnitOfMeasureTypes the itemUnitOfMeasureTypes to set
     */
    public void setItemUnitOfMeasureTypes(ListWrapper<ItemUnitOfMeasureTypeTransfer> itemUnitOfMeasureTypes) {
        this.itemUnitOfMeasureTypes = itemUnitOfMeasureTypes;
    }

    /**
     * @return the itemDescriptions
     */
    public ListWrapper<ItemDescriptionTransfer> getItemDescriptions() {
        return itemDescriptions;
    }

    /**
     * @param itemDescriptions the itemDescriptions to set
     */
    public void setItemDescriptions(ListWrapper<ItemDescriptionTransfer> itemDescriptions) {
        this.itemDescriptions = itemDescriptions;
    }

    /**
     * @return the itemVolumes
     */
    public ListWrapper<ItemVolumeTransfer> getItemVolumes() {
        return itemVolumes;
    }

    /**
     * @param itemVolumes the itemVolumes to set
     */
    public void setItemVolumes(ListWrapper<ItemVolumeTransfer> itemVolumes) {
        this.itemVolumes = itemVolumes;
    }

    /**
     * @return the itemWeights
     */
    public ListWrapper<ItemWeightTransfer> getItemWeights() {
        return itemWeights;
    }

    /**
     * @param itemWeights the itemWeights to set
     */
    public void setItemWeights(ListWrapper<ItemWeightTransfer> itemWeights) {
        this.itemWeights = itemWeights;
    }

    /**
     * @return the offerItems
     */
    public ListWrapper<OfferItemTransfer> getOfferItems() {
        return offerItems;
    }

    /**
     * @param offerItems the offerItems to set
     */
    public void setOfferItems(ListWrapper<OfferItemTransfer> offerItems) {
        this.offerItems = offerItems;
    }

    /**
     * @return the vendorItems
     */
    public ListWrapper<VendorItemTransfer> getVendorItems() {
        return vendorItems;
    }

    /**
     * @param vendorItems the vendorItems to set
     */
    public void setVendorItems(ListWrapper<VendorItemTransfer> vendorItems) {
        this.vendorItems = vendorItems;
    }

    /**
     * @return the itemCountryOfOrigins
     */
    public MapWrapper<ItemCountryOfOriginTransfer> getItemCountryOfOrigins() {
        return itemCountryOfOrigins;
    }

    /**
     * @param itemCountryOfOrigins the itemCountryOfOrigins to set
     */
    public void setItemCountryOfOrigins(MapWrapper<ItemCountryOfOriginTransfer> itemCountryOfOrigins) {
        this.itemCountryOfOrigins = itemCountryOfOrigins;
    }

    /**
     * @return the itemKitMembers
     */
    public ListWrapper<ItemKitMemberTransfer> getItemKitMembers() {
        return itemKitMembers;
    }

    /**
     * @param itemKitMembers the itemKitMembers to set
     */
    public void setItemKitMembers(ListWrapper<ItemKitMemberTransfer> itemKitMembers) {
        this.itemKitMembers = itemKitMembers;
    }

    /**
     * @return the itemPackCheckRequirements
     */
    public ListWrapper<ItemPackCheckRequirementTransfer> getItemPackCheckRequirements() {
        return itemPackCheckRequirements;
    }

    /**
     * @param itemPackCheckRequirements the itemPackCheckRequirements to set
     */
    public void setItemPackCheckRequirements(ListWrapper<ItemPackCheckRequirementTransfer> itemPackCheckRequirements) {
        this.itemPackCheckRequirements = itemPackCheckRequirements;
    }

    /**
     * @return the itemUnitCustomerTypeLimits
     */
    public ListWrapper<ItemUnitCustomerTypeLimitTransfer> getItemUnitCustomerTypeLimits() {
        return itemUnitCustomerTypeLimits;
    }

    /**
     * @param itemUnitCustomerTypeLimits the itemUnitCustomerTypeLimits to set
     */
    public void setItemUnitCustomerTypeLimits(ListWrapper<ItemUnitCustomerTypeLimitTransfer> itemUnitCustomerTypeLimits) {
        this.itemUnitCustomerTypeLimits = itemUnitCustomerTypeLimits;
    }

    /**
     * @return the itemUnitLimits
     */
    public ListWrapper<ItemUnitLimitTransfer> getItemUnitLimits() {
        return itemUnitLimits;
    }

    /**
     * @param itemUnitLimits the itemUnitLimits to set
     */
    public void setItemUnitLimits(ListWrapper<ItemUnitLimitTransfer> itemUnitLimits) {
        this.itemUnitLimits = itemUnitLimits;
    }

    /**
     * @return the itemUnitPriceLimits
     */
    public ListWrapper<ItemUnitPriceLimitTransfer> getItemUnitPriceLimits() {
        return itemUnitPriceLimits;
    }

    /**
     * @param itemUnitPriceLimits the itemUnitPriceLimits to set
     */
    public void setItemUnitPriceLimits(ListWrapper<ItemUnitPriceLimitTransfer> itemUnitPriceLimits) {
        this.itemUnitPriceLimits = itemUnitPriceLimits;
    }

    /**
     * @return the relatedItems
     */
    public MapWrapper<ListWrapper<RelatedItemTransfer>> getRelatedItems() {
        return relatedItems;
    }

    /**
     * @param relatedItems the relatedItems to set
     */
    public void setRelatedItems(MapWrapper<ListWrapper<RelatedItemTransfer>> relatedItems) {
        this.relatedItems = relatedItems;
    }

    /**
     * @return the itemHarmonizedTariffScheduleCodes
     */
    public ListWrapper<ItemHarmonizedTariffScheduleCodeTransfer> getItemHarmonizedTariffScheduleCodes() {
        return itemHarmonizedTariffScheduleCodes;
    }

    /**
     * @param itemHarmonizedTariffScheduleCodes the itemHarmonizedTariffScheduleCodes to set
     */
    public void setItemHarmonizedTariffScheduleCodes(ListWrapper<ItemHarmonizedTariffScheduleCodeTransfer> itemHarmonizedTariffScheduleCodes) {
        this.itemHarmonizedTariffScheduleCodes = itemHarmonizedTariffScheduleCodes;
    }

    /**
     * @return the itemTaxClassifications
     */
    public ListWrapper<ItemTaxClassificationTransfer> getItemTaxClassifications() {
        return itemTaxClassifications;
    }

    /**
     * @param itemTaxClassifications the itemTaxClassifications to set
     */
    public void setItemTaxClassifications(ListWrapper<ItemTaxClassificationTransfer> itemTaxClassifications) {
        this.itemTaxClassifications = itemTaxClassifications;
    }

}
