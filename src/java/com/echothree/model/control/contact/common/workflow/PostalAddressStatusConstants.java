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

package com.echothree.model.control.contact.common.workflow;

public interface PostalAddressStatusConstants {
    
    String Workflow_POSTAL_ADDRESS_STATUS = "POSTAL_ADDRESS_STATUS";
    
    String WorkflowStep_VALID   = "VALID";
    String WorkflowStep_NIXIE_A = "NIXIE_A";
    
    String WorkflowEntrance_NEW_POSTAL_ADDRESS = "NEW_POSTAL_ADDRESS";
    
    String WorkflowDestination_VALID_TO_NIXIE_A = "VALID_TO_NIXIE_A";
    String WorkflowDestination_NIXIE_A_TO_VALID = "NIXIE_A_TO_VALID";
    
}
