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

package com.echothree.model.control.license.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.license.common.choice.LicenseTypeChoicesBean;
import com.echothree.model.control.license.common.transfer.LicenseTypeDescriptionTransfer;
import com.echothree.model.control.license.common.transfer.LicenseTypeTransfer;
import com.echothree.model.control.license.server.transfer.LicenseTransferCaches;
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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LicenseControl
        extends BaseModelControl {
    
    /** Creates a new instance of LicenseControl */
    protected LicenseControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   License Transfer Caches
    // --------------------------------------------------------------------------------
    
    private LicenseTransferCaches licenseTransferCaches;
    
    public LicenseTransferCaches getLicenseTransferCaches(UserVisit userVisit) {
        if(licenseTransferCaches == null) {
            licenseTransferCaches = new LicenseTransferCaches(userVisit, this);
        }
        
        return licenseTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   License Types
    // --------------------------------------------------------------------------------

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

        var licenseType = LicenseTypeFactory.getInstance().create();
        var licenseTypeDetail = LicenseTypeDetailFactory.getInstance().create(licenseType, licenseTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        licenseType = LicenseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, licenseType.getPrimaryKey());
        licenseType.setActiveDetail(licenseTypeDetail);
        licenseType.setLastDetail(licenseTypeDetail);
        licenseType.store();

        sendEvent(licenseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return licenseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.LicenseType */
    public LicenseType getLicenseTypeByEntityInstance(EntityInstance entityInstance) {
        var pk = new LicenseTypePK(entityInstance.getEntityUniqueId());
        var licenseType = LicenseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return licenseType;
    }

    private static final Map<EntityPermission, String> getLicenseTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM licensetypes, licensetypedetails " +
                "WHERE lcnstyp_activedetailid = lcnstypdt_licensetypedetailid " +
                "AND lcnstypdt_licensetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM licensetypes, licensetypedetails " +
                "WHERE lcnstyp_activedetailid = lcnstypdt_licensetypedetailid " +
                "AND lcnstypdt_licensetypename = ? " +
                "FOR UPDATE");
        getLicenseTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LicenseType getLicenseTypeByName(String licenseTypeName, EntityPermission entityPermission) {
        return LicenseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getLicenseTypeByNameQueries, licenseTypeName);
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

    private static final Map<EntityPermission, String> getDefaultLicenseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM licensetypes, licensetypedetails " +
                "WHERE lcnstyp_activedetailid = lcnstypdt_licensetypedetailid " +
                "AND lcnstypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM licensetypes, licensetypedetails " +
                "WHERE lcnstyp_activedetailid = lcnstypdt_licensetypedetailid " +
                "AND lcnstypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLicenseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LicenseType getDefaultLicenseType(EntityPermission entityPermission) {
        return LicenseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLicenseTypeQueries);
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

    private static final Map<EntityPermission, String> getLicenseTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM licensetypes, licensetypedetails " +
                "WHERE lcnstyp_activedetailid = lcnstypdt_licensetypedetailid " +
                "ORDER BY lcnstypdt_sortorder, lcnstypdt_licensetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM licensetypes, licensetypedetails " +
                "WHERE lcnstyp_activedetailid = lcnstypdt_licensetypedetailid " +
                "FOR UPDATE");
        getLicenseTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LicenseType> getLicenseTypes(EntityPermission entityPermission) {
        return LicenseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLicenseTypesQueries);
    }

    public List<LicenseType> getLicenseTypes() {
        return getLicenseTypes(EntityPermission.READ_ONLY);
    }

    public List<LicenseType> getLicenseTypesForUpdate() {
        return getLicenseTypes(EntityPermission.READ_WRITE);
    }

   public LicenseTypeTransfer getLicenseTypeTransfer(UserVisit userVisit, LicenseType licenseType) {
        return getLicenseTransferCaches(userVisit).getLicenseTypeTransferCache().getLicenseTypeTransfer(licenseType);
    }

    public List<LicenseTypeTransfer> getLicenseTypeTransfers(UserVisit userVisit) {
        var licenseTypes = getLicenseTypes();
        List<LicenseTypeTransfer> licenseTypeTransfers = new ArrayList<>(licenseTypes.size());
        var licenseTypeTransferCache = getLicenseTransferCaches(userVisit).getLicenseTypeTransferCache();

        licenseTypes.forEach((licenseType) ->
                licenseTypeTransfers.add(licenseTypeTransferCache.getLicenseTypeTransfer(licenseType))
        );

        return licenseTypeTransfers;
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
            var licenseType = LicenseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     licenseTypeDetailValue.getLicenseTypePK());
            var licenseTypeDetail = licenseType.getActiveDetailForUpdate();

            licenseTypeDetail.setThruTime(session.START_TIME_LONG);
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

            licenseTypeDetail = LicenseTypeDetailFactory.getInstance().create(licenseTypePK, licenseTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

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

        licenseTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public LicenseTypeDescription createLicenseTypeDescription(LicenseType licenseType, Language language, String description, BasePK createdBy) {
        var licenseTypeDescription = LicenseTypeDescriptionFactory.getInstance().create(licenseType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(licenseType.getPrimaryKey(), EventTypes.MODIFY, licenseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return licenseTypeDescription;
    }

    private static final Map<EntityPermission, String> getLicenseTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM licensetypedescriptions " +
                "WHERE lcnstypd_lcnstyp_licensetypeid = ? AND lcnstypd_lang_languageid = ? AND lcnstypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM licensetypedescriptions " +
                "WHERE lcnstypd_lcnstyp_licensetypeid = ? AND lcnstypd_lang_languageid = ? AND lcnstypd_thrutime = ? " +
                "FOR UPDATE");
        getLicenseTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LicenseTypeDescription getLicenseTypeDescription(LicenseType licenseType, Language language, EntityPermission entityPermission) {
        return LicenseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLicenseTypeDescriptionQueries,
                licenseType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getLicenseTypeDescriptionsByLicenseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM licensetypedescriptions, languages " +
                "WHERE lcnstypd_lcnstyp_licensetypeid = ? AND lcnstypd_thrutime = ? AND lcnstypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM licensetypedescriptions " +
                "WHERE lcnstypd_lcnstyp_licensetypeid = ? AND lcnstypd_thrutime = ? " +
                "FOR UPDATE");
        getLicenseTypeDescriptionsByLicenseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LicenseTypeDescription> getLicenseTypeDescriptionsByLicenseType(LicenseType licenseType, EntityPermission entityPermission) {
        return LicenseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLicenseTypeDescriptionsByLicenseTypeQueries,
                licenseType, Session.MAX_TIME);
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
            licenseTypeDescription = getLicenseTypeDescription(licenseType, getPartyControl().getDefaultLanguage());
        }

        if(licenseTypeDescription == null) {
            description = licenseType.getLastDetail().getLicenseTypeName();
        } else {
            description = licenseTypeDescription.getDescription();
        }

        return description;
    }

    public LicenseTypeDescriptionTransfer getLicenseTypeDescriptionTransfer(UserVisit userVisit, LicenseTypeDescription licenseTypeDescription) {
        return getLicenseTransferCaches(userVisit).getLicenseTypeDescriptionTransferCache().getLicenseTypeDescriptionTransfer(licenseTypeDescription);
    }

    public List<LicenseTypeDescriptionTransfer> getLicenseTypeDescriptionTransfersByLicenseType(UserVisit userVisit, LicenseType licenseType) {
        var licenseTypeDescriptions = getLicenseTypeDescriptionsByLicenseType(licenseType);
        List<LicenseTypeDescriptionTransfer> licenseTypeDescriptionTransfers = new ArrayList<>(licenseTypeDescriptions.size());
        var licenseTypeDescriptionTransferCache = getLicenseTransferCaches(userVisit).getLicenseTypeDescriptionTransferCache();

        licenseTypeDescriptions.forEach((licenseTypeDescription) ->
                licenseTypeDescriptionTransfers.add(licenseTypeDescriptionTransferCache.getLicenseTypeDescriptionTransfer(licenseTypeDescription))
        );

        return licenseTypeDescriptionTransfers;
    }

    public void updateLicenseTypeDescriptionFromValue(LicenseTypeDescriptionValue licenseTypeDescriptionValue, BasePK updatedBy) {
        if(licenseTypeDescriptionValue.hasBeenModified()) {
            var licenseTypeDescription = LicenseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    licenseTypeDescriptionValue.getPrimaryKey());

            licenseTypeDescription.setThruTime(session.START_TIME_LONG);
            licenseTypeDescription.store();

            var licenseType = licenseTypeDescription.getLicenseType();
            var language = licenseTypeDescription.getLanguage();
            var description = licenseTypeDescriptionValue.getDescription();

            licenseTypeDescription = LicenseTypeDescriptionFactory.getInstance().create(licenseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(licenseType.getPrimaryKey(), EventTypes.MODIFY, licenseTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLicenseTypeDescription(LicenseTypeDescription licenseTypeDescription, BasePK deletedBy) {
        licenseTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(licenseTypeDescription.getLicenseTypePK(), EventTypes.MODIFY, licenseTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLicenseTypeDescriptionsByLicenseType(LicenseType licenseType, BasePK deletedBy) {
        var licenseTypeDescriptions = getLicenseTypeDescriptionsByLicenseTypeForUpdate(licenseType);

        licenseTypeDescriptions.forEach((licenseTypeDescription) -> 
                deleteLicenseTypeDescription(licenseTypeDescription, deletedBy)
        );
    }

}
