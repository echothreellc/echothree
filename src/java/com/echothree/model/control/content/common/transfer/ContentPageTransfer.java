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
import com.echothree.util.common.transfer.MapWrapper;

public class ContentPageTransfer
        extends BaseTransfer {
    
    private ContentSectionTransfer contentSection;
    private String contentPageName;
    private ContentPageLayoutTransfer contentPageLayout;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private MapWrapper<ContentPageAreaTransfer> contentPageAreas;
    
    /** Creates a new instance of ContentPageTransfer */
    public ContentPageTransfer(ContentSectionTransfer contentSection, String contentPageName, ContentPageLayoutTransfer contentPageLayout, Boolean isDefault,
            Integer sortOrder, String description) {
        this.contentSection = contentSection;
        this.contentPageName = contentPageName;
        this.contentPageLayout = contentPageLayout;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the contentSection.
     * @return the contentSection
     */
    public ContentSectionTransfer getContentSection() {
        return contentSection;
    }

    /**
     * Sets the contentSection.
     * @param contentSection the contentSection to set
     */
    public void setContentSection(ContentSectionTransfer contentSection) {
        this.contentSection = contentSection;
    }

    /**
     * Returns the contentPageName.
     * @return the contentPageName
     */
    public String getContentPageName() {
        return contentPageName;
    }

    /**
     * Sets the contentPageName.
     * @param contentPageName the contentPageName to set
     */
    public void setContentPageName(String contentPageName) {
        this.contentPageName = contentPageName;
    }

    /**
     * Returns the contentPageLayout.
     * @return the contentPageLayout
     */
    public ContentPageLayoutTransfer getContentPageLayout() {
        return contentPageLayout;
    }

    /**
     * Sets the contentPageLayout.
     * @param contentPageLayout the contentPageLayout to set
     */
    public void setContentPageLayout(ContentPageLayoutTransfer contentPageLayout) {
        this.contentPageLayout = contentPageLayout;
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
     * Returns the contentPageAreas.
     * @return the contentPageAreas
     */
    public MapWrapper<ContentPageAreaTransfer> getContentPageAreas() {
        return contentPageAreas;
    }

    /**
     * Sets the contentPageAreas.
     * @param contentPageAreas the contentPageAreas to set
     */
    public void setContentPageAreas(MapWrapper<ContentPageAreaTransfer> contentPageAreas) {
        this.contentPageAreas = contentPageAreas;
    }

}
