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
    private Boolean trackRevisions;
    private Integer sortOrder;
    private String description;
    
    private Boolean checkContentWebAddress;
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

    private EntityBooleanDefaultTransfer entityBooleanDefault;
    private EntityBooleanAttributeTransfer entityBooleanAttribute;
    private EntityNameAttributeTransfer entityNameAttribute;
    private EntityIntegerDefaultTransfer entityIntegerDefault;
    private EntityIntegerAttributeTransfer entityIntegerAttribute;
    private EntityLongDefaultTransfer entityLongDefault;
    private EntityLongAttributeTransfer entityLongAttribute;
    private EntityStringDefaultTransfer entityStringDefault;
    private EntityStringAttributeTransfer entityStringAttribute;
    private EntityGeoPointAttributeTransfer entityGeoPointAttribute;
    private EntityBlobAttributeTransfer entityBlobAttribute;
    private EntityClobAttributeTransfer entityClobAttribute;
    private EntityEntityAttributeTransfer entityEntityAttribute;
    private ListWrapper<EntityCollectionAttributeTransfer> entityCollectionAttributes;
    private EntityDateDefaultTransfer entityDateDefault;
    private EntityDateAttributeTransfer entityDateAttribute;
    private EntityTimeAttributeTransfer entityTimeAttribute;
    private EntityListItemDefaultTransfer entityListItemDefault;
    private EntityListItemAttributeTransfer entityListItemAttribute;
    private ListWrapper<EntityMultipleListItemDefaultTransfer> entityMultipleListItemDefaults;
    private ListWrapper<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributes;

    private Long entityListItemsCount;
    private ListWrapper<EntityListItemTransfer> entityListItems;
    private Long entityAttributeEntityTypesCount;
    private ListWrapper<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypes;

    /** Creates a new instance of EntityAttributeTransfer */
    public EntityAttributeTransfer(EntityTypeTransfer entityType, EntityAttributeTypeTransfer entityAttributeType,
            String entityAttributeName, Boolean trackRevisions, Integer sortOrder, String description) {
        this.entityType = entityType;
        this.entityAttributeName = entityAttributeName;
        this.entityAttributeType = entityAttributeType;
        this.trackRevisions = trackRevisions;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the entityType.
     * @return the entityType
     */
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }

    /**
     * Sets the entityType.
     * @param entityType the entityType to set
     */
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }

    /**
     * Returns the entityAttributeName.
     * @return the entityAttributeName
     */
    public String getEntityAttributeName() {
        return entityAttributeName;
    }

    /**
     * Sets the entityAttributeName.
     * @param entityAttributeName the entityAttributeName to set
     */
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }

    /**
     * Returns the entityAttributeType.
     * @return the entityAttributeType
     */
    public EntityAttributeTypeTransfer getEntityAttributeType() {
        return entityAttributeType;
    }

    /**
     * Sets the entityAttributeType.
     * @param entityAttributeType the entityAttributeType to set
     */
    public void setEntityAttributeType(EntityAttributeTypeTransfer entityAttributeType) {
        this.entityAttributeType = entityAttributeType;
    }

    /**
     * Returns the trackRevisions.
     * @return the trackRevisions
     */
    public boolean getTrackRevisions() {
        return trackRevisions;
    }

    /**
     * Sets the trackRevisions.
     * @param trackRevisions the trackRevisions to set
     */
    public void setTrackRevisions(boolean trackRevisions) {
        this.trackRevisions = trackRevisions;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the checkContentWebAddress.
     * @return the checkContentWebAddress
     */
    public Boolean getCheckContentWebAddress() {
        return checkContentWebAddress;
    }

    /**
     * Sets the checkContentWebAddress.
     * @param checkContentWebAddress the checkContentWebAddress to set
     */
    public void setCheckContentWebAddress(Boolean checkContentWebAddress) {
        this.checkContentWebAddress = checkContentWebAddress;
    }

    /**
     * Returns the validationPattern.
     * @return the validationPattern
     */
    public String getValidationPattern() {
        return validationPattern;
    }

    /**
     * Sets the validationPattern.
     * @param validationPattern the validationPattern to set
     */
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    /**
     * Returns the upperRangeIntegerValue.
     * @return the upperRangeIntegerValue
     */
    public Integer getUpperRangeIntegerValue() {
        return upperRangeIntegerValue;
    }

    /**
     * Sets the upperRangeIntegerValue.
     * @param upperRangeIntegerValue the upperRangeIntegerValue to set
     */
    public void setUpperRangeIntegerValue(Integer upperRangeIntegerValue) {
        this.upperRangeIntegerValue = upperRangeIntegerValue;
    }

    /**
     * Returns the upperLimitIntegerValue.
     * @return the upperLimitIntegerValue
     */
    public Integer getUpperLimitIntegerValue() {
        return upperLimitIntegerValue;
    }

    /**
     * Sets the upperLimitIntegerValue.
     * @param upperLimitIntegerValue the upperLimitIntegerValue to set
     */
    public void setUpperLimitIntegerValue(Integer upperLimitIntegerValue) {
        this.upperLimitIntegerValue = upperLimitIntegerValue;
    }

    /**
     * Returns the lowerLimitIntegerValue.
     * @return the lowerLimitIntegerValue
     */
    public Integer getLowerLimitIntegerValue() {
        return lowerLimitIntegerValue;
    }

    /**
     * Sets the lowerLimitIntegerValue.
     * @param lowerLimitIntegerValue the lowerLimitIntegerValue to set
     */
    public void setLowerLimitIntegerValue(Integer lowerLimitIntegerValue) {
        this.lowerLimitIntegerValue = lowerLimitIntegerValue;
    }

    /**
     * Returns the lowerRangeIntegerValue.
     * @return the lowerRangeIntegerValue
     */
    public Integer getLowerRangeIntegerValue() {
        return lowerRangeIntegerValue;
    }

    /**
     * Sets the lowerRangeIntegerValue.
     * @param lowerRangeIntegerValue the lowerRangeIntegerValue to set
     */
    public void setLowerRangeIntegerValue(Integer lowerRangeIntegerValue) {
        this.lowerRangeIntegerValue = lowerRangeIntegerValue;
    }

    /**
     * Returns the upperRangeLongValue.
     * @return the upperRangeLongValue
     */
    public Long getUpperRangeLongValue() {
        return upperRangeLongValue;
    }

    /**
     * Sets the upperRangeLongValue.
     * @param upperRangeLongValue the upperRangeLongValue to set
     */
    public void setUpperRangeLongValue(Long upperRangeLongValue) {
        this.upperRangeLongValue = upperRangeLongValue;
    }

    /**
     * Returns the upperLimitLongValue.
     * @return the upperLimitLongValue
     */
    public Long getUpperLimitLongValue() {
        return upperLimitLongValue;
    }

    /**
     * Sets the upperLimitLongValue.
     * @param upperLimitLongValue the upperLimitLongValue to set
     */
    public void setUpperLimitLongValue(Long upperLimitLongValue) {
        this.upperLimitLongValue = upperLimitLongValue;
    }

    /**
     * Returns the lowerLimitLongValue.
     * @return the lowerLimitLongValue
     */
    public Long getLowerLimitLongValue() {
        return lowerLimitLongValue;
    }

    /**
     * Sets the lowerLimitLongValue.
     * @param lowerLimitLongValue the lowerLimitLongValue to set
     */
    public void setLowerLimitLongValue(Long lowerLimitLongValue) {
        this.lowerLimitLongValue = lowerLimitLongValue;
    }

    /**
     * Returns the lowerRangeLongValue.
     * @return the lowerRangeLongValue
     */
    public Long getLowerRangeLongValue() {
        return lowerRangeLongValue;
    }

    /**
     * Sets the lowerRangeLongValue.
     * @param lowerRangeLongValue the lowerRangeLongValue to set
     */
    public void setLowerRangeLongValue(Long lowerRangeLongValue) {
        this.lowerRangeLongValue = lowerRangeLongValue;
    }

    /**
     * Returns the unitOfMeasureType.
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * Sets the unitOfMeasureType.
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * Returns the entityListItemSequence.
     * @return the entityListItemSequence
     */
    public SequenceTransfer getEntityListItemSequence() {
        return entityListItemSequence;
    }

    /**
     * Sets the entityListItemSequence.
     * @param entityListItemSequence the entityListItemSequence to set
     */
    public void setEntityListItemSequence(SequenceTransfer entityListItemSequence) {
        this.entityListItemSequence = entityListItemSequence;
    }

    /**
     * Returns the entityBooleanDefault.
     * @return the entityBooleanDefault
     */
    public EntityBooleanDefaultTransfer getEntityBooleanDefault() {
        return entityBooleanDefault;
    }

    /**
     * Sets the entityBooleanDefault.
     * @param entityBooleanDefault the entityBooleanDefault to set
     */
    public void setEntityBooleanDefault(EntityBooleanDefaultTransfer entityBooleanDefault) {
        this.entityBooleanDefault = entityBooleanDefault;
    }

    /**
     * Returns the entityBooleanAttribute.
     * @return the entityBooleanAttribute
     */
    public EntityBooleanAttributeTransfer getEntityBooleanAttribute() {
        return entityBooleanAttribute;
    }

    /**
     * Sets the entityBooleanAttribute.
     * @param entityBooleanAttribute the entityBooleanAttribute to set
     */
    public void setEntityBooleanAttribute(EntityBooleanAttributeTransfer entityBooleanAttribute) {
        this.entityBooleanAttribute = entityBooleanAttribute;
    }

    /**
     * Returns the entityNameAttribute.
     * @return the entityNameAttribute
     */
    public EntityNameAttributeTransfer getEntityNameAttribute() {
        return entityNameAttribute;
    }

    /**
     * Sets the entityNameAttribute.
     * @param entityNameAttribute the entityNameAttribute to set
     */
    public void setEntityNameAttribute(EntityNameAttributeTransfer entityNameAttribute) {
        this.entityNameAttribute = entityNameAttribute;
    }

    /**
     * Returns the entityIntegerDefault.
     * @return the entityIntegerDefault
     */
    public EntityIntegerDefaultTransfer getEntityIntegerDefault() {
        return entityIntegerDefault;
    }

    /**
     * Sets the entityIntegerDefault.
     * @param entityIntegerDefault the entityIntegerDefault to set
     */
    public void setEntityIntegerDefault(EntityIntegerDefaultTransfer entityIntegerDefault) {
        this.entityIntegerDefault = entityIntegerDefault;
    }

    /**
     * Returns the entityIntegerAttribute.
     * @return the entityIntegerAttribute
     */
    public EntityIntegerAttributeTransfer getEntityIntegerAttribute() {
        return entityIntegerAttribute;
    }

    /**
     * Sets the entityIntegerAttribute.
     * @param entityIntegerAttribute the entityIntegerAttribute to set
     */
    public void setEntityIntegerAttribute(EntityIntegerAttributeTransfer entityIntegerAttribute) {
        this.entityIntegerAttribute = entityIntegerAttribute;
    }

    /**
     * Returns the entityLongDefault.
     * @return the entityLongDefault
     */
    public EntityLongDefaultTransfer getEntityLongDefault() {
        return entityLongDefault;
    }

    /**
     * Sets the entityLongDefault.
     * @param entityLongDefault the entityLongDefault to set
     */
    public void setEntityLongDefault(EntityLongDefaultTransfer entityLongDefault) {
        this.entityLongDefault = entityLongDefault;
    }

    /**
     * Returns the entityLongAttribute.
     * @return the entityLongAttribute
     */
    public EntityLongAttributeTransfer getEntityLongAttribute() {
        return entityLongAttribute;
    }

    /**
     * Sets the entityLongAttribute.
     * @param entityLongAttribute the entityLongAttribute to set
     */
    public void setEntityLongAttribute(EntityLongAttributeTransfer entityLongAttribute) {
        this.entityLongAttribute = entityLongAttribute;
    }

    /**
     * Returns the entityStringDefault.
     * @return the entityStringDefault
     */
    public EntityStringDefaultTransfer getEntityStringDefault() {
        return entityStringDefault;
    }

    /**
     * Sets the entityStringDefault.
     * @param entityStringDefault the entityStringDefault to set
     */
    public void setEntityStringDefault(EntityStringDefaultTransfer entityStringDefault) {
        this.entityStringDefault = entityStringDefault;
    }

    /**
     * Returns the entityStringAttribute.
     * @return the entityStringAttribute
     */
    public EntityStringAttributeTransfer getEntityStringAttribute() {
        return entityStringAttribute;
    }

    /**
     * Sets the entityStringAttribute.
     * @param entityStringAttribute the entityStringAttribute to set
     */
    public void setEntityStringAttribute(EntityStringAttributeTransfer entityStringAttribute) {
        this.entityStringAttribute = entityStringAttribute;
    }

    /**
     * Returns the entityGeoPointAttribute.
     * @return the entityGeoPointAttribute
     */
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttribute() {
        return entityGeoPointAttribute;
    }

    /**
     * Sets the entityGeoPointAttribute.
     * @param entityGeoPointAttribute the entityGeoPointAttribute to set
     */
    public void setEntityGeoPointAttribute(EntityGeoPointAttributeTransfer entityGeoPointAttribute) {
        this.entityGeoPointAttribute = entityGeoPointAttribute;
    }

    /**
     * Returns the entityBlobAttribute.
     * @return the entityBlobAttribute
     */
    public EntityBlobAttributeTransfer getEntityBlobAttribute() {
        return entityBlobAttribute;
    }

    /**
     * Sets the entityBlobAttribute.
     * @param entityBlobAttribute the entityBlobAttribute to set
     */
    public void setEntityBlobAttribute(EntityBlobAttributeTransfer entityBlobAttribute) {
        this.entityBlobAttribute = entityBlobAttribute;
    }

    /**
     * Returns the entityClobAttribute.
     * @return the entityClobAttribute
     */
    public EntityClobAttributeTransfer getEntityClobAttribute() {
        return entityClobAttribute;
    }

    /**
     * Sets the entityClobAttribute.
     * @param entityClobAttribute the entityClobAttribute to set
     */
    public void setEntityClobAttribute(EntityClobAttributeTransfer entityClobAttribute) {
        this.entityClobAttribute = entityClobAttribute;
    }

    /**
     * Returns the entityEntityAttribute.
     * @return the entityEntityAttribute
     */
    public EntityEntityAttributeTransfer getEntityEntityAttribute() {
        return entityEntityAttribute;
    }

    /**
     * Sets the entityEntityAttribute.
     * @param entityEntityAttribute the entityEntityAttribute to set
     */
    public void setEntityEntityAttribute(EntityEntityAttributeTransfer entityEntityAttribute) {
        this.entityEntityAttribute = entityEntityAttribute;
    }

    /**
     * Returns the entityCollectionAttributes.
     * @return the entityCollectionAttributes
     */
    public ListWrapper<EntityCollectionAttributeTransfer> getEntityCollectionAttributes() {
        return entityCollectionAttributes;
    }

    /**
     * Sets the entityCollectionAttributes.
     * @param entityCollectionAttributes the entityCollectionAttributes to set
     */
    public void setEntityCollectionAttributes(ListWrapper<EntityCollectionAttributeTransfer> entityCollectionAttributes) {
        this.entityCollectionAttributes = entityCollectionAttributes;
    }

    /**
     * Returns the entityDateDefault.
     * @return the entityDateDefault
     */
    public EntityDateDefaultTransfer getEntityDateDefault() {
        return entityDateDefault;
    }

    /**
     * Sets the entityDateDefault.
     * @param entityDateDefault the entityDateDefault to set
     */
    public void setEntityDateDefault(EntityDateDefaultTransfer entityDateDefault) {
        this.entityDateDefault = entityDateDefault;
    }

    /**
     * Returns the entityDateAttribute.
     * @return the entityDateAttribute
     */
    public EntityDateAttributeTransfer getEntityDateAttribute() {
        return entityDateAttribute;
    }

    /**
     * Sets the entityDateAttribute.
     * @param entityDateAttribute the entityDateAttribute to set
     */
    public void setEntityDateAttribute(EntityDateAttributeTransfer entityDateAttribute) {
        this.entityDateAttribute = entityDateAttribute;
    }

    /**
     * Returns the entityTimeAttribute.
     * @return the entityTimeAttribute
     */
    public EntityTimeAttributeTransfer getEntityTimeAttribute() {
        return entityTimeAttribute;
    }

    /**
     * Sets the entityTimeAttribute.
     * @param entityTimeAttribute the entityTimeAttribute to set
     */
    public void setEntityTimeAttribute(EntityTimeAttributeTransfer entityTimeAttribute) {
        this.entityTimeAttribute = entityTimeAttribute;
    }

    /**
     * Returns the entityListItemDefault.
     * @return the entityListItemDefault
     */
    public EntityListItemDefaultTransfer getEntityListItemDefault() {
        return entityListItemDefault;
    }

    /**
     * Sets the entityListItemDefault.
     * @param entityListItemDefault the entityListItemDefault to set
     */
    public void setEntityListItemDefault(EntityListItemDefaultTransfer entityListItemDefault) {
        this.entityListItemDefault = entityListItemDefault;
    }

    /**
     * Returns the entityListItemAttribute.
     * @return the entityListItemAttribute
     */
    public EntityListItemAttributeTransfer getEntityListItemAttribute() {
        return entityListItemAttribute;
    }

    /**
     * Sets the entityListItemAttribute.
     * @param entityListItemAttribute the entityListItemAttribute to set
     */
    public void setEntityListItemAttribute(EntityListItemAttributeTransfer entityListItemAttribute) {
        this.entityListItemAttribute = entityListItemAttribute;
    }

    /**
     * Returns the entityMultipleListItemDefaults.
     * @return the entityMultipleListItemDefaults
     */
    public ListWrapper<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaults() {
        return entityMultipleListItemDefaults;
    }

    /**
     * Sets the entityMultipleListItemDefaults.
     * @param entityMultipleListItemDefaults the entityMultipleListItemDefaults to set
     */
    public void setEntityMultipleListItemDefaults(ListWrapper<EntityMultipleListItemDefaultTransfer> entityMultipleListItemDefaults) {
        this.entityMultipleListItemDefaults = entityMultipleListItemDefaults;
    }

    /**
     * Returns the entityMultipleListItemAttributes.
     * @return the entityMultipleListItemAttributes
     */
    public ListWrapper<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributes() {
        return entityMultipleListItemAttributes;
    }

    /**
     * Sets the entityMultipleListItemAttributes.
     * @param entityMultipleListItemAttributes the entityMultipleListItemAttributes to set
     */
    public void setEntityMultipleListItemAttributes(ListWrapper<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributes) {
        this.entityMultipleListItemAttributes = entityMultipleListItemAttributes;
    }

    public Long getEntityListItemsCount() {
        return entityListItemsCount;
    }

    public void setEntityListItemsCount(final Long entityListItemsCount) {
        this.entityListItemsCount = entityListItemsCount;
    }

    /**
     * Returns the entityListItems.
     * @return the entityListItems
     */
    public ListWrapper<EntityListItemTransfer> getEntityListItems() {
        return entityListItems;
    }

    /**
     * Sets the entityListItems.
     * @param entityListItems the entityListItems to set
     */
    public void setEntityListItems(ListWrapper<EntityListItemTransfer> entityListItems) {
        this.entityListItems = entityListItems;
    }

    public Long getEntityAttributeEntityTypesCount() {
        return entityAttributeEntityTypesCount;
    }

    public void setEntityAttributeEntityTypesCount(final Long entityAttributeEntityTypesCount) {
        this.entityAttributeEntityTypesCount = entityAttributeEntityTypesCount;
    }

    /**
     * Returns the entityAttributeEntityTypes.
     * @return the entityAttributeEntityTypes
     */
    public ListWrapper<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypes() {
        return entityAttributeEntityTypes;
    }

    /**
     * Sets the entityAttributeEntityTypes.
     * @param entityAttributeEntityTypes the entityAttributeEntityTypes to set
     */
    public void setEntityAttributeEntityTypes(ListWrapper<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypes) {
        this.entityAttributeEntityTypes = entityAttributeEntityTypes;
    }

}
