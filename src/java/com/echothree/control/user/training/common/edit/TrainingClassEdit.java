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

package com.echothree.control.user.training.common.edit;

import com.echothree.control.user.training.common.spec.TrainingClassSpec;

public interface TrainingClassEdit
        extends TrainingClassSpec, TrainingClassTranslationEdit {
    
    String getEstimatedReadingTime();
    void setEstimatedReadingTime(String estimatedReadingTime);
    
    String getEstimatedReadingTimeUnitOfMeasureTypeName();
    void setEstimatedReadingTimeUnitOfMeasureTypeName(String estimatedReadingTimeUnitOfMeasureTypeName);
    
    String getReadingTimeAllowed();
    void setReadingTimeAllowed(String readingTimeAllowed);
    
    String getReadingTimeAllowedUnitOfMeasureTypeName();
    void setReadingTimeAllowedUnitOfMeasureTypeName(String readingTimeAllowedUnitOfMeasureTypeName);
    
    String getEstimatedTestingTime();
    void setEstimatedTestingTime(String estimatedTestingTime);
    
    String getEstimatedTestingTimeUnitOfMeasureTypeName();
    void setEstimatedTestingTimeUnitOfMeasureTypeName(String estimatedTestingTimeUnitOfMeasureTypeName);
    
    String getTestingTimeAllowed();
    void setTestingTimeAllowed(String testingTimeAllowed);

    String getTestingTimeAllowedUnitOfMeasureTypeName();
    void setTestingTimeAllowedUnitOfMeasureTypeName(String testingTimeAllowedUnitOfMeasureTypeName);
    
    String getRequiredCompletionTime();
    void setRequiredCompletionTime(String requiredCompletionTime);

    String getRequiredCompletionTimeUnitOfMeasureTypeName();
    void setRequiredCompletionTimeUnitOfMeasureTypeName(String requiredCompletionTimeUnitOfMeasureTypeName);

    String getWorkEffortScopeName();
    void setWorkEffortScopeName(String workEffortScopeName);

    String getDefaultPercentageToPass();
    void setDefaultPercentageToPass(String defaultPercentageToPass);
    
    String getOverallQuestionCount();
    void setOverallQuestionCount(String overallQuestionCount);
    
    String getTestingValidityTime();
    void setTestingValidityTime(String testingValidityTime);
    
    String getTestingValidityTimeUnitOfMeasureTypeName();
    void setTestingValidityTimeUnitOfMeasureTypeName(String testingValidityTimeUnitOfMeasureTypeName);
    
    String getExpiredRetentionTime();
    void setExpiredRetentionTime(String expiredRetentionTime);
    
    String getExpiredRetentionTimeUnitOfMeasureTypeName();
    void setExpiredRetentionTimeUnitOfMeasureTypeName(String expiredRetentionTimeUnitOfMeasureTypeName);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getAlwaysReassignOnExpiration();
    void setAlwaysReassignOnExpiration(String alwaysReassignOnExpiration);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
