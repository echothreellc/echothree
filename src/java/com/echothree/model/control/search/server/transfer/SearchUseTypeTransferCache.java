// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.search.common.transfer.SearchUseTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.search.server.entity.SearchUseTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class SearchUseTypeTransferCache
        extends BaseSearchTransferCache<SearchUseType, SearchUseTypeTransfer> {

    /** Creates a new instance of SearchUseTypeTransferCache */
    public SearchUseTypeTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(SearchOptions.SearchUseTypeIncludeKey));
            setIncludeGuid(options.contains(SearchOptions.SearchUseTypeIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public SearchUseTypeTransfer getSearchUseTypeTransfer(SearchUseType searchUseType) {
        SearchUseTypeTransfer searchUseTypeTransfer = get(searchUseType);

        if(searchUseTypeTransfer == null) {
            SearchUseTypeDetail searchUseTypeDetail = searchUseType.getLastDetail();
            String searchUseTypeName = searchUseTypeDetail.getSearchUseTypeName();
            Boolean isDefault = searchUseTypeDetail.getIsDefault();
            Integer sortOrder = searchUseTypeDetail.getSortOrder();
            String description = searchControl.getBestSearchUseTypeDescription(searchUseType, getLanguage());

            searchUseTypeTransfer = new SearchUseTypeTransfer(searchUseTypeName, isDefault, sortOrder, description);
            put(searchUseType, searchUseTypeTransfer);
        }

        return searchUseTypeTransfer;
    }

}
