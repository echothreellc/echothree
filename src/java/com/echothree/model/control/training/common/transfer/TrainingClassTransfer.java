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

import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class TrainingClassTransfer
        extends BaseTransfer {
    
    private String trainingClassName;
    private Long unformattedEstimatedReadingTime;
    private String estimatedReadingTime;
    private Long unformattedReadingTimeAllowed;
    private String readingTimeAllowed;
    private Long unformattedEstimatedTestingTime;
    private String estimatedTestingTime;
    private Long unformattedTestingTimeAllowed;
    private String testingTimeAllowed;
    private Long unformattedRequiredCompletionTime;
    private String requiredCompletionTime;
    private WorkEffortScopeTransfer workEffortScope;
    private Integer unformattedDefaultPercentageToPass;
    private String defaultPercentageToPass;
    private Integer overallQuestionCount;
    private Long unformattedTestingValidityTime;
    private String testingValidityTime;
    private Long unformattedExpiredRetentionTime;
    private String expiredRetentionTime;
    private Boolean alwaysReassignOnExpiration;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<TrainingClassSectionTransfer> trainingClassSections;
    private ListWrapper<PartyTrainingClassTransfer> partyTrainingClasses;
    
    /** Creates a new instance of TrainingClassTransfer */
    public TrainingClassTransfer(String trainingClassName, Long unformattedEstimatedReadingTime, String estimatedReadingTime, Long unformattedReadingTimeAllowed,
            String readingTimeAllowed, Long unformattedEstimatedTestingTime, String estimatedTestingTime, Long unformattedTestingTimeAllowed,
            String testingTimeAllowed, Long unformattedRequiredCompletionTime, String requiredCompletionTime, WorkEffortScopeTransfer workEffortScope,
            Integer unformattedDefaultPercentageToPass, String defaultPercentageToPass, Integer overallQuestionCount, Long unformattedTestingValidityTime,
            String testingValidityTime, Long unformattedExpiredRetentionTime, String expiredRetentionTime, Boolean alwaysReassignOnExpiration, Boolean isDefault,
            Integer sortOrder, String description) {
        this.trainingClassName = trainingClassName;
        this.unformattedEstimatedReadingTime = unformattedEstimatedReadingTime;
        this.estimatedReadingTime = estimatedReadingTime;
        this.unformattedReadingTimeAllowed = unformattedReadingTimeAllowed;
        this.readingTimeAllowed = readingTimeAllowed;
        this.unformattedEstimatedTestingTime = unformattedEstimatedTestingTime;
        this.estimatedTestingTime = estimatedTestingTime;
        this.unformattedTestingTimeAllowed = unformattedTestingTimeAllowed;
        this.testingTimeAllowed = testingTimeAllowed;
        this.unformattedRequiredCompletionTime = unformattedRequiredCompletionTime;
        this.requiredCompletionTime = requiredCompletionTime;
        this.workEffortScope = workEffortScope;
        this.unformattedDefaultPercentageToPass = unformattedDefaultPercentageToPass;
        this.defaultPercentageToPass = defaultPercentageToPass;
        this.overallQuestionCount = overallQuestionCount;
        this.unformattedTestingValidityTime = unformattedTestingValidityTime;
        this.testingValidityTime = testingValidityTime;
        this.unformattedExpiredRetentionTime = unformattedExpiredRetentionTime;
        this.expiredRetentionTime = expiredRetentionTime;
        this.alwaysReassignOnExpiration = alwaysReassignOnExpiration;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getTrainingClassName() {
        return trainingClassName;
    }

    public void setTrainingClassName(String trainingClassName) {
        this.trainingClassName = trainingClassName;
    }

    public Long getUnformattedEstimatedReadingTime() {
        return unformattedEstimatedReadingTime;
    }

    public void setUnformattedEstimatedReadingTime(Long unformattedEstimatedReadingTime) {
        this.unformattedEstimatedReadingTime = unformattedEstimatedReadingTime;
    }

    public String getEstimatedReadingTime() {
        return estimatedReadingTime;
    }

    public void setEstimatedReadingTime(String estimatedReadingTime) {
        this.estimatedReadingTime = estimatedReadingTime;
    }

    public Long getUnformattedReadingTimeAllowed() {
        return unformattedReadingTimeAllowed;
    }

    public void setUnformattedReadingTimeAllowed(Long unformattedReadingTimeAllowed) {
        this.unformattedReadingTimeAllowed = unformattedReadingTimeAllowed;
    }

    public String getReadingTimeAllowed() {
        return readingTimeAllowed;
    }

    public void setReadingTimeAllowed(String readingTimeAllowed) {
        this.readingTimeAllowed = readingTimeAllowed;
    }

    public Long getUnformattedEstimatedTestingTime() {
        return unformattedEstimatedTestingTime;
    }

    public void setUnformattedEstimatedTestingTime(Long unformattedEstimatedTestingTime) {
        this.unformattedEstimatedTestingTime = unformattedEstimatedTestingTime;
    }

    public String getEstimatedTestingTime() {
        return estimatedTestingTime;
    }

    public void setEstimatedTestingTime(String estimatedTestingTime) {
        this.estimatedTestingTime = estimatedTestingTime;
    }

    public Long getUnformattedTestingTimeAllowed() {
        return unformattedTestingTimeAllowed;
    }

    public void setUnformattedTestingTimeAllowed(Long unformattedTestingTimeAllowed) {
        this.unformattedTestingTimeAllowed = unformattedTestingTimeAllowed;
    }

    public String getTestingTimeAllowed() {
        return testingTimeAllowed;
    }

    public void setTestingTimeAllowed(String testingTimeAllowed) {
        this.testingTimeAllowed = testingTimeAllowed;
    }

    public Long getUnformattedRequiredCompletionTime() {
        return unformattedRequiredCompletionTime;
    }

    public void setUnformattedRequiredCompletionTime(Long unformattedRequiredCompletionTime) {
        this.unformattedRequiredCompletionTime = unformattedRequiredCompletionTime;
    }

    public String getRequiredCompletionTime() {
        return requiredCompletionTime;
    }

    public void setRequiredCompletionTime(String requiredCompletionTime) {
        this.requiredCompletionTime = requiredCompletionTime;
    }

    /**
     * Returns the workEffortScope.
     * @return the workEffortScope
     */
    public WorkEffortScopeTransfer getWorkEffortScope() {
        return workEffortScope;
    }

    /**
     * Sets the workEffortScope.
     * @param workEffortScope the workEffortScope to set
     */
    public void setWorkEffortScope(WorkEffortScopeTransfer workEffortScope) {
        this.workEffortScope = workEffortScope;
    }

    public Integer getUnformattedDefaultPercentageToPass() {
        return unformattedDefaultPercentageToPass;
    }

    public void setUnformattedDefaultPercentageToPass(Integer unformattedDefaultPercentageToPass) {
        this.unformattedDefaultPercentageToPass = unformattedDefaultPercentageToPass;
    }

    public String getDefaultPercentageToPass() {
        return defaultPercentageToPass;
    }

    public void setDefaultPercentageToPass(String defaultPercentageToPass) {
        this.defaultPercentageToPass = defaultPercentageToPass;
    }

    public Integer getOverallQuestionCount() {
        return overallQuestionCount;
    }

    public void setOverallQuestionCount(Integer overallQuestionCount) {
        this.overallQuestionCount = overallQuestionCount;
    }

    public Long getUnformattedTestingValidityTime() {
        return unformattedTestingValidityTime;
    }

    public void setUnformattedTestingValidityTime(Long unformattedTestingValidityTime) {
        this.unformattedTestingValidityTime = unformattedTestingValidityTime;
    }

    public String getTestingValidityTime() {
        return testingValidityTime;
    }

    public void setTestingValidityTime(String testingValidityTime) {
        this.testingValidityTime = testingValidityTime;
    }

    public Long getUnformattedExpiredRetentionTime() {
        return unformattedExpiredRetentionTime;
    }

    public void setUnformattedExpiredRetentionTime(Long unformattedExpiredRetentionTime) {
        this.unformattedExpiredRetentionTime = unformattedExpiredRetentionTime;
    }

    public String getExpiredRetentionTime() {
        return expiredRetentionTime;
    }

    public void setExpiredRetentionTime(String expiredRetentionTime) {
        this.expiredRetentionTime = expiredRetentionTime;
    }

    public Boolean getAlwaysReassignOnExpiration() {
        return alwaysReassignOnExpiration;
    }

    public void setAlwaysReassignOnExpiration(Boolean alwaysReassignOnExpiration) {
        this.alwaysReassignOnExpiration = alwaysReassignOnExpiration;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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

    public ListWrapper<TrainingClassSectionTransfer> getTrainingClassSections() {
        return trainingClassSections;
    }

    public void setTrainingClassSections(ListWrapper<TrainingClassSectionTransfer> trainingClassSections) {
        this.trainingClassSections = trainingClassSections;
    }

    public ListWrapper<PartyTrainingClassTransfer> getPartyTrainingClasses() {
        return partyTrainingClasses;
    }

    public void setPartyTrainingClasses(ListWrapper<PartyTrainingClassTransfer> partyTrainingClasses) {
        this.partyTrainingClasses = partyTrainingClasses;
    }

}
