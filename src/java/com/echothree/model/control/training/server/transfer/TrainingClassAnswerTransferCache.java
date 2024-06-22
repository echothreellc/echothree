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

import com.echothree.model.control.training.common.transfer.TrainingClassAnswerTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassQuestionTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassAnswerDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TrainingClassAnswerTransferCache
        extends BaseTrainingTransferCache<TrainingClassAnswer, TrainingClassAnswerTransfer> {
    
    /** Creates a new instance of TrainingClassAnswerTransferCache */
    public TrainingClassAnswerTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        setIncludeEntityInstance(true);
    }
    
    public TrainingClassAnswerTransfer getTrainingClassAnswerTransfer(TrainingClassAnswer trainingClassAnswer) {
        TrainingClassAnswerTransfer trainingClassAnswerTransfer = get(trainingClassAnswer);
        
        if(trainingClassAnswerTransfer == null) {
            TrainingClassAnswerDetail trainingClassAnswerDetail = trainingClassAnswer.getLastDetail();
            TrainingClassQuestionTransfer trainingClassQuestion = trainingControl.getTrainingClassQuestionTransfer(userVisit, trainingClassAnswerDetail.getTrainingClassQuestion());
            String trainingClassAnswerName = trainingClassAnswerDetail.getTrainingClassAnswerName();
            Boolean isCorrect = trainingClassAnswerDetail.getIsCorrect();
            Integer sortOrder = trainingClassAnswerDetail.getSortOrder();
            
            trainingClassAnswerTransfer = new TrainingClassAnswerTransfer(trainingClassQuestion, trainingClassAnswerName, isCorrect, sortOrder);
            put(trainingClassAnswer, trainingClassAnswerTransfer);
        }
        
        return trainingClassAnswerTransfer;
    }
    
}
