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

package com.echothree.model.control.search.server.transfer;

import com.echothree.model.control.search.common.transfer.SearchDefaultOperatorDescriptionTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchDefaultOperatorDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchDefaultOperatorDescriptionTransferCache
        extends BaseSearchDescriptionTransferCache<SearchDefaultOperatorDescription, SearchDefaultOperatorDescriptionTransfer> {
    
    /** Creates a new instance of SearchDefaultOperatorDescriptionTransferCache */
    public SearchDefaultOperatorDescriptionTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
    }
    
    public SearchDefaultOperatorDescriptionTransfer getSearchDefaultOperatorDescriptionTransfer(SearchDefaultOperatorDescription searchDefaultOperatorDescription) {
        var searchDefaultOperatorDescriptionTransfer = get(searchDefaultOperatorDescription);
        
        if(searchDefaultOperatorDescriptionTransfer == null) {
            var searchDefaultOperatorTransfer = searchControl.getSearchDefaultOperatorTransfer(userVisit, searchDefaultOperatorDescription.getSearchDefaultOperator());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, searchDefaultOperatorDescription.getLanguage());
            
            searchDefaultOperatorDescriptionTransfer = new SearchDefaultOperatorDescriptionTransfer(languageTransfer, searchDefaultOperatorTransfer, searchDefaultOperatorDescription.getDescription());
            put(searchDefaultOperatorDescription, searchDefaultOperatorDescriptionTransfer);
        }
        return searchDefaultOperatorDescriptionTransfer;
    }
    
}
