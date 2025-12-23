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

package com.echothree.model.control.campaign.common.workflow;

public interface CampaignSourceStatusConstants {
    
    String Workflow_CAMPAIGN_SOURCE_STATUS = "CAMPAIGN_SOURCE_STATUS";
    
    String WorkflowStep_ACTIVE = "ACTIVE";
    String WorkflowStep_INACTIVE = "INACTIVE";
    
    String WorkflowEntrance_NEW_ACTIVE = "NEW_ACTIVE";
    
    String WorkflowDestination_ACTIVE_TO_INACTIVE = "ACTIVE_TO_INACTIVE";
    String WorkflowDestination_INACTIVE_INACTIVE_TO_ACTIVE = "INACTIVE_TO_ACTIVE";
    
}
