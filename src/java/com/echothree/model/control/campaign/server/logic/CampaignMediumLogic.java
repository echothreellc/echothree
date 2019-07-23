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

package com.echothree.model.control.campaign.server.logic;

import com.echothree.model.control.campaign.common.exception.UnknownCampaignMediumNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignMediumStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignMediumValueException;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignMediumStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
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

public class CampaignMediumLogic
        extends BaseLogic {

    private CampaignMediumLogic() {
        super();
    }

    private static class CampaignMediumLogicHolder {
        static CampaignMediumLogic instance = new CampaignMediumLogic();
    }

    public static CampaignMediumLogic getInstance() {
        return CampaignMediumLogicHolder.instance;
    }
    
    public CampaignMedium getCampaignMediumByName(final ExecutionErrorAccumulator eea, final String campaignMediumName) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignMedium campaignMedium = campaignControl.getCampaignMediumByName(campaignMediumName);

        if(campaignMedium == null) {
            handleExecutionError(UnknownCampaignMediumNameException.class, eea, ExecutionErrors.UnknownCampaignMediumName.name(), campaignMediumName);
        }

        return campaignMedium;
    }
    
    public CampaignMedium getCampaignMediumByValue(final ExecutionErrorAccumulator eea, final String campaignMediumValue) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignMedium campaignMedium = campaignControl.getCampaignMediumByValue(campaignMediumValue);

        if(campaignMedium == null) {
            handleExecutionError(UnknownCampaignMediumValueException.class, eea, ExecutionErrors.UnknownCampaignMediumValue.name(), campaignMediumValue);
        }

        return campaignMedium;
    }
    
    public void setCampaignMediumStatus(final Session session, ExecutionErrorAccumulator eea, CampaignMedium campaignMedium, String campaignMediumStatusChoice, PartyPK modifiedBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        Workflow workflow = workflowLogic.getWorkflowByName(eea, CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaignMedium.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        WorkflowDestination workflowDestination = campaignMediumStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignMediumStatusChoice);

        if(workflowDestination != null || campaignMediumStatusChoice == null) {
            WorkflowDestinationLogic workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            String currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            Map<String, Set<String>> map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(CampaignMediumStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS, CampaignMediumStatusConstants.WorkflowStep_INACTIVE)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(CampaignMediumStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS, CampaignMediumStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownCampaignMediumStatusChoiceException.class, eea, ExecutionErrors.UnknownCampaignMediumStatusChoice.name(), campaignMediumStatusChoice);
        }
    }
    
}
