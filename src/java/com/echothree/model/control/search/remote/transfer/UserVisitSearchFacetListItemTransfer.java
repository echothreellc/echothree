// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.search.remote.transfer;

import com.echothree.model.control.core.remote.transfer.EntityListItemTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class UserVisitSearchFacetListItemTransfer
        extends BaseTransfer {
    
    private EntityListItemTransfer entityListItem;
    private Integer count;
    
    /** Creates a new instance of UserVisitSearchFacetListItemTransfer */
    public UserVisitSearchFacetListItemTransfer(EntityListItemTransfer entityListItem, Integer count) {
        this.entityListItem = entityListItem;
        this.count = count;
    }

    /**
     * @return the entityListItem
     */
    public EntityListItemTransfer getEntityListItem() {
        return entityListItem;
    }

    /**
     * @param entityListItem the entityListItem to set
     */
    public void setEntityListItem(EntityListItemTransfer entityListItem) {
        this.entityListItem = entityListItem;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}
