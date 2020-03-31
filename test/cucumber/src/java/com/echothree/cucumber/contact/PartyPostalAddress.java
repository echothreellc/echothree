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
import com.echothree.cucumber.LastCommandResult;
import com.echothree.cucumber.user.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyPostalAddress implements En {

    public PartyPostalAddress() {
        When("^the user deletes the last postal address added$",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactPostalAddressForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    deleteContactPostalAddressForm.setContactMechanismName(persona.lastPostalAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactPostalAddressForm);
                });

        When("^the user begins entering a new postal address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNull();

                    persona.contactPostalAddressEdit = ContactUtil.getHome().getContactPostalAddressEdit();
                });

        When("^the user sets the postal address's first name to \"([^\"]*)\"$",
                (String firstName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setFirstName(firstName);
                });

        When("^the user sets the postal address's last name to \"([^\"]*)\"$",
                (String lastName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setLastName(lastName);
                });

        When("^the user sets the postal address's line 1 to \"([^\"]*)\"$",
                (String address1) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setAddress1(address1);
                });

        When("^the user sets the postal address's line 2 to \"([^\"]*)\"$",
                (String address2) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setAddress2(address2);
                });

        When("^the user sets the postal address's line 3 to \"([^\"]*)\"$",
                (String address3) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setAddress3(address3);
                });

        When("^the user sets the postal address's city to \"([^\"]*)\"$",
                (String city) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setCity(city);
                });

        When("^the user sets the postal address's state to \"([^\"]*)\"$",
                (String state) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setState(state);
                });

        When("^the user sets the postal address's postal code to \"([^\"]*)\"$",
                (String postalCode) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setPostalCode(postalCode);
                });

        When("^the user sets the postal address's country to \"([^\"]*)\"$",
                (String country) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setCountryName(country);
                });

        When("^the user's postal address (is|is not) a commercial location$",
                (String isCommercial) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setIsCommercial(Boolean.valueOf(isCommercial.equals("is")).toString());
                });

        When("^the user (does|does not) allow solicitations to the postal address$",
                (String allowSolicitation) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the user sets the postal address's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    persona.contactPostalAddressEdit.setDescription(description);
                });

        When("^the user adds the new postal address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactPostalAddressForm = contactService.getCreateContactPostalAddressForm();

                    createContactPostalAddressForm.set(persona.contactPostalAddressEdit.get());

                    var commandResult = contactService.createContactPostalAddress(persona.userVisitPK, createContactPostalAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactPostalAddressResult)commandResult.getExecutionResult().getResult();

                    persona.lastPostalAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.contactPostalAddressEdit = null;
                });

        When("^the user begins editing the last postal address added$",
                () -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactPostalAddressEdit).isNull();

                    spec.setContactMechanismName(persona.lastPostalAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactPostalAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactPostalAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactPostalAddressResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactPostalAddressEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the postal address$",
                () -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(edit).isNotNull();

                    spec.setContactMechanismName(persona.lastPostalAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactPostalAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactPostalAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.contactPostalAddressEdit = null;
                });
    }

}
