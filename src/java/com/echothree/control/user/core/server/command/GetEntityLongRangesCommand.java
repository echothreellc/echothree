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

import com.echothree.control.user.core.common.form.GetEntityLongRangesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.factory.EntityLongRangeFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;

public class GetEntityLongRangesCommand
        extends BaseMultipleEntitiesCommand<EntityLongRange, GetEntityLongRangesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityLongRange.name(), SecurityRoles.List.name()
                        )))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityLongRangesCommand */
    public GetEntityLongRangesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    EntityAttribute entityAttribute;

    @Override
    protected Collection<EntityLongRange> getEntities() {
        var coreControl = getCoreControl();
        var componentVendorName = form.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);
        Collection<EntityLongRange> entityLongRanges = null;

        if(componentVendor != null) {
            var entityTypeName = form.getEntityTypeName();
            var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAttributeName = form.getEntityAttributeName();

                entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    var entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
                    var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

                    if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                        entityLongRanges = coreControl.getEntityLongRanges(entityAttribute);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidEntityAttributeType.name(), componentVendorName, entityTypeName, entityAttributeName,
                                entityAttributeTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), componentVendorName, entityTypeName, entityAttributeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return entityLongRanges;
    }

    @Override
    protected BaseResult getResult(Collection<EntityLongRange> entities) {
        var result = CoreResultFactory.getGetEntityLongRangesResult();

        if(entities != null) {
            var coreControl = getCoreControl();
            var userVisit = getUserVisit();

            if(session.hasLimit(EntityLongRangeFactory.class)) {
                result.setEntityLongRangeCount(coreControl.countEntityLongRanges(entityAttribute));
            }

            result.setEntityAttribute(coreControl.getEntityAttributeTransfer(userVisit, entityAttribute, null));
            result.setEntityLongRanges(coreControl.getEntityLongRangeTransfers(userVisit, entities, null));
        }

        return result;
    }

}
