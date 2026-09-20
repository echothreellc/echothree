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

import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TIMES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTimes.EntityTimes;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;

public class EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery
        extends BaseDatabaseQuery<EntityInstancePKResult> {

    public EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery() {
        super(EntityPermission.READ_ONLY);
    }

    public List<EntityInstancePKResult> execute(final EntityType entityType) {
        var query = getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE.as("EntityInstancePK"))
                .from(EntityInstances)
                .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .where(EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityTimes.DELETED_TIME.isNull());

        return super.execute(query);
    }

}
