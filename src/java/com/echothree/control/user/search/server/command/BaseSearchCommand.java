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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.BaseSearchForm;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.PartySearchTypePreference;
import com.echothree.model.data.search.server.entity.PartySearchTypePreferenceDetail;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import java.util.List;

public abstract class BaseSearchCommand<F extends BaseSearchForm, R extends BaseResult>
        extends BaseSimpleCommand<F> {
    
    protected BaseSearchCommand(CommandSecurityDefinition COMMAND_SECURITY_DEFINITION,
            List<FieldDefinition> FORM_FIELD_DEFINITIONS, boolean allowLimits) {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, allowLimits);
    }
    
    protected BaseSearchCommand(CommandSecurityDefinition COMMAND_SECURITY_DEFINITION, boolean allowLimits) {
        super(COMMAND_SECURITY_DEFINITION, allowLimits);
    }
    
    protected PartySearchTypePreference getPartySearchTypePreference(SearchControl searchControl, SearchType searchType) {
        var party = getParty();
        
        return party == null ? null : searchControl.getPartySearchTypePreference(party, searchType);
    }
    
    protected SearchDefaultOperator getDefaultSearchDefaultOperator(SearchLogic searchLogic, boolean rememberPreferences,
            PartySearchTypePreferenceDetail partySearchTypePreferenceDetail) {
        var searchDefaultOperator = partySearchTypePreferenceDetail == null || rememberPreferences ? null
                : partySearchTypePreferenceDetail.getSearchDefaultOperator();
        
        if(searchDefaultOperator == null) {
            searchDefaultOperator = searchLogic.getDefaultSearchDefaultOperator(this);
        }
        
        return searchDefaultOperator;
    }
    
    protected SearchSortOrder getDefaultSearchSortOrder(SearchLogic searchLogic, boolean rememberPreferences, SearchKind searchKind,
            PartySearchTypePreferenceDetail partySearchTypePreferenceDetail) {
        var searchSortOrder = partySearchTypePreferenceDetail == null || rememberPreferences ? null
                : partySearchTypePreferenceDetail.getSearchSortOrder();
        
        if(searchSortOrder == null) {
            searchSortOrder = searchLogic.getDefaultSearchSortOrder(this, searchKind);
        }
        
        return searchSortOrder;
    }
    
    protected SearchSortDirection getDefaultSearchSortDirection(SearchLogic searchLogic, boolean rememberPreferences,
            PartySearchTypePreferenceDetail partySearchTypePreferenceDetail) {
        var searchSortDirection = partySearchTypePreferenceDetail == null || rememberPreferences ? null
                : partySearchTypePreferenceDetail.getSearchSortDirection();
        
        if(searchSortDirection == null) {
            searchSortDirection = searchLogic.getDefaultSearchSortDirection(this);
        }
        
        return searchSortDirection;
    }
    
    protected void updatePartySearchTypePreferences(SearchControl searchControl, SearchType searchType, PartySearchTypePreference partySearchTypePreference,
        SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, Party party) {
        
        searchDefaultOperator = form.getSearchDefaultOperatorName() == null ? null : searchDefaultOperator;
        searchSortOrder = form.getSearchSortOrderName() == null ? null : searchSortOrder;
        searchSortDirection = form.getSearchSortDirectionName() == null ? null : searchSortDirection;

        if(partySearchTypePreference == null) {
            searchControl.createPartySearchTypePreference(party, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, party.getPrimaryKey());
        } else {
            var partySearchTypePreferenceDetailValue = searchControl.getPartySearchTypePreferenceDetailValueForUpdate(partySearchTypePreference);

            // Each of the following first check to see if we've fallen back onto the default for each, and will clear
            // the value if we have. Otherwise, it'll use the new value that the user has selected.
            partySearchTypePreferenceDetailValue.setSearchDefaultOperatorPK(searchDefaultOperator == null ? null : searchDefaultOperator.getPrimaryKey());
            partySearchTypePreferenceDetailValue.setSearchSortOrderPK(searchSortOrder == null ? null : searchSortOrder.getPrimaryKey());
            partySearchTypePreferenceDetailValue.setSearchSortDirectionPK(searchSortDirection == null ? null : searchSortDirection.getPrimaryKey());

            searchControl.updatePartySearchTypePreferenceFromValue(partySearchTypePreferenceDetailValue, party.getPrimaryKey());
        }
    }
    
}
