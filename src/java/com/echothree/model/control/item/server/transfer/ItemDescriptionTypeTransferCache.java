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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDetail;
import com.echothree.model.data.item.server.entity.ItemImageDescriptionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ItemDescriptionTypeTransferCache
        extends BaseItemTransferCache<ItemDescriptionType, ItemDescriptionTypeTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    
    TransferProperties transferProperties;
    boolean filterItemDescriptionTypeName;
    boolean filterParentItemDescriptionType;
    boolean filterUseParentIfMissing;
    boolean filterMimeTypeUsageType;
    boolean filterCheckContentWebAddress;
    boolean filterIncludeInIndex;
    boolean filterIndexDefault;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterMinimumHeight;
    boolean filterMinimumWidth;
    boolean filterMaximumHeight;
    boolean filterMaximumWidth;
    boolean filterPreferredHeight;
    boolean filterPreferredWidth;
    boolean filterPreferredMimeType;
    boolean filterQuality;
    boolean filterScaleFromParent;
    boolean filterEntityInstance;

    /** Creates a new instance of ItemDescriptionTypeTransferCache */
    public ItemDescriptionTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ItemDescriptionTypeTransfer.class);
            
            if(properties != null) {
                filterItemDescriptionTypeName = !properties.contains(ItemProperties.ITEM_DESCRIPTION_TYPE_NAME);
                filterParentItemDescriptionType = !properties.contains(ItemProperties.PARENT_ITEM_DESCRIPTION_TYPE);
                filterUseParentIfMissing = !properties.contains(ItemProperties.USE_PARENT_IF_MISSING);
                filterMimeTypeUsageType = !properties.contains(ItemProperties.MIME_TYPE_USAGE_TYPE);
                filterCheckContentWebAddress = !properties.contains(ItemProperties.CHECK_CONTENT_WEB_ADDRESS);
                filterIncludeInIndex = !properties.contains(ItemProperties.INCLUDE_IN_INDEX);
                filterIndexDefault = !properties.contains(ItemProperties.INDEX_DEFAULT);
                filterIsDefault = !properties.contains(ItemProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ItemProperties.SORT_ORDER);
                filterDescription = !properties.contains(ItemProperties.DESCRIPTION);
                filterMinimumHeight = !properties.contains(ItemProperties.MINIMUM_HEIGHT);
                filterMinimumWidth = !properties.contains(ItemProperties.MINIMUM_WIDTH);
                filterMaximumHeight = !properties.contains(ItemProperties.MAXIMUM_HEIGHT);
                filterMaximumWidth = !properties.contains(ItemProperties.MAXIMUM_WIDTH);
                filterPreferredHeight = !properties.contains(ItemProperties.PREFERRED_HEIGHT);
                filterPreferredWidth = !properties.contains(ItemProperties.PREFERRED_WIDTH);
                filterPreferredMimeType = !properties.contains(ItemProperties.PREFERRED_MIME_TYPE);
                filterQuality = !properties.contains(ItemProperties.QUALITY);
                filterScaleFromParent = !properties.contains(ItemProperties.ITEMSCALE_FROM_PARENT_NAME);
                filterEntityInstance = !properties.contains(ItemProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    @Override
    public ItemDescriptionTypeTransfer getTransfer(ItemDescriptionType itemDescriptionType) {
        ItemDescriptionTypeTransfer itemDescriptionTypeTransfer = get(itemDescriptionType);
        
        if(itemDescriptionTypeTransfer == null) {
            ItemDescriptionTypeDetail itemDescriptionTypeDetail = itemDescriptionType.getLastDetail();
            String itemDescriptionTypeName = filterItemDescriptionTypeName ? null : itemDescriptionTypeDetail.getItemDescriptionTypeName();
            ItemDescriptionType parentItemDescriptionType = filterParentItemDescriptionType ? null : itemDescriptionTypeDetail.getParentItemDescriptionType();
            ItemDescriptionTypeTransfer parentItemDescriptionTypeTransfer = parentItemDescriptionType == null ? null : itemControl.getItemDescriptionTypeTransfer(userVisit, parentItemDescriptionType);
            Boolean useParentIfMissing = filterUseParentIfMissing ? null : itemDescriptionTypeDetail.getUseParentIfMissing();
            MimeTypeUsageType mimeTypeUsageType = filterMimeTypeUsageType ? null : itemDescriptionTypeDetail.getMimeTypeUsageType();
            MimeTypeUsageTypeTransfer mimeTypeUsageTypeTransfer = mimeTypeUsageType == null ? null : coreControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
            Boolean checkContentWebAddress = filterCheckContentWebAddress ? null : itemDescriptionTypeDetail.getCheckContentWebAddress();
            Boolean includeInIndex = filterIncludeInIndex ? null : itemDescriptionTypeDetail.getIncludeInIndex();
            Boolean indexDefault = filterIndexDefault ? null : itemDescriptionTypeDetail.getIndexDefault();
            Boolean isDefault = filterIsDefault ? null : itemDescriptionTypeDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : itemDescriptionTypeDetail.getSortOrder();
            String description = filterDescription ? null : itemControl.getBestItemDescriptionTypeDescription(itemDescriptionType, getLanguage());
            Integer minimumHeight = null;
            Integer minimumWidth = null;
            Integer maximumHeight = null;
            Integer maximumWidth = null;
            Integer preferredHeight = null;
            Integer preferredWidth = null;
            MimeTypeTransfer preferredMimeTypeTransfer = null;
            Integer quality = null;
            Boolean scaleFromParent = null;

            if(mimeTypeUsageType != null && mimeTypeUsageType.getMimeTypeUsageTypeName().equals(MimeTypeUsageTypes.IMAGE.name())) {
                ItemImageDescriptionType itemImageDescriptionType = itemControl.getItemImageDescriptionType(itemDescriptionType);
                MimeType preferredMimeType = filterPreferredMimeType ? null : itemImageDescriptionType.getPreferredMimeType();

                minimumHeight = filterMinimumHeight ? null : itemImageDescriptionType.getMinimumHeight();
                minimumWidth = filterMinimumWidth ? null : itemImageDescriptionType.getMinimumWidth();
                maximumHeight = filterMaximumHeight ? null : itemImageDescriptionType.getMaximumHeight();
                maximumWidth = filterMaximumWidth ? null : itemImageDescriptionType.getMaximumWidth();
                preferredHeight = filterPreferredHeight ? null : itemImageDescriptionType.getPreferredHeight();
                preferredWidth = filterPreferredWidth ? null : itemImageDescriptionType.getPreferredWidth();
                preferredMimeTypeTransfer = preferredMimeType == null ? null : coreControl.getMimeTypeTransfer(userVisit, preferredMimeType);
                quality = filterQuality ? null : itemImageDescriptionType.getQuality();
                scaleFromParent = filterScaleFromParent ? null : itemImageDescriptionType.getScaleFromParent();
            }

            itemDescriptionTypeTransfer = new ItemDescriptionTypeTransfer(itemDescriptionTypeName, parentItemDescriptionTypeTransfer, useParentIfMissing,
                    mimeTypeUsageTypeTransfer, checkContentWebAddress, includeInIndex, indexDefault, isDefault, sortOrder, description, minimumHeight,
                    minimumWidth, maximumHeight, maximumWidth, preferredHeight, preferredWidth, preferredMimeTypeTransfer, quality, scaleFromParent);
            put(itemDescriptionType, itemDescriptionTypeTransfer);
        }
        
        return itemDescriptionTypeTransfer;
    }
    
}
