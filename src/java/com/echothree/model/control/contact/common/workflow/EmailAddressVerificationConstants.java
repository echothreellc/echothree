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

public interface EmailAddressVerificationConstants {
    
    String Workflow_EMAIL_ADDRESS_VERIFICATION = "EMAIL_ADDRESS_VERIFICATION";
    
    String WorkflowStep_NOT_VERIFIED = "NOT_VERIFIED";
    String WorkflowStep_VERIFIED     = "VERIFIED";
    
    String WorkflowEntrance_NEW_EMAIL_ADDRESS = "NEW_EMAIL_ADDRESS";
    
    String WorkflowDestination_NOT_VERIFIED_TO_VERIFIED = "NOT_VERIFIED_TO_VERIFIED";
    String WorkflowDestination_VERIFIED_TO_NOT_VERIFIED = "VERIFIED_TO_NOT_VERIFIED";
    
}
