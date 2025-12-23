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

package com.echothree.model.control.content.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContentSectionDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private ContentSectionTransfer contentSection;
    private String description;
    
    /** Creates a new instance of ContentSectionDescriptionTransfer */
    public ContentSectionDescriptionTransfer(LanguageTransfer language, ContentSectionTransfer contentSection, String description) {
        this.language = language;
        this.contentSection = contentSection;
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
     * Returns the contentSection.
     * @return the contentSection
     */
    public ContentSectionTransfer getContentSection() {
        return contentSection;
    }

    /**
     * Sets the contentSection.
     * @param contentSection the contentSection to set
     */
    public void setContentSection(ContentSectionTransfer contentSection) {
        this.contentSection = contentSection;
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
