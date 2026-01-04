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

package com.echothree.model.control.content.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ContentCategoryItemTransfer
        extends BaseTransfer {
    
    private ContentCategoryTransfer contentCategory;
    private ContentCatalogItemTransfer contentCatalogItem;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of ContentCategoryItemTransfer */
    public ContentCategoryItemTransfer(ContentCategoryTransfer contentCategory, ContentCatalogItemTransfer contentCatalogItem, Boolean isDefault, Integer sortOrder) {
        this.contentCategory = contentCategory;
        this.contentCatalogItem = contentCatalogItem;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the contentCategory.
     * @return the contentCategory
     */
    public ContentCategoryTransfer getContentCategory() {
        return contentCategory;
    }

    /**
     * Sets the contentCategory.
     * @param contentCategory the contentCategory to set
     */
    public void setContentCategory(ContentCategoryTransfer contentCategory) {
        this.contentCategory = contentCategory;
    }

    /**
     * Returns the contentCatalogItem.
     * @return the contentCatalogItem
     */
    public ContentCatalogItemTransfer getContentCatalogItem() {
        return contentCatalogItem;
    }

    /**
     * Sets the contentCatalogItem.
     * @param contentCatalogItem the contentCatalogItem to set
     */
    public void setContentCatalogItem(ContentCatalogItemTransfer contentCatalogItem) {
        this.contentCatalogItem = contentCatalogItem;
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
    
}
