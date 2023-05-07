// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.CreateGlAccountResult;
import com.echothree.control.user.accounting.common.result.CreateItemAccountingCategoryResult;
import com.echothree.control.user.accounting.common.result.EditGlAccountResult;
import com.echothree.control.user.accounting.common.result.EditItemAccountingCategoryResult;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.campaign.common.CampaignUtil;
import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.CreateContentCatalogResult;
import com.echothree.control.user.content.common.result.CreateContentCategoryResult;
import com.echothree.control.user.content.common.result.CreateContentCollectionResult;
import com.echothree.control.user.content.common.result.CreateContentPageLayoutResult;
import com.echothree.control.user.content.common.result.EditContentCatalogResult;
import com.echothree.control.user.content.common.result.EditContentCategoryItemResult;
import com.echothree.control.user.content.common.result.EditContentCategoryResult;
import com.echothree.control.user.content.common.result.EditContentCollectionResult;
import com.echothree.control.user.content.common.result.EditContentPageLayoutResult;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.CreateEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.CreateEntityAttributeResult;
import com.echothree.control.user.core.common.result.CreateEntityListItemResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityBooleanAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityClobAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityDateAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityEntityAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityGeoPointAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityIntegerAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityListItemAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityListItemResult;
import com.echothree.control.user.core.common.result.EditEntityLongAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityNameAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityStringAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityTimeAttributeResult;
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
import com.echothree.control.user.inventory.common.result.CreateInventoryConditionResult;
import com.echothree.control.user.inventory.common.result.EditAllocationPriorityResult;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemAliasTypeResult;
import com.echothree.control.user.item.common.result.CreateItemCategoryResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionTypeResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.result.CreateItemImageTypeResult;
import com.echothree.control.user.item.common.result.CreateItemResult;
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
import com.echothree.control.user.party.common.result.CreateVendorResult;
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
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.CreateSearchResultActionTypeResult;
import com.echothree.control.user.search.common.result.EditSearchResultActionTypeResult;
import com.echothree.control.user.search.common.result.SearchCustomersResult;
import com.echothree.control.user.search.common.result.SearchEmployeesResult;
import com.echothree.control.user.search.common.result.SearchItemsResult;
import com.echothree.control.user.search.common.result.SearchVendorsResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.CreateSelectorResult;
import com.echothree.control.user.selector.common.result.EditSelectorResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.CreateSequenceResult;
import com.echothree.control.user.sequence.common.result.CreateSequenceTypeResult;
import com.echothree.control.user.sequence.common.result.EditSequenceResult;
import com.echothree.control.user.sequence.common.result.EditSequenceTypeResult;
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
import com.echothree.control.user.wishlist.common.WishlistUtil;
import com.echothree.control.user.wishlist.common.result.CreateWishlistPriorityResult;
import com.echothree.control.user.wishlist.common.result.CreateWishlistTypeResult;
import com.echothree.control.user.wishlist.common.result.EditWishlistPriorityResult;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypeResult;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.search.server.graphql.SearchCustomersResultObject;
import com.echothree.model.control.search.server.graphql.SearchEmployeesResultObject;
import com.echothree.model.control.search.server.graphql.SearchItemsResultObject;
import com.echothree.model.control.search.server.graphql.SearchVendorsResultObject;
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
public class GraphQlMutations
        extends BaseGraphQl {

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createContentCollection(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().createContentCollection(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteContentCollection(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentCollectionForm();

            commandForm.setContentCollectionName(contentCollectionName);

            var commandResult = ContentUtil.getHome().deleteContentCollection(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editContentCollection(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().editContentCollection(getUserVisitPK(env), commandForm);

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

                commandResult = ContentUtil.getHome().editContentCollection(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createContentCatalog(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().createContentCatalog(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteContentCatalog(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentCatalogForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);

            var commandResult = ContentUtil.getHome().deleteContentCatalog(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editContentCatalog(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().editContentCatalog(getUserVisitPK(env), commandForm);

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

                commandResult = ContentUtil.getHome().editContentCatalog(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createContentCategory(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().createContentCategory(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteContentCategory(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentCatalogName") @GraphQLNonNull final String contentCatalogName,
            @GraphQLName("contentCategoryName") @GraphQLNonNull final String contentCategoryName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentCategoryForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);

            var commandResult = ContentUtil.getHome().deleteContentCategory(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editContentCategory(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().editContentCategory(getUserVisitPK(env), commandForm);

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

                commandResult = ContentUtil.getHome().editContentCategory(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createContentCategoryItem(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().createContentCategoryItem(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteContentCategoryItem(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().deleteContentCategoryItem(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editContentCategoryItem(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().editContentCategoryItem(getUserVisitPK(env), commandForm);

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

                commandResult = ContentUtil.getHome().editContentCategoryItem(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createUserVisitTrack(final DataFetchingEnvironment env,
            @GraphQLName("trackValue") final String trackValue) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TrackUtil.getHome().getCreateUserVisitTrackForm();

            commandForm.setTrackValue(trackValue);

            var commandResult = TrackUtil.getHome().createUserVisitTrack(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createUserVisitCampaign(final DataFetchingEnvironment env,
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

            var commandResult = CampaignUtil.getHome().createUserVisitCampaign(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createSelector(final DataFetchingEnvironment env,
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

            var commandResult = SelectorUtil.getHome().createSelector(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SelectorUtil.getHome().getDeleteSelectorForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorName(selectorName);

            var commandResult = SelectorUtil.getHome().deleteSelector(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editSelector(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = SelectorUtil.getHome().getEditSelectorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SelectorUtil.getHome().editSelector(getUserVisitPK(env), commandForm);

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

                commandResult = SelectorUtil.getHome().editSelector(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createFilter(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().createFilter(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterName(filterName);

            var commandResult = FilterUtil.getHome().deleteFilter(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editFilter(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = FilterUtil.getHome().getEditFilterForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilter(getUserVisitPK(env), commandForm);

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

                commandResult = FilterUtil.getHome().editFilter(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createFilterStep(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().createFilterStep(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteFilterStep(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().deleteFilterStep(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editFilterStep(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = FilterUtil.getHome().getEditFilterStepForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterStep(getUserVisitPK(env), commandForm);

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

                commandResult = FilterUtil.getHome().editFilterStep(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createFilterAdjustment(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().createFilterAdjustment(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustment(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editFilterAdjustment(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustment(getUserVisitPK(env), commandForm);

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

                commandResult = FilterUtil.getHome().editFilterAdjustment(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createFilterAdjustmentAmount(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().createFilterAdjustmentAmount(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteFilterAdjustmentAmount(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentAmount(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editFilterAdjustmentAmount(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentAmountResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("amount"))
                    edit.setAmount(amount);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().createFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().editFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentFixedAmountResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitAmount"))
                    edit.setUnitAmount(unitAmount);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createFilterAdjustmentPercent(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().createFilterAdjustmentPercent(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteFilterAdjustmentPercent(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentPercent(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editFilterAdjustmentPercent(final DataFetchingEnvironment env,
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

            var commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentPercentResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("percent"))
                    edit.setPercent(percent);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("value") @GraphQLNonNull final String value,
            @GraphQLName("description") final String description) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = SequenceUtil.getHome().getCreateSequenceForm();

            commandForm.setSequenceName(sequenceName);
            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setChunkSize(chunkSize);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setValue(value);
            commandForm.setDescription(description);

            var commandResult = SequenceUtil.getHome().createSequence(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getDeleteSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);

            var commandResult = SequenceUtil.getHome().deleteSequence(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editSequence(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = SequenceUtil.getHome().getEditSequenceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequence(getUserVisitPK(env), commandForm);

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

                commandResult = SequenceUtil.getHome().editSequence(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject setSequenceValue(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName,
            @GraphQLName("value") @GraphQLNonNull final String value) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getSetSequenceValueForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);
            commandForm.setValue(value);

            var commandResult = SequenceUtil.getHome().setSequenceValue(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createSequenceType(final DataFetchingEnvironment env,
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

            var commandResult = SequenceUtil.getHome().createSequenceType(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getDeleteSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);

            var commandResult = SequenceUtil.getHome().deleteSequenceType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editSequenceType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = SequenceUtil.getHome().getEditSequenceTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequenceType(getUserVisitPK(env), commandForm);

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

                commandResult = SequenceUtil.getHome().editSequenceType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);
            commandForm.setSalesOrderSequenceName(salesOrderSequenceName);

            var commandResult = OfferUtil.getHome().createOfferUse(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            var commandResult = OfferUtil.getHome().deleteOfferUse(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editOfferUse(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().editOfferUse(getUserVisitPK(env), commandForm);

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

                commandResult = OfferUtil.getHome().editOfferUse(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createOffer(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().createOffer(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteOffer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferForm();

            commandForm.setOfferName(offerName);

            var commandResult = OfferUtil.getHome().deleteOffer(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editOffer(final DataFetchingEnvironment env,
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
            //spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditOfferForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOffer(getUserVisitPK(env), commandForm);

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

                commandResult = OfferUtil.getHome().editOffer(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createOfferItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            var commandResult = OfferUtil.getHome().createOfferItem(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteOfferItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            var commandResult = OfferUtil.getHome().deleteOfferItem(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createOfferItemPrice(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().createOfferItemPrice(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteOfferItemPrice(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().deleteOfferItemPrice(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editOfferItemPrice(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().editOfferItemPrice(getUserVisitPK(env), commandForm);

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

                commandResult = OfferUtil.getHome().editOfferItemPrice(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createUse(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().createUse(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteUse(final DataFetchingEnvironment env,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseForm();

            commandForm.setUseName(useName);

            var commandResult = OfferUtil.getHome().deleteUse(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editUse(final DataFetchingEnvironment env,
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
            //spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditUseForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUse(getUserVisitPK(env), commandForm);

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

                commandResult = OfferUtil.getHome().editUse(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createOfferNameElement(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().createOfferNameElement(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteOfferNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") @GraphQLNonNull final String offerNameElementName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);

            var commandResult = OfferUtil.getHome().deleteOfferNameElement(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editOfferNameElement(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditOfferNameElementForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOfferNameElement(getUserVisitPK(env), commandForm);

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

                commandResult = OfferUtil.getHome().editOfferNameElement(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createUseNameElement(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().createUseNameElement(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteUseNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") @GraphQLNonNull final String useNameElementName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);

            var commandResult = OfferUtil.getHome().deleteUseNameElement(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editUseNameElement(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditUseNameElementForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUseNameElement(getUserVisitPK(env), commandForm);

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

                commandResult = OfferUtil.getHome().editUseNameElement(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createUseType(final DataFetchingEnvironment env,
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

            var commandResult = OfferUtil.getHome().createUseType(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteUseType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseTypeForm();

            commandForm.setUseTypeName(useTypeName);

            var commandResult = OfferUtil.getHome().deleteUseType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editUseType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditUseTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUseType(getUserVisitPK(env), commandForm);

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

                commandResult = OfferUtil.getHome().editUseType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createFreeOnBoard(final DataFetchingEnvironment env,
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

            var commandResult = ShipmentUtil.getHome().createFreeOnBoard(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") @GraphQLNonNull final String freeOnBoardName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ShipmentUtil.getHome().getDeleteFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);

            var commandResult = ShipmentUtil.getHome().deleteFreeOnBoard(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editFreeOnBoard(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = ShipmentUtil.getHome().getEditFreeOnBoardForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ShipmentUtil.getHome().editFreeOnBoard(getUserVisitPK(env), commandForm);

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

                commandResult = ShipmentUtil.getHome().editFreeOnBoard(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createPaymentProcessor(final DataFetchingEnvironment env,
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

            var commandResult = PaymentUtil.getHome().createPaymentProcessor(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deletePaymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") @GraphQLNonNull final String paymentProcessorName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessor(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editPaymentProcessor(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessor(getUserVisitPK(env), commandForm);

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

                commandResult = PaymentUtil.getHome().editPaymentProcessor(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createPaymentProcessorType(final DataFetchingEnvironment env,
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

            var commandResult = PaymentUtil.getHome().createPaymentProcessorType(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deletePaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editPaymentProcessorType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorType(getUserVisitPK(env), commandForm);

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

                commandResult = PaymentUtil.getHome().editPaymentProcessorType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createPaymentMethodType(final DataFetchingEnvironment env,
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

            var commandResult = PaymentUtil.getHome().createPaymentMethodType(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deletePaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentMethodType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editPaymentMethodType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentMethodTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentMethodType(getUserVisitPK(env), commandForm);

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

                commandResult = PaymentUtil.getHome().editPaymentMethodType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createPaymentProcessorResultCode(final DataFetchingEnvironment env,
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

            var commandResult = PaymentUtil.getHome().createPaymentProcessorResultCode(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deletePaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorResultCode(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editPaymentProcessorResultCode(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorResultCodeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(getUserVisitPK(env), commandForm);

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

                commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createPaymentProcessorActionType(final DataFetchingEnvironment env,
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

            var commandResult = PaymentUtil.getHome().createPaymentProcessorActionType(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deletePaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorActionType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editPaymentProcessorActionType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorActionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(getUserVisitPK(env), commandForm);

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

                commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createAllocationPriority(final DataFetchingEnvironment env,
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

            var commandResult = InventoryUtil.getHome().createAllocationPriority(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteAllocationPriority(final DataFetchingEnvironment env,
            @GraphQLName("allocationPriorityName") @GraphQLNonNull final String allocationPriorityName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getDeleteAllocationPriorityForm();

            commandForm.setAllocationPriorityName(allocationPriorityName);

            var commandResult = InventoryUtil.getHome().deleteAllocationPriority(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editAllocationPriority(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = InventoryUtil.getHome().getEditAllocationPriorityForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editAllocationPriority(getUserVisitPK(env), commandForm);

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

                commandResult = InventoryUtil.getHome().editAllocationPriority(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createInventoryCondition(final DataFetchingEnvironment env,
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

            var commandResult = InventoryUtil.getHome().createInventoryCondition(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getDeleteInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);

            var commandResult = InventoryUtil.getHome().deleteInventoryCondition(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editInventoryCondition(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = InventoryUtil.getHome().getEditInventoryConditionForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editInventoryCondition(getUserVisitPK(env), commandForm);

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

                commandResult = InventoryUtil.getHome().editInventoryCondition(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createContentPageLayout(final DataFetchingEnvironment env,
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

            var commandResult = ContentUtil.getHome().createContentPageLayout(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);

            var commandResult = ContentUtil.getHome().deleteContentPageLayout(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editContentPageLayout(final DataFetchingEnvironment env,
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
            spec.setUlid(id);
            
            var commandForm = ContentUtil.getHome().getEditContentPageLayoutForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentPageLayout(getUserVisitPK(env), commandForm);
            
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
                
                commandResult = ContentUtil.getHome().editContentPageLayout(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject setUserVisitPreferredLanguage(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredLanguage(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredCurrency")
    public static MutationResultObject setUserVisitPreferredCurrency(final DataFetchingEnvironment env,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredCurrency(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredTimeZone")
    public static MutationResultObject setUserVisitPreferredTimeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") @GraphQLNonNull final String javaTimeZoneName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredTimeZone(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredDateTimeFormat")
    public static MutationResultObject setUserVisitPreferredDateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") @GraphQLNonNull final String dateTimeFormatName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredDateTimeFormat(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createTagScope(final DataFetchingEnvironment env,
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

            var commandResult = TagUtil.getHome().createTagScope(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteTagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagScopeForm();

            commandForm.setTagScopeName(tagScopeName);

            var commandResult = TagUtil.getHome().deleteTagScope(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editTagScope(final DataFetchingEnvironment env,
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

            var commandResult = TagUtil.getHome().editTagScope(getUserVisitPK(env), commandForm);

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

                commandResult = TagUtil.getHome().editTagScope(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createTagScopeEntityType(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = TagUtil.getHome().createTagScopeEntityType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteTagScopeEntityType(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = TagUtil.getHome().deleteTagScopeEntityType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().createTag(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().deleteTag(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editTag(final DataFetchingEnvironment env,
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

            var commandResult = TagUtil.getHome().editTag(getUserVisitPK(env), commandForm);

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

                commandResult = TagUtil.getHome().editTag(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityTag(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getCreateEntityTagForm();

            commandForm.setUlid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().createEntityTag(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityTag(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteEntityTagForm();

            commandForm.setUlid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().deleteEntityTag(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createEntityAttributeGroup(final DataFetchingEnvironment env,
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

            var commandResult = CoreUtil.getHome().createEntityAttributeGroup(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject deleteEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);

            var commandResult = CoreUtil.getHome().deleteEntityAttributeGroup(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject editEntityAttributeGroup(final DataFetchingEnvironment env,
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

            var commandResult = CoreUtil.getHome().editEntityAttributeGroup(getUserVisitPK(env), commandForm);

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

                commandResult = CoreUtil.getHome().editEntityAttributeGroup(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createEntityAttribute(final DataFetchingEnvironment env,
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
            @GraphQLName("entityListItemSequenceName") final String entityListItemSequenceName) {
        var mutationResultObject = new MutationResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAttributeForm();

            commandForm.setUlid(id);
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

            var commandResult = CoreUtil.getHome().createEntityAttribute(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject editEntityAttribute(final DataFetchingEnvironment env,
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

            spec.setUlid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(originalEntityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAttribute(getUserVisitPK(env), commandForm);

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

                commandResult = CoreUtil.getHome().editEntityAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAttributeForm();

            commandForm.setUlid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            var commandResult = CoreUtil.getHome().deleteEntityAttribute(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityAttributeEntityAttributeGroup(final DataFetchingEnvironment env,
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

            var commandResult = CoreUtil.getHome().createEntityAttributeEntityAttributeGroup(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityAttributeEntityAttributeGroup(final DataFetchingEnvironment env,
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

            var commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAttributeEntityAttributeGroupResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityAttributeEntityAttributeGroup(final DataFetchingEnvironment env,
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

            var commandResult = CoreUtil.getHome().deleteEntityAttributeEntityAttributeGroup(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createEntityListItem(final DataFetchingEnvironment env,
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

            commandForm.setUlid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createEntityListItem(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject editEntityListItem(final DataFetchingEnvironment env,
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

            spec.setUlid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setEntityListItemName(originalEntityListItemName);

            var commandForm = CoreUtil.getHome().getEditEntityListItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityListItem(getUserVisitPK(env), commandForm);

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

                commandResult = CoreUtil.getHome().editEntityListItem(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityListItem(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityListItemForm();

            commandForm.setUlid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);

            var commandResult = CoreUtil.getHome().deleteEntityListItem(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUlid(entityListItemId);

            var commandResult = CoreUtil.getHome().createEntityListItemAttribute(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityListItemAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityListItemAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityListItemAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityListItemAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityListItemName"))
                    edit.setEntityListItemName(entityListItemName);
                if(arguments.containsKey("entityListItemId"))
                    edit.setEntityListItemUlid(entityListItemId);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityListItemAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            var commandResult = CoreUtil.getHome().deleteEntityListItemAttribute(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityMultipleListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityMultipleListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUlid(entityListItemId);

            var commandResult = CoreUtil.getHome().createEntityMultipleListItemAttribute(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityMultipleListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityMultipleListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUlid(entityListItemId);

            var commandResult = CoreUtil.getHome().deleteEntityMultipleListItemAttribute(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityBooleanAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setBooleanAttribute(booleanAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityBooleanAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityBooleanAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityBooleanAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityBooleanAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityBooleanAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("booleanAttribute"))
                    edit.setBooleanAttribute(booleanAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityBooleanAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityBooleanAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityBooleanAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityIntegerAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setIntegerAttribute(integerAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityIntegerAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityIntegerAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityIntegerAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityIntegerAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityIntegerAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("integerAttribute"))
                    edit.setIntegerAttribute(integerAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityIntegerAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityIntegerAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityIntegerAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityLongAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLongAttribute(longAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityLongAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityLongAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityLongAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityLongAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityLongAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("longAttribute"))
                    edit.setLongAttribute(longAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityLongAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityLongAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityLongAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityStringAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);
            commandForm.setStringAttribute(stringAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityStringAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityStringAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);
            spec.setLanguageUlid(languageId);

            var commandForm = CoreUtil.getHome().getEditEntityStringAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityStringAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityStringAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("stringAttribute"))
                    edit.setStringAttribute(stringAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityStringAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull final String languageId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityStringAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityStringAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("clobAttribute") @GraphQLNonNull final String clobAttribute,
            @GraphQLName("mimeTypeName") @GraphQLNonNull final String mimeTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityClobAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);
            commandForm.setClobAttribute(clobAttribute);
            commandForm.setMimeTypeName(mimeTypeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityClobAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("clobAttribute") final String clobAttribute,
            @GraphQLName("mimeTypeName") final String mimeTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityClobAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);
            spec.setLanguageUlid(languageId);

            var commandForm = CoreUtil.getHome().getEditEntityClobAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityClobAttribute(getUserVisitPK(env), commandForm);

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

                commandResult = CoreUtil.getHome().editEntityClobAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull final String languageId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityClobAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityClobAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("nameAttribute") @GraphQLNonNull final String nameAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityNameAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setNameAttribute(nameAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityNameAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("nameAttribute") @GraphQLNonNull final String nameAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityNameAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityNameAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityNameAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityNameAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("nameAttribute"))
                    edit.setNameAttribute(nameAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityNameAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityNameAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityNameAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityDateAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setDateAttribute(dateAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityDateAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityDateAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityDateAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityDateAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityDateAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("dateAttribute"))
                    edit.setDateAttribute(dateAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityDateAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityDateAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityDateAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityTimeAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setTimeAttribute(timeAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityTimeAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityTimeAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityTimeAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityTimeAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityTimeAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("timeAttribute"))
                    edit.setTimeAttribute(timeAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityTimeAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityTimeAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityTimeAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityGeoPointAttribute(final DataFetchingEnvironment env,
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

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLatitude(latitude);
            commandForm.setLongitude(longitude);
            commandForm.setElevation(elevation);
            commandForm.setElevationUnitOfMeasureTypeName(elevationUnitOfMeasureTypeName);
            commandForm.setAltitude(altitude);
            commandForm.setAltitudeUnitOfMeasureTypeName(altitudeUnitOfMeasureTypeName);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityGeoPointAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityGeoPointAttribute(final DataFetchingEnvironment env,
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

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityGeoPointAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityGeoPointAttribute(getUserVisitPK(env), commandForm);

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

                commandResult = CoreUtil.getHome().editEntityGeoPointAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityGeoPointAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityGeoPointAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityGeoPointAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityEntityAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityRefAttribute(entityRefAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityEntityAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityEntityAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityEntityAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityEntityAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityEntityAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityRefAttribute"))
                    edit.setEntityRefAttribute(entityRefAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityEntityAttribute(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityEntityAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityEntityAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createEntityCollectionAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityCollectionAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityRefAttribute(entityRefAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().createEntityCollectionAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteEntityCollectionAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityCollectionAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityRefAttribute(entityRefAttribute);

            mutationResultObject.setCommandResult(CoreUtil.getHome().deleteEntityCollectionAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createSearchResultActionType(final DataFetchingEnvironment env,
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

            var commandResult = SearchUtil.getHome().createSearchResultActionType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editSearchResultActionType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = SearchUtil.getHome().getEditSearchResultActionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SearchUtil.getHome().editSearchResultActionType(getUserVisitPK(env), commandForm);

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

                commandResult = SearchUtil.getHome().editSearchResultActionType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteSearchResultActionType(final DataFetchingEnvironment env,
            @GraphQLName("searchResultActionTypeName") final String searchResultActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getDeleteSearchResultActionTypeForm();

            commandForm.setSearchResultActionTypeName(searchResultActionTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(SearchUtil.getHome().deleteSearchResultActionType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchCustomersResultObject searchCustomers(final DataFetchingEnvironment env,
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

            var commandResult = SearchUtil.getHome().searchCustomers(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchCustomersResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject clearCustomerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearCustomerResults(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchEmployeesResultObject searchEmployees(final DataFetchingEnvironment env,
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


            var commandResult = SearchUtil.getHome().searchEmployees(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchEmployeesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject clearEmployeeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEmployeeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEmployeeResults(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchItemsResultObject searchItems(final DataFetchingEnvironment env,
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

            var commandResult = SearchUtil.getHome().searchItems(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchItemsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createItemSearchResultAction(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchResultActionTypeName") @GraphQLNonNull final String searchResultActionTypeName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getCreateItemSearchResultActionForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchResultActionTypeName(searchResultActionTypeName);
            commandForm.setItemName(itemName);

            var commandResult = SearchUtil.getHome().createItemSearchResultAction(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject clearItemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearItemResults(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchVendorsResultObject searchVendors(final DataFetchingEnvironment env,
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

            var commandResult = SearchUtil.getHome().searchVendors(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
            mutationResultObject.setResult(commandResult.hasErrors() ? null : (SearchVendorsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject clearVendorResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearVendorResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearVendorResults(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createUserLogin(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);
            commandForm.setUsername(username);
            commandForm.setPassword1(password1);
            commandForm.setPassword2(password2);
            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setAnswer(answer);

            var commandResult = UserUtil.getHome().createUserLogin(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") @GraphQLNonNull final String username) {
        var mutationResultObject = new MutationResultObject();

        try {
            var spec = PartyUtil.getHome().getPartyUniversalSpec();

            spec.setPartyName(partyName);
            spec.setUlid(id);
            
            var commandForm = UserUtil.getHome().getEditUserLoginForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = UserUtil.getHome().editUserLogin(getUserVisitPK(env), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUserLoginResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                if(arguments.containsKey("username"))
                    edit.setUsername(username);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = UserUtil.getHome().editUserLogin(getUserVisitPK(env), commandForm);
            }
            
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = UserUtil.getHome().getDeleteUserLoginForm();

            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(UserUtil.getHome().deleteUserLogin(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemCategory(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemCategory(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemCategory(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemCategory(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemCategory(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemCategory(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createGlAccount(final DataFetchingEnvironment env,
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

            var commandResult = AccountingUtil.getHome().createGlAccount(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editGlAccount(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = AccountingUtil.getHome().getEditGlAccountForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = AccountingUtil.getHome().editGlAccount(getUserVisitPK(env), commandForm);

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

                commandResult = AccountingUtil.getHome().editGlAccount(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteGlAccount(final DataFetchingEnvironment env,
            @GraphQLName("glAccountName") final String glAccountName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AccountingUtil.getHome().getDeleteGlAccountForm();

            commandForm.setGlAccountName(glAccountName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(AccountingUtil.getHome().deleteGlAccount(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemAccountingCategory(final DataFetchingEnvironment env,
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

            var commandResult = AccountingUtil.getHome().createItemAccountingCategory(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemAccountingCategory(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = AccountingUtil.getHome().getEditItemAccountingCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = AccountingUtil.getHome().editItemAccountingCategory(getUserVisitPK(env), commandForm);

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

                commandResult = AccountingUtil.getHome().editItemAccountingCategory(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AccountingUtil.getHome().getDeleteItemAccountingCategoryForm();

            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(AccountingUtil.getHome().deleteItemAccountingCategory(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemPurchasingCategory(final DataFetchingEnvironment env,
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

            var commandResult = VendorUtil.getHome().createItemPurchasingCategory(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemPurchasingCategory(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = VendorUtil.getHome().getEditItemPurchasingCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editItemPurchasingCategory(getUserVisitPK(env), commandForm);

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

                commandResult = VendorUtil.getHome().editItemPurchasingCategory(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getDeleteItemPurchasingCategoryForm();

            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteItemPurchasingCategory(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createVendorType(final DataFetchingEnvironment env,
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

            var commandResult = VendorUtil.getHome().createVendorType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editVendorType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = VendorUtil.getHome().getEditVendorTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editVendorType(getUserVisitPK(env), commandForm);

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

                commandResult = VendorUtil.getHome().editVendorType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteVendorType(final DataFetchingEnvironment env,
            @GraphQLName("vendorTypeName") final String vendorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = VendorUtil.getHome().getDeleteVendorTypeForm();

            commandForm.setVendorTypeName(vendorTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteVendorType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createVendor(final DataFetchingEnvironment env,
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

            var commandResult = PartyUtil.getHome().createVendor(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editVendor(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = VendorUtil.getHome().getEditVendorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editVendor(getUserVisitPK(env), commandForm);

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

                commandResult = VendorUtil.getHome().editVendor(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject setVendorStatus(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("vendorStatusChoice") @GraphQLNonNull final String vendorStatusChoice) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = PartyUtil.getHome().getSetVendorStatusForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);
            commandForm.setVendorStatusChoice(vendorStatusChoice);

            mutationResultObject.setCommandResult(PartyUtil.getHome().setVendorStatus(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createVendorItem(final DataFetchingEnvironment env,
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

            var commandResult = VendorUtil.getHome().createVendorItem(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editVendorItem(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = VendorUtil.getHome().getEditVendorItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editVendorItem(getUserVisitPK(env), commandForm);

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

                commandResult = VendorUtil.getHome().editVendorItem(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject setVendorItemStatus(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);
            commandForm.setVendorItemStatusChoice(vendorItemStatusChoice);

            mutationResultObject.setCommandResult(VendorUtil.getHome().setVendorItemStatus(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteVendorItem(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteVendorItem(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createVendorItemCost(final DataFetchingEnvironment env,
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

            var commandResult = VendorUtil.getHome().createVendorItemCost(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editVendorItemCost(final DataFetchingEnvironment env,
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

            var commandResult = VendorUtil.getHome().editVendorItemCost(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditVendorItemCostResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitCost"))
                    edit.setUnitCost(unitCost);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = VendorUtil.getHome().editVendorItemCost(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteVendorItemCost(final DataFetchingEnvironment env,
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

            mutationResultObject.setCommandResult(VendorUtil.getHome().deleteVendorItemCost(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemImageType(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemImageType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemImageType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemImageTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemImageType(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemImageType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemImageType(final DataFetchingEnvironment env,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemImageTypeForm();

            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemImageType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemDescriptionTypeUseType(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemDescriptionTypeUseType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemDescriptionTypeUseType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeUseTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeUseTypeForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionTypeUseType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createItemDescriptionTypeUse(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeUseForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            var commandResult = ItemUtil.getHome().createItemDescriptionTypeUse(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemDescriptionTypeUse(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeUseForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionTypeUse(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemDescriptionType(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemDescriptionType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemDescriptionType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescriptionType(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemDescriptionType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemAliasType(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemAliasType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemAliasType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemAliasTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemAliasType(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemAliasType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemAliasTypeForm();

            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemAliasType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createItemAlias(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemAlias(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editItemAlias(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().editItemAlias(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemAlias(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemAlias(final DataFetchingEnvironment env,
            @GraphQLName("alias") @GraphQLID final String alias) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemAliasForm();

            commandForm.setAlias(alias);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemAlias(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createRelatedItemType(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createRelatedItemType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editRelatedItemType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditRelatedItemTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editRelatedItemType(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editRelatedItemType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteRelatedItemType(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") final String relatedItemTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteRelatedItemTypeForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteRelatedItemType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createRelatedItem(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createRelatedItem(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editRelatedItem(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().editRelatedItem(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editRelatedItem(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteRelatedItem(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") @GraphQLNonNull final String relatedItemTypeName,
            @GraphQLName("fromItemName") @GraphQLNonNull final String fromItemName,
            @GraphQLName("toItemName") @GraphQLNonNull final String toItemName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteRelatedItemForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setFromItemName(fromItemName);
            commandForm.setToItemName(toItemName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteRelatedItem(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItem(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItem(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItem(final DataFetchingEnvironment env,
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
            // spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItem(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItem(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject setItemStatus(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemStatusName,
            @GraphQLName("itemStatusChoice") @GraphQLNonNull final String itemStatusChoice) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getSetItemStatusForm();

            commandForm.setItemName(itemStatusName);
            commandForm.setItemStatusChoice(itemStatusChoice);

            mutationResultObject.setCommandResult(ItemUtil.getHome().setItemStatus(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createItemUnitOfMeasureType(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemUnitOfMeasureType(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editItemUnitOfMeasureType(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().editItemUnitOfMeasureType(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemUnitOfMeasureType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemUnitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemUnitOfMeasureTypeForm();

            commandForm.setItemName(itemName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemUnitOfMeasureType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }
    
    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createItemDescription(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemDescription(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editItemDescription(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().editItemDescription(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemDescription(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionForm();

            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setLanguageIsoName(languageIsoName);

            mutationResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescription(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject createItemPrice(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().createItemPrice(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteItemPrice(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().deleteItemPrice(getUserVisitPK(env), commandForm);
            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject editItemPrice(final DataFetchingEnvironment env,
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

            var commandResult = ItemUtil.getHome().editItemPrice(getUserVisitPK(env), commandForm);

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

                commandResult = ItemUtil.getHome().editItemPrice(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createOrderType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") @GraphQLNonNull final String orderTypeName,
            @GraphQLName("parentOrderTypeName") final String parentOrderTypeName,
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
            commandForm.setParentOrderTypeName(parentOrderTypeName);
            commandForm.setOrderSequenceTypeName(orderSequenceTypeName);
            commandForm.setOrderWorkflowName(orderWorkflowName);
            commandForm.setOrderWorkflowEntranceName(orderWorkflowEntranceName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OrderUtil.getHome().createOrderType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editOrderType(final DataFetchingEnvironment env,
            @GraphQLName("originalOrderTypeName") final String originalOrderTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("parentOrderTypeName") final String parentOrderTypeName,
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
            spec.setUlid(id);

            var commandForm = OrderUtil.getHome().getEditOrderTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OrderUtil.getHome().editOrderType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOrderTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                mutationResultObject.setEntityInstance(result.getOrderType().getEntityInstance());

                if(arguments.containsKey("orderTypeName"))
                    edit.setOrderTypeName(orderTypeName);
                if(arguments.containsKey("parentOrderTypeName"))
                    edit.setParentOrderTypeName(parentOrderTypeName);
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

                commandResult = OrderUtil.getHome().editOrderType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteOrderType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OrderUtil.getHome().getDeleteOrderTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(OrderUtil.getHome().deleteOrderType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createOrderTimeType(final DataFetchingEnvironment env,
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

            var commandResult = OrderUtil.getHome().createOrderTimeType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editOrderTimeType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = OrderUtil.getHome().getEditOrderTimeTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OrderUtil.getHome().editOrderTimeType(getUserVisitPK(env), commandForm);

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

                commandResult = OrderUtil.getHome().editOrderTimeType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteOrderTimeType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderTimeTypeName") final String orderTimeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OrderUtil.getHome().getDeleteOrderTimeTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderTimeTypeName(orderTimeTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(OrderUtil.getHome().deleteOrderTimeType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createOrderPriority(final DataFetchingEnvironment env,
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

            var commandResult = OrderUtil.getHome().createOrderPriority(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editOrderPriority(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = OrderUtil.getHome().getEditOrderPriorityForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OrderUtil.getHome().editOrderPriority(getUserVisitPK(env), commandForm);

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

                commandResult = OrderUtil.getHome().editOrderPriority(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteOrderPriority(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderPriorityName") final String orderPriorityName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = OrderUtil.getHome().getDeleteOrderPriorityForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderPriorityName(orderPriorityName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(OrderUtil.getHome().deleteOrderPriority(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createWishlistType(final DataFetchingEnvironment env,
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

            var commandResult = WishlistUtil.getHome().createWishlistType(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editWishlistType(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = WishlistUtil.getHome().getEditWishlistTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WishlistUtil.getHome().editWishlistType(getUserVisitPK(env), commandForm);

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

                commandResult = WishlistUtil.getHome().editWishlistType(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteWishlistType(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WishlistUtil.getHome().getDeleteWishlistTypeForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(WishlistUtil.getHome().deleteWishlistType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createWishlistPriority(final DataFetchingEnvironment env,
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

            var commandResult = WishlistUtil.getHome().createWishlistPriority(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject editWishlistPriority(final DataFetchingEnvironment env,
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
            spec.setUlid(id);

            var commandForm = WishlistUtil.getHome().getEditWishlistPriorityForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = WishlistUtil.getHome().editWishlistPriority(getUserVisitPK(env), commandForm);

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

                commandResult = WishlistUtil.getHome().editWishlistPriority(getUserVisitPK(env), commandForm);
            }

            mutationResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject deleteWishlistPriority(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("wishlistPriorityName") final String wishlistPriorityName,
            @GraphQLName("id") @GraphQLID final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = WishlistUtil.getHome().getDeleteWishlistPriorityForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setWishlistPriorityName(wishlistPriorityName);
            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(WishlistUtil.getHome().deleteWishlistPriority(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject unlockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getUnlockEntityForm();

            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(CoreUtil.getHome().unlockEntity(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject lockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = CoreUtil.getHome().getLockEntityForm();

            commandForm.setUlid(id);

            mutationResultObject.setCommandResult(CoreUtil.getHome().lockEntity(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject customerLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getCustomerLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(getRemoteInet4Address(env));

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().customerLogin(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultWithIdObject createCustomer(final DataFetchingEnvironment env,
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

            var commandResult = PartyUtil.getHome().createCustomer(getUserVisitPK(env), commandForm);
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
    public static MutationResultWithIdObject createCustomerWithLogin(final DataFetchingEnvironment env,
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

            var commandResult = PartyUtil.getHome().createCustomerWithLogin(getUserVisitPK(env), commandForm);
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
    public static MutationResultObject employeeLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getEmployeeLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(getRemoteInet4Address(env));
            commandForm.setCompanyName(companyName);

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().employeeLogin(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject vendorLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getVendorLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(getRemoteInet4Address(env));

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().vendorLogin(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setPassword")
    public static MutationResultObject setPassword(final DataFetchingEnvironment env,
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

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().setPassword(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject recoverPassword(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("username") final String username,
            @GraphQLName("answer") final String answer) {
        var mutationResultObject = new MutationResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getRecoverPasswordForm();

            commandForm.setPartyName(partyName);
            commandForm.setUsername(username);
            commandForm.setAnswer(answer);

            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().recoverPassword(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject idle(final DataFetchingEnvironment env) {
        var mutationResultObject = new MutationResultObject();

        try {
            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().idle(getUserVisitPK(env)));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static MutationResultObject logout(final DataFetchingEnvironment env) {
        var mutationResultObject = new MutationResultObject();

        try {
            mutationResultObject.setCommandResult(AuthenticationUtil.getHome().logout(getUserVisitPK(env)));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mutationResultObject;
    }

}
