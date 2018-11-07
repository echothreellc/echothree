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

package com.echothree.model.control.core.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityClobAttributeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private EntityInstanceTransfer entityInstance;
    private LanguageTransfer language;
    private String clobAttribute;
    private MimeTypeTransfer mimeType;
    private String eTag;
    
    /** Creates a new instance of EntityClobAttributeTransfer */
    public EntityClobAttributeTransfer(EntityAttributeTransfer entityAttribute, EntityInstanceTransfer entityInstance, LanguageTransfer language,
            String clobAttribute, MimeTypeTransfer mimeType, String eTag) {
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
        this.language = language;
        this.clobAttribute = clobAttribute;
        this.mimeType = mimeType;
        this.eTag = eTag;
    }

    /**
     * @return the entityAttribute
     */
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }

    /**
     * @param entityAttribute the entityAttribute to set
     */
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    /**
     * @return the entityInstance
     */
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    /**
     * @param entityInstance the entityInstance to set
     */
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

    /**
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * @return the clobAttribute
     */
    public String getClobAttribute() {
        return clobAttribute;
    }

    /**
     * @param clobAttribute the clobAttribute to set
     */
    public void setClobAttribute(String clobAttribute) {
        this.clobAttribute = clobAttribute;
    }

    /**
     * @return the mimeType
     */
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the eTag
     */
    public String geteTag() {
        return eTag;
    }

    /**
     * @param eTag the eTag to set
     */
    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

}
