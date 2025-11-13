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
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionPageTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionQuestionTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionSectionTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;

public class PartyTrainingClassSessionTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSession, PartyTrainingClassSessionTransfer> {
    
    boolean includePartyTrainingClassSessionPages;
    boolean includePartyTrainingClassSessionQuestions;
    
    /** Creates a new instance of PartyTrainingClassSessionTransferCache */
    public PartyTrainingClassSessionTransferCache(TrainingControl trainingControl) {
        super(trainingControl);
        
        var options = session.getOptions();
        if(options != null) {
            includePartyTrainingClassSessionPages = options.contains(TrainingOptions.PartyTrainingClassSessionIncludePartyTrainingClassSessionPages);
            includePartyTrainingClassSessionQuestions = options.contains(TrainingOptions.PartyTrainingClassSessionIncludePartyTrainingClassSessionQuestions);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyTrainingClassSessionTransfer getPartyTrainingClassSessionTransfer(PartyTrainingClassSession partyTrainingClassSession) {
        var partyTrainingClassSessionTransfer = get(partyTrainingClassSession);
        
        if(partyTrainingClassSessionTransfer == null) {
            var partyTrainingClassSessionDetail = partyTrainingClassSession.getLastDetail();
            var partyTrainingClassTransfer = trainingControl.getPartyTrainingClassTransfer(userVisit, partyTrainingClassSessionDetail.getPartyTrainingClass());
            var partyTrainingClassSessionSequence = partyTrainingClassSessionDetail.getPartyTrainingClassSessionSequence();
            var partyTrainingClassSessionStatus = trainingControl.getPartyTrainingClassSessionStatus(partyTrainingClassSession);
            PartyTrainingClassSessionSectionTransfer lastPartyTrainingClassSessionSectionTransfer = null;
            PartyTrainingClassSessionPageTransfer lastPartyTrainingClassSessionPageTransfer = null;
            PartyTrainingClassSessionQuestionTransfer lastPartyTrainingClassSessionQuestionTransfer = null;
            
            if(partyTrainingClassSessionStatus != null) {
                var lastPartyTrainingClassSessionSection = partyTrainingClassSessionStatus.getLastPartyTrainingClassSessionSection();
                var lastPartyTrainingClassSessionPage = partyTrainingClassSessionStatus.getLastPartyTrainingClassSessionPage();
                var lastPartyTrainingClassSessionQuestion = partyTrainingClassSessionStatus.getLastPartyTrainingClassSessionQuestion();

                lastPartyTrainingClassSessionSectionTransfer = lastPartyTrainingClassSessionSection == null ? null : trainingControl.getPartyTrainingClassSessionSectionTransfer(userVisit, lastPartyTrainingClassSessionSection);
                lastPartyTrainingClassSessionPageTransfer = lastPartyTrainingClassSessionPage == null ? null : trainingControl.getPartyTrainingClassSessionPageTransfer(userVisit, lastPartyTrainingClassSessionPage);
                lastPartyTrainingClassSessionQuestionTransfer = lastPartyTrainingClassSessionQuestion == null ? null : trainingControl.getPartyTrainingClassSessionQuestionTransfer(userVisit, lastPartyTrainingClassSessionQuestion);
            }
            
            partyTrainingClassSessionTransfer = new PartyTrainingClassSessionTransfer(partyTrainingClassTransfer, partyTrainingClassSessionSequence,
                    lastPartyTrainingClassSessionSectionTransfer, lastPartyTrainingClassSessionPageTransfer, lastPartyTrainingClassSessionQuestionTransfer);
            put(userVisit, partyTrainingClassSession, partyTrainingClassSessionTransfer);
            
            if(includePartyTrainingClassSessionPages) {
                partyTrainingClassSessionTransfer.setPartyTrainingClassSessionPages(new ListWrapper<>(trainingControl.getPartyTrainingClassSessionPageTransfersByPartyTrainingClassSession(userVisit, partyTrainingClassSession)));
            }
            
            if(includePartyTrainingClassSessionQuestions) {
                partyTrainingClassSessionTransfer.setPartyTrainingClassSessionQuestions(new ListWrapper<>(trainingControl.getPartyTrainingClassSessionQuestionTransfersByPartyTrainingClassSession(userVisit, partyTrainingClassSession)));
            }
        }
        
        return partyTrainingClassSessionTransfer;
    }
    
}
