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

public class LinkTypeTranslationTransfer
        extends BaseTransfer {

    private LinkTypeTransfer linkType;
    private LanguageTransfer language;
    private String description;
    private MimeTypeTransfer guidanceMimeType;
    private String guidance;

    public LinkTypeTranslationTransfer(LinkTypeTransfer linkType, LanguageTransfer language, String description, MimeTypeTransfer guidanceMimeType, String guidance) {
        this.linkType = linkType;
        this.language = language;
        this.description = description;
        this.guidanceMimeType = guidanceMimeType;
        this.guidance = guidance;
    }

    public LanguageTransfer getLanguage() {
        return language;
    }

    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    public LinkTypeTransfer getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkTypeTransfer linkType) {
        this.linkType = linkType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MimeTypeTransfer getGuidanceMimeType() {
        return guidanceMimeType;
    }

    public void setGuidanceMimeType(MimeTypeTransfer guidanceMimeType) {
        this.guidanceMimeType = guidanceMimeType;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

}
