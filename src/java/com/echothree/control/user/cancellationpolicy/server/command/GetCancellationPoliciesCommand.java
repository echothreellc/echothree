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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationPoliciesForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetCancellationPoliciesCommand
        extends BasePaginatedMultipleEntitiesCommand<CancellationPolicy, GetCancellationPoliciesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationPolicy.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetCancellationPoliciesCommand */
    public GetCancellationPoliciesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    CancellationKind cancellationKind;

    @Override
    protected void handleForm() {
        var cancellationKindName = form.getCancellationKindName();

        cancellationKind = CancellationKindLogic.getInstance().getCancellationKindByName(this, cancellationKindName);
    }

    @Override
    protected Long getTotalEntities() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);

        return hasExecutionErrors() ? null :
                cancellationPolicyControl.countCancellationPoliciesByCancellationKind(cancellationKind);
    }

    @Override
    protected Collection<CancellationPolicy> getEntities() {
        Collection<CancellationPolicy> cancellationPolicies = null;

        if(!hasExecutionErrors()) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);

            cancellationPolicies = cancellationPolicyControl.getCancellationPolicies(cancellationKind);
        }

        return cancellationPolicies;
    }

    @Override
    protected BaseResult getResult(Collection<CancellationPolicy> entities) {
        var result = CancellationPolicyResultFactory.getGetCancellationPoliciesResult();

        if(entities != null) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(CancellationPolicyFactory.class)) {
                result.setCancellationPolicyCount(getTotalEntities());
            }

            result.setCancellationKind(cancellationPolicyControl.getCancellationKindTransfer(userVisit, cancellationKind));
            result.setCancellationPolicies(cancellationPolicyControl.getCancellationPolicyTransfersByCancellationKind(userVisit, cancellationKind));
        }

        return result;
    }
    
}
