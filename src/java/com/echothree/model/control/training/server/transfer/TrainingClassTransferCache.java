// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.training.server.transfer;

import com.echothree.model.control.training.common.TrainingOptions;
import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TrainingClassTransferCache
        extends BaseTrainingTransferCache<TrainingClass, TrainingClassTransfer> {

    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);

    boolean includeTrainingClassSections;
    boolean includePartyTrainingClasses;
    
    /** Creates a new instance of TrainingClassTransferCache */
    protected TrainingClassTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeTrainingClassSections = options.contains(TrainingOptions.TrainingClassIncludeTrainingClassSections);
            includePartyTrainingClasses = options.contains(TrainingOptions.TrainingClassIncludePartyTrainingClasses);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public TrainingClassTransfer getTrainingClassTransfer(UserVisit userVisit, TrainingClass trainingClass) {
        var trainingClassTransfer = get(trainingClass);
        
        if(trainingClassTransfer == null) {
            var trainingClassDetail = trainingClass.getLastDetail();
            var trainingClassName = trainingClassDetail.getTrainingClassName();
            var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            var unitOfMeasureUtils = UnitOfMeasureUtils.getInstance();
            var unformattedEstimatedReadingTime = trainingClassDetail.getEstimatedReadingTime();
            var estimatedReadingTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedEstimatedReadingTime);
            var unformattedReadingTimeAllowed = trainingClassDetail.getReadingTimeAllowed();
            var readingTimeAllowed = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedReadingTimeAllowed);
            var unformattedEstimatedTestingTime = trainingClassDetail.getEstimatedTestingTime();
            var estimatedTestingTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedEstimatedTestingTime);
            var unformattedTestingTimeAllowed = trainingClassDetail.getTestingTimeAllowed();
            var testingTimeAllowed = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedTestingTimeAllowed);
            var unformattedRequiredCompletionTime = trainingClassDetail.getRequiredCompletionTime();
            var requiredCompletionTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedRequiredCompletionTime);
            var workEffortScope = trainingClassDetail.getWorkEffortScope();
            var workEffortScopeTransfer = workEffortScope == null ? null : workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortScope);
            var unformattedDefaultPercentageToPass = trainingClassDetail.getDefaultPercentageToPass();
            var defaultPercentageToPass = PercentUtils.getInstance().formatFractionalPercent(unformattedDefaultPercentageToPass);
            var overallQuestionCount = trainingClassDetail.getOverallQuestionCount();
            var unformattedTestingValidityTime = trainingClassDetail.getTestingValidityTime();
            var testingValidityTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedTestingValidityTime);
            var unformattedExpiredRetentionTime = trainingClassDetail.getExpiredRetentionTime();
            var expiredRetentionTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedExpiredRetentionTime);
            var alwaysReassignOnExpiration = trainingClassDetail.getAlwaysReassignOnExpiration();
            var isDefault = trainingClassDetail.getIsDefault();
            var sortOrder = trainingClassDetail.getSortOrder();
            var trainingClassTranslation = trainingControl.getBestTrainingClassTranslation(trainingClass, getLanguage(userVisit));
            var description = trainingClassTranslation == null ? trainingClassName : trainingClassTranslation.getDescription();
            
            trainingClassTransfer = new TrainingClassTransfer(trainingClassName, unformattedEstimatedReadingTime, estimatedReadingTime,
                    unformattedReadingTimeAllowed, readingTimeAllowed, unformattedEstimatedTestingTime, estimatedTestingTime, unformattedTestingTimeAllowed,
                    testingTimeAllowed, unformattedRequiredCompletionTime, requiredCompletionTime, workEffortScopeTransfer, unformattedDefaultPercentageToPass,
                    defaultPercentageToPass, overallQuestionCount, unformattedTestingValidityTime, testingValidityTime, unformattedExpiredRetentionTime,
                    expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, description);
            put(userVisit, trainingClass, trainingClassTransfer);
            
            if(includeTrainingClassSections) {
                trainingClassTransfer.setTrainingClassSections(new ListWrapper<>(trainingControl.getTrainingClassSectionTransfers(userVisit, trainingClass)));
            }
            
            if(includePartyTrainingClasses) {
                trainingClassTransfer.setPartyTrainingClasses(new ListWrapper<>(trainingControl.getPartyTrainingClassTransfersByTrainingClass(userVisit, trainingClass)));
            }
        }
        
        return trainingClassTransfer;
    }
    
}
