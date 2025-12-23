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

package com.echothree.model.control.offer.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UseTypeDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private UseTypeTransfer useType;
    private String description;
    
    /** Creates a new instance of UseTypeDescriptionTransfer */
    public UseTypeDescriptionTransfer(LanguageTransfer language, UseTypeTransfer useType, String description) {
        this.language = language;
        this.useType = useType;
        this.description = description;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public UseTypeTransfer getUseType() {
        return useType;
    }
    
    public void setUseType(UseTypeTransfer useType) {
        this.useType = useType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
