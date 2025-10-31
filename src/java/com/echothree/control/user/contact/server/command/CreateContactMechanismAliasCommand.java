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

import com.echothree.control.user.contact.common.form.CreateContactMechanismAliasForm;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateContactMechanismAliasCommand
        extends BaseSimpleCommand<CreateContactMechanismAliasForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateContactMechanismAliasCommand */
    public CreateContactMechanismAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismName = form.getContactMechanismName();
        var contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);

        if(contactMechanism != null) {
            var contactMechanismAliasTypeName = form.getContactMechanismAliasTypeName();
            var contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(contactMechanismAliasTypeName);

            if(contactMechanismAliasType != null) {
                var contactMechanismAliasTypeDetail = contactMechanismAliasType.getLastDetail();
                var validationPattern = contactMechanismAliasTypeDetail.getValidationPattern();
                var alias = form.getAlias();

                if(validationPattern != null) {
                    var pattern = Pattern.compile(validationPattern);
                    var m = pattern.matcher(alias);

                    if(!m.matches()) {
                        addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                    }
                }

                if(!hasExecutionErrors()) {
                    var contactMechanismAlias = contactControl.getContactMechanismAliasByAlias(contactMechanismAliasType,
                            alias);

                    if(contactMechanismAlias == null) {
                        contactControl.createContactMechanismAlias(contactMechanism, contactMechanismAliasType, alias, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateContactMechanismAlias.name(), contactMechanismName, contactMechanismAliasTypeName, alias);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
        }
        
        return null;
    }
    
}
