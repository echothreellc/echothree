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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.GetPartyScaleUseForm;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.scale.server.entity.PartyScaleUse;
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
public class GetPartyScaleUseCommand
        extends BaseSingleEntityCommand<PartyScaleUse, GetPartyScaleUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyScaleUse.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ScaleControl scaleControl;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyScaleUseCommand */
    public GetPartyScaleUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected PartyScaleUse getEntity() {
        PartyScaleUse partyScaleUse = null;
        var partyName = form.getPartyName();
        Party party;

        if(partyName != null) {
            party = partyLogic.getPartyByName(this, partyName);
        } else {
            party = getParty();
        }


        if(!hasExecutionErrors()) {
            var scaleUseTypeName = form.getScaleUseTypeName();
            var scaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);

            if(scaleUseType != null) {
                partyScaleUse = scaleControl.getPartyScaleUse(party, scaleUseType);

                if(partyScaleUse == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyScaleUse.name(), party.getLastDetail().getPartyName(), scaleUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownScaleUseTypeName.name(), scaleUseTypeName);
            }
        }

        return partyScaleUse;
    }

    @Override
    protected BaseResult getResult(PartyScaleUse partyScaleUse) {
        var result = ScaleResultFactory.getGetPartyScaleUseResult();

        if(partyScaleUse != null) {
            result.setPartyScaleUse(scaleControl.getPartyScaleUseTransfer(getUserVisit(), partyScaleUse));
        }

        return result;
    }
    
}
