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
import com.echothree.model.control.training.common.transfer.TrainingClassAnswerTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassAnswerTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TrainingClassAnswerTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassAnswerTranslation, TrainingClassAnswerTranslationTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);

    /** Creates a new instance of TrainingClassAnswerTranslationTransferCache */
    protected TrainingClassAnswerTranslationTransferCache() {
        super();
    }
    
    public TrainingClassAnswerTranslationTransfer getTrainingClassAnswerTranslationTransfer(UserVisit userVisit, TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        var trainingClassAnswerTranslationTransfer = get(trainingClassAnswerTranslation);
        
        if(trainingClassAnswerTranslationTransfer == null) {
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassAnswerTranslation.getLanguage());
            var trainingClassAnswerTransfer = trainingControl.getTrainingClassAnswerTransfer(userVisit, trainingClassAnswerTranslation.getTrainingClassAnswer());
            var answerMimeTypeTransfer = mimeTypeControl.getMimeTypeTransfer(userVisit, trainingClassAnswerTranslation.getAnswerMimeType());
            var answer = trainingClassAnswerTranslation.getAnswer();
            var selectedMimeType = trainingClassAnswerTranslation.getSelectedMimeType();
            var selectedMimeTypeTransfer = selectedMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, selectedMimeType);
            var selected = trainingClassAnswerTranslation.getSelected();
            
            trainingClassAnswerTranslationTransfer = new TrainingClassAnswerTranslationTransfer(trainingClassAnswerTransfer, languageTransfer,
                    answerMimeTypeTransfer, answer, selectedMimeTypeTransfer, selected);
            put(userVisit, trainingClassAnswerTranslation, trainingClassAnswerTranslationTransfer);
        }
        
        return trainingClassAnswerTranslationTransfer;
    }
    
}
