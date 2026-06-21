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
import com.echothree.util.common.command.VoidResult;

public interface ItemService
        extends ItemForms {
    
    // --------------------------------------------------------------------------------
    //   Testing
    // --------------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Item Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemType(UserVisitPK userVisitPK, CreateItemTypeForm form);
    
    CommandResult<GetItemTypeResult> getItemType(UserVisitPK userVisitPK, GetItemTypeForm form);
    
    CommandResult<GetItemTypesResult> getItemTypes(UserVisitPK userVisitPK, GetItemTypesForm form);
    
    CommandResult<GetItemTypeChoicesResult> getItemTypeChoices(UserVisitPK userVisitPK, GetItemTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemTypeDescription(UserVisitPK userVisitPK, CreateItemTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemDeliveryType(UserVisitPK userVisitPK, CreateItemDeliveryTypeForm form);
    
    CommandResult<GetItemDeliveryTypeResult> getItemDeliveryType(UserVisitPK userVisitPK, GetItemDeliveryTypeForm form);
    
    CommandResult<GetItemDeliveryTypesResult> getItemDeliveryTypes(UserVisitPK userVisitPK, GetItemDeliveryTypesForm form);
    
    CommandResult<GetItemDeliveryTypeChoicesResult> getItemDeliveryTypeChoices(UserVisitPK userVisitPK, GetItemDeliveryTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemDeliveryTypeDescription(UserVisitPK userVisitPK, CreateItemDeliveryTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemInventoryType(UserVisitPK userVisitPK, CreateItemInventoryTypeForm form);
    
    CommandResult<GetItemInventoryTypeResult> getItemInventoryType(UserVisitPK userVisitPK, GetItemInventoryTypeForm form);
    
    CommandResult<GetItemInventoryTypesResult> getItemInventoryTypes(UserVisitPK userVisitPK, GetItemInventoryTypesForm form);
    
    CommandResult<GetItemInventoryTypeChoicesResult> getItemInventoryTypeChoices(UserVisitPK userVisitPK, GetItemInventoryTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemInventoryTypeDescription(UserVisitPK userVisitPK, CreateItemInventoryTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemUseType(UserVisitPK userVisitPK, CreateItemUseTypeForm form);
    
    CommandResult<GetItemUseTypeResult> getItemUseType(UserVisitPK userVisitPK, GetItemUseTypeForm form);
    
    CommandResult<GetItemUseTypesResult> getItemUseTypes(UserVisitPK userVisitPK, GetItemUseTypesForm form);
    
    CommandResult<GetItemUseTypeChoicesResult> getItemUseTypeChoices(UserVisitPK userVisitPK, GetItemUseTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemUseTypeDescription(UserVisitPK userVisitPK, CreateItemUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateItemCategoryResult> createItemCategory(UserVisitPK userVisitPK, CreateItemCategoryForm form);

    CommandResult<GetItemCategoryChoicesResult> getItemCategoryChoices(UserVisitPK userVisitPK, GetItemCategoryChoicesForm form);

    CommandResult<GetItemCategoryResult> getItemCategory(UserVisitPK userVisitPK, GetItemCategoryForm form);

    CommandResult<GetItemCategoriesResult> getItemCategories(UserVisitPK userVisitPK, GetItemCategoriesForm form);

    CommandResult<VoidResult> setDefaultItemCategory(UserVisitPK userVisitPK, SetDefaultItemCategoryForm form);

    CommandResult<EditItemCategoryResult> editItemCategory(UserVisitPK userVisitPK, EditItemCategoryForm form);

    CommandResult<VoidResult> deleteItemCategory(UserVisitPK userVisitPK, DeleteItemCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemCategoryDescription(UserVisitPK userVisitPK, CreateItemCategoryDescriptionForm form);

    CommandResult<GetItemCategoryDescriptionResult> getItemCategoryDescription(UserVisitPK userVisitPK, GetItemCategoryDescriptionForm form);

    CommandResult<GetItemCategoryDescriptionsResult> getItemCategoryDescriptions(UserVisitPK userVisitPK, GetItemCategoryDescriptionsForm form);

    CommandResult<EditItemCategoryDescriptionResult> editItemCategoryDescription(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form);

    CommandResult<VoidResult> deleteItemCategoryDescription(UserVisitPK userVisitPK, DeleteItemCategoryDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createItemAliasChecksumType(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeForm form);

    CommandResult<GetItemAliasChecksumTypesResult> getItemAliasChecksumTypes(UserVisitPK userVisitPK, GetItemAliasChecksumTypesForm form);

    CommandResult<GetItemAliasChecksumTypeResult> getItemAliasChecksumType(UserVisitPK userVisitPK, GetItemAliasChecksumTypeForm form);

    CommandResult<GetItemAliasChecksumTypeChoicesResult> getItemAliasChecksumTypeChoices(UserVisitPK userVisitPK, GetItemAliasChecksumTypeChoicesForm form);

    // -------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createItemAliasChecksumTypeDescription(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateItemAliasTypeResult> createItemAliasType(UserVisitPK userVisitPK, CreateItemAliasTypeForm form);

    CommandResult<GetItemAliasTypeChoicesResult> getItemAliasTypeChoices(UserVisitPK userVisitPK, GetItemAliasTypeChoicesForm form);

    CommandResult<GetItemAliasTypeResult> getItemAliasType(UserVisitPK userVisitPK, GetItemAliasTypeForm form);

    CommandResult<GetItemAliasTypesResult> getItemAliasTypes(UserVisitPK userVisitPK, GetItemAliasTypesForm form);

    CommandResult<VoidResult> setDefaultItemAliasType(UserVisitPK userVisitPK, SetDefaultItemAliasTypeForm form);

    CommandResult<EditItemAliasTypeResult> editItemAliasType(UserVisitPK userVisitPK, EditItemAliasTypeForm form);

    CommandResult<VoidResult> deleteItemAliasType(UserVisitPK userVisitPK, DeleteItemAliasTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemAliasTypeDescription(UserVisitPK userVisitPK, CreateItemAliasTypeDescriptionForm form);

    CommandResult<GetItemAliasTypeDescriptionResult> getItemAliasTypeDescription(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionForm form);

    CommandResult<GetItemAliasTypeDescriptionsResult> getItemAliasTypeDescriptions(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionsForm form);

    CommandResult<EditItemAliasTypeDescriptionResult> editItemAliasTypeDescription(UserVisitPK userVisitPK, EditItemAliasTypeDescriptionForm form);

    CommandResult<VoidResult> deleteItemAliasTypeDescription(UserVisitPK userVisitPK, DeleteItemAliasTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemDescriptionTypeResult> createItemDescriptionType(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form);

    CommandResult<GetItemDescriptionTypeChoicesResult> getItemDescriptionTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeChoicesForm form);

    CommandResult<GetItemDescriptionTypeResult> getItemDescriptionType(UserVisitPK userVisitPK, GetItemDescriptionTypeForm form);

    CommandResult<GetItemDescriptionTypesResult> getItemDescriptionTypes(UserVisitPK userVisitPK, GetItemDescriptionTypesForm form);

    CommandResult<VoidResult> setDefaultItemDescriptionType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeForm form);

    CommandResult<EditItemDescriptionTypeResult> editItemDescriptionType(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form);

    CommandResult<VoidResult> deleteItemDescriptionType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createItemDescriptionTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeDescriptionResult> getItemDescriptionTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeDescriptionsResult> getItemDescriptionTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionsForm form);

    CommandResult<EditItemDescriptionTypeDescriptionResult> editItemDescriptionTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form);

    CommandResult<VoidResult> deleteItemDescriptionTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemDescriptionTypeUseTypeResult> createItemDescriptionTypeUseType(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeForm form);

    CommandResult<GetItemDescriptionTypeUseTypeChoicesResult> getItemDescriptionTypeUseTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeChoicesForm form);

    CommandResult<GetItemDescriptionTypeUseTypeResult> getItemDescriptionTypeUseType(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeForm form);

    CommandResult<GetItemDescriptionTypeUseTypesResult> getItemDescriptionTypeUseTypes(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypesForm form);

    CommandResult<VoidResult> setDefaultItemDescriptionTypeUseType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeUseTypeForm form);

    CommandResult<EditItemDescriptionTypeUseTypeResult> editItemDescriptionTypeUseType(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form);

    CommandResult<VoidResult> deleteItemDescriptionTypeUseType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeUseTypeDescriptionResult> getItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult<GetItemDescriptionTypeUseTypeDescriptionsResult> getItemDescriptionTypeUseTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionsForm form);

    CommandResult<EditItemDescriptionTypeUseTypeDescriptionResult> editItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult<VoidResult> deleteItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    public CommandResult<VoidResult> createItemDescriptionTypeUse(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseForm form);

    public CommandResult<GetItemDescriptionTypeUseResult> getItemDescriptionTypeUse(UserVisitPK userVisitPK, GetItemDescriptionTypeUseForm form);

    public CommandResult<GetItemDescriptionTypeUsesResult> getItemDescriptionTypeUses(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form);

    public CommandResult<VoidResult> deleteItemDescriptionTypeUse(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseForm form);

    // --------------------------------------------------------------------------------
    //   Item Image Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemImageTypeResult> createItemImageType(UserVisitPK userVisitPK, CreateItemImageTypeForm form);

    CommandResult<GetItemImageTypeChoicesResult> getItemImageTypeChoices(UserVisitPK userVisitPK, GetItemImageTypeChoicesForm form);

    CommandResult<GetItemImageTypeResult> getItemImageType(UserVisitPK userVisitPK, GetItemImageTypeForm form);

    CommandResult<GetItemImageTypesResult> getItemImageTypes(UserVisitPK userVisitPK, GetItemImageTypesForm form);

    CommandResult<VoidResult> setDefaultItemImageType(UserVisitPK userVisitPK, SetDefaultItemImageTypeForm form);

    CommandResult<EditItemImageTypeResult> editItemImageType(UserVisitPK userVisitPK, EditItemImageTypeForm form);

    CommandResult<VoidResult> deleteItemImageType(UserVisitPK userVisitPK, DeleteItemImageTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Image Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createItemImageTypeDescription(UserVisitPK userVisitPK, CreateItemImageTypeDescriptionForm form);

    CommandResult<GetItemImageTypeDescriptionResult> getItemImageTypeDescription(UserVisitPK userVisitPK, GetItemImageTypeDescriptionForm form);

    CommandResult<GetItemImageTypeDescriptionsResult> getItemImageTypeDescriptions(UserVisitPK userVisitPK, GetItemImageTypeDescriptionsForm form);

    CommandResult<EditItemImageTypeDescriptionResult> editItemImageTypeDescription(UserVisitPK userVisitPK, EditItemImageTypeDescriptionForm form);

    CommandResult<VoidResult> deleteItemImageTypeDescription(UserVisitPK userVisitPK, DeleteItemImageTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateItemResult> createItem(UserVisitPK userVisitPK, CreateItemForm form);
    
    CommandResult<GetItemStatusChoicesResult> getItemStatusChoices(UserVisitPK userVisitPK, GetItemStatusChoicesForm form);
    
    CommandResult<VoidResult> setItemStatus(UserVisitPK userVisitPK, SetItemStatusForm form);

    CommandResult<GetItemResult> getItem(UserVisitPK userVisitPK, GetItemForm form);

    CommandResult<GetItemsResult> getItems(UserVisitPK userVisitPK, GetItemsForm form);

    CommandResult<EditItemResult> editItem(UserVisitPK userVisitPK, EditItemForm form);
    
    // -------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemUnitOfMeasureType(UserVisitPK userVisitPK, CreateItemUnitOfMeasureTypeForm form);
    
    CommandResult<GetItemUnitOfMeasureTypeResult> getItemUnitOfMeasureType(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypeForm form);

    CommandResult<GetItemUnitOfMeasureTypesResult> getItemUnitOfMeasureTypes(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form);
    
    CommandResult<VoidResult> setDefaultItemUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultItemUnitOfMeasureTypeForm form);
    
    CommandResult<EditItemUnitOfMeasureTypeResult> editItemUnitOfMeasureType(UserVisitPK userVisitPK, EditItemUnitOfMeasureTypeForm form);
    
    CommandResult<VoidResult> deleteItemUnitOfMeasureType(UserVisitPK userVisitPK, DeleteItemUnitOfMeasureTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemAlias(UserVisitPK userVisitPK, CreateItemAliasForm form);
    
    CommandResult<GetItemAliasResult> getItemAlias(UserVisitPK userVisitPK, GetItemAliasForm form);

    CommandResult<GetItemAliasesResult> getItemAliases(UserVisitPK userVisitPK, GetItemAliasesForm form);
    
    CommandResult<EditItemAliasResult> editItemAlias(UserVisitPK userVisitPK, EditItemAliasForm form);
    
    CommandResult<VoidResult> deleteItemAlias(UserVisitPK userVisitPK, DeleteItemAliasForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateItemDescriptionResult> createItemDescription(UserVisitPK userVisitPK, CreateItemDescriptionForm form);
    
    CommandResult<GetItemDescriptionResult> getItemDescription(UserVisitPK userVisitPK, GetItemDescriptionForm form);
    
    CommandResult<GetItemDescriptionsResult> getItemDescriptions(UserVisitPK userVisitPK, GetItemDescriptionsForm form);
    
    CommandResult<EditItemDescriptionResult> editItemDescription(UserVisitPK userVisitPK, EditItemDescriptionForm form);
    
    CommandResult<VoidResult> deleteItemDescription(UserVisitPK userVisitPK, DeleteItemDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemPriceType(UserVisitPK userVisitPK, CreateItemPriceTypeForm form);
    
    CommandResult<GetItemPriceTypeResult> getItemPriceType(UserVisitPK userVisitPK, GetItemPriceTypeForm form);
    
    CommandResult<GetItemPriceTypesResult> getItemPriceTypes(UserVisitPK userVisitPK, GetItemPriceTypesForm form);
    
    CommandResult<GetItemPriceTypeChoicesResult> getItemPriceTypeChoices(UserVisitPK userVisitPK, GetItemPriceTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemPriceTypeDescription(UserVisitPK userVisitPK, CreateItemPriceTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemPrice(UserVisitPK userVisitPK, CreateItemPriceForm form);
    
    CommandResult<GetItemPriceResult> getItemPrice(UserVisitPK userVisitPK, GetItemPriceForm form);

    CommandResult<GetItemPricesResult> getItemPrices(UserVisitPK userVisitPK, GetItemPricesForm form);
    
    CommandResult<EditItemPriceResult> editItemPrice(UserVisitPK userVisitPK, EditItemPriceForm form);
    
    CommandResult<VoidResult> deleteItemPrice(UserVisitPK userVisitPK, DeleteItemPriceForm form);

    // --------------------------------------------------------------------------------
    //   Item Volume Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemVolumeTypeResult> createItemVolumeType(UserVisitPK userVisitPK, CreateItemVolumeTypeForm form);

    CommandResult<GetItemVolumeTypeChoicesResult> getItemVolumeTypeChoices(UserVisitPK userVisitPK, GetItemVolumeTypeChoicesForm form);

    CommandResult<GetItemVolumeTypeResult> getItemVolumeType(UserVisitPK userVisitPK, GetItemVolumeTypeForm form);

    CommandResult<GetItemVolumeTypesResult> getItemVolumeTypes(UserVisitPK userVisitPK, GetItemVolumeTypesForm form);

    CommandResult<VoidResult> setDefaultItemVolumeType(UserVisitPK userVisitPK, SetDefaultItemVolumeTypeForm form);

    CommandResult<EditItemVolumeTypeResult> editItemVolumeType(UserVisitPK userVisitPK, EditItemVolumeTypeForm form);

    CommandResult<VoidResult> deleteItemVolumeType(UserVisitPK userVisitPK, DeleteItemVolumeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Volume Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createItemVolumeTypeDescription(UserVisitPK userVisitPK, CreateItemVolumeTypeDescriptionForm form);

    CommandResult<GetItemVolumeTypeDescriptionResult> getItemVolumeTypeDescription(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionForm form);

    CommandResult<GetItemVolumeTypeDescriptionsResult> getItemVolumeTypeDescriptions(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionsForm form);

    CommandResult<EditItemVolumeTypeDescriptionResult> editItemVolumeTypeDescription(UserVisitPK userVisitPK, EditItemVolumeTypeDescriptionForm form);

    CommandResult<VoidResult> deleteItemVolumeTypeDescription(UserVisitPK userVisitPK, DeleteItemVolumeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemVolume(UserVisitPK userVisitPK, CreateItemVolumeForm form);
    
    CommandResult<GetItemVolumeResult> getItemVolume(UserVisitPK userVisitPK, GetItemVolumeForm form);

    CommandResult<GetItemVolumesResult> getItemVolumes(UserVisitPK userVisitPK, GetItemVolumesForm form);

    CommandResult<EditItemVolumeResult> editItemVolume(UserVisitPK userVisitPK, EditItemVolumeForm form);
    
    CommandResult<VoidResult> deleteItemVolume(UserVisitPK userVisitPK, DeleteItemVolumeForm form);

    // --------------------------------------------------------------------------------
    //   Item Weight Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateItemWeightTypeResult> createItemWeightType(UserVisitPK userVisitPK, CreateItemWeightTypeForm form);

    CommandResult<GetItemWeightTypeChoicesResult> getItemWeightTypeChoices(UserVisitPK userVisitPK, GetItemWeightTypeChoicesForm form);

    CommandResult<GetItemWeightTypeResult> getItemWeightType(UserVisitPK userVisitPK, GetItemWeightTypeForm form);

    CommandResult<GetItemWeightTypesResult> getItemWeightTypes(UserVisitPK userVisitPK, GetItemWeightTypesForm form);

    CommandResult<VoidResult> setDefaultItemWeightType(UserVisitPK userVisitPK, SetDefaultItemWeightTypeForm form);

    CommandResult<EditItemWeightTypeResult> editItemWeightType(UserVisitPK userVisitPK, EditItemWeightTypeForm form);

    CommandResult<VoidResult> deleteItemWeightType(UserVisitPK userVisitPK, DeleteItemWeightTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Weight Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createItemWeightTypeDescription(UserVisitPK userVisitPK, CreateItemWeightTypeDescriptionForm form);

    CommandResult<GetItemWeightTypeDescriptionResult> getItemWeightTypeDescription(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionForm form);

    CommandResult<GetItemWeightTypeDescriptionsResult> getItemWeightTypeDescriptions(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionsForm form);

    CommandResult<EditItemWeightTypeDescriptionResult> editItemWeightTypeDescription(UserVisitPK userVisitPK, EditItemWeightTypeDescriptionForm form);

    CommandResult<VoidResult> deleteItemWeightTypeDescription(UserVisitPK userVisitPK, DeleteItemWeightTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemWeight(UserVisitPK userVisitPK, CreateItemWeightForm form);
    
    CommandResult<GetItemWeightResult> getItemWeight(UserVisitPK userVisitPK, GetItemWeightForm form);

    CommandResult<GetItemWeightsResult> getItemWeights(UserVisitPK userVisitPK, GetItemWeightsForm form);

    CommandResult<EditItemWeightResult> editItemWeight(UserVisitPK userVisitPK, EditItemWeightForm form);
    
    CommandResult<VoidResult> deleteItemWeight(UserVisitPK userVisitPK, DeleteItemWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemCountryOfOrigin(UserVisitPK userVisitPK, CreateItemCountryOfOriginForm form);
    
    CommandResult<GetItemCountryOfOriginResult> getItemCountryOfOrigin(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form);

    CommandResult<GetItemCountryOfOriginsResult> getItemCountryOfOrigins(UserVisitPK userVisitPK, GetItemCountryOfOriginsForm form);

    CommandResult<EditItemCountryOfOriginResult> editItemCountryOfOrigin(UserVisitPK userVisitPK, EditItemCountryOfOriginForm form);
    
    CommandResult<VoidResult> deleteItemCountryOfOrigin(UserVisitPK userVisitPK, DeleteItemCountryOfOriginForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemKitMember(UserVisitPK userVisitPK, CreateItemKitMemberForm form);
    
    CommandResult<GetItemKitMemberResult> getItemKitMember(UserVisitPK userVisitPK, GetItemKitMemberForm form);

    CommandResult<GetItemKitMembersResult> getItemKitMembers(UserVisitPK userVisitPK, GetItemKitMembersForm form);

    CommandResult<VoidResult> deleteItemKitMember(UserVisitPK userVisitPK, DeleteItemKitMemberForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemPackCheckRequirement(UserVisitPK userVisitPK, CreateItemPackCheckRequirementForm form);
    
    CommandResult<GetItemPackCheckRequirementResult> getItemPackCheckRequirement(UserVisitPK userVisitPK, GetItemPackCheckRequirementForm form);
    
    CommandResult<GetItemPackCheckRequirementsResult> getItemPackCheckRequirements(UserVisitPK userVisitPK, GetItemPackCheckRequirementsForm form);
    
    CommandResult<EditItemPackCheckRequirementResult> editItemPackCheckRequirement(UserVisitPK userVisitPK, EditItemPackCheckRequirementForm form);

    CommandResult<VoidResult> deleteItemPackCheckRequirement(UserVisitPK userVisitPK, DeleteItemPackCheckRequirementForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------
    
    CommandResult<GetItemShippingTimeResult> getItemShippingTime(UserVisitPK userVisitPK, GetItemShippingTimeForm form);

    CommandResult<GetItemShippingTimesResult> getItemShippingTimes(UserVisitPK userVisitPK, GetItemShippingTimesForm form);

    CommandResult<EditItemShippingTimeResult> editItemShippingTime(UserVisitPK userVisitPK, EditItemShippingTimeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form);
    
    CommandResult<GetItemUnitCustomerTypeLimitResult> getItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitForm form);

    CommandResult<GetItemUnitCustomerTypeLimitsResult> getItemUnitCustomerTypeLimits(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitsForm form);

    CommandResult<EditItemUnitCustomerTypeLimitResult> editItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, EditItemUnitCustomerTypeLimitForm form);

    CommandResult<VoidResult> deleteItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, DeleteItemUnitCustomerTypeLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemUnitLimit(UserVisitPK userVisitPK, CreateItemUnitLimitForm form);
    
    CommandResult<GetItemUnitLimitResult> getItemUnitLimit(UserVisitPK userVisitPK, GetItemUnitLimitForm form);

    CommandResult<GetItemUnitLimitsResult> getItemUnitLimits(UserVisitPK userVisitPK, GetItemUnitLimitsForm form);

    CommandResult<EditItemUnitLimitResult> editItemUnitLimit(UserVisitPK userVisitPK, EditItemUnitLimitForm form);
    
    CommandResult<VoidResult> deleteItemUnitLimit(UserVisitPK userVisitPK, DeleteItemUnitLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createItemUnitPriceLimit(UserVisitPK userVisitPK, CreateItemUnitPriceLimitForm form);

    CommandResult<GetItemUnitPriceLimitResult> getItemUnitPriceLimit(UserVisitPK userVisitPK, GetItemUnitPriceLimitForm form);
    
    CommandResult<GetItemUnitPriceLimitsResult> getItemUnitPriceLimits(UserVisitPK userVisitPK, GetItemUnitPriceLimitsForm form);

    CommandResult<EditItemUnitPriceLimitResult> editItemUnitPriceLimit(UserVisitPK userVisitPK, EditItemUnitPriceLimitForm form);
    
    CommandResult<VoidResult> deleteItemUnitPriceLimit(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    CommandResult<CreateRelatedItemTypeResult> createRelatedItemType(UserVisitPK userVisitPK, CreateRelatedItemTypeForm form);

    CommandResult<GetRelatedItemTypeChoicesResult> getRelatedItemTypeChoices(UserVisitPK userVisitPK, GetRelatedItemTypeChoicesForm form);

    CommandResult<GetRelatedItemTypeResult> getRelatedItemType(UserVisitPK userVisitPK, GetRelatedItemTypeForm form);

    CommandResult<GetRelatedItemTypesResult> getRelatedItemTypes(UserVisitPK userVisitPK, GetRelatedItemTypesForm form);

    CommandResult<VoidResult> setDefaultRelatedItemType(UserVisitPK userVisitPK, SetDefaultRelatedItemTypeForm form);

    CommandResult<EditRelatedItemTypeResult> editRelatedItemType(UserVisitPK userVisitPK, EditRelatedItemTypeForm form);

    CommandResult<VoidResult> deleteRelatedItemType(UserVisitPK userVisitPK, DeleteRelatedItemTypeForm form);

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createRelatedItemTypeDescription(UserVisitPK userVisitPK, CreateRelatedItemTypeDescriptionForm form);

    CommandResult<GetRelatedItemTypeDescriptionResult> getRelatedItemTypeDescription(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionForm form);

    CommandResult<GetRelatedItemTypeDescriptionsResult> getRelatedItemTypeDescriptions(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionsForm form);

    CommandResult<EditRelatedItemTypeDescriptionResult> editRelatedItemTypeDescription(UserVisitPK userVisitPK, EditRelatedItemTypeDescriptionForm form);

    CommandResult<VoidResult> deleteRelatedItemTypeDescription(UserVisitPK userVisitPK, DeleteRelatedItemTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateRelatedItemResult> createRelatedItem(UserVisitPK userVisitPK, CreateRelatedItemForm form);
    
    CommandResult<GetRelatedItemResult> getRelatedItem(UserVisitPK userVisitPK, GetRelatedItemForm form);

    CommandResult<GetRelatedItemsResult> getRelatedItems(UserVisitPK userVisitPK, GetRelatedItemsForm form);

    CommandResult<EditRelatedItemResult> editRelatedItem(UserVisitPK userVisitPK, EditRelatedItemForm form);

    CommandResult<VoidResult> deleteRelatedItem(UserVisitPK userVisitPK, DeleteRelatedItemForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodesResult> getHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodesForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeResult> getHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeChoicesResult> getHarmonizedTariffScheduleCodeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeChoicesForm form);

    CommandResult<VoidResult> setDefaultHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeResult> editHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form);

    CommandResult<VoidResult> deleteHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeTranslationsResult> getHarmonizedTariffScheduleCodeTranslations(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeTranslationResult> getHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeTranslationResult> editHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult<VoidResult> deleteHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Units
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitsResult> getHarmonizedTariffScheduleCodeUnits(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitResult> getHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitChoicesResult> getHarmonizedTariffScheduleCodeUnitChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitChoicesForm form);

    CommandResult<VoidResult> setDefaultHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUnitResult> editHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult<VoidResult> deleteHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Unit Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitDescriptionsResult> getHarmonizedTariffScheduleCodeUnitDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUnitDescriptionResult> getHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUnitDescriptionResult> editHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult<VoidResult> deleteHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypesResult> getHarmonizedTariffScheduleCodeUseTypes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypesForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeResult> getHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeChoicesResult> getHarmonizedTariffScheduleCodeUseTypeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUseTypeResult> editHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult<VoidResult> deleteHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeDescriptionsResult> getHarmonizedTariffScheduleCodeUseTypeDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionsForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseTypeDescriptionResult> getHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult<EditHarmonizedTariffScheduleCodeUseTypeDescriptionResult> editHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult<VoidResult> deleteHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUsesResult> getHarmonizedTariffScheduleCodeUses(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUsesForm form);

    CommandResult<GetHarmonizedTariffScheduleCodeUseResult> getHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form);

    CommandResult<VoidResult> deleteHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseForm form);

    // -------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateItemHarmonizedTariffScheduleCodeForm form);

    CommandResult<GetItemHarmonizedTariffScheduleCodesResult> getItemHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodesForm form);

    CommandResult<GetItemHarmonizedTariffScheduleCodeResult> getItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodeForm form);

    CommandResult<EditItemHarmonizedTariffScheduleCodeResult> editItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form);

    CommandResult<VoidResult> deleteItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteItemHarmonizedTariffScheduleCodeForm form);

}
