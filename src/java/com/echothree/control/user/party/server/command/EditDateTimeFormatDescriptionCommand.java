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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.DateTimeFormatDescriptionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditDateTimeFormatDescriptionForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.DateTimeFormatDescriptionSpec;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditDateTimeFormatDescriptionCommand
        extends BaseEditCommand<DateTimeFormatDescriptionSpec, DateTimeFormatDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("DateTimeFormatName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditDateTimeFormatDescriptionCommand */
    public EditDateTimeFormatDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditDateTimeFormatDescriptionResult();
        var dateTimeFormatName = spec.getDateTimeFormatName();
        var dateTimeFormat = partyControl.getDateTimeFormatByName(dateTimeFormatName);
        
        if(dateTimeFormat != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var dateTimeFormatDescription = partyControl.getDateTimeFormatDescription(dateTimeFormat, language);
                    
                    if(dateTimeFormatDescription != null) {
                        result.setDateTimeFormatDescription(partyControl.getDateTimeFormatDescriptionTransfer(getUserVisit(), dateTimeFormatDescription));
                        
                        if(lockEntity(dateTimeFormat)) {
                            var edit = PartyEditFactory.getDateTimeFormatDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(dateTimeFormatDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(dateTimeFormat));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownDateTimeFormatDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var dateTimeFormatDescriptionValue = partyControl.getDateTimeFormatDescriptionValueForUpdate(dateTimeFormat, language);
                    
                    if(dateTimeFormatDescriptionValue != null) {
                        if(lockEntityForUpdate(dateTimeFormat)) {
                            try {
                                var description = edit.getDescription();
                                
                                dateTimeFormatDescriptionValue.setDescription(description);
                                
                                partyControl.updateDateTimeFormatDescriptionFromValue(dateTimeFormatDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(dateTimeFormat);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownDateTimeFormatDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), dateTimeFormatName);
        }
        
        return result;
    }
    
}
