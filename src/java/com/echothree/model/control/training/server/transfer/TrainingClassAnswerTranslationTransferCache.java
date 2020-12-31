// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassAnswerTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassAnswerTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassAnswerTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrainingClassAnswerTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassAnswerTranslation, TrainingClassAnswerTranslationTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of TrainingClassAnswerTranslationTransferCache */
    public TrainingClassAnswerTranslationTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
    }
    
    public TrainingClassAnswerTranslationTransfer getTrainingClassAnswerTranslationTransfer(TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        TrainingClassAnswerTranslationTransfer trainingClassAnswerTranslationTransfer = get(trainingClassAnswerTranslation);
        
        if(trainingClassAnswerTranslationTransfer == null) {
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassAnswerTranslation.getLanguage());
            TrainingClassAnswerTransfer trainingClassAnswerTransfer = trainingControl.getTrainingClassAnswerTransfer(userVisit, trainingClassAnswerTranslation.getTrainingClassAnswer());
            MimeTypeTransfer answerMimeTypeTransfer = coreControl.getMimeTypeTransfer(userVisit, trainingClassAnswerTranslation.getAnswerMimeType());
            String answer = trainingClassAnswerTranslation.getAnswer();
            MimeType selectedMimeType = trainingClassAnswerTranslation.getSelectedMimeType();
            MimeTypeTransfer selectedMimeTypeTransfer = selectedMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, selectedMimeType);
            String selected = trainingClassAnswerTranslation.getSelected();
            
            trainingClassAnswerTranslationTransfer = new TrainingClassAnswerTranslationTransfer(trainingClassAnswerTransfer, languageTransfer,
                    answerMimeTypeTransfer, answer, selectedMimeTypeTransfer, selected);
            put(trainingClassAnswerTranslation, trainingClassAnswerTranslationTransfer);
        }
        
        return trainingClassAnswerTranslationTransfer;
    }
    
}
