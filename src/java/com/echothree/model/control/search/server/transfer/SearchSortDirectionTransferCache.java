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
import com.echothree.model.control.search.common.transfer.SearchSortDirectionTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SearchSortDirectionTransferCache
        extends BaseSearchTransferCache<SearchSortDirection, SearchSortDirectionTransfer> {

    SearchControl searchControl = Session.getModelController(SearchControl.class);

    /** Creates a new instance of SearchSortDirectionTransferCache */
    public SearchSortDirectionTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(SearchOptions.SearchSortDirectionIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public SearchSortDirectionTransfer getSearchSortDirectionTransfer(UserVisit userVisit, SearchSortDirection searchSortDirection) {
        var searchSortDirectionTransfer = get(searchSortDirection);

        if(searchSortDirectionTransfer == null) {
            var searchSortDirectionDetail = searchSortDirection.getLastDetail();
            var searchSortDirectionName = searchSortDirectionDetail.getSearchSortDirectionName();
            var isDefault = searchSortDirectionDetail.getIsDefault();
            var sortOrder = searchSortDirectionDetail.getSortOrder();
            var description = searchControl.getBestSearchSortDirectionDescription(searchSortDirection, getLanguage(userVisit));

            searchSortDirectionTransfer = new SearchSortDirectionTransfer(searchSortDirectionName, isDefault, sortOrder, description);
            put(userVisit, searchSortDirection, searchSortDirectionTransfer);
        }

        return searchSortDirectionTransfer;
    }

}
