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

package com.echothree.model.control.core.common.workflow;

public interface BaseEncryptionKeyStatusConstants {
    
    String Workflow_BASE_ENCRYPTION_KEY_STATUS = "BASE_ENCRYPTION_KEY_STATUS";
    
    String WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE    = "ACTIVE";
    String WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_INACTIVE  = "INACTIVE";
    String WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_DESTROYED = "DESTROYED";
    
    String WorkflowEntrance_BASE_ENCRYPTION_KEY_STATUS_NEW_ACTIVE = "NEW_ACTIVE";
    
    String WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE    = "ACTIVE_TO_INACTIVE";
    String WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_INACTIVE_TO_DESTROYED = "INACTIVE_TO_DESTROYED";
    
}
