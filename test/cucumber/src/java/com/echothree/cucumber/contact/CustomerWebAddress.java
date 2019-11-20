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
import com.echothree.control.user.contact.common.result.CreateContactWebAddressResult;
import com.echothree.control.user.contact.common.result.EditContactWebAddressResult;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class CustomerWebAddress {

    @When("^the customer ([^\"]*) adds the web address \"([^\"]*)\" with the description \"([^\"]*)\"$")
    public void theCustomerAddsTheWebAddress(String persona, String url, String description)
            throws NamingException {
        createContactWebAddress(persona, url, description);
    }

    @When("^the customer ([^\"]*) adds the web address \"([^\"]*)\"$")
    public void theCustomerAddsTheWebAddress(String persona, String url)
            throws NamingException {
        createContactWebAddress(persona, url, null);
    }

    private void createContactWebAddress(String persona, String url, String description)
            throws NamingException {
        var contactService = ContactUtil.getHome();
        var createContactWebAddressForm = contactService.getCreateContactWebAddressForm();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        createContactWebAddressForm.setUrl(url);
        createContactWebAddressForm.setDescription(description);

        var commandResult = contactService.createContactWebAddress(customerPersona.userVisitPK, createContactWebAddressForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateContactWebAddressResult)commandResult.getExecutionResult().getResult();

        customerPersona.lastWebAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
    }

    @When("^the customer ([^\"]*) modifies the last web address added to \"([^\"]*)\" with the description \"([^\"]*)\"$")
    public void theCustomerModifiesTheWebAddress(String persona, String webAddress, String description)
            throws NamingException {
        editContactWebAddress(persona, webAddress, description);
    }

    private void editContactWebAddress(String persona, String webAddress, String description)
            throws NamingException {
        var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        spec.setContactMechanismName(customerPersona.lastWebAddressContactMechanismName);

        var commandForm = ContactUtil.getHome().getEditContactWebAddressForm();

        commandForm.setSpec(spec);
        commandForm.setEditMode(EditMode.LOCK);

        CommandResult commandResult = ContactUtil.getHome().editContactWebAddress(customerPersona.userVisitPK, commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (EditContactWebAddressResult)executionResult.getResult();
            var edit = result.getEdit();

            if(webAddress != null)
                edit.setUrl(webAddress);
            if(description != null)
                edit.setDescription(description);

            commandForm.setEdit(edit);
            commandForm.setEditMode(EditMode.UPDATE);

            commandResult = ContactUtil.getHome().editContactWebAddress(customerPersona.userVisitPK, commandForm);
        }

        LastCommandResult.commandResult = commandResult;
    }

    @When("^the customer ([^\"]*) deletes the last web address added$")
    public void theCustomerDeletesTheLastWebAddress(String persona)
            throws NamingException {
        var contactService = ContactUtil.getHome();
        var deleteContactWebAddressForm = contactService.getDeleteContactMechanismForm();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        deleteContactWebAddressForm.setContactMechanismName(customerPersona.lastWebAddressContactMechanismName);

        LastCommandResult.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactWebAddressForm);
    }

}
