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

import com.echothree.control.user.contact.common.form.GetContactMechanismPurposesForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetContactMechanismPurposesCommand
        extends BasePaginatedMultipleEntitiesCommand<ContactMechanismPurpose, GetContactMechanismPurposesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetContactMechanismPurposesCommand */
    public GetContactMechanismPurposesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var contactControl = Session.getModelController(ContactControl.class);

        return contactControl.countContactMechanismPurposes();
    }

    @Override
    protected Collection<ContactMechanismPurpose> getEntities() {
        var contactControl = Session.getModelController(ContactControl.class);

        return contactControl.getContactMechanismPurposes();
    }

    @Override
    protected BaseResult getResult(Collection<ContactMechanismPurpose> entities) {
        var result = ContactResultFactory.getGetContactMechanismPurposesResult();

        if(entities != null) {
            var contactControl = Session.getModelController(ContactControl.class);

            result.setContactMechanismPurposes(contactControl.getContactMechanismPurposeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
