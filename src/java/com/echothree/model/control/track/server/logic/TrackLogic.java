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

package com.echothree.model.control.track.server.logic;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.track.common.exception.UnknownTrackNameException;
import com.echothree.model.control.track.common.exception.UnknownTrackStatusChoiceException;
import com.echothree.model.control.track.common.exception.UnknownTrackValueException;
import com.echothree.model.control.track.common.workflow.TrackStatusConstants;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TrackLogic
        extends BaseLogic {

    protected TrackLogic() {
        super();
    }

    public static TrackLogic getInstance() {
        return CDI.current().select(TrackLogic.class).get();
    }
    
    public Track getTrackByName(final ExecutionErrorAccumulator eea, final String trackName) {
        var trackControl = Session.getModelController(TrackControl.class);
        var track = trackControl.getTrackByName(trackName);

        if(track == null) {
            handleExecutionError(UnknownTrackNameException.class, eea, ExecutionErrors.UnknownTrackName.name(), trackName);
        }

        return track;
    }
    
    public Track getTrackByValue(final ExecutionErrorAccumulator eea, final String trackValue) {
        var trackControl = Session.getModelController(TrackControl.class);
        var track = trackControl.getTrackByValue(trackValue);

        if(track == null) {
            handleExecutionError(UnknownTrackValueException.class, eea, ExecutionErrors.UnknownTrackValue.name(), trackValue);
        }

        return track;
    }
    
    public void setTrackStatus(final Session session, ExecutionErrorAccumulator eea, Track track, String trackStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, TrackStatusConstants.Workflow_TRACK_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(track.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = trackStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), trackStatusChoice);

        if(workflowDestination != null || trackStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(TrackStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, TrackStatusConstants.Workflow_TRACK_STATUS, TrackStatusConstants.WorkflowStep_INACTIVE)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(TrackStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, TrackStatusConstants.Workflow_TRACK_STATUS, TrackStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownTrackStatusChoiceException.class, eea, ExecutionErrors.UnknownTrackStatusChoice.name(), trackStatusChoice);
        }
    }
    
}
