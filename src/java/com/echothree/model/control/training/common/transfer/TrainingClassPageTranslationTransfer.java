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

package com.echothree.model.control.training.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class TrainingClassPageTranslationTransfer
        extends BaseTransfer {
    
    private TrainingClassPageTransfer trainingClassPage;
    private LanguageTransfer language;
    private String description;
    private MimeTypeTransfer pageMimeType;
    private String page;
    
    /** Creates a new instance of TrainingClassPageTranslationTransfer */
    public TrainingClassPageTranslationTransfer(TrainingClassPageTransfer trainingClassPage, LanguageTransfer language, String description,
            MimeTypeTransfer pageMimeType, String page) {
        this.trainingClassPage = trainingClassPage;
        this.language = language;
        this.description = description;
        this.pageMimeType = pageMimeType;
        this.page = page;
    }

    public TrainingClassPageTransfer getTrainingClassPage() {
        return trainingClassPage;
    }

    public void setTrainingClassPage(TrainingClassPageTransfer trainingClassPage) {
        this.trainingClassPage = trainingClassPage;
    }

    public LanguageTransfer getLanguage() {
        return language;
    }

    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MimeTypeTransfer getPageMimeType() {
        return pageMimeType;
    }

    public void setPageMimeType(MimeTypeTransfer pageMimeType) {
        this.pageMimeType = pageMimeType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
