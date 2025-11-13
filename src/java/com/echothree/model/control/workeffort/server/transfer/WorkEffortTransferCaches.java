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

package com.echothree.model.control.workeffort.server.transfer;

import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class WorkEffortTransferCaches
        extends BaseTransferCaches {
    
    protected WorkEffortControl workEffortControl;
    
    protected WorkEffortTransferCache workEffortTransferCache;
    protected WorkEffortTypeTransferCache workEffortTypeTransferCache;
    protected WorkEffortTypeDescriptionTransferCache workEffortTypeDescriptionTransferCache;
    protected WorkEffortScopeTransferCache workEffortScopeTransferCache;
    protected WorkEffortScopeDescriptionTransferCache workEffortScopeDescriptionTransferCache;
    
    /** Creates a new instance of WorkEffortTransferCaches */
    public WorkEffortTransferCaches(WorkEffortControl workEffortControl) {
        super();
        
        this.workEffortControl = workEffortControl;
    }
    
    public WorkEffortTransferCache getWorkEffortTransferCache() {
        if(workEffortTransferCache == null)
            workEffortTransferCache = new WorkEffortTransferCache(workEffortControl);
        
        return workEffortTransferCache;
    }
    
    public WorkEffortTypeTransferCache getWorkEffortTypeTransferCache() {
        if(workEffortTypeTransferCache == null)
            workEffortTypeTransferCache = new WorkEffortTypeTransferCache(workEffortControl);
        
        return workEffortTypeTransferCache;
    }
    
    public WorkEffortTypeDescriptionTransferCache getWorkEffortTypeDescriptionTransferCache() {
        if(workEffortTypeDescriptionTransferCache == null)
            workEffortTypeDescriptionTransferCache = new WorkEffortTypeDescriptionTransferCache(workEffortControl);
        
        return workEffortTypeDescriptionTransferCache;
    }
    
    public WorkEffortScopeTransferCache getWorkEffortScopeTransferCache() {
        if(workEffortScopeTransferCache == null)
            workEffortScopeTransferCache = new WorkEffortScopeTransferCache(workEffortControl);
        
        return workEffortScopeTransferCache;
    }
    
    public WorkEffortScopeDescriptionTransferCache getWorkEffortScopeDescriptionTransferCache() {
        if(workEffortScopeDescriptionTransferCache == null)
            workEffortScopeDescriptionTransferCache = new WorkEffortScopeDescriptionTransferCache(workEffortControl);
        
        return workEffortScopeDescriptionTransferCache;
    }
    
}
