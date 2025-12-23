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
    
    CommandResult createItemType(UserVisitPK userVisitPK, CreateItemTypeForm form);
    
    CommandResult getItemType(UserVisitPK userVisitPK, GetItemTypeForm form);
    
    CommandResult getItemTypes(UserVisitPK userVisitPK, GetItemTypesForm form);
    
    CommandResult getItemTypeChoices(UserVisitPK userVisitPK, GetItemTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemTypeDescription(UserVisitPK userVisitPK, CreateItemTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------
    
    CommandResult createItemDeliveryType(UserVisitPK userVisitPK, CreateItemDeliveryTypeForm form);
    
    CommandResult getItemDeliveryType(UserVisitPK userVisitPK, GetItemDeliveryTypeForm form);
    
    CommandResult getItemDeliveryTypes(UserVisitPK userVisitPK, GetItemDeliveryTypesForm form);
    
    CommandResult getItemDeliveryTypeChoices(UserVisitPK userVisitPK, GetItemDeliveryTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemDeliveryTypeDescription(UserVisitPK userVisitPK, CreateItemDeliveryTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------
    
    CommandResult createItemInventoryType(UserVisitPK userVisitPK, CreateItemInventoryTypeForm form);
    
    CommandResult getItemInventoryType(UserVisitPK userVisitPK, GetItemInventoryTypeForm form);
    
    CommandResult getItemInventoryTypes(UserVisitPK userVisitPK, GetItemInventoryTypesForm form);
    
    CommandResult getItemInventoryTypeChoices(UserVisitPK userVisitPK, GetItemInventoryTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemInventoryTypeDescription(UserVisitPK userVisitPK, CreateItemInventoryTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult createItemUseType(UserVisitPK userVisitPK, CreateItemUseTypeForm form);
    
    CommandResult getItemUseType(UserVisitPK userVisitPK, GetItemUseTypeForm form);
    
    CommandResult getItemUseTypes(UserVisitPK userVisitPK, GetItemUseTypesForm form);
    
    CommandResult getItemUseTypeChoices(UserVisitPK userVisitPK, GetItemUseTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemUseTypeDescription(UserVisitPK userVisitPK, CreateItemUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------
    
    CommandResult createItemCategory(UserVisitPK userVisitPK, CreateItemCategoryForm form);

    CommandResult getItemCategoryChoices(UserVisitPK userVisitPK, GetItemCategoryChoicesForm form);

    CommandResult getItemCategory(UserVisitPK userVisitPK, GetItemCategoryForm form);

    CommandResult getItemCategories(UserVisitPK userVisitPK, GetItemCategoriesForm form);

    CommandResult setDefaultItemCategory(UserVisitPK userVisitPK, SetDefaultItemCategoryForm form);

    CommandResult editItemCategory(UserVisitPK userVisitPK, EditItemCategoryForm form);

    CommandResult deleteItemCategory(UserVisitPK userVisitPK, DeleteItemCategoryForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemCategoryDescription(UserVisitPK userVisitPK, CreateItemCategoryDescriptionForm form);

    CommandResult getItemCategoryDescription(UserVisitPK userVisitPK, GetItemCategoryDescriptionForm form);

    CommandResult getItemCategoryDescriptions(UserVisitPK userVisitPK, GetItemCategoryDescriptionsForm form);

    CommandResult editItemCategoryDescription(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form);

    CommandResult deleteItemCategoryDescription(UserVisitPK userVisitPK, DeleteItemCategoryDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // -------------------------------------------------------------------------

    CommandResult createItemAliasChecksumType(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeForm form);

    CommandResult getItemAliasChecksumTypes(UserVisitPK userVisitPK, GetItemAliasChecksumTypesForm form);

    CommandResult getItemAliasChecksumType(UserVisitPK userVisitPK, GetItemAliasChecksumTypeForm form);

    CommandResult getItemAliasChecksumTypeChoices(UserVisitPK userVisitPK, GetItemAliasChecksumTypeChoicesForm form);

    // -------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createItemAliasChecksumTypeDescription(UserVisitPK userVisitPK, CreateItemAliasChecksumTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------
    
    CommandResult createItemAliasType(UserVisitPK userVisitPK, CreateItemAliasTypeForm form);

    CommandResult getItemAliasTypeChoices(UserVisitPK userVisitPK, GetItemAliasTypeChoicesForm form);

    CommandResult getItemAliasType(UserVisitPK userVisitPK, GetItemAliasTypeForm form);

    CommandResult getItemAliasTypes(UserVisitPK userVisitPK, GetItemAliasTypesForm form);

    CommandResult setDefaultItemAliasType(UserVisitPK userVisitPK, SetDefaultItemAliasTypeForm form);

    CommandResult editItemAliasType(UserVisitPK userVisitPK, EditItemAliasTypeForm form);

    CommandResult deleteItemAliasType(UserVisitPK userVisitPK, DeleteItemAliasTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemAliasTypeDescription(UserVisitPK userVisitPK, CreateItemAliasTypeDescriptionForm form);

    CommandResult getItemAliasTypeDescription(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionForm form);

    CommandResult getItemAliasTypeDescriptions(UserVisitPK userVisitPK, GetItemAliasTypeDescriptionsForm form);

    CommandResult editItemAliasTypeDescription(UserVisitPK userVisitPK, EditItemAliasTypeDescriptionForm form);

    CommandResult deleteItemAliasTypeDescription(UserVisitPK userVisitPK, DeleteItemAliasTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    CommandResult createItemDescriptionType(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form);

    CommandResult getItemDescriptionTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeChoicesForm form);

    CommandResult getItemDescriptionType(UserVisitPK userVisitPK, GetItemDescriptionTypeForm form);

    CommandResult getItemDescriptionTypes(UserVisitPK userVisitPK, GetItemDescriptionTypesForm form);

    CommandResult setDefaultItemDescriptionType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeForm form);

    CommandResult editItemDescriptionType(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form);

    CommandResult deleteItemDescriptionType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createItemDescriptionTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeDescriptionForm form);

    CommandResult getItemDescriptionTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionForm form);

    CommandResult getItemDescriptionTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeDescriptionsForm form);

    CommandResult editItemDescriptionTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form);

    CommandResult deleteItemDescriptionTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    CommandResult createItemDescriptionTypeUseType(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeForm form);

    CommandResult getItemDescriptionTypeUseTypeChoices(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeChoicesForm form);

    CommandResult getItemDescriptionTypeUseType(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeForm form);

    CommandResult getItemDescriptionTypeUseTypes(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypesForm form);

    CommandResult setDefaultItemDescriptionTypeUseType(UserVisitPK userVisitPK, SetDefaultItemDescriptionTypeUseTypeForm form);

    CommandResult editItemDescriptionTypeUseType(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form);

    CommandResult deleteItemDescriptionTypeUseType(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult getItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult getItemDescriptionTypeUseTypeDescriptions(UserVisitPK userVisitPK, GetItemDescriptionTypeUseTypeDescriptionsForm form);

    CommandResult editItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeDescriptionForm form);

    CommandResult deleteItemDescriptionTypeUseTypeDescription(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    public CommandResult createItemDescriptionTypeUse(UserVisitPK userVisitPK, CreateItemDescriptionTypeUseForm form);

    public CommandResult getItemDescriptionTypeUse(UserVisitPK userVisitPK, GetItemDescriptionTypeUseForm form);

    public CommandResult getItemDescriptionTypeUses(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form);

    public CommandResult deleteItemDescriptionTypeUse(UserVisitPK userVisitPK, DeleteItemDescriptionTypeUseForm form);

    // --------------------------------------------------------------------------------
    //   Item Image Types
    // --------------------------------------------------------------------------------

    CommandResult createItemImageType(UserVisitPK userVisitPK, CreateItemImageTypeForm form);

    CommandResult getItemImageTypeChoices(UserVisitPK userVisitPK, GetItemImageTypeChoicesForm form);

    CommandResult getItemImageType(UserVisitPK userVisitPK, GetItemImageTypeForm form);

    CommandResult getItemImageTypes(UserVisitPK userVisitPK, GetItemImageTypesForm form);

    CommandResult setDefaultItemImageType(UserVisitPK userVisitPK, SetDefaultItemImageTypeForm form);

    CommandResult editItemImageType(UserVisitPK userVisitPK, EditItemImageTypeForm form);

    CommandResult deleteItemImageType(UserVisitPK userVisitPK, DeleteItemImageTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Image Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createItemImageTypeDescription(UserVisitPK userVisitPK, CreateItemImageTypeDescriptionForm form);

    CommandResult getItemImageTypeDescription(UserVisitPK userVisitPK, GetItemImageTypeDescriptionForm form);

    CommandResult getItemImageTypeDescriptions(UserVisitPK userVisitPK, GetItemImageTypeDescriptionsForm form);

    CommandResult editItemImageTypeDescription(UserVisitPK userVisitPK, EditItemImageTypeDescriptionForm form);

    CommandResult deleteItemImageTypeDescription(UserVisitPK userVisitPK, DeleteItemImageTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------
    
    CommandResult createItem(UserVisitPK userVisitPK, CreateItemForm form);
    
    CommandResult getItemStatusChoices(UserVisitPK userVisitPK, GetItemStatusChoicesForm form);
    
    CommandResult setItemStatus(UserVisitPK userVisitPK, SetItemStatusForm form);

    CommandResult getItem(UserVisitPK userVisitPK, GetItemForm form);

    CommandResult getItems(UserVisitPK userVisitPK, GetItemsForm form);

    CommandResult editItem(UserVisitPK userVisitPK, EditItemForm form);
    
    // -------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // -------------------------------------------------------------------------
    
    CommandResult createItemUnitOfMeasureType(UserVisitPK userVisitPK, CreateItemUnitOfMeasureTypeForm form);
    
    CommandResult getItemUnitOfMeasureType(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypeForm form);

    CommandResult getItemUnitOfMeasureTypes(UserVisitPK userVisitPK, GetItemUnitOfMeasureTypesForm form);
    
    CommandResult setDefaultItemUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultItemUnitOfMeasureTypeForm form);
    
    CommandResult editItemUnitOfMeasureType(UserVisitPK userVisitPK, EditItemUnitOfMeasureTypeForm form);
    
    CommandResult deleteItemUnitOfMeasureType(UserVisitPK userVisitPK, DeleteItemUnitOfMeasureTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    CommandResult createItemAlias(UserVisitPK userVisitPK, CreateItemAliasForm form);
    
    CommandResult getItemAlias(UserVisitPK userVisitPK, GetItemAliasForm form);

    CommandResult getItemAliases(UserVisitPK userVisitPK, GetItemAliasesForm form);
    
    CommandResult editItemAlias(UserVisitPK userVisitPK, EditItemAliasForm form);
    
    CommandResult deleteItemAlias(UserVisitPK userVisitPK, DeleteItemAliasForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemDescription(UserVisitPK userVisitPK, CreateItemDescriptionForm form);
    
    CommandResult getItemDescription(UserVisitPK userVisitPK, GetItemDescriptionForm form);
    
    CommandResult getItemDescriptions(UserVisitPK userVisitPK, GetItemDescriptionsForm form);
    
    CommandResult editItemDescription(UserVisitPK userVisitPK, EditItemDescriptionForm form);
    
    CommandResult deleteItemDescription(UserVisitPK userVisitPK, DeleteItemDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------
    
    CommandResult createItemPriceType(UserVisitPK userVisitPK, CreateItemPriceTypeForm form);
    
    CommandResult getItemPriceType(UserVisitPK userVisitPK, GetItemPriceTypeForm form);
    
    CommandResult getItemPriceTypes(UserVisitPK userVisitPK, GetItemPriceTypesForm form);
    
    CommandResult getItemPriceTypeChoices(UserVisitPK userVisitPK, GetItemPriceTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createItemPriceTypeDescription(UserVisitPK userVisitPK, CreateItemPriceTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------
    
    CommandResult createItemPrice(UserVisitPK userVisitPK, CreateItemPriceForm form);
    
    CommandResult getItemPrice(UserVisitPK userVisitPK, GetItemPriceForm form);

    CommandResult getItemPrices(UserVisitPK userVisitPK, GetItemPricesForm form);
    
    CommandResult editItemPrice(UserVisitPK userVisitPK, EditItemPriceForm form);
    
    CommandResult deleteItemPrice(UserVisitPK userVisitPK, DeleteItemPriceForm form);

    // --------------------------------------------------------------------------------
    //   Item Volume Types
    // --------------------------------------------------------------------------------

    CommandResult createItemVolumeType(UserVisitPK userVisitPK, CreateItemVolumeTypeForm form);

    CommandResult getItemVolumeTypeChoices(UserVisitPK userVisitPK, GetItemVolumeTypeChoicesForm form);

    CommandResult getItemVolumeType(UserVisitPK userVisitPK, GetItemVolumeTypeForm form);

    CommandResult getItemVolumeTypes(UserVisitPK userVisitPK, GetItemVolumeTypesForm form);

    CommandResult setDefaultItemVolumeType(UserVisitPK userVisitPK, SetDefaultItemVolumeTypeForm form);

    CommandResult editItemVolumeType(UserVisitPK userVisitPK, EditItemVolumeTypeForm form);

    CommandResult deleteItemVolumeType(UserVisitPK userVisitPK, DeleteItemVolumeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Volume Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createItemVolumeTypeDescription(UserVisitPK userVisitPK, CreateItemVolumeTypeDescriptionForm form);

    CommandResult getItemVolumeTypeDescription(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionForm form);

    CommandResult getItemVolumeTypeDescriptions(UserVisitPK userVisitPK, GetItemVolumeTypeDescriptionsForm form);

    CommandResult editItemVolumeTypeDescription(UserVisitPK userVisitPK, EditItemVolumeTypeDescriptionForm form);

    CommandResult deleteItemVolumeTypeDescription(UserVisitPK userVisitPK, DeleteItemVolumeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult createItemVolume(UserVisitPK userVisitPK, CreateItemVolumeForm form);
    
    CommandResult getItemVolume(UserVisitPK userVisitPK, GetItemVolumeForm form);

    CommandResult getItemVolumes(UserVisitPK userVisitPK, GetItemVolumesForm form);

    CommandResult editItemVolume(UserVisitPK userVisitPK, EditItemVolumeForm form);
    
    CommandResult deleteItemVolume(UserVisitPK userVisitPK, DeleteItemVolumeForm form);

    // --------------------------------------------------------------------------------
    //   Item Weight Types
    // --------------------------------------------------------------------------------

    CommandResult createItemWeightType(UserVisitPK userVisitPK, CreateItemWeightTypeForm form);

    CommandResult getItemWeightTypeChoices(UserVisitPK userVisitPK, GetItemWeightTypeChoicesForm form);

    CommandResult getItemWeightType(UserVisitPK userVisitPK, GetItemWeightTypeForm form);

    CommandResult getItemWeightTypes(UserVisitPK userVisitPK, GetItemWeightTypesForm form);

    CommandResult setDefaultItemWeightType(UserVisitPK userVisitPK, SetDefaultItemWeightTypeForm form);

    CommandResult editItemWeightType(UserVisitPK userVisitPK, EditItemWeightTypeForm form);

    CommandResult deleteItemWeightType(UserVisitPK userVisitPK, DeleteItemWeightTypeForm form);

    // --------------------------------------------------------------------------------
    //   Item Weight Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createItemWeightTypeDescription(UserVisitPK userVisitPK, CreateItemWeightTypeDescriptionForm form);

    CommandResult getItemWeightTypeDescription(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionForm form);

    CommandResult getItemWeightTypeDescriptions(UserVisitPK userVisitPK, GetItemWeightTypeDescriptionsForm form);

    CommandResult editItemWeightTypeDescription(UserVisitPK userVisitPK, EditItemWeightTypeDescriptionForm form);

    CommandResult deleteItemWeightTypeDescription(UserVisitPK userVisitPK, DeleteItemWeightTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    CommandResult createItemWeight(UserVisitPK userVisitPK, CreateItemWeightForm form);
    
    CommandResult getItemWeight(UserVisitPK userVisitPK, GetItemWeightForm form);

    CommandResult getItemWeights(UserVisitPK userVisitPK, GetItemWeightsForm form);

    CommandResult editItemWeight(UserVisitPK userVisitPK, EditItemWeightForm form);
    
    CommandResult deleteItemWeight(UserVisitPK userVisitPK, DeleteItemWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    CommandResult createItemCountryOfOrigin(UserVisitPK userVisitPK, CreateItemCountryOfOriginForm form);
    
    CommandResult getItemCountryOfOrigin(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form);

    CommandResult getItemCountryOfOrigins(UserVisitPK userVisitPK, GetItemCountryOfOriginsForm form);

    CommandResult editItemCountryOfOrigin(UserVisitPK userVisitPK, EditItemCountryOfOriginForm form);
    
    CommandResult deleteItemCountryOfOrigin(UserVisitPK userVisitPK, DeleteItemCountryOfOriginForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------
    
    CommandResult createItemKitMember(UserVisitPK userVisitPK, CreateItemKitMemberForm form);
    
    CommandResult getItemKitMember(UserVisitPK userVisitPK, GetItemKitMemberForm form);

    CommandResult getItemKitMembers(UserVisitPK userVisitPK, GetItemKitMembersForm form);

    CommandResult deleteItemKitMember(UserVisitPK userVisitPK, DeleteItemKitMemberForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------
    
    CommandResult createItemPackCheckRequirement(UserVisitPK userVisitPK, CreateItemPackCheckRequirementForm form);
    
    CommandResult getItemPackCheckRequirement(UserVisitPK userVisitPK, GetItemPackCheckRequirementForm form);
    
    CommandResult getItemPackCheckRequirements(UserVisitPK userVisitPK, GetItemPackCheckRequirementsForm form);
    
    CommandResult editItemPackCheckRequirement(UserVisitPK userVisitPK, EditItemPackCheckRequirementForm form);

    CommandResult deleteItemPackCheckRequirement(UserVisitPK userVisitPK, DeleteItemPackCheckRequirementForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------
    
    CommandResult getItemShippingTime(UserVisitPK userVisitPK, GetItemShippingTimeForm form);

    CommandResult getItemShippingTimes(UserVisitPK userVisitPK, GetItemShippingTimesForm form);

    CommandResult editItemShippingTime(UserVisitPK userVisitPK, EditItemShippingTimeForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------
    
    CommandResult createItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form);
    
    CommandResult getItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitForm form);

    CommandResult getItemUnitCustomerTypeLimits(UserVisitPK userVisitPK, GetItemUnitCustomerTypeLimitsForm form);

    CommandResult editItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, EditItemUnitCustomerTypeLimitForm form);

    CommandResult deleteItemUnitCustomerTypeLimit(UserVisitPK userVisitPK, DeleteItemUnitCustomerTypeLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------
    
    CommandResult createItemUnitLimit(UserVisitPK userVisitPK, CreateItemUnitLimitForm form);
    
    CommandResult getItemUnitLimit(UserVisitPK userVisitPK, GetItemUnitLimitForm form);

    CommandResult getItemUnitLimits(UserVisitPK userVisitPK, GetItemUnitLimitsForm form);

    CommandResult editItemUnitLimit(UserVisitPK userVisitPK, EditItemUnitLimitForm form);
    
    CommandResult deleteItemUnitLimit(UserVisitPK userVisitPK, DeleteItemUnitLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------
    
    CommandResult createItemUnitPriceLimit(UserVisitPK userVisitPK, CreateItemUnitPriceLimitForm form);

    CommandResult getItemUnitPriceLimit(UserVisitPK userVisitPK, GetItemUnitPriceLimitForm form);
    
    CommandResult getItemUnitPriceLimits(UserVisitPK userVisitPK, GetItemUnitPriceLimitsForm form);

    CommandResult editItemUnitPriceLimit(UserVisitPK userVisitPK, EditItemUnitPriceLimitForm form);
    
    CommandResult deleteItemUnitPriceLimit(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form);
    
    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    CommandResult createRelatedItemType(UserVisitPK userVisitPK, CreateRelatedItemTypeForm form);

    CommandResult getRelatedItemTypeChoices(UserVisitPK userVisitPK, GetRelatedItemTypeChoicesForm form);

    CommandResult getRelatedItemType(UserVisitPK userVisitPK, GetRelatedItemTypeForm form);

    CommandResult getRelatedItemTypes(UserVisitPK userVisitPK, GetRelatedItemTypesForm form);

    CommandResult setDefaultRelatedItemType(UserVisitPK userVisitPK, SetDefaultRelatedItemTypeForm form);

    CommandResult editRelatedItemType(UserVisitPK userVisitPK, EditRelatedItemTypeForm form);

    CommandResult deleteRelatedItemType(UserVisitPK userVisitPK, DeleteRelatedItemTypeForm form);

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createRelatedItemTypeDescription(UserVisitPK userVisitPK, CreateRelatedItemTypeDescriptionForm form);

    CommandResult getRelatedItemTypeDescription(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionForm form);

    CommandResult getRelatedItemTypeDescriptions(UserVisitPK userVisitPK, GetRelatedItemTypeDescriptionsForm form);

    CommandResult editRelatedItemTypeDescription(UserVisitPK userVisitPK, EditRelatedItemTypeDescriptionForm form);

    CommandResult deleteRelatedItemTypeDescription(UserVisitPK userVisitPK, DeleteRelatedItemTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------
    
    CommandResult createRelatedItem(UserVisitPK userVisitPK, CreateRelatedItemForm form);
    
    CommandResult getRelatedItem(UserVisitPK userVisitPK, GetRelatedItemForm form);

    CommandResult getRelatedItems(UserVisitPK userVisitPK, GetRelatedItemsForm form);

    CommandResult editRelatedItem(UserVisitPK userVisitPK, EditRelatedItemForm form);

    CommandResult deleteRelatedItem(UserVisitPK userVisitPK, DeleteRelatedItemForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    CommandResult createHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeForm form);

    CommandResult getHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodesForm form);

    CommandResult getHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeForm form);

    CommandResult getHarmonizedTariffScheduleCodeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeChoicesForm form);

    CommandResult setDefaultHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeForm form);

    CommandResult editHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form);

    CommandResult deleteHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult createHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult getHarmonizedTariffScheduleCodeTranslations(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationsForm form);

    CommandResult getHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult editHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form);

    CommandResult deleteHarmonizedTariffScheduleCodeTranslation(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Units
    // -------------------------------------------------------------------------

    CommandResult createHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult getHarmonizedTariffScheduleCodeUnits(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitsForm form);

    CommandResult getHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult getHarmonizedTariffScheduleCodeUnitChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitChoicesForm form);

    CommandResult setDefaultHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult editHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitForm form);

    CommandResult deleteHarmonizedTariffScheduleCodeUnit(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Unit Descriptions
    // -------------------------------------------------------------------------

    CommandResult createHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult getHarmonizedTariffScheduleCodeUnitDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionsForm form);

    CommandResult getHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult editHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    CommandResult deleteHarmonizedTariffScheduleCodeUnitDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUnitDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // -------------------------------------------------------------------------

    CommandResult createHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult getHarmonizedTariffScheduleCodeUseTypes(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypesForm form);

    CommandResult getHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult getHarmonizedTariffScheduleCodeUseTypeChoices(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeChoicesForm form);

    CommandResult setDefaultHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, SetDefaultHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult editHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeForm form);

    CommandResult deleteHarmonizedTariffScheduleCodeUseType(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult getHarmonizedTariffScheduleCodeUseTypeDescriptions(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionsForm form);

    CommandResult getHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult editHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    CommandResult deleteHarmonizedTariffScheduleCodeUseTypeDescription(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // -------------------------------------------------------------------------

    CommandResult createHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, CreateHarmonizedTariffScheduleCodeUseForm form);

    CommandResult getHarmonizedTariffScheduleCodeUses(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUsesForm form);

    CommandResult getHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form);

    CommandResult deleteHarmonizedTariffScheduleCodeUse(UserVisitPK userVisitPK, DeleteHarmonizedTariffScheduleCodeUseForm form);

    // -------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // -------------------------------------------------------------------------

    CommandResult createItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, CreateItemHarmonizedTariffScheduleCodeForm form);

    CommandResult getItemHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodesForm form);

    CommandResult getItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, GetItemHarmonizedTariffScheduleCodeForm form);

    CommandResult editItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form);

    CommandResult deleteItemHarmonizedTariffScheduleCode(UserVisitPK userVisitPK, DeleteItemHarmonizedTariffScheduleCodeForm form);

}
