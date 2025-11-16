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

import com.echothree.model.control.training.common.TrainingOptions;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionQuestionTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestion;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTrainingClassSessionQuestionTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSessionQuestion, PartyTrainingClassSessionQuestionTransfer> {

    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);

    boolean includePartyTrainingClassSessionAnswers;
    
    /** Creates a new instance of PartyTrainingClassSessionQuestionTransferCache */
    public PartyTrainingClassSessionQuestionTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includePartyTrainingClassSessionAnswers = options.contains(TrainingOptions.PartyTrainingClassSessionQuestionIncludePartyTrainingClassSessionAnswers);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyTrainingClassSessionQuestionTransfer getPartyTrainingClassSessionQuestionTransfer(UserVisit userVisit, PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion) {
        var partyTrainingClassSessionQuestionTransfer = get(partyTrainingClassSessionQuestion);
        
        if(partyTrainingClassSessionQuestionTransfer == null) {
            var partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestion.getLastDetail();
            var partyTrainingClassSessionTransfer = trainingControl.getPartyTrainingClassSessionTransfer(userVisit, partyTrainingClassSessionQuestionDetail.getPartyTrainingClassSession());
            var trainingClassQuestionTransfer = trainingControl.getTrainingClassQuestionTransfer(userVisit, partyTrainingClassSessionQuestionDetail.getTrainingClassQuestion());
            var sortOrder = partyTrainingClassSessionQuestionDetail.getSortOrder();

            partyTrainingClassSessionQuestionTransfer = new PartyTrainingClassSessionQuestionTransfer(partyTrainingClassSessionTransfer,
                    trainingClassQuestionTransfer, sortOrder);
            put(userVisit, partyTrainingClassSessionQuestion, partyTrainingClassSessionQuestionTransfer);
            
            if(includePartyTrainingClassSessionAnswers) {
                partyTrainingClassSessionQuestionTransfer.setPartyTrainingClassSessionAnswers(new ListWrapper<>(trainingControl.getPartyTrainingClassSessionAnswerTransfersByPartyTrainingClassSessionQuestion(userVisit, partyTrainingClassSessionQuestion)));
            }
        }
        
        return partyTrainingClassSessionQuestionTransfer;
    }
    
}
