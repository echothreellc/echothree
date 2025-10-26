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

import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ItemTransferCaches
        extends BaseTransferCaches {
    
    protected ItemControl itemControl;
    
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
    public ItemTransferCaches(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit);
        
        this.itemControl = itemControl;
    }
    
    public ItemTypeTransferCache getItemTypeTransferCache() {
        if(itemTypeTransferCache == null) {
            itemTypeTransferCache = new ItemTypeTransferCache(userVisit, itemControl);
        }
        
        return itemTypeTransferCache;
    }
    
    public ItemDeliveryTypeTransferCache getItemDeliveryTypeTransferCache() {
        if(itemDeliveryTypeTransferCache == null) {
            itemDeliveryTypeTransferCache = new ItemDeliveryTypeTransferCache(userVisit, itemControl);
        }
        
        return itemDeliveryTypeTransferCache;
    }
    
    public ItemInventoryTypeTransferCache getItemInventoryTypeTransferCache() {
        if(itemInventoryTypeTransferCache == null) {
            itemInventoryTypeTransferCache = new ItemInventoryTypeTransferCache(userVisit, itemControl);
        }
        
        return itemInventoryTypeTransferCache;
    }
    
    public RelatedItemTypeTransferCache getRelatedItemTypeTransferCache() {
        if(relatedItemTypeTransferCache == null) {
            relatedItemTypeTransferCache = new RelatedItemTypeTransferCache(userVisit, itemControl);
        }

        return relatedItemTypeTransferCache;
    }

    public RelatedItemTypeDescriptionTransferCache getRelatedItemTypeDescriptionTransferCache() {
        if(relatedItemTypeDescriptionTransferCache == null) {
            relatedItemTypeDescriptionTransferCache = new RelatedItemTypeDescriptionTransferCache(userVisit, itemControl);
        }

        return relatedItemTypeDescriptionTransferCache;
    }

    public RelatedItemTransferCache getRelatedItemTransferCache() {
        if(relatedItemTransferCache == null) {
            relatedItemTransferCache = new RelatedItemTransferCache(userVisit, itemControl);
        }
        
        return relatedItemTransferCache;
    }
    
    public ItemUseTypeTransferCache getItemUseTypeTransferCache() {
        if(itemUseTypeTransferCache == null) {
            itemUseTypeTransferCache = new ItemUseTypeTransferCache(userVisit, itemControl);
        }
        
        return itemUseTypeTransferCache;
    }
    
    public ItemPriceTypeTransferCache getItemPriceTypeTransferCache() {
        if(itemPriceTypeTransferCache == null) {
            itemPriceTypeTransferCache = new ItemPriceTypeTransferCache(userVisit, itemControl);
        }
        
        return itemPriceTypeTransferCache;
    }
    
    public ItemTransferCache getItemTransferCache() {
        if(itemTransferCache == null) {
            itemTransferCache = new ItemTransferCache(userVisit, itemControl);
        }
        
        return itemTransferCache;
    }
    
    public ItemUnitOfMeasureTypeTransferCache getItemUnitOfMeasureTypeTransferCache() {
        if(itemUnitOfMeasureTypeTransferCache == null) {
            itemUnitOfMeasureTypeTransferCache = new ItemUnitOfMeasureTypeTransferCache(userVisit, itemControl);
        }
        
        return itemUnitOfMeasureTypeTransferCache;
    }
    
    public ItemPriceTransferCache getItemPriceTransferCache() {
        if(itemPriceTransferCache == null) {
            itemPriceTransferCache = new ItemPriceTransferCache(userVisit, itemControl);
        }
        
        return itemPriceTransferCache;
    }

    public ItemVolumeTypeTransferCache getItemVolumeTypeTransferCache() {
        if(itemVolumeTypeTransferCache == null) {
            itemVolumeTypeTransferCache = new ItemVolumeTypeTransferCache(userVisit, itemControl);
        }

        return itemVolumeTypeTransferCache;
    }

    public ItemVolumeTypeDescriptionTransferCache getItemVolumeTypeDescriptionTransferCache() {
        if(itemVolumeTypeDescriptionTransferCache == null) {
            itemVolumeTypeDescriptionTransferCache = new ItemVolumeTypeDescriptionTransferCache(userVisit, itemControl);
        }

        return itemVolumeTypeDescriptionTransferCache;
    }

    public ItemVolumeTransferCache getItemVolumeTransferCache() {
        if(itemVolumeTransferCache == null) {
            itemVolumeTransferCache = new ItemVolumeTransferCache(userVisit, itemControl);
        }
        
        return itemVolumeTransferCache;
    }
    
    public ItemShippingTimeTransferCache getItemShippingTimeTransferCache() {
        if(itemShippingTimeTransferCache == null) {
            itemShippingTimeTransferCache = new ItemShippingTimeTransferCache(userVisit, itemControl);
        }
        
        return itemShippingTimeTransferCache;
    }
    
    public ItemAliasTransferCache getItemAliasTransferCache() {
        if(itemAliasTransferCache == null) {
            itemAliasTransferCache = new ItemAliasTransferCache(userVisit, itemControl);
        }
        
        return itemAliasTransferCache;
    }
    
    public ItemAliasChecksumTypeTransferCache getItemAliasChecksumTypeTransferCache() {
        if(itemAliasChecksumTypeTransferCache == null) {
            itemAliasChecksumTypeTransferCache = new ItemAliasChecksumTypeTransferCache(userVisit, itemControl);
        }

        return itemAliasChecksumTypeTransferCache;
    }

    public ItemAliasTypeTransferCache getItemAliasTypeTransferCache() {
        if(itemAliasTypeTransferCache == null) {
            itemAliasTypeTransferCache = new ItemAliasTypeTransferCache(userVisit, itemControl);
        }

        return itemAliasTypeTransferCache;
    }

    public ItemAliasTypeDescriptionTransferCache getItemAliasTypeDescriptionTransferCache() {
        if(itemAliasTypeDescriptionTransferCache == null) {
            itemAliasTypeDescriptionTransferCache = new ItemAliasTypeDescriptionTransferCache(userVisit, itemControl);
        }
        
        return itemAliasTypeDescriptionTransferCache;
    }
    
    public ItemDescriptionTransferCache getItemDescriptionTransferCache() {
        if(itemDescriptionTransferCache == null) {
            itemDescriptionTransferCache = new ItemDescriptionTransferCache(userVisit, itemControl);
        }
        
        return itemDescriptionTransferCache;
    }
    
    public ItemDescriptionTypeTransferCache getItemDescriptionTypeTransferCache() {
        if(itemDescriptionTypeTransferCache == null) {
            itemDescriptionTypeTransferCache = new ItemDescriptionTypeTransferCache(userVisit, itemControl);
        }

        return itemDescriptionTypeTransferCache;
    }

    public ItemDescriptionTypeDescriptionTransferCache getItemDescriptionTypeDescriptionTransferCache() {
        if(itemDescriptionTypeDescriptionTransferCache == null) {
            itemDescriptionTypeDescriptionTransferCache = new ItemDescriptionTypeDescriptionTransferCache(userVisit, itemControl);
        }

        return itemDescriptionTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTypeTransferCache getItemDescriptionTypeUseTypeTransferCache() {
        if(itemDescriptionTypeUseTypeTransferCache == null) {
            itemDescriptionTypeUseTypeTransferCache = new ItemDescriptionTypeUseTypeTransferCache(userVisit, itemControl);
        }

        return itemDescriptionTypeUseTypeTransferCache;
    }

    public ItemDescriptionTypeUseTypeDescriptionTransferCache getItemDescriptionTypeUseTypeDescriptionTransferCache() {
        if(itemDescriptionTypeUseTypeDescriptionTransferCache == null) {
            itemDescriptionTypeUseTypeDescriptionTransferCache = new ItemDescriptionTypeUseTypeDescriptionTransferCache(userVisit, itemControl);
        }

        return itemDescriptionTypeUseTypeDescriptionTransferCache;
    }

    public ItemDescriptionTypeUseTransferCache getItemDescriptionTypeUseTransferCache() {
        if(itemDescriptionTypeUseTransferCache == null) {
            itemDescriptionTypeUseTransferCache = new ItemDescriptionTypeUseTransferCache(userVisit, itemControl);
        }

        return itemDescriptionTypeUseTransferCache;
    }

    public ItemWeightTypeTransferCache getItemWeightTypeTransferCache() {
        if(itemWeightTypeTransferCache == null) {
            itemWeightTypeTransferCache = new ItemWeightTypeTransferCache(userVisit, itemControl);
        }

        return itemWeightTypeTransferCache;
    }

    public ItemWeightTypeDescriptionTransferCache getItemWeightTypeDescriptionTransferCache() {
        if(itemWeightTypeDescriptionTransferCache == null) {
            itemWeightTypeDescriptionTransferCache = new ItemWeightTypeDescriptionTransferCache(userVisit, itemControl);
        }

        return itemWeightTypeDescriptionTransferCache;
    }

    public ItemWeightTransferCache getItemWeightTransferCache() {
        if(itemWeightTransferCache == null) {
            itemWeightTransferCache = new ItemWeightTransferCache(userVisit, itemControl);
        }

        return itemWeightTransferCache;
    }

    public ItemCategoryDescriptionTransferCache getItemCategoryDescriptionTransferCache() {
        if(itemCategoryDescriptionTransferCache == null) {
            itemCategoryDescriptionTransferCache = new ItemCategoryDescriptionTransferCache(userVisit, itemControl);
        }
        
        return itemCategoryDescriptionTransferCache;
    }
    
    public ItemCategoryTransferCache getItemCategoryTransferCache() {
        if(itemCategoryTransferCache == null) {
            itemCategoryTransferCache = new ItemCategoryTransferCache(userVisit, itemControl);
        }
        
        return itemCategoryTransferCache;
    }
    
    public ItemKitMemberTransferCache getItemKitMemberTransferCache() {
        if(itemKitMemberTransferCache == null) {
            itemKitMemberTransferCache = new ItemKitMemberTransferCache(userVisit, itemControl);
        }
        
        return itemKitMemberTransferCache;
    }
    
    public ItemCountryOfOriginTransferCache getItemCountryOfOriginTransferCache() {
        if(itemCountryOfOriginTransferCache == null) {
            itemCountryOfOriginTransferCache = new ItemCountryOfOriginTransferCache(userVisit, itemControl);
        }
        
        return itemCountryOfOriginTransferCache;
    }
    
    public ItemPackCheckRequirementTransferCache getItemPackCheckRequirementTransferCache() {
        if(itemPackCheckRequirementTransferCache == null) {
            itemPackCheckRequirementTransferCache = new ItemPackCheckRequirementTransferCache(userVisit, itemControl);
        }
        
        return itemPackCheckRequirementTransferCache;
    }
    
    public ItemUnitCustomerTypeLimitTransferCache getItemUnitCustomerTypeLimitTransferCache() {
        if(itemUnitCustomerTypeLimitTransferCache == null) {
            itemUnitCustomerTypeLimitTransferCache = new ItemUnitCustomerTypeLimitTransferCache(userVisit, itemControl);
        }
        
        return itemUnitCustomerTypeLimitTransferCache;
    }
    
    public ItemUnitLimitTransferCache getItemUnitLimitTransferCache() {
        if(itemUnitLimitTransferCache == null) {
            itemUnitLimitTransferCache = new ItemUnitLimitTransferCache(userVisit, itemControl);
        }
        
        return itemUnitLimitTransferCache;
    }
    
    public ItemUnitPriceLimitTransferCache getItemUnitPriceLimitTransferCache() {
        if(itemUnitPriceLimitTransferCache == null) {
            itemUnitPriceLimitTransferCache = new ItemUnitPriceLimitTransferCache(userVisit, itemControl);
        }
        
        return itemUnitPriceLimitTransferCache;
    }
    
    public ItemImageTypeTransferCache getItemImageTypeTransferCache() {
        if(itemImageTypeTransferCache == null) {
            itemImageTypeTransferCache = new ItemImageTypeTransferCache(userVisit, itemControl);
        }

        return itemImageTypeTransferCache;
    }

    public ItemImageTypeDescriptionTransferCache getItemImageTypeDescriptionTransferCache() {
        if(itemImageTypeDescriptionTransferCache == null) {
            itemImageTypeDescriptionTransferCache = new ItemImageTypeDescriptionTransferCache(userVisit, itemControl);
        }

        return itemImageTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeTransferCache getHarmonizedTariffScheduleCodeTransferCache() {
        if(harmonizedTariffScheduleCodeTransferCache == null) {
            harmonizedTariffScheduleCodeTransferCache = new HarmonizedTariffScheduleCodeTransferCache(userVisit, itemControl);
        }

        return harmonizedTariffScheduleCodeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeTranslationTransferCache getHarmonizedTariffScheduleCodeTranslationTransferCache() {
        if(harmonizedTariffScheduleCodeTranslationTransferCache == null) {
            harmonizedTariffScheduleCodeTranslationTransferCache = new HarmonizedTariffScheduleCodeTranslationTransferCache(userVisit, itemControl);
        }

        return harmonizedTariffScheduleCodeTranslationTransferCache;
    }

    public HarmonizedTariffScheduleCodeUnitTransferCache getHarmonizedTariffScheduleCodeUnitTransferCache() {
        if(harmonizedTariffScheduleCodeUnitTransferCache == null) {
            harmonizedTariffScheduleCodeUnitTransferCache = new HarmonizedTariffScheduleCodeUnitTransferCache(userVisit, itemControl);
        }

        return harmonizedTariffScheduleCodeUnitTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUnitDescriptionTransferCache getHarmonizedTariffScheduleCodeUnitDescriptionTransferCache() {
        if(harmonizedTariffScheduleCodeUnitDescriptionTransferCache == null) {
            harmonizedTariffScheduleCodeUnitDescriptionTransferCache = new HarmonizedTariffScheduleCodeUnitDescriptionTransferCache(userVisit, itemControl);
        }

        return harmonizedTariffScheduleCodeUnitDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTypeTransferCache getHarmonizedTariffScheduleCodeUseTypeTransferCache() {
        if(harmonizedTariffScheduleCodeUseTypeTransferCache == null) {
            harmonizedTariffScheduleCodeUseTypeTransferCache = new HarmonizedTariffScheduleCodeUseTypeTransferCache(userVisit, itemControl);
        }

        return harmonizedTariffScheduleCodeUseTypeTransferCache;
    }
                                                                
    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache getHarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache() {
        if(harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache == null) {
            harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache = new HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache(userVisit, itemControl);
        }

        return harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;
    }

    public HarmonizedTariffScheduleCodeUseTransferCache getHarmonizedTariffScheduleCodeUseTransferCache() {
        if(harmonizedTariffScheduleCodeUseTransferCache == null) {
            harmonizedTariffScheduleCodeUseTransferCache = new HarmonizedTariffScheduleCodeUseTransferCache(userVisit, itemControl);
        }

        return harmonizedTariffScheduleCodeUseTransferCache;
    }
    
    public ItemHarmonizedTariffScheduleCodeTransferCache getItemHarmonizedTariffScheduleCodeTransferCache() {
        if(itemHarmonizedTariffScheduleCodeTransferCache == null) {
            itemHarmonizedTariffScheduleCodeTransferCache = new ItemHarmonizedTariffScheduleCodeTransferCache(userVisit, itemControl);
        }

        return itemHarmonizedTariffScheduleCodeTransferCache;
    }

}
