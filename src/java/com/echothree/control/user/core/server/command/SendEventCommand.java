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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.SendEventForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EventTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SendEventCommand
        extends BaseSimpleCommand<SendEventForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Event.name(), SecurityRoles.Send.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EventTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of SendEventCommand */
    public SendEventCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);
        var eventType = EventTypeLogic.getInstance().getEventTypeByName(this, form.getEventTypeName());

        if(!hasExecutionErrors()) {
            var componentVendorName = entityInstance.getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName();

            if(!ComponentVendors.ECHO_THREE.name().equals(componentVendorName)) {
                var eventTypeName = eventType.getEventTypeName();
                var eventTypeEnum = EventTypes.valueOf(eventTypeName);

                switch(eventTypeEnum) {
                    case READ, MODIFY, TOUCH -> {
                        var coreControl = Session.getModelController(CoreControl.class);

                        coreControl.sendEvent(entityInstance, eventTypeEnum, (EntityInstance)null, null, getPartyPK());
                    }
                    default -> addExecutionError(ExecutionErrors.InvalidEventType.name(), eventTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidComponentVendor.name(), componentVendorName);
            }
        }

        return null;
    }

}
