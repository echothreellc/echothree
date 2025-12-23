// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.transfer.MoodDescriptionTransfer;
import com.echothree.model.data.party.server.entity.MoodDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MoodDescriptionTransferCache
        extends BasePartyDescriptionTransferCache<MoodDescription, MoodDescriptionTransfer> {
    
    /** Creates a new instance of MoodDescriptionTransferCache */
    protected MoodDescriptionTransferCache() {
        super();
    }

    @Override
    public MoodDescriptionTransfer getTransfer(UserVisit userVisit, MoodDescription moodDescription) {
        var moodDescriptionTransfer = get(moodDescription);
        
        if(moodDescriptionTransfer == null) {
            var moodTransfer = partyControl.getMoodTransfer(userVisit, moodDescription.getMood());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, moodDescription.getLanguage());
            
            moodDescriptionTransfer = new MoodDescriptionTransfer(languageTransfer, moodTransfer, moodDescription.getDescription());
            put(userVisit, moodDescription, moodDescriptionTransfer);
        }
        
        return moodDescriptionTransfer;
    }
    
}
