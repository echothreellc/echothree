// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
     * @return the contentSection
     */
    public ContentSectionTransfer getContentSection() {
        return contentSection;
    }

    /**
     * @param contentSection the contentSection to set
     */
    public void setContentSection(ContentSectionTransfer contentSection) {
        this.contentSection = contentSection;
    }

    /**
     * @return the contentPageName
     */
    public String getContentPageName() {
        return contentPageName;
    }

    /**
     * @param contentPageName the contentPageName to set
     */
    public void setContentPageName(String contentPageName) {
        this.contentPageName = contentPageName;
    }

    /**
     * @return the contentPageLayout
     */
    public ContentPageLayoutTransfer getContentPageLayout() {
        return contentPageLayout;
    }

    /**
     * @param contentPageLayout the contentPageLayout to set
     */
    public void setContentPageLayout(ContentPageLayoutTransfer contentPageLayout) {
        this.contentPageLayout = contentPageLayout;
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
     * @return the contentPageAreas
     */
    public MapWrapper<ContentPageAreaTransfer> getContentPageAreas() {
        return contentPageAreas;
    }

    /**
     * @param contentPageAreas the contentPageAreas to set
     */
    public void setContentPageAreas(MapWrapper<ContentPageAreaTransfer> contentPageAreas) {
        this.contentPageAreas = contentPageAreas;
    }

}
