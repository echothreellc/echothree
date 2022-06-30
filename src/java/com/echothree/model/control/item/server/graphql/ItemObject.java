// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
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
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.item.common.ItemPriceConstants;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.offer.common.OfferItemConstants;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
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
        return ItemSecurityUtils.getInstance().getHasItemTypeAccess(env) ? new ItemTypeObject(getItemDetail().getItemType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item use type")
    @GraphQLNonNull
    public ItemUseTypeObject getItemUseType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemUseTypeAccess(env) ? new ItemUseTypeObject(getItemDetail().getItemUseType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item category")
    public ItemCategoryObject getItemCategory(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemCategoryAccess(env) ? new ItemCategoryObject(getItemDetail().getItemCategory()) : null;
    }

    //| itm_iactgc_itemaccountingcategoryid | bigint      | YES  |     | NULL    |       |
    //| itm_iprchc_itempurchasingcategoryid | bigint      | YES  |     | NULL    |       |

    @GraphQLField
    @GraphQLDescription("company")
    @GraphQLNonNull
    public CompanyObject getCompany(final DataFetchingEnvironment env) {
        var companyParty = getItemDetail().getCompanyParty();

        return PartySecurityUtils.getInstance().getHasPartyAccess(env, companyParty) ? new CompanyObject(companyParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("item delivery type")
    @GraphQLNonNull
    public ItemDeliveryTypeObject getItemDeliveryType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemDeliveryTypeAccess(env) ? new ItemDeliveryTypeObject(getItemDetail().getItemDeliveryType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item inventory type")
    @GraphQLNonNull
    public ItemInventoryTypeObject getItemInventoryType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemInventoryTypeAccess(env) ? new ItemInventoryTypeObject(getItemDetail().getItemInventoryType()) : null;
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

        return salesOrderSequence == null ? null : (SequenceSecurityUtils.getInstance().getHasSequenceAccess(env) ? new SequenceObject(salesOrderSequence) : null);
    }

    @GraphQLField
    @GraphQLDescription("shipping charge exempt")
    @GraphQLNonNull
    public Boolean getShippingChargeExempt() {
        return getItemDetail().getShippingChargeExempt();
    }

    @GraphQLField
    @GraphQLDescription("unformatted shipping start time")
    @GraphQLNonNull
    public Long getUnformattedShippingStartTime() {
        return getItemDetail().getShippingStartTime();
    }

    @GraphQLField
    @GraphQLDescription("shipping start time")
    @GraphQLNonNull
    public String getShippingStartTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), getItemDetail().getShippingStartTime());
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted shipping end time")
    public Long getUnformattedShippingEndTime() {
        return getItemDetail().getShippingEndTime();
    }

    @GraphQLField
    @GraphQLDescription("shipping end time")
    public String getShippingEndTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), getItemDetail().getShippingEndTime());
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted sales order start time")
    @GraphQLNonNull
    public Long getUnformattedSalesOrderStartTime() {
        return getItemDetail().getSalesOrderStartTime();
    }

    @GraphQLField
    @GraphQLDescription("sales order start time")
    @GraphQLNonNull
    public String getSalesOrderStartTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), getItemDetail().getSalesOrderStartTime());
    }

    @GraphQLField
    @GraphQLDescription("unformatted sales order end time")
    public Long getUnformattedSalesOrderEndTime() {
        return getItemDetail().getSalesOrderEndTime();
    }

    @GraphQLField
    @GraphQLDescription("sales order end time")
    public String getSalesOrderEndTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), getItemDetail().getSalesOrderEndTime());
    }

    @GraphQLField
    @GraphQLDescription("unformatted purchase order start time")
    public Long getUnformattedPurchaseOrderStartTime() {
        return getItemDetail().getPurchaseOrderStartTime();
    }

    @GraphQLField
    @GraphQLDescription("purchase order start time")
    public String getPurchaseOrderStartTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), getItemDetail().getPurchaseOrderStartTime());
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted purchase order end time")
    public Long getUnformattedPurchaseOrderEndTime() {
        return getItemDetail().getPurchaseOrderEndTime();
    }

    @GraphQLField
    @GraphQLDescription("purchase order end time")
    public String getPurchaseOrderEndTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), getItemDetail().getPurchaseOrderEndTime());
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
        return UomSecurityUtils.getInstance().getHasUnitOfMeasureKindAccess(env) ? new UnitOfMeasureKindObject(getItemDetail().getUnitOfMeasureKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item price type")
    public ItemPriceTypeObject getItemPriceType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemPriceTypeAccess(env) ? new ItemPriceTypeObject(getItemDetail().getItemPriceType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var cancellationPolicy = getItemDetail().getCancellationPolicy();

        return cancellationPolicy == null ? null : new CancellationPolicyObject(cancellationPolicy);
        //return CancellationSecurityUtils.getInstance().getHasCancellationPolicyAccess(env) ? new CancellationPolicyObject(getCancellationTypeDetail().getCancellationPolicy()) : null;
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var returnPolicy = getItemDetail().getReturnPolicy();

        return returnPolicy == null ? null : (ReturnPolicySecurityUtils.getInstance().getHasReturnPolicyAccess(env) ? new ReturnPolicyObject(returnPolicy) : null);
    }

    //| itm_stylpth_stylepathid             | bigint      | YES  |     | NULL    |       |

    @GraphQLField
    @GraphQLDescription("itemPrices")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemPriceObject> getItemPrices(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getInstance().getHasItemPricesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemPricesByItem(item);

            try(var objectLimiter = new ObjectLimiter(env, ItemPriceConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemPricesByItem(item);
                var itemPrices = entities.stream().map(ItemPriceObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemPrices);
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

        return itemDescriptionType == null ? null : itemControl.getBestItemStringDescription(itemDescriptionType, item, getLanguageEntity(env));
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
        if(OfferSecurityUtils.getInstance().getHasOfferItemsAccess(env)) {
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            var totalCount = offerItemControl.countOfferItemsByItem(item);

            try(var objectLimiter = new ObjectLimiter(env, OfferItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = offerItemControl.getOfferItemsByItem(item);
                var offerItems = entities.stream().map(OfferItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, offerItems);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
