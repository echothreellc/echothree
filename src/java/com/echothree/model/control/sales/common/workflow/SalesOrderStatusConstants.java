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

package com.echothree.model.control.sales.common.workflow;

public interface SalesOrderStatusConstants {
    
    String Workflow_SALES_ORDER_STATUS = "SALES_ORDER_STATUS";
    
    String WorkflowStep_ENTRY_ALLOCATED = "ENTRY_ALLOCATED";
    String WorkflowStep_ENTRY_UNALLOCATED = "ENTRY_UNALLOCATED";
    String WorkflowStep_BATCH_AUDIT = "BATCH_AUDIT";
    String WorkflowStep_ENTRY_COMPLETE = "ENTRY_COMPLETE";
    
    String WorkflowEntrance_ENTRY_ALLOCATED = "ENTRY_ALLOCATED";
    String WorkflowEntrance_ENTRY_UNALLOCATED = "ENTRY_UNALLOCATED";
    
    String WorkflowDestination_ENTRY_ALLOCATED_TO_UNALLOCATED = "ENTRY_ALLOCATED_TO_UNALLOCATED";
    String WorkflowDestination_ENTRY_UNALLOCATED_TO_ALLOCATED = "ENTRY_UNALLOCATED_TO_ALLOCATED";
    String WorkflowDestination_ENTRY_ALLOCATED_TO_BATCH_AUDIT = "ENTRY_ALLOCATED_TO_BATCH_AUDIT";
    String WorkflowDestination_ENTRY_ALLOCATED_TO_COMPLETE = "ENTRY_ALLOCATED_TO_COMPLETE";
    String WorkflowDestination_BATCH_AUDIT_TO_COMPLETE = "BATCH_AUDIT_TO_COMPLETE";
    
}
