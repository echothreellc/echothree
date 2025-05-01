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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.party.common.transfer.MoodTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Mood;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class MoodTransferCache
        extends BasePartyTransferCache<Mood, MoodTransfer> {
    
    IconControl iconControl = Session.getModelController(IconControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of MoodTransferCache */
    public MoodTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    @Override
    public MoodTransfer getTransfer(Mood mood) {
        var moodTransfer = get(mood);
        
        if(moodTransfer == null) {
            var moodDetail = mood.getLastDetail();
            var moodName = moodDetail.getMoodName();
            var icon = moodDetail.getIcon();
            var iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            var isDefault = moodDetail.getIsDefault();
            var sortOrder = moodDetail.getSortOrder();
            var description = partyControl.getBestMoodDescription(mood, getLanguage());
            
            moodTransfer = new MoodTransfer(moodName, iconTransfer, isDefault, sortOrder, description);
            put(mood, moodTransfer);
        }
        
        return moodTransfer;
    }
    
}
