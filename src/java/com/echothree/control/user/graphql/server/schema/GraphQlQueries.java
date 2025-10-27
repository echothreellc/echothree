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

package com.echothree.control.user.graphql.server.schema;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.server.command.GetCurrenciesCommand;
import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountCategoriesCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountCategoryCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountClassCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountClassesCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountTypeCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountTypesCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountsCommand;
import com.echothree.control.user.accounting.server.command.GetGlResourceTypeCommand;
import com.echothree.control.user.accounting.server.command.GetGlResourceTypesCommand;
import com.echothree.control.user.accounting.server.command.GetItemAccountingCategoriesCommand;
import com.echothree.control.user.accounting.server.command.GetItemAccountingCategoryCommand;
import com.echothree.control.user.accounting.server.command.GetSymbolPositionCommand;
import com.echothree.control.user.accounting.server.command.GetSymbolPositionsCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionEntityRoleTypeCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionEntityRoleTypesCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionGlAccountCategoriesCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionGlAccountCategoryCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionGroupCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionGroupsCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionTimeTypeCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionTimeTypesCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionTypeCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionTypesCommand;
import com.echothree.control.user.accounting.server.command.GetTransactionsCommand;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.server.command.GetCancellationKindCommand;
import com.echothree.control.user.cancellationpolicy.server.command.GetCancellationKindsCommand;
import com.echothree.control.user.cancellationpolicy.server.command.GetCancellationPoliciesCommand;
import com.echothree.control.user.cancellationpolicy.server.command.GetCancellationPolicyCommand;
import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.server.command.GetContentCatalogCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogsCommand;
import com.echothree.control.user.content.server.command.GetContentCategoriesCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCollectionCommand;
import com.echothree.control.user.content.server.command.GetContentCollectionsCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreaCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreaTypeCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreaTypesCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreasCommand;
import com.echothree.control.user.content.server.command.GetContentPageCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutAreaCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutAreasCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutsCommand;
import com.echothree.control.user.content.server.command.GetContentPagesCommand;
import com.echothree.control.user.content.server.command.GetContentSectionCommand;
import com.echothree.control.user.content.server.command.GetContentSectionsCommand;
import com.echothree.control.user.content.server.command.GetContentWebAddressCommand;
import com.echothree.control.user.content.server.command.GetContentWebAddressesCommand;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.server.command.GetAppearanceCommand;
import com.echothree.control.user.core.server.command.GetAppearancesCommand;
import com.echothree.control.user.core.server.command.GetColorCommand;
import com.echothree.control.user.core.server.command.GetColorsCommand;
import com.echothree.control.user.core.server.command.GetComponentVendorCommand;
import com.echothree.control.user.core.server.command.GetComponentVendorsCommand;
import com.echothree.control.user.core.server.command.GetEntityAliasCommand;
import com.echothree.control.user.core.server.command.GetEntityAliasTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAliasTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityAliasesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityAttributeGroupsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributesCommand;
import com.echothree.control.user.core.server.command.GetEntityInstanceCommand;
import com.echothree.control.user.core.server.command.GetEntityInstancesCommand;
import com.echothree.control.user.core.server.command.GetEntityTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityTypesCommand;
import com.echothree.control.user.core.server.command.GetFontStyleCommand;
import com.echothree.control.user.core.server.command.GetFontStylesCommand;
import com.echothree.control.user.core.server.command.GetFontWeightCommand;
import com.echothree.control.user.core.server.command.GetFontWeightsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypesCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationsCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationsCommand;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.server.command.GetCustomerCommand;
import com.echothree.control.user.customer.server.command.GetCustomerTypeCommand;
import com.echothree.control.user.customer.server.command.GetCustomerTypesCommand;
import com.echothree.control.user.customer.server.command.GetCustomersCommand;
import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.server.command.GetEmployeeCommand;
import com.echothree.control.user.employee.server.command.GetEmployeesCommand;
import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentAmountCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentAmountsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentFixedAmountCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentFixedAmountsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentPercentCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentPercentsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentSourceCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentSourcesCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentTypesCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentsCommand;
import com.echothree.control.user.filter.server.command.GetFilterCommand;
import com.echothree.control.user.filter.server.command.GetFilterKindCommand;
import com.echothree.control.user.filter.server.command.GetFilterKindsCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepsCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypesCommand;
import com.echothree.control.user.filter.server.command.GetFiltersCommand;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.server.command.GetGeoCodeCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeScopeCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeScopesCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeTypeCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeTypesCommand;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.server.command.GetAllocationPrioritiesCommand;
import com.echothree.control.user.inventory.server.command.GetAllocationPriorityCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionsCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryTransactionTypeCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryTransactionTypesCommand;
import com.echothree.control.user.inventory.server.command.GetLotCommand;
import com.echothree.control.user.inventory.server.command.GetLotsCommand;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.server.command.GetItemAliasChecksumTypeCommand;
import com.echothree.control.user.item.server.command.GetItemAliasChecksumTypesCommand;
import com.echothree.control.user.item.server.command.GetItemAliasCommand;
import com.echothree.control.user.item.server.command.GetItemAliasTypeCommand;
import com.echothree.control.user.item.server.command.GetItemAliasTypesCommand;
import com.echothree.control.user.item.server.command.GetItemAliasesCommand;
import com.echothree.control.user.item.server.command.GetItemCategoriesCommand;
import com.echothree.control.user.item.server.command.GetItemCategoryCommand;
import com.echothree.control.user.item.server.command.GetItemCommand;
import com.echothree.control.user.item.server.command.GetItemDeliveryTypeCommand;
import com.echothree.control.user.item.server.command.GetItemDeliveryTypesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUseCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUseTypeCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUseTypesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUsesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionsCommand;
import com.echothree.control.user.item.server.command.GetItemImageTypeCommand;
import com.echothree.control.user.item.server.command.GetItemImageTypesCommand;
import com.echothree.control.user.item.server.command.GetItemInventoryTypeCommand;
import com.echothree.control.user.item.server.command.GetItemInventoryTypesCommand;
import com.echothree.control.user.item.server.command.GetItemPriceCommand;
import com.echothree.control.user.item.server.command.GetItemPriceTypeCommand;
import com.echothree.control.user.item.server.command.GetItemPriceTypesCommand;
import com.echothree.control.user.item.server.command.GetItemPricesCommand;
import com.echothree.control.user.item.server.command.GetItemTypeCommand;
import com.echothree.control.user.item.server.command.GetItemTypesCommand;
import com.echothree.control.user.item.server.command.GetItemUnitOfMeasureTypeCommand;
import com.echothree.control.user.item.server.command.GetItemUnitOfMeasureTypesCommand;
import com.echothree.control.user.item.server.command.GetItemUseTypeCommand;
import com.echothree.control.user.item.server.command.GetItemUseTypesCommand;
import com.echothree.control.user.item.server.command.GetItemVolumeTypeCommand;
import com.echothree.control.user.item.server.command.GetItemVolumeTypesCommand;
import com.echothree.control.user.item.server.command.GetItemWeightTypeCommand;
import com.echothree.control.user.item.server.command.GetItemWeightTypesCommand;
import com.echothree.control.user.item.server.command.GetItemsCommand;
import com.echothree.control.user.item.server.command.GetRelatedItemCommand;
import com.echothree.control.user.item.server.command.GetRelatedItemTypeCommand;
import com.echothree.control.user.item.server.command.GetRelatedItemTypesCommand;
import com.echothree.control.user.item.server.command.GetRelatedItemsCommand;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.server.command.GetOfferCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemPriceCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemPricesCommand;
import com.echothree.control.user.offer.server.command.GetOfferItemsCommand;
import com.echothree.control.user.offer.server.command.GetOfferNameElementCommand;
import com.echothree.control.user.offer.server.command.GetOfferNameElementsCommand;
import com.echothree.control.user.offer.server.command.GetOfferUseCommand;
import com.echothree.control.user.offer.server.command.GetOfferUsesCommand;
import com.echothree.control.user.offer.server.command.GetOffersCommand;
import com.echothree.control.user.offer.server.command.GetUseCommand;
import com.echothree.control.user.offer.server.command.GetUseNameElementCommand;
import com.echothree.control.user.offer.server.command.GetUseNameElementsCommand;
import com.echothree.control.user.offer.server.command.GetUseTypeCommand;
import com.echothree.control.user.offer.server.command.GetUseTypesCommand;
import com.echothree.control.user.offer.server.command.GetUsesCommand;
import com.echothree.control.user.order.common.OrderUtil;
import com.echothree.control.user.order.server.command.GetOrderPrioritiesCommand;
import com.echothree.control.user.order.server.command.GetOrderPriorityCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypeCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypesCommand;
import com.echothree.control.user.order.server.command.GetOrderTypeCommand;
import com.echothree.control.user.order.server.command.GetOrderTypesCommand;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.server.command.GetCompaniesCommand;
import com.echothree.control.user.party.server.command.GetCompanyCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatsCommand;
import com.echothree.control.user.party.server.command.GetDepartmentCommand;
import com.echothree.control.user.party.server.command.GetDepartmentsCommand;
import com.echothree.control.user.party.server.command.GetDivisionCommand;
import com.echothree.control.user.party.server.command.GetDivisionsCommand;
import com.echothree.control.user.party.server.command.GetLanguageCommand;
import com.echothree.control.user.party.server.command.GetLanguagesCommand;
import com.echothree.control.user.party.server.command.GetNameSuffixesCommand;
import com.echothree.control.user.party.server.command.GetPartiesCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasTypeCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasTypesCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasesCommand;
import com.echothree.control.user.party.server.command.GetPartyCommand;
import com.echothree.control.user.party.server.command.GetPartyTypeCommand;
import com.echothree.control.user.party.server.command.GetPartyTypesCommand;
import com.echothree.control.user.party.server.command.GetPersonalTitlesCommand;
import com.echothree.control.user.party.server.command.GetRoleTypeCommand;
import com.echothree.control.user.party.server.command.GetRoleTypesCommand;
import com.echothree.control.user.party.server.command.GetTimeZoneCommand;
import com.echothree.control.user.party.server.command.GetTimeZonesCommand;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.server.command.GetPaymentMethodTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentMethodTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorActionTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorActionTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorResultCodeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorResultCodesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionsCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCodeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCodeTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorsCommand;
import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.server.command.GetQueueTypeCommand;
import com.echothree.control.user.queue.server.command.GetQueueTypesCommand;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.server.command.GetReturnKindCommand;
import com.echothree.control.user.returnpolicy.server.command.GetReturnKindsCommand;
import com.echothree.control.user.returnpolicy.server.command.GetReturnPoliciesCommand;
import com.echothree.control.user.returnpolicy.server.command.GetReturnPolicyCommand;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.CheckItemSpellingResult;
import com.echothree.control.user.search.server.command.GetComponentVendorResultsCommand;
import com.echothree.control.user.search.server.command.GetContentCatalogItemResultsCommand;
import com.echothree.control.user.search.server.command.GetContentCatalogResultsCommand;
import com.echothree.control.user.search.server.command.GetContentCategoryResultsCommand;
import com.echothree.control.user.search.server.command.GetCustomerResultsCommand;
import com.echothree.control.user.search.server.command.GetEmployeeResultsCommand;
import com.echothree.control.user.search.server.command.GetEntityAliasTypeResultsCommand;
import com.echothree.control.user.search.server.command.GetEntityAttributeGroupResultsCommand;
import com.echothree.control.user.search.server.command.GetEntityAttributeResultsCommand;
import com.echothree.control.user.search.server.command.GetEntityListItemResultsCommand;
import com.echothree.control.user.search.server.command.GetEntityTypeResultsCommand;
import com.echothree.control.user.search.server.command.GetItemResultsCommand;
import com.echothree.control.user.search.server.command.GetSearchCheckSpellingActionTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchCheckSpellingActionTypesCommand;
import com.echothree.control.user.search.server.command.GetSearchDefaultOperatorCommand;
import com.echothree.control.user.search.server.command.GetSearchDefaultOperatorsCommand;
import com.echothree.control.user.search.server.command.GetSearchKindCommand;
import com.echothree.control.user.search.server.command.GetSearchKindsCommand;
import com.echothree.control.user.search.server.command.GetSearchResultActionTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchResultActionTypesCommand;
import com.echothree.control.user.search.server.command.GetSearchSortDirectionCommand;
import com.echothree.control.user.search.server.command.GetSearchSortDirectionsCommand;
import com.echothree.control.user.search.server.command.GetSearchSortOrderCommand;
import com.echothree.control.user.search.server.command.GetSearchSortOrdersCommand;
import com.echothree.control.user.search.server.command.GetSearchTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchTypesCommand;
import com.echothree.control.user.search.server.command.GetSearchUseTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchUseTypesCommand;
import com.echothree.control.user.search.server.command.GetShippingMethodResultsCommand;
import com.echothree.control.user.search.server.command.GetVendorResultsCommand;
import com.echothree.control.user.search.server.command.GetWarehouseResultsCommand;
import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.server.command.GetSecurityRoleCommand;
import com.echothree.control.user.security.server.command.GetSecurityRoleGroupCommand;
import com.echothree.control.user.security.server.command.GetSecurityRoleGroupsCommand;
import com.echothree.control.user.security.server.command.GetSecurityRolesCommand;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.server.command.GetSelectorCommand;
import com.echothree.control.user.selector.server.command.GetSelectorKindCommand;
import com.echothree.control.user.selector.server.command.GetSelectorKindsCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypeCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypesCommand;
import com.echothree.control.user.selector.server.command.GetSelectorsCommand;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.server.command.GetSequenceChecksumTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceChecksumTypesCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceEncoderTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceEncoderTypesCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceTypesCommand;
import com.echothree.control.user.sequence.server.command.GetSequencesCommand;
import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.server.command.GetFreeOnBoardCommand;
import com.echothree.control.user.shipment.server.command.GetFreeOnBoardsCommand;
import com.echothree.control.user.shipping.common.ShippingUtil;
import com.echothree.control.user.shipping.server.command.GetShippingMethodCommand;
import com.echothree.control.user.shipping.server.command.GetShippingMethodsCommand;
import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.server.command.GetEntityTagCommand;
import com.echothree.control.user.tag.server.command.GetEntityTagsCommand;
import com.echothree.control.user.tag.server.command.GetTagCommand;
import com.echothree.control.user.tag.server.command.GetTagScopeCommand;
import com.echothree.control.user.tag.server.command.GetTagScopeEntityTypeCommand;
import com.echothree.control.user.tag.server.command.GetTagScopeEntityTypesCommand;
import com.echothree.control.user.tag.server.command.GetTagScopesCommand;
import com.echothree.control.user.tag.server.command.GetTagsCommand;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.server.command.GetTermCommand;
import com.echothree.control.user.term.server.command.GetTermTypeCommand;
import com.echothree.control.user.term.server.command.GetTermTypesCommand;
import com.echothree.control.user.term.server.command.GetTermsCommand;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUsesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindsCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypesCommand;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.server.command.GetRecoveryQuestionCommand;
import com.echothree.control.user.user.server.command.GetRecoveryQuestionsCommand;
import com.echothree.control.user.user.server.command.GetUserLoginCommand;
import com.echothree.control.user.user.server.command.GetUserVisitGroupCommand;
import com.echothree.control.user.user.server.command.GetUserVisitGroupsCommand;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.server.command.GetItemPurchasingCategoriesCommand;
import com.echothree.control.user.vendor.server.command.GetItemPurchasingCategoryCommand;
import com.echothree.control.user.vendor.server.command.GetVendorCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemCostCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemCostsCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemsCommand;
import com.echothree.control.user.vendor.server.command.GetVendorTypeCommand;
import com.echothree.control.user.vendor.server.command.GetVendorTypesCommand;
import com.echothree.control.user.vendor.server.command.GetVendorsCommand;
import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.server.command.GetLocationUseTypeCommand;
import com.echothree.control.user.warehouse.server.command.GetLocationUseTypesCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehouseCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehouseTypeCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehouseTypesCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehousesCommand;
import com.echothree.control.user.wishlist.common.WishlistUtil;
import com.echothree.control.user.wishlist.server.command.GetWishlistPrioritiesCommand;
import com.echothree.control.user.wishlist.server.command.GetWishlistPriorityCommand;
import com.echothree.control.user.wishlist.server.command.GetWishlistTypeCommand;
import com.echothree.control.user.wishlist.server.command.GetWishlistTypesCommand;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.server.command.GetWorkflowCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationPartyTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationPartyTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSecurityRoleCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSecurityRolesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSelectorCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationSelectorsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowDestinationsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntityStatusesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntityTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntityTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntrancePartyTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntrancePartyTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSecurityRoleCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSecurityRolesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSelectorCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceSelectorsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntranceStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowEntrancesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowSelectorKindCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowSelectorKindsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowsCommand;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.accounting.server.graphql.GlAccountCategoryObject;
import com.echothree.model.control.accounting.server.graphql.GlAccountClassObject;
import com.echothree.model.control.accounting.server.graphql.GlAccountObject;
import com.echothree.model.control.accounting.server.graphql.GlAccountTypeObject;
import com.echothree.model.control.accounting.server.graphql.GlResourceTypeObject;
import com.echothree.model.control.accounting.server.graphql.ItemAccountingCategoryObject;
import com.echothree.model.control.accounting.server.graphql.SymbolPositionObject;
import com.echothree.model.control.accounting.server.graphql.TransactionEntityRoleTypeObject;
import com.echothree.model.control.accounting.server.graphql.TransactionGlAccountCategoryObject;
import com.echothree.model.control.accounting.server.graphql.TransactionGroupObject;
import com.echothree.model.control.accounting.server.graphql.TransactionObject;
import com.echothree.model.control.accounting.server.graphql.TransactionTimeTypeObject;
import com.echothree.model.control.accounting.server.graphql.TransactionTypeObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationKindObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.content.server.graphql.ContentCatalogItemObject;
import com.echothree.model.control.content.server.graphql.ContentCatalogObject;
import com.echothree.model.control.content.server.graphql.ContentCategoryItemObject;
import com.echothree.model.control.content.server.graphql.ContentCategoryObject;
import com.echothree.model.control.content.server.graphql.ContentCollectionObject;
import com.echothree.model.control.content.server.graphql.ContentPageAreaObject;
import com.echothree.model.control.content.server.graphql.ContentPageAreaTypeObject;
import com.echothree.model.control.content.server.graphql.ContentPageLayoutAreaObject;
import com.echothree.model.control.content.server.graphql.ContentPageLayoutObject;
import com.echothree.model.control.content.server.graphql.ContentPageObject;
import com.echothree.model.control.content.server.graphql.ContentSectionObject;
import com.echothree.model.control.content.server.graphql.ContentWebAddressObject;
import com.echothree.model.control.core.server.graphql.AppearanceObject;
import com.echothree.model.control.core.server.graphql.ColorObject;
import com.echothree.model.control.core.server.graphql.ComponentVendorObject;
import com.echothree.model.control.core.server.graphql.EntityAliasObject;
import com.echothree.model.control.core.server.graphql.EntityAliasTypeObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeEntityAttributeGroupObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeGroupObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeTypeObject;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.core.server.graphql.EntityTypeObject;
import com.echothree.model.control.core.server.graphql.FontStyleObject;
import com.echothree.model.control.core.server.graphql.FontWeightObject;
import com.echothree.model.control.core.server.graphql.MimeTypeFileExtensionObject;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.core.server.graphql.MimeTypeUsageTypeObject;
import com.echothree.model.control.core.server.graphql.TextDecorationObject;
import com.echothree.model.control.core.server.graphql.TextTransformationObject;
import com.echothree.model.control.customer.server.graphql.CustomerObject;
import com.echothree.model.control.customer.server.graphql.CustomerTypeObject;
import com.echothree.model.control.employee.server.graphql.EmployeeObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentAmountObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentFixedAmountObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentPercentObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentSourceObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentTypeObject;
import com.echothree.model.control.filter.server.graphql.FilterKindObject;
import com.echothree.model.control.filter.server.graphql.FilterObject;
import com.echothree.model.control.filter.server.graphql.FilterStepObject;
import com.echothree.model.control.filter.server.graphql.FilterTypeObject;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.graphql.GeoCodeObject;
import com.echothree.model.control.geo.server.graphql.GeoCodeScopeObject;
import com.echothree.model.control.geo.server.graphql.GeoCodeTypeObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import static com.echothree.model.control.graphql.server.util.BaseGraphQl.getUserVisitPK;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.inventory.server.graphql.AllocationPriorityObject;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.InventoryTransactionTypeObject;
import com.echothree.model.control.inventory.server.graphql.LotObject;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemAliasChecksumTypeObject;
import com.echothree.model.control.item.server.graphql.ItemAliasObject;
import com.echothree.model.control.item.server.graphql.ItemAliasTypeObject;
import com.echothree.model.control.item.server.graphql.ItemCategoryObject;
import com.echothree.model.control.item.server.graphql.ItemDeliveryTypeObject;
import com.echothree.model.control.item.server.graphql.ItemDescriptionObject;
import com.echothree.model.control.item.server.graphql.ItemDescriptionTypeObject;
import com.echothree.model.control.item.server.graphql.ItemDescriptionTypeUseObject;
import com.echothree.model.control.item.server.graphql.ItemDescriptionTypeUseTypeObject;
import com.echothree.model.control.item.server.graphql.ItemImageTypeObject;
import com.echothree.model.control.item.server.graphql.ItemInventoryTypeObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemPriceObject;
import com.echothree.model.control.item.server.graphql.ItemPriceTypeObject;
import com.echothree.model.control.item.server.graphql.ItemTypeObject;
import com.echothree.model.control.item.server.graphql.ItemUnitOfMeasureTypeObject;
import com.echothree.model.control.item.server.graphql.ItemUseTypeObject;
import com.echothree.model.control.item.server.graphql.ItemVolumeTypeObject;
import com.echothree.model.control.item.server.graphql.ItemWeightTypeObject;
import com.echothree.model.control.item.server.graphql.RelatedItemObject;
import com.echothree.model.control.item.server.graphql.RelatedItemTypeObject;
import com.echothree.model.control.offer.server.graphql.OfferItemObject;
import com.echothree.model.control.offer.server.graphql.OfferItemPriceObject;
import com.echothree.model.control.offer.server.graphql.OfferNameElementObject;
import com.echothree.model.control.offer.server.graphql.OfferObject;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.offer.server.graphql.UseNameElementObject;
import com.echothree.model.control.offer.server.graphql.UseObject;
import com.echothree.model.control.offer.server.graphql.UseTypeObject;
import com.echothree.model.control.order.server.graphql.OrderPriorityObject;
import com.echothree.model.control.order.server.graphql.OrderTimeTypeObject;
import com.echothree.model.control.order.server.graphql.OrderTypeObject;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.graphql.CompanyObject;
import com.echothree.model.control.party.server.graphql.DateTimeFormatObject;
import com.echothree.model.control.party.server.graphql.DepartmentObject;
import com.echothree.model.control.party.server.graphql.DivisionObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.NameSuffixObject;
import com.echothree.model.control.party.server.graphql.PartyAliasObject;
import com.echothree.model.control.party.server.graphql.PartyAliasTypeObject;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartyTypeObject;
import com.echothree.model.control.party.server.graphql.PersonalTitleObject;
import com.echothree.model.control.party.server.graphql.RoleTypeObject;
import com.echothree.model.control.party.server.graphql.TimeZoneObject;
import com.echothree.model.control.payment.server.graphql.PaymentMethodTypeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorActionTypeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorResultCodeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTransactionObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTypeCodeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTypeCodeTypeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTypeObject;
import com.echothree.model.control.queue.server.graphql.QueueTypeObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnKindObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.graphql.CheckItemSpellingObject;
import com.echothree.model.control.search.server.graphql.ComponentVendorResultsObject;
import com.echothree.model.control.search.server.graphql.ContentCatalogItemResultsObject;
import com.echothree.model.control.search.server.graphql.ContentCatalogResultsObject;
import com.echothree.model.control.search.server.graphql.ContentCategoryResultsObject;
import com.echothree.model.control.search.server.graphql.CustomerResultsObject;
import com.echothree.model.control.search.server.graphql.EmployeeResultsObject;
import com.echothree.model.control.search.server.graphql.EntityAliasTypeResultsObject;
import com.echothree.model.control.search.server.graphql.EntityAttributeGroupResultsObject;
import com.echothree.model.control.search.server.graphql.EntityAttributeResultsObject;
import com.echothree.model.control.search.server.graphql.EntityListItemResultsObject;
import com.echothree.model.control.search.server.graphql.EntityTypeResultsObject;
import com.echothree.model.control.search.server.graphql.ItemResultsObject;
import com.echothree.model.control.search.server.graphql.SearchCheckSpellingActionTypeObject;
import com.echothree.model.control.search.server.graphql.SearchDefaultOperatorObject;
import com.echothree.model.control.search.server.graphql.SearchKindObject;
import com.echothree.model.control.search.server.graphql.SearchResultActionTypeObject;
import com.echothree.model.control.search.server.graphql.SearchSortDirectionObject;
import com.echothree.model.control.search.server.graphql.SearchSortOrderObject;
import com.echothree.model.control.search.server.graphql.SearchTypeObject;
import com.echothree.model.control.search.server.graphql.SearchUseTypeObject;
import com.echothree.model.control.search.server.graphql.ShippingMethodResultsObject;
import com.echothree.model.control.search.server.graphql.VendorResultsObject;
import com.echothree.model.control.search.server.graphql.WarehouseResultsObject;
import com.echothree.model.control.security.server.graphql.SecurityRoleGroupObject;
import com.echothree.model.control.security.server.graphql.SecurityRoleObject;
import com.echothree.model.control.selector.server.graphql.SelectorKindObject;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceChecksumTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceEncoderTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.shipment.server.graphql.FreeOnBoardObject;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.control.shipping.server.graphql.ShippingMethodObject;
import com.echothree.model.control.tag.server.graphql.EntityTagObject;
import com.echothree.model.control.tag.server.graphql.TagObject;
import com.echothree.model.control.tag.server.graphql.TagScopeEntityTypeObject;
import com.echothree.model.control.tag.server.graphql.TagScopeObject;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.graphql.TermObject;
import com.echothree.model.control.term.server.graphql.TermTypeObject;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseTypeObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.user.server.graphql.RecoveryQuestionObject;
import com.echothree.model.control.user.server.graphql.UserLoginObject;
import com.echothree.model.control.user.server.graphql.UserSessionObject;
import com.echothree.model.control.user.server.graphql.UserVisitGroupObject;
import com.echothree.model.control.user.server.graphql.UserVisitObject;
import com.echothree.model.control.vendor.server.graphql.ItemPurchasingCategoryObject;
import com.echothree.model.control.vendor.server.graphql.VendorItemCostObject;
import com.echothree.model.control.vendor.server.graphql.VendorItemObject;
import com.echothree.model.control.vendor.server.graphql.VendorObject;
import com.echothree.model.control.vendor.server.graphql.VendorTypeObject;
import com.echothree.model.control.warehouse.server.graphql.LocationUseTypeObject;
import com.echothree.model.control.warehouse.server.graphql.WarehouseObject;
import com.echothree.model.control.warehouse.server.graphql.WarehouseTypeObject;
import com.echothree.model.control.wishlist.server.graphql.WishlistPriorityObject;
import com.echothree.model.control.wishlist.server.graphql.WishlistTypeObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowDestinationObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowDestinationPartyTypeObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowDestinationSecurityRoleObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowDestinationSelectorObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowDestinationStepObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityTypeObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntrancePartyTypeObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceSecurityRoleObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceSelectorObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceStepObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSelectorKindObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowStepObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowStepTypeObject;
import com.echothree.model.data.accounting.common.CurrencyConstants;
import com.echothree.model.data.accounting.common.GlAccountCategoryConstants;
import com.echothree.model.data.accounting.common.GlAccountClassConstants;
import com.echothree.model.data.accounting.common.GlAccountConstants;
import com.echothree.model.data.accounting.common.GlAccountTypeConstants;
import com.echothree.model.data.accounting.common.GlResourceTypeConstants;
import com.echothree.model.data.accounting.common.ItemAccountingCategoryConstants;
import com.echothree.model.data.accounting.common.SymbolPositionConstants;
import com.echothree.model.data.accounting.common.TransactionConstants;
import com.echothree.model.data.accounting.common.TransactionEntityRoleTypeConstants;
import com.echothree.model.data.accounting.common.TransactionGlAccountCategoryConstants;
import com.echothree.model.data.accounting.common.TransactionGroupConstants;
import com.echothree.model.data.accounting.common.TransactionTimeTypeConstants;
import com.echothree.model.data.accounting.common.TransactionTypeConstants;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.cancellationpolicy.common.CancellationKindConstants;
import com.echothree.model.data.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.content.common.ContentCatalogConstants;
import com.echothree.model.data.content.common.ContentCatalogItemConstants;
import com.echothree.model.data.content.common.ContentCategoryConstants;
import com.echothree.model.data.content.common.ContentCategoryItemConstants;
import com.echothree.model.data.content.common.ContentCollectionConstants;
import com.echothree.model.data.content.common.ContentPageAreaConstants;
import com.echothree.model.data.content.common.ContentPageAreaTypeConstants;
import com.echothree.model.data.content.common.ContentPageConstants;
import com.echothree.model.data.content.common.ContentPageLayoutAreaConstants;
import com.echothree.model.data.content.common.ContentPageLayoutConstants;
import com.echothree.model.data.content.common.ContentSectionConstants;
import com.echothree.model.data.content.common.ContentWebAddressConstants;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.core.common.AppearanceConstants;
import com.echothree.model.data.core.common.ColorConstants;
import com.echothree.model.data.core.common.ComponentVendorConstants;
import com.echothree.model.data.core.common.EntityAliasConstants;
import com.echothree.model.data.core.common.EntityAliasTypeConstants;
import com.echothree.model.data.core.common.EntityAttributeConstants;
import com.echothree.model.data.core.common.EntityAttributeEntityAttributeGroupConstants;
import com.echothree.model.data.core.common.EntityAttributeGroupConstants;
import com.echothree.model.data.core.common.EntityAttributeTypeConstants;
import com.echothree.model.data.core.common.EntityInstanceConstants;
import com.echothree.model.data.core.common.EntityTypeConstants;
import com.echothree.model.data.core.common.FontStyleConstants;
import com.echothree.model.data.core.common.FontWeightConstants;
import com.echothree.model.data.core.common.MimeTypeConstants;
import com.echothree.model.data.core.common.TextDecorationConstants;
import com.echothree.model.data.core.common.TextTransformationConstants;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.customer.common.CustomerConstants;
import com.echothree.model.data.customer.common.CustomerTypeConstants;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.employee.common.PartyEmployeeConstants;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.filter.common.FilterKindConstants;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.geo.common.GeoCodeScopeConstants;
import com.echothree.model.data.geo.common.GeoCodeTypeConstants;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.inventory.common.AllocationPriorityConstants;
import com.echothree.model.data.inventory.common.InventoryConditionConstants;
import com.echothree.model.data.inventory.common.InventoryTransactionTypeConstants;
import com.echothree.model.data.inventory.common.LotConstants;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.item.common.ItemAliasChecksumTypeConstants;
import com.echothree.model.data.item.common.ItemAliasTypeConstants;
import com.echothree.model.data.item.common.ItemCategoryConstants;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.model.data.item.common.ItemDeliveryTypeConstants;
import com.echothree.model.data.item.common.ItemDescriptionTypeUseTypeConstants;
import com.echothree.model.data.item.common.ItemImageTypeConstants;
import com.echothree.model.data.item.common.ItemInventoryTypeConstants;
import com.echothree.model.data.item.common.ItemPriceTypeConstants;
import com.echothree.model.data.item.common.ItemTypeConstants;
import com.echothree.model.data.item.common.ItemUnitOfMeasureTypeConstants;
import com.echothree.model.data.item.common.ItemUseTypeConstants;
import com.echothree.model.data.item.common.ItemVolumeTypeConstants;
import com.echothree.model.data.item.common.ItemWeightTypeConstants;
import com.echothree.model.data.item.common.RelatedItemTypeConstants;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUse;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.item.server.entity.ItemVolumeType;
import com.echothree.model.data.item.server.entity.ItemWeightType;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.offer.common.OfferConstants;
import com.echothree.model.data.offer.common.UseConstants;
import com.echothree.model.data.offer.common.UseTypeConstants;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.order.common.OrderPriorityConstants;
import com.echothree.model.data.order.common.OrderTimeTypeConstants;
import com.echothree.model.data.order.common.OrderTypeConstants;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.party.common.DateTimeFormatConstants;
import com.echothree.model.data.party.common.LanguageConstants;
import com.echothree.model.data.party.common.NameSuffixConstants;
import com.echothree.model.data.party.common.PartyCompanyConstants;
import com.echothree.model.data.party.common.PartyConstants;
import com.echothree.model.data.party.common.PartyTypeConstants;
import com.echothree.model.data.party.common.PersonalTitleConstants;
import com.echothree.model.data.party.common.RoleTypeConstants;
import com.echothree.model.data.party.common.TimeZoneConstants;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyAlias;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.queue.common.QueueTypeConstants;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.returnpolicy.common.ReturnKindConstants;
import com.echothree.model.data.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.search.common.SearchCheckSpellingActionTypeConstants;
import com.echothree.model.data.search.common.SearchDefaultOperatorConstants;
import com.echothree.model.data.search.common.SearchKindConstants;
import com.echothree.model.data.search.common.SearchResultActionTypeConstants;
import com.echothree.model.data.search.common.SearchSortDirectionConstants;
import com.echothree.model.data.search.common.SearchSortOrderConstants;
import com.echothree.model.data.search.common.SearchTypeConstants;
import com.echothree.model.data.search.common.SearchUseTypeConstants;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.security.common.SecurityRoleConstants;
import com.echothree.model.data.security.common.SecurityRoleGroupConstants;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.selector.common.SelectorKindConstants;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.sequence.common.SequenceChecksumTypeConstants;
import com.echothree.model.data.sequence.common.SequenceEncoderTypeConstants;
import com.echothree.model.data.sequence.common.SequenceTypeConstants;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.common.FreeOnBoardConstants;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipping.common.ShippingMethodConstants;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.tag.common.TagConstants;
import com.echothree.model.data.tag.common.TagScopeConstants;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.term.common.TermConstants;
import com.echothree.model.data.term.common.TermTypeConstants;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.model.data.uom.common.UnitOfMeasureKindConstants;
import com.echothree.model.data.uom.common.UnitOfMeasureKindUseTypeConstants;
import com.echothree.model.data.uom.common.UnitOfMeasureTypeConstants;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.RecoveryQuestionConstants;
import com.echothree.model.data.user.common.UserVisitGroupConstants;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserVisitGroup;
import com.echothree.model.data.vendor.common.ItemPurchasingCategoryConstants;
import com.echothree.model.data.vendor.common.VendorConstants;
import com.echothree.model.data.vendor.common.VendorTypeConstants;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemCost;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.warehouse.common.LocationUseTypeConstants;
import com.echothree.model.data.warehouse.common.WarehouseConstants;
import com.echothree.model.data.warehouse.common.WarehouseTypeConstants;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.model.data.wishlist.common.WishlistPriorityConstants;
import com.echothree.model.data.wishlist.common.WishlistTypeConstants;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.workflow.common.WorkflowConstants;
import com.echothree.model.data.workflow.common.WorkflowDestinationConstants;
import com.echothree.model.data.workflow.common.WorkflowDestinationPartyTypeConstants;
import com.echothree.model.data.workflow.common.WorkflowDestinationSecurityRoleConstants;
import com.echothree.model.data.workflow.common.WorkflowDestinationStepConstants;
import com.echothree.model.data.workflow.common.WorkflowEntityStatusConstants;
import com.echothree.model.data.workflow.common.WorkflowEntityTypeConstants;
import com.echothree.model.data.workflow.common.WorkflowEntranceConstants;
import com.echothree.model.data.workflow.common.WorkflowEntrancePartyTypeConstants;
import com.echothree.model.data.workflow.common.WorkflowEntranceSecurityRoleConstants;
import com.echothree.model.data.workflow.common.WorkflowEntranceStepConstants;
import com.echothree.model.data.workflow.common.WorkflowStepConstants;
import com.echothree.model.data.workflow.common.WorkflowStepTypeConstants;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSelector;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSelector;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceStep;
import com.echothree.model.data.workflow.server.entity.WorkflowSelectorKind;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLID;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.emptyList;
import java.util.stream.Collectors;
import javax.naming.NamingException;

