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

package com.echothree.control.user.party.common.edit;

import com.echothree.control.user.icon.common.spec.IconSpec;
import com.echothree.control.user.party.common.spec.BirthdayFormatSpec;
import com.echothree.control.user.party.common.spec.GenderSpec;
import com.echothree.util.common.form.BaseEdit;

public interface ProfileEdit
        extends BaseEdit, IconSpec, GenderSpec, BirthdayFormatSpec {
    
    String getNickname();
    void setNickname(String nickname);

    String getPronunciation();
    void setPronunciation(String pronunciation);

    String getPronouns();
    void setPronouns(String pronouns);

    String getBirthday();
    void setBirthday(String birthday);
    
    String getOccupation();
    void setOccupation(String occupation);
    
    String getHobbies();
    void setHobbies(String hobbies);
    
    String getLocation();
    void setLocation(String location);
    
    String getBioMimeTypeName();
    void setBioMimeTypeName(String bioMimeTypeName);
    
    String getBio();
    void setBio(String bio);
    
    String getSignatureMimeTypeName();
    void setSignatureMimeTypeName(String signatureMimeTypeName);
    
    String getSignature();
    void setSignature(String signature);
    
}
