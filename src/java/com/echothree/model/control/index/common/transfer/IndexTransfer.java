// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.index.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class IndexTransfer
        extends BaseTransfer {

    private String indexName;
    private IndexTypeTransfer indexType;
    private LanguageTransfer language;
    private String directory;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private Long unformattedCreatedTime;
    private String createdTime;

    /**
     * Creates a new instance of IndexTransfer
     */
    public IndexTransfer(String indexName, IndexTypeTransfer indexType, LanguageTransfer language, String directory, Boolean isDefault, Integer sortOrder,
            String description, Long unformattedCreatedTime, String createdTime) {
        this.indexName = indexName;
        this.indexType = indexType;
        this.language = language;
        this.directory = directory;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
        this.unformattedCreatedTime = unformattedCreatedTime;
        this.createdTime = createdTime;
    }

    /**
     * @return the indexName
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * @return the indexType
     */
    public IndexTypeTransfer getIndexType() {
        return indexType;
    }

    /**
     * @param indexType the indexType to set
     */
    public void setIndexType(IndexTypeTransfer indexType) {
        this.indexType = indexType;
    }

    /**
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * @param directory the directory to set
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
     * @return the unformattedCreatedTime
     */
    public Long getUnformattedCreatedTime() {
        return unformattedCreatedTime;
    }

    /**
     * @param unformattedCreatedTime the unformattedCreatedTime to set
     */
    public void setUnformattedCreatedTime(Long unformattedCreatedTime) {
        this.unformattedCreatedTime = unformattedCreatedTime;
    }

    /**
     * @return the createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

}
