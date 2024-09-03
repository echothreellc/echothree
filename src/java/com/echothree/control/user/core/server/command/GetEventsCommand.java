// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.control.user.core.common.result.GetEventsResult;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.factory.EventFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("CreatedByEntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("CreatedByKey", FieldType.KEY, false, null, null),
                new FieldDefinition("CreatedByGuid", FieldType.GUID, false, null, null),
                new FieldDefinition("CreatedByUlid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEventsCommand */
    public GetEventsCommand(UserVisitPK userVisitPK, GetEventsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    EntityInstance entityInstance;
    EntityInstance createdBy;
    Long eventCount;

    @Override
    protected Collection<Event> getEntities() {
        var entityRef = form.getEntityRef();
        var key = form.getKey();
        var guid = form.getGuid();
        var ulid = form.getUlid();
        var createdByEntityRef = form.getCreatedByEntityRef();
        var createdByKey = form.getCreatedByKey();
        var createdByGuid = form.getCreatedByGuid();
        var createdByUlid = form.getCreatedByUlid();
        var parameterCount = (entityRef == null ? 0 : 1) + (key == null ? 0 : 1) + (guid == null ? 0 : 1) + (ulid == null ? 0 : 1)
                + (createdByEntityRef == null ? 0 : 1) + (createdByKey == null ? 0 : 1) + (createdByGuid == null ? 0 : 1) + (createdByUlid == null ? 0 : 1);
        Collection<Event> entities = null;

        if(parameterCount == 1) {
            var coreControl = getCoreControl();

            if(entityRef != null || key != null || guid != null || ulid != null) {
                entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, entityRef, key, guid, ulid, null);

                if(!hasExecutionErrors()) {
                    if(session.hasLimit(EventFactory.class)) {
                        eventCount = coreControl.countEventsByEntityInstance(entityInstance);
                    }

                    entities = coreControl.getEventsByEntityInstance(entityInstance);
                }
            } else {
                createdBy = EntityInstanceLogic.getInstance().getEntityInstance(this, createdByEntityRef, createdByKey, createdByGuid, createdByUlid, null);

                if(!hasExecutionErrors()) {
                    if(session.hasLimit(EventFactory.class)) {
                        eventCount = coreControl.countEventsByCreatedBy(createdBy);
                    }

                    entities = coreControl.getEventsByCreatedBy(createdBy);
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
            var coreControl = getCoreControl();
            var userVisit = getUserVisit();

            result.setEventCount(eventCount);

            if(entityInstance != null) {
                result.setEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, entityInstance, false, false, false, false, false, false));
            }

            if(createdBy != null) {
                result.setCreatedByEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, createdBy, false, false, false, false, false, false));
            }

            result.setEvents(coreControl.getEventTransfers(userVisit, entities));
        }

        return result;
    }
    
}
