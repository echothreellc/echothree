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

import com.echothree.control.user.core.common.form.GetEntityInstancesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetEntityInstancesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityInstance, GetEntityInstancesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityInstance.name(), SecurityRoles.List.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityInstancesCommand */
    public GetEntityInstancesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    EntityType entityType;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();

        entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);
    }

    @Override
    protected Long getTotalEntities() {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

        return hasExecutionErrors() ? null : entityInstanceControl.countEntityInstancesByEntityType(entityType);
    }

    @Override
    protected Collection<EntityInstance> getEntities() {
        Collection<EntityInstance> entities = null;

        if(!hasExecutionErrors()) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

            entities = entityInstanceControl.getEntityInstancesByEntityType(entityType);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<EntityInstance> entities) {
        var result = CoreResultFactory.getGetEntityInstancesResult();

        if(entities != null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

            result.setEntityInstances(entityInstanceControl.getEntityInstanceTransfers(getUserVisit(), entities,
                    false, false, false, false));
        }

        return result;
    }
    
}
