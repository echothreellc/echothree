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
import com.echothree.model.control.search.common.transfer.SearchResultActionTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SearchResultActionTypeTransferCache
        extends BaseSearchTransferCache<SearchResultActionType, SearchResultActionTypeTransfer> {

    SearchControl searchControl = Session.getModelController(SearchControl.class);

    /** Creates a new instance of SearchResultActionTypeTransferCache */
    public SearchResultActionTypeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(SearchOptions.SearchResultActionTypeIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public SearchResultActionTypeTransfer getSearchResultActionTypeTransfer(UserVisit userVisit, SearchResultActionType searchResultActionType) {
        var searchResultActionTypeTransfer = get(searchResultActionType);

        if(searchResultActionTypeTransfer == null) {
            var searchResultActionTypeDetail = searchResultActionType.getLastDetail();
            var searchResultActionTypeName = searchResultActionTypeDetail.getSearchResultActionTypeName();
            var isDefault = searchResultActionTypeDetail.getIsDefault();
            var sortOrder = searchResultActionTypeDetail.getSortOrder();
            var description = searchControl.getBestSearchResultActionTypeDescription(searchResultActionType, getLanguage(userVisit));

            searchResultActionTypeTransfer = new SearchResultActionTypeTransfer(searchResultActionTypeName, isDefault, sortOrder, description);
            put(userVisit, searchResultActionType, searchResultActionTypeTransfer);
        }

        return searchResultActionTypeTransfer;
    }

}
