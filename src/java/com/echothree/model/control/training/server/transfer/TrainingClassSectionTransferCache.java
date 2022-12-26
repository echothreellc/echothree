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
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.entity.TrainingClassSectionDetail;
import com.echothree.model.data.training.server.entity.TrainingClassSectionTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.string.PercentUtils;
import java.util.Set;

public class TrainingClassSectionTransferCache
        extends BaseTrainingTransferCache<TrainingClassSection, TrainingClassSectionTransfer> {
    
    boolean includeTrainingClassPages;
    boolean includeTrainingClassQuestions;
    
    /** Creates a new instance of TrainingClassSectionTransferCache */
    public TrainingClassSectionTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeTrainingClassPages = options.contains(TrainingOptions.TrainingClassSectionIncludeTrainingClassPages);
            includeTrainingClassQuestions = options.contains(TrainingOptions.TrainingClassSectionIncludeTrainingClassQuestions);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public TrainingClassSectionTransfer getTrainingClassSectionTransfer(TrainingClassSection trainingClassSection) {
        TrainingClassSectionTransfer trainingClassSectionTransfer = get(trainingClassSection);
        
        if(trainingClassSectionTransfer == null) {
            TrainingClassSectionDetail trainingClassSectionDetail = trainingClassSection.getLastDetail();
            TrainingClassTransfer trainingClass = trainingControl.getTrainingClassTransfer(userVisit, trainingClassSectionDetail.getTrainingClass());
            String trainingClassSectionName = trainingClassSectionDetail.getTrainingClassSectionName();
            Integer unformattedPercentageToPass = trainingClassSectionDetail.getPercentageToPass();
            String percentageToPass = PercentUtils.getInstance().formatFractionalPercent(unformattedPercentageToPass);
            Integer questionCount = trainingClassSectionDetail.getQuestionCount();
            Integer sortOrder = trainingClassSectionDetail.getSortOrder();
            TrainingClassSectionTranslation trainingClassSectionTranslation = trainingControl.getBestTrainingClassSectionTranslation(trainingClassSection, getLanguage());
            String description = trainingClassSectionTranslation == null ? trainingClassSectionName : trainingClassSectionTranslation.getDescription();
            
            trainingClassSectionTransfer = new TrainingClassSectionTransfer(trainingClass, trainingClassSectionName, unformattedPercentageToPass,
                    percentageToPass, questionCount, sortOrder, description);
            put(trainingClassSection, trainingClassSectionTransfer);
            
            if(includeTrainingClassPages) {
                trainingClassSectionTransfer.setTrainingClassPages(new ListWrapper<>(trainingControl.getTrainingClassPageTransfers(userVisit, trainingClassSection)));
            }
            
            if(includeTrainingClassQuestions) {
                trainingClassSectionTransfer.setTrainingClassQuestions(new ListWrapper<>(trainingControl.getTrainingClassQuestionTransfers(userVisit, trainingClassSection)));
            }
        }
        
        return trainingClassSectionTransfer;
    }
    
}
