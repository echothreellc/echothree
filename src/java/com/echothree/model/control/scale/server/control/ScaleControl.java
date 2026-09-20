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

package com.echothree.model.control.scale.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.scale.common.choice.ScaleChoicesBean;
import com.echothree.model.control.scale.common.choice.ScaleTypeChoicesBean;
import com.echothree.model.control.scale.common.choice.ScaleUseTypeChoicesBean;
import com.echothree.model.control.scale.common.transfer.PartyScaleUseTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTypeDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTypeTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleUseTypeDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleUseTypeTransfer;
import com.echothree.model.control.scale.server.transfer.PartyScaleUseTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleDescriptionTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleTypeDescriptionTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleTypeTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleUseTypeDescriptionTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleUseTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.scale.common.pk.ScalePK;
import com.echothree.model.data.scale.common.pk.ScaleTypePK;
import com.echothree.model.data.scale.common.pk.ScaleUseTypePK;
import com.echothree.model.data.scale.server.entity.PartyScaleUse;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.scale.server.entity.ScaleDescription;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.scale.server.entity.ScaleTypeDescription;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
import com.echothree.model.data.scale.server.entity.ScaleUseTypeDescription;
import com.echothree.model.data.scale.server.factory.PartyScaleUseFactory;
import com.echothree.model.data.scale.server.factory.ScaleDescriptionFactory;
import com.echothree.model.data.scale.server.factory.ScaleDetailFactory;
import com.echothree.model.data.scale.server.factory.ScaleFactory;
import com.echothree.model.data.scale.server.factory.ScaleTypeDescriptionFactory;
import com.echothree.model.data.scale.server.factory.ScaleTypeDetailFactory;
import com.echothree.model.data.scale.server.factory.ScaleTypeFactory;
import com.echothree.model.data.scale.server.factory.ScaleUseTypeDescriptionFactory;
import com.echothree.model.data.scale.server.factory.ScaleUseTypeDetailFactory;
import com.echothree.model.data.scale.server.factory.ScaleUseTypeFactory;
import com.echothree.model.data.scale.server.value.PartyScaleUseValue;
import com.echothree.model.data.scale.server.value.ScaleDescriptionValue;
import com.echothree.model.data.scale.server.value.ScaleDetailValue;
import com.echothree.model.data.scale.server.value.ScaleTypeDescriptionValue;
import com.echothree.model.data.scale.server.value.ScaleTypeDetailValue;
import com.echothree.model.data.scale.server.value.ScaleUseTypeDescriptionValue;
import com.echothree.model.data.scale.server.value.ScaleUseTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.PARTY_SCALE_USES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.PARTY_SCALE_USES_SCALE_USE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.SCALES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.SCALE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.SCALE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.SCALE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.SCALE_USE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.SCALE_USE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.scale.ScaleForeignKeys.SCALE_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.scale.PartyScaleUses.PartyScaleUses;
import static com.echothree.model.jooq.server.tables.scale.ScaleDescriptions.ScaleDescriptions;
import static com.echothree.model.jooq.server.tables.scale.ScaleDetails.ScaleDetails;
import static com.echothree.model.jooq.server.tables.scale.ScaleTypeDescriptions.ScaleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.scale.ScaleTypeDetails.ScaleTypeDetails;
import static com.echothree.model.jooq.server.tables.scale.ScaleTypes.ScaleTypes;
import static com.echothree.model.jooq.server.tables.scale.ScaleUseTypeDescriptions.ScaleUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.scale.ScaleUseTypeDetails.ScaleUseTypeDetails;
import static com.echothree.model.jooq.server.tables.scale.ScaleUseTypes.ScaleUseTypes;
import static com.echothree.model.jooq.server.tables.scale.Scales.Scales;
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
public class ScaleControl
        extends BaseModelControl {
    
    /** Creates a new instance of ScaleControl */
    protected ScaleControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Scale Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ScaleTypeDetailFactory scaleTypeDetailFactory;

    @Inject
    protected ScaleTypeFactory scaleTypeFactory;

    @Inject
    private ScaleTypeTransferCache scaleTypeTransferCache;

    public ScaleType createScaleType(String scaleTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultScaleType = getDefaultScaleType();
        var defaultFound = defaultScaleType != null;

        if(defaultFound && isDefault) {
            var defaultScaleTypeDetailValue = getDefaultScaleTypeDetailValueForUpdate();

            defaultScaleTypeDetailValue.setIsDefault(false);
            updateScaleTypeFromValue(defaultScaleTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var scaleType = scaleTypeFactory.create();
        var scaleTypeDetail = scaleTypeDetailFactory.create(scaleType,
                scaleTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        scaleType = scaleTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                scaleType.getPrimaryKey());
        scaleType.setActiveDetail(scaleTypeDetail);
        scaleType.setLastDetail(scaleTypeDetail);
        scaleType.store();

        sendEvent(scaleType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return scaleType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ScaleType */
    public ScaleType getScaleTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ScaleTypePK(entityInstance.getEntityUniqueId());

        return scaleTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ScaleType getScaleTypeByEntityInstance(EntityInstance entityInstance) {
        return getScaleTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ScaleType getScaleTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getScaleTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countScaleTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ScaleTypes)
                .join(ScaleTypeDetails).onKey(SCALE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ScaleType getScaleTypeByName(String scaleTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleTypes.fields())
                .from(ScaleTypes)
                .join(ScaleTypeDetails).onKey(SCALE_TYPES_ACTIVE_DETAIL_FK)
                .where(ScaleTypeDetails.SCALE_TYPE_NAME.eq(scaleTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ScaleType getScaleTypeByName(String scaleTypeName) {
        return getScaleTypeByName(scaleTypeName, EntityPermission.READ_ONLY);
    }

    public ScaleType getScaleTypeByNameForUpdate(String scaleTypeName) {
        return getScaleTypeByName(scaleTypeName, EntityPermission.READ_WRITE);
    }

    public ScaleTypeDetailValue getScaleTypeDetailValueForUpdate(ScaleType scaleType) {
        return scaleType == null? null: scaleType.getLastDetailForUpdate().getScaleTypeDetailValue().clone();
    }

    public ScaleTypeDetailValue getScaleTypeDetailValueByNameForUpdate(String scaleTypeName) {
        return getScaleTypeDetailValueForUpdate(getScaleTypeByNameForUpdate(scaleTypeName));
    }

    private ScaleType getDefaultScaleType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleTypes.fields())
                .from(ScaleTypes)
                .join(ScaleTypeDetails).onKey(SCALE_TYPES_ACTIVE_DETAIL_FK)
                .where(ScaleTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ScaleType getDefaultScaleType() {
        return getDefaultScaleType(EntityPermission.READ_ONLY);
    }

    public ScaleType getDefaultScaleTypeForUpdate() {
        return getDefaultScaleType(EntityPermission.READ_WRITE);
    }

    public ScaleTypeDetailValue getDefaultScaleTypeDetailValueForUpdate() {
        return getDefaultScaleTypeForUpdate().getLastDetailForUpdate().getScaleTypeDetailValue().clone();
    }

    private List<ScaleType> getScaleTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleTypes.fields())
                .from(ScaleTypes)
                .join(ScaleTypeDetails).onKey(SCALE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ScaleTypeDetails.SORT_ORDER, ScaleTypeDetails.SCALE_TYPE_NAME),
                    ScaleTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ScaleType> getScaleTypes() {
        return getScaleTypes(EntityPermission.READ_ONLY);
    }

    public List<ScaleType> getScaleTypesForUpdate() {
        return getScaleTypes(EntityPermission.READ_WRITE);
    }

    public ScaleTypeTransfer getScaleTypeTransfer(UserVisit userVisit, ScaleType scaleType) {
        return scaleTypeTransferCache.getScaleTypeTransfer(userVisit, scaleType);
    }

    public List<ScaleTypeTransfer> getScaleTypeTransfers(UserVisit userVisit, Collection<ScaleType> scaleTypes) {
        List<ScaleTypeTransfer> scaleTypeTransfers = new ArrayList<>(scaleTypes.size());

        scaleTypes.forEach((scaleType) ->
                scaleTypeTransfers.add(scaleTypeTransferCache.getScaleTypeTransfer(userVisit, scaleType))
        );

        return scaleTypeTransfers;
    }

    public List<ScaleTypeTransfer> getScaleTypeTransfers(UserVisit userVisit) {
        return getScaleTypeTransfers(userVisit, getScaleTypes());
    }

    public ScaleTypeChoicesBean getScaleTypeChoices(String defaultScaleTypeChoice, Language language, boolean allowNullChoice) {
        var scaleTypes = getScaleTypes();
        var size = scaleTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultScaleTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var scaleType : scaleTypes) {
            var scaleTypeDetail = scaleType.getLastDetail();

            var label = getBestScaleTypeDescription(scaleType, language);
            var value = scaleTypeDetail.getScaleTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultScaleTypeChoice != null && defaultScaleTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && scaleTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ScaleTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateScaleTypeFromValue(ScaleTypeDetailValue scaleTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(scaleTypeDetailValue.hasBeenModified()) {
            var scaleType = scaleTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     scaleTypeDetailValue.getScaleTypePK());
            var scaleTypeDetail = scaleType.getActiveDetailForUpdate();

            scaleTypeDetail.setThruTime(session.getStartTime());
            scaleTypeDetail.store();

            var scaleTypePK = scaleTypeDetail.getScaleTypePK(); // Not updated
            var scaleTypeName = scaleTypeDetailValue.getScaleTypeName();
            var isDefault = scaleTypeDetailValue.getIsDefault();
            var sortOrder = scaleTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultScaleType = getDefaultScaleType();
                var defaultFound = defaultScaleType != null && !defaultScaleType.equals(scaleType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultScaleTypeDetailValue = getDefaultScaleTypeDetailValueForUpdate();

                    defaultScaleTypeDetailValue.setIsDefault(false);
                    updateScaleTypeFromValue(defaultScaleTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            scaleTypeDetail = scaleTypeDetailFactory.create(scaleTypePK, scaleTypeName, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            scaleType.setActiveDetail(scaleTypeDetail);
            scaleType.setLastDetail(scaleTypeDetail);

            sendEvent(scaleTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateScaleTypeFromValue(ScaleTypeDetailValue scaleTypeDetailValue, BasePK updatedBy) {
        updateScaleTypeFromValue(scaleTypeDetailValue, true, updatedBy);
    }

    public void deleteScaleType(ScaleType scaleType, BasePK deletedBy) {
        deleteScaleTypeDescriptionsByScaleType(scaleType, deletedBy);
        deleteScalesByScaleType(scaleType, deletedBy);

        var scaleTypeDetail = scaleType.getLastDetailForUpdate();
        scaleTypeDetail.setThruTime(session.getStartTime());
        scaleType.setActiveDetail(null);
        scaleType.store();

        // Check for default, and pick one if necessary
        var defaultScaleType = getDefaultScaleType();
        if(defaultScaleType == null) {
            var scaleTypes = getScaleTypesForUpdate();

            if(!scaleTypes.isEmpty()) {
                var iter = scaleTypes.iterator();
                if(iter.hasNext()) {
                    defaultScaleType = iter.next();
                }
                var scaleTypeDetailValue = Objects.requireNonNull(defaultScaleType).getLastDetailForUpdate().getScaleTypeDetailValue().clone();

                scaleTypeDetailValue.setIsDefault(true);
                updateScaleTypeFromValue(scaleTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(scaleType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Scale Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ScaleTypeDescriptionFactory scaleTypeDescriptionFactory;

    @Inject
    private ScaleTypeDescriptionTransferCache scaleTypeDescriptionTransferCache;

    public ScaleTypeDescription createScaleTypeDescription(ScaleType scaleType,
            Language language, String description, BasePK createdBy) {
        var scaleTypeDescription = scaleTypeDescriptionFactory.create(scaleType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(scaleType.getPrimaryKey(), EventTypes.MODIFY, scaleTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return scaleTypeDescription;
    }

    private ScaleTypeDescription getScaleTypeDescription(ScaleType scaleType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleTypeDescriptions.fields())
                .from(ScaleTypeDescriptions)
                .where(ScaleTypeDescriptions.SCALE_TYPE.eq(scaleType.getPrimaryKey()),
                        ScaleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ScaleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ScaleTypeDescription getScaleTypeDescription(ScaleType scaleType, Language language) {
        return getScaleTypeDescription(scaleType, language, EntityPermission.READ_ONLY);
    }

    public ScaleTypeDescription getScaleTypeDescriptionForUpdate(ScaleType scaleType, Language language) {
        return getScaleTypeDescription(scaleType, language, EntityPermission.READ_WRITE);
    }

    public ScaleTypeDescriptionValue getScaleTypeDescriptionValue(ScaleTypeDescription scaleTypeDescription) {
        return scaleTypeDescription == null? null: scaleTypeDescription.getScaleTypeDescriptionValue().clone();
    }

    public ScaleTypeDescriptionValue getScaleTypeDescriptionValueForUpdate(ScaleType scaleType, Language language) {
        return getScaleTypeDescriptionValue(getScaleTypeDescriptionForUpdate(scaleType, language));
    }

    private List<ScaleTypeDescription> getScaleTypeDescriptionsByScaleType(ScaleType scaleType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ScaleTypeDescriptions.fields())
                    .from(ScaleTypeDescriptions)
                    .join(Languages).onKey(SCALE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ScaleTypeDescriptions.SCALE_TYPE.eq(scaleType.getPrimaryKey()),
                            ScaleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ScaleTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ScaleTypeDescriptions.fields())
                    .from(ScaleTypeDescriptions)
                    .where(ScaleTypeDescriptions.SCALE_TYPE.eq(scaleType.getPrimaryKey()),
                            ScaleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return scaleTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ScaleTypeDescription> getScaleTypeDescriptionsByScaleType(ScaleType scaleType) {
        return getScaleTypeDescriptionsByScaleType(scaleType, EntityPermission.READ_ONLY);
    }

    public List<ScaleTypeDescription> getScaleTypeDescriptionsByScaleTypeForUpdate(ScaleType scaleType) {
        return getScaleTypeDescriptionsByScaleType(scaleType, EntityPermission.READ_WRITE);
    }

    public String getBestScaleTypeDescription(ScaleType scaleType, Language language) {
        String description;
        var scaleTypeDescription = getScaleTypeDescription(scaleType, language);

        if(scaleTypeDescription == null && !language.getIsDefault()) {
            scaleTypeDescription = getScaleTypeDescription(scaleType, partyControl.getDefaultLanguage());
        }

        if(scaleTypeDescription == null) {
            description = scaleType.getLastDetail().getScaleTypeName();
        } else {
            description = scaleTypeDescription.getDescription();
        }

        return description;
    }

    public ScaleTypeDescriptionTransfer getScaleTypeDescriptionTransfer(UserVisit userVisit, ScaleTypeDescription scaleTypeDescription) {
        return scaleTypeDescriptionTransferCache.getScaleTypeDescriptionTransfer(userVisit, scaleTypeDescription);
    }

    public List<ScaleTypeDescriptionTransfer> getScaleTypeDescriptionTransfersByScaleType(UserVisit userVisit, ScaleType scaleType) {
        var scaleTypeDescriptions = getScaleTypeDescriptionsByScaleType(scaleType);
        List<ScaleTypeDescriptionTransfer> scaleTypeDescriptionTransfers = new ArrayList<>(scaleTypeDescriptions.size());

        scaleTypeDescriptions.forEach((scaleTypeDescription) ->
                scaleTypeDescriptionTransfers.add(scaleTypeDescriptionTransferCache.getScaleTypeDescriptionTransfer(userVisit, scaleTypeDescription))
        );

        return scaleTypeDescriptionTransfers;
    }

    public void updateScaleTypeDescriptionFromValue(ScaleTypeDescriptionValue scaleTypeDescriptionValue, BasePK updatedBy) {
        if(scaleTypeDescriptionValue.hasBeenModified()) {
            var scaleTypeDescription = scaleTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    scaleTypeDescriptionValue.getPrimaryKey());

            scaleTypeDescription.setThruTime(session.getStartTime());
            scaleTypeDescription.store();

            var scaleType = scaleTypeDescription.getScaleType();
            var language = scaleTypeDescription.getLanguage();
            var description = scaleTypeDescriptionValue.getDescription();

            scaleTypeDescription = scaleTypeDescriptionFactory.create(scaleType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(scaleType.getPrimaryKey(), EventTypes.MODIFY, scaleTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteScaleTypeDescription(ScaleTypeDescription scaleTypeDescription, BasePK deletedBy) {
        scaleTypeDescription.setThruTime(session.getStartTime());

        sendEvent(scaleTypeDescription.getScaleTypePK(), EventTypes.MODIFY, scaleTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteScaleTypeDescriptionsByScaleType(ScaleType scaleType, BasePK deletedBy) {
        var scaleTypeDescriptions = getScaleTypeDescriptionsByScaleTypeForUpdate(scaleType);

        scaleTypeDescriptions.forEach((scaleTypeDescription) -> 
                deleteScaleTypeDescription(scaleTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Scales
    // --------------------------------------------------------------------------------

    @Inject
    protected ScaleDetailFactory scaleDetailFactory;

    @Inject
    protected ScaleFactory scaleFactory;

    @Inject
    private ScaleTransferCache scaleTransferCache;

    public Scale createScale(String scaleName, ScaleType scaleType, ServerService serverService, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultScale = getDefaultScale();
        var defaultFound = defaultScale != null;

        if(defaultFound && isDefault) {
            var defaultScaleDetailValue = getDefaultScaleDetailValueForUpdate();

            defaultScaleDetailValue.setIsDefault(false);
            updateScaleFromValue(defaultScaleDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var scale = scaleFactory.create();
        var scaleDetail = scaleDetailFactory.create(scale, scaleName, scaleType, serverService, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        scale = scaleFactory.getEntityFromPK(EntityPermission.READ_WRITE, scale.getPrimaryKey());
        scale.setActiveDetail(scaleDetail);
        scale.setLastDetail(scaleDetail);
        scale.store();

        sendEvent(scale.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return scale;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Scale */
    public Scale getScaleByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ScalePK(entityInstance.getEntityUniqueId());

        return scaleFactory.getEntityFromPK(entityPermission, pk);
    }

    public Scale getScaleByEntityInstance(EntityInstance entityInstance) {
        return getScaleByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Scale getScaleByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getScaleByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countScales() {
        return session.getDslContext()
                .selectCount()
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countScalesByScaleType(final ScaleType scaleType) {
        return session.getDslContext()
                .selectCount()
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK)
                .where(ScaleDetails.SCALE_TYPE.eq(scaleType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countScalesByServerService(final ServerService serverService) {
        return session.getDslContext()
                .selectCount()
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK)
                .where(ScaleDetails.SERVER_SERVICE.eq(serverService.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Scale getScaleByName(String scaleName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Scales.fields())
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK)
                .where(ScaleDetails.SCALE_NAME.eq(scaleName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleFactory.getEntityFromQuery(entityPermission, query);
    }

    public Scale getScaleByName(String scaleName) {
        return getScaleByName(scaleName, EntityPermission.READ_ONLY);
    }

    public Scale getScaleByNameForUpdate(String scaleName) {
        return getScaleByName(scaleName, EntityPermission.READ_WRITE);
    }

    public ScaleDetailValue getScaleDetailValueForUpdate(Scale scale) {
        return scale == null? null: scale.getLastDetailForUpdate().getScaleDetailValue().clone();
    }

    public ScaleDetailValue getScaleDetailValueByNameForUpdate(String scaleName) {
        return getScaleDetailValueForUpdate(getScaleByNameForUpdate(scaleName));
    }

    private Scale getDefaultScale(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Scales.fields())
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK)
                .where(ScaleDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleFactory.getEntityFromQuery(entityPermission, query);
    }

    public Scale getDefaultScale() {
        return getDefaultScale(EntityPermission.READ_ONLY);
    }

    public Scale getDefaultScaleForUpdate() {
        return getDefaultScale(EntityPermission.READ_WRITE);
    }

    public ScaleDetailValue getDefaultScaleDetailValueForUpdate() {
        return getDefaultScaleForUpdate().getLastDetailForUpdate().getScaleDetailValue().clone();
    }

    private List<Scale> getScales(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Scales.fields())
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ScaleDetails.SORT_ORDER, ScaleDetails.SCALE_NAME),
                    ScaleFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Scale> getScales() {
        return getScales(EntityPermission.READ_ONLY);
    }

    public List<Scale> getScalesForUpdate() {
        return getScales(EntityPermission.READ_WRITE);
    }

    private List<Scale> getScalesByScaleType(ScaleType scaleType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Scales.fields())
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK)
                .where(ScaleDetails.SCALE_TYPE.eq(scaleType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ScaleDetails.SORT_ORDER, ScaleDetails.SCALE_NAME),
                    ScaleFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Scale> getScalesByScaleType(ScaleType scaleType) {
        return getScalesByScaleType(scaleType, EntityPermission.READ_ONLY);
    }

    public List<Scale> getScalesByScaleTypeForUpdate(ScaleType scaleType) {
        return getScalesByScaleType(scaleType, EntityPermission.READ_WRITE);
    }

    private List<Scale> getScalesByServerService(ServerService serverService, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Scales.fields())
                .from(Scales)
                .join(ScaleDetails).onKey(SCALES_ACTIVE_DETAIL_FK)
                .where(ScaleDetails.SERVER_SERVICE.eq(serverService.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ScaleDetails.SORT_ORDER, ScaleDetails.SCALE_NAME),
                    ScaleFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Scale> getScalesByServerService(ServerService serverService) {
        return getScalesByServerService(serverService, EntityPermission.READ_ONLY);
    }

    public List<Scale> getScalesByServerServiceForUpdate(ServerService serverService) {
        return getScalesByServerService(serverService, EntityPermission.READ_WRITE);
    }

    public ScaleTransfer getScaleTransfer(UserVisit userVisit, Scale scale) {
        return scaleTransferCache.getScaleTransfer(userVisit, scale);
    }

    public List<ScaleTransfer> getScaleTransfers(UserVisit userVisit, Collection<Scale> scales) {
        List<ScaleTransfer> scaleTransfers = new ArrayList<>(scales.size());

        scales.forEach((scale) ->
                scaleTransfers.add(scaleTransferCache.getScaleTransfer(userVisit, scale))
        );

        return scaleTransfers;
    }

    public List<ScaleTransfer> getScaleTransfers(UserVisit userVisit) {
        return getScaleTransfers(userVisit, getScales());
    }

    public ScaleChoicesBean getScaleChoices(String defaultScaleChoice, Language language, boolean allowNullChoice) {
        var scales = getScales();
        var size = scales.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultScaleChoice == null) {
                defaultValue = "";
            }
        }

        for(var scale : scales) {
            var scaleDetail = scale.getLastDetail();

            var label = getBestScaleDescription(scale, language);
            var value = scaleDetail.getScaleName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultScaleChoice != null && defaultScaleChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && scaleDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ScaleChoicesBean(labels, values, defaultValue);
    }

    private void updateScaleFromValue(ScaleDetailValue scaleDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(scaleDetailValue.hasBeenModified()) {
            var scale = scaleFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     scaleDetailValue.getScalePK());
            var scaleDetail = scale.getActiveDetailForUpdate();

            scaleDetail.setThruTime(session.getStartTime());
            scaleDetail.store();

            var scalePK = scaleDetail.getScalePK(); // Not updated
            var scaleName = scaleDetailValue.getScaleName();
            var scaleTypePK = scaleDetailValue.getScaleTypePK();
            var serverServicePK = scaleDetailValue.getServerServicePK();
            var isDefault = scaleDetailValue.getIsDefault();
            var sortOrder = scaleDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultScale = getDefaultScale();
                var defaultFound = defaultScale != null && !defaultScale.equals(scale);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultScaleDetailValue = getDefaultScaleDetailValueForUpdate();

                    defaultScaleDetailValue.setIsDefault(false);
                    updateScaleFromValue(defaultScaleDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            scaleDetail = scaleDetailFactory.create(scalePK, scaleName, scaleTypePK, serverServicePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            scale.setActiveDetail(scaleDetail);
            scale.setLastDetail(scaleDetail);

            sendEvent(scalePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateScaleFromValue(ScaleDetailValue scaleDetailValue, BasePK updatedBy) {
        updateScaleFromValue(scaleDetailValue, true, updatedBy);
    }

    private void deleteScale(Scale scale, boolean checkDefault, BasePK deletedBy) {
        var scaleDetail = scale.getLastDetailForUpdate();

        deleteScaleDescriptionsByScale(scale, deletedBy);
        deletePartyScaleUsesByScale(scale, deletedBy);

        scaleDetail.setThruTime(session.getStartTime());
        scale.setActiveDetail(null);
        scale.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultScale = getDefaultScale();

            if(defaultScale == null) {
                var scales = getScalesForUpdate();

                if(!scales.isEmpty()) {
                    var iter = scales.iterator();
                    if(iter.hasNext()) {
                        defaultScale = iter.next();
                    }
                    var scaleDetailValue = Objects.requireNonNull(defaultScale).getLastDetailForUpdate().getScaleDetailValue().clone();

                    scaleDetailValue.setIsDefault(true);
                    updateScaleFromValue(scaleDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(scale.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteScale(Scale itemDescriptionType, BasePK deletedBy) {
        deleteScale(itemDescriptionType, true, deletedBy);
    }

    private void deleteScales(List<Scale> itemDescriptionTypes, boolean checkDefault, BasePK deletedBy) {
        itemDescriptionTypes.forEach((itemDescriptionType) -> deleteScale(itemDescriptionType, checkDefault, deletedBy));
    }

    public void deleteScales(List<Scale> itemDescriptionTypes, BasePK deletedBy) {
        deleteScales(itemDescriptionTypes, true, deletedBy);
    }

    public void deleteScalesByScaleType(ScaleType scaleType, BasePK deletedBy) {
        deleteScales(getScalesByScaleTypeForUpdate(scaleType), false, deletedBy);
    }

    public void deleteScalesByServerService(ServerService serverService, BasePK deletedBy) {
        deleteScales(getScalesByServerServiceForUpdate(serverService), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Scale Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ScaleDescriptionFactory scaleDescriptionFactory;

    @Inject
    private ScaleDescriptionTransferCache scaleDescriptionTransferCache;

    public ScaleDescription createScaleDescription(Scale scale,
            Language language, String description, BasePK createdBy) {
        var scaleDescription = scaleDescriptionFactory.create(scale,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(scale.getPrimaryKey(), EventTypes.MODIFY, scaleDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return scaleDescription;
    }

    private ScaleDescription getScaleDescription(Scale scale,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleDescriptions.fields())
                .from(ScaleDescriptions)
                .where(ScaleDescriptions.SCALE.eq(scale.getPrimaryKey()),
                        ScaleDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ScaleDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ScaleDescription getScaleDescription(Scale scale, Language language) {
        return getScaleDescription(scale, language, EntityPermission.READ_ONLY);
    }

    public ScaleDescription getScaleDescriptionForUpdate(Scale scale, Language language) {
        return getScaleDescription(scale, language, EntityPermission.READ_WRITE);
    }

    public ScaleDescriptionValue getScaleDescriptionValue(ScaleDescription scaleDescription) {
        return scaleDescription == null? null: scaleDescription.getScaleDescriptionValue().clone();
    }

    public ScaleDescriptionValue getScaleDescriptionValueForUpdate(Scale scale, Language language) {
        return getScaleDescriptionValue(getScaleDescriptionForUpdate(scale, language));
    }

    private List<ScaleDescription> getScaleDescriptionsByScale(Scale scale,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ScaleDescriptions.fields())
                    .from(ScaleDescriptions)
                    .join(Languages).onKey(SCALE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ScaleDescriptions.SCALE.eq(scale.getPrimaryKey()),
                            ScaleDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ScaleDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ScaleDescriptions.fields())
                    .from(ScaleDescriptions)
                    .where(ScaleDescriptions.SCALE.eq(scale.getPrimaryKey()),
                            ScaleDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return scaleDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ScaleDescription> getScaleDescriptionsByScale(Scale scale) {
        return getScaleDescriptionsByScale(scale, EntityPermission.READ_ONLY);
    }

    public List<ScaleDescription> getScaleDescriptionsByScaleForUpdate(Scale scale) {
        return getScaleDescriptionsByScale(scale, EntityPermission.READ_WRITE);
    }

    public String getBestScaleDescription(Scale scale, Language language) {
        String description;
        var scaleDescription = getScaleDescription(scale, language);

        if(scaleDescription == null && !language.getIsDefault()) {
            scaleDescription = getScaleDescription(scale, partyControl.getDefaultLanguage());
        }

        if(scaleDescription == null) {
            description = scale.getLastDetail().getScaleName();
        } else {
            description = scaleDescription.getDescription();
        }

        return description;
    }

    public ScaleDescriptionTransfer getScaleDescriptionTransfer(UserVisit userVisit, ScaleDescription scaleDescription) {
        return scaleDescriptionTransferCache.getScaleDescriptionTransfer(userVisit, scaleDescription);
    }

    public List<ScaleDescriptionTransfer> getScaleDescriptionTransfersByScale(UserVisit userVisit, Scale scale) {
        var scaleDescriptions = getScaleDescriptionsByScale(scale);
        List<ScaleDescriptionTransfer> scaleDescriptionTransfers = new ArrayList<>(scaleDescriptions.size());

        scaleDescriptions.forEach((scaleDescription) ->
                scaleDescriptionTransfers.add(scaleDescriptionTransferCache.getScaleDescriptionTransfer(userVisit, scaleDescription))
        );

        return scaleDescriptionTransfers;
    }

    public void updateScaleDescriptionFromValue(ScaleDescriptionValue scaleDescriptionValue, BasePK updatedBy) {
        if(scaleDescriptionValue.hasBeenModified()) {
            var scaleDescription = scaleDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    scaleDescriptionValue.getPrimaryKey());

            scaleDescription.setThruTime(session.getStartTime());
            scaleDescription.store();

            var scale = scaleDescription.getScale();
            var language = scaleDescription.getLanguage();
            var description = scaleDescriptionValue.getDescription();

            scaleDescription = scaleDescriptionFactory.create(scale, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(scale.getPrimaryKey(), EventTypes.MODIFY, scaleDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteScaleDescription(ScaleDescription scaleDescription, BasePK deletedBy) {
        scaleDescription.setThruTime(session.getStartTime());

        sendEvent(scaleDescription.getScalePK(), EventTypes.MODIFY, scaleDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteScaleDescriptionsByScale(Scale scale, BasePK deletedBy) {
        var scaleDescriptions = getScaleDescriptionsByScaleForUpdate(scale);

        scaleDescriptions.forEach((scaleDescription) -> 
                deleteScaleDescription(scaleDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Scale Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ScaleUseTypeDetailFactory scaleUseTypeDetailFactory;

    @Inject
    protected ScaleUseTypeFactory scaleUseTypeFactory;

    @Inject
    private ScaleUseTypeTransferCache scaleUseTypeTransferCache;

    public ScaleUseType createScaleUseType(String scaleUseTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultScaleUseType = getDefaultScaleUseType();
        var defaultFound = defaultScaleUseType != null;

        if(defaultFound && isDefault) {
            var defaultScaleUseTypeDetailValue = getDefaultScaleUseTypeDetailValueForUpdate();

            defaultScaleUseTypeDetailValue.setIsDefault(false);
            updateScaleUseTypeFromValue(defaultScaleUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var scaleUseType = scaleUseTypeFactory.create();
        var scaleUseTypeDetail = scaleUseTypeDetailFactory.create(scaleUseType,
                scaleUseTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        scaleUseType = scaleUseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                scaleUseType.getPrimaryKey());
        scaleUseType.setActiveDetail(scaleUseTypeDetail);
        scaleUseType.setLastDetail(scaleUseTypeDetail);
        scaleUseType.store();

        sendEvent(scaleUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return scaleUseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ScaleUseType */
    public ScaleUseType getScaleUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ScaleUseTypePK(entityInstance.getEntityUniqueId());

        return scaleUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ScaleUseType getScaleUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getScaleUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ScaleUseType getScaleUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getScaleUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countScaleUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ScaleUseTypes)
                .join(ScaleUseTypeDetails).onKey(SCALE_USE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ScaleUseType getScaleUseTypeByName(String scaleUseTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleUseTypes.fields())
                .from(ScaleUseTypes)
                .join(ScaleUseTypeDetails).onKey(SCALE_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(ScaleUseTypeDetails.SCALE_USE_TYPE_NAME.eq(scaleUseTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ScaleUseType getScaleUseTypeByName(String scaleUseTypeName) {
        return getScaleUseTypeByName(scaleUseTypeName, EntityPermission.READ_ONLY);
    }

    public ScaleUseType getScaleUseTypeByNameForUpdate(String scaleUseTypeName) {
        return getScaleUseTypeByName(scaleUseTypeName, EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeDetailValue getScaleUseTypeDetailValueForUpdate(ScaleUseType scaleUseType) {
        return scaleUseType == null? null: scaleUseType.getLastDetailForUpdate().getScaleUseTypeDetailValue().clone();
    }

    public ScaleUseTypeDetailValue getScaleUseTypeDetailValueByNameForUpdate(String scaleUseTypeName) {
        return getScaleUseTypeDetailValueForUpdate(getScaleUseTypeByNameForUpdate(scaleUseTypeName));
    }

    private ScaleUseType getDefaultScaleUseType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleUseTypes.fields())
                .from(ScaleUseTypes)
                .join(ScaleUseTypeDetails).onKey(SCALE_USE_TYPES_ACTIVE_DETAIL_FK)
                .where(ScaleUseTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ScaleUseType getDefaultScaleUseType() {
        return getDefaultScaleUseType(EntityPermission.READ_ONLY);
    }

    public ScaleUseType getDefaultScaleUseTypeForUpdate() {
        return getDefaultScaleUseType(EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeDetailValue getDefaultScaleUseTypeDetailValueForUpdate() {
        return getDefaultScaleUseTypeForUpdate().getLastDetailForUpdate().getScaleUseTypeDetailValue().clone();
    }

    private List<ScaleUseType> getScaleUseTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleUseTypes.fields())
                .from(ScaleUseTypes)
                .join(ScaleUseTypeDetails).onKey(SCALE_USE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ScaleUseTypeDetails.SORT_ORDER, ScaleUseTypeDetails.SCALE_USE_TYPE_NAME),
                    ScaleUseTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleUseTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ScaleUseType> getScaleUseTypes() {
        return getScaleUseTypes(EntityPermission.READ_ONLY);
    }

    public List<ScaleUseType> getScaleUseTypesForUpdate() {
        return getScaleUseTypes(EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeTransfer getScaleUseTypeTransfer(UserVisit userVisit, ScaleUseType scaleUseType) {
        return scaleUseTypeTransferCache.getScaleUseTypeTransfer(userVisit, scaleUseType);
    }

    public List<ScaleUseTypeTransfer> getScaleUseTypeTransfers(UserVisit userVisit, Collection<ScaleUseType> scaleUseTypes) {
        List<ScaleUseTypeTransfer> scaleUseTypeTransfers = new ArrayList<>(scaleUseTypes.size());

        scaleUseTypes.forEach((scaleUseType) ->
                scaleUseTypeTransfers.add(scaleUseTypeTransferCache.getScaleUseTypeTransfer(userVisit, scaleUseType))
        );

        return scaleUseTypeTransfers;
    }

    public List<ScaleUseTypeTransfer> getScaleUseTypeTransfers(UserVisit userVisit) {
        return getScaleUseTypeTransfers(userVisit, getScaleUseTypes());
    }

    public ScaleUseTypeChoicesBean getScaleUseTypeChoices(String defaultScaleUseTypeChoice, Language language, boolean allowNullChoice) {
        var scaleUseTypes = getScaleUseTypes();
        var size = scaleUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultScaleUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var scaleUseType : scaleUseTypes) {
            var scaleUseTypeDetail = scaleUseType.getLastDetail();

            var label = getBestScaleUseTypeDescription(scaleUseType, language);
            var value = scaleUseTypeDetail.getScaleUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultScaleUseTypeChoice != null && defaultScaleUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && scaleUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ScaleUseTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateScaleUseTypeFromValue(ScaleUseTypeDetailValue scaleUseTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(scaleUseTypeDetailValue.hasBeenModified()) {
            var scaleUseType = scaleUseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     scaleUseTypeDetailValue.getScaleUseTypePK());
            var scaleUseTypeDetail = scaleUseType.getActiveDetailForUpdate();

            scaleUseTypeDetail.setThruTime(session.getStartTime());
            scaleUseTypeDetail.store();

            var scaleUseTypePK = scaleUseTypeDetail.getScaleUseTypePK(); // Not updated
            var scaleUseTypeName = scaleUseTypeDetailValue.getScaleUseTypeName();
            var isDefault = scaleUseTypeDetailValue.getIsDefault();
            var sortOrder = scaleUseTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultScaleUseType = getDefaultScaleUseType();
                var defaultFound = defaultScaleUseType != null && !defaultScaleUseType.equals(scaleUseType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultScaleUseTypeDetailValue = getDefaultScaleUseTypeDetailValueForUpdate();

                    defaultScaleUseTypeDetailValue.setIsDefault(false);
                    updateScaleUseTypeFromValue(defaultScaleUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            scaleUseTypeDetail = scaleUseTypeDetailFactory.create(scaleUseTypePK, scaleUseTypeName, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            scaleUseType.setActiveDetail(scaleUseTypeDetail);
            scaleUseType.setLastDetail(scaleUseTypeDetail);

            sendEvent(scaleUseTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateScaleUseTypeFromValue(ScaleUseTypeDetailValue scaleUseTypeDetailValue, BasePK updatedBy) {
        updateScaleUseTypeFromValue(scaleUseTypeDetailValue, true, updatedBy);
    }

    public void deleteScaleUseType(ScaleUseType scaleUseType, BasePK deletedBy) {
        deleteScaleUseTypeDescriptionsByScaleUseType(scaleUseType, deletedBy);
        deletePartyScaleUsesByScaleUseType(scaleUseType, deletedBy);

        var scaleUseTypeDetail = scaleUseType.getLastDetailForUpdate();
        scaleUseTypeDetail.setThruTime(session.getStartTime());
        scaleUseType.setActiveDetail(null);
        scaleUseType.store();

        // Check for default, and pick one if necessary
        var defaultScaleUseType = getDefaultScaleUseType();
        if(defaultScaleUseType == null) {
            var scaleUseTypes = getScaleUseTypesForUpdate();

            if(!scaleUseTypes.isEmpty()) {
                var iter = scaleUseTypes.iterator();
                if(iter.hasNext()) {
                    defaultScaleUseType = iter.next();
                }
                var scaleUseTypeDetailValue = Objects.requireNonNull(defaultScaleUseType).getLastDetailForUpdate().getScaleUseTypeDetailValue().clone();

                scaleUseTypeDetailValue.setIsDefault(true);
                updateScaleUseTypeFromValue(scaleUseTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(scaleUseType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Scale Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ScaleUseTypeDescriptionFactory scaleUseTypeDescriptionFactory;

    @Inject
    private ScaleUseTypeDescriptionTransferCache scaleUseTypeDescriptionTransferCache;

    public ScaleUseTypeDescription createScaleUseTypeDescription(ScaleUseType scaleUseType,
            Language language, String description, BasePK createdBy) {
        var scaleUseTypeDescription = scaleUseTypeDescriptionFactory.create(scaleUseType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(scaleUseType.getPrimaryKey(), EventTypes.MODIFY, scaleUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return scaleUseTypeDescription;
    }

    private ScaleUseTypeDescription getScaleUseTypeDescription(ScaleUseType scaleUseType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ScaleUseTypeDescriptions.fields())
                .from(ScaleUseTypeDescriptions)
                .where(ScaleUseTypeDescriptions.SCALE_USE_TYPE.eq(scaleUseType.getPrimaryKey()),
                        ScaleUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ScaleUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return scaleUseTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ScaleUseTypeDescription getScaleUseTypeDescription(ScaleUseType scaleUseType, Language language) {
        return getScaleUseTypeDescription(scaleUseType, language, EntityPermission.READ_ONLY);
    }

    public ScaleUseTypeDescription getScaleUseTypeDescriptionForUpdate(ScaleUseType scaleUseType, Language language) {
        return getScaleUseTypeDescription(scaleUseType, language, EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeDescriptionValue getScaleUseTypeDescriptionValue(ScaleUseTypeDescription scaleUseTypeDescription) {
        return scaleUseTypeDescription == null? null: scaleUseTypeDescription.getScaleUseTypeDescriptionValue().clone();
    }

    public ScaleUseTypeDescriptionValue getScaleUseTypeDescriptionValueForUpdate(ScaleUseType scaleUseType, Language language) {
        return getScaleUseTypeDescriptionValue(getScaleUseTypeDescriptionForUpdate(scaleUseType, language));
    }

    private List<ScaleUseTypeDescription> getScaleUseTypeDescriptionsByScaleUseType(ScaleUseType scaleUseType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ScaleUseTypeDescriptions.fields())
                    .from(ScaleUseTypeDescriptions)
                    .join(Languages).onKey(SCALE_USE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ScaleUseTypeDescriptions.SCALE_USE_TYPE.eq(scaleUseType.getPrimaryKey()),
                            ScaleUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ScaleUseTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ScaleUseTypeDescriptions.fields())
                    .from(ScaleUseTypeDescriptions)
                    .where(ScaleUseTypeDescriptions.SCALE_USE_TYPE.eq(scaleUseType.getPrimaryKey()),
                            ScaleUseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return scaleUseTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ScaleUseTypeDescription> getScaleUseTypeDescriptionsByScaleUseType(ScaleUseType scaleUseType) {
        return getScaleUseTypeDescriptionsByScaleUseType(scaleUseType, EntityPermission.READ_ONLY);
    }

    public List<ScaleUseTypeDescription> getScaleUseTypeDescriptionsByScaleUseTypeForUpdate(ScaleUseType scaleUseType) {
        return getScaleUseTypeDescriptionsByScaleUseType(scaleUseType, EntityPermission.READ_WRITE);
    }

    public String getBestScaleUseTypeDescription(ScaleUseType scaleUseType, Language language) {
        String description;
        var scaleUseTypeDescription = getScaleUseTypeDescription(scaleUseType, language);

        if(scaleUseTypeDescription == null && !language.getIsDefault()) {
            scaleUseTypeDescription = getScaleUseTypeDescription(scaleUseType, partyControl.getDefaultLanguage());
        }

        if(scaleUseTypeDescription == null) {
            description = scaleUseType.getLastDetail().getScaleUseTypeName();
        } else {
            description = scaleUseTypeDescription.getDescription();
        }

        return description;
    }

    public ScaleUseTypeDescriptionTransfer getScaleUseTypeDescriptionTransfer(UserVisit userVisit, ScaleUseTypeDescription scaleUseTypeDescription) {
        return scaleUseTypeDescriptionTransferCache.getScaleUseTypeDescriptionTransfer(userVisit, scaleUseTypeDescription);
    }

    public List<ScaleUseTypeDescriptionTransfer> getScaleUseTypeDescriptionTransfersByScaleUseType(UserVisit userVisit, ScaleUseType scaleUseType) {
        var scaleUseTypeDescriptions = getScaleUseTypeDescriptionsByScaleUseType(scaleUseType);
        List<ScaleUseTypeDescriptionTransfer> scaleUseTypeDescriptionTransfers = new ArrayList<>(scaleUseTypeDescriptions.size());

        scaleUseTypeDescriptions.forEach((scaleUseTypeDescription) ->
                scaleUseTypeDescriptionTransfers.add(scaleUseTypeDescriptionTransferCache.getScaleUseTypeDescriptionTransfer(userVisit, scaleUseTypeDescription))
        );

        return scaleUseTypeDescriptionTransfers;
    }

    public void updateScaleUseTypeDescriptionFromValue(ScaleUseTypeDescriptionValue scaleUseTypeDescriptionValue, BasePK updatedBy) {
        if(scaleUseTypeDescriptionValue.hasBeenModified()) {
            var scaleUseTypeDescription = scaleUseTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    scaleUseTypeDescriptionValue.getPrimaryKey());

            scaleUseTypeDescription.setThruTime(session.getStartTime());
            scaleUseTypeDescription.store();

            var scaleUseType = scaleUseTypeDescription.getScaleUseType();
            var language = scaleUseTypeDescription.getLanguage();
            var description = scaleUseTypeDescriptionValue.getDescription();

            scaleUseTypeDescription = scaleUseTypeDescriptionFactory.create(scaleUseType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(scaleUseType.getPrimaryKey(), EventTypes.MODIFY, scaleUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteScaleUseTypeDescription(ScaleUseTypeDescription scaleUseTypeDescription, BasePK deletedBy) {
        scaleUseTypeDescription.setThruTime(session.getStartTime());

        sendEvent(scaleUseTypeDescription.getScaleUseTypePK(), EventTypes.MODIFY, scaleUseTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteScaleUseTypeDescriptionsByScaleUseType(ScaleUseType scaleUseType, BasePK deletedBy) {
        var scaleUseTypeDescriptions = getScaleUseTypeDescriptionsByScaleUseTypeForUpdate(scaleUseType);

        scaleUseTypeDescriptions.forEach((scaleUseTypeDescription) -> 
                deleteScaleUseTypeDescription(scaleUseTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Party Scale Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyScaleUseFactory partyScaleUseFactory;

    @Inject
    private PartyScaleUseTransferCache partyScaleUseTransferCache;

    public PartyScaleUse createPartyScaleUse(Party party, ScaleUseType scaleUseType, Scale scale, BasePK createdBy) {
        var partyScaleUse = partyScaleUseFactory.create(party, scaleUseType, scale,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyScaleUse.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyScaleUse;
    }

    public long countPartyScaleUsesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyScaleUses)
                .where(PartyScaleUses.PARTY.eq(party.getPrimaryKey()),
                        PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyScaleUsesByScaleUseType(final ScaleUseType scaleUseType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyScaleUses)
                .where(PartyScaleUses.SCALE_USE_TYPE.eq(scaleUseType.getPrimaryKey()),
                        PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyScaleUsesByScale(final Scale scale) {
        return session.getDslContext()
                .selectCount()
                .from(PartyScaleUses)
                .where(PartyScaleUses.SCALE.eq(scale.getPrimaryKey()),
                        PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyScaleUse getPartyScaleUse(Party party, ScaleUseType scaleUseType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyScaleUses.fields())
                .from(PartyScaleUses)
                .where(PartyScaleUses.PARTY.eq(party.getPrimaryKey()),
                        PartyScaleUses.SCALE_USE_TYPE.eq(scaleUseType.getPrimaryKey()),
                        PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyScaleUseFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyScaleUse getPartyScaleUse(Party party, ScaleUseType scaleUseType) {
        return getPartyScaleUse(party, scaleUseType, EntityPermission.READ_ONLY);
    }

    public PartyScaleUse getPartyScaleUseUsingNames(Party party, String scaleUseTypeName) {
        var scaleUseType = getScaleUseTypeByName(scaleUseTypeName);
        PartyScaleUse partyScaleUse = null;

        if(scaleUseType != null) {
            partyScaleUse = getPartyScaleUse(party, scaleUseType, EntityPermission.READ_ONLY);
        }

        return partyScaleUse;
    }

    public PartyScaleUse getPartyScaleUseForUpdate(Party party, ScaleUseType scaleUseType) {
        return getPartyScaleUse(party, scaleUseType, EntityPermission.READ_WRITE);
    }

    private List<PartyScaleUse> getPartyScaleUsesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyScaleUses.fields())
                    .from(PartyScaleUses)
                    .join(ScaleUseTypes).onKey(PARTY_SCALE_USES_SCALE_USE_TYPE_FK)
                    .join(ScaleUseTypeDetails).onKey(SCALE_USE_TYPES_LAST_DETAIL_FK)
                    .where(PartyScaleUses.PARTY.eq(party.getPrimaryKey()),
                            PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ScaleUseTypeDetails.SORT_ORDER, ScaleUseTypeDetails.SCALE_USE_TYPE_NAME),
                    PartyScaleUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyScaleUses.fields())
                    .from(PartyScaleUses)
                    .where(PartyScaleUses.PARTY.eq(party.getPrimaryKey()),
                            PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyScaleUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyScaleUse> getPartyScaleUsesByParty(Party party) {
        return getPartyScaleUsesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyScaleUse> getPartyScaleUsesByPartyForUpdate(Party party) {
        return getPartyScaleUsesByParty(party, EntityPermission.READ_WRITE);
    }

    private List<PartyScaleUse> getPartyScaleUsesByScale(Scale scale, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyScaleUses.fields())
                    .from(PartyScaleUses)
                    .join(Parties).onKey(PARTY_SCALE_USES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(ScaleUseTypes).onKey(PARTY_SCALE_USES_SCALE_USE_TYPE_FK)
                    .join(ScaleUseTypeDetails).onKey(SCALE_USE_TYPES_LAST_DETAIL_FK)
                    .where(PartyScaleUses.SCALE.eq(scale.getPrimaryKey()),
                            PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME, ScaleUseTypeDetails.SORT_ORDER, ScaleUseTypeDetails.SCALE_USE_TYPE_NAME),
                    PartyScaleUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyScaleUses.fields())
                    .from(PartyScaleUses)
                    .where(PartyScaleUses.SCALE.eq(scale.getPrimaryKey()),
                            PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyScaleUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScale(Scale scale) {
        return getPartyScaleUsesByScale(scale, EntityPermission.READ_ONLY);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScaleForUpdate(Scale scale) {
        return getPartyScaleUsesByScale(scale, EntityPermission.READ_WRITE);
    }

    private List<PartyScaleUse> getPartyScaleUsesByScaleUseType(ScaleUseType scaleUseType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyScaleUses.fields())
                    .from(PartyScaleUses)
                    .join(Parties).onKey(PARTY_SCALE_USES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyScaleUses.SCALE_USE_TYPE.eq(scaleUseType.getPrimaryKey()),
                            PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyScaleUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyScaleUses.fields())
                    .from(PartyScaleUses)
                    .where(PartyScaleUses.SCALE_USE_TYPE.eq(scaleUseType.getPrimaryKey()),
                            PartyScaleUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyScaleUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScaleUseType(ScaleUseType scaleUseType) {
        return getPartyScaleUsesByScaleUseType(scaleUseType, EntityPermission.READ_ONLY);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScaleUseTypeForUpdate(ScaleUseType scaleUseType) {
        return getPartyScaleUsesByScaleUseType(scaleUseType, EntityPermission.READ_WRITE);
    }

    public PartyScaleUseValue getPartyScaleUseValue(PartyScaleUse partyScaleUse) {
        return partyScaleUse == null? null: partyScaleUse.getPartyScaleUseValue().clone();
    }
    
    public PartyScaleUseValue getPartyScaleUseValueForUpdate(Party party, ScaleUseType scaleUseType) {
        return getPartyScaleUseValue(getPartyScaleUseForUpdate(party, scaleUseType));
    }
    
    public PartyScaleUseTransfer getPartyScaleUseTransfer(UserVisit userVisit, PartyScaleUse partyScaleUse) {
        return partyScaleUseTransferCache.getPartyScaleUseTransfer(userVisit, partyScaleUse);
    }

    public List<PartyScaleUseTransfer> getPartyScaleUseTransfers(UserVisit userVisit, Collection<PartyScaleUse> partyScaleUses) {
        List<PartyScaleUseTransfer> partyScaleUseTransfers = new ArrayList<>(partyScaleUses.size());

        partyScaleUses.forEach((partyScaleUse) ->
                partyScaleUseTransfers.add(partyScaleUseTransferCache.getPartyScaleUseTransfer(userVisit, partyScaleUse))
        );

        return partyScaleUseTransfers;
    }

    public List<PartyScaleUseTransfer> getPartyScaleUseTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyScaleUseTransfers(userVisit, getPartyScaleUsesByParty(party));
    }

    public void updatePartyScaleUseFromValue(PartyScaleUseValue partyScaleUseValue, BasePK updatedBy) {
        if(partyScaleUseValue.hasBeenModified()) {
            var partyScaleUse = partyScaleUseFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyScaleUseValue.getPrimaryKey());
            
            partyScaleUse.setThruTime(session.getStartTime());
            partyScaleUse.store();

            var partyPK = partyScaleUse.getPartyPK(); // Not updated
            var scaleUseTypePK = partyScaleUse.getScaleUseTypePK(); // Not updated
            var scalePK = partyScaleUseValue.getScalePK();
            
            partyScaleUse = partyScaleUseFactory.create(partyPK, scaleUseTypePK,
                    scalePK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyScaleUse.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyScaleUse(PartyScaleUse partyScaleUse, BasePK deletedBy) {
        partyScaleUse.setThruTime(session.getStartTime());

        sendEvent(partyScaleUse.getPartyPK(), EventTypes.MODIFY, partyScaleUse.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyScaleUses(List<PartyScaleUse> partyScaleUses, BasePK deletedBy) {
        partyScaleUses.forEach((partyScaleUse) -> 
                deletePartyScaleUse(partyScaleUse, deletedBy)
        );
    }

    public void deletePartyScaleUsesByParty(Party party, BasePK deletedBy) {
        deletePartyScaleUses(getPartyScaleUsesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyScaleUsesByScale(Scale scale, BasePK deletedBy) {
        deletePartyScaleUses(getPartyScaleUsesByScaleForUpdate(scale), deletedBy);
    }

    public void deletePartyScaleUsesByScaleUseType(ScaleUseType scaleUseType, BasePK deletedBy) {
        deletePartyScaleUses(getPartyScaleUsesByScaleUseTypeForUpdate(scaleUseType), deletedBy);
    }

}
