// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.campaign.server.logic;

import com.echothree.model.control.campaign.common.exception.UnknownCampaignTermNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignTermStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignTermValueException;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignTermStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.Map;
import java.util.Set;

public class CampaignTermLogic
        extends BaseLogic {

    private CampaignTermLogic() {
        super();
    }

    private static class CampaignTermLogicHolder {
        static CampaignTermLogic instance = new CampaignTermLogic();
    }

    public static CampaignTermLogic getInstance() {
        return CampaignTermLogicHolder.instance;
    }
    
    public CampaignTerm getCampaignTermByName(final ExecutionErrorAccumulator eea, final String campaignTermName) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        CampaignTerm campaignTerm = campaignControl.getCampaignTermByName(campaignTermName);

        if(campaignTerm == null) {
            handleExecutionError(UnknownCampaignTermNameException.class, eea, ExecutionErrors.UnknownCampaignTermName.name(), campaignTermName);
        }

        return campaignTerm;
    }
    
    public CampaignTerm getCampaignTermByValue(final ExecutionErrorAccumulator eea, final String campaignTermValue) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        CampaignTerm campaignTerm = campaignControl.getCampaignTermByValue(campaignTermValue);

        if(campaignTerm == null) {
            handleExecutionError(UnknownCampaignTermValueException.class, eea, ExecutionErrors.UnknownCampaignTermValue.name(), campaignTermValue);
        }

        return campaignTerm;
    }
    
    public void setCampaignTermStatus(final Session session, ExecutionErrorAccumulator eea, CampaignTerm campaignTerm, String campaignTermStatusChoice, PartyPK modifiedBy) {
        var coreControl = Session.getModelController(CoreControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        Workflow workflow = workflowLogic.getWorkflowByName(eea, CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaignTerm.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        WorkflowDestination workflowDestination = campaignTermStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignTermStatusChoice);

        if(workflowDestination != null || campaignTermStatusChoice == null) {
            WorkflowDestinationLogic workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            String currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            Map<String, Set<String>> map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(CampaignTermStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS, CampaignTermStatusConstants.WorkflowStep_INACTIVE)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(CampaignTermStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS, CampaignTermStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownCampaignTermStatusChoiceException.class, eea, ExecutionErrors.UnknownCampaignTermStatusChoice.name(), campaignTermStatusChoice);
        }
    }

}
