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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentCatalogTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.transfer.ListWrapperBuilder;
import java.util.Set;

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
    public ContentCategoryTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeContentCategoryItems = options.contains(ContentOptions.ContentCategoryIncludeContentCategoryItems);
            setIncludeKey(options.contains(ContentOptions.ContentCategoryIncludeKey));
            setIncludeGuid(options.contains(ContentOptions.ContentCategoryIncludeGuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentCategoryIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentCategoryIncludeTagScopes));
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ContentCategoryTransfer.class);
            
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
    
    public ContentCategoryTransfer getContentCategoryTransfer(ContentCategory contentCategory) {
        ContentCategoryTransfer contentCategoryTransfer = get(contentCategory);
        
        if(contentCategoryTransfer == null) {
            ContentCategoryDetail contentCategoryDetail = contentCategory.getLastDetail();
            ContentCatalogTransfer contentCatalogTransfer = filterContentCatalog ? null : contentControl.getContentCatalogTransfer(userVisit, contentCategoryDetail.getContentCatalog());
            String contentCategoryName = filterContentCategoryName ? null : contentCategoryDetail.getContentCategoryName();
            ContentCategory parentContentCategory = filterParentContentCategory ? null : contentCategoryDetail.getParentContentCategory();
            ContentCategoryTransfer parentContentCategoryTransfer = parentContentCategory == null ? null : contentControl.getContentCategoryTransfer(userVisit, parentContentCategory);
            OfferUse defaultOfferUse = filterDefaultOfferUse ? null : contentCategoryDetail.getDefaultOfferUse();
            OfferUseTransfer defaultOfferUseTransfer = defaultOfferUse == null ? null : offerUseControl.getOfferUseTransfer(userVisit, defaultOfferUse);
            Selector contentCategoryItemSelector = filterContentCategoryItemSelector ? null : contentCategoryDetail.getContentCategoryItemSelector();
            SelectorTransfer contentCategoryItemSelectorTransfer = contentCategoryItemSelector == null ? null : selectorControl.getSelectorTransfer(userVisit, contentCategoryItemSelector);
            Boolean isDefault = filterIsDefault ? null : contentCategoryDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : contentCategoryDetail.getSortOrder();
            String description = filterDescription ? null : contentControl.getBestContentCategoryDescription(contentCategory, getLanguage());
            
            contentCategoryTransfer = new ContentCategoryTransfer(contentCatalogTransfer, contentCategoryName, parentContentCategoryTransfer,
                    defaultOfferUseTransfer, contentCategoryItemSelectorTransfer, isDefault, sortOrder, description);
            put(contentCategory, contentCategoryTransfer);

            if(includeContentCategoryItems) {
                contentCategoryTransfer.setContentCategoryItems(ListWrapperBuilder.getInstance().filter(transferProperties, contentControl.getContentCategoryItemTransfersByContentCategory(userVisit, contentCategoryDetail.getContentCategory())));
            }
        }
        
        return contentCategoryTransfer;
    }
    
}
