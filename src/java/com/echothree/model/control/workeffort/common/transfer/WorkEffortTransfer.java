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

package com.echothree.model.control.workeffort.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class WorkEffortTransfer
        extends BaseTransfer {
    
    private String workEffortName;
    private EntityInstanceTransfer owningEntityInstance;
    private WorkEffortScopeTransfer workEffortScope;
    private Long unformattedScheduledTime;
    private String scheduledTime;
    private Long unformattedScheduledStartTime;
    private String scheduledStartTime;
    private Long unformattedScheduledEndTime;
    private String scheduledEndTime;
    private Long unformattedEstimatedTimeAllowed;
    private String estimatedTimeAllowed;
    private Long unformattedMaximumTimeAllowed;
    private String maximumTimeAllowed;
    
    private ListWrapper<WorkRequirementTransfer> workRequirements;

    /** Creates a new instance of WorkEffortTransfer */
    public WorkEffortTransfer(String workEffortName, EntityInstanceTransfer owningEntityInstance, WorkEffortScopeTransfer workEffortScope,
            Long unformattedScheduledTime, String scheduledTime, Long unformattedScheduledStartTime, String scheduledStartTime,
            Long unformattedScheduledEndTime, String scheduledEndTime, Long unformattedEstimatedTimeAllowed, String estimatedTimeAllowed,
            Long unformattedMaximumTimeAllowed, String maximumTimeAllowed) {
        this.workEffortName = workEffortName;
        this.owningEntityInstance = owningEntityInstance;
        this.workEffortScope = workEffortScope;
        this.unformattedScheduledTime = unformattedScheduledTime;
        this.scheduledTime = scheduledTime;
        this.unformattedScheduledStartTime = unformattedScheduledStartTime;
        this.scheduledStartTime = scheduledStartTime;
        this.unformattedScheduledEndTime = unformattedScheduledEndTime;
        this.scheduledEndTime = scheduledEndTime;
        this.unformattedEstimatedTimeAllowed = unformattedEstimatedTimeAllowed;
        this.estimatedTimeAllowed = estimatedTimeAllowed;
        this.unformattedMaximumTimeAllowed = unformattedMaximumTimeAllowed;
        this.maximumTimeAllowed = maximumTimeAllowed;
    }

    /**
     * Returns the workEffortName.
     * @return the workEffortName
     */
    public String getWorkEffortName() {
        return workEffortName;
    }

    /**
     * Sets the workEffortName.
     * @param workEffortName the workEffortName to set
     */
    public void setWorkEffortName(String workEffortName) {
        this.workEffortName = workEffortName;
    }

    /**
     * Returns the owningEntityInstance.
     * @return the owningEntityInstance
     */
    public EntityInstanceTransfer getOwningEntityInstance() {
        return owningEntityInstance;
    }

    /**
     * Sets the owningEntityInstance.
     * @param owningEntityInstance the owningEntityInstance to set
     */
    public void setOwningEntityInstance(EntityInstanceTransfer owningEntityInstance) {
        this.owningEntityInstance = owningEntityInstance;
    }

    /**
     * Returns the workEffortScope.
     * @return the workEffortScope
     */
    public WorkEffortScopeTransfer getWorkEffortScope() {
        return workEffortScope;
    }

    /**
     * Sets the workEffortScope.
     * @param workEffortScope the workEffortScope to set
     */
    public void setWorkEffortScope(WorkEffortScopeTransfer workEffortScope) {
        this.workEffortScope = workEffortScope;
    }

    /**
     * Returns the unformattedScheduledTime.
     * @return the unformattedScheduledTime
     */
    public Long getUnformattedScheduledTime() {
        return unformattedScheduledTime;
    }

    /**
     * Sets the unformattedScheduledTime.
     * @param unformattedScheduledTime the unformattedScheduledTime to set
     */
    public void setUnformattedScheduledTime(Long unformattedScheduledTime) {
        this.unformattedScheduledTime = unformattedScheduledTime;
    }

    /**
     * Returns the scheduledTime.
     * @return the scheduledTime
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     * Sets the scheduledTime.
     * @param scheduledTime the scheduledTime to set
     */
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * Returns the unformattedScheduledStartTime.
     * @return the unformattedScheduledStartTime
     */
    public Long getUnformattedScheduledStartTime() {
        return unformattedScheduledStartTime;
    }

    /**
     * Sets the unformattedScheduledStartTime.
     * @param unformattedScheduledStartTime the unformattedScheduledStartTime to set
     */
    public void setUnformattedScheduledStartTime(Long unformattedScheduledStartTime) {
        this.unformattedScheduledStartTime = unformattedScheduledStartTime;
    }

    /**
     * Returns the scheduledStartTime.
     * @return the scheduledStartTime
     */
    public String getScheduledStartTime() {
        return scheduledStartTime;
    }

    /**
     * Sets the scheduledStartTime.
     * @param scheduledStartTime the scheduledStartTime to set
     */
    public void setScheduledStartTime(String scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    /**
     * Returns the unformattedScheduledEndTime.
     * @return the unformattedScheduledEndTime
     */
    public Long getUnformattedScheduledEndTime() {
        return unformattedScheduledEndTime;
    }

    /**
     * Sets the unformattedScheduledEndTime.
     * @param unformattedScheduledEndTime the unformattedScheduledEndTime to set
     */
    public void setUnformattedScheduledEndTime(Long unformattedScheduledEndTime) {
        this.unformattedScheduledEndTime = unformattedScheduledEndTime;
    }

    /**
     * Returns the scheduledEndTime.
     * @return the scheduledEndTime
     */
    public String getScheduledEndTime() {
        return scheduledEndTime;
    }

    /**
     * Sets the scheduledEndTime.
     * @param scheduledEndTime the scheduledEndTime to set
     */
    public void setScheduledEndTime(String scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
    }

    /**
     * Returns the unformattedEstimatedTimeAllowed.
     * @return the unformattedEstimatedTimeAllowed
     */
    public Long getUnformattedEstimatedTimeAllowed() {
        return unformattedEstimatedTimeAllowed;
    }

    /**
     * Sets the unformattedEstimatedTimeAllowed.
     * @param unformattedEstimatedTimeAllowed the unformattedEstimatedTimeAllowed to set
     */
    public void setUnformattedEstimatedTimeAllowed(Long unformattedEstimatedTimeAllowed) {
        this.unformattedEstimatedTimeAllowed = unformattedEstimatedTimeAllowed;
    }

    /**
     * Returns the estimatedTimeAllowed.
     * @return the estimatedTimeAllowed
     */
    public String getEstimatedTimeAllowed() {
        return estimatedTimeAllowed;
    }

    /**
     * Sets the estimatedTimeAllowed.
     * @param estimatedTimeAllowed the estimatedTimeAllowed to set
     */
    public void setEstimatedTimeAllowed(String estimatedTimeAllowed) {
        this.estimatedTimeAllowed = estimatedTimeAllowed;
    }

    /**
     * Returns the unformattedMaximumTimeAllowed.
     * @return the unformattedMaximumTimeAllowed
     */
    public Long getUnformattedMaximumTimeAllowed() {
        return unformattedMaximumTimeAllowed;
    }

    /**
     * Sets the unformattedMaximumTimeAllowed.
     * @param unformattedMaximumTimeAllowed the unformattedMaximumTimeAllowed to set
     */
    public void setUnformattedMaximumTimeAllowed(Long unformattedMaximumTimeAllowed) {
        this.unformattedMaximumTimeAllowed = unformattedMaximumTimeAllowed;
    }

    /**
     * Returns the maximumTimeAllowed.
     * @return the maximumTimeAllowed
     */
    public String getMaximumTimeAllowed() {
        return maximumTimeAllowed;
    }

    /**
     * Sets the maximumTimeAllowed.
     * @param maximumTimeAllowed the maximumTimeAllowed to set
     */
    public void setMaximumTimeAllowed(String maximumTimeAllowed) {
        this.maximumTimeAllowed = maximumTimeAllowed;
    }

    /**
     * Returns the workRequirements.
     * @return the workRequirements
     */
    public ListWrapper<WorkRequirementTransfer> getWorkRequirements() {
        return workRequirements;
    }

    /**
     * Sets the workRequirements.
     * @param workRequirements the workRequirements to set
     */
    public void setWorkRequirements(ListWrapper<WorkRequirementTransfer> workRequirements) {
        this.workRequirements = workRequirements;
    }
    
}
