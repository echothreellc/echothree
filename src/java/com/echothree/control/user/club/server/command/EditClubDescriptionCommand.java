// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.edit.ClubDescriptionEdit;
import com.echothree.control.user.club.common.edit.ClubEditFactory;
import com.echothree.control.user.club.common.form.EditClubDescriptionForm;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.control.user.club.common.result.EditClubDescriptionResult;
import com.echothree.control.user.club.common.spec.ClubDescriptionSpec;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.entity.ClubDescription;
import com.echothree.model.data.club.server.value.ClubDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditClubDescriptionCommand
        extends BaseEditCommand<ClubDescriptionSpec, ClubDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditClubDescriptionCommand */
    public EditClubDescriptionCommand(UserVisitPK userVisitPK, EditClubDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var clubControl = Session.getModelController(ClubControl.class);
        EditClubDescriptionResult result = ClubResultFactory.getEditClubDescriptionResult();
        String clubName = spec.getClubName();
        Club club = clubControl.getClubByName(clubName);
        
        if(club != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    ClubDescription clubDescription = clubControl.getClubDescription(club, language);
                    
                    if(clubDescription != null) {
                        result.setClubDescription(clubControl.getClubDescriptionTransfer(getUserVisit(), clubDescription));
                        
                        if(lockEntity(club)) {
                            ClubDescriptionEdit edit = ClubEditFactory.getClubDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(clubDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(club));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownClubDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    ClubDescriptionValue clubDescriptionValue = clubControl.getClubDescriptionValueForUpdate(club, language);
                    
                    if(clubDescriptionValue != null) {
                        if(lockEntityForUpdate(club)) {
                            try {
                                String description = edit.getDescription();
                                
                                clubDescriptionValue.setDescription(description);
                                
                                clubControl.updateClubDescriptionFromValue(clubDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(club);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownClubDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownClubName.name(), clubName);
        }
        
        return result;
    }
    
}
