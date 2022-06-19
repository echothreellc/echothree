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
    @GraphQLDescription("entity boolean attribute")
    public EntityBooleanAttributeObject getEntityBooleanAttribute(final DataFetchingEnvironment env) {
        EntityBooleanAttributeObject entityBooleanAttributeObject = null;
        
        if(isEntityAttributeTypeName(EntityAttributeTypes.BOOLEAN.name()) && entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance);
            
            entityBooleanAttributeObject = entityBooleanAttribute == null ? null : new EntityBooleanAttributeObject(entityBooleanAttribute);
        }
        
        return entityBooleanAttributeObject;
    }
    
    @GraphQLField
    @GraphQLDescription("entity integer attribute")
    public EntityIntegerAttributeObject getEntityIntegerAttribute(final DataFetchingEnvironment env) {
        EntityIntegerAttributeObject entityIntegerAttributeObject = null;
        
        if(isEntityAttributeTypeName(EntityAttributeTypes.INTEGER.name()) && entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);
            
            entityIntegerAttributeObject = entityIntegerAttribute == null ? null : new EntityIntegerAttributeObject(entityIntegerAttribute);
        }
        
        return entityIntegerAttributeObject;
    }
    
    @GraphQLField
    @GraphQLDescription("entity long attribute")
    public EntityLongAttributeObject getEntityLongAttribute(final DataFetchingEnvironment env) {
        EntityLongAttributeObject entityLongAttributeObject = null;
        
        if(isEntityAttributeTypeName(EntityAttributeTypes.LONG.name()) && entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);
            
            entityLongAttributeObject = entityLongAttribute == null ? null : new EntityLongAttributeObject(entityLongAttribute);
        }
        
        return entityLongAttributeObject;
    }

    @GraphQLField
    @GraphQLDescription("entity name attribute")
    public EntityNameAttributeObject getEntityNameAttribute(final DataFetchingEnvironment env) {
        EntityNameAttributeObject entityNameAttributeObject = null;

        if(isEntityAttributeTypeName(EntityAttributeTypes.NAME.name()) && entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);

            entityNameAttributeObject = entityNameAttribute == null ? null : new EntityNameAttributeObject(entityNameAttribute);
        }

        return entityNameAttributeObject;
    }

    @GraphQLField
    @GraphQLDescription("entity string attribute")
    public EntityStringAttributeObject getEntityStringAttribute(final DataFetchingEnvironment env) {
        EntityStringAttributeObject entityStringAttributeObject = null;

        if(isEntityAttributeTypeName(EntityAttributeTypes.STRING.name()) && entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var userControl = Session.getModelController(UserControl.class);
            var entityStringAttribute = coreControl.getBestEntityStringAttribute(entityAttribute, entityInstance, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));

            entityStringAttributeObject = entityStringAttribute == null ? null : new EntityStringAttributeObject(entityStringAttribute);
        }

        return entityStringAttributeObject;
    }

    @GraphQLField
    @GraphQLDescription("entity list items")
    public Collection<EntityListItemObject> getEntityListItems(final DataFetchingEnvironment env) {
        Collection<EntityListItemObject> entityListItemObjects = null;

        if(isEntityAttributeTypeName(EntityAttributeTypes.LISTITEM.name())
                || isEntityAttributeTypeName(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
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
    @GraphQLDescription("entity list item attribute")
    public EntityListItemAttributeObject getEntityListItemAttribute(final DataFetchingEnvironment env) {
        EntityListItemAttributeObject entityListItemAttributeObject = null;
        
        if(isEntityAttributeTypeName(EntityAttributeTypes.LISTITEM.name()) && entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);
            
            entityListItemAttributeObject = entityListItemAttribute == null ? null : new EntityListItemAttributeObject(entityListItemAttribute);
        }
        
        return entityListItemAttributeObject;
    }
    
    @GraphQLField
    @GraphQLDescription("entity multiple list item attribute")
    public Collection<EntityMultipleListItemAttributeObject> getEntityMultipleListItemAttributes(final DataFetchingEnvironment env) {
        Collection<EntityMultipleListItemAttributeObject> entityMultipleListItemAttributeObjects = null;
        
        if(isEntityAttributeTypeName(EntityAttributeTypes.MULTIPLELISTITEM.name()) && entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityMultipleListItemAttributes = coreControl.getEntityMultipleListItemAttributes(entityAttribute, entityInstance);
            
            entityMultipleListItemAttributeObjects = new ArrayList<>(entityMultipleListItemAttributes.size());
            
            for(var entityMultipleListItemAttribute : entityMultipleListItemAttributes) {
                entityMultipleListItemAttributeObjects.add(new EntityMultipleListItemAttributeObject(entityMultipleListItemAttribute));
            }
        }
        
        return entityMultipleListItemAttributeObjects;
    }
    
}
