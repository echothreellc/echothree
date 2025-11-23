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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.control.user.workflow.common.spec.WorkflowEntranceUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.control.workflow.common.exception.MissingRequiredWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.UnknownDefaultWorkflowEntranceException;
import com.echothree.model.control.workflow.common.exception.UnknownEntranceWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.UnknownEntranceWorkflowStepNameException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntranceNameException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntrancePartyTypeException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntranceSecurityRoleException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntranceSelectorException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntranceStepException;
import com.echothree.model.control.workflow.common.exception.WorkflowMissingSecurityRoleGroupException;
import com.echothree.model.control.workflow.common.exception.WorkflowMissingSelectorTypeException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSelector;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WorkflowEntranceLogic
        extends BaseLogic {

    protected WorkflowEntranceLogic() {
        super();
    }

    public static WorkflowEntranceLogic getInstance() {
        return CDI.current().select(WorkflowEntranceLogic.class).get();
    }

    public WorkflowEntrance getWorkflowEntranceByName(final Class<? extends BaseException> unknownWorkflowException, final ExecutionErrors unknownWorkflowExecutionError,
            final Class<? extends BaseException>  unknownWorkflowEntranceException, final ExecutionErrors unknownWorkflowEntranceExecutionError,
            final ExecutionErrorAccumulator eea, final String workflowName, final String workflowEntranceName) {
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(unknownWorkflowException, unknownWorkflowExecutionError,
                eea, workflowName, EntityPermission.READ_ONLY);
        WorkflowEntrance workflowEntrance = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workflowEntrance = getWorkflowEntranceByName(unknownWorkflowEntranceException, unknownWorkflowEntranceExecutionError, eea,
                    workflow, workflowEntranceName);
        }

        return workflowEntrance;
    }

    public WorkflowEntrance getWorkflowEntranceByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowEntranceName, EntityPermission entityPermission) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName, entityPermission);

        if(workflowEntrance == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), workflow.getLastDetail().getWorkflowName(),
                    workflowEntranceName);
        }

        return workflowEntrance;
    }

    public WorkflowEntrance getWorkflowEntranceByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowEntranceName) {
        return getWorkflowEntranceByName(unknownException, unknownExecutionError, eea, workflow, workflowEntranceName, EntityPermission.READ_ONLY);
    }

    public WorkflowEntrance getWorkflowEntranceByNameForUpdate(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowEntranceName) {
        return getWorkflowEntranceByName(unknownException, unknownExecutionError, eea, workflow, workflowEntranceName, EntityPermission.READ_WRITE);
    }

    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowEntranceName,
            final EntityPermission entityPermission) {
        return getWorkflowEntranceByName(UnknownWorkflowEntranceNameException.class, ExecutionErrors.UnknownWorkflowEntranceName,
                eea, workflow, workflowEntranceName, entityPermission);
    }

    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowEntranceName) {
        return getWorkflowEntranceByName(UnknownWorkflowEntranceNameException.class, ExecutionErrors.UnknownWorkflowEntranceName,
                eea, workflow, workflowEntranceName);
    }

    public WorkflowEntrance getWorkflowEntranceByNameForUpdate(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowEntranceName) {
        return getWorkflowEntranceByNameForUpdate(UnknownWorkflowEntranceNameException.class, ExecutionErrors.UnknownWorkflowEntranceName,
                eea, workflow, workflowEntranceName);
    }

    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowEntranceName,
            final EntityPermission entityPermission) {
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
        WorkflowEntrance workflowEntrance = null;

        if(!eea.hasExecutionErrors()) {
            workflowEntrance = getWorkflowEntranceByName(UnknownWorkflowEntranceNameException.class, ExecutionErrors.UnknownWorkflowEntranceName,
                    eea, workflow, workflowEntranceName, entityPermission);
        }

        return workflowEntrance;
    }

    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowEntranceName) {
        return getWorkflowEntranceByName(eea, workflowName, workflowEntranceName, EntityPermission.READ_ONLY);
    }

    public WorkflowEntrance getWorkflowEntranceByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowEntranceName) {
        return getWorkflowEntranceByName(eea, workflowName, workflowEntranceName, EntityPermission.READ_WRITE);
    }

    public WorkflowEntrance getWorkflowEntranceByUniversalSpec(final ExecutionErrorAccumulator eea, final WorkflowEntranceUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowName = universalSpec.getWorkflowName();
        var workflowEntranceName = universalSpec.getWorkflowEntranceName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(workflowName, workflowEntranceName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        WorkflowEntrance workflowEntrance = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            Workflow workflow = null;

            if(workflowName != null) {
                workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
            } else {
                handleExecutionError(MissingRequiredWorkflowNameException.class, eea, ExecutionErrors.MissingRequiredWorkflowName.name());
            }

            if(!eea.hasExecutionErrors()) {
                if(workflowEntranceName == null) {
                    if(allowDefault) {
                        workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow, entityPermission);

                        if(workflowEntrance == null) {
                            handleExecutionError(UnknownDefaultWorkflowEntranceException.class, eea, ExecutionErrors.UnknownDefaultWorkflowEntrance.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    workflowEntrance = getWorkflowEntranceByName(eea, workflow, workflowEntranceName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.WorkflowEntrance.name());

            if(!eea.hasExecutionErrors()) {
                workflowEntrance = workflowControl.getWorkflowEntranceByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return workflowEntrance;
    }

    public WorkflowEntrance getWorkflowEntranceByUniversalSpec(final ExecutionErrorAccumulator eea, final WorkflowEntranceUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWorkflowEntranceByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WorkflowEntrance getWorkflowEntranceByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final WorkflowEntranceUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWorkflowEntranceByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public WorkflowEntrancePartyType getWorkflowEntrancePartyType(final ExecutionErrorAccumulator eea, final WorkflowEntrance workflowEntrance,
            final PartyType partyType, final EntityPermission entityPermission) {
        WorkflowEntrancePartyType workflowEntrancePartyType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

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
                        var workflowControl = Session.getModelController(WorkflowControl.class);

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
                    var workflowControl = Session.getModelController(WorkflowControl.class);

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
        var workflowControl = Session.getModelController(WorkflowControl.class);
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
