// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.model.control.campaign.common.workflow.CampaignTermStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CampaignTermLogic
        extends BaseLogic {

    protected CampaignTermLogic() {
        super();
    }

    public static CampaignTermLogic getInstance() {
        return CDI.current().select(CampaignTermLogic.class).get();
    }
    
    public CampaignTerm getCampaignTermByName(final ExecutionErrorAccumulator eea, final String campaignTermName) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignTerm = campaignControl.getCampaignTermByName(campaignTermName);

        if(campaignTerm == null) {
            handleExecutionError(UnknownCampaignTermNameException.class, eea, ExecutionErrors.UnknownCampaignTermName.name(), campaignTermName);
        }

        return campaignTerm;
    }
    
    public CampaignTerm getCampaignTermByValue(final ExecutionErrorAccumulator eea, final String campaignTermValue) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignTerm = campaignControl.getCampaignTermByValue(campaignTermValue);

        if(campaignTerm == null) {
            handleExecutionError(UnknownCampaignTermValueException.class, eea, ExecutionErrors.UnknownCampaignTermValue.name(), campaignTermValue);
        }

        return campaignTerm;
    }
    
    public void setCampaignTermStatus(final Session session, ExecutionErrorAccumulator eea, CampaignTerm campaignTerm, String campaignTermStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        var workflow = workflowLogic.getWorkflowByName(eea, CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignTerm.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = campaignTermStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignTermStatusChoice);

        if(workflowDestination != null || campaignTermStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
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
