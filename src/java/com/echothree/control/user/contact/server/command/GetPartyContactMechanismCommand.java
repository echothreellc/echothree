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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetPartyContactMechanismForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.PartyContactMechanismLogic;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyContactMechanismCommand
        extends BaseSingleEntityCommand<PartyContactMechanism, GetPartyContactMechanismForm> {
    
    @Inject
    ContactControl contactControl;

    @Inject
    PartyContactMechanismLogic partyContactMechanismLogic;
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetPartyContactMechanismCommand */
    public GetPartyContactMechanismCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected PartyContactMechanism getEntity() {
        return partyContactMechanismLogic.getPartyContactMechanismByUniversalSpec(this, form);
    }

    @Override
    protected BaseResult getResult(PartyContactMechanism partyContactMechanism) {
        var result = ContactResultFactory.getGetPartyContactMechanismResult();

        if(partyContactMechanism != null) {
            result.setPartyContactMechanism(contactControl.getPartyContactMechanismTransfer(getUserVisit(), partyContactMechanism));
        }

        return result;
    }
    
}
