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

package com.echothree.model.control.contactlist.common.workflow;

public interface PartyContactListStatusConstants {
    
    String Workflow_PARTY_CONTACT_LIST_STATUS = "PARTY_CONTACT_LIST_STATUS";
    
    String WorkflowStep_AWAITING_VERIFICATION = "AWAITING_VERIFICATION";
    String WorkflowStep_ACTIVE = "ACTIVE";
    String WorkflowStep_SUSPENDED = "SUSPENDED";
    
    String WorkflowEntrance_NEW_AWAITING_VERIFICATION = "NEW_AWAITING_VERIFICATION";
    String WorkflowEntrance_NEW_ACTIVE = "NEW_ACTIVE";
    
    String WorkflowDestination_AWAITING_VERIFICATION = "AWAITING_VERIFICATION";
    String WorkflowDestination_SUSPEND_ACTIVE = "SUSPEND_ACTIVE";
    String WorkflowDestination_RESUME_ACTIVE = "RESUME_ACTIVE";
    
}
