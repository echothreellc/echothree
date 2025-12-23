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

public interface PrinterStatusConstants {
    
    String Workflow_PRINTER_STATUS = "PRINTER_STATUS";
    
    String WorkflowStep_ACCEPTING_JOBS = "ACCEPTING_JOBS";
    String WorkflowStep_PAUSED         = "PAUSED";
    
    String WorkflowEntrance_NEW_PRINTER = "NEW_PRINTER";
    
    String WorkflowDestination_ACCEPTING_JOBS_TO_PAUSED = "ACCEPTING_JOBS_TO_PAUSED";
    String WorkflowDestination_PAUSED_TO_ACCEPTING_JOBS = "PAUSED_TO_ACCEPTING_JOBS";
    
}
