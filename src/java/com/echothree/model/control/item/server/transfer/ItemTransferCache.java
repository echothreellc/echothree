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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.item.common.ItemDescriptionTypes;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemCountryOfOriginTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTransfer;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.rating.common.RatingConstants;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.transfer.ListWrapperBuilder;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemTransferCache
        extends BaseItemTransferCache<Item, ItemTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    TaxControl taxControl = Session.getModelController(TaxControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    VendorControl vendorControl = Session.getModelController(VendorControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    boolean includeItemShippingTimes;
    boolean includeItemAliases;
    boolean includeItemPrices;
    boolean includeItemUnitOfMeasureTypes;
    boolean includeItemDescriptions;
    boolean includeItemVolumes;
    boolean includeItemWeights;
    boolean includeOfferItems;
    boolean includeVendorItems;
    boolean includeItemCountryOfOrigins;
    boolean includeItemKitMembers;
    boolean includeItemPackCheckRequirements;
    boolean includeItemUnitCustomerTypeLimits;
    boolean includeItemUnitLimits;
    boolean includeItemUnitPriceLimits;
    boolean includeRelatedItems;
    boolean includeItemHarmonizedTariffScheduleCodes;
    boolean includeItemTaxClassifications;
    boolean includeCustomerComments;
    boolean includeCustomerServiceComments;
    boolean includePurchasingComments;
    boolean includeCustomerRatings;
    
    TransferProperties transferProperties;
    boolean filterItemName;
    boolean filterItemType;
    boolean filterItemUseType;
    boolean filterItemCategory;
    boolean filterItemAccountingCategory;
    boolean filterItemPurchasingCategory;
    boolean filterCompany;
    boolean filterItemDeliveryType;
    boolean filterItemInventoryType;
    boolean filterInventorySerialized;
    boolean filterSerialNumberSequence;
    boolean filterShippingChargeExempt;
    boolean filterUnformattedShippingStartTime;
    boolean filterShippingStartTime;
    boolean filterUnformattedShippingEndTime;
    boolean filterShippingEndTime;
    boolean filterUnformattedSalesOrderStartTime;
    boolean filterSalesOrderStartTime;
    boolean filterUnformattedSalesOrderEndTime;
    boolean filterSalesOrderEndTime;
    boolean filterUnformattedPurchaseOrderStartTime;
    boolean filterPurchaseOrderStartTime;
    boolean filterUnformattedPurchaseOrderEndTime;
    boolean filterPurchaseOrderEndTime;
    boolean filterAllowClubDiscounts;
    boolean filterAllowCouponDiscounts;
    boolean filterAllowAssociatePayments;
    boolean filterUnitOfMeasureKind;
    boolean filterItemPriceType;
    boolean filterCancellationPolicy;
    boolean filterReturnPolicy;
    boolean filterDescription;
    boolean filterItemStatus;
    boolean filterEntityInstance;
    
    /** Creates a new instance of ItemTransferCache */
    public ItemTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeItemShippingTimes = options.contains(ItemOptions.ItemIncludeItemShippingTimes);
            includeItemAliases = options.contains(ItemOptions.ItemIncludeItemAliases);
            includeItemPrices = options.contains(ItemOptions.ItemIncludeItemPrices);
            includeItemUnitOfMeasureTypes = options.contains(ItemOptions.ItemIncludeItemUnitOfMeasureTypes);
            includeItemDescriptions = options.contains(ItemOptions.ItemIncludeItemDescriptions);
            includeItemVolumes = options.contains(ItemOptions.ItemIncludeItemVolumes);
            includeItemWeights = options.contains(ItemOptions.ItemIncludeItemWeights);
            includeOfferItems = options.contains(ItemOptions.ItemIncludeOfferItems);
            includeVendorItems = options.contains(ItemOptions.ItemIncludeVendorItems);
            includeItemCountryOfOrigins = options.contains(ItemOptions.ItemIncludeItemCountryOfOrigins);
            includeItemKitMembers = options.contains(ItemOptions.ItemIncludeItemKitMembers);
            includeItemPackCheckRequirements = options.contains(ItemOptions.ItemIncludeItemPackCheckRequirements);
            includeItemUnitCustomerTypeLimits = options.contains(ItemOptions.ItemIncludeItemUnitCustomerTypeLimits);
            includeItemUnitLimits = options.contains(ItemOptions.ItemIncludeItemUnitLimits);
            includeItemUnitPriceLimits = options.contains(ItemOptions.ItemIncludeItemUnitPriceLimits);
            includeRelatedItems = options.contains(ItemOptions.ItemIncludeRelatedItems);
            includeItemHarmonizedTariffScheduleCodes = options.contains(ItemOptions.ItemIncludeItemHarmonizedTariffScheduleCodes);
            includeItemTaxClassifications = options.contains(ItemOptions.ItemIncludeItemTaxClassifications);
            includeCustomerComments = options.contains(ItemOptions.ItemIncludeCustomerComments);
            includeCustomerServiceComments = options.contains(ItemOptions.ItemIncludeCustomerServiceComments);
            includePurchasingComments = options.contains(ItemOptions.ItemIncludePurchasingComments);
            includeCustomerRatings = options.contains(ItemOptions.ItemIncludeCustomerRatings);
            setIncludeEntityAttributeGroups(options.contains(ItemOptions.ItemIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ItemOptions.ItemIncludeTagScopes));
            
            if(includeItemHarmonizedTariffScheduleCodes) {
                verifyOptionDependency(ItemOptions.ItemIncludeItemHarmonizedTariffScheduleCodes, GeoOptions.CountryIncludeAliases);
            }
            if(includeItemTaxClassifications) {
                verifyOptionDependency(ItemOptions.ItemIncludeItemTaxClassifications, GeoOptions.CountryIncludeAliases);
            }
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ItemTransfer.class);
            
            if(properties != null) {
                filterItemName = !properties.contains(ItemProperties.ITEM_NAME);
                filterItemType = !properties.contains(ItemProperties.ITEM_TYPE);
                filterItemUseType = !properties.contains(ItemProperties.ITEM_USE_TYPE);
                filterItemCategory = !properties.contains(ItemProperties.ITEM_CATEGORY);
                filterItemAccountingCategory = !properties.contains(ItemProperties.ITEM_ACCOUNTING_CATEGORY);
                filterItemPurchasingCategory = !properties.contains(ItemProperties.ITEM_PURCHASING_CATEGORY);
                filterCompany = !properties.contains(ItemProperties.COMPANY);
                filterItemDeliveryType = !properties.contains(ItemProperties.ITEM_DELIVERY_TYPE);
                filterItemInventoryType = !properties.contains(ItemProperties.ITEM_INVENTORY_TYPE);
                filterInventorySerialized = !properties.contains(ItemProperties.INVENTORY_SERIALIZED);
                filterSerialNumberSequence = !properties.contains(ItemProperties.SERIAL_NUMBER_SEQUENCE);
                filterShippingChargeExempt = !properties.contains(ItemProperties.SHIPPING_CHARGE_EXEMPT);
                filterUnformattedShippingStartTime = !properties.contains(ItemProperties.UNFORMATTED_SHIPPING_START_TIME);
                filterShippingStartTime = !properties.contains(ItemProperties.SHIPPING_START_TIME);
                filterUnformattedShippingEndTime = !properties.contains(ItemProperties.UNFORMATTED_SHIPPING_END_TIME);
                filterShippingEndTime = !properties.contains(ItemProperties.SHIPPING_END_TIME);
                filterUnformattedSalesOrderStartTime = !properties.contains(ItemProperties.UNFORMATTED_SALES_ORDER_START_TIME);
                filterSalesOrderStartTime = !properties.contains(ItemProperties.SALES_ORDER_START_TIME);
                filterUnformattedSalesOrderEndTime = !properties.contains(ItemProperties.UNFORMATTED_SALES_ORDER_END_TIME);
                filterSalesOrderEndTime = !properties.contains(ItemProperties.SALES_ORDER_END_TIME);
                filterUnformattedPurchaseOrderStartTime = !properties.contains(ItemProperties.UNFORMATTED_PURCHASE_ORDER_START_TIME);
                filterPurchaseOrderStartTime = !properties.contains(ItemProperties.PURCHASE_ORDER_START_TIME);
                filterUnformattedPurchaseOrderEndTime = !properties.contains(ItemProperties.UNFORMATTED_PURCHASE_ORDER_END_TIME);
                filterPurchaseOrderEndTime = !properties.contains(ItemProperties.PURCHASE_ORDER_END_TIME);
                filterAllowClubDiscounts = !properties.contains(ItemProperties.ALLOW_CLUB_DISCOUNTS);
                filterAllowCouponDiscounts = !properties.contains(ItemProperties.ALLOW_COUPON_DISCOUNTS);
                filterAllowAssociatePayments = !properties.contains(ItemProperties.ALLOW_ASSOCIATE_PAYMENTS);
                filterUnitOfMeasureKind = !properties.contains(ItemProperties.UNIT_OF_MEASURE_KIND);
                filterItemPriceType = !properties.contains(ItemProperties.ITEM_PRICE_TYPE);
                filterCancellationPolicy = !properties.contains(ItemProperties.CANCELLATION_POLICY);
                filterReturnPolicy = !properties.contains(ItemProperties.RETURN_POLICY);
                filterDescription = !properties.contains(ItemProperties.DESCRIPTION);
                filterItemStatus = !properties.contains(ItemProperties.ITEM_STATUS);
                filterEntityInstance = !properties.contains(ItemProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    @Override
    public ItemTransfer getTransfer(UserVisit userVisit, Item item) {
        var itemTransfer = get(item);
        
        if(itemTransfer == null) {
            var itemDetail = item.getLastDetail();
            var itemName = filterItemName ? null : itemDetail.getItemName();
            var itemTypeTransfer = filterItemType ? null : itemControl.getItemTypeTransfer(userVisit, itemDetail.getItemType());
            var itemUseTypeTransfer = filterItemUseType ? null : itemControl.getItemUseTypeTransfer(userVisit, itemDetail.getItemUseType());
            var itemCategoryTransfer = filterItemCategory ? null : itemControl.getItemCategoryTransfer(userVisit, itemDetail.getItemCategory());
            var itemAccountingCategory = filterItemAccountingCategory ? null : itemDetail.getItemAccountingCategory();
            var itemAccountingCategoryTransfer = itemAccountingCategory == null ? null : accountingControl.getItemAccountingCategoryTransfer(userVisit, itemAccountingCategory);
            var itemPurchasingCategory = filterItemPurchasingCategory ? null : itemDetail.getItemPurchasingCategory();
            var itemPurchasingCategoryTransfer = itemPurchasingCategory == null ? null : vendorControl.getItemPurchasingCategoryTransfer(userVisit, itemPurchasingCategory);
            var companyTransfer = filterCompany ? null : partyControl.getCompanyTransfer(userVisit, itemDetail.getCompanyParty());
            var itemDeliveryType = filterItemDeliveryType ? null : itemDetail.getItemDeliveryType();
            var itemDeliveryTypeTransfer = itemDeliveryType == null ? null : itemControl.getItemDeliveryTypeTransfer(userVisit, itemDeliveryType);
            var itemInventoryType = filterItemInventoryType ? null : itemDetail.getItemInventoryType();
            var itemInventoryTypeTransfer = itemInventoryType == null ? null : itemControl.getItemInventoryTypeTransfer(userVisit, itemInventoryType);
            var inventorySerialized = filterInventorySerialized ? null : itemDetail.getInventorySerialized();
            var serialNumberSequence = filterSerialNumberSequence ? null : itemDetail.getSerialNumberSequence();
            var serialNumberSequenceTransfer = serialNumberSequence == null ? null : sequenceControl.getSequenceTransfer(userVisit, serialNumberSequence);
            var shippingChargeExempt = filterShippingChargeExempt ? null : itemDetail.getShippingChargeExempt();
            var unformattedShippingStartTime = filterUnformattedShippingStartTime ? null : itemDetail.getShippingStartTime();
            var shippingStartTime = filterShippingStartTime ? null : formatTypicalDateTime(userVisit, unformattedShippingStartTime);
            var unformattedShippingEndTime = filterUnformattedShippingEndTime ? null : itemDetail.getShippingEndTime();
            var shippingEndTime = filterShippingEndTime ? null : formatTypicalDateTime(userVisit, unformattedShippingEndTime);
            var unformattedSalesOrderStartTime = filterUnformattedSalesOrderStartTime ? null : itemDetail.getSalesOrderStartTime();
            var salesOrderStartTime = filterSalesOrderStartTime ? null : formatTypicalDateTime(userVisit, unformattedSalesOrderStartTime);
            var unformattedSalesOrderEndTime = filterUnformattedSalesOrderEndTime ? null : itemDetail.getSalesOrderEndTime();
            var salesOrderEndTime = filterSalesOrderEndTime ? null : formatTypicalDateTime(userVisit, unformattedSalesOrderEndTime);
            var unformattedPurchaseOrderStartTime = filterUnformattedPurchaseOrderStartTime ? null : itemDetail.getPurchaseOrderStartTime();
            var purchaseOrderStartTime = filterPurchaseOrderStartTime ? null : formatTypicalDateTime(userVisit, unformattedPurchaseOrderStartTime);
            var unformattedPurchaseOrderEndTime = filterUnformattedPurchaseOrderEndTime ? null : itemDetail.getPurchaseOrderEndTime();
            var purchaseOrderEndTime = filterPurchaseOrderEndTime ? null : formatTypicalDateTime(userVisit, unformattedPurchaseOrderEndTime);
            var allowClubDiscounts = filterAllowClubDiscounts ? null : itemDetail.getAllowClubDiscounts();
            var allowCouponDiscounts = filterAllowCouponDiscounts ? null : itemDetail.getAllowCouponDiscounts();
            var allowAssociatePayments = filterAllowAssociatePayments ? null : itemDetail.getAllowAssociatePayments();
            var unitOfMeasureKindTransfer = filterUnitOfMeasureKind ? null : uomControl.getUnitOfMeasureKindTransfer(userVisit, itemDetail.getUnitOfMeasureKind());
            var itemPriceTypeTransfer = filterItemPriceType ? null : itemControl.getItemPriceTypeTransfer(userVisit, itemDetail.getItemPriceType());
            var cancellationPolicy = filterCancellationPolicy ? null : itemDetail.getCancellationPolicy();
            var cancellationPolicyTransfer = cancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
            var returnPolicy = filterReturnPolicy ? null : itemDetail.getReturnPolicy();
            var returnPolicyTransfer = returnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy);
            var itemDescriptionType = filterDescription ? null : itemControl.getItemDescriptionTypeByName(ItemDescriptionTypes.DEFAULT_DESCRIPTION.name());
            var description = itemDescriptionType == null ? null : itemControl.getBestItemStringDescription(itemDescriptionType, item, getLanguage(userVisit));

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(item.getPrimaryKey());
            var itemStatusTransfer = filterItemStatus ? null : workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    ItemStatusConstants.Workflow_ITEM_STATUS, entityInstance);

            itemTransfer = new ItemTransfer(itemName, itemTypeTransfer, itemUseTypeTransfer, itemCategoryTransfer, itemAccountingCategoryTransfer,
                    itemPurchasingCategoryTransfer, companyTransfer, itemDeliveryTypeTransfer, itemInventoryTypeTransfer, inventorySerialized,
                    serialNumberSequenceTransfer, shippingChargeExempt, unformattedShippingStartTime, shippingStartTime, unformattedShippingEndTime,
                    shippingEndTime, unformattedSalesOrderStartTime, salesOrderStartTime, unformattedSalesOrderEndTime,
                    salesOrderEndTime, unformattedPurchaseOrderStartTime, purchaseOrderStartTime, unformattedPurchaseOrderEndTime, purchaseOrderEndTime,
                    allowClubDiscounts, allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKindTransfer, itemPriceTypeTransfer,
                    cancellationPolicyTransfer, returnPolicyTransfer, description, itemStatusTransfer);
            put(userVisit, item, itemTransfer, entityInstance);

            if(includeItemShippingTimes) {
                itemTransfer.setItemShippingTimes(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemShippingTimeTransfersByItem(userVisit, item)));
            }

            if(includeItemAliases) {
                itemTransfer.setItemAliases(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemAliasTransfersByItem(userVisit, item)));
            }

            if(includeItemPrices) {
                itemTransfer.setItemPrices(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemPriceTransfersByItem(userVisit, item)));
            }

            if(includeItemUnitOfMeasureTypes) {
                itemTransfer.setItemUnitOfMeasureTypes(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemUnitOfMeasureTypeTransfersByItem(userVisit, item)));
            }

            if(includeItemDescriptions) {
                itemTransfer.setItemDescriptions(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemDescriptionTransfersByItem(userVisit, item)));
            }

            if(includeItemVolumes) {
                itemTransfer.setItemVolumes(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemVolumeTransfersByItem(userVisit, item)));
            }

            if(includeItemWeights) {
                itemTransfer.setItemWeights(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemWeightTransfersByItem(userVisit, item)));
            }

            if(includeOfferItems) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);

                itemTransfer.setOfferItems(ListWrapperBuilder.getInstance().filter(transferProperties, offerItemControl.getOfferItemTransfersByItem(userVisit, item)));
            }

            if(includeVendorItems) {
                itemTransfer.setVendorItems(ListWrapperBuilder.getInstance().filter(transferProperties, vendorControl.getVendorItemTransfersByItem(userVisit, item)));
            }

            if(includeItemCountryOfOrigins) {
                var itemCountryOfOriginTransfers = itemControl.getItemCountryOfOriginTransfersByItem(userVisit, item);
                var itemCountryOfOrigins = new MapWrapper<ItemCountryOfOriginTransfer>();

                itemCountryOfOriginTransfers.forEach((itemCountryOfOriginTransfer) -> {
                    itemCountryOfOrigins.put(itemCountryOfOriginTransfer.getCountryGeoCode().getGeoCodeAliases().getMap().get(GeoCodeAliasTypes.COUNTRY_NAME.name()).getAlias(), itemCountryOfOriginTransfer);
                });

                itemTransfer.setItemCountryOfOrigins(itemCountryOfOrigins);
            }

            if(includeItemKitMembers) {
                itemTransfer.setItemKitMembers(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemKitMemberTransfersByItem(userVisit, item)));
            }

            if(includeItemPackCheckRequirements) {
                itemTransfer.setItemPackCheckRequirements(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemPackCheckRequirementTransfersByItem(userVisit, item)));
            }

            if(includeItemUnitCustomerTypeLimits) {
                itemTransfer.setItemUnitCustomerTypeLimits(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemUnitCustomerTypeLimitTransfersByItem(userVisit, item)));
            }

            if(includeItemUnitLimits) {
                itemTransfer.setItemUnitLimits(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemUnitLimitTransfersByItem(userVisit, item)));
            }

            if(includeItemUnitPriceLimits) {
                itemTransfer.setItemUnitPriceLimits(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemUnitPriceLimitTransfersByItem(userVisit, item)));
            }

            if(includeRelatedItems) {
                var relatedItemTypes = itemControl.getRelatedItemTypes();
                var relatedItems = new MapWrapper<ListWrapper<RelatedItemTransfer>>(relatedItemTypes.size());

                relatedItemTypes.forEach((relatedItemType) -> {
                    relatedItems.put(relatedItemType.getLastDetail().getRelatedItemTypeName(), new ListWrapper<>(itemControl.getRelatedItemTransfersByRelatedItemTypeAndFromItem(userVisit, relatedItemType, item)));
                });

                itemTransfer.setRelatedItems(relatedItems);
            }

            if(includeItemHarmonizedTariffScheduleCodes) {
                itemTransfer.setItemHarmonizedTariffScheduleCodes(ListWrapperBuilder.getInstance().filter(transferProperties, itemControl.getItemHarmonizedTariffScheduleCodeTransfersByItem(userVisit, item)));
            }

            if(includeItemTaxClassifications) {
                itemTransfer.setItemTaxClassifications(ListWrapperBuilder.getInstance().filter(transferProperties, taxControl.getItemTaxClassificationTransfersByItem(userVisit, item)));
            }

            if(includeCustomerComments) {
                setupComments(userVisit, null, entityInstance, itemTransfer, CommentConstants.CommentType_ITEM_CUSTOMER);
            }

            if(includeCustomerServiceComments) {
                setupComments(userVisit, null, entityInstance, itemTransfer, CommentConstants.CommentType_ITEM_CUSTOMER_SERVICE);
            }

            if(includePurchasingComments) {
                setupComments(userVisit, null, entityInstance, itemTransfer, CommentConstants.CommentType_ITEM_PURCHASING);
            }

            if(includeCustomerRatings) {
                setupRatings(userVisit, null, entityInstance, itemTransfer, RatingConstants.RatingType_ITEM_CUSTOMER);
            }
        }
        
        return itemTransfer;
    }
    
}
