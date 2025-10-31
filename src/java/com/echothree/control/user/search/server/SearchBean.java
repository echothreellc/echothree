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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateSearchDefaultOperatorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorChoices(UserVisitPK userVisitPK, GetSearchDefaultOperatorChoicesForm form) {
        return CDI.current().select(GetSearchDefaultOperatorChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperator(UserVisitPK userVisitPK, GetSearchDefaultOperatorForm form) {
        return CDI.current().select(GetSearchDefaultOperatorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperators(UserVisitPK userVisitPK, GetSearchDefaultOperatorsForm form) {
        return CDI.current().select(GetSearchDefaultOperatorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSearchDefaultOperator(UserVisitPK userVisitPK, SetDefaultSearchDefaultOperatorForm form) {
        return CDI.current().select(SetDefaultSearchDefaultOperatorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchDefaultOperator(UserVisitPK userVisitPK, EditSearchDefaultOperatorForm form) {
        return CDI.current().select(EditSearchDefaultOperatorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchDefaultOperator(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorForm form) {
        return CDI.current().select(DeleteSearchDefaultOperatorCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Search Default Operator Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchDefaultOperatorDescription(UserVisitPK userVisitPK, CreateSearchDefaultOperatorDescriptionForm form) {
        return CDI.current().select(CreateSearchDefaultOperatorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorDescription(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionForm form) {
        return CDI.current().select(GetSearchDefaultOperatorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchDefaultOperatorDescriptions(UserVisitPK userVisitPK, GetSearchDefaultOperatorDescriptionsForm form) {
        return CDI.current().select(GetSearchDefaultOperatorDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchDefaultOperatorDescription(UserVisitPK userVisitPK, EditSearchDefaultOperatorDescriptionForm form) {
        return CDI.current().select(EditSearchDefaultOperatorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchDefaultOperatorDescription(UserVisitPK userVisitPK, DeleteSearchDefaultOperatorDescriptionForm form) {
        return CDI.current().select(DeleteSearchDefaultOperatorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Search Sort Directions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchSortDirection(UserVisitPK userVisitPK, CreateSearchSortDirectionForm form) {
        return CDI.current().select(CreateSearchSortDirectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirectionChoices(UserVisitPK userVisitPK, GetSearchSortDirectionChoicesForm form) {
        return CDI.current().select(GetSearchSortDirectionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirection(UserVisitPK userVisitPK, GetSearchSortDirectionForm form) {
        return CDI.current().select(GetSearchSortDirectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirections(UserVisitPK userVisitPK, GetSearchSortDirectionsForm form) {
        return CDI.current().select(GetSearchSortDirectionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSearchSortDirection(UserVisitPK userVisitPK, SetDefaultSearchSortDirectionForm form) {
        return CDI.current().select(SetDefaultSearchSortDirectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchSortDirection(UserVisitPK userVisitPK, EditSearchSortDirectionForm form) {
        return CDI.current().select(EditSearchSortDirectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchSortDirection(UserVisitPK userVisitPK, DeleteSearchSortDirectionForm form) {
        return CDI.current().select(DeleteSearchSortDirectionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Search Sort Direction Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSearchSortDirectionDescription(UserVisitPK userVisitPK, CreateSearchSortDirectionDescriptionForm form) {
        return CDI.current().select(CreateSearchSortDirectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirectionDescription(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionForm form) {
        return CDI.current().select(GetSearchSortDirectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSearchSortDirectionDescriptions(UserVisitPK userVisitPK, GetSearchSortDirectionDescriptionsForm form) {
        return CDI.current().select(GetSearchSortDirectionDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSearchSortDirectionDescription(UserVisitPK userVisitPK, EditSearchSortDirectionDescriptionForm form) {
        return CDI.current().select(EditSearchSortDirectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSearchSortDirectionDescription(UserVisitPK userVisitPK, DeleteSearchSortDirectionDescriptionForm form) {
        return CDI.current().select(DeleteSearchSortDirectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Search Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchKind(UserVisitPK userVisitPK, CreateSearchKindForm form) {
        return CDI.current().select(CreateSearchKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKinds(UserVisitPK userVisitPK, GetSearchKindsForm form) {
        return CDI.current().select(GetSearchKindsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKind(UserVisitPK userVisitPK, GetSearchKindForm form) {
        return CDI.current().select(GetSearchKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKindChoices(UserVisitPK userVisitPK, GetSearchKindChoicesForm form) {
        return CDI.current().select(GetSearchKindChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchKind(UserVisitPK userVisitPK, SetDefaultSearchKindForm form) {
        return CDI.current().select(SetDefaultSearchKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchKind(UserVisitPK userVisitPK, EditSearchKindForm form) {
        return CDI.current().select(EditSearchKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchKind(UserVisitPK userVisitPK, DeleteSearchKindForm form) {
        return CDI.current().select(DeleteSearchKindCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchKindDescription(UserVisitPK userVisitPK, CreateSearchKindDescriptionForm form) {
        return CDI.current().select(CreateSearchKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKindDescriptions(UserVisitPK userVisitPK, GetSearchKindDescriptionsForm form) {
        return CDI.current().select(GetSearchKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchKindDescription(UserVisitPK userVisitPK, GetSearchKindDescriptionForm form) {
        return CDI.current().select(GetSearchKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchKindDescription(UserVisitPK userVisitPK, EditSearchKindDescriptionForm form) {
        return CDI.current().select(EditSearchKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchKindDescription(UserVisitPK userVisitPK, DeleteSearchKindDescriptionForm form) {
        return CDI.current().select(DeleteSearchKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchType(UserVisitPK userVisitPK, CreateSearchTypeForm form) {
        return CDI.current().select(CreateSearchTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypes(UserVisitPK userVisitPK, GetSearchTypesForm form) {
        return CDI.current().select(GetSearchTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchType(UserVisitPK userVisitPK, GetSearchTypeForm form) {
        return CDI.current().select(GetSearchTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypeChoices(UserVisitPK userVisitPK, GetSearchTypeChoicesForm form) {
        return CDI.current().select(GetSearchTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchType(UserVisitPK userVisitPK, SetDefaultSearchTypeForm form) {
        return CDI.current().select(SetDefaultSearchTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchType(UserVisitPK userVisitPK, EditSearchTypeForm form) {
        return CDI.current().select(EditSearchTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchType(UserVisitPK userVisitPK, DeleteSearchTypeForm form) {
        return CDI.current().select(DeleteSearchTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchTypeDescription(UserVisitPK userVisitPK, CreateSearchTypeDescriptionForm form) {
        return CDI.current().select(CreateSearchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypeDescriptions(UserVisitPK userVisitPK, GetSearchTypeDescriptionsForm form) {
        return CDI.current().select(GetSearchTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchTypeDescription(UserVisitPK userVisitPK, GetSearchTypeDescriptionForm form) {
        return CDI.current().select(GetSearchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchTypeDescription(UserVisitPK userVisitPK, EditSearchTypeDescriptionForm form) {
        return CDI.current().select(EditSearchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchTypeDescription(UserVisitPK userVisitPK, DeleteSearchTypeDescriptionForm form) {
        return CDI.current().select(DeleteSearchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Sort Orders
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchSortOrder(UserVisitPK userVisitPK, CreateSearchSortOrderForm form) {
        return CDI.current().select(CreateSearchSortOrderCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrders(UserVisitPK userVisitPK, GetSearchSortOrdersForm form) {
        return CDI.current().select(GetSearchSortOrdersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrder(UserVisitPK userVisitPK, GetSearchSortOrderForm form) {
        return CDI.current().select(GetSearchSortOrderCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrderChoices(UserVisitPK userVisitPK, GetSearchSortOrderChoicesForm form) {
        return CDI.current().select(GetSearchSortOrderChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchSortOrder(UserVisitPK userVisitPK, SetDefaultSearchSortOrderForm form) {
        return CDI.current().select(SetDefaultSearchSortOrderCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchSortOrder(UserVisitPK userVisitPK, EditSearchSortOrderForm form) {
        return CDI.current().select(EditSearchSortOrderCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchSortOrder(UserVisitPK userVisitPK, DeleteSearchSortOrderForm form) {
        return CDI.current().select(DeleteSearchSortOrderCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Sort Order Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchSortOrderDescription(UserVisitPK userVisitPK, CreateSearchSortOrderDescriptionForm form) {
        return CDI.current().select(CreateSearchSortOrderDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrderDescriptions(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionsForm form) {
        return CDI.current().select(GetSearchSortOrderDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchSortOrderDescription(UserVisitPK userVisitPK, GetSearchSortOrderDescriptionForm form) {
        return CDI.current().select(GetSearchSortOrderDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchSortOrderDescription(UserVisitPK userVisitPK, EditSearchSortOrderDescriptionForm form) {
        return CDI.current().select(EditSearchSortOrderDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchSortOrderDescription(UserVisitPK userVisitPK, DeleteSearchSortOrderDescriptionForm form) {
        return CDI.current().select(DeleteSearchSortOrderDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchUseType(UserVisitPK userVisitPK, CreateSearchUseTypeForm form) {
        return CDI.current().select(CreateSearchUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypes(UserVisitPK userVisitPK, GetSearchUseTypesForm form) {
        return CDI.current().select(GetSearchUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseType(UserVisitPK userVisitPK, GetSearchUseTypeForm form) {
        return CDI.current().select(GetSearchUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypeChoices(UserVisitPK userVisitPK, GetSearchUseTypeChoicesForm form) {
        return CDI.current().select(GetSearchUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchUseType(UserVisitPK userVisitPK, SetDefaultSearchUseTypeForm form) {
        return CDI.current().select(SetDefaultSearchUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchUseType(UserVisitPK userVisitPK, EditSearchUseTypeForm form) {
        return CDI.current().select(EditSearchUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchUseType(UserVisitPK userVisitPK, DeleteSearchUseTypeForm form) {
        return CDI.current().select(DeleteSearchUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchUseTypeDescription(UserVisitPK userVisitPK, CreateSearchUseTypeDescriptionForm form) {
        return CDI.current().select(CreateSearchUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypeDescriptions(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionsForm form) {
        return CDI.current().select(GetSearchUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchUseTypeDescription(UserVisitPK userVisitPK, GetSearchUseTypeDescriptionForm form) {
        return CDI.current().select(GetSearchUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchUseTypeDescription(UserVisitPK userVisitPK, EditSearchUseTypeDescriptionForm form) {
        return CDI.current().select(EditSearchUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchUseTypeDescription(UserVisitPK userVisitPK, DeleteSearchUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteSearchUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Result Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchResultActionType(UserVisitPK userVisitPK, CreateSearchResultActionTypeForm form) {
        return CDI.current().select(CreateSearchResultActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypes(UserVisitPK userVisitPK, GetSearchResultActionTypesForm form) {
        return CDI.current().select(GetSearchResultActionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionType(UserVisitPK userVisitPK, GetSearchResultActionTypeForm form) {
        return CDI.current().select(GetSearchResultActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypeChoices(UserVisitPK userVisitPK, GetSearchResultActionTypeChoicesForm form) {
        return CDI.current().select(GetSearchResultActionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchResultActionType(UserVisitPK userVisitPK, SetDefaultSearchResultActionTypeForm form) {
        return CDI.current().select(SetDefaultSearchResultActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchResultActionType(UserVisitPK userVisitPK, EditSearchResultActionTypeForm form) {
        return CDI.current().select(EditSearchResultActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchResultActionType(UserVisitPK userVisitPK, DeleteSearchResultActionTypeForm form) {
        return CDI.current().select(DeleteSearchResultActionTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Result Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchResultActionTypeDescription(UserVisitPK userVisitPK, CreateSearchResultActionTypeDescriptionForm form) {
        return CDI.current().select(CreateSearchResultActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionsForm form) {
        return CDI.current().select(GetSearchResultActionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchResultActionTypeDescription(UserVisitPK userVisitPK, GetSearchResultActionTypeDescriptionForm form) {
        return CDI.current().select(GetSearchResultActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchResultActionTypeDescription(UserVisitPK userVisitPK, EditSearchResultActionTypeDescriptionForm form) {
        return CDI.current().select(EditSearchResultActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchResultActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchResultActionTypeDescriptionForm form) {
        return CDI.current().select(DeleteSearchResultActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchCheckSpellingActionType(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeForm form) {
        return CDI.current().select(CreateSearchCheckSpellingActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypes(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypesForm form) {
        return CDI.current().select(GetSearchCheckSpellingActionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionType(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeForm form) {
        return CDI.current().select(GetSearchCheckSpellingActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeChoices(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeChoicesForm form) {
        return CDI.current().select(GetSearchCheckSpellingActionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSearchCheckSpellingActionType(UserVisitPK userVisitPK, SetDefaultSearchCheckSpellingActionTypeForm form) {
        return CDI.current().select(SetDefaultSearchCheckSpellingActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchCheckSpellingActionType(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeForm form) {
        return CDI.current().select(EditSearchCheckSpellingActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchCheckSpellingActionType(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeForm form) {
        return CDI.current().select(DeleteSearchCheckSpellingActionTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Search Check Spelling Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, CreateSearchCheckSpellingActionTypeDescriptionForm form) {
        return CDI.current().select(CreateSearchCheckSpellingActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeDescriptions(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionsForm form) {
        return CDI.current().select(GetSearchCheckSpellingActionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, GetSearchCheckSpellingActionTypeDescriptionForm form) {
        return CDI.current().select(GetSearchCheckSpellingActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeDescriptionForm form) {
        return CDI.current().select(EditSearchCheckSpellingActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSearchCheckSpellingActionTypeDescription(UserVisitPK userVisitPK, DeleteSearchCheckSpellingActionTypeDescriptionForm form) {
        return CDI.current().select(DeleteSearchCheckSpellingActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Customer Search
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult searchCustomers(UserVisitPK userVisitPK, SearchCustomersForm form) {
        return CDI.current().select(SearchCustomersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerResults(UserVisitPK userVisitPK, GetCustomerResultsForm form) {
        return CDI.current().select(GetCustomerResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerResultsFacet(UserVisitPK userVisitPK, GetCustomerResultsFacetForm form) {
        return CDI.current().select(GetCustomerResultsFacetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerResultsFacets(UserVisitPK userVisitPK, GetCustomerResultsFacetsForm form) {
        return CDI.current().select(GetCustomerResultsFacetsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countCustomerResults(UserVisitPK userVisitPK, CountCustomerResultsForm form) {
        return CDI.current().select(CountCustomerResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearCustomerResults(UserVisitPK userVisitPK, ClearCustomerResultsForm form) {
        return CDI.current().select(ClearCustomerResultsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchItems(UserVisitPK userVisitPK, SearchItemsForm form) {
        return CDI.current().select(SearchItemsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemResults(UserVisitPK userVisitPK, GetItemResultsForm form) {
        return CDI.current().select(GetItemResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemResultsFacet(UserVisitPK userVisitPK, GetItemResultsFacetForm form) {
        return CDI.current().select(GetItemResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getItemResultsFacets(UserVisitPK userVisitPK, GetItemResultsFacetsForm form) {
        return CDI.current().select(GetItemResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countItemResults(UserVisitPK userVisitPK, CountItemResultsForm form) {
        return CDI.current().select(CountItemResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearItemResults(UserVisitPK userVisitPK, ClearItemResultsForm form) {
        return CDI.current().select(ClearItemResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult createItemSearchResultAction(UserVisitPK userVisitPK, CreateItemSearchResultActionForm form) {
        return CDI.current().select(CreateItemSearchResultActionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult checkItemSpelling(UserVisitPK userVisitPK, CheckItemSpellingForm form) {
        return CDI.current().select(CheckItemSpellingCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Vendor Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchVendors(UserVisitPK userVisitPK, SearchVendorsForm form) {
        return CDI.current().select(SearchVendorsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getVendorResults(UserVisitPK userVisitPK, GetVendorResultsForm form) {
        return CDI.current().select(GetVendorResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countVendorResults(UserVisitPK userVisitPK, CountVendorResultsForm form) {
        return CDI.current().select(CountVendorResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearVendorResults(UserVisitPK userVisitPK, ClearVendorResultsForm form) {
        return CDI.current().select(ClearVendorResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Forum Message Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchForumMessages(UserVisitPK userVisitPK, SearchForumMessagesForm form) {
        return CDI.current().select(SearchForumMessagesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getForumMessageResults(UserVisitPK userVisitPK, GetForumMessageResultsForm form) {
        return CDI.current().select(GetForumMessageResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countForumMessageResults(UserVisitPK userVisitPK, CountForumMessageResultsForm form) {
        return CDI.current().select(CountForumMessageResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearForumMessageResults(UserVisitPK userVisitPK, ClearForumMessageResultsForm form) {
        return CDI.current().select(ClearForumMessageResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Employee Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEmployees(UserVisitPK userVisitPK, SearchEmployeesForm form) {
        return CDI.current().select(SearchEmployeesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeResults(UserVisitPK userVisitPK, GetEmployeeResultsForm form) {
        return CDI.current().select(GetEmployeeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeResultsFacet(UserVisitPK userVisitPK, GetEmployeeResultsFacetForm form) {
        return CDI.current().select(GetEmployeeResultsFacetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeResultsFacets(UserVisitPK userVisitPK, GetEmployeeResultsFacetsForm form) {
        return CDI.current().select(GetEmployeeResultsFacetsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEmployeeResults(UserVisitPK userVisitPK, CountEmployeeResultsForm form) {
        return CDI.current().select(CountEmployeeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEmployeeResults(UserVisitPK userVisitPK, ClearEmployeeResultsForm form) {
        return CDI.current().select(ClearEmployeeResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchLeaves(UserVisitPK userVisitPK, SearchLeavesForm form) {
        return CDI.current().select(SearchLeavesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveResults(UserVisitPK userVisitPK, GetLeaveResultsForm form) {
        return CDI.current().select(GetLeaveResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countLeaveResults(UserVisitPK userVisitPK, CountLeaveResultsForm form) {
        return CDI.current().select(CountLeaveResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearLeaveResults(UserVisitPK userVisitPK, ClearLeaveResultsForm form) {
        return CDI.current().select(ClearLeaveResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Batch Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSalesOrderBatches(UserVisitPK userVisitPK, SearchSalesOrderBatchesForm form) {
        return CDI.current().select(SearchSalesOrderBatchesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderBatchResults(UserVisitPK userVisitPK, GetSalesOrderBatchResultsForm form) {
        return CDI.current().select(GetSalesOrderBatchResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countSalesOrderBatchResults(UserVisitPK userVisitPK, CountSalesOrderBatchResultsForm form) {
        return CDI.current().select(CountSalesOrderBatchResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearSalesOrderBatchResults(UserVisitPK userVisitPK, ClearSalesOrderBatchResultsForm form) {
        return CDI.current().select(ClearSalesOrderBatchResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSalesOrders(UserVisitPK userVisitPK, SearchSalesOrdersForm form) {
        return CDI.current().select(SearchSalesOrdersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderResults(UserVisitPK userVisitPK, GetSalesOrderResultsForm form) {
        return CDI.current().select(GetSalesOrderResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countSalesOrderResults(UserVisitPK userVisitPK, CountSalesOrderResultsForm form) {
        return CDI.current().select(CountSalesOrderResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearSalesOrderResults(UserVisitPK userVisitPK, ClearSalesOrderResultsForm form) {
        return CDI.current().select(ClearSalesOrderResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Component Vendor Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchComponentVendors(UserVisitPK userVisitPK, SearchComponentVendorsForm form) {
        return CDI.current().select(SearchComponentVendorsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getComponentVendorResults(UserVisitPK userVisitPK, GetComponentVendorResultsForm form) {
        return CDI.current().select(GetComponentVendorResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countComponentVendorResults(UserVisitPK userVisitPK, CountComponentVendorResultsForm form) {
        return CDI.current().select(CountComponentVendorResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearComponentVendorResults(UserVisitPK userVisitPK, ClearComponentVendorResultsForm form) {
        return CDI.current().select(ClearComponentVendorResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityTypes(UserVisitPK userVisitPK, SearchEntityTypesForm form) {
        return CDI.current().select(SearchEntityTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityTypeResults(UserVisitPK userVisitPK, GetEntityTypeResultsForm form) {
        return CDI.current().select(GetEntityTypeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityTypeResultsFacet(UserVisitPK userVisitPK, GetEntityTypeResultsFacetForm form) {
        return CDI.current().select(GetEntityTypeResultsFacetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityTypeResultsFacets(UserVisitPK userVisitPK, GetEntityTypeResultsFacetsForm form) {
        return CDI.current().select(GetEntityTypeResultsFacetsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityTypeResults(UserVisitPK userVisitPK, CountEntityTypeResultsForm form) {
        return CDI.current().select(CountEntityTypeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityTypeResults(UserVisitPK userVisitPK, ClearEntityTypeResultsForm form) {
        return CDI.current().select(ClearEntityTypeResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAliasTypes(UserVisitPK userVisitPK, SearchEntityAliasTypesForm form) {
        return CDI.current().select(SearchEntityAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeResults(UserVisitPK userVisitPK, GetEntityAliasTypeResultsForm form) {
        return CDI.current().select(GetEntityAliasTypeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityAliasTypeResults(UserVisitPK userVisitPK, CountEntityAliasTypeResultsForm form) {
        return CDI.current().select(CountEntityAliasTypeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityAliasTypeResults(UserVisitPK userVisitPK, ClearEntityAliasTypeResultsForm form) {
        return CDI.current().select(ClearEntityAliasTypeResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Group Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAttributeGroups(UserVisitPK userVisitPK, SearchEntityAttributeGroupsForm form) {
        return CDI.current().select(SearchEntityAttributeGroupsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAttributeGroupResults(UserVisitPK userVisitPK, GetEntityAttributeGroupResultsForm form) {
        return CDI.current().select(GetEntityAttributeGroupResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityAttributeGroupResults(UserVisitPK userVisitPK, CountEntityAttributeGroupResultsForm form) {
        return CDI.current().select(CountEntityAttributeGroupResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityAttributeGroupResults(UserVisitPK userVisitPK, ClearEntityAttributeGroupResultsForm form) {
        return CDI.current().select(ClearEntityAttributeGroupResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityAttributes(UserVisitPK userVisitPK, SearchEntityAttributesForm form) {
        return CDI.current().select(SearchEntityAttributesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAttributeResults(UserVisitPK userVisitPK, GetEntityAttributeResultsForm form) {
        return CDI.current().select(GetEntityAttributeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityAttributeResults(UserVisitPK userVisitPK, CountEntityAttributeResultsForm form) {
        return CDI.current().select(CountEntityAttributeResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityAttributeResults(UserVisitPK userVisitPK, ClearEntityAttributeResultsForm form) {
        return CDI.current().select(ClearEntityAttributeResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity List Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchEntityListItems(UserVisitPK userVisitPK, SearchEntityListItemsForm form) {
        return CDI.current().select(SearchEntityListItemsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityListItemResults(UserVisitPK userVisitPK, GetEntityListItemResultsForm form) {
        return CDI.current().select(GetEntityListItemResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countEntityListItemResults(UserVisitPK userVisitPK, CountEntityListItemResultsForm form) {
        return CDI.current().select(CountEntityListItemResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearEntityListItemResults(UserVisitPK userVisitPK, ClearEntityListItemResultsForm form) {
        return CDI.current().select(ClearEntityListItemResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Content Catalog Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContentCatalogs(UserVisitPK userVisitPK, SearchContentCatalogsForm form) {
        return CDI.current().select(SearchContentCatalogsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogResults(UserVisitPK userVisitPK, GetContentCatalogResultsForm form) {
        return CDI.current().select(GetContentCatalogResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countContentCatalogResults(UserVisitPK userVisitPK, CountContentCatalogResultsForm form) {
        return CDI.current().select(CountContentCatalogResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearContentCatalogResults(UserVisitPK userVisitPK, ClearContentCatalogResultsForm form) {
        return CDI.current().select(ClearContentCatalogResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Content Catalog Item Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContentCatalogItems(UserVisitPK userVisitPK, SearchContentCatalogItemsForm form) {
        return CDI.current().select(SearchContentCatalogItemsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItemResults(UserVisitPK userVisitPK, GetContentCatalogItemResultsForm form) {
        return CDI.current().select(GetContentCatalogItemResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItemResultsFacet(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetForm form) {
        return CDI.current().select(GetContentCatalogItemResultsFacetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItemResultsFacets(UserVisitPK userVisitPK, GetContentCatalogItemResultsFacetsForm form) {
        return CDI.current().select(GetContentCatalogItemResultsFacetsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countContentCatalogItemResults(UserVisitPK userVisitPK, CountContentCatalogItemResultsForm form) {
        return CDI.current().select(CountContentCatalogItemResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearContentCatalogItemResults(UserVisitPK userVisitPK, ClearContentCatalogItemResultsForm form) {
        return CDI.current().select(ClearContentCatalogItemResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Content Category Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContentCategories(UserVisitPK userVisitPK, SearchContentCategoriesForm form) {
        return CDI.current().select(SearchContentCategoriesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryResults(UserVisitPK userVisitPK, GetContentCategoryResultsForm form) {
        return CDI.current().select(GetContentCategoryResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryResultsFacet(UserVisitPK userVisitPK, GetContentCategoryResultsFacetForm form) {
        return CDI.current().select(GetContentCategoryResultsFacetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryResultsFacets(UserVisitPK userVisitPK, GetContentCategoryResultsFacetsForm form) {
        return CDI.current().select(GetContentCategoryResultsFacetsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countContentCategoryResults(UserVisitPK userVisitPK, CountContentCategoryResultsForm form) {
        return CDI.current().select(CountContentCategoryResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearContentCategoryResults(UserVisitPK userVisitPK, ClearContentCategoryResultsForm form) {
        return CDI.current().select(ClearContentCategoryResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Security Role Group Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSecurityRoleGroups(UserVisitPK userVisitPK, SearchSecurityRoleGroupsForm form) {
        return CDI.current().select(SearchSecurityRoleGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResults(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsForm form) {
        return CDI.current().select(GetSecurityRoleGroupResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetForm form) {
        return CDI.current().select(GetSecurityRoleGroupResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleGroupResultsFacetsForm form) {
        return CDI.current().select(GetSecurityRoleGroupResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countSecurityRoleGroupResults(UserVisitPK userVisitPK, CountSecurityRoleGroupResultsForm form) {
        return CDI.current().select(CountSecurityRoleGroupResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearSecurityRoleGroupResults(UserVisitPK userVisitPK, ClearSecurityRoleGroupResultsForm form) {
        return CDI.current().select(ClearSecurityRoleGroupResultsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchSecurityRoles(UserVisitPK userVisitPK, SearchSecurityRolesForm form) {
        return CDI.current().select(SearchSecurityRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleResults(UserVisitPK userVisitPK, GetSecurityRoleResultsForm form) {
        return CDI.current().select(GetSecurityRoleResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleResultsFacet(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetForm form) {
        return CDI.current().select(GetSecurityRoleResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleResultsFacets(UserVisitPK userVisitPK, GetSecurityRoleResultsFacetsForm form) {
        return CDI.current().select(GetSecurityRoleResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countSecurityRoleResults(UserVisitPK userVisitPK, CountSecurityRoleResultsForm form) {
        return CDI.current().select(CountSecurityRoleResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearSecurityRoleResults(UserVisitPK userVisitPK, ClearSecurityRoleResultsForm form) {
        return CDI.current().select(ClearSecurityRoleResultsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchHarmonizedTariffScheduleCodes(UserVisitPK userVisitPK, SearchHarmonizedTariffScheduleCodesForm form) {
        return CDI.current().select(SearchHarmonizedTariffScheduleCodesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResultsFacet(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getHarmonizedTariffScheduleCodeResultsFacets(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeResultsFacetsForm form) {
        return CDI.current().select(GetHarmonizedTariffScheduleCodeResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, CountHarmonizedTariffScheduleCodeResultsForm form) {
        return CDI.current().select(CountHarmonizedTariffScheduleCodeResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearHarmonizedTariffScheduleCodeResults(UserVisitPK userVisitPK, ClearHarmonizedTariffScheduleCodeResultsForm form) {
        return CDI.current().select(ClearHarmonizedTariffScheduleCodeResultsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Contact Mechanism Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchContactMechanisms(UserVisitPK userVisitPK, SearchContactMechanismsForm form) {
        return CDI.current().select(SearchContactMechanismsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismResults(UserVisitPK userVisitPK, GetContactMechanismResultsForm form) {
        return CDI.current().select(GetContactMechanismResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismResultsFacet(UserVisitPK userVisitPK, GetContactMechanismResultsFacetForm form) {
        return CDI.current().select(GetContactMechanismResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismResultsFacets(UserVisitPK userVisitPK, GetContactMechanismResultsFacetsForm form) {
        return CDI.current().select(GetContactMechanismResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countContactMechanismResults(UserVisitPK userVisitPK, CountContactMechanismResultsForm form) {
        return CDI.current().select(CountContactMechanismResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearContactMechanismResults(UserVisitPK userVisitPK, ClearContactMechanismResultsForm form) {
        return CDI.current().select(ClearContactMechanismResultsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchOffers(UserVisitPK userVisitPK, SearchOffersForm form) {
        return CDI.current().select(SearchOffersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferResults(UserVisitPK userVisitPK, GetOfferResultsForm form) {
        return CDI.current().select(GetOfferResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferResultsFacet(UserVisitPK userVisitPK, GetOfferResultsFacetForm form) {
        return CDI.current().select(GetOfferResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferResultsFacets(UserVisitPK userVisitPK, GetOfferResultsFacetsForm form) {
        return CDI.current().select(GetOfferResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countOfferResults(UserVisitPK userVisitPK, CountOfferResultsForm form) {
        return CDI.current().select(CountOfferResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearOfferResults(UserVisitPK userVisitPK, ClearOfferResultsForm form) {
        return CDI.current().select(ClearOfferResultsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchUses(UserVisitPK userVisitPK, SearchUsesForm form) {
        return CDI.current().select(SearchUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseResults(UserVisitPK userVisitPK, GetUseResultsForm form) {
        return CDI.current().select(GetUseResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseResultsFacet(UserVisitPK userVisitPK, GetUseResultsFacetForm form) {
        return CDI.current().select(GetUseResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseResultsFacets(UserVisitPK userVisitPK, GetUseResultsFacetsForm form) {
        return CDI.current().select(GetUseResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countUseResults(UserVisitPK userVisitPK, CountUseResultsForm form) {
        return CDI.current().select(CountUseResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearUseResults(UserVisitPK userVisitPK, ClearUseResultsForm form) {
        return CDI.current().select(ClearUseResultsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Type Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchUseTypes(UserVisitPK userVisitPK, SearchUseTypesForm form) {
        return CDI.current().select(SearchUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeResults(UserVisitPK userVisitPK, GetUseTypeResultsForm form) {
        return CDI.current().select(GetUseTypeResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeResultsFacet(UserVisitPK userVisitPK, GetUseTypeResultsFacetForm form) {
        return CDI.current().select(GetUseTypeResultsFacetCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeResultsFacets(UserVisitPK userVisitPK, GetUseTypeResultsFacetsForm form) {
        return CDI.current().select(GetUseTypeResultsFacetsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult countUseTypeResults(UserVisitPK userVisitPK, CountUseTypeResultsForm form) {
        return CDI.current().select(CountUseTypeResultsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult clearUseTypeResults(UserVisitPK userVisitPK, ClearUseTypeResultsForm form) {
        return CDI.current().select(ClearUseTypeResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Shipping Method Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchShippingMethods(UserVisitPK userVisitPK, SearchShippingMethodsForm form) {
        return CDI.current().select(SearchShippingMethodsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShippingMethodResults(UserVisitPK userVisitPK, GetShippingMethodResultsForm form) {
        return CDI.current().select(GetShippingMethodResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countShippingMethodResults(UserVisitPK userVisitPK, CountShippingMethodResultsForm form) {
        return CDI.current().select(CountShippingMethodResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearShippingMethodResults(UserVisitPK userVisitPK, ClearShippingMethodResultsForm form) {
        return CDI.current().select(ClearShippingMethodResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Warehouse Search
    // -------------------------------------------------------------------------

    @Override
    public CommandResult searchWarehouses(UserVisitPK userVisitPK, SearchWarehousesForm form) {
        return CDI.current().select(SearchWarehousesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseResults(UserVisitPK userVisitPK, GetWarehouseResultsForm form) {
        return CDI.current().select(GetWarehouseResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseResultsFacet(UserVisitPK userVisitPK, GetWarehouseResultsFacetForm form) {
        return CDI.current().select(GetWarehouseResultsFacetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWarehouseResultsFacets(UserVisitPK userVisitPK, GetWarehouseResultsFacetsForm form) {
        return CDI.current().select(GetWarehouseResultsFacetsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult countWarehouseResults(UserVisitPK userVisitPK, CountWarehouseResultsForm form) {
        return CDI.current().select(CountWarehouseResultsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult clearWarehouseResults(UserVisitPK userVisitPK, ClearWarehouseResultsForm form) {
        return CDI.current().select(ClearWarehouseResultsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Identify
    // -------------------------------------------------------------------------

    @Override
    public CommandResult identify(UserVisitPK userVisitPK, IdentifyForm form) {
        return CDI.current().select(IdentifyCommand.class).get().run(userVisitPK, form);
    }

}
