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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.MimeTypeUsageTypeChoicesBean;
import com.echothree.model.control.core.common.transfer.MimeTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeFileExtensionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.core.server.transfer.MimeTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeFileExtensionTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeUsageTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeUsageTypeTransferCache;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.common.pk.MimeTypeUsageTypePK;
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
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.MIME_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.MIME_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.MIME_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.MIME_TYPE_USAGES_MIME_TYPE_FK;
import static com.echothree.model.jooq.server.tables.core.MimeTypeDescriptions.MimeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.core.MimeTypeDetails.MimeTypeDetails;
import static com.echothree.model.jooq.server.tables.core.MimeTypeFileExtensions.MimeTypeFileExtensions;
import static com.echothree.model.jooq.server.tables.core.MimeTypeUsageTypeDescriptions.MimeTypeUsageTypeDescriptions;
import static com.echothree.model.jooq.server.tables.core.MimeTypeUsageTypes.MimeTypeUsageTypes;
import static com.echothree.model.jooq.server.tables.core.MimeTypeUsages.MimeTypeUsages;
import static com.echothree.model.jooq.server.tables.core.MimeTypes.MimeTypes;
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
public class MimeTypeControl
        extends BaseModelControl {

    /** Creates a new instance of MimeTypeControl */
    protected MimeTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------

    @Inject
    protected MimeTypeUsageTypeFactory mimeTypeUsageTypeFactory;

    @Inject
    MimeTypeUsageTypeTransferCache mimeTypeUsageTypeTransferCache;

    public MimeTypeUsageType createMimeTypeUsageType(String mimeTypeUsageTypeName, Boolean isDefault, Integer sortOrder) {
        return mimeTypeUsageTypeFactory.create(mimeTypeUsageTypeName, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.MimeTypeUsageType */
    public MimeTypeUsageType getMimeTypeUsageTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new MimeTypeUsageTypePK(entityInstance.getEntityUniqueId());

        return mimeTypeUsageTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public MimeTypeUsageType getMimeTypeUsageTypeByEntityInstance(EntityInstance entityInstance) {
        return getMimeTypeUsageTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public MimeTypeUsageType getMimeTypeUsageTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getMimeTypeUsageTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countMimeTypeUsageTypes() {
        return session.getDslContext()
                .selectCount()
                .from(MimeTypeUsageTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<MimeTypeUsageType> getMimeTypeUsageTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(MimeTypeUsageTypes.fields())
                .from(MimeTypeUsageTypes)
                .orderBy(MimeTypeUsageTypes.SORT_ORDER, MimeTypeUsageTypes.MIME_TYPE_USAGE_TYPE_NAME),
                MimeTypeUsageTypeFactory.class);

        return mimeTypeUsageTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public MimeTypeUsageType getMimeTypeUsageTypeByName(String mimeTypeUsageTypeName) {
        var query = session.getDslContext()
                .select(MimeTypeUsageTypes.fields())
                .from(MimeTypeUsageTypes)
                .where(MimeTypeUsageTypes.MIME_TYPE_USAGE_TYPE_NAME.eq(mimeTypeUsageTypeName));

        return mimeTypeUsageTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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
        return mimeTypeUsageTypeTransferCache.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
    }

    public List<MimeTypeUsageTypeTransfer> getMimeTypeUsageTypeTransfers(UserVisit userVisit, Collection<MimeTypeUsageType> mimeTypeUsageTypes) {
        List<MimeTypeUsageTypeTransfer> mimeTypeUsageTypeTransfers = new ArrayList<>(mimeTypeUsageTypes.size());

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

    @Inject
    protected MimeTypeUsageTypeDescriptionFactory mimeTypeUsageTypeDescriptionFactory;

    public MimeTypeUsageTypeDescription createMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language, String description) {
        return mimeTypeUsageTypeDescriptionFactory.create(mimeTypeUsageType, language, description);
    }

    public MimeTypeUsageTypeDescription getMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language) {
        var query = session.getDslContext()
                .select(MimeTypeUsageTypeDescriptions.fields())
                .from(MimeTypeUsageTypeDescriptions)
                .where(MimeTypeUsageTypeDescriptions.MIME_TYPE_USAGE_TYPE.eq(mimeTypeUsageType.getPrimaryKey()),
                        MimeTypeUsageTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return mimeTypeUsageTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected MimeTypeDetailFactory mimeTypeDetailFactory;

    @Inject
    protected MimeTypeFactory mimeTypeFactory;

    @Inject
    MimeTypeTransferCache mimeTypeTransferCache;

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

        var mimeType = mimeTypeFactory.create();
        var mimeTypeDetail = mimeTypeDetailFactory.create(mimeType, mimeTypeName, entityAttributeType, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        mimeType = mimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

        return mimeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public MimeType getMimeTypeByEntityInstance(EntityInstance entityInstance) {
        return getMimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public MimeType getMimeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getMimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countMimeTypes() {
        return session.getDslContext()
                .selectCount()
                .from(MimeTypes)
                .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType) {
        return session.getDslContext()
                .selectCount()
                .from(MimeTypes)
                .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                .join(MimeTypeUsages).onKey(MIME_TYPE_USAGES_MIME_TYPE_FK)
                .where(MimeTypeUsages.MIME_TYPE_USAGE_TYPE.eq(mimeTypeUsageType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private MimeType getMimeTypeByName(String mimeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(MimeTypes.fields())
                .from(MimeTypes)
                .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                .where(MimeTypeDetails.MIME_TYPE_NAME.eq(mimeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return mimeTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private MimeType getDefaultMimeType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(MimeTypes.fields())
                .from(MimeTypes)
                .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                .where(MimeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return mimeTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<MimeType> getMimeTypes(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(MimeTypes.fields())
                    .from(MimeTypes)
                    .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                    .orderBy(MimeTypeDetails.SORT_ORDER, MimeTypeDetails.MIME_TYPE_NAME),
                    MimeTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(MimeTypes.fields())
                    .from(MimeTypes)
                    .where(MimeTypes.ACTIVE_DETAIL.isNotNull())
                    .forUpdate();
        };

        return mimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<MimeType> getMimeTypes() {
        return getMimeTypes(EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesForUpdate() {
        return getMimeTypes(EntityPermission.READ_WRITE);
    }

    private List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(MimeTypes.fields())
                    .from(MimeTypes)
                    .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                    .where(MimeTypeDetails.ENTITY_ATTRIBUTE_TYPE.eq(entityAttributeType.getPrimaryKey()))
                    .orderBy(MimeTypeDetails.SORT_ORDER, MimeTypeDetails.MIME_TYPE_NAME),
                    MimeTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(MimeTypes.fields())
                    .from(MimeTypes)
                    .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                    .where(MimeTypeDetails.ENTITY_ATTRIBUTE_TYPE.eq(entityAttributeType.getPrimaryKey()))
                    .forUpdate();
        };

        return mimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByEntityAttributeTypeForUpdate(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_WRITE);
    }

    private List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(MimeTypes.fields())
                    .from(MimeTypes)
                    .join(MimeTypeDetails).onKey(MIME_TYPES_ACTIVE_DETAIL_FK)
                    .join(MimeTypeUsages).onKey(MIME_TYPE_USAGES_MIME_TYPE_FK)
                    .where(MimeTypeUsages.MIME_TYPE_USAGE_TYPE.eq(mimeTypeUsageType.getPrimaryKey()))
                    .orderBy(MimeTypeDetails.SORT_ORDER, MimeTypeDetails.MIME_TYPE_NAME),
                    MimeTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(MimeTypes.fields())
                    .from(MimeTypes)
                    .join(MimeTypeUsages).onKey(MIME_TYPE_USAGES_MIME_TYPE_FK)
                    .where(MimeTypes.ACTIVE_DETAIL.isNotNull(),
                            MimeTypeUsages.MIME_TYPE_USAGE_TYPE.eq(mimeTypeUsageType.getPrimaryKey()))
                    .forUpdate();
        };

        return mimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageTypeForUpdate(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_WRITE);
    }

    public MimeTypeTransfer getMimeTypeTransfer(UserVisit userVisit, MimeType mimeType) {
        return mimeTypeTransferCache.getMimeTypeTransfer(userVisit, mimeType);
    }

    public List<MimeTypeTransfer> getMimeTypeTransfers(UserVisit userVisit, Collection<MimeType> mimeTypes) {
        List<MimeTypeTransfer> mimeTypeTransfers = new ArrayList<>(mimeTypes.size());

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
            var mimeType = mimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    mimeTypeDetailValue.getMimeTypePK());
            var mimeTypeDetail = mimeType.getActiveDetailForUpdate();

            mimeTypeDetail.setThruTime(session.getStartTime());
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

            mimeTypeDetail = mimeTypeDetailFactory.create(mimeTypePK, mimeTypeName, entityAttributeTypePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

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
        mimeTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected MimeTypeDescriptionFactory mimeTypeDescriptionFactory;

    @Inject
    MimeTypeDescriptionTransferCache mimeTypeDescriptionTransferCache;

    public MimeTypeDescription createMimeTypeDescription(MimeType mimeType,
            Language language, String description, BasePK createdBy) {
        var mimeTypeDescription = mimeTypeDescriptionFactory.create(mimeType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(mimeType.getPrimaryKey(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return mimeTypeDescription;
    }

    private MimeTypeDescription getMimeTypeDescription(MimeType mimeType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(MimeTypeDescriptions.fields())
                .from(MimeTypeDescriptions)
                .where(MimeTypeDescriptions.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                        MimeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        MimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return mimeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<MimeTypeDescription> getMimeTypeDescriptionsByMimeType(MimeType mimeType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(MimeTypeDescriptions.fields())
                    .from(MimeTypeDescriptions)
                    .join(Languages).onKey(MIME_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(MimeTypeDescriptions.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                            MimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    MimeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(MimeTypeDescriptions.fields())
                    .from(MimeTypeDescriptions)
                    .where(MimeTypeDescriptions.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                            MimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return mimeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return mimeTypeDescriptionTransferCache.getMimeTypeDescriptionTransfer(userVisit, mimeTypeDescription);
    }

    public List<MimeTypeDescriptionTransfer> getMimeTypeDescriptionTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        var mimeTypeDescriptions = getMimeTypeDescriptionsByMimeType(mimeType);
        List<MimeTypeDescriptionTransfer> mimeTypeDescriptionTransfers = new ArrayList<>(mimeTypeDescriptions.size());

        mimeTypeDescriptions.forEach((mimeTypeDescription) ->
                mimeTypeDescriptionTransfers.add(mimeTypeDescriptionTransferCache.getMimeTypeDescriptionTransfer(userVisit, mimeTypeDescription))
        );

        return mimeTypeDescriptionTransfers;
    }

    public void updateMimeTypeDescriptionFromValue(MimeTypeDescriptionValue mimeTypeDescriptionValue, BasePK updatedBy) {
        if(mimeTypeDescriptionValue.hasBeenModified()) {
            var mimeTypeDescription = mimeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    mimeTypeDescriptionValue.getPrimaryKey());

            mimeTypeDescription.setThruTime(session.getStartTime());
            mimeTypeDescription.store();

            var mimeType = mimeTypeDescription.getMimeType();
            var language = mimeTypeDescription.getLanguage();
            var description = mimeTypeDescriptionValue.getDescription();

            mimeTypeDescription = mimeTypeDescriptionFactory.create(mimeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(mimeType.getPrimaryKey(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteMimeTypeDescription(MimeTypeDescription mimeTypeDescription, BasePK deletedBy) {
        mimeTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected MimeTypeUsageFactory mimeTypeUsageFactory;

    @Inject
    MimeTypeUsageTransferCache mimeTypeUsageTransferCache;

    public MimeTypeUsage createMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        return mimeTypeUsageFactory.create(mimeType, mimeTypeUsageType);
    }

    public long countMimeTypeUsagesByMimeType(MimeType mimeType) {
        return session.getDslContext()
                .selectCount()
                .from(MimeTypeUsages)
                .where(MimeTypeUsages.MIME_TYPE.eq(mimeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public MimeTypeUsage getMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        var query = session.getDslContext()
                .select(MimeTypeUsages.fields())
                .from(MimeTypeUsages)
                .where(MimeTypeUsages.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                        MimeTypeUsages.MIME_TYPE_USAGE_TYPE.eq(mimeTypeUsageType.getPrimaryKey()));

        return mimeTypeUsageFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<MimeTypeUsage> getMimeTypeUsagesByMimeType(MimeType mimeType) {
        var query = session.applyLimit(session.getDslContext()
                .select(MimeTypeUsages.fields())
                .from(MimeTypeUsages)
                .join(MimeTypes).onKey(MIME_TYPE_USAGES_MIME_TYPE_FK)
                .join(MimeTypeDetails).onKey(MIME_TYPES_LAST_DETAIL_FK)
                .where(MimeTypeUsages.MIME_TYPE.eq(mimeType.getPrimaryKey()))
                .orderBy(MimeTypeDetails.SORT_ORDER, MimeTypeDetails.MIME_TYPE_NAME),
                MimeTypeUsageFactory.class);

        return mimeTypeUsageFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public MimeTypeUsageTransfer getMimeTypeUsageTransfer(UserVisit userVisit, MimeTypeUsage mimeTypeUsage) {
        return mimeTypeUsageTransferCache.getMimeTypeUsageTransfer(userVisit, mimeTypeUsage);
    }

    public List<MimeTypeUsageTransfer> getMimeTypeUsageTransfersByMimeType(UserVisit userVisit, Collection<MimeTypeUsage> mimeTypeUsages) {
        List<MimeTypeUsageTransfer> mimeTypeUsageTransfers = new ArrayList<>(mimeTypeUsages.size());

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

    @Inject
    protected MimeTypeFileExtensionFactory mimeTypeFileExtensionFactory;

    @Inject
    MimeTypeFileExtensionTransferCache mimeTypeFileExtensionTransferCache;

    public MimeTypeFileExtension createMimeTypeFileExtension(MimeType mimeType, String fileExtension, Boolean isDefault) {
        return mimeTypeFileExtensionFactory.create(mimeType, fileExtension, isDefault);
    }

    public long countMimeTypeFileExtensions() {
        return session.getDslContext()
                .selectCount()
                .from(MimeTypeFileExtensions)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countMimeTypeFileExtensionsByMimeType(MimeType mimeType) {
        return session.getDslContext()
                .selectCount()
                .from(MimeTypeFileExtensions)
                .where(MimeTypeFileExtensions.MIME_TYPE.eq(mimeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public MimeTypeFileExtension getDefaultMimeTypeFileExtension(MimeType mimeType) {
        var query = session.getDslContext()
                .select(MimeTypeFileExtensions.fields())
                .from(MimeTypeFileExtensions)
                .where(MimeTypeFileExtensions.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                        MimeTypeFileExtensions.IS_DEFAULT.eq(true));

        return mimeTypeFileExtensionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public MimeTypeFileExtension getMimeTypeFileExtension(String fileExtension) {
        var query = session.getDslContext()
                .select(MimeTypeFileExtensions.fields())
                .from(MimeTypeFileExtensions)
                .where(MimeTypeFileExtensions.FILE_EXTENSION.eq(fileExtension));

        return mimeTypeFileExtensionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<MimeTypeFileExtension> getMimeTypeFileExtensions() {
        var query = session.applyLimit(session.getDslContext()
                .select(MimeTypeFileExtensions.fields())
                .from(MimeTypeFileExtensions)
                .orderBy(MimeTypeFileExtensions.FILE_EXTENSION),
                MimeTypeFileExtensionFactory.class);

        return mimeTypeFileExtensionFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<MimeTypeFileExtension> getMimeTypeFileExtensionsByMimeType(MimeType mimeType) {
        var query = session.applyLimit(session.getDslContext()
                .select(MimeTypeFileExtensions.fields())
                .from(MimeTypeFileExtensions)
                .where(MimeTypeFileExtensions.MIME_TYPE.eq(mimeType.getPrimaryKey()))
                .orderBy(MimeTypeFileExtensions.FILE_EXTENSION),
                MimeTypeFileExtensionFactory.class);

        return mimeTypeFileExtensionFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public MimeTypeFileExtensionTransfer getMimeTypeFileExtensionTransfer(UserVisit userVisit, MimeTypeFileExtension mimeTypeFileExtension) {
        return mimeTypeFileExtensionTransferCache.getMimeTypeFileExtensionTransfer(userVisit, mimeTypeFileExtension);
    }

    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfers(UserVisit userVisit, Collection<MimeTypeFileExtension> mimeTypeFileExtensions) {
        List<MimeTypeFileExtensionTransfer> mimeTypeFileExtensionTransfers = new ArrayList<>(mimeTypeFileExtensions.size());

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
