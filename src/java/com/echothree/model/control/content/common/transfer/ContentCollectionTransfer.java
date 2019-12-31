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

import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class ContentCollectionTransfer
        extends BaseTransfer {
    
    private String contentCollectionName;
    private OfferUseTransfer defaultOfferUse;
    private String description;
    
    private ListWrapper<ContentCatalogTransfer> contentCatalogs;
    private ListWrapper<ContentForumTransfer> contentForums;
    private ListWrapper<ContentSectionTransfer> contentSections;
    
    /** Creates a new instance of ContentCollectionTransfer */
    public ContentCollectionTransfer(String contentCollectionName, OfferUseTransfer defaultOfferUse, String description) {
        this.contentCollectionName = contentCollectionName;
        this.defaultOfferUse = defaultOfferUse;
        this.description = description;
    }

    /**
     * @return the contentCollectionName
     */
    public String getContentCollectionName() {
        return contentCollectionName;
    }

    /**
     * @param contentCollectionName the contentCollectionName to set
     */
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
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
     * @return the contentCatalogs
     */
    public ListWrapper<ContentCatalogTransfer> getContentCatalogs() {
        return contentCatalogs;
    }

    /**
     * @param contentCatalogs the contentCatalogs to set
     */
    public void setContentCatalogs(ListWrapper<ContentCatalogTransfer> contentCatalogs) {
        this.contentCatalogs = contentCatalogs;
    }

    /**
     * @return the contentForums
     */
    public ListWrapper<ContentForumTransfer> getContentForums() {
        return contentForums;
    }

    /**
     * @param contentForums the contentForums to set
     */
    public void setContentForums(ListWrapper<ContentForumTransfer> contentForums) {
        this.contentForums = contentForums;
    }

    /**
     * @return the contentSections
     */
    public ListWrapper<ContentSectionTransfer> getContentSections() {
        return contentSections;
    }

    /**
     * @param contentSections the contentSections to set
     */
    public void setContentSections(ListWrapper<ContentSectionTransfer> contentSections) {
        this.contentSections = contentSections;
    }
    
}
