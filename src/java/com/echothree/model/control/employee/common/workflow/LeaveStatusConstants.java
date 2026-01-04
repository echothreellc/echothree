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

package com.echothree.model.control.employee.common.workflow;

public interface LeaveStatusConstants {

    String Workflow_LEAVE_STATUS = "LEAVE_STATUS";

    String WorkflowStep_SUBMITTED = "SUBMITTED";
    String WorkflowStep_APPROVED = "APPROVED";
    String WorkflowStep_DENIED = "DENIED";
    String WorkflowStep_REVISED = "REVISED";
    String WorkflowStep_TAKEN = "TAKEN";
    String WorkflowStep_NOT_TAKEN = "NOT_TAKEN";

    String WorkflowEntrance_NEW_SUBMITTED = "NEW_SUBMITTED";
    String WorkflowEntrance_NEW_APPROVED = "NEW_APPROVED";

    String WorkflowDestination_SUBMITTED_TO_APPROVED = "SUBMITTED_TO_APPROVED";
    String WorkflowDestination_SUBMITTED_TO_DENIED = "SUBMITTED_TO_DENIED";
    String WorkflowDestination_APPROVED_TO_REVISED = "APPROVED_TO_REVISED";
    String WorkflowDestination_APPROVED_TO_TAKEN = "APPROVED_TO_TAKEN";
    String WorkflowDestination_APPROVED_TO_NOT_TAKEN = "APPROVED_TO_NOT_TAKEN";
    String WorkflowDestination_DENIED_TO_REVISED = "DENIED_TO_REVISED";
    String WorkflowDestination_REVISED_TO_APPROVED = "REVISED_TO_APPROVED";
    String WorkflowDestination_REVISED_TO_DENIED = "REVISED_TO_DENIED";

}
