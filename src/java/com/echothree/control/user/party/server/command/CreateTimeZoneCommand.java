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

import com.echothree.control.user.party.common.form.CreateTimeZoneForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.TimeZone;
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

public class CreateTimeZoneCommand
        extends BaseSimpleCommand<CreateTimeZoneForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null),
                new FieldDefinition("UnixTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateTimeZoneCommand */
    public CreateTimeZoneCommand(UserVisitPK userVisitPK, CreateTimeZoneForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String javaTimeZoneName = form.getJavaTimeZoneName();
        TimeZone timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);
        
        if(timeZone == null) {
            String unixTimeZoneName = form.getUnixTimeZoneName();
            
            timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);
            
            if(timeZone == null) {
                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                String description = form.getDescription();
                PartyPK partyPK = getPartyPK();
                
                timeZone = partyControl.createTimeZone(javaTimeZoneName, unixTimeZoneName, isDefault, sortOrder, partyPK);
                
                if(description != null) {
                    partyControl.createTimeZoneDescription(timeZone, getPreferredLanguage(), description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateUnixTimeZoneName.name(), unixTimeZoneName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateJavaTimeZoneName.name(), javaTimeZoneName);
        }
        
        return null;
    }
    
}
