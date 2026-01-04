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

package com.echothree.model.control.filter.server.evaluator;

import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;

public class FilterCache {
    
    Session session;
    FilterControl filterControl;
    FilterType filterType;
    
    HashMap cachedFilters;
    
    /** Creates a new instance of FilterCache */
    public FilterCache(Session session, FilterControl filterControl, FilterType filterType) {
        super();
        
        this.session = session;
        this.filterControl = filterControl;
        this.filterType = filterType;
        
        cachedFilters = new HashMap();
    }
    
    public CachedFilter getFilterByName(String filterName) {
        CachedFilter cachedFilter = null;
        var filter = filterControl.getFilterByName(filterType, filterName);
        
        if(filter != null)
            cachedFilter = getFilter(filter);
        
        return cachedFilter;
    }
    
    public CachedFilter getFilter(Filter filter) {
        CachedFilter cachedFilter;
        
        if(filter.getLastDetail().getFilterType().equals(filterType)) {
            cachedFilter = (CachedFilter)cachedFilters.get(filter);
            
            if(cachedFilter == null) {
                cachedFilter = new CachedFilter(session, filterControl, filter);
                cachedFilters.put(filter, cachedFilter);
            }
        } else
            throw new IllegalArgumentException();
        
        return cachedFilter;
    }
    
}
