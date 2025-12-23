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

public interface WorkAssignmentStatusConstants {
    
    String Workflow_WORK_ASSIGNMENT_STATUS = "WORK_ASSIGNMENT_STATUS";
    
    String WorkflowStep_ASSIGNED   = "ASSIGNED";
    String WorkflowStep_ACCEPTED   = "ACCEPTED";
    String WorkflowStep_COMPLETED  = "COMPLETED";
    String WorkflowStep_REASSIGN   = "REASSIGN";
    String WorkflowStep_SUSPENDED  = "SUSPENDED";
    
    String WorkflowEntrance_NEW_ASSIGNED = "NEW_ASSIGNED";
    String WorkflowEntrance_NEW_ACCEPTED = "NEW_ACCEPTED";
    
    String WorkflowDestination_ASSIGNED_TO_ACCEPTED  = "ASSIGNED_TO_ACCEPTED";
    String WorkflowDestination_ASSIGNED_TO_REASSIGN  = "ASSIGNED_TO_REASSIGN";
    String WorkflowDestination_ACCEPTED_TO_COMPLETED = "ACCEPTED_TO_COMPLETED";
    String WorkflowDestination_ACCEPTED_TO_REASSIGN  = "ACCEPTED_TO_REASSIGN";
    String WorkflowDestination_ACCEPTED_TO_SUSPENDED = "ACCEPTED_TO_SUSPENDED";
    String WorkflowDestination_REASSIGN_TO_ASSIGNED  = "REASSIGN_TO_ASSIGNED";
    String WorkflowDestination_REASSIGN_TO_ACCEPTED  = "REASSIGN_TO_ACCEPTED";
    String WorkflowDestination_SUSPENDED_TO_ACCEPTED = "SUSPENDED_TO_ACCEPTED";
    
}
