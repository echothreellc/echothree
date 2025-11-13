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

import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class WorkRequirementTransferCaches
        extends BaseTransferCaches {
    
    protected WorkRequirementControl workRequirementControl;
    
    protected WorkRequirementTypeDescriptionTransferCache workRequirementTypeDescriptionTransferCache;
    protected WorkRequirementTypeTransferCache workRequirementTypeTransferCache;
    protected WorkRequirementScopeTransferCache workRequirementScopeTransferCache;
    protected WorkRequirementTransferCache workRequirementTransferCache;
    protected WorkAssignmentTransferCache workAssignmentTransferCache;
    protected WorkTimeTransferCache workTimeTransferCache;
    
    /** Creates a new instance of WorkRequirementTransferCaches */
    public WorkRequirementTransferCaches(WorkRequirementControl workRequirementControl) {
        super();
        
        this.workRequirementControl = workRequirementControl;
    }
    
    public WorkRequirementTypeDescriptionTransferCache getWorkRequirementTypeDescriptionTransferCache() {
        if(workRequirementTypeDescriptionTransferCache == null)
            workRequirementTypeDescriptionTransferCache = new WorkRequirementTypeDescriptionTransferCache(workRequirementControl);
        
        return workRequirementTypeDescriptionTransferCache;
    }
    
    public WorkRequirementTypeTransferCache getWorkRequirementTypeTransferCache() {
        if(workRequirementTypeTransferCache == null)
            workRequirementTypeTransferCache = new WorkRequirementTypeTransferCache(workRequirementControl);
        
        return workRequirementTypeTransferCache;
    }
    
    public WorkRequirementScopeTransferCache getWorkRequirementScopeTransferCache() {
        if(workRequirementScopeTransferCache == null)
            workRequirementScopeTransferCache = new WorkRequirementScopeTransferCache(workRequirementControl);
        
        return workRequirementScopeTransferCache;
    }
    
    public WorkRequirementTransferCache getWorkRequirementTransferCache() {
        if(workRequirementTransferCache == null)
            workRequirementTransferCache = new WorkRequirementTransferCache(workRequirementControl);
        
        return workRequirementTransferCache;
    }
    
    public WorkAssignmentTransferCache getWorkAssignmentTransferCache() {
        if(workAssignmentTransferCache == null)
            workAssignmentTransferCache = new WorkAssignmentTransferCache(workRequirementControl);
        
        return workAssignmentTransferCache;
    }
    
    public WorkTimeTransferCache getWorkTimeTransferCache() {
        if(workTimeTransferCache == null)
            workTimeTransferCache = new WorkTimeTransferCache(workRequirementControl);
        
        return workTimeTransferCache;
    }
    
}
