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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.BaseCheckSpellingForm;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.search.server.entity.PartySearchTypePreference;
import com.echothree.model.data.search.server.entity.PartySearchTypePreferenceDetail;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import java.util.List;

public abstract class BaseCheckSpellingCommand<F extends BaseCheckSpellingForm, R extends BaseResult>
        extends BaseSimpleCommand<F> {
    
    protected BaseCheckSpellingCommand(CommandSecurityDefinition COMMAND_SECURITY_DEFINITION,
            List<FieldDefinition> FORM_FIELD_DEFINITIONS, boolean allowLimits) {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, allowLimits);
    }
    
    protected BaseCheckSpellingCommand(CommandSecurityDefinition COMMAND_SECURITY_DEFINITION, boolean allowLimits) {
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
    
}
