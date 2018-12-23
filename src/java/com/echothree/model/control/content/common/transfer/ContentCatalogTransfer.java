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

package com.echothree.model.control.content.common.transfer;

import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class ContentCatalogTransfer
        extends BaseTransfer {

    private ContentCollectionTransfer contentCollection;
    private String contentCatalogName;
    private OfferUseTransfer defaultOfferUse;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    private ListWrapper<ContentCatalogItemTransfer> contentCatalogItems;
    private ListWrapper<ContentCategoryTransfer> contentCategories;
    
    /** Creates a new instance of ContentCatalogTransfer */
    public ContentCatalogTransfer(ContentCollectionTransfer contentCollection, String contentCatalogName, OfferUseTransfer defaultOfferUse, Boolean isDefault,
            Integer sortOrder, String description) {
        this.contentCollection = contentCollection;
        this.contentCatalogName = contentCatalogName;
        this.defaultOfferUse = defaultOfferUse;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the contentCollection
     */
    public ContentCollectionTransfer getContentCollection() {
        return contentCollection;
    }

    /**
     * @param contentCollection the contentCollection to set
     */
    public void setContentCollection(ContentCollectionTransfer contentCollection) {
        this.contentCollection = contentCollection;
    }

    /**
     * @return the contentCatalogName
     */
    public String getContentCatalogName() {
        return contentCatalogName;
    }

    /**
     * @param contentCatalogName the contentCatalogName to set
     */
    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    /**
     * @return the defaultOfferUse
     */
    public OfferUseTransfer getDefaultOfferUse() {
        return defaultOfferUse;
    }

    /**
     * @param defaultOfferUse the defaultOfferUse to set
     */
    public void setDefaultOfferUse(OfferUseTransfer defaultOfferUse) {
        this.defaultOfferUse = defaultOfferUse;
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
     * @return the contentCatalogItems
     */
    public ListWrapper<ContentCatalogItemTransfer> getContentCatalogItems() {
        return contentCatalogItems;
    }

    /**
     * @param contentCatalogItems the contentCatalogItems to set
     */
    public void setContentCatalogItems(ListWrapper<ContentCatalogItemTransfer> contentCatalogItems) {
        this.contentCatalogItems = contentCatalogItems;
    }

    /**
     * @return the contentCategories
     */
    public ListWrapper<ContentCategoryTransfer> getContentCategories() {
        return contentCategories;
    }

    /**
     * @param contentCategories the contentCategories to set
     */
    public void setContentCategories(ListWrapper<ContentCategoryTransfer> contentCategories) {
        this.contentCategories = contentCategories;
    }

}