@GraphQLName("query")
public interface GraphQlQueries {

    @GraphQLField
    @GraphQLName("searchKind")
    static SearchKindObject searchKind(final DataFetchingEnvironment env,
            @GraphQLName("searchKindName") final String searchKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchKind searchKind;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchKindForm();

            commandForm.setSearchKindName(searchKindName);
            commandForm.setUuid(id);

            searchKind = new GetSearchKindCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchKind == null ? null : new SearchKindObject(searchKind);
    }

    @GraphQLField
    @GraphQLName("searchKinds")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchKindObject> searchKinds(final DataFetchingEnvironment env) {
        CountingPaginatedData<SearchKindObject> data;

        try {
            var searchControl = Session.getModelController(SearchControl.class);
            var totalCount = searchControl.countSearchKinds();

            try(var objectLimiter = new ObjectLimiter(env, SearchKindConstants.COMPONENT_VENDOR_NAME, SearchKindConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = SearchUtil.getHome().getGetSearchKindsForm();
                var entities = new GetSearchKindsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var searchKinds = entities.stream().map(SearchKindObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchKinds);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("searchType")
    static SearchTypeObject searchType(final DataFetchingEnvironment env,
            @GraphQLName("searchKindName") final String searchKindName,
            @GraphQLName("searchTypeName") final String searchTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchType searchType;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchTypeForm();

            commandForm.setSearchKindName(searchKindName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setUuid(id);

            searchType = new GetSearchTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchType == null ? null : new SearchTypeObject(searchType);
    }

    @GraphQLField
    @GraphQLName("searchTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchTypeObject> searchTypes(final DataFetchingEnvironment env,
            @GraphQLName("searchKindName") @GraphQLNonNull final String searchKindName) {
        CountingPaginatedData<SearchTypeObject> data;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchTypesForm();
            var command = new GetSearchTypesCommand();

            commandForm.setSearchKindName(searchKindName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SearchTypeConstants.COMPONENT_VENDOR_NAME, SearchTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var searchTypes = entities.stream()
                            .map(SearchTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("searchSortOrder")
    static SearchSortOrderObject searchSortOrder(final DataFetchingEnvironment env,
            @GraphQLName("searchKindName") final String searchKindName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchSortOrder searchSortOrder;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchSortOrderForm();

            commandForm.setSearchKindName(searchKindName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setUuid(id);

            searchSortOrder = new GetSearchSortOrderCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchSortOrder == null ? null : new SearchSortOrderObject(searchSortOrder);
    }

    @GraphQLField
    @GraphQLName("searchSortOrders")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchSortOrderObject> searchSortOrders(final DataFetchingEnvironment env,
            @GraphQLName("searchKindName") @GraphQLNonNull final String searchKindName) {
        CountingPaginatedData<SearchSortOrderObject> data;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchSortOrdersForm();
            var command = new GetSearchSortOrdersCommand();

            commandForm.setSearchKindName(searchKindName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SearchSortOrderConstants.COMPONENT_VENDOR_NAME, SearchSortOrderConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var searchSortOrders = entities.stream()
                            .map(SearchSortOrderObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchSortOrders);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("searchUseType")
    static SearchUseTypeObject searchUseType(final DataFetchingEnvironment env,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchUseType searchUseType;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchUseTypeForm();

            commandForm.setSearchUseTypeName(searchUseTypeName);
            commandForm.setUuid(id);

            searchUseType = new GetSearchUseTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchUseType == null ? null : new SearchUseTypeObject(searchUseType);
    }

    @GraphQLField
    @GraphQLName("searchUseTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchUseTypeObject> searchUseTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<SearchUseTypeObject> data;

        try {
            var searchControl = Session.getModelController(SearchControl.class);
            var totalCount = searchControl.countSearchUseTypes();

            try(var objectLimiter = new ObjectLimiter(env, SearchUseTypeConstants.COMPONENT_VENDOR_NAME, SearchUseTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = SearchUtil.getHome().getGetSearchUseTypesForm();
                var entities = new GetSearchUseTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var searchUseTypes = entities.stream().map(SearchUseTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchUseTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("searchDefaultOperator")
    static SearchDefaultOperatorObject searchDefaultOperator(final DataFetchingEnvironment env,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchDefaultOperator searchDefaultOperator;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchDefaultOperatorForm();

            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setUuid(id);

            searchDefaultOperator = new GetSearchDefaultOperatorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchDefaultOperator == null ? null : new SearchDefaultOperatorObject(searchDefaultOperator);
    }

    @GraphQLField
    @GraphQLName("searchDefaultOperators")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchDefaultOperatorObject> searchDefaultOperators(final DataFetchingEnvironment env) {
        CountingPaginatedData<SearchDefaultOperatorObject> data;

        try {
            var searchControl = Session.getModelController(SearchControl.class);
            var totalCount = searchControl.countSearchDefaultOperators();

            try(var objectLimiter = new ObjectLimiter(env, SearchDefaultOperatorConstants.COMPONENT_VENDOR_NAME, SearchDefaultOperatorConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = SearchUtil.getHome().getGetSearchDefaultOperatorsForm();
                var entities = new GetSearchDefaultOperatorsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var searchDefaultOperators = entities.stream().map(SearchDefaultOperatorObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchDefaultOperators);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("searchSortDirection")
    static SearchSortDirectionObject searchSortDirection(final DataFetchingEnvironment env,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchSortDirection searchSortDirection;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchSortDirectionForm();

            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setUuid(id);

            searchSortDirection = new GetSearchSortDirectionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchSortDirection == null ? null : new SearchSortDirectionObject(searchSortDirection);
    }

    @GraphQLField
    @GraphQLName("searchSortDirections")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchSortDirectionObject> searchSortDirections(final DataFetchingEnvironment env) {
        CountingPaginatedData<SearchSortDirectionObject> data;

        try {
            var searchControl = Session.getModelController(SearchControl.class);
            var totalCount = searchControl.countSearchSortDirections();

            try(var objectLimiter = new ObjectLimiter(env, SearchSortDirectionConstants.COMPONENT_VENDOR_NAME, SearchSortDirectionConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = SearchUtil.getHome().getGetSearchSortDirectionsForm();
                var entities = new GetSearchSortDirectionsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var searchSortDirections = entities.stream().map(SearchSortDirectionObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchSortDirections);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("searchResultActionType")
    static SearchResultActionTypeObject searchResultActionType(final DataFetchingEnvironment env,
            @GraphQLName("searchResultActionTypeName") final String searchResultActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchResultActionType searchResultActionType;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchResultActionTypeForm();

            commandForm.setSearchResultActionTypeName(searchResultActionTypeName);
            commandForm.setUuid(id);

            searchResultActionType = new GetSearchResultActionTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchResultActionType == null ? null : new SearchResultActionTypeObject(searchResultActionType);
    }

    @GraphQLField
    @GraphQLName("searchResultActionTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchResultActionTypeObject> searchResultActionTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<SearchResultActionTypeObject> data;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchResultActionTypesForm();
            var command = new GetSearchResultActionTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SearchResultActionTypeConstants.COMPONENT_VENDOR_NAME, SearchResultActionTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var searchResultActionTypes = entities.stream()
                            .map(SearchResultActionTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchResultActionTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("searchCheckSpellingActionType")
    static SearchCheckSpellingActionTypeObject searchCheckSpellingActionType(final DataFetchingEnvironment env,
            @GraphQLName("searchCheckSpellingActionTypeName") final String searchCheckSpellingActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchCheckSpellingActionType searchCheckSpellingActionType;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchCheckSpellingActionTypeForm();

            commandForm.setSearchCheckSpellingActionTypeName(searchCheckSpellingActionTypeName);
            commandForm.setUuid(id);

            searchCheckSpellingActionType = new GetSearchCheckSpellingActionTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchCheckSpellingActionType == null ? null : new SearchCheckSpellingActionTypeObject(searchCheckSpellingActionType);
    }

    @GraphQLField
    @GraphQLName("searchCheckSpellingActionTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SearchCheckSpellingActionTypeObject> searchCheckSpellingActionTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<SearchCheckSpellingActionTypeObject> data;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchCheckSpellingActionTypesForm();
            var command = new GetSearchCheckSpellingActionTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SearchCheckSpellingActionTypeConstants.COMPONENT_VENDOR_NAME, SearchCheckSpellingActionTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var searchCheckSpellingActionTypes = entities.stream()
                            .map(SearchCheckSpellingActionTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, searchCheckSpellingActionTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflow")
    static WorkflowObject workflow(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("id") @GraphQLID final String id) {
        Workflow workflow;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setUuid(id);

            workflow = new GetWorkflowCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflow == null ? null : new WorkflowObject(workflow);
    }

    @GraphQLField
    @GraphQLName("workflows")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowObject> workflows(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName) {
        CountingPaginatedData<WorkflowObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowsForm();
            var command = new GetWorkflowsCommand();

            commandForm.setSelectorKindName(selectorKindName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowConstants.COMPONENT_VENDOR_NAME, WorkflowConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflows = entities.stream()
                            .map(WorkflowObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflows);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }
    
    @GraphQLField
    @GraphQLName("workflowEntityType")
    static WorkflowEntityTypeObject workflowEntityType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        WorkflowEntityType workflowEntityType;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntityTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            workflowEntityType = new GetWorkflowEntityTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowEntityType == null ? null : new WorkflowEntityTypeObject(workflowEntityType);
    }

    @GraphQLField
    @GraphQLName("workflowEntityTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowEntityTypeObject> workflowEntityTypes(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName) {
        CountingPaginatedData<WorkflowEntityTypeObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntityTypesForm();
            var command = new GetWorkflowEntityTypesCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowEntityTypeConstants.COMPONENT_VENDOR_NAME, WorkflowEntityTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowEntityTypes = entities.stream()
                            .map(WorkflowEntityTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowEntityTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowSelectorKind")
    static WorkflowSelectorKindObject workflowSelectorKind(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName) {
        WorkflowSelectorKind workflowSelectorKind;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowSelectorKindForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setSelectorKindName(selectorKindName);

            workflowSelectorKind = new GetWorkflowSelectorKindCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowSelectorKind == null ? null : new WorkflowSelectorKindObject(workflowSelectorKind);
    }

    @GraphQLField
    @GraphQLName("workflowSelectorKinds")
    static Collection<WorkflowSelectorKindObject> workflowSelectorKinds(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("selectorKindName") final String selectorKindName) {
        Collection<WorkflowSelectorKind> workflowSelectorKinds;
        Collection<WorkflowSelectorKindObject> workflowSelectorKindObjects;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowSelectorKindsForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setSelectorKindName(selectorKindName);

            workflowSelectorKinds = new GetWorkflowSelectorKindsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(workflowSelectorKinds == null) {
            workflowSelectorKindObjects = emptyList();
        } else {
            workflowSelectorKindObjects = new ArrayList<>(workflowSelectorKinds.size());

            workflowSelectorKinds.stream()
                    .map(WorkflowSelectorKindObject::new)
                    .forEachOrdered(workflowSelectorKindObjects::add);
        }

        return workflowSelectorKindObjects;
    }

    @GraphQLField
    @GraphQLName("workflowEntityStatuses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowEntityStatusObject> workflowEntityStatuses(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName) {
        CountingPaginatedData<WorkflowEntityStatusObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntityStatusesForm();
            var command = new GetWorkflowEntityStatusesCommand();

            commandForm.setWorkflowName(workflowName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowEntityStatusConstants.COMPONENT_VENDOR_NAME, WorkflowEntityStatusConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowEntityStatuses = entities.stream()
                            .map(WorkflowEntityStatusObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowEntityStatuses);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowStep")
    static WorkflowStepObject workflowStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowStepName") final String workflowStepName,
            @GraphQLName("id") @GraphQLID final String id) {
        WorkflowStep workflowStep;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setUuid(id);

            workflowStep = new GetWorkflowStepCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowStep == null ? null : new WorkflowStepObject(workflowStep);
    }

    @GraphQLField
    @GraphQLName("workflowSteps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowStepObject> workflowSteps(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName) {
        CountingPaginatedData<WorkflowStepObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepsForm();
            var command = new GetWorkflowStepsCommand();

            commandForm.setWorkflowName(workflowName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowStepConstants.COMPONENT_VENDOR_NAME, WorkflowStepConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowSteps = entities.stream()
                            .map(WorkflowStepObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowSteps);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }
    
    @GraphQLField
    @GraphQLName("workflowDestination")
    static WorkflowDestinationObject workflowDestination(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowStepName") final String workflowStepName,
            @GraphQLName("workflowDestinationName") final String workflowDestinationName,
            @GraphQLName("id") @GraphQLID final String id) {
        WorkflowDestination workflowDestination;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setUuid(id);

            workflowDestination = new GetWorkflowDestinationCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowDestination == null ? null : new WorkflowDestinationObject(workflowDestination);
    }

    @GraphQLField
    @GraphQLName("workflowDestinations")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowDestinationObject> workflowDestinations(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName) {
        CountingPaginatedData<WorkflowDestinationObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationsForm();
            var command = new GetWorkflowDestinationsCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowDestinations = entities.stream()
                            .map(WorkflowDestinationObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowDestinations);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowDestinationStep")
    static WorkflowDestinationStepObject workflowDestinationStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("destinationWorkflowName") @GraphQLNonNull final String destinationWorkflowName,
            @GraphQLName("destinationWorkflowStepName") @GraphQLNonNull final String destinationWorkflowStepName) {
        WorkflowDestinationStep workflowDestinationStep;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setDestinationWorkflowName(destinationWorkflowName);
            commandForm.setDestinationWorkflowStepName(destinationWorkflowStepName);

            workflowDestinationStep = new GetWorkflowDestinationStepCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowDestinationStep == null ? null : new WorkflowDestinationStepObject(workflowDestinationStep);
    }

    @GraphQLField
    @GraphQLName("workflowDestinationSteps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowDestinationStepObject> workflowDestinationSteps(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName) {
        CountingPaginatedData<WorkflowDestinationStepObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationStepsForm();
            var command = new GetWorkflowDestinationStepsCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationStepConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationStepConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowDestinationSteps = entities.stream()
                            .map(WorkflowDestinationStepObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowDestinationSteps);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowDestinationPartyType")
    static WorkflowDestinationPartyTypeObject workflowDestinationPartyType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        WorkflowDestinationPartyType workflowDestinationPartyType;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationPartyTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setPartyTypeName(partyTypeName);

            workflowDestinationPartyType = new GetWorkflowDestinationPartyTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowDestinationPartyType == null ? null : new WorkflowDestinationPartyTypeObject(workflowDestinationPartyType);
    }

    @GraphQLField
    @GraphQLName("workflowDestinationPartyTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowDestinationPartyTypeObject> workflowDestinationPartyTypes(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName) {
        CountingPaginatedData<WorkflowDestinationPartyTypeObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationPartyTypesForm();
            var command = new GetWorkflowDestinationPartyTypesCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationPartyTypeConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationPartyTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowDestinationPartyTypes = entities.stream()
                            .map(WorkflowDestinationPartyTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowDestinationPartyTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowDestinationSecurityRole")
    static WorkflowDestinationSecurityRoleObject workflowDestinationSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName,
            @GraphQLName("securityRoleName") @GraphQLNonNull final String securityRoleName) {
        WorkflowDestinationSecurityRole workflowDestinationSecurityRole;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationSecurityRoleForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setSecurityRoleName(securityRoleName);

            workflowDestinationSecurityRole = new GetWorkflowDestinationSecurityRoleCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowDestinationSecurityRole == null ? null : new WorkflowDestinationSecurityRoleObject(workflowDestinationSecurityRole);
    }

    @GraphQLField
    @GraphQLName("workflowDestinationSecurityRoles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowDestinationSecurityRoleObject> workflowDestinationSecurityRoles(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        CountingPaginatedData<WorkflowDestinationSecurityRoleObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationSecurityRolesForm();
            var command = new GetWorkflowDestinationSecurityRolesCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setPartyTypeName(partyTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowDestinationSecurityRoleConstants.COMPONENT_VENDOR_NAME, WorkflowDestinationSecurityRoleConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowDestinationSecurityRoles = entities.stream()
                            .map(WorkflowDestinationSecurityRoleObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowDestinationSecurityRoles);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowDestinationSelector")
    static WorkflowDestinationSelectorObject workflowDestinationSelector(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName) {
        WorkflowDestinationSelector workflowDestinationSelector;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationSelectorForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setSelectorName(selectorName);

            workflowDestinationSelector = new GetWorkflowDestinationSelectorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowDestinationSelector == null ? null : new WorkflowDestinationSelectorObject(workflowDestinationSelector);
    }

    @GraphQLField
    @GraphQLName("workflowDestinationSelectors")
    static Collection<WorkflowDestinationSelectorObject> workflowDestinationSelectors(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowStepName") @GraphQLNonNull final String workflowStepName,
            @GraphQLName("workflowDestinationName") @GraphQLNonNull final String workflowDestinationName) {
        Collection<WorkflowDestinationSelector> workflowDestinationSelectors;
        Collection<WorkflowDestinationSelectorObject> workflowDestinationSelectorObjects;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationSelectorsForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);

            workflowDestinationSelectors = new GetWorkflowDestinationSelectorsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(workflowDestinationSelectors == null) {
            workflowDestinationSelectorObjects = emptyList();
        } else {
            workflowDestinationSelectorObjects = new ArrayList<>(workflowDestinationSelectors.size());

            workflowDestinationSelectors.stream()
                    .map(WorkflowDestinationSelectorObject::new)
                    .forEachOrdered(workflowDestinationSelectorObjects::add);
        }

        return workflowDestinationSelectorObjects;
    }

    @GraphQLField
    @GraphQLName("workflowEntrance")
    static WorkflowEntranceObject workflowEntrance(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowEntranceName") final String workflowEntranceName,
            @GraphQLName("id") @GraphQLID final String id) {
        WorkflowEntrance workflowEntrance;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setUuid(id);

            workflowEntrance = new GetWorkflowEntranceCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowEntrance == null ? null : new WorkflowEntranceObject(workflowEntrance);
    }

    @GraphQLField
    @GraphQLName("workflowEntrances")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowEntranceObject> workflowEntrances(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName) {
        CountingPaginatedData<WorkflowEntranceObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntrancesForm();
            var command = new GetWorkflowEntrancesCommand();

            commandForm.setWorkflowName(workflowName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowEntranceConstants.COMPONENT_VENDOR_NAME, WorkflowEntranceConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowEntrances = entities.stream()
                            .map(WorkflowEntranceObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowEntrances);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowEntranceStep")
    static WorkflowEntranceStepObject workflowEntranceStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("entranceWorkflowName") @GraphQLNonNull final String entranceWorkflowName,
            @GraphQLName("entranceWorkflowStepName") @GraphQLNonNull final String entranceWorkflowStepName) {
        WorkflowEntranceStep workflowEntranceStep;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setEntranceWorkflowName(entranceWorkflowName);
            commandForm.setEntranceWorkflowStepName(entranceWorkflowStepName);

            workflowEntranceStep = new GetWorkflowEntranceStepCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowEntranceStep == null ? null : new WorkflowEntranceStepObject(workflowEntranceStep);
    }

    @GraphQLField
    @GraphQLName("workflowEntranceSteps")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowEntranceStepObject> workflowEntranceSteps(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName) {
        CountingPaginatedData<WorkflowEntranceStepObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceStepsForm();
            var command = new GetWorkflowEntranceStepsCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowEntranceStepConstants.COMPONENT_VENDOR_NAME, WorkflowEntranceStepConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowEntranceSteps = entities.stream()
                            .map(WorkflowEntranceStepObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowEntranceSteps);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowEntrancePartyType")
    static WorkflowEntrancePartyTypeObject workflowEntrancePartyType(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        WorkflowEntrancePartyType workflowEntrancePartyType;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntrancePartyTypeForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setPartyTypeName(partyTypeName);

            workflowEntrancePartyType = new GetWorkflowEntrancePartyTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowEntrancePartyType == null ? null : new WorkflowEntrancePartyTypeObject(workflowEntrancePartyType);
    }

    @GraphQLField
    @GraphQLName("workflowEntrancePartyTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowEntrancePartyTypeObject> workflowEntrancePartyTypes(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName) {
        CountingPaginatedData<WorkflowEntrancePartyTypeObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntrancePartyTypesForm();
            var command = new GetWorkflowEntrancePartyTypesCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowEntrancePartyTypeConstants.COMPONENT_VENDOR_NAME, WorkflowEntrancePartyTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowEntrancePartyTypes = entities.stream()
                            .map(WorkflowEntrancePartyTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowEntrancePartyTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowEntranceSecurityRole")
    static WorkflowEntranceSecurityRoleObject workflowEntranceSecurityRole(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName,
            @GraphQLName("securityRoleName") @GraphQLNonNull final String securityRoleName) {
        WorkflowEntranceSecurityRole workflowEntranceSecurityRole;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceSecurityRoleForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setSecurityRoleName(securityRoleName);

            workflowEntranceSecurityRole = new GetWorkflowEntranceSecurityRoleCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowEntranceSecurityRole == null ? null : new WorkflowEntranceSecurityRoleObject(workflowEntranceSecurityRole);
    }

    @GraphQLField
    @GraphQLName("workflowEntranceSecurityRoles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowEntranceSecurityRoleObject> workflowEntranceSecurityRoles(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        CountingPaginatedData<WorkflowEntranceSecurityRoleObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceSecurityRolesForm();
            var command = new GetWorkflowEntranceSecurityRolesCommand();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setPartyTypeName(partyTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowEntranceSecurityRoleConstants.COMPONENT_VENDOR_NAME, WorkflowEntranceSecurityRoleConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowEntranceSecurityRoles = entities.stream()
                            .map(WorkflowEntranceSecurityRoleObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowEntranceSecurityRoles);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("workflowEntranceSelector")
    static WorkflowEntranceSelectorObject workflowEntranceSelector(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName) {
        WorkflowEntranceSelector workflowEntranceSelector;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceSelectorForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);
            commandForm.setSelectorName(selectorName);

            workflowEntranceSelector = new GetWorkflowEntranceSelectorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowEntranceSelector == null ? null : new WorkflowEntranceSelectorObject(workflowEntranceSelector);
    }

    @GraphQLField
    @GraphQLName("workflowEntranceSelectors")
    static Collection<WorkflowEntranceSelectorObject> workflowEntranceSelectors(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName,
            @GraphQLName("workflowEntranceName") @GraphQLNonNull final String workflowEntranceName) {
        Collection<WorkflowEntranceSelector> workflowEntranceSelectors;
        Collection<WorkflowEntranceSelectorObject> workflowEntranceSelectorObjects;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceSelectorsForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowEntranceName(workflowEntranceName);

            workflowEntranceSelectors = new GetWorkflowEntranceSelectorsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(workflowEntranceSelectors == null) {
            workflowEntranceSelectorObjects = emptyList();
        } else {
            workflowEntranceSelectorObjects = new ArrayList<>(workflowEntranceSelectors.size());

            workflowEntranceSelectors.stream()
                    .map(WorkflowEntranceSelectorObject::new)
                    .forEachOrdered(workflowEntranceSelectorObjects::add);
        }

        return workflowEntranceSelectorObjects;
    }

    @GraphQLField
    @GraphQLName("workflowStepType")
    static WorkflowStepTypeObject workflowStepType(final DataFetchingEnvironment env,
            @GraphQLName("workflowStepTypeName") final String workflowStepTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        WorkflowStepType workflowStepType;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepTypeForm();

            commandForm.setWorkflowStepTypeName(workflowStepTypeName);
            commandForm.setUuid(id);

            workflowStepType = new GetWorkflowStepTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowStepType == null ? null : new WorkflowStepTypeObject(workflowStepType);
    }

    @GraphQLField
    @GraphQLName("workflowStepTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WorkflowStepTypeObject> workflowStepTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<WorkflowStepTypeObject> data;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepTypesForm();
            var command = new GetWorkflowStepTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WorkflowStepTypeConstants.COMPONENT_VENDOR_NAME, WorkflowStepTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var workflowStepTypes = entities.stream()
                            .map(WorkflowStepTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, workflowStepTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }
    
    @GraphQLField
    @GraphQLName("sequence")
    static SequenceObject sequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("sequenceName") final String sequenceName,
            @GraphQLName("id") @GraphQLID final String id) {
        Sequence sequence;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);
            commandForm.setUuid(id);

            sequence = new GetSequenceCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequence == null ? null : new SequenceObject(sequence);
    }

    @GraphQLField
    @GraphQLName("sequences")
    static Collection<SequenceObject> sequences(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName) {
        Collection<Sequence> sequences;
        Collection<SequenceObject> sequenceObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequencesForm();

            commandForm.setSequenceTypeName(sequenceTypeName);

            sequences = new GetSequencesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequences == null) {
            sequenceObjects = emptyList();
        } else {
            sequenceObjects = new ArrayList<>(sequences.size());

            sequences.stream()
                    .map(SequenceObject::new)
                    .forEachOrdered(sequenceObjects::add);
        }

        return sequenceObjects;
    }

    @GraphQLField
    @GraphQLName("sequenceType")
    static SequenceTypeObject sequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SequenceType sequenceType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setUuid(id);

            sequenceType = new GetSequenceTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceType == null ? null : new SequenceTypeObject(sequenceType);
    }

    @GraphQLField
    @GraphQLName("sequenceTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SequenceTypeObject> sequenceTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<SequenceTypeObject> data;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceTypesForm();
            var command = new GetSequenceTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SequenceTypeConstants.COMPONENT_VENDOR_NAME, SequenceTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var sequenceTypes = entities.stream()
                            .map(SequenceTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, sequenceTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("sequenceChecksumType")
    static SequenceChecksumTypeObject sequenceChecksumType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceChecksumTypeName") @GraphQLNonNull final String sequenceChecksumTypeName) {
        SequenceChecksumType sequenceChecksumType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceChecksumTypeForm();

            commandForm.setSequenceChecksumTypeName(sequenceChecksumTypeName);

            sequenceChecksumType = new GetSequenceChecksumTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceChecksumType == null ? null : new SequenceChecksumTypeObject(sequenceChecksumType);
    }

    @GraphQLField
    @GraphQLName("sequenceChecksumTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SequenceChecksumTypeObject> sequenceChecksumTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<SequenceChecksumTypeObject> data;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceChecksumTypesForm();
            var command = new GetSequenceChecksumTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SequenceChecksumTypeConstants.COMPONENT_VENDOR_NAME, SequenceChecksumTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var sequenceChecksumTypes = entities.stream()
                            .map(SequenceChecksumTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, sequenceChecksumTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("sequenceEncoderType")
    static SequenceEncoderTypeObject sequenceEncoderType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceEncoderTypeName") @GraphQLNonNull final String sequenceEncoderTypeName) {
        SequenceEncoderType sequenceEncoderType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceEncoderTypeForm();

            commandForm.setSequenceEncoderTypeName(sequenceEncoderTypeName);

            sequenceEncoderType = new GetSequenceEncoderTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceEncoderType == null ? null : new SequenceEncoderTypeObject(sequenceEncoderType);
    }

    @GraphQLField
    @GraphQLName("sequenceEncoderTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SequenceEncoderTypeObject> sequenceEncoderTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<SequenceEncoderTypeObject> data;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceEncoderTypesForm();
            var command = new GetSequenceEncoderTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SequenceEncoderTypeConstants.COMPONENT_VENDOR_NAME, SequenceEncoderTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var sequenceEncoderTypes = entities.stream()
                            .map(SequenceEncoderTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, sequenceEncoderTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("selectorKind")
    static SelectorKindObject selectorKind(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        SelectorKind selectorKind;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorKindForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setUuid(id);

            selectorKind = new GetSelectorKindCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selectorKind == null ? null : new SelectorKindObject(selectorKind);
    }

    @GraphQLField
    @GraphQLName("selectorKinds")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SelectorKindObject> selectorKinds(final DataFetchingEnvironment env) {
        CountingPaginatedData<SelectorKindObject> data;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorKindsForm();
            var command = new GetSelectorKindsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SelectorKindConstants.COMPONENT_VENDOR_NAME, SelectorKindConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var selectorKinds = entities.stream()
                            .map(SelectorKindObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, selectorKinds);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }
    
    @GraphQLField
    @GraphQLName("selectorType")
    static SelectorTypeObject selectorType(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("selectorTypeName") final String selectorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SelectorType selectorType;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorTypeForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setUuid(id);

            selectorType = new GetSelectorTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selectorType == null ? null : new SelectorTypeObject(selectorType);
    }

    @GraphQLField
    @GraphQLName("selectorTypes")
    static Collection<SelectorTypeObject> selectorTypes(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName) {
        Collection<SelectorType> selectorTypes;
        Collection<SelectorTypeObject> selectorTypeObjects;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorTypesForm();

            commandForm.setSelectorKindName(selectorKindName);

            selectorTypes = new GetSelectorTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(selectorTypes == null) {
            selectorTypeObjects = emptyList();
        } else {
            selectorTypeObjects = new ArrayList<>(selectorTypes.size());

            selectorTypes.stream()
                    .map(SelectorTypeObject::new)
                    .forEachOrdered(selectorTypeObjects::add);
        }

        return selectorTypeObjects;
    }

    @GraphQLField
    @GraphQLName("selector")
    static SelectorObject selector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("selectorTypeName") final String selectorTypeName,
            @GraphQLName("selectorName") final String selectorName,
            @GraphQLName("id") @GraphQLID final String id) {
        Selector selector;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.setUuid(id);

            selector = new GetSelectorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selector == null ? null : new SelectorObject(selector);
    }

    @GraphQLField
    @GraphQLName("selectors")
    static Collection<SelectorObject> selectors(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName) {
        Collection<Selector> selectors;
        Collection<SelectorObject> selectorObjects;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorsForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);

            selectors = new GetSelectorsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(selectors == null) {
            selectorObjects = emptyList();
        } else {
            selectorObjects = new ArrayList<>(selectors.size());

            selectors.stream()
                    .map(SelectorObject::new)
                    .forEachOrdered(selectorObjects::add);
        }

        return selectorObjects;
    }

    @GraphQLField
    @GraphQLName("filterKind")
    static FilterKindObject filterKind(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterKind filterKind;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterKindForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setUuid(id);

            filterKind = new GetFilterKindCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterKind == null ? null : new FilterKindObject(filterKind);
    }

    @GraphQLField
    @GraphQLName("filterKinds")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<FilterKindObject> filterKinds(final DataFetchingEnvironment env) {
        CountingPaginatedData<FilterKindObject> data;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterKindsForm();
            var command = new GetFilterKindsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, FilterKindConstants.COMPONENT_VENDOR_NAME, FilterKindConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var filterKinds = entities.stream()
                            .map(FilterKindObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, filterKinds);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("filterType")
    static FilterTypeObject filterType(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("filterTypeName") final String filterTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterType filterType;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterTypeForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setUuid(id);

            filterType = new GetFilterTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterType == null ? null : new FilterTypeObject(filterType);
    }

    @GraphQLField
    @GraphQLName("filterTypes")
    static Collection<FilterTypeObject> filterTypes(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName) {
        Collection<FilterType> filterTypes;
        Collection<FilterTypeObject> filterTypeObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterTypesForm();

            commandForm.setFilterKindName(filterKindName);

            filterTypes = new GetFilterTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterTypes == null) {
            filterTypeObjects = emptyList();
        } else {
            filterTypeObjects = new ArrayList<>(filterTypes.size());

            filterTypes.stream()
                    .map(FilterTypeObject::new)
                    .forEachOrdered(filterTypeObjects::add);
        }

        return filterTypeObjects;
    }

    @GraphQLField
    @GraphQLName("filter")
    static FilterObject filter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("filterTypeName") final String filterTypeName,
            @GraphQLName("filterName") final String filterName,
            @GraphQLName("id") @GraphQLID final String id) {
        Filter filter;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);
            commandForm.setUuid(id);

            filter = new GetFilterCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filter == null ? null : new FilterObject(filter);
    }

    @GraphQLField
    @GraphQLName("filters")
    static Collection<FilterObject> filters(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName) {
        Collection<Filter> filters;
        Collection<FilterObject> filterObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFiltersForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);

            filters = new GetFiltersCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filters == null) {
            filterObjects = emptyList();
        } else {
            filterObjects = new ArrayList<>(filters.size());

            filters.stream()
                    .map(FilterObject::new)
                    .forEachOrdered(filterObjects::add);
        }

        return filterObjects;
    }

    @GraphQLField
    @GraphQLName("filterStep")
    static FilterStepObject filterStep(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("filterTypeName") final String filterTypeName,
            @GraphQLName("filterName") final String filterName,
            @GraphQLName("filterStepName") final String filterStepName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterStep filterStep;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterStepForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);
            commandForm.setFilterStepName(filterStepName);
            commandForm.setUuid(id);

            filterStep = new GetFilterStepCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterStep == null ? null : new FilterStepObject(filterStep);
    }

    @GraphQLField
    @GraphQLName("filterSteps")
    static Collection<FilterStepObject> filterSteps(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName) {
        Collection<FilterStep> filterSteps;
        Collection<FilterStepObject> filterStepObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterStepsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);

            filterSteps = new GetFilterStepsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterSteps == null) {
            filterStepObjects = emptyList();
        } else {
            filterStepObjects = new ArrayList<>(filterSteps.size());

            filterSteps.stream()
                    .map(FilterStepObject::new)
                    .forEachOrdered(filterStepObjects::add);
        }

        return filterStepObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentSource")
    static FilterAdjustmentSourceObject filterAdjustmentSource(final DataFetchingEnvironment env,
            @GraphQLName("filterAdjustmentSourceName") @GraphQLNonNull final String filterAdjustmentSourceName) {
        FilterAdjustmentSource filterAdjustmentSource;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentSourceForm();

            commandForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);

            filterAdjustmentSource = new GetFilterAdjustmentSourceCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentSource == null ? null : new FilterAdjustmentSourceObject(filterAdjustmentSource);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentSources")
    static Collection<FilterAdjustmentSourceObject> filterAdjustmentSources(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentSource> filterAdjustmentSources;
        Collection<FilterAdjustmentSourceObject> filterAdjustmentSourceObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentSourcesForm();

            filterAdjustmentSources = new GetFilterAdjustmentSourcesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustmentSources == null) {
            filterAdjustmentSourceObjects = emptyList();
        } else {
            filterAdjustmentSourceObjects = new ArrayList<>(filterAdjustmentSources.size());

            filterAdjustmentSources.stream()
                    .map(FilterAdjustmentSourceObject::new)
                    .forEachOrdered(filterAdjustmentSourceObjects::add);
        }

        return filterAdjustmentSourceObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentType")
    static FilterAdjustmentTypeObject filterAdjustmentType(final DataFetchingEnvironment env,
            @GraphQLName("filterAdjustmentTypeName") @GraphQLNonNull final String filterAdjustmentTypeName) {
        FilterAdjustmentType filterAdjustmentType;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentTypeForm();

            commandForm.setFilterAdjustmentTypeName(filterAdjustmentTypeName);

            filterAdjustmentType = new GetFilterAdjustmentTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentType == null ? null : new FilterAdjustmentTypeObject(filterAdjustmentType);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentTypes")
    static Collection<FilterAdjustmentTypeObject> filterAdjustmentTypes(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentType> filterAdjustmentTypes;
        Collection<FilterAdjustmentTypeObject> filterAdjustmentTypeObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentTypesForm();

            filterAdjustmentTypes = new GetFilterAdjustmentTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustmentTypes == null) {
            filterAdjustmentTypeObjects = emptyList();
        } else {
            filterAdjustmentTypeObjects = new ArrayList<>(filterAdjustmentTypes.size());

            filterAdjustmentTypes.stream()
                    .map(FilterAdjustmentTypeObject::new)
                    .forEachOrdered(filterAdjustmentTypeObjects::add);
        }

        return filterAdjustmentTypeObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustment")
    static FilterAdjustmentObject filterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("filterAdjustmentName") final String filterAdjustmentName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterAdjustment filterAdjustment;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUuid(id);

            filterAdjustment = new GetFilterAdjustmentCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustment == null ? null : new FilterAdjustmentObject(filterAdjustment);
    }

    @GraphQLField
    @GraphQLName("filterAdjustments")
    static Collection<FilterAdjustmentObject> filterAdjustments(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName) {
        Collection<FilterAdjustment> filterAdjustments;
        Collection<FilterAdjustmentObject> filterAdjustmentObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentsForm();

            commandForm.setFilterKindName(filterKindName);

            filterAdjustments = new GetFilterAdjustmentsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustments == null) {
            filterAdjustmentObjects = emptyList();
        } else {
            filterAdjustmentObjects = new ArrayList<>(filterAdjustments.size());

            filterAdjustments.stream()
                    .map(FilterAdjustmentObject::new)
                    .forEachOrdered(filterAdjustmentObjects::add);
        }

        return filterAdjustmentObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentAmount")
    static FilterAdjustmentAmountObject filterAdjustmentAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        FilterAdjustmentAmount filterAdjustmentAmount;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            filterAdjustmentAmount = new GetFilterAdjustmentAmountCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentAmount == null ? null : new FilterAdjustmentAmountObject(filterAdjustmentAmount);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentAmounts")
    static Collection<FilterAdjustmentAmountObject> filterAdjustmentAmounts(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        Collection<FilterAdjustmentAmount> filterAdjustmentAmounts;
        Collection<FilterAdjustmentAmountObject> filterAdjustmentAmountObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentAmountsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            filterAdjustmentAmounts = new GetFilterAdjustmentAmountsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustmentAmounts == null) {
            filterAdjustmentAmountObjects = emptyList();
        } else {
            filterAdjustmentAmountObjects = new ArrayList<>(filterAdjustmentAmounts.size());

            filterAdjustmentAmounts.stream()
                    .map(FilterAdjustmentAmountObject::new)
                    .forEachOrdered(filterAdjustmentAmountObjects::add);
        }

        return filterAdjustmentAmountObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentFixedAmount")
    static FilterAdjustmentFixedAmountObject filterAdjustmentFixedAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        FilterAdjustmentFixedAmount filterAdjustmentFixedAmount;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentFixedAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            filterAdjustmentFixedAmount = new GetFilterAdjustmentFixedAmountCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentFixedAmount == null ? null : new FilterAdjustmentFixedAmountObject(filterAdjustmentFixedAmount);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentFixedAmounts")
    static Collection<FilterAdjustmentFixedAmountObject> filterAdjustmentFixedAmounts(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        Collection<FilterAdjustmentFixedAmount> filterAdjustmentFixedAmounts;
        Collection<FilterAdjustmentFixedAmountObject> filterAdjustmentFixedAmountObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentFixedAmountsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            filterAdjustmentFixedAmounts = new GetFilterAdjustmentFixedAmountsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustmentFixedAmounts == null) {
            filterAdjustmentFixedAmountObjects = emptyList();
        } else {
            filterAdjustmentFixedAmountObjects = new ArrayList<>(filterAdjustmentFixedAmounts.size());

            filterAdjustmentFixedAmounts.stream()
                    .map(FilterAdjustmentFixedAmountObject::new)
                    .forEachOrdered(filterAdjustmentFixedAmountObjects::add);
        }

        return filterAdjustmentFixedAmountObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentPercent")
    static FilterAdjustmentPercentObject filterAdjustmentPercent(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        FilterAdjustmentPercent filterAdjustmentPercent;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentPercentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            filterAdjustmentPercent = new GetFilterAdjustmentPercentCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentPercent == null ? null : new FilterAdjustmentPercentObject(filterAdjustmentPercent);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentPercents")
    static Collection<FilterAdjustmentPercentObject> filterAdjustmentPercents(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        Collection<FilterAdjustmentPercent> filterAdjustmentPercents;
        Collection<FilterAdjustmentPercentObject> filterAdjustmentPercentObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentPercentsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            filterAdjustmentPercents = new GetFilterAdjustmentPercentsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustmentPercents == null) {
            filterAdjustmentPercentObjects = emptyList();
        } else {
            filterAdjustmentPercentObjects = new ArrayList<>(filterAdjustmentPercents.size());

            filterAdjustmentPercents.stream()
                    .map(FilterAdjustmentPercentObject::new)
                    .forEachOrdered(filterAdjustmentPercentObjects::add);
        }

        return filterAdjustmentPercentObjects;
    }

    @GraphQLField
    @GraphQLName("offerUse")
    static OfferUseObject offerUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        OfferUse offerUse;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            offerUse = new GetOfferUseCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerUse == null ? null : new OfferUseObject(offerUse);
    }

    @GraphQLField
    @GraphQLName("offerUses")
    static Collection<OfferUseObject> offerUses(final DataFetchingEnvironment env,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("useName") final String useName) {
        Collection<OfferUse> offerUses;
        Collection<OfferUseObject> offerUseObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferUsesForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            offerUses = new GetOfferUsesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offerUses == null) {
            offerUseObjects = emptyList();
        } else {
            offerUseObjects = new ArrayList<>(offerUses.size());

            offerUses.stream()
                    .map(OfferUseObject::new)
                    .forEachOrdered(offerUseObjects::add);
        }

        return offerUseObjects;
    }

    @GraphQLField
    @GraphQLName("offer")
    static OfferObject offer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("id") @GraphQLID final String id) {
        Offer offer;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferForm();

            commandForm.setOfferName(offerName);
            commandForm.setUuid(id);

            offer = new GetOfferCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offer == null ? null : new OfferObject(offer);
    }

    @GraphQLField
    @GraphQLName("offers")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<OfferObject> offers(final DataFetchingEnvironment env) {
        CountingPaginatedData<OfferObject> data;

        try {
            var commandForm = OfferUtil.getHome().getGetOffersForm();
            var command = new GetOffersCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, OfferConstants.COMPONENT_VENDOR_NAME, OfferConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var offers = entities.stream()
                            .map(OfferObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, offers);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }
    
    @GraphQLField
    @GraphQLName("offerItem")
    static OfferItemObject offerItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("id") @GraphQLID final String id) {
        OfferItem offerItem;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);
            commandForm.setUuid(id);

            offerItem = new GetOfferItemCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerItem == null ? null : new OfferItemObject(offerItem);
    }

    @GraphQLField
    @GraphQLName("offerItems")
    static Collection<OfferItemObject> offerItems(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName) {
        Collection<OfferItem> offerItem;
        Collection<OfferItemObject> offerItemObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferItemsForm();

            commandForm.setOfferName(offerName);

            offerItem = new GetOfferItemsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offerItem == null) {
            offerItemObjects = emptyList();
        } else {
            offerItemObjects = new ArrayList<>(offerItem.size());

            offerItem.stream()
                    .map(OfferItemObject::new)
                    .forEachOrdered(offerItemObjects::add);
        }

        return offerItemObjects;
    }

    @GraphQLField
    @GraphQLName("offerItemPrice")
    static OfferItemPriceObject offerItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        OfferItemPrice offerItemPrice;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferItemPriceForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            offerItemPrice = new GetOfferItemPriceCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerItemPrice == null ? null : new OfferItemPriceObject(offerItemPrice);
    }

    @GraphQLField
    @GraphQLName("offerItemPrices")
    static Collection<OfferItemPriceObject> offerItemPrices(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        Collection<OfferItemPrice> offerItemPrice;
        Collection<OfferItemPriceObject> offerItemPriceObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferItemPricesForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            offerItemPrice = new GetOfferItemPricesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offerItemPrice == null) {
            offerItemPriceObjects = emptyList();
        } else {
            offerItemPriceObjects = new ArrayList<>(offerItemPrice.size());

            offerItemPrice.stream()
                    .map(OfferItemPriceObject::new)
                    .forEachOrdered(offerItemPriceObjects::add);
        }

        return offerItemPriceObjects;
    }

    @GraphQLField
    @GraphQLName("use")
    static UseObject use(final DataFetchingEnvironment env,
            @GraphQLName("useName") final String useName,
            @GraphQLName("id") @GraphQLID final String id) {
        Use use;

        try {
            var commandForm = OfferUtil.getHome().getGetUseForm();

            commandForm.setUseName(useName);
            commandForm.setUuid(id);

            use = new GetUseCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return use == null ? null : new UseObject(use);
    }

    @GraphQLField
    @GraphQLName("uses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<UseObject> uses(final DataFetchingEnvironment env) {
        CountingPaginatedData<UseObject> data;

        try {
            var commandForm = OfferUtil.getHome().getGetUsesForm();
            var command = new GetUsesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, UseConstants.COMPONENT_VENDOR_NAME, UseConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var uses = entities.stream()
                            .map(UseObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, uses);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("offerNameElement")
    static OfferNameElementObject offerNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") final String offerNameElementName,
            @GraphQLName("id") @GraphQLID final String id) {
        OfferNameElement offerNameElement;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);
            commandForm.setUuid(id);

            offerNameElement = new GetOfferNameElementCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerNameElement == null ? null : new OfferNameElementObject(offerNameElement);
    }

    @GraphQLField
    @GraphQLName("offerNameElements")
    static Collection<OfferNameElementObject> offerNameElements(final DataFetchingEnvironment env) {
        Collection<OfferNameElement> offerNameElements;
        Collection<OfferNameElementObject> offerNameElementObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferNameElementsForm();

            offerNameElements = new GetOfferNameElementsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offerNameElements == null) {
            offerNameElementObjects = emptyList();
        } else {
            offerNameElementObjects = new ArrayList<>(offerNameElements.size());

            offerNameElements.stream()
                    .map(OfferNameElementObject::new)
                    .forEachOrdered(offerNameElementObjects::add);
        }

        return offerNameElementObjects;
    }

    @GraphQLField
    @GraphQLName("useNameElement")
    static UseNameElementObject useNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") final String useNameElementName,
            @GraphQLName("id") @GraphQLID final String id) {
        UseNameElement useNameElement;

        try {
            var commandForm = OfferUtil.getHome().getGetUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);
            commandForm.setUuid(id);

            useNameElement = new GetUseNameElementCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return useNameElement == null ? null : new UseNameElementObject(useNameElement);
    }

    @GraphQLField
    @GraphQLName("useNameElements")
    static Collection<UseNameElementObject> useNameElements(final DataFetchingEnvironment env) {
        Collection<UseNameElement> useNameElements;
        Collection<UseNameElementObject> useNameElementObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetUseNameElementsForm();

            useNameElements = new GetUseNameElementsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(useNameElements == null) {
            useNameElementObjects = emptyList();
        } else {
            useNameElementObjects = new ArrayList<>(useNameElements.size());

            useNameElements.stream()
                    .map(UseNameElementObject::new)
                    .forEachOrdered(useNameElementObjects::add);
        }

        return useNameElementObjects;
    }

    @GraphQLField
    @GraphQLName("useType")
    static UseTypeObject useType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") final String useTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        UseType useType;

        try {
            var commandForm = OfferUtil.getHome().getGetUseTypeForm();

            commandForm.setUseTypeName(useTypeName);
            commandForm.setUuid(id);

            useType = new GetUseTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return useType == null ? null : new UseTypeObject(useType);
    }

    @GraphQLField
    @GraphQLName("useTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<UseTypeObject> useTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<UseTypeObject> data;

        try {
            var commandForm = OfferUtil.getHome().getGetUseTypesForm();
            var command = new GetUseTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, UseTypeConstants.COMPONENT_VENDOR_NAME, UseTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var useTypes = entities.stream()
                            .map(UseTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, useTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("freeOnBoard")
    static FreeOnBoardObject freeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") final String freeOnBoardName,
            @GraphQLName("id") @GraphQLID final String id) {
        FreeOnBoard freeOnBoard;

        try {
            var commandForm = ShipmentUtil.getHome().getGetFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);
            commandForm.setUuid(id);

            freeOnBoard = new GetFreeOnBoardCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return freeOnBoard == null ? null : new FreeOnBoardObject(freeOnBoard);
    }

    @GraphQLField
    @GraphQLName("freeOnBoards")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<FreeOnBoardObject> freeOnBoards(final DataFetchingEnvironment env) {
        CountingPaginatedData<FreeOnBoardObject> data;

        try {
            var commandForm = ShipmentUtil.getHome().getGetFreeOnBoardsForm();
            var command = new GetFreeOnBoardsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, FreeOnBoardConstants.COMPONENT_VENDOR_NAME, FreeOnBoardConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var freeOnBoards = entities.stream()
                            .map(FreeOnBoardObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, freeOnBoards);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypeCodeType")
    static PaymentProcessorTypeCodeTypeObject paymentProcessorTypeCodeType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("paymentProcessorTypeCodeTypeName") final String paymentProcessorTypeCodeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTypeCodeType paymentProcessorTypeCodeType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeCodeTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setPaymentProcessorTypeCodeTypeName(paymentProcessorTypeCodeTypeName);
            commandForm.setUuid(id);

            paymentProcessorTypeCodeType = new GetPaymentProcessorTypeCodeTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTypeCodeType == null ? null : new PaymentProcessorTypeCodeTypeObject(paymentProcessorTypeCodeType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypeCode")
    static PaymentProcessorTypeCodeObject paymentProcessorTypeCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("paymentProcessorTypeCodeTypeName") final String paymentProcessorTypeCodeTypeName,
            @GraphQLName("paymentProcessorTypeCodeName") final String paymentProcessorTypeCodeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTypeCode paymentProcessorTypeCode;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeCodeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setPaymentProcessorTypeCodeTypeName(paymentProcessorTypeCodeTypeName);
            commandForm.setPaymentProcessorTypeCodeName(paymentProcessorTypeCodeName);
            commandForm.setUuid(id);

            paymentProcessorTypeCode = new GetPaymentProcessorTypeCodeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTypeCode == null ? null : new PaymentProcessorTypeCodeObject(paymentProcessorTypeCode);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTransaction")
    static PaymentProcessorTransactionObject paymentProcessorTransaction(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTransactionName") final String paymentProcessorTransactionName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTransaction paymentProcessorTransaction;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTransactionForm();

            commandForm.setPaymentProcessorTransactionName(paymentProcessorTransactionName);
            commandForm.setUuid(id);

            paymentProcessorTransaction = new GetPaymentProcessorTransactionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTransaction == null ? null : new PaymentProcessorTransactionObject(paymentProcessorTransaction);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTransactions")
    static Collection<PaymentProcessorTransactionObject> paymentProcessorTransactions(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorTransaction> paymentProcessorTransactions;
        Collection<PaymentProcessorTransactionObject> paymentProcessorTransactionObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTransactionsForm();

            paymentProcessorTransactions = new GetPaymentProcessorTransactionsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorTransactions == null) {
            paymentProcessorTransactionObjects = emptyList();
        } else {
            paymentProcessorTransactionObjects = new ArrayList<>(paymentProcessorTransactions.size());

            paymentProcessorTransactions.stream()
                    .map(PaymentProcessorTransactionObject::new)
                    .forEachOrdered(paymentProcessorTransactionObjects::add);
        }

        return paymentProcessorTransactionObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessor")
    static PaymentProcessorObject paymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") final String paymentProcessorName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessor paymentProcessor;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);
            commandForm.setUuid(id);

            paymentProcessor = new GetPaymentProcessorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessor == null ? null : new PaymentProcessorObject(paymentProcessor);
    }

    @GraphQLField
    @GraphQLName("paymentProcessors")
    static Collection<PaymentProcessorObject> paymentProcessors(final DataFetchingEnvironment env) {
        Collection<PaymentProcessor> paymentProcessors;
        Collection<PaymentProcessorObject> paymentProcessorObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorsForm();

            paymentProcessors = new GetPaymentProcessorsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessors == null) {
            paymentProcessorObjects = emptyList();
        } else {
            paymentProcessorObjects = new ArrayList<>(paymentProcessors.size());

            paymentProcessors.stream()
                    .map(PaymentProcessorObject::new)
                    .forEachOrdered(paymentProcessorObjects::add);
        }

        return paymentProcessorObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorType")
    static PaymentProcessorTypeObject paymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorType paymentProcessorType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setUuid(id);

            paymentProcessorType = new GetPaymentProcessorTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorType == null ? null : new PaymentProcessorTypeObject(paymentProcessorType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypes")
    static Collection<PaymentProcessorTypeObject> paymentProcessorTypes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorType> paymentProcessorTypes;
        Collection<PaymentProcessorTypeObject> paymentProcessorTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypesForm();

            paymentProcessorTypes = new GetPaymentProcessorTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorTypes == null) {
            paymentProcessorTypeObjects = emptyList();
        } else {
            paymentProcessorTypeObjects = new ArrayList<>(paymentProcessorTypes.size());

            paymentProcessorTypes.stream()
                    .map(PaymentProcessorTypeObject::new)
                    .forEachOrdered(paymentProcessorTypeObjects::add);
        }

        return paymentProcessorTypeObjects;
    }

    @GraphQLField
    @GraphQLName("paymentMethodType")
    static PaymentMethodTypeObject paymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") final String paymentMethodTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentMethodType paymentMethodType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);
            commandForm.setUuid(id);

            paymentMethodType = new GetPaymentMethodTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentMethodType == null ? null : new PaymentMethodTypeObject(paymentMethodType);
    }

    @GraphQLField
    @GraphQLName("paymentMethodTypes")
    static Collection<PaymentMethodTypeObject> paymentMethodTypes(final DataFetchingEnvironment env) {
        Collection<PaymentMethodType> paymentMethodTypes;
        Collection<PaymentMethodTypeObject> paymentMethodTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentMethodTypesForm();

            paymentMethodTypes = new GetPaymentMethodTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentMethodTypes == null) {
            paymentMethodTypeObjects = emptyList();
        } else {
            paymentMethodTypeObjects = new ArrayList<>(paymentMethodTypes.size());

            paymentMethodTypes.stream()
                    .map(PaymentMethodTypeObject::new)
                    .forEachOrdered(paymentMethodTypeObjects::add);
        }

        return paymentMethodTypeObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorResultCode")
    static PaymentProcessorResultCodeObject paymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") final String paymentProcessorResultCodeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorResultCode paymentProcessorResultCode;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
            commandForm.setUuid(id);

            paymentProcessorResultCode = new GetPaymentProcessorResultCodeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorResultCode == null ? null : new PaymentProcessorResultCodeObject(paymentProcessorResultCode);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorResultCodes")
    static Collection<PaymentProcessorResultCodeObject> paymentProcessorResultCodes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorResultCode> paymentProcessorResultCodes;
        Collection<PaymentProcessorResultCodeObject> paymentProcessorResultCodeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorResultCodesForm();

            paymentProcessorResultCodes = new GetPaymentProcessorResultCodesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorResultCodes == null) {
            paymentProcessorResultCodeObjects = emptyList();
        } else {
            paymentProcessorResultCodeObjects = new ArrayList<>(paymentProcessorResultCodes.size());

            paymentProcessorResultCodes.stream()
                    .map(PaymentProcessorResultCodeObject::new)
                    .forEachOrdered(paymentProcessorResultCodeObjects::add);
        }

        return paymentProcessorResultCodeObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorActionType")
    static PaymentProcessorActionTypeObject paymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") final String paymentProcessorActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorActionType paymentProcessorActionType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
            commandForm.setUuid(id);

            paymentProcessorActionType = new GetPaymentProcessorActionTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorActionType == null ? null : new PaymentProcessorActionTypeObject(paymentProcessorActionType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorActionTypes")
    static Collection<PaymentProcessorActionTypeObject> paymentProcessorActionTypes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorActionType> paymentProcessorActionTypes;
        Collection<PaymentProcessorActionTypeObject> paymentProcessorActionTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorActionTypesForm();

            paymentProcessorActionTypes = new GetPaymentProcessorActionTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorActionTypes == null) {
            paymentProcessorActionTypeObjects = emptyList();
        } else {
            paymentProcessorActionTypeObjects = new ArrayList<>(paymentProcessorActionTypes.size());

            paymentProcessorActionTypes.stream()
                    .map(PaymentProcessorActionTypeObject::new)
                    .forEachOrdered(paymentProcessorActionTypeObjects::add);
        }

        return paymentProcessorActionTypeObjects;
    }

    @GraphQLField
    @GraphQLName("appearance")
    static AppearanceObject appearance(final DataFetchingEnvironment env,
            @GraphQLName("appearanceName") final String appearanceName,
            @GraphQLName("id") @GraphQLID final String id) {
        Appearance appearance;

        try {
            var commandForm = CoreUtil.getHome().getGetAppearanceForm();

            commandForm.setAppearanceName(appearanceName);
            commandForm.setUuid(id);

            appearance = new GetAppearanceCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return appearance == null ? null : new AppearanceObject(appearance);
    }

    @GraphQLField
    @GraphQLName("appearances")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<AppearanceObject> appearances(final DataFetchingEnvironment env) {
        CountingPaginatedData<AppearanceObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetAppearancesForm();
            var command = new GetAppearancesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, AppearanceConstants.COMPONENT_VENDOR_NAME, AppearanceConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var appearances = entities.stream()
                            .map(AppearanceObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, appearances);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityAliasType")
    static EntityAliasTypeObject entityAliasType(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAliasTypeName") final String entityAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityAliasType entityAliasType;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAliasTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAliasTypeName(entityAliasTypeName);
            commandForm.setUuid(id);

            entityAliasType = new GetEntityAliasTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAliasType == null ? null : new EntityAliasTypeObject(entityAliasType, null);
    }

    @GraphQLField
    @GraphQLName("entityAliasTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityAliasTypeObject> entityAliasTypes(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        CountingPaginatedData<EntityAliasTypeObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAliasTypesForm();
            var command = new GetEntityAliasTypesCommand();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setUuid(id);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityAliasTypeConstants.COMPONENT_VENDOR_NAME, EntityAliasTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityAliasTypes = entities.stream()
                            .map(entityAliasType -> new EntityAliasTypeObject(entityAliasType, null))
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityAliasTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityAlias")
    static EntityAliasObject entityAlias(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAliasTypeName") final String entityAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        EntityAlias entityAlias;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAliasForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAliasTypeName(entityAliasTypeName);
            commandForm.setUuid(id);
            commandForm.setAlias(alias);

            entityAlias = new GetEntityAliasCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAlias == null ? null : new EntityAliasObject(entityAlias);
    }

    @GraphQLField
    @GraphQLName("entityAliases")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityAliasObject> entityAliases(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAliasTypeName") final String entityAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        CountingPaginatedData<EntityAliasObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAliasesForm();
            var command = new GetEntityAliasesCommand();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAliasTypeName(entityAliasTypeName);
            commandForm.setUuid(id);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityAliasConstants.COMPONENT_VENDOR_NAME, EntityAliasConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityAliases = entities.stream()
                            .map(EntityAliasObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityAliases);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }


    @GraphQLField
    @GraphQLName("entityAttributeGroup")
    static EntityAttributeGroupObject entityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") final String entityAttributeGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityAttributeGroup entityAttributeGroup;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);
            commandForm.setUuid(id);

            entityAttributeGroup = new GetEntityAttributeGroupCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAttributeGroup == null ? null : new EntityAttributeGroupObject(entityAttributeGroup, null);
    }

    @GraphQLField
    @GraphQLName("entityAttributeGroups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityAttributeGroupObject> entityAttributeGroups(final DataFetchingEnvironment env) {
        CountingPaginatedData<EntityAttributeGroupObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeGroupsForm();
            var command = new GetEntityAttributeGroupsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityAttributeGroupConstants.COMPONENT_VENDOR_NAME, EntityAttributeGroupConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityAttributeGroups = entities.stream()
                            .map(entityAttributeGroup -> new EntityAttributeGroupObject(entityAttributeGroup, null))
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityAttributeGroups);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityAttribute")
    static EntityAttributeObject entityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityAttribute entityAttribute;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setUuid(id);

            entityAttribute = new GetEntityAttributeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAttribute == null ? null : new EntityAttributeObject(entityAttribute, null);
    }

    @GraphQLField
    @GraphQLName("entityAttributes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityAttributeObject> entityAttributes(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeTypeNames") final String entityAttributeTypeNames,
            @GraphQLName("id") @GraphQLID final String id) {
        CountingPaginatedData<EntityAttributeObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributesForm();
            var command = new GetEntityAttributesCommand();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeTypeNames(entityAttributeTypeNames);
            commandForm.setUuid(id);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityAttributeConstants.COMPONENT_VENDOR_NAME, EntityAttributeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityAttributes = entities.stream()
                            .map(entityAttribute -> new EntityAttributeObject(entityAttribute, null))
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityAttributes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityAttributeEntityAttributeGroup")
    static EntityAttributeEntityAttributeGroupObject entityAttributeEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName,
            @GraphQLName("entityAttributeName") @GraphQLNonNull final String entityAttributeName,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName) {
        EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeEntityAttributeGroupForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);

            entityAttributeEntityAttributeGroup = new GetEntityAttributeEntityAttributeGroupCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAttributeEntityAttributeGroup == null ? null : new EntityAttributeEntityAttributeGroupObject(entityAttributeEntityAttributeGroup);
    }

    @GraphQLField
    @GraphQLName("entityAttributeEntityAttributeGroups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityAttributeEntityAttributeGroupObject> entityAttributeEntityAttributeGroups(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeGroupName") final String entityAttributeGroupName) {
        CountingPaginatedData<EntityAttributeEntityAttributeGroupObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeEntityAttributeGroupsForm();
            var command = new GetEntityAttributeEntityAttributeGroupsCommand();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityAttributeEntityAttributeGroupConstants.COMPONENT_VENDOR_NAME, EntityAttributeEntityAttributeGroupConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityAttributeEntityAttributeGroups = entities.stream()
                            .map(EntityAttributeEntityAttributeGroupObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityAttributeEntityAttributeGroups);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityInstance")
    static EntityInstanceObject entityInstance(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("entityRef") final String entityRef) {
        EntityInstance entityInstance;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityInstanceForm();

            commandForm.setUuid(id);
            commandForm.setEntityRef(entityRef);

            entityInstance = new GetEntityInstanceCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityInstance == null ? null : new EntityInstanceObject(entityInstance);
    }

    @GraphQLField
    @GraphQLName("entityInstances")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityInstanceObject> entityInstances(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        CountingPaginatedData<EntityInstanceObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityInstancesForm();
            var command = new GetEntityInstancesCommand();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityInstanceConstants.COMPONENT_VENDOR_NAME, EntityInstanceConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityInstances = entities.stream()
                            .map(EntityInstanceObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityInstances);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityType")
    static EntityTypeObject entityType(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityType entityType;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setUuid(id);

            entityType = new GetEntityTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityType == null ? null : new EntityTypeObject(entityType);
    }

    @GraphQLField
    @GraphQLName("entityTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityTypeObject> entityTypes(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName) {
        CountingPaginatedData<EntityTypeObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityTypesForm();
            var command = new GetEntityTypesCommand();

            commandForm.setComponentVendorName(componentVendorName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityTypeConstants.COMPONENT_VENDOR_NAME, EntityTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityTypes = entities.stream()
                            .map(EntityTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("componentVendor")
    static ComponentVendorObject componentVendor(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("id") @GraphQLID final String id) {
        ComponentVendor componentVendor;

        try {
            var commandForm = CoreUtil.getHome().getGetComponentVendorForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setUuid(id);

            componentVendor = new GetComponentVendorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return componentVendor == null ? null : new ComponentVendorObject(componentVendor);
    }

    @GraphQLField
    @GraphQLName("componentVendors")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ComponentVendorObject> componentVendors(final DataFetchingEnvironment env) {
        CountingPaginatedData<ComponentVendorObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetComponentVendorsForm();
            var command = new GetComponentVendorsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ComponentVendorConstants.COMPONENT_VENDOR_NAME, ComponentVendorConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var componentVendors = entities.stream()
                            .map(ComponentVendorObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, componentVendors);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("inventoryCondition")
    static InventoryConditionObject inventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("id") @GraphQLID final String id) {
        InventoryCondition inventoryCondition;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUuid(id);

            inventoryCondition = new GetInventoryConditionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return inventoryCondition == null ? null : new InventoryConditionObject(inventoryCondition);
    }

    @GraphQLField
    @GraphQLName("inventoryConditions")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<InventoryConditionObject> inventoryConditions(final DataFetchingEnvironment env) {
        CountingPaginatedData<InventoryConditionObject> data;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryConditionsForm();
            var command = new GetInventoryConditionsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, InventoryConditionConstants.COMPONENT_VENDOR_NAME, InventoryConditionConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var inventoryConditions = entities.stream()
                            .map(InventoryConditionObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, inventoryConditions);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("allocationPriority")
    static AllocationPriorityObject allocationPriority(final DataFetchingEnvironment env,
            @GraphQLName("allocationPriorityName") final String allocationPriorityName,
            @GraphQLName("id") @GraphQLID final String id) {
        AllocationPriority allocationPriority;

        try {
            var commandForm = InventoryUtil.getHome().getGetAllocationPriorityForm();

            commandForm.setAllocationPriorityName(allocationPriorityName);
            commandForm.setUuid(id);

            allocationPriority = new GetAllocationPriorityCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return allocationPriority == null ? null : new AllocationPriorityObject(allocationPriority);
    }

    @GraphQLField
    @GraphQLName("allocationPriorities")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<AllocationPriorityObject> allocationPriorities(final DataFetchingEnvironment env) {
        CountingPaginatedData<AllocationPriorityObject> data;

        try {
            var commandForm = InventoryUtil.getHome().getGetAllocationPrioritiesForm();
            var command = new GetAllocationPrioritiesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, AllocationPriorityConstants.COMPONENT_VENDOR_NAME, AllocationPriorityConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var allocationPriorities = entities.stream()
                            .map(AllocationPriorityObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, allocationPriorities);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("inventoryTransactionType")
    static InventoryTransactionTypeObject inventoryTransactionType(final DataFetchingEnvironment env,
            @GraphQLName("inventoryTransactionTypeName") final String inventoryTransactionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        InventoryTransactionType inventoryTransactionType;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryTransactionTypeForm();

            commandForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
            commandForm.setUuid(id);

            inventoryTransactionType = new GetInventoryTransactionTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return inventoryTransactionType == null ? null : new InventoryTransactionTypeObject(inventoryTransactionType);
    }

    @GraphQLField
    @GraphQLName("inventoryTransactionTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<InventoryTransactionTypeObject> inventoryTransactionTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<InventoryTransactionTypeObject> data;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryTransactionTypesForm();
            var command = new GetInventoryTransactionTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, InventoryTransactionTypeConstants.COMPONENT_VENDOR_NAME, InventoryTransactionTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var inventoryTransactionTypes = entities.stream()
                            .map(InventoryTransactionTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, inventoryTransactionTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("lot")
    static LotObject lot(final DataFetchingEnvironment env,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("lotIdentifier") final String lotIdentifier,
            @GraphQLName("id") @GraphQLID final String id) {
        Lot lot;

        try {
            var commandForm = InventoryUtil.getHome().getGetLotForm();

            commandForm.setItemName(itemName);
            commandForm.setLotIdentifier(lotIdentifier);
            commandForm.setUuid(id);

            lot = new GetLotCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return lot == null ? null : new LotObject(lot);
    }

    @GraphQLField
    @GraphQLName("lots")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<LotObject> lots(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        CountingPaginatedData<LotObject> data;

        try {
            var commandForm = InventoryUtil.getHome().getGetLotsForm();
            var command = new GetLotsCommand();

            commandForm.setItemName(itemName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, LotConstants.COMPONENT_VENDOR_NAME, LotConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var lots = entities.stream()
                            .map(LotObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, lots);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentPageLayout")
    static ContentPageLayoutObject contentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") final String contentPageLayoutName,
            @GraphQLName("id") @GraphQLID final String id) {
        ContentPageLayout contentPageLayout;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);
            commandForm.setUuid(id);

            contentPageLayout = new GetContentPageLayoutCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentPageLayout == null ? null : new ContentPageLayoutObject(contentPageLayout);
    }

    @GraphQLField
    @GraphQLName("contentPageLayouts")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentPageLayoutObject> contentPageLayouts(final DataFetchingEnvironment env) {
        CountingPaginatedData<ContentPageLayoutObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutsForm();
            var command = new GetContentPageLayoutsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentPageLayoutConstants.COMPONENT_VENDOR_NAME, ContentPageLayoutConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentPageLayouts = entities.stream()
                            .map(ContentPageLayoutObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentPageLayouts);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentPageLayoutArea")
    static ContentPageLayoutAreaObject contentPageLayoutArea(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder) {
        ContentPageLayoutArea contentPageLayoutArea;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutAreaForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setSortOrder(sortOrder);

            contentPageLayoutArea = new GetContentPageLayoutAreaCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentPageLayoutArea == null ? null : new ContentPageLayoutAreaObject(contentPageLayoutArea);
    }

    @GraphQLField
    @GraphQLName("contentPageLayoutAreas")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentPageLayoutAreaObject> contentPageLayoutAreas(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName) {
        CountingPaginatedData<ContentPageLayoutAreaObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutAreasForm();
            var command = new GetContentPageLayoutAreasCommand();


            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentPageLayoutAreaConstants.COMPONENT_VENDOR_NAME, ContentPageLayoutAreaConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentPageLayoutAreas = entities.stream()
                            .map(ContentPageLayoutAreaObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentPageLayoutAreas);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentPageAreaType")
    static ContentPageAreaTypeObject contentPageAreaType(final DataFetchingEnvironment env,
            @GraphQLName("contentPageAreaTypeName") final String contentPageAreaTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ContentPageAreaType contentPageAreaType;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaTypeForm();

            commandForm.setContentPageAreaTypeName(contentPageAreaTypeName);
            commandForm.setUuid(id);

            contentPageAreaType = new GetContentPageAreaTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentPageAreaType == null ? null : new ContentPageAreaTypeObject(contentPageAreaType);
    }

    @GraphQLField
    @GraphQLName("contentPageAreaTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentPageAreaTypeObject> contentPageAreaTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ContentPageAreaTypeObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaTypesForm();
            var command = new GetContentPageAreaTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentPageAreaTypeConstants.COMPONENT_VENDOR_NAME, ContentPageAreaTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentPageAreaTypes = entities.stream()
                            .map(ContentPageAreaTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentPageAreaTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentWebAddress")
    static ContentWebAddressObject contentWebAddress(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") @GraphQLNonNull final String contentWebAddressName) {
        ContentWebAddress contentWebAddress;

        try {
            var commandForm = ContentUtil.getHome().getGetContentWebAddressForm();

            commandForm.setContentWebAddressName(contentWebAddressName);

            contentWebAddress = new GetContentWebAddressCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentWebAddress == null ? null : new ContentWebAddressObject(contentWebAddress);
    }

    @GraphQLField
    @GraphQLName("contentWebAddresses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentWebAddressObject> contentWebAddresses(final DataFetchingEnvironment env) {
        CountingPaginatedData<ContentWebAddressObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentWebAddressesForm();
            var command = new GetContentWebAddressesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentWebAddressConstants.COMPONENT_VENDOR_NAME, ContentWebAddressConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentWebAddresses = entities.stream()
                            .map(ContentWebAddressObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentWebAddresses);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentCollection")
    static ContentCollectionObject contentCollection(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") final String contentCollectionName) {
        ContentCollection contentCollection;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCollectionForm();

            commandForm.setContentCollectionName(contentCollectionName);

            contentCollection = new GetContentCollectionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCollection == null ? null : new ContentCollectionObject(contentCollection);
    }

    @GraphQLField
    @GraphQLName("contentCollections")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentCollectionObject> contentCollections(final DataFetchingEnvironment env) {
        CountingPaginatedData<ContentCollectionObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCollectionsForm();
            var command = new GetContentCollectionsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentCollectionConstants.COMPONENT_VENDOR_NAME, ContentCollectionConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentCollections = entities.stream()
                            .map(ContentCollectionObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentCollections);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }
    
    @GraphQLField
    @GraphQLName("contentSection")
    static ContentSectionObject contentSection(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentSectionName") final String contentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentSection contentSection;

        try {
            var commandForm = ContentUtil.getHome().getGetContentSectionForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentSection = new GetContentSectionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentSection == null ? null : new ContentSectionObject(contentSection);
    }

    @GraphQLField
    @GraphQLName("contentSections")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentSectionObject> contentSections(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("parentContentSectionName") final String parentContentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        CountingPaginatedData<ContentSectionObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentSectionsForm();
            var command = new GetContentSectionsCommand();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setParentContentSectionName(parentContentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentSectionConstants.COMPONENT_VENDOR_NAME, ContentSectionConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentSections = entities.stream()
                            .map(ContentSectionObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentSections);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentPage")
    static ContentPageObject contentPage(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentSectionName") final String contentSectionName,
            @GraphQLName("contentPageName") final String contentPageName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentPage contentPage;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentPage = new GetContentPageCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentPage == null ? null : new ContentPageObject(contentPage);
    }

    @GraphQLField
    @GraphQLName("contentPages")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentPageObject> contentPages(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentSectionName") final String contentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        CountingPaginatedData<ContentPageObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPagesForm();
            var command = new GetContentPagesCommand();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentPageConstants.COMPONENT_VENDOR_NAME, ContentPageConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentPages = entities.stream()
                            .map(ContentPageObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentPages);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentPageArea")
    static ContentPageAreaObject contentPageArea(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        ContentPageArea contentPageArea;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setSortOrder(sortOrder);
            commandForm.setLanguageIsoName(languageIsoName);

            contentPageArea = new GetContentPageAreaCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentPageArea == null ? null : new ContentPageAreaObject(contentPageArea);
    }

    @GraphQLField
    @GraphQLName("contentPageAreas")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentPageAreaObject> contentPageAreas(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName) {
        CountingPaginatedData<ContentPageAreaObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreasForm();
            var command = new GetContentPageAreasCommand();


            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentPageAreaConstants.COMPONENT_VENDOR_NAME, ContentPageAreaConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentPageAreas = entities.stream()
                            .map(ContentPageAreaObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentPageAreas);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }


    @GraphQLField
    @GraphQLName("contentCatalog")
    static ContentCatalogObject contentCatalog(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCatalog contentCatalog;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalog = new GetContentCatalogCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCatalog == null ? null : new ContentCatalogObject(contentCatalog);
    }

    @GraphQLField
    @GraphQLName("contentCatalogs")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentCatalogObject> contentCatalogs(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        CountingPaginatedData<ContentCatalogObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogsForm();
            var command = new GetContentCatalogsCommand();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentCatalogConstants.COMPONENT_VENDOR_NAME, ContentCatalogConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentCatalogs = entities.stream()
                            .map(ContentCatalogObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentCatalogs);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentCatalogItem")
    static ContentCatalogItemObject contentCatalogItem(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCatalogItem contentCatalogItem;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogItemForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalogItem = new GetContentCatalogItemCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCatalogItem == null ? null : new ContentCatalogItemObject(contentCatalogItem);
    }

    @GraphQLField
    @GraphQLName("contentCatalogItems")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentCatalogItemObject> contentCatalogItems(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        CountingPaginatedData<ContentCatalogItemObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogItemsForm();
            var command = new GetContentCatalogItemsCommand();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentCatalogItemConstants.COMPONENT_VENDOR_NAME, ContentCatalogItemConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentCatalogItems = entities.stream()
                            .map(ContentCatalogItemObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentCatalogItems);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentCategory")
    static ContentCategoryObject contentCategory(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCategory contentCategory;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoryForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategory = new GetContentCategoryCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCategory == null ? null : new ContentCategoryObject(contentCategory);
    }

    @GraphQLField
    @GraphQLName("contentCategories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentCategoryObject> contentCategories(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("parentContentCategoryName") final String parentContentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        CountingPaginatedData<ContentCategoryObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoriesForm();
            var command = new GetContentCategoriesCommand();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setParentContentCategoryName(parentContentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentCategoryConstants.COMPONENT_VENDOR_NAME, ContentCategoryConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentCategories = entities.stream()
                            .map(ContentCategoryObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentCategories);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("contentCategoryItem")
    static ContentCategoryItemObject contentCategoryItem(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCategoryItem contentCategoryItem;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoryItemForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategoryItem = new GetContentCategoryItemCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCategoryItem == null ? null : new ContentCategoryItemObject(contentCategoryItem);
    }

    @GraphQLField
    @GraphQLName("contentCategoryItems")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ContentCategoryItemObject> contentCategoryItems(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        CountingPaginatedData<ContentCategoryItemObject> data;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoryItemsForm();
            var command = new GetContentCategoryItemsCommand();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ContentCategoryItemConstants.COMPONENT_VENDOR_NAME, ContentCategoryItemConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var contentCategoryItems = entities.stream()
                            .map(ContentCategoryItemObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, contentCategoryItems);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("mimeTypeFileExtension")
    static MimeTypeFileExtensionObject mimeTypeFileExtension(final DataFetchingEnvironment env,
            @GraphQLName("fileExtension") @GraphQLNonNull final String fileExtension) {
        MimeTypeFileExtension mimeTypeFileExtension;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionForm();

            commandForm.setFileExtension(fileExtension);

            mimeTypeFileExtension = new GetMimeTypeFileExtensionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeTypeFileExtension == null ? null : new MimeTypeFileExtensionObject(mimeTypeFileExtension);
    }

    @GraphQLField
    @GraphQLName("mimeTypeFileExtensions")
    static Collection<MimeTypeFileExtensionObject> mimeTypeFileExtensions(final DataFetchingEnvironment env) {
        Collection<MimeTypeFileExtension> mimeTypeFileExtensions;
        Collection<MimeTypeFileExtensionObject> mimeTypeFileExtensionObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionsForm();

            mimeTypeFileExtensions = new GetMimeTypeFileExtensionsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypeFileExtensions == null) {
            mimeTypeFileExtensionObjects = emptyList();
        } else {
            mimeTypeFileExtensionObjects = new ArrayList<>(mimeTypeFileExtensions.size());

            mimeTypeFileExtensions.stream()
                    .map(MimeTypeFileExtensionObject::new)
                    .forEachOrdered(mimeTypeFileExtensionObjects::add);
        }

        return mimeTypeFileExtensionObjects;
    }

    @GraphQLField
    @GraphQLName("mimeTypeUsageType")
    static MimeTypeUsageTypeObject mimeTypeUsageType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeUsageTypeName") @GraphQLNonNull final String mimeTypeUsageTypeName) {
        MimeTypeUsageType mimeTypeUsageType;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypeForm();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            mimeTypeUsageType = new GetMimeTypeUsageTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeTypeUsageType == null ? null : new MimeTypeUsageTypeObject(mimeTypeUsageType);
    }

    @GraphQLField
    @GraphQLName("mimeTypeUsageTypes")
    static Collection<MimeTypeUsageTypeObject> mimeTypeUsageTypes(final DataFetchingEnvironment env) {
        Collection<MimeTypeUsageType> mimeTypeUsageTypes;
        Collection<MimeTypeUsageTypeObject> mimeTypeUsageTypeObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypesForm();

            mimeTypeUsageTypes = new GetMimeTypeUsageTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypeUsageTypes == null) {
            mimeTypeUsageTypeObjects = emptyList();
        } else {
            mimeTypeUsageTypeObjects = new ArrayList<>(mimeTypeUsageTypes.size());

            mimeTypeUsageTypes.stream()
                    .map(MimeTypeUsageTypeObject::new)
                    .forEachOrdered(mimeTypeUsageTypeObjects::add);
        }

        return mimeTypeUsageTypeObjects;
    }

    @GraphQLField
    @GraphQLName("mimeType")
    static MimeTypeObject mimeType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeName") @GraphQLNonNull final String mimeTypeName) {
        MimeType mimeType;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeForm();

            commandForm.setMimeTypeName(mimeTypeName);

            mimeType = new GetMimeTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeType == null ? null : new MimeTypeObject(mimeType);
    }

    @GraphQLField
    @GraphQLName("mimeTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<MimeTypeObject> mimeTypes(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeUsageTypeName") final String mimeTypeUsageTypeName) {
        CountingPaginatedData<MimeTypeObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypesForm();
            var command = new GetMimeTypesCommand();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, MimeTypeConstants.COMPONENT_VENDOR_NAME, MimeTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var mimeTypes = entities.stream()
                            .map(MimeTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, mimeTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("queueType")
    static QueueTypeObject queueType(final DataFetchingEnvironment env,
            @GraphQLName("queueTypeName") @GraphQLNonNull final String queueTypeName) {
        QueueType queueType;

        try {
            var commandForm = QueueUtil.getHome().getGetQueueTypeForm();

            commandForm.setQueueTypeName(queueTypeName);

            queueType = new GetQueueTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return queueType == null ? null : new QueueTypeObject(queueType);
    }

    @GraphQLField
    @GraphQLName("queueTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<QueueTypeObject> queueTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<QueueTypeObject> data;

        try {
            var commandForm = QueueUtil.getHome().getGetQueueTypesForm();
            var command = new GetQueueTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, QueueTypeConstants.COMPONENT_VENDOR_NAME, QueueTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var queueTypes = entities.stream()
                            .map(QueueTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, queueTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUse")
    static UnitOfMeasureKindUseObject unitOfMeasureKindUse(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") @GraphQLNonNull final String unitOfMeasureKindUseTypeName,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        UnitOfMeasureKindUse unitOfMeasureKindUse;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUse = new GetUnitOfMeasureKindUseCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return unitOfMeasureKindUse == null ? null : new UnitOfMeasureKindUseObject(unitOfMeasureKindUse);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUses")
    static Collection<UnitOfMeasureKindUseObject> unitOfMeasureKindUses(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") final String unitOfMeasureKindUseTypeName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName) {
        Collection<UnitOfMeasureKindUse> unitOfMeasureKindUses;
        Collection<UnitOfMeasureKindUseObject> unitOfMeasureKindUseObjects;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUsesForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUses = new GetUnitOfMeasureKindUsesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(unitOfMeasureKindUses == null) {
            unitOfMeasureKindUseObjects = emptyList();
        } else {
            unitOfMeasureKindUseObjects = new ArrayList<>(unitOfMeasureKindUses.size());

            unitOfMeasureKindUses.stream()
                    .map(UnitOfMeasureKindUseObject::new)
                    .forEachOrdered(unitOfMeasureKindUseObjects::add);
        }

        return unitOfMeasureKindUseObjects;
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureType")
    static UnitOfMeasureTypeObject unitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        UnitOfMeasureType unitOfMeasureType;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypeForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            unitOfMeasureType = new GetUnitOfMeasureTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return unitOfMeasureType == null ? null : new UnitOfMeasureTypeObject(unitOfMeasureType);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<UnitOfMeasureTypeObject> unitOfMeasureTypes(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        CountingPaginatedData<UnitOfMeasureTypeObject> data;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypesForm();
            var command = new GetUnitOfMeasureTypesCommand();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, UnitOfMeasureTypeConstants.COMPONENT_VENDOR_NAME, UnitOfMeasureTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var unitOfMeasureTypes = entities.stream()
                            .map(UnitOfMeasureTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, unitOfMeasureTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKind")
    static UnitOfMeasureKindObject unitOfMeasureKind(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        UnitOfMeasureKind unitOfMeasureKind;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKind = new GetUnitOfMeasureKindCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return unitOfMeasureKind == null ? null : new UnitOfMeasureKindObject(unitOfMeasureKind);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKinds")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<UnitOfMeasureKindObject> unitOfMeasureKinds(final DataFetchingEnvironment env) {
        CountingPaginatedData<UnitOfMeasureKindObject> data;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindsForm();
            var command = new GetUnitOfMeasureKindsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, UnitOfMeasureKindConstants.COMPONENT_VENDOR_NAME, UnitOfMeasureKindConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var unitOfMeasureKinds = entities.stream()
                            .map(UnitOfMeasureKindObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, unitOfMeasureKinds);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUseType")
    static UnitOfMeasureKindUseTypeObject unitOfMeasureKindUseType(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") @GraphQLNonNull final String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUseType unitOfMeasureKindUseType;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypeForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);

            unitOfMeasureKindUseType = new GetUnitOfMeasureKindUseTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return unitOfMeasureKindUseType == null ? null : new UnitOfMeasureKindUseTypeObject(unitOfMeasureKindUseType);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUseTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<UnitOfMeasureKindUseTypeObject> unitOfMeasureKindUseTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<UnitOfMeasureKindUseTypeObject> data;

        try {
            var uomControl = Session.getModelController(UomControl.class);
            var totalCount = uomControl.countUnitOfMeasureKindUseType();

            try(var objectLimiter = new ObjectLimiter(env, UnitOfMeasureKindUseTypeConstants.COMPONENT_VENDOR_NAME, UnitOfMeasureKindUseTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypesForm();
                var entities = new GetUnitOfMeasureKindUseTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var objects = entities.stream()
                            .map(UnitOfMeasureKindUseTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, objects);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityAttributeType")
    static EntityAttributeTypeObject entityAttributeType(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeTypeName") @GraphQLNonNull final String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypeForm();

            commandForm.setEntityAttributeTypeName(entityAttributeTypeName);

            entityAttributeType = new GetEntityAttributeTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAttributeType == null ? null : new EntityAttributeTypeObject(entityAttributeType);
    }

    @GraphQLField
    @GraphQLName("entityAttributeTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EntityAttributeTypeObject> entityAttributeTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<EntityAttributeTypeObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypesForm();
            var command = new GetEntityAttributeTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, EntityAttributeTypeConstants.COMPONENT_VENDOR_NAME, EntityAttributeTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var entityAttributeTypes = entities.stream()
                            .map(EntityAttributeTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, entityAttributeTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("componentVendorResults")
    static ComponentVendorResultsObject componentVendorResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        ComponentVendorResultsObject componentVendorResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetComponentVendorResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetComponentVendorResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                componentVendorResultsObject = new ComponentVendorResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return componentVendorResultsObject;
    }

    @GraphQLField
    @GraphQLName("entityTypeResults")
    static EntityTypeResultsObject entityTypeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        EntityTypeResultsObject entityTypeResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetEntityTypeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetEntityTypeResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                entityTypeResultsObject = new EntityTypeResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityTypeResultsObject;
    }

    @GraphQLField
    @GraphQLName("entityAliasTypeResults")
    static EntityAliasTypeResultsObject entityAliasTypeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        EntityAliasTypeResultsObject entityAliasTypeResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetEntityAliasTypeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetEntityAliasTypeResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                entityAliasTypeResultsObject = new EntityAliasTypeResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAliasTypeResultsObject;
    }

    @GraphQLField
    @GraphQLName("entityAttributeGroupResults")
    static EntityAttributeGroupResultsObject entityAttributeGroupResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        EntityAttributeGroupResultsObject entityAttributeGroupResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetEntityAttributeGroupResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetEntityAttributeGroupResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                entityAttributeGroupResultsObject = new EntityAttributeGroupResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAttributeGroupResultsObject;
    }

    @GraphQLField
    @GraphQLName("entityAttributeResults")
    static EntityAttributeResultsObject entityAttributeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        EntityAttributeResultsObject entityAttributeResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetEntityAttributeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetEntityAttributeResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                entityAttributeResultsObject = new EntityAttributeResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAttributeResultsObject;
    }

    @GraphQLField
    @GraphQLName("entityListItemResults")
    static EntityListItemResultsObject entityListItemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        EntityListItemResultsObject entityListItemResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetEntityListItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetEntityListItemResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                entityListItemResultsObject = new EntityListItemResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityListItemResultsObject;
    }

    @GraphQLField
    @GraphQLName("customerResults")
    static CustomerResultsObject customerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        CustomerResultsObject customerResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetCustomerResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                customerResultsObject = new CustomerResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return customerResultsObject;
    }

    @GraphQLField
    @GraphQLName("employeeResults")
    static EmployeeResultsObject employeeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        EmployeeResultsObject employeeResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetEmployeeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetEmployeeResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                employeeResultsObject = new EmployeeResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return employeeResultsObject;
    }

    @GraphQLField
    @GraphQLName("itemResults")
    static ItemResultsObject itemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        ItemResultsObject itemResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetItemResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                itemResultsObject = new ItemResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemResultsObject;
    }

    @GraphQLField
    @GraphQLName("checkItemSpelling")
    static CheckItemSpellingObject checkItemSpelling(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("q") final String q) {
        var checkItemSpellingObject = new CheckItemSpellingObject();

        try {
            var commandForm = SearchUtil.getHome().getCheckItemSpellingForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setQ(q);

            var commandResult = SearchUtil.getHome().checkItemSpelling(getUserVisitPK(env), commandForm);
            checkItemSpellingObject.setCommandResult(commandResult);
            checkItemSpellingObject.setResult(commandResult.hasErrors() ? null : (CheckItemSpellingResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return checkItemSpellingObject;
    }

    @GraphQLField
    @GraphQLName("vendorResults")
    static VendorResultsObject vendorResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        VendorResultsObject vendorResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetVendorResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetVendorResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                vendorResultsObject = new VendorResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return vendorResultsObject;
    }

    @GraphQLField
    @GraphQLName("shippingMethodResults")
    static ShippingMethodResultsObject shippingMethodResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        ShippingMethodResultsObject shippingMethodResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetShippingMethodResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetShippingMethodResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                shippingMethodResultsObject = new ShippingMethodResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return shippingMethodResultsObject;
    }

    @GraphQLField
    @GraphQLName("warehouseResults")
    static WarehouseResultsObject warehouseResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        WarehouseResultsObject warehouseResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetWarehouseResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetWarehouseResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                warehouseResultsObject = new WarehouseResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return warehouseResultsObject;
    }

    @GraphQLField
    @GraphQLName("contentCatalogResults")
    static ContentCatalogResultsObject contentCatalogResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        ContentCatalogResultsObject contentCatalogResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetContentCatalogResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetContentCatalogResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                contentCatalogResultsObject = new ContentCatalogResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCatalogResultsObject;
    }

    @GraphQLField
    @GraphQLName("contentCatalogItemResults")
    static ContentCatalogItemResultsObject contentCatalogItemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        ContentCatalogItemResultsObject contentCatalogItemResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetContentCatalogItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetContentCatalogItemResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                contentCatalogItemResultsObject = new ContentCatalogItemResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCatalogItemResultsObject;
    }

    @GraphQLField
    @GraphQLName("contentCategoryResults")
    static ContentCategoryResultsObject contentCategoryResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        ContentCategoryResultsObject contentCategoryResultsObject = null;

        try {
            var commandForm = SearchUtil.getHome().getGetContentCategoryResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetContentCategoryResultsCommand().canQueryByGraphQl(getUserVisitPK(env), commandForm)) {
                contentCategoryResultsObject = new ContentCategoryResultsObject(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return contentCategoryResultsObject;
    }

    @GraphQLField
    @GraphQLName("color")
    static ColorObject color(final DataFetchingEnvironment env,
            @GraphQLName("colorName") final String colorName,
            @GraphQLName("id") @GraphQLID final String id) {
        Color color;

        try {
            var commandForm = CoreUtil.getHome().getGetColorForm();

            commandForm.setColorName(colorName);
            commandForm.setUuid(id);

            color = new GetColorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return color == null ? null : new ColorObject(color);
    }

    @GraphQLField
    @GraphQLName("colors")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ColorObject> colors(final DataFetchingEnvironment env) {
        CountingPaginatedData<ColorObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetColorsForm();
            var command = new GetColorsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ColorConstants.COMPONENT_VENDOR_NAME, ColorConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var colors = entities.stream()
                            .map(ColorObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, colors);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("fontStyle")
    static FontStyleObject fontStyle(final DataFetchingEnvironment env,
            @GraphQLName("fontStyleName") final String fontStyleName,
            @GraphQLName("id") @GraphQLID final String id) {
        FontStyle fontStyle;

        try {
            var commandForm = CoreUtil.getHome().getGetFontStyleForm();

            commandForm.setFontStyleName(fontStyleName);
            commandForm.setUuid(id);

            fontStyle = new GetFontStyleCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return fontStyle == null ? null : new FontStyleObject(fontStyle);
    }

    @GraphQLField
    @GraphQLName("fontStyles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<FontStyleObject> fontStyles(final DataFetchingEnvironment env) {
        CountingPaginatedData<FontStyleObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetFontStylesForm();
            var command = new GetFontStylesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, FontStyleConstants.COMPONENT_VENDOR_NAME, FontStyleConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var fontStyles = entities.stream()
                            .map(FontStyleObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, fontStyles);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("fontWeight")
    static FontWeightObject fontWeight(final DataFetchingEnvironment env,
            @GraphQLName("fontWeightName") final String fontWeightName,
            @GraphQLName("id") @GraphQLID final String id) {
        FontWeight fontWeight;

        try {
            var commandForm = CoreUtil.getHome().getGetFontWeightForm();

            commandForm.setFontWeightName(fontWeightName);
            commandForm.setUuid(id);

            fontWeight = new GetFontWeightCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return fontWeight == null ? null : new FontWeightObject(fontWeight);
    }

    @GraphQLField
    @GraphQLName("fontWeights")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<FontWeightObject> fontWeights(final DataFetchingEnvironment env) {
        CountingPaginatedData<FontWeightObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetFontWeightsForm();
            var command = new GetFontWeightsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, FontWeightConstants.COMPONENT_VENDOR_NAME, FontWeightConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var fontWeights = entities.stream()
                            .map(FontWeightObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, fontWeights);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("textDecoration")
    static TextDecorationObject textDecoration(final DataFetchingEnvironment env,
            @GraphQLName("textDecorationName") final String textDecorationName,
            @GraphQLName("id") @GraphQLID final String id) {
        TextDecoration textDecoration;

        try {
            var commandForm = CoreUtil.getHome().getGetTextDecorationForm();

            commandForm.setTextDecorationName(textDecorationName);
            commandForm.setUuid(id);

            textDecoration = new GetTextDecorationCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return textDecoration == null ? null : new TextDecorationObject(textDecoration);
    }

    @GraphQLField
    @GraphQLName("textDecorations")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TextDecorationObject> textDecorations(final DataFetchingEnvironment env) {
        CountingPaginatedData<TextDecorationObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetTextDecorationsForm();
            var command = new GetTextDecorationsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, TextDecorationConstants.COMPONENT_VENDOR_NAME, TextDecorationConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var textDecorations = entities.stream()
                            .map(TextDecorationObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, textDecorations);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("textTransformation")
    static TextTransformationObject textTransformation(final DataFetchingEnvironment env,
            @GraphQLName("textTransformationName") final String textTransformationName,
            @GraphQLName("id") @GraphQLID final String id) {
        TextTransformation textTransformation;

        try {
            var commandForm = CoreUtil.getHome().getGetTextTransformationForm();

            commandForm.setTextTransformationName(textTransformationName);
            commandForm.setUuid(id);

            textTransformation = new GetTextTransformationCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return textTransformation == null ? null : new TextTransformationObject(textTransformation);
    }

    @GraphQLField
    @GraphQLName("textTransformations")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TextTransformationObject> textTransformations(final DataFetchingEnvironment env) {
        CountingPaginatedData<TextTransformationObject> data;

        try {
            var commandForm = CoreUtil.getHome().getGetTextTransformationsForm();
            var command = new GetTextTransformationsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, TextTransformationConstants.COMPONENT_VENDOR_NAME, TextTransformationConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var textTransformations = entities.stream()
                            .map(TextTransformationObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, textTransformations);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("userLogin")
    static UserLoginObject userLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") final String username,
            @GraphQLName("id") @GraphQLID final String id) {
        UserLogin userLogin;
        UserLogin foundByUsernameUserLogin;

        try {
            var commandForm = UserUtil.getHome().getGetUserLoginForm();

            commandForm.setUsername(username);
            commandForm.setUuid(id);

            var getUserLoginCommand = new GetUserLoginCommand();
            userLogin = getUserLoginCommand.getEntityForGraphQl(getUserVisitPK(env), commandForm);
            foundByUsernameUserLogin = getUserLoginCommand.foundByUsernameUserLogin;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return userLogin == null && foundByUsernameUserLogin == null? null : new UserLoginObject(userLogin, foundByUsernameUserLogin);
    }

    @GraphQLField
    @GraphQLName("recoveryQuestion")
    static RecoveryQuestionObject recoveryQuestion(final DataFetchingEnvironment env,
            @GraphQLName("recoveryQuestionName") final String recoveryQuestionName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") final String username) {
        RecoveryQuestion recoveryQuestion;

        try {
            var commandForm = UserUtil.getHome().getGetRecoveryQuestionForm();

            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setUuid(id);
            commandForm.setUsername(username);

            recoveryQuestion = new GetRecoveryQuestionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return recoveryQuestion == null ? null : new RecoveryQuestionObject(recoveryQuestion);
    }

    @GraphQLField
    @GraphQLName("recoveryQuestions")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<RecoveryQuestionObject> recoveryQuestions(final DataFetchingEnvironment env) {
        CountingPaginatedData<RecoveryQuestionObject> data;

        try {
            var commandForm = UserUtil.getHome().getGetRecoveryQuestionsForm();
            var command = new GetRecoveryQuestionsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, RecoveryQuestionConstants.COMPONENT_VENDOR_NAME, RecoveryQuestionConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var recoveryQuestions = entities.stream()
                            .map(RecoveryQuestionObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, recoveryQuestions);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("userSession")
    static UserSessionObject userSession(final DataFetchingEnvironment env) {
        var userSession = BaseGraphQl.getUserSession(env);

        return userSession == null ? null : new UserSessionObject(userSession);
    }

    @GraphQLField
    @GraphQLName("userVisitGroup")
    static UserVisitGroupObject userVisitGroup(final DataFetchingEnvironment env,
            @GraphQLName("userVisitGroupName") final String userVisitGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        UserVisitGroup userVisitGroup;

        try {
            var commandForm = UserUtil.getHome().getGetUserVisitGroupForm();

            commandForm.setUserVisitGroupName(userVisitGroupName);
            commandForm.setUuid(id);

            userVisitGroup = new GetUserVisitGroupCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return userVisitGroup == null ? null : new UserVisitGroupObject(userVisitGroup);
    }

    @GraphQLField
    @GraphQLName("userVisitGroups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<UserVisitGroupObject> userVisitGroups(final DataFetchingEnvironment env) {
        CountingPaginatedData<UserVisitGroupObject> data;

        try {
            var userControl = Session.getModelController(UserControl.class);
            var totalCount = userControl.countUserVisitGroups();

            try(var objectLimiter = new ObjectLimiter(env, UserVisitGroupConstants.COMPONENT_VENDOR_NAME, UserVisitGroupConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = UserUtil.getHome().getGetUserVisitGroupsForm();
                var entities = new GetUserVisitGroupsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var userVisitGroups = entities.stream().map(UserVisitGroupObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, userVisitGroups);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("userVisit")
    static UserVisitObject userVisit(final DataFetchingEnvironment env) {
        var userVisit = BaseGraphQl.getUserVisit(env);

        return userVisit == null ? null : new UserVisitObject(userVisit);
    }

    @GraphQLField
    @GraphQLName("symbolPosition")
    static SymbolPositionObject symbolPosition(final DataFetchingEnvironment env,
            @GraphQLName("symbolPositionName") final String symbolPositionName,
            @GraphQLName("id") @GraphQLID final String id) {
        SymbolPosition symbolPosition;

        try {
            var commandForm = AccountingUtil.getHome().getGetSymbolPositionForm();

            commandForm.setSymbolPositionName(symbolPositionName);
            commandForm.setUuid(id);

            symbolPosition = new GetSymbolPositionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return symbolPosition == null ? null : new SymbolPositionObject(symbolPosition);
    }

    @GraphQLField
    @GraphQLName("symbolPositions")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SymbolPositionObject> symbolPositions(final DataFetchingEnvironment env) {
        CountingPaginatedData<SymbolPositionObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetSymbolPositionsForm();
            var command = new GetSymbolPositionsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SymbolPositionConstants.COMPONENT_VENDOR_NAME, SymbolPositionConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var symbolPositions = entities.stream()
                            .map(SymbolPositionObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, symbolPositions);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("currency")
    static CurrencyObject currency(final DataFetchingEnvironment env,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("id") @GraphQLID final String id) {
        Currency currency;

        try {
            var commandForm = AccountingUtil.getHome().getGetCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUuid(id);

            currency = new GetCurrencyCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return currency == null ? null : new CurrencyObject(currency);
    }

    @GraphQLField
    @GraphQLName("currencies")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<CurrencyObject> currencies(final DataFetchingEnvironment env) {
        CountingPaginatedData<CurrencyObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetCurrenciesForm();
            var command = new GetCurrenciesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, CurrencyConstants.COMPONENT_VENDOR_NAME, CurrencyConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var currencies = entities.stream()
                            .map(CurrencyObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, currencies);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("language")
    static LanguageObject language(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("id") @GraphQLID final String id) {
        Language language;

        try {
            var commandForm = PartyUtil.getHome().getGetLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setUuid(id);

            language = new GetLanguageCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return language == null ? null : new LanguageObject(language);
    }

    @GraphQLField
    @GraphQLName("languages")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<LanguageObject> languages(final DataFetchingEnvironment env) {
        CountingPaginatedData<LanguageObject> data;

        try {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countLanguages();

            try(var objectLimiter = new ObjectLimiter(env, LanguageConstants.COMPONENT_VENDOR_NAME, LanguageConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = PartyUtil.getHome().getGetLanguagesForm();
                var entities = new GetLanguagesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var languages = entities.stream().map(LanguageObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, languages);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("dateTimeFormat")
    static DateTimeFormatObject dateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") final String dateTimeFormatName,
            @GraphQLName("id") @GraphQLID final String id) {
        DateTimeFormat dateTimeFormat;

        try {
            var commandForm = PartyUtil.getHome().getGetDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);
            commandForm.setUuid(id);

            dateTimeFormat = new GetDateTimeFormatCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return dateTimeFormat == null ? null : new DateTimeFormatObject(dateTimeFormat);
    }

    @GraphQLField
    @GraphQLName("dateTimeFormats")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<DateTimeFormatObject> dateTimeFormats(final DataFetchingEnvironment env) {
        CountingPaginatedData<DateTimeFormatObject> data;

        try {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countDateTimeFormats();

            try(var objectLimiter = new ObjectLimiter(env, DateTimeFormatConstants.COMPONENT_VENDOR_NAME, DateTimeFormatConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = PartyUtil.getHome().getGetDateTimeFormatsForm();
                var entities = new GetDateTimeFormatsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var dateTimeFormats = entities.stream().map(DateTimeFormatObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, dateTimeFormats);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("timeZone")
    static TimeZoneObject timeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") final String javaTimeZoneName,
            @GraphQLName("id") @GraphQLID final String id) {
        TimeZone timeZone;

        try {
            var commandForm = PartyUtil.getHome().getGetTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);
            commandForm.setUuid(id);

            timeZone = new GetTimeZoneCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return timeZone == null ? null : new TimeZoneObject(timeZone);
    }

    @GraphQLField
    @GraphQLName("timeZones")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TimeZoneObject> timeZones(final DataFetchingEnvironment env) {
        CountingPaginatedData<TimeZoneObject> data;

        try {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countTimeZones();

            try(var objectLimiter = new ObjectLimiter(env, TimeZoneConstants.COMPONENT_VENDOR_NAME, TimeZoneConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = PartyUtil.getHome().getGetTimeZonesForm();
                var entities = new GetTimeZonesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var timeZones = entities.stream().map(TimeZoneObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, timeZones);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("customerType")
    static CustomerTypeObject customerType(final DataFetchingEnvironment env,
            @GraphQLName("customerTypeName") final String customerTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        CustomerType customerType;

        try {
            var commandForm = CustomerUtil.getHome().getGetCustomerTypeForm();

            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setUuid(id);

            customerType = new GetCustomerTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return customerType == null ? null : new CustomerTypeObject(customerType);
    }

    @GraphQLField
    @GraphQLName("customerTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<CustomerTypeObject> customerTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<CustomerTypeObject> data;

        try {
            var commandForm = CustomerUtil.getHome().getGetCustomerTypesForm();
            var command = new GetCustomerTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, CustomerTypeConstants.COMPONENT_VENDOR_NAME, CustomerTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var customerTypes = entities.stream()
                            .map(CustomerTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, customerTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("customer")
    static CustomerObject customer(final DataFetchingEnvironment env,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        Customer customer;

        try {
            var commandForm = CustomerUtil.getHome().getGetCustomerForm();

            commandForm.setCustomerName(customerName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            customer = new GetCustomerCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return customer == null ? null : new CustomerObject(customer);
    }

    @GraphQLField
    @GraphQLName("customers")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<CustomerObject> customers(final DataFetchingEnvironment env) {
        CountingPaginatedData<CustomerObject> data;

        try {
            var commandForm = CustomerUtil.getHome().getGetCustomersForm();
            var command = new GetCustomersCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, CustomerConstants.COMPONENT_VENDOR_NAME, CustomerConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var customers = entities.stream()
                            .map(CustomerObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, customers);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("employee")
    static EmployeeObject employee(final DataFetchingEnvironment env,
            @GraphQLName("employeeName") final String employeeName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyEmployee partyEmployee;

        try {
            var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

            commandForm.setEmployeeName(employeeName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            partyEmployee = new GetEmployeeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return partyEmployee == null ? null : new EmployeeObject(partyEmployee);
    }

    @GraphQLField
    @GraphQLName("employees")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<EmployeeObject> employees(final DataFetchingEnvironment env) {
        CountingPaginatedData<EmployeeObject> data;

        try {
            var commandForm = EmployeeUtil.getHome().getGetEmployeesForm();
            var command = new GetEmployeesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, PartyEmployeeConstants.COMPONENT_VENDOR_NAME, PartyEmployeeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var employees = entities.stream()
                            .map(EmployeeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, employees);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("vendorType")
    static VendorTypeObject vendorType(final DataFetchingEnvironment env,
            @GraphQLName("vendorTypeName") final String vendorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        VendorType vendorType;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorTypeForm();

            commandForm.setVendorTypeName(vendorTypeName);
            commandForm.setUuid(id);

            vendorType = new GetVendorTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return vendorType == null ? null : new VendorTypeObject(vendorType);
    }

    @GraphQLField
    @GraphQLName("vendorTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<VendorTypeObject> vendorTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<VendorTypeObject> data;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorTypesForm();
            var command = new GetVendorTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, VendorTypeConstants.COMPONENT_VENDOR_NAME, VendorTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var vendorTypes = entities.stream()
                            .map(VendorTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, vendorTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("vendor")
    static VendorObject vendor(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        Vendor vendor;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            vendor = new GetVendorCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return vendor == null ? null : new VendorObject(vendor);
    }

    @GraphQLField
    @GraphQLName("vendors")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<VendorObject> vendors(final DataFetchingEnvironment env) {
        CountingPaginatedData<VendorObject> data;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorsForm();
            var command = new GetVendorsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, VendorConstants.COMPONENT_VENDOR_NAME, VendorConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var vendors = entities.stream()
                            .map(VendorObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, vendors);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("vendorItem")
    static VendorItemObject vendorItem(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("vendorItemName") final String vendorItemName,
            @GraphQLName("id") @GraphQLID final String id) {
        VendorItem vendorItem;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorItemForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setVendorItemName(vendorItemName);
            commandForm.setUuid(id);

            vendorItem = new GetVendorItemCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return vendorItem == null ? null : new VendorItemObject(vendorItem);
    }

    @GraphQLField
    @GraphQLName("vendorItems")
    static Collection<VendorItemObject> vendorItems(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("itemName") final String itemName) {
        Collection<VendorItem> vendorItems;
        Collection<VendorItemObject> vendorItemObjects;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorItemsForm();

            commandForm.setVendorName(vendorName);
            commandForm.setItemName(itemName);

            vendorItems = new GetVendorItemsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(vendorItems == null) {
            vendorItemObjects = emptyList();
        } else {
            vendorItemObjects = new ArrayList<>(vendorItems.size());

            vendorItems.stream()
                    .map(VendorItemObject::new)
                    .forEachOrdered(vendorItemObjects::add);
        }

        return vendorItemObjects;
    }

    @GraphQLField
    @GraphQLName("vendorItemCost")
    static VendorItemCostObject vendorItemCost(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("vendorItemName") @GraphQLNonNull final String vendorItemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        VendorItemCost vendorItemCost;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorItemCostForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setVendorItemName(vendorItemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            vendorItemCost = new GetVendorItemCostCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return vendorItemCost == null ? null : new VendorItemCostObject(vendorItemCost);
    }

    @GraphQLField
    @GraphQLName("vendorItemCosts")
    static Collection<VendorItemCostObject> vendorItemCosts(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") @GraphQLNonNull final String vendorName,
            @GraphQLName("vendorItemName") @GraphQLNonNull final String vendorItemName) {
        Collection<VendorItemCost> vendorItemCosts;
        Collection<VendorItemCostObject> vendorItemCostObjects;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorItemCostsForm();

            commandForm.setVendorName(vendorName);
            commandForm.setVendorItemName(vendorItemName);

            vendorItemCosts = new GetVendorItemCostsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(vendorItemCosts == null) {
            vendorItemCostObjects = emptyList();
        } else {
            vendorItemCostObjects = new ArrayList<>(vendorItemCosts.size());

            vendorItemCosts.stream()
                    .map(VendorItemCostObject::new)
                    .forEachOrdered(vendorItemCostObjects::add);
        }

        return vendorItemCostObjects;
    }

    @GraphQLField
    @GraphQLName("roleType")
    static RoleTypeObject roleType(final DataFetchingEnvironment env,
            @GraphQLName("roleTypeName") final String roleTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        RoleType roleType;

        try {
            var commandForm = PartyUtil.getHome().getGetRoleTypeForm();

            commandForm.setRoleTypeName(roleTypeName);
            commandForm.setUuid(id);

            roleType = new GetRoleTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return roleType == null ? null : new RoleTypeObject(roleType);
    }

    @GraphQLField
    @GraphQLName("roleTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<RoleTypeObject> roleTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<RoleTypeObject> data;

        try {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countRoleTypes();

            try(var objectLimiter = new ObjectLimiter(env, RoleTypeConstants.COMPONENT_VENDOR_NAME, RoleTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = PartyUtil.getHome().getGetRoleTypesForm();
                var entities = new GetRoleTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var roleTypes = entities.stream().map(RoleTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, roleTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("partyType")
    static PartyTypeObject partyType(final DataFetchingEnvironment env,
            @GraphQLName("partyTypeName") final String partyTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyType partyType;

        try {
            var commandForm = PartyUtil.getHome().getGetPartyTypeForm();

            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setUuid(id);

            partyType = new GetPartyTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return partyType == null ? null : new PartyTypeObject(partyType);
    }

    @GraphQLField
    @GraphQLName("partyTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<PartyTypeObject> partyTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<PartyTypeObject> data;

        try {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countPartyTypes();

            try(var objectLimiter = new ObjectLimiter(env, PartyTypeConstants.COMPONENT_VENDOR_NAME, PartyTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = PartyUtil.getHome().getGetPartyTypesForm();
                var entities = new GetPartyTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var partyTypes = entities.stream().map(PartyTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, partyTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("partyAliasType")
    static PartyAliasTypeObject partyAliasType(final DataFetchingEnvironment env,
            @GraphQLName("partyTypeName") final String partyTypeName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyAliasType partyAliasType;

        try {
            var commandForm = PartyUtil.getHome().getGetPartyAliasTypeForm();

            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setUuid(id);

            partyAliasType = new GetPartyAliasTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return partyAliasType == null ? null : new PartyAliasTypeObject(partyAliasType);
    }

    @GraphQLField
    @GraphQLName("partyAliasTypes")
    static Collection<PartyAliasTypeObject> partyAliasTypes(final DataFetchingEnvironment env,
            @GraphQLName("partyTypeName") @GraphQLNonNull final String partyTypeName) {
        Collection<PartyAliasType> partyAliasTypes;
        Collection<PartyAliasTypeObject> partyAliasTypeObjects;

        try {
            var commandForm = PartyUtil.getHome().getGetPartyAliasTypesForm();

            commandForm.setPartyTypeName(partyTypeName);

            partyAliasTypes = new GetPartyAliasTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(partyAliasTypes == null) {
            partyAliasTypeObjects = emptyList();
        } else {
            partyAliasTypeObjects = new ArrayList<>(partyAliasTypes.size());

            partyAliasTypes.stream()
                    .map(PartyAliasTypeObject::new)
                    .forEachOrdered(partyAliasTypeObjects::add);
        }

        return partyAliasTypeObjects;
    }

    @GraphQLField
    @GraphQLName("partyAlias")
    static PartyAliasObject partyAlias(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyTypeName") final String partyTypeName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias) {
        PartyAlias partyAlias;

        try {
            var commandForm = PartyUtil.getHome().getGetPartyAliasForm();

            commandForm.setPartyName(partyName);
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);

            partyAlias = new GetPartyAliasCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return partyAlias == null ? null : new PartyAliasObject(partyAlias);
    }

    @GraphQLField
    @GraphQLName("partyAliases")
    static Collection<PartyAliasObject> partyAliases(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyTypeName") final String partyTypeName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName) {
        Collection<PartyAlias> partyAliases;
        Collection<PartyAliasObject> partyAliasObjects;

        try {
            var commandForm = PartyUtil.getHome().getGetPartyAliasesForm();

            commandForm.setPartyName(partyName);
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);

            partyAliases = new GetPartyAliasesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(partyAliases == null) {
            partyAliasObjects = emptyList();
        } else {
            partyAliasObjects = new ArrayList<>(partyAliases.size());

            partyAliases.stream()
                    .map(PartyAliasObject::new)
                    .forEachOrdered(partyAliasObjects::add);
        }

        return partyAliasObjects;
    }

    @GraphQLField
    @GraphQLName("party")
    static PartyObject party(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        Party party;

        try {
            var commandForm = PartyUtil.getHome().getGetPartyForm();

            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            party = new GetPartyCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return party == null ? null : new PartyObject(party);
    }

    @GraphQLField
    @GraphQLName("parties")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<PartyObject> parties(final DataFetchingEnvironment env) {
        CountingPaginatedData<PartyObject> data;

        try {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countParties();

            try(var objectLimiter = new ObjectLimiter(env, PartyConstants.COMPONENT_VENDOR_NAME, PartyConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = PartyUtil.getHome().getGetPartiesForm();
                var entities = new GetPartiesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var parties = entities.stream().map(PartyObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, parties);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("company")
    static CompanyObject company(final DataFetchingEnvironment env,
            @GraphQLName("companyName") final String companyName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyCompany company;

        try {
            var commandForm = PartyUtil.getHome().getGetCompanyForm();

            commandForm.setCompanyName(companyName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            company = new GetCompanyCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return company == null ? null : new CompanyObject(company);
    }

    @GraphQLField
    @GraphQLName("companies")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<CompanyObject> companies(final DataFetchingEnvironment env) {
        CountingPaginatedData<CompanyObject> data;

        try {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countPartyCompanies();

            try(var objectLimiter = new ObjectLimiter(env, PartyCompanyConstants.COMPONENT_VENDOR_NAME, PartyCompanyConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = PartyUtil.getHome().getGetCompaniesForm();
                var entities = new GetCompaniesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var companies = entities.stream().map(CompanyObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, companies);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("division")
    static DivisionObject division(final DataFetchingEnvironment env,
            @GraphQLName("companyName") final String companyName,
            @GraphQLName("divisionName") final String divisionName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyDivision division;

        try {
            var commandForm = PartyUtil.getHome().getGetDivisionForm();

            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            division = new GetDivisionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return division == null ? null : new DivisionObject(division);
    }

    @GraphQLField
    @GraphQLName("divisions")
    static Collection<DivisionObject> divisions(final DataFetchingEnvironment env,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName) {
        Collection<PartyDivision> partyDivisions;
        Collection<DivisionObject> divisionObjects;

        try {
            var commandForm = PartyUtil.getHome().getGetDivisionsForm();

            commandForm.setCompanyName(companyName);

            partyDivisions = new GetDivisionsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(partyDivisions == null) {
            divisionObjects = emptyList();
        } else {
            divisionObjects = new ArrayList<>(partyDivisions.size());

            partyDivisions.stream()
                    .map(DivisionObject::new)
                    .forEachOrdered(divisionObjects::add);
        }

        return divisionObjects;
    }

    @GraphQLField
    @GraphQLName("department")
    static DepartmentObject department(final DataFetchingEnvironment env,
            @GraphQLName("companyName") final String companyName,
            @GraphQLName("divisionName") final String divisionName,
            @GraphQLName("departmentName") final String departmentName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyDepartment department;

        try {
            var commandForm = PartyUtil.getHome().getGetDepartmentForm();

            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);
            commandForm.setDepartmentName(departmentName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            department = new GetDepartmentCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return department == null ? null : new DepartmentObject(department);
    }

    @GraphQLField
    @GraphQLName("departments")
    static Collection<DepartmentObject> departments(final DataFetchingEnvironment env,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName,
            @GraphQLName("divisionName") final String divisionName) {
        Collection<PartyDepartment> partyDepartments;
        Collection<DepartmentObject> departmentObjects;

        try {
            var commandForm = PartyUtil.getHome().getGetDepartmentsForm();

            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);

            partyDepartments = new GetDepartmentsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(partyDepartments == null) {
            departmentObjects = emptyList();
        } else {
            departmentObjects = new ArrayList<>(partyDepartments.size());

            partyDepartments.stream()
                    .map(DepartmentObject::new)
                    .forEachOrdered(departmentObjects::add);
        }

        return departmentObjects;
    }

    @GraphQLField
    @GraphQLName("warehouseType")
    static WarehouseTypeObject warehouseType(final DataFetchingEnvironment env,
            @GraphQLName("warehouseTypeName") final String warehouseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        WarehouseType warehouseType;

        try {
            var commandForm = WarehouseUtil.getHome().getGetWarehouseTypeForm();

            commandForm.setWarehouseTypeName(warehouseTypeName);
            commandForm.setUuid(id);

            warehouseType = new GetWarehouseTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return warehouseType == null ? null : new WarehouseTypeObject(warehouseType);
    }

    @GraphQLField
    @GraphQLName("warehouseTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WarehouseTypeObject> warehouseTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<WarehouseTypeObject> data;

        try {
            var commandForm = WarehouseUtil.getHome().getGetWarehouseTypesForm();
            var command = new GetWarehouseTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WarehouseTypeConstants.COMPONENT_VENDOR_NAME, WarehouseTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var warehouseTypes = entities.stream()
                            .map(WarehouseTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, warehouseTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("warehouse")
    static WarehouseObject warehouse(final DataFetchingEnvironment env,
            @GraphQLName("warehouseName") final String warehouseName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        Warehouse warehouse;

        try {
            var commandForm = WarehouseUtil.getHome().getGetWarehouseForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.setPartyName(partyName);
            commandForm.setUuid(id);

            warehouse = new GetWarehouseCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return warehouse == null ? null : new WarehouseObject(warehouse);
    }

    @GraphQLField
    @GraphQLName("warehouses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WarehouseObject> warehouses(final DataFetchingEnvironment env) {
        CountingPaginatedData<WarehouseObject> data;

        try {
            var commandForm = WarehouseUtil.getHome().getGetWarehousesForm();
            var command = new GetWarehousesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WarehouseConstants.COMPONENT_VENDOR_NAME, WarehouseConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var warehouses = entities.stream()
                            .map(WarehouseObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, warehouses);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("locationUseType")
    static LocationUseTypeObject locationUseType(final DataFetchingEnvironment env,
            @GraphQLName("locationUseTypeName") final String locationUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        LocationUseType locationUseType;

        try {
            var commandForm = WarehouseUtil.getHome().getGetLocationUseTypeForm();

            commandForm.setLocationUseTypeName(locationUseTypeName);
            commandForm.setUuid(id);

            locationUseType = new GetLocationUseTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return locationUseType == null ? null : new LocationUseTypeObject(locationUseType);
    }

    @GraphQLField
    @GraphQLName("locationUseTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<LocationUseTypeObject> locationUseTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<LocationUseTypeObject> data;

        try {
            var commandForm = WarehouseUtil.getHome().getGetLocationUseTypesForm();
            var command = new GetLocationUseTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, LocationUseTypeConstants.COMPONENT_VENDOR_NAME, LocationUseTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var locationUseTypes = entities.stream()
                            .map(LocationUseTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, locationUseTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("cancellationKind")
    static CancellationKindObject cancellationKind(final DataFetchingEnvironment env,
            @GraphQLName("cancellationKindName") final String cancellationKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        CancellationKind cancellationKind;

        try {
            var commandForm = CancellationPolicyUtil.getHome().getGetCancellationKindForm();

            commandForm.setCancellationKindName(cancellationKindName);
            commandForm.setUuid(id);

            cancellationKind = new GetCancellationKindCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return cancellationKind == null ? null : new CancellationKindObject(cancellationKind);
    }

    @GraphQLField
    @GraphQLName("cancellationKinds")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<CancellationKindObject> cancellationKinds(final DataFetchingEnvironment env) {
        CountingPaginatedData<CancellationKindObject> data;

        try {
            var commandForm = CancellationPolicyUtil.getHome().getGetCancellationKindsForm();
            var command = new GetCancellationKindsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, CancellationKindConstants.COMPONENT_VENDOR_NAME, CancellationKindConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var cancellationKinds = entities.stream()
                            .map(CancellationKindObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, cancellationKinds);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("cancellationPolicy")
    static CancellationPolicyObject cancellationPolicy(final DataFetchingEnvironment env,
            @GraphQLName("cancellationKindName") final String cancellationKindName,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("id") @GraphQLID final String id) {
        CancellationPolicy cancellationPolicy;

        try {
            var commandForm = CancellationPolicyUtil.getHome().getGetCancellationPolicyForm();

            commandForm.setCancellationKindName(cancellationKindName);
            commandForm.setCancellationPolicyName(cancellationPolicyName);
            commandForm.setUuid(id);

            cancellationPolicy = new GetCancellationPolicyCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return cancellationPolicy == null ? null : new CancellationPolicyObject(cancellationPolicy);
    }

    @GraphQLField
    @GraphQLName("cancellationPolicies")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<CancellationPolicyObject> cancellationPolicies(final DataFetchingEnvironment env,
            @GraphQLName("cancellationKindName") final String cancellationKindName) {
        CountingPaginatedData<CancellationPolicyObject> data;

        try {
            var commandForm = CancellationPolicyUtil.getHome().getGetCancellationPoliciesForm();
            var command = new GetCancellationPoliciesCommand();

            commandForm.setCancellationKindName(cancellationKindName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, CancellationPolicyConstants.COMPONENT_VENDOR_NAME, CancellationPolicyConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var cancellationPolicies = entities.stream()
                            .map(CancellationPolicyObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, cancellationPolicies);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("returnKind")
    static ReturnKindObject returnKind(final DataFetchingEnvironment env,
            @GraphQLName("returnKindName") final String returnKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        ReturnKind returnKind;

        try {
            var commandForm = ReturnPolicyUtil.getHome().getGetReturnKindForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.setUuid(id);

            returnKind = new GetReturnKindCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return returnKind == null ? null : new ReturnKindObject(returnKind);
    }

    @GraphQLField
    @GraphQLName("returnKinds")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ReturnKindObject> returnKinds(final DataFetchingEnvironment env) {
        CountingPaginatedData<ReturnKindObject> data;

        try {
            var commandForm = ReturnPolicyUtil.getHome().getGetReturnKindsForm();
            var command = new GetReturnKindsCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ReturnKindConstants.COMPONENT_VENDOR_NAME, ReturnKindConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var returnKinds = entities.stream()
                            .map(ReturnKindObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, returnKinds);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("returnPolicy")
    static ReturnPolicyObject returnPolicy(final DataFetchingEnvironment env,
            @GraphQLName("returnKindName") final String returnKindName,
            @GraphQLName("returnPolicyName") final String returnPolicyName,
            @GraphQLName("id") @GraphQLID final String id) {
        ReturnPolicy returnPolicy;

        try {
            var commandForm = ReturnPolicyUtil.getHome().getGetReturnPolicyForm();

            commandForm.setReturnKindName(returnKindName);
            commandForm.setReturnPolicyName(returnPolicyName);
            commandForm.setUuid(id);

            returnPolicy = new GetReturnPolicyCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return returnPolicy == null ? null : new ReturnPolicyObject(returnPolicy);
    }

    @GraphQLField
    @GraphQLName("returnPolicies")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ReturnPolicyObject> returnPolicies(final DataFetchingEnvironment env,
            @GraphQLName("returnKindName") final String returnKindName) {
        CountingPaginatedData<ReturnPolicyObject> data;

        try {
            var commandForm = ReturnPolicyUtil.getHome().getGetReturnPoliciesForm();
            var command = new GetReturnPoliciesCommand();

            commandForm.setReturnKindName(returnKindName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ReturnPolicyConstants.COMPONENT_VENDOR_NAME, ReturnPolicyConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var returnPolicies = entities.stream()
                            .map(ReturnPolicyObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, returnPolicies);
                }
            }
        } catch(NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("item")
    static ItemObject item(final DataFetchingEnvironment env,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemNameOrAlias") final String itemNameOrAlias,
            @GraphQLName("id") @GraphQLID final String id) {
        Item item;

        try {
            var commandForm = ItemUtil.getHome().getGetItemForm();

            commandForm.setItemName(itemName);
            commandForm.setItemNameOrAlias(itemNameOrAlias);
            commandForm.setUuid(id);

            item = new GetItemCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return item == null ? null : new ItemObject(item);
    }

    @GraphQLField
    @GraphQLName("items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemObject> items(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItems();

            try(var objectLimiter = new ObjectLimiter(env, ItemConstants.COMPONENT_VENDOR_NAME, ItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemsForm();
                var entities = new GetItemsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var items = entities.stream().map(ItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, items);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemUnitOfMeasureType")
    static ItemUnitOfMeasureTypeObject itemUnitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        ItemUnitOfMeasureType itemUnitOfMeasureType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemUnitOfMeasureTypeForm();

            commandForm.setItemName(itemName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

            itemUnitOfMeasureType = new GetItemUnitOfMeasureTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemUnitOfMeasureType == null ? null : new ItemUnitOfMeasureTypeObject(itemUnitOfMeasureType);
    }

    @GraphQLField
    @GraphQLName("itemUnitOfMeasureTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemUnitOfMeasureTypeObject> itemUnitOfMeasureTypes(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        CountingPaginatedData<ItemUnitOfMeasureTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemUnitOfMeasureTypesForm();
            var command = new GetItemUnitOfMeasureTypesCommand();

            commandForm.setItemName(itemName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemUnitOfMeasureTypeConstants.COMPONENT_VENDOR_NAME, ItemUnitOfMeasureTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemUnitOfMeasureTypeObjects = entities.stream()
                            .map(ItemUnitOfMeasureTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemUnitOfMeasureTypeObjects);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }


    @GraphQLField
    @GraphQLName("itemDescription")
    static ItemDescriptionObject itemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("referrer") final String referrer) {
        ItemDescription itemDescription;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setItemName(itemName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setUuid(id);
            commandForm.setReferrer(referrer);

            itemDescription = new GetItemDescriptionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDescription == null ? null : new ItemDescriptionObject(itemDescription);
    }

    @GraphQLField
    @GraphQLName("itemDescriptions")
    static Collection<ItemDescriptionObject> itemDescriptions(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("languageIsoName") final String languageIsoName) {
        Collection<ItemDescription> itemDescriptions;
        Collection<ItemDescriptionObject> itemDescriptionObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionsForm();

            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setLanguageIsoName(languageIsoName);

            itemDescriptions = new GetItemDescriptionsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(itemDescriptions == null) {
            itemDescriptionObjects = emptyList();
        } else {
            itemDescriptionObjects = new ArrayList<>(itemDescriptions.size());

            itemDescriptions.stream()
                    .map(ItemDescriptionObject::new)
                    .forEachOrdered(itemDescriptionObjects::add);
        }

        return itemDescriptionObjects;
    }

    @GraphQLField
    @GraphQLName("itemType")
    static ItemTypeObject itemType(final DataFetchingEnvironment env,
            @GraphQLName("itemTypeName") final String itemTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemType itemType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemTypeForm();

            commandForm.setItemTypeName(itemTypeName);
            commandForm.setUuid(id);

            itemType = new GetItemTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemType == null ? null : new ItemTypeObject(itemType);
    }

    @GraphQLField
    @GraphQLName("itemTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemTypeObject> itemTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemTypesForm();
            var command = new GetItemTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemTypeConstants.COMPONENT_VENDOR_NAME, ItemTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemTypes = entities.stream()
                            .map(ItemTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }


    @GraphQLField
    @GraphQLName("itemUseType")
    static ItemUseTypeObject itemUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemUseTypeName") final String itemUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemUseType itemUseType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemUseTypeForm();

            commandForm.setItemUseTypeName(itemUseTypeName);
            commandForm.setUuid(id);

            itemUseType = new GetItemUseTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemUseType == null ? null : new ItemUseTypeObject(itemUseType);
    }

    @GraphQLField
    @GraphQLName("itemUseTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemUseTypeObject> itemUseTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemUseTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemUseTypesForm();
            var command = new GetItemUseTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemUseTypeConstants.COMPONENT_VENDOR_NAME, ItemUseTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemUseTypes = entities.stream()
                            .map(ItemUseTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemUseTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemPriceType")
    static ItemPriceTypeObject itemPriceType(final DataFetchingEnvironment env,
            @GraphQLName("itemPriceTypeName") final String itemPriceTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemPriceType itemPriceType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemPriceTypeForm();

            commandForm.setItemPriceTypeName(itemPriceTypeName);
            commandForm.setUuid(id);

            itemPriceType = new GetItemPriceTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemPriceType == null ? null : new ItemPriceTypeObject(itemPriceType);
    }

    @GraphQLField
    @GraphQLName("itemPriceTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemPriceTypeObject> itemPriceTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemPriceTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemPriceTypesForm();
            var command = new GetItemPriceTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemPriceTypeConstants.COMPONENT_VENDOR_NAME, ItemPriceTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemPriceTypes = entities.stream()
                            .map(ItemPriceTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemPriceTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemDeliveryType")
    static ItemDeliveryTypeObject itemDeliveryType(final DataFetchingEnvironment env,
            @GraphQLName("itemDeliveryTypeName") final String itemDeliveryTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemDeliveryType itemDeliveryType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDeliveryTypeForm();

            commandForm.setItemDeliveryTypeName(itemDeliveryTypeName);
            commandForm.setUuid(id);

            itemDeliveryType = new GetItemDeliveryTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDeliveryType == null ? null : new ItemDeliveryTypeObject(itemDeliveryType);
    }

    @GraphQLField
    @GraphQLName("itemDeliveryTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemDeliveryTypeObject> itemDeliveryTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemDeliveryTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDeliveryTypesForm();
            var command = new GetItemDeliveryTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemDeliveryTypeConstants.COMPONENT_VENDOR_NAME, ItemDeliveryTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemDeliveryTypes = entities.stream()
                            .map(ItemDeliveryTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemDeliveryTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemInventoryType")
    static ItemInventoryTypeObject itemInventoryType(final DataFetchingEnvironment env,
            @GraphQLName("itemInventoryTypeName") final String itemInventoryTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemInventoryType itemInventoryType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemInventoryTypeForm();

            commandForm.setItemInventoryTypeName(itemInventoryTypeName);
            commandForm.setUuid(id);

            itemInventoryType = new GetItemInventoryTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemInventoryType == null ? null : new ItemInventoryTypeObject(itemInventoryType);
    }

    @GraphQLField
    @GraphQLName("itemInventoryTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemInventoryTypeObject> itemInventoryTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemInventoryTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemInventoryTypesForm();
            var command = new GetItemInventoryTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemInventoryTypeConstants.COMPONENT_VENDOR_NAME, ItemInventoryTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemInventoryTypes = entities.stream()
                            .map(ItemInventoryTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemInventoryTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemPrice")
    static ItemPriceObject itemPrice(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        ItemPrice itemPrice;

        try {
            var commandForm = ItemUtil.getHome().getGetItemPriceForm();

            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            itemPrice = new GetItemPriceCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemPrice == null ? null : new ItemPriceObject(itemPrice);
    }

    @GraphQLField
    @GraphQLName("itemPrices")
    static Collection<ItemPriceObject> itemPrices(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        Collection<ItemPrice> itemPrice;
        Collection<ItemPriceObject> itemPriceObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemPricesForm();

            commandForm.setItemName(itemName);

            itemPrice = new GetItemPricesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(itemPrice == null) {
            itemPriceObjects = emptyList();
        } else {
            itemPriceObjects = new ArrayList<>(itemPrice.size());

            itemPrice.stream()
                    .map(ItemPriceObject::new)
                    .forEachOrdered(itemPriceObjects::add);
        }

        return itemPriceObjects;
    }

    @GraphQLField
    @GraphQLName("itemAliasChecksumType")
    static ItemAliasChecksumTypeObject itemAliasChecksumType(final DataFetchingEnvironment env,
            @GraphQLName("itemAliasChecksumTypeName") final String itemAliasChecksumTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemAliasChecksumType itemAliasChecksumType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemAliasChecksumTypeForm();

            commandForm.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
            commandForm.setUuid(id);

            itemAliasChecksumType = new GetItemAliasChecksumTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemAliasChecksumType == null ? null : new ItemAliasChecksumTypeObject(itemAliasChecksumType);
    }

    @GraphQLField
    @GraphQLName("itemAliasChecksumTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemAliasChecksumTypeObject> itemAliasChecksumTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemAliasChecksumTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemAliasChecksumTypesForm();
            var command = new GetItemAliasChecksumTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemAliasChecksumTypeConstants.COMPONENT_VENDOR_NAME, ItemAliasChecksumTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemAliasChecksumTypes = entities.stream()
                            .map(ItemAliasChecksumTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemAliasChecksumTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemAlias")
    static ItemAliasObject itemAlias(final DataFetchingEnvironment env,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        ItemAlias itemAlias;

        try {
            var commandForm = ItemUtil.getHome().getGetItemAliasForm();

            commandForm.setAlias(alias);

            itemAlias = new GetItemAliasCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemAlias == null ? null : new ItemAliasObject(itemAlias);
    }

    @GraphQLField
    @GraphQLName("itemAliases")
    static Collection<ItemAliasObject> itemAliases(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        Collection<ItemAlias> itemAlias;
        Collection<ItemAliasObject> itemAliasObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemAliasesForm();

            commandForm.setItemName(itemName);

            itemAlias = new GetItemAliasesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(itemAlias == null) {
            itemAliasObjects = emptyList();
        } else {
            itemAliasObjects = new ArrayList<>(itemAlias.size());

            itemAlias.stream()
                    .map(ItemAliasObject::new)
                    .forEachOrdered(itemAliasObjects::add);
        }

        return itemAliasObjects;
    }

    @GraphQLField
    @GraphQLName("itemCategory")
    static ItemCategoryObject itemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemCategory itemCategory;

        try {
            var commandForm = ItemUtil.getHome().getGetItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUuid(id);

            itemCategory = new GetItemCategoryCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemCategory == null ? null : new ItemCategoryObject(itemCategory);
    }

    @GraphQLField
    @GraphQLName("itemCategories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemCategoryObject> itemCategories(final DataFetchingEnvironment env,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName) {
        CountingPaginatedData<ItemCategoryObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemCategoriesForm();
            var command = new GetItemCategoriesCommand();

            commandForm.setParentItemCategoryName(parentItemCategoryName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemCategoryConstants.COMPONENT_VENDOR_NAME, ItemCategoryConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemCategories = entities.stream()
                            .map(ItemCategoryObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemCategories);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemAccountingCategory")
    static ItemAccountingCategoryObject itemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemAccountingCategory itemAccountingCategory;

        try {
            var commandForm = AccountingUtil.getHome().getGetItemAccountingCategoryForm();

            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setUuid(id);

            itemAccountingCategory = new GetItemAccountingCategoryCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemAccountingCategory == null ? null : new ItemAccountingCategoryObject(itemAccountingCategory);
    }

    @GraphQLField
    @GraphQLName("itemAccountingCategories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemAccountingCategoryObject> itemAccountingCategories(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemAccountingCategoryObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetItemAccountingCategoriesForm();
            var command = new GetItemAccountingCategoriesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemAccountingCategoryConstants.COMPONENT_VENDOR_NAME, ItemAccountingCategoryConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemAccountingCategories = entities.stream()
                            .map(ItemAccountingCategoryObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemAccountingCategories);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("glAccount")
    static GlAccountObject glAccount(final DataFetchingEnvironment env,
            @GraphQLName("glAccountName") final String glAccountName,
            @GraphQLName("id") @GraphQLID final String id) {
        GlAccount glAccount;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountForm();

            commandForm.setGlAccountName(glAccountName);
            commandForm.setUuid(id);

            glAccount = new GetGlAccountCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return glAccount == null ? null : new GlAccountObject(glAccount);
    }

    @GraphQLField
    @GraphQLName("glAccounts")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<GlAccountObject> glAccounts(final DataFetchingEnvironment env,
            @GraphQLName("glAccountCategoryName") final String glAccountCategoryName) {
        CountingPaginatedData<GlAccountObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountsForm();
            var command = new GetGlAccountsCommand();

            commandForm.setGlAccountCategoryName(glAccountCategoryName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, GlAccountConstants.COMPONENT_VENDOR_NAME, GlAccountConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var glAccounts = entities.stream()
                            .map(GlAccountObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, glAccounts);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("glAccountType")
    static GlAccountTypeObject glAccountType(final DataFetchingEnvironment env,
            @GraphQLName("glAccountTypeName") final String glAccountTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        GlAccountType glAccountType;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountTypeForm();

            commandForm.setGlAccountTypeName(glAccountTypeName);
            commandForm.setUuid(id);

            glAccountType = new GetGlAccountTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return glAccountType == null ? null : new GlAccountTypeObject(glAccountType);
    }

    @GraphQLField
    @GraphQLName("glAccountTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<GlAccountTypeObject> glAccountTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<GlAccountTypeObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountTypesForm();
            var command = new GetGlAccountTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, GlAccountTypeConstants.COMPONENT_VENDOR_NAME, GlAccountTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var glAccountTypes = entities.stream()
                            .map(GlAccountTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, glAccountTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("glAccountClass")
    static GlAccountClassObject glAccountClass(final DataFetchingEnvironment env,
            @GraphQLName("glAccountClassName") final String glAccountClassName,
            @GraphQLName("id") @GraphQLID final String id) {
        GlAccountClass glAccountClass;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountClassForm();

            commandForm.setGlAccountClassName(glAccountClassName);
            commandForm.setUuid(id);

            glAccountClass = new GetGlAccountClassCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return glAccountClass == null ? null : new GlAccountClassObject(glAccountClass);
    }

    @GraphQLField
    @GraphQLName("glAccountClasses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<GlAccountClassObject> glAccountClasses(final DataFetchingEnvironment env) {
        CountingPaginatedData<GlAccountClassObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountClassesForm();
            var command = new GetGlAccountClassesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, GlAccountClassConstants.COMPONENT_VENDOR_NAME, GlAccountClassConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var glAccountClasses = entities.stream()
                            .map(GlAccountClassObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, glAccountClasses);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("glAccountCategory")
    static GlAccountCategoryObject glAccountCategory(final DataFetchingEnvironment env,
            @GraphQLName("glAccountCategoryName") final String glAccountCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        GlAccountCategory glAccountCategory;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountCategoryForm();

            commandForm.setGlAccountCategoryName(glAccountCategoryName);
            commandForm.setUuid(id);

            glAccountCategory = new GetGlAccountCategoryCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return glAccountCategory == null ? null : new GlAccountCategoryObject(glAccountCategory);
    }

    @GraphQLField
    @GraphQLName("glAccountCategories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<GlAccountCategoryObject> glAccountCategories(final DataFetchingEnvironment env) {
        CountingPaginatedData<GlAccountCategoryObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlAccountCategoriesForm();
            var command = new GetGlAccountCategoriesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, GlAccountCategoryConstants.COMPONENT_VENDOR_NAME, GlAccountCategoryConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var glAccountCategories = entities.stream()
                            .map(GlAccountCategoryObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, glAccountCategories);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("glResourceType")
    static GlResourceTypeObject glResourceType(final DataFetchingEnvironment env,
            @GraphQLName("glResourceTypeName") final String glResourceTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        GlResourceType glResourceType;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlResourceTypeForm();

            commandForm.setGlResourceTypeName(glResourceTypeName);
            commandForm.setUuid(id);

            glResourceType = new GetGlResourceTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return glResourceType == null ? null : new GlResourceTypeObject(glResourceType);
    }

    @GraphQLField
    @GraphQLName("glResourceTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<GlResourceTypeObject> glResourceTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<GlResourceTypeObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetGlResourceTypesForm();
            var command = new GetGlResourceTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, GlResourceTypeConstants.COMPONENT_VENDOR_NAME, GlResourceTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var glResourceTypes = entities.stream()
                            .map(GlResourceTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, glResourceTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemPurchasingCategory")
    static ItemPurchasingCategoryObject itemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemPurchasingCategory itemPurchasingCategory;

        try {
            var commandForm = VendorUtil.getHome().getGetItemPurchasingCategoryForm();

            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setUuid(id);

            itemPurchasingCategory = new GetItemPurchasingCategoryCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemPurchasingCategory == null ? null : new ItemPurchasingCategoryObject(itemPurchasingCategory);
    }

    @GraphQLField
    @GraphQLName("itemPurchasingCategories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemPurchasingCategoryObject> itemPurchasingCategories(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemPurchasingCategoryObject> data;

        try {
            var commandForm = VendorUtil.getHome().getGetItemPurchasingCategoriesForm();
            var command = new GetItemPurchasingCategoriesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemPurchasingCategoryConstants.COMPONENT_VENDOR_NAME, ItemPurchasingCategoryConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemPurchasingCategories = entities.stream()
                            .map(ItemPurchasingCategoryObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemPurchasingCategories);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemDescriptionType")
    static ItemDescriptionTypeObject itemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemDescriptionType itemDescriptionType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setUuid(id);

            itemDescriptionType = new GetItemDescriptionTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDescriptionType == null ? null : new ItemDescriptionTypeObject(itemDescriptionType);
    }

    @GraphQLField
    @GraphQLName("itemDescriptionTypes")
    static Collection<ItemDescriptionTypeObject> itemDescriptionTypes(final DataFetchingEnvironment env,
            @GraphQLName("parentItemDescriptionTypeName") final String parentItemDescriptionTypeName) {
        Collection<ItemDescriptionType> itemDescriptionTypes;
        Collection<ItemDescriptionTypeObject> itemDescriptionTypeObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionTypesForm();

            commandForm.setParentItemDescriptionTypeName(parentItemDescriptionTypeName);

            itemDescriptionTypes = new GetItemDescriptionTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(itemDescriptionTypes == null) {
            itemDescriptionTypeObjects = emptyList();
        } else {
            itemDescriptionTypeObjects = new ArrayList<>(itemDescriptionTypes.size());

            itemDescriptionTypes.stream()
                    .map(ItemDescriptionTypeObject::new)
                    .forEachOrdered(itemDescriptionTypeObjects::add);
        }

        return itemDescriptionTypeObjects;
    }

    @GraphQLField
    @GraphQLName("itemImageType")
    static ItemImageTypeObject itemImageType(final DataFetchingEnvironment env,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemImageType itemImageType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemImageTypeForm();

            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setUuid(id);

            itemImageType = new GetItemImageTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemImageType == null ? null : new ItemImageTypeObject(itemImageType);
    }

    @GraphQLField
    @GraphQLName("itemImageTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemImageTypeObject> itemImageTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemImageTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemImageTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemImageTypeConstants.COMPONENT_VENDOR_NAME, ItemImageTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemImageTypesForm();
                var entities = new GetItemImageTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemImageTypes = entities.stream().map(ItemImageTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemImageTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemDescriptionTypeUseType")
    static ItemDescriptionTypeUseTypeObject itemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemDescriptionTypeUseType itemDescriptionTypeUseType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeUseTypeForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setUuid(id);

            itemDescriptionTypeUseType = new GetItemDescriptionTypeUseTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDescriptionTypeUseType == null ? null : new ItemDescriptionTypeUseTypeObject(itemDescriptionTypeUseType);
    }

    @GraphQLField
    @GraphQLName("itemDescriptionTypeUseTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemDescriptionTypeUseTypeObject> itemDescriptionTypeUseTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemDescriptionTypeUseTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemDescriptionTypeUseTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemDescriptionTypeUseTypeConstants.COMPONENT_VENDOR_NAME, ItemDescriptionTypeUseTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeUseTypesForm();
                var entities = new GetItemDescriptionTypeUseTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemDescriptionTypeUseTypes = entities.stream().map(ItemDescriptionTypeUseTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemDescriptionTypeUseTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemDescriptionTypeUse")
    static ItemDescriptionTypeUseObject itemDescriptionTypeUse(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName) {
        ItemDescriptionTypeUse itemDescriptionTypeUse;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeUseForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            itemDescriptionTypeUse = new GetItemDescriptionTypeUseCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDescriptionTypeUse == null ? null : new ItemDescriptionTypeUseObject(itemDescriptionTypeUse);
    }

    @GraphQLField
    @GraphQLName("itemDescriptionTypeUses")
    static Collection<ItemDescriptionTypeUseObject> itemDescriptionTypeUses(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName) {
        Collection<ItemDescriptionTypeUse> itemDescriptionTypeUse;
        Collection<ItemDescriptionTypeUseObject> itemDescriptionTypeUseObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeUsesForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            itemDescriptionTypeUse = new GetItemDescriptionTypeUsesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(itemDescriptionTypeUse == null) {
            itemDescriptionTypeUseObjects = emptyList();
        } else {
            itemDescriptionTypeUseObjects = new ArrayList<>(itemDescriptionTypeUse.size());

            itemDescriptionTypeUse.stream()
                    .map(ItemDescriptionTypeUseObject::new)
                    .forEachOrdered(itemDescriptionTypeUseObjects::add);
        }

        return itemDescriptionTypeUseObjects;
    }

    @GraphQLField
    @GraphQLName("itemAliasType")
    static ItemAliasTypeObject itemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemAliasType itemAliasType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemAliasTypeForm();

            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setUuid(id);

            itemAliasType = new GetItemAliasTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemAliasType == null ? null : new ItemAliasTypeObject(itemAliasType);
    }

    @GraphQLField
    @GraphQLName("itemAliasTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemAliasTypeObject> itemAliasTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemAliasTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetItemAliasTypesForm();
            var command = new GetItemAliasTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, ItemAliasTypeConstants.COMPONENT_VENDOR_NAME, ItemAliasTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var itemAliasTypes = entities.stream()
                            .map(ItemAliasTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemAliasTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemWeightType")
    static ItemWeightTypeObject itemWeightType(final DataFetchingEnvironment env,
            @GraphQLName("itemWeightTypeName") final String itemWeightTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemWeightType itemWeightType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemWeightTypeForm();

            commandForm.setItemWeightTypeName(itemWeightTypeName);
            commandForm.setUuid(id);

            itemWeightType = new GetItemWeightTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemWeightType == null ? null : new ItemWeightTypeObject(itemWeightType);
    }

    @GraphQLField
    @GraphQLName("itemWeightTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemWeightTypeObject> itemWeightTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemWeightTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemWeightTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemWeightTypeConstants.COMPONENT_VENDOR_NAME, ItemWeightTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemWeightTypesForm();
                var entities = new GetItemWeightTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemWeightTypes = entities.stream().map(ItemWeightTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemWeightTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("itemVolumeType")
    static ItemVolumeTypeObject itemVolumeType(final DataFetchingEnvironment env,
            @GraphQLName("itemVolumeTypeName") final String itemVolumeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemVolumeType itemVolumeType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemVolumeTypeForm();

            commandForm.setItemVolumeTypeName(itemVolumeTypeName);
            commandForm.setUuid(id);

            itemVolumeType = new GetItemVolumeTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemVolumeType == null ? null : new ItemVolumeTypeObject(itemVolumeType);
    }

    @GraphQLField
    @GraphQLName("itemVolumeTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ItemVolumeTypeObject> itemVolumeTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemVolumeTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemVolumeTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemVolumeTypeConstants.COMPONENT_VENDOR_NAME, ItemVolumeTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemVolumeTypesForm();
                var entities = new GetItemVolumeTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemVolumeTypes = entities.stream().map(ItemVolumeTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, itemVolumeTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("relatedItemType")
    static RelatedItemTypeObject relatedItemType(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") final String relatedItemTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        RelatedItemType relatedItemType;

        try {
            var commandForm = ItemUtil.getHome().getGetRelatedItemTypeForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setUuid(id);

            relatedItemType = new GetRelatedItemTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return relatedItemType == null ? null : new RelatedItemTypeObject(relatedItemType, null);
    }

    @GraphQLField
    @GraphQLName("relatedItemTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<RelatedItemTypeObject> relatedItemTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<RelatedItemTypeObject> data;

        try {
            var commandForm = ItemUtil.getHome().getGetRelatedItemTypesForm();
            var command = new GetRelatedItemTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, RelatedItemTypeConstants.COMPONENT_VENDOR_NAME, RelatedItemTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var relatedItemTypes = entities.stream()
                            .map((RelatedItemType relatedItemType) -> new RelatedItemTypeObject(relatedItemType, null))
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, relatedItemTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("relatedItem")
    static RelatedItemObject relatedItem(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") @GraphQLNonNull final String relatedItemTypeName,
            @GraphQLName("fromItemName") @GraphQLNonNull final String fromItemName,
            @GraphQLName("toItemName") @GraphQLNonNull final String toItemName) {
        RelatedItem relatedItem;

        try {
            var commandForm = ItemUtil.getHome().getGetRelatedItemForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setFromItemName(fromItemName);
            commandForm.setToItemName(toItemName);

            relatedItem = new GetRelatedItemCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return relatedItem == null ? null : new RelatedItemObject(relatedItem);
    }

    @GraphQLField
    @GraphQLName("relatedItems")
    static Collection<RelatedItemObject> relatedItems(final DataFetchingEnvironment env,
            @GraphQLName("relatedItemTypeName") final String relatedItemTypeName,
            @GraphQLName("fromItemName") final String fromItemName,
            @GraphQLName("toItemName") final String toItemName) {
        Collection<RelatedItem> relatedItems;
        Collection<RelatedItemObject> relatedItemObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetRelatedItemsForm();

            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setFromItemName(fromItemName);
            commandForm.setToItemName(toItemName);

            relatedItems = new GetRelatedItemsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(relatedItems == null) {
            relatedItemObjects = emptyList();
        } else {
            relatedItemObjects = new ArrayList<>(relatedItems.size());

            relatedItems.stream()
                    .map(RelatedItemObject::new)
                    .forEachOrdered(relatedItemObjects::add);
        }

        return relatedItemObjects;
    }

    @GraphQLField
    @GraphQLName("orderType")
    static OrderTypeObject orderType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        OrderType orderType;

        try {
            var commandForm = OrderUtil.getHome().getGetOrderTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setUuid(id);

            orderType = new GetOrderTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return orderType == null ? null : new OrderTypeObject(orderType);
    }

    @GraphQLField
    @GraphQLName("orderTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<OrderTypeObject> orderTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<OrderTypeObject> data;

        try {
            var commandForm = OrderUtil.getHome().getGetOrderTypesForm();
            var command = new GetOrderTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, OrderTypeConstants.COMPONENT_VENDOR_NAME, OrderTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var orderTypes = entities.stream()
                            .map(OrderTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, orderTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("orderPriority")
    static OrderPriorityObject orderPriority(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderPriorityName") final String orderPriorityName,
            @GraphQLName("id") @GraphQLID final String id) {
        OrderPriority orderPriority;

        try {
            var commandForm = OrderUtil.getHome().getGetOrderPriorityForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderPriorityName(orderPriorityName);
            commandForm.setUuid(id);

            orderPriority = new GetOrderPriorityCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return orderPriority == null ? null : new OrderPriorityObject(orderPriority);
    }

    @GraphQLField
    @GraphQLName("orderPriorities")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<OrderPriorityObject> orderPriorities(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName) {
        CountingPaginatedData<OrderPriorityObject> data;

        try {
            var commandForm = OrderUtil.getHome().getGetOrderPrioritiesForm();
            var command = new GetOrderPrioritiesCommand();

            commandForm.setOrderTypeName(orderTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, OrderPriorityConstants.COMPONENT_VENDOR_NAME, OrderPriorityConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var orderPriorities = entities.stream()
                            .map(OrderPriorityObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, orderPriorities);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("orderTimeType")
    static OrderTimeTypeObject orderTimeType(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName,
            @GraphQLName("orderTimeTypeName") final String orderTimeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        OrderTimeType orderTimeType;

        try {
            var commandForm = OrderUtil.getHome().getGetOrderTimeTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderTimeTypeName(orderTimeTypeName);
            commandForm.setUuid(id);

            orderTimeType = new GetOrderTimeTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return orderTimeType == null ? null : new OrderTimeTypeObject(orderTimeType);
    }

    @GraphQLField
    @GraphQLName("orderTimeTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<OrderTimeTypeObject> orderTimeTypes(final DataFetchingEnvironment env,
            @GraphQLName("orderTypeName") final String orderTypeName) {
        CountingPaginatedData<OrderTimeTypeObject> data;

        try {
            var commandForm = OrderUtil.getHome().getGetOrderTimeTypesForm();
            var command = new GetOrderTimeTypesCommand();

            commandForm.setOrderTypeName(orderTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, OrderTimeTypeConstants.COMPONENT_VENDOR_NAME, OrderTimeTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var orderTimeTypes = entities.stream()
                            .map(OrderTimeTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, orderTimeTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("wishlistType")
    static WishlistTypeObject wishlistType(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        WishlistType wishlistType;

        try {
            var commandForm = WishlistUtil.getHome().getGetWishlistTypeForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setUuid(id);

            wishlistType = new GetWishlistTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return wishlistType == null ? null : new WishlistTypeObject(wishlistType);
    }

    @GraphQLField
    @GraphQLName("wishlistTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WishlistTypeObject> wishlistTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<WishlistTypeObject> data;

        try {
            var commandForm = WishlistUtil.getHome().getGetWishlistTypesForm();
            var command = new GetWishlistTypesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WishlistTypeConstants.COMPONENT_VENDOR_NAME, WishlistTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var wishlistTypes = entities.stream()
                            .map(WishlistTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, wishlistTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("wishlistPriority")
    static WishlistPriorityObject wishlistPriority(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") final String wishlistTypeName,
            @GraphQLName("wishlistPriorityName") final String wishlistPriorityName,
            @GraphQLName("id") @GraphQLID final String id) {
        WishlistPriority wishlistPriority;

        try {
            var commandForm = WishlistUtil.getHome().getGetWishlistPriorityForm();

            commandForm.setWishlistTypeName(wishlistTypeName);
            commandForm.setWishlistPriorityName(wishlistPriorityName);
            commandForm.setUuid(id);

            wishlistPriority = new GetWishlistPriorityCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return wishlistPriority == null ? null : new WishlistPriorityObject(wishlistPriority);
    }

    @GraphQLField
    @GraphQLName("wishlistPriorities")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<WishlistPriorityObject> wishlistPriorities(final DataFetchingEnvironment env,
            @GraphQLName("wishlistTypeName") @GraphQLNonNull final String wishlistTypeName) {
        CountingPaginatedData<WishlistPriorityObject> data;

        try {
            var commandForm = WishlistUtil.getHome().getGetWishlistPrioritiesForm();
            var command = new GetWishlistPrioritiesCommand();

            commandForm.setWishlistTypeName(wishlistTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, WishlistPriorityConstants.COMPONENT_VENDOR_NAME, WishlistPriorityConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var wishlistPriorities = entities.stream()
                            .map(WishlistPriorityObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, wishlistPriorities);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("termType")
    static TermTypeObject termType(final DataFetchingEnvironment env,
            @GraphQLName("termTypeName") final String termTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        TermType termType;

        try {
            var commandForm = TermUtil.getHome().getGetTermTypeForm();

            commandForm.setTermTypeName(termTypeName);
            commandForm.setUuid(id);

            termType = new GetTermTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return termType == null ? null : new TermTypeObject(termType);
    }

    @GraphQLField
    @GraphQLName("termTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TermTypeObject> termTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<TermTypeObject> data;

        try {
            var termControl = Session.getModelController(TermControl.class);
            var totalCount = termControl.countTermTypes();

            try(var objectLimiter = new ObjectLimiter(env, TermTypeConstants.COMPONENT_VENDOR_NAME, TermTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = TermUtil.getHome().getGetTermTypesForm();
                var entities = new GetTermTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var termTypes = entities.stream().map(TermTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, termTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("term")
    static TermObject term(final DataFetchingEnvironment env,
            @GraphQLName("termName") final String termName,
            @GraphQLName("id") @GraphQLID final String id) {
        Term term;

        try {
            var commandForm = TermUtil.getHome().getGetTermForm();

            commandForm.setTermName(termName);
            commandForm.setUuid(id);

            term = new GetTermCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return term == null ? null : new TermObject(term);
    }

    @GraphQLField
    @GraphQLName("terms")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TermObject> terms(final DataFetchingEnvironment env) {
        CountingPaginatedData<TermObject> data;

        try {
            var termControl = Session.getModelController(TermControl.class);
            var totalCount = termControl.countTerms();

            try(var objectLimiter = new ObjectLimiter(env, TermConstants.COMPONENT_VENDOR_NAME, TermConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = TermUtil.getHome().getGetTermsForm();
                var entities = new GetTermsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var terms = entities.stream().map(TermObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, terms);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("securityRoleGroup")
    static SecurityRoleGroupObject securityRoleGroup(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        SecurityRoleGroup securityRoleGroup;

        try {
            var commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupForm();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setUuid(id);

            securityRoleGroup = new GetSecurityRoleGroupCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return securityRoleGroup == null ? null : new SecurityRoleGroupObject(securityRoleGroup);
    }

    @GraphQLField
    @GraphQLName("securityRoleGroups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SecurityRoleGroupObject> securityRoleGroups(final DataFetchingEnvironment env,
            @GraphQLName("parentSecurityRoleGroupName") final String parentSecurityRoleGroupName) {
        CountingPaginatedData<SecurityRoleGroupObject> data;

        try {
            var commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupsForm();
            var command = new GetSecurityRoleGroupsCommand();

            commandForm.setParentSecurityRoleGroupName(parentSecurityRoleGroupName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SecurityRoleGroupConstants.COMPONENT_VENDOR_NAME, SecurityRoleGroupConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var securityRoleGroups = entities.stream()
                            .map(SecurityRoleGroupObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, securityRoleGroups);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("securityRole")
    static SecurityRoleObject securityRole(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("securityRoleName") final String securityRoleName,
            @GraphQLName("id") @GraphQLID final String id) {
        SecurityRole securityRole;

        try {
            var commandForm = SecurityUtil.getHome().getGetSecurityRoleForm();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setSecurityRoleName(securityRoleName);
            commandForm.setUuid(id);

            securityRole = new GetSecurityRoleCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return securityRole == null ? null : new SecurityRoleObject(securityRole);
    }

    @GraphQLField
    @GraphQLName("securityRoles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<SecurityRoleObject> securityRoles(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") @GraphQLNonNull final String securityRoleGroupName) {
        CountingPaginatedData<SecurityRoleObject> data;

        try {
            var commandForm = SecurityUtil.getHome().getGetSecurityRolesForm();
            var command = new GetSecurityRolesCommand();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, SecurityRoleConstants.COMPONENT_VENDOR_NAME, SecurityRoleConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var securityRoles = entities.stream()
                            .map(SecurityRoleObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, securityRoles);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("personalTitles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<PersonalTitleObject> personalTitles(final DataFetchingEnvironment env) {
        CountingPaginatedData<PersonalTitleObject> data;

        try {
            var commandForm = PartyUtil.getHome().getGetPersonalTitlesForm();
            var command = new GetPersonalTitlesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, PersonalTitleConstants.COMPONENT_VENDOR_NAME, PersonalTitleConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var personalTitles = entities.stream()
                            .map(PersonalTitleObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, personalTitles);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("nameSuffixes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<NameSuffixObject> nameSuffixes(final DataFetchingEnvironment env) {
        CountingPaginatedData<NameSuffixObject> data;

        try {
            var commandForm = PartyUtil.getHome().getGetNameSuffixesForm();
            var command = new GetNameSuffixesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, NameSuffixConstants.COMPONENT_VENDOR_NAME, NameSuffixConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var nameSuffixes = entities.stream()
                            .map(NameSuffixObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, nameSuffixes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("tagScope")
    static TagScopeObject tagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") final String tagScopeName,
            @GraphQLName("id") @GraphQLID final String id) {
        TagScope tagScope;

        try {
            var commandForm = TagUtil.getHome().getGetTagScopeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setUuid(id);

            tagScope = new GetTagScopeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return tagScope == null ? null : new TagScopeObject(tagScope, null);
    }

    @GraphQLField
    @GraphQLName("tagScopes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TagScopeObject> tagScopes(final DataFetchingEnvironment env) {
        CountingPaginatedData<TagScopeObject> data;

        try {
            var commandForm = TagUtil.getHome().getGetTagScopesForm();
            var command = new GetTagScopesCommand();

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, TagScopeConstants.COMPONENT_VENDOR_NAME, TagScopeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var tagScopes = entities.stream()
                            .map(TagScopeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, tagScopes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("tagScopeEntityType")
    static TagScopeEntityTypeObject tagScopeEntityType(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        TagScopeEntityType tagScopeEntityType;

        try {
            var commandForm = TagUtil.getHome().getGetTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            tagScopeEntityType = new GetTagScopeEntityTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return tagScopeEntityType == null ? null : new TagScopeEntityTypeObject(tagScopeEntityType);
    }

    @GraphQLField
    @GraphQLName("tagScopeEntityTypes")
    static Collection<TagScopeEntityTypeObject> tagScopeEntityTypes(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") final String tagScopeName,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName) {
        Collection<TagScopeEntityType> tagScopeEntityTypes;
        Collection<TagScopeEntityTypeObject> tagScopeEntityTypeObjects;

        try {
            var commandForm = TagUtil.getHome().getGetTagScopeEntityTypesForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            tagScopeEntityTypes = new GetTagScopeEntityTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(tagScopeEntityTypes == null) {
            tagScopeEntityTypeObjects = emptyList();
        } else {
            tagScopeEntityTypeObjects = new ArrayList<>(tagScopeEntityTypes.size());

            tagScopeEntityTypes.stream().map(TagScopeEntityTypeObject::new).forEachOrdered(tagScopeEntityTypeObjects::add);
        }

        return tagScopeEntityTypeObjects;
    }

    @GraphQLField
    @GraphQLName("tag")
    static TagObject tag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") final String tagScopeName,
            @GraphQLName("tagName") final String tagName,
            @GraphQLName("id") @GraphQLID final String id) {
        Tag tag;

        try {
            var commandForm = TagUtil.getHome().getGetTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);
            commandForm.setUuid(id);

            tag = new GetTagCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return tag == null ? null : new TagObject(tag);
    }

    @GraphQLField
    @GraphQLName("tags")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TagObject> tags(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName) {
        CountingPaginatedData<TagObject> data;

        try {
            var commandForm = TagUtil.getHome().getGetTagsForm();
            var command = new GetTagsCommand();

            commandForm.setTagScopeName(tagScopeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, TagConstants.COMPONENT_VENDOR_NAME, TagConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var tagObjects = entities.stream()
                            .map(TagObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, tagObjects);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("entityTag")
    static EntityTagObject entityTag(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        EntityTag entityTag;

        try {
            var commandForm = TagUtil.getHome().getGetEntityTagForm();

            commandForm.setUuid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            entityTag = new GetEntityTagCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityTag == null ? null : new EntityTagObject(entityTag);
    }

    @GraphQLField
    @GraphQLName("entityTags")
    static Collection<EntityTagObject> entityTags(final DataFetchingEnvironment env,
            @GraphQLName("id") final String id,
            @GraphQLName("tagScopeName") final String tagScopeName,
            @GraphQLName("tagName") final String tagName) {
        Collection<EntityTag> entityTags;
        Collection<EntityTagObject> entityTagObjects;

        try {
            var commandForm = TagUtil.getHome().getGetEntityTagsForm();

            commandForm.setUuid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            entityTags = new GetEntityTagsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(entityTags == null) {
            entityTagObjects = emptyList();
        } else {
            entityTagObjects = new ArrayList<>(entityTags.size());

            entityTags.stream().map(EntityTagObject::new).forEachOrdered(entityTagObjects::add);
        }

        return entityTagObjects;
    }

    @GraphQLField
    @GraphQLName("geoCodeType")
    static GeoCodeTypeObject geoCodeType(final DataFetchingEnvironment env,
            @GraphQLName("geoCodeTypeName") final String geoCodeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        GeoCodeType geoCodeType;

        try {
            var commandForm = GeoUtil.getHome().getGetGeoCodeTypeForm();

            commandForm.setGeoCodeTypeName(geoCodeTypeName);
            commandForm.setUuid(id);

            geoCodeType = new GetGeoCodeTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return geoCodeType == null ? null : new GeoCodeTypeObject(geoCodeType);
    }

    @GraphQLField
    @GraphQLName("geoCodeTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<GeoCodeTypeObject> geoCodeTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<GeoCodeTypeObject> data;

        try {
            var geoControl = Session.getModelController(GeoControl.class);
            var totalCount = geoControl.countGeoCodeTypes();

            try(var objectLimiter = new ObjectLimiter(env, GeoCodeTypeConstants.COMPONENT_VENDOR_NAME, GeoCodeTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = GeoUtil.getHome().getGetGeoCodeTypesForm();
                var entities = new GetGeoCodeTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var geoCodeTypes = entities.stream().map(GeoCodeTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, geoCodeTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("geoCodeScope")
    static GeoCodeScopeObject geoCodeScope(final DataFetchingEnvironment env,
            @GraphQLName("geoCodeScopeName") final String geoCodeScopeName,
            @GraphQLName("id") @GraphQLID final String id) {
        GeoCodeScope geoCodeScope;

        try {
            var commandForm = GeoUtil.getHome().getGetGeoCodeScopeForm();

            commandForm.setGeoCodeScopeName(geoCodeScopeName);
            commandForm.setUuid(id);

            geoCodeScope = new GetGeoCodeScopeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return geoCodeScope == null ? null : new GeoCodeScopeObject(geoCodeScope);
    }

    @GraphQLField
    @GraphQLName("geoCodeScopes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<GeoCodeScopeObject> geoCodeScopes(final DataFetchingEnvironment env) {
        CountingPaginatedData<GeoCodeScopeObject> data;

        try {
            var geoControl = Session.getModelController(GeoControl.class);
            var totalCount = geoControl.countGeoCodeScopes();

            try(var objectLimiter = new ObjectLimiter(env, GeoCodeScopeConstants.COMPONENT_VENDOR_NAME, GeoCodeScopeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = GeoUtil.getHome().getGetGeoCodeScopesForm();
                var entities = new GetGeoCodeScopesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var geoCodeScopes = entities.stream().map(GeoCodeScopeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, geoCodeScopes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("geoCode")
    static GeoCodeObject geoCode(final DataFetchingEnvironment env,
            @GraphQLName("geoCodeName") final String geoCodeName,
            @GraphQLName("id") @GraphQLID final String id) {
        GeoCode geoCode;

        try {
            var commandForm = GeoUtil.getHome().getGetGeoCodeForm();

            commandForm.setGeoCodeName(geoCodeName);
            commandForm.setUuid(id);

            geoCode = new GetGeoCodeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return geoCode == null ? null : new GeoCodeObject(geoCode);
    }

    @GraphQLField
    @GraphQLName("shippingMethod")
    static ShippingMethodObject shippingMethod(final DataFetchingEnvironment env,
            @GraphQLName("shippingMethodName") final String shippingMethodName,
            @GraphQLName("id") @GraphQLID final String id) {
        ShippingMethod shippingMethod;

        try {
            var commandForm = ShippingUtil.getHome().getGetShippingMethodForm();

            commandForm.setShippingMethodName(shippingMethodName);
            commandForm.setUuid(id);

            shippingMethod = new GetShippingMethodCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return shippingMethod == null ? null : new ShippingMethodObject(shippingMethod);
    }

    @GraphQLField
    @GraphQLName("shippingMethods")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<ShippingMethodObject> shippingMethods(final DataFetchingEnvironment env) {
        CountingPaginatedData<ShippingMethodObject> data;

        try {
            var shippingControl = Session.getModelController(ShippingControl.class);
            var totalCount = shippingControl.countShippingMethods();

            try(var objectLimiter = new ObjectLimiter(env, ShippingMethodConstants.COMPONENT_VENDOR_NAME, ShippingMethodConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ShippingUtil.getHome().getGetShippingMethodsForm();
                var entities = new GetShippingMethodsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var shippingMethods = entities.stream().map(ShippingMethodObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, shippingMethods);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("transactionTimeType")
    static TransactionTimeTypeObject transactionTimeType(final DataFetchingEnvironment env,
            @GraphQLName("transactionTimeTypeName") final String transactionTimeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        TransactionTimeType transactionTimeType;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionTimeTypeForm();

            commandForm.setTransactionTimeTypeName(transactionTimeTypeName);
            commandForm.setUuid(id);

            transactionTimeType = new GetTransactionTimeTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return transactionTimeType == null ? null : new TransactionTimeTypeObject(transactionTimeType);
    }

    @GraphQLField
    @GraphQLName("transactionTimeTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TransactionTimeTypeObject> transactionTimeTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<TransactionTimeTypeObject> data;

        try {
            var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
            var totalCount = transactionTimeControl.countTransactionTimeTypes();

            try(var objectLimiter = new ObjectLimiter(env, TransactionTimeTypeConstants.COMPONENT_VENDOR_NAME, TransactionTimeTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = AccountingUtil.getHome().getGetTransactionTimeTypesForm();
                var entities = new GetTransactionTimeTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var transactionTimeTypes = entities.stream().map(TransactionTimeTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, transactionTimeTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("transactionType")
    static TransactionTypeObject transactionType(final DataFetchingEnvironment env,
            @GraphQLName("transactionTypeName") final String transactionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        TransactionType transactionType;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionTypeForm();

            commandForm.setTransactionTypeName(transactionTypeName);
            commandForm.setUuid(id);

            transactionType = new GetTransactionTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return transactionType == null ? null : new TransactionTypeObject(transactionType);
    }

    @GraphQLField
    @GraphQLName("transactionTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TransactionTypeObject> transactionTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<TransactionTypeObject> data;

        try {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countTransactionTypes();

            try(var objectLimiter = new ObjectLimiter(env, TransactionTypeConstants.COMPONENT_VENDOR_NAME, TransactionTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = AccountingUtil.getHome().getGetTransactionTypesForm();
                var entities = new GetTransactionTypesCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var transactionTypes = entities.stream().map(TransactionTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, transactionTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("transactionGlAccountCategory")
    static TransactionGlAccountCategoryObject transactionGlAccountCategory(final DataFetchingEnvironment env,
            @GraphQLName("transactionTypeName") final String transactionTypeName,
            @GraphQLName("transactionGlAccountCategoryName") final String transactionGlAccountCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        TransactionGlAccountCategory transactionGlAccountCategory;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionGlAccountCategoryForm();

            commandForm.setTransactionTypeName(transactionTypeName);
            commandForm.setTransactionGlAccountCategoryName(transactionGlAccountCategoryName);
            commandForm.setUuid(id);

            transactionGlAccountCategory = new GetTransactionGlAccountCategoryCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return transactionGlAccountCategory == null ? null : new TransactionGlAccountCategoryObject(transactionGlAccountCategory);
    }

    @GraphQLField
    @GraphQLName("transactionGlAccountCategories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TransactionGlAccountCategoryObject> transactionGlAccountCategories(final DataFetchingEnvironment env,
            @GraphQLName("transactionTypeName") final String transactionTypeName) {
        CountingPaginatedData<TransactionGlAccountCategoryObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionGlAccountCategoriesForm();
            var command = new GetTransactionGlAccountCategoriesCommand();

            commandForm.setTransactionTypeName(transactionTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, TransactionGlAccountCategoryConstants.COMPONENT_VENDOR_NAME, TransactionGlAccountCategoryConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var transactionGlAccountCategories = entities.stream()
                            .map(TransactionGlAccountCategoryObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, transactionGlAccountCategories);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("transactionEntityRoleType")
    static TransactionEntityRoleTypeObject transactionEntityRoleType(final DataFetchingEnvironment env,
            @GraphQLName("transactionTypeName") final String transactionTypeName,
            @GraphQLName("transactionEntityRoleTypeName") final String transactionEntityRoleTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        TransactionEntityRoleType transactionEntityRoleType;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionEntityRoleTypeForm();

            commandForm.setTransactionTypeName(transactionTypeName);
            commandForm.setTransactionEntityRoleTypeName(transactionEntityRoleTypeName);
            commandForm.setUuid(id);

            transactionEntityRoleType = new GetTransactionEntityRoleTypeCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return transactionEntityRoleType == null ? null : new TransactionEntityRoleTypeObject(transactionEntityRoleType);
    }

    @GraphQLField
    @GraphQLName("transactionEntityRoleTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TransactionEntityRoleTypeObject> transactionEntityRoleTypes(final DataFetchingEnvironment env,
            @GraphQLName("transactionTypeName") final String transactionTypeName) {
        CountingPaginatedData<TransactionEntityRoleTypeObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionEntityRoleTypesForm();
            var command = new GetTransactionEntityRoleTypesCommand();

            commandForm.setTransactionTypeName(transactionTypeName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, TransactionEntityRoleTypeConstants.COMPONENT_VENDOR_NAME, TransactionEntityRoleTypeConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var transactionEntityRoleTypes = entities.stream()
                            .map(TransactionEntityRoleTypeObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, transactionEntityRoleTypes);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("transactionGroup")
    static TransactionGroupObject transactionGroup(final DataFetchingEnvironment env,
            @GraphQLName("transactionGroupName") final String transactionGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        TransactionGroup transactionGroup;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionGroupForm();

            commandForm.setTransactionGroupName(transactionGroupName);
            commandForm.setUuid(id);

            transactionGroup = new GetTransactionGroupCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return transactionGroup == null ? null : new TransactionGroupObject(transactionGroup);
    }

    @GraphQLField
    @GraphQLName("transactionGroups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TransactionGroupObject> transactionGroups(final DataFetchingEnvironment env) {
        CountingPaginatedData<TransactionGroupObject> data;

        try {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countTransactionGroups();

            try(var objectLimiter = new ObjectLimiter(env, TransactionGroupConstants.COMPONENT_VENDOR_NAME, TransactionGroupConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = AccountingUtil.getHome().getGetTransactionGroupsForm();
                var entities = new GetTransactionGroupsCommand().getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var transactionGroups = entities.stream().map(TransactionGroupObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, transactionGroups);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("transaction")
    static TransactionObject transaction(final DataFetchingEnvironment env,
            @GraphQLName("transactionName") final String transactionName,
            @GraphQLName("id") @GraphQLID final String id) {
        Transaction transaction;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionForm();

            commandForm.setTransactionName(transactionName);
            commandForm.setUuid(id);

            transaction = new GetTransactionCommand().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return transaction == null ? null : new TransactionObject(transaction);
    }

    @GraphQLField
    @GraphQLName("transactions")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    static CountingPaginatedData<TransactionObject> transactions(final DataFetchingEnvironment env,
            @GraphQLName("transactionGroupName") final String transactionGroupName) {
        CountingPaginatedData<TransactionObject> data;

        try {
            var commandForm = AccountingUtil.getHome().getGetTransactionsForm();
            var command = new GetTransactionsCommand();

            commandForm.setTransactionGroupName(transactionGroupName);

            var totalEntities = command.getTotalEntitiesForGraphQl(getUserVisitPK(env), commandForm);
            if(totalEntities == null) {
                data = Connections.emptyConnection();
            } else {
                try(var objectLimiter = new ObjectLimiter(env, TransactionConstants.COMPONENT_VENDOR_NAME, TransactionConstants.ENTITY_TYPE_NAME, totalEntities)) {
                    var entities = command.getEntitiesForGraphQl(getUserVisitPK(env), commandForm);

                    var transactions = entities.stream()
                            .map(TransactionObject::new)
                            .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, transactions);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

}
