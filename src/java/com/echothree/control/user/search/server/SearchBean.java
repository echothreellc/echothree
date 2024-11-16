// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.search.server;

import com.echothree.control.user.search.common.SearchRemote;
import com.echothree.control.user.search.common.form.*;
import com.echothree.control.user.search.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class SearchBean
        extends SearchFormsImpl
        implements SearchRemote, SearchLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "SearchBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Search Default Operators
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchDefaultOperator(UserVisitPK userVisitPK, CreateSearchDefaultOperatorForm form) {
        return new CreateSearchDefaultOperatorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorChoices(UserVisitPK userVisitPK, GetSearchDefaultOperatorChoicesForm form) {
        return new GetSearchDefaultOperatorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchDefaultOperator(UserVisitPK userVisitPK, GetSearchDefaultOperatorForm form) {
        return new GetSearchDefaultOperatorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchDefaultOperators(UserVisitPK userVisitPK, GetSearchDefaultOperatorsForm form) {
        return new GetSearchDefaultOperatorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSearchDefaultOperator(UserVisitPK userVisitPK, SetDefaultSearchDefaultOperatorForm form) {
        return new SetDefaultSearchDefaultOperatorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSearchDefaultOperator(UserVisitPK userVisitPK, EditSearchDefaultOperatorForm form) {
        return new EditSearchDefaultOperatorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSearchDefaultOperator(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorForm form) {
        return new DeleteSearchDefaultOperatorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Search Default Operator Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchDefaultOperatorDescription(UserVisitPK userVisitPK, CreateSearchDefaultOperatorDescriptionForm form) {
        return new CreateSearchDefaultOperatorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorDescription(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionForm form) {
        return new GetSearchDefaultOperatorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorDescriptions(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionsForm form) {
        return new GetSearchDefaultOperatorDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSearchDefaultOperatorDescription(UserVisitPK userVisitPK, EditSearchDefaultOperatorDescriptionForm form) {
        return new EditSearchDefaultOperatorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSearchDefaultOperatorDescription(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorDescriptionForm form) {
        return new DeleteSearchDefaultOperatorDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Search Sort Directions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchSortDirection(UserVisitPK userVisitPK, CreateSearchSortDirectionForm form) {
        return new CreateSearchSortDirectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchSortDirectionChoices(UserVisitPK userVisitPK, GetSearchSortDirectionChoicesForm form) {
        return new GetSearchSortDirectionChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchSortDirection(UserVisitPK userVisitPK, GetSearchSortDirectionForm form) {
        return new GetSearchSortDirectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchSortDirections(UserVisitPK userVisitPK, GetSearchSortDirectionsForm form) {
        return new GetSearchSortDirectionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSearchSortDirection(UserVisitPK userVisitPK, SetDefaultSearchSortDirectionForm form) {
        return new SetDefaultSearchSortDirectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSearchSortDirection(UserVisitPK userVisitPK, EditSearchSortDirectionForm form) {
        return new EditSearchSortDirectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSearchSortDirection(UserVisitPK userVisitPK, DeleteSearchSortDirectionForm form) {
        return new DeleteSearchSortDirectionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Search Sort Direction Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchSortDirectionDescription(UserVisitPK userVisitPK, CreateSearchSortDirectionDescriptionForm form) {
        return new CreateSearchSortDirectionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchSortDirectionDescription(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionForm form) {
        return new GetSearchSortDirectionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSearchSortDirectionDescriptions(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionsForm form) {
        return new GetSearchSortDirectionDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSearchSortDirectionDescription(UserVisitPK userVisitPK, EditSearchSortDirectionDescriptionForm form) {
        return new EditSearchSortDirectionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSearchSortDirectionDescription(UserVisitPK userVisitPK, DeleteSearchSortDirectionDescriptionForm form) {
        return new DeleteSearchSortDirectionDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Search Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchKind(UserVisitPK userVisitPK, CreateSearchKindForm form) {
        return new CreateSearchKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchKinds(UserVisitPK userVisitPK, GetSearchKindsForm form) {
        return new GetSearchKindsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchKind(UserVisitPK userVisitPK, GetSearchKindForm form) {
        return new GetSearchKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchKindChoices(UserVisitPK userVisitPK, GetSearchKindChoicesForm form) {
        return new GetSearchKindChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSearchKind(UserVisitPK userVisitPK, SetDefaultSearchKindForm form) {
        return new SetDefaultSearchKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchKind(UserVisitPK userVisitPK, EditSearchKindForm form) {
        return new EditSearchKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchKind(UserVisitPK userVisitPK, DeleteSearchKindForm form) {
        return new DeleteSearchKindCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchKindDescription(UserVisitPK userVisitPK, CreateSearchKindDescriptionForm form) {
        return new CreateSearchKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchKindDescriptions(UserVisitPK userVisitPK, GetSearchKindDescriptionsForm form) {
        return new GetSearchKindDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchKindDescription(UserVisitPK userVisitPK, GetSearchKindDescriptionForm form) {
        return new GetSearchKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchKindDescription(UserVisitPK userVisitPK, EditSearchKindDescriptionForm form) {
        return new EditSearchKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchKindDescription(UserVisitPK userVisitPK, DeleteSearchKindDescriptionForm form) {
        return new DeleteSearchKindDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchType(UserVisitPK userVisitPK, CreateSearchTypeForm form) {
        return new CreateSearchTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchTypes(UserVisitPK userVisitPK, GetSearchTypesForm form) {
        return new GetSearchTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchType(UserVisitPK userVisitPK, GetSearchTypeForm form) {
        return new GetSearchTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchTypeChoices(UserVisitPK userVisitPK, GetSearchTypeChoicesForm form) {
        return new GetSearchTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSearchType(UserVisitPK userVisitPK, SetDefaultSearchTypeForm form) {
        return new SetDefaultSearchTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchType(UserVisitPK userVisitPK, EditSearchTypeForm form) {
        return new EditSearchTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchType(UserVisitPK userVisitPK, DeleteSearchTypeForm form) {
        return new DeleteSearchTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchTypeDescription(UserVisitPK userVisitPK, CreateSearchTypeDescriptionForm form) {
        return new CreateSearchTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchTypeDescriptions(UserVisitPK userVisitPK, GetSearchTypeDescriptionsForm form) {
        return new GetSearchTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchTypeDescription(UserVisitPK userVisitPK, GetSearchTypeDescriptionForm form) {
        return new GetSearchTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchTypeDescription(UserVisitPK userVisitPK, EditSearchTypeDescriptionForm form) {
        return new EditSearchTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchTypeDescription(UserVisitPK userVisitPK, DeleteSearchTypeDescriptionForm form) {
        return new DeleteSearchTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Sort Orders
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchSortOrder(UserVisitPK userVisitPK, CreateSearchSortOrderForm form) {
        return new CreateSearchSortOrderCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchSortOrders(UserVisitPK userVisitPK, GetSearchSortOrdersForm form) {
        return new GetSearchSortOrdersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchSortOrder(UserVisitPK userVisitPK, GetSearchSortOrderForm form) {
        return new GetSearchSortOrderCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchSortOrderChoices(UserVisitPK userVisitPK, GetSearchSortOrderChoicesForm form) {
        return new GetSearchSortOrderChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSearchSortOrder(UserVisitPK userVisitPK, SetDefaultSearchSortOrderForm form) {
        return new SetDefaultSearchSortOrderCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchSortOrder(UserVisitPK userVisitPK, EditSearchSortOrderForm form) {
        return new EditSearchSortOrderCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchSortOrder(UserVisitPK userVisitPK, DeleteSearchSortOrderForm form) {
        return new DeleteSearchSortOrderCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Sort Order Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchSortOrderDescription(UserVisitPK userVisitPK, CreateSearchSortOrderDescriptionForm form) {
        return new CreateSearchSortOrderDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchSortOrderDescriptions(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionsForm form) {
        return new GetSearchSortOrderDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchSortOrderDescription(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionForm form) {
        return new GetSearchSortOrderDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchSortOrderDescription(UserVisitPK userVisitPK, EditSearchSortOrderDescriptionForm form) {
        return new EditSearchSortOrderDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchSortOrderDescription(UserVisitPK userVisitPK, DeleteSearchSortOrderDescriptionForm form) {
        return new DeleteSearchSortOrderDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchUseType(UserVisitPK userVisitPK, CreateSearchUseTypeForm form) {
        return new CreateSearchUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchUseTypes(UserVisitPK userVisitPK, GetSearchUseTypesForm form) {
        return new GetSearchUseTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchUseType(UserVisitPK userVisitPK, GetSearchUseTypeForm form) {
        return new GetSearchUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchUseTypeChoices(UserVisitPK userVisitPK, GetSearchUseTypeChoicesForm form) {
        return new GetSearchUseTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSearchUseType(UserVisitPK userVisitPK, SetDefaultSearchUseTypeForm form) {
        return new SetDefaultSearchUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchUseType(UserVisitPK userVisitPK, EditSearchUseTypeForm form) {
        return new EditSearchUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchUseType(UserVisitPK userVisitPK, DeleteSearchUseTypeForm form) {
        return new DeleteSearchUseTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchUseTypeDescription(UserVisitPK userVisitPK, CreateSearchUseTypeDescriptionForm form) {
        return new CreateSearchUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchUseTypeDescriptions(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionsForm form) {
        return new GetSearchUseTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchUseTypeDescription(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionForm form) {
        return new GetSearchUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchUseTypeDescription(UserVisitPK userVisitPK, EditSearchUseTypeDescriptionForm form) {
        return new EditSearchUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchUseTypeDescription(UserVisitPK userVisitPK, DeleteSearchUseTypeDescriptionForm form) {
        return new DeleteSearchUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Result Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchResultActionType(UserVisitPK userVisitPK, CreateSearchResultActionTypeForm form) {
        return new CreateSearchResultActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchResultActionTypes(UserVisitPK userVisitPK, GetSearchResultActionTypesForm form) {
        return new GetSearchResultActionTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchResultActionType(UserVisitPK userVisitPK, GetSearchResultActionTypeForm form) {
        return new GetSearchResultActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchResultActionTypeChoices(UserVisitPK userVisitPK, GetSearchResultActionTypeChoicesForm form) {
        return new GetSearchResultActionTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSearchResultActionType(UserVisitPK userVisitPK, SetDefaultSearchResultActionTypeForm form) {
        return new SetDefaultSearchResultActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchResultActionType(UserVisitPK userVisitPK, EditSearchResultActionTypeForm form) {
        return new EditSearchResultActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchResultActionType(UserVisitPK userVisitPK, DeleteSearchResultActionTypeForm form) {
        return new DeleteSearchResultActionTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Result Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchResultActionTypeDescription(UserVisitPK userVisitPK, CreateSearchResultActionTypeDescriptionForm form) {
        return new CreateSearchResultActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchResultActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionsForm form) {
        return new GetSearchResultActionTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchResultActionTypeDescription(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionForm form) {
        return new GetSearchResultActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchResultActionTypeDescription(UserVisitPK userVisitPK, EditSearchResultActionTypeDescriptionForm form) {
        return new EditSearchResultActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchResultActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchResultActionTypeDescriptionForm form) {
        return new DeleteSearchResultActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchCheckSpellingActionType(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeForm form) {
        return new CreateSearchCheckSpellingActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypes(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypesForm form) {
        return new GetSearchCheckSpellingActionTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchCheckSpellingActionType(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeForm form) {
        return new GetSearchCheckSpellingActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeChoices(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeChoicesForm form) {
        return new GetSearchCheckSpellingActionTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSearchCheckSpellingActionType(UserVisitPK userVisitPK, SetDefaultSearchCheckSpellingActionTypeForm form) {
        return new SetDefaultSearchCheckSpellingActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchCheckSpellingActionType(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeForm form) {
        return new EditSearchCheckSpellingActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchCheckSpellingActionType(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeForm form) {
        return new DeleteSearchCheckSpellingActionTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeDescriptionForm form) {
        return new CreateSearchCheckSpellingActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionsForm form) {
        return new GetSearchCheckSpellingActionTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionForm form) {
        return new GetSearchCheckSpellingActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeDescriptionForm form) {
        return new EditSearchCheckSpellingActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeDescriptionForm form) {
        return new DeleteSearchCheckSpellingActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Customer Search
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult searchCustomers(UserVisitPK userVisitPK, SearchCustomersForm form) {
        return new SearchCustomersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerResults(UserVisitPK userVisitPK, GetCustomerResultsForm form) {
        return new GetCustomerResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCustomerResultsFacet(UserVisitPK userVisitPK, GetCustomerResultsFacetForm form) {
        return new GetCustomerResultsFacetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCustomerResultsFacets(UserVisitPK userVisitPK, GetCustomerResultsFacetsForm form) {
        return new GetCustomerResultsFacetsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countCustomerResults(UserVisitPK userVisitPK, CountCustomerResultsForm form) {
        return new CountCustomerResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearCustomerResults(UserVisitPK userVisitPK, ClearCustomerResultsForm form) {
        return new ClearCustomerResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchItems(UserVisitPK userVisitPK, SearchItemsForm form) {
        return new SearchItemsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemResults(UserVisitPK userVisitPK, GetItemResultsForm form) {
        return new GetItemResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemResultsFacet(UserVisitPK userVisitPK, GetItemResultsFacetForm form) {
        return new GetItemResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getItemResultsFacets(UserVisitPK userVisitPK, GetItemResultsFacetsForm form) {
        return new GetItemResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countItemResults(UserVisitPK userVisitPK, CountItemResultsForm form) {
        return new CountItemResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearItemResults(UserVisitPK userVisitPK, ClearItemResultsForm form) {
        return new ClearItemResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult createItemSearchResultAction(UserVisitPK userVisitPK, CreateItemSearchResultActionForm form) {
        return new CreateItemSearchResultActionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult checkItemSpelling(UserVisitPK userVisitPK, CheckItemSpellingForm form) {
        return new CheckItemSpellingCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchVendors(UserVisitPK userVisitPK, SearchVendorsForm form) {
        return new SearchVendorsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getVendorResults(UserVisitPK userVisitPK, GetVendorResultsForm form) {
        return new GetVendorResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countVendorResults(UserVisitPK userVisitPK, CountVendorResultsForm form) {
        return new CountVendorResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearVendorResults(UserVisitPK userVisitPK, ClearVendorResultsForm form) {
        return new ClearVendorResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Forum Message Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchForumMessages(UserVisitPK userVisitPK, SearchForumMessagesForm form) {
        return new SearchForumMessagesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getForumMessageResults(UserVisitPK userVisitPK, GetForumMessageResultsForm form) {
        return new GetForumMessageResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countForumMessageResults(UserVisitPK userVisitPK, CountForumMessageResultsForm form) {
        return new CountForumMessageResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearForumMessageResults(UserVisitPK userVisitPK, ClearForumMessageResultsForm form) {
        return new ClearForumMessageResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Employee Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEmployees(UserVisitPK userVisitPK, SearchEmployeesForm form) {
        return new SearchEmployeesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEmployeeResults(UserVisitPK userVisitPK, GetEmployeeResultsForm form) {
        return new GetEmployeeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEmployeeResultsFacet(UserVisitPK userVisitPK, GetEmployeeResultsFacetForm form) {
        return new GetEmployeeResultsFacetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEmployeeResultsFacets(UserVisitPK userVisitPK, GetEmployeeResultsFacetsForm form) {
        return new GetEmployeeResultsFacetsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countEmployeeResults(UserVisitPK userVisitPK, CountEmployeeResultsForm form) {
        return new CountEmployeeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearEmployeeResults(UserVisitPK userVisitPK, ClearEmployeeResultsForm form) {
        return new ClearEmployeeResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Leave Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchLeaves(UserVisitPK userVisitPK, SearchLeavesForm form) {
        return new SearchLeavesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveResults(UserVisitPK userVisitPK, GetLeaveResultsForm form) {
        return new GetLeaveResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countLeaveResults(UserVisitPK userVisitPK, CountLeaveResultsForm form) {
        return new CountLeaveResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearLeaveResults(UserVisitPK userVisitPK, ClearLeaveResultsForm form) {
        return new ClearLeaveResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Sales Order Batch Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSalesOrderBatches(UserVisitPK userVisitPK, SearchSalesOrderBatchesForm form) {
        return new SearchSalesOrderBatchesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSalesOrderBatchResults(UserVisitPK userVisitPK, GetSalesOrderBatchResultsForm form) {
        return new GetSalesOrderBatchResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countSalesOrderBatchResults(UserVisitPK userVisitPK, CountSalesOrderBatchResultsForm form) {
        return new CountSalesOrderBatchResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearSalesOrderBatchResults(UserVisitPK userVisitPK, ClearSalesOrderBatchResultsForm form) {
        return new ClearSalesOrderBatchResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Sales Order Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSalesOrders(UserVisitPK userVisitPK, SearchSalesOrdersForm form) {
        return new SearchSalesOrdersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSalesOrderResults(UserVisitPK userVisitPK, GetSalesOrderResultsForm form) {
        return new GetSalesOrderResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countSalesOrderResults(UserVisitPK userVisitPK, CountSalesOrderResultsForm form) {
        return new CountSalesOrderResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearSalesOrderResults(UserVisitPK userVisitPK, ClearSalesOrderResultsForm form) {
        return new ClearSalesOrderResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Component Vendor Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchComponentVendors(UserVisitPK userVisitPK, SearchComponentVendorsForm form) {
        return new SearchComponentVendorsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getComponentVendorResults(UserVisitPK userVisitPK, GetComponentVendorResultsForm form) {
        return new GetComponentVendorResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countComponentVendorResults(UserVisitPK userVisitPK, CountComponentVendorResultsForm form) {
        return new CountComponentVendorResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearComponentVendorResults(UserVisitPK userVisitPK, ClearComponentVendorResultsForm form) {
        return new ClearComponentVendorResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityTypes(UserVisitPK userVisitPK, SearchEntityTypesForm form) {
        return new SearchEntityTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityTypeResults(UserVisitPK userVisitPK, GetEntityTypeResultsForm form) {
        return new GetEntityTypeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityTypeResultsFacet(UserVisitPK userVisitPK, GetEntityTypeResultsFacetForm form) {
        return new GetEntityTypeResultsFacetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityTypeResultsFacets(UserVisitPK userVisitPK, GetEntityTypeResultsFacetsForm form) {
        return new GetEntityTypeResultsFacetsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countEntityTypeResults(UserVisitPK userVisitPK, CountEntityTypeResultsForm form) {
        return new CountEntityTypeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearEntityTypeResults(UserVisitPK userVisitPK, ClearEntityTypeResultsForm form) {
        return new ClearEntityTypeResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAliasTypes(UserVisitPK userVisitPK, SearchEntityAliasTypesForm form) {
        return new SearchEntityAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAliasTypeResults(UserVisitPK userVisitPK, GetEntityAliasTypeResultsForm form) {
        return new GetEntityAliasTypeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countEntityAliasTypeResults(UserVisitPK userVisitPK, CountEntityAliasTypeResultsForm form) {
        return new CountEntityAliasTypeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearEntityAliasTypeResults(UserVisitPK userVisitPK, ClearEntityAliasTypeResultsForm form) {
        return new ClearEntityAliasTypeResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAttributes(UserVisitPK userVisitPK, SearchEntityAttributesForm form) {
        return new SearchEntityAttributesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAttributeResults(UserVisitPK userVisitPK, GetEntityAttributeResultsForm form) {
        return new GetEntityAttributeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countEntityAttributeResults(UserVisitPK userVisitPK, CountEntityAttributeResultsForm form) {
        return new CountEntityAttributeResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearEntityAttributeResults(UserVisitPK userVisitPK, ClearEntityAttributeResultsForm form) {
        return new ClearEntityAttributeResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity List Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityListItems(UserVisitPK userVisitPK, SearchEntityListItemsForm form) {
        return new SearchEntityListItemsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityListItemResults(UserVisitPK userVisitPK, GetEntityListItemResultsForm form) {
        return new GetEntityListItemResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countEntityListItemResults(UserVisitPK userVisitPK, CountEntityListItemResultsForm form) {
        return new CountEntityListItemResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearEntityListItemResults(UserVisitPK userVisitPK, ClearEntityListItemResultsForm form) {
        return new ClearEntityListItemResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Content Category Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContentCategories(UserVisitPK userVisitPK, SearchContentCategoriesForm form) {
        return new SearchContentCategoriesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategoryResults(UserVisitPK userVisitPK, GetContentCategoryResultsForm form) {
        return new GetContentCategoryResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategoryResultsFacet(UserVisitPK userVisitPK, GetContentCategoryResultsFacetForm form) {
        return new GetContentCategoryResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategoryResultsFacets(UserVisitPK userVisitPK, GetContentCategoryResultsFacetsForm form) {
        return new GetContentCategoryResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countContentCategoryResults(UserVisitPK userVisitPK, CountContentCategoryResultsForm form) {
        return new CountContentCategoryResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearContentCategoryResults(UserVisitPK userVisitPK, ClearContentCategoryResultsForm form) {
        return new ClearContentCategoryResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Group Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSecurityRoleGroups(UserVisitPK userVisitPK, SearchSecurityRoleGroupsForm form) {
        return new SearchSecurityRoleGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResults(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsForm form) {
        return new GetSecurityRoleGroupResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetForm form) {
        return new GetSecurityRoleGroupResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetsForm form) {
        return new GetSecurityRoleGroupResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countSecurityRoleGroupResults(UserVisitPK userVisitPK, CountSecurityRoleGroupResultsForm form) {
        return new CountSecurityRoleGroupResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearSecurityRoleGroupResults(UserVisitPK userVisitPK, ClearSecurityRoleGroupResultsForm form) {
        return new ClearSecurityRoleGroupResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSecurityRoles(UserVisitPK userVisitPK, SearchSecurityRolesForm form) {
        return new SearchSecurityRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleResults(UserVisitPK userVisitPK, GetSecurityRoleResultsForm form) {
        return new GetSecurityRoleResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetForm form) {
        return new GetSecurityRoleResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetsForm form) {
        return new GetSecurityRoleResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countSecurityRoleResults(UserVisitPK userVisitPK, CountSecurityRoleResultsForm form) {
        return new CountSecurityRoleResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearSecurityRoleResults(UserVisitPK userVisitPK, ClearSecurityRoleResultsForm form) {
        return new ClearSecurityRoleResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, SearchHarmonizedTariffScheduleCodesForm form) {
        return new SearchHarmonizedTariffScheduleCodesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsForm form) {
        return new GetHarmonizedTariffScheduleCodeResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResultsFacet(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetForm form) {
        return new GetHarmonizedTariffScheduleCodeResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResultsFacets(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetsForm form) {
        return new GetHarmonizedTariffScheduleCodeResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, CountHarmonizedTariffScheduleCodeResultsForm form) {
        return new CountHarmonizedTariffScheduleCodeResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, ClearHarmonizedTariffScheduleCodeResultsForm form) {
        return new ClearHarmonizedTariffScheduleCodeResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Contact Mechanism Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContactMechanisms(UserVisitPK userVisitPK, SearchContactMechanismsForm form) {
        return new SearchContactMechanismsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismResults(UserVisitPK userVisitPK, GetContactMechanismResultsForm form) {
        return new GetContactMechanismResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismResultsFacet(UserVisitPK userVisitPK, GetContactMechanismResultsFacetForm form) {
        return new GetContactMechanismResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismResultsFacets(UserVisitPK userVisitPK, GetContactMechanismResultsFacetsForm form) {
        return new GetContactMechanismResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countContactMechanismResults(UserVisitPK userVisitPK, CountContactMechanismResultsForm form) {
        return new CountContactMechanismResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearContactMechanismResults(UserVisitPK userVisitPK, ClearContactMechanismResultsForm form) {
        return new ClearContactMechanismResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchOffers(UserVisitPK userVisitPK, SearchOffersForm form) {
        return new SearchOffersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferResults(UserVisitPK userVisitPK, GetOfferResultsForm form) {
        return new GetOfferResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferResultsFacet(UserVisitPK userVisitPK, GetOfferResultsFacetForm form) {
        return new GetOfferResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferResultsFacets(UserVisitPK userVisitPK, GetOfferResultsFacetsForm form) {
        return new GetOfferResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countOfferResults(UserVisitPK userVisitPK, CountOfferResultsForm form) {
        return new CountOfferResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearOfferResults(UserVisitPK userVisitPK, ClearOfferResultsForm form) {
        return new ClearOfferResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Use Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchUses(UserVisitPK userVisitPK, SearchUsesForm form) {
        return new SearchUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseResults(UserVisitPK userVisitPK, GetUseResultsForm form) {
        return new GetUseResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseResultsFacet(UserVisitPK userVisitPK, GetUseResultsFacetForm form) {
        return new GetUseResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseResultsFacets(UserVisitPK userVisitPK, GetUseResultsFacetsForm form) {
        return new GetUseResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countUseResults(UserVisitPK userVisitPK, CountUseResultsForm form) {
        return new CountUseResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearUseResults(UserVisitPK userVisitPK, ClearUseResultsForm form) {
        return new ClearUseResultsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Use Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchUseTypes(UserVisitPK userVisitPK, SearchUseTypesForm form) {
        return new SearchUseTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseTypeResults(UserVisitPK userVisitPK, GetUseTypeResultsForm form) {
        return new GetUseTypeResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseTypeResultsFacet(UserVisitPK userVisitPK, GetUseTypeResultsFacetForm form) {
        return new GetUseTypeResultsFacetCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseTypeResultsFacets(UserVisitPK userVisitPK, GetUseTypeResultsFacetsForm form) {
        return new GetUseTypeResultsFacetsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult countUseTypeResults(UserVisitPK userVisitPK, CountUseTypeResultsForm form) {
        return new CountUseTypeResultsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult clearUseTypeResults(UserVisitPK userVisitPK, ClearUseTypeResultsForm form) {
        return new ClearUseTypeResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Warehouse Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchWarehouses(UserVisitPK userVisitPK, SearchWarehousesForm form) {
        return new SearchWarehousesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getWarehouseResults(UserVisitPK userVisitPK, GetWarehouseResultsForm form) {
        return new GetWarehouseResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getWarehouseResultsFacet(UserVisitPK userVisitPK, GetWarehouseResultsFacetForm form) {
        return new GetWarehouseResultsFacetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getWarehouseResultsFacets(UserVisitPK userVisitPK, GetWarehouseResultsFacetsForm form) {
        return new GetWarehouseResultsFacetsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult countWarehouseResults(UserVisitPK userVisitPK, CountWarehouseResultsForm form) {
        return new CountWarehouseResultsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult clearWarehouseResults(UserVisitPK userVisitPK, ClearWarehouseResultsForm form) {
        return new ClearWarehouseResultsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Identify
    // -------------------------------------------------------------------------

    @Override
    public CommandResult identify(UserVisitPK userVisitPK, IdentifyForm form) {
        return new IdentifyCommand(userVisitPK, form).run();
    }

}
