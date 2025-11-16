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
import javax.enterprise.inject.spi.CDI;

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
            contentCatalogTransferCache = CDI.current().select(ContentCatalogTransferCache.class).get();
        
        return contentCatalogTransferCache;
    }
    
    public ContentCatalogDescriptionTransferCache getContentCatalogDescriptionTransferCache() {
        if(contentCatalogDescriptionTransferCache == null)
            contentCatalogDescriptionTransferCache = CDI.current().select(ContentCatalogDescriptionTransferCache.class).get();
        
        return contentCatalogDescriptionTransferCache;
    }
    
    public ContentCategoryTransferCache getContentCategoryTransferCache() {
        if(contentCategoryTransferCache == null)
            contentCategoryTransferCache = CDI.current().select(ContentCategoryTransferCache.class).get();
        
        return contentCategoryTransferCache;
    }
    
    public ContentCategoryDescriptionTransferCache getContentCategoryDescriptionTransferCache() {
        if(contentCategoryDescriptionTransferCache == null)
            contentCategoryDescriptionTransferCache = CDI.current().select(ContentCategoryDescriptionTransferCache.class).get();
        
        return contentCategoryDescriptionTransferCache;
    }
    
    public ContentCollectionTransferCache getContentCollectionTransferCache() {
        if(contentCollectionTransferCache == null)
            contentCollectionTransferCache = CDI.current().select(ContentCollectionTransferCache.class).get();
        
        return contentCollectionTransferCache;
    }
    
    public ContentCollectionDescriptionTransferCache getContentCollectionDescriptionTransferCache() {
        if(contentCollectionDescriptionTransferCache == null)
            contentCollectionDescriptionTransferCache = CDI.current().select(ContentCollectionDescriptionTransferCache.class).get();
        
        return contentCollectionDescriptionTransferCache;
    }
    
    public ContentPageLayoutTransferCache getContentPageLayoutTransferCache() {
        if(contentPageLayoutTransferCache == null)
            contentPageLayoutTransferCache = CDI.current().select(ContentPageLayoutTransferCache.class).get();
        
        return contentPageLayoutTransferCache;
    }
    
    public ContentPageLayoutDescriptionTransferCache getContentPageLayoutDescriptionTransferCache() {
        if(contentPageLayoutDescriptionTransferCache == null)
            contentPageLayoutDescriptionTransferCache = CDI.current().select(ContentPageLayoutDescriptionTransferCache.class).get();
        
        return contentPageLayoutDescriptionTransferCache;
    }
    
    public ContentPageTransferCache getContentPageTransferCache() {
        if(contentPageTransferCache == null)
            contentPageTransferCache = CDI.current().select(ContentPageTransferCache.class).get();
        
        return contentPageTransferCache;
    }
    
    public ContentPageDescriptionTransferCache getContentPageDescriptionTransferCache() {
        if(contentPageDescriptionTransferCache == null)
            contentPageDescriptionTransferCache = CDI.current().select(ContentPageDescriptionTransferCache.class).get();
        
        return contentPageDescriptionTransferCache;
    }
    
    public ContentSectionTransferCache getContentSectionTransferCache() {
        if(contentSectionTransferCache == null)
            contentSectionTransferCache = CDI.current().select(ContentSectionTransferCache.class).get();
        
        return contentSectionTransferCache;
    }
    
    public ContentSectionDescriptionTransferCache getContentSectionDescriptionTransferCache() {
        if(contentSectionDescriptionTransferCache == null)
            contentSectionDescriptionTransferCache = CDI.current().select(ContentSectionDescriptionTransferCache.class).get();
        
        return contentSectionDescriptionTransferCache;
    }
    
    public ContentWebAddressTransferCache getContentWebAddressTransferCache() {
        if(contentWebAddressTransferCache == null)
            contentWebAddressTransferCache = CDI.current().select(ContentWebAddressTransferCache.class).get();
        
        return contentWebAddressTransferCache;
    }
    
    public ContentWebAddressDescriptionTransferCache getContentWebAddressDescriptionTransferCache() {
        if(contentWebAddressDescriptionTransferCache == null)
            contentWebAddressDescriptionTransferCache = CDI.current().select(ContentWebAddressDescriptionTransferCache.class).get();
        
        return contentWebAddressDescriptionTransferCache;
    }
    
    public ContentPageAreaTransferCache getContentPageAreaTransferCache() {
        if(contentPageAreaTransferCache == null)
            contentPageAreaTransferCache = CDI.current().select(ContentPageAreaTransferCache.class).get();
        
        return contentPageAreaTransferCache;
    }
    
    public ContentPageAreaTypeTransferCache getContentPageAreaTypeTransferCache() {
        if(contentPageAreaTypeTransferCache == null)
            contentPageAreaTypeTransferCache = CDI.current().select(ContentPageAreaTypeTransferCache.class).get();
        
        return contentPageAreaTypeTransferCache;
    }
    
    public ContentPageLayoutAreaTransferCache getContentPageLayoutAreaTransferCache() {
        if(contentPageLayoutAreaTransferCache == null)
            contentPageLayoutAreaTransferCache = CDI.current().select(ContentPageLayoutAreaTransferCache.class).get();
        
        return contentPageLayoutAreaTransferCache;
    }
    
    public ContentForumTransferCache getContentForumTransferCache() {
        if(contentForumTransferCache == null)
            contentForumTransferCache = CDI.current().select(ContentForumTransferCache.class).get();
        
        return contentForumTransferCache;
    }
    
    public ContentCategoryItemTransferCache getContentCategoryItemTransferCache() {
        if(contentCategoryItemTransferCache == null)
            contentCategoryItemTransferCache = CDI.current().select(ContentCategoryItemTransferCache.class).get();
        
        return contentCategoryItemTransferCache;
    }
    
    public ContentCatalogItemTransferCache getContentCatalogItemTransferCache() {
        if(contentCatalogItemTransferCache == null)
            contentCatalogItemTransferCache = CDI.current().select(ContentCatalogItemTransferCache.class).get();
        
        return contentCatalogItemTransferCache;
    }
    
}
