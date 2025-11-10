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

package com.echothree.model.control.training.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.training.common.TrainingOptions;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class PartyTrainingClassTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClass, PartyTrainingClassTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    boolean includePartyTrainingClassSessions;
    
    /** Creates a new instance of PartyTrainingClassTransferCache */
    public PartyTrainingClassTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        var options = session.getOptions();
        if(options != null) {
            includePartyTrainingClassSessions = options.contains(TrainingOptions.PartyTrainingClassIncludePartyTrainingClassSessions);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyTrainingClassTransfer getPartyTrainingClassTransfer(PartyTrainingClass partyTrainingClass) {
        var partyTrainingClassTransfer = get(partyTrainingClass);

        if(partyTrainingClassTransfer == null) {
            var partyTrainingClassDetail = partyTrainingClass.getLastDetail();
            var partyTrainingClassName = partyTrainingClassDetail.getPartyTrainingClassName();
            var partyTransfer = partyControl.getPartyTransfer(userVisit, partyTrainingClassDetail.getParty());
            var trainingClassTransfer = trainingControl.getTrainingClassTransfer(userVisit, partyTrainingClassDetail.getTrainingClass());
            var unformattedCompletedTime = partyTrainingClassDetail.getCompletedTime();
            var completedTime = unformattedCompletedTime == null ? null : formatTypicalDateTime(userVisit, unformattedCompletedTime);
            var unformattedValidUntilTime = partyTrainingClassDetail.getValidUntilTime();
            var validUntilTime = unformattedValidUntilTime == null ? null : formatTypicalDateTime(userVisit, unformattedValidUntilTime);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyTrainingClass.getPrimaryKey());
            var partyTrainingClassStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS, entityInstance);

            partyTrainingClassTransfer = new PartyTrainingClassTransfer(partyTrainingClassName, partyTransfer, trainingClassTransfer,
                    unformattedCompletedTime, completedTime, unformattedValidUntilTime, validUntilTime, partyTrainingClassStatus);
            put(userVisit, partyTrainingClass, partyTrainingClassTransfer);
            setupOwnedWorkEfforts(userVisit, null, entityInstance, partyTrainingClassTransfer);

            if(includePartyTrainingClassSessions) {
                partyTrainingClassTransfer.setPartyTrainingClassSessions(new ListWrapper<>(trainingControl.getPartyTrainingClassSessionTransfersByPartyTrainingClass(userVisit, partyTrainingClass)));
            }
        }
        
        return partyTrainingClassTransfer;
    }
    
}
