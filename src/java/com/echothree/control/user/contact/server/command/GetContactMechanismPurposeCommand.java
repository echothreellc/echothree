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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetContactMechanismPurposeForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.ContactMechanismPurposeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetContactMechanismPurposeCommand
        extends BaseSingleEntityCommand<ContactMechanismPurpose, GetContactMechanismPurposeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContactMechanismPurposeCommand */
    public GetContactMechanismPurposeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected ContactMechanismPurpose getEntity() {
        var contactMechanismPurpose = ContactMechanismPurposeLogic.getInstance().getContactMechanismPurposeByUniversalSpec(this, form);

        return contactMechanismPurpose;
    }

    @Override
    protected BaseResult getResult(ContactMechanismPurpose contactMechanismPurpose) {
        var result = ContactResultFactory.getGetContactMechanismPurposeResult();

        if(contactMechanismPurpose != null) {
            var contactControl = Session.getModelController(ContactControl.class);

            result.setContactMechanismPurpose(contactControl.getContactMechanismPurposeTransfer(getUserVisit(), contactMechanismPurpose));
        }

        return result;
    }

}
