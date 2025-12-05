// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.item.server.database;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.data.item.common.pk.ItemAliasTypePK;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class ItemEntityInstancesByItemAliasTypeQuery
        extends BaseDatabaseQuery<ItemEntityInstanceResult> {

    public ItemEntityInstancesByItemAliasTypeQuery() {
        super("""
                SELECT eni_entityinstanceid AS EntityInstancePK
                FROM itemaliastypes, itemaliastypedetails, itemaliases, componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances
                WHERE iat_itemaliastypeid = ? AND iat_activedetailid = iatdt_itemaliastypedetailid
                AND iat_itemaliastypeid = itmal_iat_itemaliastypeid AND itmal_thrutime = ?
                AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ?
                AND ent_activedetailid = entdt_entitytypedetailid AND cvnd_componentvendorid = entdt_cvnd_componentvendorid AND entdt_entitytypename = ?
                AND eni_ent_entitytypeid = ent_entitytypeid AND eni_entityuniqueid = itmal_itm_itemid
                """, EntityPermission.READ_ONLY);
    }
    
    public List<ItemEntityInstanceResult> execute(final ItemAliasTypePK itemAliasTypePK) {
        return super.execute(itemAliasTypePK, Session.MAX_TIME, ComponentVendors.ECHO_THREE.name(), EntityTypes.Item.name());
    }

}
