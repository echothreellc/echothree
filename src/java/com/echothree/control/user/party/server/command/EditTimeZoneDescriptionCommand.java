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

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.TimeZoneDescriptionEdit;
import com.echothree.control.user.party.common.form.EditTimeZoneDescriptionForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.TimeZoneDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditTimeZoneDescriptionCommand
        extends BaseEditCommand<TimeZoneDescriptionSpec, TimeZoneDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditTimeZoneDescriptionCommand */
    public EditTimeZoneDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditTimeZoneDescriptionResult();
        var javaTimeZoneName = spec.getJavaTimeZoneName();
        var timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);
        
        if(timeZone != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var timeZoneDescription = partyControl.getTimeZoneDescription(timeZone, language);
                    
                    if(timeZoneDescription != null) {
                        result.setTimeZoneDescription(partyControl.getTimeZoneDescriptionTransfer(getUserVisit(), timeZoneDescription));
                        
                        if(lockEntity(timeZone)) {
                            var edit = PartyEditFactory.getTimeZoneDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(timeZoneDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(timeZone));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTimeZoneDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var timeZoneDescriptionValue = partyControl.getTimeZoneDescriptionValueForUpdate(timeZone, language);
                    
                    if(timeZoneDescriptionValue != null) {
                        if(lockEntityForUpdate(timeZone)) {
                            try {
                                var description = edit.getDescription();
                                
                                timeZoneDescriptionValue.setDescription(description);
                                
                                partyControl.updateTimeZoneDescriptionFromValue(timeZoneDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(timeZone);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTimeZoneDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), javaTimeZoneName);
        }
        
        return result;
    }
    
}
