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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ItemTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    ItemTypeTransferCache itemTypeTransferCache;
    
    @Inject
    ItemDeliveryTypeTransferCache itemDeliveryTypeTransferCache;
    
    @Inject
    ItemInventoryTypeTransferCache itemInventoryTypeTransferCache;
    
    @Inject
    RelatedItemTypeTransferCache relatedItemTypeTransferCache;
    
    @Inject
    RelatedItemTypeDescriptionTransferCache relatedItemTypeDescriptionTransferCache;
    
    @Inject
    RelatedItemTransferCache relatedItemTransferCache;
    
    @Inject
    ItemUseTypeTransferCache itemUseTypeTransferCache;
    
    @Inject
    ItemPriceTypeTransferCache itemPriceTypeTransferCache;
    
    @Inject
    ItemTransferCache itemTransferCache;
    
    @Inject
    ItemUnitOfMeasureTypeTransferCache itemUnitOfMeasureTypeTransferCache;
    
    @Inject
    ItemPriceTransferCache itemPriceTransferCache;
    
    @Inject
    ItemVolumeTypeTransferCache itemVolumeTypeTransferCache;
    
    @Inject
    ItemVolumeTypeDescriptionTransferCache itemVolumeTypeDescriptionTransferCache;
    
    @Inject
    ItemVolumeTransferCache itemVolumeTransferCache;
    
    @Inject
    ItemShippingTimeTransferCache itemShippingTimeTransferCache;
    
    @Inject
    ItemAliasTransferCache itemAliasTransferCache;
    
    @Inject
    ItemAliasChecksumTypeTransferCache itemAliasChecksumTypeTransferCache;
    
    @Inject
    ItemAliasTypeTransferCache itemAliasTypeTransferCache;
    
    @Inject
    ItemAliasTypeDescriptionTransferCache itemAliasTypeDescriptionTransferCache;
    
    @Inject
    ItemDescriptionTransferCache itemDescriptionTransferCache;
    
    @Inject
    ItemDescriptionTypeTransferCache itemDescriptionTypeTransferCache;
    
    @Inject
    ItemDescriptionTypeDescriptionTransferCache itemDescriptionTypeDescriptionTransferCache;
    
    @Inject
    ItemDescriptionTypeUseTypeTransferCache itemDescriptionTypeUseTypeTransferCache;
    
    @Inject
    ItemDescriptionTypeUseTypeDescriptionTransferCache itemDescriptionTypeUseTypeDescriptionTransferCache;
    
    @Inject
    ItemDescriptionTypeUseTransferCache itemDescriptionTypeUseTransferCache;
    
    @Inject
    ItemWeightTypeTransferCache itemWeightTypeTransferCache;
    
    @Inject
    ItemWeightTypeDescriptionTransferCache itemWeightTypeDescriptionTransferCache;
    
    @Inject
    ItemWeightTransferCache itemWeightTransferCache;
    
    @Inject
    ItemCategoryDescriptionTransferCache itemCategoryDescriptionTransferCache;
    
    @Inject
    ItemCategoryTransferCache itemCategoryTransferCache;
    
    @Inject
    ItemKitMemberTransferCache itemKitMemberTransferCache;
    
    @Inject
    ItemCountryOfOriginTransferCache itemCountryOfOriginTransferCache;
    
    @Inject
    ItemPackCheckRequirementTransferCache itemPackCheckRequirementTransferCache;
    
    @Inject
    ItemUnitCustomerTypeLimitTransferCache itemUnitCustomerTypeLimitTransferCache;
    
    @Inject
    ItemUnitLimitTransferCache itemUnitLimitTransferCache;
    
    @Inject
    ItemUnitPriceLimitTransferCache itemUnitPriceLimitTransferCache;
    
    @Inject
    ItemImageTypeTransferCache itemImageTypeTransferCache;
    
    @Inject
    ItemImageTypeDescriptionTransferCache itemImageTypeDescriptionTransferCache;
    
    @Inject
    HarmonizedTariffScheduleCodeTransferCache harmonizedTariffScheduleCodeTransferCache;
    
    @Inject
    HarmonizedTariffScheduleCodeTranslationTransferCache harmonizedTariffScheduleCodeTranslationTransferCache;
    
    @Inject
    HarmonizedTariffScheduleCodeUnitTransferCache harmonizedTariffScheduleCodeUnitTransferCache;
    
    @Inject
    HarmonizedTariffScheduleCodeUnitDescriptionTransferCache harmonizedTariffScheduleCodeUnitDescriptionTransferCache;
    
    @Inject
    HarmonizedTariffScheduleCodeUseTypeTransferCache harmonizedTariffScheduleCodeUseTypeTransferCache;
    
    @Inject
    HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;
    
    @Inject
    HarmonizedTariffScheduleCodeUseTransferCache harmonizedTariffScheduleCodeUseTransferCache;
    
    @Inject
    ItemHarmonizedTariffScheduleCodeTransferCache itemHarmonizedTariffScheduleCodeTransferCache;

    /** Creates a new instance of ItemTransferCaches */
    protected ItemTransferCaches() {
        super();
    }
    
    public ItemTypeTransferCache getItemTypeTransferCache() {
        return itemTypeTransferCache;
    }
    
    public ItemDeliveryTypeTransferCache getItemDeliveryTypeTransferCache() {
        return itemDeliveryTypeTransferCache;
    }
    
    public ItemInventoryTypeTransferCache getItemInventoryTypeTransferCache() {
        return itemInventoryTypeTransferCache;
    }
    
    public RelatedItemTypeTransferCache getRelatedItemTypeTransferCache() {
        return relatedItemTypeTransferCache;
    }

    public RelatedItemTypeDescriptionTransferCache getRelatedItemTypeDescriptionTransferCache() {
        return relatedItemTypeDescriptionTransferCache;
    }

    public RelatedItemTransferCache getRelatedItemTransferCache() {
        return relatedItemTransferCache;
    }
    
    public ItemUseTypeTransferCache getItemUseTypeTransferCache() {
        return itemUseTypeTransferCache;
    }
    
    public ItemPriceTypeTransferCache getItemPriceTypeTransferCache() {
        return itemPriceTypeTransferCache;
    }
    
    public ItemTransferCache getItemTransferCache() {
        return itemTransferCache;
    }
    
    public ItemUnitOfMeasureTypeTransferCache getItemUnitOfMeasureTypeTransferCache() {
        return itemUnitOfMeasureTypeTransferCache;
    }
    
    public ItemPriceTransferCache getItemPriceTransferCache() {
        return itemPriceTransferCache;
    }

    public ItemVolumeTypeTransferCache getItemVolumeTypeTransferCache() {
        return itemVolumeTypeTransferCache;
    }

    public ItemVolumeTypeDescriptionTransferCache getItemVolumeTypeDescriptionTransferCache() {
        return itemVolumeTypeDescriptionTransferCache;
    }

    public ItemVolumeTransferCache getItemVolumeTransferCache() {
        return itemVolumeTransferCache;
    }
    
    public ItemShippingTimeTransferCache getItemShippingTimeTransferCache() {
        return itemShippingTimeTransferCache;
    }
    
    public ItemAliasTransferCache getItemAliasTransferCache() {
        return itemAliasTransferCache;
    }
    
    public ItemAliasChecksumTypeTransferCache getItemAliasChecksumTypeTransferCache() {
        return itemAliasChecksumTypeTransferCache;
    }

    public ItemAliasTypeTransferCache getItemAliasTypeTransferCache() {
        return itemAliasTypeTransferCache;
    }

    public ItemAliasTypeDescriptionTransferCache getItemAliasTypeDescriptionTransferCache() {
        return itemAliasTypeDescriptionTransferCache;
    }
    
    public ItemDescriptionTransferCache getItemDescriptionTransferCache() {
        return itemDescriptionTransferCache;
    }
    
    public ItemDescriptionTypeTransferCache getItemDescriptionTypeTransferCache() {
        return itemDescriptionTypeTransferCache;
    }

    public ItemDescriptionTypeDescriptionTransferCache getItemDescriptionTypeDescriptionTransferCache() {
        return itemDescriptionTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTypeTransferCache getItemDescriptionTypeUseTypeTransferCache() {
        return itemDescriptionTypeUseTypeTransferCache;
    }

    public ItemDescriptionTypeUseTypeDescriptionTransferCache getItemDescriptionTypeUseTypeDescriptionTransferCache() {
        return itemDescriptionTypeUseTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTransferCache getItemDescriptionTypeUseTransferCache() {
        return itemDescriptionTypeUseTransferCache;
    }

    public ItemWeightTypeTransferCache getItemWeightTypeTransferCache() {
        return itemWeightTypeTransferCache;
    }

    public ItemWeightTypeDescriptionTransferCache getItemWeightTypeDescriptionTransferCache() {
        return itemWeightTypeDescriptionTransferCache;
    }

    public ItemWeightTransferCache getItemWeightTransferCache() {
        return itemWeightTransferCache;
    }

    public ItemCategoryDescriptionTransferCache getItemCategoryDescriptionTransferCache() {
        return itemCategoryDescriptionTransferCache;
    }
    
    public ItemCategoryTransferCache getItemCategoryTransferCache() {
        return itemCategoryTransferCache;
    }
    
    public ItemKitMemberTransferCache getItemKitMemberTransferCache() {
        return itemKitMemberTransferCache;
    }
    
    public ItemCountryOfOriginTransferCache getItemCountryOfOriginTransferCache() {
        return itemCountryOfOriginTransferCache;
    }
    
    public ItemPackCheckRequirementTransferCache getItemPackCheckRequirementTransferCache() {
        return itemPackCheckRequirementTransferCache;
    }
    
    public ItemUnitCustomerTypeLimitTransferCache getItemUnitCustomerTypeLimitTransferCache() {
        return itemUnitCustomerTypeLimitTransferCache;
    }
    
    public ItemUnitLimitTransferCache getItemUnitLimitTransferCache() {
        return itemUnitLimitTransferCache;
    }
    
    public ItemUnitPriceLimitTransferCache getItemUnitPriceLimitTransferCache() {
        return itemUnitPriceLimitTransferCache;
    }
    
    public ItemImageTypeTransferCache getItemImageTypeTransferCache() {
        return itemImageTypeTransferCache;
    }

    public ItemImageTypeDescriptionTransferCache getItemImageTypeDescriptionTransferCache() {
        return itemImageTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeTransferCache getHarmonizedTariffScheduleCodeTransferCache() {
        return harmonizedTariffScheduleCodeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeTranslationTransferCache getHarmonizedTariffScheduleCodeTranslationTransferCache() {
        return harmonizedTariffScheduleCodeTranslationTransferCache;
    }

    public HarmonizedTariffScheduleCodeUnitTransferCache getHarmonizedTariffScheduleCodeUnitTransferCache() {
        return harmonizedTariffScheduleCodeUnitTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUnitDescriptionTransferCache getHarmonizedTariffScheduleCodeUnitDescriptionTransferCache() {
        return harmonizedTariffScheduleCodeUnitDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTypeTransferCache getHarmonizedTariffScheduleCodeUseTypeTransferCache() {
        return harmonizedTariffScheduleCodeUseTypeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache getHarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache() {
        return harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTransferCache getHarmonizedTariffScheduleCodeUseTransferCache() {
        return harmonizedTariffScheduleCodeUseTransferCache;
    }
    
    public ItemHarmonizedTariffScheduleCodeTransferCache getItemHarmonizedTariffScheduleCodeTransferCache() {
        return itemHarmonizedTariffScheduleCodeTransferCache;
    }

}
