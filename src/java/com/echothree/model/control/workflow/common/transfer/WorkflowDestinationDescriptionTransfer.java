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

package com.echothree.model.control.workflow.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowDestinationDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private WorkflowDestinationTransfer workflowDestination;
    private String description;
    
    /** Creates a new instance of WorkflowDestinationDescriptionTransfer */
    public WorkflowDestinationDescriptionTransfer(LanguageTransfer language, WorkflowDestinationTransfer workflowDestination,
            String description) {
        this.language = language;
        this.workflowDestination = workflowDestination;
        this.description = description;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public WorkflowDestinationTransfer getWorkflowDestination() {
        return workflowDestination;
    }
    
    public void setWorkflowDestination(WorkflowDestinationTransfer workflowDestination) {
        this.workflowDestination = workflowDestination;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
