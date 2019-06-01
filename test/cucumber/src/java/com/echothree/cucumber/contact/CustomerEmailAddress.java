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
import com.echothree.control.user.contact.common.result.CreateContactEmailAddressResult;
import com.echothree.cucumber.CustomerPersonas;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class CustomerEmailAddress {

    @When("^the customer ([^\"]*) adds the email address \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$")
    public void theCustomerAddsTheEmailAddress(String persona, String emailAddress, String description, String allowSolicitation)
            throws NamingException {
        createContactEmailAddress(persona, emailAddress, description, allowSolicitation);
    }

    @When("^the customer ([^\"]*) adds the email address \"([^\"]*)\" and (does|does not) allow solicitations to it$")
    public void theCustomerAddsTheEmailAddress(String persona, String emailAddress, String allowSolicitation)
            throws NamingException {
        createContactEmailAddress(persona, emailAddress, null, allowSolicitation);
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

        customerPersona.commandResult = commandResult;
        var result = (CreateContactEmailAddressResult)commandResult.getExecutionResult().getResult();

        customerPersona.lastEmailAddressContactMechanismName = result.getContactMechanismName();
    }

    @When("^the customer ([^\"]*) deletes the last email address added$")
    public void theCustomerDeletesTheLastEmailAddress(String persona)
            throws NamingException {
        var contactService = ContactUtil.getHome();
        var deleteContactEmailAddressForm = contactService.getDeleteContactMechanismForm();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        deleteContactEmailAddressForm.setContactMechanismName(customerPersona.lastEmailAddressContactMechanismName);

        customerPersona.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactEmailAddressForm);
    }

}
