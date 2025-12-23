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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface WorkflowService
        extends WorkflowForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Types
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowStepType(UserVisitPK userVisitPK, CreateWorkflowStepTypeForm form);

    CommandResult getWorkflowStepType(UserVisitPK userVisitPK, GetWorkflowStepTypeForm form);

    CommandResult getWorkflowStepTypes(UserVisitPK userVisitPK, GetWorkflowStepTypesForm form);

    CommandResult getWorkflowStepTypeChoices(UserVisitPK userVisitPK, GetWorkflowStepTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowStepTypeDescription(UserVisitPK userVisitPK, CreateWorkflowStepTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflows
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflow(UserVisitPK userVisitPK, CreateWorkflowForm form);
    
    CommandResult getWorkflow(UserVisitPK userVisitPK, GetWorkflowForm form);
    
    CommandResult getWorkflows(UserVisitPK userVisitPK, GetWorkflowsForm form);

    CommandResult getWorkflowChoices(UserVisitPK userVisitPK, GetWorkflowChoicesForm form);
    
    CommandResult editWorkflow(UserVisitPK userVisitPK, EditWorkflowForm form);
    
    CommandResult deleteWorkflow(UserVisitPK userVisitPK, DeleteWorkflowForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowDescription(UserVisitPK userVisitPK, CreateWorkflowDescriptionForm form);
    
    CommandResult getWorkflowDescription(UserVisitPK userVisitPK, GetWorkflowDescriptionForm form);
    
    CommandResult getWorkflowDescriptions(UserVisitPK userVisitPK, GetWorkflowDescriptionsForm form);
    
    CommandResult editWorkflowDescription(UserVisitPK userVisitPK, EditWorkflowDescriptionForm form);
    
    CommandResult deleteWorkflowDescription(UserVisitPK userVisitPK, DeleteWorkflowDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Steps
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowStep(UserVisitPK userVisitPK, CreateWorkflowStepForm form);
    
    CommandResult getWorkflowStep(UserVisitPK userVisitPK, GetWorkflowStepForm form);
    
    CommandResult getWorkflowSteps(UserVisitPK userVisitPK, GetWorkflowStepsForm form);
    
    CommandResult getWorkflowStepChoices(UserVisitPK userVisitPK, GetWorkflowStepChoicesForm form);
    
    CommandResult editWorkflowStep(UserVisitPK userVisitPK, EditWorkflowStepForm form);
    
    CommandResult setDefaultWorkflowStep(UserVisitPK userVisitPK, SetDefaultWorkflowStepForm form);
    
    CommandResult deleteWorkflowStep(UserVisitPK userVisitPK, DeleteWorkflowStepForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Description
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowStepDescription(UserVisitPK userVisitPK, CreateWorkflowStepDescriptionForm form);
    
    CommandResult getWorkflowStepDescription(UserVisitPK userVisitPK, GetWorkflowStepDescriptionForm form);
    
    CommandResult getWorkflowStepDescriptions(UserVisitPK userVisitPK, GetWorkflowStepDescriptionsForm form);
    
    CommandResult editWorkflowStepDescription(UserVisitPK userVisitPK, EditWorkflowStepDescriptionForm form);
    
    CommandResult deleteWorkflowStepDescription(UserVisitPK userVisitPK, DeleteWorkflowStepDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destinations
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowDestination(UserVisitPK userVisitPK, CreateWorkflowDestinationForm form);
    
    CommandResult getWorkflowDestination(UserVisitPK userVisitPK, GetWorkflowDestinationForm form);
    
    CommandResult getWorkflowDestinations(UserVisitPK userVisitPK, GetWorkflowDestinationsForm form);
    
    CommandResult editWorkflowDestination(UserVisitPK userVisitPK, EditWorkflowDestinationForm form);
    
    CommandResult setDefaultWorkflowDestination(UserVisitPK userVisitPK, SetDefaultWorkflowDestinationForm form);
    
    CommandResult deleteWorkflowDestination(UserVisitPK userVisitPK, DeleteWorkflowDestinationForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowDestinationDescription(UserVisitPK userVisitPK, CreateWorkflowDestinationDescriptionForm form);
    
    CommandResult getWorkflowDestinationDescription(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionForm form);
    
    CommandResult getWorkflowDestinationDescriptions(UserVisitPK userVisitPK, GetWorkflowDestinationDescriptionsForm form);
    
    CommandResult editWorkflowDestinationDescription(UserVisitPK userVisitPK, EditWorkflowDestinationDescriptionForm form);
    
    CommandResult deleteWorkflowDestinationDescription(UserVisitPK userVisitPK, DeleteWorkflowDestinationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Steps
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowDestinationStep(UserVisitPK userVisitPK, CreateWorkflowDestinationStepForm form);
    
    CommandResult getWorkflowDestinationStep(UserVisitPK userVisitPK, GetWorkflowDestinationStepForm form);
    
    CommandResult getWorkflowDestinationSteps(UserVisitPK userVisitPK, GetWorkflowDestinationStepsForm form);
    
    CommandResult deleteWorkflowDestinationStep(UserVisitPK userVisitPK, DeleteWorkflowDestinationStepForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Selectors
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowDestinationSelector(UserVisitPK userVisitPK, CreateWorkflowDestinationSelectorForm form);
    
    CommandResult getWorkflowDestinationSelector(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorForm form);
    
    CommandResult getWorkflowDestinationSelectors(UserVisitPK userVisitPK, GetWorkflowDestinationSelectorsForm form);
    
    CommandResult deleteWorkflowDestinationSelector(UserVisitPK userVisitPK, DeleteWorkflowDestinationSelectorForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Party Types
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowDestinationPartyType(UserVisitPK userVisitPK, CreateWorkflowDestinationPartyTypeForm form);
    
    CommandResult getWorkflowDestinationPartyType(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypeForm form);
    
    CommandResult getWorkflowDestinationPartyTypes(UserVisitPK userVisitPK, GetWorkflowDestinationPartyTypesForm form);
    
    CommandResult deleteWorkflowDestinationPartyType(UserVisitPK userVisitPK, DeleteWorkflowDestinationPartyTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Security Roles
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, CreateWorkflowDestinationSecurityRoleForm form);
    
    CommandResult getWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRoleForm form);
    
    CommandResult getWorkflowDestinationSecurityRoles(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRolesForm form);
    
    CommandResult deleteWorkflowDestinationSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowDestinationSecurityRoleForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Types
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowEntityType(UserVisitPK userVisitPK, CreateWorkflowEntityTypeForm form);

    CommandResult getWorkflowEntityType(UserVisitPK userVisitPK, GetWorkflowEntityTypeForm form);

    CommandResult getWorkflowEntityTypes(UserVisitPK userVisitPK, GetWorkflowEntityTypesForm form);
    
    CommandResult deleteWorkflowEntityType(UserVisitPK userVisitPK, DeleteWorkflowEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Workflow Entrances
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowEntrance(UserVisitPK userVisitPK, CreateWorkflowEntranceForm form);
    
    CommandResult getWorkflowEntrance(UserVisitPK userVisitPK, GetWorkflowEntranceForm form);
    
    CommandResult getWorkflowEntrances(UserVisitPK userVisitPK, GetWorkflowEntrancesForm form);
    
    CommandResult editWorkflowEntrance(UserVisitPK userVisitPK, EditWorkflowEntranceForm form);
    
    CommandResult setDefaultWorkflowEntrance(UserVisitPK userVisitPK, SetDefaultWorkflowEntranceForm form);
    
    CommandResult deleteWorkflowEntrance(UserVisitPK userVisitPK, DeleteWorkflowEntranceForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowEntranceDescription(UserVisitPK userVisitPK, CreateWorkflowEntranceDescriptionForm form);
    
    CommandResult getWorkflowEntranceDescription(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionForm form);
    
    CommandResult getWorkflowEntranceDescriptions(UserVisitPK userVisitPK, GetWorkflowEntranceDescriptionsForm form);
    
    CommandResult editWorkflowEntranceDescription(UserVisitPK userVisitPK, EditWorkflowEntranceDescriptionForm form);
    
    CommandResult deleteWorkflowEntranceDescription(UserVisitPK userVisitPK, DeleteWorkflowEntranceDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Steps
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowEntranceStep(UserVisitPK userVisitPK, CreateWorkflowEntranceStepForm form);
    
    CommandResult getWorkflowEntranceStep(UserVisitPK userVisitPK, GetWorkflowEntranceStepForm form);
    
    CommandResult getWorkflowEntranceSteps(UserVisitPK userVisitPK, GetWorkflowEntranceStepsForm form);
    
    CommandResult deleteWorkflowEntranceStep(UserVisitPK userVisitPK, DeleteWorkflowEntranceStepForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Selectors
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowEntranceSelector(UserVisitPK userVisitPK, CreateWorkflowEntranceSelectorForm form);
    
    CommandResult getWorkflowEntranceSelector(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorForm form);
    
    CommandResult getWorkflowEntranceSelectors(UserVisitPK userVisitPK, GetWorkflowEntranceSelectorsForm form);
    
    CommandResult deleteWorkflowEntranceSelector(UserVisitPK userVisitPK, DeleteWorkflowEntranceSelectorForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Party Types
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowEntrancePartyType(UserVisitPK userVisitPK, CreateWorkflowEntrancePartyTypeForm form);
    
    CommandResult getWorkflowEntrancePartyType(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypeForm form);
    
    CommandResult getWorkflowEntrancePartyTypes(UserVisitPK userVisitPK, GetWorkflowEntrancePartyTypesForm form);
    
    CommandResult deleteWorkflowEntrancePartyType(UserVisitPK userVisitPK, DeleteWorkflowEntrancePartyTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Security Roles
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, CreateWorkflowEntranceSecurityRoleForm form);
    
    CommandResult getWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRoleForm form);
    
    CommandResult getWorkflowEntranceSecurityRoles(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRolesForm form);
    
    CommandResult deleteWorkflowEntranceSecurityRole(UserVisitPK userVisitPK, DeleteWorkflowEntranceSecurityRoleForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Selector Kinds
    // --------------------------------------------------------------------------------
    
    CommandResult createWorkflowSelectorKind(UserVisitPK userVisitPK, CreateWorkflowSelectorKindForm form);
    
    CommandResult getWorkflowSelectorKind(UserVisitPK userVisitPK, GetWorkflowSelectorKindForm form);
    
    CommandResult getWorkflowSelectorKinds(UserVisitPK userVisitPK, GetWorkflowSelectorKindsForm form);
    
    CommandResult deleteWorkflowSelectorKind(UserVisitPK userVisitPK, DeleteWorkflowSelectorKindForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Statuses
    // --------------------------------------------------------------------------------
    
    CommandResult getWorkflowEntityStatuses(UserVisitPK userVisitPK, GetWorkflowEntityStatusesForm form);
    
    // --------------------------------------------------------------------------------
    //   Workflow Triggers
    // --------------------------------------------------------------------------------
    
    CommandResult processWorkflowTriggers(UserVisitPK userVisitPK);
    
}
