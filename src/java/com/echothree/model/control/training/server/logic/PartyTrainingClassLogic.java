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

package com.echothree.model.control.training.server.logic;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.training.common.exception.UnknownPartyTrainingClassNameException;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.workeffort.common.workeffort.TrainingConstants;
import com.echothree.model.control.workeffort.server.logic.WorkEffortLogic;
import com.echothree.model.control.workeffort.server.logic.WorkEffortLogic.PreparedWorkEffort;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workrequirement.server.logic.WorkRequirementLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.value.PartyTrainingClassDetailValue;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class PartyTrainingClassLogic
        extends BaseLogic {
    
    private PartyTrainingClassLogic() {
        super();
    }
    
    private static class PartyTrainingClassLogicHolder {
        static PartyTrainingClassLogic instance = new PartyTrainingClassLogic();
    }
    
    public static PartyTrainingClassLogic getInstance() {
        return PartyTrainingClassLogicHolder.instance;
    }
    
    public PartyTrainingClass getPartyTrainingClassByName(final ExecutionErrorAccumulator eea, final String partyTrainingClassName) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyTrainingClass = trainingControl.getPartyTrainingClassByName(partyTrainingClassName);

        if(partyTrainingClass == null) {
            handleExecutionError(UnknownPartyTrainingClassNameException.class, eea, ExecutionErrors.UnknownPartyTrainingClassName.name(), partyTrainingClassName);
        }

        return partyTrainingClass;
    }
    
    private void insertPartyTrainingClassIntoWorkflow(final EntityInstance entityInstance, final Long completedTime, final Long validUntilTime,
            final BasePK partyPK) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowEntranceName = completedTime == null ? PartyTrainingClassStatusConstants.WorkflowEntrance_NEW_ASSIGNED : PartyTrainingClassStatusConstants.WorkflowEntrance_NEW_PASSED;
        
        workflowControl.addEntityToWorkflowUsingNames(null, PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS, workflowEntranceName,
                entityInstance, null, validUntilTime, partyPK);
    }

    public static class PreparedPartyTrainingClass {
        
        private Party party;
        private TrainingClass trainingClass;
        private Long completedTime;
        private Long validUntilTime;
        private PreparedWorkEffort preparedWorkEffort;

        public Party getParty() {
            return party;
        }

        public void setParty(Party party) {
            this.party = party;
        }

        public TrainingClass getTrainingClass() {
            return trainingClass;
        }

        public void setTrainingClass(TrainingClass trainingClass) {
            this.trainingClass = trainingClass;
        }

        public Long getCompletedTime() {
            return completedTime;
        }

        public void setCompletedTime(Long completedTime) {
            this.completedTime = completedTime;
        }

        public Long getValidUntilTime() {
            return validUntilTime;
        }

        public void setValidUntilTime(Long validUntilTime) {
            this.validUntilTime = validUntilTime;
        }

        public PreparedWorkEffort getPreparedWorkEffort() {
            return preparedWorkEffort;
        }

        public void setPreparedWorkEffort(PreparedWorkEffort preparedWorkEffort) {
            this.preparedWorkEffort = preparedWorkEffort;
        }

    }

    public PreparedPartyTrainingClass preparePartyTrainingClass(final ExecutionErrorAccumulator eea, final Party party, final TrainingClass trainingClass,
            final Long completedTime, final Long validUntilTime) {
        var preparedPartyTrainingClass = new PreparedPartyTrainingClass();

        preparedPartyTrainingClass.setParty(party);
        preparedPartyTrainingClass.setTrainingClass(trainingClass);
        preparedPartyTrainingClass.setCompletedTime(completedTime);
        preparedPartyTrainingClass.setValidUntilTime(validUntilTime);

        if(completedTime == null) {
            var workEffortScope = trainingClass.getLastDetail().getWorkEffortScope();

            if(workEffortScope != null) {
                var trainingClassDetail = trainingClass.getLastDetail();
                var estimatedReadingTime = trainingClassDetail.getEstimatedReadingTime();
                var readingTimeAllowed = trainingClassDetail.getReadingTimeAllowed();
                var estimatedTestingTime = trainingClassDetail.getEstimatedTestingTime();
                var testingTimeAllowed = trainingClassDetail.getTestingTimeAllowed();
                var requiredCompletionTime = trainingClassDetail.getRequiredCompletionTime();

                if(estimatedReadingTime == null) {
                    eea.addExecutionError(ExecutionErrors.MissingEstimatedReadingTime.name(), trainingClassDetail.getTrainingClassName());
                }
                if(readingTimeAllowed == null) {
                    eea.addExecutionError(ExecutionErrors.MissingReadingTimeAllowed.name(), trainingClassDetail.getTrainingClassName());
                }
                if(estimatedTestingTime == null) {
                    eea.addExecutionError(ExecutionErrors.MissingEstimatedTestingTime.name(), trainingClassDetail.getTrainingClassName());
                }
                if(testingTimeAllowed == null) {
                    eea.addExecutionError(ExecutionErrors.MissingTestingTimeAllowed.name(), trainingClassDetail.getTrainingClassName());
                }
                if(requiredCompletionTime == null) {
                    eea.addExecutionError(ExecutionErrors.MissingRequiredCompletionTime.name(), trainingClassDetail.getTrainingClassName());
                }

                if(eea == null || !eea.hasExecutionErrors()) {
                    Long estimatedTimeAllowed = estimatedReadingTime + estimatedTestingTime;
                    Long maximumTimeAllowed = readingTimeAllowed + testingTimeAllowed;

                    preparedPartyTrainingClass.setPreparedWorkEffort(WorkEffortLogic.getInstance().prepareForWorkEffort(eea, workEffortScope,
                            requiredCompletionTime, estimatedTimeAllowed, maximumTimeAllowed));
                }
            }
        }

        return preparedPartyTrainingClass;
    }

    public PartyTrainingClass createPartyTrainingClass(final Session session, final PreparedPartyTrainingClass preparedPartyTrainingClass,
            final BasePK createdBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var trainingControl = Session.getModelController(TrainingControl.class);
        var party = preparedPartyTrainingClass.getParty();
        var trainingClass = preparedPartyTrainingClass.getTrainingClass();
        var completedTime = preparedPartyTrainingClass.getCompletedTime();
        WorkEffort workEffort;

        var partyTrainingClass = trainingControl.createPartyTrainingClass(preparedPartyTrainingClass.getParty(), trainingClass,
                preparedPartyTrainingClass.completedTime, preparedPartyTrainingClass.getValidUntilTime(), createdBy);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyTrainingClass.getPrimaryKey());

        if(completedTime == null) {
            var preparedWorkEffort = preparedPartyTrainingClass.getPreparedWorkEffort();

            if(preparedWorkEffort != null) {
                var requiredCompletionTime = trainingClass.getLastDetail().getRequiredCompletionTime();
                var requiredTime = requiredCompletionTime == null ? null : session.START_TIME + requiredCompletionTime;

                workEffort = WorkEffortLogic.getInstance().createWorkEffort(preparedWorkEffort, entityInstance, createdBy);

                WorkRequirementLogic.getInstance().createWorkRequirementUsingNames(session, workEffort, TrainingConstants.WorkRequirementType_TRAINING,
                    party, null, requiredTime, createdBy);
            }
        }

        insertPartyTrainingClassIntoWorkflow(entityInstance, preparedPartyTrainingClass.getCompletedTime(), preparedPartyTrainingClass.getValidUntilTime(),
                createdBy);

        if(completedTime == null) {
            var trainingClassSections = trainingControl.getTrainingClassSections(trainingClass);

            // If there are Pages to read, or Questions to answer, then setup a PartyTrainingClassSession for them.
            if(trainingControl.countTrainingClassPages(trainingClassSections) != 0 || trainingControl.countTrainingClassQuestions(trainingClassSections) != 0) {
                PartyTrainingClassSessionLogic.getInstance().createPartyTrainingClassSession(partyTrainingClass, createdBy);
            }
        }

        return partyTrainingClass;
    }

    /**
     * Check to make sure that the Party requesting the Party Training Class is the one that its assigned to, and verify that its in a valid
     * status for requesting the information.
     */
    public void checkPartyTrainingClassStatus(final ExecutionErrorAccumulator eea, final PartyTrainingClass partyTrainingClass, final PartyPK modifiedBy) {
        var partyTrainingClassDetail = partyTrainingClass.getLastDetail();
        var invalidPartyTrainingClass = false;

        if(modifiedBy.equals(partyTrainingClassDetail.getPartyPK())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyTrainingClass.getPrimaryKey());
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS,
                    entityInstance);
            var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();

            // Check to see if its ASSIGNED. If it is, move it to TRAINING.
            if(workflowStepName.equals(PartyTrainingClassStatusConstants.WorkflowStep_ASSIGNED)) {
                workflowControl.transitionEntityInWorkflowUsingNames(null, workflowEntityStatus, PartyTrainingClassStatusConstants.WorkflowDestination_ASSIGNED_TO_TRAINING,
                        workflowControl.getWorkflowTriggerTime(workflowEntityStatus), modifiedBy);
            } else if(!workflowStepName.equals(PartyTrainingClassStatusConstants.WorkflowStep_TRAINING)) {
                invalidPartyTrainingClass = true;
            }
        } else {
            invalidPartyTrainingClass = true;
        }

        if(invalidPartyTrainingClass) {
            eea.addExecutionError(ExecutionErrors.InvalidPartyTrainingClass.name(), partyTrainingClassDetail.getPartyTrainingClassName());
        }
    }

    public void updatePartyTrainingClassFromValue(final PartyTrainingClassDetailValue partyTrainingClassDetailValue, final BasePK updatedBy) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        
        // TODO: adjust Status if necessary
        // TODO: delete PartyTrainingClassStatus if necessary
        
        trainingControl.updatePartyTrainingClassFromValue(partyTrainingClassDetailValue, updatedBy);
    }
    
    public void deletePartyTrainingClass(PartyTrainingClass partyTrainingClass, final BasePK deleteBy) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        
        trainingControl.deletePartyTrainingClass(partyTrainingClass, deleteBy);
    }
    
}
