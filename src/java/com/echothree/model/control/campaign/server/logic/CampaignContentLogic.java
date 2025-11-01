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

import com.echothree.model.control.campaign.common.exception.UnknownCampaignContentNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignContentStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignContentValueException;
import com.echothree.model.control.campaign.common.workflow.CampaignContentStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CampaignContentLogic
        extends BaseLogic {

    protected CampaignContentLogic() {
        super();
    }

    public static CampaignContentLogic getInstance() {
        return CDI.current().select(CampaignContentLogic.class).get();
    }
    
    public CampaignContent getCampaignContentByName(final ExecutionErrorAccumulator eea, final String campaignContentName) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignContent = campaignControl.getCampaignContentByName(campaignContentName);

        if(campaignContent == null) {
            handleExecutionError(UnknownCampaignContentNameException.class, eea, ExecutionErrors.UnknownCampaignContentName.name(), campaignContentName);
        }

        return campaignContent;
    }
    
    public CampaignContent getCampaignContentByValue(final ExecutionErrorAccumulator eea, final String campaignContentValue) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignContent = campaignControl.getCampaignContentByValue(campaignContentValue);

        if(campaignContent == null) {
            handleExecutionError(UnknownCampaignContentValueException.class, eea, ExecutionErrors.UnknownCampaignContentValue.name(), campaignContentValue);
        }

        return campaignContent;
    }
    
    public void setCampaignContentStatus(final Session session, ExecutionErrorAccumulator eea, CampaignContent campaignContent, String campaignContentStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        var workflow = workflowLogic.getWorkflowByName(eea, CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignContent.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = campaignContentStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignContentStatusChoice);

        if(workflowDestination != null || campaignContentStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(CampaignContentStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS, CampaignContentStatusConstants.WorkflowStep_INACTIVE)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(CampaignContentStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS, CampaignContentStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownCampaignContentStatusChoiceException.class, eea, ExecutionErrors.UnknownCampaignContentStatusChoice.name(), campaignContentStatusChoice);
        }
    }
    
}
