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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.choice.LotTimeTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.LotTimeTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTypeTransfer;
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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LotTimeControl
        extends BaseInventoryControl {

    /** Creates a new instance of LotTimeControl */
    protected LotTimeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

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

        var lotTimeType = LotTimeTypeFactory.getInstance().create();
        var lotTimeTypeDetail = LotTimeTypeDetailFactory.getInstance().create(lotTimeType, lotTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        lotTimeType = LotTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                lotTimeType.getPrimaryKey());
        lotTimeType.setActiveDetail(lotTimeTypeDetail);
        lotTimeType.setLastDetail(lotTimeTypeDetail);
        lotTimeType.store();

        sendEvent(lotTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return lotTimeType;
    }

    private static final Map<EntityPermission, String> getLotTimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lottimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lottimetypename = ? " +
                "FOR UPDATE");
        getLotTimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTimeType getLotTimeTypeByName(String lotTimeTypeName, EntityPermission entityPermission) {
        return LotTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getLotTimeTypeByNameQueries,
                lotTimeTypeName);
    }

    public LotTimeType getLotTimeTypeByName(String lotTimeTypeName) {
        return getLotTimeTypeByName(lotTimeTypeName, EntityPermission.READ_ONLY);
    }

    public LotTimeType getLotTimeTypeByNameForUpdate(String lotTimeTypeName) {
        return getLotTimeTypeByName(lotTimeTypeName, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDetailValue getLotTimeTypeDetailValueForUpdate(LotTimeType lotTimeType) {
        return lotTimeType == null? null: lotTimeType.getLastDetailForUpdate().getLotTimeTypeDetailValue().clone();
    }

    public LotTimeTypeDetailValue getLotTimeTypeDetailValueByNameForUpdate(String lotTimeTypeName) {
        return getLotTimeTypeDetailValueForUpdate(getLotTimeTypeByNameForUpdate(lotTimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultLotTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLotTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTimeType getDefaultLotTimeType(EntityPermission entityPermission) {
        return LotTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLotTimeTypeQueries);
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

    private static final Map<EntityPermission, String> getLotTimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "ORDER BY lttimtypdt_sortorder, lttimtypdt_lottimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "FOR UPDATE");
        getLotTimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTimeType> getLotTimeTypes(EntityPermission entityPermission) {
        return LotTimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimeTypesQueries);
    }

    public List<LotTimeType> getLotTimeTypes() {
        return getLotTimeTypes(EntityPermission.READ_ONLY);
    }

    public List<LotTimeType> getLotTimeTypesForUpdate() {
        return getLotTimeTypes(EntityPermission.READ_WRITE);
    }

    public LotTimeTypeTransfer getLotTimeTypeTransfer(UserVisit userVisit, LotTimeType lotTimeType) {
        return getInventoryTransferCaches(userVisit).getLotTimeTypeTransferCache().getTransfer(lotTimeType);
    }

    public List<LotTimeTypeTransfer> getLotTimeTypeTransfers(UserVisit userVisit) {
        var lotTimeTypes = getLotTimeTypes();
        List<LotTimeTypeTransfer> lotTimeTypeTransfers = new ArrayList<>(lotTimeTypes.size());
        var lotTimeTypeTransferCache = getInventoryTransferCaches(userVisit).getLotTimeTypeTransferCache();

        lotTimeTypes.forEach((lotTimeType) ->
                lotTimeTypeTransfers.add(lotTimeTypeTransferCache.getTransfer(lotTimeType))
        );

        return lotTimeTypeTransfers;
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

            labels.add(label == null? value: label);
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
            var lotTimeType = LotTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     lotTimeTypeDetailValue.getLotTimeTypePK());
            var lotTimeTypeDetail = lotTimeType.getActiveDetailForUpdate();

            lotTimeTypeDetail.setThruTime(session.START_TIME_LONG);
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

            lotTimeTypeDetail = LotTimeTypeDetailFactory.getInstance().create(lotTimeTypePK, lotTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        lotTimeTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public LotTimeTypeDescription createLotTimeTypeDescription(LotTimeType lotTimeType, Language language, String description, BasePK createdBy) {
        var lotTimeTypeDescription = LotTimeTypeDescriptionFactory.getInstance().create(lotTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(lotTimeType.getPrimaryKey(), EventTypes.MODIFY, lotTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return lotTimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getLotTimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_lang_languageid = ? AND lttimtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_lang_languageid = ? AND lttimtypd_thrutime = ? " +
                "FOR UPDATE");
        getLotTimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTimeTypeDescription getLotTimeTypeDescription(LotTimeType lotTimeType, Language language, EntityPermission entityPermission) {
        return LotTimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLotTimeTypeDescriptionQueries,
                lotTimeType, language, Session.MAX_TIME);
    }

    public LotTimeTypeDescription getLotTimeTypeDescription(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescription(lotTimeType, language, EntityPermission.READ_ONLY);
    }

    public LotTimeTypeDescription getLotTimeTypeDescriptionForUpdate(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescription(lotTimeType, language, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDescriptionValue getLotTimeTypeDescriptionValue(LotTimeTypeDescription lotTimeTypeDescription) {
        return lotTimeTypeDescription == null? null: lotTimeTypeDescription.getLotTimeTypeDescriptionValue().clone();
    }

    public LotTimeTypeDescriptionValue getLotTimeTypeDescriptionValueForUpdate(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescriptionValue(getLotTimeTypeDescriptionForUpdate(lotTimeType, language));
    }

    private static final Map<EntityPermission, String> getLotTimeTypeDescriptionsByLotTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions, languages " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_thrutime = ? AND lttimtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_thrutime = ? " +
                "FOR UPDATE");
        getLotTimeTypeDescriptionsByLotTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTimeTypeDescription> getLotTimeTypeDescriptionsByLotTimeType(LotTimeType lotTimeType, EntityPermission entityPermission) {
        return LotTimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimeTypeDescriptionsByLotTimeTypeQueries,
                lotTimeType, Session.MAX_TIME);
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
        return getInventoryTransferCaches(userVisit).getLotTimeTypeDescriptionTransferCache().getTransfer(lotTimeTypeDescription);
    }

    public List<LotTimeTypeDescriptionTransfer> getLotTimeTypeDescriptionTransfersByLotTimeType(UserVisit userVisit, LotTimeType lotTimeType) {
        var lotTimeTypeDescriptions = getLotTimeTypeDescriptionsByLotTimeType(lotTimeType);
        List<LotTimeTypeDescriptionTransfer> lotTimeTypeDescriptionTransfers = new ArrayList<>(lotTimeTypeDescriptions.size());
        var lotTimeTypeDescriptionTransferCache = getInventoryTransferCaches(userVisit).getLotTimeTypeDescriptionTransferCache();

        lotTimeTypeDescriptions.forEach((lotTimeTypeDescription) ->
                lotTimeTypeDescriptionTransfers.add(lotTimeTypeDescriptionTransferCache.getTransfer(lotTimeTypeDescription))
        );

        return lotTimeTypeDescriptionTransfers;
    }

    public void updateLotTimeTypeDescriptionFromValue(LotTimeTypeDescriptionValue lotTimeTypeDescriptionValue, BasePK updatedBy) {
        if(lotTimeTypeDescriptionValue.hasBeenModified()) {
            var lotTimeTypeDescription = LotTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTimeTypeDescriptionValue.getPrimaryKey());

            lotTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            lotTimeTypeDescription.store();

            var lotTimeType = lotTimeTypeDescription.getLotTimeType();
            var language = lotTimeTypeDescription.getLanguage();
            var description = lotTimeTypeDescriptionValue.getDescription();

            lotTimeTypeDescription = LotTimeTypeDescriptionFactory.getInstance().create(lotTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(lotTimeType.getPrimaryKey(), EventTypes.MODIFY, lotTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLotTimeTypeDescription(LotTimeTypeDescription lotTimeTypeDescription, BasePK deletedBy) {
        lotTimeTypeDescription.setThruTime(session.START_TIME_LONG);

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

    public LotTime createLotTime(Lot lot, LotTimeType lotTimeType, Long time, BasePK createdBy) {
        var lotTime = LotTimeFactory.getInstance().create(lot, lotTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(lot.getPrimaryKey(), EventTypes.MODIFY, lotTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return lotTime;
    }

    public long countLotTimesByLot(Lot lot) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_thrutime = ?",
                lot, Session.MAX_TIME_LONG);
    }

    public long countLotTimesByLotTimeType(LotTimeType lotTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM lottimes " +
                "WHERE lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ?",
                lotTimeType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getLotTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ? " +
                "FOR UPDATE");
        getLotTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTime getLotTime(Lot lot, LotTimeType lotTimeType, EntityPermission entityPermission) {
        return LotTimeFactory.getInstance().getEntityFromQuery(entityPermission, getLotTimeQueries, lot, lotTimeType, Session.MAX_TIME);
    }

    public LotTime getLotTime(Lot lot, LotTimeType lotTimeType) {
        return getLotTime(lot, lotTimeType, EntityPermission.READ_ONLY);
    }

    public LotTime getLotTimeForUpdate(Lot lot, LotTimeType lotTimeType) {
        return getLotTime(lot, lotTimeType, EntityPermission.READ_WRITE);
    }

    public LotTimeValue getLotTimeValue(LotTime lotTime) {
        return lotTime == null? null: lotTime.getLotTimeValue().clone();
    }

    public LotTimeValue getLotTimeValueForUpdate(Lot lot, LotTimeType lotTimeType) {
        return getLotTimeValue(getLotTimeForUpdate(lot, lotTimeType));
    }

    private static final Map<EntityPermission, String> getLotTimesByLotQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimes, lottimetypes, lottimetypedetails " +
                "WHERE lttim_lt_lotid = ? AND lttim_thrutime = ? " +
                "AND lttim_lttimtyp_lottimetypeid = lttimtyp_lottimetypeid AND lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "ORDER BY lttimtypdt_sortorder, lttimtypdt_lottimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_thrutime = ? " +
                "FOR UPDATE");
        getLotTimesByLotQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTime> getLotTimesByLot(Lot lot, EntityPermission entityPermission) {
        return LotTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimesByLotQueries, lot, Session.MAX_TIME);
    }

    public List<LotTime> getLotTimesByLot(Lot lot) {
        return getLotTimesByLot(lot, EntityPermission.READ_ONLY);
    }

    public List<LotTime> getLotTimesByLotForUpdate(Lot lot) {
        return getLotTimesByLot(lot, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotTimesByLotTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimes, lots, lotdetails " +
                "WHERE lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ? " +
                "AND lttim_lt_lotid = lttim_lt_lotid AND lt_activedetailid = ltdt_lotdetailid " +
                "ORDER BY ltdt_lotname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ? " +
                "FOR UPDATE");
        getLotTimesByLotTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTime> getLotTimesByLotTimeType(LotTimeType lotTimeType, EntityPermission entityPermission) {
        return LotTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimesByLotTimeTypeQueries, lotTimeType, Session.MAX_TIME);
    }

    public List<LotTime> getLotTimesByLotTimeType(LotTimeType lotTimeType) {
        return getLotTimesByLotTimeType(lotTimeType, EntityPermission.READ_ONLY);
    }

    public List<LotTime> getLotTimesByLotTimeTypeForUpdate(LotTimeType lotTimeType) {
        return getLotTimesByLotTimeType(lotTimeType, EntityPermission.READ_WRITE);
    }

    public LotTimeTransfer getLotTimeTransfer(UserVisit userVisit, LotTime lotTime) {
        return getInventoryTransferCaches(userVisit).getLotTimeTransferCache().getTransfer(lotTime);
    }

    public List<LotTimeTransfer> getLotTimeTransfers(UserVisit userVisit, Collection<LotTime> lotTimes) {
        List<LotTimeTransfer> lotTimeTransfers = new ArrayList<>(lotTimes.size());
        var lotTimeTransferCache = getInventoryTransferCaches(userVisit).getLotTimeTransferCache();

        lotTimes.forEach((lotTime) ->
                lotTimeTransfers.add(lotTimeTransferCache.getTransfer(lotTime))
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
            var lotTime = LotTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTimeValue.getPrimaryKey());

            lotTime.setThruTime(session.START_TIME_LONG);
            lotTime.store();

            var lotPK = lotTime.getLotPK(); // Not updated
            var lotTimeTypePK = lotTime.getLotTimeTypePK(); // Not updated
            var time = lotTimeValue.getTime();

            lotTime = LotTimeFactory.getInstance().create(lotPK, lotTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(lotPK, EventTypes.MODIFY, lotTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLotTime(LotTime lotTime, BasePK deletedBy) {
        lotTime.setThruTime(session.START_TIME_LONG);

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
