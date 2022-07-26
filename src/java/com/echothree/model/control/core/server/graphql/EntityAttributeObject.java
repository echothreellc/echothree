// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeBlob;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeListItem;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeNumeric;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

@GraphQLDescription("entity attribute object")
@GraphQLName("EntityAttribute")
public class EntityAttributeObject
        extends BaseEntityInstanceObject {
    
    private final EntityAttribute entityAttribute; // Always Present
    private final EntityInstance entityInstance; // Optional
    
    public EntityAttributeObject(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        super(entityAttribute.getPrimaryKey());
        
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
    }

    private EntityAttributeDetail entityAttributeDetail; // Optional, use getEntityAttributeDetail()
    
    private EntityAttributeDetail getEntityAttributeDetail() {
        if(entityAttributeDetail == null) {
            entityAttributeDetail = entityAttribute.getLastDetail();
        }
        
        return entityAttributeDetail;
    }
    
    private EntityAttributeBlob entityAttributeBlob; // Optional, use getEntityAttributeBlob()
    
    private EntityAttributeBlob getEntityAttributeBlob() {
        if(entityAttributeBlob == null
                && isEntityAttributeTypeName(EntityAttributeTypes.BLOB.name())) {
            var coreControl = Session.getModelController(CoreControl.class);
    
            entityAttributeBlob = coreControl.getEntityAttributeBlob(entityAttribute);
        }
        
        return entityAttributeBlob;
    }
    
    private EntityAttributeString entityAttributeString; // Optional, use getEntityAttributeString()
    
    private EntityAttributeString getEntityAttributeString() {
        if(entityAttributeString == null
                && isEntityAttributeTypeName(EntityAttributeTypes.STRING.name())) {
            var coreControl = Session.getModelController(CoreControl.class);
    
            entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
        }
        
        return entityAttributeString;
    }
    
    private EntityAttributeInteger entityAttributeInteger; // Optional, use getEntityAttributeInteger()
    
    private EntityAttributeInteger getEntityAttributeInteger() {
        if(entityAttributeInteger == null
                && isEntityAttributeTypeName(EntityAttributeTypes.INTEGER.name())) {
            var coreControl = Session.getModelController(CoreControl.class);
    
            entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);
        }
        
        return entityAttributeInteger;
    }
    
    private EntityAttributeLong entityAttributeLong; // Optional, use getEntityAttributeLong()
    
    private EntityAttributeLong getEntityAttributeLong() {
        if(entityAttributeLong == null
                && isEntityAttributeTypeName(EntityAttributeTypes.LONG.name())) {
            var coreControl = Session.getModelController(CoreControl.class);
    
            entityAttributeLong = coreControl.getEntityAttributeLong(entityAttribute);
        }
        
        return entityAttributeLong;
    }

    private EntityAttributeNumeric entityAttributeNumeric; // Optional, use getEntityAttributeNumeric()

    private EntityAttributeNumeric getEntityAttributeNumeric() {
        if(entityAttributeNumeric == null
                && (isEntityAttributeTypeName(EntityAttributeTypes.INTEGER.name())
                || isEntityAttributeTypeName(EntityAttributeTypes.LONG.name()))) {
            var coreControl = Session.getModelController(CoreControl.class);

            entityAttributeNumeric = coreControl.getEntityAttributeNumeric(entityAttribute);
        }

        return entityAttributeNumeric;
    }

    private EntityAttributeListItem entityAttributeListItem; // Optional, use getEntityAttributeListItem()

    private EntityAttributeListItem getEntityAttributeListItem() {
        if(entityAttributeListItem == null
                && (isEntityAttributeTypeName(EntityAttributeTypes.LISTITEM.name())
                || isEntityAttributeTypeName(EntityAttributeTypes.MULTIPLELISTITEM.name()))) {
            var coreControl = Session.getModelController(CoreControl.class);

            entityAttributeListItem = coreControl.getEntityAttributeListItem(entityAttribute);
        }

        return entityAttributeListItem;
    }

    @GraphQLField
    @GraphQLDescription("entity type")
    public EntityTypeObject getEntityType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getInstance().getHasEntityTypeAccess(env) ? new EntityTypeObject(getEntityAttributeDetail().getEntityType()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute name")
    @GraphQLNonNull
    public String getEntityAttributeName() {
        return getEntityAttributeDetail().getEntityAttributeName();
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute type")
    @GraphQLNonNull
    public EntityAttributeTypeObject getEntityAttributeType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getInstance().getHasEntityAttributeTypeAccess(env) ? new EntityAttributeTypeObject(getEntityAttributeDetail().getEntityAttributeType()) : null;
    }
    
    protected boolean isEntityAttributeTypeName(String entityAttributeTypeName) {
        return getEntityAttributeDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(entityAttributeTypeName);
    }
    
    @GraphQLField
    @GraphQLDescription("track revisions")
    @GraphQLNonNull
    public boolean getTrackRevisions() {
        return getEntityAttributeDetail().getTrackRevisions();
    }
    
    @GraphQLField
    @GraphQLDescription("check content web address")
    @GraphQLNonNull
    public boolean getCheckContentWebAddress() {
        return getEntityAttributeBlob().getCheckContentWebAddress();
    }
    
    @GraphQLField
    @GraphQLDescription("validation pattern")
    public String getValidationPattern() {
        var entityAttributeString = getEntityAttributeString();
        
        return entityAttributeString == null ? null : entityAttributeString.getValidationPattern();
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted upper range integer value")
    public Integer getUnformattedUpperRangeIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getUpperRangeIntegerValue();
    }
    
    @GraphQLField
    @GraphQLDescription("upper range integer value")
    public String getUpperRangeIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getUpperRangeIntegerValue().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted upper limit integer value")
    public Integer getUnformattedUpperLimitIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getUpperLimitIntegerValue();
    }
    
    @GraphQLField
    @GraphQLDescription("upper limit integer value")
    public String getUpperLimitIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getUpperLimitIntegerValue().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted lower limit integer value")
    public Integer getUnformattedLowerLimitIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getLowerLimitIntegerValue();
    }
    
    @GraphQLField
    @GraphQLDescription("lower limit integer value")
    public String getLowerLimitIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getLowerLimitIntegerValue().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted lower range integer value")
    public Integer getUnformattedLowerRangeIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getLowerRangeIntegerValue();
    }
    
    @GraphQLField
    @GraphQLDescription("lower range integer value")
    public String getLowerRangeIntegerValue() {
        var entityAttributeInteger = getEntityAttributeInteger();
        
        return entityAttributeInteger == null ? null : entityAttributeInteger.getLowerRangeIntegerValue().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted upper range long value")
    public Long getUnformattedUpperRangeLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getUpperRangeLongValue();
    }
    
    @GraphQLField
    @GraphQLDescription("upper range long value")
    public String getUpperRangeLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getUpperRangeLongValue().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted upper limit long value")
    public Long getUnformattedUpperLimitLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getUpperLimitLongValue();
    }
    
    @GraphQLField
    @GraphQLDescription("upper limit long value")
    public String getUpperLimitLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getUpperLimitLongValue().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted lower limit long value")
    public Long getUnformattedLowerLimitLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getLowerLimitLongValue();
    }
    
    @GraphQLField
    @GraphQLDescription("lower limit long value")
    public String getLowerLimitLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getLowerLimitLongValue().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted lower range long value")
    public Long getUnformattedLowerRangeLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getLowerRangeLongValue();
    }
    
    @GraphQLField
    @GraphQLDescription("lower range long value")
    public String getLowerRangeLongValue() {
        var entityAttributeLong = getEntityAttributeLong();
        
        return entityAttributeLong == null ? null : entityAttributeLong.getLowerRangeLongValue().toString(); // TODO
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        var entityAttributeNumeric = getEntityAttributeNumeric();
        var unitOfMeasureType = entityAttributeNumeric == null ? null : entityAttributeNumeric.getUnitOfMeasureType();

        return unitOfMeasureType != null && UomSecurityUtils.getInstance().getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(unitOfMeasureType) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity list item sequence")
    public SequenceObject getEntityListItemSequence(final DataFetchingEnvironment env) {
        var entityAttributeListItem = getEntityAttributeListItem();
        var entityListItemSequence = entityAttributeListItem == null ? null : entityAttributeListItem.getEntityListItemSequence();

        return entityListItemSequence != null && SequenceSecurityUtils.getInstance().getHasSequenceAccess(env) ? new SequenceObject(entityListItemSequence) : null;
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityAttributeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = Session.getModelController(CoreControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return coreControl.getBestEntityAttributeDescription(entityAttribute, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("attribute")
    public AttributeInterface getAttribute(final DataFetchingEnvironment env) {
        AttributeInterface entityAttributeValueInterface = null;

        if(entityInstance != null) {
            var entityAttributeType = EntityAttributeTypes.valueOf(getEntityAttributeDetail().getEntityAttributeType().getEntityAttributeTypeName());
            var coreControl = Session.getModelController(CoreControl.class);

            switch(entityAttributeType) {
                // TODO: BLOB
                case BOOLEAN -> {
                    var entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityBooleanAttribute == null ? null : new EntityBooleanAttributeObject(entityBooleanAttribute);
                }
                case CLOB -> {
                    var userControl = Session.getModelController(UserControl.class);
                    var entityClobAttribute = coreControl.getBestEntityClobAttribute(entityAttribute, entityInstance, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));

                    entityAttributeValueInterface = entityClobAttribute == null ? null : new EntityClobAttributeObject(entityClobAttribute);
                }
                case COLLECTION -> {
                    entityAttributeValueInterface = new EntityCollectionAttributesObject(entityAttribute, entityInstance);
                }
                case DATE -> {
                    var entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityDateAttribute == null ? null : new EntityDateAttributeObject(entityDateAttribute);
                }
                case ENTITY -> {
                    var entityEntityAttribute = coreControl.getEntityEntityAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityEntityAttribute == null ? null : new EntityEntityAttributeObject(entityEntityAttribute);
                }
                case GEOPOINT -> {
                    var entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityGeoPointAttribute == null ? null : new EntityGeoPointAttributeObject(entityGeoPointAttribute);
                }
                case INTEGER -> {
                    var entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityIntegerAttribute == null ? null : new EntityIntegerAttributeObject(entityIntegerAttribute);
                }
                case LISTITEM -> {
                    var entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityListItemAttribute == null ? null : new EntityListItemAttributeObject(entityListItemAttribute);
                }
                case LONG -> {
                    var entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityLongAttribute == null ? null : new EntityLongAttributeObject(entityLongAttribute);
                }
                case MULTIPLELISTITEM -> {
                    entityAttributeValueInterface = new EntityMultipleListItemAttributesObject(entityAttribute, entityInstance);
                }
                case NAME -> {
                    var entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityNameAttribute == null ? null : new EntityNameAttributeObject(entityNameAttribute);
                }
                case STRING -> {
                    var userControl = Session.getModelController(UserControl.class);
                    var entityStringAttribute = coreControl.getBestEntityStringAttribute(entityAttribute, entityInstance, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));

                    entityAttributeValueInterface = entityStringAttribute == null ? null : new EntityStringAttributeObject(entityStringAttribute);
                }
                case TIME -> {
                    var entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);

                    entityAttributeValueInterface = entityTimeAttribute == null ? null : new EntityTimeAttributeObject(entityTimeAttribute);
                }
                default -> {} // Leave entityAttributeValueInterface null
            }
        }

        return entityAttributeValueInterface;
    }

    @GraphQLField
    @GraphQLDescription("entity list items")
    public Collection<EntityListItemObject> getEntityListItems(final DataFetchingEnvironment env) {
        Collection<EntityListItemObject> entityListItemObjects = null;

        if((isEntityAttributeTypeName(EntityAttributeTypes.LISTITEM.name())
                || isEntityAttributeTypeName(EntityAttributeTypes.MULTIPLELISTITEM.name()))
                && CoreSecurityUtils.getInstance().getHasEntityListItemsAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityListItems = coreControl.getEntityListItems(entityAttribute);

            entityListItemObjects = new ArrayList<>(entityListItems.size());

            for(var entityListItem : entityListItems) {
                entityListItemObjects.add(new EntityListItemObject(entityListItem));
            }
        }

        return entityListItemObjects;
    }

    @GraphQLField
    @GraphQLDescription("entity long ranges")
    public Collection<EntityLongRangeObject> getEntityLongRanges(final DataFetchingEnvironment env) {
        Collection<EntityLongRangeObject> entityLongRangeObjects = null;

        if(isEntityAttributeTypeName(EntityAttributeTypes.LONG.name())
                && CoreSecurityUtils.getInstance().getHasEntityLongRangesAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityLongRanges = coreControl.getEntityLongRanges(entityAttribute);

            entityLongRangeObjects = new ArrayList<>(entityLongRanges.size());

            for(var entityLongRange : entityLongRanges) {
                entityLongRangeObjects.add(new EntityLongRangeObject(entityLongRange));
            }
        }

        return entityLongRangeObjects;
    }

    @GraphQLField
    @GraphQLDescription("entity integer ranges")
    public Collection<EntityIntegerRangeObject> getEntityIntegerRanges(final DataFetchingEnvironment env) {
        Collection<EntityIntegerRangeObject> entityIntegerRangeObjects = null;

        if(isEntityAttributeTypeName(EntityAttributeTypes.INTEGER.name())
                && CoreSecurityUtils.getInstance().getHasEntityIntegerRangesAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityIntegerRanges = coreControl.getEntityIntegerRanges(entityAttribute);

            entityIntegerRangeObjects = new ArrayList<>(entityIntegerRanges.size());

            for(var entityIntegerRange : entityIntegerRanges) {
                entityIntegerRangeObjects.add(new EntityIntegerRangeObject(entityIntegerRange));
            }
        }

        return entityIntegerRangeObjects;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute entity attribute groups")
    public Collection<EntityAttributeEntityAttributeGroupObject> getEntityAttributeEntityAttributeGroups(final DataFetchingEnvironment env) {
        Collection<EntityAttributeEntityAttributeGroupObject> entityAttributeEntityAttributeGroupObjects = null;

        if(CoreSecurityUtils.getInstance().getHasEntityAttributeEntityAttributeGroupsAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityAttributeEntityAttributeGroups = coreControl.getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute);

            entityAttributeEntityAttributeGroupObjects = new ArrayList<>(entityAttributeEntityAttributeGroups.size());

            for(var entityAttributeEntityAttributeGroup : entityAttributeEntityAttributeGroups) {
                entityAttributeEntityAttributeGroupObjects.add(new EntityAttributeEntityAttributeGroupObject(entityAttributeEntityAttributeGroup));
            }
        }

        return entityAttributeEntityAttributeGroupObjects;
    }

}
