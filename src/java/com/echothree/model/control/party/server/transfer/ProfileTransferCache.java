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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.control.party.common.transfer.BirthdayFormatTransfer;
import com.echothree.model.control.party.common.transfer.GenderTransfer;
import com.echothree.model.control.party.common.transfer.MoodTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.model.data.party.server.entity.Mood;
import com.echothree.model.data.party.server.entity.Profile;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;

public class ProfileTransferCache
        extends BasePartyTransferCache<Profile, ProfileTransfer> {
    
    CoreControl coreControl;
    IconControl iconControl;
    
    /** Creates a new instance of ProfileTransferCache */
    public ProfileTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        iconControl = (IconControl)Session.getModelController(IconControl.class);
    }
    
    public ProfileTransfer getProfileTransfer(Profile profile) {
        ProfileTransfer profileTransfer = get(profile);
        
        if(profileTransfer == null) {
            String nickname = profile.getNickname();
            Icon icon = profile.getIcon();
            IconTransfer iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            Gender gender = profile.getGender();
            GenderTransfer genderTransfer = gender == null? null: partyControl.getGenderTransfer(userVisit, gender);
            Mood mood = profile.getMood();
            MoodTransfer moodTransfer = mood == null? null: partyControl.getMoodTransfer(userVisit, mood);
            Integer unformattedBirthday = profile.getBirthday();
            BirthdayFormatTransfer birthdayFormat = partyControl.getBirthdayFormatTransfer(userVisit, profile.getBirthdayFormat());
            String birthday = DateUtils.getInstance().formatDate(userVisit, unformattedBirthday);
            String occupation = profile.getOccupation();
            String hobbies = profile.getHobbies();
            String location = profile.getLocation();
            MimeType bioMimeType = profile.getBioMimeType();
            MimeTypeTransfer bioMimeTypeTransfer = bioMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, bioMimeType);
            String bio = profile.getBio();
            MimeType signatureMimeType = profile.getSignatureMimeType();
            MimeTypeTransfer signatureMimeTypeTransfer = signatureMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, signatureMimeType);
            String signature = profile.getSignature();
            
            profileTransfer = new ProfileTransfer(nickname,  iconTransfer, genderTransfer, moodTransfer, birthday, unformattedBirthday, birthdayFormat,
                    occupation, hobbies, location, bioMimeTypeTransfer, bio, signatureMimeTypeTransfer, signature);
            put(profile, profileTransfer);
        }
        
        return profileTransfer;
    }
    
}
