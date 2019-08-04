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

import com.echothree.model.control.campaign.common.exception.UnknownCampaignSourceNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignSourceStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignSourceValueException;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignSourceStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
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

public class CampaignSourceLogic
        extends BaseLogic {

    private CampaignSourceLogic() {
        super();
    }

    private static class CampaignSourceLogicHolder {
        static CampaignSourceLogic instance = new CampaignSourceLogic();
    }

    public static CampaignSourceLogic getInstance() {
        return CampaignSourceLogicHolder.instance;
    }
    
    public CampaignSource getCampaignSourceByName(final ExecutionErrorAccumulator eea, final String campaignSourceName) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignSource campaignSource = campaignControl.getCampaignSourceByName(campaignSourceName);

        if(campaignSource == null) {
            handleExecutionError(UnknownCampaignSourceNameException.class, eea, ExecutionErrors.UnknownCampaignSourceName.name(), campaignSourceName);
        }

        return campaignSource;
    }
    
    public CampaignSource getCampaignSourceByValue(final ExecutionErrorAccumulator eea, final String campaignSourceValue) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignSource campaignSource = campaignControl.getCampaignSourceByValue(campaignSourceValue);

        if(campaignSource == null) {
            handleExecutionError(UnknownCampaignSourceValueException.class, eea, ExecutionErrors.UnknownCampaignSourceValue.name(), campaignSourceValue);
        }

        return campaignSource;
    }
    
    public void setCampaignSourceStatus(final Session session, ExecutionErrorAccumulator eea, CampaignSource campaignSource, String campaignSourceStatusChoice, PartyPK modifiedBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        var workflowLogic = WorkflowLogic.getInstance();
        Workflow workflow = workflowLogic.getWorkflowByName(eea, CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaignSource.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        WorkflowDestination workflowDestination = campaignSourceStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), campaignSourceStatusChoice);

        if(workflowDestination != null || campaignSourceStatusChoice == null) {
            WorkflowDestinationLogic workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            String currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            Map<String, Set<String>> map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
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
