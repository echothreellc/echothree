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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.GetReturnPoliciesForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.factory.ReturnPolicyFactory;
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

public class GetReturnPoliciesCommand
        extends BasePaginatedMultipleEntitiesCommand<ReturnPolicy, GetReturnPoliciesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnPolicy.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetReturnPoliciesCommand */
    public GetReturnPoliciesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ReturnKind returnKind;

    @Override
    protected void handleForm() {
        var returnKindName = form.getReturnKindName();

        returnKind = ReturnKindLogic.getInstance().getReturnKindByName(this, returnKindName);
    }

    @Override
    protected Long getTotalEntities() {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

        return hasExecutionErrors() ? null :
                returnPolicyControl.countReturnPoliciesByReturnKind(returnKind);
    }

    @Override
    protected Collection<ReturnPolicy> getEntities() {
        Collection<ReturnPolicy> returnPolicies = null;

        if(!hasExecutionErrors()) {
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

            returnPolicies = returnPolicyControl.getReturnPolicies(returnKind);
        }

        return returnPolicies;
    }

    @Override
    protected BaseResult getResult(Collection<ReturnPolicy> entities) {
        var result = ReturnPolicyResultFactory.getGetReturnPoliciesResult();

        if(entities != null) {
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(ReturnPolicyFactory.class)) {
                result.setReturnPolicyCount(getTotalEntities());
            }

            result.setReturnKind(returnPolicyControl.getReturnKindTransfer(userVisit, returnKind));
            result.setReturnPolicies(returnPolicyControl.getReturnPolicyTransfersByReturnKind(userVisit, returnKind));
        }

        return result;
    }

}
