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

import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorNodeBoolean;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.model.data.selector.server.entity.SelectorNodeEntityListItem;
import com.echothree.model.data.selector.server.entity.SelectorNodeGeoCode;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemAccountingCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemPurchasingCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeResponsibilityType;
import com.echothree.model.data.selector.server.entity.SelectorNodeSkillType;
import com.echothree.model.data.selector.server.entity.SelectorNodeTrainingClass;
import com.echothree.model.data.selector.server.entity.SelectorNodeWorkflowStep;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;
import java.util.Map;

public class CachedSelector {
    
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    
    Selector selector;
    
    SelectorNodeDetail rootSelectorNodeDetail;
    
    Map<SelectorNode, SelectorNodeDetail> selectorNodes;
    Map<SelectorNode, SelectorNodeBoolean> selectorNodeBooleans;
    Map<SelectorNode, SelectorNodeEntityListItem> selectorNodeEntityListItems;
    Map<SelectorNode, SelectorNodeWorkflowStep> selectorNodeWorkflowSteps;
    Map<SelectorNode, SelectorNodeResponsibilityType> selectorNodeResponsibilityTypes;
    Map<SelectorNode, SelectorNodeSkillType> selectorNodeSkillTypes;
    Map<SelectorNode, SelectorNodeTrainingClass> selectorNodeTrainingClasses;
    Map<SelectorNode, SelectorNodeGeoCode> selectorNodeGeoCodes;
    Map<SelectorNode, SelectorNodeItemCategory> selectorNodeItemCategories;
    Map<SelectorNode, SelectorNodeItemAccountingCategory> selectorNodeItemAccountingCategories;
    Map<SelectorNode, SelectorNodeItemPurchasingCategory> selectorNodeItemPurchasingCategories;
    
    /** Create a new instance of CachedSelector and fill in the details that are cached for the Selector */
    /** Creates a new instance of CachedSelector */
    public CachedSelector(Selector selector) {
        init(selector);
    }
    
    private void init(final Selector selector) {
        var selectorDetail = selector.getLastDetail();
        var selectorKindName = selectorDetail.getSelectorType().getLastDetail().getSelectorKind().getLastDetail().getSelectorKindName();
        
        this.selector = selector;
        
        cacheSelectorNodes();
        cacheSelectorNodeBooleans();
        cacheSelectorNodeEntityListItems();
        cacheSelectorNodeWorkflowSteps();
        
        if(selectorKindName.equals(SelectorKinds.CUSTOMER.name()) ||
                selectorKindName.equals(SelectorKinds.EMPLOYEE.name()) ||
                selectorKindName.equals(SelectorKinds.VENDOR.name())) {
            cacheSelectorNodeResponsibilityTypes();
            cacheSelectorNodeSkillTypes();
            cacheSelectorNodeTrainingClasses();
        }
        
        if(selectorKindName.equals(SelectorKinds.POSTAL_ADDRESS.name())) {
            cacheSelectorNodeGeoCodes();
        }
        
        if(selectorKindName.equals(SelectorKinds.ITEM.name())) {
            cacheSelectorNodeItemCategories();
            cacheSelectorNodeItemAccountingCategories();
            cacheSelectorNodeItemPurchasingCategories();
        }
    }
    
    /** Copy the rawSelectorNodes into the selectorNodes Map that uses the SelectorNode as the key, and
     * the SelectorNodeDetail as the value.
     */
    private void cacheSelectorNodes() {
        var rawSelectorNodes = selectorControl.getSelectorNodesBySelector(selector);
        var size = rawSelectorNodes.size();
        
        selectorNodes = new HashMap<>(size);
        
        if(size > 0) {
            rawSelectorNodes.forEach((selectorNode) -> {
                var selectorNodeDetail = selectorNode.getLastDetail();
                
                if(selectorNodeDetail.getIsRootSelectorNode()) {
                    rootSelectorNodeDetail = selectorNodeDetail;
                }
                
                selectorNodes.put(selectorNode, selectorNodeDetail);
            });
        }
    }
    
    /** Copy rawSelectorNodeBooleans into the selectorNodeBooleans Map, SelectorNode is the key,
     * SelectorNodeBoolean is the value.
     */
    private void cacheSelectorNodeBooleans() {
        var rawSelectorNodeBooleans = selectorControl.getSelectorNodeBooleansBySelector(selector);
        var size = rawSelectorNodeBooleans.size();
        
        if(size > 0) {
            selectorNodeBooleans = new HashMap<>(size);
            
            rawSelectorNodeBooleans.forEach((selectorNodeBoolean) -> {
                selectorNodeBooleans.put(selectorNodeBoolean.getSelectorNode(), selectorNodeBoolean);
            });
        } else
            selectorNodeBooleans = null;
    }
    
