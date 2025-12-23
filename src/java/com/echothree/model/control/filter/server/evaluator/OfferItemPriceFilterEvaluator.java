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

import com.echothree.model.control.filter.common.FilterAdjustmentSources;
import com.echothree.model.control.filter.common.FilterAdjustmentTypes;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.FilterTypes;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.evaluator.FilterItemSelectorEvaluator;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class OfferItemPriceFilterEvaluator
        extends BaseFilterEvaluator {
    
    FilterItemSelectorEvaluator filterItemSelectorEvaluator;
    
    private CachedFilter cachedFilter = null;
    private Item item = null;
    private ItemPrice itemPrice = null;
    private ItemFixedPrice itemFixedPrice = null;
    private Long listUnitPrice = null;
    
    /** Creates a new instance of OfferItemPriceFilterEvaluator */
    public OfferItemPriceFilterEvaluator(Session session, BasePK evaluatedBy) {
        super(session, evaluatedBy, OfferItemPriceFilterEvaluator.class, FilterKinds.PRICE.name(),
                FilterTypes.OFFER_ITEM_PRICE.name(), SelectorTypes.FILTER.name());
        
        filterItemSelectorEvaluator = new FilterItemSelectorEvaluator(session, evaluatedBy);
    }
    
    protected FilteredItemFixedPrice applyFilterFixedPriceAdjustment(FilteredItemFixedPrice filteredItemFixedPrice, FilterAdjustment filterAdjustment) {
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info(">>> OfferItemPriceFilterEvaluator.applyFilterFixedPriceAdjustment");

        var filterAdjustmentDetail = filterAdjustment.getLastDetail();
        var filterAdjustmentSourceName = filterAdjustmentDetail.getFilterAdjustmentSource().getFilterAdjustmentSourceName();
        var filterAdjustmentType = filterAdjustmentDetail.getFilterAdjustmentType();
        long initialUnitPrice = filteredItemFixedPrice.getUnitPrice();
        var unitPrice = initialUnitPrice;
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator) {
            log.info("--- filterAdjustmentName = " + filterAdjustmentDetail.getFilterAdjustmentName());
            log.info("--- filterAdjustmentSourceName = " + filterAdjustmentSourceName);
        }

        if(filterAdjustmentSourceName.equals(FilterAdjustmentSources.CURRENT.name())) {
            // No source price changes.
        } else if(filterAdjustmentSourceName.equals(FilterAdjustmentSources.INVENTORY_COST.name())) {
            // TODO
        } else if(filterAdjustmentSourceName.equals(FilterAdjustmentSources.ITEM_PRICE.name())) {
            unitPrice = listUnitPrice;
        } else if(filterAdjustmentSourceName.equals(FilterAdjustmentSources.PRIMARY_VENDOR_COST.name())) {
            // TODO
        } else if(filterAdjustmentSourceName.equals(FilterAdjustmentSources.VENDOR_COST.name())) {
            // TODO
        } else if(filterAdjustmentSourceName.equals(FilterAdjustmentSources.SET_AMOUNT.name())) {
            // No change here, taken care of by filterAdjustmentType
            if(filterAdjustmentType == null)
                throw new IllegalArgumentException("filterAdjustmentType is required when FilterAdjustmentSource_SET_AMOUNT");
        } else
            throw new IllegalArgumentException("Unknown filterAdjustmentSourceName");
        
        if(filterAdjustmentType != null) {
            var filterAdjustmentTypeName = filterAdjustmentType.getFilterAdjustmentTypeName();
            var unitOfMeasureType = filteredItemFixedPrice.getUnitOfMeasureType();
            var currency = filteredItemFixedPrice.getCurrency();
            
            if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
                log.info("--- filterAdjustmentTypeName = " + filterAdjustmentTypeName);
            if(filterAdjustmentTypeName.equals(FilterAdjustmentTypes.AMOUNT.name())) {
                var filterAdjustmentAmount = filterControl.getFilterAdjustmentAmount(filterAdjustment, unitOfMeasureType, currency);
                
                if(filterAdjustmentAmount != null) {
                    long amount = filterAdjustmentAmount.getAmount();
                    
                    unitPrice += amount;
                    if(unitPrice < 0)
                        unitPrice = 0;
                }
            } else if(filterAdjustmentTypeName.equals(FilterAdjustmentTypes.FIXED_AMOUNT.name())) {
                var filterAdjustmentFixedAmount = filterControl.getFilterAdjustmentFixedAmount(filterAdjustment, unitOfMeasureType, currency);
                
                if(filterAdjustmentFixedAmount != null) {
                    unitPrice = filterAdjustmentFixedAmount.getUnitAmount();
                }
            } else if(filterAdjustmentTypeName.equals(FilterAdjustmentTypes.PERCENT.name())) {
                var filterAdjustmentPercent = filterControl.getFilterAdjustmentPercent(filterAdjustment, unitOfMeasureType, currency);
                
                if(filterAdjustmentPercent != null) {
                    int percent = filterAdjustmentPercent.getPercent();
                    
                    unitPrice = unitPrice - (unitPrice * percent / 100000);
                }
            } else
                throw new IllegalArgumentException("Unknown filterAdjustmentTypeName");
        }
        
        if(initialUnitPrice != unitPrice) {
            try {
                filteredItemFixedPrice = filteredItemFixedPrice.clone();
            } catch(CloneNotSupportedException ex) {
                throw new RuntimeException((ex));
            }

            filteredItemFixedPrice.setUnitPrice(unitPrice);
        }
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("<<< OfferItemPriceFilterEvaluator.applyFilterFixedPriceAdjustment, initialUnitPrice = " + initialUnitPrice + ", unitPrice = " + unitPrice);
        return filteredItemFixedPrice;
    }
    
    protected FilteredItemFixedPrice evaluateFilterStep(FilterStep filterStep, FilteredItemFixedPrice filteredItemFixedPrice) {
        var filterStepDetail = filterStep.getLastDetail();
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info(">>> OfferItemPriceFilterEvaluator.evaluateFilterStep, filterStepName = " + filterStepDetail.getFilterStepName());

        var filterItemSelector = filterStepDetail.getFilterItemSelector();
        var useFilterStep = filterItemSelector == null? true: filterItemSelectorEvaluator.evaluate(selectorCache.getSelector(filterItemSelector), item);
        
        if(useFilterStep) {
            var filterStepElementDetails = cachedFilter.getFilterStepElementDetailsByFilterStep(filterStep);
            var evaluatedPrices = new TreeSet<FilteredItemFixedPrice>();
            
            for(var filterStepElementDetail : filterStepElementDetails) {
                if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
                    log.info("--- filterStepElementName = " + filterStepElementDetail.getFilterStepElementName());
                
                filterItemSelector = filterStepElementDetail.getFilterItemSelector();
                var useFilterStepElement = filterItemSelector == null? true: filterItemSelectorEvaluator.evaluate(selectorCache.getSelector(filterItemSelector), item);
                if(useFilterStepElement) {
                    var filterAdjustment = filterStepElementDetail.getFilterAdjustment();
                    
                    evaluatedPrices.add(applyFilterFixedPriceAdjustment(filteredItemFixedPrice, filterAdjustment));
                }
            }
            
            if(!evaluatedPrices.isEmpty())
                filteredItemFixedPrice = evaluatedPrices.first();
        } else {
            filteredItemFixedPrice = null;
        }
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("<<< OfferItemPriceFilterEvaluator.evaluateFilterStep");
        return filteredItemFixedPrice;
    }
    
    protected FilteredItemFixedPrice evaluateFilterSteps(List<FilterStep> nextFilterSteps, FilteredItemFixedPrice filteredItemFixedPrice) {
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info(">>> OfferItemPriceFilterEvaluator.evaluateFilterSteps, nextFilterSteps.size() = " + nextFilterSteps.size());

        var evaluatedPrices = new TreeSet<FilteredItemFixedPrice>();
        for(var filterStep : nextFilterSteps) {
            var evaluatedPrice = evaluateFilterStep(filterStep, filteredItemFixedPrice);
            
            if(evaluatedPrice != null) {
                nextFilterSteps = cachedFilter.getFilterStepDestinationsByFilterStep(filterStep);
                evaluatedPrice = evaluateFilterSteps(nextFilterSteps, evaluatedPrice);
                
                evaluatedPrices.add(evaluatedPrice);
            }
        }
        
        if(!evaluatedPrices.isEmpty())
            filteredItemFixedPrice = (FilteredItemFixedPrice)evaluatedPrices.first();
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("<<< OfferItemPriceFilterEvaluator.evaluateFilterSteps");
        return filteredItemFixedPrice;
    }
    
    public FilteredItemFixedPrice evaluate(Item item, ItemPrice itemPrice, ItemFixedPrice itemFixedPrice, Filter filter) {
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info(">>> OfferItemPriceFilterEvaluator.evaluate");
        
        if(item == null)
            throw new IllegalArgumentException("item == null");
        if(itemPrice == null)
            throw new IllegalArgumentException("itemPrice == null");
        if(itemFixedPrice == null)
            throw new IllegalArgumentException("itemFixedPrice == null");

        var unitOfMeasureType = itemPrice.getUnitOfMeasureType();
        var currency = itemPrice.getCurrency();
        listUnitPrice = itemFixedPrice.getUnitPrice();

        var filteredItemFixedPrice = new FilteredItemFixedPrice(item, unitOfMeasureType, currency, listUnitPrice);
        
        if(filter != null) {
            var filterDetail = filter.getLastDetail();
            var selector = filterDetail.getFilterItemSelector();
            var useFilter = selector == null? true: filterItemSelectorEvaluator.evaluate(selectorCache.getSelector(selector), item);
            
            if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator) {
                log.info("--- filterName = " + filterDetail.getFilterName() + ", selectorName = " + selector == null? "none": selector.getLastDetail().getSelectorName()
                + ", useFilter = " + useFilter);
            }
            
            if(useFilter) {
                var initialFilterAdjustment = filterDetail.getInitialFilterAdjustment();
                
                cachedFilter = filterCache.getFilter(filter);
                this.item = item;
                this.itemPrice = itemPrice;
                this.itemFixedPrice = itemFixedPrice;
                
                filteredItemFixedPrice = applyFilterFixedPriceAdjustment(filteredItemFixedPrice, initialFilterAdjustment);

                var filterEntranceSteps = cachedFilter.getFilterEntranceSteps();
                List<FilterStep> nextFilterSteps = new ArrayList<>(filterEntranceSteps.size());
                
                nextFilterSteps.addAll(filterEntranceSteps);
                
                filteredItemFixedPrice = evaluateFilterSteps(nextFilterSteps, filteredItemFixedPrice);
                
                this.item = null;
                this.itemPrice = null;
                this.itemFixedPrice = null;
                cachedFilter = null;
            }
        }
        
        listUnitPrice = null;
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("<<< OfferItemPriceFilterEvaluator.evaluate");
        return filteredItemFixedPrice;
    }
    
}
