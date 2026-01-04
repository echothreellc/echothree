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

import com.echothree.model.control.core.common.transfer.EntityLongRangeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UserVisitSearchFacetLongTransfer
        extends BaseTransfer {
    
    private EntityLongRangeTransfer entityLongRange;
    private Long count;
    
    /** Creates a new instance of UserVisitSearchFacetListItemTransfer */
    public UserVisitSearchFacetLongTransfer(EntityLongRangeTransfer entityLongRange, Long count) {
        this.entityLongRange = entityLongRange;
        this.count = count;
    }

    /**
     * Returns the entityLongRange.
     * @return the entityLongRange
     */
    public EntityLongRangeTransfer getEntityLongRange() {
        return entityLongRange;
    }

    /**
     * Sets the entityLongRange.
     * @param entityLongRange the entityLongRange to set
     */
    public void setEntityLongRange(EntityLongRangeTransfer entityLongRange) {
        this.entityLongRange = entityLongRange;
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
