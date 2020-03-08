// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.cucumber.contact;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.CreateContactEmailAddressResult;
import com.echothree.control.user.contact.common.result.EditContactEmailAddressResult;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;

public class CustomerEmailAddress implements En {

    public CustomerEmailAddress() {
        When("^the customer ([^\"]*) deletes the last email address added$",
                (String persona) -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactEmailAddressForm = contactService.getDeleteContactMechanismForm();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    deleteContactEmailAddressForm.setContactMechanismName(customerPersona.lastEmailAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactEmailAddressForm);
                });

        When("^the customer ([^\"]*) adds the email address \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$",
                (String persona, String emailAddress, String description, String allowSolicitation) -> {
                    createContactEmailAddress(persona, emailAddress, description, allowSolicitation);
                });

        When("^the customer ([^\"]*) adds the email address \"([^\"]*)\" and (does|does not) allow solicitations to it$",
                (String persona, String emailAddress, String allowSolicitation) -> {
                    createContactEmailAddress(persona, emailAddress, null, allowSolicitation);
                });

        When("^the customer ([^\"]*) modifies the last email address added to \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$",
                (String persona, String emailAddress, String description, String allowSolicitation) -> {
                    editContactEmailAddress(persona, emailAddress, description, allowSolicitation);
                });
    }

    private void createContactEmailAddress(String persona, String emailAddress, String description, String allowSolicitation)
            throws NamingException {
        var contactService = ContactUtil.getHome();
        var createContactEmailAddressForm = contactService.getCreateContactEmailAddressForm();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        createContactEmailAddressForm.setEmailAddress(emailAddress);
        createContactEmailAddressForm.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
        createContactEmailAddressForm.setDescription(description);

        var commandResult = contactService.createContactEmailAddress(customerPersona.userVisitPK, createContactEmailAddressForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateContactEmailAddressResult)commandResult.getExecutionResult().getResult();

        customerPersona.lastEmailAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
    }

    private void editContactEmailAddress(String persona, String emailAddress, String description, String allowSolicitation)
            throws NamingException {
        var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        spec.setContactMechanismName(customerPersona.lastEmailAddressContactMechanismName);

        var commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();

        commandForm.setSpec(spec);
        commandForm.setEditMode(EditMode.LOCK);

        CommandResult commandResult = ContactUtil.getHome().editContactEmailAddress(customerPersona.userVisitPK, commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (EditContactEmailAddressResult)executionResult.getResult();
            var edit = result.getEdit();

            if(emailAddress != null)
                edit.setEmailAddress(emailAddress);
            if(description != null)
                edit.setDescription(description);
            if(allowSolicitation != null)
                edit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());

            commandForm.setEdit(edit);
            commandForm.setEditMode(EditMode.UPDATE);

            commandResult = ContactUtil.getHome().editContactEmailAddress(customerPersona.userVisitPK, commandForm);
        }

        LastCommandResult.commandResult = commandResult;
    }

}
