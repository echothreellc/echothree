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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.choice.LotTimeTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.LotTimeTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.LotTimeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.LotTimeTypePK;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.entity.LotTime;
import com.echothree.model.data.inventory.server.entity.LotTimeType;
import com.echothree.model.data.inventory.server.entity.LotTimeTypeDescription;
import com.echothree.model.data.inventory.server.factory.LotTimeFactory;
import com.echothree.model.data.inventory.server.factory.LotTimeTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.LotTimeTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotTimeTypeFactory;
import com.echothree.model.data.inventory.server.value.LotTimeTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotTimeTypeDetailValue;
import com.echothree.model.data.inventory.server.value.LotTimeValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.LOT_TIME_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.LotDetails.LotDetails;
import static com.echothree.model.jooq.server.tables.inventory.LotTimeTypeDescriptions.LotTimeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.LotTimeTypeDetails.LotTimeTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.LotTimeTypes.LotTimeTypes;
import static com.echothree.model.jooq.server.tables.inventory.LotTimes.LotTimes;
import static com.echothree.model.jooq.server.tables.inventory.Lots.Lots;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
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
public class LotTimeControl
        extends BaseModelControl {

    @Inject
    LotTimeTypeTransferCache lotTimeTypeTransferCache;

    @Inject
    LotTimeTypeDescriptionTransferCache lotTimeTypeDescriptionTransferCache;

    @Inject
    LotTimeTransferCache lotTimeTransferCache;

    /**
     * Creates a new instance of LotTimeControl
     */
    protected LotTimeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    @Inject
    protected LotTimeTypeFactory lotTimeTypeFactory;

    @Inject
    protected LotTimeTypeDetailFactory lotTimeTypeDetailFactory;

    public LotTimeType createLotTimeType(String lotTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLotTimeType = getDefaultLotTimeType();
        var defaultFound = defaultLotTimeType != null;

        if(defaultFound && isDefault) {
            var defaultLotTimeTypeDetailValue = getDefaultLotTimeTypeDetailValueForUpdate();

            defaultLotTimeTypeDetailValue.setIsDefault(false);
            updateLotTimeTypeFromValue(defaultLotTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var lotTimeType = lotTimeTypeFactory.create();
        var lotTimeTypeDetail = lotTimeTypeDetailFactory.create(lotTimeType, lotTimeTypeName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        lotTimeType = lotTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                lotTimeType.getPrimaryKey());
        lotTimeType.setActiveDetail(lotTimeTypeDetail);
        lotTimeType.setLastDetail(lotTimeTypeDetail);
        lotTimeType.store();

        sendEvent(lotTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return lotTimeType;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.LotTimeType
     */
    public LotTimeType getLotTimeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LotTimeTypePK(entityInstance.getEntityUniqueId());

        return lotTimeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public LotTimeType getLotTimeTypeByEntityInstance(EntityInstance entityInstance) {
        return getLotTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LotTimeType getLotTimeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLotTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLotTimeTypes() {
        return session.getDslContext()
                .selectCount()
                .from(LotTimeTypes)
                .join(LotTimeTypeDetails).onKey(LOT_TIME_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private LotTimeType getLotTimeTypeByName(String lotTimeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotTimeTypes.fields())
                .from(LotTimeTypes)
                .join(LotTimeTypeDetails).onKey(LOT_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(LotTimeTypeDetails.LOT_TIME_TYPE_NAME.eq(lotTimeTypeName));

        var query = entityPermission == EntityPermission.READ_ONLY ? baseQuery : baseQuery.forUpdate();

        return lotTimeTypeFactory.getEntityFromQuery(entityPermission,
                lotTimeTypeFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public LotTimeType getLotTimeTypeByName(String lotTimeTypeName) {
        return getLotTimeTypeByName(lotTimeTypeName, EntityPermission.READ_ONLY);
    }

    public LotTimeType getLotTimeTypeByNameForUpdate(String lotTimeTypeName) {
        return getLotTimeTypeByName(lotTimeTypeName, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDetailValue getLotTimeTypeDetailValueForUpdate(LotTimeType lotTimeType) {
        return lotTimeType == null ? null : lotTimeType.getLastDetailForUpdate().getLotTimeTypeDetailValue().clone();
    }

    public LotTimeTypeDetailValue getLotTimeTypeDetailValueByNameForUpdate(String lotTimeTypeName) {
        return getLotTimeTypeDetailValueForUpdate(getLotTimeTypeByNameForUpdate(lotTimeTypeName));
    }

    private LotTimeType getDefaultLotTimeType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotTimeTypes.fields())
                .from(LotTimeTypes)
                .join(LotTimeTypeDetails).onKey(LOT_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(LotTimeTypeDetails.IS_DEFAULT.eq(true));

        var query = entityPermission == EntityPermission.READ_ONLY ? baseQuery : baseQuery.forUpdate();

        return lotTimeTypeFactory.getEntityFromQuery(entityPermission,
                lotTimeTypeFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public LotTimeType getDefaultLotTimeType() {
        return getDefaultLotTimeType(EntityPermission.READ_ONLY);
    }

    public LotTimeType getDefaultLotTimeTypeForUpdate() {
        return getDefaultLotTimeType(EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDetailValue getDefaultLotTimeTypeDetailValueForUpdate() {
        return getDefaultLotTimeTypeForUpdate().getLastDetailForUpdate().getLotTimeTypeDetailValue().clone();
    }

    private List<LotTimeType> getLotTimeTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotTimeTypes.fields())
                .from(LotTimeTypes)
                .join(LotTimeTypeDetails).onKey(LOT_TIME_TYPES_ACTIVE_DETAIL_FK);

        var query = entityPermission == EntityPermission.READ_ONLY
                ? baseQuery.orderBy(LotTimeTypeDetails.SORT_ORDER, LotTimeTypeDetails.LOT_TIME_TYPE_NAME) : baseQuery.forUpdate();

        var sql = query.getSQL() + (entityPermission == EntityPermission.READ_ONLY ? " _LIMIT_" : "");

        return lotTimeTypeFactory.getEntitiesFromQuery(entityPermission, lotTimeTypeFactory.prepareStatement(sql));
    }

    public List<LotTimeType> getLotTimeTypes() {
        return getLotTimeTypes(EntityPermission.READ_ONLY);
    }

    public List<LotTimeType> getLotTimeTypesForUpdate() {
        return getLotTimeTypes(EntityPermission.READ_WRITE);
    }

    public LotTimeTypeTransfer getLotTimeTypeTransfer(UserVisit userVisit, LotTimeType lotTimeType) {
        return lotTimeTypeTransferCache.getTransfer(userVisit, lotTimeType);
    }

    public List<LotTimeTypeTransfer> getLotTimeTypeTransfers(UserVisit userVisit, Collection<LotTimeType> lotTimeTypes) {
        List<LotTimeTypeTransfer> lotTimeTypeTransfers = new ArrayList<>(lotTimeTypes.size());

        lotTimeTypes.forEach((lotTimeType) ->
                lotTimeTypeTransfers.add(lotTimeTypeTransferCache.getTransfer(userVisit, lotTimeType))
        );

        return lotTimeTypeTransfers;
    }

    public List<LotTimeTypeTransfer> getLotTimeTypeTransfers(UserVisit userVisit) {
        return getLotTimeTypeTransfers(userVisit, getLotTimeTypes());
    }

    public LotTimeTypeChoicesBean getLotTimeTypeChoices(String defaultLotTimeTypeChoice, Language language, boolean allowNullChoice) {
        var lotTimeTypes = getLotTimeTypes();
        var size = lotTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLotTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var lotTimeType : lotTimeTypes) {
            var lotTimeTypeDetail = lotTimeType.getLastDetail();

            var label = getBestLotTimeTypeDescription(lotTimeType, language);
            var value = lotTimeTypeDetail.getLotTimeTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultLotTimeTypeChoice != null && defaultLotTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && lotTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LotTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateLotTimeTypeFromValue(LotTimeTypeDetailValue lotTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(lotTimeTypeDetailValue.hasBeenModified()) {
            var lotTimeType = lotTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTimeTypeDetailValue.getLotTimeTypePK());
            var lotTimeTypeDetail = lotTimeType.getActiveDetailForUpdate();

            lotTimeTypeDetail.setThruTime(session.getStartTime());
            lotTimeTypeDetail.store();

            var lotTimeTypePK = lotTimeTypeDetail.getLotTimeTypePK(); // Not updated
            var lotTimeTypeName = lotTimeTypeDetailValue.getLotTimeTypeName();
            var isDefault = lotTimeTypeDetailValue.getIsDefault();
            var sortOrder = lotTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultLotTimeType = getDefaultLotTimeType();
                var defaultFound = defaultLotTimeType != null && !defaultLotTimeType.equals(lotTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLotTimeTypeDetailValue = getDefaultLotTimeTypeDetailValueForUpdate();

                    defaultLotTimeTypeDetailValue.setIsDefault(false);
                    updateLotTimeTypeFromValue(defaultLotTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            lotTimeTypeDetail = lotTimeTypeDetailFactory.create(lotTimeTypePK, lotTimeTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            lotTimeType.setActiveDetail(lotTimeTypeDetail);
            lotTimeType.setLastDetail(lotTimeTypeDetail);

            sendEvent(lotTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateLotTimeTypeFromValue(LotTimeTypeDetailValue lotTimeTypeDetailValue, BasePK updatedBy) {
        updateLotTimeTypeFromValue(lotTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteLotTimeType(LotTimeType lotTimeType, BasePK deletedBy) {
        deleteLotTimesByLotTimeType(lotTimeType, deletedBy);
        deleteLotTimeTypeDescriptionsByLotTimeType(lotTimeType, deletedBy);

        var lotTimeTypeDetail = lotTimeType.getLastDetailForUpdate();
        lotTimeTypeDetail.setThruTime(session.getStartTime());
        lotTimeType.setActiveDetail(null);
        lotTimeType.store();

        // Check for default, and pick one if necessary
        var defaultLotTimeType = getDefaultLotTimeType();
        if(defaultLotTimeType == null) {
            var lotTimeTypes = getLotTimeTypesForUpdate();

            if(!lotTimeTypes.isEmpty()) {
                var iter = lotTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultLotTimeType = iter.next();
                }
                var lotTimeTypeDetailValue = Objects.requireNonNull(defaultLotTimeType).getLastDetailForUpdate().getLotTimeTypeDetailValue().clone();

                lotTimeTypeDetailValue.setIsDefault(true);
                updateLotTimeTypeFromValue(lotTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(lotTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected LotTimeTypeDescriptionFactory lotTimeTypeDescriptionFactory;

    public LotTimeTypeDescription createLotTimeTypeDescription(LotTimeType lotTimeType, Language language, String description, BasePK createdBy) {
        var lotTimeTypeDescription = lotTimeTypeDescriptionFactory.create(lotTimeType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(lotTimeType.getPrimaryKey(), EventTypes.MODIFY, lotTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return lotTimeTypeDescription;
    }

    private LotTimeTypeDescription getLotTimeTypeDescription(LotTimeType lotTimeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotTimeTypeDescriptions.fields())
                .from(LotTimeTypeDescriptions)
                .where(LotTimeTypeDescriptions.LOT_TIME_TYPE.eq(lotTimeType.getPrimaryKey()),
                        LotTimeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()), LotTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = entityPermission == EntityPermission.READ_ONLY ? baseQuery : baseQuery.forUpdate();

        return lotTimeTypeDescriptionFactory.getEntityFromQuery(entityPermission,
                lotTimeTypeDescriptionFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public LotTimeTypeDescription getLotTimeTypeDescription(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescription(lotTimeType, language, EntityPermission.READ_ONLY);
    }

    public LotTimeTypeDescription getLotTimeTypeDescriptionForUpdate(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescription(lotTimeType, language, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDescriptionValue getLotTimeTypeDescriptionValue(LotTimeTypeDescription lotTimeTypeDescription) {
        return lotTimeTypeDescription == null ? null : lotTimeTypeDescription.getLotTimeTypeDescriptionValue().clone();
    }

    public LotTimeTypeDescriptionValue getLotTimeTypeDescriptionValueForUpdate(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescriptionValue(getLotTimeTypeDescriptionForUpdate(lotTimeType, language));
    }

    private List<LotTimeTypeDescription> getLotTimeTypeDescriptionsByLotTimeType(LotTimeType lotTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.getDslContext()
                    .select(LotTimeTypeDescriptions.fields())
                    .from(LotTimeTypeDescriptions)
                    .join(Languages)
                    .on(LotTimeTypeDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(LotTimeTypeDescriptions.LOT_TIME_TYPE.eq(lotTimeType.getPrimaryKey()),
                            LotTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME);
            case READ_WRITE -> session.getDslContext()
                    .select(LotTimeTypeDescriptions.fields())
                    .from(LotTimeTypeDescriptions)
                    .where(LotTimeTypeDescriptions.LOT_TIME_TYPE.eq(lotTimeType.getPrimaryKey()),
                            LotTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        var sql = query.getSQL() + (entityPermission == EntityPermission.READ_ONLY ? " _LIMIT_" : "");

        return lotTimeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission,
                lotTimeTypeDescriptionFactory.prepareStatement(sql), query.getBindValues().toArray());
    }

    public List<LotTimeTypeDescription> getLotTimeTypeDescriptionsByLotTimeType(LotTimeType lotTimeType) {
        return getLotTimeTypeDescriptionsByLotTimeType(lotTimeType, EntityPermission.READ_ONLY);
    }

    public List<LotTimeTypeDescription> getLotTimeTypeDescriptionsByLotTimeTypeForUpdate(LotTimeType lotTimeType) {
        return getLotTimeTypeDescriptionsByLotTimeType(lotTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestLotTimeTypeDescription(LotTimeType lotTimeType, Language language) {
        String description;
        var lotTimeTypeDescription = getLotTimeTypeDescription(lotTimeType, language);

        if(lotTimeTypeDescription == null && !language.getIsDefault()) {
            lotTimeTypeDescription = getLotTimeTypeDescription(lotTimeType, partyControl.getDefaultLanguage());
        }

        if(lotTimeTypeDescription == null) {
            description = lotTimeType.getLastDetail().getLotTimeTypeName();
        } else {
            description = lotTimeTypeDescription.getDescription();
        }

        return description;
    }

    public LotTimeTypeDescriptionTransfer getLotTimeTypeDescriptionTransfer(UserVisit userVisit, LotTimeTypeDescription lotTimeTypeDescription) {
        return lotTimeTypeDescriptionTransferCache.getTransfer(userVisit, lotTimeTypeDescription);
    }

    public List<LotTimeTypeDescriptionTransfer> getLotTimeTypeDescriptionTransfersByLotTimeType(UserVisit userVisit, LotTimeType lotTimeType) {
        var lotTimeTypeDescriptions = getLotTimeTypeDescriptionsByLotTimeType(lotTimeType);
        List<LotTimeTypeDescriptionTransfer> lotTimeTypeDescriptionTransfers = new ArrayList<>(lotTimeTypeDescriptions.size());

        lotTimeTypeDescriptions.forEach((lotTimeTypeDescription) ->
                lotTimeTypeDescriptionTransfers.add(lotTimeTypeDescriptionTransferCache.getTransfer(userVisit, lotTimeTypeDescription))
        );

        return lotTimeTypeDescriptionTransfers;
    }

    public void updateLotTimeTypeDescriptionFromValue(LotTimeTypeDescriptionValue lotTimeTypeDescriptionValue, BasePK updatedBy) {
        if(lotTimeTypeDescriptionValue.hasBeenModified()) {
            var lotTimeTypeDescription = lotTimeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTimeTypeDescriptionValue.getPrimaryKey());

            lotTimeTypeDescription.setThruTime(session.getStartTime());
            lotTimeTypeDescription.store();

            var lotTimeType = lotTimeTypeDescription.getLotTimeType();
            var language = lotTimeTypeDescription.getLanguage();
            var description = lotTimeTypeDescriptionValue.getDescription();

            lotTimeTypeDescription = lotTimeTypeDescriptionFactory.create(lotTimeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(lotTimeType.getPrimaryKey(), EventTypes.MODIFY, lotTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLotTimeTypeDescription(LotTimeTypeDescription lotTimeTypeDescription, BasePK deletedBy) {
        lotTimeTypeDescription.setThruTime(session.getStartTime());

        sendEvent(lotTimeTypeDescription.getLotTimeTypePK(), EventTypes.MODIFY, lotTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLotTimeTypeDescriptionsByLotTimeType(LotTimeType lotTimeType, BasePK deletedBy) {
        var lotTimeTypeDescriptions = getLotTimeTypeDescriptionsByLotTimeTypeForUpdate(lotTimeType);

        lotTimeTypeDescriptions.forEach((lotTimeTypeDescription) ->
                deleteLotTimeTypeDescription(lotTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Lot Times
    // --------------------------------------------------------------------------------

    @Inject
    protected LotTimeFactory lotTimeFactory;

    public LotTime createLotTime(Lot lot, LotTimeType lotTimeType, Long time, BasePK createdBy) {
        var lotTime = lotTimeFactory.create(lot, lotTimeType, time, session.getStartTime(), Session.MAX_TIME);

        sendEvent(lot.getPrimaryKey(), EventTypes.MODIFY, lotTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return lotTime;
    }

    public long countLotTimesByLot(Lot lot) {
        return session.getDslContext()
                .selectCount()
                .from(LotTimes)
                .where(LotTimes.LOT.eq(lot.getPrimaryKey()), LotTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLotTimesByLotTimeType(LotTimeType lotTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(LotTimes)
                .where(LotTimes.LOT_TIME_TYPE.eq(lotTimeType.getPrimaryKey()), LotTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private LotTime getLotTime(Lot lot, LotTimeType lotTimeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotTimes.fields())
                .from(LotTimes)
                .where(LotTimes.LOT.eq(lot.getPrimaryKey()), LotTimes.LOT_TIME_TYPE.eq(lotTimeType.getPrimaryKey()),
                        LotTimes.THRU_TIME.eq(Session.MAX_TIME));

        var query = entityPermission == EntityPermission.READ_ONLY ? baseQuery : baseQuery.forUpdate();

        return lotTimeFactory.getEntityFromQuery(entityPermission,
                lotTimeFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public LotTime getLotTime(Lot lot, LotTimeType lotTimeType) {
        return getLotTime(lot, lotTimeType, EntityPermission.READ_ONLY);
    }

    public LotTime getLotTimeForUpdate(Lot lot, LotTimeType lotTimeType) {
        return getLotTime(lot, lotTimeType, EntityPermission.READ_WRITE);
    }

    public LotTimeValue getLotTimeValue(LotTime lotTime) {
        return lotTime == null ? null : lotTime.getLotTimeValue().clone();
    }

    public LotTimeValue getLotTimeValueForUpdate(Lot lot, LotTimeType lotTimeType) {
        return getLotTimeValue(getLotTimeForUpdate(lot, lotTimeType));
    }

    private List<LotTime> getLotTimesByLot(Lot lot, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.getDslContext()
                    .select(LotTimes.fields())
                    .from(LotTimes)
                    .join(LotTimeTypes)
                    .on(LotTimes.LOT_TIME_TYPE.eq(LotTimeTypes.LOT_TIME_TYPE))
                    .join(LotTimeTypeDetails).onKey(LOT_TIME_TYPES_ACTIVE_DETAIL_FK)
                    .where(LotTimes.LOT.eq(lot.getPrimaryKey()), LotTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(LotTimeTypeDetails.SORT_ORDER, LotTimeTypeDetails.LOT_TIME_TYPE_NAME);
            case READ_WRITE -> session.getDslContext()
                    .select(LotTimes.fields())
                    .from(LotTimes)
                    .where(LotTimes.LOT.eq(lot.getPrimaryKey()), LotTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        var sql = query.getSQL() + (entityPermission == EntityPermission.READ_ONLY ? " _LIMIT_" : "");

        return lotTimeFactory.getEntitiesFromQuery(entityPermission,
                lotTimeFactory.prepareStatement(sql), query.getBindValues().toArray());
    }

    public List<LotTime> getLotTimesByLot(Lot lot) {
        return getLotTimesByLot(lot, EntityPermission.READ_ONLY);
    }

    public List<LotTime> getLotTimesByLotForUpdate(Lot lot) {
        return getLotTimesByLot(lot, EntityPermission.READ_WRITE);
    }

    private List<LotTime> getLotTimesByLotTimeType(LotTimeType lotTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.getDslContext()
                    .select(LotTimes.fields())
                    .from(LotTimes)
                    .join(Lots)
                    .on(LotTimes.LOT.eq(Lots.LOT))
                    .join(LotDetails)
                    .on(Lots.ACTIVE_DETAIL.eq(LotDetails.LOT_DETAIL))
                    .where(LotTimes.LOT_TIME_TYPE.eq(lotTimeType.getPrimaryKey()), LotTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(LotDetails.LOT_IDENTIFIER);
            case READ_WRITE -> session.getDslContext()
                    .select(LotTimes.fields())
                    .from(LotTimes)
                    .where(LotTimes.LOT_TIME_TYPE.eq(lotTimeType.getPrimaryKey()), LotTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        var sql = query.getSQL() + (entityPermission == EntityPermission.READ_ONLY ? " _LIMIT_" : "");

        return lotTimeFactory.getEntitiesFromQuery(entityPermission,
                lotTimeFactory.prepareStatement(sql), query.getBindValues().toArray());
    }

    public List<LotTime> getLotTimesByLotTimeType(LotTimeType lotTimeType) {
        return getLotTimesByLotTimeType(lotTimeType, EntityPermission.READ_ONLY);
    }

    public List<LotTime> getLotTimesByLotTimeTypeForUpdate(LotTimeType lotTimeType) {
        return getLotTimesByLotTimeType(lotTimeType, EntityPermission.READ_WRITE);
    }

    public LotTimeTransfer getLotTimeTransfer(UserVisit userVisit, LotTime lotTime) {
        return lotTimeTransferCache.getTransfer(userVisit, lotTime);
    }

    public List<LotTimeTransfer> getLotTimeTransfers(UserVisit userVisit, Collection<LotTime> lotTimes) {
        List<LotTimeTransfer> lotTimeTransfers = new ArrayList<>(lotTimes.size());

        lotTimes.forEach((lotTime) ->
                lotTimeTransfers.add(lotTimeTransferCache.getTransfer(userVisit, lotTime))
        );

        return lotTimeTransfers;
    }

    public List<LotTimeTransfer> getLotTimeTransfersByLot(UserVisit userVisit, Lot lot) {
        return getLotTimeTransfers(userVisit, getLotTimesByLot(lot));
    }

    public List<LotTimeTransfer> getLotTimeTransfersByLotTimeType(UserVisit userVisit, LotTimeType lotTimeType) {
        return getLotTimeTransfers(userVisit, getLotTimesByLotTimeType(lotTimeType));
    }

    public void updateLotTimeFromValue(LotTimeValue lotTimeValue, BasePK updatedBy) {
        if(lotTimeValue.hasBeenModified()) {
            var lotTime = lotTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTimeValue.getPrimaryKey());

            lotTime.setThruTime(session.getStartTime());
            lotTime.store();

            var lotPK = lotTime.getLotPK(); // Not updated
            var lotTimeTypePK = lotTime.getLotTimeTypePK(); // Not updated
            var time = lotTimeValue.getTime();

            lotTime = lotTimeFactory.create(lotPK, lotTimeTypePK, time, session.getStartTime(), Session.MAX_TIME);

            sendEvent(lotPK, EventTypes.MODIFY, lotTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLotTime(LotTime lotTime, BasePK deletedBy) {
        lotTime.setThruTime(session.getStartTime());

        sendEvent(lotTime.getLotTimeTypePK(), EventTypes.MODIFY, lotTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLotTimes(List<LotTime> lotTimes, BasePK deletedBy) {
        lotTimes.forEach((lotTime) ->
                deleteLotTime(lotTime, deletedBy)
        );
    }

    public void deleteLotTimesByLot(Lot lot, BasePK deletedBy) {
        deleteLotTimes(getLotTimesByLotForUpdate(lot), deletedBy);
    }

    public void deleteLotTimesByLotTimeType(LotTimeType lotTimeType, BasePK deletedBy) {
        deleteLotTimes(getLotTimesByLotTimeTypeForUpdate(lotTimeType), deletedBy);
    }

}
