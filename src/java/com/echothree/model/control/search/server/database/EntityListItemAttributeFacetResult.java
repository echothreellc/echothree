// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.search.server.database;

import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.util.server.persistence.BaseDatabaseResult;

public class EntityListItemAttributeFacetResult
        implements BaseDatabaseResult {
    
    private EntityListItem entityListItem;
    private Integer count;

    /**
     * @return the entityListItem
     */
    public EntityListItem getEntityListItem() {
        return entityListItem;
    }

    /**
     * @param entityListItem the entityListItem to set
     */
    public void setEntityListItem(EntityListItem entityListItem) {
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