    /** Copy rawSelectorNodeEntityListItems into the selectorNodeEntityListItems Map, SelectorNode is the key,
     * SelectorNodeEntityListItem is the value.
     */
    private void cacheSelectorNodeEntityListItems() {
        var rawSelectorNodeEntityListItems = selectorControl.getSelectorNodeEntityListItemsBySelector(selector);
        var size = rawSelectorNodeEntityListItems.size();
        
        if(size > 0) {
            selectorNodeEntityListItems = new HashMap<>(size);
            
            rawSelectorNodeEntityListItems.forEach((selectorNodeEntityListItem) -> {
                selectorNodeEntityListItems.put(selectorNodeEntityListItem.getSelectorNode(), selectorNodeEntityListItem);
            });
        } else
            selectorNodeEntityListItems = null;
    }
    
    /** Copy rawSelectorNodeWorkflowSteps into the selectorNodeWorkflowSteps Map, SelectorNode is the key,
     * SelectorNodeWorkflowStep is the value.
     */
    private void cacheSelectorNodeWorkflowSteps() {
        var rawSelectorNodeWorkflowSteps = selectorControl.getSelectorNodeWorkflowStepsBySelector(selector);
        var size = rawSelectorNodeWorkflowSteps.size();
        
        if(size > 0) {
            selectorNodeWorkflowSteps = new HashMap<>(size);
            
            rawSelectorNodeWorkflowSteps.forEach((selectorNodeWorkflowStep) -> {
                selectorNodeWorkflowSteps.put(selectorNodeWorkflowStep.getSelectorNode(), selectorNodeWorkflowStep);
            });
        } else
            selectorNodeWorkflowSteps = null;
    }
    
    /** Copy rawSelectorNodeResponsibilityTypes into the selectorNodeResponsibilityTypes Map, SelectorNode is the key,
     * SelectorNodeResponsibilityType is the value.
     */
    private void cacheSelectorNodeResponsibilityTypes() {
        var rawSelectorNodeResponsibilityTypes = selectorControl.getSelectorNodeResponsibilityTypesBySelector(selector);
        var size = rawSelectorNodeResponsibilityTypes.size();
        
        if(size > 0) {
            selectorNodeResponsibilityTypes = new HashMap<>(size);
            
            rawSelectorNodeResponsibilityTypes.forEach((selectorNodeResponsibilityType) -> {
                selectorNodeResponsibilityTypes.put(selectorNodeResponsibilityType.getSelectorNode(), selectorNodeResponsibilityType);
            });
        } else
            selectorNodeResponsibilityTypes = null;
    }
    
    /** Copy rawSelectorNodeSkillTypes into the selectorNodeSkillTypes Map, SelectorNode is the key,
     * SelectorNodeSkillType is the value.
     */
    private void cacheSelectorNodeSkillTypes() {
        var rawSelectorNodeSkillTypes = selectorControl.getSelectorNodeSkillTypesBySelector(selector);
        var size = rawSelectorNodeSkillTypes.size();
        
        if(size > 0) {
            selectorNodeSkillTypes = new HashMap<>(size);
            
            rawSelectorNodeSkillTypes.forEach((selectorNodeSkillType) -> {
                selectorNodeSkillTypes.put(selectorNodeSkillType.getSelectorNode(), selectorNodeSkillType);
            });
        } else
            selectorNodeSkillTypes = null;
    }
    
    /** Copy rawSelectorNodeTrainingClasses into the selectorNodeTrainingClasses Map, SelectorNode is the key,
     * SelectorNodeTrainingClass is the value.
     */
    private void cacheSelectorNodeTrainingClasses() {
        var rawSelectorNodeTrainingClasses = selectorControl.getSelectorNodeTrainingClassesBySelector(selector);
        var size = rawSelectorNodeTrainingClasses.size();
        
        if(size > 0) {
            selectorNodeTrainingClasses = new HashMap<>(size);
            
            rawSelectorNodeTrainingClasses.forEach((selectorNodeTrainingClass) -> {
                selectorNodeTrainingClasses.put(selectorNodeTrainingClass.getSelectorNode(), selectorNodeTrainingClass);
            });
        } else
            selectorNodeTrainingClasses = null;
    }
    
    /** Copy rawSelectorNodeGeoCodes into the selectorNodeGeoCodes Map, SelectorNode is the key,
     * SelectorNodeGeoCode is the value.
     */
    private void cacheSelectorNodeGeoCodes() {
        var rawSelectorNodeGeoCodes = selectorControl.getSelectorNodeGeoCodesBySelector(selector);
        var size = rawSelectorNodeGeoCodes.size();
        
        if(size > 0) {
            selectorNodeGeoCodes = new HashMap<>(size);
            
            rawSelectorNodeGeoCodes.forEach((selectorNodeGeoCode) -> {
                selectorNodeGeoCodes.put(selectorNodeGeoCode.getSelectorNode(), selectorNodeGeoCode);
            });
        } else
            selectorNodeGeoCodes = null;
    }
    
    /** Copy rawSelectorNodeItemCategories into the selectorNodeItemCategories Map, SelectorNode is the key,
     * SelectorNodeItemCategory is the value.
     */
    private void cacheSelectorNodeItemCategories() {
        var rawSelectorNodeItemCategories = selectorControl.getSelectorNodeItemCategoriesBySelector(selector);
        var size = rawSelectorNodeItemCategories.size();
        
        if(size > 0) {
            selectorNodeItemCategories = new HashMap<>(size);
            
            rawSelectorNodeItemCategories.forEach((selectorNodeItemCategory) -> {
                selectorNodeItemCategories.put(selectorNodeItemCategory.getSelectorNode(), selectorNodeItemCategory);
            });
        } else
            selectorNodeItemCategories = null;
    }
    
