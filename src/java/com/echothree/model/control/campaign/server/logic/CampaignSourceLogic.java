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

import com.echothree.model.control.campaign.common.exception.UnknownCampaignSourceNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignSourceStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignSourceValueException;
import com.echothree.model.control.campaign.common.workflow.CampaignSourceStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CampaignSourceLogic
        extends BaseLogic {

    protected CampaignSourceLogic() {
        super();
    }

    public static CampaignSourceLogic getInstance() {
        return CDI.current().select(CampaignSourceLogic.class).get();
    }
    
    public CampaignSource getCampaignSourceByName(final ExecutionErrorAccumulator eea, final String campaignSourceName) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignSource = campaignControl.getCampaignSourceByName(campaignSourceName);

        if(campaignSource == null) {
            handleExecutionError(UnknownCampaignSourceNameException.class, eea, ExecutionErrors.UnknownCampaignSourceName.name(), campaignSourceName);
        }

        return campaignSource;
    }
    
    public CampaignSource getCampaignSourceByValue(final ExecutionErrorAccumulator eea, final String campaignSourceValue) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignSource = campaignControl.getCampaignSourceByValue(campaignSourceValue);

        if(campaignSource == null) {
            handleExecutionError(UnknownCampaignSourceValueException.class, eea, ExecutionErrors.UnknownCampaignSourceValue.name(), campaignSourceValue);
        }

        return campaignSource;
    }
    
    public void setCampaignSourceStatus(final Session session, ExecutionErrorAccumulator eea, CampaignSource campaignSource, String campaignSourceStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        var workflow = workflowLogic.getWorkflowByName(eea, CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignSource.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = campaignSourceStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignSourceStatusChoice);

        if(workflowDestination != null || campaignSourceStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(CampaignSourceStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS, CampaignSourceStatusConstants.WorkflowStep_INACTIVE)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(CampaignSourceStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS, CampaignSourceStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownCampaignSourceStatusChoiceException.class, eea, ExecutionErrors.UnknownCampaignSourceStatusChoice.name(), campaignSourceStatusChoice);
        }
    }
    
}
