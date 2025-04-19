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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateProfileForm;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
                new FieldDefinition("Pronunciation", FieldType.STRING, false, 1L, 200L),
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Pronouns", FieldType.STRING, false, 1L, 50L),
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
                new FieldDefinition("Pronunciation", FieldType.STRING, false, 1L, 200L),
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Pronouns", FieldType.STRING, false, 1L, 50L),
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
        var partyTypeName = getPartyTypeName();

        return partyTypeName == null || partyTypeName.equals(PartyTypes.CUSTOMER.name()) ? customerFormFieldDefinitions : otherFormFieldDefinitions;
    }
    
    @Override
    protected BaseResult execute() {
        var bioMimeTypeName = form.getBioMimeTypeName();
        var bio = form.getBio();
        var bioParameterCount = (bioMimeTypeName == null ? 0 : 1) + (bio == null ? 0 : 1);
        var signatureMimeTypeName = form.getSignatureMimeTypeName();
        var signature = form.getSignature();
        var signatureParameterCount = (signatureMimeTypeName == null ? 0 : 1) + (signature == null ? 0 : 1);
        
        if((bioParameterCount == 0 || bioParameterCount == 2) && (signatureParameterCount == 0 || signatureParameterCount == 2)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyTypeName = getPartyTypeName();
            var partyName = partyTypeName.equals(PartyTypes.CUSTOMER.name())? null: form.getPartyName();
            var party = partyName == null? null: partyControl.getPartyByName(partyName);
            
            if(partyName == null || party != null) {
                var nickname = form.getNickname();
                var profile = nickname == null? null: partyControl.getProfileByNickname(nickname);
                
                if(party == null) {
                    party = getParty();
                }
                
                if(profile == null) {
                    var iconControl = Session.getModelController(IconControl.class);
                    var iconName = form.getIconName();
                    var icon = iconName == null? null: iconControl.getIconByName(iconName);
                    
                    if(iconName == null || icon != null) {
                        if(icon != null) {
                            var iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_PROFILE);
                            var iconUsage = iconControl.getIconUsage(iconUsageType, icon);
                            
                            if(iconUsage == null) {
                                addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            var genderName = form.getGenderName();
                            var gender = genderName == null? null: partyControl.getGenderByName(genderName);
                            
                            if(genderName == null || gender != null) {
                                var birthdayFormatName = form.getBirthdayFormatName();
                                var birthdayFormat = birthdayFormatName == null? null: partyControl.getBirthdayFormatByName(birthdayFormatName);

                                if(birthdayFormat != null) {
                                    var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                                    var bioMimeType = bioMimeTypeName == null? null: mimeTypeControl.getMimeTypeByName(bioMimeTypeName);

                                    if(bioMimeTypeName == null || bioMimeType != null) {
                                        var signatureMimeType = signatureMimeTypeName == null? null: mimeTypeControl.getMimeTypeByName(signatureMimeTypeName);

                                        if(signatureMimeTypeName == null || signatureMimeType != null) {
                                            var pronunciation = form.getPronunciation();
                                            var pronouns = form.getPronouns();
                                            var occupation = form.getOccupation();
                                            var hobbies = form.getHobbies();
                                            var location = form.getLocation();
                                            var rawBirthday = form.getBirthday();
                                            var birthday = rawBirthday == null? null: Integer.valueOf(rawBirthday);

                                            partyControl.createProfile(party, nickname, icon, pronunciation, gender, pronouns,
                                                    birthday, birthdayFormat, occupation, hobbies, location, bioMimeType, bio,
                                                    signatureMimeType, signature, getPartyPK());
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
