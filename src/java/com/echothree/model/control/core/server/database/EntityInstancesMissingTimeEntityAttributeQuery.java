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

import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityInstancesMissingTimeEntityAttributeQuery
        extends BaseDatabaseQuery<EntityInstanceResult> {

    public EntityInstancesMissingTimeEntityAttributeQuery() {
        super("""
                SELECT eni_entityinstanceid AS EntityInstance
                FROM entityattributes
                JOIN entityattributedetails ON ena_activedetailid = enadt_entityattributedetailid
                JOIN entityinstances ON enadt_ent_entitytypeid = eni_ent_entitytypeid
                JOIN entitytimes ON eni_entityinstanceid = etim_eni_entityinstanceid
                WHERE ena_entityattributeid = ? AND etim_deletedtime IS NULL
                AND eni_entityinstanceid NOT IN
                    (SELECT enta_eni_entityinstanceid
                     FROM entitytimeattributes
                     WHERE enta_ena_entityattributeid = ? AND enta_thrutime = ?)
                """, EntityPermission.READ_ONLY);
    }
    
    public List<EntityInstanceResult> execute(final EntityAttribute entityAttribute) {
        return super.execute(entityAttribute, entityAttribute, Session.MAX_TIME);
    }
    
}
