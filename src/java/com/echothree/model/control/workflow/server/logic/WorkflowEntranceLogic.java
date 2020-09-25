// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.control.workflow.common.exception.UnknownEntranceWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.UnknownEntranceWorkflowStepNameException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowDestinationStepException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntrancePartyTypeException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntranceSecurityRoleException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntranceSelectorException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntranceStepException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.WorkflowMissingSecurityRoleGroupException;
import com.echothree.model.control.workflow.common.exception.WorkflowMissingSelectorTypeException;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSelector;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class WorkflowEntranceLogic
        extends BaseLogic {

    private WorkflowEntranceLogic() {
        super();
    }
    
    private static class WorkflowEntranceLogicHolder {
        static WorkflowEntranceLogic instance = new WorkflowEntranceLogic();
    }
    
    public static WorkflowEntranceLogic getInstance() {
        return WorkflowEntranceLogicHolder.instance;
    }
    
    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final Workflow workflow,
            final String workflowEntranceName) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowEntrance workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

        if(workflowEntrance == null) {
            handleExecutionError(UnknownWorkflowNameException.class, eea, ExecutionErrors.UnknownWorkflowEntranceName.name(),
                    workflow.getLastDetail().getWorkflowName(), workflowEntranceName);
        }

        return workflowEntrance;
    }

    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName) {
        Workflow workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
        WorkflowEntrance workflowEntrance = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowEntrance = getWorkflowEntranceByName(eea, workflow, workflowEntranceName);
        }
        
        return workflowEntrance;
    }

    public WorkflowEntrancePartyType getWorkflowEntrancePartyType(final ExecutionErrorAccumulator eea, final WorkflowEntrance workflowEntrance,
            final PartyType partyType, final EntityPermission entityPermission) {
        WorkflowEntrancePartyType workflowEntrancePartyType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

            workflowEntrancePartyType = workflowControl.getWorkflowEntrancePartyType(workflowEntrance, partyType, entityPermission);

            if(workflowEntrancePartyType == null) {
               var workflowEntranceDetail = workflowEntrance.getLastDetail();

                handleExecutionError(UnknownWorkflowEntrancePartyTypeException.class, eea, ExecutionErrors.UnknownWorkflowEntrancePartyType.name(),
                        workflowEntranceDetail.getWorkflow().getLastDetail().getWorkflowName(),
                        workflowEntranceDetail.getWorkflowEntranceName(), partyType.getPartyTypeName());
            }
        }

        return workflowEntrancePartyType;
    }

    public WorkflowEntrancePartyType getWorkflowEntrancePartyType(final ExecutionErrorAccumulator eea, final WorkflowEntrance workflowEntrance,
            final PartyType partyType) {
        return getWorkflowEntrancePartyType(eea, workflowEntrance, partyType, EntityPermission.READ_ONLY);
    }

    public WorkflowEntrancePartyType getWorkflowEntrancePartyTypeForUpdate(final ExecutionErrorAccumulator eea, final WorkflowEntrance workflowEntrance,
            final PartyType partyType) {
        return getWorkflowEntrancePartyType(eea, workflowEntrance, partyType, EntityPermission.READ_WRITE);
    }

    public WorkflowEntrancePartyType getWorkflowEntrancePartyTypeByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String partyTypeName, final EntityPermission entityPermission) {
        var workflowEntrance = getWorkflowEntranceByName(eea, workflowName, workflowEntranceName);
        var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);

        return getWorkflowEntrancePartyType(eea, workflowEntrance, partyType, entityPermission);
    }

    public WorkflowEntrancePartyType getWorkflowEntrancePartyTypeByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String partyTypeName) {
        return getWorkflowEntrancePartyTypeByName(eea, workflowName, workflowEntranceName, partyTypeName,
                EntityPermission.READ_ONLY);
    }


    public WorkflowEntrancePartyType getWorkflowEntrancePartyTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String partyTypeName) {
        return getWorkflowEntrancePartyTypeByName(eea, workflowName, workflowEntranceName, partyTypeName,
                EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRoleByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String partyTypeName, final String securityRoleName,
            final EntityPermission entityPermission) {
        var workflowEntrance = getWorkflowEntranceByName(eea, workflowName, workflowEntranceName);
        var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
        WorkflowEntranceSecurityRole workflowEntranceSecurityRole = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var securityRoleGroup = workflowEntrance.getLastDetail().getWorkflow().getLastDetail().getSecurityRoleGroup();

            if(securityRoleGroup != null) {
                var securityRole = SecurityRoleLogic.getInstance().getSecurityRoleByName(eea, securityRoleGroup, securityRoleName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var workflowEntrancePartyType = getWorkflowEntrancePartyType(eea, workflowEntrance, partyType);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

                        workflowEntranceSecurityRole = workflowControl.getWorkflowEntranceSecurityRole(workflowEntrancePartyType,
                                securityRole, entityPermission);

                        if(workflowEntranceSecurityRole == null) {
                            var workflowEntranceDetail = workflowEntrance.getLastDetail();
                            var securityRoleDetail = securityRole.getLastDetail();

                            handleExecutionError(UnknownWorkflowEntranceSecurityRoleException.class, eea, ExecutionErrors.UnknownWorkflowEntranceSecurityRole.name(),
                                    workflowEntranceDetail.getWorkflow().getLastDetail().getWorkflowName(),
                                    workflowEntranceDetail.getWorkflowEntranceName(), partyType.getPartyTypeName(),
                                    securityRoleDetail.getSecurityRole().getLastDetail().getSecurityRoleName());
                        }
                    }
                }
            } else {
                var workflowEntranceDetail = workflowEntrance.getLastDetail();

                handleExecutionError(WorkflowMissingSecurityRoleGroupException.class, eea, ExecutionErrors.WorkflowMissingSecurityRoleGroup.name(),
                        workflowEntranceDetail.getWorkflow().getLastDetail().getWorkflowName());
            }
        }

        return workflowEntranceSecurityRole;
    }

    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRoleByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String partyTypeName, final String securityRoleName) {
        return getWorkflowEntranceSecurityRoleByName(eea, workflowName, workflowEntranceName, partyTypeName, securityRoleName,
                EntityPermission.READ_ONLY);
    }


    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRoleByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String partyTypeName, final String securityRoleName) {
        return getWorkflowEntranceSecurityRoleByName(eea, workflowName, workflowEntranceName, partyTypeName, securityRoleName,
                EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSelector getWorkflowEntranceSelectorByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String selectorName, final EntityPermission entityPermission) {
        var workflowEntrance = getWorkflowEntranceByName(eea, workflowName, workflowEntranceName);
        WorkflowEntranceSelector workflowEntranceSelector = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var selectorType = workflowEntrance.getLastDetail().getWorkflow().getLastDetail().getSelectorType();

            if(selectorType != null) {
                var selector = SelectorLogic.getInstance().getSelectorByName(eea, selectorType, selectorName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

                    workflowEntranceSelector = workflowControl.getWorkflowEntranceSelector(workflowEntrance,
                            selector, entityPermission);

                    if(workflowEntranceSelector == null) {
                        var workflowEntranceDetail = workflowEntrance.getLastDetail();
                        var selectorDetail = selector.getLastDetail();

                        handleExecutionError(UnknownWorkflowEntranceSelectorException.class, eea, ExecutionErrors.UnknownWorkflowEntranceSelector.name(),
                                workflowEntranceDetail.getWorkflow().getLastDetail().getWorkflowName(),
                                workflowEntranceDetail.getWorkflowEntranceName(),
                                selectorDetail.getSelector().getLastDetail().getSelectorName());
                    }
                }
            } else {
                var workflowEntranceDetail = workflowEntrance.getLastDetail();

                handleExecutionError(WorkflowMissingSelectorTypeException.class, eea, ExecutionErrors.WorkflowMissingSelectorType.name(),
                        workflowEntranceDetail.getWorkflow().getLastDetail().getWorkflowName());
            }
        }

        return workflowEntranceSelector;
    }

    public WorkflowEntranceSelector getWorkflowEntranceSelectorByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String selectorName) {
        return getWorkflowEntranceSelectorByName(eea, workflowName, workflowEntranceName, selectorName,
                EntityPermission.READ_ONLY);
    }


    public WorkflowEntranceSelector getWorkflowEntranceSelectorByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName, final String selectorName) {
        return getWorkflowEntranceSelectorByName(eea, workflowName, workflowEntranceName, selectorName,
                EntityPermission.READ_WRITE);
    }

    public WorkflowStep getEntranceWorkflowStep(final ExecutionErrorAccumulator eea, final String entranceWorkflowName,
            final String entranceWorkflowStepName) {
        return WorkflowStepLogic.getInstance().getWorkflowStepByName(
                UnknownEntranceWorkflowNameException.class, ExecutionErrors.UnknownEntranceWorkflowName,
                UnknownEntranceWorkflowStepNameException.class, ExecutionErrors.UnknownEntranceWorkflowStepName,
                eea, entranceWorkflowName, entranceWorkflowStepName);
    }

    public WorkflowEntranceStep getWorkflowEntranceStep(final ExecutionErrorAccumulator eea,
            WorkflowEntrance workflowEntrance, WorkflowStep entranceWorkflowStep) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        var workflowEntranceStep = workflowControl.getWorkflowEntranceStep(workflowEntrance, entranceWorkflowStep);

        if(workflowEntranceStep == null) {
            var workflowEntranceDetail = workflowEntrance.getLastDetail();
            var entranceWorkflowStepDetail = entranceWorkflowStep.getLastDetail();

            handleExecutionError(UnknownWorkflowEntranceStepException.class, eea, ExecutionErrors.UnknownWorkflowEntranceStep.name(),
                    workflowEntranceDetail.getWorkflow().getLastDetail().getWorkflowName(),
                    workflowEntranceDetail.getWorkflowEntranceName(),
                    entranceWorkflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(),
                    entranceWorkflowStepDetail.getWorkflowStepName());
        }

        return workflowEntranceStep;
    }

    public WorkflowEntranceStep getWorkflowEntranceStepByName(final ExecutionErrorAccumulator eea,
            final String workflowName, final String workflowEntranceName, final String entranceWorkflowName,
            final String entranceWorkflowStepName) {
        var workflowEntrance = getWorkflowEntranceByName(eea, workflowName, workflowEntranceName);
        var entranceWorkflowStep = getEntranceWorkflowStep(eea, entranceWorkflowName, entranceWorkflowStepName);
        WorkflowEntranceStep workflowEntranceStep = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workflowEntranceStep = getWorkflowEntranceStep(eea, workflowEntrance, entranceWorkflowStep);
        }

        return workflowEntranceStep;
    }
    
}
