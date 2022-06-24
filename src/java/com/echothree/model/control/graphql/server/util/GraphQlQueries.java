// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.graphql.server.util;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.server.command.GetCurrenciesCommand;
import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.server.command.GetCancellationKindCommand;
import com.echothree.control.user.cancellationpolicy.server.command.GetCancellationKindsCommand;
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
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypesCommand;
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
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionsCommand;
import com.echothree.control.user.inventory.server.command.GetLotCommand;
import com.echothree.control.user.inventory.server.command.GetLotsCommand;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.server.command.GetItemCategoriesCommand;
import com.echothree.control.user.item.server.command.GetItemCategoryCommand;
import com.echothree.control.user.item.server.command.GetItemCommand;
import com.echothree.control.user.item.server.command.GetItemDeliveryTypeCommand;
import com.echothree.control.user.item.server.command.GetItemDeliveryTypesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeCommand;
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
import com.echothree.control.user.item.server.command.GetItemUseTypeCommand;
import com.echothree.control.user.item.server.command.GetItemUseTypesCommand;
import com.echothree.control.user.item.server.command.GetItemsCommand;
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
import com.echothree.control.user.party.server.command.GetPersonalTitlesCommand;
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
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.CheckItemSpellingResult;
import com.echothree.control.user.search.server.command.GetCustomerResultsCommand;
import com.echothree.control.user.search.server.command.GetEmployeeResultsCommand;
import com.echothree.control.user.search.server.command.GetItemResultsCommand;
import com.echothree.control.user.search.server.command.GetSearchCheckSpellingActionTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchCheckSpellingActionTypesCommand;
import com.echothree.control.user.search.server.command.GetVendorResultsCommand;
import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.server.command.GetSecurityRoleGroupCommand;
import com.echothree.control.user.security.server.command.GetSecurityRoleGroupsCommand;
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
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.server.command.GetVendorCommand;
import com.echothree.control.user.vendor.server.command.GetVendorsCommand;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.server.command.GetWorkflowCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowStepsCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowTypeCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowTypesCommand;
import com.echothree.control.user.workflow.server.command.GetWorkflowsCommand;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationKindObject;
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
import com.echothree.model.control.core.server.graphql.EntityAttributeGroupObject;
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
import com.echothree.model.control.graphql.server.graphql.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.LotObject;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemCategoryObject;
import com.echothree.model.control.item.server.graphql.ItemDeliveryTypeObject;
import com.echothree.model.control.item.server.graphql.ItemDescriptionObject;
import com.echothree.model.control.item.server.graphql.ItemDescriptionTypeObject;
import com.echothree.model.control.item.server.graphql.ItemImageTypeObject;
import com.echothree.model.control.item.server.graphql.ItemInventoryTypeObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemPriceObject;
import com.echothree.model.control.item.server.graphql.ItemPriceTypeObject;
import com.echothree.model.control.item.server.graphql.ItemTypeObject;
import com.echothree.model.control.item.server.graphql.ItemUseTypeObject;
import com.echothree.model.control.offer.server.graphql.OfferItemObject;
import com.echothree.model.control.offer.server.graphql.OfferItemPriceObject;
import com.echothree.model.control.offer.server.graphql.OfferNameElementObject;
import com.echothree.model.control.offer.server.graphql.OfferObject;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.offer.server.graphql.UseNameElementObject;
import com.echothree.model.control.offer.server.graphql.UseObject;
import com.echothree.model.control.offer.server.graphql.UseTypeObject;
import com.echothree.model.control.party.server.graphql.CompanyObject;
import com.echothree.model.control.party.server.graphql.DateTimeFormatObject;
import com.echothree.model.control.party.server.graphql.DepartmentObject;
import com.echothree.model.control.party.server.graphql.DivisionObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.NameSuffixObject;
import com.echothree.model.control.party.server.graphql.PersonalTitleObject;
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
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnKindObject;
import com.echothree.model.control.search.server.graphql.CheckItemSpellingObject;
import com.echothree.model.control.search.server.graphql.CustomerResultsObject;
import com.echothree.model.control.search.server.graphql.EmployeeResultsObject;
import com.echothree.model.control.search.server.graphql.ItemResultsObject;
import com.echothree.model.control.search.server.graphql.SearchCheckSpellingActionTypeObject;
import com.echothree.model.control.search.server.graphql.VendorResultsObject;
import com.echothree.model.control.security.server.graphql.SecurityRoleGroupObject;
import com.echothree.model.control.selector.server.graphql.SelectorKindObject;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceChecksumTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceEncoderTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.shipment.server.graphql.FreeOnBoardObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseTypeObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.user.server.graphql.RecoveryQuestionObject;
import com.echothree.model.control.user.server.graphql.UserLoginObject;
import com.echothree.model.control.user.server.graphql.UserSessionObject;
import com.echothree.model.control.user.server.graphql.UserVisitObject;
import com.echothree.model.control.vendor.server.graphql.VendorObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowStepObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowStepTypeObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowTypeObject;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.cancellationpolicy.common.CancellationKindConstants;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
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
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ComponentVendor;
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
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
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
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.model.data.item.common.ItemDeliveryTypeConstants;
import com.echothree.model.data.item.common.ItemImageTypeConstants;
import com.echothree.model.data.item.common.ItemInventoryTypeConstants;
import com.echothree.model.data.item.common.ItemPriceTypeConstants;
import com.echothree.model.data.item.common.ItemTypeConstants;
import com.echothree.model.data.item.common.ItemUseTypeConstants;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.returnpolicy.common.ReturnKindConstants;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepType;
import com.echothree.model.data.workflow.server.entity.WorkflowType;
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
public final class GraphQlQueries
        extends BaseGraphQl {

    @GraphQLField
    @GraphQLName("searchCheckSpellingActionType")
    public static SearchCheckSpellingActionTypeObject searchCheckSpellingActionType(final DataFetchingEnvironment env,
            @GraphQLName("searchCheckSpellingActionTypeName") final String searchCheckSpellingActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SearchCheckSpellingActionType searchCheckSpellingActionType;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchCheckSpellingActionTypeForm();

            commandForm.setSearchCheckSpellingActionTypeName(searchCheckSpellingActionTypeName);
            commandForm.setUlid(id);

            searchCheckSpellingActionType = new GetSearchCheckSpellingActionTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return searchCheckSpellingActionType == null ? null : new SearchCheckSpellingActionTypeObject(searchCheckSpellingActionType);
    }

    @GraphQLField
    @GraphQLName("searchCheckSpellingActionTypes")
    public static Collection<SearchCheckSpellingActionTypeObject> searchCheckSpellingActionTypes(final DataFetchingEnvironment env) {
        Collection<SearchCheckSpellingActionType> searchCheckSpellingActionTypes;
        Collection<SearchCheckSpellingActionTypeObject> searchCheckSpellingActionTypeObjects;

        try {
            var commandForm = SearchUtil.getHome().getGetSearchCheckSpellingActionTypesForm();

            searchCheckSpellingActionTypes = new GetSearchCheckSpellingActionTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(searchCheckSpellingActionTypes == null) {
            searchCheckSpellingActionTypeObjects = emptyList();
        } else {
            searchCheckSpellingActionTypeObjects = new ArrayList<>(searchCheckSpellingActionTypes.size());

            searchCheckSpellingActionTypes.stream()
                    .map(SearchCheckSpellingActionTypeObject::new)
                    .forEachOrdered(searchCheckSpellingActionTypeObjects::add);
        }

        return searchCheckSpellingActionTypeObjects;
    }

    @GraphQLField
    @GraphQLName("workflow")
    public static WorkflowObject workflow(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("id") @GraphQLID final String id) {
        Workflow workflow;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setUlid(id);

            workflow = new GetWorkflowCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflow == null ? null : new WorkflowObject(workflow);
    }

    @GraphQLField
    @GraphQLName("workflows")
    public static Collection<WorkflowObject> workflows(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName) {
        Collection<Workflow> workflows;
        Collection<WorkflowObject> workflowObjects;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowsForm();

            commandForm.setSelectorKindName(selectorKindName);

            workflows = new GetWorkflowsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(workflows == null) {
            workflowObjects = emptyList();
        } else {
            workflowObjects = new ArrayList<>(workflows.size());

            workflows.stream()
                    .map(WorkflowObject::new)
                    .forEachOrdered(workflowObjects::add);
        }

        return workflowObjects;
    }

    @GraphQLField
    @GraphQLName("workflowStep")
    public static WorkflowStepObject workflowStep(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") final String workflowName,
            @GraphQLName("workflowStepName") final String workflowStepName,
            @GraphQLName("id") @GraphQLID final String id) {
        WorkflowStep workflowStep;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepForm();

            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setUlid(id);

            workflowStep = new GetWorkflowStepCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowStep == null ? null : new WorkflowStepObject(workflowStep);
    }

    @GraphQLField
    @GraphQLName("workflowSteps")
    public static Collection<WorkflowStepObject> workflowSteps(final DataFetchingEnvironment env,
            @GraphQLName("workflowName") @GraphQLNonNull final String workflowName) {
        Collection<WorkflowStep> workflowSteps;
        Collection<WorkflowStepObject> workflowStepObjects;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepsForm();

            commandForm.setWorkflowName(workflowName);

            workflowSteps = new GetWorkflowStepsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(workflowSteps == null) {
            workflowStepObjects = emptyList();
        } else {
            workflowStepObjects = new ArrayList<>(workflowSteps.size());

            workflowSteps.stream()
                    .map(WorkflowStepObject::new)
                    .forEachOrdered(workflowStepObjects::add);
        }

        return workflowStepObjects;
    }

    @GraphQLField
    @GraphQLName("workflowType")
    public static WorkflowTypeObject workflowType(final DataFetchingEnvironment env,
            @GraphQLName("workflowTypeName") final String workflowTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        WorkflowType workflowType;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowTypeForm();

            commandForm.setWorkflowTypeName(workflowTypeName);
            commandForm.setUlid(id);

            workflowType = new GetWorkflowTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowType == null ? null : new WorkflowTypeObject(workflowType);
    }

    @GraphQLField
    @GraphQLName("workflowTypes")
    public static Collection<WorkflowTypeObject> workflowTypes(final DataFetchingEnvironment env) {
        Collection<WorkflowType> workflowTypes;
        Collection<WorkflowTypeObject> workflowTypeObjects;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowTypesForm();

            workflowTypes = new GetWorkflowTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(workflowTypes == null) {
            workflowTypeObjects = emptyList();
        } else {
            workflowTypeObjects = new ArrayList<>(workflowTypes.size());

            workflowTypes.stream()
                    .map(WorkflowTypeObject::new)
                    .forEachOrdered(workflowTypeObjects::add);
        }

        return workflowTypeObjects;
    }

    @GraphQLField
    @GraphQLName("workflowStepType")
    public static WorkflowStepTypeObject workflowStepType(final DataFetchingEnvironment env,
            @GraphQLName("workflowStepTypeName") final String workflowStepTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        WorkflowStepType workflowStepType;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepTypeForm();

            commandForm.setWorkflowStepTypeName(workflowStepTypeName);
            commandForm.setUlid(id);

            workflowStepType = new GetWorkflowStepTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return workflowStepType == null ? null : new WorkflowStepTypeObject(workflowStepType);
    }

    @GraphQLField
    @GraphQLName("workflowStepTypes")
    public static Collection<WorkflowStepTypeObject> workflowStepTypes(final DataFetchingEnvironment env) {
        Collection<WorkflowStepType> workflowStepTypes;
        Collection<WorkflowStepTypeObject> workflowStepTypeObjects;

        try {
            var commandForm = WorkflowUtil.getHome().getGetWorkflowStepTypesForm();

            workflowStepTypes = new GetWorkflowStepTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(workflowStepTypes == null) {
            workflowStepTypeObjects = emptyList();
        } else {
            workflowStepTypeObjects = new ArrayList<>(workflowStepTypes.size());

            workflowStepTypes.stream()
                    .map(WorkflowStepTypeObject::new)
                    .forEachOrdered(workflowStepTypeObjects::add);
        }

        return workflowStepTypeObjects;
    }

    @GraphQLField
    @GraphQLName("sequence")
    public static SequenceObject sequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("sequenceName") final String sequenceName,
            @GraphQLName("id") @GraphQLID final String id) {
        Sequence sequence;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);
            commandForm.setUlid(id);

            sequence = new GetSequenceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequence == null ? null : new SequenceObject(sequence);
    }

    @GraphQLField
    @GraphQLName("sequences")
    public static Collection<SequenceObject> sequences(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName) {
        Collection<Sequence> sequences;
        Collection<SequenceObject> sequenceObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequencesForm();

            commandForm.setSequenceTypeName(sequenceTypeName);

            sequences = new GetSequencesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static SequenceTypeObject sequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SequenceType sequenceType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setUlid(id);

            sequenceType = new GetSequenceTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceType == null ? null : new SequenceTypeObject(sequenceType);
    }

    @GraphQLField
    @GraphQLName("sequenceTypes")
    public static Collection<SequenceTypeObject> sequenceTypes(final DataFetchingEnvironment env) {
        Collection<SequenceType> sequenceTypes;
        Collection<SequenceTypeObject> sequenceTypeObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceTypesForm();

            sequenceTypes = new GetSequenceTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequenceTypes == null) {
            sequenceTypeObjects = emptyList();
        } else {
            sequenceTypeObjects = new ArrayList<>(sequenceTypes.size());

            sequenceTypes.stream()
                    .map(SequenceTypeObject::new)
                    .forEachOrdered(sequenceTypeObjects::add);
        }

        return sequenceTypeObjects;
    }

    @GraphQLField
    @GraphQLName("sequenceChecksumType")
    public static SequenceChecksumTypeObject sequenceChecksumType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceChecksumTypeName") @GraphQLNonNull final String sequenceChecksumTypeName) {
        SequenceChecksumType sequenceChecksumType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceChecksumTypeForm();

            commandForm.setSequenceChecksumTypeName(sequenceChecksumTypeName);

            sequenceChecksumType = new GetSequenceChecksumTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceChecksumType == null ? null : new SequenceChecksumTypeObject(sequenceChecksumType);
    }

    @GraphQLField
    @GraphQLName("sequenceChecksumTypes")
    public static Collection<SequenceChecksumTypeObject> sequenceChecksumTypes(final DataFetchingEnvironment env) {
        Collection<SequenceChecksumType> sequenceChecksumTypes;
        Collection<SequenceChecksumTypeObject> sequenceChecksumTypeObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceChecksumTypesForm();

            sequenceChecksumTypes = new GetSequenceChecksumTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequenceChecksumTypes == null) {
            sequenceChecksumTypeObjects = emptyList();
        } else {
            sequenceChecksumTypeObjects = new ArrayList<>(sequenceChecksumTypes.size());

            sequenceChecksumTypes.stream()
                    .map(SequenceChecksumTypeObject::new)
                    .forEachOrdered(sequenceChecksumTypeObjects::add);
        }

        return sequenceChecksumTypeObjects;
    }

    @GraphQLField
    @GraphQLName("sequenceEncoderType")
    public static SequenceEncoderTypeObject sequenceEncoderType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceEncoderTypeName") @GraphQLNonNull final String sequenceEncoderTypeName) {
        SequenceEncoderType sequenceEncoderType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceEncoderTypeForm();

            commandForm.setSequenceEncoderTypeName(sequenceEncoderTypeName);

            sequenceEncoderType = new GetSequenceEncoderTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceEncoderType == null ? null : new SequenceEncoderTypeObject(sequenceEncoderType);
    }

    @GraphQLField
    @GraphQLName("sequenceEncoderTypes")
    public static Collection<SequenceEncoderTypeObject> sequenceEncoderTypes(final DataFetchingEnvironment env) {
        Collection<SequenceEncoderType> sequenceEncoderTypes;
        Collection<SequenceEncoderTypeObject> sequenceEncoderTypeObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceEncoderTypesForm();

            sequenceEncoderTypes = new GetSequenceEncoderTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequenceEncoderTypes == null) {
            sequenceEncoderTypeObjects = emptyList();
        } else {
            sequenceEncoderTypeObjects = new ArrayList<>(sequenceEncoderTypes.size());

            sequenceEncoderTypes.stream()
                    .map(SequenceEncoderTypeObject::new)
                    .forEachOrdered(sequenceEncoderTypeObjects::add);
        }

        return sequenceEncoderTypeObjects;
    }

    @GraphQLField
    @GraphQLName("selectorKind")
    public static SelectorKindObject selectorKind(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        SelectorKind selectorKind;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorKindForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setUlid(id);

            selectorKind = new GetSelectorKindCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selectorKind == null ? null : new SelectorKindObject(selectorKind);
    }

    @GraphQLField
    @GraphQLName("selectorKinds")
    public static Collection<SelectorKindObject> selectorKinds(final DataFetchingEnvironment env) {
        Collection<SelectorKind> selectorKinds;
        Collection<SelectorKindObject> selectorKindObjects;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorKindsForm();

            selectorKinds = new GetSelectorKindsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(selectorKinds == null) {
            selectorKindObjects = emptyList();
        } else {
            selectorKindObjects = new ArrayList<>(selectorKinds.size());

            selectorKinds.stream()
                    .map(SelectorKindObject::new)
                    .forEachOrdered(selectorKindObjects::add);
        }

        return selectorKindObjects;
    }

    @GraphQLField
    @GraphQLName("selectorType")
    public static SelectorTypeObject selectorType(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("selectorTypeName") final String selectorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SelectorType selectorType;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorTypeForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setUlid(id);

            selectorType = new GetSelectorTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selectorType == null ? null : new SelectorTypeObject(selectorType);
    }

    @GraphQLField
    @GraphQLName("selectorTypes")
    public static Collection<SelectorTypeObject> selectorTypes(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName) {
        Collection<SelectorType> selectorTypes;
        Collection<SelectorTypeObject> selectorTypeObjects;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorTypesForm();

            commandForm.setSelectorKindName(selectorKindName);

            selectorTypes = new GetSelectorTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static SelectorObject selector(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);

            selector = new GetSelectorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selector == null ? null : new SelectorObject(selector);
    }

    @GraphQLField
    @GraphQLName("selectors")
    public static Collection<SelectorObject> selectors(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("selectorTypeName") final String selectorTypeName) {
        Collection<Selector> selectors;
        Collection<SelectorObject> selectorObjects;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorsForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);

            selectors = new GetSelectorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterKindObject filterKind(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterKind filterKind;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterKindForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setUlid(id);

            filterKind = new GetFilterKindCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterKind == null ? null : new FilterKindObject(filterKind);
    }

    @GraphQLField
    @GraphQLName("filterKinds")
    public static Collection<FilterKindObject> filterKinds(final DataFetchingEnvironment env) {
        Collection<FilterKind> filterKinds;
        Collection<FilterKindObject> filterKindObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterKindsForm();

            filterKinds = new GetFilterKindsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterKinds == null) {
            filterKindObjects = emptyList();
        } else {
            filterKindObjects = new ArrayList<>(filterKinds.size());

            filterKinds.stream()
                    .map(FilterKindObject::new)
                    .forEachOrdered(filterKindObjects::add);
        }

        return filterKindObjects;
    }

    @GraphQLField
    @GraphQLName("filterType")
    public static FilterTypeObject filterType(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("filterTypeName") final String filterTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterType filterType;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterTypeForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setUlid(id);

            filterType = new GetFilterTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterType == null ? null : new FilterTypeObject(filterType);
    }

    @GraphQLField
    @GraphQLName("filterTypes")
    public static Collection<FilterTypeObject> filterTypes(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName) {
        Collection<FilterType> filterTypes;
        Collection<FilterTypeObject> filterTypeObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterTypesForm();

            commandForm.setFilterKindName(filterKindName);

            filterTypes = new GetFilterTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterObject filter(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);

            filter = new GetFilterCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filter == null ? null : new FilterObject(filter);
    }

    @GraphQLField
    @GraphQLName("filters")
    public static Collection<FilterObject> filters(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName) {
        Collection<Filter> filters;
        Collection<FilterObject> filterObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFiltersForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);

            filters = new GetFiltersCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterStepObject filterStep(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);

            filterStep = new GetFilterStepCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterStep == null ? null : new FilterStepObject(filterStep);
    }

    @GraphQLField
    @GraphQLName("filterSteps")
    public static Collection<FilterStepObject> filterSteps(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") final String filterName) {
        Collection<FilterStep> filterSteps;
        Collection<FilterStepObject> filterStepObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterStepsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);

            filterSteps = new GetFilterStepsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterAdjustmentSourceObject filterAdjustmentSource(final DataFetchingEnvironment env,
            @GraphQLName("filterAdjustmentSourceName") @GraphQLNonNull final String filterAdjustmentSourceName) {
        FilterAdjustmentSource filterAdjustmentSource;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentSourceForm();

            commandForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);

            filterAdjustmentSource = new GetFilterAdjustmentSourceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentSource == null ? null : new FilterAdjustmentSourceObject(filterAdjustmentSource);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentSources")
    public static Collection<FilterAdjustmentSourceObject> filterAdjustmentSources(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentSource> filterAdjustmentSources;
        Collection<FilterAdjustmentSourceObject> filterAdjustmentSourceObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentSourcesForm();

            filterAdjustmentSources = new GetFilterAdjustmentSourcesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterAdjustmentTypeObject filterAdjustmentType(final DataFetchingEnvironment env,
            @GraphQLName("filterAdjustmentTypeName") @GraphQLNonNull final String filterAdjustmentTypeName) {
        FilterAdjustmentType filterAdjustmentType;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentTypeForm();

            commandForm.setFilterAdjustmentTypeName(filterAdjustmentTypeName);

            filterAdjustmentType = new GetFilterAdjustmentTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentType == null ? null : new FilterAdjustmentTypeObject(filterAdjustmentType);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentTypes")
    public static Collection<FilterAdjustmentTypeObject> filterAdjustmentTypes(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentType> filterAdjustmentTypes;
        Collection<FilterAdjustmentTypeObject> filterAdjustmentTypeObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentTypesForm();

            filterAdjustmentTypes = new GetFilterAdjustmentTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterAdjustmentObject filterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("filterAdjustmentName") final String filterAdjustmentName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterAdjustment filterAdjustment;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUlid(id);

            filterAdjustment = new GetFilterAdjustmentCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustment == null ? null : new FilterAdjustmentObject(filterAdjustment);
    }

    @GraphQLField
    @GraphQLName("filterAdjustments")
    public static Collection<FilterAdjustmentObject> filterAdjustments(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName) {
        Collection<FilterAdjustment> filterAdjustments;
        Collection<FilterAdjustmentObject> filterAdjustmentObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentsForm();

            commandForm.setFilterKindName(filterKindName);

            filterAdjustments = new GetFilterAdjustmentsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterAdjustmentAmountObject filterAdjustmentAmount(final DataFetchingEnvironment env,
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

            filterAdjustmentAmount = new GetFilterAdjustmentAmountCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentAmount == null ? null : new FilterAdjustmentAmountObject(filterAdjustmentAmount);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentAmounts")
    public static Collection<FilterAdjustmentAmountObject> filterAdjustmentAmounts(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        Collection<FilterAdjustmentAmount> filterAdjustmentAmounts;
        Collection<FilterAdjustmentAmountObject> filterAdjustmentAmountObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentAmountsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            filterAdjustmentAmounts = new GetFilterAdjustmentAmountsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterAdjustmentFixedAmountObject filterAdjustmentFixedAmount(final DataFetchingEnvironment env,
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

            filterAdjustmentFixedAmount = new GetFilterAdjustmentFixedAmountCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentFixedAmount == null ? null : new FilterAdjustmentFixedAmountObject(filterAdjustmentFixedAmount);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentFixedAmounts")
    public static Collection<FilterAdjustmentFixedAmountObject> filterAdjustmentFixedAmounts(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        Collection<FilterAdjustmentFixedAmount> filterAdjustmentFixedAmounts;
        Collection<FilterAdjustmentFixedAmountObject> filterAdjustmentFixedAmountObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentFixedAmountsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            filterAdjustmentFixedAmounts = new GetFilterAdjustmentFixedAmountsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static FilterAdjustmentPercentObject filterAdjustmentPercent(final DataFetchingEnvironment env,
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

            filterAdjustmentPercent = new GetFilterAdjustmentPercentCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentPercent == null ? null : new FilterAdjustmentPercentObject(filterAdjustmentPercent);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentPercents")
    public static Collection<FilterAdjustmentPercentObject> filterAdjustmentPercents(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        Collection<FilterAdjustmentPercent> filterAdjustmentPercents;
        Collection<FilterAdjustmentPercentObject> filterAdjustmentPercentObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentPercentsForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            filterAdjustmentPercents = new GetFilterAdjustmentPercentsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static OfferUseObject offerUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        OfferUse offerUse;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            offerUse = new GetOfferUseCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerUse == null ? null : new OfferUseObject(offerUse);
    }

    @GraphQLField
    @GraphQLName("offerUses")
    public static Collection<OfferUseObject> offerUses(final DataFetchingEnvironment env,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("useName") final String useName) {
        Collection<OfferUse> offerUses;
        Collection<OfferUseObject> offerUseObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferUsesForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            offerUses = new GetOfferUsesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static OfferObject offer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("id") @GraphQLID final String id) {
        Offer offer;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferForm();

            commandForm.setOfferName(offerName);
            commandForm.setUlid(id);

            offer = new GetOfferCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offer == null ? null : new OfferObject(offer);
    }

    @GraphQLField
    @GraphQLName("offers")
    public static Collection<OfferObject> offers(final DataFetchingEnvironment env) {
        Collection<Offer> offers;
        Collection<OfferObject> offerObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOffersForm();

            offers = new GetOffersCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offers == null) {
            offerObjects = emptyList();
        } else {
            offerObjects = new ArrayList<>(offers.size());

            offers.stream()
                    .map(OfferObject::new)
                    .forEachOrdered(offerObjects::add);
        }

        return offerObjects;
    }

    @GraphQLField
    @GraphQLName("offerItem")
    public static OfferItemObject offerItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        OfferItem offerItem;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            offerItem = new GetOfferItemCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerItem == null ? null : new OfferItemObject(offerItem);
    }

    @GraphQLField
    @GraphQLName("offerItems")
    public static Collection<OfferItemObject> offerItems(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName) {
        Collection<OfferItem> offerItem;
        Collection<OfferItemObject> offerItemObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferItemsForm();

            commandForm.setOfferName(offerName);

            offerItem = new GetOfferItemsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static OfferItemPriceObject offerItemPrice(final DataFetchingEnvironment env,
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

            offerItemPrice = new GetOfferItemPriceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerItemPrice == null ? null : new OfferItemPriceObject(offerItemPrice);
    }

    @GraphQLField
    @GraphQLName("offerItemPrices")
    public static Collection<OfferItemPriceObject> offerItemPrices(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        Collection<OfferItemPrice> offerItemPrice;
        Collection<OfferItemPriceObject> offerItemPriceObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferItemPricesForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            offerItemPrice = new GetOfferItemPricesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static UseObject use(final DataFetchingEnvironment env,
            @GraphQLName("useName") final String useName,
            @GraphQLName("id") @GraphQLID final String id) {
        Use use;

        try {
            var commandForm = OfferUtil.getHome().getGetUseForm();

            commandForm.setUseName(useName);
            commandForm.setUlid(id);

            use = new GetUseCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return use == null ? null : new UseObject(use);
    }

    @GraphQLField
    @GraphQLName("uses")
    public static Collection<UseObject> uses(final DataFetchingEnvironment env) {
        Collection<Use> uses;
        Collection<UseObject> useObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetUsesForm();

            uses = new GetUsesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(uses == null) {
            useObjects = emptyList();
        } else {
            useObjects = new ArrayList<>(uses.size());

            uses.stream()
                    .map(UseObject::new)
                    .forEachOrdered(useObjects::add);
        }

        return useObjects;
    }

    @GraphQLField
    @GraphQLName("offerNameElement")
    public static OfferNameElementObject offerNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") final String offerNameElementName,
            @GraphQLName("id") @GraphQLID final String id) {
        OfferNameElement offerNameElement;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);
            commandForm.setUlid(id);

            offerNameElement = new GetOfferNameElementCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerNameElement == null ? null : new OfferNameElementObject(offerNameElement);
    }

    @GraphQLField
    @GraphQLName("offerNameElements")
    public static Collection<OfferNameElementObject> offerNameElements(final DataFetchingEnvironment env) {
        Collection<OfferNameElement> offerNameElements;
        Collection<OfferNameElementObject> offerNameElementObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferNameElementsForm();

            offerNameElements = new GetOfferNameElementsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static UseNameElementObject useNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") final String useNameElementName,
            @GraphQLName("id") @GraphQLID final String id) {
        UseNameElement useNameElement;

        try {
            var commandForm = OfferUtil.getHome().getGetUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);
            commandForm.setUlid(id);

            useNameElement = new GetUseNameElementCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return useNameElement == null ? null : new UseNameElementObject(useNameElement);
    }

    @GraphQLField
    @GraphQLName("useNameElements")
    public static Collection<UseNameElementObject> useNameElements(final DataFetchingEnvironment env) {
        Collection<UseNameElement> useNameElements;
        Collection<UseNameElementObject> useNameElementObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetUseNameElementsForm();

            useNameElements = new GetUseNameElementsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static UseTypeObject useType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") final String useTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        UseType useType;

        try {
            var commandForm = OfferUtil.getHome().getGetUseTypeForm();

            commandForm.setUseTypeName(useTypeName);
            commandForm.setUlid(id);

            useType = new GetUseTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return useType == null ? null : new UseTypeObject(useType);
    }

    @GraphQLField
    @GraphQLName("useTypes")
    public static Collection<UseTypeObject> useTypes(final DataFetchingEnvironment env) {
        Collection<UseType> useTypes;
        Collection<UseTypeObject> useTypeObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetUseTypesForm();

            useTypes = new GetUseTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(useTypes == null) {
            useTypeObjects = emptyList();
        } else {
            useTypeObjects = new ArrayList<>(useTypes.size());

            useTypes.stream()
                    .map(UseTypeObject::new)
                    .forEachOrdered(useTypeObjects::add);
        }

        return useTypeObjects;
    }

    @GraphQLField
    @GraphQLName("freeOnBoard")
    public static FreeOnBoardObject freeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") final String freeOnBoardName,
            @GraphQLName("id") @GraphQLID final String id) {
        FreeOnBoard freeOnBoard;

        try {
            var commandForm = ShipmentUtil.getHome().getGetFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);
            commandForm.setUlid(id);

            freeOnBoard = new GetFreeOnBoardCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return freeOnBoard == null ? null : new FreeOnBoardObject(freeOnBoard);
    }

    @GraphQLField
    @GraphQLName("freeOnBoards")
    public static Collection<FreeOnBoardObject> freeOnBoards(final DataFetchingEnvironment env) {
        Collection<FreeOnBoard> freeOnBoards;
        Collection<FreeOnBoardObject> freeOnBoardObjects;

        try {
            var commandForm = ShipmentUtil.getHome().getGetFreeOnBoardsForm();

            freeOnBoards = new GetFreeOnBoardsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(freeOnBoards == null) {
            freeOnBoardObjects = emptyList();
        } else {
            freeOnBoardObjects = new ArrayList<>(freeOnBoards.size());

            freeOnBoards.stream()
                    .map(FreeOnBoardObject::new)
                    .forEachOrdered(freeOnBoardObjects::add);
        }

        return freeOnBoardObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypeCodeType")
    public static PaymentProcessorTypeCodeTypeObject paymentProcessorTypeCodeType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("paymentProcessorTypeCodeTypeName") final String paymentProcessorTypeCodeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTypeCodeType paymentProcessorTypeCodeType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeCodeTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setPaymentProcessorTypeCodeTypeName(paymentProcessorTypeCodeTypeName);
            commandForm.setUlid(id);

            paymentProcessorTypeCodeType = new GetPaymentProcessorTypeCodeTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTypeCodeType == null ? null : new PaymentProcessorTypeCodeTypeObject(paymentProcessorTypeCodeType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypeCode")
    public static PaymentProcessorTypeCodeObject paymentProcessorTypeCode(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);

            paymentProcessorTypeCode = new GetPaymentProcessorTypeCodeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTypeCode == null ? null : new PaymentProcessorTypeCodeObject(paymentProcessorTypeCode);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTransaction")
    public static PaymentProcessorTransactionObject paymentProcessorTransaction(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTransactionName") final String paymentProcessorTransactionName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTransaction paymentProcessorTransaction;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTransactionForm();

            commandForm.setPaymentProcessorTransactionName(paymentProcessorTransactionName);
            commandForm.setUlid(id);

            paymentProcessorTransaction = new GetPaymentProcessorTransactionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTransaction == null ? null : new PaymentProcessorTransactionObject(paymentProcessorTransaction);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTransactions")
    public static Collection<PaymentProcessorTransactionObject> paymentProcessorTransactions(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorTransaction> paymentProcessorTransactions;
        Collection<PaymentProcessorTransactionObject> paymentProcessorTransactionObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTransactionsForm();

            paymentProcessorTransactions = new GetPaymentProcessorTransactionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static PaymentProcessorObject paymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") final String paymentProcessorName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessor paymentProcessor;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);
            commandForm.setUlid(id);

            paymentProcessor = new GetPaymentProcessorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessor == null ? null : new PaymentProcessorObject(paymentProcessor);
    }

    @GraphQLField
    @GraphQLName("paymentProcessors")
    public static Collection<PaymentProcessorObject> paymentProcessors(final DataFetchingEnvironment env) {
        Collection<PaymentProcessor> paymentProcessors;
        Collection<PaymentProcessorObject> paymentProcessorObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorsForm();

            paymentProcessors = new GetPaymentProcessorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static PaymentProcessorTypeObject paymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorType paymentProcessorType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setUlid(id);

            paymentProcessorType = new GetPaymentProcessorTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorType == null ? null : new PaymentProcessorTypeObject(paymentProcessorType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypes")
    public static Collection<PaymentProcessorTypeObject> paymentProcessorTypes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorType> paymentProcessorTypes;
        Collection<PaymentProcessorTypeObject> paymentProcessorTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypesForm();

            paymentProcessorTypes = new GetPaymentProcessorTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static PaymentMethodTypeObject paymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") final String paymentMethodTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentMethodType paymentMethodType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);
            commandForm.setUlid(id);

            paymentMethodType = new GetPaymentMethodTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentMethodType == null ? null : new PaymentMethodTypeObject(paymentMethodType);
    }

    @GraphQLField
    @GraphQLName("paymentMethodTypes")
    public static Collection<PaymentMethodTypeObject> paymentMethodTypes(final DataFetchingEnvironment env) {
        Collection<PaymentMethodType> paymentMethodTypes;
        Collection<PaymentMethodTypeObject> paymentMethodTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentMethodTypesForm();

            paymentMethodTypes = new GetPaymentMethodTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static PaymentProcessorResultCodeObject paymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") final String paymentProcessorResultCodeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorResultCode paymentProcessorResultCode;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
            commandForm.setUlid(id);

            paymentProcessorResultCode = new GetPaymentProcessorResultCodeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorResultCode == null ? null : new PaymentProcessorResultCodeObject(paymentProcessorResultCode);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorResultCodes")
    public static Collection<PaymentProcessorResultCodeObject> paymentProcessorResultCodes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorResultCode> paymentProcessorResultCodes;
        Collection<PaymentProcessorResultCodeObject> paymentProcessorResultCodeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorResultCodesForm();

            paymentProcessorResultCodes = new GetPaymentProcessorResultCodesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static PaymentProcessorActionTypeObject paymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") final String paymentProcessorActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorActionType paymentProcessorActionType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
            commandForm.setUlid(id);

            paymentProcessorActionType = new GetPaymentProcessorActionTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorActionType == null ? null : new PaymentProcessorActionTypeObject(paymentProcessorActionType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorActionTypes")
    public static Collection<PaymentProcessorActionTypeObject> paymentProcessorActionTypes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorActionType> paymentProcessorActionTypes;
        Collection<PaymentProcessorActionTypeObject> paymentProcessorActionTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorActionTypesForm();

            paymentProcessorActionTypes = new GetPaymentProcessorActionTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static AppearanceObject appearance(final DataFetchingEnvironment env,
            @GraphQLName("appearanceName") final String appearanceName,
            @GraphQLName("id") @GraphQLID final String id) {
        Appearance appearance;

        try {
            var commandForm = CoreUtil.getHome().getGetAppearanceForm();

            commandForm.setAppearanceName(appearanceName);
            commandForm.setUlid(id);

            appearance = new GetAppearanceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return appearance == null ? null : new AppearanceObject(appearance);
    }

    @GraphQLField
    @GraphQLName("appearances")
    public static Collection<AppearanceObject> appearances(final DataFetchingEnvironment env) {
        Collection<Appearance> appearances;
        Collection<AppearanceObject> appearanceObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetAppearancesForm();

            appearances = new GetAppearancesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(appearances == null) {
            appearanceObjects = emptyList();
        } else {
            appearanceObjects = new ArrayList<>(appearances.size());

            appearances.stream().map(AppearanceObject::new).forEachOrdered(appearanceObjects::add);
        }

        return appearanceObjects;
    }

    @GraphQLField
    @GraphQLName("entityAttributeGroup")
    public static EntityAttributeGroupObject entityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") final String entityAttributeGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityAttributeGroup entityAttributeGroup;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);
            commandForm.setUlid(id);

            entityAttributeGroup = new GetEntityAttributeGroupCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityAttributeGroup == null ? null : new EntityAttributeGroupObject(entityAttributeGroup, null);
    }

    @GraphQLField
    @GraphQLName("entityAttributeGroups")
    public static Collection<EntityAttributeGroupObject> entityAttributeGroups(final DataFetchingEnvironment env) {
        Collection<EntityAttributeGroup> entityAttributeGroups;
        Collection<EntityAttributeGroupObject> entityAttributeGroupObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeGroupsForm();

            entityAttributeGroups = new GetEntityAttributeGroupsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(entityAttributeGroups == null) {
            entityAttributeGroupObjects = emptyList();
        } else {
            entityAttributeGroupObjects = new ArrayList<>(entityAttributeGroups.size());

            entityAttributeGroups.stream().map(e -> new EntityAttributeGroupObject(e, null)).forEachOrdered(entityAttributeGroupObjects::add);
        }

        return entityAttributeGroupObjects;
    }

    @GraphQLField
    @GraphQLName("entityInstance")
    public static EntityInstanceObject entityInstance(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityInstance entityInstance;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityInstanceForm();

            commandForm.setUlid(id);

            entityInstance = new GetEntityInstanceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityInstance == null ? null : new EntityInstanceObject(entityInstance);
    }

    @GraphQLField
    @GraphQLName("entityInstances")
    public static Collection<EntityInstanceObject> entityInstances(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName) {
        Collection<EntityInstance> entityInstances;
        Collection<EntityInstanceObject> entityInstanceObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityInstancesForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            entityInstances = new GetEntityInstancesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(entityInstances == null) {
            entityInstanceObjects = emptyList();
        } else {
            entityInstanceObjects = new ArrayList<>(entityInstances.size());

            entityInstances.stream().map(EntityInstanceObject::new).forEachOrdered(entityInstanceObjects::add);
        }

        return entityInstanceObjects;
    }

    @GraphQLField
    @GraphQLName("entityType")
    public static EntityTypeObject entityType(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityType entityType;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setUlid(id);

            entityType = new GetEntityTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityType == null ? null : new EntityTypeObject(entityType);
    }

    @GraphQLField
    @GraphQLName("entityTypes")
    public static Collection<EntityTypeObject> entityTypes(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName) {
        Collection<EntityType> entityTypes;
        Collection<EntityTypeObject> entityTypeObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityTypesForm();

            commandForm.setComponentVendorName(componentVendorName);

            entityTypes = new GetEntityTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(entityTypes == null) {
            entityTypeObjects = emptyList();
        } else {
            entityTypeObjects = new ArrayList<>(entityTypes.size());

            entityTypes.stream().map(EntityTypeObject::new).forEachOrdered(entityTypeObjects::add);
        }

        return entityTypeObjects;
    }

    @GraphQLField
    @GraphQLName("componentVendor")
    public static ComponentVendorObject componentVendor(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("id") @GraphQLID final String id) {
        ComponentVendor componentVendor;

        try {
            var commandForm = CoreUtil.getHome().getGetComponentVendorForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setUlid(id);

            componentVendor = new GetComponentVendorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return componentVendor == null ? null : new ComponentVendorObject(componentVendor);
    }

    @GraphQLField
    @GraphQLName("componentVendors")
    public static Collection<ComponentVendorObject> componentVendors(final DataFetchingEnvironment env) {
        Collection<ComponentVendor> componentVendors;
        Collection<ComponentVendorObject> componentVendorObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetComponentVendorsForm();

            componentVendors = new GetComponentVendorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(componentVendors == null) {
            componentVendorObjects = emptyList();
        } else {
            componentVendorObjects = new ArrayList<>(componentVendors.size());

            componentVendors.stream().map(ComponentVendorObject::new).forEachOrdered(componentVendorObjects::add);
        }

        return componentVendorObjects;
    }

    @GraphQLField
    @GraphQLName("inventoryCondition")
    public static InventoryConditionObject inventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("id") @GraphQLID final String id) {
        InventoryCondition inventoryCondition;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUlid(id);

            inventoryCondition = new GetInventoryConditionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return inventoryCondition == null ? null : new InventoryConditionObject(inventoryCondition);
    }

    @GraphQLField
    @GraphQLName("inventoryConditions")
    public static Collection<InventoryConditionObject> inventoryConditions(final DataFetchingEnvironment env) {
        Collection<InventoryCondition> inventoryConditions;
        Collection<InventoryConditionObject> inventoryConditionObjects;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryConditionsForm();

            inventoryConditions = new GetInventoryConditionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(inventoryConditions == null) {
            inventoryConditionObjects = emptyList();
        } else {
            inventoryConditionObjects = new ArrayList<>(inventoryConditions.size());

            inventoryConditions.stream()
                    .map(InventoryConditionObject::new)
                    .forEachOrdered(inventoryConditionObjects::add);
        }

        return inventoryConditionObjects;
    }

    @GraphQLField
    @GraphQLName("lot")
    public static LotObject lot(final DataFetchingEnvironment env,
            @GraphQLName("lotName") final String lotName,
            @GraphQLName("id") @GraphQLID final String id) {
        Lot lot;

        try {
            var commandForm = InventoryUtil.getHome().getGetLotForm();

            commandForm.setLotName(lotName);
            commandForm.setUlid(id);

            lot = new GetLotCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return lot == null ? null : new LotObject(lot);
    }

    @GraphQLField
    @GraphQLName("lots")
    public static Collection<LotObject> lots(final DataFetchingEnvironment env) {
        Collection<Lot> lots;
        Collection<LotObject> lotObjects;

        try {
            var commandForm = InventoryUtil.getHome().getGetLotsForm();

            lots = new GetLotsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(lots == null) {
            lotObjects = emptyList();
        } else {
            lotObjects = new ArrayList<>(lots.size());

            lots.stream()
                    .map(LotObject::new)
                    .forEachOrdered(lotObjects::add);
        }

        return lotObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageLayout")
    public static ContentPageLayoutObject contentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") final String contentPageLayoutName,
            @GraphQLName("id") @GraphQLID final String id) {
        ContentPageLayout contentPageLayout;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);
            commandForm.setUlid(id);
        
            contentPageLayout = new GetContentPageLayoutCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageLayout == null ? null : new ContentPageLayoutObject(contentPageLayout);
    }

    @GraphQLField
    @GraphQLName("contentPageLayouts")
    public static Collection<ContentPageLayoutObject> contentPageLayouts(final DataFetchingEnvironment env) {
        Collection<ContentPageLayout> contentPageLayouts;
        Collection<ContentPageLayoutObject> contentPageLayoutObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutsForm();
        
            contentPageLayouts = new GetContentPageLayoutsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageLayouts == null) {
            contentPageLayoutObjects = emptyList();
        } else {
            contentPageLayoutObjects = new ArrayList<>(contentPageLayouts.size());

            contentPageLayouts.stream()
                    .map(ContentPageLayoutObject::new)
                    .forEachOrdered(contentPageLayoutObjects::add);
        }
        
        return contentPageLayoutObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageLayoutArea")
    public static ContentPageLayoutAreaObject contentPageLayoutArea(final DataFetchingEnvironment env,
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
        
            contentPageLayoutArea = new GetContentPageLayoutAreaCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageLayoutArea == null ? null : new ContentPageLayoutAreaObject(contentPageLayoutArea);
    }

    @GraphQLField
    @GraphQLName("contentPageLayoutAreas")
    public static Collection<ContentPageLayoutAreaObject> contentPageLayoutAreas(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName) {
        Collection<ContentPageLayoutArea> contentPageLayoutAreas;
        Collection<ContentPageLayoutAreaObject> contentPageLayoutAreaObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutAreasForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            
            contentPageLayoutAreas = new GetContentPageLayoutAreasCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageLayoutAreas == null) {
            contentPageLayoutAreaObjects = emptyList();
        } else {
            contentPageLayoutAreaObjects = new ArrayList<>(contentPageLayoutAreas.size());

            contentPageLayoutAreas.stream()
                    .map(ContentPageLayoutAreaObject::new)
                    .forEachOrdered(contentPageLayoutAreaObjects::add);
        }
        
        return contentPageLayoutAreaObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageAreaType")
    public static ContentPageAreaTypeObject contentPageAreaType(final DataFetchingEnvironment env,
            @GraphQLName("contentPageAreaTypeName") final String contentPageAreaTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ContentPageAreaType contentPageAreaType;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaTypeForm();

            commandForm.setContentPageAreaTypeName(contentPageAreaTypeName);
            commandForm.setUlid(id);
        
            contentPageAreaType = new GetContentPageAreaTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageAreaType == null ? null : new ContentPageAreaTypeObject(contentPageAreaType);
    }

    @GraphQLField
    @GraphQLName("contentPageAreaTypes")
    public static Collection<ContentPageAreaTypeObject> contentPageAreaTypes(final DataFetchingEnvironment env) {
        Collection<ContentPageAreaType> contentPageAreaTypes;
        Collection<ContentPageAreaTypeObject> contentPageAreaTypeObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaTypesForm();
        
            contentPageAreaTypes = new GetContentPageAreaTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageAreaTypes == null) {
            contentPageAreaTypeObjects = emptyList();
        } else {
            contentPageAreaTypeObjects = new ArrayList<>(contentPageAreaTypes.size());

            contentPageAreaTypes.stream()
                    .map(ContentPageAreaTypeObject::new)
                    .forEachOrdered(contentPageAreaTypeObjects::add);
        }
        
        return contentPageAreaTypeObjects;
    }

    @GraphQLField
    @GraphQLName("contentWebAddress")
    public static ContentWebAddressObject contentWebAddress(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") @GraphQLNonNull final String contentWebAddressName) {
        ContentWebAddress contentWebAddress;

        try {
            var commandForm = ContentUtil.getHome().getGetContentWebAddressForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
        
            contentWebAddress = new GetContentWebAddressCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentWebAddress == null ? null : new ContentWebAddressObject(contentWebAddress);
    }

    @GraphQLField
    @GraphQLName("contentWebAddresses")
    public static Collection<ContentWebAddressObject> contentWebAddresses(final DataFetchingEnvironment env) {
        Collection<ContentWebAddress> contentWebAddresses;
        Collection<ContentWebAddressObject> contentWebAddressObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentWebAddressesForm();
        
            contentWebAddresses = new GetContentWebAddressesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentWebAddresses == null) {
            contentWebAddressObjects = emptyList();
        } else {
            contentWebAddressObjects = new ArrayList<>(contentWebAddresses.size());

            contentWebAddresses.stream()
                    .map(ContentWebAddressObject::new)
                    .forEachOrdered(contentWebAddressObjects::add);
        }
        
        return contentWebAddressObjects;
    }

    @GraphQLField
    @GraphQLName("contentCollection")
    public static ContentCollectionObject contentCollection(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") final String contentCollectionName) {
        ContentCollection contentCollection;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCollectionForm();

            commandForm.setContentCollectionName(contentCollectionName);

            contentCollection = new GetContentCollectionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCollection == null ? null : new ContentCollectionObject(contentCollection);
    }

    @GraphQLField
    @GraphQLName("contentCollections")
    public static Collection<ContentCollectionObject> contentCollections(final DataFetchingEnvironment env) {
        Collection<ContentCollection> contentCollections;
        Collection<ContentCollectionObject> contentCollectionObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCollectionsForm();
        
            contentCollections = new GetContentCollectionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCollections == null) {
            contentCollectionObjects = emptyList();
        } else {
            contentCollectionObjects = new ArrayList<>(contentCollections.size());

            contentCollections.stream()
                    .map(ContentCollectionObject::new)
                    .forEachOrdered(contentCollectionObjects::add);
        }
        
        return contentCollectionObjects;
    }

    @GraphQLField
    @GraphQLName("contentSection")
    public static ContentSectionObject contentSection(final DataFetchingEnvironment env,
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

            contentSection = new GetContentSectionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentSection == null ? null : new ContentSectionObject(contentSection);
    }

    @GraphQLField
    @GraphQLName("contentSections")
    public static Collection<ContentSectionObject> contentSections(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("parentContentSectionName") final String parentContentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentSection> contentSections;
        Collection<ContentSectionObject> contentSectionObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentSectionsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setParentContentSectionName(parentContentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentSections = new GetContentSectionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentSections == null) {
            contentSectionObjects = emptyList();
        } else {
            contentSectionObjects = new ArrayList<>(contentSections.size());

            contentSections.stream()
                    .map(ContentSectionObject::new)
                    .forEachOrdered(contentSectionObjects::add);
        }
        
        return contentSectionObjects;
    }

    @GraphQLField
    @GraphQLName("contentPage")
    public static ContentPageObject contentPage(final DataFetchingEnvironment env,
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

            contentPage = new GetContentPageCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPage == null ? null : new ContentPageObject(contentPage);
    }

    @GraphQLField
    @GraphQLName("contentPages")
    public static Collection<ContentPageObject> contentPages(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentSectionName") final String contentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentPage> contentPages;
        Collection<ContentPageObject> contentPageObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPagesForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentPages = new GetContentPagesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPages == null) {
            contentPageObjects = emptyList();
        } else {
            contentPageObjects = new ArrayList<>(contentPages.size());

            contentPages.stream()
                    .map(ContentPageObject::new)
                    .forEachOrdered(contentPageObjects::add);
        }
        
        return contentPageObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageArea")
    public static ContentPageAreaObject contentPageArea(final DataFetchingEnvironment env,
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

            contentPageArea = new GetContentPageAreaCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageArea == null ? null : new ContentPageAreaObject(contentPageArea);
    }

    @GraphQLField
    @GraphQLName("contentPageAreas")
    public static Collection<ContentPageAreaObject> contentPageAreas(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName) {
        Collection<ContentPageArea> contentPageAreas;
        Collection<ContentPageAreaObject> contentPageAreaObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreasForm();
        
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);

            contentPageAreas = new GetContentPageAreasCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageAreas == null) {
            contentPageAreaObjects = emptyList();
        } else {
            contentPageAreaObjects = new ArrayList<>(contentPageAreas.size());

            contentPageAreas.stream()
                    .map(ContentPageAreaObject::new)
                    .forEachOrdered(contentPageAreaObjects::add);
        }
        
        return contentPageAreaObjects;
    }

    @GraphQLField
    @GraphQLName("contentCatalog")
    public static ContentCatalogObject contentCatalog(final DataFetchingEnvironment env,
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

            contentCatalog = new GetContentCatalogCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCatalog == null ? null : new ContentCatalogObject(contentCatalog);
    }

    @GraphQLField
    @GraphQLName("contentCatalogs")
    public static Collection<ContentCatalogObject> contentCatalogs(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCatalog> contentCatalogs;
        Collection<ContentCatalogObject> contentCatalogObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalogs = new GetContentCatalogsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCatalogs == null) {
            contentCatalogObjects = emptyList();
        } else {
            contentCatalogObjects = new ArrayList<>(contentCatalogs.size());

            contentCatalogs.stream()
                    .map(ContentCatalogObject::new)
                    .forEachOrdered(contentCatalogObjects::add);
        }
        
        return contentCatalogObjects;
    }

    @GraphQLField
    @GraphQLName("contentCatalogItem")
    public static ContentCatalogItemObject contentCatalogItem(final DataFetchingEnvironment env,
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

            contentCatalogItem = new GetContentCatalogItemCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCatalogItem == null ? null : new ContentCatalogItemObject(contentCatalogItem);
    }

    @GraphQLField
    @GraphQLName("contentCatalogItems")
    public static Collection<ContentCatalogItemObject> contentCatalogItems(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCatalogItem> contentCatalogItems;
        Collection<ContentCatalogItemObject> contentCatalogItemObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogItemsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalogItems = new GetContentCatalogItemsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCatalogItems == null) {
            contentCatalogItemObjects = emptyList();
        } else {
            contentCatalogItemObjects = new ArrayList<>(contentCatalogItems.size());

            contentCatalogItems.stream()
                    .map(ContentCatalogItemObject::new)
                    .forEachOrdered(contentCatalogItemObjects::add);
        }
        
        return contentCatalogItemObjects;
    }

    @GraphQLField
    @GraphQLName("contentCategory")
    public static ContentCategoryObject contentCategory(final DataFetchingEnvironment env,
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

            contentCategory = new GetContentCategoryCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCategory == null ? null : new ContentCategoryObject(contentCategory);
    }

    @GraphQLField
    @GraphQLName("contentCategories")
    public static Collection<ContentCategoryObject> contentCategories(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("parentContentCategoryName") final String parentContentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCategory> contentCategories;
        Collection<ContentCategoryObject> contentCategoryObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoriesForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setParentContentCategoryName(parentContentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategories = new GetContentCategoriesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCategories == null) {
            contentCategoryObjects = emptyList();
        } else {
            contentCategoryObjects = new ArrayList<>(contentCategories.size());

            contentCategories.stream()
                    .map(ContentCategoryObject::new)
                    .forEachOrdered(contentCategoryObjects::add);
        }
        
        return contentCategoryObjects;
    }

    @GraphQLField
    @GraphQLName("contentCategoryItem")
    public static ContentCategoryItemObject contentCategoryItem(final DataFetchingEnvironment env,
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

            contentCategoryItem = new GetContentCategoryItemCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCategoryItem == null ? null : new ContentCategoryItemObject(contentCategoryItem);
    }

    @GraphQLField
    @GraphQLName("contentCategoryItems")
    public static Collection<ContentCategoryItemObject> contentCategoryItems(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCategoryItem> contentCategoryItems;
        Collection<ContentCategoryItemObject> contentCategoryItemObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoryItemsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategoryItems = new GetContentCategoryItemsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCategoryItems == null) {
            contentCategoryItemObjects = emptyList();
        } else {
            contentCategoryItemObjects = new ArrayList<>(contentCategoryItems.size());

            contentCategoryItems.stream()
                    .map(ContentCategoryItemObject::new)
                    .forEachOrdered(contentCategoryItemObjects::add);
        }
        
        return contentCategoryItemObjects;
    }

    @GraphQLField
    @GraphQLName("mimeTypeFileExtension")
    public static MimeTypeFileExtensionObject mimeTypeFileExtension(final DataFetchingEnvironment env,
            @GraphQLName("fileExtension") @GraphQLNonNull final String fileExtension) {
        MimeTypeFileExtension mimeTypeFileExtension;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionForm();

            commandForm.setFileExtension(fileExtension);

            mimeTypeFileExtension = new GetMimeTypeFileExtensionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeTypeFileExtension == null ? null : new MimeTypeFileExtensionObject(mimeTypeFileExtension);
    }

    @GraphQLField
    @GraphQLName("mimeTypeFileExtensions")
    public static Collection<MimeTypeFileExtensionObject> mimeTypeFileExtensions(final DataFetchingEnvironment env) {
        Collection<MimeTypeFileExtension> mimeTypeFileExtensions;
        Collection<MimeTypeFileExtensionObject> mimeTypeFileExtensionObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionsForm();

            mimeTypeFileExtensions = new GetMimeTypeFileExtensionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static MimeTypeUsageTypeObject mimeTypeUsageType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeUsageTypeName") @GraphQLNonNull final String mimeTypeUsageTypeName) {
        MimeTypeUsageType mimeTypeUsageType;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypeForm();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            mimeTypeUsageType = new GetMimeTypeUsageTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeTypeUsageType == null ? null : new MimeTypeUsageTypeObject(mimeTypeUsageType);
    }

    @GraphQLField
    @GraphQLName("mimeTypeUsageTypes")
    public static Collection<MimeTypeUsageTypeObject> mimeTypeUsageTypes(final DataFetchingEnvironment env) {
        Collection<MimeTypeUsageType> mimeTypeUsageTypes;
        Collection<MimeTypeUsageTypeObject> mimeTypeUsageTypeObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypesForm();

            mimeTypeUsageTypes = new GetMimeTypeUsageTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static MimeTypeObject mimeType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeName") @GraphQLNonNull final String mimeTypeName) {
        MimeType mimeType;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeForm();

            commandForm.setMimeTypeName(mimeTypeName);

            mimeType = new GetMimeTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeType == null ? null : new MimeTypeObject(mimeType);
    }

    @GraphQLField
    @GraphQLName("mimeTypes")
    public static Collection<MimeTypeObject> mimeTypes(final DataFetchingEnvironment env,
           @GraphQLName("mimeTypeUsageTypeName") final String mimeTypeUsageTypeName) {
        Collection<MimeType> mimeTypes;
        Collection<MimeTypeObject> mimeTypeObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypesForm();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            mimeTypes = new GetMimeTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypes == null) {
            mimeTypeObjects = emptyList();
        } else {
            mimeTypeObjects = new ArrayList<>(mimeTypes.size());

            mimeTypes.stream()
                    .map(MimeTypeObject::new)
                    .forEachOrdered(mimeTypeObjects::add);
        }

        return mimeTypeObjects;
    }

    @GraphQLField
    @GraphQLName("queueType")
    public static QueueTypeObject queueType(final DataFetchingEnvironment env,
            @GraphQLName("queueTypeName") @GraphQLNonNull final String queueTypeName) {
        QueueType queueType;

        try {
            var commandForm = QueueUtil.getHome().getGetQueueTypeForm();

            commandForm.setQueueTypeName(queueTypeName);

            queueType = new GetQueueTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return queueType == null ? null : new QueueTypeObject(queueType);
    }

    @GraphQLField
    @GraphQLName("queueTypes")
    public static Collection<QueueTypeObject> queueTypes(final DataFetchingEnvironment env) {
        Collection<QueueType> queueTypes;
        Collection<QueueTypeObject> queueTypeObjects;

        try {
            var commandForm = QueueUtil.getHome().getGetQueueTypesForm();

            queueTypes = new GetQueueTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(queueTypes == null) {
            queueTypeObjects = emptyList();
        } else {
            queueTypeObjects = new ArrayList<>(queueTypes.size());

            queueTypes.stream()
                    .map(QueueTypeObject::new)
                    .forEachOrdered(queueTypeObjects::add);
        }

        return queueTypeObjects;
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUse")
    public static UnitOfMeasureKindUseObject unitOfMeasureKindUse(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") @GraphQLNonNull final String unitOfMeasureKindUseTypeName,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        UnitOfMeasureKindUse unitOfMeasureKindUse;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUse = new GetUnitOfMeasureKindUseCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return unitOfMeasureKindUse == null ? null : new UnitOfMeasureKindUseObject(unitOfMeasureKindUse);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUses")
    public static Collection<UnitOfMeasureKindUseObject> unitOfMeasureKindUses(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") final String unitOfMeasureKindUseTypeName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName) {
        Collection<UnitOfMeasureKindUse> unitOfMeasureKindUses;
        Collection<UnitOfMeasureKindUseObject> unitOfMeasureKindUseObjects;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUsesForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUses = new GetUnitOfMeasureKindUsesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static UnitOfMeasureTypeObject unitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        UnitOfMeasureType unitOfMeasureType;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypeForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
        
            unitOfMeasureType = new GetUnitOfMeasureTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return unitOfMeasureType == null ? null : new UnitOfMeasureTypeObject(unitOfMeasureType);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureTypes")
    public static Collection<UnitOfMeasureTypeObject> unitOfMeasureTypes(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        Collection<UnitOfMeasureType> unitOfMeasureTypes;
        Collection<UnitOfMeasureTypeObject> unitOfMeasureTypeObjects;
        
        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypesForm();
        
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            
            unitOfMeasureTypes = new GetUnitOfMeasureTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureTypes == null) {
            unitOfMeasureTypeObjects = emptyList();
        } else {
            unitOfMeasureTypeObjects = new ArrayList<>(unitOfMeasureTypes.size());

            unitOfMeasureTypes.stream()
                    .map(UnitOfMeasureTypeObject::new)
                    .forEachOrdered(unitOfMeasureTypeObjects::add);
        }
        
        return unitOfMeasureTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("unitOfMeasureKind")
    public static UnitOfMeasureKindObject unitOfMeasureKind(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        UnitOfMeasureKind unitOfMeasureKind;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
        
            unitOfMeasureKind = new GetUnitOfMeasureKindCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return unitOfMeasureKind == null ? null : new UnitOfMeasureKindObject(unitOfMeasureKind);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKinds")
    public static Collection<UnitOfMeasureKindObject> unitOfMeasureKinds(final DataFetchingEnvironment env) {
        Collection<UnitOfMeasureKind> unitOfMeasureKinds;
        Collection<UnitOfMeasureKindObject> unitOfMeasureKindObjects;
        
        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindsForm();
        
            unitOfMeasureKinds = new GetUnitOfMeasureKindsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureKinds == null) {
            unitOfMeasureKindObjects = emptyList();
        } else {
            unitOfMeasureKindObjects = new ArrayList<>(unitOfMeasureKinds.size());

            unitOfMeasureKinds.stream()
                    .map(UnitOfMeasureKindObject::new)
                    .forEachOrdered(unitOfMeasureKindObjects::add);
        }
        
        return unitOfMeasureKindObjects;
    }
    
    @GraphQLField
    @GraphQLName("unitOfMeasureKindUseType")
    public static UnitOfMeasureKindUseTypeObject unitOfMeasureKindUseType(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") @GraphQLNonNull final String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUseType unitOfMeasureKindUseType;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypeForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
        
            unitOfMeasureKindUseType = new GetUnitOfMeasureKindUseTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return unitOfMeasureKindUseType == null ? null : new UnitOfMeasureKindUseTypeObject(unitOfMeasureKindUseType);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUseTypes")
    public static Collection<UnitOfMeasureKindUseTypeObject> unitOfMeasureKindUseTypes(final DataFetchingEnvironment env) {
        Collection<UnitOfMeasureKindUseType> unitOfMeasureKindUseTypes;
        Collection<UnitOfMeasureKindUseTypeObject> unitOfMeasureKindUseTypeObjects;
        
        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypesForm();
        
            unitOfMeasureKindUseTypes = new GetUnitOfMeasureKindUseTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureKindUseTypes == null) {
            unitOfMeasureKindUseTypeObjects = emptyList();
        } else {
            unitOfMeasureKindUseTypeObjects = new ArrayList<>(unitOfMeasureKindUseTypes.size());

            unitOfMeasureKindUseTypes.stream()
                    .map(UnitOfMeasureKindUseTypeObject::new)
                    .forEachOrdered(unitOfMeasureKindUseTypeObjects::add);
        }
        
        return unitOfMeasureKindUseTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("entityAttributeType")
    public static EntityAttributeTypeObject entityAttributeType(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeTypeName") @GraphQLNonNull final String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypeForm();

            commandForm.setEntityAttributeTypeName(entityAttributeTypeName);
        
            entityAttributeType = new GetEntityAttributeTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return entityAttributeType == null ? null : new EntityAttributeTypeObject(entityAttributeType);
    }

    @GraphQLField
    @GraphQLName("entityAttributeTypes")
    public static Collection<EntityAttributeTypeObject> entityAttributeTypes(final DataFetchingEnvironment env) {
        Collection<EntityAttributeType> entityAttributeTypes;
        Collection<EntityAttributeTypeObject> entityAttributeTypeObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypesForm();
        
            entityAttributeTypes = new GetEntityAttributeTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(entityAttributeTypes == null) {
            entityAttributeTypeObjects = emptyList();
        } else {
            entityAttributeTypeObjects = new ArrayList<>(entityAttributeTypes.size());

            entityAttributeTypes.stream()
                    .map(EntityAttributeTypeObject::new)
                    .forEachOrdered(entityAttributeTypeObjects::add);
        }
        
        return entityAttributeTypeObjects;
    }

    @GraphQLField
    @GraphQLName("customerResults")
    public static CustomerResultsObject customerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        CustomerResultsObject customerResultsObject = new CustomerResultsObject();

        try {
            var commandForm = SearchUtil.getHome().getGetCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetCustomerResultsCommand(getUserVisitPK(env), commandForm).canQueryByGraphQl()) {
                customerResultsObject.setForm(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return customerResultsObject;
    }

    @GraphQLField
    @GraphQLName("employeeResults")
    public static EmployeeResultsObject employeeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        EmployeeResultsObject employeeResultsObject = new EmployeeResultsObject();

        try {
            var commandForm = SearchUtil.getHome().getGetEmployeeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetEmployeeResultsCommand(getUserVisitPK(env), commandForm).canQueryByGraphQl()) {
                employeeResultsObject.setForm(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return employeeResultsObject;
    }

    @GraphQLField
    @GraphQLName("itemResults")
    public static ItemResultsObject itemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        ItemResultsObject itemResultsObject = new ItemResultsObject();

        try {
            var commandForm = SearchUtil.getHome().getGetItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetItemResultsCommand(getUserVisitPK(env), commandForm).canQueryByGraphQl()) {
                itemResultsObject.setForm(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemResultsObject;
    }

    @GraphQLField
    @GraphQLName("checkItemSpelling")
    public static CheckItemSpellingObject checkItemSpelling(final DataFetchingEnvironment env,
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
    public static VendorResultsObject vendorResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        VendorResultsObject vendorResultsObject = new VendorResultsObject();

        try {
            var commandForm = SearchUtil.getHome().getGetVendorResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            if(new GetVendorResultsCommand(getUserVisitPK(env), commandForm).canQueryByGraphQl()) {
                vendorResultsObject.setForm(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return vendorResultsObject;
    }

    @GraphQLField
    @GraphQLName("color")
    public static ColorObject color(final DataFetchingEnvironment env,
            @GraphQLName("colorName") final String colorName,
            @GraphQLName("id") @GraphQLID final String id) {
        Color color;

        try {
            var commandForm = CoreUtil.getHome().getGetColorForm();

            commandForm.setColorName(colorName);
            commandForm.setUlid(id);
        
            color = new GetColorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return color == null ? null : new ColorObject(color);
    }

    @GraphQLField
    @GraphQLName("colors")
    public static Collection<ColorObject> colors(final DataFetchingEnvironment env) {
        Collection<Color> colors;
        Collection<ColorObject> colorObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetColorsForm();
        
            colors = new GetColorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(colors == null) {
            colorObjects = emptyList();
        } else {
            colorObjects = new ArrayList<>(colors.size());

            colors.stream()
                    .map(ColorObject::new)
                    .forEachOrdered(colorObjects::add);
        }
        
        return colorObjects;
    }

    @GraphQLField
    @GraphQLName("fontStyle")
    public static FontStyleObject fontStyle(final DataFetchingEnvironment env,
            @GraphQLName("fontStyleName") final String fontStyleName,
            @GraphQLName("id") @GraphQLID final String id) {
        FontStyle fontStyle;

        try {
            var commandForm = CoreUtil.getHome().getGetFontStyleForm();

            commandForm.setFontStyleName(fontStyleName);
            commandForm.setUlid(id);
        
            fontStyle = new GetFontStyleCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return fontStyle == null ? null : new FontStyleObject(fontStyle);
    }

    @GraphQLField
    @GraphQLName("fontStyles")
    public static Collection<FontStyleObject> fontStyles(final DataFetchingEnvironment env) {
        Collection<FontStyle> fontStyles;
        Collection<FontStyleObject> fontStyleObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetFontStylesForm();
        
            fontStyles = new GetFontStylesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(fontStyles == null) {
            fontStyleObjects = emptyList();
        } else {
            fontStyleObjects = new ArrayList<>(fontStyles.size());

            fontStyles.stream()
                    .map(FontStyleObject::new)
                    .forEachOrdered(fontStyleObjects::add);
        }
        
        return fontStyleObjects;
    }

    @GraphQLField
    @GraphQLName("fontWeight")
    public static FontWeightObject fontWeight(final DataFetchingEnvironment env,
            @GraphQLName("fontWeightName") final String fontWeightName,
            @GraphQLName("id") @GraphQLID final String id) {
        FontWeight fontWeight;

        try {
            var commandForm = CoreUtil.getHome().getGetFontWeightForm();

            commandForm.setFontWeightName(fontWeightName);
            commandForm.setUlid(id);
        
            fontWeight = new GetFontWeightCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return fontWeight == null ? null : new FontWeightObject(fontWeight);
    }

    @GraphQLField
    @GraphQLName("fontWeights")
    public static Collection<FontWeightObject> fontWeights(final DataFetchingEnvironment env) {
        Collection<FontWeight> fontWeights;
        Collection<FontWeightObject> fontWeightObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetFontWeightsForm();
        
            fontWeights = new GetFontWeightsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(fontWeights == null) {
            fontWeightObjects = emptyList();
        } else {
            fontWeightObjects = new ArrayList<>(fontWeights.size());

            fontWeights.stream()
                    .map(FontWeightObject::new)
                    .forEachOrdered(fontWeightObjects::add);
        }
        
        return fontWeightObjects;
    }

    @GraphQLField
    @GraphQLName("textDecoration")
    public static TextDecorationObject textDecoration(final DataFetchingEnvironment env,
            @GraphQLName("textDecorationName") final String textDecorationName,
            @GraphQLName("id") @GraphQLID final String id) {
        TextDecoration textDecoration;

        try {
            var commandForm = CoreUtil.getHome().getGetTextDecorationForm();

            commandForm.setTextDecorationName(textDecorationName);
            commandForm.setUlid(id);
        
            textDecoration = new GetTextDecorationCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return textDecoration == null ? null : new TextDecorationObject(textDecoration);
    }

    @GraphQLField
    @GraphQLName("textDecorations")
    public static Collection<TextDecorationObject> textDecorations(final DataFetchingEnvironment env) {
        Collection<TextDecoration> textDecorations;
        Collection<TextDecorationObject> textDecorationObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetTextDecorationsForm();
        
            textDecorations = new GetTextDecorationsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(textDecorations == null) {
            textDecorationObjects = emptyList();
        } else {
            textDecorationObjects = new ArrayList<>(textDecorations.size());

            textDecorations.stream()
                    .map(TextDecorationObject::new)
                    .forEachOrdered(textDecorationObjects::add);
        }
        
        return textDecorationObjects;
    }

    @GraphQLField
    @GraphQLName("textTransformation")
    public static TextTransformationObject textTransformation(final DataFetchingEnvironment env,
            @GraphQLName("textTransformationName") final String textTransformationName,
            @GraphQLName("id") @GraphQLID final String id) {
        TextTransformation textTransformation;

        try {
            var commandForm = CoreUtil.getHome().getGetTextTransformationForm();

            commandForm.setTextTransformationName(textTransformationName);
            commandForm.setUlid(id);
        
            textTransformation = new GetTextTransformationCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return textTransformation == null ? null : new TextTransformationObject(textTransformation);
    }

    @GraphQLField
    @GraphQLName("textTransformations")
    public static Collection<TextTransformationObject> textTransformations(final DataFetchingEnvironment env) {
        Collection<TextTransformation> textTransformations;
        Collection<TextTransformationObject> textTransformationObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetTextTransformationsForm();
        
            textTransformations = new GetTextTransformationsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(textTransformations == null) {
            textTransformationObjects = emptyList();
        } else {
            textTransformationObjects = new ArrayList<>(textTransformations.size());

            textTransformations.stream()
                    .map(TextTransformationObject::new)
                    .forEachOrdered(textTransformationObjects::add);
        }
        
        return textTransformationObjects;
    }

    @GraphQLField
    @GraphQLName("userLogin")
    public static UserLoginObject userLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") final String username,
            @GraphQLName("id") @GraphQLID final String id) {
        UserLogin userLogin;
        UserLogin foundByUsernameUserLogin;

        try {
            var commandForm = UserUtil.getHome().getGetUserLoginForm();

            commandForm.setUsername(username);
            commandForm.setUlid(id);
        
            GetUserLoginCommand getUserLoginCommand = new GetUserLoginCommand(getUserVisitPK(env), commandForm);
            userLogin = getUserLoginCommand.runForGraphQl();
            foundByUsernameUserLogin = getUserLoginCommand.foundByUsernameUserLogin;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return userLogin == null && foundByUsernameUserLogin == null? null : new UserLoginObject(userLogin, foundByUsernameUserLogin);
    }
    
    @GraphQLField
    @GraphQLName("recoveryQuestion")
    public static RecoveryQuestionObject recoveryQuestion(final DataFetchingEnvironment env,
            @GraphQLName("recoveryQuestionName") final String recoveryQuestionName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") final String username) {
        RecoveryQuestion recoveryQuestion;

        try {
            var commandForm = UserUtil.getHome().getGetRecoveryQuestionForm();

            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setUlid(id);
            commandForm.setUsername(username);
        
            recoveryQuestion = new GetRecoveryQuestionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return recoveryQuestion == null ? null : new RecoveryQuestionObject(recoveryQuestion);
    }

    @GraphQLField
    @GraphQLName("recoveryQuestions")
    public static Collection<RecoveryQuestionObject> recoveryQuestions(final DataFetchingEnvironment env) {
        Collection<RecoveryQuestion> recoveryQuestions;
        Collection<RecoveryQuestionObject> recoveryQuestionObjects;
        
        try {
            var commandForm = UserUtil.getHome().getGetRecoveryQuestionsForm();
        
            recoveryQuestions = new GetRecoveryQuestionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(recoveryQuestions == null) {
            recoveryQuestionObjects = emptyList();
        } else {
            recoveryQuestionObjects = new ArrayList<>(recoveryQuestions.size());

            recoveryQuestions.stream()
                    .map(RecoveryQuestionObject::new)
                    .forEachOrdered(recoveryQuestionObjects::add);
        }
        
        return recoveryQuestionObjects;
    }

    @GraphQLField
    @GraphQLName("userSession")
    public static UserSessionObject userSession(final DataFetchingEnvironment env) {
        var userSession = getUserSession(env);
        
        return userSession == null ? null : new UserSessionObject(userSession);
    }
    
    @GraphQLField
    @GraphQLName("userVisit")
    public static UserVisitObject userVisit(final DataFetchingEnvironment env) {
        var userVisit = getUserVisit(env);

        return userVisit == null ? null : new UserVisitObject(userVisit);
    }
    
    @GraphQLField
    @GraphQLName("currency")
    public static CurrencyObject currency(final DataFetchingEnvironment env,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("id") @GraphQLID final String id) {
        Currency currency;

        try {
            var commandForm = AccountingUtil.getHome().getGetCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUlid(id);
        
            currency = new GetCurrencyCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return currency == null ? null : new CurrencyObject(currency);
    }

    @GraphQLField
    @GraphQLName("currencies")
    public static Collection<CurrencyObject> currencies(final DataFetchingEnvironment env) {
        Collection<Currency> currencies;
        Collection<CurrencyObject> currencyObjects;
        
        try {
            var commandForm = AccountingUtil.getHome().getGetCurrenciesForm();
        
            currencies = new GetCurrenciesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(currencies == null) {
            currencyObjects = emptyList();
        } else {
            currencyObjects = new ArrayList<>(currencies.size());

            currencies.stream()
                    .map(CurrencyObject::new)
                    .forEachOrdered(currencyObjects::add);
        }
        
        return currencyObjects;
    }
    
    @GraphQLField
    @GraphQLName("language")
    public static LanguageObject language(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("id") @GraphQLID final String id) {
        Language language;

        try {
            var commandForm = PartyUtil.getHome().getGetLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setUlid(id);
        
            language = new GetLanguageCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return language == null ? null : new LanguageObject(language);
    }

    @GraphQLField
    @GraphQLName("languages")
    public static Collection<LanguageObject> languages(final DataFetchingEnvironment env) {
        Collection<Language> languages;
        Collection<LanguageObject> languageObjects;
        
        try {
            var commandForm = PartyUtil.getHome().getGetLanguagesForm();
        
            languages = new GetLanguagesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(languages == null) {
            languageObjects = emptyList();
        } else {
            languageObjects = new ArrayList<>(languages.size());

            languages.stream()
                    .map(LanguageObject::new)
                    .forEachOrdered(languageObjects::add);
        }
        
        return languageObjects;
    }

    @GraphQLField
    @GraphQLName("dateTimeFormat")
    public static DateTimeFormatObject dateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") final String dateTimeFormatName,
            @GraphQLName("id") @GraphQLID final String id) {
        DateTimeFormat dateTimeFormat;

        try {
            var commandForm = PartyUtil.getHome().getGetDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);
            commandForm.setUlid(id);
        
            dateTimeFormat = new GetDateTimeFormatCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return dateTimeFormat == null ? null : new DateTimeFormatObject(dateTimeFormat);
    }

    @GraphQLField
    @GraphQLName("dateTimeFormats")
    public static Collection<DateTimeFormatObject> dateTimeFormats(final DataFetchingEnvironment env) {
        Collection<DateTimeFormat> dateTimeFormats;
        Collection<DateTimeFormatObject> dateTimeFormatObjects;
        
        try {
            var commandForm = PartyUtil.getHome().getGetDateTimeFormatsForm();
        
            dateTimeFormats = new GetDateTimeFormatsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(dateTimeFormats == null) {
            dateTimeFormatObjects = emptyList();
        } else {
            dateTimeFormatObjects = new ArrayList<>(dateTimeFormats.size());

            dateTimeFormats.stream()
                    .map(DateTimeFormatObject::new)
                    .forEachOrdered(dateTimeFormatObjects::add);
        }
        
        return dateTimeFormatObjects;
    }

    @GraphQLField
    @GraphQLName("timeZone")
    public static TimeZoneObject timeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") final String javaTimeZoneName,
            @GraphQLName("id") @GraphQLID final String id) {
        TimeZone timeZone;

        try {
            var commandForm = PartyUtil.getHome().getGetTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);
            commandForm.setUlid(id);
        
            timeZone = new GetTimeZoneCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return timeZone == null ? null : new TimeZoneObject(timeZone);
    }

    @GraphQLField
    @GraphQLName("timeZones")
    public static Collection<TimeZoneObject> timeZones(final DataFetchingEnvironment env) {
        Collection<TimeZone> timeZones;
        Collection<TimeZoneObject> timeZoneObjects;
        
        try {
            var commandForm = PartyUtil.getHome().getGetTimeZonesForm();
        
            timeZones = new GetTimeZonesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(timeZones == null) {
            timeZoneObjects = emptyList();
        } else {
            timeZoneObjects = new ArrayList<>(timeZones.size());

            timeZones.stream()
                    .map(TimeZoneObject::new)
                    .forEachOrdered(timeZoneObjects::add);
        }
        
        return timeZoneObjects;
    }

    @GraphQLField
    @GraphQLName("customer")
    public static CustomerObject customer(final DataFetchingEnvironment env,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        Customer customer;

        try {
            var commandForm = CustomerUtil.getHome().getGetCustomerForm();

            commandForm.setCustomerName(customerName);
            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);

            customer = new GetCustomerCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return customer == null ? null : new CustomerObject(customer);
    }

    @GraphQLField
    @GraphQLName("customers")
    public static Collection<CustomerObject> customers(final DataFetchingEnvironment env) {
        Collection<Customer> customers;
        Collection<CustomerObject> customerObjects;

        try {
            var commandForm = CustomerUtil.getHome().getGetCustomersForm();

            customers = new GetCustomersCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(customers == null) {
            customerObjects = emptyList();
        } else {
            customerObjects = new ArrayList<>(customers.size());

            customers.stream()
                    .map(CustomerObject::new)
                    .forEachOrdered(customerObjects::add);
        }

        return customerObjects;
    }

    @GraphQLField
    @GraphQLName("employee")
    public static EmployeeObject employee(final DataFetchingEnvironment env,
            @GraphQLName("employeeName") final String employeeName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyEmployee partyEmployee;

        try {
            var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

            commandForm.setEmployeeName(employeeName);
            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);

            partyEmployee = new GetEmployeeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return partyEmployee == null ? null : new EmployeeObject(partyEmployee);
    }

    @GraphQLField
    @GraphQLName("employees")
    public static Collection<EmployeeObject> employees(final DataFetchingEnvironment env) {
        Collection<PartyEmployee> partyEmployees;
        Collection<EmployeeObject> employeeObjects;

        try {
            var commandForm = EmployeeUtil.getHome().getGetEmployeesForm();

            partyEmployees = new GetEmployeesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(partyEmployees == null) {
            employeeObjects = emptyList();
        } else {
            employeeObjects = new ArrayList<>(partyEmployees.size());

            partyEmployees.stream()
                    .map(EmployeeObject::new)
                    .forEachOrdered(employeeObjects::add);
        }

        return employeeObjects;
    }

    @GraphQLField
    @GraphQLName("vendor")
    public static VendorObject vendor(final DataFetchingEnvironment env,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        Vendor vendor;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorForm();

            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);

            vendor = new GetVendorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return vendor == null ? null : new VendorObject(vendor);
    }

    @GraphQLField
    @GraphQLName("vendors")
    public static Collection<VendorObject> vendors(final DataFetchingEnvironment env) {
        Collection<Vendor> vendors;
        Collection<VendorObject> vendorObjects;

        try {
            var commandForm = VendorUtil.getHome().getGetVendorsForm();

            vendors = new GetVendorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(vendors == null) {
            vendorObjects = emptyList();
        } else {
            vendorObjects = new ArrayList<>(vendors.size());

            vendors.stream()
                    .map(VendorObject::new)
                    .forEachOrdered(vendorObjects::add);
        }

        return vendorObjects;
    }

    @GraphQLField
    @GraphQLName("company")
    public static CompanyObject company(final DataFetchingEnvironment env,
            @GraphQLName("companyName") final String companyName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        PartyCompany company;

        try {
            var commandForm = PartyUtil.getHome().getGetCompanyForm();

            commandForm.setCompanyName(companyName);
            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);

            company = new GetCompanyCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return company == null ? null : new CompanyObject(company);
    }

    @GraphQLField
    @GraphQLName("companies")
    public static Collection<CompanyObject> companies(final DataFetchingEnvironment env) {
        Collection<PartyCompany> partyCompanies;
        Collection<CompanyObject> companyObjects;

        try {
            var commandForm = PartyUtil.getHome().getGetCompaniesForm();

            partyCompanies = new GetCompaniesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(partyCompanies == null) {
            companyObjects = emptyList();
        } else {
            companyObjects = new ArrayList<>(partyCompanies.size());

            partyCompanies.stream()
                    .map(CompanyObject::new)
                    .forEachOrdered(companyObjects::add);
        }

        return companyObjects;
    }

    @GraphQLField
    @GraphQLName("division")
    public static DivisionObject division(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);

            division = new GetDivisionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return division == null ? null : new DivisionObject(division);
    }

    @GraphQLField
    @GraphQLName("divisions")
    public static Collection<DivisionObject> divisions(final DataFetchingEnvironment env,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName) {
        Collection<PartyDivision> partyDivisions;
        Collection<DivisionObject> divisionObjects;

        try {
            var commandForm = PartyUtil.getHome().getGetDivisionsForm();

            commandForm.setCompanyName(companyName);

            partyDivisions = new GetDivisionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static DepartmentObject department(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);

            department = new GetDepartmentCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return department == null ? null : new DepartmentObject(department);
    }

    @GraphQLField
    @GraphQLName("departments")
    public static Collection<DepartmentObject> departments(final DataFetchingEnvironment env,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName,
            @GraphQLName("divisionName") final String divisionName) {
        Collection<PartyDepartment> partyDepartments;
        Collection<DepartmentObject> departmentObjects;

        try {
            var commandForm = PartyUtil.getHome().getGetDepartmentsForm();

            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);

            partyDepartments = new GetDepartmentsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    @GraphQLName("cancellationKind")
    public static CancellationKindObject cancellationKind(final DataFetchingEnvironment env,
            @GraphQLName("cancellationKindName") final String cancellationKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        CancellationKind cancellationKind;

        try {
            var commandForm = CancellationPolicyUtil.getHome().getGetCancellationKindForm();

            commandForm.setCancellationKindName(cancellationKindName);
            commandForm.setUlid(id);

            cancellationKind = new GetCancellationKindCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return cancellationKind == null ? null : new CancellationKindObject(cancellationKind);
    }

    @GraphQLField
    @GraphQLName("cancellationKinds")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<CancellationKindObject> cancellationKinds(final DataFetchingEnvironment env) {
        CountingPaginatedData<CancellationKindObject> data;

        try {
            var cancellationKindControl = Session.getModelController(CancellationPolicyControl.class);
            var totalCount = cancellationKindControl.countCancellationKinds();

            try(var objectLimiter = new ObjectLimiter(env, CancellationKindConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = CancellationPolicyUtil.getHome().getGetCancellationKindsForm();
                var entities = new GetCancellationKindsCommand(getUserVisitPK(env), commandForm).runForGraphQl();

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var cancellationKinds = entities.stream().map(CancellationKindObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                    data = new CountedObjects<>(objectLimiter, cancellationKinds);
                }
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return data;
    }

    @GraphQLField
    @GraphQLName("item")
    public static ItemObject item(final DataFetchingEnvironment env,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemNameOrAlias") final String itemNameOrAlias,
            @GraphQLName("id") @GraphQLID final String id) {
        Item item;

        try {
            var commandForm = ItemUtil.getHome().getGetItemForm();

            commandForm.setItemName(itemName);
            commandForm.setItemNameOrAlias(itemNameOrAlias);
            commandForm.setUlid(id);

            item = new GetItemCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return item == null ? null : new ItemObject(item);
    }

    @GraphQLField
    @GraphQLName("items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<ItemObject> items(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItems();

            try(var objectLimiter = new ObjectLimiter(env, ItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemsForm();
                var entities = new GetItemsCommand(getUserVisitPK(env), commandForm).runForGraphQl();

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
    @GraphQLName("itemDescription")
    public static ItemDescriptionObject itemDescription(final DataFetchingEnvironment env,
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
            commandForm.setUlid(id);
            commandForm.setReferrer(referrer);

            itemDescription = new GetItemDescriptionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDescription == null ? null : new ItemDescriptionObject(itemDescription);
    }

    @GraphQLField
    @GraphQLName("itemDescriptions")
    public static Collection<ItemDescriptionObject> itemDescriptions(final DataFetchingEnvironment env,
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

            itemDescriptions = new GetItemDescriptionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static ItemTypeObject itemType(final DataFetchingEnvironment env,
            @GraphQLName("itemTypeName") final String itemTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemType itemType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemTypeForm();

            commandForm.setItemTypeName(itemTypeName);
            commandForm.setUlid(id);

            itemType = new GetItemTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemType == null ? null : new ItemTypeObject(itemType);
    }

    @GraphQLField
    @GraphQLName("itemTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<ItemTypeObject> itemTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemTypesForm();
                var entities = new GetItemTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemTypes = entities.stream().map(ItemTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

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
    public static ItemUseTypeObject itemUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemUseTypeName") final String itemUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemUseType itemUseType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemUseTypeForm();

            commandForm.setItemUseTypeName(itemUseTypeName);
            commandForm.setUlid(id);

            itemUseType = new GetItemUseTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemUseType == null ? null : new ItemUseTypeObject(itemUseType);
    }

    @GraphQLField
    @GraphQLName("itemUseTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<ItemUseTypeObject> itemUseTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemUseTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemUseTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemUseTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemUseTypesForm();
                var entities = new GetItemUseTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemUseTypes = entities.stream().map(ItemUseTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

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
    public static ItemPriceTypeObject itemPriceType(final DataFetchingEnvironment env,
            @GraphQLName("itemPriceTypeName") final String itemPriceTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemPriceType itemPriceType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemPriceTypeForm();

            commandForm.setItemPriceTypeName(itemPriceTypeName);
            commandForm.setUlid(id);

            itemPriceType = new GetItemPriceTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemPriceType == null ? null : new ItemPriceTypeObject(itemPriceType);
    }

    @GraphQLField
    @GraphQLName("itemPriceTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<ItemPriceTypeObject> itemPriceTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemPriceTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemPriceTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemPriceTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemPriceTypesForm();
                var entities = new GetItemPriceTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemPriceTypes = entities.stream().map(ItemPriceTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

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
    public static ItemDeliveryTypeObject itemDeliveryType(final DataFetchingEnvironment env,
            @GraphQLName("itemDeliveryTypeName") final String itemDeliveryTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemDeliveryType itemDeliveryType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDeliveryTypeForm();

            commandForm.setItemDeliveryTypeName(itemDeliveryTypeName);
            commandForm.setUlid(id);

            itemDeliveryType = new GetItemDeliveryTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDeliveryType == null ? null : new ItemDeliveryTypeObject(itemDeliveryType);
    }

    @GraphQLField
    @GraphQLName("itemDeliveryTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<ItemDeliveryTypeObject> itemDeliveryTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemDeliveryTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemDeliveryTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemDeliveryTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemDeliveryTypesForm();
                var entities = new GetItemDeliveryTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemDeliveryTypes = entities.stream().map(ItemDeliveryTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

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
    public static ItemInventoryTypeObject itemInventoryType(final DataFetchingEnvironment env,
            @GraphQLName("itemInventoryTypeName") final String itemInventoryTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemInventoryType itemInventoryType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemInventoryTypeForm();

            commandForm.setItemInventoryTypeName(itemInventoryTypeName);
            commandForm.setUlid(id);

            itemInventoryType = new GetItemInventoryTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemInventoryType == null ? null : new ItemInventoryTypeObject(itemInventoryType);
    }

    @GraphQLField
    @GraphQLName("itemInventoryTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<ItemInventoryTypeObject> itemInventoryTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemInventoryTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemInventoryTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemInventoryTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemInventoryTypesForm();
                var entities = new GetItemInventoryTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();

                if(entities == null) {
                    data = Connections.emptyConnection();
                } else {
                    var itemInventoryTypes = entities.stream().map(ItemInventoryTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

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
    public static ItemPriceObject itemPrice(final DataFetchingEnvironment env,
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

            itemPrice = new GetItemPriceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemPrice == null ? null : new ItemPriceObject(itemPrice);
    }

    @GraphQLField
    @GraphQLName("itemPrices")
    public static Collection<ItemPriceObject> itemPrices(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        Collection<ItemPrice> itemPrice;
        Collection<ItemPriceObject> itemPriceObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemPricesForm();

            commandForm.setItemName(itemName);

            itemPrice = new GetItemPricesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    @GraphQLName("itemCategory")
    public static ItemCategoryObject itemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemCategory itemCategory;

        try {
            var commandForm = ItemUtil.getHome().getGetItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUlid(id);

            itemCategory = new GetItemCategoryCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemCategory == null ? null : new ItemCategoryObject(itemCategory);
    }

    @GraphQLField
    @GraphQLName("itemCategories")
    public static Collection<ItemCategoryObject> itemCategories(final DataFetchingEnvironment env,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName) {
        Collection<ItemCategory> itemCategories;
        Collection<ItemCategoryObject> itemCategoryObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemCategoriesForm();

            commandForm.setParentItemCategoryName(parentItemCategoryName);

            itemCategories = new GetItemCategoriesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(itemCategories == null) {
            itemCategoryObjects = emptyList();
        } else {
            itemCategoryObjects = new ArrayList<>(itemCategories.size());

            itemCategories.stream()
                    .map(ItemCategoryObject::new)
                    .forEachOrdered(itemCategoryObjects::add);
        }

        return itemCategoryObjects;
    }

    @GraphQLField
    @GraphQLName("itemDescriptionType")
    public static ItemDescriptionTypeObject itemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemDescriptionType itemDescriptionType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setUlid(id);

            itemDescriptionType = new GetItemDescriptionTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemDescriptionType == null ? null : new ItemDescriptionTypeObject(itemDescriptionType);
    }

    @GraphQLField
    @GraphQLName("itemDescriptionTypes")
    public static Collection<ItemDescriptionTypeObject> itemDescriptionTypes(final DataFetchingEnvironment env,
            @GraphQLName("parentItemDescriptionTypeName") final String parentItemDescriptionTypeName) {
        Collection<ItemDescriptionType> itemDescriptionTypes;
        Collection<ItemDescriptionTypeObject> itemDescriptionTypeObjects;

        try {
            var commandForm = ItemUtil.getHome().getGetItemDescriptionTypesForm();

            commandForm.setParentItemDescriptionTypeName(parentItemDescriptionTypeName);

            itemDescriptionTypes = new GetItemDescriptionTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
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
    public static ItemImageTypeObject itemImageType(final DataFetchingEnvironment env,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemImageType itemImageType;

        try {
            var commandForm = ItemUtil.getHome().getGetItemImageTypeForm();

            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setUlid(id);

            itemImageType = new GetItemImageTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return itemImageType == null ? null : new ItemImageTypeObject(itemImageType);
    }

    @GraphQLField
    @GraphQLName("itemImageTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public static CountingPaginatedData<ItemImageTypeObject> itemImageTypes(final DataFetchingEnvironment env) {
        CountingPaginatedData<ItemImageTypeObject> data;

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemImageTypes();

            try(var objectLimiter = new ObjectLimiter(env, ItemImageTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var commandForm = ItemUtil.getHome().getGetItemImageTypesForm();
                var entities = new GetItemImageTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();

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
    @GraphQLName("securityRoleGroup")
    public static SecurityRoleGroupObject securityRoleGroup(final DataFetchingEnvironment env,
            @GraphQLName("securityRoleGroupName") final String securityRoleGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        SecurityRoleGroup securityRoleGroup;

        try {
            var commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupForm();

            commandForm.setSecurityRoleGroupName(securityRoleGroupName);
            commandForm.setUlid(id);

            securityRoleGroup = new GetSecurityRoleGroupCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return securityRoleGroup == null ? null : new SecurityRoleGroupObject(securityRoleGroup);
    }

    @GraphQLField
    @GraphQLName("securityRoleGroups")
    public static Collection<SecurityRoleGroupObject> securityRoleGroups(final DataFetchingEnvironment env,
            @GraphQLName("parentSecurityRoleGroupName") final String parentSecurityRoleGroupName) {
        Collection<SecurityRoleGroup> securityRoleGroups;
        Collection<SecurityRoleGroupObject> securityRoleGroupObjects;

        try {
            var commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupsForm();

            commandForm.setParentSecurityRoleGroupName(parentSecurityRoleGroupName);

            securityRoleGroups = new GetSecurityRoleGroupsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(securityRoleGroups == null) {
            securityRoleGroupObjects = emptyList();
        } else {
            securityRoleGroupObjects = new ArrayList<>(securityRoleGroups.size());

            securityRoleGroups.stream()
                    .map(SecurityRoleGroupObject::new)
                    .forEachOrdered(securityRoleGroupObjects::add);
        }

        return securityRoleGroupObjects;
    }

    @GraphQLField
    @GraphQLName("personalTitles")
    public static Collection<PersonalTitleObject> personalTitles(final DataFetchingEnvironment env) {
        Collection<PersonalTitle> personalTitles;
        
        try {
            var commandForm = PartyUtil.getHome().getGetPersonalTitlesForm();
        
            personalTitles = new GetPersonalTitlesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        Collection<PersonalTitleObject> personalTitleObjects = new ArrayList<>(personalTitles.size());
        
        personalTitles.stream()
                .map(PersonalTitleObject::new)
                .forEachOrdered(personalTitleObjects::add);
        
        return personalTitleObjects;
    }

    @GraphQLField
    @GraphQLName("nameSuffixes")
    public static Collection<NameSuffixObject> nameSuffixes(final DataFetchingEnvironment env) {
        Collection<NameSuffix> nameSuffixes;
        
        try {
            var commandForm = PartyUtil.getHome().getGetNameSuffixesForm();
        
            nameSuffixes = new GetNameSuffixesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        Collection<NameSuffixObject> nameSuffixObjects = new ArrayList<>(nameSuffixes.size());

        nameSuffixes.stream()
                .map(NameSuffixObject::new)
                .forEachOrdered(nameSuffixObjects::add);
        
        return nameSuffixObjects;
    }

}
