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

package com.echothree.model.control.workrequirement.common.workflow;

public interface WorkRequirementStatusConstants {
    
    String Workflow_WORK_REQUIREMENT_STATUS = "WORK_REQUIREMENT_STATUS";
    
    String WorkflowStep_ASSIGNED = "ASSIGNED";
    String WorkflowStep_COMPLETED = "COMPLETED";
    String WorkflowStep_UNASSIGNED = "UNASSIGNED";
    
    String WorkflowEntrance_NEW_ASSIGNED = "NEW_ASSIGNED";
    String WorkflowEntrance_NEW_UNASSIGNED = "NEW_UNASSIGNED";
    
    String WorkflowDestination_UNASSIGNED_TO_ASSIGNED = "UNASSIGNED_TO_ASSIGNED";
    String WorkflowDestination_ASSIGNED_TO_UNASSIGNED = "ASSIGNED_TO_UNASSIGNED";
    String WorkflowDestination_ASSIGNED_TO_COMPLETED = "ASSIGNED_TO_COMPLETED";
    
}
