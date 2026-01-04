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

import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.selector.common.SelectorNodeTypes;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;

public class BasePartySelectorEvaluator
        extends BaseSelectorEvaluator {
    
    protected PartyControl partyControl = Session.getModelController(PartyControl.class);
    protected EmployeeControl employeeControl = Session.getModelController(EmployeeControl.class);
    protected TrainingControl trainingControl = Session.getModelController(TrainingControl.class);
    
    private Party party = null;
    
    protected BasePartySelectorEvaluator(Session session, BasePK evaluatedBy, Class logClass) {
        super(session, evaluatedBy, logClass);
    }
    
    private Boolean evaluateResponsibilityType(final SelectorNodeDetail snd) {
        var snrt = cachedSelector.getSelectorNodeResponsibilityTypeFromSelectorNodeDetail(snd);
        var pr = employeeControl.getPartyResponsibility(party, snrt.getResponsibilityType());
        var result = !(pr == null);
        
        return snd.getNegate()? !result: result;
    }
    
    private Boolean evaluateSkillType(final SelectorNodeDetail snd) {
        var snst = cachedSelector.getSelectorNodeSkillTypeFromSelectorNodeDetail(snd);
        var ps = employeeControl.getPartySkill(party, snst.getSkillType());
        var result = !(ps == null);
        
        return snd.getNegate()? !result: result;
    }
    
    private Boolean evaluateTrainingClass(final SelectorNodeDetail snd) {
        var sntct = cachedSelector.getSelectorNodeTrainingClassFromSelectorNodeDetail(snd);
        var count = trainingControl.countPartyTrainingClassesUsingNames(party, sntct.getTrainingClass(), PartyTrainingClassStatusConstants.WorkflowStep_PASSED);
        var result = count > 0;
        
        return snd.getNegate()? !result: result;
    }
    
    @Override
    protected Boolean evaluateSelectorNode(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BasePartySelectorEvaluator)
            log.info(">>> BasePartySelectorEvaluator.evaluateSelectorNode(snd = " + snd + ")");
        Boolean result;
        
        if(snd != null) {
            var snt = snd.getSelectorNodeType();
            var sntn = snt.getSelectorNodeTypeName();
            
            if(sntn.equals(SelectorNodeTypes.RESPONSIBILITY_TYPE.name())) {
                result = evaluateResponsibilityType(snd);
            } else if(sntn.equals(SelectorNodeTypes.SKILL_TYPE.name())) {
                result = evaluateSkillType(snd);
            } else if(sntn.equals(SelectorNodeTypes.TRAINING_CLASS.name())) {
                result = evaluateTrainingClass(snd);
            } else {
                result = super.evaluateSelectorNode(snd);
            }
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BasePartySelectorEvaluator)
                log.info("--- snd was null, evaluating to FALSE");
            result = false;
        }
        
        if(BaseSelectorEvaluatorDebugFlags.BasePartySelectorEvaluator)
            log.info("<<< BasePartySelectorEvaluator.evaluateSelectorNode, result = " + result);
        return result;
    }
    
    protected Boolean isPartySelected(CachedSelector cachedSelector, EntityInstance entityInstance, Party party) {
        if(BaseSelectorEvaluatorDebugFlags.BasePartySelectorEvaluator)
            log.info(">>> BasePartySelectorEvaluator(cachedSelector = " + cachedSelector + ", entityInstance = " + entityInstance + ", party = " + party + ")");
        
        this.cachedSelector = cachedSelector;
        this.entityInstance = entityInstance;
        this.party = party;

        var result = evaluateSelectorNode(cachedSelector.getRootSelectorNodeDetail());
        
        this.party = null;
        this.entityInstance = null;
        this.cachedSelector = null;
        
        if(BaseSelectorEvaluatorDebugFlags.BasePartySelectorEvaluator)
            log.info("<<< BasePartySelectorEvaluator, result = " + result);
        return result;
    }
    
    protected Boolean isPartySelected(CachedSelector cachedSelector, Party party) {
        return isPartySelected(cachedSelector, getEntityInstanceByBasePK(party.getPrimaryKey()), party);
    }
    
}
