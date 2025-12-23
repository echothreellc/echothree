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

import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;

public class EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery
        extends BaseDatabaseQuery<EntityInstancePKResult> {

    public EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery() {
        super("SELECT eni_entityinstanceid AS EntityInstancePK "
                        + "FROM entityinstances, entitytimes "
                        + "WHERE eni_ent_entitytypeid = ? AND eni_entityinstanceid = etim_eni_entityinstanceid "
                        + "AND etim_deletedtime IS NULL",
                EntityPermission.READ_ONLY);
    }

    public List<EntityInstancePKResult> execute(final EntityType entityType) {
        return super.execute(entityType);
    }

}
