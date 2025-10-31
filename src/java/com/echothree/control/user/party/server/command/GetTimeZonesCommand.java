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

import com.echothree.control.user.party.common.form.GetTimeZonesForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetTimeZonesCommand
        extends BaseMultipleEntitiesCommand<TimeZone, GetTimeZonesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetTimeZonesCommand */
    public GetTimeZonesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<TimeZone> getEntities() {
        var partyControl = Session.getModelController(PartyControl.class);
        
        return partyControl.getTimeZones();
    }
    
    @Override
    protected BaseResult getResult(Collection<TimeZone> entities) {
        var result = PartyResultFactory.getGetTimeZonesResult();
        var partyControl = Session.getModelController(PartyControl.class);
        
        result.setTimeZones(partyControl.getTimeZoneTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
