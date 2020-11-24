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
import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrainingClassTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassTranslation, TrainingClassTranslationTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of TrainingClassTranslationTransferCache */
    public TrainingClassTranslationTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
    }
    
    public TrainingClassTranslationTransfer getTrainingClassTranslationTransfer(TrainingClassTranslation trainingClassTranslation) {
        TrainingClassTranslationTransfer trainingClassTranslationTransfer = get(trainingClassTranslation);
        
        if(trainingClassTranslationTransfer == null) {
            TrainingClassTransfer trainingClassTransfer = trainingControl.getTrainingClassTransfer(userVisit, trainingClassTranslation.getTrainingClass());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassTranslation.getLanguage());
            String description = trainingClassTranslation.getDescription();
            MimeType overviewMimeType = trainingClassTranslation.getOverviewMimeType();
            MimeTypeTransfer overviewMimeTypeTransfer = overviewMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, overviewMimeType);
            String overview = trainingClassTranslation.getOverview();
            MimeType introductionMimeType = trainingClassTranslation.getIntroductionMimeType();
            MimeTypeTransfer introductionMimeTypeTransfer = introductionMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, introductionMimeType);
            String introduction = trainingClassTranslation.getIntroduction();
            
            trainingClassTranslationTransfer = new TrainingClassTranslationTransfer(trainingClassTransfer, languageTransfer, description,
                    overviewMimeTypeTransfer, overview, introductionMimeTypeTransfer, introduction);
            put(trainingClassTranslation, trainingClassTranslationTransfer);
        }
        
        return trainingClassTranslationTransfer;
    }
    
}
