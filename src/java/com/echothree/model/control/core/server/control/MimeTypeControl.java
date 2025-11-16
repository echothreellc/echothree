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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.MimeTypeUsageTypeChoicesBean;
import com.echothree.model.control.core.common.transfer.MimeTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeFileExtensionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDescription;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageTypeDescription;
import com.echothree.model.data.core.server.factory.MimeTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeDetailFactory;
import com.echothree.model.data.core.server.factory.MimeTypeFactory;
import com.echothree.model.data.core.server.factory.MimeTypeFileExtensionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageTypeFactory;
import com.echothree.model.data.core.server.value.MimeTypeDescriptionValue;
import com.echothree.model.data.core.server.value.MimeTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MimeTypeControl
        extends BaseCoreControl {

    /** Creates a new instance of MimeTypeControl */
    protected MimeTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------

    public MimeTypeUsageType createMimeTypeUsageType(String mimeTypeUsageTypeName, Boolean isDefault, Integer sortOrder) {
        return MimeTypeUsageTypeFactory.getInstance().create(mimeTypeUsageTypeName, isDefault, sortOrder);
    }

    public List<MimeTypeUsageType> getMimeTypeUsageTypes() {
        var ps = MimeTypeUsageTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                        "FROM mimetypeusagetypes " +
                        "ORDER BY mtyput_sortorder, mtyput_mimetypeusagetypename");

        return MimeTypeUsageTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public MimeTypeUsageType getMimeTypeUsageTypeByName(String mimeTypeUsageTypeName) {
        MimeTypeUsageType mimeTypeUsageType;

        try {
            var ps = MimeTypeUsageTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM mimetypeusagetypes " +
                            "WHERE mtyput_mimetypeusagetypename = ?");

            ps.setString(1, mimeTypeUsageTypeName);

            mimeTypeUsageType = MimeTypeUsageTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return mimeTypeUsageType;
    }

    public MimeTypeUsageTypeChoicesBean getMimeTypeUsageTypeChoices(String defaultMimeTypeUsageTypeChoice, Language language,
            boolean allowNullChoice) {
        var mimeTypeUsageTypes = getMimeTypeUsageTypes();
        var size = mimeTypeUsageTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultMimeTypeUsageTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var mimeTypeUsageType : mimeTypeUsageTypes) {
            var label = getBestMimeTypeUsageTypeDescription(mimeTypeUsageType, language);
            var value = mimeTypeUsageType.getMimeTypeUsageTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultMimeTypeUsageTypeChoice != null && defaultMimeTypeUsageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeUsageType.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new MimeTypeUsageTypeChoicesBean(labels, values, defaultValue);
    }

    public MimeTypeUsageTypeTransfer getMimeTypeUsageTypeTransfer(UserVisit userVisit, MimeTypeUsageType mimeTypeUsageType) {
        return coreTransferCaches.getMimeTypeUsageTypeTransferCache().getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
    }

    public List<MimeTypeUsageTypeTransfer> getMimeTypeUsageTypeTransfers(UserVisit userVisit, Collection<MimeTypeUsageType> mimeTypeUsageTypes) {
        List<MimeTypeUsageTypeTransfer> mimeTypeUsageTypeTransfers = new ArrayList<>(mimeTypeUsageTypes.size());
        var mimeTypeUsageTypeTransferCache = coreTransferCaches.getMimeTypeUsageTypeTransferCache();

        mimeTypeUsageTypes.forEach((mimeTypeUsageType) ->
                mimeTypeUsageTypeTransfers.add(mimeTypeUsageTypeTransferCache.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType))
        );

        return mimeTypeUsageTypeTransfers;
    }

    public List<MimeTypeUsageTypeTransfer> getMimeTypeUsageTypeTransfers(UserVisit userVisit) {
        return getMimeTypeUsageTypeTransfers(userVisit, getMimeTypeUsageTypes());
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------

    public MimeTypeUsageTypeDescription createMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language, String description) {
        return MimeTypeUsageTypeDescriptionFactory.getInstance().create(mimeTypeUsageType, language, description);
    }

    public MimeTypeUsageTypeDescription getMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language) {
        MimeTypeUsageTypeDescription mimeTypeUsageTypeDescription;

        try {
            var ps = MimeTypeUsageTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM mimetypeusagetypedescriptions " +
                            "WHERE mtyputd_mtyput_mimetypeusagetypeid = ? AND mtyputd_lang_languageid = ?");

            ps.setLong(1, mimeTypeUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());

            mimeTypeUsageTypeDescription = MimeTypeUsageTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return mimeTypeUsageTypeDescription;
    }

    public String getBestMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language) {
        String description;
        var mimeTypeUsageTypeDescription = getMimeTypeUsageTypeDescription(mimeTypeUsageType, language);

        if(mimeTypeUsageTypeDescription == null && !language.getIsDefault()) {
            mimeTypeUsageTypeDescription = getMimeTypeUsageTypeDescription(mimeTypeUsageType, partyControl.getDefaultLanguage());
        }

        if(mimeTypeUsageTypeDescription == null) {
            description = mimeTypeUsageType.getMimeTypeUsageTypeName();
        } else {
            description = mimeTypeUsageTypeDescription.getDescription();
        }

        return description;
    }

    // --------------------------------------------------------------------------------
    //   Mime Types
    // --------------------------------------------------------------------------------

    public MimeType createMimeType(String mimeTypeName, EntityAttributeType entityAttributeType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultMimeType = getDefaultMimeType();
        var defaultFound = defaultMimeType != null;

        if(defaultFound && isDefault) {
            var defaultMimeTypeDetailValue = getDefaultMimeTypeDetailValueForUpdate();

            defaultMimeTypeDetailValue.setIsDefault(false);
            updateMimeTypeFromValue(defaultMimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var mimeType = MimeTypeFactory.getInstance().create();
        var mimeTypeDetail = MimeTypeDetailFactory.getInstance().create(mimeType, mimeTypeName, entityAttributeType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                mimeType.getPrimaryKey());
        mimeType.setActiveDetail(mimeTypeDetail);
        mimeType.setLastDetail(mimeTypeDetail);
        mimeType.store();

        sendEvent(mimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return mimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.MimeType */
    public MimeType getMimeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new MimeTypePK(entityInstance.getEntityUniqueId());

        return MimeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public MimeType getMimeTypeByEntityInstance(EntityInstance entityInstance) {
        return getMimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public MimeType getMimeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getMimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countMimeTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM mimetypes
                JOIN mimetypedetails ON mtyp_activedetailid = mtypdt_mimetypedetailid
                """);
    }

    public long countMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM mimetypes
                JOIN mimetypedetails ON mtyp_activedetailid = mtypdt_mimetypedetailid
                JOIN mimetypeusages ON mtyp_mimetypeid = mtypu_mtyp_mimetypeid
                WHERE mtypu_mtyput_mimetypeusagetypeid = ?
                """, mimeTypeUsageType);
    }

    private static final Map<EntityPermission, String> getMimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM mimetypes, mimetypedetails " +
                        "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid " +
                        "AND mtypdt_mimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM mimetypes, mimetypedetails " +
                        "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid " +
                        "AND mtypdt_mimetypename = ? " +
                        "FOR UPDATE");
        getMimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeType getMimeTypeByName(String mimeTypeName, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getMimeTypeByNameQueries, mimeTypeName);
    }

    public MimeType getMimeTypeByName(String mimeTypeName) {
        return getMimeTypeByName(mimeTypeName, EntityPermission.READ_ONLY);
    }

    public MimeType getMimeTypeByNameForUpdate(String mimeTypeName) {
        return getMimeTypeByName(mimeTypeName, EntityPermission.READ_WRITE);
    }

    public MimeTypeDetailValue getMimeTypeDetailValueForUpdate(MimeType mimeType) {
        return mimeType == null? null: mimeType.getLastDetailForUpdate().getMimeTypeDetailValue().clone();
    }

    public MimeTypeDetailValue getMimeTypeDetailValueByNameForUpdate(String mimeTypeName) {
        return getMimeTypeDetailValueForUpdate(getMimeTypeByNameForUpdate(mimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultMimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "AND mtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "AND mtypdt_isdefault = 1 "
                        + "FOR UPDATE");
        getDefaultMimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeType getDefaultMimeType(EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultMimeTypeQueries);
    }

    public MimeType getDefaultMimeType() {
        return getDefaultMimeType(EntityPermission.READ_ONLY);
    }

    public MimeType getDefaultMimeTypeForUpdate() {
        return getDefaultMimeType(EntityPermission.READ_WRITE);
    }

    public MimeTypeDetailValue getDefaultMimeTypeDetailValueForUpdate() {
        return getDefaultMimeTypeForUpdate().getLastDetailForUpdate().getMimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getMimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "FOR UPDATE");
        getMimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypes(EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesQueries);
    }

    public List<MimeType> getMimeTypes() {
        return getMimeTypes(EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesForUpdate() {
        return getMimeTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getMimeTypesByEntityAttributeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "AND mtypdt_enat_entityattributetypeid = ? "
                        + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "AND mtypdt_enat_entityattributetypeid = ?"
                        + "FOR UPDATE");
        getMimeTypesByEntityAttributeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesByEntityAttributeTypeQueries,
                entityAttributeType);
    }

    public List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByEntityAttributeTypeForUpdate(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getMimeTypesByMimeTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails, mimetypeusages "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "AND mtyp_mimetypeid = mtypu_mtyp_mimetypeid AND mtypu_mtyput_mimetypeusagetypeid = ? "
                        + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM mimetypes, mimetypedetails, mimetypeusages "
                        + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                        + "AND mtyp_mimetypeid = mtypu_mtyp_mimetypeid AND mtypu_mtyput_mimetypeusagetypeid = ? "
                        + "FOR UPDATE");
        getMimeTypesByMimeTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesByMimeTypeUsageTypeQueries,
                mimeTypeUsageType);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageTypeForUpdate(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_WRITE);
    }

    public MimeTypeTransfer getMimeTypeTransfer(UserVisit userVisit, MimeType mimeType) {
        return coreTransferCaches.getMimeTypeTransferCache().getMimeTypeTransfer(userVisit, mimeType);
    }

    public List<MimeTypeTransfer> getMimeTypeTransfers(UserVisit userVisit, Collection<MimeType> mimeTypes) {
        List<MimeTypeTransfer> mimeTypeTransfers = new ArrayList<>(mimeTypes.size());
        var mimeTypeTransferCache = coreTransferCaches.getMimeTypeTransferCache();

        mimeTypes.forEach((mimeType) ->
                mimeTypeTransfers.add(mimeTypeTransferCache.getMimeTypeTransfer(userVisit, mimeType))
        );

        return mimeTypeTransfers;
    }

    public List<MimeTypeTransfer> getMimeTypeTransfers(UserVisit userVisit) {
        return getMimeTypeTransfers(userVisit, getMimeTypes());
    }

    public List<MimeTypeTransfer> getMimeTypeTransfersByEntityAttributeType(UserVisit userVisit,
            EntityAttributeType entityAttributeType) {
        return getMimeTypeTransfers(userVisit, getMimeTypesByEntityAttributeType(entityAttributeType));
    }

    public List<MimeTypeTransfer> getMimeTypeTransfersByMimeTypeUsageType(UserVisit userVisit,
            MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypeTransfers(userVisit, getMimeTypesByMimeTypeUsageType(mimeTypeUsageType));
    }

    public MimeTypeChoicesBean getMimeTypeChoices(String defaultMimeTypeChoice, Language language, boolean allowNullChoice) {
        var mimeTypes = getMimeTypes();
        var size = mimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var mimeType : mimeTypes) {
            var mimeTypeDetail = mimeType.getLastDetail();

            var label = getBestMimeTypeDescription(mimeType, language);
            var value = mimeTypeDetail.getMimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultMimeTypeChoice != null && defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }

    public MimeTypeChoicesBean getMimeTypeChoices(MimeTypeUsageType mimeTypeUsageType, String defaultMimeTypeChoice, Language language,
            boolean allowNullChoice) {
        var mimeTypes = getMimeTypesByMimeTypeUsageType(mimeTypeUsageType);
        var size = mimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var mimeType : mimeTypes) {
            var mimeTypeDetail = mimeType.getLastDetail();

            var label = getBestMimeTypeDescription(mimeType, language);
            var value = mimeTypeDetail.getMimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultMimeTypeChoice != null && defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateMimeTypeFromValue(MimeTypeDetailValue mimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(mimeTypeDetailValue.hasBeenModified()) {
            var mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    mimeTypeDetailValue.getMimeTypePK());
            var mimeTypeDetail = mimeType.getActiveDetailForUpdate();

            mimeTypeDetail.setThruTime(session.START_TIME_LONG);
            mimeTypeDetail.store();

            var mimeTypePK = mimeTypeDetail.getMimeTypePK(); // Not updated
            var mimeTypeName = mimeTypeDetailValue.getMimeTypeName();
            var entityAttributeTypePK = mimeTypeDetail.getEntityAttributeTypePK(); // Not updated
            var isDefault = mimeTypeDetailValue.getIsDefault();
            var sortOrder = mimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultMimeType = getDefaultMimeType();
                var defaultFound = defaultMimeType != null && !defaultMimeType.equals(mimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultMimeTypeDetailValue = getDefaultMimeTypeDetailValueForUpdate();

                    defaultMimeTypeDetailValue.setIsDefault(false);
                    updateMimeTypeFromValue(defaultMimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            mimeTypeDetail = MimeTypeDetailFactory.getInstance().create(mimeTypePK, mimeTypeName, entityAttributeTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            mimeType.setActiveDetail(mimeTypeDetail);
            mimeType.setLastDetail(mimeTypeDetail);

            sendEvent(mimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateMimeTypeFromValue(MimeTypeDetailValue mimeTypeDetailValue, BasePK updatedBy) {
        updateMimeTypeFromValue(mimeTypeDetailValue, true, updatedBy);
    }

    public void deleteMimeType(MimeType mimeType, BasePK deletedBy) {
        deleteMimeTypeDescriptionsByMimeType(mimeType, deletedBy);

        var mimeTypeDetail = mimeType.getLastDetailForUpdate();
        mimeTypeDetail.setThruTime(session.START_TIME_LONG);
        mimeType.setActiveDetail(null);
        mimeType.store();

        // Check for default, and pick one if necessary
        var defaultMimeType = getDefaultMimeType();
        if(defaultMimeType == null) {
            var mimeTypes = getMimeTypesForUpdate();

            if(!mimeTypes.isEmpty()) {
                var iter = mimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultMimeType = iter.next();
                }
                var mimeTypeDetailValue = Objects.requireNonNull(defaultMimeType).getLastDetailForUpdate().getMimeTypeDetailValue().clone();

                mimeTypeDetailValue.setIsDefault(true);
                updateMimeTypeFromValue(mimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(mimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Descriptions
    // --------------------------------------------------------------------------------

    public MimeTypeDescription createMimeTypeDescription(MimeType mimeType,
            Language language, String description, BasePK createdBy) {
        var mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().create(mimeType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(mimeType.getPrimaryKey(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return mimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getMimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM mimetypedescriptions "
                        + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_lang_languageid = ? AND mtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM mimetypedescriptions "
                        + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_lang_languageid = ? AND mtypd_thrutime = ? "
                        + "FOR UPDATE");
        getMimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeTypeDescription getMimeTypeDescription(MimeType mimeType,
            Language language, EntityPermission entityPermission) {
        return MimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getMimeTypeDescriptionQueries,
                mimeType, language, Session.MAX_TIME);
    }

    public MimeTypeDescription getMimeTypeDescription(MimeType mimeType, Language language) {
        return getMimeTypeDescription(mimeType, language, EntityPermission.READ_ONLY);
    }

    public MimeTypeDescription getMimeTypeDescriptionForUpdate(MimeType mimeType, Language language) {
        return getMimeTypeDescription(mimeType, language, EntityPermission.READ_WRITE);
    }

    public MimeTypeDescriptionValue getMimeTypeDescriptionValue(MimeTypeDescription mimeTypeDescription) {
        return mimeTypeDescription == null? null: mimeTypeDescription.getMimeTypeDescriptionValue().clone();
    }

    public MimeTypeDescriptionValue getMimeTypeDescriptionValueForUpdate(MimeType mimeType, Language language) {
        return getMimeTypeDescriptionValue(getMimeTypeDescriptionForUpdate(mimeType, language));
    }

    private static final Map<EntityPermission, String> getMimeTypeDescriptionsByMimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM mimetypedescriptions, languages "
                        + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_thrutime = ? AND mtypd_lang_languageid = lang_languageid "
                        + "ORDER BY lang_sortorder, lang_languageisoname "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM mimetypedescriptions "
                        + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_thrutime = ? "
                        + "FOR UPDATE");
        getMimeTypeDescriptionsByMimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeTypeDescription> getMimeTypeDescriptionsByMimeType(MimeType mimeType,
            EntityPermission entityPermission) {
        return MimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypeDescriptionsByMimeTypeQueries,
                mimeType, Session.MAX_TIME);
    }

    public List<MimeTypeDescription> getMimeTypeDescriptionsByMimeType(MimeType mimeType) {
        return getMimeTypeDescriptionsByMimeType(mimeType, EntityPermission.READ_ONLY);
    }

    public List<MimeTypeDescription> getMimeTypeDescriptionsByMimeTypeForUpdate(MimeType mimeType) {
        return getMimeTypeDescriptionsByMimeType(mimeType, EntityPermission.READ_WRITE);
    }

    public String getBestMimeTypeDescription(MimeType mimeType, Language language) {
        String description;
        var mimeTypeDescription = getMimeTypeDescription(mimeType, language);

        if(mimeTypeDescription == null && !language.getIsDefault()) {
            mimeTypeDescription = getMimeTypeDescription(mimeType, partyControl.getDefaultLanguage());
        }

        if(mimeTypeDescription == null) {
            description = mimeType.getLastDetail().getMimeTypeName();
        } else {
            description = mimeTypeDescription.getDescription();
        }

        return description;
    }

    public MimeTypeDescriptionTransfer getMimeTypeDescriptionTransfer(UserVisit userVisit, MimeTypeDescription mimeTypeDescription) {
        return coreTransferCaches.getMimeTypeDescriptionTransferCache().getMimeTypeDescriptionTransfer(userVisit, mimeTypeDescription);
    }

    public List<MimeTypeDescriptionTransfer> getMimeTypeDescriptionTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        var mimeTypeDescriptions = getMimeTypeDescriptionsByMimeType(mimeType);
        List<MimeTypeDescriptionTransfer> mimeTypeDescriptionTransfers = new ArrayList<>(mimeTypeDescriptions.size());
        var mimeTypeDescriptionTransferCache = coreTransferCaches.getMimeTypeDescriptionTransferCache();

        mimeTypeDescriptions.forEach((mimeTypeDescription) ->
                mimeTypeDescriptionTransfers.add(mimeTypeDescriptionTransferCache.getMimeTypeDescriptionTransfer(userVisit, mimeTypeDescription))
        );

        return mimeTypeDescriptionTransfers;
    }

    public void updateMimeTypeDescriptionFromValue(MimeTypeDescriptionValue mimeTypeDescriptionValue, BasePK updatedBy) {
        if(mimeTypeDescriptionValue.hasBeenModified()) {
            var mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    mimeTypeDescriptionValue.getPrimaryKey());

            mimeTypeDescription.setThruTime(session.START_TIME_LONG);
            mimeTypeDescription.store();

            var mimeType = mimeTypeDescription.getMimeType();
            var language = mimeTypeDescription.getLanguage();
            var description = mimeTypeDescriptionValue.getDescription();

            mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().create(mimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(mimeType.getPrimaryKey(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteMimeTypeDescription(MimeTypeDescription mimeTypeDescription, BasePK deletedBy) {
        mimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(mimeTypeDescription.getMimeTypePK(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteMimeTypeDescriptionsByMimeType(MimeType mimeType, BasePK deletedBy) {
        var mimeTypeDescriptions = getMimeTypeDescriptionsByMimeTypeForUpdate(mimeType);

        mimeTypeDescriptions.forEach((mimeTypeDescription) ->
                deleteMimeTypeDescription(mimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------

    public MimeTypeUsage createMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        return MimeTypeUsageFactory.getInstance().create(mimeType, mimeTypeUsageType);
    }

    public MimeTypeUsage getMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        MimeTypeUsage mimeTypeUsage;

        try {
            var ps = MimeTypeUsageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM mimetypeusages " +
                            "WHERE mtypu_mtyp_mimetypeid = ? AND mtypu_mtyput_mimetypeusagetypeid = ?");

            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            ps.setLong(2, mimeTypeUsageType.getPrimaryKey().getEntityId());

            mimeTypeUsage = MimeTypeUsageFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return mimeTypeUsage;
    }

    public List<MimeTypeUsage> getMimeTypeUsagesByMimeType(MimeType mimeType) {
        List<MimeTypeUsage> mimeTypeUsages;

        try {
            var ps = MimeTypeUsageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                            + "FROM mimetypeusages, mimetypes, mimetypedetails "
                            + "WHERE mtypu_mtyp_mimetypeid = ? "
                            + "AND mtypu_mtyp_mimetypeid = mtyp_mimetypeid AND mtyp_lastdetailid = mtypdt_mimetypedetailid "
                            + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename");

            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());

            mimeTypeUsages = MimeTypeUsageFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return mimeTypeUsages;
    }

    public MimeTypeUsageTransfer getMimeTypeUsageTransfer(UserVisit userVisit, MimeTypeUsage mimeTypeUsage) {
        return coreTransferCaches.getMimeTypeUsageTransferCache().getMimeTypeUsageTransfer(userVisit, mimeTypeUsage);
    }

    public List<MimeTypeUsageTransfer> getMimeTypeUsageTransfersByMimeType(UserVisit userVisit, Collection<MimeTypeUsage> mimeTypeUsages) {
        List<MimeTypeUsageTransfer> mimeTypeUsageTransfers = new ArrayList<>(mimeTypeUsages.size());
        var mimeTypeUsageTransferCache = coreTransferCaches.getMimeTypeUsageTransferCache();

        mimeTypeUsages.forEach((mimeTypeUsage) ->
                mimeTypeUsageTransfers.add(mimeTypeUsageTransferCache.getMimeTypeUsageTransfer(userVisit, mimeTypeUsage))
        );

        return mimeTypeUsageTransfers;
    }

    public List<MimeTypeUsageTransfer> getMimeTypeUsageTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getMimeTypeUsageTransfersByMimeType(userVisit, getMimeTypeUsagesByMimeType(mimeType));
    }

    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------

    public MimeTypeFileExtension createMimeTypeFileExtension(MimeType mimeType, String fileExtension, Boolean isDefault) {
        return MimeTypeFileExtensionFactory.getInstance().create(mimeType, fileExtension, isDefault);
    }

    public MimeTypeFileExtension getDefaultMimeTypeFileExtension(MimeType mimeType) {
        MimeTypeFileExtension mimeTypeFileExtension;

        try {
            var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM mimetypefileextensions " +
                            "WHERE mtypfe_mtyp_mimetypeid = ? AND mtypfe_isdefault = 1");

            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());

            mimeTypeFileExtension = MimeTypeFileExtensionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return mimeTypeFileExtension;
    }

    public MimeTypeFileExtension getMimeTypeFileExtension(String fileExtension) {
        MimeTypeFileExtension mimeTypeFileExtension;

        try {
            var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM mimetypefileextensions " +
                            "WHERE mtypfe_fileextension = ?");

            ps.setString(1, fileExtension);

            mimeTypeFileExtension = MimeTypeFileExtensionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return mimeTypeFileExtension;
    }

    public List<MimeTypeFileExtension> getMimeTypeFileExtensions() {
        var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                        "FROM mimetypefileextensions " +
                        "ORDER BY mtypfe_fileextension");

        return MimeTypeFileExtensionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public List<MimeTypeFileExtension> getMimeTypeFileExtensionsByMimeType(MimeType mimeType) {
        var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                "SELECT _ALL_ "
                        + "FROM mimetypefileextensions "
                        + "WHERE mtypfe_mtyp_mimetypeid = ? "
                        + "ORDER BY mtypfe_fileextension");

        return MimeTypeFileExtensionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps,
                mimeType);
    }

    public MimeTypeFileExtensionTransfer getMimeTypeFileExtensionTransfer(UserVisit userVisit, MimeTypeFileExtension mimeTypeFileExtension) {
        return coreTransferCaches.getMimeTypeFileExtensionTransferCache().getMimeTypeFileExtensionTransfer(userVisit, mimeTypeFileExtension);
    }

    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfers(UserVisit userVisit, Collection<MimeTypeFileExtension> mimeTypeFileExtensions) {
        List<MimeTypeFileExtensionTransfer> mimeTypeFileExtensionTransfers = new ArrayList<>(mimeTypeFileExtensions.size());
        var mimeTypeFileExtensionTransferCache = coreTransferCaches.getMimeTypeFileExtensionTransferCache();

        mimeTypeFileExtensions.forEach((mimeTypeFileExtension) ->
                mimeTypeFileExtensionTransfers.add(mimeTypeFileExtensionTransferCache.getMimeTypeFileExtensionTransfer(userVisit, mimeTypeFileExtension))
        );

        return mimeTypeFileExtensionTransfers;
    }

    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfers(UserVisit userVisit) {
        return getMimeTypeFileExtensionTransfers(userVisit, getMimeTypeFileExtensions());
    }

    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getMimeTypeFileExtensionTransfers(userVisit, getMimeTypeFileExtensionsByMimeType(mimeType));
    }

}
