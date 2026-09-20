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

package com.echothree.model.control.item.server.database;

import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_ALIASES_ITEM_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEM_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.item.ItemAliasTypeDetails.ItemAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.item.ItemAliasTypes.ItemAliasTypes;
import static com.echothree.model.jooq.server.tables.item.ItemAliases.ItemAliases;
import com.echothree.model.data.item.common.pk.ItemAliasTypePK;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class ItemEntityInstancesByItemAliasTypeQuery
        extends BaseDatabaseQuery<ItemEntityInstanceResult> {

    public ItemEntityInstancesByItemAliasTypeQuery() {
        super(EntityPermission.READ_ONLY);
    }
    
    public List<ItemEntityInstanceResult> execute(final ItemAliasTypePK itemAliasTypePK) {
        var query = getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE.as("EntityInstancePK"))
                .from(ItemAliasTypes)
                .join(ItemAliasTypeDetails).onKey(ITEM_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .join(ItemAliases).onKey(ITEM_ALIASES_ITEM_ALIAS_TYPE_FK)
                .join(EntityInstances).on(EntityInstances.ENTITY_UNIQUE.eq(ItemAliases.ITEM.cast(Long.class)))
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .where(ItemAliasTypes.ITEM_ALIAS_TYPE.eq(itemAliasTypePK),
                        ItemAliases.THRU_TIME.eq(Session.MAX_TIME),
                        ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                        EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.Item.name()));

        return super.execute(query);
    }

}
