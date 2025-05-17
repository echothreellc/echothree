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

import com.echothree.control.user.party.common.form.GetTimeZoneDescriptionsForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
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

public class GetTimeZoneDescriptionsCommand
        extends BaseSimpleCommand<GetTimeZoneDescriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetTimeZoneDescriptionsCommand */
    public GetTimeZoneDescriptionsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getGetTimeZoneDescriptionsResult();
        var javaTimeZoneName = form.getJavaTimeZoneName();
        var timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);
        
        if(timeZone != null) {
            result.setTimeZone(partyControl.getTimeZoneTransfer(getUserVisit(), timeZone));
            result.setTimeZoneDescriptions(partyControl.getTimeZoneDescriptionTransfers(getUserVisit(), timeZone));
        } else {
            addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), javaTimeZoneName);
        }
        
        return result;
    }
    
}
