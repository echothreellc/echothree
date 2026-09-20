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

import static com.echothree.model.jooq.server.tables.core.EntityEntityAttributes.EntityEntityAttributes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityInstancePKsByEntityEntityAttributeQuery
        extends BaseEntityInstancePKQuery<EntityInstancePKResult> {

    @Override
    public List<EntityInstancePKResult> execute(final EntityAttribute entityAttribute) {
        var query = getDslContext()
                .select(EntityEntityAttributes.ENTITY_INSTANCE.as("EntityInstancePK"))
                .from(EntityEntityAttributes)
                .where(EntityEntityAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityEntityAttributes.THRU_TIME.eq(Session.MAX_TIME));

        return super.execute(query);
    }
    
}
