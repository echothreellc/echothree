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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.remote.edit.PartyEditFactory;
import com.echothree.control.user.party.remote.edit.ProfileEdit;
import com.echothree.control.user.party.remote.form.EditProfileForm;
import com.echothree.control.user.party.remote.result.EditProfileResult;
import com.echothree.control.user.party.remote.result.PartyResultFactory;
import com.echothree.control.user.party.remote.spec.PartySpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.icon.server.entity.IconUsage;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.party.server.entity.BirthdayFormat;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.model.data.party.server.entity.Mood;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.Profile;
import com.echothree.model.data.party.server.value.ProfileValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditProfileCommand
        extends BaseAbstractEditCommand<PartySpec, ProfileEdit, EditProfileResult, Profile, Party> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> customerEditFieldDefinitions;
    private final static List<FieldDefinition> otherEditFieldDefinitions;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
                ));
        
        customerEditFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Nickname", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MoodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Birthday", FieldType.DATE, false, null, null),
                new FieldDefinition("BirthdayFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Occupation", FieldType.STRING, false, 1L, 200L),
                new FieldDefinition("Hobbies", FieldType.STRING, false, 1L, 200L),
                new FieldDefinition("Location", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("BioMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Bio", FieldType.STRING, false, 1L, 512L),
                new FieldDefinition("SignatureMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Signature", FieldType.STRING, false, 1L, 512L)
                ));
        
        otherEditFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Nickname", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MoodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Birthday", FieldType.DATE, false, null, null),
                new FieldDefinition("BirthdayFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Occupation", FieldType.STRING, false, 1L, 200L),
                new FieldDefinition("Hobbies", FieldType.STRING, false, 1L, 200L),
                new FieldDefinition("Location", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("BioMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Bio", FieldType.STRING, false, 1L, 512L),
                new FieldDefinition("SignatureMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Signature", FieldType.STRING, false, 1L, 512L)
                ));
    }
    
    /** Creates a new instance of EditProfileCommand */
    public EditProfileCommand(UserVisitPK userVisitPK, EditProfileForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, null);
    }
    
    @Override
    protected List<FieldDefinition> getEditFieldDefinitions() {
        String partyTypeName = getPartyTypeName();
        
        return partyTypeName == null || partyTypeName.equals(PartyConstants.PartyType_CUSTOMER) ? customerEditFieldDefinitions : otherEditFieldDefinitions;
    }
    
    @Override
    public EditProfileResult getResult() {
        return PartyResultFactory.getEditProfileResult();
    }

    @Override
    public ProfileEdit getEdit() {
        return PartyEditFactory.getProfileEdit();
    }

    @Override
    public Profile getEntity(EditProfileResult result) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        Profile profile = null;
        String partyTypeName = getPartyTypeName();
        String partyName = partyTypeName.equals(PartyConstants.PartyType_CUSTOMER) ? null : spec.getPartyName();
        Party party = partyName == null ? null : partyControl.getPartyByName(partyName);

        if(partyName == null || party != null) {
            if(party == null) {
                party = getParty();
            }

            if(party == null) {
                addExecutionError(ExecutionErrors.PartyRequired.name());
            } else {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    profile = partyControl.getProfile(party);
                } else { // EditMode.UPDATE
                    profile = partyControl.getProfileForUpdate(party);
                }

                if(profile == null) {
                    addExecutionError(ExecutionErrors.UnknownProfile.name(), partyName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return profile;
    }

    @Override
    public Party getLockEntity(Profile profile) {
        return profile.getParty();
    }

    @Override
    public void fillInResult(EditProfileResult result, Profile profile) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

        result.setParty(partyControl.getPartyTransfer(getUserVisit(), profile.getParty()));
    }

    Icon icon;
    Gender gender;
    Mood mood;
    MimeType bioMimeType;
    MimeType signatureMimeType;

    @Override
    public void doLock(ProfileEdit edit, Profile profile) {
        icon = profile.getIcon();
        gender = profile.getGender();
        mood = profile.getMood();

        bioMimeType = profile.getBioMimeType();
        signatureMimeType = profile.getSignatureMimeType();

        edit.setNickname(profile.getNickname());
        edit.setIconName(icon == null? null: icon.getLastDetail().getIconName());
        edit.setGenderName(gender == null? null: gender.getLastDetail().getGenderName());
        edit.setMoodName(mood == null? null: mood.getLastDetail().getMoodName());
        edit.setBirthday(DateUtils.getInstance().formatDate(getUserVisit(), profile.getBirthday()));
        edit.setBirthdayFormatName(profile.getBirthdayFormat().getLastDetail().getBirthdayFormatName());
        edit.setOccupation(profile.getOccupation());
        edit.setHobbies(profile.getHobbies());
        edit.setLocation(profile.getLocation());
        edit.setBioMimeTypeName(bioMimeType == null ? null : bioMimeType.getLastDetail().getMimeTypeName());
        edit.setBio(profile.getBio());
        edit.setSignatureMimeTypeName(signatureMimeType == null ? null : signatureMimeType.getLastDetail().getMimeTypeName());
        edit.setSignature(profile.getSignature());
    }

    BirthdayFormat birthdayFormat;

    @Override
    protected void canUpdate(Profile profile) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        MimeTypeLogic mimeTypeLogic = MimeTypeLogic.getInstance();
        String bioMimeTypeName = edit.getBioMimeTypeName();
        String bio = edit.getBio();
        String signatureMimeTypeName = edit.getSignatureMimeTypeName();
        String signature = edit.getSignature();

        bioMimeType = mimeTypeLogic.checkMimeType(this, bioMimeTypeName, bio, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredBioMimeTypeName.name(), ExecutionErrors.MissingRequiredBio.name(),
                ExecutionErrors.UnknownBioMimeTypeName.name(), ExecutionErrors.UnknownBioMimeTypeUsage.name());

        signatureMimeType = mimeTypeLogic.checkMimeType(this, signatureMimeTypeName, signature, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredSignatureMimeTypeName.name(), ExecutionErrors.MissingRequiredSignature.name(),
                ExecutionErrors.UnknownSignatureMimeTypeName.name(), ExecutionErrors.UnknownSignatureMimeTypeUsage.name());
        
        String nickname = edit.getNickname();
        Profile duplicateProfile = nickname == null ? null : partyControl.getProfileByNickname(nickname);

        if(duplicateProfile == null || duplicateProfile.getPrimaryKey().equals(profile.getPrimaryKey())) {
            IconControl iconControl = (IconControl)Session.getModelController(IconControl.class);
            String iconName = edit.getIconName();

            icon = iconName == null? null: iconControl.getIconByName(iconName);

            if(iconName == null || icon != null) {
                if(icon != null) {
                    IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_PROFILE);
                    IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, icon);

                    if(iconUsage == null) {
                        addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    String genderName = edit.getGenderName();

                    gender = genderName == null? null: partyControl.getGenderByName(genderName);

                    if(genderName == null || gender != null) {
                        String moodName = edit.getMoodName();

                        mood = moodName == null? null: partyControl.getMoodByName(moodName);

                        if(moodName == null || mood != null) {
                            String birthdayFormatName = edit.getBirthdayFormatName();

                            birthdayFormat = birthdayFormatName == null ? null : partyControl.getBirthdayFormatByName(birthdayFormatName);

                            if(birthdayFormat == null) {
                                addExecutionError(ExecutionErrors.UnknownBirthdayFormatName.name(), birthdayFormatName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownMoodName.name(), moodName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGenderName.name(), genderName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownIconName.name(), iconName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateNickname.name(), nickname);
        }
    }

    @Override
    public void doUpdate(Profile profile) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        ProfileValue profileValue = partyControl.getProfileValue(profile);
        String nickname = edit.getNickname();
        String birthday = edit.getBirthday();

        profileValue.setNickname(nickname);
        profileValue.setIconPK(icon == null ? null : icon.getPrimaryKey());
        profileValue.setGenderPK(gender == null ? null : gender.getPrimaryKey());
        profileValue.setMoodPK(mood == null ? null : mood.getPrimaryKey());
        profileValue.setBirthday(birthday == null ? null : Integer.valueOf(birthday));
        profileValue.setBirthdayFormatPK(birthdayFormat.getPrimaryKey());
        profileValue.setOccupation(edit.getOccupation());
        profileValue.setHobbies(edit.getHobbies());
        profileValue.setLocation(edit.getLocation());
        profileValue.setBioMimeTypePK(bioMimeType == null ? null : bioMimeType.getPrimaryKey());
        profileValue.setBio(edit.getBio());
        profileValue.setSignatureMimeTypePK(signatureMimeType == null ? null : signatureMimeType.getPrimaryKey());
        profileValue.setSignature(edit.getSignature());

        partyControl.updateProfileFromValue(profileValue, getPartyPK());
    }
    
}
