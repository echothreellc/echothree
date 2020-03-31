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
import com.echothree.control.user.contact.common.result.CreateContactTelephoneResult;
import com.echothree.control.user.contact.common.result.EditContactTelephoneResult;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTelephone implements En {

    public CustomerTelephone() {
        When("^the customer ([^\"]*) deletes the last telephone number added$",
                (String persona) -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactTelephoneForm = contactService.getDeleteContactMechanismForm();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    deleteContactTelephoneForm.setContactMechanismName(customerPersona.lastTelephoneContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactTelephoneForm);
                });

        When("^the customer ([^\"]*) begins entering a new telephone number$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNull();

                    customerPersona.contactTelephoneEdit = ContactUtil.getHome().getContactTelephoneEdit();
                });

        When("^the customer ([^\"]*) (does|does not) allow solicitations to the telephone number$",
                (String persona, String allowSolicitation) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNotNull();

                    customerPersona.contactTelephoneEdit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the customer ([^\"]*) sets the telephone number's description to \"([^\"]*)\"$",
                (String persona, String description) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNotNull();

                    customerPersona.contactTelephoneEdit.setDescription(description);
                });

        When("^the customer ([^\"]*) sets the telephone number's country to \"([^\"]*)\"$",
                (String persona, String countryName) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNotNull();

                    customerPersona.contactTelephoneEdit.setCountryName(countryName);
                });

        When("^the customer ([^\"]*) sets the telephone number's area code to \"([^\"]*)\"$",
                (String persona, String areaCode) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNotNull();

                    customerPersona.contactTelephoneEdit.setAreaCode(areaCode);
                });

        When("^the customer ([^\"]*) sets the telephone number's number to \"([^\"]*)\"$",
                (String persona, String telephoneNumber) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNotNull();

                    customerPersona.contactTelephoneEdit.setTelephoneNumber(telephoneNumber);
                });

        When("^the customer ([^\"]*) sets the telephone number's extension to \"([^\"]*)\"$",
                (String persona, String extension) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNotNull();

                    customerPersona.contactTelephoneEdit.setTelephoneExtension(extension);
                });

        When("^the customer ([^\"]*) adds the new telephone number$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactTelephoneForm = contactService.getCreateContactTelephoneForm();

                    createContactTelephoneForm.set(customerPersona.contactTelephoneEdit.get());

                    var commandResult = contactService.createContactTelephone(customerPersona.userVisitPK, createContactTelephoneForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactTelephoneResult)commandResult.getExecutionResult().getResult();

                    customerPersona.lastTelephoneContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    customerPersona.contactTelephoneEdit = null;
                });

        When("^the customer ([^\"]*) begins editing the last telephone number added$",
                (String persona) -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactTelephoneEdit).isNull();

                    spec.setContactMechanismName(customerPersona.lastTelephoneContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactTelephone(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactTelephoneResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        customerPersona.contactTelephoneEdit = result.getEdit();
                    }
                });

        When("^the customer ([^\"]*) finishes editing the telephone number$",
                (String persona) -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);
                    var edit = customerPersona.contactTelephoneEdit;

                    assertThat(edit).isNotNull();

                    spec.setContactMechanismName(customerPersona.lastTelephoneContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactTelephone(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    customerPersona.contactTelephoneEdit = null;
                });
    }

}
