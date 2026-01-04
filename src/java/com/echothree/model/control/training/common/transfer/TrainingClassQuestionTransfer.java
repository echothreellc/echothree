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

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class TrainingClassQuestionTransfer
        extends BaseTransfer {
    
    private TrainingClassSectionTransfer trainingClassSection;
    private String trainingClassQuestionName;
    private Boolean askingRequired;
    private Boolean passingRequired;
    private Integer sortOrder;
    
    private ListWrapper<TrainingClassAnswerTransfer> trainingClassAnswers;
    
    /** Creates a new instance of TrainingClassQuestionTransfer */
    public TrainingClassQuestionTransfer(TrainingClassSectionTransfer trainingClassSection, String trainingClassQuestionName, Boolean askingRequired,
            Boolean passingRequired, Integer sortOrder) {
        this.trainingClassSection = trainingClassSection;
        this.trainingClassQuestionName = trainingClassQuestionName;
        this.askingRequired = askingRequired;
        this.passingRequired = passingRequired;
        this.sortOrder = sortOrder;
    }

    public TrainingClassSectionTransfer getTrainingClassSection() {
        return trainingClassSection;
    }

    public void setTrainingClassSection(TrainingClassSectionTransfer trainingClassSection) {
        this.trainingClassSection = trainingClassSection;
    }

    public String getTrainingClassQuestionName() {
        return trainingClassQuestionName;
    }

    public void setTrainingClassQuestionName(String trainingClassQuestionName) {
        this.trainingClassQuestionName = trainingClassQuestionName;
    }

    public Boolean getAskingRequired() {
        return askingRequired;
    }

    public void setAskingRequired(Boolean askingRequired) {
        this.askingRequired = askingRequired;
    }

    public Boolean getPassingRequired() {
        return passingRequired;
    }

    public void setPassingRequired(Boolean passingRequired) {
        this.passingRequired = passingRequired;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ListWrapper<TrainingClassAnswerTransfer> getTrainingClassAnswers() {
        return trainingClassAnswers;
    }

    public void setTrainingClassAnswers(ListWrapper<TrainingClassAnswerTransfer> trainingClassAnswers) {
        this.trainingClassAnswers = trainingClassAnswers;
    }

}
