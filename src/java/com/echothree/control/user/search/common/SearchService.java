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

package com.echothree.control.user.search.common;

import com.echothree.control.user.search.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface SearchService
        extends SearchForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Search Default Operators
    // --------------------------------------------------------------------------------
    
    CommandResult createSearchDefaultOperator(UserVisitPK userVisitPK, CreateSearchDefaultOperatorForm form);
    
    CommandResult getSearchDefaultOperatorChoices(UserVisitPK userVisitPK, GetSearchDefaultOperatorChoicesForm form);
    
    CommandResult getSearchDefaultOperator(UserVisitPK userVisitPK, GetSearchDefaultOperatorForm form);
    
    CommandResult getSearchDefaultOperators(UserVisitPK userVisitPK, GetSearchDefaultOperatorsForm form);
    
    CommandResult setDefaultSearchDefaultOperator(UserVisitPK userVisitPK, SetDefaultSearchDefaultOperatorForm form);
    
    CommandResult editSearchDefaultOperator(UserVisitPK userVisitPK, EditSearchDefaultOperatorForm form);
    
    CommandResult deleteSearchDefaultOperator(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorForm form);
    
    // --------------------------------------------------------------------------------
    //   Search Default Operator Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSearchDefaultOperatorDescription(UserVisitPK userVisitPK, CreateSearchDefaultOperatorDescriptionForm form);
    
    CommandResult getSearchDefaultOperatorDescription(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionForm form);
    
    CommandResult getSearchDefaultOperatorDescriptions(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionsForm form);
    
    CommandResult editSearchDefaultOperatorDescription(UserVisitPK userVisitPK, EditSearchDefaultOperatorDescriptionForm form);
    
    CommandResult deleteSearchDefaultOperatorDescription(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Search Sort Directions
    // --------------------------------------------------------------------------------
    
    CommandResult createSearchSortDirection(UserVisitPK userVisitPK, CreateSearchSortDirectionForm form);
    
    CommandResult getSearchSortDirectionChoices(UserVisitPK userVisitPK, GetSearchSortDirectionChoicesForm form);
    
    CommandResult getSearchSortDirection(UserVisitPK userVisitPK, GetSearchSortDirectionForm form);
    
    CommandResult getSearchSortDirections(UserVisitPK userVisitPK, GetSearchSortDirectionsForm form);
    
    CommandResult setDefaultSearchSortDirection(UserVisitPK userVisitPK, SetDefaultSearchSortDirectionForm form);
    
    CommandResult editSearchSortDirection(UserVisitPK userVisitPK, EditSearchSortDirectionForm form);
    
    CommandResult deleteSearchSortDirection(UserVisitPK userVisitPK, DeleteSearchSortDirectionForm form);
    
    // --------------------------------------------------------------------------------
    //   Search Sort Direction Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSearchSortDirectionDescription(UserVisitPK userVisitPK, CreateSearchSortDirectionDescriptionForm form);
    
    CommandResult getSearchSortDirectionDescription(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionForm form);
    
    CommandResult getSearchSortDirectionDescriptions(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionsForm form);
    
    CommandResult editSearchSortDirectionDescription(UserVisitPK userVisitPK, EditSearchSortDirectionDescriptionForm form);
    
    CommandResult deleteSearchSortDirectionDescription(UserVisitPK userVisitPK, DeleteSearchSortDirectionDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Search Kinds
    // -------------------------------------------------------------------------

    CommandResult createSearchKind(UserVisitPK userVisitPK, CreateSearchKindForm form);

    CommandResult getSearchKinds(UserVisitPK userVisitPK, GetSearchKindsForm form);

    CommandResult getSearchKind(UserVisitPK userVisitPK, GetSearchKindForm form);

    CommandResult getSearchKindChoices(UserVisitPK userVisitPK, GetSearchKindChoicesForm form);

    CommandResult setDefaultSearchKind(UserVisitPK userVisitPK, SetDefaultSearchKindForm form);

    CommandResult editSearchKind(UserVisitPK userVisitPK, EditSearchKindForm form);

    CommandResult deleteSearchKind(UserVisitPK userVisitPK, DeleteSearchKindForm form);

    // -------------------------------------------------------------------------
    //   Search Kind Descriptions
    // -------------------------------------------------------------------------

    CommandResult createSearchKindDescription(UserVisitPK userVisitPK, CreateSearchKindDescriptionForm form);

    CommandResult getSearchKindDescriptions(UserVisitPK userVisitPK, GetSearchKindDescriptionsForm form);

    CommandResult getSearchKindDescription(UserVisitPK userVisitPK, GetSearchKindDescriptionForm form);

    CommandResult editSearchKindDescription(UserVisitPK userVisitPK, EditSearchKindDescriptionForm form);

    CommandResult deleteSearchKindDescription(UserVisitPK userVisitPK, DeleteSearchKindDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Types
    // -------------------------------------------------------------------------

    CommandResult createSearchType(UserVisitPK userVisitPK, CreateSearchTypeForm form);

    CommandResult getSearchTypes(UserVisitPK userVisitPK, GetSearchTypesForm form);

    CommandResult getSearchType(UserVisitPK userVisitPK, GetSearchTypeForm form);

    CommandResult getSearchTypeChoices(UserVisitPK userVisitPK, GetSearchTypeChoicesForm form);

    CommandResult setDefaultSearchType(UserVisitPK userVisitPK, SetDefaultSearchTypeForm form);

    CommandResult editSearchType(UserVisitPK userVisitPK, EditSearchTypeForm form);

    CommandResult deleteSearchType(UserVisitPK userVisitPK, DeleteSearchTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createSearchTypeDescription(UserVisitPK userVisitPK, CreateSearchTypeDescriptionForm form);

    CommandResult getSearchTypeDescriptions(UserVisitPK userVisitPK, GetSearchTypeDescriptionsForm form);

    CommandResult getSearchTypeDescription(UserVisitPK userVisitPK, GetSearchTypeDescriptionForm form);

    CommandResult editSearchTypeDescription(UserVisitPK userVisitPK, EditSearchTypeDescriptionForm form);

    CommandResult deleteSearchTypeDescription(UserVisitPK userVisitPK, DeleteSearchTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Sort Orders
    // -------------------------------------------------------------------------

    CommandResult createSearchSortOrder(UserVisitPK userVisitPK, CreateSearchSortOrderForm form);

    CommandResult getSearchSortOrders(UserVisitPK userVisitPK, GetSearchSortOrdersForm form);

    CommandResult getSearchSortOrder(UserVisitPK userVisitPK, GetSearchSortOrderForm form);

    CommandResult getSearchSortOrderChoices(UserVisitPK userVisitPK, GetSearchSortOrderChoicesForm form);

    CommandResult setDefaultSearchSortOrder(UserVisitPK userVisitPK, SetDefaultSearchSortOrderForm form);

    CommandResult editSearchSortOrder(UserVisitPK userVisitPK, EditSearchSortOrderForm form);

    CommandResult deleteSearchSortOrder(UserVisitPK userVisitPK, DeleteSearchSortOrderForm form);

    // -------------------------------------------------------------------------
    //   Search Sort Order Descriptions
    // -------------------------------------------------------------------------

    CommandResult createSearchSortOrderDescription(UserVisitPK userVisitPK, CreateSearchSortOrderDescriptionForm form);

    CommandResult getSearchSortOrderDescriptions(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionsForm form);

    CommandResult getSearchSortOrderDescription(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionForm form);

    CommandResult editSearchSortOrderDescription(UserVisitPK userVisitPK, EditSearchSortOrderDescriptionForm form);

    CommandResult deleteSearchSortOrderDescription(UserVisitPK userVisitPK, DeleteSearchSortOrderDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Use Types
    // -------------------------------------------------------------------------

    CommandResult createSearchUseType(UserVisitPK userVisitPK, CreateSearchUseTypeForm form);

    CommandResult getSearchUseTypes(UserVisitPK userVisitPK, GetSearchUseTypesForm form);

    CommandResult getSearchUseType(UserVisitPK userVisitPK, GetSearchUseTypeForm form);

    CommandResult getSearchUseTypeChoices(UserVisitPK userVisitPK, GetSearchUseTypeChoicesForm form);

    CommandResult setDefaultSearchUseType(UserVisitPK userVisitPK, SetDefaultSearchUseTypeForm form);

    CommandResult editSearchUseType(UserVisitPK userVisitPK, EditSearchUseTypeForm form);

    CommandResult deleteSearchUseType(UserVisitPK userVisitPK, DeleteSearchUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createSearchUseTypeDescription(UserVisitPK userVisitPK, CreateSearchUseTypeDescriptionForm form);

    CommandResult getSearchUseTypeDescriptions(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionsForm form);

    CommandResult getSearchUseTypeDescription(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionForm form);

    CommandResult editSearchUseTypeDescription(UserVisitPK userVisitPK, EditSearchUseTypeDescriptionForm form);

    CommandResult deleteSearchUseTypeDescription(UserVisitPK userVisitPK, DeleteSearchUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Result Action Types
    // -------------------------------------------------------------------------

    CommandResult createSearchResultActionType(UserVisitPK userVisitPK, CreateSearchResultActionTypeForm form);

    CommandResult getSearchResultActionTypes(UserVisitPK userVisitPK, GetSearchResultActionTypesForm form);

    CommandResult getSearchResultActionType(UserVisitPK userVisitPK, GetSearchResultActionTypeForm form);

    CommandResult getSearchResultActionTypeChoices(UserVisitPK userVisitPK, GetSearchResultActionTypeChoicesForm form);

    CommandResult setDefaultSearchResultActionType(UserVisitPK userVisitPK, SetDefaultSearchResultActionTypeForm form);

    CommandResult editSearchResultActionType(UserVisitPK userVisitPK, EditSearchResultActionTypeForm form);

    CommandResult deleteSearchResultActionType(UserVisitPK userVisitPK, DeleteSearchResultActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Result Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createSearchResultActionTypeDescription(UserVisitPK userVisitPK, CreateSearchResultActionTypeDescriptionForm form);

    CommandResult getSearchResultActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionsForm form);

    CommandResult getSearchResultActionTypeDescription(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionForm form);

    CommandResult editSearchResultActionTypeDescription(UserVisitPK userVisitPK, EditSearchResultActionTypeDescriptionForm form);

    CommandResult deleteSearchResultActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchResultActionTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Types
    // -------------------------------------------------------------------------

    CommandResult createSearchCheckSpellingActionType(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeForm form);

    CommandResult getSearchCheckSpellingActionTypes(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypesForm form);

    CommandResult getSearchCheckSpellingActionType(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeForm form);

    CommandResult getSearchCheckSpellingActionTypeChoices(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeChoicesForm form);

    CommandResult setDefaultSearchCheckSpellingActionType(UserVisitPK userVisitPK, SetDefaultSearchCheckSpellingActionTypeForm form);

    CommandResult editSearchCheckSpellingActionType(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeForm form);

    CommandResult deleteSearchCheckSpellingActionType(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeDescriptionForm form);

    CommandResult getSearchCheckSpellingActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionsForm form);

    CommandResult getSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionForm form);

    CommandResult editSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeDescriptionForm form);

    CommandResult deleteSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Customer Search
    // -------------------------------------------------------------------------
    
    CommandResult searchCustomers(UserVisitPK userVisitPK, SearchCustomersForm form);
    
    CommandResult getCustomerResults(UserVisitPK userVisitPK, GetCustomerResultsForm form);

    CommandResult getCustomerResultsFacet(UserVisitPK userVisitPK, GetCustomerResultsFacetForm form);

    CommandResult getCustomerResultsFacets(UserVisitPK userVisitPK, GetCustomerResultsFacetsForm form);

    CommandResult countCustomerResults(UserVisitPK userVisitPK, CountCustomerResultsForm form);
    
    CommandResult clearCustomerResults(UserVisitPK userVisitPK, ClearCustomerResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Item Search
    // -------------------------------------------------------------------------

    CommandResult searchItems(UserVisitPK userVisitPK, SearchItemsForm form);
    
    CommandResult getItemResults(UserVisitPK userVisitPK, GetItemResultsForm form);
    
    CommandResult getItemResultsFacet(UserVisitPK userVisitPK, GetItemResultsFacetForm form);
    
    CommandResult getItemResultsFacets(UserVisitPK userVisitPK, GetItemResultsFacetsForm form);
    
    CommandResult countItemResults(UserVisitPK userVisitPK, CountItemResultsForm form);
    
    CommandResult clearItemResults(UserVisitPK userVisitPK, ClearItemResultsForm form);
    
    CommandResult createItemSearchResultAction(UserVisitPK userVisitPK, CreateItemSearchResultActionForm form);
    
    CommandResult checkItemSpelling(UserVisitPK userVisitPK, CheckItemSpellingForm form);
    
    // -------------------------------------------------------------------------
    //   Vendor Search
    // -------------------------------------------------------------------------

    CommandResult searchVendors(UserVisitPK userVisitPK, SearchVendorsForm form);

    CommandResult getVendorResults(UserVisitPK userVisitPK, GetVendorResultsForm form);

    CommandResult countVendorResults(UserVisitPK userVisitPK, CountVendorResultsForm form);

    CommandResult clearVendorResults(UserVisitPK userVisitPK, ClearVendorResultsForm form);

    // -------------------------------------------------------------------------
    //   Forum Message Search
    // -------------------------------------------------------------------------

    CommandResult searchForumMessages(UserVisitPK userVisitPK, SearchForumMessagesForm form);

    CommandResult getForumMessageResults(UserVisitPK userVisitPK, GetForumMessageResultsForm form);

    CommandResult countForumMessageResults(UserVisitPK userVisitPK, CountForumMessageResultsForm form);

    CommandResult clearForumMessageResults(UserVisitPK userVisitPK, ClearForumMessageResultsForm form);

    // -------------------------------------------------------------------------
    //   Employee Search
    // -------------------------------------------------------------------------

    CommandResult searchEmployees(UserVisitPK userVisitPK, SearchEmployeesForm form);

    CommandResult getEmployeeResults(UserVisitPK userVisitPK, GetEmployeeResultsForm form);

    CommandResult getEmployeeResultsFacet(UserVisitPK userVisitPK, GetEmployeeResultsFacetForm form);

    CommandResult getEmployeeResultsFacets(UserVisitPK userVisitPK, GetEmployeeResultsFacetsForm form);

    CommandResult countEmployeeResults(UserVisitPK userVisitPK, CountEmployeeResultsForm form);

    CommandResult clearEmployeeResults(UserVisitPK userVisitPK, ClearEmployeeResultsForm form);

    // -------------------------------------------------------------------------
    //   Leave Search
    // -------------------------------------------------------------------------

    CommandResult searchLeaves(UserVisitPK userVisitPK, SearchLeavesForm form);

    CommandResult getLeaveResults(UserVisitPK userVisitPK, GetLeaveResultsForm form);

    CommandResult countLeaveResults(UserVisitPK userVisitPK, CountLeaveResultsForm form);

    CommandResult clearLeaveResults(UserVisitPK userVisitPK, ClearLeaveResultsForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Batch Search
    // -------------------------------------------------------------------------

    CommandResult searchSalesOrderBatches(UserVisitPK userVisitPK, SearchSalesOrderBatchesForm form);

    CommandResult getSalesOrderBatchResults(UserVisitPK userVisitPK, GetSalesOrderBatchResultsForm form);

    CommandResult countSalesOrderBatchResults(UserVisitPK userVisitPK, CountSalesOrderBatchResultsForm form);

    CommandResult clearSalesOrderBatchResults(UserVisitPK userVisitPK, ClearSalesOrderBatchResultsForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Search
    // -------------------------------------------------------------------------

    CommandResult searchSalesOrders(UserVisitPK userVisitPK, SearchSalesOrdersForm form);

    CommandResult getSalesOrderResults(UserVisitPK userVisitPK, GetSalesOrderResultsForm form);

    CommandResult countSalesOrderResults(UserVisitPK userVisitPK, CountSalesOrderResultsForm form);

    CommandResult clearSalesOrderResults(UserVisitPK userVisitPK, ClearSalesOrderResultsForm form);

    // -------------------------------------------------------------------------
    //   Component Vendor Search
    // -------------------------------------------------------------------------

    CommandResult searchComponentVendors(UserVisitPK userVisitPK, SearchComponentVendorsForm form);

    CommandResult getComponentVendorResults(UserVisitPK userVisitPK, GetComponentVendorResultsForm form);

    CommandResult countComponentVendorResults(UserVisitPK userVisitPK, CountComponentVendorResultsForm form);

    CommandResult clearComponentVendorResults(UserVisitPK userVisitPK, ClearComponentVendorResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Type Search
    // -------------------------------------------------------------------------

    CommandResult searchEntityTypes(UserVisitPK userVisitPK, SearchEntityTypesForm form);

    CommandResult getEntityTypeResults(UserVisitPK userVisitPK, GetEntityTypeResultsForm form);

    CommandResult getEntityTypeResultsFacet(UserVisitPK userVisitPK, GetEntityTypeResultsFacetForm form);

    CommandResult getEntityTypeResultsFacets(UserVisitPK userVisitPK, GetEntityTypeResultsFacetsForm form);

    CommandResult countEntityTypeResults(UserVisitPK userVisitPK, CountEntityTypeResultsForm form);

    CommandResult clearEntityTypeResults(UserVisitPK userVisitPK, ClearEntityTypeResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Type Search
    // -------------------------------------------------------------------------

    CommandResult searchEntityAliasTypes(UserVisitPK userVisitPK, SearchEntityAliasTypesForm form);

    CommandResult getEntityAliasTypeResults(UserVisitPK userVisitPK, GetEntityAliasTypeResultsForm form);

    CommandResult countEntityAliasTypeResults(UserVisitPK userVisitPK, CountEntityAliasTypeResultsForm form);

    CommandResult clearEntityAliasTypeResults(UserVisitPK userVisitPK, ClearEntityAliasTypeResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Attribute Search
    // -------------------------------------------------------------------------

    CommandResult searchEntityAttributes(UserVisitPK userVisitPK, SearchEntityAttributesForm form);

    CommandResult getEntityAttributeResults(UserVisitPK userVisitPK, GetEntityAttributeResultsForm form);

    CommandResult countEntityAttributeResults(UserVisitPK userVisitPK, CountEntityAttributeResultsForm form);

    CommandResult clearEntityAttributeResults(UserVisitPK userVisitPK, ClearEntityAttributeResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Attribute Group Search
    // -------------------------------------------------------------------------

    CommandResult searchEntityAttributeGroups(UserVisitPK userVisitPK, SearchEntityAttributeGroupsForm form);

    CommandResult getEntityAttributeGroupResults(UserVisitPK userVisitPK, GetEntityAttributeGroupResultsForm form);

    CommandResult countEntityAttributeGroupResults(UserVisitPK userVisitPK, CountEntityAttributeGroupResultsForm form);

    CommandResult clearEntityAttributeGroupResults(UserVisitPK userVisitPK, ClearEntityAttributeGroupResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity List Item Search
    // -------------------------------------------------------------------------

    CommandResult searchEntityListItems(UserVisitPK userVisitPK, SearchEntityListItemsForm form);

    CommandResult getEntityListItemResults(UserVisitPK userVisitPK, GetEntityListItemResultsForm form);

    CommandResult countEntityListItemResults(UserVisitPK userVisitPK, CountEntityListItemResultsForm form);

    CommandResult clearEntityListItemResults(UserVisitPK userVisitPK, ClearEntityListItemResultsForm form);

    // -------------------------------------------------------------------------
    //   Content Catalog Search
    // -------------------------------------------------------------------------

    CommandResult searchContentCatalogs(UserVisitPK userVisitPK, SearchContentCatalogsForm form);

    CommandResult getContentCatalogResults(UserVisitPK userVisitPK, GetContentCatalogResultsForm form);

    CommandResult countContentCatalogResults(UserVisitPK userVisitPK, CountContentCatalogResultsForm form);

    CommandResult clearContentCatalogResults(UserVisitPK userVisitPK, ClearContentCatalogResultsForm form);

    // -------------------------------------------------------------------------
    //   Content Catalog Item Search
    // -------------------------------------------------------------------------

    CommandResult searchContentCatalogItems(UserVisitPK userVisitPK, SearchContentCatalogItemsForm form);

    CommandResult getContentCatalogItemResults(UserVisitPK userVisitPK, GetContentCatalogItemResultsForm form);

    CommandResult getContentCatalogItemResultsFacet(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetForm form);

    CommandResult getContentCatalogItemResultsFacets(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetsForm form);

    CommandResult countContentCatalogItemResults(UserVisitPK userVisitPK, CountContentCatalogItemResultsForm form);

    CommandResult clearContentCatalogItemResults(UserVisitPK userVisitPK, ClearContentCatalogItemResultsForm form);

    // -------------------------------------------------------------------------
    //   Content Category Search
    // -------------------------------------------------------------------------

    CommandResult searchContentCategories(UserVisitPK userVisitPK, SearchContentCategoriesForm form);

    CommandResult getContentCategoryResults(UserVisitPK userVisitPK, GetContentCategoryResultsForm form);

    CommandResult getContentCategoryResultsFacet(UserVisitPK userVisitPK, GetContentCategoryResultsFacetForm form);

    CommandResult getContentCategoryResultsFacets(UserVisitPK userVisitPK, GetContentCategoryResultsFacetsForm form);

    CommandResult countContentCategoryResults(UserVisitPK userVisitPK, CountContentCategoryResultsForm form);

    CommandResult clearContentCategoryResults(UserVisitPK userVisitPK, ClearContentCategoryResultsForm form);

    // -------------------------------------------------------------------------
    //   Security Role Group Search
    // -------------------------------------------------------------------------

    CommandResult searchSecurityRoleGroups(UserVisitPK userVisitPK, SearchSecurityRoleGroupsForm form);
    
    CommandResult getSecurityRoleGroupResults(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsForm form);
    
    CommandResult getSecurityRoleGroupResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetForm form);
    
    CommandResult getSecurityRoleGroupResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetsForm form);
    
    CommandResult countSecurityRoleGroupResults(UserVisitPK userVisitPK, CountSecurityRoleGroupResultsForm form);
    
    CommandResult clearSecurityRoleGroupResults(UserVisitPK userVisitPK, ClearSecurityRoleGroupResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Security Role Search
    // -------------------------------------------------------------------------

    CommandResult searchSecurityRoles(UserVisitPK userVisitPK, SearchSecurityRolesForm form);
    
    CommandResult getSecurityRoleResults(UserVisitPK userVisitPK, GetSecurityRoleResultsForm form);
    
    CommandResult getSecurityRoleResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetForm form);
    
    CommandResult getSecurityRoleResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetsForm form);
    
    CommandResult countSecurityRoleResults(UserVisitPK userVisitPK, CountSecurityRoleResultsForm form);
    
    CommandResult clearSecurityRoleResults(UserVisitPK userVisitPK, ClearSecurityRoleResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Search
    // -------------------------------------------------------------------------

    CommandResult searchHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, SearchHarmonizedTariffScheduleCodesForm form);
    
    CommandResult getHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsForm form);
    
    CommandResult getHarmonizedTariffScheduleCodeResultsFacet(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetForm form);
    
    CommandResult getHarmonizedTariffScheduleCodeResultsFacets(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetsForm form);
    
    CommandResult countHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, CountHarmonizedTariffScheduleCodeResultsForm form);
    
    CommandResult clearHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, ClearHarmonizedTariffScheduleCodeResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Contact Mechanism Search
    // -------------------------------------------------------------------------

    CommandResult searchContactMechanisms(UserVisitPK userVisitPK, SearchContactMechanismsForm form);
    
    CommandResult getContactMechanismResults(UserVisitPK userVisitPK, GetContactMechanismResultsForm form);
    
    CommandResult getContactMechanismResultsFacet(UserVisitPK userVisitPK, GetContactMechanismResultsFacetForm form);
    
    CommandResult getContactMechanismResultsFacets(UserVisitPK userVisitPK, GetContactMechanismResultsFacetsForm form);
    
    CommandResult countContactMechanismResults(UserVisitPK userVisitPK, CountContactMechanismResultsForm form);
    
    CommandResult clearContactMechanismResults(UserVisitPK userVisitPK, ClearContactMechanismResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Search
    // -------------------------------------------------------------------------

    CommandResult searchOffers(UserVisitPK userVisitPK, SearchOffersForm form);
    
    CommandResult getOfferResults(UserVisitPK userVisitPK, GetOfferResultsForm form);
    
    CommandResult getOfferResultsFacet(UserVisitPK userVisitPK, GetOfferResultsFacetForm form);
    
    CommandResult getOfferResultsFacets(UserVisitPK userVisitPK, GetOfferResultsFacetsForm form);
    
    CommandResult countOfferResults(UserVisitPK userVisitPK, CountOfferResultsForm form);
    
    CommandResult clearOfferResults(UserVisitPK userVisitPK, ClearOfferResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Use Search
    // -------------------------------------------------------------------------

    CommandResult searchUses(UserVisitPK userVisitPK, SearchUsesForm form);
    
    CommandResult getUseResults(UserVisitPK userVisitPK, GetUseResultsForm form);
    
    CommandResult getUseResultsFacet(UserVisitPK userVisitPK, GetUseResultsFacetForm form);
    
    CommandResult getUseResultsFacets(UserVisitPK userVisitPK, GetUseResultsFacetsForm form);
    
    CommandResult countUseResults(UserVisitPK userVisitPK, CountUseResultsForm form);
    
    CommandResult clearUseResults(UserVisitPK userVisitPK, ClearUseResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Use Type Search
    // -------------------------------------------------------------------------

    CommandResult searchUseTypes(UserVisitPK userVisitPK, SearchUseTypesForm form);
    
    CommandResult getUseTypeResults(UserVisitPK userVisitPK, GetUseTypeResultsForm form);
    
    CommandResult getUseTypeResultsFacet(UserVisitPK userVisitPK, GetUseTypeResultsFacetForm form);
    
    CommandResult getUseTypeResultsFacets(UserVisitPK userVisitPK, GetUseTypeResultsFacetsForm form);
    
    CommandResult countUseTypeResults(UserVisitPK userVisitPK, CountUseTypeResultsForm form);
    
    CommandResult clearUseTypeResults(UserVisitPK userVisitPK, ClearUseTypeResultsForm form);

    // -------------------------------------------------------------------------
    //   Shipping Method Search
    // -------------------------------------------------------------------------

    CommandResult searchShippingMethods(UserVisitPK userVisitPK, SearchShippingMethodsForm form);

    CommandResult getShippingMethodResults(UserVisitPK userVisitPK, GetShippingMethodResultsForm form);

    CommandResult countShippingMethodResults(UserVisitPK userVisitPK, CountShippingMethodResultsForm form);

    CommandResult clearShippingMethodResults(UserVisitPK userVisitPK, ClearShippingMethodResultsForm form);

    // -------------------------------------------------------------------------
    //   Warehouse Search
    // -------------------------------------------------------------------------

    CommandResult searchWarehouses(UserVisitPK userVisitPK, SearchWarehousesForm form);

    CommandResult getWarehouseResults(UserVisitPK userVisitPK, GetWarehouseResultsForm form);

    CommandResult getWarehouseResultsFacet(UserVisitPK userVisitPK, GetWarehouseResultsFacetForm form);

    CommandResult getWarehouseResultsFacets(UserVisitPK userVisitPK, GetWarehouseResultsFacetsForm form);

    CommandResult countWarehouseResults(UserVisitPK userVisitPK, CountWarehouseResultsForm form);

    CommandResult clearWarehouseResults(UserVisitPK userVisitPK, ClearWarehouseResultsForm form);

    // -------------------------------------------------------------------------
    //   Identify
    // -------------------------------------------------------------------------

    CommandResult identify(UserVisitPK userVisitPK, IdentifyForm form);

}
