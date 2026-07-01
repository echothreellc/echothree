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

package com.echothree.control.user.workflow.common;

import com.echothree.control.user.workflow.common.form.*;
import com.echothree.control.user.workflow.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface WorkflowService
        extends WorkflowForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowStepType(UserVisitPK userVisitPK, CreateWorkflowStepTypeForm form);

    CommandResult<GetWorkflowStepTypeResult> getWorkflowStepType(UserVisitPK userVisitPK, GetWorkflowStepTypeForm form);

    CommandResult<GetWorkflowStepTypesResult> getWorkflowStepTypes(UserVisitPK userVisitPK, GetWorkflowStepTypesForm form);

    CommandResult<GetWorkflowStepTypeChoicesResult> getWorkflowStepTypeChoices(UserVisitPK userVisitPK, GetWorkflowStepTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowStepTypeDescription(UserVisitPK userVisitPK, CreateWorkflowStepTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflows
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateWorkflowResult> createWorkflow(UserVisitPK userVisitPK, CreateWorkflowForm form);
    
    CommandResult<GetWorkflowResult> getWorkflow(UserVisitPK userVisitPK, GetWorkflowForm form);
    
    CommandResult<GetWorkflowsResult> getWorkflows(UserVisitPK userVisitPK, GetWorkflowsForm form);

    CommandResult<GetWorkflowChoicesResult> getWorkflowChoices(UserVisitPK userVisitPK, GetWorkflowChoicesForm form);
    
    CommandResult<EditWorkflowResult> editWorkflow(UserVisitPK userVisitPK, EditWorkflowForm form);
    
    CommandResult<VoidResult> deleteWorkflow(UserVisitPK userVisitPK, DeleteWorkflowForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowDescription(UserVisitPK userVisitPK, CreateWorkflowDescriptionForm form);
    
    CommandResult<GetWorkflowDescriptionResult> getWorkflowDescription(UserVisitPK userVisitPK, GetWorkflowDescriptionForm form);
    
    CommandResult<GetWorkflowDescriptionsResult> getWorkflowDescriptions(UserVisitPK userVisitPK, GetWorkflowDescriptionsForm form);
    
    CommandResult<EditWorkflowDescriptionResult> editWorkflowDescription(UserVisitPK userVisitPK, EditWorkflowDescriptionForm form);
    
    CommandResult<VoidResult> deleteWorkflowDescription(UserVisitPK userVisitPK, DeleteWorkflowDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Steps
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateWorkflowStepResult> createWorkflowStep(UserVisitPK userVisitPK, CreateWorkflowStepForm form);
    
    CommandResult<GetWorkflowStepResult> getWorkflowStep(UserVisitPK userVisitPK, GetWorkflowStepForm form);
    
    CommandResult<GetWorkflowStepsResult> getWorkflowSteps(UserVisitPK userVisitPK, GetWorkflowStepsForm form);
    
    CommandResult<GetWorkflowStepChoicesResult> getWorkflowStepChoices(UserVisitPK userVisitPK, GetWorkflowStepChoicesForm form);
    
    CommandResult<EditWorkflowStepResult> editWorkflowStep(UserVisitPK userVisitPK, EditWorkflowStepForm form);
    
    CommandResult<VoidResult> setDefaultWorkflowStep(UserVisitPK userVisitPK, SetDefaultWorkflowStepForm form);
    
    CommandResult<VoidResult> deleteWorkflowStep(UserVisitPK userVisitPK, DeleteWorkflowStepForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Description
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowStepDescription(UserVisitPK userVisitPK, CreateWorkflowStepDescriptionForm form);
    
    CommandResult<GetWorkflowStepDescriptionResult> getWorkflowStepDescription(UserVisitPK userVisitPK, GetWorkflowStepDescriptionForm form);
    
    CommandResult<GetWorkflowStepDescriptionsResult> getWorkflowStepDescriptions(UserVisitPK userVisitPK, GetWorkflowStepDescriptionsForm form);
    
    CommandResult<EditWorkflowStepDescriptionResult> editWorkflowStepDescription(UserVisitPK userVisitPK, EditWorkflowStepDescriptionForm form);
    
    CommandResult<VoidResult> deleteWorkflowStepDescription(UserVisitPK userVisitPK, DeleteWorkflowStepDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destinations
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateWorkflowDestinationResult> createWorkflowDestination(UserVisitPK userVisitPK, CreateWorkflowDestinationForm form);
    
    CommandResult<GetWorkflowDestinationResult> getWorkflowDestination(UserVisitPK userVisitPK, GetWorkflowDestinationForm form);
    
    CommandResult<GetWorkflowDestinationsResult> getWorkflowDestinations(UserVisitPK userVisitPK, GetWorkflowDestinationsForm form);
    
    CommandResult<EditWorkflowDestinationResult> editWorkflowDestination(UserVisitPK userVisitPK, EditWorkflowDestinationForm form);
    
    CommandResult<VoidResult> setDefaultWorkflowDestination(UserVisitPK userVisitPK, SetDefaultWorkflowDestinationForm form);
    
    CommandResult<VoidResult> deleteWorkflowDestination(UserVisitPK userVisitPK, DeleteWorkflowDestinationForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowDestinationDescription(UserVisitPK userVisitPK, CreateWorkflowDestinationDescriptionForm form);
    
    CommandResult<GetWorkflowDestinationDescriptionResult> getWorkflowDestinationDescription(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionForm form);
    
    CommandResult<GetWorkflowDestinationDescriptionsResult> getWorkflowDestinationDescriptions(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionsForm form);
    
    CommandResult<EditWorkflowDestinationDescriptionResult> editWorkflowDestinationDescription(UserVisitPK userVisitPK, EditWorkflowDestinationDescriptionForm form);
    
    CommandResult<VoidResult> deleteWorkflowDestinationDescription(UserVisitPK userVisitPK, DeleteWorkflowDestinationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Steps
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowDestinationStep(UserVisitPK userVisitPK, CreateWorkflowDestinationStepForm form);
    
    CommandResult<GetWorkflowDestinationStepResult> getWorkflowDestinationStep(UserVisitPK userVisitPK, GetWorkflowDestinationStepForm form);
    
    CommandResult<GetWorkflowDestinationStepsResult> getWorkflowDestinationSteps(UserVisitPK userVisitPK, GetWorkflowDestinationStepsForm form);
    
    CommandResult<VoidResult> deleteWorkflowDestinationStep(UserVisitPK userVisitPK, DeleteWorkflowDestinationStepForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Selectors
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowDestinationSelector(UserVisitPK userVisitPK, CreateWorkflowDestinationSelectorForm form);
    
    CommandResult<GetWorkflowDestinationSelectorResult> getWorkflowDestinationSelector(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorForm form);
    
    CommandResult<GetWorkflowDestinationSelectorsResult> getWorkflowDestinationSelectors(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorsForm form);
    
    CommandResult<VoidResult> deleteWorkflowDestinationSelector(UserVisitPK userVisitPK, DeleteWorkflowDestinationSelectorForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Party Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowDestinationPartyType(UserVisitPK userVisitPK, CreateWorkflowDestinationPartyTypeForm form);
    
    CommandResult<GetWorkflowDestinationPartyTypeResult> getWorkflowDestinationPartyType(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypeForm form);
    
    CommandResult<GetWorkflowDestinationPartyTypesResult> getWorkflowDestinationPartyTypes(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypesForm form);
    
    CommandResult<VoidResult> deleteWorkflowDestinationPartyType(UserVisitPK userVisitPK, DeleteWorkflowDestinationPartyTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Security Roles
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, CreateWorkflowDestinationSecurityRoleForm form);
    
    CommandResult<GetWorkflowDestinationSecurityRoleResult> getWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRoleForm form);
    
    CommandResult<GetWorkflowDestinationSecurityRolesResult> getWorkflowDestinationSecurityRoles(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRolesForm form);
    
    CommandResult<VoidResult> deleteWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowDestinationSecurityRoleForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowEntityType(UserVisitPK userVisitPK, CreateWorkflowEntityTypeForm form);

    CommandResult<GetWorkflowEntityTypeResult> getWorkflowEntityType(UserVisitPK userVisitPK, GetWorkflowEntityTypeForm form);

    CommandResult<GetWorkflowEntityTypesResult> getWorkflowEntityTypes(UserVisitPK userVisitPK, GetWorkflowEntityTypesForm form);
    
    CommandResult<VoidResult> deleteWorkflowEntityType(UserVisitPK userVisitPK, DeleteWorkflowEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Workflow Entrances
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateWorkflowEntranceResult> createWorkflowEntrance(UserVisitPK userVisitPK, CreateWorkflowEntranceForm form);
    
    CommandResult<GetWorkflowEntranceResult> getWorkflowEntrance(UserVisitPK userVisitPK, GetWorkflowEntranceForm form);
    
    CommandResult<GetWorkflowEntrancesResult> getWorkflowEntrances(UserVisitPK userVisitPK, GetWorkflowEntrancesForm form);
    
    CommandResult<EditWorkflowEntranceResult> editWorkflowEntrance(UserVisitPK userVisitPK, EditWorkflowEntranceForm form);
    
    CommandResult<VoidResult> setDefaultWorkflowEntrance(UserVisitPK userVisitPK, SetDefaultWorkflowEntranceForm form);
    
    CommandResult<VoidResult> deleteWorkflowEntrance(UserVisitPK userVisitPK, DeleteWorkflowEntranceForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowEntranceDescription(UserVisitPK userVisitPK, CreateWorkflowEntranceDescriptionForm form);
    
    CommandResult<GetWorkflowEntranceDescriptionResult> getWorkflowEntranceDescription(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionForm form);
    
    CommandResult<GetWorkflowEntranceDescriptionsResult> getWorkflowEntranceDescriptions(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionsForm form);
    
    CommandResult<EditWorkflowEntranceDescriptionResult> editWorkflowEntranceDescription(UserVisitPK userVisitPK, EditWorkflowEntranceDescriptionForm form);
    
    CommandResult<VoidResult> deleteWorkflowEntranceDescription(UserVisitPK userVisitPK, DeleteWorkflowEntranceDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Steps
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowEntranceStep(UserVisitPK userVisitPK, CreateWorkflowEntranceStepForm form);
    
    CommandResult<GetWorkflowEntranceStepResult> getWorkflowEntranceStep(UserVisitPK userVisitPK, GetWorkflowEntranceStepForm form);
    
    CommandResult<GetWorkflowEntranceStepsResult> getWorkflowEntranceSteps(UserVisitPK userVisitPK, GetWorkflowEntranceStepsForm form);
    
    CommandResult<VoidResult> deleteWorkflowEntranceStep(UserVisitPK userVisitPK, DeleteWorkflowEntranceStepForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Selectors
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowEntranceSelector(UserVisitPK userVisitPK, CreateWorkflowEntranceSelectorForm form);
    
    CommandResult<GetWorkflowEntranceSelectorResult> getWorkflowEntranceSelector(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorForm form);
    
    CommandResult<GetWorkflowEntranceSelectorsResult> getWorkflowEntranceSelectors(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorsForm form);
    
    CommandResult<VoidResult> deleteWorkflowEntranceSelector(UserVisitPK userVisitPK, DeleteWorkflowEntranceSelectorForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Party Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowEntrancePartyType(UserVisitPK userVisitPK, CreateWorkflowEntrancePartyTypeForm form);
    
    CommandResult<GetWorkflowEntrancePartyTypeResult> getWorkflowEntrancePartyType(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypeForm form);
    
    CommandResult<GetWorkflowEntrancePartyTypesResult> getWorkflowEntrancePartyTypes(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypesForm form);
    
    CommandResult<VoidResult> deleteWorkflowEntrancePartyType(UserVisitPK userVisitPK, DeleteWorkflowEntrancePartyTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Security Roles
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, CreateWorkflowEntranceSecurityRoleForm form);
    
    CommandResult<GetWorkflowEntranceSecurityRoleResult> getWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRoleForm form);
    
    CommandResult<GetWorkflowEntranceSecurityRolesResult> getWorkflowEntranceSecurityRoles(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRolesForm form);
    
    CommandResult<VoidResult> deleteWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowEntranceSecurityRoleForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Selector Kinds
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkflowSelectorKind(UserVisitPK userVisitPK, CreateWorkflowSelectorKindForm form);
    
    CommandResult<GetWorkflowSelectorKindResult> getWorkflowSelectorKind(UserVisitPK userVisitPK, GetWorkflowSelectorKindForm form);
    
    CommandResult<GetWorkflowSelectorKindsResult> getWorkflowSelectorKinds(UserVisitPK userVisitPK, GetWorkflowSelectorKindsForm form);
    
    CommandResult<VoidResult> deleteWorkflowSelectorKind(UserVisitPK userVisitPK, DeleteWorkflowSelectorKindForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Statuses
    // --------------------------------------------------------------------------------
    
    CommandResult<GetWorkflowEntityStatusesResult> getWorkflowEntityStatuses(UserVisitPK userVisitPK, GetWorkflowEntityStatusesForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Triggers
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> processWorkflowTriggers(UserVisitPK userVisitPK);
    
}
