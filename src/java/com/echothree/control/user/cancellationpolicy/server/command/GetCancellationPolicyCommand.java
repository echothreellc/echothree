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

import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationPolicyForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetCancellationPolicyCommand
        extends BaseSingleEntityCommand<CancellationPolicy, GetCancellationPolicyForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetCancellationPolicyCommand */
    public GetCancellationPolicyCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CancellationPolicy getEntity() {
        var cancellationPolicy = CancellationPolicyLogic.getInstance().getCancellationPolicyByUniversalSpec(this, form, true);

        if(cancellationPolicy != null) {
            sendEvent(cancellationPolicy.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return cancellationPolicy;
    }

    @Override
    protected BaseResult getResult(CancellationPolicy cancellationPolicy) {
        var result = CancellationPolicyResultFactory.getGetCancellationPolicyResult();

        if(cancellationPolicy != null) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);

            result.setCancellationPolicy(cancellationPolicyControl.getCancellationPolicyTransfer(getUserVisit(), cancellationPolicy));
        }

        return result;
    }

}
