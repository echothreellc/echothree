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

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Profile;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;

public class ProfileTransferCache
        extends BasePartyTransferCache<Profile, ProfileTransfer> {

    IconControl iconControl = Session.getModelController(IconControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    
    /** Creates a new instance of ProfileTransferCache */
    public ProfileTransferCache(final UserVisit userVisit, final PartyControl partyControl) {
        super(userVisit, partyControl);
    }
    
    public ProfileTransfer getProfileTransfer(final Profile profile) {
        var profileTransfer = get(profile);
        
        if(profileTransfer == null) {
            var nickname = profile.getNickname();
            var icon = profile.getIcon();
            var iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            var pronunciation = profile.getPronunciation();
            var gender = profile.getGender();
            var genderTransfer = gender == null? null: partyControl.getGenderTransfer(userVisit, gender);
            var pronouns = profile.getPronouns();
            var unformattedBirthday = profile.getBirthday();
            var birthdayFormat = partyControl.getBirthdayFormatTransfer(userVisit, profile.getBirthdayFormat());
            var birthday = DateUtils.getInstance().formatDate(userVisit, unformattedBirthday);
            var occupation = profile.getOccupation();
            var hobbies = profile.getHobbies();
            var location = profile.getLocation();
            var bioMimeType = profile.getBioMimeType();
            var bioMimeTypeTransfer = bioMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, bioMimeType);
            var bio = profile.getBio();
            var signatureMimeType = profile.getSignatureMimeType();
            var signatureMimeTypeTransfer = signatureMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, signatureMimeType);
            var signature = profile.getSignature();
            
            profileTransfer = new ProfileTransfer(nickname, iconTransfer, pronunciation, genderTransfer, pronouns, birthday,
                    unformattedBirthday, birthdayFormat, occupation, hobbies, location, bioMimeTypeTransfer, bio,
                    signatureMimeTypeTransfer, signature);
            put(profile, profileTransfer);
        }
        
        return profileTransfer;
    }
    
}
