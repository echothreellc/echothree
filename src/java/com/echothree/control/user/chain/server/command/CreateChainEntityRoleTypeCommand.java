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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.form.CreateChainEntityRoleTypeForm;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.ComponentVendor;
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
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateChainEntityRoleTypeCommand
        extends BaseSimpleCommand<CreateChainEntityRoleTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ChainEntityRoleType.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateChainEntityRoleTypeCommand */
    public CreateChainEntityRoleTypeCommand(UserVisitPK userVisitPK, CreateChainEntityRoleTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        String chainKindName = form.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            String chainTypeName = form.getChainTypeName();
            ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                String chainEntityRoleTypeName = form.getChainEntityRoleTypeName();
                ChainEntityRoleType chainEntityRoleType = chainControl.getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName);

                if(chainEntityRoleType == null) {
                    CoreControl coreControl = getCoreControl();
                    String componentVendorName = form.getComponentVendorName();
                    ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
                    
                    if(componentVendor != null) {
                        String entityTypeName = form.getEntityTypeName();
                        EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                        
                        if(entityType != null) {
                            PartyPK partyPK = getPartyPK();
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            String description = form.getDescription();

                            chainEntityRoleType = chainControl.createChainEntityRoleType(chainType, chainEntityRoleTypeName, entityType, sortOrder, partyPK);

                            if(description != null) {
                                chainControl.createChainEntityRoleTypeDescription(chainEntityRoleType, getPreferredLanguage(), description, partyPK);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateChainEntityRoleTypeName.name(), chainKindName, chainTypeName, chainEntityRoleTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }
        
        return null;
    }
    
}
