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

import com.echothree.control.user.party.common.form.GetDateTimeFormatForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.DateTimeFormatLogic;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetDateTimeFormatCommand
        extends BaseSingleEntityCommand<DateTimeFormat, GetDateTimeFormatForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetDateTimeFormatCommand */
    public GetDateTimeFormatCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected DateTimeFormat getEntity() {
        var partyControl = Session.getModelController(PartyControl.class);
        DateTimeFormat dateTimeFormat = null;
        var dateTimeFormatName = form.getDateTimeFormatName();
        var parameterCount = (dateTimeFormatName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        switch(parameterCount) {
            case 0:
                dateTimeFormat = partyControl.getDefaultDateTimeFormat();
                break;
            case 1:
                if(dateTimeFormatName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.DateTimeFormat.name());
                    
                    if(!hasExecutionErrors()) {
                        dateTimeFormat = partyControl.getDateTimeFormatByEntityInstance(entityInstance);
                    }
                } else {
                    dateTimeFormat = DateTimeFormatLogic.getInstance().getDateTimeFormatByName(this, dateTimeFormatName);
                }
                break;
            default:
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                break;
        }
        
        if(dateTimeFormat != null) {
            sendEvent(dateTimeFormat.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return dateTimeFormat;
    }
    
    @Override
    protected BaseResult getResult(DateTimeFormat dateTimeFormat) {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getGetDateTimeFormatResult();

        if(dateTimeFormat != null) {
            result.setDateTimeFormat(partyControl.getDateTimeFormatTransfer(getUserVisit(), dateTimeFormat));
        }

        return result;
    }
    
}
