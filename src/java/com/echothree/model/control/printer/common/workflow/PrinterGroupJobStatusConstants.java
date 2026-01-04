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

package com.echothree.model.control.printer.common.workflow;

public interface PrinterGroupJobStatusConstants {
    
    String Workflow_PRINTER_GROUP_JOB_STATUS = "PRINTER_GROUP_JOB_STATUS";
    
    String WorkflowStep_QUEUED  = "QUEUED";
    String WorkflowStep_PRINTED = "PRINTED";
    String WorkflowStep_ERRORED = "ERRORED";
    String WorkflowStep_DELETED = "DELETED";
    
    String WorkflowEntrance_NEW_PRINTER_GROUP_JOB = "NEW_PRINTER_GROUP_JOB";
    
    String WorkflowDestination_QUEUED_TO_PRINTED = "QUEUED_TO_PRINTED";
    String WorkflowDestination_QUEUED_TO_ERRORED = "QUEUED_TO_ERRORED";
    String WorkflowDestination_QUEUED_TO_DELETED = "QUEUED_TO_DELETED";
    String WorkflowDestination_PRINTED_TO_QUEUED = "PRINTED_TO_QUEUED";
    String WorkflowDestination_PRINTED_TO_DELETED = "PRINTED_TO_DELETED";
    String WorkflowDestination_ERRORED_TO_QUEUED = "ERRORED_TO_QUEUED";
    String WorkflowDestination_ERRORED_TO_DELETED = "ERRORED_TO_DELETED";
    String WorkflowDestination_DELETED_TO_QUEUED = "DELETED_TO_QUEUED";
    
}
