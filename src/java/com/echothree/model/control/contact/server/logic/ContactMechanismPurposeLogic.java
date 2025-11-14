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

package com.echothree.model.control.contact.server.logic;

import com.echothree.control.user.contact.common.spec.ContactMechanismPurposeUniversalSpec;
import com.echothree.model.control.contact.common.exception.UnknownContactMechanismPurposeNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ContactMechanismPurposeLogic
    extends BaseLogic {

    protected ContactMechanismPurposeLogic() {
        super();
    }

    public static ContactMechanismPurposeLogic getInstance() {
        return CDI.current().select(ContactMechanismPurposeLogic.class).get();
    }

    public ContactMechanismPurpose getContactMechanismPurposeByName(final ExecutionErrorAccumulator eea, final String contactMechanismPurposeName) {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);

        if(contactMechanismPurpose == null) {
            handleExecutionError(UnknownContactMechanismPurposeNameException.class, eea, ExecutionErrors.UnknownContactMechanismPurposeName.name(), contactMechanismPurposeName);
        }

        return contactMechanismPurpose;
    }

    public ContactMechanismPurpose getContactMechanismPurposeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactMechanismPurposeUniversalSpec universalSpec) {
        ContactMechanismPurpose contactMechanismPurpose = null;
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismPurposeName = universalSpec.getContactMechanismPurposeName();
        var parameterCount = (contactMechanismPurposeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(contactMechanismPurposeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContactMechanismPurpose.name());

                    if(!eea.hasExecutionErrors()) {
                        contactMechanismPurpose = contactControl.getContactMechanismPurposeByEntityInstance(entityInstance);
                    }
                } else {
                    contactMechanismPurpose = getContactMechanismPurposeByName(eea, contactMechanismPurposeName);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contactMechanismPurpose;
    }

}
