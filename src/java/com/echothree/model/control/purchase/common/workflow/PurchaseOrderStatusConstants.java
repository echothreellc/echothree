// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.purchase.common.workflow;

public interface PurchaseOrderStatusConstants {
    
    String Workflow_PURCHASE_ORDER_STATUS = "PURCHASE_ORDER_STATUS";
    
    String WorkflowStep_ENTRY = "ENTRY";
    String WorkflowStep_ENTRY_COMPLETE = "ENTRY_COMPLETE";
    String WorkflowStep_RELEASED = "RELEASED";
    String WorkflowStep_RECEIVED = "RECEIVED";
    String WorkflowStep_CHANGE_ORDER = "CHANGE_ORDER";
    String WorkflowStep_CANCELED = "CANCELED";
    String WorkflowStep_CLOSED = "CLOSED";

    String WorkflowEntrance_ENTRY = "ENTRY";
    String WorkflowEntrance_ENTRY_COMPLETE= "ENTRY_COMPLETE";

    String WorkflowDestination_ENTRY_TO_ENTRY_COMPLETE = "ENTRY_TO_ENTRY_COMPLETE";
    String WorkflowDestination_ENTRY_TO_ENTRY_CANCELED = "ENTRY_TO_CANCELED";

}