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

package com.echothree.model.control.index.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.index.common.choice.IndexChoicesBean;
import com.echothree.model.control.index.common.choice.IndexFieldChoicesBean;
import com.echothree.model.control.index.common.choice.IndexTypeChoicesBean;
import com.echothree.model.control.index.common.transfer.IndexDescriptionTransfer;
import com.echothree.model.control.index.common.transfer.IndexFieldDescriptionTransfer;
import com.echothree.model.control.index.common.transfer.IndexFieldTransfer;
import com.echothree.model.control.index.common.transfer.IndexTransfer;
import com.echothree.model.control.index.common.transfer.IndexTypeDescriptionTransfer;
import com.echothree.model.control.index.common.transfer.IndexTypeTransfer;
import com.echothree.model.control.index.server.transfer.IndexDescriptionTransferCache;
import com.echothree.model.control.index.server.transfer.IndexFieldDescriptionTransferCache;
import com.echothree.model.control.index.server.transfer.IndexFieldTransferCache;
import com.echothree.model.control.index.server.transfer.IndexTransferCache;
import com.echothree.model.control.index.server.transfer.IndexTypeDescriptionTransferCache;
import com.echothree.model.control.index.server.transfer.IndexTypeTransferCache;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.common.pk.IndexFieldPK;
import com.echothree.model.data.index.common.pk.IndexPK;
import com.echothree.model.data.index.common.pk.IndexTypePK;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexDescription;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexFieldDescription;
import com.echothree.model.data.index.server.entity.IndexStatus;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.index.server.entity.IndexTypeDescription;
import com.echothree.model.data.index.server.factory.IndexDescriptionFactory;
import com.echothree.model.data.index.server.factory.IndexDetailFactory;
import com.echothree.model.data.index.server.factory.IndexFactory;
import com.echothree.model.data.index.server.factory.IndexFieldDescriptionFactory;
import com.echothree.model.data.index.server.factory.IndexFieldDetailFactory;
import com.echothree.model.data.index.server.factory.IndexFieldFactory;
import com.echothree.model.data.index.server.factory.IndexStatusFactory;
import com.echothree.model.data.index.server.factory.IndexTypeDescriptionFactory;
import com.echothree.model.data.index.server.factory.IndexTypeDetailFactory;
import com.echothree.model.data.index.server.factory.IndexTypeFactory;
import com.echothree.model.data.index.server.value.IndexDescriptionValue;
import com.echothree.model.data.index.server.value.IndexDetailValue;
import com.echothree.model.data.index.server.value.IndexFieldDescriptionValue;
import com.echothree.model.data.index.server.value.IndexFieldDetailValue;
import com.echothree.model.data.index.server.value.IndexTypeDescriptionValue;
import com.echothree.model.data.index.server.value.IndexTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.index.IndexForeignKeys.INDEXES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.index.IndexForeignKeys.INDEX_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.index.IndexForeignKeys.INDEX_FIELDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.index.IndexForeignKeys.INDEX_FIELD_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.index.IndexForeignKeys.INDEX_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.index.IndexForeignKeys.INDEX_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.index.IndexDescriptions.IndexDescriptions;
import static com.echothree.model.jooq.server.tables.index.IndexDetails.IndexDetails;
import static com.echothree.model.jooq.server.tables.index.IndexFieldDescriptions.IndexFieldDescriptions;
import static com.echothree.model.jooq.server.tables.index.IndexFieldDetails.IndexFieldDetails;
import static com.echothree.model.jooq.server.tables.index.IndexFields.IndexFields;
import static com.echothree.model.jooq.server.tables.index.IndexStatuses.IndexStatuses;
import static com.echothree.model.jooq.server.tables.index.IndexTypeDescriptions.IndexTypeDescriptions;
import static com.echothree.model.jooq.server.tables.index.IndexTypeDetails.IndexTypeDetails;
import static com.echothree.model.jooq.server.tables.index.IndexTypes.IndexTypes;
import static com.echothree.model.jooq.server.tables.index.Indexes.Indexes;
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
public class IndexControl
        extends BaseModelControl {
    
    @Inject
    protected SearchControl searchControl;

    /** Creates a new instance of IndexControl */
    protected IndexControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Index Types
    // --------------------------------------------------------------------------------

    @Inject
    protected IndexTypeDetailFactory indexTypeDetailFactory;

    @Inject
    protected IndexTypeFactory indexTypeFactory;

    @Inject
    IndexTypeTransferCache indexTypeTransferCache;

    public IndexType createIndexType(String indexTypeName, EntityType entityType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultIndexType = getDefaultIndexType();
        var defaultFound = defaultIndexType != null;

        if(defaultFound && isDefault) {
            var defaultIndexTypeDetailValue = getDefaultIndexTypeDetailValueForUpdate();

            defaultIndexTypeDetailValue.setIsDefault(false);
            updateIndexTypeFromValue(defaultIndexTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var indexType = indexTypeFactory.create();
        var indexTypeDetail = indexTypeDetailFactory.create(indexType, indexTypeName, entityType, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        indexType = indexTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, indexType.getPrimaryKey());
        indexType.setActiveDetail(indexTypeDetail);
        indexType.setLastDetail(indexTypeDetail);
        indexType.store();

        sendEvent(indexType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return indexType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.IndexType */
    public IndexType getIndexTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new IndexTypePK(entityInstance.getEntityUniqueId());

        return indexTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public IndexType getIndexTypeByEntityInstance(EntityInstance entityInstance) {
        return getIndexTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public IndexType getIndexTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getIndexTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countIndexTypes() {
        return session.getDslContext()
                .selectCount()
                .from(IndexTypes)
                .join(IndexTypeDetails).onKey(INDEX_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countIndexTypesByEntityType(EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(IndexTypes)
                .join(IndexTypeDetails).onKey(INDEX_TYPES_ACTIVE_DETAIL_FK)
                .where(IndexTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean isEntityTypeUsedByIndexTypes(EntityType entityType) {
        return countIndexTypesByEntityType(entityType) != 0;
    }

    private IndexType getIndexTypeByName(String indexTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexTypes.fields())
                .from(IndexTypes)
                .join(IndexTypeDetails).onKey(INDEX_TYPES_ACTIVE_DETAIL_FK)
                .where(IndexTypeDetails.INDEX_TYPE_NAME.eq(indexTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexType getIndexTypeByName(String indexTypeName) {
        return getIndexTypeByName(indexTypeName, EntityPermission.READ_ONLY);
    }

    public IndexType getIndexTypeByNameForUpdate(String indexTypeName) {
        return getIndexTypeByName(indexTypeName, EntityPermission.READ_WRITE);
    }

    public IndexTypeDetailValue getIndexTypeDetailValueForUpdate(IndexType indexType) {
        return indexType == null? null: indexType.getLastDetailForUpdate().getIndexTypeDetailValue().clone();
    }

    public IndexTypeDetailValue getIndexTypeDetailValueByNameForUpdate(String indexTypeName) {
        return getIndexTypeDetailValueForUpdate(getIndexTypeByNameForUpdate(indexTypeName));
    }

    private IndexType getDefaultIndexType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexTypes.fields())
                .from(IndexTypes)
                .join(IndexTypeDetails).onKey(INDEX_TYPES_ACTIVE_DETAIL_FK)
                .where(IndexTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexType getDefaultIndexType() {
        return getDefaultIndexType(EntityPermission.READ_ONLY);
    }

    public IndexType getDefaultIndexTypeForUpdate() {
        return getDefaultIndexType(EntityPermission.READ_WRITE);
    }

    public IndexTypeDetailValue getDefaultIndexTypeDetailValueForUpdate() {
        return getDefaultIndexTypeForUpdate().getLastDetailForUpdate().getIndexTypeDetailValue().clone();
    }

    private List<IndexType> getIndexTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexTypes.fields())
                .from(IndexTypes)
                .join(IndexTypeDetails).onKey(INDEX_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(IndexTypeDetails.SORT_ORDER, IndexTypeDetails.INDEX_TYPE_NAME),
                    IndexTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<IndexType> getIndexTypes() {
        return getIndexTypes(EntityPermission.READ_ONLY);
    }

    public List<IndexType> getIndexTypesForUpdate() {
        return getIndexTypes(EntityPermission.READ_WRITE);
    }

    private List<IndexType> getIndexTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexTypes.fields())
                .from(IndexTypes)
                .join(IndexTypeDetails).onKey(INDEX_TYPES_ACTIVE_DETAIL_FK)
                .where(IndexTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(IndexTypeDetails.SORT_ORDER, IndexTypeDetails.INDEX_TYPE_NAME),
                    IndexTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<IndexType> getIndexTypesByEntityType(EntityType entityType) {
        return getIndexTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<IndexType> getIndexTypesByEntityTypeForUpdate(EntityType entityType) {
        return getIndexTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

   public IndexTypeTransfer getIndexTypeTransfer(UserVisit userVisit, IndexType indexType) {
        return indexTypeTransferCache.getIndexTypeTransfer(userVisit, indexType);
    }

    public List<IndexTypeTransfer> getIndexTypeTransfers(UserVisit userVisit, Collection<IndexType> indexTypes) {
        List<IndexTypeTransfer> indexTypeTransfers = new ArrayList<>(indexTypes.size());

        indexTypes.forEach((indexType) ->
                indexTypeTransfers.add(indexTypeTransferCache.getIndexTypeTransfer(userVisit, indexType))
        );

        return indexTypeTransfers;
    }

    public List<IndexTypeTransfer> getIndexTypeTransfers(UserVisit userVisit) {
        return getIndexTypeTransfers(userVisit, getIndexTypes());
    }

    public List<IndexTypeTransfer> getIndexTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getIndexTypeTransfers(userVisit, getIndexTypesByEntityType(entityType));
    }

    public IndexTypeChoicesBean getIndexTypeChoices(String defaultIndexTypeChoice, Language language, boolean allowNullChoice) {
        var indexTypes = getIndexTypes();
        var size = indexTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultIndexTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var indexType : indexTypes) {
            var indexTypeDetail = indexType.getLastDetail();

            var label = getBestIndexTypeDescription(indexType, language);
            var value = indexTypeDetail.getIndexTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultIndexTypeChoice != null && defaultIndexTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && indexTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new IndexTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateIndexTypeFromValue(IndexTypeDetailValue indexTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(indexTypeDetailValue.hasBeenModified()) {
            var indexType = indexTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     indexTypeDetailValue.getIndexTypePK());
            var indexTypeDetail = indexType.getActiveDetailForUpdate();

            indexTypeDetail.setThruTime(session.getStartTime());
            indexTypeDetail.store();

            var indexTypePK = indexTypeDetail.getIndexTypePK(); // Not updated
            var indexTypeName = indexTypeDetailValue.getIndexTypeName();
            var entityTypePK = indexTypeDetailValue.getEntityTypePK();
            var isDefault = indexTypeDetailValue.getIsDefault();
            var sortOrder = indexTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultIndexType = getDefaultIndexType();
                var defaultFound = defaultIndexType != null && !defaultIndexType.equals(indexType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultIndexTypeDetailValue = getDefaultIndexTypeDetailValueForUpdate();

                    defaultIndexTypeDetailValue.setIsDefault(false);
                    updateIndexTypeFromValue(defaultIndexTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            indexTypeDetail = indexTypeDetailFactory.create(indexTypePK, indexTypeName, entityTypePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            indexType.setActiveDetail(indexTypeDetail);
            indexType.setLastDetail(indexTypeDetail);

            sendEvent(indexTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateIndexTypeFromValue(IndexTypeDetailValue indexTypeDetailValue, BasePK updatedBy) {
        updateIndexTypeFromValue(indexTypeDetailValue, true, updatedBy);
    }

    private void deleteIndexType(IndexType indexType, boolean checkDefault, BasePK deletedBy) {
        var indexTypeDetail = indexType.getLastDetailForUpdate();

        deleteIndexFieldsByIndexType(indexType, deletedBy);
        deleteIndexesByIndexType(indexType, deletedBy);
        deleteIndexTypeDescriptionsByIndexType(indexType, deletedBy);

        indexTypeDetail.setThruTime(session.getStartTime());
        indexType.setActiveDetail(null);
        indexType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultIndexType = getDefaultIndexType();

            if(defaultIndexType == null) {
                var indexTypes = getIndexTypesForUpdate();

                if(!indexTypes.isEmpty()) {
                    var iter = indexTypes.iterator();
                    if(iter.hasNext()) {
                        defaultIndexType = iter.next();
                    }
                    var indexTypeDetailValue = Objects.requireNonNull(defaultIndexType).getLastDetailForUpdate().getIndexTypeDetailValue().clone();

                    indexTypeDetailValue.setIsDefault(true);
                    updateIndexTypeFromValue(indexTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(indexType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteIndexType(IndexType indexType, BasePK deletedBy) {
        deleteIndexType(indexType, true, deletedBy);
    }

    private void deleteIndexTypes(List<IndexType> indexTypes, boolean checkDefault, BasePK deletedBy) {
        indexTypes.forEach((indexType) -> deleteIndexType(indexType, checkDefault, deletedBy));
    }

    public void deleteIndexTypes(List<IndexType> indexTypes, BasePK deletedBy) {
        deleteIndexTypes(indexTypes, true, deletedBy);
    }

    public void deleteIndexTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteIndexTypes(getIndexTypesByEntityTypeForUpdate(entityType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Index Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected IndexTypeDescriptionFactory indexTypeDescriptionFactory;

    @Inject
    IndexTypeDescriptionTransferCache indexTypeDescriptionTransferCache;

    public IndexTypeDescription createIndexTypeDescription(IndexType indexType, Language language, String description, BasePK createdBy) {
        var indexTypeDescription = indexTypeDescriptionFactory.create(indexType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(indexType.getPrimaryKey(), EventTypes.MODIFY, indexTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return indexTypeDescription;
    }

    private IndexTypeDescription getIndexTypeDescription(IndexType indexType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexTypeDescriptions.fields())
                .from(IndexTypeDescriptions)
                .where(IndexTypeDescriptions.INDEX_TYPE.eq(indexType.getPrimaryKey()),
                        IndexTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        IndexTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexTypeDescription getIndexTypeDescription(IndexType indexType, Language language) {
        return getIndexTypeDescription(indexType, language, EntityPermission.READ_ONLY);
    }

    public IndexTypeDescription getIndexTypeDescriptionForUpdate(IndexType indexType, Language language) {
        return getIndexTypeDescription(indexType, language, EntityPermission.READ_WRITE);
    }

    public IndexTypeDescriptionValue getIndexTypeDescriptionValue(IndexTypeDescription indexTypeDescription) {
        return indexTypeDescription == null? null: indexTypeDescription.getIndexTypeDescriptionValue().clone();
    }

    public IndexTypeDescriptionValue getIndexTypeDescriptionValueForUpdate(IndexType indexType, Language language) {
        return getIndexTypeDescriptionValue(getIndexTypeDescriptionForUpdate(indexType, language));
    }

    private List<IndexTypeDescription> getIndexTypeDescriptionsByIndexType(IndexType indexType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(IndexTypeDescriptions.fields())
                    .from(IndexTypeDescriptions)
                    .join(Languages).onKey(INDEX_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(IndexTypeDescriptions.INDEX_TYPE.eq(indexType.getPrimaryKey()),
                            IndexTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    IndexTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(IndexTypeDescriptions.fields())
                    .from(IndexTypeDescriptions)
                    .where(IndexTypeDescriptions.INDEX_TYPE.eq(indexType.getPrimaryKey()),
                            IndexTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return indexTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<IndexTypeDescription> getIndexTypeDescriptionsByIndexType(IndexType indexType) {
        return getIndexTypeDescriptionsByIndexType(indexType, EntityPermission.READ_ONLY);
    }

    public List<IndexTypeDescription> getIndexTypeDescriptionsByIndexTypeForUpdate(IndexType indexType) {
        return getIndexTypeDescriptionsByIndexType(indexType, EntityPermission.READ_WRITE);
    }

    public String getBestIndexTypeDescription(IndexType indexType, Language language) {
        String description;
        var indexTypeDescription = getIndexTypeDescription(indexType, language);

        if(indexTypeDescription == null && !language.getIsDefault()) {
            indexTypeDescription = getIndexTypeDescription(indexType, partyControl.getDefaultLanguage());
        }

        if(indexTypeDescription == null) {
            description = indexType.getLastDetail().getIndexTypeName();
        } else {
            description = indexTypeDescription.getDescription();
        }

        return description;
    }

    public IndexTypeDescriptionTransfer getIndexTypeDescriptionTransfer(UserVisit userVisit, IndexTypeDescription indexTypeDescription) {
        return indexTypeDescriptionTransferCache.getIndexTypeDescriptionTransfer(userVisit, indexTypeDescription);
    }

    public List<IndexTypeDescriptionTransfer> getIndexTypeDescriptionTransfersByIndexType(UserVisit userVisit, IndexType indexType) {
        var indexTypeDescriptions = getIndexTypeDescriptionsByIndexType(indexType);
        List<IndexTypeDescriptionTransfer> indexTypeDescriptionTransfers = new ArrayList<>(indexTypeDescriptions.size());

        indexTypeDescriptions.forEach((indexTypeDescription) ->
                indexTypeDescriptionTransfers.add(indexTypeDescriptionTransferCache.getIndexTypeDescriptionTransfer(userVisit, indexTypeDescription))
        );

        return indexTypeDescriptionTransfers;
    }

    public void updateIndexTypeDescriptionFromValue(IndexTypeDescriptionValue indexTypeDescriptionValue, BasePK updatedBy) {
        if(indexTypeDescriptionValue.hasBeenModified()) {
            var indexTypeDescription = indexTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    indexTypeDescriptionValue.getPrimaryKey());

            indexTypeDescription.setThruTime(session.getStartTime());
            indexTypeDescription.store();

            var indexType = indexTypeDescription.getIndexType();
            var language = indexTypeDescription.getLanguage();
            var description = indexTypeDescriptionValue.getDescription();

            indexTypeDescription = indexTypeDescriptionFactory.create(indexType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(indexType.getPrimaryKey(), EventTypes.MODIFY, indexTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteIndexTypeDescription(IndexTypeDescription indexTypeDescription, BasePK deletedBy) {
        indexTypeDescription.setThruTime(session.getStartTime());

        sendEvent(indexTypeDescription.getIndexTypePK(), EventTypes.MODIFY, indexTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteIndexTypeDescriptionsByIndexType(IndexType indexType, BasePK deletedBy) {
        var indexTypeDescriptions = getIndexTypeDescriptionsByIndexTypeForUpdate(indexType);

        indexTypeDescriptions.forEach((indexTypeDescription) -> 
                deleteIndexTypeDescription(indexTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Index Fields
    // --------------------------------------------------------------------------------

    @Inject
    protected IndexFieldDetailFactory indexFieldDetailFactory;

    @Inject
    protected IndexFieldFactory indexFieldFactory;

    @Inject
    IndexFieldTransferCache indexFieldTransferCache;

    public IndexField createIndexField(IndexType indexType, String indexFieldName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultIndexField = getDefaultIndexField(indexType);
        var defaultFound = defaultIndexField != null;

        if(defaultFound && isDefault) {
            var defaultIndexFieldDetailValue = getDefaultIndexFieldDetailValueForUpdate(indexType);

            defaultIndexFieldDetailValue.setIsDefault(false);
            updateIndexFieldFromValue(defaultIndexFieldDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var indexField = indexFieldFactory.create();
        var indexFieldDetail = indexFieldDetailFactory.create( indexField, indexType, indexFieldName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        indexField = indexFieldFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                indexField.getPrimaryKey());
        indexField.setActiveDetail(indexFieldDetail);
        indexField.setLastDetail(indexFieldDetail);
        indexField.store();

        sendEvent(indexField.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return indexField;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.IndexField */
    public IndexField getIndexFieldByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new IndexFieldPK(entityInstance.getEntityUniqueId());

        return indexFieldFactory.getEntityFromPK(entityPermission, pk);
    }

    public IndexField getIndexFieldByEntityInstance(EntityInstance entityInstance) {
        return getIndexFieldByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public IndexField getIndexFieldByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getIndexFieldByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countIndexFieldsByIndexType(final IndexType indexType) {
        return session.getDslContext()
                .selectCount()
                .from(IndexFields)
                .join(IndexFieldDetails).onKey(INDEX_FIELDS_ACTIVE_DETAIL_FK)
                .where(IndexFieldDetails.INDEX_TYPE.eq(indexType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<IndexField> getIndexFields(IndexType indexType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexFields.fields())
                .from(IndexFields)
                .join(IndexFieldDetails).onKey(INDEX_FIELDS_ACTIVE_DETAIL_FK)
                .where(IndexFieldDetails.INDEX_TYPE.eq(indexType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(IndexFieldDetails.SORT_ORDER, IndexFieldDetails.INDEX_FIELD_NAME),
                    IndexFieldFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFieldFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<IndexField> getIndexFields(IndexType indexType) {
        return getIndexFields(indexType, EntityPermission.READ_ONLY);
    }

    public List<IndexField> getIndexFieldsForUpdate(IndexType indexType) {
        return getIndexFields(indexType, EntityPermission.READ_WRITE);
    }

    private IndexField getDefaultIndexField(IndexType indexType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexFields.fields())
                .from(IndexFields)
                .join(IndexFieldDetails).onKey(INDEX_FIELDS_ACTIVE_DETAIL_FK)
                .where(IndexFieldDetails.INDEX_TYPE.eq(indexType.getPrimaryKey()),
                        IndexFieldDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFieldFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexField getDefaultIndexField(IndexType indexType) {
        return getDefaultIndexField(indexType, EntityPermission.READ_ONLY);
    }

    public IndexField getDefaultIndexFieldForUpdate(IndexType indexType) {
        return getDefaultIndexField(indexType, EntityPermission.READ_WRITE);
    }

    public IndexFieldDetailValue getDefaultIndexFieldDetailValueForUpdate(IndexType indexType) {
        return getDefaultIndexFieldForUpdate(indexType).getLastDetailForUpdate().getIndexFieldDetailValue().clone();
    }

    private IndexField getIndexFieldByName(IndexType indexType, String indexFieldName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexFields.fields())
                .from(IndexFields)
                .join(IndexFieldDetails).onKey(INDEX_FIELDS_ACTIVE_DETAIL_FK)
                .where(IndexFieldDetails.INDEX_TYPE.eq(indexType.getPrimaryKey()),
                        IndexFieldDetails.INDEX_FIELD_NAME.eq(indexFieldName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFieldFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexField getIndexFieldByName(IndexType indexType, String indexFieldName) {
        return getIndexFieldByName(indexType, indexFieldName, EntityPermission.READ_ONLY);
    }

    public IndexField getIndexFieldByNameForUpdate(IndexType indexType, String indexFieldName) {
        return getIndexFieldByName(indexType, indexFieldName, EntityPermission.READ_WRITE);
    }

    public IndexFieldDetailValue getIndexFieldDetailValueForUpdate(IndexField indexField) {
        return indexField == null? null: indexField.getLastDetailForUpdate().getIndexFieldDetailValue().clone();
    }

    public IndexFieldDetailValue getIndexFieldDetailValueByNameForUpdate(IndexType indexType, String indexFieldName) {
        return getIndexFieldDetailValueForUpdate(getIndexFieldByNameForUpdate(indexType, indexFieldName));
    }

    public IndexFieldChoicesBean getIndexFieldChoices(String defaultIndexFieldChoice, Language language, boolean allowNullChoice, IndexType indexType) {
        var indexFields = getIndexFields(indexType);
        var size = indexFields.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultIndexFieldChoice == null) {
                defaultValue = "";
            }
        }

        for(var indexField : indexFields) {
            var indexFieldDetail = indexField.getLastDetail();
            var label = getBestIndexFieldDescription(indexField, language);
            var value = indexFieldDetail.getIndexFieldName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultIndexFieldChoice != null && defaultIndexFieldChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && indexFieldDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new IndexFieldChoicesBean(labels, values, defaultValue);
    }

    public IndexFieldTransfer getIndexFieldTransfer(UserVisit userVisit, IndexField indexField) {
        return indexFieldTransferCache.getIndexFieldTransfer(userVisit, indexField);
    }

    public List<IndexFieldTransfer> getIndexFieldTransfersByIndexType(UserVisit userVisit, IndexType indexType) {
        var indexFields = getIndexFields(indexType);
        List<IndexFieldTransfer> indexFieldTransfers = new ArrayList<>(indexFields.size());

        indexFields.forEach((indexField) ->
                indexFieldTransfers.add(indexFieldTransferCache.getIndexFieldTransfer(userVisit, indexField))
        );

        return indexFieldTransfers;
    }

    private void updateIndexFieldFromValue(IndexFieldDetailValue indexFieldDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(indexFieldDetailValue.hasBeenModified()) {
            var indexField = indexFieldFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     indexFieldDetailValue.getIndexFieldPK());
            var indexFieldDetail = indexField.getActiveDetailForUpdate();

            indexFieldDetail.setThruTime(session.getStartTime());
            indexFieldDetail.store();

            var indexFieldPK = indexFieldDetail.getIndexFieldPK();
            var indexType = indexFieldDetail.getIndexType();
            var indexTypePK = indexType.getPrimaryKey();
            var indexFieldName = indexFieldDetailValue.getIndexFieldName();
            var isDefault = indexFieldDetailValue.getIsDefault();
            var sortOrder = indexFieldDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultIndexField = getDefaultIndexField(indexType);
                var defaultFound = defaultIndexField != null && !defaultIndexField.equals(indexField);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultIndexFieldDetailValue = getDefaultIndexFieldDetailValueForUpdate(indexType);

                    defaultIndexFieldDetailValue.setIsDefault(false);
                    updateIndexFieldFromValue(defaultIndexFieldDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            indexFieldDetail = indexFieldDetailFactory.create(indexFieldPK, indexTypePK, indexFieldName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            indexField.setActiveDetail(indexFieldDetail);
            indexField.setLastDetail(indexFieldDetail);

            sendEvent(indexFieldPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateIndexFieldFromValue(IndexFieldDetailValue indexFieldDetailValue, BasePK updatedBy) {
        updateIndexFieldFromValue(indexFieldDetailValue, true, updatedBy);
    }

    public void deleteIndexField(IndexField indexField, BasePK deletedBy) {
        
        deleteIndexFieldDescriptionsByIndexField(indexField, deletedBy);
        searchControl.deleteCachedSearchIndexFieldsByIndexField(indexField, deletedBy);

        var indexFieldDetail = indexField.getLastDetailForUpdate();
        indexFieldDetail.setThruTime(session.getStartTime());
        indexField.setActiveDetail(null);
        indexField.store();

        // Check for default, and pick one if necessary
        var indexType = indexFieldDetail.getIndexType();
        var defaultIndexField = getDefaultIndexField(indexType);
        if(defaultIndexField == null) {
            var indexFields = getIndexFieldsForUpdate(indexType);

            if(!indexFields.isEmpty()) {
                var iter = indexFields.iterator();
                if(iter.hasNext()) {
                    defaultIndexField = iter.next();
                }
                var indexFieldDetailValue = Objects.requireNonNull(defaultIndexField).getLastDetailForUpdate().getIndexFieldDetailValue().clone();

                indexFieldDetailValue.setIsDefault(true);
                updateIndexFieldFromValue(indexFieldDetailValue, false, deletedBy);
            }
        }

        sendEvent(indexField.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteIndexFieldsByIndexType(IndexType indexType, BasePK deletedBy) {
        var indexFields = getIndexFieldsForUpdate(indexType);

        indexFields.forEach((indexField) -> 
                deleteIndexField(indexField, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Index Field Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected IndexFieldDescriptionFactory indexFieldDescriptionFactory;

    @Inject
    IndexFieldDescriptionTransferCache indexFieldDescriptionTransferCache;

    public IndexFieldDescription createIndexFieldDescription(IndexField indexField, Language language, String description,
            BasePK createdBy) {
        var indexFieldDescription = indexFieldDescriptionFactory.create(indexField,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(indexField.getPrimaryKey(), EventTypes.MODIFY, indexFieldDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return indexFieldDescription;
    }

    private IndexFieldDescription getIndexFieldDescription(IndexField indexField, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexFieldDescriptions.fields())
                .from(IndexFieldDescriptions)
                .where(IndexFieldDescriptions.INDEX_FIELD.eq(indexField.getPrimaryKey()),
                        IndexFieldDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        IndexFieldDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFieldDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexFieldDescription getIndexFieldDescription(IndexField indexField, Language language) {
        return getIndexFieldDescription(indexField, language, EntityPermission.READ_ONLY);
    }

    public IndexFieldDescription getIndexFieldDescriptionForUpdate(IndexField indexField, Language language) {
        return getIndexFieldDescription(indexField, language, EntityPermission.READ_WRITE);
    }

    public IndexFieldDescriptionValue getIndexFieldDescriptionValue(IndexFieldDescription indexFieldDescription) {
        return indexFieldDescription == null? null: indexFieldDescription.getIndexFieldDescriptionValue().clone();
    }

    public IndexFieldDescriptionValue getIndexFieldDescriptionValueForUpdate(IndexField indexField, Language language) {
        return getIndexFieldDescriptionValue(getIndexFieldDescriptionForUpdate(indexField, language));
    }

    private List<IndexFieldDescription> getIndexFieldDescriptionsByIndexField(IndexField indexField, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(IndexFieldDescriptions.fields())
                    .from(IndexFieldDescriptions)
                    .join(Languages).onKey(INDEX_FIELD_DESCRIPTIONS_LANGUAGE_FK)
                    .where(IndexFieldDescriptions.INDEX_FIELD.eq(indexField.getPrimaryKey()),
                            IndexFieldDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    IndexFieldDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(IndexFieldDescriptions.fields())
                    .from(IndexFieldDescriptions)
                    .where(IndexFieldDescriptions.INDEX_FIELD.eq(indexField.getPrimaryKey()),
                            IndexFieldDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return indexFieldDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<IndexFieldDescription> getIndexFieldDescriptionsByIndexField(IndexField indexField) {
        return getIndexFieldDescriptionsByIndexField(indexField, EntityPermission.READ_ONLY);
    }

    public List<IndexFieldDescription> getIndexFieldDescriptionsByIndexFieldForUpdate(IndexField indexField) {
        return getIndexFieldDescriptionsByIndexField(indexField, EntityPermission.READ_WRITE);
    }

    public String getBestIndexFieldDescription(IndexField indexField, Language language) {
        String description;
        var indexFieldDescription = getIndexFieldDescription(indexField, language);

        if(indexFieldDescription == null && !language.getIsDefault()) {
            indexFieldDescription = getIndexFieldDescription(indexField, partyControl.getDefaultLanguage());
        }

        if(indexFieldDescription == null) {
            description = indexField.getLastDetail().getIndexFieldName();
        } else {
            description = indexFieldDescription.getDescription();
        }

        return description;
    }

    public IndexFieldDescriptionTransfer getIndexFieldDescriptionTransfer(UserVisit userVisit, IndexFieldDescription indexFieldDescription) {
        return indexFieldDescriptionTransferCache.getIndexFieldDescriptionTransfer(userVisit, indexFieldDescription);
    }

    public List<IndexFieldDescriptionTransfer> getIndexFieldDescriptionTransfersByIndexField(UserVisit userVisit, IndexField indexField) {
        var indexFieldDescriptions = getIndexFieldDescriptionsByIndexField(indexField);
        List<IndexFieldDescriptionTransfer> indexFieldDescriptionTransfers = new ArrayList<>(indexFieldDescriptions.size());

        indexFieldDescriptions.forEach((indexFieldDescription) -> {
            indexFieldDescriptionTransfers.add(indexFieldDescriptionTransferCache.getIndexFieldDescriptionTransfer(userVisit, indexFieldDescription));
        });

        return indexFieldDescriptionTransfers;
    }

    public void updateIndexFieldDescriptionFromValue(IndexFieldDescriptionValue indexFieldDescriptionValue, BasePK updatedBy) {
        if(indexFieldDescriptionValue.hasBeenModified()) {
            var indexFieldDescription = indexFieldDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     indexFieldDescriptionValue.getPrimaryKey());

            indexFieldDescription.setThruTime(session.getStartTime());
            indexFieldDescription.store();

            var indexField = indexFieldDescription.getIndexField();
            var language = indexFieldDescription.getLanguage();
            var description = indexFieldDescriptionValue.getDescription();

            indexFieldDescription = indexFieldDescriptionFactory.create(indexField, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(indexField.getPrimaryKey(), EventTypes.MODIFY, indexFieldDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteIndexFieldDescription(IndexFieldDescription indexFieldDescription, BasePK deletedBy) {
        indexFieldDescription.setThruTime(session.getStartTime());

        sendEvent(indexFieldDescription.getIndexFieldPK(), EventTypes.MODIFY, indexFieldDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteIndexFieldDescriptionsByIndexField(IndexField indexField, BasePK deletedBy) {
        var indexFieldDescriptions = getIndexFieldDescriptionsByIndexFieldForUpdate(indexField);

        indexFieldDescriptions.forEach((indexFieldDescription) -> 
                deleteIndexFieldDescription(indexFieldDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Indexes
    // --------------------------------------------------------------------------------

    @Inject
    protected IndexDetailFactory indexDetailFactory;

    @Inject
    protected IndexFactory indexFactory;

    @Inject
    IndexTransferCache indexTransferCache;

    public Index createIndex(String indexName, IndexType indexType, Language language, String directory, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultIndex = getDefaultIndex();
        var defaultFound = defaultIndex != null;

        if(defaultFound && isDefault) {
            var defaultIndexDetailValue = getDefaultIndexDetailValueForUpdate();

            defaultIndexDetailValue.setIsDefault(false);
            updateIndexFromValue(defaultIndexDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var index = indexFactory.create();
        var indexDetail = indexDetailFactory.create(index, indexName, indexType, language, directory, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        index = indexFactory.getEntityFromPK(EntityPermission.READ_WRITE, index.getPrimaryKey());
        index.setActiveDetail(indexDetail);
        index.setLastDetail(indexDetail);
        index.store();

        sendEvent(index.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createIndexStatus(index);
        
        return index;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Index */
    public Index getIndexByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new IndexPK(entityInstance.getEntityUniqueId());

        return indexFactory.getEntityFromPK(entityPermission, pk);
    }

    public Index getIndexByEntityInstance(EntityInstance entityInstance) {
        return getIndexByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Index getIndexByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getIndexByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countIndexes() {
        return session.getDslContext()
                .selectCount()
                .from(Indexes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countIndexesByIndexType(IndexType indexType) {
        return session.getDslContext()
                .selectCount()
                .from(Indexes)
                .join(IndexDetails).onKey(INDEXES_ACTIVE_DETAIL_FK)
                .where(IndexDetails.INDEX_TYPE.eq(indexType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Index getIndex(IndexType indexType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Indexes.fields())
                .from(Indexes)
                .join(IndexDetails).onKey(INDEXES_ACTIVE_DETAIL_FK)
                .where(IndexDetails.INDEX_TYPE.eq(indexType.getPrimaryKey()),
                        IndexDetails.LANGUAGE.eq(language.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFactory.getEntityFromQuery(entityPermission, query);
    }

    public Index getIndex(IndexType indexType, Language language) {
        return getIndex(indexType, language, EntityPermission.READ_ONLY);
    }

    public Index getIndexForUpdate(IndexType indexType, Language language) {
        return getIndex(indexType, language, EntityPermission.READ_WRITE);
    }

    private Index getIndexByName(String indexName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Indexes.fields())
                .from(Indexes)
                .join(IndexDetails).onKey(INDEXES_ACTIVE_DETAIL_FK)
                .where(IndexDetails.INDEX_NAME.eq(indexName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFactory.getEntityFromQuery(entityPermission, query);
    }

    public Index getIndexByName(String indexName) {
        return getIndexByName(indexName, EntityPermission.READ_ONLY);
    }

    public Index getIndexByNameForUpdate(String indexName) {
        return getIndexByName(indexName, EntityPermission.READ_WRITE);
    }

    private Index getIndexByDirectory(String directory, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Indexes.fields())
                .from(Indexes)
                .join(IndexDetails).onKey(INDEXES_ACTIVE_DETAIL_FK)
                .where(IndexDetails.DIRECTORY.eq(directory));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFactory.getEntityFromQuery(entityPermission, query);
    }

    public Index getIndexByDirectory(String directory) {
        return getIndexByDirectory(directory, EntityPermission.READ_ONLY);
    }

    public Index getIndexByDirectoryForUpdate(String directory) {
        return getIndexByDirectory(directory, EntityPermission.READ_WRITE);
    }

    public IndexDetailValue getIndexDetailValueForUpdate(Index index) {
        return index == null? null: index.getLastDetailForUpdate().getIndexDetailValue().clone();
    }

    public IndexDetailValue getIndexDetailValueByNameForUpdate(String indexName) {
        return getIndexDetailValueForUpdate(getIndexByNameForUpdate(indexName));
    }

    private Index getDefaultIndex(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Indexes.fields())
                .from(Indexes)
                .join(IndexDetails).onKey(INDEXES_ACTIVE_DETAIL_FK)
                .where(IndexDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFactory.getEntityFromQuery(entityPermission, query);
    }

    public Index getDefaultIndex() {
        return getDefaultIndex(EntityPermission.READ_ONLY);
    }

    public Index getDefaultIndexForUpdate() {
        return getDefaultIndex(EntityPermission.READ_WRITE);
    }

    public IndexDetailValue getDefaultIndexDetailValueForUpdate() {
        return getDefaultIndexForUpdate().getLastDetailForUpdate().getIndexDetailValue().clone();
    }

    private List<Index> getIndexes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Indexes.fields())
                .from(Indexes)
                .join(IndexDetails).onKey(INDEXES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(IndexDetails.SORT_ORDER, IndexDetails.INDEX_NAME),
                    IndexFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Index> getIndexes() {
        return getIndexes(EntityPermission.READ_ONLY);
    }

    public List<Index> getIndexesForUpdate() {
        return getIndexes(EntityPermission.READ_WRITE);
    }

    private List<Index> getIndexesByIndexType(IndexType indexType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Indexes.fields())
                .from(Indexes)
                .join(IndexDetails).onKey(INDEXES_ACTIVE_DETAIL_FK)
                .where(IndexDetails.INDEX_TYPE.eq(indexType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(IndexDetails.SORT_ORDER, IndexDetails.INDEX_NAME),
                    IndexFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Index> getIndexesByIndexType(IndexType indexType) {
        return getIndexesByIndexType(indexType, EntityPermission.READ_ONLY);
    }

    public List<Index> getIndexesByIndexTypeForUpdate(IndexType indexType) {
        return getIndexesByIndexType(indexType, EntityPermission.READ_WRITE);
    }

    public Index getBestIndex(IndexType indexType, Language language) {
        var index = getIndex(indexType, language);

        if(index == null && !language.getIsDefault()) {
            index = getIndex(indexType, partyControl.getDefaultLanguage());
        }

        return index;
    }

   public IndexTransfer getIndexTransfer(UserVisit userVisit, Index index) {
        return indexTransferCache.getIndexTransfer(userVisit, index);
    }

    public List<IndexTransfer> getIndexTransfers(UserVisit userVisit, Collection<Index> indexes) {
        List<IndexTransfer> indexTransfers = new ArrayList<>(indexes.size());

        indexes.forEach((index) ->
                indexTransfers.add(indexTransferCache.getIndexTransfer(userVisit, index))
        );

        return indexTransfers;
    }

    public List<IndexTransfer> getIndexTransfers(UserVisit userVisit) {
        return getIndexTransfers(userVisit, getIndexes());
    }

    public IndexChoicesBean getIndexChoices(String defaultIndexChoice, Language language, boolean allowNullChoice) {
        var indexes = getIndexes();
        var size = indexes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultIndexChoice == null) {
                defaultValue = "";
            }
        }

        for(var index : indexes) {
            var indexDetail = index.getLastDetail();

            var label = getBestIndexDescription(index, language);
            var value = indexDetail.getIndexName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultIndexChoice != null && defaultIndexChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && indexDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new IndexChoicesBean(labels, values, defaultValue);
    }

    private void updateIndexFromValue(IndexDetailValue indexDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(indexDetailValue.hasBeenModified()) {
            var index = indexFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     indexDetailValue.getIndexPK());
            var indexDetail = index.getActiveDetailForUpdate();

            indexDetail.setThruTime(session.getStartTime());
            indexDetail.store();

            var indexPK = indexDetail.getIndexPK(); // Not updated
            var indexName = indexDetailValue.getIndexName();
            var indexTypePK = indexDetail.getIndexTypePK(); // Not updated
            var languagePK = indexDetail.getLanguagePK(); // Not updated
            var directory = indexDetailValue.getDirectory();
            var isDefault = indexDetailValue.getIsDefault();
            var sortOrder = indexDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultIndex = getDefaultIndex();
                var defaultFound = defaultIndex != null && !defaultIndex.equals(index);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultIndexDetailValue = getDefaultIndexDetailValueForUpdate();

                    defaultIndexDetailValue.setIsDefault(false);
                    updateIndexFromValue(defaultIndexDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            indexDetail = indexDetailFactory.create(indexPK, indexName, indexTypePK, languagePK, directory, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            index.setActiveDetail(indexDetail);
            index.setLastDetail(indexDetail);

            sendEvent(indexPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateIndexFromValue(IndexDetailValue indexDetailValue, BasePK updatedBy) {
        updateIndexFromValue(indexDetailValue, true, updatedBy);
    }

    private void deleteIndex(Index index, boolean checkDefault, BasePK deletedBy) {
        var indexDetail = index.getLastDetailForUpdate();

        searchControl.deleteCachedSearchesByIndex(index, deletedBy);
        removeIndexStatusByIndex(index);
        deleteIndexDescriptionsByIndex(index, deletedBy);

        indexDetail.setThruTime(session.getStartTime());
        index.setActiveDetail(null);
        index.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultIndex = getDefaultIndex();

            if(defaultIndex == null) {
                var indexes = getIndexesForUpdate();

                if(!indexes.isEmpty()) {
                    var iter = indexes.iterator();
                    if(iter.hasNext()) {
                        defaultIndex = iter.next();
                    }
                    var indexDetailValue = Objects.requireNonNull(defaultIndex).getLastDetailForUpdate().getIndexDetailValue().clone();

                    indexDetailValue.setIsDefault(true);
                    updateIndexFromValue(indexDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(index.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteIndex(Index index, BasePK deletedBy) {
        deleteIndex(index, true, deletedBy);
    }

    private void deleteIndexes(List<Index> indexes, boolean checkDefault, BasePK deletedBy) {
        indexes.forEach((index) -> deleteIndex(index, checkDefault, deletedBy));
    }

    public void deleteIndexes(List<Index> indexes, BasePK deletedBy) {
        deleteIndexes(indexes, true, deletedBy);
    }

    public void deleteIndexesByIndexType(IndexType indexType, BasePK deletedBy) {
        deleteIndexes(getIndexesByIndexTypeForUpdate(indexType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Index Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected IndexDescriptionFactory indexDescriptionFactory;

    @Inject
    IndexDescriptionTransferCache indexDescriptionTransferCache;

    public IndexDescription createIndexDescription(Index index, Language language, String description, BasePK createdBy) {
        var indexDescription = indexDescriptionFactory.create(index, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(index.getPrimaryKey(), EventTypes.MODIFY, indexDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return indexDescription;
    }

    private IndexDescription getIndexDescription(Index index, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexDescriptions.fields())
                .from(IndexDescriptions)
                .where(IndexDescriptions.INDEX.eq(index.getPrimaryKey()),
                        IndexDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        IndexDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexDescription getIndexDescription(Index index, Language language) {
        return getIndexDescription(index, language, EntityPermission.READ_ONLY);
    }

    public IndexDescription getIndexDescriptionForUpdate(Index index, Language language) {
        return getIndexDescription(index, language, EntityPermission.READ_WRITE);
    }

    public IndexDescriptionValue getIndexDescriptionValue(IndexDescription indexDescription) {
        return indexDescription == null? null: indexDescription.getIndexDescriptionValue().clone();
    }

    public IndexDescriptionValue getIndexDescriptionValueForUpdate(Index index, Language language) {
        return getIndexDescriptionValue(getIndexDescriptionForUpdate(index, language));
    }

    private List<IndexDescription> getIndexDescriptionsByIndex(Index index, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(IndexDescriptions.fields())
                    .from(IndexDescriptions)
                    .join(Languages).onKey(INDEX_DESCRIPTIONS_LANGUAGE_FK)
                    .where(IndexDescriptions.INDEX.eq(index.getPrimaryKey()),
                            IndexDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    IndexDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(IndexDescriptions.fields())
                    .from(IndexDescriptions)
                    .where(IndexDescriptions.INDEX.eq(index.getPrimaryKey()),
                            IndexDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return indexDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<IndexDescription> getIndexDescriptionsByIndex(Index index) {
        return getIndexDescriptionsByIndex(index, EntityPermission.READ_ONLY);
    }

    public List<IndexDescription> getIndexDescriptionsByIndexForUpdate(Index index) {
        return getIndexDescriptionsByIndex(index, EntityPermission.READ_WRITE);
    }

    public String getBestIndexDescription(Index index, Language language) {
        String description;
        var indexDescription = getIndexDescription(index, language);

        if(indexDescription == null && !language.getIsDefault()) {
            indexDescription = getIndexDescription(index, partyControl.getDefaultLanguage());
        }

        if(indexDescription == null) {
            description = index.getLastDetail().getIndexName();
        } else {
            description = indexDescription.getDescription();
        }

        return description;
    }

    public IndexDescriptionTransfer getIndexDescriptionTransfer(UserVisit userVisit, IndexDescription indexDescription) {
        return indexDescriptionTransferCache.getIndexDescriptionTransfer(userVisit, indexDescription);
    }

    public List<IndexDescriptionTransfer> getIndexDescriptionTransfersByIndex(UserVisit userVisit, Index index) {
        var indexDescriptions = getIndexDescriptionsByIndex(index);
        List<IndexDescriptionTransfer> indexDescriptionTransfers = new ArrayList<>(indexDescriptions.size());

        indexDescriptions.forEach((indexDescription) ->
                indexDescriptionTransfers.add(indexDescriptionTransferCache.getIndexDescriptionTransfer(userVisit, indexDescription))
        );

        return indexDescriptionTransfers;
    }

    public void updateIndexDescriptionFromValue(IndexDescriptionValue indexDescriptionValue, BasePK updatedBy) {
        if(indexDescriptionValue.hasBeenModified()) {
            var indexDescription = indexDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    indexDescriptionValue.getPrimaryKey());

            indexDescription.setThruTime(session.getStartTime());
            indexDescription.store();

            var index = indexDescription.getIndex();
            var language = indexDescription.getLanguage();
            var description = indexDescriptionValue.getDescription();

            indexDescription = indexDescriptionFactory.create(index, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(index.getPrimaryKey(), EventTypes.MODIFY, indexDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteIndexDescription(IndexDescription indexDescription, BasePK deletedBy) {
        indexDescription.setThruTime(session.getStartTime());

        sendEvent(indexDescription.getIndexPK(), EventTypes.MODIFY, indexDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteIndexDescriptionsByIndex(Index index, BasePK deletedBy) {
        var indexDescriptions = getIndexDescriptionsByIndexForUpdate(index);

        indexDescriptions.forEach((indexDescription) -> 
                deleteIndexDescription(indexDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Index Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected IndexStatusFactory indexStatusFactory;

    public IndexStatus createIndexStatus(Index index) {
        return indexStatusFactory.create(index, null);
    }
    
    private IndexStatus getIndexStatus(Index index, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IndexStatuses.fields())
                .from(IndexStatuses)
                .where(IndexStatuses.INDEX.eq(index.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return indexStatusFactory.getEntityFromQuery(entityPermission, query);
    }

    public IndexStatus getIndexStatus(Index index) {
        return getIndexStatus(index, EntityPermission.READ_ONLY);
    }
    
    public IndexStatus getIndexStatusForUpdate(Index index) {
        return getIndexStatus(index, EntityPermission.READ_WRITE);
    }
    
    public void removeIndexStatusByIndex(Index index) {
        var indexStatus = getIndexStatusForUpdate(index);
        
        if(indexStatus != null) {
            indexStatus.remove();
        }
    }
    
}
