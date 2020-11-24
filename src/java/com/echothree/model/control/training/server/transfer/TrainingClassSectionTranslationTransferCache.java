// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassSectionTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrainingClassSectionTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassSectionTranslation, TrainingClassSectionTranslationTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of TrainingClassSectionTranslationTransferCache */
    public TrainingClassSectionTranslationTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
    }
    
    public TrainingClassSectionTranslationTransfer getTrainingClassSectionTranslationTransfer(TrainingClassSectionTranslation trainingClassSectionTranslation) {
        TrainingClassSectionTranslationTransfer trainingClassSectionTranslationTransfer = get(trainingClassSectionTranslation);
        
        if(trainingClassSectionTranslationTransfer == null) {
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassSectionTranslation.getLanguage());
            TrainingClassSectionTransfer trainingClassSectionTransfer = trainingControl.getTrainingClassSectionTransfer(userVisit, trainingClassSectionTranslation.getTrainingClassSection());
            String description = trainingClassSectionTranslation.getDescription();
            MimeType overviewMimeType = trainingClassSectionTranslation.getOverviewMimeType();
            MimeTypeTransfer overviewMimeTypeTransfer = overviewMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, overviewMimeType);
            String overview = trainingClassSectionTranslation.getOverview();
            MimeType introductionMimeType = trainingClassSectionTranslation.getIntroductionMimeType();
            MimeTypeTransfer introductionMimeTypeTransfer = introductionMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, introductionMimeType);
            String introduction = trainingClassSectionTranslation.getIntroduction();
            
            trainingClassSectionTranslationTransfer = new TrainingClassSectionTranslationTransfer(trainingClassSectionTransfer, languageTransfer, description,
                    overviewMimeTypeTransfer, overview, introductionMimeTypeTransfer, introduction);
            put(trainingClassSectionTranslation, trainingClassSectionTranslationTransfer);
        }
        
        return trainingClassSectionTranslationTransfer;
    }
    
}
