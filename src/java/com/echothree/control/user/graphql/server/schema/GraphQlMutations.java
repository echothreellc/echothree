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

package com.echothree.control.user.graphql.server.schema;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.CreateGlAccountResult;
import com.echothree.control.user.accounting.common.result.CreateItemAccountingCategoryResult;
import com.echothree.control.user.accounting.common.result.CreateTransactionTimeTypeResult;
import com.echothree.control.user.accounting.common.result.EditGlAccountResult;
import com.echothree.control.user.accounting.common.result.EditItemAccountingCategoryResult;
import com.echothree.control.user.accounting.common.result.EditTransactionTimeTypeResult;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.campaign.common.CampaignUtil;
import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.CreateContentCatalogResult;
import com.echothree.control.user.content.common.result.CreateContentCategoryResult;
import com.echothree.control.user.content.common.result.CreateContentCollectionResult;
import com.echothree.control.user.content.common.result.CreateContentPageLayoutResult;
import com.echothree.control.user.content.common.result.CreateContentWebAddressResult;
import com.echothree.control.user.content.common.result.EditContentCatalogResult;
import com.echothree.control.user.content.common.result.EditContentCategoryItemResult;
import com.echothree.control.user.content.common.result.EditContentCategoryResult;
import com.echothree.control.user.content.common.result.EditContentCollectionResult;
import com.echothree.control.user.content.common.result.EditContentPageLayoutResult;
import com.echothree.control.user.content.common.result.EditContentWebAddressResult;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.CreateComponentVendorResult;
import com.echothree.control.user.core.common.result.CreateEntityAliasTypeResult;
import com.echothree.control.user.core.common.result.CreateEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.CreateEntityAttributeResult;
import com.echothree.control.user.core.common.result.CreateEntityInstanceResult;
import com.echothree.control.user.core.common.result.CreateEntityListItemResult;
import com.echothree.control.user.core.common.result.CreateEntityTypeResult;
import com.echothree.control.user.core.common.result.EditComponentVendorResult;
import com.echothree.control.user.core.common.result.EditEntityAliasResult;
import com.echothree.control.user.core.common.result.EditEntityAliasTypeResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityBooleanAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityBooleanDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityClobAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityDateAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityDateDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityEntityAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityGeoPointAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityGeoPointDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityIntegerAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityIntegerDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityListItemAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityListItemDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityListItemResult;
import com.echothree.control.user.core.common.result.EditEntityLongAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityLongDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityNameAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityStringAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityStringDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityTimeAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityTimeDefaultResult;
import com.echothree.control.user.core.common.result.EditEntityTypeResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.CreateCustomerTypeResult;
import com.echothree.control.user.customer.common.result.EditCustomerTypeResult;
import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.CreateFilterAdjustmentResult;
import com.echothree.control.user.filter.common.result.CreateFilterResult;
import com.echothree.control.user.filter.common.result.CreateFilterStepResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentAmountResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentFixedAmountResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentPercentResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentResult;
import com.echothree.control.user.filter.common.result.EditFilterResult;
import com.echothree.control.user.filter.common.result.EditFilterStepResult;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.CreateAllocationPriorityResult;
import com.echothree.control.user.inventory.common.result.CreateInventoryAdjustmentTypeResult;
import com.echothree.control.user.inventory.common.result.CreateInventoryConditionResult;
import com.echothree.control.user.inventory.common.result.CreateInventoryTransactionTypeResult;
import com.echothree.control.user.inventory.common.result.EditAllocationPriorityResult;
import com.echothree.control.user.inventory.common.result.EditInventoryAdjustmentTypeResult;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionResult;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionTypeResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemAliasTypeResult;
import com.echothree.control.user.item.common.result.CreateItemCategoryResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionTypeResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.result.CreateItemImageTypeResult;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.control.user.item.common.result.CreateItemVolumeTypeResult;
import com.echothree.control.user.item.common.result.CreateItemWeightTypeResult;
import com.echothree.control.user.item.common.result.CreateRelatedItemResult;
import com.echothree.control.user.item.common.result.CreateRelatedItemTypeResult;
import com.echothree.control.user.item.common.result.EditItemAliasResult;
import com.echothree.control.user.item.common.result.EditItemAliasTypeResult;
import com.echothree.control.user.item.common.result.EditItemCategoryResult;
import com.echothree.control.user.item.common.result.EditItemDescriptionResult;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeResult;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.result.EditItemImageTypeResult;
import com.echothree.control.user.item.common.result.EditItemPriceResult;
import com.echothree.control.user.item.common.result.EditItemResult;
import com.echothree.control.user.item.common.result.EditItemUnitOfMeasureTypeResult;
import com.echothree.control.user.item.common.result.EditItemVolumeTypeResult;
import com.echothree.control.user.item.common.result.EditItemWeightTypeResult;
import com.echothree.control.user.item.common.result.EditRelatedItemResult;
import com.echothree.control.user.item.common.result.EditRelatedItemTypeResult;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.CreateOfferItemResult;
import com.echothree.control.user.offer.common.result.CreateOfferNameElementResult;
import com.echothree.control.user.offer.common.result.CreateOfferResult;
import com.echothree.control.user.offer.common.result.CreateOfferUseResult;
import com.echothree.control.user.offer.common.result.CreateUseNameElementResult;
import com.echothree.control.user.offer.common.result.CreateUseResult;
import com.echothree.control.user.offer.common.result.CreateUseTypeResult;
import com.echothree.control.user.offer.common.result.EditOfferItemPriceResult;
import com.echothree.control.user.offer.common.result.EditOfferNameElementResult;
import com.echothree.control.user.offer.common.result.EditOfferResult;
import com.echothree.control.user.offer.common.result.EditOfferUseResult;
import com.echothree.control.user.offer.common.result.EditUseNameElementResult;
import com.echothree.control.user.offer.common.result.EditUseResult;
import com.echothree.control.user.offer.common.result.EditUseTypeResult;
import com.echothree.control.user.order.common.OrderUtil;
import com.echothree.control.user.order.common.result.CreateOrderPriorityResult;
import com.echothree.control.user.order.common.result.CreateOrderTimeTypeResult;
import com.echothree.control.user.order.common.result.CreateOrderTypeResult;
import com.echothree.control.user.order.common.result.EditOrderPriorityResult;
import com.echothree.control.user.order.common.result.EditOrderTimeTypeResult;
import com.echothree.control.user.order.common.result.EditOrderTypeResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.control.user.party.common.result.CreateCustomerWithLoginResult;
import com.echothree.control.user.party.common.result.CreatePartyAliasTypeResult;
import com.echothree.control.user.party.common.result.CreateVendorResult;
import com.echothree.control.user.party.common.result.EditPartyAliasResult;
import com.echothree.control.user.party.common.result.EditPartyAliasTypeResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.CreatePaymentMethodTypeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorActionTypeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorResultCodeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentMethodTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorActionTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResultCodeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorTypeResult;
import com.echothree.control.user.period.common.PeriodUtil;
import com.echothree.control.user.period.common.result.CreateFiscalYearResult;
import com.echothree.control.user.sales.common.SalesUtil;
import com.echothree.control.user.sales.common.result.CreateSalesOrderLineResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderResult;
import com.echothree.control.user.sales.common.result.EditSalesOrderShipmentGroupResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.CreateSearchResultActionTypeResult;
import com.echothree.control.user.search.common.result.EditSearchResultActionTypeResult;
import com.echothree.control.user.search.common.result.SearchComponentVendorsResult;
import com.echothree.control.user.search.common.result.SearchContentCatalogItemsResult;
import com.echothree.control.user.search.common.result.SearchContentCatalogsResult;
import com.echothree.control.user.search.common.result.SearchContentCategoriesResult;
import com.echothree.control.user.search.common.result.SearchCustomersResult;
import com.echothree.control.user.search.common.result.SearchEmployeesResult;
import com.echothree.control.user.search.common.result.SearchEntityAliasTypesResult;
import com.echothree.control.user.search.common.result.SearchEntityAttributeGroupsResult;
import com.echothree.control.user.search.common.result.SearchEntityAttributesResult;
import com.echothree.control.user.search.common.result.SearchEntityListItemsResult;
import com.echothree.control.user.search.common.result.SearchEntityTypesResult;
import com.echothree.control.user.search.common.result.SearchItemsResult;
import com.echothree.control.user.search.common.result.SearchShippingMethodsResult;
import com.echothree.control.user.search.common.result.SearchVendorsResult;
import com.echothree.control.user.search.common.result.SearchWarehousesResult;
import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.result.CreateSecurityRoleGroupResult;
import com.echothree.control.user.security.common.result.CreateSecurityRoleResult;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupResult;
import com.echothree.control.user.security.common.result.EditSecurityRoleResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.CreateSelectorResult;
import com.echothree.control.user.selector.common.result.EditSelectorResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.CreateSequenceResult;
import com.echothree.control.user.sequence.common.result.CreateSequenceTypeResult;
import com.echothree.control.user.sequence.common.result.EditSequenceResult;
import com.echothree.control.user.sequence.common.result.EditSequenceTypeResult;
import com.echothree.control.user.sequence.common.result.GetNextSequenceValueResult;
import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.common.result.CreateFreeOnBoardResult;
import com.echothree.control.user.shipment.common.result.EditFreeOnBoardResult;
import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.result.CreateTagResult;
import com.echothree.control.user.tag.common.result.CreateTagScopeResult;
import com.echothree.control.user.tag.common.result.EditTagResult;
import com.echothree.control.user.tag.common.result.EditTagScopeResult;
import com.echothree.control.user.track.common.TrackUtil;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.EditUserLoginResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.CreateItemPurchasingCategoryResult;
import com.echothree.control.user.vendor.common.result.CreateVendorItemResult;
import com.echothree.control.user.vendor.common.result.CreateVendorTypeResult;
import com.echothree.control.user.vendor.common.result.EditItemPurchasingCategoryResult;
import com.echothree.control.user.vendor.common.result.EditVendorItemCostResult;
import com.echothree.control.user.vendor.common.result.EditVendorItemResult;
import com.echothree.control.user.vendor.common.result.EditVendorResult;
import com.echothree.control.user.vendor.common.result.EditVendorTypeResult;
import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.CreateWarehouseResult;
import com.echothree.control.user.warehouse.common.result.CreateWarehouseTypeResult;
import com.echothree.control.user.warehouse.common.result.EditWarehouseResult;
import com.echothree.control.user.warehouse.common.result.EditWarehouseTypeResult;
import com.echothree.control.user.wishlist.common.WishlistUtil;
import com.echothree.control.user.wishlist.common.result.CreateWishlistPriorityResult;
import com.echothree.control.user.wishlist.common.result.CreateWishlistTypeResult;
import com.echothree.control.user.wishlist.common.result.EditWishlistPriorityResult;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypeResult;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.CreateWorkflowDestinationResult;
import com.echothree.control.user.workflow.common.result.CreateWorkflowEntranceResult;
import com.echothree.control.user.workflow.common.result.CreateWorkflowResult;
import com.echothree.control.user.workflow.common.result.CreateWorkflowStepResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepResult;
import com.echothree.model.control.graphql.server.graphql.MutationResultObject;
import com.echothree.model.control.graphql.server.graphql.MutationResultWithIdObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.sales.server.graphql.CreateSalesOrderLineResultObject;
import com.echothree.model.control.sales.server.graphql.CreateSalesOrderResultObject;
import com.echothree.model.control.search.server.graphql.SearchComponentVendorsResultObject;
import com.echothree.model.control.search.server.graphql.SearchContentCatalogItemsResultObject;
import com.echothree.model.control.search.server.graphql.SearchContentCatalogsResultObject;
import com.echothree.model.control.search.server.graphql.SearchContentCategoriesResultObject;
import com.echothree.model.control.search.server.graphql.SearchCustomersResultObject;
import com.echothree.model.control.search.server.graphql.SearchEmployeesResultObject;
import com.echothree.model.control.search.server.graphql.SearchEntityAliasTypesResultObject;
import com.echothree.model.control.search.server.graphql.SearchEntityAttributeGroupsResultObject;
import com.echothree.model.control.search.server.graphql.SearchEntityAttributesResultObject;
import com.echothree.model.control.search.server.graphql.SearchEntityListItemsResultObject;
import com.echothree.model.control.search.server.graphql.SearchEntityTypesResultObject;
import com.echothree.model.control.search.server.graphql.SearchItemsResultObject;
import com.echothree.model.control.search.server.graphql.SearchShippingMethodsResultObject;
import com.echothree.model.control.search.server.graphql.SearchVendorsResultObject;
import com.echothree.model.control.search.server.graphql.SearchWarehousesResultObject;
import com.echothree.model.control.sequence.server.graphql.GetNextSequenceValueResultObject;
import com.echothree.util.common.command.EditMode;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLID;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.annotationTypes.GraphQLRelayMutation;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import javax.naming.NamingException;

