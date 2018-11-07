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

import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemImageTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemBlobDescription;
import com.echothree.model.data.item.server.entity.ItemClobDescription;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionDetail;
import com.echothree.model.data.item.server.entity.ItemImageDescription;
import com.echothree.model.data.item.server.entity.ItemStringDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ItemDescriptionTransferCache
        extends BaseItemTransferCache<ItemDescription, ItemDescriptionTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    
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
    public ItemDescriptionTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);

        Set<String> options = session.getOptions();
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
            Set<String> properties = transferProperties.getProperties(ItemDescriptionTransfer.class);
            
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
        ItemDescriptionTransfer itemDescriptionTransfer = get(itemDescription);
        
        if(itemDescriptionTransfer == null) {
            ItemDescriptionDetail itemDescriptionDetail = itemDescription.getLastDetail();
            ItemDescriptionTypeTransfer itemDescriptionTypeTransfer = filterItemDescriptionType ? null : itemControl.getItemDescriptionTypeTransfer(userVisit, itemDescriptionDetail.getItemDescriptionType());
            Item item = itemDescriptionDetail.getItem();
            ItemTransfer itemTransfer = filterItem ? null : itemControl.getItemTransfer(userVisit, item);
            LanguageTransfer languageTransfer = filterLanguage ? null : partyControl.getLanguageTransfer(userVisit, itemDescriptionDetail.getLanguage());
            MimeType mimeType = filterMimeType ? null : itemDescriptionDetail.getMimeType();
            MimeTypeTransfer mimeTypeTransfer = mimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, mimeType);
            ByteArray blobDescription = null;
            String clobDescription = null;
            String stringDescription = null;
            ItemImageTypeTransfer itemImageTypeTransfer = null;
            Integer height = null;
            Integer width = null;
            Boolean scaledFromParent = null;
            String eTag = null;
            long eTagEntityId = 0;
            int eTagSize = 0;
            
            if(includeBlob || includeETag) {
                ItemBlobDescription itemBlobDescription = itemControl.getItemBlobDescription(itemDescription);
                
                if(itemBlobDescription != null) {
                    if(includeBlob) {
                        blobDescription = itemBlobDescription.getBlobDescription();
                    }
                    
                    eTagEntityId = itemBlobDescription.getPrimaryKey().getEntityId();
                    eTagSize = itemBlobDescription.getBlobDescription().byteArrayValue().length;
                }
            }
            
            if(includeClob || includeETag) {
                ItemClobDescription itemClobDescription = itemControl.getItemClobDescription(itemDescription);
                
                if(itemClobDescription != null) {
                    if(includeClob) {
                        clobDescription = itemClobDescription.getClobDescription();
                    }

                    eTagEntityId = itemClobDescription.getPrimaryKey().getEntityId();
                    eTagSize = itemClobDescription.getClobDescription().length();
                }
            }
            
            if(includeString || includeETag) {
                ItemStringDescription itemStringDescription = itemControl.getItemStringDescription(itemDescription);
                
                if(itemStringDescription != null) {
                    if(includeString) {
                        stringDescription = itemStringDescription.getStringDescription();
                    }

                    eTagEntityId = itemStringDescription.getPrimaryKey().getEntityId();
                    eTagSize = itemStringDescription.getStringDescription().length();
                }
            }

            if(includeImageDescription) {
                ItemImageDescription itemImageDescription = itemControl.getItemImageDescription(itemDescription);

                if(itemImageDescription != null) {
                    itemImageTypeTransfer = filterItemImageType ? null : itemControl.getItemImageTypeTransfer(userVisit, itemImageDescription.getItemImageType());
                    height = filterHeight ? null : itemImageDescription.getHeight();
                    width = filterWidth ? null : itemImageDescription.getWidth();
                    scaledFromParent = filterScaledFromParent ? null : itemImageDescription.getScaledFromParent();
                }
            }

            if(includeETag && eTagEntityId != 0) {
                // Item Descriptions do not have their own EntityTime, fall back on the Item's EntityTime.
                EntityTimeTransfer entityTimeTransfer = item == null ? null : itemTransfer.getEntityInstance().getEntityTime();
                Long maxTime;
                
                if(entityTimeTransfer == null) {
                    EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(item.getPrimaryKey());
                    EntityTime entityTime = coreControl.getEntityTime(entityInstance);
                    Long modifiedTime = entityTime.getModifiedTime();
                    
                    maxTime = modifiedTime == null ? entityTime.getCreatedTime() : modifiedTime;
                } else {
                    Long modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                    
                    maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;
                }
                
                // EntityId-Size-ModifiedTime
                eTag = new StringBuilder(Long.toHexString(eTagEntityId)).append('-').append(Integer.toHexString(eTagSize)).append('-').append(Long.toHexString(maxTime)).toString();
            }
            
            itemDescriptionTransfer = new ItemDescriptionTransfer(itemDescriptionTypeTransfer, itemTransfer, languageTransfer, mimeTypeTransfer,
                    blobDescription, clobDescription, stringDescription, itemImageTypeTransfer, height, width, scaledFromParent, eTag);
            put(itemDescription, itemDescriptionTransfer);
        }
        
        return itemDescriptionTransfer;
    }
    
}
