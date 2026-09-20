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
import com.echothree.model.control.shipment.common.choice.FreeOnBoardChoicesBean;
import com.echothree.model.control.shipment.common.transfer.FreeOnBoardDescriptionTransfer;
import com.echothree.model.control.shipment.common.transfer.FreeOnBoardTransfer;
import com.echothree.model.control.shipment.server.transfer.FreeOnBoardDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.FreeOnBoardTransferCache;
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
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.FREE_ON_BOARDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.FREE_ON_BOARD_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.shipment.FreeOnBoardDescriptions.FreeOnBoardDescriptions;
import static com.echothree.model.jooq.server.tables.shipment.FreeOnBoardDetails.FreeOnBoardDetails;
import static com.echothree.model.jooq.server.tables.shipment.FreeOnBoards.FreeOnBoards;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class FreeOnBoardControl
        extends BaseModelControl {

    @Inject
    protected PartyFreeOnBoardControl partyFreeOnBoardControl;

    /** Creates a new instance of FreeOnBoardControl */
    protected FreeOnBoardControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Free On Boards
    // --------------------------------------------------------------------------------

    @Inject
    protected FreeOnBoardDetailFactory freeOnBoardDetailFactory;

    @Inject
    protected FreeOnBoardFactory freeOnBoardFactory;

    @Inject
    private FreeOnBoardTransferCache freeOnBoardTransferCache;

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

        var freeOnBoard = freeOnBoardFactory.create();
        var freeOnBoardDetail = freeOnBoardDetailFactory.create(
                freeOnBoard, freeOnBoardName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        freeOnBoard = freeOnBoardFactory.getEntityFromPK(EntityPermission.READ_WRITE, freeOnBoard.getPrimaryKey());
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

        return freeOnBoardFactory.getEntityFromPK(entityPermission, pk);
    }

    public FreeOnBoard getFreeOnBoardByEntityInstance(final EntityInstance entityInstance) {
        return getFreeOnBoardByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FreeOnBoard getFreeOnBoardByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getFreeOnBoardByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countFreeOnBoards() {
        return session.getDslContext()
                .selectCount()
                .from(FreeOnBoards)
                .join(FreeOnBoardDetails).onKey(FREE_ON_BOARDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }


    public FreeOnBoard getFreeOnBoardByName(final String freeOnBoardName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FreeOnBoards.fields())
                .from(FreeOnBoards)
                .join(FreeOnBoardDetails).onKey(FREE_ON_BOARDS_ACTIVE_DETAIL_FK)
                .where(FreeOnBoardDetails.FREE_ON_BOARD_NAME.eq(freeOnBoardName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return freeOnBoardFactory.getEntityFromQuery(entityPermission, query);
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


    public FreeOnBoard getDefaultFreeOnBoard(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FreeOnBoards.fields())
                .from(FreeOnBoards)
                .join(FreeOnBoardDetails).onKey(FREE_ON_BOARDS_ACTIVE_DETAIL_FK)
                .where(FreeOnBoardDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return freeOnBoardFactory.getEntityFromQuery(entityPermission, query);
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


    private List<FreeOnBoard> getFreeOnBoards(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FreeOnBoards.fields())
                .from(FreeOnBoards)
                .join(FreeOnBoardDetails).onKey(FREE_ON_BOARDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FreeOnBoardDetails.SORT_ORDER, FreeOnBoardDetails.FREE_ON_BOARD_NAME),
                    FreeOnBoardFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return freeOnBoardFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<FreeOnBoard> getFreeOnBoards() {
        return getFreeOnBoards(EntityPermission.READ_ONLY);
    }

    public List<FreeOnBoard> getFreeOnBoardsForUpdate() {
        return getFreeOnBoards(EntityPermission.READ_WRITE);
    }

    public FreeOnBoardTransfer getFreeOnBoardTransfer(final UserVisit userVisit,
            final FreeOnBoard freeOnBoard) {
        return freeOnBoardTransferCache.getTransfer(userVisit, freeOnBoard);
    }

    public List<FreeOnBoardTransfer> getFreeOnBoardTransfers(final UserVisit userVisit,
            final Collection<FreeOnBoard> freeOnBoards) {
        var freeOnBoardTransfers = new ArrayList<FreeOnBoardTransfer>(freeOnBoards.size());

        freeOnBoards.forEach((freeOnBoard) ->
                freeOnBoardTransfers.add(freeOnBoardTransferCache.getTransfer(userVisit, freeOnBoard))
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
            var freeOnBoard = freeOnBoardFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    freeOnBoardDetailValue.getFreeOnBoardPK());
            var freeOnBoardDetail = freeOnBoard.getActiveDetailForUpdate();

            freeOnBoardDetail.setThruTime(session.getStartTime());
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

            freeOnBoardDetail = freeOnBoardDetailFactory.create(freeOnBoardPK,
                    freeOnBoardName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

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

        partyFreeOnBoardControl.deletePartyFreeOnBoardsByFreeOnBoard(freeOnBoard, deletedBy);
        deleteFreeOnBoardDescriptionsByFreeOnBoard(freeOnBoard, deletedBy);

        var freeOnBoardDetail = freeOnBoard.getLastDetailForUpdate();
        freeOnBoardDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected FreeOnBoardDescriptionFactory freeOnBoardDescriptionFactory;

    @Inject
    private FreeOnBoardDescriptionTransferCache freeOnBoardDescriptionTransferCache;

    public FreeOnBoardDescription createFreeOnBoardDescription(final FreeOnBoard freeOnBoard,
            final Language language, final String description, final BasePK createdBy) {
        var freeOnBoardDescription = freeOnBoardDescriptionFactory.create(freeOnBoard,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(freeOnBoard.getPrimaryKey(), EventTypes.MODIFY, freeOnBoardDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return freeOnBoardDescription;
    }


    private FreeOnBoardDescription getFreeOnBoardDescription(final FreeOnBoard freeOnBoard,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FreeOnBoardDescriptions.fields())
                .from(FreeOnBoardDescriptions)
                .where(FreeOnBoardDescriptions.FREE_ON_BOARD.eq(freeOnBoard.getPrimaryKey()),
                        FreeOnBoardDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        FreeOnBoardDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return freeOnBoardDescriptionFactory.getEntityFromQuery(entityPermission, query);
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


    private List<FreeOnBoardDescription> getFreeOnBoardDescriptionsByFreeOnBoard(final FreeOnBoard freeOnBoard,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FreeOnBoardDescriptions.fields())
                    .from(FreeOnBoardDescriptions)
                    .join(Languages).onKey(FREE_ON_BOARD_DESCRIPTIONS_LANGUAGE_FK)
                    .where(FreeOnBoardDescriptions.FREE_ON_BOARD.eq(freeOnBoard.getPrimaryKey()),
                            FreeOnBoardDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    FreeOnBoardDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FreeOnBoardDescriptions.fields())
                    .from(FreeOnBoardDescriptions)
                    .where(FreeOnBoardDescriptions.FREE_ON_BOARD.eq(freeOnBoard.getPrimaryKey()),
                            FreeOnBoardDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return freeOnBoardDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            freeOnBoardDescription = getFreeOnBoardDescription(freeOnBoard, partyControl.getDefaultLanguage());
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
        return freeOnBoardDescriptionTransferCache.getTransfer(userVisit, freeOnBoardDescription);
    }

    public List<FreeOnBoardDescriptionTransfer> getFreeOnBoardDescriptionTransfersByFreeOnBoard(final UserVisit userVisit,
            final FreeOnBoard freeOnBoard) {
        var freeOnBoardDescriptions = getFreeOnBoardDescriptionsByFreeOnBoard(freeOnBoard);
        var freeOnBoardDescriptionTransfers = new ArrayList<FreeOnBoardDescriptionTransfer>(freeOnBoardDescriptions.size());

        freeOnBoardDescriptions.forEach((freeOnBoardDescription) ->
                freeOnBoardDescriptionTransfers.add(freeOnBoardDescriptionTransferCache.getTransfer(userVisit, freeOnBoardDescription))
        );

        return freeOnBoardDescriptionTransfers;
    }

    public void updateFreeOnBoardDescriptionFromValue(final FreeOnBoardDescriptionValue freeOnBoardDescriptionValue,
            final BasePK updatedBy) {
        if(freeOnBoardDescriptionValue.hasBeenModified()) {
            var freeOnBoardDescription = freeOnBoardDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, freeOnBoardDescriptionValue.getPrimaryKey());

            freeOnBoardDescription.setThruTime(session.getStartTime());
            freeOnBoardDescription.store();

            var freeOnBoard = freeOnBoardDescription.getFreeOnBoard();
            var language = freeOnBoardDescription.getLanguage();
            var description = freeOnBoardDescriptionValue.getDescription();

            freeOnBoardDescription = freeOnBoardDescriptionFactory.create(freeOnBoard, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(freeOnBoard.getPrimaryKey(), EventTypes.MODIFY, freeOnBoardDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFreeOnBoardDescription(final FreeOnBoardDescription freeOnBoardDescription, final BasePK deletedBy) {
        freeOnBoardDescription.setThruTime(session.getStartTime());

        sendEvent(freeOnBoardDescription.getFreeOnBoardPK(), EventTypes.MODIFY, freeOnBoardDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFreeOnBoardDescriptionsByFreeOnBoard(final FreeOnBoard freeOnBoard, final BasePK deletedBy) {
        var freeOnBoardDescriptions = getFreeOnBoardDescriptionsByFreeOnBoardForUpdate(freeOnBoard);

        freeOnBoardDescriptions.forEach((freeOnBoardDescription) -> {
            deleteFreeOnBoardDescription(freeOnBoardDescription, deletedBy);
        });
    }

}
