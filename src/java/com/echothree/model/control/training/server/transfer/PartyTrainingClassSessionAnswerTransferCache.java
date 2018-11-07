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

import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionAnswerTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionQuestionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassAnswerTransfer;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyTrainingClassSessionAnswerTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSessionAnswer, PartyTrainingClassSessionAnswerTransfer> {
    
    /** Creates a new instance of PartyTrainingClassSessionAnswerTransferCache */
    public PartyTrainingClassSessionAnswerTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
    }
    
    public PartyTrainingClassSessionAnswerTransfer getPartyTrainingClassSessionAnswerTransfer(PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        PartyTrainingClassSessionAnswerTransfer partyTrainingClassSessionAnswerTransfer = get(partyTrainingClassSessionAnswer);
        
        if(partyTrainingClassSessionAnswerTransfer == null) {
            PartyTrainingClassSessionQuestionTransfer partyTrainingClassSessionQuestionTransfer = trainingControl.getPartyTrainingClassSessionQuestionTransfer(userVisit, partyTrainingClassSessionAnswer.getPartyTrainingClassSessionQuestion());
            Integer partyTrainingClassSessionAnswerSequence = partyTrainingClassSessionAnswer.getPartyTrainingClassSessionAnswerSequence();
            TrainingClassAnswer trainingClassAnswer = partyTrainingClassSessionAnswer.getTrainingClassAnswer();
            TrainingClassAnswerTransfer trainingClassAnswerTransfer = trainingClassAnswer == null ? null : trainingControl.getTrainingClassAnswerTransfer(userVisit, trainingClassAnswer);
            Long unformattedQuestionStartTime = partyTrainingClassSessionAnswer.getQuestionStartTime();
            String questionStartTime = unformattedQuestionStartTime == null ? null : formatTypicalDateTime(unformattedQuestionStartTime);
            Long unformattedQuestionEndTime = partyTrainingClassSessionAnswer.getQuestionEndTime();
            String questionEndTime = unformattedQuestionEndTime == null ? null : formatTypicalDateTime(unformattedQuestionEndTime);

            partyTrainingClassSessionAnswerTransfer = new PartyTrainingClassSessionAnswerTransfer(partyTrainingClassSessionQuestionTransfer,
                    partyTrainingClassSessionAnswerSequence, trainingClassAnswerTransfer, unformattedQuestionStartTime, questionStartTime,
                    unformattedQuestionEndTime, questionEndTime);
            put(partyTrainingClassSessionAnswer, partyTrainingClassSessionAnswerTransfer);
        }
        
        return partyTrainingClassSessionAnswerTransfer;
    }
    
}
