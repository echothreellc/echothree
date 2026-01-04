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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.transfer.EntityTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeResultTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.graphql.EntityTypeObject;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDescription;
import com.echothree.model.data.core.server.factory.EntityTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityTypeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityTypeFactory;
import com.echothree.model.data.core.server.value.EntityTypeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class EntityTypeControl
        extends BaseCoreControl {

    @Inject
    protected AccountingControl accountingControl;

    @Inject
    protected BatchControl batchControl;

    @Inject
    protected CommentControl commentControl;

    @Inject
    protected CoreControl coreControl;

    @Inject
    protected EntityAliasControl entityAliasControl;

    @Inject
    protected IndexControl indexControl;

    @Inject
    protected MessageControl messageControl;

    @Inject
    protected RatingControl ratingControl;

    @Inject
    protected TagControl tagControl;

    /** Creates a new instance of EntityTypeControl */
    protected EntityTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Types
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityTypeFactory entityTypeFactory;
    
    @Inject
    protected EntityTypeDetailFactory entityTypeDetailFactory;

    public EntityType createEntityType(ComponentVendor componentVendor, String entityTypeName, Boolean keepAllHistory,
            Long lockTimeout, Boolean isExtensible, Integer sortOrder, BasePK createdBy) {
        var entityType = entityTypeFactory.create();
        var entityTypeDetail = entityTypeDetailFactory.create(entityType, componentVendor,
                entityTypeName, keepAllHistory, lockTimeout, isExtensible, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        entityType = entityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityType.getPrimaryKey());
        entityType.setActiveDetail(entityTypeDetail);
        entityType.setLastDetail(entityTypeDetail);
        entityType.store();

        sendEvent(entityType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return entityType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityType */
    public EntityType getEntityTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityTypePK(entityInstance.getEntityUniqueId());

        return entityTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityType getEntityTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entitytypes, entitytypedetails " +
                        "WHERE ent_activedetailid = entdt_entitytypedetailid");
    }

    public long countEntityTypesByComponentVendor(ComponentVendor componentVendor) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entitytypes, entitytypedetails " +
                        "WHERE ent_activedetailid = entdt_entitytypedetailid AND entdt_cvnd_componentvendorid = ?",
                componentVendor);
    }

    public EntityType getEntityTypeByName(ComponentVendor componentVendor, String entityTypeName, EntityPermission entityPermission) {
        EntityType entityType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypes, entitytypedetails " +
                        "WHERE ent_activedetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = ? AND entdt_entitytypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypes, entitytypedetails " +
                        "WHERE ent_activedetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = ? AND entdt_entitytypename = ? " +
                        "FOR UPDATE";
            }

            var ps = entityTypeFactory.prepareStatement(query);

            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            ps.setString(2, entityTypeName);

            entityType = entityTypeFactory.getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityType;
    }

    public EntityType getEntityTypeByName(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeByName(componentVendor, entityTypeName, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByNameForUpdate(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeByName(componentVendor, entityTypeName, EntityPermission.READ_WRITE);
    }

    public EntityTypeDetailValue getEntityTypeDetailValueForUpdate(EntityType entityType) {
        return entityType == null? null: entityType.getLastDetailForUpdate().getEntityTypeDetailValue().clone();
    }

    public EntityTypeDetailValue getEntityTypeDetailValueByNameForUpdate(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeDetailValueForUpdate(getEntityTypeByNameForUpdate(componentVendor, entityTypeName));
    }

    private final Map<ComponentVendor, Map<String, EntityType>> entityTypeCache = new HashMap<>();

    public EntityType getEntityTypeByNameFromCache(ComponentVendor componentVendor, String entityTypeName) {
        var cacheByComponentVendor = entityTypeCache.computeIfAbsent(componentVendor, k -> new HashMap<>());

        var entityType = cacheByComponentVendor.get(entityTypeName);

        if(entityType == null) {
            entityType = getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                cacheByComponentVendor.put(entityTypeName, entityType);
            }
        }

        return entityType;
    }

    private List<EntityType> getEntityTypesByComponentVendor(ComponentVendor componentVendor, EntityPermission entityPermission) {
        List<EntityType> entityTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitytypes, entitytypedetails "
                        + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                        + "AND entdt_cvnd_componentvendorid = ? "
                        + "ORDER BY entdt_sortorder, entdt_entitytypename "
                        + "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitytypes, entitytypedetails "
                        + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                        + "AND entdt_cvnd_componentvendorid = ? "
                        + "FOR UPDATE";
            }

            var ps = entityTypeFactory.prepareStatement(query);

            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());

            entityTypes = entityTypeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTypes;
    }

    public List<EntityType> getEntityTypesByComponentVendor(ComponentVendor componentVendor) {
        return getEntityTypesByComponentVendor(componentVendor, EntityPermission.READ_ONLY);
    }

    public List<EntityType> getEntityTypesByComponentVendorForUpdate(ComponentVendor componentVendor) {
        return getEntityTypesByComponentVendor(componentVendor, EntityPermission.READ_WRITE);
    }

    private List<EntityType> getEntityTypesByName(String entityTypeName, EntityPermission entityPermission) {
        List<EntityType> entityTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitytypes, entitytypedetails "
                        + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                        + "AND entdt_entitytypename = ? "
                        + "ORDER BY entdt_sortorder, entdt_entitytypename "
                        + "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitytypes, entitytypedetails "
                        + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                        + "AND entdt_entitytypename = ? "
                        + "FOR UPDATE";
            }

            var ps = entityTypeFactory.prepareStatement(query);

            ps.setString(1, entityTypeName);

            entityTypes = entityTypeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTypes;
    }

    public List<EntityType> getEntityTypesByName(String entityTypeName) {
        return getEntityTypesByName(entityTypeName, EntityPermission.READ_ONLY);
    }

    public List<EntityType> getEntityTypesByNameForUpdate(String entityTypeName) {
        return getEntityTypesByName(entityTypeName, EntityPermission.READ_WRITE);
    }

    public List<EntityType> getEntityTypes() {
        var ps = entityTypeFactory.prepareStatement(
                "SELECT _ALL_ "
                        + "FROM entitytypes, entitytypedetails "
                        + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                        + "ORDER BY entdt_sortorder, entdt_entitytypename "
                        + "_LIMIT_");

        return entityTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public EntityTypeTransfer getEntityTypeTransfer(UserVisit userVisit, EntityType entityType) {
        return entityTypeTransferCache.getEntityTypeTransfer(userVisit, entityType);
    }

    public List<EntityTypeTransfer> getEntityTypeTransfers(UserVisit userVisit, Collection<EntityType> entityTypes) {
        List<EntityTypeTransfer> entityTypeTransfers = new ArrayList<>(entityTypes.size());

        entityTypes.forEach((entityType) ->
                entityTypeTransfers.add(entityTypeTransferCache.getEntityTypeTransfer(userVisit, entityType))
        );

        return entityTypeTransfers;
    }

    public List<EntityTypeTransfer> getEntityTypeTransfers(UserVisit userVisit) {
        return getEntityTypeTransfers(userVisit, getEntityTypes());
    }

    public List<EntityTypeTransfer> getEntityTypeTransfersByComponentVendor(UserVisit userVisit, ComponentVendor componentVendor) {
        return getEntityTypeTransfers(userVisit, getEntityTypesByComponentVendor(componentVendor));
    }

    public void updateEntityTypeFromValue(EntityTypeDetailValue entityTypeDetailValue, BasePK updatedBy) {
        if(entityTypeDetailValue.hasBeenModified()) {
            var entityType = entityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityTypeDetailValue.getEntityTypePK());
            var entityTypeDetail = entityType.getActiveDetailForUpdate();

            entityTypeDetail.setThruTime(session.getStartTime());
            entityTypeDetail.store();

            var entityTypePK = entityTypeDetail.getEntityTypePK();
            var componentVendorPK = entityTypeDetail.getComponentVendorPK(); // Not updated
            var entityTypeName = entityTypeDetailValue.getEntityTypeName();
            var keepAllHistory = entityTypeDetailValue.getKeepAllHistory();
            var lockTimeout = entityTypeDetailValue.getLockTimeout();
            var isExtensible = entityTypeDetailValue.getIsExtensible();
            var sortOrder = entityTypeDetailValue.getSortOrder();

            entityTypeDetail = entityTypeDetailFactory.create(entityTypePK, componentVendorPK, entityTypeName,
                    keepAllHistory, lockTimeout, isExtensible, sortOrder, session.getStartTime(), Session.MAX_TIME);

            entityType.setActiveDetail(entityTypeDetail);
            entityType.setLastDetail(entityTypeDetail);

            sendEvent(entityTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public EntityType getEntityTypeByPK(EntityTypePK entityTypePK) {
        return entityTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, entityTypePK);
    }

    public void deleteEntityType(EntityType entityType, BasePK deletedBy) {
        entityInstanceControl.deleteEntityInstancesByEntityTypeWithNullDeletedTime(entityType, deletedBy);
        deleteEntityTypeDescriptionsByEntityType(entityType, deletedBy);
        entityAliasControl.deleteEntityAliasTypesByEntityType(entityType, deletedBy);
        coreControl.deleteEntityAttributesByEntityType(entityType, deletedBy);
        coreControl.deleteEntityAttributeEntityTypesByAllowedEntityType(entityType, deletedBy);
        accountingControl.deleteTransactionEntityRoleTypesByEntityType(entityType, deletedBy);
        batchControl.deleteBatchTypeEntityTypesByEntityType(entityType, deletedBy);
        commentControl.deleteCommentTypesByEntityType(entityType, deletedBy);
        indexControl.deleteIndexTypesByEntityType(entityType, deletedBy);
        messageControl.deleteMessageTypesByEntityType(entityType, deletedBy);
        ratingControl.deleteRatingTypesByEntityType(entityType, deletedBy);
        tagControl.deleteTagScopeEntityTypesByEntityType(entityType, deletedBy);
        workflowControl.deleteWorkflowEntityTypesByEntityType(entityType, deletedBy);

        var entityTypeDetail = entityType.getLastDetailForUpdate();
        entityTypeDetail.setThruTime(session.getStartTime());
        entityType.setActiveDetail(null);
        entityType.store();

        sendEvent(entityType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteEntityTypesByComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        var entityTypes = getEntityTypesByComponentVendorForUpdate(componentVendor);

        entityTypes.forEach((entityType) ->
                deleteEntityType(entityType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityTypeDescriptionFactory entityTypeDescriptionFactory;
    
    public EntityTypeDescription createEntityTypeDescription(EntityType entityType, Language language, String description,
            BasePK createdBy) {
        var entityTypeDescription = entityTypeDescriptionFactory.create(entityType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityType.getPrimaryKey(), EventTypes.MODIFY, entityTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityTypeDescription;
    }

    private EntityTypeDescription getEntityTypeDescription(EntityType entityType, Language language,
            EntityPermission entityPermission) {
        EntityTypeDescription entityTypeDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_lang_languageid = ? AND entd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_lang_languageid = ? AND entd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityTypeDescriptionFactory.prepareStatement(query);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityTypeDescription = entityTypeDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTypeDescription;
    }

    public EntityTypeDescription getEntityTypeDescription(EntityType entityType, Language language) {
        return getEntityTypeDescription(entityType, language, EntityPermission.READ_ONLY);
    }

    public EntityTypeDescription getEntityTypeDescriptionForUpdate(EntityType entityType, Language language) {
        return getEntityTypeDescription(entityType, language, EntityPermission.READ_WRITE);
    }

    public EntityTypeDescriptionValue getEntityTypeDescriptionValue(EntityTypeDescription entityTypeDescription) {
        return entityTypeDescription == null? null: entityTypeDescription.getEntityTypeDescriptionValue().clone();
    }

    public EntityTypeDescriptionValue getEntityTypeDescriptionValueForUpdate(EntityType entityType, Language language) {
        return getEntityTypeDescriptionValue(getEntityTypeDescriptionForUpdate(entityType, language));
    }

    private List<EntityTypeDescription> getEntityTypeDescriptionsByEntityType(EntityType entityType,
            EntityPermission entityPermission) {
        List<EntityTypeDescription> entityTypeDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions, languages " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_thrutime = ? AND entd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityTypeDescriptionFactory.prepareStatement(query);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityTypeDescriptions = entityTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTypeDescriptions;
    }

    public List<EntityTypeDescription> getEntityTypeDescriptionsByEntityType(EntityType entityType) {
        return getEntityTypeDescriptionsByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<EntityTypeDescription> getEntityTypeDescriptionsByEntityTypeForUpdate(EntityType entityType) {
        return getEntityTypeDescriptionsByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    public String getBestEntityTypeDescription(EntityType entityType, Language language) {
        String description;
        var entityTypeDescription = getEntityTypeDescription(entityType, language);

        if(entityTypeDescription == null && !language.getIsDefault()) {
            entityTypeDescription = getEntityTypeDescription(entityType, partyControl.getDefaultLanguage());
        }

        if(entityTypeDescription == null) {
            description = entityType.getLastDetail().getEntityTypeName();
        } else {
            description = entityTypeDescription.getDescription();
        }

        return description;
    }

    public EntityTypeDescriptionTransfer getEntityTypeDescriptionTransfer(UserVisit userVisit, EntityTypeDescription entityTypeDescription) {
        return entityTypeDescriptionTransferCache.getEntityTypeDescriptionTransfer(userVisit, entityTypeDescription);
    }

    public List<EntityTypeDescriptionTransfer> getEntityTypeDescriptionTransfersByEntityType(UserVisit userVisit,
            EntityType entityType) {
        var entityTypeDescriptions = getEntityTypeDescriptionsByEntityType(entityType);
        List<EntityTypeDescriptionTransfer> entityTypeDescriptionTransfers = new ArrayList<>(entityTypeDescriptions.size());

        entityTypeDescriptions.forEach((entityTypeDescription) ->
                entityTypeDescriptionTransfers.add(entityTypeDescriptionTransferCache.getEntityTypeDescriptionTransfer(userVisit, entityTypeDescription))
        );

        return entityTypeDescriptionTransfers;
    }

    public void updateEntityTypeDescriptionFromValue(EntityTypeDescriptionValue entityTypeDescriptionValue, BasePK updatedBy) {
        if(entityTypeDescriptionValue.hasBeenModified()) {
            var entityTypeDescription = entityTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityTypeDescriptionValue.getPrimaryKey());

            entityTypeDescription.setThruTime(session.getStartTime());
            entityTypeDescription.store();

            var entityType = entityTypeDescription.getEntityType();
            var language = entityTypeDescription.getLanguage();
            var description = entityTypeDescriptionValue.getDescription();

            entityTypeDescription = entityTypeDescriptionFactory.create(entityType, language,
                    description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityType.getPrimaryKey(), EventTypes.MODIFY, entityTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityTypeDescription(EntityTypeDescription entityTypeDescription, BasePK deletedBy) {
        entityTypeDescription.setThruTime(session.getStartTime());

        sendEvent(entityTypeDescription.getEntityTypePK(), EventTypes.MODIFY, entityTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityTypeDescriptionsByEntityType(EntityType entityType, BasePK deletedBy) {
        var entityTypeDescriptions = getEntityTypeDescriptionsByEntityTypeForUpdate(entityType);

        entityTypeDescriptions.forEach((entityTypeDescription) ->
                deleteEntityTypeDescription(entityTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Type Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected SearchControl searchControl;
    
    @Inject
    protected SearchResultFactory searchResultFactory;
    
    public List<EntityTypeResultTransfer> getEntityTypeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var entityTypeResultTransfers = new ArrayList<EntityTypeResultTransfer>();
        var includeEntityType = false;

        var options = session.getOptions();
        if(options != null) {
            includeEntityType = options.contains(SearchOptions.EntityTypeResultIncludeEntityType);
        }

        try {
            try(var ps = searchResultFactory.prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_")) {

                ps.setLong(1, search.getPrimaryKey().getEntityId());

                try(var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        var entityType = entityTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, new EntityTypePK(rs.getLong(1)));
                        var entityTypeDetail = entityType.getLastDetail();

                        entityTypeResultTransfers.add(new EntityTypeResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityTypeDetail.getEntityTypeName(), includeEntityType ? getEntityTypeTransfer(userVisit, entityType) : null));
                    }
                } catch(SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            }
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTypeResultTransfers;
    }

    public List<EntityTypeObject> getEntityTypeObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var entityTypeObjects = new ArrayList<EntityTypeObject>();

        try(var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var entityType = getEntityTypeByPK(new EntityTypePK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                entityTypeObjects.add(new EntityTypeObject(entityType));
            }
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTypeObjects;
    }

}
