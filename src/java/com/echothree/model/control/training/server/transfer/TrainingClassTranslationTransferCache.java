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
import com.echothree.model.control.training.common.transfer.TrainingClassTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TrainingClassTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassTranslation, TrainingClassTranslationTransfer> {

    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    
    /** Creates a new instance of TrainingClassTranslationTransferCache */
    protected TrainingClassTranslationTransferCache() {
        super();
    }
    
    public TrainingClassTranslationTransfer getTrainingClassTranslationTransfer(UserVisit userVisit, TrainingClassTranslation trainingClassTranslation) {
        var trainingClassTranslationTransfer = get(trainingClassTranslation);
        
        if(trainingClassTranslationTransfer == null) {
            var trainingClassTransfer = trainingControl.getTrainingClassTransfer(userVisit, trainingClassTranslation.getTrainingClass());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassTranslation.getLanguage());
            var description = trainingClassTranslation.getDescription();
            var overviewMimeType = trainingClassTranslation.getOverviewMimeType();
            var overviewMimeTypeTransfer = overviewMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, overviewMimeType);
            var overview = trainingClassTranslation.getOverview();
            var introductionMimeType = trainingClassTranslation.getIntroductionMimeType();
            var introductionMimeTypeTransfer = introductionMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, introductionMimeType);
            var introduction = trainingClassTranslation.getIntroduction();
            
            trainingClassTranslationTransfer = new TrainingClassTranslationTransfer(trainingClassTransfer, languageTransfer, description,
                    overviewMimeTypeTransfer, overview, introductionMimeTypeTransfer, introduction);
            put(userVisit, trainingClassTranslation, trainingClassTranslationTransfer);
        }
        
        return trainingClassTranslationTransfer;
    }
    
}
