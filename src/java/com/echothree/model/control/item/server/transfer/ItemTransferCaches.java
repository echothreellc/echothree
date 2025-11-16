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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
    protected ItemTransferCaches() {
        super();
    }
    
    public ItemTypeTransferCache getItemTypeTransferCache() {
        if(itemTypeTransferCache == null) {
            itemTypeTransferCache = CDI.current().select(ItemTypeTransferCache.class).get();
        }
        
        return itemTypeTransferCache;
    }
    
    public ItemDeliveryTypeTransferCache getItemDeliveryTypeTransferCache() {
        if(itemDeliveryTypeTransferCache == null) {
            itemDeliveryTypeTransferCache = CDI.current().select(ItemDeliveryTypeTransferCache.class).get();
        }
        
        return itemDeliveryTypeTransferCache;
    }
    
    public ItemInventoryTypeTransferCache getItemInventoryTypeTransferCache() {
        if(itemInventoryTypeTransferCache == null) {
            itemInventoryTypeTransferCache = CDI.current().select(ItemInventoryTypeTransferCache.class).get();
        }
        
        return itemInventoryTypeTransferCache;
    }
    
    public RelatedItemTypeTransferCache getRelatedItemTypeTransferCache() {
        if(relatedItemTypeTransferCache == null) {
            relatedItemTypeTransferCache = CDI.current().select(RelatedItemTypeTransferCache.class).get();
        }

        return relatedItemTypeTransferCache;
    }

    public RelatedItemTypeDescriptionTransferCache getRelatedItemTypeDescriptionTransferCache() {
        if(relatedItemTypeDescriptionTransferCache == null) {
            relatedItemTypeDescriptionTransferCache = CDI.current().select(RelatedItemTypeDescriptionTransferCache.class).get();
        }

        return relatedItemTypeDescriptionTransferCache;
    }

    public RelatedItemTransferCache getRelatedItemTransferCache() {
        if(relatedItemTransferCache == null) {
            relatedItemTransferCache = CDI.current().select(RelatedItemTransferCache.class).get();
        }
        
        return relatedItemTransferCache;
    }
    
    public ItemUseTypeTransferCache getItemUseTypeTransferCache() {
        if(itemUseTypeTransferCache == null) {
            itemUseTypeTransferCache = CDI.current().select(ItemUseTypeTransferCache.class).get();
        }
        
        return itemUseTypeTransferCache;
    }
    
    public ItemPriceTypeTransferCache getItemPriceTypeTransferCache() {
        if(itemPriceTypeTransferCache == null) {
            itemPriceTypeTransferCache = CDI.current().select(ItemPriceTypeTransferCache.class).get();
        }
        
        return itemPriceTypeTransferCache;
    }
    
    public ItemTransferCache getItemTransferCache() {
        if(itemTransferCache == null) {
            itemTransferCache = CDI.current().select(ItemTransferCache.class).get();
        }
        
        return itemTransferCache;
    }
    
    public ItemUnitOfMeasureTypeTransferCache getItemUnitOfMeasureTypeTransferCache() {
        if(itemUnitOfMeasureTypeTransferCache == null) {
            itemUnitOfMeasureTypeTransferCache = CDI.current().select(ItemUnitOfMeasureTypeTransferCache.class).get();
        }
        
        return itemUnitOfMeasureTypeTransferCache;
    }
    
    public ItemPriceTransferCache getItemPriceTransferCache() {
        if(itemPriceTransferCache == null) {
            itemPriceTransferCache = CDI.current().select(ItemPriceTransferCache.class).get();
        }
        
        return itemPriceTransferCache;
    }

    public ItemVolumeTypeTransferCache getItemVolumeTypeTransferCache() {
        if(itemVolumeTypeTransferCache == null) {
            itemVolumeTypeTransferCache = CDI.current().select(ItemVolumeTypeTransferCache.class).get();
        }

        return itemVolumeTypeTransferCache;
    }

    public ItemVolumeTypeDescriptionTransferCache getItemVolumeTypeDescriptionTransferCache() {
        if(itemVolumeTypeDescriptionTransferCache == null) {
            itemVolumeTypeDescriptionTransferCache = CDI.current().select(ItemVolumeTypeDescriptionTransferCache.class).get();
        }

        return itemVolumeTypeDescriptionTransferCache;
    }

    public ItemVolumeTransferCache getItemVolumeTransferCache() {
        if(itemVolumeTransferCache == null) {
            itemVolumeTransferCache = CDI.current().select(ItemVolumeTransferCache.class).get();
        }
        
        return itemVolumeTransferCache;
    }
    
    public ItemShippingTimeTransferCache getItemShippingTimeTransferCache() {
        if(itemShippingTimeTransferCache == null) {
            itemShippingTimeTransferCache = CDI.current().select(ItemShippingTimeTransferCache.class).get();
        }
        
        return itemShippingTimeTransferCache;
    }
    
    public ItemAliasTransferCache getItemAliasTransferCache() {
        if(itemAliasTransferCache == null) {
            itemAliasTransferCache = CDI.current().select(ItemAliasTransferCache.class).get();
        }
        
        return itemAliasTransferCache;
    }
    
    public ItemAliasChecksumTypeTransferCache getItemAliasChecksumTypeTransferCache() {
        if(itemAliasChecksumTypeTransferCache == null) {
            itemAliasChecksumTypeTransferCache = CDI.current().select(ItemAliasChecksumTypeTransferCache.class).get();
        }

        return itemAliasChecksumTypeTransferCache;
    }

    public ItemAliasTypeTransferCache getItemAliasTypeTransferCache() {
        if(itemAliasTypeTransferCache == null) {
            itemAliasTypeTransferCache = CDI.current().select(ItemAliasTypeTransferCache.class).get();
        }

        return itemAliasTypeTransferCache;
    }

    public ItemAliasTypeDescriptionTransferCache getItemAliasTypeDescriptionTransferCache() {
        if(itemAliasTypeDescriptionTransferCache == null) {
            itemAliasTypeDescriptionTransferCache = CDI.current().select(ItemAliasTypeDescriptionTransferCache.class).get();
        }
        
        return itemAliasTypeDescriptionTransferCache;
    }
    
    public ItemDescriptionTransferCache getItemDescriptionTransferCache() {
        if(itemDescriptionTransferCache == null) {
            itemDescriptionTransferCache = CDI.current().select(ItemDescriptionTransferCache.class).get();
        }
        
        return itemDescriptionTransferCache;
    }
    
    public ItemDescriptionTypeTransferCache getItemDescriptionTypeTransferCache() {
        if(itemDescriptionTypeTransferCache == null) {
            itemDescriptionTypeTransferCache = CDI.current().select(ItemDescriptionTypeTransferCache.class).get();
        }

        return itemDescriptionTypeTransferCache;
    }

    public ItemDescriptionTypeDescriptionTransferCache getItemDescriptionTypeDescriptionTransferCache() {
        if(itemDescriptionTypeDescriptionTransferCache == null) {
            itemDescriptionTypeDescriptionTransferCache = CDI.current().select(ItemDescriptionTypeDescriptionTransferCache.class).get();
        }

        return itemDescriptionTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTypeTransferCache getItemDescriptionTypeUseTypeTransferCache() {
        if(itemDescriptionTypeUseTypeTransferCache == null) {
            itemDescriptionTypeUseTypeTransferCache = CDI.current().select(ItemDescriptionTypeUseTypeTransferCache.class).get();
        }

        return itemDescriptionTypeUseTypeTransferCache;
    }

    public ItemDescriptionTypeUseTypeDescriptionTransferCache getItemDescriptionTypeUseTypeDescriptionTransferCache() {
        if(itemDescriptionTypeUseTypeDescriptionTransferCache == null) {
            itemDescriptionTypeUseTypeDescriptionTransferCache = CDI.current().select(ItemDescriptionTypeUseTypeDescriptionTransferCache.class).get();
        }

        return itemDescriptionTypeUseTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTransferCache getItemDescriptionTypeUseTransferCache() {
        if(itemDescriptionTypeUseTransferCache == null) {
            itemDescriptionTypeUseTransferCache = CDI.current().select(ItemDescriptionTypeUseTransferCache.class).get();
        }

        return itemDescriptionTypeUseTransferCache;
    }

    public ItemWeightTypeTransferCache getItemWeightTypeTransferCache() {
        if(itemWeightTypeTransferCache == null) {
            itemWeightTypeTransferCache = CDI.current().select(ItemWeightTypeTransferCache.class).get();
        }

        return itemWeightTypeTransferCache;
    }

    public ItemWeightTypeDescriptionTransferCache getItemWeightTypeDescriptionTransferCache() {
        if(itemWeightTypeDescriptionTransferCache == null) {
            itemWeightTypeDescriptionTransferCache = CDI.current().select(ItemWeightTypeDescriptionTransferCache.class).get();
        }

        return itemWeightTypeDescriptionTransferCache;
    }

    public ItemWeightTransferCache getItemWeightTransferCache() {
        if(itemWeightTransferCache == null) {
            itemWeightTransferCache = CDI.current().select(ItemWeightTransferCache.class).get();
        }

        return itemWeightTransferCache;
    }

    public ItemCategoryDescriptionTransferCache getItemCategoryDescriptionTransferCache() {
        if(itemCategoryDescriptionTransferCache == null) {
            itemCategoryDescriptionTransferCache = CDI.current().select(ItemCategoryDescriptionTransferCache.class).get();
        }
        
        return itemCategoryDescriptionTransferCache;
    }
    
    public ItemCategoryTransferCache getItemCategoryTransferCache() {
        if(itemCategoryTransferCache == null) {
            itemCategoryTransferCache = CDI.current().select(ItemCategoryTransferCache.class).get();
        }
        
        return itemCategoryTransferCache;
    }
    
    public ItemKitMemberTransferCache getItemKitMemberTransferCache() {
        if(itemKitMemberTransferCache == null) {
            itemKitMemberTransferCache = CDI.current().select(ItemKitMemberTransferCache.class).get();
        }
        
        return itemKitMemberTransferCache;
    }
    
    public ItemCountryOfOriginTransferCache getItemCountryOfOriginTransferCache() {
        if(itemCountryOfOriginTransferCache == null) {
            itemCountryOfOriginTransferCache = CDI.current().select(ItemCountryOfOriginTransferCache.class).get();
        }
        
        return itemCountryOfOriginTransferCache;
    }
    
    public ItemPackCheckRequirementTransferCache getItemPackCheckRequirementTransferCache() {
        if(itemPackCheckRequirementTransferCache == null) {
            itemPackCheckRequirementTransferCache = CDI.current().select(ItemPackCheckRequirementTransferCache.class).get();
        }
        
        return itemPackCheckRequirementTransferCache;
    }
    
    public ItemUnitCustomerTypeLimitTransferCache getItemUnitCustomerTypeLimitTransferCache() {
        if(itemUnitCustomerTypeLimitTransferCache == null) {
            itemUnitCustomerTypeLimitTransferCache = CDI.current().select(ItemUnitCustomerTypeLimitTransferCache.class).get();
        }
        
        return itemUnitCustomerTypeLimitTransferCache;
    }
    
    public ItemUnitLimitTransferCache getItemUnitLimitTransferCache() {
        if(itemUnitLimitTransferCache == null) {
            itemUnitLimitTransferCache = CDI.current().select(ItemUnitLimitTransferCache.class).get();
        }
        
        return itemUnitLimitTransferCache;
    }
    
    public ItemUnitPriceLimitTransferCache getItemUnitPriceLimitTransferCache() {
        if(itemUnitPriceLimitTransferCache == null) {
            itemUnitPriceLimitTransferCache = CDI.current().select(ItemUnitPriceLimitTransferCache.class).get();
        }
        
        return itemUnitPriceLimitTransferCache;
    }
    
    public ItemImageTypeTransferCache getItemImageTypeTransferCache() {
        if(itemImageTypeTransferCache == null) {
            itemImageTypeTransferCache = CDI.current().select(ItemImageTypeTransferCache.class).get();
        }

        return itemImageTypeTransferCache;
    }

    public ItemImageTypeDescriptionTransferCache getItemImageTypeDescriptionTransferCache() {
        if(itemImageTypeDescriptionTransferCache == null) {
            itemImageTypeDescriptionTransferCache = CDI.current().select(ItemImageTypeDescriptionTransferCache.class).get();
        }

        return itemImageTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeTransferCache getHarmonizedTariffScheduleCodeTransferCache() {
        if(harmonizedTariffScheduleCodeTransferCache == null) {
            harmonizedTariffScheduleCodeTransferCache = CDI.current().select(HarmonizedTariffScheduleCodeTransferCache.class).get();
        }

        return harmonizedTariffScheduleCodeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeTranslationTransferCache getHarmonizedTariffScheduleCodeTranslationTransferCache() {
        if(harmonizedTariffScheduleCodeTranslationTransferCache == null) {
            harmonizedTariffScheduleCodeTranslationTransferCache = CDI.current().select(HarmonizedTariffScheduleCodeTranslationTransferCache.class).get();
        }

        return harmonizedTariffScheduleCodeTranslationTransferCache;
    }

    public HarmonizedTariffScheduleCodeUnitTransferCache getHarmonizedTariffScheduleCodeUnitTransferCache() {
        if(harmonizedTariffScheduleCodeUnitTransferCache == null) {
            harmonizedTariffScheduleCodeUnitTransferCache = CDI.current().select(HarmonizedTariffScheduleCodeUnitTransferCache.class).get();
        }

        return harmonizedTariffScheduleCodeUnitTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUnitDescriptionTransferCache getHarmonizedTariffScheduleCodeUnitDescriptionTransferCache() {
        if(harmonizedTariffScheduleCodeUnitDescriptionTransferCache == null) {
            harmonizedTariffScheduleCodeUnitDescriptionTransferCache = CDI.current().select(HarmonizedTariffScheduleCodeUnitDescriptionTransferCache.class).get();
        }

        return harmonizedTariffScheduleCodeUnitDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTypeTransferCache getHarmonizedTariffScheduleCodeUseTypeTransferCache() {
        if(harmonizedTariffScheduleCodeUseTypeTransferCache == null) {
            harmonizedTariffScheduleCodeUseTypeTransferCache = CDI.current().select(HarmonizedTariffScheduleCodeUseTypeTransferCache.class).get();
        }

        return harmonizedTariffScheduleCodeUseTypeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache getHarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache() {
        if(harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache == null) {
            harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache = CDI.current().select(HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache.class).get();
        }

        return harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTransferCache getHarmonizedTariffScheduleCodeUseTransferCache() {
        if(harmonizedTariffScheduleCodeUseTransferCache == null) {
            harmonizedTariffScheduleCodeUseTransferCache = CDI.current().select(HarmonizedTariffScheduleCodeUseTransferCache.class).get();
        }

        return harmonizedTariffScheduleCodeUseTransferCache;
    }
    
    public ItemHarmonizedTariffScheduleCodeTransferCache getItemHarmonizedTariffScheduleCodeTransferCache() {
        if(itemHarmonizedTariffScheduleCodeTransferCache == null) {
            itemHarmonizedTariffScheduleCodeTransferCache = CDI.current().select(ItemHarmonizedTariffScheduleCodeTransferCache.class).get();
        }

        return itemHarmonizedTariffScheduleCodeTransferCache;
    }

}
