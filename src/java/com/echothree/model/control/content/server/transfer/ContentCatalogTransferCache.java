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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentCatalogTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentCatalogTransferCache
        extends BaseContentTransferCache<ContentCatalog, ContentCatalogTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    
    boolean includeContentCatalogItems;
    boolean includeContentCatalogCategories;

    TransferProperties transferProperties;
    boolean filterContentCollection;
    boolean filterContentCatalogName;
    boolean filterDefaultOfferUse;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of ContentCatalogTransferCache */
    protected ContentCatalogTransferCache() {
        super();
    
        var options = session.getOptions();
        if(options != null) {
            includeContentCatalogItems = options.contains(ContentOptions.ContentCatalogIncludeContentCatalogItems);
            includeContentCatalogCategories = options.contains(ContentOptions.ContentCatalogIncludeContentCatalogCategories);
            setIncludeUuid(options.contains(ContentOptions.ContentCatalogIncludeUuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentCatalogIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentCatalogIncludeTagScopes));
        }
        
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ContentCategoryTransfer.class);
            
            if(properties != null) {
                filterContentCollection = !properties.contains(ContentProperties.CONTENT_COLLECTION);
                filterContentCatalogName = !properties.contains(ContentProperties.CONTENT_CATALOG_NAME);
                filterDefaultOfferUse = !properties.contains(ContentProperties.DEFAULT_OFFER_USE);
                filterIsDefault = !properties.contains(ContentProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ContentProperties.SORT_ORDER);
                filterDescription = !properties.contains(ContentProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(ContentProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }

    public ContentCatalogTransfer getContentCatalogTransfer(UserVisit userVisit, ContentCatalog contentCatalog) {
        var contentCatalogTransfer = get(contentCatalog);
        
        if(contentCatalogTransfer == null) {
            var contentCatalogDetail = contentCatalog.getLastDetail();
            var contentCollectionTransfer = filterContentCollection ? null : contentControl.getContentCollectionTransfer(userVisit, contentCatalogDetail.getContentCollection());
            var contentCatalogName = filterContentCatalogName ? null : contentCatalogDetail.getContentCatalogName();
            var defaultOfferUse = filterDefaultOfferUse ? null : contentCatalogDetail.getDefaultOfferUse();
            var defaultOfferUseTransfer = defaultOfferUse == null ? null : offerUseControl.getOfferUseTransfer(userVisit, defaultOfferUse);
            var isDefault = filterIsDefault ? null : contentCatalogDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : contentCatalogDetail.getSortOrder();
            var description = filterDescription ? null : contentControl.getBestContentCatalogDescription(contentCatalog, getLanguage(userVisit));
            
            contentCatalogTransfer = new ContentCatalogTransfer(contentCollectionTransfer, contentCatalogName, defaultOfferUseTransfer, isDefault, sortOrder,
                    description);
            put(userVisit, contentCatalog, contentCatalogTransfer);
            
            if(includeContentCatalogItems) {
                contentCatalogTransfer.setContentCatalogItems(new ListWrapper<>(contentControl.getContentCatalogItemTransfers(userVisit, contentCatalog)));
            }

            if(includeContentCatalogCategories) {
                contentCatalogTransfer.setContentCategories(new ListWrapper<>(contentControl.getContentCategoryTransfers(userVisit, contentCatalog)));
            }
        }
        
        return contentCatalogTransfer;
    }
    
}
