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

import com.echothree.model.control.content.common.transfer.ContentCategoryTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContentCategoryResultTransfer
        extends BaseTransfer {
    
    private String contentCollectionName;
    private String contentCatalogName;
    private String contentCategoryName;
    private ContentCategoryTransfer contentCategory;
    
    /** Creates a new instance of ItemResultTransfer */
    public ContentCategoryResultTransfer(String contentCollectionName, String contentCatalogName, String contentCategoryName,
            ContentCategoryTransfer contentCategory) {
        this.contentCollectionName = contentCollectionName;
        this.contentCatalogName = contentCatalogName;
        this.contentCategoryName = contentCategoryName;
        this.contentCategory = contentCategory;
    }

    /**
     * Returns the contentCollectionName.
     * @return the contentCollectionName
     */
    public String getContentCollectionName() {
        return contentCollectionName;
    }

    /**
     * Sets the contentCollectionName.
     * @param contentCollectionName the contentCollectionName to set
     */
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }

    /**
     * Returns the contentCatalogName.
     * @return the contentCatalogName
     */
    public String getContentCatalogName() {
        return contentCatalogName;
    }

    /**
     * Sets the contentCatalogName.
     * @param contentCatalogName the contentCatalogName to set
     */
    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    /**
     * Returns the contentCategoryName.
     * @return the contentCategoryName
     */
    public String getContentCategoryName() {
        return contentCategoryName;
    }

    /**
     * Sets the contentCategoryName.
     * @param contentCategoryName the contentCategoryName to set
     */
    public void setContentCategoryName(String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
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

}
