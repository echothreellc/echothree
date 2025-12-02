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

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.ContactWebAddressEdit;
import com.echothree.control.user.contact.common.form.EditContactWebAddressForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditContactWebAddressResult;
import com.echothree.control.user.contact.common.spec.PartyContactMechanismSpec;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditContactWebAddressCommand
        extends BaseAbstractEditCommand<PartyContactMechanismSpec, ContactWebAddressEdit, EditContactWebAddressResult, PartyContactMechanism, ContactMechanism> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanism.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Url", FieldType.URL, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContactWebAddressCommand */
    public EditContactWebAddressCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected SecurityResult security() {
        var securityResult = super.security();

        return securityResult != null ? securityResult : selfOnly(spec);
    }
    
    @Override
    public EditContactWebAddressResult getResult() {
        return ContactResultFactory.getEditContactWebAddressResult();
    }

    @Override
    public ContactWebAddressEdit getEdit() {
        return ContactEditFactory.getContactWebAddressEdit();
    }

    @Override
    public PartyContactMechanism getEntity(EditContactWebAddressResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyContactMechanism partyContactMechanism = null;
        var partyName = spec.getPartyName();
        var party = partyName == null ? getParty() : partyControl.getPartyByName(partyName);

        if(party != null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var contactMechanismName = spec.getContactMechanismName();
            var contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);

            if(contactMechanism != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyContactMechanism = contactControl.getPartyContactMechanism(party, contactMechanism);
                } else { // EditMode.UPDATE
                    partyContactMechanism = contactControl.getPartyContactMechanismForUpdate(party, contactMechanism);
                }

                if(partyContactMechanism != null) {
                    var lastContactMechanismDetail = contactMechanism.getLastDetail();
                    var contactMechanismTypeName = lastContactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();

                    result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));

                    if(!ContactMechanismTypes.WEB_ADDRESS.name().equals(contactMechanismTypeName)) {
                        addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), partyName, contactMechanismName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return partyContactMechanism;
    }

    @Override
    public ContactMechanism getLockEntity(PartyContactMechanism partyContactMechanism) {
        return partyContactMechanism.getLastDetail().getContactMechanism();
    }

    @Override
    public void fillInResult(EditContactWebAddressResult result, PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);

        result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(),
                partyContactMechanism.getLastDetail().getContactMechanism()));
    }

    @Override
    public void doLock(ContactWebAddressEdit edit, PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanism = partyContactMechanism.getLastDetail().getContactMechanism();
        var contactWebAddress = contactControl.getContactWebAddress(contactMechanism);
        var partyContactMechanismDetail = partyContactMechanism.getLastDetail();

        edit.setUrl(contactWebAddress.getUrl());
        edit.setDescription(partyContactMechanismDetail.getDescription());
    }

    @Override
    public void doUpdate(PartyContactMechanism partyContactMechanism) {
        var contactControl = Session.getModelController(ContactControl.class);
        var updatedBy = getPartyPK();
        var contactMechanism = partyContactMechanism.getLastDetail().getContactMechanism();
        var contactWebAddressValue = contactControl.getContactWebAddressValueForUpdate(contactMechanism);
        var partyContactMechanismDetailValue = contactControl.getPartyContactMechanismDetailValueForUpdate(partyContactMechanism);

        contactWebAddressValue.setUrl(edit.getUrl());
        partyContactMechanismDetailValue.setDescription(edit.getDescription());

        contactControl.updateContactWebAddressFromValue(contactWebAddressValue, updatedBy);
        contactControl.updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, updatedBy);
    }

}

