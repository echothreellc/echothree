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

import com.echothree.control.user.returnpolicy.common.form.GetReturnPolicyForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnPolicyLogic;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetReturnPolicyCommand
        extends BaseSingleEntityCommand<ReturnPolicy, GetReturnPolicyForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetReturnPolicyCommand */
    public GetReturnPolicyCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected ReturnPolicy getEntity() {
        var returnPolicy = ReturnPolicyLogic.getInstance().getReturnPolicyByUniversalSpec(this, form, true);

        if(returnPolicy != null) {
            sendEvent(returnPolicy.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return returnPolicy;
    }

    @Override
    protected BaseResult getResult(ReturnPolicy returnPolicy) {
        var result = ReturnPolicyResultFactory.getGetReturnPolicyResult();

        if(returnPolicy != null) {
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

            result.setReturnPolicy(returnPolicyControl.getReturnPolicyTransfer(getUserVisit(), returnPolicy));
        }

        return result;
    }

}
