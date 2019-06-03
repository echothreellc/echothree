// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class CustomerPostalAddress {

    @When("^the customer ([^\"]*) adds the postal address with the first name \"([^\"]*)\", last name \"([^\"]*)\", address line 1 \"([^\"]*)\", city \"([^\"]*)\", state \"([^\"]*)\", postal code \"([^\"]*)\" and country \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$")
    public void theCustomerAddsThePostalAddress(String persona, String firstName, String lastName, String address1,
            String city, String state, String postalCode, String countryName, String description, String allowSolicitation)
            throws NamingException {
        createContactPostalAddress(persona, firstName, lastName, address1, city, state, postalCode, countryName,
                description, allowSolicitation);
    }

    @When("^the customer ([^\"]*) adds the postal address with the first name \"([^\"]*)\", last name \"([^\"]*)\", address line 1 \"([^\"]*)\", city \"([^\"]*)\", state \"([^\"]*)\", postal code \"([^\"]*)\" and country \"([^\"]*)\" and (does|does not) allow solicitations to it$")
    public void theCustomerAddsThePostalAddress(String persona, String firstName, String lastName, String address1,
            String city, String state, String postalCode, String countryName, String allowSolicitation)
            throws NamingException {
        createContactPostalAddress(persona, firstName, lastName, address1, city, state, postalCode, countryName,
                null, allowSolicitation);
    }

    private void createContactPostalAddress(String persona, String firstName, String lastName, String address1, String city,
            String state, String postalCode, String countryName, String description, String allowSolicitation)
            throws NamingException {
        var contactService = ContactUtil.getHome();
        var createContactPostalAddressForm = contactService.getCreateContactPostalAddressForm();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        createContactPostalAddressForm.setFirstName(firstName);
        createContactPostalAddressForm.setLastName(lastName);
        createContactPostalAddressForm.setAddress1(address1);
        createContactPostalAddressForm.setCity(city);
        createContactPostalAddressForm.setState(state);
        createContactPostalAddressForm.setPostalCode(postalCode);
        createContactPostalAddressForm.setCountryName(countryName);
        createContactPostalAddressForm.setIsCommercial(Boolean.FALSE.toString());
        createContactPostalAddressForm.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
        createContactPostalAddressForm.setDescription(description);

        var commandResult = contactService.createContactPostalAddress(customerPersona.userVisitPK, createContactPostalAddressForm);

        customerPersona.commandResult = commandResult;
        var result = (CreateContactPostalAddressResult)commandResult.getExecutionResult().getResult();

        customerPersona.lastPostalAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
    }

    @When("^the customer ([^\"]*) modifies the last postal address added to the first name \"([^\"]*)\", last name \"([^\"]*)\", address line 1 \"([^\"]*)\", city \"([^\"]*)\", state \"([^\"]*)\", postal code \"([^\"]*)\" and country \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$")
    public void theCustomerModifiesThePostalAddress(String persona, String firstName, String lastName, String address1,
            String city, String state, String postalCode, String countryName, String description, String allowSolicitation)
            throws NamingException {
        editContactPostalAddress(persona, firstName, lastName, address1, city, state, postalCode, countryName,
                description, allowSolicitation);
    }

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

        customerPersona.commandResult = commandResult;
    }

    @When("^the customer ([^\"]*) deletes the last postal address added$")
    public void theCustomerDeletesTheLastPostalAddress(String persona)
            throws NamingException {
        var contactService = ContactUtil.getHome();
        var deleteContactPostalAddressForm = contactService.getDeleteContactMechanismForm();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        deleteContactPostalAddressForm.setContactMechanismName(customerPersona.lastPostalAddressContactMechanismName);

        customerPersona.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactPostalAddressForm);
    }

}
