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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class IndexControl
        extends BaseModelControl {
    
    /** Creates a new instance of IndexControl */
    protected IndexControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Index Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    IndexTypeTransferCache indexTypeTransferCache;

    @Inject
    IndexTypeDescriptionTransferCache indexTypeDescriptionTransferCache;

    @Inject
    IndexFieldTransferCache indexFieldTransferCache;

    @Inject
    IndexFieldDescriptionTransferCache indexFieldDescriptionTransferCache;

    @Inject
    IndexTransferCache indexTransferCache;

    @Inject
    IndexDescriptionTransferCache indexDescriptionTransferCache;

    // --------------------------------------------------------------------------------
    //   Index Types
    // --------------------------------------------------------------------------------

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

        var indexType = IndexTypeFactory.getInstance().create();
        var indexTypeDetail = IndexTypeDetailFactory.getInstance().create(indexType, indexTypeName, entityType, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME_LONG);

        // Convert to R/W
        indexType = IndexTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, indexType.getPrimaryKey());
        indexType.setActiveDetail(indexTypeDetail);
        indexType.setLastDetail(indexTypeDetail);
        indexType.store();

        sendEvent(indexType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return indexType;
    }

    public long countIndexTypesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid AND idxtdt_ent_entitytypeid = ?",
                entityType);
    }

    public boolean isEntityTypeUsedByIndexTypes(EntityType entityType) {
        return countIndexTypesByEntityType(entityType) != 0;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.IndexType */
    public IndexType getIndexTypeByEntityInstance(EntityInstance entityInstance) {
        var pk = new IndexTypePK(entityInstance.getEntityUniqueId());
        var indexType = IndexTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return indexType;
    }

    private static final Map<EntityPermission, String> getIndexTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_indextypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_indextypename = ? " +
                "FOR UPDATE");
        getIndexTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexType getIndexTypeByName(String indexTypeName, EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntityFromQuery(entityPermission, getIndexTypeByNameQueries, indexTypeName);
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

    private static final Map<EntityPermission, String> getDefaultIndexTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultIndexTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexType getDefaultIndexType(EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultIndexTypeQueries);
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

    private static final Map<EntityPermission, String> getIndexTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "ORDER BY idxtdt_sortorder, idxtdt_indextypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "FOR UPDATE");
        getIndexTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexType> getIndexTypes(EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexTypesQueries);
    }

    public List<IndexType> getIndexTypes() {
        return getIndexTypes(EntityPermission.READ_ONLY);
    }

    public List<IndexType> getIndexTypesForUpdate() {
        return getIndexTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexTypesByEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indextypes, indextypedetails "
                + "WHERE idxt_activedetailid = idxtdt_indextypedetailid AND idxtdt_ent_entitytypeid = ? "
                + "ORDER BY idxtdt_sortorder, idxtdt_indextypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indextypes, indextypedetails "
                + "WHERE idxt_activedetailid = idxtdt_indextypedetailid AND idxtdt_ent_entitytypeid = ? "
                + "FOR UPDATE");
        getIndexTypesByEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexType> getIndexTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexTypesByEntityTypeQueries,
                entityType);
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
            var indexType = IndexTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

            indexTypeDetail = IndexTypeDetailFactory.getInstance().create(indexTypePK, indexTypeName, entityTypePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME_LONG);

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

    public IndexTypeDescription createIndexTypeDescription(IndexType indexType, Language language, String description, BasePK createdBy) {
        var indexTypeDescription = IndexTypeDescriptionFactory.getInstance().create(indexType, language, description,
                session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(indexType.getPrimaryKey(), EventTypes.MODIFY, indexTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return indexTypeDescription;
    }

    private static final Map<EntityPermission, String> getIndexTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_lang_languageid = ? AND idxtd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_lang_languageid = ? AND idxtd_thrutime = ? " +
                "FOR UPDATE");
        getIndexTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexTypeDescription getIndexTypeDescription(IndexType indexType, Language language, EntityPermission entityPermission) {
        return IndexTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getIndexTypeDescriptionQueries,
                indexType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getIndexTypeDescriptionsByIndexTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions, languages " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_thrutime = ? AND idxtd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_thrutime = ? " +
                "FOR UPDATE");
        getIndexTypeDescriptionsByIndexTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexTypeDescription> getIndexTypeDescriptionsByIndexType(IndexType indexType, EntityPermission entityPermission) {
        return IndexTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexTypeDescriptionsByIndexTypeQueries,
                indexType, Session.MAX_TIME);
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
            var indexTypeDescription = IndexTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    indexTypeDescriptionValue.getPrimaryKey());

            indexTypeDescription.setThruTime(session.getStartTime());
            indexTypeDescription.store();

            var indexType = indexTypeDescription.getIndexType();
            var language = indexTypeDescription.getLanguage();
            var description = indexTypeDescriptionValue.getDescription();

            indexTypeDescription = IndexTypeDescriptionFactory.getInstance().create(indexType, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);

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
    //   Search Types
    // --------------------------------------------------------------------------------

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

        var indexField = IndexFieldFactory.getInstance().create();
        var indexFieldDetail = IndexFieldDetailFactory.getInstance().create(session, indexField, indexType, indexFieldName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME_LONG);

        // Convert to R/W
        indexField = IndexFieldFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                indexField.getPrimaryKey());
        indexField.setActiveDetail(indexFieldDetail);
        indexField.setLastDetail(indexFieldDetail);
        indexField.store();

        sendEvent(indexField.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return indexField;
    }

    private static final Map<EntityPermission, String> getIndexFieldsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid AND idxflddt_idxt_indextypeid = ? "
                + "ORDER BY idxflddt_sortorder, idxflddt_indexfieldname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid AND idxflddt_idxt_indextypeid = ? "
                + "FOR UPDATE");
        getIndexFieldsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexField> getIndexFields(IndexType indexType, EntityPermission entityPermission) {
        return IndexFieldFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexFieldsQueries,
                indexType);
    }

    public List<IndexField> getIndexFields(IndexType indexType) {
        return getIndexFields(indexType, EntityPermission.READ_ONLY);
    }

    public List<IndexField> getIndexFieldsForUpdate(IndexType indexType) {
        return getIndexFields(indexType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultIndexFieldQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultIndexFieldQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexField getDefaultIndexField(IndexType indexType, EntityPermission entityPermission) {
        return IndexFieldFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultIndexFieldQueries,
                indexType);
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

    private static final Map<EntityPermission, String> getIndexFieldByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_indexfieldname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_indexfieldname = ? "
                + "FOR UPDATE");
        getIndexFieldByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexField getIndexFieldByName(IndexType indexType, String indexFieldName, EntityPermission entityPermission) {
        return IndexFieldFactory.getInstance().getEntityFromQuery(entityPermission, getIndexFieldByNameQueries,
                indexType, indexFieldName);
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
            var indexField = IndexFieldFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

            indexFieldDetail = IndexFieldDetailFactory.getInstance().create(indexFieldPK, indexTypePK, indexFieldName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME_LONG);

            indexField.setActiveDetail(indexFieldDetail);
            indexField.setLastDetail(indexFieldDetail);

            sendEvent(indexFieldPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateIndexFieldFromValue(IndexFieldDetailValue indexFieldDetailValue, BasePK updatedBy) {
        updateIndexFieldFromValue(indexFieldDetailValue, true, updatedBy);
    }

    public void deleteIndexField(IndexField indexField, BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);
        
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
    //   Search Type Descriptions
    // --------------------------------------------------------------------------------

    public IndexFieldDescription createIndexFieldDescription(IndexField indexField, Language language, String description,
            BasePK createdBy) {
        var indexFieldDescription = IndexFieldDescriptionFactory.getInstance().create(indexField,
                language, description, session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(indexField.getPrimaryKey(), EventTypes.MODIFY, indexFieldDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return indexFieldDescription;
    }

    private static final Map<EntityPermission, String> getIndexFieldDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_lang_languageid = ? AND idxfldd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_lang_languageid = ? AND idxfldd_thrutime = ? "
                + "FOR UPDATE");
        getIndexFieldDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexFieldDescription getIndexFieldDescription(IndexField indexField, Language language, EntityPermission entityPermission) {
        return IndexFieldDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getIndexFieldDescriptionQueries,
                indexField, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getIndexFieldDescriptionsByIndexFieldQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions, languages "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_thrutime = ? AND idxfldd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_thrutime = ? "
                + "FOR UPDATE");
        getIndexFieldDescriptionsByIndexFieldQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexFieldDescription> getIndexFieldDescriptionsByIndexField(IndexField indexField, EntityPermission entityPermission) {
        return IndexFieldDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexFieldDescriptionsByIndexFieldQueries,
                indexField, Session.MAX_TIME);
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
            var indexFieldDescription = IndexFieldDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     indexFieldDescriptionValue.getPrimaryKey());

            indexFieldDescription.setThruTime(session.getStartTime());
            indexFieldDescription.store();

            var indexField = indexFieldDescription.getIndexField();
            var language = indexFieldDescription.getLanguage();
            var description = indexFieldDescriptionValue.getDescription();

            indexFieldDescription = IndexFieldDescriptionFactory.getInstance().create(indexField, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);

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

        var index = IndexFactory.getInstance().create();
        var indexDetail = IndexDetailFactory.getInstance().create(index, indexName, indexType, language, directory, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME_LONG);

        // Convert to R/W
        index = IndexFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, index.getPrimaryKey());
        index.setActiveDetail(indexDetail);
        index.setLastDetail(indexDetail);
        index.store();

        sendEvent(index.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createIndexStatus(index);
        
        return index;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Index */
    public Index getIndexByEntityInstance(EntityInstance entityInstance) {
        var pk = new IndexPK(entityInstance.getEntityUniqueId());
        var index = IndexFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return index;
    }

    public long countIndexes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM indexes");
    }

    public long countIndexesByIndexType(IndexType indexType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid AND idxdt_idxt_indextypeid = ?",
                indexType);
    }

    private static final Map<EntityPermission, String> getIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_idxt_indextypeid = ? AND idxdt_lang_languageid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_idxt_indextypeid = ? AND idxdt_lang_languageid = ? " +
                "FOR UPDATE");
        getIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getIndex(IndexType indexType, Language language, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getIndexQueries, indexType, language);
    }

    public Index getIndex(IndexType indexType, Language language) {
        return getIndex(indexType, language, EntityPermission.READ_ONLY);
    }

    public Index getIndexForUpdate(IndexType indexType, Language language) {
        return getIndex(indexType, language, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_indexname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_indexname = ? " +
                "FOR UPDATE");
        getIndexByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getIndexByName(String indexName, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getIndexByNameQueries, indexName);
    }

    public Index getIndexByName(String indexName) {
        return getIndexByName(indexName, EntityPermission.READ_ONLY);
    }

    public Index getIndexByNameForUpdate(String indexName) {
        return getIndexByName(indexName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexByDirectoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_directory = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_directory = ? " +
                "FOR UPDATE");
        getIndexByDirectoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getIndexByDirectory(String directory, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getIndexByDirectoryQueries, directory);
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

    private static final Map<EntityPermission, String> getDefaultIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getDefaultIndex(EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultIndexQueries);
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

    private static final Map<EntityPermission, String> getIndexesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "ORDER BY idxdt_sortorder, idxdt_indexname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "FOR UPDATE");
        getIndexesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Index> getIndexes(EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexesQueries);
    }

    public List<Index> getIndexes() {
        return getIndexes(EntityPermission.READ_ONLY);
    }

    public List<Index> getIndexesForUpdate() {
        return getIndexes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexesByIndexTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexes, indexdetails "
                + "WHERE idx_activedetailid = idxdt_indexdetailid "
                + "AND idxdt_idxt_indextypeid = ? "
                + "ORDER BY idxdt_sortorder, idxdt_indexname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexes, indexdetails "
                + "WHERE idx_activedetailid = idxdt_indexdetailid "
                + "AND idxdt_idxt_indextypeid = ? "
                + "FOR UPDATE");
        getIndexesByIndexTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Index> getIndexesByIndexType(IndexType indexType, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexesByIndexTypeQueries,
                indexType);
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

    public List<IndexTransfer> getIndexTransfers(UserVisit userVisit) {
        var indexes = getIndexes();
        List<IndexTransfer> indexTransfers = new ArrayList<>(indexes.size());

        indexes.forEach((index) ->
                indexTransfers.add(indexTransferCache.getIndexTransfer(userVisit, index))
        );

        return indexTransfers;
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
            var index = IndexFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

            indexDetail = IndexDetailFactory.getInstance().create(indexPK, indexName, indexTypePK, languagePK, directory, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME_LONG);

            index.setActiveDetail(indexDetail);
            index.setLastDetail(indexDetail);

            sendEvent(indexPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateIndexFromValue(IndexDetailValue indexDetailValue, BasePK updatedBy) {
        updateIndexFromValue(indexDetailValue, true, updatedBy);
    }

    private void deleteIndex(Index index, boolean checkDefault, BasePK deletedBy) {
        var searchControl = Session.getModelController(SearchControl.class);
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

    public IndexDescription createIndexDescription(Index index, Language language, String description, BasePK createdBy) {
        var indexDescription = IndexDescriptionFactory.getInstance().create(index, language, description,
                session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(index.getPrimaryKey(), EventTypes.MODIFY, indexDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return indexDescription;
    }

    private static final Map<EntityPermission, String> getIndexDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexdescriptions " +
                "WHERE idxd_idx_indexid = ? AND idxd_lang_languageid = ? AND idxd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexdescriptions " +
                "WHERE idxd_idx_indexid = ? AND idxd_lang_languageid = ? AND idxd_thrutime = ? " +
                "FOR UPDATE");
        getIndexDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexDescription getIndexDescription(Index index, Language language, EntityPermission entityPermission) {
        return IndexDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getIndexDescriptionQueries,
                index, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getIndexDescriptionsByIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexdescriptions, languages " +
                "WHERE idxd_idx_indexid = ? AND idxd_thrutime = ? AND idxd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexdescriptions " +
                "WHERE idxd_idx_indexid = ? AND idxd_thrutime = ? " +
                "FOR UPDATE");
        getIndexDescriptionsByIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexDescription> getIndexDescriptionsByIndex(Index index, EntityPermission entityPermission) {
        return IndexDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexDescriptionsByIndexQueries,
                index, Session.MAX_TIME);
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
            var indexDescription = IndexDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    indexDescriptionValue.getPrimaryKey());

            indexDescription.setThruTime(session.getStartTime());
            indexDescription.store();

            var index = indexDescription.getIndex();
            var language = indexDescription.getLanguage();
            var description = indexDescriptionValue.getDescription();

            indexDescription = IndexDescriptionFactory.getInstance().create(index, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);

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
    
    public IndexStatus createIndexStatus(Index index) {
        return IndexStatusFactory.getInstance().create(index, null);
    }
    
    private static final Map<EntityPermission, String> getIndexStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexstatuses "
                + "WHERE idxst_idx_indexid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexstatuses "
                + "WHERE idxst_idx_indexid = ? "
                + "FOR UPDATE");
        getIndexStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexStatus getIndexStatus(Index index, EntityPermission entityPermission) {
        return IndexStatusFactory.getInstance().getEntityFromQuery(entityPermission, getIndexStatusQueries,
                index);
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
