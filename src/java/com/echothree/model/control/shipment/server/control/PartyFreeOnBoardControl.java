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

package com.echothree.model.control.shipment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.shipment.common.transfer.PartyFreeOnBoardTransfer;
import com.echothree.model.control.shipment.server.transfer.PartyFreeOnBoardTransferCache;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipment.server.entity.PartyFreeOnBoard;
import com.echothree.model.data.shipment.server.factory.PartyFreeOnBoardFactory;
import com.echothree.model.data.shipment.server.value.PartyFreeOnBoardValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.PARTY_FREE_ON_BOARDS_PARTY_FK;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.shipment.PartyFreeOnBoards.PartyFreeOnBoards;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class PartyFreeOnBoardControl
        extends BaseModelControl {

    /** Creates a new instance of FreeOnBoardControl */
    protected PartyFreeOnBoardControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Free On Boards
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyFreeOnBoardFactory partyFreeOnBoardFactory;

    @Inject
    private PartyFreeOnBoardTransferCache partyFreeOnBoardTransferCache;

    public PartyFreeOnBoard createPartyFreeOnBoard(Party party, FreeOnBoard freeOnBoard, BasePK createdBy) {
        var partyFreeOnBoard = partyFreeOnBoardFactory.create(party, freeOnBoard, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyFreeOnBoard.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyFreeOnBoard;
    }

    private PartyFreeOnBoard getPartyFreeOnBoard(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyFreeOnBoards.fields())
                .from(PartyFreeOnBoards)
                .where(PartyFreeOnBoards.PARTY.eq(party.getPrimaryKey()),
                        PartyFreeOnBoards.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyFreeOnBoardFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyFreeOnBoard getPartyFreeOnBoard(Party party) {
        return getPartyFreeOnBoard(party, EntityPermission.READ_ONLY);
    }

    public PartyFreeOnBoard getPartyFreeOnBoardForUpdate(Party party) {
        return getPartyFreeOnBoard(party, EntityPermission.READ_WRITE);
    }

    public PartyFreeOnBoardValue getPartyFreeOnBoardValue(PartyFreeOnBoard partyFreeOnBoard) {
        return partyFreeOnBoard == null? null: partyFreeOnBoard.getPartyFreeOnBoardValue().clone();
    }

    public PartyFreeOnBoardValue getPartyFreeOnBoardValueForUpdate(Party party) {
        return getPartyFreeOnBoardValue(getPartyFreeOnBoardForUpdate(party));
    }

    private List<PartyFreeOnBoard> getPartyFreeOnBoardsByFreeOnBoard(FreeOnBoard freeOnBoard, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyFreeOnBoards.fields())
                    .from(PartyFreeOnBoards)
                    .join(Parties).onKey(PARTY_FREE_ON_BOARDS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyFreeOnBoards.FREE_ON_BOARD.eq(freeOnBoard.getPrimaryKey()),
                            PartyFreeOnBoards.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyFreeOnBoardFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyFreeOnBoards.fields())
                    .from(PartyFreeOnBoards)
                    .where(PartyFreeOnBoards.FREE_ON_BOARD.eq(freeOnBoard.getPrimaryKey()),
                            PartyFreeOnBoards.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyFreeOnBoardFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyFreeOnBoard> getPartyFreeOnBoardsByFreeOnBoard(FreeOnBoard freeOnBoard) {
        return getPartyFreeOnBoardsByFreeOnBoard(freeOnBoard, EntityPermission.READ_ONLY);
    }

    public List<PartyFreeOnBoard> getPartyFreeOnBoardsByFreeOnBoardForUpdate(FreeOnBoard freeOnBoard) {
        return getPartyFreeOnBoardsByFreeOnBoard(freeOnBoard, EntityPermission.READ_WRITE);
    }

    public PartyFreeOnBoardTransfer getPartyFreeOnBoardTransfer(UserVisit userVisit, Party party) {
        var partyFreeOnBoard = getPartyFreeOnBoard(party);

        return partyFreeOnBoard == null? null: partyFreeOnBoardTransferCache.getTransfer(userVisit, partyFreeOnBoard);
    }

    public PartyFreeOnBoardTransfer getPartyFreeOnBoardTransfer(UserVisit userVisit, PartyFreeOnBoard partyFreeOnBoard) {
        return partyFreeOnBoardTransferCache.getTransfer(userVisit, partyFreeOnBoard);
    }

    public void updatePartyFreeOnBoardFromValue(PartyFreeOnBoardValue partyFreeOnBoardValue, BasePK updatedBy) {
        if(partyFreeOnBoardValue.hasBeenModified()) {
            var partyFreeOnBoard = partyFreeOnBoardFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyFreeOnBoardValue.getPrimaryKey());

            partyFreeOnBoard.setThruTime(session.getStartTime());
            partyFreeOnBoard.store();

            var partyPK = partyFreeOnBoard.getPartyPK(); // Not updated
            var freeOnBoardPK = partyFreeOnBoardValue.getFreeOnBoardPK();

            partyFreeOnBoard = partyFreeOnBoardFactory.create(partyPK, freeOnBoardPK, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(partyPK, EventTypes.MODIFY, partyFreeOnBoard.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyFreeOnBoard(PartyFreeOnBoard partyFreeOnBoard, BasePK deletedBy) {
        partyFreeOnBoard.setThruTime(session.getStartTime());

        sendEvent(partyFreeOnBoard.getPartyPK(), EventTypes.MODIFY, partyFreeOnBoard.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyFreeOnBoardByParty(Party party, BasePK deletedBy) {
        var partyFreeOnBoard = getPartyFreeOnBoardForUpdate(party);

        if(partyFreeOnBoard != null) {
            deletePartyFreeOnBoard(partyFreeOnBoard, deletedBy);
        }
    }

    public void deletePartyFreeOnBoardsByFreeOnBoard(FreeOnBoard freeOnBoard, BasePK deletedBy) {
        var partyFreeOnBoards = getPartyFreeOnBoardsByFreeOnBoardForUpdate(freeOnBoard);

        partyFreeOnBoards.forEach((partyFreeOnBoard) -> 
                deletePartyFreeOnBoard(partyFreeOnBoard, deletedBy)
        );
    }

}
