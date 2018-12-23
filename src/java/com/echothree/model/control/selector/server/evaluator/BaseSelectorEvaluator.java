// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.selector.server.entity.SelectorBooleanType;
import com.echothree.model.data.selector.server.entity.SelectorNodeBoolean;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.model.data.selector.server.entity.SelectorNodeEntityListItem;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.model.data.selector.server.entity.SelectorNodeWorkflowStep;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseSelectorEvaluator {
    
    protected Session session;
    protected BasePK evaluatedBy;
    protected CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    protected SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
    protected WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
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
        return coreControl.getEntityInstanceByBasePK(pk);
    }
    
    protected EntityInstance getEntityInstanceByBaseEntity(BaseEntity baseEntity) {
        return getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
    }
    
    private boolean evaluateBoolean(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BaseSelectorEvaluator)
            log.info("--- evaluateBoolean");
        SelectorNodeBoolean snb = cachedSelector.getSelectorNodeBooleanFromSelectorNodeDetail(snd);
        SelectorNodeDetail lsnd = snb.getLeftSelectorNode().getLastDetail(); // Left Selector Node Detail
        Boolean lr = evaluateSelectorNode(lsnd); // Left Result
        boolean lrbv = lr;
        SelectorBooleanType sbt = snb.getSelectorBooleanType();
        String sbtn = sbt.getSelectorBooleanTypeName();
        boolean result = false;
        
        if(sbtn.equals(SelectorConstants.SelectorBooleanType_AND)) {
            // If its an AND, don't evalute the right side if the left side is already false
            if(lrbv) {
                SelectorNodeDetail rsnd = snb.getRightSelectorNode().getLastDetail(); // Right Selector Node Detail
                Boolean rr = evaluateSelectorNode(rsnd); // Right Result
                
                result = lrbv && rr;
            }
        } else if(sbtn.equals(SelectorConstants.SelectorBooleanType_OR)) {
            // If its an OR, don't evaluate the right side if the left side is already true
            if(lrbv) {
                result = true;
            } else {
                SelectorNodeDetail rsnd = snb.getRightSelectorNode().getLastDetail(); // Right Selector Node Detail
                Boolean rr = evaluateSelectorNode(rsnd); // Right Result
                
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
        SelectorNodeEntityListItem sneli = cachedSelector.getSelectorNodeEntityListItemFromSelectorNodeDetail(snd);
        EntityListItem eli = sneli.getEntityListItem();
        EntityAttribute ea = eli.getLastDetail().getEntityAttribute();
        EntityAttributeType eat = ea.getLastDetail().getEntityAttributeType();
        String eatn = eat.getEntityAttributeTypeName();
        boolean result = false;
        
        if(eatn.equals(EntityAttributeTypes.LISTITEM.name())) {
            EntityListItemAttribute elia = coreControl.getEntityListItemAttribute(ea, entityInstance);
            if(elia != null)
                result = elia.getEntityListItem().equals(eli);
        } else if(eatn.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            EntityMultipleListItemAttribute emlia = coreControl.getEntityMultipleListItemAttribute(ea, entityInstance, eli);
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
        SelectorNodeWorkflowStep wnws = cachedSelector.getSelectorNodeWorkflowStepFromSelectorNodeDetail(snd);
        WorkflowStep ws = wnws.getWorkflowStep();
        Workflow workflow = wnws.getWorkflowStep().getLastDetail().getWorkflow();
        List<WorkflowEntityStatus> wess = workflowControl.getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance);
        boolean result = false;
        
        for(WorkflowEntityStatus wes: wess) {
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
        boolean result = false;
        
        if(snd != null) {
            SelectorNodeType snt = snd.getSelectorNodeType();
            String sntn = snt.getSelectorNodeTypeName();
            
            if(sntn.equals(SelectorConstants.SelectorNodeType_BOOLEAN)) {
                result = evaluateBoolean(snd);
            } else if(sntn.equals(SelectorConstants.SelectorNodeType_ENTITY_LIST_ITEM)) {
                result = evaluateEntityListItem(snd);
            } else if(sntn.equals(SelectorConstants.SelectorNodeType_WORKFLOW_STEP)) {
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
