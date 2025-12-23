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

public interface CustomerStatusConstants {
    
    String Workflow_CUSTOMER_STATUS = "CUSTOMER_STATUS";

    String WorkflowStep_POPULATION = "POPULATION";
    String WorkflowStep_PROSPECT = "PROSPECT";
    String WorkflowStep_VISITOR = "VISITOR";
    String WorkflowStep_SHOPPER = "SHOPPER";
    String WorkflowStep_CUSTOMER = "CUSTOMER";
    String WorkflowStep_INACTIVE = "INACTIVE";
    String WorkflowStep_EX_CUSTOMER= "EX_CUSTOMER";

    String WorkflowEntrance_NEW_POPULATION = "NEW_POPULATION";
    String WorkflowEntrance_NEW_PROSPECT = "NEW_PROSPECT";
    String WorkflowEntrance_NEW_VISITOR = "NEW_VISITOR";
    String WorkflowEntrance_NEW_SHOPPER = "NEW_SHOPPER";

    String WorkflowDestination_POPULATION_TO_PROSPECT = "POPULATION_TO_PROSPECT";
    String WorkflowDestination_PROSPECT_TO_VISITOR = "PROSPECT_TO_VISITOR";
    String WorkflowDestination_VISITOR_TO_SHOPPER = "VISITOR_TO_SHOPPER";
    String WorkflowDestination_SHOPPER_TO_CUSTOMER = "SHOPPER_TO_CUSTOMER";
    String WorkflowDestination_CUSTOMER_TO_INACTIVE = "CUSTOMER_TO_INACTIVE";
    String WorkflowDestination_CUSTOMER_TO_EX_CUSTOMER = "CUSTOMER_TO_EX_CUSTOMER";
    String WorkflowDestination_INACTIVE_TO_EX_CUSTOMER = "INACTIVE_TO_EX_CUSTOMER";

}