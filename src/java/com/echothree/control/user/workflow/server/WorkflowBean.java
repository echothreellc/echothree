// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.workflow.server;

import com.echothree.control.user.workflow.common.WorkflowRemote;
import com.echothree.control.user.workflow.common.form.*;
import com.echothree.control.user.workflow.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class WorkflowBean
        extends WorkflowFormsImpl
        implements WorkflowRemote, WorkflowLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "WorkflowBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowType(UserVisitPK userVisitPK, CreateWorkflowTypeForm form) {
        return new CreateWorkflowTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowTypeChoices(UserVisitPK userVisitPK, GetWorkflowTypeChoicesForm form) {
        return new GetWorkflowTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowTypeDescription(UserVisitPK userVisitPK, CreateWorkflowTypeDescriptionForm form) {
        return new CreateWorkflowTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStepType(UserVisitPK userVisitPK, CreateWorkflowStepTypeForm form) {
        return new CreateWorkflowStepTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowStepTypeChoices(UserVisitPK userVisitPK, GetWorkflowStepTypeChoicesForm form) {
        return new GetWorkflowStepTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStepTypeDescription(UserVisitPK userVisitPK, CreateWorkflowStepTypeDescriptionForm form) {
        return new CreateWorkflowStepTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflows
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflow(UserVisitPK userVisitPK, CreateWorkflowForm form) {
        return new CreateWorkflowCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflow(UserVisitPK userVisitPK, GetWorkflowForm form) {
        return new GetWorkflowCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflows(UserVisitPK userVisitPK, GetWorkflowsForm form) {
        return new GetWorkflowsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflow(UserVisitPK userVisitPK, EditWorkflowForm form) {
        return new EditWorkflowCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflow(UserVisitPK userVisitPK, DeleteWorkflowForm form) {
        return new DeleteWorkflowCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDescription(UserVisitPK userVisitPK, CreateWorkflowDescriptionForm form) {
        return new CreateWorkflowDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDescription(UserVisitPK userVisitPK, GetWorkflowDescriptionForm form) {
        return new GetWorkflowDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDescriptions(UserVisitPK userVisitPK, GetWorkflowDescriptionsForm form) {
        return new GetWorkflowDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflowDescription(UserVisitPK userVisitPK, EditWorkflowDescriptionForm form) {
        return new EditWorkflowDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowDescription(UserVisitPK userVisitPK, DeleteWorkflowDescriptionForm form) {
        return new DeleteWorkflowDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStep(UserVisitPK userVisitPK, CreateWorkflowStepForm form) {
        return new CreateWorkflowStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowStep(UserVisitPK userVisitPK, GetWorkflowStepForm form) {
        return new GetWorkflowStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowSteps(UserVisitPK userVisitPK, GetWorkflowStepsForm form) {
        return new GetWorkflowStepsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowStepChoices(UserVisitPK userVisitPK, GetWorkflowStepChoicesForm form) {
        return new GetWorkflowStepChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflowStep(UserVisitPK userVisitPK, EditWorkflowStepForm form) {
        return new EditWorkflowStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultWorkflowStep(UserVisitPK userVisitPK, SetDefaultWorkflowStepForm form) {
        return new SetDefaultWorkflowStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowStep(UserVisitPK userVisitPK, DeleteWorkflowStepForm form) {
        return new DeleteWorkflowStepCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Description
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStepDescription(UserVisitPK userVisitPK, CreateWorkflowStepDescriptionForm form) {
        return new CreateWorkflowStepDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowStepDescription(UserVisitPK userVisitPK, GetWorkflowStepDescriptionForm form) {
        return new GetWorkflowStepDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowStepDescriptions(UserVisitPK userVisitPK, GetWorkflowStepDescriptionsForm form) {
        return new GetWorkflowStepDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflowStepDescription(UserVisitPK userVisitPK, EditWorkflowStepDescriptionForm form) {
        return new EditWorkflowStepDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowStepDescription(UserVisitPK userVisitPK, DeleteWorkflowStepDescriptionForm form) {
        return new DeleteWorkflowStepDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destinations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestination(UserVisitPK userVisitPK, CreateWorkflowDestinationForm form) {
        return new CreateWorkflowDestinationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestination(UserVisitPK userVisitPK, GetWorkflowDestinationForm form) {
        return new GetWorkflowDestinationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinations(UserVisitPK userVisitPK, GetWorkflowDestinationsForm form) {
        return new GetWorkflowDestinationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflowDestination(UserVisitPK userVisitPK, EditWorkflowDestinationForm form) {
        return new EditWorkflowDestinationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultWorkflowDestination(UserVisitPK userVisitPK, SetDefaultWorkflowDestinationForm form) {
        return new SetDefaultWorkflowDestinationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowDestination(UserVisitPK userVisitPK, DeleteWorkflowDestinationForm form) {
        return new DeleteWorkflowDestinationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationDescription(UserVisitPK userVisitPK, CreateWorkflowDestinationDescriptionForm form) {
        return new CreateWorkflowDestinationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationDescription(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionForm form) {
        return new GetWorkflowDestinationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationDescriptions(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionsForm form) {
        return new GetWorkflowDestinationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflowDestinationDescription(UserVisitPK userVisitPK, EditWorkflowDestinationDescriptionForm form) {
        return new EditWorkflowDestinationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationDescription(UserVisitPK userVisitPK, DeleteWorkflowDestinationDescriptionForm form) {
        return new DeleteWorkflowDestinationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationStep(UserVisitPK userVisitPK, CreateWorkflowDestinationStepForm form) {
        return new CreateWorkflowDestinationStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationStep(UserVisitPK userVisitPK, GetWorkflowDestinationStepForm form) {
        return new GetWorkflowDestinationStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationSteps(UserVisitPK userVisitPK, GetWorkflowDestinationStepsForm form) {
        return new GetWorkflowDestinationStepsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationStep(UserVisitPK userVisitPK, DeleteWorkflowDestinationStepForm form) {
        return new DeleteWorkflowDestinationStepCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationSelector(UserVisitPK userVisitPK, CreateWorkflowDestinationSelectorForm form) {
        return new CreateWorkflowDestinationSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationSelector(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorForm form) {
        return new GetWorkflowDestinationSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationSelectors(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorsForm form) {
        return new GetWorkflowDestinationSelectorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationSelector(UserVisitPK userVisitPK, DeleteWorkflowDestinationSelectorForm form) {
        return new DeleteWorkflowDestinationSelectorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Party Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationPartyType(UserVisitPK userVisitPK, CreateWorkflowDestinationPartyTypeForm form) {
        return new CreateWorkflowDestinationPartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationPartyType(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypeForm form) {
        return new GetWorkflowDestinationPartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationPartyTypes(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypesForm form) {
        return new GetWorkflowDestinationPartyTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationPartyType(UserVisitPK userVisitPK, DeleteWorkflowDestinationPartyTypeForm form) {
        return new DeleteWorkflowDestinationPartyTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Security Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, CreateWorkflowDestinationSecurityRoleForm form) {
        return new CreateWorkflowDestinationSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRoleForm form) {
        return new GetWorkflowDestinationSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowDestinationSecurityRoles(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRolesForm form) {
        return new GetWorkflowDestinationSecurityRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowDestinationSecurityRoleForm form) {
        return new DeleteWorkflowDestinationSecurityRoleCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntityType(UserVisitPK userVisitPK, CreateWorkflowEntityTypeForm form) {
        return new CreateWorkflowEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntityType(UserVisitPK userVisitPK, GetWorkflowEntityTypeForm form) {
        return new GetWorkflowEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getWorkflowEntityTypes(UserVisitPK userVisitPK, GetWorkflowEntityTypesForm form) {
        return new GetWorkflowEntityTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteWorkflowEntityType(UserVisitPK userVisitPK, DeleteWorkflowEntityTypeForm form) {
        return new DeleteWorkflowEntityTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Workflow Entrances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntrance(UserVisitPK userVisitPK, CreateWorkflowEntranceForm form) {
        return new CreateWorkflowEntranceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntrance(UserVisitPK userVisitPK, GetWorkflowEntranceForm form) {
        return new GetWorkflowEntranceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntrances(UserVisitPK userVisitPK, GetWorkflowEntrancesForm form) {
        return new GetWorkflowEntrancesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflowEntrance(UserVisitPK userVisitPK, EditWorkflowEntranceForm form) {
        return new EditWorkflowEntranceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultWorkflowEntrance(UserVisitPK userVisitPK, SetDefaultWorkflowEntranceForm form) {
        return new SetDefaultWorkflowEntranceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowEntrance(UserVisitPK userVisitPK, DeleteWorkflowEntranceForm form) {
        return new DeleteWorkflowEntranceCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceDescription(UserVisitPK userVisitPK, CreateWorkflowEntranceDescriptionForm form) {
        return new CreateWorkflowEntranceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceDescription(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionForm form) {
        return new GetWorkflowEntranceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceDescriptions(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionsForm form) {
        return new GetWorkflowEntranceDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkflowEntranceDescription(UserVisitPK userVisitPK, EditWorkflowEntranceDescriptionForm form) {
        return new EditWorkflowEntranceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceDescription(UserVisitPK userVisitPK, DeleteWorkflowEntranceDescriptionForm form) {
        return new DeleteWorkflowEntranceDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceStep(UserVisitPK userVisitPK, CreateWorkflowEntranceStepForm form) {
        return new CreateWorkflowEntranceStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceStep(UserVisitPK userVisitPK, GetWorkflowEntranceStepForm form) {
        return new GetWorkflowEntranceStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceSteps(UserVisitPK userVisitPK, GetWorkflowEntranceStepsForm form) {
        return new GetWorkflowEntranceStepsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceStep(UserVisitPK userVisitPK, DeleteWorkflowEntranceStepForm form) {
        return new DeleteWorkflowEntranceStepCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceSelector(UserVisitPK userVisitPK, CreateWorkflowEntranceSelectorForm form) {
        return new CreateWorkflowEntranceSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceSelector(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorForm form) {
        return new GetWorkflowEntranceSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceSelectors(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorsForm form) {
        return new GetWorkflowEntranceSelectorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceSelector(UserVisitPK userVisitPK, DeleteWorkflowEntranceSelectorForm form) {
        return new DeleteWorkflowEntranceSelectorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Party Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntrancePartyType(UserVisitPK userVisitPK, CreateWorkflowEntrancePartyTypeForm form) {
        return new CreateWorkflowEntrancePartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntrancePartyType(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypeForm form) {
        return new GetWorkflowEntrancePartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntrancePartyTypes(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypesForm form) {
        return new GetWorkflowEntrancePartyTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowEntrancePartyType(UserVisitPK userVisitPK, DeleteWorkflowEntrancePartyTypeForm form) {
        return new DeleteWorkflowEntrancePartyTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Security Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, CreateWorkflowEntranceSecurityRoleForm form) {
        return new CreateWorkflowEntranceSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRoleForm form) {
        return new GetWorkflowEntranceSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowEntranceSecurityRoles(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRolesForm form) {
        return new GetWorkflowEntranceSecurityRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowEntranceSecurityRoleForm form) {
        return new DeleteWorkflowEntranceSecurityRoleCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Selector Kinds
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowSelectorKind(UserVisitPK userVisitPK, CreateWorkflowSelectorKindForm form) {
        return new CreateWorkflowSelectorKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowSelectorKind(UserVisitPK userVisitPK, GetWorkflowSelectorKindForm form) {
        return new GetWorkflowSelectorKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkflowSelectorKinds(UserVisitPK userVisitPK, GetWorkflowSelectorKindsForm form) {
        return new GetWorkflowSelectorKindsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkflowSelectorKind(UserVisitPK userVisitPK, DeleteWorkflowSelectorKindForm form) {
        return new DeleteWorkflowSelectorKindCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Statuses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getWorkflowEntityStatuses(UserVisitPK userVisitPK, GetWorkflowEntityStatusesForm form) {
        return new GetWorkflowEntityStatusesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Triggers
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult processWorkflowTriggers(UserVisitPK userVisitPK) {
        return new ProcessWorkflowTriggersCommand(userVisitPK).run();
    }
    
}
