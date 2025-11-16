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
import javax.enterprise.inject.spi.CDI;

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
            periodKindTransferCache = CDI.current().select(PeriodKindTransferCache.class).get();
        
        return periodKindTransferCache;
    }

    public PeriodKindDescriptionTransferCache getPeriodKindDescriptionTransferCache() {
        if(periodKindDescriptionTransferCache == null)
            periodKindDescriptionTransferCache = CDI.current().select(PeriodKindDescriptionTransferCache.class).get();
        
        return periodKindDescriptionTransferCache;
    }
    
    public PeriodTypeTransferCache getPeriodTypeTransferCache() {
        if(periodTypeTransferCache == null)
            periodTypeTransferCache = CDI.current().select(PeriodTypeTransferCache.class).get();
        
        return periodTypeTransferCache;
    }
    
    public PeriodTypeDescriptionTransferCache getPeriodTypeDescriptionTransferCache() {
        if(periodTypeDescriptionTransferCache == null)
            periodTypeDescriptionTransferCache = CDI.current().select(PeriodTypeDescriptionTransferCache.class).get();
        
        return periodTypeDescriptionTransferCache;
    }
    
    public PeriodTransferCache getPeriodTransferCache() {
        if(periodTransferCache == null)
            periodTransferCache = CDI.current().select(PeriodTransferCache.class).get();
        
        return periodTransferCache;
    }
    
    public PeriodDescriptionTransferCache getPeriodDescriptionTransferCache() {
        if(periodDescriptionTransferCache == null)
            periodDescriptionTransferCache = CDI.current().select(PeriodDescriptionTransferCache.class).get();
        
        return periodDescriptionTransferCache;
    }
    
}
