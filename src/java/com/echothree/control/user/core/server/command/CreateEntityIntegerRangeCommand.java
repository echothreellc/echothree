// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.CreateEntityIntegerRangeForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class CreateEntityIntegerRangeCommand
        extends BaseSimpleCommand<CreateEntityIntegerRangeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityIntegerRange.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityIntegerRangeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MinimumIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateEntityIntegerRangeCommand */
    public CreateEntityIntegerRangeCommand(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        String componentVendorName = form.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = form.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                String entityAttributeName = form.getEntityAttributeName();
                EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);
                
                if(entityAttribute != null) {
                    String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
                    
                    if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                        String entityIntegerRangeName = form.getEntityIntegerRangeName();
                        EntityIntegerRange entityIntegerRange = coreControl.getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName);

                        if(entityIntegerRange == null) {
                            PartyPK partyPK = getPartyPK();
                            String strMinimumIntegerValue = form.getMinimumIntegerValue();
                            Integer minimumIntegerValue = strMinimumIntegerValue == null ? null : Integer.valueOf(strMinimumIntegerValue);
                            String strMaximumIntegerValue = form.getMaximumIntegerValue();
                            Integer maximumIntegerValue = strMaximumIntegerValue == null ? null : Integer.valueOf(strMaximumIntegerValue);
                            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            String description = form.getDescription();

                            if(minimumIntegerValue == null || maximumIntegerValue == null || maximumIntegerValue >= minimumIntegerValue) {
                                entityIntegerRange = coreControl.createEntityIntegerRange(entityAttribute, entityIntegerRangeName, minimumIntegerValue,
                                        maximumIntegerValue, isDefault, sortOrder, partyPK);

                                if(description != null) {
                                    coreControl.createEntityIntegerRangeDescription(entityIntegerRange, getPreferredLanguage(), description, partyPK);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MinimumValueGreaterThanMaximumValue.name(), strMinimumIntegerValue, strMaximumIntegerValue);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateEntityIntegerRangeName.name(), componentVendorName, entityTypeName, entityAttributeName, entityIntegerRangeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidEntityAttributeType.name(), componentVendorName, entityTypeName, entityAttributeName, entityAttributeTypeName);
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
        
        return null;
    }
    
}
