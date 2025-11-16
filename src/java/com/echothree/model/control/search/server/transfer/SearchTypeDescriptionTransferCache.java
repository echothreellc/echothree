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

import com.echothree.model.control.search.common.transfer.SearchTypeDescriptionTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SearchTypeDescriptionTransferCache
        extends BaseSearchDescriptionTransferCache<SearchTypeDescription, SearchTypeDescriptionTransfer> {

    SearchControl searchControl = Session.getModelController(SearchControl.class);

    /** Creates a new instance of SearchTypeDescriptionTransferCache */
    public SearchTypeDescriptionTransferCache() {
        super();
    }
    
    public SearchTypeDescriptionTransfer getSearchTypeDescriptionTransfer(UserVisit userVisit, SearchTypeDescription searchTypeDescription) {
        var searchTypeDescriptionTransfer = get(searchTypeDescription);
        
        if(searchTypeDescriptionTransfer == null) {
            var searchTypeTransfer = searchControl.getSearchTypeTransfer(userVisit, searchTypeDescription.getSearchType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, searchTypeDescription.getLanguage());
            
            searchTypeDescriptionTransfer = new SearchTypeDescriptionTransfer(languageTransfer, searchTypeTransfer, searchTypeDescription.getDescription());
            put(userVisit, searchTypeDescription, searchTypeDescriptionTransfer);
        }
        
        return searchTypeDescriptionTransfer;
    }
    
}
