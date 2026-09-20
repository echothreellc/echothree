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

package com.echothree.model.control.party.server.control;


import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.transfer.PartyEntityTypeTransfer;
import com.echothree.model.control.party.server.transfer.PartyEntityTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyEntityType;
import com.echothree.model.data.party.server.factory.PartyEntityTypeFactory;
import com.echothree.model.data.party.server.value.PartyEntityTypeValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_ENTITY_TYPES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.PartyEntityTypes.PartyEntityTypes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class PartyEntityTypeControl
        extends BaseModelControl {

    /** Creates a new instance of PartyEntityTypeControl */
    protected PartyEntityTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Entity Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyEntityTypeFactory partyEntityTypeFactory;

    @Inject
    PartyEntityTypeTransferCache partyEntityTypeTransferCache;

    public PartyEntityType createPartyEntityType(Party party, EntityType entityType, Boolean confirmDelete, BasePK createdBy) {
        var partyEntityType = partyEntityTypeFactory.create(party, entityType, confirmDelete, session.getStartTime(), Session.MAX_TIME);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyEntityType;
    }

    public long countPartyEntityTypesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyEntityTypes)
                .where(PartyEntityTypes.PARTY.eq(party.getPrimaryKey()),
                        PartyEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyEntityTypesByEntityType(final EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyEntityTypes)
                .where(PartyEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        PartyEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyEntityType getPartyEntityType(Party party, EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyEntityTypes.fields())
                .from(PartyEntityTypes)
                .where(PartyEntityTypes.PARTY.eq(party.getPrimaryKey()),
                        PartyEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        PartyEntityTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyEntityTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyEntityType getPartyEntityType(Party party, EntityType entityType) {
        return getPartyEntityType(party, entityType, EntityPermission.READ_ONLY);
    }

    public PartyEntityType getPartyEntityTypeForUpdate(Party party, EntityType entityType) {
        return getPartyEntityType(party, entityType, EntityPermission.READ_WRITE);
    }

    public PartyEntityTypeValue getPartyEntityTypeValue(PartyEntityType partyEntityType) {
        return partyEntityType == null ? null : partyEntityType.getPartyEntityTypeValue().clone();
    }

    public PartyEntityTypeValue getPartyEntityTypeValueForUpdate(Party party, EntityType entityType) {
        return getPartyEntityTypeValue(getPartyEntityTypeForUpdate(party, entityType));
    }

    private List<PartyEntityType> getPartyEntityTypesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyEntityTypes.fields())
                    .from(PartyEntityTypes)
                    .join(EntityTypes).onKey(PARTY_ENTITY_TYPES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(PartyEntityTypes.PARTY.eq(party.getPrimaryKey()),
                            PartyEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, ComponentVendorDetails.COMPONENT_VENDOR_NAME),
                    PartyEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyEntityTypes.fields())
                    .from(PartyEntityTypes)
                    .where(PartyEntityTypes.PARTY.eq(party.getPrimaryKey()),
                            PartyEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyEntityType> getPartyEntityTypesByParty(Party party) {
        return getPartyEntityTypesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyEntityType> getPartyEntityTypesByPartyForUpdate(Party party) {
        return getPartyEntityTypesByParty(party, EntityPermission.READ_WRITE);
    }

    public PartyEntityTypeTransfer getPartyEntityTypeTransfer(UserVisit userVisit, PartyEntityType partyEntityType) {
        return partyEntityTypeTransferCache.getPartyEntityTypeTransfer(userVisit, partyEntityType);
    }

    public List<PartyEntityTypeTransfer> getPartyEntityTypeTransfersByParty(UserVisit userVisit, Party party) {
        var partyEntityTypes = getPartyEntityTypesByParty(party);
        List<PartyEntityTypeTransfer> partyEntityTypeTransfers = new ArrayList<>(partyEntityTypes.size());

        partyEntityTypes.forEach((partyEntityType) ->
                partyEntityTypeTransfers.add(partyEntityTypeTransferCache.getPartyEntityTypeTransfer(userVisit, partyEntityType))
        );

        return partyEntityTypeTransfers;
    }

    public void updatePartyEntityTypeFromValue(PartyEntityTypeValue partyEntityTypeValue, BasePK updatedBy) {
        if(partyEntityTypeValue.hasBeenModified()) {
            var partyEntityType = partyEntityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyEntityTypeValue.getPrimaryKey());

            partyEntityType.setThruTime(session.getStartTime());
            partyEntityType.store();

            var partyPK = partyEntityType.getPartyPK(); // Not updated
            var entityTypePK = partyEntityType.getEntityTypePK(); // Not updated
            var confirmDelete = partyEntityTypeValue.getConfirmDelete();

            partyEntityType = partyEntityTypeFactory.create(partyPK, entityTypePK, confirmDelete, session.getStartTime(), Session.MAX_TIME);

            sendEvent(partyPK, EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyEntityType(PartyEntityType partyEntityType, BasePK deletedBy) {
        partyEntityType.setThruTime(session.getStartTime());

        sendEvent(partyEntityType.getPartyPK(), EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyEntityTypesByParty(Party party, BasePK deletedBy) {
        getPartyEntityTypesByPartyForUpdate(party).forEach((partyEntityType) ->
                deletePartyEntityType(partyEntityType, deletedBy)
        );
    }

}