    /** Copy rawSelectorNodeItemAccountingCategories into the selectorNodeItemAccountingCategories Map, SelectorNode is the key,
     * SelectorNodeItemAccountingCategory is the value.
     */
    private void cacheSelectorNodeItemAccountingCategories() {
        var rawSelectorNodeItemAccountingCategories = selectorControl.getSelectorNodeItemAccountingCategoriesBySelector(selector);
        var size = rawSelectorNodeItemAccountingCategories.size();
        
        if(size > 0) {
            selectorNodeItemAccountingCategories = new HashMap<>(size);
            
            rawSelectorNodeItemAccountingCategories.forEach((selectorNodeItemAccountingCategory) -> {
                selectorNodeItemAccountingCategories.put(selectorNodeItemAccountingCategory.getSelectorNode(), selectorNodeItemAccountingCategory);
            });
        } else
            selectorNodeItemAccountingCategories = null;
    }
    
    /** Copy rawSelectorNodeItemPurchasingCategories into the selectorNodeItemPurchasingCategories Map, SelectorNode is the key,
     * SelectorNodeItemPurchasingCategory is the value.
     */
    private void cacheSelectorNodeItemPurchasingCategories() {
        var rawSelectorNodeItemPurchasingCategories = selectorControl.getSelectorNodeItemPurchasingCategoriesBySelector(selector);
        var size = rawSelectorNodeItemPurchasingCategories.size();
        
        if(size > 0) {
            selectorNodeItemPurchasingCategories = new HashMap<>(size);
            
            rawSelectorNodeItemPurchasingCategories.forEach((selectorNodeItemPurchasingCategory) -> {
                selectorNodeItemPurchasingCategories.put(selectorNodeItemPurchasingCategory.getSelectorNode(), selectorNodeItemPurchasingCategory);
            });
        } else
            selectorNodeItemPurchasingCategories = null;
    }
    
    public Selector getSelector() {
        return selector;
    }
    
    public SelectorNodeDetail getRootSelectorNodeDetail() {
        return rootSelectorNodeDetail;
    }
    
    public SelectorNodeDetail getSelectorNodeDetailFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodes.get(selectorNode);
    }
    
    public SelectorNodeBoolean getSelectorNodeBooleanFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeBooleans.get(selectorNode);
    }
    
    public SelectorNodeBoolean getSelectorNodeBooleanFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeBooleans.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeEntityListItem getSelectorNodeEntityListItemFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeEntityListItems.get(selectorNode);
    }
    
    public SelectorNodeEntityListItem getSelectorNodeEntityListItemFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeEntityListItems.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeWorkflowStep getSelectorNodeWorkflowStepFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeWorkflowSteps.get(selectorNode);
    }
    
    public SelectorNodeWorkflowStep getSelectorNodeWorkflowStepFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeWorkflowSteps.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeResponsibilityType getSelectorNodeResponsibilityTypeFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeResponsibilityTypes.get(selectorNode);
    }
    
    public SelectorNodeResponsibilityType getSelectorNodeResponsibilityTypeFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeResponsibilityTypes.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeSkillType getSelectorNodeSkillTypeFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeSkillTypes.get(selectorNode);
    }
    
    public SelectorNodeSkillType getSelectorNodeSkillTypeFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeSkillTypes.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeTrainingClass getSelectorNodeTrainingClassFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeTrainingClasses.get(selectorNode);
    }
    
    public SelectorNodeTrainingClass getSelectorNodeTrainingClassFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeTrainingClasses.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeGeoCode getSelectorNodeGeoCodeFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeGeoCodes.get(selectorNode);
    }
    
    public SelectorNodeGeoCode getSelectorNodeGeoCodeFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeGeoCodes.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeItemCategory getSelectorNodeItemCategoryFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeItemCategories.get(selectorNode);
    }
    
    public SelectorNodeItemCategory getSelectorNodeItemCategoryFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeItemCategories.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeItemAccountingCategory getSelectorNodeItemAccountingCategoryFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeItemAccountingCategories.get(selectorNode);
    }
    
    public SelectorNodeItemAccountingCategory getSelectorNodeItemAccountingCategoryFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeItemAccountingCategories.get(selectorNodeDetail.getSelectorNode());
    }
    
    public SelectorNodeItemPurchasingCategory getSelectorNodeItemPurchasingCategoryFromSelectorNode(SelectorNode selectorNode) {
        return selectorNodeItemPurchasingCategories.get(selectorNode);
    }
    
    public SelectorNodeItemPurchasingCategory getSelectorNodeItemPurchasingCategoryFromSelectorNodeDetail(SelectorNodeDetail selectorNodeDetail) {
        return selectorNodeItemPurchasingCategories.get(selectorNodeDetail.getSelectorNode());
    }
    
}
