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

package com.echothree.model.control.shipment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.shipment.common.transfer.PartyFreeOnBoardTransfer;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipment.server.entity.PartyFreeOnBoard;
import com.echothree.model.data.shipment.server.factory.PartyFreeOnBoardFactory;
import com.echothree.model.data.shipment.server.value.PartyFreeOnBoardValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyFreeOnBoardControl
        extends BaseShipmentControl {

    /** Creates a new instance of FreeOnBoardControl */
    protected PartyFreeOnBoardControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Free On Boards
    // --------------------------------------------------------------------------------

    public PartyFreeOnBoard createPartyFreeOnBoard(Party party, FreeOnBoard freeOnBoard, BasePK createdBy) {
        var partyFreeOnBoard = PartyFreeOnBoardFactory.getInstance().create(party, freeOnBoard, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyFreeOnBoard.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyFreeOnBoard;
    }

    private PartyFreeOnBoard getPartyFreeOnBoard(Party party, EntityPermission entityPermission) {
        PartyFreeOnBoard partyFreeOnBoard;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyfreeonboards " +
                        "WHERE pfob_par_partyid = ? AND pfob_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyfreeonboards " +
                        "WHERE pfob_par_partyid = ? AND pfob_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyFreeOnBoardFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            partyFreeOnBoard = PartyFreeOnBoardFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyFreeOnBoard;
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
        List<PartyFreeOnBoard> partyFreeOnBoards;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyfreeonboards, freeOnBoards, freeOnBoarddetails " +
                        "WHERE pfob_fob_freeOnBoardid = ? AND pfob_thrutime = ? " +
                        "AND pfob_fob_freeOnBoardid = fob_freeOnBoardid AND fob_lastdetailid = fobdt_freeOnBoarddetailid " +
                        "ORDER BY fobdt_sortorder, fobdt_freeOnBoardname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyfreeonboards " +
                        "WHERE pfob_fob_freeOnBoardid = ? AND pfob_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyFreeOnBoardFactory.getInstance().prepareStatement(query);

            ps.setLong(1, freeOnBoard.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            partyFreeOnBoards = PartyFreeOnBoardFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyFreeOnBoards;
    }

    public List<PartyFreeOnBoard> getPartyFreeOnBoardsByFreeOnBoard(FreeOnBoard freeOnBoard) {
        return getPartyFreeOnBoardsByFreeOnBoard(freeOnBoard, EntityPermission.READ_ONLY);
    }

    public List<PartyFreeOnBoard> getPartyFreeOnBoardsByFreeOnBoardForUpdate(FreeOnBoard freeOnBoard) {
        return getPartyFreeOnBoardsByFreeOnBoard(freeOnBoard, EntityPermission.READ_WRITE);
    }

    public PartyFreeOnBoardTransfer getPartyFreeOnBoardTransfer(UserVisit userVisit, Party party) {
        var partyFreeOnBoard = getPartyFreeOnBoard(party);

        return partyFreeOnBoard == null? null: getShipmentTransferCaches().getPartyFreeOnBoardTransferCache().getTransfer(userVisit, partyFreeOnBoard);
    }

    public PartyFreeOnBoardTransfer getPartyFreeOnBoardTransfer(UserVisit userVisit, PartyFreeOnBoard partyFreeOnBoard) {
        return getShipmentTransferCaches().getPartyFreeOnBoardTransferCache().getTransfer(userVisit, partyFreeOnBoard);
    }

    public void updatePartyFreeOnBoardFromValue(PartyFreeOnBoardValue partyFreeOnBoardValue, BasePK updatedBy) {
        if(partyFreeOnBoardValue.hasBeenModified()) {
            var partyFreeOnBoard = PartyFreeOnBoardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyFreeOnBoardValue.getPrimaryKey());

            partyFreeOnBoard.setThruTime(session.START_TIME_LONG);
            partyFreeOnBoard.store();

            var partyPK = partyFreeOnBoard.getPartyPK(); // Not updated
            var freeOnBoardPK = partyFreeOnBoardValue.getFreeOnBoardPK();

            partyFreeOnBoard = PartyFreeOnBoardFactory.getInstance().create(partyPK, freeOnBoardPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(partyPK, EventTypes.MODIFY, partyFreeOnBoard.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyFreeOnBoard(PartyFreeOnBoard partyFreeOnBoard, BasePK deletedBy) {
        partyFreeOnBoard.setThruTime(session.START_TIME_LONG);

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
