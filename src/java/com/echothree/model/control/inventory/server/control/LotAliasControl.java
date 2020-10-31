// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.inventory.common.choice.LotAliasTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.LotAliasTransfer;
import com.echothree.model.control.inventory.common.transfer.LotAliasTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.LotAliasTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.LotAliasTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeTransferCache;
import com.echothree.model.data.inventory.common.pk.LotAliasTypePK;
import com.echothree.model.data.inventory.common.pk.LotPK;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.entity.LotAlias;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDescription;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDetail;
import com.echothree.model.data.inventory.server.factory.LotAliasFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeFactory;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDetailValue;
import com.echothree.model.data.inventory.server.value.LotAliasValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LotAliasControl
        extends BaseInventoryControl {

    /** Creates a new instance of LotAliasControl */
    public LotAliasControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    public LotAliasType createLotAliasType(String lotAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        LotAliasType defaultLotAliasType = getDefaultLotAliasType();
        boolean defaultFound = defaultLotAliasType != null;

        if(defaultFound && isDefault) {
            LotAliasTypeDetailValue defaultLotAliasTypeDetailValue = getDefaultLotAliasTypeDetailValueForUpdate();

            defaultLotAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateLotAliasTypeFromValue(defaultLotAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        LotAliasType lotAliasType = LotAliasTypeFactory.getInstance().create();
        LotAliasTypeDetail lotAliasTypeDetail = LotAliasTypeDetailFactory.getInstance().create(lotAliasType, lotAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        lotAliasType = LotAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, lotAliasType.getPrimaryKey());
        lotAliasType.setActiveDetail(lotAliasTypeDetail);
        lotAliasType.setLastDetail(lotAliasTypeDetail);
        lotAliasType.store();

        sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return lotAliasType;
    }

    private static final Map<EntityPermission, String> getLotAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid " +
                "AND ltaltypdt_lotaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid " +
                "AND ltaltypdt_lotaliastypename = ? " +
                "FOR UPDATE");
        getLotAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAliasType getLotAliasTypeByName(String lotAliasTypeName, EntityPermission entityPermission) {
        return LotAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasTypeByNameQueries,
                lotAliasTypeName);
    }

    public LotAliasType getLotAliasTypeByName(String lotAliasTypeName) {
        return getLotAliasTypeByName(lotAliasTypeName, EntityPermission.READ_ONLY);
    }

    public LotAliasType getLotAliasTypeByNameForUpdate(String lotAliasTypeName) {
        return getLotAliasTypeByName(lotAliasTypeName, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDetailValue getLotAliasTypeDetailValueForUpdate(LotAliasType lotAliasType) {
        return lotAliasType == null? null: lotAliasType.getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();
    }

    public LotAliasTypeDetailValue getLotAliasTypeDetailValueByNameForUpdate(String lotAliasTypeName) {
        return getLotAliasTypeDetailValueForUpdate(getLotAliasTypeByNameForUpdate(lotAliasTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultLotAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid " +
                "AND ltaltypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid " +
                "AND ltaltypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLotAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAliasType getDefaultLotAliasType(EntityPermission entityPermission) {
        return LotAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLotAliasTypeQueries);
    }

    public LotAliasType getDefaultLotAliasType() {
        return getDefaultLotAliasType(EntityPermission.READ_ONLY);
    }

    public LotAliasType getDefaultLotAliasTypeForUpdate() {
        return getDefaultLotAliasType(EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDetailValue getDefaultLotAliasTypeDetailValueForUpdate() {
        return getDefaultLotAliasTypeForUpdate().getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getLotAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid " +
                "ORDER BY ltaltypdt_sortorder, ltaltypdt_lotaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid " +
                "FOR UPDATE");
        getLotAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAliasType> getLotAliasTypes(EntityPermission entityPermission) {
        return LotAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasTypesQueries);
    }

    public List<LotAliasType> getLotAliasTypes() {
        return getLotAliasTypes(EntityPermission.READ_ONLY);
    }

    public List<LotAliasType> getLotAliasTypesForUpdate() {
        return getLotAliasTypes(EntityPermission.READ_WRITE);
    }

    public LotAliasTypeTransfer getLotAliasTypeTransfer(UserVisit userVisit, LotAliasType lotAliasType) {
        return getInventoryTransferCaches(userVisit).getLotAliasTypeTransferCache().getTransfer(lotAliasType);
    }

    public List<LotAliasTypeTransfer> getLotAliasTypeTransfers(UserVisit userVisit) {
        List<LotAliasType> lotAliasTypes = getLotAliasTypes();
        List<LotAliasTypeTransfer> lotAliasTypeTransfers = new ArrayList<>(lotAliasTypes.size());
        LotAliasTypeTransferCache lotAliasTypeTransferCache = getInventoryTransferCaches(userVisit).getLotAliasTypeTransferCache();

        lotAliasTypes.forEach((lotAliasType) ->
                lotAliasTypeTransfers.add(lotAliasTypeTransferCache.getTransfer(lotAliasType))
        );

        return lotAliasTypeTransfers;
    }

    public LotAliasTypeChoicesBean getLotAliasTypeChoices(String defaultLotAliasTypeChoice, Language language,
            boolean allowNullChoice) {
        List<LotAliasType> lotAliasTypes = getLotAliasTypes();
        int size = lotAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLotAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(LotAliasType lotAliasType: lotAliasTypes) {
            LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getLastDetail();

            String label = getBestLotAliasTypeDescription(lotAliasType, language);
            String value = lotAliasTypeDetail.getLotAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultLotAliasTypeChoice != null && defaultLotAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && lotAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LotAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateLotAliasTypeFromValue(LotAliasTypeDetailValue lotAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(lotAliasTypeDetailValue.hasBeenModified()) {
            LotAliasType lotAliasType = LotAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotAliasTypeDetailValue.getLotAliasTypePK());
            LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getActiveDetailForUpdate();

            lotAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            lotAliasTypeDetail.store();

            LotAliasTypePK lotAliasTypePK = lotAliasTypeDetail.getLotAliasTypePK();
            String lotAliasTypeName = lotAliasTypeDetailValue.getLotAliasTypeName();
            String validationPattern = lotAliasTypeDetailValue.getValidationPattern();
            Boolean isDefault = lotAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = lotAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                LotAliasType defaultLotAliasType = getDefaultLotAliasType();
                boolean defaultFound = defaultLotAliasType != null && !defaultLotAliasType.equals(lotAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    LotAliasTypeDetailValue defaultLotAliasTypeDetailValue = getDefaultLotAliasTypeDetailValueForUpdate();

                    defaultLotAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateLotAliasTypeFromValue(defaultLotAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            lotAliasTypeDetail = LotAliasTypeDetailFactory.getInstance().create(lotAliasTypePK, lotAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            lotAliasType.setActiveDetail(lotAliasTypeDetail);
            lotAliasType.setLastDetail(lotAliasTypeDetail);

            sendEventUsingNames(lotAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateLotAliasTypeFromValue(LotAliasTypeDetailValue lotAliasTypeDetailValue, BasePK updatedBy) {
        updateLotAliasTypeFromValue(lotAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        deleteLotAliasesByLotAliasType(lotAliasType, deletedBy);
        deleteLotAliasTypeDescriptionsByLotAliasType(lotAliasType, deletedBy);

        LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getLastDetailForUpdate();
        lotAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        lotAliasType.setActiveDetail(null);
        lotAliasType.store();

        // Check for default, and pick one if necessary
        LotAliasType defaultLotAliasType = getDefaultLotAliasType();
        if(defaultLotAliasType == null) {
            List<LotAliasType> lotAliasTypes = getLotAliasTypesForUpdate();

            if(!lotAliasTypes.isEmpty()) {
                Iterator<LotAliasType> iter = lotAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultLotAliasType = iter.next();
                }
                LotAliasTypeDetailValue lotAliasTypeDetailValue = defaultLotAliasType.getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();

                lotAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateLotAliasTypeFromValue(lotAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteLotAliasTypes(List<LotAliasType> lotAliasTypes, BasePK deletedBy) {
        lotAliasTypes.stream().forEach((lotAliasType) -> {
            deleteLotAliasType(lotAliasType, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public LotAliasTypeDescription createLotAliasTypeDescription(LotAliasType lotAliasType, Language language, String description, BasePK createdBy) {
        LotAliasTypeDescription lotAliasTypeDescription = LotAliasTypeDescriptionFactory.getInstance().create(lotAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), lotAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return lotAliasTypeDescription;
    }

    private static final Map<EntityPermission, String> getLotAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_lang_languageid = ? AND ltaltypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_lang_languageid = ? AND ltaltypd_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAliasTypeDescription getLotAliasTypeDescription(LotAliasType lotAliasType, Language language, EntityPermission entityPermission) {
        return LotAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasTypeDescriptionQueries,
                lotAliasType, language, Session.MAX_TIME);
    }

    public LotAliasTypeDescription getLotAliasTypeDescription(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescription(lotAliasType, language, EntityPermission.READ_ONLY);
    }

    public LotAliasTypeDescription getLotAliasTypeDescriptionForUpdate(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescription(lotAliasType, language, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDescriptionValue getLotAliasTypeDescriptionValue(LotAliasTypeDescription lotAliasTypeDescription) {
        return lotAliasTypeDescription == null? null: lotAliasTypeDescription.getLotAliasTypeDescriptionValue().clone();
    }

    public LotAliasTypeDescriptionValue getLotAliasTypeDescriptionValueForUpdate(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescriptionValue(getLotAliasTypeDescriptionForUpdate(lotAliasType, language));
    }

    private static final Map<EntityPermission, String> getLotAliasTypeDescriptionsByLotAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions, languages " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_thrutime = ? AND ltaltypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasTypeDescriptionsByLotAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType, EntityPermission entityPermission) {
        return LotAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasTypeDescriptionsByLotAliasTypeQueries,
                lotAliasType, Session.MAX_TIME);
    }

    public List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType) {
        return getLotAliasTypeDescriptionsByLotAliasType(lotAliasType, EntityPermission.READ_ONLY);
    }

    public List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasTypeForUpdate(LotAliasType lotAliasType) {
        return getLotAliasTypeDescriptionsByLotAliasType(lotAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestLotAliasTypeDescription(LotAliasType lotAliasType, Language language) {
        String description;
        LotAliasTypeDescription lotAliasTypeDescription = getLotAliasTypeDescription(lotAliasType, language);

        if(lotAliasTypeDescription == null && !language.getIsDefault()) {
            lotAliasTypeDescription = getLotAliasTypeDescription(lotAliasType, getPartyControl().getDefaultLanguage());
        }

        if(lotAliasTypeDescription == null) {
            description = lotAliasType.getLastDetail().getLotAliasTypeName();
        } else {
            description = lotAliasTypeDescription.getDescription();
        }

        return description;
    }

    public LotAliasTypeDescriptionTransfer getLotAliasTypeDescriptionTransfer(UserVisit userVisit, LotAliasTypeDescription lotAliasTypeDescription) {
        return getInventoryTransferCaches(userVisit).getLotAliasTypeDescriptionTransferCache().getTransfer(lotAliasTypeDescription);
    }

    public List<LotAliasTypeDescriptionTransfer> getLotAliasTypeDescriptionTransfersByLotAliasType(UserVisit userVisit, LotAliasType lotAliasType) {
        List<LotAliasTypeDescription> lotAliasTypeDescriptions = getLotAliasTypeDescriptionsByLotAliasType(lotAliasType);
        List<LotAliasTypeDescriptionTransfer> lotAliasTypeDescriptionTransfers = new ArrayList<>(lotAliasTypeDescriptions.size());
        LotAliasTypeDescriptionTransferCache lotAliasTypeDescriptionTransferCache = getInventoryTransferCaches(userVisit).getLotAliasTypeDescriptionTransferCache();

        lotAliasTypeDescriptions.forEach((lotAliasTypeDescription) ->
                lotAliasTypeDescriptionTransfers.add(lotAliasTypeDescriptionTransferCache.getTransfer(lotAliasTypeDescription))
        );

        return lotAliasTypeDescriptionTransfers;
    }

    public void updateLotAliasTypeDescriptionFromValue(LotAliasTypeDescriptionValue lotAliasTypeDescriptionValue, BasePK updatedBy) {
        if(lotAliasTypeDescriptionValue.hasBeenModified()) {
            LotAliasTypeDescription lotAliasTypeDescription = LotAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     lotAliasTypeDescriptionValue.getPrimaryKey());

            lotAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            lotAliasTypeDescription.store();

            LotAliasType lotAliasType = lotAliasTypeDescription.getLotAliasType();
            Language language = lotAliasTypeDescription.getLanguage();
            String description = lotAliasTypeDescriptionValue.getDescription();

            lotAliasTypeDescription = LotAliasTypeDescriptionFactory.getInstance().create(lotAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), lotAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLotAliasTypeDescription(LotAliasTypeDescription lotAliasTypeDescription, BasePK deletedBy) {
        lotAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(lotAliasTypeDescription.getLotAliasTypePK(), EventTypes.MODIFY.name(), lotAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        List<LotAliasTypeDescription> lotAliasTypeDescriptions = getLotAliasTypeDescriptionsByLotAliasTypeForUpdate(lotAliasType);

        lotAliasTypeDescriptions.stream().forEach((lotAliasTypeDescription) -> {
            deleteLotAliasTypeDescription(lotAliasTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    public LotAlias createLotAlias(Lot lot, LotAliasType lotAliasType, String alias, BasePK createdBy) {
        LotAlias lotAlias = LotAliasFactory.getInstance().create(lot, lotAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(lot.getPrimaryKey(), EventTypes.MODIFY.name(), lotAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return lotAlias;
    }

    private static final Map<EntityPermission, String> getLotAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_lt_lotid = ? AND ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_lt_lotid = ? AND ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAlias getLotAlias(Lot lot, LotAliasType lotAliasType, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasQueries,
                lot, lotAliasType, Session.MAX_TIME);
    }

    public LotAlias getLotAlias(Lot lot, LotAliasType lotAliasType) {
        return getLotAlias(lot, lotAliasType, EntityPermission.READ_ONLY);
    }

    public LotAlias getLotAliasForUpdate(Lot lot, LotAliasType lotAliasType) {
        return getLotAlias(lot, lotAliasType, EntityPermission.READ_WRITE);
    }

    public LotAliasValue getLotAliasValue(LotAlias lotAlias) {
        return lotAlias == null? null: lotAlias.getLotAliasValue().clone();
    }

    public LotAliasValue getLotAliasValueForUpdate(Lot lot, LotAliasType lotAliasType) {
        return getLotAliasValue(getLotAliasForUpdate(lot, lotAliasType));
    }

    private static final Map<EntityPermission, String> getLotAliasByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_alias = ? AND ltal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_alias = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAlias getLotAliasByAlias(LotAliasType lotAliasType, String alias, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasByAliasQueries, lotAliasType, alias, Session.MAX_TIME);
    }

    public LotAlias getLotAliasByAlias(LotAliasType lotAliasType, String alias) {
        return getLotAliasByAlias(lotAliasType, alias, EntityPermission.READ_ONLY);
    }

    public LotAlias getLotAliasByAliasForUpdate(LotAliasType lotAliasType, String alias) {
        return getLotAliasByAlias(lotAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotAliasesByLotQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases, lotaliastypes, lotaliastypedetails " +
                "WHERE ltal_lt_lotid = ? AND ltal_thrutime = ? " +
                "AND ltal_ltaltyp_lotaliastypeid = ltaltyp_lotaliastypeid AND ltaltyp_lastdetailid = ltaltypdt_lotaliastypedetailid" +
                "ORDER BY ltaltypdt_sortorder, ltaltypdt_lotaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_lt_lotid = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasesByLotQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAlias> getLotAliasesByLot(Lot lot, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasesByLotQueries,
                lot, Session.MAX_TIME);
    }

    public List<LotAlias> getLotAliasesByLot(Lot lot) {
        return getLotAliasesByLot(lot, EntityPermission.READ_ONLY);
    }

    public List<LotAlias> getLotAliasesByLotForUpdate(Lot lot) {
        return getLotAliasesByLot(lot, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotAliasesByLotAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases, lotes, lotdetails " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ? " +
                "AND ltal_lt_lotid = lt_lotid AND lt_lastdetailid = ltdt_lotdetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasesByLotAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAlias> getLotAliasesByLotAliasType(LotAliasType lotAliasType, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasesByLotAliasTypeQueries,
                lotAliasType, Session.MAX_TIME);
    }

    public List<LotAlias> getLotAliasesByLotAliasType(LotAliasType lotAliasType) {
        return getLotAliasesByLotAliasType(lotAliasType, EntityPermission.READ_ONLY);
    }

    public List<LotAlias> getLotAliasesByLotAliasTypeForUpdate(LotAliasType lotAliasType) {
        return getLotAliasesByLotAliasType(lotAliasType, EntityPermission.READ_WRITE);
    }

    public LotAliasTransfer getLotAliasTransfer(UserVisit userVisit, LotAlias lotAlias) {
        return getInventoryTransferCaches(userVisit).getLotAliasTransferCache().getTransfer(lotAlias);
    }

    public List<LotAliasTransfer> getLotAliasTransfersByLot(UserVisit userVisit, Lot lot) {
        List<LotAlias> lotaliases = getLotAliasesByLot(lot);
        List<LotAliasTransfer> lotAliasTransfers = new ArrayList<>(lotaliases.size());
        LotAliasTransferCache lotAliasTransferCache = getInventoryTransferCaches(userVisit).getLotAliasTransferCache();

        lotaliases.forEach((lotAlias) ->
                lotAliasTransfers.add(lotAliasTransferCache.getTransfer(lotAlias))
        );

        return lotAliasTransfers;
    }

    public void updateLotAliasFromValue(LotAliasValue lotAliasValue, BasePK updatedBy) {
        if(lotAliasValue.hasBeenModified()) {
            LotAlias lotAlias = LotAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, lotAliasValue.getPrimaryKey());

            lotAlias.setThruTime(session.START_TIME_LONG);
            lotAlias.store();

            LotPK lotPK = lotAlias.getLotPK();
            LotAliasTypePK lotAliasTypePK = lotAlias.getLotAliasTypePK();
            String alias  = lotAliasValue.getAlias();

            lotAlias = LotAliasFactory.getInstance().create(lotPK, lotAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(lotPK, EventTypes.MODIFY.name(), lotAlias.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLotAlias(LotAlias lotAlias, BasePK deletedBy) {
        lotAlias.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(lotAlias.getLotPK(), EventTypes.MODIFY.name(), lotAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLotAliasesByLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        List<LotAlias> lotaliases = getLotAliasesByLotAliasTypeForUpdate(lotAliasType);

        lotaliases.stream().forEach((lotAlias) -> {
            deleteLotAlias(lotAlias, deletedBy);
        });
    }

    public void deleteLotAliasesByLot(Lot lot, BasePK deletedBy) {
        List<LotAlias> lotaliases = getLotAliasesByLotForUpdate(lot);

        lotaliases.stream().forEach((lotAlias) -> {
            deleteLotAlias(lotAlias, deletedBy);
        });
    }

}
