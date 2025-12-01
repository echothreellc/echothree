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

package com.echothree.model.control.item.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
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
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
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
import com.echothree.model.data.item.common.pk.ItemAliasChecksumTypePK;
import com.echothree.model.data.item.common.pk.ItemAliasTypePK;
import com.echothree.model.data.item.common.pk.ItemCategoryPK;
import com.echothree.model.data.item.common.pk.ItemDeliveryTypePK;
import com.echothree.model.data.item.common.pk.ItemDescriptionPK;
import com.echothree.model.data.item.common.pk.ItemDescriptionTypePK;
import com.echothree.model.data.item.common.pk.ItemDescriptionTypeUseTypePK;
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
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.style.server.entity.StylePath;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import static java.lang.Math.toIntExact;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ItemControl
        extends BaseModelControl {
    
    /** Creates a new instance of ItemControl */
    protected ItemControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Transfer Caches
    // --------------------------------------------------------------------------------

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

    // --------------------------------------------------------------------------------
    //   Item Types
    // --------------------------------------------------------------------------------
    
    public ItemType createItemType(String itemTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var itemType = ItemTypeFactory.getInstance().create(itemTypeName, isDefault, sortOrder);

        sendEvent(itemType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemType;
    }

    public long countItemTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemtypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemType */
    public ItemType getItemTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemTypePK(entityInstance.getEntityUniqueId());

        return ItemTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemType getItemTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemType getItemTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemType getItemTypeByName(String itemTypeName, EntityPermission entityPermission) {
        ItemType itemType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemtypes " +
                        "WHERE ityp_itemtypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemtypes " +
                        "WHERE ityp_itemtypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, itemTypeName);

            itemType = ItemTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemType;
    }

    public ItemType getItemTypeByName(String itemTypeName) {
        return getItemTypeByName(itemTypeName, EntityPermission.READ_ONLY);
    }

    public ItemType getItemTypeByNameForUpdate(String itemTypeName) {
        return getItemTypeByName(itemTypeName, EntityPermission.READ_WRITE);
    }

    public ItemType getDefaultItemType(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemtypes " +
                    "WHERE ityp_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemtypes " +
                    "WHERE ityp_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemTypeFactory.getInstance().prepareStatement(query);

        return ItemTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        var ps = ItemTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemtypes " +
                "ORDER BY ityp_sortorder, ityp_itemtypename " +
                "_LIMIT_");
        
        return ItemTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
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
    
    public ItemTypeDescription createItemTypeDescription(ItemType itemType, Language language, String description,
            BasePK createdBy) {
        var itemTypeDescription = ItemTypeDescriptionFactory.getInstance().create(itemType, language, description);

        sendEvent(itemType.getPrimaryKey(), EventTypes.MODIFY, itemTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemTypeDescription;
    }
    
    public ItemTypeDescription getItemTypeDescription(ItemType itemType, Language language) {
        ItemTypeDescription itemTypeDescription;
        
        try {
            var ps = ItemTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemtypedescriptions " +
                    "WHERE itypd_ityp_itemtypeid = ? AND itypd_lang_languageid = ?");
            
            ps.setLong(1, itemType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            itemTypeDescription = ItemTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemTypeDescription;
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
    
    public ItemDeliveryType createItemDeliveryType(String itemDeliveryTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemDeliveryType = ItemDeliveryTypeFactory.getInstance().create(itemDeliveryTypeName, isDefault, sortOrder);

        sendEvent(itemDeliveryType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemDeliveryType;
    }

    public long countItemDeliveryTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemdeliverytypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemDeliveryType */
    public ItemDeliveryType getItemDeliveryTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemDeliveryTypePK(entityInstance.getEntityUniqueId());

        return ItemDeliveryTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemDeliveryType getItemDeliveryTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemDeliveryTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemDeliveryType getItemDeliveryTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemDeliveryTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemDeliveryType getItemDeliveryTypeByName(String itemDeliveryTypeName, EntityPermission entityPermission) {
        ItemDeliveryType itemDeliveryType;
        
        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdeliverytypes " +
                        "WHERE idlvrtyp_itemdeliverytypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdeliverytypes " +
                        "WHERE idlvrtyp_itemdeliverytypename = ? " +
                        "FOR UPDATE";
            }
            
            var ps = ItemDeliveryTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, itemDeliveryTypeName);
            
            itemDeliveryType = ItemDeliveryTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemDeliveryType;
    }

    public ItemDeliveryType getItemDeliveryTypeByName(String itemDeliveryTypeName) {
        return getItemDeliveryTypeByName(itemDeliveryTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDeliveryType getItemDeliveryTypeByNameForUpdate(String itemDeliveryTypeName) {
        return getItemDeliveryTypeByName(itemDeliveryTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDeliveryType getDefaultItemDeliveryType(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemdeliverytypes " +
                    "WHERE idlvrtyp_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemdeliverytypes " +
                    "WHERE idlvrtyp_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemDeliveryTypeFactory.getInstance().prepareStatement(query);

        return ItemDeliveryTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        var ps = ItemDeliveryTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemdeliverytypes " +
                "ORDER BY idlvrtyp_sortorder, idlvrtyp_itemdeliverytypename " +
                "_LIMIT_");
        
        return ItemDeliveryTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
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
    
    public ItemDeliveryTypeDescription createItemDeliveryTypeDescription(ItemDeliveryType itemDeliveryType, Language language,
            String description, BasePK createdBy) {
        var itemDeliveryTypeDescription = ItemDeliveryTypeDescriptionFactory.getInstance().create(itemDeliveryType, language, description);

        sendEvent(itemDeliveryType.getPrimaryKey(), EventTypes.MODIFY, itemDeliveryTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDeliveryTypeDescription;
    }
    
    public ItemDeliveryTypeDescription getItemDeliveryTypeDescription(ItemDeliveryType itemDeliveryType, Language language) {
        ItemDeliveryTypeDescription itemDeliveryTypeDescription;
        
        try {
            var ps = ItemDeliveryTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemdeliverytypedescriptions " +
                    "WHERE idlvrtypd_idlvrtyp_itemdeliverytypeid = ? AND idlvrtypd_lang_languageid = ?");
            
            ps.setLong(1, itemDeliveryType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            itemDeliveryTypeDescription = ItemDeliveryTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemDeliveryTypeDescription;
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
    
    public ItemInventoryType createItemInventoryType(String itemInventoryTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemInventoryType = ItemInventoryTypeFactory.getInstance().create(itemInventoryTypeName, isDefault, sortOrder);

        sendEvent(itemInventoryType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemInventoryType;
    }

    public long countItemInventoryTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM iteminventorytypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemInventoryType */
    public ItemInventoryType getItemInventoryTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemInventoryTypePK(entityInstance.getEntityUniqueId());

        return ItemInventoryTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemInventoryType getItemInventoryTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemInventoryTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemInventoryType getItemInventoryTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemInventoryTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemInventoryType getItemInventoryTypeByName(String itemInventoryTypeName, EntityPermission entityPermission) {
        ItemInventoryType itemInventoryType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iteminventorytypes " +
                        "WHERE iinvtyp_iteminventorytypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iteminventorytypes " +
                        "WHERE iinvtyp_iteminventorytypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemInventoryTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, itemInventoryTypeName);

            itemInventoryType = ItemInventoryTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemInventoryType;
    }

    public ItemInventoryType getItemInventoryTypeByName(String itemInventoryTypeName) {
        return getItemInventoryTypeByName(itemInventoryTypeName, EntityPermission.READ_ONLY);
    }

    public ItemInventoryType getItemInventoryTypeByNameForUpdate(String itemInventoryTypeName) {
        return getItemInventoryTypeByName(itemInventoryTypeName, EntityPermission.READ_WRITE);
    }

    public ItemInventoryType getDefaultItemInventoryType(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM iteminventorytypes " +
                    "WHERE iinvtyp_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM iteminventorytypes " +
                    "WHERE iinvtyp_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemInventoryTypeFactory.getInstance().prepareStatement(query);

        return ItemInventoryTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        var ps = ItemInventoryTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM iteminventorytypes " +
                "ORDER BY iinvtyp_sortorder, iinvtyp_iteminventorytypename " +
                "_LIMIT_");
        
        return ItemInventoryTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
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
    
    public ItemInventoryTypeDescription createItemInventoryTypeDescription(ItemInventoryType itemInventoryType, Language language,
            String description, BasePK createdBy) {
        var itemInventoryTypeDescription = ItemInventoryTypeDescriptionFactory.getInstance().create(itemInventoryType, language, description);

        sendEvent(itemInventoryType.getPrimaryKey(), EventTypes.MODIFY, itemInventoryTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemInventoryTypeDescription;
    }
    
    public ItemInventoryTypeDescription getItemInventoryTypeDescription(ItemInventoryType itemInventoryType, Language language) {
        ItemInventoryTypeDescription itemInventoryTypeDescription;
        
        try {
            var ps = ItemInventoryTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM iteminventorytypedescriptions " +
                    "WHERE iinvtypd_iinvtyp_iteminventorytypeid = ? AND iinvtypd_lang_languageid = ?");
            
            ps.setLong(1, itemInventoryType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            itemInventoryTypeDescription = ItemInventoryTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemInventoryTypeDescription;
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

    public ItemUseType createItemUseType(String itemUseTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemUseType = ItemUseTypeFactory.getInstance().create(itemUseTypeName, isDefault, sortOrder);

        sendEvent(itemUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemUseType;
    }

    public long countItemUseTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemusetypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemUseType */
    public ItemUseType getItemUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemUseTypePK(entityInstance.getEntityUniqueId());

        return ItemUseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemUseType getItemUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemUseType getItemUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemUseType getItemUseTypeByName(String itemUseTypeName, EntityPermission entityPermission) {
        ItemUseType itemUseType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemusetypes " +
                        "WHERE iutyp_itemusetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemusetypes " +
                        "WHERE iutyp_itemusetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUseTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, itemUseTypeName);

            itemUseType = ItemUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemUseType;
    }

    public ItemUseType getItemUseTypeByName(String itemUseTypeName) {
        return getItemUseTypeByName(itemUseTypeName, EntityPermission.READ_ONLY);
    }

    public ItemUseType getItemUseTypeByNameForUpdate(String itemUseTypeName) {
        return getItemUseTypeByName(itemUseTypeName, EntityPermission.READ_WRITE);
    }

    public ItemUseType getDefaultItemUseType(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemusetypes " +
                    "WHERE iutyp_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemusetypes " +
                    "WHERE iutyp_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemUseTypeFactory.getInstance().prepareStatement(query);

        return ItemUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        var ps = ItemUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemusetypes " +
                "ORDER BY iutyp_sortorder, iutyp_itemusetypename " +
                "_LIMIT_");

        return ItemUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
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

    public ItemUseTypeDescription createItemUseTypeDescription(ItemUseType itemUseType, Language language,
            String description, BasePK createdBy) {
        var itemUseTypeDescription = ItemUseTypeDescriptionFactory.getInstance().create(itemUseType, language, description);

        sendEvent(itemUseType.getPrimaryKey(), EventTypes.MODIFY, itemUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemUseTypeDescription;
    }

    public ItemUseTypeDescription getItemUseTypeDescription(ItemUseType itemUseType, Language language) {
        ItemUseTypeDescription itemUseTypeDescription;
        
        try {
            var ps = ItemUseTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemusetypedescriptions " +
                    "WHERE iutypd_iutyp_itemusetypeid = ? AND iutypd_lang_languageid = ?");
            
            ps.setLong(1, itemUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            itemUseTypeDescription = ItemUseTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUseTypeDescription;
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

        var itemCategory = ItemCategoryFactory.getInstance().create();
        var itemCategoryDetail = ItemCategoryDetailFactory.getInstance().create(session,
                itemCategory, itemCategoryName, parentItemCategory, itemSequence, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemCategory = ItemCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemCategory.getPrimaryKey());
        itemCategory.setActiveDetail(itemCategoryDetail);
        itemCategory.setLastDetail(itemCategoryDetail);
        itemCategory.store();
        
        sendEvent(itemCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return itemCategory;
    }

    public long countItemCategories() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM itemcategories, itemcategorydetails " +
                        "WHERE ic_activedetailid = icdt_itemcategorydetailid");
    }

    public long countItemCategoriesByParentItemCategory(ItemCategory parentItemCategory) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM itemcategories, itemcategorydetails " +
                        "WHERE ic_activedetailid = icdt_itemcategorydetailid " +
                        "AND icdt_parentitemcategoryid = ?",
                parentItemCategory);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemCategory */
    public ItemCategory getItemCategoryByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemCategoryPK(entityInstance.getEntityUniqueId());

        return ItemCategoryFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemCategory getItemCategoryByEntityInstance(EntityInstance entityInstance) {
        return getItemCategoryByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemCategory getItemCategoryByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemCategoryByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemCategory getItemCategoryByName(String itemCategoryName, EntityPermission entityPermission) {
        ItemCategory itemCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategories, itemcategorydetails " +
                        "WHERE ic_activedetailid = icdt_itemcategorydetailid AND icdt_itemcategoryname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategories, itemcategorydetails " +
                        "WHERE ic_activedetailid = icdt_itemcategorydetailid AND icdt_itemcategoryname = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, itemCategoryName);
            
            itemCategory = ItemCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemCategory;
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemcategories, itemcategorydetails " +
                    "WHERE ic_activedetailid = icdt_itemcategorydetailid AND icdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemcategories, itemcategorydetails " +
                    "WHERE ic_activedetailid = icdt_itemcategorydetailid AND icdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemCategoryFactory.getInstance().prepareStatement(query);
        
        return ItemCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemcategories, itemcategorydetails " +
                    "WHERE ic_activedetailid = icdt_itemcategorydetailid " +
                    "ORDER BY icdt_sortorder, icdt_itemcategoryname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemcategories, itemcategorydetails " +
                    "WHERE ic_activedetailid = icdt_itemcategorydetailid " +
                    "FOR UPDATE";
        }

        var ps = ItemCategoryFactory.getInstance().prepareStatement(query);
        
        return ItemCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<ItemCategory> getItemCategories() {
        return getItemCategories(EntityPermission.READ_ONLY);
    }
    
    public List<ItemCategory> getItemCategoriesForUpdate() {
        return getItemCategories(EntityPermission.READ_WRITE);
    }
    
    private List<ItemCategory> getItemCategoriesByParentItemCategory(ItemCategory parentItemCategory,
            EntityPermission entityPermission) {
        List<ItemCategory> itemCategories;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategories, itemcategorydetails " +
                        "WHERE ic_activedetailid = icdt_itemcategorydetailid AND icdt_parentitemcategoryid = ? " +
                        "ORDER BY icdt_sortorder, icdt_itemcategoryname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategories, itemcategorydetails " +
                        "WHERE ic_activedetailid = icdt_itemcategorydetailid AND icdt_parentitemcategoryid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemCategoryFactory.getInstance().prepareStatement(query);

            ps.setLong(1, parentItemCategory.getPrimaryKey().getEntityId());

            itemCategories = ItemCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemCategories;
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
            var itemCategory = ItemCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemCategoryDetailValue.getItemCategoryPK());
            var itemCategoryDetail = itemCategory.getActiveDetailForUpdate();
            
            itemCategoryDetail.setThruTime(session.START_TIME_LONG);
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
            
            itemCategoryDetail = ItemCategoryDetailFactory.getInstance().create(itemCategoryPK,
                    itemCategoryName, parentItemCategoryPK, itemSequencePK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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
        itemCategoryDetail.setThruTime(session.START_TIME_LONG);
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
    
    public ItemCategoryDescription createItemCategoryDescription(ItemCategory itemCategory, Language language, String description, BasePK createdBy) {
        var itemCategoryDescription = ItemCategoryDescriptionFactory.getInstance().create(itemCategory, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(itemCategory.getPrimaryKey(), EventTypes.MODIFY, itemCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemCategoryDescription;
    }
    
    private ItemCategoryDescription getItemCategoryDescription(ItemCategory itemCategory, Language language, EntityPermission entityPermission) {
        ItemCategoryDescription itemCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategorydescriptions " +
                        "WHERE icd_ic_itemcategoryid = ? AND icd_lang_languageid = ? AND icd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategorydescriptions " +
                        "WHERE icd_ic_itemcategoryid = ? AND icd_lang_languageid = ? AND icd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemCategoryDescription = ItemCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemCategoryDescription;
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
        List<ItemCategoryDescription> itemCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategorydescriptions, languages " +
                        "WHERE icd_ic_itemcategoryid = ? AND icd_thrutime = ? AND icd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcategorydescriptions " +
                        "WHERE icd_ic_itemcategoryid = ? AND icd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemCategoryDescriptions = ItemCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemCategoryDescriptions;
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
            var itemCategoryDescription = ItemCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemCategoryDescriptionValue.getPrimaryKey());
            
            itemCategoryDescription.setThruTime(session.START_TIME_LONG);
            itemCategoryDescription.store();

            var itemCategory = itemCategoryDescription.getItemCategory();
            var language = itemCategoryDescription.getLanguage();
            var description = itemCategoryDescriptionValue.getDescription();
            
            itemCategoryDescription = ItemCategoryDescriptionFactory.getInstance().create(itemCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemCategory.getPrimaryKey(), EventTypes.MODIFY, itemCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemCategoryDescription(ItemCategoryDescription itemCategoryDescription, BasePK deletedBy) {
        itemCategoryDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public Item createItem(String itemName, ItemType itemType, ItemUseType itemUseType, ItemCategory itemCategory,
            ItemAccountingCategory itemAccountingCategory, ItemPurchasingCategory itemPurchasingCategory, Party companyParty,
            ItemDeliveryType itemDeliveryType, ItemInventoryType itemInventoryType, Boolean inventorySerialized, Sequence serialNumberSequence,
            Boolean shippingChargeExempt, Long shippingStartTime, Long shippingEndTime, Long salesOrderStartTime, Long salesOrderEndTime,
            Long purchaseOrderStartTime, Long purchaseOrderEndTime, Boolean allowClubDiscounts, Boolean allowCouponDiscounts, Boolean allowAssociatePayments,
            UnitOfMeasureKind unitOfMeasureKind, ItemPriceType itemPriceType, CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy,
            StylePath stylePath, BasePK createdBy) {
        var item = ItemFactory.getInstance().create();
        var itemDetail = ItemDetailFactory.getInstance().create(item, itemName, itemType, itemUseType, itemCategory, itemAccountingCategory,
                itemPurchasingCategory, companyParty, itemDeliveryType, itemInventoryType, inventorySerialized, serialNumberSequence, shippingChargeExempt,
                shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime, purchaseOrderStartTime, purchaseOrderEndTime, allowClubDiscounts,
                allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKind, itemPriceType, cancellationPolicy, returnPolicy, stylePath,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        item = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, item.getPrimaryKey());
        item.setActiveDetail(itemDetail);
        item.setLastDetail(itemDetail);
        item.store();
        
        sendEvent(item.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return item;
    }
    
    public long countItems() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid");
    }

    public long countItemsByItemCategory(ItemCategory itemCategory) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_ic_itemcategoryid = ?",
                itemCategory);
    }

    public long countItemsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_iactgc_itemaccountingcategoryid = ?",
                itemAccountingCategory);
    }

    public long countItemsByItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_iprchc_itempurchasingcategoryid = ?",
                itemPurchasingCategory);
    }

    public long countItemsByCompanyParty(Party companyParty) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_companypartyid = ?",
                companyParty);
    }

    public long countItemsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_uomk_unitofmeasurekindid = ?",
                unitOfMeasureKind);
    }

    public long countItemsByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_cnclplcy_cancellationpolicyid = ?",
                cancellationPolicy);
    }

    public long countItemsByReturnPolicy(ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_rtnplcy_returnpolicyid = ?",
                returnPolicy);
    }

    public long countItemsByStylePath(StylePath stylePath) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM items, itemdetails " +
                "WHERE itm_activedetailid = itmdt_itemdetailid " +
                "AND itmdt_stylpth_stylepathid = ?",
                stylePath);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Item */
    public Item getItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemPK(entityInstance.getEntityUniqueId());

        return ItemFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Item getItemByEntityInstance(EntityInstance entityInstance) {
        return getItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Item getItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Item> getItems(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM items, itemdetails " +
                    "WHERE itm_activedetailid = itmdt_itemdetailid " +
                    "ORDER BY itmdt_itemname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM items, itemdetails " +
                    "WHERE itm_activedetailid = itmdt_itemdetailid " +
                    "FOR UPDATE";
        }

        var ps = ItemFactory.getInstance().prepareStatement(query);

        return ItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }


    public List<Item> getItems() {
        return getItems(EntityPermission.READ_ONLY);
    }

    public List<Item> getItemsForUpdate() {
        return getItems(EntityPermission.READ_WRITE);
    }

    private List<Item> getItemsByItemCategory(EntityPermission entityPermission, ItemCategory itemCategory) {
        List<Item> items;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_ic_itemcategoryid = ? " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_ic_itemcategoryid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemCategory.getPrimaryKey().getEntityId());

            items = ItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return items;
    }


    public List<Item> getItemsByItemCategory(ItemCategory itemCategory) {
        return getItemsByItemCategory(EntityPermission.READ_ONLY, itemCategory);
    }

    public List<Item> getItemsByItemCategoryForUpdate(ItemCategory itemCategory) {
        return getItemsByItemCategory(EntityPermission.READ_WRITE, itemCategory);
    }

    private List<Item> getItemsByItemAccountingCategory(EntityPermission entityPermission, ItemAccountingCategory itemAccountingCategory) {
        List<Item> items;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_iactgc_itemaccountingcategoryid = ? " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_iactgc_itemaccountingcategoryid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemAccountingCategory.getPrimaryKey().getEntityId());

            items = ItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return items;
    }

    public List<Item> getItemsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return getItemsByItemAccountingCategory(EntityPermission.READ_ONLY, itemAccountingCategory);
    }

    public List<Item> getItemsByItemAccountingCategoryForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return getItemsByItemAccountingCategory(EntityPermission.READ_WRITE, itemAccountingCategory);
    }

    private List<Item> getItemsByItemPurchasingCategory(EntityPermission entityPermission, ItemPurchasingCategory itemPurchasingCategory) {
        List<Item> items;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_iprchc_itempurchasingcategoryid = ? " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_iprchc_itempurchasingcategoryid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemPurchasingCategory.getPrimaryKey().getEntityId());

            items = ItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return items;
    }


    public List<Item> getItemsByItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory) {
        return getItemsByItemPurchasingCategory(EntityPermission.READ_ONLY, itemPurchasingCategory);
    }

    public List<Item> getItemsByItemPurchasingCategoryForUpdate(ItemPurchasingCategory itemPurchasingCategory) {
        return getItemsByItemPurchasingCategory(EntityPermission.READ_WRITE, itemPurchasingCategory);
    }

    private List<Item> getItemsByCompanyParty(EntityPermission entityPermission, Party companyParty) {
        List<Item> items;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_companypartyid = ? " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_companypartyid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemFactory.getInstance().prepareStatement(query);

            ps.setLong(1, companyParty.getPrimaryKey().getEntityId());

            items = ItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return items;
    }


    public List<Item> getItemsByCompanyParty(Party companyParty) {
        return getItemsByCompanyParty(EntityPermission.READ_ONLY, companyParty);
    }

    public List<Item> getItemsByCompanyPartyForUpdate(Party companyParty) {
        return getItemsByCompanyParty(EntityPermission.READ_WRITE, companyParty);
    }

    private List<Item> getItemsByUnitOfMeasureKind(EntityPermission entityPermission, UnitOfMeasureKind unitOfMeasureKind) {
        List<Item> items;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_uomk_unitofmeasurekindid = ? " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_uomk_unitofmeasurekindid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemFactory.getInstance().prepareStatement(query);

            ps.setLong(1, unitOfMeasureKind.getPrimaryKey().getEntityId());

            items = ItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return items;
    }

    public List<Item> getItemsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getItemsByUnitOfMeasureKind(EntityPermission.READ_ONLY, unitOfMeasureKind);
    }

    public List<Item> getItemsByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getItemsByUnitOfMeasureKind(EntityPermission.READ_WRITE, unitOfMeasureKind);
    }

    private Item getItemByName(String itemName, EntityPermission entityPermission) {
        Item item;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_itemname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM items, itemdetails " +
                        "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_itemname = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, itemName);
            
            item = ItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return item;
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
                item = itemAlias.getItem(session, entityPermission);
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
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
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
            var item = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDetailValue.getItemPK());
            var itemDetail = item.getActiveDetailForUpdate();
            
            itemDetail.setThruTime(session.START_TIME_LONG);
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
                    
            itemDetail = ItemDetailFactory.getInstance().create(itemPK, itemName, itemTypePK, itemUseTypePK, itemCategoryPK, itemAccountingCategoryPK,
                    itemPurchasingCategoryPK, companyPartyPK, itemDeliveryTypePK, itemInventoryTypePK, inventorySerialized, serialNumberSequencePK,
                    shippingChargeExempt, shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime, purchaseOrderStartTime,
                    purchaseOrderEndTime, allowClubDiscounts, allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKindPK, itemPriceTypePK,
                    cancellationPolicyPK, returnPolicyPK, stylePathPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            item.setActiveDetail(itemDetail);
            item.setLastDetail(itemDetail);
            
            sendEvent(itemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public Item getItemByPK(ItemPK itemPK) {
        return ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, itemPK);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
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

        var itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().create(item, unitOfMeasureType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitOfMeasureType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitOfMeasureType;
    }

    public long countItemUnitOfMeasureTypesByItem(Item item) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemunitofmeasuretypes
                WHERE iuomt_itm_itemid = ? AND iuomt_thrutime = ?""",
                item, Session.MAX_TIME);
    }

    public long countItemUnitOfMeasureTypesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemunitofmeasuretypes
                WHERE iuomt_uomt_unitofmeasuretypeid = ? AND iuomt_thrutime = ?""",
                unitOfMeasureType, Session.MAX_TIME);
    }

    private ItemUnitOfMeasureType getItemUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        ItemUnitOfMeasureType itemUnitOfMeasureType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_itm_itemid = ? AND iuomt_uomt_unitofmeasuretypeid = ? AND iuomt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_itm_itemid = ? AND iuomt_uomt_unitofmeasuretypeid = ? AND iuomt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitOfMeasureType;
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
        ItemUnitOfMeasureType itemUnitOfMeasureType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_itm_itemid = ? AND iuomt_isdefault = 1 AND iuomt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_itm_itemid = ? AND iuomt_isdefault = 1 AND iuomt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitOfMeasureType;
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
        List<ItemUnitOfMeasureType> itemUnitOfMeasureTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes, unitofmeasuretypes, unitofmeasuretypedetails, unitofmeasurekinds, unitofmeasurekinddetails " +
                        "WHERE iuomt_itm_itemid = ? AND iuomt_thrutime = ? " +
                        "AND iuomt_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomk_unitofmeasurekindid AND uomk_lastdetailid = uomkdt_unitofmeasurekinddetailid " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, uomkdt_sortorder, uomkdt_unitofmeasurekindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_itm_itemid = ? AND iuomt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitOfMeasureTypes = ItemUnitOfMeasureTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitOfMeasureTypes;
    }
    
    public List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByItem(Item item) {
        return getItemUnitOfMeasureTypesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByItemForUpdate(Item item) {
        return getItemUnitOfMeasureTypesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitOfMeasureType> getItemUnitOfMeasureTypesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemUnitOfMeasureType> itemUnitOfMeasureTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes, items, itemdetails " +
                        "WHERE iuomt_uomt_unitofmeasuretypeid = ? AND iuomt_thrutime = ? " +
                        "AND iuomt_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_uomt_unitofmeasuretypeid = ? AND iuomt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitOfMeasureTypes = ItemUnitOfMeasureTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitOfMeasureTypes;
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
            var itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitOfMeasureTypeValue.getPrimaryKey());
            
            itemUnitOfMeasureType.setThruTime(session.START_TIME_LONG);
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
            
            itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().create(itemPK, unitOfMeasureTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemUnitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateItemUnitOfMeasureTypeFromValue(ItemUnitOfMeasureTypeValue itemUnitOfMeasureTypeValue, BasePK updatedBy) {
        updateItemUnitOfMeasureTypeFromValue(itemUnitOfMeasureTypeValue, true, updatedBy);
    }
    
    public void deleteItemUnitOfMeasureType(ItemUnitOfMeasureType itemUnitOfMeasureType, BasePK deletedBy) {
        var vendorControl = Session.getModelController(VendorControl.class);
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
        OfferItemLogic.getInstance().deleteOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        vendorControl.deleteVendorItemCostsByItemAndUnitOfMeasureType(item, unitOfMeasureType, deletedBy);
        
        itemUnitOfMeasureType.setThruTime(session.START_TIME_LONG);
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
    
    public ItemShippingTime createItemShippingTime(Item item, CustomerType customerType, Long shippingStartTime, Long shippingEndTime,
            BasePK createdBy) {
        var itemShippingTime = ItemShippingTimeFactory.getInstance().create(item, customerType,
                shippingStartTime, shippingEndTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemShippingTime.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemShippingTime;
    }
    
    private ItemShippingTime getItemShippingTime(Item item, CustomerType customerType, EntityPermission entityPermission) {
        ItemShippingTime itemShippingTime;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemshippingtimes " +
                        "WHERE istim_itm_itemid = ? AND istim_cuty_customertypeid = ? AND istim_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemshippingtimes " +
                        "WHERE istim_itm_itemid = ? AND istim_cuty_customertypeid = ? AND istim_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemShippingTimeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, customerType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemShippingTime = ItemShippingTimeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemShippingTime;
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
        List<ItemShippingTime> itemShippingTimes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemshippingtimes, customertypes, customertypedetails " +
                        "WHERE istim_itm_itemid = ? AND istim_thrutime = ? " +
                        "AND istim_cuty_customertypeid = cuty_customertypeid " +
                        "AND cuty_lastdetailid = cutydt_customertypedetailid " +
                        "ORDER BY cutydt_sortorder, cutydt_customertypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemshippingtimes " +
                        "WHERE istim_itm_itemid = ? AND istim_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemShippingTimeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemShippingTimes = ItemShippingTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemShippingTimes;
    }
    
    public List<ItemShippingTime> getItemShippingTimesByItem(Item item) {
        return getItemShippingTimesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemShippingTime> getItemShippingTimesByItemForUpdate(Item item) {
        return getItemShippingTimesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemShippingTime> getItemShippingTimesByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        List<ItemShippingTime> itemShippingTimes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemshippingtimes, items, itemdetails " +
                        "WHERE istim_cuty_customertypeid = ? AND istim_thrutime = ? " +
                        "AND istim_itm_itemid = itm_itemid " +
                        "AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY itmdt_itemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemshippingtimes " +
                        "WHERE istim_cuty_customertypeid = ? AND istim_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemShippingTimeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, customerType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemShippingTimes = ItemShippingTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemShippingTimes;
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
            var itemShippingTime = ItemShippingTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemShippingTimePK);
            
            itemShippingTime.setThruTime(session.START_TIME_LONG);
            itemShippingTime.store();

            var itemPK = itemShippingTime.getItemPK();
            var customerTypePK = itemShippingTime.getCustomerTypePK();
            var shippingStartTime = itemShippingTimeValue.getShippingStartTime();
            var shippingEndTime = itemShippingTimeValue.getShippingEndTime();
            
            itemShippingTime = ItemShippingTimeFactory.getInstance().create(itemPK, customerTypePK, shippingStartTime, shippingEndTime,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemShippingTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public ItemShippingTimeTransfer getItemShippingTimeTransfer(UserVisit userVisit, ItemShippingTime itemShippingTime) {
        return itemShippingTime == null? null: itemShippingTimeTransferCache.getTransfer(userVisit, itemShippingTime);
    }
    
    public ItemShippingTimeTransfer getItemShippingTimeTransfer(UserVisit userVisit, Item item, CustomerType customerType) {
        return getItemShippingTimeTransfer(userVisit, getItemShippingTime(item, customerType));
    }
    
    public List<ItemShippingTimeTransfer> getItemShippingTimeTransfersByItem(UserVisit userVisit, Item item) {
        var itemShippingTimes = getItemShippingTimesByItem(item);
        List<ItemShippingTimeTransfer> itemShippingTimeTransfers = new ArrayList<>(itemShippingTimes.size());
        
        itemShippingTimes.forEach((itemShippingTime) -> {
            itemShippingTimeTransfers.add(itemShippingTimeTransferCache.getTransfer(userVisit, itemShippingTime));
        });
        
        return itemShippingTimeTransfers;
    }
    
    public void deleteItemShippingTime(ItemShippingTime itemShippingTime, BasePK deletedBy) {
        itemShippingTime.setThruTime(session.START_TIME_LONG);
        
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

    public ItemAliasChecksumType createItemAliasChecksumType(String itemAliasChecksumTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemAliasChecksumType = ItemAliasChecksumTypeFactory.getInstance().create(itemAliasChecksumTypeName, isDefault, sortOrder);

        sendEvent(itemAliasChecksumType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemAliasChecksumType;
    }

    public long countItemAliasChecksumTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemaliaschecksumtypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemAliasChecksumType */
    public ItemAliasChecksumType getItemAliasChecksumTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemAliasChecksumTypePK(entityInstance.getEntityUniqueId());

        return ItemAliasChecksumTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemAliasChecksumTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemAliasChecksumTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByName(String itemAliasChecksumTypeName, EntityPermission entityPermission) {
        ItemAliasChecksumType itemAliasChecksumType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliaschecksumtypes " +
                        "WHERE iact_itemaliaschecksumtypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliaschecksumtypes " +
                        "WHERE iact_itemaliaschecksumtypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasChecksumTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, itemAliasChecksumTypeName);

            itemAliasChecksumType = ItemAliasChecksumTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemAliasChecksumType;
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByName(String itemAliasChecksumTypeName) {
        return getItemAliasChecksumTypeByName(itemAliasChecksumTypeName, EntityPermission.READ_ONLY);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByNameForUpdate(String itemAliasChecksumTypeName) {
        return getItemAliasChecksumTypeByName(itemAliasChecksumTypeName, EntityPermission.READ_WRITE);
    }

    public ItemAliasChecksumType getDefaultItemAliasChecksumType(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemaliaschecksumtypes " +
                    "WHERE iact_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemaliaschecksumtypes " +
                    "WHERE iact_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemAliasChecksumTypeFactory.getInstance().prepareStatement(query);

        return ItemAliasChecksumTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        var ps = ItemAliasChecksumTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemaliaschecksumtypes " +
                "ORDER BY iact_sortorder, iact_itemaliaschecksumtypename " +
                "_LIMIT_");

        return ItemAliasChecksumTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
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

    public ItemAliasChecksumTypeDescription createItemAliasChecksumTypeDescription(ItemAliasChecksumType itemAliasChecksumType,
            Language language, String description, BasePK createdBy) {
        var itemAliasChecksumTypeDescription = ItemAliasChecksumTypeDescriptionFactory.getInstance().create(itemAliasChecksumType, language, description);

        sendEvent(itemAliasChecksumType.getPrimaryKey(), EventTypes.MODIFY, itemAliasChecksumTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemAliasChecksumTypeDescription;
    }

    public ItemAliasChecksumTypeDescription getItemAliasChecksumTypeDescription(ItemAliasChecksumType itemAliasChecksumType, Language language) {
        ItemAliasChecksumTypeDescription itemAliasChecksumTypeDescription;

        try {
            var ps = ItemAliasChecksumTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemaliaschecksumtypedescriptions " +
                    "WHERE iactd_iact_itemaliaschecksumtypeid = ? AND iactd_lang_languageid = ?");

            ps.setLong(1, itemAliasChecksumType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());

            itemAliasChecksumTypeDescription = ItemAliasChecksumTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemAliasChecksumTypeDescription;
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

        var itemAliasType = ItemAliasTypeFactory.getInstance().create();
        var itemAliasTypeDetail = ItemAliasTypeDetailFactory.getInstance().create(session, itemAliasType, itemAliasTypeName, validationPattern,
                itemAliasChecksumType, allowMultiple, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemAliasType = ItemAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemAliasType.getPrimaryKey());
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

        return ItemAliasTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemAliasType getItemAliasTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemAliasType getItemAliasTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemAliasType getItemAliasTypeByPK(ItemAliasTypePK pk) {
        return ItemAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countItemAliasTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemaliastypes, itemaliastypedetails " +
                "WHERE iat_activedetailid = iatdt_itemaliastypedetailid");
    }

    public ItemAliasType getItemAliasTypeByName(String itemAliasTypeName, EntityPermission entityPermission) {
        ItemAliasType itemAliasType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliastypes, itemaliastypedetails " +
                        "WHERE iat_activedetailid = iatdt_itemaliastypedetailid AND iatdt_itemaliastypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliastypes, itemaliastypedetails " +
                        "WHERE iat_activedetailid = iatdt_itemaliastypedetailid AND iatdt_itemaliastypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, itemAliasTypeName);
            
            itemAliasType = ItemAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliasType;
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemaliastypes, itemaliastypedetails " +
                    "WHERE iat_activedetailid = iatdt_itemaliastypedetailid AND iatdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemaliastypes, itemaliastypedetails " +
                    "WHERE iat_activedetailid = iatdt_itemaliastypedetailid AND iatdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemAliasTypeFactory.getInstance().prepareStatement(query);
        
        return ItemAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemaliastypes, itemaliastypedetails " +
                    "WHERE iat_activedetailid = iatdt_itemaliastypedetailid " +
                    "ORDER BY iatdt_sortorder, iatdt_itemaliastypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemaliastypes, itemaliastypedetails " +
                    "WHERE iat_activedetailid = iatdt_itemaliastypedetailid " +
                    "FOR UPDATE";
        }

        var ps = ItemAliasTypeFactory.getInstance().prepareStatement(query);
        
        return ItemAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
            final var itemAliasType = ItemAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAliasTypeDetailValue.getItemAliasTypePK());
            var itemAliasTypeDetail = itemAliasType.getActiveDetailForUpdate();
            
            itemAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            itemAliasTypeDetail = ItemAliasTypeDetailFactory.getInstance().create(itemAliasTypePK, itemAliasTypeName,
                    validationPattern, itemAliasChecksumTypePK, allowMultiple, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            itemAliasType.setActiveDetail(itemAliasTypeDetail);
            itemAliasType.setLastDetail(itemAliasTypeDetail);
            
            sendEvent(itemAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateItemAliasTypeFromValue(final ItemAliasTypeDetailValue itemAliasTypeDetailValue, final BasePK updatedBy) {
        updateItemAliasTypeFromValue(itemAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteItemAliasType(ItemAliasType itemAliasType, BasePK deletedBy) {
        var vendorControl = Session.getModelController(VendorControl.class);

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
        itemAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public ItemAliasTypeDescription createItemAliasTypeDescription(ItemAliasType itemAliasType, Language language,
            String description, BasePK createdBy) {
        var itemAliasTypeDescription = ItemAliasTypeDescriptionFactory.getInstance().create(session,
                itemAliasType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(itemAliasType.getPrimaryKey(), EventTypes.MODIFY, itemAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemAliasTypeDescription;
    }
    
    private ItemAliasTypeDescription getItemAliasTypeDescription(ItemAliasType itemAliasType, Language language, EntityPermission entityPermission) {
        ItemAliasTypeDescription itemAliasTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliastypedescriptions " +
                        "WHERE iatd_iat_itemaliastypeid = ? AND iatd_lang_languageid = ? AND iatd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliastypedescriptions " +
                        "WHERE iatd_iat_itemaliastypeid = ? AND iatd_lang_languageid = ? AND iatd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemAliasTypeDescription = ItemAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliasTypeDescription;
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
        List<ItemAliasTypeDescription> itemAliasTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliastypedescriptions, languages " +
                        "WHERE iatd_iat_itemaliastypeid = ? AND iatd_thrutime = ? AND iatd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliastypedescriptions " +
                        "WHERE iatd_iat_itemaliastypeid = ? AND iatd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemAliasTypeDescriptions = ItemAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliasTypeDescriptions;
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
            var itemAliasTypeDescription = ItemAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAliasTypeDescriptionValue.getPrimaryKey());
            
            itemAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            itemAliasTypeDescription.store();

            var itemAliasType = itemAliasTypeDescription.getItemAliasType();
            var language = itemAliasTypeDescription.getLanguage();
            var description = itemAliasTypeDescriptionValue.getDescription();
            
            itemAliasTypeDescription = ItemAliasTypeDescriptionFactory.getInstance().create(itemAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemAliasType.getPrimaryKey(), EventTypes.MODIFY, itemAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemAliasTypeDescription(ItemAliasTypeDescription itemAliasTypeDescription, BasePK deletedBy) {
        itemAliasTypeDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public ItemAlias createItemAlias(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType, String alias,
            BasePK createdBy) {
        var itemAlias = ItemAliasFactory.getInstance().create(item, unitOfMeasureType, itemAliasType, alias,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemAlias;
    }

    public long countItemAliasesByItem(Item item) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemaliases
                WHERE itmal_itm_itemid = ? AND itmal_thrutime = ?""",
                item, Session.MAX_TIME);
    }

    public long countItemAliasesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemaliases
                WHERE itmal_uomt_unitofmeasuretypeid = ? AND itmal_thrutime = ?""",
                unitOfMeasureType, Session.MAX_TIME);
    }

    public long countItemAliasesByItemAliasType(ItemAliasType itemAliasType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemaliases
                WHERE itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ?""",
                itemAliasType, Session.MAX_TIME);
    }

    public long countItemAliases(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemaliases " +
                "WHERE itmal_itm_itemid = ? AND itmal_uomt_unitofmeasuretypeid = ? AND itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ?",
                item, unitOfMeasureType, itemAliasType, Session.MAX_TIME);
    }
    
    private ItemAlias getItemAliasByAlias(String alias, EntityPermission entityPermission) {
        ItemAlias itemAlias;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_alias = ? AND itmal_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_alias = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, alias);
            ps.setLong(2, Session.MAX_TIME);
            
            itemAlias = ItemAliasFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAlias;
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
        List<ItemAlias> itemAliases;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_itm_itemid = ? AND itmal_uomt_unitofmeasuretypeid = ? AND itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ? " +
                        "ORDER BY itmal_alias " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_itm_itemid = ? AND itmal_uomt_unitofmeasuretypeid = ? AND itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, itemAliasType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            itemAliases = ItemAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliases;
    }
    
    public List<ItemAlias> getItemAliases(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType) {
        return getItemAliases(item, unitOfMeasureType, itemAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesForUpdate(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType) {
        return getItemAliases(item, unitOfMeasureType, itemAliasType, EntityPermission.READ_WRITE);
    }
    
    private List<ItemAlias> getItemAliasesByItem(Item item, EntityPermission entityPermission) {
        List<ItemAlias> itemAliases;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases, itemaliastypes, itemaliastypedetails " +
                        "WHERE itmal_itm_itemid = ? AND itmal_thrutime = ? " +
                        "AND itmal_iat_itemaliastypeid = iat_itemaliastypeid AND iat_lastdetailid = iatdt_itemaliastypedetailid " +
                        "ORDER BY iatdt_sortorder, iatdt_itemaliastypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_itm_itemid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemAliases = ItemAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliases;
    }
    
    public List<ItemAlias> getItemAliasesByItem(Item item) {
        return getItemAliasesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesByItemForUpdate(Item item) {
        return getItemAliasesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemAlias> getItemAliasesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemAlias> itemAliases;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases, items, itemdetails " +
                        "WHERE itmal_uomt_unitofmeasuretypeid = ? AND itmal_thrutime = ? " +
                        "AND itmal_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_uomt_unitofmeasuretypeid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemAliases = ItemAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliases;
    }
    
    public List<ItemAlias> getItemAliasesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemAliasesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemAliasesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private List<ItemAlias> getItemAliasesByItemAliasType(ItemAliasType itemAliasType, EntityPermission entityPermission) {
        List<ItemAlias> itemAliases;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases, items, itemdetails " +
                        "WHERE itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ? " +
                        "AND itmal_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemAliases = ItemAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliases;
    }
    
    public List<ItemAlias> getItemAliasesByItemAliasType(ItemAliasType itemAliasType) {
        return getItemAliasesByItemAliasType(itemAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAlias> getItemAliasesByItemAliasTypeForUpdate(ItemAliasType itemAliasType) {
        return getItemAliasesByItemAliasType(itemAliasType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemAlias> getItemAliasesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType) {
        List<ItemAlias> itemAliases;
        
        try {
            var ps = ItemAliasFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemaliases " +
                    "WHERE itmal_itm_itemid = ? AND itmal_uomt_unitofmeasuretypeid = ? AND itmal_thrutime = ? " +
                    "FOR UPDATE " +
                    "_LIMIT_");
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemAliases = ItemAliasFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAliases;
    }
    
    public void updateItemAliasFromValue(ItemAliasValue itemAliasValue, BasePK updatedBy) {
        if(itemAliasValue.hasBeenModified()) {
            var itemAliasPK = itemAliasValue.getPrimaryKey();
            var itemAlias = ItemAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemAliasPK);
            
            itemAlias.setThruTime(session.START_TIME_LONG);
            itemAlias.store();

            var itemPK = itemAlias.getItemPK();
            var unitOfMeasureTypePK = itemAliasValue.getUnitOfMeasureTypePK();
            var itemAliasTypePK = itemAliasValue.getItemAliasTypePK();
            var alias = itemAliasValue.getAlias();
            
            itemAlias = ItemAliasFactory.getInstance().create(itemPK, unitOfMeasureTypePK, itemAliasTypePK, alias,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        itemAlias.setThruTime(session.START_TIME_LONG);
        
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
        deleteItemAliases(getItemAliasesByItemAndUnitOfMeasureType(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Kit Options
    // --------------------------------------------------------------------------------
    
    public ItemKitOption createItemKitOption(Item item, Boolean allowPartialShipments, BasePK createdBy) {
        var itemKitOption = ItemKitOptionFactory.getInstance().create(item, allowPartialShipments,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemKitOption.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemKitOption;
    }
    
    private ItemKitOption getItemKitOption(Item item, EntityPermission entityPermission) {
        ItemKitOption itemKitOption;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitoptions " +
                        "WHERE ikopt_itm_itemid = ? AND ikopt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitoptions " +
                        "WHERE ikopt_itm_itemid = ? AND ikopt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitOptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemKitOption = ItemKitOptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitOption;
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
            var itemKitOption = ItemKitOptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemKitOptionPK);
            
            itemKitOption.setThruTime(session.START_TIME_LONG);
            itemKitOption.store();

            var itemPK = itemKitOption.getItemPK();
            var allowPartialShipments = itemKitOptionValue.getAllowPartialShipments();
            
            itemKitOption = ItemKitOptionFactory.getInstance().create(itemPK, allowPartialShipments,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemPK, EventTypes.MODIFY, itemKitOption.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemKitOption(ItemKitOption itemKitOption, BasePK deletedBy) {
        itemKitOption.setThruTime(session.START_TIME_LONG);
        
        sendEvent(itemKitOption.getItemPK(), EventTypes.MODIFY, itemKitOption.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    public ItemCountryOfOrigin createItemCountryOfOrigin(Item item, GeoCode countryGeoCode, Integer percent, BasePK createdBy) {
        var itemCountryOfOrigin = ItemCountryOfOriginFactory.getInstance().create(item, countryGeoCode, percent,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemCountryOfOrigin.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemCountryOfOrigin;
    }
    
    public long countItemCountryOfOriginsByCountryGeoCode(GeoCode countryGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemcountryoforigins " +
                "WHERE icoorgn_countrygeocodeid = ? AND icoorgn_thrutime = ?",
                countryGeoCode, Session.MAX_TIME_LONG);
    }

    private ItemCountryOfOrigin getItemCountryOfOrigin(Item item, GeoCode countryGeoCode, EntityPermission entityPermission) {
        ItemCountryOfOrigin itemCountryOfOrigin;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcountryoforigins " +
                        "WHERE icoorgn_itm_itemid = ? AND icoorgn_countrygeocodeid = ? AND icoorgn_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcountryoforigins " +
                        "WHERE icoorgn_itm_itemid = ? AND icoorgn_countrygeocodeid = ? AND icoorgn_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemCountryOfOriginFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, countryGeoCode.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemCountryOfOrigin = ItemCountryOfOriginFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemCountryOfOrigin;
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
        List<ItemCountryOfOrigin> itemCountryOfOrigins;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcountryoforigins, geocodes, geocodedetails " +
                        "WHERE icoorgn_itm_itemid = ? AND icoorgn_thrutime = ? " +
                        "AND icoorgn_countrygeocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid " +
                        "ORDER BY geodt_sortorder, geodt_geocodename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcountryoforigins " +
                        "WHERE icoorgn_itm_itemid = ? AND icoorgn_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemCountryOfOriginFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemCountryOfOrigins = ItemCountryOfOriginFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemCountryOfOrigins;
    }
    
    public List<ItemCountryOfOrigin> getItemCountryOfOriginsByItem(Item item) {
        return getItemCountryOfOriginsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemCountryOfOrigin> getItemCountryOfOriginsByItemForUpdate(Item item) {
        return getItemCountryOfOriginsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemCountryOfOrigin> getItemCountryOfOriginsByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        List<ItemCountryOfOrigin> itemCountryOfOrigins;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcountryoforigins, items, itemdetails " +
                        "WHERE icoorgn_countrygeocodeid = ? AND icoorgn_thrutime = ? " +
                        "AND icoorgn_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY itmdt_itemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemcountryoforigins " +
                        "WHERE icoorgn_countrygeocodeid = ? AND icoorgn_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemCountryOfOriginFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, countryGeoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemCountryOfOrigins = ItemCountryOfOriginFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemCountryOfOrigins;
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
            var itemCountryOfOrigin = ItemCountryOfOriginFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemCountryOfOriginPK);
            
            itemCountryOfOrigin.setThruTime(session.START_TIME_LONG);
            itemCountryOfOrigin.store();

            var itemPK = itemCountryOfOrigin.getItemPK();
            var countryGeoCodePK = itemCountryOfOrigin.getCountryGeoCodePK();
            var percent = itemCountryOfOriginValue.getPercent();
            
            itemCountryOfOrigin = ItemCountryOfOriginFactory.getInstance().create(itemPK, countryGeoCodePK, percent, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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
        itemCountryOfOrigin.setThruTime(session.START_TIME_LONG);
        
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
    
    public ItemKitMember createItemKitMember(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Item memberItem, InventoryCondition memberInventoryCondition, UnitOfMeasureType memberUnitOfMeasureType,
            Long quantity, BasePK createdBy) {
        var itemKitMember = ItemKitMemberFactory.getInstance().create(item, inventoryCondition, unitOfMeasureType,
                memberItem, memberInventoryCondition, memberUnitOfMeasureType, quantity, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemKitMember.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemKitMember;
    }
    
    private ItemKitMember getItemKitMember(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Item memberItem, InventoryCondition memberInventoryCondition, UnitOfMeasureType memberUnitOfMeasureType,
            EntityPermission entityPermission) {
        ItemKitMember itemKitMember;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_itm_itemid = ? AND ikm_invcon_inventoryconditionid = ?  AND ikm_uomt_unitofmeasuretypeid = ? " +
                        "AND ikm_memberitemid = ? AND ikm_memberinventoryconditionid = ?  AND ikm_memberunitofmeasuretypeid = ? " +
                        "AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_itm_itemid = ? AND ikm_invcon_inventoryconditionid = ?  AND ikm_uomt_unitofmeasuretypeid = ? " +
                        "AND ikm_memberitemid = ? AND ikm_memberinventoryconditionid = ?  AND ikm_memberunitofmeasuretypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, memberItem.getPrimaryKey().getEntityId());
            ps.setLong(5, memberInventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(6, memberUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(7, Session.MAX_TIME);
            
            itemKitMember = ItemKitMemberFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMember;
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
    
    private List<ItemKitMember> getItemKitMembersFromItem(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_itm_itemid = ? AND ikm_invcon_inventoryconditionid = ? AND ikm_uomt_unitofmeasuretypeid = ? " +
                        "AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_itm_itemid = ? AND ikm_invcon_inventoryconditionid = ? AND ikm_uomt_unitofmeasuretypeid = ? " +
                        "AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
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
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_memberitemid = ? AND ikm_memberinventoryconditionid = ? AND ikm_memberunitofmeasuretypeid = ? " +
                        "AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_memberitemid = ? AND ikm_memberinventoryconditionid = ? AND ikm_memberunitofmeasuretypeid = ? " +
                        "AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, memberItem.getPrimaryKey().getEntityId());
            ps.setLong(2, memberInventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, memberUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
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
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_itm_itemid = ? AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_itm_itemid = ? AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    public List<ItemKitMember> getItemKitMembersByItem(Item item) {
        return getItemKitMembersByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByItemForUpdate(Item item) {
        return getItemKitMembersByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByMemberItem(Item memberItem, EntityPermission entityPermission) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_memberitemid = ? AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_itm_itemid = ? AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, memberItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberItem(Item memberItem) {
        return getItemKitMembersByMemberItem(memberItem, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberItemForUpdate(Item memberItem) {
        return getItemKitMembersByMemberItem(memberItem, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_invcon_inventoryconditionid = ? AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_invcon_inventoryconditionid = ? AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    public List<ItemKitMember> getItemKitMembersByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemKitMembersByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemKitMembersByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByMemberInventoryCondition(InventoryCondition memberInventoryCondition, EntityPermission entityPermission) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_memberinventoryconditionid = ? AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_memberinventoryconditionid = ? AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, memberInventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberInventoryCondition(InventoryCondition memberInventoryCondition) {
        return getItemKitMembersByMemberInventoryCondition(memberInventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberInventoryConditionForUpdate(InventoryCondition memberInventoryCondition) {
        return getItemKitMembersByMemberInventoryCondition(memberInventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemKitMember> getItemKitMembersByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_uomt_unitofmeasuretypeid = ? AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_uomt_unitofmeasuretypeid = ? AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    public List<ItemKitMember> getItemKitMembersByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemKitMembersByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemKitMembersByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemKitMember> getItemKitMembersByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            var ps = ItemKitMemberFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemkitmembers " +
                    "WHERE ikm_itm_itemid = ? AND ikm_uomt_unitofmeasuretypeid = ? AND ikm_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    private List<ItemKitMember> getItemKitMembersByMemberUnitOfMeasureType(UnitOfMeasureType memberUnitOfMeasureType, EntityPermission entityPermission) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                // TODO: 'ORDER BY'
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_memberunitofmeasuretypeid = ? AND ikm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemkitmembers " +
                        "WHERE ikm_memberunitofmeasuretypeid = ? AND ikm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, memberUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberUnitOfMeasureType(UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMembersByMemberUnitOfMeasureType(memberUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberUnitOfMeasureTypeForUpdate(UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMembersByMemberUnitOfMeasureType(memberUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemKitMember> getItemKitMembersByMemberItemAndUnitOfMeasureTypeForUpdate(Item memberItem, UnitOfMeasureType memberUnitOfMeasureType) {
        List<ItemKitMember> itemKitMembers;
        
        try {
            var ps = ItemKitMemberFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemkitmembers " +
                    "WHERE ikm_memberitemid = ? AND ikm_memberunitofmeasuretypeid = ? AND ikm_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, memberItem.getPrimaryKey().getEntityId());
            ps.setLong(2, memberUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemKitMembers = ItemKitMemberFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemKitMembers;
    }
    
    public void updateItemKitMemberFromValue(ItemKitMemberValue itemKitMemberValue, BasePK updatedBy) {
        if(itemKitMemberValue.hasBeenModified()) {
            var itemKitMember = ItemKitMemberFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemKitMemberValue.getPrimaryKey());
            
            itemKitMember.setThruTime(session.START_TIME_LONG);
            itemKitMember.store();

            var itemPK = itemKitMember.getItemPK();
            var inventoryConditionPK = itemKitMember.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemKitMember.getUnitOfMeasureTypePK();
            var memberItemPK = itemKitMember.getMemberItemPK();
            var memberInventoryConditionPK = itemKitMember.getMemberInventoryConditionPK();
            var memberUnitOfMeasureTypePK = itemKitMember.getMemberUnitOfMeasureTypePK();
            var quantity = itemKitMemberValue.getQuantity();
            
            itemKitMember = ItemKitMemberFactory.getInstance().create(itemPK, inventoryConditionPK, unitOfMeasureTypePK,
                    memberItemPK, memberInventoryConditionPK, memberUnitOfMeasureTypePK, quantity, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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

    public List<ItemKitMemberTransfer> getItemKitMemberTransfersByItem(UserVisit userVisit, Item item) {
        var itemKitMembers = getItemKitMembersByItem(item);
        List<ItemKitMemberTransfer> itemKitMemberTransfers = new ArrayList<>(itemKitMembers.size());
        
        itemKitMembers.forEach((itemKitMember) ->
                itemKitMemberTransfers.add(itemKitMemberTransferCache.getTransfer(userVisit, itemKitMember))
        );
        
        return itemKitMemberTransfers;
    }
    
    public void deleteItemKitMember(ItemKitMember itemKitMember, BasePK deletedBy) {
        itemKitMember.setThruTime(session.START_TIME_LONG);
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
    
    public ItemPackCheckRequirement createItemPackCheckRequirement(Item item, UnitOfMeasureType unitOfMeasureType,
            Long minimumQuantity, Long maximumQuantity, BasePK createdBy) {
        var itemPackCheckRequirement = ItemPackCheckRequirementFactory.getInstance().create(item,
                unitOfMeasureType, minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemPackCheckRequirement.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemPackCheckRequirement;
    }
    
    private ItemPackCheckRequirement getItemPackCheckRequirement(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        ItemPackCheckRequirement itemPackCheckRequirement;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itempackcheckrequirements " +
                        "WHERE ipcr_itm_itemid = ? AND ipcr_uomt_unitofmeasuretypeid = ? AND ipcr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itempackcheckrequirements " +
                        "WHERE ipcr_itm_itemid = ? AND ipcr_uomt_unitofmeasuretypeid = ? AND ipcr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemPackCheckRequirement = ItemPackCheckRequirementFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPackCheckRequirement;
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
        List<ItemPackCheckRequirement> itemPackCheckRequirements;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itempackcheckrequirements, unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE ipcr_itm_itemid = ? AND ipcr_thrutime = ? " +
                        "AND ipcr_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid " +
                        "AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itempackcheckrequirements " +
                        "WHERE ipcr_itm_itemid = ? AND ipcr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemPackCheckRequirements = ItemPackCheckRequirementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPackCheckRequirements;
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByItem(Item item) {
        return getItemPackCheckRequirementsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByItemForUpdate(Item item) {
        return getItemPackCheckRequirementsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemPackCheckRequirement> getItemPackCheckRequirementsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemPackCheckRequirement> itemPackCheckRequirements;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itempackcheckrequirements, items, itemdetails " +
                        "WHERE ipcr_itm_itemid = ? AND ipcr_thrutime = ? " +
                        "AND ipcr_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itempackcheckrequirements " +
                        "WHERE ipcr_uomt_unitofmeasuretypeid = ? AND ipcr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemPackCheckRequirements = ItemPackCheckRequirementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPackCheckRequirements;
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemPackCheckRequirementsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemPackCheckRequirementsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemPackCheckRequirement> getItemPackCheckRequirementsByItemAndUnitOfMeasureTypeForUpdate(Item item,
            UnitOfMeasureType unitOfMeasureType) {
        List<ItemPackCheckRequirement> itemPackCheckRequirements;
        
        try {
            var ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itempackcheckrequirements " +
                    "WHERE ipcr_itm_itemid = ? AND ipcr_uomt_unitofmeasuretypeid = ? AND ipcr_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemPackCheckRequirements = ItemPackCheckRequirementFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPackCheckRequirements;
    }
    
    public void updateItemPackCheckRequirementFromValue(ItemPackCheckRequirementValue itemPackCheckRequirementValue, BasePK updatedBy) {
        if(itemPackCheckRequirementValue.hasBeenModified()) {
            var itemPackCheckRequirementPK = itemPackCheckRequirementValue.getPrimaryKey();
            var itemPackCheckRequirement = ItemPackCheckRequirementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemPackCheckRequirementPK);
            
            itemPackCheckRequirement.setThruTime(session.START_TIME_LONG);
            itemPackCheckRequirement.store();

            var itemPK = itemPackCheckRequirement.getItemPK();
            var unitOfMeasureTypePK = itemPackCheckRequirement.getUnitOfMeasureTypePK();
            var minimumQuantity = itemPackCheckRequirementValue.getMinimumQuantity();
            var maximumQuantity = itemPackCheckRequirementValue.getMaximumQuantity();
            
            itemPackCheckRequirement = ItemPackCheckRequirementFactory.getInstance().create(itemPK, unitOfMeasureTypePK,
                    minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        itemPackCheckRequirement.setThruTime(session.START_TIME_LONG);
        
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
    
    public ItemUnitCustomerTypeLimit createItemUnitCustomerTypeLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType, Long minimumQuantity, Long maximumQuantity,
            BasePK createdBy) {
        var itemUnitCustomerTypeLimit = ItemUnitCustomerTypeLimitFactory.getInstance().create(item,
                inventoryCondition, unitOfMeasureType, customerType, minimumQuantity, maximumQuantity, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitCustomerTypeLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitCustomerTypeLimit;
    }
    
    private ItemUnitCustomerTypeLimit getItemUnitCustomerTypeLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType, EntityPermission entityPermission) {
        ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits " +
                        "WHERE iuctl_itm_itemid = ? AND iuctl_invcon_inventoryconditionid = ? AND iuctl_uomt_unitofmeasuretypeid = ? " +
                        "AND iuctl_cuty_customertypeid = ? AND iuctl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits " +
                        "WHERE iuctl_itm_itemid = ? AND iuctl_invcon_inventoryconditionid = ? AND iuctl_uomt_unitofmeasuretypeid = ? " +
                        "AND iuctl_cuty_customertypeid = ? AND iuctl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, customerType.getPrimaryKey().getEntityId());
            ps.setLong(5, Session.MAX_TIME);
            
            itemUnitCustomerTypeLimit = ItemUnitCustomerTypeLimitFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitCustomerTypeLimit;
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
        List<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails, customertypes, customertypedetails " +
                        "WHERE iuctl_itm_itemid = ? AND iuctl_thrutime = ? " +
                        "AND iuctl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND iuctl_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND iuctl_cuty_customertypeid = cuty_customertypeid AND cuty_lastdetailid = cutydt_customertypedetailid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cutydt_sortorder, cutydt_customertypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits " +
                        "WHERE iuctl_itm_itemid = ? AND iuctl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitCustomerTypeLimits = ItemUnitCustomerTypeLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitCustomerTypeLimits;
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByItem(Item item) {
        return getItemUnitCustomerTypeLimitsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByItemForUpdate(Item item) {
        return getItemUnitCustomerTypeLimitsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits, items, itemdetails, unitofmeasuretypes, unitofmeasuretypedetails, customertypes, customertypedetails " +
                        "WHERE iuctl_invcon_inventoryconditionid = ? AND iuctl_thrutime = ? " +
                        "AND iuctl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND iuctl_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND iuctl_cuty_customertypeid = cuty_customertypeid AND cuty_lastdetailid = cutydt_customertypedetailid " +
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cutydt_sortorder, cutydt_customertypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits " +
                        "WHERE iuctl_invcon_inventoryconditionid = ? AND iuctl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitCustomerTypeLimits = ItemUnitCustomerTypeLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitCustomerTypeLimits;
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemUnitCustomerTypeLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemUnitCustomerTypeLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits, items, itemdetails, inventoryconditions, inventoryconditiondetails, customertypes, customertypedetails " +
                        "WHERE iuctl_uomt_unitofmeasuretypeid = ? AND iuctl_thrutime = ? " +
                        "AND iuctl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND iuctl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND iuctl_cuty_customertypeid = cuty_customertypeid AND cuty_lastdetailid = cutydt_customertypedetailid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname, cutydt_sortorder, cutydt_customertypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits " +
                        "WHERE iuctl_invcon_inventoryconditionid = ? AND iuctl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitCustomerTypeLimits = ItemUnitCustomerTypeLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitCustomerTypeLimits;
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitCustomerTypeLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitCustomerTypeLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByItemAndUnitOfMeasureTypeForUpdate(Item item,
            UnitOfMeasureType unitOfMeasureType) {
        List<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits;
        
        try {
            var ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemunitcustomertypelimits " +
                    "WHERE iuctl_itm_itemid = ? AND iuctl_invcon_inventoryconditionid = ? AND iuctl_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemUnitCustomerTypeLimits = ItemUnitCustomerTypeLimitFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitCustomerTypeLimits;
    }
    
    private List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        List<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits, items, itemdetails, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE iuctl_cuty_customertypeid = ? AND iuctl_thrutime = ? " +
                        "AND iuctl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND iuctl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND iuctl_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitcustomertypelimits " +
                        "WHERE iuctl_invcon_inventoryconditionid = ? AND iuctl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, customerType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitCustomerTypeLimits = ItemUnitCustomerTypeLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitCustomerTypeLimits;
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByCustomerType(CustomerType customerType) {
        return getItemUnitCustomerTypeLimitsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitCustomerTypeLimit> getItemUnitCustomerTypeLimitsByCustomerTypeForUpdate(CustomerType customerType) {
        return getItemUnitCustomerTypeLimitsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    public void updateItemUnitCustomerTypeLimitFromValue(ItemUnitCustomerTypeLimitValue itemUnitCustomerTypeLimitValue, BasePK updatedBy) {
        if(itemUnitCustomerTypeLimitValue.hasBeenModified()) {
            var itemUnitCustomerTypeLimit = ItemUnitCustomerTypeLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitCustomerTypeLimitValue.getPrimaryKey());
            
            itemUnitCustomerTypeLimit.setThruTime(session.START_TIME_LONG);
            itemUnitCustomerTypeLimit.store();

            var itemPK = itemUnitCustomerTypeLimit.getItemPK();
            var inventoryConditionPK = itemUnitCustomerTypeLimit.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemUnitCustomerTypeLimit.getUnitOfMeasureTypePK();
            var customerTypePK = itemUnitCustomerTypeLimit.getCustomerTypePK();
            var minimumQuantity = itemUnitCustomerTypeLimitValue.getMinimumQuantity();
            var maximumQuantity = itemUnitCustomerTypeLimitValue.getMaximumQuantity();
            
            itemUnitCustomerTypeLimit = ItemUnitCustomerTypeLimitFactory.getInstance().create(itemPK, inventoryConditionPK,
                    unitOfMeasureTypePK, customerTypePK, minimumQuantity, maximumQuantity, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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

    public List<ItemUnitCustomerTypeLimitTransfer> getItemUnitCustomerTypeLimitTransfersByItem(UserVisit userVisit, Item item) {
        var itemUnitCustomerTypeLimits = getItemUnitCustomerTypeLimitsByItem(item);
        List<ItemUnitCustomerTypeLimitTransfer> itemUnitCustomerTypeLimitTransfers = new ArrayList<>(itemUnitCustomerTypeLimits.size());
        
        itemUnitCustomerTypeLimits.forEach((itemUnitCustomerTypeLimit) ->
                itemUnitCustomerTypeLimitTransfers.add(itemUnitCustomerTypeLimitTransferCache.getTransfer(userVisit, itemUnitCustomerTypeLimit))
        );
        
        return itemUnitCustomerTypeLimitTransfers;
    }
    
    public void deleteItemUnitCustomerTypeLimit(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit, BasePK deletedBy) {
        itemUnitCustomerTypeLimit.setThruTime(session.START_TIME_LONG);
        
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
    
    public ItemUnitLimit createItemUnitLimit(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Long minimumQuantity, Long maximumQuantity, BasePK createdBy) {
        var itemUnitLimit = ItemUnitLimitFactory.getInstance().create(item, inventoryCondition, unitOfMeasureType,
                minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitLimit;
    }
    
    private ItemUnitLimit getItemUnitLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        ItemUnitLimit itemUnitLimit;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits " +
                        "WHERE iul_itm_itemid = ? AND iul_invcon_inventoryconditionid = ? AND iul_uomt_unitofmeasuretypeid = ? " +
                        "AND iul_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits " +
                        "WHERE iul_itm_itemid = ? AND iul_invcon_inventoryconditionid = ? AND iul_uomt_unitofmeasuretypeid = ? " +
                        "AND iul_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            itemUnitLimit = ItemUnitLimitFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitLimit;
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
        List<ItemUnitLimit> itemUnitLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE iul_itm_itemid = ? AND iul_thrutime = ? " +
                        "AND iul_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND iul_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits " +
                        "WHERE iul_itm_itemid = ? AND iul_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitLimits = ItemUnitLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitLimits;
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByItem(Item item) {
        return getItemUnitLimitsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByItemForUpdate(Item item) {
        return getItemUnitLimitsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitLimit> getItemUnitLimitsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<ItemUnitLimit> itemUnitLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits, items, itemdetails, unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE iul_invcon_inventoryconditionid = ? AND iul_thrutime = ? " +
                        "AND iul_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND iul_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits " +
                        "WHERE iul_invcon_inventoryconditionid = ? AND iul_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitLimits = ItemUnitLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitLimits;
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemUnitLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemUnitLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitLimit> getItemUnitLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemUnitLimit> itemUnitLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits, items, itemdetails, inventoryconditions, inventoryconditiondetails " +
                        "WHERE iul_uomt_unitofmeasuretypeid = ? AND iul_thrutime = ? " +
                        "AND iul_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND iul_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitlimits " +
                        "WHERE iul_uomt_unitofmeasuretypeid = ? AND iul_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitLimits = ItemUnitLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitLimits;
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemUnitLimit> getItemUnitLimitsByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        List<ItemUnitLimit> itemUnitLimits;
        
        try {
            var ps = ItemUnitLimitFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemunitlimits " +
                    "WHERE iul_itm_itemid = ? AND iul_uomt_unitofmeasuretypeid = ? AND iul_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemUnitLimits = ItemUnitLimitFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitLimits;
    }
    
    public void updateItemUnitLimitFromValue(ItemUnitLimitValue itemUnitLimitValue, BasePK updatedBy) {
        if(itemUnitLimitValue.hasBeenModified()) {
            var itemUnitLimit = ItemUnitLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitLimitValue.getPrimaryKey());
            
            itemUnitLimit.setThruTime(session.START_TIME_LONG);
            itemUnitLimit.store();

            var itemPK = itemUnitLimit.getItemPK();
            var inventoryConditionPK = itemUnitLimit.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemUnitLimit.getUnitOfMeasureTypePK();
            var minimumQuantity = itemUnitLimitValue.getMinimumQuantity();
            var maximumQuantity = itemUnitLimitValue.getMaximumQuantity();
            
            itemUnitLimit = ItemUnitLimitFactory.getInstance().create(itemPK, inventoryConditionPK, unitOfMeasureTypePK,
                    minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        var itemUnitLimits = getItemUnitLimitsByItem(item);
        List<ItemUnitLimitTransfer> itemUnitLimitTransfers = new ArrayList<>(itemUnitLimits.size());
        
        itemUnitLimits.forEach((itemUnitLimit) ->
                itemUnitLimitTransfers.add(itemUnitLimitTransferCache.getTransfer(userVisit, itemUnitLimit))
        );
        
        return itemUnitLimitTransfers;
    }
    
    public void deleteItemUnitLimit(ItemUnitLimit itemUnitLimit, BasePK deletedBy) {
        itemUnitLimit.setThruTime(session.START_TIME_LONG);
        
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
    
    public ItemUnitPriceLimit createItemUnitPriceLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long minimumUnitPrice, Long maximumUnitPrice,
            BasePK createdBy) {
        var itemUnitPriceLimit = ItemUnitPriceLimitFactory.getInstance().create(item, inventoryCondition,
                unitOfMeasureType, currency, minimumUnitPrice, maximumUnitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemUnitPriceLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemUnitPriceLimit;
    }
    
    private ItemUnitPriceLimit getItemUnitPriceLimit(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, EntityPermission entityPermission) {
        ItemUnitPriceLimit itemUnitPriceLimit;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits " +
                        "WHERE iupl_itm_itemid = ? AND iupl_invcon_inventoryconditionid = ? AND iupl_uomt_unitofmeasuretypeid = ? " +
                        "AND iupl_cur_currencyid = ? AND iupl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits " +
                        "WHERE iupl_itm_itemid = ? AND iupl_invcon_inventoryconditionid = ? AND iupl_uomt_unitofmeasuretypeid = ? " +
                        "AND iupl_cur_currencyid = ? AND iupl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, currency.getPrimaryKey().getEntityId());
            ps.setLong(5, Session.MAX_TIME);
            
            itemUnitPriceLimit = ItemUnitPriceLimitFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitPriceLimit;
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
        List<ItemUnitPriceLimit> itemUnitPriceLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE iupl_itm_itemid = ? AND iupl_thrutime = ? " +
                        "AND iupl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND iupl_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND iupl_cur_currencyid = cur_currencyid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits " +
                        "WHERE iupl_itm_itemid = ? AND iupl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitPriceLimits = ItemUnitPriceLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitPriceLimits;
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByItem(Item item) {
        return getItemUnitPriceLimitsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByItemForUpdate(Item item) {
        return getItemUnitPriceLimitsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitPriceLimit> getItemUnitPriceLimitsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<ItemUnitPriceLimit> itemUnitPriceLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits, items, itemdetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE iupl_invcon_inventoryconditionid = ? AND iupl_thrutime = ? " +
                        "AND iupl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND iupl_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND iupl_cur_currencyid = cur_currencyid " +
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits " +
                        "WHERE iupl_itm_itemid = ? AND iupl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitPriceLimits = ItemUnitPriceLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitPriceLimits;
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemUnitPriceLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemUnitPriceLimitsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ItemUnitPriceLimit> getItemUnitPriceLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemUnitPriceLimit> itemUnitPriceLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits, items, itemdetails, inventoryconditions, inventoryconditiondetails, currencies " +
                        "WHERE iupl_uomt_unitofmeasuretypeid = ? AND iupl_thrutime = ? " +
                        "AND iupl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND iupl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND iupl_cur_currencyid = cur_currencyid " +
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, invcondt_sortorder, invcondt_inventoryconditionname, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitpricelimits " +
                        "WHERE iupl_uomt_unitofmeasuretypeid = ? AND iupl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemUnitPriceLimits = ItemUnitPriceLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitPriceLimits;
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitPriceLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitPriceLimitsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public List<ItemUnitPriceLimit> getItemUnitPriceLimitsByItemAndUnitOfMeasureTypeForUpdate(Item item,
            UnitOfMeasureType unitOfMeasureType) {
        List<ItemUnitPriceLimit> itemUnitPriceLimits;
        
        try {
            var ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemunitpricelimits " +
                    "WHERE iupl_itm_itemid = ? AND iupl_uomt_unitofmeasuretypeid = ? AND iupl_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemUnitPriceLimits = ItemUnitPriceLimitFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUnitPriceLimits;
    }
    
    public void updateItemUnitPriceLimitFromValue(ItemUnitPriceLimitValue itemUnitPriceLimitValue, BasePK updatedBy) {
        if(itemUnitPriceLimitValue.hasBeenModified()) {
            var itemUnitPriceLimit = ItemUnitPriceLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemUnitPriceLimitValue.getPrimaryKey());
            
            itemUnitPriceLimit.setThruTime(session.START_TIME_LONG);
            itemUnitPriceLimit.store();

            var itemPK = itemUnitPriceLimit.getItemPK();
            var inventoryConditionPK = itemUnitPriceLimit.getInventoryConditionPK();
            var unitOfMeasureTypePK = itemUnitPriceLimit.getUnitOfMeasureTypePK();
            var currencyPK = itemUnitPriceLimit.getCurrencyPK();
            var minimumUnitPrice = itemUnitPriceLimitValue.getMinimumUnitPrice();
            var maximumUnitPrice = itemUnitPriceLimitValue.getMaximumUnitPrice();
            
            itemUnitPriceLimit = ItemUnitPriceLimitFactory.getInstance().create(itemPK, inventoryConditionPK,
                    unitOfMeasureTypePK, currencyPK, minimumUnitPrice, maximumUnitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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

    public List<ItemUnitPriceLimitTransfer> getItemUnitPriceLimitTransfersByItem(UserVisit userVisit, Item item) {
        var itemUnitPriceLimits = getItemUnitPriceLimitsByItem(item);
        List<ItemUnitPriceLimitTransfer> itemUnitPriceLimitTransfers = new ArrayList<>(itemUnitPriceLimits.size());
        
        itemUnitPriceLimits.forEach((itemUnitPriceLimit) ->
                itemUnitPriceLimitTransfers.add(itemUnitPriceLimitTransferCache.getTransfer(userVisit, itemUnitPriceLimit))
        );
        
        return itemUnitPriceLimitTransfers;
    }
    
    public void deleteItemUnitPriceLimit(ItemUnitPriceLimit itemUnitPriceLimit, BasePK deletedBy) {
        itemUnitPriceLimit.setThruTime(session.START_TIME_LONG);
        
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
    
    public ItemPriceType createItemPriceType(String itemPriceTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var itemPriceType = ItemPriceTypeFactory.getInstance().create(itemPriceTypeName, isDefault, sortOrder);

        sendEvent(itemPriceType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return itemPriceType;
    }

    public long countItemPriceTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itempricetypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemPriceType */
    public ItemPriceType getItemPriceTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemPriceTypePK(entityInstance.getEntityUniqueId());

        return ItemPriceTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemPriceType getItemPriceTypeByEntityInstance(EntityInstance entityInstance) {
        return getItemPriceTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemPriceType getItemPriceTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemPriceTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemPriceType getItemPriceTypeByName(String itemPriceTypeName, EntityPermission entityPermission) {
        ItemPriceType itemPriceType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itempricetypes " +
                        "WHERE ipt_itempricetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itempricetypes " +
                        "WHERE ipt_itempricetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPriceTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, itemPriceTypeName);

            itemPriceType = ItemPriceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemPriceType;
    }

    public ItemPriceType getItemPriceTypeByName(String itemPriceTypeName) {
        return getItemPriceTypeByName(itemPriceTypeName, EntityPermission.READ_ONLY);
    }

    public ItemPriceType getItemPriceTypeByNameForUpdate(String itemPriceTypeName) {
        return getItemPriceTypeByName(itemPriceTypeName, EntityPermission.READ_WRITE);
    }

    public ItemPriceType getDefaultItemPriceType(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itempricetypes " +
                    "WHERE ipt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itempricetypes " +
                    "WHERE ipt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemPriceTypeFactory.getInstance().prepareStatement(query);

        return ItemPriceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        var ps = ItemPriceTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itempricetypes " +
                "ORDER BY ipt_sortorder, ipt_itempricetypename " +
                "_LIMIT_");
        
        return ItemPriceTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
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
    
    public ItemPriceTypeDescription createItemPriceTypeDescription(ItemPriceType itemPriceType, Language language,
            String description, BasePK createdBy) {
        var itemPriceTypeDescription = ItemPriceTypeDescriptionFactory.getInstance().create(itemPriceType, language, description);

        sendEvent(itemPriceType.getPrimaryKey(), EventTypes.MODIFY, itemPriceTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemPriceTypeDescription;
    }
    
    public ItemPriceTypeDescription getItemPriceTypeDescription(ItemPriceType itemPriceType, Language language) {
        ItemPriceTypeDescription itemPriceTypeDescription;
        
        try {
            var ps = ItemPriceTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itempricetypedescriptions " +
                    "WHERE iptd_ipt_itempricetypeid = ? AND iptd_lang_languageid = ?");
            
            ps.setLong(1, itemPriceType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            itemPriceTypeDescription = ItemPriceTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPriceTypeDescription;
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
    
    public ItemPrice createItemPrice(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, BasePK createdBy) {
        var itemPrice = ItemPriceFactory.getInstance().create(item, inventoryCondition, unitOfMeasureType, currency,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemPrice;
    }

    public long countItemPricesByItem(Item item) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemprices
                WHERE itmp_itm_itemid = ? AND itmp_thrutime = ?""",
                item, Session.MAX_TIME);
    }

    public long countItemPricesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemprices
                WHERE itmp_invcon_inventoryconditionid = ? AND itmp_thrutime = ?""",
                inventoryCondition, Session.MAX_TIME);
    }

    public long countItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemprices
                WHERE itmp_uomt_unitofmeasuretypeid = ? AND itmp_thrutime = ?""",
                unitOfMeasureType, Session.MAX_TIME);
    }

    public long countItemPricesByCurrency(Currency currency) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemprices
                WHERE itmp_cur_currencyid = ? AND itmp_thrutime = ?""",
                currency, Session.MAX_TIME);
    }

    private List<ItemPrice> getItemPricesByItem(Item item, EntityPermission entityPermission) {
        List<ItemPrice> itemPrices;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE itmp_itm_itemid = ? AND itmp_thrutime = ? " +
                        "AND itmp_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND itmp_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND itmp_cur_currencyid = cur_currencyid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_itm_itemid = ? AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemPrices = ItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPrices;
    }
    
    public List<ItemPrice> getItemPricesByItem(Item item) {
        return getItemPricesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPrice> getItemPricesByItemForUpdate(Item item) {
        return getItemPricesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ItemPrice> getItemPricesByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<ItemPrice> itemPrices;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices, items, itemdetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE itmp_invcon_inventoryconditionid = ? AND itmp_thrutime = ? " +
                        "AND itmp_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND itmp_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND itmp_cur_currencyid = cur_currencyid " +
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_invcon_inventoryconditionid = ? AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemPrices = ItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPrices;
    }
    
    public List<ItemPrice> getItemPricesByInventoryCondition(InventoryCondition inventoryCondition) {
        return getItemPricesByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPrice> getItemPricesByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getItemPricesByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    private List<ItemPrice> getItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ItemPrice> itemPrices;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices, items, itemdetails, inventoryconditions, inventoryconditiondetails, currencies " +
                        "WHERE itmp_uomt_unitofmeasuretypeid = ? AND itmp_thrutime = ? " +
                        "AND itmp_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND itmp_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND itmp_cur_currencyid = cur_currencyid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_uomt_unitofmeasuretypeid = ? AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPriceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemPrices = ItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemPrices;
    }

    public List<ItemPrice> getItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getItemPricesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<ItemPrice> getItemPricesByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getItemPricesByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<ItemPrice> getItemPricesByCurrency(Currency currency, EntityPermission entityPermission) {
        List<ItemPrice> itemPrices;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices, items, itemdetails, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE itmp_cur_currencyid = ? AND itmp_thrutime = ? " +
                        "AND itmp_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND itmp_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND itmp_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_cur_currencyid = ? AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPriceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemPrices = ItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemPrices;
    }

    public List<ItemPrice> getItemPricesByCurrency(Currency currency) {
        return getItemPricesByCurrency(currency, EntityPermission.READ_ONLY);
    }

    public List<ItemPrice> getItemPricesByCurrencyForUpdate(Currency currency) {
        return getItemPricesByCurrency(currency, EntityPermission.READ_WRITE);
    }

    public List<ItemPrice> getItemPricesByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        List<ItemPrice> itemPrices;
        
        try {
            var ps = ItemPriceFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemprices " +
                    "WHERE itmp_itm_itemid = ? AND itmp_uomt_unitofmeasuretypeid = ? AND itmp_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemPrices = ItemPriceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPrices;
    }
    
    private List<ItemPrice> getItemPrices(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            EntityPermission entityPermission) {
        List<ItemPrice> itemPrices;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices, currencies " +
                        "WHERE itmp_itm_itemid = ? AND itmp_invcon_inventoryconditionid = ? AND itmp_uomt_unitofmeasuretypeid = ? " +
                        "AND itmp_thrutime = ? AND itmp_cur_currencyid = cur_currencyid " +
                        "ORDER BY cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_itm_itemid = ? AND itmp_invcon_inventoryconditionid = ? AND itmp_uomt_unitofmeasuretypeid = ? " +
                        "AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            itemPrices = ItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPrices;
    }
    
    public List<ItemPrice> getItemPrices(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getItemPrices(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPrice> getItemPricesForUpdate(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getItemPrices(item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private ItemPrice getItemPrice(Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        ItemPrice itemPrice;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_itm_itemid = ? AND itmp_invcon_inventoryconditionid = ? AND itmp_uomt_unitofmeasuretypeid = ? AND itmp_cur_currencyid = ? " +
                        "AND itmp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_itm_itemid = ? AND itmp_invcon_inventoryconditionid = ? AND itmp_uomt_unitofmeasuretypeid = ? AND itmp_cur_currencyid = ? " +
                        "AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(4, currency.getPrimaryKey().getEntityId());
            ps.setLong(5, Session.MAX_TIME);
            
            itemPrice = ItemPriceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPrice;
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
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var item = itemPrice.getItem();
        var itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();
        
        if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
            var itemFixedPrice = getItemFixedPriceForUpdate(itemPrice);
            
            if(itemFixedPrice != null) {
                deleteItemFixedPrice(itemFixedPrice, deletedBy);
            }
        } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
            var itemVariablePrice = getItemVariablePriceForUpdate(itemPrice);
            
            if(itemVariablePrice != null) {
                deleteItemVariablePrice(itemVariablePrice, deletedBy);
            }
        }
        
        itemPrice.setThruTime(session.START_TIME_LONG);
        itemPrice.store();
        
        sendEvent(itemPrice.getItemPK(), EventTypes.MODIFY, itemPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);

        OfferItemLogic.getInstance().deleteOfferItemPrices(offerItemControl.getOfferItemPricesForUpdate(item,
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
    
    public ItemFixedPrice createItemFixedPrice(ItemPrice itemPrice, Long unitPrice, BasePK createdBy) {
        var itemFixedPrice = ItemFixedPriceFactory.getInstance().create(itemPrice, unitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(itemPrice.getItemPK(), EventTypes.MODIFY, itemFixedPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemFixedPrice;
    }
    
    private ItemFixedPrice getItemFixedPrice(ItemPrice itemPrice, EntityPermission entityPermission) {
        ItemFixedPrice itemFixedPrice;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemfixedprices " +
                        "WHERE itmfp_itmp_itempriceid = ? AND itmfp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemfixedprices " +
                        "WHERE itmfp_itmp_itempriceid = ? AND itmfp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemFixedPriceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemPrice.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemFixedPrice = ItemFixedPriceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemFixedPrice;
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
    
    private static final Map<EntityPermission, String> getItemFixedPriceHistoryQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemfixedprices "
                + "WHERE itmfp_itmp_itempriceid = ? "
                + "ORDER BY itmfp_thrutime");
        getItemFixedPriceHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<ItemFixedPrice> getItemFixedPriceHistory(ItemPrice itemPrice) {
        return ItemFixedPriceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getItemFixedPriceHistoryQueries,
                itemPrice);
    }
    
    public void updateItemFixedPriceFromValue(ItemFixedPriceValue itemFixedPriceValue, BasePK updatedBy) {
        if(itemFixedPriceValue.hasBeenModified()) {
            var itemFixedPrice = ItemFixedPriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemFixedPriceValue.getPrimaryKey());
            
            itemFixedPrice.setThruTime(session.START_TIME_LONG);
            itemFixedPrice.store();

            var itemPricePK = itemFixedPrice.getItemPricePK();
            var unitPrice = itemFixedPriceValue.getUnitPrice();
            
            itemFixedPrice = ItemFixedPriceFactory.getInstance().create(itemPricePK, unitPrice,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemFixedPrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemFixedPrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemFixedPrice(ItemFixedPrice itemFixedPrice, BasePK deletedBy) {
        itemFixedPrice.setThruTime(session.START_TIME_LONG);
        itemFixedPrice.store();
        
        sendEvent(itemFixedPrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemFixedPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Variable Prices
    // --------------------------------------------------------------------------------
    
    public ItemVariablePrice createItemVariablePrice(ItemPrice itemPrice, Long minimumUnitPrice, Long maximumUnitPrice, Long unitPriceIncrement,
            BasePK createdBy) {
        var itemVariablePrice = ItemVariablePriceFactory.getInstance().create(itemPrice, minimumUnitPrice,
                maximumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(itemPrice.getItemPK(), EventTypes.MODIFY, itemVariablePrice.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemVariablePrice;
    }
    
    private ItemVariablePrice getItemVariablePrice(ItemPrice itemPrice, EntityPermission entityPermission) {
        ItemVariablePrice itemVariablePrice;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvariableprices " +
                        "WHERE itmvp_itmp_itempriceid = ? AND itmvp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvariableprices " +
                        "WHERE itmvp_itmp_itempriceid = ? AND itmvp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVariablePriceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemPrice.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemVariablePrice = ItemVariablePriceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemVariablePrice;
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
    
    private static final Map<EntityPermission, String> getItemVariablePriceHistoryQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemvariableprices "
                + "WHERE itmvp_itmp_itempriceid = ? "
                + "ORDER BY itmvp_thrutime");
        getItemVariablePriceHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<ItemVariablePrice> getItemVariablePriceHistory(ItemPrice itemPrice) {
        return ItemVariablePriceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getItemVariablePriceHistoryQueries,
                itemPrice);
    }
    
    public void updateItemVariablePriceFromValue(ItemVariablePriceValue itemVariablePriceValue, BasePK updatedBy) {
        if(itemVariablePriceValue.hasBeenModified()) {
            var itemVariablePrice = ItemVariablePriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemVariablePriceValue.getPrimaryKey());
            
            itemVariablePrice.setThruTime(session.START_TIME_LONG);
            itemVariablePrice.store();

            var itemPricePK = itemVariablePrice.getItemPricePK();
            var maximumUnitPrice = itemVariablePriceValue.getMaximumUnitPrice();
            var minimumUnitPrice = itemVariablePriceValue.getMinimumUnitPrice();
            var unitPriceIncrement = itemVariablePriceValue.getUnitPriceIncrement();
            
            itemVariablePrice = ItemVariablePriceFactory.getInstance().create(itemPricePK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemVariablePrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemVariablePrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemVariablePrice(ItemVariablePrice itemVariablePrice, BasePK deletedBy) {
        itemVariablePrice.setThruTime(session.START_TIME_LONG);
        itemVariablePrice.store();
        
        sendEvent(itemVariablePrice.getItemPrice().getItemPK(), EventTypes.MODIFY, itemVariablePrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

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

        var itemDescriptionType = ItemDescriptionTypeFactory.getInstance().create();
        var itemDescriptionTypeDetail = ItemDescriptionTypeDetailFactory.getInstance().create(itemDescriptionType, itemDescriptionTypeName,
                parentItemDescriptionType, useParentIfMissing, mimeTypeUsageType, checkContentWebAddress, includeInIndex, indexDefault, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemDescriptionType = ItemDescriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return ItemDescriptionTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
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
        return session.queryForLong(
                "SELECT COUNT(*) " +
                    "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                    "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid");
    }

    public long countItemDescriptionTypesByParentItemDescriptionType(ItemDescriptionType parentItemDescriptionType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                        "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                        "AND idtdt_parentitemdescriptiontypeid = ?",
                parentItemDescriptionType);
    }

    private static final Map<EntityPermission, String> getItemDescriptionTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_itemdescriptiontypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_itemdescriptiontypename = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemDescriptionType getItemDescriptionTypeByName(String itemDescriptionTypeName, EntityPermission entityPermission) {
        return ItemDescriptionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getItemDescriptionTypeByNameQueries, itemDescriptionTypeName);
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

    private static final Map<EntityPermission, String> getItemDescriptionTypesByIncludeInIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_includeinindex = 1 " +
                "ORDER BY idtdt_sortorder, idtdt_itemdescriptiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_includeinindex = 1 " +
                "FOR UPDATE");
        getItemDescriptionTypesByIncludeInIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionType> getItemDescriptionTypesByIncludeInIndex(EntityPermission entityPermission) {
        return ItemDescriptionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypesByIncludeInIndexQueries);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesByIncludeInIndex() {
        return getItemDescriptionTypesByIncludeInIndex(EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesByIncludeInIndexForUpdate() {
        return getItemDescriptionTypesByIncludeInIndex(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexDefaultItemDescriptionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_indexdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_indexdefault = 1 " +
                "FOR UPDATE");
        getIndexDefaultItemDescriptionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemDescriptionType getIndexDefaultItemDescriptionType(EntityPermission entityPermission) {
        return ItemDescriptionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getIndexDefaultItemDescriptionTypeQueries);
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

    private static final Map<EntityPermission, String> getDefaultItemDescriptionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "AND idtdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultItemDescriptionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemDescriptionType getDefaultItemDescriptionType(EntityPermission entityPermission) {
        return ItemDescriptionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultItemDescriptionTypeQueries);
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

    private static final Map<EntityPermission, String> getItemDescriptionTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "ORDER BY idtdt_sortorder, idtdt_itemdescriptiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid " +
                "FOR UPDATE");
        getItemDescriptionTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionType> getItemDescriptionTypes(EntityPermission entityPermission) {
        return ItemDescriptionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypesQueries);
    }

    public List<ItemDescriptionType> getItemDescriptionTypes() {
        return getItemDescriptionTypes(EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionType> getItemDescriptionTypesForUpdate() {
        return getItemDescriptionTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemDescriptionTypesByParentItemDescriptionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid AND idtdt_parentitemdescriptiontypeid = ? " +
                "ORDER BY idtdt_sortorder, idtdt_itemdescriptiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idt_activedetailid = idtdt_itemdescriptiontypedetailid AND idtdt_parentitemdescriptiontypeid = ? " +
                "FOR UPDATE");
        getItemDescriptionTypesByParentItemDescriptionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionType> getItemDescriptionTypesByParentItemDescriptionType(ItemDescriptionType parentItemDescriptionType,
            EntityPermission entityPermission) {
        return ItemDescriptionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypesByParentItemDescriptionTypeQueries,
                parentItemDescriptionType);
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
            var itemDescriptionType = ItemDescriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionTypeDetailValue.getItemDescriptionTypePK());
            var itemDescriptionTypeDetail = itemDescriptionType.getActiveDetailForUpdate();

            itemDescriptionTypeDetail.setThruTime(session.START_TIME_LONG);
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

            itemDescriptionTypeDetail = ItemDescriptionTypeDetailFactory.getInstance().create(itemDescriptionTypePK, itemDescriptionTypeName,
                    parentItemDescriptionTypePK, useParentIfMissing, mimeTypeUsageTypePK, checkContentWebAddress, includeInIndex, indexDefault, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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

        itemDescriptionTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ItemDescriptionTypeDescription createItemDescriptionTypeDescription(ItemDescriptionType itemDescriptionType, Language language, String description, BasePK createdBy) {
        var itemDescriptionTypeDescription = ItemDescriptionTypeDescriptionFactory.getInstance().create(itemDescriptionType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDescriptionTypeDescription;
    }

    private static final Map<EntityPermission, String> getItemDescriptionTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypedescriptions " +
                "WHERE idtd_idt_itemdescriptiontypeid = ? AND idtd_lang_languageid = ? AND idtd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypedescriptions " +
                "WHERE idtd_idt_itemdescriptiontypeid = ? AND idtd_lang_languageid = ? AND idtd_thrutime = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemDescriptionTypeDescription getItemDescriptionTypeDescription(ItemDescriptionType itemDescriptionType, Language language, EntityPermission entityPermission) {
        return ItemDescriptionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getItemDescriptionTypeDescriptionQueries,
                itemDescriptionType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getItemDescriptionTypeDescriptionsByItemDescriptionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypedescriptions, languages " +
                "WHERE idtd_idt_itemdescriptiontypeid = ? AND idtd_thrutime = ? AND idtd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypedescriptions " +
                "WHERE idtd_idt_itemdescriptiontypeid = ? AND idtd_thrutime = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeDescriptionsByItemDescriptionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionTypeDescription> getItemDescriptionTypeDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        return ItemDescriptionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypeDescriptionsByItemDescriptionTypeQueries,
                itemDescriptionType, Session.MAX_TIME);
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
            var itemDescriptionTypeDescription = ItemDescriptionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDescriptionTypeDescriptionValue.getPrimaryKey());

            itemDescriptionTypeDescription.setThruTime(session.START_TIME_LONG);
            itemDescriptionTypeDescription.store();

            var itemDescriptionType = itemDescriptionTypeDescription.getItemDescriptionType();
            var language = itemDescriptionTypeDescription.getLanguage();
            var description = itemDescriptionTypeDescriptionValue.getDescription();

            itemDescriptionTypeDescription = ItemDescriptionTypeDescriptionFactory.getInstance().create(itemDescriptionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemDescriptionTypeDescription(ItemDescriptionTypeDescription itemDescriptionTypeDescription, BasePK deletedBy) {
        itemDescriptionTypeDescription.setThruTime(session.START_TIME_LONG);

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

    public ItemImageDescriptionType createItemImageDescriptionType(ItemDescriptionType itemDescriptionType, Integer minimumHeight, Integer minimumWidth,
            Integer maximumHeight, Integer maximumWidth, Integer preferredHeight, Integer preferredWidth, MimeType preferredMimeType, Integer quality,
            Boolean scaleFromParent, BasePK createdBy) {
        var itemImageDescriptionType = ItemImageDescriptionTypeFactory.getInstance().create(itemDescriptionType, minimumHeight,
                minimumWidth, maximumHeight, maximumWidth, preferredHeight, preferredWidth, preferredMimeType, quality, scaleFromParent,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemImageDescriptionType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemImageDescriptionType;
    }

    private static final Map<EntityPermission, String> getItemImageDescriptionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemimagedescriptiontypes " +
                "WHERE iimgdt_idt_itemdescriptiontypeid = ? AND iimgdt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagedescriptiontypes " +
                "WHERE iimgdt_idt_itemdescriptiontypeid = ? AND iimgdt_thrutime = ? " +
                "FOR UPDATE");
        getItemImageDescriptionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemImageDescriptionType getItemImageDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        return ItemImageDescriptionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getItemImageDescriptionTypeQueries,
                itemDescriptionType, Session.MAX_TIME);
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
            var itemImageDescriptionType = ItemImageDescriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageDescriptionTypeValue.getPrimaryKey());

            itemImageDescriptionType.setThruTime(session.START_TIME_LONG);
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

            itemImageDescriptionType = ItemImageDescriptionTypeFactory.getInstance().create(itemDescriptionTypePK, minimumHeight, minimumWidth, maximumHeight,
                    maximumWidth, preferredHeight, preferredWidth, preferredMimeTypePK, quality, scaleFromParent, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(itemImageDescriptionType.getItemDescriptionTypePK(), EventTypes.MODIFY, itemImageDescriptionType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemImageDescriptionType(ItemImageDescriptionType itemImageDescriptionType, BasePK deletedBy) {
        itemImageDescriptionType.setThruTime(session.START_TIME_LONG);

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

        var itemDescriptionTypeUseType = ItemDescriptionTypeUseTypeFactory.getInstance().create();
        var itemDescriptionTypeUseTypeDetail = ItemDescriptionTypeUseTypeDetailFactory.getInstance().create(itemDescriptionTypeUseType,
                itemDescriptionTypeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemDescriptionTypeUseType = ItemDescriptionTypeUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return ItemDescriptionTypeUseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemDescriptionTypeUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemDescriptionTypeUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countItemDescriptionTypeUseTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid");
    }

    private static final Map<EntityPermission, String> getItemDescriptionTypeUseTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid " +
                "AND idtutypdt_itemdescriptiontypeusetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid " +
                "AND idtutypdt_itemdescriptiontypeusetypename = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeUseTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByName(String itemDescriptionTypeUseTypeName, EntityPermission entityPermission) {
        return ItemDescriptionTypeUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getItemDescriptionTypeUseTypeByNameQueries, itemDescriptionTypeUseTypeName);
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

    private static final Map<EntityPermission, String> getDefaultItemDescriptionTypeUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid " +
                "AND idtutypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid " +
                "AND idtutypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultItemDescriptionTypeUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemDescriptionTypeUseType getDefaultItemDescriptionTypeUseType(EntityPermission entityPermission) {
        return ItemDescriptionTypeUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultItemDescriptionTypeUseTypeQueries);
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

    private static final Map<EntityPermission, String> getItemDescriptionTypeUseTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid " +
                "ORDER BY idtutypdt_sortorder, idtutypdt_itemdescriptiontypeusetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid " +
                "FOR UPDATE");
        getItemDescriptionTypeUseTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypes(EntityPermission entityPermission) {
        return ItemDescriptionTypeUseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypeUseTypesQueries);
    }

    public List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypes() {
        return getItemDescriptionTypeUseTypes(EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypesForUpdate() {
        return getItemDescriptionTypeUseTypes(EntityPermission.READ_WRITE);
    }

    private List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypesByParentItemDescriptionTypeUseType(ItemDescriptionTypeUseType parentItemDescriptionTypeUseType,
            EntityPermission entityPermission) {
        List<ItemDescriptionTypeUseType> itemDescriptionTypeUseTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                        "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid AND idtutypdt_parentitemdescriptiontypeusetypeid = ? " +
                        "ORDER BY idtutypdt_sortorder, idtutypdt_itemdescriptiontypeusetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                        "WHERE idtutyp_activedetailid = idtutypdt_itemdescriptiontypeusetypedetailid AND idtutypdt_parentitemdescriptiontypeusetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemDescriptionTypeUseTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, parentItemDescriptionTypeUseType.getPrimaryKey().getEntityId());

            itemDescriptionTypeUseTypes = ItemDescriptionTypeUseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemDescriptionTypeUseTypes;
    }

    public List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypesByParentItemDescriptionTypeUseType(ItemDescriptionTypeUseType parentItemDescriptionTypeUseType) {
        return getItemDescriptionTypeUseTypesByParentItemDescriptionTypeUseType(parentItemDescriptionTypeUseType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeUseType> getItemDescriptionTypeUseTypesByParentItemDescriptionTypeUseTypeForUpdate(ItemDescriptionTypeUseType parentItemDescriptionTypeUseType) {
        return getItemDescriptionTypeUseTypesByParentItemDescriptionTypeUseType(parentItemDescriptionTypeUseType, EntityPermission.READ_WRITE);
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

    public List<ItemDescriptionTypeUseTypeTransfer> getItemDescriptionTypeUseTypeTransfersByParentItemDescriptionTypeUseType(UserVisit userVisit,
            ItemDescriptionTypeUseType parentItemDescriptionTypeUseType) {
        return getItemDescriptionTypeUseTypeTransfers(userVisit, getItemDescriptionTypeUseTypesByParentItemDescriptionTypeUseType(parentItemDescriptionTypeUseType));
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
            var itemDescriptionTypeUseType = ItemDescriptionTypeUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionTypeUseTypeDetailValue.getItemDescriptionTypeUseTypePK());
            var itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getActiveDetailForUpdate();

            itemDescriptionTypeUseTypeDetail.setThruTime(session.START_TIME_LONG);
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

            itemDescriptionTypeUseTypeDetail = ItemDescriptionTypeUseTypeDetailFactory.getInstance().create(itemDescriptionTypeUseTypePK,
                    itemDescriptionTypeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        itemDescriptionTypeUseTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ItemDescriptionTypeUseTypeDescription createItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            Language language, String description, BasePK createdBy) {
        var itemDescriptionTypeUseTypeDescription = ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().create(itemDescriptionTypeUseType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDescriptionTypeUseTypeDescription;
    }

    private static final Map<EntityPermission, String> getItemDescriptionTypeUseTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypedescriptions " +
                "WHERE idtutypd_idtutyp_itemdescriptiontypeusetypeid = ? AND idtutypd_lang_languageid = ? AND idtutypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypedescriptions " +
                "WHERE idtutypd_idtutyp_itemdescriptiontypeusetypeid = ? AND idtutypd_lang_languageid = ? AND idtutypd_thrutime = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeUseTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemDescriptionTypeUseTypeDescription getItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            Language language, EntityPermission entityPermission) {
        return ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getItemDescriptionTypeUseTypeDescriptionQueries,
                itemDescriptionTypeUseType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypedescriptions, languages " +
                "WHERE idtutypd_idtutyp_itemdescriptiontypeusetypeid = ? AND idtutypd_thrutime = ? AND idtutypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeusetypedescriptions " +
                "WHERE idtutypd_idtutyp_itemdescriptiontypeusetypeid = ? AND idtutypd_thrutime = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionTypeUseTypeDescription> getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            EntityPermission entityPermission) {
        return ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseTypeQueries,
                itemDescriptionTypeUseType, Session.MAX_TIME);
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
            var itemDescriptionTypeUseTypeDescription = ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDescriptionTypeUseTypeDescriptionValue.getPrimaryKey());

            itemDescriptionTypeUseTypeDescription.setThruTime(session.START_TIME_LONG);
            itemDescriptionTypeUseTypeDescription.store();

            var itemDescriptionTypeUseType = itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseType();
            var language = itemDescriptionTypeUseTypeDescription.getLanguage();
            var description = itemDescriptionTypeUseTypeDescriptionValue.getDescription();

            itemDescriptionTypeUseTypeDescription = ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().create(itemDescriptionTypeUseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription, BasePK deletedBy) {
        itemDescriptionTypeUseTypeDescription.setThruTime(session.START_TIME_LONG);

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

    public ItemDescriptionTypeUse createItemDescriptionTypeUse(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            BasePK createdBy) {
        var itemDescriptionTypeUse = ItemDescriptionTypeUseFactory.getInstance().create(itemDescriptionType, itemDescriptionTypeUseType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY, itemDescriptionTypeUse.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemDescriptionTypeUse;
    }

    public long countItemDescriptionTypeUsesByItemDescriptionType(ItemDescriptionType itemDescriptionType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemdescriptiontypeuses
                WHERE idtu_idt_itemdescriptiontypeid = ? AND idtu_thrutime = ?
                """, itemDescriptionType, Session.MAX_TIME);
    }

    public long countItemDescriptionTypeUsesByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itemdescriptiontypeuses
                WHERE idtu_idtutyp_itemdescriptiontypeusetypeid = ? AND idtu_thrutime = ?
                """, itemDescriptionTypeUseType, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getItemDescriptionTypeUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeuses " +
                "WHERE idtu_idt_itemdescriptiontypeid = ? AND idtu_idtutyp_itemdescriptiontypeusetypeid = ? AND idtu_thrutime = ? " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeuses " +
                "WHERE idtu_idt_itemdescriptiontypeid = ? AND idtu_idtutyp_itemdescriptiontypeusetypeid = ? AND idtu_thrutime = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemDescriptionTypeUse getItemDescriptionTypeUse(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            EntityPermission entityPermission) {
        return ItemDescriptionTypeUseFactory.getInstance().getEntityFromQuery(entityPermission, getItemDescriptionTypeUseQueries, itemDescriptionType,
                itemDescriptionTypeUseType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getItemDescriptionTypeUsesByItemDescriptionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeuses, itemdescriptiontypes, itemdescriptiontypedetails " +
                "WHERE idtu_idt_itemdescriptiontypeid = ? AND idtu_thrutime = ? " +
                "AND idtu_idt_itemdescriptiontypeid = idt_itemdescriptiontypeid AND idt_lastdetailid = idtdt_itemdescriptiontypedetailid " +
                "ORDER BY idtdt_sortorder, idtdt_itemdescriptiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeuses " +
                "WHERE idtu_idt_itemdescriptiontypeid = ? AND idtu_thrutime = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeUsesByItemDescriptionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        return ItemDescriptionTypeUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypeUsesByItemDescriptionTypeQueries,
                itemDescriptionType, Session.MAX_TIME);
    }

    public List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionType(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionTypeForUpdate(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemDescriptionTypeUsesByItemDescriptionTypeUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeuses, itemdescriptiontypeusetypes, itemdescriptiontypeusetypedetails " +
                "WHERE idtu_idtutyp_itemdescriptiontypeusetypeid = ? AND idtu_thrutime = ? " +
                "AND idtu_idtutyp_itemdescriptiontypeusetypeid = idtutyp_itemdescriptiontypeusetypeid AND idtutyp_lastdetailid = idtutypdt_itemdescriptiontypeusetypedetailid " +
                "ORDER BY idtutypdt_sortorder, idtutypdt_itemdescriptiontypeusetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptiontypeuses " +
                "WHERE idtu_idtutyp_itemdescriptiontypeusetypeid = ? AND idtu_thrutime = ? " +
                "FOR UPDATE");
        getItemDescriptionTypeUsesByItemDescriptionTypeUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescriptionTypeUse> getItemDescriptionTypeUsesByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            EntityPermission entityPermission) {
        return ItemDescriptionTypeUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemDescriptionTypeUsesByItemDescriptionTypeUseTypeQueries,
                itemDescriptionTypeUseType, Session.MAX_TIME);
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
        itemDescriptionTypeUse.setThruTime(session.START_TIME_LONG);
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

        var itemImageType = ItemImageTypeFactory.getInstance().create();
        var itemImageTypeDetail = ItemImageTypeDetailFactory.getInstance().create(itemImageType, itemImageTypeName, preferredMimeType, quality,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemImageType = ItemImageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return ItemImageTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
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
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemimagetypes, itemimagetypedetails " +
                "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid");
    }

    private static final Map<EntityPermission, String> getItemImageTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemimagetypes, itemimagetypedetails " +
                "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid " +
                "AND iimgtdt_itemimagetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagetypes, itemimagetypedetails " +
                "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid " +
                "AND iimgtdt_itemimagetypename = ? " +
                "FOR UPDATE");
        getItemImageTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemImageType getItemImageTypeByName(String itemImageTypeName, EntityPermission entityPermission) {
        return ItemImageTypeFactory.getInstance().getEntityFromQuery(entityPermission, getItemImageTypeByNameQueries, itemImageTypeName);
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

    private static final Map<EntityPermission, String> getDefaultItemImageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemimagetypes, itemimagetypedetails " +
                "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid " +
                "AND iimgtdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagetypes, itemimagetypedetails " +
                "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid " +
                "AND iimgtdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultItemImageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public ItemImageType getDefaultItemImageType(EntityPermission entityPermission) {
        return ItemImageTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultItemImageTypeQueries);
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

    private static final Map<EntityPermission, String> getItemImageTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemimagetypes, itemimagetypedetails " +
                "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid " +
                "ORDER BY iimgtdt_sortorder, iimgtdt_itemimagetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagetypes, itemimagetypedetails " +
                "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid " +
                "FOR UPDATE");
        getItemImageTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemImageType> getItemImageTypes(EntityPermission entityPermission) {
        return ItemImageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemImageTypesQueries);
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
            var itemImageType = ItemImageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageTypeDetailValue.getItemImageTypePK());
            var itemImageTypeDetail = itemImageType.getActiveDetailForUpdate();

            itemImageTypeDetail.setThruTime(session.START_TIME_LONG);
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

            itemImageTypeDetail = ItemImageTypeDetailFactory.getInstance().create(itemImageTypePK, itemImageTypeName, preferredMimeTypePK, quality, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        itemImageTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ItemImageTypeDescription createItemImageTypeDescription(ItemImageType itemImageType,
            Language language, String description, BasePK createdBy) {
        var itemImageTypeDescription = ItemImageTypeDescriptionFactory.getInstance().create(itemImageType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemImageType.getPrimaryKey(), EventTypes.MODIFY, itemImageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemImageTypeDescription;
    }

    private static final Map<EntityPermission, String> getItemImageTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemimagetypedescriptions " +
                "WHERE iimgtd_iimgt_itemimagetypeid = ? AND iimgtd_lang_languageid = ? AND iimgtd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagetypedescriptions " +
                "WHERE iimgtd_iimgt_itemimagetypeid = ? AND iimgtd_lang_languageid = ? AND iimgtd_thrutime = ? " +
                "FOR UPDATE");
        getItemImageTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemImageTypeDescription getItemImageTypeDescription(ItemImageType itemImageType,
            Language language, EntityPermission entityPermission) {
        return ItemImageTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getItemImageTypeDescriptionQueries,
                itemImageType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getItemImageTypeDescriptionsByItemImageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemimagetypedescriptions, languages " +
                "WHERE iimgtd_iimgt_itemimagetypeid = ? AND iimgtd_thrutime = ? AND iimgtd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagetypedescriptions " +
                "WHERE iimgtd_iimgt_itemimagetypeid = ? AND iimgtd_thrutime = ? " +
                "FOR UPDATE");
        getItemImageTypeDescriptionsByItemImageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemImageTypeDescription> getItemImageTypeDescriptionsByItemImageType(ItemImageType itemImageType,
            EntityPermission entityPermission) {
        return ItemImageTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemImageTypeDescriptionsByItemImageTypeQueries,
                itemImageType, Session.MAX_TIME);
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
            var itemImageTypeDescription = ItemImageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemImageTypeDescriptionValue.getPrimaryKey());

            itemImageTypeDescription.setThruTime(session.START_TIME_LONG);
            itemImageTypeDescription.store();

            var itemImageType = itemImageTypeDescription.getItemImageType();
            var language = itemImageTypeDescription.getLanguage();
            var description = itemImageTypeDescriptionValue.getDescription();

            itemImageTypeDescription = ItemImageTypeDescriptionFactory.getInstance().create(itemImageType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(itemImageType.getPrimaryKey(), EventTypes.MODIFY, itemImageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemImageTypeDescription(ItemImageTypeDescription itemImageTypeDescription, BasePK deletedBy) {
        itemImageTypeDescription.setThruTime(session.START_TIME_LONG);

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
    
    public ItemDescription createItemDescription(ItemDescriptionType itemDescriptionType, Item item, Language language,
            MimeType mimeType, BasePK createdBy) {
        var itemDescription = ItemDescriptionFactory.getInstance().create();
        var itemDescriptionDetail = ItemDescriptionDetailFactory.getInstance().create(itemDescription,
                itemDescriptionType, item, language, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemDescription = ItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return ItemDescriptionFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemDescription getItemDescriptionByEntityInstance(EntityInstance entityInstance) {
        return getItemDescriptionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemDescription getItemDescriptionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemDescriptionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getScaledItemDescriptionsByItemDescriptionTypePKQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                // TODO: ORDER BY
                "SELECT _ALL_ " +
                "FROM itemdescriptions, itemdescriptiondetails, itemimagedescriptions " +
                "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid " +
                "AND idesc_itemdescriptionid = iimgdesc_idesc_itemdescriptionid AND iimgdesc_thrutime = ? " +
                "AND idescdt_idt_itemdescriptiontypeid = ? AND iimgdesc_scaledfromparent = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptions, itemdescriptiondetails, itemimagedescriptions " +
                "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid " +
                "AND idesc_itemdescriptionid = iimgdesc_idesc_itemdescriptionid AND iimgdesc_thrutime = ? " +
                "AND idescdt_idt_itemdescriptiontypeid = ? AND iimgdesc_scaledfromparent = 1 " +
                "FOR UPDATE");
        getScaledItemDescriptionsByItemDescriptionTypePKQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescription> getScaledItemDescriptionsByItemDescriptionTypePK(ItemDescriptionTypePK itemDescriptionTypePK, EntityPermission entityPermission) {
        return ItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getScaledItemDescriptionsByItemDescriptionTypePKQueries,
                Session.MAX_TIME, itemDescriptionTypePK);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemDescriptionTypePK(ItemDescriptionTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemDescriptionTypePK(itemDescriptionTypePK, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemDescriptionTypePKForUpdate(ItemDescriptionTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemDescriptionTypePK(itemDescriptionTypePK, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getScaledItemDescriptionsByItemImageTypePKQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                // TODO: ORDER BY
                "SELECT _ALL_ " +
                "FROM itemdescriptions, itemdescriptiondetails, itemimagedescriptions " +
                "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid " +
                "AND idesc_itemdescriptionid = iimgdesc_idesc_itemdescriptionid AND iimgdesc_thrutime = ? " +
                "AND iimgdesc_iimgt_itemimagetypeid = ? AND iimgdesc_scaledfromparent = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemdescriptions, itemdescriptiondetails, itemimagedescriptions " +
                "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid " +
                "AND idesc_itemdescriptionid = iimgdesc_idesc_itemdescriptionid AND iimgdesc_thrutime = ? " +
                "AND iimgdesc_iimgt_itemimagetypeid = ? AND iimgdesc_scaledfromparent = 1 " +
                "FOR UPDATE");
        getScaledItemDescriptionsByItemImageTypePKQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemDescription> getScaledItemDescriptionsByItemImageTypePK(ItemImageTypePK itemDescriptionTypePK, EntityPermission entityPermission) {
        return ItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getScaledItemDescriptionsByItemImageTypePKQueries,
                Session.MAX_TIME, itemDescriptionTypePK);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemImageTypePK(ItemImageTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemImageTypePK(itemDescriptionTypePK, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getScaledItemDescriptionsByItemImageTypePKForUpdate(ItemImageTypePK itemDescriptionTypePK) {
        return getScaledItemDescriptionsByItemImageTypePK(itemDescriptionTypePK, EntityPermission.READ_WRITE);
    }

    public List<ItemDescription> getItemDescriptionsByItem(Item item, EntityPermission entityPermission) {
        List<ItemDescription> itemDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptions, itemdescriptiondetails, itemdescriptiontypes, itemdescriptiontypedetails, languages " +
                        "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid AND idescdt_itm_itemid = ? " +
                        "AND idescdt_idt_itemdescriptiontypeid = idt_itemdescriptiontypeid AND idt_lastdetailid = idtdt_itemdescriptiontypedetailid " +
                        "AND idescdt_lang_languageid = lang_languageid " +
                        "ORDER BY idtdt_sortorder, idtdt_itemdescriptiontypename, lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptions, itemdescriptiondetails " +
                        "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid AND idescdt_itm_itemid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, item.getPrimaryKey().getEntityId());

            itemDescriptions = ItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemDescriptions;
    }

    public List<ItemDescription> getItemDescriptionsByItem(Item item) {
        return getItemDescriptionsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getItemDescriptionsByItemForUpdate(Item item) {
        return getItemDescriptionsByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemDescription> getItemDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType, EntityPermission entityPermission) {
        List<ItemDescription> itemDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptions, itemdescriptiondetails, items, itemdetails, languages " +
                        "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid AND idescdt_idt_itemdescriptiontypeid = ? " +
                        "AND idescdt_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND idescdt_lang_languageid = lang_languageid " +
                        "ORDER BY itmdt_itemname, lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptions, itemdescriptiondetails " +
                        "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid AND idescdt_idt_itemdescriptiontypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemDescriptionType.getPrimaryKey().getEntityId());

            itemDescriptions = ItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemDescriptions;
    }

    public List<ItemDescription> getItemDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionsByItemDescriptionType(itemDescriptionType, EntityPermission.READ_ONLY);
    }

    public List<ItemDescription> getItemDescriptionsByItemDescriptionTypeForUpdate(ItemDescriptionType itemDescriptionType) {
        return getItemDescriptionsByItemDescriptionType(itemDescriptionType, EntityPermission.READ_WRITE);
    }

    public long countItemDescriptionsByItem(Item item) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM itemdescriptions, itemdescriptiondetails " +
                "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid AND idescdt_itm_itemid = ?",
                item);
    }

    private ItemDescription getItemDescription(ItemDescriptionType itemDescriptionType, Item item, Language language,
            EntityPermission entityPermission) {
        ItemDescription itemDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptions, itemdescriptiondetails " +
                        "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid AND idescdt_idt_itemdescriptiontypeid = ? " +
                        "AND idescdt_itm_itemid = ? AND idescdt_lang_languageid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemdescriptions, itemdescriptiondetails " +
                        "WHERE idesc_activedetailid = idescdt_itemdescriptiondetailid AND idescdt_idt_itemdescriptiontypeid = ? " +
                        "AND idescdt_itm_itemid = ? AND idescdt_lang_languageid = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemDescriptionType.getPrimaryKey().getEntityId());
            ps.setLong(2, item.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            
            itemDescription = ItemDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemDescription;
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
            var itemDescription = ItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionDetailValue.getItemDescriptionPK());
            var itemDescriptionDetail = itemDescription.getActiveDetailForUpdate();
            
            itemDescriptionDetail.setThruTime(session.START_TIME_LONG);
            itemDescriptionDetail.store();

            var itemDescriptionPK = itemDescriptionDetail.getItemDescriptionPK(); // Not updated
            var itemDescriptionTypePK = itemDescriptionDetailValue.getItemDescriptionTypePK();
            var itemPK = itemDescriptionDetailValue.getItemPK();
            var languagePK = itemDescriptionDetailValue.getLanguagePK();
            var mimeTypePK = itemDescriptionDetailValue.getMimeTypePK();
            
            itemDescriptionDetail = ItemDescriptionDetailFactory.getInstance().create(itemDescriptionPK,
                    itemDescriptionTypePK, itemPK, languagePK, mimeTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        itemDescriptionDetail.setThruTime(session.START_TIME_LONG);
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
    
    public ItemBlobDescription createItemBlobDescription(ItemDescription itemDescription, ByteArray blobDescription,
            BasePK createdBy) {
        var itemBlobDescription = ItemBlobDescriptionFactory.getInstance().create(itemDescription,
                blobDescription, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemBlobDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemBlobDescription;
    }
    
    private ItemBlobDescription getItemBlobDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        ItemBlobDescription itemBlobDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemblobdescriptions " +
                        "WHERE ibdesc_idesc_itemdescriptionid = ? AND ibdesc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemblobdescriptions " +
                        "WHERE ibdesc_idesc_itemdescriptionid = ? AND ibdesc_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemBlobDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemDescription.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemBlobDescription = ItemBlobDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemBlobDescription;
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
            var itemBlobDescription = ItemBlobDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemBlobDescriptionValue.getPrimaryKey());
            
            itemBlobDescription.setThruTime(session.START_TIME_LONG);
            itemBlobDescription.store();

            var itemDescriptionPK = itemBlobDescription.getItemDescriptionPK(); // Not updated
            var blobDescription = itemBlobDescriptionValue.getBlobDescription();
            
            itemBlobDescription = ItemBlobDescriptionFactory.getInstance().create(itemDescriptionPK, blobDescription,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemBlobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemBlobDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemBlobDescription(ItemBlobDescription itemBlobDescription, BasePK deletedBy) {
        itemBlobDescription.setThruTime(session.START_TIME_LONG);
        
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


    public ItemImageDescription createItemImageDescription(ItemDescription itemDescription, ItemImageType itemImageType, Integer height, Integer width,
            Boolean scaledFromParent, BasePK createdBy) {
        var itemImageDescription = ItemImageDescriptionFactory.getInstance().create(itemDescription, itemImageType, height, width,
                scaledFromParent, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemImageDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemImageDescription;
    }

    private static final Map<EntityPermission, String> getItemImageDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemimagedescriptions " +
                "WHERE iimgdesc_idesc_itemdescriptionid = ? AND iimgdesc_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagedescriptions " +
                "WHERE iimgdesc_idesc_itemdescriptionid = ? AND iimgdesc_thrutime = ? " +
                "FOR UPDATE");
        getItemImageDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemImageDescription getItemImageDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        return ItemImageDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getItemImageDescriptionQueries,
                itemDescription, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getItemImageDescriptionByItemImageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                // TODO: ORDER BY
                "SELECT _ALL_ " +
                "FROM itemimagedescriptions " +
                "WHERE iimgdesc_iimgt_itemimagetypeid = ? AND iimgdesc_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemimagedescriptions " +
                "WHERE iimgdesc_iimgt_itemimagetypeid = ? AND iimgdesc_thrutime = ? " +
                "FOR UPDATE");
        getItemImageDescriptionByItemImageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemImageDescription> getItemImageDescriptionsByItemImageType(ItemImageType itemImageType, EntityPermission entityPermission) {
        return ItemImageDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemImageDescriptionByItemImageTypeQueries,
                itemImageType, Session.MAX_TIME);
    }

    public List<ItemImageDescription> getItemImageDescriptionsByItemImageType(ItemImageType itemImageType) {
        return getItemImageDescriptionsByItemImageType(itemImageType, EntityPermission.READ_ONLY);
    }

    public List<ItemImageDescription> getItemImageDescriptionsByItemImageTypeForUpdate(ItemImageType itemImageType) {
        return getItemImageDescriptionsByItemImageType(itemImageType, EntityPermission.READ_WRITE);
    }

    public void updateItemImageDescriptionFromValue(ItemImageDescriptionValue itemImageDescriptionValue, BasePK updatedBy) {
        if(itemImageDescriptionValue.hasBeenModified()) {
            var itemImageDescription = ItemImageDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageDescriptionValue.getPrimaryKey());

            itemImageDescription.setThruTime(session.START_TIME_LONG);
            itemImageDescription.store();

            var itemDescriptionPK = itemImageDescription.getItemDescriptionPK(); // Not updated
            var itemImageTypePK = itemImageDescriptionValue.getItemImageTypePK();
            var height = itemImageDescriptionValue.getHeight();
            var width = itemImageDescriptionValue.getWidth();
            var scaledFromParent = itemImageDescriptionValue.getScaledFromParent();

            itemImageDescription = ItemImageDescriptionFactory.getInstance().create(itemDescriptionPK, itemImageTypePK, height, width, scaledFromParent,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(itemImageDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemImageDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemImageDescription(ItemImageDescription itemImageDescription, BasePK deletedBy) {
        itemImageDescription.setThruTime(session.START_TIME_LONG);

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
    
    public ItemClobDescription createItemClobDescription(ItemDescription itemDescription, String clobDescription,
            BasePK createdBy) {
        var itemClobDescription = ItemClobDescriptionFactory.getInstance().create(itemDescription,
                clobDescription, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemClobDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemClobDescription;
    }
    
    private ItemClobDescription getItemClobDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        ItemClobDescription itemClobDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemclobdescriptions " +
                        "WHERE icdesc_idesc_itemdescriptionid = ? AND icdesc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemclobdescriptions " +
                        "WHERE icdesc_idesc_itemdescriptionid = ? AND icdesc_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemClobDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemDescription.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemClobDescription = ItemClobDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemClobDescription;
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
            var itemClobDescription = ItemClobDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemClobDescriptionValue.getPrimaryKey());
            
            itemClobDescription.setThruTime(session.START_TIME_LONG);
            itemClobDescription.store();

            var itemDescriptionPK = itemClobDescription.getItemDescriptionPK(); // Not updated
            var clobDescription = itemClobDescriptionValue.getClobDescription();
            
            itemClobDescription = ItemClobDescriptionFactory.getInstance().create(itemDescriptionPK, clobDescription,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemClobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemClobDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemClobDescription(ItemClobDescription itemClobDescription, BasePK deletedBy) {
        itemClobDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public ItemStringDescription createItemStringDescription(ItemDescription itemDescription, String stringDescription,
            BasePK createdBy) {
        var itemStringDescription = ItemStringDescriptionFactory.getInstance().create(itemDescription,
                stringDescription, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY, itemStringDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemStringDescription;
    }
    
    private ItemStringDescription getItemStringDescription(ItemDescription itemDescription, EntityPermission entityPermission) {
        ItemStringDescription itemStringDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemstringdescriptions " +
                        "WHERE isdesc_idesc_itemdescriptionid = ? AND isdesc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemstringdescriptions " +
                        "WHERE isdesc_idesc_itemdescriptionid = ? AND isdesc_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemStringDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemDescription.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemStringDescription = ItemStringDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemStringDescription;
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
            var itemStringDescription = ItemStringDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemStringDescriptionValue.getPrimaryKey());
            
            itemStringDescription.setThruTime(session.START_TIME_LONG);
            itemStringDescription.store();

            var itemDescriptionPK = itemStringDescription.getItemDescriptionPK(); // Not updated
            var stringDescription = itemStringDescriptionValue.getStringDescription();
            
            itemStringDescription = ItemStringDescriptionFactory.getInstance().create(itemDescriptionPK, stringDescription,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(itemStringDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY, itemStringDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemStringDescription(ItemStringDescription itemStringDescription, BasePK deletedBy) {
        itemStringDescription.setThruTime(session.START_TIME_LONG);
        
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

        var itemVolumeType = ItemVolumeTypeFactory.getInstance().create();
        var itemVolumeTypeDetail = ItemVolumeTypeDetailFactory.getInstance().create(session, itemVolumeType, itemVolumeTypeName,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemVolumeType = ItemVolumeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemVolumeType.getPrimaryKey());
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

        return ItemVolumeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemVolumeType getItemVolumeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemVolumeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemVolumeType getItemVolumeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemVolumeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemVolumeType getItemVolumeTypeByPK(ItemVolumeTypePK pk) {
        return ItemVolumeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countItemVolumeTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM itemvolumetypes, itemvolumetypedetails " +
                        "WHERE ivolt_activedetailid = ivoltdt_itemvolumetypedetailid");
    }

    public ItemVolumeType getItemVolumeTypeByName(String itemVolumeTypeName, EntityPermission entityPermission) {
        ItemVolumeType itemVolumeType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumetypes, itemvolumetypedetails " +
                        "WHERE ivolt_activedetailid = ivoltdt_itemvolumetypedetailid AND ivoltdt_itemvolumetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumetypes, itemvolumetypedetails " +
                        "WHERE ivolt_activedetailid = ivoltdt_itemvolumetypedetailid AND ivoltdt_itemvolumetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVolumeTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, itemVolumeTypeName);

            itemVolumeType = ItemVolumeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemVolumeType;
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
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemvolumetypes, itemvolumetypedetails " +
                    "WHERE ivolt_activedetailid = ivoltdt_itemvolumetypedetailid AND ivoltdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemvolumetypes, itemvolumetypedetails " +
                    "WHERE ivolt_activedetailid = ivoltdt_itemvolumetypedetailid AND ivoltdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemVolumeTypeFactory.getInstance().prepareStatement(query);

        return ItemVolumeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemvolumetypes, itemvolumetypedetails " +
                    "WHERE ivolt_activedetailid = ivoltdt_itemvolumetypedetailid " +
                    "ORDER BY ivoltdt_sortorder, ivoltdt_itemvolumetypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemvolumetypes, itemvolumetypedetails " +
                    "WHERE ivolt_activedetailid = ivoltdt_itemvolumetypedetailid " +
                    "FOR UPDATE";
        }

        var ps = ItemVolumeTypeFactory.getInstance().prepareStatement(query);

        return ItemVolumeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
            final var itemVolumeType = ItemVolumeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemVolumeTypeDetailValue.getItemVolumeTypePK());
            var itemVolumeTypeDetail = itemVolumeType.getActiveDetailForUpdate();

            itemVolumeTypeDetail.setThruTime(session.START_TIME_LONG);
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

            itemVolumeTypeDetail = ItemVolumeTypeDetailFactory.getInstance().create(itemVolumeTypePK, itemVolumeTypeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        itemVolumeTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ItemVolumeTypeDescription createItemVolumeTypeDescription(ItemVolumeType itemVolumeType, Language language,
            String description, BasePK createdBy) {
        var itemVolumeTypeDescription = ItemVolumeTypeDescriptionFactory.getInstance().create(session,
                itemVolumeType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemVolumeType.getPrimaryKey(), EventTypes.MODIFY, itemVolumeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemVolumeTypeDescription;
    }

    private ItemVolumeTypeDescription getItemVolumeTypeDescription(ItemVolumeType itemVolumeType, Language language, EntityPermission entityPermission) {
        ItemVolumeTypeDescription itemVolumeTypeDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumetypedescriptions " +
                        "WHERE ivoltd_ivolt_itemvolumetypeid = ? AND ivoltd_lang_languageid = ? AND ivoltd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumetypedescriptions " +
                        "WHERE ivoltd_ivolt_itemvolumetypeid = ? AND ivoltd_lang_languageid = ? AND ivoltd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVolumeTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemVolumeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            itemVolumeTypeDescription = ItemVolumeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemVolumeTypeDescription;
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
        List<ItemVolumeTypeDescription> itemVolumeTypeDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumetypedescriptions, languages " +
                        "WHERE ivoltd_ivolt_itemvolumetypeid = ? AND ivoltd_thrutime = ? AND ivoltd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumetypedescriptions " +
                        "WHERE ivoltd_ivolt_itemvolumetypeid = ? AND ivoltd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVolumeTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemVolumeType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemVolumeTypeDescriptions = ItemVolumeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemVolumeTypeDescriptions;
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
            var itemVolumeTypeDescription = ItemVolumeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemVolumeTypeDescriptionValue.getPrimaryKey());

            itemVolumeTypeDescription.setThruTime(session.START_TIME_LONG);
            itemVolumeTypeDescription.store();

            var itemVolumeType = itemVolumeTypeDescription.getItemVolumeType();
            var language = itemVolumeTypeDescription.getLanguage();
            var description = itemVolumeTypeDescriptionValue.getDescription();

            itemVolumeTypeDescription = ItemVolumeTypeDescriptionFactory.getInstance().create(itemVolumeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(itemVolumeType.getPrimaryKey(), EventTypes.MODIFY, itemVolumeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemVolumeTypeDescription(ItemVolumeTypeDescription itemVolumeTypeDescription, BasePK deletedBy) {
        itemVolumeTypeDescription.setThruTime(session.START_TIME_LONG);

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

    public ItemVolume createItemVolume(Item item, UnitOfMeasureType unitOfMeasureType, ItemVolumeType itemVolumeType,
            Long height, Long width, Long depth, BasePK createdBy) {
        var itemVolume = ItemVolumeFactory.getInstance().create(item, unitOfMeasureType, itemVolumeType, height, width, depth,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemVolume.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemVolume;
    }

    private ItemVolume getItemVolume(Item item, UnitOfMeasureType unitOfMeasureType, ItemVolumeType itemVolumeType,
            EntityPermission entityPermission) {
        ItemVolume itemVolume;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_uomt_unitofmeasuretypeid = ? AND ivol_ivolt_itemvolumetypeid = ? AND ivol_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_uomt_unitofmeasuretypeid = ? AND ivol_ivolt_itemvolumetypeid = ? AND ivol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVolumeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, itemVolumeType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);

            itemVolume = ItemVolumeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemVolume;
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
        List<ItemVolume> itemVolumes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "JOIN itemvolumetypes ON ivol_ivolt_itemvolumetypeid = ivolt_itemvolumetypeid " +
                        "JOIN itemvolumetypedetails ON ivolt_lastdetailid = ivoltdt_itemvolumetypedetailid " +
                        "WHERE ivol_itm_itemid = ? AND ivol_uomt_unitofmeasuretypeid = ? AND ivol_thrutime = ? " +
                        "ORDER BY ivoltdt_sortorder, ivoltdt_itemvolumetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_uomt_unitofmeasuretypeid = ? AND ivol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVolumeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            itemVolumes = ItemVolumeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemVolumes;
    }

    public List<ItemVolume> getItemVolumes(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolumes(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<ItemVolume> getItemVolumesForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolumes(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<ItemVolume> getItemVolumesByItem(Item item, EntityPermission entityPermission) {
        List<ItemVolume> itemVolumes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "JOIN unitofmeasuretypes ON ivol_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid " +
                        "JOIN unitofmeasuretypedetails ON uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "JOIN itemvolumetypes ON ivol_ivolt_itemvolumetypeid = ivolt_itemvolumetypeid " +
                        "JOIN itemvolumetypedetails ON ivolt_lastdetailid = ivoltdt_itemvolumetypedetailid " +
                        "WHERE ivol_itm_itemid = ? AND ivol_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, ivoltdt_sortorder, ivoltdt_itemvolumetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVolumeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemVolumes = ItemVolumeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemVolumes;
    }

    public List<ItemVolume> getItemVolumesByItem(Item item) {
        return getItemVolumesByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemVolume> getItemVolumesByItemForUpdate(Item item) {
        return getItemVolumesByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemVolume> getItemVolumesByItemVolumeType(ItemVolumeType itemVolumeType, EntityPermission entityPermission) {
        List<ItemVolume> itemVolumes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "JOIN items ON ivol_itm_itemid = itm_itemid " +
                        "JOIN itemdetails ON itm_lastdetailid = itmdt_itemdetailid " +
                        "JOIN unitofmeasuretypes ON ivol_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid " +
                        "JOIN unitofmeasuretypedetails ON uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "WHERE ivol_uomt_unitofmeasuretypeid = ? AND ivol_thrutime = ? " +
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemVolumeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemVolumeType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemVolumes = ItemVolumeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemVolumes;
    }

    public List<ItemVolume> getItemVolumesByItemVolumeType(ItemVolumeType itemVolumeType) {
        return getItemVolumesByItemVolumeType(itemVolumeType, EntityPermission.READ_ONLY);
    }

    public List<ItemVolume> getItemVolumesByItemVolumeTypeForUpdate(ItemVolumeType itemVolumeType) {
        return getItemVolumesByItemVolumeType(itemVolumeType, EntityPermission.READ_WRITE);
    }

    public void updateItemVolumeFromValue(ItemVolumeValue itemVolumeValue, BasePK updatedBy) {
        if(itemVolumeValue.hasBeenModified()) {
            var itemVolume = ItemVolumeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemVolumeValue.getPrimaryKey());

            itemVolume.setThruTime(session.START_TIME_LONG);
            itemVolume.store();

            var itemPK = itemVolume.getItemPK();
            var unitOfMeasureTypePK = itemVolume.getUnitOfMeasureTypePK();
            var itemVolumeTypePK = itemVolume.getItemVolumeTypePK();
            var height = itemVolumeValue.getHeight();
            var width = itemVolumeValue.getWidth();
            var depth = itemVolumeValue.getDepth();

            itemVolume = ItemVolumeFactory.getInstance().create(itemPK, unitOfMeasureTypePK, itemVolumeTypePK, height,
                    width, depth, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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

    public List<ItemVolumeTransfer> getItemVolumeTransfersByItem(UserVisit userVisit, Item item) {
        var itemVolumes = getItemVolumesByItem(item);
        List<ItemVolumeTransfer> itemVolumeTransfers = new ArrayList<>(itemVolumes.size());

        itemVolumes.forEach((itemVolume) ->
                itemVolumeTransfers.add(itemVolumeTransferCache.getTransfer(userVisit, itemVolume))
        );

        return itemVolumeTransfers;
    }

    public void deleteItemVolume(ItemVolume itemVolume, BasePK deletedBy) {
        itemVolume.setThruTime(session.START_TIME_LONG);

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

        var itemWeightType = ItemWeightTypeFactory.getInstance().create();
        var itemWeightTypeDetail = ItemWeightTypeDetailFactory.getInstance().create(session, itemWeightType, itemWeightTypeName,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemWeightType = ItemWeightTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemWeightType.getPrimaryKey());
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

        return ItemWeightTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ItemWeightType getItemWeightTypeByEntityInstance(final EntityInstance entityInstance) {
        return getItemWeightTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemWeightType getItemWeightTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getItemWeightTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemWeightType getItemWeightTypeByPK(ItemWeightTypePK pk) {
        return ItemWeightTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countItemWeightTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM itemweighttypes, itemweighttypedetails " +
                        "WHERE iwghtt_activedetailid = iwghttdt_itemweighttypedetailid");
    }

    public ItemWeightType getItemWeightTypeByName(String itemWeightTypeName, EntityPermission entityPermission) {
        ItemWeightType itemWeightType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweighttypes, itemweighttypedetails " +
                        "WHERE iwghtt_activedetailid = iwghttdt_itemweighttypedetailid AND iwghttdt_itemweighttypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweighttypes, itemweighttypedetails " +
                        "WHERE iwghtt_activedetailid = iwghttdt_itemweighttypedetailid AND iwghttdt_itemweighttypename = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemWeightTypeFactory.getInstance().prepareStatement(query);

            ps.setString(1, itemWeightTypeName);

            itemWeightType = ItemWeightTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemWeightType;
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
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemweighttypes, itemweighttypedetails " +
                    "WHERE iwghtt_activedetailid = iwghttdt_itemweighttypedetailid AND iwghttdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemweighttypes, itemweighttypedetails " +
                    "WHERE iwghtt_activedetailid = iwghttdt_itemweighttypedetailid AND iwghttdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = ItemWeightTypeFactory.getInstance().prepareStatement(query);

        return ItemWeightTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM itemweighttypes, itemweighttypedetails " +
                    "WHERE iwghtt_activedetailid = iwghttdt_itemweighttypedetailid " +
                    "ORDER BY iwghttdt_sortorder, iwghttdt_itemweighttypename " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM itemweighttypes, itemweighttypedetails " +
                    "WHERE iwghtt_activedetailid = iwghttdt_itemweighttypedetailid " +
                    "FOR UPDATE";
        }

        var ps = ItemWeightTypeFactory.getInstance().prepareStatement(query);

        return ItemWeightTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
            final var itemWeightType = ItemWeightTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemWeightTypeDetailValue.getItemWeightTypePK());
            var itemWeightTypeDetail = itemWeightType.getActiveDetailForUpdate();

            itemWeightTypeDetail.setThruTime(session.START_TIME_LONG);
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

            itemWeightTypeDetail = ItemWeightTypeDetailFactory.getInstance().create(itemWeightTypePK, itemWeightTypeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        itemWeightTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ItemWeightTypeDescription createItemWeightTypeDescription(ItemWeightType itemWeightType, Language language,
            String description, BasePK createdBy) {
        var itemWeightTypeDescription = ItemWeightTypeDescriptionFactory.getInstance().create(session,
                itemWeightType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(itemWeightType.getPrimaryKey(), EventTypes.MODIFY, itemWeightTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemWeightTypeDescription;
    }

    private ItemWeightTypeDescription getItemWeightTypeDescription(ItemWeightType itemWeightType, Language language, EntityPermission entityPermission) {
        ItemWeightTypeDescription itemWeightTypeDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweighttypedescriptions " +
                        "WHERE iwghttd_iwghtt_itemweighttypeid = ? AND iwghttd_lang_languageid = ? AND iwghttd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweighttypedescriptions " +
                        "WHERE iwghttd_iwghtt_itemweighttypeid = ? AND iwghttd_lang_languageid = ? AND iwghttd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemWeightTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemWeightType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            itemWeightTypeDescription = ItemWeightTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemWeightTypeDescription;
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
        List<ItemWeightTypeDescription> itemWeightTypeDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweighttypedescriptions, languages " +
                        "WHERE iwghttd_iwghtt_itemweighttypeid = ? AND iwghttd_thrutime = ? AND iwghttd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweighttypedescriptions " +
                        "WHERE iwghttd_iwghtt_itemweighttypeid = ? AND iwghttd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemWeightTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemWeightType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemWeightTypeDescriptions = ItemWeightTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemWeightTypeDescriptions;
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
            var itemWeightTypeDescription = ItemWeightTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemWeightTypeDescriptionValue.getPrimaryKey());

            itemWeightTypeDescription.setThruTime(session.START_TIME_LONG);
            itemWeightTypeDescription.store();

            var itemWeightType = itemWeightTypeDescription.getItemWeightType();
            var language = itemWeightTypeDescription.getLanguage();
            var description = itemWeightTypeDescriptionValue.getDescription();

            itemWeightTypeDescription = ItemWeightTypeDescriptionFactory.getInstance().create(itemWeightType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(itemWeightType.getPrimaryKey(), EventTypes.MODIFY, itemWeightTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemWeightTypeDescription(ItemWeightTypeDescription itemWeightTypeDescription, BasePK deletedBy) {
        itemWeightTypeDescription.setThruTime(session.START_TIME_LONG);

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
    
    public ItemWeight createItemWeight(Item item, UnitOfMeasureType unitOfMeasureType, ItemWeightType itemWeightType,
            Long weight, BasePK createdBy) {
        var itemWeight = ItemWeightFactory.getInstance().create(item, unitOfMeasureType, itemWeightType, weight,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemWeight.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemWeight;
    }
    
    private ItemWeight getItemWeight(Item item, UnitOfMeasureType unitOfMeasureType, ItemWeightType itemWeightType,
            EntityPermission entityPermission) {
        ItemWeight itemWeight;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_uomt_unitofmeasuretypeid = ? AND iwght_iwghtt_itemweighttypeid = ? AND iwght_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_uomt_unitofmeasuretypeid = ? AND iwght_iwghtt_itemweighttypeid = ? AND iwght_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemWeightFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, itemWeightType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            itemWeight = ItemWeightFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemWeight;
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
        List<ItemWeight> itemWeights;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "JOIN itemweighttypes ON iwght_iwghtt_itemweighttypeid = iwghtt_itemweighttypeid " +
                        "JOIN itemweighttypedetails ON iwghtt_lastdetailid = iwghttdt_itemweighttypedetailid " +
                        "WHERE iwght_itm_itemid = ? AND iwght_uomt_unitofmeasuretypeid = ? AND iwght_thrutime = ? " +
                        "ORDER BY iwghttdt_sortorder, iwghttdt_itemweighttypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_uomt_unitofmeasuretypeid = ? AND iwght_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemWeightFactory.getInstance().prepareStatement(query);

            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            itemWeights = ItemWeightFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemWeights;
    }

    public List<ItemWeight> getItemWeights(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeights(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<ItemWeight> getItemWeightsForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeights(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<ItemWeight> getItemWeightsByItem(Item item, EntityPermission entityPermission) {
        List<ItemWeight> itemWeights;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "JOIN unitofmeasuretypes ON iwght_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid " +
                        "JOIN unitofmeasuretypedetails ON uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "JOIN itemweighttypes ON iwght_iwghtt_itemweighttypeid = iwghtt_itemweighttypeid " +
                        "JOIN itemweighttypedetails ON iwghtt_lastdetailid = iwghttdt_itemweighttypedetailid " +
                        "WHERE iwght_itm_itemid = ? AND iwght_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, iwghttdt_sortorder, iwghttdt_itemweighttypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemWeightFactory.getInstance().prepareStatement(query);

            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemWeights = ItemWeightFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemWeights;
    }

    public List<ItemWeight> getItemWeightsByItem(Item item) {
        return getItemWeightsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemWeight> getItemWeightsByItemForUpdate(Item item) {
        return getItemWeightsByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemWeight> getItemWeightsByItemWeightType(ItemWeightType itemWeightType, EntityPermission entityPermission) {
        List<ItemWeight> itemWeights;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "JOIN items ON iwght_itm_itemid = itm_itemid " +
                        "JOIN itemdetails ON itm_lastdetailid = itmdt_itemdetailid " +
                        "JOIN unitofmeasuretypes ON iwght_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid " +
                        "JOIN unitofmeasuretypedetails ON uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "WHERE iwght_uomt_unitofmeasuretypeid = ? AND iwght_thrutime = ? " +
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ItemWeightFactory.getInstance().prepareStatement(query);

            ps.setLong(1, itemWeightType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            itemWeights = ItemWeightFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemWeights;
    }

    public List<ItemWeight> getItemWeightsByItemWeightType(ItemWeightType itemWeightType) {
        return getItemWeightsByItemWeightType(itemWeightType, EntityPermission.READ_ONLY);
    }

    public List<ItemWeight> getItemWeightsByItemWeightTypeForUpdate(ItemWeightType itemWeightType) {
        return getItemWeightsByItemWeightType(itemWeightType, EntityPermission.READ_WRITE);
    }

    public void updateItemWeightFromValue(ItemWeightValue itemWeightValue, BasePK updatedBy) {
        if(itemWeightValue.hasBeenModified()) {
            var itemWeight = ItemWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemWeightValue.getPrimaryKey());
            
            itemWeight.setThruTime(session.START_TIME_LONG);
            itemWeight.store();

            var itemPK = itemWeight.getItemPK();
            var unitOfMeasureTypePK = itemWeight.getUnitOfMeasureTypePK();
            var itemWeightTypePK = itemWeight.getItemWeightTypePK();
            var weight = itemWeightValue.getWeight();
            
            itemWeight = ItemWeightFactory.getInstance().create(itemPK, unitOfMeasureTypePK, itemWeightTypePK, weight,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        itemWeight.setThruTime(session.START_TIME_LONG);
        
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

        var relatedItemType = RelatedItemTypeFactory.getInstance().create();
        var relatedItemTypeDetail = RelatedItemTypeDetailFactory.getInstance().create(relatedItemType,
                relatedItemTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        relatedItemType = RelatedItemTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return RelatedItemTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public RelatedItemType getRelatedItemTypeByEntityInstance(final EntityInstance entityInstance) {
        return getRelatedItemTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public RelatedItemType getRelatedItemTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getRelatedItemTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public RelatedItemType getRelatedItemTypeByPK(RelatedItemTypePK pk) {
        return RelatedItemTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countRelatedItemTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid");
    }

    private static final Map<EntityPermission, String> getRelatedItemTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid " +
                "AND rltitypdt_relateditemtypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid " +
                "AND rltitypdt_relateditemtypename = ? " +
                "FOR UPDATE");
        getRelatedItemTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public RelatedItemType getRelatedItemTypeByName(String relatedItemTypeName, EntityPermission entityPermission) {
        return RelatedItemTypeFactory.getInstance().getEntityFromQuery(entityPermission, getRelatedItemTypeByNameQueries, relatedItemTypeName);
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

    private static final Map<EntityPermission, String> getDefaultRelatedItemTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid " +
                "AND rltitypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid " +
                "AND rltitypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultRelatedItemTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public RelatedItemType getDefaultRelatedItemType(EntityPermission entityPermission) {
        return RelatedItemTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultRelatedItemTypeQueries);
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

    private static final Map<EntityPermission, String> getRelatedItemTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid " +
                "ORDER BY rltitypdt_sortorder, rltitypdt_relateditemtypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid " +
                "FOR UPDATE");
        getRelatedItemTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItemType> getRelatedItemTypes(EntityPermission entityPermission) {
        return RelatedItemTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemTypesQueries);
    }

    public List<RelatedItemType> getRelatedItemTypes() {
        return getRelatedItemTypes(EntityPermission.READ_ONLY);
    }

    public List<RelatedItemType> getRelatedItemTypesForUpdate() {
        return getRelatedItemTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getRelatedItemTypesByParentRelatedItemTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid AND rltitypdt_parentrelateditemtypeid = ? " +
                "ORDER BY rltitypdt_sortorder, rltitypdt_relateditemtypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditemtypes, relateditemtypedetails " +
                "WHERE rltityp_activedetailid = rltitypdt_relateditemtypedetailid AND rltitypdt_parentrelateditemtypeid = ? " +
                "FOR UPDATE");
        getRelatedItemTypesByParentRelatedItemTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItemType> getRelatedItemTypesByParentRelatedItemType(RelatedItemType parentRelatedItemType, EntityPermission entityPermission) {
        return RelatedItemTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemTypesByParentRelatedItemTypeQueries,
                parentRelatedItemType);
    }

    public List<RelatedItemType> getRelatedItemTypesByParentRelatedItemType(RelatedItemType parentRelatedItemType) {
        return getRelatedItemTypesByParentRelatedItemType(parentRelatedItemType, EntityPermission.READ_ONLY);
    }

    public List<RelatedItemType> getRelatedItemTypesByParentRelatedItemTypeForUpdate(RelatedItemType parentRelatedItemType) {
        return getRelatedItemTypesByParentRelatedItemType(parentRelatedItemType, EntityPermission.READ_WRITE);
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

    public List<RelatedItemTypeTransfer> getRelatedItemTypeTransfersByParentRelatedItemType(UserVisit userVisit,
            RelatedItemType parentRelatedItemType) {
        return getRelatedItemTypeTransfers(userVisit, getRelatedItemTypesByParentRelatedItemType(parentRelatedItemType));
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
            var relatedItemType = RelatedItemTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     relatedItemTypeDetailValue.getRelatedItemTypePK());
            var relatedItemTypeDetail = relatedItemType.getActiveDetailForUpdate();

            relatedItemTypeDetail.setThruTime(session.START_TIME_LONG);
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

            relatedItemTypeDetail = RelatedItemTypeDetailFactory.getInstance().create(relatedItemTypePK,
                    relatedItemTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        relatedItemTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public RelatedItemTypeDescription createRelatedItemTypeDescription(RelatedItemType relatedItemType,
            Language language, String description, BasePK createdBy) {
        var relatedItemTypeDescription = RelatedItemTypeDescriptionFactory.getInstance().create(relatedItemType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(relatedItemType.getPrimaryKey(), EventTypes.MODIFY, relatedItemTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return relatedItemTypeDescription;
    }

    private static final Map<EntityPermission, String> getRelatedItemTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditemtypedescriptions " +
                "WHERE rltitypd_rltityp_relateditemtypeid = ? AND rltitypd_lang_languageid = ? AND rltitypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditemtypedescriptions " +
                "WHERE rltitypd_rltityp_relateditemtypeid = ? AND rltitypd_lang_languageid = ? AND rltitypd_thrutime = ? " +
                "FOR UPDATE");
        getRelatedItemTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private RelatedItemTypeDescription getRelatedItemTypeDescription(RelatedItemType relatedItemType,
            Language language, EntityPermission entityPermission) {
        return RelatedItemTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getRelatedItemTypeDescriptionQueries,
                relatedItemType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getRelatedItemTypeDescriptionsByRelatedItemTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditemtypedescriptions, languages " +
                "WHERE rltitypd_rltityp_relateditemtypeid = ? AND rltitypd_thrutime = ? AND rltitypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditemtypedescriptions " +
                "WHERE rltitypd_rltityp_relateditemtypeid = ? AND rltitypd_thrutime = ? " +
                "FOR UPDATE");
        getRelatedItemTypeDescriptionsByRelatedItemTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItemTypeDescription> getRelatedItemTypeDescriptionsByRelatedItemType(RelatedItemType relatedItemType,
            EntityPermission entityPermission) {
        return RelatedItemTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemTypeDescriptionsByRelatedItemTypeQueries,
                relatedItemType, Session.MAX_TIME);
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
            var relatedItemTypeDescription = RelatedItemTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    relatedItemTypeDescriptionValue.getPrimaryKey());

            relatedItemTypeDescription.setThruTime(session.START_TIME_LONG);
            relatedItemTypeDescription.store();

            var relatedItemType = relatedItemTypeDescription.getRelatedItemType();
            var language = relatedItemTypeDescription.getLanguage();
            var description = relatedItemTypeDescriptionValue.getDescription();

            relatedItemTypeDescription = RelatedItemTypeDescriptionFactory.getInstance().create(relatedItemType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(relatedItemType.getPrimaryKey(), EventTypes.MODIFY, relatedItemTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteRelatedItemTypeDescription(RelatedItemTypeDescription relatedItemTypeDescription, BasePK deletedBy) {
        relatedItemTypeDescription.setThruTime(session.START_TIME_LONG);

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

    public RelatedItem createRelatedItem(RelatedItemType relatedItemType, Item fromItem, Item toItem, Integer sortOrder, BasePK createdBy) {
        var relatedItem = RelatedItemFactory.getInstance().create();
        var relatedItemDetail = RelatedItemDetailFactory.getInstance().create(relatedItem, relatedItemType, fromItem, toItem, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        relatedItem = RelatedItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                relatedItem.getPrimaryKey());
        relatedItem.setActiveDetail(relatedItemDetail);
        relatedItem.setLastDetail(relatedItemDetail);
        relatedItem.store();

        sendEvent(relatedItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return relatedItem;
    }

    private static final Map<EntityPermission, String> getRelatedItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_fromitemid = ? AND rltidt_toitemid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_fromitemid = ? AND rltidt_toitemid = ? " +
                "FOR UPDATE");
        getRelatedItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private RelatedItem getRelatedItem(RelatedItemType relatedItemType, Item fromItem, Item toItem, EntityPermission entityPermission) {
        return RelatedItemFactory.getInstance().getEntityFromQuery(entityPermission, getRelatedItemQueries, relatedItemType, fromItem, toItem);
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

    private static final Map<EntityPermission, String> getRelatedItemsByRelatedItemTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails, items, itemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? " +
                "AND rltidt_fromitemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                "ORDER BY rltidt_sortorder, itmdt_itemname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? " +
                "FOR UPDATE");
        getRelatedItemsByRelatedItemTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItem> getRelatedItemsByRelatedItemType(RelatedItemType relatedItemType, EntityPermission entityPermission) {
        return RelatedItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemsByRelatedItemTypeQueries, relatedItemType);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemType(RelatedItemType relatedItemType) {
        return getRelatedItemsByRelatedItemType(relatedItemType, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeForUpdate(RelatedItemType relatedItemType) {
        return getRelatedItemsByRelatedItemType(relatedItemType, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByRelatedItemType(RelatedItemType relatedItemType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ?",
                relatedItemType);
    }

    private static final Map<EntityPermission, String> getRelatedItemsByRelatedItemTypeAndFromItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails, items, itemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_fromitemid = ? " +
                "AND rltidt_fromitemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                "ORDER BY rltidt_sortorder, itmdt_itemname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_fromitemid = ? " +
                "FOR UPDATE");
        getRelatedItemsByRelatedItemTypeAndFromItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItem> getRelatedItemsByRelatedItemTypeAndFromItem(RelatedItemType relatedItemType, Item fromItem, EntityPermission entityPermission) {
        return RelatedItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemsByRelatedItemTypeAndFromItemQueries, relatedItemType, fromItem);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndFromItem(RelatedItemType relatedItemType, Item fromItem) {
        return getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndFromItemForUpdate(RelatedItemType relatedItemType, Item fromItem) {
        return getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByRelatedItemTypeAndFromItem(RelatedItemType relatedItemType, Item fromItem) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_fromitemid = ?",
                relatedItemType, fromItem);
    }

    private static final Map<EntityPermission, String> getRelatedItemsByRelatedItemTypeAndToItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails, items, itemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_toitemid = ? " +
                "AND rltidt_toitemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                "ORDER BY rltidt_sortorder, itmdt_itemname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_toitemid = ? " +
                "FOR UPDATE");
        getRelatedItemsByRelatedItemTypeAndToItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItem> getRelatedItemsByRelatedItemTypeAndToItem(RelatedItemType relatedItemType, Item toItem, EntityPermission entityPermission) {
        return RelatedItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemsByRelatedItemTypeAndToItemQueries, relatedItemType, toItem);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndToItem(RelatedItemType relatedItemType, Item toItem) {
        return getRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByRelatedItemTypeAndToItemForUpdate(RelatedItemType relatedItemType, Item toItem) {
        return getRelatedItemsByRelatedItemTypeAndToItem(relatedItemType, toItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByRelatedItemTypeAndToItem(RelatedItemType relatedItemType, Item toItem) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_rltityp_relateditemtypeid = ? AND rltidt_toitemid = ?",
                relatedItemType, toItem);
    }

    private static final Map<EntityPermission, String> getRelatedItemsByFromItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails, relateditemtypes, relateditemtypedetails, items, itemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_fromitemid = ? " +
                "AND rltidt_rltityp_relateditemtypeid = rltityp_relateditemtypeid AND rltityp_lastdetailid = rltitypdt_relateditemtypedetailid " +
                "AND rltidt_fromitemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                "ORDER BY rltitypdt_sortorder, rltitypdt_relateditemtypename, rltidt_sortorder, itmdt_itemname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_fromitemid = ? " +
                "FOR UPDATE");
        getRelatedItemsByFromItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItem> getRelatedItemsByFromItem(Item fromItem, EntityPermission entityPermission) {
        return RelatedItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemsByFromItemQueries, fromItem);
    }

    public List<RelatedItem> getRelatedItemsByFromItem(Item fromItem) {
        return getRelatedItemsByFromItem(fromItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByFromItemForUpdate(Item fromItem) {
        return getRelatedItemsByFromItem(fromItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByFromItem(Item fromItem) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_fromitemid = ?",
                fromItem);
    }

    private static final Map<EntityPermission, String> getRelatedItemsByToItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails, relateditemtypes, relateditemtypedetails, items, itemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_toitemid = ? " +
                "AND rltidt_rltityp_relateditemtypeid = rltityp_relateditemtypeid AND rltityp_lastdetailid = rltitypdt_relateditemtypedetailid " +
                "AND rltidt_toitemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                "ORDER BY rltitypdt_sortorder, rltitypdt_relateditemtypename, rltidt_sortorder, itmdt_itemname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_toitemid = ? " +
                "FOR UPDATE");
        getRelatedItemsByToItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RelatedItem> getRelatedItemsByToItem(Item toItem, EntityPermission entityPermission) {
        return RelatedItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getRelatedItemsByToItemQueries, toItem);
    }

    public List<RelatedItem> getRelatedItemsByToItem(Item toItem) {
        return getRelatedItemsByToItem(toItem, EntityPermission.READ_ONLY);
    }

    public List<RelatedItem> getRelatedItemsByToItemForUpdate(Item toItem) {
        return getRelatedItemsByToItem(toItem, EntityPermission.READ_WRITE);
    }

    public long countRelatedItemsByToItem(Item toItem) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM relateditems, relateditemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_toitemid = ?",
                toItem);
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
            var relatedItem = RelatedItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     relatedItemDetailValue.getRelatedItemPK());
            var relatedItemDetail = relatedItem.getActiveDetailForUpdate();

            relatedItemDetail.setThruTime(session.START_TIME_LONG);
            relatedItemDetail.store();

            var relatedItemPK = relatedItemDetail.getRelatedItemPK(); // Not updated
            var relatedItemTypePK = relatedItemDetail.getRelatedItemTypePK(); // Not updated
            var fromItemPK = relatedItemDetail.getFromItemPK(); // Not updated
            var toItemPK = relatedItemDetail.getToItemPK(); // Not updated
            var sortOrder = relatedItemDetailValue.getSortOrder();

            relatedItemDetail = RelatedItemDetailFactory.getInstance().create(relatedItemPK, relatedItemTypePK, fromItemPK, toItemPK, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            relatedItem.setActiveDetail(relatedItemDetail);
            relatedItem.setLastDetail(relatedItemDetail);

            sendEvent(relatedItemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteRelatedItem(RelatedItem relatedItem, BasePK deletedBy) {
        var relatedItemDetail = relatedItem.getLastDetailForUpdate();
        relatedItemDetail.setThruTime(session.START_TIME_LONG);
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

        var harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().create();
        var harmonizedTariffScheduleCodeDetail = HarmonizedTariffScheduleCodeDetailFactory.getInstance().create(session,
                harmonizedTariffScheduleCode, countryGeoCode, harmonizedTariffScheduleCodeName, firstHarmonizedTariffScheduleCodeUnit,
                secondHarmonizedTariffScheduleCodeUnit, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCode.getPrimaryKey());
        harmonizedTariffScheduleCode.setActiveDetail(harmonizedTariffScheduleCodeDetail);
        harmonizedTariffScheduleCode.setLastDetail(harmonizedTariffScheduleCodeDetail);
        harmonizedTariffScheduleCode.store();

        sendEvent(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return harmonizedTariffScheduleCode;
    }

    public long countHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_countrygeocodeid = ?",
                countryGeoCode);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.HarmonizedTariffScheduleCode */
    public HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByEntityInstance(EntityInstance entityInstance) {
        var pk = new HarmonizedTariffScheduleCodePK(entityInstance.getEntityUniqueId());
        var harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return harmonizedTariffScheduleCode;
    }
    
    public long countHarmonizedTariffScheduleCodes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails " +
                "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid");
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodesByCountryGeoCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_countrygeocodeid = ? "
                + "ORDER BY hztscdt_sortorder, hztscdt_harmonizedtariffschedulecodename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_countrygeocodeid = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodesByCountryGeoCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodesByCountryGeoCodeQueries,
                countryGeoCode);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode) {
        return getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnitQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_firstharmonizedtariffschedulecodeunitid = ? "
                + "ORDER BY hztscdt_sortorder, hztscdt_harmonizedtariffschedulecodename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_firstharmonizedtariffschedulecodeunitid = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnitQueries,
                firstHarmonizedTariffScheduleCodeUnit);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(firstHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnitForUpdate(HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesByFirstHarmonizedTariffScheduleCodeUnit(firstHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnitQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_secondharmonizedtariffschedulecodeunitid = ? "
                + "ORDER BY hztscdt_sortorder, hztscdt_harmonizedtariffschedulecodename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_secondharmonizedtariffschedulecodeunitid = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnitQueries,
                secondHarmonizedTariffScheduleCodeUnit);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(secondHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCode> getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnitForUpdate(HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit) {
        return getHarmonizedTariffScheduleCodesBySecondHarmonizedTariffScheduleCodeUnit(secondHarmonizedTariffScheduleCodeUnit, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultHarmonizedTariffScheduleCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid "
                + "AND hztscdt_countrygeocodeid = ? AND hztscdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid "
                + "AND hztscdt_countrygeocodeid = ? AND hztscdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultHarmonizedTariffScheduleCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCode getDefaultHarmonizedTariffScheduleCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultHarmonizedTariffScheduleCodeQueries,
                countryGeoCode);
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

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid "
                + "AND hztscdt_countrygeocodeid = ? AND hztscdt_harmonizedtariffschedulecodename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid "
                + "AND hztscdt_countrygeocodeid = ? AND hztscdt_harmonizedtariffschedulecodename = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByName(GeoCode countryGeoCode, String harmonizedTariffScheduleCodeName, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromQuery(entityPermission, getHarmonizedTariffScheduleCodeByNameQueries,
                countryGeoCode, harmonizedTariffScheduleCodeName);
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
            var harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodePK());
            var harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getActiveDetailForUpdate();

            harmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
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

            harmonizedTariffScheduleCodeDetail = HarmonizedTariffScheduleCodeDetailFactory.getInstance().create(harmonizedTariffScheduleCodePK,
                    countryGeoCodePK, harmonizedTariffScheduleCodeName, firstHarmonizedTariffScheduleCodeUnitPK, secondHarmonizedTariffScheduleCodeUnitPK, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        harmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
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

    public HarmonizedTariffScheduleCodeTranslation createHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            Language language, String description, MimeType overviewMimeType, String overview, BasePK createdBy) {
        var harmonizedTariffScheduleCodeTranslation = HarmonizedTariffScheduleCodeTranslationFactory.getInstance().create(harmonizedTariffScheduleCode,
                language, description, overviewMimeType, overview, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeTranslation;
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeTranslationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodetranslations "
                + "WHERE hztsctr_hztsc_harmonizedtariffschedulecodeid = ? AND hztsctr_lang_languageid = ? AND hztsctr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodetranslations "
                + "WHERE hztsctr_hztsc_harmonizedtariffschedulecodeid = ? AND hztsctr_lang_languageid = ? AND hztsctr_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeTranslationQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeTranslation getHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, Language language, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getHarmonizedTariffScheduleCodeTranslationQueries,
                harmonizedTariffScheduleCode, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodetranslations, languages "
                + "WHERE hztsctr_hztsc_harmonizedtariffschedulecodeid = ? AND hztsctr_thrutime = ? AND hztsctr_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodetranslations "
                + "WHERE hztsctr_hztsc_harmonizedtariffschedulecodeid = ? AND hztsctr_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCodeTranslation> getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCodeQueries,
                harmonizedTariffScheduleCode, Session.MAX_TIME);
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
            var harmonizedTariffScheduleCodeTranslation = HarmonizedTariffScheduleCodeTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeTranslationValue.getPrimaryKey());

            harmonizedTariffScheduleCodeTranslation.setThruTime(session.START_TIME_LONG);
            harmonizedTariffScheduleCodeTranslation.store();

            var harmonizedTariffScheduleCodePK = harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCodePK();
            var languagePK = harmonizedTariffScheduleCodeTranslation.getLanguagePK();
            var description = harmonizedTariffScheduleCodeTranslationValue.getDescription();
            var overviewMimeTypePK = harmonizedTariffScheduleCodeTranslationValue.getOverviewMimeTypePK();
            var overview = harmonizedTariffScheduleCodeTranslationValue.getOverview();

            harmonizedTariffScheduleCodeTranslation = HarmonizedTariffScheduleCodeTranslationFactory.getInstance().create(harmonizedTariffScheduleCodePK,
                    languagePK, description, overviewMimeTypePK, overview, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(harmonizedTariffScheduleCodePK, EventTypes.MODIFY, harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation, BasePK deletedBy) {
        harmonizedTariffScheduleCodeTranslation.setThruTime(session.START_TIME_LONG);

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

        var harmonizedTariffScheduleCodeUseType = HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().create();
        var harmonizedTariffScheduleCodeUseTypeDetail = HarmonizedTariffScheduleCodeUseTypeDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUseType,
                harmonizedTariffScheduleCodeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        harmonizedTariffScheduleCodeUseType = HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCodeUseType.getPrimaryKey());
        harmonizedTariffScheduleCodeUseType.setActiveDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.setLastDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.store();

        sendEvent(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return harmonizedTariffScheduleCodeUseType;
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUseTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE hztscutyp_activedetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid AND hztscutypdt_harmonizedtariffschedulecodeusetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE hztscutyp_activedetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid AND hztscutypdt_harmonizedtariffschedulecodeusetypename = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUseTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByName(String harmonizedTariffScheduleCodeUseTypeName, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUseTypeByNameQueries,
                harmonizedTariffScheduleCodeUseTypeName);
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

    private static final Map<EntityPermission, String> getDefaultHarmonizedTariffScheduleCodeUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE hztscutyp_activedetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid AND hztscutypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE hztscutyp_activedetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid AND hztscutypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultHarmonizedTariffScheduleCodeUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeUseType getDefaultHarmonizedTariffScheduleCodeUseType(EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultHarmonizedTariffScheduleCodeUseTypeQueries);
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

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUseTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE hztscutyp_activedetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid "
                + "ORDER BY hztscutypdt_sortorder, hztscutypdt_harmonizedtariffschedulecodeusetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE hztscutyp_activedetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUseTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCodeUseType> getHarmonizedTariffScheduleCodeUseTypes(EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUseTypesQueries);
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

    public List<HarmonizedTariffScheduleCodeUseTypeTransfer> getHarmonizedTariffScheduleCodeUseTypeTransfers(UserVisit userVisit) {
        var harmonizedTariffScheduleCodeUseTypes = getHarmonizedTariffScheduleCodeUseTypes();
        List<HarmonizedTariffScheduleCodeUseTypeTransfer> harmonizedTariffScheduleCodeUseTypeTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUseTypes.size());

        harmonizedTariffScheduleCodeUseTypes.forEach((harmonizedTariffScheduleCodeUseType) ->
                harmonizedTariffScheduleCodeUseTypeTransfers.add(harmonizedTariffScheduleCodeUseTypeTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUseType))
        );

        return harmonizedTariffScheduleCodeUseTypeTransfers;
    }

    private void updateHarmonizedTariffScheduleCodeUseTypeFromValue(HarmonizedTariffScheduleCodeUseTypeDetailValue harmonizedTariffScheduleCodeUseTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        var harmonizedTariffScheduleCodeUseType = HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, harmonizedTariffScheduleCodeUseTypeDetailValue.getHarmonizedTariffScheduleCodeUseTypePK());
        var harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getActiveDetailForUpdate();

        harmonizedTariffScheduleCodeUseTypeDetail.setThruTime(session.START_TIME_LONG);
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

        harmonizedTariffScheduleCodeUseTypeDetail = HarmonizedTariffScheduleCodeUseTypeDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUseTypePK, harmonizedTariffScheduleCodeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

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
        harmonizedTariffScheduleCodeUseTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public HarmonizedTariffScheduleCodeUseTypeDescription createHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType,
            Language language, String description, BasePK createdBy) {
        var harmonizedTariffScheduleCodeUseTypeDescription = HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUseType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeUseTypeDescription;
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUseTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypedescriptions "
                + "WHERE hztscutypd_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscutypd_lang_languageid = ? AND hztscutypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypedescriptions "
                + "WHERE hztscutypd_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscutypd_lang_languageid = ? AND hztscutypd_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUseTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeUseTypeDescription getHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, Language language, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUseTypeDescriptionQueries,
                harmonizedTariffScheduleCodeUseType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypedescriptions, languages "
                + "WHERE hztscutypd_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscutypd_thrutime = ? AND hztscutypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeusetypedescriptions "
                + "WHERE hztscutypd_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscutypd_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCodeUseTypeDescription> getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseTypeQueries,
                harmonizedTariffScheduleCodeUseType, Session.MAX_TIME);
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
            var harmonizedTariffScheduleCodeUseTypeDescription = HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeUseTypeDescriptionValue.getPrimaryKey());

            harmonizedTariffScheduleCodeUseTypeDescription.setThruTime(session.START_TIME_LONG);
            harmonizedTariffScheduleCodeUseTypeDescription.store();

            var harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseType();
            var language = harmonizedTariffScheduleCodeUseTypeDescription.getLanguage();
            var description = harmonizedTariffScheduleCodeUseTypeDescriptionValue.getDescription();

            harmonizedTariffScheduleCodeUseTypeDescription = HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUseTypeDescription.setThruTime(session.START_TIME_LONG);

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

        var harmonizedTariffScheduleCodeUnit = HarmonizedTariffScheduleCodeUnitFactory.getInstance().create();
        var harmonizedTariffScheduleCodeUnitDetail = HarmonizedTariffScheduleCodeUnitDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUnit,
                harmonizedTariffScheduleCodeUnitName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        harmonizedTariffScheduleCodeUnit = HarmonizedTariffScheduleCodeUnitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCodeUnit.getPrimaryKey());
        harmonizedTariffScheduleCodeUnit.setActiveDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.setLastDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.store();

        sendEvent(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return harmonizedTariffScheduleCodeUnit;
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUnitByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunits, harmonizedtariffschedulecodeunitdetails "
                + "WHERE hztscunt_activedetailid = hztscuntdt_harmonizedtariffschedulecodeunitdetailid AND hztscuntdt_harmonizedtariffschedulecodeunitname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunits, harmonizedtariffschedulecodeunitdetails "
                + "WHERE hztscunt_activedetailid = hztscuntdt_harmonizedtariffschedulecodeunitdetailid AND hztscuntdt_harmonizedtariffschedulecodeunitname = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUnitByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByName(String harmonizedTariffScheduleCodeUnitName, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUnitFactory.getInstance().getEntityFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUnitByNameQueries,
                harmonizedTariffScheduleCodeUnitName);
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

    private static final Map<EntityPermission, String> getDefaultHarmonizedTariffScheduleCodeUnitQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunits, harmonizedtariffschedulecodeunitdetails "
                + "WHERE hztscunt_activedetailid = hztscuntdt_harmonizedtariffschedulecodeunitdetailid AND hztscuntdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunits, harmonizedtariffschedulecodeunitdetails "
                + "WHERE hztscunt_activedetailid = hztscuntdt_harmonizedtariffschedulecodeunitdetailid AND hztscuntdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultHarmonizedTariffScheduleCodeUnitQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeUnit getDefaultHarmonizedTariffScheduleCodeUnit(EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUnitFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultHarmonizedTariffScheduleCodeUnitQueries);
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

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUnitsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunits, harmonizedtariffschedulecodeunitdetails "
                + "WHERE hztscunt_activedetailid = hztscuntdt_harmonizedtariffschedulecodeunitdetailid "
                + "ORDER BY hztscuntdt_sortorder, hztscuntdt_harmonizedtariffschedulecodeunitname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunits, harmonizedtariffschedulecodeunitdetails "
                + "WHERE hztscunt_activedetailid = hztscuntdt_harmonizedtariffschedulecodeunitdetailid "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUnitsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCodeUnit> getHarmonizedTariffScheduleCodeUnits(EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUnitFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUnitsQueries);
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

    public List<HarmonizedTariffScheduleCodeUnitTransfer> getHarmonizedTariffScheduleCodeUnitTransfers(UserVisit userVisit) {
        var harmonizedTariffScheduleCodeUnits = getHarmonizedTariffScheduleCodeUnits();
        List<HarmonizedTariffScheduleCodeUnitTransfer> harmonizedTariffScheduleCodeUnitTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUnits.size());

        harmonizedTariffScheduleCodeUnits.forEach((harmonizedTariffScheduleCodeUnit) ->
                harmonizedTariffScheduleCodeUnitTransfers.add(harmonizedTariffScheduleCodeUnitTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUnit))
        );

        return harmonizedTariffScheduleCodeUnitTransfers;
    }

    private void updateHarmonizedTariffScheduleCodeUnitFromValue(HarmonizedTariffScheduleCodeUnitDetailValue harmonizedTariffScheduleCodeUnitDetailValue, boolean checkDefault, BasePK updatedBy) {
        var harmonizedTariffScheduleCodeUnit = HarmonizedTariffScheduleCodeUnitFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, harmonizedTariffScheduleCodeUnitDetailValue.getHarmonizedTariffScheduleCodeUnitPK());
        var harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getActiveDetailForUpdate();

        harmonizedTariffScheduleCodeUnitDetail.setThruTime(session.START_TIME_LONG);
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

        harmonizedTariffScheduleCodeUnitDetail = HarmonizedTariffScheduleCodeUnitDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUnitPK, harmonizedTariffScheduleCodeUnitName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

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
        harmonizedTariffScheduleCodeUnitDetail.setThruTime(session.START_TIME_LONG);
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

    public HarmonizedTariffScheduleCodeUnitDescription createHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit,
            Language language, String description, BasePK createdBy) {
        var harmonizedTariffScheduleCodeUnitDescription = HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUnit,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeUnitDescription;
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUnitDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunitdescriptions "
                + "WHERE hztscuntd_hztscunt_harmonizedtariffschedulecodeunitid = ? AND hztscuntd_lang_languageid = ? AND hztscuntd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunitdescriptions "
                + "WHERE hztscuntd_hztscunt_harmonizedtariffschedulecodeunitid = ? AND hztscuntd_lang_languageid = ? AND hztscuntd_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUnitDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeUnitDescription getHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, Language language, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUnitDescriptionQueries,
                harmonizedTariffScheduleCodeUnit, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnitQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunitdescriptions, languages "
                + "WHERE hztscuntd_hztscunt_harmonizedtariffschedulecodeunitid = ? AND hztscuntd_thrutime = ? AND hztscuntd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeunitdescriptions "
                + "WHERE hztscuntd_hztscunt_harmonizedtariffschedulecodeunitid = ? AND hztscuntd_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCodeUnitDescription> getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnitQueries,
                harmonizedTariffScheduleCodeUnit, Session.MAX_TIME);
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
            var harmonizedTariffScheduleCodeUnitDescription = HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeUnitDescriptionValue.getPrimaryKey());

            harmonizedTariffScheduleCodeUnitDescription.setThruTime(session.START_TIME_LONG);
            harmonizedTariffScheduleCodeUnitDescription.store();

            var harmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeUnitDescription.getHarmonizedTariffScheduleCodeUnit();
            var language = harmonizedTariffScheduleCodeUnitDescription.getLanguage();
            var description = harmonizedTariffScheduleCodeUnitDescriptionValue.getDescription();

            harmonizedTariffScheduleCodeUnitDescription = HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUnit, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUnitDescription.setThruTime(session.START_TIME_LONG);

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

    public HarmonizedTariffScheduleCodeUse createHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK createdBy) {
        var harmonizedTariffScheduleCodeUse = HarmonizedTariffScheduleCodeUseFactory.getInstance().create(harmonizedTariffScheduleCode,
                harmonizedTariffScheduleCodeUseType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.MODIFY, harmonizedTariffScheduleCodeUse.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return harmonizedTariffScheduleCodeUse;
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeuses "
                + "WHERE hztscu_hztsc_harmonizedtariffschedulecodeid = ? AND hztscu_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscu_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeuses "
                + "WHERE hztscu_hztsc_harmonizedtariffschedulecodeid = ? AND hztscu_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscu_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private HarmonizedTariffScheduleCodeUse getHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseFactory.getInstance().getEntityFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUseQueries,
                harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeUseType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeuses, harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE hztscu_hztsc_harmonizedtariffschedulecodeid = ? AND hztscu_thrutime = ? "
                + "AND hztscu_hztscutyp_harmonizedtariffschedulecodeusetypeid = hztscutyp_harmonizedtariffschedulecodeusetypeid AND hztscutyp_lastdetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid "
                + "ORDER BY hztscutypdt_sortorder, hztscutypdt_harmonizedtariffschedulecodeusetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeuses "
                + "WHERE hztscu_hztsc_harmonizedtariffschedulecodeid = ? AND hztscu_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeQueries,
                harmonizedTariffScheduleCode, Session.MAX_TIME);
    }

    public List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_ONLY);
    }

    public List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeuses, harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztscu_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscu_thrutime = ? "
                + "AND hztscu_hztsc_harmonizedtariffschedulecodeid = hztsc_harmonizedtariffschedulecodeid AND hztsc_lastdetailid = hztscdt_harmonizedtariffschedulecodedetailid "
                + "ORDER BY hztscdt_sortorder, hztscdt_harmonizedtariffschedulecodename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM harmonizedtariffschedulecodeuses "
                + "WHERE hztscu_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? AND hztscu_thrutime = ? "
                + "FOR UPDATE");
        getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<HarmonizedTariffScheduleCodeUse> getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType,
            EntityPermission entityPermission) {
        return HarmonizedTariffScheduleCodeUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseTypeQueries,
                harmonizedTariffScheduleCodeUseType, Session.MAX_TIME);
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

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, List<HarmonizedTariffScheduleCodeUse> harmonizedTariffScheduleCodeUses) {
        List<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUseTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUses.size());

        harmonizedTariffScheduleCodeUses.forEach((harmonizedTariffScheduleCodeUse) -> {
            harmonizedTariffScheduleCodeUseTransfers.add(harmonizedTariffScheduleCodeUseTransferCache.getTransfer(userVisit, harmonizedTariffScheduleCodeUse));
        });

        return harmonizedTariffScheduleCodeUseTransfers;
    }

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(userVisit, getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode));
    }

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCodeUseType(UserVisit userVisit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(userVisit, getHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType));
    }

    public void deleteHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUse.setThruTime(session.START_TIME_LONG);

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

    public ItemHarmonizedTariffScheduleCode createItemHarmonizedTariffScheduleCode(Item item, GeoCode countryGeoCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            BasePK createdBy) {
        var itemHarmonizedTariffScheduleCode = ItemHarmonizedTariffScheduleCodeFactory.getInstance().create();
        var itemHarmonizedTariffScheduleCodeDetail = ItemHarmonizedTariffScheduleCodeDetailFactory.getInstance().create(session,
                itemHarmonizedTariffScheduleCode, item, countryGeoCode, harmonizedTariffScheduleCodeUseType, harmonizedTariffScheduleCode, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        itemHarmonizedTariffScheduleCode = ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemHarmonizedTariffScheduleCode.getPrimaryKey());
        itemHarmonizedTariffScheduleCode.setActiveDetail(itemHarmonizedTariffScheduleCodeDetail);
        itemHarmonizedTariffScheduleCode.setLastDetail(itemHarmonizedTariffScheduleCodeDetail);
        itemHarmonizedTariffScheduleCode.store();

        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemHarmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemHarmonizedTariffScheduleCode;
    }

    private static final Map<EntityPermission, String> getItemHarmonizedTariffScheduleCodesByItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails, geocodes, geocodedetails, harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_itm_itemid = ? "
                + "AND itmhztscdt_countrygeocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid "
                + "AND itmhztscdt_hztscutyp_harmonizedtariffschedulecodeusetypeid = hztscutyp_harmonizedtariffschedulecodeusetypeid AND hztscutyp_lastdetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid "
                + "ORDER BY geodt_sortorder, geodt_geocodename, hztscutypdt_sortorder, hztscutypdt_harmonizedtariffschedulecodeusetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_itm_itemid = ? "
                + "FOR UPDATE");
        getItemHarmonizedTariffScheduleCodesByItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByItem(Item item, EntityPermission entityPermission) {
        return ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemHarmonizedTariffScheduleCodesByItemQueries,
                item);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByItem(Item item) {
        return getItemHarmonizedTariffScheduleCodesByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByItemForUpdate(Item item) {
        return getItemHarmonizedTariffScheduleCodesByItem(item, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemHarmonizedTariffScheduleCodesByCountryGeoCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails, items, itemdetails, harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_countrygeocodeid = ? "
                + "AND itmhztscdt_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid "
                + "AND itmhztscdt_hztscutyp_harmonizedtariffschedulecodeusetypeid = hztscutyp_harmonizedtariffschedulecodeusetypeid AND hztscutyp_lastdetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid "
                + "ORDER BY itmdt_itemname, hztscutypdt_sortorder, hztscutypdt_harmonizedtariffschedulecodeusetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_countrygeocodeid = ? "
                + "FOR UPDATE");
        getItemHarmonizedTariffScheduleCodesByCountryGeoCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        return ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemHarmonizedTariffScheduleCodesByCountryGeoCodeQueries,
                countryGeoCode);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode) {
        return getItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails, items, itemdetails, geocodes, geocodedetails "
                + "AND itmhztscdt_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid "
                + "AND itmhztscdt_countrygeocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? "
                + "ORDER BY itmdt_itemname, geodt_sortorder, geodt_geocodename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? "
                + "FOR UPDATE");
        getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        return ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseQueries,
                harmonizedTariffScheduleCodeUseType);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseForUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails, items, itemdetails, geocodes, geocodedetails, harmonizedtariffschedulecodeusetypes, harmonizedtariffschedulecodeusetypedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_hztsc_harmonizedtariffschedulecodeid = ? "
                + "AND itmhztscdt_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid "
                + "AND itmhztscdt_countrygeocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid "
                + "AND itmhztscdt_hztscutyp_harmonizedtariffschedulecodeusetypeid = hztscutyp_harmonizedtariffschedulecodeusetypeid AND hztscutyp_lastdetailid = hztscutypdt_harmonizedtariffschedulecodeusetypedetailid "
                + "ORDER BY itmdt_itemname, geodt_sortorder, geodt_geocodename, hztscutypdt_sortorder, hztscutypdt_harmonizedtariffschedulecodeusetypename ");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid AND itmhztscdt_hztsc_harmonizedtariffschedulecodeid = ? "
                + "FOR UPDATE");
        getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, EntityPermission entityPermission) {
        return ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeQueries,
                harmonizedTariffScheduleCode);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_ONLY);
    }

    public List<ItemHarmonizedTariffScheduleCode> getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeForUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemHarmonizedTariffScheduleCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid "
                + "AND itmhztscdt_itm_itemid = ? AND itmhztscdt_countrygeocodeid = ? AND itmhztscdt_hztscutyp_harmonizedtariffschedulecodeusetypeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemharmonizedtariffschedulecodes, itemharmonizedtariffschedulecodedetails "
                + "WHERE itmhztsc_activedetailid = itmhztscdt_itemharmonizedtariffschedulecodedetailid "
                + "AND itmhztscdt_itm_itemid = ? AND itmhztscdt_countrygeocodeid = ? AND itmhztscdt_hztscutyp_harmonizedtariffschedulecodeusetypeid = ? "
                + "FOR UPDATE");
        getItemHarmonizedTariffScheduleCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemHarmonizedTariffScheduleCode getItemHarmonizedTariffScheduleCode(Item item, GeoCode countryGeoCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, EntityPermission entityPermission) {
        return ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromQuery(entityPermission, getItemHarmonizedTariffScheduleCodeQueries,
                item, countryGeoCode, harmonizedTariffScheduleCodeUseType);
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
            var itemHarmonizedTariffScheduleCode = ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemHarmonizedTariffScheduleCodeDetailValue.getItemHarmonizedTariffScheduleCodePK());
            var itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getActiveDetailForUpdate();

            itemHarmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
            itemHarmonizedTariffScheduleCodeDetail.store();

            var itemHarmonizedTariffScheduleCodePK = itemHarmonizedTariffScheduleCodeDetail.getItemHarmonizedTariffScheduleCodePK();
            var itemPK = itemHarmonizedTariffScheduleCodeDetail.getItemPK();
            var countryGeoCodePK = itemHarmonizedTariffScheduleCodeDetail.getCountryGeoCodePK();
            var harmonizedTariffScheduleCodeUseTypePK = itemHarmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeUseTypePK();
            var harmonizedTariffScheduleCodePK = itemHarmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodePK();

            itemHarmonizedTariffScheduleCodeDetail = ItemHarmonizedTariffScheduleCodeDetailFactory.getInstance().create(itemHarmonizedTariffScheduleCodePK,
                    itemPK, countryGeoCodePK, harmonizedTariffScheduleCodeUseTypePK, harmonizedTariffScheduleCodePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            itemHarmonizedTariffScheduleCode.setActiveDetail(itemHarmonizedTariffScheduleCodeDetail);
            itemHarmonizedTariffScheduleCode.setLastDetail(itemHarmonizedTariffScheduleCodeDetail);

            sendEvent(itemPK, EventTypes.MODIFY, itemHarmonizedTariffScheduleCodePK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemHarmonizedTariffScheduleCode(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode, BasePK deletedBy) {
        var itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getLastDetailForUpdate();
        itemHarmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
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

    public List<ItemResultTransfer> getItemResultTransfers(UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var itemResultTransfers = new ArrayList<ItemResultTransfer>(toIntExact(searchControl.countSearchResults(userVisitSearch)));;
        var includeItem = false;

        // ItemTransfer objects are not included unless specifically requested;
        var options = session.getOptions();
        if(options != null) {
            includeItem = options.contains(SearchOptions.ItemResultIncludeItem);
        }

        if(userVisitSearch.getSearch().getCachedSearch() != null) {
            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);
        }

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            var itemControl = Session.getModelController(ItemControl.class);

            while(rs.next()) {
                var item = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ItemPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                itemResultTransfers.add(new ItemResultTransfer(item.getLastDetail().getItemName(),
                        includeItem ? itemControl.getItemTransfer(userVisitSearch.getUserVisit(), item) : null));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemResultTransfers;
    }

    public List<ItemObject> getItemObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var itemObjects = new ArrayList<ItemObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            var itemControl = Session.getModelController(ItemControl.class);

            while(rs.next()) {
                var item = itemControl.getItemByPK(new ItemPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                itemObjects.add(new ItemObject(item));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemObjects;
    }

}
