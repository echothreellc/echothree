// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.core.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class EntityAttributeTransfer
        extends BaseTransfer {
    
    private EntityTypeTransfer entityType;
    private String entityAttributeName;
    private EntityAttributeTypeTransfer entityAttributeType;
    private boolean trackRevisions;
    private Integer sortOrder;
    private String description;
    
    private boolean checkContentWebAddress;
    private String validationPattern;
    private Integer upperRangeIntegerValue;
    private Integer upperLimitIntegerValue;
    private Integer lowerLimitIntegerValue;
    private Integer lowerRangeIntegerValue;
    private Long upperRangeLongValue;
    private Long upperLimitLongValue;
    private Long lowerLimitLongValue;
    private Long lowerRangeLongValue;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private SequenceTransfer entityListItemSequence;
    
    private EntityBooleanAttributeTransfer entityBooleanAttribute;
    private EntityNameAttributeTransfer entityNameAttribute;
    private EntityIntegerAttributeTransfer entityIntegerAttribute;
    private EntityLongAttributeTransfer entityLongAttribute;
    private EntityStringAttributeTransfer entityStringAttribute;
    private EntityGeoPointAttributeTransfer entityGeoPointAttribute;
    private EntityBlobAttributeTransfer entityBlobAttribute;
    private EntityClobAttributeTransfer entityClobAttribute;
    private EntityEntityAttributeTransfer entityEntityAttribute;
    private ListWrapper<EntityCollectionAttributeTransfer> entityCollectionAttributes;
    private EntityDateAttributeTransfer entityDateAttribute;
    private EntityTimeAttributeTransfer entityTimeAttribute;
    private EntityListItemAttributeTransfer entityListItemAttribute;
    private ListWrapper<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributes;
    
    private ListWrapper<EntityListItemTransfer> entityListItems;
    private ListWrapper<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypes;
    
    /** Creates a new instance of EntityAttributeTransfer */
    public EntityAttributeTransfer(EntityTypeTransfer entityType, EntityAttributeTypeTransfer entityAttributeType,
            String entityAttributeName, boolean trackRevisions, Integer sortOrder, String description) {
        this.entityType = entityType;
        this.entityAttributeName = entityAttributeName;
        this.entityAttributeType = entityAttributeType;
        this.trackRevisions = trackRevisions;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the entityType
     */
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }

    /**
     * @param entityType the entityType to set
     */
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }

    /**
     * @return the entityAttributeName
     */
    public String getEntityAttributeName() {
        return entityAttributeName;
    }

    /**
     * @param entityAttributeName the entityAttributeName to set
     */
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }

    /**
     * @return the entityAttributeType
     */
    public EntityAttributeTypeTransfer getEntityAttributeType() {
        return entityAttributeType;
    }

    /**
     * @param entityAttributeType the entityAttributeType to set
     */
    public void setEntityAttributeType(EntityAttributeTypeTransfer entityAttributeType) {
        this.entityAttributeType = entityAttributeType;
    }

    /**
     * @return the trackRevisions
     */
    public boolean getTrackRevisions() {
        return trackRevisions;
    }

    /**
     * @param trackRevisions the trackRevisions to set
     */
    public void setTrackRevisions(boolean trackRevisions) {
        this.trackRevisions = trackRevisions;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the checkContentWebAddress
     */
    public boolean isCheckContentWebAddress() {
        return checkContentWebAddress;
    }

    /**
     * @param checkContentWebAddress the checkContentWebAddress to set
     */
    public void setCheckContentWebAddress(boolean checkContentWebAddress) {
        this.checkContentWebAddress = checkContentWebAddress;
    }

    /**
     * @return the validationPattern
     */
    public String getValidationPattern() {
        return validationPattern;
    }

    /**
     * @param validationPattern the validationPattern to set
     */
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    /**
     * @return the upperRangeIntegerValue
     */
    public Integer getUpperRangeIntegerValue() {
        return upperRangeIntegerValue;
    }

    /**
     * @param upperRangeIntegerValue the upperRangeIntegerValue to set
     */
    public void setUpperRangeIntegerValue(Integer upperRangeIntegerValue) {
        this.upperRangeIntegerValue = upperRangeIntegerValue;
    }

    /**
     * @return the upperLimitIntegerValue
     */
    public Integer getUpperLimitIntegerValue() {
        return upperLimitIntegerValue;
    }

    /**
     * @param upperLimitIntegerValue the upperLimitIntegerValue to set
     */
    public void setUpperLimitIntegerValue(Integer upperLimitIntegerValue) {
        this.upperLimitIntegerValue = upperLimitIntegerValue;
    }

    /**
     * @return the lowerLimitIntegerValue
     */
    public Integer getLowerLimitIntegerValue() {
        return lowerLimitIntegerValue;
    }

    /**
     * @param lowerLimitIntegerValue the lowerLimitIntegerValue to set
     */
    public void setLowerLimitIntegerValue(Integer lowerLimitIntegerValue) {
        this.lowerLimitIntegerValue = lowerLimitIntegerValue;
    }

    /**
     * @return the lowerRangeIntegerValue
     */
    public Integer getLowerRangeIntegerValue() {
        return lowerRangeIntegerValue;
    }

    /**
     * @param lowerRangeIntegerValue the lowerRangeIntegerValue to set
     */
    public void setLowerRangeIntegerValue(Integer lowerRangeIntegerValue) {
        this.lowerRangeIntegerValue = lowerRangeIntegerValue;
    }

    /**
     * @return the upperRangeLongValue
     */
    public Long getUpperRangeLongValue() {
        return upperRangeLongValue;
    }

    /**
     * @param upperRangeLongValue the upperRangeLongValue to set
     */
    public void setUpperRangeLongValue(Long upperRangeLongValue) {
        this.upperRangeLongValue = upperRangeLongValue;
    }

    /**
     * @return the upperLimitLongValue
     */
    public Long getUpperLimitLongValue() {
        return upperLimitLongValue;
    }

    /**
     * @param upperLimitLongValue the upperLimitLongValue to set
     */
    public void setUpperLimitLongValue(Long upperLimitLongValue) {
        this.upperLimitLongValue = upperLimitLongValue;
    }

    /**
     * @return the lowerLimitLongValue
     */
    public Long getLowerLimitLongValue() {
        return lowerLimitLongValue;
    }

    /**
     * @param lowerLimitLongValue the lowerLimitLongValue to set
     */
    public void setLowerLimitLongValue(Long lowerLimitLongValue) {
        this.lowerLimitLongValue = lowerLimitLongValue;
    }

    /**
     * @return the lowerRangeLongValue
     */
    public Long getLowerRangeLongValue() {
        return lowerRangeLongValue;
    }

    /**
     * @param lowerRangeLongValue the lowerRangeLongValue to set
     */
    public void setLowerRangeLongValue(Long lowerRangeLongValue) {
        this.lowerRangeLongValue = lowerRangeLongValue;
    }

    /**
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * @return the entityListItemSequence
     */
    public SequenceTransfer getEntityListItemSequence() {
        return entityListItemSequence;
    }

    /**
     * @param entityListItemSequence the entityListItemSequence to set
     */
    public void setEntityListItemSequence(SequenceTransfer entityListItemSequence) {
        this.entityListItemSequence = entityListItemSequence;
    }

    /**
     * @return the entityBooleanAttribute
     */
    public EntityBooleanAttributeTransfer getEntityBooleanAttribute() {
        return entityBooleanAttribute;
    }

    /**
     * @param entityBooleanAttribute the entityBooleanAttribute to set
     */
    public void setEntityBooleanAttribute(EntityBooleanAttributeTransfer entityBooleanAttribute) {
        this.entityBooleanAttribute = entityBooleanAttribute;
    }

    /**
     * @return the entityNameAttribute
     */
    public EntityNameAttributeTransfer getEntityNameAttribute() {
        return entityNameAttribute;
    }

    /**
     * @param entityNameAttribute the entityNameAttribute to set
     */
    public void setEntityNameAttribute(EntityNameAttributeTransfer entityNameAttribute) {
        this.entityNameAttribute = entityNameAttribute;
    }

    /**
     * @return the entityIntegerAttribute
     */
    public EntityIntegerAttributeTransfer getEntityIntegerAttribute() {
        return entityIntegerAttribute;
    }

    /**
     * @param entityIntegerAttribute the entityIntegerAttribute to set
     */
    public void setEntityIntegerAttribute(EntityIntegerAttributeTransfer entityIntegerAttribute) {
        this.entityIntegerAttribute = entityIntegerAttribute;
    }

    /**
     * @return the entityLongAttribute
     */
    public EntityLongAttributeTransfer getEntityLongAttribute() {
        return entityLongAttribute;
    }

    /**
     * @param entityLongAttribute the entityLongAttribute to set
     */
    public void setEntityLongAttribute(EntityLongAttributeTransfer entityLongAttribute) {
        this.entityLongAttribute = entityLongAttribute;
    }

    /**
     * @return the entityStringAttribute
     */
    public EntityStringAttributeTransfer getEntityStringAttribute() {
        return entityStringAttribute;
    }

    /**
     * @param entityStringAttribute the entityStringAttribute to set
     */
    public void setEntityStringAttribute(EntityStringAttributeTransfer entityStringAttribute) {
        this.entityStringAttribute = entityStringAttribute;
    }

    /**
     * @return the entityGeoPointAttribute
     */
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttribute() {
        return entityGeoPointAttribute;
    }

    /**
     * @param entityGeoPointAttribute the entityGeoPointAttribute to set
     */
    public void setEntityGeoPointAttribute(EntityGeoPointAttributeTransfer entityGeoPointAttribute) {
        this.entityGeoPointAttribute = entityGeoPointAttribute;
    }

    /**
     * @return the entityBlobAttribute
     */
    public EntityBlobAttributeTransfer getEntityBlobAttribute() {
        return entityBlobAttribute;
    }

    /**
     * @param entityBlobAttribute the entityBlobAttribute to set
     */
    public void setEntityBlobAttribute(EntityBlobAttributeTransfer entityBlobAttribute) {
        this.entityBlobAttribute = entityBlobAttribute;
    }

    /**
     * @return the entityClobAttribute
     */
    public EntityClobAttributeTransfer getEntityClobAttribute() {
        return entityClobAttribute;
    }

    /**
     * @param entityClobAttribute the entityClobAttribute to set
     */
    public void setEntityClobAttribute(EntityClobAttributeTransfer entityClobAttribute) {
        this.entityClobAttribute = entityClobAttribute;
    }

    /**
     * @return the entityEntityAttribute
     */
    public EntityEntityAttributeTransfer getEntityEntityAttribute() {
        return entityEntityAttribute;
    }

    /**
     * @param entityEntityAttribute the entityEntityAttribute to set
     */
    public void setEntityEntityAttribute(EntityEntityAttributeTransfer entityEntityAttribute) {
        this.entityEntityAttribute = entityEntityAttribute;
    }

    /**
     * @return the entityCollectionAttributes
     */
    public ListWrapper<EntityCollectionAttributeTransfer> getEntityCollectionAttributes() {
        return entityCollectionAttributes;
    }

    /**
     * @param entityCollectionAttributes the entityCollectionAttributes to set
     */
    public void setEntityCollectionAttributes(ListWrapper<EntityCollectionAttributeTransfer> entityCollectionAttributes) {
        this.entityCollectionAttributes = entityCollectionAttributes;
    }

    /**
     * @return the entityDateAttribute
     */
    public EntityDateAttributeTransfer getEntityDateAttribute() {
        return entityDateAttribute;
    }

    /**
     * @param entityDateAttribute the entityDateAttribute to set
     */
    public void setEntityDateAttribute(EntityDateAttributeTransfer entityDateAttribute) {
        this.entityDateAttribute = entityDateAttribute;
    }

    /**
     * @return the entityTimeAttribute
     */
    public EntityTimeAttributeTransfer getEntityTimeAttribute() {
        return entityTimeAttribute;
    }

    /**
     * @param entityTimeAttribute the entityTimeAttribute to set
     */
    public void setEntityTimeAttribute(EntityTimeAttributeTransfer entityTimeAttribute) {
        this.entityTimeAttribute = entityTimeAttribute;
    }

    /**
     * @return the entityListItemAttribute
     */
    public EntityListItemAttributeTransfer getEntityListItemAttribute() {
        return entityListItemAttribute;
    }

    /**
     * @param entityListItemAttribute the entityListItemAttribute to set
     */
    public void setEntityListItemAttribute(EntityListItemAttributeTransfer entityListItemAttribute) {
        this.entityListItemAttribute = entityListItemAttribute;
    }

    /**
     * @return the entityMultipleListItemAttributes
     */
    public ListWrapper<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributes() {
        return entityMultipleListItemAttributes;
    }

    /**
     * @param entityMultipleListItemAttributes the entityMultipleListItemAttributes to set
     */
    public void setEntityMultipleListItemAttributes(ListWrapper<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributes) {
        this.entityMultipleListItemAttributes = entityMultipleListItemAttributes;
    }

    /**
     * @return the entityListItems
     */
    public ListWrapper<EntityListItemTransfer> getEntityListItems() {
        return entityListItems;
    }

    /**
     * @param entityListItems the entityListItems to set
     */
    public void setEntityListItems(ListWrapper<EntityListItemTransfer> entityListItems) {
        this.entityListItems = entityListItems;
    }

    /**
     * @return the entityAttributeEntityTypes
     */
    public ListWrapper<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypes() {
        return entityAttributeEntityTypes;
    }

    /**
     * @param entityAttributeEntityTypes the entityAttributeEntityTypes to set
     */
    public void setEntityAttributeEntityTypes(ListWrapper<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypes) {
        this.entityAttributeEntityTypes = entityAttributeEntityTypes;
    }

}
