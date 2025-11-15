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

package com.echothree.model.control.period.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;

public class PeriodTransferCaches
        extends BaseTransferCaches {
    
    protected PeriodKindTransferCache periodKindTransferCache;
    protected PeriodKindDescriptionTransferCache periodKindDescriptionTransferCache;
    protected PeriodTypeTransferCache periodTypeTransferCache;
    protected PeriodTypeDescriptionTransferCache periodTypeDescriptionTransferCache;
    protected PeriodTransferCache periodTransferCache;
    protected PeriodDescriptionTransferCache periodDescriptionTransferCache;
    
    /** Creates a new instance of PeriodTransferCaches */
    public PeriodTransferCaches() {
        super();
    }
    
    public PeriodKindTransferCache getPeriodKindTransferCache() {
        if(periodKindTransferCache == null)
            periodKindTransferCache = new PeriodKindTransferCache();
        
        return periodKindTransferCache;
    }

    public PeriodKindDescriptionTransferCache getPeriodKindDescriptionTransferCache() {
        if(periodKindDescriptionTransferCache == null)
            periodKindDescriptionTransferCache = new PeriodKindDescriptionTransferCache();
        
        return periodKindDescriptionTransferCache;
    }
    
    public PeriodTypeTransferCache getPeriodTypeTransferCache() {
        if(periodTypeTransferCache == null)
            periodTypeTransferCache = new PeriodTypeTransferCache();
        
        return periodTypeTransferCache;
    }
    
    public PeriodTypeDescriptionTransferCache getPeriodTypeDescriptionTransferCache() {
        if(periodTypeDescriptionTransferCache == null)
            periodTypeDescriptionTransferCache = new PeriodTypeDescriptionTransferCache();
        
        return periodTypeDescriptionTransferCache;
    }
    
    public PeriodTransferCache getPeriodTransferCache() {
        if(periodTransferCache == null)
            periodTransferCache = new PeriodTransferCache();
        
        return periodTransferCache;
    }
    
    public PeriodDescriptionTransferCache getPeriodDescriptionTransferCache() {
        if(periodDescriptionTransferCache == null)
            periodDescriptionTransferCache = new PeriodDescriptionTransferCache();
        
        return periodDescriptionTransferCache;
    }
    
}
