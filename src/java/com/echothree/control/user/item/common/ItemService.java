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

package com.echothree.control.user.item.common;

import com.echothree.control.user.item.common.form.*;
import com.echothree.control.user.item.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ItemService
        extends ItemForms {
    
    // --------------------------------------------------------------------------------
    //   Testing
    // --------------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Item Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemType(UserVisitPK userVisitPK, CreateItemTypeForm form);
    
    CommandResult<GetItemTypeResult> getItemType(UserVisitPK userVisitPK, GetItemTypeForm form);
    
    CommandResult<GetItemTypesResult> getItemTypes(UserVisitPK userVisitPK, GetItemTypesForm form);
    
    CommandResult<GetItemTypeChoicesResult> getItemTypeChoices(UserVisitPK userVisitPK, GetItemTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemTypeDescription(UserVisitPK userVisitPK, CreateItemTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemDeliveryType(UserVisitPK userVisitPK, CreateItemDeliveryTypeForm form);
    
    CommandResult<GetItemDeliveryTypeResult> getItemDeliveryType(UserVisitPK userVisitPK, GetItemDeliveryTypeForm form);
    
    CommandResult<GetItemDeliveryTypesResult> getItemDeliveryTypes(UserVisitPK userVisitPK, GetItemDeliveryTypesForm form);
    
    CommandResult<GetItemDeliveryTypeChoicesResult> getItemDeliveryTypeChoices(UserVisitPK userVisitPK, GetItemDeliveryTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemDeliveryTypeDescription(UserVisitPK userVisitPK, CreateItemDeliveryTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemInventoryType(UserVisitPK userVisitPK, CreateItemInventoryTypeForm form);
    
    CommandResult<GetItemInventoryTypeResult> getItemInventoryType(UserVisitPK userVisitPK, GetItemInventoryTypeForm form);
    
    CommandResult<GetItemInventoryTypesResult> getItemInventoryTypes(UserVisitPK userVisitPK, GetItemInventoryTypesForm form);
    
    CommandResult<GetItemInventoryTypeChoicesResult> getItemInventoryTypeChoices(UserVisitPK userVisitPK, GetItemInventoryTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemInventoryTypeDescription(UserVisitPK userVisitPK, CreateItemInventoryTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemUseType(UserVisitPK userVisitPK, CreateItemUseTypeForm form);
    
    CommandResult<GetItemUseTypeResult> getItemUseType(UserVisitPK userVisitPK, GetItemUseTypeForm form);
    
    CommandResult<GetItemUseTypesResult> getItemUseTypes(UserVisitPK userVisitPK, GetItemUseTypesForm form);
    
    CommandResult<GetItemUseTypeChoicesResult> getItemUseTypeChoices(UserVisitPK userVisitPK, GetItemUseTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemUseTypeDescription(UserVisitPK userVisitPK, CreateItemUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemCategory(UserVisitPK userVisitPK, CreateItemCategoryForm form);

    CommandResult<GetItemCategoryChoicesResult> getItemCategoryChoices(UserVisitPK userVisitPK, GetItemCategoryChoicesForm form);

    CommandResult<GetItemCategoryResult> getItemCategory(UserVisitPK userVisitPK, GetItemCategoryForm form);

    CommandResult<GetItemCategoriesResult> getItemCategories(UserVisitPK userVisitPK, GetItemCategoriesForm form);

    CommandResult<?> setDefaultItemCategory(UserVisitPK userVisitPK, SetDefaultItemCategoryForm form);

    CommandResult<EditItemCategoryResult> editItemCategory(UserVisitPK userVisitPK, EditItemCategoryForm form);

    CommandResult<?> deleteItemCategory(UserVisitPK userVisitPK, DeleteItemCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemCategoryDescription(UserVisitPK userVisitPK, CreateItemCategoryDescriptionForm form);

    CommandResult<GetItemCategoryDescriptionResult> getItemCategoryDescription(UserVisitPK userVisitPK, GetItemCategoryDescriptionForm form);

    CommandResult<GetItemCategoryDescriptionsResult> getItemCategoryDescriptions(UserVisitPK userVisitPK, GetItemCategoryDescriptionsForm form);

    CommandResult<EditItemCategoryDescriptionResult> editItemCategoryDescription(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form);

    CommandResult<?> deleteItemCategoryDescription(UserVisitPK userVisitPK, DeleteItemCategoryDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // -------------------------------------------------------------------------

    CommandResult<?> createItemAliasChecksumType(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeForm form);

    CommandResult<GetItemAliasChecksumTypesResult> getItemAliasChecksumTypes(UserVisitPK userVisitPK, GetItemAliasChecksumTypesForm form);

    CommandResult<GetItemAliasChecksumTypeResult> getItemAliasChecksumType(UserVisitPK userVisitPK, GetItemAliasChecksumTypeForm form);

    CommandResult<GetItemAliasChecksumTypeChoicesResult> getItemAliasChecksumTypeChoices(UserVisitPK userVisitPK, GetItemAliasChecksumTypeChoicesForm form);

    // -------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createItemAliasChecksumTypeDescription(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateItemAliasTypeResult> createItemAliasType(UserVisitPK userVisitPK, CreateItemAliasTypeForm form);

    CommandResult<GetItemAliasTypeChoicesResult> getItemAliasTypeChoices(UserVisitPK userVisitPK, GetItemAliasTypeChoicesForm form);

    CommandResult<GetItemAliasTypeResult> getItemAliasType(UserVisitPK userVisitPK, GetItemAliasTypeForm form);

    CommandResult<GetItemAliasTypesResult> getItemAliasTypes(UserVisitPK userVisitPK, GetItemAliasTypesForm form);

    CommandResult<?> setDefaultItemAliasType(UserVisitPK userVisitPK, SetDefaultItemAliasTypeForm form);

    CommandResult<EditItemAliasTypeResult> editItemAliasType(UserVisitPK userVisitPK, EditItemAliasTypeForm form);

    CommandResult<?> deleteItemAliasType(UserVisitPK userVisitPK, DeleteItemAliasTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemAliasTypeDescription(UserVisitPK userVisitPK, CreateItemAliasTypeDescriptionForm form);

    CommandResult<GetItemAliasTypeDescriptionResult> getItemAliasTypeDescription(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionForm form);

    CommandResult<GetItemAliasTypeDescriptionsResult> getItemAliasTypeDescriptions(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionsForm form);

    CommandResult<EditItemAliasTypeDescriptionResult> editItemAliasTypeDescription(UserVisitPK userVisitPK, EditItemAliasTypeDescriptionForm form);

    CommandResult<?> deleteItemAliasTypeDescription(UserVisitPK userVisitPK, DeleteItemAliasTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createItemDescriptionType(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form);

    CommandResult<GetItemDescriptionTypeChoicesResult> getItemDescriptionTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeChoicesForm form);

    CommandResult<GetItemDescriptionTypeResult> getItemDescriptionType(UserVisitPK userVisitPK, GetItemDescriptionTypeForm form);

    CommandResult<GetItemDescriptionTypesResult> getItemDescriptionTypes(UserVisitPK userVisitPK, GetItemDescriptionTypesForm form);

    CommandResult<?> setDefaultItemDescriptionType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeForm form);

    CommandResult<EditItemDescriptionTypeResult> editItemDescriptionType(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form);

    CommandResult<?> deleteItemDescriptionType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createItemDescriptionTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeDescriptionResult> getItemDescriptionTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeDescriptionsResult> getItemDescriptionTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionsForm form);

    CommandResult<EditItemDescriptionTypeDescriptionResult> editItemDescriptionTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form);

    CommandResult<?> deleteItemDescriptionTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemDescriptionTypeUseTypeResult> createItemDescriptionTypeUseType(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeForm form);

    CommandResult<GetItemDescriptionTypeUseTypeChoicesResult> getItemDescriptionTypeUseTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeChoicesForm form);

    CommandResult<GetItemDescriptionTypeUseTypeResult> getItemDescriptionTypeUseType(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeForm form);

    CommandResult<GetItemDescriptionTypeUseTypesResult> getItemDescriptionTypeUseTypes(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypesForm form);

    CommandResult<?> setDefaultItemDescriptionTypeUseType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeUseTypeForm form);

    CommandResult<EditItemDescriptionTypeUseTypeResult> editItemDescriptionTypeUseType(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form);

    CommandResult<?> deleteItemDescriptionTypeUseType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeUseTypeDescriptionResult> getItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeUseTypeDescriptionsResult> getItemDescriptionTypeUseTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionsForm form);

    CommandResult<EditItemDescriptionTypeUseTypeDescriptionResult> editItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult<?> deleteItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    public CommandResult<?> createItemDescriptionTypeUse(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseForm form);

    public CommandResult<GetItemDescriptionTypeUseResult> getItemDescriptionTypeUse(UserVisitPK userVisitPK, GetItemDescriptionTypeUseForm form);

    public CommandResult<GetItemDescriptionTypeUsesResult> getItemDescriptionTypeUses(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form);

    public CommandResult<?> deleteItemDescriptionTypeUse(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseForm form);

    // --------------------------------------------------------------------------------
    //   Item Image Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemImageTypeResult> createItemImageType(UserVisitPK userVisitPK, CreateItemImageTypeForm form);

    CommandResult<GetItemImageTypeChoicesResult> getItemImageTypeChoices(UserVisitPK userVisitPK, GetItemImageTypeChoicesForm form);

    CommandResult<GetItemImageTypeResult> getItemImageType(UserVisitPK userVisitPK, GetItemImageTypeForm form);

    CommandResult<GetItemImageTypesResult> getItemImageTypes(UserVisitPK userVisitPK, GetItemImageTypesForm form);

    CommandResult<?> setDefaultItemImageType(UserVisitPK userVisitPK, SetDefaultItemImageTypeForm form);

    CommandResult<EditItemImageTypeResult> editItemImageType(UserVisitPK userVisitPK, EditItemImageTypeForm form);

    CommandResult<?> deleteItemImageType(UserVisitPK userVisitPK, DeleteItemImageTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Image Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createItemImageTypeDescription(UserVisitPK userVisitPK, CreateItemImageTypeDescriptionForm form);

    CommandResult<GetItemImageTypeDescriptionResult> getItemImageTypeDescription(UserVisitPK userVisitPK, GetItemImageTypeDescriptionForm form);

    CommandResult<GetItemImageTypeDescriptionsResult> getItemImageTypeDescriptions(UserVisitPK userVisitPK, GetItemImageTypeDescriptionsForm form);

    CommandResult<EditItemImageTypeDescriptionResult> editItemImageTypeDescription(UserVisitPK userVisitPK, EditItemImageTypeDescriptionForm form);

    CommandResult<?> deleteItemImageTypeDescription(UserVisitPK userVisitPK, DeleteItemImageTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateItemResult> createItem(UserVisitPK userVisitPK, CreateItemForm form);
    
    CommandResult<GetItemStatusChoicesResult> getItemStatusChoices(UserVisitPK userVisitPK, GetItemStatusChoicesForm form);
    
    CommandResult<?> setItemStatus(UserVisitPK userVisitPK, SetItemStatusForm form);

    CommandResult<GetItemResult> getItem(UserVisitPK userVisitPK, GetItemForm form);

    CommandResult<GetItemsResult> getItems(UserVisitPK userVisitPK, GetItemsForm form);

    CommandResult<EditItemResult> editItem(UserVisitPK userVisitPK, EditItemForm form);
    
    // -------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createItemUnitOfMeasureType(UserVisitPK userVisitPK, CreateItemUnitOfMeasureTypeForm form);
    
    CommandResult<GetItemUnitOfMeasureTypeResult> getItemUnitOfMeasureType(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypeForm form);

    CommandResult<GetItemUnitOfMeasureTypesResult> getItemUnitOfMeasureTypes(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form);
    
    CommandResult<?> setDefaultItemUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultItemUnitOfMeasureTypeForm form);
    
    CommandResult<EditItemUnitOfMeasureTypeResult> editItemUnitOfMeasureType(UserVisitPK userVisitPK, EditItemUnitOfMeasureTypeForm form);
    
    CommandResult<?> deleteItemUnitOfMeasureType(UserVisitPK userVisitPK, DeleteItemUnitOfMeasureTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemAlias(UserVisitPK userVisitPK, CreateItemAliasForm form);
    
    CommandResult<GetItemAliasResult> getItemAlias(UserVisitPK userVisitPK, GetItemAliasForm form);

    CommandResult<GetItemAliasesResult> getItemAliases(UserVisitPK userVisitPK, GetItemAliasesForm form);
    
    CommandResult<EditItemAliasResult> editItemAlias(UserVisitPK userVisitPK, EditItemAliasForm form);
    
    CommandResult<?> deleteItemAlias(UserVisitPK userVisitPK, DeleteItemAliasForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemDescription(UserVisitPK userVisitPK, CreateItemDescriptionForm form);
    
    CommandResult<GetItemDescriptionResult> getItemDescription(UserVisitPK userVisitPK, GetItemDescriptionForm form);
    
    CommandResult<GetItemDescriptionsResult> getItemDescriptions(UserVisitPK userVisitPK, GetItemDescriptionsForm form);
    
    CommandResult<EditItemDescriptionResult> editItemDescription(UserVisitPK userVisitPK, EditItemDescriptionForm form);
    
    CommandResult<?> deleteItemDescription(UserVisitPK userVisitPK, DeleteItemDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemPriceType(UserVisitPK userVisitPK, CreateItemPriceTypeForm form);
    
    CommandResult<GetItemPriceTypeResult> getItemPriceType(UserVisitPK userVisitPK, GetItemPriceTypeForm form);
    
    CommandResult<GetItemPriceTypesResult> getItemPriceTypes(UserVisitPK userVisitPK, GetItemPriceTypesForm form);
    
    CommandResult<GetItemPriceTypeChoicesResult> getItemPriceTypeChoices(UserVisitPK userVisitPK, GetItemPriceTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemPriceTypeDescription(UserVisitPK userVisitPK, CreateItemPriceTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemPrice(UserVisitPK userVisitPK, CreateItemPriceForm form);
    
    CommandResult<GetItemPriceResult> getItemPrice(UserVisitPK userVisitPK, GetItemPriceForm form);

    CommandResult<GetItemPricesResult> getItemPrices(UserVisitPK userVisitPK, GetItemPricesForm form);
    
    CommandResult<EditItemPriceResult> editItemPrice(UserVisitPK userVisitPK, EditItemPriceForm form);
    
    CommandResult<?> deleteItemPrice(UserVisitPK userVisitPK, DeleteItemPriceForm form);

    // --------------------------------------------------------------------------------
    //   Item Volume Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemVolumeTypeResult> createItemVolumeType(UserVisitPK userVisitPK, CreateItemVolumeTypeForm form);

    CommandResult<GetItemVolumeTypeChoicesResult> getItemVolumeTypeChoices(UserVisitPK userVisitPK, GetItemVolumeTypeChoicesForm form);

    CommandResult<GetItemVolumeTypeResult> getItemVolumeType(UserVisitPK userVisitPK, GetItemVolumeTypeForm form);

    CommandResult<GetItemVolumeTypesResult> getItemVolumeTypes(UserVisitPK userVisitPK, GetItemVolumeTypesForm form);

    CommandResult<?> setDefaultItemVolumeType(UserVisitPK userVisitPK, SetDefaultItemVolumeTypeForm form);

    CommandResult<EditItemVolumeTypeResult> editItemVolumeType(UserVisitPK userVisitPK, EditItemVolumeTypeForm form);

    CommandResult<?> deleteItemVolumeType(UserVisitPK userVisitPK, DeleteItemVolumeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Volume Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createItemVolumeTypeDescription(UserVisitPK userVisitPK, CreateItemVolumeTypeDescriptionForm form);

    CommandResult<GetItemVolumeTypeDescriptionResult> getItemVolumeTypeDescription(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionForm form);

    CommandResult<GetItemVolumeTypeDescriptionsResult> getItemVolumeTypeDescriptions(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionsForm form);

    CommandResult<EditItemVolumeTypeDescriptionResult> editItemVolumeTypeDescription(UserVisitPK userVisitPK, EditItemVolumeTypeDescriptionForm form);

    CommandResult<?> deleteItemVolumeTypeDescription(UserVisitPK userVisitPK, DeleteItemVolumeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemVolume(UserVisitPK userVisitPK, CreateItemVolumeForm form);
    
    CommandResult<GetItemVolumeResult> getItemVolume(UserVisitPK userVisitPK, GetItemVolumeForm form);

    CommandResult<GetItemVolumesResult> getItemVolumes(UserVisitPK userVisitPK, GetItemVolumesForm form);

    CommandResult<EditItemVolumeResult> editItemVolume(UserVisitPK userVisitPK, EditItemVolumeForm form);
    
    CommandResult<?> deleteItemVolume(UserVisitPK userVisitPK, DeleteItemVolumeForm form);

    // --------------------------------------------------------------------------------
    //   Item Weight Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemWeightTypeResult> createItemWeightType(UserVisitPK userVisitPK, CreateItemWeightTypeForm form);

    CommandResult<GetItemWeightTypeChoicesResult> getItemWeightTypeChoices(UserVisitPK userVisitPK, GetItemWeightTypeChoicesForm form);

    CommandResult<GetItemWeightTypeResult> getItemWeightType(UserVisitPK userVisitPK, GetItemWeightTypeForm form);

    CommandResult<GetItemWeightTypesResult> getItemWeightTypes(UserVisitPK userVisitPK, GetItemWeightTypesForm form);

    CommandResult<?> setDefaultItemWeightType(UserVisitPK userVisitPK, SetDefaultItemWeightTypeForm form);

    CommandResult<EditItemWeightTypeResult> editItemWeightType(UserVisitPK userVisitPK, EditItemWeightTypeForm form);

    CommandResult<?> deleteItemWeightType(UserVisitPK userVisitPK, DeleteItemWeightTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Weight Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createItemWeightTypeDescription(UserVisitPK userVisitPK, CreateItemWeightTypeDescriptionForm form);

    CommandResult<GetItemWeightTypeDescriptionResult> getItemWeightTypeDescription(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionForm form);

    CommandResult<GetItemWeightTypeDescriptionsResult> getItemWeightTypeDescriptions(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionsForm form);

    CommandResult<EditItemWeightTypeDescriptionResult> editItemWeightTypeDescription(UserVisitPK userVisitPK, EditItemWeightTypeDescriptionForm form);

    CommandResult<?> deleteItemWeightTypeDescription(UserVisitPK userVisitPK, DeleteItemWeightTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemWeight(UserVisitPK userVisitPK, CreateItemWeightForm form);
    
    CommandResult<GetItemWeightResult> getItemWeight(UserVisitPK userVisitPK, GetItemWeightForm form);

    CommandResult<GetItemWeightsResult> getItemWeights(UserVisitPK userVisitPK, GetItemWeightsForm form);

    CommandResult<EditItemWeightResult> editItemWeight(UserVisitPK userVisitPK, EditItemWeightForm form);
    
    CommandResult<?> deleteItemWeight(UserVisitPK userVisitPK, DeleteItemWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemCountryOfOrigin(UserVisitPK userVisitPK, CreateItemCountryOfOriginForm form);
    
    CommandResult<GetItemCountryOfOriginResult> getItemCountryOfOrigin(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form);

    CommandResult<GetItemCountryOfOriginsResult> getItemCountryOfOrigins(UserVisitPK userVisitPK, GetItemCountryOfOriginsForm form);

    CommandResult<EditItemCountryOfOriginResult> editItemCountryOfOrigin(UserVisitPK userVisitPK, EditItemCountryOfOriginForm form);
    
    CommandResult<?> deleteItemCountryOfOrigin(UserVisitPK userVisitPK, DeleteItemCountryOfOriginForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemKitMember(UserVisitPK userVisitPK, CreateItemKitMemberForm form);
    
    CommandResult<GetItemKitMemberResult> getItemKitMember(UserVisitPK userVisitPK, GetItemKitMemberForm form);

    CommandResult<GetItemKitMembersResult> getItemKitMembers(UserVisitPK userVisitPK, GetItemKitMembersForm form);

    CommandResult<?> deleteItemKitMember(UserVisitPK userVisitPK, DeleteItemKitMemberForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemPackCheckRequirement(UserVisitPK userVisitPK, CreateItemPackCheckRequirementForm form);
    
    CommandResult<GetItemPackCheckRequirementResult> getItemPackCheckRequirement(UserVisitPK userVisitPK, GetItemPackCheckRequirementForm form);
    
    CommandResult<GetItemPackCheckRequirementsResult> getItemPackCheckRequirements(UserVisitPK userVisitPK, GetItemPackCheckRequirementsForm form);
    
    CommandResult<EditItemPackCheckRequirementResult> editItemPackCheckRequirement(UserVisitPK userVisitPK, EditItemPackCheckRequirementForm form);

    CommandResult<?> deleteItemPackCheckRequirement(UserVisitPK userVisitPK, DeleteItemPackCheckRequirementForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------
    
    CommandResult<GetItemShippingTimeResult> getItemShippingTime(UserVisitPK userVisitPK, GetItemShippingTimeForm form);

    CommandResult<GetItemShippingTimesResult> getItemShippingTimes(UserVisitPK userVisitPK, GetItemShippingTimesForm form);

    CommandResult<EditItemShippingTimeResult> editItemShippingTime(UserVisitPK userVisitPK, EditItemShippingTimeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form);
    
    CommandResult<GetItemUnitCustomerTypeLimitResult> getItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitForm form);

    CommandResult<GetItemUnitCustomerTypeLimitsResult> getItemUnitCustomerTypeLimits(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitsForm form);

    CommandResult<EditItemUnitCustomerTypeLimitResult> editItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, EditItemUnitCustomerTypeLimitForm form);

    CommandResult<?> deleteItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, DeleteItemUnitCustomerTypeLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemUnitLimit(UserVisitPK userVisitPK, CreateItemUnitLimitForm form);
    
    CommandResult<GetItemUnitLimitResult> getItemUnitLimit(UserVisitPK userVisitPK, GetItemUnitLimitForm form);

    CommandResult<GetItemUnitLimitsResult> getItemUnitLimits(UserVisitPK userVisitPK, GetItemUnitLimitsForm form);

    CommandResult<EditItemUnitLimitResult> editItemUnitLimit(UserVisitPK userVisitPK, EditItemUnitLimitForm form);
    
    CommandResult<?> deleteItemUnitLimit(UserVisitPK userVisitPK, DeleteItemUnitLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createItemUnitPriceLimit(UserVisitPK userVisitPK, CreateItemUnitPriceLimitForm form);

    CommandResult<GetItemUnitPriceLimitResult> getItemUnitPriceLimit(UserVisitPK userVisitPK, GetItemUnitPriceLimitForm form);
    
    CommandResult<GetItemUnitPriceLimitsResult> getItemUnitPriceLimits(UserVisitPK userVisitPK, GetItemUnitPriceLimitsForm form);

    CommandResult<EditItemUnitPriceLimitResult> editItemUnitPriceLimit(UserVisitPK userVisitPK, EditItemUnitPriceLimitForm form);
    
    CommandResult<?> deleteItemUnitPriceLimit(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createRelatedItemType(UserVisitPK userVisitPK, CreateRelatedItemTypeForm form);

    CommandResult<GetRelatedItemTypeChoicesResult> getRelatedItemTypeChoices(UserVisitPK userVisitPK, GetRelatedItemTypeChoicesForm form);

    CommandResult<GetRelatedItemTypeResult> getRelatedItemType(UserVisitPK userVisitPK, GetRelatedItemTypeForm form);

    CommandResult<GetRelatedItemTypesResult> getRelatedItemTypes(UserVisitPK userVisitPK, GetRelatedItemTypesForm form);

    CommandResult<?> setDefaultRelatedItemType(UserVisitPK userVisitPK, SetDefaultRelatedItemTypeForm form);

    CommandResult<EditRelatedItemTypeResult> editRelatedItemType(UserVisitPK userVisitPK, EditRelatedItemTypeForm form);

    CommandResult<?> deleteRelatedItemType(UserVisitPK userVisitPK, DeleteRelatedItemTypeForm form);

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createRelatedItemTypeDescription(UserVisitPK userVisitPK, CreateRelatedItemTypeDescriptionForm form);

    CommandResult<GetRelatedItemTypeDescriptionResult> getRelatedItemTypeDescription(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionForm form);

    CommandResult<GetRelatedItemTypeDescriptionsResult> getRelatedItemTypeDescriptions(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionsForm form);

    CommandResult<EditRelatedItemTypeDescriptionResult> editRelatedItemTypeDescription(UserVisitPK userVisitPK, EditRelatedItemTypeDescriptionForm form);

    CommandResult<?> deleteRelatedItemTypeDescription(UserVisitPK userVisitPK, DeleteRelatedItemTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createRelatedItem(UserVisitPK userVisitPK, CreateRelatedItemForm form);
    
    CommandResult<GetRelatedItemResult> getRelatedItem(UserVisitPK userVisitPK, GetRelatedItemForm form);

    CommandResult<GetRelatedItemsResult> getRelatedItems(UserVisitPK userVisitPK, GetRelatedItemsForm form);

    CommandResult<EditRelatedItemResult> editRelatedItem(UserVisitPK userVisitPK, EditRelatedItemForm form);

    CommandResult<?> deleteRelatedItem(UserVisitPK userVisitPK, DeleteRelatedItemForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    CommandResult<?> createHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodesResult> getHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodesForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeResult> getHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeChoicesResult> getHarmonizedTariffScheduleCodeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeChoicesForm form);

    CommandResult<?> setDefaultHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeResult> editHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form);

    CommandResult<?> deleteHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeTranslationsResult> getHarmonizedTariffScheduleCodeTranslations(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeTranslationResult> getHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeTranslationResult> editHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult<?> deleteHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Units
    // -------------------------------------------------------------------------

    CommandResult<?> createHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitsResult> getHarmonizedTariffScheduleCodeUnits(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitResult> getHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitChoicesResult> getHarmonizedTariffScheduleCodeUnitChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitChoicesForm form);

    CommandResult<?> setDefaultHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUnitResult> editHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<?> deleteHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Unit Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitDescriptionsResult> getHarmonizedTariffScheduleCodeUnitDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitDescriptionResult> getHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUnitDescriptionResult> editHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult<?> deleteHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // -------------------------------------------------------------------------

    CommandResult<?> createHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypesResult> getHarmonizedTariffScheduleCodeUseTypes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypesForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeResult> getHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeChoicesResult> getHarmonizedTariffScheduleCodeUseTypeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeChoicesForm form);

    CommandResult<?> setDefaultHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUseTypeResult> editHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<?> deleteHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeDescriptionsResult> getHarmonizedTariffScheduleCodeUseTypeDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeDescriptionResult> getHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult> editHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult<?> deleteHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // -------------------------------------------------------------------------

    CommandResult<?> createHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUsesResult> getHarmonizedTariffScheduleCodeUses(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUsesForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseResult> getHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form);

    CommandResult<?> deleteHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseForm form);

    // -------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    CommandResult<?> createItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateItemHarmonizedTariffScheduleCodeForm form);

    CommandResult<GetItemHarmonizedTariffScheduleCodesResult> getItemHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodesForm form);

    CommandResult<GetItemHarmonizedTariffScheduleCodeResult> getItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodeForm form);

    CommandResult<EditItemHarmonizedTariffScheduleCodeResult> editItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form);

    CommandResult<?> deleteItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteItemHarmonizedTariffScheduleCodeForm form);

}
