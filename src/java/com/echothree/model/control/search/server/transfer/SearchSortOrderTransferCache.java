// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.search.common.transfer.SearchSortOrderTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SearchSortOrderTransferCache
        extends BaseSearchTransferCache<SearchSortOrder, SearchSortOrderTransfer> {

    SearchControl searchControl = Session.getModelController(SearchControl.class);

    /** Creates a new instance of SearchSortOrderTransferCache */
    protected SearchSortOrderTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public SearchSortOrderTransfer getSearchSortOrderTransfer(UserVisit userVisit, SearchSortOrder searchSortOrder) {
        var searchSortOrderTransfer = get(searchSortOrder);
        
        if(searchSortOrderTransfer == null) {
            var searchSortOrderDetail = searchSortOrder.getLastDetail();
            var searchKindTransfer = searchControl.getSearchKindTransfer(userVisit, searchSortOrderDetail.getSearchKind());
            var searchSortOrderName = searchSortOrderDetail.getSearchSortOrderName();
            var isDefault = searchSortOrderDetail.getIsDefault();
            var sortOrder = searchSortOrderDetail.getSortOrder();
            var description = searchControl.getBestSearchSortOrderDescription(searchSortOrder, getLanguage(userVisit));
            
            searchSortOrderTransfer = new SearchSortOrderTransfer(searchKindTransfer, searchSortOrderName, isDefault, sortOrder, description);
            put(userVisit, searchSortOrder, searchSortOrderTransfer);
        }
        
        return searchSortOrderTransfer;
    }
    
}
