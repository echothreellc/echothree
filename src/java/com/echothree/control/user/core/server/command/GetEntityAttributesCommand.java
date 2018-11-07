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

import com.echothree.control.user.core.common.form.GetEntityAttributesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetEntityAttributesResult;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEntityAttributesCommand
        extends BaseSimpleCommand<GetEntityAttributesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.List.name())
                        )))
                )));
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeTypeNames", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityAttributesCommand */
    public GetEntityAttributesCommand(UserVisitPK userVisitPK, GetEntityAttributesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        GetEntityAttributesResult result = CoreResultFactory.getGetEntityAttributesResult();
        String componentVendorName = form.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = form.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                String entityAttributeTypeNames = form.getEntityAttributeTypeNames();
                
                if(entityAttributeTypeNames == null) {
                    result.setEntityAttributes(coreControl.getEntityAttributeTransfersByEntityType(getUserVisit(), entityType, null));
                } else {
                    List<EntityAttributeTransfer> entityAttributeTransfers = new ArrayList<>();
                    String []entityAttributeTypeNamesToCheck = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(entityAttributeTypeNames).toArray(new String[0]);
                    int entityAttributeTypeNamesToCheckLength = entityAttributeTypeNamesToCheck.length;
                    
                    for(int i = 0; i < entityAttributeTypeNamesToCheckLength && !hasExecutionErrors(); i++) {
                        String entityAttributeTypeName = entityAttributeTypeNamesToCheck[i];
                        EntityAttributeType entityAttributeType = coreControl.getEntityAttributeTypeByName(entityAttributeTypeName);
                        
                        if(entityAttributeType != null) {
                            entityAttributeTransfers.addAll(coreControl.getEntityAttributeTransfersByEntityTypeAndEntityAttributeType(getUserVisit(),
                                    entityType, entityAttributeType, null));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                        }
                    }
                    
                    if(!hasExecutionErrors()) {
                        result.setEntityAttributes(entityAttributeTransfers);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return result;
    }
    
}
