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

package com.echothree.model.control.queue.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class QueueTypeTransfer
        extends BaseTransfer {
    
    private String queueTypeName;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private Long queuedEntityCount;
    private Long unformattedOldestQueuedEntityTime;
    private String oldestQueuedEntityTime;
    private Long unformattedLatestQueuedEntityTime;
    private String latestQueuedEntityTime;
    private ListWrapper<QueuedEntityTransfer> queuedEntities;
    
    /** Creates a new instance of QueueTypeTransfer */
    public QueueTypeTransfer(String queueTypeName, Boolean isDefault, Integer sortOrder, String description) {
        this.queueTypeName = queueTypeName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the queueTypeName.
     * @return the queueTypeName
     */
    public String getQueueTypeName() {
        return queueTypeName;
    }

    /**
     * Sets the queueTypeName.
     * @param queueTypeName the queueTypeName to set
     */
    public void setQueueTypeName(String queueTypeName) {
        this.queueTypeName = queueTypeName;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
     * Returns the queuedEntityCount.
     * @return the queuedEntityCount
     */
    public Long getQueuedEntityCount() {
        return queuedEntityCount;
    }

    /**
     * Sets the queuedEntityCount.
     * @param queuedEntityCount the queuedEntityCount to set
     */
    public void setQueuedEntityCount(Long queuedEntityCount) {
        this.queuedEntityCount = queuedEntityCount;
    }

    /**
     * Returns the unformattedOldestQueuedEntityTime.
     * @return the unformattedOldestQueuedEntityTime
     */
    public Long getUnformattedOldestQueuedEntityTime() {
        return unformattedOldestQueuedEntityTime;
    }

    /**
     * Sets the unformattedOldestQueuedEntityTime.
     * @param unformattedOldestQueuedEntityTime the unformattedOldestQueuedEntityTime to set
     */
    public void setUnformattedOldestQueuedEntityTime(Long unformattedOldestQueuedEntityTime) {
        this.unformattedOldestQueuedEntityTime = unformattedOldestQueuedEntityTime;
    }

    /**
     * Returns the oldestQueuedEntityTime.
     * @return the oldestQueuedEntityTime
     */
    public String getOldestQueuedEntityTime() {
        return oldestQueuedEntityTime;
    }

    /**
     * Sets the oldestQueuedEntityTime.
     * @param oldestQueuedEntityTime the oldestQueuedEntityTime to set
     */
    public void setOldestQueuedEntityTime(String oldestQueuedEntityTime) {
        this.oldestQueuedEntityTime = oldestQueuedEntityTime;
    }

    /**
     * Returns the unformattedLatestQueuedEntityTime.
     * @return the unformattedLatestQueuedEntityTime
     */
    public Long getUnformattedLatestQueuedEntityTime() {
        return unformattedLatestQueuedEntityTime;
    }

    /**
     * Sets the unformattedLatestQueuedEntityTime.
     * @param unformattedLatestQueuedEntityTime the unformattedLatestQueuedEntityTime to set
     */
    public void setUnformattedLatestQueuedEntityTime(Long unformattedLatestQueuedEntityTime) {
        this.unformattedLatestQueuedEntityTime = unformattedLatestQueuedEntityTime;
    }

    /**
     * Returns the latestQueuedEntityTime.
     * @return the latestQueuedEntityTime
     */
    public String getLatestQueuedEntityTime() {
        return latestQueuedEntityTime;
    }

    /**
     * Sets the latestQueuedEntityTime.
     * @param latestQueuedEntityTime the latestQueuedEntityTime to set
     */
    public void setLatestQueuedEntityTime(String latestQueuedEntityTime) {
        this.latestQueuedEntityTime = latestQueuedEntityTime;
    }

    /**
     * Returns the queuedEntities.
     * @return the queuedEntities
     */
    public ListWrapper<QueuedEntityTransfer> getQueuedEntities() {
        return queuedEntities;
    }

    /**
     * Sets the queuedEntities.
     * @param queuedEntities the queuedEntities to set
     */
    public void setQueuedEntities(ListWrapper<QueuedEntityTransfer> queuedEntities) {
        this.queuedEntities = queuedEntities;
    }

}
