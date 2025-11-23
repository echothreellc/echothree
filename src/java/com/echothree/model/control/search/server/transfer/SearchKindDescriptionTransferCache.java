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

import com.echothree.model.control.search.common.transfer.SearchKindDescriptionTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SearchKindDescriptionTransferCache
        extends BaseSearchDescriptionTransferCache<SearchKindDescription, SearchKindDescriptionTransfer> {

    SearchControl searchControl = Session.getModelController(SearchControl.class);

    /** Creates a new instance of SearchKindDescriptionTransferCache */
    protected SearchKindDescriptionTransferCache() {
        super();
    }
    
    public SearchKindDescriptionTransfer getSearchKindDescriptionTransfer(UserVisit userVisit, SearchKindDescription searchKindDescription) {
        var searchKindDescriptionTransfer = get(searchKindDescription);
        
        if(searchKindDescriptionTransfer == null) {
            var searchKindTransfer = searchControl.getSearchKindTransfer(userVisit, searchKindDescription.getSearchKind());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, searchKindDescription.getLanguage());
            
            searchKindDescriptionTransfer = new SearchKindDescriptionTransfer(languageTransfer, searchKindTransfer, searchKindDescription.getDescription());
            put(userVisit, searchKindDescription, searchKindDescriptionTransfer);
        }
        
        return searchKindDescriptionTransfer;
    }
    
}
