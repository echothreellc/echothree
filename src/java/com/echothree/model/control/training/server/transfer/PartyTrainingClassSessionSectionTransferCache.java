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

import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionSectionTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionSection;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyTrainingClassSessionSectionTransferCache
        extends BaseTrainingTransferCache<PartyTrainingClassSessionSection, PartyTrainingClassSessionSectionTransfer> {

    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);

    /** Creates a new instance of PartyTrainingClassSessionSectionTransferCache */
    public PartyTrainingClassSessionSectionTransferCache() {
        super();
    }
    
    public PartyTrainingClassSessionSectionTransfer getPartyTrainingClassSessionSectionTransfer(UserVisit userVisit, PartyTrainingClassSessionSection partyTrainingClassSessionSection) {
        var partyTrainingClassSessionSectionTransfer = get(partyTrainingClassSessionSection);
        
        if(partyTrainingClassSessionSectionTransfer == null) {
            var partyTrainingClassSession = trainingControl.getPartyTrainingClassSessionTransfer(userVisit, partyTrainingClassSessionSection.getPartyTrainingClassSession());
            var partyTrainingClassSessionSectionSequence = partyTrainingClassSessionSection.getPartyTrainingClassSessionSectionSequence();
            var trainingClassSection = trainingControl.getTrainingClassSectionTransfer(userVisit, partyTrainingClassSessionSection.getTrainingClassSection());
            var unformattedReadingStartTime = partyTrainingClassSessionSection.getReadingStartTime();
            var readingStartTime = formatTypicalDateTime(userVisit, unformattedReadingStartTime);
            var unformattedReadingEndTime = partyTrainingClassSessionSection.getReadingEndTime();
            var readingEndTime = formatTypicalDateTime(userVisit, unformattedReadingEndTime);


            partyTrainingClassSessionSectionTransfer = new PartyTrainingClassSessionSectionTransfer(partyTrainingClassSession, partyTrainingClassSessionSectionSequence,
                    trainingClassSection, unformattedReadingStartTime, readingStartTime, unformattedReadingEndTime, readingEndTime);
            put(userVisit, partyTrainingClassSessionSection, partyTrainingClassSessionSectionTransfer);
        }
        
        return partyTrainingClassSessionSectionTransfer;
    }
    
}
