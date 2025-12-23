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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetEventsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.factory.EventFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetEventsCommand
        extends BaseMultipleEntitiesCommand<Event, GetEventsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Event.name(), SecurityRoles.List.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("CreatedByEntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("CreatedByUuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEventsCommand */
    public GetEventsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    EntityInstance entityInstance;
    EntityInstance createdBy;
    Long eventCount;

    @Override
    protected Collection<Event> getEntities() {
        var eventControl = Session.getModelController(EventControl.class);
        var entityRef = form.getEntityRef();
        var uuid = form.getUuid();
        var createdByEntityRef = form.getCreatedByEntityRef();
        var createdByUuid = form.getCreatedByUuid();
        var parameterCount = (entityRef == null ? 0 : 1) + (uuid == null ? 0 : 1)
                + (createdByEntityRef == null ? 0 : 1) + (createdByUuid == null ? 0 : 1);
        Collection<Event> entities = null;

        if(parameterCount == 1) {
            if(entityRef != null || uuid != null) {
                entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, entityRef, uuid, null);

                if(!hasExecutionErrors()) {
                    if(session.hasLimit(EventFactory.class)) {
                        eventCount = eventControl.countEventsByEntityInstance(entityInstance);
                    }

                    entities = eventControl.getEventsByEntityInstance(entityInstance);
                }
            } else {
                createdBy = EntityInstanceLogic.getInstance().getEntityInstance(this, createdByEntityRef, createdByUuid, null);

                if(!hasExecutionErrors()) {
                    if(session.hasLimit(EventFactory.class)) {
                        eventCount = eventControl.countEventsByCreatedBy(createdBy);
                    }

                    entities = eventControl.getEventsByCreatedBy(createdBy);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<Event> entities) {
        var result = CoreResultFactory.getGetEventsResult();

        if(entities != null) {
            var eventControl = Session.getModelController(EventControl.class);
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var userVisit = getUserVisit();

            result.setEventCount(eventCount);

            if(entityInstance != null) {
                result.setEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityInstance, false, false, false, false));
            }

            if(createdBy != null) {
                result.setCreatedByEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, createdBy, false, false, false, false));
            }

            result.setEvents(eventControl.getEventTransfers(userVisit, entities));
        }

        return result;
    }
    
}
