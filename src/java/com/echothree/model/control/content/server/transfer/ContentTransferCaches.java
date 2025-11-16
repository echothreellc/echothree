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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ContentTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    ContentCatalogTransferCache contentCatalogTransferCache;
    
    @Inject
    ContentCatalogDescriptionTransferCache contentCatalogDescriptionTransferCache;
    
    @Inject
    ContentCategoryTransferCache contentCategoryTransferCache;
    
    @Inject
    ContentCategoryDescriptionTransferCache contentCategoryDescriptionTransferCache;
    
    @Inject
    ContentCollectionTransferCache contentCollectionTransferCache;
    
    @Inject
    ContentCollectionDescriptionTransferCache contentCollectionDescriptionTransferCache;
    
    @Inject
    ContentPageAreaTransferCache contentPageAreaTransferCache;
    
    @Inject
    ContentPageAreaTypeTransferCache contentPageAreaTypeTransferCache;
    
    @Inject
    ContentPageDescriptionTransferCache contentPageDescriptionTransferCache;
    
    @Inject
    ContentPageLayoutAreaTransferCache contentPageLayoutAreaTransferCache;
    
    @Inject
    ContentPageLayoutTransferCache contentPageLayoutTransferCache;
    
    @Inject
    ContentPageLayoutDescriptionTransferCache contentPageLayoutDescriptionTransferCache;
    
    @Inject
    ContentPageTransferCache contentPageTransferCache;
    
    @Inject
    ContentSectionTransferCache contentSectionTransferCache;
    
    @Inject
    ContentSectionDescriptionTransferCache contentSectionDescriptionTransferCache;
    
    @Inject
    ContentWebAddressTransferCache contentWebAddressTransferCache;
    
    @Inject
    ContentWebAddressDescriptionTransferCache contentWebAddressDescriptionTransferCache;
    
    @Inject
    ContentForumTransferCache contentForumTransferCache;
    
    @Inject
    ContentCategoryItemTransferCache contentCategoryItemTransferCache;
    
    @Inject
    ContentCatalogItemTransferCache contentCatalogItemTransferCache;

    /** Creates a new instance of ContentTransferCaches */
    protected ContentTransferCaches() {
        super();
    }
    
    public ContentCatalogTransferCache getContentCatalogTransferCache() {
        return contentCatalogTransferCache;
    }
    
    public ContentCatalogDescriptionTransferCache getContentCatalogDescriptionTransferCache() {
        return contentCatalogDescriptionTransferCache;
    }
    
    public ContentCategoryTransferCache getContentCategoryTransferCache() {
        return contentCategoryTransferCache;
    }
    
    public ContentCategoryDescriptionTransferCache getContentCategoryDescriptionTransferCache() {
        return contentCategoryDescriptionTransferCache;
    }
    
    public ContentCollectionTransferCache getContentCollectionTransferCache() {
        return contentCollectionTransferCache;
    }
    
    public ContentCollectionDescriptionTransferCache getContentCollectionDescriptionTransferCache() {
        return contentCollectionDescriptionTransferCache;
    }
    
    public ContentPageLayoutTransferCache getContentPageLayoutTransferCache() {
        return contentPageLayoutTransferCache;
    }
    
    public ContentPageLayoutDescriptionTransferCache getContentPageLayoutDescriptionTransferCache() {
        return contentPageLayoutDescriptionTransferCache;
    }
    
    public ContentPageTransferCache getContentPageTransferCache() {
        return contentPageTransferCache;
    }
    
    public ContentPageDescriptionTransferCache getContentPageDescriptionTransferCache() {
        return contentPageDescriptionTransferCache;
    }
    
    public ContentSectionTransferCache getContentSectionTransferCache() {
        return contentSectionTransferCache;
    }
    
    public ContentSectionDescriptionTransferCache getContentSectionDescriptionTransferCache() {
        return contentSectionDescriptionTransferCache;
    }
    
    public ContentWebAddressTransferCache getContentWebAddressTransferCache() {
        return contentWebAddressTransferCache;
    }
    
    public ContentWebAddressDescriptionTransferCache getContentWebAddressDescriptionTransferCache() {
        return contentWebAddressDescriptionTransferCache;
    }
    
    public ContentPageAreaTransferCache getContentPageAreaTransferCache() {
        return contentPageAreaTransferCache;
    }
    
    public ContentPageAreaTypeTransferCache getContentPageAreaTypeTransferCache() {
        return contentPageAreaTypeTransferCache;
    }
    
    public ContentPageLayoutAreaTransferCache getContentPageLayoutAreaTransferCache() {
        return contentPageLayoutAreaTransferCache;
    }
    
    public ContentForumTransferCache getContentForumTransferCache() {
        return contentForumTransferCache;
    }
    
    public ContentCategoryItemTransferCache getContentCategoryItemTransferCache() {
        return contentCategoryItemTransferCache;
    }
    
    public ContentCatalogItemTransferCache getContentCatalogItemTransferCache() {
        return contentCatalogItemTransferCache;
    }
    
}
