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

import com.echothree.control.user.core.common.form.GetEntityAttributeEntityTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityType;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityTypeFactory;
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
public class GetEntityAttributeEntityTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityAttributeEntityType, GetEntityAttributeEntityTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeEntityType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowedComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowedEntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
        );
    }
    
    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetEntityAttributeEntityTypesCommand */
    public GetEntityAttributeEntityTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private EntityAttribute entityAttribute;
    private EntityType allowedEntityType;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityAttributeName = form.getEntityAttributeName();
        var allowedComponentVendorName = form.getAllowedComponentVendorName();
        var allowedEntityTypeName = form.getAllowedEntityTypeName();
        var parameterCount = (componentVendorName != null && entityTypeName != null && entityAttributeName != null && allowedComponentVendorName == null && allowedEntityTypeName == null ? 1 : 0)
                + (componentVendorName == null && entityTypeName == null && entityAttributeName == null && allowedComponentVendorName != null && allowedEntityTypeName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(componentVendorName != null) {
                var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

                if(entityType != null) {
                    entityAttribute = entityAttributeLogic.getEntityAttributeByName(this, entityType, entityAttributeName);

                    if(entityAttribute == null) {
                        addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityAttributeName);
                    }
                }
            } else {
                var allowedComponentVendor = componentControl.getComponentVendorByName(allowedComponentVendorName);

                if(allowedComponentVendor != null) {
                    allowedEntityType = entityTypeControl.getEntityTypeByName(allowedComponentVendor, allowedEntityTypeName);

                    if(allowedEntityType == null) {
                        addExecutionError(ExecutionErrors.UnknownAllowedEntityTypeName.name(), allowedComponentVendorName, allowedEntityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownAllowedComponentVendorName.name(), allowedComponentVendorName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                entityAttribute != null ? coreControl.countEntityAttributeEntityTypesByEntityAttribute(entityAttribute) :
                        coreControl.countEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType);
    }

    @Override
    protected Collection<EntityAttributeEntityType> getEntities() {
        return hasExecutionErrors() ? null :
                entityAttribute != null ? coreControl.getEntityAttributeEntityTypesByEntityAttribute(entityAttribute) :
                        coreControl.getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType);
    }

    @Override
    protected BaseResult getResult(Collection<EntityAttributeEntityType> entities) {
        var result = CoreResultFactory.getGetEntityAttributeEntityTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(entityAttribute != null) {
                result.setEntityAttribute(coreControl.getEntityAttributeTransfer(userVisit, entityAttribute, null));
            }

            if(allowedEntityType != null) {
                result.setEntityType(entityTypeControl.getEntityTypeTransfer(userVisit, allowedEntityType));
            }

            if(session.hasLimit(EntityAttributeEntityTypeFactory.class)) {
                result.setEntityAttributeEntityTypeCount(getTotalEntities());
            }

            result.setEntityAttributeEntityTypes(coreControl.getEntityAttributeEntityTypeTransfers(userVisit, entities, null));
        }

        return result;
    }
    
}
