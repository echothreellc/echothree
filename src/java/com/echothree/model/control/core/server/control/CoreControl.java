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

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.EntityAttributeGroupChoicesBean;
import com.echothree.model.control.core.common.choice.EntityAttributeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityIntegerRangeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityListItemChoicesBean;
import com.echothree.model.control.core.common.choice.EntityLongRangeChoicesBean;
import com.echothree.model.control.core.common.transfer.EntityAttributeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBooleanAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBooleanDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityClobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityCollectionAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityDateAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityDateDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityEntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityGeoPointAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityGeoPointDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeTransfer;
import com.echothree.model.control.core.common.transfer.EntityMultipleListItemAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityMultipleListItemDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityNameAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityStringAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityStringDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeDefaultTransfer;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityIntegerRangePK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityLongRangePK;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeBlob;
import com.echothree.model.data.core.server.entity.EntityAttributeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityType;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeListItem;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeNumeric;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityAttributeTypeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeWorkflow;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanDefault;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityCollectionAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityDateDefault;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointDefault;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerDefault;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDescription;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.entity.EntityListItemDescription;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityLongDefault;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.entity.EntityLongRangeDescription;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemDefault;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityStringDefault;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.factory.EntityAttributeBlobFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityTypeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeIntegerFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeListItemFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeLongFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeNumericFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeStringFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeTypeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeWorkflowFactory;
import com.echothree.model.data.core.server.factory.EntityBlobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityBooleanAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityBooleanDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityClobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityCollectionAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityDateAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityDateDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityEntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDetailFactory;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.core.server.factory.EntityLongAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityLongDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeFactory;
import com.echothree.model.data.core.server.factory.EntityMultipleListItemAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityMultipleListItemDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityNameAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityStringAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityStringDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityTimeAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityTimeDefaultFactory;
import com.echothree.model.data.core.server.value.EntityAttributeBlobValue;
import com.echothree.model.data.core.server.value.EntityAttributeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeEntityAttributeGroupValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeIntegerValue;
import com.echothree.model.data.core.server.value.EntityAttributeListItemValue;
import com.echothree.model.data.core.server.value.EntityAttributeLongValue;
import com.echothree.model.data.core.server.value.EntityAttributeNumericValue;
import com.echothree.model.data.core.server.value.EntityAttributeStringValue;
import com.echothree.model.data.core.server.value.EntityAttributeWorkflowValue;
import com.echothree.model.data.core.server.value.EntityBlobAttributeValue;
import com.echothree.model.data.core.server.value.EntityBooleanAttributeValue;
import com.echothree.model.data.core.server.value.EntityBooleanDefaultValue;
import com.echothree.model.data.core.server.value.EntityClobAttributeValue;
import com.echothree.model.data.core.server.value.EntityDateAttributeValue;
import com.echothree.model.data.core.server.value.EntityDateDefaultValue;
import com.echothree.model.data.core.server.value.EntityEntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointDefaultValue;
import com.echothree.model.data.core.server.value.EntityIntegerAttributeValue;
import com.echothree.model.data.core.server.value.EntityIntegerDefaultValue;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDefaultValue;
import com.echothree.model.data.core.server.value.EntityListItemDescriptionValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.core.server.value.EntityLongAttributeValue;
import com.echothree.model.data.core.server.value.EntityLongDefaultValue;
import com.echothree.model.data.core.server.value.EntityLongRangeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityLongRangeDetailValue;
import com.echothree.model.data.core.server.value.EntityNameAttributeValue;
import com.echothree.model.data.core.server.value.EntityStringAttributeValue;
import com.echothree.model.data.core.server.value.EntityStringDefaultValue;
import com.echothree.model.data.core.server.value.EntityTimeAttributeValue;
import com.echothree.model.data.core.server.value.EntityTimeDefaultValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
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
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class CoreControl
        extends BaseCoreControl {
    
    /** Creates a new instance of CoreControl */
    protected CoreControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Types
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeTypeFactory entityAttributeTypeFactory;
    
    public EntityAttributeType createEntityAttributeType(String entityAttributeTypeName) {
        var entityAttributeType = entityAttributeTypeFactory.create(entityAttributeTypeName);
        
        return entityAttributeType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttributeType */
    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributeTypePK(entityInstance.getEntityUniqueId());

        return entityAttributeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityAttributeTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributetypes ");
    }

    public EntityAttributeType getEntityAttributeTypeByName(String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType;
        
        try {
            var ps = entityAttributeTypeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypes " +
                    "WHERE enat_entityattributetypename = ?");
            
            ps.setString(1, entityAttributeTypeName);
            
            entityAttributeType = entityAttributeTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeType;
    }
    
    public List<EntityAttributeType> getEntityAttributeTypes() {
        var ps = entityAttributeTypeFactory.prepareStatement(
                "SELECT _ALL_ " +
                "FROM entityattributetypes " +
                "ORDER BY enat_entityattributetypename " +
                "_LIMIT_");
        
        return entityAttributeTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public EntityAttributeTypeTransfer getEntityAttributeTypeTransfer(UserVisit userVisit, EntityAttributeType entityAttributeType) {
        return entityAttributeTypeTransferCache.getEntityAttributeTypeTransfer(userVisit, entityAttributeType);
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit, Collection<EntityAttributeType> entityAttributeTypes) {
        List<EntityAttributeTypeTransfer> entityAttributeTypeTransfers = null;
        
        if(entityAttributeTypes != null) {
            entityAttributeTypeTransfers = new ArrayList<>(entityAttributeTypes.size());
            
            for(var entityAttributeType : entityAttributeTypes) {
                entityAttributeTypeTransfers.add(entityAttributeTypeTransferCache.getEntityAttributeTypeTransfer(userVisit, entityAttributeType));
            }
        }
        
        return entityAttributeTypeTransfers;
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit) {
        return getEntityAttributeTypeTransfers(userVisit, getEntityAttributeTypes());
    }
    
    public EntityAttributeTypeChoicesBean getEntityAttributeTypeChoices(String defaultEntityAttributeTypeChoice, Language language) {
        var entityAttributeTypes = getEntityAttributeTypes();
        var size = entityAttributeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var entityAttributeType : entityAttributeTypes) {
            var label = getBestEntityAttributeTypeDescription(entityAttributeType, language);
            var value = entityAttributeType.getEntityAttributeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityAttributeTypeChoice != null && defaultEntityAttributeTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new EntityAttributeTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeTypeDescriptionFactory entityAttributeTypeDescriptionFactory;
    
    public EntityAttributeTypeDescription createEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language, String description) {
        var entityAttributeTypeDescription = entityAttributeTypeDescriptionFactory.create(entityAttributeType, language, description);
        
        return entityAttributeTypeDescription;
    }
    
    public EntityAttributeTypeDescription getEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        EntityAttributeTypeDescription entityAttributeTypeDescription;
        
        try {
            var ps = entityAttributeTypeDescriptionFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypedescriptions " +
                    "WHERE enatd_enat_entityattributetypeid = ? AND enatd_lang_languageid = ?");
            
            ps.setLong(1, entityAttributeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            entityAttributeTypeDescription = entityAttributeTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeTypeDescription;
    }
    
    public String getBestEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        String description;
        var entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, language);
        
        if(entityAttributeTypeDescription == null && !language.getIsDefault()) {
            entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, partyControl.getDefaultLanguage());
        }
        
        if(entityAttributeTypeDescription == null) {
            description = entityAttributeType.getEntityAttributeTypeName();
        } else {
            description = entityAttributeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Groups
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeGroupFactory entityAttributeGroupFactory;
    
    @Inject
    protected EntityAttributeGroupDetailFactory entityAttributeGroupDetailFactory;
    
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
                "SELECT COUNT(*) " +
                "FROM entityattributegroups, entityattributegroupdetails " +
                "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid");
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
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid " +
                    "ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid " +
                    "FOR UPDATE";
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
                query = "SELECT _ALL_ "
                        + "FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ? "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "GROUP BY enagp_entityattributegroupid "
                        + "ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ? "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "GROUP BY enagp_entityattributegroupid "
                        + "FOR UPDATE";
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
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1 " +
                    "FOR UPDATE";
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
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroups, entityattributegroupdetails " +
                        "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroups, entityattributegroupdetails " +
                        "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ? " +
                        "FOR UPDATE";
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
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ? " +
                        "FOR UPDATE";
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
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions, languages " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ? " +
                        "AND enagpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ? " +
                        "FOR UPDATE";
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
    //   Entity Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeFactory entityAttributeFactory;
    
    @Inject
    protected EntityAttributeDetailFactory entityAttributeDetailFactory;
    
    public EntityAttribute createEntityAttribute(EntityType entityType, String entityAttributeName,
            EntityAttributeType entityAttributeType, Boolean trackRevisions, Integer sortOrder, BasePK createdBy) {
        var entityAttribute = entityAttributeFactory.create();
        var entityAttributeDetail = entityAttributeDetailFactory.create(entityAttribute, entityType,
                entityAttributeName, entityAttributeType, trackRevisions, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityAttribute = entityAttributeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                entityAttribute.getPrimaryKey());
        entityAttribute.setActiveDetail(entityAttributeDetail);
        entityAttribute.setLastDetail(entityAttributeDetail);
        entityAttribute.store();
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityAttribute;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttribute */
    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributePK(entityInstance.getEntityUniqueId());

        return entityAttributeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttribute getEntityAttributeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByPK(EntityAttributePK pk) {
        return entityAttributeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countEntityAttributesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributes, entityattributedetails " +
                "WHERE ena_activedetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ?",
                entityType);
    }

    public long countEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType, EntityAttributeType entityAttributeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributes, entityattributedetails " +
                "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ?",
                entityType, entityAttributeType);
    }

    public long countEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails " +
                        "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? " +
                        "AND enaenagp_ena_entityattributeid = ena_entityattributeid " +
                        "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ?",
                entityAttributeGroup, Session.MAX_TIME, entityType);
    }

    public long countEntityAttributesByEntityTypeAndWorkflow(final EntityType entityType, final Workflow workflow) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM entityattributes
                        JOIN entityattributedetails ON ena_activedetailid = enadt_entityattributedetailid
                        JOIN entityattributetypes ON enadt_enat_entityattributetypeid = enat_entityattributetypeid
                        JOIN entityattributeworkflows ON ena_entityattributeid = enawkfl_ena_entityattributeid AND enawkfl_thrutime = ?
                        WHERE enadt_ent_entitytypeid = ? AND enat_entityattributetypename = ? AND enawkfl_wkfl_workflowid = ?
                        """, Session.MAX_TIME, entityType, EntityAttributeTypes.WORKFLOW.name(), workflow);
    }

    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName, EntityPermission entityPermission) {
        EntityAttribute entityAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_entityattributename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_entityattributename = ? " +
                        "FOR UPDATE";
            }

            var ps = entityAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, entityAttributeName);
            
            entityAttribute = entityAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeByName(entityType, entityAttributeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByNameForUpdate(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeByName(entityType, entityAttributeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeDetailValue getEntityAttributeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return entityAttribute == null? null: entityAttribute.getLastDetailForUpdate().getEntityAttributeDetailValue().clone();
    }
    
    public EntityAttributeDetailValue getEntityAttributeDetailValueByNameForUpdate(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeDetailValueForUpdate(getEntityAttributeByNameForUpdate(entityType, entityAttributeName));
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? " +
                        "ORDER BY enadt_sortorder, enadt_entityattributename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = entityAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = entityAttributeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityType(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ? " +
                        "ORDER BY enadt_sortorder, enadt_entityattributename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = entityAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeType.getPrimaryKey().getEntityId());
            
            entityAttributes = entityAttributeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeTypeForUpdate(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType,
            EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_ena_entityattributeid = ena_entityattributeid "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "ORDER BY enaenagp_sortorder, enadt_sortorder, enadt_entityattributename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_ena_entityattributeid = ena_entityattributeid "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "FOR UPDATE";
            }

            var ps = entityAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = entityAttributeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityTypeForUpdate(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeTransfer getEntityAttributeTransfer(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityAttributeTransferCache.getEntityAttributeTransfer(userVisit, entityAttribute, entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfers(UserVisit userVisit, Collection<EntityAttribute> entityAttributes, EntityInstance entityInstance) {
        List<EntityAttributeTransfer> entityAttributeTransfers = new ArrayList<>(entityAttributes.size());
        
        entityAttributes.forEach((entityAttribute) ->
                entityAttributeTransfers.add(entityAttributeTransferCache.getEntityAttributeTransfer(userVisit, entityAttribute, entityInstance))
        );
        
        return entityAttributeTransfers;
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityType(entityType), entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityTypeAndEntityAttributeType(UserVisit userVisit, EntityType entityType,
            EntityAttributeType entityAttributeType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType), entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityAttributeGroupAndEntityType(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType),
                entityInstance);
    }
    
    public void updateEntityAttributeFromValue(final EntityAttributeDetailValue entityAttributeDetailValue, final BasePK updatedBy) {
        if(entityAttributeDetailValue.hasBeenModified()) {
            final var entityAttribute = entityAttributeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDetailValue.getEntityAttributePK());
            var entityAttributeDetail = entityAttribute.getActiveDetailForUpdate();
            
            entityAttributeDetail.setThruTime(session.getStartTime());
            entityAttributeDetail.store();

            final var entityAttributePK = entityAttributeDetail.getEntityAttributePK(); // Not updated
            final var entityTypePK = entityAttributeDetail.getEntityTypePK(); // Not updated
            final var entityAttributeName = entityAttributeDetailValue.getEntityAttributeName();
            final var entityAttributeTypePK = entityAttributeDetail.getEntityAttributeTypePK(); // Not updated
            final var trackRevisions = entityAttributeDetailValue.getTrackRevisions();
            final var sortOrder = entityAttributeDetailValue.getSortOrder();
            
            entityAttributeDetail = entityAttributeDetailFactory.create(entityAttributePK, entityTypePK,
                    entityAttributeName, entityAttributeTypePK, trackRevisions, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            entityAttribute.setActiveDetail(entityAttributeDetail);
            entityAttribute.setLastDetail(entityAttributeDetail);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeDetail = entityAttribute.getLastDetailForUpdate();
        var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();
        var entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);

        switch(entityAttributeType) {
            case BOOLEAN -> {
                deleteEntityBooleanDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityBooleanAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case NAME -> deleteEntityNameAttributesByEntityAttribute(entityAttribute, deletedBy);
            case INTEGER, LONG -> {
                deleteEntityAttributeNumericByEntityAttribute(entityAttribute, deletedBy);

                switch(entityAttributeType) {
                    case INTEGER:
                        deleteEntityAttributeIntegerByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerRangesByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerDefaultByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    case LONG:
                        deleteEntityAttributeLongByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongRangesByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongDefaultByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    default:
                        break;
                }
            }
            case STRING -> {
                deleteEntityAttributeStringByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityStringDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityStringAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case GEOPOINT -> {
                deleteEntityGeoPointDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityGeoPointAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case BLOB -> {
                deleteEntityAttributeBlobByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityBlobAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case CLOB -> deleteEntityClobAttributesByEntityAttribute(entityAttribute, deletedBy);
            case DATE -> {
                deleteEntityDateDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityDateAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case TIME -> {
                deleteEntityTimeDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityTimeAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case LISTITEM, MULTIPLELISTITEM -> {
                deleteEntityAttributeListItemByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityListItemsByEntityAttribute(entityAttribute, deletedBy); // Default deletion handled here
            }
            case ENTITY, COLLECTION -> {
                deleteEntityAttributeEntityTypesByEntityAttribute(entityAttribute, deletedBy);

                switch(entityAttributeType) {
                    case ENTITY:
                        deleteEntityEntityAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    case COLLECTION:
                        deleteEntityCollectionAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    default:
                        break;
                }
            }
            case WORKFLOW -> {
                var entityAttributeWorkflow = getEntityAttributeWorkflow(entityAttribute);

                workflowControl.deleteWorkflowEntityStatusesByWorkflowAndEntityType(entityAttributeWorkflow.getWorkflow(),
                        entityAttributeDetail.getEntityType(), deletedBy);
                deleteEntityAttributeWorkflowByEntityAttribute(entityAttribute, deletedBy);
            }
        }
        
        deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, deletedBy);
        deleteEntityAttributeDescriptionsByEntityAttribute(entityAttribute, deletedBy);
        
        if(entityAttribute.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
            // Convert to R/W
            entityAttribute = entityAttributeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityAttribute.getPrimaryKey());
        }
        
        entityAttributeDetail.setThruTime(session.getStartTime());
        entityAttribute.setActiveDetail(null);
        entityAttribute.store();
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        var entityAttributes = getEntityAttributesByEntityType(entityInstance.getEntityType());
        
        entityAttributes.stream().map((entityAttribute) -> entityAttribute.getLastDetailForUpdate().getEntityAttributeType().getEntityAttributeTypeName()).forEach((entityAttributeTypeName) -> {
            if(entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())) {
                deleteEntityBooleanAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
                deleteEntityNameAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                deleteEntityIntegerAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                deleteEntityLongAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
                deleteEntityStringAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                deleteEntityGeoPointAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                deleteEntityBlobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                deleteEntityClobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.ENTITY.name())) {
                deleteEntityEntityAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.COLLECTION.name())) {
                deleteEntityCollectionAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                deleteEntityDateAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                deleteEntityTimeAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                deleteEntityListItemAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                deleteEntityMultipleListItemAttributesByEntityInstance(entityInstance, deletedBy);
            }
        });
    }
    
    public void deleteEntityAttributes(List<EntityAttribute> entityAttributes, BasePK deletedBy) {
        entityAttributes.forEach((entityAttribute) -> 
                deleteEntityAttribute(entityAttribute, deletedBy)
        );
    }
    
    public void deleteEntityAttributesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteEntityAttributes(getEntityAttributesByEntityTypeForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeDescriptionFactory entityAttributeDescriptionFactory;
    
    public EntityAttributeDescription createEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            String description, BasePK createdBy) {
        var entityAttributeDescription = entityAttributeDescriptionFactory.create(
                entityAttribute, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeDescription;
    }
    
    private EntityAttributeDescription getEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            EntityPermission entityPermission) {
        EntityAttributeDescription entityAttributeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_lang_languageid = ? AND enad_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_lang_languageid = ? AND enad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityAttributeDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeDescription = entityAttributeDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeDescription;
    }
    
    public EntityAttributeDescription getEntityAttributeDescription(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescription(entityAttribute, language, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeDescription getEntityAttributeDescriptionForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescription(entityAttribute, language, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeDescriptionValue getEntityAttributeDescriptionValue(EntityAttributeDescription entityAttributeDescription) {
        return entityAttributeDescription == null? null: entityAttributeDescription.getEntityAttributeDescriptionValue().clone();
    }
    
    public EntityAttributeDescriptionValue getEntityAttributeDescriptionValueForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescriptionValue(getEntityAttributeDescriptionForUpdate(entityAttribute, language));
    }
    
    private List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        List<EntityAttributeDescription> entityAttributeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions, languages " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_thrutime = ? AND enad_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityAttributeDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeDescriptions = entityAttributeDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeDescriptions;
    }
    
    public List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeDescriptionsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeDescriptionsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityAttributeDescription(EntityAttribute entityAttribute, Language language) {
        String description;
        var entityAttributeDescription = getEntityAttributeDescription(entityAttribute, language);
        
        if(entityAttributeDescription == null && !language.getIsDefault()) {
            entityAttributeDescription = getEntityAttributeDescription(entityAttribute, partyControl.getDefaultLanguage());
        }
        
        if(entityAttributeDescription == null) {
            description = entityAttribute.getLastDetail().getEntityAttributeName();
        } else {
            description = entityAttributeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityAttributeDescriptionTransfer getEntityAttributeDescriptionTransfer(UserVisit userVisit, EntityAttributeDescription entityAttributeDescription, EntityInstance entityInstance) {
        return entityAttributeDescriptionTransferCache.getEntityAttributeDescriptionTransfer(userVisit, entityAttributeDescription, entityInstance);
    }
    
    public List<EntityAttributeDescriptionTransfer> getEntityAttributeDescriptionTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttribute(entityAttribute);
        List<EntityAttributeDescriptionTransfer> entityAttributeDescriptionTransfers = new ArrayList<>(entityAttributeDescriptions.size());
        
        entityAttributeDescriptions.forEach((entityAttributeDescription) ->
                entityAttributeDescriptionTransfers.add(entityAttributeDescriptionTransferCache.getEntityAttributeDescriptionTransfer(userVisit, entityAttributeDescription, entityInstance))
        );
        
        return entityAttributeDescriptionTransfers;
    }
    
    public void updateEntityAttributeDescriptionFromValue(EntityAttributeDescriptionValue entityAttributeDescriptionValue, BasePK updatedBy) {
        if(entityAttributeDescriptionValue.hasBeenModified()) {
            var entityAttributeDescription = entityAttributeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDescriptionValue.getPrimaryKey());
            
            entityAttributeDescription.setThruTime(session.getStartTime());
            entityAttributeDescription.store();

            var entityAttribute = entityAttributeDescription.getEntityAttribute();
            var language = entityAttributeDescription.getLanguage();
            var description = entityAttributeDescriptionValue.getDescription();
            
            entityAttributeDescription = entityAttributeDescriptionFactory.create(entityAttribute, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeDescription(EntityAttributeDescription entityAttributeDescription, BasePK deletedBy) {
        entityAttributeDescription.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeDescription.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttributeForUpdate(entityAttribute);
        
        entityAttributeDescriptions.forEach((entityAttributeDescription) -> 
                deleteEntityAttributeDescription(entityAttributeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Blobs
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeBlobFactory entityAttributeBlobFactory;
    
    public EntityAttributeBlob createEntityAttributeBlob(EntityAttribute entityAttribute, Boolean checkContentWebAddress,
            BasePK createdBy) {
        var entityAttributeBlob = entityAttributeBlobFactory.create(
                entityAttribute, checkContentWebAddress, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeBlob;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeBlobQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeblobs "
                + "WHERE enab_ena_entityattributeid = ? AND enab_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeblobs "
                + "WHERE enab_ena_entityattributeid = ? AND enab_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeBlobQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeBlob getEntityAttributeBlob(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeBlobFactory.getEntityFromQuery(entityPermission, getEntityAttributeBlobQueries,
                entityAttribute, Session.MAX_TIME);
    }
    
    public EntityAttributeBlob getEntityAttributeBlob(EntityAttribute entityAttribute) {
        return getEntityAttributeBlob(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeBlob getEntityAttributeBlobForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeBlob(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeBlobValue getEntityAttributeBlobValue(EntityAttributeBlob entityAttributeBlob) {
        return entityAttributeBlob == null? null: entityAttributeBlob.getEntityAttributeBlobValue().clone();
    }
    
    public EntityAttributeBlobValue getEntityAttributeBlobValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeBlobValue(getEntityAttributeBlobForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeBlobFromValue(EntityAttributeBlobValue entityAttributeBlobValue, BasePK updatedBy) {
        if(entityAttributeBlobValue.hasBeenModified()) {
            var entityAttributeBlob = entityAttributeBlobFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeBlobValue.getPrimaryKey());
            
            entityAttributeBlob.setThruTime(session.getStartTime());
            entityAttributeBlob.store();

            var entityAttribute = entityAttributeBlob.getEntityAttribute();
            var checkContentWebAddress = entityAttributeBlobValue.getCheckContentWebAddress();
            
            entityAttributeBlob = entityAttributeBlobFactory.create(entityAttribute, checkContentWebAddress,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeBlob(EntityAttributeBlob entityAttributeBlob, BasePK deletedBy) {
        entityAttributeBlob.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeBlob.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeBlobByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeBlob = getEntityAttributeBlobForUpdate(entityAttribute);
        
        if(entityAttributeBlob != null) {
            deleteEntityAttributeBlob(entityAttributeBlob, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Strings
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeStringFactory entityAttributeStringFactory;
    
    public EntityAttributeString createEntityAttributeString(EntityAttribute entityAttribute, String validationPattern,
            BasePK createdBy) {
        var entityAttributeString = entityAttributeStringFactory.create(
                entityAttribute, validationPattern, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeString;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeStringQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributestrings "
                + "WHERE enas_ena_entityattributeid = ? AND enas_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributestrings "
                + "WHERE enas_ena_entityattributeid = ? AND enas_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeStringQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeString getEntityAttributeString(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeStringFactory.getEntityFromQuery(entityPermission, getEntityAttributeStringQueries,
                entityAttribute, Session.MAX_TIME);
    }
    
    public EntityAttributeString getEntityAttributeString(EntityAttribute entityAttribute) {
        return getEntityAttributeString(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeString getEntityAttributeStringForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeString(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeStringValue getEntityAttributeStringValue(EntityAttributeString entityAttributeString) {
        return entityAttributeString == null? null: entityAttributeString.getEntityAttributeStringValue().clone();
    }
    
    public EntityAttributeStringValue getEntityAttributeStringValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeStringValue(getEntityAttributeStringForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeStringFromValue(EntityAttributeStringValue entityAttributeStringValue, BasePK updatedBy) {
        if(entityAttributeStringValue.hasBeenModified()) {
            var entityAttributeString = entityAttributeStringFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeStringValue.getPrimaryKey());
            
            entityAttributeString.setThruTime(session.getStartTime());
            entityAttributeString.store();

            var entityAttribute = entityAttributeString.getEntityAttribute();
            var validationPattern = entityAttributeStringValue.getValidationPattern();
            
            entityAttributeString = entityAttributeStringFactory.create(entityAttribute, validationPattern,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeString(EntityAttributeString entityAttributeString, BasePK deletedBy) {
        entityAttributeString.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeString.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeStringByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeString = getEntityAttributeStringForUpdate(entityAttribute);
        
        if(entityAttributeString != null) {
            deleteEntityAttributeString(entityAttributeString, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Integers
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeIntegerFactory entityAttributeIntegerFactory;
    
    public EntityAttributeInteger createEntityAttributeInteger(EntityAttribute entityAttribute, Integer upperRangeIntegerValue,
            Integer upperLimitIntegerValue, Integer lowerLimitIntegerValue, Integer lowerRangeIntegerValue, BasePK createdBy) {
        var entityAttributeInteger = entityAttributeIntegerFactory.create(
                entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeInteger;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeIntegerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeintegers "
                + "WHERE enai_ena_entityattributeid = ? AND enai_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeintegers "
                + "WHERE enai_ena_entityattributeid = ? AND enai_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeIntegerQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeInteger getEntityAttributeInteger(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeIntegerFactory.getEntityFromQuery(entityPermission, getEntityAttributeIntegerQueries,
                entityAttribute, Session.MAX_TIME);
    }
    
    public EntityAttributeInteger getEntityAttributeInteger(EntityAttribute entityAttribute) {
        return getEntityAttributeInteger(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeInteger getEntityAttributeIntegerForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeInteger(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeIntegerValue getEntityAttributeIntegerValue(EntityAttributeInteger entityAttributeInteger) {
        return entityAttributeInteger == null? null: entityAttributeInteger.getEntityAttributeIntegerValue().clone();
    }
    
    public EntityAttributeIntegerValue getEntityAttributeIntegerValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeIntegerValue(getEntityAttributeIntegerForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeIntegerFromValue(EntityAttributeIntegerValue entityAttributeIntegerValue, BasePK updatedBy) {
        if(entityAttributeIntegerValue.hasBeenModified()) {
            var entityAttributeInteger = entityAttributeIntegerFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeIntegerValue.getPrimaryKey());
            
            entityAttributeInteger.setThruTime(session.getStartTime());
            entityAttributeInteger.store();

            var entityAttribute = entityAttributeInteger.getEntityAttribute();
            var upperRangeIntegerValue = entityAttributeIntegerValue.getUpperRangeIntegerValue();
            var upperLimitIntegerValue = entityAttributeIntegerValue.getUpperLimitIntegerValue();
            var lowerLimitIntegerValue = entityAttributeIntegerValue.getLowerLimitIntegerValue();
            var lowerRangeIntegerValue = entityAttributeIntegerValue.getLowerRangeIntegerValue();
            
            entityAttributeInteger = entityAttributeIntegerFactory.create(entityAttribute, upperRangeIntegerValue,
                    upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeInteger(EntityAttributeInteger entityAttributeInteger, BasePK deletedBy) {
        entityAttributeInteger.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeInteger.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeIntegerByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeInteger = getEntityAttributeIntegerForUpdate(entityAttribute);
        
        if(entityAttributeInteger != null) {
            deleteEntityAttributeInteger(entityAttributeInteger, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Longs
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeLongFactory entityAttributeLongFactory;
    
    public EntityAttributeLong createEntityAttributeLong(EntityAttribute entityAttribute, Long upperRangeLongValue,
            Long upperLimitLongValue, Long lowerLimitLongValue, Long lowerRangeLongValue, BasePK createdBy) {
        var entityAttributeLong = entityAttributeLongFactory.create(
                entityAttribute, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeLong;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeLongQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributelongs "
                + "WHERE enal_ena_entityattributeid = ? AND enal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributelongs "
                + "WHERE enal_ena_entityattributeid = ? AND enal_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeLongQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeLong getEntityAttributeLong(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeLongFactory.getEntityFromQuery(entityPermission, getEntityAttributeLongQueries,
                entityAttribute, Session.MAX_TIME);
    }
    
    public EntityAttributeLong getEntityAttributeLong(EntityAttribute entityAttribute) {
        return getEntityAttributeLong(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeLong getEntityAttributeLongForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeLong(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeLongValue getEntityAttributeLongValue(EntityAttributeLong entityAttributeLong) {
        return entityAttributeLong == null? null: entityAttributeLong.getEntityAttributeLongValue().clone();
    }
    
    public EntityAttributeLongValue getEntityAttributeLongValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeLongValue(getEntityAttributeLongForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeLongFromValue(EntityAttributeLongValue entityAttributeLongValue, BasePK updatedBy) {
        if(entityAttributeLongValue.hasBeenModified()) {
            var entityAttributeLong = entityAttributeLongFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeLongValue.getPrimaryKey());
            
            entityAttributeLong.setThruTime(session.getStartTime());
            entityAttributeLong.store();

            var entityAttribute = entityAttributeLong.getEntityAttribute();
            var upperRangeLongValue = entityAttributeLongValue.getUpperRangeLongValue();
            var upperLimitLongValue = entityAttributeLongValue.getUpperLimitLongValue();
            var lowerLimitLongValue = entityAttributeLongValue.getLowerLimitLongValue();
            var lowerRangeLongValue = entityAttributeLongValue.getLowerRangeLongValue();
            
            entityAttributeLong = entityAttributeLongFactory.create(entityAttribute, upperRangeLongValue,
                    upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeLong(EntityAttributeLong entityAttributeLong, BasePK deletedBy) {
        entityAttributeLong.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeLong.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeLongByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeLong = getEntityAttributeLongForUpdate(entityAttribute);
        
        if(entityAttributeLong != null) {
            deleteEntityAttributeLong(entityAttributeLong, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Numerics
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeNumericFactory entityAttributeNumericFactory;
    
    public EntityAttributeNumeric createEntityAttributeNumeric(EntityAttribute entityAttribute, UnitOfMeasureType unitOfMeasureType,
            BasePK createdBy) {
        var entityAttributeNumeric = entityAttributeNumericFactory.create(
                entityAttribute, unitOfMeasureType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeNumeric;
    }
    
    public long countEntityAttributeNumericsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributenumerics " +
                "WHERE enan_uomt_unitofmeasuretypeid = ? AND enan_thrutime = ?",
                unitOfMeasureType, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getEntityAttributeNumericQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributenumerics "
                + "WHERE enan_ena_entityattributeid = ? AND enan_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributenumerics "
                + "WHERE enan_ena_entityattributeid = ? AND enan_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeNumericQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeNumeric getEntityAttributeNumeric(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeNumericFactory.getEntityFromQuery(entityPermission, getEntityAttributeNumericQueries,
                entityAttribute, Session.MAX_TIME);
    }
    
    public EntityAttributeNumeric getEntityAttributeNumeric(EntityAttribute entityAttribute) {
        return getEntityAttributeNumeric(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeNumeric getEntityAttributeNumericForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeNumeric(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeNumericValue getEntityAttributeNumericValue(EntityAttributeNumeric entityAttributeNumeric) {
        return entityAttributeNumeric == null? null: entityAttributeNumeric.getEntityAttributeNumericValue().clone();
    }
    
    public EntityAttributeNumericValue getEntityAttributeNumericValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeNumericValue(getEntityAttributeNumericForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeNumericFromValue(EntityAttributeNumericValue entityAttributeNumericValue, BasePK updatedBy) {
        if(entityAttributeNumericValue.hasBeenModified()) {
            var entityAttributeNumeric = entityAttributeNumericFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeNumericValue.getPrimaryKey());
            
            entityAttributeNumeric.setThruTime(session.getStartTime());
            entityAttributeNumeric.store();

            var entityAttributePK = entityAttributeNumeric.getEntityAttributePK();
            var unitOfMeasureTypePK = entityAttributeNumericValue.getUnitOfMeasureTypePK();
            
            entityAttributeNumeric = entityAttributeNumericFactory.create(entityAttributePK, unitOfMeasureTypePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeNumeric(EntityAttributeNumeric entityAttributeNumeric, BasePK deletedBy) {
        entityAttributeNumeric.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeNumeric.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeNumericByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeNumeric = getEntityAttributeNumericForUpdate(entityAttribute);
        
        if(entityAttributeNumeric != null) {
            deleteEntityAttributeNumeric(entityAttributeNumeric, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute List Items
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeListItemFactory entityAttributeListItemFactory;
    
    public EntityAttributeListItem createEntityAttributeListItem(EntityAttribute entityAttribute, Sequence entityListItemSequence,
            BasePK createdBy) {
        var entityAttributeListItem = entityAttributeListItemFactory.create(
                entityAttribute, entityListItemSequence, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeListItem;
    }
    
    public long countEntityAttributeListItemsByEntityListItemSequence(Sequence entityListItemSequence) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributelistitems " +
                "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ?",
                entityListItemSequence, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getEntityAttributeListItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributelistitems "
                + "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributelistitems "
                + "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeListItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeListItem getEntityAttributeListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeListItemFactory.getEntityFromQuery(entityPermission, getEntityAttributeListItemQueries,
                entityAttribute, Session.MAX_TIME);
    }
    
    public EntityAttributeListItem getEntityAttributeListItem(EntityAttribute entityAttribute) {
        return getEntityAttributeListItem(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeListItem getEntityAttributeListItemForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeListItem(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeListItemValue getEntityAttributeListItemValue(EntityAttributeListItem entityAttributeListItem) {
        return entityAttributeListItem == null? null: entityAttributeListItem.getEntityAttributeListItemValue().clone();
    }
    
    public EntityAttributeListItemValue getEntityAttributeListItemValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeListItemValue(getEntityAttributeListItemForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeListItemFromValue(EntityAttributeListItemValue entityAttributeListItemValue, BasePK updatedBy) {
        if(entityAttributeListItemValue.hasBeenModified()) {
            var entityAttributeListItem = entityAttributeListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeListItemValue.getPrimaryKey());
            
            entityAttributeListItem.setThruTime(session.getStartTime());
            entityAttributeListItem.store();

            var entityAttributePK = entityAttributeListItem.getEntityAttributePK();
            var entityListItemSequencePK = entityAttributeListItemValue.getEntityListItemSequencePK();
            
            entityAttributeListItem = entityAttributeListItemFactory.create(entityAttributePK, entityListItemSequencePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeListItem(EntityAttributeListItem entityAttributeListItem, BasePK deletedBy) {
        entityAttributeListItem.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeListItem.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeListItemByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeListItem = getEntityAttributeListItemForUpdate(entityAttribute);
        
        if(entityAttributeListItem != null) {
            deleteEntityAttributeListItem(entityAttributeListItem, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Workflows
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeWorkflowFactory entityAttributeWorkflowFactory;
    
    public EntityAttributeWorkflow createEntityAttributeWorkflow(EntityAttribute entityAttribute, Workflow workflow,
            BasePK createdBy) {
        var entityAttributeWorkflow = entityAttributeWorkflowFactory.create(
                entityAttribute, workflow, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeWorkflow;
    }

    public long countEntityAttributeWorkflowsByWorkflow(Workflow workflow) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityattributeworkflows " +
                        "WHERE enawkfl_wkfl_workflowid = ? AND enawkfl_thrutime = ?",
                workflow, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getEntityAttributeWorkflowQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM entityattributeworkflows "
                        + "WHERE enawkfl_ena_entityattributeid = ? AND enawkfl_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM entityattributeworkflows "
                        + "WHERE enawkfl_ena_entityattributeid = ? AND enawkfl_thrutime = ? "
                        + "FOR UPDATE");
        getEntityAttributeWorkflowQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeWorkflow getEntityAttributeWorkflow(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeWorkflowFactory.getEntityFromQuery(entityPermission, getEntityAttributeWorkflowQueries,
                entityAttribute, Session.MAX_TIME);
    }

    public EntityAttributeWorkflow getEntityAttributeWorkflow(EntityAttribute entityAttribute) {
        return getEntityAttributeWorkflow(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityAttributeWorkflow getEntityAttributeWorkflowForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeWorkflow(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityAttributeWorkflowValue getEntityAttributeWorkflowValue(EntityAttributeWorkflow entityAttributeWorkflow) {
        return entityAttributeWorkflow == null? null: entityAttributeWorkflow.getEntityAttributeWorkflowValue().clone();
    }

    public EntityAttributeWorkflowValue getEntityAttributeWorkflowValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeWorkflowValue(getEntityAttributeWorkflowForUpdate(entityAttribute));
    }

    public void updateEntityAttributeWorkflowFromValue(EntityAttributeWorkflowValue entityAttributeWorkflowValue, BasePK updatedBy) {
        if(entityAttributeWorkflowValue.hasBeenModified()) {
            var entityAttributeWorkflow = entityAttributeWorkflowFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAttributeWorkflowValue.getPrimaryKey());

            entityAttributeWorkflow.setThruTime(session.getStartTime());
            entityAttributeWorkflow.store();

            var entityAttributePK = entityAttributeWorkflow.getEntityAttributePK();
            var workflowPK = entityAttributeWorkflowValue.getWorkflowPK();

            entityAttributeWorkflow = entityAttributeWorkflowFactory.create(entityAttributePK, workflowPK,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeWorkflow(EntityAttributeWorkflow entityAttributeWorkflow, BasePK deletedBy) {
        entityAttributeWorkflow.setThruTime(session.getStartTime());

        sendEvent(entityAttributeWorkflow.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAttributeWorkflowByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeWorkflow = getEntityAttributeWorkflowForUpdate(entityAttribute);

        if(entityAttributeWorkflow != null) {
            deleteEntityAttributeWorkflow(entityAttributeWorkflow, deletedBy);
        }
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
                "SELECT COUNT(*) " +
                "FROM entityattributeentityattributegroups " +
                "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ?",
                entityAttribute, Session.MAX_TIME);
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributeentityattributegroups " +
                "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?",
                entityAttributeGroup, Session.MAX_TIME);
    }

    private EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributeentityattributegroups " +
                        "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributeentityattributegroups " +
                        "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? " +
                        "FOR UPDATE";
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
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails "
                        + "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups "
                        + "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ? "
                        + "FOR UPDATE";
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
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "FOR UPDATE";
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
    //   Entity List Items
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityListItemFactory entityListItemFactory;
    
    @Inject
    protected EntityListItemDetailFactory entityListItemDetailFactory;
    
    public EntityListItem createEntityListItem(EntityAttribute entityAttribute, String entityListItemName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
        var defaultFound = defaultEntityListItem != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityListItemDetailValue = getDefaultEntityListItemDetailValueForUpdate(entityAttribute);
            
            defaultEntityListItemDetailValue.setIsDefault(false);
            updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityListItem = entityListItemFactory.create();
        var entityListItemDetail = entityListItemDetailFactory.create(entityListItem,
                entityAttribute, entityListItemName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityListItem = entityListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityListItem.getPrimaryKey());
        entityListItem.setActiveDetail(entityListItemDetail);
        entityListItem.setLastDetail(entityListItemDetail);
        entityListItem.store();
        
        sendEvent(entityListItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityListItem;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityListItem */
    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityListItemPK(entityInstance.getEntityUniqueId());

        return entityListItemFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItem getEntityListItemByPK(EntityListItemPK pk) {
        return entityListItemFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }
    
    public long countEntityListItems() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylistitems, entitylistitemdetails " +
                "WHERE eli_activedetailid = elidt_entitylistitemdetailid");
    }

    private EntityListItem getDefaultEntityListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityListItem entityListItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = entityListItemFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItem = entityListItemFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItem;
    }
    
    public EntityListItem getDefaultEntityListItem(EntityAttribute entityAttribute) {
        return getDefaultEntityListItem(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getDefaultEntityListItemForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityListItem(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDetailValue getDefaultEntityListItemDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityListItemForUpdate(entityAttribute).getLastDetailForUpdate().getEntityListItemDetailValue().clone();
    }
    
    public EntityListItem getEntityListItemByName(EntityAttribute entityAttribute, String entityListItemName, EntityPermission entityPermission) {
        EntityListItem entityListItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_entitylistitemname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_entitylistitemname = ? " +
                        "FOR UPDATE";
            }

            var ps = entityListItemFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityListItemName);
            
            entityListItem = entityListItemFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItem;
    }
    
    public EntityListItem getEntityListItemByName(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemByName(entityAttribute, entityListItemName, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getEntityListItemByNameForUpdate(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemByName(entityAttribute, entityListItemName, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDetailValue getEntityListItemDetailValueForUpdate(EntityListItem entityListItem) {
        return entityListItem == null? null: entityListItem.getLastDetailForUpdate().getEntityListItemDetailValue().clone();
    }
    
    public EntityListItemDetailValue getEntityListItemDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemDetailValueForUpdate(getEntityListItemByNameForUpdate(entityAttribute, entityListItemName));
    }
    
    private List<EntityListItem> getEntityListItems(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityListItem> entityListItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? " +
                        "ORDER BY elidt_sortorder, elidt_entitylistitemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }

            var ps = entityListItemFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItems = entityListItemFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItems;
    }
    
    public List<EntityListItem> getEntityListItems(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItem> getEntityListItemsForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityListItems(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylistitems, entitylistitemdetails " +
                "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityListItemTransfer getEntityListItemTransfer(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        return entityListItemTransferCache.getEntityListItemTransfer(userVisit, entityListItem, entityInstance);
    }
    
    public List<EntityListItemTransfer> getEntityListItemTransfers(UserVisit userVisit, Collection<EntityListItem> entityListItems, EntityInstance entityInstance) {
        List<EntityListItemTransfer> entityListItemTransfers = new ArrayList<>(entityListItems.size());

        entityListItems.forEach((entityListItem) ->
                entityListItemTransfers.add(entityListItemTransferCache.getEntityListItemTransfer(userVisit, entityListItem, entityInstance))
        );

        return entityListItemTransfers;
    }

    public List<EntityListItemTransfer> getEntityListItemTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemTransfers(userVisit, getEntityListItems(entityAttribute), entityInstance);
    }

    private void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityListItemDetailValue.hasBeenModified()) {
            var entityListItem = entityListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityListItemDetailValue.getEntityListItemPK());
            var entityListItemDetail = entityListItem.getActiveDetailForUpdate();
            
            entityListItemDetail.setThruTime(session.getStartTime());
            entityListItemDetail.store();

            var entityListItemPK = entityListItemDetail.getEntityListItemPK(); // Not updated
            var entityAttribute = entityListItemDetail.getEntityAttribute(); // Not updated
            var entityListItemName = entityListItemDetailValue.getEntityListItemName();
            var isDefault = entityListItemDetailValue.getIsDefault();
            var sortOrder = entityListItemDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
                var defaultFound = defaultEntityListItem != null && !defaultEntityListItem.equals(entityListItem);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityListItemDetailValue = getDefaultEntityListItemDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityListItemDetailValue.setIsDefault(false);
                    updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            entityListItemDetail = entityListItemDetailFactory.create(entityListItemPK,
                    entityAttribute.getPrimaryKey(), entityListItemName, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            entityListItem.setActiveDetail(entityListItemDetail);
            entityListItem.setLastDetail(entityListItemDetail);
            
            sendEvent(entityListItemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, BasePK updatedBy) {
        updateEntityListItemFromValue(entityListItemDetailValue, true, updatedBy);
    }
    
    public EntityListItemChoicesBean getEntityListItemChoices(String defaultEntityListItemChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        var entityListItems = getEntityListItems(entityAttribute);
        var size = entityListItems.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityListItemChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var entityListItem : entityListItems) {
            var entityListItemDetail = entityListItem.getLastDetail();
            var label = getBestEntityListItemDescription(entityListItem, language);
            var value = entityListItemDetail.getEntityListItemName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityListItemChoice != null && defaultEntityListItemChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityListItemDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityListItemChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityListItem(EntityListItem entityListItem, boolean checkDefault, BasePK deletedBy) {
        var entityListItemDetail = entityListItem.getLastDetailForUpdate();
        var entityAttributeTypeName = entityListItemDetail.getEntityAttribute().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        
        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            deleteEntityListItemDefaultByEntityListItem(entityListItem, deletedBy);
            deleteEntityListItemAttributesByEntityListItem(entityListItem, deletedBy);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            deleteEntityMultipleListItemDefaultsByEntityListItem(entityListItem, deletedBy);
            deleteEntityMultipleListItemAttributesByEntityListItem(entityListItem, deletedBy);
        }
        
        deleteEntityListItemDescriptionsByEntityListItem(entityListItem, deletedBy);
        
        entityListItemDetail.setThruTime(session.getStartTime());
        entityListItem.setActiveDetail(null);
        entityListItem.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var entityAttribute = entityListItemDetail.getEntityAttribute();
            var defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
            if(defaultEntityListItem == null) {
                var entityListItems = getEntityListItemsForUpdate(entityAttribute);

                if(!entityListItems.isEmpty()) {
                    var iter = entityListItems.iterator();
                    if(iter.hasNext()) {
                        defaultEntityListItem = iter.next();
                    }
                    var entityListItemDetailValue = Objects.requireNonNull(defaultEntityListItem).getLastDetailForUpdate().getEntityListItemDetailValue().clone();

                    entityListItemDetailValue.setIsDefault(true);
                    updateEntityListItemFromValue(entityListItemDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityListItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityListItem(entityListItem, true, deletedBy);
    }

    public void deleteEntityListItemsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityListItems = getEntityListItemsForUpdate(entityAttribute);
        
        entityListItems.forEach((entityListItem) ->
                deleteEntityListItem(entityListItem, false, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityListItemDescriptionFactory entityListItemDescriptionFactory;
    
    public EntityListItemDescription createEntityListItemDescription(EntityListItem entityListItem, Language language, String description, BasePK createdBy) {
        var entityListItemDescription = entityListItemDescriptionFactory.create(entityListItem, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(entityListItem.getPrimaryKey(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityListItemDescription;
    }
    
    private EntityListItemDescription getEntityListItemDescription(EntityListItem entityListItem, Language language, EntityPermission entityPermission) {
        EntityListItemDescription entityListItemDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_lang_languageid = ? AND elid_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_lang_languageid = ? AND elid_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityListItemDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemDescription = entityListItemDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemDescription;
    }
    
    public EntityListItemDescription getEntityListItemDescription(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescription(entityListItem, language, EntityPermission.READ_ONLY);
    }
    
    public EntityListItemDescription getEntityListItemDescriptionForUpdate(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescription(entityListItem, language, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDescriptionValue getEntityListItemDescriptionValue(EntityListItemDescription entityListItemDescription) {
        return entityListItemDescription == null? null: entityListItemDescription.getEntityListItemDescriptionValue().clone();
    }
    
    public EntityListItemDescriptionValue getEntityListItemDescriptionValueForUpdate(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescriptionValue(getEntityListItemDescriptionForUpdate(entityListItem, language));
    }
    
    private List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityListItemDescription> entityListItemDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions, languages " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_thrutime = ? AND elid_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityListItemDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemDescriptions = entityListItemDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemDescriptions;
    }
    
    public List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemDescriptionsByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemDescriptionsByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityListItemDescription(EntityListItem entityListItem, Language language) {
        String description;
        var entityListItemDescription = getEntityListItemDescription(entityListItem, language);
        
        if(entityListItemDescription == null && !language.getIsDefault()) {
            entityListItemDescription = getEntityListItemDescription(entityListItem, partyControl.getDefaultLanguage());
        }
        
        if(entityListItemDescription == null) {
            description = entityListItem.getLastDetail().getEntityListItemName();
        } else {
            description = entityListItemDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityListItemDescriptionTransfer getEntityListItemDescriptionTransfer(UserVisit userVisit, EntityListItemDescription entityListItemDescription, EntityInstance entityInstance) {
        return entityListItemDescriptionTransferCache.getEntityListItemDescriptionTransfer(userVisit, entityListItemDescription, entityInstance);
    }
    
    public List<EntityListItemDescriptionTransfer> getEntityListItemDescriptionTransfersByEntityListItem(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        var entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItem(entityListItem);
        List<EntityListItemDescriptionTransfer> entityListItemDescriptionTransfers = new ArrayList<>(entityListItemDescriptions.size());
        
        entityListItemDescriptions.forEach((entityListItemDescription) ->
                entityListItemDescriptionTransfers.add(entityListItemDescriptionTransferCache.getEntityListItemDescriptionTransfer(userVisit, entityListItemDescription, entityInstance))
        );
        
        return entityListItemDescriptionTransfers;
    }
    
    public void updateEntityListItemDescriptionFromValue(EntityListItemDescriptionValue entityListItemDescriptionValue, BasePK updatedBy) {
        if(entityListItemDescriptionValue.hasBeenModified()) {
            var entityListItemDescription = entityListItemDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityListItemDescriptionValue.getPrimaryKey());
            
            entityListItemDescription.setThruTime(session.getStartTime());
            entityListItemDescription.store();

            var entityListItem = entityListItemDescription.getEntityListItem();
            var language = entityListItemDescription.getLanguage();
            var description = entityListItemDescriptionValue.getDescription();
            
            entityListItemDescription = entityListItemDescriptionFactory.create(entityListItem, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityListItem.getPrimaryKey(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemDescription(EntityListItemDescription entityListItemDescription, BasePK deletedBy) {
        entityListItemDescription.setThruTime(session.getStartTime());
        
        sendEvent(entityListItemDescription.getEntityListItemPK(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        var entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItemForUpdate(entityListItem);
        
        entityListItemDescriptions.forEach((entityListItemDescription) -> 
                deleteEntityListItemDescription(entityListItemDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Ranges
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityIntegerRangeFactory entityIntegerRangeFactory;
    
    @Inject
    protected EntityIntegerRangeDetailFactory entityIntegerRangeDetailFactory;
    
    public EntityIntegerRange createEntityIntegerRange(EntityAttribute entityAttribute, String entityIntegerRangeName, Integer minimumIntegerValue,
            Integer maximumIntegerValue, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
        var defaultFound = defaultEntityIntegerRange != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityIntegerRangeDetailValue = getDefaultEntityIntegerRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityIntegerRangeDetailValue.setIsDefault(false);
            updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityIntegerRange = entityIntegerRangeFactory.create();
        var entityIntegerRangeDetail = entityIntegerRangeDetailFactory.create(entityIntegerRange, entityAttribute,
                entityIntegerRangeName, minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityIntegerRange = entityIntegerRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRange.getPrimaryKey());
        entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
        entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
        entityIntegerRange.store();
        
        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityIntegerRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityIntegerRange */
    public EntityIntegerRange getEntityIntegerRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityIntegerRangePK(entityInstance.getEntityUniqueId());
        var entityIntegerRange = entityIntegerRangeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityIntegerRange;
    }
    
    private EntityIntegerRange getDefaultEntityIntegerRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityIntegerRange entityIntegerRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = entityIntegerRangeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRange = entityIntegerRangeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRange;
    }
    
    public EntityIntegerRange getDefaultEntityIntegerRange(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRange(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRange getDefaultEntityIntegerRangeForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRange(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDetailValue getDefaultEntityIntegerRangeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRangeForUpdate(entityAttribute).getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();
    }
    
    private EntityIntegerRange getEntityIntegerRangeByName(EntityAttribute entityAttribute, String entityIntegerRangeName, EntityPermission entityPermission) {
        EntityIntegerRange entityIntegerRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_entityintegerrangename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_entityintegerrangename = ? " +
                        "FOR UPDATE";
            }

            var ps = entityIntegerRangeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityIntegerRangeName);
            
            entityIntegerRange = entityIntegerRangeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRange;
    }
    
    public EntityIntegerRange getEntityIntegerRangeByName(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRange getEntityIntegerRangeByNameForUpdate(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDetailValue getEntityIntegerRangeDetailValueForUpdate(EntityIntegerRange entityIntegerRange) {
        return entityIntegerRange == null? null: entityIntegerRange.getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();
    }
    
    public EntityIntegerRangeDetailValue getEntityIntegerRangeDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeDetailValueForUpdate(getEntityIntegerRangeByNameForUpdate(entityAttribute, entityIntegerRangeName));
    }
    
    private List<EntityIntegerRange> getEntityIntegerRanges(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityIntegerRange> entityIntegerRanges;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ? " +
                        "ORDER BY enirdt_sortorder, enirdt_entityintegerrangename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }

            var ps = entityIntegerRangeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRanges = entityIntegerRangeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRanges;
    }
    
    public List<EntityIntegerRange> getEntityIntegerRanges(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityIntegerRange> getEntityIntegerRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityIntegerRanges(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityintegerranges, entityintegerrangedetails " +
                "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityIntegerRangeTransfer getEntityIntegerRangeTransfer(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        return entityIntegerRangeTransferCache.getEntityIntegerRangeTransfer(userVisit, entityIntegerRange, entityInstance);
    }
    
    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfers(UserVisit userVisit, Collection<EntityIntegerRange> entityIntegerRanges, EntityInstance entityInstance) {
        List<EntityIntegerRangeTransfer> entityIntegerRangeTransfers = new ArrayList<>(entityIntegerRanges.size());

        entityIntegerRanges.forEach((entityIntegerRange) ->
                entityIntegerRangeTransfers.add(entityIntegerRangeTransferCache.getEntityIntegerRangeTransfer(userVisit, entityIntegerRange, entityInstance))
        );

        return entityIntegerRangeTransfers;
    }

    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerRangeTransfers(userVisit, getEntityIntegerRanges(entityAttribute), entityInstance);
    }

    private void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityIntegerRangeDetailValue.hasBeenModified()) {
            var entityIntegerRange = entityIntegerRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityIntegerRangeDetailValue.getEntityIntegerRangePK());
            var entityIntegerRangeDetail = entityIntegerRange.getActiveDetailForUpdate();
            
            entityIntegerRangeDetail.setThruTime(session.getStartTime());
            entityIntegerRangeDetail.store();

            var entityIntegerRangePK = entityIntegerRangeDetail.getEntityIntegerRangePK(); // Not updated
            var entityAttribute = entityIntegerRangeDetail.getEntityAttribute(); // Not updated
            var entityIntegerRangeName = entityIntegerRangeDetailValue.getEntityIntegerRangeName();
            var minimumIntegerValue = entityIntegerRangeDetailValue.getMinimumIntegerValue();
            var maximumIntegerValue = entityIntegerRangeDetailValue.getMaximumIntegerValue();
            var isDefault = entityIntegerRangeDetailValue.getIsDefault();
            var sortOrder = entityIntegerRangeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
                var defaultFound = defaultEntityIntegerRange != null && !defaultEntityIntegerRange.equals(entityIntegerRange);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityIntegerRangeDetailValue = getDefaultEntityIntegerRangeDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityIntegerRangeDetailValue.setIsDefault(false);
                    updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            entityIntegerRangeDetail = entityIntegerRangeDetailFactory.create(entityIntegerRangePK, entityAttribute.getPrimaryKey(), entityIntegerRangeName,
                    minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
            entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
            
            sendEvent(entityIntegerRangePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, BasePK updatedBy) {
        updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, true, updatedBy);
    }
    
    public EntityIntegerRangeChoicesBean getEntityIntegerRangeChoices(String defaultEntityIntegerRangeChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        var entityIntegerRanges = getEntityIntegerRanges(entityAttribute);
        var size = entityIntegerRanges.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityIntegerRangeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var entityIntegerRange : entityIntegerRanges) {
            var entityIntegerRangeDetail = entityIntegerRange.getLastDetail();
            var label = getBestEntityIntegerRangeDescription(entityIntegerRange, language);
            var value = entityIntegerRangeDetail.getEntityIntegerRangeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityIntegerRangeChoice != null && defaultEntityIntegerRangeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityIntegerRangeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityIntegerRangeChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityIntegerRange(EntityIntegerRange entityIntegerRange, boolean checkDefault, BasePK deletedBy) {
        var entityIntegerRangeDetail = entityIntegerRange.getLastDetailForUpdate();
        
        deleteEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, deletedBy);
        
        entityIntegerRangeDetail.setThruTime(session.getStartTime());
        entityIntegerRange.setActiveDetail(null);
        entityIntegerRange.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var entityAttribute = entityIntegerRangeDetail.getEntityAttribute();
            var defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
            if(defaultEntityIntegerRange == null) {
                var entityIntegerRanges = getEntityIntegerRangesForUpdate(entityAttribute);

                if(!entityIntegerRanges.isEmpty()) {
                    var iter = entityIntegerRanges.iterator();
                    if(iter.hasNext()) {
                        defaultEntityIntegerRange = iter.next();
                    }
                    var entityIntegerRangeDetailValue = Objects.requireNonNull(defaultEntityIntegerRange).getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();

                    entityIntegerRangeDetailValue.setIsDefault(true);
                    updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityIntegerRange(EntityIntegerRange entityIntegerRange, BasePK deletedBy) {
        deleteEntityIntegerRange(entityIntegerRange, true, deletedBy);
    }

    public void deleteEntityIntegerRangesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityIntegerRanges = getEntityIntegerRangesForUpdate(entityAttribute);
        
        entityIntegerRanges.forEach((entityIntegerRange) ->
                deleteEntityIntegerRange(entityIntegerRange, false, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityIntegerRangeDescriptionFactory entityIntegerRangeDescriptionFactory;
    
    public EntityIntegerRangeDescription createEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, String description, BasePK createdBy) {
        var entityIntegerRangeDescription = entityIntegerRangeDescriptionFactory.create(entityIntegerRange, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityIntegerRangeDescription;
    }
    
    private EntityIntegerRangeDescription getEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, EntityPermission entityPermission) {
        EntityIntegerRangeDescription entityIntegerRangeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_lang_languageid = ? AND enird_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_lang_languageid = ? AND enird_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityIntegerRangeDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerRangeDescription = entityIntegerRangeDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRangeDescription;
    }
    
    public EntityIntegerRangeDescription getEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescription(entityIntegerRange, language, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRangeDescription getEntityIntegerRangeDescriptionForUpdate(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescription(entityIntegerRange, language, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDescriptionValue getEntityIntegerRangeDescriptionValue(EntityIntegerRangeDescription entityIntegerRangeDescription) {
        return entityIntegerRangeDescription == null? null: entityIntegerRangeDescription.getEntityIntegerRangeDescriptionValue().clone();
    }
    
    public EntityIntegerRangeDescriptionValue getEntityIntegerRangeDescriptionValueForUpdate(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescriptionValue(getEntityIntegerRangeDescriptionForUpdate(entityIntegerRange, language));
    }
    
    private List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange, EntityPermission entityPermission) {
        List<EntityIntegerRangeDescription> entityIntegerRangeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions, languages " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_thrutime = ? AND enird_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityIntegerRangeDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerRangeDescriptions = entityIntegerRangeDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRangeDescriptions;
    }
    
    public List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange) {
        return getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, EntityPermission.READ_ONLY);
    }
    
    public List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRangeForUpdate(EntityIntegerRange entityIntegerRange) {
        return getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language) {
        String description;
        var entityIntegerRangeDescription = getEntityIntegerRangeDescription(entityIntegerRange, language);
        
        if(entityIntegerRangeDescription == null && !language.getIsDefault()) {
            entityIntegerRangeDescription = getEntityIntegerRangeDescription(entityIntegerRange, partyControl.getDefaultLanguage());
        }
        
        if(entityIntegerRangeDescription == null) {
            description = entityIntegerRange.getLastDetail().getEntityIntegerRangeName();
        } else {
            description = entityIntegerRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityIntegerRangeDescriptionTransfer getEntityIntegerRangeDescriptionTransfer(UserVisit userVisit, EntityIntegerRangeDescription entityIntegerRangeDescription, EntityInstance entityInstance) {
        return entityIntegerRangeDescriptionTransferCache.getEntityIntegerRangeDescriptionTransfer(userVisit, entityIntegerRangeDescription, entityInstance);
    }
    
    public List<EntityIntegerRangeDescriptionTransfer> getEntityIntegerRangeDescriptionTransfersByEntityIntegerRange(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        var entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange);
        List<EntityIntegerRangeDescriptionTransfer> entityIntegerRangeDescriptionTransfers = new ArrayList<>(entityIntegerRangeDescriptions.size());
        
        entityIntegerRangeDescriptions.forEach((entityIntegerRangeDescription) ->
                entityIntegerRangeDescriptionTransfers.add(entityIntegerRangeDescriptionTransferCache.getEntityIntegerRangeDescriptionTransfer(userVisit, entityIntegerRangeDescription, entityInstance))
        );
        
        return entityIntegerRangeDescriptionTransfers;
    }
    
    public void updateEntityIntegerRangeDescriptionFromValue(EntityIntegerRangeDescriptionValue entityIntegerRangeDescriptionValue, BasePK updatedBy) {
        if(entityIntegerRangeDescriptionValue.hasBeenModified()) {
            var entityIntegerRangeDescription = entityIntegerRangeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRangeDescriptionValue.getPrimaryKey());
            
            entityIntegerRangeDescription.setThruTime(session.getStartTime());
            entityIntegerRangeDescription.store();

            var entityIntegerRange = entityIntegerRangeDescription.getEntityIntegerRange();
            var language = entityIntegerRangeDescription.getLanguage();
            var description = entityIntegerRangeDescriptionValue.getDescription();
            
            entityIntegerRangeDescription = entityIntegerRangeDescriptionFactory.create(entityIntegerRange, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityIntegerRangeDescription(EntityIntegerRangeDescription entityIntegerRangeDescription, BasePK deletedBy) {
        entityIntegerRangeDescription.setThruTime(session.getStartTime());
        
        sendEvent(entityIntegerRangeDescription.getEntityIntegerRangePK(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange, BasePK deletedBy) {
        var entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRangeForUpdate(entityIntegerRange);
        
        entityIntegerRangeDescriptions.forEach((entityIntegerRangeDescription) -> 
                deleteEntityIntegerRangeDescription(entityIntegerRangeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Ranges
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityLongRangeFactory entityLongRangeFactory;
    
    @Inject
    protected EntityLongRangeDetailFactory entityLongRangeDetailFactory;
    
    public EntityLongRange createEntityLongRange(EntityAttribute entityAttribute, String entityLongRangeName, Long minimumLongValue, Long maximumLongValue,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
        var defaultFound = defaultEntityLongRange != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityLongRangeDetailValue = getDefaultEntityLongRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityLongRangeDetailValue.setIsDefault(false);
            updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityLongRange = entityLongRangeFactory.create();
        var entityLongRangeDetail = entityLongRangeDetailFactory.create(entityLongRange, entityAttribute, entityLongRangeName,
                minimumLongValue, maximumLongValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityLongRange = entityLongRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityLongRange.getPrimaryKey());
        entityLongRange.setActiveDetail(entityLongRangeDetail);
        entityLongRange.setLastDetail(entityLongRangeDetail);
        entityLongRange.store();
        
        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityLongRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityLongRange */
    public EntityLongRange getEntityLongRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityLongRangePK(entityInstance.getEntityUniqueId());
        var entityLongRange = entityLongRangeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityLongRange;
    }
    
    private EntityLongRange getDefaultEntityLongRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityLongRange entityLongRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = entityLongRangeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRange = entityLongRangeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRange;
    }
    
    public EntityLongRange getDefaultEntityLongRange(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRange(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRange getDefaultEntityLongRangeForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRange(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDetailValue getDefaultEntityLongRangeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRangeForUpdate(entityAttribute).getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();
    }
    
    private EntityLongRange getEntityLongRangeByName(EntityAttribute entityAttribute, String entityLongRangeName, EntityPermission entityPermission) {
        EntityLongRange entityLongRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_entitylongrangename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_entitylongrangename = ? " +
                        "FOR UPDATE";
            }

            var ps = entityLongRangeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityLongRangeName);
            
            entityLongRange = entityLongRangeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRange;
    }
    
    public EntityLongRange getEntityLongRangeByName(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeByName(entityAttribute, entityLongRangeName, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRange getEntityLongRangeByNameForUpdate(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeByName(entityAttribute, entityLongRangeName, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDetailValue getEntityLongRangeDetailValueForUpdate(EntityLongRange entityLongRange) {
        return entityLongRange == null? null: entityLongRange.getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();
    }
    
    public EntityLongRangeDetailValue getEntityLongRangeDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeDetailValueForUpdate(getEntityLongRangeByNameForUpdate(entityAttribute, entityLongRangeName));
    }
    
    private List<EntityLongRange> getEntityLongRanges(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityLongRange> entityLongRanges;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ? " +
                        "ORDER BY enlrdt_sortorder, enlrdt_entitylongrangename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }

            var ps = entityLongRangeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRanges = entityLongRangeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRanges;
    }
    
    public List<EntityLongRange> getEntityLongRanges(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityLongRange> getEntityLongRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityLongRanges(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylongranges, entitylongrangedetails " +
                "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityLongRangeTransfer getEntityLongRangeTransfer(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        return entityLongRangeTransferCache.getEntityLongRangeTransfer(userVisit, entityLongRange, entityInstance);
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfers(UserVisit userVisit, Collection<EntityLongRange> entityLongRanges, EntityInstance entityInstance) {
        List<EntityLongRangeTransfer> entityLongRangeTransfers = new ArrayList<>(entityLongRanges.size());

        entityLongRanges.forEach((entityLongRange) ->
                entityLongRangeTransfers.add(entityLongRangeTransferCache.getEntityLongRangeTransfer(userVisit, entityLongRange, entityInstance))
        );

        return entityLongRangeTransfers;
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongRangeTransfers(userVisit, getEntityLongRanges(entityAttribute), entityInstance);
    }

    private void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityLongRangeDetailValue.hasBeenModified()) {
            var entityLongRange = entityLongRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityLongRangeDetailValue.getEntityLongRangePK());
            var entityLongRangeDetail = entityLongRange.getActiveDetailForUpdate();
            
            entityLongRangeDetail.setThruTime(session.getStartTime());
            entityLongRangeDetail.store();

            var entityLongRangePK = entityLongRangeDetail.getEntityLongRangePK(); // Not updated
            var entityAttribute = entityLongRangeDetail.getEntityAttribute(); // Not updated
            var entityLongRangeName = entityLongRangeDetailValue.getEntityLongRangeName();
            var minimumLongValue = entityLongRangeDetailValue.getMinimumLongValue();
            var maximumLongValue = entityLongRangeDetailValue.getMaximumLongValue();
            var isDefault = entityLongRangeDetailValue.getIsDefault();
            var sortOrder = entityLongRangeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
                var defaultFound = defaultEntityLongRange != null && !defaultEntityLongRange.equals(entityLongRange);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityLongRangeDetailValue = getDefaultEntityLongRangeDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityLongRangeDetailValue.setIsDefault(false);
                    updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            entityLongRangeDetail = entityLongRangeDetailFactory.create(entityLongRangePK, entityAttribute.getPrimaryKey(), entityLongRangeName,
                    minimumLongValue, maximumLongValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            entityLongRange.setActiveDetail(entityLongRangeDetail);
            entityLongRange.setLastDetail(entityLongRangeDetail);
            
            sendEvent(entityLongRangePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, BasePK updatedBy) {
        updateEntityLongRangeFromValue(entityLongRangeDetailValue, true, updatedBy);
    }
    
    public EntityLongRangeChoicesBean getEntityLongRangeChoices(String defaultEntityLongRangeChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        var entityLongRanges = getEntityLongRanges(entityAttribute);
        var size = entityLongRanges.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityLongRangeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var entityLongRange : entityLongRanges) {
            var entityLongRangeDetail = entityLongRange.getLastDetail();
            var label = getBestEntityLongRangeDescription(entityLongRange, language);
            var value = entityLongRangeDetail.getEntityLongRangeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityLongRangeChoice != null && defaultEntityLongRangeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityLongRangeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityLongRangeChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityLongRange(EntityLongRange entityLongRange, boolean checkDefault, BasePK deletedBy) {
        var entityLongRangeDetail = entityLongRange.getLastDetailForUpdate();
        
        deleteEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, deletedBy);
        
        entityLongRangeDetail.setThruTime(session.getStartTime());
        entityLongRange.setActiveDetail(null);
        entityLongRange.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var entityAttribute = entityLongRangeDetail.getEntityAttribute();
            var defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
            if(defaultEntityLongRange == null) {
                var entityLongRanges = getEntityLongRangesForUpdate(entityAttribute);

                if(!entityLongRanges.isEmpty()) {
                    var iter = entityLongRanges.iterator();
                    if(iter.hasNext()) {
                        defaultEntityLongRange = iter.next();
                    }
                    var entityLongRangeDetailValue = Objects.requireNonNull(defaultEntityLongRange).getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();

                    entityLongRangeDetailValue.setIsDefault(true);
                    updateEntityLongRangeFromValue(entityLongRangeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityLongRange(EntityLongRange entityLongRange, BasePK deletedBy) {
        deleteEntityLongRange(entityLongRange, true, deletedBy);
    }

    public void deleteEntityLongRangesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityLongRanges = getEntityLongRangesForUpdate(entityAttribute);
        
        entityLongRanges.forEach((entityLongRange) ->
                deleteEntityLongRange(entityLongRange, false, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityLongRangeDescriptionFactory entityLongRangeDescriptionFactory;
    
    public EntityLongRangeDescription createEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, String description, BasePK createdBy) {
        var entityLongRangeDescription = entityLongRangeDescriptionFactory.create(entityLongRange, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityLongRangeDescription;
    }
    
    private EntityLongRangeDescription getEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, EntityPermission entityPermission) {
        EntityLongRangeDescription entityLongRangeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_lang_languageid = ? AND enlrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_lang_languageid = ? AND enlrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityLongRangeDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongRangeDescription = entityLongRangeDescriptionFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRangeDescription;
    }
    
    public EntityLongRangeDescription getEntityLongRangeDescription(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescription(entityLongRange, language, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRangeDescription getEntityLongRangeDescriptionForUpdate(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescription(entityLongRange, language, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDescriptionValue getEntityLongRangeDescriptionValue(EntityLongRangeDescription entityLongRangeDescription) {
        return entityLongRangeDescription == null? null: entityLongRangeDescription.getEntityLongRangeDescriptionValue().clone();
    }
    
    public EntityLongRangeDescriptionValue getEntityLongRangeDescriptionValueForUpdate(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescriptionValue(getEntityLongRangeDescriptionForUpdate(entityLongRange, language));
    }
    
    private List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange, EntityPermission entityPermission) {
        List<EntityLongRangeDescription> entityLongRangeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions, languages " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_thrutime = ? AND enlrd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityLongRangeDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongRangeDescriptions = entityLongRangeDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRangeDescriptions;
    }
    
    public List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange) {
        return getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, EntityPermission.READ_ONLY);
    }
    
    public List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRangeForUpdate(EntityLongRange entityLongRange) {
        return getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityLongRangeDescription(EntityLongRange entityLongRange, Language language) {
        String description;
        var entityLongRangeDescription = getEntityLongRangeDescription(entityLongRange, language);
        
        if(entityLongRangeDescription == null && !language.getIsDefault()) {
            entityLongRangeDescription = getEntityLongRangeDescription(entityLongRange, partyControl.getDefaultLanguage());
        }
        
        if(entityLongRangeDescription == null) {
            description = entityLongRange.getLastDetail().getEntityLongRangeName();
        } else {
            description = entityLongRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityLongRangeDescriptionTransfer getEntityLongRangeDescriptionTransfer(UserVisit userVisit, EntityLongRangeDescription entityLongRangeDescription, EntityInstance entityInstance) {
        return entityLongRangeDescriptionTransferCache.getEntityLongRangeDescriptionTransfer(userVisit, entityLongRangeDescription, entityInstance);
    }
    
    public List<EntityLongRangeDescriptionTransfer> getEntityLongRangeDescriptionTransfersByEntityLongRange(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        var entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange);
        List<EntityLongRangeDescriptionTransfer> entityLongRangeDescriptionTransfers = new ArrayList<>(entityLongRangeDescriptions.size());
        
        entityLongRangeDescriptions.forEach((entityLongRangeDescription) ->
                entityLongRangeDescriptionTransfers.add(entityLongRangeDescriptionTransferCache.getEntityLongRangeDescriptionTransfer(userVisit, entityLongRangeDescription, entityInstance))
        );
        
        return entityLongRangeDescriptionTransfers;
    }
    
    public void updateEntityLongRangeDescriptionFromValue(EntityLongRangeDescriptionValue entityLongRangeDescriptionValue, BasePK updatedBy) {
        if(entityLongRangeDescriptionValue.hasBeenModified()) {
            var entityLongRangeDescription = entityLongRangeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityLongRangeDescriptionValue.getPrimaryKey());
            
            entityLongRangeDescription.setThruTime(session.getStartTime());
            entityLongRangeDescription.store();

            var entityLongRange = entityLongRangeDescription.getEntityLongRange();
            var language = entityLongRangeDescription.getLanguage();
            var description = entityLongRangeDescriptionValue.getDescription();
            
            entityLongRangeDescription = entityLongRangeDescriptionFactory.create(entityLongRange, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityLongRange.getPrimaryKey(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongRangeDescription(EntityLongRangeDescription entityLongRangeDescription, BasePK deletedBy) {
        entityLongRangeDescription.setThruTime(session.getStartTime());
        
        sendEvent(entityLongRangeDescription.getEntityLongRangePK(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange, BasePK deletedBy) {
        var entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRangeForUpdate(entityLongRange);
        
        entityLongRangeDescriptions.forEach((entityLongRangeDescription) -> 
                deleteEntityLongRangeDescription(entityLongRangeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityBooleanDefaultFactory entityBooleanDefaultFactory;
    
    public EntityBooleanDefault createEntityBooleanDefault(EntityAttribute entityAttribute, Boolean booleanAttribute,
            BasePK createdBy) {
        var entityBooleanDefault = entityBooleanDefaultFactory.create(entityAttribute,
                booleanAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityBooleanDefault;
    }

    public long countEntityBooleanDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitybooleandefaults
                    WHERE enbldef_ena_entityattributeid = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityBooleanDefaultHistoryQueries;

    static {
        getEntityBooleanDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitybooleandefaults
                WHERE enbldef_ena_entityattributeid = ?
                ORDER BY enbldef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityBooleanDefault> getEntityBooleanDefaultHistory(EntityAttribute entityAttribute) {
        return entityBooleanDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityBooleanDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityBooleanDefault getEntityBooleanDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityBooleanDefault entityBooleanDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleandefaults " +
                        "WHERE enbldef_ena_entityattributeid = ? AND enbldef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleandefaults " +
                        "WHERE enbldef_ena_entityattributeid = ? AND enbldef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityBooleanDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityBooleanDefault = entityBooleanDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityBooleanDefault;
    }

    public EntityBooleanDefault getEntityBooleanDefault(EntityAttribute entityAttribute) {
        return getEntityBooleanDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityBooleanDefault getEntityBooleanDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityBooleanDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityBooleanDefaultValue getEntityBooleanDefaultValueForUpdate(EntityBooleanDefault entityBooleanDefault) {
        return entityBooleanDefault == null? null: entityBooleanDefault.getEntityBooleanDefaultValue().clone();
    }

    public EntityBooleanDefaultValue getEntityBooleanDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityBooleanDefaultValueForUpdate(getEntityBooleanDefaultForUpdate(entityAttribute));
    }

    public EntityBooleanDefaultTransfer getEntityBooleanDefaultTransfer(UserVisit userVisit, EntityBooleanDefault entityBooleanDefault) {
        return entityBooleanDefaultTransferCache.getEntityBooleanDefaultTransfer(userVisit, entityBooleanDefault);
    }

    public void updateEntityBooleanDefaultFromValue(EntityBooleanDefaultValue entityBooleanDefaultValue, BasePK updatedBy) {
        if(entityBooleanDefaultValue.hasBeenModified()) {
            var entityBooleanDefault = entityBooleanDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityBooleanDefaultValue);
            var entityAttribute = entityBooleanDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanDefault.setThruTime(session.getStartTime());
                entityBooleanDefault.store();
            } else {
                entityBooleanDefault.remove();
            }

            entityBooleanDefault = entityBooleanDefaultFactory.create(entityAttribute,
                    entityBooleanDefaultValue.getBooleanAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityBooleanDefault(EntityBooleanDefault entityBooleanDefault, BasePK deletedBy) {
        var entityAttribute = entityBooleanDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanDefault.setThruTime(session.getStartTime());
        } else {
            entityBooleanDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityBooleanDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityBooleanDefault = getEntityBooleanDefaultForUpdate(entityAttribute);

        if(entityBooleanDefault != null) {
            deleteEntityBooleanDefault(entityBooleanDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityBooleanAttributeFactory entityBooleanAttributeFactory;
    
    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        return createEntityBooleanAttribute(entityAttribute.getPrimaryKey(), entityInstance, booleanAttribute,
                createdBy);
    }

    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        var entityBooleanAttribute = entityBooleanAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), booleanAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityBooleanAttribute;
    }

    public long countEntityBooleanAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitybooleanattributes
                    WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityBooleanAttributeHistoryQueries;

    static {
        getEntityBooleanAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitybooleanattributes
                WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ?
                ORDER BY enbla_thrutime
                _LIMIT_
                """);
    }

    public List<EntityBooleanAttribute> getEntityBooleanAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityBooleanAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityBooleanAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }
    
    private EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityBooleanAttribute entityBooleanAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleanattributes " +
                        "WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ? AND enbla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleanattributes " +
                        "WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ? AND enbla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityBooleanAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityBooleanAttribute = entityBooleanAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttribute;
    }
    
    public EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityBooleanAttribute getEntityBooleanAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityBooleanAttributeValue getEntityBooleanAttributeValueForUpdate(EntityBooleanAttribute entityBooleanAttribute) {
        return entityBooleanAttribute == null? null: entityBooleanAttribute.getEntityBooleanAttributeValue().clone();
    }
    
    public EntityBooleanAttributeValue getEntityBooleanAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttributeValueForUpdate(getEntityBooleanAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityBooleanAttribute> entityBooleanAttributes;
        
        try {
            var ps = entityBooleanAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_ena_entityattributeid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = entityBooleanAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBooleanAttribute> entityBooleanAttributes;
        
        try {
            var ps = entityBooleanAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_eni_entityinstanceid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = entityBooleanAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public EntityBooleanAttributeTransfer getEntityBooleanAttributeTransfer(UserVisit userVisit, EntityBooleanAttribute entityBooleanAttribute, EntityInstance entityInstance) {
        return entityBooleanAttributeTransferCache.getEntityBooleanAttributeTransfer(userVisit, entityBooleanAttribute, entityInstance);
    }
    
    public void updateEntityBooleanAttributeFromValue(EntityBooleanAttributeValue entityBooleanAttributeValue, BasePK updatedBy) {
        if(entityBooleanAttributeValue.hasBeenModified()) {
            var entityBooleanAttribute = entityBooleanAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityBooleanAttributeValue);
            var entityAttribute = entityBooleanAttribute.getEntityAttribute();
            var entityInstance = entityBooleanAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanAttribute.setThruTime(session.getStartTime());
                entityBooleanAttribute.store();
            } else {
                entityBooleanAttribute.remove();
            }
            
            entityBooleanAttributeFactory.create(entityAttribute, entityInstance, entityBooleanAttributeValue.getBooleanAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBooleanAttribute(EntityBooleanAttribute entityBooleanAttribute, BasePK deletedBy) {
        var entityAttribute = entityBooleanAttribute.getEntityAttribute();
        var entityInstance = entityBooleanAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanAttribute.setThruTime(session.getStartTime());
        } else {
            entityBooleanAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityBooleanAttributes(List<EntityBooleanAttribute> entityBooleanAttributes, BasePK deletedBy) {
        entityBooleanAttributes.forEach((entityBooleanAttribute) -> 
                deleteEntityBooleanAttribute(entityBooleanAttribute, deletedBy)
        );
    }
    
    public void deleteEntityBooleanAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityBooleanAttributes(getEntityBooleanAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityBooleanAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityBooleanAttributes(getEntityBooleanAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityDateDefaultFactory entityDateDefaultFactory;
    
    public EntityDateDefault createEntityDateDefault(EntityAttribute entityAttribute, Integer dateAttribute,
            BasePK createdBy) {
        var entityDateDefault = entityDateDefaultFactory.create(entityAttribute,
                dateAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityDateDefault;
    }

    public long countEntityDateDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitydatedefaults
                    WHERE enddef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityDateDefaultHistoryQueries;

    static {
        getEntityDateDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitydatedefaults
                WHERE enddef_ena_entityattributeid = ?
                ORDER BY enddef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityDateDefault> getEntityDateDefaultHistory(EntityAttribute entityAttribute) {
        return entityDateDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityDateDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityDateDefault getEntityDateDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityDateDefault entityDateDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydatedefaults " +
                        "WHERE enddef_ena_entityattributeid = ? AND enddef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydatedefaults " +
                        "WHERE enddef_ena_entityattributeid = ? AND enddef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityDateDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityDateDefault = entityDateDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityDateDefault;
    }

    public EntityDateDefault getEntityDateDefault(EntityAttribute entityAttribute) {
        return getEntityDateDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityDateDefault getEntityDateDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityDateDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityDateDefaultValue getEntityDateDefaultValueForUpdate(EntityDateDefault entityDateDefault) {
        return entityDateDefault == null? null: entityDateDefault.getEntityDateDefaultValue().clone();
    }

    public EntityDateDefaultValue getEntityDateDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityDateDefaultValueForUpdate(getEntityDateDefaultForUpdate(entityAttribute));
    }

    public EntityDateDefaultTransfer getEntityDateDefaultTransfer(UserVisit userVisit, EntityDateDefault entityDateDefault) {
        return entityDateDefaultTransferCache.getEntityDateDefaultTransfer(userVisit, entityDateDefault);
    }

    public void updateEntityDateDefaultFromValue(EntityDateDefaultValue entityDateDefaultValue, BasePK updatedBy) {
        if(entityDateDefaultValue.hasBeenModified()) {
            var entityDateDefault = entityDateDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityDateDefaultValue);
            var entityAttribute = entityDateDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateDefault.setThruTime(session.getStartTime());
                entityDateDefault.store();
            } else {
                entityDateDefault.remove();
            }

            entityDateDefault = entityDateDefaultFactory.create(entityAttribute,
                    entityDateDefaultValue.getDateAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityDateDefault(EntityDateDefault entityDateDefault, BasePK deletedBy) {
        var entityAttribute = entityDateDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateDefault.setThruTime(session.getStartTime());
        } else {
            entityDateDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityDateDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityDateDefault = getEntityDateDefaultForUpdate(entityAttribute);

        if(entityDateDefault != null) {
            deleteEntityDateDefault(entityDateDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityDateAttributeFactory entityDateAttributeFactory;
    
    public EntityDateAttribute createEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        return createEntityDateAttribute(entityAttribute.getPrimaryKey(), entityInstance, dateAttribute,
                createdBy);
    }

    public EntityDateAttribute createEntityDateAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        var entityDateAttribute = entityDateAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), dateAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityDateAttribute;
    }

    public long countEntityDateAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitydateattributes
                    WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityDateAttributeHistoryQueries;

    static {
        getEntityDateAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitydateattributes
                WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ?
                ORDER BY enda_thrutime
                _LIMIT_
                """);
    }

    public List<EntityDateAttribute> getEntityDateAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityDateAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityDateAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityDateAttribute getEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityDateAttribute entityDateAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydateattributes " +
                        "WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ? AND enda_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydateattributes " +
                        "WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ? AND enda_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityDateAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityDateAttribute = entityDateAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttribute;
    }
    
    public EntityDateAttribute getEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityDateAttribute getEntityDateAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityDateAttributeValue getEntityDateAttributeValueForUpdate(EntityDateAttribute entityDateAttribute) {
        return entityDateAttribute == null? null: entityDateAttribute.getEntityDateAttributeValue().clone();
    }
    
    public EntityDateAttributeValue getEntityDateAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttributeValueForUpdate(getEntityDateAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityDateAttribute> entityDateAttributes;
        
        try {
            var ps = entityDateAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_ena_entityattributeid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = entityDateAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityDateAttribute> entityDateAttributes;
        
        try {
            var ps = entityDateAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_eni_entityinstanceid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = entityDateAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public EntityDateAttributeTransfer getEntityDateAttributeTransfer(UserVisit userVisit, EntityDateAttribute entityDateAttribute, EntityInstance entityInstance) {
        return entityDateAttributeTransferCache.getEntityDateAttributeTransfer(userVisit, entityDateAttribute, entityInstance);
    }
    
    public void updateEntityDateAttributeFromValue(EntityDateAttributeValue entityDateAttributeValue, BasePK updatedBy) {
        if(entityDateAttributeValue.hasBeenModified()) {
            var entityDateAttribute = entityDateAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityDateAttributeValue);
            var entityAttribute = entityDateAttribute.getEntityAttribute();
            var entityInstance = entityDateAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateAttribute.setThruTime(session.getStartTime());
                entityDateAttribute.store();
            } else {
                entityDateAttribute.remove();
            }
            
            entityDateAttributeFactory.create(entityAttribute, entityInstance, entityDateAttributeValue.getDateAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityDateAttribute(EntityDateAttribute entityDateAttribute, BasePK deletedBy) {
        var entityAttribute = entityDateAttribute.getEntityAttribute();
        var entityInstance = entityDateAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateAttribute.setThruTime(session.getStartTime());
        } else {
            entityDateAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityDateAttributes(List<EntityDateAttribute> entityDateAttributes, BasePK deletedBy) {
        entityDateAttributes.forEach((entityDateAttribute) -> 
                deleteEntityDateAttribute(entityDateAttribute, deletedBy)
        );
    }
    
    public void deleteEntityDateAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityDateAttributes(getEntityDateAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityDateAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityDateAttributes(getEntityDateAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityIntegerDefaultFactory entityIntegerDefaultFactory;
    
    public EntityIntegerDefault createEntityIntegerDefault(EntityAttribute entityAttribute, Integer integerAttribute,
            BasePK createdBy) {
        var entityIntegerDefault = entityIntegerDefaultFactory.create(entityAttribute,
                integerAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityIntegerDefault;
    }

    public long countEntityIntegerDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityintegerdefaults
                    WHERE enidef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityIntegerDefaultHistoryQueries;

    static {
        getEntityIntegerDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityintegerdefaults
                WHERE enidef_ena_entityattributeid = ?
                ORDER BY enidef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityIntegerDefault> getEntityIntegerDefaultHistory(EntityAttribute entityAttribute) {
        return entityIntegerDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityIntegerDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityIntegerDefault getEntityIntegerDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityIntegerDefault entityIntegerDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerdefaults " +
                        "WHERE enidef_ena_entityattributeid = ? AND enidef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerdefaults " +
                        "WHERE enidef_ena_entityattributeid = ? AND enidef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityIntegerDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityIntegerDefault = entityIntegerDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityIntegerDefault;
    }

    public EntityIntegerDefault getEntityIntegerDefault(EntityAttribute entityAttribute) {
        return getEntityIntegerDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityIntegerDefault getEntityIntegerDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityIntegerDefaultValue getEntityIntegerDefaultValueForUpdate(EntityIntegerDefault entityIntegerDefault) {
        return entityIntegerDefault == null? null: entityIntegerDefault.getEntityIntegerDefaultValue().clone();
    }

    public EntityIntegerDefaultValue getEntityIntegerDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerDefaultValueForUpdate(getEntityIntegerDefaultForUpdate(entityAttribute));
    }

    public EntityIntegerDefaultTransfer getEntityIntegerDefaultTransfer(UserVisit userVisit, EntityIntegerDefault entityIntegerDefault) {
        return entityIntegerDefaultTransferCache.getEntityIntegerDefaultTransfer(userVisit, entityIntegerDefault);
    }

    public void updateEntityIntegerDefaultFromValue(EntityIntegerDefaultValue entityIntegerDefaultValue, BasePK updatedBy) {
        if(entityIntegerDefaultValue.hasBeenModified()) {
            var entityIntegerDefault = entityIntegerDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityIntegerDefaultValue);
            var entityAttribute = entityIntegerDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerDefault.setThruTime(session.getStartTime());
                entityIntegerDefault.store();
            } else {
                entityIntegerDefault.remove();
            }

            entityIntegerDefault = entityIntegerDefaultFactory.create(entityAttribute,
                    entityIntegerDefaultValue.getIntegerAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityIntegerDefault(EntityIntegerDefault entityIntegerDefault, BasePK deletedBy) {
        var entityAttribute = entityIntegerDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerDefault.setThruTime(session.getStartTime());
        } else {
            entityIntegerDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityIntegerDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityIntegerDefault = getEntityIntegerDefaultForUpdate(entityAttribute);

        if(entityIntegerDefault != null) {
            deleteEntityIntegerDefault(entityIntegerDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityIntegerAttributeFactory entityIntegerAttributeFactory;
    
    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        return createEntityIntegerAttribute(entityAttribute.getPrimaryKey(), entityInstance, integerAttribute,
                createdBy);
    }

    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        var entityIntegerAttribute = entityIntegerAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), integerAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityIntegerAttribute;
    }
    
    public long countEntityIntegerAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityintegerattributes
                    WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityIntegerAttributeHistoryQueries;

    static {
        getEntityIntegerAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityintegerattributes
                WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ?
                ORDER BY enia_thrutime
                _LIMIT_
                """);
    }

    public List<EntityIntegerAttribute> getEntityIntegerAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityIntegerAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityIntegerAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityIntegerAttribute entityIntegerAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerattributes " +
                        "WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ? AND enia_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerattributes " +
                        "WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ? AND enia_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityIntegerAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerAttribute = entityIntegerAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttribute;
    }
    
    public EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerAttribute getEntityIntegerAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerAttributeValue getEntityIntegerAttributeValueForUpdate(EntityIntegerAttribute entityIntegerAttribute) {
        return entityIntegerAttribute == null? null: entityIntegerAttribute.getEntityIntegerAttributeValue().clone();
    }
    
    public EntityIntegerAttributeValue getEntityIntegerAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttributeValueForUpdate(getEntityIntegerAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityIntegerAttribute> entityIntegerAttributes;
        
        try {
            var ps = entityIntegerAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_ena_entityattributeid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = entityIntegerAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityIntegerAttribute> entityIntegerAttributes;
        
        try {
            var ps = entityIntegerAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_eni_entityinstanceid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = entityIntegerAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public EntityIntegerAttributeTransfer getEntityIntegerAttributeTransfer(UserVisit userVisit, EntityIntegerAttribute entityIntegerAttribute, EntityInstance entityInstance) {
        return entityIntegerAttributeTransferCache.getEntityIntegerAttributeTransfer(userVisit, entityIntegerAttribute, entityInstance);
    }
    
    public void updateEntityIntegerAttributeFromValue(EntityIntegerAttributeValue entityIntegerAttributeValue, BasePK updatedBy) {
        if(entityIntegerAttributeValue.hasBeenModified()) {
            var entityIntegerAttribute = entityIntegerAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityIntegerAttributeValue);
            var entityAttribute = entityIntegerAttribute.getEntityAttribute();
            var entityInstance = entityIntegerAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerAttribute.setThruTime(session.getStartTime());
                entityIntegerAttribute.store();
            } else {
                entityIntegerAttribute.remove();
            }
            
            entityIntegerAttributeFactory.create(entityAttribute, entityInstance, entityIntegerAttributeValue.getIntegerAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    public void deleteEntityIntegerAttribute(EntityIntegerAttribute entityIntegerAttribute, BasePK deletedBy) {
        var entityAttribute = entityIntegerAttribute.getEntityAttribute();
        var entityInstance = entityIntegerAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerAttribute.setThruTime(session.getStartTime());
        } else {
            entityIntegerAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityIntegerAttributes(List<EntityIntegerAttribute> entityIntegerAttributes, BasePK deletedBy) {
        entityIntegerAttributes.forEach((entityIntegerAttribute) -> 
                deleteEntityIntegerAttribute(entityIntegerAttribute, deletedBy)
        );
    }
    
    public void deleteEntityIntegerAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityIntegerAttributes(getEntityIntegerAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityIntegerAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityIntegerAttributes(getEntityIntegerAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityListItemDefaultFactory entityListItemDefaultFactory;
    
    public EntityListItemDefault createEntityListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemDefault = entityListItemDefaultFactory.create(
                entityAttribute, entityListItem, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityListItemDefault;
    }

    public long countEntityListItemDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylistitemdefaults
                    WHERE eladef_ena_entityattributeid = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityListItemDefaultHistoryQueries;

    static {
        getEntityListItemDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylistitemdefaults
                WHERE eladef_ena_entityattributeid = ?
                ORDER BY eladef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityListItemDefault> getEntityListItemDefaultHistory(EntityAttribute entityAttribute) {
        return entityListItemDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityListItemDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityListItemDefault getEntityListItemDefault(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        EntityListItemDefault entityListItemDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdefaults " +
                        "WHERE eladef_ena_entityattributeid = ? AND eladef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdefaults " +
                        "WHERE eladef_ena_entityattributeid = ? AND eladef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityListItemDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityListItemDefault = entityListItemDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityListItemDefault;
    }

    public EntityListItemDefault getEntityListItemDefault(EntityAttribute entityAttribute) {
        return getEntityListItemDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityListItemDefault getEntityListItemDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItemDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityListItemDefaultValue getEntityListItemDefaultValueForUpdate(EntityListItemDefault entityListItemDefault) {
        return entityListItemDefault == null? null: entityListItemDefault.getEntityListItemDefaultValue().clone();
    }

    public EntityListItemDefaultValue getEntityListItemDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItemDefaultValueForUpdate(getEntityListItemDefaultForUpdate(entityAttribute));
    }

    private EntityListItemDefault getEntityListItemDefaultByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        EntityListItemDefault entityListItemDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemdefaults "
                        + "WHERE eladef_eli_entitylistitemid = ? AND eladef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemdefaults "
                        + "WHERE eladef_eli_entitylistitemid = ? AND eladef_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = entityListItemDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityListItemDefault = entityListItemDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityListItemDefault;
    }

    public EntityListItemDefault getEntityListItemDefaultByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemDefaultByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }

    public EntityListItemDefault getEntityListItemDefaultByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemDefaultByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }

    public EntityListItemDefaultTransfer getEntityListItemDefaultTransfer(UserVisit userVisit, EntityListItemDefault entityListItemDefault) {
        return entityListItemDefaultTransferCache.getEntityListItemDefaultTransfer(userVisit, entityListItemDefault);
    }

    public void updateEntityListItemDefaultFromValue(EntityListItemDefaultValue entityListItemDefaultValue, BasePK updatedBy) {
        if(entityListItemDefaultValue.hasBeenModified()) {
            var entityListItemDefault = entityListItemDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityListItemDefaultValue);
            var entityAttribute = entityListItemDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemDefault.setThruTime(session.getStartTime());
                entityListItemDefault.store();
            } else {
                entityListItemDefault.remove();
            }

            entityListItemDefault = entityListItemDefaultFactory.create(entityAttribute.getPrimaryKey(),
                    entityListItemDefaultValue.getEntityListItemPK(), session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.DELETE, updatedBy);
        }
    }

    public void deleteEntityListItemDefault(EntityListItemDefault entityListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemDefault.setThruTime(session.getStartTime());
        } else {
            entityListItemDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityListItemDefaultByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        var deleteEntityListItemDefault = getEntityListItemDefaultByEntityListItemForUpdate(entityListItem);

        if(deleteEntityListItemDefault != null) {
            deleteEntityListItemDefault(deleteEntityListItemDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityListItemAttributeFactory entityListItemAttributeFactory;
    
    public EntityListItemAttribute createEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityListItemAttribute createEntityListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = entityListItemAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityListItemAttribute;
    }

    public long countEntityListItemAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylistitemattributes
                    WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityListItemAttributeHistoryQueries;

    static {
        getEntityListItemAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylistitemattributes
                WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ?
                ORDER BY ela_thrutime
                _LIMIT_
                """);
    }

    public List<EntityListItemAttribute> getEntityListItemAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityListItemAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityListItemAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityListItemAttribute getEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityListItemAttribute entityListItemAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemattributes " +
                        "WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ? AND ela_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemattributes " +
                        "WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ? AND ela_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityListItemAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemAttribute = entityListItemAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttribute;
    }
    
    public EntityListItemAttribute getEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityListItemAttribute getEntityListItemAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemAttributeValue getEntityListItemAttributeValueForUpdate(EntityListItemAttribute entityListItemAttribute) {
        return entityListItemAttribute == null? null: entityListItemAttribute.getEntityListItemAttributeValue().clone();
    }
    
    public EntityListItemAttributeValue getEntityListItemAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttributeValueForUpdate(getEntityListItemAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    private List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityListItemAttribute> entityListItemAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemattributes, entityinstances "
                        + "WHERE ela_eli_entitylistitemid = ? AND ela_thrutime = ? "
                        + "AND ela_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemattributes "
                        + "WHERE ela_eli_entitylistitemid = ? AND ela_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = entityListItemAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = entityListItemAttributeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttributes;
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityListItemAttribute> entityListItemAttributes;
        
        try {
            var ps = entityListItemAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylistitemattributes " +
                    "WHERE ela_eni_entityinstanceid = ? AND ela_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = entityListItemAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttributes;
    }
    
    public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(UserVisit userVisit, EntityListItemAttribute entityListItemAttribute, EntityInstance entityInstance) {
        return entityListItemAttributeTransferCache.getEntityListItemAttributeTransfer(userVisit, entityListItemAttribute, entityInstance);
    }
    
    public void updateEntityListItemAttributeFromValue(EntityListItemAttributeValue entityListItemAttributeValue, BasePK updatedBy) {
        if(entityListItemAttributeValue.hasBeenModified()) {
            var entityListItemAttribute = entityListItemAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityListItemAttributeValue);
            var entityAttribute = entityListItemAttribute.getEntityAttribute();
            var entityInstance = entityListItemAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemAttribute.setThruTime(session.getStartTime());
                entityListItemAttribute.store();
            } else {
                entityListItemAttribute.remove();
            }
            
            entityListItemAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), entityListItemAttributeValue.getEntityListItemPK(),
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemAttribute(EntityListItemAttribute entityListItemAttribute, BasePK deletedBy) {
        var entityAttribute = entityListItemAttribute.getEntityAttribute();
        var entityInstance = entityListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemAttribute.setThruTime(session.getStartTime());
        } else {
            entityListItemAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityListItemAttributes(List<EntityListItemAttribute> entityListItemAttributes, BasePK deletedBy) {
        entityListItemAttributes.forEach((entityListItemAttribute) -> 
                deleteEntityListItemAttribute(entityListItemAttribute, deletedBy)
        );
    }
    
    public void deleteEntityListItemAttributesByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityListItemAttributes(getEntityListItemAttributesByEntityListItemForUpdate(entityListItem), deletedBy);
    }
    
    public void deleteEntityListItemAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityListItemAttributes(getEntityListItemAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityLongDefaultFactory entityLongDefaultFactory;
    
    public EntityLongDefault createEntityLongDefault(EntityAttribute entityAttribute, Long longAttribute,
            BasePK createdBy) {
        var entityLongDefault = entityLongDefaultFactory.create(entityAttribute,
                longAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityLongDefault;
    }

    public long countEntityLongDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylongdefaults
                    WHERE enldef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityLongDefaultHistoryQueries;

    static {
        getEntityLongDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylongdefaults
                WHERE enldef_ena_entityattributeid = ?
                ORDER BY enldef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityLongDefault> getEntityLongDefaultHistory(EntityAttribute entityAttribute) {
        return entityLongDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityLongDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityLongDefault getEntityLongDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityLongDefault entityLongDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongdefaults " +
                        "WHERE enldef_ena_entityattributeid = ? AND enldef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongdefaults " +
                        "WHERE enldef_ena_entityattributeid = ? AND enldef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityLongDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityLongDefault = entityLongDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityLongDefault;
    }

    public EntityLongDefault getEntityLongDefault(EntityAttribute entityAttribute) {
        return getEntityLongDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityLongDefault getEntityLongDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityLongDefaultValue getEntityLongDefaultValueForUpdate(EntityLongDefault entityLongDefault) {
        return entityLongDefault == null? null: entityLongDefault.getEntityLongDefaultValue().clone();
    }

    public EntityLongDefaultValue getEntityLongDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongDefaultValueForUpdate(getEntityLongDefaultForUpdate(entityAttribute));
    }

    public EntityLongDefaultTransfer getEntityLongDefaultTransfer(UserVisit userVisit, EntityLongDefault entityLongDefault) {
        return entityLongDefaultTransferCache.getEntityLongDefaultTransfer(userVisit, entityLongDefault);
    }

    public void updateEntityLongDefaultFromValue(EntityLongDefaultValue entityLongDefaultValue, BasePK updatedBy) {
        if(entityLongDefaultValue.hasBeenModified()) {
            var entityLongDefault = entityLongDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityLongDefaultValue);
            var entityAttribute = entityLongDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongDefault.setThruTime(session.getStartTime());
                entityLongDefault.store();
            } else {
                entityLongDefault.remove();
            }

            entityLongDefault = entityLongDefaultFactory.create(entityAttribute,
                    entityLongDefaultValue.getLongAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityLongDefault(EntityLongDefault entityLongDefault, BasePK deletedBy) {
        var entityAttribute = entityLongDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongDefault.setThruTime(session.getStartTime());
        } else {
            entityLongDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityLongDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityLongDefault = getEntityLongDefaultForUpdate(entityAttribute);

        if(entityLongDefault != null) {
            deleteEntityLongDefault(entityLongDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityLongAttributeFactory entityLongAttributeFactory;
    
    public EntityLongAttribute createEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        return createEntityLongAttribute(entityAttribute.getPrimaryKey(), entityInstance, longAttribute,
                createdBy);
    }

    public EntityLongAttribute createEntityLongAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        var entityLongAttribute = entityLongAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), longAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityLongAttribute;
    }

    public long countEntityLongAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylongattributes
                    WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityLongAttributeHistoryQueries;

    static {
        getEntityLongAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylongattributes
                WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ?
                ORDER BY enla_thrutime
                _LIMIT_
                """);
    }

    public List<EntityLongAttribute> getEntityLongAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityLongAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityLongAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityLongAttribute getEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityLongAttribute entityLongAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongattributes " +
                        "WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ? AND enla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongattributes " +
                        "WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ? AND enla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityLongAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongAttribute = entityLongAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttribute;
    }
    
    public EntityLongAttribute getEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityLongAttribute getEntityLongAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityLongAttributeValue getEntityLongAttributeValueForUpdate(EntityLongAttribute entityLongAttribute) {
        return entityLongAttribute == null? null: entityLongAttribute.getEntityLongAttributeValue().clone();
    }
    
    public EntityLongAttributeValue getEntityLongAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttributeValueForUpdate(getEntityLongAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityLongAttribute> entityLongAttributes;
        
        try {
            var ps = entityLongAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_ena_entityattributeid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = entityLongAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityLongAttribute> entityLongAttributes;
        
        try {
            var ps = entityLongAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_eni_entityinstanceid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = entityLongAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public EntityLongAttributeTransfer getEntityLongAttributeTransfer(UserVisit userVisit, EntityLongAttribute entityLongAttribute, EntityInstance entityInstance) {
        return entityLongAttributeTransferCache.getEntityLongAttributeTransfer(userVisit, entityLongAttribute, entityInstance);
    }
    
    public void updateEntityLongAttributeFromValue(EntityLongAttributeValue entityLongAttributeValue, BasePK updatedBy) {
        if(entityLongAttributeValue.hasBeenModified()) {
            var entityLongAttribute = entityLongAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityLongAttributeValue);
            var entityAttribute = entityLongAttribute.getEntityAttribute();
            var entityInstance = entityLongAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongAttribute.setThruTime(session.getStartTime());
                entityLongAttribute.store();
            } else {
                entityLongAttribute.remove();
            }
            
            entityLongAttributeFactory.create(entityAttribute, entityInstance, entityLongAttributeValue.getLongAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongAttribute(EntityLongAttribute entityLongAttribute, BasePK deletedBy) {
        var entityAttribute = entityLongAttribute.getEntityAttribute();
        var entityInstance = entityLongAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongAttribute.setThruTime(session.getStartTime());
        } else {
            entityLongAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityLongAttributes(List<EntityLongAttribute> entityLongAttributes, BasePK deletedBy) {
        entityLongAttributes.forEach((entityLongAttribute) -> 
                deleteEntityLongAttribute(entityLongAttribute, deletedBy)
        );
    }
    
    public void deleteEntityLongAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityLongAttributes(getEntityLongAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityLongAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityLongAttributes(getEntityLongAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityMultipleListItemDefaultFactory entityMultipleListItemDefaultFactory;
    
    public EntityMultipleListItemDefault createEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityMultipleListItemDefault = entityMultipleListItemDefaultFactory.create(
                entityAttribute, entityListItem, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityMultipleListItemDefault;
    }

    public long countEntityMultipleListItemDefaults(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitymultiplelistitemdefaults
                    WHERE emlidef_ena_entityattributeid = ? AND emlidef_thrutime = ?
                    """, entityAttribute, Session.MAX_TIME);
    }

    public long countEntityMultipleListItemDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitymultiplelistitemdefaults
                    WHERE emlidef_ena_entityattributeid = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityMultipleListItemDefaultHistoryQueries;

    static {
        getEntityMultipleListItemDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitymultiplelistitemdefaults
                WHERE emlidef_ena_entityattributeid = ?
                ORDER BY emlidef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultHistory(EntityAttribute entityAttribute) {
        return entityMultipleListItemDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityMultipleListItemDefaultHistoryQueries,
                entityAttribute);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaults(EntityAttribute entityAttribute) {
        List<EntityMultipleListItemDefault> entityMultipleListItemDefaults;

        try {
            var ps = entityMultipleListItemDefaultFactory.prepareStatement(
                    "SELECT _ALL_ "
                            + "FROM entitymultiplelistitemdefaults, entitylistitems, entitylistitemdetails "
                            + "WHERE emlidef_ena_entityattributeid = ? AND emlidef_thrutime = ? "
                            + "AND emlidef_eli_entitylistitemid = eli_entitylistitemid AND eli_lastdetailid = elidt_entitylistitemdetailid "
                            + "ORDER BY elidt_sortorder, elidt_entitylistitemname");

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityMultipleListItemDefaults = entityMultipleListItemDefaultFactory.getEntitiesFromQuery(
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityMultipleListItemDefaults;
    }

    private EntityMultipleListItemDefault getEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, EntityPermission entityPermission) {
        EntityMultipleListItemDefault entityMultipleListItemDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemdefaults " +
                        "WHERE emlidef_ena_entityattributeid = ? AND emlidef_eli_entitylistitemid = ? " +
                        "AND emlidef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemdefaults " +
                        "WHERE emlidef_ena_entityattributeid = ? AND emlidef_eli_entitylistitemid = ? " +
                        "AND emlidef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityMultipleListItemDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityMultipleListItemDefault = entityMultipleListItemDefaultFactory.getEntityFromQuery(
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityMultipleListItemDefault;
    }

    public EntityMultipleListItemDefault getEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem) {
        return getEntityMultipleListItemDefault(entityAttribute, entityListItem, EntityPermission.READ_ONLY);
    }

    public EntityMultipleListItemDefault getEntityMultipleListItemDefaultForUpdate(EntityAttribute entityAttribute,
            EntityListItem entityListItem) {
        return getEntityMultipleListItemDefault(entityAttribute, entityListItem, EntityPermission.READ_WRITE);
    }

    private List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityMultipleListItemDefault> entityMultipleListItemDefaults;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemdefaults "
                        + "WHERE emlidef_eli_entitylistitemid = ? AND emlidef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemdefaults "
                        + "WHERE emlidef_eli_entitylistitemid = ? AND emlidef_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = entityMultipleListItemDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityMultipleListItemDefaults = entityMultipleListItemDefaultFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityMultipleListItemDefaults;
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItem(EntityListItem entityListItem) {
        return getEntityMultipleListItemDefaultsByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityMultipleListItemDefaultsByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }

    public EntityMultipleListItemDefaultTransfer getEntityMultipleListItemDefaultTransfer(UserVisit userVisit, EntityMultipleListItemDefault entityMultipleListItemDefault) {
        return entityMultipleListItemDefaultTransferCache.getEntityMultipleListItemDefaultTransfer(userVisit, entityMultipleListItemDefault);
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, Collection<EntityMultipleListItemDefault> entityMultipleListItemDefaults) {
        List<EntityMultipleListItemDefaultTransfer> entityMultipleListItemDefaultTransfers = new ArrayList<>(entityMultipleListItemDefaults.size());

        entityMultipleListItemDefaults.forEach((entityMultipleListItemDefault) ->
                entityMultipleListItemDefaultTransfers.add(entityMultipleListItemDefaultTransferCache.getEntityMultipleListItemDefaultTransfer(userVisit, entityMultipleListItemDefault))
        );

        return entityMultipleListItemDefaultTransfers;
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, EntityAttribute entityAttribute) {
        return getEntityMultipleListItemDefaultTransfers(userVisit, getEntityMultipleListItemDefaults(entityAttribute));
    }

    public void deleteEntityMultipleListItemDefault(EntityMultipleListItemDefault entityMultipleListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityMultipleListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityMultipleListItemDefault.setThruTime(session.getStartTime());
        } else {
            entityMultipleListItemDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityMultipleListItemDefaults(List<EntityMultipleListItemDefault> entityMultipleListItemDefaults, BasePK deletedBy) {
        entityMultipleListItemDefaults.forEach((entityMultipleListItemDefault) ->
                deleteEntityMultipleListItemDefault(entityMultipleListItemDefault, deletedBy)
        );
    }

    public void deleteEntityMultipleListItemDefaultsByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityMultipleListItemDefaults(getEntityMultipleListItemDefaultsByEntityListItemForUpdate(entityListItem), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityMultipleListItemAttributeFactory entityMultipleListItemAttributeFactory;
    
    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityMultipleListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = entityMultipleListItemAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityListItemAttribute;
    }

    public long countEntityMultipleListItemAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitymultiplelistitemattributes
                    WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_thrutime = ?
                    """, entityAttribute, entityInstance, Session.MAX_TIME);
    }

    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes;
        
        try {
            var ps = entityMultipleListItemAttributeFactory.prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM entitymultiplelistitemattributes, entitylistitems, entitylistitemdetails "
                    + "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? "
                    + "AND emlia_eli_entitylistitemid = eli_entitylistitemid AND eli_lastdetailid = elidt_entitylistitemdetailid "
                    + "ORDER BY elidt_sortorder, elidt_entitylistitemname");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = entityMultipleListItemAttributeFactory.getEntitiesFromQuery(
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    private EntityMultipleListItemAttribute getEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, EntityPermission entityPermission) {
        EntityMultipleListItemAttribute entityMultipleListItemAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemattributes " +
                        "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_eli_entitylistitemid = ? " +
                        "AND emlia_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemattributes " +
                        "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_eli_entitylistitemid = ? " +
                        "AND emlia_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityMultipleListItemAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityMultipleListItemAttribute = entityMultipleListItemAttributeFactory.getEntityFromQuery(
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttribute;
    }
    
    public EntityMultipleListItemAttribute getEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem) {
        return getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, EntityPermission.READ_ONLY);
    }
    
    public EntityMultipleListItemAttribute getEntityMultipleListItemAttributeForUpdate(EntityAttribute entityAttribute,
            EntityInstance entityInstance, EntityListItem entityListItem) {
        return getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, EntityPermission.READ_WRITE);
    }
    
    private List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemattributes, entityinstances "
                        + "WHERE emlia_eli_entitylistitemid = ? AND emlia_thrutime = ? "
                        + "AND emlia_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemattributes "
                        + "WHERE emlia_eli_entitylistitemid = ? AND emlia_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = entityMultipleListItemAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = entityMultipleListItemAttributeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes;
        
        try {
            var ps = entityMultipleListItemAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitymultiplelistitemattributes " +
                    "WHERE emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = entityMultipleListItemAttributeFactory.getEntitiesFromQuery(
                    EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    public EntityMultipleListItemAttributeTransfer getEntityMultipleListItemAttributeTransfer(UserVisit userVisit, EntityMultipleListItemAttribute entityMultipleListItemAttribute, EntityInstance entityInstance) {
        return entityMultipleListItemAttributeTransferCache.getEntityMultipleListItemAttributeTransfer(userVisit, entityMultipleListItemAttribute, entityInstance);
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, Collection<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributeTransfers = new ArrayList<>(entityMultipleListItemAttributes.size());
        
        entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) ->
                entityMultipleListItemAttributeTransfers.add(entityMultipleListItemAttributeTransferCache.getEntityMultipleListItemAttributeTransfer(userVisit, entityMultipleListItemAttribute, entityInstance))
        );
        
        return entityMultipleListItemAttributeTransfers;
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityMultipleListItemAttributeTransfers(userVisit, getEntityMultipleListItemAttributes(entityAttribute, entityInstance), entityInstance);
    }
    
    public void deleteEntityMultipleListItemAttribute(EntityMultipleListItemAttribute entityMultipleListItemAttribute, BasePK deletedBy) {
        var entityAttribute = entityMultipleListItemAttribute.getEntityAttribute();
        var entityInstance = entityMultipleListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityMultipleListItemAttribute.setThruTime(session.getStartTime());
        } else {
            entityMultipleListItemAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityMultipleListItemAttributes(List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, BasePK deletedBy) {
        entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) -> 
                deleteEntityMultipleListItemAttribute(entityMultipleListItemAttribute, deletedBy)
        );
    }
    
    public void deleteEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityMultipleListItemAttributes(getEntityMultipleListItemAttributesByEntityListItemForUpdate(entityListItem), deletedBy);
    }
    
    public void deleteEntityMultipleListItemAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityMultipleListItemAttributes(getEntityMultipleListItemAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityNameAttributeFactory entityNameAttributeFactory;
    
    public EntityNameAttribute createEntityNameAttribute(EntityAttribute entityAttribute, String nameAttribute,
            EntityInstance entityInstance, BasePK createdBy) {
        var entityNameAttribute = entityNameAttributeFactory.create(entityAttribute,
                nameAttribute, entityInstance, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityNameAttribute;
    }

    public long countEntityNameAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitynameattributes
                    WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityNameAttributeHistoryQueries;

    static {
        getEntityNameAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitynameattributes
                WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ?
                ORDER BY enna_thrutime
                _LIMIT_
                """);
    }

    public List<EntityNameAttribute> getEntityNameAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityNameAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityNameAttribute getEntityNameAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityNameAttribute entityNameAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitynameattributes " +
                        "WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ? AND enna_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitynameattributes " +
                        "WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ? AND enna_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityNameAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttribute = entityNameAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttribute;
    }
    
    public EntityNameAttribute getEntityNameAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityNameAttribute getEntityNameAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityNameAttributeValue getEntityNameAttributeValueForUpdate(EntityNameAttribute entityNameAttribute) {
        return entityNameAttribute == null? null: entityNameAttribute.getEntityNameAttributeValue().clone();
    }
    
    public EntityNameAttributeValue getEntityNameAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttributeValueForUpdate(getEntityNameAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityNameAttribute> entityNameAttributes;
        
        try {
            var ps = entityNameAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityNameAttribute> entityNameAttributes;
        
        try {
            var ps = entityNameAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_eni_entityinstanceid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public EntityNameAttributeTransfer getEntityNameAttributeTransfer(UserVisit userVisit, EntityNameAttribute entityNameAttribute, EntityInstance entityInstance) {
        return entityNameAttributeTransferCache.getEntityNameAttributeTransfer(userVisit, entityNameAttribute, entityInstance);
    }
    
    public void updateEntityNameAttributeFromValue(EntityNameAttributeValue entityNameAttributeValue, BasePK updatedBy) {
        if(entityNameAttributeValue.hasBeenModified()) {
            var entityNameAttribute = entityNameAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityNameAttributeValue);
            var entityAttribute = entityNameAttribute.getEntityAttribute();
            var entityInstance = entityNameAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityNameAttribute.setThruTime(session.getStartTime());
                entityNameAttribute.store();
            } else {
                entityNameAttribute.remove();
            }
            
            entityNameAttributeFactory.create(entityAttribute, entityNameAttributeValue.getNameAttribute(), entityInstance, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityNameAttribute(EntityNameAttribute entityNameAttribute, BasePK deletedBy) {
        var entityAttribute = entityNameAttribute.getEntityAttribute();
        var entityInstance = entityNameAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityNameAttribute.setThruTime(session.getStartTime());
        } else {
            entityNameAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityNameAttributes(List<EntityNameAttribute> entityNameAttributes, BasePK deletedBy) {
        entityNameAttributes.forEach((entityNameAttribute) -> 
                deleteEntityNameAttribute(entityNameAttribute, deletedBy)
        );
    }
    
    public void deleteEntityNameAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityNameAttributes(getEntityNameAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityNameAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityNameAttributes(getEntityNameAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByName(EntityAttribute entityAttribute, String nameAttribute) {
        List<EntityNameAttribute> entityNameAttributes;
        
        try {
            var ps = entityNameAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_nameattribute = ? AND enna_thrutime = ?");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, nameAttribute);
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttributes = entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityStringDefaultFactory entityStringDefaultFactory;
    
    public EntityStringDefault createEntityStringDefault(EntityAttribute entityAttribute, Language language,
            String stringAttribute, BasePK createdBy) {
        var entityStringDefault = entityStringDefaultFactory.create(entityAttribute, language,
                stringAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityStringDefault;
    }

    public long countEntityStringDefaultHistory(EntityAttribute entityAttribute, Language language) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitystringdefaults
                    WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ?
                    """, entityAttribute, language);
    }

    private static final Map<EntityPermission, String> getEntityStringDefaultHistoryQueries;

    static {
        getEntityStringDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitystringdefaults
                WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ?
                ORDER BY ensdef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityStringDefault> getEntityStringDefaultHistory(EntityAttribute entityAttribute, Language language) {
        return entityStringDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityStringDefaultHistoryQueries,
                entityAttribute, language);
    }

    private EntityStringDefault getEntityStringDefault(EntityAttribute entityAttribute, Language language, EntityPermission entityPermission) {
        EntityStringDefault entityStringDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ? AND ensdef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ? AND ensdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityStringDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityStringDefault = entityStringDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityStringDefault;
    }

    public EntityStringDefault getEntityStringDefault(EntityAttribute entityAttribute, Language language) {
        return getEntityStringDefault(entityAttribute, language, EntityPermission.READ_ONLY);
    }

    public EntityStringDefault getEntityStringDefaultForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityStringDefault(entityAttribute, language, EntityPermission.READ_WRITE);
    }

    public EntityStringDefaultValue getEntityStringDefaultValueForUpdate(EntityStringDefault entityStringDefault) {
        return entityStringDefault == null? null: entityStringDefault.getEntityStringDefaultValue().clone();
    }

    public EntityStringDefaultValue getEntityStringDefaultValueForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityStringDefaultValueForUpdate(getEntityStringDefaultForUpdate(entityAttribute, language));
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttribute(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityStringDefault> entityStringDefaults;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "JOIN languages ON ensdef_lang_languageid = lang_languageid " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_thrutime = ? " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityStringDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityStringDefaults = entityStringDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityStringDefaults;
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityStringDefaultsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityStringDefaultsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }


    public EntityStringDefaultTransfer getEntityStringDefaultTransfer(UserVisit userVisit, EntityStringDefault entityStringDefault) {
        return entityStringDefaultTransferCache.getEntityStringDefaultTransfer(userVisit, entityStringDefault);
    }

    public void updateEntityStringDefaultFromValue(EntityStringDefaultValue entityStringDefaultValue, BasePK updatedBy) {
        if(entityStringDefaultValue.hasBeenModified()) {
            var entityStringDefault = entityStringDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityStringDefaultValue);
            var entityAttribute = entityStringDefault.getEntityAttribute();
            var language = entityStringDefault.getLanguage();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringDefault.setThruTime(session.getStartTime());
                entityStringDefault.store();
            } else {
                entityStringDefault.remove();
            }

            entityStringDefault = entityStringDefaultFactory.create(entityAttribute, language,
                    entityStringDefaultValue.getStringAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityStringDefault(EntityStringDefault entityStringDefault, BasePK deletedBy) {
        var entityAttribute = entityStringDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringDefault.setThruTime(session.getStartTime());
        } else {
            entityStringDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityStringDefaults(Collection<EntityStringDefault> entityStringDefaults, BasePK deletedBy) {
        entityStringDefaults.forEach((entityStringDefault) ->
                deleteEntityStringDefault(entityStringDefault, deletedBy));
    }

    public void deleteEntityStringDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityStringDefaults(getEntityStringDefaultsByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityStringAttributeFactory entityStringAttributeFactory;
    
    public EntityStringAttribute createEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        return createEntityStringAttribute(entityAttribute.getPrimaryKey(), entityInstance, language, stringAttribute,
                createdBy);
    }

    public EntityStringAttribute createEntityStringAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        var entityStringAttribute = entityStringAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), language.getPrimaryKey(), stringAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityStringAttribute;
    }
    
    public long countEntityStringAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitystringattributes
                    WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ?
                    """, entityAttribute, entityInstance, language);
    }

    private static final Map<EntityPermission, String> getEntityStringAttributeHistoryQueries;

    static {
        getEntityStringAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitystringattributes
                WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ?
                ORDER BY ensa_thrutime
                _LIMIT_
                """);
    }

    public List<EntityStringAttribute> getEntityStringAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language) {
        return entityStringAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityStringAttributeHistoryQueries,
                entityAttribute, entityInstance, language);
    }

    private EntityStringAttribute getEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityStringAttribute entityStringAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringattributes " +
                        "WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ? AND ensa_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringattributes " +
                        "WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ? AND ensa_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityStringAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityStringAttribute = entityStringAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttribute;
    }
    
    public EntityStringAttribute getEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityStringAttribute getEntityStringAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityStringAttribute getBestEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        var entityStringAttribute = getEntityStringAttribute(entityAttribute, entityInstance, language);
        
        if(entityStringAttribute == null && !language.getIsDefault()) {
            entityStringAttribute = getEntityStringAttribute(entityAttribute, entityInstance, partyControl.getDefaultLanguage());
        }
        
        return entityStringAttribute;
    }
    
    public EntityStringAttributeValue getEntityStringAttributeValueForUpdate(EntityStringAttribute entityStringAttribute) {
        return entityStringAttribute == null? null: entityStringAttribute.getEntityStringAttributeValue().clone();
    }
    
    public EntityStringAttributeValue getEntityStringAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttributeValueForUpdate(getEntityStringAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityStringAttribute> entityStringAttributes;
        
        try {
            var ps = entityStringAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_ena_entityattributeid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = entityStringAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityStringAttribute> entityStringAttributes;
        
        try {
            var ps = entityStringAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_eni_entityinstanceid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = entityStringAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public EntityStringAttributeTransfer getEntityStringAttributeTransfer(UserVisit userVisit, EntityStringAttribute entityStringAttribute, EntityInstance entityInstance) {
        return entityStringAttributeTransferCache.getEntityStringAttributeTransfer(userVisit, entityStringAttribute, entityInstance);
    }
    
    public void updateEntityStringAttributeFromValue(EntityStringAttributeValue entityStringAttributeValue, BasePK updatedBy) {
        if(entityStringAttributeValue.hasBeenModified()) {
            var entityStringAttribute = entityStringAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityStringAttributeValue);
            var entityAttribute = entityStringAttribute.getEntityAttribute();
            var entityInstance = entityStringAttribute.getEntityInstance();
            var language = entityStringAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringAttribute.setThruTime(session.getStartTime());
                entityStringAttribute.store();
            } else {
                entityStringAttribute.remove();
            }
            
            entityStringAttributeFactory.create(entityAttribute, entityInstance, language, entityStringAttributeValue.getStringAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityStringAttribute(EntityStringAttribute entityStringAttribute, BasePK deletedBy) {
        var entityAttribute = entityStringAttribute.getEntityAttribute();
        var entityInstance = entityStringAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringAttribute.setThruTime(session.getStartTime());
        } else {
            entityStringAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityStringAttributes(List<EntityStringAttribute> entityStringAttributes, BasePK deletedBy) {
        entityStringAttributes.forEach((entityStringAttribute) -> 
                deleteEntityStringAttribute(entityStringAttribute, deletedBy)
        );
    }
    
    public void deleteEntityStringAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityStringAttributes(getEntityStringAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityStringAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityStringAttributes(getEntityStringAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityGeoPointDefaultFactory entityGeoPointDefaultFactory;
    
    public EntityGeoPointDefault createEntityGeoPointDefault(EntityAttribute entityAttribute, Integer latitude,
            Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointDefault = entityGeoPointDefaultFactory.create(entityAttribute,
                latitude, longitude, elevation, altitude, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityGeoPointDefault;
    }

    public long countEntityGeoPointDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitygeopointdefaults
                    WHERE engeopntdef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityGeoPointDefaultHistoryQueries;

    static {
        getEntityGeoPointDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitygeopointdefaults
                WHERE engeopntdef_ena_entityattributeid = ?
                ORDER BY engeopntdef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityGeoPointDefault> getEntityGeoPointDefaultHistory(EntityAttribute entityAttribute) {
        return entityGeoPointDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityGeoPointDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityGeoPointDefault getEntityGeoPointDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityGeoPointDefault entityGeoPointDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointdefaults " +
                        "WHERE engeopntdef_ena_entityattributeid = ? AND engeopntdef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointdefaults " +
                        "WHERE engeopntdef_ena_entityattributeid = ? AND engeopntdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityGeoPointDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityGeoPointDefault = entityGeoPointDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityGeoPointDefault;
    }

    public EntityGeoPointDefault getEntityGeoPointDefault(EntityAttribute entityAttribute) {
        return getEntityGeoPointDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityGeoPointDefault getEntityGeoPointDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityGeoPointDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityGeoPointDefaultValue getEntityGeoPointDefaultValueForUpdate(EntityGeoPointDefault entityGeoPointDefault) {
        return entityGeoPointDefault == null? null: entityGeoPointDefault.getEntityGeoPointDefaultValue().clone();
    }

    public EntityGeoPointDefaultValue getEntityGeoPointDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityGeoPointDefaultValueForUpdate(getEntityGeoPointDefaultForUpdate(entityAttribute));
    }

    public EntityGeoPointDefaultTransfer getEntityGeoPointDefaultTransfer(UserVisit userVisit, EntityGeoPointDefault entityGeoPointDefault) {
        return entityGeoPointDefaultTransferCache.getEntityGeoPointDefaultTransfer(userVisit, entityGeoPointDefault);
    }

    public void updateEntityGeoPointDefaultFromValue(EntityGeoPointDefaultValue entityGeoPointDefaultValue, BasePK updatedBy) {
        if(entityGeoPointDefaultValue.hasBeenModified()) {
            var entityGeoPointDefault = entityGeoPointDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityGeoPointDefaultValue);
            var entityAttribute = entityGeoPointDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointDefault.setThruTime(session.getStartTime());
                entityGeoPointDefault.store();
            } else {
                entityGeoPointDefault.remove();
            }

            entityGeoPointDefault = entityGeoPointDefaultFactory.create(entityAttribute,
                    entityGeoPointDefaultValue.getLatitude(), entityGeoPointDefaultValue.getLongitude(),
                    entityGeoPointDefaultValue.getElevation(), entityGeoPointDefaultValue.getAltitude(),
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityGeoPointDefault(EntityGeoPointDefault entityGeoPointDefault, BasePK deletedBy) {
        var entityAttribute = entityGeoPointDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointDefault.setThruTime(session.getStartTime());
        } else {
            entityGeoPointDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityGeoPointDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityGeoPointDefault = getEntityGeoPointDefaultForUpdate(entityAttribute);

        if(entityGeoPointDefault != null) {
            deleteEntityGeoPointDefault(entityGeoPointDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityGeoPointAttributeFactory entityGeoPointAttributeFactory;

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        return createEntityGeoPointAttribute(entityAttribute.getPrimaryKey(), entityInstance, latitude, longitude,
                elevation, altitude, createdBy);
    }

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointAttribute = entityGeoPointAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), latitude, longitude, elevation, altitude, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityGeoPointAttribute;
    }

    public long countEntityGeoPointAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitygeopointattributes
                    WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityGeoPointAttributeHistoryQueries;

    static {
        getEntityGeoPointAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitygeopointattributes
                WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ?
                ORDER BY engeopnta_thrutime
                _LIMIT_
                """);
    }

    public List<EntityGeoPointAttribute> getEntityGeoPointAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityGeoPointAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityGeoPointAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityGeoPointAttribute entityGeoPointAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointattributes " +
                        "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointattributes " +
                        "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityGeoPointAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityGeoPointAttribute = entityGeoPointAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttribute;
    }
    
    public EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityGeoPointAttribute getEntityGeoPointAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityGeoPointAttributeValue getEntityGeoPointAttributeValueForUpdate(EntityGeoPointAttribute entityGeoPointAttribute) {
        return entityGeoPointAttribute == null? null: entityGeoPointAttribute.getEntityGeoPointAttributeValue().clone();
    }
    
    public EntityGeoPointAttributeValue getEntityGeoPointAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttributeValueForUpdate(getEntityGeoPointAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityGeoPointAttribute> entityGeoPointAttributes;
        
        try {
            var ps = entityGeoPointAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = entityGeoPointAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityGeoPointAttribute> entityGeoPointAttributes;
        
        try {
            var ps = entityGeoPointAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = entityGeoPointAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttributeTransfer(UserVisit userVisit, EntityGeoPointAttribute entityGeoPointAttribute, EntityInstance entityInstance) {
        return entityGeoPointAttributeTransferCache.getEntityGeoPointAttributeTransfer(userVisit, entityGeoPointAttribute, entityInstance);
    }
    
    public void updateEntityGeoPointAttributeFromValue(EntityGeoPointAttributeValue entityGeoPointAttributeValue, BasePK updatedBy) {
        if(entityGeoPointAttributeValue.hasBeenModified()) {
            var entityGeoPointAttribute = entityGeoPointAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityGeoPointAttributeValue);
            var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
            var entityInstance = entityGeoPointAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointAttribute.setThruTime(session.getStartTime());
                entityGeoPointAttribute.store();
            } else {
                entityGeoPointAttribute.remove();
            }
            
            entityGeoPointAttributeFactory.create(entityAttribute, entityInstance, entityGeoPointAttributeValue.getLatitude(),
                    entityGeoPointAttributeValue.getLongitude(), entityGeoPointAttributeValue.getElevation(), entityGeoPointAttributeValue.getAltitude(),
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityGeoPointAttribute(EntityGeoPointAttribute entityGeoPointAttribute, BasePK deletedBy) {
        var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
        var entityInstance = entityGeoPointAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointAttribute.setThruTime(session.getStartTime());
        } else {
            entityGeoPointAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityGeoPointAttributes(List<EntityGeoPointAttribute> entityGeoPointAttributes, BasePK deletedBy) {
        entityGeoPointAttributes.forEach((entityGeoPointAttribute) -> 
                deleteEntityGeoPointAttribute(entityGeoPointAttribute, deletedBy)
        );
    }
    
    public void deleteEntityGeoPointAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityGeoPointAttributes(getEntityGeoPointAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityGeoPointAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityGeoPointAttributes(getEntityGeoPointAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityTimeDefaultFactory entityTimeDefaultFactory;
    
    public EntityTimeDefault createEntityTimeDefault(EntityAttribute entityAttribute, Long timeAttribute,
            BasePK createdBy) {
        var entityTimeDefault = entityTimeDefaultFactory.create(entityAttribute,
                timeAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityTimeDefault;
    }

    public long countEntityTimeDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitytimedefaults
                    WHERE entdef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityTimeDefaultHistoryQueries;

    static {
        getEntityTimeDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitytimedefaults
                WHERE entdef_ena_entityattributeid = ?
                ORDER BY entdef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityTimeDefault> getEntityTimeDefaultHistory(EntityAttribute entityAttribute) {
        return entityTimeDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityTimeDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityTimeDefault getEntityTimeDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityTimeDefault entityTimeDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimedefaults " +
                        "WHERE entdef_ena_entityattributeid = ? AND entdef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimedefaults " +
                        "WHERE entdef_ena_entityattributeid = ? AND entdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityTimeDefaultFactory.prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityTimeDefault = entityTimeDefaultFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTimeDefault;
    }

    public EntityTimeDefault getEntityTimeDefault(EntityAttribute entityAttribute) {
        return getEntityTimeDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityTimeDefault getEntityTimeDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityTimeDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityTimeDefaultValue getEntityTimeDefaultValueForUpdate(EntityTimeDefault entityTimeDefault) {
        return entityTimeDefault == null? null: entityTimeDefault.getEntityTimeDefaultValue().clone();
    }

    public EntityTimeDefaultValue getEntityTimeDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityTimeDefaultValueForUpdate(getEntityTimeDefaultForUpdate(entityAttribute));
    }

    public EntityTimeDefaultTransfer getEntityTimeDefaultTransfer(UserVisit userVisit, EntityTimeDefault entityTimeDefault) {
        return entityTimeDefaultTransferCache.getEntityTimeDefaultTransfer(userVisit, entityTimeDefault);
    }

    public void updateEntityTimeDefaultFromValue(EntityTimeDefaultValue entityTimeDefaultValue, BasePK updatedBy) {
        if(entityTimeDefaultValue.hasBeenModified()) {
            var entityTimeDefault = entityTimeDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityTimeDefaultValue);
            var entityAttribute = entityTimeDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeDefault.setThruTime(session.getStartTime());
                entityTimeDefault.store();
            } else {
                entityTimeDefault.remove();
            }

            entityTimeDefault = entityTimeDefaultFactory.create(entityAttribute,
                    entityTimeDefaultValue.getTimeAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityTimeDefault(EntityTimeDefault entityTimeDefault, BasePK deletedBy) {
        var entityAttribute = entityTimeDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeDefault.setThruTime(session.getStartTime());
        } else {
            entityTimeDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityTimeDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityTimeDefault = getEntityTimeDefaultForUpdate(entityAttribute);

        if(entityTimeDefault != null) {
            deleteEntityTimeDefault(entityTimeDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityTimeAttributeFactory entityTimeAttributeFactory;

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        return createEntityTimeAttribute(entityAttribute.getPrimaryKey(), entityInstance, timeAttribute,
                createdBy);
    }

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        var entityTimeAttribute = entityTimeAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), timeAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityTimeAttribute;
    }

    public long countEntityTimeAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitytimeattributes
                    WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityTimeAttributeHistoryQueries;

    static {
        getEntityTimeAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitytimeattributes
                WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ?
                ORDER BY enta_thrutime
                _LIMIT_
                """);
    }

    public List<EntityTimeAttribute> getEntityTimeAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityTimeAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityTimeAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityTimeAttribute getEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityTimeAttribute entityTimeAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimeattributes " +
                        "WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ? AND enta_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimeattributes " +
                        "WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ? AND enta_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityTimeAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityTimeAttribute = entityTimeAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttribute;
    }
    
    public EntityTimeAttribute getEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityTimeAttribute getEntityTimeAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityTimeAttributeValue getEntityTimeAttributeValueForUpdate(EntityTimeAttribute entityTimeAttribute) {
        return entityTimeAttribute == null? null: entityTimeAttribute.getEntityTimeAttributeValue().clone();
    }
    
    public EntityTimeAttributeValue getEntityTimeAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttributeValueForUpdate(getEntityTimeAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityTimeAttribute> entityTimeAttributes;
        
        try {
            var ps = entityTimeAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_ena_entityattributeid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = entityTimeAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityTimeAttribute> entityTimeAttributes;
        
        try {
            var ps = entityTimeAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_eni_entityinstanceid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = entityTimeAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public EntityTimeAttributeTransfer getEntityTimeAttributeTransfer(UserVisit userVisit, EntityTimeAttribute entityTimeAttribute, EntityInstance entityInstance) {
        return entityTimeAttributeTransferCache.getEntityTimeAttributeTransfer(userVisit, entityTimeAttribute, entityInstance);
    }
    
    public void updateEntityTimeAttributeFromValue(EntityTimeAttributeValue entityTimeAttributeValue, BasePK updatedBy) {
        if(entityTimeAttributeValue.hasBeenModified()) {
            var entityTimeAttribute = entityTimeAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityTimeAttributeValue);
            var entityAttribute = entityTimeAttribute.getEntityAttribute();
            var entityInstance = entityTimeAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeAttribute.setThruTime(session.getStartTime());
                entityTimeAttribute.store();
            } else {
                entityTimeAttribute.remove();
            }
            
            entityTimeAttributeFactory.create(entityAttribute, entityInstance, entityTimeAttributeValue.getTimeAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityTimeAttribute(EntityTimeAttribute entityTimeAttribute, BasePK deletedBy) {
        var entityAttribute = entityTimeAttribute.getEntityAttribute();
        var entityInstance = entityTimeAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeAttribute.setThruTime(session.getStartTime());
        } else {
            entityTimeAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityTimeAttributes(List<EntityTimeAttribute> entityTimeAttributes, BasePK deletedBy) {
        entityTimeAttributes.forEach((entityTimeAttribute) -> 
                deleteEntityTimeAttribute(entityTimeAttribute, deletedBy)
        );
    }
    
    public void deleteEntityTimeAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityTimeAttributes(getEntityTimeAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityTimeAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityTimeAttributes(getEntityTimeAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityBlobAttributeFactory entityBlobAttributeFactory;
    
    public EntityBlobAttribute createEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, ByteArray blobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityBlobAttribute = entityBlobAttributeFactory.create(entityAttribute,
                entityInstance, language, blobAttribute, mimeType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityBlobAttribute;
    }
    
    private EntityBlobAttribute getEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityBlobAttribute entityBlobAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityblobattributes " +
                        "WHERE enba_ena_entityattributeid = ? AND enba_eni_entityinstanceid = ? AND enba_lang_languageid = ? " +
                        "AND enba_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityblobattributes " +
                        "WHERE enba_ena_entityattributeid = ? AND enba_eni_entityinstanceid = ? AND enba_lang_languageid = ? " +
                        "AND enba_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityBlobAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityBlobAttribute = entityBlobAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttribute;
    }
    
    public EntityBlobAttribute getEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityBlobAttribute getEntityBlobAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityBlobAttribute getBestEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        var entityBlobAttribute = getEntityBlobAttribute(entityAttribute, entityInstance, language);
        
        if(entityBlobAttribute == null && !language.getIsDefault()) {
            entityBlobAttribute = getEntityBlobAttribute(entityAttribute, entityInstance, partyControl.getDefaultLanguage());
        }
        
        return entityBlobAttribute;
    }
    
    public EntityBlobAttributeValue getEntityBlobAttributeValueForUpdate(EntityBlobAttribute entityBlobAttribute) {
        return entityBlobAttribute == null? null: entityBlobAttribute.getEntityBlobAttributeValue().clone();
    }
    
    public EntityBlobAttributeValue getEntityBlobAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttributeValueForUpdate(getEntityBlobAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityBlobAttribute> entityBlobAttributes;
        
        try {
            var ps = entityBlobAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_ena_entityattributeid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = entityBlobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBlobAttribute> entityBlobAttributes;
        
        try {
            var ps = entityBlobAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_eni_entityinstanceid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = entityBlobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(UserVisit userVisit, EntityBlobAttribute entityBlobAttribute, EntityInstance entityInstance) {
        return entityBlobAttributeTransferCache.getEntityBlobAttributeTransfer(userVisit, entityBlobAttribute, entityInstance);
    }
    
    public void updateEntityBlobAttributeFromValue(EntityBlobAttributeValue entityBlobAttributeValue, BasePK updatedBy) {
        if(entityBlobAttributeValue.hasBeenModified()) {
            var entityBlobAttribute = entityBlobAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityBlobAttributeValue);
            var entityAttribute = entityBlobAttribute.getEntityAttribute();
            var entityInstance = entityBlobAttribute.getEntityInstance();
            var language = entityBlobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBlobAttribute.setThruTime(session.getStartTime());
                entityBlobAttribute.store();
            } else {
                entityBlobAttribute.remove();
            }
            
            entityBlobAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityBlobAttributeValue.getBlobAttribute(), entityBlobAttributeValue.getMimeTypePK(), session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBlobAttribute(EntityBlobAttribute entityBlobAttribute, BasePK deletedBy) {
        var entityAttribute = entityBlobAttribute.getEntityAttribute();
        var entityInstance = entityBlobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBlobAttribute.setThruTime(session.getStartTime());
        } else {
            entityBlobAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityBlobAttributes(List<EntityBlobAttribute> entityBlobAttributes, BasePK deletedBy) {
        entityBlobAttributes.forEach((entityBlobAttribute) -> 
                deleteEntityBlobAttribute(entityBlobAttribute, deletedBy)
        );
    }
    
    public void deleteEntityBlobAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityBlobAttributes(getEntityBlobAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityBlobAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityBlobAttributes(getEntityBlobAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityClobAttributeFactory entityClobAttributeFactory;
    
    public EntityClobAttribute createEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String clobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityClobAttribute = entityClobAttributeFactory.create(entityAttribute,
                entityInstance, language, clobAttribute, mimeType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityClobAttribute;
    }

    public long countEntityClobAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityclobattributes
                    WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityClobAttributeHistoryQueries;

    static {
        getEntityClobAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityclobattributes
                WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ?
                ORDER BY enca_thrutime
                _LIMIT_
                """);
    }

    public List<EntityClobAttribute> getEntityClobAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityClobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityClobAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityClobAttribute getEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityClobAttribute entityClobAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityclobattributes " +
                        "WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ? AND enca_lang_languageid = ? " +
                        "AND enca_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityclobattributes " +
                        "WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ? AND enca_lang_languageid = ? " +
                        "AND enca_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityClobAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityClobAttribute = entityClobAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttribute;
    }
    
    public EntityClobAttribute getEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityClobAttribute getEntityClobAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityClobAttribute getBestEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        var entityClobAttribute = getEntityClobAttribute(entityAttribute, entityInstance, language);
        
        if(entityClobAttribute == null && !language.getIsDefault()) {
            entityClobAttribute = getEntityClobAttribute(entityAttribute, entityInstance, partyControl.getDefaultLanguage());
        }
        
        return entityClobAttribute;
    }
    
    public EntityClobAttributeValue getEntityClobAttributeValueForUpdate(EntityClobAttribute entityClobAttribute) {
        return entityClobAttribute == null? null: entityClobAttribute.getEntityClobAttributeValue().clone();
    }
    
    public EntityClobAttributeValue getEntityClobAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttributeValueForUpdate(getEntityClobAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityClobAttribute> entityClobAttributes;
        
        try {
            var ps = entityClobAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_ena_entityattributeid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = entityClobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityClobAttribute> entityClobAttributes;
        
        try {
            var ps = entityClobAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_eni_entityinstanceid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = entityClobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public EntityClobAttributeTransfer getEntityClobAttributeTransfer(UserVisit userVisit, EntityClobAttribute entityClobAttribute, EntityInstance entityInstance) {
        return entityClobAttributeTransferCache.getEntityClobAttributeTransfer(userVisit, entityClobAttribute, entityInstance);
    }
    
    public void updateEntityClobAttributeFromValue(EntityClobAttributeValue entityClobAttributeValue, BasePK updatedBy) {
        if(entityClobAttributeValue.hasBeenModified()) {
            var entityClobAttribute = entityClobAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityClobAttributeValue);
            var entityAttribute = entityClobAttribute.getEntityAttribute();
            var entityInstance = entityClobAttribute.getEntityInstance();
            var language = entityClobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityClobAttribute.setThruTime(session.getStartTime());
                entityClobAttribute.store();
            } else {
                entityClobAttribute.remove();
            }
            
            entityClobAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityClobAttributeValue.getClobAttribute(), entityClobAttributeValue.getMimeTypePK(), session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityClobAttribute(EntityClobAttribute entityClobAttribute, BasePK deletedBy) {
        var entityAttribute = entityClobAttribute.getEntityAttribute();
        var entityInstance = entityClobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityClobAttribute.setThruTime(session.getStartTime());
        } else {
            entityClobAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityClobAttributes(List<EntityClobAttribute> entityClobAttributes, BasePK deletedBy) {
        entityClobAttributes.forEach((entityClobAttribute) -> 
                deleteEntityClobAttribute(entityClobAttribute, deletedBy)
        );
    }
    
    public void deleteEntityClobAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityClobAttributes(getEntityClobAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityClobAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityClobAttributes(getEntityClobAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityAttributeEntityTypeFactory entityAttributeEntityTypeFactory;
    
    public EntityAttributeEntityType createEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, BasePK createdBy) {
        var entityAttributeEntityType = entityAttributeEntityTypeFactory.create(entityAttribute, allowedEntityType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeEntityType;
    }
    
    public long countEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ?",
                entityAttribute, Session.MAX_TIME);
    }

    public long countEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_allowedentitytypeid = ? AND enaent_thrutime = ?",
                allowedEntityType, Session.MAX_TIME);
    }

    public boolean entityAttributeEntityTypeExists(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return 1 == session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ?",
                entityAttribute, allowedEntityType, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getEntityAttributeEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, EntityPermission entityPermission) {
        return entityAttributeEntityTypeFactory.getEntityFromQuery(entityPermission, getEntityAttributeEntityTypeQueries,
                entityAttribute, allowedEntityType, Session.MAX_TIME);
    }

    public EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_ONLY);
    }

    public EntityAttributeEntityType getEntityAttributeEntityTypeForUpdate(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getEntityAttributeEntityTypesByEntityAttributeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "AND enaent_allowedentitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                + "ORDER BY entdt_sortorder, entdt_entitytypename, cvndd_componentvendorname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypesByEntityAttributeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return entityAttributeEntityTypeFactory.getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByEntityAttributeQueries,
                entityAttribute, Session.MAX_TIME);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeEntityTypesByAllowedEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes, entityattributes, entityattributedetails "
                + "WHERE enaent_allowedentitytypeid = ? AND enaent_thrutime = ? "
                + "AND enaent_ena_entityattributeid = ena_entityattributeid AND ena_lastdetailid = enadt_entityAttributedetailid "
                + "ORDER BY enadt_sortorder, enadt_entityAttributename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypesByAllowedEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType, EntityPermission entityPermission) {
        return entityAttributeEntityTypeFactory.getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByAllowedEntityTypeQueries,
                allowedEntityType, Session.MAX_TIME);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityTypeTransfer getEntityAttributeEntityTypeTransfer(UserVisit userVisit, EntityAttributeEntityType entityAttributeEntityType, EntityInstance entityInstance) {
        return entityAttributeEntityTypeTransferCache.getEntityAttributeEntityTypeTransfer(userVisit, entityAttributeEntityType, entityInstance);
    }
    
    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfers(UserVisit userVisit, Collection<EntityAttributeEntityType> entityAttributeEntityTypes, EntityInstance entityInstance) {
        List<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypeTransfers = new ArrayList<>(entityAttributeEntityTypes.size());

        entityAttributeEntityTypes.forEach((entityAttributeEntityType) ->
                entityAttributeEntityTypeTransfers.add(entityAttributeEntityTypeTransferCache.getEntityAttributeEntityTypeTransfer(userVisit, entityAttributeEntityType, entityInstance))
        );

        return entityAttributeEntityTypeTransfers;
    }

    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityAttributeEntityTypeTransfers(userVisit, getEntityAttributeEntityTypesByEntityAttribute(entityAttribute), entityInstance);
    }

    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfersByAllowedEntityType(UserVisit userVisit, EntityType allowedEntityType, EntityInstance entityInstance) {
        return getEntityAttributeEntityTypeTransfers(userVisit, getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType), entityInstance);
    }

    public void deleteEntityAttributeEntityType(EntityAttributeEntityType entityAttributeEntityType, BasePK deletedBy) {
        entityAttributeEntityType.setThruTime(session.getStartTime());
        
        sendEvent(entityAttributeEntityType.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeEntityTypes(List<EntityAttributeEntityType> entityAttributeEntityTypes, BasePK deletedBy) {
        entityAttributeEntityTypes.forEach((entityAttributeEntityType) -> 
                deleteEntityAttributeEntityType(entityAttributeEntityType, deletedBy)
        );
    }
    
    public void deleteEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityAttributeEntityTypes(getEntityAttributeEntityTypesByEntityAttributeForUpdate(entityAttribute),  deletedBy);
    }

    public void deleteEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType, BasePK deletedBy) {
        deleteEntityAttributeEntityTypes(getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(allowedEntityType),  deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityEntityAttributeFactory entityEntityAttributeFactory;
    
    public EntityEntityAttribute createEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityEntityAttribute = entityEntityAttributeFactory.create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityEntityAttribute;
    }

    public long countEntityEntityAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityentityattributes
                    WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityEntityAttributeHistoryQueries;

    static {
        getEntityEntityAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityentityattributes
                WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ?
                ORDER BY eea_thrutime
                _LIMIT_
                """);
    }

    public List<EntityEntityAttribute> getEntityEntityAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityEntityAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityEntityAttribute getEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityEntityAttribute entityEntityAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityentityattributes " +
                        "WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ? " +
                        "AND eea_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityentityattributes " +
                        "WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ? " +
                        "AND eea_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityEntityAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityEntityAttribute = entityEntityAttributeFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttribute;
    }
    
    public EntityEntityAttribute getEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityEntityAttribute getEntityEntityAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityEntityAttributeValue getEntityEntityAttributeValueForUpdate(EntityEntityAttribute entityEntityAttribute) {
        return entityEntityAttribute == null? null: entityEntityAttribute.getEntityEntityAttributeValue().clone();
    }
    
    public EntityEntityAttributeValue getEntityEntityAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttributeValueForUpdate(getEntityEntityAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = entityEntityAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = entityEntityAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = entityEntityAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_entityinstanceattributeid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public EntityEntityAttributeTransfer getEntityEntityAttributeTransfer(UserVisit userVisit, EntityEntityAttribute entityEntityAttribute, EntityInstance entityInstance) {
        return entityEntityAttributeTransferCache.getEntityEntityAttributeTransfer(userVisit, entityEntityAttribute, entityInstance);
    }
    
    public void updateEntityEntityAttributeFromValue(EntityEntityAttributeValue entityEntityAttributeValue, BasePK updatedBy) {
        if(entityEntityAttributeValue.hasBeenModified()) {
            var entityEntityAttribute = entityEntityAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityEntityAttributeValue);
            var entityAttribute = entityEntityAttribute.getEntityAttribute();
            var entityInstance = entityEntityAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityEntityAttribute.setThruTime(session.getStartTime());
                entityEntityAttribute.store();
            } else {
                entityEntityAttribute.remove();
            }
            
            entityEntityAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(),
                    entityEntityAttributeValue.getEntityInstanceAttributePK(), session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityEntityAttribute(EntityEntityAttribute entityEntityAttribute, BasePK deletedBy) {
        var entityAttribute = entityEntityAttribute.getEntityAttribute();
        var entityInstance = entityEntityAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityEntityAttribute.setThruTime(session.getStartTime());
        } else {
            entityEntityAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityEntityAttributes(List<EntityEntityAttribute> entityEntityAttributes, BasePK deletedBy) {
        entityEntityAttributes.forEach((entityEntityAttribute) -> 
                deleteEntityEntityAttribute(entityEntityAttribute, deletedBy)
        );
    }
    
    public void deleteEntityEntityAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityEntityAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityInstanceAttributeForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected EntityCollectionAttributeFactory entityCollectionAttributeFactory;
    
    public EntityCollectionAttribute createEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityCollectionAttribute = entityCollectionAttributeFactory.create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityCollectionAttribute;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = entityCollectionAttributeFactory.prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM entitycollectionattributes, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                    + "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_thrutime = ? "
                    + "AND eca_entityinstanceattributeid = eni_entityinstanceid "
                    + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                    + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                    + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityCollectionAttributes = entityCollectionAttributeFactory.getEntitiesFromQuery(
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    private EntityCollectionAttribute getEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, EntityPermission entityPermission) {
        EntityCollectionAttribute entityCollectionAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitycollectionattributes " +
                        "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_entityinstanceattributeid = ? " +
                        "AND eca_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitycollectionattributes " +
                        "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_entityinstanceattributeid = ? " +
                        "AND eca_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = entityCollectionAttributeFactory.prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityCollectionAttribute = entityCollectionAttributeFactory.getEntityFromQuery(
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttribute;
    }
    
    public EntityCollectionAttribute getEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute) {
        return getEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityCollectionAttribute getEntityCollectionAttributeForUpdate(EntityAttribute entityAttribute,
            EntityInstance entityInstance, EntityInstance entityInstanceAttribute) {
        return getEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute, EntityPermission.READ_WRITE);
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = entityCollectionAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = entityCollectionAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = entityCollectionAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = entityCollectionAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = entityCollectionAttributeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_entityinstanceattributeid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = entityCollectionAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public EntityCollectionAttributeTransfer getEntityCollectionAttributeTransfer(UserVisit userVisit, EntityCollectionAttribute entityCollectionAttribute, EntityInstance entityInstance) {
        return entityCollectionAttributeTransferCache.getEntityCollectionAttributeTransfer(userVisit, entityCollectionAttribute, entityInstance);
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, Collection<EntityCollectionAttribute> entityCollectionAttributes, EntityInstance entityInstance) {
        List<EntityCollectionAttributeTransfer> entityCollectionAttributeTransfers = new ArrayList<>(entityCollectionAttributes.size());
        
        entityCollectionAttributes.forEach((entityCollectionAttribute) ->
                entityCollectionAttributeTransfers.add(entityCollectionAttributeTransferCache.getEntityCollectionAttributeTransfer(userVisit, entityCollectionAttribute, entityInstance))
        );
        
        return entityCollectionAttributeTransfers;
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityCollectionAttributeTransfers(userVisit, getEntityCollectionAttributes(entityAttribute, entityInstance), entityInstance);
    }
    
    public void deleteEntityCollectionAttribute(EntityCollectionAttribute entityCollectionAttribute, BasePK deletedBy) {
        var entityAttribute = entityCollectionAttribute.getEntityAttribute();
        var entityInstance = entityCollectionAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityCollectionAttribute.setThruTime(session.getStartTime());
        } else {
            entityCollectionAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityCollectionAttributes(List<EntityCollectionAttribute> entityCollectionAttributes, BasePK deletedBy) {
        entityCollectionAttributes.forEach((entityCollectionAttribute) -> 
                deleteEntityCollectionAttribute(entityCollectionAttribute, deletedBy)
        );
    }
    
    public void deleteEntityCollectionAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityCollectionAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(entityInstance), deletedBy);
    }

}
