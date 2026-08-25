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
import com.echothree.model.control.inventory.common.choice.LotAliasTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.LotAliasTransfer;
import com.echothree.model.control.inventory.common.transfer.LotAliasTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.LotAliasTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.LotAliasTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.LotAliasTypePK;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.entity.LotAlias;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDescription;
import com.echothree.model.data.inventory.server.factory.LotAliasFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeFactory;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDetailValue;
import com.echothree.model.data.inventory.server.value.LotAliasValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.LOT_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.LotAliasTypeDescriptions.LotAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.LotAliasTypeDetails.LotAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.LotAliasTypes.LotAliasTypes;
import static com.echothree.model.jooq.server.tables.inventory.LotAliases.LotAliases;
import static com.echothree.model.jooq.server.tables.inventory.LotDetails.LotDetails;
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
public class LotAliasControl
        extends BaseModelControl {

    @Inject
    LotAliasTypeTransferCache lotAliasTypeTransferCache;

    @Inject
    LotAliasTypeDescriptionTransferCache lotAliasTypeDescriptionTransferCache;

    @Inject
    LotAliasTransferCache lotAliasTransferCache;

    /**
     * Creates a new instance of LotAliasControl
     */
    protected LotAliasControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected LotAliasTypeFactory lotAliasTypeFactory;

    @Inject
    protected LotAliasTypeDetailFactory lotAliasTypeDetailFactory;

    public LotAliasType createLotAliasType(String lotAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultLotAliasType = getDefaultLotAliasType();
        var defaultFound = defaultLotAliasType != null;

        if(defaultFound && isDefault) {
            var defaultLotAliasTypeDetailValue = getDefaultLotAliasTypeDetailValueForUpdate();

            defaultLotAliasTypeDetailValue.setIsDefault(false);
            updateLotAliasTypeFromValue(defaultLotAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var lotAliasType = lotAliasTypeFactory.create();
        var lotAliasTypeDetail = lotAliasTypeDetailFactory.create(lotAliasType, lotAliasTypeName,
                validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        lotAliasType = lotAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, lotAliasType.getPrimaryKey());
        lotAliasType.setActiveDetail(lotAliasTypeDetail);
        lotAliasType.setLastDetail(lotAliasTypeDetail);
        lotAliasType.store();

        sendEvent(lotAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return lotAliasType;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.LotAliasType
     */
    public LotAliasType getLotAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LotAliasTypePK(entityInstance.getEntityUniqueId());

        return lotAliasTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public LotAliasType getLotAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getLotAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LotAliasType getLotAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLotAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLotAliasTypes() {
        return session.getDslContext()
                .selectCount()
                .from(LotAliasTypes)
                .join(LotAliasTypeDetails).onKey(LOT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private LotAliasType getLotAliasTypeByName(String lotAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotAliasTypes.fields())
                .from(LotAliasTypes)
                .join(LotAliasTypeDetails).onKey(LOT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(LotAliasTypeDetails.LOT_ALIAS_TYPE_NAME.eq(lotAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return lotAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public LotAliasType getLotAliasTypeByName(String lotAliasTypeName) {
        return getLotAliasTypeByName(lotAliasTypeName, EntityPermission.READ_ONLY);
    }

    public LotAliasType getLotAliasTypeByNameForUpdate(String lotAliasTypeName) {
        return getLotAliasTypeByName(lotAliasTypeName, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDetailValue getLotAliasTypeDetailValueForUpdate(LotAliasType lotAliasType) {
        return lotAliasType == null ? null : lotAliasType.getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();
    }

    public LotAliasTypeDetailValue getLotAliasTypeDetailValueByNameForUpdate(String lotAliasTypeName) {
        return getLotAliasTypeDetailValueForUpdate(getLotAliasTypeByNameForUpdate(lotAliasTypeName));
    }

    private LotAliasType getDefaultLotAliasType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotAliasTypes.fields())
                .from(LotAliasTypes)
                .join(LotAliasTypeDetails).onKey(LOT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(LotAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return lotAliasTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<LotAliasType> getLotAliasTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotAliasTypes.fields())
                .from(LotAliasTypes)
                .join(LotAliasTypeDetails).onKey(LOT_ALIAS_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LotAliasTypeDetails.SORT_ORDER, LotAliasTypeDetails.LOT_ALIAS_TYPE_NAME),
                    LotAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return lotAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LotAliasType> getLotAliasTypes() {
        return getLotAliasTypes(EntityPermission.READ_ONLY);
    }

    public List<LotAliasType> getLotAliasTypesForUpdate() {
        return getLotAliasTypes(EntityPermission.READ_WRITE);
    }

    public LotAliasTypeTransfer getLotAliasTypeTransfer(UserVisit userVisit, LotAliasType lotAliasType) {
        return lotAliasTypeTransferCache.getTransfer(userVisit, lotAliasType);
    }

    public List<LotAliasTypeTransfer> getLotAliasTypeTransfers(UserVisit userVisit, Collection<LotAliasType> lotAliasTypes) {
        List<LotAliasTypeTransfer> lotAliasTypeTransfers = new ArrayList<>(lotAliasTypes.size());

        lotAliasTypes.forEach((lotAliasType) ->
                lotAliasTypeTransfers.add(lotAliasTypeTransferCache.getTransfer(userVisit, lotAliasType))
        );

        return lotAliasTypeTransfers;
    }

    public List<LotAliasTypeTransfer> getLotAliasTypeTransfers(UserVisit userVisit) {
        return getLotAliasTypeTransfers(userVisit, getLotAliasTypes());
    }

    public LotAliasTypeChoicesBean getLotAliasTypeChoices(String defaultLotAliasTypeChoice, Language language,
            boolean allowNullChoice) {
        var lotAliasTypes = getLotAliasTypes();
        var size = lotAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLotAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var lotAliasType : lotAliasTypes) {
            var lotAliasTypeDetail = lotAliasType.getLastDetail();

            var label = getBestLotAliasTypeDescription(lotAliasType, language);
            var value = lotAliasTypeDetail.getLotAliasTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultLotAliasTypeChoice != null && defaultLotAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && lotAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LotAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateLotAliasTypeFromValue(LotAliasTypeDetailValue lotAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(lotAliasTypeDetailValue.hasBeenModified()) {
            var lotAliasType = lotAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    lotAliasTypeDetailValue.getLotAliasTypePK());
            var lotAliasTypeDetail = lotAliasType.getActiveDetailForUpdate();

            lotAliasTypeDetail.setThruTime(session.getStartTime());
            lotAliasTypeDetail.store();

            var lotAliasTypePK = lotAliasTypeDetail.getLotAliasTypePK();
            var lotAliasTypeName = lotAliasTypeDetailValue.getLotAliasTypeName();
            var validationPattern = lotAliasTypeDetailValue.getValidationPattern();
            var isDefault = lotAliasTypeDetailValue.getIsDefault();
            var sortOrder = lotAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultLotAliasType = getDefaultLotAliasType();
                var defaultFound = defaultLotAliasType != null && !defaultLotAliasType.equals(lotAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLotAliasTypeDetailValue = getDefaultLotAliasTypeDetailValueForUpdate();

                    defaultLotAliasTypeDetailValue.setIsDefault(false);
                    updateLotAliasTypeFromValue(defaultLotAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            lotAliasTypeDetail = lotAliasTypeDetailFactory.create(lotAliasTypePK, lotAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            lotAliasType.setActiveDetail(lotAliasTypeDetail);
            lotAliasType.setLastDetail(lotAliasTypeDetail);

            sendEvent(lotAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateLotAliasTypeFromValue(LotAliasTypeDetailValue lotAliasTypeDetailValue, BasePK updatedBy) {
        updateLotAliasTypeFromValue(lotAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        deleteLotAliasesByLotAliasType(lotAliasType, deletedBy);
        deleteLotAliasTypeDescriptionsByLotAliasType(lotAliasType, deletedBy);

        var lotAliasTypeDetail = lotAliasType.getLastDetailForUpdate();
        lotAliasTypeDetail.setThruTime(session.getStartTime());
        lotAliasType.setActiveDetail(null);
        lotAliasType.store();

        // Check for default, and pick one if necessary
        var defaultLotAliasType = getDefaultLotAliasType();
        if(defaultLotAliasType == null) {
            var lotAliasTypes = getLotAliasTypesForUpdate();

            if(!lotAliasTypes.isEmpty()) {
                var iter = lotAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultLotAliasType = iter.next();
                }
                var lotAliasTypeDetailValue = Objects.requireNonNull(defaultLotAliasType).getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();

                lotAliasTypeDetailValue.setIsDefault(true);
                updateLotAliasTypeFromValue(lotAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(lotAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteLotAliasTypes(List<LotAliasType> lotAliasTypes, BasePK deletedBy) {
        lotAliasTypes.forEach((lotAliasType) ->
                deleteLotAliasType(lotAliasType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected LotAliasTypeDescriptionFactory lotAliasTypeDescriptionFactory;

    public LotAliasTypeDescription createLotAliasTypeDescription(LotAliasType lotAliasType, Language language, String description, BasePK createdBy) {
        var lotAliasTypeDescription = lotAliasTypeDescriptionFactory.create(lotAliasType, language,
                description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(lotAliasType.getPrimaryKey(), EventTypes.MODIFY, lotAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return lotAliasTypeDescription;
    }

    private LotAliasTypeDescription getLotAliasTypeDescription(LotAliasType lotAliasType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotAliasTypeDescriptions.fields())
                .from(LotAliasTypeDescriptions)
                .where(LotAliasTypeDescriptions.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()),
                        LotAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()), LotAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return lotAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public LotAliasTypeDescription getLotAliasTypeDescription(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescription(lotAliasType, language, EntityPermission.READ_ONLY);
    }

    public LotAliasTypeDescription getLotAliasTypeDescriptionForUpdate(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescription(lotAliasType, language, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDescriptionValue getLotAliasTypeDescriptionValue(LotAliasTypeDescription lotAliasTypeDescription) {
        return lotAliasTypeDescription == null ? null : lotAliasTypeDescription.getLotAliasTypeDescriptionValue().clone();
    }

    public LotAliasTypeDescriptionValue getLotAliasTypeDescriptionValueForUpdate(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescriptionValue(getLotAliasTypeDescriptionForUpdate(lotAliasType, language));
    }

    private List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LotAliasTypeDescriptions.fields())
                    .from(LotAliasTypeDescriptions)
                    .join(Languages)
                    .on(LotAliasTypeDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(LotAliasTypeDescriptions.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()),
                            LotAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    LotAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LotAliasTypeDescriptions.fields())
                    .from(LotAliasTypeDescriptions)
                    .where(LotAliasTypeDescriptions.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()),
                            LotAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return lotAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType) {
        return getLotAliasTypeDescriptionsByLotAliasType(lotAliasType, EntityPermission.READ_ONLY);
    }

    public List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasTypeForUpdate(LotAliasType lotAliasType) {
        return getLotAliasTypeDescriptionsByLotAliasType(lotAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestLotAliasTypeDescription(LotAliasType lotAliasType, Language language) {
        String description;
        var lotAliasTypeDescription = getLotAliasTypeDescription(lotAliasType, language);

        if(lotAliasTypeDescription == null && !language.getIsDefault()) {
            lotAliasTypeDescription = getLotAliasTypeDescription(lotAliasType, partyControl.getDefaultLanguage());
        }

        if(lotAliasTypeDescription == null) {
            description = lotAliasType.getLastDetail().getLotAliasTypeName();
        } else {
            description = lotAliasTypeDescription.getDescription();
        }

        return description;
    }

    public LotAliasTypeDescriptionTransfer getLotAliasTypeDescriptionTransfer(UserVisit userVisit, LotAliasTypeDescription lotAliasTypeDescription) {
        return lotAliasTypeDescriptionTransferCache.getTransfer(userVisit, lotAliasTypeDescription);
    }

    public List<LotAliasTypeDescriptionTransfer> getLotAliasTypeDescriptionTransfersByLotAliasType(UserVisit userVisit, LotAliasType lotAliasType) {
        var lotAliasTypeDescriptions = getLotAliasTypeDescriptionsByLotAliasType(lotAliasType);
        List<LotAliasTypeDescriptionTransfer> lotAliasTypeDescriptionTransfers = new ArrayList<>(lotAliasTypeDescriptions.size());

        lotAliasTypeDescriptions.forEach((lotAliasTypeDescription) ->
                lotAliasTypeDescriptionTransfers.add(lotAliasTypeDescriptionTransferCache.getTransfer(userVisit, lotAliasTypeDescription))
        );

        return lotAliasTypeDescriptionTransfers;
    }

    public void updateLotAliasTypeDescriptionFromValue(LotAliasTypeDescriptionValue lotAliasTypeDescriptionValue, BasePK updatedBy) {
        if(lotAliasTypeDescriptionValue.hasBeenModified()) {
            var lotAliasTypeDescription = lotAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    lotAliasTypeDescriptionValue.getPrimaryKey());

            lotAliasTypeDescription.setThruTime(session.getStartTime());
            lotAliasTypeDescription.store();

            var lotAliasType = lotAliasTypeDescription.getLotAliasType();
            var language = lotAliasTypeDescription.getLanguage();
            var description = lotAliasTypeDescriptionValue.getDescription();

            lotAliasTypeDescription = lotAliasTypeDescriptionFactory.create(lotAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(lotAliasType.getPrimaryKey(), EventTypes.MODIFY, lotAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLotAliasTypeDescription(LotAliasTypeDescription lotAliasTypeDescription, BasePK deletedBy) {
        lotAliasTypeDescription.setThruTime(session.getStartTime());

        sendEvent(lotAliasTypeDescription.getLotAliasTypePK(), EventTypes.MODIFY, lotAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        var lotAliasTypeDescriptions = getLotAliasTypeDescriptionsByLotAliasTypeForUpdate(lotAliasType);

        lotAliasTypeDescriptions.forEach((lotAliasTypeDescription) ->
                deleteLotAliasTypeDescription(lotAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected LotAliasFactory lotAliasFactory;

    public LotAlias createLotAlias(Lot lot, LotAliasType lotAliasType, String alias, BasePK createdBy) {
        var lotAlias = lotAliasFactory.create(lot, lotAliasType, alias, session.getStartTime(), Session.MAX_TIME);

        sendEvent(lot.getPrimaryKey(), EventTypes.MODIFY, lotAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return lotAlias;
    }

    public long countLotAliasesByLot(final Lot lot) {
        return session.getDslContext()
                .selectCount()
                .from(LotAliases)
                .where(LotAliases.LOT.eq(lot.getPrimaryKey()), LotAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLotAliasesByLotAliasType(final LotAliasType lotAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(LotAliases)
                .where(LotAliases.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()), LotAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private LotAlias getLotAlias(Lot lot, LotAliasType lotAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotAliases.fields())
                .from(LotAliases)
                .where(LotAliases.LOT.eq(lot.getPrimaryKey()), LotAliases.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()),
                        LotAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return lotAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public LotAlias getLotAlias(Lot lot, LotAliasType lotAliasType) {
        return getLotAlias(lot, lotAliasType, EntityPermission.READ_ONLY);
    }

    public LotAlias getLotAliasForUpdate(Lot lot, LotAliasType lotAliasType) {
        return getLotAlias(lot, lotAliasType, EntityPermission.READ_WRITE);
    }

    public LotAliasValue getLotAliasValue(LotAlias lotAlias) {
        return lotAlias == null ? null : lotAlias.getLotAliasValue().clone();
    }

    public LotAliasValue getLotAliasValueForUpdate(Lot lot, LotAliasType lotAliasType) {
        return getLotAliasValue(getLotAliasForUpdate(lot, lotAliasType));
    }

    private LotAlias getLotAliasByAlias(LotAliasType lotAliasType, String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LotAliases.fields())
                .from(LotAliases)
                .where(LotAliases.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()), LotAliases.ALIAS.eq(alias),
                        LotAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return lotAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public LotAlias getLotAliasByAlias(LotAliasType lotAliasType, String alias) {
        return getLotAliasByAlias(lotAliasType, alias, EntityPermission.READ_ONLY);
    }

    public LotAlias getLotAliasByAliasForUpdate(LotAliasType lotAliasType, String alias) {
        return getLotAliasByAlias(lotAliasType, alias, EntityPermission.READ_WRITE);
    }

    private List<LotAlias> getLotAliasesByLot(Lot lot, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LotAliases.fields())
                    .from(LotAliases)
                    .join(LotAliasTypes)
                    .on(LotAliases.LOT_ALIAS_TYPE.eq(LotAliasTypes.LOT_ALIAS_TYPE))
                    .join(LotAliasTypeDetails)
                    .on(LotAliasTypes.LAST_DETAIL.eq(LotAliasTypeDetails.LOT_ALIAS_TYPE_DETAIL))
                    .where(LotAliases.LOT.eq(lot.getPrimaryKey()), LotAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(LotAliasTypeDetails.SORT_ORDER, LotAliasTypeDetails.LOT_ALIAS_TYPE_NAME),
                    LotAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LotAliases.fields())
                    .from(LotAliases)
                    .where(LotAliases.LOT.eq(lot.getPrimaryKey()), LotAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return lotAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LotAlias> getLotAliasesByLot(Lot lot) {
        return getLotAliasesByLot(lot, EntityPermission.READ_ONLY);
    }

    public List<LotAlias> getLotAliasesByLotForUpdate(Lot lot) {
        return getLotAliasesByLot(lot, EntityPermission.READ_WRITE);
    }

    private List<LotAlias> getLotAliasesByLotAliasType(LotAliasType lotAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LotAliases.fields())
                    .from(LotAliases)
                    .join(Lots)
                    .on(LotAliases.LOT.eq(Lots.LOT))
                    .join(LotDetails)
                    .on(Lots.LAST_DETAIL.eq(LotDetails.LOT_DETAIL))
                    .where(LotAliases.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()), LotAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(LotDetails.LOT_IDENTIFIER),
                    LotAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LotAliases.fields())
                    .from(LotAliases)
                    .where(LotAliases.LOT_ALIAS_TYPE.eq(lotAliasType.getPrimaryKey()), LotAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return lotAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LotAlias> getLotAliasesByLotAliasType(LotAliasType lotAliasType) {
        return getLotAliasesByLotAliasType(lotAliasType, EntityPermission.READ_ONLY);
    }

    public List<LotAlias> getLotAliasesByLotAliasTypeForUpdate(LotAliasType lotAliasType) {
        return getLotAliasesByLotAliasType(lotAliasType, EntityPermission.READ_WRITE);
    }

    public LotAliasTransfer getLotAliasTransfer(UserVisit userVisit, LotAlias lotAlias) {
        return lotAliasTransferCache.getTransfer(userVisit, lotAlias);
    }

    public List<LotAliasTransfer> getLotAliasTransfers(UserVisit userVisit, Collection<LotAlias> lotAliases) {
        List<LotAliasTransfer> lotAliasTransfers = new ArrayList<>(lotAliases.size());

        lotAliases.forEach((lotAlias) ->
                lotAliasTransfers.add(lotAliasTransferCache.getTransfer(userVisit, lotAlias))
        );

        return lotAliasTransfers;
    }

    public List<LotAliasTransfer> getLotAliasTransfersByLot(UserVisit userVisit, Lot lot) {
        return getLotAliasTransfers(userVisit, getLotAliasesByLot(lot));
    }

    public void updateLotAliasFromValue(LotAliasValue lotAliasValue, BasePK updatedBy) {
        if(lotAliasValue.hasBeenModified()) {
            var lotAlias = lotAliasFactory.getEntityFromPK(EntityPermission.READ_WRITE, lotAliasValue.getPrimaryKey());

            lotAlias.setThruTime(session.getStartTime());
            lotAlias.store();

            var lotPK = lotAlias.getLotPK();
            var lotAliasTypePK = lotAlias.getLotAliasTypePK();
            var alias = lotAliasValue.getAlias();

            lotAlias = lotAliasFactory.create(lotPK, lotAliasTypePK, alias, session.getStartTime(), Session.MAX_TIME);

            sendEvent(lotPK, EventTypes.MODIFY, lotAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLotAlias(LotAlias lotAlias, BasePK deletedBy) {
        lotAlias.setThruTime(session.getStartTime());

        sendEvent(lotAlias.getLotPK(), EventTypes.MODIFY, lotAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLotAliasesByLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        var lotaliases = getLotAliasesByLotAliasTypeForUpdate(lotAliasType);

        lotaliases.forEach((lotAlias) ->
                deleteLotAlias(lotAlias, deletedBy)
        );
    }

    public void deleteLotAliasesByLot(Lot lot, BasePK deletedBy) {
        var lotaliases = getLotAliasesByLotForUpdate(lot);

        lotaliases.forEach((lotAlias) ->
                deleteLotAlias(lotAlias, deletedBy)
        );
    }

}
