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

public class ItemTransferCaches
        extends BaseTransferCaches {
    
    protected ItemTypeTransferCache itemTypeTransferCache;
    protected ItemDeliveryTypeTransferCache itemDeliveryTypeTransferCache;
    protected ItemInventoryTypeTransferCache itemInventoryTypeTransferCache;
    protected RelatedItemTypeTransferCache relatedItemTypeTransferCache;
    protected RelatedItemTypeDescriptionTransferCache relatedItemTypeDescriptionTransferCache;
    protected RelatedItemTransferCache relatedItemTransferCache;
    protected ItemUseTypeTransferCache itemUseTypeTransferCache;
    protected ItemPriceTypeTransferCache itemPriceTypeTransferCache;
    protected ItemTransferCache itemTransferCache;
    protected ItemUnitOfMeasureTypeTransferCache itemUnitOfMeasureTypeTransferCache;
    protected ItemPriceTransferCache itemPriceTransferCache;
    protected ItemVolumeTypeTransferCache itemVolumeTypeTransferCache;
    protected ItemVolumeTypeDescriptionTransferCache itemVolumeTypeDescriptionTransferCache;
    protected ItemVolumeTransferCache itemVolumeTransferCache;
    protected ItemShippingTimeTransferCache itemShippingTimeTransferCache;
    protected ItemAliasTransferCache itemAliasTransferCache;
    protected ItemAliasChecksumTypeTransferCache itemAliasChecksumTypeTransferCache;
    protected ItemAliasTypeTransferCache itemAliasTypeTransferCache;
    protected ItemAliasTypeDescriptionTransferCache itemAliasTypeDescriptionTransferCache;
    protected ItemDescriptionTransferCache itemDescriptionTransferCache;
    protected ItemDescriptionTypeTransferCache itemDescriptionTypeTransferCache;
    protected ItemDescriptionTypeDescriptionTransferCache itemDescriptionTypeDescriptionTransferCache;
    protected ItemDescriptionTypeUseTypeTransferCache itemDescriptionTypeUseTypeTransferCache;
    protected ItemDescriptionTypeUseTypeDescriptionTransferCache itemDescriptionTypeUseTypeDescriptionTransferCache;
    protected ItemDescriptionTypeUseTransferCache itemDescriptionTypeUseTransferCache;
    protected ItemWeightTypeTransferCache itemWeightTypeTransferCache;
    protected ItemWeightTypeDescriptionTransferCache itemWeightTypeDescriptionTransferCache;
    protected ItemWeightTransferCache itemWeightTransferCache;
    protected ItemCategoryDescriptionTransferCache itemCategoryDescriptionTransferCache;
    protected ItemCategoryTransferCache itemCategoryTransferCache;
    protected ItemKitMemberTransferCache itemKitMemberTransferCache;
    protected ItemCountryOfOriginTransferCache itemCountryOfOriginTransferCache;
    protected ItemPackCheckRequirementTransferCache itemPackCheckRequirementTransferCache;
    protected ItemUnitCustomerTypeLimitTransferCache itemUnitCustomerTypeLimitTransferCache;
    protected ItemUnitLimitTransferCache itemUnitLimitTransferCache;
    protected ItemUnitPriceLimitTransferCache itemUnitPriceLimitTransferCache;
    protected ItemImageTypeTransferCache itemImageTypeTransferCache;
    protected ItemImageTypeDescriptionTransferCache itemImageTypeDescriptionTransferCache;
    protected HarmonizedTariffScheduleCodeTransferCache harmonizedTariffScheduleCodeTransferCache;
    protected HarmonizedTariffScheduleCodeTranslationTransferCache harmonizedTariffScheduleCodeTranslationTransferCache;
    protected HarmonizedTariffScheduleCodeUnitTransferCache harmonizedTariffScheduleCodeUnitTransferCache;
    protected HarmonizedTariffScheduleCodeUnitDescriptionTransferCache harmonizedTariffScheduleCodeUnitDescriptionTransferCache;
    protected HarmonizedTariffScheduleCodeUseTypeTransferCache harmonizedTariffScheduleCodeUseTypeTransferCache;
    protected HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;
    protected HarmonizedTariffScheduleCodeUseTransferCache harmonizedTariffScheduleCodeUseTransferCache;
    protected ItemHarmonizedTariffScheduleCodeTransferCache itemHarmonizedTariffScheduleCodeTransferCache;
    
    /** Creates a new instance of ItemTransferCaches */
    public ItemTransferCaches() {
        super();
    }
    
    public ItemTypeTransferCache getItemTypeTransferCache() {
        if(itemTypeTransferCache == null) {
            itemTypeTransferCache = new ItemTypeTransferCache();
        }
        
        return itemTypeTransferCache;
    }
    
    public ItemDeliveryTypeTransferCache getItemDeliveryTypeTransferCache() {
        if(itemDeliveryTypeTransferCache == null) {
            itemDeliveryTypeTransferCache = new ItemDeliveryTypeTransferCache();
        }
        
        return itemDeliveryTypeTransferCache;
    }
    
    public ItemInventoryTypeTransferCache getItemInventoryTypeTransferCache() {
        if(itemInventoryTypeTransferCache == null) {
            itemInventoryTypeTransferCache = new ItemInventoryTypeTransferCache();
        }
        
        return itemInventoryTypeTransferCache;
    }
    
    public RelatedItemTypeTransferCache getRelatedItemTypeTransferCache() {
        if(relatedItemTypeTransferCache == null) {
            relatedItemTypeTransferCache = new RelatedItemTypeTransferCache();
        }

        return relatedItemTypeTransferCache;
    }

    public RelatedItemTypeDescriptionTransferCache getRelatedItemTypeDescriptionTransferCache() {
        if(relatedItemTypeDescriptionTransferCache == null) {
            relatedItemTypeDescriptionTransferCache = new RelatedItemTypeDescriptionTransferCache();
        }

        return relatedItemTypeDescriptionTransferCache;
    }

    public RelatedItemTransferCache getRelatedItemTransferCache() {
        if(relatedItemTransferCache == null) {
            relatedItemTransferCache = new RelatedItemTransferCache();
        }
        
        return relatedItemTransferCache;
    }
    
    public ItemUseTypeTransferCache getItemUseTypeTransferCache() {
        if(itemUseTypeTransferCache == null) {
            itemUseTypeTransferCache = new ItemUseTypeTransferCache();
        }
        
        return itemUseTypeTransferCache;
    }
    
    public ItemPriceTypeTransferCache getItemPriceTypeTransferCache() {
        if(itemPriceTypeTransferCache == null) {
            itemPriceTypeTransferCache = new ItemPriceTypeTransferCache();
        }
        
        return itemPriceTypeTransferCache;
    }
    
    public ItemTransferCache getItemTransferCache() {
        if(itemTransferCache == null) {
            itemTransferCache = new ItemTransferCache();
        }
        
        return itemTransferCache;
    }
    
    public ItemUnitOfMeasureTypeTransferCache getItemUnitOfMeasureTypeTransferCache() {
        if(itemUnitOfMeasureTypeTransferCache == null) {
            itemUnitOfMeasureTypeTransferCache = new ItemUnitOfMeasureTypeTransferCache();
        }
        
        return itemUnitOfMeasureTypeTransferCache;
    }
    
    public ItemPriceTransferCache getItemPriceTransferCache() {
        if(itemPriceTransferCache == null) {
            itemPriceTransferCache = new ItemPriceTransferCache();
        }
        
        return itemPriceTransferCache;
    }

    public ItemVolumeTypeTransferCache getItemVolumeTypeTransferCache() {
        if(itemVolumeTypeTransferCache == null) {
            itemVolumeTypeTransferCache = new ItemVolumeTypeTransferCache();
        }

        return itemVolumeTypeTransferCache;
    }

    public ItemVolumeTypeDescriptionTransferCache getItemVolumeTypeDescriptionTransferCache() {
        if(itemVolumeTypeDescriptionTransferCache == null) {
            itemVolumeTypeDescriptionTransferCache = new ItemVolumeTypeDescriptionTransferCache();
        }

        return itemVolumeTypeDescriptionTransferCache;
    }

    public ItemVolumeTransferCache getItemVolumeTransferCache() {
        if(itemVolumeTransferCache == null) {
            itemVolumeTransferCache = new ItemVolumeTransferCache();
        }
        
        return itemVolumeTransferCache;
    }
    
    public ItemShippingTimeTransferCache getItemShippingTimeTransferCache() {
        if(itemShippingTimeTransferCache == null) {
            itemShippingTimeTransferCache = new ItemShippingTimeTransferCache();
        }
        
        return itemShippingTimeTransferCache;
    }
    
    public ItemAliasTransferCache getItemAliasTransferCache() {
        if(itemAliasTransferCache == null) {
            itemAliasTransferCache = new ItemAliasTransferCache();
        }
        
        return itemAliasTransferCache;
    }
    
    public ItemAliasChecksumTypeTransferCache getItemAliasChecksumTypeTransferCache() {
        if(itemAliasChecksumTypeTransferCache == null) {
            itemAliasChecksumTypeTransferCache = new ItemAliasChecksumTypeTransferCache();
        }

        return itemAliasChecksumTypeTransferCache;
    }

    public ItemAliasTypeTransferCache getItemAliasTypeTransferCache() {
        if(itemAliasTypeTransferCache == null) {
            itemAliasTypeTransferCache = new ItemAliasTypeTransferCache();
        }

        return itemAliasTypeTransferCache;
    }

    public ItemAliasTypeDescriptionTransferCache getItemAliasTypeDescriptionTransferCache() {
        if(itemAliasTypeDescriptionTransferCache == null) {
            itemAliasTypeDescriptionTransferCache = new ItemAliasTypeDescriptionTransferCache();
        }
        
        return itemAliasTypeDescriptionTransferCache;
    }
    
    public ItemDescriptionTransferCache getItemDescriptionTransferCache() {
        if(itemDescriptionTransferCache == null) {
            itemDescriptionTransferCache = new ItemDescriptionTransferCache();
        }
        
        return itemDescriptionTransferCache;
    }
    
    public ItemDescriptionTypeTransferCache getItemDescriptionTypeTransferCache() {
        if(itemDescriptionTypeTransferCache == null) {
            itemDescriptionTypeTransferCache = new ItemDescriptionTypeTransferCache();
        }

        return itemDescriptionTypeTransferCache;
    }

    public ItemDescriptionTypeDescriptionTransferCache getItemDescriptionTypeDescriptionTransferCache() {
        if(itemDescriptionTypeDescriptionTransferCache == null) {
            itemDescriptionTypeDescriptionTransferCache = new ItemDescriptionTypeDescriptionTransferCache();
        }

        return itemDescriptionTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTypeTransferCache getItemDescriptionTypeUseTypeTransferCache() {
        if(itemDescriptionTypeUseTypeTransferCache == null) {
            itemDescriptionTypeUseTypeTransferCache = new ItemDescriptionTypeUseTypeTransferCache();
        }

        return itemDescriptionTypeUseTypeTransferCache;
    }

    public ItemDescriptionTypeUseTypeDescriptionTransferCache getItemDescriptionTypeUseTypeDescriptionTransferCache() {
        if(itemDescriptionTypeUseTypeDescriptionTransferCache == null) {
            itemDescriptionTypeUseTypeDescriptionTransferCache = new ItemDescriptionTypeUseTypeDescriptionTransferCache();
        }

        return itemDescriptionTypeUseTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTransferCache getItemDescriptionTypeUseTransferCache() {
        if(itemDescriptionTypeUseTransferCache == null) {
            itemDescriptionTypeUseTransferCache = new ItemDescriptionTypeUseTransferCache();
        }

        return itemDescriptionTypeUseTransferCache;
    }

    public ItemWeightTypeTransferCache getItemWeightTypeTransferCache() {
        if(itemWeightTypeTransferCache == null) {
            itemWeightTypeTransferCache = new ItemWeightTypeTransferCache();
        }

        return itemWeightTypeTransferCache;
    }

    public ItemWeightTypeDescriptionTransferCache getItemWeightTypeDescriptionTransferCache() {
        if(itemWeightTypeDescriptionTransferCache == null) {
            itemWeightTypeDescriptionTransferCache = new ItemWeightTypeDescriptionTransferCache();
        }

        return itemWeightTypeDescriptionTransferCache;
    }

    public ItemWeightTransferCache getItemWeightTransferCache() {
        if(itemWeightTransferCache == null) {
            itemWeightTransferCache = new ItemWeightTransferCache();
        }

        return itemWeightTransferCache;
    }

    public ItemCategoryDescriptionTransferCache getItemCategoryDescriptionTransferCache() {
        if(itemCategoryDescriptionTransferCache == null) {
            itemCategoryDescriptionTransferCache = new ItemCategoryDescriptionTransferCache();
        }
        
        return itemCategoryDescriptionTransferCache;
    }
    
    public ItemCategoryTransferCache getItemCategoryTransferCache() {
        if(itemCategoryTransferCache == null) {
            itemCategoryTransferCache = new ItemCategoryTransferCache();
        }
        
        return itemCategoryTransferCache;
    }
    
    public ItemKitMemberTransferCache getItemKitMemberTransferCache() {
        if(itemKitMemberTransferCache == null) {
            itemKitMemberTransferCache = new ItemKitMemberTransferCache();
        }
        
        return itemKitMemberTransferCache;
    }
    
    public ItemCountryOfOriginTransferCache getItemCountryOfOriginTransferCache() {
        if(itemCountryOfOriginTransferCache == null) {
            itemCountryOfOriginTransferCache = new ItemCountryOfOriginTransferCache();
        }
        
        return itemCountryOfOriginTransferCache;
    }
    
    public ItemPackCheckRequirementTransferCache getItemPackCheckRequirementTransferCache() {
        if(itemPackCheckRequirementTransferCache == null) {
            itemPackCheckRequirementTransferCache = new ItemPackCheckRequirementTransferCache();
        }
        
        return itemPackCheckRequirementTransferCache;
    }
    
    public ItemUnitCustomerTypeLimitTransferCache getItemUnitCustomerTypeLimitTransferCache() {
        if(itemUnitCustomerTypeLimitTransferCache == null) {
            itemUnitCustomerTypeLimitTransferCache = new ItemUnitCustomerTypeLimitTransferCache();
        }
        
        return itemUnitCustomerTypeLimitTransferCache;
    }
    
    public ItemUnitLimitTransferCache getItemUnitLimitTransferCache() {
        if(itemUnitLimitTransferCache == null) {
            itemUnitLimitTransferCache = new ItemUnitLimitTransferCache();
        }
        
        return itemUnitLimitTransferCache;
    }
    
    public ItemUnitPriceLimitTransferCache getItemUnitPriceLimitTransferCache() {
        if(itemUnitPriceLimitTransferCache == null) {
            itemUnitPriceLimitTransferCache = new ItemUnitPriceLimitTransferCache();
        }
        
        return itemUnitPriceLimitTransferCache;
    }
    
    public ItemImageTypeTransferCache getItemImageTypeTransferCache() {
        if(itemImageTypeTransferCache == null) {
            itemImageTypeTransferCache = new ItemImageTypeTransferCache();
        }

        return itemImageTypeTransferCache;
    }

    public ItemImageTypeDescriptionTransferCache getItemImageTypeDescriptionTransferCache() {
        if(itemImageTypeDescriptionTransferCache == null) {
            itemImageTypeDescriptionTransferCache = new ItemImageTypeDescriptionTransferCache();
        }

        return itemImageTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeTransferCache getHarmonizedTariffScheduleCodeTransferCache() {
        if(harmonizedTariffScheduleCodeTransferCache == null) {
            harmonizedTariffScheduleCodeTransferCache = new HarmonizedTariffScheduleCodeTransferCache();
        }

        return harmonizedTariffScheduleCodeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeTranslationTransferCache getHarmonizedTariffScheduleCodeTranslationTransferCache() {
        if(harmonizedTariffScheduleCodeTranslationTransferCache == null) {
            harmonizedTariffScheduleCodeTranslationTransferCache = new HarmonizedTariffScheduleCodeTranslationTransferCache();
        }

        return harmonizedTariffScheduleCodeTranslationTransferCache;
    }

    public HarmonizedTariffScheduleCodeUnitTransferCache getHarmonizedTariffScheduleCodeUnitTransferCache() {
        if(harmonizedTariffScheduleCodeUnitTransferCache == null) {
            harmonizedTariffScheduleCodeUnitTransferCache = new HarmonizedTariffScheduleCodeUnitTransferCache();
        }

        return harmonizedTariffScheduleCodeUnitTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUnitDescriptionTransferCache getHarmonizedTariffScheduleCodeUnitDescriptionTransferCache() {
        if(harmonizedTariffScheduleCodeUnitDescriptionTransferCache == null) {
            harmonizedTariffScheduleCodeUnitDescriptionTransferCache = new HarmonizedTariffScheduleCodeUnitDescriptionTransferCache();
        }

        return harmonizedTariffScheduleCodeUnitDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTypeTransferCache getHarmonizedTariffScheduleCodeUseTypeTransferCache() {
        if(harmonizedTariffScheduleCodeUseTypeTransferCache == null) {
            harmonizedTariffScheduleCodeUseTypeTransferCache = new HarmonizedTariffScheduleCodeUseTypeTransferCache();
        }

        return harmonizedTariffScheduleCodeUseTypeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache getHarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache() {
        if(harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache == null) {
            harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache = new HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache();
        }

        return harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTransferCache getHarmonizedTariffScheduleCodeUseTransferCache() {
        if(harmonizedTariffScheduleCodeUseTransferCache == null) {
            harmonizedTariffScheduleCodeUseTransferCache = new HarmonizedTariffScheduleCodeUseTransferCache();
        }

        return harmonizedTariffScheduleCodeUseTransferCache;
    }
    
    public ItemHarmonizedTariffScheduleCodeTransferCache getItemHarmonizedTariffScheduleCodeTransferCache() {
        if(itemHarmonizedTariffScheduleCodeTransferCache == null) {
            itemHarmonizedTariffScheduleCodeTransferCache = new ItemHarmonizedTariffScheduleCodeTransferCache();
        }

        return itemHarmonizedTariffScheduleCodeTransferCache;
    }

}
