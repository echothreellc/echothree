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

import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentCategoryTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.transfer.ListWrapperBuilder;

public class ContentCategoryTransferCache
        extends BaseContentTransferCache<ContentCategory, ContentCategoryTransfer> {

    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    
    boolean includeContentCategoryItems;
    
    TransferProperties transferProperties;
    boolean filterContentCatalog;
    boolean filterContentCategoryName;
    boolean filterParentContentCategory;
    boolean filterDefaultOfferUse;
    boolean filterContentCategoryItemSelector;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of ContentCategoryTransferCache */
    public ContentCategoryTransferCache(ContentControl contentControl) {
        super(contentControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeContentCategoryItems = options.contains(ContentOptions.ContentCategoryIncludeContentCategoryItems);
            setIncludeUuid(options.contains(ContentOptions.ContentCategoryIncludeUuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentCategoryIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentCategoryIncludeTagScopes));
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ContentCategoryTransfer.class);
            
            if(properties != null) {
                filterContentCatalog = !properties.contains(ContentProperties.CONTENT_CATALOG);
                filterContentCategoryName = !properties.contains(ContentProperties.CONTENT_CATEGORY_NAME);
                filterParentContentCategory = !properties.contains(ContentProperties.PARENT_CONTENT_CATEGORY);
                filterDefaultOfferUse = !properties.contains(ContentProperties.DEFAULT_OFFER_USE);
                filterContentCategoryItemSelector = !properties.contains(ContentProperties.CONTENT_CATEGORY_ITEM_SELECTOR);
                filterIsDefault = !properties.contains(ContentProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ContentProperties.SORT_ORDER);
                filterDescription = !properties.contains(ContentProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(ContentProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public ContentCategoryTransfer getContentCategoryTransfer(UserVisit userVisit, ContentCategory contentCategory) {
        var contentCategoryTransfer = get(contentCategory);
        
        if(contentCategoryTransfer == null) {
            var contentCategoryDetail = contentCategory.getLastDetail();
            var contentCatalogTransfer = filterContentCatalog ? null : contentControl.getContentCatalogTransfer(userVisit, contentCategoryDetail.getContentCatalog());
            var contentCategoryName = filterContentCategoryName ? null : contentCategoryDetail.getContentCategoryName();
            var parentContentCategory = filterParentContentCategory ? null : contentCategoryDetail.getParentContentCategory();
            var parentContentCategoryTransfer = parentContentCategory == null ? null : contentControl.getContentCategoryTransfer(userVisit, parentContentCategory);
            var defaultOfferUse = filterDefaultOfferUse ? null : contentCategoryDetail.getDefaultOfferUse();
            var defaultOfferUseTransfer = defaultOfferUse == null ? null : offerUseControl.getOfferUseTransfer(userVisit, defaultOfferUse);
            var contentCategoryItemSelector = filterContentCategoryItemSelector ? null : contentCategoryDetail.getContentCategoryItemSelector();
            var contentCategoryItemSelectorTransfer = contentCategoryItemSelector == null ? null : selectorControl.getSelectorTransfer(userVisit, contentCategoryItemSelector);
            var isDefault = filterIsDefault ? null : contentCategoryDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : contentCategoryDetail.getSortOrder();
            var description = filterDescription ? null : contentControl.getBestContentCategoryDescription(contentCategory, getLanguage(userVisit));
            
            contentCategoryTransfer = new ContentCategoryTransfer(contentCatalogTransfer, contentCategoryName, parentContentCategoryTransfer,
                    defaultOfferUseTransfer, contentCategoryItemSelectorTransfer, isDefault, sortOrder, description);
            put(userVisit, contentCategory, contentCategoryTransfer);

            if(includeContentCategoryItems) {
                contentCategoryTransfer.setContentCategoryItems(ListWrapperBuilder.getInstance().filter(transferProperties, contentControl.getContentCategoryItemTransfersByContentCategory(userVisit, contentCategoryDetail.getContentCategory())));
            }
        }
        
        return contentCategoryTransfer;
    }
    
}
