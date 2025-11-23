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

import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentCategoryItemTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentCategoryItemTransferCache
        extends BaseContentTransferCache<ContentCategoryItem, ContentCategoryItemTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);

    TransferProperties transferProperties;
    boolean filterContentCategory;
    boolean filterContentCatalogItem;
    boolean filterIsDefault;
    boolean filterSortOrder;

    /** Creates a new instance of ContentCategoryItemTransferCache */
    protected ContentCategoryItemTransferCache() {
        super();
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ContentCategoryItemTransfer.class);
            
            if(properties != null) {
                filterContentCategory = !properties.contains(ContentProperties.CONTENT_CATEGORY);
                filterContentCatalogItem = !properties.contains(ContentProperties.CONTENT_CATALOG_ITEM);
                filterIsDefault = !properties.contains(ContentProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ContentProperties.SORT_ORDER);
            }
        }
    }
    
    public ContentCategoryItemTransfer getContentCategoryItemTransfer(UserVisit userVisit, ContentCategoryItem contentCategoryItem) {
        var contentCategoryItemTransfer = get(contentCategoryItem);
        
        if(contentCategoryItemTransfer == null) {
            var contentCategory = filterContentCategory ? null : contentControl.getContentCategoryTransfer(userVisit, contentCategoryItem.getContentCategory());
            var contentCatalogItem = filterContentCatalogItem ? null : contentControl.getContentCatalogItemTransfer(userVisit, contentCategoryItem.getContentCatalogItem());
            var isDefault = filterIsDefault ? null : contentCategoryItem.getIsDefault();
            var sortOrder = filterSortOrder ? null : contentCategoryItem.getSortOrder();
            
            contentCategoryItemTransfer = new ContentCategoryItemTransfer(contentCategory, contentCatalogItem, isDefault, sortOrder);
            put(userVisit, contentCategoryItem, contentCategoryItemTransfer);
        }
        
        return contentCategoryItemTransfer;
    }

}
