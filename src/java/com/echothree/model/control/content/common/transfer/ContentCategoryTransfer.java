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

import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class ContentCategoryTransfer
        extends BaseTransfer {

    private ContentCatalogTransfer contentCatalog;
    private String contentCategoryName;
    private ContentCategoryTransfer parentContentCategory;
    private OfferUseTransfer defaultOfferUse;
    private SelectorTransfer contentCategoryItemSelector;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    private ListWrapper<ContentCategoryItemTransfer> contentCategoryItems;
    
    /** Creates a new instance of ContentCategoryTransfer */
    public ContentCategoryTransfer(ContentCatalogTransfer contentCatalog, String contentCategoryName, ContentCategoryTransfer parentContentCategory,
            OfferUseTransfer defaultOfferUse, SelectorTransfer contentCategoryItemSelector, Boolean isDefault, Integer sortOrder, String description) {
        this.contentCatalog = contentCatalog;
        this.contentCategoryName = contentCategoryName;
        this.parentContentCategory = parentContentCategory;
        this.defaultOfferUse = defaultOfferUse;
        this.contentCategoryItemSelector = contentCategoryItemSelector;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the contentCatalog.
     * @return the contentCatalog
     */
    public ContentCatalogTransfer getContentCatalog() {
        return contentCatalog;
    }

    /**
     * Sets the contentCatalog.
     * @param contentCatalog the contentCatalog to set
     */
    public void setContentCatalog(ContentCatalogTransfer contentCatalog) {
        this.contentCatalog = contentCatalog;
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
     * Returns the parentContentCategory.
     * @return the parentContentCategory
     */
    public ContentCategoryTransfer getParentContentCategory() {
        return parentContentCategory;
    }

    /**
     * Sets the parentContentCategory.
     * @param parentContentCategory the parentContentCategory to set
     */
    public void setParentContentCategory(ContentCategoryTransfer parentContentCategory) {
        this.parentContentCategory = parentContentCategory;
    }

    /**
     * Returns the defaultOfferUse.
     * @return the defaultOfferUse
     */
    public OfferUseTransfer getDefaultOfferUse() {
        return defaultOfferUse;
    }

    /**
     * Sets the defaultOfferUse.
     * @param defaultOfferUse the defaultOfferUse to set
     */
    public void setDefaultOfferUse(OfferUseTransfer defaultOfferUse) {
        this.defaultOfferUse = defaultOfferUse;
    }

    /**
     * Returns the contentCategoryItemSelector.
     * @return the contentCategoryItemSelector
     */
    public SelectorTransfer getContentCategoryItemSelector() {
        return contentCategoryItemSelector;
    }

    /**
     * Sets the contentCategoryItemSelector.
     * @param contentCategoryItemSelector the contentCategoryItemSelector to set
     */
    public void setContentCategoryItemSelector(SelectorTransfer contentCategoryItemSelector) {
        this.contentCategoryItemSelector = contentCategoryItemSelector;
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
     * Returns the contentCategoryItems.
     * @return the contentCategoryItems
     */
    public ListWrapper<ContentCategoryItemTransfer> getContentCategoryItems() {
        return contentCategoryItems;
    }

    /**
     * Sets the contentCategoryItems.
     * @param contentCategoryItems the contentCategoryItems to set
     */
    public void setContentCategoryItems(ListWrapper<ContentCategoryItemTransfer> contentCategoryItems) {
        this.contentCategoryItems = contentCategoryItems;
    }

}
