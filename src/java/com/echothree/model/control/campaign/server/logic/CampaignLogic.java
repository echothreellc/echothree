// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.campaign.common.exception.UnknownCampaignNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignValueException;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.Campaign;
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

public class CampaignLogic
        extends BaseLogic {

    private CampaignLogic() {
        super();
    }

    private static class CampaignLogicHolder {
        static CampaignLogic instance = new CampaignLogic();
    }

    public static CampaignLogic getInstance() {
        return CampaignLogicHolder.instance;
    }
    
    public Campaign getCampaignByName(final ExecutionErrorAccumulator eea, final String campaignName) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        Campaign campaign = campaignControl.getCampaignByName(campaignName);

        if(campaign == null) {
            handleExecutionError(UnknownCampaignNameException.class, eea, ExecutionErrors.UnknownCampaignName.name(), campaignName);
        }

        return campaign;
    }
    
    public Campaign getCampaignByValue(final ExecutionErrorAccumulator eea, final String campaignValue) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        Campaign campaign = campaignControl.getCampaignByValue(campaignValue);

        if(campaign == null) {
            handleExecutionError(UnknownCampaignValueException.class, eea, ExecutionErrors.UnknownCampaignValue.name(), campaignValue);
        }

        return campaign;
    }
    
    public void setCampaignStatus(final Session session, ExecutionErrorAccumulator eea, Campaign campaign, String campaignStatusChoice, PartyPK modifiedBy) {
        var coreControl = Session.getModelController(CoreControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        Workflow workflow = workflowLogic.getWorkflowByName(eea, CampaignStatusConstants.Workflow_CAMPAIGN_STATUS);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaign.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        WorkflowDestination workflowDestination = campaignStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignStatusChoice);

        if(workflowDestination != null || campaignStatusChoice == null) {
            WorkflowDestinationLogic workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            String currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            Map<String, Set<String>> map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(CampaignStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignStatusConstants.Workflow_CAMPAIGN_STATUS, CampaignStatusConstants.WorkflowStep_INACTIVE)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(CampaignStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignStatusConstants.Workflow_CAMPAIGN_STATUS, CampaignStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownCampaignStatusChoiceException.class, eea, ExecutionErrors.UnknownCampaignStatusChoice.name(), campaignStatusChoice);
        }
    }
    
}
