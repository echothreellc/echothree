// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetEntityAttributesCommand
        extends BaseMultipleEntitiesCommand<EntityAttribute, GetEntityAttributesForm> {

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
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("EntityAttributeTypeNames", FieldType.STRING, false, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityAttributesCommand */
    public GetEntityAttributesCommand(UserVisitPK userVisitPK, GetEntityAttributesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<EntityAttribute> getEntities() {
        Collection<EntityAttribute> entityAttributes = null;
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByUniversalSpec(this, form);

        if(!hasExecutionErrors()) {
            var coreControl = getCoreControl();
            var entityAttributeTypeNames = form.getEntityAttributeTypeNames();

            if(entityAttributeTypeNames == null) {
                entityAttributes = coreControl.getEntityAttributesByEntityType(entityType);
            } else {
                var entityAttributeTypeNamesToCheck = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(entityAttributeTypeNames).toArray(new String[0]);
                var entityAttributeTypeNamesToCheckLength = entityAttributeTypeNamesToCheck.length;

                entityAttributes = new ArrayList<>();

                for(int i = 0; i < entityAttributeTypeNamesToCheckLength && !hasExecutionErrors(); i++) {
                    var entityAttributeTypeName = entityAttributeTypeNamesToCheck[i];
                    var entityAttributeType = coreControl.getEntityAttributeTypeByName(entityAttributeTypeName);

                    if(entityAttributeType != null) {
                        entityAttributes.addAll(coreControl.getEntityAttributesByEntityTypeAndEntityAttributeType(
                                entityType, entityAttributeType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                    }
                }

                if(hasExecutionErrors()) {
                    // If we encounter an UnknownEntityAttributeTypeName error, this will end up true and
                    // we will nuke the results and return nothing.
                    entityAttributes = null;
                }
            }
        }

        return entityAttributes;
    }

    @Override
    protected BaseResult getTransfers(Collection<EntityAttribute> entities) {
        var result = CoreResultFactory.getGetEntityAttributesResult();

        if(entities != null) {
            var coreControl = getCoreControl();

            result.setEntityAttributes(coreControl.getEntityAttributeTransfers(getUserVisit(), entities, null));
        }

        return result;
    }

}
