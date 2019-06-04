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

package com.echothree.model.control.training.server.logic;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.training.common.exception.UnknownPartyTrainingClassNameException;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.workeffort.common.workeffort.TrainingConstants;
import com.echothree.model.control.workeffort.server.logic.WorkEffortLogic;
import com.echothree.model.control.workeffort.server.logic.WorkEffortLogic.PreparedWorkEffort;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workrequirement.server.logic.WorkRequirementLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.PartyTrainingClassDetail;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassDetail;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.value.PartyTrainingClassDetailValue;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;

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
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        PartyTrainingClass partyTrainingClass = trainingControl.getPartyTrainingClassByName(partyTrainingClassName);

        if(partyTrainingClass == null) {
            handleExecutionError(UnknownPartyTrainingClassNameException.class, eea, ExecutionErrors.UnknownPartyTrainingClassName.name(), partyTrainingClassName);
        }

        return partyTrainingClass;
    }
    
    private void insertPartyTrainingClassIntoWorkflow(final EntityInstance entityInstance, final Long completedTime, final Long validUntilTime,
            final BasePK partyPK) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        String workflowEntranceName = completedTime == null ? PartyTrainingClassStatusConstants.WorkflowEntrance_NEW_ASSIGNED : PartyTrainingClassStatusConstants.WorkflowEntrance_NEW_PASSED;
        
        workflowControl.addEntityToWorkflowUsingNames(null, PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS, workflowEntranceName,
                entityInstance, null, validUntilTime, partyPK);
    }

    public static class PreparedPartyTrainingClass {
        
        private Party party;
        private TrainingClass trainingClass;
        private Long completedTime;
        private Long validUntilTime;
        private PreparedWorkEffort preparedWorkEffort;

        /**
         * @return the party
         */
        public Party getParty() {
            return party;
        }

        /**
         * @param party the party to set
         */
        public void setParty(Party party) {
            this.party = party;
        }

        /**
         * @return the trainingClass
         */
        public TrainingClass getTrainingClass() {
            return trainingClass;
        }

        /**
         * @param trainingClass the trainingClass to set
         */
        public void setTrainingClass(TrainingClass trainingClass) {
            this.trainingClass = trainingClass;
        }

        /**
         * @return the completedTime
         */
        public Long getCompletedTime() {
            return completedTime;
        }

        /**
         * @param completedTime the completedTime to set
         */
        public void setCompletedTime(Long completedTime) {
            this.completedTime = completedTime;
        }

        /**
         * @return the validUntilTime
         */
        public Long getValidUntilTime() {
            return validUntilTime;
        }

        /**
         * @param validUntilTime the validUntilTime to set
         */
        public void setValidUntilTime(Long validUntilTime) {
            this.validUntilTime = validUntilTime;
        }

        /**
         * @return the preparedWorkEffort
         */
        public PreparedWorkEffort getPreparedWorkEffort() {
            return preparedWorkEffort;
        }

        /**
         * @param preparedWorkEffort the preparedWorkEffort to set
         */
        public void setPreparedWorkEffort(PreparedWorkEffort preparedWorkEffort) {
            this.preparedWorkEffort = preparedWorkEffort;
        }

    }

    public PreparedPartyTrainingClass preparePartyTrainingClass(final ExecutionErrorAccumulator eea, final Party party, final TrainingClass trainingClass,
            final Long completedTime, final Long validUntilTime) {
        PreparedPartyTrainingClass preparedPartyTrainingClass = new PreparedPartyTrainingClass();

        preparedPartyTrainingClass.setParty(party);
        preparedPartyTrainingClass.setTrainingClass(trainingClass);
        preparedPartyTrainingClass.setCompletedTime(completedTime);
        preparedPartyTrainingClass.setValidUntilTime(validUntilTime);

        if(completedTime == null) {
            WorkEffortScope workEffortScope = trainingClass.getLastDetail().getWorkEffortScope();

            if(workEffortScope != null) {
                TrainingClassDetail trainingClassDetail = trainingClass.getLastDetail();
                Long estimatedReadingTime = trainingClassDetail.getEstimatedReadingTime();
                Long readingTimeAllowed = trainingClassDetail.getReadingTimeAllowed();
                Long estimatedTestingTime = trainingClassDetail.getEstimatedTestingTime();
                Long testingTimeAllowed = trainingClassDetail.getTestingTimeAllowed();
                Long requiredCompletionTime = trainingClassDetail.getRequiredCompletionTime();

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
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        Party party = preparedPartyTrainingClass.getParty();
        TrainingClass trainingClass = preparedPartyTrainingClass.getTrainingClass();
        Long completedTime = preparedPartyTrainingClass.getCompletedTime();
        WorkEffort workEffort = null;

        PartyTrainingClass partyTrainingClass = trainingControl.createPartyTrainingClass(preparedPartyTrainingClass.getParty(), trainingClass,
                preparedPartyTrainingClass.completedTime, preparedPartyTrainingClass.getValidUntilTime(), createdBy);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(partyTrainingClass.getPrimaryKey());

        if(completedTime == null) {
            PreparedWorkEffort preparedWorkEffort = preparedPartyTrainingClass.getPreparedWorkEffort();

            if(preparedWorkEffort != null) {
                Long requiredCompletionTime = trainingClass.getLastDetail().getRequiredCompletionTime();
                Long requiredTime = requiredCompletionTime == null ? null : session.START_TIME + requiredCompletionTime;

                workEffort = WorkEffortLogic.getInstance().createWorkEffort(preparedWorkEffort, entityInstance, createdBy);

                WorkRequirementLogic.getInstance().createWorkRequirementUsingNames(session, workEffort, TrainingConstants.WorkRequirementType_TRAINING,
                    party, null, requiredTime, createdBy);
            }
        }

        insertPartyTrainingClassIntoWorkflow(entityInstance, preparedPartyTrainingClass.getCompletedTime(), preparedPartyTrainingClass.getValidUntilTime(),
                createdBy);

        if(completedTime == null) {
            List<TrainingClassSection> trainingClassSections = trainingControl.getTrainingClassSections(trainingClass);

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
        PartyTrainingClassDetail partyTrainingClassDetail = partyTrainingClass.getLastDetail();
        boolean invalidPartyTrainingClass = false;

        if(modifiedBy.equals(partyTrainingClassDetail.getPartyPK())) {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(partyTrainingClass.getPrimaryKey());
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS,
                    entityInstance);
            String workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();

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
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        
        // TODO: adjust Status if necesssary
        // TODO: delete PartyTrainingClassStatus if necessary
        
        trainingControl.updatePartyTrainingClassFromValue(partyTrainingClassDetailValue, updatedBy);
    }
    
    public void deletePartyTrainingClass(PartyTrainingClass partyTrainingClass, final BasePK deleteBy) {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        
        trainingControl.deletePartyTrainingClass(partyTrainingClass, deleteBy);
    }
    
}
