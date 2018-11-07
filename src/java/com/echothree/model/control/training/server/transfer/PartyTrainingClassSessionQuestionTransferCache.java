// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.training.common.TrainingOptions;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionQuestionTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassQuestionTransfer;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestion;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class PartyTrainingClassSessionQuestionTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSessionQuestion, PartyTrainingClassSessionQuestionTransfer> {
    
    boolean includePartyTrainingClassSessionAnswers;
    
    /** Creates a new instance of PartyTrainingClassSessionQuestionTransferCache */
    public PartyTrainingClassSessionQuestionTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includePartyTrainingClassSessionAnswers = options.contains(TrainingOptions.PartyTrainingClassSessionQuestionIncludePartyTrainingClassSessionAnswers);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyTrainingClassSessionQuestionTransfer getPartyTrainingClassSessionQuestionTransfer(PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        PartyTrainingClassSessionQuestionTransfer partyTrainingClassSessionQuestionTransfer = get(partyTrainingClassSessionQuestion);
        
        if(partyTrainingClassSessionQuestionTransfer == null) {
            PartyTrainingClassSessionQuestionDetail partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestion.getLastDetail();
            PartyTrainingClassSessionTransfer partyTrainingClassSessionTransfer = trainingControl.getPartyTrainingClassSessionTransfer(userVisit, partyTrainingClassSessionQuestionDetail.getPartyTrainingClassSession());
            TrainingClassQuestionTransfer trainingClassQuestionTransfer = trainingControl.getTrainingClassQuestionTransfer(userVisit, partyTrainingClassSessionQuestionDetail.getTrainingClassQuestion());
            Integer sortOrder = partyTrainingClassSessionQuestionDetail.getSortOrder();

            partyTrainingClassSessionQuestionTransfer = new PartyTrainingClassSessionQuestionTransfer(partyTrainingClassSessionTransfer,
                    trainingClassQuestionTransfer, sortOrder);
            put(partyTrainingClassSessionQuestion, partyTrainingClassSessionQuestionTransfer);
            
            if(includePartyTrainingClassSessionAnswers) {
                partyTrainingClassSessionQuestionTransfer.setPartyTrainingClassSessionAnswers(new ListWrapper<>(trainingControl.getPartyTrainingClassSessionAnswerTransfersByPartyTrainingClassSessionQuestion(userVisit, partyTrainingClassSessionQuestion)));
            }
        }
        
        return partyTrainingClassSessionQuestionTransfer;
    }
    
}
