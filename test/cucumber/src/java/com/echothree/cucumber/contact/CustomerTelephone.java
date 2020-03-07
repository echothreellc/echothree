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
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;

public class CustomerTelephone implements En {

//    @When("^the customer ([^\"]*) adds the telephone in the country \"([^\"]*)\" with the area code \"([^\"]*)\", telephone number \"([^\"]*)\" and the extension \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$")
//    public void theCustomerAddsTheTelephone(String persona, String countryName, String areaCode, String telephoneNumber,
//            String extension, String description, String allowSolicitation)
//            throws NamingException {
//        createContactTelephone(persona, countryName, areaCode, telephoneNumber, extension, description, allowSolicitation);
//    }
//
//    @When("^the customer ([^\"]*) adds the telephone in the country \"([^\"]*)\" with the area code \"([^\"]*)\" and telephone number \"([^\"]*)\" and the extension \"([^\"]*)\" and (does|does not) allow solicitations to it$")
//    public void theCustomerAddsTheTelephone(String persona, String countryName, String areaCode, String telephoneNumber,
//            String extension, String allowSolicitation)
//            throws NamingException {
//        createContactTelephone(persona, countryName, areaCode, telephoneNumber, extension, null, allowSolicitation);
//    }

    private void createContactTelephone(String persona, String countryName, String areaCode, String telephoneNumber,
            String extension, String description, String allowSolicitation)
            throws NamingException {
        var contactService = ContactUtil.getHome();
        var createContactTelephoneForm = contactService.getCreateContactTelephoneForm();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        createContactTelephoneForm.setCountryName(countryName);
        createContactTelephoneForm.setAreaCode(areaCode);
        createContactTelephoneForm.setTelephoneNumber(telephoneNumber);
        createContactTelephoneForm.setTelephoneExtension(extension);
        createContactTelephoneForm.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
        createContactTelephoneForm.setDescription(description);

        var commandResult = contactService.createContactTelephone(customerPersona.userVisitPK, createContactTelephoneForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateContactTelephoneResult) commandResult.getExecutionResult().getResult();

        customerPersona.lastTelephoneContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
    }

//    @When("^the customer ([^\"]*) modifies the last telephone added to the country \"([^\"]*)\" with the area code \"([^\"]*)\", telephone number \"([^\"]*)\" and the extension \"([^\"]*)\" with the description \"([^\"]*)\" and (does|does not) allow solicitations to it$")
//    public void theCustomerModifiesTheTelephone(String persona, String countryName, String areaCode, String telephoneNumber,
//            String extension, String description, String allowSolicitation)
//            throws NamingException {
//        editContactTelephone(persona, countryName, areaCode, telephoneNumber, extension, description, allowSolicitation);
//    }

    private void editContactTelephone(String persona, String countryName, String areaCode, String telephoneNumber,
            String extension, String description, String allowSolicitation)
            throws NamingException {
        var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
        var customerPersona = CustomerPersonas.getCustomerPersona(persona);

        spec.setContactMechanismName(customerPersona.lastTelephoneContactMechanismName);

        var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();

        commandForm.setSpec(spec);
        commandForm.setEditMode(EditMode.LOCK);

        CommandResult commandResult = ContactUtil.getHome().editContactTelephone(customerPersona.userVisitPK, commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (EditContactTelephoneResult)executionResult.getResult();
            var edit = result.getEdit();

            if(countryName != null)
                edit.setCountryName(countryName);
            if(areaCode != null)
                edit.setAreaCode(areaCode);
            if(telephoneNumber != null)
                edit.setTelephoneNumber(telephoneNumber);
            if(extension != null)
                edit.setTelephoneExtension(extension);
            if(extension != null)
                edit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
            if(extension != null)
                edit.setDescription(description);

            commandForm.setEdit(edit);
            commandForm.setEditMode(EditMode.UPDATE);

            commandResult = ContactUtil.getHome().editContactTelephone(customerPersona.userVisitPK, commandForm);
        }

        LastCommandResult.commandResult = commandResult;
    }

//    @When("^the customer ([^\"]*) deletes the last telephone added$")
//    public void theCustomerDeletesTheLastTelephone(String persona)
//            throws NamingException {
//        var contactService = ContactUtil.getHome();
//        var deleteContactTelephoneForm = contactService.getDeleteContactMechanismForm();
//        var customerPersona = CustomerPersonas.getCustomerPersona(persona);
//
//        deleteContactTelephoneForm.setContactMechanismName(customerPersona.lastTelephoneContactMechanismName);
//
//        LastCommandResult.commandResult = contactService.deleteContactMechanism(customerPersona.userVisitPK, deleteContactTelephoneForm);
//    }

}
