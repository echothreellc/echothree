// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ContentTransferCaches
        extends BaseTransferCaches {
    
    protected ContentControl contentControl;
    
    protected ContentCatalogTransferCache contentCatalogTransferCache;
    protected ContentCatalogDescriptionTransferCache contentCatalogDescriptionTransferCache;
    protected ContentCategoryTransferCache contentCategoryTransferCache;
    protected ContentCategoryDescriptionTransferCache contentCategoryDescriptionTransferCache;
    protected ContentCollectionTransferCache contentCollectionTransferCache;
    protected ContentCollectionDescriptionTransferCache contentCollectionDescriptionTransferCache;
    protected ContentPageAreaTransferCache contentPageAreaTransferCache;
    protected ContentPageAreaTypeTransferCache contentPageAreaTypeTransferCache;
    protected ContentPageDescriptionTransferCache contentPageDescriptionTransferCache;
    protected ContentPageLayoutAreaTransferCache contentPageLayoutAreaTransferCache;
    protected ContentPageLayoutTransferCache contentPageLayoutTransferCache;
    protected ContentPageLayoutDescriptionTransferCache contentPageLayoutDescriptionTransferCache;
    protected ContentPageTransferCache contentPageTransferCache;
    protected ContentSectionTransferCache contentSectionTransferCache;
    protected ContentSectionDescriptionTransferCache contentSectionDescriptionTransferCache;
    protected ContentWebAddressTransferCache contentWebAddressTransferCache;
    protected ContentWebAddressDescriptionTransferCache contentWebAddressDescriptionTransferCache;
    protected ContentForumTransferCache contentForumTransferCache;
    protected ContentCategoryItemTransferCache contentCategoryItemTransferCache;
    protected ContentCatalogItemTransferCache contentCatalogItemTransferCache;
    
    /** Creates a new instance of ContentTransferCaches */
    public ContentTransferCaches(ContentControl contentControl) {
        super();
        
        this.contentControl = contentControl;
    }
    
    public ContentCatalogTransferCache getContentCatalogTransferCache() {
        if(contentCatalogTransferCache == null)
            contentCatalogTransferCache = new ContentCatalogTransferCache(contentControl);
        
        return contentCatalogTransferCache;
    }
    
    public ContentCatalogDescriptionTransferCache getContentCatalogDescriptionTransferCache() {
        if(contentCatalogDescriptionTransferCache == null)
            contentCatalogDescriptionTransferCache = new ContentCatalogDescriptionTransferCache(contentControl);
        
        return contentCatalogDescriptionTransferCache;
    }
    
    public ContentCategoryTransferCache getContentCategoryTransferCache() {
        if(contentCategoryTransferCache == null)
            contentCategoryTransferCache = new ContentCategoryTransferCache(contentControl);
        
        return contentCategoryTransferCache;
    }
    
    public ContentCategoryDescriptionTransferCache getContentCategoryDescriptionTransferCache() {
        if(contentCategoryDescriptionTransferCache == null)
            contentCategoryDescriptionTransferCache = new ContentCategoryDescriptionTransferCache(contentControl);
        
        return contentCategoryDescriptionTransferCache;
    }
    
    public ContentCollectionTransferCache getContentCollectionTransferCache() {
        if(contentCollectionTransferCache == null)
            contentCollectionTransferCache = new ContentCollectionTransferCache(contentControl);
        
        return contentCollectionTransferCache;
    }
    
    public ContentCollectionDescriptionTransferCache getContentCollectionDescriptionTransferCache() {
        if(contentCollectionDescriptionTransferCache == null)
            contentCollectionDescriptionTransferCache = new ContentCollectionDescriptionTransferCache(contentControl);
        
        return contentCollectionDescriptionTransferCache;
    }
    
    public ContentPageLayoutTransferCache getContentPageLayoutTransferCache() {
        if(contentPageLayoutTransferCache == null)
            contentPageLayoutTransferCache = new ContentPageLayoutTransferCache(contentControl);
        
        return contentPageLayoutTransferCache;
    }
    
    public ContentPageLayoutDescriptionTransferCache getContentPageLayoutDescriptionTransferCache() {
        if(contentPageLayoutDescriptionTransferCache == null)
            contentPageLayoutDescriptionTransferCache = new ContentPageLayoutDescriptionTransferCache(contentControl);
        
        return contentPageLayoutDescriptionTransferCache;
    }
    
    public ContentPageTransferCache getContentPageTransferCache() {
        if(contentPageTransferCache == null)
            contentPageTransferCache = new ContentPageTransferCache(contentControl);
        
        return contentPageTransferCache;
    }
    
    public ContentPageDescriptionTransferCache getContentPageDescriptionTransferCache() {
        if(contentPageDescriptionTransferCache == null)
            contentPageDescriptionTransferCache = new ContentPageDescriptionTransferCache(contentControl);
        
        return contentPageDescriptionTransferCache;
    }
    
    public ContentSectionTransferCache getContentSectionTransferCache() {
        if(contentSectionTransferCache == null)
            contentSectionTransferCache = new ContentSectionTransferCache(contentControl);
        
        return contentSectionTransferCache;
    }
    
    public ContentSectionDescriptionTransferCache getContentSectionDescriptionTransferCache() {
        if(contentSectionDescriptionTransferCache == null)
            contentSectionDescriptionTransferCache = new ContentSectionDescriptionTransferCache(contentControl);
        
        return contentSectionDescriptionTransferCache;
    }
    
    public ContentWebAddressTransferCache getContentWebAddressTransferCache() {
        if(contentWebAddressTransferCache == null)
            contentWebAddressTransferCache = new ContentWebAddressTransferCache(contentControl);
        
        return contentWebAddressTransferCache;
    }
    
    public ContentWebAddressDescriptionTransferCache getContentWebAddressDescriptionTransferCache() {
        if(contentWebAddressDescriptionTransferCache == null)
            contentWebAddressDescriptionTransferCache = new ContentWebAddressDescriptionTransferCache(contentControl);
        
        return contentWebAddressDescriptionTransferCache;
    }
    
    public ContentPageAreaTransferCache getContentPageAreaTransferCache() {
        if(contentPageAreaTransferCache == null)
            contentPageAreaTransferCache = new ContentPageAreaTransferCache(contentControl);
        
        return contentPageAreaTransferCache;
    }
    
    public ContentPageAreaTypeTransferCache getContentPageAreaTypeTransferCache() {
        if(contentPageAreaTypeTransferCache == null)
            contentPageAreaTypeTransferCache = new ContentPageAreaTypeTransferCache(contentControl);
        
        return contentPageAreaTypeTransferCache;
    }
    
    public ContentPageLayoutAreaTransferCache getContentPageLayoutAreaTransferCache() {
        if(contentPageLayoutAreaTransferCache == null)
            contentPageLayoutAreaTransferCache = new ContentPageLayoutAreaTransferCache(contentControl);
        
        return contentPageLayoutAreaTransferCache;
    }
    
    public ContentForumTransferCache getContentForumTransferCache() {
        if(contentForumTransferCache == null)
            contentForumTransferCache = new ContentForumTransferCache(contentControl);
        
        return contentForumTransferCache;
    }
    
    public ContentCategoryItemTransferCache getContentCategoryItemTransferCache() {
        if(contentCategoryItemTransferCache == null)
            contentCategoryItemTransferCache = new ContentCategoryItemTransferCache(contentControl);
        
        return contentCategoryItemTransferCache;
    }
    
    public ContentCatalogItemTransferCache getContentCatalogItemTransferCache() {
        if(contentCatalogItemTransferCache == null)
            contentCatalogItemTransferCache = new ContentCatalogItemTransferCache(contentControl);
        
        return contentCatalogItemTransferCache;
    }
    
}
