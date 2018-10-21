// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.core.remote.form.GetEventsForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.GetEventsResult;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.EventFactory;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEventsCommand
        extends BaseSimpleCommand<GetEventsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
    
    @Override
    protected BaseResult execute() {
        GetEventsResult result = CoreResultFactory.getGetEventsResult();
        String entityRef = form.getEntityRef();
        String key = form.getKey();
        String guid = form.getGuid();
        String ulid = form.getUlid();
        String createdByEntityRef = form.getCreatedByEntityRef();
        String createdByKey = form.getCreatedByKey();
        String createdByGuid = form.getCreatedByGuid();
        String createdByUlid = form.getCreatedByUlid();
        int parameterCount = (entityRef == null ? 0 : 1) + (key == null ? 0 : 1) + (guid == null ? 0 : 1) + (ulid == null ? 0 : 1)
                + (createdByEntityRef == null ? 0 : 1) + (createdByKey == null ? 0 : 1) + (createdByGuid == null ? 0 : 1) + (createdByUlid == null ? 0 : 1);
        
        if(parameterCount == 1) {
            CoreControl coreControl = getCoreControl();
            UserVisit userVisit = getUserVisit();

            if(entityRef != null || key != null || guid != null || ulid != null) {
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, entityRef, key, guid, ulid, null);

                if(!hasExecutionErrors()) {
                    if(session.hasLimit(EventFactory.class)) {
                        result.setEventCount(coreControl.countEventsByEntityInstance(entityInstance));
                    }

                    result.setEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, entityInstance, false, false, false, false, false));
                    result.setEvents(coreControl.getEventTransfersByEntityInstance(userVisit, entityInstance));
                }
            } else {
                EntityInstance createdBy = EntityInstanceLogic.getInstance().getEntityInstance(this, createdByEntityRef, createdByKey, createdByGuid, createdByUlid, null);

                if(!hasExecutionErrors()) {
                    if(session.hasLimit(EventFactory.class)) {
                        result.setEventCount(coreControl.countEventsByCreatedBy(createdBy));
                    }

                    result.setCreatedByEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, createdBy, false, false, false, false, false));
                    result.setEvents(coreControl.getEventTransfersByCreatedBy(userVisit, createdBy));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
