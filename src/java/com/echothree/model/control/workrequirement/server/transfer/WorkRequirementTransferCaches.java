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
import javax.enterprise.inject.spi.CDI;

public class WorkRequirementTransferCaches
        extends BaseTransferCaches {
    
    protected WorkRequirementTypeDescriptionTransferCache workRequirementTypeDescriptionTransferCache;
    protected WorkRequirementTypeTransferCache workRequirementTypeTransferCache;
    protected WorkRequirementScopeTransferCache workRequirementScopeTransferCache;
    protected WorkRequirementTransferCache workRequirementTransferCache;
    protected WorkAssignmentTransferCache workAssignmentTransferCache;
    protected WorkTimeTransferCache workTimeTransferCache;
    
    /** Creates a new instance of WorkRequirementTransferCaches */
    public WorkRequirementTransferCaches() {
        super();
    }
    
    public WorkRequirementTypeDescriptionTransferCache getWorkRequirementTypeDescriptionTransferCache() {
        if(workRequirementTypeDescriptionTransferCache == null)
            workRequirementTypeDescriptionTransferCache = CDI.current().select(WorkRequirementTypeDescriptionTransferCache.class).get();
        
        return workRequirementTypeDescriptionTransferCache;
    }
    
    public WorkRequirementTypeTransferCache getWorkRequirementTypeTransferCache() {
        if(workRequirementTypeTransferCache == null)
            workRequirementTypeTransferCache = CDI.current().select(WorkRequirementTypeTransferCache.class).get();
        
        return workRequirementTypeTransferCache;
    }
    
    public WorkRequirementScopeTransferCache getWorkRequirementScopeTransferCache() {
        if(workRequirementScopeTransferCache == null)
            workRequirementScopeTransferCache = CDI.current().select(WorkRequirementScopeTransferCache.class).get();
        
        return workRequirementScopeTransferCache;
    }
    
    public WorkRequirementTransferCache getWorkRequirementTransferCache() {
        if(workRequirementTransferCache == null)
            workRequirementTransferCache = CDI.current().select(WorkRequirementTransferCache.class).get();
        
        return workRequirementTransferCache;
    }
    
    public WorkAssignmentTransferCache getWorkAssignmentTransferCache() {
        if(workAssignmentTransferCache == null)
            workAssignmentTransferCache = CDI.current().select(WorkAssignmentTransferCache.class).get();
        
        return workAssignmentTransferCache;
    }
    
    public WorkTimeTransferCache getWorkTimeTransferCache() {
        if(workTimeTransferCache == null)
            workTimeTransferCache = CDI.current().select(WorkTimeTransferCache.class).get();
        
        return workTimeTransferCache;
    }
    
}
