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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassPageTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassPageTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassPageTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrainingClassPageTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassPageTranslation, TrainingClassPageTranslationTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of TrainingClassPageTranslationTransferCache */
    public TrainingClassPageTranslationTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
    }
    
    public TrainingClassPageTranslationTransfer getTrainingClassPageTranslationTransfer(TrainingClassPageTranslation trainingClassPageTranslation) {
        TrainingClassPageTranslationTransfer trainingClassPageTranslationTransfer = get(trainingClassPageTranslation);
        
        if(trainingClassPageTranslationTransfer == null) {
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassPageTranslation.getLanguage());
            TrainingClassPageTransfer trainingClassPageTransfer = trainingControl.getTrainingClassPageTransfer(userVisit, trainingClassPageTranslation.getTrainingClassPage());
            String description = trainingClassPageTranslation.getDescription();
            MimeTypeTransfer pageMimeTypeTransfer = coreControl.getMimeTypeTransfer(userVisit, trainingClassPageTranslation.getPageMimeType());
            String page = trainingClassPageTranslation.getPage();
            
            trainingClassPageTranslationTransfer = new TrainingClassPageTranslationTransfer(trainingClassPageTransfer, languageTransfer, description,
                    pageMimeTypeTransfer, page);
            put(trainingClassPageTranslation, trainingClassPageTranslationTransfer);
        }
        
        return trainingClassPageTranslationTransfer;
    }
    
}
