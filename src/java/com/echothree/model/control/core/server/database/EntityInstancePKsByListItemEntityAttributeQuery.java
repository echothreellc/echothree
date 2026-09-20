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

package com.echothree.model.control.core.server.database;

import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEM_ATTRIBUTES_ENTITY_LIST_ITEM_FK;
import static com.echothree.model.jooq.server.tables.core.EntityListItemAttributes.EntityListItemAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityListItemDetails.EntityListItemDetails;
import static com.echothree.model.jooq.server.tables.core.EntityListItems.EntityListItems;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityInstancePKsByListItemEntityAttributeQuery
        extends BaseEntityInstancePKQuery<EntityInstancePKResult> {

    @Override
    public List<EntityInstancePKResult> execute(final EntityAttribute entityAttribute) {
        var query = getDslContext()
                .select(EntityListItemAttributes.ENTITY_INSTANCE.as("EntityInstancePK"))
                .from(EntityListItems)
                .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .join(EntityListItemAttributes).onKey(ENTITY_LIST_ITEM_ATTRIBUTES_ENTITY_LIST_ITEM_FK)
                .where(EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME));

        return super.execute(query);
    }
    
}
