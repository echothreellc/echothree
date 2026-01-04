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

package com.echothree.model.control.item.common.workflow;

public interface ItemStatusConstants {
    
    String Workflow_ITEM_STATUS = "ITEM_STATUS";
    
    String WorkflowStep_ITEM_STATUS_ACTIVE = "ACTIVE";
    String WorkflowStep_ITEM_STATUS_DISCONTINUED = "DISCONTINUED";
    String WorkflowStep_ITEM_STATUS_CANCEL_IF_NOT_IN_STOCK = "CANCEL_IF_NOT_IN_STOCK";
    
    String WorkflowEntrance_ITEM_STATUS_NEW_ACTIVE = "NEW_ACTIVE";
    String WorkflowEntrance_ITEM_STATUS_NEW_DISCONTINUED = "NEW_DISCONTINUED";
    String WorkflowEntrance_ITEM_STATUS_NEW_CANCEL_IF_NOT_IN_STOCK = "NEW_CANCEL_IF_NOT_IN_STOCK";
    
    String WorkflowDestination_ITEM_STATUS_DISCONTINUED_TO_ACTIVE = "DISCONTINUED_TO_ACTIVE";
    String WorkflowDestination_ITEM_STATUS_DISCONTINUED_TO_CANCEL_IF_NOT_IN_STOCK = "DISCONTINUED_TO_CANCEL_IF_NOT_IN_STOCK";
    String WorkflowDestination_ITEM_STATUS_CANCEL_IF_NOT_IN_STOCK_CANCEL_IF_NOT_IN_STOCK_TO_ACTIVE = "CANCEL_IF_NOT_IN_STOCK_TO_ACTIVE";
    String WorkflowDestination_ITEM_STATUS_CANCEL_IF_NOT_IN_STOCK_CANCEL_IF_NOT_IN_STOCK_TO_DISCONTINUED = "CANCEL_IF_NOT_IN_STOCK_TO_DISCONTINUED";
    String WorkflowDestination_ITEM_STATUS_ACTIVE_TO_DISCONTINUED = "ACTIVE_TO_DISCONTINUED";
    String WorkflowDestination_ITEM_STATUS_ACTIVE_TO_CANCEL_IF_NOT_IN_STOCK = "ACTIVE_TO_CANCEL_IF_NOT_IN_STOCK";
    
}
