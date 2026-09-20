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

import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TIMES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeDetails.EntityAttributeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityAttributes.EntityAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityListItemAttributes.EntityListItemAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityTimes.EntityTimes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityInstancesMissingListItemEntityAttributeQuery
        extends BaseDatabaseQuery<EntityInstanceResult> {

    public EntityInstancesMissingListItemEntityAttributeQuery() {
        super(EntityPermission.READ_ONLY);
    }
    
    public List<EntityInstanceResult> execute(final EntityAttribute entityAttribute) {
        var query = getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE.as("EntityInstance"))
                .from(EntityAttributes)
                .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                .join(EntityInstances).on(EntityAttributeDetails.ENTITY_TYPE.eq(EntityInstances.ENTITY_TYPE))
                .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .where(EntityAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityTimes.DELETED_TIME.isNull())
                .and(EntityInstances.ENTITY_INSTANCE.notIn(getDslContext()
                        .select(EntityListItemAttributes.ENTITY_INSTANCE)
                        .from(EntityListItemAttributes)
                        .where(EntityListItemAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                                EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))));

        return super.execute(query);
    }
    
}
