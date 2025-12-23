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

public class TrainingClassAnswerTranslationTransfer
        extends BaseTransfer {
    
    private TrainingClassAnswerTransfer trainingClassAnswer;
    private LanguageTransfer language;
    private MimeTypeTransfer answerMimeType;
    private String answer;
    private MimeTypeTransfer selectedMimeType;
    private String selected;
    
    /** Creates a new instance of TrainingClassAnswerTranslationTransfer */
    public TrainingClassAnswerTranslationTransfer(TrainingClassAnswerTransfer trainingClassAnswer, LanguageTransfer language, MimeTypeTransfer answerMimeType,
            String answer, MimeTypeTransfer selectedMimeType, String selected) {
        this.trainingClassAnswer = trainingClassAnswer;
        this.language = language;
        this.answerMimeType = answerMimeType;
        this.answer = answer;
        this.selectedMimeType = selectedMimeType;
        this.selected = selected;
    }

    public TrainingClassAnswerTransfer getTrainingClassAnswer() {
        return trainingClassAnswer;
    }

    public void setTrainingClassAnswer(TrainingClassAnswerTransfer trainingClassAnswer) {
        this.trainingClassAnswer = trainingClassAnswer;
    }

    public LanguageTransfer getLanguage() {
        return language;
    }

    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    public MimeTypeTransfer getAnswerMimeType() {
        return answerMimeType;
    }

    public void setAnswerMimeType(MimeTypeTransfer answerMimeType) {
        this.answerMimeType = answerMimeType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public MimeTypeTransfer getSelectedMimeType() {
        return selectedMimeType;
    }

    public void setSelectedMimeType(MimeTypeTransfer selectedMimeType) {
        this.selectedMimeType = selectedMimeType;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

}
