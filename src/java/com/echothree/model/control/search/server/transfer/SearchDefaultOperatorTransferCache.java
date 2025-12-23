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

import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.transfer.SearchDefaultOperatorTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SearchDefaultOperatorTransferCache
        extends BaseSearchTransferCache<SearchDefaultOperator, SearchDefaultOperatorTransfer> {

    SearchControl searchControl = Session.getModelController(SearchControl.class);

    /** Creates a new instance of SearchDefaultOperatorTransferCache */
    protected SearchDefaultOperatorTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(SearchOptions.SearchDefaultOperatorIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public SearchDefaultOperatorTransfer getSearchDefaultOperatorTransfer(UserVisit userVisit, SearchDefaultOperator searchDefaultOperator) {
        var searchDefaultOperatorTransfer = get(searchDefaultOperator);

        if(searchDefaultOperatorTransfer == null) {
            var searchDefaultOperatorDetail = searchDefaultOperator.getLastDetail();
            var searchDefaultOperatorName = searchDefaultOperatorDetail.getSearchDefaultOperatorName();
            var isDefault = searchDefaultOperatorDetail.getIsDefault();
            var sortOrder = searchDefaultOperatorDetail.getSortOrder();
            var description = searchControl.getBestSearchDefaultOperatorDescription(searchDefaultOperator, getLanguage(userVisit));

            searchDefaultOperatorTransfer = new SearchDefaultOperatorTransfer(searchDefaultOperatorName, isDefault, sortOrder, description);
            put(userVisit, searchDefaultOperator, searchDefaultOperatorTransfer);
        }

        return searchDefaultOperatorTransfer;
    }

}
