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

package com.echothree.model.control.party.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ProfileTransfer
        extends BaseTransfer {

    private String nickname;
    private IconTransfer icon;
    private String pronunciation;
    private GenderTransfer gender;
    private String pronouns;
    private String birthday;
    private Integer unformattedBirthday;
    private BirthdayFormatTransfer birthdayFormat;
    private String occupation;
    private String hobbies;
    private String location;
    private MimeTypeTransfer bioMimeType;
    private String bio;
    private MimeTypeTransfer signatureMimeType;
    private String signature;
    
    /** Creates a new instance of ProfileTransfer */
    public ProfileTransfer(String nickname, IconTransfer icon, String pronunciation, GenderTransfer gender, String pronouns,
            String birthday, Integer unformattedBirthday, BirthdayFormatTransfer birthdayFormat, String occupation, String hobbies,
            String location, MimeTypeTransfer bioMimeType, String bio, MimeTypeTransfer signatureMimeType, String signature) {
        this.nickname = nickname;
        this.icon = icon;
        this.pronunciation = pronunciation;
        this.gender = gender;
        this.pronouns = pronouns;
        this.unformattedBirthday = unformattedBirthday;
        this.birthday = birthday;
        this.birthdayFormat = birthdayFormat;
        this.occupation = occupation;
        this.hobbies = hobbies;
        this.location = location;
        this.bioMimeType = bioMimeType;
        this.bio = bio;
        this.signatureMimeType = signatureMimeType;
        this.signature = signature;
    }

    /**
     * Returns the nickname.
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname.
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the icon.
     * @return the icon
     */
    public IconTransfer getIcon() {
        return icon;
    }

    /**
     * Sets the icon.
     * @param icon the icon to set
     */
    public void setIcon(IconTransfer icon) {
        this.icon = icon;
    }

    /**
     * Returns the pronunciation.
     * @return the pronunciation
     */
    public String getPronunciation() {
        return pronunciation;
    }

    /**
     * Sets the pronunciation.
     * @param pronunciation the pronunciation to set
     */
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    /**
     * Returns the gender.
     * @return the gender
     */
    public GenderTransfer getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     * @param gender the gender to set
     */
    public void setGender(GenderTransfer gender) {
        this.gender = gender;
    }

    /**
     * Returns the pronouns.
     * @return the pronouns
     */
    public String getPronouns() {
        return pronouns;
    }

    /**
     * Sets the pronouns.
     * @param pronouns the pronouns to set
     */
    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    /**
     * Returns the birthday.
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday.
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the unformattedBirthday.
     * @return the unformattedBirthday
     */
    public Integer getUnformattedBirthday() {
        return unformattedBirthday;
    }

    /**
     * Sets the unformattedBirthday.
     * @param unformattedBirthday the unformattedBirthday to set
     */
    public void setUnformattedBirthday(Integer unformattedBirthday) {
        this.unformattedBirthday = unformattedBirthday;
    }

    /**
     * Returns the birthdayFormat.
     * @return the birthdayFormat
     */
    public BirthdayFormatTransfer getBirthdayFormat() {
        return birthdayFormat;
    }

    /**
     * Sets the birthdayFormat.
     * @param birthdayFormat the birthdayFormat to set
     */
    public void setBirthdayFormat(BirthdayFormatTransfer birthdayFormat) {
        this.birthdayFormat = birthdayFormat;
    }

    /**
     * Returns the occupation.
     * @return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Sets the occupation.
     * @param occupation the occupation to set
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Returns the hobbies.
     * @return the hobbies
     */
    public String getHobbies() {
        return hobbies;
    }

    /**
     * Sets the hobbies.
     * @param hobbies the hobbies to set
     */
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    /**
     * Returns the location.
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the bioMimeType.
     * @return the bioMimeType
     */
    public MimeTypeTransfer getBioMimeType() {
        return bioMimeType;
    }

    /**
     * Sets the bioMimeType.
     * @param bioMimeType the bioMimeType to set
     */
    public void setBioMimeType(MimeTypeTransfer bioMimeType) {
        this.bioMimeType = bioMimeType;
    }

    /**
     * Returns the bio.
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the bio.
     * @param bio the bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Returns the signatureMimeType.
     * @return the signatureMimeType
     */
    public MimeTypeTransfer getSignatureMimeType() {
        return signatureMimeType;
    }

    /**
     * Sets the signatureMimeType.
     * @param signatureMimeType the signatureMimeType to set
     */
    public void setSignatureMimeType(MimeTypeTransfer signatureMimeType) {
        this.signatureMimeType = signatureMimeType;
    }

    /**
     * Returns the signature.
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the signature.
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

}
