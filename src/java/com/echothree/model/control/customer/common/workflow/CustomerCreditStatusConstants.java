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

package com.echothree.model.control.customer.common.workflow;

public interface CustomerCreditStatusConstants {
    
    String Workflow_CUSTOMER_CREDIT_STATUS = "CUSTOMER_CREDIT_STATUS";
    
    String WorkflowStep_APPROVED         = "APPROVED";
    String WorkflowStep_CUT_OFF          = "CUT_OFF";
    String WorkflowStep_NOT_APPROVED     = "NOT_APPROVED";
    String WorkflowStep_OVER             = "OVER";
    String WorkflowStep_OVER_POTENTIAL   = "OVER_POTENTIAL";
    String WorkflowStep_WAIT_APPROVAL    = "WAIT_APPROVAL";
    String WorkflowStep_WAIT_INFORMATION = "WAIT_INFORMATION";
    
    String WorkflowEntrance_APPROVED         = "APPROVED";
    String WorkflowEntrance_WAITING_APPROVAL = "WAITING_APPROVAL";
    
    String WorkflowDestination_APPROVED_TO_CUT_OFF                = "APPROVED_TO_CUT_OFF";
    String WorkflowDestination_CUT_OFF_TO_WAIT_APPROVAL           = "CUT_OFF_TO_WAIT_APPROVAL";
    String WorkflowDestination_OVER_POTENTIAL_TO_APPROVED         = "OVER_POTENTIAL_TO_APPROVED";
    String WorkflowDestination_OVER_POTENTIAL_TO_WAIT_INFORMATION = "OVER_POTENTIAL_TO_WAIT_INFORMATION";
    String WorkflowDestination_OVER_TO_APPROVED                   = "OVER_TO_APPROVED";
    String WorkflowDestination_OVER_TO_WAIT_INFORMATION           = "OVER_TO_WAIT_INFORMATION";
    String WorkflowDestination_WAIT_APPROVAL_TO_APPROVED          = "WAIT_APPROVAL_TO_APPROVED";
    String WorkflowDestination_WAIT_APPROVAL_TO_NOT_APPROVED      = "WAIT_APPROVAL_TO_NOT_APPROVED";
    String WorkflowDestination_WAIT_APPROVAL_TO_WAIT_INFORMATION  = "WAIT_APPROVAL_TO_WAIT_INFORMATION";
    String WorkflowDestination_WAIT_INFORMATION_TO_APPROVED       = "WAIT_INFORMATION_TO_APPROVED";
    String WorkflowDestination_WAIT_INFORMATION_TO_NOT_APPROVED   = "WAIT_INFORMATION_TO_NOT_APPROVED";
    
}