@GraphQLName("mutation")
public interface GraphQlMutations {

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createContentCollection(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ContentUtil.getHome().getCreateContentCollectionForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setDefaultOfferName(defaultOfferName);
            commandForm.setDefaultUseName(defaultUseName);
            commandForm.setDefaultSourceName(defaultSourceName);
            commandForm.setDescription(description);

            var commandResult = ContentUtil.getHome().createContentCollection(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateContentCollectionResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteContentCollection(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentCollectionForm();

            commandForm.setContentCollectionName(contentCollectionName);

            var commandResult = ContentUtil.getHome().deleteContentCollection(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editContentCollection(final DataFetchingEnvironment env,
            @GraphQLName("originalContentCollectionName") @GraphQLNonNull final String originalContentCollectionName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ContentUtil.getHome().getContentCollectionSpec();

            spec.setContentCollectionName(originalContentCollectionName);

            var commandForm = ContentUtil.getHome().getEditContentCollectionForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentCollection(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentCollectionResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getContentCollection().getEntityInstance());

                if(arguments.containsKey("contentCollectionName"))
                    edit.setContentCollectionName(contentCollectionName);
                if(arguments.containsKey("defaultOfferName"))
                    edit.setDefaultOfferName(defaultOfferName);
                if(arguments.containsKey("defaultUseName"))
                    edit.setDefaultUseName(defaultUseName);
                if(arguments.containsKey("defaultSourceName"))
                    edit.setDefaultSourceName(defaultSourceName);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ContentUtil.getHome().editContentCollection(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createContentWebAddress(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") @GraphQLNonNull final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ContentUtil.getHome().getCreateContentWebAddressForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setDescription(description);

            var commandResult = ContentUtil.getHome().createContentWebAddress(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateContentWebAddressResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteContentWebAddress(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") @GraphQLNonNull final String contentWebAddressName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentWebAddressForm();

            commandForm.setContentWebAddressName(contentWebAddressName);

            var commandResult = ContentUtil.getHome().deleteContentWebAddress(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editContentWebAddress(final DataFetchingEnvironment env,
            @GraphQLName("originalContentWebAddressName") @GraphQLNonNull final String originalContentWebAddressName,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ContentUtil.getHome().getContentWebAddressSpec();

            spec.setContentWebAddressName(originalContentWebAddressName);

            var commandForm = ContentUtil.getHome().getEditContentWebAddressForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentWebAddress(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentWebAddressResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getContentWebAddress().getEntityInstance());

                if(arguments.containsKey("contentWebAddressName"))
                    edit.setContentWebAddressName(contentWebAddressName);
                if(arguments.containsKey("contentCollectionName"))
                    edit.setContentCollectionName(contentCollectionName);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ContentUtil.getHome().editContentWebAddress(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createContentCatalog(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ContentUtil.getHome().getCreateContentCatalogForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setDefaultOfferName(defaultOfferName);
            commandForm.setDefaultUseName(defaultUseName);
            commandForm.setDefaultSourceName(defaultSourceName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ContentUtil.getHome().createContentCatalog(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateContentCatalogResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteContentCatalog(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentCatalogForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);

            var commandResult = ContentUtil.getHome().deleteContentCatalog(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editContentCatalog(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("originalContentCatalogName") @GraphQLNonNull final String originalContentCatalogName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ContentUtil.getHome().getContentCatalogSpec();

            spec.setContentCollectionName(contentCollectionName);
            spec.setContentCatalogName(originalContentCatalogName);

            var commandForm = ContentUtil.getHome().getEditContentCatalogForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentCatalog(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentCatalogResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getContentCatalog().getEntityInstance());

                if(arguments.containsKey("contentCatalogName"))
                    edit.setContentCatalogName(contentCatalogName);
                if(arguments.containsKey("defaultOfferName"))
                    edit.setDefaultOfferName(defaultOfferName);
                if(arguments.containsKey("defaultUseName"))
                    edit.setDefaultUseName(defaultUseName);
                if(arguments.containsKey("defaultSourceName"))
                    edit.setDefaultSourceName(defaultSourceName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ContentUtil.getHome().editContentCatalog(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createContentCategory(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("contentCategoryName") @GraphQLNonNull final String contentCategoryName,
            @GraphQLName("parentContentCategoryName") final String parentContentCategoryName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("contentCategoryItemSelectorName") final String contentCategoryItemSelectorName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ContentUtil.getHome().getCreateContentCategoryForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setParentContentCategoryName(parentContentCategoryName);
            commandForm.setDefaultOfferName(defaultOfferName);
            commandForm.setDefaultUseName(defaultUseName);
            commandForm.setDefaultSourceName(defaultSourceName);
            commandForm.setContentCategoryItemSelectorName(contentCategoryItemSelectorName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ContentUtil.getHome().createContentCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateContentCategoryResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteContentCategory(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("contentCategoryName") @GraphQLNonNull final String contentCategoryName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentCategoryForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);

            var commandResult = ContentUtil.getHome().deleteContentCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editContentCategory(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("originalContentCategoryName") @GraphQLNonNull final String originalContentCategoryName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("parentContentCategoryName") final String parentContentCategoryName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("contentCategoryItemSelectorName") final String contentCategoryItemSelectorName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ContentUtil.getHome().getContentCategorySpec();

            spec.setContentCollectionName(contentCollectionName);
            spec.setContentCatalogName(contentCatalogName);
            spec.setContentCategoryName(originalContentCategoryName);

            var commandForm = ContentUtil.getHome().getEditContentCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentCategory(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentCategoryResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getContentCategory().getEntityInstance());

                if(arguments.containsKey("contentCategoryName"))
                    edit.setContentCategoryName(contentCategoryName);
                if(arguments.containsKey("parentContentCategoryName"))
                    edit.setParentContentCategoryName(parentContentCategoryName);
                if(arguments.containsKey("defaultOfferName"))
                    edit.setDefaultOfferName(defaultOfferName);
                if(arguments.containsKey("defaultUseName"))
                    edit.setDefaultUseName(defaultUseName);
                if(arguments.containsKey("defaultSourceName"))
                    edit.setDefaultSourceName(defaultSourceName);
                if(arguments.containsKey("contentCategoryItemSelectorName"))
                    edit.setContentCategoryItemSelectorName(contentCategoryItemSelectorName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ContentUtil.getHome().editContentCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createContentCategoryItem(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("contentCategoryName") @GraphQLNonNull final String contentCategoryName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getCreateContentCategoryItemForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);

            var commandResult = ContentUtil.getHome().createContentCategoryItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteContentCategoryItem(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("contentCategoryName") @GraphQLNonNull final String contentCategoryName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentCategoryItemForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = ContentUtil.getHome().deleteContentCategoryItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editContentCategoryItem(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("contentCategoryName") @GraphQLNonNull final String contentCategoryName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = ContentUtil.getHome().getContentCategoryItemSpec();

            spec.setContentCollectionName(contentCollectionName);
            spec.setContentCatalogName(contentCatalogName);
            spec.setContentCategoryName(contentCategoryName);
            spec.setItemName(itemName);
            spec.setInventoryConditionName(inventoryConditionName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = ContentUtil.getHome().getEditContentCategoryItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentCategoryItem(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentCategoryItemResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ContentUtil.getHome().editContentCategoryItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createUserVisitTrack(final DataFetchingEnvironment env,
            @GraphQLName("trackValue") final String trackValue) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TrackUtil.getHome().getCreateUserVisitTrackForm();

            commandForm.setTrackValue(trackValue);

            var commandResult = TrackUtil.getHome().createUserVisitTrack(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createUserVisitCampaign(final DataFetchingEnvironment env,
            @GraphQLName("campaignValue") final String campaignValue,
            @GraphQLName("campaignSourceValue") final String campaignSourceValue,
            @GraphQLName("campaignMediumValue") final String campaignMediumValue,
            @GraphQLName("campaignTermValue") final String campaignTermValue,
            @GraphQLName("campaignContentValue") final String campaignContentValue) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CampaignUtil.getHome().getCreateUserVisitCampaignForm();

            commandForm.setCampaignValue(campaignValue);
            commandForm.setCampaignSourceValue(campaignSourceValue);
            commandForm.setCampaignMediumValue(campaignMediumValue);
            commandForm.setCampaignTermValue(campaignTermValue);
            commandForm.setCampaignContentValue(campaignContentValue);

            var commandResult = CampaignUtil.getHome().createUserVisitCampaign(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = SelectorUtil.getHome().getCreateSelectorForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = SelectorUtil.getHome().createSelector(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSelectorResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SelectorUtil.getHome().getDeleteSelectorForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorName(selectorName);

            var commandResult = SelectorUtil.getHome().deleteSelector(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("originalSelectorName") final String originalSelectorName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("selectorName") final String selectorName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = SelectorUtil.getHome().getSelectorUniversalSpec();

            spec.setSelectorKindName(selectorKindName);
            spec.setSelectorTypeName(selectorTypeName);
            spec.setSelectorName(originalSelectorName);
            spec.setUuid(id);

            var commandForm = SelectorUtil.getHome().getEditSelectorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SelectorUtil.getHome().editSelector(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSelectorResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getSelector().getEntityInstance());

                if(arguments.containsKey("selectorName"))
                    edit.setSelectorName(selectorName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SelectorUtil.getHome().editSelector(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("initialFilterAdjustmentName") @GraphQLNonNull final String initialFilterAdjustmentName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);
            commandForm.setInitialFilterAdjustmentName(initialFilterAdjustmentName);
            commandForm.setFilterItemSelectorName(filterItemSelectorName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = FilterUtil.getHome().createFilter(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFilterResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterName(filterName);

            var commandResult = FilterUtil.getHome().deleteFilter(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("originalFilterName") final String originalFilterName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("filterName") final String filterName,
            @GraphQLName("initialFilterAdjustmentName") @GraphQLNonNull final String initialFilterAdjustmentName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = FilterUtil.getHome().getFilterUniversalSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterTypeName(filterTypeName);
            spec.setFilterName(originalFilterName);
            spec.setUuid(id);

            var commandForm = FilterUtil.getHome().getEditFilterForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilter(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getFilter().getEntityInstance());

                if(arguments.containsKey("filterName"))
                    edit.setFilterName(filterName);
                if(arguments.containsKey("initialFilterAdjustmentName"))
                    edit.setInitialFilterAdjustmentName(initialFilterAdjustmentName);
                if(arguments.containsKey("filterItemSelectorName"))
                    edit.setFilterItemSelectorName(filterItemSelectorName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilter(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createFilterStep(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterStepName") @GraphQLNonNull final String filterStepName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterStepForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);
            commandForm.setFilterStepName(filterStepName);
            commandForm.setFilterItemSelectorName(filterItemSelectorName);
            commandForm.setDescription(description);

            var commandResult = FilterUtil.getHome().createFilterStep(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFilterStepResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteFilterStep(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("filterStepName") @GraphQLNonNull final String filterStepName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterStepForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterStepName(filterStepName);
            commandForm.setFilterName(filterName);

            var commandResult = FilterUtil.getHome().deleteFilterStep(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editFilterStep(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("originalFilterStepName") final String originalFilterStepName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("filterStepName") final String filterStepName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = FilterUtil.getHome().getFilterStepUniversalSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterTypeName(filterTypeName);
            spec.setFilterName(filterName);
            spec.setFilterStepName(originalFilterStepName);
            spec.setUuid(id);

            var commandForm = FilterUtil.getHome().getEditFilterStepForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterStep(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterStepResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getFilterStep().getEntityInstance());

                if(arguments.containsKey("filterStepName"))
                    edit.setFilterStepName(filterStepName);
                if(arguments.containsKey("filterItemSelectorName"))
                    edit.setFilterItemSelectorName(filterItemSelectorName);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterStep(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("filterAdjustmentSourceName") @GraphQLNonNull final String filterAdjustmentSourceName,
            @GraphQLName("filterAdjustmentTypeName") final String filterAdjustmentTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
            commandForm.setFilterAdjustmentTypeName(filterAdjustmentTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = FilterUtil.getHome().createFilterAdjustment(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFilterAdjustmentResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustment(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("originalFilterAdjustmentName") final String originalFilterAdjustmentName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("filterAdjustmentName") final String filterAdjustmentName,
            @GraphQLName("filterAdjustmentSourceName") final String filterAdjustmentSourceName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentUniversalSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(originalFilterAdjustmentName);
            spec.setUuid(id);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustment(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getFilterAdjustment().getEntityInstance());

                if(arguments.containsKey("filterAdjustmentName"))
                    edit.setFilterAdjustmentName(filterAdjustmentName);
                if(arguments.containsKey("filterAdjustmentSourceName"))
                    edit.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustment(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createFilterAdjustmentAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("amount") @GraphQLNonNull final String amount) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setAmount(amount);

            var commandResult = FilterUtil.getHome().createFilterAdjustmentAmount(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteFilterAdjustmentAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentAmount(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editFilterAdjustmentAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("amount") @GraphQLNonNull final String amount) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentAmountSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(filterAdjustmentName);
            spec.setUnitOfMeasureName(unitOfMeasureName);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentAmountForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentAmountResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("amount"))
                    edit.setAmount(amount);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitAmount") @GraphQLNonNull final String unitAmount) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentFixedAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUnitAmount(unitAmount);

            var commandResult = FilterUtil.getHome().createFilterAdjustmentFixedAmount(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentFixedAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentFixedAmount(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitAmount") @GraphQLNonNull final String unitAmount) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentFixedAmountSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(filterAdjustmentName);
            spec.setUnitOfMeasureName(unitOfMeasureName);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentFixedAmountForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustmentFixedAmount(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentFixedAmountResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitAmount"))
                    edit.setUnitAmount(unitAmount);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentFixedAmount(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createFilterAdjustmentPercent(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("percent") @GraphQLNonNull final String percent) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentPercentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setPercent(percent);

            var commandResult = FilterUtil.getHome().createFilterAdjustmentPercent(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteFilterAdjustmentPercent(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentPercentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentPercent(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editFilterAdjustmentPercent(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("percent") @GraphQLNonNull final String percent) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentPercentSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(filterAdjustmentName);
            spec.setUnitOfMeasureName(unitOfMeasureName);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentPercentForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentPercentResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("percent"))
                    edit.setPercent(percent);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName,
            @GraphQLName("mask") @GraphQLNonNull final String mask,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("value") final String value,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = SequenceUtil.getHome().getCreateSequenceForm();

            commandForm.setSequenceName(sequenceName);
            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setMask(mask);
            commandForm.setChunkSize(chunkSize);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setValue(value);
            commandForm.setDescription(description);

            var commandResult = SequenceUtil.getHome().createSequence(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSequenceResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getDeleteSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);

            var commandResult = SequenceUtil.getHome().deleteSequence(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("originalSequenceName") final String originalSequenceName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("sequenceName") final String sequenceName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = SequenceUtil.getHome().getSequenceUniversalSpec();

            spec.setSequenceTypeName(sequenceTypeName);
            spec.setSequenceName(originalSequenceName);
            spec.setUuid(id);

            var commandForm = SequenceUtil.getHome().getEditSequenceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequence(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSequenceResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getSequence().getEntityInstance());

                if(arguments.containsKey("sequenceName"))
                    edit.setSequenceName(sequenceName);
                if(arguments.containsKey("chunkSize"))
                    edit.setChunkSize(chunkSize);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SequenceUtil.getHome().editSequence(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setSequenceValue")
    static MutationResultObject setSequenceValue(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("sequenceName") final String sequenceName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("value") @GraphQLNonNull final String value) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getSetSequenceValueForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);
            commandForm.setUuid(id);
            commandForm.setValue(value);

            var commandResult = SequenceUtil.getHome().setSequenceValue(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("nextSequenceValue")
    static GetNextSequenceValueResultObject nextSequenceValue(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("sequenceName") final String sequenceName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new GetNextSequenceValueResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getGetNextSequenceValueForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);
            commandForm.setUuid(id);

            var commandResult = SequenceUtil.getHome().getNextSequenceValue(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                mutationResultObject.setGetNextSequenceValueResult((GetNextSequenceValueResult)commandResult.getExecutionResult().getResult());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("prefix") final String prefix,
            @GraphQLName("suffix") final String suffix,
            @GraphQLName("sequenceEncoderTypeName") @GraphQLNonNull final String sequenceEncoderTypeName,
            @GraphQLName("sequenceChecksumTypeName") @GraphQLNonNull final String sequenceChecksumTypeName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = SequenceUtil.getHome().getCreateSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setPrefix(prefix);
            commandForm.setSuffix(suffix);
            commandForm.setSequenceEncoderTypeName(sequenceEncoderTypeName);
            commandForm.setSequenceChecksumTypeName(sequenceChecksumTypeName);
            commandForm.setChunkSize(chunkSize);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = SequenceUtil.getHome().createSequenceType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSequenceTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getDeleteSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);

            var commandResult = SequenceUtil.getHome().deleteSequenceType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("originalSequenceTypeName") final String originalSequenceTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("prefix") final String prefix,
            @GraphQLName("suffix") final String suffix,
            @GraphQLName("sequenceEncoderTypeName") final String sequenceEncoderTypeName,
            @GraphQLName("sequenceChecksumTypeName") final String sequenceChecksumTypeName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = SequenceUtil.getHome().getSequenceTypeUniversalSpec();

            spec.setSequenceTypeName(originalSequenceTypeName);
            spec.setUuid(id);

            var commandForm = SequenceUtil.getHome().getEditSequenceTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequenceType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSequenceTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getSequenceType().getEntityInstance());

                if(arguments.containsKey("sequenceTypeName"))
                    edit.setSequenceTypeName(sequenceTypeName);
                if(arguments.containsKey("prefix"))
                    edit.setPrefix(prefix);
                if(arguments.containsKey("suffix"))
                    edit.setSuffix(suffix);
                if(arguments.containsKey("sequenceEncoderTypeName"))
                    edit.setSequenceEncoderTypeName(sequenceEncoderTypeName);
                if(arguments.containsKey("sequenceChecksumTypeName"))
                    edit.setSequenceChecksumTypeName(sequenceChecksumTypeName);
                if(arguments.containsKey("chunkSize"))
                    edit.setChunkSize(chunkSize);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SequenceUtil.getHome().editSequenceType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);
            commandForm.setSalesOrderSequenceName(salesOrderSequenceName);

            var commandResult = OfferUtil.getHome().createOfferUse(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferUseResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            var commandResult = OfferUtil.getHome().deleteOfferUse(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getOfferUseSpec();

            spec.setOfferName(offerName);
            spec.setUseName(useName);

            var commandForm = OfferUtil.getHome().getEditOfferUseForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOfferUse(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferUseResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOfferUse().getEntityInstance());

                if(arguments.containsKey("salesOrderSequenceName"))
                    edit.setSalesOrderSequenceName(salesOrderSequenceName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOfferUse(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createOffer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName,
            @GraphQLName("companyName") final String companyName,
            @GraphQLName("divisionName") final String divisionName,
            @GraphQLName("departmentName") final String departmentName,
            @GraphQLName("offerItemSelectorName") final String offerItemSelectorName,
            @GraphQLName("offerItemPriceFilterName") final String offerItemPriceFilterName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferForm();

            commandForm.setOfferName(offerName);
            commandForm.setSalesOrderSequenceName(salesOrderSequenceName);
            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);
            commandForm.setDepartmentName(departmentName);
            commandForm.setOfferItemSelectorName(offerItemSelectorName);
            commandForm.setOfferItemPriceFilterName(offerItemPriceFilterName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createOffer(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOffer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferForm();

            commandForm.setOfferName(offerName);

            var commandResult = OfferUtil.getHome().deleteOffer(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editOffer(final DataFetchingEnvironment env,
            @GraphQLName("originalOfferName") final String originalOfferName,
            //@GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName,
            @GraphQLName("offerItemSelectorName") final String offerItemSelectorName,
            @GraphQLName("offerItemPriceFilterName") final String offerItemPriceFilterName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getOfferUniversalSpec();

            spec.setOfferName(originalOfferName);
            //spec.setUuid(id);

            var commandForm = OfferUtil.getHome().getEditOfferForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOffer(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOffer().getEntityInstance());

                if(arguments.containsKey("offerName"))
                    edit.setOfferName(offerName);
                if(arguments.containsKey("salesOrderSequenceName"))
                    edit.setSalesOrderSequenceName(salesOrderSequenceName);
                if(arguments.containsKey("offerItemSelectorName"))
                    edit.setOfferItemSelectorName(offerItemSelectorName);
                if(arguments.containsKey("offerItemPriceFilterName"))
                    edit.setOfferItemPriceFilterName(offerItemPriceFilterName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOffer(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createOfferItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            var commandResult = OfferUtil.getHome().createOfferItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferItemResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOfferItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            var commandResult = OfferUtil.getHome().deleteOfferItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createOfferItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferItemPriceForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUnitPrice(unitPrice);
            commandForm.setMinimumUnitPrice(minimumUnitPrice);
            commandForm.setMaximumUnitPrice(maximumUnitPrice);
            commandForm.setUnitPriceIncrement(unitPriceIncrement);

            var commandResult = OfferUtil.getHome().createOfferItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOfferItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferItemPriceForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = OfferUtil.getHome().deleteOfferItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editOfferItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = OfferUtil.getHome().getOfferItemPriceSpec();

            spec.setOfferName(offerName);
            spec.setItemName(itemName);
            spec.setInventoryConditionName(inventoryConditionName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = OfferUtil.getHome().getEditOfferItemPriceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOfferItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferItemPriceResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitPrice"))
                    edit.setUnitPrice(unitPrice);
                if(arguments.containsKey("minimumUnitPrice"))
                    edit.setMinimumUnitPrice(minimumUnitPrice);
                if(arguments.containsKey("maximumUnitPrice"))
                    edit.setMaximumUnitPrice(maximumUnitPrice);
                if(arguments.containsKey("unitPriceIncrement"))
                    edit.setUnitPriceIncrement(unitPriceIncrement);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOfferItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createUse(final DataFetchingEnvironment env,
            @GraphQLName("useName") @GraphQLNonNull final String useName,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateUseForm();

            commandForm.setUseName(useName);
            commandForm.setUseTypeName(useTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createUse(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateUseResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteUse(final DataFetchingEnvironment env,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseForm();

            commandForm.setUseName(useName);

            var commandResult = OfferUtil.getHome().deleteUse(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editUse(final DataFetchingEnvironment env,
            @GraphQLName("originalUseName") final String originalUseName,
            //@GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("useName") final String useName,
            @GraphQLName("useTypeName") final String useTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getUseUniversalSpec();

            spec.setUseName(originalUseName);
            //spec.setUuid(id);

            var commandForm = OfferUtil.getHome().getEditUseForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUse(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUseResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getUse().getEntityInstance());

                if(arguments.containsKey("useName"))
                    edit.setUseName(useName);
                if(arguments.containsKey("useTypeName"))
                    edit.setUseTypeName(useTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editUse(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createOfferNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") @GraphQLNonNull final String offerNameElementName,
            @GraphQLName("offset") @GraphQLNonNull final String offset,
            @GraphQLName("length") @GraphQLNonNull final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);
            commandForm.setOffset(offset);
            commandForm.setLength(length);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createOfferNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferNameElementResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOfferNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") @GraphQLNonNull final String offerNameElementName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);

            var commandResult = OfferUtil.getHome().deleteOfferNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editOfferNameElement(final DataFetchingEnvironment env,
            @GraphQLName("originalOfferNameElementName") final String originalOfferNameElementName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("offerNameElementName") final String offerNameElementName,
            @GraphQLName("offset") final String offset,
            @GraphQLName("length") final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getOfferNameElementUniversalSpec();

            spec.setOfferNameElementName(originalOfferNameElementName);
            spec.setUuid(id);

            var commandForm = OfferUtil.getHome().getEditOfferNameElementForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOfferNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferNameElementResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOfferNameElement().getEntityInstance());

                if(arguments.containsKey("offerNameElementName"))
                    edit.setOfferNameElementName(offerNameElementName);
                if(arguments.containsKey("offset"))
                    edit.setOffset(offset);
                if(arguments.containsKey("length"))
                    edit.setLength(length);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOfferNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createUseNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") @GraphQLNonNull final String useNameElementName,
            @GraphQLName("offset") @GraphQLNonNull final String offset,
            @GraphQLName("length") @GraphQLNonNull final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);
            commandForm.setOffset(offset);
            commandForm.setLength(length);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createUseNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateUseNameElementResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteUseNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") @GraphQLNonNull final String useNameElementName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);

            var commandResult = OfferUtil.getHome().deleteUseNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editUseNameElement(final DataFetchingEnvironment env,
            @GraphQLName("originalUseNameElementName") final String originalUseNameElementName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("useNameElementName") final String useNameElementName,
            @GraphQLName("offset") final String offset,
            @GraphQLName("length") final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getUseNameElementUniversalSpec();

            spec.setUseNameElementName(originalUseNameElementName);
            spec.setUuid(id);

            var commandForm = OfferUtil.getHome().getEditUseNameElementForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUseNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUseNameElementResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getUseNameElement().getEntityInstance());

                if(arguments.containsKey("useNameElementName"))
                    edit.setUseNameElementName(useNameElementName);
                if(arguments.containsKey("offset"))
                    edit.setOffset(offset);
                if(arguments.containsKey("length"))
                    edit.setLength(length);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editUseNameElement(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createUseType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateUseTypeForm();

            commandForm.setUseTypeName(useTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createUseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateUseTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteUseType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseTypeForm();

            commandForm.setUseTypeName(useTypeName);

            var commandResult = OfferUtil.getHome().deleteUseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editUseType(final DataFetchingEnvironment env,
            @GraphQLName("originalUseTypeName") final String originalUseTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("useTypeName") final String useTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getUseTypeUniversalSpec();

            spec.setUseTypeName(originalUseTypeName);
            spec.setUuid(id);

            var commandForm = OfferUtil.getHome().getEditUseTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUseType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUseTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getUseType().getEntityInstance());

                if(arguments.containsKey("useTypeName"))
                    edit.setUseTypeName(useTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editUseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") @GraphQLNonNull final String freeOnBoardName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ShipmentUtil.getHome().getCreateFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ShipmentUtil.getHome().createFreeOnBoard(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFreeOnBoardResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") @GraphQLNonNull final String freeOnBoardName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ShipmentUtil.getHome().getDeleteFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);

            var commandResult = ShipmentUtil.getHome().deleteFreeOnBoard(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("originalFreeOnBoardName") final String originalFreeOnBoardName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("freeOnBoardName") final String freeOnBoardName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ShipmentUtil.getHome().getFreeOnBoardUniversalSpec();

            spec.setFreeOnBoardName(originalFreeOnBoardName);
            spec.setUuid(id);

            var commandForm = ShipmentUtil.getHome().getEditFreeOnBoardForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ShipmentUtil.getHome().editFreeOnBoard(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFreeOnBoardResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getFreeOnBoard().getEntityInstance());

                if(arguments.containsKey("freeOnBoardName"))
                    edit.setFreeOnBoardName(freeOnBoardName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ShipmentUtil.getHome().editFreeOnBoard(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createPaymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") @GraphQLNonNull final String paymentProcessorName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessor(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deletePaymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") @GraphQLNonNull final String paymentProcessorName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessor(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editPaymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorName") final String originalPaymentProcessorName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorName") final String paymentProcessorName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorUniversalSpec();

            spec.setPaymentProcessorName(originalPaymentProcessorName);
            spec.setUuid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessor(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getPaymentProcessor().getEntityInstance());

                if(arguments.containsKey("paymentProcessorName"))
                    edit.setPaymentProcessorName(paymentProcessorName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessor(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deletePaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorTypeName") final String originalPaymentProcessorTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorTypeUniversalSpec();

            spec.setPaymentProcessorTypeName(originalPaymentProcessorTypeName);
            spec.setUuid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getPaymentProcessorType().getEntityInstance());

                if(arguments.containsKey("paymentProcessorTypeName"))
                    edit.setPaymentProcessorTypeName(paymentProcessorTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentMethodType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentMethodTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deletePaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentMethodType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentMethodTypeName") final String originalPaymentMethodTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentMethodTypeName") final String paymentMethodTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentMethodTypeUniversalSpec();

            spec.setPaymentMethodTypeName(originalPaymentMethodTypeName);
            spec.setUuid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentMethodTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentMethodType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentMethodTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getPaymentMethodType().getEntityInstance());

                if(arguments.containsKey("paymentMethodTypeName"))
                    edit.setPaymentMethodTypeName(paymentMethodTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentMethodType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorResultCode(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorResultCodeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deletePaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorResultCode(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorResultCodeName") final String originalPaymentProcessorResultCodeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorResultCodeName") final String paymentProcessorResultCodeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorResultCodeUniversalSpec();

            spec.setPaymentProcessorResultCodeName(originalPaymentProcessorResultCodeName);
            spec.setUuid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorResultCodeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorResultCodeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getPaymentProcessorResultCode().getEntityInstance());

                if(arguments.containsKey("paymentProcessorResultCodeName"))
                    edit.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorActionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorActionTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deletePaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorActionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorActionTypeName") final String originalPaymentProcessorActionTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorActionTypeName") final String paymentProcessorActionTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorActionTypeUniversalSpec();

            spec.setPaymentProcessorActionTypeName(originalPaymentProcessorActionTypeName);
            spec.setUuid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorActionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorActionTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getPaymentProcessorActionType().getEntityInstance());

                if(arguments.containsKey("paymentProcessorActionTypeName"))
                    edit.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createAllocationPriority(final DataFetchingEnvironment env,
            @GraphQLName("allocationPriorityName") @GraphQLNonNull final String allocationPriorityName,
            @GraphQLName("priority") @GraphQLNonNull final String priority,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = InventoryUtil.getHome().getCreateAllocationPriorityForm();

            commandForm.setAllocationPriorityName(allocationPriorityName);
            commandForm.setPriority(priority);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = InventoryUtil.getHome().createAllocationPriority(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateAllocationPriorityResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteAllocationPriority(final DataFetchingEnvironment env,
            @GraphQLName("allocationPriorityName") @GraphQLNonNull final String allocationPriorityName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getDeleteAllocationPriorityForm();

            commandForm.setAllocationPriorityName(allocationPriorityName);

            var commandResult = InventoryUtil.getHome().deleteAllocationPriority(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editAllocationPriority(final DataFetchingEnvironment env,
            @GraphQLName("originalAllocationPriorityName") final String originalAllocationPriorityName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("allocationPriorityName") final String allocationPriorityName,
            @GraphQLName("priority") final String priority,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = InventoryUtil.getHome().getAllocationPriorityUniversalSpec();

            spec.setAllocationPriorityName(originalAllocationPriorityName);
            spec.setUuid(id);

            var commandForm = InventoryUtil.getHome().getEditAllocationPriorityForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editAllocationPriority(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditAllocationPriorityResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getAllocationPriority().getEntityInstance());

                if(arguments.containsKey("allocationPriorityName"))
                    edit.setAllocationPriorityName(allocationPriorityName);
                if(arguments.containsKey("priority"))
                    edit.setPriority(priority);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = InventoryUtil.getHome().editAllocationPriority(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = InventoryUtil.getHome().getCreateInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = InventoryUtil.getHome().createInventoryCondition(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateInventoryConditionResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getDeleteInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);

            var commandResult = InventoryUtil.getHome().deleteInventoryCondition(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("originalInventoryConditionName") final String originalInventoryConditionName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = InventoryUtil.getHome().getInventoryConditionUniversalSpec();

            spec.setInventoryConditionName(originalInventoryConditionName);
            spec.setUuid(id);

            var commandForm = InventoryUtil.getHome().getEditInventoryConditionForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editInventoryCondition(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditInventoryConditionResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getInventoryCondition().getEntityInstance());

                if(arguments.containsKey("inventoryConditionName"))
                    edit.setInventoryConditionName(inventoryConditionName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = InventoryUtil.getHome().editInventoryCondition(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createInventoryAdjustmentType(final DataFetchingEnvironment env,
            @GraphQLName("inventoryAdjustmentTypeName") @GraphQLNonNull final String inventoryAdjustmentTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = InventoryUtil.getHome().getCreateInventoryAdjustmentTypeForm();

            commandForm.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = InventoryUtil.getHome().createInventoryAdjustmentType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateInventoryAdjustmentTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editInventoryAdjustmentType(final DataFetchingEnvironment env,
            @GraphQLName("originalInventoryAdjustmentTypeName") final String originalInventoryAdjustmentTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("inventoryAdjustmentTypeName") final String inventoryAdjustmentTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = InventoryUtil.getHome().getInventoryAdjustmentTypeUniversalSpec();

            spec.setInventoryAdjustmentTypeName(originalInventoryAdjustmentTypeName);
            spec.setUuid(id);

            var commandForm = InventoryUtil.getHome().getEditInventoryAdjustmentTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editInventoryAdjustmentType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditInventoryAdjustmentTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getInventoryAdjustmentType().getEntityInstance());

                if(arguments.containsKey("inventoryAdjustmentTypeName"))
                    edit.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = InventoryUtil.getHome().editInventoryAdjustmentType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteInventoryAdjustmentType(final DataFetchingEnvironment env,
            @GraphQLName("inventoryAdjustmentTypeName") final String inventoryAdjustmentTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getDeleteInventoryAdjustmentTypeForm();

            commandForm.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(InventoryUtil.getHome().deleteInventoryAdjustmentType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createInventoryTransactionType(final DataFetchingEnvironment env,
            @GraphQLName("inventoryTransactionTypeName") @GraphQLNonNull final String inventoryTransactionTypeName,
            @GraphQLName("inventoryTransactionSequenceTypeName") final String inventoryTransactionSequenceTypeName,
            @GraphQLName("inventoryTransactionWorkflowName") final String inventoryTransactionWorkflowName,
            @GraphQLName("inventoryTransactionWorkflowEntranceName") final String inventoryTransactionWorkflowEntranceName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = InventoryUtil.getHome().getCreateInventoryTransactionTypeForm();

            commandForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
            commandForm.setInventoryTransactionSequenceTypeName(inventoryTransactionSequenceTypeName);
            commandForm.setInventoryTransactionWorkflowName(inventoryTransactionWorkflowName);
            commandForm.setInventoryTransactionWorkflowEntranceName(inventoryTransactionWorkflowEntranceName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = InventoryUtil.getHome().createInventoryTransactionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateInventoryTransactionTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editInventoryTransactionType(final DataFetchingEnvironment env,
            @GraphQLName("originalInventoryTransactionTypeName") final String originalInventoryTransactionTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("inventoryTransactionTypeName") final String inventoryTransactionTypeName,
            @GraphQLName("inventoryTransactionSequenceTypeName") final String inventoryTransactionSequenceTypeName,
            @GraphQLName("inventoryTransactionWorkflowName") final String inventoryTransactionWorkflowName,
            @GraphQLName("inventoryTransactionWorkflowEntranceName") final String inventoryTransactionWorkflowEntranceName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = InventoryUtil.getHome().getInventoryTransactionTypeUniversalSpec();

            spec.setInventoryTransactionTypeName(originalInventoryTransactionTypeName);
            spec.setUuid(id);

            var commandForm = InventoryUtil.getHome().getEditInventoryTransactionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editInventoryTransactionType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditInventoryTransactionTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getInventoryTransactionType().getEntityInstance());

                if(arguments.containsKey("inventoryTransactionTypeName"))
                    edit.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                if(arguments.containsKey("inventoryTransactionSequenceTypeName"))
                    edit.setInventoryTransactionSequenceTypeName(inventoryTransactionSequenceTypeName);
                if(arguments.containsKey("inventoryTransactionWorkflowName"))
                    edit.setInventoryTransactionWorkflowName(inventoryTransactionWorkflowName);
                if(arguments.containsKey("inventoryTransactionWorkflowEntranceName"))
                    edit.setInventoryTransactionWorkflowEntranceName(inventoryTransactionWorkflowEntranceName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = InventoryUtil.getHome().editInventoryTransactionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteInventoryTransactionType(final DataFetchingEnvironment env,
            @GraphQLName("inventoryTransactionTypeName") final String inventoryTransactionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getDeleteInventoryTransactionTypeForm();

            commandForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(InventoryUtil.getHome().deleteInventoryTransactionType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ContentUtil.getHome().getCreateContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ContentUtil.getHome().createContentPageLayout(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateContentPageLayoutResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);

            var commandResult = ContentUtil.getHome().deleteContentPageLayout(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("originalContentPageLayoutName") final String originalContentPageLayoutName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("contentPageLayoutName") final String contentPageLayoutName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ContentUtil.getHome().getContentPageLayoutUniversalSpec();

            spec.setContentPageLayoutName(originalContentPageLayoutName);
            spec.setUuid(id);

            var commandForm = ContentUtil.getHome().getEditContentPageLayoutForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentPageLayout(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentPageLayoutResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getContentPageLayout().getEntityInstance());

                if(arguments.containsKey("contentPageLayoutName"))
                    edit.setContentPageLayoutName(contentPageLayoutName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ContentUtil.getHome().editContentPageLayout(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredLanguage")
    static MutationResultObject setUserVisitPreferredLanguage(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredLanguage(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredCurrency")
    static MutationResultObject setUserVisitPreferredCurrency(final DataFetchingEnvironment env,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredCurrency(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredTimeZone")
    static MutationResultObject setUserVisitPreferredTimeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") @GraphQLNonNull final String javaTimeZoneName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredTimeZone(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredDateTimeFormat")
    static MutationResultObject setUserVisitPreferredDateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") @GraphQLNonNull final String dateTimeFormatName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredDateTimeFormat(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createComponentVendor(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateComponentVendorForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createComponentVendor(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateComponentVendorResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteComponentVendor(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteComponentVendorForm();

            commandForm.setComponentVendorName(componentVendorName);

            var commandResult = CoreUtil.getHome().deleteComponentVendor(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editComponentVendor(final DataFetchingEnvironment env,
            @GraphQLName("originalComponentVendorName") @GraphQLNonNull final String originalComponentVendorName,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = CoreUtil.getHome().getComponentVendorSpec();

            spec.setComponentVendorName(originalComponentVendorName);

            var commandForm = CoreUtil.getHome().getEditComponentVendorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editComponentVendor(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditComponentVendorResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getComponentVendor().getEntityInstance());

                if(arguments.containsKey("componentVendorName"))
                    edit.setComponentVendorName(componentVendorName);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editComponentVendor(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createEntityType(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName,
            @GraphQLName("keepAllHistory") @GraphQLNonNull final String keepAllHistory,
            @GraphQLName("lockTimeout") final String lockTimeout,
            @GraphQLName("lockTimeoutUnitOfMeasureTypeName") final String lockTimeoutUnitOfMeasureTypeName,
            @GraphQLName("isExtensible") @GraphQLNonNull final String isExtensible,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setKeepAllHistory(keepAllHistory);
            commandForm.setLockTimeout(lockTimeout);
            commandForm.setLockTimeoutUnitOfMeasureTypeName(lockTimeoutUnitOfMeasureTypeName);
            commandForm.setIsExtensible(isExtensible);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createEntityType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityType(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = CoreUtil.getHome().deleteEntityType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editEntityType(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("originalEntityTypeName") @GraphQLNonNull final String originalEntityTypeName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("keepAllHistory") final String keepAllHistory,
            @GraphQLName("lockTimeout") final String lockTimeout,
            @GraphQLName("lockTimeoutUnitOfMeasureTypeName") final String lockTimeoutUnitOfMeasureTypeName,
            @GraphQLName("isExtensible") final String isExtensible,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = CoreUtil.getHome().getEntityTypeSpec();

            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(originalEntityTypeName);

            var commandForm = CoreUtil.getHome().getEditEntityTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getEntityType().getEntityInstance());

                if(arguments.containsKey("entityTypeName"))
                    edit.setEntityTypeName(entityTypeName);
                if(arguments.containsKey("keepAllHistory"))
                    edit.setKeepAllHistory(keepAllHistory);
                if(arguments.containsKey("lockTimeout"))
                    edit.setLockTimeout(lockTimeout);
                if(arguments.containsKey("lockTimeoutUnitOfMeasureTypeName"))
                    edit.setLockTimeoutUnitOfMeasureTypeName(lockTimeoutUnitOfMeasureTypeName);
                if(arguments.containsKey("isExtensible"))
                    edit.setIsExtensible(isExtensible);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createEntityInstance(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityInstanceForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = CoreUtil.getHome().createEntityInstance(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityInstanceResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityInstance(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityInstanceForm();

            commandForm.setUuid(id);

            var commandResult = CoreUtil.getHome().deleteEntityInstance(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject removeEntityInstance(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getRemoveEntityInstanceForm();

            commandForm.setUuid(id);

            var commandResult = CoreUtil.getHome().removeEntityInstance(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject sendEvent(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("eventTypeName") @GraphQLNonNull final String eventTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getSendEventForm();

            commandForm.setUuid(id);
            commandForm.setEventTypeName(eventTypeName);

            var commandResult = CoreUtil.getHome().sendEvent(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createTagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagScopeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = TagUtil.getHome().createTagScope(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateTagScopeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteTagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagScopeForm();

            commandForm.setTagScopeName(tagScopeName);

            var commandResult = TagUtil.getHome().deleteTagScope(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editTagScope(final DataFetchingEnvironment env,
            @GraphQLName("originalTagScopeName") @GraphQLNonNull final String originalTagScopeName,
            @GraphQLName("tagScopeName") final String tagScopeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = TagUtil.getHome().getTagScopeSpec();

            spec.setTagScopeName(originalTagScopeName);

            var commandForm = TagUtil.getHome().getEditTagScopeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = TagUtil.getHome().editTagScope(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditTagScopeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getTagScope().getEntityInstance());

                if(arguments.containsKey("tagScopeName"))
                    edit.setTagScopeName(tagScopeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = TagUtil.getHome().editTagScope(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createTagScopeEntityType(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = TagUtil.getHome().createTagScopeEntityType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteTagScopeEntityType(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = TagUtil.getHome().deleteTagScopeEntityType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().createTag(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateTagResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().deleteTag(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("originalTagName") @GraphQLNonNull final String originalTagName,
            @GraphQLName("tagName") final String tagName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = TagUtil.getHome().getTagSpec();

            spec.setTagScopeName(tagScopeName);
            spec.setTagName(originalTagName);

            var commandForm = TagUtil.getHome().getEditTagForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = TagUtil.getHome().editTag(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditTagResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getTag().getEntityInstance());

                if(arguments.containsKey("tagName"))
                    edit.setTagName(tagName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = TagUtil.getHome().editTag(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityTag(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getCreateEntityTagForm();

            commandForm.setUuid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().createEntityTag(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityTag(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteEntityTagForm();

            commandForm.setUuid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().deleteEntityTag(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createEntityAliasType(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAliasTypeName") final String entityAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAliasTypeForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAliasTypeName(entityAliasTypeName);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createEntityAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityAliasTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityAliasType(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("originalEntityAliasTypeName") final String originalEntityAliasTypeName,
            @GraphQLName("entityAliasTypeName") final String entityAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityAliasTypeUniversalSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAliasTypeName(originalEntityAliasTypeName);

            var commandForm = CoreUtil.getHome().getEditEntityAliasTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAliasTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityAliasTypeName"))
                    edit.setEntityAliasTypeName(entityAliasTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityAliasType(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAliasTypeName") final String entityAliasTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAliasTypeForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAliasTypeName(entityAliasTypeName);

            var commandResult = CoreUtil.getHome().deleteEntityAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityAlias(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAliasTypeId") @GraphQLNonNull @GraphQLID final String entityAliasTypeId,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAliasForm();

            commandForm.setUuid(id);
            commandForm.setEntityAliasTypeUuid(entityAliasTypeId);
            commandForm.setAlias(alias);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityAlias(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityAlias(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAliasTypeId") @GraphQLNonNull @GraphQLID final String entityAliasTypeId,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityAliasSpec();

            spec.setUuid(id);
            spec.setEntityAliasTypeUuid(entityAliasTypeId);

            var commandForm = CoreUtil.getHome().getEditEntityAliasForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAlias(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAliasResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("alias"))
                    edit.setAlias(alias);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAlias(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityAlias(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAliasTypeId") @GraphQLNonNull @GraphQLID final String entityAliasTypeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAliasForm();

            commandForm.setUuid(id);
            commandForm.setEntityAliasTypeUuid(entityAliasTypeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityAlias(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityAttributeGroupResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);

            var commandResult = CoreUtil.getHome().deleteEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("originalEntityAttributeGroupName") @GraphQLNonNull final String originalEntityAttributeGroupName,
            @GraphQLName("entityAttributeGroupName") final String entityAttributeGroupName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = CoreUtil.getHome().getEntityAttributeGroupSpec();

            spec.setEntityAttributeGroupName(originalEntityAttributeGroupName);

            var commandForm = CoreUtil.getHome().getEditEntityAttributeGroupForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAttributeGroupResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getEntityAttributeGroup().getEntityInstance());

                if(arguments.containsKey("entityAttributeGroupName"))
                    edit.setEntityAttributeGroupName(entityAttributeGroupName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeTypeName") @GraphQLNonNull final String entityAttributeTypeName,
            @GraphQLName("trackRevisions") @GraphQLNonNull final String trackRevisions,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description,
            @GraphQLName("checkContentWebAddress") final String checkContentWebAddress,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("upperRangeIntegerValue") final String upperRangeIntegerValue,
            @GraphQLName("upperLimitIntegerValue") final String upperLimitIntegerValue,
            @GraphQLName("lowerLimitIntegerValue") final String lowerLimitIntegerValue,
            @GraphQLName("lowerRangeIntegerValue") final String lowerRangeIntegerValue,
            @GraphQLName("upperRangeLongValue") final String upperRangeLongValue,
            @GraphQLName("upperLimitLongValue") final String upperLimitLongValue,
            @GraphQLName("lowerLimitLongValue") final String lowerLimitLongValue,
            @GraphQLName("lowerRangeLongValue") final String lowerRangeLongValue,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("entityListItemSequenceName") final String entityListItemSequenceName,
            @GraphQLName("workflowName") final String workflowName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAttributeForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeTypeName(entityAttributeTypeName);
            commandForm.setTrackRevisions(trackRevisions);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);
            commandForm.setCheckContentWebAddress(checkContentWebAddress);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setUpperRangeIntegerValue(upperRangeIntegerValue);
            commandForm.setUpperLimitIntegerValue(upperLimitIntegerValue);
            commandForm.setLowerLimitIntegerValue(lowerLimitIntegerValue);
            commandForm.setLowerRangeIntegerValue(lowerRangeIntegerValue);
            commandForm.setUpperRangeLongValue(upperRangeLongValue);
            commandForm.setUpperLimitLongValue(upperLimitLongValue);
            commandForm.setLowerLimitLongValue(lowerLimitLongValue);
            commandForm.setLowerRangeLongValue(lowerRangeLongValue);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setEntityListItemSequenceName(entityListItemSequenceName);
            commandForm.setWorkflowName(workflowName);

            var commandResult = CoreUtil.getHome().createEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityAttributeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("originalEntityAttributeName") final String originalEntityAttributeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("trackRevisions") final String trackRevisions,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityAttributeUniversalSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(originalEntityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityAttributeName"))
                    edit.setEntityAttributeName(entityAttributeName);
                if(arguments.containsKey("trackRevisions"))
                    edit.setTrackRevisions(trackRevisions);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAttributeForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            var commandResult = CoreUtil.getHome().deleteEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityAttributeEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName,
            @GraphQLName("entityAttributeName") @GraphQLNonNull final String entityAttributeName,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAttributeEntityAttributeGroupForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);
            commandForm.setSortOrder(sortOrder);

            var commandResult = CoreUtil.getHome().createEntityAttributeEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityAttributeEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName,
            @GraphQLName("entityAttributeName") @GraphQLNonNull final String entityAttributeName,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName,
            @GraphQLName("sortOrder") final String sortOrder) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityAttributeEntityAttributeGroupSpec();

            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setEntityAttributeGroupName(entityAttributeGroupName);

            var commandForm = CoreUtil.getHome().getEditEntityAttributeEntityAttributeGroupForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAttributeEntityAttributeGroupResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityAttributeEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName,
            @GraphQLName("entityAttributeName") @GraphQLNonNull final String entityAttributeName,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAttributeEntityAttributeGroupForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);

            var commandResult = CoreUtil.getHome().deleteEntityAttributeEntityAttributeGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createEntityListItem(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityListItemForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createEntityListItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityListItemResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityListItem(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("originalEntityListItemName") final String originalEntityListItemName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityListItemUniversalSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setEntityListItemName(originalEntityListItemName);

            var commandForm = CoreUtil.getHome().getEditEntityListItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityListItem(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityListItemResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityListItemName"))
                    edit.setEntityListItemName(entityListItemName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityListItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityListItem(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityListItemForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);

            var commandResult = CoreUtil.getHome().deleteEntityListItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityListItemDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityListItemDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUuid(entityListItemId);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            var commandResult = CoreUtil.getHome().createEntityListItemDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityListItemDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityListItemDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityListItemDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityListItemDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityListItemDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityListItemName"))
                    edit.setEntityListItemName(entityListItemName);
                if(arguments.containsKey("entityListItemId"))
                    edit.setEntityListItemUuid(entityListItemId);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityListItemDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityListItemDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityListItemDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            var commandResult = CoreUtil.getHome().deleteEntityListItemDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityListItemAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUuid(entityListItemId);

            var commandResult = CoreUtil.getHome().createEntityListItemAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityListItemAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityListItemAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityListItemAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityListItemAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityListItemName"))
                    edit.setEntityListItemName(entityListItemName);
                if(arguments.containsKey("entityListItemId"))
                    edit.setEntityListItemUuid(entityListItemId);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityListItemAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityListItemAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            var commandResult = CoreUtil.getHome().deleteEntityListItemAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityMultipleListItemDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityMultipleListItemDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUuid(entityListItemId);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            var commandResult = CoreUtil.getHome().createEntityMultipleListItemDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityMultipleListItemDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityMultipleListItemDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUuid(entityListItemId);

            var commandResult = CoreUtil.getHome().deleteEntityMultipleListItemDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityMultipleListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityMultipleListItemAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUuid(entityListItemId);

            var commandResult = CoreUtil.getHome().createEntityMultipleListItemAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityMultipleListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityMultipleListItemAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUuid(entityListItemId);

            var commandResult = CoreUtil.getHome().deleteEntityMultipleListItemAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityBooleanDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityBooleanDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setBooleanAttribute(booleanAttribute);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityBooleanDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityBooleanDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityBooleanDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityBooleanDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityBooleanDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityBooleanDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("booleanAttribute"))
                    edit.setBooleanAttribute(booleanAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityBooleanDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityBooleanDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityBooleanDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityBooleanDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityBooleanAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setBooleanAttribute(booleanAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityBooleanAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityBooleanAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityBooleanAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityBooleanAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityBooleanAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("booleanAttribute"))
                    edit.setBooleanAttribute(booleanAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityBooleanAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityBooleanAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityBooleanAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityIntegerDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityIntegerDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setIntegerAttribute(integerAttribute);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityIntegerDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityIntegerDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityIntegerDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityIntegerDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityIntegerDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityIntegerDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("integerAttribute"))
                    edit.setIntegerAttribute(integerAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityIntegerDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityIntegerDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityIntegerDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityIntegerDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityIntegerAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setIntegerAttribute(integerAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityIntegerAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityIntegerAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityIntegerAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityIntegerAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityIntegerAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("integerAttribute"))
                    edit.setIntegerAttribute(integerAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityIntegerAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityIntegerAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityIntegerAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityLongDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityLongDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setLongAttribute(longAttribute);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityLongDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityLongDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityLongDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityLongDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityLongDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityLongDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("longAttribute"))
                    edit.setLongAttribute(longAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityLongDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityLongDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityLongDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityLongDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityLongAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setLongAttribute(longAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityLongAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityLongAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityLongAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityLongAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityLongAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("longAttribute"))
                    edit.setLongAttribute(longAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityLongAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityLongAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityLongAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityStringDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("languageId") @GraphQLID final String languageId,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityStringDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setStringAttribute(stringAttribute);
            commandForm.setLanguageUuid(languageId);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityStringDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityStringDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("languageId") @GraphQLID final String languageId,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityStringDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setLanguageUuid(languageId);
            spec.setLanguageIsoName(languageIsoName);

            var commandForm = CoreUtil.getHome().getEditEntityStringDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityStringDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityStringDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("stringAttribute"))
                    edit.setStringAttribute(stringAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityStringDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityStringDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("languageId") @GraphQLID final String languageId,
            @GraphQLName("languageIsoName") final String languageIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityStringDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setLanguageUuid(languageId);
            commandForm.setLanguageIsoName(languageIsoName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityStringDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityStringAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setLanguageUuid(languageId);
            commandForm.setStringAttribute(stringAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityStringAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityStringAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);
            spec.setLanguageUuid(languageId);

            var commandForm = CoreUtil.getHome().getEditEntityStringAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityStringAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityStringAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("stringAttribute"))
                    edit.setStringAttribute(stringAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityStringAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull final String languageId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityStringAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setLanguageUuid(languageId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityStringAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("clobAttribute") @GraphQLNonNull final String clobAttribute,
            @GraphQLName("mimeTypeName") @GraphQLNonNull final String mimeTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityClobAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setLanguageUuid(languageId);
            commandForm.setClobAttribute(clobAttribute);
            commandForm.setMimeTypeName(mimeTypeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityClobAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("clobAttribute") final String clobAttribute,
            @GraphQLName("mimeTypeName") final String mimeTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityClobAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);
            spec.setLanguageUuid(languageId);

            var commandForm = CoreUtil.getHome().getEditEntityClobAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityClobAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityClobAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("clobAttribute"))
                    edit.setClobAttribute(clobAttribute);
                if(arguments.containsKey("mimeTypeName"))
                    edit.setMimeTypeName(mimeTypeName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityClobAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull final String languageId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityClobAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setLanguageUuid(languageId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityClobAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("nameAttribute") @GraphQLNonNull final String nameAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityNameAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setNameAttribute(nameAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityNameAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("nameAttribute") @GraphQLNonNull final String nameAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityNameAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityNameAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityNameAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityNameAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("nameAttribute"))
                    edit.setNameAttribute(nameAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityNameAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityNameAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityNameAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityDateDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityDateDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setDateAttribute(dateAttribute);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityDateDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityDateDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityDateDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityDateDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityDateDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityDateDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("dateAttribute"))
                    edit.setDateAttribute(dateAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityDateDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityDateDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityDateDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityDateDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityDateAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setDateAttribute(dateAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityDateAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityDateAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityDateAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityDateAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityDateAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("dateAttribute"))
                    edit.setDateAttribute(dateAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityDateAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityDateAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityDateAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityTimeDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityTimeDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setTimeAttribute(timeAttribute);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityTimeDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityTimeDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityTimeDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityTimeDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityTimeDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityTimeDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("timeAttribute"))
                    edit.setTimeAttribute(timeAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityTimeDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityTimeDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityTimeDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityTimeDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityTimeAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setTimeAttribute(timeAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityTimeAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityTimeAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityTimeAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityTimeAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityTimeAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("timeAttribute"))
                    edit.setTimeAttribute(timeAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityTimeAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityTimeAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityTimeAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityGeoPointDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("latitude") @GraphQLNonNull final String latitude,
            @GraphQLName("longitude") @GraphQLNonNull final String longitude,
            @GraphQLName("elevation") final String elevation,
            @GraphQLName("elevationUnitOfMeasureTypeName") final String elevationUnitOfMeasureTypeName,
            @GraphQLName("altitude") final String altitude,
            @GraphQLName("altitudeUnitOfMeasureTypeName") final String altitudeUnitOfMeasureTypeName,
            @GraphQLName("addMissingAttributes") @GraphQLNonNull final String addMissingAttributes) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityGeoPointDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setLatitude(latitude);
            commandForm.setLongitude(longitude);
            commandForm.setElevation(elevation);
            commandForm.setElevationUnitOfMeasureTypeName(elevationUnitOfMeasureTypeName);
            commandForm.setAltitude(altitude);
            commandForm.setAltitudeUnitOfMeasureTypeName(altitudeUnitOfMeasureTypeName);
            commandForm.setAddMissingAttributes(addMissingAttributes);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityGeoPointDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityGeoPointDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("latitude") final String latitude,
            @GraphQLName("longitude") final String longitude,
            @GraphQLName("elevation") final String elevation,
            @GraphQLName("elevationUnitOfMeasureTypeName") final String elevationUnitOfMeasureTypeName,
            @GraphQLName("altitude") final String altitude,
            @GraphQLName("altitudeUnitOfMeasureTypeName") final String altitudeUnitOfMeasureTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityGeoPointDefaultSpec();

            spec.setUuid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityGeoPointDefaultForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityGeoPointDefault(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityGeoPointDefaultResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("latitude"))
                    edit.setLatitude(latitude);
                if(arguments.containsKey("longitude"))
                    edit.setLongitude(longitude);
                if(arguments.containsKey("elevation"))
                    edit.setElevation(elevation);
                if(arguments.containsKey("elevationUnitOfMeasureTypeName"))
                    edit.setElevationUnitOfMeasureTypeName(elevationUnitOfMeasureTypeName);
                if(arguments.containsKey("altitude"))
                    edit.setAltitude(altitude);
                if(arguments.containsKey("altitudeUnitOfMeasureTypeName"))
                    edit.setAltitudeUnitOfMeasureTypeName(altitudeUnitOfMeasureTypeName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityGeoPointDefault(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityGeoPointDefault(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityGeoPointDefaultForm();

            commandForm.setUuid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityGeoPointDefault(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityGeoPointAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("latitude") @GraphQLNonNull final String latitude,
            @GraphQLName("longitude") @GraphQLNonNull final String longitude,
            @GraphQLName("elevation") final String elevation,
            @GraphQLName("elevationUnitOfMeasureTypeName") final String elevationUnitOfMeasureTypeName,
            @GraphQLName("altitude") final String altitude,
            @GraphQLName("altitudeUnitOfMeasureTypeName") final String altitudeUnitOfMeasureTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityGeoPointAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setLatitude(latitude);
            commandForm.setLongitude(longitude);
            commandForm.setElevation(elevation);
            commandForm.setElevationUnitOfMeasureTypeName(elevationUnitOfMeasureTypeName);
            commandForm.setAltitude(altitude);
            commandForm.setAltitudeUnitOfMeasureTypeName(altitudeUnitOfMeasureTypeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityGeoPointAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityGeoPointAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("latitude") final String latitude,
            @GraphQLName("longitude") final String longitude,
            @GraphQLName("elevation") final String elevation,
            @GraphQLName("elevationUnitOfMeasureTypeName") final String elevationUnitOfMeasureTypeName,
            @GraphQLName("altitude") final String altitude,
            @GraphQLName("altitudeUnitOfMeasureTypeName") final String altitudeUnitOfMeasureTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityGeoPointAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityGeoPointAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityGeoPointAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityGeoPointAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("latitude"))
                    edit.setLatitude(latitude);
                if(arguments.containsKey("longitude"))
                    edit.setLongitude(longitude);
                if(arguments.containsKey("elevation"))
                    edit.setElevation(elevation);
                if(arguments.containsKey("elevationUnitOfMeasureTypeName"))
                    edit.setElevationUnitOfMeasureTypeName(elevationUnitOfMeasureTypeName);
                if(arguments.containsKey("altitude"))
                    edit.setAltitude(altitude);
                if(arguments.containsKey("altitudeUnitOfMeasureTypeName"))
                    edit.setAltitudeUnitOfMeasureTypeName(altitudeUnitOfMeasureTypeName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityGeoPointAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityGeoPointAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityGeoPointAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityGeoPointAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("idAttribute") @GraphQLNonNull final String idAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityEntityAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setUuidAttribute(idAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("idAttribute") @GraphQLNonNull @GraphQLID final String idAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityEntityAttributeSpec();

            spec.setUuid(id);
            spec.setEntityAttributeUuid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityEntityAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityEntityAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("idAttribute")) {
                    edit.setEntityRefAttribute(null);
                    edit.setUuidAttribute(idAttribute);
                }

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityEntityAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityEntityAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityCollectionAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("idAttribute") @GraphQLNonNull @GraphQLID final String idAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityCollectionAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setUuidAttribute(idAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityCollectionAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityCollectionAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("idAttribute") @GraphQLNonNull @GraphQLID final String idAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityCollectionAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setUuidAttribute(idAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityCollectionAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createEntityWorkflowAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("workflowEntranceName") final String workflowEntranceName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityWorkflowAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setWorkflowEntranceName(workflowEntranceName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityWorkflowAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }


    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setEntityWorkflowAttributeStatus")
    static MutationResultObject setEntityWorkflowAttributeStatus(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") final String workflowDestinationName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getSetEntityWorkflowAttributeStatusForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().setEntityWorkflowAttributeStatus(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteEntityWorkflowAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityWorkflowAttributeForm();

            commandForm.setUuid(id);
            commandForm.setEntityAttributeUuid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityWorkflowAttribute(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createSearchResultActionType(final DataFetchingEnvironment env,
            @GraphQLName("searchResultActionTypeName") @GraphQLNonNull final String searchResultActionTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = SearchUtil.getHome().getCreateSearchResultActionTypeForm();

            commandForm.setSearchResultActionTypeName(searchResultActionTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = SearchUtil.getHome().createSearchResultActionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSearchResultActionTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editSearchResultActionType(final DataFetchingEnvironment env,
            @GraphQLName("originalSearchResultActionTypeName") final String originalSearchResultActionTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("searchResultActionTypeName") final String searchResultActionTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = SearchUtil.getHome().getSearchResultActionTypeUniversalSpec();

            spec.setSearchResultActionTypeName(originalSearchResultActionTypeName);
            spec.setUuid(id);

            var commandForm = SearchUtil.getHome().getEditSearchResultActionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SearchUtil.getHome().editSearchResultActionType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSearchResultActionTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getSearchResultActionType().getEntityInstance());

                if(arguments.containsKey("searchResultActionTypeName"))
                    edit.setSearchResultActionTypeName(searchResultActionTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SearchUtil.getHome().editSearchResultActionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteSearchResultActionType(final DataFetchingEnvironment env,
            @GraphQLName("searchResultActionTypeName") final String searchResultActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getDeleteSearchResultActionTypeForm();

            commandForm.setSearchResultActionTypeName(searchResultActionTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(SearchUtil.getHome().deleteSearchResultActionType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchComponentVendorsResultObject searchComponentVendors(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchComponentVendorsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchComponentVendorsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchComponentVendors(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchComponentVendorsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearComponentVendorResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearComponentVendorResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearComponentVendorResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchEntityTypesResultObject searchEntityTypes(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchEntityTypesResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchEntityTypesForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchEntityTypes(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchEntityTypesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearEntityTypeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEntityTypeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEntityTypeResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchEntityAliasTypesResultObject searchEntityAliasTypes(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchEntityAliasTypesResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchEntityAliasTypesForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchEntityAliasTypes(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchEntityAliasTypesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearEntityAliasTypeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEntityAliasTypeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEntityAliasTypeResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchEntityAttributeGroupsResultObject searchEntityAttributeGroups(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchEntityAttributeGroupsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchEntityAttributeGroupsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchEntityAttributeGroups(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchEntityAttributeGroupsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearEntityAttributeGroupResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEntityAttributeGroupResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEntityAttributeGroupResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchEntityAttributesResultObject searchEntityAttributes(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchEntityAttributesResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchEntityAttributesForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchEntityAttributes(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchEntityAttributesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearEntityAttributeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEntityAttributeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEntityAttributeResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchEntityListItemsResultObject searchEntityListItems(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchEntityListItemsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchEntityListItemsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchEntityListItems(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchEntityListItemsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearEntityListItemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEntityListItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEntityListItemResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchCustomersResultObject searchCustomers(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("customerTypeName") final String customerTypeName,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("firstNameSoundex") final String firstNameSoundex,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("middleNameSoundex") final String middleNameSoundex,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("lastNameSoundex") final String lastNameSoundex,
            @GraphQLName("name") final String name,
            @GraphQLName("emailAddress") final String emailAddress,
            @GraphQLName("countryName") final String countryName,
            @GraphQLName("areaCode") final String areaCode,
            @GraphQLName("telephoneNumber") final String telephoneNumber,
            @GraphQLName("telephoneExtension") final String telephoneExtension,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince) {
        var mutationResultObject = new SearchCustomersResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchCustomersForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setName(name);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setCountryName(countryName);
            commandForm.setAreaCode(areaCode);
            commandForm.setTelephoneNumber(telephoneNumber);
            commandForm.setTelephoneExtension(telephoneExtension);
            commandForm.setCustomerName(customerName);
            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);

            var commandResult = SearchUtil.getHome().searchCustomers(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchCustomersResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearCustomerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearCustomerResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchEmployeesResultObject searchEmployees(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("firstNameSoundex") final String firstNameSoundex,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("middleNameSoundex") final String middleNameSoundex,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("lastNameSoundex") final String lastNameSoundex,
            @GraphQLName("employeeName") final String employeeName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("employeeStatusChoice") final String employeeStatusChoice,
            @GraphQLName("employeeAvailabilityChoice") final String employeeAvailabilityChoice,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields) {
        var mutationResultObject = new SearchEmployeesResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchEmployeesForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setEmployeeName(employeeName);
            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setEmployeeStatusChoice(employeeStatusChoice);
            commandForm.setEmployeeAvailabilityChoice(employeeAvailabilityChoice);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);


            var commandResult = SearchUtil.getHome().searchEmployees(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchEmployeesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearEmployeeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEmployeeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEmployeeResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchItemsResultObject searchItems(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("itemNameOrAlias") final String itemNameOrAlias,
            @GraphQLName("description") final String description,
            @GraphQLName("itemTypeName") final String itemTypeName,
            @GraphQLName("itemUseTypeName") final String itemUseTypeName,
            @GraphQLName("itemStatusChoice") final String itemStatusChoice,
            @GraphQLName("itemStatusChoices") final String itemStatusChoices,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchItemsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchItemsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setItemNameOrAlias(itemNameOrAlias);
            commandForm.setDescription(description);
            commandForm.setItemTypeName(itemTypeName);
            commandForm.setItemUseTypeName(itemUseTypeName);
            commandForm.setItemStatusChoice(itemStatusChoice);
            commandForm.setItemStatusChoices(itemStatusChoices);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchItems(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchItemsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createItemSearchResultAction(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchResultActionTypeName") @GraphQLNonNull final String searchResultActionTypeName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getCreateItemSearchResultActionForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchResultActionTypeName(searchResultActionTypeName);
            commandForm.setItemName(itemName);

            var commandResult = SearchUtil.getHome().createItemSearchResultAction(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearItemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearItemResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchVendorsResultObject searchVendors(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("firstNameSoundex") final String firstNameSoundex,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("middleNameSoundex") final String middleNameSoundex,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("lastNameSoundex") final String lastNameSoundex,
            @GraphQLName("name") final String name,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields) {
        var mutationResultObject = new SearchVendorsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchVendorsForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setName(name);
            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);

            var commandResult = SearchUtil.getHome().searchVendors(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchVendorsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearVendorResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearVendorResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearVendorResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchShippingMethodsResultObject searchShippingMethods(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchShippingMethodsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchShippingMethodsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchShippingMethods(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchShippingMethodsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearShippingMethodResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearShippingMethodResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearShippingMethodResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchWarehousesResultObject searchWarehouses(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("q") final String q,
            @GraphQLName("warehouseName") final String warehouseName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields) {
        var mutationResultObject = new SearchWarehousesResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchWarehousesForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setQ(q);
            commandForm.setWarehouseName(warehouseName);
            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);

            var commandResult = SearchUtil.getHome().searchWarehouses(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchWarehousesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearWarehouseResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearWarehouseResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearWarehouseResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchContentCatalogsResultObject searchContentCatalogs(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchContentCatalogsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchContentCatalogsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchContentCatalogs(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchContentCatalogsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearContentCatalogResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearContentCatalogResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearContentCatalogResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchContentCatalogItemsResultObject searchContentCatalogItems(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchContentCatalogItemsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchContentCatalogItemsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchContentCatalogItems(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchContentCatalogItemsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearContentCatalogItemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearContentCatalogItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearContentCatalogItemResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static SearchContentCategoriesResultObject searchContentCategories(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("q") final String q,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var mutationResultObject = new SearchContentCategoriesResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchContentCategoriesForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchContentCategories(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchContentCategoriesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject clearContentCategoryResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearContentCategoryResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearContentCategoryResults(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") final String username,
            @GraphQLName("password1") @GraphQLNonNull final String password1,
            @GraphQLName("password2") @GraphQLNonNull final String password2,
            @GraphQLName("recoveryQuestionName") final String recoveryQuestionName,
            @GraphQLName("answer") final String answer) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getCreateUserLoginForm();

            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);
            commandForm.setUsername(username);
            commandForm.setPassword1(password1);
            commandForm.setPassword2(password2);
            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setAnswer(answer);

            var commandResult = UserUtil.getHome().createUserLogin(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") @GraphQLNonNull final String username) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = PartyUtil.getHome().getPartyUniversalSpec();

            spec.setPartyName(partyName);
            spec.setUuid(id);

            var commandForm = UserUtil.getHome().getEditUserLoginForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = UserUtil.getHome().editUserLogin(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUserLoginResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("username"))
                    edit.setUsername(username);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = UserUtil.getHome().editUserLogin(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getDeleteUserLoginForm();

            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(UserUtil.getHome().deleteUserLogin(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject resetLockout(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getResetLockoutForm();

            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(UserUtil.getHome().resetLockout(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") @GraphQLNonNull final String itemCategoryName,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setParentItemCategoryName(parentItemCategoryName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemCategoryResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("originalItemCategoryName") final String originalItemCategoryName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemCategoryUniversalSpec();

            spec.setItemCategoryName(originalItemCategoryName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemCategory(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemCategoryResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemCategory().getEntityInstance());

                if(arguments.containsKey("itemCategoryName"))
                    edit.setItemCategoryName(itemCategoryName);
                if(arguments.containsKey("parentItemCategoryName"))
                    edit.setParentItemCategoryName(parentItemCategoryName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemCategory(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createGlAccount(final DataFetchingEnvironment env,
            @GraphQLName("glAccountName") @GraphQLNonNull final String glAccountName,
            @GraphQLName("parentGlAccountName") final String parentGlAccountName,
            @GraphQLName("glAccountTypeName") @GraphQLNonNull final String glAccountTypeName,
            @GraphQLName("glAccountClassName") @GraphQLNonNull final String glAccountClassName,
            @GraphQLName("glAccountCategoryName") final String glAccountCategoryName,
            @GraphQLName("glResourceTypeName") @GraphQLNonNull final String glResourceTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = AccountingUtil.getHome().getCreateGlAccountForm();

            commandForm.setGlAccountName(glAccountName);
            commandForm.setParentGlAccountName(parentGlAccountName);
            commandForm.setGlAccountTypeName(glAccountTypeName);
            commandForm.setGlAccountClassName(glAccountClassName);
            commandForm.setGlAccountCategoryName(glAccountCategoryName);
            commandForm.setGlResourceTypeName(glResourceTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setIsDefault(isDefault);
            commandForm.setDescription(description);

            var commandResult = AccountingUtil.getHome().createGlAccount(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateGlAccountResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editGlAccount(final DataFetchingEnvironment env,
            @GraphQLName("originalGlAccountName") final String originalGlAccountName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("glAccountName") final String glAccountName,
            @GraphQLName("parentGlAccountName") final String parentGlAccountName,
            @GraphQLName("glAccountTypeName") final String glAccountTypeName,
            @GraphQLName("glAccountClassName") final String glAccountClassName,
            @GraphQLName("glAccountCategoryName") final String glAccountCategoryName,
            @GraphQLName("glResourceTypeName") final String glResourceTypeName,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = AccountingUtil.getHome().getGlAccountUniversalSpec();

            spec.setGlAccountName(originalGlAccountName);
            spec.setUuid(id);

            var commandForm = AccountingUtil.getHome().getEditGlAccountForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = AccountingUtil.getHome().editGlAccount(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditGlAccountResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getGlAccount().getEntityInstance());

                if(arguments.containsKey("glAccountName"))
                    edit.setGlAccountName(glAccountName);
                if(arguments.containsKey("parentGlAccountName"))
                    edit.setParentGlAccountName(parentGlAccountName);
                if(arguments.containsKey("glAccountClassName"))
                    edit.setGlAccountClassName(glAccountClassName);
                if(arguments.containsKey("glAccountCategoryName"))
                    edit.setGlAccountCategoryName(glAccountCategoryName);
                if(arguments.containsKey("glResourceTypeName"))
                    edit.setGlResourceTypeName(glResourceTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = AccountingUtil.getHome().editGlAccount(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteGlAccount(final DataFetchingEnvironment env,
            @GraphQLName("glAccountName") final String glAccountName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AccountingUtil.getHome().getDeleteGlAccountForm();

            commandForm.setGlAccountName(glAccountName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(AccountingUtil.getHome().deleteGlAccount(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemAccountingCategoryName") @GraphQLNonNull final String itemAccountingCategoryName,
            @GraphQLName("parentItemAccountingCategoryName") final String parentItemAccountingCategoryName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = AccountingUtil.getHome().getCreateItemAccountingCategoryForm();

            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setParentItemAccountingCategoryName(parentItemAccountingCategoryName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = AccountingUtil.getHome().createItemAccountingCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemAccountingCategoryResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("originalItemAccountingCategoryName") final String originalItemAccountingCategoryName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("parentItemAccountingCategoryName") final String parentItemAccountingCategoryName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = AccountingUtil.getHome().getItemAccountingCategoryUniversalSpec();

            spec.setItemAccountingCategoryName(originalItemAccountingCategoryName);
            spec.setUuid(id);

            var commandForm = AccountingUtil.getHome().getEditItemAccountingCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = AccountingUtil.getHome().editItemAccountingCategory(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemAccountingCategoryResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemAccountingCategory().getEntityInstance());

                if(arguments.containsKey("itemAccountingCategoryName"))
                    edit.setItemAccountingCategoryName(itemAccountingCategoryName);
                if(arguments.containsKey("parentItemAccountingCategoryName"))
                    edit.setParentItemAccountingCategoryName(parentItemAccountingCategoryName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = AccountingUtil.getHome().editItemAccountingCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AccountingUtil.getHome().getDeleteItemAccountingCategoryForm();

            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(AccountingUtil.getHome().deleteItemAccountingCategory(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemPurchasingCategoryName") @GraphQLNonNull final String itemPurchasingCategoryName,
            @GraphQLName("parentItemPurchasingCategoryName") final String parentItemPurchasingCategoryName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = VendorUtil.getHome().getCreateItemPurchasingCategoryForm();

            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setParentItemPurchasingCategoryName(parentItemPurchasingCategoryName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = VendorUtil.getHome().createItemPurchasingCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemPurchasingCategoryResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("originalItemPurchasingCategoryName") final String originalItemPurchasingCategoryName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("parentItemPurchasingCategoryName") final String parentItemPurchasingCategoryName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = VendorUtil.getHome().getItemPurchasingCategoryUniversalSpec();

            spec.setItemPurchasingCategoryName(originalItemPurchasingCategoryName);
            spec.setUuid(id);

            var commandForm = VendorUtil.getHome().getEditItemPurchasingCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editItemPurchasingCategory(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemPurchasingCategoryResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemPurchasingCategory().getEntityInstance());

                if(arguments.containsKey("itemPurchasingCategoryName"))
                    edit.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                if(arguments.containsKey("parentItemPurchasingCategoryName"))
                    edit.setParentItemPurchasingCategoryName(parentItemPurchasingCategoryName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = VendorUtil.getHome().editItemPurchasingCategory(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getDeleteItemPurchasingCategoryForm();

            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteItemPurchasingCategory(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }


    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createPartyAliasType(final DataFetchingEnvironment env,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName,
            @GraphQLName("partyAliasTypeName") @GraphQLNonNull final String partyAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PartyUtil.getHome().getCreatePartyAliasTypeForm();

            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PartyUtil.getHome().createPartyAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePartyAliasTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editPartyAliasType(final DataFetchingEnvironment env,
            @GraphQLName("partyTypeName") final String partyTypeName,
            @GraphQLName("originalPartyAliasTypeName") final String originalPartyAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = PartyUtil.getHome().getPartyAliasTypeUniversalSpec();

            spec.setPartyTypeName(partyTypeName);
            spec.setPartyAliasTypeName(originalPartyAliasTypeName);
            spec.setUuid(id);

            var commandForm = PartyUtil.getHome().getEditPartyAliasTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PartyUtil.getHome().editPartyAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPartyAliasTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getPartyAliasType().getEntityInstance());

                if(arguments.containsKey("partyAliasTypeName"))
                    edit.setPartyAliasTypeName(partyAliasTypeName);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PartyUtil.getHome().editPartyAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deletePartyAliasType(final DataFetchingEnvironment env,
            @GraphQLName("partyTypeName") final String partyTypeName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PartyUtil.getHome().getDeletePartyAliasTypeForm();

            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(PartyUtil.getHome().deletePartyAliasType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createPartyAlias(final DataFetchingEnvironment env,
            @GraphQLName("partyName") @GraphQLNonNull final String partyName,
            @GraphQLName("partyAliasTypeName") @GraphQLNonNull final String partyAliasTypeName,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PartyUtil.getHome().getCreatePartyAliasForm();

            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);

            mutationResultObject.setCommandResult(PartyUtil.getHome().createPartyAlias(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editPartyAlias(final DataFetchingEnvironment env,
            @GraphQLName("partyName") @GraphQLNonNull final String partyName,
            @GraphQLName("partyAliasTypeName") @GraphQLNonNull final String partyAliasTypeName,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = PartyUtil.getHome().getPartyAliasSpec();

            spec.setPartyName(partyName);
            spec.setPartyAliasTypeName(partyAliasTypeName);

            var commandForm = PartyUtil.getHome().getEditPartyAliasForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PartyUtil.getHome().editPartyAlias(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPartyAliasResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("alias"))
                    edit.setAlias(alias);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PartyUtil.getHome().editPartyAlias(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deletePartyAlias(final DataFetchingEnvironment env,
            @GraphQLName("partyName") @GraphQLNonNull final String partyName,
            @GraphQLName("partyAliasTypeName") @GraphQLNonNull final String partyAliasTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PartyUtil.getHome().getDeletePartyAliasForm();

            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);

            mutationResultObject.setCommandResult(PartyUtil.getHome().deletePartyAlias(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createVendorType(final DataFetchingEnvironment env,
            @GraphQLName("vendorTypeName") @GraphQLNonNull final String vendorTypeName,
            @GraphQLName("defaultTermName") final String defaultTermName,
            @GraphQLName("defaultFreeOnBoardName") final String defaultFreeOnBoardName,
            @GraphQLName("defaultCancellationPolicyName") final String defaultCancellationPolicyName,
            @GraphQLName("defaultReturnPolicyName") final String defaultReturnPolicyName,
            @GraphQLName("defaultApGlAccountName") final String defaultApGlAccountName,
            @GraphQLName("defaultHoldUntilComplete") @GraphQLNonNull final String defaultHoldUntilComplete,
            @GraphQLName("defaultAllowBackorders") @GraphQLNonNull final String defaultAllowBackorders,
            @GraphQLName("defaultAllowSubstitutions") @GraphQLNonNull final String defaultAllowSubstitutions,
            @GraphQLName("defaultAllowCombiningShipments") @GraphQLNonNull final String defaultAllowCombiningShipments,
            @GraphQLName("defaultRequireReference") @GraphQLNonNull final String defaultRequireReference,
            @GraphQLName("defaultAllowReferenceDuplicates") @GraphQLNonNull final String defaultAllowReferenceDuplicates,
            @GraphQLName("defaultReferenceValidationPattern") final String defaultReferenceValidationPattern,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = VendorUtil.getHome().getCreateVendorTypeForm();

            commandForm.setVendorTypeName(vendorTypeName);
            commandForm.setDefaultTermName(defaultTermName);
            commandForm.setDefaultFreeOnBoardName(defaultFreeOnBoardName);
            commandForm.setDefaultCancellationPolicyName(defaultCancellationPolicyName);
            commandForm.setDefaultReturnPolicyName(defaultReturnPolicyName);
            commandForm.setDefaultApGlAccountName(defaultApGlAccountName);
            commandForm.setDefaultHoldUntilComplete(defaultHoldUntilComplete);
            commandForm.setDefaultAllowBackorders(defaultAllowBackorders);
            commandForm.setDefaultAllowSubstitutions(defaultAllowSubstitutions);
            commandForm.setDefaultAllowCombiningShipments(defaultAllowCombiningShipments);
            commandForm.setDefaultRequireReference(defaultRequireReference);
            commandForm.setDefaultAllowReferenceDuplicates(defaultAllowReferenceDuplicates);
            commandForm.setDefaultReferenceValidationPattern(defaultReferenceValidationPattern);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = VendorUtil.getHome().createVendorType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateVendorTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editVendorType(final DataFetchingEnvironment env,
            @GraphQLName("originalVendorTypeName") final String originalVendorTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("vendorTypeName") final String vendorTypeName,
            @GraphQLName("defaultTermName") final String defaultTermName,
            @GraphQLName("defaultFreeOnBoardName") final String defaultFreeOnBoardName,
            @GraphQLName("defaultCancellationPolicyName") final String defaultCancellationPolicyName,
            @GraphQLName("defaultReturnPolicyName") final String defaultReturnPolicyName,
            @GraphQLName("defaultApGlAccountName") final String defaultApGlAccountName,
            @GraphQLName("defaultHoldUntilComplete") final String defaultHoldUntilComplete,
            @GraphQLName("defaultAllowBackorders") final String defaultAllowBackorders,
            @GraphQLName("defaultAllowSubstitutions") final String defaultAllowSubstitutions,
            @GraphQLName("defaultAllowCombiningShipments") final String defaultAllowCombiningShipments,
            @GraphQLName("defaultRequireReference") final String defaultRequireReference,
            @GraphQLName("defaultAllowReferenceDuplicates") final String defaultAllowReferenceDuplicates,
            @GraphQLName("defaultReferenceValidationPattern") final String defaultReferenceValidationPattern,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = VendorUtil.getHome().getVendorTypeUniversalSpec();

            spec.setVendorTypeName(originalVendorTypeName);
            spec.setUuid(id);

            var commandForm = VendorUtil.getHome().getEditVendorTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editVendorType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditVendorTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getVendorType().getEntityInstance());

                if(arguments.containsKey("vendorTypeName"))
                    edit.setVendorTypeName(vendorTypeName);
                if(arguments.containsKey("defaultTermName"))
                    edit.setDefaultTermName(defaultTermName);
                if(arguments.containsKey("defaultFreeOnBoardName"))
                    edit.setDefaultFreeOnBoardName(defaultFreeOnBoardName);
                if(arguments.containsKey("defaultCancellationPolicyName"))
                    edit.setDefaultCancellationPolicyName(defaultCancellationPolicyName);
                if(arguments.containsKey("defaultReturnPolicyName"))
                    edit.setDefaultReturnPolicyName(defaultReturnPolicyName);
                if(arguments.containsKey("defaultApGlAccountName"))
                    edit.setDefaultApGlAccountName(defaultApGlAccountName);
                if(arguments.containsKey("defaultHoldUntilComplete"))
                    edit.setDefaultHoldUntilComplete(defaultHoldUntilComplete);
                if(arguments.containsKey("defaultAllowBackorders"))
                    edit.setDefaultAllowBackorders(defaultAllowBackorders);
                if(arguments.containsKey("defaultAllowSubstitutions"))
                    edit.setDefaultAllowSubstitutions(defaultAllowSubstitutions);
                if(arguments.containsKey("defaultAllowCombiningShipments"))
                    edit.setDefaultAllowCombiningShipments(defaultAllowCombiningShipments);
                if(arguments.containsKey("defaultRequireReference"))
                    edit.setDefaultRequireReference(defaultRequireReference);
                if(arguments.containsKey("defaultAllowReferenceDuplicates"))
                    edit.setDefaultAllowReferenceDuplicates(defaultAllowReferenceDuplicates);
                if(arguments.containsKey("defaultReferenceValidationPattern"))
                    edit.setDefaultReferenceValidationPattern(defaultReferenceValidationPattern);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = VendorUtil.getHome().editVendorType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteVendorType(final DataFetchingEnvironment env,
            @GraphQLName("vendorTypeName") final String vendorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getDeleteVendorTypeForm();

            commandForm.setVendorTypeName(vendorTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteVendorType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createVendor(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("vendorTypeName") final String vendorTypeName,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName,
            @GraphQLName("apGlAccountName") final String apGlAccountName,
            @GraphQLName("minimumPurchaseOrderLines") final String minimumPurchaseOrderLines,
            @GraphQLName("maximumPurchaseOrderLines") final String maximumPurchaseOrderLines,
            @GraphQLName("minimumPurchaseOrderAmount") final String minimumPurchaseOrderAmount,
            @GraphQLName("maximumPurchaseOrderAmount") final String maximumPurchaseOrderAmount,
            @GraphQLName("useItemPurchasingCategories") @GraphQLNonNull final String useItemPurchasingCategories,
            @GraphQLName("defaultItemAliasTypeName") final String defaultItemAliasTypeName,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("preferredLanguageIsoName") final String preferredLanguageIsoName,
            @GraphQLName("preferredCurrencyIsoName") final String preferredCurrencyIsoName,
            @GraphQLName("preferredJavaTimeZoneName") final String preferredJavaTimeZoneName,
            @GraphQLName("preferredDateTimeFormatName") final String preferredDateTimeFormatName,
            @GraphQLName("emailAddress") final String emailAddress,
            @GraphQLName("allowSolicitation") @GraphQLNonNull final String allowSolicitation) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PartyUtil.getHome().getCreateVendorForm();

            commandForm.setVendorName(vendorName);
            commandForm.setVendorTypeName(vendorTypeName);
            commandForm.setCancellationPolicyName(cancellationPolicyName);
            commandForm.setReturnPolicyName(returnPolicyName);
            commandForm.setApGlAccountName(apGlAccountName);
            commandForm.setMinimumPurchaseOrderLines(minimumPurchaseOrderLines);
            commandForm.setMaximumPurchaseOrderLines(maximumPurchaseOrderLines);
            commandForm.setMinimumPurchaseOrderAmount(minimumPurchaseOrderAmount);
            commandForm.setMaximumPurchaseOrderLines(maximumPurchaseOrderAmount);
            commandForm.setUseItemPurchasingCategories(useItemPurchasingCategories);
            commandForm.setDefaultItemAliasTypeName(defaultItemAliasTypeName);
            commandForm.setPersonalTitleId(personalTitleId);
            commandForm.setFirstName(firstName);
            commandForm.setMiddleName(middleName);
            commandForm.setLastName(lastName);
            commandForm.setNameSuffixId(nameSuffixId);
            commandForm.setName(name);
            commandForm.setPreferredLanguageIsoName(preferredLanguageIsoName);
            commandForm.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
            commandForm.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
            commandForm.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setAllowSolicitation(allowSolicitation);

            var commandResult = PartyUtil.getHome().createVendor(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateVendorResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editVendor(final DataFetchingEnvironment env,
            @GraphQLName("originalVendorName") final String originalVendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("vendorTypeName") final String vendorTypeName,
            @GraphQLName("minimumPurchaseOrderLines") final String minimumPurchaseOrderLines,
            @GraphQLName("maximumPurchaseOrderLines") final String maximumPurchaseOrderLines,
            @GraphQLName("minimumPurchaseOrderAmount") final String minimumPurchaseOrderAmount,
            @GraphQLName("maximumPurchaseOrderAmount") final String maximumPurchaseOrderAmount,
            @GraphQLName("useItemPurchasingCategories") final String useItemPurchasingCategories,
            @GraphQLName("defaultItemAliasTypeName") final String defaultItemAliasTypeName,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName,
            @GraphQLName("apGlAccountName") final String apGlAccountName,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("preferredLanguageIsoName") final String preferredLanguageIsoName,
            @GraphQLName("preferredCurrencyIsoName") final String preferredCurrencyIsoName,
            @GraphQLName("preferredJavaTimeZoneName") final String preferredJavaTimeZoneName,
            @GraphQLName("preferredDateTimeFormatName") final String preferredDateTimeFormatName,
            @GraphQLName("holdUntilComplete") final String holdUntilComplete,
            @GraphQLName("allowBackorders") final String allowBackorders,
            @GraphQLName("allowSubstitutions") final String allowSubstitutions,
            @GraphQLName("allowCombiningShipments") final String allowCombiningShipments,
            @GraphQLName("requireReference") final String requireReference,
            @GraphQLName("allowReferenceDuplicates") final String allowReferenceDuplicates,
            @GraphQLName("referenceValidationPattern") final String referenceValidationPattern
    ) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = VendorUtil.getHome().getVendorUniversalSpec();

            spec.setVendorName(originalVendorName);
            spec.setPartyName(partyName);
            spec.setUuid(id);

            var commandForm = VendorUtil.getHome().getEditVendorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editVendor(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditVendorResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getVendor().getEntityInstance());

                if(arguments.containsKey("vendorName"))
                    edit.setVendorName(vendorName);
                if(arguments.containsKey("vendorTypeName"))
                    edit.setVendorTypeName(vendorTypeName);
                if(arguments.containsKey("minimumPurchaseOrderLines"))
                    edit.setMinimumPurchaseOrderLines(minimumPurchaseOrderLines);
                if(arguments.containsKey("maximumPurchaseOrderLines"))
                    edit.setMaximumPurchaseOrderLines(maximumPurchaseOrderLines);
                if(arguments.containsKey("minimumPurchaseOrderAmount"))
                    edit.setMinimumPurchaseOrderAmount(minimumPurchaseOrderAmount);
                if(arguments.containsKey("maximumPurchaseOrderAmount"))
                    edit.setMaximumPurchaseOrderAmount(maximumPurchaseOrderAmount);
                if(arguments.containsKey("useItemPurchasingCategories"))
                    edit.setUseItemPurchasingCategories(useItemPurchasingCategories);
                if(arguments.containsKey("defaultItemAliasTypeName"))
                    edit.setDefaultItemAliasTypeName(defaultItemAliasTypeName);
                if(arguments.containsKey("cancellationPolicyName"))
                    edit.setCancellationPolicyName(cancellationPolicyName);
                if(arguments.containsKey("returnPolicyName"))
                    edit.setReturnPolicyName(returnPolicyName);
                if(arguments.containsKey("apGlAccountName"))
                    edit.setApGlAccountName(apGlAccountName);
                if(arguments.containsKey("personalTitleId"))
                    edit.setPersonalTitleId(personalTitleId);
                if(arguments.containsKey("firstName"))
                    edit.setFirstName(firstName);
                if(arguments.containsKey("middleName"))
                    edit.setMiddleName(middleName);
                if(arguments.containsKey("lastName"))
                    edit.setLastName(lastName);
                if(arguments.containsKey("nameSuffixId"))
                    edit.setNameSuffixId(nameSuffixId);
                if(arguments.containsKey("name"))
                    edit.setName(name);
                if(arguments.containsKey("preferredLanguageIsoName"))
                    edit.setPreferredLanguageIsoName(preferredLanguageIsoName);
                if(arguments.containsKey("preferredCurrencyIsoName"))
                    edit.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
                if(arguments.containsKey("preferredJavaTimeZoneName"))
                    edit.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
                if(arguments.containsKey("preferredDateTimeFormatName"))
                    edit.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
                if(arguments.containsKey("holdUntilComplete"))
                    edit.setHoldUntilComplete(holdUntilComplete);
                if(arguments.containsKey("allowBackorders"))
                    edit.setAllowBackorders(allowBackorders);
                if(arguments.containsKey("allowSubstitutions"))
                    edit.setAllowSubstitutions(allowSubstitutions);
                if(arguments.containsKey("allowCombiningShipments"))
                    edit.setAllowCombiningShipments(allowCombiningShipments);
                if(arguments.containsKey("requireReference"))
                    edit.setRequireReference(requireReference);
                if(arguments.containsKey("allowReferenceDuplicates"))
                    edit.setAllowReferenceDuplicates(allowReferenceDuplicates);
                if(arguments.containsKey("referenceValidationPattern"))
                    edit.setReferenceValidationPattern(referenceValidationPattern);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = VendorUtil.getHome().editVendor(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setVendorStatus")
    static MutationResultObject setVendorStatus(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("vendorStatusChoice") @GraphQLNonNull final String vendorStatusChoice) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PartyUtil.getHome().getSetVendorStatusForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);
            commandForm.setVendorStatusChoice(vendorStatusChoice);

            mutationResultObject.setCommandResult(PartyUtil.getHome().setVendorStatus(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createVendorItem(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("vendorName") @GraphQLNonNull final String vendorName,
            @GraphQLName("vendorItemName") final String vendorItemName,
            @GraphQLName("description") final String description,
            @GraphQLName("priority") @GraphQLNonNull final String priority,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = VendorUtil.getHome().getCreateVendorItemForm();

            commandForm.setItemName(itemName);
            commandForm.setVendorName(vendorName);
            commandForm.setVendorItemName(vendorItemName);
            commandForm.setDescription(description);
            commandForm.setPriority(priority);
            commandForm.setCancellationPolicyName(cancellationPolicyName);
            commandForm.setReturnPolicyName(returnPolicyName);

            var commandResult = VendorUtil.getHome().createVendorItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateVendorItemResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editVendorItem(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("originalVendorItemName") final String originalVendorItemName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("vendorItemName") final String vendorItemName,
            @GraphQLName("description") final String description,
            @GraphQLName("priority") final String priority,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = VendorUtil.getHome().getVendorItemUniversalSpec();

            spec.setVendorName(vendorName);
            spec.setPartyName(partyName);
            spec.setVendorItemName(originalVendorItemName);
            spec.setUuid(id);

            var commandForm = VendorUtil.getHome().getEditVendorItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editVendorItem(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditVendorItemResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getVendorItem().getEntityInstance());

                if(arguments.containsKey("vendorItemName"))
                    edit.setVendorItemName(vendorItemName);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                if(arguments.containsKey("priority"))
                    edit.setPriority(priority);
                if(arguments.containsKey("cancellationPolicyName"))
                    edit.setCancellationPolicyName(cancellationPolicyName);
                if(arguments.containsKey("returnPolicyName"))
                    edit.setReturnPolicyName(returnPolicyName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = VendorUtil.getHome().editVendorItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setVendorItemStatus")
    static MutationResultObject setVendorItemStatus(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("vendorItemName") final String vendorItemName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("vendorItemStatusChoice") @GraphQLNonNull final String vendorItemStatusChoice) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getSetVendorItemStatusForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setVendorItemName(vendorItemName);
            commandForm.setUuid(id);
            commandForm.setVendorItemStatusChoice(vendorItemStatusChoice);

            mutationResultObject.setCommandResult(VendorUtil.getHome().setVendorItemStatus(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteVendorItem(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("vendorItemName") final String vendorItemName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getDeleteVendorItemForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setVendorItemName(vendorItemName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteVendorItem(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createVendorItemCost(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") @GraphQLNonNull final String vendorName,
            @GraphQLName("vendorItemName") @GraphQLNonNull final String vendorItemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("unitCost") @GraphQLNonNull final String unitCost) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getCreateVendorItemCostForm();

            commandForm.setVendorName(vendorName);
            commandForm.setVendorItemName(vendorItemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setUnitCost(unitCost);

            var commandResult = VendorUtil.getHome().createVendorItemCost(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editVendorItemCost(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") @GraphQLNonNull final String vendorName,
            @GraphQLName("vendorItemName") @GraphQLNonNull final String vendorItemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("unitCost") final String unitCost) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = VendorUtil.getHome().getVendorItemCostSpec();

            spec.setVendorName(vendorName);
            spec.setVendorItemName(vendorItemName);
            spec.setInventoryConditionName(inventoryConditionName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            var commandForm = VendorUtil.getHome().getEditVendorItemCostForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editVendorItemCost(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditVendorItemCostResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitCost"))
                    edit.setUnitCost(unitCost);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = VendorUtil.getHome().editVendorItemCost(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteVendorItemCost(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") @GraphQLNonNull final String vendorName,
            @GraphQLName("vendorItemName") @GraphQLNonNull final String vendorItemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getDeleteVendorItemCostForm();

            commandForm.setVendorName(vendorName);
            commandForm.setVendorItemName(vendorItemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteVendorItemCost(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }


    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWarehouseType(final DataFetchingEnvironment env,
            @GraphQLName("warehouseTypeName") @GraphQLNonNull final String warehouseTypeName,
            @GraphQLName("priority") @GraphQLNonNull final String priority,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WarehouseUtil.getHome().getCreateWarehouseTypeForm();

            commandForm.setWarehouseTypeName(warehouseTypeName);
            commandForm.setPriority(priority);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = WarehouseUtil.getHome().createWarehouseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWarehouseTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWarehouseType(final DataFetchingEnvironment env,
            @GraphQLName("warehouseTypeName") @GraphQLNonNull final String warehouseTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WarehouseUtil.getHome().getDeleteWarehouseTypeForm();

            commandForm.setWarehouseTypeName(warehouseTypeName);

            var commandResult = WarehouseUtil.getHome().deleteWarehouseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWarehouseType(final DataFetchingEnvironment env,
            @GraphQLName("originalWarehouseTypeName") final String originalWarehouseTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("warehouseTypeName") final String warehouseTypeName,
            @GraphQLName("priority") final String priority,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WarehouseUtil.getHome().getWarehouseTypeUniversalSpec();

            spec.setWarehouseTypeName(originalWarehouseTypeName);
            spec.setUuid(id);

            var commandForm = WarehouseUtil.getHome().getEditWarehouseTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WarehouseUtil.getHome().editWarehouseType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWarehouseTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWarehouseType().getEntityInstance());

                if(arguments.containsKey("warehouseTypeName"))
                    edit.setWarehouseTypeName(warehouseTypeName);
                if(arguments.containsKey("priority"))
                    edit.setPriority(priority);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WarehouseUtil.getHome().editWarehouseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWarehouse(final DataFetchingEnvironment env,
            @GraphQLName("warehouseName") @GraphQLNonNull final String warehouseName,
            @GraphQLName("warehouseTypeName") @GraphQLNonNull final String warehouseTypeName,
            @GraphQLName("name") final String name,
            @GraphQLName("preferredLanguageIsoName") final String preferredLanguageIsoName,
            @GraphQLName("preferredCurrencyIsoName") final String preferredCurrencyIsoName,
            @GraphQLName("preferredJavaTimeZoneName") final String preferredJavaTimeZoneName,
            @GraphQLName("preferredDateTimeFormatName") final String preferredDateTimeFormatName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("inventoryMovePrinterGroupName") @GraphQLNonNull final String inventoryMovePrinterGroupName,
            @GraphQLName("picklistPrinterGroupName") @GraphQLNonNull final String picklistPrinterGroupName,
            @GraphQLName("packingListPrinterGroupName") @GraphQLNonNull final String packingListPrinterGroupName,
            @GraphQLName("shippingManifestPrinterGroupName") @GraphQLNonNull final String shippingManifestPrinterGroupName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WarehouseUtil.getHome().getCreateWarehouseForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.setWarehouseTypeName(warehouseTypeName);
            commandForm.setName(name);
            commandForm.setPreferredLanguageIsoName(preferredLanguageIsoName);
            commandForm.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
            commandForm.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
            commandForm.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setInventoryMovePrinterGroupName(inventoryMovePrinterGroupName);
            commandForm.setPicklistPrinterGroupName(picklistPrinterGroupName);
            commandForm.setPackingListPrinterGroupName(packingListPrinterGroupName);
            commandForm.setShippingManifestPrinterGroupName(shippingManifestPrinterGroupName);

            var commandResult = WarehouseUtil.getHome().createWarehouse(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWarehouseResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWarehouse(final DataFetchingEnvironment env,
            @GraphQLName("originalWarehouseName") final String originalWarehouseName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("warehouseName") final String warehouseName,
            @GraphQLName("warehouseTypeName") final String warehouseTypeName,
            @GraphQLName("name") final String name,
            @GraphQLName("preferredLanguageIsoName") final String preferredLanguageIsoName,
            @GraphQLName("preferredCurrencyIsoName") final String preferredCurrencyIsoName,
            @GraphQLName("preferredJavaTimeZoneName") final String preferredJavaTimeZoneName,
            @GraphQLName("preferredDateTimeFormatName") final String preferredDateTimeFormatName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("inventoryMovePrinterGroupName") final String inventoryMovePrinterGroupName,
            @GraphQLName("picklistPrinterGroupName") final String picklistPrinterGroupName,
            @GraphQLName("packingListPrinterGroupName") final String packingListPrinterGroupName,
            @GraphQLName("shippingManifestPrinterGroupName") final String shippingManifestPrinterGroupName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WarehouseUtil.getHome().getWarehouseUniversalSpec();

            spec.setWarehouseName(originalWarehouseName);
            spec.setPartyName(partyName);
            spec.setUuid(id);

            var commandForm = WarehouseUtil.getHome().getEditWarehouseForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WarehouseUtil.getHome().editWarehouse(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWarehouseResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWarehouse().getEntityInstance());

                if(arguments.containsKey("warehouseName"))
                    edit.setWarehouseName(warehouseName);
                if(arguments.containsKey("warehouseTypeName"))
                    edit.setWarehouseTypeName(warehouseTypeName);
                if(arguments.containsKey("name"))
                    edit.setName(name);
                if(arguments.containsKey("preferredLanguageIsoName"))
                    edit.setPreferredLanguageIsoName(preferredLanguageIsoName);
                if(arguments.containsKey("preferredCurrencyIsoName"))
                    edit.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
                if(arguments.containsKey("preferredJavaTimeZoneName"))
                    edit.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
                if(arguments.containsKey("preferredDateTimeFormatName"))
                    edit.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("inventoryMovePrinterGroupName"))
                    edit.setInventoryMovePrinterGroupName(inventoryMovePrinterGroupName);
                if(arguments.containsKey("picklistPrinterGroupName"))
                    edit.setPicklistPrinterGroupName(picklistPrinterGroupName);
                if(arguments.containsKey("packingListPrinterGroupName"))
                    edit.setPackingListPrinterGroupName(packingListPrinterGroupName);
                if(arguments.containsKey("shippingManifestPrinterGroupName"))
                    edit.setShippingManifestPrinterGroupName(shippingManifestPrinterGroupName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WarehouseUtil.getHome().editWarehouse(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWarehouse(final DataFetchingEnvironment env,
            @GraphQLName("warehouseName") final String warehouseName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WarehouseUtil.getHome().getDeleteWarehouseForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(WarehouseUtil.getHome().deleteWarehouse(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemImageType(final DataFetchingEnvironment env,
            @GraphQLName("itemImageTypeName") @GraphQLNonNull final String itemImageTypeName,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemImageTypeForm();

            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setPreferredMimeTypeName(preferredMimeTypeName);
            commandForm.setQuality(quality);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemImageType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemImageTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemImageType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemImageTypeName") final String originalItemImageTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemImageTypeUniversalSpec();

            spec.setItemImageTypeName(originalItemImageTypeName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemImageTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemImageType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemImageTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemImageType().getEntityInstance());

                if(arguments.containsKey("itemImageTypeName"))
                    edit.setItemImageTypeName(itemImageTypeName);
                if(arguments.containsKey("preferredMimeTypeName"))
                    edit.setPreferredMimeTypeName(preferredMimeTypeName);
                if(arguments.containsKey("quality"))
                    edit.setQuality(quality);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemImageType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemImageType(final DataFetchingEnvironment env,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemImageTypeForm();

            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemImageType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeUseTypeForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemDescriptionTypeUseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemDescriptionTypeUseTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemDescriptionTypeUseTypeName") final String originalItemDescriptionTypeUseTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemDescriptionTypeUseTypeUniversalSpec();

            spec.setItemDescriptionTypeUseTypeName(originalItemDescriptionTypeUseTypeName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeUseTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemDescriptionTypeUseTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemDescriptionTypeUseType().getEntityInstance());

                if(arguments.containsKey("itemDescriptionTypeUseTypeName"))
                    edit.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeUseTypeForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionTypeUseType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createItemDescriptionTypeUse(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeUseForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            var commandResult = ItemUtil.getHome().createItemDescriptionTypeUse(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemDescriptionTypeUse(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeUseForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionTypeUse(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("parentItemDescriptionTypeName") final String parentItemDescriptionTypeName,
            @GraphQLName("useParentIfMissing") @GraphQLNonNull final String useParentIfMissing,
            @GraphQLName("mimeTypeUsageTypeName") final String mimeTypeUsageTypeName,
            @GraphQLName("checkContentWebAddress") @GraphQLNonNull final String checkContentWebAddress,
            @GraphQLName("includeInIndex") @GraphQLNonNull final String includeInIndex,
            @GraphQLName("indexDefault") @GraphQLNonNull final String indexDefault,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description,
            @GraphQLName("minimumHeight") final String minimumHeight,
            @GraphQLName("minimumWidth") final String minimumWidth,
            @GraphQLName("maximumHeight") final String maximumHeight,
            @GraphQLName("maximumWidth") final String maximumWidth,
            @GraphQLName("preferredHeight") final String preferredHeight,
            @GraphQLName("preferredWidth") final String preferredWidth,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("scaleFromParent") final String scaleFromParent) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setParentItemDescriptionTypeName(parentItemDescriptionTypeName);
            commandForm.setUseParentIfMissing(useParentIfMissing);
            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);
            commandForm.setCheckContentWebAddress(checkContentWebAddress);
            commandForm.setIncludeInIndex(includeInIndex);
            commandForm.setIndexDefault(indexDefault);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);
            commandForm.setMinimumHeight(minimumHeight);
            commandForm.setMinimumWidth(minimumWidth);
            commandForm.setMaximumHeight(maximumHeight);
            commandForm.setMaximumWidth(maximumWidth);
            commandForm.setPreferredHeight(preferredHeight);
            commandForm.setPreferredWidth(preferredWidth);
            commandForm.setPreferredMimeTypeName(preferredMimeTypeName);
            commandForm.setQuality(quality);
            commandForm.setScaleFromParent(scaleFromParent);

            var commandResult = ItemUtil.getHome().createItemDescriptionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemDescriptionTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemDescriptionTypeName") final String originalItemDescriptionTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("parentItemDescriptionTypeName") final String parentItemDescriptionTypeName,
            @GraphQLName("useParentIfMissing") final String useParentIfMissing,
            @GraphQLName("checkContentWebAddress") final String checkContentWebAddress,
            @GraphQLName("includeInIndex") final String includeInIndex,
            @GraphQLName("indexDefault") final String indexDefault,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description,
            @GraphQLName("minimumHeight") final String minimumHeight,
            @GraphQLName("minimumWidth") final String minimumWidth,
            @GraphQLName("maximumHeight") final String maximumHeight,
            @GraphQLName("maximumWidth") final String maximumWidth,
            @GraphQLName("preferredHeight") final String preferredHeight,
            @GraphQLName("preferredWidth") final String preferredWidth,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("scaleFromParent") final String scaleFromParent) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemDescriptionTypeUniversalSpec();

            spec.setItemDescriptionTypeName(originalItemDescriptionTypeName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescriptionType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemDescriptionTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemDescriptionType().getEntityInstance());

                if(arguments.containsKey("itemDescriptionTypeName"))
                    edit.setItemDescriptionTypeName(itemDescriptionTypeName);
                if(arguments.containsKey("useParentIfMissing"))
                    edit.setUseParentIfMissing(useParentIfMissing);
                if(arguments.containsKey("checkContentWebAddress"))
                    edit.setCheckContentWebAddress(checkContentWebAddress);
                if(arguments.containsKey("includeInIndex"))
                    edit.setIncludeInIndex(includeInIndex);
                if(arguments.containsKey("indexDefault"))
                    edit.setIndexDefault(indexDefault);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                if(arguments.containsKey("minimumHeight"))
                    edit.setMinimumHeight(minimumHeight);
                if(arguments.containsKey("minimumWidth"))
                    edit.setMinimumWidth(minimumWidth);
                if(arguments.containsKey("maximumHeight"))
                    edit.setMaximumHeight(maximumHeight);
                if(arguments.containsKey("maximumWidth"))
                    edit.setMaximumWidth(maximumWidth);
                if(arguments.containsKey("preferredHeight"))
                    edit.setPreferredHeight(preferredHeight);
                if(arguments.containsKey("preferredWidth"))
                    edit.setPreferredWidth(preferredWidth);
                if(arguments.containsKey("preferredMimeTypeName"))
                    edit.setPreferredMimeTypeName(preferredMimeTypeName);
                if(arguments.containsKey("quality"))
                    edit.setQuality(quality);
                if(arguments.containsKey("scaleFromParent"))
                    edit.setScaleFromParent(scaleFromParent);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemDescriptionType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("itemAliasTypeName") @GraphQLNonNull final String itemAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("itemAliasChecksumTypeName") @GraphQLNonNull final String itemAliasChecksumTypeName,
            @GraphQLName("allowMultiple") @GraphQLNonNull final String allowMultiple,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemAliasTypeForm();

            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
            commandForm.setAllowMultiple(allowMultiple);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemAliasTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemAliasTypeName") final String originalItemAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("itemAliasChecksumTypeName") final String itemAliasChecksumTypeName,
            @GraphQLName("allowMultiple") final String allowMultiple,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemAliasTypeUniversalSpec();

            spec.setItemAliasTypeName(originalItemAliasTypeName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemAliasTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemAliasTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemAliasType().getEntityInstance());

                if(arguments.containsKey("itemAliasTypeName"))
                    edit.setItemAliasTypeName(itemAliasTypeName);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("itemAliasChecksumTypeName"))
                    edit.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
                if(arguments.containsKey("allowMultiple"))
                    edit.setAllowMultiple(allowMultiple);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemAliasType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemAliasTypeForm();

            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemAliasType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createItemAlias(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("itemAliasTypeName") @GraphQLNonNull final String itemAliasTypeName,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemAliasForm();

            commandForm.setItemName(itemName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setAlias(alias);

            var commandResult = ItemUtil.getHome().createItemAlias(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editItemAlias(final DataFetchingEnvironment env,
            @GraphQLName("originalAlias") @GraphQLNonNull final String originalAlias,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("alias") final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = ItemUtil.getHome().getItemAliasSpec();

            spec.setAlias(originalAlias);

            var commandForm = ItemUtil.getHome().getEditItemAliasForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemAlias(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemAliasResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitOfMeasureTypeName"))
                    edit.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                if(arguments.containsKey("itemAliasTypeName"))
                    edit.setItemAliasTypeName(itemAliasTypeName);
                if(arguments.containsKey("alias"))
                    edit.setAlias(alias);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemAlias(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemAlias(final DataFetchingEnvironment env,
            @GraphQLName("alias") @GraphQLID final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemAliasForm();

            commandForm.setAlias(alias);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemAlias(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemWeightType(final DataFetchingEnvironment env,
            @GraphQLName("itemWeightTypeName") @GraphQLNonNull final String itemWeightTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemWeightTypeForm();

            commandForm.setItemWeightTypeName(itemWeightTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemWeightType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemWeightTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemWeightType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemWeightTypeName") final String originalItemWeightTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemWeightTypeName") final String itemWeightTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemWeightTypeUniversalSpec();

            spec.setItemWeightTypeName(originalItemWeightTypeName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemWeightTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemWeightType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemWeightTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemWeightType().getEntityInstance());

                if(arguments.containsKey("itemWeightTypeName"))
                    edit.setItemWeightTypeName(itemWeightTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemWeightType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemWeightType(final DataFetchingEnvironment env,
            @GraphQLName("itemWeightTypeName") final String itemWeightTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemWeightTypeForm();

            commandForm.setItemWeightTypeName(itemWeightTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemWeightType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemVolumeType(final DataFetchingEnvironment env,
            @GraphQLName("itemVolumeTypeName") @GraphQLNonNull final String itemVolumeTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemVolumeTypeForm();

            commandForm.setItemVolumeTypeName(itemVolumeTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemVolumeType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemVolumeTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemVolumeType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemVolumeTypeName") final String originalItemVolumeTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemVolumeTypeName") final String itemVolumeTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemVolumeTypeUniversalSpec();

            spec.setItemVolumeTypeName(originalItemVolumeTypeName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemVolumeTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemVolumeType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemVolumeTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemVolumeType().getEntityInstance());

                if(arguments.containsKey("itemVolumeTypeName"))
                    edit.setItemVolumeTypeName(itemVolumeTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemVolumeType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemVolumeType(final DataFetchingEnvironment env,
            @GraphQLName("itemVolumeTypeName") final String itemVolumeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemVolumeTypeForm();

            commandForm.setItemVolumeTypeName(itemVolumeTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemVolumeType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createRelatedItemType(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") @GraphQLNonNull final String relatedItemTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateRelatedItemTypeForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createRelatedItemType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateRelatedItemTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editRelatedItemType(final DataFetchingEnvironment env,
            @GraphQLName("originalRelatedItemTypeName") final String originalRelatedItemTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("relatedItemTypeName") final String relatedItemTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getRelatedItemTypeUniversalSpec();

            spec.setRelatedItemTypeName(originalRelatedItemTypeName);
            spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditRelatedItemTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editRelatedItemType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditRelatedItemTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getRelatedItemType().getEntityInstance());

                if(arguments.containsKey("relatedItemTypeName"))
                    edit.setRelatedItemTypeName(relatedItemTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editRelatedItemType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteRelatedItemType(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") final String relatedItemTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteRelatedItemTypeForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteRelatedItemType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createRelatedItem(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") @GraphQLNonNull final String relatedItemTypeName,
            @GraphQLName("fromItemName") @GraphQLNonNull final String fromItemName,
            @GraphQLName("toItemName") @GraphQLNonNull final String toItemName,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateRelatedItemForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setFromItemName(fromItemName);
            commandForm.setToItemName(toItemName);
            commandForm.setSortOrder(sortOrder);

            var commandResult = ItemUtil.getHome().createRelatedItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateRelatedItemResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editRelatedItem(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") @GraphQLNonNull final String relatedItemTypeName,
            @GraphQLName("fromItemName") @GraphQLNonNull final String fromItemName,
            @GraphQLName("toItemName") @GraphQLNonNull final String toItemName,
            @GraphQLName("sortOrder") final String sortOrder) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getRelatedItemSpec();

            spec.setRelatedItemTypeName(relatedItemTypeName);
            spec.setFromItemName(fromItemName);
            spec.setToItemName(toItemName);

            var commandForm = ItemUtil.getHome().getEditRelatedItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editRelatedItem(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditRelatedItemResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getRelatedItem().getEntityInstance());

                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editRelatedItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteRelatedItem(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") @GraphQLNonNull final String relatedItemTypeName,
            @GraphQLName("fromItemName") @GraphQLNonNull final String fromItemName,
            @GraphQLName("toItemName") @GraphQLNonNull final String toItemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteRelatedItemForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setFromItemName(fromItemName);
            commandForm.setToItemName(toItemName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteRelatedItem(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItem(final DataFetchingEnvironment env,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemTypeName") @GraphQLNonNull final String itemTypeName,
            @GraphQLName("itemUseTypeName") @GraphQLNonNull final String itemUseTypeName,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName,
            @GraphQLName("itemDeliveryTypeName") final String itemDeliveryTypeName,
            @GraphQLName("itemInventoryTypeName") final String itemInventoryTypeName,
            @GraphQLName("inventorySerialized") final String inventorySerialized,
            @GraphQLName("shippingChargeExempt") @GraphQLNonNull final String shippingChargeExempt,
            @GraphQLName("shippingStartTime") final String shippingStartTime,
            @GraphQLName("shippingEndTime") final String shippingEndTime,
            @GraphQLName("salesOrderStartTime") final String salesOrderStartTime,
            @GraphQLName("salesOrderEndTime") final String salesOrderEndTime,
            @GraphQLName("purchaseOrderStartTime") final String purchaseOrderStartTime,
            @GraphQLName("purchaseOrderEndTime") final String purchaseOrderEndTime,
            @GraphQLName("allowClubDiscounts") @GraphQLNonNull final String allowClubDiscounts,
            @GraphQLName("allowCouponDiscounts") @GraphQLNonNull final String allowCouponDiscounts,
            @GraphQLName("allowAssociatePayments") @GraphQLNonNull final String allowAssociatePayments,
            @GraphQLName("itemStatus") @GraphQLNonNull final String itemStatus,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName,
            @GraphQLName("itemPriceTypeName") @GraphQLNonNull final String itemPriceTypeName,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemForm();

            commandForm.setItemName(itemName);
            commandForm.setItemTypeName(itemTypeName);
            commandForm.setItemUseTypeName(itemUseTypeName);
            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setCompanyName(companyName);
            commandForm.setItemDeliveryTypeName(itemDeliveryTypeName);
            commandForm.setItemInventoryTypeName(itemInventoryTypeName);
            commandForm.setInventorySerialized(inventorySerialized);
            commandForm.setShippingChargeExempt(shippingChargeExempt);
            commandForm.setShippingStartTime(shippingStartTime);
            commandForm.setShippingEndTime(shippingEndTime);
            commandForm.setSalesOrderStartTime(salesOrderStartTime);
            commandForm.setSalesOrderEndTime(salesOrderEndTime);
            commandForm.setPurchaseOrderStartTime(purchaseOrderStartTime);
            commandForm.setPurchaseOrderEndTime(purchaseOrderEndTime);
            commandForm.setAllowClubDiscounts(allowClubDiscounts);
            commandForm.setAllowCouponDiscounts(allowCouponDiscounts);
            commandForm.setAllowAssociatePayments(allowAssociatePayments);
            commandForm.setItemStatus(itemStatus);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setItemPriceTypeName(itemPriceTypeName);
            commandForm.setCancellationPolicyName(cancellationPolicyName);
            commandForm.setReturnPolicyName(returnPolicyName);

            var commandResult = ItemUtil.getHome().createItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItem(final DataFetchingEnvironment env,
            @GraphQLName("originalItemName") @GraphQLNonNull final String originalItemName,
            // @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("shippingChargeExempt") final String shippingChargeExempt,
            @GraphQLName("shippingStartTime") final String shippingStartTime,
            @GraphQLName("shippingEndTime") final String shippingEndTime,
            @GraphQLName("salesOrderStartTime") final String salesOrderStartTime,
            @GraphQLName("salesOrderEndTime") final String salesOrderEndTime,
            @GraphQLName("purchaseOrderStartTime") final String purchaseOrderStartTime,
            @GraphQLName("purchaseOrderEndTime") final String purchaseOrderEndTime,
            @GraphQLName("allowClubDiscounts") final String allowClubDiscounts,
            @GraphQLName("allowCouponDiscounts") final String allowCouponDiscounts,
            @GraphQLName("allowAssociatePayments") final String allowAssociatePayments,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemUniversalSpec();

            spec.setItemName(originalItemName);
            // spec.setUuid(id);

            var commandForm = ItemUtil.getHome().getEditItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItem(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItem().getEntityInstance());

                if(arguments.containsKey("itemName"))
                    edit.setItemName(itemName);
                if(arguments.containsKey("itemCategoryName"))
                    edit.setItemCategoryName(itemCategoryName);
                if(arguments.containsKey("itemAccountingCategoryName"))
                    edit.setItemAccountingCategoryName(itemAccountingCategoryName);
                if(arguments.containsKey("itemPurchasingCategoryName"))
                    edit.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                if(arguments.containsKey("shippingChargeExempt"))
                    edit.setShippingChargeExempt(shippingChargeExempt);
                if(arguments.containsKey("shippingStartTime"))
                    edit.setShippingStartTime(shippingStartTime);
                if(arguments.containsKey("shippingEndTime"))
                    edit.setShippingEndTime(shippingEndTime);
                if(arguments.containsKey("salesOrderStartTime"))
                    edit.setSalesOrderStartTime(salesOrderStartTime);
                if(arguments.containsKey("salesOrderEndTime"))
                    edit.setSalesOrderEndTime(salesOrderEndTime);
                if(arguments.containsKey("purchaseOrderStartTime"))
                    edit.setPurchaseOrderStartTime(purchaseOrderStartTime);
                if(arguments.containsKey("purchaseOrderEndTime"))
                    edit.setPurchaseOrderEndTime(purchaseOrderEndTime);
                if(arguments.containsKey("allowClubDiscounts"))
                    edit.setAllowClubDiscounts(allowClubDiscounts);
                if(arguments.containsKey("allowCouponDiscounts"))
                    edit.setAllowCouponDiscounts(allowCouponDiscounts);
                if(arguments.containsKey("allowAssociatePayments"))
                    edit.setAllowAssociatePayments(allowAssociatePayments);
                if(arguments.containsKey("cancellationPolicyName"))
                    edit.setCancellationPolicyName(cancellationPolicyName);
                if(arguments.containsKey("returnPolicyName"))
                    edit.setReturnPolicyName(returnPolicyName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItem(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setItemStatus")
    static MutationResultObject setItemStatus(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemStatusName,
            @GraphQLName("itemStatusChoice") @GraphQLNonNull final String itemStatusChoice) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getSetItemStatusForm();

            commandForm.setItemName(itemStatusName);
            commandForm.setItemStatusChoice(itemStatusChoice);

            mutationResultObject.setCommandResult(ItemUtil.getHome().setItemStatus(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createItemUnitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemUnitOfMeasureTypeForm();

            commandForm.setItemName(itemName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);

            var commandResult = ItemUtil.getHome().createItemUnitOfMeasureType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editItemUnitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = ItemUtil.getHome().getItemUnitOfMeasureTypeSpec();

            spec.setItemName(itemName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            var commandForm = ItemUtil.getHome().getEditItemUnitOfMeasureTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemUnitOfMeasureType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemUnitOfMeasureTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemUnitOfMeasureType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemUnitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemUnitOfMeasureTypeForm();

            commandForm.setItemName(itemName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemUnitOfMeasureType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createItemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName,
            @GraphQLName("mimeTypeName") final String mimeTypeName,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("clobDescription") final String clobDescription,
            @GraphQLName("stringDescription") final String stringDescription) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionForm();

            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setMimeTypeName(mimeTypeName);
            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setClobDescription(clobDescription);
            commandForm.setStringDescription(stringDescription);

            var commandResult = ItemUtil.getHome().createItemDescription(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemDescriptionResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editItemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName,
            @GraphQLName("mimeTypeName") final String mimeTypeName,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("clobDescription") final String clobDescription,
            @GraphQLName("stringDescription") final String stringDescription) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemDescriptionUniversalSpec();

            spec.setItemName(itemName);
            spec.setItemDescriptionTypeName(itemDescriptionTypeName);
            spec.setLanguageIsoName(languageIsoName);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescription(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemDescriptionResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getItemDescription().getEntityInstance());

                if(arguments.containsKey("mimeTypeName"))
                    edit.setMimeTypeName(mimeTypeName);
                if(arguments.containsKey("itemImageTypeName"))
                    edit.setItemImageTypeName(itemImageTypeName);
                if(arguments.containsKey("clobDescription"))
                    edit.setClobDescription(clobDescription);
                if(arguments.containsKey("stringDescription"))
                    edit.setStringDescription(stringDescription);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemDescription(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionForm();

            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setLanguageIsoName(languageIsoName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescription(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemPriceForm();

            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUnitPrice(unitPrice);
            commandForm.setMinimumUnitPrice(minimumUnitPrice);
            commandForm.setMaximumUnitPrice(maximumUnitPrice);
            commandForm.setUnitPriceIncrement(unitPriceIncrement);

            var commandResult = ItemUtil.getHome().createItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemPriceForm();

            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = ItemUtil.getHome().deleteItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject editItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = ItemUtil.getHome().getItemPriceSpec();

            spec.setItemName(itemName);
            spec.setInventoryConditionName(inventoryConditionName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = ItemUtil.getHome().getEditItemPriceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemPriceResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitPrice"))
                    edit.setUnitPrice(unitPrice);
                if(arguments.containsKey("minimumUnitPrice"))
                    edit.setMinimumUnitPrice(minimumUnitPrice);
                if(arguments.containsKey("maximumUnitPrice"))
                    edit.setMaximumUnitPrice(maximumUnitPrice);
                if(arguments.containsKey("unitPriceIncrement"))
                    edit.setUnitPriceIncrement(unitPriceIncrement);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemPrice(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createOrderType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") @GraphQLNonNull final String orderTypeName,
            @GraphQLName("orderSequenceTypeName") final String orderSequenceTypeName,
            @GraphQLName("orderWorkflowName") final String orderWorkflowName,
            @GraphQLName("orderWorkflowEntranceName") final String orderWorkflowEntranceName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OrderUtil.getHome().getCreateOrderTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderSequenceTypeName(orderSequenceTypeName);
            commandForm.setOrderWorkflowName(orderWorkflowName);
            commandForm.setOrderWorkflowEntranceName(orderWorkflowEntranceName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OrderUtil.getHome().createOrderType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOrderTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editOrderType(final DataFetchingEnvironment env,
            @GraphQLName("originalOrderTypeName") final String originalOrderTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderSequenceTypeName") final String orderSequenceTypeName,
            @GraphQLName("orderWorkflowName") final String orderWorkflowName,
            @GraphQLName("orderWorkflowEntranceName") final String orderWorkflowEntranceName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OrderUtil.getHome().getOrderTypeUniversalSpec();

            spec.setOrderTypeName(originalOrderTypeName);
            spec.setUuid(id);

            var commandForm = OrderUtil.getHome().getEditOrderTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OrderUtil.getHome().editOrderType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOrderTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOrderType().getEntityInstance());

                if(arguments.containsKey("orderTypeName"))
                    edit.setOrderTypeName(orderTypeName);
                if(arguments.containsKey("orderSequenceTypeName"))
                    edit.setOrderSequenceTypeName(orderSequenceTypeName);
                if(arguments.containsKey("orderWorkflowName"))
                    edit.setOrderWorkflowName(orderWorkflowName);
                if(arguments.containsKey("orderWorkflowEntranceName"))
                    edit.setOrderWorkflowEntranceName(orderWorkflowEntranceName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OrderUtil.getHome().editOrderType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOrderType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OrderUtil.getHome().getDeleteOrderTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(OrderUtil.getHome().deleteOrderType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createOrderTimeType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderTimeTypeName") @GraphQLNonNull final String orderTimeTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OrderUtil.getHome().getCreateOrderTimeTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderTimeTypeName(orderTimeTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OrderUtil.getHome().createOrderTimeType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOrderTimeTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editOrderTimeType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("originalOrderTimeTypeName") final String originalOrderTimeTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("orderTimeTypeName") final String orderTimeTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OrderUtil.getHome().getOrderTimeTypeUniversalSpec();

            spec.setOrderTypeName(orderTypeName);
            spec.setOrderTimeTypeName(originalOrderTimeTypeName);
            spec.setUuid(id);

            var commandForm = OrderUtil.getHome().getEditOrderTimeTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OrderUtil.getHome().editOrderTimeType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOrderTimeTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOrderTimeType().getEntityInstance());

                if(arguments.containsKey("orderTimeTypeName"))
                    edit.setOrderTimeTypeName(orderTimeTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OrderUtil.getHome().editOrderTimeType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOrderTimeType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderTimeTypeName") final String orderTimeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OrderUtil.getHome().getDeleteOrderTimeTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderTimeTypeName(orderTimeTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(OrderUtil.getHome().deleteOrderTimeType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createOrderPriority(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") @GraphQLNonNull final String orderTypeName,
            @GraphQLName("orderPriorityName") @GraphQLNonNull final String orderPriorityName,
            @GraphQLName("priority") @GraphQLNonNull final String priority,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OrderUtil.getHome().getCreateOrderPriorityForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderPriorityName(orderPriorityName);
            commandForm.setPriority(priority);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OrderUtil.getHome().createOrderPriority(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOrderPriorityResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editOrderPriority(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("originalOrderPriorityName") final String originalOrderPriorityName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("orderPriorityName") final String orderPriorityName,
            @GraphQLName("priority") final String priority,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = OrderUtil.getHome().getOrderPriorityUniversalSpec();

            spec.setOrderTypeName(orderTypeName);
            spec.setOrderPriorityName(originalOrderPriorityName);
            spec.setUuid(id);

            var commandForm = OrderUtil.getHome().getEditOrderPriorityForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OrderUtil.getHome().editOrderPriority(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOrderPriorityResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOrderPriority().getEntityInstance());

                if(arguments.containsKey("orderPriorityName"))
                    edit.setOrderPriorityName(orderPriorityName);
                if(arguments.containsKey("priority"))
                    edit.setPriority(priority);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OrderUtil.getHome().editOrderPriority(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteOrderPriority(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderPriorityName") final String orderPriorityName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OrderUtil.getHome().getDeleteOrderPriorityForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderPriorityName(orderPriorityName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(OrderUtil.getHome().deleteOrderPriority(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWishlistType(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") @GraphQLNonNull final String wishlistTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WishlistUtil.getHome().getCreateWishlistTypeForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = WishlistUtil.getHome().createWishlistType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWishlistTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWishlistType(final DataFetchingEnvironment env,
            @GraphQLName("originalWishlistTypeName") final String originalWishlistTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WishlistUtil.getHome().getWishlistTypeUniversalSpec();

            spec.setWishlistTypeName(originalWishlistTypeName);
            spec.setUuid(id);

            var commandForm = WishlistUtil.getHome().getEditWishlistTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WishlistUtil.getHome().editWishlistType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWishlistTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWishlistType().getEntityInstance());

                if(arguments.containsKey("wishlistTypeName"))
                    edit.setWishlistTypeName(wishlistTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WishlistUtil.getHome().editWishlistType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWishlistType(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WishlistUtil.getHome().getDeleteWishlistTypeForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(WishlistUtil.getHome().deleteWishlistType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWishlistPriority(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") @GraphQLNonNull final String wishlistTypeName,
            @GraphQLName("wishlistPriorityName") @GraphQLNonNull final String wishlistPriorityName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WishlistUtil.getHome().getCreateWishlistPriorityForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setWishlistPriorityName(wishlistPriorityName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = WishlistUtil.getHome().createWishlistPriority(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWishlistPriorityResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWishlistPriority(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("originalWishlistPriorityName") final String originalWishlistPriorityName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("wishlistPriorityName") final String wishlistPriorityName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WishlistUtil.getHome().getWishlistPriorityUniversalSpec();

            spec.setWishlistTypeName(wishlistTypeName);
            spec.setWishlistPriorityName(originalWishlistPriorityName);
            spec.setUuid(id);

            var commandForm = WishlistUtil.getHome().getEditWishlistPriorityForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WishlistUtil.getHome().editWishlistPriority(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWishlistPriorityResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWishlistPriority().getEntityInstance());

                if(arguments.containsKey("wishlistPriorityName"))
                    edit.setWishlistPriorityName(wishlistPriorityName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WishlistUtil.getHome().editWishlistPriority(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWishlistPriority(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("wishlistPriorityName") final String wishlistPriorityName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WishlistUtil.getHome().getDeleteWishlistPriorityForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setWishlistPriorityName(wishlistPriorityName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(WishlistUtil.getHome().deleteWishlistPriority(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject unlockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getUnlockEntityForm();

            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(CoreUtil.getHome().unlockEntity(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject lockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getLockEntityForm();

            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(CoreUtil.getHome().lockEntity(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject customerLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getCustomerLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(BaseGraphQl.getRemoteInet4Address(env));

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().customerLogin(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createCustomerType(final DataFetchingEnvironment env,
            @GraphQLName("customerTypeName") @GraphQLNonNull final String customerTypeName,
            @GraphQLName("customerSequenceName") final String customerSequenceName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("defaultTermName") final String defaultTermName,
            @GraphQLName("defaultFreeOnBoardName") final String defaultFreeOnBoardName,
            @GraphQLName("defaultCancellationPolicyName") final String defaultCancellationPolicyName,
            @GraphQLName("defaultReturnPolicyName") final String defaultReturnPolicyName,
            @GraphQLName("defaultCustomerStatusChoice") final String defaultCustomerStatusChoice,
            @GraphQLName("defaultCustomerCreditStatusChoice") final String defaultCustomerCreditStatusChoice,
            @GraphQLName("defaultArGlAccountName") final String defaultArGlAccountName,
            @GraphQLName("defaultHoldUntilComplete") @GraphQLNonNull final String defaultHoldUntilComplete,
            @GraphQLName("defaultAllowBackorders") @GraphQLNonNull final String defaultAllowBackorders,
            @GraphQLName("defaultAllowSubstitutions") @GraphQLNonNull final String defaultAllowSubstitutions,
            @GraphQLName("defaultAllowCombiningShipments") @GraphQLNonNull final String defaultAllowCombiningShipments,
            @GraphQLName("defaultRequireReference") @GraphQLNonNull final String defaultRequireReference,
            @GraphQLName("defaultAllowReferenceDuplicates") @GraphQLNonNull final String defaultAllowReferenceDuplicates,
            @GraphQLName("defaultReferenceValidationPattern") final String defaultReferenceValidationPattern,
            @GraphQLName("defaultTaxable") @GraphQLNonNull final String defaultTaxable,
            @GraphQLName("allocationPriorityName") final String allocationPriorityName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CustomerUtil.getHome().getCreateCustomerTypeForm();

            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setCustomerSequenceName(customerSequenceName);
            commandForm.setDefaultOfferName(defaultOfferName);
            commandForm.setDefaultUseName(defaultUseName);
            commandForm.setDefaultSourceName(defaultSourceName);
            commandForm.setDefaultTermName(defaultTermName);
            commandForm.setDefaultFreeOnBoardName(defaultFreeOnBoardName);
            commandForm.setDefaultCancellationPolicyName(defaultCancellationPolicyName);
            commandForm.setDefaultReturnPolicyName(defaultReturnPolicyName);
            commandForm.setDefaultCustomerStatusChoice(defaultCustomerStatusChoice);
            commandForm.setDefaultCustomerCreditStatusChoice(defaultCustomerCreditStatusChoice);
            commandForm.setDefaultArGlAccountName(defaultArGlAccountName);
            commandForm.setDefaultHoldUntilComplete(defaultHoldUntilComplete);
            commandForm.setDefaultAllowBackorders(defaultAllowBackorders);
            commandForm.setDefaultAllowSubstitutions(defaultAllowSubstitutions);
            commandForm.setDefaultAllowCombiningShipments(defaultAllowCombiningShipments);
            commandForm.setDefaultRequireReference(defaultRequireReference);
            commandForm.setDefaultAllowReferenceDuplicates(defaultAllowReferenceDuplicates);
            commandForm.setDefaultReferenceValidationPattern(defaultReferenceValidationPattern);
            commandForm.setDefaultTaxable(defaultTaxable);
            commandForm.setAllocationPriorityName(allocationPriorityName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CustomerUtil.getHome().createCustomerType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateCustomerTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editCustomerType(final DataFetchingEnvironment env,
            @GraphQLName("originalCustomerTypeName") final String originalCustomerTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("customerTypeName") final String customerTypeName,
            @GraphQLName("customerSequenceName") final String customerSequenceName,
            @GraphQLName("defaultOfferName") final String defaultOfferName,
            @GraphQLName("defaultUseName") final String defaultUseName,
            @GraphQLName("defaultSourceName") final String defaultSourceName,
            @GraphQLName("defaultTermName") final String defaultTermName,
            @GraphQLName("defaultFreeOnBoardName") final String defaultFreeOnBoardName,
            @GraphQLName("defaultCancellationPolicyName") final String defaultCancellationPolicyName,
            @GraphQLName("defaultReturnPolicyName") final String defaultReturnPolicyName,
            @GraphQLName("defaultCustomerStatusChoice") final String defaultCustomerStatusChoice,
            @GraphQLName("defaultCustomerCreditStatusChoice") final String defaultCustomerCreditStatusChoice,
            @GraphQLName("defaultArGlAccountName") final String defaultArGlAccountName,
            @GraphQLName("defaultHoldUntilComplete") final String defaultHoldUntilComplete,
            @GraphQLName("defaultAllowBackorders") final String defaultAllowBackorders,
            @GraphQLName("defaultAllowSubstitutions") final String defaultAllowSubstitutions,
            @GraphQLName("defaultAllowCombiningShipments") final String defaultAllowCombiningShipments,
            @GraphQLName("defaultRequireReference") final String defaultRequireReference,
            @GraphQLName("defaultAllowReferenceDuplicates") final String defaultAllowReferenceDuplicates,
            @GraphQLName("defaultReferenceValidationPattern") final String defaultReferenceValidationPattern,
            @GraphQLName("defaultTaxable") final String defaultTaxable,
            @GraphQLName("allocationPriorityName") final String allocationPriorityName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = CustomerUtil.getHome().getCustomerTypeUniversalSpec();

            spec.setCustomerTypeName(originalCustomerTypeName);
            spec.setUuid(id);

            var commandForm = CustomerUtil.getHome().getEditCustomerTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CustomerUtil.getHome().editCustomerType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditCustomerTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getCustomerType().getEntityInstance());

                if(arguments.containsKey("customerTypeName"))
                    edit.setCustomerTypeName(customerTypeName);
                if(arguments.containsKey("customerSequenceName"))
                    edit.setCustomerSequenceName(customerSequenceName);
                if(arguments.containsKey("defaultOfferName"))
                    edit.setDefaultOfferName(defaultOfferName);
                if(arguments.containsKey("defaultUseName"))
                    edit.setDefaultUseName(defaultUseName);
                if(arguments.containsKey("defaultSourceName"))
                    edit.setDefaultSourceName(defaultSourceName);
                if(arguments.containsKey("defaultTermName"))
                    edit.setDefaultTermName(defaultTermName);
                if(arguments.containsKey("defaultFreeOnBoardName"))
                    edit.setDefaultFreeOnBoardName(defaultFreeOnBoardName);
                if(arguments.containsKey("defaultCancellationPolicyName"))
                    edit.setDefaultCancellationPolicyName(defaultCancellationPolicyName);
                if(arguments.containsKey("defaultReturnPolicyName"))
                    edit.setDefaultReturnPolicyName(defaultReturnPolicyName);
                if(arguments.containsKey("defaultCustomerStatusChoice"))
                    edit.setDefaultCustomerStatusChoice(defaultCustomerStatusChoice);
                if(arguments.containsKey("defaultCustomerCreditStatusChoice"))
                    edit.setDefaultCustomerCreditStatusChoice(defaultCustomerCreditStatusChoice);
                if(arguments.containsKey("defaultArGlAccountName"))
                    edit.setDefaultArGlAccountName(defaultArGlAccountName);
                if(arguments.containsKey("defaultHoldUntilComplete"))
                    edit.setDefaultHoldUntilComplete(defaultHoldUntilComplete);
                if(arguments.containsKey("defaultAllowBackorders"))
                    edit.setDefaultAllowBackorders(defaultAllowBackorders);
                if(arguments.containsKey("defaultAllowSubstitutions"))
                    edit.setDefaultAllowSubstitutions(defaultAllowSubstitutions);
                if(arguments.containsKey("defaultAllowCombiningShipments"))
                    edit.setDefaultAllowCombiningShipments(defaultAllowCombiningShipments);
                if(arguments.containsKey("defaultRequireReference"))
                    edit.setDefaultRequireReference(defaultRequireReference);
                if(arguments.containsKey("defaultAllowReferenceDuplicates"))
                    edit.setDefaultAllowReferenceDuplicates(defaultAllowReferenceDuplicates);
                if(arguments.containsKey("defaultReferenceValidationPattern"))
                    edit.setDefaultReferenceValidationPattern(defaultReferenceValidationPattern);
                if(arguments.containsKey("defaultTaxable"))
                    edit.setDefaultTaxable(defaultTaxable);
                if(arguments.containsKey("allocationPriorityName"))
                    edit.setAllocationPriorityName(allocationPriorityName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CustomerUtil.getHome().editCustomerType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteCustomerType(final DataFetchingEnvironment env,
            @GraphQLName("customerTypeName") final String customerTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CustomerUtil.getHome().getDeleteCustomerTypeForm();

            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(CustomerUtil.getHome().deleteCustomerType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createCustomer(final DataFetchingEnvironment env,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") @GraphQLNonNull final String firstName,
            @GraphQLName("lastName") @GraphQLNonNull final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("initialOfferName") final String initialOfferName,
            @GraphQLName("initialUseName") final String initialUseName,
            @GraphQLName("initialSourceName") final String initialSourceName,
            @GraphQLName("emailAddress") final String emailAddress,
            @GraphQLName("allowSolicitation") @GraphQLNonNull final String allowSolicitation) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PartyUtil.getHome().getCreateCustomerForm();

            commandForm.setPersonalTitleId(personalTitleId);
            commandForm.setFirstName(firstName);
            commandForm.setLastName(lastName);
            commandForm.setNameSuffixId(nameSuffixId);
            commandForm.setName(name);
            commandForm.setInitialOfferName(initialOfferName);
            commandForm.setInitialUseName(initialUseName);
            commandForm.setInitialSourceName(initialSourceName);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setAllowSolicitation(allowSolicitation);

            var commandResult = PartyUtil.getHome().createCustomer(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateCustomerResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createCustomerWithLogin(final DataFetchingEnvironment env,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") @GraphQLNonNull final String firstName,
            @GraphQLName("lastName") @GraphQLNonNull final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("initialOfferName") final String initialOfferName,
            @GraphQLName("initialUseName") final String initialUseName,
            @GraphQLName("initialSourceName") final String initialSourceName,
            @GraphQLName("emailAddress") @GraphQLNonNull final String emailAddress,
            @GraphQLName("allowSolicitation") @GraphQLNonNull final String allowSolicitation,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password1") @GraphQLNonNull final String password1,
            @GraphQLName("password2") @GraphQLNonNull final String password2,
            @GraphQLName("recoveryQuestionName") @GraphQLNonNull final String recoveryQuestionName,
            @GraphQLName("answer") @GraphQLNonNull final String answer) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PartyUtil.getHome().getCreateCustomerWithLoginForm();

            commandForm.setPersonalTitleId(personalTitleId);
            commandForm.setFirstName(firstName);
            commandForm.setLastName(lastName);
            commandForm.setNameSuffixId(nameSuffixId);
            commandForm.setName(name);
            commandForm.setInitialOfferName(initialOfferName);
            commandForm.setInitialUseName(initialUseName);
            commandForm.setInitialSourceName(initialSourceName);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setAllowSolicitation(allowSolicitation);
            commandForm.setUsername(username);
            commandForm.setPassword1(password1);
            commandForm.setPassword2(password2);
            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setAnswer(answer);

            var commandResult = PartyUtil.getHome().createCustomerWithLogin(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateCustomerWithLoginResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject employeeLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getEmployeeLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(BaseGraphQl.getRemoteInet4Address(env));
            commandForm.setCompanyName(companyName);

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().employeeLogin(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject vendorLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getVendorLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(BaseGraphQl.getRemoteInet4Address(env));

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().vendorLogin(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setPassword")
    static MutationResultObject setPassword(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("employeeName") final String employeeName,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("oldPassword") final String oldPassword,
            @GraphQLName("newPassword1") @GraphQLNonNull final String newPassword1,
            @GraphQLName("newPassword2") @GraphQLNonNull final String newPassword2) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getSetPasswordForm();

            commandForm.setPartyName(partyName);
            commandForm.setEmployeeName(employeeName);
            commandForm.setCustomerName(customerName);
            commandForm.setVendorName(vendorName);
            commandForm.setOldPassword(oldPassword);
            commandForm.setNewPassword1(newPassword1);
            commandForm.setNewPassword2(newPassword2);

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().setPassword(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject recoverPassword(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("username") final String username,
            @GraphQLName("answer") final String answer) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getRecoverPasswordForm();

            commandForm.setPartyName(partyName);
            commandForm.setUsername(username);
            commandForm.setAnswer(answer);

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().recoverPassword(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject idle(final DataFetchingEnvironment env) {
        var mutationResultObject = new MutationResultObject();

        try {
            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().idle(BaseGraphQl.getUserVisitPK(env)));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject logout(final DataFetchingEnvironment env) {
        var mutationResultObject = new MutationResultObject();

        try {
            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().logout(BaseGraphQl.getUserVisitPK(env)));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createSecurityRoleGroup(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") @GraphQLNonNull final String securityRoleGroupName,
            @GraphQLName("parentSecurityRoleGroupName") final String parentSecurityRoleGroupName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = SecurityUtil.getHome().getCreateSecurityRoleGroupForm();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setParentSecurityRoleGroupName(parentSecurityRoleGroupName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = SecurityUtil.getHome().createSecurityRoleGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSecurityRoleGroupResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editSecurityRoleGroup(final DataFetchingEnvironment env,
            @GraphQLName("originalSecurityRoleGroupName") final String originalSecurityRoleGroupName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("parentSecurityRoleGroupName") final String parentSecurityRoleGroupName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = SecurityUtil.getHome().getSecurityRoleGroupUniversalSpec();

            spec.setSecurityRoleGroupName(originalSecurityRoleGroupName);
            spec.setUuid(id);

            var commandForm = SecurityUtil.getHome().getEditSecurityRoleGroupForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SecurityUtil.getHome().editSecurityRoleGroup(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSecurityRoleGroupResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getSecurityRoleGroup().getEntityInstance());

                if(arguments.containsKey("securityRoleGroupName"))
                    edit.setSecurityRoleGroupName(securityRoleGroupName);
                if(arguments.containsKey("parentSecurityRoleGroupName"))
                    edit.setParentSecurityRoleGroupName(parentSecurityRoleGroupName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SecurityUtil.getHome().editSecurityRoleGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteSecurityRoleGroup(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SecurityUtil.getHome().getDeleteSecurityRoleGroupForm();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(SecurityUtil.getHome().deleteSecurityRoleGroup(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("securityRoleName") @GraphQLNonNull final String securityRoleName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = SecurityUtil.getHome().getCreateSecurityRoleForm();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setSecurityRoleName(securityRoleName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = SecurityUtil.getHome().createSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSecurityRoleResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("originalSecurityRoleName") final String originalSecurityRoleName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("securityRoleName") final String securityRoleName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = SecurityUtil.getHome().getSecurityRoleUniversalSpec();

            spec.setSecurityRoleGroupName(securityRoleGroupName);
            spec.setSecurityRoleName(originalSecurityRoleName);
            spec.setUuid(id);

            var commandForm = SecurityUtil.getHome().getEditSecurityRoleForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SecurityUtil.getHome().editSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSecurityRoleResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getSecurityRole().getEntityInstance());

                if(arguments.containsKey("securityRoleName"))
                    edit.setSecurityRoleName(securityRoleName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SecurityUtil.getHome().editSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("securityRoleName") final String securityRoleName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SecurityUtil.getHome().getDeleteSecurityRoleForm();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setSecurityRoleName(securityRoleName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(SecurityUtil.getHome().deleteSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWorkflow(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("selectorTypeName") final String selectorTypeName,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = WorkflowUtil.getHome().createWorkflow(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWorkflowResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWorkflow(final DataFetchingEnvironment env,
            @GraphQLName("originalWorkflowName") final String originalWorkflowName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("selectorTypeName") final String selectorTypeName,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WorkflowUtil.getHome().getWorkflowUniversalSpec();

            spec.setWorkflowName(originalWorkflowName);
            spec.setUuid(id);

            var commandForm = WorkflowUtil.getHome().getEditWorkflowForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WorkflowUtil.getHome().editWorkflow(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWorkflowResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWorkflow().getEntityInstance());

                if(arguments.containsKey("workflowName"))
                    edit.setWorkflowName(workflowName);
                if(arguments.containsKey("selectorKindName"))
                    edit.setSelectorKindName(selectorKindName);
                if(arguments.containsKey("selectorTypeName"))
                    edit.setSelectorTypeName(selectorTypeName);
                if(arguments.containsKey("securityRoleGroupName"))
                    edit.setSecurityRoleGroupName(securityRoleGroupName);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WorkflowUtil.getHome().editWorkflow(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflow(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflow(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createWorkflowEntityType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowEntityTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = WorkflowUtil.getHome().createWorkflowEntityType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowEntityType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowEntityTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowEntityType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWorkflowStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowStepTypeName") @GraphQLNonNull final String workflowStepTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowStepTypeName(workflowStepTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = WorkflowUtil.getHome().createWorkflowStep(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWorkflowStepResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWorkflowStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("originalWorkflowStepName") final String originalWorkflowStepName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("workflowStepName") final String workflowStepName,
            @GraphQLName("workflowStepTypeName") final String workflowStepTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WorkflowUtil.getHome().getWorkflowStepUniversalSpec();

            spec.setWorkflowName(workflowName);
            spec.setWorkflowStepName(originalWorkflowStepName);
            spec.setUuid(id);

            var commandForm = WorkflowUtil.getHome().getEditWorkflowStepForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WorkflowUtil.getHome().editWorkflowStep(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWorkflowStepResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWorkflowStep().getEntityInstance());

                if(arguments.containsKey("workflowStepName"))
                    edit.setWorkflowStepName(workflowStepName);
                if(arguments.containsKey("workflowStepTypeName"))
                    edit.setWorkflowStepTypeName(workflowStepTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WorkflowUtil.getHome().editWorkflowStep(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowStepName") final String workflowStepName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowStep(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWorkflowEntrance(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowEntranceForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = WorkflowUtil.getHome().createWorkflowEntrance(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWorkflowEntranceResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWorkflowEntrance(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("originalWorkflowEntranceName") final String originalWorkflowEntranceName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("workflowEntranceName") final String workflowEntranceName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WorkflowUtil.getHome().getWorkflowEntranceUniversalSpec();

            spec.setWorkflowName(workflowName);
            spec.setWorkflowEntranceName(originalWorkflowEntranceName);
            spec.setUuid(id);

            var commandForm = WorkflowUtil.getHome().getEditWorkflowEntranceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WorkflowUtil.getHome().editWorkflowEntrance(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWorkflowEntranceResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWorkflowEntrance().getEntityInstance());

                if(arguments.containsKey("workflowEntranceName"))
                    edit.setWorkflowEntranceName(workflowEntranceName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WorkflowUtil.getHome().editWorkflowEntrance(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowEntrance(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowEntranceName") final String workflowEntranceName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowEntranceForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowEntrance(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createWorkflowEntranceStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("entranceWorkflowName") @GraphQLNonNull final String entranceWorkflowName,
            @GraphQLName("entranceWorkflowStepName") @GraphQLNonNull final String entranceWorkflowStepName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowEntranceStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setEntranceWorkflowName(entranceWorkflowName);
            commandForm.setEntranceWorkflowStepName(entranceWorkflowStepName);

            var commandResult = WorkflowUtil.getHome().createWorkflowEntranceStep(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowEntranceStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("entranceWorkflowName") @GraphQLNonNull final String entranceWorkflowName,
            @GraphQLName("entranceWorkflowStepName") @GraphQLNonNull final String entranceWorkflowStepName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowEntranceStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setEntranceWorkflowName(entranceWorkflowName);
            commandForm.setEntranceWorkflowStepName(entranceWorkflowStepName);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowEntranceStep(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createWorkflowEntrancePartyType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowEntrancePartyTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setPartyTypeName(partyTypeName);

            var commandResult = WorkflowUtil.getHome().createWorkflowEntrancePartyType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowEntrancePartyType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowEntrancePartyTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setPartyTypeName(partyTypeName);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowEntrancePartyType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createWorkflowEntranceSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName,
            @GraphQLName("securityRoleName") @GraphQLNonNull final String securityRoleName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowEntranceSecurityRoleForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setSecurityRoleName(securityRoleName);
            commandForm.setPartyTypeName(partyTypeName);

            var commandResult = WorkflowUtil.getHome().createWorkflowEntranceSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowEntranceSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName,
            @GraphQLName("securityRoleName") @GraphQLNonNull final String securityRoleName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowEntranceSecurityRoleForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setSecurityRoleName(securityRoleName);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowEntranceSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createWorkflowDestination(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowDestinationForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = WorkflowUtil.getHome().createWorkflowDestination(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateWorkflowDestinationResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editWorkflowDestination(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowStepName") final String workflowStepName,
            @GraphQLName("originalWorkflowDestinationName") final String originalWorkflowDestinationName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("workflowDestinationName") final String workflowDestinationName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = WorkflowUtil.getHome().getWorkflowDestinationUniversalSpec();

            spec.setWorkflowName(workflowName);
            spec.setWorkflowStepName(workflowStepName);
            spec.setWorkflowDestinationName(originalWorkflowDestinationName);
            spec.setUuid(id);

            var commandForm = WorkflowUtil.getHome().getEditWorkflowDestinationForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WorkflowUtil.getHome().editWorkflowDestination(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditWorkflowDestinationResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getWorkflowDestination().getEntityInstance());

                if(arguments.containsKey("workflowDestinationName"))
                    edit.setWorkflowDestinationName(workflowDestinationName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = WorkflowUtil.getHome().editWorkflowDestination(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowDestination(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowStepName") final String workflowStepName,
            @GraphQLName("workflowDestinationName") final String workflowDestinationName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setUuid(id);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowDestination(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createWorkflowDestinationStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("destinationWorkflowName") @GraphQLNonNull final String destinationWorkflowName,
            @GraphQLName("destinationWorkflowStepName") @GraphQLNonNull final String destinationWorkflowStepName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowDestinationStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setDestinationWorkflowName(destinationWorkflowName);
            commandForm.setDestinationWorkflowStepName(destinationWorkflowStepName);

            var commandResult = WorkflowUtil.getHome().createWorkflowDestinationStep(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowDestinationStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("destinationWorkflowName") @GraphQLNonNull final String destinationWorkflowName,
            @GraphQLName("destinationWorkflowStepName") @GraphQLNonNull final String destinationWorkflowStepName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setDestinationWorkflowName(destinationWorkflowName);
            commandForm.setDestinationWorkflowStepName(destinationWorkflowStepName);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowDestinationStep(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createWorkflowDestinationPartyType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowDestinationPartyTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setPartyTypeName(partyTypeName);

            var commandResult = WorkflowUtil.getHome().createWorkflowDestinationPartyType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowDestinationPartyType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationPartyTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setPartyTypeName(partyTypeName);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowDestinationPartyType(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject createWorkflowDestinationSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName,
            @GraphQLName("securityRoleName") @GraphQLNonNull final String securityRoleName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getCreateWorkflowDestinationSecurityRoleForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setSecurityRoleName(securityRoleName);
            commandForm.setPartyTypeName(partyTypeName);

            var commandResult = WorkflowUtil.getHome().createWorkflowDestinationSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteWorkflowDestinationSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName,
            @GraphQLName("securityRoleName") @GraphQLNonNull final String securityRoleName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationSecurityRoleForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setSecurityRoleName(securityRoleName);

            mutationResultObject.setCommandResult(WorkflowUtil.getHome().deleteWorkflowDestinationSecurityRole(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static CreateSalesOrderResultObject createSalesOrder(final DataFetchingEnvironment env,
            @GraphQLName("batchName") final String batchName,
            @GraphQLName("sourceName") final String sourceName,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("termName") final String termName,
            @GraphQLName("billToPartyName") final String billToPartyName,
            @GraphQLName("orderPriorityName") final String orderPriorityName,
            @GraphQLName("holdUntilComplete") final String holdUntilComplete,
            @GraphQLName("allowBackorders") final String allowBackorders,
            @GraphQLName("allowSubstitutions") final String allowSubstitutions,
            @GraphQLName("allowCombiningShipments") final String allowCombiningShipments,
            @GraphQLName("reference") final String reference,
            @GraphQLName("freeOnBoardName") final String freeOnBoardName,
            @GraphQLName("taxable") final String taxable,
            @GraphQLName("workflowEntranceName") final String workflowEntranceName) {
        var mutationResultObject = new CreateSalesOrderResultObject();

        try {
            var commandForm = SalesUtil.getHome().getCreateSalesOrderForm();

            commandForm.setBatchName(batchName);
            commandForm.setSourceName(sourceName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setTermName(termName);
            commandForm.setBillToPartyName(billToPartyName);
            commandForm.setOrderPriorityName(orderPriorityName);
            commandForm.setHoldUntilComplete(holdUntilComplete);
            commandForm.setAllowBackorders(allowBackorders);
            commandForm.setAllowSubstitutions(allowSubstitutions);
            commandForm.setAllowCombiningShipments(allowCombiningShipments);
            commandForm.setReference(reference);
            commandForm.setFreeOnBoardName(freeOnBoardName);
            commandForm.setTaxable(taxable);
            commandForm.setWorkflowEntranceName(workflowEntranceName);

            var commandResult = SalesUtil.getHome().createSalesOrder(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                mutationResultObject.setCreateSalesOrderResult((CreateSalesOrderResult)commandResult.getExecutionResult().getResult());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editSalesOrderShipmentGroup(final DataFetchingEnvironment env,
            @GraphQLName("orderName") final String orderName,
            @GraphQLName("orderShipmentGroupSequence") final String orderShipmentGroupSequence,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("contactMechanismName") final String contactMechanismName,
            @GraphQLName("shippingMethodName") final String shippingMethodName,
            @GraphQLName("holdUntilComplete") final String holdUntilComplete) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = SalesUtil.getHome().getSalesOrderShipmentGroupSpec();

            spec.setOrderName(orderName);
            spec.setOrderShipmentGroupSequence(orderShipmentGroupSequence);

            var commandForm = SalesUtil.getHome().getEditSalesOrderShipmentGroupForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SalesUtil.getHome().editSalesOrderShipmentGroup(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSalesOrderShipmentGroupResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOrderShipmentGroup().getEntityInstance());

                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("partyName"))
                    edit.setPartyName(partyName);
                if(arguments.containsKey("contactMechanismName"))
                    edit.setContactMechanismName(contactMechanismName);
                if(arguments.containsKey("shippingMethodName"))
                    edit.setShippingMethodName(shippingMethodName);
                if(arguments.containsKey("holdUntilComplete"))
                    edit.setHoldUntilComplete(holdUntilComplete);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SalesUtil.getHome().editSalesOrderShipmentGroup(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static CreateSalesOrderLineResultObject createSalesOrderLine(final DataFetchingEnvironment env,
            @GraphQLName("orderName") final String orderName,
            @GraphQLName("orderLineSequence") final String orderLineSequence,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("quantity") @GraphQLNonNull final String quantity,
            @GraphQLName("unitAmount") final String unitAmount,
            @GraphQLName("taxable") final String taxable,
            @GraphQLName("description") final String description,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName,
            @GraphQLName("sourceName") final String sourceName) {
        var mutationResultObject = new CreateSalesOrderLineResultObject();

        try {
            var commandForm = SalesUtil.getHome().getCreateSalesOrderLineForm();

            commandForm.setOrderName(orderName);
            commandForm.setOrderLineSequence(orderLineSequence);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setQuantity(quantity);
            commandForm.setUnitAmount(unitAmount);
            commandForm.setTaxable(taxable);
            commandForm.setDescription(description);
            commandForm.setCancellationPolicyName(cancellationPolicyName);
            commandForm.setReturnPolicyName(returnPolicyName);
            commandForm.setSourceName(sourceName);

            var commandResult = SalesUtil.getHome().createSalesOrderLine(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                mutationResultObject.setCreateSalesOrderLineResult((CreateSalesOrderLineResult)commandResult.getExecutionResult().getResult());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createFiscalYear(final DataFetchingEnvironment env,
            @GraphQLName("year") @GraphQLNonNull final String year) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = PeriodUtil.getHome().getCreateFiscalYearForm();

            commandForm.setYear(year);

            var commandResult = PeriodUtil.getHome().createFiscalYear(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFiscalYearResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setFiscalYearStatus")
    static MutationResultObject setFiscalYearStatus(final DataFetchingEnvironment env,
            @GraphQLName("periodName") @GraphQLNonNull final String periodName,
            @GraphQLName("fiscalPeriodStatusChoice") @GraphQLNonNull final String fiscalPeriodStatusChoice) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PeriodUtil.getHome().getSetFiscalPeriodStatusForm();

            commandForm.setPeriodName(periodName);
            commandForm.setFiscalPeriodStatusChoice(fiscalPeriodStatusChoice);

            mutationResultObject.setCommandResult(PeriodUtil.getHome().setFiscalPeriodStatus(BaseGraphQl.getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject createTransactionTimeType(final DataFetchingEnvironment env,
            @GraphQLName("transactionTimeTypeName") @GraphQLNonNull final String transactionTimeTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = AccountingUtil.getHome().getCreateTransactionTimeTypeForm();

            commandForm.setTransactionTimeTypeName(transactionTimeTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = AccountingUtil.getHome().createTransactionTimeType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateTransactionTimeTypeResult)commandResult.getExecutionResult().getResult();

                mutationResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultObject deleteTransactionTimeType(final DataFetchingEnvironment env,
            @GraphQLName("transactionTimeTypeName") @GraphQLNonNull final String transactionTimeTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AccountingUtil.getHome().getDeleteTransactionTimeTypeForm();

            commandForm.setTransactionTimeTypeName(transactionTimeTypeName);

            var commandResult = AccountingUtil.getHome().deleteTransactionTimeType(BaseGraphQl.getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    static MutationResultWithIdObject editTransactionTimeType(final DataFetchingEnvironment env,
            @GraphQLName("originalTransactionTimeTypeName") final String originalTransactionTimeTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("transactionTimeTypeName") final String transactionTimeTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var spec = AccountingUtil.getHome().getTransactionTimeTypeUniversalSpec();

            spec.setTransactionTimeTypeName(originalTransactionTimeTypeName);
            spec.setUuid(id);

            var commandForm = AccountingUtil.getHome().getEditTransactionTimeTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = AccountingUtil.getHome().editTransactionTimeType(BaseGraphQl.getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditTransactionTimeTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getTransactionTimeType().getEntityInstance());

                if(arguments.containsKey("transactionTimeTypeName"))
                    edit.setTransactionTimeTypeName(transactionTimeTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = AccountingUtil.getHome().editTransactionTimeType(BaseGraphQl.getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }
    
}
