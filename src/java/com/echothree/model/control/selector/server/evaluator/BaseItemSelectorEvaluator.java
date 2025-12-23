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

package com.echothree.model.control.selector.server.evaluator;

import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.selector.common.SelectorNodeTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;

public class BaseItemSelectorEvaluator
        extends BaseSelectorEvaluator {
    
    protected ItemControl itemControl = Session.getModelController(ItemControl.class);
    
    private Item item = null;
    
    protected BaseItemSelectorEvaluator(Session session, BasePK evaluatedBy, Class logClass) {
        super(session, evaluatedBy, logClass);
    }
    
    private Boolean evaluateItemCategory(final SelectorNodeDetail snd) {
        var selectorNodeItemCategory = cachedSelector.getSelectorNodeItemCategoryFromSelectorNodeDetail(snd);
        var itemCategory = selectorNodeItemCategory.getItemCategory();
        var itemDetail = item.getLastDetail();
        var tempResult = selectorNodeItemCategory.getItemCategory().equals(itemCategory);
        
        if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
            log.info("--- looking for itemCategory = " + selectorNodeItemCategory.getItemCategory().getLastDetail().getItemCategoryName());
            log.info("--- item is: " + itemDetail.getItemCategory().getLastDetail().getItemCategoryName() + ", tempResult = " + tempResult);
        }
        
        if(!tempResult && selectorNodeItemCategory.getCheckParents()) {
            if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
                log.info("--- checking parents:");
            }
            
            for(itemCategory = itemCategory.getLastDetail().getParentItemCategory();
            itemCategory != null && !tempResult;
            itemCategory = itemCategory.getLastDetail().getParentItemCategory()) {
                tempResult = selectorNodeItemCategory.getItemCategory().equals(itemCategory);
                
                if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
                    log.info("---     " + selectorNodeItemCategory.getItemCategory().getLastDetail().getItemCategoryName() + ", tempResult = " + tempResult);
                }
            }
        }
        
        return snd.getNegate()? !tempResult: tempResult;
    }
    
    private Boolean evaluateItemAccountingCategory(final SelectorNodeDetail snd) {
        var selectorNodeItemAccountingCategory = cachedSelector.getSelectorNodeItemAccountingCategoryFromSelectorNodeDetail(snd);
        var itemAccountingCategory = selectorNodeItemAccountingCategory.getItemAccountingCategory();
        var itemDetail = item.getLastDetail();
        var tempResult = selectorNodeItemAccountingCategory.getItemAccountingCategory().equals(itemAccountingCategory);
        
        if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
            log.info("--- looking for itemAccountingCategory = " + selectorNodeItemAccountingCategory.getItemAccountingCategory().getLastDetail().getItemAccountingCategoryName());
            log.info("--- item is: " + itemDetail.getItemAccountingCategory().getLastDetail().getItemAccountingCategoryName() + ", tempResult = " + tempResult);
        }
        
        if(!tempResult && selectorNodeItemAccountingCategory.getCheckParents()) {
            if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
                log.info("--- checking parents:");
            }
            
            for(itemAccountingCategory = itemAccountingCategory.getLastDetail().getParentItemAccountingCategory();
            itemAccountingCategory != null && !tempResult;
            itemAccountingCategory = itemAccountingCategory.getLastDetail().getParentItemAccountingCategory()) {
                tempResult = selectorNodeItemAccountingCategory.getItemAccountingCategory().equals(itemAccountingCategory);
                
                if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
                    log.info("---     " + selectorNodeItemAccountingCategory.getItemAccountingCategory().getLastDetail().getItemAccountingCategoryName() + ", tempResult = " + tempResult);
                }
            }
        }
        
        return snd.getNegate()? !tempResult: tempResult;
    }
    
    private Boolean evaluateItemPurchasingCategory(final SelectorNodeDetail snd) {
        var selectorNodeItemPurchasingCategory = cachedSelector.getSelectorNodeItemPurchasingCategoryFromSelectorNodeDetail(snd);
        var itemPurchasingCategory = selectorNodeItemPurchasingCategory.getItemPurchasingCategory();
        var itemDetail = item.getLastDetail();
        var tempResult = selectorNodeItemPurchasingCategory.getItemPurchasingCategory().equals(itemPurchasingCategory);
        
        if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
            log.info("--- looking for itemPurchasingCategory = " + selectorNodeItemPurchasingCategory.getItemPurchasingCategory().getLastDetail().getItemPurchasingCategoryName());
            log.info("--- item is: " + itemDetail.getItemPurchasingCategory().getLastDetail().getItemPurchasingCategoryName() + ", tempResult = " + tempResult);
        }
        
        if(!tempResult && selectorNodeItemPurchasingCategory.getCheckParents()) {
            if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
                log.info("--- checking parents:");
            }
            
            for(itemPurchasingCategory = itemPurchasingCategory.getLastDetail().getParentItemPurchasingCategory();
            itemPurchasingCategory != null && !tempResult;
            itemPurchasingCategory = itemPurchasingCategory.getLastDetail().getParentItemPurchasingCategory()) {
                tempResult = selectorNodeItemPurchasingCategory.getItemPurchasingCategory().equals(itemPurchasingCategory);
                
                if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator) {
                    log.info("---     " + selectorNodeItemPurchasingCategory.getItemPurchasingCategory().getLastDetail().getItemPurchasingCategoryName() + ", tempResult = " + tempResult);
                }
            }
        }
        
        return snd.getNegate()? !tempResult: tempResult;
    }
    
    @Override
    protected Boolean evaluateSelectorNode(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator)
            log.info(">>> BaseItemSelectorEvaluator.evaluateSelectorNode(snd = " + snd + ")");
        Boolean result;
        
        if(snd != null) {
            var snt = snd.getSelectorNodeType();
            var sntn = snt.getSelectorNodeTypeName();
            
            if(sntn.equals(SelectorNodeTypes.ITEM_CATEGORY.name())) {
                result = evaluateItemCategory(snd);
            } else if(sntn.equals(SelectorNodeTypes.ITEM_ACCOUNTING_CATEGORY.name())) {
                result = evaluateItemAccountingCategory(snd);
            } else if(sntn.equals(SelectorNodeTypes.ITEM_PURCHASING_CATEGORY.name())) {
                result = evaluateItemPurchasingCategory(snd);
            } else {
                result = super.evaluateSelectorNode(snd);
            }
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator)
                log.info("--- snd was null, evaluating to FALSE");
            result = false;
        }
        
        if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator)
            log.info("<<< BaseItemSelectorEvaluator.evaluateSelectorNode, result = " + result);
        return result;
    }
    
    protected Boolean isItemSelected(CachedSelector cachedSelector, EntityInstance entityInstance, Item item) {
        if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator)
            log.info(">>> BaseItemSelectorEvaluator(cachedSelector = " + cachedSelector + ", entityInstance = " + entityInstance + ", item = " + item + ")");
        
        this.cachedSelector = cachedSelector;
        this.entityInstance = entityInstance;
        this.item = item;

        var result = evaluateSelectorNode(cachedSelector.getRootSelectorNodeDetail());
        
        this.item = null;
        this.entityInstance = null;
        this.cachedSelector = null;
        
        if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator)
            log.info("<<< BaseItemSelectorEvaluator, result = " + result);
        return result;
    }
    
    protected Boolean isItemSelected(CachedSelector cachedSelector, Item item) {
        return isItemSelected(cachedSelector, getEntityInstanceByBasePK(item.getPrimaryKey()), item);
    }
    
}
