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

import com.echothree.control.user.core.common.form.GetEntityAttributeEntityTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetEntityAttributeEntityTypesCommand
        extends BaseSimpleCommand<GetEntityAttributeEntityTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeEntityType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowedComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowedEntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityAttributeEntityTypesCommand */
    public GetEntityAttributeEntityTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getGetEntityAttributeEntityTypesResult();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityAttributeName = form.getEntityAttributeName();
        var allowedComponentVendorName = form.getAllowedComponentVendorName();
        var allowedEntityTypeName = form.getAllowedEntityTypeName();
        var parameterCount = (componentVendorName == null && entityTypeName == null && entityAttributeName == null && allowedComponentVendorName != null && allowedEntityTypeName != null ? 0 : 1)
                + (componentVendorName != null && entityTypeName != null && entityAttributeName != null && allowedComponentVendorName == null && allowedEntityTypeName == null ? 0 : 1);

        if(parameterCount == 1) {

            if(componentVendorName != null) {
                var componentVendor = componentControl.getComponentVendorByName(componentVendorName);

                if(componentVendor != null) {
                    var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);

                    if(entityType != null) {
                        var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                        if(entityAttribute != null) {
                            result.setEntityAttribute(coreControl.getEntityAttributeTransfer(getUserVisit(), entityAttribute, null));
                            result.setEntityAttributeEntityTypes(coreControl.getEntityAttributeEntityTypeTransfersByEntityAttribute(getUserVisit(), entityAttribute, null));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityAttributeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            } else {
                var allowedComponentVendor = componentControl.getComponentVendorByName(allowedComponentVendorName);

                if(allowedComponentVendor != null) {
                    var allowedEntityType = entityTypeControl.getEntityTypeByName(allowedComponentVendor, allowedEntityTypeName);

                    if(allowedEntityType != null) {
                        result.setEntityType(entityTypeControl.getEntityTypeTransfer(getUserVisit(), allowedEntityType));
                        result.setEntityAttributeEntityTypes(coreControl.getEntityAttributeEntityTypeTransfersByAllowedEntityType(getUserVisit(), allowedEntityType, null));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownAllowedEntityTypeName.name(), allowedComponentVendorName, allowedEntityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownAllowedComponentVendorName.name(), allowedComponentVendorName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
