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
import com.echothree.control.user.contact.common.result.CreateContactWebAddressResult;
import com.echothree.control.user.contact.common.result.EditContactWebAddressResult;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerWebAddress implements En {

    public CustomerWebAddress() {
        When("^the customer ([^\"]*) deletes the last web address added$",
                (String persona) -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactWebAddressForm = contactService.getDeleteContactMechanismForm();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    deleteContactWebAddressForm.setContactMechanismName(customerPersona.lastWebAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactWebAddressForm);
                });

        When("^the customer ([^\"]*) begins entering a new web address",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactWebAddressEdit).isNull();

                    customerPersona.contactWebAddressEdit = ContactUtil.getHome().getContactWebAddressEdit();
                });

        When("^the customer ([^\"]*) sets the web address's description to \"([^\"]*)\"",
                (String persona, String description) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactWebAddressEdit).isNotNull();

                    customerPersona.contactWebAddressEdit.setDescription(description);
                });

        When("^the customer ([^\"]*) sets the web address's url to \"([^\"]*)\"",
                (String persona, String url) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactWebAddressEdit).isNotNull();

                    customerPersona.contactWebAddressEdit.setUrl(url);
                });

        When("^the customer ([^\"]*) adds the new web address",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactWebAddressEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactWebAddressForm = contactService.getCreateContactWebAddressForm();

                    createContactWebAddressForm.set(customerPersona.contactWebAddressEdit.get());

                    var commandResult = contactService.createContactWebAddress(customerPersona.userVisitPK, createContactWebAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactWebAddressResult)commandResult.getExecutionResult().getResult();

                    customerPersona.lastWebAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    customerPersona.contactWebAddressEdit = null;
                });

        When("^the customer ([^\"]*) begins editing the last web address added",
                (String persona) -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactWebAddressEdit).isNull();

                    spec.setContactMechanismName(customerPersona.lastWebAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactWebAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactWebAddress(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactWebAddressResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        customerPersona.contactWebAddressEdit = result.getEdit();
                    }
                });

        When("^the customer ([^\"]*) finishes editing the web address",
                (String persona) -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);
                    var edit = customerPersona.contactWebAddressEdit;

                    assertThat(edit).isNotNull();

                    spec.setContactMechanismName(customerPersona.lastWebAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactWebAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactWebAddress(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;
                });
    }

}
