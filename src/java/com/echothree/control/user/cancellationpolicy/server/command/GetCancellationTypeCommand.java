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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationTypeForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCancellationTypeCommand
        extends BaseSingleEntityCommand<CancellationType, GetCancellationTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;
    
    /** Creates a new instance of GetCancellationTypeCommand */
    public GetCancellationTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected CancellationType getEntity() {
        CancellationType cancellationType = null;
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, form.getCancellationKindName());
        
        if(!hasExecutionErrors()) {
            var cancellationTypeName = form.getCancellationTypeName();

            cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
            
            if(cancellationType == null) {
                addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
            } else {
                sendEvent(cancellationType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        }
        
        return cancellationType;
    }

    @Override
    protected BaseResult getResult(CancellationType cancellationType) {
        var result = CancellationPolicyResultFactory.getGetCancellationTypeResult();

        if(cancellationType != null) {
            result.setCancellationType(cancellationPolicyControl.getCancellationTypeTransfer(getUserVisit(), cancellationType));
        }

        return result;
    }
    
}
