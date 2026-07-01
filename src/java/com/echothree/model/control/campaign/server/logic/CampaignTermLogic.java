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

import com.echothree.control.user.campaign.common.spec.CampaignTermUniversalSpec;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignTermNameException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignTermStatusChoiceException;
import com.echothree.model.control.campaign.common.exception.UnknownCampaignTermValueException;
import com.echothree.model.control.campaign.common.workflow.CampaignTermStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
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
public class CampaignTermLogic
        extends BaseLogic {

    protected CampaignTermLogic() {
        super();
    }

    public static CampaignTermLogic getInstance() {
        return CDI.current().select(CampaignTermLogic.class).get();
    }

    @Inject
    CampaignControl campaignControl;

    public CampaignTerm getCampaignTermByName(final ExecutionErrorAccumulator eea, final String campaignTermName,
            final EntityPermission entityPermission) {
        var campaignTerm = campaignControl.getCampaignTermByName(campaignTermName, entityPermission);

        if(campaignTerm == null) {
            handleExecutionError(UnknownCampaignTermNameException.class, eea, ExecutionErrors.UnknownCampaignTermName.name(), campaignTermName);
        }

        return campaignTerm;
    }

    public CampaignTerm getCampaignTermByName(final ExecutionErrorAccumulator eea, final String campaignTermName) {
        return getCampaignTermByName(eea, campaignTermName, EntityPermission.READ_ONLY);
    }

    public CampaignTerm getCampaignTermByNameForUpdate(final ExecutionErrorAccumulator eea, final String campaignTermName) {
        return getCampaignTermByName(eea, campaignTermName, EntityPermission.READ_WRITE);
    }

    public CampaignTerm getCampaignTermByValue(final ExecutionErrorAccumulator eea, final String campaignTermValue,
            final EntityPermission entityPermission) {
        var campaignTerm = campaignControl.getCampaignTermByValue(campaignTermValue, entityPermission);

        if(campaignTerm == null) {
            handleExecutionError(UnknownCampaignTermValueException.class, eea, ExecutionErrors.UnknownCampaignTermValue.name(), campaignTermValue);
        }

        return campaignTerm;
    }

    public CampaignTerm getCampaignTermByValue(final ExecutionErrorAccumulator eea, final String campaignTermValue) {
        return getCampaignTermByValue(eea, campaignTermValue, EntityPermission.READ_ONLY);
    }

    public CampaignTerm getCampaignTermByValueForUpdate(final ExecutionErrorAccumulator eea, final String campaignTermValue) {
        return getCampaignTermByValue(eea, campaignTermValue, EntityPermission.READ_WRITE);
    }

    public CampaignTerm getCampaignTermByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CampaignTermUniversalSpec universalSpec, final EntityPermission entityPermission) {
        CampaignTerm campaignTerm = null;
        var campaignTermName = universalSpec.getCampaignTermName();
        var parameterCount = (campaignTermName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(campaignTermName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.CampaignTerm.name());

                    if(!eea.hasExecutionErrors()) {
                        campaignTerm = campaignControl.getCampaignTermByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    campaignTerm = getCampaignTermByName(eea, campaignTermName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return campaignTerm;
    }

    public CampaignTerm getCampaignTermByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CampaignTermUniversalSpec universalSpec) {
        return getCampaignTermByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public CampaignTerm getCampaignTermByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final CampaignTermUniversalSpec universalSpec) {
        return getCampaignTermByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
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
