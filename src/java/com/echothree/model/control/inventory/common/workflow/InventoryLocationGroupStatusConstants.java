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

package com.echothree.model.control.inventory.common.workflow;

public interface InventoryLocationGroupStatusConstants {
    
    String Workflow_INVENTORY_LOCATION_GROUP_STATUS = "INVENTORY_LOCATION_GROUP_STATUS";
    
    String WorkflowStep_ACTIVE         = "ACTIVE";
    String WorkflowStep_INVENTORY_PREP = "INVENTORY_PREP";
    String WorkflowStep_INVENTORY      = "INVENTORY";
    
    String WorkflowEntrance_NEW_INVENTORY_LOCATION_GROUP = "NEW_INVENTORY_LOCATION_GROUP";
    
    String WorkflowDestination_ACTIVE_TO_INVENTORY_PREP    = "ACTIVE_TO_INVENTORY_PREP";
    String WorkflowDestination_INVENTORY_PREP_TO_ACTIVE    = "INVENTORY_PREP_TO_ACTIVE";
    String WorkflowDestination_INVENTORY_PREP_TO_INVENTORY = "INVENTORY_PREP_TO_INVENTORY";
    String WorkflowDestination_INVENTORY_TO_ACTIVE         = "INVENTORY_TO_ACTIVE";
    
}
