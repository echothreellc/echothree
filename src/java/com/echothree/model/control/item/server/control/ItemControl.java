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

package com.echothree.model.control.item.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.common.choice.HarmonizedTariffScheduleCodeChoicesBean;
import com.echothree.model.control.item.common.choice.HarmonizedTariffScheduleCodeUnitChoicesBean;
import com.echothree.model.control.item.common.choice.HarmonizedTariffScheduleCodeUseTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemAliasChecksumTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemAliasTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemCategoryChoicesBean;
import com.echothree.model.control.item.common.choice.ItemDeliveryTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemDescriptionTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemDescriptionTypeUseTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemImageTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemInventoryTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemPriceTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemStatusChoicesBean;
import com.echothree.model.control.item.common.choice.ItemTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemUseTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemVolumeTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemWeightTypeChoicesBean;
import com.echothree.model.control.item.common.choice.RelatedItemTypeChoicesBean;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTranslationTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUnitDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUnitTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemAliasChecksumTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemAliasTransfer;
import com.echothree.model.control.item.common.transfer.ItemAliasTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemAliasTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemCategoryDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemCategoryTransfer;
import com.echothree.model.control.item.common.transfer.ItemCountryOfOriginTransfer;
import com.echothree.model.control.item.common.transfer.ItemDeliveryTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeUseTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeUseTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeUseTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemHarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.ItemImageTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemImageTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemInventoryTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemKitMemberTransfer;
import com.echothree.model.control.item.common.transfer.ItemPackCheckRequirementTransfer;
import com.echothree.model.control.item.common.transfer.ItemPriceTransfer;
import com.echothree.model.control.item.common.transfer.ItemPriceTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemResultTransfer;
import com.echothree.model.control.item.common.transfer.ItemShippingTimeTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.common.transfer.ItemTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemUnitCustomerTypeLimitTransfer;
import com.echothree.model.control.item.common.transfer.ItemUnitLimitTransfer;
import com.echothree.model.control.item.common.transfer.ItemUnitOfMeasureTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemUnitPriceLimitTransfer;
import com.echothree.model.control.item.common.transfer.ItemUseTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemVolumeTransfer;
import com.echothree.model.control.item.common.transfer.ItemVolumeTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemVolumeTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemWeightTransfer;
import com.echothree.model.control.item.common.transfer.ItemWeightTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemWeightTypeTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTypeTransfer;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeTranslationTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeUnitDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeUnitTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeUseTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeUseTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemAliasChecksumTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemAliasTransferCache;
import com.echothree.model.control.item.server.transfer.ItemAliasTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemAliasTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemCategoryDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemCategoryTransferCache;
import com.echothree.model.control.item.server.transfer.ItemCountryOfOriginTransferCache;
import com.echothree.model.control.item.server.transfer.ItemDeliveryTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemDescriptionTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemDescriptionTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemDescriptionTypeUseTransferCache;
import com.echothree.model.control.item.server.transfer.ItemDescriptionTypeUseTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemDescriptionTypeUseTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemHarmonizedTariffScheduleCodeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemImageTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemImageTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemInventoryTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemKitMemberTransferCache;
import com.echothree.model.control.item.server.transfer.ItemPackCheckRequirementTransferCache;
import com.echothree.model.control.item.server.transfer.ItemPriceTransferCache;
import com.echothree.model.control.item.server.transfer.ItemPriceTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemShippingTimeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemTransferCache;
import com.echothree.model.control.item.server.transfer.ItemTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitCustomerTypeLimitTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitLimitTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitOfMeasureTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitPriceLimitTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUseTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemVolumeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemVolumeTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemVolumeTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemWeightTransferCache;
import com.echothree.model.control.item.server.transfer.ItemWeightTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.ItemWeightTypeTransferCache;
import com.echothree.model.control.item.server.transfer.RelatedItemTransferCache;
import com.echothree.model.control.item.server.transfer.RelatedItemTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.RelatedItemTypeTransferCache;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodePK;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodeUnitPK;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodeUseTypePK;
import com.echothree.model.data.item.common.pk.ItemAliasChecksumTypePK;
import com.echothree.model.data.item.common.pk.ItemAliasTypePK;
import com.echothree.model.data.item.common.pk.ItemCategoryPK;
import com.echothree.model.data.item.common.pk.ItemDeliveryTypePK;
import com.echothree.model.data.item.common.pk.ItemDescriptionPK;
import com.echothree.model.data.item.common.pk.ItemDescriptionTypePK;
import com.echothree.model.data.item.common.pk.ItemDescriptionTypeUseTypePK;
import com.echothree.model.data.item.common.pk.ItemHarmonizedTariffScheduleCodePK;
import com.echothree.model.data.item.common.pk.ItemImageTypePK;
import com.echothree.model.data.item.common.pk.ItemInventoryTypePK;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.common.pk.ItemPriceTypePK;
import com.echothree.model.data.item.common.pk.ItemTypePK;
import com.echothree.model.data.item.common.pk.ItemUseTypePK;
import com.echothree.model.data.item.common.pk.ItemVolumeTypePK;
import com.echothree.model.data.item.common.pk.ItemWeightTypePK;
import com.echothree.model.data.item.common.pk.RelatedItemTypePK;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeTranslation;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnitDescription;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUse;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseTypeDescription;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumTypeDescription;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDescription;
import com.echothree.model.data.item.server.entity.ItemBlobDescription;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemCategoryDescription;
import com.echothree.model.data.item.server.entity.ItemClobDescription;
import com.echothree.model.data.item.server.entity.ItemCountryOfOrigin;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemDeliveryTypeDescription;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUse;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDescription;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.ItemImageDescription;
import com.echothree.model.data.item.server.entity.ItemImageDescriptionType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.item.server.entity.ItemImageTypeDescription;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.item.server.entity.ItemInventoryTypeDescription;
import com.echothree.model.data.item.server.entity.ItemKitMember;
import com.echothree.model.data.item.server.entity.ItemKitOption;
import com.echothree.model.data.item.server.entity.ItemPackCheckRequirement;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.item.server.entity.ItemPriceTypeDescription;
import com.echothree.model.data.item.server.entity.ItemShippingTime;
import com.echothree.model.data.item.server.entity.ItemStringDescription;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.model.data.item.server.entity.ItemTypeDescription;
import com.echothree.model.data.item.server.entity.ItemUnitCustomerTypeLimit;
import com.echothree.model.data.item.server.entity.ItemUnitLimit;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.item.server.entity.ItemUnitPriceLimit;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.item.server.entity.ItemUseTypeDescription;
import com.echothree.model.data.item.server.entity.ItemVariablePrice;
import com.echothree.model.data.item.server.entity.ItemVolume;
import com.echothree.model.data.item.server.entity.ItemVolumeType;
import com.echothree.model.data.item.server.entity.ItemVolumeTypeDescription;
import com.echothree.model.data.item.server.entity.ItemWeight;
import com.echothree.model.data.item.server.entity.ItemWeightType;
import com.echothree.model.data.item.server.entity.ItemWeightTypeDescription;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.entity.RelatedItemTypeDescription;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeDetailFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeTranslationFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeUnitDescriptionFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeUnitDetailFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeUnitFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeUseFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeUseTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeUseTypeDetailFactory;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeUseTypeFactory;
import com.echothree.model.data.item.server.factory.ItemAliasChecksumTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemAliasChecksumTypeFactory;
import com.echothree.model.data.item.server.factory.ItemAliasFactory;
import com.echothree.model.data.item.server.factory.ItemAliasTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemAliasTypeDetailFactory;
import com.echothree.model.data.item.server.factory.ItemAliasTypeFactory;
import com.echothree.model.data.item.server.factory.ItemBlobDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemCategoryDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemCategoryDetailFactory;
import com.echothree.model.data.item.server.factory.ItemCategoryFactory;
import com.echothree.model.data.item.server.factory.ItemClobDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemCountryOfOriginFactory;
import com.echothree.model.data.item.server.factory.ItemDeliveryTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemDeliveryTypeFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionDetailFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeDetailFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeUseFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeUseTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeUseTypeDetailFactory;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeUseTypeFactory;
import com.echothree.model.data.item.server.factory.ItemDetailFactory;
import com.echothree.model.data.item.server.factory.ItemFactory;
import com.echothree.model.data.item.server.factory.ItemFixedPriceFactory;
import com.echothree.model.data.item.server.factory.ItemHarmonizedTariffScheduleCodeDetailFactory;
import com.echothree.model.data.item.server.factory.ItemHarmonizedTariffScheduleCodeFactory;
import com.echothree.model.data.item.server.factory.ItemImageDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemImageDescriptionTypeFactory;
import com.echothree.model.data.item.server.factory.ItemImageTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemImageTypeDetailFactory;
import com.echothree.model.data.item.server.factory.ItemImageTypeFactory;
import com.echothree.model.data.item.server.factory.ItemInventoryTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemInventoryTypeFactory;
import com.echothree.model.data.item.server.factory.ItemKitMemberFactory;
import com.echothree.model.data.item.server.factory.ItemKitOptionFactory;
import com.echothree.model.data.item.server.factory.ItemPackCheckRequirementFactory;
import com.echothree.model.data.item.server.factory.ItemPriceFactory;
import com.echothree.model.data.item.server.factory.ItemPriceTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemPriceTypeFactory;
import com.echothree.model.data.item.server.factory.ItemShippingTimeFactory;
import com.echothree.model.data.item.server.factory.ItemStringDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemTypeFactory;
import com.echothree.model.data.item.server.factory.ItemUnitCustomerTypeLimitFactory;
import com.echothree.model.data.item.server.factory.ItemUnitLimitFactory;
import com.echothree.model.data.item.server.factory.ItemUnitOfMeasureTypeFactory;
import com.echothree.model.data.item.server.factory.ItemUnitPriceLimitFactory;
import com.echothree.model.data.item.server.factory.ItemUseTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemUseTypeFactory;
import com.echothree.model.data.item.server.factory.ItemVariablePriceFactory;
import com.echothree.model.data.item.server.factory.ItemVolumeFactory;
import com.echothree.model.data.item.server.factory.ItemVolumeTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemVolumeTypeDetailFactory;
import com.echothree.model.data.item.server.factory.ItemVolumeTypeFactory;
import com.echothree.model.data.item.server.factory.ItemWeightFactory;
import com.echothree.model.data.item.server.factory.ItemWeightTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemWeightTypeDetailFactory;
import com.echothree.model.data.item.server.factory.ItemWeightTypeFactory;
import com.echothree.model.data.item.server.factory.RelatedItemDetailFactory;
import com.echothree.model.data.item.server.factory.RelatedItemFactory;
import com.echothree.model.data.item.server.factory.RelatedItemTypeDescriptionFactory;
import com.echothree.model.data.item.server.factory.RelatedItemTypeDetailFactory;
import com.echothree.model.data.item.server.factory.RelatedItemTypeFactory;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeDetailValue;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeTranslationValue;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeUnitDescriptionValue;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeUnitDetailValue;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeUseTypeDescriptionValue;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeUseTypeDetailValue;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeUseValue;
import com.echothree.model.data.item.server.value.ItemAliasChecksumTypeValue;
import com.echothree.model.data.item.server.value.ItemAliasTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemAliasTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemAliasValue;
import com.echothree.model.data.item.server.value.ItemBlobDescriptionValue;
import com.echothree.model.data.item.server.value.ItemCategoryDescriptionValue;
import com.echothree.model.data.item.server.value.ItemCategoryDetailValue;
import com.echothree.model.data.item.server.value.ItemClobDescriptionValue;
import com.echothree.model.data.item.server.value.ItemCountryOfOriginValue;
import com.echothree.model.data.item.server.value.ItemDeliveryTypeValue;
import com.echothree.model.data.item.server.value.ItemDescriptionDetailValue;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeUseTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeUseTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeUseValue;
import com.echothree.model.data.item.server.value.ItemDetailValue;
import com.echothree.model.data.item.server.value.ItemFixedPriceValue;
import com.echothree.model.data.item.server.value.ItemHarmonizedTariffScheduleCodeDetailValue;
import com.echothree.model.data.item.server.value.ItemImageDescriptionTypeValue;
import com.echothree.model.data.item.server.value.ItemImageDescriptionValue;
import com.echothree.model.data.item.server.value.ItemImageTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemImageTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemInventoryTypeValue;
import com.echothree.model.data.item.server.value.ItemKitMemberValue;
import com.echothree.model.data.item.server.value.ItemKitOptionValue;
import com.echothree.model.data.item.server.value.ItemPackCheckRequirementValue;
import com.echothree.model.data.item.server.value.ItemPriceTypeValue;
import com.echothree.model.data.item.server.value.ItemShippingTimeValue;
import com.echothree.model.data.item.server.value.ItemStringDescriptionValue;
import com.echothree.model.data.item.server.value.ItemTypeValue;
import com.echothree.model.data.item.server.value.ItemUnitCustomerTypeLimitValue;
import com.echothree.model.data.item.server.value.ItemUnitLimitValue;
import com.echothree.model.data.item.server.value.ItemUnitOfMeasureTypeValue;
import com.echothree.model.data.item.server.value.ItemUnitPriceLimitValue;
import com.echothree.model.data.item.server.value.ItemUseTypeValue;
import com.echothree.model.data.item.server.value.ItemVariablePriceValue;
import com.echothree.model.data.item.server.value.ItemVolumeTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemVolumeTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemVolumeValue;
import com.echothree.model.data.item.server.value.ItemWeightTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemWeightTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemWeightValue;
import com.echothree.model.data.item.server.value.RelatedItemDetailValue;
import com.echothree.model.data.item.server.value.RelatedItemTypeDescriptionValue;
import com.echothree.model.data.item.server.value.RelatedItemTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.style.server.entity.StylePath;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_CONDITIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_UNITS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_USES_HARMONIZED_TARIFF_SCHEDULE_CODE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_USES_HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_ALIASES_ITEM_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_ALIASES_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_CATEGORIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_CATEGORY_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_COUNTRY_OF_ORIGINS_COUNTRY_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_COUNTRY_OF_ORIGINS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_DETAILS_ITEM_DESCRIPTION_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_DETAILS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_DETAILS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPE_USES_ITEM_DESCRIPTION_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPE_USES_ITEM_DESCRIPTION_TYPE_USE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPE_USE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPE_USE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_DESCRIPTION_TYPE_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_COUNTRY_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_IMAGE_DESCRIPTIONS_ITEM_DESCRIPTION_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_IMAGE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_IMAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_PACK_CHECK_REQUIREMENTS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_PACK_CHECK_REQUIREMENTS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_PRICES_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_PRICES_INVENTORY_CONDITION_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_PRICES_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_PRICES_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_SHIPPING_TIMES_CUSTOMER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_SHIPPING_TIMES_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_CUSTOMER_TYPE_LIMITS_CUSTOMER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_CUSTOMER_TYPE_LIMITS_INVENTORY_CONDITION_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_CUSTOMER_TYPE_LIMITS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_CUSTOMER_TYPE_LIMITS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_LIMITS_INVENTORY_CONDITION_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_LIMITS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_LIMITS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_OF_MEASURE_TYPES_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_OF_MEASURE_TYPES_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_PRICE_LIMITS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_PRICE_LIMITS_INVENTORY_CONDITION_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_PRICE_LIMITS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_UNIT_PRICE_LIMITS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_VOLUMES_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_VOLUMES_ITEM_VOLUME_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_VOLUMES_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_VOLUME_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_VOLUME_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_VOLUME_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_WEIGHTS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_WEIGHTS_ITEM_WEIGHT_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_WEIGHTS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_WEIGHT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_WEIGHT_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_WEIGHT_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.RELATED_ITEMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.RELATED_ITEM_DETAILS_FROM_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.RELATED_ITEM_DETAILS_RELATED_ITEM_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.RELATED_ITEM_DETAILS_TO_ITEM_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.RELATED_ITEM_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.RELATED_ITEM_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.RELATED_ITEM_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_KIND_FK;
import static com.echothree.model.jooq.server.tables.accounting.Currencies.Currencies;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypeDetails.CustomerTypeDetails;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypes.CustomerTypes;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeDetails.GeoCodeDetails;
import static com.echothree.model.jooq.server.tables.geo.GeoCodes.GeoCodes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeDetails.HarmonizedTariffScheduleCodeDetails;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeTranslations.HarmonizedTariffScheduleCodeTranslations;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeUnitDescriptions.HarmonizedTariffScheduleCodeUnitDescriptions;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeUnitDetails.HarmonizedTariffScheduleCodeUnitDetails;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeUnits.HarmonizedTariffScheduleCodeUnits;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeUseTypeDescriptions.HarmonizedTariffScheduleCodeUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeUseTypeDetails.HarmonizedTariffScheduleCodeUseTypeDetails;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeUseTypes.HarmonizedTariffScheduleCodeUseTypes;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodeUses.HarmonizedTariffScheduleCodeUses;
import static com.echothree.model.jooq.server.tables.item.HarmonizedTariffScheduleCodes.HarmonizedTariffScheduleCodes;
import static com.echothree.model.jooq.server.tables.item.ItemAliasChecksumTypeDescriptions.ItemAliasChecksumTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemAliasChecksumTypes.ItemAliasChecksumTypes;
import static com.echothree.model.jooq.server.tables.item.ItemAliasTypeDescriptions.ItemAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemAliasTypeDetails.ItemAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemAliasTypes.ItemAliasTypes;
import static com.echothree.model.jooq.server.tables.item.ItemAliases.ItemAliases;
import static com.echothree.model.jooq.server.tables.item.ItemBlobDescriptions.ItemBlobDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemCategories.ItemCategories;
import static com.echothree.model.jooq.server.tables.item.ItemCategoryDescriptions.ItemCategoryDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemCategoryDetails.ItemCategoryDetails;
import static com.echothree.model.jooq.server.tables.item.ItemClobDescriptions.ItemClobDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemCountryOfOrigins.ItemCountryOfOrigins;
import static com.echothree.model.jooq.server.tables.item.ItemDeliveryTypeDescriptions.ItemDeliveryTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemDeliveryTypes.ItemDeliveryTypes;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionDetails.ItemDescriptionDetails;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionTypeDescriptions.ItemDescriptionTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionTypeDetails.ItemDescriptionTypeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionTypeUseTypeDescriptions.ItemDescriptionTypeUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionTypeUseTypeDetails.ItemDescriptionTypeUseTypeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionTypeUseTypes.ItemDescriptionTypeUseTypes;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionTypeUses.ItemDescriptionTypeUses;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptionTypes.ItemDescriptionTypes;
import static com.echothree.model.jooq.server.tables.item.ItemDescriptions.ItemDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.ItemFixedPrices.ItemFixedPrices;
import static com.echothree.model.jooq.server.tables.item.ItemHarmonizedTariffScheduleCodeDetails.ItemHarmonizedTariffScheduleCodeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemHarmonizedTariffScheduleCodes.ItemHarmonizedTariffScheduleCodes;
import static com.echothree.model.jooq.server.tables.item.ItemImageDescriptionTypes.ItemImageDescriptionTypes;
import static com.echothree.model.jooq.server.tables.item.ItemImageDescriptions.ItemImageDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemImageTypeDescriptions.ItemImageTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemImageTypeDetails.ItemImageTypeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemImageTypes.ItemImageTypes;
import static com.echothree.model.jooq.server.tables.item.ItemInventoryTypeDescriptions.ItemInventoryTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemInventoryTypes.ItemInventoryTypes;
import static com.echothree.model.jooq.server.tables.item.ItemKitMembers.ItemKitMembers;
import static com.echothree.model.jooq.server.tables.item.ItemKitOptions.ItemKitOptions;
import static com.echothree.model.jooq.server.tables.item.ItemPackCheckRequirements.ItemPackCheckRequirements;
import static com.echothree.model.jooq.server.tables.item.ItemPriceTypeDescriptions.ItemPriceTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemPriceTypes.ItemPriceTypes;
import static com.echothree.model.jooq.server.tables.item.ItemPrices.ItemPrices;
import static com.echothree.model.jooq.server.tables.item.ItemShippingTimes.ItemShippingTimes;
import static com.echothree.model.jooq.server.tables.item.ItemStringDescriptions.ItemStringDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemTypeDescriptions.ItemTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemTypes.ItemTypes;
import static com.echothree.model.jooq.server.tables.item.ItemUnitCustomerTypeLimits.ItemUnitCustomerTypeLimits;
import static com.echothree.model.jooq.server.tables.item.ItemUnitLimits.ItemUnitLimits;
import static com.echothree.model.jooq.server.tables.item.ItemUnitOfMeasureTypes.ItemUnitOfMeasureTypes;
import static com.echothree.model.jooq.server.tables.item.ItemUnitPriceLimits.ItemUnitPriceLimits;
import static com.echothree.model.jooq.server.tables.item.ItemUseTypeDescriptions.ItemUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemUseTypes.ItemUseTypes;
import static com.echothree.model.jooq.server.tables.item.ItemVariablePrices.ItemVariablePrices;
import static com.echothree.model.jooq.server.tables.item.ItemVolumeTypeDescriptions.ItemVolumeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemVolumeTypeDetails.ItemVolumeTypeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemVolumeTypes.ItemVolumeTypes;
import static com.echothree.model.jooq.server.tables.item.ItemVolumes.ItemVolumes;
import static com.echothree.model.jooq.server.tables.item.ItemWeightTypeDescriptions.ItemWeightTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.ItemWeightTypeDetails.ItemWeightTypeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemWeightTypes.ItemWeightTypes;
import static com.echothree.model.jooq.server.tables.item.ItemWeights.ItemWeights;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.item.RelatedItemDetails.RelatedItemDetails;
import static com.echothree.model.jooq.server.tables.item.RelatedItemTypeDescriptions.RelatedItemTypeDescriptions;
import static com.echothree.model.jooq.server.tables.item.RelatedItemTypeDetails.RelatedItemTypeDetails;
import static com.echothree.model.jooq.server.tables.item.RelatedItemTypes.RelatedItemTypes;
import static com.echothree.model.jooq.server.tables.item.RelatedItems.RelatedItems;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindDetails.UnitOfMeasureKindDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKinds.UnitOfMeasureKinds;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypes.UnitOfMeasureTypes;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import org.jooq.Condition;
import org.jooq.Select;

@CommandScope
public class ItemControl
        extends BaseModelControl {
    
    @Inject
    protected OfferItemControl offerItemControl;

    @Inject
    protected SearchControl searchControl;

    @Inject
    protected VendorControl vendorControl;

    @Inject
    protected OfferItemLogic offerItemLogic;

    /** Creates a new instance of ItemControl */
    protected ItemControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemTypeFactory itemTypeFactory;

    @Inject
    ItemTypeTransferCache itemTypeTransferCache;

    public ItemType createItemType(String itemTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var itemType = itemTypeFactory.create(itemTypeName, isDefault, sortOrder);

        sendEvent(itemType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemType;
    }

    public long countItemTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemType */
    public ItemType getItemTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemTypePK(entityInstance.getEntityUniqueId());

        return itemTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemType getItemTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemType getItemTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemType getItemTypeByName(String itemTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemTypes.fields())
                .from(ItemTypes)
                .where(ItemTypes.ITEM_TYPE_NAME.eq(itemTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemType getItemTypeByName(String itemTypeName) {
        return getItemTypeByName(itemTypeName, EntityPermission.READ_ONLY);
    }

    public ItemType getItemTypeByNameForUpdate(String itemTypeName) {
        return getItemTypeByName(itemTypeName, EntityPermission.READ_WRITE);
    }

    public ItemType getDefaultItemType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemTypes.fields())
                .from(ItemTypes)
                .where(ItemTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemType getDefaultItemType() {
        return getDefaultItemType(EntityPermission.READ_ONLY);
    }

    public ItemType getDefaultItemTypeForUpdate() {
        return getDefaultItemType(EntityPermission.READ_WRITE);
    }

    public ItemTypeValue getDefaultItemTypeValueForUpdate() {
        return getDefaultItemTypeForUpdate().getItemTypeValue().clone();
    }
    
    public List<ItemType> getItemTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemTypes.fields())
                .from(ItemTypes)
                .orderBy(ItemTypes.SORT_ORDER, ItemTypes.ITEM_TYPE_NAME),
                ItemTypeFactory.class);

        return itemTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ItemTypeChoicesBean getItemTypeChoices(String defaultItemTypeChoice, Language language, boolean allowNullChoice) {
        var itemTypes = getItemTypes();
        var size = itemTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemType : itemTypes) {
            var label = getBestItemTypeDescription(itemType, language);
            var value = itemType.getItemTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemTypeChoice != null && defaultItemTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemTypeTransfer getItemTypeTransfer(UserVisit userVisit, ItemType itemType) {
        return itemTypeTransferCache.getTransfer(userVisit, itemType);
    }

    public List<ItemTypeTransfer> getItemTypeTransfers(UserVisit userVisit, Collection<ItemType> entities) {
        var itemTypeTransfers = new ArrayList<ItemTypeTransfer>(entities.size());

        entities.forEach((entity) ->
                itemTypeTransfers.add(itemTypeTransferCache.getTransfer(userVisit, entity))
        );

        return itemTypeTransfers;
    }

    public List<ItemTypeTransfer> getItemTypeTransfers(UserVisit userVisit) {
        return getItemTypeTransfers(userVisit, getItemTypes());
    }

    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemTypeDescriptionFactory itemTypeDescriptionFactory;

    public ItemTypeDescription createItemTypeDescription(ItemType itemType, Language language, String description,
            BasePK createdBy) {
        var itemTypeDescription = itemTypeDescriptionFactory.create(itemType, language, description);

        sendEvent(itemType.getPrimaryKey(), EventTypes.MODIFY, itemTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemTypeDescription;
    }
    
    public ItemTypeDescription getItemTypeDescription(ItemType itemType, Language language) {
        var query = session.getDslContext()
                .select(ItemTypeDescriptions.fields())
                .from(ItemTypeDescriptions)
                .where(ItemTypeDescriptions.ITEM_TYPE.eq(itemType.getPrimaryKey()),
                        ItemTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return itemTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestItemTypeDescription(ItemType itemType, Language language) {
        String description;
        var itemTypeDescription = getItemTypeDescription(itemType, language);
        
        if(itemTypeDescription == null && !language.getIsDefault()) {
            itemTypeDescription = getItemTypeDescription(itemType, partyControl.getDefaultLanguage());
        }
        
        if(itemTypeDescription == null) {
            description = itemType.getItemTypeName();
        } else {
            description = itemTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDeliveryTypeFactory itemDeliveryTypeFactory;

    @Inject
    ItemDeliveryTypeTransferCache itemDeliveryTypeTransferCache;

    public ItemDeliveryType createItemDeliveryType(String itemDeliveryTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemDeliveryType = itemDeliveryTypeFactory.create(itemDeliveryTypeName, isDefault, sortOrder);

        sendEvent(itemDeliveryType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemDeliveryType;
    }

    public long countItemDeliveryTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemDeliveryTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemDeliveryType */
    public ItemDeliveryType getItemDeliveryTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemDeliveryTypePK(entityInstance.getEntityUniqueId());

        return itemDeliveryTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemDeliveryType getItemDeliveryTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemDeliveryTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemDeliveryType getItemDeliveryTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemDeliveryTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemDeliveryType getItemDeliveryTypeByName(String itemDeliveryTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDeliveryTypes.fields())
                .from(ItemDeliveryTypes)
                .where(ItemDeliveryTypes.ITEM_DELIVERY_TYPE_NAME.eq(itemDeliveryTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDeliveryTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDeliveryType getItemDeliveryTypeByName(String itemDeliveryTypeName) {
        return getItemDeliveryTypeByName(itemDeliveryTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDeliveryType getItemDeliveryTypeByNameForUpdate(String itemDeliveryTypeName) {
        return getItemDeliveryTypeByName(itemDeliveryTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDeliveryType getDefaultItemDeliveryType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDeliveryTypes.fields())
                .from(ItemDeliveryTypes)
                .where(ItemDeliveryTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDeliveryTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDeliveryType getDefaultItemDeliveryType() {
        return getDefaultItemDeliveryType(EntityPermission.READ_ONLY);
    }

    public ItemDeliveryType getDefaultItemDeliveryTypeForUpdate() {
        return getDefaultItemDeliveryType(EntityPermission.READ_WRITE);
    }

    public ItemDeliveryTypeValue getDefaultItemDeliveryTypeValueForUpdate() {
        return getDefaultItemDeliveryTypeForUpdate().getItemDeliveryTypeValue().clone();
    }
    
    public List<ItemDeliveryType> getItemDeliveryTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemDeliveryTypes.fields())
                .from(ItemDeliveryTypes)
                .orderBy(ItemDeliveryTypes.SORT_ORDER, ItemDeliveryTypes.ITEM_DELIVERY_TYPE_NAME),
                ItemDeliveryTypeFactory.class);

        return itemDeliveryTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ItemDeliveryTypeChoicesBean getItemDeliveryTypeChoices(String defaultItemDeliveryTypeChoice, Language language, boolean allowNullChoice) {
        var itemDeliveryTypes = getItemDeliveryTypes();
        var size = itemDeliveryTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemDeliveryTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemDeliveryType : itemDeliveryTypes) {
            var label = getBestItemDeliveryTypeDescription(itemDeliveryType, language);
            var value = itemDeliveryType.getItemDeliveryTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemDeliveryTypeChoice != null && defaultItemDeliveryTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemDeliveryType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemDeliveryTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemDeliveryTypeTransfer getItemDeliveryTypeTransfer(UserVisit userVisit, ItemDeliveryType itemDeliveryType) {
        return itemDeliveryTypeTransferCache.getTransfer(userVisit, itemDeliveryType);
    }

    public List<ItemDeliveryTypeTransfer> getItemDeliveryTypeTransfers(UserVisit userVisit, Collection<ItemDeliveryType> entities) {
        var itemDeliveryTypeTransfers = new ArrayList<ItemDeliveryTypeTransfer>(entities.size());

        entities.forEach((entity) ->
                itemDeliveryTypeTransfers.add(itemDeliveryTypeTransferCache.getTransfer(userVisit, entity))
        );

        return itemDeliveryTypeTransfers;
    }

    public List<ItemDeliveryTypeTransfer> getItemDeliveryTypeTransfers(UserVisit userVisit) {
        return getItemDeliveryTypeTransfers(userVisit, getItemDeliveryTypes());
    }

    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDeliveryTypeDescriptionFactory itemDeliveryTypeDescriptionFactory;

    public ItemDeliveryTypeDescription createItemDeliveryTypeDescription(ItemDeliveryType itemDeliveryType, Language language,
            String description, BasePK createdBy) {
        var itemDeliveryTypeDescription = itemDeliveryTypeDescriptionFactory.create(itemDeliveryType, language, description);

        sendEvent(itemDeliveryType.getPrimaryKey(), EventTypes.MODIFY, itemDeliveryTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDeliveryTypeDescription;
    }
    
    public ItemDeliveryTypeDescription getItemDeliveryTypeDescription(ItemDeliveryType itemDeliveryType, Language language) {
        var query = session.getDslContext()
                .select(ItemDeliveryTypeDescriptions.fields())
                .from(ItemDeliveryTypeDescriptions)
                .where(ItemDeliveryTypeDescriptions.ITEM_DELIVERY_TYPE.eq(itemDeliveryType.getPrimaryKey()),
                        ItemDeliveryTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return itemDeliveryTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestItemDeliveryTypeDescription(ItemDeliveryType itemDeliveryType, Language language) {
        String description;
        var itemDeliveryTypeDescription = getItemDeliveryTypeDescription(itemDeliveryType, language);
        
        if(itemDeliveryTypeDescription == null && !language.getIsDefault()) {
            itemDeliveryTypeDescription = getItemDeliveryTypeDescription(itemDeliveryType, partyControl.getDefaultLanguage());
        }
        
        if(itemDeliveryTypeDescription == null) {
            description = itemDeliveryType.getItemDeliveryTypeName();
        } else {
            description = itemDeliveryTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemInventoryTypeFactory itemInventoryTypeFactory;

    @Inject
    ItemInventoryTypeTransferCache itemInventoryTypeTransferCache;

    public ItemInventoryType createItemInventoryType(String itemInventoryTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemInventoryType = itemInventoryTypeFactory.create(itemInventoryTypeName, isDefault, sortOrder);

        sendEvent(itemInventoryType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemInventoryType;
    }

    public long countItemInventoryTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemInventoryTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemInventoryType */
    public ItemInventoryType getItemInventoryTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemInventoryTypePK(entityInstance.getEntityUniqueId());

        return itemInventoryTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemInventoryType getItemInventoryTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemInventoryTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemInventoryType getItemInventoryTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemInventoryTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemInventoryType getItemInventoryTypeByName(String itemInventoryTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemInventoryTypes.fields())
                .from(ItemInventoryTypes)
                .where(ItemInventoryTypes.ITEM_INVENTORY_TYPE_NAME.eq(itemInventoryTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemInventoryTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemInventoryType getItemInventoryTypeByName(String itemInventoryTypeName) {
        return getItemInventoryTypeByName(itemInventoryTypeName, EntityPermission.READ_ONLY);
    }

    public ItemInventoryType getItemInventoryTypeByNameForUpdate(String itemInventoryTypeName) {
        return getItemInventoryTypeByName(itemInventoryTypeName, EntityPermission.READ_WRITE);
    }

    public ItemInventoryType getDefaultItemInventoryType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemInventoryTypes.fields())
                .from(ItemInventoryTypes)
                .where(ItemInventoryTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemInventoryTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemInventoryType getDefaultItemInventoryType() {
        return getDefaultItemInventoryType(EntityPermission.READ_ONLY);
    }

    public ItemInventoryType getDefaultItemInventoryTypeForUpdate() {
        return getDefaultItemInventoryType(EntityPermission.READ_WRITE);
    }

    public ItemInventoryTypeValue getDefaultItemInventoryTypeValueForUpdate() {
        return getDefaultItemInventoryTypeForUpdate().getItemInventoryTypeValue().clone();
    }

    public List<ItemInventoryType> getItemInventoryTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemInventoryTypes.fields())
                .from(ItemInventoryTypes)
                .orderBy(ItemInventoryTypes.SORT_ORDER, ItemInventoryTypes.ITEM_INVENTORY_TYPE_NAME),
                ItemInventoryTypeFactory.class);

        return itemInventoryTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ItemInventoryTypeChoicesBean getItemInventoryTypeChoices(String defaultItemInventoryTypeChoice, Language language, boolean allowNullChoice) {
        var itemInventoryTypes = getItemInventoryTypes();
        var size = itemInventoryTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemInventoryTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemInventoryType : itemInventoryTypes) {
            var label = getBestItemInventoryTypeDescription(itemInventoryType, language);
            var value = itemInventoryType.getItemInventoryTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemInventoryTypeChoice != null && defaultItemInventoryTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemInventoryType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemInventoryTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemInventoryTypeTransfer getItemInventoryTypeTransfer(UserVisit userVisit, ItemInventoryType itemInventoryType) {
        return itemInventoryTypeTransferCache.getTransfer(userVisit, itemInventoryType);
    }

    public List<ItemInventoryTypeTransfer> getItemInventoryTypeTransfers(UserVisit userVisit, Collection<ItemInventoryType> entities) {
        var itemInventoryTypeTransfers = new ArrayList<ItemInventoryTypeTransfer>(entities.size());

        entities.forEach((entity) ->
                itemInventoryTypeTransfers.add(itemInventoryTypeTransferCache.getTransfer(userVisit, entity))
        );

        return itemInventoryTypeTransfers;
    }

    public List<ItemInventoryTypeTransfer> getItemInventoryTypeTransfers(UserVisit userVisit) {
        return getItemInventoryTypeTransfers(userVisit, getItemInventoryTypes());
    }

    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemInventoryTypeDescriptionFactory itemInventoryTypeDescriptionFactory;

    public ItemInventoryTypeDescription createItemInventoryTypeDescription(ItemInventoryType itemInventoryType, Language language,
            String description, BasePK createdBy) {
        var itemInventoryTypeDescription = itemInventoryTypeDescriptionFactory.create(itemInventoryType, language, description);

        sendEvent(itemInventoryType.getPrimaryKey(), EventTypes.MODIFY, itemInventoryTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemInventoryTypeDescription;
    }
    
    public ItemInventoryTypeDescription getItemInventoryTypeDescription(ItemInventoryType itemInventoryType, Language language) {
        var query = session.getDslContext()
                .select(ItemInventoryTypeDescriptions.fields())
                .from(ItemInventoryTypeDescriptions)
                .where(ItemInventoryTypeDescriptions.ITEM_INVENTORY_TYPE.eq(itemInventoryType.getPrimaryKey()),
                        ItemInventoryTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return itemInventoryTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestItemInventoryTypeDescription(ItemInventoryType itemInventoryType, Language language) {
        String description;
        var itemInventoryTypeDescription = getItemInventoryTypeDescription(itemInventoryType, language);
        
        if(itemInventoryTypeDescription == null && !language.getIsDefault()) {
            itemInventoryTypeDescription = getItemInventoryTypeDescription(itemInventoryType, partyControl.getDefaultLanguage());
        }
        
        if(itemInventoryTypeDescription == null) {
            description = itemInventoryType.getItemInventoryTypeName();
        } else {
            description = itemInventoryTypeDescription.getDescription();
        }
        
        return description;
    }

    // --------------------------------------------------------------------------------
    //   Item Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemUseTypeFactory itemUseTypeFactory;

    @Inject
    ItemUseTypeTransferCache itemUseTypeTransferCache;

    public ItemUseType createItemUseType(String itemUseTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemUseType = itemUseTypeFactory.create(itemUseTypeName, isDefault, sortOrder);

        sendEvent(itemUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemUseType;
    }

    public long countItemUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemUseTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemUseType */
    public ItemUseType getItemUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemUseTypePK(entityInstance.getEntityUniqueId());

        return itemUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemUseType getItemUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemUseType getItemUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemUseType getItemUseTypeByName(String itemUseTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemUseTypes.fields())
                .from(ItemUseTypes)
                .where(ItemUseTypes.ITEM_USE_TYPE_NAME.eq(itemUseTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemUseType getItemUseTypeByName(String itemUseTypeName) {
        return getItemUseTypeByName(itemUseTypeName, EntityPermission.READ_ONLY);
    }

    public ItemUseType getItemUseTypeByNameForUpdate(String itemUseTypeName) {
        return getItemUseTypeByName(itemUseTypeName, EntityPermission.READ_WRITE);
    }

    public ItemUseType getDefaultItemUseType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemUseTypes.fields())
                .from(ItemUseTypes)
                .where(ItemUseTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemUseType getDefaultItemUseType() {
        return getDefaultItemUseType(EntityPermission.READ_ONLY);
    }

    public ItemUseType getDefaultItemUseTypeForUpdate() {
        return getDefaultItemUseType(EntityPermission.READ_WRITE);
    }

    public ItemUseTypeValue getDefaultItemUseTypeValueForUpdate() {
        return getDefaultItemUseTypeForUpdate().getItemUseTypeValue().clone();
    }

    public List<ItemUseType> getItemUseTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemUseTypes.fields())
                .from(ItemUseTypes)
                .orderBy(ItemUseTypes.SORT_ORDER, ItemUseTypes.ITEM_USE_TYPE_NAME),
                ItemUseTypeFactory.class);

        return itemUseTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public ItemUseTypeChoicesBean getItemUseTypeChoices(String defaultItemUseTypeChoice, Language language, boolean allowNullChoice) {
        var itemUseTypes = getItemUseTypes();
        var size = itemUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemUseType : itemUseTypes) {
            var label = getBestItemUseTypeDescription(itemUseType, language);
            var value = itemUseType.getItemUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultItemUseTypeChoice != null && defaultItemUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemUseType.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemUseTypeChoicesBean(labels, values, defaultValue);
    }

    public ItemUseTypeTransfer getItemUseTypeTransfer(UserVisit userVisit, ItemUseType itemUseType) {
        return itemUseTypeTransferCache.getTransfer(userVisit, itemUseType);
    }

    public List<ItemUseTypeTransfer> getItemUseTypeTransfers(UserVisit userVisit, Collection<ItemUseType> entities) {
        var itemUseTypeTransfers = new ArrayList<ItemUseTypeTransfer>(entities.size());

        entities.forEach((entity) ->
                itemUseTypeTransfers.add(itemUseTypeTransferCache.getTransfer(userVisit, entity))
        );

        return itemUseTypeTransfers;
    }

    public List<ItemUseTypeTransfer> getItemUseTypeTransfers(UserVisit userVisit) {
        return getItemUseTypeTransfers(userVisit, getItemUseTypes());
    }

    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemUseTypeDescriptionFactory itemUseTypeDescriptionFactory;

    public ItemUseTypeDescription createItemUseTypeDescription(ItemUseType itemUseType, Language language,
            String description, BasePK createdBy) {
        var itemUseTypeDescription = itemUseTypeDescriptionFactory.create(itemUseType, language, description);

        sendEvent(itemUseType.getPrimaryKey(), EventTypes.MODIFY, itemUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemUseTypeDescription;
    }

    public ItemUseTypeDescription getItemUseTypeDescription(ItemUseType itemUseType, Language language) {
        var query = session.getDslContext()
                .select(ItemUseTypeDescriptions.fields())
                .from(ItemUseTypeDescriptions)
                .where(ItemUseTypeDescriptions.ITEM_USE_TYPE.eq(itemUseType.getPrimaryKey()),
                        ItemUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return itemUseTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestItemUseTypeDescription(ItemUseType itemUseType, Language language) {
        String description;
        var itemUseTypeDescription = getItemUseTypeDescription(itemUseType, language);
        
        if(itemUseTypeDescription == null && !language.getIsDefault()) {
            itemUseTypeDescription = getItemUseTypeDescription(itemUseType, partyControl.getDefaultLanguage());
        }
        
        if(itemUseTypeDescription == null) {
            description = itemUseType.getItemUseTypeName();
        } else {
            description = itemUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Categories
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemCategoryDetailFactory itemCategoryDetailFactory;

    @Inject
    protected ItemCategoryFactory itemCategoryFactory;

    @Inject
    ItemCategoryTransferCache itemCategoryTransferCache;

    public ItemCategory createItemCategory(String itemCategoryName, ItemCategory parentItemCategory, Sequence itemSequence, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultItemCategory = getDefaultItemCategory();
        var defaultFound = defaultItemCategory != null;
        
        if(defaultFound && isDefault) {
            var defaultItemCategoryDetailValue = getDefaultItemCategoryDetailValueForUpdate();
            
            defaultItemCategoryDetailValue.setIsDefault(false);
            updateItemCategoryFromValue(defaultItemCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemCategory = itemCategoryFactory.create();
        var itemCategoryDetail = itemCategoryDetailFactory.create(
                itemCategory, itemCategoryName, parentItemCategory, itemSequence, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        itemCategory = itemCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemCategory.getPrimaryKey());
        itemCategory.setActiveDetail(itemCategoryDetail);
        itemCategory.setLastDetail(itemCategoryDetail);
        itemCategory.store();
        
        sendEvent(itemCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return itemCategory;
    }

    public long countItemCategories() {
        return session.getDslContext()
                .selectCount()
                .from(ItemCategories)
                .join(ItemCategoryDetails).onKey(ITEM_CATEGORIES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemCategoriesByParentItemCategory(ItemCategory parentItemCategory) {
        return session.getDslContext()
                .selectCount()
                .from(ItemCategories)
                .join(ItemCategoryDetails).onKey(ITEM_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ItemCategoryDetails.PARENT_ITEM_CATEGORY.eq(parentItemCategory.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemCategory */
    public ItemCategory getItemCategoryByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemCategoryPK(entityInstance.getEntityUniqueId());

        return itemCategoryFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemCategory getItemCategoryByEntityInstance(EntityInstance entityInstance) {
        return getItemCategoryByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemCategory getItemCategoryByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemCategoryByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemCategory getItemCategoryByName(String itemCategoryName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemCategories.fields())
                .from(ItemCategories)
                .join(ItemCategoryDetails).onKey(ITEM_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ItemCategoryDetails.ITEM_CATEGORY_NAME.eq(itemCategoryName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemCategoryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemCategory getItemCategoryByName(String itemCategoryName) {
        return getItemCategoryByName(itemCategoryName, EntityPermission.READ_ONLY);
    }
    
    public ItemCategory getItemCategoryByNameForUpdate(String itemCategoryName) {
        return getItemCategoryByName(itemCategoryName, EntityPermission.READ_WRITE);
    }
    
    public ItemCategoryDetailValue getItemCategoryDetailValueForUpdate(ItemCategory itemCategory) {
        return itemCategory == null? null: itemCategory.getLastDetailForUpdate().getItemCategoryDetailValue().clone();
    }
    
    public ItemCategoryDetailValue getItemCategoryDetailValueByNameForUpdate(String itemCategoryName) {
        return getItemCategoryDetailValueForUpdate(getItemCategoryByNameForUpdate(itemCategoryName));
    }
    
    private ItemCategory getDefaultItemCategory(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemCategories.fields())
                .from(ItemCategories)
                .join(ItemCategoryDetails).onKey(ITEM_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ItemCategoryDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemCategoryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemCategory getDefaultItemCategory() {
        return getDefaultItemCategory(EntityPermission.READ_ONLY);
    }
    
    public ItemCategory getDefaultItemCategoryForUpdate() {
        return getDefaultItemCategory(EntityPermission.READ_WRITE);
    }
    
    public ItemCategoryDetailValue getDefaultItemCategoryDetailValueForUpdate() {
        return getDefaultItemCategoryForUpdate().getLastDetailForUpdate().getItemCategoryDetailValue().clone();
    }
    
    private List<ItemCategory> getItemCategories(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemCategories.fields())
                .from(ItemCategories)
                .join(ItemCategoryDetails).onKey(ITEM_CATEGORIES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemCategoryDetails.SORT_ORDER, ItemCategoryDetails.ITEM_CATEGORY_NAME),
                    ItemCategoryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemCategory> getItemCategories() {
        return getItemCategories(EntityPermission.READ_ONLY);
    }
    
    public List<ItemCategory> getItemCategoriesForUpdate() {
        return getItemCategories(EntityPermission.READ_WRITE);
    }
    
    private List<ItemCategory> getItemCategoriesByParentItemCategory(ItemCategory parentItemCategory,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemCategories.fields())
                .from(ItemCategories)
                .join(ItemCategoryDetails).onKey(ITEM_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ItemCategoryDetails.PARENT_ITEM_CATEGORY.eq(parentItemCategory.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemCategoryDetails.SORT_ORDER, ItemCategoryDetails.ITEM_CATEGORY_NAME),
                    ItemCategoryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemCategory> getItemCategoriesByParentItemCategory(ItemCategory parentItemCategory) {
        return getItemCategoriesByParentItemCategory(parentItemCategory, EntityPermission.READ_ONLY);
    }

    public List<ItemCategory> getItemCategoriesByParentItemCategoryForUpdate(ItemCategory parentItemCategory) {
        return getItemCategoriesByParentItemCategory(parentItemCategory, EntityPermission.READ_WRITE);
    }

    public ItemCategoryTransfer getItemCategoryTransfer(UserVisit userVisit, ItemCategory itemCategory) {
        return itemCategoryTransferCache.getTransfer(userVisit, itemCategory);
    }

    public List<ItemCategoryTransfer> getItemCategoryTransfers(UserVisit userVisit, Collection<ItemCategory> itemCategories) {
        List<ItemCategoryTransfer> itemCategoryTransfers = new ArrayList<>(itemCategories.size());

        itemCategories.forEach((itemCategory) ->
                itemCategoryTransfers.add(itemCategoryTransferCache.getTransfer(userVisit, itemCategory))
        );

        return itemCategoryTransfers;
    }

    public List<ItemCategoryTransfer> getItemCategoryTransfers(UserVisit userVisit) {
        return getItemCategoryTransfers(userVisit, getItemCategories());
    }

    public List<ItemCategoryTransfer> getItemCategoryTransfersByParentItemCategory(UserVisit userVisit,
            ItemCategory parentItemCategory) {
        return getItemCategoryTransfers(userVisit, getItemCategoriesByParentItemCategory(parentItemCategory));
    }

    public ItemCategoryChoicesBean getItemCategoryChoices(String defaultItemCategoryChoice, Language language,
            boolean allowNullChoice) {
        var itemCategories = getItemCategories();
        var size = itemCategories.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemCategory : itemCategories) {
            var itemCategoryDetail = itemCategory.getLastDetail();
            
            var label = allowNullChoice ? getBestItemCategoryDescription(itemCategory, language) + (itemCategoryDetail.getIsDefault() ? " *" : "") : getBestItemCategoryDescription(itemCategory, language);
            var value = itemCategoryDetail.getItemCategoryName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemCategoryChoice != null && defaultItemCategoryChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemCategoryDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentItemCategorySafe(ItemCategory itemCategory, ItemCategory parentItemCategory) {
        var safe = true;
        
        if(parentItemCategory != null) {
            Set<ItemCategory> parentItemCategories = new HashSet<>();
            
            parentItemCategories.add(itemCategory);
            do {
                if(parentItemCategories.contains(parentItemCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemCategories.add(parentItemCategory);
                parentItemCategory = parentItemCategory.getLastDetail().getParentItemCategory();
            } while(parentItemCategory != null);
        }
        
        return safe;
    }
    
    private void updateItemCategoryFromValue(ItemCategoryDetailValue itemCategoryDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemCategoryDetailValue.hasBeenModified()) {
            var itemCategory = itemCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemCategoryDetailValue.getItemCategoryPK());
            var itemCategoryDetail = itemCategory.getActiveDetailForUpdate();
            
            itemCategoryDetail.setThruTime(session.getStartTime());
            itemCategoryDetail.store();

            var itemCategoryPK = itemCategoryDetail.getItemCategoryPK();
            var itemCategoryName = itemCategoryDetailValue.getItemCategoryName();
            var parentItemCategoryPK = itemCategoryDetailValue.getParentItemCategoryPK();
            var itemSequencePK = itemCategoryDetailValue.getItemSequencePK();
            var isDefault = itemCategoryDetailValue.getIsDefault();
            var sortOrder = itemCategoryDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultItemCategory = getDefaultItemCategory();
                var defaultFound = defaultItemCategory != null && !defaultItemCategory.equals(itemCategory);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultItemCategoryDetailValue = getDefaultItemCategoryDetailValueForUpdate();
                    
                    defaultItemCategoryDetailValue.setIsDefault(false);
                    updateItemCategoryFromValue(defaultItemCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            itemCategoryDetail = itemCategoryDetailFactory.create(itemCategoryPK,
                    itemCategoryName, parentItemCategoryPK, itemSequencePK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            itemCategory.setActiveDetail(itemCategoryDetail);
            itemCategory.setLastDetail(itemCategoryDetail);
            
            sendEvent(itemCategoryPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateItemCategoryFromValue(ItemCategoryDetailValue itemCategoryDetailValue, BasePK updatedBy) {
        updateItemCategoryFromValue(itemCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteItemCategory(ItemCategory itemCategory, boolean checkDefault, BasePK deletedBy) {
        deleteItemCategoriesByParentItemCategory(itemCategory, deletedBy);
        deleteItemCategoryDescriptionsByItemCategory(itemCategory, deletedBy);

        var itemCategoryDetail = itemCategory.getLastDetailForUpdate();
        itemCategoryDetail.setThruTime(session.getStartTime());
        itemCategory.setActiveDetail(null);
        itemCategory.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultItemCategory = getDefaultItemCategory();

            if(defaultItemCategory == null) {
                var itemCategories = getItemCategoriesForUpdate();

                if(!itemCategories.isEmpty()) {
                    var iter = itemCategories.iterator();
                    if(iter.hasNext()) {
                        defaultItemCategory = iter.next();
                    }
                    var itemCategoryDetailValue = Objects.requireNonNull(defaultItemCategory).getLastDetailForUpdate().getItemCategoryDetailValue().clone();

                    itemCategoryDetailValue.setIsDefault(true);
                    updateItemCategoryFromValue(itemCategoryDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(itemCategory.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteItemCategory(ItemCategory itemCategory, BasePK deletedBy) {
        deleteItemCategory(itemCategory, true, deletedBy);
    }

    private void deleteItemCategories(List<ItemCategory> itemCategories, boolean checkDefault, BasePK deletedBy) {
        itemCategories.forEach((itemCategory) -> deleteItemCategory(itemCategory, checkDefault, deletedBy));
    }

    public void deleteItemCategories(List<ItemCategory> itemCategories, BasePK deletedBy) {
        deleteItemCategories(itemCategories, true, deletedBy);
    }

    private void deleteItemCategoriesByParentItemCategory(ItemCategory parentItemCategory, BasePK deletedBy) {
        deleteItemCategories(getItemCategoriesByParentItemCategoryForUpdate(parentItemCategory), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Category Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemCategoryDescriptionFactory itemCategoryDescriptionFactory;

    @Inject
    ItemCategoryDescriptionTransferCache itemCategoryDescriptionTransferCache;

    public ItemCategoryDescription createItemCategoryDescription(ItemCategory itemCategory, Language language, String description, BasePK createdBy) {
        var itemCategoryDescription = itemCategoryDescriptionFactory.create(itemCategory, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(itemCategory.getPrimaryKey(), EventTypes.MODIFY, itemCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemCategoryDescription;
    }
    
    private ItemCategoryDescription getItemCategoryDescription(ItemCategory itemCategory, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemCategoryDescriptions.fields())
                .from(ItemCategoryDescriptions)
                .where(ItemCategoryDescriptions.ITEM_CATEGORY.eq(itemCategory.getPrimaryKey()),
                        ItemCategoryDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemCategoryDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemCategoryDescription getItemCategoryDescription(ItemCategory itemCategory, Language language) {
        return getItemCategoryDescription(itemCategory, language, EntityPermission.READ_ONLY);
    }
    
    public ItemCategoryDescription getItemCategoryDescriptionForUpdate(ItemCategory itemCategory, Language language) {
        return getItemCategoryDescription(itemCategory, language, EntityPermission.READ_WRITE);
    }
    
    public ItemCategoryDescriptionValue getItemCategoryDescriptionValue(ItemCategoryDescription itemCategoryDescription) {
        return itemCategoryDescription == null? null: itemCategoryDescription.getItemCategoryDescriptionValue().clone();
    }
    
    public ItemCategoryDescriptionValue getItemCategoryDescriptionValueForUpdate(ItemCategory itemCategory, Language language) {
        return getItemCategoryDescriptionValue(getItemCategoryDescriptionForUpdate(itemCategory, language));
    }
    
    private List<ItemCategoryDescription> getItemCategoryDescriptionsByItemCategory(ItemCategory itemCategory, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemCategoryDescriptions.fields())
                    .from(ItemCategoryDescriptions)
                    .join(Languages).onKey(ITEM_CATEGORY_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemCategoryDescriptions.ITEM_CATEGORY.eq(itemCategory.getPrimaryKey()),
                            ItemCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemCategoryDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemCategoryDescriptions.fields())
                    .from(ItemCategoryDescriptions)
                    .where(ItemCategoryDescriptions.ITEM_CATEGORY.eq(itemCategory.getPrimaryKey()),
                            ItemCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemCategoryDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemCategoryDescription> getItemCategoryDescriptionsByItemCategory(ItemCategory itemCategory) {
        return getItemCategoryDescriptionsByItemCategory(itemCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ItemCategoryDescription> getItemCategoryDescriptionsByItemCategoryForUpdate(ItemCategory itemCategory) {
        return getItemCategoryDescriptionsByItemCategory(itemCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestItemCategoryDescription(ItemCategory itemCategory, Language language) {
        String description;
        var itemCategoryDescription = getItemCategoryDescription(itemCategory, language);
        
        if(itemCategoryDescription == null && !language.getIsDefault()) {
            itemCategoryDescription = getItemCategoryDescription(itemCategory, partyControl.getDefaultLanguage());
        }
        
        if(itemCategoryDescription == null) {
            description = itemCategory.getLastDetail().getItemCategoryName();
        } else {
            description = itemCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public ItemCategoryDescriptionTransfer getItemCategoryDescriptionTransfer(UserVisit userVisit, ItemCategoryDescription itemCategoryDescription) {
        return itemCategoryDescriptionTransferCache.getTransfer(userVisit, itemCategoryDescription);
    }
    
    public List<ItemCategoryDescriptionTransfer> getItemCategoryDescriptionTransfersByItemCategory(UserVisit userVisit, ItemCategory itemCategory) {
        var itemCategoryDescriptions = getItemCategoryDescriptionsByItemCategory(itemCategory);
        List<ItemCategoryDescriptionTransfer> itemCategoryDescriptionTransfers = new ArrayList<>(itemCategoryDescriptions.size());
        
        itemCategoryDescriptions.forEach((itemCategoryDescription) ->
                itemCategoryDescriptionTransfers.add(itemCategoryDescriptionTransferCache.getTransfer(userVisit, itemCategoryDescription))
        );
        
        return itemCategoryDescriptionTransfers;
    }
    
    public void updateItemCategoryDescriptionFromValue(ItemCategoryDescriptionValue itemCategoryDescriptionValue, BasePK updatedBy) {
        if(itemCategoryDescriptionValue.hasBeenModified()) {
            var itemCategoryDescription = itemCategoryDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemCategoryDescriptionValue.getPrimaryKey());
            
            itemCategoryDescription.setThruTime(session.getStartTime());
            itemCategoryDescription.store();

            var itemCategory = itemCategoryDescription.getItemCategory();
            var language = itemCategoryDescription.getLanguage();
            var description = itemCategoryDescriptionValue.getDescription();
            
            itemCategoryDescription = itemCategoryDescriptionFactory.create(itemCategory, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemCategory.getPrimaryKey(), EventTypes.MODIFY, itemCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemCategoryDescription(ItemCategoryDescription itemCategoryDescription, BasePK deletedBy) {
        itemCategoryDescription.setThruTime(session.getStartTime());
        
        sendEvent(itemCategoryDescription.getItemCategoryPK(), EventTypes.MODIFY, itemCategoryDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteItemCategoryDescriptionsByItemCategory(ItemCategory itemCategory, BasePK deletedBy) {
        var itemCategoryDescriptions = getItemCategoryDescriptionsByItemCategoryForUpdate(itemCategory);
        
        itemCategoryDescriptions.forEach((itemCategoryDescription) -> 
                deleteItemCategoryDescription(itemCategoryDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Items
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDetailFactory itemDetailFactory;

    @Inject
    protected ItemFactory itemFactory;

    @Inject
    ItemTransferCache itemTransferCache;

    public Item createItem(String itemName, ItemType itemType, ItemUseType itemUseType, ItemCategory itemCategory,
            ItemAccountingCategory itemAccountingCategory, ItemPurchasingCategory itemPurchasingCategory, Party companyParty,
            ItemDeliveryType itemDeliveryType, ItemInventoryType itemInventoryType, Boolean inventorySerialized, Sequence serialNumberSequence,
            Boolean shippingChargeExempt, Long shippingStartTime, Long shippingEndTime, Long salesOrderStartTime, Long salesOrderEndTime,
            Long purchaseOrderStartTime, Long purchaseOrderEndTime, Boolean allowClubDiscounts, Boolean allowCouponDiscounts, Boolean allowAssociatePayments,
            UnitOfMeasureKind unitOfMeasureKind, ItemPriceType itemPriceType, CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy,
            StylePath stylePath, BasePK createdBy) {
        var item = itemFactory.create();
        var itemDetail = itemDetailFactory.create(item, itemName, itemType, itemUseType, itemCategory, itemAccountingCategory,
                itemPurchasingCategory, companyParty, itemDeliveryType, itemInventoryType, inventorySerialized, serialNumberSequence, shippingChargeExempt,
                shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime, purchaseOrderStartTime, purchaseOrderEndTime, allowClubDiscounts,
                allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKind, itemPriceType, cancellationPolicy, returnPolicy, stylePath,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        item = itemFactory.getEntityFromPK(EntityPermission.READ_WRITE, item.getPrimaryKey());
        item.setActiveDetail(itemDetail);
        item.setLastDetail(itemDetail);
        item.store();
        
        sendEvent(item.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return item;
    }
    
    public long countItems() {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByItemCategory(ItemCategory itemCategory) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.ITEM_CATEGORY.eq(itemCategory.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.ITEM_ACCOUNTING_CATEGORY.eq(itemAccountingCategory.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.ITEM_PURCHASING_CATEGORY.eq(itemPurchasingCategory.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByCompanyParty(Party companyParty) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByReturnPolicy(ReturnPolicy returnPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemsByStylePath(StylePath stylePath) {
        return session.getDslContext()
                .selectCount()
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.STYLE_PATH.eq(stylePath.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Item */
    public Item getItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemPK(entityInstance.getEntityUniqueId());

        return itemFactory.getEntityFromPK(entityPermission, pk);
    }

    public Item getItemByEntityInstance(EntityInstance entityInstance) {
        return getItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Item getItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Item> getItems(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Items.fields())
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Item> getItems() {
        return getItems(EntityPermission.READ_ONLY);
    }

    public List<Item> getItemsForUpdate() {
        return getItems(EntityPermission.READ_WRITE);
    }

    private List<Item> getItemsByItemCategory(EntityPermission entityPermission, ItemCategory itemCategory) {
        var baseQuery = session.getDslContext()
                .select(Items.fields())
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.ITEM_CATEGORY.eq(itemCategory.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Item> getItemsByItemCategory(ItemCategory itemCategory) {
        return getItemsByItemCategory(EntityPermission.READ_ONLY, itemCategory);
    }

    public List<Item> getItemsByItemCategoryForUpdate(ItemCategory itemCategory) {
        return getItemsByItemCategory(EntityPermission.READ_WRITE, itemCategory);
    }

    private List<Item> getItemsByItemAccountingCategory(EntityPermission entityPermission, ItemAccountingCategory itemAccountingCategory) {
        var baseQuery = session.getDslContext()
                .select(Items.fields())
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.ITEM_ACCOUNTING_CATEGORY.eq(itemAccountingCategory.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Item> getItemsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return getItemsByItemAccountingCategory(EntityPermission.READ_ONLY, itemAccountingCategory);
    }

    public List<Item> getItemsByItemAccountingCategoryForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return getItemsByItemAccountingCategory(EntityPermission.READ_WRITE, itemAccountingCategory);
    }

    private List<Item> getItemsByItemPurchasingCategory(EntityPermission entityPermission, ItemPurchasingCategory itemPurchasingCategory) {
        var baseQuery = session.getDslContext()
                .select(Items.fields())
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.ITEM_PURCHASING_CATEGORY.eq(itemPurchasingCategory.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Item> getItemsByItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory) {
        return getItemsByItemPurchasingCategory(EntityPermission.READ_ONLY, itemPurchasingCategory);
    }

    public List<Item> getItemsByItemPurchasingCategoryForUpdate(ItemPurchasingCategory itemPurchasingCategory) {
        return getItemsByItemPurchasingCategory(EntityPermission.READ_WRITE, itemPurchasingCategory);
    }

    private List<Item> getItemsByCompanyParty(EntityPermission entityPermission, Party companyParty) {
        var baseQuery = session.getDslContext()
                .select(Items.fields())
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Item> getItemsByCompanyParty(Party companyParty) {
        return getItemsByCompanyParty(EntityPermission.READ_ONLY, companyParty);
    }

    public List<Item> getItemsByCompanyPartyForUpdate(Party companyParty) {
        return getItemsByCompanyParty(EntityPermission.READ_WRITE, companyParty);
    }

    private List<Item> getItemsByUnitOfMeasureKind(EntityPermission entityPermission, UnitOfMeasureKind unitOfMeasureKind) {
        var baseQuery = session.getDslContext()
                .select(Items.fields())
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Item> getItemsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getItemsByUnitOfMeasureKind(EntityPermission.READ_ONLY, unitOfMeasureKind);
    }

    public List<Item> getItemsByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getItemsByUnitOfMeasureKind(EntityPermission.READ_WRITE, unitOfMeasureKind);
    }

    private Item getItemByName(String itemName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Items.fields())
                .from(Items)
                .join(ItemDetails).onKey(ITEMS_ACTIVE_DETAIL_FK)
                .where(ItemDetails.ITEM_NAME.eq(itemName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Item getItemByName(String itemName) {
        return getItemByName(itemName, EntityPermission.READ_ONLY);
    }
    
    public Item getItemByNameForUpdate(String itemName) {
        return getItemByName(itemName, EntityPermission.READ_WRITE);
    }
    
    public ItemDetailValue getItemDetailValueForUpdate(Item item) {
        return item == null? null: item.getLastDetailForUpdate().getItemDetailValue().clone();
    }
    
    public ItemDetailValue getItemDetailValueByNameForUpdate(String itemName) {
        return getItemDetailValueForUpdate(getItemByNameForUpdate(itemName));
    }
    
    private Item getItemByNameThenAlias(String itemName, EntityPermission entityPermission) {
        var item = getItemByName(itemName);
        
        if(item == null) {
            var itemAlias = getItemAliasByAlias(itemName);
            
            if(itemAlias != null) {
                item = itemAlias.getItem(entityPermission);
            }
        }
        
        return item;
    }
    
    public Item getItemByNameThenAlias(String itemName) {
        return getItemByNameThenAlias(itemName, EntityPermission.READ_ONLY);
    }
    
    public Item getItemByNameThenAliasForUpdate(String itemName) {
        return getItemByNameThenAlias(itemName, EntityPermission.READ_WRITE);
    }

    public ItemTransfer getItemTransfer(UserVisit userVisit, Item item) {
        return itemTransferCache.getTransfer(userVisit, item);
    }

    public List<ItemTransfer> getItemTransfers(UserVisit userVisit, Collection<Item> items) {
        List<ItemTransfer> itemTransfers = new ArrayList<>(items.size());

        items.forEach((item) ->
                itemTransfers.add(itemTransferCache.getTransfer(userVisit, item))
        );

        return itemTransfers;
    }

    public List<ItemTransfer> getItemTransfers(UserVisit userVisit) {
        return getItemTransfers(userVisit, getItems());
    }

    public ItemStatusChoicesBean getItemStatusChoices(String defaultItemStatusChoice, Language language, boolean allowNullChoice,
            Item item, PartyPK partyPK) {
        var itemStatusChoicesBean = new ItemStatusChoicesBean();
        
        if(item == null) {
            workflowControl.getWorkflowEntranceChoices(itemStatusChoicesBean, defaultItemStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(ItemStatusConstants.Workflow_ITEM_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(item.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(ItemStatusConstants.Workflow_ITEM_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(itemStatusChoicesBean, defaultItemStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return itemStatusChoicesBean;
    }
    
    public void setItemStatus(ExecutionErrorAccumulator eea, Item item, String itemStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(item);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(ItemStatusConstants.Workflow_ITEM_STATUS,
                entityInstance);
        var workflowDestination = itemStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), itemStatusChoice);
        
        if(workflowDestination != null || itemStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownItemStatusChoice.name(), itemStatusChoice);
        }
    }
    
    public void updateItemFromValue(ItemDetailValue itemDetailValue, BasePK updatedBy) {
        if(itemDetailValue.hasBeenModified()) {
            var item = itemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDetailValue.getItemPK());
            var itemDetail = item.getActiveDetailForUpdate();
            
            itemDetail.setThruTime(session.getStartTime());
            itemDetail.store();

            var itemPK = itemDetail.getItemPK(); // Not updated
            var itemName = itemDetailValue.getItemName(); // Not updated
            var itemTypePK = itemDetail.getItemTypePK(); // Not updated
            var itemUseTypePK = itemDetail.getItemUseTypePK(); // Not updated
            var itemCategoryPK = itemDetailValue.getItemCategoryPK();
            var itemAccountingCategoryPK = itemDetailValue.getItemAccountingCategoryPK();
            var itemPurchasingCategoryPK = itemDetailValue.getItemPurchasingCategoryPK();
            var companyPartyPK = itemDetail.getCompanyPartyPK(); // Not updated
            var itemDeliveryTypePK = itemDetail.getItemDeliveryTypePK(); // Not updated
            var itemInventoryTypePK = itemDetail.getItemInventoryTypePK(); // Not updated
            var inventorySerialized = itemDetail.getInventorySerialized(); // Not updated
            var serialNumberSequencePK = itemDetail.getSerialNumberSequencePK(); // Not updated
            var shippingChargeExempt = itemDetailValue.getShippingChargeExempt();
            var shippingStartTime = itemDetailValue.getShippingStartTime();
            var shippingEndTime = itemDetailValue.getShippingEndTime();
            var salesOrderStartTime = itemDetailValue.getSalesOrderStartTime();
            var salesOrderEndTime = itemDetailValue.getSalesOrderEndTime();
            var purchaseOrderStartTime = itemDetailValue.getPurchaseOrderStartTime();
            var purchaseOrderEndTime = itemDetailValue.getPurchaseOrderEndTime();
            var allowClubDiscounts = itemDetailValue.getAllowClubDiscounts();
            var allowCouponDiscounts = itemDetailValue.getAllowCouponDiscounts();
            var allowAssociatePayments = itemDetailValue.getAllowAssociatePayments();
            var unitOfMeasureKindPK = itemDetail.getUnitOfMeasureKindPK(); // Not updated
            var itemPriceTypePK = itemDetail.getItemPriceTypePK(); // Not updated
            var cancellationPolicyPK = itemDetailValue.getCancellationPolicyPK();
            var returnPolicyPK = itemDetailValue.getReturnPolicyPK();
            var stylePathPK = itemDetail.getStylePathPK(); // Not updated
                    
            itemDetail = itemDetailFactory.create(itemPK, itemName, itemTypePK, itemUseTypePK, itemCategoryPK, itemAccountingCategoryPK,
                    itemPurchasingCategoryPK, companyPartyPK, itemDeliveryTypePK, itemInventoryTypePK, inventorySerialized, serialNumberSequencePK,
                    shippingChargeExempt, shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime, purchaseOrderStartTime,
                    purchaseOrderEndTime, allowClubDiscounts, allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKindPK, itemPriceTypePK,
                    cancellationPolicyPK, returnPolicyPK, stylePathPK, session.getStartTime(), Session.MAX_TIME);
            
            item.setActiveDetail(itemDetail);
            item.setLastDetail(itemDetail);
            
            sendEvent(itemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public Item getItemByPK(ItemPK itemPK) {
        return itemFactory.getEntityFromPK(EntityPermission.READ_ONLY, itemPK);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemUnitOfMeasureTypeFactory itemUnitOfMeasureTypeFactory;

    @Inject
    ItemUnitOfMeasureTypeTransferCache itemUnitOfMeasureTypeTransferCache;

    public ItemUnitOfMeasureType createItemUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultItemUnitOfMeasureType = getDefaultItemUnitOfMeasureType(item);
        var defaultFound = defaultItemUnitOfMeasureType != null;
        
        if(defaultFound && isDefault) {
            var defaultItemUnitOfMeasureTypeValue = getDefaultItemUnitOfMeasureTypeValueForUpdate(item);
            
            defaultItemUnitOfMeasureTypeValue.setIsDefault(false);
            updateItemUnitOfMeasureTypeFromValue(defaultItemUnitOfMeasureTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemUnitOfMeasureType = itemUnitOfMeasureTypeFactory.create(item, unitOfMeasureType,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitOfMeasureType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitOfMeasureType;
    }

    public long countItemUnitOfMeasureTypesByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitOfMeasureTypes)
                .where(ItemUnitOfMeasureTypes.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitOfMeasureTypesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitOfMeasureTypes)
                .where(ItemUnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemUnitOfMeasureType getItemUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemUnitOfMeasureTypes.fields())
                .from(ItemUnitOfMeasureTypes)
                .where(ItemUnitOfMeasureTypes.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemUnitOfMeasureTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemUnitOfMeasureType getItemUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public ItemUnitOfMeasureType getItemUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public ItemUnitOfMeasureTypeValue getItemUnitOfMeasureTypeValue(ItemUnitOfMeasureType itemUnitOfMeasureType) {
        return itemUnitOfMeasureType == null? null: itemUnitOfMeasureType.getItemUnitOfMeasureTypeValue().clone();
    }

    public ItemUnitOfMeasureTypeValue getItemUnitOfMeasureTypeValueForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        var itemUnitOfMeasureType = getItemUnitOfMeasureTypeForUpdate(item, unitOfMeasureType);
        
        return itemUnitOfMeasureType == null? null: itemUnitOfMeasureType.getItemUnitOfMeasureTypeValue().clone();
    }
    
    private ItemUnitOfMeasureType getDefaultItemUnitOfMeasureType(Item item, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemUnitOfMeasureTypes.fields())
                .from(ItemUnitOfMeasureTypes)
                .where(ItemUnitOfMeasureTypes.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitOfMeasureTypes.IS_DEFAULT.eq(true),
                        ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemUnitOfMeasureTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemUnitOfMeasureType getDefaultItemUnitOfMeasureType(Item item) {
        return getDefaultItemUnitOfMeasureType(item, EntityPermission.READ_ONLY);
    }
    
    public ItemUnitOfMeasureType getDefaultItemUnitOfMeasureTypeForUpdate(Item item) {
        return getDefaultItemUnitOfMeasureType(item, EntityPermission.READ_WRITE);
    }
    
    public ItemUnitOfMeasureTypeValue getDefaultItemUnitOfMeasureTypeValueForUpdate(Item item) {
        var itemUnitOfMeasureType = getDefaultItemUnitOfMeasureTypeForUpdate(item);
        
        return itemUnitOfMeasureType == null? null: itemUnitOfMeasureType.getItemUnitOfMeasureTypeValue().clone();
    }
    
    private List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitOfMeasureTypes.fields())
                    .from(ItemUnitOfMeasureTypes)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_OF_MEASURE_TYPES_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(UnitOfMeasureKinds).onKey(UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_KIND_FK)
                    .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KINDS_LAST_DETAIL_FK)
                    .where(ItemUnitOfMeasureTypes.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME),
                    ItemUnitOfMeasureTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitOfMeasureTypes.fields())
                    .from(ItemUnitOfMeasureTypes)
                    .where(ItemUnitOfMeasureTypes.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitOfMeasureTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByItem(Item item) {
        return getItemUnitOfMeasureTypesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByItemForUpdate(Item item) {
        return getItemUnitOfMeasureTypesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitOfMeasureTypes.fields())
                    .from(ItemUnitOfMeasureTypes)
                    .join(Items).onKey(ITEM_UNIT_OF_MEASURE_TYPES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ItemUnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemUnitOfMeasureTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitOfMeasureTypes.fields())
                    .from(ItemUnitOfMeasureTypes)
                    .where(ItemUnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitOfMeasureTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitOfMeasureTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemUnitOfMeasureTypeTransfer> getItemUnitOfMeasureTypeTransfers(UserVisit userVisit, Collection<ItemUnitOfMeasureType> itemUnitOfMeasureTypes) {
        List<ItemUnitOfMeasureTypeTransfer> itemUnitOfMeasureTypeTransfers = new ArrayList<>(itemUnitOfMeasureTypes.size());
        
        itemUnitOfMeasureTypes.forEach((itemUnitOfMeasureType) ->
                itemUnitOfMeasureTypeTransfers.add(itemUnitOfMeasureTypeTransferCache.getTransfer(userVisit, itemUnitOfMeasureType))
        );
        
        return itemUnitOfMeasureTypeTransfers;
    }
    
    public List<ItemUnitOfMeasureTypeTransfer> getItemUnitOfMeasureTypeTransfersByItem(UserVisit userVisit, Item item) {
        return getItemUnitOfMeasureTypeTransfers(userVisit, getItemUnitOfMeasureTypesByItem(item));
    }
    
    public List<ItemUnitOfMeasureTypeTransfer> getItemUnitOfMeasureTypeTransfersByUnitOfMeasureType(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitOfMeasureTypeTransfers(userVisit, getItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType));
    }
    
    public ItemUnitOfMeasureTypeTransfer getItemUnitOfMeasureTypeTransfer(UserVisit userVisit, ItemUnitOfMeasureType itemUnitOfMeasureType) {
        return itemUnitOfMeasureTypeTransferCache.getTransfer(userVisit, itemUnitOfMeasureType);
    }
    
    private void updateItemUnitOfMeasureTypeFromValue(ItemUnitOfMeasureTypeValue itemUnitOfMeasureTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(itemUnitOfMeasureTypeValue.hasBeenModified()) {
            var itemUnitOfMeasureType = itemUnitOfMeasureTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitOfMeasureTypeValue.getPrimaryKey());
            
            itemUnitOfMeasureType.setThruTime(session.getStartTime());
            itemUnitOfMeasureType.store();

            var item = itemUnitOfMeasureType.getItem(); // Not Updated
            var itemPK = item.getPrimaryKey(); // Not Updated
            var unitOfMeasureTypePK = itemUnitOfMeasureType.getUnitOfMeasureTypePK(); // Not Updated
            var isDefault = itemUnitOfMeasureTypeValue.getIsDefault();
            var sortOrder = itemUnitOfMeasureTypeValue.getSortOrder();
            
            if(checkDefault) {
                var defaultItemUnitOfMeasureType = getDefaultItemUnitOfMeasureType(item);
                var defaultFound = defaultItemUnitOfMeasureType != null && !defaultItemUnitOfMeasureType.equals(itemUnitOfMeasureType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultItemUnitOfMeasureTypeValue = getDefaultItemUnitOfMeasureTypeValueForUpdate(item);
                    
                    defaultItemUnitOfMeasureTypeValue.setIsDefault(false);
                    updateItemUnitOfMeasureTypeFromValue(defaultItemUnitOfMeasureTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            itemUnitOfMeasureType = itemUnitOfMeasureTypeFactory.create(itemPK, unitOfMeasureTypePK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemUnitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateItemUnitOfMeasureTypeFromValue(ItemUnitOfMeasureTypeValue itemUnitOfMeasureTypeValue, BasePK updatedBy) {
        updateItemUnitOfMeasureTypeFromValue(itemUnitOfMeasureTypeValue, true, updatedBy);
    }
    
    public void deleteItemUnitOfMeasureType(ItemUnitOfMeasureType itemUnitOfMeasureType, BasePK deletedBy) {
        var item = itemUnitOfMeasureType.getItem();
        var unitOfMeasureType = itemUnitOfMeasureType.getUnitOfMeasureType();
        
        deleteItemAliasesByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemKitMembersByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemPackCheckRequirementsByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemUnitCustomerTypeLimitsByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemUnitLimitsByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemUnitPriceLimitsByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemVolumeByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        deleteItemWeightByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        offerItemLogic.deleteOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        vendorControl.deleteVendorItemCostsByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        
        itemUnitOfMeasureType.setThruTime(session.getStartTime());
        itemUnitOfMeasureType.store();
        
        // Check for default, and pick one if necessary
        var defaultItemUnitOfMeasureType = getDefaultItemUnitOfMeasureType(item);
        if(defaultItemUnitOfMeasureType == null) {
            var itemUnitOfMeasureTypes = getItemUnitOfMeasureTypesByItemForUpdate(item);
            
            if(!itemUnitOfMeasureTypes.isEmpty()) {
                var iter = itemUnitOfMeasureTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemUnitOfMeasureType = iter.next();
                }
                var itemUnitOfMeasureTypeValue = defaultItemUnitOfMeasureType.getItemUnitOfMeasureTypeValue().clone();
                
                itemUnitOfMeasureTypeValue.setIsDefault(true);
                updateItemUnitOfMeasureTypeFromValue(itemUnitOfMeasureTypeValue, false, deletedBy);
            }
        }
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitOfMeasureType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemUnitOfMeasureTypes(List<ItemUnitOfMeasureType> itemUnitOfMeasureTypes, BasePK deletedBy) {
        itemUnitOfMeasureTypes.forEach((itemUnitOfMeasureType) -> 
                deleteItemUnitOfMeasureType(itemUnitOfMeasureType, deletedBy)
        );
    }
    
    public void deleteItemUnitOfMeasureTypesByItem(Item item, BasePK deletedBy) {
        deleteItemUnitOfMeasureTypes(getItemUnitOfMeasureTypesByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemUnitOfMeasureTypesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemUnitOfMeasureTypes(getItemUnitOfMeasureTypesByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Shipping Times
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemShippingTimeFactory itemShippingTimeFactory;

    @Inject
    ItemShippingTimeTransferCache itemShippingTimeTransferCache;

    public ItemShippingTime createItemShippingTime(Item item, CustomerType customerType, Long shippingStartTime, Long shippingEndTime,
            BasePK createdBy) {
        var itemShippingTime = itemShippingTimeFactory.create(item, customerType,
                shippingStartTime, shippingEndTime, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemShippingTime.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemShippingTime;
    }

    public long countItemShippingTimesByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemShippingTimes)
                .where(ItemShippingTimes.ITEM.eq(item.getPrimaryKey()),
                        ItemShippingTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemShippingTimesByCustomerType(final CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemShippingTimes)
                .where(ItemShippingTimes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        ItemShippingTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemShippingTime getItemShippingTime(Item item, CustomerType customerType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemShippingTimes.fields())
                .from(ItemShippingTimes)
                .where(ItemShippingTimes.ITEM.eq(item.getPrimaryKey()),
                        ItemShippingTimes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        ItemShippingTimes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemShippingTimeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemShippingTime getItemShippingTime(Item item, CustomerType customerType) {
        return getItemShippingTime(item, customerType, EntityPermission.READ_ONLY);
    }
    
    public ItemShippingTime getItemShippingTimeForUpdate(Item item, CustomerType customerType) {
        return getItemShippingTime(item, customerType, EntityPermission.READ_WRITE);
    }
    
    public ItemShippingTimeValue getItemShippingTimeValue(ItemShippingTime itemShippingTime) {
        return itemShippingTime == null? null: itemShippingTime.getItemShippingTimeValue().clone();
    }

    public ItemShippingTimeValue getItemShippingTimeValueForUpdate(Item item, CustomerType customerType) {
        return getItemShippingTimeForUpdate(item, customerType).getItemShippingTimeValue().clone();
    }
    
    private List<ItemShippingTime> getItemShippingTimesByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemShippingTimes.fields())
                    .from(ItemShippingTimes)
                    .join(CustomerTypes).onKey(ITEM_SHIPPING_TIMES_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_LAST_DETAIL_FK)
                    .where(ItemShippingTimes.ITEM.eq(item.getPrimaryKey()),
                            ItemShippingTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    ItemShippingTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemShippingTimes.fields())
                    .from(ItemShippingTimes)
                    .where(ItemShippingTimes.ITEM.eq(item.getPrimaryKey()),
                            ItemShippingTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemShippingTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemShippingTime> getItemShippingTimesByItem(Item item) {
        return getItemShippingTimesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemShippingTime> getItemShippingTimesByItemForUpdate(Item item) {
        return getItemShippingTimesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemShippingTime> getItemShippingTimesByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemShippingTimes.fields())
                    .from(ItemShippingTimes)
                    .join(Items).onKey(ITEM_SHIPPING_TIMES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ItemShippingTimes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            ItemShippingTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemShippingTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemShippingTimes.fields())
                    .from(ItemShippingTimes)
                    .where(ItemShippingTimes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            ItemShippingTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemShippingTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemShippingTime> getItemShippingTimesByCustomerType(CustomerType customerType) {
        return getItemShippingTimesByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemShippingTime> getItemShippingTimesByCustomerTypeForUpdate(CustomerType customerType) {
        return getItemShippingTimesByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    public void updateItemShippingTimeFromValue(ItemShippingTimeValue itemShippingTimeValue, BasePK updatedBy) {
        if(itemShippingTimeValue.hasBeenModified()) {
            var itemShippingTimePK = itemShippingTimeValue.getPrimaryKey();
            var itemShippingTime = itemShippingTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemShippingTimePK);
            
            itemShippingTime.setThruTime(session.getStartTime());
            itemShippingTime.store();

            var itemPK = itemShippingTime.getItemPK();
            var customerTypePK = itemShippingTime.getCustomerTypePK();
            var shippingStartTime = itemShippingTimeValue.getShippingStartTime();
            var shippingEndTime = itemShippingTimeValue.getShippingEndTime();
            
            itemShippingTime = itemShippingTimeFactory.create(itemPK, customerTypePK, shippingStartTime, shippingEndTime,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemShippingTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemShippingTimeTransfer getItemShippingTimeTransfer(UserVisit userVisit, ItemShippingTime itemShippingTime) {
        return itemShippingTime == null? null: itemShippingTimeTransferCache.getTransfer(userVisit, itemShippingTime);
    }
    
    public ItemShippingTimeTransfer getItemShippingTimeTransfer(UserVisit userVisit, Item item, CustomerType customerType) {
        return getItemShippingTimeTransfer(userVisit, getItemShippingTime(item, customerType));
    }
    
    public List<ItemShippingTimeTransfer> getItemShippingTimeTransfers(UserVisit userVisit, Collection<ItemShippingTime> entities) {
        List<ItemShippingTimeTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(itemShippingTimeTransferCache.getTransfer(userVisit, entity))
        );

        return transfers;
    }

    public List<ItemShippingTimeTransfer> getItemShippingTimeTransfersByItem(UserVisit userVisit, Item item) {
        return getItemShippingTimeTransfers(userVisit, getItemShippingTimesByItem(item));
    }
    
    public void deleteItemShippingTime(ItemShippingTime itemShippingTime, BasePK deletedBy) {
        itemShippingTime.setThruTime(session.getStartTime());
        
        sendEvent(itemShippingTime.getItemPK(), EventTypes.MODIFY, itemShippingTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemShippingTimesByItem(Item item, BasePK deletedBy) {
        var itemShippingTimes = getItemShippingTimesByItemForUpdate(item);
        
        itemShippingTimes.forEach((itemShippingTime) -> 
                deleteItemShippingTime(itemShippingTime, deletedBy)
        );
    }
    
    public void deleteItemShippingTimesByCustomerType(CustomerType customerType, BasePK deletedBy) {
        var itemShippingTimes = getItemShippingTimesByCustomerTypeForUpdate(customerType);
        
        itemShippingTimes.forEach((itemShippingTime) -> 
                deleteItemShippingTime(itemShippingTime, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemAliasChecksumTypeFactory itemAliasChecksumTypeFactory;

    @Inject
    ItemAliasChecksumTypeTransferCache itemAliasChecksumTypeTransferCache;

    public ItemAliasChecksumType createItemAliasChecksumType(String itemAliasChecksumTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemAliasChecksumType = itemAliasChecksumTypeFactory.create(itemAliasChecksumTypeName, isDefault, sortOrder);

        sendEvent(itemAliasChecksumType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemAliasChecksumType;
    }

    public long countItemAliasChecksumTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemAliasChecksumTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemAliasChecksumType */
    public ItemAliasChecksumType getItemAliasChecksumTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemAliasChecksumTypePK(entityInstance.getEntityUniqueId());

        return itemAliasChecksumTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemAliasChecksumTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemAliasChecksumTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByName(String itemAliasChecksumTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliasChecksumTypes.fields())
                .from(ItemAliasChecksumTypes)
                .where(ItemAliasChecksumTypes.ITEM_ALIAS_CHECKSUM_TYPE_NAME.eq(itemAliasChecksumTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasChecksumTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByName(String itemAliasChecksumTypeName) {
        return getItemAliasChecksumTypeByName(itemAliasChecksumTypeName, EntityPermission.READ_ONLY);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByNameForUpdate(String itemAliasChecksumTypeName) {
        return getItemAliasChecksumTypeByName(itemAliasChecksumTypeName, EntityPermission.READ_WRITE);
    }

    public ItemAliasChecksumType getDefaultItemAliasChecksumType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliasChecksumTypes.fields())
                .from(ItemAliasChecksumTypes)
                .where(ItemAliasChecksumTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasChecksumTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemAliasChecksumType getDefaultItemAliasChecksumType() {
        return getDefaultItemAliasChecksumType(EntityPermission.READ_ONLY);
    }

    public ItemAliasChecksumType getDefaultItemAliasChecksumTypeForUpdate() {
        return getDefaultItemAliasChecksumType(EntityPermission.READ_WRITE);
    }

    public ItemAliasChecksumTypeValue getDefaultItemAliasChecksumTypeValueForUpdate() {
        return getDefaultItemAliasChecksumTypeForUpdate().getItemAliasChecksumTypeValue().clone();
    }

    public List<ItemAliasChecksumType> getItemAliasChecksumTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemAliasChecksumTypes.fields())
                .from(ItemAliasChecksumTypes)
                .orderBy(ItemAliasChecksumTypes.SORT_ORDER, ItemAliasChecksumTypes.ITEM_ALIAS_CHECKSUM_TYPE_NAME),
                ItemAliasChecksumTypeFactory.class);

        return itemAliasChecksumTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public ItemAliasChecksumTypeChoicesBean getItemAliasChecksumTypeChoices(String defaultItemAliasChecksumTypeChoice, Language language, boolean allowNullChoice) {
        var itemAliasChecksumTypes = getItemAliasChecksumTypes();
        var size = itemAliasChecksumTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemAliasChecksumTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemAliasChecksumType : itemAliasChecksumTypes) {
            var label = getBestItemAliasChecksumTypeDescription(itemAliasChecksumType, language);
            var value = itemAliasChecksumType.getItemAliasChecksumTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultItemAliasChecksumTypeChoice != null && defaultItemAliasChecksumTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemAliasChecksumType.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemAliasChecksumTypeChoicesBean(labels, values, defaultValue);
    }

    public ItemAliasChecksumTypeTransfer getItemAliasChecksumTypeTransfer(UserVisit userVisit, ItemAliasChecksumType itemAliasChecksumType) {
        return itemAliasChecksumTypeTransferCache.getTransfer(userVisit, itemAliasChecksumType);
    }

    public List<ItemAliasChecksumTypeTransfer> getItemAliasChecksumTypeTransfers(UserVisit userVisit, Collection<ItemAliasChecksumType> entities) {
        var itemAliasChecksumTypeTransfers = new ArrayList<ItemAliasChecksumTypeTransfer>(entities.size());

        entities.forEach((entity) ->
                itemAliasChecksumTypeTransfers.add(itemAliasChecksumTypeTransferCache.getTransfer(userVisit, entity))
        );

        return itemAliasChecksumTypeTransfers;
    }

    public List<ItemAliasChecksumTypeTransfer> getItemAliasChecksumTypeTransfers(UserVisit userVisit) {
        return getItemAliasChecksumTypeTransfers(userVisit, getItemAliasChecksumTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemAliasChecksumTypeDescriptionFactory itemAliasChecksumTypeDescriptionFactory;

    public ItemAliasChecksumTypeDescription createItemAliasChecksumTypeDescription(ItemAliasChecksumType itemAliasChecksumType,
            Language language, String description, BasePK createdBy) {
        var itemAliasChecksumTypeDescription = itemAliasChecksumTypeDescriptionFactory.create(itemAliasChecksumType, language, description);

        sendEvent(itemAliasChecksumType.getPrimaryKey(), EventTypes.MODIFY, itemAliasChecksumTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemAliasChecksumTypeDescription;
    }

    public ItemAliasChecksumTypeDescription getItemAliasChecksumTypeDescription(ItemAliasChecksumType itemAliasChecksumType, Language language) {
        var query = session.getDslContext()
                .select(ItemAliasChecksumTypeDescriptions.fields())
                .from(ItemAliasChecksumTypeDescriptions)
                .where(ItemAliasChecksumTypeDescriptions.ITEM_ALIAS_CHECKSUM_TYPE.eq(itemAliasChecksumType.getPrimaryKey()),
                        ItemAliasChecksumTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return itemAliasChecksumTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public String getBestItemAliasChecksumTypeDescription(ItemAliasChecksumType itemAliasChecksumType, Language language) {
        String description;
        var itemAliasChecksumTypeDescription = getItemAliasChecksumTypeDescription(itemAliasChecksumType, language);

        if(itemAliasChecksumTypeDescription == null && !language.getIsDefault()) {
            itemAliasChecksumTypeDescription = getItemAliasChecksumTypeDescription(itemAliasChecksumType, partyControl.getDefaultLanguage());
        }

        if(itemAliasChecksumTypeDescription == null) {
            description = itemAliasChecksumType.getItemAliasChecksumTypeName();
        } else {
            description = itemAliasChecksumTypeDescription.getDescription();
        }

        return description;
    }

    // --------------------------------------------------------------------------------
    //   Item Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemAliasTypeDetailFactory itemAliasTypeDetailFactory;

    @Inject
    protected ItemAliasTypeFactory itemAliasTypeFactory;

    @Inject
    ItemAliasTypeTransferCache itemAliasTypeTransferCache;

    public ItemAliasType createItemAliasType(String itemAliasTypeName, String validationPattern, ItemAliasChecksumType itemAliasChecksumType, Boolean allowMultiple,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultItemAliasType = getDefaultItemAliasType();
        var defaultFound = defaultItemAliasType != null;
        
        if(defaultFound && isDefault) {
            var defaultItemAliasTypeDetailValue = getDefaultItemAliasTypeDetailValueForUpdate();
            
            defaultItemAliasTypeDetailValue.setIsDefault(false);
            updateItemAliasTypeFromValue(defaultItemAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemAliasType = itemAliasTypeFactory.create();
        var itemAliasTypeDetail = itemAliasTypeDetailFactory.create( itemAliasType, itemAliasTypeName, validationPattern,
                itemAliasChecksumType, allowMultiple, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        itemAliasType = itemAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemAliasType.getPrimaryKey());
        itemAliasType.setActiveDetail(itemAliasTypeDetail);
        itemAliasType.setLastDetail(itemAliasTypeDetail);
        itemAliasType.store();
        
        sendEvent(itemAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return itemAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemAliasType */
    public ItemAliasType getItemAliasTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new ItemAliasTypePK(entityInstance.getEntityUniqueId());

        return itemAliasTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemAliasType getItemAliasTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemAliasType getItemAliasTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemAliasType getItemAliasTypeByPK(ItemAliasTypePK pk) {
        return itemAliasTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countItemAliasTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemAliasTypes)
                .join(ItemAliasTypeDetails).onKey(ITEM_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ItemAliasType getItemAliasTypeByName(String itemAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliasTypes.fields())
                .from(ItemAliasTypes)
                .join(ItemAliasTypeDetails).onKey(ITEM_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemAliasTypeDetails.ITEM_ALIAS_TYPE_NAME.eq(itemAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemAliasType getItemAliasTypeByName(String itemAliasTypeName) {
        return getItemAliasTypeByName(itemAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public ItemAliasType getItemAliasTypeByNameForUpdate(String itemAliasTypeName) {
        return getItemAliasTypeByName(itemAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public ItemAliasTypeDetailValue getItemAliasTypeDetailValueForUpdate(ItemAliasType itemAliasType) {
        return itemAliasType == null? null: itemAliasType.getLastDetailForUpdate().getItemAliasTypeDetailValue().clone();
    }
    
    public ItemAliasTypeDetailValue getItemAliasTypeDetailValueByNameForUpdate(String itemAliasTypeName) {
        return getItemAliasTypeDetailValueForUpdate(getItemAliasTypeByNameForUpdate(itemAliasTypeName));
    }
    
    public ItemAliasType getDefaultItemAliasType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliasTypes.fields())
                .from(ItemAliasTypes)
                .join(ItemAliasTypeDetails).onKey(ITEM_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemAliasType getDefaultItemAliasType() {
        return getDefaultItemAliasType(EntityPermission.READ_ONLY);
    }
    
    public ItemAliasType getDefaultItemAliasTypeForUpdate() {
        return getDefaultItemAliasType(EntityPermission.READ_WRITE);
    }
    
    public ItemAliasTypeDetailValue getDefaultItemAliasTypeDetailValueForUpdate() {
        return getDefaultItemAliasTypeForUpdate().getLastDetailForUpdate().getItemAliasTypeDetailValue().clone();
    }
    
    private List<ItemAliasType> getItemAliasTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliasTypes.fields())
                .from(ItemAliasTypes)
                .join(ItemAliasTypeDetails).onKey(ITEM_ALIAS_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemAliasTypeDetails.SORT_ORDER, ItemAliasTypeDetails.ITEM_ALIAS_TYPE_NAME),
                    ItemAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemAliasType> getItemAliasTypes() {
        return getItemAliasTypes(EntityPermission.READ_ONLY);
    }
    
    public List<ItemAliasType> getItemAliasTypesForUpdate() {
        return getItemAliasTypes(EntityPermission.READ_WRITE);
    }
    
    public ItemAliasTypeTransfer getItemAliasTypeTransfer(UserVisit userVisit, ItemAliasType itemAliasType) {
        return itemAliasTypeTransferCache.getTransfer(userVisit, itemAliasType);
    }

    public List<ItemAliasTypeTransfer> getItemAliasTypeTransfers(UserVisit userVisit, Collection<ItemAliasType> itemAliasTypes) {
        List<ItemAliasTypeTransfer> itemAliasTypeTransfers = new ArrayList<>(itemAliasTypes.size());

        itemAliasTypes.forEach((itemAliasType) ->
                itemAliasTypeTransfers.add(itemAliasTypeTransferCache.getTransfer(userVisit, itemAliasType))
        );

        return itemAliasTypeTransfers;
    }

    public List<ItemAliasTypeTransfer> getItemAliasTypeTransfers(UserVisit userVisit) {
        return getItemAliasTypeTransfers(userVisit, getItemAliasTypes());
    }

    public ItemAliasTypeChoicesBean getItemAliasTypeChoices(String defaultItemAliasTypeChoice, Language language,
            boolean allowNullChoice) {
        var itemAliasTypes = getItemAliasTypes();
        var size = itemAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemAliasType : itemAliasTypes) {
            var itemAliasTypeDetail = itemAliasType.getLastDetail();
            
            var label = getBestItemAliasTypeDescription(itemAliasType, language);
            var value = itemAliasTypeDetail.getItemAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemAliasTypeChoice != null && defaultItemAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateItemAliasTypeFromValue(final ItemAliasTypeDetailValue itemAliasTypeDetailValue, final boolean checkDefault,
            final BasePK updatedBy) {
        if(itemAliasTypeDetailValue.hasBeenModified()) {
            final var itemAliasType = itemAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAliasTypeDetailValue.getItemAliasTypePK());
            var itemAliasTypeDetail = itemAliasType.getActiveDetailForUpdate();
            
            itemAliasTypeDetail.setThruTime(session.getStartTime());
            itemAliasTypeDetail.store();

            final var itemAliasTypePK = itemAliasTypeDetail.getItemAliasTypePK();
            final var itemAliasTypeName = itemAliasTypeDetailValue.getItemAliasTypeName();
            final var validationPattern = itemAliasTypeDetailValue.getValidationPattern();
            final var itemAliasChecksumTypePK = itemAliasTypeDetailValue.getItemAliasChecksumTypePK();
            final var allowMultiple = itemAliasTypeDetailValue.getAllowMultiple();
            var isDefault = itemAliasTypeDetailValue.getIsDefault();
            final var sortOrder = itemAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                final var defaultItemAliasType = getDefaultItemAliasType();
                final var defaultFound = defaultItemAliasType != null && !defaultItemAliasType.equals(itemAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    final var defaultItemAliasTypeDetailValue = getDefaultItemAliasTypeDetailValueForUpdate();
                    
                    defaultItemAliasTypeDetailValue.setIsDefault(false);
                    updateItemAliasTypeFromValue(defaultItemAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            itemAliasTypeDetail = itemAliasTypeDetailFactory.create(itemAliasTypePK, itemAliasTypeName,
                    validationPattern, itemAliasChecksumTypePK, allowMultiple, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            itemAliasType.setActiveDetail(itemAliasTypeDetail);
            itemAliasType.setLastDetail(itemAliasTypeDetail);
            
            sendEvent(itemAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateItemAliasTypeFromValue(final ItemAliasTypeDetailValue itemAliasTypeDetailValue, final BasePK updatedBy) {
        updateItemAliasTypeFromValue(itemAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteItemAliasType(ItemAliasType itemAliasType, BasePK deletedBy) {

        var vendors = vendorControl.getVendorsByDefaultItemAliasTypeForUpdate(itemAliasType);
        vendors.stream().map((vendor) -> vendorControl.getVendorValue(vendor)).map((vendorValue) -> {
            vendorValue.setDefaultItemAliasTypePK(null);
            return vendorValue;            
        }).forEach((vendorValue) -> {
            vendorControl.updateVendorFromValue(vendorValue, deletedBy);
        });
        
        deleteItemAliasTypeDescriptionsByItemAliasType(itemAliasType, deletedBy);
        deleteItemAliasesByItemAliasType(itemAliasType, deletedBy);

        var itemAliasTypeDetail = itemAliasType.getLastDetailForUpdate();
        itemAliasTypeDetail.setThruTime(session.getStartTime());
        itemAliasType.setActiveDetail(null);
        itemAliasType.store();

        // Check for default, and pick one if necessary
        var defaultItemAliasType = getDefaultItemAliasType();
        if(defaultItemAliasType == null) {
            var itemAliasTypes = getItemAliasTypesForUpdate();
            
            if(!itemAliasTypes.isEmpty()) {
                var iter = itemAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemAliasType = (ItemAliasType)iter.next();
                }
                var itemAliasTypeDetailValue = Objects.requireNonNull(defaultItemAliasType).getLastDetailForUpdate().getItemAliasTypeDetailValue().clone();
                
                itemAliasTypeDetailValue.setIsDefault(true);
                updateItemAliasTypeFromValue(itemAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(itemAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemAliasTypeDescriptionFactory itemAliasTypeDescriptionFactory;

    @Inject
    ItemAliasTypeDescriptionTransferCache itemAliasTypeDescriptionTransferCache;

    public ItemAliasTypeDescription createItemAliasTypeDescription(ItemAliasType itemAliasType, Language language,
            String description, BasePK createdBy) {
        var itemAliasTypeDescription = itemAliasTypeDescriptionFactory.create(
                itemAliasType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(itemAliasType.getPrimaryKey(), EventTypes.MODIFY, itemAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemAliasTypeDescription;
    }
    
    private ItemAliasTypeDescription getItemAliasTypeDescription(ItemAliasType itemAliasType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliasTypeDescriptions.fields())
                .from(ItemAliasTypeDescriptions)
                .where(ItemAliasTypeDescriptions.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                        ItemAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemAliasTypeDescription getItemAliasTypeDescription(ItemAliasType itemAliasType, Language language) {
        return getItemAliasTypeDescription(itemAliasType, language, EntityPermission.READ_ONLY);
    }
    
    public ItemAliasTypeDescription getItemAliasTypeDescriptionForUpdate(ItemAliasType itemAliasType, Language language) {
        return getItemAliasTypeDescription(itemAliasType, language, EntityPermission.READ_WRITE);
    }
    
    public ItemAliasTypeDescriptionValue getItemAliasTypeDescriptionValue(ItemAliasTypeDescription itemAliasTypeDescription) {
        return itemAliasTypeDescription == null? null: itemAliasTypeDescription.getItemAliasTypeDescriptionValue().clone();
    }
    
    public ItemAliasTypeDescriptionValue getItemAliasTypeDescriptionValueForUpdate(ItemAliasType itemAliasType, Language language) {
        return getItemAliasTypeDescriptionValue(getItemAliasTypeDescriptionForUpdate(itemAliasType, language));
    }
    
    private List<ItemAliasTypeDescription> getItemAliasTypeDescriptionsByItemAliasType(ItemAliasType itemAliasType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemAliasTypeDescriptions.fields())
                    .from(ItemAliasTypeDescriptions)
                    .join(Languages).onKey(ITEM_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemAliasTypeDescriptions.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                            ItemAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemAliasTypeDescriptions.fields())
                    .from(ItemAliasTypeDescriptions)
                    .where(ItemAliasTypeDescriptions.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                            ItemAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemAliasTypeDescription> getItemAliasTypeDescriptionsByItemAliasType(ItemAliasType itemAliasType) {
        return getItemAliasTypeDescriptionsByItemAliasType(itemAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAliasTypeDescription> getItemAliasTypeDescriptionsByItemAliasTypeForUpdate(ItemAliasType itemAliasType) {
        return getItemAliasTypeDescriptionsByItemAliasType(itemAliasType, EntityPermission.READ_WRITE);
    }
    
    public String getBestItemAliasTypeDescription(ItemAliasType itemAliasType, Language language) {
        String description;
        var itemAliasTypeDescription = getItemAliasTypeDescription(itemAliasType, language);
        
        if(itemAliasTypeDescription == null && !language.getIsDefault()) {
            itemAliasTypeDescription = getItemAliasTypeDescription(itemAliasType, partyControl.getDefaultLanguage());
        }
        
        if(itemAliasTypeDescription == null) {
            description = itemAliasType.getLastDetail().getItemAliasTypeName();
        } else {
            description = itemAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public ItemAliasTypeDescriptionTransfer getItemAliasTypeDescriptionTransfer(UserVisit userVisit, ItemAliasTypeDescription itemAliasTypeDescription) {
        return itemAliasTypeDescriptionTransferCache.getTransfer(userVisit, itemAliasTypeDescription);
    }
    
    public List<ItemAliasTypeDescriptionTransfer> getItemAliasTypeDescriptionTransfersByItemAliasType(UserVisit userVisit, ItemAliasType itemAliasType) {
        var itemAliasTypeDescriptions = getItemAliasTypeDescriptionsByItemAliasType(itemAliasType);
        List<ItemAliasTypeDescriptionTransfer> itemAliasTypeDescriptionTransfers = new ArrayList<>(itemAliasTypeDescriptions.size());
        
        itemAliasTypeDescriptions.forEach((itemAliasTypeDescription) ->
                itemAliasTypeDescriptionTransfers.add(itemAliasTypeDescriptionTransferCache.getTransfer(userVisit, itemAliasTypeDescription))
        );
        
        return itemAliasTypeDescriptionTransfers;
    }
    
    public void updateItemAliasTypeDescriptionFromValue(ItemAliasTypeDescriptionValue itemAliasTypeDescriptionValue, BasePK updatedBy) {
        if(itemAliasTypeDescriptionValue.hasBeenModified()) {
            var itemAliasTypeDescription = itemAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAliasTypeDescriptionValue.getPrimaryKey());
            
            itemAliasTypeDescription.setThruTime(session.getStartTime());
            itemAliasTypeDescription.store();

            var itemAliasType = itemAliasTypeDescription.getItemAliasType();
            var language = itemAliasTypeDescription.getLanguage();
            var description = itemAliasTypeDescriptionValue.getDescription();
            
            itemAliasTypeDescription = itemAliasTypeDescriptionFactory.create(itemAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemAliasType.getPrimaryKey(), EventTypes.MODIFY, itemAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemAliasTypeDescription(ItemAliasTypeDescription itemAliasTypeDescription, BasePK deletedBy) {
        itemAliasTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(itemAliasTypeDescription.getItemAliasTypePK(), EventTypes.MODIFY, itemAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteItemAliasTypeDescriptionsByItemAliasType(ItemAliasType itemAliasType, BasePK deletedBy) {
        var itemAliasTypeDescriptions = getItemAliasTypeDescriptionsByItemAliasTypeForUpdate(itemAliasType);
        
        itemAliasTypeDescriptions.forEach((itemAliasTypeDescription) -> 
                deleteItemAliasTypeDescription(itemAliasTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemAliasFactory itemAliasFactory;

    @Inject
    ItemAliasTransferCache itemAliasTransferCache;

    public ItemAlias createItemAlias(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType, String alias,
            BasePK createdBy) {
        var itemAlias = itemAliasFactory.create(item, unitOfMeasureType, itemAliasType, alias,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemAlias;
    }

    public long countItemAliasesByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemAliases)
                .where(ItemAliases.ITEM.eq(item.getPrimaryKey()),
                        ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemAliasesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemAliases)
                .where(ItemAliases.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemAliasesByItemAliasType(ItemAliasType itemAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemAliases)
                .where(ItemAliases.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                        ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemAliases(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemAliases)
                .where(ItemAliases.ITEM.eq(item.getPrimaryKey()),
                        ItemAliases.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemAliases.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                        ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    private ItemAlias getItemAliasByAlias(String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliases.fields())
                .from(ItemAliases)
                .where(ItemAliases.ALIAS.eq(alias),
                        ItemAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemAlias getItemAliasByAlias(String alias) {
        return getItemAliasByAlias(alias, EntityPermission.READ_ONLY);
    }
    
    public ItemAlias getItemAliasByAliasForUpdate(String alias) {
        return getItemAliasByAlias(alias, EntityPermission.READ_WRITE);
    }
    
    public ItemAliasValue getItemAliasValue(ItemAlias itemAlias) {
        return itemAlias == null? null: itemAlias.getItemAliasValue().clone();
    }
    
    public ItemAliasValue getItemAliasValueByAliasForUpdate(String alias) {
        return getItemAliasValue(getItemAliasByAliasForUpdate(alias));
    }
    
    private List<ItemAlias> getItemAliases(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemAliases.fields())
                .from(ItemAliases)
                .where(ItemAliases.ITEM.eq(item.getPrimaryKey()),
                        ItemAliases.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemAliases.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                        ItemAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemAliases.ALIAS),
                    ItemAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemAlias> getItemAliases(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType) {
        return getItemAliases(item, unitOfMeasureType, itemAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesForUpdate(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType) {
        return getItemAliases(item, unitOfMeasureType, itemAliasType, EntityPermission.READ_WRITE);
    }
    
    private List<ItemAlias> getItemAliasesByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .join(ItemAliasTypes).onKey(ITEM_ALIASES_ITEM_ALIAS_TYPE_FK)
                    .join(ItemAliasTypeDetails).onKey(ITEM_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(ItemAliases.ITEM.eq(item.getPrimaryKey()),
                            ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemAliasTypeDetails.SORT_ORDER, ItemAliasTypeDetails.ITEM_ALIAS_TYPE_NAME),
                    ItemAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .where(ItemAliases.ITEM.eq(item.getPrimaryKey()),
                            ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemAlias> getItemAliasesByItem(Item item) {
        return getItemAliasesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesByItemForUpdate(Item item) {
        return getItemAliasesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemAlias> getItemAliasesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .join(Items).onKey(ITEM_ALIASES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ItemAliases.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .where(ItemAliases.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemAlias> getItemAliasesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemAliasesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemAliasesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private List<ItemAlias> getItemAliasesByItemAliasType(ItemAliasType itemAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .join(Items).onKey(ITEM_ALIASES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ItemAliases.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                            ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .where(ItemAliases.ITEM_ALIAS_TYPE.eq(itemAliasType.getPrimaryKey()),
                            ItemAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemAlias> getItemAliasesByItemAliasType(ItemAliasType itemAliasType) {
        return getItemAliasesByItemAliasType(itemAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesByItemAliasTypeForUpdate(ItemAliasType itemAliasType) {
        return getItemAliasesByItemAliasType(itemAliasType, EntityPermission.READ_WRITE);
    }
    
    private List<ItemAlias> getItemAliasesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType,
            EntityPermission entityPermission) {
        var condition = ItemAliases.ITEM.eq(item.getPrimaryKey())
                .and(ItemAliases.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()))
                .and(ItemAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .join(ItemAliasTypes).onKey(ITEM_ALIASES_ITEM_ALIAS_TYPE_FK)
                    .join(ItemAliasTypeDetails).onKey(ITEM_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(condition)
                    .orderBy(ItemAliasTypeDetails.SORT_ORDER, ItemAliasTypeDetails.ITEM_ALIAS_TYPE_NAME, ItemAliases.ALIAS),
                    ItemAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemAliases.fields())
                    .from(ItemAliases)
                    .where(condition)
                    .forUpdate();
        };

        return itemAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemAlias> getItemAliasesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemAliasesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<ItemAlias> getItemAliasesByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemAliasesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public void updateItemAliasFromValue(ItemAliasValue itemAliasValue, BasePK updatedBy) {
        if(itemAliasValue.hasBeenModified()) {
            var itemAliasPK = itemAliasValue.getPrimaryKey();
            var itemAlias = itemAliasFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemAliasPK);
            
            itemAlias.setThruTime(session.getStartTime());
            itemAlias.store();

            var itemPK = itemAlias.getItemPK();
            var unitOfMeasureTypePK = itemAliasValue.getUnitOfMeasureTypePK();
            var itemAliasTypePK = itemAliasValue.getItemAliasTypePK();
            var alias = itemAliasValue.getAlias();
            
            itemAlias = itemAliasFactory.create(itemPK, unitOfMeasureTypePK, itemAliasTypePK, alias,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemAliasTransfer getItemAliasTransfer(UserVisit userVisit, ItemAlias itemAlias) {
        return itemAliasTransferCache.getTransfer(userVisit, itemAlias);
    }

    public List<ItemAliasTransfer> getItemAliasTransfers(UserVisit userVisit, Collection<ItemAlias> itemAliases) {
        List<ItemAliasTransfer> itemAliasTransfers = new ArrayList<>(itemAliases.size());

        itemAliases.forEach((itemAlias) -> {
            itemAliasTransfers.add(itemAliasTransferCache.getTransfer(userVisit, itemAlias));
        });

        return itemAliasTransfers;
    }

    public List<ItemAliasTransfer> getItemAliasTransfersByItem(UserVisit userVisit, Item item) {
        return getItemAliasTransfers(userVisit, getItemAliasesByItem(item));
    }

    public void deleteItemAlias(ItemAlias itemAlias, BasePK deletedBy) {
        itemAlias.setThruTime(session.getStartTime());
        
        sendEvent(itemAlias.getItem().getPrimaryKey(), EventTypes.MODIFY, itemAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemAliases(List<ItemAlias> itemAliases, BasePK deletedBy) {
        itemAliases.forEach((itemAlias) -> 
                deleteItemAlias(itemAlias, deletedBy)
        );
    }
    
    public void deleteItemAliasesByItem(Item item, BasePK deletedBy) {
        deleteItemAliases(getItemAliasesByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemAliasesByItemAliasType(ItemAliasType itemAliasType, BasePK deletedBy) {
        deleteItemAliases(getItemAliasesByItemAliasTypeForUpdate(itemAliasType), deletedBy);
    }
    
    public void deleteItemAliasesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemAliases(getItemAliasesByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemAliasesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemAliases(getItemAliasesByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Kit Options
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemKitOptionFactory itemKitOptionFactory;

    public ItemKitOption createItemKitOption(Item item, Boolean allowPartialShipments, BasePK createdBy) {
        var itemKitOption = itemKitOptionFactory.create(item, allowPartialShipments,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemKitOption.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemKitOption;
    }
    
    private ItemKitOption getItemKitOption(Item item, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemKitOptions.fields())
                .from(ItemKitOptions)
                .where(ItemKitOptions.ITEM.eq(item.getPrimaryKey()),
                        ItemKitOptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemKitOptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemKitOption getItemKitOption(Item item) {
        return getItemKitOption(item, EntityPermission.READ_ONLY);
    }
    
    public ItemKitOption getItemKitOptionForUpdate(Item item) {
        return getItemKitOption(item, EntityPermission.READ_WRITE);
    }
    
    public ItemKitOptionValue getItemKitOptionValueForUpdate(Item item) {
        return getItemKitOptionForUpdate(item).getItemKitOptionValue().clone();
    }
    
    public void updateItemKitOptionFromValue(ItemKitOptionValue itemKitOptionValue, BasePK updatedBy) {
        if(itemKitOptionValue.hasBeenModified()) {
            var itemKitOptionPK = itemKitOptionValue.getPrimaryKey();
            var itemKitOption = itemKitOptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemKitOptionPK);
            
            itemKitOption.setThruTime(session.getStartTime());
            itemKitOption.store();

            var itemPK = itemKitOption.getItemPK();
            var allowPartialShipments = itemKitOptionValue.getAllowPartialShipments();
            
            itemKitOption = itemKitOptionFactory.create(itemPK, allowPartialShipments,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemKitOption.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemKitOption(ItemKitOption itemKitOption, BasePK deletedBy) {
        itemKitOption.setThruTime(session.getStartTime());
        
        sendEvent(itemKitOption.getItemPK(), EventTypes.MODIFY, itemKitOption.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemCountryOfOriginFactory itemCountryOfOriginFactory;

    @Inject
    ItemCountryOfOriginTransferCache itemCountryOfOriginTransferCache;

    public ItemCountryOfOrigin createItemCountryOfOrigin(Item item, GeoCode countryGeoCode, Integer percent, BasePK createdBy) {
        var itemCountryOfOrigin = itemCountryOfOriginFactory.create(item, countryGeoCode, percent,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemCountryOfOrigin.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemCountryOfOrigin;
    }

    public long countItemCountryOfOriginsByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemCountryOfOrigins)
                .where(ItemCountryOfOrigins.ITEM.eq(item.getPrimaryKey()),
                        ItemCountryOfOrigins.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemCountryOfOriginsByCountryGeoCode(final GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ItemCountryOfOrigins)
                .where(ItemCountryOfOrigins.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ItemCountryOfOrigins.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemCountryOfOrigin getItemCountryOfOrigin(Item item, GeoCode countryGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemCountryOfOrigins.fields())
                .from(ItemCountryOfOrigins)
                .where(ItemCountryOfOrigins.ITEM.eq(item.getPrimaryKey()),
                        ItemCountryOfOrigins.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ItemCountryOfOrigins.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemCountryOfOriginFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemCountryOfOrigin getItemCountryOfOrigin(Item item, GeoCode countryGeoCode) {
        return getItemCountryOfOrigin(item, countryGeoCode, EntityPermission.READ_ONLY);
    }
    
    public ItemCountryOfOrigin getItemCountryOfOriginForUpdate(Item item, GeoCode countryGeoCode) {
        return getItemCountryOfOrigin(item, countryGeoCode, EntityPermission.READ_WRITE);
    }
    
    public ItemCountryOfOriginValue getItemCountryOfOriginValue(ItemCountryOfOrigin itemCountryOfOrigin) {
        return itemCountryOfOrigin == null? null: itemCountryOfOrigin.getItemCountryOfOriginValue().clone();
    }

    public ItemCountryOfOriginValue getItemCountryOfOriginValueForUpdate(Item item, GeoCode countryGeoCode) {
        return getItemCountryOfOriginForUpdate(item, countryGeoCode).getItemCountryOfOriginValue().clone();
    }
    
    private List<ItemCountryOfOrigin> getItemCountryOfOriginsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemCountryOfOrigins.fields())
                    .from(ItemCountryOfOrigins)
                    .join(GeoCodes).onKey(ITEM_COUNTRY_OF_ORIGINS_COUNTRY_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(ItemCountryOfOrigins.ITEM.eq(item.getPrimaryKey()),
                            ItemCountryOfOrigins.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    ItemCountryOfOriginFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemCountryOfOrigins.fields())
                    .from(ItemCountryOfOrigins)
                    .where(ItemCountryOfOrigins.ITEM.eq(item.getPrimaryKey()),
                            ItemCountryOfOrigins.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemCountryOfOriginFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemCountryOfOrigin> getItemCountryOfOriginsByItem(Item item) {
        return getItemCountryOfOriginsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemCountryOfOrigin> getItemCountryOfOriginsByItemForUpdate(Item item) {
        return getItemCountryOfOriginsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemCountryOfOrigin> getItemCountryOfOriginsByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemCountryOfOrigins.fields())
                    .from(ItemCountryOfOrigins)
                    .join(Items).onKey(ITEM_COUNTRY_OF_ORIGINS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ItemCountryOfOrigins.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                            ItemCountryOfOrigins.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemCountryOfOriginFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemCountryOfOrigins.fields())
                    .from(ItemCountryOfOrigins)
                    .where(ItemCountryOfOrigins.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                            ItemCountryOfOrigins.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemCountryOfOriginFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemCountryOfOrigin> getItemCountryOfOriginsByCountryGeoCode(GeoCode countryGeoCode) {
        return getItemCountryOfOriginsByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }
    
    public List<ItemCountryOfOrigin> getItemCountryOfOriginsByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getItemCountryOfOriginsByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }
    
    public void updateItemCountryOfOriginFromValue(ItemCountryOfOriginValue itemCountryOfOriginValue, BasePK updatedBy) {
        if(itemCountryOfOriginValue.hasBeenModified()) {
            var itemCountryOfOriginPK = itemCountryOfOriginValue.getPrimaryKey();
            var itemCountryOfOrigin = itemCountryOfOriginFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemCountryOfOriginPK);
            
            itemCountryOfOrigin.setThruTime(session.getStartTime());
            itemCountryOfOrigin.store();

            var itemPK = itemCountryOfOrigin.getItemPK();
            var countryGeoCodePK = itemCountryOfOrigin.getCountryGeoCodePK();
            var percent = itemCountryOfOriginValue.getPercent();
            
            itemCountryOfOrigin = itemCountryOfOriginFactory.create(itemPK, countryGeoCodePK, percent, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemCountryOfOrigin.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemCountryOfOriginTransfer getItemCountryOfOriginTransfer(UserVisit userVisit, ItemCountryOfOrigin itemCountryOfOrigin) {
        return itemCountryOfOrigin == null? null: itemCountryOfOriginTransferCache.getTransfer(userVisit, itemCountryOfOrigin);
    }

    public ItemCountryOfOriginTransfer getItemCountryOfOriginTransfer(UserVisit userVisit, Item item, GeoCode countryGeoCode) {
        return getItemCountryOfOriginTransfer(userVisit, getItemCountryOfOrigin(item, countryGeoCode));
    }

    public List<ItemCountryOfOriginTransfer> getItemCountryOfOriginTransfersByItem(UserVisit userVisit, Item item) {
        var itemCountryOfOrigins = getItemCountryOfOriginsByItem(item);
        List<ItemCountryOfOriginTransfer> itemCountryOfOriginTransfers = new ArrayList<>(itemCountryOfOrigins.size());
        
        itemCountryOfOrigins.forEach((itemCountryOfOrigin) ->
                itemCountryOfOriginTransfers.add(itemCountryOfOriginTransferCache.getTransfer(userVisit, itemCountryOfOrigin))
        );
        
        return itemCountryOfOriginTransfers;
    }
    
    public void deleteItemCountryOfOrigin(ItemCountryOfOrigin itemCountryOfOrigin, BasePK deletedBy) {
        itemCountryOfOrigin.setThruTime(session.getStartTime());
        
        sendEvent(itemCountryOfOrigin.getItemPK(), EventTypes.MODIFY, itemCountryOfOrigin.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemCountryOfOrigins(List<ItemCountryOfOrigin> itemCountryOfOrigins, BasePK deletedBy) {
        itemCountryOfOrigins.forEach((itemCountryOfOrigin) -> 
                deleteItemCountryOfOrigin(itemCountryOfOrigin, deletedBy)
        );
    }
    
    public void deleteItemCountryOfOriginsByItem(Item item, BasePK deletedBy) {
        deleteItemCountryOfOrigins(getItemCountryOfOriginsByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemCountryOfOriginsByCountryGeoCode(GeoCode countryGeoCode, BasePK deletedBy) {
        deleteItemCountryOfOrigins(getItemCountryOfOriginsByCountryGeoCodeForUpdate(countryGeoCode), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Kit Members
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemKitMemberFactory itemKitMemberFactory;

    @Inject
    ItemKitMemberTransferCache itemKitMemberTransferCache;

    public ItemKitMember createItemKitMember(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Item memberItem, InventoryCondition memberInventoryCondition, UnitOfMeasureType memberUnitOfMeasureType,
            Long quantity, BasePK createdBy) {
        var itemKitMember = itemKitMemberFactory.create(item, inventoryCondition, unitOfMeasureType,
                memberItem, memberInventoryCondition, memberUnitOfMeasureType, quantity, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemKitMember.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemKitMember;
    }

    public long countItemKitMembersByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemKitMembers)
                .where(ItemKitMembers.ITEM.eq(item.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemKitMembersByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(ItemKitMembers)
                .where(ItemKitMembers.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemKitMembersByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemKitMembers)
                .where(ItemKitMembers.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemKitMembersByMemberItem(Item memberItem) {
        return session.getDslContext()
                .selectCount()
                .from(ItemKitMembers)
                .where(ItemKitMembers.MEMBER_ITEM.eq(memberItem.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemKitMembersByMemberInventoryCondition(InventoryCondition memberInventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(ItemKitMembers)
                .where(ItemKitMembers.MEMBER_INVENTORY_CONDITION.eq(memberInventoryCondition.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemKitMembersByMemberUnitOfMeasureType(UnitOfMeasureType memberUnitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemKitMembers)
                .where(ItemKitMembers.MEMBER_UNIT_OF_MEASURE_TYPE.eq(memberUnitOfMeasureType.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemKitMember getItemKitMember(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Item memberItem, InventoryCondition memberInventoryCondition, UnitOfMeasureType memberUnitOfMeasureType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.getDslContext()
                    .select(ItemKitMembers.fields())
                    .from(ItemKitMembers)
                    .where(ItemKitMembers.ITEM.eq(item.getPrimaryKey()),
                            ItemKitMembers.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemKitMembers.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemKitMembers.MEMBER_ITEM.eq(memberItem.getPrimaryKey()),
                            ItemKitMembers.MEMBER_INVENTORY_CONDITION.eq(memberInventoryCondition.getPrimaryKey()),
                            ItemKitMembers.MEMBER_UNIT_OF_MEASURE_TYPE.eq(memberUnitOfMeasureType.getPrimaryKey()),
                            ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));
            case READ_WRITE -> session.getDslContext()
                    .select(ItemKitMembers.fields())
                    .from(ItemKitMembers)
                    .where(ItemKitMembers.ITEM.eq(item.getPrimaryKey()),
                            ItemKitMembers.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemKitMembers.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemKitMembers.MEMBER_ITEM.eq(memberItem.getPrimaryKey()),
                            ItemKitMembers.MEMBER_INVENTORY_CONDITION.eq(memberInventoryCondition.getPrimaryKey()),
                            ItemKitMembers.MEMBER_UNIT_OF_MEASURE_TYPE.eq(memberUnitOfMeasureType.getPrimaryKey()))
                    .forUpdate();
        };

        return itemKitMemberFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemKitMember getItemKitMember(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Item memberItem, InventoryCondition memberInventoryCondition, UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMember(item, inventoryCondition, unitOfMeasureType, memberItem, memberInventoryCondition,
                memberUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public ItemKitMember getItemKitMemberForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Item memberItem, InventoryCondition memberInventoryCondition,
            UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMember(item, inventoryCondition, unitOfMeasureType, memberItem, memberInventoryCondition,
                memberUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public ItemKitMemberValue getItemKitMemberValueForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Item memberItem, InventoryCondition memberInventoryCondition,
            UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMemberForUpdate(item, inventoryCondition, unitOfMeasureType, memberItem, memberInventoryCondition,
                memberUnitOfMeasureType).getItemKitMemberValue().clone();
    }
    
    private List<ItemKitMember> getItemKitMembers(Condition condition, EntityPermission entityPermission) {
        var kitItems = Items.as("kitItems");
        var kitItemDetails = ItemDetails.as("kitItemDetails");
        var kitInventoryConditions = InventoryConditions.as("kitInventoryConditions");
        var kitInventoryConditionDetails = InventoryConditionDetails.as("kitInventoryConditionDetails");
        var kitUnitOfMeasureTypes = UnitOfMeasureTypes.as("kitUnitOfMeasureTypes");
        var kitUnitOfMeasureTypeDetails = UnitOfMeasureTypeDetails.as("kitUnitOfMeasureTypeDetails");
        var memberItems = Items.as("memberItems");
        var memberItemDetails = ItemDetails.as("memberItemDetails");
        var memberInventoryConditions = InventoryConditions.as("memberInventoryConditions");
        var memberInventoryConditionDetails = InventoryConditionDetails.as("memberInventoryConditionDetails");
        var memberUnitOfMeasureTypes = UnitOfMeasureTypes.as("memberUnitOfMeasureTypes");
        var memberUnitOfMeasureTypeDetails = UnitOfMeasureTypeDetails.as("memberUnitOfMeasureTypeDetails");

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemKitMembers.fields())
                    .from(ItemKitMembers)
                    .join(kitItems).on(ItemKitMembers.ITEM.eq(kitItems.ITEM))
                    .join(kitItemDetails).on(kitItems.LAST_DETAIL.eq(kitItemDetails.ITEM_DETAIL))
                    .join(kitInventoryConditions).on(ItemKitMembers.INVENTORY_CONDITION.eq(kitInventoryConditions.INVENTORY_CONDITION))
                    .join(kitInventoryConditionDetails).on(kitInventoryConditions.LAST_DETAIL.eq(kitInventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .join(kitUnitOfMeasureTypes).on(ItemKitMembers.UNIT_OF_MEASURE_TYPE.eq(kitUnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE))
                    .join(kitUnitOfMeasureTypeDetails).on(kitUnitOfMeasureTypes.LAST_DETAIL.eq(kitUnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_DETAIL))
                    .join(memberItems).on(ItemKitMembers.MEMBER_ITEM.eq(memberItems.ITEM))
                    .join(memberItemDetails).on(memberItems.LAST_DETAIL.eq(memberItemDetails.ITEM_DETAIL))
                    .join(memberInventoryConditions).on(ItemKitMembers.MEMBER_INVENTORY_CONDITION.eq(memberInventoryConditions.INVENTORY_CONDITION))
                    .join(memberInventoryConditionDetails).on(memberInventoryConditions.LAST_DETAIL.eq(memberInventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .join(memberUnitOfMeasureTypes).on(ItemKitMembers.MEMBER_UNIT_OF_MEASURE_TYPE.eq(memberUnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE))
                    .join(memberUnitOfMeasureTypeDetails).on(memberUnitOfMeasureTypes.LAST_DETAIL.eq(memberUnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_DETAIL))
                    .where(condition)
                    .orderBy(kitItemDetails.ITEM_NAME, kitInventoryConditionDetails.SORT_ORDER, kitInventoryConditionDetails.INVENTORY_CONDITION_NAME, kitUnitOfMeasureTypeDetails.SORT_ORDER, kitUnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, memberItemDetails.ITEM_NAME, memberInventoryConditionDetails.SORT_ORDER, memberInventoryConditionDetails.INVENTORY_CONDITION_NAME, memberUnitOfMeasureTypeDetails.SORT_ORDER, memberUnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemKitMemberFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemKitMembers.fields())
                    .from(ItemKitMembers)
                    .where(condition)
                    .forUpdate();
        };

        return itemKitMemberFactory.getEntitiesFromQuery(entityPermission, query);
    }

    private List<ItemKitMember> getItemKitMembersFromItem(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var condition = ItemKitMembers.ITEM.eq(item.getPrimaryKey())
                .and(ItemKitMembers.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()))
                .and(ItemKitMembers.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()))
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersFromItem(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getItemKitMembersFromItem(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersFromItemForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getItemKitMembersFromItem(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersFromMemberItem(Item memberItem, InventoryCondition memberInventoryCondition,
            UnitOfMeasureType memberUnitOfMeasureType, EntityPermission entityPermission) {
        var condition = ItemKitMembers.MEMBER_ITEM.eq(memberItem.getPrimaryKey())
                .and(ItemKitMembers.MEMBER_INVENTORY_CONDITION.eq(memberInventoryCondition.getPrimaryKey()))
                .and(ItemKitMembers.MEMBER_UNIT_OF_MEASURE_TYPE.eq(memberUnitOfMeasureType.getPrimaryKey()))
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersFromMemberItem(Item memberItem, InventoryCondition memberInventoryCondition,
            UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMembersFromMemberItem(memberItem, memberInventoryCondition, memberUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersFromMemberItemForUpdate(Item memberItem, InventoryCondition memberInventoryCondition,
            UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMembersFromMemberItem(memberItem, memberInventoryCondition, memberUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByItem(Item item, EntityPermission entityPermission) {
        var condition = ItemKitMembers.ITEM.eq(item.getPrimaryKey())
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersByItem(Item item) {
        return getItemKitMembersByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByItemForUpdate(Item item) {
        return getItemKitMembersByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByMemberItem(Item memberItem, EntityPermission entityPermission) {
        var condition = ItemKitMembers.MEMBER_ITEM.eq(memberItem.getPrimaryKey())
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberItem(Item memberItem) {
        return getItemKitMembersByMemberItem(memberItem, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberItemForUpdate(Item memberItem) {
        return getItemKitMembersByMemberItem(memberItem, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var condition = ItemKitMembers.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey())
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemKitMembersByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemKitMembersByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByMemberInventoryCondition(InventoryCondition memberInventoryCondition, EntityPermission entityPermission) {
        var condition = ItemKitMembers.MEMBER_INVENTORY_CONDITION.eq(memberInventoryCondition.getPrimaryKey())
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberInventoryCondition(InventoryCondition memberInventoryCondition) {
        return getItemKitMembersByMemberInventoryCondition(memberInventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberInventoryConditionForUpdate(InventoryCondition memberInventoryCondition) {
        return getItemKitMembersByMemberInventoryCondition(memberInventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var condition = ItemKitMembers.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey())
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemKitMembersByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemKitMembersByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemKitMember> getItemKitMembersByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(ItemKitMembers.fields())
                .from(ItemKitMembers)
                .where(ItemKitMembers.ITEM.eq(item.getPrimaryKey()),
                        ItemKitMembers.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return itemKitMemberFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    private List<ItemKitMember> getItemKitMembersByMemberUnitOfMeasureType(UnitOfMeasureType memberUnitOfMeasureType, EntityPermission entityPermission) {
        var condition = ItemKitMembers.MEMBER_UNIT_OF_MEASURE_TYPE.eq(memberUnitOfMeasureType.getPrimaryKey())
                .and(ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME));

        return getItemKitMembers(condition, entityPermission);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberUnitOfMeasureType(UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMembersByMemberUnitOfMeasureType(memberUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberUnitOfMeasureTypeForUpdate(UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMembersByMemberUnitOfMeasureType(memberUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberItemAndUnitOfMeasureTypeForUpdate(Item memberItem, UnitOfMeasureType memberUnitOfMeasureType) {
        var query = session.getDslContext()
                .select(ItemKitMembers.fields())
                .from(ItemKitMembers)
                .where(ItemKitMembers.MEMBER_ITEM.eq(memberItem.getPrimaryKey()),
                        ItemKitMembers.MEMBER_UNIT_OF_MEASURE_TYPE.eq(memberUnitOfMeasureType.getPrimaryKey()),
                        ItemKitMembers.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return itemKitMemberFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public void updateItemKitMemberFromValue(ItemKitMemberValue itemKitMemberValue, BasePK updatedBy) {
        if(itemKitMemberValue.hasBeenModified()) {
            var itemKitMember = itemKitMemberFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemKitMemberValue.getPrimaryKey());
            
            itemKitMember.setThruTime(session.getStartTime());
            itemKitMember.store();

            var itemPK = itemKitMember.getItemPK();
            var inventoryConditionPK = itemKitMember.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemKitMember.getUnitOfMeasureTypePK();
            var memberItemPK = itemKitMember.getMemberItemPK();
            var memberInventoryConditionPK = itemKitMember.getMemberInventoryConditionPK();
            var memberUnitOfMeasureTypePK = itemKitMember.getMemberUnitOfMeasureTypePK();
            var quantity = itemKitMemberValue.getQuantity();
            
            itemKitMember = itemKitMemberFactory.create(itemPK, inventoryConditionPK, unitOfMeasureTypePK,
                    memberItemPK, memberInventoryConditionPK, memberUnitOfMeasureTypePK, quantity, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemKitMember.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemKitMemberTransfer getItemKitMemberTransfer(UserVisit userVisit, ItemKitMember itemKitMember) {
        return itemKitMember == null? null: itemKitMemberTransferCache.getTransfer(userVisit, itemKitMember);
    }

    public ItemKitMemberTransfer getItemKitMemberTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Item memberItem, InventoryCondition memberInventoryCondition, UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMemberTransfer(userVisit, getItemKitMember(item, inventoryCondition, unitOfMeasureType, memberItem, memberInventoryCondition,
                memberUnitOfMeasureType));
    }

    public List<ItemKitMemberTransfer> getItemKitMemberTransfers(UserVisit userVisit, Collection<ItemKitMember> itemKitMembers) {
        List<ItemKitMemberTransfer> itemKitMemberTransfers = new ArrayList<>(itemKitMembers.size());

        itemKitMembers.forEach((itemKitMember) ->
                itemKitMemberTransfers.add(itemKitMemberTransferCache.getTransfer(userVisit, itemKitMember))
        );

        return itemKitMemberTransfers;
    }

    public List<ItemKitMemberTransfer> getItemKitMemberTransfersByItem(UserVisit userVisit, Item item) {
        return getItemKitMemberTransfers(userVisit, getItemKitMembersByItem(item));
    }
    
    public void deleteItemKitMember(ItemKitMember itemKitMember, BasePK deletedBy) {
        itemKitMember.setThruTime(session.getStartTime());
        itemKitMember.store();
        
        sendEvent(itemKitMember.getItemPK(), EventTypes.MODIFY, itemKitMember.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemKitMembers(List<ItemKitMember> itemKitMembers, BasePK deletedBy) {
        itemKitMembers.forEach((itemKitMember) -> 
                deleteItemKitMember(itemKitMember, deletedBy)
        );
    }
    
    public void deleteItemKitMembersByItem(Item item, BasePK deletedBy) {
        deleteItemKitMembers(getItemKitMembersByItemForUpdate(item), deletedBy);
        deleteItemKitMembers(getItemKitMembersByMemberItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemKitMembersByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteItemKitMembers(getItemKitMembersByInventoryConditionForUpdate(inventoryCondition), deletedBy);
        deleteItemKitMembers(getItemKitMembersByMemberInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteItemKitMembersByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemKitMembers(getItemKitMembersByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
        deleteItemKitMembers(getItemKitMembersByMemberUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemKitMembersByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemKitMembers(getItemKitMembersByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
        deleteItemKitMembers(getItemKitMembersByMemberItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Pack Check Requirements
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemPackCheckRequirementFactory itemPackCheckRequirementFactory;

    @Inject
    ItemPackCheckRequirementTransferCache itemPackCheckRequirementTransferCache;

    public ItemPackCheckRequirement createItemPackCheckRequirement(Item item, UnitOfMeasureType unitOfMeasureType,
            Long minimumQuantity, Long maximumQuantity, BasePK createdBy) {
        var itemPackCheckRequirement = itemPackCheckRequirementFactory.create(item,
                unitOfMeasureType, minimumQuantity, maximumQuantity, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemPackCheckRequirement.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemPackCheckRequirement;
    }

    public long countItemPackCheckRequirementByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemPackCheckRequirements)
                .where(ItemPackCheckRequirements.ITEM.eq(item.getPrimaryKey()),
                        ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemPackCheckRequirementByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemPackCheckRequirements)
                .where(ItemPackCheckRequirements.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemPackCheckRequirement getItemPackCheckRequirement(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPackCheckRequirements.fields())
                .from(ItemPackCheckRequirements)
                .where(ItemPackCheckRequirements.ITEM.eq(item.getPrimaryKey()),
                        ItemPackCheckRequirements.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPackCheckRequirementFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemPackCheckRequirement getItemPackCheckRequirement(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemPackCheckRequirement(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public ItemPackCheckRequirement getItemPackCheckRequirementForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemPackCheckRequirement(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public ItemPackCheckRequirementValue getItemPackCheckRequirementValue(ItemPackCheckRequirement itemPackCheckRequirement) {
        return itemPackCheckRequirement == null? null: itemPackCheckRequirement.getItemPackCheckRequirementValue().clone();
    }

    public ItemPackCheckRequirementValue getItemPackCheckRequirementValueForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemPackCheckRequirementForUpdate(item, unitOfMeasureType).getItemPackCheckRequirementValue().clone();
    }

    private List<ItemPackCheckRequirement> getItemPackCheckRequirementsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPackCheckRequirements.fields())
                    .from(ItemPackCheckRequirements)
                    .join(UnitOfMeasureTypes).onKey(ITEM_PACK_CHECK_REQUIREMENTS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(ItemPackCheckRequirements.ITEM.eq(item.getPrimaryKey()),
                            ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemPackCheckRequirementFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPackCheckRequirements.fields())
                    .from(ItemPackCheckRequirements)
                    .where(ItemPackCheckRequirements.ITEM.eq(item.getPrimaryKey()),
                            ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPackCheckRequirementFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByItem(Item item) {
        return getItemPackCheckRequirementsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByItemForUpdate(Item item) {
        return getItemPackCheckRequirementsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemPackCheckRequirement> getItemPackCheckRequirementsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPackCheckRequirements.fields())
                    .from(ItemPackCheckRequirements)
                    .join(Items).onKey(ITEM_PACK_CHECK_REQUIREMENTS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ItemPackCheckRequirements.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemPackCheckRequirementFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPackCheckRequirements.fields())
                    .from(ItemPackCheckRequirements)
                    .where(ItemPackCheckRequirements.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPackCheckRequirementFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemPackCheckRequirementsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemPackCheckRequirementsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByItemAndUnitOfMeasureTypeForUpdate(Item item,
            UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(ItemPackCheckRequirements.fields())
                .from(ItemPackCheckRequirements)
                .where(ItemPackCheckRequirements.ITEM.eq(item.getPrimaryKey()),
                        ItemPackCheckRequirements.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemPackCheckRequirements.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return itemPackCheckRequirementFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public void updateItemPackCheckRequirementFromValue(ItemPackCheckRequirementValue itemPackCheckRequirementValue, BasePK updatedBy) {
        if(itemPackCheckRequirementValue.hasBeenModified()) {
            var itemPackCheckRequirementPK = itemPackCheckRequirementValue.getPrimaryKey();
            var itemPackCheckRequirement = itemPackCheckRequirementFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemPackCheckRequirementPK);
            
            itemPackCheckRequirement.setThruTime(session.getStartTime());
            itemPackCheckRequirement.store();

            var itemPK = itemPackCheckRequirement.getItemPK();
            var unitOfMeasureTypePK = itemPackCheckRequirement.getUnitOfMeasureTypePK();
            var minimumQuantity = itemPackCheckRequirementValue.getMinimumQuantity();
            var maximumQuantity = itemPackCheckRequirementValue.getMaximumQuantity();
            
            itemPackCheckRequirement = itemPackCheckRequirementFactory.create(itemPK, unitOfMeasureTypePK,
                    minimumQuantity, maximumQuantity, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemPackCheckRequirement.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemPackCheckRequirementTransfer getItemPackCheckRequirementTransfer(UserVisit userVisit, ItemPackCheckRequirement itemPackCheckRequirement) {
        return itemPackCheckRequirement == null ? null : itemPackCheckRequirementTransferCache.getTransfer(userVisit, itemPackCheckRequirement);
    }

    public ItemPackCheckRequirementTransfer getItemPackCheckRequirementTransfer(UserVisit userVisit, Item item, UnitOfMeasureType unitOfMeasureType) {
        var itemPackCheckRequirement = getItemPackCheckRequirement(item, unitOfMeasureType);

        return getItemPackCheckRequirementTransfer(userVisit, itemPackCheckRequirement);
    }

    public List<ItemPackCheckRequirementTransfer> getItemPackCheckRequirementTransfersByItem(UserVisit userVisit, Item item) {
        var itemPackCheckRequirements = getItemPackCheckRequirementsByItem(item);
        List<ItemPackCheckRequirementTransfer> itemPackCheckRequirementTransfers = new ArrayList<>(itemPackCheckRequirements.size());
        
        itemPackCheckRequirements.forEach((itemPackCheckRequirement) ->
                itemPackCheckRequirementTransfers.add(itemPackCheckRequirementTransferCache.getTransfer(userVisit, itemPackCheckRequirement))
        );
        
        return itemPackCheckRequirementTransfers;
    }
    
    public void deleteItemPackCheckRequirement(ItemPackCheckRequirement itemPackCheckRequirement, BasePK deletedBy) {
        itemPackCheckRequirement.setThruTime(session.getStartTime());
        
        sendEvent(itemPackCheckRequirement.getItemPK(), EventTypes.MODIFY, itemPackCheckRequirement.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemPackCheckRequirements(List<ItemPackCheckRequirement> itemPackCheckRequirements, BasePK deletedBy) {
        itemPackCheckRequirements.forEach((itemPackCheckRequirement) -> 
                deleteItemPackCheckRequirement(itemPackCheckRequirement, deletedBy)
        );
    }
    
    public void deleteItemPackCheckRequirementsByItem(Item item, BasePK deletedBy) {
        deleteItemPackCheckRequirements(getItemPackCheckRequirementsByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemPackCheckRequirementsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemPackCheckRequirements(getItemPackCheckRequirementsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemPackCheckRequirementsByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemPackCheckRequirements(getItemPackCheckRequirementsByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Unit Customer Type Limits
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemUnitCustomerTypeLimitFactory itemUnitCustomerTypeLimitFactory;

    @Inject
    ItemUnitCustomerTypeLimitTransferCache itemUnitCustomerTypeLimitTransferCache;

    public ItemUnitCustomerTypeLimit createItemUnitCustomerTypeLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType, Long minimumQuantity, Long maximumQuantity,
            BasePK createdBy) {
        var itemUnitCustomerTypeLimit = itemUnitCustomerTypeLimitFactory.create(item,
                inventoryCondition, unitOfMeasureType, customerType, minimumQuantity, maximumQuantity, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitCustomerTypeLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitCustomerTypeLimit;
    }

    public long countItemUnitCustomerTypeLimitsByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitCustomerTypeLimits)
                .where(ItemUnitCustomerTypeLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitCustomerTypeLimitsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitCustomerTypeLimits)
                .where(ItemUnitCustomerTypeLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitCustomerTypeLimitsByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitCustomerTypeLimits)
                .where(ItemUnitCustomerTypeLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitCustomerTypeLimitsByCustomerType(final CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitCustomerTypeLimits)
                .where(ItemUnitCustomerTypeLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemUnitCustomerTypeLimit getItemUnitCustomerTypeLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemUnitCustomerTypeLimits.fields())
                .from(ItemUnitCustomerTypeLimits)
                .where(ItemUnitCustomerTypeLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemUnitCustomerTypeLimitFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemUnitCustomerTypeLimit getItemUnitCustomerTypeLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType) {
        return getItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType, EntityPermission.READ_ONLY);
    }
    
    public ItemUnitCustomerTypeLimit getItemUnitCustomerTypeLimitForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType) {
        return getItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType, EntityPermission.READ_WRITE);
    }
    
    public ItemUnitCustomerTypeLimitValue getItemUnitCustomerTypeLimitValue(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        return itemUnitCustomerTypeLimit == null? null: itemUnitCustomerTypeLimit.getItemUnitCustomerTypeLimitValue().clone();
    }

    public ItemUnitCustomerTypeLimitValue getItemUnitCustomerTypeLimitValueForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType) {
        return getItemUnitCustomerTypeLimitForUpdate(item, inventoryCondition, unitOfMeasureType, customerType).getItemUnitCustomerTypeLimitValue().clone();
    }
    
    private List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .join(InventoryConditions).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(CustomerTypes).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_LAST_DETAIL_FK)
                    .where(ItemUnitCustomerTypeLimits.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    ItemUnitCustomerTypeLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .where(ItemUnitCustomerTypeLimits.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitCustomerTypeLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByItem(Item item) {
        return getItemUnitCustomerTypeLimitsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByItemForUpdate(Item item) {
        return getItemUnitCustomerTypeLimitsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .join(Items).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(CustomerTypes).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_LAST_DETAIL_FK)
                    .where(ItemUnitCustomerTypeLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    ItemUnitCustomerTypeLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .where(ItemUnitCustomerTypeLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitCustomerTypeLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemUnitCustomerTypeLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemUnitCustomerTypeLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .join(Items).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(CustomerTypes).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_LAST_DETAIL_FK)
                    .where(ItemUnitCustomerTypeLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    ItemUnitCustomerTypeLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .where(ItemUnitCustomerTypeLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitCustomerTypeLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitCustomerTypeLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitCustomerTypeLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByItemAndUnitOfMeasureTypeForUpdate(Item item,
            UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(ItemUnitCustomerTypeLimits.fields())
                .from(ItemUnitCustomerTypeLimits)
                .where(ItemUnitCustomerTypeLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return itemUnitCustomerTypeLimitFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    private List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .join(Items).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_CUSTOMER_TYPE_LIMITS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(ItemUnitCustomerTypeLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemUnitCustomerTypeLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitCustomerTypeLimits.fields())
                    .from(ItemUnitCustomerTypeLimits)
                    .where(ItemUnitCustomerTypeLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            ItemUnitCustomerTypeLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitCustomerTypeLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByCustomerType(CustomerType customerType) {
        return getItemUnitCustomerTypeLimitsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByCustomerTypeForUpdate(CustomerType customerType) {
        return getItemUnitCustomerTypeLimitsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    public void updateItemUnitCustomerTypeLimitFromValue(ItemUnitCustomerTypeLimitValue itemUnitCustomerTypeLimitValue, BasePK updatedBy) {
        if(itemUnitCustomerTypeLimitValue.hasBeenModified()) {
            var itemUnitCustomerTypeLimit = itemUnitCustomerTypeLimitFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitCustomerTypeLimitValue.getPrimaryKey());
            
            itemUnitCustomerTypeLimit.setThruTime(session.getStartTime());
            itemUnitCustomerTypeLimit.store();

            var itemPK = itemUnitCustomerTypeLimit.getItemPK();
            var inventoryConditionPK = itemUnitCustomerTypeLimit.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemUnitCustomerTypeLimit.getUnitOfMeasureTypePK();
            var customerTypePK = itemUnitCustomerTypeLimit.getCustomerTypePK();
            var minimumQuantity = itemUnitCustomerTypeLimitValue.getMinimumQuantity();
            var maximumQuantity = itemUnitCustomerTypeLimitValue.getMaximumQuantity();
            
            itemUnitCustomerTypeLimit = itemUnitCustomerTypeLimitFactory.create(itemPK, inventoryConditionPK,
                    unitOfMeasureTypePK, customerTypePK, minimumQuantity, maximumQuantity, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemUnitCustomerTypeLimit.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemUnitCustomerTypeLimitTransfer getItemUnitCustomerTypeLimitTransfer(UserVisit userVisit, ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        return itemUnitCustomerTypeLimit == null? null: itemUnitCustomerTypeLimitTransferCache.getTransfer(userVisit, itemUnitCustomerTypeLimit);
    }

    public ItemUnitCustomerTypeLimitTransfer getItemUnitCustomerTypeLimitTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType) {
        return getItemUnitCustomerTypeLimitTransfer(userVisit, getItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType));
    }

    public List<ItemUnitCustomerTypeLimitTransfer> getItemUnitCustomerTypeLimitTransfers(UserVisit userVisit, Collection<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits) {
        List<ItemUnitCustomerTypeLimitTransfer> itemUnitCustomerTypeLimitTransfers = new ArrayList<>(itemUnitCustomerTypeLimits.size());
        
        itemUnitCustomerTypeLimits.forEach((itemUnitCustomerTypeLimit) ->
                itemUnitCustomerTypeLimitTransfers.add(itemUnitCustomerTypeLimitTransferCache.getTransfer(userVisit, itemUnitCustomerTypeLimit))
        );
        
        return itemUnitCustomerTypeLimitTransfers;
    }
    
    public List<ItemUnitCustomerTypeLimitTransfer> getItemUnitCustomerTypeLimitTransfersByItem(UserVisit userVisit, Item item) {
        return getItemUnitCustomerTypeLimitTransfers(userVisit, getItemUnitCustomerTypeLimitsByItem(item));
    }
    
    public void deleteItemUnitCustomerTypeLimit(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit, BasePK deletedBy) {
        itemUnitCustomerTypeLimit.setThruTime(session.getStartTime());
        
        sendEvent(itemUnitCustomerTypeLimit.getItemPK(), EventTypes.MODIFY, itemUnitCustomerTypeLimit.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemUnitCustomerTypeLimits(List<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits, BasePK deletedBy) {
        itemUnitCustomerTypeLimits.forEach((itemUnitCustomerTypeLimit) -> 
                deleteItemUnitCustomerTypeLimit(itemUnitCustomerTypeLimit, deletedBy)
        );
    }
    
    public void deleteItemUnitCustomerTypeLimitsByItem(Item item, BasePK deletedBy) {
        deleteItemUnitCustomerTypeLimits(getItemUnitCustomerTypeLimitsByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemUnitCustomerTypeLimitsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteItemUnitCustomerTypeLimits(getItemUnitCustomerTypeLimitsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteItemUnitCustomerTypeLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemUnitCustomerTypeLimits(getItemUnitCustomerTypeLimitsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemUnitCustomerTypeLimitsByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemUnitCustomerTypeLimits(getItemUnitCustomerTypeLimitsByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemUnitCustomerTypeLimitsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        deleteItemUnitCustomerTypeLimits(getItemUnitCustomerTypeLimitsByCustomerTypeForUpdate(customerType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Unit Limits
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemUnitLimitFactory itemUnitLimitFactory;

    @Inject
    ItemUnitLimitTransferCache itemUnitLimitTransferCache;

    public ItemUnitLimit createItemUnitLimit(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Long minimumQuantity, Long maximumQuantity, BasePK createdBy) {
        var itemUnitLimit = itemUnitLimitFactory.create(item, inventoryCondition, unitOfMeasureType,
                minimumQuantity, maximumQuantity, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitLimit;
    }

    public long countItemUnitLimitsByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitLimits)
                .where(ItemUnitLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitLimitsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitLimits)
                .where(ItemUnitLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitLimitsByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitLimits)
                .where(ItemUnitLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemUnitLimit getItemUnitLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemUnitLimits.fields())
                .from(ItemUnitLimits)
                .where(ItemUnitLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemUnitLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemUnitLimitFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemUnitLimit getItemUnitLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimit(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public ItemUnitLimit getItemUnitLimitForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimit(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public ItemUnitLimitValue getItemUnitLimitValue(ItemUnitLimit itemUnitLimit) {
        return itemUnitLimit == null? null: itemUnitLimit.getItemUnitLimitValue().clone();
    }

    public ItemUnitLimitValue getItemUnitLimitValueForUpdate(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimitForUpdate(item, inventoryCondition, unitOfMeasureType).getItemUnitLimitValue().clone();
    }
    
    private List<ItemUnitLimit> getItemUnitLimitsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitLimits.fields())
                    .from(ItemUnitLimits)
                    .join(InventoryConditions).onKey(ITEM_UNIT_LIMITS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_LIMITS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(ItemUnitLimits.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemUnitLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitLimits.fields())
                    .from(ItemUnitLimits)
                    .where(ItemUnitLimits.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByItem(Item item) {
        return getItemUnitLimitsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByItemForUpdate(Item item) {
        return getItemUnitLimitsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitLimit> getItemUnitLimitsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitLimits.fields())
                    .from(ItemUnitLimits)
                    .join(Items).onKey(ITEM_UNIT_LIMITS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_LIMITS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(ItemUnitLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemUnitLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitLimits.fields())
                    .from(ItemUnitLimits)
                    .where(ItemUnitLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemUnitLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemUnitLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitLimit> getItemUnitLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitLimits.fields())
                    .from(ItemUnitLimits)
                    .join(Items).onKey(ITEM_UNIT_LIMITS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(ITEM_UNIT_LIMITS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .where(ItemUnitLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME),
                    ItemUnitLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitLimits.fields())
                    .from(ItemUnitLimits)
                    .where(ItemUnitLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(ItemUnitLimits.fields())
                .from(ItemUnitLimits)
                .where(ItemUnitLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitLimits.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return itemUnitLimitFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public void updateItemUnitLimitFromValue(ItemUnitLimitValue itemUnitLimitValue, BasePK updatedBy) {
        if(itemUnitLimitValue.hasBeenModified()) {
            var itemUnitLimit = itemUnitLimitFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitLimitValue.getPrimaryKey());
            
            itemUnitLimit.setThruTime(session.getStartTime());
            itemUnitLimit.store();

            var itemPK = itemUnitLimit.getItemPK();
            var inventoryConditionPK = itemUnitLimit.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemUnitLimit.getUnitOfMeasureTypePK();
            var minimumQuantity = itemUnitLimitValue.getMinimumQuantity();
            var maximumQuantity = itemUnitLimitValue.getMaximumQuantity();
            
            itemUnitLimit = itemUnitLimitFactory.create(itemPK, inventoryConditionPK, unitOfMeasureTypePK,
                    minimumQuantity, maximumQuantity, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemUnitLimit.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemUnitLimitTransfer getItemUnitLimitTransfer(UserVisit userVisit, ItemUnitLimit itemUnitLimit) {
        return itemUnitLimit == null? null: itemUnitLimitTransferCache.getTransfer(userVisit, itemUnitLimit);
    }

    public ItemUnitLimitTransfer getItemUnitLimitTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimitTransfer(userVisit, getItemUnitLimit(item, inventoryCondition, unitOfMeasureType));
    }

    public List<ItemUnitLimitTransfer> getItemUnitLimitTransfersByItem(UserVisit userVisit, Item item) {
        return getItemUnitLimitTransfers(userVisit, getItemUnitLimitsByItem(item));
    }

    public List<ItemUnitLimitTransfer> getItemUnitLimitTransfers(UserVisit userVisit, Collection<ItemUnitLimit> itemUnitLimits) {
        List<ItemUnitLimitTransfer> itemUnitLimitTransfers = new ArrayList<>(itemUnitLimits.size());

        itemUnitLimits.forEach((itemUnitLimit) ->
                itemUnitLimitTransfers.add(itemUnitLimitTransferCache.getTransfer(userVisit, itemUnitLimit))
        );

        return itemUnitLimitTransfers;
    }
    
    public void deleteItemUnitLimit(ItemUnitLimit itemUnitLimit, BasePK deletedBy) {
        itemUnitLimit.setThruTime(session.getStartTime());
        
        sendEvent(itemUnitLimit.getItemPK(), EventTypes.MODIFY, itemUnitLimit.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemUnitLimits(List<ItemUnitLimit> itemUnitLimits, BasePK deletedBy) {
        itemUnitLimits.forEach((itemUnitLimit) -> 
                deleteItemUnitLimit(itemUnitLimit, deletedBy)
        );
    }
    
    public void deleteItemUnitLimitsByItem(Item item, BasePK deletedBy) {
        deleteItemUnitLimits(getItemUnitLimitsByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemUnitLimitsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteItemUnitLimits(getItemUnitLimitsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteItemUnitLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemUnitLimits(getItemUnitLimitsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemUnitLimitsByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemUnitLimits(getItemUnitLimitsByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Unit Price Limits
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemUnitPriceLimitFactory itemUnitPriceLimitFactory;

    @Inject
    ItemUnitPriceLimitTransferCache itemUnitPriceLimitTransferCache;

    public ItemUnitPriceLimit createItemUnitPriceLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long minimumUnitPrice, Long maximumUnitPrice,
            BasePK createdBy) {
        var itemUnitPriceLimit = itemUnitPriceLimitFactory.create(item, inventoryCondition,
                unitOfMeasureType, currency, minimumUnitPrice, maximumUnitPrice, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitPriceLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitPriceLimit;
    }

    public long countItemUnitPriceLimitsByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitPriceLimits)
                .where(ItemUnitPriceLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitPriceLimitsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitPriceLimits)
                .where(ItemUnitPriceLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitPriceLimitsByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitPriceLimits)
                .where(ItemUnitPriceLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemUnitPriceLimitsByCurrency(final Currency currency) {
        return session.getDslContext()
                .selectCount()
                .from(ItemUnitPriceLimits)
                .where(ItemUnitPriceLimits.CURRENCY.eq(currency.getPrimaryKey()),
                        ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemUnitPriceLimit getItemUnitPriceLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemUnitPriceLimits.fields())
                .from(ItemUnitPriceLimits)
                .where(ItemUnitPriceLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitPriceLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemUnitPriceLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitPriceLimits.CURRENCY.eq(currency.getPrimaryKey()),
                        ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemUnitPriceLimitFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemUnitPriceLimit getItemUnitPriceLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public ItemUnitPriceLimit getItemUnitPriceLimitForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public ItemUnitPriceLimitValue getItemUnitPriceLimitValue(ItemUnitPriceLimit itemUnitPriceLimit) {
        return itemUnitPriceLimit == null? null: itemUnitPriceLimit.getItemUnitPriceLimitValue().clone();
    }

    public ItemUnitPriceLimitValue getItemUnitPriceLimitValueForUpdate(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getItemUnitPriceLimitForUpdate(item, inventoryCondition, unitOfMeasureType, currency).getItemUnitPriceLimitValue().clone();
    }
    
    private List<ItemUnitPriceLimit> getItemUnitPriceLimitsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitPriceLimits.fields())
                    .from(ItemUnitPriceLimits)
                    .join(InventoryConditions).onKey(ITEM_UNIT_PRICE_LIMITS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_PRICE_LIMITS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(ITEM_UNIT_PRICE_LIMITS_CURRENCY_FK)
                    .where(ItemUnitPriceLimits.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ItemUnitPriceLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitPriceLimits.fields())
                    .from(ItemUnitPriceLimits)
                    .where(ItemUnitPriceLimits.ITEM.eq(item.getPrimaryKey()),
                            ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitPriceLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByItem(Item item) {
        return getItemUnitPriceLimitsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByItemForUpdate(Item item) {
        return getItemUnitPriceLimitsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitPriceLimit> getItemUnitPriceLimitsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitPriceLimits.fields())
                    .from(ItemUnitPriceLimits)
                    .join(Items).onKey(ITEM_UNIT_PRICE_LIMITS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_UNIT_PRICE_LIMITS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(ITEM_UNIT_PRICE_LIMITS_CURRENCY_FK)
                    .where(ItemUnitPriceLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ItemUnitPriceLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitPriceLimits.fields())
                    .from(ItemUnitPriceLimits)
                    .where(ItemUnitPriceLimits.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitPriceLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemUnitPriceLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemUnitPriceLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitPriceLimit> getItemUnitPriceLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemUnitPriceLimits.fields())
                    .from(ItemUnitPriceLimits)
                    .join(Items).onKey(ITEM_UNIT_PRICE_LIMITS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(ITEM_UNIT_PRICE_LIMITS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(Currencies).onKey(ITEM_UNIT_PRICE_LIMITS_CURRENCY_FK)
                    .where(ItemUnitPriceLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ItemUnitPriceLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemUnitPriceLimits.fields())
                    .from(ItemUnitPriceLimits)
                    .where(ItemUnitPriceLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemUnitPriceLimitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitPriceLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitPriceLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByItemAndUnitOfMeasureTypeForUpdate(Item item,
            UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(ItemUnitPriceLimits.fields())
                .from(ItemUnitPriceLimits)
                .where(ItemUnitPriceLimits.ITEM.eq(item.getPrimaryKey()),
                        ItemUnitPriceLimits.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemUnitPriceLimits.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return itemUnitPriceLimitFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public void updateItemUnitPriceLimitFromValue(ItemUnitPriceLimitValue itemUnitPriceLimitValue, BasePK updatedBy) {
        if(itemUnitPriceLimitValue.hasBeenModified()) {
            var itemUnitPriceLimit = itemUnitPriceLimitFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemUnitPriceLimitValue.getPrimaryKey());
            
            itemUnitPriceLimit.setThruTime(session.getStartTime());
            itemUnitPriceLimit.store();

            var itemPK = itemUnitPriceLimit.getItemPK();
            var inventoryConditionPK = itemUnitPriceLimit.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemUnitPriceLimit.getUnitOfMeasureTypePK();
            var currencyPK = itemUnitPriceLimit.getCurrencyPK();
            var minimumUnitPrice = itemUnitPriceLimitValue.getMinimumUnitPrice();
            var maximumUnitPrice = itemUnitPriceLimitValue.getMaximumUnitPrice();
            
            itemUnitPriceLimit = itemUnitPriceLimitFactory.create(itemPK, inventoryConditionPK,
                    unitOfMeasureTypePK, currencyPK, minimumUnitPrice, maximumUnitPrice, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemUnitPriceLimit.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemUnitPriceLimitTransfer getItemUnitPriceLimitTransfer(UserVisit userVisit, ItemUnitPriceLimit itemUnitPriceLimit) {
        return itemUnitPriceLimit == null? null: itemUnitPriceLimitTransferCache.getTransfer(userVisit, itemUnitPriceLimit);
    }

    public ItemUnitPriceLimitTransfer getItemUnitPriceLimitTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getItemUnitPriceLimitTransfer(userVisit, getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency));
    }

    public List<ItemUnitPriceLimitTransfer> getItemUnitPriceLimitTransfers(UserVisit userVisit, Collection<ItemUnitPriceLimit> itemUnitPriceLimits) {
        List<ItemUnitPriceLimitTransfer> itemUnitPriceLimitTransfers = new ArrayList<>(itemUnitPriceLimits.size());

        itemUnitPriceLimits.forEach((itemUnitPriceLimit) ->
                itemUnitPriceLimitTransfers.add(itemUnitPriceLimitTransferCache.getTransfer(userVisit, itemUnitPriceLimit))
        );

        return itemUnitPriceLimitTransfers;
    }

    public List<ItemUnitPriceLimitTransfer> getItemUnitPriceLimitTransfersByItem(UserVisit userVisit, Item item) {
        return getItemUnitPriceLimitTransfers(userVisit, getItemUnitPriceLimitsByItem(item));
    }
    
    public void deleteItemUnitPriceLimit(ItemUnitPriceLimit itemUnitPriceLimit, BasePK deletedBy) {
        itemUnitPriceLimit.setThruTime(session.getStartTime());
        
        sendEvent(itemUnitPriceLimit.getItemPK(), EventTypes.MODIFY, itemUnitPriceLimit.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemUnitPriceLimits(List<ItemUnitPriceLimit> itemUnitPriceLimits, BasePK deletedBy) {
        itemUnitPriceLimits.forEach((itemUnitPriceLimit) -> 
                deleteItemUnitPriceLimit(itemUnitPriceLimit, deletedBy)
        );
    }
    
    public void deleteItemUnitPriceLimitsByItem(Item item, BasePK deletedBy) {
        deleteItemUnitPriceLimits(getItemUnitPriceLimitsByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemUnitPriceLimitsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteItemUnitPriceLimits(getItemUnitPriceLimitsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteItemUnitPriceLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemUnitPriceLimits(getItemUnitPriceLimitsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemUnitPriceLimitsByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemUnitPriceLimits(getItemUnitPriceLimitsByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemPriceTypeFactory itemPriceTypeFactory;

    @Inject
    ItemPriceTypeTransferCache itemPriceTypeTransferCache;

    public ItemPriceType createItemPriceType(String itemPriceTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemPriceType = itemPriceTypeFactory.create(itemPriceTypeName, isDefault, sortOrder);

        sendEvent(itemPriceType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemPriceType;
    }

    public long countItemPriceTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemPriceTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemPriceType */
    public ItemPriceType getItemPriceTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemPriceTypePK(entityInstance.getEntityUniqueId());

        return itemPriceTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemPriceType getItemPriceTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemPriceTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemPriceType getItemPriceTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemPriceTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemPriceType getItemPriceTypeByName(String itemPriceTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPriceTypes.fields())
                .from(ItemPriceTypes)
                .where(ItemPriceTypes.ITEM_PRICE_TYPE_NAME.eq(itemPriceTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPriceTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemPriceType getItemPriceTypeByName(String itemPriceTypeName) {
        return getItemPriceTypeByName(itemPriceTypeName, EntityPermission.READ_ONLY);
    }

    public ItemPriceType getItemPriceTypeByNameForUpdate(String itemPriceTypeName) {
        return getItemPriceTypeByName(itemPriceTypeName, EntityPermission.READ_WRITE);
    }

    public ItemPriceType getDefaultItemPriceType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPriceTypes.fields())
                .from(ItemPriceTypes)
                .where(ItemPriceTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPriceTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemPriceType getDefaultItemPriceType() {
        return getDefaultItemPriceType(EntityPermission.READ_ONLY);
    }

    public ItemPriceType getDefaultItemPriceTypeForUpdate() {
        return getDefaultItemPriceType(EntityPermission.READ_WRITE);
    }

    public ItemPriceTypeValue getDefaultItemPriceTypeValueForUpdate() {
        return getDefaultItemPriceTypeForUpdate().getItemPriceTypeValue().clone();
    }

    public List<ItemPriceType> getItemPriceTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemPriceTypes.fields())
                .from(ItemPriceTypes)
                .orderBy(ItemPriceTypes.SORT_ORDER, ItemPriceTypes.ITEM_PRICE_TYPE_NAME),
                ItemPriceTypeFactory.class);

        return itemPriceTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ItemPriceTypeChoicesBean getItemPriceTypeChoices(String defaultItemPriceTypeChoice, Language language,
            boolean allowNullChoice) {
        var itemPriceTypes = getItemPriceTypes();
        var size = itemPriceTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemPriceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemPriceType : itemPriceTypes) {
            var label = getBestItemPriceTypeDescription(itemPriceType, language);
            var value = itemPriceType.getItemPriceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemPriceTypeChoice != null && defaultItemPriceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemPriceType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemPriceTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemPriceTypeTransfer getItemPriceTypeTransfer(UserVisit userVisit, ItemPriceType itemPriceType) {
        return itemPriceTypeTransferCache.getTransfer(userVisit, itemPriceType);
    }

    public List<ItemPriceTypeTransfer> getItemPriceTypeTransfers(UserVisit userVisit, Collection<ItemPriceType> entities) {
        var itemPriceTypeTransfers = new ArrayList<ItemPriceTypeTransfer>(entities.size());

        entities.forEach((entity) ->
                itemPriceTypeTransfers.add(itemPriceTypeTransferCache.getTransfer(userVisit, entity))
        );

        return itemPriceTypeTransfers;
    }

    public List<ItemPriceTypeTransfer> getItemPriceTypeTransfers(UserVisit userVisit) {
        return getItemPriceTypeTransfers(userVisit, getItemPriceTypes());
    }

    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemPriceTypeDescriptionFactory itemPriceTypeDescriptionFactory;

    public ItemPriceTypeDescription createItemPriceTypeDescription(ItemPriceType itemPriceType, Language language,
            String description, BasePK createdBy) {
        var itemPriceTypeDescription = itemPriceTypeDescriptionFactory.create(itemPriceType, language, description);

        sendEvent(itemPriceType.getPrimaryKey(), EventTypes.MODIFY, itemPriceTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemPriceTypeDescription;
    }
    
    public ItemPriceTypeDescription getItemPriceTypeDescription(ItemPriceType itemPriceType, Language language) {
        var query = session.getDslContext()
                .select(ItemPriceTypeDescriptions.fields())
                .from(ItemPriceTypeDescriptions)
                .where(ItemPriceTypeDescriptions.ITEM_PRICE_TYPE.eq(itemPriceType.getPrimaryKey()),
                        ItemPriceTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return itemPriceTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestItemPriceTypeDescription(ItemPriceType itemPriceType, Language language) {
        String description;
        var itemPriceTypeDescription = getItemPriceTypeDescription(itemPriceType, language);
        
        if(itemPriceTypeDescription == null && !language.getIsDefault()) {
            itemPriceTypeDescription = getItemPriceTypeDescription(itemPriceType, partyControl.getDefaultLanguage());
        }
        
        if(itemPriceTypeDescription == null) {
            description = itemPriceType.getItemPriceTypeName();
        } else {
            description = itemPriceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Prices
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemPriceFactory itemPriceFactory;

    @Inject
    ItemPriceTransferCache itemPriceTransferCache;

    public ItemPrice createItemPrice(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, BasePK createdBy) {
        var itemPrice = itemPriceFactory.create(item, inventoryCondition, unitOfMeasureType, currency,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemPrice;
    }

    public long countItemPricesByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemPrices)
                .where(ItemPrices.ITEM.eq(item.getPrimaryKey()),
                        ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemPricesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(ItemPrices)
                .where(ItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemPrices)
                .where(ItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemPricesByCurrency(Currency currency) {
        return session.getDslContext()
                .selectCount()
                .from(ItemPrices)
                .where(ItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                        ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ItemPrice> getItemPricesByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .join(InventoryConditions).onKey(ITEM_PRICES_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_PRICES_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(ITEM_PRICES_CURRENCY_FK)
                    .where(ItemPrices.ITEM.eq(item.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ItemPriceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .where(ItemPrices.ITEM.eq(item.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemPrice> getItemPricesByItem(Item item) {
        return getItemPricesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPrice> getItemPricesByItemForUpdate(Item item) {
        return getItemPricesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemPrice> getItemPricesByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .join(Items).onKey(ITEM_PRICES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_PRICES_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(ITEM_PRICES_CURRENCY_FK)
                    .where(ItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ItemPriceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .where(ItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemPrice> getItemPricesByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemPricesByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPrice> getItemPricesByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemPricesByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    private List<ItemPrice> getItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .join(Items).onKey(ITEM_PRICES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(ITEM_PRICES_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(Currencies).onKey(ITEM_PRICES_CURRENCY_FK)
                    .where(ItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ItemPriceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .where(ItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemPrice> getItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemPricesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<ItemPrice> getItemPricesByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemPricesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<ItemPrice> getItemPricesByCurrency(Currency currency, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .join(Items).onKey(ITEM_PRICES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(ITEM_PRICES_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_PRICES_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(ItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemPriceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .where(ItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemPrice> getItemPricesByCurrency(Currency currency) {
        return getItemPricesByCurrency(currency, EntityPermission.READ_ONLY);
    }

    public List<ItemPrice> getItemPricesByCurrencyForUpdate(Currency currency) {
        return getItemPricesByCurrency(currency, EntityPermission.READ_WRITE);
    }

    public List<ItemPrice> getItemPricesByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(ItemPrices.fields())
                .from(ItemPrices)
                .where(ItemPrices.ITEM.eq(item.getPrimaryKey()),
                        ItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return itemPriceFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    private List<ItemPrice> getItemPrices(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .join(Currencies).onKey(ITEM_PRICES_CURRENCY_FK)
                    .where(ItemPrices.ITEM.eq(item.getPrimaryKey()),
                            ItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ItemPriceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPrices.fields())
                    .from(ItemPrices)
                    .where(ItemPrices.ITEM.eq(item.getPrimaryKey()),
                            ItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemPrice> getItemPrices(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getItemPrices(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPrice> getItemPricesForUpdate(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getItemPrices(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private ItemPrice getItemPrice(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPrices.fields())
                .from(ItemPrices)
                .where(ItemPrices.ITEM.eq(item.getPrimaryKey()),
                        ItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                        ItemPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPriceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemPrice getItemPrice(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getItemPrice(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public ItemPrice getItemPriceForUpdate(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getItemPrice(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public ItemPriceTransfer getItemPriceTransfer(UserVisit userVisit, ItemPrice itemPrice) {
        return itemPriceTransferCache.getTransfer(userVisit, itemPrice);
    }
    
    public List<ItemPriceTransfer> getItemPriceTransfers(UserVisit userVisit, Collection<ItemPrice> itemPrices) {
        List<ItemPriceTransfer> itemPriceTransfers = new ArrayList<>(itemPrices.size());
        
        itemPrices.forEach((itemPrice) ->
                itemPriceTransfers.add(itemPriceTransferCache.getTransfer(userVisit, itemPrice))
        );
        
        return itemPriceTransfers;
    }
    
    public ListWrapper<HistoryTransfer<ItemPriceTransfer>> getItemPriceHistory(UserVisit userVisit, ItemPrice itemPrice) {
        return itemPriceTransferCache.getHistory(userVisit, itemPrice);
    }
    
    public List<ItemPriceTransfer> getItemPriceTransfersByItem(UserVisit userVisit, Item item) {
        return getItemPriceTransfers(userVisit, getItemPricesByItem(item));
    }
    
    public void deleteItemPrice(ItemPrice itemPrice, BasePK deletedBy) {
        var item = itemPrice.getItem();
        var itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();
        
        if(com.echothree.model.control.item.common.ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
            var itemFixedPrice = getItemFixedPriceForUpdate(itemPrice);
            
            if(itemFixedPrice != null) {
                deleteItemFixedPrice(itemFixedPrice, deletedBy);
            }
        } else if(com.echothree.model.control.item.common.ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
            var itemVariablePrice = getItemVariablePriceForUpdate(itemPrice);
            
            if(itemVariablePrice != null) {
                deleteItemVariablePrice(itemVariablePrice, deletedBy);
            }
        }
        
        itemPrice.setThruTime(session.getStartTime());
        itemPrice.store();
        
        sendEvent(itemPrice.getItemPK(), EventTypes.MODIFY, itemPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);

        offerItemLogic.deleteOfferItemPrices(offerItemControl.getOfferItemPricesForUpdate(item,
                itemPrice.getInventoryCondition(), itemPrice.getUnitOfMeasureType(), itemPrice.getCurrency()),
                deletedBy);
    }
    
    public void deleteItemPrices(List<ItemPrice> itemPrices, BasePK deletedBy) {
        itemPrices.forEach((itemPrice) -> 
                deleteItemPrice(itemPrice, deletedBy)
        );
    }
    
    public void deleteItemPricesByItem(Item item, BasePK deletedBy) {
        deleteItemPrices(getItemPricesByItemForUpdate(item), deletedBy);
    }
    
    public void deleteItemPricesByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteItemPrices(getItemPricesByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemPrices(getItemPricesByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemPrices(getItemPricesByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Fixed Prices
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemFixedPriceFactory itemFixedPriceFactory;

    public ItemFixedPrice createItemFixedPrice(ItemPrice itemPrice, Long unitPrice, BasePK createdBy) {
        var itemFixedPrice = itemFixedPriceFactory.create(itemPrice, unitPrice, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(itemPrice.getItemPK(), EventTypes.MODIFY, itemFixedPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemFixedPrice;
    }
    
    private ItemFixedPrice getItemFixedPrice(ItemPrice itemPrice, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemFixedPrices.fields())
                .from(ItemFixedPrices)
                .where(ItemFixedPrices.ITEM_PRICE.eq(itemPrice.getPrimaryKey()),
                        ItemFixedPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemFixedPriceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemFixedPrice getItemFixedPrice(ItemPrice itemPrice) {
        return getItemFixedPrice(itemPrice, EntityPermission.READ_ONLY);
    }
    
    public ItemFixedPrice getItemFixedPriceForUpdate(ItemPrice itemPrice) {
        return getItemFixedPrice(itemPrice, EntityPermission.READ_WRITE);
    }
    
    public ItemFixedPriceValue getItemFixedPriceValue(ItemFixedPrice itemFixedPrice) {
        return itemFixedPrice == null? null: itemFixedPrice.getItemFixedPriceValue().clone();
    }

    public ItemFixedPriceValue getItemFixedPriceValueForUpdate(ItemPrice itemPrice) {
        var itemFixedPrice = getItemFixedPriceForUpdate(itemPrice);
        
        return itemFixedPrice == null? null: itemFixedPrice.getItemFixedPriceValue().clone();
    }
    
    public List<ItemFixedPrice> getItemFixedPriceHistory(ItemPrice itemPrice) {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemFixedPrices.fields())
                .from(ItemFixedPrices)
                .where(ItemFixedPrices.ITEM_PRICE.eq(itemPrice.getPrimaryKey()))
                .orderBy(ItemFixedPrices.THRU_TIME),
                ItemFixedPriceFactory.class);

        return itemFixedPriceFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public void updateItemFixedPriceFromValue(ItemFixedPriceValue itemFixedPriceValue, BasePK updatedBy) {
        if(itemFixedPriceValue.hasBeenModified()) {
            var itemFixedPrice = itemFixedPriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemFixedPriceValue.getPrimaryKey());
            
            itemFixedPrice.setThruTime(session.getStartTime());
            itemFixedPrice.store();

            var itemPricePK = itemFixedPrice.getItemPricePK();
            var unitPrice = itemFixedPriceValue.getUnitPrice();
            
            itemFixedPrice = itemFixedPriceFactory.create(itemPricePK, unitPrice,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemFixedPrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemFixedPrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemFixedPrice(ItemFixedPrice itemFixedPrice, BasePK deletedBy) {
        itemFixedPrice.setThruTime(session.getStartTime());
        itemFixedPrice.store();
        
        sendEvent(itemFixedPrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemFixedPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Variable Prices
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemVariablePriceFactory itemVariablePriceFactory;

    public ItemVariablePrice createItemVariablePrice(ItemPrice itemPrice, Long minimumUnitPrice, Long maximumUnitPrice, Long unitPriceIncrement,
            BasePK createdBy) {
        var itemVariablePrice = itemVariablePriceFactory.create(itemPrice, minimumUnitPrice,
                maximumUnitPrice, unitPriceIncrement, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(itemPrice.getItemPK(), EventTypes.MODIFY, itemVariablePrice.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemVariablePrice;
    }
    
    private ItemVariablePrice getItemVariablePrice(ItemPrice itemPrice, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemVariablePrices.fields())
                .from(ItemVariablePrices)
                .where(ItemVariablePrices.ITEM_PRICE.eq(itemPrice.getPrimaryKey()),
                        ItemVariablePrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemVariablePriceFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemVariablePrice getItemVariablePrice(ItemPrice itemPrice) {
        return getItemVariablePrice(itemPrice, EntityPermission.READ_ONLY);
    }
    
    public ItemVariablePrice getItemVariablePriceForUpdate(ItemPrice itemPrice) {
        return getItemVariablePrice(itemPrice, EntityPermission.READ_WRITE);
    }
    
    public ItemVariablePriceValue getItemVariablePriceValue(ItemVariablePrice itemVariablePrice) {
        return itemVariablePrice == null? null: itemVariablePrice.getItemVariablePriceValue().clone();
    }

    public ItemVariablePriceValue getItemVariablePriceValueForUpdate(ItemPrice itemPrice) {
        var itemVariablePrice = getItemVariablePriceForUpdate(itemPrice);
        
        return itemVariablePrice == null? null: itemVariablePrice.getItemVariablePriceValue().clone();
    }
    
    public List<ItemVariablePrice> getItemVariablePriceHistory(ItemPrice itemPrice) {
        var query = session.applyLimit(session.getDslContext()
                .select(ItemVariablePrices.fields())
                .from(ItemVariablePrices)
                .where(ItemVariablePrices.ITEM_PRICE.eq(itemPrice.getPrimaryKey()))
                .orderBy(ItemVariablePrices.THRU_TIME),
                ItemVariablePriceFactory.class);

        return itemVariablePriceFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public void updateItemVariablePriceFromValue(ItemVariablePriceValue itemVariablePriceValue, BasePK updatedBy) {
        if(itemVariablePriceValue.hasBeenModified()) {
            var itemVariablePrice = itemVariablePriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemVariablePriceValue.getPrimaryKey());
            
            itemVariablePrice.setThruTime(session.getStartTime());
            itemVariablePrice.store();

            var itemPricePK = itemVariablePrice.getItemPricePK();
            var maximumUnitPrice = itemVariablePriceValue.getMaximumUnitPrice();
            var minimumUnitPrice = itemVariablePriceValue.getMinimumUnitPrice();
            var unitPriceIncrement = itemVariablePriceValue.getUnitPriceIncrement();
            
            itemVariablePrice = itemVariablePriceFactory.create(itemPricePK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemVariablePrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemVariablePrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemVariablePrice(ItemVariablePrice itemVariablePrice, BasePK deletedBy) {
        itemVariablePrice.setThruTime(session.getStartTime());
        itemVariablePrice.store();
        
        sendEvent(itemVariablePrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemVariablePrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDescriptionTypeDetailFactory itemDescriptionTypeDetailFactory;

    @Inject
    protected ItemDescriptionTypeFactory itemDescriptionTypeFactory;

    @Inject
    ItemDescriptionTypeTransferCache itemDescriptionTypeTransferCache;

    public ItemDescriptionType createItemDescriptionType(String itemDescriptionTypeName, ItemDescriptionType parentItemDescriptionType,
            Boolean useParentIfMissing, MimeTypeUsageType mimeTypeUsageType, Boolean checkContentWebAddress, Boolean includeInIndex, Boolean indexDefault,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultItemDescriptionType = getDefaultItemDescriptionType();
        var defaultFound = defaultItemDescriptionType != null;

        if(defaultFound && isDefault) {
            var defaultItemDescriptionTypeDetailValue = getDefaultItemDescriptionTypeDetailValueForUpdate();

            defaultItemDescriptionTypeDetailValue.setIsDefault(false);
            updateItemDescriptionTypeFromValue(defaultItemDescriptionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemDescriptionType = itemDescriptionTypeFactory.create();
        var itemDescriptionTypeDetail = itemDescriptionTypeDetailFactory.create(itemDescriptionType, itemDescriptionTypeName,
                parentItemDescriptionType, useParentIfMissing, mimeTypeUsageType, checkContentWebAddress, includeInIndex, indexDefault, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        itemDescriptionType = itemDescriptionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemDescriptionType.getPrimaryKey());
        itemDescriptionType.setActiveDetail(itemDescriptionTypeDetail);
        itemDescriptionType.setLastDetail(itemDescriptionTypeDetail);
        itemDescriptionType.store();

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemDescriptionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemDescriptionType */
    public ItemDescriptionType getItemDescriptionTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new ItemDescriptionTypePK(entityInstance.getEntityUniqueId());

        return itemDescriptionTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemDescriptionType getItemDescriptionTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemDescriptionTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionType getItemDescriptionTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemDescriptionTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeDetailValue getItemDescriptionTypeDetailValueForUpdate(ItemDescriptionType itemDescriptionType) {
        return itemDescriptionType.getLastDetailForUpdate().getItemDescriptionTypeDetailValue().clone();
    }

    public long countItemDescriptionTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemDescriptionTypesByParentItemDescriptionType(ItemDescriptionType parentItemDescriptionType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeDetails.PARENT_ITEM_DESCRIPTION_TYPE.eq(parentItemDescriptionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ItemDescriptionType getItemDescriptionTypeByName(String itemDescriptionTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypes.fields())
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME.eq(itemDescriptionTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionType getItemDescriptionTypeByName(String itemDescriptionTypeName) {
        return getItemDescriptionTypeByName(itemDescriptionTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionType getItemDescriptionTypeByNameForUpdate(String itemDescriptionTypeName) {
        return getItemDescriptionTypeByName(itemDescriptionTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeDetailValue getItemDescriptionTypeDetailValueByNameForUpdate(String itemDescriptionTypeName) {
        return getItemDescriptionTypeDetailValueForUpdate(getItemDescriptionTypeByNameForUpdate(itemDescriptionTypeName));
    }

    private List<ItemDescriptionType> getItemDescriptionTypesByIncludeInIndex(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypes.fields())
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeDetails.INCLUDE_IN_INDEX.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDescriptionTypeDetails.SORT_ORDER, ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME),
                    ItemDescriptionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesByIncludeInIndex() {
        return getItemDescriptionTypesByIncludeInIndex(EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesByIncludeInIndexForUpdate() {
        return getItemDescriptionTypesByIncludeInIndex(EntityPermission.READ_WRITE);
    }

    private ItemDescriptionType getIndexDefaultItemDescriptionType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypes.fields())
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeDetails.INDEX_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionType getIndexDefaultItemDescriptionType() {
        return getIndexDefaultItemDescriptionType(EntityPermission.READ_ONLY);
    }

    public ItemDescriptionType getIndexDefaultItemDescriptionTypeForUpdate() {
        return getIndexDefaultItemDescriptionType(EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeDetailValue getIndexDefaultItemDescriptionTypeDetailValueForUpdate() {
        return getIndexDefaultItemDescriptionTypeForUpdate().getLastDetailForUpdate().getItemDescriptionTypeDetailValue().clone();
    }

    public ItemDescriptionType getDefaultItemDescriptionType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypes.fields())
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionType getDefaultItemDescriptionType() {
        return getDefaultItemDescriptionType(EntityPermission.READ_ONLY);
    }

    public ItemDescriptionType getDefaultItemDescriptionTypeForUpdate() {
        return getDefaultItemDescriptionType(EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeDetailValue getDefaultItemDescriptionTypeDetailValueForUpdate() {
        return getDefaultItemDescriptionTypeForUpdate().getLastDetailForUpdate().getItemDescriptionTypeDetailValue().clone();
    }

    private List<ItemDescriptionType> getItemDescriptionTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypes.fields())
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDescriptionTypeDetails.SORT_ORDER, ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME),
                    ItemDescriptionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionType> getItemDescriptionTypes() {
        return getItemDescriptionTypes(EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesForUpdate() {
        return getItemDescriptionTypes(EntityPermission.READ_WRITE);
    }

    private List<ItemDescriptionType> getItemDescriptionTypesByParentItemDescriptionType(ItemDescriptionType parentItemDescriptionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypes.fields())
                .from(ItemDescriptionTypes)
                .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeDetails.PARENT_ITEM_DESCRIPTION_TYPE.eq(parentItemDescriptionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDescriptionTypeDetails.SORT_ORDER, ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME),
                    ItemDescriptionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesByParentItemDescriptionType(ItemDescriptionType parentItemDescriptionType) {
        return getItemDescriptionTypesByParentItemDescriptionType(parentItemDescriptionType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesByParentItemDescriptionTypeForUpdate(ItemDescriptionType parentItemDescriptionType) {
        return getItemDescriptionTypesByParentItemDescriptionType(parentItemDescriptionType, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeTransfer getItemDescriptionTypeTransfer(UserVisit userVisit, ItemDescriptionType itemDescriptionType) {
        return itemDescriptionTypeTransferCache.getTransfer(userVisit, itemDescriptionType);
    }

    public List<ItemDescriptionTypeTransfer> getItemDescriptionTypeTransfers(UserVisit userVisit, Collection<ItemDescriptionType> itemDescriptionTypes) {
        List<ItemDescriptionTypeTransfer> itemDescriptionTypeTransfers = new ArrayList<>(itemDescriptionTypes.size());

        itemDescriptionTypes.forEach((itemDescriptionType) ->
                itemDescriptionTypeTransfers.add(itemDescriptionTypeTransferCache.getTransfer(userVisit, itemDescriptionType))
        );

        return itemDescriptionTypeTransfers;
    }

    public List<ItemDescriptionTypeTransfer> getItemDescriptionTypeTransfers(UserVisit userVisit) {
        return getItemDescriptionTypeTransfers(userVisit, getItemDescriptionTypes());
    }

    public List<ItemDescriptionTypeTransfer> getItemDescriptionTypeTransfersByParentItemDescriptionType(UserVisit userVisit,
            ItemDescriptionType parentItemDescriptionType) {
        return getItemDescriptionTypeTransfers(userVisit, getItemDescriptionTypesByParentItemDescriptionType(parentItemDescriptionType));
    }

    public ItemDescriptionTypeChoicesBean getItemDescriptionTypeChoices(String defaultItemDescriptionTypeChoice, Language language, boolean allowNullChoice) {
        var itemDescriptionTypes = getItemDescriptionTypes();
        var size = itemDescriptionTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemDescriptionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemDescriptionType : itemDescriptionTypes) {
            var itemDescriptionTypeDetail = itemDescriptionType.getLastDetail();

            var label = getBestItemDescriptionTypeDescription(itemDescriptionType, language);
            var value = itemDescriptionTypeDetail.getItemDescriptionTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultItemDescriptionTypeChoice != null && defaultItemDescriptionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemDescriptionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemDescriptionTypeChoicesBean(labels, values, defaultValue);
    }

    public boolean isParentItemDescriptionTypeSafe(ItemDescriptionType itemDescriptionType,
            ItemDescriptionType parentItemDescriptionType) {
        var safe = true;

        if(parentItemDescriptionType != null) {
            Set<ItemDescriptionType> parentItemDescriptionTypes = new HashSet<>();

            parentItemDescriptionTypes.add(itemDescriptionType);
            do {
                if(parentItemDescriptionTypes.contains(parentItemDescriptionType)) {
                    safe = false;
                    break;
                }

                parentItemDescriptionTypes.add(parentItemDescriptionType);
                parentItemDescriptionType = parentItemDescriptionType.getLastDetail().getParentItemDescriptionType();
            } while(parentItemDescriptionType != null);
        }

        return safe;
    }

    private void updateItemDescriptionTypeFromValue(ItemDescriptionTypeDetailValue itemDescriptionTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemDescriptionTypeDetailValue.hasBeenModified()) {
            var itemDescriptionType = itemDescriptionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionTypeDetailValue.getItemDescriptionTypePK());
            var itemDescriptionTypeDetail = itemDescriptionType.getActiveDetailForUpdate();

            itemDescriptionTypeDetail.setThruTime(session.getStartTime());
            itemDescriptionTypeDetail.store();

            var itemDescriptionTypePK = itemDescriptionTypeDetail.getItemDescriptionTypePK(); // Not updated
            var itemDescriptionTypeName = itemDescriptionTypeDetailValue.getItemDescriptionTypeName();
            var parentItemDescriptionTypePK = itemDescriptionTypeDetailValue.getParentItemDescriptionTypePK();
            var useParentIfMissing = itemDescriptionTypeDetailValue.getUseParentIfMissing();
            var mimeTypeUsageTypePK = itemDescriptionTypeDetail.getMimeTypeUsageTypePK(); // Not updated
            var checkContentWebAddress = itemDescriptionTypeDetailValue.getCheckContentWebAddress();
            var includeInIndex = itemDescriptionTypeDetailValue.getIncludeInIndex();
            var indexDefault = itemDescriptionTypeDetailValue.getIndexDefault();
            var isDefault = itemDescriptionTypeDetailValue.getIsDefault();
            var sortOrder = itemDescriptionTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultItemDescriptionType = getDefaultItemDescriptionType();
                var defaultFound = defaultItemDescriptionType != null && !defaultItemDescriptionType.equals(itemDescriptionType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultItemDescriptionTypeDetailValue = getDefaultItemDescriptionTypeDetailValueForUpdate();

                    defaultItemDescriptionTypeDetailValue.setIsDefault(false);
                    updateItemDescriptionTypeFromValue(defaultItemDescriptionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            itemDescriptionTypeDetail = itemDescriptionTypeDetailFactory.create(itemDescriptionTypePK, itemDescriptionTypeName,
                    parentItemDescriptionTypePK, useParentIfMissing, mimeTypeUsageTypePK, checkContentWebAddress, includeInIndex, indexDefault, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            itemDescriptionType.setActiveDetail(itemDescriptionTypeDetail);
            itemDescriptionType.setLastDetail(itemDescriptionTypeDetail);

            sendEvent(itemDescriptionTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateItemDescriptionTypeFromValue(ItemDescriptionTypeDetailValue itemDescriptionTypeDetailValue, BasePK updatedBy) {
        updateItemDescriptionTypeFromValue(itemDescriptionTypeDetailValue, true, updatedBy);
    }

    private void deleteItemDescriptionType(ItemDescriptionType itemDescriptionType, boolean checkDefault, BasePK deletedBy) {
        var itemDescriptionTypeDetail = itemDescriptionType.getLastDetailForUpdate();
        var mimeTypeUsageType = itemDescriptionTypeDetail.getMimeTypeUsageType();

        deleteItemDescriptionsByItemDescriptionType(itemDescriptionType, deletedBy);
        deleteItemDescriptionTypesByParentItemDescriptionType(itemDescriptionType, deletedBy);
        deleteItemDescriptionTypeDescriptionsByItemDescriptionType(itemDescriptionType, deletedBy);
        deleteItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType, deletedBy);

        if(mimeTypeUsageType != null) {
            var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                deleteItemImageDescriptionTypeByItemDescriptionType(itemDescriptionType, deletedBy);
            }
        }

        itemDescriptionTypeDetail.setThruTime(session.getStartTime());
        itemDescriptionType.setActiveDetail(null);
        itemDescriptionType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultItemDescriptionType = getDefaultItemDescriptionType();
            
            if(defaultItemDescriptionType == null) {
                var itemDescriptionTypes = getItemDescriptionTypesForUpdate();

                if(!itemDescriptionTypes.isEmpty()) {
                    var iter = itemDescriptionTypes.iterator();
                    if(iter.hasNext()) {
                        defaultItemDescriptionType = iter.next();
                    }
                    var itemDescriptionTypeDetailValue = Objects.requireNonNull(defaultItemDescriptionType).getLastDetailForUpdate().getItemDescriptionTypeDetailValue().clone();

                    itemDescriptionTypeDetailValue.setIsDefault(true);
                    updateItemDescriptionTypeFromValue(itemDescriptionTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteItemDescriptionType(ItemDescriptionType itemDescriptionType, BasePK deletedBy) {
        deleteItemDescriptionType(itemDescriptionType, true, deletedBy);
    }

    private void deleteItemDescriptionTypes(List<ItemDescriptionType> itemDescriptionTypes, boolean checkDefault, BasePK deletedBy) {
        itemDescriptionTypes.forEach((itemDescriptionType) -> deleteItemDescriptionType(itemDescriptionType, checkDefault, deletedBy));
    }

    public void deleteItemDescriptionTypes(List<ItemDescriptionType> itemDescriptionTypes, BasePK deletedBy) {
        deleteItemDescriptionTypes(itemDescriptionTypes, true, deletedBy);
    }

    private void deleteItemDescriptionTypesByParentItemDescriptionType(ItemDescriptionType parentItemDescriptionType, BasePK deletedBy) {
        deleteItemDescriptionTypes(getItemDescriptionTypesByParentItemDescriptionTypeForUpdate(parentItemDescriptionType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDescriptionTypeDescriptionFactory itemDescriptionTypeDescriptionFactory;

    @Inject
    ItemDescriptionTypeDescriptionTransferCache itemDescriptionTypeDescriptionTransferCache;

    public ItemDescriptionTypeDescription createItemDescriptionTypeDescription(ItemDescriptionType itemDescriptionType, Language language, String description, BasePK createdBy) {
        var itemDescriptionTypeDescription = itemDescriptionTypeDescriptionFactory.create(itemDescriptionType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDescriptionTypeDescription;
    }

    private ItemDescriptionTypeDescription getItemDescriptionTypeDescription(ItemDescriptionType itemDescriptionType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypeDescriptions.fields())
                .from(ItemDescriptionTypeDescriptions)
                .where(ItemDescriptionTypeDescriptions.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                        ItemDescriptionTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemDescriptionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionTypeDescription getItemDescriptionTypeDescription(ItemDescriptionType itemDescriptionType, Language language) {
        return getItemDescriptionTypeDescription(itemDescriptionType, language, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeDescription getItemDescriptionTypeDescriptionForUpdate(ItemDescriptionType itemDescriptionType, Language language) {
        return getItemDescriptionTypeDescription(itemDescriptionType, language, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeDescriptionValue getItemDescriptionTypeDescriptionValue(ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        return itemDescriptionTypeDescription == null? null: itemDescriptionTypeDescription.getItemDescriptionTypeDescriptionValue().clone();
    }

    public ItemDescriptionTypeDescriptionValue getItemDescriptionTypeDescriptionValueForUpdate(ItemDescriptionType itemDescriptionType, Language language) {
        return getItemDescriptionTypeDescriptionValue(getItemDescriptionTypeDescriptionForUpdate(itemDescriptionType, language));
    }

    private List<ItemDescriptionTypeDescription> getItemDescriptionTypeDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptionTypeDescriptions.fields())
                    .from(ItemDescriptionTypeDescriptions)
                    .join(Languages).onKey(ITEM_DESCRIPTION_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemDescriptionTypeDescriptions.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                            ItemDescriptionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemDescriptionTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemDescriptionTypeDescriptions.fields())
                    .from(ItemDescriptionTypeDescriptions)
                    .where(ItemDescriptionTypeDescriptions.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                            ItemDescriptionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemDescriptionTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionTypeDescription> getItemDescriptionTypeDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionTypeDescriptionsByItemDescriptionType(itemDescriptionType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeDescription> getItemDescriptionTypeDescriptionsByItemDescriptionTypeForUpdate(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionTypeDescriptionsByItemDescriptionType(itemDescriptionType, EntityPermission.READ_WRITE);
    }

    public String getBestItemDescriptionTypeDescription(ItemDescriptionType itemDescriptionType, Language language) {
        String description;
        var itemDescriptionTypeDescription = getItemDescriptionTypeDescription(itemDescriptionType, language);

        if(itemDescriptionTypeDescription == null && !language.getIsDefault()) {
            itemDescriptionTypeDescription = getItemDescriptionTypeDescription(itemDescriptionType, partyControl.getDefaultLanguage());
        }

        if(itemDescriptionTypeDescription == null) {
            description = itemDescriptionType.getLastDetail().getItemDescriptionTypeName();
        } else {
            description = itemDescriptionTypeDescription.getDescription();
        }

        return description;
    }

    public ItemDescriptionTypeDescriptionTransfer getItemDescriptionTypeDescriptionTransfer(UserVisit userVisit, ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        return itemDescriptionTypeDescriptionTransferCache.getTransfer(userVisit, itemDescriptionTypeDescription);
    }

    public List<ItemDescriptionTypeDescriptionTransfer> getItemDescriptionTypeDescriptionTransfersByItemDescriptionType(UserVisit userVisit, ItemDescriptionType itemDescriptionType) {
        var itemDescriptionTypeDescriptions = getItemDescriptionTypeDescriptionsByItemDescriptionType(itemDescriptionType);
        List<ItemDescriptionTypeDescriptionTransfer> itemDescriptionTypeDescriptionTransfers = new ArrayList<>(itemDescriptionTypeDescriptions.size());

        itemDescriptionTypeDescriptions.forEach((itemDescriptionTypeDescription) ->
                itemDescriptionTypeDescriptionTransfers.add(itemDescriptionTypeDescriptionTransferCache.getTransfer(userVisit, itemDescriptionTypeDescription))
        );

        return itemDescriptionTypeDescriptionTransfers;
    }

    public void updateItemDescriptionTypeDescriptionFromValue(ItemDescriptionTypeDescriptionValue itemDescriptionTypeDescriptionValue, BasePK updatedBy) {
        if(itemDescriptionTypeDescriptionValue.hasBeenModified()) {
            var itemDescriptionTypeDescription = itemDescriptionTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDescriptionTypeDescriptionValue.getPrimaryKey());

            itemDescriptionTypeDescription.setThruTime(session.getStartTime());
            itemDescriptionTypeDescription.store();

            var itemDescriptionType = itemDescriptionTypeDescription.getItemDescriptionType();
            var language = itemDescriptionTypeDescription.getLanguage();
            var description = itemDescriptionTypeDescriptionValue.getDescription();

            itemDescriptionTypeDescription = itemDescriptionTypeDescriptionFactory.create(itemDescriptionType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemDescriptionTypeDescription(ItemDescriptionTypeDescription itemDescriptionTypeDescription, BasePK deletedBy) {
        itemDescriptionTypeDescription.setThruTime(session.getStartTime());

        sendEvent(itemDescriptionTypeDescription.getItemDescriptionTypePK(), EventTypes.MODIFY, itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteItemDescriptionTypeDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType, BasePK deletedBy) {
        var itemDescriptionTypeDescriptions = getItemDescriptionTypeDescriptionsByItemDescriptionTypeForUpdate(itemDescriptionType);

        itemDescriptionTypeDescriptions.forEach((itemDescriptionTypeDescription) -> 
                deleteItemDescriptionTypeDescription(itemDescriptionTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Image Description Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemImageDescriptionTypeFactory itemImageDescriptionTypeFactory;

    public ItemImageDescriptionType createItemImageDescriptionType(ItemDescriptionType itemDescriptionType, Integer minimumHeight, Integer minimumWidth,
            Integer maximumHeight, Integer maximumWidth, Integer preferredHeight, Integer preferredWidth, MimeType preferredMimeType, Integer quality,
            Boolean scaleFromParent, BasePK createdBy) {
        var itemImageDescriptionType = itemImageDescriptionTypeFactory.create(itemDescriptionType, minimumHeight,
                minimumWidth, maximumHeight, maximumWidth, preferredHeight, preferredWidth, preferredMimeType, quality, scaleFromParent,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemImageDescriptionType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemImageDescriptionType;
    }

    private ItemImageDescriptionType getItemImageDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemImageDescriptionTypes.fields())
                .from(ItemImageDescriptionTypes)
                .where(ItemImageDescriptionTypes.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                        ItemImageDescriptionTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemImageDescriptionTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemImageDescriptionType getItemImageDescriptionType(ItemDescriptionType itemDescriptionType) {
        return getItemImageDescriptionType(itemDescriptionType, EntityPermission.READ_ONLY);
    }

    public ItemImageDescriptionType getItemImageDescriptionTypeForUpdate(ItemDescriptionType itemDescriptionType) {
        return getItemImageDescriptionType(itemDescriptionType, EntityPermission.READ_WRITE);
    }

    public ItemImageDescriptionTypeValue getItemImageDescriptionTypeValue(ItemImageDescriptionType itemImageDescriptionType) {
        return itemImageDescriptionType == null? null: itemImageDescriptionType.getItemImageDescriptionTypeValue().clone();
    }

    public ItemImageDescriptionTypeValue getItemImageDescriptionTypeValueForUpdate(ItemDescriptionType itemDescriptionType) {
        return getItemImageDescriptionTypeValue(getItemImageDescriptionTypeForUpdate(itemDescriptionType));
    }

    /** Use the version located in ItemDescriptionLogic instead. */
    public void updateItemImageDescriptionTypeFromValue(ItemImageDescriptionTypeValue itemImageDescriptionTypeValue, BasePK updatedBy) {
        if(itemImageDescriptionTypeValue.hasBeenModified()) {
            var itemImageDescriptionType = itemImageDescriptionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageDescriptionTypeValue.getPrimaryKey());

            itemImageDescriptionType.setThruTime(session.getStartTime());
            itemImageDescriptionType.store();

            var itemDescriptionTypePK = itemImageDescriptionType.getItemDescriptionTypePK(); // Not updated
            var minimumHeight = itemImageDescriptionTypeValue.getMinimumHeight();
            var minimumWidth = itemImageDescriptionTypeValue.getMinimumWidth();
            var maximumHeight = itemImageDescriptionTypeValue.getMaximumHeight();
            var maximumWidth = itemImageDescriptionTypeValue.getMaximumWidth();
            var preferredHeight = itemImageDescriptionTypeValue.getPreferredHeight();
            var preferredWidth = itemImageDescriptionTypeValue.getPreferredWidth();
            var preferredMimeTypePK = itemImageDescriptionTypeValue.getPreferredMimeTypePK();
            var quality = itemImageDescriptionTypeValue.getQuality();
            var scaleFromParent = itemImageDescriptionTypeValue.getScaleFromParent();

            itemImageDescriptionType = itemImageDescriptionTypeFactory.create(itemDescriptionTypePK, minimumHeight, minimumWidth, maximumHeight,
                    maximumWidth, preferredHeight, preferredWidth, preferredMimeTypePK, quality, scaleFromParent, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(itemImageDescriptionType.getItemDescriptionTypePK(), EventTypes.MODIFY, itemImageDescriptionType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemImageDescriptionType(ItemImageDescriptionType itemImageDescriptionType, BasePK deletedBy) {
        itemImageDescriptionType.setThruTime(session.getStartTime());

        sendEvent(itemImageDescriptionType.getItemDescriptionTypePK(), EventTypes.MODIFY, itemImageDescriptionType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteItemImageDescriptionTypeByItemDescriptionType(ItemDescriptionType itemDescriptionType, BasePK deletedBy) {
        var itemImageDescriptionType = getItemImageDescriptionTypeForUpdate(itemDescriptionType);

        if(itemImageDescriptionType != null) {
            deleteItemImageDescriptionType(itemImageDescriptionType, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDescriptionTypeUseTypeDetailFactory itemDescriptionTypeUseTypeDetailFactory;

    @Inject
    protected ItemDescriptionTypeUseTypeFactory itemDescriptionTypeUseTypeFactory;

    @Inject
    ItemDescriptionTypeUseTypeTransferCache itemDescriptionTypeUseTypeTransferCache;

    public ItemDescriptionTypeUseType createItemDescriptionTypeUseType(String itemDescriptionTypeUseTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultItemDescriptionTypeUseType = getDefaultItemDescriptionTypeUseType();
        var defaultFound = defaultItemDescriptionTypeUseType != null;

        if(defaultFound && isDefault) {
            var defaultItemDescriptionTypeUseTypeDetailValue = getDefaultItemDescriptionTypeUseTypeDetailValueForUpdate();

            defaultItemDescriptionTypeUseTypeDetailValue.setIsDefault(false);
            updateItemDescriptionTypeUseTypeFromValue(defaultItemDescriptionTypeUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemDescriptionTypeUseType = itemDescriptionTypeUseTypeFactory.create();
        var itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseTypeDetailFactory.create(itemDescriptionTypeUseType,
                itemDescriptionTypeUseTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        itemDescriptionTypeUseType = itemDescriptionTypeUseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemDescriptionTypeUseType.getPrimaryKey());
        itemDescriptionTypeUseType.setActiveDetail(itemDescriptionTypeUseTypeDetail);
        itemDescriptionTypeUseType.setLastDetail(itemDescriptionTypeUseTypeDetail);
        itemDescriptionTypeUseType.store();

        sendEvent(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemDescriptionTypeUseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemDescriptionTypeUseType */
    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new ItemDescriptionTypeUseTypePK(entityInstance.getEntityUniqueId());

        return itemDescriptionTypeUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemDescriptionTypeUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemDescriptionTypeUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countItemDescriptionTypeUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemDescriptionTypeUseTypes)
                .join(ItemDescriptionTypeUseTypeDetails).onKey(ITEM_DESCRIPTION_TYPE_USE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByName(String itemDescriptionTypeUseTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypeUseTypes.fields())
                .from(ItemDescriptionTypeUseTypes)
                .join(ItemDescriptionTypeUseTypeDetails).onKey(ITEM_DESCRIPTION_TYPE_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeUseTypeDetails.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME.eq(itemDescriptionTypeUseTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByName(String itemDescriptionTypeUseTypeName) {
        return getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByNameForUpdate(String itemDescriptionTypeUseTypeName) {
        return getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeUseTypeDetailValue getItemDescriptionTypeUseTypeDetailValueForUpdate(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return itemDescriptionTypeUseType == null? null: itemDescriptionTypeUseType.getLastDetailForUpdate().getItemDescriptionTypeUseTypeDetailValue().clone();
    }

    public ItemDescriptionTypeUseTypeDetailValue getItemDescriptionTypeUseTypeDetailValueByNameForUpdate(String itemDescriptionTypeUseTypeName) {
        return getItemDescriptionTypeUseTypeDetailValueForUpdate(getItemDescriptionTypeUseTypeByNameForUpdate(itemDescriptionTypeUseTypeName));
    }

    public ItemDescriptionTypeUseType getDefaultItemDescriptionTypeUseType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypeUseTypes.fields())
                .from(ItemDescriptionTypeUseTypes)
                .join(ItemDescriptionTypeUseTypeDetails).onKey(ITEM_DESCRIPTION_TYPE_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionTypeUseTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionTypeUseType getDefaultItemDescriptionTypeUseType() {
        return getDefaultItemDescriptionTypeUseType(EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUseType getDefaultItemDescriptionTypeUseTypeForUpdate() {
        return getDefaultItemDescriptionTypeUseType(EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeUseTypeDetailValue getDefaultItemDescriptionTypeUseTypeDetailValueForUpdate() {
        return getDefaultItemDescriptionTypeUseTypeForUpdate().getLastDetailForUpdate().getItemDescriptionTypeUseTypeDetailValue().clone();
    }

    private List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypeUseTypes.fields())
                .from(ItemDescriptionTypeUseTypes)
                .join(ItemDescriptionTypeUseTypeDetails).onKey(ITEM_DESCRIPTION_TYPE_USE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDescriptionTypeUseTypeDetails.SORT_ORDER, ItemDescriptionTypeUseTypeDetails.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME),
                    ItemDescriptionTypeUseTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeUseTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypes() {
        return getItemDescriptionTypeUseTypes(EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypesForUpdate() {
        return getItemDescriptionTypeUseTypes(EntityPermission.READ_WRITE);
    }







    public ItemDescriptionTypeUseTypeTransfer getItemDescriptionTypeUseTypeTransfer(UserVisit userVisit, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return itemDescriptionTypeUseTypeTransferCache.getTransfer(userVisit, itemDescriptionTypeUseType);
    }

    public List<ItemDescriptionTypeUseTypeTransfer> getItemDescriptionTypeUseTypeTransfers(UserVisit userVisit, Collection<ItemDescriptionTypeUseType> itemDescriptionTypeUseTypes) {
        List<ItemDescriptionTypeUseTypeTransfer> itemDescriptionTypeUseTypeTransfers = new ArrayList<>(itemDescriptionTypeUseTypes.size());

        itemDescriptionTypeUseTypes.forEach((itemDescriptionTypeUseType) ->
                itemDescriptionTypeUseTypeTransfers.add(itemDescriptionTypeUseTypeTransferCache.getTransfer(userVisit, itemDescriptionTypeUseType))
        );

        return itemDescriptionTypeUseTypeTransfers;
    }

    public List<ItemDescriptionTypeUseTypeTransfer> getItemDescriptionTypeUseTypeTransfers(UserVisit userVisit) {
        return getItemDescriptionTypeUseTypeTransfers(userVisit, getItemDescriptionTypeUseTypes());
    }



    public ItemDescriptionTypeUseTypeChoicesBean getItemDescriptionTypeUseTypeChoices(String defaultItemDescriptionTypeUseTypeChoice, Language language, boolean allowNullChoice) {
        var itemDescriptionTypeUseTypes = getItemDescriptionTypeUseTypes();
        var size = itemDescriptionTypeUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemDescriptionTypeUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemDescriptionTypeUseType : itemDescriptionTypeUseTypes) {
            var itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getLastDetail();

            var label = getBestItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language);
            var value = itemDescriptionTypeUseTypeDetail.getItemDescriptionTypeUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultItemDescriptionTypeUseTypeChoice != null && defaultItemDescriptionTypeUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemDescriptionTypeUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemDescriptionTypeUseTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateItemDescriptionTypeUseTypeFromValue(ItemDescriptionTypeUseTypeDetailValue itemDescriptionTypeUseTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemDescriptionTypeUseTypeDetailValue.hasBeenModified()) {
            var itemDescriptionTypeUseType = itemDescriptionTypeUseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionTypeUseTypeDetailValue.getItemDescriptionTypeUseTypePK());
            var itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getActiveDetailForUpdate();

            itemDescriptionTypeUseTypeDetail.setThruTime(session.getStartTime());
            itemDescriptionTypeUseTypeDetail.store();

            var itemDescriptionTypeUseTypePK = itemDescriptionTypeUseTypeDetail.getItemDescriptionTypeUseTypePK(); // Not updated
            var itemDescriptionTypeUseTypeName = itemDescriptionTypeUseTypeDetailValue.getItemDescriptionTypeUseTypeName();
            var isDefault = itemDescriptionTypeUseTypeDetailValue.getIsDefault();
            var sortOrder = itemDescriptionTypeUseTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultItemDescriptionTypeUseType = getDefaultItemDescriptionTypeUseType();
                var defaultFound = defaultItemDescriptionTypeUseType != null && !defaultItemDescriptionTypeUseType.equals(itemDescriptionTypeUseType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultItemDescriptionTypeUseTypeDetailValue = getDefaultItemDescriptionTypeUseTypeDetailValueForUpdate();

                    defaultItemDescriptionTypeUseTypeDetailValue.setIsDefault(false);
                    updateItemDescriptionTypeUseTypeFromValue(defaultItemDescriptionTypeUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseTypeDetailFactory.create(itemDescriptionTypeUseTypePK,
                    itemDescriptionTypeUseTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            itemDescriptionTypeUseType.setActiveDetail(itemDescriptionTypeUseTypeDetail);
            itemDescriptionTypeUseType.setLastDetail(itemDescriptionTypeUseTypeDetail);

            sendEvent(itemDescriptionTypeUseTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateItemDescriptionTypeUseTypeFromValue(ItemDescriptionTypeUseTypeDetailValue itemDescriptionTypeUseTypeDetailValue, BasePK updatedBy) {
        updateItemDescriptionTypeUseTypeFromValue(itemDescriptionTypeUseTypeDetailValue, true, updatedBy);
    }

    public void deleteItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType, BasePK deletedBy) {
        deleteItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(itemDescriptionTypeUseType, deletedBy);
        deleteItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType, deletedBy);

        var itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getLastDetailForUpdate();
        itemDescriptionTypeUseTypeDetail.setThruTime(session.getStartTime());
        itemDescriptionTypeUseType.setActiveDetail(null);
        itemDescriptionTypeUseType.store();

        // Check for default, and pick one if necessary
        var defaultItemDescriptionTypeUseType = getDefaultItemDescriptionTypeUseType();
        if(defaultItemDescriptionTypeUseType == null) {
            var itemDescriptionTypeUseTypes = getItemDescriptionTypeUseTypesForUpdate();

            if(!itemDescriptionTypeUseTypes.isEmpty()) {
                var iter = itemDescriptionTypeUseTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemDescriptionTypeUseType = iter.next();
                }
                var itemDescriptionTypeUseTypeDetailValue = Objects.requireNonNull(defaultItemDescriptionTypeUseType).getLastDetailForUpdate().getItemDescriptionTypeUseTypeDetailValue().clone();

                itemDescriptionTypeUseTypeDetailValue.setIsDefault(true);
                updateItemDescriptionTypeUseTypeFromValue(itemDescriptionTypeUseTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDescriptionTypeUseTypeDescriptionFactory itemDescriptionTypeUseTypeDescriptionFactory;

    @Inject
    ItemDescriptionTypeUseTypeDescriptionTransferCache itemDescriptionTypeUseTypeDescriptionTransferCache;

    public ItemDescriptionTypeUseTypeDescription createItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            Language language, String description, BasePK createdBy) {
        var itemDescriptionTypeUseTypeDescription = itemDescriptionTypeUseTypeDescriptionFactory.create(itemDescriptionTypeUseType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDescriptionTypeUseTypeDescription;
    }

    private ItemDescriptionTypeUseTypeDescription getItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypeUseTypeDescriptions.fields())
                .from(ItemDescriptionTypeUseTypeDescriptions)
                .where(ItemDescriptionTypeUseTypeDescriptions.ITEM_DESCRIPTION_TYPE_USE_TYPE.eq(itemDescriptionTypeUseType.getPrimaryKey()),
                        ItemDescriptionTypeUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemDescriptionTypeUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeUseTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionTypeUseTypeDescription getItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseType itemDescriptionTypeUseType, Language language) {
        return getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUseTypeDescription getItemDescriptionTypeUseTypeDescriptionForUpdate(ItemDescriptionTypeUseType itemDescriptionTypeUseType, Language language) {
        return getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeUseTypeDescriptionValue getItemDescriptionTypeUseTypeDescriptionValue(ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        return itemDescriptionTypeUseTypeDescription == null? null: itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseTypeDescriptionValue().clone();
    }

    public ItemDescriptionTypeUseTypeDescriptionValue getItemDescriptionTypeUseTypeDescriptionValueForUpdate(ItemDescriptionTypeUseType itemDescriptionTypeUseType, Language language) {
        return getItemDescriptionTypeUseTypeDescriptionValue(getItemDescriptionTypeUseTypeDescriptionForUpdate(itemDescriptionTypeUseType, language));
    }

    private List<ItemDescriptionTypeUseTypeDescription> getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptionTypeUseTypeDescriptions.fields())
                    .from(ItemDescriptionTypeUseTypeDescriptions)
                    .join(Languages).onKey(ITEM_DESCRIPTION_TYPE_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemDescriptionTypeUseTypeDescriptions.ITEM_DESCRIPTION_TYPE_USE_TYPE.eq(itemDescriptionTypeUseType.getPrimaryKey()),
                            ItemDescriptionTypeUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemDescriptionTypeUseTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemDescriptionTypeUseTypeDescriptions.fields())
                    .from(ItemDescriptionTypeUseTypeDescriptions)
                    .where(ItemDescriptionTypeUseTypeDescriptions.ITEM_DESCRIPTION_TYPE_USE_TYPE.eq(itemDescriptionTypeUseType.getPrimaryKey()),
                            ItemDescriptionTypeUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemDescriptionTypeUseTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionTypeUseTypeDescription> getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(itemDescriptionTypeUseType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeUseTypeDescription> getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseTypeForUpdate(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(itemDescriptionTypeUseType, EntityPermission.READ_WRITE);
    }

    public String getBestItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseType itemDescriptionTypeUseType, Language language) {
        String description;
        var itemDescriptionTypeUseTypeDescription = getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language);

        if(itemDescriptionTypeUseTypeDescription == null && !language.getIsDefault()) {
            itemDescriptionTypeUseTypeDescription = getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, partyControl.getDefaultLanguage());
        }

        if(itemDescriptionTypeUseTypeDescription == null) {
            description = itemDescriptionTypeUseType.getLastDetail().getItemDescriptionTypeUseTypeName();
        } else {
            description = itemDescriptionTypeUseTypeDescription.getDescription();
        }

        return description;
    }

    public ItemDescriptionTypeUseTypeDescriptionTransfer getItemDescriptionTypeUseTypeDescriptionTransfer(UserVisit userVisit, ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        return itemDescriptionTypeUseTypeDescriptionTransferCache.getTransfer(userVisit, itemDescriptionTypeUseTypeDescription);
    }

    public List<ItemDescriptionTypeUseTypeDescriptionTransfer> getItemDescriptionTypeUseTypeDescriptionTransfersByItemDescriptionTypeUseType(UserVisit userVisit, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        var itemDescriptionTypeUseTypeDescriptions = getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(itemDescriptionTypeUseType);
        List<ItemDescriptionTypeUseTypeDescriptionTransfer> itemDescriptionTypeUseTypeDescriptionTransfers = new ArrayList<>(itemDescriptionTypeUseTypeDescriptions.size());

        itemDescriptionTypeUseTypeDescriptions.forEach((itemDescriptionTypeUseTypeDescription) ->
                itemDescriptionTypeUseTypeDescriptionTransfers.add(itemDescriptionTypeUseTypeDescriptionTransferCache.getTransfer(userVisit, itemDescriptionTypeUseTypeDescription))
        );

        return itemDescriptionTypeUseTypeDescriptionTransfers;
    }

    public void updateItemDescriptionTypeUseTypeDescriptionFromValue(ItemDescriptionTypeUseTypeDescriptionValue itemDescriptionTypeUseTypeDescriptionValue, BasePK updatedBy) {
        if(itemDescriptionTypeUseTypeDescriptionValue.hasBeenModified()) {
            var itemDescriptionTypeUseTypeDescription = itemDescriptionTypeUseTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDescriptionTypeUseTypeDescriptionValue.getPrimaryKey());

            itemDescriptionTypeUseTypeDescription.setThruTime(session.getStartTime());
            itemDescriptionTypeUseTypeDescription.store();

            var itemDescriptionTypeUseType = itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseType();
            var language = itemDescriptionTypeUseTypeDescription.getLanguage();
            var description = itemDescriptionTypeUseTypeDescriptionValue.getDescription();

            itemDescriptionTypeUseTypeDescription = itemDescriptionTypeUseTypeDescriptionFactory.create(itemDescriptionTypeUseType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription, BasePK deletedBy) {
        itemDescriptionTypeUseTypeDescription.setThruTime(session.getStartTime());

        sendEvent(itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseTypePK(), EventTypes.MODIFY, itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType, BasePK deletedBy) {
        var itemDescriptionTypeUseTypeDescriptions = getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseTypeForUpdate(itemDescriptionTypeUseType);

        itemDescriptionTypeUseTypeDescriptions.forEach((itemDescriptionTypeUseTypeDescription) -> 
                deleteItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDescriptionTypeUseFactory itemDescriptionTypeUseFactory;

    @Inject
    ItemDescriptionTypeUseTransferCache itemDescriptionTypeUseTransferCache;

    public ItemDescriptionTypeUse createItemDescriptionTypeUse(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            BasePK createdBy) {
        var itemDescriptionTypeUse = itemDescriptionTypeUseFactory.create(itemDescriptionType, itemDescriptionTypeUseType,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeUse.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDescriptionTypeUse;
    }

    public long countItemDescriptionTypeUsesByItemDescriptionType(ItemDescriptionType itemDescriptionType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemDescriptionTypeUses)
                .where(ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                        ItemDescriptionTypeUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemDescriptionTypeUsesByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemDescriptionTypeUses)
                .where(ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE_USE_TYPE.eq(itemDescriptionTypeUseType.getPrimaryKey()),
                        ItemDescriptionTypeUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemDescriptionTypeUse getItemDescriptionTypeUse(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptionTypeUses.fields())
                .from(ItemDescriptionTypeUses)
                .where(ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                        ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE_USE_TYPE.eq(itemDescriptionTypeUseType.getPrimaryKey()),
                        ItemDescriptionTypeUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionTypeUseFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemDescriptionTypeUse getItemDescriptionTypeUse(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUse(itemDescriptionType, itemDescriptionTypeUseType, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUse getItemDescriptionTypeUseForUpdate(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUse(itemDescriptionType, itemDescriptionTypeUseType, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeUseValue getItemDescriptionTypeUseValueForUpdate(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUseForUpdate(itemDescriptionType, itemDescriptionTypeUseType).getItemDescriptionTypeUseValue().clone();
    }

    private List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptionTypeUses.fields())
                    .from(ItemDescriptionTypeUses)
                    .join(ItemDescriptionTypes).onKey(ITEM_DESCRIPTION_TYPE_USES_ITEM_DESCRIPTION_TYPE_FK)
                    .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_LAST_DETAIL_FK)
                    .where(ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                            ItemDescriptionTypeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDescriptionTypeDetails.SORT_ORDER, ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME),
                    ItemDescriptionTypeUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemDescriptionTypeUses.fields())
                    .from(ItemDescriptionTypeUses)
                    .where(ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                            ItemDescriptionTypeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemDescriptionTypeUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionType(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionTypeForUpdate(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType, EntityPermission.READ_WRITE);
    }

    private List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptionTypeUses.fields())
                    .from(ItemDescriptionTypeUses)
                    .join(ItemDescriptionTypeUseTypes).onKey(ITEM_DESCRIPTION_TYPE_USES_ITEM_DESCRIPTION_TYPE_USE_TYPE_FK)
                    .join(ItemDescriptionTypeUseTypeDetails).onKey(ITEM_DESCRIPTION_TYPE_USE_TYPES_LAST_DETAIL_FK)
                    .where(ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE_USE_TYPE.eq(itemDescriptionTypeUseType.getPrimaryKey()),
                            ItemDescriptionTypeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDescriptionTypeUseTypeDetails.SORT_ORDER, ItemDescriptionTypeUseTypeDetails.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME),
                    ItemDescriptionTypeUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemDescriptionTypeUses.fields())
                    .from(ItemDescriptionTypeUses)
                    .where(ItemDescriptionTypeUses.ITEM_DESCRIPTION_TYPE_USE_TYPE.eq(itemDescriptionTypeUseType.getPrimaryKey()),
                            ItemDescriptionTypeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemDescriptionTypeUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionTypeUseTypeForUpdate(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeUseTransfer getItemDescriptionTypeUseTransfer(UserVisit userVisit, ItemDescriptionTypeUse itemDescriptionTypeUse) {
        return itemDescriptionTypeUseTransferCache.getTransfer(userVisit, itemDescriptionTypeUse);
    }

    public List<ItemDescriptionTypeUseTransfer> getItemDescriptionTypeUseTransfers(final UserVisit userVisit, final Collection<ItemDescriptionTypeUse> itemDescriptionTypeUses) {
        List<ItemDescriptionTypeUseTransfer> itemDescriptionTypeUseTransfers = new ArrayList<>(itemDescriptionTypeUses.size());

        itemDescriptionTypeUses.forEach((itemDescriptionTypeUse) ->
                itemDescriptionTypeUseTransfers.add(itemDescriptionTypeUseTransferCache.getTransfer(userVisit, itemDescriptionTypeUse))
        );

        return itemDescriptionTypeUseTransfers;
    }

    public List<ItemDescriptionTypeUseTransfer> getItemDescriptionTypeUseTransfersByItemDescriptionType(UserVisit userVisit, ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionTypeUseTransfers(userVisit, getItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType));
    }

    public List<ItemDescriptionTypeUseTransfer> getItemDescriptionTypeUseTransfersByItemDescriptionTypeUseType(UserVisit userVisit, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return getItemDescriptionTypeUseTransfers(userVisit, getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType));
    }

    public void deleteItemDescriptionTypeUse(ItemDescriptionTypeUse itemDescriptionTypeUse, BasePK deletedBy) {
        itemDescriptionTypeUse.setThruTime(session.getStartTime());
        itemDescriptionTypeUse.store();

        sendEvent(itemDescriptionTypeUse.getItemDescriptionTypePK(), EventTypes.MODIFY, itemDescriptionTypeUse.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteItemDescriptionTypeUses(List<ItemDescriptionTypeUse> itemDescriptionTypeUses, BasePK deletedBy) {
        itemDescriptionTypeUses.forEach((itemDescriptionTypeUse) -> 
                deleteItemDescriptionTypeUse(itemDescriptionTypeUse, deletedBy)
        );
    }

    public void deleteItemDescriptionTypeUsesByItemDescriptionType(ItemDescriptionType itemDescriptionType, BasePK deletedBy) {
        deleteItemDescriptionTypeUses(getItemDescriptionTypeUsesByItemDescriptionTypeForUpdate(itemDescriptionType), deletedBy);
    }

    public void deleteItemDescriptionTypeUsesByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType, BasePK deletedBy) {
        deleteItemDescriptionTypeUses(getItemDescriptionTypeUsesByItemDescriptionTypeUseTypeForUpdate(itemDescriptionTypeUseType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemImageTypeDetailFactory itemImageTypeDetailFactory;

    @Inject
    protected ItemImageTypeFactory itemImageTypeFactory;

    @Inject
    ItemImageTypeTransferCache itemImageTypeTransferCache;

    public ItemImageType createItemImageType(String itemImageTypeName, MimeType preferredMimeType, Integer quality, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultItemImageType = getDefaultItemImageType();
        var defaultFound = defaultItemImageType != null;

        if(defaultFound && isDefault) {
            var defaultItemImageTypeDetailValue = getDefaultItemImageTypeDetailValueForUpdate();

            defaultItemImageTypeDetailValue.setIsDefault(false);
            updateItemImageTypeFromValue(defaultItemImageTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemImageType = itemImageTypeFactory.create();
        var itemImageTypeDetail = itemImageTypeDetailFactory.create(itemImageType, itemImageTypeName, preferredMimeType, quality,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        itemImageType = itemImageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemImageType.getPrimaryKey());
        itemImageType.setActiveDetail(itemImageTypeDetail);
        itemImageType.setLastDetail(itemImageTypeDetail);
        itemImageType.store();

        sendEvent(itemImageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemImageType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemImageType */
    public ItemImageType getItemImageTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new ItemImageTypePK(entityInstance.getEntityUniqueId());

        return itemImageTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemImageType getItemImageTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemImageTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemImageType getItemImageTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemImageTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemImageTypeDetailValue getItemImageTypeDetailValueForUpdate(ItemImageType itemImageType) {
        return itemImageType.getLastDetailForUpdate().getItemImageTypeDetailValue().clone();
    }

    public long countItemImageTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemImageTypes)
                .join(ItemImageTypeDetails).onKey(ITEM_IMAGE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ItemImageType getItemImageTypeByName(String itemImageTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemImageTypes.fields())
                .from(ItemImageTypes)
                .join(ItemImageTypeDetails).onKey(ITEM_IMAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemImageTypeDetails.ITEM_IMAGE_TYPE_NAME.eq(itemImageTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemImageTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemImageType getItemImageTypeByName(String itemImageTypeName) {
        return getItemImageTypeByName(itemImageTypeName, EntityPermission.READ_ONLY);
    }

    public ItemImageType getItemImageTypeByNameForUpdate(String itemImageTypeName) {
        return getItemImageTypeByName(itemImageTypeName, EntityPermission.READ_WRITE);
    }

    public ItemImageTypeDetailValue getItemImageTypeDetailValueByNameForUpdate(String itemImageTypeName) {
        return getItemImageTypeDetailValueForUpdate(getItemImageTypeByNameForUpdate(itemImageTypeName));
    }

    public ItemImageType getDefaultItemImageType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemImageTypes.fields())
                .from(ItemImageTypes)
                .join(ItemImageTypeDetails).onKey(ITEM_IMAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemImageTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemImageTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemImageType getDefaultItemImageType() {
        return getDefaultItemImageType(EntityPermission.READ_ONLY);
    }

    public ItemImageType getDefaultItemImageTypeForUpdate() {
        return getDefaultItemImageType(EntityPermission.READ_WRITE);
    }

    public ItemImageTypeDetailValue getDefaultItemImageTypeDetailValueForUpdate() {
        return getDefaultItemImageTypeForUpdate().getLastDetailForUpdate().getItemImageTypeDetailValue().clone();
    }

    private List<ItemImageType> getItemImageTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemImageTypes.fields())
                .from(ItemImageTypes)
                .join(ItemImageTypeDetails).onKey(ITEM_IMAGE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemImageTypeDetails.SORT_ORDER, ItemImageTypeDetails.ITEM_IMAGE_TYPE_NAME),
                    ItemImageTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemImageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemImageType> getItemImageTypes() {
        return getItemImageTypes(EntityPermission.READ_ONLY);
    }

    public List<ItemImageType> getItemImageTypesForUpdate() {
        return getItemImageTypes(EntityPermission.READ_WRITE);
    }

    public ItemImageTypeTransfer getItemImageTypeTransfer(UserVisit userVisit, ItemImageType itemImageType) {
        return itemImageTypeTransferCache.getTransfer(userVisit, itemImageType);
    }

    public List<ItemImageTypeTransfer> getItemImageTypeTransfers(UserVisit userVisit, Collection<ItemImageType> itemImageTypes) {
        List<ItemImageTypeTransfer> itemImageTypeTransfers = new ArrayList<>(itemImageTypes.size());

        itemImageTypes.forEach((itemImageType) ->
                itemImageTypeTransfers.add(itemImageTypeTransferCache.getTransfer(userVisit, itemImageType))
        );

        return itemImageTypeTransfers;
    }

    public List<ItemImageTypeTransfer> getItemImageTypeTransfers(UserVisit userVisit) {
        return getItemImageTypeTransfers(userVisit, getItemImageTypes());
    }

    public ItemImageTypeChoicesBean getItemImageTypeChoices(String defaultItemImageTypeChoice, Language language, boolean allowNullChoice) {
        var itemImageTypes = getItemImageTypes();
        var size = itemImageTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemImageTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemImageType : itemImageTypes) {
            var itemImageTypeDetail = itemImageType.getLastDetail();

            var label = getBestItemImageTypeDescription(itemImageType, language);
            var value = itemImageTypeDetail.getItemImageTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultItemImageTypeChoice != null && defaultItemImageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemImageTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemImageTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateItemImageTypeFromValue(ItemImageTypeDetailValue itemImageTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemImageTypeDetailValue.hasBeenModified()) {
            var itemImageType = itemImageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageTypeDetailValue.getItemImageTypePK());
            var itemImageTypeDetail = itemImageType.getActiveDetailForUpdate();

            itemImageTypeDetail.setThruTime(session.getStartTime());
            itemImageTypeDetail.store();

            var itemImageTypePK = itemImageTypeDetail.getItemImageTypePK(); // Not updated
            var itemImageTypeName = itemImageTypeDetailValue.getItemImageTypeName();
            var preferredMimeTypePK = itemImageTypeDetailValue.getPreferredMimeTypePK();
            var quality = itemImageTypeDetailValue.getQuality();
            var isDefault = itemImageTypeDetailValue.getIsDefault();
            var sortOrder = itemImageTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultItemImageType = getDefaultItemImageType();
                var defaultFound = defaultItemImageType != null && !defaultItemImageType.equals(itemImageType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultItemImageTypeDetailValue = getDefaultItemImageTypeDetailValueForUpdate();

                    defaultItemImageTypeDetailValue.setIsDefault(false);
                    updateItemImageTypeFromValue(defaultItemImageTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            itemImageTypeDetail = itemImageTypeDetailFactory.create(itemImageTypePK, itemImageTypeName, preferredMimeTypePK, quality, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            itemImageType.setActiveDetail(itemImageTypeDetail);
            itemImageType.setLastDetail(itemImageTypeDetail);

            sendEvent(itemImageTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    /** Use the version located in ItemDescriptionLogic instead. */
    public void updateItemImageTypeFromValue(ItemImageTypeDetailValue itemImageTypeDetailValue, BasePK updatedBy) {
        updateItemImageTypeFromValue(itemImageTypeDetailValue, true, updatedBy);
    }

    public void deleteItemImageType(ItemImageType itemImageType, BasePK deletedBy) {
        deleteItemImageTypeDescriptionsByItemImageType(itemImageType, deletedBy);
        deleteItemDescriptionsByItemImageType(itemImageType, deletedBy);

        var itemImageTypeDetail = itemImageType.getLastDetailForUpdate();
        itemImageTypeDetail.setThruTime(session.getStartTime());
        itemImageType.setActiveDetail(null);
        itemImageType.store();

        // Check for default, and pick one if necessary
        var defaultItemImageType = getDefaultItemImageType();
        if(defaultItemImageType == null) {
            var itemImageTypes = getItemImageTypesForUpdate();

            if(!itemImageTypes.isEmpty()) {
                var iter = itemImageTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemImageType = iter.next();
                }
                var itemImageTypeDetailValue = Objects.requireNonNull(defaultItemImageType).getLastDetailForUpdate().getItemImageTypeDetailValue().clone();

                itemImageTypeDetailValue.setIsDefault(true);
                updateItemImageTypeFromValue(itemImageTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(itemImageType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemImageTypeDescriptionFactory itemImageTypeDescriptionFactory;

    @Inject
    ItemImageTypeDescriptionTransferCache itemImageTypeDescriptionTransferCache;

    public ItemImageTypeDescription createItemImageTypeDescription(ItemImageType itemImageType,
            Language language, String description, BasePK createdBy) {
        var itemImageTypeDescription = itemImageTypeDescriptionFactory.create(itemImageType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemImageType.getPrimaryKey(), EventTypes.MODIFY, itemImageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemImageTypeDescription;
    }

    private ItemImageTypeDescription getItemImageTypeDescription(ItemImageType itemImageType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemImageTypeDescriptions.fields())
                .from(ItemImageTypeDescriptions)
                .where(ItemImageTypeDescriptions.ITEM_IMAGE_TYPE.eq(itemImageType.getPrimaryKey()),
                        ItemImageTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemImageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemImageTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemImageTypeDescription getItemImageTypeDescription(ItemImageType itemImageType, Language language) {
        return getItemImageTypeDescription(itemImageType, language, EntityPermission.READ_ONLY);
    }

    public ItemImageTypeDescription getItemImageTypeDescriptionForUpdate(ItemImageType itemImageType, Language language) {
        return getItemImageTypeDescription(itemImageType, language, EntityPermission.READ_WRITE);
    }

    public ItemImageTypeDescriptionValue getItemImageTypeDescriptionValue(ItemImageTypeDescription itemImageTypeDescription) {
        return itemImageTypeDescription == null? null: itemImageTypeDescription.getItemImageTypeDescriptionValue().clone();
    }

    public ItemImageTypeDescriptionValue getItemImageTypeDescriptionValueForUpdate(ItemImageType itemImageType, Language language) {
        return getItemImageTypeDescriptionValue(getItemImageTypeDescriptionForUpdate(itemImageType, language));
    }

    private List<ItemImageTypeDescription> getItemImageTypeDescriptionsByItemImageType(ItemImageType itemImageType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemImageTypeDescriptions.fields())
                    .from(ItemImageTypeDescriptions)
                    .join(Languages).onKey(ITEM_IMAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemImageTypeDescriptions.ITEM_IMAGE_TYPE.eq(itemImageType.getPrimaryKey()),
                            ItemImageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemImageTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemImageTypeDescriptions.fields())
                    .from(ItemImageTypeDescriptions)
                    .where(ItemImageTypeDescriptions.ITEM_IMAGE_TYPE.eq(itemImageType.getPrimaryKey()),
                            ItemImageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemImageTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemImageTypeDescription> getItemImageTypeDescriptionsByItemImageType(ItemImageType itemImageType) {
        return getItemImageTypeDescriptionsByItemImageType(itemImageType, EntityPermission.READ_ONLY);
    }

    public List<ItemImageTypeDescription> getItemImageTypeDescriptionsByItemImageTypeForUpdate(ItemImageType itemImageType) {
        return getItemImageTypeDescriptionsByItemImageType(itemImageType, EntityPermission.READ_WRITE);
    }

    public String getBestItemImageTypeDescription(ItemImageType itemImageType, Language language) {
        String description;
        var itemImageTypeDescription = getItemImageTypeDescription(itemImageType, language);

        if(itemImageTypeDescription == null && !language.getIsDefault()) {
            itemImageTypeDescription = getItemImageTypeDescription(itemImageType, partyControl.getDefaultLanguage());
        }

        if(itemImageTypeDescription == null) {
            description = itemImageType.getLastDetail().getItemImageTypeName();
        } else {
            description = itemImageTypeDescription.getDescription();
        }

        return description;
    }

    public ItemImageTypeDescriptionTransfer getItemImageTypeDescriptionTransfer(UserVisit userVisit, ItemImageTypeDescription itemImageTypeDescription) {
        return itemImageTypeDescriptionTransferCache.getTransfer(userVisit, itemImageTypeDescription);
    }

    public List<ItemImageTypeDescriptionTransfer> getItemImageTypeDescriptionTransfersByItemImageType(UserVisit userVisit, ItemImageType itemImageType) {
        var itemImageTypeDescriptions = getItemImageTypeDescriptionsByItemImageType(itemImageType);
        List<ItemImageTypeDescriptionTransfer> itemImageTypeDescriptionTransfers = new ArrayList<>(itemImageTypeDescriptions.size());

        itemImageTypeDescriptions.forEach((itemImageTypeDescription) ->
                itemImageTypeDescriptionTransfers.add(itemImageTypeDescriptionTransferCache.getTransfer(userVisit, itemImageTypeDescription))
        );

        return itemImageTypeDescriptionTransfers;
    }

    public void updateItemImageTypeDescriptionFromValue(ItemImageTypeDescriptionValue itemImageTypeDescriptionValue, BasePK updatedBy) {
        if(itemImageTypeDescriptionValue.hasBeenModified()) {
            var itemImageTypeDescription = itemImageTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemImageTypeDescriptionValue.getPrimaryKey());

            itemImageTypeDescription.setThruTime(session.getStartTime());
            itemImageTypeDescription.store();

            var itemImageType = itemImageTypeDescription.getItemImageType();
            var language = itemImageTypeDescription.getLanguage();
            var description = itemImageTypeDescriptionValue.getDescription();

            itemImageTypeDescription = itemImageTypeDescriptionFactory.create(itemImageType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(itemImageType.getPrimaryKey(), EventTypes.MODIFY, itemImageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemImageTypeDescription(ItemImageTypeDescription itemImageTypeDescription, BasePK deletedBy) {
        itemImageTypeDescription.setThruTime(session.getStartTime());

        sendEvent(itemImageTypeDescription.getItemImageTypePK(), EventTypes.MODIFY, itemImageTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteItemImageTypeDescriptionsByItemImageType(ItemImageType itemImageType, BasePK deletedBy) {
        var itemImageTypeDescriptions = getItemImageTypeDescriptionsByItemImageTypeForUpdate(itemImageType);

        itemImageTypeDescriptions.forEach((itemImageTypeDescription) -> 
                deleteItemImageTypeDescription(itemImageTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemDescriptionDetailFactory itemDescriptionDetailFactory;

    @Inject
    protected ItemDescriptionFactory itemDescriptionFactory;

    @Inject
    ItemDescriptionTransferCache itemDescriptionTransferCache;

    public ItemDescription createItemDescription(ItemDescriptionType itemDescriptionType, Item item, Language language,
            MimeType mimeType, BasePK createdBy) {
        var itemDescription = itemDescriptionFactory.create();
        var itemDescriptionDetail = itemDescriptionDetailFactory.create(itemDescription,
                itemDescriptionType, item, language, mimeType, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        itemDescription = itemDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemDescription.getPrimaryKey());
        itemDescription.setActiveDetail(itemDescriptionDetail);
        itemDescription.setLastDetail(itemDescriptionDetail);
        itemDescription.store();
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemDescription;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemDescription */
    public ItemDescription getItemDescriptionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemDescriptionPK(entityInstance.getEntityUniqueId());

        return itemDescriptionFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemDescription getItemDescriptionByEntityInstance(EntityInstance entityInstance) {
        return getItemDescriptionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemDescription getItemDescriptionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemDescriptionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<ItemDescription> getScaledItemDescriptionsByItemDescriptionTypePK(ItemDescriptionTypePK itemDescriptionTypePK, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptions.fields())
                .from(ItemDescriptions)
                .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                .join(ItemImageDescriptions).onKey(ITEM_IMAGE_DESCRIPTIONS_ITEM_DESCRIPTION_FK)
                .where(ItemImageDescriptions.THRU_TIME.eq(Session.MAX_TIME),
                        ItemDescriptionDetails.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionTypePK),
                        ItemImageDescriptions.SCALED_FROM_PARENT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptions.fields())
                    .from(ItemDescriptions)
                    .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                    .join(ItemImageDescriptions).onKey(ITEM_IMAGE_DESCRIPTIONS_ITEM_DESCRIPTION_FK)
                    .join(Items).onKey(ITEM_DESCRIPTION_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(Languages).onKey(ITEM_DESCRIPTION_DETAILS_LANGUAGE_FK)
                    .where(ItemImageDescriptions.THRU_TIME.eq(Session.MAX_TIME),
                            ItemDescriptionDetails.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionTypePK),
                            ItemImageDescriptions.SCALED_FROM_PARENT.eq(true))
                    .orderBy(ItemDetails.ITEM_NAME, Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemDescriptionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemDescriptionTypePK(ItemDescriptionTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemDescriptionTypePK(itemDescriptionTypePK, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemDescriptionTypePKForUpdate(ItemDescriptionTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemDescriptionTypePK(itemDescriptionTypePK, EntityPermission.READ_WRITE);
    }

    private List<ItemDescription> getScaledItemDescriptionsByItemImageTypePK(ItemImageTypePK itemDescriptionTypePK, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptions.fields())
                .from(ItemDescriptions)
                .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                .join(ItemImageDescriptions).onKey(ITEM_IMAGE_DESCRIPTIONS_ITEM_DESCRIPTION_FK)
                .where(ItemImageDescriptions.THRU_TIME.eq(Session.MAX_TIME),
                        ItemImageDescriptions.ITEM_IMAGE_TYPE.eq(itemDescriptionTypePK),
                        ItemImageDescriptions.SCALED_FROM_PARENT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptions.fields())
                    .from(ItemDescriptions)
                    .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                    .join(ItemImageDescriptions).onKey(ITEM_IMAGE_DESCRIPTIONS_ITEM_DESCRIPTION_FK)
                    .join(Items).onKey(ITEM_DESCRIPTION_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(ItemDescriptionTypes).onKey(ITEM_DESCRIPTION_DETAILS_ITEM_DESCRIPTION_TYPE_FK)
                    .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_LAST_DETAIL_FK)
                    .join(Languages).onKey(ITEM_DESCRIPTION_DETAILS_LANGUAGE_FK)
                    .where(ItemImageDescriptions.THRU_TIME.eq(Session.MAX_TIME),
                            ItemImageDescriptions.ITEM_IMAGE_TYPE.eq(itemDescriptionTypePK),
                            ItemImageDescriptions.SCALED_FROM_PARENT.eq(true))
                    .orderBy(ItemDetails.ITEM_NAME, ItemDescriptionTypeDetails.SORT_ORDER, ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME, Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemDescriptionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemImageTypePK(ItemImageTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemImageTypePK(itemDescriptionTypePK, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemImageTypePKForUpdate(ItemImageTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemImageTypePK(itemDescriptionTypePK, EntityPermission.READ_WRITE);
    }

    public List<ItemDescription> getItemDescriptionsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptions.fields())
                    .from(ItemDescriptions)
                    .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                    .join(ItemDescriptionTypes).onKey(ITEM_DESCRIPTION_DETAILS_ITEM_DESCRIPTION_TYPE_FK)
                    .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_LAST_DETAIL_FK)
                    .join(Languages).onKey(ITEM_DESCRIPTION_DETAILS_LANGUAGE_FK)
                    .where(ItemDescriptionDetails.ITEM.eq(item.getPrimaryKey()))
                    .orderBy(ItemDescriptionTypeDetails.SORT_ORDER, ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME, Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemDescriptions.fields())
                    .from(ItemDescriptions)
                    .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                    .where(ItemDescriptionDetails.ITEM.eq(item.getPrimaryKey()))
                    .forUpdate();
        };

        return itemDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescription> getItemDescriptionsByItem(Item item) {
        return getItemDescriptionsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getItemDescriptionsByItemForUpdate(Item item) {
        return getItemDescriptionsByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemDescription> getItemDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemDescriptions.fields())
                    .from(ItemDescriptions)
                    .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(ITEM_DESCRIPTION_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(Languages).onKey(ITEM_DESCRIPTION_DETAILS_LANGUAGE_FK)
                    .where(ItemDescriptionDetails.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()))
                    .orderBy(ItemDetails.ITEM_NAME, Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemDescriptions.fields())
                    .from(ItemDescriptions)
                    .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                    .where(ItemDescriptionDetails.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()))
                    .forUpdate();
        };

        return itemDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemDescription> getItemDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionsByItemDescriptionType(itemDescriptionType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getItemDescriptionsByItemDescriptionTypeForUpdate(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionsByItemDescriptionType(itemDescriptionType, EntityPermission.READ_WRITE);
    }

    public long countItemDescriptionsByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemDescriptions)
                .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionDetails.ITEM.eq(item.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemDescription getItemDescription(ItemDescriptionType itemDescriptionType, Item item, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemDescriptions.fields())
                .from(ItemDescriptions)
                .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_ACTIVE_DETAIL_FK)
                .where(ItemDescriptionDetails.ITEM_DESCRIPTION_TYPE.eq(itemDescriptionType.getPrimaryKey()),
                        ItemDescriptionDetails.ITEM.eq(item.getPrimaryKey()),
                        ItemDescriptionDetails.LANGUAGE.eq(language.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemDescription getItemDescription(ItemDescriptionType itemDescriptionType, Item item, Language language) {
        return getItemDescription(itemDescriptionType, item, language, EntityPermission.READ_ONLY);
    }
    
    public ItemDescription getItemDescriptionForUpdate(ItemDescriptionType itemDescriptionType, Item item, Language language) {
        return getItemDescription(itemDescriptionType, item, language, EntityPermission.READ_WRITE);
    }
    
    public ItemDescriptionDetailValue getItemDescriptionDetailValue(ItemDescription itemDescription) {
        return itemDescription == null? null: itemDescription.getLastDetail().getItemDescriptionDetailValue().clone();
    }
    
    public ItemDescriptionDetailValue getItemDescriptionDetailValueForUpdate(ItemDescription itemDescription) {
        return itemDescription == null? null: itemDescription.getLastDetailForUpdate().getItemDescriptionDetailValue().clone();
    }
    
    public ItemDescriptionDetailValue getItemDescriptionDetailValueForUpdate(ItemDescriptionType itemDescriptionType, Item item,
            Language language) {
        return getItemDescriptionDetailValueForUpdate(getItemDescriptionForUpdate(itemDescriptionType, item, language));
    }
    
    private ItemDescription getBestItemDescriptionWithinLanguage(final ItemDescriptionType itemDescriptionType, final Item item, final Language language) {
        ItemDescription itemDescription;
        var attemptedItemDescriptionType = itemDescriptionType;

        // For the requested ItemDescriptionType, Item, and Language, first try to get the Item Description by those
        // exact qualifications. If no Item Description can be found, then try to get it using the Parent Item
        // Description Type. When those run out, return null.
        do {
            itemDescription = getItemDescription(attemptedItemDescriptionType, item, language);
            
            if(itemDescription == null) {
                attemptedItemDescriptionType = attemptedItemDescriptionType.getLastDetail().getParentItemDescriptionType();
            } else {
                break;
            }
        } while(attemptedItemDescriptionType != null);
        
        return itemDescription;
    }
    
    public ItemDescription getBestItemDescription(final ItemDescriptionType itemDescriptionType, final Item item, final Language language) {
        // Try first with the specified language...
        var itemDescription = getBestItemDescriptionWithinLanguage(itemDescriptionType, item, language);

        // If the specified language wasn't found, and it was not possible to fall back to a parent item description type,
        // then try the default language...
        if(itemDescription == null && !language.getIsDefault()) {
            itemDescription = getBestItemDescriptionWithinLanguage(itemDescriptionType, item, partyControl.getDefaultLanguage());
        }
        
        return itemDescription;
    }
    
    public String getBestItemStringDescription(ItemDescriptionType itemDescriptionType, Item item, Language language) {
        String description = null;
        var itemDescription = getBestItemDescription(itemDescriptionType, item, language);
        
        if(itemDescription != null) {
            var itemStringDescription = getItemStringDescription(itemDescription);
            description = itemStringDescription == null? null: itemStringDescription.getStringDescription();
        }
        
        if(description == null) {
            description = item.getLastDetail().getItemName();
        }
        
        return description;
    }
    
    public ItemDescriptionTransfer getItemDescriptionTransfer(UserVisit userVisit, ItemDescription itemDescription) {
        return itemDescriptionTransferCache.getTransfer(userVisit, itemDescription);
    }

    public List<ItemDescriptionTransfer> getItemDescriptionTransfers(UserVisit userVisit, Collection<ItemDescription> itemDescriptions) {
        List<ItemDescriptionTransfer> itemDescriptionTransfers = new ArrayList<>(itemDescriptions.size());

        itemDescriptions.forEach((itemDescription) ->
                itemDescriptionTransfers.add(itemDescriptionTransferCache.getTransfer(userVisit, itemDescription))
        );

        return itemDescriptionTransfers;
    }

    public List<ItemDescriptionTransfer> getItemDescriptionTransfersByItem(UserVisit userVisit, Item item) {
        return getItemDescriptionTransfers(userVisit, getItemDescriptionsByItem(item));
    }

    public void updateItemDescriptionFromValue(ItemDescriptionDetailValue itemDescriptionDetailValue, BasePK updatedBy) {
        if(itemDescriptionDetailValue.hasBeenModified()) {
            var itemDescription = itemDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionDetailValue.getItemDescriptionPK());
            var itemDescriptionDetail = itemDescription.getActiveDetailForUpdate();
            
            itemDescriptionDetail.setThruTime(session.getStartTime());
            itemDescriptionDetail.store();

            var itemDescriptionPK = itemDescriptionDetail.getItemDescriptionPK(); // Not updated
            var itemDescriptionTypePK = itemDescriptionDetailValue.getItemDescriptionTypePK();
            var itemPK = itemDescriptionDetailValue.getItemPK();
            var languagePK = itemDescriptionDetailValue.getLanguagePK();
            var mimeTypePK = itemDescriptionDetailValue.getMimeTypePK();
            
            itemDescriptionDetail = itemDescriptionDetailFactory.create(itemDescriptionPK,
                    itemDescriptionTypePK, itemPK, languagePK, mimeTypePK, session.getStartTime(), Session.MAX_TIME);
            
            itemDescription.setActiveDetail(itemDescriptionDetail);
            itemDescription.setLastDetail(itemDescriptionDetail);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemDescriptionPK, EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        var itemDescriptionDetail = itemDescription.getLastDetailForUpdate();
        var mimeType = itemDescriptionDetail.getMimeType();
        
        if(mimeType == null) {
            deleteItemStringDescriptionByItemDescription(itemDescription, deletedBy);
        } else {
            var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            
            if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                deleteItemBlobDescriptionByItemDescription(itemDescription, deletedBy);
                deleteItemImageDescriptionByItemDescription(itemDescription, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                deleteItemClobDescriptionByItemDescription(itemDescription, deletedBy);
            }
        }
        
        itemDescriptionDetail.setThruTime(session.getStartTime());
        itemDescription.setActiveDetail(null);
        itemDescription.store();
        
        sendEvent(itemDescriptionDetail.getItemPK(), EventTypes.MODIFY, itemDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemDescriptions(List<ItemDescription> itemDescriptions, BasePK deletedBy) {
        itemDescriptions.forEach((itemDescription) -> 
                deleteItemDescription(itemDescription, deletedBy)
        );
    }

    public void deleteItemDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType, BasePK deletedBy) {
        deleteItemDescriptions(getItemDescriptionsByItemDescriptionTypeForUpdate(itemDescriptionType), deletedBy);
    }

    public void deleteItemDescriptionsByItem(Item item, BasePK deletedBy) {
        deleteItemDescriptions(getItemDescriptionsByItemForUpdate(item), deletedBy);
    }

    public void deleteItemDescriptionsByItemImageType(ItemImageType itemImageType, BasePK deletedBy) {
        var itemImageDescriptions = getItemImageDescriptionsByItemImageTypeForUpdate(itemImageType);

        itemImageDescriptions.forEach((itemImageDescription) -> {
            deleteItemDescription(itemImageDescription.getItemDescription(), deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Item Blob Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemBlobDescriptionFactory itemBlobDescriptionFactory;

    public ItemBlobDescription createItemBlobDescription(ItemDescription itemDescription, ByteArray blobDescription,
            BasePK createdBy) {
        var itemBlobDescription = itemBlobDescriptionFactory.create(itemDescription,
                blobDescription, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemBlobDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemBlobDescription;
    }
    
    private ItemBlobDescription getItemBlobDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemBlobDescriptions.fields())
                .from(ItemBlobDescriptions)
                .where(ItemBlobDescriptions.ITEM_DESCRIPTION.eq(itemDescription.getPrimaryKey()),
                        ItemBlobDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemBlobDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    
    public ItemBlobDescription getItemBlobDescription(ItemDescription itemDescription) {
        return getItemBlobDescription(itemDescription, EntityPermission.READ_ONLY);
    }
    
    public ItemBlobDescription getItemBlobDescriptionForUpdate(ItemDescription itemDescription) {
        return getItemBlobDescription(itemDescription, EntityPermission.READ_WRITE);
    }
    
    public ItemBlobDescriptionValue getItemBlobDescriptionValue(ItemBlobDescription itemBlobDescription) {
        return itemBlobDescription == null? null: itemBlobDescription.getItemBlobDescriptionValue().clone();
    }
    
    public ItemBlobDescriptionValue getItemBlobDescriptionValueForUpdate(ItemDescription itemDescription) {
        return getItemBlobDescriptionValue(getItemBlobDescriptionForUpdate(itemDescription));
    }
    
    public void updateItemBlobDescriptionFromValue(ItemBlobDescriptionValue itemBlobDescriptionValue, BasePK updatedBy) {
        if(itemBlobDescriptionValue.hasBeenModified()) {
            var itemBlobDescription = itemBlobDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemBlobDescriptionValue.getPrimaryKey());
            
            itemBlobDescription.setThruTime(session.getStartTime());
            itemBlobDescription.store();

            var itemDescriptionPK = itemBlobDescription.getItemDescriptionPK(); // Not updated
            var blobDescription = itemBlobDescriptionValue.getBlobDescription();
            
            itemBlobDescription = itemBlobDescriptionFactory.create(itemDescriptionPK, blobDescription,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemBlobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemBlobDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemBlobDescription(ItemBlobDescription itemBlobDescription, BasePK deletedBy) {
        itemBlobDescription.setThruTime(session.getStartTime());
        
        sendEvent(itemBlobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemBlobDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemBlobDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        var itemBlobDescription = getItemBlobDescriptionForUpdate(itemDescription);
        
        if(itemBlobDescription != null) {
            deleteItemBlobDescription(itemBlobDescription, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Item Image Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemImageDescriptionFactory itemImageDescriptionFactory;

    public ItemImageDescription createItemImageDescription(ItemDescription itemDescription, ItemImageType itemImageType, Integer height, Integer width,
            Boolean scaledFromParent, BasePK createdBy) {
        var itemImageDescription = itemImageDescriptionFactory.create(itemDescription, itemImageType, height, width,
                scaledFromParent, session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemImageDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemImageDescription;
    }

    private ItemImageDescription getItemImageDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemImageDescriptions.fields())
                .from(ItemImageDescriptions)
                .where(ItemImageDescriptions.ITEM_DESCRIPTION.eq(itemDescription.getPrimaryKey()),
                        ItemImageDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemImageDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemImageDescription getItemImageDescription(ItemDescription itemDescription) {
        return getItemImageDescription(itemDescription, EntityPermission.READ_ONLY);
    }

    public ItemImageDescription getItemImageDescriptionForUpdate(ItemDescription itemDescription) {
        return getItemImageDescription(itemDescription, EntityPermission.READ_WRITE);
    }

    public ItemImageDescriptionValue getItemImageDescriptionValue(ItemImageDescription itemImageDescription) {
        return itemImageDescription == null? null: itemImageDescription.getItemImageDescriptionValue().clone();
    }

    public ItemImageDescriptionValue getItemImageDescriptionValueForUpdate(ItemDescription itemDescription) {
        return getItemImageDescriptionValue(getItemImageDescriptionForUpdate(itemDescription));
    }

    private List<ItemImageDescription> getItemImageDescriptionsByItemImageType(ItemImageType itemImageType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemImageDescriptions.fields())
                .from(ItemImageDescriptions)
                .where(ItemImageDescriptions.ITEM_IMAGE_TYPE.eq(itemImageType.getPrimaryKey()),
                        ItemImageDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemImageDescriptions.fields())
                    .from(ItemImageDescriptions)
                    .join(ItemDescriptions).onKey(ITEM_IMAGE_DESCRIPTIONS_ITEM_DESCRIPTION_FK)
                    .join(ItemDescriptionDetails).onKey(ITEM_DESCRIPTIONS_LAST_DETAIL_FK)
                    .join(Items).onKey(ITEM_DESCRIPTION_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(ItemDescriptionTypes).onKey(ITEM_DESCRIPTION_DETAILS_ITEM_DESCRIPTION_TYPE_FK)
                    .join(ItemDescriptionTypeDetails).onKey(ITEM_DESCRIPTION_TYPES_LAST_DETAIL_FK)
                    .join(Languages).onKey(ITEM_DESCRIPTION_DETAILS_LANGUAGE_FK)
                    .where(ItemImageDescriptions.ITEM_IMAGE_TYPE.eq(itemImageType.getPrimaryKey()),
                            ItemImageDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, ItemDescriptionTypeDetails.SORT_ORDER, ItemDescriptionTypeDetails.ITEM_DESCRIPTION_TYPE_NAME, Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemImageDescriptionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemImageDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemImageDescription> getItemImageDescriptionsByItemImageType(ItemImageType itemImageType) {
        return getItemImageDescriptionsByItemImageType(itemImageType, EntityPermission.READ_ONLY);
    }

    public List<ItemImageDescription> getItemImageDescriptionsByItemImageTypeForUpdate(ItemImageType itemImageType) {
        return getItemImageDescriptionsByItemImageType(itemImageType, EntityPermission.READ_WRITE);
    }

    public void updateItemImageDescriptionFromValue(ItemImageDescriptionValue itemImageDescriptionValue, BasePK updatedBy) {
        if(itemImageDescriptionValue.hasBeenModified()) {
            var itemImageDescription = itemImageDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageDescriptionValue.getPrimaryKey());

            itemImageDescription.setThruTime(session.getStartTime());
            itemImageDescription.store();

            var itemDescriptionPK = itemImageDescription.getItemDescriptionPK(); // Not updated
            var itemImageTypePK = itemImageDescriptionValue.getItemImageTypePK();
            var height = itemImageDescriptionValue.getHeight();
            var width = itemImageDescriptionValue.getWidth();
            var scaledFromParent = itemImageDescriptionValue.getScaledFromParent();

            itemImageDescription = itemImageDescriptionFactory.create(itemDescriptionPK, itemImageTypePK, height, width, scaledFromParent,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(itemImageDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemImageDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemImageDescription(ItemImageDescription itemImageDescription, BasePK deletedBy) {
        itemImageDescription.setThruTime(session.getStartTime());

        sendEvent(itemImageDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemImageDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteItemImageDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        var itemImageDescription = getItemImageDescriptionForUpdate(itemDescription);

        if(itemImageDescription != null) {
            deleteItemImageDescription(itemImageDescription, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Item Clob Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemClobDescriptionFactory itemClobDescriptionFactory;

    public ItemClobDescription createItemClobDescription(ItemDescription itemDescription, String clobDescription,
            BasePK createdBy) {
        var itemClobDescription = itemClobDescriptionFactory.create(itemDescription,
                clobDescription, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemClobDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemClobDescription;
    }
    
    private ItemClobDescription getItemClobDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemClobDescriptions.fields())
                .from(ItemClobDescriptions)
                .where(ItemClobDescriptions.ITEM_DESCRIPTION.eq(itemDescription.getPrimaryKey()),
                        ItemClobDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemClobDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemClobDescription getItemClobDescription(ItemDescription itemDescription) {
        return getItemClobDescription(itemDescription, EntityPermission.READ_ONLY);
    }
    
    public ItemClobDescription getItemClobDescriptionForUpdate(ItemDescription itemDescription) {
        return getItemClobDescription(itemDescription, EntityPermission.READ_WRITE);
    }
    
    public ItemClobDescriptionValue getItemClobDescriptionValue(ItemClobDescription itemClobDescription) {
        return itemClobDescription == null? null: itemClobDescription.getItemClobDescriptionValue().clone();
    }
    
    public ItemClobDescriptionValue getItemClobDescriptionValueForUpdate(ItemDescription itemDescription) {
        return getItemClobDescriptionValue(getItemClobDescriptionForUpdate(itemDescription));
    }
    
    public void updateItemClobDescriptionFromValue(ItemClobDescriptionValue itemClobDescriptionValue, BasePK updatedBy) {
        if(itemClobDescriptionValue.hasBeenModified()) {
            var itemClobDescription = itemClobDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemClobDescriptionValue.getPrimaryKey());
            
            itemClobDescription.setThruTime(session.getStartTime());
            itemClobDescription.store();

            var itemDescriptionPK = itemClobDescription.getItemDescriptionPK(); // Not updated
            var clobDescription = itemClobDescriptionValue.getClobDescription();
            
            itemClobDescription = itemClobDescriptionFactory.create(itemDescriptionPK, clobDescription,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemClobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemClobDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemClobDescription(ItemClobDescription itemClobDescription, BasePK deletedBy) {
        itemClobDescription.setThruTime(session.getStartTime());
        
        sendEvent(itemClobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemClobDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemClobDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        var itemClobDescription = getItemClobDescriptionForUpdate(itemDescription);
        
        if(itemClobDescription != null) {
            deleteItemClobDescription(itemClobDescription, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Item String Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemStringDescriptionFactory itemStringDescriptionFactory;

    public ItemStringDescription createItemStringDescription(ItemDescription itemDescription, String stringDescription,
            BasePK createdBy) {
        var itemStringDescription = itemStringDescriptionFactory.create(itemDescription,
                stringDescription, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemStringDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemStringDescription;
    }
    
    private ItemStringDescription getItemStringDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemStringDescriptions.fields())
                .from(ItemStringDescriptions)
                .where(ItemStringDescriptions.ITEM_DESCRIPTION.eq(itemDescription.getPrimaryKey()),
                        ItemStringDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemStringDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemStringDescription getItemStringDescription(ItemDescription itemDescription) {
        return getItemStringDescription(itemDescription, EntityPermission.READ_ONLY);
    }
    
    public ItemStringDescription getItemStringDescriptionForUpdate(ItemDescription itemDescription) {
        return getItemStringDescription(itemDescription, EntityPermission.READ_WRITE);
    }
    
    public ItemStringDescriptionValue getItemStringDescriptionValue(ItemStringDescription itemStringDescription) {
        return itemStringDescription == null? null: itemStringDescription.getItemStringDescriptionValue().clone();
    }
    
    public ItemStringDescriptionValue getItemStringDescriptionValueForUpdate(ItemDescription itemDescription) {
        return getItemStringDescriptionValue(getItemStringDescriptionForUpdate(itemDescription));
    }
    
    public void updateItemStringDescriptionFromValue(ItemStringDescriptionValue itemStringDescriptionValue, BasePK updatedBy) {
        if(itemStringDescriptionValue.hasBeenModified()) {
            var itemStringDescription = itemStringDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemStringDescriptionValue.getPrimaryKey());
            
            itemStringDescription.setThruTime(session.getStartTime());
            itemStringDescription.store();

            var itemDescriptionPK = itemStringDescription.getItemDescriptionPK(); // Not updated
            var stringDescription = itemStringDescriptionValue.getStringDescription();
            
            itemStringDescription = itemStringDescriptionFactory.create(itemDescriptionPK, stringDescription,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemStringDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemStringDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemStringDescription(ItemStringDescription itemStringDescription, BasePK deletedBy) {
        itemStringDescription.setThruTime(session.getStartTime());
        
        sendEvent(itemStringDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemStringDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteItemStringDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        var itemStringDescription = getItemStringDescriptionForUpdate(itemDescription);
        
        if(itemStringDescription != null) {
            deleteItemStringDescription(itemStringDescription, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Item Volume Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemVolumeTypeDetailFactory itemVolumeTypeDetailFactory;

    @Inject
    protected ItemVolumeTypeFactory itemVolumeTypeFactory;

    @Inject
    ItemVolumeTypeTransferCache itemVolumeTypeTransferCache;

    public ItemVolumeType createItemVolumeType(String itemVolumeTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultItemVolumeType = getDefaultItemVolumeType();
        var defaultFound = defaultItemVolumeType != null;

        if(defaultFound && isDefault) {
            var defaultItemVolumeTypeDetailValue = getDefaultItemVolumeTypeDetailValueForUpdate();

            defaultItemVolumeTypeDetailValue.setIsDefault(false);
            updateItemVolumeTypeFromValue(defaultItemVolumeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemVolumeType = itemVolumeTypeFactory.create();
        var itemVolumeTypeDetail = itemVolumeTypeDetailFactory.create( itemVolumeType, itemVolumeTypeName,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        itemVolumeType = itemVolumeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemVolumeType.getPrimaryKey());
        itemVolumeType.setActiveDetail(itemVolumeTypeDetail);
        itemVolumeType.setLastDetail(itemVolumeTypeDetail);
        itemVolumeType.store();

        sendEvent(itemVolumeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemVolumeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemVolumeType */
    public ItemVolumeType getItemVolumeTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new ItemVolumeTypePK(entityInstance.getEntityUniqueId());

        return itemVolumeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemVolumeType getItemVolumeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemVolumeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemVolumeType getItemVolumeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemVolumeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemVolumeType getItemVolumeTypeByPK(ItemVolumeTypePK pk) {
        return itemVolumeTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countItemVolumeTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemVolumeTypes)
                .join(ItemVolumeTypeDetails).onKey(ITEM_VOLUME_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ItemVolumeType getItemVolumeTypeByName(String itemVolumeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemVolumeTypes.fields())
                .from(ItemVolumeTypes)
                .join(ItemVolumeTypeDetails).onKey(ITEM_VOLUME_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemVolumeTypeDetails.ITEM_VOLUME_TYPE_NAME.eq(itemVolumeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemVolumeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemVolumeType getItemVolumeTypeByName(String itemVolumeTypeName) {
        return getItemVolumeTypeByName(itemVolumeTypeName, EntityPermission.READ_ONLY);
    }

    public ItemVolumeType getItemVolumeTypeByNameForUpdate(String itemVolumeTypeName) {
        return getItemVolumeTypeByName(itemVolumeTypeName, EntityPermission.READ_WRITE);
    }

    public ItemVolumeTypeDetailValue getItemVolumeTypeDetailValueForUpdate(ItemVolumeType itemVolumeType) {
        return itemVolumeType == null? null: itemVolumeType.getLastDetailForUpdate().getItemVolumeTypeDetailValue().clone();
    }

    public ItemVolumeTypeDetailValue getItemVolumeTypeDetailValueByNameForUpdate(String itemVolumeTypeName) {
        return getItemVolumeTypeDetailValueForUpdate(getItemVolumeTypeByNameForUpdate(itemVolumeTypeName));
    }

    public ItemVolumeType getDefaultItemVolumeType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemVolumeTypes.fields())
                .from(ItemVolumeTypes)
                .join(ItemVolumeTypeDetails).onKey(ITEM_VOLUME_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemVolumeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemVolumeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemVolumeType getDefaultItemVolumeType() {
        return getDefaultItemVolumeType(EntityPermission.READ_ONLY);
    }

    public ItemVolumeType getDefaultItemVolumeTypeForUpdate() {
        return getDefaultItemVolumeType(EntityPermission.READ_WRITE);
    }

    public ItemVolumeTypeDetailValue getDefaultItemVolumeTypeDetailValueForUpdate() {
        return getDefaultItemVolumeTypeForUpdate().getLastDetailForUpdate().getItemVolumeTypeDetailValue().clone();
    }

    private List<ItemVolumeType> getItemVolumeTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemVolumeTypes.fields())
                .from(ItemVolumeTypes)
                .join(ItemVolumeTypeDetails).onKey(ITEM_VOLUME_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemVolumeTypeDetails.SORT_ORDER, ItemVolumeTypeDetails.ITEM_VOLUME_TYPE_NAME),
                    ItemVolumeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemVolumeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemVolumeType> getItemVolumeTypes() {
        return getItemVolumeTypes(EntityPermission.READ_ONLY);
    }

    public List<ItemVolumeType> getItemVolumeTypesForUpdate() {
        return getItemVolumeTypes(EntityPermission.READ_WRITE);
    }

    public ItemVolumeTypeTransfer getItemVolumeTypeTransfer(UserVisit userVisit, ItemVolumeType itemVolumeType) {
        return itemVolumeTypeTransferCache.getTransfer(userVisit, itemVolumeType);
    }

    public List<ItemVolumeTypeTransfer> getItemVolumeTypeTransfers(UserVisit userVisit, Collection<ItemVolumeType> itemVolumeTypes) {
        List<ItemVolumeTypeTransfer> itemVolumeTypeTransfers = new ArrayList<>(itemVolumeTypes.size());

        itemVolumeTypes.forEach((itemVolumeType) ->
                itemVolumeTypeTransfers.add(itemVolumeTypeTransferCache.getTransfer(userVisit, itemVolumeType))
        );

        return itemVolumeTypeTransfers;
    }

    public List<ItemVolumeTypeTransfer> getItemVolumeTypeTransfers(UserVisit userVisit) {
        return getItemVolumeTypeTransfers(userVisit, getItemVolumeTypes());
    }

    public ItemVolumeTypeChoicesBean getItemVolumeTypeChoices(String defaultItemVolumeTypeChoice, Language language,
            boolean allowNullChoice) {
        var itemVolumeTypes = getItemVolumeTypes();
        var size = itemVolumeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemVolumeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemVolumeType : itemVolumeTypes) {
            var itemVolumeTypeDetail = itemVolumeType.getLastDetail();

            var label = getBestItemVolumeTypeDescription(itemVolumeType, language);
            var value = itemVolumeTypeDetail.getItemVolumeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultItemVolumeTypeChoice != null && defaultItemVolumeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemVolumeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemVolumeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateItemVolumeTypeFromValue(final ItemVolumeTypeDetailValue itemVolumeTypeDetailValue, final boolean checkDefault,
            final BasePK updatedBy) {
        if(itemVolumeTypeDetailValue.hasBeenModified()) {
            final var itemVolumeType = itemVolumeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemVolumeTypeDetailValue.getItemVolumeTypePK());
            var itemVolumeTypeDetail = itemVolumeType.getActiveDetailForUpdate();

            itemVolumeTypeDetail.setThruTime(session.getStartTime());
            itemVolumeTypeDetail.store();

            final var itemVolumeTypePK = itemVolumeTypeDetail.getItemVolumeTypePK();
            final var itemVolumeTypeName = itemVolumeTypeDetailValue.getItemVolumeTypeName();
            var isDefault = itemVolumeTypeDetailValue.getIsDefault();
            final var sortOrder = itemVolumeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                final var defaultItemVolumeType = getDefaultItemVolumeType();
                final var defaultFound = defaultItemVolumeType != null && !defaultItemVolumeType.equals(itemVolumeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    final var defaultItemVolumeTypeDetailValue = getDefaultItemVolumeTypeDetailValueForUpdate();

                    defaultItemVolumeTypeDetailValue.setIsDefault(false);
                    updateItemVolumeTypeFromValue(defaultItemVolumeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            itemVolumeTypeDetail = itemVolumeTypeDetailFactory.create(itemVolumeTypePK, itemVolumeTypeName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            itemVolumeType.setActiveDetail(itemVolumeTypeDetail);
            itemVolumeType.setLastDetail(itemVolumeTypeDetail);

            sendEvent(itemVolumeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateItemVolumeTypeFromValue(final ItemVolumeTypeDetailValue itemVolumeTypeDetailValue, final BasePK updatedBy) {
        updateItemVolumeTypeFromValue(itemVolumeTypeDetailValue, true, updatedBy);
    }

    public void deleteItemVolumeType(ItemVolumeType itemVolumeType, BasePK deletedBy) {
        deleteItemVolumeTypeDescriptionsByItemVolumeType(itemVolumeType, deletedBy);

        var itemVolumeTypeDetail = itemVolumeType.getLastDetailForUpdate();
        itemVolumeTypeDetail.setThruTime(session.getStartTime());
        itemVolumeType.setActiveDetail(null);
        itemVolumeType.store();

        // Check for default, and pick one if necessary
        var defaultItemVolumeType = getDefaultItemVolumeType();
        if(defaultItemVolumeType == null) {
            var itemVolumeTypes = getItemVolumeTypesForUpdate();

            if(!itemVolumeTypes.isEmpty()) {
                var iter = itemVolumeTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemVolumeType = (ItemVolumeType)iter.next();
                }
                var itemVolumeTypeDetailValue = Objects.requireNonNull(defaultItemVolumeType).getLastDetailForUpdate().getItemVolumeTypeDetailValue().clone();

                itemVolumeTypeDetailValue.setIsDefault(true);
                updateItemVolumeTypeFromValue(itemVolumeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(itemVolumeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Volume Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemVolumeTypeDescriptionFactory itemVolumeTypeDescriptionFactory;

    @Inject
    ItemVolumeTypeDescriptionTransferCache itemVolumeTypeDescriptionTransferCache;

    public ItemVolumeTypeDescription createItemVolumeTypeDescription(ItemVolumeType itemVolumeType, Language language,
            String description, BasePK createdBy) {
        var itemVolumeTypeDescription = itemVolumeTypeDescriptionFactory.create(
                itemVolumeType, language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemVolumeType.getPrimaryKey(), EventTypes.MODIFY, itemVolumeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemVolumeTypeDescription;
    }

    private ItemVolumeTypeDescription getItemVolumeTypeDescription(ItemVolumeType itemVolumeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemVolumeTypeDescriptions.fields())
                .from(ItemVolumeTypeDescriptions)
                .where(ItemVolumeTypeDescriptions.ITEM_VOLUME_TYPE.eq(itemVolumeType.getPrimaryKey()),
                        ItemVolumeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemVolumeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemVolumeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemVolumeTypeDescription getItemVolumeTypeDescription(ItemVolumeType itemVolumeType, Language language) {
        return getItemVolumeTypeDescription(itemVolumeType, language, EntityPermission.READ_ONLY);
    }

    public ItemVolumeTypeDescription getItemVolumeTypeDescriptionForUpdate(ItemVolumeType itemVolumeType, Language language) {
        return getItemVolumeTypeDescription(itemVolumeType, language, EntityPermission.READ_WRITE);
    }

    public ItemVolumeTypeDescriptionValue getItemVolumeTypeDescriptionValue(ItemVolumeTypeDescription itemVolumeTypeDescription) {
        return itemVolumeTypeDescription == null? null: itemVolumeTypeDescription.getItemVolumeTypeDescriptionValue().clone();
    }

    public ItemVolumeTypeDescriptionValue getItemVolumeTypeDescriptionValueForUpdate(ItemVolumeType itemVolumeType, Language language) {
        return getItemVolumeTypeDescriptionValue(getItemVolumeTypeDescriptionForUpdate(itemVolumeType, language));
    }

    private List<ItemVolumeTypeDescription> getItemVolumeTypeDescriptionsByItemVolumeType(ItemVolumeType itemVolumeType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemVolumeTypeDescriptions.fields())
                    .from(ItemVolumeTypeDescriptions)
                    .join(Languages).onKey(ITEM_VOLUME_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemVolumeTypeDescriptions.ITEM_VOLUME_TYPE.eq(itemVolumeType.getPrimaryKey()),
                            ItemVolumeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemVolumeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemVolumeTypeDescriptions.fields())
                    .from(ItemVolumeTypeDescriptions)
                    .where(ItemVolumeTypeDescriptions.ITEM_VOLUME_TYPE.eq(itemVolumeType.getPrimaryKey()),
                            ItemVolumeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemVolumeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemVolumeTypeDescription> getItemVolumeTypeDescriptionsByItemVolumeType(ItemVolumeType itemVolumeType) {
        return getItemVolumeTypeDescriptionsByItemVolumeType(itemVolumeType, EntityPermission.READ_ONLY);
    }

    public List<ItemVolumeTypeDescription> getItemVolumeTypeDescriptionsByItemVolumeTypeForUpdate(ItemVolumeType itemVolumeType) {
        return getItemVolumeTypeDescriptionsByItemVolumeType(itemVolumeType, EntityPermission.READ_WRITE);
    }

    public String getBestItemVolumeTypeDescription(ItemVolumeType itemVolumeType, Language language) {
        String description;
        var itemVolumeTypeDescription = getItemVolumeTypeDescription(itemVolumeType, language);

        if(itemVolumeTypeDescription == null && !language.getIsDefault()) {
            itemVolumeTypeDescription = getItemVolumeTypeDescription(itemVolumeType, partyControl.getDefaultLanguage());
        }

        if(itemVolumeTypeDescription == null) {
            description = itemVolumeType.getLastDetail().getItemVolumeTypeName();
        } else {
            description = itemVolumeTypeDescription.getDescription();
        }

        return description;
    }

    public ItemVolumeTypeDescriptionTransfer getItemVolumeTypeDescriptionTransfer(UserVisit userVisit, ItemVolumeTypeDescription itemVolumeTypeDescription) {
        return itemVolumeTypeDescriptionTransferCache.getTransfer(userVisit, itemVolumeTypeDescription);
    }

    public List<ItemVolumeTypeDescriptionTransfer> getItemVolumeTypeDescriptionTransfersByItemVolumeType(UserVisit userVisit, ItemVolumeType itemVolumeType) {
        var itemVolumeTypeDescriptions = getItemVolumeTypeDescriptionsByItemVolumeType(itemVolumeType);
        List<ItemVolumeTypeDescriptionTransfer> itemVolumeTypeDescriptionTransfers = new ArrayList<>(itemVolumeTypeDescriptions.size());

        itemVolumeTypeDescriptions.forEach((itemVolumeTypeDescription) ->
                itemVolumeTypeDescriptionTransfers.add(itemVolumeTypeDescriptionTransferCache.getTransfer(userVisit, itemVolumeTypeDescription))
        );

        return itemVolumeTypeDescriptionTransfers;
    }

    public void updateItemVolumeTypeDescriptionFromValue(ItemVolumeTypeDescriptionValue itemVolumeTypeDescriptionValue, BasePK updatedBy) {
        if(itemVolumeTypeDescriptionValue.hasBeenModified()) {
            var itemVolumeTypeDescription = itemVolumeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemVolumeTypeDescriptionValue.getPrimaryKey());

            itemVolumeTypeDescription.setThruTime(session.getStartTime());
            itemVolumeTypeDescription.store();

            var itemVolumeType = itemVolumeTypeDescription.getItemVolumeType();
            var language = itemVolumeTypeDescription.getLanguage();
            var description = itemVolumeTypeDescriptionValue.getDescription();

            itemVolumeTypeDescription = itemVolumeTypeDescriptionFactory.create(itemVolumeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(itemVolumeType.getPrimaryKey(), EventTypes.MODIFY, itemVolumeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemVolumeTypeDescription(ItemVolumeTypeDescription itemVolumeTypeDescription, BasePK deletedBy) {
        itemVolumeTypeDescription.setThruTime(session.getStartTime());

        sendEvent(itemVolumeTypeDescription.getItemVolumeTypePK(), EventTypes.MODIFY, itemVolumeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteItemVolumeTypeDescriptionsByItemVolumeType(ItemVolumeType itemVolumeType, BasePK deletedBy) {
        var itemVolumeTypeDescriptions = getItemVolumeTypeDescriptionsByItemVolumeTypeForUpdate(itemVolumeType);

        itemVolumeTypeDescriptions.forEach((itemVolumeTypeDescription) ->
                deleteItemVolumeTypeDescription(itemVolumeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemVolumeFactory itemVolumeFactory;

    @Inject
    ItemVolumeTransferCache itemVolumeTransferCache;

    public ItemVolume createItemVolume(Item item, UnitOfMeasureType unitOfMeasureType, ItemVolumeType itemVolumeType,
            Long height, Long width, Long depth, BasePK createdBy) {
        var itemVolume = itemVolumeFactory.create(item, unitOfMeasureType, itemVolumeType, height, width, depth,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemVolume.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemVolume;
    }

    public long countItemVolumesByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemVolumes)
                .where(ItemVolumes.ITEM.eq(item.getPrimaryKey()),
                        ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemVolumesByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemVolumes)
                .where(ItemVolumes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemVolumesByItemVolumeType(final ItemVolumeType itemVolumeType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemVolumes)
                .where(ItemVolumes.ITEM_VOLUME_TYPE.eq(itemVolumeType.getPrimaryKey()),
                        ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemVolume getItemVolume(Item item, UnitOfMeasureType unitOfMeasureType, ItemVolumeType itemVolumeType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemVolumes.fields())
                .from(ItemVolumes)
                .where(ItemVolumes.ITEM.eq(item.getPrimaryKey()),
                        ItemVolumes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemVolumes.ITEM_VOLUME_TYPE.eq(itemVolumeType.getPrimaryKey()),
                        ItemVolumes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemVolumeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemVolume getItemVolume(Item item, UnitOfMeasureType unitOfMeasureType, ItemVolumeType itemVolumeType) {
        return getItemVolume(item, unitOfMeasureType, itemVolumeType, EntityPermission.READ_ONLY);
    }

    public ItemVolume getItemVolumeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType, ItemVolumeType itemVolumeType) {
        return getItemVolume(item, unitOfMeasureType, itemVolumeType, EntityPermission.READ_WRITE);
    }

    public ItemVolumeValue getItemVolumeValue(ItemVolume itemVolume) {
        return itemVolume == null? null: itemVolume.getItemVolumeValue().clone();
    }

    public ItemVolumeValue getItemVolumeValueForUpdate(Item item, UnitOfMeasureType unitOfMeasureType, ItemVolumeType itemVolumeType) {
        return getItemVolumeForUpdate(item, unitOfMeasureType, itemVolumeType).getItemVolumeValue().clone();
    }

    private List<ItemVolume> getItemVolumes(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemVolumes.fields())
                    .from(ItemVolumes)
                    .join(ItemVolumeTypes).onKey(ITEM_VOLUMES_ITEM_VOLUME_TYPE_FK)
                    .join(ItemVolumeTypeDetails).onKey(ITEM_VOLUME_TYPES_LAST_DETAIL_FK)
                    .where(ItemVolumes.ITEM.eq(item.getPrimaryKey()),
                            ItemVolumes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemVolumeTypeDetails.SORT_ORDER, ItemVolumeTypeDetails.ITEM_VOLUME_TYPE_NAME),
                    ItemVolumeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemVolumes.fields())
                    .from(ItemVolumes)
                    .where(ItemVolumes.ITEM.eq(item.getPrimaryKey()),
                            ItemVolumes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemVolumeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemVolume> getItemVolumes(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolumes(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<ItemVolume> getItemVolumesForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolumes(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<ItemVolume> getItemVolumesByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemVolumes.fields())
                    .from(ItemVolumes)
                    .join(UnitOfMeasureTypes).onKey(ITEM_VOLUMES_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(ItemVolumeTypes).onKey(ITEM_VOLUMES_ITEM_VOLUME_TYPE_FK)
                    .join(ItemVolumeTypeDetails).onKey(ITEM_VOLUME_TYPES_LAST_DETAIL_FK)
                    .where(ItemVolumes.ITEM.eq(item.getPrimaryKey()),
                            ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, ItemVolumeTypeDetails.SORT_ORDER, ItemVolumeTypeDetails.ITEM_VOLUME_TYPE_NAME),
                    ItemVolumeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemVolumes.fields())
                    .from(ItemVolumes)
                    .where(ItemVolumes.ITEM.eq(item.getPrimaryKey()),
                            ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemVolumeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemVolume> getItemVolumesByItem(Item item) {
        return getItemVolumesByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemVolume> getItemVolumesByItemForUpdate(Item item) {
        return getItemVolumesByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemVolume> getItemVolumesByItemVolumeType(ItemVolumeType itemVolumeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemVolumes.fields())
                    .from(ItemVolumes)
                    .join(Items).onKey(ITEM_VOLUMES_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_VOLUMES_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(ItemVolumes.ITEM_VOLUME_TYPE.eq(itemVolumeType.getPrimaryKey()),
                            ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemVolumeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemVolumes.fields())
                    .from(ItemVolumes)
                    .where(ItemVolumes.ITEM_VOLUME_TYPE.eq(itemVolumeType.getPrimaryKey()),
                            ItemVolumes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemVolumeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemVolume> getItemVolumesByItemVolumeType(ItemVolumeType itemVolumeType) {
        return getItemVolumesByItemVolumeType(itemVolumeType, EntityPermission.READ_ONLY);
    }

    public List<ItemVolume> getItemVolumesByItemVolumeTypeForUpdate(ItemVolumeType itemVolumeType) {
        return getItemVolumesByItemVolumeType(itemVolumeType, EntityPermission.READ_WRITE);
    }

    public void updateItemVolumeFromValue(ItemVolumeValue itemVolumeValue, BasePK updatedBy) {
        if(itemVolumeValue.hasBeenModified()) {
            var itemVolume = itemVolumeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemVolumeValue.getPrimaryKey());

            itemVolume.setThruTime(session.getStartTime());
            itemVolume.store();

            var itemPK = itemVolume.getItemPK();
            var unitOfMeasureTypePK = itemVolume.getUnitOfMeasureTypePK();
            var itemVolumeTypePK = itemVolume.getItemVolumeTypePK();
            var height = itemVolumeValue.getHeight();
            var width = itemVolumeValue.getWidth();
            var depth = itemVolumeValue.getDepth();

            itemVolume = itemVolumeFactory.create(itemPK, unitOfMeasureTypePK, itemVolumeTypePK, height,
                    width, depth, session.getStartTime(), Session.MAX_TIME);

            sendEvent(itemPK, EventTypes.MODIFY, itemVolume.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public ItemVolumeTransfer getItemVolumeTransfer(UserVisit userVisit, ItemVolume itemVolume) {
        return itemVolume == null? null: itemVolumeTransferCache.getTransfer(userVisit, itemVolume);
    }

    public ItemVolumeTransfer getItemVolumeTransfer(UserVisit userVisit, Item item, UnitOfMeasureType unitOfMeasureType,
            ItemVolumeType itemVolumeType) {
        return getItemVolumeTransfer(userVisit, getItemVolume(item, unitOfMeasureType, itemVolumeType));
    }

    public List<ItemVolumeTransfer> getItemVolumeTransfers(UserVisit userVisit, Collection<ItemVolume> itemVolumes) {
        List<ItemVolumeTransfer> itemVolumeTransfers = new ArrayList<>(itemVolumes.size());

        itemVolumes.forEach((itemVolume) ->
                itemVolumeTransfers.add(itemVolumeTransferCache.getTransfer(userVisit, itemVolume))
        );

        return itemVolumeTransfers;
    }

    public List<ItemVolumeTransfer> getItemVolumeTransfersByItem(UserVisit userVisit, Item item) {
        return getItemVolumeTransfers(userVisit, getItemVolumesByItem(item));
    }

    public void deleteItemVolume(ItemVolume itemVolume, BasePK deletedBy) {
        itemVolume.setThruTime(session.getStartTime());

        sendEvent(itemVolume.getItemPK(), EventTypes.MODIFY, itemVolume.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteItemVolumes(Collection<ItemVolume> itemVolumes, BasePK deletedBy) {
        itemVolumes.forEach((itemVolume) ->
                deleteItemVolume(itemVolume, deletedBy)
        );
    }

    public void deleteItemVolumesByItem(Item item, BasePK deletedBy) {
        deleteItemVolumes(getItemVolumesByItemForUpdate(item), deletedBy);
    }

    public void deleteItemVolumeByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemVolumes(getItemVolumesForUpdate(item, unitOfMeasureType), deletedBy);
    }

    public void deleteItemVolumesByItemVolumeType(ItemVolumeType itemVolumeType, BasePK deletedBy) {
        deleteItemVolumes(getItemVolumesByItemVolumeTypeForUpdate(itemVolumeType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Weight Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemWeightTypeDetailFactory itemWeightTypeDetailFactory;

    @Inject
    protected ItemWeightTypeFactory itemWeightTypeFactory;

    @Inject
    ItemWeightTypeTransferCache itemWeightTypeTransferCache;

    public ItemWeightType createItemWeightType(String itemWeightTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultItemWeightType = getDefaultItemWeightType();
        var defaultFound = defaultItemWeightType != null;

        if(defaultFound && isDefault) {
            var defaultItemWeightTypeDetailValue = getDefaultItemWeightTypeDetailValueForUpdate();

            defaultItemWeightTypeDetailValue.setIsDefault(false);
            updateItemWeightTypeFromValue(defaultItemWeightTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemWeightType = itemWeightTypeFactory.create();
        var itemWeightTypeDetail = itemWeightTypeDetailFactory.create( itemWeightType, itemWeightTypeName,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        itemWeightType = itemWeightTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemWeightType.getPrimaryKey());
        itemWeightType.setActiveDetail(itemWeightTypeDetail);
        itemWeightType.setLastDetail(itemWeightTypeDetail);
        itemWeightType.store();

        sendEvent(itemWeightType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemWeightType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemWeightType */
    public ItemWeightType getItemWeightTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new ItemWeightTypePK(entityInstance.getEntityUniqueId());

        return itemWeightTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemWeightType getItemWeightTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemWeightTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemWeightType getItemWeightTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemWeightTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemWeightType getItemWeightTypeByPK(ItemWeightTypePK pk) {
        return itemWeightTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countItemWeightTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ItemWeightTypes)
                .join(ItemWeightTypeDetails).onKey(ITEM_WEIGHT_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ItemWeightType getItemWeightTypeByName(String itemWeightTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemWeightTypes.fields())
                .from(ItemWeightTypes)
                .join(ItemWeightTypeDetails).onKey(ITEM_WEIGHT_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemWeightTypeDetails.ITEM_WEIGHT_TYPE_NAME.eq(itemWeightTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemWeightTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemWeightType getItemWeightTypeByName(String itemWeightTypeName) {
        return getItemWeightTypeByName(itemWeightTypeName, EntityPermission.READ_ONLY);
    }

    public ItemWeightType getItemWeightTypeByNameForUpdate(String itemWeightTypeName) {
        return getItemWeightTypeByName(itemWeightTypeName, EntityPermission.READ_WRITE);
    }

    public ItemWeightTypeDetailValue getItemWeightTypeDetailValueForUpdate(ItemWeightType itemWeightType) {
        return itemWeightType == null? null: itemWeightType.getLastDetailForUpdate().getItemWeightTypeDetailValue().clone();
    }

    public ItemWeightTypeDetailValue getItemWeightTypeDetailValueByNameForUpdate(String itemWeightTypeName) {
        return getItemWeightTypeDetailValueForUpdate(getItemWeightTypeByNameForUpdate(itemWeightTypeName));
    }

    public ItemWeightType getDefaultItemWeightType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemWeightTypes.fields())
                .from(ItemWeightTypes)
                .join(ItemWeightTypeDetails).onKey(ITEM_WEIGHT_TYPES_ACTIVE_DETAIL_FK)
                .where(ItemWeightTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemWeightTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemWeightType getDefaultItemWeightType() {
        return getDefaultItemWeightType(EntityPermission.READ_ONLY);
    }

    public ItemWeightType getDefaultItemWeightTypeForUpdate() {
        return getDefaultItemWeightType(EntityPermission.READ_WRITE);
    }

    public ItemWeightTypeDetailValue getDefaultItemWeightTypeDetailValueForUpdate() {
        return getDefaultItemWeightTypeForUpdate().getLastDetailForUpdate().getItemWeightTypeDetailValue().clone();
    }

    private List<ItemWeightType> getItemWeightTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemWeightTypes.fields())
                .from(ItemWeightTypes)
                .join(ItemWeightTypeDetails).onKey(ITEM_WEIGHT_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemWeightTypeDetails.SORT_ORDER, ItemWeightTypeDetails.ITEM_WEIGHT_TYPE_NAME),
                    ItemWeightTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemWeightTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemWeightType> getItemWeightTypes() {
        return getItemWeightTypes(EntityPermission.READ_ONLY);
    }

    public List<ItemWeightType> getItemWeightTypesForUpdate() {
        return getItemWeightTypes(EntityPermission.READ_WRITE);
    }

    public ItemWeightTypeTransfer getItemWeightTypeTransfer(UserVisit userVisit, ItemWeightType itemWeightType) {
        return itemWeightTypeTransferCache.getTransfer(userVisit, itemWeightType);
    }

    public List<ItemWeightTypeTransfer> getItemWeightTypeTransfers(UserVisit userVisit, Collection<ItemWeightType> itemWeightTypes) {
        List<ItemWeightTypeTransfer> itemWeightTypeTransfers = new ArrayList<>(itemWeightTypes.size());

        itemWeightTypes.forEach((itemWeightType) ->
                itemWeightTypeTransfers.add(itemWeightTypeTransferCache.getTransfer(userVisit, itemWeightType))
        );

        return itemWeightTypeTransfers;
    }

    public List<ItemWeightTypeTransfer> getItemWeightTypeTransfers(UserVisit userVisit) {
        return getItemWeightTypeTransfers(userVisit, getItemWeightTypes());
    }

    public ItemWeightTypeChoicesBean getItemWeightTypeChoices(String defaultItemWeightTypeChoice, Language language,
            boolean allowNullChoice) {
        var itemWeightTypes = getItemWeightTypes();
        var size = itemWeightTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemWeightTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemWeightType : itemWeightTypes) {
            var itemWeightTypeDetail = itemWeightType.getLastDetail();

            var label = getBestItemWeightTypeDescription(itemWeightType, language);
            var value = itemWeightTypeDetail.getItemWeightTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultItemWeightTypeChoice != null && defaultItemWeightTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemWeightTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemWeightTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateItemWeightTypeFromValue(final ItemWeightTypeDetailValue itemWeightTypeDetailValue, final boolean checkDefault,
            final BasePK updatedBy) {
        if(itemWeightTypeDetailValue.hasBeenModified()) {
            final var itemWeightType = itemWeightTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemWeightTypeDetailValue.getItemWeightTypePK());
            var itemWeightTypeDetail = itemWeightType.getActiveDetailForUpdate();

            itemWeightTypeDetail.setThruTime(session.getStartTime());
            itemWeightTypeDetail.store();

            final var itemWeightTypePK = itemWeightTypeDetail.getItemWeightTypePK();
            final var itemWeightTypeName = itemWeightTypeDetailValue.getItemWeightTypeName();
            var isDefault = itemWeightTypeDetailValue.getIsDefault();
            final var sortOrder = itemWeightTypeDetailValue.getSortOrder();

            if(checkDefault) {
                final var defaultItemWeightType = getDefaultItemWeightType();
                final var defaultFound = defaultItemWeightType != null && !defaultItemWeightType.equals(itemWeightType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    final var defaultItemWeightTypeDetailValue = getDefaultItemWeightTypeDetailValueForUpdate();

                    defaultItemWeightTypeDetailValue.setIsDefault(false);
                    updateItemWeightTypeFromValue(defaultItemWeightTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            itemWeightTypeDetail = itemWeightTypeDetailFactory.create(itemWeightTypePK, itemWeightTypeName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            itemWeightType.setActiveDetail(itemWeightTypeDetail);
            itemWeightType.setLastDetail(itemWeightTypeDetail);

            sendEvent(itemWeightTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateItemWeightTypeFromValue(final ItemWeightTypeDetailValue itemWeightTypeDetailValue, final BasePK updatedBy) {
        updateItemWeightTypeFromValue(itemWeightTypeDetailValue, true, updatedBy);
    }

    public void deleteItemWeightType(ItemWeightType itemWeightType, BasePK deletedBy) {
        deleteItemWeightsByItemWeightType(itemWeightType, deletedBy);
        deleteItemWeightTypeDescriptionsByItemWeightType(itemWeightType, deletedBy);

        var itemWeightTypeDetail = itemWeightType.getLastDetailForUpdate();
        itemWeightTypeDetail.setThruTime(session.getStartTime());
        itemWeightType.setActiveDetail(null);
        itemWeightType.store();

        // Check for default, and pick one if necessary
        var defaultItemWeightType = getDefaultItemWeightType();
        if(defaultItemWeightType == null) {
            var itemWeightTypes = getItemWeightTypesForUpdate();

            if(!itemWeightTypes.isEmpty()) {
                var iter = itemWeightTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemWeightType = (ItemWeightType)iter.next();
                }
                var itemWeightTypeDetailValue = Objects.requireNonNull(defaultItemWeightType).getLastDetailForUpdate().getItemWeightTypeDetailValue().clone();

                itemWeightTypeDetailValue.setIsDefault(true);
                updateItemWeightTypeFromValue(itemWeightTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(itemWeightType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Weight Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemWeightTypeDescriptionFactory itemWeightTypeDescriptionFactory;

    @Inject
    ItemWeightTypeDescriptionTransferCache itemWeightTypeDescriptionTransferCache;

    public ItemWeightTypeDescription createItemWeightTypeDescription(ItemWeightType itemWeightType, Language language,
            String description, BasePK createdBy) {
        var itemWeightTypeDescription = itemWeightTypeDescriptionFactory.create(
                itemWeightType, language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(itemWeightType.getPrimaryKey(), EventTypes.MODIFY, itemWeightTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemWeightTypeDescription;
    }

    private ItemWeightTypeDescription getItemWeightTypeDescription(ItemWeightType itemWeightType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemWeightTypeDescriptions.fields())
                .from(ItemWeightTypeDescriptions)
                .where(ItemWeightTypeDescriptions.ITEM_WEIGHT_TYPE.eq(itemWeightType.getPrimaryKey()),
                        ItemWeightTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemWeightTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemWeightTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemWeightTypeDescription getItemWeightTypeDescription(ItemWeightType itemWeightType, Language language) {
        return getItemWeightTypeDescription(itemWeightType, language, EntityPermission.READ_ONLY);
    }

    public ItemWeightTypeDescription getItemWeightTypeDescriptionForUpdate(ItemWeightType itemWeightType, Language language) {
        return getItemWeightTypeDescription(itemWeightType, language, EntityPermission.READ_WRITE);
    }

    public ItemWeightTypeDescriptionValue getItemWeightTypeDescriptionValue(ItemWeightTypeDescription itemWeightTypeDescription) {
        return itemWeightTypeDescription == null? null: itemWeightTypeDescription.getItemWeightTypeDescriptionValue().clone();
    }

    public ItemWeightTypeDescriptionValue getItemWeightTypeDescriptionValueForUpdate(ItemWeightType itemWeightType, Language language) {
        return getItemWeightTypeDescriptionValue(getItemWeightTypeDescriptionForUpdate(itemWeightType, language));
    }

    private List<ItemWeightTypeDescription> getItemWeightTypeDescriptionsByItemWeightType(ItemWeightType itemWeightType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemWeightTypeDescriptions.fields())
                    .from(ItemWeightTypeDescriptions)
                    .join(Languages).onKey(ITEM_WEIGHT_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemWeightTypeDescriptions.ITEM_WEIGHT_TYPE.eq(itemWeightType.getPrimaryKey()),
                            ItemWeightTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemWeightTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemWeightTypeDescriptions.fields())
                    .from(ItemWeightTypeDescriptions)
                    .where(ItemWeightTypeDescriptions.ITEM_WEIGHT_TYPE.eq(itemWeightType.getPrimaryKey()),
                            ItemWeightTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemWeightTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemWeightTypeDescription> getItemWeightTypeDescriptionsByItemWeightType(ItemWeightType itemWeightType) {
        return getItemWeightTypeDescriptionsByItemWeightType(itemWeightType, EntityPermission.READ_ONLY);
    }

    public List<ItemWeightTypeDescription> getItemWeightTypeDescriptionsByItemWeightTypeForUpdate(ItemWeightType itemWeightType) {
        return getItemWeightTypeDescriptionsByItemWeightType(itemWeightType, EntityPermission.READ_WRITE);
    }

    public String getBestItemWeightTypeDescription(ItemWeightType itemWeightType, Language language) {
        String description;
        var itemWeightTypeDescription = getItemWeightTypeDescription(itemWeightType, language);

        if(itemWeightTypeDescription == null && !language.getIsDefault()) {
            itemWeightTypeDescription = getItemWeightTypeDescription(itemWeightType, partyControl.getDefaultLanguage());
        }

        if(itemWeightTypeDescription == null) {
            description = itemWeightType.getLastDetail().getItemWeightTypeName();
        } else {
            description = itemWeightTypeDescription.getDescription();
        }

        return description;
    }

    public ItemWeightTypeDescriptionTransfer getItemWeightTypeDescriptionTransfer(UserVisit userVisit, ItemWeightTypeDescription itemWeightTypeDescription) {
        return itemWeightTypeDescriptionTransferCache.getTransfer(userVisit, itemWeightTypeDescription);
    }

    public List<ItemWeightTypeDescriptionTransfer> getItemWeightTypeDescriptionTransfersByItemWeightType(UserVisit userVisit, ItemWeightType itemWeightType) {
        var itemWeightTypeDescriptions = getItemWeightTypeDescriptionsByItemWeightType(itemWeightType);
        List<ItemWeightTypeDescriptionTransfer> itemWeightTypeDescriptionTransfers = new ArrayList<>(itemWeightTypeDescriptions.size());

        itemWeightTypeDescriptions.forEach((itemWeightTypeDescription) ->
                itemWeightTypeDescriptionTransfers.add(itemWeightTypeDescriptionTransferCache.getTransfer(userVisit, itemWeightTypeDescription))
        );

        return itemWeightTypeDescriptionTransfers;
    }

    public void updateItemWeightTypeDescriptionFromValue(ItemWeightTypeDescriptionValue itemWeightTypeDescriptionValue, BasePK updatedBy) {
        if(itemWeightTypeDescriptionValue.hasBeenModified()) {
            var itemWeightTypeDescription = itemWeightTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemWeightTypeDescriptionValue.getPrimaryKey());

            itemWeightTypeDescription.setThruTime(session.getStartTime());
            itemWeightTypeDescription.store();

            var itemWeightType = itemWeightTypeDescription.getItemWeightType();
            var language = itemWeightTypeDescription.getLanguage();
            var description = itemWeightTypeDescriptionValue.getDescription();

            itemWeightTypeDescription = itemWeightTypeDescriptionFactory.create(itemWeightType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(itemWeightType.getPrimaryKey(), EventTypes.MODIFY, itemWeightTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemWeightTypeDescription(ItemWeightTypeDescription itemWeightTypeDescription, BasePK deletedBy) {
        itemWeightTypeDescription.setThruTime(session.getStartTime());

        sendEvent(itemWeightTypeDescription.getItemWeightTypePK(), EventTypes.MODIFY, itemWeightTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteItemWeightTypeDescriptionsByItemWeightType(ItemWeightType itemWeightType, BasePK deletedBy) {
        var itemWeightTypeDescriptions = getItemWeightTypeDescriptionsByItemWeightTypeForUpdate(itemWeightType);

        itemWeightTypeDescriptions.forEach((itemWeightTypeDescription) ->
                deleteItemWeightTypeDescription(itemWeightTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemWeightFactory itemWeightFactory;

    @Inject
    ItemWeightTransferCache itemWeightTransferCache;

    public ItemWeight createItemWeight(Item item, UnitOfMeasureType unitOfMeasureType, ItemWeightType itemWeightType,
            Long weight, BasePK createdBy) {
        var itemWeight = itemWeightFactory.create(item, unitOfMeasureType, itemWeightType, weight,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemWeight.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemWeight;
    }

    public long countItemWeightsByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemWeights)
                .where(ItemWeights.ITEM.eq(item.getPrimaryKey()),
                        ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemWeightsByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemWeights)
                .where(ItemWeights.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemWeightsByItemWeightType(final ItemWeightType itemWeightType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemWeights)
                .where(ItemWeights.ITEM_WEIGHT_TYPE.eq(itemWeightType.getPrimaryKey()),
                        ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ItemWeight getItemWeight(Item item, UnitOfMeasureType unitOfMeasureType, ItemWeightType itemWeightType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemWeights.fields())
                .from(ItemWeights)
                .where(ItemWeights.ITEM.eq(item.getPrimaryKey()),
                        ItemWeights.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ItemWeights.ITEM_WEIGHT_TYPE.eq(itemWeightType.getPrimaryKey()),
                        ItemWeights.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemWeightFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemWeight getItemWeight(Item item, UnitOfMeasureType unitOfMeasureType, ItemWeightType itemWeightType) {
        return getItemWeight(item, unitOfMeasureType, itemWeightType, EntityPermission.READ_ONLY);
    }
    
    public ItemWeight getItemWeightForUpdate(Item item, UnitOfMeasureType unitOfMeasureType, ItemWeightType itemWeightType) {
        return getItemWeight(item, unitOfMeasureType, itemWeightType, EntityPermission.READ_WRITE);
    }
    
    public ItemWeightValue getItemWeightValue(ItemWeight itemWeight) {
        return itemWeight == null? null: itemWeight.getItemWeightValue().clone();
    }

    public ItemWeightValue getItemWeightValueForUpdate(Item item, UnitOfMeasureType unitOfMeasureType, ItemWeightType itemWeightType) {
        return getItemWeightForUpdate(item, unitOfMeasureType, itemWeightType).getItemWeightValue().clone();
    }

    private List<ItemWeight> getItemWeights(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemWeights.fields())
                    .from(ItemWeights)
                    .join(ItemWeightTypes).onKey(ITEM_WEIGHTS_ITEM_WEIGHT_TYPE_FK)
                    .join(ItemWeightTypeDetails).onKey(ITEM_WEIGHT_TYPES_LAST_DETAIL_FK)
                    .where(ItemWeights.ITEM.eq(item.getPrimaryKey()),
                            ItemWeights.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemWeightTypeDetails.SORT_ORDER, ItemWeightTypeDetails.ITEM_WEIGHT_TYPE_NAME),
                    ItemWeightFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemWeights.fields())
                    .from(ItemWeights)
                    .where(ItemWeights.ITEM.eq(item.getPrimaryKey()),
                            ItemWeights.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemWeightFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemWeight> getItemWeights(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeights(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<ItemWeight> getItemWeightsForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeights(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<ItemWeight> getItemWeightsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemWeights.fields())
                    .from(ItemWeights)
                    .join(UnitOfMeasureTypes).onKey(ITEM_WEIGHTS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(ItemWeightTypes).onKey(ITEM_WEIGHTS_ITEM_WEIGHT_TYPE_FK)
                    .join(ItemWeightTypeDetails).onKey(ITEM_WEIGHT_TYPES_LAST_DETAIL_FK)
                    .where(ItemWeights.ITEM.eq(item.getPrimaryKey()),
                            ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, ItemWeightTypeDetails.SORT_ORDER, ItemWeightTypeDetails.ITEM_WEIGHT_TYPE_NAME),
                    ItemWeightFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemWeights.fields())
                    .from(ItemWeights)
                    .where(ItemWeights.ITEM.eq(item.getPrimaryKey()),
                            ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemWeightFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemWeight> getItemWeightsByItem(Item item) {
        return getItemWeightsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemWeight> getItemWeightsByItemForUpdate(Item item) {
        return getItemWeightsByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemWeight> getItemWeightsByItemWeightType(ItemWeightType itemWeightType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemWeights.fields())
                    .from(ItemWeights)
                    .join(Items).onKey(ITEM_WEIGHTS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(ITEM_WEIGHTS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(ItemWeights.ITEM_WEIGHT_TYPE.eq(itemWeightType.getPrimaryKey()),
                            ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    ItemWeightFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemWeights.fields())
                    .from(ItemWeights)
                    .where(ItemWeights.ITEM_WEIGHT_TYPE.eq(itemWeightType.getPrimaryKey()),
                            ItemWeights.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemWeightFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemWeight> getItemWeightsByItemWeightType(ItemWeightType itemWeightType) {
        return getItemWeightsByItemWeightType(itemWeightType, EntityPermission.READ_ONLY);
    }

    public List<ItemWeight> getItemWeightsByItemWeightTypeForUpdate(ItemWeightType itemWeightType) {
        return getItemWeightsByItemWeightType(itemWeightType, EntityPermission.READ_WRITE);
    }

    public void updateItemWeightFromValue(ItemWeightValue itemWeightValue, BasePK updatedBy) {
        if(itemWeightValue.hasBeenModified()) {
            var itemWeight = itemWeightFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    itemWeightValue.getPrimaryKey());
            
            itemWeight.setThruTime(session.getStartTime());
            itemWeight.store();

            var itemPK = itemWeight.getItemPK();
            var unitOfMeasureTypePK = itemWeight.getUnitOfMeasureTypePK();
            var itemWeightTypePK = itemWeight.getItemWeightTypePK();
            var weight = itemWeightValue.getWeight();
            
            itemWeight = itemWeightFactory.create(itemPK, unitOfMeasureTypePK, itemWeightTypePK, weight,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemWeight.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemWeightTransfer getItemWeightTransfer(UserVisit userVisit, ItemWeight itemWeight) {
        return itemWeight == null? null: itemWeightTransferCache.getTransfer(userVisit, itemWeight);
    }
    
    public ItemWeightTransfer getItemWeightTransfer(UserVisit userVisit, Item item, UnitOfMeasureType unitOfMeasureType,
            ItemWeightType itemWeightType) {
        return getItemWeightTransfer(userVisit, getItemWeight(item, unitOfMeasureType, itemWeightType));
    }
    
    public List<ItemWeightTransfer> getItemWeightTransfersByItem(UserVisit userVisit, Item item) {
        var itemWeights = getItemWeightsByItem(item);
        List<ItemWeightTransfer> itemWeightTransfers = new ArrayList<>(itemWeights.size());
        
        itemWeights.forEach((itemWeight) ->
                itemWeightTransfers.add(itemWeightTransferCache.getTransfer(userVisit, itemWeight))
        );
        
        return itemWeightTransfers;
    }
    
    public void deleteItemWeight(ItemWeight itemWeight, BasePK deletedBy) {
        itemWeight.setThruTime(session.getStartTime());
        
        sendEvent(itemWeight.getItemPK(), EventTypes.MODIFY, itemWeight.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteItemWeights(Collection<ItemWeight> itemWeights, BasePK deletedBy) {
        itemWeights.forEach((itemWeight) ->
                deleteItemWeight(itemWeight, deletedBy)
        );
    }

    public void deleteItemWeightsByItem(Item item, BasePK deletedBy) {
        deleteItemWeights(getItemWeightsByItemForUpdate(item), deletedBy);
    }

    public void deleteItemWeightByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteItemWeights(getItemWeightsForUpdate(item, unitOfMeasureType), deletedBy);
    }

    public void deleteItemWeightsByItemWeightType(ItemWeightType itemWeightType, BasePK deletedBy) {
        deleteItemWeights(getItemWeightsByItemWeightTypeForUpdate(itemWeightType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    @Inject
    protected RelatedItemTypeDetailFactory relatedItemTypeDetailFactory;

    @Inject
    protected RelatedItemTypeFactory relatedItemTypeFactory;

    @Inject
    RelatedItemTypeTransferCache relatedItemTypeTransferCache;

    public RelatedItemType createRelatedItemType(String relatedItemTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultRelatedItemType = getDefaultRelatedItemType();
        var defaultFound = defaultRelatedItemType != null;

        if(defaultFound && isDefault) {
            var defaultRelatedItemTypeDetailValue = getDefaultRelatedItemTypeDetailValueForUpdate();

            defaultRelatedItemTypeDetailValue.setIsDefault(false);
            updateRelatedItemTypeFromValue(defaultRelatedItemTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var relatedItemType = relatedItemTypeFactory.create();
        var relatedItemTypeDetail = relatedItemTypeDetailFactory.create(relatedItemType,
                relatedItemTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        relatedItemType = relatedItemTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                relatedItemType.getPrimaryKey());
        relatedItemType.setActiveDetail(relatedItemTypeDetail);
        relatedItemType.setLastDetail(relatedItemTypeDetail);
        relatedItemType.store();

        sendEvent(relatedItemType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return relatedItemType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.RelatedItemType */
    public RelatedItemType getRelatedItemTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new RelatedItemTypePK(entityInstance.getEntityUniqueId());

        return relatedItemTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public RelatedItemType getRelatedItemTypeByEntityInstance(final EntityInstance entityInstance) {
        return getRelatedItemTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public RelatedItemType getRelatedItemTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getRelatedItemTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public RelatedItemType getRelatedItemTypeByPK(RelatedItemTypePK pk) {
        return relatedItemTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countRelatedItemTypes() {
        return session.getDslContext()
                .selectCount()
                .from(RelatedItemTypes)
                .join(RelatedItemTypeDetails).onKey(RELATED_ITEM_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public RelatedItemType getRelatedItemTypeByName(String relatedItemTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RelatedItemTypes.fields())
                .from(RelatedItemTypes)
                .join(RelatedItemTypeDetails).onKey(RELATED_ITEM_TYPES_ACTIVE_DETAIL_FK)
                .where(RelatedItemTypeDetails.RELATED_ITEM_TYPE_NAME.eq(relatedItemTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return relatedItemTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public RelatedItemType getRelatedItemTypeByName(String relatedItemTypeName) {
        return getRelatedItemTypeByName(relatedItemTypeName, EntityPermission.READ_ONLY);
    }

    public RelatedItemType getRelatedItemTypeByNameForUpdate(String relatedItemTypeName) {
        return getRelatedItemTypeByName(relatedItemTypeName, EntityPermission.READ_WRITE);
    }

    public RelatedItemTypeDetailValue getRelatedItemTypeDetailValueForUpdate(RelatedItemType relatedItemType) {
        return relatedItemType == null? null: relatedItemType.getLastDetailForUpdate().getRelatedItemTypeDetailValue().clone();
    }

    public RelatedItemTypeDetailValue getRelatedItemTypeDetailValueByNameForUpdate(String relatedItemTypeName) {
        return getRelatedItemTypeDetailValueForUpdate(getRelatedItemTypeByNameForUpdate(relatedItemTypeName));
    }

    public RelatedItemType getDefaultRelatedItemType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RelatedItemTypes.fields())
                .from(RelatedItemTypes)
                .join(RelatedItemTypeDetails).onKey(RELATED_ITEM_TYPES_ACTIVE_DETAIL_FK)
                .where(RelatedItemTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return relatedItemTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public RelatedItemType getDefaultRelatedItemType() {
        return getDefaultRelatedItemType(EntityPermission.READ_ONLY);
    }

    public RelatedItemType getDefaultRelatedItemTypeForUpdate() {
        return getDefaultRelatedItemType(EntityPermission.READ_WRITE);
    }

    public RelatedItemTypeDetailValue getDefaultRelatedItemTypeDetailValueForUpdate() {
        return getDefaultRelatedItemTypeForUpdate().getLastDetailForUpdate().getRelatedItemTypeDetailValue().clone();
    }

    private List<RelatedItemType> getRelatedItemTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RelatedItemTypes.fields())
                .from(RelatedItemTypes)
                .join(RelatedItemTypeDetails).onKey(RELATED_ITEM_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RelatedItemTypeDetails.SORT_ORDER, RelatedItemTypeDetails.RELATED_ITEM_TYPE_NAME),
                    RelatedItemTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return relatedItemTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<RelatedItemType> getRelatedItemTypes() {
        return getRelatedItemTypes(EntityPermission.READ_ONLY);
    }

    public List<RelatedItemType> getRelatedItemTypesForUpdate() {
        return getRelatedItemTypes(EntityPermission.READ_WRITE);
    }







    public RelatedItemTypeTransfer getRelatedItemTypeTransfer(UserVisit userVisit, RelatedItemType relatedItemType) {
        return relatedItemTypeTransferCache.getTransfer(userVisit, relatedItemType);
    }

    public List<RelatedItemTypeTransfer> getRelatedItemTypeTransfers(UserVisit userVisit, Collection<RelatedItemType> relatedItemTypes) {
        List<RelatedItemTypeTransfer> relatedItemTypeTransfers = new ArrayList<>(relatedItemTypes.size());

        relatedItemTypes.forEach((relatedItemType) ->
                relatedItemTypeTransfers.add(relatedItemTypeTransferCache.getTransfer(userVisit, relatedItemType))
        );

        return relatedItemTypeTransfers;
    }

    public List<RelatedItemTypeTransfer> getRelatedItemTypeTransfers(UserVisit userVisit) {
        return getRelatedItemTypeTransfers(userVisit, getRelatedItemTypes());
    }



    public RelatedItemTypeChoicesBean getRelatedItemTypeChoices(String defaultRelatedItemTypeChoice, Language language, boolean allowNullChoice) {
        var relatedItemTypes = getRelatedItemTypes();
        var size = relatedItemTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultRelatedItemTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var relatedItemType : relatedItemTypes) {
            var relatedItemTypeDetail = relatedItemType.getLastDetail();

            var label = getBestRelatedItemTypeDescription(relatedItemType, language);
            var value = relatedItemTypeDetail.getRelatedItemTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultRelatedItemTypeChoice != null && defaultRelatedItemTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && relatedItemTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new RelatedItemTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateRelatedItemTypeFromValue(RelatedItemTypeDetailValue relatedItemTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(relatedItemTypeDetailValue.hasBeenModified()) {
            var relatedItemType = relatedItemTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     relatedItemTypeDetailValue.getRelatedItemTypePK());
            var relatedItemTypeDetail = relatedItemType.getActiveDetailForUpdate();

            relatedItemTypeDetail.setThruTime(session.getStartTime());
            relatedItemTypeDetail.store();

            var relatedItemTypePK = relatedItemTypeDetail.getRelatedItemTypePK(); // Not updated
            var relatedItemTypeName = relatedItemTypeDetailValue.getRelatedItemTypeName();
            var isDefault = relatedItemTypeDetailValue.getIsDefault();
            var sortOrder = relatedItemTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultRelatedItemType = getDefaultRelatedItemType();
                var defaultFound = defaultRelatedItemType != null && !defaultRelatedItemType.equals(relatedItemType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultRelatedItemTypeDetailValue = getDefaultRelatedItemTypeDetailValueForUpdate();

                    defaultRelatedItemTypeDetailValue.setIsDefault(false);
                    updateRelatedItemTypeFromValue(defaultRelatedItemTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            relatedItemTypeDetail = relatedItemTypeDetailFactory.create(relatedItemTypePK,
                    relatedItemTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            relatedItemType.setActiveDetail(relatedItemTypeDetail);
            relatedItemType.setLastDetail(relatedItemTypeDetail);

            sendEvent(relatedItemTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateRelatedItemTypeFromValue(RelatedItemTypeDetailValue relatedItemTypeDetailValue, BasePK updatedBy) {
        updateRelatedItemTypeFromValue(relatedItemTypeDetailValue, true, updatedBy);
    }

    public void deleteRelatedItemType(RelatedItemType relatedItemType, BasePK deletedBy) {
        deleteRelatedItemTypeDescriptionsByRelatedItemType(relatedItemType, deletedBy);
        deleteRelatedItemsByRelatedItemType(relatedItemType, deletedBy);

        var relatedItemTypeDetail = relatedItemType.getLastDetailForUpdate();
        relatedItemTypeDetail.setThruTime(session.getStartTime());
        relatedItemType.setActiveDetail(null);
        relatedItemType.store();

        // Check for default, and pick one if necessary
        var defaultRelatedItemType = getDefaultRelatedItemType();
        if(defaultRelatedItemType == null) {
            var relatedItemTypes = getRelatedItemTypesForUpdate();

            if(!relatedItemTypes.isEmpty()) {
                var iter = relatedItemTypes.iterator();
                if(iter.hasNext()) {
                    defaultRelatedItemType = iter.next();
                }
                var relatedItemTypeDetailValue = Objects.requireNonNull(defaultRelatedItemType).getLastDetailForUpdate().getRelatedItemTypeDetailValue().clone();

                relatedItemTypeDetailValue.setIsDefault(true);
                updateRelatedItemTypeFromValue(relatedItemTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(relatedItemType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected RelatedItemTypeDescriptionFactory relatedItemTypeDescriptionFactory;

    @Inject
    RelatedItemTypeDescriptionTransferCache relatedItemTypeDescriptionTransferCache;

    public RelatedItemTypeDescription createRelatedItemTypeDescription(RelatedItemType relatedItemType,
            Language language, String description, BasePK createdBy) {
        var relatedItemTypeDescription = relatedItemTypeDescriptionFactory.create(relatedItemType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(relatedItemType.getPrimaryKey(), EventTypes.MODIFY, relatedItemTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return relatedItemTypeDescription;
    }

    private RelatedItemTypeDescription getRelatedItemTypeDescription(RelatedItemType relatedItemType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RelatedItemTypeDescriptions.fields())
                .from(RelatedItemTypeDescriptions)
                .where(RelatedItemTypeDescriptions.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                        RelatedItemTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        RelatedItemTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return relatedItemTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public RelatedItemTypeDescription getRelatedItemTypeDescription(RelatedItemType relatedItemType, Language language) {
        return getRelatedItemTypeDescription(relatedItemType, language, EntityPermission.READ_ONLY);
    }

    public RelatedItemTypeDescription getRelatedItemTypeDescriptionForUpdate(RelatedItemType relatedItemType, Language language) {
        return getRelatedItemTypeDescription(relatedItemType, language, EntityPermission.READ_WRITE);
    }

    public RelatedItemTypeDescriptionValue getRelatedItemTypeDescriptionValue(RelatedItemTypeDescription relatedItemTypeDescription) {
        return relatedItemTypeDescription == null? null: relatedItemTypeDescription.getRelatedItemTypeDescriptionValue().clone();
    }

    public RelatedItemTypeDescriptionValue getRelatedItemTypeDescriptionValueForUpdate(RelatedItemType relatedItemType, Language language) {
        return getRelatedItemTypeDescriptionValue(getRelatedItemTypeDescriptionForUpdate(relatedItemType, language));
    }

    private List<RelatedItemTypeDescription> getRelatedItemTypeDescriptionsByRelatedItemType(RelatedItemType relatedItemType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RelatedItemTypeDescriptions.fields())
                    .from(RelatedItemTypeDescriptions)
                    .join(Languages).onKey(RELATED_ITEM_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(RelatedItemTypeDescriptions.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                            RelatedItemTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    RelatedItemTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RelatedItemTypeDescriptions.fields())
                    .from(RelatedItemTypeDescriptions)
                    .where(RelatedItemTypeDescriptions.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                            RelatedItemTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return relatedItemTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<RelatedItemTypeDescription> getRelatedItemTypeDescriptionsByRelatedItemType(RelatedItemType relatedItemType) {
        return getRelatedItemTypeDescriptionsByRelatedItemType(relatedItemType, EntityPermission.READ_ONLY);
    }

    public List<RelatedItemTypeDescription> getRelatedItemTypeDescriptionsByRelatedItemTypeForUpdate(RelatedItemType relatedItemType) {
        return getRelatedItemTypeDescriptionsByRelatedItemType(relatedItemType, EntityPermission.READ_WRITE);
    }

    public String getBestRelatedItemTypeDescription(RelatedItemType relatedItemType, Language language) {
        String description;
        var relatedItemTypeDescription = getRelatedItemTypeDescription(relatedItemType, language);

        if(relatedItemTypeDescription == null && !language.getIsDefault()) {
            relatedItemTypeDescription = getRelatedItemTypeDescription(relatedItemType, partyControl.getDefaultLanguage());
        }

        if(relatedItemTypeDescription == null) {
            description = relatedItemType.getLastDetail().getRelatedItemTypeName();
        } else {
            description = relatedItemTypeDescription.getDescription();
        }

        return description;
    }

    public RelatedItemTypeDescriptionTransfer getRelatedItemTypeDescriptionTransfer(UserVisit userVisit, RelatedItemTypeDescription relatedItemTypeDescription) {
        return relatedItemTypeDescriptionTransferCache.getTransfer(userVisit, relatedItemTypeDescription);
    }

    public List<RelatedItemTypeDescriptionTransfer> getRelatedItemTypeDescriptionTransfersByRelatedItemType(UserVisit userVisit, RelatedItemType relatedItemType) {
        var relatedItemTypeDescriptions = getRelatedItemTypeDescriptionsByRelatedItemType(relatedItemType);
        List<RelatedItemTypeDescriptionTransfer> relatedItemTypeDescriptionTransfers = new ArrayList<>(relatedItemTypeDescriptions.size());

        relatedItemTypeDescriptions.forEach((relatedItemTypeDescription) ->
                relatedItemTypeDescriptionTransfers.add(relatedItemTypeDescriptionTransferCache.getTransfer(userVisit, relatedItemTypeDescription))
        );

        return relatedItemTypeDescriptionTransfers;
    }

    public void updateRelatedItemTypeDescriptionFromValue(RelatedItemTypeDescriptionValue relatedItemTypeDescriptionValue, BasePK updatedBy) {
        if(relatedItemTypeDescriptionValue.hasBeenModified()) {
            var relatedItemTypeDescription = relatedItemTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    relatedItemTypeDescriptionValue.getPrimaryKey());

            relatedItemTypeDescription.setThruTime(session.getStartTime());
            relatedItemTypeDescription.store();

            var relatedItemType = relatedItemTypeDescription.getRelatedItemType();
            var language = relatedItemTypeDescription.getLanguage();
            var description = relatedItemTypeDescriptionValue.getDescription();

            relatedItemTypeDescription = relatedItemTypeDescriptionFactory.create(relatedItemType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(relatedItemType.getPrimaryKey(), EventTypes.MODIFY, relatedItemTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteRelatedItemTypeDescription(RelatedItemTypeDescription relatedItemTypeDescription, BasePK deletedBy) {
        relatedItemTypeDescription.setThruTime(session.getStartTime());

        sendEvent(relatedItemTypeDescription.getRelatedItemTypePK(), EventTypes.MODIFY, relatedItemTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteRelatedItemTypeDescriptionsByRelatedItemType(RelatedItemType relatedItemType, BasePK deletedBy) {
        var relatedItemTypeDescriptions = getRelatedItemTypeDescriptionsByRelatedItemTypeForUpdate(relatedItemType);

        relatedItemTypeDescriptions.forEach((relatedItemTypeDescription) -> 
                deleteRelatedItemTypeDescription(relatedItemTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------

    @Inject
    protected RelatedItemDetailFactory relatedItemDetailFactory;

    @Inject
    protected RelatedItemFactory relatedItemFactory;

    @Inject
    RelatedItemTransferCache relatedItemTransferCache;

    public RelatedItem createRelatedItem(RelatedItemType relatedItemType, Item fromItem, Item toItem, Integer sortOrder, BasePK createdBy) {
        var relatedItem = relatedItemFactory.create();
        var relatedItemDetail = relatedItemDetailFactory.create(relatedItem, relatedItemType, fromItem, toItem, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        relatedItem = relatedItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                relatedItem.getPrimaryKey());
        relatedItem.setActiveDetail(relatedItemDetail);
        relatedItem.setLastDetail(relatedItemDetail);
        relatedItem.store();

        sendEvent(relatedItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return relatedItem;
    }

    private RelatedItem getRelatedItem(RelatedItemType relatedItemType, Item fromItem, Item toItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RelatedItems.fields())
                .from(RelatedItems)
                .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                        RelatedItemDetails.FROM_ITEM.eq(fromItem.getPrimaryKey()),
                        RelatedItemDetails.TO_ITEM.eq(toItem.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return relatedItemFactory.getEntityFromQuery(entityPermission, query);
    }

    public RelatedItem getRelatedItem(RelatedItemType relatedItemType, Item fromItem, Item toItem) {
        return getRelatedItem(relatedItemType, fromItem, toItem, EntityPermission.READ_ONLY);
    }

    public RelatedItem getRelatedItemForUpdate(RelatedItemType relatedItemType, Item fromItem, Item toItem) {
        return getRelatedItem(relatedItemType, fromItem, toItem, EntityPermission.READ_WRITE);
    }

    public RelatedItemDetailValue getRelatedItemDetailValueForUpdate(RelatedItem relatedItem) {
        return relatedItem == null? null: relatedItem.getLastDetailForUpdate().getRelatedItemDetailValue().clone();
    }

    public RelatedItemDetailValue getRelatedItemDetailValueForUpdate(RelatedItemType relatedItemType, Item fromItem, Item toItem) {
        return getRelatedItemDetailValueForUpdate(getRelatedItemForUpdate(relatedItemType, fromItem, toItem));
    }

    private List<RelatedItem> getRelatedItemsByRelatedItemType(RelatedItemType relatedItemType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(RELATED_ITEM_DETAILS_FROM_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()))
                    .orderBy(RelatedItemDetails.SORT_ORDER, ItemDetails.ITEM_NAME),
                    RelatedItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()))
                    .forUpdate();
        };

        return relatedItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemType(RelatedItemType relatedItemType) {
        return getRelatedItemsByRelatedItemType(relatedItemType, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeForUpdate(RelatedItemType relatedItemType) {
        return getRelatedItemsByRelatedItemType(relatedItemType, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByRelatedItemType(RelatedItemType relatedItemType) {
        return session.getDslContext()
                .selectCount()
                .from(RelatedItems)
                .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<RelatedItem> getRelatedItemsByRelatedItemTypeAndFromItem(RelatedItemType relatedItemType, Item fromItem, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(RELATED_ITEM_DETAILS_FROM_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                            RelatedItemDetails.FROM_ITEM.eq(fromItem.getPrimaryKey()))
                    .orderBy(RelatedItemDetails.SORT_ORDER, ItemDetails.ITEM_NAME),
                    RelatedItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                            RelatedItemDetails.FROM_ITEM.eq(fromItem.getPrimaryKey()))
                    .forUpdate();
        };

        return relatedItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndFromItem(RelatedItemType relatedItemType, Item fromItem) {
        return getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndFromItemForUpdate(RelatedItemType relatedItemType, Item fromItem) {
        return getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByRelatedItemTypeAndFromItem(RelatedItemType relatedItemType, Item fromItem) {
        return session.getDslContext()
                .selectCount()
                .from(RelatedItems)
                .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                        RelatedItemDetails.FROM_ITEM.eq(fromItem.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<RelatedItem> getRelatedItemsByRelatedItemTypeAndToItem(RelatedItemType relatedItemType, Item toItem, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(RELATED_ITEM_DETAILS_TO_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                            RelatedItemDetails.TO_ITEM.eq(toItem.getPrimaryKey()))
                    .orderBy(RelatedItemDetails.SORT_ORDER, ItemDetails.ITEM_NAME),
                    RelatedItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                            RelatedItemDetails.TO_ITEM.eq(toItem.getPrimaryKey()))
                    .forUpdate();
        };

        return relatedItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndToItem(RelatedItemType relatedItemType, Item toItem) {
        return getRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndToItemForUpdate(RelatedItemType relatedItemType, Item toItem) {
        return getRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByRelatedItemTypeAndToItem(RelatedItemType relatedItemType, Item toItem) {
        return session.getDslContext()
                .selectCount()
                .from(RelatedItems)
                .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                .where(RelatedItemDetails.RELATED_ITEM_TYPE.eq(relatedItemType.getPrimaryKey()),
                        RelatedItemDetails.TO_ITEM.eq(toItem.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<RelatedItem> getRelatedItemsByFromItem(Item fromItem, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .join(RelatedItemTypes).onKey(RELATED_ITEM_DETAILS_RELATED_ITEM_TYPE_FK)
                    .join(RelatedItemTypeDetails).onKey(RELATED_ITEM_TYPES_LAST_DETAIL_FK)
                    .join(Items).onKey(RELATED_ITEM_DETAILS_FROM_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(RelatedItemDetails.FROM_ITEM.eq(fromItem.getPrimaryKey()))
                    .orderBy(RelatedItemTypeDetails.SORT_ORDER, RelatedItemTypeDetails.RELATED_ITEM_TYPE_NAME, RelatedItemDetails.SORT_ORDER, ItemDetails.ITEM_NAME),
                    RelatedItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .where(RelatedItemDetails.FROM_ITEM.eq(fromItem.getPrimaryKey()))
                    .forUpdate();
        };

        return relatedItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<RelatedItem> getRelatedItemsByFromItem(Item fromItem) {
        return getRelatedItemsByFromItem(fromItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByFromItemForUpdate(Item fromItem) {
        return getRelatedItemsByFromItem(fromItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByFromItem(Item fromItem) {
        return session.getDslContext()
                .selectCount()
                .from(RelatedItems)
                .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                .where(RelatedItemDetails.FROM_ITEM.eq(fromItem.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<RelatedItem> getRelatedItemsByToItem(Item toItem, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .join(RelatedItemTypes).onKey(RELATED_ITEM_DETAILS_RELATED_ITEM_TYPE_FK)
                    .join(RelatedItemTypeDetails).onKey(RELATED_ITEM_TYPES_LAST_DETAIL_FK)
                    .join(Items).onKey(RELATED_ITEM_DETAILS_TO_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(RelatedItemDetails.TO_ITEM.eq(toItem.getPrimaryKey()))
                    .orderBy(RelatedItemTypeDetails.SORT_ORDER, RelatedItemTypeDetails.RELATED_ITEM_TYPE_NAME, RelatedItemDetails.SORT_ORDER, ItemDetails.ITEM_NAME),
                    RelatedItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RelatedItems.fields())
                    .from(RelatedItems)
                    .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                    .where(RelatedItemDetails.TO_ITEM.eq(toItem.getPrimaryKey()))
                    .forUpdate();
        };

        return relatedItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<RelatedItem> getRelatedItemsByToItem(Item toItem) {
        return getRelatedItemsByToItem(toItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByToItemForUpdate(Item toItem) {
        return getRelatedItemsByToItem(toItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByToItem(Item toItem) {
        return session.getDslContext()
                .selectCount()
                .from(RelatedItems)
                .join(RelatedItemDetails).onKey(RELATED_ITEMS_ACTIVE_DETAIL_FK)
                .where(RelatedItemDetails.TO_ITEM.eq(toItem.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public RelatedItemTransfer getRelatedItemTransfer(UserVisit userVisit, RelatedItem relatedItem) {
        return relatedItemTransferCache.getTransfer(userVisit, relatedItem);
    }

    public List<RelatedItemTransfer> getRelatedItemTransfers(UserVisit userVisit, Collection<RelatedItem> relatedItems) {
        List<RelatedItemTransfer> relatedItemTransfers = new ArrayList<>(relatedItems.size());

        relatedItems.forEach((relatedItem) ->
                relatedItemTransfers.add(relatedItemTransferCache.getTransfer(userVisit, relatedItem))
        );

        return relatedItemTransfers;
    }

    public List<RelatedItemTransfer> getRelatedItemTransfersByRelatedItemTypeAndFromItem(UserVisit userVisit, RelatedItemType relatedItemType, Item fromItem) {
        return getRelatedItemTransfers(userVisit, getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem));
    }

    public List<RelatedItemTransfer> getRelatedItemTransfersByRelatedItemTypeAndToItem(UserVisit userVisit, RelatedItemType relatedItemType, Item toItem) {
        return getRelatedItemTransfers(userVisit, getRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem));
    }

    public List<RelatedItemTransfer> getRelatedItemTransfersByFromItem(UserVisit userVisit, Item fromItem) {
        return getRelatedItemTransfers(userVisit, getRelatedItemsByFromItem(fromItem));
    }

    public List<RelatedItemTransfer> getRelatedItemTransfersByToItem(UserVisit userVisit, Item toItem) {
        return getRelatedItemTransfers(userVisit, getRelatedItemsByToItem(toItem));
    }

    public void updateRelatedItemFromValue(RelatedItemDetailValue relatedItemDetailValue, BasePK updatedBy) {
        if(relatedItemDetailValue.hasBeenModified()) {
            var relatedItem = relatedItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     relatedItemDetailValue.getRelatedItemPK());
            var relatedItemDetail = relatedItem.getActiveDetailForUpdate();

            relatedItemDetail.setThruTime(session.getStartTime());
            relatedItemDetail.store();

            var relatedItemPK = relatedItemDetail.getRelatedItemPK(); // Not updated
            var relatedItemTypePK = relatedItemDetail.getRelatedItemTypePK(); // Not updated
            var fromItemPK = relatedItemDetail.getFromItemPK(); // Not updated
            var toItemPK = relatedItemDetail.getToItemPK(); // Not updated
            var sortOrder = relatedItemDetailValue.getSortOrder();

            relatedItemDetail = relatedItemDetailFactory.create(relatedItemPK, relatedItemTypePK, fromItemPK, toItemPK, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            relatedItem.setActiveDetail(relatedItemDetail);
            relatedItem.setLastDetail(relatedItemDetail);

            sendEvent(relatedItemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteRelatedItem(RelatedItem relatedItem, BasePK deletedBy) {
        var relatedItemDetail = relatedItem.getLastDetailForUpdate();
        relatedItemDetail.setThruTime(session.getStartTime());
        relatedItem.setActiveDetail(null);
        relatedItem.store();

        sendEvent(relatedItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteRelatedItems(List<RelatedItem> relatedItems, BasePK deletedBy) {
        relatedItems.forEach((relatedItem) -> 
                deleteRelatedItem(relatedItem, deletedBy)
        );
    }

    public void deleteRelatedItemsByFromItem(Item fromItem, BasePK deletedBy) {
        deleteRelatedItems(getRelatedItemsByFromItemForUpdate(fromItem), deletedBy);
    }

    public void deleteRelatedItemsByRelatedItemType(RelatedItemType relatedItemType, BasePK deletedBy) {
        deleteRelatedItems(getRelatedItemsByRelatedItemTypeForUpdate(relatedItemType), deletedBy);
    }

    public void deleteRelatedItemsByToItem(Item toItem, BasePK deletedBy) {
        deleteRelatedItems(getRelatedItemsByToItemForUpdate(toItem), deletedBy);
    }

    public void deleteRelatedItemsByItem(Item item, BasePK deletedBy) {
        deleteRelatedItemsByFromItem(item, deletedBy);
        deleteRelatedItemsByToItem(item, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Codes
    // --------------------------------------------------------------------------------

    @Inject
    protected HarmonizedTariffScheduleCodeDetailFactory harmonizedTariffScheduleCodeDetailFactory;

    @Inject
    protected HarmonizedTariffScheduleCodeFactory harmonizedTariffScheduleCodeFactory;

    @Inject
    HarmonizedTariffScheduleCodeTransferCache harmonizedTariffScheduleCodeTransferCache;

    public HarmonizedTariffScheduleCode createHarmonizedTariffScheduleCode(GeoCode countryGeoCode, String harmonizedTariffScheduleCodeName,
            HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit, HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultHarmonizedTariffScheduleCode = getDefaultHarmonizedTariffScheduleCode(countryGeoCode);
        var defaultFound = defaultHarmonizedTariffScheduleCode != null;

        if(defaultFound && isDefault) {
            var defaultHarmonizedTariffScheduleCodeDetailValue = getDefaultHarmonizedTariffScheduleCodeDetailValueForUpdate(countryGeoCode);

            defaultHarmonizedTariffScheduleCodeDetailValue.setIsDefault(false);
            updateHarmonizedTariffScheduleCodeFromValue(defaultHarmonizedTariffScheduleCodeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var harmonizedTariffScheduleCode = harmonizedTariffScheduleCodeFactory.create();
        var harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCodeDetailFactory.create(
                harmonizedTariffScheduleCode, countryGeoCode, harmonizedTariffScheduleCodeName, firstHarmonizedTariffScheduleCodeUnit,
                secondHarmonizedTariffScheduleCodeUnit, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        harmonizedTariffScheduleCode = harmonizedTariffScheduleCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCode.getPrimaryKey());
        harmonizedTariffScheduleCode.setActiveDetail(harmonizedTariffScheduleCodeDetail);
        harmonizedTariffScheduleCode.setLastDetail(harmonizedTariffScheduleCodeDetail);
        harmonizedTariffScheduleCode.store();

        sendEvent(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return harmonizedTariffScheduleCode;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.HarmonizedTariffScheduleCode */
    public HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new HarmonizedTariffScheduleCodePK(entityInstance.getEntityUniqueId());

        return harmonizedTariffScheduleCodeFactory.getEntityFromPK(entityPermission, pk);
    }

    public HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByEntityInstance(EntityInstance entityInstance) {
        return getHarmonizedTariffScheduleCodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getHarmonizedTariffScheduleCodeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public long countHarmonizedTariffScheduleCodes() {
        return session.getDslContext()
                .selectCount()
                .from(HarmonizedTariffScheduleCodes)
                .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(HarmonizedTariffScheduleCodes)
                .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodes.fields())
                .from(HarmonizedTariffScheduleCodes)
                .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(HarmonizedTariffScheduleCodeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME),
                    HarmonizedTariffScheduleCodeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode) {
        return getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodes.fields())
                .from(HarmonizedTariffScheduleCodes)
                .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeDetails.FIRST_HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT.eq(firstHarmonizedTariffScheduleCodeUnit.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(HarmonizedTariffScheduleCodeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME),
                    HarmonizedTariffScheduleCodeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(firstHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnitForUpdate(HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(firstHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_WRITE);
    }

    private List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodes.fields())
                .from(HarmonizedTariffScheduleCodes)
                .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeDetails.SECOND_HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT.eq(secondHarmonizedTariffScheduleCodeUnit.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(HarmonizedTariffScheduleCodeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME),
                    HarmonizedTariffScheduleCodeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(secondHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnitForUpdate(HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(secondHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_WRITE);
    }

    private HarmonizedTariffScheduleCode getDefaultHarmonizedTariffScheduleCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodes.fields())
                .from(HarmonizedTariffScheduleCodes)
                .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCode getDefaultHarmonizedTariffScheduleCode(GeoCode countryGeoCode) {
        return getDefaultHarmonizedTariffScheduleCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCode getDefaultHarmonizedTariffScheduleCodeForUpdate(GeoCode countryGeoCode) {
        return getDefaultHarmonizedTariffScheduleCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeDetailValue getDefaultHarmonizedTariffScheduleCodeDetailValueForUpdate(GeoCode countryGeoCode) {
        return getDefaultHarmonizedTariffScheduleCodeForUpdate(countryGeoCode).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeDetailValue().clone();
    }

    private HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByName(GeoCode countryGeoCode, String harmonizedTariffScheduleCodeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodes.fields())
                .from(HarmonizedTariffScheduleCodes)
                .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME.eq(harmonizedTariffScheduleCodeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByName(GeoCode countryGeoCode, String harmonizedTariffScheduleCodeName) {
        return getHarmonizedTariffScheduleCodeByName(countryGeoCode, harmonizedTariffScheduleCodeName, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByNameForUpdate(GeoCode countryGeoCode, String harmonizedTariffScheduleCodeName) {
        return getHarmonizedTariffScheduleCodeByName(countryGeoCode, harmonizedTariffScheduleCodeName, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeDetailValue getHarmonizedTariffScheduleCodeDetailValueForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return harmonizedTariffScheduleCode == null? null: harmonizedTariffScheduleCode.getLastDetailForUpdate().getHarmonizedTariffScheduleCodeDetailValue().clone();
    }

    public HarmonizedTariffScheduleCodeDetailValue getHarmonizedTariffScheduleCodeDetailValueByNameForUpdate(GeoCode countryGeoCode, String harmonizedTariffScheduleCodeName) {
        return getHarmonizedTariffScheduleCodeDetailValueForUpdate(getHarmonizedTariffScheduleCodeByNameForUpdate(countryGeoCode, harmonizedTariffScheduleCodeName));
    }

    public HarmonizedTariffScheduleCodeChoicesBean getHarmonizedTariffScheduleCodeChoices(String defaultHarmonizedTariffScheduleCodeChoice, Language language, boolean allowNullChoice, GeoCode countryGeoCode) {
        var harmonizedTariffScheduleCodees = getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);
        var size = harmonizedTariffScheduleCodees.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultHarmonizedTariffScheduleCodeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var harmonizedTariffScheduleCode : harmonizedTariffScheduleCodees) {
            var harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();
            var harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName();
            var harmonizedTariffScheduleCodeTranslation = getBestHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language);
            
            var label = harmonizedTariffScheduleCodeTranslation == null ? harmonizedTariffScheduleCodeName : harmonizedTariffScheduleCodeTranslation.getDescription();
            var value = harmonizedTariffScheduleCodeName;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultHarmonizedTariffScheduleCodeChoice != null && defaultHarmonizedTariffScheduleCodeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && harmonizedTariffScheduleCodeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new HarmonizedTariffScheduleCodeChoicesBean(labels, values, defaultValue);
    }
    
    public HarmonizedTariffScheduleCodeTransfer getHarmonizedTariffScheduleCodeTransfer(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return harmonizedTariffScheduleCodeTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCode);
    }

    public List<HarmonizedTariffScheduleCodeTransfer> getHarmonizedTariffScheduleCodeTransfers(UserVisit userVisit, Collection<HarmonizedTariffScheduleCode> harmonizedTariffScheduleCodes) {
        List<HarmonizedTariffScheduleCodeTransfer> harmonizedTariffScheduleCodeTransfers = new ArrayList<>(harmonizedTariffScheduleCodes.size());

        harmonizedTariffScheduleCodes.forEach((harmonizedTariffScheduleCode) ->
                harmonizedTariffScheduleCodeTransfers.add(harmonizedTariffScheduleCodeTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCode))
        );

        return harmonizedTariffScheduleCodeTransfers;
    }

    public List<HarmonizedTariffScheduleCodeTransfer> getHarmonizedTariffScheduleCodeTransfersByCountryGeoCode(UserVisit userVisit, GeoCode countryGeoCode) {
        return getHarmonizedTariffScheduleCodeTransfers(userVisit, getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode));
    }

    private void updateHarmonizedTariffScheduleCodeFromValue(HarmonizedTariffScheduleCodeDetailValue harmonizedTariffScheduleCodeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeDetailValue.hasBeenModified()) {
            var harmonizedTariffScheduleCode = harmonizedTariffScheduleCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodePK());
            var harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getActiveDetailForUpdate();

            harmonizedTariffScheduleCodeDetail.setThruTime(session.getStartTime());
            harmonizedTariffScheduleCodeDetail.store();

            var harmonizedTariffScheduleCodePK = harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodePK();
            var countryGeoCode = harmonizedTariffScheduleCodeDetail.getCountryGeoCode();
            var countryGeoCodePK = countryGeoCode.getPrimaryKey();
            var harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodeName();
            var firstHarmonizedTariffScheduleCodeUnitPK = harmonizedTariffScheduleCodeDetailValue.getFirstHarmonizedTariffScheduleCodeUnitPK();
            var secondHarmonizedTariffScheduleCodeUnitPK = harmonizedTariffScheduleCodeDetailValue.getSecondHarmonizedTariffScheduleCodeUnitPK();
            var isDefault = harmonizedTariffScheduleCodeDetailValue.getIsDefault();
            var sortOrder = harmonizedTariffScheduleCodeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultHarmonizedTariffScheduleCode = getDefaultHarmonizedTariffScheduleCode(countryGeoCode);
                var defaultFound = defaultHarmonizedTariffScheduleCode != null && !defaultHarmonizedTariffScheduleCode.equals(harmonizedTariffScheduleCode);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultHarmonizedTariffScheduleCodeDetailValue = getDefaultHarmonizedTariffScheduleCodeDetailValueForUpdate(countryGeoCode);

                    defaultHarmonizedTariffScheduleCodeDetailValue.setIsDefault(false);
                    updateHarmonizedTariffScheduleCodeFromValue(defaultHarmonizedTariffScheduleCodeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCodeDetailFactory.create(harmonizedTariffScheduleCodePK,
                    countryGeoCodePK, harmonizedTariffScheduleCodeName, firstHarmonizedTariffScheduleCodeUnitPK, secondHarmonizedTariffScheduleCodeUnitPK, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            harmonizedTariffScheduleCode.setActiveDetail(harmonizedTariffScheduleCodeDetail);
            harmonizedTariffScheduleCode.setLastDetail(harmonizedTariffScheduleCodeDetail);

            sendEvent(harmonizedTariffScheduleCodePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateHarmonizedTariffScheduleCodeFromValue(HarmonizedTariffScheduleCodeDetailValue harmonizedTariffScheduleCodeDetailValue, BasePK updatedBy) {
        updateHarmonizedTariffScheduleCodeFromValue(harmonizedTariffScheduleCodeDetailValue, true, updatedBy);
    }

    public void deleteHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, BasePK deletedBy) {
        deleteItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, deletedBy);
        deleteHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, deletedBy);
        deleteHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, deletedBy);

        var harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetailForUpdate();
        harmonizedTariffScheduleCodeDetail.setThruTime(session.getStartTime());
        harmonizedTariffScheduleCode.setActiveDetail(null);
        harmonizedTariffScheduleCode.store();

        // Check for default, and pick one if necessary
        var countryGeoCode = harmonizedTariffScheduleCodeDetail.getCountryGeoCode();
        var defaultHarmonizedTariffScheduleCode = getDefaultHarmonizedTariffScheduleCode(countryGeoCode);
        if(defaultHarmonizedTariffScheduleCode == null) {
            var harmonizedTariffScheduleCodes = getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);

            if(!harmonizedTariffScheduleCodes.isEmpty()) {
                var iter = harmonizedTariffScheduleCodes.iterator();
                if(iter.hasNext()) {
                    defaultHarmonizedTariffScheduleCode = iter.next();
                }
                var harmonizedTariffScheduleCodeDetailValue = Objects.requireNonNull(defaultHarmonizedTariffScheduleCode).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeDetailValue().clone();

                harmonizedTariffScheduleCodeDetailValue.setIsDefault(true);
                updateHarmonizedTariffScheduleCodeFromValue(harmonizedTariffScheduleCodeDetailValue, false, deletedBy);
            }
        }

        sendEvent(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteHarmonizedTariffScheduleCodes(List<HarmonizedTariffScheduleCode> harmonizedTariffScheduleCodes, BasePK deletedBy) {
        harmonizedTariffScheduleCodes.forEach((harmonizedTariffScheduleCode) -> 
                deleteHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, deletedBy)
        );
    }

    public void deleteHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodes(getHarmonizedTariffScheduleCodesByCountryGeoCodeForUpdate(countryGeoCode), deletedBy);
    }
    
    public void deleteHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodes(getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnitForUpdate(firstHarmonizedTariffScheduleCodeUnit), deletedBy);
    }

    public void deleteHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodes(getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnitForUpdate(secondHarmonizedTariffScheduleCodeUnit), deletedBy);
    }

    public void deleteHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, deletedBy);
        deleteHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected HarmonizedTariffScheduleCodeTranslationFactory harmonizedTariffScheduleCodeTranslationFactory;

    @Inject
    HarmonizedTariffScheduleCodeTranslationTransferCache harmonizedTariffScheduleCodeTranslationTransferCache;

    public HarmonizedTariffScheduleCodeTranslation createHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            Language language, String description, MimeType overviewMimeType, String overview, BasePK createdBy) {
        var harmonizedTariffScheduleCodeTranslation = harmonizedTariffScheduleCodeTranslationFactory.create(harmonizedTariffScheduleCode,
                language, description, overviewMimeType, overview, session.getStartTime(), Session.MAX_TIME);

        sendEvent(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeTranslation;
    }

    private HarmonizedTariffScheduleCodeTranslation getHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeTranslations.fields())
                .from(HarmonizedTariffScheduleCodeTranslations)
                .where(HarmonizedTariffScheduleCodeTranslations.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeTranslationFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeTranslation getHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, Language language) {
        return getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeTranslation getHarmonizedTariffScheduleCodeTranslationForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, Language language) {
        return getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeTranslationValue getHarmonizedTariffScheduleCodeTranslationValue(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        return harmonizedTariffScheduleCodeTranslation == null? null: harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCodeTranslationValue().clone();
    }

    public HarmonizedTariffScheduleCodeTranslationValue getHarmonizedTariffScheduleCodeTranslationValueForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, Language language) {
        return getHarmonizedTariffScheduleCodeTranslationValue(getHarmonizedTariffScheduleCodeTranslationForUpdate(harmonizedTariffScheduleCode, language));
    }

    private List<HarmonizedTariffScheduleCodeTranslation> getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeTranslations.fields())
                    .from(HarmonizedTariffScheduleCodeTranslations)
                    .join(Languages).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_TRANSLATIONS_LANGUAGE_FK)
                    .where(HarmonizedTariffScheduleCodeTranslations.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    HarmonizedTariffScheduleCodeTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeTranslations.fields())
                    .from(HarmonizedTariffScheduleCodeTranslations)
                    .where(HarmonizedTariffScheduleCodeTranslations.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return harmonizedTariffScheduleCodeTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCodeTranslation> getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeTranslation> getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCodeForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeTranslation getBestHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, Language language) {
        var harmonizedTariffScheduleCodeTranslation = getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language);
        
        if(harmonizedTariffScheduleCodeTranslation == null && !language.getIsDefault()) {
            harmonizedTariffScheduleCodeTranslation = getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, partyControl.getDefaultLanguage());
        }
        
        return harmonizedTariffScheduleCodeTranslation;
    }
    
    public HarmonizedTariffScheduleCodeTranslationTransfer getHarmonizedTariffScheduleCodeTranslationTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        return harmonizedTariffScheduleCodeTranslationTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeTranslation);
    }

    public List<HarmonizedTariffScheduleCodeTranslationTransfer> getHarmonizedTariffScheduleCodeTranslationTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        var harmonizedTariffScheduleCodeTranslations = getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode);
        List<HarmonizedTariffScheduleCodeTranslationTransfer> harmonizedTariffScheduleCodeTranslationTransfers = new ArrayList<>(harmonizedTariffScheduleCodeTranslations.size());

        harmonizedTariffScheduleCodeTranslations.forEach((harmonizedTariffScheduleCodeTranslation) -> {
            harmonizedTariffScheduleCodeTranslationTransfers.add(harmonizedTariffScheduleCodeTranslationTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeTranslation));
        });

        return harmonizedTariffScheduleCodeTranslationTransfers;
    }

    public void updateHarmonizedTariffScheduleCodeTranslationFromValue(HarmonizedTariffScheduleCodeTranslationValue harmonizedTariffScheduleCodeTranslationValue, BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeTranslationValue.hasBeenModified()) {
            var harmonizedTariffScheduleCodeTranslation = harmonizedTariffScheduleCodeTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeTranslationValue.getPrimaryKey());

            harmonizedTariffScheduleCodeTranslation.setThruTime(session.getStartTime());
            harmonizedTariffScheduleCodeTranslation.store();

            var harmonizedTariffScheduleCodePK = harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCodePK();
            var languagePK = harmonizedTariffScheduleCodeTranslation.getLanguagePK();
            var description = harmonizedTariffScheduleCodeTranslationValue.getDescription();
            var overviewMimeTypePK = harmonizedTariffScheduleCodeTranslationValue.getOverviewMimeTypePK();
            var overview = harmonizedTariffScheduleCodeTranslationValue.getOverview();

            harmonizedTariffScheduleCodeTranslation = harmonizedTariffScheduleCodeTranslationFactory.create(harmonizedTariffScheduleCodePK,
                    languagePK, description, overviewMimeTypePK, overview, session.getStartTime(), Session.MAX_TIME);

            sendEvent(harmonizedTariffScheduleCodePK, EventTypes.MODIFY, harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation, BasePK deletedBy) {
        harmonizedTariffScheduleCodeTranslation.setThruTime(session.getStartTime());

        sendEvent(harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCodePK(), EventTypes.MODIFY, harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, BasePK deletedBy) {
        var harmonizedTariffScheduleCodeTranslations = getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCodeForUpdate(harmonizedTariffScheduleCode);

        harmonizedTariffScheduleCodeTranslations.forEach((harmonizedTariffScheduleCodeTranslation) -> 
                deleteHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCodeTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected HarmonizedTariffScheduleCodeUseTypeDetailFactory harmonizedTariffScheduleCodeUseTypeDetailFactory;

    @Inject
    protected HarmonizedTariffScheduleCodeUseTypeFactory harmonizedTariffScheduleCodeUseTypeFactory;

    @Inject
    HarmonizedTariffScheduleCodeUseTypeTransferCache harmonizedTariffScheduleCodeUseTypeTransferCache;

    public HarmonizedTariffScheduleCodeUseType createHarmonizedTariffScheduleCodeUseType(String harmonizedTariffScheduleCodeUseTypeName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultHarmonizedTariffScheduleCodeUseType = getDefaultHarmonizedTariffScheduleCodeUseType();
        var defaultFound = defaultHarmonizedTariffScheduleCodeUseType != null;

        if(defaultFound && isDefault) {
            var defaultHarmonizedTariffScheduleCodeUseTypeDetailValue = getDefaultHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate();

            defaultHarmonizedTariffScheduleCodeUseTypeDetailValue.setIsDefault(false);
            updateHarmonizedTariffScheduleCodeUseTypeFromValue(defaultHarmonizedTariffScheduleCodeUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseTypeFactory.create();
        var harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseTypeDetailFactory.create(harmonizedTariffScheduleCodeUseType,
                harmonizedTariffScheduleCodeUseTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCodeUseType.getPrimaryKey());
        harmonizedTariffScheduleCodeUseType.setActiveDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.setLastDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.store();

        sendEvent(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return harmonizedTariffScheduleCodeUseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.HarmonizedTariffScheduleCodeUseType */
    public HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new HarmonizedTariffScheduleCodeUseTypePK(entityInstance.getEntityUniqueId());

        return harmonizedTariffScheduleCodeUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getHarmonizedTariffScheduleCodeUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getHarmonizedTariffScheduleCodeUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countHarmonizedTariffScheduleCodeUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(HarmonizedTariffScheduleCodeUseTypes)
                .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByName(String harmonizedTariffScheduleCodeUseTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUseTypes.fields())
                .from(HarmonizedTariffScheduleCodeUseTypes)
                .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeUseTypeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME.eq(harmonizedTariffScheduleCodeUseTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByName(String harmonizedTariffScheduleCodeUseTypeName) {
        return getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByNameForUpdate(String harmonizedTariffScheduleCodeUseTypeName) {
        return getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUseTypeDetailValue getHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return harmonizedTariffScheduleCodeUseType == null? null: harmonizedTariffScheduleCodeUseType.getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUseTypeDetailValue().clone();
    }

    public HarmonizedTariffScheduleCodeUseTypeDetailValue getHarmonizedTariffScheduleCodeUseTypeDetailValueByNameForUpdate(String harmonizedTariffScheduleCodeUseTypeName) {
        return getHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate(getHarmonizedTariffScheduleCodeUseTypeByNameForUpdate(harmonizedTariffScheduleCodeUseTypeName));
    }

    private HarmonizedTariffScheduleCodeUseType getDefaultHarmonizedTariffScheduleCodeUseType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUseTypes.fields())
                .from(HarmonizedTariffScheduleCodeUseTypes)
                .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeUseTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeUseType getDefaultHarmonizedTariffScheduleCodeUseType() {
        return getDefaultHarmonizedTariffScheduleCodeUseType(EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUseType getDefaultHarmonizedTariffScheduleCodeUseTypeForUpdate() {
        return getDefaultHarmonizedTariffScheduleCodeUseType(EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUseTypeDetailValue getDefaultHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate() {
        return getDefaultHarmonizedTariffScheduleCodeUseType(EntityPermission.READ_WRITE).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUseTypeDetailValue();
    }

    private List<HarmonizedTariffScheduleCodeUseType> getHarmonizedTariffScheduleCodeUseTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUseTypes.fields())
                .from(HarmonizedTariffScheduleCodeUseTypes)
                .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(HarmonizedTariffScheduleCodeUseTypeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeUseTypeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME),
                    HarmonizedTariffScheduleCodeUseTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUseTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCodeUseType> getHarmonizedTariffScheduleCodeUseTypes() {
        return getHarmonizedTariffScheduleCodeUseTypes(EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeUseType> getHarmonizedTariffScheduleCodeUseTypesForUpdate() {
        return getHarmonizedTariffScheduleCodeUseTypes(EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUseTypeChoicesBean getHarmonizedTariffScheduleCodeUseTypeChoices(String defaultHarmonizedTariffScheduleCodeUseTypeChoice, Language language, boolean allowNullChoice) {
        var harmonizedTariffScheduleCodeUseTypes = getHarmonizedTariffScheduleCodeUseTypes();
        var size = harmonizedTariffScheduleCodeUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultHarmonizedTariffScheduleCodeUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var harmonizedTariffScheduleCodeUseType : harmonizedTariffScheduleCodeUseTypes) {
            var harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getLastDetail();

            var label = getBestHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, language);
            var value = harmonizedTariffScheduleCodeUseTypeDetail.getHarmonizedTariffScheduleCodeUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultHarmonizedTariffScheduleCodeUseTypeChoice != null && defaultHarmonizedTariffScheduleCodeUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && harmonizedTariffScheduleCodeUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new HarmonizedTariffScheduleCodeUseTypeChoicesBean(labels, values, defaultValue);
    }

    public HarmonizedTariffScheduleCodeUseTypeTransfer getHarmonizedTariffScheduleCodeUseTypeTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return harmonizedTariffScheduleCodeUseTypeTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUseType);
    }

    public List<HarmonizedTariffScheduleCodeUseTypeTransfer> getHarmonizedTariffScheduleCodeUseTypeTransfers(UserVisit userVisit, Collection<HarmonizedTariffScheduleCodeUseType> entities) {
        List<HarmonizedTariffScheduleCodeUseTypeTransfer> harmonizedTariffScheduleCodeUseTypeTransfers = new ArrayList<>(entities.size());

        entities.forEach((harmonizedTariffScheduleCodeUseType) ->
                harmonizedTariffScheduleCodeUseTypeTransfers.add(harmonizedTariffScheduleCodeUseTypeTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUseType))
        );

        return harmonizedTariffScheduleCodeUseTypeTransfers;
    }

    public List<HarmonizedTariffScheduleCodeUseTypeTransfer> getHarmonizedTariffScheduleCodeUseTypeTransfers(UserVisit userVisit) {
        return getHarmonizedTariffScheduleCodeUseTypeTransfers(userVisit, getHarmonizedTariffScheduleCodeUseTypes());
    }

    private void updateHarmonizedTariffScheduleCodeUseTypeFromValue(HarmonizedTariffScheduleCodeUseTypeDetailValue harmonizedTariffScheduleCodeUseTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        var harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseTypeFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, harmonizedTariffScheduleCodeUseTypeDetailValue.getHarmonizedTariffScheduleCodeUseTypePK());
        var harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getActiveDetailForUpdate();

        harmonizedTariffScheduleCodeUseTypeDetail.setThruTime(session.getStartTime());
        harmonizedTariffScheduleCodeUseTypeDetail.store();

        var harmonizedTariffScheduleCodeUseTypePK = harmonizedTariffScheduleCodeUseTypeDetail.getHarmonizedTariffScheduleCodeUseTypePK();
        var harmonizedTariffScheduleCodeUseTypeName = harmonizedTariffScheduleCodeUseTypeDetailValue.getHarmonizedTariffScheduleCodeUseTypeName();
        var isDefault = harmonizedTariffScheduleCodeUseTypeDetailValue.getIsDefault();
        var sortOrder = harmonizedTariffScheduleCodeUseTypeDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultHarmonizedTariffScheduleCodeUseType = getDefaultHarmonizedTariffScheduleCodeUseType();
            var defaultFound = defaultHarmonizedTariffScheduleCodeUseType != null && !defaultHarmonizedTariffScheduleCodeUseType.equals(harmonizedTariffScheduleCodeUseType);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultHarmonizedTariffScheduleCodeUseTypeDetailValue = getDefaultHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate();

                defaultHarmonizedTariffScheduleCodeUseTypeDetailValue.setIsDefault(false);
                updateHarmonizedTariffScheduleCodeUseTypeFromValue(defaultHarmonizedTariffScheduleCodeUseTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseTypeDetailFactory.create(harmonizedTariffScheduleCodeUseTypePK, harmonizedTariffScheduleCodeUseTypeName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        harmonizedTariffScheduleCodeUseType.setActiveDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.setLastDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.store();

        sendEvent(harmonizedTariffScheduleCodeUseTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateHarmonizedTariffScheduleCodeUseTypeFromValue(HarmonizedTariffScheduleCodeUseTypeDetailValue harmonizedTariffScheduleCodeUseTypeDetailValue, BasePK updatedBy) {
        updateHarmonizedTariffScheduleCodeUseTypeFromValue(harmonizedTariffScheduleCodeUseTypeDetailValue, true, updatedBy);
    }

    public void deleteHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, deletedBy);
        deleteHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, deletedBy);

        var harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getLastDetailForUpdate();
        harmonizedTariffScheduleCodeUseTypeDetail.setThruTime(session.getStartTime());
        harmonizedTariffScheduleCodeUseType.setActiveDetail(null);
        harmonizedTariffScheduleCodeUseType.store();

        // Check for default, and pick one if necessary
        var defaultHarmonizedTariffScheduleCodeUseType = getDefaultHarmonizedTariffScheduleCodeUseType();
        if(defaultHarmonizedTariffScheduleCodeUseType == null) {
            var harmonizedTariffScheduleCodeUseTypes = getHarmonizedTariffScheduleCodeUseTypesForUpdate();

            if(!harmonizedTariffScheduleCodeUseTypes.isEmpty()) {
                var iter = harmonizedTariffScheduleCodeUseTypes.iterator();
                if(iter.hasNext()) {
                    defaultHarmonizedTariffScheduleCodeUseType = iter.next();
                }
                var harmonizedTariffScheduleCodeUseTypeDetailValue = Objects.requireNonNull(defaultHarmonizedTariffScheduleCodeUseType).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUseTypeDetailValue().clone();

                harmonizedTariffScheduleCodeUseTypeDetailValue.setIsDefault(true);
                updateHarmonizedTariffScheduleCodeUseTypeFromValue(harmonizedTariffScheduleCodeUseTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected HarmonizedTariffScheduleCodeUseTypeDescriptionFactory harmonizedTariffScheduleCodeUseTypeDescriptionFactory;

    @Inject
    HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache;

    public HarmonizedTariffScheduleCodeUseTypeDescription createHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType,
            Language language, String description, BasePK createdBy) {
        var harmonizedTariffScheduleCodeUseTypeDescription = harmonizedTariffScheduleCodeUseTypeDescriptionFactory.create(harmonizedTariffScheduleCodeUseType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeUseTypeDescription;
    }

    private HarmonizedTariffScheduleCodeUseTypeDescription getHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUseTypeDescriptions.fields())
                .from(HarmonizedTariffScheduleCodeUseTypeDescriptions)
                .where(HarmonizedTariffScheduleCodeUseTypeDescriptions.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUseTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeUseTypeDescription getHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, Language language) {
        return getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, language, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUseTypeDescription getHarmonizedTariffScheduleCodeUseTypeDescriptionForUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, Language language) {
        return getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, language, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUseTypeDescriptionValue getHarmonizedTariffScheduleCodeUseTypeDescriptionValue(HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        return harmonizedTariffScheduleCodeUseTypeDescription == null? null: harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseTypeDescriptionValue().clone();
    }

    public HarmonizedTariffScheduleCodeUseTypeDescriptionValue getHarmonizedTariffScheduleCodeUseTypeDescriptionValueForUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, Language language) {
        return getHarmonizedTariffScheduleCodeUseTypeDescriptionValue(getHarmonizedTariffScheduleCodeUseTypeDescriptionForUpdate(harmonizedTariffScheduleCodeUseType, language));
    }

    private List<HarmonizedTariffScheduleCodeUseTypeDescription> getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUseTypeDescriptions.fields())
                    .from(HarmonizedTariffScheduleCodeUseTypeDescriptions)
                    .join(Languages).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(HarmonizedTariffScheduleCodeUseTypeDescriptions.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUseTypeDescriptions.fields())
                    .from(HarmonizedTariffScheduleCodeUseTypeDescriptions)
                    .where(HarmonizedTariffScheduleCodeUseTypeDescriptions.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return harmonizedTariffScheduleCodeUseTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCodeUseTypeDescription> getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeUseTypeDescription> getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseTypeForUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_WRITE);
    }

    public String getBestHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, Language language) {
        String description;
        var harmonizedTariffScheduleCodeUseTypeDescription = getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, language);

        if(harmonizedTariffScheduleCodeUseTypeDescription == null && !language.getIsDefault()) {
            harmonizedTariffScheduleCodeUseTypeDescription = getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, partyControl.getDefaultLanguage());
        }

        if(harmonizedTariffScheduleCodeUseTypeDescription == null) {
            description = harmonizedTariffScheduleCodeUseType.getLastDetail().getHarmonizedTariffScheduleCodeUseTypeName();
        } else {
            description = harmonizedTariffScheduleCodeUseTypeDescription.getDescription();
        }

        return description;
    }

    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer getHarmonizedTariffScheduleCodeUseTypeDescriptionTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        return harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUseTypeDescription);
    }

    public List<HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer> getHarmonizedTariffScheduleCodeUseTypeDescriptionTransfersByHarmonizedTariffScheduleCodeUseType(UserVisit userVisit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        var harmonizedTariffScheduleCodeUseTypeDescriptions = getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType);
        List<HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer> harmonizedTariffScheduleCodeUseTypeDescriptionTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUseTypeDescriptions.size());

        harmonizedTariffScheduleCodeUseTypeDescriptions.forEach((harmonizedTariffScheduleCodeUseTypeDescription) -> {
            harmonizedTariffScheduleCodeUseTypeDescriptionTransfers.add(harmonizedTariffScheduleCodeUseTypeDescriptionTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUseTypeDescription));
        });

        return harmonizedTariffScheduleCodeUseTypeDescriptionTransfers;
    }

    public void updateHarmonizedTariffScheduleCodeUseTypeDescriptionFromValue(HarmonizedTariffScheduleCodeUseTypeDescriptionValue harmonizedTariffScheduleCodeUseTypeDescriptionValue, BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeUseTypeDescriptionValue.hasBeenModified()) {
            var harmonizedTariffScheduleCodeUseTypeDescription = harmonizedTariffScheduleCodeUseTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeUseTypeDescriptionValue.getPrimaryKey());

            harmonizedTariffScheduleCodeUseTypeDescription.setThruTime(session.getStartTime());
            harmonizedTariffScheduleCodeUseTypeDescription.store();

            var harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseType();
            var language = harmonizedTariffScheduleCodeUseTypeDescription.getLanguage();
            var description = harmonizedTariffScheduleCodeUseTypeDescriptionValue.getDescription();

            harmonizedTariffScheduleCodeUseTypeDescription = harmonizedTariffScheduleCodeUseTypeDescriptionFactory.create(harmonizedTariffScheduleCodeUseType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUseTypeDescription.setThruTime(session.getStartTime());

        sendEvent(harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseTypePK(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK deletedBy) {
        var harmonizedTariffScheduleCodeUseTypeDescriptions = getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseTypeForUpdate(harmonizedTariffScheduleCodeUseType);

        harmonizedTariffScheduleCodeUseTypeDescriptions.forEach((harmonizedTariffScheduleCodeUseTypeDescription) -> 
                deleteHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected HarmonizedTariffScheduleCodeUnitDetailFactory harmonizedTariffScheduleCodeUnitDetailFactory;

    @Inject
    protected HarmonizedTariffScheduleCodeUnitFactory harmonizedTariffScheduleCodeUnitFactory;

    @Inject
    HarmonizedTariffScheduleCodeUnitTransferCache harmonizedTariffScheduleCodeUnitTransferCache;

    public HarmonizedTariffScheduleCodeUnit createHarmonizedTariffScheduleCodeUnit(String harmonizedTariffScheduleCodeUnitName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultHarmonizedTariffScheduleCodeUnit = getDefaultHarmonizedTariffScheduleCodeUnit();
        var defaultFound = defaultHarmonizedTariffScheduleCodeUnit != null;

        if(defaultFound && isDefault) {
            var defaultHarmonizedTariffScheduleCodeUnitDetailValue = getDefaultHarmonizedTariffScheduleCodeUnitDetailValueForUpdate();

            defaultHarmonizedTariffScheduleCodeUnitDetailValue.setIsDefault(false);
            updateHarmonizedTariffScheduleCodeUnitFromValue(defaultHarmonizedTariffScheduleCodeUnitDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var harmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeUnitFactory.create();
        var harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnitDetailFactory.create(harmonizedTariffScheduleCodeUnit,
                harmonizedTariffScheduleCodeUnitName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        harmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeUnitFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCodeUnit.getPrimaryKey());
        harmonizedTariffScheduleCodeUnit.setActiveDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.setLastDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.store();

        sendEvent(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return harmonizedTariffScheduleCodeUnit;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.HarmonizedTariffScheduleCodeUnit */
    public HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new HarmonizedTariffScheduleCodeUnitPK(entityInstance.getEntityUniqueId());

        return harmonizedTariffScheduleCodeUnitFactory.getEntityFromPK(entityPermission, pk);
    }

    public HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByEntityInstance(EntityInstance entityInstance) {
        return getHarmonizedTariffScheduleCodeUnitByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getHarmonizedTariffScheduleCodeUnitByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countHarmonizedTariffScheduleCodeUnits() {
        return session.getDslContext()
                .selectCount()
                .from(HarmonizedTariffScheduleCodeUnits)
                .join(HarmonizedTariffScheduleCodeUnitDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_UNITS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByName(String harmonizedTariffScheduleCodeUnitName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUnits.fields())
                .from(HarmonizedTariffScheduleCodeUnits)
                .join(HarmonizedTariffScheduleCodeUnitDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_UNITS_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeUnitDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_NAME.eq(harmonizedTariffScheduleCodeUnitName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUnitFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByName(String harmonizedTariffScheduleCodeUnitName) {
        return getHarmonizedTariffScheduleCodeUnitByName(harmonizedTariffScheduleCodeUnitName, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByNameForUpdate(String harmonizedTariffScheduleCodeUnitName) {
        return getHarmonizedTariffScheduleCodeUnitByName(harmonizedTariffScheduleCodeUnitName, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUnitDetailValue getHarmonizedTariffScheduleCodeUnitDetailValueForUpdate(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        return harmonizedTariffScheduleCodeUnit == null? null: harmonizedTariffScheduleCodeUnit.getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUnitDetailValue().clone();
    }

    public HarmonizedTariffScheduleCodeUnitDetailValue getHarmonizedTariffScheduleCodeUnitDetailValueByNameForUpdate(String harmonizedTariffScheduleCodeUnitName) {
        return getHarmonizedTariffScheduleCodeUnitDetailValueForUpdate(getHarmonizedTariffScheduleCodeUnitByNameForUpdate(harmonizedTariffScheduleCodeUnitName));
    }

    private HarmonizedTariffScheduleCodeUnit getDefaultHarmonizedTariffScheduleCodeUnit(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUnits.fields())
                .from(HarmonizedTariffScheduleCodeUnits)
                .join(HarmonizedTariffScheduleCodeUnitDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_UNITS_ACTIVE_DETAIL_FK)
                .where(HarmonizedTariffScheduleCodeUnitDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUnitFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeUnit getDefaultHarmonizedTariffScheduleCodeUnit() {
        return getDefaultHarmonizedTariffScheduleCodeUnit(EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUnit getDefaultHarmonizedTariffScheduleCodeUnitForUpdate() {
        return getDefaultHarmonizedTariffScheduleCodeUnit(EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUnitDetailValue getDefaultHarmonizedTariffScheduleCodeUnitDetailValueForUpdate() {
        return getDefaultHarmonizedTariffScheduleCodeUnit(EntityPermission.READ_WRITE).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUnitDetailValue();
    }

    private List<HarmonizedTariffScheduleCodeUnit> getHarmonizedTariffScheduleCodeUnits(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUnits.fields())
                .from(HarmonizedTariffScheduleCodeUnits)
                .join(HarmonizedTariffScheduleCodeUnitDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_UNITS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(HarmonizedTariffScheduleCodeUnitDetails.SORT_ORDER, HarmonizedTariffScheduleCodeUnitDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_NAME),
                    HarmonizedTariffScheduleCodeUnitFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUnitFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCodeUnit> getHarmonizedTariffScheduleCodeUnits() {
        return getHarmonizedTariffScheduleCodeUnits(EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeUnit> getHarmonizedTariffScheduleCodeUnitsForUpdate() {
        return getHarmonizedTariffScheduleCodeUnits(EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUnitChoicesBean getHarmonizedTariffScheduleCodeUnitChoices(String defaultHarmonizedTariffScheduleCodeUnitChoice, Language language, boolean allowNullChoice) {
        var harmonizedTariffScheduleCodeUnits = getHarmonizedTariffScheduleCodeUnits();
        var size = harmonizedTariffScheduleCodeUnits.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultHarmonizedTariffScheduleCodeUnitChoice == null) {
                defaultValue = "";
            }
        }

        for(var harmonizedTariffScheduleCodeUnit : harmonizedTariffScheduleCodeUnits) {
            var harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getLastDetail();

            var label = getBestHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, language);
            var value = harmonizedTariffScheduleCodeUnitDetail.getHarmonizedTariffScheduleCodeUnitName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultHarmonizedTariffScheduleCodeUnitChoice != null && defaultHarmonizedTariffScheduleCodeUnitChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && harmonizedTariffScheduleCodeUnitDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new HarmonizedTariffScheduleCodeUnitChoicesBean(labels, values, defaultValue);
    }

    public HarmonizedTariffScheduleCodeUnitTransfer getHarmonizedTariffScheduleCodeUnitTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        return harmonizedTariffScheduleCodeUnitTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUnit);
    }

    public List<HarmonizedTariffScheduleCodeUnitTransfer> getHarmonizedTariffScheduleCodeUnitTransfers(UserVisit userVisit, Collection<HarmonizedTariffScheduleCodeUnit> entities) {
        List<HarmonizedTariffScheduleCodeUnitTransfer> harmonizedTariffScheduleCodeUnitTransfers = new ArrayList<>(entities.size());

        entities.forEach((harmonizedTariffScheduleCodeUnit) ->
                harmonizedTariffScheduleCodeUnitTransfers.add(harmonizedTariffScheduleCodeUnitTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUnit))
        );

        return harmonizedTariffScheduleCodeUnitTransfers;
    }

    public List<HarmonizedTariffScheduleCodeUnitTransfer> getHarmonizedTariffScheduleCodeUnitTransfers(UserVisit userVisit) {
        return getHarmonizedTariffScheduleCodeUnitTransfers(userVisit, getHarmonizedTariffScheduleCodeUnits());
    }

    private void updateHarmonizedTariffScheduleCodeUnitFromValue(HarmonizedTariffScheduleCodeUnitDetailValue harmonizedTariffScheduleCodeUnitDetailValue, boolean checkDefault, BasePK updatedBy) {
        var harmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeUnitFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, harmonizedTariffScheduleCodeUnitDetailValue.getHarmonizedTariffScheduleCodeUnitPK());
        var harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getActiveDetailForUpdate();

        harmonizedTariffScheduleCodeUnitDetail.setThruTime(session.getStartTime());
        harmonizedTariffScheduleCodeUnitDetail.store();

        var harmonizedTariffScheduleCodeUnitPK = harmonizedTariffScheduleCodeUnitDetail.getHarmonizedTariffScheduleCodeUnitPK();
        var harmonizedTariffScheduleCodeUnitName = harmonizedTariffScheduleCodeUnitDetailValue.getHarmonizedTariffScheduleCodeUnitName();
        var isDefault = harmonizedTariffScheduleCodeUnitDetailValue.getIsDefault();
        var sortOrder = harmonizedTariffScheduleCodeUnitDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultHarmonizedTariffScheduleCodeUnit = getDefaultHarmonizedTariffScheduleCodeUnit();
            var defaultFound = defaultHarmonizedTariffScheduleCodeUnit != null && !defaultHarmonizedTariffScheduleCodeUnit.equals(harmonizedTariffScheduleCodeUnit);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultHarmonizedTariffScheduleCodeUnitDetailValue = getDefaultHarmonizedTariffScheduleCodeUnitDetailValueForUpdate();

                defaultHarmonizedTariffScheduleCodeUnitDetailValue.setIsDefault(false);
                updateHarmonizedTariffScheduleCodeUnitFromValue(defaultHarmonizedTariffScheduleCodeUnitDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnitDetailFactory.create(harmonizedTariffScheduleCodeUnitPK, harmonizedTariffScheduleCodeUnitName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        harmonizedTariffScheduleCodeUnit.setActiveDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.setLastDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.store();

        sendEvent(harmonizedTariffScheduleCodeUnitPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateHarmonizedTariffScheduleCodeUnitFromValue(HarmonizedTariffScheduleCodeUnitDetailValue harmonizedTariffScheduleCodeUnitDetailValue, BasePK updatedBy) {
        updateHarmonizedTariffScheduleCodeUnitFromValue(harmonizedTariffScheduleCodeUnitDetailValue, true, updatedBy);
    }

    public void deleteHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, deletedBy);
        deleteHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, deletedBy);

        var harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getLastDetailForUpdate();
        harmonizedTariffScheduleCodeUnitDetail.setThruTime(session.getStartTime());
        harmonizedTariffScheduleCodeUnit.setActiveDetail(null);
        harmonizedTariffScheduleCodeUnit.store();

        // Check for default, and pick one if necessary
        var defaultHarmonizedTariffScheduleCodeUnit = getDefaultHarmonizedTariffScheduleCodeUnit();
        if(defaultHarmonizedTariffScheduleCodeUnit == null) {
            var harmonizedTariffScheduleCodeUnits = getHarmonizedTariffScheduleCodeUnitsForUpdate();

            if(!harmonizedTariffScheduleCodeUnits.isEmpty()) {
                var iter = harmonizedTariffScheduleCodeUnits.iterator();
                if(iter.hasNext()) {
                    defaultHarmonizedTariffScheduleCodeUnit = iter.next();
                }
                var harmonizedTariffScheduleCodeUnitDetailValue = Objects.requireNonNull(defaultHarmonizedTariffScheduleCodeUnit).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUnitDetailValue().clone();

                harmonizedTariffScheduleCodeUnitDetailValue.setIsDefault(true);
                updateHarmonizedTariffScheduleCodeUnitFromValue(harmonizedTariffScheduleCodeUnitDetailValue, false, deletedBy);
            }
        }

        sendEvent(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected HarmonizedTariffScheduleCodeUnitDescriptionFactory harmonizedTariffScheduleCodeUnitDescriptionFactory;

    @Inject
    HarmonizedTariffScheduleCodeUnitDescriptionTransferCache harmonizedTariffScheduleCodeUnitDescriptionTransferCache;

    public HarmonizedTariffScheduleCodeUnitDescription createHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit,
            Language language, String description, BasePK createdBy) {
        var harmonizedTariffScheduleCodeUnitDescription = harmonizedTariffScheduleCodeUnitDescriptionFactory.create(harmonizedTariffScheduleCodeUnit,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeUnitDescription;
    }

    private HarmonizedTariffScheduleCodeUnitDescription getHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUnitDescriptions.fields())
                .from(HarmonizedTariffScheduleCodeUnitDescriptions)
                .where(HarmonizedTariffScheduleCodeUnitDescriptions.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT.eq(harmonizedTariffScheduleCodeUnit.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUnitDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUnitDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUnitDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeUnitDescription getHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, Language language) {
        return getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, language, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUnitDescription getHarmonizedTariffScheduleCodeUnitDescriptionForUpdate(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, Language language) {
        return getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, language, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUnitDescriptionValue getHarmonizedTariffScheduleCodeUnitDescriptionValue(HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription) {
        return harmonizedTariffScheduleCodeUnitDescription == null? null: harmonizedTariffScheduleCodeUnitDescription.getHarmonizedTariffScheduleCodeUnitDescriptionValue().clone();
    }

    public HarmonizedTariffScheduleCodeUnitDescriptionValue getHarmonizedTariffScheduleCodeUnitDescriptionValueForUpdate(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, Language language) {
        return getHarmonizedTariffScheduleCodeUnitDescriptionValue(getHarmonizedTariffScheduleCodeUnitDescriptionForUpdate(harmonizedTariffScheduleCodeUnit, language));
    }

    private List<HarmonizedTariffScheduleCodeUnitDescription> getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUnitDescriptions.fields())
                    .from(HarmonizedTariffScheduleCodeUnitDescriptions)
                    .join(Languages).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(HarmonizedTariffScheduleCodeUnitDescriptions.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT.eq(harmonizedTariffScheduleCodeUnit.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUnitDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    HarmonizedTariffScheduleCodeUnitDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUnitDescriptions.fields())
                    .from(HarmonizedTariffScheduleCodeUnitDescriptions)
                    .where(HarmonizedTariffScheduleCodeUnitDescriptions.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT.eq(harmonizedTariffScheduleCodeUnit.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUnitDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return harmonizedTariffScheduleCodeUnitDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCodeUnitDescription> getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeUnitDescription> getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnitForUpdate(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, EntityPermission.READ_WRITE);
    }

    public String getBestHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, Language language) {
        String description;
        var harmonizedTariffScheduleCodeUnitDescription = getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, language);

        if(harmonizedTariffScheduleCodeUnitDescription == null && !language.getIsDefault()) {
            harmonizedTariffScheduleCodeUnitDescription = getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, partyControl.getDefaultLanguage());
        }

        if(harmonizedTariffScheduleCodeUnitDescription == null) {
            description = harmonizedTariffScheduleCodeUnit.getLastDetail().getHarmonizedTariffScheduleCodeUnitName();
        } else {
            description = harmonizedTariffScheduleCodeUnitDescription.getDescription();
        }

        return description;
    }

    public HarmonizedTariffScheduleCodeUnitDescriptionTransfer getHarmonizedTariffScheduleCodeUnitDescriptionTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription) {
        return harmonizedTariffScheduleCodeUnitDescriptionTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUnitDescription);
    }

    public List<HarmonizedTariffScheduleCodeUnitDescriptionTransfer> getHarmonizedTariffScheduleCodeUnitDescriptionTransfersByHarmonizedTariffScheduleCodeUnit(UserVisit userVisit, HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        var harmonizedTariffScheduleCodeUnitDescriptions = getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit);
        List<HarmonizedTariffScheduleCodeUnitDescriptionTransfer> harmonizedTariffScheduleCodeUnitDescriptionTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUnitDescriptions.size());

        harmonizedTariffScheduleCodeUnitDescriptions.forEach((harmonizedTariffScheduleCodeUnitDescription) -> {
            harmonizedTariffScheduleCodeUnitDescriptionTransfers.add(harmonizedTariffScheduleCodeUnitDescriptionTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUnitDescription));
        });

        return harmonizedTariffScheduleCodeUnitDescriptionTransfers;
    }

    public void updateHarmonizedTariffScheduleCodeUnitDescriptionFromValue(HarmonizedTariffScheduleCodeUnitDescriptionValue harmonizedTariffScheduleCodeUnitDescriptionValue, BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeUnitDescriptionValue.hasBeenModified()) {
            var harmonizedTariffScheduleCodeUnitDescription = harmonizedTariffScheduleCodeUnitDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeUnitDescriptionValue.getPrimaryKey());

            harmonizedTariffScheduleCodeUnitDescription.setThruTime(session.getStartTime());
            harmonizedTariffScheduleCodeUnitDescription.store();

            var harmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeUnitDescription.getHarmonizedTariffScheduleCodeUnit();
            var language = harmonizedTariffScheduleCodeUnitDescription.getLanguage();
            var description = harmonizedTariffScheduleCodeUnitDescriptionValue.getDescription();

            harmonizedTariffScheduleCodeUnitDescription = harmonizedTariffScheduleCodeUnitDescriptionFactory.create(harmonizedTariffScheduleCodeUnit, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUnitDescription.setThruTime(session.getStartTime());

        sendEvent(harmonizedTariffScheduleCodeUnitDescription.getHarmonizedTariffScheduleCodeUnitPK(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, BasePK deletedBy) {
        var harmonizedTariffScheduleCodeUnitDescriptions = getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnitForUpdate(harmonizedTariffScheduleCodeUnit);

        harmonizedTariffScheduleCodeUnitDescriptions.forEach((harmonizedTariffScheduleCodeUnitDescription) -> 
                deleteHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnitDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected HarmonizedTariffScheduleCodeUseFactory harmonizedTariffScheduleCodeUseFactory;

    @Inject
    HarmonizedTariffScheduleCodeUseTransferCache harmonizedTariffScheduleCodeUseTransferCache;

    public HarmonizedTariffScheduleCodeUse createHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK createdBy) {
        var harmonizedTariffScheduleCodeUse = harmonizedTariffScheduleCodeUseFactory.create(harmonizedTariffScheduleCode,
                harmonizedTariffScheduleCodeUseType, session.getStartTime(), Session.MAX_TIME);

        sendEvent(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUse.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeUse;
    }

    public long countHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return session.getDslContext()
                .selectCount()
                .from(HarmonizedTariffScheduleCodeUses)
                .where(HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return session.getDslContext()
                .selectCount()
                .from(HarmonizedTariffScheduleCodeUses)
                .where(HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private HarmonizedTariffScheduleCodeUse getHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(HarmonizedTariffScheduleCodeUses.fields())
                .from(HarmonizedTariffScheduleCodeUses)
                .where(HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()),
                        HarmonizedTariffScheduleCodeUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return harmonizedTariffScheduleCodeUseFactory.getEntityFromQuery(entityPermission, query);
    }

    public HarmonizedTariffScheduleCodeUse getHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeUseType, EntityPermission.READ_ONLY);
    }

    public HarmonizedTariffScheduleCodeUse getHarmonizedTariffScheduleCodeUseForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeUseType, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUseValue getHarmonizedTariffScheduleCodeUseValue(HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse) {
        return harmonizedTariffScheduleCodeUse == null? null: harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodeUseValue().clone();
    }

    public HarmonizedTariffScheduleCodeUseValue getHarmonizedTariffScheduleCodeUseValueForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUseValue(getHarmonizedTariffScheduleCodeUseForUpdate(harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeUseType));
    }

    private List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUses.fields())
                    .from(HarmonizedTariffScheduleCodeUses)
                    .join(HarmonizedTariffScheduleCodeUseTypes).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USES_HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_FK)
                    .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_LAST_DETAIL_FK)
                    .where(HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(HarmonizedTariffScheduleCodeUseTypeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeUseTypeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME),
                    HarmonizedTariffScheduleCodeUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUses.fields())
                    .from(HarmonizedTariffScheduleCodeUses)
                    .where(HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return harmonizedTariffScheduleCodeUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_WRITE);
    }

    private List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUses.fields())
                    .from(HarmonizedTariffScheduleCodeUses)
                    .join(HarmonizedTariffScheduleCodes).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USES_HARMONIZED_TARIFF_SCHEDULE_CODE_FK)
                    .join(HarmonizedTariffScheduleCodeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODES_LAST_DETAIL_FK)
                    .where(HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(HarmonizedTariffScheduleCodeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME),
                    HarmonizedTariffScheduleCodeUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(HarmonizedTariffScheduleCodeUses.fields())
                    .from(HarmonizedTariffScheduleCodeUses)
                    .where(HarmonizedTariffScheduleCodeUses.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()),
                            HarmonizedTariffScheduleCodeUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return harmonizedTariffScheduleCodeUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseTypeForUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_WRITE);
    }

    public HarmonizedTariffScheduleCodeUseTransfer getHarmonizedTariffScheduleCodeUseTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse) {
        return harmonizedTariffScheduleCodeUseTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUse);
    }

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfers(UserVisit userVisit, Collection<HarmonizedTariffScheduleCodeUse> harmonizedTariffScheduleCodeUses) {
        List<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUseTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUses.size());

        harmonizedTariffScheduleCodeUses.forEach((harmonizedTariffScheduleCodeUse) -> {
            harmonizedTariffScheduleCodeUseTransfers.add(harmonizedTariffScheduleCodeUseTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUse));
        });

        return harmonizedTariffScheduleCodeUseTransfers;
    }

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, List<HarmonizedTariffScheduleCodeUse> harmonizedTariffScheduleCodeUses) {
        return getHarmonizedTariffScheduleCodeUseTransfers(userVisit, harmonizedTariffScheduleCodeUses);
    }

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(userVisit, getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode));
    }

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCodeUseType(UserVisit userVisit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(userVisit, getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType));
    }

    public void deleteHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUse.setThruTime(session.getStartTime());

        sendEvent(harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodePK(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUse.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteHarmonizedTariffScheduleCodeUses(List<HarmonizedTariffScheduleCodeUse> harmonizedTariffScheduleCodeUses, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUses.forEach((harmonizedTariffScheduleCodeUse) -> 
                deleteHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCodeUse, deletedBy)
        );
    }

    public void deleteHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodeUses(getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeForUpdate(harmonizedTariffScheduleCode), deletedBy);
    }

    public void deleteHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodeUses(getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseTypeForUpdate(harmonizedTariffScheduleCodeUseType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Harmonized Tariff Schedule Codes
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemHarmonizedTariffScheduleCodeDetailFactory itemHarmonizedTariffScheduleCodeDetailFactory;

    @Inject
    protected ItemHarmonizedTariffScheduleCodeFactory itemHarmonizedTariffScheduleCodeFactory;

    @Inject
    ItemHarmonizedTariffScheduleCodeTransferCache itemHarmonizedTariffScheduleCodeTransferCache;

    public ItemHarmonizedTariffScheduleCode createItemHarmonizedTariffScheduleCode(Item item, GeoCode countryGeoCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            BasePK createdBy) {
        var itemHarmonizedTariffScheduleCode = itemHarmonizedTariffScheduleCodeFactory.create();
        var itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCodeDetailFactory.create(
                itemHarmonizedTariffScheduleCode, item, countryGeoCode, harmonizedTariffScheduleCodeUseType, harmonizedTariffScheduleCode, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        itemHarmonizedTariffScheduleCode = itemHarmonizedTariffScheduleCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemHarmonizedTariffScheduleCode.getPrimaryKey());
        itemHarmonizedTariffScheduleCode.setActiveDetail(itemHarmonizedTariffScheduleCodeDetail);
        itemHarmonizedTariffScheduleCode.setLastDetail(itemHarmonizedTariffScheduleCodeDetail);
        itemHarmonizedTariffScheduleCode.store();

        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemHarmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemHarmonizedTariffScheduleCode;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemHarmonizedTariffScheduleCode */
    public ItemHarmonizedTariffScheduleCode getItemHarmonizedTariffScheduleCodeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemHarmonizedTariffScheduleCodePK(entityInstance.getEntityUniqueId());

        return itemHarmonizedTariffScheduleCodeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemHarmonizedTariffScheduleCode getItemHarmonizedTariffScheduleCodeByEntityInstance(EntityInstance entityInstance) {
        return getItemHarmonizedTariffScheduleCodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemHarmonizedTariffScheduleCode getItemHarmonizedTariffScheduleCodeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemHarmonizedTariffScheduleCodeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countItemHarmonizedTariffScheduleCodesByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemHarmonizedTariffScheduleCodes)
                .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(ItemHarmonizedTariffScheduleCodeDetails.ITEM.eq(item.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemHarmonizedTariffScheduleCodesByCountryGeoCode(final GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ItemHarmonizedTariffScheduleCodes)
                .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(ItemHarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(final HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return session.getDslContext()
                .selectCount()
                .from(ItemHarmonizedTariffScheduleCodes)
                .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(ItemHarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(final HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return session.getDslContext()
                .selectCount()
                .from(ItemHarmonizedTariffScheduleCodes)
                .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(ItemHarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .join(GeoCodes).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_COUNTRY_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .join(HarmonizedTariffScheduleCodeUseTypes).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_FK)
                    .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_LAST_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.ITEM.eq(item.getPrimaryKey()))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME, HarmonizedTariffScheduleCodeUseTypeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeUseTypeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME),
                    ItemHarmonizedTariffScheduleCodeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.ITEM.eq(item.getPrimaryKey()))
                    .forUpdate();
        };

        return itemHarmonizedTariffScheduleCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByItem(Item item) {
        return getItemHarmonizedTariffScheduleCodesByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByItemForUpdate(Item item) {
        return getItemHarmonizedTariffScheduleCodesByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(HarmonizedTariffScheduleCodeUseTypes).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_FK)
                    .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_LAST_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                    .orderBy(ItemDetails.ITEM_NAME, HarmonizedTariffScheduleCodeUseTypeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeUseTypeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME),
                    ItemHarmonizedTariffScheduleCodeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                    .forUpdate();
        };

        return itemHarmonizedTariffScheduleCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode) {
        return getItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(GeoCodes).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_COUNTRY_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()))
                    .orderBy(ItemDetails.ITEM_NAME, GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    ItemHarmonizedTariffScheduleCodeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()))
                    .forUpdate();
        };

        return itemHarmonizedTariffScheduleCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseForUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_WRITE);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(GeoCodes).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_COUNTRY_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .join(HarmonizedTariffScheduleCodeUseTypes).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE_DETAILS_HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_FK)
                    .join(HarmonizedTariffScheduleCodeUseTypeDetails).onKey(HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPES_LAST_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()))
                    .orderBy(ItemDetails.ITEM_NAME, GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME, HarmonizedTariffScheduleCodeUseTypeDetails.SORT_ORDER, HarmonizedTariffScheduleCodeUseTypeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME),
                    ItemHarmonizedTariffScheduleCodeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemHarmonizedTariffScheduleCodes.fields())
                    .from(ItemHarmonizedTariffScheduleCodes)
                    .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                    .where(ItemHarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE.eq(harmonizedTariffScheduleCode.getPrimaryKey()))
                    .forUpdate();
        };

        return itemHarmonizedTariffScheduleCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_WRITE);
    }

    private ItemHarmonizedTariffScheduleCode getItemHarmonizedTariffScheduleCode(Item item, GeoCode countryGeoCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemHarmonizedTariffScheduleCodes.fields())
                .from(ItemHarmonizedTariffScheduleCodes)
                .join(ItemHarmonizedTariffScheduleCodeDetails).onKey(ITEM_HARMONIZED_TARIFF_SCHEDULE_CODES_ACTIVE_DETAIL_FK)
                .where(ItemHarmonizedTariffScheduleCodeDetails.ITEM.eq(item.getPrimaryKey()),
                        ItemHarmonizedTariffScheduleCodeDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ItemHarmonizedTariffScheduleCodeDetails.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE.eq(harmonizedTariffScheduleCodeUseType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemHarmonizedTariffScheduleCodeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemHarmonizedTariffScheduleCode getItemHarmonizedTariffScheduleCode(Item item, GeoCode countryGeoCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCode(item, countryGeoCode, harmonizedTariffScheduleCodeUseType, EntityPermission.READ_ONLY);
    }

    public ItemHarmonizedTariffScheduleCode getItemHarmonizedTariffScheduleCodeForUpdate(Item item, GeoCode countryGeoCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCode(item, countryGeoCode, harmonizedTariffScheduleCodeUseType, EntityPermission.READ_WRITE);
    }

    public ItemHarmonizedTariffScheduleCodeDetailValue getItemHarmonizedTariffScheduleCodeDetailValueForUpdate(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        return itemHarmonizedTariffScheduleCode == null ? null : itemHarmonizedTariffScheduleCode.getLastDetailForUpdate().getItemHarmonizedTariffScheduleCodeDetailValue().clone();
    }

    public ItemHarmonizedTariffScheduleCodeDetailValue getItemHarmonizedTariffScheduleCodeDetailValueForUpdate(Item item, GeoCode countryGeoCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCodeDetailValueForUpdate(getItemHarmonizedTariffScheduleCodeForUpdate(item, countryGeoCode,
                harmonizedTariffScheduleCodeUseType));
    }

    public ItemHarmonizedTariffScheduleCodeTransfer getItemHarmonizedTariffScheduleCodeTransfer(UserVisit userVisit, ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        return itemHarmonizedTariffScheduleCodeTransferCache.getTransfer(userVisit, itemHarmonizedTariffScheduleCode);
    }

    public List<ItemHarmonizedTariffScheduleCodeTransfer> getItemHarmonizedTariffScheduleCodeTransfers(UserVisit userVisit, Collection<ItemHarmonizedTariffScheduleCode> itemHarmonizedTariffScheduleCodes) {
        List<ItemHarmonizedTariffScheduleCodeTransfer> itemHarmonizedTariffScheduleCodeTransfers = new ArrayList<>(itemHarmonizedTariffScheduleCodes.size());

        itemHarmonizedTariffScheduleCodes.forEach((itemHarmonizedTariffScheduleCode) ->
                itemHarmonizedTariffScheduleCodeTransfers.add(itemHarmonizedTariffScheduleCodeTransferCache.getTransfer(userVisit, itemHarmonizedTariffScheduleCode))
        );

        return itemHarmonizedTariffScheduleCodeTransfers;
    }

    public List<ItemHarmonizedTariffScheduleCodeTransfer> getItemHarmonizedTariffScheduleCodeTransfersByItem(UserVisit userVisit, Item item) {
        return getItemHarmonizedTariffScheduleCodeTransfers(userVisit, getItemHarmonizedTariffScheduleCodesByItem(item));
    }

    public List<ItemHarmonizedTariffScheduleCodeTransfer> getItemHarmonizedTariffScheduleCodeTransfersByCountryGeoCode(UserVisit userVisit, GeoCode countryGeoCode) {
        return getItemHarmonizedTariffScheduleCodeTransfers(userVisit, getItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode));
    }

    public List<ItemHarmonizedTariffScheduleCodeTransfer> getItemHarmonizedTariffScheduleCodeTransfersByHarmonizedTariffScheduleCodeUseType(UserVisit userVisit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCodeTransfers(userVisit, getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCodeUseType));
    }

    public List<ItemHarmonizedTariffScheduleCodeTransfer> getItemHarmonizedTariffScheduleCodeTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getItemHarmonizedTariffScheduleCodeTransfers(userVisit, getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode));
    }

    public void updateItemHarmonizedTariffScheduleCodeFromValue(ItemHarmonizedTariffScheduleCodeDetailValue itemHarmonizedTariffScheduleCodeDetailValue,
            BasePK updatedBy) {
        if(itemHarmonizedTariffScheduleCodeDetailValue.hasBeenModified()) {
            var itemHarmonizedTariffScheduleCode = itemHarmonizedTariffScheduleCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemHarmonizedTariffScheduleCodeDetailValue.getItemHarmonizedTariffScheduleCodePK());
            var itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getActiveDetailForUpdate();

            itemHarmonizedTariffScheduleCodeDetail.setThruTime(session.getStartTime());
            itemHarmonizedTariffScheduleCodeDetail.store();

            var itemHarmonizedTariffScheduleCodePK = itemHarmonizedTariffScheduleCodeDetail.getItemHarmonizedTariffScheduleCodePK();
            var itemPK = itemHarmonizedTariffScheduleCodeDetail.getItemPK();
            var countryGeoCodePK = itemHarmonizedTariffScheduleCodeDetail.getCountryGeoCodePK();
            var harmonizedTariffScheduleCodeUseTypePK = itemHarmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeUseTypePK();
            var harmonizedTariffScheduleCodePK = itemHarmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodePK();

            itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCodeDetailFactory.create(itemHarmonizedTariffScheduleCodePK,
                    itemPK, countryGeoCodePK, harmonizedTariffScheduleCodeUseTypePK, harmonizedTariffScheduleCodePK, session.getStartTime(), Session.MAX_TIME);

            itemHarmonizedTariffScheduleCode.setActiveDetail(itemHarmonizedTariffScheduleCodeDetail);
            itemHarmonizedTariffScheduleCode.setLastDetail(itemHarmonizedTariffScheduleCodeDetail);

            sendEvent(itemPK, EventTypes.MODIFY, itemHarmonizedTariffScheduleCodePK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemHarmonizedTariffScheduleCode(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode, BasePK deletedBy) {
        var itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getLastDetailForUpdate();
        itemHarmonizedTariffScheduleCodeDetail.setThruTime(session.getStartTime());
        itemHarmonizedTariffScheduleCode.setActiveDetail(null);
        itemHarmonizedTariffScheduleCode.store();

        sendEvent(itemHarmonizedTariffScheduleCodeDetail.getItemPK(), EventTypes.MODIFY, itemHarmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteItemHarmonizedTariffScheduleCodes(List<ItemHarmonizedTariffScheduleCode> itemHarmonizedTariffScheduleCodes, BasePK deletedBy) {
        itemHarmonizedTariffScheduleCodes.forEach((itemHarmonizedTariffScheduleCode) -> 
                deleteItemHarmonizedTariffScheduleCode(itemHarmonizedTariffScheduleCode, deletedBy)
        );
    }

    public void deleteItemHarmonizedTariffScheduleCodesByItem(Item item, BasePK deletedBy) {
        deleteItemHarmonizedTariffScheduleCodes(getItemHarmonizedTariffScheduleCodesByItem(item), deletedBy);
    }

    public void deleteItemHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode, BasePK deletedBy) {
        deleteItemHarmonizedTariffScheduleCodes(getItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode), deletedBy);
    }

    public void deleteItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCode harmonizedTariffScheduleCodeUseType, BasePK deletedBy) {
        deleteItemHarmonizedTariffScheduleCodes(getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeForUpdate(harmonizedTariffScheduleCodeUseType), deletedBy);
    }

    public void deleteItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, BasePK deletedBy) {
        deleteItemHarmonizedTariffScheduleCodes(getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeForUpdate(harmonizedTariffScheduleCode), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Searches
    // --------------------------------------------------------------------------------

    private List<Long> getItemIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        Select<?> query;

        if(cachedSearch == null) {
            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(SearchResults)
                    .join(EntityInstances).onKey(SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()))
                    .orderBy(SearchResults.SORT_ORDER, SearchResults.ENTITY_INSTANCE), SearchResultFactory.class);
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(CachedExecutedSearchResults)
                    .join(EntityInstances).onKey(CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()))
                    .orderBy(CachedExecutedSearchResults.SORT_ORDER, CachedExecutedSearchResults.ENTITY_INSTANCE),
                    CachedExecutedSearchResultFactory.class);
        }

        return query.fetch(EntityInstances.ENTITY_UNIQUE);
    }

    public List<ItemResultTransfer> getItemResultTransfers(UserVisitSearch userVisitSearch) {
        var entityIds = getItemIdsFromUserVisitSearch(userVisitSearch);
        var itemResultTransfers = new ArrayList<ItemResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeItem = options != null && options.contains(SearchOptions.ItemResultIncludeItem);

        for(var entityUniqueId : entityIds) {
            var item = itemFactory.getEntityFromPK(EntityPermission.READ_ONLY, new ItemPK(entityUniqueId));

            itemResultTransfers.add(new ItemResultTransfer(item.getLastDetail().getItemName(),
                    includeItem ? getItemTransfer(userVisitSearch.getUserVisit(), item) : null));
        }

        return itemResultTransfers;
    }

    public List<ItemObject> getItemObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var itemObjects = new ArrayList<ItemObject>();

        for(var entityUniqueId : getItemIdsFromUserVisitSearch(userVisitSearch)) {
            var item = getItemByPK(new ItemPK(entityUniqueId));

            itemObjects.add(new ItemObject(item));
        }

        return itemObjects;
    }

}
