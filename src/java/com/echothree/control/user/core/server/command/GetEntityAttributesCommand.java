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

import com.echothree.control.user.core.common.form.GetEntityAttributesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetEntityAttributesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityAttribute, GetEntityAttributesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeTypeNames", FieldType.STRING, false, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityAttributesCommand */
    public GetEntityAttributesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    EntityType entityType;
    Collection<EntityAttributeType> entityAttributeTypes;

    @Override
    protected void handleForm() {
        var entityAttributeTypeNames = form.getEntityAttributeTypeNames();

        entityType = EntityTypeLogic.getInstance().getEntityTypeByUniversalSpec(this, form);

        if(!hasExecutionErrors() && entityAttributeTypeNames != null) {
            var entityAttributeTypeNamesToCheck = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(entityAttributeTypeNames).toArray(new String[0]);
            var entityAttributeTypeNamesToCheckLength = entityAttributeTypeNamesToCheck.length;

            entityAttributeTypes = new ArrayList<>();

            for(var i = 0; i < entityAttributeTypeNamesToCheckLength && !hasExecutionErrors(); i++) {
                var entityAttributeTypeName = entityAttributeTypeNamesToCheck[i];
                var entityAttributeType = coreControl.getEntityAttributeTypeByName(entityAttributeTypeName);

                if(entityAttributeType != null) {
                    entityAttributeTypes.add(entityAttributeType);
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                }
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(entityAttributeTypes == null) {
                totalEntities = coreControl.countEntityAttributesByEntityType(entityType);
            } else {
                var totalEntitiesTally = 0L;

                for(var entityAttributeType : entityAttributeTypes) {
                    totalEntitiesTally += coreControl.countEntityAttributesByEntityTypeAndEntityAttributeType(
                            entityType, entityAttributeType);
                }

                totalEntities = totalEntitiesTally;
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<EntityAttribute> getEntities() {
        Collection<EntityAttribute> entityAttributes = null;

        if(!hasExecutionErrors()) {
            if(entityAttributeTypes == null) {
                entityAttributes = coreControl.getEntityAttributesByEntityType(entityType);
            } else {

                entityAttributes = new ArrayList<>();

                for(var entityAttributeType : entityAttributeTypes) {
                    entityAttributes.addAll(coreControl.getEntityAttributesByEntityTypeAndEntityAttributeType(
                            entityType, entityAttributeType));

                }
            }
        }

        return entityAttributes;
    }

    @Override
    protected BaseResult getResult(Collection<EntityAttribute> entities) {
        var result = CoreResultFactory.getGetEntityAttributesResult();

        if(entities != null) {

            result.setEntityAttributes(coreControl.getEntityAttributeTransfers(getUserVisit(), entities, null));
        }

        return result;
    }

}
