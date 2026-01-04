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

package com.echothree.model.control.workrequirement.common.workflow;

public interface WorkTimeStatusConstants {
    
    String Workflow_WORK_TIME_STATUS = "WORK_TIME_STATUS";
    
    String WorkflowStep_IN_PROGRESS = "IN_PROGRESS";
    String WorkflowStep_COMPLETE = "COMPLETE";
    String WorkflowStep_INCOMPLETE = "INCOMPLETE";
    
    String WorkflowEntrance_NEW_IN_PROGRESS = "NEW_IN_PROGRESS";
    String WorkflowEntrance_NEW_COMPLETE = "NEW_COMPLETE";
    String WorkflowEntrance_NEW_INCOMPLETE = "NEW_INCOMPLETE";
    
    String WorkflowDestination_IN_PROGRESS_TO_COMPLETE = "IN_PROGRESS_TO_COMPLETE";
    String WorkflowDestination_IN_PROGRESS_TO_INCOMPLETE = "IN_PROGRESS_TO_INCOMPLETE";
    
}
