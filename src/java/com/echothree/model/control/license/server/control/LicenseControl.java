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

package com.echothree.model.control.license.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.license.common.choice.LicenseTypeChoicesBean;
import com.echothree.model.control.license.common.transfer.LicenseTypeDescriptionTransfer;
import com.echothree.model.control.license.common.transfer.LicenseTypeTransfer;
import com.echothree.model.control.license.server.transfer.LicenseTypeDescriptionTransferCache;
import com.echothree.model.control.license.server.transfer.LicenseTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.license.common.pk.LicenseTypePK;
import com.echothree.model.data.license.server.entity.LicenseType;
import com.echothree.model.data.license.server.entity.LicenseTypeDescription;
import com.echothree.model.data.license.server.factory.LicenseTypeDescriptionFactory;
import com.echothree.model.data.license.server.factory.LicenseTypeDetailFactory;
import com.echothree.model.data.license.server.factory.LicenseTypeFactory;
import com.echothree.model.data.license.server.value.LicenseTypeDescriptionValue;
import com.echothree.model.data.license.server.value.LicenseTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.license.LicenseForeignKeys.LICENSE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.license.LicenseForeignKeys.LICENSE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.license.LicenseTypeDescriptions.LicenseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.license.LicenseTypeDetails.LicenseTypeDetails;
import static com.echothree.model.jooq.server.tables.license.LicenseTypes.LicenseTypes;
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
public class LicenseControl
        extends BaseModelControl {
    
    /** Creates a new instance of LicenseControl */
    protected LicenseControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   License Types
    // --------------------------------------------------------------------------------

    @Inject
    protected LicenseTypeDetailFactory licenseTypeDetailFactory;

    @Inject
    protected LicenseTypeFactory licenseTypeFactory;

    @Inject
    LicenseTypeTransferCache licenseTypeTransferCache;

    public LicenseType createLicenseType(String licenseTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLicenseType = getDefaultLicenseType();
        var defaultFound = defaultLicenseType != null;

        if(defaultFound && isDefault) {
            var defaultLicenseTypeDetailValue = getDefaultLicenseTypeDetailValueForUpdate();

            defaultLicenseTypeDetailValue.setIsDefault(false);
            updateLicenseTypeFromValue(defaultLicenseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var licenseType = licenseTypeFactory.create();
        var licenseTypeDetail = licenseTypeDetailFactory.create(licenseType, licenseTypeName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        licenseType = licenseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, licenseType.getPrimaryKey());
        licenseType.setActiveDetail(licenseTypeDetail);
        licenseType.setLastDetail(licenseTypeDetail);
        licenseType.store();

        sendEvent(licenseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return licenseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.LicenseType */
    public LicenseType getLicenseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LicenseTypePK(entityInstance.getEntityUniqueId());

        return licenseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public LicenseType getLicenseTypeByEntityInstance(EntityInstance entityInstance) {
        return getLicenseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LicenseType getLicenseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLicenseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLicenseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(LicenseTypes)
                .join(LicenseTypeDetails).onKey(LICENSE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private LicenseType getLicenseTypeByName(String licenseTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LicenseTypes.fields())
                .from(LicenseTypes)
                .join(LicenseTypeDetails).onKey(LICENSE_TYPES_ACTIVE_DETAIL_FK)
                .where(LicenseTypeDetails.LICENSE_TYPE_NAME.eq(licenseTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return licenseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public LicenseType getLicenseTypeByName(String licenseTypeName) {
        return getLicenseTypeByName(licenseTypeName, EntityPermission.READ_ONLY);
    }

    public LicenseType getLicenseTypeByNameForUpdate(String licenseTypeName) {
        return getLicenseTypeByName(licenseTypeName, EntityPermission.READ_WRITE);
    }

    public LicenseTypeDetailValue getLicenseTypeDetailValueForUpdate(LicenseType licenseType) {
        return licenseType == null? null: licenseType.getLastDetailForUpdate().getLicenseTypeDetailValue().clone();
    }

    public LicenseTypeDetailValue getLicenseTypeDetailValueByNameForUpdate(String licenseTypeName) {
        return getLicenseTypeDetailValueForUpdate(getLicenseTypeByNameForUpdate(licenseTypeName));
    }

    private LicenseType getDefaultLicenseType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LicenseTypes.fields())
                .from(LicenseTypes)
                .join(LicenseTypeDetails).onKey(LICENSE_TYPES_ACTIVE_DETAIL_FK)
                .where(LicenseTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return licenseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public LicenseType getDefaultLicenseType() {
        return getDefaultLicenseType(EntityPermission.READ_ONLY);
    }

    public LicenseType getDefaultLicenseTypeForUpdate() {
        return getDefaultLicenseType(EntityPermission.READ_WRITE);
    }

    public LicenseTypeDetailValue getDefaultLicenseTypeDetailValueForUpdate() {
        return getDefaultLicenseTypeForUpdate().getLastDetailForUpdate().getLicenseTypeDetailValue().clone();
    }

    private List<LicenseType> getLicenseTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LicenseTypes.fields())
                .from(LicenseTypes)
                .join(LicenseTypeDetails).onKey(LICENSE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LicenseTypeDetails.SORT_ORDER, LicenseTypeDetails.LICENSE_TYPE_NAME),
                    LicenseTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return licenseTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LicenseType> getLicenseTypes() {
        return getLicenseTypes(EntityPermission.READ_ONLY);
    }

    public List<LicenseType> getLicenseTypesForUpdate() {
        return getLicenseTypes(EntityPermission.READ_WRITE);
    }

    public LicenseTypeTransfer getLicenseTypeTransfer(UserVisit userVisit, LicenseType licenseType) {
        return licenseTypeTransferCache.getLicenseTypeTransfer(userVisit, licenseType);
    }

    public List<LicenseTypeTransfer> getLicenseTypeTransfers(UserVisit userVisit, Collection<LicenseType> licenseTypes) {
        var licenseTypeTransfers = new ArrayList<LicenseTypeTransfer>(licenseTypes.size());

        licenseTypes.forEach((licenseType) ->
                licenseTypeTransfers.add(licenseTypeTransferCache.getLicenseTypeTransfer(userVisit, licenseType))
        );

        return licenseTypeTransfers;
    }

    public List<LicenseTypeTransfer> getLicenseTypeTransfers(UserVisit userVisit) {
        return getLicenseTypeTransfers(userVisit, getLicenseTypes());
    }

    public LicenseTypeChoicesBean getLicenseTypeChoices(String defaultLicenseTypeChoice, Language language, boolean allowNullChoice) {
        var licenseTypes = getLicenseTypes();
        var size = licenseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLicenseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var licenseType : licenseTypes) {
            var licenseTypeDetail = licenseType.getLastDetail();

            var label = getBestLicenseTypeDescription(licenseType, language);
            var value = licenseTypeDetail.getLicenseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultLicenseTypeChoice != null && defaultLicenseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && licenseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LicenseTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateLicenseTypeFromValue(LicenseTypeDetailValue licenseTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(licenseTypeDetailValue.hasBeenModified()) {
            var licenseType = licenseTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     licenseTypeDetailValue.getLicenseTypePK());
            var licenseTypeDetail = licenseType.getActiveDetailForUpdate();

            licenseTypeDetail.setThruTime(session.getStartTime());
            licenseTypeDetail.store();

            var licenseTypePK = licenseTypeDetail.getLicenseTypePK(); // Not updated
            var licenseTypeName = licenseTypeDetailValue.getLicenseTypeName();
            var isDefault = licenseTypeDetailValue.getIsDefault();
            var sortOrder = licenseTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultLicenseType = getDefaultLicenseType();
                var defaultFound = defaultLicenseType != null && !defaultLicenseType.equals(licenseType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLicenseTypeDetailValue = getDefaultLicenseTypeDetailValueForUpdate();

                    defaultLicenseTypeDetailValue.setIsDefault(false);
                    updateLicenseTypeFromValue(defaultLicenseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            licenseTypeDetail = licenseTypeDetailFactory.create(licenseTypePK, licenseTypeName, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);

            licenseType.setActiveDetail(licenseTypeDetail);
            licenseType.setLastDetail(licenseTypeDetail);

            sendEvent(licenseTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateLicenseTypeFromValue(LicenseTypeDetailValue licenseTypeDetailValue, BasePK updatedBy) {
        updateLicenseTypeFromValue(licenseTypeDetailValue, true, updatedBy);
    }

    private void deleteLicenseType(LicenseType licenseType, boolean checkDefault, BasePK deletedBy) {
        var licenseTypeDetail = licenseType.getLastDetailForUpdate();

        deleteLicenseTypeDescriptionsByLicenseType(licenseType, deletedBy);

        licenseTypeDetail.setThruTime(session.getStartTime());
        licenseType.setActiveDetail(null);
        licenseType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultLicenseType = getDefaultLicenseType();

            if(defaultLicenseType == null) {
                var licenseTypes = getLicenseTypesForUpdate();

                if(!licenseTypes.isEmpty()) {
                    var iter = licenseTypes.iterator();
                    if(iter.hasNext()) {
                        defaultLicenseType = iter.next();
                    }
                    var licenseTypeDetailValue = Objects.requireNonNull(defaultLicenseType).getLastDetailForUpdate().getLicenseTypeDetailValue().clone();

                    licenseTypeDetailValue.setIsDefault(true);
                    updateLicenseTypeFromValue(licenseTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(licenseType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteLicenseType(LicenseType licenseType, BasePK deletedBy) {
        deleteLicenseType(licenseType, true, deletedBy);
    }

    private void deleteLicenseTypes(List<LicenseType> licenseTypes, boolean checkDefault, BasePK deletedBy) {
        licenseTypes.forEach((licenseType) -> deleteLicenseType(licenseType, checkDefault, deletedBy));
    }

    public void deleteLicenseTypes(List<LicenseType> licenseTypes, BasePK deletedBy) {
        deleteLicenseTypes(licenseTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   License Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected LicenseTypeDescriptionFactory licenseTypeDescriptionFactory;

    @Inject
    LicenseTypeDescriptionTransferCache licenseTypeDescriptionTransferCache;

    public LicenseTypeDescription createLicenseTypeDescription(LicenseType licenseType, Language language, String description, BasePK createdBy) {
        var licenseTypeDescription = licenseTypeDescriptionFactory.create(licenseType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(licenseType.getPrimaryKey(), EventTypes.MODIFY, licenseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return licenseTypeDescription;
    }

    private LicenseTypeDescription getLicenseTypeDescription(LicenseType licenseType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LicenseTypeDescriptions.fields())
                .from(LicenseTypeDescriptions)
                .where(LicenseTypeDescriptions.LICENSE_TYPE.eq(licenseType.getPrimaryKey()),
                        LicenseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        LicenseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return licenseTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public LicenseTypeDescription getLicenseTypeDescription(LicenseType licenseType, Language language) {
        return getLicenseTypeDescription(licenseType, language, EntityPermission.READ_ONLY);
    }

    public LicenseTypeDescription getLicenseTypeDescriptionForUpdate(LicenseType licenseType, Language language) {
        return getLicenseTypeDescription(licenseType, language, EntityPermission.READ_WRITE);
    }

    public LicenseTypeDescriptionValue getLicenseTypeDescriptionValue(LicenseTypeDescription licenseTypeDescription) {
        return licenseTypeDescription == null? null: licenseTypeDescription.getLicenseTypeDescriptionValue().clone();
    }

    public LicenseTypeDescriptionValue getLicenseTypeDescriptionValueForUpdate(LicenseType licenseType, Language language) {
        return getLicenseTypeDescriptionValue(getLicenseTypeDescriptionForUpdate(licenseType, language));
    }

    private List<LicenseTypeDescription> getLicenseTypeDescriptionsByLicenseType(LicenseType licenseType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LicenseTypeDescriptions.fields())
                    .from(LicenseTypeDescriptions)
                    .join(Languages).onKey(LICENSE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(LicenseTypeDescriptions.LICENSE_TYPE.eq(licenseType.getPrimaryKey()),
                            LicenseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    LicenseTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LicenseTypeDescriptions.fields())
                    .from(LicenseTypeDescriptions)
                    .where(LicenseTypeDescriptions.LICENSE_TYPE.eq(licenseType.getPrimaryKey()),
                            LicenseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return licenseTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LicenseTypeDescription> getLicenseTypeDescriptionsByLicenseType(LicenseType licenseType) {
        return getLicenseTypeDescriptionsByLicenseType(licenseType, EntityPermission.READ_ONLY);
    }

    public List<LicenseTypeDescription> getLicenseTypeDescriptionsByLicenseTypeForUpdate(LicenseType licenseType) {
        return getLicenseTypeDescriptionsByLicenseType(licenseType, EntityPermission.READ_WRITE);
    }

    public String getBestLicenseTypeDescription(LicenseType licenseType, Language language) {
        String description;
        var licenseTypeDescription = getLicenseTypeDescription(licenseType, language);

        if(licenseTypeDescription == null && !language.getIsDefault()) {
            licenseTypeDescription = getLicenseTypeDescription(licenseType, partyControl.getDefaultLanguage());
        }

        if(licenseTypeDescription == null) {
            description = licenseType.getLastDetail().getLicenseTypeName();
        } else {
            description = licenseTypeDescription.getDescription();
        }

        return description;
    }

    public LicenseTypeDescriptionTransfer getLicenseTypeDescriptionTransfer(UserVisit userVisit, LicenseTypeDescription licenseTypeDescription) {
        return licenseTypeDescriptionTransferCache.getLicenseTypeDescriptionTransfer(userVisit, licenseTypeDescription);
    }

    public List<LicenseTypeDescriptionTransfer> getLicenseTypeDescriptionTransfersByLicenseType(UserVisit userVisit, LicenseType licenseType) {
        var licenseTypeDescriptions = getLicenseTypeDescriptionsByLicenseType(licenseType);
        List<LicenseTypeDescriptionTransfer> licenseTypeDescriptionTransfers = new ArrayList<>(licenseTypeDescriptions.size());

        licenseTypeDescriptions.forEach((licenseTypeDescription) ->
                licenseTypeDescriptionTransfers.add(licenseTypeDescriptionTransferCache.getLicenseTypeDescriptionTransfer(userVisit, licenseTypeDescription))
        );

        return licenseTypeDescriptionTransfers;
    }

    public void updateLicenseTypeDescriptionFromValue(LicenseTypeDescriptionValue licenseTypeDescriptionValue, BasePK updatedBy) {
        if(licenseTypeDescriptionValue.hasBeenModified()) {
            var licenseTypeDescription = licenseTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    licenseTypeDescriptionValue.getPrimaryKey());

            licenseTypeDescription.setThruTime(session.getStartTime());
            licenseTypeDescription.store();

            var licenseType = licenseTypeDescription.getLicenseType();
            var language = licenseTypeDescription.getLanguage();
            var description = licenseTypeDescriptionValue.getDescription();

            licenseTypeDescription = licenseTypeDescriptionFactory.create(licenseType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(licenseType.getPrimaryKey(), EventTypes.MODIFY, licenseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLicenseTypeDescription(LicenseTypeDescription licenseTypeDescription, BasePK deletedBy) {
        licenseTypeDescription.setThruTime(session.getStartTime());

        sendEvent(licenseTypeDescription.getLicenseTypePK(), EventTypes.MODIFY, licenseTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLicenseTypeDescriptionsByLicenseType(LicenseType licenseType, BasePK deletedBy) {
        var licenseTypeDescriptions = getLicenseTypeDescriptionsByLicenseTypeForUpdate(licenseType);

        licenseTypeDescriptions.forEach((licenseTypeDescription) -> 
                deleteLicenseTypeDescription(licenseTypeDescription, deletedBy)
        );
    }

}
