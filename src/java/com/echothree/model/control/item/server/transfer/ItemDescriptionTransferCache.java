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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemImageTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;

public class ItemDescriptionTransferCache
        extends BaseItemTransferCache<ItemDescription, ItemDescriptionTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    EventControl eventControl = Session.getModelController(EventControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    boolean includeBlob;
    boolean includeClob;
    boolean includeString;
    boolean includeImageDescription;
    boolean includeETag;
    
    TransferProperties transferProperties;
    boolean filterItemDescriptionType;
    boolean filterItem;
    boolean filterLanguage;
    boolean filterMimeType;
    boolean filterItemImageType;
    boolean filterHeight;
    boolean filterWidth;
    boolean filterScaledFromParent;
    boolean filterEntityInstance;

    /** Creates a new instance of ItemDescriptionTransferCache */
    public ItemDescriptionTransferCache(ItemControl itemControl) {
        super(itemControl);

        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(ItemOptions.ItemDescriptionIncludeBlob);
            includeClob = options.contains(ItemOptions.ItemDescriptionIncludeClob);
            includeString = options.contains(ItemOptions.ItemDescriptionIncludeString);
            includeImageDescription = options.contains(ItemOptions.ItemDescriptionIncludeImageDescription);
            includeETag = options.contains(ItemOptions.ItemDescriptionIncludeETag);
            setIncludeEntityAttributeGroups(options.contains(ItemOptions.ItemDescriptionIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ItemOptions.ItemDescriptionIncludeTagScopes));
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ItemDescriptionTransfer.class);
            
            if(properties != null) {
                filterItemDescriptionType = !properties.contains(ItemProperties.ITEM_DESCRIPTION_TYPE);
                filterItem = !properties.contains(ItemProperties.ITEM);
                filterLanguage = !properties.contains(ItemProperties.LANGUAGE);
                filterMimeType = !properties.contains(ItemProperties.MIME_TYPE);
                filterItemImageType = !properties.contains(ItemProperties.ITEM_IMAGE_TYPE);
                filterHeight = !properties.contains(ItemProperties.HEIGHT);
                filterWidth = !properties.contains(ItemProperties.WIDTH);
                filterScaledFromParent = !properties.contains(ItemProperties.SCALED_FROM_PARENT);
                filterEntityInstance = !properties.contains(ItemProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    @Override
    public ItemDescriptionTransfer getTransfer(ItemDescription itemDescription) {
        var itemDescriptionTransfer = get(itemDescription);
        
        if(itemDescriptionTransfer == null) {
            var itemDescriptionDetail = itemDescription.getLastDetail();
            var itemDescriptionTypeTransfer = filterItemDescriptionType ? null : itemControl.getItemDescriptionTypeTransfer(userVisit, itemDescriptionDetail.getItemDescriptionType());
            var item = itemDescriptionDetail.getItem();
            var itemTransfer = filterItem ? null : itemControl.getItemTransfer(userVisit, item);
            var languageTransfer = filterLanguage ? null : partyControl.getLanguageTransfer(userVisit, itemDescriptionDetail.getLanguage());
            var mimeType = filterMimeType ? null : itemDescriptionDetail.getMimeType();
            var mimeTypeTransfer = mimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, mimeType);
            ByteArray blobDescription = null;
            String clobDescription = null;
            String stringDescription = null;
            ItemImageTypeTransfer itemImageTypeTransfer = null;
            Integer height = null;
            Integer width = null;
            Boolean scaledFromParent = null;
            String eTag = null;
            long eTagEntityId = 0;
            var eTagSize = 0;

            if(includeBlob || includeETag) {
                var itemBlobDescription = itemControl.getItemBlobDescription(itemDescription);

                if(itemBlobDescription != null) {
                    if(includeBlob) {
                        blobDescription = itemBlobDescription.getBlobDescription();
                    }

                    eTagEntityId = itemBlobDescription.getPrimaryKey().getEntityId();
                    eTagSize = itemBlobDescription.getBlobDescription().byteArrayValue().length;
                }
            }

            if(includeClob || includeETag) {
                var itemClobDescription = itemControl.getItemClobDescription(itemDescription);

                if(itemClobDescription != null) {
                    if(includeClob) {
                        clobDescription = itemClobDescription.getClobDescription();
                    }

                    eTagEntityId = itemClobDescription.getPrimaryKey().getEntityId();
                    eTagSize = itemClobDescription.getClobDescription().length();
                }
            }

            if(includeString || includeETag) {
                var itemStringDescription = itemControl.getItemStringDescription(itemDescription);

                if(itemStringDescription != null) {
                    if(includeString) {
                        stringDescription = itemStringDescription.getStringDescription();
                    }

                    eTagEntityId = itemStringDescription.getPrimaryKey().getEntityId();
                    eTagSize = itemStringDescription.getStringDescription().length();
                }
            }

            if(includeImageDescription) {
                var itemImageDescription = itemControl.getItemImageDescription(itemDescription);

                if(itemImageDescription != null) {
                    itemImageTypeTransfer = filterItemImageType ? null : itemControl.getItemImageTypeTransfer(userVisit, itemImageDescription.getItemImageType());
                    height = filterHeight ? null : itemImageDescription.getHeight();
                    width = filterWidth ? null : itemImageDescription.getWidth();
                    scaledFromParent = filterScaledFromParent ? null : itemImageDescription.getScaledFromParent();
                }
            }

            if(includeETag && eTagEntityId != 0) {
                // Item Descriptions do not have their own EntityTime, fall back on the Item's EntityTime.
                var entityTimeTransfer = item == null ? null : itemTransfer.getEntityInstance().getEntityTime();
                Long maxTime;

                if(entityTimeTransfer == null) {
                    var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(item.getPrimaryKey());
                    var entityTime = eventControl.getEntityTime(entityInstance);
                    var modifiedTime = entityTime.getModifiedTime();
                    
                    maxTime = modifiedTime == null ? entityTime.getCreatedTime() : modifiedTime;
                } else {
                    var modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                    
                    maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;
                }
                
                // EntityId-Size-ModifiedTime
                eTag = Long.toHexString(eTagEntityId) + '-' + Integer.toHexString(eTagSize) + '-' + Long.toHexString(maxTime);
            }
            
            itemDescriptionTransfer = new ItemDescriptionTransfer(itemDescriptionTypeTransfer, itemTransfer, languageTransfer, mimeTypeTransfer,
                    blobDescription, clobDescription, stringDescription, itemImageTypeTransfer, height, width, scaledFromParent, eTag);
            put(userVisit, itemDescription, itemDescriptionTransfer);
        }
        
        return itemDescriptionTransfer;
    }
    
}
