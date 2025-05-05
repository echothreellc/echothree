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
        return new CreateSearchDefaultOperatorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorChoices(UserVisitPK userVisitPK, GetSearchDefaultOperatorChoicesForm form) {
        return new GetSearchDefaultOperatorChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperator(UserVisitPK userVisitPK, GetSearchDefaultOperatorForm form) {
        return new GetSearchDefaultOperatorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperators(UserVisitPK userVisitPK, GetSearchDefaultOperatorsForm form) {
        return new GetSearchDefaultOperatorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSearchDefaultOperator(UserVisitPK userVisitPK, SetDefaultSearchDefaultOperatorForm form) {
        return new SetDefaultSearchDefaultOperatorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchDefaultOperator(UserVisitPK userVisitPK, EditSearchDefaultOperatorForm form) {
        return new EditSearchDefaultOperatorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchDefaultOperator(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorForm form) {
        return new DeleteSearchDefaultOperatorCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Search Default Operator Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchDefaultOperatorDescription(UserVisitPK userVisitPK, CreateSearchDefaultOperatorDescriptionForm form) {
        return new CreateSearchDefaultOperatorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorDescription(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionForm form) {
        return new GetSearchDefaultOperatorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorDescriptions(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionsForm form) {
        return new GetSearchDefaultOperatorDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchDefaultOperatorDescription(UserVisitPK userVisitPK, EditSearchDefaultOperatorDescriptionForm form) {
        return new EditSearchDefaultOperatorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchDefaultOperatorDescription(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorDescriptionForm form) {
        return new DeleteSearchDefaultOperatorDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Search Sort Directions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchSortDirection(UserVisitPK userVisitPK, CreateSearchSortDirectionForm form) {
        return new CreateSearchSortDirectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirectionChoices(UserVisitPK userVisitPK, GetSearchSortDirectionChoicesForm form) {
        return new GetSearchSortDirectionChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirection(UserVisitPK userVisitPK, GetSearchSortDirectionForm form) {
        return new GetSearchSortDirectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirections(UserVisitPK userVisitPK, GetSearchSortDirectionsForm form) {
        return new GetSearchSortDirectionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSearchSortDirection(UserVisitPK userVisitPK, SetDefaultSearchSortDirectionForm form) {
        return new SetDefaultSearchSortDirectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchSortDirection(UserVisitPK userVisitPK, EditSearchSortDirectionForm form) {
        return new EditSearchSortDirectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchSortDirection(UserVisitPK userVisitPK, DeleteSearchSortDirectionForm form) {
        return new DeleteSearchSortDirectionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Search Sort Direction Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchSortDirectionDescription(UserVisitPK userVisitPK, CreateSearchSortDirectionDescriptionForm form) {
        return new CreateSearchSortDirectionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirectionDescription(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionForm form) {
        return new GetSearchSortDirectionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirectionDescriptions(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionsForm form) {
        return new GetSearchSortDirectionDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchSortDirectionDescription(UserVisitPK userVisitPK, EditSearchSortDirectionDescriptionForm form) {
        return new EditSearchSortDirectionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchSortDirectionDescription(UserVisitPK userVisitPK, DeleteSearchSortDirectionDescriptionForm form) {
        return new DeleteSearchSortDirectionDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Search Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchKind(UserVisitPK userVisitPK, CreateSearchKindForm form) {
        return new CreateSearchKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKinds(UserVisitPK userVisitPK, GetSearchKindsForm form) {
        return new GetSearchKindsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKind(UserVisitPK userVisitPK, GetSearchKindForm form) {
        return new GetSearchKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKindChoices(UserVisitPK userVisitPK, GetSearchKindChoicesForm form) {
        return new GetSearchKindChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchKind(UserVisitPK userVisitPK, SetDefaultSearchKindForm form) {
        return new SetDefaultSearchKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchKind(UserVisitPK userVisitPK, EditSearchKindForm form) {
        return new EditSearchKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchKind(UserVisitPK userVisitPK, DeleteSearchKindForm form) {
        return new DeleteSearchKindCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchKindDescription(UserVisitPK userVisitPK, CreateSearchKindDescriptionForm form) {
        return new CreateSearchKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKindDescriptions(UserVisitPK userVisitPK, GetSearchKindDescriptionsForm form) {
        return new GetSearchKindDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKindDescription(UserVisitPK userVisitPK, GetSearchKindDescriptionForm form) {
        return new GetSearchKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchKindDescription(UserVisitPK userVisitPK, EditSearchKindDescriptionForm form) {
        return new EditSearchKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchKindDescription(UserVisitPK userVisitPK, DeleteSearchKindDescriptionForm form) {
        return new DeleteSearchKindDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchType(UserVisitPK userVisitPK, CreateSearchTypeForm form) {
        return new CreateSearchTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypes(UserVisitPK userVisitPK, GetSearchTypesForm form) {
        return new GetSearchTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchType(UserVisitPK userVisitPK, GetSearchTypeForm form) {
        return new GetSearchTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypeChoices(UserVisitPK userVisitPK, GetSearchTypeChoicesForm form) {
        return new GetSearchTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchType(UserVisitPK userVisitPK, SetDefaultSearchTypeForm form) {
        return new SetDefaultSearchTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchType(UserVisitPK userVisitPK, EditSearchTypeForm form) {
        return new EditSearchTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchType(UserVisitPK userVisitPK, DeleteSearchTypeForm form) {
        return new DeleteSearchTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchTypeDescription(UserVisitPK userVisitPK, CreateSearchTypeDescriptionForm form) {
        return new CreateSearchTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypeDescriptions(UserVisitPK userVisitPK, GetSearchTypeDescriptionsForm form) {
        return new GetSearchTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypeDescription(UserVisitPK userVisitPK, GetSearchTypeDescriptionForm form) {
        return new GetSearchTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchTypeDescription(UserVisitPK userVisitPK, EditSearchTypeDescriptionForm form) {
        return new EditSearchTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchTypeDescription(UserVisitPK userVisitPK, DeleteSearchTypeDescriptionForm form) {
        return new DeleteSearchTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Sort Orders
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchSortOrder(UserVisitPK userVisitPK, CreateSearchSortOrderForm form) {
        return new CreateSearchSortOrderCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrders(UserVisitPK userVisitPK, GetSearchSortOrdersForm form) {
        return new GetSearchSortOrdersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrder(UserVisitPK userVisitPK, GetSearchSortOrderForm form) {
        return new GetSearchSortOrderCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrderChoices(UserVisitPK userVisitPK, GetSearchSortOrderChoicesForm form) {
        return new GetSearchSortOrderChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchSortOrder(UserVisitPK userVisitPK, SetDefaultSearchSortOrderForm form) {
        return new SetDefaultSearchSortOrderCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchSortOrder(UserVisitPK userVisitPK, EditSearchSortOrderForm form) {
        return new EditSearchSortOrderCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchSortOrder(UserVisitPK userVisitPK, DeleteSearchSortOrderForm form) {
        return new DeleteSearchSortOrderCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Sort Order Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchSortOrderDescription(UserVisitPK userVisitPK, CreateSearchSortOrderDescriptionForm form) {
        return new CreateSearchSortOrderDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrderDescriptions(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionsForm form) {
        return new GetSearchSortOrderDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrderDescription(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionForm form) {
        return new GetSearchSortOrderDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchSortOrderDescription(UserVisitPK userVisitPK, EditSearchSortOrderDescriptionForm form) {
        return new EditSearchSortOrderDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchSortOrderDescription(UserVisitPK userVisitPK, DeleteSearchSortOrderDescriptionForm form) {
        return new DeleteSearchSortOrderDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchUseType(UserVisitPK userVisitPK, CreateSearchUseTypeForm form) {
        return new CreateSearchUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypes(UserVisitPK userVisitPK, GetSearchUseTypesForm form) {
        return new GetSearchUseTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseType(UserVisitPK userVisitPK, GetSearchUseTypeForm form) {
        return new GetSearchUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypeChoices(UserVisitPK userVisitPK, GetSearchUseTypeChoicesForm form) {
        return new GetSearchUseTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchUseType(UserVisitPK userVisitPK, SetDefaultSearchUseTypeForm form) {
        return new SetDefaultSearchUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchUseType(UserVisitPK userVisitPK, EditSearchUseTypeForm form) {
        return new EditSearchUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchUseType(UserVisitPK userVisitPK, DeleteSearchUseTypeForm form) {
        return new DeleteSearchUseTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchUseTypeDescription(UserVisitPK userVisitPK, CreateSearchUseTypeDescriptionForm form) {
        return new CreateSearchUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypeDescriptions(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionsForm form) {
        return new GetSearchUseTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypeDescription(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionForm form) {
        return new GetSearchUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchUseTypeDescription(UserVisitPK userVisitPK, EditSearchUseTypeDescriptionForm form) {
        return new EditSearchUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchUseTypeDescription(UserVisitPK userVisitPK, DeleteSearchUseTypeDescriptionForm form) {
        return new DeleteSearchUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Result Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchResultActionType(UserVisitPK userVisitPK, CreateSearchResultActionTypeForm form) {
        return new CreateSearchResultActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypes(UserVisitPK userVisitPK, GetSearchResultActionTypesForm form) {
        return new GetSearchResultActionTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionType(UserVisitPK userVisitPK, GetSearchResultActionTypeForm form) {
        return new GetSearchResultActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypeChoices(UserVisitPK userVisitPK, GetSearchResultActionTypeChoicesForm form) {
        return new GetSearchResultActionTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchResultActionType(UserVisitPK userVisitPK, SetDefaultSearchResultActionTypeForm form) {
        return new SetDefaultSearchResultActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchResultActionType(UserVisitPK userVisitPK, EditSearchResultActionTypeForm form) {
        return new EditSearchResultActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchResultActionType(UserVisitPK userVisitPK, DeleteSearchResultActionTypeForm form) {
        return new DeleteSearchResultActionTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Result Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchResultActionTypeDescription(UserVisitPK userVisitPK, CreateSearchResultActionTypeDescriptionForm form) {
        return new CreateSearchResultActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionsForm form) {
        return new GetSearchResultActionTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypeDescription(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionForm form) {
        return new GetSearchResultActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchResultActionTypeDescription(UserVisitPK userVisitPK, EditSearchResultActionTypeDescriptionForm form) {
        return new EditSearchResultActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchResultActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchResultActionTypeDescriptionForm form) {
        return new DeleteSearchResultActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchCheckSpellingActionType(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeForm form) {
        return new CreateSearchCheckSpellingActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypes(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypesForm form) {
        return new GetSearchCheckSpellingActionTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionType(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeForm form) {
        return new GetSearchCheckSpellingActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeChoices(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeChoicesForm form) {
        return new GetSearchCheckSpellingActionTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchCheckSpellingActionType(UserVisitPK userVisitPK, SetDefaultSearchCheckSpellingActionTypeForm form) {
        return new SetDefaultSearchCheckSpellingActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchCheckSpellingActionType(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeForm form) {
        return new EditSearchCheckSpellingActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchCheckSpellingActionType(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeForm form) {
        return new DeleteSearchCheckSpellingActionTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeDescriptionForm form) {
        return new CreateSearchCheckSpellingActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionsForm form) {
        return new GetSearchCheckSpellingActionTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionForm form) {
        return new GetSearchCheckSpellingActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeDescriptionForm form) {
        return new EditSearchCheckSpellingActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeDescriptionForm form) {
        return new DeleteSearchCheckSpellingActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Customer Search
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult searchCustomers(UserVisitPK userVisitPK, SearchCustomersForm form) {
        return new SearchCustomersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerResults(UserVisitPK userVisitPK, GetCustomerResultsForm form) {
        return new GetCustomerResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerResultsFacet(UserVisitPK userVisitPK, GetCustomerResultsFacetForm form) {
        return new GetCustomerResultsFacetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerResultsFacets(UserVisitPK userVisitPK, GetCustomerResultsFacetsForm form) {
        return new GetCustomerResultsFacetsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countCustomerResults(UserVisitPK userVisitPK, CountCustomerResultsForm form) {
        return new CountCustomerResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearCustomerResults(UserVisitPK userVisitPK, ClearCustomerResultsForm form) {
        return new ClearCustomerResultsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchItems(UserVisitPK userVisitPK, SearchItemsForm form) {
        return new SearchItemsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemResults(UserVisitPK userVisitPK, GetItemResultsForm form) {
        return new GetItemResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemResultsFacet(UserVisitPK userVisitPK, GetItemResultsFacetForm form) {
        return new GetItemResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemResultsFacets(UserVisitPK userVisitPK, GetItemResultsFacetsForm form) {
        return new GetItemResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countItemResults(UserVisitPK userVisitPK, CountItemResultsForm form) {
        return new CountItemResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearItemResults(UserVisitPK userVisitPK, ClearItemResultsForm form) {
        return new ClearItemResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult createItemSearchResultAction(UserVisitPK userVisitPK, CreateItemSearchResultActionForm form) {
        return new CreateItemSearchResultActionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult checkItemSpelling(UserVisitPK userVisitPK, CheckItemSpellingForm form) {
        return new CheckItemSpellingCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchVendors(UserVisitPK userVisitPK, SearchVendorsForm form) {
        return new SearchVendorsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getVendorResults(UserVisitPK userVisitPK, GetVendorResultsForm form) {
        return new GetVendorResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countVendorResults(UserVisitPK userVisitPK, CountVendorResultsForm form) {
        return new CountVendorResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearVendorResults(UserVisitPK userVisitPK, ClearVendorResultsForm form) {
        return new ClearVendorResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Forum Message Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchForumMessages(UserVisitPK userVisitPK, SearchForumMessagesForm form) {
        return new SearchForumMessagesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageResults(UserVisitPK userVisitPK, GetForumMessageResultsForm form) {
        return new GetForumMessageResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countForumMessageResults(UserVisitPK userVisitPK, CountForumMessageResultsForm form) {
        return new CountForumMessageResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearForumMessageResults(UserVisitPK userVisitPK, ClearForumMessageResultsForm form) {
        return new ClearForumMessageResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Employee Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEmployees(UserVisitPK userVisitPK, SearchEmployeesForm form) {
        return new SearchEmployeesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeResults(UserVisitPK userVisitPK, GetEmployeeResultsForm form) {
        return new GetEmployeeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeResultsFacet(UserVisitPK userVisitPK, GetEmployeeResultsFacetForm form) {
        return new GetEmployeeResultsFacetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeResultsFacets(UserVisitPK userVisitPK, GetEmployeeResultsFacetsForm form) {
        return new GetEmployeeResultsFacetsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEmployeeResults(UserVisitPK userVisitPK, CountEmployeeResultsForm form) {
        return new CountEmployeeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEmployeeResults(UserVisitPK userVisitPK, ClearEmployeeResultsForm form) {
        return new ClearEmployeeResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchLeaves(UserVisitPK userVisitPK, SearchLeavesForm form) {
        return new SearchLeavesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveResults(UserVisitPK userVisitPK, GetLeaveResultsForm form) {
        return new GetLeaveResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countLeaveResults(UserVisitPK userVisitPK, CountLeaveResultsForm form) {
        return new CountLeaveResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearLeaveResults(UserVisitPK userVisitPK, ClearLeaveResultsForm form) {
        return new ClearLeaveResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Batch Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSalesOrderBatches(UserVisitPK userVisitPK, SearchSalesOrderBatchesForm form) {
        return new SearchSalesOrderBatchesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderBatchResults(UserVisitPK userVisitPK, GetSalesOrderBatchResultsForm form) {
        return new GetSalesOrderBatchResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countSalesOrderBatchResults(UserVisitPK userVisitPK, CountSalesOrderBatchResultsForm form) {
        return new CountSalesOrderBatchResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearSalesOrderBatchResults(UserVisitPK userVisitPK, ClearSalesOrderBatchResultsForm form) {
        return new ClearSalesOrderBatchResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSalesOrders(UserVisitPK userVisitPK, SearchSalesOrdersForm form) {
        return new SearchSalesOrdersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderResults(UserVisitPK userVisitPK, GetSalesOrderResultsForm form) {
        return new GetSalesOrderResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countSalesOrderResults(UserVisitPK userVisitPK, CountSalesOrderResultsForm form) {
        return new CountSalesOrderResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearSalesOrderResults(UserVisitPK userVisitPK, ClearSalesOrderResultsForm form) {
        return new ClearSalesOrderResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Component Vendor Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchComponentVendors(UserVisitPK userVisitPK, SearchComponentVendorsForm form) {
        return new SearchComponentVendorsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getComponentVendorResults(UserVisitPK userVisitPK, GetComponentVendorResultsForm form) {
        return new GetComponentVendorResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countComponentVendorResults(UserVisitPK userVisitPK, CountComponentVendorResultsForm form) {
        return new CountComponentVendorResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearComponentVendorResults(UserVisitPK userVisitPK, ClearComponentVendorResultsForm form) {
        return new ClearComponentVendorResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityTypes(UserVisitPK userVisitPK, SearchEntityTypesForm form) {
        return new SearchEntityTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityTypeResults(UserVisitPK userVisitPK, GetEntityTypeResultsForm form) {
        return new GetEntityTypeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityTypeResultsFacet(UserVisitPK userVisitPK, GetEntityTypeResultsFacetForm form) {
        return new GetEntityTypeResultsFacetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityTypeResultsFacets(UserVisitPK userVisitPK, GetEntityTypeResultsFacetsForm form) {
        return new GetEntityTypeResultsFacetsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityTypeResults(UserVisitPK userVisitPK, CountEntityTypeResultsForm form) {
        return new CountEntityTypeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityTypeResults(UserVisitPK userVisitPK, ClearEntityTypeResultsForm form) {
        return new ClearEntityTypeResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAliasTypes(UserVisitPK userVisitPK, SearchEntityAliasTypesForm form) {
        return new SearchEntityAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeResults(UserVisitPK userVisitPK, GetEntityAliasTypeResultsForm form) {
        return new GetEntityAliasTypeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityAliasTypeResults(UserVisitPK userVisitPK, CountEntityAliasTypeResultsForm form) {
        return new CountEntityAliasTypeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityAliasTypeResults(UserVisitPK userVisitPK, ClearEntityAliasTypeResultsForm form) {
        return new ClearEntityAliasTypeResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Group Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAttributeGroups(UserVisitPK userVisitPK, SearchEntityAttributeGroupsForm form) {
        return new SearchEntityAttributeGroupsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAttributeGroupResults(UserVisitPK userVisitPK, GetEntityAttributeGroupResultsForm form) {
        return new GetEntityAttributeGroupResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityAttributeGroupResults(UserVisitPK userVisitPK, CountEntityAttributeGroupResultsForm form) {
        return new CountEntityAttributeGroupResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityAttributeGroupResults(UserVisitPK userVisitPK, ClearEntityAttributeGroupResultsForm form) {
        return new ClearEntityAttributeGroupResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAttributes(UserVisitPK userVisitPK, SearchEntityAttributesForm form) {
        return new SearchEntityAttributesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAttributeResults(UserVisitPK userVisitPK, GetEntityAttributeResultsForm form) {
        return new GetEntityAttributeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityAttributeResults(UserVisitPK userVisitPK, CountEntityAttributeResultsForm form) {
        return new CountEntityAttributeResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityAttributeResults(UserVisitPK userVisitPK, ClearEntityAttributeResultsForm form) {
        return new ClearEntityAttributeResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity List Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityListItems(UserVisitPK userVisitPK, SearchEntityListItemsForm form) {
        return new SearchEntityListItemsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityListItemResults(UserVisitPK userVisitPK, GetEntityListItemResultsForm form) {
        return new GetEntityListItemResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityListItemResults(UserVisitPK userVisitPK, CountEntityListItemResultsForm form) {
        return new CountEntityListItemResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityListItemResults(UserVisitPK userVisitPK, ClearEntityListItemResultsForm form) {
        return new ClearEntityListItemResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Content Catalog Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContentCatalogs(UserVisitPK userVisitPK, SearchContentCatalogsForm form) {
        return new SearchContentCatalogsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogResults(UserVisitPK userVisitPK, GetContentCatalogResultsForm form) {
        return new GetContentCatalogResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countContentCatalogResults(UserVisitPK userVisitPK, CountContentCatalogResultsForm form) {
        return new CountContentCatalogResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearContentCatalogResults(UserVisitPK userVisitPK, ClearContentCatalogResultsForm form) {
        return new ClearContentCatalogResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Content Catalog Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContentCatalogItems(UserVisitPK userVisitPK, SearchContentCatalogItemsForm form) {
        return new SearchContentCatalogItemsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItemResults(UserVisitPK userVisitPK, GetContentCatalogItemResultsForm form) {
        return new GetContentCatalogItemResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItemResultsFacet(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetForm form) {
        return new GetContentCatalogItemResultsFacetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItemResultsFacets(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetsForm form) {
        return new GetContentCatalogItemResultsFacetsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countContentCatalogItemResults(UserVisitPK userVisitPK, CountContentCatalogItemResultsForm form) {
        return new CountContentCatalogItemResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearContentCatalogItemResults(UserVisitPK userVisitPK, ClearContentCatalogItemResultsForm form) {
        return new ClearContentCatalogItemResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Content Category Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContentCategories(UserVisitPK userVisitPK, SearchContentCategoriesForm form) {
        return new SearchContentCategoriesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryResults(UserVisitPK userVisitPK, GetContentCategoryResultsForm form) {
        return new GetContentCategoryResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryResultsFacet(UserVisitPK userVisitPK, GetContentCategoryResultsFacetForm form) {
        return new GetContentCategoryResultsFacetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryResultsFacets(UserVisitPK userVisitPK, GetContentCategoryResultsFacetsForm form) {
        return new GetContentCategoryResultsFacetsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countContentCategoryResults(UserVisitPK userVisitPK, CountContentCategoryResultsForm form) {
        return new CountContentCategoryResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearContentCategoryResults(UserVisitPK userVisitPK, ClearContentCategoryResultsForm form) {
        return new ClearContentCategoryResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Security Role Group Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSecurityRoleGroups(UserVisitPK userVisitPK, SearchSecurityRoleGroupsForm form) {
        return new SearchSecurityRoleGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResults(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsForm form) {
        return new GetSecurityRoleGroupResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetForm form) {
        return new GetSecurityRoleGroupResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetsForm form) {
        return new GetSecurityRoleGroupResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countSecurityRoleGroupResults(UserVisitPK userVisitPK, CountSecurityRoleGroupResultsForm form) {
        return new CountSecurityRoleGroupResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearSecurityRoleGroupResults(UserVisitPK userVisitPK, ClearSecurityRoleGroupResultsForm form) {
        return new ClearSecurityRoleGroupResultsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSecurityRoles(UserVisitPK userVisitPK, SearchSecurityRolesForm form) {
        return new SearchSecurityRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleResults(UserVisitPK userVisitPK, GetSecurityRoleResultsForm form) {
        return new GetSecurityRoleResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetForm form) {
        return new GetSecurityRoleResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetsForm form) {
        return new GetSecurityRoleResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countSecurityRoleResults(UserVisitPK userVisitPK, CountSecurityRoleResultsForm form) {
        return new CountSecurityRoleResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearSecurityRoleResults(UserVisitPK userVisitPK, ClearSecurityRoleResultsForm form) {
        return new ClearSecurityRoleResultsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, SearchHarmonizedTariffScheduleCodesForm form) {
        return new SearchHarmonizedTariffScheduleCodesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsForm form) {
        return new GetHarmonizedTariffScheduleCodeResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResultsFacet(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetForm form) {
        return new GetHarmonizedTariffScheduleCodeResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResultsFacets(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetsForm form) {
        return new GetHarmonizedTariffScheduleCodeResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, CountHarmonizedTariffScheduleCodeResultsForm form) {
        return new CountHarmonizedTariffScheduleCodeResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, ClearHarmonizedTariffScheduleCodeResultsForm form) {
        return new ClearHarmonizedTariffScheduleCodeResultsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Contact Mechanism Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContactMechanisms(UserVisitPK userVisitPK, SearchContactMechanismsForm form) {
        return new SearchContactMechanismsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismResults(UserVisitPK userVisitPK, GetContactMechanismResultsForm form) {
        return new GetContactMechanismResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismResultsFacet(UserVisitPK userVisitPK, GetContactMechanismResultsFacetForm form) {
        return new GetContactMechanismResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismResultsFacets(UserVisitPK userVisitPK, GetContactMechanismResultsFacetsForm form) {
        return new GetContactMechanismResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countContactMechanismResults(UserVisitPK userVisitPK, CountContactMechanismResultsForm form) {
        return new CountContactMechanismResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearContactMechanismResults(UserVisitPK userVisitPK, ClearContactMechanismResultsForm form) {
        return new ClearContactMechanismResultsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchOffers(UserVisitPK userVisitPK, SearchOffersForm form) {
        return new SearchOffersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferResults(UserVisitPK userVisitPK, GetOfferResultsForm form) {
        return new GetOfferResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferResultsFacet(UserVisitPK userVisitPK, GetOfferResultsFacetForm form) {
        return new GetOfferResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferResultsFacets(UserVisitPK userVisitPK, GetOfferResultsFacetsForm form) {
        return new GetOfferResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countOfferResults(UserVisitPK userVisitPK, CountOfferResultsForm form) {
        return new CountOfferResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearOfferResults(UserVisitPK userVisitPK, ClearOfferResultsForm form) {
        return new ClearOfferResultsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchUses(UserVisitPK userVisitPK, SearchUsesForm form) {
        return new SearchUsesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseResults(UserVisitPK userVisitPK, GetUseResultsForm form) {
        return new GetUseResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseResultsFacet(UserVisitPK userVisitPK, GetUseResultsFacetForm form) {
        return new GetUseResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseResultsFacets(UserVisitPK userVisitPK, GetUseResultsFacetsForm form) {
        return new GetUseResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countUseResults(UserVisitPK userVisitPK, CountUseResultsForm form) {
        return new CountUseResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearUseResults(UserVisitPK userVisitPK, ClearUseResultsForm form) {
        return new ClearUseResultsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchUseTypes(UserVisitPK userVisitPK, SearchUseTypesForm form) {
        return new SearchUseTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeResults(UserVisitPK userVisitPK, GetUseTypeResultsForm form) {
        return new GetUseTypeResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeResultsFacet(UserVisitPK userVisitPK, GetUseTypeResultsFacetForm form) {
        return new GetUseTypeResultsFacetCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeResultsFacets(UserVisitPK userVisitPK, GetUseTypeResultsFacetsForm form) {
        return new GetUseTypeResultsFacetsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countUseTypeResults(UserVisitPK userVisitPK, CountUseTypeResultsForm form) {
        return new CountUseTypeResultsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearUseTypeResults(UserVisitPK userVisitPK, ClearUseTypeResultsForm form) {
        return new ClearUseTypeResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Shipping Method Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchShippingMethods(UserVisitPK userVisitPK, SearchShippingMethodsForm form) {
        return new SearchShippingMethodsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShippingMethodResults(UserVisitPK userVisitPK, GetShippingMethodResultsForm form) {
        return new GetShippingMethodResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countShippingMethodResults(UserVisitPK userVisitPK, CountShippingMethodResultsForm form) {
        return new CountShippingMethodResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearShippingMethodResults(UserVisitPK userVisitPK, ClearShippingMethodResultsForm form) {
        return new ClearShippingMethodResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchWarehouses(UserVisitPK userVisitPK, SearchWarehousesForm form) {
        return new SearchWarehousesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseResults(UserVisitPK userVisitPK, GetWarehouseResultsForm form) {
        return new GetWarehouseResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseResultsFacet(UserVisitPK userVisitPK, GetWarehouseResultsFacetForm form) {
        return new GetWarehouseResultsFacetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseResultsFacets(UserVisitPK userVisitPK, GetWarehouseResultsFacetsForm form) {
        return new GetWarehouseResultsFacetsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult countWarehouseResults(UserVisitPK userVisitPK, CountWarehouseResultsForm form) {
        return new CountWarehouseResultsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearWarehouseResults(UserVisitPK userVisitPK, ClearWarehouseResultsForm form) {
        return new ClearWarehouseResultsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Identify
    // -------------------------------------------------------------------------

    @Override
    public CommandResult identify(UserVisitPK userVisitPK, IdentifyForm form) {
        return new IdentifyCommand().run(userVisitPK, form);
    }

}
