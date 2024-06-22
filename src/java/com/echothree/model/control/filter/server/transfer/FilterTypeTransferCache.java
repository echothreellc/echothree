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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.common.transfer.FilterKindTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTypeTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.entity.FilterTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FilterTypeTransferCache
        extends BaseFilterTransferCache<FilterType, FilterTypeTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterTypeTransferCache */
    public FilterTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    @Override
    public FilterTypeTransfer getTransfer(FilterType filterType) {
        FilterTypeTransfer filterTypeTransfer = get(filterType);
        
        if(filterTypeTransfer == null) {
            FilterTypeDetail filterTypeDetail = filterType.getLastDetail();
            FilterKindTransfer filterKindTransfer = filterControl.getFilterKindTransfer(userVisit, filterTypeDetail.getFilterKind());
            String filterTypeName = filterTypeDetail.getFilterTypeName();
            Boolean isDefault = filterTypeDetail.getIsDefault();
            Integer sortOrder = filterTypeDetail.getSortOrder();
            String description = filterControl.getBestFilterTypeDescription(filterType, getLanguage());
            
            filterTypeTransfer = new FilterTypeTransfer(filterKindTransfer, filterTypeName, isDefault, sortOrder, description);
            put(filterType, filterTypeTransfer);
        }
        
        return filterTypeTransfer;
    }
    
}
