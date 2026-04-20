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
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetEventsCommand
        extends BasePaginatedMultipleEntitiesCommand<Event, GetEventsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.Event.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("CreatedByEntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("CreatedByUuid", FieldType.UUID, false, null, null)
        );
    }
    
    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    /** Creates a new instance of GetEventsCommand */
    public GetEventsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private EntityInstance entityInstance;
    private EntityInstance createdBy;

    @Override
    protected void handleForm() {
        var entityRef = form.getEntityRef();
        var uuid = form.getUuid();
        var createdByEntityRef = form.getCreatedByEntityRef();
        var createdByUuid = form.getCreatedByUuid();
        var parameterCount = (entityRef == null ? 0 : 1) + (uuid == null ? 0 : 1)
                + (createdByEntityRef == null ? 0 : 1) + (createdByUuid == null ? 0 : 1);

        if(parameterCount == 1) {
            if(entityRef != null || uuid != null) {
                entityInstance = entityInstanceLogic.getEntityInstance(this, entityRef, uuid, null);
            } else {
                createdBy = entityInstanceLogic.getEntityInstance(this, createdByEntityRef, createdByUuid, null);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(entityInstance != null) {
                total = eventControl.countEventsByEntityInstance(entityInstance);
            } else if(createdBy != null) {
                total = eventControl.countEventsByCreatedBy(createdBy);
            }
        }

        return total;
    }

    @Override
    protected Collection<Event> getEntities() {
        Collection<Event> entities = null;

        if(!hasExecutionErrors()) {
            if(entityInstance != null) {
                entities = eventControl.getEventsByEntityInstance(entityInstance);
            } else if(createdBy != null) {
                entities = eventControl.getEventsByCreatedBy(createdBy);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<Event> entities) {
        var result = CoreResultFactory.getGetEventsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(EventFactory.class)) {
                result.setEventCount(getTotalEntities());
            }

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
