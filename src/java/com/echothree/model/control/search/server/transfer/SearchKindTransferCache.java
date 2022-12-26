// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.search.common.transfer.SearchKindTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchKindDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchKindTransferCache
        extends BaseSearchTransferCache<SearchKind, SearchKindTransfer> {
    
    /** Creates a new instance of SearchKindTransferCache */
    public SearchKindTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SearchKindTransfer getSearchKindTransfer(SearchKind searchKind) {
        SearchKindTransfer searchKindTransfer = get(searchKind);
        
        if(searchKindTransfer == null) {
            SearchKindDetail searchKindDetail = searchKind.getLastDetail();
            String searchKindName = searchKindDetail.getSearchKindName();
            Boolean isDefault = searchKindDetail.getIsDefault();
            Integer sortOrder = searchKindDetail.getSortOrder();
            String description = searchControl.getBestSearchKindDescription(searchKind, getLanguage());
            
            searchKindTransfer = new SearchKindTransfer(searchKindName, isDefault, sortOrder, description);
            put(searchKind, searchKindTransfer);
        }
        
        return searchKindTransfer;
    }
    
}
