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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.icon.remote.transfer.IconTransfer;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.control.party.remote.transfer.MoodTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.party.server.entity.Mood;
import com.echothree.model.data.party.server.entity.MoodDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class MoodTransferCache
        extends BasePartyTransferCache<Mood, MoodTransfer> {
    
    IconControl iconControl = (IconControl)Session.getModelController(IconControl.class);
    
    /** Creates a new instance of MoodTransferCache */
    public MoodTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public MoodTransfer getMoodTransfer(Mood mood) {
        MoodTransfer moodTransfer = get(mood);
        
        if(moodTransfer == null) {
            MoodDetail moodDetail = mood.getLastDetail();
            String moodName = moodDetail.getMoodName();
            Icon icon = moodDetail.getIcon();
            IconTransfer iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            Boolean isDefault = moodDetail.getIsDefault();
            Integer sortOrder = moodDetail.getSortOrder();
            String description = partyControl.getBestMoodDescription(mood, getLanguage());
            
            moodTransfer = new MoodTransfer(moodName, iconTransfer, isDefault, sortOrder, description);
            put(mood, moodTransfer);
        }
        
        return moodTransfer;
    }
    
}
