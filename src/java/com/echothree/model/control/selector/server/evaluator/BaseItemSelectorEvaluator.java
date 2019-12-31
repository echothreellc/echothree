// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemAccountingCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemPurchasingCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;

public class BaseItemSelectorEvaluator
        extends BaseSelectorEvaluator {
    
    protected ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    
    private Item item = null;
    
    protected BaseItemSelectorEvaluator(Session session, BasePK evaluatedBy, Class logClass) {
        super(session, evaluatedBy, logClass);
    }
    
    private Boolean evaluateItemCategory(final SelectorNodeDetail snd) {
        SelectorNodeItemCategory selectorNodeItemCategory = cachedSelector.getSelectorNodeItemCategoryFromSelectorNodeDetail(snd);
        ItemCategory itemCategory = selectorNodeItemCategory.getItemCategory();
        ItemDetail itemDetail = item.getLastDetail();
        boolean tempResult = selectorNodeItemCategory.getItemCategory().equals(itemCategory);
        
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
        SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory = cachedSelector.getSelectorNodeItemAccountingCategoryFromSelectorNodeDetail(snd);
        ItemAccountingCategory itemAccountingCategory = selectorNodeItemAccountingCategory.getItemAccountingCategory();
        ItemDetail itemDetail = item.getLastDetail();
        boolean tempResult = selectorNodeItemAccountingCategory.getItemAccountingCategory().equals(itemAccountingCategory);
        
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
        SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory = cachedSelector.getSelectorNodeItemPurchasingCategoryFromSelectorNodeDetail(snd);
        ItemPurchasingCategory itemPurchasingCategory = selectorNodeItemPurchasingCategory.getItemPurchasingCategory();
        ItemDetail itemDetail = item.getLastDetail();
        boolean tempResult = selectorNodeItemPurchasingCategory.getItemPurchasingCategory().equals(itemPurchasingCategory);
        
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
            SelectorNodeType snt = snd.getSelectorNodeType();
            String sntn = snt.getSelectorNodeTypeName();
            
            if(sntn.equals(SelectorConstants.SelectorNodeType_ITEM_CATEGORY)) {
                result = evaluateItemCategory(snd);
            } else if(sntn.equals(SelectorConstants.SelectorNodeType_ITEM_ACCOUNTING_CATEGORY)) {
                result = evaluateItemAccountingCategory(snd);
            } else if(sntn.equals(SelectorConstants.SelectorNodeType_ITEM_PURCHASING_CATEGORY)) {
                result = evaluateItemPurchasingCategory(snd);
            } else {
                result = super.evaluateSelectorNode(snd);
            }
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BaseItemSelectorEvaluator)
                log.info("--- snd was null, evaluating to FALSE");
            result = Boolean.FALSE;
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
        
        Boolean result = evaluateSelectorNode(cachedSelector.getRootSelectorNodeDetail());
        
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
