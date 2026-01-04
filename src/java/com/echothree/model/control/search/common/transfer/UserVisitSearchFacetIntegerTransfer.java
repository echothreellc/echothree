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

package com.echothree.model.control.search.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityIntegerRangeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UserVisitSearchFacetIntegerTransfer
        extends BaseTransfer {
    
    private EntityIntegerRangeTransfer entityIntegerRange;
    private Long count;
    
    /** Creates a new instance of UserVisitSearchFacetListItemTransfer */
    public UserVisitSearchFacetIntegerTransfer(EntityIntegerRangeTransfer entityIntegerRange, Long count) {
        this.entityIntegerRange = entityIntegerRange;
        this.count = count;
    }

    /**
     * Returns the entityIntegerRange.
     * @return the entityIntegerRange
     */
    public EntityIntegerRangeTransfer getEntityIntegerRange() {
        return entityIntegerRange;
    }

    /**
     * Sets the entityIntegerRange.
     * @param entityIntegerRange the entityIntegerRange to set
     */
    public void setEntityIntegerRange(EntityIntegerRangeTransfer entityIntegerRange) {
        this.entityIntegerRange = entityIntegerRange;
    }

    /**
     * Returns the count.
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sets the count.
     * @param count the count to set
     */
    public void setCount(Long count) {
        this.count = count;
    }

}
