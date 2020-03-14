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
import com.echothree.control.user.contact.common.result.CreateContactPostalAddressResult;
import com.echothree.control.user.contact.common.result.EditContactPostalAddressResult;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPostalAddress implements En {

    public CustomerPostalAddress() {
        When("^the customer ([^\"]*) deletes the last postal address added$",
                (String persona) -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactPostalAddressForm = contactService.getDeleteContactMechanismForm();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    deleteContactPostalAddressForm.setContactMechanismName(customerPersona.lastPostalAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactPostalAddressForm);
                });

        When("^the customer ([^\"]*) begins entering a new postal address",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNull();

                    customerPersona.contactPostalAddressEdit = ContactUtil.getHome().getContactPostalAddressEdit();
                });

        When("^the customer ([^\"]*) sets the postal address's first name to \"([^\"]*)\"",
                (String persona, String firstName) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setFirstName(firstName);
                });

        When("^the customer ([^\"]*) sets the postal address's last name to \"([^\"]*)\"",
                (String persona, String lastName) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setLastName(lastName);
                });

        When("^the customer ([^\"]*) sets the postal address's line 1 to \"([^\"]*)\"",
                (String persona, String address1) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setAddress1(address1);
                });

        When("^the customer ([^\"]*) sets the postal address's line 2 to \"([^\"]*)\"",
                (String persona, String address2) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setAddress2(address2);
                });

        When("^the customer ([^\"]*) sets the postal address's line 3 to \"([^\"]*)\"",
                (String persona, String address3) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setAddress3(address3);
                });

        When("^the customer ([^\"]*) sets the postal address's city to \"([^\"]*)\"",
                (String persona, String city) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setCity(city);
                });

        When("^the customer ([^\"]*) sets the postal address's state to \"([^\"]*)\"",
                (String persona, String state) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setState(state);
                });

        When("^the customer ([^\"]*) sets the postal address's postal code to \"([^\"]*)\"",
                (String persona, String postalCode) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setPostalCode(postalCode);
                });

        When("^the customer ([^\"]*) sets the postal address's country to \"([^\"]*)\"",
                (String persona, String country) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setCountryName(country);
                });

        When("^the customer ([^\"]*)'s postal address (is|is not) a commercial location",
                (String persona, String isCommercial) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setIsCommercial(Boolean.valueOf(isCommercial.equals("is")).toString());
                });

        When("^the customer ([^\"]*) (does|does not) allow solicitations to the postal address",
                (String persona, String allowSolicitation) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the customer ([^\"]*) sets the postal address's description to \"([^\"]*)\"",
                (String persona, String description) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    customerPersona.contactPostalAddressEdit.setDescription(description);
                });

        When("^the customer ([^\"]*) adds the new postal address",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.contactPostalAddressEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactPostalAddressForm = contactService.getCreateContactPostalAddressForm();

                    createContactPostalAddressForm.set(customerPersona.contactPostalAddressEdit.get());

                    var commandResult = contactService.createContactPostalAddress(customerPersona.userVisitPK, createContactPostalAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactPostalAddressResult)commandResult.getExecutionResult().getResult();

                    customerPersona.lastPostalAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    customerPersona.contactPostalAddressEdit = null;
                });
    }

//    @When("^the customer ([^\"]*) modifies the last postal address added to the first name \"([^\"]*)\", last name \"([^\"]*)\", address line 1 \"([^\"]*)\", city \"([^\"]*)\", state \"([^\"]*)\", postal code \"([^\"]*)\" and country \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$")
//    public void theCustomerModifiesThePostalAddress(String persona, String firstName, String lastName, String address1,
//            String city, String state, String postalCode, String countryName, String description, String allowSolicitation)
//            throws NamingException {
//        editContactPostalAddress(persona, firstName, lastName, address1, city, state, postalCode, countryName,
//                description, allowSolicitation);
//    }

    private void editContactPostalAddress(String persona, String firstName, String lastName, String address1,
            String city, String state, String postalCode, String countryName, String description, String allowSolicitation)
            throws NamingException {
        var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        spec.setContactMechanismName(customerPersona.lastPostalAddressContactMechanismName);

        var commandForm = ContactUtil.getHome().getEditContactPostalAddressForm();

        commandForm.setSpec(spec);
        commandForm.setEditMode(EditMode.LOCK);

        CommandResult commandResult = ContactUtil.getHome().editContactPostalAddress(customerPersona.userVisitPK, commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (EditContactPostalAddressResult)executionResult.getResult();
            var edit = result.getEdit();

            if(firstName != null)
                edit.setFirstName(firstName);
            if(lastName != null)
                edit.setLastName(lastName);
            if(address1 != null)
                edit.setAddress1(address1);
            if(city != null)
                edit.setCity(city);
            if(state != null)
                edit.setState(state);
            if(postalCode != null)
                edit.setPostalCode(postalCode);
            if(countryName != null)
                edit.setCountryName(countryName);
            if(description != null)
                edit.setDescription(description);
            if(allowSolicitation != null)
                edit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());

            commandForm.setEdit(edit);
            commandForm.setEditMode(EditMode.UPDATE);

            commandResult = ContactUtil.getHome().editContactPostalAddress(customerPersona.userVisitPK, commandForm);
        }

        LastCommandResult.commandResult = commandResult;
    }

}
