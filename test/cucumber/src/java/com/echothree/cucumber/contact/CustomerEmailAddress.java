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
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

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

        When("^the customer ([^\"]*) begins entering a new email address$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactEmailAddressEdit).isNull();

                    customerPersona.contactEmailAddressEdit = ContactUtil.getHome().getContactEmailAddressEdit();
                });

        When("^the customer ([^\"]*) (does|does not) allow solicitations to the email address$",
                (String persona, String allowSolicitation) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactEmailAddressEdit).isNotNull();

                    customerPersona.contactEmailAddressEdit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the customer ([^\"]*) sets the email address's description to \"([^\"]*)\"$",
                (String persona, String description) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactEmailAddressEdit).isNotNull();

                    customerPersona.contactEmailAddressEdit.setDescription(description);
                });

        When("^the customer ([^\"]*) sets the email address's email address to \"([^\"]*)\"$",
                (String persona, String emailAddress) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactEmailAddressEdit).isNotNull();

                    customerPersona.contactEmailAddressEdit.setEmailAddress(emailAddress);
                });

        When("^the customer ([^\"]*) adds the new email address$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactEmailAddressEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactEmailAddressForm = contactService.getCreateContactEmailAddressForm();

                    createContactEmailAddressForm.set(customerPersona.contactEmailAddressEdit.get());

                    var commandResult = contactService.createContactEmailAddress(customerPersona.userVisitPK, createContactEmailAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactEmailAddressResult)commandResult.getExecutionResult().getResult();

                    customerPersona.lastEmailAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    customerPersona.contactEmailAddressEdit = null;
                });

        When("^the customer ([^\"]*) begins editing the last email address added$",
                (String persona) -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactEmailAddressEdit).isNull();

                    spec.setContactMechanismName(customerPersona.lastEmailAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactEmailAddress(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactEmailAddressResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        customerPersona.contactEmailAddressEdit = result.getEdit();
                    }
                });

        When("^the customer ([^\"]*) finishes editing the email address$",
                (String persona) -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);
                    var edit = customerPersona.contactEmailAddressEdit;

                    assertThat(edit).isNotNull();

                    spec.setContactMechanismName(customerPersona.lastEmailAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactEmailAddress(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    customerPersona.contactEmailAddressEdit = null;
                });
    }

}
