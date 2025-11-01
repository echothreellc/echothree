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

package com.echothree.model.control.campaign.server.logic;

import com.echothree.model.control.campaign.common.exception.UnknownCampaignMediumNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignMediumStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignMediumValueException;
import com.echothree.model.control.campaign.common.workflow.CampaignMediumStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CampaignMediumLogic
        extends BaseLogic {

    protected CampaignMediumLogic() {
        super();
    }

    public static CampaignMediumLogic getInstance() {
        return CDI.current().select(CampaignMediumLogic.class).get();
    }
    
    public CampaignMedium getCampaignMediumByName(final ExecutionErrorAccumulator eea, final String campaignMediumName) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignMedium = campaignControl.getCampaignMediumByName(campaignMediumName);

        if(campaignMedium == null) {
            handleExecutionError(UnknownCampaignMediumNameException.class, eea, ExecutionErrors.UnknownCampaignMediumName.name(), campaignMediumName);
        }

        return campaignMedium;
    }
    
    public CampaignMedium getCampaignMediumByValue(final ExecutionErrorAccumulator eea, final String campaignMediumValue) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignMedium = campaignControl.getCampaignMediumByValue(campaignMediumValue);

        if(campaignMedium == null) {
            handleExecutionError(UnknownCampaignMediumValueException.class, eea, ExecutionErrors.UnknownCampaignMediumValue.name(), campaignMediumValue);
        }

        return campaignMedium;
    }
    
    public void setCampaignMediumStatus(final Session session, ExecutionErrorAccumulator eea, CampaignMedium campaignMedium, String campaignMediumStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        var workflow = workflowLogic.getWorkflowByName(eea, CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignMedium.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = campaignMediumStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignMediumStatusChoice);

        if(workflowDestination != null || campaignMediumStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
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
