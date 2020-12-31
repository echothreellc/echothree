// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.search.common.transfer.SearchCheckSpellingActionTypeDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchCheckSpellingActionTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchCheckSpellingActionTypeDescriptionTransferCache
        extends BaseSearchDescriptionTransferCache<SearchCheckSpellingActionTypeDescription, SearchCheckSpellingActionTypeDescriptionTransfer> {
    
    /** Creates a new instance of SearchCheckSpellingActionTypeDescriptionTransferCache */
    public SearchCheckSpellingActionTypeDescriptionTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
    }
    
    public SearchCheckSpellingActionTypeDescriptionTransfer getSearchCheckSpellingActionTypeDescriptionTransfer(SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription) {
        SearchCheckSpellingActionTypeDescriptionTransfer searchCheckSpellingActionTypeDescriptionTransfer = get(searchCheckSpellingActionTypeDescription);
        
        if(searchCheckSpellingActionTypeDescriptionTransfer == null) {
            SearchCheckSpellingActionTypeTransfer searchCheckSpellingActionTypeTransfer = searchControl.getSearchCheckSpellingActionTypeTransfer(userVisit, searchCheckSpellingActionTypeDescription.getSearchCheckSpellingActionType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, searchCheckSpellingActionTypeDescription.getLanguage());
            
            searchCheckSpellingActionTypeDescriptionTransfer = new SearchCheckSpellingActionTypeDescriptionTransfer(languageTransfer, searchCheckSpellingActionTypeTransfer, searchCheckSpellingActionTypeDescription.getDescription());
            put(searchCheckSpellingActionTypeDescription, searchCheckSpellingActionTypeDescriptionTransfer);
        }
        return searchCheckSpellingActionTypeDescriptionTransfer;
    }
    
}
