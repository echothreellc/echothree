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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.comment.server.CommentControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemCategoryTransfer;
import com.echothree.model.control.item.common.transfer.ItemCountryOfOriginTransfer;
import com.echothree.model.control.item.common.transfer.ItemDeliveryTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemInventoryTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemPriceTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.common.transfer.ItemTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemUseTypeTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTransfer;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.rating.common.RatingConstants;
import com.echothree.model.control.rating.server.RatingControl;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.vendor.common.transfer.ItemPurchasingCategoryTransfer;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.transfer.ListWrapperBuilder;
import java.util.List;
import java.util.Set;

public class ItemTransferCache
        extends BaseItemTransferCache<Item, ItemTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
    CommentControl commentControl = (CommentControl)Session.getModelController(CommentControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    RatingControl ratingControl = (RatingControl)Session.getModelController(RatingControl.class);
    ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
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
    public ItemTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        Set<String> options = session.getOptions();
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
            Set<String> properties = transferProperties.getProperties(ItemTransfer.class);
            
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
    public ItemTransfer getTransfer(Item item) {
        ItemTransfer itemTransfer = get(item);
        
        if(itemTransfer == null) {
            ItemDetail itemDetail = item.getLastDetail();
            String itemName = filterItemName ? null : itemDetail.getItemName();
            ItemTypeTransfer itemTypeTransfer = filterItemType ? null : itemControl.getItemTypeTransfer(userVisit, itemDetail.getItemType());
            ItemUseTypeTransfer itemUseTypeTransfer = filterItemUseType ? null : itemControl.getItemUseTypeTransfer(userVisit, itemDetail.getItemUseType());
            ItemCategoryTransfer itemCategoryTransfer = filterItemCategory ? null : itemControl.getItemCategoryTransfer(userVisit, itemDetail.getItemCategory());
            ItemAccountingCategory itemAccountingCategory = filterItemAccountingCategory ? null : itemDetail.getItemAccountingCategory();
            ItemAccountingCategoryTransfer itemAccountingCategoryTransfer = itemAccountingCategory == null ? null : accountingControl.getItemAccountingCategoryTransfer(userVisit, itemAccountingCategory);
            ItemPurchasingCategory itemPurchasingCategory = filterItemPurchasingCategory ? null : itemDetail.getItemPurchasingCategory();
            ItemPurchasingCategoryTransfer itemPurchasingCategoryTransfer = itemPurchasingCategory == null ? null : vendorControl.getItemPurchasingCategoryTransfer(userVisit, itemPurchasingCategory);
            CompanyTransfer companyTransfer = filterCompany ? null : partyControl.getCompanyTransfer(userVisit, itemDetail.getCompanyParty());
            ItemDeliveryType itemDeliveryType = filterItemDeliveryType ? null : itemDetail.getItemDeliveryType();
            ItemDeliveryTypeTransfer itemDeliveryTypeTransfer = itemDeliveryType == null ? null : itemControl.getItemDeliveryTypeTransfer(userVisit, itemDeliveryType);
            ItemInventoryType itemInventoryType = filterItemInventoryType ? null : itemDetail.getItemInventoryType();
            ItemInventoryTypeTransfer itemInventoryTypeTransfer = itemInventoryType == null ? null : itemControl.getItemInventoryTypeTransfer(userVisit, itemInventoryType);
            Boolean inventorySerialized = filterInventorySerialized ? null : itemDetail.getInventorySerialized();
            Sequence serialNumberSequence = filterSerialNumberSequence ? null : itemDetail.getSerialNumberSequence();
            SequenceTransfer serialNumberSequenceTransfer = serialNumberSequence == null ? null : sequenceControl.getSequenceTransfer(userVisit, serialNumberSequence);
            Boolean shippingChargeExempt = filterShippingChargeExempt ? null : itemDetail.getShippingChargeExempt();
            Long unformattedShippingStartTime = filterUnformattedShippingStartTime ? null : itemDetail.getShippingStartTime();
            String shippingStartTime = filterShippingStartTime ? null : formatTypicalDateTime(unformattedShippingStartTime);
            Long unformattedShippingEndTime = filterUnformattedShippingEndTime ? null : itemDetail.getShippingEndTime();
            String shippingEndTime = filterShippingEndTime ? null : formatTypicalDateTime(unformattedShippingEndTime);
            Long unformattedSalesOrderStartTime = filterUnformattedSalesOrderStartTime ? null : itemDetail.getSalesOrderStartTime();
            String salesOrderStartTime = filterSalesOrderStartTime ? null : formatTypicalDateTime(unformattedSalesOrderStartTime);
            Long unformattedSalesOrderEndTime = filterUnformattedSalesOrderEndTime ? null : itemDetail.getSalesOrderEndTime();
            String salesOrderEndTime = filterSalesOrderEndTime ? null : formatTypicalDateTime(unformattedSalesOrderEndTime);
            Long unformattedPurchaseOrderStartTime = filterUnformattedPurchaseOrderStartTime ? null : itemDetail.getPurchaseOrderStartTime();
            String purchaseOrderStartTime = filterPurchaseOrderStartTime ? null : formatTypicalDateTime(unformattedPurchaseOrderStartTime);
            Long unformattedPurchaseOrderEndTime = filterUnformattedPurchaseOrderEndTime ? null : itemDetail.getPurchaseOrderEndTime();
            String purchaseOrderEndTime = filterPurchaseOrderEndTime ? null : formatTypicalDateTime(unformattedPurchaseOrderEndTime);
            Boolean allowClubDiscounts = filterAllowClubDiscounts ? null : itemDetail.getAllowClubDiscounts();
            Boolean allowCouponDiscounts = filterAllowCouponDiscounts ? null : itemDetail.getAllowCouponDiscounts();
            Boolean allowAssociatePayments = filterAllowAssociatePayments ? null : itemDetail.getAllowAssociatePayments();
            UnitOfMeasureKindTransfer unitOfMeasureKindTransfer = filterUnitOfMeasureKind ? null : uomControl.getUnitOfMeasureKindTransfer(userVisit, itemDetail.getUnitOfMeasureKind());
            ItemPriceTypeTransfer itemPriceTypeTransfer = filterItemPriceType ? null : itemControl.getItemPriceTypeTransfer(userVisit, itemDetail.getItemPriceType());
            CancellationPolicy cancellationPolicy = filterCancellationPolicy ? null : itemDetail.getCancellationPolicy();
            CancellationPolicyTransfer cancellationPolicyTransfer = cancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
            ReturnPolicy returnPolicy = filterReturnPolicy ? null : itemDetail.getReturnPolicy();
            ReturnPolicyTransfer returnPolicyTransfer = returnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy);
            ItemDescriptionType itemDescriptionType = filterDescription ? null : itemControl.getItemDescriptionTypeByName(ItemConstants.ItemDescriptionType_DEFAULT_DESCRIPTION);
            String description = itemDescriptionType == null ? null : itemControl.getBestItemStringDescription(itemDescriptionType, item, getLanguage());

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(item.getPrimaryKey());
            WorkflowEntityStatusTransfer itemStatusTransfer = filterItemStatus ? null : workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    ItemStatusConstants.Workflow_ITEM_STATUS, entityInstance);

            itemTransfer = new ItemTransfer(itemName, itemTypeTransfer, itemUseTypeTransfer, itemCategoryTransfer, itemAccountingCategoryTransfer,
                    itemPurchasingCategoryTransfer, companyTransfer, itemDeliveryTypeTransfer, itemInventoryTypeTransfer, inventorySerialized,
                    serialNumberSequenceTransfer, shippingChargeExempt, unformattedShippingStartTime, shippingStartTime, unformattedShippingEndTime,
                    shippingEndTime, unformattedSalesOrderStartTime, salesOrderStartTime, unformattedSalesOrderEndTime,
                    salesOrderEndTime, unformattedPurchaseOrderStartTime, purchaseOrderStartTime, unformattedPurchaseOrderEndTime, purchaseOrderEndTime,
                    allowClubDiscounts, allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKindTransfer, itemPriceTypeTransfer,
                    cancellationPolicyTransfer, returnPolicyTransfer, description, itemStatusTransfer);
            put(item, itemTransfer, entityInstance);

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
                var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

                itemTransfer.setOfferItems(ListWrapperBuilder.getInstance().filter(transferProperties, offerItemControl.getOfferItemTransfersByItem(userVisit, item)));
            }

            if(includeVendorItems) {
                itemTransfer.setVendorItems(ListWrapperBuilder.getInstance().filter(transferProperties, vendorControl.getVendorItemTransfersByItem(userVisit, item)));
            }

            if(includeItemCountryOfOrigins) {
                List<ItemCountryOfOriginTransfer> itemCountryOfOriginTransfers = itemControl.getItemCountryOfOriginTransfersByItem(userVisit, item);
                MapWrapper<ItemCountryOfOriginTransfer> itemCountryOfOrigins = new MapWrapper<>();

                itemCountryOfOriginTransfers.stream().forEach((itemCountryOfOriginTransfer) -> {
                    itemCountryOfOrigins.put(itemCountryOfOriginTransfer.getCountryGeoCode().getGeoCodeAliases().getMap().get(GeoConstants.GeoCodeAliasType_COUNTRY_NAME).getAlias(), itemCountryOfOriginTransfer);
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
                List<RelatedItemType> relatedItemTypes = itemControl.getRelatedItemTypes();
                MapWrapper<ListWrapper<RelatedItemTransfer>> relatedItems = new MapWrapper<>(relatedItemTypes.size());

                relatedItemTypes.stream().forEach((relatedItemType) -> {
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
                setupComments(null, entityInstance, itemTransfer, CommentConstants.CommentType_ITEM_CUSTOMER);
            }

            if(includeCustomerServiceComments) {
                setupComments(null, entityInstance, itemTransfer, CommentConstants.CommentType_ITEM_CUSTOMER_SERVICE);
            }

            if(includePurchasingComments) {
                setupComments(null, entityInstance, itemTransfer, CommentConstants.CommentType_ITEM_PURCHASING);
            }

            if(includeCustomerRatings) {
                setupRatings(null, entityInstance, itemTransfer, RatingConstants.RatingType_ITEM_CUSTOMER);
            }
        }
        
        return itemTransfer;
    }
    
}
