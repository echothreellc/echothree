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
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.CoreDebugFlags;
import com.echothree.model.control.core.server.database.EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UuidUtils;
import com.google.common.base.Splitter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class EntityInstanceControl
        extends BaseCoreControl {

    /** Creates a new instance of EntityInstanceControl */
    protected EntityInstanceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Instances
    // --------------------------------------------------------------------------------

    public EntityInstance createEntityInstance(EntityType entityType, Long entityUniqueId) {
        return EntityInstanceFactory.getInstance().create(entityType, entityUniqueId, null);
    }

    public EntityInstance createEntityAttributeDefaults(EntityInstance entityInstance, BasePK createdBy) {
        var coreControl = Session.getModelController(CoreControl.class);
        var entityAttributes = coreControl.getEntityAttributesByEntityType(entityInstance.getEntityType());

        entityAttributes.forEach(entityAttribute -> {
            var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            var entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);

            switch(entityAttributeType) {
                case BOOLEAN -> {
                    var entityBooleanDefault = coreControl.getEntityBooleanDefault(entityAttribute);

                    if(entityBooleanDefault != null) {
                        coreControl.createEntityBooleanAttribute(entityAttribute, entityInstance, entityBooleanDefault.getBooleanAttribute(), createdBy);
                    }
                }
                case DATE -> {
                    var entityDateDefault = coreControl.getEntityDateDefault(entityAttribute);

                    if(entityDateDefault != null) {
                        coreControl.createEntityDateAttribute(entityAttribute, entityInstance, entityDateDefault.getDateAttribute(), createdBy);
                    }
                }
                case GEOPOINT -> {
                    var entityGeoPointDefault = coreControl.getEntityGeoPointDefault(entityAttribute);

                    if(entityGeoPointDefault != null) {
                        coreControl.createEntityGeoPointAttribute(entityAttribute, entityInstance, entityGeoPointDefault.getLatitude(),
                                entityGeoPointDefault.getLongitude(), entityGeoPointDefault.getElevation(),
                                entityGeoPointDefault.getAltitude(), createdBy);
                    }
                }
                case INTEGER -> {
                    var entityIntegerDefault = coreControl.getEntityIntegerDefault(entityAttribute);

                    if(entityIntegerDefault != null) {
                        coreControl.createEntityIntegerAttribute(entityAttribute, entityInstance, entityIntegerDefault.getIntegerAttribute(), createdBy);
                    }
                }
                case LISTITEM -> {
                    var entityListItemDefault = coreControl.getEntityListItemDefault(entityAttribute);

                    if(entityListItemDefault != null) {
                        coreControl.createEntityListItemAttribute(entityAttribute, entityInstance, entityListItemDefault.getEntityListItem(), createdBy);
                    }
                }
                case LONG -> {
                    var entityLongDefault = coreControl.getEntityLongDefault(entityAttribute);

                    if(entityLongDefault != null) {
                        coreControl.createEntityLongAttribute(entityAttribute, entityInstance, entityLongDefault.getLongAttribute(), createdBy);
                    }
                }
                case MULTIPLELISTITEM ->
                        coreControl.getEntityMultipleListItemDefaults(entityAttribute).forEach((entityMultipleListItemDefault) ->
                                coreControl.createEntityListItemAttribute(entityAttribute, entityInstance, entityMultipleListItemDefault.getEntityListItem(), createdBy));
                case STRING -> {
                    var entityStringDefaults = coreControl.getEntityStringDefaultsByEntityAttribute(entityAttribute);

                    entityStringDefaults.forEach(entityStringDefault ->
                            coreControl.createEntityStringAttribute(entityAttribute, entityInstance, entityStringDefault.getLanguage(),
                                    entityStringDefault.getStringAttribute(), createdBy));
                }
                case TIME -> {
                    var entityTimeDefault = coreControl.getEntityTimeDefault(entityAttribute);

                    if(entityTimeDefault != null) {
                        coreControl.createEntityTimeAttribute(entityAttribute, entityInstance, entityTimeDefault.getTimeAttribute(), createdBy);
                    }
                }
                default -> {}
            }
        });

        return entityInstance;
    }

    public boolean verifyEntityInstance(final EntityInstance entityInstance, final String componentVendorName, final String entityTypeName) {
        var result = true;
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

        if(entityTypeDetail.getEntityTypeName().equals(entityTypeName)) {
            if(!entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName().equals(componentVendorName)) {
                result = false;
            }
        } else {
            result = false;
        }

        return result;
    }

    public long countEntityInstances() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityinstances");
    }

    public long countEntityInstancesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityinstances " +
                        "WHERE eni_ent_entitytypeid = ?",
                entityType);
    }

    public List<EntityInstance> getEntityInstancesByEntityType(EntityType entityType) {
        List<EntityInstance> entityInstances;

        try {
            var ps = EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM entityinstances " +
                            "WHERE eni_ent_entitytypeid = ? " +
                            "ORDER BY eni_entityuniqueid " +
                            "_LIMIT_");

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());

            entityInstances = EntityInstanceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityInstances;
    }

    private EntityInstance getEntityInstance(EntityType entityType, Long entityUniqueId, EntityPermission entityPermission) {
        EntityInstance entityInstance;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ent_entitytypeid = ? AND eni_entityuniqueid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ent_entitytypeid = ? AND eni_entityuniqueid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityInstanceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityUniqueId);

            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstance(EntityType entityType, Long entityUniqueId) {
        return getEntityInstance(entityType, entityUniqueId, EntityPermission.READ_ONLY);
    }

    public EntityInstance getEntityInstanceForUpdate(EntityType entityType, Long entityUniqueId) {
        return getEntityInstance(entityType, entityUniqueId, EntityPermission.READ_WRITE);
    }

    private EntityInstance getEntityInstanceByUuid(String uuid, EntityPermission entityPermission) {
        EntityInstance entityInstance;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_uuid = UUID_TO_BIN(?)";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_uuid = UUID_TO_BIN(?) " +
                        "FOR UPDATE";
            }

            var ps = EntityInstanceFactory.getInstance().prepareStatement(query);

            ps.setString(1, uuid);

            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstanceByUuid(String uuid) {
        return getEntityInstanceByUuid(uuid, EntityPermission.READ_ONLY);
    }

    public EntityInstance getEntityInstanceByUuidForUpdate(String uuid) {
        return getEntityInstanceByUuid(uuid, EntityPermission.READ_WRITE);
    }

    public EntityInstance ensureUuidForEntityInstance(EntityInstance entityInstance, boolean forceRegeneration) {
        var uuid = entityInstance.getUuid();

        if(uuid == null || forceRegeneration) {
            // Convert to READ_WRITE if necessary...
            if(entityInstance.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
                entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
            }

            // Keep generating UUIDs until a unique one is found...
            EntityInstance duplicateEntityInstance;
            do {
                uuid = UuidUtils.getInstance().generateUuid(entityInstance);
                duplicateEntityInstance = getEntityInstanceByUuid(uuid);
            } while(duplicateEntityInstance != null);

            // Store it immediately in order to decrease the odds of another thread choosing the same UUID...
            entityInstance.setUuid(uuid);
            entityInstance.store();
        }

        return entityInstance;
    }

    public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, EntityInstance entityInstance, boolean includeEntityAppearance,
            boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        return entityInstanceTransferCache.getEntityInstanceTransfer(userVisit, entityInstance, includeEntityAppearance,
                includeEntityVisit, includeNames, includeUuid);
    }

    public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, BaseEntity baseEntity, boolean includeEntityAppearance,
            boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        return getEntityInstanceTransfer(userVisit, getEntityInstanceByBasePK(baseEntity.getPrimaryKey()), includeEntityAppearance,
                includeEntityVisit, includeNames, includeUuid);
    }

    public List<EntityInstanceTransfer> getEntityInstanceTransfers(UserVisit userVisit, Collection<EntityInstance> entityInstances,
            boolean includeEntityAppearance, boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        var entityInstanceTransfers = new ArrayList<EntityInstanceTransfer>(entityInstances.size());

        entityInstances.forEach((entityInstance) ->
                entityInstanceTransfers.add(entityInstanceTransferCache.getEntityInstanceTransfer(userVisit, entityInstance,
                        includeEntityAppearance, includeEntityVisit, includeNames, includeUuid))
        );

        return entityInstanceTransfers;
    }

    public List<EntityInstanceTransfer> getEntityInstanceTransfersByEntityType(UserVisit userVisit, EntityType entityType,
            boolean includeEntityAppearance, boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        return getEntityInstanceTransfers(userVisit, getEntityInstancesByEntityType(entityType), includeEntityAppearance,
                includeEntityVisit, includeNames, includeUuid);
    }

    /** Gets an EntityInstance for BasePK, creating it if necessary. Overrides function from BaseModelControl.
     * Some errors from this function are normal during the initial load of data into the database.
     */
    private EntityInstance getEntityInstanceByBasePK(BasePK pk, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;

        if(CoreDebugFlags.LogEntityInstanceResolution) {
            getLog().info(">>> getEntityInstanceByBasePK(pk=" + pk + ")");
        }

        if(pk != null) {
            var componentControl = Session.getModelController(ComponentControl.class);
            var componentVendorName = pk.getComponentVendorName();
            var componentVendor = componentControl.getComponentVendorByNameFromCache(componentVendorName);

            if(CoreDebugFlags.LogEntityInstanceResolution) {
                getLog().info("--- componentVendor = " + componentVendor);
            }

            if(componentVendor != null) {
                var entityTypeControl = Session.getModelController(EntityTypeControl.class);
                var entityTypeName = pk.getEntityTypeName();
                var entityType = entityTypeControl.getEntityTypeByNameFromCache(componentVendor, entityTypeName);

                if(CoreDebugFlags.LogEntityInstanceResolution) {
                    getLog().info("--- entityType = " + entityType);
                }

                if(entityType != null) {
                    var entityUniqueId = pk.getEntityId();

                    if(CoreDebugFlags.LogEntityInstanceResolution) {
                        getLog().info("--- entityUniqueId = " + entityUniqueId);
                    }

                    entityInstance = getEntityInstance(entityType, entityUniqueId, entityPermission);
                    if(entityInstance == null) {
                        entityInstance = createEntityInstance(entityType, entityUniqueId);

                        if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                            // Convert to R/W
                            entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(
                                    EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
                        }
                    }

                    if(CoreDebugFlags.LogEntityInstanceResolution) {
                        getLog().info("--- entityInstance = " + entityInstance);
                    }
                } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                    getLog().error("getEntityInstanceByBasePK: unknown entityTypeName \"" + componentVendorName + "." + entityTypeName + "\"");
                }
            } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                getLog().error("getEntityInstanceByBasePK: unknown componentVendorName \"" + componentVendorName + "\"");
            }
        } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
            getLog().error("getEntityInstanceByBasePK: PK was null");
        }

        if(CoreDebugFlags.LogEntityInstanceResolution) {
            getLog().info("<<< entityInstance=" + entityInstance);
        }

        return entityInstance;
    }

    @Override
    public EntityInstance getEntityInstanceByBasePK(BasePK pk) {
        return getEntityInstanceByBasePK(pk, EntityPermission.READ_ONLY);
    }

    public EntityInstance getEntityInstanceByBasePKForUpdate(BasePK pk) {
        return getEntityInstanceByBasePK(pk, EntityPermission.READ_WRITE);
    }

    /** This function handles data passed back from a client. Because of this, missing entity instances
     * are not automatically created if they do not exist. Do not trust what the user is telling us.
     */
    private EntityInstance getEntityInstanceByEntityRef(String entityRef, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;

        if(entityRef != null) {
            var entityRefParts = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(entityRef).toArray(new String[0]);

            if(entityRefParts.length == 3) {
                var componentControl = Session.getModelController(ComponentControl.class);
                var componentVendorName = entityRefParts[0];
                var componentVendor = componentControl.getComponentVendorByNameFromCache(componentVendorName);

                if(componentVendor != null) {
                    var entityTypeControl = Session.getModelController(EntityTypeControl.class);
                    var entityTypeName = entityRefParts[1];
                    var entityType = entityTypeControl.getEntityTypeByNameFromCache(componentVendor, entityTypeName);

                    if(entityType != null) {
                        var entityUniqueId = Long.valueOf(entityRefParts[2]);

                        entityInstance = getEntityInstance(entityType, entityUniqueId, entityPermission);

                        if(CoreDebugFlags.LogUnresolvedEntityInstances && entityInstance == null) {
                            getLog().error("getEntityInstanceByEntityRef: unknown entityUniqueId \"" + componentVendorName + "." + entityTypeName + "." + entityUniqueId + "\"");
                        }
                    } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                        getLog().error("getEntityInstanceByEntityRef: unknown entityTypeName \"" + componentVendorName + "." + entityTypeName + "\"");
                    }
                } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                    getLog().error("getEntityInstanceByEntityRef: unknown componentVendorName \"" + componentVendorName + "\"");
                }
            } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                getLog().error("getEntityInstanceByEntityRef: entityRef not valid");
            }
        } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
            getLog().error("getEntityInstanceByEntityRef: entityRef was null");
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstanceByEntityRef(String entityRef) {
        return getEntityInstanceByEntityRef(entityRef, EntityPermission.READ_ONLY);
    }

    public EntityInstance getEntityInstanceByEntityRefForUpdate(String entityRef) {
        return getEntityInstanceByEntityRef(entityRef, EntityPermission.READ_WRITE);
    }

    public EntityInstance getEntityInstanceByPK(EntityInstancePK entityInstancePK) {
        return EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, entityInstancePK);
    }

    /** This function is a little odd. It doesn't actually delete the Entity Instance, rather, it cleans up all the
     * entities scattered through several components that depend on them.
     */
    public void deleteEntityInstanceDependencies(EntityInstance entityInstance, BasePK deletedBy) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var chainControl = Session.getModelController(ChainControl.class);
        var coreControl = Session.getModelController(CoreControl.class);
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var searchControl = Session.getModelController(SearchControl.class);
        var securityControl = Session.getModelController(SecurityControl.class);

        Session.getModelController(AccountingControl.class).deleteTransactionEntityRolesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(AssociateControl.class).deleteAssociateReferralsByTargetEntityInstance(entityInstance, deletedBy);
        Session.getModelController(BatchControl.class).deleteBatchEntitiesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(CommentControl.class).deleteCommentsByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(MessageControl.class).deleteEntityMessagesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(RatingControl.class).deleteRatingsByEntityInstance(entityInstance, deletedBy);
        searchControl.removeSearchResultsByEntityInstance(entityInstance);
        searchControl.removeCachedExecutedSearchResultsByEntityInstance(entityInstance);
        searchControl.deleteSearchResultActionsByEntityInstance(entityInstance, deletedBy);
        securityControl.deletePartyEntitySecurityRolesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(TagControl.class).deleteEntityTagsByEntityInstance(entityInstance, deletedBy);
        workflowControl.deleteWorkflowEntityStatusesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(WorkEffortControl.class).deleteWorkEffortsByOwningEntityInstance(entityInstance, deletedBy);

        // If an EntityInstance is in a role for a ChainInstance, then that ChainInstance should be deleted. Because an individual
        // EntityInstance may be in more than one role, the list of ChainInstances needs to be deduplicated.
        Set<ChainInstance> chainInstances = new HashSet<>();
        chainControl.getChainInstanceEntityRolesByEntityInstanceForUpdate(entityInstance).forEach((chainInstanceEntityRole) ->
                chainInstances.add(chainInstanceEntityRole.getChainInstanceForUpdate())
        );
        chainControl.deleteChainInstances(chainInstances, deletedBy);

        entityAliasControl.deleteEntityAliasesByEntityInstance(entityInstance, deletedBy);
        coreControl.deleteEntityAttributesByEntityInstance(entityInstance, deletedBy);
        appearanceControl.deleteEntityAppearancesByEntityInstance(entityInstance, deletedBy);
    }

    public void deleteEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        // sendEvent(...) handles calling back to deleteEntityInstanceDependencies(...)
        sendEvent(entityInstance, EventTypes.DELETE, (EntityInstance)null, null, deletedBy);
    }

    public void deleteEntityInstancesByEntityTypeWithNullDeletedTime(final EntityType entityType, final BasePK deletedBy) {
        for(var entityInstanceResult : new EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery().execute(entityType)) {
            deleteEntityInstance(EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityInstanceResult.getEntityInstancePK()), deletedBy);
        }
    }

    public void removeEntityInstance(EntityInstance entityInstance) {
        entityInstance.remove();
    }

    public void removeEntityInstanceByBasePK(BasePK pk) {
        removeEntityInstance(getEntityInstanceByBasePKForUpdate(pk));
    }

    public void removeEntityInstanceByEntityRef(String entityRef) {
        removeEntityInstance(getEntityInstanceByEntityRefForUpdate(entityRef));
    }

}
