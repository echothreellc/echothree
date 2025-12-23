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
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityStringAttributeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private EntityInstanceTransfer entityInstance;
    private LanguageTransfer language;
    private String stringAttribute;
    
    /** Creates a new instance of EntityStringAttributeTransfer */
    public EntityStringAttributeTransfer(EntityAttributeTransfer entityAttribute, EntityInstanceTransfer entityInstance,
            LanguageTransfer language, String stringAttribute) {
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
        this.language = language;
        this.stringAttribute = stringAttribute;
    }
    
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }
    
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }
    
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }
    
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public String getStringAttribute() {
        return stringAttribute;
    }
    
    public void setStringAttribute(String stringAttribute) {
        this.stringAttribute = stringAttribute;
    }
    
}
