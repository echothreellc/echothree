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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.common.transfer.FilterKindTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FilterKindTransferCache
        extends BaseFilterTransferCache<FilterKind, FilterKindTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterKindTransferCache */
    protected FilterKindTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public FilterKindTransfer getTransfer(UserVisit userVisit, FilterKind filterKind) {
        var filterKindTransfer = get(filterKind);
        
        if(filterKindTransfer == null) {
            var filterKindDetail = filterKind.getLastDetail();
            var filterKindName = filterKindDetail.getFilterKindName();
            var isDefault = filterKindDetail.getIsDefault();
            var sortOrder = filterKindDetail.getSortOrder();
            var description = filterControl.getBestFilterKindDescription(filterKind, getLanguage(userVisit));
            
            filterKindTransfer = new FilterKindTransfer(filterKindName, isDefault, sortOrder, description);
            put(userVisit, filterKind, filterKindTransfer);
        }
        
        return filterKindTransfer;
    }
    
}
