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

package com.echothree.model.control.search.server.transfer;

import com.echothree.model.control.search.common.transfer.SearchKindTransfer;
import com.echothree.model.control.search.common.transfer.SearchTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchTypeTransferCache
        extends BaseSearchTransferCache<SearchType, SearchTypeTransfer> {
    
    /** Creates a new instance of SearchTypeTransferCache */
    public SearchTypeTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SearchTypeTransfer getSearchTypeTransfer(SearchType searchType) {
        SearchTypeTransfer searchTypeTransfer = get(searchType);
        
        if(searchTypeTransfer == null) {
            SearchTypeDetail searchTypeDetail = searchType.getLastDetail();
            SearchKindTransfer searchKindTransfer = searchControl.getSearchKindTransfer(userVisit, searchTypeDetail.getSearchKind());
            String searchTypeName = searchTypeDetail.getSearchTypeName();
            Boolean isDefault = searchTypeDetail.getIsDefault();
            Integer sortOrder = searchTypeDetail.getSortOrder();
            String description = searchControl.getBestSearchTypeDescription(searchType, getLanguage());
            
            searchTypeTransfer = new SearchTypeTransfer(searchKindTransfer, searchTypeName, isDefault, sortOrder, description);
            put(searchType, searchTypeTransfer);
        }
        
        return searchTypeTransfer;
    }
    
}
