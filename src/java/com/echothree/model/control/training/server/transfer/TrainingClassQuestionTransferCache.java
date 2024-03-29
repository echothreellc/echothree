// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.training.common.transfer.TrainingClassQuestionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassQuestionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class TrainingClassQuestionTransferCache
        extends BaseTrainingTransferCache<TrainingClassQuestion, TrainingClassQuestionTransfer> {
    
    boolean includeTrainingClassAnswers;
    
    /** Creates a new instance of TrainingClassQuestionTransferCache */
    public TrainingClassQuestionTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeTrainingClassAnswers = options.contains(TrainingOptions.TrainingClassQuestionIncludeTrainingClassAnswers);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public TrainingClassQuestionTransfer getTrainingClassQuestionTransfer(TrainingClassQuestion trainingClassQuestion) {
        TrainingClassQuestionTransfer trainingClassQuestionTransfer = get(trainingClassQuestion);
        
        if(trainingClassQuestionTransfer == null) {
            TrainingClassQuestionDetail trainingClassQuestionDetail = trainingClassQuestion.getLastDetail();
            TrainingClassSectionTransfer trainingClassSection = trainingControl.getTrainingClassSectionTransfer(userVisit, trainingClassQuestionDetail.getTrainingClassSection());
            String trainingClassQuestionName = trainingClassQuestionDetail.getTrainingClassQuestionName();
            Boolean askingRequired = trainingClassQuestionDetail.getAskingRequired();
            Boolean passingRequired = trainingClassQuestionDetail.getPassingRequired();
            Integer sortOrder = trainingClassQuestionDetail.getSortOrder();
            
            trainingClassQuestionTransfer = new TrainingClassQuestionTransfer(trainingClassSection, trainingClassQuestionName, askingRequired, passingRequired,
                    sortOrder);
            put(trainingClassQuestion, trainingClassQuestionTransfer);
            
            if(includeTrainingClassAnswers) {
                trainingClassQuestionTransfer.setTrainingClassAnswers(new ListWrapper<>(trainingControl.getTrainingClassAnswerTransfers(userVisit, trainingClassQuestion)));
            }
        }
        
        return trainingClassQuestionTransfer;
    }
    
}
