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

import com.echothree.control.user.campaign.common.spec.CampaignContentUniversalSpec;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignContentNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignContentStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignContentValueException;
import com.echothree.model.control.campaign.common.workflow.CampaignContentStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class CampaignContentLogic
        extends BaseLogic {

    protected CampaignContentLogic() {
        super();
    }

    public static CampaignContentLogic getInstance() {
        return CDI.current().select(CampaignContentLogic.class).get();
    }

    @Inject
    CampaignControl campaignControl;

    public CampaignContent getCampaignContentByName(final ExecutionErrorAccumulator eea, final String campaignContentName,
            final EntityPermission entityPermission) {
        var campaignContent = campaignControl.getCampaignContentByName(campaignContentName, entityPermission);

        if(campaignContent == null) {
            handleExecutionError(UnknownCampaignContentNameException.class, eea, ExecutionErrors.UnknownCampaignContentName.name(), campaignContentName);
        }

        return campaignContent;
    }

    public CampaignContent getCampaignContentByName(final ExecutionErrorAccumulator eea, final String campaignContentName) {
        return getCampaignContentByName(eea, campaignContentName, EntityPermission.READ_ONLY);
    }

    public CampaignContent getCampaignContentByNameForUpdate(final ExecutionErrorAccumulator eea, final String campaignContentName) {
        return getCampaignContentByName(eea, campaignContentName, EntityPermission.READ_WRITE);
    }

    public CampaignContent getCampaignContentByValue(final ExecutionErrorAccumulator eea, final String campaignContentValue,
            final EntityPermission entityPermission) {
        var campaignContent = campaignControl.getCampaignContentByValue(campaignContentValue, entityPermission);

        if(campaignContent == null) {
            handleExecutionError(UnknownCampaignContentValueException.class, eea, ExecutionErrors.UnknownCampaignContentValue.name(), campaignContentValue);
        }

        return campaignContent;
    }

    public CampaignContent getCampaignContentByValue(final ExecutionErrorAccumulator eea, final String campaignContentValue) {
        return getCampaignContentByValue(eea, campaignContentValue, EntityPermission.READ_ONLY);
    }

    public CampaignContent getCampaignContentByValueForUpdate(final ExecutionErrorAccumulator eea, final String campaignContentValue) {
        return getCampaignContentByValue(eea, campaignContentValue, EntityPermission.READ_WRITE);
    }

    public CampaignContent getCampaignContentByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CampaignContentUniversalSpec universalSpec, final EntityPermission entityPermission) {
        CampaignContent campaignContent = null;
        var campaignContentName = universalSpec.getCampaignContentName();
        var parameterCount = (campaignContentName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(campaignContentName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.CampaignContent.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        campaignContent = campaignControl.getCampaignContentByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    campaignContent = getCampaignContentByName(eea, campaignContentName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return campaignContent;
    }

    public CampaignContent getCampaignContentByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CampaignContentUniversalSpec universalSpec) {
        return getCampaignContentByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public CampaignContent getCampaignContentByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final CampaignContentUniversalSpec universalSpec) {
        return getCampaignContentByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
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
