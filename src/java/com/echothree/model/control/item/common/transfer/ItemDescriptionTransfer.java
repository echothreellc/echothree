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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemDescriptionTransfer
        extends BaseTransfer {
    
    private ItemDescriptionTypeTransfer itemDescriptionType;
    private ItemTransfer item;
    private LanguageTransfer language;
    private MimeTypeTransfer mimeType;
    private ByteArray blobDescription;
    private String clobDescription;
    private String stringDescription;
    private ItemImageTypeTransfer itemImageType;
    private Integer height;
    private Integer width;
    private Boolean scaledFromParent;
    private String eTag;
    
    /** Creates a new instance of ItemDescriptionTransfer */
    public ItemDescriptionTransfer(ItemDescriptionTypeTransfer itemDescriptionType, ItemTransfer item, LanguageTransfer language, MimeTypeTransfer mimeType,
            ByteArray blobDescription, String clobDescription, String stringDescription, ItemImageTypeTransfer itemImageType, Integer height, Integer width,
            Boolean scaledFromParent, String eTag) {
        this.itemDescriptionType = itemDescriptionType;
        this.item = item;
        this.language = language;
        this.mimeType = mimeType;
        this.blobDescription = blobDescription;
        this.clobDescription = clobDescription;
        this.stringDescription = stringDescription;
        this.itemImageType = itemImageType;
        this.height = height;
        this.width = width;
        this.scaledFromParent = scaledFromParent;
        this.eTag = eTag;
    }
    
    public ItemDescriptionTypeTransfer getItemDescriptionType() {
        return itemDescriptionType;
    }
    
    public void setItemDescriptionType(ItemDescriptionTypeTransfer itemDescriptionType) {
        this.itemDescriptionType = itemDescriptionType;
    }
    
    public ItemTransfer getItem() {
        return item;
    }
    
    public void setItem(ItemTransfer item) {
        this.item = item;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }

    public ByteArray getBlobDescription() {
        return blobDescription;
    }

    public void setBlobDescription(ByteArray blobDescription) {
        this.blobDescription = blobDescription;
    }

    public String getClobDescription() {
        return clobDescription;
    }

    public void setClobDescription(String clobDescription) {
        this.clobDescription = clobDescription;
    }

    public String getStringDescription() {
        return stringDescription;
    }

    public void setStringDescription(String stringDescription) {
        this.stringDescription = stringDescription;
    }

    /**
     * Returns the itemImageType.
     * @return the itemImageType
     */
    public ItemImageTypeTransfer getItemImageType() {
        return itemImageType;
    }

    /**
     * Sets the itemImageType.
     * @param itemImageType the itemImageType to set
     */
    public void setItemImageType(ItemImageTypeTransfer itemImageType) {
        this.itemImageType = itemImageType;
    }

    /**
     * Returns the height.
     * @return the height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Sets the height.
     * @param height the height to set
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Returns the width.
     * @return the width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the width.
     * @param width the width to set
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Returns the scaledFromParent.
     * @return the scaledFromParent
     */
    public Boolean getScaledFromParent() {
        return scaledFromParent;
    }

    /**
     * Sets the scaledFromParent.
     * @param scaledFromParent the scaledFromParent to set
     */
    public void setScaledFromParent(Boolean scaledFromParent) {
        this.scaledFromParent = scaledFromParent;
    }

    /**
     * Returns the eTag.
     * @return the eTag
     */
    public String getETag() {
        return eTag;
    }

    /**
     * Sets the eTag.
     * @param eTag the eTag to set
     */
    public void setETag(String eTag) {
        this.eTag = eTag;
    }

}
