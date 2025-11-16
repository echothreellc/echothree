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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PeriodTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    PeriodKindTransferCache periodKindTransferCache;
    
    @Inject
    PeriodKindDescriptionTransferCache periodKindDescriptionTransferCache;
    
    @Inject
    PeriodTypeTransferCache periodTypeTransferCache;
    
    @Inject
    PeriodTypeDescriptionTransferCache periodTypeDescriptionTransferCache;
    
    @Inject
    PeriodTransferCache periodTransferCache;
    
    @Inject
    PeriodDescriptionTransferCache periodDescriptionTransferCache;

    /** Creates a new instance of PeriodTransferCaches */
    protected PeriodTransferCaches() {
        super();
    }
    
    public PeriodKindTransferCache getPeriodKindTransferCache() {
        return periodKindTransferCache;
    }

    public PeriodKindDescriptionTransferCache getPeriodKindDescriptionTransferCache() {
        return periodKindDescriptionTransferCache;
    }
    
    public PeriodTypeTransferCache getPeriodTypeTransferCache() {
        return periodTypeTransferCache;
    }
    
    public PeriodTypeDescriptionTransferCache getPeriodTypeDescriptionTransferCache() {
        return periodTypeDescriptionTransferCache;
    }
    
    public PeriodTransferCache getPeriodTransferCache() {
        return periodTransferCache;
    }
    
    public PeriodDescriptionTransferCache getPeriodDescriptionTransferCache() {
        return periodDescriptionTransferCache;
    }
    
}
