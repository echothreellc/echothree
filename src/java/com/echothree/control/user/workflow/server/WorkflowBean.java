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

package com.echothree.control.user.workflow.server;

import com.echothree.control.user.workflow.common.WorkflowRemote;
import com.echothree.control.user.workflow.common.form.*;
import com.echothree.control.user.workflow.common.result.*;
import com.echothree.control.user.workflow.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<?> createWorkflowStepType(UserVisitPK userVisitPK, CreateWorkflowStepTypeForm form) {
        return CDI.current().select(CreateWorkflowStepTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWorkflowStepTypeResult> getWorkflowStepType(UserVisitPK userVisitPK, GetWorkflowStepTypeForm form) {
        return CDI.current().select(GetWorkflowStepTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWorkflowStepTypesResult> getWorkflowStepTypes(UserVisitPK userVisitPK, GetWorkflowStepTypesForm form) {
        return CDI.current().select(GetWorkflowStepTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWorkflowStepTypeChoicesResult> getWorkflowStepTypeChoices(UserVisitPK userVisitPK, GetWorkflowStepTypeChoicesForm form) {
        return CDI.current().select(GetWorkflowStepTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowStepTypeDescription(UserVisitPK userVisitPK, CreateWorkflowStepTypeDescriptionForm form) {
        return CDI.current().select(CreateWorkflowStepTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflows
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateWorkflowResult> createWorkflow(UserVisitPK userVisitPK, CreateWorkflowForm form) {
        return CDI.current().select(CreateWorkflowCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowResult> getWorkflow(UserVisitPK userVisitPK, GetWorkflowForm form) {
        return CDI.current().select(GetWorkflowCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowsResult> getWorkflows(UserVisitPK userVisitPK, GetWorkflowsForm form) {
        return CDI.current().select(GetWorkflowsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWorkflowChoicesResult> getWorkflowChoices(UserVisitPK userVisitPK, GetWorkflowChoicesForm form) {
        return CDI.current().select(GetWorkflowChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowResult> editWorkflow(UserVisitPK userVisitPK, EditWorkflowForm form) {
        return CDI.current().select(EditWorkflowCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflow(UserVisitPK userVisitPK, DeleteWorkflowForm form) {
        return CDI.current().select(DeleteWorkflowCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowDescription(UserVisitPK userVisitPK, CreateWorkflowDescriptionForm form) {
        return CDI.current().select(CreateWorkflowDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDescriptionResult> getWorkflowDescription(UserVisitPK userVisitPK, GetWorkflowDescriptionForm form) {
        return CDI.current().select(GetWorkflowDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDescriptionsResult> getWorkflowDescriptions(UserVisitPK userVisitPK, GetWorkflowDescriptionsForm form) {
        return CDI.current().select(GetWorkflowDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowDescriptionResult> editWorkflowDescription(UserVisitPK userVisitPK, EditWorkflowDescriptionForm form) {
        return CDI.current().select(EditWorkflowDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowDescription(UserVisitPK userVisitPK, DeleteWorkflowDescriptionForm form) {
        return CDI.current().select(DeleteWorkflowDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateWorkflowStepResult> createWorkflowStep(UserVisitPK userVisitPK, CreateWorkflowStepForm form) {
        return CDI.current().select(CreateWorkflowStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowStepResult> getWorkflowStep(UserVisitPK userVisitPK, GetWorkflowStepForm form) {
        return CDI.current().select(GetWorkflowStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowStepsResult> getWorkflowSteps(UserVisitPK userVisitPK, GetWorkflowStepsForm form) {
        return CDI.current().select(GetWorkflowStepsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowStepChoicesResult> getWorkflowStepChoices(UserVisitPK userVisitPK, GetWorkflowStepChoicesForm form) {
        return CDI.current().select(GetWorkflowStepChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowStepResult> editWorkflowStep(UserVisitPK userVisitPK, EditWorkflowStepForm form) {
        return CDI.current().select(EditWorkflowStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultWorkflowStep(UserVisitPK userVisitPK, SetDefaultWorkflowStepForm form) {
        return CDI.current().select(SetDefaultWorkflowStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowStep(UserVisitPK userVisitPK, DeleteWorkflowStepForm form) {
        return CDI.current().select(DeleteWorkflowStepCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Description
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowStepDescription(UserVisitPK userVisitPK, CreateWorkflowStepDescriptionForm form) {
        return CDI.current().select(CreateWorkflowStepDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowStepDescriptionResult> getWorkflowStepDescription(UserVisitPK userVisitPK, GetWorkflowStepDescriptionForm form) {
        return CDI.current().select(GetWorkflowStepDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowStepDescriptionsResult> getWorkflowStepDescriptions(UserVisitPK userVisitPK, GetWorkflowStepDescriptionsForm form) {
        return CDI.current().select(GetWorkflowStepDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowStepDescriptionResult> editWorkflowStepDescription(UserVisitPK userVisitPK, EditWorkflowStepDescriptionForm form) {
        return CDI.current().select(EditWorkflowStepDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowStepDescription(UserVisitPK userVisitPK, DeleteWorkflowStepDescriptionForm form) {
        return CDI.current().select(DeleteWorkflowStepDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destinations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateWorkflowDestinationResult> createWorkflowDestination(UserVisitPK userVisitPK, CreateWorkflowDestinationForm form) {
        return CDI.current().select(CreateWorkflowDestinationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationResult> getWorkflowDestination(UserVisitPK userVisitPK, GetWorkflowDestinationForm form) {
        return CDI.current().select(GetWorkflowDestinationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationsResult> getWorkflowDestinations(UserVisitPK userVisitPK, GetWorkflowDestinationsForm form) {
        return CDI.current().select(GetWorkflowDestinationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowDestinationResult> editWorkflowDestination(UserVisitPK userVisitPK, EditWorkflowDestinationForm form) {
        return CDI.current().select(EditWorkflowDestinationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultWorkflowDestination(UserVisitPK userVisitPK, SetDefaultWorkflowDestinationForm form) {
        return CDI.current().select(SetDefaultWorkflowDestinationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowDestination(UserVisitPK userVisitPK, DeleteWorkflowDestinationForm form) {
        return CDI.current().select(DeleteWorkflowDestinationCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowDestinationDescription(UserVisitPK userVisitPK, CreateWorkflowDestinationDescriptionForm form) {
        return CDI.current().select(CreateWorkflowDestinationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationDescriptionResult> getWorkflowDestinationDescription(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionForm form) {
        return CDI.current().select(GetWorkflowDestinationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationDescriptionsResult> getWorkflowDestinationDescriptions(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionsForm form) {
        return CDI.current().select(GetWorkflowDestinationDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowDestinationDescriptionResult> editWorkflowDestinationDescription(UserVisitPK userVisitPK, EditWorkflowDestinationDescriptionForm form) {
        return CDI.current().select(EditWorkflowDestinationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowDestinationDescription(UserVisitPK userVisitPK, DeleteWorkflowDestinationDescriptionForm form) {
        return CDI.current().select(DeleteWorkflowDestinationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowDestinationStep(UserVisitPK userVisitPK, CreateWorkflowDestinationStepForm form) {
        return CDI.current().select(CreateWorkflowDestinationStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationStepResult> getWorkflowDestinationStep(UserVisitPK userVisitPK, GetWorkflowDestinationStepForm form) {
        return CDI.current().select(GetWorkflowDestinationStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationStepsResult> getWorkflowDestinationSteps(UserVisitPK userVisitPK, GetWorkflowDestinationStepsForm form) {
        return CDI.current().select(GetWorkflowDestinationStepsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowDestinationStep(UserVisitPK userVisitPK, DeleteWorkflowDestinationStepForm form) {
        return CDI.current().select(DeleteWorkflowDestinationStepCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowDestinationSelector(UserVisitPK userVisitPK, CreateWorkflowDestinationSelectorForm form) {
        return CDI.current().select(CreateWorkflowDestinationSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationSelectorResult> getWorkflowDestinationSelector(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorForm form) {
        return CDI.current().select(GetWorkflowDestinationSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationSelectorsResult> getWorkflowDestinationSelectors(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorsForm form) {
        return CDI.current().select(GetWorkflowDestinationSelectorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowDestinationSelector(UserVisitPK userVisitPK, DeleteWorkflowDestinationSelectorForm form) {
        return CDI.current().select(DeleteWorkflowDestinationSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Party Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowDestinationPartyType(UserVisitPK userVisitPK, CreateWorkflowDestinationPartyTypeForm form) {
        return CDI.current().select(CreateWorkflowDestinationPartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationPartyTypeResult> getWorkflowDestinationPartyType(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypeForm form) {
        return CDI.current().select(GetWorkflowDestinationPartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationPartyTypesResult> getWorkflowDestinationPartyTypes(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypesForm form) {
        return CDI.current().select(GetWorkflowDestinationPartyTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowDestinationPartyType(UserVisitPK userVisitPK, DeleteWorkflowDestinationPartyTypeForm form) {
        return CDI.current().select(DeleteWorkflowDestinationPartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Security Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, CreateWorkflowDestinationSecurityRoleForm form) {
        return CDI.current().select(CreateWorkflowDestinationSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationSecurityRoleResult> getWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRoleForm form) {
        return CDI.current().select(GetWorkflowDestinationSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowDestinationSecurityRolesResult> getWorkflowDestinationSecurityRoles(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRolesForm form) {
        return CDI.current().select(GetWorkflowDestinationSecurityRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowDestinationSecurityRoleForm form) {
        return CDI.current().select(DeleteWorkflowDestinationSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowEntityType(UserVisitPK userVisitPK, CreateWorkflowEntityTypeForm form) {
        return CDI.current().select(CreateWorkflowEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntityTypeResult> getWorkflowEntityType(UserVisitPK userVisitPK, GetWorkflowEntityTypeForm form) {
        return CDI.current().select(GetWorkflowEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetWorkflowEntityTypesResult> getWorkflowEntityTypes(UserVisitPK userVisitPK, GetWorkflowEntityTypesForm form) {
        return CDI.current().select(GetWorkflowEntityTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteWorkflowEntityType(UserVisitPK userVisitPK, DeleteWorkflowEntityTypeForm form) {
        return CDI.current().select(DeleteWorkflowEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Workflow Entrances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateWorkflowEntranceResult> createWorkflowEntrance(UserVisitPK userVisitPK, CreateWorkflowEntranceForm form) {
        return CDI.current().select(CreateWorkflowEntranceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceResult> getWorkflowEntrance(UserVisitPK userVisitPK, GetWorkflowEntranceForm form) {
        return CDI.current().select(GetWorkflowEntranceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntrancesResult> getWorkflowEntrances(UserVisitPK userVisitPK, GetWorkflowEntrancesForm form) {
        return CDI.current().select(GetWorkflowEntrancesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowEntranceResult> editWorkflowEntrance(UserVisitPK userVisitPK, EditWorkflowEntranceForm form) {
        return CDI.current().select(EditWorkflowEntranceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> setDefaultWorkflowEntrance(UserVisitPK userVisitPK, SetDefaultWorkflowEntranceForm form) {
        return CDI.current().select(SetDefaultWorkflowEntranceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowEntrance(UserVisitPK userVisitPK, DeleteWorkflowEntranceForm form) {
        return CDI.current().select(DeleteWorkflowEntranceCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowEntranceDescription(UserVisitPK userVisitPK, CreateWorkflowEntranceDescriptionForm form) {
        return CDI.current().select(CreateWorkflowEntranceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceDescriptionResult> getWorkflowEntranceDescription(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionForm form) {
        return CDI.current().select(GetWorkflowEntranceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceDescriptionsResult> getWorkflowEntranceDescriptions(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionsForm form) {
        return CDI.current().select(GetWorkflowEntranceDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditWorkflowEntranceDescriptionResult> editWorkflowEntranceDescription(UserVisitPK userVisitPK, EditWorkflowEntranceDescriptionForm form) {
        return CDI.current().select(EditWorkflowEntranceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowEntranceDescription(UserVisitPK userVisitPK, DeleteWorkflowEntranceDescriptionForm form) {
        return CDI.current().select(DeleteWorkflowEntranceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Steps
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowEntranceStep(UserVisitPK userVisitPK, CreateWorkflowEntranceStepForm form) {
        return CDI.current().select(CreateWorkflowEntranceStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceStepResult> getWorkflowEntranceStep(UserVisitPK userVisitPK, GetWorkflowEntranceStepForm form) {
        return CDI.current().select(GetWorkflowEntranceStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceStepsResult> getWorkflowEntranceSteps(UserVisitPK userVisitPK, GetWorkflowEntranceStepsForm form) {
        return CDI.current().select(GetWorkflowEntranceStepsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowEntranceStep(UserVisitPK userVisitPK, DeleteWorkflowEntranceStepForm form) {
        return CDI.current().select(DeleteWorkflowEntranceStepCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowEntranceSelector(UserVisitPK userVisitPK, CreateWorkflowEntranceSelectorForm form) {
        return CDI.current().select(CreateWorkflowEntranceSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceSelectorResult> getWorkflowEntranceSelector(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorForm form) {
        return CDI.current().select(GetWorkflowEntranceSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceSelectorsResult> getWorkflowEntranceSelectors(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorsForm form) {
        return CDI.current().select(GetWorkflowEntranceSelectorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowEntranceSelector(UserVisitPK userVisitPK, DeleteWorkflowEntranceSelectorForm form) {
        return CDI.current().select(DeleteWorkflowEntranceSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Party Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowEntrancePartyType(UserVisitPK userVisitPK, CreateWorkflowEntrancePartyTypeForm form) {
        return CDI.current().select(CreateWorkflowEntrancePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntrancePartyTypeResult> getWorkflowEntrancePartyType(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypeForm form) {
        return CDI.current().select(GetWorkflowEntrancePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntrancePartyTypesResult> getWorkflowEntrancePartyTypes(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypesForm form) {
        return CDI.current().select(GetWorkflowEntrancePartyTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowEntrancePartyType(UserVisitPK userVisitPK, DeleteWorkflowEntrancePartyTypeForm form) {
        return CDI.current().select(DeleteWorkflowEntrancePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Security Roles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, CreateWorkflowEntranceSecurityRoleForm form) {
        return CDI.current().select(CreateWorkflowEntranceSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceSecurityRoleResult> getWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRoleForm form) {
        return CDI.current().select(GetWorkflowEntranceSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowEntranceSecurityRolesResult> getWorkflowEntranceSecurityRoles(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRolesForm form) {
        return CDI.current().select(GetWorkflowEntranceSecurityRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowEntranceSecurityRoleForm form) {
        return CDI.current().select(DeleteWorkflowEntranceSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Selector Kinds
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createWorkflowSelectorKind(UserVisitPK userVisitPK, CreateWorkflowSelectorKindForm form) {
        return CDI.current().select(CreateWorkflowSelectorKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowSelectorKindResult> getWorkflowSelectorKind(UserVisitPK userVisitPK, GetWorkflowSelectorKindForm form) {
        return CDI.current().select(GetWorkflowSelectorKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetWorkflowSelectorKindsResult> getWorkflowSelectorKinds(UserVisitPK userVisitPK, GetWorkflowSelectorKindsForm form) {
        return CDI.current().select(GetWorkflowSelectorKindsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<?> deleteWorkflowSelectorKind(UserVisitPK userVisitPK, DeleteWorkflowSelectorKindForm form) {
        return CDI.current().select(DeleteWorkflowSelectorKindCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Statuses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetWorkflowEntityStatusesResult> getWorkflowEntityStatuses(UserVisitPK userVisitPK, GetWorkflowEntityStatusesForm form) {
        return CDI.current().select(GetWorkflowEntityStatusesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Triggers
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> processWorkflowTriggers(UserVisitPK userVisitPK) {
        return CDI.current().select(ProcessWorkflowTriggersCommand.class).get().run(userVisitPK);
    }
    
}
