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
import com.echothree.model.control.training.common.transfer.TrainingClassPageTranslationTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassPageTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrainingClassPageTranslationTransferCache
        extends BaseTrainingDescriptionTransferCache<TrainingClassPageTranslation, TrainingClassPageTranslationTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    
    /** Creates a new instance of TrainingClassPageTranslationTransferCache */
    public TrainingClassPageTranslationTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
    }
    
    public TrainingClassPageTranslationTransfer getTrainingClassPageTranslationTransfer(TrainingClassPageTranslation trainingClassPageTranslation) {
        var trainingClassPageTranslationTransfer = get(trainingClassPageTranslation);
        
        if(trainingClassPageTranslationTransfer == null) {
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, trainingClassPageTranslation.getLanguage());
            var trainingClassPageTransfer = trainingControl.getTrainingClassPageTransfer(userVisit, trainingClassPageTranslation.getTrainingClassPage());
            var description = trainingClassPageTranslation.getDescription();
            var pageMimeTypeTransfer = mimeTypeControl.getMimeTypeTransfer(userVisit, trainingClassPageTranslation.getPageMimeType());
            var page = trainingClassPageTranslation.getPage();
            
            trainingClassPageTranslationTransfer = new TrainingClassPageTranslationTransfer(trainingClassPageTransfer, languageTransfer, description,
                    pageMimeTypeTransfer, page);
            put(userVisit, trainingClassPageTranslation, trainingClassPageTranslationTransfer);
        }
        
        return trainingClassPageTranslationTransfer;
    }
    
}
