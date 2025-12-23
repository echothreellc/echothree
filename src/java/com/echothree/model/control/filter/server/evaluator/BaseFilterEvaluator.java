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
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.server.evaluator.SelectorCache;
import com.echothree.model.control.selector.server.evaluator.SelectorCacheFactory;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseFilterEvaluator {
    
    protected Session session;
    protected BasePK evaluatedBy;
    protected Log log;
    
    protected FilterControl filterControl;
    protected FilterCache filterCache;
    protected SelectorCache selectorCache;
    
    /** Creates a new instance of BaseFilterEvaluator */
    public BaseFilterEvaluator(Session session, BasePK evaluatedBy, Class logClass, String filterKindName, String filterTypeName,
            String selectorTypeName) {
        if(logClass != null)
            log = LogFactory.getLog(logClass);
        else
            log = null;
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info(">>> BaseFilterEvaluator, filterKindName = " + filterKindName + ", filterTypeName = " + filterTypeName +
                    ", selectorTypeName = " + selectorTypeName);
        
        this.session = session;
        this.evaluatedBy = evaluatedBy;
        
        filterControl = Session.getModelController(FilterControl.class);
        filterCache = FilterCacheFactory.getInstance().getFilterCache(session, filterKindName, filterTypeName);
        selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                selectorTypeName);
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("<<< BaseFilterEvaluator");
    }
    
}
