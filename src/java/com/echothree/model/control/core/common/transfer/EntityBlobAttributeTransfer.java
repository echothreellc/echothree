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

package com.echothree.model.control.core.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityBlobAttributeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private EntityInstanceTransfer entityInstance;
    private LanguageTransfer language;
    private ByteArray blobAttribute;
    private MimeTypeTransfer mimeType;
    private String eTag;
    
    /** Creates a new instance of EntityBlobAttributeTransfer */
    public EntityBlobAttributeTransfer(EntityAttributeTransfer entityAttribute, EntityInstanceTransfer entityInstance, LanguageTransfer language,
            ByteArray blobAttribute, MimeTypeTransfer mimeType, String eTag) {
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
        this.language = language;
        this.blobAttribute = blobAttribute;
        this.mimeType = mimeType;
        this.eTag = eTag;
    }

    /**
     * Returns the entityAttribute.
     * @return the entityAttribute
     */
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }

    /**
     * Sets the entityAttribute.
     * @param entityAttribute the entityAttribute to set
     */
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    /**
     * Returns the entityInstance.
     * @return the entityInstance
     */
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    /**
     * Sets the entityInstance.
     * @param entityInstance the entityInstance to set
     */
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

    /**
     * Returns the language.
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * Returns the blobAttribute.
     * @return the blobAttribute
     */
    public ByteArray getBlobAttribute() {
        return blobAttribute;
    }

    /**
     * Sets the blobAttribute.
     * @param blobAttribute the blobAttribute to set
     */
    public void setBlobAttribute(ByteArray blobAttribute) {
        this.blobAttribute = blobAttribute;
    }

    /**
     * Returns the mimeType.
     * @return the mimeType
     */
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mimeType.
     * @param mimeType the mimeType to set
     */
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Returns the eTag.
     * @return the eTag
     */
    public String geteTag() {
        return eTag;
    }

    /**
     * Sets the eTag.
     * @param eTag the eTag to set
     */
    public void seteTag(String eTag) {
        this.eTag = eTag;
    }
    
}
