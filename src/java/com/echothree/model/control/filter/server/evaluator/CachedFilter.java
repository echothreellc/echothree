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
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.evaluator.SelectorCache;
import com.echothree.model.control.selector.server.evaluator.SelectorCacheFactory;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepElementDetail;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CachedFilter {
    
    Session session;
    FilterControl filterControl;
    Filter filter;
    Log log;
    
    SelectorCache selectorCache;
    List<FilterStep> filterSteps;
    List<FilterStep> filterEntranceSteps;
    Map<FilterStep, List<FilterStep>> filterStepDestinations;
    Map<FilterStep, List<FilterStepElementDetail>> filterStepElements;
    
    /** Creates a new instance of CachedFilter */
    public CachedFilter(Session session, FilterControl filterControl, Filter filter) {
        var filterItemSelector = filter.getLastDetail().getFilterItemSelector();
        
        this.session = session;
        this.filterControl = filterControl;
        this.filter = filter;
        this.log = LogFactory.getLog(CachedFilter.class);
        
        selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                SelectorTypes.FILTER.name());
        
        if(filterItemSelector != null)
            selectorCache.getSelector(filterItemSelector);
        
        cacheFilterSteps();
        cacheFilterEntraceSteps();
        cacheFilterStepDestinations();
        cacheFilterStepElements();
    }
    
    /** Cache the selectors used in FilterStepDetails. The entity cache handles holding onto
     * FilterSteps and their FilterStepDetails.
     */
    private void cacheFilterSteps() {
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info(">>> cacheFilterSteps");
        
        Collection<FilterStep> rawFilterSteps = filterControl.getFilterStepsByFilter(filter);
        var size = rawFilterSteps.size();
        
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info("---   rawFilterSteps.size() = " + size);
        
        filterSteps = new ArrayList<>(size);
        rawFilterSteps.forEach((filterStep) -> {
            var filterStepDetail = filterStep.getLastDetail();
            var filterItemSelector = filterStepDetail.getFilterItemSelector();
            filterSteps.add(filterStep);
            if (filterItemSelector != null) {
                selectorCache.getSelector(filterItemSelector);
            }
        });
        
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info("<<< cacheFilterSteps");
    }
    
    /** Load the filterEntranceSteps ArrayList, simple array of FilterSteps.
     */
    private void cacheFilterEntraceSteps() {
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info(">>> cacheFilterEntraceSteps");

        var rawFilterEntranceSteps = filterControl.getFilterEntranceStepsByFilter(filter);
        var size = rawFilterEntranceSteps.size();
        
        filterEntranceSteps = new ArrayList<>(size);
        rawFilterEntranceSteps.stream().map((filterEntranceStep) -> filterEntranceStep.getFilterStep()).map((filterStep) -> {
            if(BaseFilterEvaluatorDebugFlags.CachedFilter)
                log.info("---   filterStepName = " + filterStep.getLastDetail().getFilterStepName());
            return filterStep;
        }).forEach((filterStep) -> {
            filterEntranceSteps.add(filterStep);
        });
        
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info("<<< cacheFilterEntraceSteps");
    }
    
    /** Load the filterStepDestinations HashMap, the FilterStep is the key, each
     * value is an ArrayList of FilterSteps.
     */
    private void cacheFilterStepDestinations() {
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info(">>> cacheFilterStepDestinations");

        var filterStepsSize = filterSteps.size();
        filterStepDestinations = new HashMap<>(filterStepsSize);
        
        filterSteps.forEach((filterStep) -> {
            var rawFilterStepDestinations = filterControl.getFilterStepDestinationsByFromFilterStep(filterStep);
            var rawFilterStepDestinationsSize = rawFilterStepDestinations.size();
            List<FilterStep> filterStepDestinationsList = new ArrayList<>(rawFilterStepDestinationsSize);
            if(BaseFilterEvaluatorDebugFlags.CachedFilter)
                log.info("---   fromFilterStepName = " + filterStep.getLastDetail().getFilterStepName() + ", rawFilterStepDestinationsSize = " + rawFilterStepDestinationsSize);
            if (rawFilterStepDestinationsSize != 0) {
                rawFilterStepDestinations.stream().map((filterStepDestination) -> filterStepDestination.getToFilterStep()).map((toFilterStep) -> {
                    if(BaseFilterEvaluatorDebugFlags.CachedFilter)
                        log.info("---     toFilterStepName = " + toFilterStep.getLastDetail().getFilterStepName());
                    return toFilterStep;
                }).forEach((toFilterStep) -> {
                    filterStepDestinationsList.add(toFilterStep);
                });
            }
            filterStepDestinations.put(filterStep, filterStepDestinationsList);
        });
        
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info("<<< cacheFilterStepDestinations");
    }
    
    /** Load the filterStepElements HaspMap, the FilterStep is the key, each value is an
     * ArrayList of FilterStepElementDetails.
     */
    private void cacheFilterStepElements() {
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info(">>> cacheFilterStepElements");

        var filterStepsSize = filterSteps.size();
        filterStepElements = new HashMap<>(filterStepsSize);
        
        filterSteps.forEach((filterStep) -> {
            var rawFilterStepElements = filterControl.getFilterStepElementsByFilterStep(filterStep);
            var rawFilterStepElementsSize = rawFilterStepElements.size();
            List<FilterStepElementDetail> filterStepElementsList = new ArrayList<>(rawFilterStepElementsSize);
            if(BaseFilterEvaluatorDebugFlags.CachedFilter)
                log.info("---   filterStepName = " + filterStep.getLastDetail().getFilterStepName() + ", rawFilterStepElementsSize = " + rawFilterStepElementsSize);
            if (rawFilterStepElementsSize != 0) {
                rawFilterStepElements.forEach((filterStepElement) -> {
                    var filterItemSelector = filterStepElement.getLastDetail().getFilterItemSelector();
                    if(BaseFilterEvaluatorDebugFlags.CachedFilter)
                        log.info("---     filterStepElementName = " + filterStepElement.getLastDetail().getFilterStepElementName());
                    filterStepElementsList.add(filterStepElement.getLastDetail());
                    if (filterItemSelector != null) {
                        selectorCache.getSelector(filterItemSelector);
                    }
                });
            }
            filterStepElements.put(filterStep, filterStepElementsList);
        });
        
        if(BaseFilterEvaluatorDebugFlags.CachedFilter)
            log.info("<<< cacheFilterStepElements");
    }
    
    public List<FilterStep> getFilterEntranceSteps() {
        return filterEntranceSteps;
    }
    
    public List<FilterStep> getFilterStepDestinationsByFilterStep(FilterStep filterStep) {
        return filterStepDestinations.get(filterStep);
    }
    
    public List<FilterStepElementDetail> getFilterStepElementDetailsByFilterStep(FilterStep filterStep) {
        return filterStepElements.get(filterStep);
    }
    
}
