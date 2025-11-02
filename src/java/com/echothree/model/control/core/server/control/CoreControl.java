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
import com.echothree.model.control.workflow.server.control.WorkflowControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CoreControl
        extends BaseCoreControl {
    
    /** Creates a new instance of CoreControl */
    protected CoreControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Types
    // --------------------------------------------------------------------------------
    
    public EntityAttributeType createEntityAttributeType(String entityAttributeTypeName) {
        var entityAttributeType = EntityAttributeTypeFactory.getInstance().create(entityAttributeTypeName);
        
        return entityAttributeType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttributeType */
    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributeTypePK(entityInstance.getEntityUniqueId());

        return EntityAttributeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
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
            var ps = EntityAttributeTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypes " +
                    "WHERE enat_entityattributetypename = ?");
            
            ps.setString(1, entityAttributeTypeName);
            
            entityAttributeType = EntityAttributeTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeType;
    }
    
    public List<EntityAttributeType> getEntityAttributeTypes() {
        var ps = EntityAttributeTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM entityattributetypes " +
                "ORDER BY enat_entityattributetypename " +
                "_LIMIT_");
        
        return EntityAttributeTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public EntityAttributeTypeTransfer getEntityAttributeTypeTransfer(UserVisit userVisit, EntityAttributeType entityAttributeType) {
        return getCoreTransferCaches(userVisit).getEntityAttributeTypeTransferCache().getEntityAttributeTypeTransfer(entityAttributeType);
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit, Collection<EntityAttributeType> entityAttributeTypes) {
        List<EntityAttributeTypeTransfer> entityAttributeTypeTransfers = null;
        
        if(entityAttributeTypes != null) {
            entityAttributeTypeTransfers = new ArrayList<>(entityAttributeTypes.size());
            
            for(var entityAttributeType : entityAttributeTypes) {
                entityAttributeTypeTransfers.add(getCoreTransferCaches(userVisit).getEntityAttributeTypeTransferCache().getEntityAttributeTypeTransfer(entityAttributeType));
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
    
    public EntityAttributeTypeDescription createEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language, String description) {
        var entityAttributeTypeDescription = EntityAttributeTypeDescriptionFactory.getInstance().create(entityAttributeType, language, description);
        
        return entityAttributeTypeDescription;
    }
    
    public EntityAttributeTypeDescription getEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        EntityAttributeTypeDescription entityAttributeTypeDescription;
        
        try {
            var ps = EntityAttributeTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypedescriptions " +
                    "WHERE enatd_enat_entityattributetypeid = ? AND enatd_lang_languageid = ?");
            
            ps.setLong(1, entityAttributeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            entityAttributeTypeDescription = EntityAttributeTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
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

        var entityAttributeGroup = EntityAttributeGroupFactory.getInstance().create();
        var entityAttributeGroupDetail = EntityAttributeGroupDetailFactory.getInstance().create(entityAttributeGroup,
                entityAttributeGroupName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return EntityAttributeGroupFactory.getInstance().getEntityFromPK(entityPermission, pk);
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

        var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
        
        return EntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());
            
            entityAttributeGroups = EntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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

        var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
        
        return EntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, entityAttributeGroupName);
            
            entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityAttributeGroupTransferCache().getEntityAttributeGroupTransfer(entityAttributeGroup, entityInstance);
    }
    
    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, Collection<EntityAttributeGroup> entityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeGroupTransfer> entityAttributeGroupTransfers = new ArrayList<>(entityAttributeGroups.size());
        var entityAttributeGroupTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeGroupTransferCache();
        
        entityAttributeGroups.forEach((entityAttributeGroup) ->
                entityAttributeGroupTransfers.add(entityAttributeGroupTransferCache.getEntityAttributeGroupTransfer(entityAttributeGroup, entityInstance))
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
            var entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDetailValue.getEntityAttributeGroupPK());
            var entityAttributeGroupDetail = entityAttributeGroup.getActiveDetailForUpdate();
            
            entityAttributeGroupDetail.setThruTime(session.START_TIME_LONG);
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
            
            entityAttributeGroupDetail = EntityAttributeGroupDetailFactory.getInstance().create(entityAttributeGroupPK, entityAttributeGroupName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
            entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);
            
            sendEvent(entityAttributeGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, BasePK updatedBy) {
        updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, true, updatedBy);
    }

    public EntityAttributeGroup getEntityAttributeGroupByPK(EntityAttributeGroupPK pk) {
        return EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public void deleteEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, deletedBy);
        deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, deletedBy);

        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetailForUpdate();
        entityAttributeGroupDetail.setThruTime(session.START_TIME_LONG);
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
    
    public EntityAttributeGroupDescription createEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, String description,
            BasePK createdBy) {
        var entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().create(entityAttributeGroup,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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

            var ps = EntityAttributeGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeGroupDescriptions = EntityAttributeGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityAttributeGroupDescriptionTransferCache().getEntityAttributeGroupDescriptionTransfer(entityAttributeGroupDescription, entityInstance);
    }
    
    public List<EntityAttributeGroupDescriptionTransfer> getEntityAttributeGroupDescriptionTransfers(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        var entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup);
        List<EntityAttributeGroupDescriptionTransfer> entityAttributeGroupDescriptionTransfers = new ArrayList<>(entityAttributeGroupDescriptions.size());
        var entityAttributeGroupDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeGroupDescriptionTransferCache();
        
        entityAttributeGroupDescriptions.forEach((entityAttributeGroupDescription) ->
                entityAttributeGroupDescriptionTransfers.add(entityAttributeGroupDescriptionTransferCache.getEntityAttributeGroupDescriptionTransfer(entityAttributeGroupDescription, entityInstance))
        );
        
        return entityAttributeGroupDescriptionTransfers;
    }
    
    public void updateEntityAttributeGroupDescriptionFromValue(EntityAttributeGroupDescriptionValue entityAttributeGroupDescriptionValue, BasePK updatedBy) {
        if(entityAttributeGroupDescriptionValue.hasBeenModified()) {
            var entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDescriptionValue.getPrimaryKey());
            
            entityAttributeGroupDescription.setThruTime(session.START_TIME_LONG);
            entityAttributeGroupDescription.store();

            var entityAttributeGroup = entityAttributeGroupDescription.getEntityAttributeGroup();
            var language = entityAttributeGroupDescription.getLanguage();
            var description = entityAttributeGroupDescriptionValue.getDescription();
            
            entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().create(entityAttributeGroup, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeGroupDescription(EntityAttributeGroupDescription entityAttributeGroupDescription, BasePK deletedBy) {
        entityAttributeGroupDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityAttribute createEntityAttribute(EntityType entityType, String entityAttributeName,
            EntityAttributeType entityAttributeType, Boolean trackRevisions, Integer sortOrder, BasePK createdBy) {
        var entityAttribute = EntityAttributeFactory.getInstance().create();
        var entityAttributeDetail = EntityAttributeDetailFactory.getInstance().create(entityAttribute, entityType,
                entityAttributeName, entityAttributeType, trackRevisions, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return EntityAttributeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttribute getEntityAttributeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByPK(EntityAttributePK pk) {
        return EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
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

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, entityAttributeName);
            
            entityAttribute = EntityAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityAttributeTransferCache().getEntityAttributeTransfer(entityAttribute, entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfers(UserVisit userVisit, Collection<EntityAttribute> entityAttributes, EntityInstance entityInstance) {
        List<EntityAttributeTransfer> entityAttributeTransfers = new ArrayList<>(entityAttributes.size());
        var entityAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeTransferCache();
        
        entityAttributes.forEach((entityAttribute) ->
                entityAttributeTransfers.add(entityAttributeTransferCache.getEntityAttributeTransfer(entityAttribute, entityInstance))
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
            final var entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDetailValue.getEntityAttributePK());
            var entityAttributeDetail = entityAttribute.getActiveDetailForUpdate();
            
            entityAttributeDetail.setThruTime(session.START_TIME_LONG);
            entityAttributeDetail.store();

            final var entityAttributePK = entityAttributeDetail.getEntityAttributePK(); // Not updated
            final var entityTypePK = entityAttributeDetail.getEntityTypePK(); // Not updated
            final var entityAttributeName = entityAttributeDetailValue.getEntityAttributeName();
            final var entityAttributeTypePK = entityAttributeDetail.getEntityAttributeTypePK(); // Not updated
            final var trackRevisions = entityAttributeDetailValue.getTrackRevisions();
            final var sortOrder = entityAttributeDetailValue.getSortOrder();
            
            entityAttributeDetail = EntityAttributeDetailFactory.getInstance().create(entityAttributePK, entityTypePK,
                    entityAttributeName, entityAttributeTypePK, trackRevisions, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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
                var workflowControl = Session.getModelController(WorkflowControl.class);
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
            entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityAttribute.getPrimaryKey());
        }
        
        entityAttributeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public EntityAttributeDescription createEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            String description, BasePK createdBy) {
        var entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().create(session,
                entityAttribute, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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

            var ps = EntityAttributeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeDescriptions = EntityAttributeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityAttributeDescriptionTransferCache().getEntityAttributeDescriptionTransfer(entityAttributeDescription, entityInstance);
    }
    
    public List<EntityAttributeDescriptionTransfer> getEntityAttributeDescriptionTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttribute(entityAttribute);
        List<EntityAttributeDescriptionTransfer> entityAttributeDescriptionTransfers = new ArrayList<>(entityAttributeDescriptions.size());
        var entityAttributeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeDescriptionTransferCache();
        
        entityAttributeDescriptions.forEach((entityAttributeDescription) ->
                entityAttributeDescriptionTransfers.add(entityAttributeDescriptionTransferCache.getEntityAttributeDescriptionTransfer(entityAttributeDescription, entityInstance))
        );
        
        return entityAttributeDescriptionTransfers;
    }
    
    public void updateEntityAttributeDescriptionFromValue(EntityAttributeDescriptionValue entityAttributeDescriptionValue, BasePK updatedBy) {
        if(entityAttributeDescriptionValue.hasBeenModified()) {
            var entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDescriptionValue.getPrimaryKey());
            
            entityAttributeDescription.setThruTime(session.START_TIME_LONG);
            entityAttributeDescription.store();

            var entityAttribute = entityAttributeDescription.getEntityAttribute();
            var language = entityAttributeDescription.getLanguage();
            var description = entityAttributeDescriptionValue.getDescription();
            
            entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().create(entityAttribute, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeDescription(EntityAttributeDescription entityAttributeDescription, BasePK deletedBy) {
        entityAttributeDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityAttributeBlob createEntityAttributeBlob(EntityAttribute entityAttribute, Boolean checkContentWebAddress,
            BasePK createdBy) {
        var entityAttributeBlob = EntityAttributeBlobFactory.getInstance().create(session,
                entityAttribute, checkContentWebAddress, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        return EntityAttributeBlobFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeBlobQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
            var entityAttributeBlob = EntityAttributeBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeBlobValue.getPrimaryKey());
            
            entityAttributeBlob.setThruTime(session.START_TIME_LONG);
            entityAttributeBlob.store();

            var entityAttribute = entityAttributeBlob.getEntityAttribute();
            var checkContentWebAddress = entityAttributeBlobValue.getCheckContentWebAddress();
            
            entityAttributeBlob = EntityAttributeBlobFactory.getInstance().create(entityAttribute, checkContentWebAddress,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeBlob(EntityAttributeBlob entityAttributeBlob, BasePK deletedBy) {
        entityAttributeBlob.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityAttributeString createEntityAttributeString(EntityAttribute entityAttribute, String validationPattern,
            BasePK createdBy) {
        var entityAttributeString = EntityAttributeStringFactory.getInstance().create(session,
                entityAttribute, validationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        return EntityAttributeStringFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeStringQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
            var entityAttributeString = EntityAttributeStringFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeStringValue.getPrimaryKey());
            
            entityAttributeString.setThruTime(session.START_TIME_LONG);
            entityAttributeString.store();

            var entityAttribute = entityAttributeString.getEntityAttribute();
            var validationPattern = entityAttributeStringValue.getValidationPattern();
            
            entityAttributeString = EntityAttributeStringFactory.getInstance().create(entityAttribute, validationPattern,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeString(EntityAttributeString entityAttributeString, BasePK deletedBy) {
        entityAttributeString.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityAttributeInteger createEntityAttributeInteger(EntityAttribute entityAttribute, Integer upperRangeIntegerValue,
            Integer upperLimitIntegerValue, Integer lowerLimitIntegerValue, Integer lowerRangeIntegerValue, BasePK createdBy) {
        var entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().create(session,
                entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        return EntityAttributeIntegerFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeIntegerQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
            var entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeIntegerValue.getPrimaryKey());
            
            entityAttributeInteger.setThruTime(session.START_TIME_LONG);
            entityAttributeInteger.store();

            var entityAttribute = entityAttributeInteger.getEntityAttribute();
            var upperRangeIntegerValue = entityAttributeIntegerValue.getUpperRangeIntegerValue();
            var upperLimitIntegerValue = entityAttributeIntegerValue.getUpperLimitIntegerValue();
            var lowerLimitIntegerValue = entityAttributeIntegerValue.getLowerLimitIntegerValue();
            var lowerRangeIntegerValue = entityAttributeIntegerValue.getLowerRangeIntegerValue();
            
            entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().create(entityAttribute, upperRangeIntegerValue,
                    upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeInteger(EntityAttributeInteger entityAttributeInteger, BasePK deletedBy) {
        entityAttributeInteger.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityAttributeLong createEntityAttributeLong(EntityAttribute entityAttribute, Long upperRangeLongValue,
            Long upperLimitLongValue, Long lowerLimitLongValue, Long lowerRangeLongValue, BasePK createdBy) {
        var entityAttributeLong = EntityAttributeLongFactory.getInstance().create(session,
                entityAttribute, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        return EntityAttributeLongFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeLongQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
            var entityAttributeLong = EntityAttributeLongFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeLongValue.getPrimaryKey());
            
            entityAttributeLong.setThruTime(session.START_TIME_LONG);
            entityAttributeLong.store();

            var entityAttribute = entityAttributeLong.getEntityAttribute();
            var upperRangeLongValue = entityAttributeLongValue.getUpperRangeLongValue();
            var upperLimitLongValue = entityAttributeLongValue.getUpperLimitLongValue();
            var lowerLimitLongValue = entityAttributeLongValue.getLowerLimitLongValue();
            var lowerRangeLongValue = entityAttributeLongValue.getLowerRangeLongValue();
            
            entityAttributeLong = EntityAttributeLongFactory.getInstance().create(entityAttribute, upperRangeLongValue,
                    upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeLong(EntityAttributeLong entityAttributeLong, BasePK deletedBy) {
        entityAttributeLong.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityAttributeNumeric createEntityAttributeNumeric(EntityAttribute entityAttribute, UnitOfMeasureType unitOfMeasureType,
            BasePK createdBy) {
        var entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().create(session,
                entityAttribute, unitOfMeasureType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeNumeric;
    }
    
    public long countEntityAttributeNumericsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributenumerics " +
                "WHERE enan_uomt_unitofmeasuretypeid = ? AND enan_thrutime = ?",
                unitOfMeasureType, Session.MAX_TIME_LONG);
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
        return EntityAttributeNumericFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeNumericQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
            var entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeNumericValue.getPrimaryKey());
            
            entityAttributeNumeric.setThruTime(session.START_TIME_LONG);
            entityAttributeNumeric.store();

            var entityAttributePK = entityAttributeNumeric.getEntityAttributePK();
            var unitOfMeasureTypePK = entityAttributeNumericValue.getUnitOfMeasureTypePK();
            
            entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().create(entityAttributePK, unitOfMeasureTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeNumeric(EntityAttributeNumeric entityAttributeNumeric, BasePK deletedBy) {
        entityAttributeNumeric.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityAttributeListItem createEntityAttributeListItem(EntityAttribute entityAttribute, Sequence entityListItemSequence,
            BasePK createdBy) {
        var entityAttributeListItem = EntityAttributeListItemFactory.getInstance().create(session,
                entityAttribute, entityListItemSequence, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeListItem;
    }
    
    public long countEntityAttributeListItemsByEntityListItemSequence(Sequence entityListItemSequence) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributelistitems " +
                "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ?",
                entityListItemSequence, Session.MAX_TIME_LONG);
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
        return EntityAttributeListItemFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeListItemQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
            var entityAttributeListItem = EntityAttributeListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeListItemValue.getPrimaryKey());
            
            entityAttributeListItem.setThruTime(session.START_TIME_LONG);
            entityAttributeListItem.store();

            var entityAttributePK = entityAttributeListItem.getEntityAttributePK();
            var entityListItemSequencePK = entityAttributeListItemValue.getEntityListItemSequencePK();
            
            entityAttributeListItem = EntityAttributeListItemFactory.getInstance().create(entityAttributePK, entityListItemSequencePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeListItem(EntityAttributeListItem entityAttributeListItem, BasePK deletedBy) {
        entityAttributeListItem.setThruTime(session.START_TIME_LONG);
        
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

    public EntityAttributeWorkflow createEntityAttributeWorkflow(EntityAttribute entityAttribute, Workflow workflow,
            BasePK createdBy) {
        var entityAttributeWorkflow = EntityAttributeWorkflowFactory.getInstance().create(session,
                entityAttribute, workflow, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeWorkflow;
    }

    public long countEntityAttributeWorkflowsByWorkflow(Workflow workflow) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityattributeworkflows " +
                        "WHERE enawkfl_wkfl_workflowid = ? AND enawkfl_thrutime = ?",
                workflow, Session.MAX_TIME_LONG);
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
        return EntityAttributeWorkflowFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeWorkflowQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
            var entityAttributeWorkflow = EntityAttributeWorkflowFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAttributeWorkflowValue.getPrimaryKey());

            entityAttributeWorkflow.setThruTime(session.START_TIME_LONG);
            entityAttributeWorkflow.store();

            var entityAttributePK = entityAttributeWorkflow.getEntityAttributePK();
            var workflowPK = entityAttributeWorkflowValue.getWorkflowPK();

            entityAttributeWorkflow = EntityAttributeWorkflowFactory.getInstance().create(entityAttributePK, workflowPK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeWorkflow(EntityAttributeWorkflow entityAttributeWorkflow, BasePK deletedBy) {
        entityAttributeWorkflow.setThruTime(session.START_TIME_LONG);

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
    
    public EntityAttributeEntityAttributeGroup createEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, Integer sortOrder, BasePK createdBy) {
        var entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().create(session,
                entityAttribute, entityAttributeGroup, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeEntityAttributeGroup;
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributeentityattributegroups " +
                "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ?",
                entityAttribute, Session.MAX_TIME_LONG);
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributeentityattributegroups " +
                "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?",
                entityAttributeGroup, Session.MAX_TIME_LONG);
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

            var ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroups = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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

            var ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroups = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityAttributeEntityAttributeGroupTransferCache().getEntityAttributeEntityAttributeGroupTransfer(entityAttributeEntityAttributeGroup, entityInstance);
    }
    
    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfers(UserVisit userVisit,
            Collection<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeEntityAttributeGroupTransfer> entityAttributeEntityAttributeGroupTransfers = new ArrayList<>(entityAttributeEntityAttributeGroups.size());
        var entityAttributeEntityAttributeGroupTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeEntityAttributeGroupTransferCache();
        
        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                entityAttributeEntityAttributeGroupTransfers.add(entityAttributeEntityAttributeGroupTransferCache.getEntityAttributeEntityAttributeGroupTransfer(entityAttributeEntityAttributeGroup, entityInstance))
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
            var entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeEntityAttributeGroupValue.getPrimaryKey());
            
            entityAttributeEntityAttributeGroup.setThruTime(session.START_TIME_LONG);
            entityAttributeEntityAttributeGroup.store();

            var entityAttribute = entityAttributeEntityAttributeGroup.getEntityAttribute();
            var entityAttributeGroup = entityAttributeEntityAttributeGroup.getEntityAttributeGroup();
            var sortOrder = entityAttributeEntityAttributeGroupValue.getSortOrder();
            
            entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().create(entityAttribute, entityAttributeGroup,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeEntityAttributeGroup(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, BasePK deletedBy) {
        entityAttributeEntityAttributeGroup.setThruTime(session.START_TIME_LONG);
        
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

        var entityListItem = EntityListItemFactory.getInstance().create();
        var entityListItemDetail = EntityListItemDetailFactory.getInstance().create(entityListItem,
                entityAttribute, entityListItemName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityListItem.getPrimaryKey());
        entityListItem.setActiveDetail(entityListItemDetail);
        entityListItem.setLastDetail(entityListItemDetail);
        entityListItem.store();
        
        sendEvent(entityListItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityListItem;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityListItem */
    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityListItemPK(entityInstance.getEntityUniqueId());

        return EntityListItemFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItem getEntityListItemByPK(EntityListItemPK pk) {
        return EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
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

            var ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItem = EntityListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityListItemName);
            
            entityListItem = EntityListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItems = EntityListItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityListItemTransferCache().getEntityListItemTransfer(entityListItem, entityInstance);
    }
    
    public List<EntityListItemTransfer> getEntityListItemTransfers(UserVisit userVisit, Collection<EntityListItem> entityListItems, EntityInstance entityInstance) {
        List<EntityListItemTransfer> entityListItemTransfers = new ArrayList<>(entityListItems.size());
        var entityListItemTransferCache = getCoreTransferCaches(userVisit).getEntityListItemTransferCache();

        entityListItems.forEach((entityListItem) ->
                entityListItemTransfers.add(entityListItemTransferCache.getEntityListItemTransfer(entityListItem, entityInstance))
        );

        return entityListItemTransfers;
    }

    public List<EntityListItemTransfer> getEntityListItemTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemTransfers(userVisit, getEntityListItems(entityAttribute), entityInstance);
    }

    private void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityListItemDetailValue.hasBeenModified()) {
            var entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityListItemDetailValue.getEntityListItemPK());
            var entityListItemDetail = entityListItem.getActiveDetailForUpdate();
            
            entityListItemDetail.setThruTime(session.START_TIME_LONG);
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
            
            entityListItemDetail = EntityListItemDetailFactory.getInstance().create(entityListItemPK,
                    entityAttribute.getPrimaryKey(), entityListItemName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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
        
        entityListItemDetail.setThruTime(session.START_TIME_LONG);
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
    
    public EntityListItemDescription createEntityListItemDescription(EntityListItem entityListItem, Language language, String description, BasePK createdBy) {
        var entityListItemDescription = EntityListItemDescriptionFactory.getInstance().create(entityListItem, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
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

            var ps = EntityListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemDescription = EntityListItemDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemDescriptions = EntityListItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityListItemDescriptionTransferCache().getEntityListItemDescriptionTransfer(entityListItemDescription, entityInstance);
    }
    
    public List<EntityListItemDescriptionTransfer> getEntityListItemDescriptionTransfersByEntityListItem(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        var entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItem(entityListItem);
        List<EntityListItemDescriptionTransfer> entityListItemDescriptionTransfers = new ArrayList<>(entityListItemDescriptions.size());
        var entityListItemDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityListItemDescriptionTransferCache();
        
        entityListItemDescriptions.forEach((entityListItemDescription) ->
                entityListItemDescriptionTransfers.add(entityListItemDescriptionTransferCache.getEntityListItemDescriptionTransfer(entityListItemDescription, entityInstance))
        );
        
        return entityListItemDescriptionTransfers;
    }
    
    public void updateEntityListItemDescriptionFromValue(EntityListItemDescriptionValue entityListItemDescriptionValue, BasePK updatedBy) {
        if(entityListItemDescriptionValue.hasBeenModified()) {
            var entityListItemDescription = EntityListItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityListItemDescriptionValue.getPrimaryKey());
            
            entityListItemDescription.setThruTime(session.START_TIME_LONG);
            entityListItemDescription.store();

            var entityListItem = entityListItemDescription.getEntityListItem();
            var language = entityListItemDescription.getLanguage();
            var description = entityListItemDescriptionValue.getDescription();
            
            entityListItemDescription = EntityListItemDescriptionFactory.getInstance().create(entityListItem, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityListItem.getPrimaryKey(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemDescription(EntityListItemDescription entityListItemDescription, BasePK deletedBy) {
        entityListItemDescription.setThruTime(session.START_TIME_LONG);
        
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

        var entityIntegerRange = EntityIntegerRangeFactory.getInstance().create();
        var entityIntegerRangeDetail = EntityIntegerRangeDetailFactory.getInstance().create(entityIntegerRange, entityAttribute,
                entityIntegerRangeName, minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRange.getPrimaryKey());
        entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
        entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
        entityIntegerRange.store();
        
        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityIntegerRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityIntegerRange */
    public EntityIntegerRange getEntityIntegerRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityIntegerRangePK(entityInstance.getEntityUniqueId());
        var entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
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

            var ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityIntegerRangeName);
            
            entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRanges = EntityIntegerRangeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityIntegerRangeTransferCache().getEntityIntegerRangeTransfer(entityIntegerRange, entityInstance);
    }
    
    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfers(UserVisit userVisit, Collection<EntityIntegerRange> entityIntegerRanges, EntityInstance entityInstance) {
        List<EntityIntegerRangeTransfer> entityIntegerRangeTransfers = new ArrayList<>(entityIntegerRanges.size());
        var entityIntegerRangeTransferCache = getCoreTransferCaches(userVisit).getEntityIntegerRangeTransferCache();

        entityIntegerRanges.forEach((entityIntegerRange) ->
                entityIntegerRangeTransfers.add(entityIntegerRangeTransferCache.getEntityIntegerRangeTransfer(entityIntegerRange, entityInstance))
        );

        return entityIntegerRangeTransfers;
    }

    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerRangeTransfers(userVisit, getEntityIntegerRanges(entityAttribute), entityInstance);
    }

    private void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityIntegerRangeDetailValue.hasBeenModified()) {
            var entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityIntegerRangeDetailValue.getEntityIntegerRangePK());
            var entityIntegerRangeDetail = entityIntegerRange.getActiveDetailForUpdate();
            
            entityIntegerRangeDetail.setThruTime(session.START_TIME_LONG);
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
            
            entityIntegerRangeDetail = EntityIntegerRangeDetailFactory.getInstance().create(entityIntegerRangePK, entityAttribute.getPrimaryKey(), entityIntegerRangeName,
                    minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        entityIntegerRangeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public EntityIntegerRangeDescription createEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, String description, BasePK createdBy) {
        var entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().create(entityIntegerRange, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
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

            var ps = EntityIntegerRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityIntegerRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerRangeDescriptions = EntityIntegerRangeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityIntegerRangeDescriptionTransferCache().getEntityIntegerRangeDescriptionTransfer(entityIntegerRangeDescription, entityInstance);
    }
    
    public List<EntityIntegerRangeDescriptionTransfer> getEntityIntegerRangeDescriptionTransfersByEntityIntegerRange(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        var entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange);
        List<EntityIntegerRangeDescriptionTransfer> entityIntegerRangeDescriptionTransfers = new ArrayList<>(entityIntegerRangeDescriptions.size());
        var entityIntegerRangeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityIntegerRangeDescriptionTransferCache();
        
        entityIntegerRangeDescriptions.forEach((entityIntegerRangeDescription) ->
                entityIntegerRangeDescriptionTransfers.add(entityIntegerRangeDescriptionTransferCache.getEntityIntegerRangeDescriptionTransfer(entityIntegerRangeDescription, entityInstance))
        );
        
        return entityIntegerRangeDescriptionTransfers;
    }
    
    public void updateEntityIntegerRangeDescriptionFromValue(EntityIntegerRangeDescriptionValue entityIntegerRangeDescriptionValue, BasePK updatedBy) {
        if(entityIntegerRangeDescriptionValue.hasBeenModified()) {
            var entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRangeDescriptionValue.getPrimaryKey());
            
            entityIntegerRangeDescription.setThruTime(session.START_TIME_LONG);
            entityIntegerRangeDescription.store();

            var entityIntegerRange = entityIntegerRangeDescription.getEntityIntegerRange();
            var language = entityIntegerRangeDescription.getLanguage();
            var description = entityIntegerRangeDescriptionValue.getDescription();
            
            entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().create(entityIntegerRange, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityIntegerRangeDescription(EntityIntegerRangeDescription entityIntegerRangeDescription, BasePK deletedBy) {
        entityIntegerRangeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var entityLongRange = EntityLongRangeFactory.getInstance().create();
        var entityLongRangeDetail = EntityLongRangeDetailFactory.getInstance().create(entityLongRange, entityAttribute, entityLongRangeName,
                minimumLongValue, maximumLongValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityLongRange.getPrimaryKey());
        entityLongRange.setActiveDetail(entityLongRangeDetail);
        entityLongRange.setLastDetail(entityLongRangeDetail);
        entityLongRange.store();
        
        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityLongRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityLongRange */
    public EntityLongRange getEntityLongRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityLongRangePK(entityInstance.getEntityUniqueId());
        var entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
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

            var ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityLongRangeName);
            
            entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRanges = EntityLongRangeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityLongRangeTransferCache().getEntityLongRangeTransfer(entityLongRange, entityInstance);
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfers(UserVisit userVisit, Collection<EntityLongRange> entityLongRanges, EntityInstance entityInstance) {
        List<EntityLongRangeTransfer> entityLongRangeTransfers = new ArrayList<>(entityLongRanges.size());
        var entityLongRangeTransferCache = getCoreTransferCaches(userVisit).getEntityLongRangeTransferCache();

        entityLongRanges.forEach((entityLongRange) ->
                entityLongRangeTransfers.add(entityLongRangeTransferCache.getEntityLongRangeTransfer(entityLongRange, entityInstance))
        );

        return entityLongRangeTransfers;
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongRangeTransfers(userVisit, getEntityLongRanges(entityAttribute), entityInstance);
    }

    private void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityLongRangeDetailValue.hasBeenModified()) {
            var entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityLongRangeDetailValue.getEntityLongRangePK());
            var entityLongRangeDetail = entityLongRange.getActiveDetailForUpdate();
            
            entityLongRangeDetail.setThruTime(session.START_TIME_LONG);
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
            
            entityLongRangeDetail = EntityLongRangeDetailFactory.getInstance().create(entityLongRangePK, entityAttribute.getPrimaryKey(), entityLongRangeName,
                    minimumLongValue, maximumLongValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        entityLongRangeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public EntityLongRangeDescription createEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, String description, BasePK createdBy) {
        var entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().create(entityLongRange, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
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

            var ps = EntityLongRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityLongRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongRangeDescriptions = EntityLongRangeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityLongRangeDescriptionTransferCache().getEntityLongRangeDescriptionTransfer(entityLongRangeDescription, entityInstance);
    }
    
    public List<EntityLongRangeDescriptionTransfer> getEntityLongRangeDescriptionTransfersByEntityLongRange(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        var entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange);
        List<EntityLongRangeDescriptionTransfer> entityLongRangeDescriptionTransfers = new ArrayList<>(entityLongRangeDescriptions.size());
        var entityLongRangeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityLongRangeDescriptionTransferCache();
        
        entityLongRangeDescriptions.forEach((entityLongRangeDescription) ->
                entityLongRangeDescriptionTransfers.add(entityLongRangeDescriptionTransferCache.getEntityLongRangeDescriptionTransfer(entityLongRangeDescription, entityInstance))
        );
        
        return entityLongRangeDescriptionTransfers;
    }
    
    public void updateEntityLongRangeDescriptionFromValue(EntityLongRangeDescriptionValue entityLongRangeDescriptionValue, BasePK updatedBy) {
        if(entityLongRangeDescriptionValue.hasBeenModified()) {
            var entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityLongRangeDescriptionValue.getPrimaryKey());
            
            entityLongRangeDescription.setThruTime(session.START_TIME_LONG);
            entityLongRangeDescription.store();

            var entityLongRange = entityLongRangeDescription.getEntityLongRange();
            var language = entityLongRangeDescription.getLanguage();
            var description = entityLongRangeDescriptionValue.getDescription();
            
            entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().create(entityLongRange, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityLongRange.getPrimaryKey(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongRangeDescription(EntityLongRangeDescription entityLongRangeDescription, BasePK deletedBy) {
        entityLongRangeDescription.setThruTime(session.START_TIME_LONG);
        
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

    public EntityBooleanDefault createEntityBooleanDefault(EntityAttribute entityAttribute, Boolean booleanAttribute,
            BasePK createdBy) {
        var entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().create(entityAttribute,
                booleanAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityBooleanDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityBooleanDefaultHistoryQueries,
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

            var ps = EntityBooleanDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityBooleanDefaultTransferCache().getEntityBooleanDefaultTransfer(entityBooleanDefault);
    }

    public void updateEntityBooleanDefaultFromValue(EntityBooleanDefaultValue entityBooleanDefaultValue, BasePK updatedBy) {
        if(entityBooleanDefaultValue.hasBeenModified()) {
            var entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBooleanDefaultValue);
            var entityAttribute = entityBooleanDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanDefault.setThruTime(session.START_TIME_LONG);
                entityBooleanDefault.store();
            } else {
                entityBooleanDefault.remove();
            }

            entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().create(entityAttribute,
                    entityBooleanDefaultValue.getBooleanAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityBooleanDefault(EntityBooleanDefault entityBooleanDefault, BasePK deletedBy) {
        var entityAttribute = entityBooleanDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        return createEntityBooleanAttribute(entityAttribute.getPrimaryKey(), entityInstance, booleanAttribute,
                createdBy);
    }

    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        var entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), booleanAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitybooleanattributes
                WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ?
                ORDER BY enbla_thrutime
                _LIMIT_
                """);
        getEntityBooleanAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityBooleanAttribute> getEntityBooleanAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityBooleanAttributeHistoryQueries,
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

            var ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_ena_entityattributeid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBooleanAttribute> entityBooleanAttributes;
        
        try {
            var ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_eni_entityinstanceid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public EntityBooleanAttributeTransfer getEntityBooleanAttributeTransfer(UserVisit userVisit, EntityBooleanAttribute entityBooleanAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityBooleanAttributeTransferCache().getEntityBooleanAttributeTransfer(entityBooleanAttribute, entityInstance);
    }
    
    public void updateEntityBooleanAttributeFromValue(EntityBooleanAttributeValue entityBooleanAttributeValue, BasePK updatedBy) {
        if(entityBooleanAttributeValue.hasBeenModified()) {
            var entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBooleanAttributeValue);
            var entityAttribute = entityBooleanAttribute.getEntityAttribute();
            var entityInstance = entityBooleanAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanAttribute.setThruTime(session.START_TIME_LONG);
                entityBooleanAttribute.store();
            } else {
                entityBooleanAttribute.remove();
            }
            
            EntityBooleanAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityBooleanAttributeValue.getBooleanAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBooleanAttribute(EntityBooleanAttribute entityBooleanAttribute, BasePK deletedBy) {
        var entityAttribute = entityBooleanAttribute.getEntityAttribute();
        var entityInstance = entityBooleanAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanAttribute.setThruTime(session.START_TIME_LONG);
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

    public EntityDateDefault createEntityDateDefault(EntityAttribute entityAttribute, Integer dateAttribute,
            BasePK createdBy) {
        var entityDateDefault = EntityDateDefaultFactory.getInstance().create(entityAttribute,
                dateAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityDateDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityDateDefaultHistoryQueries,
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

            var ps = EntityDateDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityDateDefault = EntityDateDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityDateDefaultTransferCache().getEntityDateDefaultTransfer(entityDateDefault);
    }

    public void updateEntityDateDefaultFromValue(EntityDateDefaultValue entityDateDefaultValue, BasePK updatedBy) {
        if(entityDateDefaultValue.hasBeenModified()) {
            var entityDateDefault = EntityDateDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityDateDefaultValue);
            var entityAttribute = entityDateDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateDefault.setThruTime(session.START_TIME_LONG);
                entityDateDefault.store();
            } else {
                entityDateDefault.remove();
            }

            entityDateDefault = EntityDateDefaultFactory.getInstance().create(entityAttribute,
                    entityDateDefaultValue.getDateAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityDateDefault(EntityDateDefault entityDateDefault, BasePK deletedBy) {
        var entityAttribute = entityDateDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityDateAttribute createEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        return createEntityDateAttribute(entityAttribute.getPrimaryKey(), entityInstance, dateAttribute,
                createdBy);
    }

    public EntityDateAttribute createEntityDateAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        var entityDateAttribute = EntityDateAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), dateAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitydateattributes
                WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ?
                ORDER BY enda_thrutime
                _LIMIT_
                """);
        getEntityDateAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityDateAttribute> getEntityDateAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityDateAttributeHistoryQueries,
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

            var ps = EntityDateAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityDateAttribute = EntityDateAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityDateAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_ena_entityattributeid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityDateAttribute> entityDateAttributes;
        
        try {
            var ps = EntityDateAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_eni_entityinstanceid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public EntityDateAttributeTransfer getEntityDateAttributeTransfer(UserVisit userVisit, EntityDateAttribute entityDateAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityDateAttributeTransferCache().getEntityDateAttributeTransfer(entityDateAttribute, entityInstance);
    }
    
    public void updateEntityDateAttributeFromValue(EntityDateAttributeValue entityDateAttributeValue, BasePK updatedBy) {
        if(entityDateAttributeValue.hasBeenModified()) {
            var entityDateAttribute = EntityDateAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityDateAttributeValue);
            var entityAttribute = entityDateAttribute.getEntityAttribute();
            var entityInstance = entityDateAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateAttribute.setThruTime(session.START_TIME_LONG);
                entityDateAttribute.store();
            } else {
                entityDateAttribute.remove();
            }
            
            EntityDateAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityDateAttributeValue.getDateAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityDateAttribute(EntityDateAttribute entityDateAttribute, BasePK deletedBy) {
        var entityAttribute = entityDateAttribute.getEntityAttribute();
        var entityInstance = entityDateAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateAttribute.setThruTime(session.START_TIME_LONG);
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

    public EntityIntegerDefault createEntityIntegerDefault(EntityAttribute entityAttribute, Integer integerAttribute,
            BasePK createdBy) {
        var entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().create(entityAttribute,
                integerAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityIntegerDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityIntegerDefaultHistoryQueries,
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

            var ps = EntityIntegerDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityIntegerDefaultTransferCache().getEntityIntegerDefaultTransfer(entityIntegerDefault);
    }

    public void updateEntityIntegerDefaultFromValue(EntityIntegerDefaultValue entityIntegerDefaultValue, BasePK updatedBy) {
        if(entityIntegerDefaultValue.hasBeenModified()) {
            var entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityIntegerDefaultValue);
            var entityAttribute = entityIntegerDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerDefault.setThruTime(session.START_TIME_LONG);
                entityIntegerDefault.store();
            } else {
                entityIntegerDefault.remove();
            }

            entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().create(entityAttribute,
                    entityIntegerDefaultValue.getIntegerAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityIntegerDefault(EntityIntegerDefault entityIntegerDefault, BasePK deletedBy) {
        var entityAttribute = entityIntegerDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        return createEntityIntegerAttribute(entityAttribute.getPrimaryKey(), entityInstance, integerAttribute,
                createdBy);
    }

    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        var entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), integerAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityintegerattributes
                WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ?
                ORDER BY enia_thrutime
                _LIMIT_
                """);
        getEntityIntegerAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityIntegerAttribute> getEntityIntegerAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityIntegerAttributeHistoryQueries,
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

            var ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_ena_entityattributeid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityIntegerAttribute> entityIntegerAttributes;
        
        try {
            var ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_eni_entityinstanceid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public EntityIntegerAttributeTransfer getEntityIntegerAttributeTransfer(UserVisit userVisit, EntityIntegerAttribute entityIntegerAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityIntegerAttributeTransferCache().getEntityIntegerAttributeTransfer(entityIntegerAttribute, entityInstance);
    }
    
    public void updateEntityIntegerAttributeFromValue(EntityIntegerAttributeValue entityIntegerAttributeValue, BasePK updatedBy) {
        if(entityIntegerAttributeValue.hasBeenModified()) {
            var entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityIntegerAttributeValue);
            var entityAttribute = entityIntegerAttribute.getEntityAttribute();
            var entityInstance = entityIntegerAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerAttribute.setThruTime(session.START_TIME_LONG);
                entityIntegerAttribute.store();
            } else {
                entityIntegerAttribute.remove();
            }
            
            EntityIntegerAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityIntegerAttributeValue.getIntegerAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    public void deleteEntityIntegerAttribute(EntityIntegerAttribute entityIntegerAttribute, BasePK deletedBy) {
        var entityAttribute = entityIntegerAttribute.getEntityAttribute();
        var entityInstance = entityIntegerAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerAttribute.setThruTime(session.START_TIME_LONG);
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

    public EntityListItemDefault createEntityListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemDefault = EntityListItemDefaultFactory.getInstance().create(session,
                entityAttribute, entityListItem, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityListItemDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityListItemDefaultHistoryQueries,
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

            var ps = EntityListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityListItemDefault = EntityListItemDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityListItemDefault = EntityListItemDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityListItemDefaultTransferCache().getEntityListItemDefaultTransfer(entityListItemDefault);
    }

    public void updateEntityListItemDefaultFromValue(EntityListItemDefaultValue entityListItemDefaultValue, BasePK updatedBy) {
        if(entityListItemDefaultValue.hasBeenModified()) {
            var entityListItemDefault = EntityListItemDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityListItemDefaultValue);
            var entityAttribute = entityListItemDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemDefault.setThruTime(session.START_TIME_LONG);
                entityListItemDefault.store();
            } else {
                entityListItemDefault.remove();
            }

            entityListItemDefault = EntityListItemDefaultFactory.getInstance().create(entityAttribute.getPrimaryKey(),
                    entityListItemDefaultValue.getEntityListItemPK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.DELETE, updatedBy);
        }
    }

    public void deleteEntityListItemDefault(EntityListItemDefault entityListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityListItemAttribute createEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityListItemAttribute createEntityListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = EntityListItemAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylistitemattributes
                WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ?
                ORDER BY ela_thrutime
                _LIMIT_
                """);
        getEntityListItemAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityListItemAttribute> getEntityListItemAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityListItemAttributeHistoryQueries,
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

            var ps = EntityListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemAttribute = EntityListItemAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
            var ps = EntityListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylistitemattributes " +
                    "WHERE ela_eni_entityinstanceid = ? AND ela_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttributes;
    }
    
    public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(UserVisit userVisit, EntityListItemAttribute entityListItemAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityListItemAttributeTransferCache().getEntityListItemAttributeTransfer(entityListItemAttribute, entityInstance);
    }
    
    public void updateEntityListItemAttributeFromValue(EntityListItemAttributeValue entityListItemAttributeValue, BasePK updatedBy) {
        if(entityListItemAttributeValue.hasBeenModified()) {
            var entityListItemAttribute = EntityListItemAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityListItemAttributeValue);
            var entityAttribute = entityListItemAttribute.getEntityAttribute();
            var entityInstance = entityListItemAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemAttribute.setThruTime(session.START_TIME_LONG);
                entityListItemAttribute.store();
            } else {
                entityListItemAttribute.remove();
            }
            
            EntityListItemAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), entityListItemAttributeValue.getEntityListItemPK(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemAttribute(EntityListItemAttribute entityListItemAttribute, BasePK deletedBy) {
        var entityAttribute = entityListItemAttribute.getEntityAttribute();
        var entityInstance = entityListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemAttribute.setThruTime(session.START_TIME_LONG);
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

    public EntityLongDefault createEntityLongDefault(EntityAttribute entityAttribute, Long longAttribute,
            BasePK createdBy) {
        var entityLongDefault = EntityLongDefaultFactory.getInstance().create(entityAttribute,
                longAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityLongDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityLongDefaultHistoryQueries,
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

            var ps = EntityLongDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityLongDefault = EntityLongDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityLongDefaultTransferCache().getEntityLongDefaultTransfer(entityLongDefault);
    }

    public void updateEntityLongDefaultFromValue(EntityLongDefaultValue entityLongDefaultValue, BasePK updatedBy) {
        if(entityLongDefaultValue.hasBeenModified()) {
            var entityLongDefault = EntityLongDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityLongDefaultValue);
            var entityAttribute = entityLongDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongDefault.setThruTime(session.START_TIME_LONG);
                entityLongDefault.store();
            } else {
                entityLongDefault.remove();
            }

            entityLongDefault = EntityLongDefaultFactory.getInstance().create(entityAttribute,
                    entityLongDefaultValue.getLongAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityLongDefault(EntityLongDefault entityLongDefault, BasePK deletedBy) {
        var entityAttribute = entityLongDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityLongAttribute createEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        return createEntityLongAttribute(entityAttribute.getPrimaryKey(), entityInstance, longAttribute,
                createdBy);
    }

    public EntityLongAttribute createEntityLongAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        var entityLongAttribute = EntityLongAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), longAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylongattributes
                WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ?
                ORDER BY enla_thrutime
                _LIMIT_
                """);
        getEntityLongAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityLongAttribute> getEntityLongAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityLongAttributeHistoryQueries,
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

            var ps = EntityLongAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongAttribute = EntityLongAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityLongAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_ena_entityattributeid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityLongAttribute> entityLongAttributes;
        
        try {
            var ps = EntityLongAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_eni_entityinstanceid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public EntityLongAttributeTransfer getEntityLongAttributeTransfer(UserVisit userVisit, EntityLongAttribute entityLongAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityLongAttributeTransferCache().getEntityLongAttributeTransfer(entityLongAttribute, entityInstance);
    }
    
    public void updateEntityLongAttributeFromValue(EntityLongAttributeValue entityLongAttributeValue, BasePK updatedBy) {
        if(entityLongAttributeValue.hasBeenModified()) {
            var entityLongAttribute = EntityLongAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityLongAttributeValue);
            var entityAttribute = entityLongAttribute.getEntityAttribute();
            var entityInstance = entityLongAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongAttribute.setThruTime(session.START_TIME_LONG);
                entityLongAttribute.store();
            } else {
                entityLongAttribute.remove();
            }
            
            EntityLongAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityLongAttributeValue.getLongAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongAttribute(EntityLongAttribute entityLongAttribute, BasePK deletedBy) {
        var entityAttribute = entityLongAttribute.getEntityAttribute();
        var entityInstance = entityLongAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongAttribute.setThruTime(session.START_TIME_LONG);
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

    public EntityMultipleListItemDefault createEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityMultipleListItemDefault = EntityMultipleListItemDefaultFactory.getInstance().create(session,
                entityAttribute, entityListItem, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityMultipleListItemDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityMultipleListItemDefaultHistoryQueries,
                entityAttribute);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaults(EntityAttribute entityAttribute) {
        List<EntityMultipleListItemDefault> entityMultipleListItemDefaults;

        try {
            var ps = EntityMultipleListItemDefaultFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                            + "FROM entitymultiplelistitemdefaults, entitylistitems, entitylistitemdetails "
                            + "WHERE emlidef_ena_entityattributeid = ? AND emlidef_thrutime = ? "
                            + "AND emlidef_eli_entitylistitemid = eli_entitylistitemid AND eli_lastdetailid = elidt_entitylistitemdetailid "
                            + "ORDER BY elidt_sortorder, elidt_entitylistitemname");

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityMultipleListItemDefaults = EntityMultipleListItemDefaultFactory.getInstance().getEntitiesFromQuery(session,
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

            var ps = EntityMultipleListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityMultipleListItemDefault = EntityMultipleListItemDefaultFactory.getInstance().getEntityFromQuery(session,
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

            var ps = EntityMultipleListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityMultipleListItemDefaults = EntityMultipleListItemDefaultFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityMultipleListItemDefaultTransferCache().getEntityMultipleListItemDefaultTransfer(entityMultipleListItemDefault);
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, Collection<EntityMultipleListItemDefault> entityMultipleListItemDefaults) {
        List<EntityMultipleListItemDefaultTransfer> entityMultipleListItemDefaultTransfers = new ArrayList<>(entityMultipleListItemDefaults.size());
        var entityMultipleListItemDefaultTransferCache = getCoreTransferCaches(userVisit).getEntityMultipleListItemDefaultTransferCache();

        entityMultipleListItemDefaults.forEach((entityMultipleListItemDefault) ->
                entityMultipleListItemDefaultTransfers.add(entityMultipleListItemDefaultTransferCache.getEntityMultipleListItemDefaultTransfer(entityMultipleListItemDefault))
        );

        return entityMultipleListItemDefaultTransfers;
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, EntityAttribute entityAttribute) {
        return getEntityMultipleListItemDefaultTransfers(userVisit, getEntityMultipleListItemDefaults(entityAttribute));
    }

    public void deleteEntityMultipleListItemDefault(EntityMultipleListItemDefault entityMultipleListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityMultipleListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityMultipleListItemDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityMultipleListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = EntityMultipleListItemAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

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
            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM entitymultiplelistitemattributes, entitylistitems, entitylistitemdetails "
                    + "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? "
                    + "AND emlia_eli_entitylistitemid = eli_entitylistitemid AND eli_lastdetailid = elidt_entitylistitemdetailid "
                    + "ORDER BY elidt_sortorder, elidt_entitylistitemname");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(session,
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

            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityMultipleListItemAttribute = EntityMultipleListItemAttributeFactory.getInstance().getEntityFromQuery(session,
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

            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitymultiplelistitemattributes " +
                    "WHERE emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    public EntityMultipleListItemAttributeTransfer getEntityMultipleListItemAttributeTransfer(UserVisit userVisit, EntityMultipleListItemAttribute entityMultipleListItemAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityMultipleListItemAttributeTransferCache().getEntityMultipleListItemAttributeTransfer(entityMultipleListItemAttribute, entityInstance);
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, Collection<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributeTransfers = new ArrayList<>(entityMultipleListItemAttributes.size());
        var entityMultipleListItemAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityMultipleListItemAttributeTransferCache();
        
        entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) ->
                entityMultipleListItemAttributeTransfers.add(entityMultipleListItemAttributeTransferCache.getEntityMultipleListItemAttributeTransfer(entityMultipleListItemAttribute, entityInstance))
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
            entityMultipleListItemAttribute.setThruTime(session.START_TIME_LONG);
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
    
    public EntityNameAttribute createEntityNameAttribute(EntityAttribute entityAttribute, String nameAttribute,
            EntityInstance entityInstance, BasePK createdBy) {
        var entityNameAttribute = EntityNameAttributeFactory.getInstance().create(entityAttribute,
                nameAttribute, entityInstance, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitynameattributes
                WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ?
                ORDER BY enna_thrutime
                _LIMIT_
                """);
        getEntityNameAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityNameAttribute> getEntityNameAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityNameAttributeHistoryQueries,
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

            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttribute = EntityNameAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityNameAttribute> entityNameAttributes;
        
        try {
            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_eni_entityinstanceid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public EntityNameAttributeTransfer getEntityNameAttributeTransfer(UserVisit userVisit, EntityNameAttribute entityNameAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityNameAttributeTransferCache().getEntityNameAttributeTransfer(entityNameAttribute, entityInstance);
    }
    
    public void updateEntityNameAttributeFromValue(EntityNameAttributeValue entityNameAttributeValue, BasePK updatedBy) {
        if(entityNameAttributeValue.hasBeenModified()) {
            var entityNameAttribute = EntityNameAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityNameAttributeValue);
            var entityAttribute = entityNameAttribute.getEntityAttribute();
            var entityInstance = entityNameAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityNameAttribute.setThruTime(session.START_TIME_LONG);
                entityNameAttribute.store();
            } else {
                entityNameAttribute.remove();
            }
            
            EntityNameAttributeFactory.getInstance().create(entityAttribute, entityNameAttributeValue.getNameAttribute(), entityInstance, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityNameAttribute(EntityNameAttribute entityNameAttribute, BasePK deletedBy) {
        var entityAttribute = entityNameAttribute.getEntityAttribute();
        var entityInstance = entityNameAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityNameAttribute.setThruTime(session.START_TIME_LONG);
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
            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_nameattribute = ? AND enna_thrutime = ?");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, nameAttribute);
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    public EntityStringDefault createEntityStringDefault(EntityAttribute entityAttribute, Language language,
            String stringAttribute, BasePK createdBy) {
        var entityStringDefault = EntityStringDefaultFactory.getInstance().create(entityAttribute, language,
                stringAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityStringDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityStringDefaultHistoryQueries,
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

            var ps = EntityStringDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityStringDefault = EntityStringDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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

            var ps = EntityStringDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityStringDefaults = EntityStringDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
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
        return getCoreTransferCaches(userVisit).getEntityStringDefaultTransferCache().getEntityStringDefaultTransfer(entityStringDefault);
    }

    public void updateEntityStringDefaultFromValue(EntityStringDefaultValue entityStringDefaultValue, BasePK updatedBy) {
        if(entityStringDefaultValue.hasBeenModified()) {
            var entityStringDefault = EntityStringDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityStringDefaultValue);
            var entityAttribute = entityStringDefault.getEntityAttribute();
            var language = entityStringDefault.getLanguage();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringDefault.setThruTime(session.START_TIME_LONG);
                entityStringDefault.store();
            } else {
                entityStringDefault.remove();
            }

            entityStringDefault = EntityStringDefaultFactory.getInstance().create(entityAttribute, language,
                    entityStringDefaultValue.getStringAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityStringDefault(EntityStringDefault entityStringDefault, BasePK deletedBy) {
        var entityAttribute = entityStringDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityStringAttribute createEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        return createEntityStringAttribute(entityAttribute.getPrimaryKey(), entityInstance, language, stringAttribute,
                createdBy);
    }

    public EntityStringAttribute createEntityStringAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        var entityStringAttribute = EntityStringAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), language.getPrimaryKey(), stringAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityStringAttributeHistoryQueries,
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

            var ps = EntityStringAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityStringAttribute = EntityStringAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityStringAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_ena_entityattributeid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityStringAttribute> entityStringAttributes;
        
        try {
            var ps = EntityStringAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_eni_entityinstanceid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public EntityStringAttributeTransfer getEntityStringAttributeTransfer(UserVisit userVisit, EntityStringAttribute entityStringAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityStringAttributeTransferCache().getEntityStringAttributeTransfer(entityStringAttribute, entityInstance);
    }
    
    public void updateEntityStringAttributeFromValue(EntityStringAttributeValue entityStringAttributeValue, BasePK updatedBy) {
        if(entityStringAttributeValue.hasBeenModified()) {
            var entityStringAttribute = EntityStringAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityStringAttributeValue);
            var entityAttribute = entityStringAttribute.getEntityAttribute();
            var entityInstance = entityStringAttribute.getEntityInstance();
            var language = entityStringAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringAttribute.setThruTime(session.START_TIME_LONG);
                entityStringAttribute.store();
            } else {
                entityStringAttribute.remove();
            }
            
            EntityStringAttributeFactory.getInstance().create(entityAttribute, entityInstance, language, entityStringAttributeValue.getStringAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityStringAttribute(EntityStringAttribute entityStringAttribute, BasePK deletedBy) {
        var entityAttribute = entityStringAttribute.getEntityAttribute();
        var entityInstance = entityStringAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringAttribute.setThruTime(session.START_TIME_LONG);
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

    public EntityGeoPointDefault createEntityGeoPointDefault(EntityAttribute entityAttribute, Integer latitude,
            Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().create(entityAttribute,
                latitude, longitude, elevation, altitude, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityGeoPointDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityGeoPointDefaultHistoryQueries,
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

            var ps = EntityGeoPointDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityGeoPointDefaultTransferCache().getEntityGeoPointDefaultTransfer(entityGeoPointDefault);
    }

    public void updateEntityGeoPointDefaultFromValue(EntityGeoPointDefaultValue entityGeoPointDefaultValue, BasePK updatedBy) {
        if(entityGeoPointDefaultValue.hasBeenModified()) {
            var entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityGeoPointDefaultValue);
            var entityAttribute = entityGeoPointDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointDefault.setThruTime(session.START_TIME_LONG);
                entityGeoPointDefault.store();
            } else {
                entityGeoPointDefault.remove();
            }

            entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().create(entityAttribute,
                    entityGeoPointDefaultValue.getLatitude(), entityGeoPointDefaultValue.getLongitude(),
                    entityGeoPointDefaultValue.getElevation(), entityGeoPointDefaultValue.getAltitude(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityGeoPointDefault(EntityGeoPointDefault entityGeoPointDefault, BasePK deletedBy) {
        var entityAttribute = entityGeoPointDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        return createEntityGeoPointAttribute(entityAttribute.getPrimaryKey(), entityInstance, latitude, longitude,
                elevation, altitude, createdBy);
    }

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), latitude, longitude, elevation, altitude, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitygeopointattributes
                WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ?
                ORDER BY engeopnta_thrutime
                _LIMIT_
                """);
        getEntityGeoPointAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityGeoPointAttribute> getEntityGeoPointAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityGeoPointAttributeHistoryQueries,
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

            var ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityGeoPointAttribute> entityGeoPointAttributes;
        
        try {
            var ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttributeTransfer(UserVisit userVisit, EntityGeoPointAttribute entityGeoPointAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityGeoPointAttributeTransferCache().getEntityGeoPointAttributeTransfer(entityGeoPointAttribute, entityInstance);
    }
    
    public void updateEntityGeoPointAttributeFromValue(EntityGeoPointAttributeValue entityGeoPointAttributeValue, BasePK updatedBy) {
        if(entityGeoPointAttributeValue.hasBeenModified()) {
            var entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityGeoPointAttributeValue);
            var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
            var entityInstance = entityGeoPointAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointAttribute.setThruTime(session.START_TIME_LONG);
                entityGeoPointAttribute.store();
            } else {
                entityGeoPointAttribute.remove();
            }
            
            EntityGeoPointAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityGeoPointAttributeValue.getLatitude(),
                    entityGeoPointAttributeValue.getLongitude(), entityGeoPointAttributeValue.getElevation(), entityGeoPointAttributeValue.getAltitude(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityGeoPointAttribute(EntityGeoPointAttribute entityGeoPointAttribute, BasePK deletedBy) {
        var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
        var entityInstance = entityGeoPointAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointAttribute.setThruTime(session.START_TIME_LONG);
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

    public EntityTimeDefault createEntityTimeDefault(EntityAttribute entityAttribute, Long timeAttribute,
            BasePK createdBy) {
        var entityTimeDefault = EntityTimeDefaultFactory.getInstance().create(entityAttribute,
                timeAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        return EntityTimeDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityTimeDefaultHistoryQueries,
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

            var ps = EntityTimeDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityTimeDefault = EntityTimeDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        return getCoreTransferCaches(userVisit).getEntityTimeDefaultTransferCache().getEntityTimeDefaultTransfer(entityTimeDefault);
    }

    public void updateEntityTimeDefaultFromValue(EntityTimeDefaultValue entityTimeDefaultValue, BasePK updatedBy) {
        if(entityTimeDefaultValue.hasBeenModified()) {
            var entityTimeDefault = EntityTimeDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityTimeDefaultValue);
            var entityAttribute = entityTimeDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeDefault.setThruTime(session.START_TIME_LONG);
                entityTimeDefault.store();
            } else {
                entityTimeDefault.remove();
            }

            entityTimeDefault = EntityTimeDefaultFactory.getInstance().create(entityAttribute,
                    entityTimeDefaultValue.getTimeAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityTimeDefault(EntityTimeDefault entityTimeDefault, BasePK deletedBy) {
        var entityAttribute = entityTimeDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeDefault.setThruTime(session.START_TIME_LONG);
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

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        return createEntityTimeAttribute(entityAttribute.getPrimaryKey(), entityInstance, timeAttribute,
                createdBy);
    }

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        var entityTimeAttribute = EntityTimeAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), timeAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitytimeattributes
                WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ?
                ORDER BY enta_thrutime
                _LIMIT_
                """);
        getEntityTimeAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityTimeAttribute> getEntityTimeAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityTimeAttributeHistoryQueries,
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

            var ps = EntityTimeAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityTimeAttribute = EntityTimeAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityTimeAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_ena_entityattributeid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityTimeAttribute> entityTimeAttributes;
        
        try {
            var ps = EntityTimeAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_eni_entityinstanceid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public EntityTimeAttributeTransfer getEntityTimeAttributeTransfer(UserVisit userVisit, EntityTimeAttribute entityTimeAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityTimeAttributeTransferCache().getEntityTimeAttributeTransfer(entityTimeAttribute, entityInstance);
    }
    
    public void updateEntityTimeAttributeFromValue(EntityTimeAttributeValue entityTimeAttributeValue, BasePK updatedBy) {
        if(entityTimeAttributeValue.hasBeenModified()) {
            var entityTimeAttribute = EntityTimeAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityTimeAttributeValue);
            var entityAttribute = entityTimeAttribute.getEntityAttribute();
            var entityInstance = entityTimeAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeAttribute.setThruTime(session.START_TIME_LONG);
                entityTimeAttribute.store();
            } else {
                entityTimeAttribute.remove();
            }
            
            EntityTimeAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityTimeAttributeValue.getTimeAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityTimeAttribute(EntityTimeAttribute entityTimeAttribute, BasePK deletedBy) {
        var entityAttribute = entityTimeAttribute.getEntityAttribute();
        var entityInstance = entityTimeAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeAttribute.setThruTime(session.START_TIME_LONG);
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
    
    public EntityBlobAttribute createEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, ByteArray blobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityBlobAttribute = EntityBlobAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, language, blobAttribute, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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

            var ps = EntityBlobAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityBlobAttribute = EntityBlobAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityBlobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_ena_entityattributeid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = EntityBlobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBlobAttribute> entityBlobAttributes;
        
        try {
            var ps = EntityBlobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_eni_entityinstanceid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = EntityBlobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(UserVisit userVisit, EntityBlobAttribute entityBlobAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityBlobAttributeTransferCache().getEntityBlobAttributeTransfer(entityBlobAttribute, entityInstance);
    }
    
    public void updateEntityBlobAttributeFromValue(EntityBlobAttributeValue entityBlobAttributeValue, BasePK updatedBy) {
        if(entityBlobAttributeValue.hasBeenModified()) {
            var entityBlobAttribute = EntityBlobAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBlobAttributeValue);
            var entityAttribute = entityBlobAttribute.getEntityAttribute();
            var entityInstance = entityBlobAttribute.getEntityInstance();
            var language = entityBlobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBlobAttribute.setThruTime(session.START_TIME_LONG);
                entityBlobAttribute.store();
            } else {
                entityBlobAttribute.remove();
            }
            
            EntityBlobAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityBlobAttributeValue.getBlobAttribute(), entityBlobAttributeValue.getMimeTypePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBlobAttribute(EntityBlobAttribute entityBlobAttribute, BasePK deletedBy) {
        var entityAttribute = entityBlobAttribute.getEntityAttribute();
        var entityInstance = entityBlobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBlobAttribute.setThruTime(session.START_TIME_LONG);
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
    
    public EntityClobAttribute createEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String clobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityClobAttribute = EntityClobAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, language, clobAttribute, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityclobattributes
                WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ?
                ORDER BY enca_thrutime
                _LIMIT_
                """);
        getEntityClobAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityClobAttribute> getEntityClobAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityClobAttributeHistoryQueries,
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

            var ps = EntityClobAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityClobAttribute = EntityClobAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityClobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_ena_entityattributeid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityClobAttribute> entityClobAttributes;
        
        try {
            var ps = EntityClobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_eni_entityinstanceid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public EntityClobAttributeTransfer getEntityClobAttributeTransfer(UserVisit userVisit, EntityClobAttribute entityClobAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityClobAttributeTransferCache().getEntityClobAttributeTransfer(entityClobAttribute, entityInstance);
    }
    
    public void updateEntityClobAttributeFromValue(EntityClobAttributeValue entityClobAttributeValue, BasePK updatedBy) {
        if(entityClobAttributeValue.hasBeenModified()) {
            var entityClobAttribute = EntityClobAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityClobAttributeValue);
            var entityAttribute = entityClobAttribute.getEntityAttribute();
            var entityInstance = entityClobAttribute.getEntityInstance();
            var language = entityClobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityClobAttribute.setThruTime(session.START_TIME_LONG);
                entityClobAttribute.store();
            } else {
                entityClobAttribute.remove();
            }
            
            EntityClobAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityClobAttributeValue.getClobAttribute(), entityClobAttributeValue.getMimeTypePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityClobAttribute(EntityClobAttribute entityClobAttribute, BasePK deletedBy) {
        var entityAttribute = entityClobAttribute.getEntityAttribute();
        var entityInstance = entityClobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityClobAttribute.setThruTime(session.START_TIME_LONG);
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
    
    public EntityAttributeEntityType createEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, BasePK createdBy) {
        var entityAttributeEntityType = EntityAttributeEntityTypeFactory.getInstance().create(entityAttribute, allowedEntityType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        return EntityAttributeEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeEntityTypeQueries,
                entityAttribute, allowedEntityType, Session.MAX_TIME_LONG);
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
        return EntityAttributeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByEntityAttributeQueries,
                entityAttribute, Session.MAX_TIME_LONG);
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
        return EntityAttributeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByAllowedEntityTypeQueries,
                allowedEntityType, Session.MAX_TIME_LONG);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityTypeTransfer getEntityAttributeEntityTypeTransfer(UserVisit userVisit, EntityAttributeEntityType entityAttributeEntityType, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeEntityTypeTransferCache().getEntityAttributeEntityTypeTransfer(entityAttributeEntityType, entityInstance);
    }
    
    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfers(UserVisit userVisit, Collection<EntityAttributeEntityType> entityAttributeEntityTypes, EntityInstance entityInstance) {
        List<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypeTransfers = new ArrayList<>(entityAttributeEntityTypes.size());
        var entityAttributeEntityTypeTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeEntityTypeTransferCache();

        entityAttributeEntityTypes.forEach((entityAttributeEntityType) ->
                entityAttributeEntityTypeTransfers.add(entityAttributeEntityTypeTransferCache.getEntityAttributeEntityTypeTransfer(entityAttributeEntityType, entityInstance))
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
        entityAttributeEntityType.setThruTime(session.START_TIME_LONG);
        
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
    
    public EntityEntityAttribute createEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityEntityAttribute = EntityEntityAttributeFactory.getInstance().create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityentityattributes
                WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ?
                ORDER BY eea_thrutime
                _LIMIT_
                """);
        getEntityEntityAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityEntityAttribute> getEntityEntityAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityEntityAttributeHistoryQueries,
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

            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityEntityAttribute = EntityEntityAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_entityinstanceattributeid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public EntityEntityAttributeTransfer getEntityEntityAttributeTransfer(UserVisit userVisit, EntityEntityAttribute entityEntityAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityEntityAttributeTransferCache().getEntityEntityAttributeTransfer(entityEntityAttribute, entityInstance);
    }
    
    public void updateEntityEntityAttributeFromValue(EntityEntityAttributeValue entityEntityAttributeValue, BasePK updatedBy) {
        if(entityEntityAttributeValue.hasBeenModified()) {
            var entityEntityAttribute = EntityEntityAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityEntityAttributeValue);
            var entityAttribute = entityEntityAttribute.getEntityAttribute();
            var entityInstance = entityEntityAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityEntityAttribute.setThruTime(session.START_TIME_LONG);
                entityEntityAttribute.store();
            } else {
                entityEntityAttribute.remove();
            }
            
            EntityEntityAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(),
                    entityEntityAttributeValue.getEntityInstanceAttributePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityEntityAttribute(EntityEntityAttribute entityEntityAttribute, BasePK deletedBy) {
        var entityAttribute = entityEntityAttribute.getEntityAttribute();
        var entityInstance = entityEntityAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityEntityAttribute.setThruTime(session.START_TIME_LONG);
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
    
    public EntityCollectionAttribute createEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityCollectionAttribute = EntityCollectionAttributeFactory.getInstance().create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityCollectionAttribute;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
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
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(session,
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

            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityCollectionAttribute = EntityCollectionAttributeFactory.getInstance().getEntityFromQuery(session,
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
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_entityinstanceattributeid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public EntityCollectionAttributeTransfer getEntityCollectionAttributeTransfer(UserVisit userVisit, EntityCollectionAttribute entityCollectionAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityCollectionAttributeTransferCache().getEntityCollectionAttributeTransfer(entityCollectionAttribute, entityInstance);
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, Collection<EntityCollectionAttribute> entityCollectionAttributes, EntityInstance entityInstance) {
        List<EntityCollectionAttributeTransfer> entityCollectionAttributeTransfers = new ArrayList<>(entityCollectionAttributes.size());
        var entityCollectionAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityCollectionAttributeTransferCache();
        
        entityCollectionAttributes.forEach((entityCollectionAttribute) ->
                entityCollectionAttributeTransfers.add(entityCollectionAttributeTransferCache.getEntityCollectionAttributeTransfer(entityCollectionAttribute, entityInstance))
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
            entityCollectionAttribute.setThruTime(session.START_TIME_LONG);
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
