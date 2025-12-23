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

public class TrainingClassPageTransfer
        extends BaseTransfer {
    
    private TrainingClassSectionTransfer trainingClassSection;
    private String trainingClassPageName;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of TrainingClassPageTransfer */
    public TrainingClassPageTransfer(TrainingClassSectionTransfer trainingClassSection, String trainingClassPageName, Integer sortOrder, String description) {
        this.trainingClassSection = trainingClassSection;
        this.trainingClassPageName = trainingClassPageName;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public TrainingClassSectionTransfer getTrainingClassSection() {
        return trainingClassSection;
    }

    public void setTrainingClassSection(TrainingClassSectionTransfer trainingClassSection) {
        this.trainingClassSection = trainingClassSection;
    }

    public String getTrainingClassPageName() {
        return trainingClassPageName;
    }

    public void setTrainingClassPageName(String trainingClassPageName) {
        this.trainingClassPageName = trainingClassPageName;
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

}
