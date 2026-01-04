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

package com.echothree.model.control.returnpolicy.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ReturnTypeDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private ReturnTypeTransfer returnType;
    private String description;
    
    /** Creates a new instance of ReturnTypeDescriptionTransfer */
    public ReturnTypeDescriptionTransfer(LanguageTransfer language, ReturnTypeTransfer returnType, String description) {
        this.language = language;
        this.returnType = returnType;
        this.description = description;
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
     * Returns the returnType.
     * @return the returnType
     */
    public ReturnTypeTransfer getReturnType() {
        return returnType;
    }

    /**
     * Sets the returnType.
     * @param returnType the returnType to set
     */
    public void setReturnType(ReturnTypeTransfer returnType) {
        this.returnType = returnType;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
