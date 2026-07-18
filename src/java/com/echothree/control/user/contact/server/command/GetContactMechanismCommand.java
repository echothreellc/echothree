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

import com.echothree.control.user.contact.common.form.GetContactMechanismForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.ContactMechanismLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetContactMechanismCommand
        extends BaseSingleEntityCommand<ContactMechanism, GetContactMechanismForm> {
    
    @Inject
    ContactControl contactControl;

    @Inject
    ContactMechanismLogic contactMechanismLogic;
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetContactMechanismCommand */
    public GetContactMechanismCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ContactMechanism getEntity() {
        var contactMechanism = contactMechanismLogic.getContactMechanismByUniversalSpec(this, form);

        if(contactMechanism != null) {
            sendEvent(contactMechanism.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return contactMechanism;
    }

    @Override
    protected BaseResult getResult(ContactMechanism contactMechanism) {
        var result = ContactResultFactory.getGetContactMechanismResult();

        if(contactMechanism != null) {
            result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));
        }

        return result;
    }
    
}
