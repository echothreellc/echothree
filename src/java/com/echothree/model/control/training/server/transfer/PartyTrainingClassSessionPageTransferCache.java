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

import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionPageTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionPage;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTrainingClassSessionPageTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSessionPage, PartyTrainingClassSessionPageTransfer> {

    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);

    /** Creates a new instance of PartyTrainingClassSessionPageTransferCache */
    protected PartyTrainingClassSessionPageTransferCache() {
        super();
    }
    
    public PartyTrainingClassSessionPageTransfer getPartyTrainingClassSessionPageTransfer(UserVisit userVisit, PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        var partyTrainingClassSessionPageTransfer = get(partyTrainingClassSessionPage);
        
        if(partyTrainingClassSessionPageTransfer == null) {
            var partyTrainingClassSession = trainingControl.getPartyTrainingClassSessionTransfer(userVisit, partyTrainingClassSessionPage.getPartyTrainingClassSession());
            var partyTrainingClassSessionPageSequence = partyTrainingClassSessionPage.getPartyTrainingClassSessionPageSequence();
            var trainingClassPage = trainingControl.getTrainingClassPageTransfer(userVisit, partyTrainingClassSessionPage.getTrainingClassPage());
            var unformattedReadingStartTime = partyTrainingClassSessionPage.getReadingStartTime();
            var readingStartTime = formatTypicalDateTime(userVisit, unformattedReadingStartTime);
            var unformattedReadingEndTime = partyTrainingClassSessionPage.getReadingEndTime();
            var readingEndTime = formatTypicalDateTime(userVisit, unformattedReadingEndTime);


            partyTrainingClassSessionPageTransfer = new PartyTrainingClassSessionPageTransfer(partyTrainingClassSession, partyTrainingClassSessionPageSequence,
                    trainingClassPage, unformattedReadingStartTime, readingStartTime, unformattedReadingEndTime, readingEndTime);
            put(userVisit, partyTrainingClassSessionPage, partyTrainingClassSessionPageTransfer);
        }
        
        return partyTrainingClassSessionPageTransfer;
    }
    
}
