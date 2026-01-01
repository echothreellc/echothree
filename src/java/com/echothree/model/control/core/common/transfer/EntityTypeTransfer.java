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

import com.echothree.model.control.comment.common.transfer.CommentTypeTransfer;
import com.echothree.model.control.index.common.transfer.IndexTypeTransfer;
import com.echothree.model.control.message.common.transfer.MessageTypeTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Objects;

public final class EntityTypeTransfer
        extends BaseTransfer {
    
    private ComponentVendorTransfer componentVendor;
    private String entityTypeName;
    private Boolean keepAllHistory;
    private Long unformattedLockTimeout;
    private String lockTimeout;
    private Boolean isExtensible;
    private Integer sortOrder;
    private String description;
    
    private Long indexTypesCount;
    private ListWrapper<IndexTypeTransfer> indexTypes;
    private ListWrapper<EntityAliasTypeTransfer> entityAliasTypes;
    private ListWrapper<EntityAttributeTransfer> entityAttributes;
    private ListWrapper<CommentTypeTransfer> commentTypes;
    private ListWrapper<RatingTypeTransfer> ratingTypes;
    private ListWrapper<MessageTypeTransfer> messageTypes;
    private Long entityInstancesCount;
    private ListWrapper<EntityInstanceTransfer> entityInstances;

    /**
     * Creates a new instance of EntityTypeTransfer */
    public EntityTypeTransfer() {
        this(null, null, null, null, null, null, null, null);
    }
    
    /** Creates a new instance of EntityTypeTransfer */
    public EntityTypeTransfer(ComponentVendorTransfer componentVendor, String entityTypeName, Boolean keepAllHistory, Long unformattedLockTimeout,
            String lockTimeout, Boolean isExtensible, Integer sortOrder, String description) {
        this.componentVendor = componentVendor;
        this.entityTypeName = entityTypeName;
        this.keepAllHistory = keepAllHistory;
        this.unformattedLockTimeout = unformattedLockTimeout;
        this.lockTimeout = lockTimeout;
        this.isExtensible = isExtensible;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public ComponentVendorTransfer getComponentVendor() {
        return componentVendor;
    }
    
    public void setComponentVendor(ComponentVendorTransfer componentVendor) {
        this.componentVendor = componentVendor;
    }
    
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    /**
     * Returns the keepAllHistory.
     * @return the keepAllHistory
     */
    public Boolean getKeepAllHistory() {
        return keepAllHistory;
    }

    /**
     * Sets the keepAllHistory.
     * @param keepAllHistory the keepAllHistory to set
     */
    public void setKeepAllHistory(Boolean keepAllHistory) {
        this.keepAllHistory = keepAllHistory;
    }
    
    /**
     * Returns the unformattedLockTimeout.
     * @return the unformattedLockTimeout
     */
    public Long getUnformattedLockTimeout() {
        return unformattedLockTimeout;
    }

    /**
     * Sets the unformattedLockTimeout.
     * @param unformattedLockTimeout the unformattedLockTimeout to set
     */
    public void setUnformattedLockTimeout(Long unformattedLockTimeout) {
        this.unformattedLockTimeout = unformattedLockTimeout;
    }

    /**
     * Returns the lockTimeout.
     * @return the lockTimeout
     */
    public String getLockTimeout() {
        return lockTimeout;
    }

    /**
     * Sets the lockTimeout.
     * @param lockTimeout the lockTimeout to set
     */
    public void setLockTimeout(String lockTimeout) {
        this.lockTimeout = lockTimeout;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsExtensible() {
        return isExtensible;
    }

    public void setIsExtensible(Boolean isExtensible) {
        this.isExtensible = isExtensible;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the indexTypesCount.
     * @return the indexTypesCount
     */
    public Long getIndexTypesCount() {
        return indexTypesCount;
    }

    /**
     * Sets the indexTypesCount.
     * @param indexTypesCount the indexTypesCount to set
     */
    public void setIndexTypesCount(Long indexTypesCount) {
        this.indexTypesCount = indexTypesCount;
    }

    /**
     * Returns the indexTypes.
     * @return the indexTypes
     */
    public ListWrapper<IndexTypeTransfer> getIndexTypes() {
        return indexTypes;
    }

    /**
     * Sets the indexTypes.
     * @param indexTypes the indexTypes to set
     */
    public void setIndexTypes(ListWrapper<IndexTypeTransfer> indexTypes) {
        this.indexTypes = indexTypes;
    }

    public ListWrapper<EntityAliasTypeTransfer> getEntityAliasTypes() {
        return entityAliasTypes;
    }

    public void setEntityAliasTypes(final ListWrapper<EntityAliasTypeTransfer> entityAliasTypes) {
        this.entityAliasTypes = entityAliasTypes;
    }

    public ListWrapper<EntityAttributeTransfer> getEntityAttributes() {
        return entityAttributes;
    }

    public void setEntityAttributes(final ListWrapper<EntityAttributeTransfer> entityAttributes) {
        this.entityAttributes = entityAttributes;
    }

    public ListWrapper<CommentTypeTransfer> getCommentTypes() {
        return commentTypes;
    }

    public void setCommentTypes(final ListWrapper<CommentTypeTransfer> commentTypes) {
        this.commentTypes = commentTypes;
    }

    public ListWrapper<RatingTypeTransfer> getRatingTypes() {
        return ratingTypes;
    }

    public void setRatingTypes(final ListWrapper<RatingTypeTransfer> ratingTypes) {
        this.ratingTypes = ratingTypes;
    }

    public ListWrapper<MessageTypeTransfer> getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(final ListWrapper<MessageTypeTransfer> messageTypes) {
        this.messageTypes = messageTypes;
    }

    public Long getEntityInstancesCount() {
        return entityInstancesCount;
    }

    public void setEntityInstancesCount(final Long entityInstancesCount) {
        this.entityInstancesCount = entityInstancesCount;
    }

    public ListWrapper<EntityInstanceTransfer> getEntityInstances() {
        return entityInstances;
    }

    public void setEntityInstances(final ListWrapper<EntityInstanceTransfer> entityInstances) {
        this.entityInstances = entityInstances;
    }

    /** componentVendor and entityTypeName must be present.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (EntityTypeTransfer) o;
        return componentVendor.equals(that.componentVendor) && entityTypeName.equals(that.entityTypeName);
    }

    /** componentVendor and entityTypeName must be present.
     */
    @Override
    public int hashCode() {
        return Objects.hash(componentVendor, entityTypeName);
    }

}
