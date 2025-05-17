// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.ComponentControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.selector.common.SelectorBooleanTypes;
import com.echothree.model.control.selector.common.SelectorNodeTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseSelectorEvaluator {
    
    protected Session session;
    protected BasePK evaluatedBy;
    protected CoreControl coreControl = Session.getModelController(CoreControl.class);
    protected EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    protected EventControl eventControl = Session.getModelController(EventControl.class);
    protected ComponentControl componentControl = Session.getModelController(ComponentControl.class);
    protected EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    protected SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    protected WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    protected Log log;
    
    protected CachedSelector cachedSelector = null;
    protected EntityInstance entityInstance = null;
    
    protected BaseSelectorEvaluator(Session session, BasePK evaluatedBy, Class logClass) {
        this.session = session;
        this.evaluatedBy = evaluatedBy;
        
        if(logClass != null) {
            log = LogFactory.getLog(logClass);
        }  else {
            log = null;
        }
    }
    
    protected EntityInstance getEntityInstanceByBasePK(BasePK pk) {
        return entityInstanceControl.getEntityInstanceByBasePK(pk);
    }
    
    protected EntityInstance getEntityInstanceByBaseEntity(BaseEntity baseEntity) {
        return getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
    }
    
    private boolean evaluateBoolean(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
            log.info("--- evaluateBoolean");
        var snb = cachedSelector.getSelectorNodeBooleanFromSelectorNodeDetail(snd);
        var lsnd = snb.getLeftSelectorNode().getLastDetail(); // Left Selector Node Detail
        var lr = evaluateSelectorNode(lsnd); // Left Result
        boolean lrbv = lr;
        var sbt = snb.getSelectorBooleanType();
        var sbtn = sbt.getSelectorBooleanTypeName();
        var result = false;
        
        if(sbtn.equals(SelectorBooleanTypes.AND.name())) {
            // If its an AND, don't evalute the right side if the left side is already false
            if(lrbv) {
                var rsnd = snb.getRightSelectorNode().getLastDetail(); // Right Selector Node Detail
                var rr = evaluateSelectorNode(rsnd); // Right Result
                
                result = lrbv && rr;
            }
        } else if(sbtn.equals(SelectorBooleanTypes.OR.name())) {
            // If its an OR, don't evaluate the right side if the left side is already true
            if(lrbv) {
                result = true;
            } else {
                var rsnd = snb.getRightSelectorNode().getLastDetail(); // Right Selector Node Detail
                var rr = evaluateSelectorNode(rsnd); // Right Result
                
                result = rr;
            }
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
                log.info("--- sbtn was unknown, evaluating to FALSE");
        }
        
        return snd.getNegate()? !result: result;
    }
    
    private boolean evaluateEntityListItem(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
            log.info("--- evaluateEntityListItem");
        var sneli = cachedSelector.getSelectorNodeEntityListItemFromSelectorNodeDetail(snd);
        var eli = sneli.getEntityListItem();
        var ea = eli.getLastDetail().getEntityAttribute();
        var eat = ea.getLastDetail().getEntityAttributeType();
        var eatn = eat.getEntityAttributeTypeName();
        var result = false;
        
        if(eatn.equals(EntityAttributeTypes.LISTITEM.name())) {
            var elia = coreControl.getEntityListItemAttribute(ea, entityInstance);
            if(elia != null)
                result = elia.getEntityListItem().equals(eli);
        } else if(eatn.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            var emlia = coreControl.getEntityMultipleListItemAttribute(ea, entityInstance, eli);
            result = emlia != null;
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
                log.info("--- eatn was unknown, evaluating to FALSE");
        }
        
        return snd.getNegate()? !result: result;
    }
    
    private boolean evaluateWorkflowStep(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
            log.info("--- evaluateWorkflowStep");
        var wnws = cachedSelector.getSelectorNodeWorkflowStepFromSelectorNodeDetail(snd);
        var ws = wnws.getWorkflowStep();
        var workflow = wnws.getWorkflowStep().getLastDetail().getWorkflow();
        var wess = workflowControl.getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance);
        var result = false;
        
        for(var wes : wess) {
            if(wes.getWorkflowStep().equals(ws)) {
                result = true;
                break;
            }
        }
        
        return snd.getNegate()? !result: result;
    }
    
    protected Boolean evaluateSelectorNode(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
            log.info(">>> BaseSelectorEvaluator.evaluateSelectorNode(snd = " + snd + ")");
        var result = false;
        
        if(snd != null) {
            var snt = snd.getSelectorNodeType();
            var sntn = snt.getSelectorNodeTypeName();
            
            if(sntn.equals(SelectorNodeTypes.BOOLEAN.name())) {
                result = evaluateBoolean(snd);
            } else if(sntn.equals(SelectorNodeTypes.ENTITY_LIST_ITEM.name())) {
                result = evaluateEntityListItem(snd);
            } else if(sntn.equals(SelectorNodeTypes.WORKFLOW_STEP.name())) {
                result = evaluateWorkflowStep(snd);
            } else {
                if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
                    log.info("--- sntn was unknown, evaluating to FALSE");
            }
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
                log.info("--- snd was null, evaluating to FALSE");
        }
        
        if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
            log.info("<<< BaseSelectorEvaluator.evaluateSelectorNode, result = " + result);
        return result;
    }
    
}
