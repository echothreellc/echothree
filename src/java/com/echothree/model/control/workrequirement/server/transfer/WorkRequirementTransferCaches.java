// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class WorkRequirementTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    WorkRequirementTypeDescriptionTransferCache workRequirementTypeDescriptionTransferCache;
    
    @Inject
    WorkRequirementTypeTransferCache workRequirementTypeTransferCache;
    
    @Inject
    WorkRequirementScopeTransferCache workRequirementScopeTransferCache;
    
    @Inject
    WorkRequirementTransferCache workRequirementTransferCache;
    
    @Inject
    WorkAssignmentTransferCache workAssignmentTransferCache;
    
    @Inject
    WorkTimeTransferCache workTimeTransferCache;

    /** Creates a new instance of WorkRequirementTransferCaches */
    protected WorkRequirementTransferCaches() {
        super();
    }
    
    public WorkRequirementTypeDescriptionTransferCache getWorkRequirementTypeDescriptionTransferCache() {
        return workRequirementTypeDescriptionTransferCache;
    }
    
    public WorkRequirementTypeTransferCache getWorkRequirementTypeTransferCache() {
        return workRequirementTypeTransferCache;
    }
    
    public WorkRequirementScopeTransferCache getWorkRequirementScopeTransferCache() {
        return workRequirementScopeTransferCache;
    }
    
    public WorkRequirementTransferCache getWorkRequirementTransferCache() {
        return workRequirementTransferCache;
    }
    
    public WorkAssignmentTransferCache getWorkAssignmentTransferCache() {
        return workAssignmentTransferCache;
    }
    
    public WorkTimeTransferCache getWorkTimeTransferCache() {
        return workTimeTransferCache;
    }
    
}
