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

package com.echothree.model.control.filter.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class FilterEntranceStepTransfer
        extends BaseTransfer {
    
    private FilterTransfer filter;
    private FilterStepTransfer filterStep;
    
    /** Creates a new instance of FilterEntranceStepTransfer */
    public FilterEntranceStepTransfer(FilterTransfer filter, FilterStepTransfer filterStep) {
        this.filter = filter;
        this.filterStep = filterStep;
    }
    
    public FilterTransfer getFilter() {
        return filter;
    }
    
    public void setFilter(FilterTransfer filter) {
        this.filter = filter;
    }
    
    public FilterStepTransfer getFilterStep() {
        return filterStep;
    }
    
    public void setFilterStep(FilterStepTransfer filterStep) {
        this.filterStep = filterStep;
    }
    
}
