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

import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.transfer.SearchResultActionTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.search.server.entity.SearchResultActionTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class SearchResultActionTypeTransferCache
        extends BaseSearchTransferCache<SearchResultActionType, SearchResultActionTypeTransfer> {

    /** Creates a new instance of SearchResultActionTypeTransferCache */
    public SearchResultActionTypeTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(SearchOptions.SearchResultActionTypeIncludeKey));
            setIncludeGuid(options.contains(SearchOptions.SearchResultActionTypeIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public SearchResultActionTypeTransfer getSearchResultActionTypeTransfer(SearchResultActionType searchResultActionType) {
        SearchResultActionTypeTransfer searchResultActionTypeTransfer = get(searchResultActionType);

        if(searchResultActionTypeTransfer == null) {
            SearchResultActionTypeDetail searchResultActionTypeDetail = searchResultActionType.getLastDetail();
            String searchResultActionTypeName = searchResultActionTypeDetail.getSearchResultActionTypeName();
            Boolean isDefault = searchResultActionTypeDetail.getIsDefault();
            Integer sortOrder = searchResultActionTypeDetail.getSortOrder();
            String description = searchControl.getBestSearchResultActionTypeDescription(searchResultActionType, getLanguage());

            searchResultActionTypeTransfer = new SearchResultActionTypeTransfer(searchResultActionTypeName, isDefault, sortOrder, description);
            put(searchResultActionType, searchResultActionTypeTransfer);
        }

        return searchResultActionTypeTransfer;
    }

}
