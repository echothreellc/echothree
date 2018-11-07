// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.training.common.TrainingOptions;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.PartyTrainingClassDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class PartyTrainingClassTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClass, PartyTrainingClassTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    boolean includePartyTrainingClassSessions;
    
    /** Creates a new instance of PartyTrainingClassTransferCache */
    public PartyTrainingClassTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includePartyTrainingClassSessions = options.contains(TrainingOptions.PartyTrainingClassIncludePartyTrainingClassSessions);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyTrainingClassTransfer getPartyTrainingClassTransfer(PartyTrainingClass partyTrainingClass) {
        PartyTrainingClassTransfer partyTrainingClassTransfer = get(partyTrainingClass);

        if(partyTrainingClassTransfer == null) {
            PartyTrainingClassDetail partyTrainingClassDetail = partyTrainingClass.getLastDetail();
            String partyTrainingClassName = partyTrainingClassDetail.getPartyTrainingClassName();
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, partyTrainingClassDetail.getParty());
            TrainingClassTransfer trainingClassTransfer = trainingControl.getTrainingClassTransfer(userVisit, partyTrainingClassDetail.getTrainingClass());
            Long unformattedCompletedTime = partyTrainingClassDetail.getCompletedTime();
            String completedTime = unformattedCompletedTime == null ? null : formatTypicalDateTime(unformattedCompletedTime);
            Long unformattedValidUntilTime = partyTrainingClassDetail.getValidUntilTime();
            String validUntilTime = unformattedValidUntilTime == null ? null : formatTypicalDateTime(unformattedValidUntilTime);

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(partyTrainingClass.getPrimaryKey());
            WorkflowEntityStatusTransfer partyTrainingClassStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PartyTrainingClassStatusConstants.Workflow_PARTY_TRAINING_CLASS_STATUS, entityInstance);

            partyTrainingClassTransfer = new PartyTrainingClassTransfer(partyTrainingClassName, partyTransfer, trainingClassTransfer,
                    unformattedCompletedTime, completedTime, unformattedValidUntilTime, validUntilTime, partyTrainingClassStatus);
            put(partyTrainingClass, partyTrainingClassTransfer);
            setupOwnedWorkEfforts(null, entityInstance, partyTrainingClassTransfer);

            if(includePartyTrainingClassSessions) {
                partyTrainingClassTransfer.setPartyTrainingClassSessions(new ListWrapper<>(trainingControl.getPartyTrainingClassSessionTransfersByPartyTrainingClass(userVisit, partyTrainingClass)));
            }
        }
        
        return partyTrainingClassTransfer;
    }
    
}
