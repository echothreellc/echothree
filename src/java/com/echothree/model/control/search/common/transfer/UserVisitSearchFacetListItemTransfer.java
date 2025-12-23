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

import com.echothree.model.control.core.common.transfer.EntityListItemTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UserVisitSearchFacetListItemTransfer
        extends BaseTransfer {
    
    private EntityListItemTransfer entityListItem;
    private Long count;
    
    /** Creates a new instance of UserVisitSearchFacetListItemTransfer */
    public UserVisitSearchFacetListItemTransfer(EntityListItemTransfer entityListItem, Long count) {
        this.entityListItem = entityListItem;
        this.count = count;
    }

    /**
     * Returns the entityListItem.
     * @return the entityListItem
     */
    public EntityListItemTransfer getEntityListItem() {
        return entityListItem;
    }

    /**
     * Sets the entityListItem.
     * @param entityListItem the entityListItem to set
     */
    public void setEntityListItem(EntityListItemTransfer entityListItem) {
        this.entityListItem = entityListItem;
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
