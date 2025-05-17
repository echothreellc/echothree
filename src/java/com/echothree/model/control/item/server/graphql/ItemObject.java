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

package com.echothree.model.control.item.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.ItemAccountingCategoryObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicySecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.graphql.OfferItemObject;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.party.server.graphql.CompanyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.graphql.ItemPurchasingCategoryObject;
import com.echothree.model.control.vendor.server.graphql.VendorItemObject;
import com.echothree.model.control.vendor.server.graphql.VendorSecurityUtils;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.item.common.ItemAliasConstants;
import com.echothree.model.data.item.common.ItemPriceConstants;
import com.echothree.model.data.item.common.ItemUnitOfMeasureTypeConstants;
import com.echothree.model.data.item.common.RelatedItemTypeConstants;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.offer.common.OfferItemConstants;
import com.echothree.model.data.vendor.common.VendorItemConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("item object")
@GraphQLName("Item")
public class ItemObject
        extends BaseEntityInstanceObject {
    
    private final Item item; // Always Present
    
    public ItemObject(Item item) {
        super(item.getPrimaryKey());

        this.item = item;
    }

    private ItemDetail itemDetail; // Optional, use getItemDetail()
    
    private ItemDetail getItemDetail() {
        if(itemDetail == null) {
            itemDetail = item.getLastDetail();
        }
        
        return itemDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item name")
    @GraphQLNonNull
    public String getItemName() {
        return getItemDetail().getItemName();
    }

    @GraphQLField
    @GraphQLDescription("item type")
    @GraphQLNonNull
    public ItemTypeObject getItemType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemTypeAccess(env) ? new ItemTypeObject(getItemDetail().getItemType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item use type")
    @GraphQLNonNull
    public ItemUseTypeObject getItemUseType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemUseTypeAccess(env) ? new ItemUseTypeObject(getItemDetail().getItemUseType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item category")
    public ItemCategoryObject getItemCategory(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemCategoryAccess(env) ? new ItemCategoryObject(getItemDetail().getItemCategory()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item accounting category")
    public ItemAccountingCategoryObject getItemAccountingCategory(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasItemAccountingCategoryAccess(env) ? new ItemAccountingCategoryObject(getItemDetail().getItemAccountingCategory()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item purchasing category")
    public ItemPurchasingCategoryObject getItemPurchasingCategory(final DataFetchingEnvironment env) {
        return VendorSecurityUtils.getHasItemPurchasingCategoryAccess(env) ? new ItemPurchasingCategoryObject(getItemDetail().getItemPurchasingCategory()) : null;
    }

    @GraphQLField
    @GraphQLDescription("company")
    @GraphQLNonNull
    public CompanyObject getCompany(final DataFetchingEnvironment env) {
        var companyParty = getItemDetail().getCompanyParty();

        return PartySecurityUtils.getHasPartyAccess(env, companyParty) ? new CompanyObject(companyParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("item delivery type")
    @GraphQLNonNull
    public ItemDeliveryTypeObject getItemDeliveryType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemDeliveryTypeAccess(env) ? new ItemDeliveryTypeObject(getItemDetail().getItemDeliveryType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item inventory type")
    @GraphQLNonNull
    public ItemInventoryTypeObject getItemInventoryType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemInventoryTypeAccess(env) ? new ItemInventoryTypeObject(getItemDetail().getItemInventoryType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory serialized")
    public Boolean getInventorySerialized() {
        return getItemDetail().getInventorySerialized();
    }

    @GraphQLField
    @GraphQLDescription("serial number sequence")
    public SequenceObject getSalesOrderSequence(final DataFetchingEnvironment env) {
        var salesOrderSequence = getItemDetail().getSerialNumberSequence();

        return salesOrderSequence == null ? null : (SequenceSecurityUtils.getHasSequenceAccess(env) ? new SequenceObject(salesOrderSequence) : null);
    }

    @GraphQLField
    @GraphQLDescription("shipping charge exempt")
    @GraphQLNonNull
    public Boolean getShippingChargeExempt() {
        return getItemDetail().getShippingChargeExempt();
    }

    @GraphQLField
    @GraphQLDescription("shipping start time")
    @GraphQLNonNull
    public TimeObject getShippingStartTime(final DataFetchingEnvironment env) {
        return new TimeObject(getItemDetail().getShippingStartTime());
    }
    
    @GraphQLField
    @GraphQLDescription("shipping end time")
    public TimeObject getShippingEndTime(final DataFetchingEnvironment env) {
        var shippingEndTime = getItemDetail().getShippingEndTime();

        return shippingEndTime == null ? null : new TimeObject(shippingEndTime);
    }
    
    @GraphQLField
    @GraphQLDescription("sales order start time")
    @GraphQLNonNull
    public TimeObject getSalesOrderStartTime(final DataFetchingEnvironment env) {
        return new TimeObject(getItemDetail().getSalesOrderStartTime());
    }

    @GraphQLField
    @GraphQLDescription("sales order end time")
    public TimeObject getSalesOrderEndTime(final DataFetchingEnvironment env) {
        var salesOrderEndTime = getItemDetail().getSalesOrderEndTime();

        return salesOrderEndTime == null ? null : new TimeObject(salesOrderEndTime);
    }

    @GraphQLField
    @GraphQLDescription("purchase order start time")
    public TimeObject getPurchaseOrderStartTime(final DataFetchingEnvironment env) {
        var purchaseOrderStartTime = getItemDetail().getPurchaseOrderStartTime();

        return purchaseOrderStartTime == null ? null : new TimeObject(purchaseOrderStartTime);
    }
    
    @GraphQLField
    @GraphQLDescription("purchase order end time")
    public TimeObject getPurchaseOrderEndTime(final DataFetchingEnvironment env) {
        var purchaseOrderEndTime = getItemDetail().getPurchaseOrderEndTime();

        return purchaseOrderEndTime == null ? null : new TimeObject(purchaseOrderEndTime);
    }

    @GraphQLField
    @GraphQLDescription("allow club discounts")
    @GraphQLNonNull
    public Boolean getAllowClubDiscounts() {
        return getItemDetail().getAllowClubDiscounts();
    }

    @GraphQLField
    @GraphQLDescription("allow coupon discounts")
    @GraphQLNonNull
    public Boolean getAllowCouponDiscounts() {
        return getItemDetail().getAllowCouponDiscounts();
    }

    @GraphQLField
    @GraphQLDescription("allow associate payments")
    @GraphQLNonNull
    public Boolean getAllowAssociatePayments() {
        return getItemDetail().getAllowAssociatePayments();
    }

    @GraphQLField
    @GraphQLDescription("unit of measure kind")
    public UnitOfMeasureKindObject getUnitOfMeasureKind(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureKindAccess(env) ? new UnitOfMeasureKindObject(getItemDetail().getUnitOfMeasureKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item price type")
    public ItemPriceTypeObject getItemPriceType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemPriceTypeAccess(env) ? new ItemPriceTypeObject(getItemDetail().getItemPriceType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var cancellationPolicy = getItemDetail().getCancellationPolicy();

        return cancellationPolicy == null ? null : (CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ? new CancellationPolicyObject(cancellationPolicy) : null);
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var returnPolicy = getItemDetail().getReturnPolicy();

        return returnPolicy == null ? null : (ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ? new ReturnPolicyObject(returnPolicy) : null);
    }

    //| itm_stylpth_stylepathid             | bigint      | YES  |     | NULL    |       |

    @GraphQLField
    @GraphQLDescription("item prices")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemPriceObject> getItemPrices(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemPricesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemPricesByItem(item);

            try(var objectLimiter = new ObjectLimiter(env, ItemPriceConstants.COMPONENT_VENDOR_NAME, ItemPriceConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemPricesByItem(item);
                var itemPrices = entities.stream().map(ItemPriceObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemPrices);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("item unit of measure types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemUnitOfMeasureTypeObject> getItemUnitOfMeasureTypes(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemUnitOfMeasureTypesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemUnitOfMeasureTypesByItem(item);

            try(var objectLimiter = new ObjectLimiter(env, ItemUnitOfMeasureTypeConstants.COMPONENT_VENDOR_NAME, ItemUnitOfMeasureTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemUnitOfMeasureTypesByItem(item);
                var itemAliass = entities.stream().map(ItemUnitOfMeasureTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemAliass);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("item aliases")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemAliasObject> getItemAliases(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemAliasesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemAliasesByItem(item);

            try(var objectLimiter = new ObjectLimiter(env, ItemAliasConstants.COMPONENT_VENDOR_NAME, ItemAliasConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemAliasesByItem(item);
                var itemAliases = entities.stream().map(ItemAliasObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemAliases);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionType = itemControl.getItemDescriptionTypeByName(ItemConstants.ItemDescriptionType_DEFAULT_DESCRIPTION);

        return itemDescriptionType == null ? null : itemControl.getBestItemStringDescription(itemDescriptionType, item, BaseGraphQl.getLanguageEntity(env));
    }

    @GraphQLField
    @GraphQLDescription("item status")
    public WorkflowEntityStatusObject getItemStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, ItemStatusConstants.Workflow_ITEM_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("offer items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<OfferItemObject> getOfferItems(final DataFetchingEnvironment env) {
        if(OfferSecurityUtils.getHasOfferItemsAccess(env)) {
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            var totalCount = offerItemControl.countOfferItemsByItem(item);

            try(var objectLimiter = new ObjectLimiter(env, OfferItemConstants.COMPONENT_VENDOR_NAME, OfferItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = offerItemControl.getOfferItemsByItem(item);
                var offerItems = entities.stream().map(OfferItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, offerItems);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("related item types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<RelatedItemTypeObject> getRelatedItemTypes(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasRelatedItemTypesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countRelatedItemTypes();

            try(var objectLimiter = new ObjectLimiter(env, RelatedItemTypeConstants.COMPONENT_VENDOR_NAME, RelatedItemTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getRelatedItemTypes();
                var relatedItemTypes = entities.stream()
                        .map((RelatedItemType relatedItemType) -> new RelatedItemTypeObject(relatedItemType, item))
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, relatedItemTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }


    @GraphQLField
    @GraphQLDescription("vendor items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<VendorItemObject> getVendorItems(final DataFetchingEnvironment env) {
        if(VendorSecurityUtils.getHasVendorItemsAccess(env)) {
            var itemControl = Session.getModelController(VendorControl.class);
            var totalCount = itemControl.countVendorItemsByItem(item);

            try(var objectLimiter = new ObjectLimiter(env, VendorItemConstants.COMPONENT_VENDOR_NAME, VendorItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getVendorItemsByItem(item);
                var items = entities.stream().map(VendorItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
