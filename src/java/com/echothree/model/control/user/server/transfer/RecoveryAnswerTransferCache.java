// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.common.transfer.RecoveryAnswerTransfer;
import com.echothree.model.control.user.common.transfer.RecoveryQuestionTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.RecoveryAnswer;
import com.echothree.model.data.user.server.entity.RecoveryAnswerDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class RecoveryAnswerTransferCache
        extends BaseUserTransferCache<RecoveryAnswer, RecoveryAnswerTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of RecoveryAnswerTransferCache */
    public RecoveryAnswerTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);

        setIncludeEntityInstance(true);
    }
    
    public RecoveryAnswerTransfer getRecoveryAnswerTransfer(RecoveryAnswer recoveryAnswer) {
        RecoveryAnswerTransfer recoveryAnswerTransfer = get(recoveryAnswer);
        
        if(recoveryAnswerTransfer == null) {
            RecoveryAnswerDetail recoveryAnswerDetail = recoveryAnswer.getLastDetail();
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, recoveryAnswerDetail.getParty());
            RecoveryQuestionTransfer recoveryQuestion = userControl.getRecoveryQuestionTransfer(userVisit, recoveryAnswerDetail.getRecoveryQuestion());
            String answer = recoveryAnswerDetail.getAnswer();
            
            recoveryAnswerTransfer = new RecoveryAnswerTransfer(party, recoveryQuestion, answer);
            put(recoveryAnswer, recoveryAnswerTransfer);
        }
        
        return recoveryAnswerTransfer;
    }
    
}
