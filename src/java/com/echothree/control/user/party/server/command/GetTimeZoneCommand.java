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

import com.echothree.control.user.party.common.form.GetTimeZoneForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.TimeZoneLogic;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTimeZoneCommand
        extends BaseSingleEntityCommand<TimeZone, GetTimeZoneForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetTimeZoneCommand */
    public GetTimeZoneCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected TimeZone getEntity() {
        var partyControl = Session.getModelController(PartyControl.class);
        TimeZone timeZone = null;
        var timeZoneName = form.getJavaTimeZoneName();
        var parameterCount = (timeZoneName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        switch(parameterCount) {
            case 0:
                timeZone = partyControl.getDefaultTimeZone();
                break;
            case 1:
                if(timeZoneName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.TimeZone.name());
                    
                    if(!hasExecutionErrors()) {
                        timeZone = partyControl.getTimeZoneByEntityInstance(entityInstance);
                    }
                } else {
                    timeZone = TimeZoneLogic.getInstance().getTimeZoneByName(this, timeZoneName);
                }
                break;
            default:
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        if(timeZone != null) {
            sendEvent(timeZone.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }
        
        return timeZone;
    }
    
    @Override
    protected BaseResult getResult(TimeZone timeZone) {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getGetTimeZoneResult();

        if(timeZone != null) {
            result.setTimeZone(partyControl.getTimeZoneTransfer(getUserVisit(), timeZone));
        }

        return result;
    }
    
}
