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
import com.echothree.control.user.search.common.result.*;
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
    
    CommandResult<?> createSearchDefaultOperator(UserVisitPK userVisitPK, CreateSearchDefaultOperatorForm form);
    
    CommandResult<GetSearchDefaultOperatorChoicesResult> getSearchDefaultOperatorChoices(UserVisitPK userVisitPK, GetSearchDefaultOperatorChoicesForm form);
    
    CommandResult<GetSearchDefaultOperatorResult> getSearchDefaultOperator(UserVisitPK userVisitPK, GetSearchDefaultOperatorForm form);
    
    CommandResult<GetSearchDefaultOperatorsResult> getSearchDefaultOperators(UserVisitPK userVisitPK, GetSearchDefaultOperatorsForm form);
    
    CommandResult<?> setDefaultSearchDefaultOperator(UserVisitPK userVisitPK, SetDefaultSearchDefaultOperatorForm form);
    
    CommandResult<EditSearchDefaultOperatorResult> editSearchDefaultOperator(UserVisitPK userVisitPK, EditSearchDefaultOperatorForm form);
    
    CommandResult<?> deleteSearchDefaultOperator(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorForm form);
    
    // --------------------------------------------------------------------------------
    //   Search Default Operator Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createSearchDefaultOperatorDescription(UserVisitPK userVisitPK, CreateSearchDefaultOperatorDescriptionForm form);
    
    CommandResult<GetSearchDefaultOperatorDescriptionResult> getSearchDefaultOperatorDescription(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionForm form);
    
    CommandResult<GetSearchDefaultOperatorDescriptionsResult> getSearchDefaultOperatorDescriptions(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionsForm form);
    
    CommandResult<EditSearchDefaultOperatorDescriptionResult> editSearchDefaultOperatorDescription(UserVisitPK userVisitPK, EditSearchDefaultOperatorDescriptionForm form);
    
    CommandResult<?> deleteSearchDefaultOperatorDescription(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Search Sort Directions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createSearchSortDirection(UserVisitPK userVisitPK, CreateSearchSortDirectionForm form);
    
    CommandResult<GetSearchSortDirectionChoicesResult> getSearchSortDirectionChoices(UserVisitPK userVisitPK, GetSearchSortDirectionChoicesForm form);
    
    CommandResult<GetSearchSortDirectionResult> getSearchSortDirection(UserVisitPK userVisitPK, GetSearchSortDirectionForm form);
    
    CommandResult<GetSearchSortDirectionsResult> getSearchSortDirections(UserVisitPK userVisitPK, GetSearchSortDirectionsForm form);
    
    CommandResult<?> setDefaultSearchSortDirection(UserVisitPK userVisitPK, SetDefaultSearchSortDirectionForm form);
    
    CommandResult<EditSearchSortDirectionResult> editSearchSortDirection(UserVisitPK userVisitPK, EditSearchSortDirectionForm form);
    
    CommandResult<?> deleteSearchSortDirection(UserVisitPK userVisitPK, DeleteSearchSortDirectionForm form);
    
    // --------------------------------------------------------------------------------
    //   Search Sort Direction Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createSearchSortDirectionDescription(UserVisitPK userVisitPK, CreateSearchSortDirectionDescriptionForm form);
    
    CommandResult<GetSearchSortDirectionDescriptionResult> getSearchSortDirectionDescription(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionForm form);
    
    CommandResult<GetSearchSortDirectionDescriptionsResult> getSearchSortDirectionDescriptions(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionsForm form);
    
    CommandResult<EditSearchSortDirectionDescriptionResult> editSearchSortDirectionDescription(UserVisitPK userVisitPK, EditSearchSortDirectionDescriptionForm form);
    
    CommandResult<?> deleteSearchSortDirectionDescription(UserVisitPK userVisitPK, DeleteSearchSortDirectionDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Search Kinds
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchKind(UserVisitPK userVisitPK, CreateSearchKindForm form);

    CommandResult<GetSearchKindsResult> getSearchKinds(UserVisitPK userVisitPK, GetSearchKindsForm form);

    CommandResult<GetSearchKindResult> getSearchKind(UserVisitPK userVisitPK, GetSearchKindForm form);

    CommandResult<GetSearchKindChoicesResult> getSearchKindChoices(UserVisitPK userVisitPK, GetSearchKindChoicesForm form);

    CommandResult<?> setDefaultSearchKind(UserVisitPK userVisitPK, SetDefaultSearchKindForm form);

    CommandResult<EditSearchKindResult> editSearchKind(UserVisitPK userVisitPK, EditSearchKindForm form);

    CommandResult<?> deleteSearchKind(UserVisitPK userVisitPK, DeleteSearchKindForm form);

    // -------------------------------------------------------------------------
    //   Search Kind Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchKindDescription(UserVisitPK userVisitPK, CreateSearchKindDescriptionForm form);

    CommandResult<GetSearchKindDescriptionsResult> getSearchKindDescriptions(UserVisitPK userVisitPK, GetSearchKindDescriptionsForm form);

    CommandResult<GetSearchKindDescriptionResult> getSearchKindDescription(UserVisitPK userVisitPK, GetSearchKindDescriptionForm form);

    CommandResult<EditSearchKindDescriptionResult> editSearchKindDescription(UserVisitPK userVisitPK, EditSearchKindDescriptionForm form);

    CommandResult<?> deleteSearchKindDescription(UserVisitPK userVisitPK, DeleteSearchKindDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Types
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchType(UserVisitPK userVisitPK, CreateSearchTypeForm form);

    CommandResult<GetSearchTypesResult> getSearchTypes(UserVisitPK userVisitPK, GetSearchTypesForm form);

    CommandResult<GetSearchTypeResult> getSearchType(UserVisitPK userVisitPK, GetSearchTypeForm form);

    CommandResult<GetSearchTypeChoicesResult> getSearchTypeChoices(UserVisitPK userVisitPK, GetSearchTypeChoicesForm form);

    CommandResult<?> setDefaultSearchType(UserVisitPK userVisitPK, SetDefaultSearchTypeForm form);

    CommandResult<EditSearchTypeResult> editSearchType(UserVisitPK userVisitPK, EditSearchTypeForm form);

    CommandResult<?> deleteSearchType(UserVisitPK userVisitPK, DeleteSearchTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchTypeDescription(UserVisitPK userVisitPK, CreateSearchTypeDescriptionForm form);

    CommandResult<GetSearchTypeDescriptionsResult> getSearchTypeDescriptions(UserVisitPK userVisitPK, GetSearchTypeDescriptionsForm form);

    CommandResult<GetSearchTypeDescriptionResult> getSearchTypeDescription(UserVisitPK userVisitPK, GetSearchTypeDescriptionForm form);

    CommandResult<EditSearchTypeDescriptionResult> editSearchTypeDescription(UserVisitPK userVisitPK, EditSearchTypeDescriptionForm form);

    CommandResult<?> deleteSearchTypeDescription(UserVisitPK userVisitPK, DeleteSearchTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Sort Orders
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchSortOrder(UserVisitPK userVisitPK, CreateSearchSortOrderForm form);

    CommandResult<GetSearchSortOrdersResult> getSearchSortOrders(UserVisitPK userVisitPK, GetSearchSortOrdersForm form);

    CommandResult<GetSearchSortOrderResult> getSearchSortOrder(UserVisitPK userVisitPK, GetSearchSortOrderForm form);

    CommandResult<GetSearchSortOrderChoicesResult> getSearchSortOrderChoices(UserVisitPK userVisitPK, GetSearchSortOrderChoicesForm form);

    CommandResult<?> setDefaultSearchSortOrder(UserVisitPK userVisitPK, SetDefaultSearchSortOrderForm form);

    CommandResult<EditSearchSortOrderResult> editSearchSortOrder(UserVisitPK userVisitPK, EditSearchSortOrderForm form);

    CommandResult<?> deleteSearchSortOrder(UserVisitPK userVisitPK, DeleteSearchSortOrderForm form);

    // -------------------------------------------------------------------------
    //   Search Sort Order Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchSortOrderDescription(UserVisitPK userVisitPK, CreateSearchSortOrderDescriptionForm form);

    CommandResult<GetSearchSortOrderDescriptionsResult> getSearchSortOrderDescriptions(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionsForm form);

    CommandResult<GetSearchSortOrderDescriptionResult> getSearchSortOrderDescription(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionForm form);

    CommandResult<EditSearchSortOrderDescriptionResult> editSearchSortOrderDescription(UserVisitPK userVisitPK, EditSearchSortOrderDescriptionForm form);

    CommandResult<?> deleteSearchSortOrderDescription(UserVisitPK userVisitPK, DeleteSearchSortOrderDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Use Types
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchUseType(UserVisitPK userVisitPK, CreateSearchUseTypeForm form);

    CommandResult<GetSearchUseTypesResult> getSearchUseTypes(UserVisitPK userVisitPK, GetSearchUseTypesForm form);

    CommandResult<GetSearchUseTypeResult> getSearchUseType(UserVisitPK userVisitPK, GetSearchUseTypeForm form);

    CommandResult<GetSearchUseTypeChoicesResult> getSearchUseTypeChoices(UserVisitPK userVisitPK, GetSearchUseTypeChoicesForm form);

    CommandResult<?> setDefaultSearchUseType(UserVisitPK userVisitPK, SetDefaultSearchUseTypeForm form);

    CommandResult<EditSearchUseTypeResult> editSearchUseType(UserVisitPK userVisitPK, EditSearchUseTypeForm form);

    CommandResult<?> deleteSearchUseType(UserVisitPK userVisitPK, DeleteSearchUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchUseTypeDescription(UserVisitPK userVisitPK, CreateSearchUseTypeDescriptionForm form);

    CommandResult<GetSearchUseTypeDescriptionsResult> getSearchUseTypeDescriptions(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionsForm form);

    CommandResult<GetSearchUseTypeDescriptionResult> getSearchUseTypeDescription(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionForm form);

    CommandResult<EditSearchUseTypeDescriptionResult> editSearchUseTypeDescription(UserVisitPK userVisitPK, EditSearchUseTypeDescriptionForm form);

    CommandResult<?> deleteSearchUseTypeDescription(UserVisitPK userVisitPK, DeleteSearchUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Result Action Types
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchResultActionType(UserVisitPK userVisitPK, CreateSearchResultActionTypeForm form);

    CommandResult<GetSearchResultActionTypesResult> getSearchResultActionTypes(UserVisitPK userVisitPK, GetSearchResultActionTypesForm form);

    CommandResult<GetSearchResultActionTypeResult> getSearchResultActionType(UserVisitPK userVisitPK, GetSearchResultActionTypeForm form);

    CommandResult<GetSearchResultActionTypeChoicesResult> getSearchResultActionTypeChoices(UserVisitPK userVisitPK, GetSearchResultActionTypeChoicesForm form);

    CommandResult<?> setDefaultSearchResultActionType(UserVisitPK userVisitPK, SetDefaultSearchResultActionTypeForm form);

    CommandResult<EditSearchResultActionTypeResult> editSearchResultActionType(UserVisitPK userVisitPK, EditSearchResultActionTypeForm form);

    CommandResult<?> deleteSearchResultActionType(UserVisitPK userVisitPK, DeleteSearchResultActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Result Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchResultActionTypeDescription(UserVisitPK userVisitPK, CreateSearchResultActionTypeDescriptionForm form);

    CommandResult<GetSearchResultActionTypeDescriptionsResult> getSearchResultActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionsForm form);

    CommandResult<GetSearchResultActionTypeDescriptionResult> getSearchResultActionTypeDescription(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionForm form);

    CommandResult<EditSearchResultActionTypeDescriptionResult> editSearchResultActionTypeDescription(UserVisitPK userVisitPK, EditSearchResultActionTypeDescriptionForm form);

    CommandResult<?> deleteSearchResultActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchResultActionTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Types
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchCheckSpellingActionType(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeForm form);

    CommandResult<GetSearchCheckSpellingActionTypesResult> getSearchCheckSpellingActionTypes(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypesForm form);

    CommandResult<GetSearchCheckSpellingActionTypeResult> getSearchCheckSpellingActionType(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeForm form);

    CommandResult<GetSearchCheckSpellingActionTypeChoicesResult> getSearchCheckSpellingActionTypeChoices(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeChoicesForm form);

    CommandResult<?> setDefaultSearchCheckSpellingActionType(UserVisitPK userVisitPK, SetDefaultSearchCheckSpellingActionTypeForm form);

    CommandResult<EditSearchCheckSpellingActionTypeResult> editSearchCheckSpellingActionType(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeForm form);

    CommandResult<?> deleteSearchCheckSpellingActionType(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeDescriptionForm form);

    CommandResult<GetSearchCheckSpellingActionTypeDescriptionsResult> getSearchCheckSpellingActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionsForm form);

    CommandResult<GetSearchCheckSpellingActionTypeDescriptionResult> getSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionForm form);

    CommandResult<EditSearchCheckSpellingActionTypeDescriptionResult> editSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeDescriptionForm form);

    CommandResult<?> deleteSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Customer Search
    // -------------------------------------------------------------------------
    
    CommandResult<?> searchCustomers(UserVisitPK userVisitPK, SearchCustomersForm form);
    
    CommandResult<GetCustomerResultsResult> getCustomerResults(UserVisitPK userVisitPK, GetCustomerResultsForm form);

    CommandResult<GetCustomerResultsFacetResult> getCustomerResultsFacet(UserVisitPK userVisitPK, GetCustomerResultsFacetForm form);

    CommandResult<GetCustomerResultsFacetsResult> getCustomerResultsFacets(UserVisitPK userVisitPK, GetCustomerResultsFacetsForm form);

    CommandResult<?> countCustomerResults(UserVisitPK userVisitPK, CountCustomerResultsForm form);
    
    CommandResult<?> clearCustomerResults(UserVisitPK userVisitPK, ClearCustomerResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Item Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchItems(UserVisitPK userVisitPK, SearchItemsForm form);
    
    CommandResult<GetItemResultsResult> getItemResults(UserVisitPK userVisitPK, GetItemResultsForm form);
    
    CommandResult<GetItemResultsFacetResult> getItemResultsFacet(UserVisitPK userVisitPK, GetItemResultsFacetForm form);
    
    CommandResult<GetItemResultsFacetsResult> getItemResultsFacets(UserVisitPK userVisitPK, GetItemResultsFacetsForm form);
    
    CommandResult<?> countItemResults(UserVisitPK userVisitPK, CountItemResultsForm form);
    
    CommandResult<?> clearItemResults(UserVisitPK userVisitPK, ClearItemResultsForm form);
    
    CommandResult<?> createItemSearchResultAction(UserVisitPK userVisitPK, CreateItemSearchResultActionForm form);
    
    CommandResult<?> checkItemSpelling(UserVisitPK userVisitPK, CheckItemSpellingForm form);
    
    // -------------------------------------------------------------------------
    //   Vendor Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchVendors(UserVisitPK userVisitPK, SearchVendorsForm form);

    CommandResult<GetVendorResultsResult> getVendorResults(UserVisitPK userVisitPK, GetVendorResultsForm form);

    CommandResult<?> countVendorResults(UserVisitPK userVisitPK, CountVendorResultsForm form);

    CommandResult<?> clearVendorResults(UserVisitPK userVisitPK, ClearVendorResultsForm form);

    // -------------------------------------------------------------------------
    //   Forum Message Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchForumMessages(UserVisitPK userVisitPK, SearchForumMessagesForm form);

    CommandResult<GetForumMessageResultsResult> getForumMessageResults(UserVisitPK userVisitPK, GetForumMessageResultsForm form);

    CommandResult<?> countForumMessageResults(UserVisitPK userVisitPK, CountForumMessageResultsForm form);

    CommandResult<?> clearForumMessageResults(UserVisitPK userVisitPK, ClearForumMessageResultsForm form);

    // -------------------------------------------------------------------------
    //   Employee Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchEmployees(UserVisitPK userVisitPK, SearchEmployeesForm form);

    CommandResult<GetEmployeeResultsResult> getEmployeeResults(UserVisitPK userVisitPK, GetEmployeeResultsForm form);

    CommandResult<GetEmployeeResultsFacetResult> getEmployeeResultsFacet(UserVisitPK userVisitPK, GetEmployeeResultsFacetForm form);

    CommandResult<GetEmployeeResultsFacetsResult> getEmployeeResultsFacets(UserVisitPK userVisitPK, GetEmployeeResultsFacetsForm form);

    CommandResult<?> countEmployeeResults(UserVisitPK userVisitPK, CountEmployeeResultsForm form);

    CommandResult<?> clearEmployeeResults(UserVisitPK userVisitPK, ClearEmployeeResultsForm form);

    // -------------------------------------------------------------------------
    //   Leave Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchLeaves(UserVisitPK userVisitPK, SearchLeavesForm form);

    CommandResult<GetLeaveResultsResult> getLeaveResults(UserVisitPK userVisitPK, GetLeaveResultsForm form);

    CommandResult<?> countLeaveResults(UserVisitPK userVisitPK, CountLeaveResultsForm form);

    CommandResult<?> clearLeaveResults(UserVisitPK userVisitPK, ClearLeaveResultsForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Batch Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchSalesOrderBatches(UserVisitPK userVisitPK, SearchSalesOrderBatchesForm form);

    CommandResult<GetSalesOrderBatchResultsResult> getSalesOrderBatchResults(UserVisitPK userVisitPK, GetSalesOrderBatchResultsForm form);

    CommandResult<?> countSalesOrderBatchResults(UserVisitPK userVisitPK, CountSalesOrderBatchResultsForm form);

    CommandResult<?> clearSalesOrderBatchResults(UserVisitPK userVisitPK, ClearSalesOrderBatchResultsForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchSalesOrders(UserVisitPK userVisitPK, SearchSalesOrdersForm form);

    CommandResult<GetSalesOrderResultsResult> getSalesOrderResults(UserVisitPK userVisitPK, GetSalesOrderResultsForm form);

    CommandResult<?> countSalesOrderResults(UserVisitPK userVisitPK, CountSalesOrderResultsForm form);

    CommandResult<?> clearSalesOrderResults(UserVisitPK userVisitPK, ClearSalesOrderResultsForm form);

    // -------------------------------------------------------------------------
    //   Component Vendor Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchComponentVendors(UserVisitPK userVisitPK, SearchComponentVendorsForm form);

    CommandResult<GetComponentVendorResultsResult> getComponentVendorResults(UserVisitPK userVisitPK, GetComponentVendorResultsForm form);

    CommandResult<?> countComponentVendorResults(UserVisitPK userVisitPK, CountComponentVendorResultsForm form);

    CommandResult<?> clearComponentVendorResults(UserVisitPK userVisitPK, ClearComponentVendorResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Type Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchEntityTypes(UserVisitPK userVisitPK, SearchEntityTypesForm form);

    CommandResult<GetEntityTypeResultsResult> getEntityTypeResults(UserVisitPK userVisitPK, GetEntityTypeResultsForm form);

    CommandResult<GetEntityTypeResultsFacetResult> getEntityTypeResultsFacet(UserVisitPK userVisitPK, GetEntityTypeResultsFacetForm form);

    CommandResult<GetEntityTypeResultsFacetsResult> getEntityTypeResultsFacets(UserVisitPK userVisitPK, GetEntityTypeResultsFacetsForm form);

    CommandResult<?> countEntityTypeResults(UserVisitPK userVisitPK, CountEntityTypeResultsForm form);

    CommandResult<?> clearEntityTypeResults(UserVisitPK userVisitPK, ClearEntityTypeResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Type Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchEntityAliasTypes(UserVisitPK userVisitPK, SearchEntityAliasTypesForm form);

    CommandResult<GetEntityAliasTypeResultsResult> getEntityAliasTypeResults(UserVisitPK userVisitPK, GetEntityAliasTypeResultsForm form);

    CommandResult<?> countEntityAliasTypeResults(UserVisitPK userVisitPK, CountEntityAliasTypeResultsForm form);

    CommandResult<?> clearEntityAliasTypeResults(UserVisitPK userVisitPK, ClearEntityAliasTypeResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Attribute Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchEntityAttributes(UserVisitPK userVisitPK, SearchEntityAttributesForm form);

    CommandResult<GetEntityAttributeResultsResult> getEntityAttributeResults(UserVisitPK userVisitPK, GetEntityAttributeResultsForm form);

    CommandResult<?> countEntityAttributeResults(UserVisitPK userVisitPK, CountEntityAttributeResultsForm form);

    CommandResult<?> clearEntityAttributeResults(UserVisitPK userVisitPK, ClearEntityAttributeResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity Attribute Group Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchEntityAttributeGroups(UserVisitPK userVisitPK, SearchEntityAttributeGroupsForm form);

    CommandResult<GetEntityAttributeGroupResultsResult> getEntityAttributeGroupResults(UserVisitPK userVisitPK, GetEntityAttributeGroupResultsForm form);

    CommandResult<?> countEntityAttributeGroupResults(UserVisitPK userVisitPK, CountEntityAttributeGroupResultsForm form);

    CommandResult<?> clearEntityAttributeGroupResults(UserVisitPK userVisitPK, ClearEntityAttributeGroupResultsForm form);

    // -------------------------------------------------------------------------
    //   Entity List Item Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchEntityListItems(UserVisitPK userVisitPK, SearchEntityListItemsForm form);

    CommandResult<GetEntityListItemResultsResult> getEntityListItemResults(UserVisitPK userVisitPK, GetEntityListItemResultsForm form);

    CommandResult<?> countEntityListItemResults(UserVisitPK userVisitPK, CountEntityListItemResultsForm form);

    CommandResult<?> clearEntityListItemResults(UserVisitPK userVisitPK, ClearEntityListItemResultsForm form);

    // -------------------------------------------------------------------------
    //   Content Catalog Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchContentCatalogs(UserVisitPK userVisitPK, SearchContentCatalogsForm form);

    CommandResult<GetContentCatalogResultsResult> getContentCatalogResults(UserVisitPK userVisitPK, GetContentCatalogResultsForm form);

    CommandResult<?> countContentCatalogResults(UserVisitPK userVisitPK, CountContentCatalogResultsForm form);

    CommandResult<?> clearContentCatalogResults(UserVisitPK userVisitPK, ClearContentCatalogResultsForm form);

    // -------------------------------------------------------------------------
    //   Content Catalog Item Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchContentCatalogItems(UserVisitPK userVisitPK, SearchContentCatalogItemsForm form);

    CommandResult<GetContentCatalogItemResultsResult> getContentCatalogItemResults(UserVisitPK userVisitPK, GetContentCatalogItemResultsForm form);

    CommandResult<GetContentCatalogItemResultsFacetResult> getContentCatalogItemResultsFacet(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetForm form);

    CommandResult<GetContentCatalogItemResultsFacetsResult> getContentCatalogItemResultsFacets(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetsForm form);

    CommandResult<?> countContentCatalogItemResults(UserVisitPK userVisitPK, CountContentCatalogItemResultsForm form);

    CommandResult<?> clearContentCatalogItemResults(UserVisitPK userVisitPK, ClearContentCatalogItemResultsForm form);

    // -------------------------------------------------------------------------
    //   Content Category Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchContentCategories(UserVisitPK userVisitPK, SearchContentCategoriesForm form);

    CommandResult<GetContentCategoryResultsResult> getContentCategoryResults(UserVisitPK userVisitPK, GetContentCategoryResultsForm form);

    CommandResult<GetContentCategoryResultsFacetResult> getContentCategoryResultsFacet(UserVisitPK userVisitPK, GetContentCategoryResultsFacetForm form);

    CommandResult<GetContentCategoryResultsFacetsResult> getContentCategoryResultsFacets(UserVisitPK userVisitPK, GetContentCategoryResultsFacetsForm form);

    CommandResult<?> countContentCategoryResults(UserVisitPK userVisitPK, CountContentCategoryResultsForm form);

    CommandResult<?> clearContentCategoryResults(UserVisitPK userVisitPK, ClearContentCategoryResultsForm form);

    // -------------------------------------------------------------------------
    //   Security Role Group Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchSecurityRoleGroups(UserVisitPK userVisitPK, SearchSecurityRoleGroupsForm form);
    
    CommandResult<GetSecurityRoleGroupResultsResult> getSecurityRoleGroupResults(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsForm form);
    
    CommandResult<GetSecurityRoleGroupResultsFacetResult> getSecurityRoleGroupResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetForm form);
    
    CommandResult<GetSecurityRoleGroupResultsFacetsResult> getSecurityRoleGroupResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetsForm form);
    
    CommandResult<?> countSecurityRoleGroupResults(UserVisitPK userVisitPK, CountSecurityRoleGroupResultsForm form);
    
    CommandResult<?> clearSecurityRoleGroupResults(UserVisitPK userVisitPK, ClearSecurityRoleGroupResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Security Role Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchSecurityRoles(UserVisitPK userVisitPK, SearchSecurityRolesForm form);
    
    CommandResult<GetSecurityRoleResultsResult> getSecurityRoleResults(UserVisitPK userVisitPK, GetSecurityRoleResultsForm form);
    
    CommandResult<GetSecurityRoleResultsFacetResult> getSecurityRoleResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetForm form);
    
    CommandResult<GetSecurityRoleResultsFacetsResult> getSecurityRoleResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetsForm form);
    
    CommandResult<?> countSecurityRoleResults(UserVisitPK userVisitPK, CountSecurityRoleResultsForm form);
    
    CommandResult<?> clearSecurityRoleResults(UserVisitPK userVisitPK, ClearSecurityRoleResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, SearchHarmonizedTariffScheduleCodesForm form);
    
    CommandResult<GetHarmonizedTariffScheduleCodeResultsResult> getHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsForm form);
    
    CommandResult<GetHarmonizedTariffScheduleCodeResultsFacetResult> getHarmonizedTariffScheduleCodeResultsFacet(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetForm form);
    
    CommandResult<GetHarmonizedTariffScheduleCodeResultsFacetsResult> getHarmonizedTariffScheduleCodeResultsFacets(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetsForm form);
    
    CommandResult<?> countHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, CountHarmonizedTariffScheduleCodeResultsForm form);
    
    CommandResult<?> clearHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, ClearHarmonizedTariffScheduleCodeResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Contact Mechanism Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchContactMechanisms(UserVisitPK userVisitPK, SearchContactMechanismsForm form);
    
    CommandResult<GetContactMechanismResultsResult> getContactMechanismResults(UserVisitPK userVisitPK, GetContactMechanismResultsForm form);
    
    CommandResult<GetContactMechanismResultsFacetResult> getContactMechanismResultsFacet(UserVisitPK userVisitPK, GetContactMechanismResultsFacetForm form);
    
    CommandResult<GetContactMechanismResultsFacetsResult> getContactMechanismResultsFacets(UserVisitPK userVisitPK, GetContactMechanismResultsFacetsForm form);
    
    CommandResult<?> countContactMechanismResults(UserVisitPK userVisitPK, CountContactMechanismResultsForm form);
    
    CommandResult<?> clearContactMechanismResults(UserVisitPK userVisitPK, ClearContactMechanismResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchOffers(UserVisitPK userVisitPK, SearchOffersForm form);
    
    CommandResult<GetOfferResultsResult> getOfferResults(UserVisitPK userVisitPK, GetOfferResultsForm form);
    
    CommandResult<GetOfferResultsFacetResult> getOfferResultsFacet(UserVisitPK userVisitPK, GetOfferResultsFacetForm form);
    
    CommandResult<GetOfferResultsFacetsResult> getOfferResultsFacets(UserVisitPK userVisitPK, GetOfferResultsFacetsForm form);
    
    CommandResult<?> countOfferResults(UserVisitPK userVisitPK, CountOfferResultsForm form);
    
    CommandResult<?> clearOfferResults(UserVisitPK userVisitPK, ClearOfferResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Use Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchUses(UserVisitPK userVisitPK, SearchUsesForm form);
    
    CommandResult<GetUseResultsResult> getUseResults(UserVisitPK userVisitPK, GetUseResultsForm form);
    
    CommandResult<GetUseResultsFacetResult> getUseResultsFacet(UserVisitPK userVisitPK, GetUseResultsFacetForm form);
    
    CommandResult<GetUseResultsFacetsResult> getUseResultsFacets(UserVisitPK userVisitPK, GetUseResultsFacetsForm form);
    
    CommandResult<?> countUseResults(UserVisitPK userVisitPK, CountUseResultsForm form);
    
    CommandResult<?> clearUseResults(UserVisitPK userVisitPK, ClearUseResultsForm form);
    
    // -------------------------------------------------------------------------
    //   Use Type Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchUseTypes(UserVisitPK userVisitPK, SearchUseTypesForm form);
    
    CommandResult<GetUseTypeResultsResult> getUseTypeResults(UserVisitPK userVisitPK, GetUseTypeResultsForm form);
    
    CommandResult<GetUseTypeResultsFacetResult> getUseTypeResultsFacet(UserVisitPK userVisitPK, GetUseTypeResultsFacetForm form);
    
    CommandResult<GetUseTypeResultsFacetsResult> getUseTypeResultsFacets(UserVisitPK userVisitPK, GetUseTypeResultsFacetsForm form);
    
    CommandResult<?> countUseTypeResults(UserVisitPK userVisitPK, CountUseTypeResultsForm form);
    
    CommandResult<?> clearUseTypeResults(UserVisitPK userVisitPK, ClearUseTypeResultsForm form);

    // -------------------------------------------------------------------------
    //   Shipping Method Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchShippingMethods(UserVisitPK userVisitPK, SearchShippingMethodsForm form);

    CommandResult<GetShippingMethodResultsResult> getShippingMethodResults(UserVisitPK userVisitPK, GetShippingMethodResultsForm form);

    CommandResult<?> countShippingMethodResults(UserVisitPK userVisitPK, CountShippingMethodResultsForm form);

    CommandResult<?> clearShippingMethodResults(UserVisitPK userVisitPK, ClearShippingMethodResultsForm form);

    // -------------------------------------------------------------------------
    //   Warehouse Search
    // -------------------------------------------------------------------------

    CommandResult<?> searchWarehouses(UserVisitPK userVisitPK, SearchWarehousesForm form);

    CommandResult<GetWarehouseResultsResult> getWarehouseResults(UserVisitPK userVisitPK, GetWarehouseResultsForm form);

    CommandResult<GetWarehouseResultsFacetResult> getWarehouseResultsFacet(UserVisitPK userVisitPK, GetWarehouseResultsFacetForm form);

    CommandResult<GetWarehouseResultsFacetsResult> getWarehouseResultsFacets(UserVisitPK userVisitPK, GetWarehouseResultsFacetsForm form);

    CommandResult<?> countWarehouseResults(UserVisitPK userVisitPK, CountWarehouseResultsForm form);

    CommandResult<?> clearWarehouseResults(UserVisitPK userVisitPK, ClearWarehouseResultsForm form);

    // -------------------------------------------------------------------------
    //   Identify
    // -------------------------------------------------------------------------

    CommandResult<?> identify(UserVisitPK userVisitPK, IdentifyForm form);

}
