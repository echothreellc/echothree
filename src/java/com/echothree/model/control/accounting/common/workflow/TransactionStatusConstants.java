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

package com.echothree.model.control.accounting.common.workflow;

public interface TransactionStatusConstants {
    
    String Workflow_TRANSACTION_STATUS = "TRANSACTION_STATUS";
    
    String WorkflowStep_TRANSACTION_STATUS_ENTRY   = "ENTRY";
    String WorkflowStep_TRANSACTION_STATUS_PENDING = "PENDING";
    String WorkflowStep_TRANSACTION_STATUS_REJECTED = "REJECTED";
    String WorkflowStep_TRANSACTION_STATUS_POSTED = "POSTED";

    String WorkflowEntrance_TRANSACTION_STATUS_NEW_ENTRY = "NEW_ENTRY";
    String WorkflowEntrance_TRANSACTION_STATUS_NEW_POSTED = "NEW_POSTED";

    String WorkflowDestination_TRANSACTION_STATUS_ENTRY_TO_PENDING = "ENTRY_TO_PENDING";
    String WorkflowDestination_TRANSACTION_STATUS_ENTRY_TO_POSTED = "ENTRY_TO_POSTED";
    String WorkflowDestination_TRANSACTION_STATUS_PENDING_TO_REJECTED = "PENDING_TO_REJECTED";
    String WorkflowDestination_TRANSACTION_STATUS_PENDING_TO_POSTED = "PENDING_TO_POSTED";

}
