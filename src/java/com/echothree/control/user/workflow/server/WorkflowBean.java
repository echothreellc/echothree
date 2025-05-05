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
    //   Workflow Step Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStepType(UserVisitPK userVisitPK, CreateWorkflowStepTypeForm form) {
        return new CreateWorkflowStepTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkflowStepType(UserVisitPK userVisitPK, GetWorkflowStepTypeForm form) {
        return new GetWorkflowStepTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkflowStepTypes(UserVisitPK userVisitPK, GetWorkflowStepTypesForm form) {
        return new GetWorkflowStepTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkflowStepTypeChoices(UserVisitPK userVisitPK, GetWorkflowStepTypeChoicesForm form) {
        return new GetWorkflowStepTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStepTypeDescription(UserVisitPK userVisitPK, CreateWorkflowStepTypeDescriptionForm form) {
        return new CreateWorkflowStepTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflows
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflow(UserVisitPK userVisitPK, CreateWorkflowForm form) {
        return new CreateWorkflowCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflow(UserVisitPK userVisitPK, GetWorkflowForm form) {
        return new GetWorkflowCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflows(UserVisitPK userVisitPK, GetWorkflowsForm form) {
        return new GetWorkflowsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkflowChoices(UserVisitPK userVisitPK, GetWorkflowChoicesForm form) {
        return new GetWorkflowChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflow(UserVisitPK userVisitPK, EditWorkflowForm form) {
        return new EditWorkflowCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflow(UserVisitPK userVisitPK, DeleteWorkflowForm form) {
        return new DeleteWorkflowCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDescription(UserVisitPK userVisitPK, CreateWorkflowDescriptionForm form) {
        return new CreateWorkflowDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDescription(UserVisitPK userVisitPK, GetWorkflowDescriptionForm form) {
        return new GetWorkflowDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDescriptions(UserVisitPK userVisitPK, GetWorkflowDescriptionsForm form) {
        return new GetWorkflowDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflowDescription(UserVisitPK userVisitPK, EditWorkflowDescriptionForm form) {
        return new EditWorkflowDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowDescription(UserVisitPK userVisitPK, DeleteWorkflowDescriptionForm form) {
        return new DeleteWorkflowDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStep(UserVisitPK userVisitPK, CreateWorkflowStepForm form) {
        return new CreateWorkflowStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowStep(UserVisitPK userVisitPK, GetWorkflowStepForm form) {
        return new GetWorkflowStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowSteps(UserVisitPK userVisitPK, GetWorkflowStepsForm form) {
        return new GetWorkflowStepsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowStepChoices(UserVisitPK userVisitPK, GetWorkflowStepChoicesForm form) {
        return new GetWorkflowStepChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflowStep(UserVisitPK userVisitPK, EditWorkflowStepForm form) {
        return new EditWorkflowStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWorkflowStep(UserVisitPK userVisitPK, SetDefaultWorkflowStepForm form) {
        return new SetDefaultWorkflowStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowStep(UserVisitPK userVisitPK, DeleteWorkflowStepForm form) {
        return new DeleteWorkflowStepCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Description
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowStepDescription(UserVisitPK userVisitPK, CreateWorkflowStepDescriptionForm form) {
        return new CreateWorkflowStepDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowStepDescription(UserVisitPK userVisitPK, GetWorkflowStepDescriptionForm form) {
        return new GetWorkflowStepDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowStepDescriptions(UserVisitPK userVisitPK, GetWorkflowStepDescriptionsForm form) {
        return new GetWorkflowStepDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflowStepDescription(UserVisitPK userVisitPK, EditWorkflowStepDescriptionForm form) {
        return new EditWorkflowStepDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowStepDescription(UserVisitPK userVisitPK, DeleteWorkflowStepDescriptionForm form) {
        return new DeleteWorkflowStepDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destinations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestination(UserVisitPK userVisitPK, CreateWorkflowDestinationForm form) {
        return new CreateWorkflowDestinationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestination(UserVisitPK userVisitPK, GetWorkflowDestinationForm form) {
        return new GetWorkflowDestinationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinations(UserVisitPK userVisitPK, GetWorkflowDestinationsForm form) {
        return new GetWorkflowDestinationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflowDestination(UserVisitPK userVisitPK, EditWorkflowDestinationForm form) {
        return new EditWorkflowDestinationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWorkflowDestination(UserVisitPK userVisitPK, SetDefaultWorkflowDestinationForm form) {
        return new SetDefaultWorkflowDestinationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowDestination(UserVisitPK userVisitPK, DeleteWorkflowDestinationForm form) {
        return new DeleteWorkflowDestinationCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationDescription(UserVisitPK userVisitPK, CreateWorkflowDestinationDescriptionForm form) {
        return new CreateWorkflowDestinationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationDescription(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionForm form) {
        return new GetWorkflowDestinationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationDescriptions(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionsForm form) {
        return new GetWorkflowDestinationDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflowDestinationDescription(UserVisitPK userVisitPK, EditWorkflowDestinationDescriptionForm form) {
        return new EditWorkflowDestinationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationDescription(UserVisitPK userVisitPK, DeleteWorkflowDestinationDescriptionForm form) {
        return new DeleteWorkflowDestinationDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationStep(UserVisitPK userVisitPK, CreateWorkflowDestinationStepForm form) {
        return new CreateWorkflowDestinationStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationStep(UserVisitPK userVisitPK, GetWorkflowDestinationStepForm form) {
        return new GetWorkflowDestinationStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationSteps(UserVisitPK userVisitPK, GetWorkflowDestinationStepsForm form) {
        return new GetWorkflowDestinationStepsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationStep(UserVisitPK userVisitPK, DeleteWorkflowDestinationStepForm form) {
        return new DeleteWorkflowDestinationStepCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationSelector(UserVisitPK userVisitPK, CreateWorkflowDestinationSelectorForm form) {
        return new CreateWorkflowDestinationSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationSelector(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorForm form) {
        return new GetWorkflowDestinationSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationSelectors(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorsForm form) {
        return new GetWorkflowDestinationSelectorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationSelector(UserVisitPK userVisitPK, DeleteWorkflowDestinationSelectorForm form) {
        return new DeleteWorkflowDestinationSelectorCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Party Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationPartyType(UserVisitPK userVisitPK, CreateWorkflowDestinationPartyTypeForm form) {
        return new CreateWorkflowDestinationPartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationPartyType(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypeForm form) {
        return new GetWorkflowDestinationPartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationPartyTypes(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypesForm form) {
        return new GetWorkflowDestinationPartyTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationPartyType(UserVisitPK userVisitPK, DeleteWorkflowDestinationPartyTypeForm form) {
        return new DeleteWorkflowDestinationPartyTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Security Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, CreateWorkflowDestinationSecurityRoleForm form) {
        return new CreateWorkflowDestinationSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRoleForm form) {
        return new GetWorkflowDestinationSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowDestinationSecurityRoles(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRolesForm form) {
        return new GetWorkflowDestinationSecurityRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowDestinationSecurityRoleForm form) {
        return new DeleteWorkflowDestinationSecurityRoleCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntityType(UserVisitPK userVisitPK, CreateWorkflowEntityTypeForm form) {
        return new CreateWorkflowEntityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntityType(UserVisitPK userVisitPK, GetWorkflowEntityTypeForm form) {
        return new GetWorkflowEntityTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkflowEntityTypes(UserVisitPK userVisitPK, GetWorkflowEntityTypesForm form) {
        return new GetWorkflowEntityTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWorkflowEntityType(UserVisitPK userVisitPK, DeleteWorkflowEntityTypeForm form) {
        return new DeleteWorkflowEntityTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Workflow Entrances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntrance(UserVisitPK userVisitPK, CreateWorkflowEntranceForm form) {
        return new CreateWorkflowEntranceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntrance(UserVisitPK userVisitPK, GetWorkflowEntranceForm form) {
        return new GetWorkflowEntranceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntrances(UserVisitPK userVisitPK, GetWorkflowEntrancesForm form) {
        return new GetWorkflowEntrancesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflowEntrance(UserVisitPK userVisitPK, EditWorkflowEntranceForm form) {
        return new EditWorkflowEntranceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWorkflowEntrance(UserVisitPK userVisitPK, SetDefaultWorkflowEntranceForm form) {
        return new SetDefaultWorkflowEntranceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowEntrance(UserVisitPK userVisitPK, DeleteWorkflowEntranceForm form) {
        return new DeleteWorkflowEntranceCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceDescription(UserVisitPK userVisitPK, CreateWorkflowEntranceDescriptionForm form) {
        return new CreateWorkflowEntranceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceDescription(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionForm form) {
        return new GetWorkflowEntranceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceDescriptions(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionsForm form) {
        return new GetWorkflowEntranceDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkflowEntranceDescription(UserVisitPK userVisitPK, EditWorkflowEntranceDescriptionForm form) {
        return new EditWorkflowEntranceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceDescription(UserVisitPK userVisitPK, DeleteWorkflowEntranceDescriptionForm form) {
        return new DeleteWorkflowEntranceDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceStep(UserVisitPK userVisitPK, CreateWorkflowEntranceStepForm form) {
        return new CreateWorkflowEntranceStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceStep(UserVisitPK userVisitPK, GetWorkflowEntranceStepForm form) {
        return new GetWorkflowEntranceStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceSteps(UserVisitPK userVisitPK, GetWorkflowEntranceStepsForm form) {
        return new GetWorkflowEntranceStepsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceStep(UserVisitPK userVisitPK, DeleteWorkflowEntranceStepForm form) {
        return new DeleteWorkflowEntranceStepCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceSelector(UserVisitPK userVisitPK, CreateWorkflowEntranceSelectorForm form) {
        return new CreateWorkflowEntranceSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceSelector(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorForm form) {
        return new GetWorkflowEntranceSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceSelectors(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorsForm form) {
        return new GetWorkflowEntranceSelectorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceSelector(UserVisitPK userVisitPK, DeleteWorkflowEntranceSelectorForm form) {
        return new DeleteWorkflowEntranceSelectorCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Party Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntrancePartyType(UserVisitPK userVisitPK, CreateWorkflowEntrancePartyTypeForm form) {
        return new CreateWorkflowEntrancePartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntrancePartyType(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypeForm form) {
        return new GetWorkflowEntrancePartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntrancePartyTypes(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypesForm form) {
        return new GetWorkflowEntrancePartyTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowEntrancePartyType(UserVisitPK userVisitPK, DeleteWorkflowEntrancePartyTypeForm form) {
        return new DeleteWorkflowEntrancePartyTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Security Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, CreateWorkflowEntranceSecurityRoleForm form) {
        return new CreateWorkflowEntranceSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRoleForm form) {
        return new GetWorkflowEntranceSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowEntranceSecurityRoles(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRolesForm form) {
        return new GetWorkflowEntranceSecurityRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowEntranceSecurityRoleForm form) {
        return new DeleteWorkflowEntranceSecurityRoleCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Selector Kinds
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkflowSelectorKind(UserVisitPK userVisitPK, CreateWorkflowSelectorKindForm form) {
        return new CreateWorkflowSelectorKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowSelectorKind(UserVisitPK userVisitPK, GetWorkflowSelectorKindForm form) {
        return new GetWorkflowSelectorKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkflowSelectorKinds(UserVisitPK userVisitPK, GetWorkflowSelectorKindsForm form) {
        return new GetWorkflowSelectorKindsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkflowSelectorKind(UserVisitPK userVisitPK, DeleteWorkflowSelectorKindForm form) {
        return new DeleteWorkflowSelectorKindCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Statuses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getWorkflowEntityStatuses(UserVisitPK userVisitPK, GetWorkflowEntityStatusesForm form) {
        return new GetWorkflowEntityStatusesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Triggers
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult processWorkflowTriggers(UserVisitPK userVisitPK) {
        return new ProcessWorkflowTriggersCommand().run(userVisitPK);
    }
    
}
