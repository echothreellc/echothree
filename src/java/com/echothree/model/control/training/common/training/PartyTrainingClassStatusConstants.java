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

package com.echothree.model.control.training.common.training;

public interface PartyTrainingClassStatusConstants {
    
    String Workflow_PARTY_TRAINING_CLASS_STATUS = "PARTY_TRAINING_CLASS_STATUS";
    
    String WorkflowStep_ASSIGNED = "ASSIGNED";
    String WorkflowStep_TRAINING = "TRAINING";
    String WorkflowStep_PASSED = "PASSED";
    String WorkflowStep_FAILED = "FAILED";
    String WorkflowStep_EXPIRED = "EXPIRED";
    
    String WorkflowEntrance_NEW_ASSIGNED = "NEW_ASSIGNED";
    String WorkflowEntrance_NEW_PASSED = "NEW_PASSED";
    
    String WorkflowDestination_ASSIGNED_TO_TRAINING = "ASSIGNED_TO_TRAINING";
    String WorkflowDestination_TRAINING_TO_PASSED = "TRAINING_TO_PASSED";
    String WorkflowDestination_TRAINING_TO_FAILED = "TRAINING_TO_FAILED";
    String WorkflowDestination_PASSED_TO_EXPIRED = "PASSED_TO_EXPIRED";
    String WorkflowDestination_FAILED_TO_TRAINING = "FAILED_TO_TRAINING";
    
}
