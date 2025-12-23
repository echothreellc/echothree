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

package com.echothree.model.control.workflow.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowEntityStatusTransfer
        extends BaseTransfer {

    private EntityInstanceTransfer entityInstance;
    private WorkflowStepTransfer workflowStep;
    private WorkEffortScopeTransfer workEffortScope;
    private Long unformattedFromTime;
    private String fromTime;
    private Long unformattedThruTime;
    private String thruTime;
    
    private Long unformattedTriggerTime;
    private String triggerTime;

    /** Creates a new instance of WorkflowEntityStatusTransfer */
    public WorkflowEntityStatusTransfer(EntityInstanceTransfer entityInstance, WorkflowStepTransfer workflowStep, WorkEffortScopeTransfer workEffortScope,
            Long unformattedFromTime, String fromTime, Long unformattedThruTime, String thruTime, Long unformattedTriggerTime, String triggerTime) {
        this.entityInstance = entityInstance;
        this.workflowStep = workflowStep;
        this.workEffortScope = workEffortScope;
        this.unformattedFromTime = unformattedFromTime;
        this.fromTime = fromTime;
        this.unformattedThruTime = unformattedThruTime;
        this.thruTime = thruTime;
        this.unformattedTriggerTime = unformattedTriggerTime;
        this.triggerTime = triggerTime;
    }

    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

    public WorkflowStepTransfer getWorkflowStep() {
        return workflowStep;
    }

    public void setWorkflowStep(WorkflowStepTransfer workflowStep) {
        this.workflowStep = workflowStep;
    }

    public WorkEffortScopeTransfer getWorkEffortScope() {
        return workEffortScope;
    }

    public void setWorkEffortScope(WorkEffortScopeTransfer workEffortScope) {
        this.workEffortScope = workEffortScope;
    }

    public Long getUnformattedFromTime() {
        return unformattedFromTime;
    }

    public void setUnformattedFromTime(Long unformattedFromTime) {
        this.unformattedFromTime = unformattedFromTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public Long getUnformattedThruTime() {
        return unformattedThruTime;
    }

    public void setUnformattedThruTime(Long unformattedThruTime) {
        this.unformattedThruTime = unformattedThruTime;
    }

    public String getThruTime() {
        return thruTime;
    }

    public void setThruTime(String thruTime) {
        this.thruTime = thruTime;
    }

    public Long getUnformattedTriggerTime() {
        return unformattedTriggerTime;
    }

    public void setUnformattedTriggerTime(Long unformattedTriggerTime) {
        this.unformattedTriggerTime = unformattedTriggerTime;
    }

    public String getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(String triggerTime) {
        this.triggerTime = triggerTime;
    }
}