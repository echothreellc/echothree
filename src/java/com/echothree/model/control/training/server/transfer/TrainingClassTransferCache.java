// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassDetail;
import com.echothree.model.data.training.server.entity.TrainingClassTranslation;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import java.util.Set;

public class TrainingClassTransferCache
        extends BaseTrainingTransferCache<TrainingClass, TrainingClassTransfer> {
    
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);
    boolean includeTrainingClassSections;
    boolean includePartyTrainingClasses;
    
    /** Creates a new instance of TrainingClassTransferCache */
    public TrainingClassTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeTrainingClassSections = options.contains(TrainingOptions.TrainingClassIncludeTrainingClassSections);
            includePartyTrainingClasses = options.contains(TrainingOptions.TrainingClassIncludePartyTrainingClasses);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public TrainingClassTransfer getTrainingClassTransfer(TrainingClass trainingClass) {
        TrainingClassTransfer trainingClassTransfer = get(trainingClass);
        
        if(trainingClassTransfer == null) {
            TrainingClassDetail trainingClassDetail = trainingClass.getLastDetail();
            String trainingClassName = trainingClassDetail.getTrainingClassName();
            UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            UnitOfMeasureUtils unitOfMeasureUtils = UnitOfMeasureUtils.getInstance();
            Long unformattedEstimatedReadingTime = trainingClassDetail.getEstimatedReadingTime();
            String estimatedReadingTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedEstimatedReadingTime);
            Long unformattedReadingTimeAllowed = trainingClassDetail.getReadingTimeAllowed();
            String readingTimeAllowed = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedReadingTimeAllowed);
            Long unformattedEstimatedTestingTime = trainingClassDetail.getEstimatedTestingTime();
            String estimatedTestingTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedEstimatedTestingTime);
            Long unformattedTestingTimeAllowed = trainingClassDetail.getTestingTimeAllowed();
            String testingTimeAllowed = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedTestingTimeAllowed);
            Long unformattedRequiredCompletionTime = trainingClassDetail.getRequiredCompletionTime();
            String requiredCompletionTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedRequiredCompletionTime);
            WorkEffortScope workEffortScope = trainingClassDetail.getWorkEffortScope();
            WorkEffortScopeTransfer workEffortScopeTransfer = workEffortScope == null ? null : workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortScope);
            Integer unformattedDefaultPercentageToPass = trainingClassDetail.getDefaultPercentageToPass();
            String defaultPercentageToPass = PercentUtils.getInstance().formatFractionalPercent(unformattedDefaultPercentageToPass);
            Integer overallQuestionCount = trainingClassDetail.getOverallQuestionCount();
            Long unformattedTestingValidityTime = trainingClassDetail.getTestingValidityTime();
            String testingValidityTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedTestingValidityTime);
            Long unformattedExpiredRetentionTime = trainingClassDetail.getExpiredRetentionTime();
            String expiredRetentionTime = unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedExpiredRetentionTime);
            Boolean alwaysReassignOnExpiration = trainingClassDetail.getAlwaysReassignOnExpiration();
            Boolean isDefault = trainingClassDetail.getIsDefault();
            Integer sortOrder = trainingClassDetail.getSortOrder();
            TrainingClassTranslation trainingClassTranslation = trainingControl.getBestTrainingClassTranslation(trainingClass, getLanguage());
            String description = trainingClassTranslation == null ? trainingClassName : trainingClassTranslation.getDescription();
            
            trainingClassTransfer = new TrainingClassTransfer(trainingClassName, unformattedEstimatedReadingTime, estimatedReadingTime,
                    unformattedReadingTimeAllowed, readingTimeAllowed, unformattedEstimatedTestingTime, estimatedTestingTime, unformattedTestingTimeAllowed,
                    testingTimeAllowed, unformattedRequiredCompletionTime, requiredCompletionTime, workEffortScopeTransfer, unformattedDefaultPercentageToPass,
                    defaultPercentageToPass, overallQuestionCount, unformattedTestingValidityTime, testingValidityTime, unformattedExpiredRetentionTime,
                    expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, description);
            put(trainingClass, trainingClassTransfer);
            
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
