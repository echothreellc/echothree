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

package com.echothree.model.control.party.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.transfer.PartyEntityTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyEntityType;
import com.echothree.model.data.party.server.factory.PartyEntityTypeFactory;
import com.echothree.model.data.party.server.value.PartyEntityTypeValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyEntityTypeControl
        extends BasePartyControl {

    /** Creates a new instance of PartyEntityTypeControl */
    protected PartyEntityTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Entity Types
    // --------------------------------------------------------------------------------

    public PartyEntityType createPartyEntityType(Party party, EntityType entityType, Boolean confirmDelete, BasePK createdBy) {
        var partyEntityType = PartyEntityTypeFactory.getInstance().create(party, entityType, confirmDelete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyEntityType;
    }

    private PartyEntityType getPartyEntityType(Party party, EntityType entityType, EntityPermission entityPermission) {
        PartyEntityType partyEntityType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_ent_entitytypeid = ? AND pent_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_ent_entitytypeid = ? AND pent_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyEntityTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            partyEntityType = PartyEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyEntityType;
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
        List<PartyEntityType> partyEntityTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes, entitytypes, entitytypedetails, componentvendors, componentvendordetails " +
                        "WHERE pent_par_partyid = ? AND pent_thrutime = ? " +
                        "AND pent_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid " +
                        "ORDER BY entdt_sortorder, entdt_entitytypename, cvndd_componentvendorname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyEntityTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            partyEntityTypes = PartyEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyEntityTypes;
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
            var partyEntityType = PartyEntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyEntityTypeValue.getPrimaryKey());

            partyEntityType.setThruTime(session.START_TIME_LONG);
            partyEntityType.store();

            var partyPK = partyEntityType.getPartyPK(); // Not updated
            var entityTypePK = partyEntityType.getEntityTypePK(); // Not updated
            var confirmDelete = partyEntityTypeValue.getConfirmDelete();

            partyEntityType = PartyEntityTypeFactory.getInstance().create(partyPK, entityTypePK, confirmDelete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(partyPK, EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyEntityType(PartyEntityType partyEntityType, BasePK deletedBy) {
        partyEntityType.setThruTime(session.START_TIME_LONG);

        sendEvent(partyEntityType.getPartyPK(), EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyEntityTypesByParty(Party party, BasePK deletedBy) {
        getPartyEntityTypesByPartyForUpdate(party).forEach((partyEntityType) ->
                deletePartyEntityType(partyEntityType, deletedBy)
        );
    }

}
