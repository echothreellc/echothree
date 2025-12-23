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

public class TrainingClassSectionTranslationTransfer
        extends BaseTransfer {
    
    private TrainingClassSectionTransfer trainingClassSection;
    private LanguageTransfer language;
    private String description;
    private MimeTypeTransfer overviewMimeType;
    private String overview;
    private MimeTypeTransfer introductionMimeType;
    private String introduction;
    
    /** Creates a new instance of TrainingClassSectionTranslationTransfer */
    public TrainingClassSectionTranslationTransfer(TrainingClassSectionTransfer trainingClassSection, LanguageTransfer language, String description,
            MimeTypeTransfer overviewMimeType, String overview, MimeTypeTransfer introductionMimeType, String introduction) {
        this.trainingClassSection = trainingClassSection;
        this.language = language;
        this.description = description;
        this.overviewMimeType = overviewMimeType;
        this.overview = overview;
        this.introductionMimeType = introductionMimeType;
        this.introduction = introduction;
    }

    public TrainingClassSectionTransfer getTrainingClassSection() {
        return trainingClassSection;
    }

    public void setTrainingClassSection(TrainingClassSectionTransfer trainingClassSection) {
        this.trainingClassSection = trainingClassSection;
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

    public MimeTypeTransfer getOverviewMimeType() {
        return overviewMimeType;
    }

    public void setOverviewMimeType(MimeTypeTransfer overviewMimeType) {
        this.overviewMimeType = overviewMimeType;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MimeTypeTransfer getIntroductionMimeType() {
        return introductionMimeType;
    }

    public void setIntroductionMimeType(MimeTypeTransfer introductionMimeType) {
        this.introductionMimeType = introductionMimeType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
