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

public class TrainingClassSectionTransfer
        extends BaseTransfer {
    
    private TrainingClassTransfer trainingClass;
    private String trainingClassSectionName;
    private Integer unformattedPercentageToPass;
    private String percentageToPass;
    private Integer questionCount;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<TrainingClassPageTransfer> trainingClassPages;
    private ListWrapper<TrainingClassQuestionTransfer> trainingClassQuestions;
    
    /** Creates a new instance of TrainingClassSectionTransfer */
    public TrainingClassSectionTransfer(TrainingClassTransfer trainingClass, String trainingClassSectionName, Integer unformattedPercentageToPass,
            String percentageToPass, Integer questionCount, Integer sortOrder, String description) {
        this.trainingClass = trainingClass;
        this.trainingClassSectionName = trainingClassSectionName;
        this.unformattedPercentageToPass = unformattedPercentageToPass;
        this.percentageToPass = percentageToPass;
        this.questionCount = questionCount;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public TrainingClassTransfer getTrainingClass() {
        return trainingClass;
    }

    public void setTrainingClass(TrainingClassTransfer trainingClass) {
        this.trainingClass = trainingClass;
    }

    public String getTrainingClassSectionName() {
        return trainingClassSectionName;
    }

    public void setTrainingClassSectionName(String trainingClassSectionName) {
        this.trainingClassSectionName = trainingClassSectionName;
    }

    public Integer getUnformattedPercentageToPass() {
        return unformattedPercentageToPass;
    }

    public void setUnformattedPercentageToPass(Integer unformattedPercentageToPass) {
        this.unformattedPercentageToPass = unformattedPercentageToPass;
    }

    public String getPercentageToPass() {
        return percentageToPass;
    }

    public void setPercentageToPass(String percentageToPass) {
        this.percentageToPass = percentageToPass;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ListWrapper<TrainingClassPageTransfer> getTrainingClassPages() {
        return trainingClassPages;
    }

    public void setTrainingClassPages(ListWrapper<TrainingClassPageTransfer> trainingClassPages) {
        this.trainingClassPages = trainingClassPages;
    }

    public ListWrapper<TrainingClassQuestionTransfer> getTrainingClassQuestions() {
        return trainingClassQuestions;
    }

    public void setTrainingClassQuestions(ListWrapper<TrainingClassQuestionTransfer> trainingClassQuestions) {
        this.trainingClassQuestions = trainingClassQuestions;
    }

}
