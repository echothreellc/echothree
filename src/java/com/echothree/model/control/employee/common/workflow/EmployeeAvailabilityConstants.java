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

public interface EmployeeAvailabilityConstants {
    
    String Workflow_EMPLOYEE_AVAILABILITY = "EMPLOYEE_AVAILABILITY";
    
    String WorkflowStep_AVAILABLE     = "AVAILABLE";
    String WorkflowStep_UNAVAILABLE   = "UNAVAILABLE";
    String WorkflowStep_BREAK         = "BREAK";
    String WorkflowStep_OUT_OF_OFFICE = "OUT_OF_OFFICE";
    String WorkflowStep_BREAKFAST     = "BREAKFAST";
    String WorkflowStep_LUNCH         = "LUNCH";
    String WorkflowStep_DINNER        = "DINNER";
    
    String WorkflowEntrance_NEW_AVAILABLE = "NEW_AVAILABLE";
    
    String WorkflowDestination_AVAILABLE_TO_UNAVAILABLE   = "AVAILABLE_TO_UNAVAILABLE";
    String WorkflowDestination_UNAVAILABLE_TO_AVAILABLE   = "UNAVAILABLE_TO_AVAILABLE";
    String WorkflowDestination_AVAILABLE_TO_BREAK         = "AVAILABLE_TO_BREAK";
    String WorkflowDestination_BREAK_TO_AVAILABLE         = "BREAK_TO_AVAILABLE";
    String WorkflowDestination_AVAILABLE_TO_OUT_OF_OFFICE = "AVAILABLE_TO_OUT_OF_OFFICE";
    String WorkflowDestination_OUT_OF_OFFICE_TO_AVAILABLE = "OUT_OF_OFFICE_TO_AVAILABLE";
    String WorkflowDestination_AVAILABLE_TO_BREAKFAST     = "AVAILABLE_TO_BREAKFAST";
    String WorkflowDestination_BREAKFAST_TO_AVAILABLE     = "BREAKFAST_TO_AVAILABLE";
    String WorkflowDestination_AVAILABLE_TO_LUNCH         = "AVAILABLE_TO_LUNCH";
    String WorkflowDestination_LUNCH_TO_AVAILABLE         = "LUNCH_TO_AVAILABLE";
    String WorkflowDestination_AVAILABLE_TO_DINNER        = "AVAILABLE_TO_DINNER";
    String WorkflowDestination_DINNER_TO_AVAILABLE        = "DINNER_TO_AVAILABLE";
    
}
