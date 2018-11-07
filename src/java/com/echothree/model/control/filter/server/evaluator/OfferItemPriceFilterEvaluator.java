// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.evaluator.FilterItemSelectorEvaluator;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDetail;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterDetail;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDetail;
import com.echothree.model.data.filter.server.entity.FilterStepElementDetail;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
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
        super(session, evaluatedBy, OfferItemPriceFilterEvaluator.class, FilterConstants.FilterKind_PRICE,
                FilterConstants.FilterType_OFFER_ITEM_PRICE, SelectorConstants.SelectorType_FILTER);
        
        filterItemSelectorEvaluator = new FilterItemSelectorEvaluator(session, evaluatedBy);
    }
    
    protected FilteredItemFixedPrice applyFilterFixedPriceAdjustment(FilteredItemFixedPrice filteredItemFixedPrice, FilterAdjustment filterAdjustment) {
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info(">>> OfferItemPriceFilterEvaluator.applyFilterFixedPriceAdjustment");
        
        FilterAdjustmentDetail filterAdjustmentDetail = filterAdjustment.getLastDetail();
        String filterAdjustmentSourceName = filterAdjustmentDetail.getFilterAdjustmentSource().getFilterAdjustmentSourceName();
        FilterAdjustmentType filterAdjustmentType = filterAdjustmentDetail.getFilterAdjustmentType();
        long initialUnitPrice = filteredItemFixedPrice.getUnitPrice();
        long unitPrice = initialUnitPrice;
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("--- filterAdjustmentName = " + filterAdjustmentDetail.getFilterAdjustmentName());
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("--- filterAdjustmentSourceName = " + filterAdjustmentSourceName);
        if(filterAdjustmentSourceName.equals(FilterConstants.FilterAdjustmentSource_CURRENT)) {
            // No source price changes.
        } else if(filterAdjustmentSourceName.equals(FilterConstants.FilterAdjustmentSource_INVENTORY_COST)) {
            // TODO
        } else if(filterAdjustmentSourceName.equals(FilterConstants.FilterAdjustmentSource_ITEM_PRICE)) {
            unitPrice = listUnitPrice;
        } else if(filterAdjustmentSourceName.equals(FilterConstants.FilterAdjustmentSource_PRIMARY_VENDOR_COST)) {
            // TODO
        } else if(filterAdjustmentSourceName.equals(FilterConstants.FilterAdjustmentSource_VENDOR_COST)) {
            // TODO
        } else if(filterAdjustmentSourceName.equals(FilterConstants.FilterAdjustmentSource_SET_AMOUNT)) {
            // No change here, taken care of by filterAdjustmentType
            if(filterAdjustmentType == null)
                throw new IllegalArgumentException("filterAdjustmentType is required when FilterAdjustmentSource_SET_AMOUNT");
        } else
            throw new IllegalArgumentException("Unknown filterAdjustmentSourceName");
        
        if(filterAdjustmentType != null) {
            String filterAdjustmentTypeName = filterAdjustmentType.getFilterAdjustmentTypeName();
            UnitOfMeasureType unitOfMeasureType = filteredItemFixedPrice.getUnitOfMeasureType();
            Currency currency = filteredItemFixedPrice.getCurrency();
            
            if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
                log.info("--- filterAdjustmentTypeName = " + filterAdjustmentTypeName);
            if(filterAdjustmentTypeName.equals(FilterConstants.FilterAdjustmentType_AMOUNT)) {
                FilterAdjustmentAmount filterAdjustmentAmount = filterControl.getFilterAdjustmentAmount(filterAdjustment, unitOfMeasureType, currency);
                
                if(filterAdjustmentAmount != null) {
                    long amount = filterAdjustmentAmount.getAmount();
                    
                    unitPrice += amount;
                    if(unitPrice < 0)
                        unitPrice = 0;
                }
            } else if(filterAdjustmentTypeName.equals(FilterConstants.FilterAdjustmentType_FIXED_AMOUNT)) {
                FilterAdjustmentFixedAmount filterAdjustmentFixedAmount = filterControl.getFilterAdjustmentFixedAmount(filterAdjustment, unitOfMeasureType, currency);
                
                if(filterAdjustmentFixedAmount != null) {
                    unitPrice = filterAdjustmentFixedAmount.getUnitAmount();
                }
            } else if(filterAdjustmentTypeName.equals(FilterConstants.FilterAdjustmentType_PERCENT)) {
                FilterAdjustmentPercent filterAdjustmentPercent = filterControl.getFilterAdjustmentPercent(filterAdjustment, unitOfMeasureType, currency);
                
                if(filterAdjustmentPercent != null) {
                    int percent = filterAdjustmentPercent.getPercent();
                    
                    unitPrice = unitPrice - (unitPrice * percent / 100000);
                }
            } else
                throw new IllegalArgumentException("Unknown filterAdjustmentTypeName");
        }
        
        if(initialUnitPrice != unitPrice) {
            filteredItemFixedPrice = filteredItemFixedPrice.clone();
            filteredItemFixedPrice.setUnitPrice(unitPrice);
        }
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info("<<< OfferItemPriceFilterEvaluator.applyFilterFixedPriceAdjustment, initialUnitPrice = " + initialUnitPrice + ", unitPrice = " + unitPrice);
        return filteredItemFixedPrice;
    }
    
    protected FilteredItemFixedPrice evaluateFilterStep(FilterStep filterStep, FilteredItemFixedPrice filteredItemFixedPrice) {
        FilterStepDetail filterStepDetail = filterStep.getLastDetail();
        
        if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
            log.info(">>> OfferItemPriceFilterEvaluator.evaluateFilterStep, filterStepName = " + filterStepDetail.getFilterStepName());
        
        Selector filterItemSelector = filterStepDetail.getFilterItemSelector();
        boolean useFilterStep = filterItemSelector == null? true: filterItemSelectorEvaluator.evaluate(selectorCache.getSelector(filterItemSelector), item);
        
        if(useFilterStep) {
            List<FilterStepElementDetail> filterStepElementDetails = cachedFilter.getFilterStepElementDetailsByFilterStep(filterStep);
            TreeSet<FilteredItemFixedPrice> evaluatedPrices = new TreeSet<>();
            
            for(FilterStepElementDetail filterStepElementDetail : filterStepElementDetails) {
                if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator)
                    log.info("--- filterStepElementName = " + filterStepElementDetail.getFilterStepElementName());
                
                filterItemSelector = filterStepElementDetail.getFilterItemSelector();
                boolean useFilterStepElement = filterItemSelector == null? true: filterItemSelectorEvaluator.evaluate(selectorCache.getSelector(filterItemSelector), item);
                if(useFilterStepElement) {
                    FilterAdjustment filterAdjustment = filterStepElementDetail.getFilterAdjustment();
                    
                    evaluatedPrices.add(applyFilterFixedPriceAdjustment(filteredItemFixedPrice, filterAdjustment));
                }
            }
            
            if(!evaluatedPrices.isEmpty())
                filteredItemFixedPrice = (FilteredItemFixedPrice)evaluatedPrices.first();
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
        
        TreeSet<FilteredItemFixedPrice> evaluatedPrices = new TreeSet<>();
        for(FilterStep filterStep : nextFilterSteps) {
            FilteredItemFixedPrice evaluatedPrice = evaluateFilterStep(filterStep, filteredItemFixedPrice);
            
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
        
        UnitOfMeasureType unitOfMeasureType = itemPrice.getUnitOfMeasureType();
        Currency currency = itemPrice.getCurrency();
        listUnitPrice = itemFixedPrice.getUnitPrice();
        
        FilteredItemFixedPrice filteredItemFixedPrice = new FilteredItemFixedPrice(item, unitOfMeasureType, currency, listUnitPrice);
        
        if(filter != null) {
            FilterDetail filterDetail = filter.getLastDetail();
            Selector selector = filterDetail.getFilterItemSelector();
            boolean useFilter = selector == null? true: filterItemSelectorEvaluator.evaluate(selectorCache.getSelector(selector), item);
            
            if(BaseFilterEvaluatorDebugFlags.OfferItemPriceFilterEvaluator) {
                log.info("--- filterName = " + filterDetail.getFilterName() + ", selectorName = " + selector == null? "none": selector.getLastDetail().getSelectorName()
                + ", useFilter = " + useFilter);
            }
            
            if(useFilter) {
                FilterAdjustment initialFilterAdjustment = filterDetail.getInitialFilterAdjustment();
                
                cachedFilter = filterCache.getFilter(filter);
                this.item = item;
                this.itemPrice = itemPrice;
                this.itemFixedPrice = itemFixedPrice;
                
                filteredItemFixedPrice = applyFilterFixedPriceAdjustment(filteredItemFixedPrice, initialFilterAdjustment);
                
                List<FilterStep> filterEntranceSteps = cachedFilter.getFilterEntranceSteps();
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
