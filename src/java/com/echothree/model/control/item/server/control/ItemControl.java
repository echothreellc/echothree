// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.item.common.transfer.ItemWeightTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.RelatedItemTypeTransfer;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeUnitTransferCache;
import com.echothree.model.control.item.server.transfer.HarmonizedTariffScheduleCodeUseTypeTransferCache;
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
import com.echothree.model.control.item.server.transfer.ItemTransferCaches;
import com.echothree.model.control.item.server.transfer.ItemTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitCustomerTypeLimitTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitLimitTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitOfMeasureTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUnitPriceLimitTransferCache;
import com.echothree.model.control.item.server.transfer.ItemUseTypeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemVolumeTransferCache;
import com.echothree.model.control.item.server.transfer.ItemWeightTransferCache;
import com.echothree.model.control.item.server.transfer.RelatedItemTransferCache;
import com.echothree.model.control.item.server.transfer.RelatedItemTypeDescriptionTransferCache;
import com.echothree.model.control.item.server.transfer.RelatedItemTypeTransferCache;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.control.search.server.graphql.ItemResultObject;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.ItemAccountingCategoryPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationPolicyPK;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.common.pk.MimeTypeUsageTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.geo.common.pk.GeoCodePK;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.inventory.common.pk.InventoryConditionPK;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodePK;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodeUnitPK;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodeUseTypePK;
import com.echothree.model.data.item.common.pk.ItemAliasChecksumTypePK;
import com.echothree.model.data.item.common.pk.ItemAliasPK;
import com.echothree.model.data.item.common.pk.ItemAliasTypePK;
import com.echothree.model.data.item.common.pk.ItemCategoryPK;
import com.echothree.model.data.item.common.pk.ItemCountryOfOriginPK;
import com.echothree.model.data.item.common.pk.ItemDeliveryTypePK;
import com.echothree.model.data.item.common.pk.ItemDescriptionPK;
import com.echothree.model.data.item.common.pk.ItemDescriptionTypePK;
import com.echothree.model.data.item.common.pk.ItemDescriptionTypeUseTypePK;
import com.echothree.model.data.item.common.pk.ItemHarmonizedTariffScheduleCodePK;
import com.echothree.model.data.item.common.pk.ItemImageTypePK;
import com.echothree.model.data.item.common.pk.ItemInventoryTypePK;
import com.echothree.model.data.item.common.pk.ItemKitOptionPK;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.common.pk.ItemPackCheckRequirementPK;
import com.echothree.model.data.item.common.pk.ItemPricePK;
import com.echothree.model.data.item.common.pk.ItemPriceTypePK;
import com.echothree.model.data.item.common.pk.ItemShippingTimePK;
import com.echothree.model.data.item.common.pk.ItemTypePK;
import com.echothree.model.data.item.common.pk.ItemUseTypePK;
import com.echothree.model.data.item.common.pk.RelatedItemPK;
import com.echothree.model.data.item.common.pk.RelatedItemTypePK;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeTranslation;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnitDescription;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnitDetail;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUse;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseTypeDescription;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseTypeDetail;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumTypeDescription;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDescription;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDetail;
import com.echothree.model.data.item.server.entity.ItemBlobDescription;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemCategoryDescription;
import com.echothree.model.data.item.server.entity.ItemCategoryDetail;
import com.echothree.model.data.item.server.entity.ItemClobDescription;
import com.echothree.model.data.item.server.entity.ItemCountryOfOrigin;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemDeliveryTypeDescription;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionDetail;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDetail;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUse;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDetail;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.item.server.entity.ItemImageDescription;
import com.echothree.model.data.item.server.entity.ItemImageDescriptionType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.item.server.entity.ItemImageTypeDescription;
import com.echothree.model.data.item.server.entity.ItemImageTypeDetail;
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
import com.echothree.model.data.item.server.entity.ItemWeight;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.item.server.entity.RelatedItemDetail;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.entity.RelatedItemTypeDescription;
import com.echothree.model.data.item.server.entity.RelatedItemTypeDetail;
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
import com.echothree.model.data.item.server.factory.ItemWeightFactory;
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
import com.echothree.model.data.item.server.value.ItemAliasTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemAliasTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemAliasValue;
import com.echothree.model.data.item.server.value.ItemBlobDescriptionValue;
import com.echothree.model.data.item.server.value.ItemCategoryDescriptionValue;
import com.echothree.model.data.item.server.value.ItemCategoryDetailValue;
import com.echothree.model.data.item.server.value.ItemClobDescriptionValue;
import com.echothree.model.data.item.server.value.ItemCountryOfOriginValue;
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
import com.echothree.model.data.item.server.value.ItemKitMemberValue;
import com.echothree.model.data.item.server.value.ItemKitOptionValue;
import com.echothree.model.data.item.server.value.ItemPackCheckRequirementValue;
import com.echothree.model.data.item.server.value.ItemShippingTimeValue;
import com.echothree.model.data.item.server.value.ItemStringDescriptionValue;
import com.echothree.model.data.item.server.value.ItemUnitCustomerTypeLimitValue;
import com.echothree.model.data.item.server.value.ItemUnitLimitValue;
import com.echothree.model.data.item.server.value.ItemUnitOfMeasureTypeValue;
import com.echothree.model.data.item.server.value.ItemUnitPriceLimitValue;
import com.echothree.model.data.item.server.value.ItemVariablePriceValue;
import com.echothree.model.data.item.server.value.ItemVolumeValue;
import com.echothree.model.data.item.server.value.ItemWeightValue;
import com.echothree.model.data.item.server.value.RelatedItemDetailValue;
import com.echothree.model.data.item.server.value.RelatedItemTypeDescriptionValue;
import com.echothree.model.data.item.server.value.RelatedItemTypeDetailValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.style.common.pk.StylePathPK;
import com.echothree.model.data.style.server.entity.StylePath;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindPK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.common.pk.ItemPurchasingCategoryPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ItemControl
        extends BaseModelControl {
    
    /** Creates a new instance of ItemControl */
    public ItemControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Item Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ItemTransferCaches itemTransferCaches;
    
    public ItemTransferCaches getItemTransferCaches(UserVisit userVisit) {
        if(itemTransferCaches == null) {
            itemTransferCaches = new ItemTransferCaches(userVisit, this);
        }
        
        return itemTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Types
    // --------------------------------------------------------------------------------
    
    public ItemType createItemType(String itemTypeName, Boolean isDefault, Integer sortOrder) {
        return ItemTypeFactory.getInstance().create(itemTypeName, isDefault, sortOrder);
    }
    
    public ItemType getItemTypeByName(String itemTypeName) {
        ItemType itemType;
        
        try {
            PreparedStatement ps = ItemTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemtypes " +
                    "WHERE ityp_itemtypename = ?");
            
            ps.setString(1, itemTypeName);
            
            itemType = ItemTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemType;
    }
    
    public List<ItemType> getItemTypes() {
        PreparedStatement ps = ItemTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemtypes " +
                "ORDER BY ityp_sortorder, ityp_itemtypename");
        
        return ItemTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ItemTypeChoicesBean getItemTypeChoices(String defaultItemTypeChoice, Language language, boolean allowNullChoice) {
        List<ItemType> itemTypes = getItemTypes();
        int size = itemTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemType : itemTypes) {
            String label = getBestItemTypeDescription(itemType, language);
            String value = itemType.getItemTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemTypeChoice != null && defaultItemTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemTypeTransfer getItemTypeTransfer(UserVisit userVisit, ItemType itemType) {
        return getItemTransferCaches(userVisit).getItemTypeTransferCache().getTransfer(itemType);
    }
    
    public List<ItemTypeTransfer> getItemTypeTransfers(UserVisit userVisit) {
        List<ItemType> itemTypes = getItemTypes();
        List<ItemTypeTransfer> itemTypeTransfers = new ArrayList<>(itemTypes.size());
        ItemTypeTransferCache itemTypeTransferCache = getItemTransferCaches(userVisit).getItemTypeTransferCache();
        
        itemTypes.forEach((itemType) ->
                itemTypeTransfers.add(itemTypeTransferCache.getTransfer(itemType))
        );
        
        return itemTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemTypeDescription createItemTypeDescription(ItemType itemType, Language language, String description) {
        return ItemTypeDescriptionFactory.getInstance().create(itemType, language, description);
    }
    
    public ItemTypeDescription getItemTypeDescription(ItemType itemType, Language language) {
        ItemTypeDescription itemTypeDescription;
        
        try {
            PreparedStatement ps = ItemTypeDescriptionFactory.getInstance().prepareStatement(
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
        ItemTypeDescription itemTypeDescription = getItemTypeDescription(itemType, language);
        
        if(itemTypeDescription == null && !language.getIsDefault()) {
            itemTypeDescription = getItemTypeDescription(itemType, getPartyControl().getDefaultLanguage());
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
    
    public ItemDeliveryType createItemDeliveryType(String itemDeliveryTypeName, Boolean isDefault, Integer sortOrder) {
        return ItemDeliveryTypeFactory.getInstance().create(itemDeliveryTypeName, isDefault, sortOrder);
    }
    
    public ItemDeliveryType getItemDeliveryTypeByName(String itemDeliveryTypeName) {
        ItemDeliveryType itemDeliveryType;
        
        try {
            PreparedStatement ps = ItemDeliveryTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemdeliverytypes " +
                    "WHERE idlvrtyp_itemdeliverytypename = ?");
            
            ps.setString(1, itemDeliveryTypeName);
            
            itemDeliveryType = ItemDeliveryTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemDeliveryType;
    }
    
    public List<ItemDeliveryType> getItemDeliveryTypes() {
        PreparedStatement ps = ItemDeliveryTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemdeliverytypes " +
                "ORDER BY idlvrtyp_sortorder, idlvrtyp_itemdeliverytypename");
        
        return ItemDeliveryTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ItemDeliveryTypeChoicesBean getItemDeliveryTypeChoices(String defaultItemDeliveryTypeChoice, Language language, boolean allowNullChoice) {
        List<ItemDeliveryType> itemDeliveryTypes = getItemDeliveryTypes();
        int size = itemDeliveryTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemDeliveryTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemDeliveryType : itemDeliveryTypes) {
            String label = getBestItemDeliveryTypeDescription(itemDeliveryType, language);
            String value = itemDeliveryType.getItemDeliveryTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemDeliveryTypeChoice != null && defaultItemDeliveryTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemDeliveryType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemDeliveryTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemDeliveryTypeTransfer getItemDeliveryTypeTransfer(UserVisit userVisit, ItemDeliveryType itemDeliveryType) {
        return getItemTransferCaches(userVisit).getItemDeliveryTypeTransferCache().getTransfer(itemDeliveryType);
    }
    
    public List<ItemDeliveryTypeTransfer> getItemDeliveryTypeTransfers(UserVisit userVisit) {
        List<ItemDeliveryType> itemDeliveryTypes = getItemDeliveryTypes();
        List<ItemDeliveryTypeTransfer> itemDeliveryTypeTransfers = new ArrayList<>(itemDeliveryTypes.size());
        ItemDeliveryTypeTransferCache itemDeliveryTypeTransferCache = getItemTransferCaches(userVisit).getItemDeliveryTypeTransferCache();
        
        itemDeliveryTypes.forEach((itemDeliveryType) ->
                itemDeliveryTypeTransfers.add(itemDeliveryTypeTransferCache.getTransfer(itemDeliveryType))
        );
        
        return itemDeliveryTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Delivery Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemDeliveryTypeDescription createItemDeliveryTypeDescription(ItemDeliveryType itemDeliveryType, Language language, String description) {
        return ItemDeliveryTypeDescriptionFactory.getInstance().create(itemDeliveryType, language, description);
    }
    
    public ItemDeliveryTypeDescription getItemDeliveryTypeDescription(ItemDeliveryType itemDeliveryType, Language language) {
        ItemDeliveryTypeDescription itemDeliveryTypeDescription;
        
        try {
            PreparedStatement ps = ItemDeliveryTypeDescriptionFactory.getInstance().prepareStatement(
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
        ItemDeliveryTypeDescription itemDeliveryTypeDescription = getItemDeliveryTypeDescription(itemDeliveryType, language);
        
        if(itemDeliveryTypeDescription == null && !language.getIsDefault()) {
            itemDeliveryTypeDescription = getItemDeliveryTypeDescription(itemDeliveryType, getPartyControl().getDefaultLanguage());
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
    
    public ItemInventoryType createItemInventoryType(String itemInventoryTypeName, Boolean isDefault, Integer sortOrder) {
        return ItemInventoryTypeFactory.getInstance().create(itemInventoryTypeName, isDefault, sortOrder);
    }
    
    public ItemInventoryType getItemInventoryTypeByName(String itemInventoryTypeName) {
        ItemInventoryType itemInventoryType;
        
        try {
            PreparedStatement ps = ItemInventoryTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM iteminventorytypes " +
                    "WHERE iinvtyp_iteminventorytypename = ?");
            
            ps.setString(1, itemInventoryTypeName);
            
            itemInventoryType = ItemInventoryTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemInventoryType;
    }
    
    public List<ItemInventoryType> getItemInventoryTypes() {
        PreparedStatement ps = ItemInventoryTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM iteminventorytypes " +
                "ORDER BY iinvtyp_sortorder, iinvtyp_iteminventorytypename");
        
        return ItemInventoryTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ItemInventoryTypeChoicesBean getItemInventoryTypeChoices(String defaultItemInventoryTypeChoice, Language language, boolean allowNullChoice) {
        List<ItemInventoryType> itemInventoryTypes = getItemInventoryTypes();
        int size = itemInventoryTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemInventoryTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemInventoryType : itemInventoryTypes) {
            String label = getBestItemInventoryTypeDescription(itemInventoryType, language);
            String value = itemInventoryType.getItemInventoryTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemInventoryTypeChoice != null && defaultItemInventoryTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemInventoryType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemInventoryTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemInventoryTypeTransfer getItemInventoryTypeTransfer(UserVisit userVisit, ItemInventoryType itemInventoryType) {
        return getItemTransferCaches(userVisit).getItemInventoryTypeTransferCache().getTransfer(itemInventoryType);
    }
    
    public List<ItemInventoryTypeTransfer> getItemInventoryTypeTransfers(UserVisit userVisit) {
        List<ItemInventoryType> itemInventoryTypes = getItemInventoryTypes();
        List<ItemInventoryTypeTransfer> itemInventoryTypeTransfers = new ArrayList<>(itemInventoryTypes.size());
        ItemInventoryTypeTransferCache itemInventoryTypeTransferCache = getItemTransferCaches(userVisit).getItemInventoryTypeTransferCache();
        
        itemInventoryTypes.forEach((itemInventoryType) ->
                itemInventoryTypeTransfers.add(itemInventoryTypeTransferCache.getTransfer(itemInventoryType))
        );
        
        return itemInventoryTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Inventory Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemInventoryTypeDescription createItemInventoryTypeDescription(ItemInventoryType itemInventoryType, Language language, String description) {
        return ItemInventoryTypeDescriptionFactory.getInstance().create(itemInventoryType, language, description);
    }
    
    public ItemInventoryTypeDescription getItemInventoryTypeDescription(ItemInventoryType itemInventoryType, Language language) {
        ItemInventoryTypeDescription itemInventoryTypeDescription;
        
        try {
            PreparedStatement ps = ItemInventoryTypeDescriptionFactory.getInstance().prepareStatement(
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
        ItemInventoryTypeDescription itemInventoryTypeDescription = getItemInventoryTypeDescription(itemInventoryType, language);
        
        if(itemInventoryTypeDescription == null && !language.getIsDefault()) {
            itemInventoryTypeDescription = getItemInventoryTypeDescription(itemInventoryType, getPartyControl().getDefaultLanguage());
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
    
    public ItemUseType createItemUseType(String itemUseTypeName, Boolean isDefault, Integer sortOrder) {
        return ItemUseTypeFactory.getInstance().create(itemUseTypeName, isDefault, sortOrder);
    }
    
    public ItemUseType getItemUseTypeByName(String itemUseTypeName) {
        ItemUseType itemUseType;
        
        try {
            PreparedStatement ps = ItemUseTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemusetypes " +
                    "WHERE iutyp_itemusetypename = ?");
            
            ps.setString(1, itemUseTypeName);
            
            itemUseType = ItemUseTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemUseType;
    }
    
    public List<ItemUseType> getItemUseTypes() {
        PreparedStatement ps = ItemUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemusetypes " +
                "ORDER BY iutyp_sortorder, iutyp_itemusetypename");
        
        return ItemUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ItemUseTypeChoicesBean getItemUseTypeChoices(String defaultItemUseTypeChoice, Language language, boolean allowNullChoice) {
        List<ItemUseType> itemUseTypes = getItemUseTypes();
        int size = itemUseTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemUseType : itemUseTypes) {
            String label = getBestItemUseTypeDescription(itemUseType, language);
            String value = itemUseType.getItemUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemUseTypeChoice != null && defaultItemUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemUseType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemUseTypeTransfer getItemUseTypeTransfer(UserVisit userVisit, ItemUseType itemUseType) {
        return getItemTransferCaches(userVisit).getItemUseTypeTransferCache().getTransfer(itemUseType);
    }
    
    public List<ItemUseTypeTransfer> getItemUseTypeTransfers(UserVisit userVisit) {
        List<ItemUseType> itemUseTypes = getItemUseTypes();
        List<ItemUseTypeTransfer> itemUseTypeTransfers = new ArrayList<>(itemUseTypes.size());
        ItemUseTypeTransferCache itemUseTypeTransferCache = getItemTransferCaches(userVisit).getItemUseTypeTransferCache();
        
        itemUseTypes.forEach((itemUseType) ->
                itemUseTypeTransfers.add(itemUseTypeTransferCache.getTransfer(itemUseType))
        );
        
        return itemUseTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemUseTypeDescription createItemUseTypeDescription(ItemUseType itemUseType, Language language, String description) {
        return ItemUseTypeDescriptionFactory.getInstance().create(itemUseType, language, description);
    }
    
    public ItemUseTypeDescription getItemUseTypeDescription(ItemUseType itemUseType, Language language) {
        ItemUseTypeDescription itemUseTypeDescription;
        
        try {
            PreparedStatement ps = ItemUseTypeDescriptionFactory.getInstance().prepareStatement(
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
        ItemUseTypeDescription itemUseTypeDescription = getItemUseTypeDescription(itemUseType, language);
        
        if(itemUseTypeDescription == null && !language.getIsDefault()) {
            itemUseTypeDescription = getItemUseTypeDescription(itemUseType, getPartyControl().getDefaultLanguage());
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
        ItemCategory defaultItemCategory = getDefaultItemCategory();
        boolean defaultFound = defaultItemCategory != null;
        
        if(defaultFound && isDefault) {
            ItemCategoryDetailValue defaultItemCategoryDetailValue = getDefaultItemCategoryDetailValueForUpdate();
            
            defaultItemCategoryDetailValue.setIsDefault(Boolean.FALSE);
            updateItemCategoryFromValue(defaultItemCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ItemCategory itemCategory = ItemCategoryFactory.getInstance().create();
        ItemCategoryDetail itemCategoryDetail = ItemCategoryDetailFactory.getInstance().create(session,
                itemCategory, itemCategoryName, parentItemCategory, itemSequence, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemCategory = ItemCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemCategory.getPrimaryKey());
        itemCategory.setActiveDetail(itemCategoryDetail);
        itemCategory.setLastDetail(itemCategoryDetail);
        itemCategory.store();
        
        sendEventUsingNames(itemCategory.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return itemCategory;
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
            
            PreparedStatement ps = ItemCategoryFactory.getInstance().prepareStatement(query);
            
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
        
        PreparedStatement ps = ItemCategoryFactory.getInstance().prepareStatement(query);
        
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
        
        PreparedStatement ps = ItemCategoryFactory.getInstance().prepareStatement(query);
        
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
        List<ItemCategory> itemCategories = null;

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

            PreparedStatement ps = ItemCategoryFactory.getInstance().prepareStatement(query);

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
        return getItemTransferCaches(userVisit).getItemCategoryTransferCache().getTransfer(itemCategory);
    }

    public List<ItemCategoryTransfer> getItemCategoryTransfers(UserVisit userVisit, Collection<ItemCategory> itemCategories) {
        List<ItemCategoryTransfer> itemCategoryTransfers = new ArrayList<>(itemCategories.size());
        ItemCategoryTransferCache itemCategoryTransferCache = getItemTransferCaches(userVisit).getItemCategoryTransferCache();

        itemCategories.forEach((itemCategory) ->
                itemCategoryTransfers.add(itemCategoryTransferCache.getTransfer(itemCategory))
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

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ItemCategory */
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

    public ItemCategoryChoicesBean getItemCategoryChoices(String defaultItemCategoryChoice, Language language,
            boolean allowNullChoice) {
        List<ItemCategory> itemCategories = getItemCategories();
        int size = itemCategories.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemCategory : itemCategories) {
            ItemCategoryDetail itemCategoryDetail = itemCategory.getLastDetail();
            
            String label = allowNullChoice ? new StringBuilder(getBestItemCategoryDescription(itemCategory, language)).append(itemCategoryDetail.getIsDefault() ? " *" : "").toString(): getBestItemCategoryDescription(itemCategory, language);
            String value = itemCategoryDetail.getItemCategoryName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemCategoryChoice != null && defaultItemCategoryChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemCategoryDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentItemCategorySafe(ItemCategory itemCategory, ItemCategory parentItemCategory) {
        boolean safe = true;
        
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
            ItemCategory itemCategory = ItemCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemCategoryDetailValue.getItemCategoryPK());
            ItemCategoryDetail itemCategoryDetail = itemCategory.getActiveDetailForUpdate();
            
            itemCategoryDetail.setThruTime(session.START_TIME_LONG);
            itemCategoryDetail.store();
            
            ItemCategoryPK itemCategoryPK = itemCategoryDetail.getItemCategoryPK();
            String itemCategoryName = itemCategoryDetailValue.getItemCategoryName();
            ItemCategoryPK parentItemCategoryPK = itemCategoryDetailValue.getParentItemCategoryPK();
            SequencePK itemSequencePK = itemCategoryDetailValue.getItemSequencePK();
            Boolean isDefault = itemCategoryDetailValue.getIsDefault();
            Integer sortOrder = itemCategoryDetailValue.getSortOrder();
            
            if(checkDefault) {
                ItemCategory defaultItemCategory = getDefaultItemCategory();
                boolean defaultFound = defaultItemCategory != null && !defaultItemCategory.equals(itemCategory);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ItemCategoryDetailValue defaultItemCategoryDetailValue = getDefaultItemCategoryDetailValueForUpdate();
                    
                    defaultItemCategoryDetailValue.setIsDefault(Boolean.FALSE);
                    updateItemCategoryFromValue(defaultItemCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            itemCategoryDetail = ItemCategoryDetailFactory.getInstance().create(itemCategoryPK,
                    itemCategoryName, parentItemCategoryPK, itemSequencePK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            itemCategory.setActiveDetail(itemCategoryDetail);
            itemCategory.setLastDetail(itemCategoryDetail);
            
            sendEventUsingNames(itemCategoryPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateItemCategoryFromValue(ItemCategoryDetailValue itemCategoryDetailValue, BasePK updatedBy) {
        updateItemCategoryFromValue(itemCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteItemCategory(ItemCategory itemCategory, boolean checkDefault, BasePK deletedBy) {
        deleteItemCategoriesByParentItemCategory(itemCategory, deletedBy);
        deleteItemCategoryDescriptionsByItemCategory(itemCategory, deletedBy);
        
        ItemCategoryDetail itemCategoryDetail = itemCategory.getLastDetailForUpdate();
        itemCategoryDetail.setThruTime(session.START_TIME_LONG);
        itemCategory.setActiveDetail(null);
        itemCategory.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            ItemCategory defaultItemCategory = getDefaultItemCategory();

            if(defaultItemCategory == null) {
                List<ItemCategory> itemCategories = getItemCategoriesForUpdate();

                if(!itemCategories.isEmpty()) {
                    Iterator<ItemCategory> iter = itemCategories.iterator();
                    if(iter.hasNext()) {
                        defaultItemCategory = iter.next();
                    }
                    ItemCategoryDetailValue itemCategoryDetailValue = Objects.requireNonNull(defaultItemCategory).getLastDetailForUpdate().getItemCategoryDetailValue().clone();

                    itemCategoryDetailValue.setIsDefault(Boolean.TRUE);
                    updateItemCategoryFromValue(itemCategoryDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(itemCategory.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
        ItemCategoryDescription itemCategoryDescription = ItemCategoryDescriptionFactory.getInstance().create(itemCategory, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemCategory.getPrimaryKey(), EventTypes.MODIFY.name(), itemCategoryDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
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
        ItemCategoryDescription itemCategoryDescription = getItemCategoryDescription(itemCategory, language);
        
        if(itemCategoryDescription == null && !language.getIsDefault()) {
            itemCategoryDescription = getItemCategoryDescription(itemCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(itemCategoryDescription == null) {
            description = itemCategory.getLastDetail().getItemCategoryName();
        } else {
            description = itemCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public ItemCategoryDescriptionTransfer getItemCategoryDescriptionTransfer(UserVisit userVisit, ItemCategoryDescription itemCategoryDescription) {
        return getItemTransferCaches(userVisit).getItemCategoryDescriptionTransferCache().getTransfer(itemCategoryDescription);
    }
    
    public List<ItemCategoryDescriptionTransfer> getItemCategoryDescriptionTransfersByItemCategory(UserVisit userVisit, ItemCategory itemCategory) {
        List<ItemCategoryDescription> itemCategoryDescriptions = getItemCategoryDescriptionsByItemCategory(itemCategory);
        List<ItemCategoryDescriptionTransfer> itemCategoryDescriptionTransfers = new ArrayList<>(itemCategoryDescriptions.size());
        ItemCategoryDescriptionTransferCache itemCategoryDescriptionTransferCache = getItemTransferCaches(userVisit).getItemCategoryDescriptionTransferCache();
        
        itemCategoryDescriptions.forEach((itemCategoryDescription) ->
                itemCategoryDescriptionTransfers.add(itemCategoryDescriptionTransferCache.getTransfer(itemCategoryDescription))
        );
        
        return itemCategoryDescriptionTransfers;
    }
    
    public void updateItemCategoryDescriptionFromValue(ItemCategoryDescriptionValue itemCategoryDescriptionValue, BasePK updatedBy) {
        if(itemCategoryDescriptionValue.hasBeenModified()) {
            ItemCategoryDescription itemCategoryDescription = ItemCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemCategoryDescriptionValue.getPrimaryKey());
            
            itemCategoryDescription.setThruTime(session.START_TIME_LONG);
            itemCategoryDescription.store();
            
            ItemCategory itemCategory = itemCategoryDescription.getItemCategory();
            Language language = itemCategoryDescription.getLanguage();
            String description = itemCategoryDescriptionValue.getDescription();
            
            itemCategoryDescription = ItemCategoryDescriptionFactory.getInstance().create(itemCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemCategory.getPrimaryKey(), EventTypes.MODIFY.name(), itemCategoryDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemCategoryDescription(ItemCategoryDescription itemCategoryDescription, BasePK deletedBy) {
        itemCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemCategoryDescription.getItemCategoryPK(), EventTypes.MODIFY.name(), itemCategoryDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteItemCategoryDescriptionsByItemCategory(ItemCategory itemCategory, BasePK deletedBy) {
        List<ItemCategoryDescription> itemCategoryDescriptions = getItemCategoryDescriptionsByItemCategoryForUpdate(itemCategory);
        
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
        Item item = ItemFactory.getInstance().create();
        ItemDetail itemDetail = ItemDetailFactory.getInstance().create(item, itemName, itemType, itemUseType, itemCategory, itemAccountingCategory,
                itemPurchasingCategory, companyParty, itemDeliveryType, itemInventoryType, inventorySerialized, serialNumberSequence, shippingChargeExempt,
                shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime, purchaseOrderStartTime, purchaseOrderEndTime, allowClubDiscounts,
                allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKind, itemPriceType, cancellationPolicy, returnPolicy, stylePath,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        item = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, item.getPrimaryKey());
        item.setActiveDetail(itemDetail);
        item.setLastDetail(itemDetail);
        item.store();
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
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

    private List<Item> getItems(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM items, itemdetails " +
                    "WHERE itm_activedetailid = itmdt_itemdetailid " +
                    "ORDER BY itmdt_itemname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM items, itemdetails " +
                    "WHERE itm_activedetailid = itmdt_itemdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = ItemFactory.getInstance().prepareStatement(query);
        
        return ItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    
    public List<Item> getItems() {
        return getItems(EntityPermission.READ_ONLY);
    }
    
    public List<Item> getItemsForUpdate() {
        return getItems(EntityPermission.READ_WRITE);
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
            
            PreparedStatement ps = ItemFactory.getInstance().prepareStatement(query);
            
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
        Item item = getItemByName(itemName);
        
        if(item == null) {
            ItemAlias itemAlias = getItemAliasByAlias(itemName);
            
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
        return getItemTransferCaches(userVisit).getItemTransferCache().getTransfer(item);
    }
    
    public ItemStatusChoicesBean getItemStatusChoices(String defaultItemStatusChoice, Language language, boolean allowNullChoice,
            Item item, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        ItemStatusChoicesBean itemStatusChoicesBean = new ItemStatusChoicesBean();
        
        if(item == null) {
            workflowControl.getWorkflowEntranceChoices(itemStatusChoicesBean, defaultItemStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(ItemStatusConstants.Workflow_ITEM_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(item.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(ItemStatusConstants.Workflow_ITEM_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(itemStatusChoicesBean, defaultItemStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return itemStatusChoicesBean;
    }
    
    public void setItemStatus(ExecutionErrorAccumulator eea, Item item, String itemStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(item);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(ItemStatusConstants.Workflow_ITEM_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = itemStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), itemStatusChoice);
        
        if(workflowDestination != null || itemStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownItemStatusChoice.name(), itemStatusChoice);
        }
    }
    
    public void updateItemFromValue(ItemDetailValue itemDetailValue, BasePK updatedBy) {
        if(itemDetailValue.hasBeenModified()) {
            Item item = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDetailValue.getItemPK());
            ItemDetail itemDetail = item.getActiveDetailForUpdate();
            
            itemDetail.setThruTime(session.START_TIME_LONG);
            itemDetail.store();
            
            ItemPK itemPK = itemDetail.getItemPK(); // Not updated
            String itemName = itemDetailValue.getItemName(); // Not updated
            ItemTypePK itemTypePK = itemDetail.getItemTypePK(); // Not updated
            ItemUseTypePK itemUseTypePK = itemDetail.getItemUseTypePK(); // Not updated
            ItemCategoryPK itemCategoryPK = itemDetailValue.getItemCategoryPK();
            ItemAccountingCategoryPK itemAccountingCategoryPK = itemDetailValue.getItemAccountingCategoryPK();
            ItemPurchasingCategoryPK itemPurchasingCategoryPK = itemDetailValue.getItemPurchasingCategoryPK();
            PartyPK companyPartyPK = itemDetail.getCompanyPartyPK(); // Not updated
            ItemDeliveryTypePK itemDeliveryTypePK = itemDetail.getItemDeliveryTypePK(); // Not updated
            ItemInventoryTypePK itemInventoryTypePK = itemDetail.getItemInventoryTypePK(); // Not updated
            Boolean inventorySerialized = itemDetail.getInventorySerialized(); // Not updated
            SequencePK serialNumberSequencePK = itemDetail.getSerialNumberSequencePK(); // Not updated
            Boolean shippingChargeExempt = itemDetailValue.getShippingChargeExempt();
            Long shippingStartTime = itemDetailValue.getShippingStartTime();
            Long shippingEndTime = itemDetailValue.getShippingEndTime();
            Long salesOrderStartTime = itemDetailValue.getSalesOrderStartTime();
            Long salesOrderEndTime = itemDetailValue.getSalesOrderEndTime();
            Long purchaseOrderStartTime = itemDetailValue.getPurchaseOrderStartTime();
            Long purchaseOrderEndTime = itemDetailValue.getPurchaseOrderEndTime();
            Boolean allowClubDiscounts = itemDetailValue.getAllowClubDiscounts();
            Boolean allowCouponDiscounts = itemDetailValue.getAllowCouponDiscounts();
            Boolean allowAssociatePayments = itemDetailValue.getAllowAssociatePayments();
            UnitOfMeasureKindPK unitOfMeasureKindPK = itemDetail.getUnitOfMeasureKindPK(); // Not updated
            ItemPriceTypePK itemPriceTypePK = itemDetail.getItemPriceTypePK(); // Not updated
            CancellationPolicyPK cancellationPolicyPK = itemDetailValue.getCancellationPolicyPK();
            ReturnPolicyPK returnPolicyPK = itemDetailValue.getReturnPolicyPK();
            StylePathPK stylePathPK = itemDetail.getStylePathPK(); // Not updated
                    
            itemDetail = ItemDetailFactory.getInstance().create(itemPK, itemName, itemTypePK, itemUseTypePK, itemCategoryPK, itemAccountingCategoryPK,
                    itemPurchasingCategoryPK, companyPartyPK, itemDeliveryTypePK, itemInventoryTypePK, inventorySerialized, serialNumberSequencePK,
                    shippingChargeExempt, shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime, purchaseOrderStartTime,
                    purchaseOrderEndTime, allowClubDiscounts, allowCouponDiscounts, allowAssociatePayments, unitOfMeasureKindPK, itemPriceTypePK,
                    cancellationPolicyPK, returnPolicyPK, stylePathPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            item.setActiveDetail(itemDetail);
            item.setLastDetail(itemDetail);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public Item getItemByPK(ItemPK itemPK) {
        return ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, itemPK);
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Item */
    public Item getItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        ItemPK pk = new ItemPK(entityInstance.getEntityUniqueId());
        Item item = ItemFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return item;
    }

    public Item getItemByEntityInstance(EntityInstance entityInstance) {
        return getItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Item getItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Item Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    public ItemUnitOfMeasureType createItemUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ItemUnitOfMeasureType defaultItemUnitOfMeasureType = getDefaultItemUnitOfMeasureType(item);
        boolean defaultFound = defaultItemUnitOfMeasureType != null;
        
        if(defaultFound && isDefault) {
            ItemUnitOfMeasureTypeValue defaultItemUnitOfMeasureTypeValue = getDefaultItemUnitOfMeasureTypeValueForUpdate(item);
            
            defaultItemUnitOfMeasureTypeValue.setIsDefault(Boolean.FALSE);
            updateItemUnitOfMeasureTypeFromValue(defaultItemUnitOfMeasureTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ItemUnitOfMeasureType itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().create(item, unitOfMeasureType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemUnitOfMeasureType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return itemUnitOfMeasureType;
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
            
            PreparedStatement ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
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
        ItemUnitOfMeasureType itemUnitOfMeasureType = getItemUnitOfMeasureTypeForUpdate(item, unitOfMeasureType);
        
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
            
            PreparedStatement ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
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
        ItemUnitOfMeasureType itemUnitOfMeasureType = getDefaultItemUnitOfMeasureTypeForUpdate(item);
        
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
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, uomkdt_sortorder, uomkdt_unitofmeasurekindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_itm_itemid = ? AND iuomt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
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
                        "ORDER BY itmdt_itemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemunitofmeasuretypes " +
                        "WHERE iuomt_uomt_unitofmeasuretypeid = ? AND iuomt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemUnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
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
    
    public List<ItemUnitOfMeasureTypeTransfer> getItemUnitOfMeasureTypeTransfers(UserVisit userVisit, List<ItemUnitOfMeasureType> itemUnitOfMeasureTypes) {
        List<ItemUnitOfMeasureTypeTransfer> itemUnitOfMeasureTypeTransfers = new ArrayList<>(itemUnitOfMeasureTypes.size());
        ItemUnitOfMeasureTypeTransferCache itemUnitOfMeasureTypeTransferCache = getItemTransferCaches(userVisit).getItemUnitOfMeasureTypeTransferCache();
        
        itemUnitOfMeasureTypes.forEach((itemUnitOfMeasureType) ->
                itemUnitOfMeasureTypeTransfers.add(itemUnitOfMeasureTypeTransferCache.getTransfer(itemUnitOfMeasureType))
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
        return getItemTransferCaches(userVisit).getItemUnitOfMeasureTypeTransferCache().getTransfer(itemUnitOfMeasureType);
    }
    
    private void updateItemUnitOfMeasureTypeFromValue(ItemUnitOfMeasureTypeValue itemUnitOfMeasureTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(itemUnitOfMeasureTypeValue.hasBeenModified()) {
            ItemUnitOfMeasureType itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitOfMeasureTypeValue.getPrimaryKey());
            
            itemUnitOfMeasureType.setThruTime(session.START_TIME_LONG);
            itemUnitOfMeasureType.store();
            
            Item item = itemUnitOfMeasureType.getItem(); // Not Updated
            ItemPK itemPK = item.getPrimaryKey(); // Not Updated
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemUnitOfMeasureType.getUnitOfMeasureTypePK(); // Not Updated
            Boolean isDefault = itemUnitOfMeasureTypeValue.getIsDefault();
            Integer sortOrder = itemUnitOfMeasureTypeValue.getSortOrder();
            
            if(checkDefault) {
                ItemUnitOfMeasureType defaultItemUnitOfMeasureType = getDefaultItemUnitOfMeasureType(item);
                boolean defaultFound = defaultItemUnitOfMeasureType != null && !defaultItemUnitOfMeasureType.equals(itemUnitOfMeasureType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ItemUnitOfMeasureTypeValue defaultItemUnitOfMeasureTypeValue = getDefaultItemUnitOfMeasureTypeValueForUpdate(item);
                    
                    defaultItemUnitOfMeasureTypeValue.setIsDefault(Boolean.FALSE);
                    updateItemUnitOfMeasureTypeFromValue(defaultItemUnitOfMeasureTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            itemUnitOfMeasureType = ItemUnitOfMeasureTypeFactory.getInstance().create(itemPK, unitOfMeasureTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemUnitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateItemUnitOfMeasureTypeFromValue(ItemUnitOfMeasureTypeValue itemUnitOfMeasureTypeValue, BasePK updatedBy) {
        updateItemUnitOfMeasureTypeFromValue(itemUnitOfMeasureTypeValue, true, updatedBy);
    }
    
    public void deleteItemUnitOfMeasureType(ItemUnitOfMeasureType itemUnitOfMeasureType, BasePK deletedBy) {
        var vendorControl = Session.getModelController(VendorControl.class);
        Item item = itemUnitOfMeasureType.getItem();
        UnitOfMeasureType unitOfMeasureType = itemUnitOfMeasureType.getUnitOfMeasureType();
        
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
        ItemUnitOfMeasureType defaultItemUnitOfMeasureType = getDefaultItemUnitOfMeasureType(item);
        if(defaultItemUnitOfMeasureType == null) {
            List<ItemUnitOfMeasureType> itemUnitOfMeasureTypes = getItemUnitOfMeasureTypesByItemForUpdate(item);
            
            if(!itemUnitOfMeasureTypes.isEmpty()) {
                Iterator<ItemUnitOfMeasureType> iter = itemUnitOfMeasureTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemUnitOfMeasureType = iter.next();
                }
                ItemUnitOfMeasureTypeValue itemUnitOfMeasureTypeValue = defaultItemUnitOfMeasureType.getItemUnitOfMeasureTypeValue().clone();
                
                itemUnitOfMeasureTypeValue.setIsDefault(Boolean.TRUE);
                updateItemUnitOfMeasureTypeFromValue(itemUnitOfMeasureTypeValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemUnitOfMeasureType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemShippingTime itemShippingTime = ItemShippingTimeFactory.getInstance().create(item, customerType,
                shippingStartTime, shippingEndTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemShippingTime.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemShippingTimeFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemShippingTimeFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemShippingTimeFactory.getInstance().prepareStatement(query);
            
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
            ItemShippingTimePK itemShippingTimePK = itemShippingTimeValue.getPrimaryKey();
            ItemShippingTime itemShippingTime = ItemShippingTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemShippingTimePK);
            
            itemShippingTime.setThruTime(session.START_TIME_LONG);
            itemShippingTime.store();
            
            ItemPK itemPK = itemShippingTime.getItemPK();
            CustomerTypePK customerTypePK = itemShippingTime.getCustomerTypePK();
            Long shippingStartTime = itemShippingTimeValue.getShippingStartTime();
            Long shippingEndTime = itemShippingTimeValue.getShippingEndTime();
            
            itemShippingTime = ItemShippingTimeFactory.getInstance().create(itemPK, customerTypePK, shippingStartTime, shippingEndTime,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemShippingTime.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemShippingTimeTransfer getItemShippingTimeTransfer(UserVisit userVisit, ItemShippingTime itemShippingTime) {
        return itemShippingTime == null? null: getItemTransferCaches(userVisit).getItemShippingTimeTransferCache().getTransfer(itemShippingTime);
    }
    
    public ItemShippingTimeTransfer getItemShippingTimeTransfer(UserVisit userVisit, Item item, CustomerType customerType) {
        return getItemShippingTimeTransfer(userVisit, getItemShippingTime(item, customerType));
    }
    
    public List<ItemShippingTimeTransfer> getItemShippingTimeTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemShippingTime> itemShippingTimes = getItemShippingTimesByItem(item);
        List<ItemShippingTimeTransfer> itemShippingTimeTransfers = new ArrayList<>(itemShippingTimes.size());
        
        itemShippingTimes.forEach((itemShippingTime) -> {
            itemShippingTimeTransfers.add(getItemTransferCaches(userVisit).getItemShippingTimeTransferCache().getTransfer(itemShippingTime));
        });
        
        return itemShippingTimeTransfers;
    }
    
    public void deleteItemShippingTime(ItemShippingTime itemShippingTime, BasePK deletedBy) {
        itemShippingTime.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemShippingTime.getItemPK(), EventTypes.MODIFY.name(), itemShippingTime.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteItemShippingTimesByItem(Item item, BasePK deletedBy) {
        List<ItemShippingTime> itemShippingTimes = getItemShippingTimesByItemForUpdate(item);
        
        itemShippingTimes.forEach((itemShippingTime) -> 
                deleteItemShippingTime(itemShippingTime, deletedBy)
        );
    }
    
    public void deleteItemShippingTimesByCustomerType(CustomerType customerType, BasePK deletedBy) {
        List<ItemShippingTime> itemShippingTimes = getItemShippingTimesByCustomerTypeForUpdate(customerType);
        
        itemShippingTimes.forEach((itemShippingTime) -> 
                deleteItemShippingTime(itemShippingTime, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Checksum Types
    // --------------------------------------------------------------------------------

    public ItemAliasChecksumType createItemAliasChecksumType(String itemAliasChecksumTypeName, Boolean isDefault, Integer sortOrder) {
        return ItemAliasChecksumTypeFactory.getInstance().create(itemAliasChecksumTypeName, isDefault, sortOrder);
    }

    public List<ItemAliasChecksumType> getItemAliasChecksumTypes() {
        PreparedStatement ps = ItemAliasChecksumTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itemaliaschecksumtypes " +
                "ORDER BY iact_sortorder, iact_itemaliaschecksumtypename");

        return ItemAliasChecksumTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public ItemAliasChecksumType getItemAliasChecksumTypeByName(String itemAliasChecksumTypeName) {
        ItemAliasChecksumType itemAliasChecksumType = null;

        try {
            PreparedStatement ps = ItemAliasChecksumTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemaliaschecksumtypes " +
                    "WHERE iact_itemaliaschecksumtypename = ?");

            ps.setString(1, itemAliasChecksumTypeName);

            itemAliasChecksumType = ItemAliasChecksumTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemAliasChecksumType;
    }

    public ItemAliasChecksumTypeChoicesBean getItemAliasChecksumTypeChoices(String defaultItemAliasChecksumTypeChoice,
            Language language, boolean allowNullChoice) {
        List<ItemAliasChecksumType> itemAliasChecksumTypes = getItemAliasChecksumTypes();
        int size = itemAliasChecksumTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemAliasChecksumTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemAliasChecksumType : itemAliasChecksumTypes) {
            String label = getBestItemAliasChecksumTypeDescription(itemAliasChecksumType, language);
            String value = itemAliasChecksumType.getItemAliasChecksumTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultItemAliasChecksumTypeChoice != null && defaultItemAliasChecksumTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemAliasChecksumType.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemAliasChecksumTypeChoicesBean(labels, values, defaultValue);
    }

    public ItemAliasChecksumTypeTransfer getItemAliasChecksumTypeTransfer(UserVisit userVisit, ItemAliasChecksumType itemAliasChecksumType) {
        return getItemTransferCaches(userVisit).getItemAliasChecksumTypeTransferCache().getTransfer(itemAliasChecksumType);
    }

    // --------------------------------------------------------------------------------
    //   Item Alias Checksum Type Descriptions
    // --------------------------------------------------------------------------------

    public ItemAliasChecksumTypeDescription createItemAliasChecksumTypeDescription(ItemAliasChecksumType itemAliasChecksumType, Language language, String description) {
        return ItemAliasChecksumTypeDescriptionFactory.getInstance().create(itemAliasChecksumType, language, description);
    }

    public ItemAliasChecksumTypeDescription getItemAliasChecksumTypeDescription(ItemAliasChecksumType itemAliasChecksumType, Language language) {
        ItemAliasChecksumTypeDescription itemAliasChecksumTypeDescription = null;

        try {
            PreparedStatement ps = ItemAliasChecksumTypeDescriptionFactory.getInstance().prepareStatement(
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
        ItemAliasChecksumTypeDescription itemAliasChecksumTypeDescription = getItemAliasChecksumTypeDescription(itemAliasChecksumType, language);

        if(itemAliasChecksumTypeDescription == null && !language.getIsDefault()) {
            itemAliasChecksumTypeDescription = getItemAliasChecksumTypeDescription(itemAliasChecksumType, getPartyControl().getDefaultLanguage());
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
        ItemAliasType defaultItemAliasType = getDefaultItemAliasType();
        boolean defaultFound = defaultItemAliasType != null;
        
        if(defaultFound && isDefault) {
            ItemAliasTypeDetailValue defaultItemAliasTypeDetailValue = getDefaultItemAliasTypeDetailValueForUpdate();
            
            defaultItemAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateItemAliasTypeFromValue(defaultItemAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ItemAliasType itemAliasType = ItemAliasTypeFactory.getInstance().create();
        ItemAliasTypeDetail itemAliasTypeDetail = ItemAliasTypeDetailFactory.getInstance().create(session, itemAliasType, itemAliasTypeName, validationPattern,
                itemAliasChecksumType, allowMultiple, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemAliasType = ItemAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemAliasType.getPrimaryKey());
        itemAliasType.setActiveDetail(itemAliasTypeDetail);
        itemAliasType.setLastDetail(itemAliasTypeDetail);
        itemAliasType.store();
        
        sendEventUsingNames(itemAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return itemAliasType;
    }
    
    private ItemAliasType getItemAliasTypeByName(String itemAliasTypeName, EntityPermission entityPermission) {
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
            
            PreparedStatement ps = ItemAliasTypeFactory.getInstance().prepareStatement(query);
            
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
    
    private ItemAliasType getDefaultItemAliasType(EntityPermission entityPermission) {
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
        
        PreparedStatement ps = ItemAliasTypeFactory.getInstance().prepareStatement(query);
        
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
        
        PreparedStatement ps = ItemAliasTypeFactory.getInstance().prepareStatement(query);
        
        return ItemAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<ItemAliasType> getItemAliasTypes() {
        return getItemAliasTypes(EntityPermission.READ_ONLY);
    }
    
    public List<ItemAliasType> getItemAliasTypesForUpdate() {
        return getItemAliasTypes(EntityPermission.READ_WRITE);
    }
    
    public ItemAliasTypeTransfer getItemAliasTypeTransfer(UserVisit userVisit, ItemAliasType itemAliasType) {
        return getItemTransferCaches(userVisit).getItemAliasTypeTransferCache().getTransfer(itemAliasType);
    }
    
    public List<ItemAliasTypeTransfer> getItemAliasTypeTransfers(UserVisit userVisit) {
        List<ItemAliasType> itemAliasTypes = getItemAliasTypes();
        List<ItemAliasTypeTransfer> itemAliasTypeTransfers = new ArrayList<>(itemAliasTypes.size());
        ItemAliasTypeTransferCache itemAliasTypeTransferCache = getItemTransferCaches(userVisit).getItemAliasTypeTransferCache();
        
        itemAliasTypes.forEach((itemAliasType) ->
                itemAliasTypeTransfers.add(itemAliasTypeTransferCache.getTransfer(itemAliasType))
        );
        
        return itemAliasTypeTransfers;
    }
    
    public ItemAliasTypeChoicesBean getItemAliasTypeChoices(String defaultItemAliasTypeChoice, Language language,
            boolean allowNullChoice) {
        List<ItemAliasType> itemAliasTypes = getItemAliasTypes();
        int size = itemAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemAliasType : itemAliasTypes) {
            ItemAliasTypeDetail itemAliasTypeDetail = itemAliasType.getLastDetail();
            
            String label = getBestItemAliasTypeDescription(itemAliasType, language);
            String value = itemAliasTypeDetail.getItemAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemAliasTypeChoice != null && defaultItemAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateItemAliasTypeFromValue(ItemAliasTypeDetailValue itemAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemAliasTypeDetailValue.hasBeenModified()) {
            ItemAliasType itemAliasType = ItemAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAliasTypeDetailValue.getItemAliasTypePK());
            ItemAliasTypeDetail itemAliasTypeDetail = itemAliasType.getActiveDetailForUpdate();
            
            itemAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            itemAliasTypeDetail.store();
            
            ItemAliasTypePK itemAliasTypePK = itemAliasTypeDetail.getItemAliasTypePK();
            String itemAliasTypeName = itemAliasTypeDetailValue.getItemAliasTypeName();
            String validationPattern = itemAliasTypeDetailValue.getValidationPattern();
            ItemAliasChecksumTypePK itemAliasChecksumTypePK = itemAliasTypeDetailValue.getItemAliasChecksumTypePK();
            Boolean allowMultiple = itemAliasTypeDetailValue.getAllowMultiple();
            Boolean isDefault = itemAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = itemAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                ItemAliasType defaultItemAliasType = getDefaultItemAliasType();
                boolean defaultFound = defaultItemAliasType != null && !defaultItemAliasType.equals(itemAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ItemAliasTypeDetailValue defaultItemAliasTypeDetailValue = getDefaultItemAliasTypeDetailValueForUpdate();
                    
                    defaultItemAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateItemAliasTypeFromValue(defaultItemAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            itemAliasTypeDetail = ItemAliasTypeDetailFactory.getInstance().create(itemAliasTypePK, itemAliasTypeName, validationPattern, itemAliasChecksumTypePK,
                    allowMultiple, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            itemAliasType.setActiveDetail(itemAliasTypeDetail);
            itemAliasType.setLastDetail(itemAliasTypeDetail);
            
            sendEventUsingNames(itemAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateItemAliasTypeFromValue(ItemAliasTypeDetailValue itemAliasTypeDetailValue, BasePK updatedBy) {
        updateItemAliasTypeFromValue(itemAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteItemAliasType(ItemAliasType itemAliasType, BasePK deletedBy) {
        var vendorControl = Session.getModelController(VendorControl.class);
        
        List<Vendor> vendors = vendorControl.getVendorsByDefaultItemAliasTypeForUpdate(itemAliasType);
        vendors.stream().map((vendor) -> vendorControl.getVendorValue(vendor)).map((vendorValue) -> {
            vendorValue.setDefaultItemAliasTypePK(null);
            return vendorValue;            
        }).forEach((vendorValue) -> {
            vendorControl.updateVendorFromValue(vendorValue, deletedBy);
        });
        
        deleteItemAliasTypeDescriptionsByItemAliasType(itemAliasType, deletedBy);
        deleteItemAliasesByItemAliasType(itemAliasType, deletedBy);
        
        ItemAliasTypeDetail itemAliasTypeDetail = itemAliasType.getLastDetailForUpdate();
        itemAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        itemAliasType.setActiveDetail(null);
        itemAliasType.store();

        // Check for default, and pick one if necessary
        ItemAliasType defaultItemAliasType = getDefaultItemAliasType();
        if(defaultItemAliasType == null) {
            List<ItemAliasType> itemAliasTypes = getItemAliasTypesForUpdate();
            
            if(!itemAliasTypes.isEmpty()) {
                Iterator<ItemAliasType> iter = itemAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemAliasType = (ItemAliasType)iter.next();
                }
                ItemAliasTypeDetailValue itemAliasTypeDetailValue = Objects.requireNonNull(defaultItemAliasType).getLastDetailForUpdate().getItemAliasTypeDetailValue().clone();
                
                itemAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateItemAliasTypeFromValue(itemAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(itemAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemAliasTypeDescription createItemAliasTypeDescription(ItemAliasType itemAliasType, Language language,
            String description, BasePK createdBy) {
        ItemAliasTypeDescription itemAliasTypeDescription = ItemAliasTypeDescriptionFactory.getInstance().create(session,
                itemAliasType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), itemAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        ItemAliasTypeDescription itemAliasTypeDescription = getItemAliasTypeDescription(itemAliasType, language);
        
        if(itemAliasTypeDescription == null && !language.getIsDefault()) {
            itemAliasTypeDescription = getItemAliasTypeDescription(itemAliasType, getPartyControl().getDefaultLanguage());
        }
        
        if(itemAliasTypeDescription == null) {
            description = itemAliasType.getLastDetail().getItemAliasTypeName();
        } else {
            description = itemAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public ItemAliasTypeDescriptionTransfer getItemAliasTypeDescriptionTransfer(UserVisit userVisit, ItemAliasTypeDescription itemAliasTypeDescription) {
        return getItemTransferCaches(userVisit).getItemAliasTypeDescriptionTransferCache().getTransfer(itemAliasTypeDescription);
    }
    
    public List<ItemAliasTypeDescriptionTransfer> getItemAliasTypeDescriptionTransfersByItemAliasType(UserVisit userVisit, ItemAliasType itemAliasType) {
        List<ItemAliasTypeDescription> itemAliasTypeDescriptions = getItemAliasTypeDescriptionsByItemAliasType(itemAliasType);
        List<ItemAliasTypeDescriptionTransfer> itemAliasTypeDescriptionTransfers = new ArrayList<>(itemAliasTypeDescriptions.size());
        ItemAliasTypeDescriptionTransferCache itemAliasTypeDescriptionTransferCache = getItemTransferCaches(userVisit).getItemAliasTypeDescriptionTransferCache();
        
        itemAliasTypeDescriptions.forEach((itemAliasTypeDescription) ->
                itemAliasTypeDescriptionTransfers.add(itemAliasTypeDescriptionTransferCache.getTransfer(itemAliasTypeDescription))
        );
        
        return itemAliasTypeDescriptionTransfers;
    }
    
    public void updateItemAliasTypeDescriptionFromValue(ItemAliasTypeDescriptionValue itemAliasTypeDescriptionValue, BasePK updatedBy) {
        if(itemAliasTypeDescriptionValue.hasBeenModified()) {
            ItemAliasTypeDescription itemAliasTypeDescription = ItemAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAliasTypeDescriptionValue.getPrimaryKey());
            
            itemAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            itemAliasTypeDescription.store();
            
            ItemAliasType itemAliasType = itemAliasTypeDescription.getItemAliasType();
            Language language = itemAliasTypeDescription.getLanguage();
            String description = itemAliasTypeDescriptionValue.getDescription();
            
            itemAliasTypeDescription = ItemAliasTypeDescriptionFactory.getInstance().create(itemAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), itemAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemAliasTypeDescription(ItemAliasTypeDescription itemAliasTypeDescription, BasePK deletedBy) {
        itemAliasTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemAliasTypeDescription.getItemAliasTypePK(), EventTypes.MODIFY.name(), itemAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteItemAliasTypeDescriptionsByItemAliasType(ItemAliasType itemAliasType, BasePK deletedBy) {
        List<ItemAliasTypeDescription> itemAliasTypeDescriptions = getItemAliasTypeDescriptionsByItemAliasTypeForUpdate(itemAliasType);
        
        itemAliasTypeDescriptions.forEach((itemAliasTypeDescription) -> 
                deleteItemAliasTypeDescription(itemAliasTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Item Aliases
    // --------------------------------------------------------------------------------
    
    public ItemAlias createItemAlias(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType, String alias,
            BasePK createdBy) {
        ItemAlias itemAlias = ItemAliasFactory.getInstance().create(item, unitOfMeasureType, itemAliasType, alias,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return itemAlias;
    }
    
    public int countItemAliases(Item item, UnitOfMeasureType unitOfMeasureType, ItemAliasType itemAliasType) {
        return session.queryForInteger(
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
            
            PreparedStatement ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
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
                        "ORDER BY itmal_alias";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_itm_itemid = ? AND itmal_uomt_unitofmeasuretypeid = ? AND itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
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
                        "ORDER BY iatdt_sortorder, iatdt_itemaliastypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_itm_itemid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
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
                        "ORDER BY itmdt_itemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_uomt_unitofmeasuretypeid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
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
                        "ORDER BY itmdt_itemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaliases " +
                        "WHERE itmal_iat_itemaliastypeid = ? AND itmal_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemAliasFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = ItemAliasFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itemaliases " +
                    "WHERE itmal_itm_itemid = ? AND itmal_uomt_unitofmeasuretypeid = ? AND itmal_thrutime = ? " +
                    "FOR UPDATE");
            
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
            ItemAliasPK itemAliasPK = itemAliasValue.getPrimaryKey();
            ItemAlias itemAlias = ItemAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemAliasPK);
            
            itemAlias.setThruTime(session.START_TIME_LONG);
            itemAlias.store();
            
            ItemPK itemPK = itemAlias.getItemPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemAliasValue.getUnitOfMeasureTypePK();
            ItemAliasTypePK itemAliasTypePK = itemAliasValue.getItemAliasTypePK();
            String alias = itemAliasValue.getAlias();
            
            itemAlias = ItemAliasFactory.getInstance().create(itemPK, unitOfMeasureTypePK, itemAliasTypePK, alias,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemAlias.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemAliasTransfer getItemAliasTransfer(UserVisit userVisit, ItemAlias itemAlias) {
        return getItemTransferCaches(userVisit).getItemAliasTransferCache().getTransfer(itemAlias);
    }
    
    public List<ItemAliasTransfer> getItemAliasTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemAlias> itemAliases = getItemAliasesByItem(item);
        List<ItemAliasTransfer> itemAliasTransfers = new ArrayList<>(itemAliases.size());
        
        itemAliases.forEach((itemAlias) -> {
            itemAliasTransfers.add(getItemTransferCaches(userVisit).getItemAliasTransferCache().getTransfer(itemAlias));
        });
        
        return itemAliasTransfers;
    }
    
    public void deleteItemAlias(ItemAlias itemAlias, BasePK deletedBy) {
        itemAlias.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemAlias.getItem().getPrimaryKey(), EventTypes.MODIFY.name(), itemAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemKitOption itemKitOption = ItemKitOptionFactory.getInstance().create(item, allowPartialShipments,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemKitOption.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemKitOptionFactory.getInstance().prepareStatement(query);
            
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
            ItemKitOptionPK itemKitOptionPK = itemKitOptionValue.getPrimaryKey();
            ItemKitOption itemKitOption = ItemKitOptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemKitOptionPK);
            
            itemKitOption.setThruTime(session.START_TIME_LONG);
            itemKitOption.store();
            
            ItemPK itemPK = itemKitOption.getItemPK();
            Boolean allowPartialShipments = itemKitOptionValue.getAllowPartialShipments();
            
            itemKitOption = ItemKitOptionFactory.getInstance().create(itemPK, allowPartialShipments,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemKitOption.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemKitOption(ItemKitOption itemKitOption, BasePK deletedBy) {
        itemKitOption.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemKitOption.getItemPK(), EventTypes.MODIFY.name(), itemKitOption.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Country Of Origins
    // --------------------------------------------------------------------------------
    
    public ItemCountryOfOrigin createItemCountryOfOrigin(Item item, GeoCode countryGeoCode, Integer percent, BasePK createdBy) {
        ItemCountryOfOrigin itemCountryOfOrigin = ItemCountryOfOriginFactory.getInstance().create(item, countryGeoCode, percent,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemCountryOfOrigin.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemCountryOfOriginFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemCountryOfOriginFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemCountryOfOriginFactory.getInstance().prepareStatement(query);
            
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
            ItemCountryOfOriginPK itemCountryOfOriginPK = itemCountryOfOriginValue.getPrimaryKey();
            ItemCountryOfOrigin itemCountryOfOrigin = ItemCountryOfOriginFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemCountryOfOriginPK);
            
            itemCountryOfOrigin.setThruTime(session.START_TIME_LONG);
            itemCountryOfOrigin.store();
            
            ItemPK itemPK = itemCountryOfOrigin.getItemPK();
            GeoCodePK countryGeoCodePK = itemCountryOfOrigin.getCountryGeoCodePK();
            Integer percent = itemCountryOfOriginValue.getPercent();
            
            itemCountryOfOrigin = ItemCountryOfOriginFactory.getInstance().create(itemPK, countryGeoCodePK, percent, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemCountryOfOrigin.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemCountryOfOriginTransfer getItemCountryOfOriginTransfer(UserVisit userVisit, ItemCountryOfOrigin itemCountryOfOrigin) {
        return itemCountryOfOrigin == null? null: getItemTransferCaches(userVisit).getItemCountryOfOriginTransferCache().getTransfer(itemCountryOfOrigin);
    }

    public ItemCountryOfOriginTransfer getItemCountryOfOriginTransfer(UserVisit userVisit, Item item, GeoCode countryGeoCode) {
        return getItemCountryOfOriginTransfer(userVisit, getItemCountryOfOrigin(item, countryGeoCode));
    }

    public List<ItemCountryOfOriginTransfer> getItemCountryOfOriginTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemCountryOfOrigin> itemCountryOfOrigins = getItemCountryOfOriginsByItem(item);
        List<ItemCountryOfOriginTransfer> itemCountryOfOriginTransfers = new ArrayList<>(itemCountryOfOrigins.size());
        ItemCountryOfOriginTransferCache itemCountryOfOriginTransferCache = getItemTransferCaches(userVisit).getItemCountryOfOriginTransferCache();
        
        itemCountryOfOrigins.forEach((itemCountryOfOrigin) ->
                itemCountryOfOriginTransfers.add(itemCountryOfOriginTransferCache.getTransfer(itemCountryOfOrigin))
        );
        
        return itemCountryOfOriginTransfers;
    }
    
    public void deleteItemCountryOfOrigin(ItemCountryOfOrigin itemCountryOfOrigin, BasePK deletedBy) {
        itemCountryOfOrigin.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemCountryOfOrigin.getItemPK(), EventTypes.MODIFY.name(), itemCountryOfOrigin.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemKitMember itemKitMember = ItemKitMemberFactory.getInstance().create(item, inventoryCondition, unitOfMeasureType,
                memberItem, memberInventoryCondition, memberUnitOfMeasureType, quantity, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemKitMember.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(
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
            
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = ItemKitMemberFactory.getInstance().prepareStatement(
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
            ItemKitMember itemKitMember = ItemKitMemberFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemKitMemberValue.getPrimaryKey());
            
            itemKitMember.setThruTime(session.START_TIME_LONG);
            itemKitMember.store();
            
            ItemPK itemPK = itemKitMember.getItemPK();
            InventoryConditionPK inventoryConditionPK = itemKitMember.getInventoryConditionPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemKitMember.getUnitOfMeasureTypePK();
            ItemPK memberItemPK = itemKitMember.getMemberItemPK();
            InventoryConditionPK memberInventoryConditionPK = itemKitMember.getMemberInventoryConditionPK();
            UnitOfMeasureTypePK memberUnitOfMeasureTypePK = itemKitMember.getMemberUnitOfMeasureTypePK();
            Long quantity = itemKitMemberValue.getQuantity();
            
            itemKitMember = ItemKitMemberFactory.getInstance().create(itemPK, inventoryConditionPK, unitOfMeasureTypePK,
                    memberItemPK, memberInventoryConditionPK, memberUnitOfMeasureTypePK, quantity, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemKitMember.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemKitMemberTransfer getItemKitMemberTransfer(UserVisit userVisit, ItemKitMember itemKitMember) {
        return itemKitMember == null? null: getItemTransferCaches(userVisit).getItemKitMemberTransferCache().getTransfer(itemKitMember);
    }

    public ItemKitMemberTransfer getItemKitMemberTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Item memberItem, InventoryCondition memberInventoryCondition, UnitOfMeasureType memberUnitOfMeasureType) {
        return getItemKitMemberTransfer(userVisit, getItemKitMember(item, inventoryCondition, unitOfMeasureType, memberItem, memberInventoryCondition,
                memberUnitOfMeasureType));
    }

    public List<ItemKitMemberTransfer> getItemKitMemberTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemKitMember> itemKitMembers = getItemKitMembersByItem(item);
        List<ItemKitMemberTransfer> itemKitMemberTransfers = new ArrayList<>(itemKitMembers.size());
        ItemKitMemberTransferCache itemKitMemberTransferCache = getItemTransferCaches(userVisit).getItemKitMemberTransferCache();
        
        itemKitMembers.forEach((itemKitMember) ->
                itemKitMemberTransfers.add(itemKitMemberTransferCache.getTransfer(itemKitMember))
        );
        
        return itemKitMemberTransfers;
    }
    
    public void deleteItemKitMember(ItemKitMember itemKitMember, BasePK deletedBy) {
        itemKitMember.setThruTime(session.START_TIME_LONG);
        itemKitMember.store();
        
        sendEventUsingNames(itemKitMember.getItemPK(), EventTypes.MODIFY.name(), itemKitMember.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemPackCheckRequirement itemPackCheckRequirement = ItemPackCheckRequirementFactory.getInstance().create(item,
                unitOfMeasureType, minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemPackCheckRequirement.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = ItemPackCheckRequirementFactory.getInstance().prepareStatement(
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
            ItemPackCheckRequirementPK itemPackCheckRequirementPK = itemPackCheckRequirementValue.getPrimaryKey();
            ItemPackCheckRequirement itemPackCheckRequirement = ItemPackCheckRequirementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemPackCheckRequirementPK);
            
            itemPackCheckRequirement.setThruTime(session.START_TIME_LONG);
            itemPackCheckRequirement.store();
            
            ItemPK itemPK = itemPackCheckRequirement.getItemPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemPackCheckRequirement.getUnitOfMeasureTypePK();
            Long minimumQuantity = itemPackCheckRequirementValue.getMinimumQuantity();
            Long maximumQuantity = itemPackCheckRequirementValue.getMaximumQuantity();
            
            itemPackCheckRequirement = ItemPackCheckRequirementFactory.getInstance().create(itemPK, unitOfMeasureTypePK,
                    minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemPackCheckRequirement.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemPackCheckRequirementTransfer getItemPackCheckRequirementTransfer(UserVisit userVisit, ItemPackCheckRequirement itemPackCheckRequirement) {
        return itemPackCheckRequirement == null ? null : getItemTransferCaches(userVisit).getItemPackCheckRequirementTransferCache().getTransfer(itemPackCheckRequirement);
    }

    public ItemPackCheckRequirementTransfer getItemPackCheckRequirementTransfer(UserVisit userVisit, Item item, UnitOfMeasureType unitOfMeasureType) {
        ItemPackCheckRequirement itemPackCheckRequirement = getItemPackCheckRequirement(item, unitOfMeasureType);

        return getItemPackCheckRequirementTransfer(userVisit, itemPackCheckRequirement);
    }

    public List<ItemPackCheckRequirementTransfer> getItemPackCheckRequirementTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemPackCheckRequirement> itemPackCheckRequirements = getItemPackCheckRequirementsByItem(item);
        List<ItemPackCheckRequirementTransfer> itemPackCheckRequirementTransfers = new ArrayList<>(itemPackCheckRequirements.size());
        ItemPackCheckRequirementTransferCache itemPackCheckRequirementTransferCache = getItemTransferCaches(userVisit).getItemPackCheckRequirementTransferCache();
        
        itemPackCheckRequirements.forEach((itemPackCheckRequirement) ->
                itemPackCheckRequirementTransfers.add(itemPackCheckRequirementTransferCache.getTransfer(itemPackCheckRequirement))
        );
        
        return itemPackCheckRequirementTransfers;
    }
    
    public void deleteItemPackCheckRequirement(ItemPackCheckRequirement itemPackCheckRequirement, BasePK deletedBy) {
        itemPackCheckRequirement.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemPackCheckRequirement.getItemPK(), EventTypes.MODIFY.name(), itemPackCheckRequirement.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit = ItemUnitCustomerTypeLimitFactory.getInstance().create(item,
                inventoryCondition, unitOfMeasureType, customerType, minimumQuantity, maximumQuantity, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemUnitCustomerTypeLimit.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(
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
            
            PreparedStatement ps = ItemUnitCustomerTypeLimitFactory.getInstance().prepareStatement(query);
            
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
            ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit = ItemUnitCustomerTypeLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitCustomerTypeLimitValue.getPrimaryKey());
            
            itemUnitCustomerTypeLimit.setThruTime(session.START_TIME_LONG);
            itemUnitCustomerTypeLimit.store();
            
            ItemPK itemPK = itemUnitCustomerTypeLimit.getItemPK();
            InventoryConditionPK inventoryConditionPK = itemUnitCustomerTypeLimit.getInventoryConditionPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemUnitCustomerTypeLimit.getUnitOfMeasureTypePK();
            CustomerTypePK customerTypePK = itemUnitCustomerTypeLimit.getCustomerTypePK();
            Long minimumQuantity = itemUnitCustomerTypeLimitValue.getMinimumQuantity();
            Long maximumQuantity = itemUnitCustomerTypeLimitValue.getMaximumQuantity();
            
            itemUnitCustomerTypeLimit = ItemUnitCustomerTypeLimitFactory.getInstance().create(itemPK, inventoryConditionPK,
                    unitOfMeasureTypePK, customerTypePK, minimumQuantity, maximumQuantity, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemUnitCustomerTypeLimit.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemUnitCustomerTypeLimitTransfer getItemUnitCustomerTypeLimitTransfer(UserVisit userVisit, ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        return itemUnitCustomerTypeLimit == null? null: getItemTransferCaches(userVisit).getItemUnitCustomerTypeLimitTransferCache().getTransfer(itemUnitCustomerTypeLimit);
    }

    public ItemUnitCustomerTypeLimitTransfer getItemUnitCustomerTypeLimitTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, CustomerType customerType) {
        return getItemUnitCustomerTypeLimitTransfer(userVisit, getItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType));
    }

    public List<ItemUnitCustomerTypeLimitTransfer> getItemUnitCustomerTypeLimitTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemUnitCustomerTypeLimit> itemUnitCustomerTypeLimits = getItemUnitCustomerTypeLimitsByItem(item);
        List<ItemUnitCustomerTypeLimitTransfer> itemUnitCustomerTypeLimitTransfers = new ArrayList<>(itemUnitCustomerTypeLimits.size());
        ItemUnitCustomerTypeLimitTransferCache itemUnitCustomerTypeLimitTransferCache = getItemTransferCaches(userVisit).getItemUnitCustomerTypeLimitTransferCache();
        
        itemUnitCustomerTypeLimits.forEach((itemUnitCustomerTypeLimit) ->
                itemUnitCustomerTypeLimitTransfers.add(itemUnitCustomerTypeLimitTransferCache.getTransfer(itemUnitCustomerTypeLimit))
        );
        
        return itemUnitCustomerTypeLimitTransfers;
    }
    
    public void deleteItemUnitCustomerTypeLimit(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit, BasePK deletedBy) {
        itemUnitCustomerTypeLimit.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemUnitCustomerTypeLimit.getItemPK(), EventTypes.MODIFY.name(), itemUnitCustomerTypeLimit.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemUnitLimit itemUnitLimit = ItemUnitLimitFactory.getInstance().create(item, inventoryCondition, unitOfMeasureType,
                minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemUnitLimit.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitLimitFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = ItemUnitLimitFactory.getInstance().prepareStatement(
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
            ItemUnitLimit itemUnitLimit = ItemUnitLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemUnitLimitValue.getPrimaryKey());
            
            itemUnitLimit.setThruTime(session.START_TIME_LONG);
            itemUnitLimit.store();
            
            ItemPK itemPK = itemUnitLimit.getItemPK();
            InventoryConditionPK inventoryConditionPK = itemUnitLimit.getInventoryConditionPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemUnitLimit.getUnitOfMeasureTypePK();
            Long minimumQuantity = itemUnitLimitValue.getMinimumQuantity();
            Long maximumQuantity = itemUnitLimitValue.getMaximumQuantity();
            
            itemUnitLimit = ItemUnitLimitFactory.getInstance().create(itemPK, inventoryConditionPK, unitOfMeasureTypePK,
                    minimumQuantity, maximumQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemUnitLimit.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemUnitLimitTransfer getItemUnitLimitTransfer(UserVisit userVisit, ItemUnitLimit itemUnitLimit) {
        return itemUnitLimit == null? null: getItemTransferCaches(userVisit).getItemUnitLimitTransferCache().getTransfer(itemUnitLimit);
    }

    public ItemUnitLimitTransfer getItemUnitLimitTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getItemUnitLimitTransfer(userVisit, getItemUnitLimit(item, inventoryCondition, unitOfMeasureType));
    }

    public List<ItemUnitLimitTransfer> getItemUnitLimitTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemUnitLimit> itemUnitLimits = getItemUnitLimitsByItem(item);
        List<ItemUnitLimitTransfer> itemUnitLimitTransfers = new ArrayList<>(itemUnitLimits.size());
        ItemUnitLimitTransferCache itemUnitLimitTransferCache = getItemTransferCaches(userVisit).getItemUnitLimitTransferCache();
        
        itemUnitLimits.forEach((itemUnitLimit) ->
                itemUnitLimitTransfers.add(itemUnitLimitTransferCache.getTransfer(itemUnitLimit))
        );
        
        return itemUnitLimitTransfers;
    }
    
    public void deleteItemUnitLimit(ItemUnitLimit itemUnitLimit, BasePK deletedBy) {
        itemUnitLimit.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemUnitLimit.getItemPK(), EventTypes.MODIFY.name(), itemUnitLimit.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemUnitPriceLimit itemUnitPriceLimit = ItemUnitPriceLimitFactory.getInstance().create(item, inventoryCondition,
                unitOfMeasureType, currency, minimumUnitPrice, maximumUnitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemUnitPriceLimit.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = ItemUnitPriceLimitFactory.getInstance().prepareStatement(
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
            ItemUnitPriceLimit itemUnitPriceLimit = ItemUnitPriceLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemUnitPriceLimitValue.getPrimaryKey());
            
            itemUnitPriceLimit.setThruTime(session.START_TIME_LONG);
            itemUnitPriceLimit.store();
            
            ItemPK itemPK = itemUnitPriceLimit.getItemPK();
            InventoryConditionPK inventoryConditionPK = itemUnitPriceLimit.getInventoryConditionPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemUnitPriceLimit.getUnitOfMeasureTypePK();
            CurrencyPK currencyPK = itemUnitPriceLimit.getCurrencyPK();
            Long minimumUnitPrice = itemUnitPriceLimitValue.getMinimumUnitPrice();
            Long maximumUnitPrice = itemUnitPriceLimitValue.getMaximumUnitPrice();
            
            itemUnitPriceLimit = ItemUnitPriceLimitFactory.getInstance().create(itemPK, inventoryConditionPK,
                    unitOfMeasureTypePK, currencyPK, minimumUnitPrice, maximumUnitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemUnitPriceLimit.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemUnitPriceLimitTransfer getItemUnitPriceLimitTransfer(UserVisit userVisit, ItemUnitPriceLimit itemUnitPriceLimit) {
        return itemUnitPriceLimit == null? null: getItemTransferCaches(userVisit).getItemUnitPriceLimitTransferCache().getTransfer(itemUnitPriceLimit);
    }

    public ItemUnitPriceLimitTransfer getItemUnitPriceLimitTransfer(UserVisit userVisit, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getItemUnitPriceLimitTransfer(userVisit, getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency));
    }

    public List<ItemUnitPriceLimitTransfer> getItemUnitPriceLimitTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemUnitPriceLimit> itemUnitPriceLimits = getItemUnitPriceLimitsByItem(item);
        List<ItemUnitPriceLimitTransfer> itemUnitPriceLimitTransfers = new ArrayList<>(itemUnitPriceLimits.size());
        ItemUnitPriceLimitTransferCache itemUnitPriceLimitTransferCache = getItemTransferCaches(userVisit).getItemUnitPriceLimitTransferCache();
        
        itemUnitPriceLimits.forEach((itemUnitPriceLimit) ->
                itemUnitPriceLimitTransfers.add(itemUnitPriceLimitTransferCache.getTransfer(itemUnitPriceLimit))
        );
        
        return itemUnitPriceLimitTransfers;
    }
    
    public void deleteItemUnitPriceLimit(ItemUnitPriceLimit itemUnitPriceLimit, BasePK deletedBy) {
        itemUnitPriceLimit.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemUnitPriceLimit.getItemPK(), EventTypes.MODIFY.name(), itemUnitPriceLimit.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
    
    public ItemPriceType createItemPriceType(String itemPriceTypeName, Boolean isDefault, Integer sortOrder) {
        return ItemPriceTypeFactory.getInstance().create(itemPriceTypeName, isDefault, sortOrder);
    }
    
    public ItemPriceType getItemPriceTypeByName(String itemPriceTypeName) {
        ItemPriceType itemPriceType;
        
        try {
            PreparedStatement ps = ItemPriceTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM itempricetypes " +
                    "WHERE ipt_itempricetypename = ?");
            
            ps.setString(1, itemPriceTypeName);
            
            itemPriceType = ItemPriceTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemPriceType;
    }
    
    public List<ItemPriceType> getItemPriceTypes() {
        PreparedStatement ps = ItemPriceTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM itempricetypes " +
                "ORDER BY ipt_sortorder, ipt_itempricetypename");
        
        return ItemPriceTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ItemPriceTypeChoicesBean getItemPriceTypeChoices(String defaultItemPriceTypeChoice, Language language,
            boolean allowNullChoice) {
        List<ItemPriceType> itemPriceTypes = getItemPriceTypes();
        int size = itemPriceTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemPriceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemPriceType : itemPriceTypes) {
            String label = getBestItemPriceTypeDescription(itemPriceType, language);
            String value = itemPriceType.getItemPriceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemPriceTypeChoice != null && defaultItemPriceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemPriceType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemPriceTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ItemPriceTypeTransfer getItemPriceTypeTransfer(UserVisit userVisit, ItemPriceType itemPriceType) {
        return getItemTransferCaches(userVisit).getItemPriceTypeTransferCache().getTransfer(itemPriceType);
    }
    
    public List<ItemPriceTypeTransfer> getItemPriceTypeTransfers(UserVisit userVisit) {
        List<ItemPriceType> itemPriceTypes = getItemPriceTypes();
        List<ItemPriceTypeTransfer> itemPriceTypeTransfers = new ArrayList<>(itemPriceTypes.size());
        ItemPriceTypeTransferCache itemPriceTypeTransferCache = getItemTransferCaches(userVisit).getItemPriceTypeTransferCache();
        
        itemPriceTypes.forEach((itemPriceType) ->
                itemPriceTypeTransfers.add(itemPriceTypeTransferCache.getTransfer(itemPriceType))
        );
        
        return itemPriceTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Price Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemPriceTypeDescription createItemPriceTypeDescription(ItemPriceType itemPriceType, Language language, String description) {
        return ItemPriceTypeDescriptionFactory.getInstance().create(itemPriceType, language, description);
    }
    
    public ItemPriceTypeDescription getItemPriceTypeDescription(ItemPriceType itemPriceType, Language language) {
        ItemPriceTypeDescription itemPriceTypeDescription;
        
        try {
            PreparedStatement ps = ItemPriceTypeDescriptionFactory.getInstance().prepareStatement(
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
        ItemPriceTypeDescription itemPriceTypeDescription = getItemPriceTypeDescription(itemPriceType, language);
        
        if(itemPriceTypeDescription == null && !language.getIsDefault()) {
            itemPriceTypeDescription = getItemPriceTypeDescription(itemPriceType, getPartyControl().getDefaultLanguage());
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
        ItemPrice itemPrice = ItemPriceFactory.getInstance().create(item, inventoryCondition, unitOfMeasureType, currency,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemPrice.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return itemPrice;
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
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_itm_itemid = ? AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
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
                        "ORDER BY itmdt_itemname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_invcon_inventoryconditionid = ? AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
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
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_uomt_unitofmeasuretypeid = ? AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
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
    
    public List<ItemPrice> getItemPricesByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        List<ItemPrice> itemPrices;
        
        try {
            PreparedStatement ps = ItemPriceFactory.getInstance().prepareStatement(
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
                        "ORDER BY cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemprices " +
                        "WHERE itmp_itm_itemid = ? AND itmp_invcon_inventoryconditionid = ? AND itmp_uomt_unitofmeasuretypeid = ? " +
                        "AND itmp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = ItemPriceFactory.getInstance().prepareStatement(query);
            
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
        return getItemTransferCaches(userVisit).getItemPriceTransferCache().getTransfer(itemPrice);
    }
    
    private List<ItemPriceTransfer> getItemPriceTransfers(UserVisit userVisit, List<ItemPrice> itemPrices) {
        List<ItemPriceTransfer> itemPriceTransfers = new ArrayList<>(itemPrices.size());
        ItemPriceTransferCache itemPriceTransferCache = getItemTransferCaches(userVisit).getItemPriceTransferCache();
        
        itemPrices.forEach((itemPrice) ->
                itemPriceTransfers.add(itemPriceTransferCache.getTransfer(itemPrice))
        );
        
        return itemPriceTransfers;
    }
    
    public ListWrapper<HistoryTransfer<ItemPriceTransfer>> getItemPriceHistory(UserVisit userVisit, ItemPrice itemPrice) {
        return getItemTransferCaches(userVisit).getItemPriceTransferCache().getHistory(itemPrice);
    }
    
    public List<ItemPriceTransfer> getItemPriceTransfersByItem(UserVisit userVisit, Item item) {
        return getItemPriceTransfers(userVisit, getItemPricesByItem(item));
    }
    
    public void deleteItemPrice(ItemPrice itemPrice, BasePK deletedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        Item item = itemPrice.getItem();
        String itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();
        
        if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
            ItemFixedPrice itemFixedPrice = getItemFixedPriceForUpdate(itemPrice);
            
            if(itemFixedPrice != null) {
                deleteItemFixedPrice(itemFixedPrice, deletedBy);
            }
        } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
            ItemVariablePrice itemVariablePrice = getItemVariablePriceForUpdate(itemPrice);
            
            if(itemVariablePrice != null) {
                deleteItemVariablePrice(itemVariablePrice, deletedBy);
            }
        }
        
        itemPrice.setThruTime(session.START_TIME_LONG);
        itemPrice.store();
        
        sendEventUsingNames(itemPrice.getItemPK(), EventTypes.MODIFY.name(), itemPrice.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

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
        ItemFixedPrice itemFixedPrice = ItemFixedPriceFactory.getInstance().create(itemPrice, unitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemPrice.getItemPK(), EventTypes.MODIFY.name(), itemFixedPrice.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemFixedPriceFactory.getInstance().prepareStatement(query);
            
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
        ItemFixedPrice itemFixedPrice = getItemFixedPriceForUpdate(itemPrice);
        
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
            ItemFixedPrice itemFixedPrice = ItemFixedPriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemFixedPriceValue.getPrimaryKey());
            
            itemFixedPrice.setThruTime(session.START_TIME_LONG);
            itemFixedPrice.store();
            
            ItemPricePK itemPricePK = itemFixedPrice.getItemPricePK();
            Long unitPrice = itemFixedPriceValue.getUnitPrice();
            
            itemFixedPrice = ItemFixedPriceFactory.getInstance().create(itemPricePK, unitPrice,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemFixedPrice.getItemPrice().getItemPK(), EventTypes.MODIFY.name(), itemFixedPrice.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemFixedPrice(ItemFixedPrice itemFixedPrice, BasePK deletedBy) {
        itemFixedPrice.setThruTime(session.START_TIME_LONG);
        itemFixedPrice.store();
        
        sendEventUsingNames(itemFixedPrice.getItemPrice().getItemPK(), EventTypes.MODIFY.name(), itemFixedPrice.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Variable Prices
    // --------------------------------------------------------------------------------
    
    public ItemVariablePrice createItemVariablePrice(ItemPrice itemPrice, Long minimumUnitPrice, Long maximumUnitPrice, Long unitPriceIncrement,
            BasePK createdBy) {
        ItemVariablePrice itemVariablePrice = ItemVariablePriceFactory.getInstance().create(itemPrice, minimumUnitPrice,
                maximumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemPrice.getItemPK(), EventTypes.MODIFY.name(), itemVariablePrice.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemVariablePriceFactory.getInstance().prepareStatement(query);
            
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
        ItemVariablePrice itemVariablePrice = getItemVariablePriceForUpdate(itemPrice);
        
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
            ItemVariablePrice itemVariablePrice = ItemVariablePriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemVariablePriceValue.getPrimaryKey());
            
            itemVariablePrice.setThruTime(session.START_TIME_LONG);
            itemVariablePrice.store();
            
            ItemPricePK itemPricePK = itemVariablePrice.getItemPricePK();
            Long maximumUnitPrice = itemVariablePriceValue.getMaximumUnitPrice();
            Long minimumUnitPrice = itemVariablePriceValue.getMinimumUnitPrice();
            Long unitPriceIncrement = itemVariablePriceValue.getUnitPriceIncrement();
            
            itemVariablePrice = ItemVariablePriceFactory.getInstance().create(itemPricePK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemVariablePrice.getItemPrice().getItemPK(), EventTypes.MODIFY.name(), itemVariablePrice.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemVariablePrice(ItemVariablePrice itemVariablePrice, BasePK deletedBy) {
        itemVariablePrice.setThruTime(session.START_TIME_LONG);
        itemVariablePrice.store();
        
        sendEventUsingNames(itemVariablePrice.getItemPrice().getItemPK(), EventTypes.MODIFY.name(), itemVariablePrice.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Description Types
    // --------------------------------------------------------------------------------

    public ItemDescriptionType createItemDescriptionType(String itemDescriptionTypeName, ItemDescriptionType parentItemDescriptionType,
            Boolean useParentIfMissing, MimeTypeUsageType mimeTypeUsageType, Boolean checkContentWebAddress, Boolean includeInIndex, Boolean indexDefault,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ItemDescriptionType defaultItemDescriptionType = getDefaultItemDescriptionType();
        boolean defaultFound = defaultItemDescriptionType != null;

        if(defaultFound && isDefault) {
            ItemDescriptionTypeDetailValue defaultItemDescriptionTypeDetailValue = getDefaultItemDescriptionTypeDetailValueForUpdate();

            defaultItemDescriptionTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateItemDescriptionTypeFromValue(defaultItemDescriptionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ItemDescriptionType itemDescriptionType = ItemDescriptionTypeFactory.getInstance().create();
        ItemDescriptionTypeDetail itemDescriptionTypeDetail = ItemDescriptionTypeDetailFactory.getInstance().create(itemDescriptionType, itemDescriptionTypeName,
                parentItemDescriptionType, useParentIfMissing, mimeTypeUsageType, checkContentWebAddress, includeInIndex, indexDefault, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemDescriptionType = ItemDescriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemDescriptionType.getPrimaryKey());
        itemDescriptionType.setActiveDetail(itemDescriptionTypeDetail);
        itemDescriptionType.setLastDetail(itemDescriptionTypeDetail);
        itemDescriptionType.store();

        sendEventUsingNames(itemDescriptionType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return itemDescriptionType;
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

    private ItemDescriptionType getItemDescriptionTypeByName(String itemDescriptionTypeName, EntityPermission entityPermission) {
        return ItemDescriptionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getItemDescriptionTypeByNameQueries, itemDescriptionTypeName);
    }

    public ItemDescriptionType getItemDescriptionTypeByName(String itemDescriptionTypeName) {
        return getItemDescriptionTypeByName(itemDescriptionTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionType getItemDescriptionTypeByNameForUpdate(String itemDescriptionTypeName) {
        return getItemDescriptionTypeByName(itemDescriptionTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeDetailValue getItemDescriptionTypeDetailValueForUpdate(ItemDescriptionType itemDescriptionType) {
        return itemDescriptionType == null? null: itemDescriptionType.getLastDetailForUpdate().getItemDescriptionTypeDetailValue().clone();
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

    private ItemDescriptionType getDefaultItemDescriptionType(EntityPermission entityPermission) {
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
        return getItemTransferCaches(userVisit).getItemDescriptionTypeTransferCache().getTransfer(itemDescriptionType);
    }

    public List<ItemDescriptionTypeTransfer> getItemDescriptionTypeTransfers(UserVisit userVisit, List<ItemDescriptionType> itemDescriptionTypes) {
        List<ItemDescriptionTypeTransfer> itemDescriptionTypeTransfers = new ArrayList<>(itemDescriptionTypes.size());
        ItemDescriptionTypeTransferCache itemDescriptionTypeTransferCache = getItemTransferCaches(userVisit).getItemDescriptionTypeTransferCache();

        itemDescriptionTypes.forEach((itemDescriptionType) ->
                itemDescriptionTypeTransfers.add(itemDescriptionTypeTransferCache.getTransfer(itemDescriptionType))
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
        List<ItemDescriptionType> itemDescriptionTypes = getItemDescriptionTypes();
        int size = itemDescriptionTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemDescriptionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemDescriptionType : itemDescriptionTypes) {
            ItemDescriptionTypeDetail itemDescriptionTypeDetail = itemDescriptionType.getLastDetail();

            String label = getBestItemDescriptionTypeDescription(itemDescriptionType, language);
            String value = itemDescriptionTypeDetail.getItemDescriptionTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultItemDescriptionTypeChoice != null && defaultItemDescriptionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemDescriptionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemDescriptionTypeChoicesBean(labels, values, defaultValue);
    }

    public boolean isParentItemDescriptionTypeSafe(ItemDescriptionType itemDescriptionType,
            ItemDescriptionType parentItemDescriptionType) {
        boolean safe = true;

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
            ItemDescriptionType itemDescriptionType = ItemDescriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionTypeDetailValue.getItemDescriptionTypePK());
            ItemDescriptionTypeDetail itemDescriptionTypeDetail = itemDescriptionType.getActiveDetailForUpdate();

            itemDescriptionTypeDetail.setThruTime(session.START_TIME_LONG);
            itemDescriptionTypeDetail.store();

            ItemDescriptionTypePK itemDescriptionTypePK = itemDescriptionTypeDetail.getItemDescriptionTypePK(); // Not updated
            String itemDescriptionTypeName = itemDescriptionTypeDetailValue.getItemDescriptionTypeName();
            ItemDescriptionTypePK parentItemDescriptionTypePK = itemDescriptionTypeDetailValue.getParentItemDescriptionTypePK();
            Boolean useParentIfMissing = itemDescriptionTypeDetailValue.getUseParentIfMissing();
            MimeTypeUsageTypePK mimeTypeUsageTypePK = itemDescriptionTypeDetail.getMimeTypeUsageTypePK(); // Not updated
            Boolean checkContentWebAddress = itemDescriptionTypeDetailValue.getCheckContentWebAddress();
            Boolean includeInIndex = itemDescriptionTypeDetailValue.getIncludeInIndex();
            Boolean indexDefault = itemDescriptionTypeDetailValue.getIndexDefault();
            Boolean isDefault = itemDescriptionTypeDetailValue.getIsDefault();
            Integer sortOrder = itemDescriptionTypeDetailValue.getSortOrder();

            if(checkDefault) {
                ItemDescriptionType defaultItemDescriptionType = getDefaultItemDescriptionType();
                boolean defaultFound = defaultItemDescriptionType != null && !defaultItemDescriptionType.equals(itemDescriptionType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ItemDescriptionTypeDetailValue defaultItemDescriptionTypeDetailValue = getDefaultItemDescriptionTypeDetailValueForUpdate();

                    defaultItemDescriptionTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateItemDescriptionTypeFromValue(defaultItemDescriptionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            itemDescriptionTypeDetail = ItemDescriptionTypeDetailFactory.getInstance().create(itemDescriptionTypePK, itemDescriptionTypeName,
                    parentItemDescriptionTypePK, useParentIfMissing, mimeTypeUsageTypePK, checkContentWebAddress, includeInIndex, indexDefault, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            itemDescriptionType.setActiveDetail(itemDescriptionTypeDetail);
            itemDescriptionType.setLastDetail(itemDescriptionTypeDetail);

            sendEventUsingNames(itemDescriptionTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateItemDescriptionTypeFromValue(ItemDescriptionTypeDetailValue itemDescriptionTypeDetailValue, BasePK updatedBy) {
        updateItemDescriptionTypeFromValue(itemDescriptionTypeDetailValue, true, updatedBy);
    }

    private void deleteItemDescriptionType(ItemDescriptionType itemDescriptionType, boolean checkDefault, BasePK deletedBy) {
        ItemDescriptionTypeDetail itemDescriptionTypeDetail = itemDescriptionType.getLastDetailForUpdate();
        MimeTypeUsageType mimeTypeUsageType = itemDescriptionTypeDetail.getMimeTypeUsageType();

        deleteItemDescriptionsByItemDescriptionType(itemDescriptionType, deletedBy);
        deleteItemDescriptionTypesByParentItemDescriptionType(itemDescriptionType, deletedBy);
        deleteItemDescriptionTypeDescriptionsByItemDescriptionType(itemDescriptionType, deletedBy);
        deleteItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType, deletedBy);

        if(mimeTypeUsageType != null) {
            String mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                deleteItemImageDescriptionTypeByItemDescriptionType(itemDescriptionType, deletedBy);
            }
        }

        itemDescriptionTypeDetail.setThruTime(session.START_TIME_LONG);
        itemDescriptionType.setActiveDetail(null);
        itemDescriptionType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            ItemDescriptionType defaultItemDescriptionType = getDefaultItemDescriptionType();
            
            if(defaultItemDescriptionType == null) {
                List<ItemDescriptionType> itemDescriptionTypes = getItemDescriptionTypesForUpdate();

                if(!itemDescriptionTypes.isEmpty()) {
                    Iterator<ItemDescriptionType> iter = itemDescriptionTypes.iterator();
                    if(iter.hasNext()) {
                        defaultItemDescriptionType = iter.next();
                    }
                    ItemDescriptionTypeDetailValue itemDescriptionTypeDetailValue = Objects.requireNonNull(defaultItemDescriptionType).getLastDetailForUpdate().getItemDescriptionTypeDetailValue().clone();

                    itemDescriptionTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateItemDescriptionTypeFromValue(itemDescriptionTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(itemDescriptionType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
        ItemDescriptionTypeDescription itemDescriptionTypeDescription = ItemDescriptionTypeDescriptionFactory.getInstance().create(itemDescriptionType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY.name(), itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        ItemDescriptionTypeDescription itemDescriptionTypeDescription = getItemDescriptionTypeDescription(itemDescriptionType, language);

        if(itemDescriptionTypeDescription == null && !language.getIsDefault()) {
            itemDescriptionTypeDescription = getItemDescriptionTypeDescription(itemDescriptionType, getPartyControl().getDefaultLanguage());
        }

        if(itemDescriptionTypeDescription == null) {
            description = itemDescriptionType.getLastDetail().getItemDescriptionTypeName();
        } else {
            description = itemDescriptionTypeDescription.getDescription();
        }

        return description;
    }

    public ItemDescriptionTypeDescriptionTransfer getItemDescriptionTypeDescriptionTransfer(UserVisit userVisit, ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        return getItemTransferCaches(userVisit).getItemDescriptionTypeDescriptionTransferCache().getTransfer(itemDescriptionTypeDescription);
    }

    public List<ItemDescriptionTypeDescriptionTransfer> getItemDescriptionTypeDescriptionTransfersByItemDescriptionType(UserVisit userVisit, ItemDescriptionType itemDescriptionType) {
        List<ItemDescriptionTypeDescription> itemDescriptionTypeDescriptions = getItemDescriptionTypeDescriptionsByItemDescriptionType(itemDescriptionType);
        List<ItemDescriptionTypeDescriptionTransfer> itemDescriptionTypeDescriptionTransfers = new ArrayList<>(itemDescriptionTypeDescriptions.size());
        ItemDescriptionTypeDescriptionTransferCache itemDescriptionTypeDescriptionTransferCache = getItemTransferCaches(userVisit).getItemDescriptionTypeDescriptionTransferCache();

        itemDescriptionTypeDescriptions.forEach((itemDescriptionTypeDescription) ->
                itemDescriptionTypeDescriptionTransfers.add(itemDescriptionTypeDescriptionTransferCache.getTransfer(itemDescriptionTypeDescription))
        );

        return itemDescriptionTypeDescriptionTransfers;
    }

    public void updateItemDescriptionTypeDescriptionFromValue(ItemDescriptionTypeDescriptionValue itemDescriptionTypeDescriptionValue, BasePK updatedBy) {
        if(itemDescriptionTypeDescriptionValue.hasBeenModified()) {
            ItemDescriptionTypeDescription itemDescriptionTypeDescription = ItemDescriptionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDescriptionTypeDescriptionValue.getPrimaryKey());

            itemDescriptionTypeDescription.setThruTime(session.START_TIME_LONG);
            itemDescriptionTypeDescription.store();

            ItemDescriptionType itemDescriptionType = itemDescriptionTypeDescription.getItemDescriptionType();
            Language language = itemDescriptionTypeDescription.getLanguage();
            String description = itemDescriptionTypeDescriptionValue.getDescription();

            itemDescriptionTypeDescription = ItemDescriptionTypeDescriptionFactory.getInstance().create(itemDescriptionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY.name(), itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteItemDescriptionTypeDescription(ItemDescriptionTypeDescription itemDescriptionTypeDescription, BasePK deletedBy) {
        itemDescriptionTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(itemDescriptionTypeDescription.getItemDescriptionTypePK(), EventTypes.MODIFY.name(), itemDescriptionTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteItemDescriptionTypeDescriptionsByItemDescriptionType(ItemDescriptionType itemDescriptionType, BasePK deletedBy) {
        List<ItemDescriptionTypeDescription> itemDescriptionTypeDescriptions = getItemDescriptionTypeDescriptionsByItemDescriptionTypeForUpdate(itemDescriptionType);

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
        ItemImageDescriptionType itemImageDescriptionType = ItemImageDescriptionTypeFactory.getInstance().create(itemDescriptionType, minimumHeight,
                minimumWidth, maximumHeight, maximumWidth, preferredHeight, preferredWidth, preferredMimeType, quality, scaleFromParent,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY.name(), itemImageDescriptionType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
            ItemImageDescriptionType itemImageDescriptionType = ItemImageDescriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageDescriptionTypeValue.getPrimaryKey());

            itemImageDescriptionType.setThruTime(session.START_TIME_LONG);
            itemImageDescriptionType.store();

            ItemDescriptionTypePK itemDescriptionTypePK = itemImageDescriptionType.getItemDescriptionTypePK(); // Not updated
            Integer minimumHeight = itemImageDescriptionTypeValue.getMinimumHeight();
            Integer minimumWidth = itemImageDescriptionTypeValue.getMinimumWidth();
            Integer maximumHeight = itemImageDescriptionTypeValue.getMaximumHeight();
            Integer maximumWidth = itemImageDescriptionTypeValue.getMaximumWidth();
            Integer preferredHeight = itemImageDescriptionTypeValue.getPreferredHeight();
            Integer preferredWidth = itemImageDescriptionTypeValue.getPreferredWidth();
            MimeTypePK preferredMimeTypePK = itemImageDescriptionTypeValue.getPreferredMimeTypePK();
            Integer quality = itemImageDescriptionTypeValue.getQuality();
            Boolean scaleFromParent = itemImageDescriptionTypeValue.getScaleFromParent();

            itemImageDescriptionType = ItemImageDescriptionTypeFactory.getInstance().create(itemDescriptionTypePK, minimumHeight, minimumWidth, maximumHeight,
                    maximumWidth, preferredHeight, preferredWidth, preferredMimeTypePK, quality, scaleFromParent, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEventUsingNames(itemImageDescriptionType.getItemDescriptionTypePK(), EventTypes.MODIFY.name(), itemImageDescriptionType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteItemImageDescriptionType(ItemImageDescriptionType itemImageDescriptionType, BasePK deletedBy) {
        itemImageDescriptionType.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(itemImageDescriptionType.getItemDescriptionTypePK(), EventTypes.MODIFY.name(), itemImageDescriptionType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteItemImageDescriptionTypeByItemDescriptionType(ItemDescriptionType itemDescriptionType, BasePK deletedBy) {
        ItemImageDescriptionType itemImageDescriptionType = getItemImageDescriptionTypeForUpdate(itemDescriptionType);

        if(itemImageDescriptionType != null) {
            deleteItemImageDescriptionType(itemImageDescriptionType, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Types
    // --------------------------------------------------------------------------------

    public ItemDescriptionTypeUseType createItemDescriptionTypeUseType(String itemDescriptionTypeUseTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ItemDescriptionTypeUseType defaultItemDescriptionTypeUseType = getDefaultItemDescriptionTypeUseType();
        boolean defaultFound = defaultItemDescriptionTypeUseType != null;

        if(defaultFound && isDefault) {
            ItemDescriptionTypeUseTypeDetailValue defaultItemDescriptionTypeUseTypeDetailValue = getDefaultItemDescriptionTypeUseTypeDetailValueForUpdate();

            defaultItemDescriptionTypeUseTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateItemDescriptionTypeUseTypeFromValue(defaultItemDescriptionTypeUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ItemDescriptionTypeUseType itemDescriptionTypeUseType = ItemDescriptionTypeUseTypeFactory.getInstance().create();
        ItemDescriptionTypeUseTypeDetail itemDescriptionTypeUseTypeDetail = ItemDescriptionTypeUseTypeDetailFactory.getInstance().create(itemDescriptionTypeUseType,
                itemDescriptionTypeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemDescriptionTypeUseType = ItemDescriptionTypeUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemDescriptionTypeUseType.getPrimaryKey());
        itemDescriptionTypeUseType.setActiveDetail(itemDescriptionTypeUseTypeDetail);
        itemDescriptionTypeUseType.setLastDetail(itemDescriptionTypeUseTypeDetail);
        itemDescriptionTypeUseType.store();

        sendEventUsingNames(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return itemDescriptionTypeUseType;
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

    private ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByName(String itemDescriptionTypeUseTypeName, EntityPermission entityPermission) {
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

    private ItemDescriptionTypeUseType getDefaultItemDescriptionTypeUseType(EntityPermission entityPermission) {
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
        List<ItemDescriptionTypeUseType> itemDescriptionTypeUseTypes = null;

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

            PreparedStatement ps = ItemDescriptionTypeUseTypeFactory.getInstance().prepareStatement(query);

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
        return getItemTransferCaches(userVisit).getItemDescriptionTypeUseTypeTransferCache().getTransfer(itemDescriptionTypeUseType);
    }

    public List<ItemDescriptionTypeUseTypeTransfer> getItemDescriptionTypeUseTypeTransfers(UserVisit userVisit, List<ItemDescriptionTypeUseType> itemDescriptionTypeUseTypes) {
        List<ItemDescriptionTypeUseTypeTransfer> itemDescriptionTypeUseTypeTransfers = new ArrayList<>(itemDescriptionTypeUseTypes.size());
        ItemDescriptionTypeUseTypeTransferCache itemDescriptionTypeUseTypeTransferCache = getItemTransferCaches(userVisit).getItemDescriptionTypeUseTypeTransferCache();

        itemDescriptionTypeUseTypes.forEach((itemDescriptionTypeUseType) ->
                itemDescriptionTypeUseTypeTransfers.add(itemDescriptionTypeUseTypeTransferCache.getTransfer(itemDescriptionTypeUseType))
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
        List<ItemDescriptionTypeUseType> itemDescriptionTypeUseTypes = getItemDescriptionTypeUseTypes();
        int size = itemDescriptionTypeUseTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemDescriptionTypeUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemDescriptionTypeUseType : itemDescriptionTypeUseTypes) {
            ItemDescriptionTypeUseTypeDetail itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getLastDetail();

            String label = getBestItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language);
            String value = itemDescriptionTypeUseTypeDetail.getItemDescriptionTypeUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultItemDescriptionTypeUseTypeChoice != null && defaultItemDescriptionTypeUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemDescriptionTypeUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemDescriptionTypeUseTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateItemDescriptionTypeUseTypeFromValue(ItemDescriptionTypeUseTypeDetailValue itemDescriptionTypeUseTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemDescriptionTypeUseTypeDetailValue.hasBeenModified()) {
            ItemDescriptionTypeUseType itemDescriptionTypeUseType = ItemDescriptionTypeUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionTypeUseTypeDetailValue.getItemDescriptionTypeUseTypePK());
            ItemDescriptionTypeUseTypeDetail itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getActiveDetailForUpdate();

            itemDescriptionTypeUseTypeDetail.setThruTime(session.START_TIME_LONG);
            itemDescriptionTypeUseTypeDetail.store();

            ItemDescriptionTypeUseTypePK itemDescriptionTypeUseTypePK = itemDescriptionTypeUseTypeDetail.getItemDescriptionTypeUseTypePK(); // Not updated
            String itemDescriptionTypeUseTypeName = itemDescriptionTypeUseTypeDetailValue.getItemDescriptionTypeUseTypeName();
            Boolean isDefault = itemDescriptionTypeUseTypeDetailValue.getIsDefault();
            Integer sortOrder = itemDescriptionTypeUseTypeDetailValue.getSortOrder();

            if(checkDefault) {
                ItemDescriptionTypeUseType defaultItemDescriptionTypeUseType = getDefaultItemDescriptionTypeUseType();
                boolean defaultFound = defaultItemDescriptionTypeUseType != null && !defaultItemDescriptionTypeUseType.equals(itemDescriptionTypeUseType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ItemDescriptionTypeUseTypeDetailValue defaultItemDescriptionTypeUseTypeDetailValue = getDefaultItemDescriptionTypeUseTypeDetailValueForUpdate();

                    defaultItemDescriptionTypeUseTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateItemDescriptionTypeUseTypeFromValue(defaultItemDescriptionTypeUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            itemDescriptionTypeUseTypeDetail = ItemDescriptionTypeUseTypeDetailFactory.getInstance().create(itemDescriptionTypeUseTypePK,
                    itemDescriptionTypeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            itemDescriptionTypeUseType.setActiveDetail(itemDescriptionTypeUseTypeDetail);
            itemDescriptionTypeUseType.setLastDetail(itemDescriptionTypeUseTypeDetail);

            sendEventUsingNames(itemDescriptionTypeUseTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateItemDescriptionTypeUseTypeFromValue(ItemDescriptionTypeUseTypeDetailValue itemDescriptionTypeUseTypeDetailValue, BasePK updatedBy) {
        updateItemDescriptionTypeUseTypeFromValue(itemDescriptionTypeUseTypeDetailValue, true, updatedBy);
    }

    public void deleteItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType, BasePK deletedBy) {
        deleteItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(itemDescriptionTypeUseType, deletedBy);
        deleteItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType, deletedBy);

        ItemDescriptionTypeUseTypeDetail itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getLastDetailForUpdate();
        itemDescriptionTypeUseTypeDetail.setThruTime(session.START_TIME_LONG);
        itemDescriptionTypeUseType.setActiveDetail(null);
        itemDescriptionTypeUseType.store();

        // Check for default, and pick one if necessary
        ItemDescriptionTypeUseType defaultItemDescriptionTypeUseType = getDefaultItemDescriptionTypeUseType();
        if(defaultItemDescriptionTypeUseType == null) {
            List<ItemDescriptionTypeUseType> itemDescriptionTypeUseTypes = getItemDescriptionTypeUseTypesForUpdate();

            if(!itemDescriptionTypeUseTypes.isEmpty()) {
                Iterator<ItemDescriptionTypeUseType> iter = itemDescriptionTypeUseTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemDescriptionTypeUseType = iter.next();
                }
                ItemDescriptionTypeUseTypeDetailValue itemDescriptionTypeUseTypeDetailValue = Objects.requireNonNull(defaultItemDescriptionTypeUseType).getLastDetailForUpdate().getItemDescriptionTypeUseTypeDetailValue().clone();

                itemDescriptionTypeUseTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateItemDescriptionTypeUseTypeFromValue(itemDescriptionTypeUseTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    public ItemDescriptionTypeUseTypeDescription createItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            Language language, String description, BasePK createdBy) {
        ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription = ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().create(itemDescriptionTypeUseType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.MODIFY.name(), itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription = getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language);

        if(itemDescriptionTypeUseTypeDescription == null && !language.getIsDefault()) {
            itemDescriptionTypeUseTypeDescription = getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, getPartyControl().getDefaultLanguage());
        }

        if(itemDescriptionTypeUseTypeDescription == null) {
            description = itemDescriptionTypeUseType.getLastDetail().getItemDescriptionTypeUseTypeName();
        } else {
            description = itemDescriptionTypeUseTypeDescription.getDescription();
        }

        return description;
    }

    public ItemDescriptionTypeUseTypeDescriptionTransfer getItemDescriptionTypeUseTypeDescriptionTransfer(UserVisit userVisit, ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        return getItemTransferCaches(userVisit).getItemDescriptionTypeUseTypeDescriptionTransferCache().getTransfer(itemDescriptionTypeUseTypeDescription);
    }

    public List<ItemDescriptionTypeUseTypeDescriptionTransfer> getItemDescriptionTypeUseTypeDescriptionTransfersByItemDescriptionTypeUseType(UserVisit userVisit, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        List<ItemDescriptionTypeUseTypeDescription> itemDescriptionTypeUseTypeDescriptions = getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(itemDescriptionTypeUseType);
        List<ItemDescriptionTypeUseTypeDescriptionTransfer> itemDescriptionTypeUseTypeDescriptionTransfers = new ArrayList<>(itemDescriptionTypeUseTypeDescriptions.size());
        ItemDescriptionTypeUseTypeDescriptionTransferCache itemDescriptionTypeUseTypeDescriptionTransferCache = getItemTransferCaches(userVisit).getItemDescriptionTypeUseTypeDescriptionTransferCache();

        itemDescriptionTypeUseTypeDescriptions.forEach((itemDescriptionTypeUseTypeDescription) ->
                itemDescriptionTypeUseTypeDescriptionTransfers.add(itemDescriptionTypeUseTypeDescriptionTransferCache.getTransfer(itemDescriptionTypeUseTypeDescription))
        );

        return itemDescriptionTypeUseTypeDescriptionTransfers;
    }

    public void updateItemDescriptionTypeUseTypeDescriptionFromValue(ItemDescriptionTypeUseTypeDescriptionValue itemDescriptionTypeUseTypeDescriptionValue, BasePK updatedBy) {
        if(itemDescriptionTypeUseTypeDescriptionValue.hasBeenModified()) {
            ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription = ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemDescriptionTypeUseTypeDescriptionValue.getPrimaryKey());

            itemDescriptionTypeUseTypeDescription.setThruTime(session.START_TIME_LONG);
            itemDescriptionTypeUseTypeDescription.store();

            ItemDescriptionTypeUseType itemDescriptionTypeUseType = itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseType();
            Language language = itemDescriptionTypeUseTypeDescription.getLanguage();
            String description = itemDescriptionTypeUseTypeDescriptionValue.getDescription();

            itemDescriptionTypeUseTypeDescription = ItemDescriptionTypeUseTypeDescriptionFactory.getInstance().create(itemDescriptionTypeUseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.MODIFY.name(), itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteItemDescriptionTypeUseTypeDescription(ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription, BasePK deletedBy) {
        itemDescriptionTypeUseTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseTypePK(), EventTypes.MODIFY.name(), itemDescriptionTypeUseTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseType(ItemDescriptionTypeUseType itemDescriptionTypeUseType, BasePK deletedBy) {
        List<ItemDescriptionTypeUseTypeDescription> itemDescriptionTypeUseTypeDescriptions = getItemDescriptionTypeUseTypeDescriptionsByItemDescriptionTypeUseTypeForUpdate(itemDescriptionTypeUseType);

        itemDescriptionTypeUseTypeDescriptions.forEach((itemDescriptionTypeUseTypeDescription) -> 
                deleteItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Uses
    // --------------------------------------------------------------------------------

    public ItemDescriptionTypeUse createItemDescriptionTypeUse(ItemDescriptionType itemDescriptionType, ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            BasePK createdBy) {
        ItemDescriptionTypeUse itemDescriptionTypeUse = ItemDescriptionTypeUseFactory.getInstance().create(itemDescriptionType, itemDescriptionTypeUseType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(itemDescriptionType.getPrimaryKey(), EventTypes.MODIFY.name(), itemDescriptionTypeUse.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return itemDescriptionTypeUse;
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
        return getItemTransferCaches(userVisit).getItemDescriptionTypeUseTransferCache().getTransfer(itemDescriptionTypeUse);
    }

    private List<ItemDescriptionTypeUseTransfer> getItemDescriptionTypeUseTransfers(final UserVisit userVisit, final List<ItemDescriptionTypeUse> itemDescriptionTypeUses) {
        List<ItemDescriptionTypeUseTransfer> itemDescriptionTypeUseTransfers = new ArrayList<>(itemDescriptionTypeUses.size());
        ItemDescriptionTypeUseTransferCache itemDescriptionTypeUseTransferCache = getItemTransferCaches(userVisit).getItemDescriptionTypeUseTransferCache();

        itemDescriptionTypeUses.forEach((itemDescriptionTypeUse) ->
                itemDescriptionTypeUseTransfers.add(itemDescriptionTypeUseTransferCache.getTransfer(itemDescriptionTypeUse))
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

        sendEventUsingNames(itemDescriptionTypeUse.getItemDescriptionTypePK(), EventTypes.MODIFY.name(), itemDescriptionTypeUse.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        ItemImageType defaultItemImageType = getDefaultItemImageType();
        boolean defaultFound = defaultItemImageType != null;

        if(defaultFound && isDefault) {
            ItemImageTypeDetailValue defaultItemImageTypeDetailValue = getDefaultItemImageTypeDetailValueForUpdate();

            defaultItemImageTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateItemImageTypeFromValue(defaultItemImageTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ItemImageType itemImageType = ItemImageTypeFactory.getInstance().create();
        ItemImageTypeDetail itemImageTypeDetail = ItemImageTypeDetailFactory.getInstance().create(itemImageType, itemImageTypeName, preferredMimeType, quality,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        itemImageType = ItemImageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemImageType.getPrimaryKey());
        itemImageType.setActiveDetail(itemImageTypeDetail);
        itemImageType.setLastDetail(itemImageTypeDetail);
        itemImageType.store();

        sendEventUsingNames(itemImageType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return itemImageType;
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

    private ItemImageType getItemImageTypeByName(String itemImageTypeName, EntityPermission entityPermission) {
        return ItemImageTypeFactory.getInstance().getEntityFromQuery(entityPermission, getItemImageTypeByNameQueries, itemImageTypeName);
    }

    public ItemImageType getItemImageTypeByName(String itemImageTypeName) {
        return getItemImageTypeByName(itemImageTypeName, EntityPermission.READ_ONLY);
    }

    public ItemImageType getItemImageTypeByNameForUpdate(String itemImageTypeName) {
        return getItemImageTypeByName(itemImageTypeName, EntityPermission.READ_WRITE);
    }

    public ItemImageTypeDetailValue getItemImageTypeDetailValueForUpdate(ItemImageType itemImageType) {
        return itemImageType == null? null: itemImageType.getLastDetailForUpdate().getItemImageTypeDetailValue().clone();
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

    private ItemImageType getDefaultItemImageType(EntityPermission entityPermission) {
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

    private List<ItemImageType> getItemImageTypesByParentItemImageType(ItemImageType parentItemImageType,
            EntityPermission entityPermission) {
        List<ItemImageType> itemImageTypes = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemimagetypes, itemimagetypedetails " +
                        "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid AND iimgtdt_parentitemimagetypeid = ? " +
                        "ORDER BY iimgtdt_sortorder, iimgtdt_itemimagetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemimagetypes, itemimagetypedetails " +
                        "WHERE iimgt_activedetailid = iimgtdt_itemimagetypedetailid AND iimgtdt_parentitemimagetypeid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ItemImageTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, parentItemImageType.getPrimaryKey().getEntityId());

            itemImageTypes = ItemImageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemImageTypes;
    }

    public List<ItemImageType> getItemImageTypesByParentItemImageType(ItemImageType parentItemImageType) {
        return getItemImageTypesByParentItemImageType(parentItemImageType, EntityPermission.READ_ONLY);
    }

    public List<ItemImageType> getItemImageTypesByParentItemImageTypeForUpdate(ItemImageType parentItemImageType) {
        return getItemImageTypesByParentItemImageType(parentItemImageType, EntityPermission.READ_WRITE);
    }

    public ItemImageTypeTransfer getItemImageTypeTransfer(UserVisit userVisit, ItemImageType itemImageType) {
        return getItemTransferCaches(userVisit).getItemImageTypeTransferCache().getTransfer(itemImageType);
    }

    public List<ItemImageTypeTransfer> getItemImageTypeTransfers(UserVisit userVisit, List<ItemImageType> itemImageTypes) {
        List<ItemImageTypeTransfer> itemImageTypeTransfers = new ArrayList<>(itemImageTypes.size());
        ItemImageTypeTransferCache itemImageTypeTransferCache = getItemTransferCaches(userVisit).getItemImageTypeTransferCache();

        itemImageTypes.forEach((itemImageType) ->
                itemImageTypeTransfers.add(itemImageTypeTransferCache.getTransfer(itemImageType))
        );

        return itemImageTypeTransfers;
    }

    public List<ItemImageTypeTransfer> getItemImageTypeTransfers(UserVisit userVisit) {
        return getItemImageTypeTransfers(userVisit, getItemImageTypes());
    }

    public List<ItemImageTypeTransfer> getItemImageTypeTransfersByParentItemImageType(UserVisit userVisit,
            ItemImageType parentItemImageType) {
        return getItemImageTypeTransfers(userVisit, getItemImageTypesByParentItemImageType(parentItemImageType));
    }

    public ItemImageTypeChoicesBean getItemImageTypeChoices(String defaultItemImageTypeChoice, Language language, boolean allowNullChoice) {
        List<ItemImageType> itemImageTypes = getItemImageTypes();
        int size = itemImageTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultItemImageTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var itemImageType : itemImageTypes) {
            ItemImageTypeDetail itemImageTypeDetail = itemImageType.getLastDetail();

            String label = getBestItemImageTypeDescription(itemImageType, language);
            String value = itemImageTypeDetail.getItemImageTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultItemImageTypeChoice != null && defaultItemImageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemImageTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ItemImageTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateItemImageTypeFromValue(ItemImageTypeDetailValue itemImageTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemImageTypeDetailValue.hasBeenModified()) {
            ItemImageType itemImageType = ItemImageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageTypeDetailValue.getItemImageTypePK());
            ItemImageTypeDetail itemImageTypeDetail = itemImageType.getActiveDetailForUpdate();

            itemImageTypeDetail.setThruTime(session.START_TIME_LONG);
            itemImageTypeDetail.store();

            ItemImageTypePK itemImageTypePK = itemImageTypeDetail.getItemImageTypePK(); // Not updated
            String itemImageTypeName = itemImageTypeDetailValue.getItemImageTypeName();
            MimeTypePK preferredMimeTypePK = itemImageTypeDetailValue.getPreferredMimeTypePK();
            Integer quality = itemImageTypeDetailValue.getQuality();
            Boolean isDefault = itemImageTypeDetailValue.getIsDefault();
            Integer sortOrder = itemImageTypeDetailValue.getSortOrder();

            if(checkDefault) {
                ItemImageType defaultItemImageType = getDefaultItemImageType();
                boolean defaultFound = defaultItemImageType != null && !defaultItemImageType.equals(itemImageType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ItemImageTypeDetailValue defaultItemImageTypeDetailValue = getDefaultItemImageTypeDetailValueForUpdate();

                    defaultItemImageTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateItemImageTypeFromValue(defaultItemImageTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            itemImageTypeDetail = ItemImageTypeDetailFactory.getInstance().create(itemImageTypePK, itemImageTypeName, preferredMimeTypePK, quality, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            itemImageType.setActiveDetail(itemImageTypeDetail);
            itemImageType.setLastDetail(itemImageTypeDetail);

            sendEventUsingNames(itemImageTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    /** Use the version located in ItemDescriptionLogic instead. */
    public void updateItemImageTypeFromValue(ItemImageTypeDetailValue itemImageTypeDetailValue, BasePK updatedBy) {
        updateItemImageTypeFromValue(itemImageTypeDetailValue, true, updatedBy);
    }

    public void deleteItemImageType(ItemImageType itemImageType, BasePK deletedBy) {
        deleteItemImageTypeDescriptionsByItemImageType(itemImageType, deletedBy);
        deleteItemDescriptionsByItemImageType(itemImageType, deletedBy);

        ItemImageTypeDetail itemImageTypeDetail = itemImageType.getLastDetailForUpdate();
        itemImageTypeDetail.setThruTime(session.START_TIME_LONG);
        itemImageType.setActiveDetail(null);
        itemImageType.store();

        // Check for default, and pick one if necessary
        ItemImageType defaultItemImageType = getDefaultItemImageType();
        if(defaultItemImageType == null) {
            List<ItemImageType> itemImageTypes = getItemImageTypesForUpdate();

            if(!itemImageTypes.isEmpty()) {
                Iterator<ItemImageType> iter = itemImageTypes.iterator();
                if(iter.hasNext()) {
                    defaultItemImageType = iter.next();
                }
                ItemImageTypeDetailValue itemImageTypeDetailValue = Objects.requireNonNull(defaultItemImageType).getLastDetailForUpdate().getItemImageTypeDetailValue().clone();

                itemImageTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateItemImageTypeFromValue(itemImageTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(itemImageType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Description Type Use Type Descriptions
    // --------------------------------------------------------------------------------

    public ItemImageTypeDescription createItemImageTypeDescription(ItemImageType itemImageType,
            Language language, String description, BasePK createdBy) {
        ItemImageTypeDescription itemImageTypeDescription = ItemImageTypeDescriptionFactory.getInstance().create(itemImageType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(itemImageType.getPrimaryKey(), EventTypes.MODIFY.name(), itemImageTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        ItemImageTypeDescription itemImageTypeDescription = getItemImageTypeDescription(itemImageType, language);

        if(itemImageTypeDescription == null && !language.getIsDefault()) {
            itemImageTypeDescription = getItemImageTypeDescription(itemImageType, getPartyControl().getDefaultLanguage());
        }

        if(itemImageTypeDescription == null) {
            description = itemImageType.getLastDetail().getItemImageTypeName();
        } else {
            description = itemImageTypeDescription.getDescription();
        }

        return description;
    }

    public ItemImageTypeDescriptionTransfer getItemImageTypeDescriptionTransfer(UserVisit userVisit, ItemImageTypeDescription itemImageTypeDescription) {
        return getItemTransferCaches(userVisit).getItemImageTypeDescriptionTransferCache().getTransfer(itemImageTypeDescription);
    }

    public List<ItemImageTypeDescriptionTransfer> getItemImageTypeDescriptionTransfersByItemImageType(UserVisit userVisit, ItemImageType itemImageType) {
        List<ItemImageTypeDescription> itemImageTypeDescriptions = getItemImageTypeDescriptionsByItemImageType(itemImageType);
        List<ItemImageTypeDescriptionTransfer> itemImageTypeDescriptionTransfers = new ArrayList<>(itemImageTypeDescriptions.size());
        ItemImageTypeDescriptionTransferCache itemImageTypeDescriptionTransferCache = getItemTransferCaches(userVisit).getItemImageTypeDescriptionTransferCache();

        itemImageTypeDescriptions.forEach((itemImageTypeDescription) ->
                itemImageTypeDescriptionTransfers.add(itemImageTypeDescriptionTransferCache.getTransfer(itemImageTypeDescription))
        );

        return itemImageTypeDescriptionTransfers;
    }

    public void updateItemImageTypeDescriptionFromValue(ItemImageTypeDescriptionValue itemImageTypeDescriptionValue, BasePK updatedBy) {
        if(itemImageTypeDescriptionValue.hasBeenModified()) {
            ItemImageTypeDescription itemImageTypeDescription = ItemImageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemImageTypeDescriptionValue.getPrimaryKey());

            itemImageTypeDescription.setThruTime(session.START_TIME_LONG);
            itemImageTypeDescription.store();

            ItemImageType itemImageType = itemImageTypeDescription.getItemImageType();
            Language language = itemImageTypeDescription.getLanguage();
            String description = itemImageTypeDescriptionValue.getDescription();

            itemImageTypeDescription = ItemImageTypeDescriptionFactory.getInstance().create(itemImageType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(itemImageType.getPrimaryKey(), EventTypes.MODIFY.name(), itemImageTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteItemImageTypeDescription(ItemImageTypeDescription itemImageTypeDescription, BasePK deletedBy) {
        itemImageTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(itemImageTypeDescription.getItemImageTypePK(), EventTypes.MODIFY.name(), itemImageTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteItemImageTypeDescriptionsByItemImageType(ItemImageType itemImageType, BasePK deletedBy) {
        List<ItemImageTypeDescription> itemImageTypeDescriptions = getItemImageTypeDescriptionsByItemImageTypeForUpdate(itemImageType);

        itemImageTypeDescriptions.forEach((itemImageTypeDescription) -> 
                deleteItemImageTypeDescription(itemImageTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemDescription createItemDescription(ItemDescriptionType itemDescriptionType, Item item, Language language,
            MimeType mimeType, BasePK createdBy) {
        ItemDescription itemDescription = ItemDescriptionFactory.getInstance().create();
        ItemDescriptionDetail itemDescriptionDetail = ItemDescriptionDetailFactory.getInstance().create(itemDescription,
                itemDescriptionType, item, language, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemDescription = ItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemDescription.getPrimaryKey());
        itemDescription.setActiveDetail(itemDescriptionDetail);
        itemDescription.setLastDetail(itemDescriptionDetail);
        itemDescription.store();
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return itemDescription;
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

    private List<ItemDescription> getItemDescriptionsByItem(Item item, EntityPermission entityPermission) {
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

            PreparedStatement ps = ItemDescriptionFactory.getInstance().prepareStatement(query);

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

            PreparedStatement ps = ItemDescriptionFactory.getInstance().prepareStatement(query);

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
            
            PreparedStatement ps = ItemDescriptionFactory.getInstance().prepareStatement(query);
            
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
        ItemDescription itemDescription = null;
        ItemDescriptionType attemptedItemDescriptionType = itemDescriptionType;
        
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
        ItemDescription itemDescription = getBestItemDescriptionWithinLanguage(itemDescriptionType, item, language);

        if(itemDescription == null && !language.getIsDefault()) {
            itemDescription = getBestItemDescriptionWithinLanguage(itemDescriptionType, item, getPartyControl().getDefaultLanguage());
        }
        
        return itemDescription;
    }
    
    public String getBestItemStringDescription(ItemDescriptionType itemDescriptionType, Item item, Language language) {
        String description = null;
        ItemDescription itemDescription = getBestItemDescription(itemDescriptionType, item, language);
        
        if(itemDescription != null) {
            ItemStringDescription itemStringDescription = getItemStringDescription(itemDescription);
            description = itemStringDescription == null? null: itemStringDescription.getStringDescription();
        }
        
        if(description == null) {
            description = item.getLastDetail().getItemName();
        }
        
        return description;
    }
    
    public ItemDescriptionTransfer getItemDescriptionTransfer(UserVisit userVisit, ItemDescription itemDescription) {
        return getItemTransferCaches(userVisit).getItemDescriptionTransferCache().getTransfer(itemDescription);
    }
    
    public List<ItemDescriptionTransfer> getItemDescriptionTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemDescription> itemDescriptions = getItemDescriptionsByItem(item);
        List<ItemDescriptionTransfer> itemDescriptionTransfers = new ArrayList<>(itemDescriptions.size());
        ItemDescriptionTransferCache itemDescriptionTransferCache = getItemTransferCaches(userVisit).getItemDescriptionTransferCache();
        
        itemDescriptions.forEach((itemDescription) ->
                itemDescriptionTransfers.add(itemDescriptionTransferCache.getTransfer(itemDescription))
        );
        
        return itemDescriptionTransfers;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ItemDescription */
    public ItemDescription getItemDescriptionByEntityInstance(EntityInstance entityInstance) {
        ItemDescriptionPK pk = new ItemDescriptionPK(entityInstance.getEntityUniqueId());
        ItemDescription itemDescription = ItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return itemDescription;
    }

    public void updateItemDescriptionFromValue(ItemDescriptionDetailValue itemDescriptionDetailValue, BasePK updatedBy) {
        if(itemDescriptionDetailValue.hasBeenModified()) {
            ItemDescription itemDescription = ItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemDescriptionDetailValue.getItemDescriptionPK());
            ItemDescriptionDetail itemDescriptionDetail = itemDescription.getActiveDetailForUpdate();
            
            itemDescriptionDetail.setThruTime(session.START_TIME_LONG);
            itemDescriptionDetail.store();
            
            ItemDescriptionPK itemDescriptionPK = itemDescriptionDetail.getItemDescriptionPK(); // Not updated
            ItemDescriptionTypePK itemDescriptionTypePK = itemDescriptionDetailValue.getItemDescriptionTypePK();
            ItemPK itemPK = itemDescriptionDetailValue.getItemPK();
            LanguagePK languagePK = itemDescriptionDetailValue.getLanguagePK();
            MimeTypePK mimeTypePK = itemDescriptionDetailValue.getMimeTypePK();
            
            itemDescriptionDetail = ItemDescriptionDetailFactory.getInstance().create(itemDescriptionPK,
                    itemDescriptionTypePK, itemPK, languagePK, mimeTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            itemDescription.setActiveDetail(itemDescriptionDetail);
            itemDescription.setLastDetail(itemDescriptionDetail);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemDescriptionPK, EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        ItemDescriptionDetail itemDescriptionDetail = itemDescription.getLastDetailForUpdate();
        MimeType mimeType = itemDescriptionDetail.getMimeType();
        
        if(mimeType == null) {
            deleteItemStringDescriptionByItemDescription(itemDescription, deletedBy);
        } else {
            String entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            
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
        
        sendEventUsingNames(itemDescriptionDetail.getItemPK(), EventTypes.MODIFY.name(), itemDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        List<ItemImageDescription> itemImageDescriptions = getItemImageDescriptionsByItemImageTypeForUpdate(itemImageType);

        itemImageDescriptions.forEach((itemImageDescription) -> {
            deleteItemDescription(itemImageDescription.getItemDescription(), deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Item Blob Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemBlobDescription createItemBlobDescription(ItemDescription itemDescription, ByteArray blobDescription,
            BasePK createdBy) {
        ItemBlobDescription itemBlobDescription = ItemBlobDescriptionFactory.getInstance().create(itemDescription,
                blobDescription, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemBlobDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemBlobDescriptionFactory.getInstance().prepareStatement(query);
            
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
            ItemBlobDescription itemBlobDescription = ItemBlobDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemBlobDescriptionValue.getPrimaryKey());
            
            itemBlobDescription.setThruTime(session.START_TIME_LONG);
            itemBlobDescription.store();
            
            ItemDescriptionPK itemDescriptionPK = itemBlobDescription.getItemDescriptionPK(); // Not updated
            ByteArray blobDescription = itemBlobDescriptionValue.getBlobDescription();
            
            itemBlobDescription = ItemBlobDescriptionFactory.getInstance().create(itemDescriptionPK, blobDescription,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemBlobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemBlobDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemBlobDescription(ItemBlobDescription itemBlobDescription, BasePK deletedBy) {
        itemBlobDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemBlobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemBlobDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteItemBlobDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        ItemBlobDescription itemBlobDescription = getItemBlobDescriptionForUpdate(itemDescription);
        
        if(itemBlobDescription != null) {
            deleteItemBlobDescription(itemBlobDescription, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Item Image Descriptions
    // --------------------------------------------------------------------------------


    public ItemImageDescription createItemImageDescription(ItemDescription itemDescription, ItemImageType itemImageType, Integer height, Integer width,
            Boolean scaledFromParent, BasePK createdBy) {
        ItemImageDescription itemImageDescription = ItemImageDescriptionFactory.getInstance().create(itemDescription, itemImageType, height, width,
                scaledFromParent, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemImageDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
            ItemImageDescription itemImageDescription = ItemImageDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemImageDescriptionValue.getPrimaryKey());

            itemImageDescription.setThruTime(session.START_TIME_LONG);
            itemImageDescription.store();

            ItemDescriptionPK itemDescriptionPK = itemImageDescription.getItemDescriptionPK(); // Not updated
            ItemImageTypePK itemImageTypePK = itemImageDescriptionValue.getItemImageTypePK();
            Integer height = itemImageDescriptionValue.getHeight();
            Integer width = itemImageDescriptionValue.getWidth();
            Boolean scaledFromParent = itemImageDescriptionValue.getScaledFromParent();

            itemImageDescription = ItemImageDescriptionFactory.getInstance().create(itemDescriptionPK, itemImageTypePK, height, width, scaledFromParent,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(itemImageDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemImageDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteItemImageDescription(ItemImageDescription itemImageDescription, BasePK deletedBy) {
        itemImageDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(itemImageDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemImageDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteItemImageDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        ItemImageDescription itemImageDescription = getItemImageDescriptionForUpdate(itemDescription);

        if(itemImageDescription != null) {
            deleteItemImageDescription(itemImageDescription, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Item Clob Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemClobDescription createItemClobDescription(ItemDescription itemDescription, String clobDescription,
            BasePK createdBy) {
        ItemClobDescription itemClobDescription = ItemClobDescriptionFactory.getInstance().create(itemDescription,
                clobDescription, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemClobDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemClobDescriptionFactory.getInstance().prepareStatement(query);
            
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
            ItemClobDescription itemClobDescription = ItemClobDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemClobDescriptionValue.getPrimaryKey());
            
            itemClobDescription.setThruTime(session.START_TIME_LONG);
            itemClobDescription.store();
            
            ItemDescriptionPK itemDescriptionPK = itemClobDescription.getItemDescriptionPK(); // Not updated
            String clobDescription = itemClobDescriptionValue.getClobDescription();
            
            itemClobDescription = ItemClobDescriptionFactory.getInstance().create(itemDescriptionPK, clobDescription,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemClobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemClobDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemClobDescription(ItemClobDescription itemClobDescription, BasePK deletedBy) {
        itemClobDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemClobDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemClobDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteItemClobDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        ItemClobDescription itemClobDescription = getItemClobDescriptionForUpdate(itemDescription);
        
        if(itemClobDescription != null) {
            deleteItemClobDescription(itemClobDescription, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Item String Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemStringDescription createItemStringDescription(ItemDescription itemDescription, String stringDescription,
            BasePK createdBy) {
        ItemStringDescription itemStringDescription = ItemStringDescriptionFactory.getInstance().create(itemDescription,
                stringDescription, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemDescription.getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemStringDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = ItemStringDescriptionFactory.getInstance().prepareStatement(query);
            
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
            ItemStringDescription itemStringDescription = ItemStringDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemStringDescriptionValue.getPrimaryKey());
            
            itemStringDescription.setThruTime(session.START_TIME_LONG);
            itemStringDescription.store();
            
            ItemDescriptionPK itemDescriptionPK = itemStringDescription.getItemDescriptionPK(); // Not updated
            String stringDescription = itemStringDescriptionValue.getStringDescription();
            
            itemStringDescription = ItemStringDescriptionFactory.getInstance().create(itemDescriptionPK, stringDescription,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemStringDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemStringDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemStringDescription(ItemStringDescription itemStringDescription, BasePK deletedBy) {
        itemStringDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemStringDescription.getItemDescription().getLastDetail().getItemPK(), EventTypes.MODIFY.name(), itemStringDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteItemStringDescriptionByItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        ItemStringDescription itemStringDescription = getItemStringDescriptionForUpdate(itemDescription);
        
        if(itemStringDescription != null) {
            deleteItemStringDescription(itemStringDescription, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Item Volumes
    // --------------------------------------------------------------------------------
    
    public ItemVolume createItemVolume(Item item, UnitOfMeasureType unitOfMeasureType, Long height, Long width, Long depth,
            BasePK createdBy) {
        ItemVolume itemVolume = ItemVolumeFactory.getInstance().create(item, unitOfMeasureType, height, width, depth,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemVolume.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return itemVolume;
    }
    
    private ItemVolume getItemVolume(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        ItemVolume itemVolume;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_uomt_unitofmeasuretypeid = ? AND ivol_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_uomt_unitofmeasuretypeid = ? AND ivol_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemVolumeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemVolume = ItemVolumeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemVolume;
    }
    
    public ItemVolume getItemVolume(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolume(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public ItemVolume getItemVolumeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolume(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public ItemVolumeValue getItemVolumeValue(ItemVolume itemVolume) {
        return itemVolume == null? null: itemVolume.getItemVolumeValue().clone();
    }

    public ItemVolumeValue getItemVolumeValueForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolumeForUpdate(item, unitOfMeasureType).getItemVolumeValue().clone();
    }
    
    private List<ItemVolume> getItemVolumesByItem(Item item, EntityPermission entityPermission) {
        List<ItemVolume> itemVolumes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes, unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE ivol_itm_itemid = ? AND ivol_thrutime = ? " +
                        "AND ivol_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid " +
                        "AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemvolumes " +
                        "WHERE ivol_itm_itemid = ? AND ivol_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemVolumeFactory.getInstance().prepareStatement(query);
            
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
    
    public void updateItemVolumeFromValue(ItemVolumeValue itemVolumeValue, BasePK updatedBy) {
        if(itemVolumeValue.hasBeenModified()) {
            ItemVolume itemVolume = ItemVolumeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemVolumeValue.getPrimaryKey());
            
            itemVolume.setThruTime(session.START_TIME_LONG);
            itemVolume.store();
            
            ItemPK itemPK = itemVolume.getItemPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemVolume.getUnitOfMeasureTypePK();
            Long height = itemVolumeValue.getHeight();
            Long width = itemVolumeValue.getWidth();
            Long depth = itemVolumeValue.getDepth();
            
            itemVolume = ItemVolumeFactory.getInstance().create(itemPK, unitOfMeasureTypePK, height, width, depth,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemVolume.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemVolumeTransfer getItemVolumeTransfer(UserVisit userVisit, ItemVolume itemVolume) {
        return itemVolume == null? null: getItemTransferCaches(userVisit).getItemVolumeTransferCache().getTransfer(itemVolume);
    }
    
    public ItemVolumeTransfer getItemVolumeTransfer(UserVisit userVisit, Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemVolumeTransfer(userVisit, getItemVolume(item, unitOfMeasureType));
    }
    
    public List<ItemVolumeTransfer> getItemVolumeTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemVolume> itemVolumes = getItemVolumesByItem(item);
        List<ItemVolumeTransfer> itemVolumeTransfers = new ArrayList<>(itemVolumes.size());
        ItemVolumeTransferCache itemVolumeTransferCache = getItemTransferCaches(userVisit).getItemVolumeTransferCache();
        
        itemVolumes.forEach((itemVolume) ->
                itemVolumeTransfers.add(itemVolumeTransferCache.getTransfer(itemVolume))
        );
        
        return itemVolumeTransfers;
    }
    
    public void deleteItemVolume(ItemVolume itemVolume, BasePK deletedBy) {
        itemVolume.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemVolume.getItemPK(), EventTypes.MODIFY.name(), itemVolume.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteItemVolumesByItem(Item item, BasePK deletedBy) {
        List<ItemVolume> itemVolumes = getItemVolumesByItemForUpdate(item);
        
        itemVolumes.forEach((itemVolume) -> 
                deleteItemVolume(itemVolume, deletedBy)
        );
    }
    
    public void deleteItemVolumeByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        ItemVolume itemVolume = getItemVolumeForUpdate(item, unitOfMeasureType);
        
        if(itemVolume!= null) {
            deleteItemVolume(itemVolume, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Item Weights
    // --------------------------------------------------------------------------------
    
    public ItemWeight createItemWeight(Item item, UnitOfMeasureType unitOfMeasureType, Long weight, BasePK createdBy) {
        ItemWeight itemWeight = ItemWeightFactory.getInstance().create(item, unitOfMeasureType, weight,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemWeight.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return itemWeight;
    }
    
    private ItemWeight getItemWeight(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        ItemWeight itemWeight;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_uomt_unitofmeasuretypeid = ? AND iwght_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_uomt_unitofmeasuretypeid = ? AND iwght_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemWeightFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemWeight = ItemWeightFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemWeight;
    }
    
    public ItemWeight getItemWeight(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeight(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public ItemWeight getItemWeightForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeight(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public ItemWeightValue getItemWeightValue(ItemWeight itemWeight) {
        return itemWeight == null? null: itemWeight.getItemWeightValue().clone();
    }

    public ItemWeightValue getItemWeightValueForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeightForUpdate(item, unitOfMeasureType).getItemWeightValue().clone();
    }
    
    private List<ItemWeight> getItemWeightsByItem(Item item, EntityPermission entityPermission) {
        List<ItemWeight> itemWeights;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights, unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE iwght_itm_itemid = ? AND iwght_thrutime = ? " +
                        "AND iwght_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid " +
                        "AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemweights " +
                        "WHERE iwght_itm_itemid = ? AND iwght_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemWeightFactory.getInstance().prepareStatement(query);
            
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
    
    public void updateItemWeightFromValue(ItemWeightValue itemWeightValue, BasePK updatedBy) {
        if(itemWeightValue.hasBeenModified()) {
            ItemWeight itemWeight = ItemWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    itemWeightValue.getPrimaryKey());
            
            itemWeight.setThruTime(session.START_TIME_LONG);
            itemWeight.store();
            
            ItemPK itemPK = itemWeight.getItemPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = itemWeight.getUnitOfMeasureTypePK();
            Long weight = itemWeightValue.getWeight();
            
            itemWeight = ItemWeightFactory.getInstance().create(itemPK, unitOfMeasureTypePK, weight, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemWeight.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public ItemWeightTransfer getItemWeightTransfer(UserVisit userVisit, ItemWeight itemWeight) {
        return itemWeight == null? null: getItemTransferCaches(userVisit).getItemWeightTransferCache().getTransfer(itemWeight);
    }
    
    public ItemWeightTransfer getItemWeightTransfer(UserVisit userVisit, Item item, UnitOfMeasureType unitOfMeasureType) {
        return getItemWeightTransfer(userVisit, getItemWeight(item, unitOfMeasureType));
    }
    
    public List<ItemWeightTransfer> getItemWeightTransfersByItem(UserVisit userVisit, Item item) {
        List<ItemWeight> itemWeights = getItemWeightsByItem(item);
        List<ItemWeightTransfer> itemWeightTransfers = new ArrayList<>(itemWeights.size());
        ItemWeightTransferCache itemWeightTransferCache = getItemTransferCaches(userVisit).getItemWeightTransferCache();
        
        itemWeights.forEach((itemWeight) ->
                itemWeightTransfers.add(itemWeightTransferCache.getTransfer(itemWeight))
        );
        
        return itemWeightTransfers;
    }
    
    public void deleteItemWeight(ItemWeight itemWeight, BasePK deletedBy) {
        itemWeight.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemWeight.getItemPK(), EventTypes.MODIFY.name(), itemWeight.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteItemWeightsByItem(Item item, BasePK deletedBy) {
        List<ItemWeight> itemWeights = getItemWeightsByItemForUpdate(item);
        
        itemWeights.forEach((itemWeight) -> 
                deleteItemWeight(itemWeight, deletedBy)
        );
    }
    
    public void deleteItemWeightByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        ItemWeight itemWeight = getItemWeightForUpdate(item, unitOfMeasureType);
        
        if(itemWeight!= null) {
            deleteItemWeight(itemWeight, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Related Item Types
    // --------------------------------------------------------------------------------

    public RelatedItemType createRelatedItemType(String relatedItemTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        RelatedItemType defaultRelatedItemType = getDefaultRelatedItemType();
        boolean defaultFound = defaultRelatedItemType != null;

        if(defaultFound && isDefault) {
            RelatedItemTypeDetailValue defaultRelatedItemTypeDetailValue = getDefaultRelatedItemTypeDetailValueForUpdate();

            defaultRelatedItemTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateRelatedItemTypeFromValue(defaultRelatedItemTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        RelatedItemType relatedItemType = RelatedItemTypeFactory.getInstance().create();
        RelatedItemTypeDetail relatedItemTypeDetail = RelatedItemTypeDetailFactory.getInstance().create(relatedItemType,
                relatedItemTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        relatedItemType = RelatedItemTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                relatedItemType.getPrimaryKey());
        relatedItemType.setActiveDetail(relatedItemTypeDetail);
        relatedItemType.setLastDetail(relatedItemTypeDetail);
        relatedItemType.store();

        sendEventUsingNames(relatedItemType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return relatedItemType;
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

    private RelatedItemType getRelatedItemTypeByName(String relatedItemTypeName, EntityPermission entityPermission) {
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

    private RelatedItemType getDefaultRelatedItemType(EntityPermission entityPermission) {
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
        return getItemTransferCaches(userVisit).getRelatedItemTypeTransferCache().getTransfer(relatedItemType);
    }

    public List<RelatedItemTypeTransfer> getRelatedItemTypeTransfers(UserVisit userVisit, List<RelatedItemType> relatedItemTypes) {
        List<RelatedItemTypeTransfer> relatedItemTypeTransfers = new ArrayList<>(relatedItemTypes.size());
        RelatedItemTypeTransferCache relatedItemTypeTransferCache = getItemTransferCaches(userVisit).getRelatedItemTypeTransferCache();

        relatedItemTypes.forEach((relatedItemType) ->
                relatedItemTypeTransfers.add(relatedItemTypeTransferCache.getTransfer(relatedItemType))
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
        List<RelatedItemType> relatedItemTypes = getRelatedItemTypes();
        int size = relatedItemTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultRelatedItemTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var relatedItemType : relatedItemTypes) {
            RelatedItemTypeDetail relatedItemTypeDetail = relatedItemType.getLastDetail();

            String label = getBestRelatedItemTypeDescription(relatedItemType, language);
            String value = relatedItemTypeDetail.getRelatedItemTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultRelatedItemTypeChoice != null && defaultRelatedItemTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && relatedItemTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new RelatedItemTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateRelatedItemTypeFromValue(RelatedItemTypeDetailValue relatedItemTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(relatedItemTypeDetailValue.hasBeenModified()) {
            RelatedItemType relatedItemType = RelatedItemTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     relatedItemTypeDetailValue.getRelatedItemTypePK());
            RelatedItemTypeDetail relatedItemTypeDetail = relatedItemType.getActiveDetailForUpdate();

            relatedItemTypeDetail.setThruTime(session.START_TIME_LONG);
            relatedItemTypeDetail.store();

            RelatedItemTypePK relatedItemTypePK = relatedItemTypeDetail.getRelatedItemTypePK(); // Not updated
            String relatedItemTypeName = relatedItemTypeDetailValue.getRelatedItemTypeName();
            Boolean isDefault = relatedItemTypeDetailValue.getIsDefault();
            Integer sortOrder = relatedItemTypeDetailValue.getSortOrder();

            if(checkDefault) {
                RelatedItemType defaultRelatedItemType = getDefaultRelatedItemType();
                boolean defaultFound = defaultRelatedItemType != null && !defaultRelatedItemType.equals(relatedItemType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    RelatedItemTypeDetailValue defaultRelatedItemTypeDetailValue = getDefaultRelatedItemTypeDetailValueForUpdate();

                    defaultRelatedItemTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateRelatedItemTypeFromValue(defaultRelatedItemTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            relatedItemTypeDetail = RelatedItemTypeDetailFactory.getInstance().create(relatedItemTypePK,
                    relatedItemTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            relatedItemType.setActiveDetail(relatedItemTypeDetail);
            relatedItemType.setLastDetail(relatedItemTypeDetail);

            sendEventUsingNames(relatedItemTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateRelatedItemTypeFromValue(RelatedItemTypeDetailValue relatedItemTypeDetailValue, BasePK updatedBy) {
        updateRelatedItemTypeFromValue(relatedItemTypeDetailValue, true, updatedBy);
    }

    public void deleteRelatedItemType(RelatedItemType relatedItemType, BasePK deletedBy) {
        deleteRelatedItemTypeDescriptionsByRelatedItemType(relatedItemType, deletedBy);
        deleteRelatedItemsByRelatedItemType(relatedItemType, deletedBy);

        RelatedItemTypeDetail relatedItemTypeDetail = relatedItemType.getLastDetailForUpdate();
        relatedItemTypeDetail.setThruTime(session.START_TIME_LONG);
        relatedItemType.setActiveDetail(null);
        relatedItemType.store();

        // Check for default, and pick one if necessary
        RelatedItemType defaultRelatedItemType = getDefaultRelatedItemType();
        if(defaultRelatedItemType == null) {
            List<RelatedItemType> relatedItemTypes = getRelatedItemTypesForUpdate();

            if(!relatedItemTypes.isEmpty()) {
                Iterator<RelatedItemType> iter = relatedItemTypes.iterator();
                if(iter.hasNext()) {
                    defaultRelatedItemType = iter.next();
                }
                RelatedItemTypeDetailValue relatedItemTypeDetailValue = Objects.requireNonNull(defaultRelatedItemType).getLastDetailForUpdate().getRelatedItemTypeDetailValue().clone();

                relatedItemTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateRelatedItemTypeFromValue(relatedItemTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(relatedItemType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Related Item Type Descriptions
    // --------------------------------------------------------------------------------

    public RelatedItemTypeDescription createRelatedItemTypeDescription(RelatedItemType relatedItemType,
            Language language, String description, BasePK createdBy) {
        RelatedItemTypeDescription relatedItemTypeDescription = RelatedItemTypeDescriptionFactory.getInstance().create(relatedItemType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(relatedItemType.getPrimaryKey(), EventTypes.MODIFY.name(), relatedItemTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        RelatedItemTypeDescription relatedItemTypeDescription = getRelatedItemTypeDescription(relatedItemType, language);

        if(relatedItemTypeDescription == null && !language.getIsDefault()) {
            relatedItemTypeDescription = getRelatedItemTypeDescription(relatedItemType, getPartyControl().getDefaultLanguage());
        }

        if(relatedItemTypeDescription == null) {
            description = relatedItemType.getLastDetail().getRelatedItemTypeName();
        } else {
            description = relatedItemTypeDescription.getDescription();
        }

        return description;
    }

    public RelatedItemTypeDescriptionTransfer getRelatedItemTypeDescriptionTransfer(UserVisit userVisit, RelatedItemTypeDescription relatedItemTypeDescription) {
        return getItemTransferCaches(userVisit).getRelatedItemTypeDescriptionTransferCache().getTransfer(relatedItemTypeDescription);
    }

    public List<RelatedItemTypeDescriptionTransfer> getRelatedItemTypeDescriptionTransfersByRelatedItemType(UserVisit userVisit, RelatedItemType relatedItemType) {
        List<RelatedItemTypeDescription> relatedItemTypeDescriptions = getRelatedItemTypeDescriptionsByRelatedItemType(relatedItemType);
        List<RelatedItemTypeDescriptionTransfer> relatedItemTypeDescriptionTransfers = new ArrayList<>(relatedItemTypeDescriptions.size());
        RelatedItemTypeDescriptionTransferCache relatedItemTypeDescriptionTransferCache = getItemTransferCaches(userVisit).getRelatedItemTypeDescriptionTransferCache();

        relatedItemTypeDescriptions.forEach((relatedItemTypeDescription) ->
                relatedItemTypeDescriptionTransfers.add(relatedItemTypeDescriptionTransferCache.getTransfer(relatedItemTypeDescription))
        );

        return relatedItemTypeDescriptionTransfers;
    }

    public void updateRelatedItemTypeDescriptionFromValue(RelatedItemTypeDescriptionValue relatedItemTypeDescriptionValue, BasePK updatedBy) {
        if(relatedItemTypeDescriptionValue.hasBeenModified()) {
            RelatedItemTypeDescription relatedItemTypeDescription = RelatedItemTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    relatedItemTypeDescriptionValue.getPrimaryKey());

            relatedItemTypeDescription.setThruTime(session.START_TIME_LONG);
            relatedItemTypeDescription.store();

            RelatedItemType relatedItemType = relatedItemTypeDescription.getRelatedItemType();
            Language language = relatedItemTypeDescription.getLanguage();
            String description = relatedItemTypeDescriptionValue.getDescription();

            relatedItemTypeDescription = RelatedItemTypeDescriptionFactory.getInstance().create(relatedItemType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(relatedItemType.getPrimaryKey(), EventTypes.MODIFY.name(), relatedItemTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteRelatedItemTypeDescription(RelatedItemTypeDescription relatedItemTypeDescription, BasePK deletedBy) {
        relatedItemTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(relatedItemTypeDescription.getRelatedItemTypePK(), EventTypes.MODIFY.name(), relatedItemTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteRelatedItemTypeDescriptionsByRelatedItemType(RelatedItemType relatedItemType, BasePK deletedBy) {
        List<RelatedItemTypeDescription> relatedItemTypeDescriptions = getRelatedItemTypeDescriptionsByRelatedItemTypeForUpdate(relatedItemType);

        relatedItemTypeDescriptions.forEach((relatedItemTypeDescription) -> 
                deleteRelatedItemTypeDescription(relatedItemTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Related Items
    // --------------------------------------------------------------------------------

    public RelatedItem createRelatedItem(RelatedItemType relatedItemType, Item fromItem, Item toItem, Integer sortOrder, BasePK createdBy) {
        RelatedItem relatedItem = RelatedItemFactory.getInstance().create();
        RelatedItemDetail relatedItemDetail = RelatedItemDetailFactory.getInstance().create(relatedItem, relatedItemType, fromItem, toItem, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        relatedItem = RelatedItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                relatedItem.getPrimaryKey());
        relatedItem.setActiveDetail(relatedItemDetail);
        relatedItem.setLastDetail(relatedItemDetail);
        relatedItem.store();

        sendEventUsingNames(relatedItem.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
                "FROM relateditems, relateditemdetails, relateditemtypes, items, itemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_fromitemid = ? " +
                "AND rltidt_rltityp_relateditemtypeid = rltityp_relateditemtypeid " +
                "AND rltidt_fromitemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                "ORDER BY rltityp_sortorder, rltityp_relateditemtypename, rltidt_sortorder, itmdt_itemname " +
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
                "FROM relateditems, relateditemdetails, relateditemtypes, items, itemdetails " +
                "WHERE rlti_activedetailid = rltidt_relateditemdetailid " +
                "AND rltidt_toitemid = ? " +
                "AND rltidt_rltityp_relateditemtypeid = rltityp_relateditemtypeid " +
                "AND rltidt_toitemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                "ORDER BY rltityp_sortorder, rltityp_relateditemtypename, rltidt_sortorder, itmdt_itemname " +
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
        return getItemTransferCaches(userVisit).getRelatedItemTransferCache().getTransfer(relatedItem);
    }

    public List<RelatedItemTransfer> getRelatedItemTransfers(UserVisit userVisit, List<RelatedItem> relatedItems) {
        List<RelatedItemTransfer> relatedItemTransfers = new ArrayList<>(relatedItems.size());
        RelatedItemTransferCache relatedItemTransferCache = getItemTransferCaches(userVisit).getRelatedItemTransferCache();

        relatedItems.forEach((relatedItem) ->
                relatedItemTransfers.add(relatedItemTransferCache.getTransfer(relatedItem))
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
            RelatedItem relatedItem = RelatedItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     relatedItemDetailValue.getRelatedItemPK());
            RelatedItemDetail relatedItemDetail = relatedItem.getActiveDetailForUpdate();

            relatedItemDetail.setThruTime(session.START_TIME_LONG);
            relatedItemDetail.store();

            RelatedItemPK relatedItemPK = relatedItemDetail.getRelatedItemPK(); // Not updated
            RelatedItemTypePK relatedItemTypePK = relatedItemDetail.getRelatedItemTypePK(); // Not updated
            ItemPK fromItemPK = relatedItemDetail.getFromItemPK(); // Not updated
            ItemPK toItemPK = relatedItemDetail.getToItemPK(); // Not updated
            Integer sortOrder = relatedItemDetailValue.getSortOrder();

            relatedItemDetail = RelatedItemDetailFactory.getInstance().create(relatedItemPK, relatedItemTypePK, fromItemPK, toItemPK, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            relatedItem.setActiveDetail(relatedItemDetail);
            relatedItem.setLastDetail(relatedItemDetail);

            sendEventUsingNames(relatedItemPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void deleteRelatedItem(RelatedItem relatedItem, BasePK deletedBy) {
        RelatedItemDetail relatedItemDetail = relatedItem.getLastDetailForUpdate();
        relatedItemDetail.setThruTime(session.START_TIME_LONG);
        relatedItem.setActiveDetail(null);
        relatedItem.store();

        sendEventUsingNames(relatedItemDetail.getFromItemPK(), EventTypes.MODIFY.name(), relatedItem.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        HarmonizedTariffScheduleCode defaultHarmonizedTariffScheduleCode = getDefaultHarmonizedTariffScheduleCode(countryGeoCode);
        boolean defaultFound = defaultHarmonizedTariffScheduleCode != null;

        if(defaultFound && isDefault) {
            HarmonizedTariffScheduleCodeDetailValue defaultHarmonizedTariffScheduleCodeDetailValue = getDefaultHarmonizedTariffScheduleCodeDetailValueForUpdate(countryGeoCode);

            defaultHarmonizedTariffScheduleCodeDetailValue.setIsDefault(Boolean.FALSE);
            updateHarmonizedTariffScheduleCodeFromValue(defaultHarmonizedTariffScheduleCodeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        HarmonizedTariffScheduleCode harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().create();
        HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = HarmonizedTariffScheduleCodeDetailFactory.getInstance().create(session,
                harmonizedTariffScheduleCode, countryGeoCode, harmonizedTariffScheduleCodeName, firstHarmonizedTariffScheduleCodeUnit,
                secondHarmonizedTariffScheduleCodeUnit, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCode.getPrimaryKey());
        harmonizedTariffScheduleCode.setActiveDetail(harmonizedTariffScheduleCodeDetail);
        harmonizedTariffScheduleCode.setLastDetail(harmonizedTariffScheduleCodeDetail);
        harmonizedTariffScheduleCode.store();

        sendEventUsingNames(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return harmonizedTariffScheduleCode;
    }

    public long countHarmonizedTariffScheduleCodesByCountryGeoCode(GeoCode countryGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM harmonizedtariffschedulecodes, harmonizedtariffschedulecodedetails "
                + "WHERE hztsc_activedetailid = hztscdt_harmonizedtariffschedulecodedetailid AND hztscdt_countrygeocodeid = ?",
                countryGeoCode);
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.HarmonizedTariffScheduleCode */
    public HarmonizedTariffScheduleCode getHarmonizedTariffScheduleCodeByEntityInstance(EntityInstance entityInstance) {
        HarmonizedTariffScheduleCodePK pk = new HarmonizedTariffScheduleCodePK(entityInstance.getEntityUniqueId());
        HarmonizedTariffScheduleCode harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
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
        List<HarmonizedTariffScheduleCode> harmonizedTariffScheduleCodees = getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);
        int size = harmonizedTariffScheduleCodees.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultHarmonizedTariffScheduleCodeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var harmonizedTariffScheduleCode : harmonizedTariffScheduleCodees) {
            HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();
            String harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName();
            HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = getBestHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language);
            
            String label = harmonizedTariffScheduleCodeTranslation == null ? harmonizedTariffScheduleCodeName : harmonizedTariffScheduleCodeTranslation.getDescription();
            String value = harmonizedTariffScheduleCodeName;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultHarmonizedTariffScheduleCodeChoice != null && defaultHarmonizedTariffScheduleCodeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && harmonizedTariffScheduleCodeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new HarmonizedTariffScheduleCodeChoicesBean(labels, values, defaultValue);
    }
    
    public HarmonizedTariffScheduleCodeTransfer getHarmonizedTariffScheduleCodeTransfer(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeTransferCache().getTransfer(harmonizedTariffScheduleCode);
    }

    public List<HarmonizedTariffScheduleCodeTransfer> getHarmonizedTariffScheduleCodeTransfers(UserVisit userVisit, List<HarmonizedTariffScheduleCode> harmonizedTariffScheduleCodes) {
        List<HarmonizedTariffScheduleCodeTransfer> harmonizedTariffScheduleCodeTransfers = new ArrayList<>(harmonizedTariffScheduleCodes.size());
        HarmonizedTariffScheduleCodeTransferCache harmonizedTariffScheduleCodeTransferCache = getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeTransferCache();

        harmonizedTariffScheduleCodes.forEach((harmonizedTariffScheduleCode) ->
                harmonizedTariffScheduleCodeTransfers.add(harmonizedTariffScheduleCodeTransferCache.getTransfer(harmonizedTariffScheduleCode))
        );

        return harmonizedTariffScheduleCodeTransfers;
    }

    public List<HarmonizedTariffScheduleCodeTransfer> getHarmonizedTariffScheduleCodeTransfersByCountryGeoCode(UserVisit userVisit, GeoCode countryGeoCode) {
        return getHarmonizedTariffScheduleCodeTransfers(userVisit, getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode));
    }

    private void updateHarmonizedTariffScheduleCodeFromValue(HarmonizedTariffScheduleCodeDetailValue harmonizedTariffScheduleCodeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeDetailValue.hasBeenModified()) {
            HarmonizedTariffScheduleCode harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodePK());
            HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getActiveDetailForUpdate();

            harmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
            harmonizedTariffScheduleCodeDetail.store();

            HarmonizedTariffScheduleCodePK harmonizedTariffScheduleCodePK = harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodePK();
            GeoCode countryGeoCode = harmonizedTariffScheduleCodeDetail.getCountryGeoCode();
            GeoCodePK countryGeoCodePK = countryGeoCode.getPrimaryKey();
            String harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodeName();
            HarmonizedTariffScheduleCodeUnitPK firstHarmonizedTariffScheduleCodeUnitPK = harmonizedTariffScheduleCodeDetailValue.getFirstHarmonizedTariffScheduleCodeUnitPK();
            HarmonizedTariffScheduleCodeUnitPK secondHarmonizedTariffScheduleCodeUnitPK = harmonizedTariffScheduleCodeDetailValue.getSecondHarmonizedTariffScheduleCodeUnitPK();
            Boolean isDefault = harmonizedTariffScheduleCodeDetailValue.getIsDefault();
            Integer sortOrder = harmonizedTariffScheduleCodeDetailValue.getSortOrder();

            if(checkDefault) {
                HarmonizedTariffScheduleCode defaultHarmonizedTariffScheduleCode = getDefaultHarmonizedTariffScheduleCode(countryGeoCode);
                boolean defaultFound = defaultHarmonizedTariffScheduleCode != null && !defaultHarmonizedTariffScheduleCode.equals(harmonizedTariffScheduleCode);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    HarmonizedTariffScheduleCodeDetailValue defaultHarmonizedTariffScheduleCodeDetailValue = getDefaultHarmonizedTariffScheduleCodeDetailValueForUpdate(countryGeoCode);

                    defaultHarmonizedTariffScheduleCodeDetailValue.setIsDefault(Boolean.FALSE);
                    updateHarmonizedTariffScheduleCodeFromValue(defaultHarmonizedTariffScheduleCodeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            harmonizedTariffScheduleCodeDetail = HarmonizedTariffScheduleCodeDetailFactory.getInstance().create(harmonizedTariffScheduleCodePK,
                    countryGeoCodePK, harmonizedTariffScheduleCodeName, firstHarmonizedTariffScheduleCodeUnitPK, secondHarmonizedTariffScheduleCodeUnitPK, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            harmonizedTariffScheduleCode.setActiveDetail(harmonizedTariffScheduleCodeDetail);
            harmonizedTariffScheduleCode.setLastDetail(harmonizedTariffScheduleCodeDetail);

            sendEventUsingNames(harmonizedTariffScheduleCodePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateHarmonizedTariffScheduleCodeFromValue(HarmonizedTariffScheduleCodeDetailValue harmonizedTariffScheduleCodeDetailValue, BasePK updatedBy) {
        updateHarmonizedTariffScheduleCodeFromValue(harmonizedTariffScheduleCodeDetailValue, true, updatedBy);
    }

    public void deleteHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, BasePK deletedBy) {
        deleteItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, deletedBy);
        deleteHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, deletedBy);
        deleteHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode, deletedBy);

        HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetailForUpdate();
        harmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
        harmonizedTariffScheduleCode.setActiveDetail(null);
        harmonizedTariffScheduleCode.store();

        // Check for default, and pick one if necessary
        GeoCode countryGeoCode = harmonizedTariffScheduleCodeDetail.getCountryGeoCode();
        HarmonizedTariffScheduleCode defaultHarmonizedTariffScheduleCode = getDefaultHarmonizedTariffScheduleCode(countryGeoCode);
        if(defaultHarmonizedTariffScheduleCode == null) {
            List<HarmonizedTariffScheduleCode> harmonizedTariffScheduleCodes = getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);

            if(!harmonizedTariffScheduleCodes.isEmpty()) {
                Iterator<HarmonizedTariffScheduleCode> iter = harmonizedTariffScheduleCodes.iterator();
                if(iter.hasNext()) {
                    defaultHarmonizedTariffScheduleCode = iter.next();
                }
                HarmonizedTariffScheduleCodeDetailValue harmonizedTariffScheduleCodeDetailValue = Objects.requireNonNull(defaultHarmonizedTariffScheduleCode).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeDetailValue().clone();

                harmonizedTariffScheduleCodeDetailValue.setIsDefault(Boolean.TRUE);
                updateHarmonizedTariffScheduleCodeFromValue(harmonizedTariffScheduleCodeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
        HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = HarmonizedTariffScheduleCodeTranslationFactory.getInstance().create(harmonizedTariffScheduleCode,
                language, description, overviewMimeType, overview, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language);
        
        if(harmonizedTariffScheduleCodeTranslation == null && !language.getIsDefault()) {
            harmonizedTariffScheduleCodeTranslation = getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, getPartyControl().getDefaultLanguage());
        }
        
        return harmonizedTariffScheduleCodeTranslation;
    }
    
    public HarmonizedTariffScheduleCodeTranslationTransfer getHarmonizedTariffScheduleCodeTranslationTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        return getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeTranslationTransferCache().getTransfer(harmonizedTariffScheduleCodeTranslation);
    }

    public List<HarmonizedTariffScheduleCodeTranslationTransfer> getHarmonizedTariffScheduleCodeTranslationTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        List<HarmonizedTariffScheduleCodeTranslation> harmonizedTariffScheduleCodeTranslations = getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(harmonizedTariffScheduleCode);
        List<HarmonizedTariffScheduleCodeTranslationTransfer> harmonizedTariffScheduleCodeTranslationTransfers = new ArrayList<>(harmonizedTariffScheduleCodeTranslations.size());

        harmonizedTariffScheduleCodeTranslations.forEach((harmonizedTariffScheduleCodeTranslation) -> {
            harmonizedTariffScheduleCodeTranslationTransfers.add(getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeTranslationTransferCache().getTransfer(harmonizedTariffScheduleCodeTranslation));
        });

        return harmonizedTariffScheduleCodeTranslationTransfers;
    }

    public void updateHarmonizedTariffScheduleCodeTranslationFromValue(HarmonizedTariffScheduleCodeTranslationValue harmonizedTariffScheduleCodeTranslationValue, BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeTranslationValue.hasBeenModified()) {
            HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = HarmonizedTariffScheduleCodeTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeTranslationValue.getPrimaryKey());

            harmonizedTariffScheduleCodeTranslation.setThruTime(session.START_TIME_LONG);
            harmonizedTariffScheduleCodeTranslation.store();

            HarmonizedTariffScheduleCodePK harmonizedTariffScheduleCodePK = harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCodePK();
            LanguagePK languagePK = harmonizedTariffScheduleCodeTranslation.getLanguagePK();
            String description = harmonizedTariffScheduleCodeTranslationValue.getDescription();
            MimeTypePK overviewMimeTypePK = harmonizedTariffScheduleCodeTranslationValue.getOverviewMimeTypePK();
            String overview = harmonizedTariffScheduleCodeTranslationValue.getOverview();

            harmonizedTariffScheduleCodeTranslation = HarmonizedTariffScheduleCodeTranslationFactory.getInstance().create(harmonizedTariffScheduleCodePK,
                    languagePK, description, overviewMimeTypePK, overview, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(harmonizedTariffScheduleCodePK, EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeTranslation(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation, BasePK deletedBy) {
        harmonizedTariffScheduleCodeTranslation.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCodePK(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeTranslation.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode, BasePK deletedBy) {
        List<HarmonizedTariffScheduleCodeTranslation> harmonizedTariffScheduleCodeTranslations = getHarmonizedTariffScheduleCodeTranslationsByHarmonizedTariffScheduleCodeForUpdate(harmonizedTariffScheduleCode);

        harmonizedTariffScheduleCodeTranslations.forEach((harmonizedTariffScheduleCodeTranslation) -> 
                deleteHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCodeTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // --------------------------------------------------------------------------------

    public HarmonizedTariffScheduleCodeUseType createHarmonizedTariffScheduleCodeUseType(String harmonizedTariffScheduleCodeUseTypeName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        HarmonizedTariffScheduleCodeUseType defaultHarmonizedTariffScheduleCodeUseType = getDefaultHarmonizedTariffScheduleCodeUseType();
        boolean defaultFound = defaultHarmonizedTariffScheduleCodeUseType != null;

        if(defaultFound && isDefault) {
            HarmonizedTariffScheduleCodeUseTypeDetailValue defaultHarmonizedTariffScheduleCodeUseTypeDetailValue = getDefaultHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate();

            defaultHarmonizedTariffScheduleCodeUseTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateHarmonizedTariffScheduleCodeUseTypeFromValue(defaultHarmonizedTariffScheduleCodeUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType = HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().create();
        HarmonizedTariffScheduleCodeUseTypeDetail harmonizedTariffScheduleCodeUseTypeDetail = HarmonizedTariffScheduleCodeUseTypeDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUseType,
                harmonizedTariffScheduleCodeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        harmonizedTariffScheduleCodeUseType = HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCodeUseType.getPrimaryKey());
        harmonizedTariffScheduleCodeUseType.setActiveDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.setLastDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.store();

        sendEventUsingNames(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
        List<HarmonizedTariffScheduleCodeUseType> harmonizedTariffScheduleCodeUseTypes = getHarmonizedTariffScheduleCodeUseTypes();
        int size = harmonizedTariffScheduleCodeUseTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultHarmonizedTariffScheduleCodeUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var harmonizedTariffScheduleCodeUseType : harmonizedTariffScheduleCodeUseTypes) {
            HarmonizedTariffScheduleCodeUseTypeDetail harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getLastDetail();

            String label = getBestHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, language);
            String value = harmonizedTariffScheduleCodeUseTypeDetail.getHarmonizedTariffScheduleCodeUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultHarmonizedTariffScheduleCodeUseTypeChoice != null && defaultHarmonizedTariffScheduleCodeUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && harmonizedTariffScheduleCodeUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new HarmonizedTariffScheduleCodeUseTypeChoicesBean(labels, values, defaultValue);
    }

    public HarmonizedTariffScheduleCodeUseTypeTransfer getHarmonizedTariffScheduleCodeUseTypeTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUseTypeTransferCache().getTransfer(harmonizedTariffScheduleCodeUseType);
    }

    public List<HarmonizedTariffScheduleCodeUseTypeTransfer> getHarmonizedTariffScheduleCodeUseTypeTransfers(UserVisit userVisit) {
        List<HarmonizedTariffScheduleCodeUseType> harmonizedTariffScheduleCodeUseTypes = getHarmonizedTariffScheduleCodeUseTypes();
        List<HarmonizedTariffScheduleCodeUseTypeTransfer> harmonizedTariffScheduleCodeUseTypeTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUseTypes.size());
        HarmonizedTariffScheduleCodeUseTypeTransferCache harmonizedTariffScheduleCodeUseTypeTransferCache = getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUseTypeTransferCache();

        harmonizedTariffScheduleCodeUseTypes.forEach((harmonizedTariffScheduleCodeUseType) ->
                harmonizedTariffScheduleCodeUseTypeTransfers.add(harmonizedTariffScheduleCodeUseTypeTransferCache.getTransfer(harmonizedTariffScheduleCodeUseType))
        );

        return harmonizedTariffScheduleCodeUseTypeTransfers;
    }

    private void updateHarmonizedTariffScheduleCodeUseTypeFromValue(HarmonizedTariffScheduleCodeUseTypeDetailValue harmonizedTariffScheduleCodeUseTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType = HarmonizedTariffScheduleCodeUseTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, harmonizedTariffScheduleCodeUseTypeDetailValue.getHarmonizedTariffScheduleCodeUseTypePK());
        HarmonizedTariffScheduleCodeUseTypeDetail harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getActiveDetailForUpdate();

        harmonizedTariffScheduleCodeUseTypeDetail.setThruTime(session.START_TIME_LONG);
        harmonizedTariffScheduleCodeUseTypeDetail.store();

        HarmonizedTariffScheduleCodeUseTypePK harmonizedTariffScheduleCodeUseTypePK = harmonizedTariffScheduleCodeUseTypeDetail.getHarmonizedTariffScheduleCodeUseTypePK();
        String harmonizedTariffScheduleCodeUseTypeName = harmonizedTariffScheduleCodeUseTypeDetailValue.getHarmonizedTariffScheduleCodeUseTypeName();
        Boolean isDefault = harmonizedTariffScheduleCodeUseTypeDetailValue.getIsDefault();
        Integer sortOrder = harmonizedTariffScheduleCodeUseTypeDetailValue.getSortOrder();

        if(checkDefault) {
            HarmonizedTariffScheduleCodeUseType defaultHarmonizedTariffScheduleCodeUseType = getDefaultHarmonizedTariffScheduleCodeUseType();
            boolean defaultFound = defaultHarmonizedTariffScheduleCodeUseType != null && !defaultHarmonizedTariffScheduleCodeUseType.equals(harmonizedTariffScheduleCodeUseType);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                HarmonizedTariffScheduleCodeUseTypeDetailValue defaultHarmonizedTariffScheduleCodeUseTypeDetailValue = getDefaultHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate();

                defaultHarmonizedTariffScheduleCodeUseTypeDetailValue.setIsDefault(Boolean.FALSE);
                updateHarmonizedTariffScheduleCodeUseTypeFromValue(defaultHarmonizedTariffScheduleCodeUseTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        harmonizedTariffScheduleCodeUseTypeDetail = HarmonizedTariffScheduleCodeUseTypeDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUseTypePK, harmonizedTariffScheduleCodeUseTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        harmonizedTariffScheduleCodeUseType.setActiveDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.setLastDetail(harmonizedTariffScheduleCodeUseTypeDetail);
        harmonizedTariffScheduleCodeUseType.store();

        sendEventUsingNames(harmonizedTariffScheduleCodeUseTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateHarmonizedTariffScheduleCodeUseTypeFromValue(HarmonizedTariffScheduleCodeUseTypeDetailValue harmonizedTariffScheduleCodeUseTypeDetailValue, BasePK updatedBy) {
        updateHarmonizedTariffScheduleCodeUseTypeFromValue(harmonizedTariffScheduleCodeUseTypeDetailValue, true, updatedBy);
    }

    public void deleteHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodeUsesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, deletedBy);
        deleteHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType, deletedBy);

        HarmonizedTariffScheduleCodeUseTypeDetail harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getLastDetailForUpdate();
        harmonizedTariffScheduleCodeUseTypeDetail.setThruTime(session.START_TIME_LONG);
        harmonizedTariffScheduleCodeUseType.setActiveDetail(null);
        harmonizedTariffScheduleCodeUseType.store();

        // Check for default, and pick one if necessary
        HarmonizedTariffScheduleCodeUseType defaultHarmonizedTariffScheduleCodeUseType = getDefaultHarmonizedTariffScheduleCodeUseType();
        if(defaultHarmonizedTariffScheduleCodeUseType == null) {
            List<HarmonizedTariffScheduleCodeUseType> harmonizedTariffScheduleCodeUseTypes = getHarmonizedTariffScheduleCodeUseTypesForUpdate();

            if(!harmonizedTariffScheduleCodeUseTypes.isEmpty()) {
                Iterator<HarmonizedTariffScheduleCodeUseType> iter = harmonizedTariffScheduleCodeUseTypes.iterator();
                if(iter.hasNext()) {
                    defaultHarmonizedTariffScheduleCodeUseType = iter.next();
                }
                HarmonizedTariffScheduleCodeUseTypeDetailValue harmonizedTariffScheduleCodeUseTypeDetailValue = Objects.requireNonNull(defaultHarmonizedTariffScheduleCodeUseType).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUseTypeDetailValue().clone();

                harmonizedTariffScheduleCodeUseTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateHarmonizedTariffScheduleCodeUseTypeFromValue(harmonizedTariffScheduleCodeUseTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // --------------------------------------------------------------------------------

    public HarmonizedTariffScheduleCodeUseTypeDescription createHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType,
            Language language, String description, BasePK createdBy) {
        HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription = HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUseType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription = getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, language);

        if(harmonizedTariffScheduleCodeUseTypeDescription == null && !language.getIsDefault()) {
            harmonizedTariffScheduleCodeUseTypeDescription = getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, getPartyControl().getDefaultLanguage());
        }

        if(harmonizedTariffScheduleCodeUseTypeDescription == null) {
            description = harmonizedTariffScheduleCodeUseType.getLastDetail().getHarmonizedTariffScheduleCodeUseTypeName();
        } else {
            description = harmonizedTariffScheduleCodeUseTypeDescription.getDescription();
        }

        return description;
    }

    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer getHarmonizedTariffScheduleCodeUseTypeDescriptionTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        return getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache().getTransfer(harmonizedTariffScheduleCodeUseTypeDescription);
    }

    public List<HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer> getHarmonizedTariffScheduleCodeUseTypeDescriptionTransfersByHarmonizedTariffScheduleCodeUseType(UserVisit userVisit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        List<HarmonizedTariffScheduleCodeUseTypeDescription> harmonizedTariffScheduleCodeUseTypeDescriptions = getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType);
        List<HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer> harmonizedTariffScheduleCodeUseTypeDescriptionTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUseTypeDescriptions.size());

        harmonizedTariffScheduleCodeUseTypeDescriptions.forEach((harmonizedTariffScheduleCodeUseTypeDescription) -> {
            harmonizedTariffScheduleCodeUseTypeDescriptionTransfers.add(getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache().getTransfer(harmonizedTariffScheduleCodeUseTypeDescription));
        });

        return harmonizedTariffScheduleCodeUseTypeDescriptionTransfers;
    }

    public void updateHarmonizedTariffScheduleCodeUseTypeDescriptionFromValue(HarmonizedTariffScheduleCodeUseTypeDescriptionValue harmonizedTariffScheduleCodeUseTypeDescriptionValue, BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeUseTypeDescriptionValue.hasBeenModified()) {
            HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription = HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeUseTypeDescriptionValue.getPrimaryKey());

            harmonizedTariffScheduleCodeUseTypeDescription.setThruTime(session.START_TIME_LONG);
            harmonizedTariffScheduleCodeUseTypeDescription.store();

            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseType();
            Language language = harmonizedTariffScheduleCodeUseTypeDescription.getLanguage();
            String description = harmonizedTariffScheduleCodeUseTypeDescriptionValue.getDescription();

            harmonizedTariffScheduleCodeUseTypeDescription = HarmonizedTariffScheduleCodeUseTypeDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(harmonizedTariffScheduleCodeUseType.getPrimaryKey(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeUseTypeDescription(HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUseTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseTypePK(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUseTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK deletedBy) {
        List<HarmonizedTariffScheduleCodeUseTypeDescription> harmonizedTariffScheduleCodeUseTypeDescriptions = getHarmonizedTariffScheduleCodeUseTypeDescriptionsByHarmonizedTariffScheduleCodeUseTypeForUpdate(harmonizedTariffScheduleCodeUseType);

        harmonizedTariffScheduleCodeUseTypeDescriptions.forEach((harmonizedTariffScheduleCodeUseTypeDescription) -> 
                deleteHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Types
    // --------------------------------------------------------------------------------

    public HarmonizedTariffScheduleCodeUnit createHarmonizedTariffScheduleCodeUnit(String harmonizedTariffScheduleCodeUnitName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        HarmonizedTariffScheduleCodeUnit defaultHarmonizedTariffScheduleCodeUnit = getDefaultHarmonizedTariffScheduleCodeUnit();
        boolean defaultFound = defaultHarmonizedTariffScheduleCodeUnit != null;

        if(defaultFound && isDefault) {
            HarmonizedTariffScheduleCodeUnitDetailValue defaultHarmonizedTariffScheduleCodeUnitDetailValue = getDefaultHarmonizedTariffScheduleCodeUnitDetailValueForUpdate();

            defaultHarmonizedTariffScheduleCodeUnitDetailValue.setIsDefault(Boolean.FALSE);
            updateHarmonizedTariffScheduleCodeUnitFromValue(defaultHarmonizedTariffScheduleCodeUnitDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit = HarmonizedTariffScheduleCodeUnitFactory.getInstance().create();
        HarmonizedTariffScheduleCodeUnitDetail harmonizedTariffScheduleCodeUnitDetail = HarmonizedTariffScheduleCodeUnitDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUnit,
                harmonizedTariffScheduleCodeUnitName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        harmonizedTariffScheduleCodeUnit = HarmonizedTariffScheduleCodeUnitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                harmonizedTariffScheduleCodeUnit.getPrimaryKey());
        harmonizedTariffScheduleCodeUnit.setActiveDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.setLastDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.store();

        sendEventUsingNames(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
        List<HarmonizedTariffScheduleCodeUnit> harmonizedTariffScheduleCodeUnits = getHarmonizedTariffScheduleCodeUnits();
        int size = harmonizedTariffScheduleCodeUnits.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultHarmonizedTariffScheduleCodeUnitChoice == null) {
                defaultValue = "";
            }
        }

        for(var harmonizedTariffScheduleCodeUnit : harmonizedTariffScheduleCodeUnits) {
            HarmonizedTariffScheduleCodeUnitDetail harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getLastDetail();

            String label = getBestHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, language);
            String value = harmonizedTariffScheduleCodeUnitDetail.getHarmonizedTariffScheduleCodeUnitName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultHarmonizedTariffScheduleCodeUnitChoice != null && defaultHarmonizedTariffScheduleCodeUnitChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && harmonizedTariffScheduleCodeUnitDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new HarmonizedTariffScheduleCodeUnitChoicesBean(labels, values, defaultValue);
    }

    public HarmonizedTariffScheduleCodeUnitTransfer getHarmonizedTariffScheduleCodeUnitTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        return getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUnitTransferCache().getTransfer(harmonizedTariffScheduleCodeUnit);
    }

    public List<HarmonizedTariffScheduleCodeUnitTransfer> getHarmonizedTariffScheduleCodeUnitTransfers(UserVisit userVisit) {
        List<HarmonizedTariffScheduleCodeUnit> harmonizedTariffScheduleCodeUnits = getHarmonizedTariffScheduleCodeUnits();
        List<HarmonizedTariffScheduleCodeUnitTransfer> harmonizedTariffScheduleCodeUnitTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUnits.size());
        HarmonizedTariffScheduleCodeUnitTransferCache harmonizedTariffScheduleCodeUnitTransferCache = getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUnitTransferCache();

        harmonizedTariffScheduleCodeUnits.forEach((harmonizedTariffScheduleCodeUnit) ->
                harmonizedTariffScheduleCodeUnitTransfers.add(harmonizedTariffScheduleCodeUnitTransferCache.getTransfer(harmonizedTariffScheduleCodeUnit))
        );

        return harmonizedTariffScheduleCodeUnitTransfers;
    }

    private void updateHarmonizedTariffScheduleCodeUnitFromValue(HarmonizedTariffScheduleCodeUnitDetailValue harmonizedTariffScheduleCodeUnitDetailValue, boolean checkDefault, BasePK updatedBy) {
        HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit = HarmonizedTariffScheduleCodeUnitFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, harmonizedTariffScheduleCodeUnitDetailValue.getHarmonizedTariffScheduleCodeUnitPK());
        HarmonizedTariffScheduleCodeUnitDetail harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getActiveDetailForUpdate();

        harmonizedTariffScheduleCodeUnitDetail.setThruTime(session.START_TIME_LONG);
        harmonizedTariffScheduleCodeUnitDetail.store();

        HarmonizedTariffScheduleCodeUnitPK harmonizedTariffScheduleCodeUnitPK = harmonizedTariffScheduleCodeUnitDetail.getHarmonizedTariffScheduleCodeUnitPK();
        String harmonizedTariffScheduleCodeUnitName = harmonizedTariffScheduleCodeUnitDetailValue.getHarmonizedTariffScheduleCodeUnitName();
        Boolean isDefault = harmonizedTariffScheduleCodeUnitDetailValue.getIsDefault();
        Integer sortOrder = harmonizedTariffScheduleCodeUnitDetailValue.getSortOrder();

        if(checkDefault) {
            HarmonizedTariffScheduleCodeUnit defaultHarmonizedTariffScheduleCodeUnit = getDefaultHarmonizedTariffScheduleCodeUnit();
            boolean defaultFound = defaultHarmonizedTariffScheduleCodeUnit != null && !defaultHarmonizedTariffScheduleCodeUnit.equals(harmonizedTariffScheduleCodeUnit);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                HarmonizedTariffScheduleCodeUnitDetailValue defaultHarmonizedTariffScheduleCodeUnitDetailValue = getDefaultHarmonizedTariffScheduleCodeUnitDetailValueForUpdate();

                defaultHarmonizedTariffScheduleCodeUnitDetailValue.setIsDefault(Boolean.FALSE);
                updateHarmonizedTariffScheduleCodeUnitFromValue(defaultHarmonizedTariffScheduleCodeUnitDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        harmonizedTariffScheduleCodeUnitDetail = HarmonizedTariffScheduleCodeUnitDetailFactory.getInstance().create(harmonizedTariffScheduleCodeUnitPK, harmonizedTariffScheduleCodeUnitName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        harmonizedTariffScheduleCodeUnit.setActiveDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.setLastDetail(harmonizedTariffScheduleCodeUnitDetail);
        harmonizedTariffScheduleCodeUnit.store();

        sendEventUsingNames(harmonizedTariffScheduleCodeUnitPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateHarmonizedTariffScheduleCodeUnitFromValue(HarmonizedTariffScheduleCodeUnitDetailValue harmonizedTariffScheduleCodeUnitDetailValue, BasePK updatedBy) {
        updateHarmonizedTariffScheduleCodeUnitFromValue(harmonizedTariffScheduleCodeUnitDetailValue, true, updatedBy);
    }

    public void deleteHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, BasePK deletedBy) {
        deleteHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, deletedBy);
        deleteHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit, deletedBy);

        HarmonizedTariffScheduleCodeUnitDetail harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getLastDetailForUpdate();
        harmonizedTariffScheduleCodeUnitDetail.setThruTime(session.START_TIME_LONG);
        harmonizedTariffScheduleCodeUnit.setActiveDetail(null);
        harmonizedTariffScheduleCodeUnit.store();

        // Check for default, and pick one if necessary
        HarmonizedTariffScheduleCodeUnit defaultHarmonizedTariffScheduleCodeUnit = getDefaultHarmonizedTariffScheduleCodeUnit();
        if(defaultHarmonizedTariffScheduleCodeUnit == null) {
            List<HarmonizedTariffScheduleCodeUnit> harmonizedTariffScheduleCodeUnits = getHarmonizedTariffScheduleCodeUnitsForUpdate();

            if(!harmonizedTariffScheduleCodeUnits.isEmpty()) {
                Iterator<HarmonizedTariffScheduleCodeUnit> iter = harmonizedTariffScheduleCodeUnits.iterator();
                if(iter.hasNext()) {
                    defaultHarmonizedTariffScheduleCodeUnit = iter.next();
                }
                HarmonizedTariffScheduleCodeUnitDetailValue harmonizedTariffScheduleCodeUnitDetailValue = Objects.requireNonNull(defaultHarmonizedTariffScheduleCodeUnit).getLastDetailForUpdate().getHarmonizedTariffScheduleCodeUnitDetailValue().clone();

                harmonizedTariffScheduleCodeUnitDetailValue.setIsDefault(Boolean.TRUE);
                updateHarmonizedTariffScheduleCodeUnitFromValue(harmonizedTariffScheduleCodeUnitDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Use Type Descriptions
    // --------------------------------------------------------------------------------

    public HarmonizedTariffScheduleCodeUnitDescription createHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit,
            Language language, String description, BasePK createdBy) {
        HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription = HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUnit,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription = getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, language);

        if(harmonizedTariffScheduleCodeUnitDescription == null && !language.getIsDefault()) {
            harmonizedTariffScheduleCodeUnitDescription = getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, getPartyControl().getDefaultLanguage());
        }

        if(harmonizedTariffScheduleCodeUnitDescription == null) {
            description = harmonizedTariffScheduleCodeUnit.getLastDetail().getHarmonizedTariffScheduleCodeUnitName();
        } else {
            description = harmonizedTariffScheduleCodeUnitDescription.getDescription();
        }

        return description;
    }

    public HarmonizedTariffScheduleCodeUnitDescriptionTransfer getHarmonizedTariffScheduleCodeUnitDescriptionTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription) {
        return getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUnitDescriptionTransferCache().getTransfer(harmonizedTariffScheduleCodeUnitDescription);
    }

    public List<HarmonizedTariffScheduleCodeUnitDescriptionTransfer> getHarmonizedTariffScheduleCodeUnitDescriptionTransfersByHarmonizedTariffScheduleCodeUnit(UserVisit userVisit, HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        List<HarmonizedTariffScheduleCodeUnitDescription> harmonizedTariffScheduleCodeUnitDescriptions = getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(harmonizedTariffScheduleCodeUnit);
        List<HarmonizedTariffScheduleCodeUnitDescriptionTransfer> harmonizedTariffScheduleCodeUnitDescriptionTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUnitDescriptions.size());

        harmonizedTariffScheduleCodeUnitDescriptions.forEach((harmonizedTariffScheduleCodeUnitDescription) -> {
            harmonizedTariffScheduleCodeUnitDescriptionTransfers.add(getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUnitDescriptionTransferCache().getTransfer(harmonizedTariffScheduleCodeUnitDescription));
        });

        return harmonizedTariffScheduleCodeUnitDescriptionTransfers;
    }

    public void updateHarmonizedTariffScheduleCodeUnitDescriptionFromValue(HarmonizedTariffScheduleCodeUnitDescriptionValue harmonizedTariffScheduleCodeUnitDescriptionValue, BasePK updatedBy) {
        if(harmonizedTariffScheduleCodeUnitDescriptionValue.hasBeenModified()) {
            HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription = HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     harmonizedTariffScheduleCodeUnitDescriptionValue.getPrimaryKey());

            harmonizedTariffScheduleCodeUnitDescription.setThruTime(session.START_TIME_LONG);
            harmonizedTariffScheduleCodeUnitDescription.store();

            HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeUnitDescription.getHarmonizedTariffScheduleCodeUnit();
            Language language = harmonizedTariffScheduleCodeUnitDescription.getLanguage();
            String description = harmonizedTariffScheduleCodeUnitDescriptionValue.getDescription();

            harmonizedTariffScheduleCodeUnitDescription = HarmonizedTariffScheduleCodeUnitDescriptionFactory.getInstance().create(harmonizedTariffScheduleCodeUnit, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteHarmonizedTariffScheduleCodeUnitDescription(HarmonizedTariffScheduleCodeUnitDescription harmonizedTariffScheduleCodeUnitDescription, BasePK deletedBy) {
        harmonizedTariffScheduleCodeUnitDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(harmonizedTariffScheduleCodeUnitDescription.getHarmonizedTariffScheduleCodeUnitPK(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUnitDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit, BasePK deletedBy) {
        List<HarmonizedTariffScheduleCodeUnitDescription> harmonizedTariffScheduleCodeUnitDescriptions = getHarmonizedTariffScheduleCodeUnitDescriptionsByHarmonizedTariffScheduleCodeUnitForUpdate(harmonizedTariffScheduleCodeUnit);

        harmonizedTariffScheduleCodeUnitDescriptions.forEach((harmonizedTariffScheduleCodeUnitDescription) -> 
                deleteHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnitDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Uses
    // --------------------------------------------------------------------------------

    public HarmonizedTariffScheduleCodeUse createHarmonizedTariffScheduleCodeUse(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode,
            HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType, BasePK createdBy) {
        HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse = HarmonizedTariffScheduleCodeUseFactory.getInstance().create(harmonizedTariffScheduleCode,
                harmonizedTariffScheduleCodeUseType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(harmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUse.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        return getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUseTransferCache().getTransfer(harmonizedTariffScheduleCodeUse);
    }

    public List<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(UserVisit userVisit, List<HarmonizedTariffScheduleCodeUse> harmonizedTariffScheduleCodeUses) {
        List<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUseTransfers = new ArrayList<>(harmonizedTariffScheduleCodeUses.size());

        harmonizedTariffScheduleCodeUses.forEach((harmonizedTariffScheduleCodeUse) -> {
            harmonizedTariffScheduleCodeUseTransfers.add(getItemTransferCaches(userVisit).getHarmonizedTariffScheduleCodeUseTransferCache().getTransfer(harmonizedTariffScheduleCodeUse));
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

        sendEventUsingNames(harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodePK(), EventTypes.MODIFY.name(), harmonizedTariffScheduleCodeUse.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

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
        ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode = ItemHarmonizedTariffScheduleCodeFactory.getInstance().create();
        ItemHarmonizedTariffScheduleCodeDetail itemHarmonizedTariffScheduleCodeDetail = ItemHarmonizedTariffScheduleCodeDetailFactory.getInstance().create(session,
                itemHarmonizedTariffScheduleCode, item, countryGeoCode, harmonizedTariffScheduleCodeUseType, harmonizedTariffScheduleCode, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        itemHarmonizedTariffScheduleCode = ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemHarmonizedTariffScheduleCode.getPrimaryKey());
        itemHarmonizedTariffScheduleCode.setActiveDetail(itemHarmonizedTariffScheduleCodeDetail);
        itemHarmonizedTariffScheduleCode.setLastDetail(itemHarmonizedTariffScheduleCodeDetail);
        itemHarmonizedTariffScheduleCode.store();

        sendEventUsingNames(item.getPrimaryKey(), EventTypes.MODIFY.name(), itemHarmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        return getItemTransferCaches(userVisit).getItemHarmonizedTariffScheduleCodeTransferCache().getTransfer(itemHarmonizedTariffScheduleCode);
    }

    public List<ItemHarmonizedTariffScheduleCodeTransfer> getItemHarmonizedTariffScheduleCodeTransfers(UserVisit userVisit, List<ItemHarmonizedTariffScheduleCode> itemHarmonizedTariffScheduleCodes) {
        List<ItemHarmonizedTariffScheduleCodeTransfer> itemHarmonizedTariffScheduleCodeTransfers = new ArrayList<>(itemHarmonizedTariffScheduleCodes.size());
        ItemHarmonizedTariffScheduleCodeTransferCache itemHarmonizedTariffScheduleCodeTransferCache = getItemTransferCaches(userVisit).getItemHarmonizedTariffScheduleCodeTransferCache();

        itemHarmonizedTariffScheduleCodes.forEach((itemHarmonizedTariffScheduleCode) ->
                itemHarmonizedTariffScheduleCodeTransfers.add(itemHarmonizedTariffScheduleCodeTransferCache.getTransfer(itemHarmonizedTariffScheduleCode))
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
            ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode = ItemHarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemHarmonizedTariffScheduleCodeDetailValue.getItemHarmonizedTariffScheduleCodePK());
            ItemHarmonizedTariffScheduleCodeDetail itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getActiveDetailForUpdate();

            itemHarmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
            itemHarmonizedTariffScheduleCodeDetail.store();

            ItemHarmonizedTariffScheduleCodePK itemHarmonizedTariffScheduleCodePK = itemHarmonizedTariffScheduleCodeDetail.getItemHarmonizedTariffScheduleCodePK();
            ItemPK itemPK = itemHarmonizedTariffScheduleCodeDetail.getItemPK();
            GeoCodePK countryGeoCodePK = itemHarmonizedTariffScheduleCodeDetail.getCountryGeoCodePK();
            HarmonizedTariffScheduleCodeUseTypePK harmonizedTariffScheduleCodeUseTypePK = itemHarmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeUseTypePK();
            HarmonizedTariffScheduleCodePK harmonizedTariffScheduleCodePK = itemHarmonizedTariffScheduleCodeDetailValue.getHarmonizedTariffScheduleCodePK();

            itemHarmonizedTariffScheduleCodeDetail = ItemHarmonizedTariffScheduleCodeDetailFactory.getInstance().create(itemHarmonizedTariffScheduleCodePK,
                    itemPK, countryGeoCodePK, harmonizedTariffScheduleCodeUseTypePK, harmonizedTariffScheduleCodePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            itemHarmonizedTariffScheduleCode.setActiveDetail(itemHarmonizedTariffScheduleCodeDetail);
            itemHarmonizedTariffScheduleCode.setLastDetail(itemHarmonizedTariffScheduleCodeDetail);

            sendEventUsingNames(itemPK, EventTypes.MODIFY.name(), itemHarmonizedTariffScheduleCodePK, EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteItemHarmonizedTariffScheduleCode(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode, BasePK deletedBy) {
        ItemHarmonizedTariffScheduleCodeDetail itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getLastDetailForUpdate();
        itemHarmonizedTariffScheduleCodeDetail.setThruTime(session.START_TIME_LONG);
        itemHarmonizedTariffScheduleCode.setActiveDetail(null);
        itemHarmonizedTariffScheduleCode.store();

        sendEventUsingNames(itemHarmonizedTariffScheduleCodeDetail.getItemPK(), EventTypes.MODIFY.name(), itemHarmonizedTariffScheduleCode.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
        var itemResultTransfers = new ArrayList<ItemResultTransfer>(searchControl.countSearchResults(userVisitSearch));;
        var includeItem = false;

        // ItemTransfer objects are not included unless specifically requested;
        var options = session.getOptions();
        if(options != null) {
            includeItem = options.contains(SearchOptions.ItemResultIncludeItem);
        }

        if(userVisitSearch.getSearch().getCachedSearch() != null) {
            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);
        }

        try (ResultSet rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
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

    public List<ItemResultObject> getItemResultObjects(UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var itemResultObjects = new ArrayList<ItemResultObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            var itemControl = Session.getModelController(ItemControl.class);

            while(rs.next()) {
                var item = itemControl.getItemByPK(new ItemPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                itemResultObjects.add(new ItemResultObject(item));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return itemResultObjects;
    }

}
