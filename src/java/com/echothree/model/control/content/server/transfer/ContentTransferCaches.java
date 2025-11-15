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

import com.echothree.util.server.transfer.BaseTransferCaches;

public class ContentTransferCaches
        extends BaseTransferCaches {
    
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
    public ContentTransferCaches() {
        super();
    }
    
    public ContentCatalogTransferCache getContentCatalogTransferCache() {
        if(contentCatalogTransferCache == null)
            contentCatalogTransferCache = new ContentCatalogTransferCache();
        
        return contentCatalogTransferCache;
    }
    
    public ContentCatalogDescriptionTransferCache getContentCatalogDescriptionTransferCache() {
        if(contentCatalogDescriptionTransferCache == null)
            contentCatalogDescriptionTransferCache = new ContentCatalogDescriptionTransferCache();
        
        return contentCatalogDescriptionTransferCache;
    }
    
    public ContentCategoryTransferCache getContentCategoryTransferCache() {
        if(contentCategoryTransferCache == null)
            contentCategoryTransferCache = new ContentCategoryTransferCache();
        
        return contentCategoryTransferCache;
    }
    
    public ContentCategoryDescriptionTransferCache getContentCategoryDescriptionTransferCache() {
        if(contentCategoryDescriptionTransferCache == null)
            contentCategoryDescriptionTransferCache = new ContentCategoryDescriptionTransferCache();
        
        return contentCategoryDescriptionTransferCache;
    }
    
    public ContentCollectionTransferCache getContentCollectionTransferCache() {
        if(contentCollectionTransferCache == null)
            contentCollectionTransferCache = new ContentCollectionTransferCache();
        
        return contentCollectionTransferCache;
    }
    
    public ContentCollectionDescriptionTransferCache getContentCollectionDescriptionTransferCache() {
        if(contentCollectionDescriptionTransferCache == null)
            contentCollectionDescriptionTransferCache = new ContentCollectionDescriptionTransferCache();
        
        return contentCollectionDescriptionTransferCache;
    }
    
    public ContentPageLayoutTransferCache getContentPageLayoutTransferCache() {
        if(contentPageLayoutTransferCache == null)
            contentPageLayoutTransferCache = new ContentPageLayoutTransferCache();
        
        return contentPageLayoutTransferCache;
    }
    
    public ContentPageLayoutDescriptionTransferCache getContentPageLayoutDescriptionTransferCache() {
        if(contentPageLayoutDescriptionTransferCache == null)
            contentPageLayoutDescriptionTransferCache = new ContentPageLayoutDescriptionTransferCache();
        
        return contentPageLayoutDescriptionTransferCache;
    }
    
    public ContentPageTransferCache getContentPageTransferCache() {
        if(contentPageTransferCache == null)
            contentPageTransferCache = new ContentPageTransferCache();
        
        return contentPageTransferCache;
    }
    
    public ContentPageDescriptionTransferCache getContentPageDescriptionTransferCache() {
        if(contentPageDescriptionTransferCache == null)
            contentPageDescriptionTransferCache = new ContentPageDescriptionTransferCache();
        
        return contentPageDescriptionTransferCache;
    }
    
    public ContentSectionTransferCache getContentSectionTransferCache() {
        if(contentSectionTransferCache == null)
            contentSectionTransferCache = new ContentSectionTransferCache();
        
        return contentSectionTransferCache;
    }
    
    public ContentSectionDescriptionTransferCache getContentSectionDescriptionTransferCache() {
        if(contentSectionDescriptionTransferCache == null)
            contentSectionDescriptionTransferCache = new ContentSectionDescriptionTransferCache();
        
        return contentSectionDescriptionTransferCache;
    }
    
    public ContentWebAddressTransferCache getContentWebAddressTransferCache() {
        if(contentWebAddressTransferCache == null)
            contentWebAddressTransferCache = new ContentWebAddressTransferCache();
        
        return contentWebAddressTransferCache;
    }
    
    public ContentWebAddressDescriptionTransferCache getContentWebAddressDescriptionTransferCache() {
        if(contentWebAddressDescriptionTransferCache == null)
            contentWebAddressDescriptionTransferCache = new ContentWebAddressDescriptionTransferCache();
        
        return contentWebAddressDescriptionTransferCache;
    }
    
    public ContentPageAreaTransferCache getContentPageAreaTransferCache() {
        if(contentPageAreaTransferCache == null)
            contentPageAreaTransferCache = new ContentPageAreaTransferCache();
        
        return contentPageAreaTransferCache;
    }
    
    public ContentPageAreaTypeTransferCache getContentPageAreaTypeTransferCache() {
        if(contentPageAreaTypeTransferCache == null)
            contentPageAreaTypeTransferCache = new ContentPageAreaTypeTransferCache();
        
        return contentPageAreaTypeTransferCache;
    }
    
    public ContentPageLayoutAreaTransferCache getContentPageLayoutAreaTransferCache() {
        if(contentPageLayoutAreaTransferCache == null)
            contentPageLayoutAreaTransferCache = new ContentPageLayoutAreaTransferCache();
        
        return contentPageLayoutAreaTransferCache;
    }
    
    public ContentForumTransferCache getContentForumTransferCache() {
        if(contentForumTransferCache == null)
            contentForumTransferCache = new ContentForumTransferCache();
        
        return contentForumTransferCache;
    }
    
    public ContentCategoryItemTransferCache getContentCategoryItemTransferCache() {
        if(contentCategoryItemTransferCache == null)
            contentCategoryItemTransferCache = new ContentCategoryItemTransferCache();
        
        return contentCategoryItemTransferCache;
    }
    
    public ContentCatalogItemTransferCache getContentCatalogItemTransferCache() {
        if(contentCatalogItemTransferCache == null)
            contentCatalogItemTransferCache = new ContentCatalogItemTransferCache();
        
        return contentCatalogItemTransferCache;
    }
    
}
