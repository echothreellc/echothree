// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentCatalogItemTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryItemTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryTransfer;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import java.util.Set;

public class ContentCategoryItemTransferCache
        extends BaseContentTransferCache<ContentCategoryItem, ContentCategoryItemTransfer> {
    
    TransferProperties transferProperties;
    boolean filterContentCategory;
    boolean filterContentCatalogItem;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterEntityInstance;
    
    /** Creates a new instance of ContentCategoryItemTransferCache */
    public ContentCategoryItemTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ContentCategoryItemTransfer.class);
            
            if(properties != null) {
                filterContentCategory = !properties.contains(ContentProperties.CONTENT_CATEGORY);
                filterContentCatalogItem = !properties.contains(ContentProperties.CONTENT_CATALOG_ITEM);
                filterIsDefault = !properties.contains(ContentProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ContentProperties.SORT_ORDER);
                filterEntityInstance = !properties.contains(ContentProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public ContentCategoryItemTransfer getContentCategoryItemTransfer(ContentCategoryItem contentCategoryItem) {
        ContentCategoryItemTransfer contentCategoryItemTransfer = get(contentCategoryItem);
        
        if(contentCategoryItemTransfer == null) {
            ContentCategoryTransfer contentCategory = filterContentCategory ? null : contentControl.getContentCategoryTransfer(userVisit, contentCategoryItem.getContentCategory());
            ContentCatalogItemTransfer contentCatalogItem = filterContentCatalogItem ? null : contentControl.getContentCatalogItemTransfer(userVisit, contentCategoryItem.getContentCatalogItem());
            Boolean isDefault = filterIsDefault ? null : contentCategoryItem.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : contentCategoryItem.getSortOrder();
            
            contentCategoryItemTransfer = new ContentCategoryItemTransfer(contentCategory, contentCatalogItem, isDefault, sortOrder);
            put(contentCategoryItem, contentCategoryItemTransfer);
        }
        
        return contentCategoryItemTransfer;
    }

}
