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

import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionPageTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassPageTransfer;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionPage;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyTrainingClassSessionPageTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSessionPage, PartyTrainingClassSessionPageTransfer> {
    
    /** Creates a new instance of PartyTrainingClassSessionPageTransferCache */
    public PartyTrainingClassSessionPageTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
    }
    
    public PartyTrainingClassSessionPageTransfer getPartyTrainingClassSessionPageTransfer(PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        PartyTrainingClassSessionPageTransfer partyTrainingClassSessionPageTransfer = get(partyTrainingClassSessionPage);
        
        if(partyTrainingClassSessionPageTransfer == null) {
            PartyTrainingClassSessionTransfer partyTrainingClassSession = trainingControl.getPartyTrainingClassSessionTransfer(userVisit, partyTrainingClassSessionPage.getPartyTrainingClassSession());
            Integer partyTrainingClassSessionPageSequence = partyTrainingClassSessionPage.getPartyTrainingClassSessionPageSequence();
            TrainingClassPageTransfer trainingClassPage = trainingControl.getTrainingClassPageTransfer(userVisit, partyTrainingClassSessionPage.getTrainingClassPage());
            Long unformattedReadingStartTime = partyTrainingClassSessionPage.getReadingStartTime();
            String readingStartTime = formatTypicalDateTime(unformattedReadingStartTime);
            Long unformattedReadingEndTime = partyTrainingClassSessionPage.getReadingEndTime();
            String readingEndTime = formatTypicalDateTime(unformattedReadingEndTime);


            partyTrainingClassSessionPageTransfer = new PartyTrainingClassSessionPageTransfer(partyTrainingClassSession, partyTrainingClassSessionPageSequence,
                    trainingClassPage, unformattedReadingStartTime, readingStartTime, unformattedReadingEndTime, readingEndTime);
            put(partyTrainingClassSessionPage, partyTrainingClassSessionPageTransfer);
        }
        
        return partyTrainingClassSessionPageTransfer;
    }
    
}
