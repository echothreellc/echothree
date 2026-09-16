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
import com.echothree.model.control.core.common.choice.EntityAttributeGroupChoicesBean;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupResultTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.server.graphql.EntityAttributeGroupObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupFactory;
import com.echothree.model.data.core.server.value.EntityAttributeEntityAttributeGroupValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import static java.lang.Math.toIntExact;

@CommandScope
public class EntityAttributeGroupControl
        extends BaseCoreControl {

    @Inject
    protected SearchControl searchControl;

    /** Creates a new instance of EntityAttributeGroupControl */
    protected EntityAttributeGroupControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeGroupDetailFactory entityAttributeGroupDetailFactory;

    @Inject
    protected EntityAttributeGroupFactory entityAttributeGroupFactory;

    public EntityAttributeGroup createEntityAttributeGroup(String entityAttributeGroupName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        var defaultFound = defaultEntityAttributeGroup != null;

        if(defaultFound && isDefault) {
            var defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();

            defaultEntityAttributeGroupDetailValue.setIsDefault(false);
            updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityAttributeGroup = entityAttributeGroupFactory.create();
        var entityAttributeGroupDetail = entityAttributeGroupDetailFactory.create(entityAttributeGroup,
                entityAttributeGroupName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        entityAttributeGroup = entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                entityAttributeGroup.getPrimaryKey());
        entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
        entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);
        entityAttributeGroup.store();

        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return entityAttributeGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttributeGroup */
    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributeGroupPK(entityInstance.getEntityUniqueId());

        return entityAttributeGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityAttributeGroups() {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM entityattributegroups, entityattributegroupdetails
                WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid
                """);
    }

    public long countEntityAttributeGroupsByEntityType(EntityType entityType) {
        var count = session.queryForLong("""
                                SELECT COUNT(*)
                                FROM (
                                    SELECT COUNT(*)
                                    FROM entityattributegroups
                                    JOIN entityattributegroupdetails ON enagp_lastdetailid = enagpdt_entityattributegroupdetailid
                                    JOIN entityattributeentityattributegroups ON enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_thrutime = ?
                                    JOIN entityattributes ON enaenagp_ena_entityattributeid = ena_entityattributeid
                                    JOIN entityattributedetails ON ena_lastdetailid = enadt_entityattributedetailid
                                    WHERE enadt_ent_entitytypeid = ?
                                    GROUP BY enagp_entityattributegroupid
                                ) AS entityattributegroups
                                """, Session.MAX_TIME, entityType);

        return count == null ? 0L : count;
    }

    private List<EntityAttributeGroup> getEntityAttributeGroups(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = """
                    SELECT _ALL_
                    FROM entityattributegroups, entityattributegroupdetails
                    WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid
                    ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname
                    _LIMIT_
                    """;
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = """
                    SELECT _ALL_
                    FROM entityattributegroups, entityattributegroupdetails
                    WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid
                    FOR UPDATE
                    """;
        }

        var ps = entityAttributeGroupFactory.prepareStatement(query);

        return entityAttributeGroupFactory.getEntitiesFromQuery(entityPermission, ps);
    }

    public List<EntityAttributeGroup> getEntityAttributeGroups() {
        return getEntityAttributeGroups(EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeGroup> getEntityAttributeGroupsForUpdate() {
        return getEntityAttributeGroups(EntityPermission.READ_WRITE);
    }

    private List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<EntityAttributeGroup> entityAttributeGroups;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails
                        WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid
                        AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ?
                        AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ?
                        GROUP BY enagp_entityattributegroupid
                        ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails
                        WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid
                        AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ?
                        AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ?
                        GROUP BY enagp_entityattributegroupid
                        FOR UPDATE
                        """;
            }

            var ps = entityAttributeGroupFactory.prepareStatement(query);

            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());

            entityAttributeGroups = entityAttributeGroupFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeGroups;
    }

    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    private EntityAttributeGroup getDefaultEntityAttributeGroup(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = """
                    SELECT _ALL_
                    FROM entityattributegroups, entityattributegroupdetails
                    WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1
                    """;
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = """
                    SELECT _ALL_
                    FROM entityattributegroups, entityattributegroupdetails
                    WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1
                    FOR UPDATE
                    """;
        }

        var ps = entityAttributeGroupFactory.prepareStatement(query);

        return entityAttributeGroupFactory.getEntityFromQuery(entityPermission, ps);
    }

    public EntityAttributeGroup getDefaultEntityAttributeGroup() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getDefaultEntityAttributeGroupForUpdate() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroupDetailValue getDefaultEntityAttributeGroupDetailValueForUpdate() {
        return getDefaultEntityAttributeGroupForUpdate().getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName, EntityPermission entityPermission) {
        EntityAttributeGroup entityAttributeGroup;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroups, entityattributegroupdetails
                        WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroups, entityattributegroupdetails
                        WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ?
                        FOR UPDATE
                        """;
            }

            var ps = entityAttributeGroupFactory.prepareStatement(query);

            ps.setString(1, entityAttributeGroupName);

            entityAttributeGroup = entityAttributeGroupFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return entityAttributeGroup == null? null: entityAttributeGroup.getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }

    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupDetailValueForUpdate(getEntityAttributeGroupByNameForUpdate(entityAttributeGroupName));
    }

    public EntityAttributeGroupChoicesBean getEntityAttributeGroupChoices(String defaultEntityAttributeGroupChoice, Language language,
            boolean allowNullChoice) {
        var entityAttributeGroups = getEntityAttributeGroups();
        var size = entityAttributeGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultEntityAttributeGroupChoice == null) {
                defaultValue = "";
            }
        }

        for(var entityAttributeGroup : entityAttributeGroups) {
            var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();
            var label = getBestEntityAttributeGroupDescription(entityAttributeGroup, language);
            var value = entityAttributeGroupDetail.getEntityAttributeGroupName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultEntityAttributeGroupChoice != null && defaultEntityAttributeGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityAttributeGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new EntityAttributeGroupChoicesBean(labels, values, defaultValue);
    }

    public EntityAttributeGroupTransfer getEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return entityAttributeGroupTransferCache.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, entityInstance);
    }

    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, Collection<EntityAttributeGroup> entityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeGroupTransfer> entityAttributeGroupTransfers = new ArrayList<>(entityAttributeGroups.size());

        entityAttributeGroups.forEach((entityAttributeGroup) ->
                entityAttributeGroupTransfers.add(entityAttributeGroupTransferCache.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, entityInstance))
        );

        return entityAttributeGroupTransfers;
    }

    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroups(), entityInstance);
    }

    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroupsByEntityType(entityType), entityInstance);
    }

    private void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityAttributeGroupDetailValue.hasBeenModified()) {
            var entityAttributeGroup = entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDetailValue.getEntityAttributeGroupPK());
            var entityAttributeGroupDetail = entityAttributeGroup.getActiveDetailForUpdate();

            entityAttributeGroupDetail.setThruTime(session.getStartTime());
            entityAttributeGroupDetail.store();

            var entityAttributeGroupPK = entityAttributeGroupDetail.getEntityAttributeGroupPK();
            var entityAttributeGroupName = entityAttributeGroupDetailValue.getEntityAttributeGroupName();
            var isDefault = entityAttributeGroupDetailValue.getIsDefault();
            var sortOrder = entityAttributeGroupDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
                var defaultFound = defaultEntityAttributeGroup != null && !defaultEntityAttributeGroup.equals(entityAttributeGroup);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();

                    defaultEntityAttributeGroupDetailValue.setIsDefault(false);
                    updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            entityAttributeGroupDetail = entityAttributeGroupDetailFactory.create(entityAttributeGroupPK, entityAttributeGroupName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
            entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);

            sendEvent(entityAttributeGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, BasePK updatedBy) {
        updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, true, updatedBy);
    }

    public EntityAttributeGroup getEntityAttributeGroupByPK(EntityAttributeGroupPK pk) {
        return entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public void deleteEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, deletedBy);
        deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, deletedBy);

        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetailForUpdate();
        entityAttributeGroupDetail.setThruTime(session.getStartTime());
        entityAttributeGroup.setActiveDetail(null);
        entityAttributeGroup.store();

        // Check for default, and pick one if necessary
        var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        if(defaultEntityAttributeGroup == null) {
            var entityAttributeGroups = getEntityAttributeGroupsForUpdate();

            if(!entityAttributeGroups.isEmpty()) {
                var iter = entityAttributeGroups.iterator();
                if(iter.hasNext()) {
                    defaultEntityAttributeGroup = iter.next();
                }
                var entityAttributeGroupDetailValue = Objects.requireNonNull(defaultEntityAttributeGroup).getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();

                entityAttributeGroupDetailValue.setIsDefault(true);
                updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, false, deletedBy);
            }
        }

        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeGroupDescriptionFactory entityAttributeGroupDescriptionFactory;

    public EntityAttributeGroupDescription createEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, String description,
            BasePK createdBy) {
        var entityAttributeGroupDescription = entityAttributeGroupDescriptionFactory.create(entityAttributeGroup,
                language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeGroupDescription;
    }

    private EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, EntityPermission entityPermission) {
        EntityAttributeGroupDescription entityAttributeGroupDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroupdescriptions
                        WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroupdescriptions
                        WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = entityAttributeGroupDescriptionFactory.prepareStatement(query);

            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityAttributeGroupDescription = entityAttributeGroupDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeGroupDescription;
    }

    public EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroupDescription getEntityAttributeGroupDescriptionForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValue(EntityAttributeGroupDescription entityAttributeGroupDescription) {
        return entityAttributeGroupDescription == null? null: entityAttributeGroupDescription.getEntityAttributeGroupDescriptionValue().clone();
    }

    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValueForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescriptionValue(getEntityAttributeGroupDescriptionForUpdate(entityAttributeGroup, language));
    }

    private List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        List<EntityAttributeGroupDescription> entityAttributeGroupDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroupdescriptions, languages
                        WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ?
                        AND enagpd_lang_languageid = lang_languageid
                        ORDER BY lang_sortorder, lang_languageisoname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributegroupdescriptions
                        WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = entityAttributeGroupDescriptionFactory.prepareStatement(query);

            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityAttributeGroupDescriptions = entityAttributeGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeGroupDescriptions;
    }

    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }

    public String getBestEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        String description;
        var entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, language);

        if(entityAttributeGroupDescription == null && !language.getIsDefault()) {
            entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, partyControl.getDefaultLanguage());
        }

        if(entityAttributeGroupDescription == null) {
            description = entityAttributeGroup.getLastDetail().getEntityAttributeGroupName();
        } else {
            description = entityAttributeGroupDescription.getDescription();
        }

        return description;
    }

    public EntityAttributeGroupDescriptionTransfer getEntityAttributeGroupDescriptionTransfer(UserVisit userVisit, EntityAttributeGroupDescription entityAttributeGroupDescription, EntityInstance entityInstance) {
        return entityAttributeGroupDescriptionTransferCache.getEntityAttributeGroupDescriptionTransfer(userVisit, entityAttributeGroupDescription, entityInstance);
    }

    public List<EntityAttributeGroupDescriptionTransfer> getEntityAttributeGroupDescriptionTransfers(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        var entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup);
        List<EntityAttributeGroupDescriptionTransfer> entityAttributeGroupDescriptionTransfers = new ArrayList<>(entityAttributeGroupDescriptions.size());

        entityAttributeGroupDescriptions.forEach((entityAttributeGroupDescription) ->
                entityAttributeGroupDescriptionTransfers.add(entityAttributeGroupDescriptionTransferCache.getEntityAttributeGroupDescriptionTransfer(userVisit, entityAttributeGroupDescription, entityInstance))
        );

        return entityAttributeGroupDescriptionTransfers;
    }

    public void updateEntityAttributeGroupDescriptionFromValue(EntityAttributeGroupDescriptionValue entityAttributeGroupDescriptionValue, BasePK updatedBy) {
        if(entityAttributeGroupDescriptionValue.hasBeenModified()) {
            var entityAttributeGroupDescription = entityAttributeGroupDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDescriptionValue.getPrimaryKey());

            entityAttributeGroupDescription.setThruTime(session.getStartTime());
            entityAttributeGroupDescription.store();

            var entityAttributeGroup = entityAttributeGroupDescription.getEntityAttributeGroup();
            var language = entityAttributeGroupDescription.getLanguage();
            var description = entityAttributeGroupDescriptionValue.getDescription();

            entityAttributeGroupDescription = entityAttributeGroupDescriptionFactory.create(entityAttributeGroup, language,
                    description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeGroupDescription(EntityAttributeGroupDescription entityAttributeGroupDescription, BasePK deletedBy) {
        entityAttributeGroupDescription.setThruTime(session.getStartTime());

        sendEvent(entityAttributeGroupDescription.getEntityAttributeGroupPK(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        var entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(entityAttributeGroup);

        entityAttributeGroupDescriptions.forEach((entityAttributeGroupDescription) ->
                deleteEntityAttributeGroupDescription(entityAttributeGroupDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeEntityAttributeGroupFactory entityAttributeEntityAttributeGroupFactory;

    public EntityAttributeEntityAttributeGroup createEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, Integer sortOrder, BasePK createdBy) {
        var entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroupFactory.create(
                entityAttribute, entityAttributeGroup, sortOrder, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeEntityAttributeGroup;
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM entityattributeentityattributegroups
                WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ?
                """,
                entityAttribute, Session.MAX_TIME);
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM entityattributeentityattributegroups
                WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?
                """,
                entityAttributeGroup, Session.MAX_TIME);
    }

    private EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributeentityattributegroups
                        WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributeentityattributegroups
                        WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = entityAttributeEntityAttributeGroupFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroupFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeEntityAttributeGroup;
    }

    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_ONLY);
    }

    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroupForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValue(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        return entityAttributeEntityAttributeGroup == null? null: entityAttributeEntityAttributeGroup.getEntityAttributeEntityAttributeGroupValue().clone();
    }

    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValueForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupValue(getEntityAttributeEntityAttributeGroupForUpdate(entityAttribute, entityAttributeGroup));
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails
                        WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ?
                        AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid
                        ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributeentityattributegroups
                        WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = entityAttributeEntityAttributeGroupFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityAttributeEntityAttributeGroups = entityAttributeEntityAttributeGroupFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeEntityAttributeGroups;
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup,
            EntityPermission entityPermission) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails
                        WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?
                        AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid
                        ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM entityattributeentityattributegroups
                        WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = entityAttributeEntityAttributeGroupFactory.prepareStatement(query);

            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityAttributeEntityAttributeGroups = entityAttributeEntityAttributeGroupFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeEntityAttributeGroups;
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityAttributeGroupTransfer getEntityAttributeEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, EntityInstance entityInstance) {
        return entityAttributeEntityAttributeGroupTransferCache.getEntityAttributeEntityAttributeGroupTransfer(userVisit, entityAttributeEntityAttributeGroup, entityInstance);
    }

    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfers(UserVisit userVisit,
            Collection<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeEntityAttributeGroupTransfer> entityAttributeEntityAttributeGroupTransfers = new ArrayList<>(entityAttributeEntityAttributeGroups.size());

        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                entityAttributeEntityAttributeGroupTransfers.add(entityAttributeEntityAttributeGroupTransferCache.getEntityAttributeEntityAttributeGroupTransfer(userVisit, entityAttributeEntityAttributeGroup, entityInstance))
        );

        return entityAttributeEntityAttributeGroupTransfers;
    }

    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute), entityInstance);
    }

    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttributeGroup(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup), entityInstance);
    }

    public void updateEntityAttributeEntityAttributeGroupFromValue(EntityAttributeEntityAttributeGroupValue entityAttributeEntityAttributeGroupValue, BasePK updatedBy) {
        if(entityAttributeEntityAttributeGroupValue.hasBeenModified()) {
            var entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeEntityAttributeGroupValue.getPrimaryKey());

            entityAttributeEntityAttributeGroup.setThruTime(session.getStartTime());
            entityAttributeEntityAttributeGroup.store();

            var entityAttribute = entityAttributeEntityAttributeGroup.getEntityAttribute();
            var entityAttributeGroup = entityAttributeEntityAttributeGroup.getEntityAttributeGroup();
            var sortOrder = entityAttributeEntityAttributeGroupValue.getSortOrder();

            entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroupFactory.create(entityAttribute, entityAttributeGroup,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeEntityAttributeGroup(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, BasePK deletedBy) {
        entityAttributeEntityAttributeGroup.setThruTime(session.getStartTime());

        sendEvent(entityAttributeEntityAttributeGroup.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(entityAttribute);

        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy)
        );
    }

    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        var entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(entityAttributeGroup);

        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Group Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected CachedExecutedSearchResultFactory cachedExecutedSearchResultFactory;

    @Inject
    protected SearchResultFactory searchResultFactory;

    public List<EntityAttributeGroupResultTransfer> getEntityAttributeGroupResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        List<EntityAttributeGroupResultTransfer> entityAttributeGroupResultTransfers;
        var includeEntityAttributeGroup = false;

        var options = session.getOptions();
        if(options != null) {
            includeEntityAttributeGroup = options.contains(SearchOptions.EntityAttributeGroupResultIncludeEntityAttributeGroup);
        }

        if(cachedSearch == null) {
            entityAttributeGroupResultTransfers = new ArrayList<>(toIntExact(searchControl.countSearchResults(search)));

            try {
                var ps = searchResultFactory.prepareStatement(
                        """
                        SELECT eni_entityuniqueid
                        FROM searchresults, entityinstances
                        WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid
                        ORDER BY srchr_sortorder, srchr_eni_entityinstanceid
                        _LIMIT_
                        """);

                ps.setLong(1, search.getPrimaryKey().getEntityId());

                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        var entityAttributeGroup = entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_ONLY, new EntityAttributeGroupPK(rs.getLong(1)));
                        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

                        entityAttributeGroupResultTransfers.add(new EntityAttributeGroupResultTransfer(entityAttributeGroupDetail.getEntityAttributeGroupName(),
                                includeEntityAttributeGroup ? getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            entityAttributeGroupResultTransfers = new ArrayList<>(toIntExact(searchControl.countCachedExecutedSearchResults(cachedExecutedSearch)));

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            try {
                var ps = cachedExecutedSearchResultFactory.prepareStatement(
                        """
                        SELECT eni_entityuniqueid
                        FROM cachedexecutedsearchresults, entityinstances
                        WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? AND cxsrchr_eni_entityinstanceid = eni_entityinstanceid
                        ORDER BY cxsrchr_sortorder, cxsrchr_eni_entityinstanceid
                        _LIMIT_
                        """);

                ps.setLong(1, cachedExecutedSearch.getPrimaryKey().getEntityId());

                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        var entityAttributeGroup = entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_ONLY, new EntityAttributeGroupPK(rs.getLong(1)));
                        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

                        entityAttributeGroupResultTransfers.add(new EntityAttributeGroupResultTransfer(entityAttributeGroupDetail.getEntityAttributeGroupName(),
                                includeEntityAttributeGroup ? getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }

        return entityAttributeGroupResultTransfers;
    }

    public List<EntityAttributeGroupObject> getEntityAttributeGroupObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var entityAttributeGroupObjects = new ArrayList<EntityAttributeGroupObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var entityAttributeGroup = getEntityAttributeGroupByPK(new EntityAttributeGroupPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                entityAttributeGroupObjects.add(new EntityAttributeGroupObject(entityAttributeGroup, null));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeGroupObjects;
    }

}
