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

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.training.common.transfer.TrainingClassQuestionTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassQuestionTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrainingClassQuestionTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassQuestionTranslation, TrainingClassQuestionTranslationTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    
    /** Creates a new instance of TrainingClassQuestionTranslationTransferCache */
    public TrainingClassQuestionTranslationTransferCache(TrainingControl trainingControl) {
        super(trainingControl);
    }
    
    public TrainingClassQuestionTranslationTransfer getTrainingClassQuestionTranslationTransfer(UserVisit userVisit, TrainingClassQuestionTranslation trainingClassQuestionTranslation) {
        var trainingClassQuestionTranslationTransfer = get(trainingClassQuestionTranslation);
        
        if(trainingClassQuestionTranslationTransfer == null) {
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassQuestionTranslation.getLanguage());
            var trainingClassQuestionTransfer = trainingControl.getTrainingClassQuestionTransfer(userVisit, trainingClassQuestionTranslation.getTrainingClassQuestion());
            var questionMimeTypeTransfer = mimeTypeControl.getMimeTypeTransfer(userVisit, trainingClassQuestionTranslation.getQuestionMimeType());
            var question = trainingClassQuestionTranslation.getQuestion();
            
            trainingClassQuestionTranslationTransfer = new TrainingClassQuestionTranslationTransfer(trainingClassQuestionTransfer, languageTransfer,
                    questionMimeTypeTransfer, question);
            put(userVisit, trainingClassQuestionTranslation, trainingClassQuestionTranslationTransfer);
        }
        
        return trainingClassQuestionTranslationTransfer;
    }
    
}
