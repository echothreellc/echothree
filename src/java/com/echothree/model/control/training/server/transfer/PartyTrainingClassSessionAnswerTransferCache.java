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

import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionAnswerTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionAnswer;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTrainingClassSessionAnswerTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSessionAnswer, PartyTrainingClassSessionAnswerTransfer> {

    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);

    /** Creates a new instance of PartyTrainingClassSessionAnswerTransferCache */
    public PartyTrainingClassSessionAnswerTransferCache() {
        super();
    }
    
    public PartyTrainingClassSessionAnswerTransfer getPartyTrainingClassSessionAnswerTransfer(UserVisit userVisit, PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        var partyTrainingClassSessionAnswerTransfer = get(partyTrainingClassSessionAnswer);
        
        if(partyTrainingClassSessionAnswerTransfer == null) {
            var partyTrainingClassSessionQuestionTransfer = trainingControl.getPartyTrainingClassSessionQuestionTransfer(userVisit, partyTrainingClassSessionAnswer.getPartyTrainingClassSessionQuestion());
            var partyTrainingClassSessionAnswerSequence = partyTrainingClassSessionAnswer.getPartyTrainingClassSessionAnswerSequence();
            var trainingClassAnswer = partyTrainingClassSessionAnswer.getTrainingClassAnswer();
            var trainingClassAnswerTransfer = trainingClassAnswer == null ? null : trainingControl.getTrainingClassAnswerTransfer(userVisit, trainingClassAnswer);
            var unformattedQuestionStartTime = partyTrainingClassSessionAnswer.getQuestionStartTime();
            var questionStartTime = unformattedQuestionStartTime == null ? null : formatTypicalDateTime(userVisit, unformattedQuestionStartTime);
            var unformattedQuestionEndTime = partyTrainingClassSessionAnswer.getQuestionEndTime();
            var questionEndTime = unformattedQuestionEndTime == null ? null : formatTypicalDateTime(userVisit, unformattedQuestionEndTime);

            partyTrainingClassSessionAnswerTransfer = new PartyTrainingClassSessionAnswerTransfer(partyTrainingClassSessionQuestionTransfer,
                    partyTrainingClassSessionAnswerSequence, trainingClassAnswerTransfer, unformattedQuestionStartTime, questionStartTime,
                    unformattedQuestionEndTime, questionEndTime);
            put(userVisit, partyTrainingClassSessionAnswer, partyTrainingClassSessionAnswerTransfer);
        }
        
        return partyTrainingClassSessionAnswerTransfer;
    }
    
}
