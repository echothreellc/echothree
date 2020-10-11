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

package com.echothree.model.control.workeffort.server.logic;

import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScopeDetail;
import com.echothree.model.data.workeffort.server.entity.WorkEffortTypeDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class WorkEffortLogic {
    
    private WorkEffortLogic() {
        super();
    }
    
    private static class WorkEffortLogicHolder {
        static WorkEffortLogic instance = new WorkEffortLogic();
    }
    
    public static WorkEffortLogic getInstance() {
        return WorkEffortLogicHolder.instance;
    }

    public static class PreparedWorkEffort {

        private WorkEffortScope workEffortScope;
        private Sequence workEffortSequence;
        private Long scheduledTime;
        private Long estimatedTimeAllowed;
        private Long maximumTimeAllowed;

        /**
         * @return the workEffortScope
         */
        public WorkEffortScope getWorkEffortScope() {
            return workEffortScope;
        }

        /**
         * @param workEffortScope the workEffortScope to set
         */
        public void setWorkEffortScope(WorkEffortScope workEffortScope) {
            this.workEffortScope = workEffortScope;
        }

        /**
         * @return the workEffortSequence
         */
        public Sequence getWorkEffortSequence() {
            return workEffortSequence;
        }

        /**
         * @param workEffortSequence the workEffortSequence to set
         */
        public void setWorkEffortSequence(Sequence workEffortSequence) {
            this.workEffortSequence = workEffortSequence;
        }

        /**
         * @return the scheduledTime
         */
        public Long getScheduledTime() {
            return scheduledTime;
        }

        /**
         * @param scheduledTime the scheduledTime to set
         */
        public void setScheduledTime(Long scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        /**
         * @return the estimatedTimeAllowed
         */
        public Long getEstimatedTimeAllowed() {
            return estimatedTimeAllowed;
        }

        /**
         * @param estimatedTimeAllowed the estimatedTimeAllowed to set
         */
        public void setEstimatedTimeAllowed(Long estimatedTimeAllowed) {
            this.estimatedTimeAllowed = estimatedTimeAllowed;
        }

        /**
         * @return the maximumTimeAllowed
         */
        public Long getMaximumTimeAllowed() {
            return maximumTimeAllowed;
        }

        /**
         * @param maximumTimeAllowed the maximumTimeAllowed to set
         */
        public void setMaximumTimeAllowed(Long maximumTimeAllowed) {
            this.maximumTimeAllowed = maximumTimeAllowed;
        }

    }

    public PreparedWorkEffort prepareForWorkEffort(final ExecutionErrorAccumulator ema, final WorkEffortScope workEffortScope, Long scheduledTime,
            Long estimatedTimeAllowed, Long maximumTimeAllowed) {
        PreparedWorkEffort preparedWorkEffort = new PreparedWorkEffort();
        WorkEffortScopeDetail workEffortScopeDetail = workEffortScope.getLastDetail();
        WorkEffortTypeDetail workEffortTypeDetail = workEffortScopeDetail.getWorkEffortType().getLastDetail();
        Sequence workEffortSequence = workEffortScopeDetail.getWorkEffortSequence();

        preparedWorkEffort.setWorkEffortScope(workEffortScope);

        if(workEffortSequence == null) {
            workEffortSequence = workEffortTypeDetail.getWorkEffortSequence();

            if(workEffortSequence == null) {
                var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                
                workEffortSequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.WORK_EFFORT.name());
            }
        }

        if(workEffortSequence != null) {
            preparedWorkEffort.setWorkEffortSequence(workEffortSequence);

            // If scheduledTime wasn't supplied, try to get it from the Scope, and if that isn't set, attempt to get it
            // from the Type.
            if(scheduledTime == null) {
                scheduledTime = workEffortScopeDetail.getScheduledTime();

                if(scheduledTime == null) {
                    scheduledTime = workEffortTypeDetail.getScheduledTime();
                }
            }

            if(scheduledTime != null) {
                preparedWorkEffort.setScheduledTime(scheduledTime);

                // If estimatedTimeAllowed wasn't supplied, try to get it from the Scope, and if that isn't set, attempt to get it
                // from the Type.
                if(estimatedTimeAllowed == null) {
                    estimatedTimeAllowed = workEffortScopeDetail.getEstimatedTimeAllowed();

                    if(estimatedTimeAllowed == null) {
                        estimatedTimeAllowed = workEffortTypeDetail.getEstimatedTimeAllowed();
                    }
                }

                if(estimatedTimeAllowed != null) {
                    preparedWorkEffort.setEstimatedTimeAllowed(estimatedTimeAllowed);

                    // If maximumTimeAllowed wasn't supplied, try to get it from the Scope, and if that isn't set, attempt to get it
                    // from the Type.
                    if(maximumTimeAllowed == null) {
                        maximumTimeAllowed = workEffortScopeDetail.getMaximumTimeAllowed();

                        if(maximumTimeAllowed == null) {
                            maximumTimeAllowed = workEffortTypeDetail.getMaximumTimeAllowed();
                        }
                    }

                    if(maximumTimeAllowed != null) {
                        preparedWorkEffort.setMaximumTimeAllowed(maximumTimeAllowed);
                    } else {
                        ema.addExecutionError(ExecutionErrors.MissingRequiredMaximumTimeAllowed.name(), workEffortTypeDetail.getWorkEffortTypeName(),
                                workEffortScopeDetail.getWorkEffortScopeName());
                    }
                } else {
                    ema.addExecutionError(ExecutionErrors.MissingRequiredEstimatedTimeAllowed.name(), workEffortTypeDetail.getWorkEffortTypeName(),
                            workEffortScopeDetail.getWorkEffortScopeName());
                }
            } else {
                ema.addExecutionError(ExecutionErrors.MissingRequiredScheduledTime.name(), workEffortTypeDetail.getWorkEffortTypeName(),
                        workEffortScopeDetail.getWorkEffortScopeName());
            }
        } else {
            ema.addExecutionError(ExecutionErrors.MissingDefaultSequence.name(), SequenceTypes.WORK_EFFORT.name());
        }

        return preparedWorkEffort;
    }

    public WorkEffort createWorkEffort(final PreparedWorkEffort preparedWorkEffort, final EntityInstance owningEntityInstance, final BasePK createdBy) {
        var workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
        String workEffortName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(preparedWorkEffort.getWorkEffortSequence());
        
        WorkEffort workEffort = workEffortControl.createWorkEffort(workEffortName, owningEntityInstance, preparedWorkEffort.getWorkEffortScope(),
                preparedWorkEffort.getScheduledTime(), null, null, preparedWorkEffort.getEstimatedTimeAllowed(), preparedWorkEffort.getMaximumTimeAllowed(),
                createdBy);

        return workEffort;
    }

    public void deleteWorkEffort(final WorkEffort workEffort, final BasePK deletedBy) {
        var workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);

        workEffortControl.deleteWorkEffort(workEffort, deletedBy);
    }

}
