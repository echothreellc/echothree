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

package com.echothree.model.control.accounting.common.workflow;

public interface FiscalPeriodStatusConstants {
    
    String Workflow_FISCAL_PERIOD_STATUS = "FISCAL_PERIOD_STATUS";
    
    String WorkflowStep_OPEN = "OPEN";
    String WorkflowStep_CLOSED = "CLOSED";
    String WorkflowStep_FINAL = "FINAL";
    
    String WorkflowEntrance_NEW_FISCAL_PERIOD = "NEW_FISCAL_PERIOD";
    
    String WorkflowDestination_OPEN_TO_CLOSED = "OPEN_TO_CLOSED";
    String WorkflowDestination_CLOSED_TO_OPEN = "CLOSED_TO_OPEN";
    String WorkflowDestination_CLOSED_TO_FINAL = "CLOSED_TO_FINAL";
    
}
