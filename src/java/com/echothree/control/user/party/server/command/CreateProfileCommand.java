// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.CreateProfileForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.control.party.common.PartyTypes;
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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateProfileCommand
        extends BaseSimpleCommand<CreateProfileForm> {
    
    private final static List<FieldDefinition> customerFormFieldDefinitions;
    private final static List<FieldDefinition> otherFormFieldDefinitions;
    
    static {
        // If a Customer is creating their Profile, then Nickname is a required field. Otherwise, it, along with all the other
        // fields, are optional.
        customerFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
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
        
        otherFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
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
    
    /** Creates a new instance of CreateProfileCommand */
    public CreateProfileCommand(UserVisitPK userVisitPK, CreateProfileForm form) {
        super(userVisitPK, form, null, null, false);
    }
    
    @Override
    protected List<FieldDefinition> getFormFieldDefinitions() {
        String partyTypeName = getPartyTypeName();

        return partyTypeName == null || partyTypeName.equals(PartyTypes.CUSTOMER.name()) ? customerFormFieldDefinitions : otherFormFieldDefinitions;
    }
    
    @Override
    protected BaseResult execute() {
        String bioMimeTypeName = form.getBioMimeTypeName();
        String bio = form.getBio();
        int bioParameterCount = (bioMimeTypeName == null? 0: 1) + (bio == null? 0: 1);
        String signatureMimeTypeName = form.getSignatureMimeTypeName();
        String signature = form.getSignature();
        int signatureParameterCount = (signatureMimeTypeName == null? 0: 1) + (signature == null? 0: 1);
        
        if((bioParameterCount == 0 || bioParameterCount == 2) && (signatureParameterCount == 0 || signatureParameterCount == 2)) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String partyTypeName = getPartyTypeName();
            String partyName = partyTypeName.equals(PartyTypes.CUSTOMER.name())? null: form.getPartyName();
            Party party = partyName == null? null: partyControl.getPartyByName(partyName);
            
            if(partyName == null || party != null) {
                String nickname = form.getNickname();
                Profile profile = nickname == null? null: partyControl.getProfileByNickname(nickname);
                
                if(party == null) {
                    party = getParty();
                }
                
                if(profile == null) {
                    var iconControl = (IconControl)Session.getModelController(IconControl.class);
                    String iconName = form.getIconName();
                    Icon icon = iconName == null? null: iconControl.getIconByName(iconName);
                    
                    if(iconName == null || icon != null) {
                        if(icon != null) {
                            IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_PROFILE);
                            IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, icon);
                            
                            if(iconUsage == null) {
                                addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            String genderName = form.getGenderName();
                            Gender gender = genderName == null? null: partyControl.getGenderByName(genderName);
                            
                            if(genderName == null || gender != null) {
                                String moodName = form.getMoodName();
                                Mood mood = moodName == null? null: partyControl.getMoodByName(moodName);

                                if(moodName == null || mood != null) {
                                    String birthdayFormatName = form.getBirthdayFormatName();
                                    BirthdayFormat birthdayFormat = birthdayFormatName == null? null: partyControl.getBirthdayFormatByName(birthdayFormatName);

                                    if(birthdayFormat != null) {
                                        var coreControl = getCoreControl();
                                        MimeType bioMimeType = bioMimeTypeName == null? null: coreControl.getMimeTypeByName(bioMimeTypeName);

                                        if(bioMimeTypeName == null || bioMimeType != null) {
                                            MimeType signatureMimeType = signatureMimeTypeName == null? null: coreControl.getMimeTypeByName(signatureMimeTypeName);

                                            if(signatureMimeTypeName == null || signatureMimeType != null) {
                                                String occupation = form.getOccupation();
                                                String hobbies = form.getHobbies();
                                                String location = form.getLocation();
                                                String rawBirthday = form.getBirthday();
                                                Integer birthday = rawBirthday == null? null: Integer.valueOf(rawBirthday);

                                                partyControl.createProfile(party, nickname, icon, gender, mood, birthday, birthdayFormat,
                                                        occupation, hobbies, location, bioMimeType, bio, signatureMimeType, signature,
                                                        getPartyPK());
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownSignatureMimeTypeName.name(), signatureMimeTypeName);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownBioMimeTypeName.name(), bioMimeTypeName);
                                        }
                                    } else {
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
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
