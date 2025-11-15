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
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassSectionTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrainingClassSectionTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassSectionTranslation, TrainingClassSectionTranslationTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);

    /** Creates a new instance of TrainingClassSectionTranslationTransferCache */
    public TrainingClassSectionTranslationTransferCache() {
        super();
    }
    
    public TrainingClassSectionTranslationTransfer getTrainingClassSectionTranslationTransfer(UserVisit userVisit, TrainingClassSectionTranslation trainingClassSectionTranslation) {
        var trainingClassSectionTranslationTransfer = get(trainingClassSectionTranslation);
        
        if(trainingClassSectionTranslationTransfer == null) {
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassSectionTranslation.getLanguage());
            var trainingClassSectionTransfer = trainingControl.getTrainingClassSectionTransfer(userVisit, trainingClassSectionTranslation.getTrainingClassSection());
            var description = trainingClassSectionTranslation.getDescription();
            var overviewMimeType = trainingClassSectionTranslation.getOverviewMimeType();
            var overviewMimeTypeTransfer = overviewMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, overviewMimeType);
            var overview = trainingClassSectionTranslation.getOverview();
            var introductionMimeType = trainingClassSectionTranslation.getIntroductionMimeType();
            var introductionMimeTypeTransfer = introductionMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, introductionMimeType);
            var introduction = trainingClassSectionTranslation.getIntroduction();
            
            trainingClassSectionTranslationTransfer = new TrainingClassSectionTranslationTransfer(trainingClassSectionTransfer, languageTransfer, description,
                    overviewMimeTypeTransfer, overview, introductionMimeTypeTransfer, introduction);
            put(userVisit, trainingClassSectionTranslation, trainingClassSectionTranslationTransfer);
        }
        
        return trainingClassSectionTranslationTransfer;
    }
    
}
