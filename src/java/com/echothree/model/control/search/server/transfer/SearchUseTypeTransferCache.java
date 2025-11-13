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

import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.transfer.SearchUseTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchUseTypeTransferCache
        extends BaseSearchTransferCache<SearchUseType, SearchUseTypeTransfer> {

    /** Creates a new instance of SearchUseTypeTransferCache */
    public SearchUseTypeTransferCache(SearchControl searchControl) {
        super(searchControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(SearchOptions.SearchUseTypeIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public SearchUseTypeTransfer getSearchUseTypeTransfer(UserVisit userVisit, SearchUseType searchUseType) {
        var searchUseTypeTransfer = get(searchUseType);

        if(searchUseTypeTransfer == null) {
            var searchUseTypeDetail = searchUseType.getLastDetail();
            var searchUseTypeName = searchUseTypeDetail.getSearchUseTypeName();
            var isDefault = searchUseTypeDetail.getIsDefault();
            var sortOrder = searchUseTypeDetail.getSortOrder();
            var description = searchControl.getBestSearchUseTypeDescription(searchUseType, getLanguage(userVisit));

            searchUseTypeTransfer = new SearchUseTypeTransfer(searchUseTypeName, isDefault, sortOrder, description);
            put(userVisit, searchUseType, searchUseTypeTransfer);
        }

        return searchUseTypeTransfer;
    }

}
