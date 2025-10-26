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
import com.echothree.model.control.shipment.common.choice.FreeOnBoardChoicesBean;
import com.echothree.model.control.shipment.common.transfer.FreeOnBoardDescriptionTransfer;
import com.echothree.model.control.shipment.common.transfer.FreeOnBoardTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.shipment.common.pk.FreeOnBoardPK;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipment.server.entity.FreeOnBoardDescription;
import com.echothree.model.data.shipment.server.factory.FreeOnBoardDescriptionFactory;
import com.echothree.model.data.shipment.server.factory.FreeOnBoardDetailFactory;
import com.echothree.model.data.shipment.server.factory.FreeOnBoardFactory;
import com.echothree.model.data.shipment.server.value.FreeOnBoardDescriptionValue;
import com.echothree.model.data.shipment.server.value.FreeOnBoardDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FreeOnBoardControl
        extends BaseShipmentControl {

    /** Creates a new instance of FreeOnBoardControl */
    public FreeOnBoardControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Free On Boards
    // --------------------------------------------------------------------------------

    public FreeOnBoard createFreeOnBoard(final String freeOnBoardName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultFreeOnBoard = getDefaultFreeOnBoard();
        var defaultFound = defaultFreeOnBoard != null;

        if(defaultFound && isDefault) {
            var defaultFreeOnBoardDetailValue = getDefaultFreeOnBoardDetailValueForUpdate();

            defaultFreeOnBoardDetailValue.setIsDefault(false);
            updateFreeOnBoardFromValue(defaultFreeOnBoardDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var freeOnBoard = FreeOnBoardFactory.getInstance().create();
        var freeOnBoardDetail = FreeOnBoardDetailFactory.getInstance().create(session,
                freeOnBoard, freeOnBoardName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        freeOnBoard = FreeOnBoardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, freeOnBoard.getPrimaryKey());
        freeOnBoard.setActiveDetail(freeOnBoardDetail);
        freeOnBoard.setLastDetail(freeOnBoardDetail);
        freeOnBoard.store();

        sendEvent(freeOnBoard.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return freeOnBoard;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FreeOnBoard */
    public FreeOnBoard getFreeOnBoardByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new FreeOnBoardPK(entityInstance.getEntityUniqueId());

        return FreeOnBoardFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public FreeOnBoard getFreeOnBoardByEntityInstance(final EntityInstance entityInstance) {
        return getFreeOnBoardByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FreeOnBoard getFreeOnBoardByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getFreeOnBoardByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countFreeOnBoards() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM freeonboards, freeonboarddetails " +
                        "WHERE fob_activedetailid = fobdt_freeonboarddetailid");
    }

    private static final Map<EntityPermission, String> getFreeOnBoardByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM freeonboards, freeonboarddetails " +
                    "WHERE fob_activedetailid = fobdt_freeonboarddetailid AND fobdt_freeonboardname = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM freeonboards, freeonboarddetails " +
                    "WHERE fob_activedetailid = fobdt_freeonboarddetailid AND fobdt_freeonboardname = ? " +
                    "FOR UPDATE");

    public FreeOnBoard getFreeOnBoardByName(final String freeOnBoardName, final EntityPermission entityPermission) {
        return FreeOnBoardFactory.getInstance().getEntityFromQuery(entityPermission, getFreeOnBoardByNameQueries,
                freeOnBoardName);
    }

    public FreeOnBoard getFreeOnBoardByName(final String freeOnBoardName) {
        return getFreeOnBoardByName(freeOnBoardName, EntityPermission.READ_ONLY);
    }

    public FreeOnBoard getFreeOnBoardByNameForUpdate(final String freeOnBoardName) {
        return getFreeOnBoardByName(freeOnBoardName, EntityPermission.READ_WRITE);
    }

    public FreeOnBoardDetailValue getFreeOnBoardDetailValueForUpdate(final FreeOnBoard freeOnBoard) {
        return freeOnBoard == null ? null: freeOnBoard.getLastDetailForUpdate().getFreeOnBoardDetailValue().clone();
    }

    public FreeOnBoardDetailValue getFreeOnBoardDetailValueByNameForUpdate(final String freeOnBoardName) {
        return getFreeOnBoardDetailValueForUpdate(getFreeOnBoardByNameForUpdate(freeOnBoardName));
    }

    private static final Map<EntityPermission, String> getDefaultFreeOnBoardQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM freeonboards, freeonboarddetails " +
                    "WHERE fob_activedetailid = fobdt_freeonboarddetailid AND fobdt_isdefault = 1",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM freeonboards, freeonboarddetails " +
                    "WHERE fob_activedetailid = fobdt_freeonboarddetailid AND fobdt_isdefault = 1 " +
                    "FOR UPDATE");

    public FreeOnBoard getDefaultFreeOnBoard(final EntityPermission entityPermission) {
        return FreeOnBoardFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFreeOnBoardQueries);
    }

    public FreeOnBoard getDefaultFreeOnBoard() {
        return getDefaultFreeOnBoard(EntityPermission.READ_ONLY);
    }

    public FreeOnBoard getDefaultFreeOnBoardForUpdate() {
        return getDefaultFreeOnBoard(EntityPermission.READ_WRITE);
    }

    public FreeOnBoardDetailValue getDefaultFreeOnBoardDetailValueForUpdate() {
        return getDefaultFreeOnBoardForUpdate().getLastDetailForUpdate().getFreeOnBoardDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getFreeOnBoardsQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM freeonboards, freeonboarddetails " +
                    "WHERE fob_activedetailid = fobdt_freeonboarddetailid " +
                    "ORDER BY fobdt_sortorder, fobdt_freeonboardname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM freeonboards, freeonboarddetails " +
                    "WHERE fob_activedetailid = fobdt_freeonboarddetailid " +
                    "FOR UPDATE");

    private List<FreeOnBoard> getFreeOnBoards(final EntityPermission entityPermission) {
        return FreeOnBoardFactory.getInstance().getEntitiesFromQuery(entityPermission, getFreeOnBoardsQueries);
    }

    public List<FreeOnBoard> getFreeOnBoards() {
        return getFreeOnBoards(EntityPermission.READ_ONLY);
    }

    public List<FreeOnBoard> getFreeOnBoardsForUpdate() {
        return getFreeOnBoards(EntityPermission.READ_WRITE);
    }

    public FreeOnBoardTransfer getFreeOnBoardTransfer(final UserVisit userVisit,
            final FreeOnBoard freeOnBoard) {
        return getShipmentTransferCaches(userVisit).getFreeOnBoardTransferCache().getTransfer(freeOnBoard);
    }

    public List<FreeOnBoardTransfer> getFreeOnBoardTransfers(final UserVisit userVisit,
            final Collection<FreeOnBoard> freeOnBoards) {
        var freeOnBoardTransfers = new ArrayList<FreeOnBoardTransfer>(freeOnBoards.size());
        var freeOnBoardTransferCache = getShipmentTransferCaches(userVisit).getFreeOnBoardTransferCache();

        freeOnBoards.forEach((freeOnBoard) ->
                freeOnBoardTransfers.add(freeOnBoardTransferCache.getTransfer(freeOnBoard))
        );

        return freeOnBoardTransfers;
    }

    public List<FreeOnBoardTransfer> getFreeOnBoardTransfers(final UserVisit userVisit) {
        return getFreeOnBoardTransfers(userVisit, getFreeOnBoards());
    }

    public FreeOnBoardChoicesBean getFreeOnBoardChoices(final String defaultFreeOnBoardChoice,
            final Language language, final boolean allowNullChoice) {
        var freeOnBoards = getFreeOnBoards();
        var size = freeOnBoards.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFreeOnBoardChoice == null) {
                defaultValue = "";
            }
        }

        for(var freeOnBoard : freeOnBoards) {
            var freeOnBoardDetail = freeOnBoard.getLastDetail();

            var label = getBestFreeOnBoardDescription(freeOnBoard, language);
            var value = freeOnBoardDetail.getFreeOnBoardName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultFreeOnBoardChoice, value);
            if(usingDefaultChoice || (defaultValue == null && freeOnBoardDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FreeOnBoardChoicesBean(labels, values, defaultValue);
    }

    private void updateFreeOnBoardFromValue(final FreeOnBoardDetailValue freeOnBoardDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(freeOnBoardDetailValue.hasBeenModified()) {
            var freeOnBoard = FreeOnBoardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    freeOnBoardDetailValue.getFreeOnBoardPK());
            var freeOnBoardDetail = freeOnBoard.getActiveDetailForUpdate();

            freeOnBoardDetail.setThruTime(session.START_TIME_LONG);
            freeOnBoardDetail.store();

            var freeOnBoardPK = freeOnBoardDetail.getFreeOnBoardPK();
            var freeOnBoardName = freeOnBoardDetailValue.getFreeOnBoardName();
            var isDefault = freeOnBoardDetailValue.getIsDefault();
            var sortOrder = freeOnBoardDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultFreeOnBoard = getDefaultFreeOnBoard();
                var defaultFound = defaultFreeOnBoard != null && !defaultFreeOnBoard.equals(freeOnBoard);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFreeOnBoardDetailValue = getDefaultFreeOnBoardDetailValueForUpdate();

                    defaultFreeOnBoardDetailValue.setIsDefault(false);
                    updateFreeOnBoardFromValue(defaultFreeOnBoardDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            freeOnBoardDetail = FreeOnBoardDetailFactory.getInstance().create(freeOnBoardPK,
                    freeOnBoardName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            freeOnBoard.setActiveDetail(freeOnBoardDetail);
            freeOnBoard.setLastDetail(freeOnBoardDetail);

            sendEvent(freeOnBoardPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateFreeOnBoardFromValue(final FreeOnBoardDetailValue freeOnBoardDetailValue,
            final BasePK updatedBy) {
        updateFreeOnBoardFromValue(freeOnBoardDetailValue, true, updatedBy);
    }

    public void deleteFreeOnBoard(final FreeOnBoard freeOnBoard, final BasePK deletedBy) {
        var partyFreeOnBoardControl = Session.getModelController(PartyFreeOnBoardControl.class);

        partyFreeOnBoardControl.deletePartyFreeOnBoardsByFreeOnBoard(freeOnBoard, deletedBy);
        deleteFreeOnBoardDescriptionsByFreeOnBoard(freeOnBoard, deletedBy);

        var freeOnBoardDetail = freeOnBoard.getLastDetailForUpdate();
        freeOnBoardDetail.setThruTime(session.START_TIME_LONG);
        freeOnBoardDetail.store();
        freeOnBoard.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultFreeOnBoard = getDefaultFreeOnBoard();
        if(defaultFreeOnBoard == null) {
            var freeOnBoards = getFreeOnBoardsForUpdate();

            if(!freeOnBoards.isEmpty()) {
                var iter = freeOnBoards.iterator();
                if(iter.hasNext()) {
                    defaultFreeOnBoard = iter.next();
                }
                var freeOnBoardDetailValue = Objects.requireNonNull(defaultFreeOnBoard).getLastDetailForUpdate().getFreeOnBoardDetailValue().clone();

                freeOnBoardDetailValue.setIsDefault(true);
                updateFreeOnBoardFromValue(freeOnBoardDetailValue, false, deletedBy);
            }
        }

        sendEvent(freeOnBoard.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Free On Board Descriptions
    // --------------------------------------------------------------------------------

    public FreeOnBoardDescription createFreeOnBoardDescription(final FreeOnBoard freeOnBoard,
            final Language language, final String description, final BasePK createdBy) {
        var freeOnBoardDescription = FreeOnBoardDescriptionFactory.getInstance().create(freeOnBoard,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(freeOnBoard.getPrimaryKey(), EventTypes.MODIFY, freeOnBoardDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return freeOnBoardDescription;
    }

    private static final Map<EntityPermission, String> getFreeOnBoardDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM freeonboarddescriptions " +
                    "WHERE fobd_fob_freeonboardid = ? AND fobd_lang_languageid = ? AND fobd_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM freeonboarddescriptions " +
                    "WHERE fobd_fob_freeonboardid = ? AND fobd_lang_languageid = ? AND fobd_thrutime = ? " +
                    "FOR UPDATE");

    private FreeOnBoardDescription getFreeOnBoardDescription(final FreeOnBoard freeOnBoard,
            final Language language, final EntityPermission entityPermission) {
        return FreeOnBoardDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFreeOnBoardDescriptionQueries,
                freeOnBoard, language, Session.MAX_TIME);
    }

    public FreeOnBoardDescription getFreeOnBoardDescription(final FreeOnBoard freeOnBoard,
            final Language language) {
        return getFreeOnBoardDescription(freeOnBoard, language, EntityPermission.READ_ONLY);
    }

    public FreeOnBoardDescription getFreeOnBoardDescriptionForUpdate(final FreeOnBoard freeOnBoard,
            final Language language) {
        return getFreeOnBoardDescription(freeOnBoard, language, EntityPermission.READ_WRITE);
    }

    public FreeOnBoardDescriptionValue getFreeOnBoardDescriptionValue(final FreeOnBoardDescription freeOnBoardDescription) {
        return freeOnBoardDescription == null ? null: freeOnBoardDescription.getFreeOnBoardDescriptionValue().clone();
    }

    public FreeOnBoardDescriptionValue getFreeOnBoardDescriptionValueForUpdate(final FreeOnBoard freeOnBoard,
            final Language language) {
        return getFreeOnBoardDescriptionValue(getFreeOnBoardDescriptionForUpdate(freeOnBoard, language));
    }

    private static final Map<EntityPermission, String> getFreeOnBoardDescriptionsByFreeOnBoardQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM freeonboarddescriptions, languages " +
                    "WHERE fobd_fob_freeonboardid = ? AND fobd_thrutime = ? AND fobd_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM freeonboarddescriptions " +
                    "WHERE fobd_fob_freeonboardid = ? AND fobd_thrutime = ? " +
                    "FOR UPDATE");

    private List<FreeOnBoardDescription> getFreeOnBoardDescriptionsByFreeOnBoard(final FreeOnBoard freeOnBoard,
            final EntityPermission entityPermission) {
        return FreeOnBoardDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getFreeOnBoardDescriptionsByFreeOnBoardQueries,
                freeOnBoard, Session.MAX_TIME);
    }

    public List<FreeOnBoardDescription> getFreeOnBoardDescriptionsByFreeOnBoard(final FreeOnBoard freeOnBoard) {
        return getFreeOnBoardDescriptionsByFreeOnBoard(freeOnBoard, EntityPermission.READ_ONLY);
    }

    public List<FreeOnBoardDescription> getFreeOnBoardDescriptionsByFreeOnBoardForUpdate(final FreeOnBoard freeOnBoard) {
        return getFreeOnBoardDescriptionsByFreeOnBoard(freeOnBoard, EntityPermission.READ_WRITE);
    }

    public String getBestFreeOnBoardDescription(final FreeOnBoard freeOnBoard, final Language language) {
        var freeOnBoardDescription = getFreeOnBoardDescription(freeOnBoard, language);
        String description;

        if(freeOnBoardDescription == null && !language.getIsDefault()) {
            freeOnBoardDescription = getFreeOnBoardDescription(freeOnBoard, getPartyControl().getDefaultLanguage());
        }

        if(freeOnBoardDescription == null) {
            description = freeOnBoard.getLastDetail().getFreeOnBoardName();
        } else {
            description = freeOnBoardDescription.getDescription();
        }

        return description;
    }

    public FreeOnBoardDescriptionTransfer getFreeOnBoardDescriptionTransfer(final UserVisit userVisit,
            final FreeOnBoardDescription freeOnBoardDescription) {
        return getShipmentTransferCaches(userVisit).getFreeOnBoardDescriptionTransferCache().getTransfer(freeOnBoardDescription);
    }

    public List<FreeOnBoardDescriptionTransfer> getFreeOnBoardDescriptionTransfersByFreeOnBoard(final UserVisit userVisit,
            final FreeOnBoard freeOnBoard) {
        var freeOnBoardDescriptions = getFreeOnBoardDescriptionsByFreeOnBoard(freeOnBoard);
        var freeOnBoardDescriptionTransfers = new ArrayList<FreeOnBoardDescriptionTransfer>(freeOnBoardDescriptions.size());
        var freeOnBoardDescriptionTransferCache = getShipmentTransferCaches(userVisit).getFreeOnBoardDescriptionTransferCache();

        freeOnBoardDescriptions.forEach((freeOnBoardDescription) ->
                freeOnBoardDescriptionTransfers.add(freeOnBoardDescriptionTransferCache.getTransfer(freeOnBoardDescription))
        );

        return freeOnBoardDescriptionTransfers;
    }

    public void updateFreeOnBoardDescriptionFromValue(final FreeOnBoardDescriptionValue freeOnBoardDescriptionValue,
            final BasePK updatedBy) {
        if(freeOnBoardDescriptionValue.hasBeenModified()) {
            var freeOnBoardDescription = FreeOnBoardDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, freeOnBoardDescriptionValue.getPrimaryKey());

            freeOnBoardDescription.setThruTime(session.START_TIME_LONG);
            freeOnBoardDescription.store();

            var freeOnBoard = freeOnBoardDescription.getFreeOnBoard();
            var language = freeOnBoardDescription.getLanguage();
            var description = freeOnBoardDescriptionValue.getDescription();

            freeOnBoardDescription = FreeOnBoardDescriptionFactory.getInstance().create(freeOnBoard, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(freeOnBoard.getPrimaryKey(), EventTypes.MODIFY, freeOnBoardDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFreeOnBoardDescription(final FreeOnBoardDescription freeOnBoardDescription, final BasePK deletedBy) {
        freeOnBoardDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(freeOnBoardDescription.getFreeOnBoardPK(), EventTypes.MODIFY, freeOnBoardDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFreeOnBoardDescriptionsByFreeOnBoard(final FreeOnBoard freeOnBoard, final BasePK deletedBy) {
        var freeOnBoardDescriptions = getFreeOnBoardDescriptionsByFreeOnBoardForUpdate(freeOnBoard);

        freeOnBoardDescriptions.forEach((freeOnBoardDescription) -> {
            deleteFreeOnBoardDescription(freeOnBoardDescription, deletedBy);
        });
    }

}
