// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyPostalAddressSteps implements En {

    public PartyPostalAddressSteps() {
        When("the user begins entering a new postal address",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactPostalAddressForm).isNull();
                    assertThat(persona.contactPostalAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.createContactPostalAddressForm = ContactUtil.getHome().getCreateContactPostalAddressForm();
                });

        When("the user adds the new postal address",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;

                    assertThat(createContactPostalAddressForm).isNotNull();

                    var commandResult = ContactUtil.getHome().createContactPostalAddress(persona.userVisitPK, createContactPostalAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    persona.lastPostalAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.createContactPostalAddressForm = null;
                });

        When("the user deletes the last postal address added",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactPostalAddressForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactPostalAddressForm).isNull();
                    assertThat(persona.contactPostalAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    deleteContactPostalAddressForm.setContactMechanismName(persona.lastPostalAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactPostalAddressForm);
                });

        When("the user begins specifying a postal address to edit",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactPostalAddressForm).isNull();
                    assertThat(persona.contactPostalAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.partyContactMechanismSpec = ContactUtil.getHome().getPartyContactMechanismSpec();
                });

        When("the user begins editing the postal address",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactPostalAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactPostalAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactPostalAddressEdit = result.getEdit();
                    }
                });

        When("the user finishes editing the postal address",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactPostalAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactPostalAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.partyContactMechanismSpec = null;
                    persona.contactPostalAddressEdit = null;
                });

        When("the user sets the postal address's party to the last party added",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;

                    assertThat(partyContactMechanismSpec != null || createContactPostalAddressForm != null).isTrue();

                    var lastPartyName = persona.lastPartyName;
                    if(partyContactMechanismSpec != null) {
                        partyContactMechanismSpec.setPartyName(lastPartyName);
                    } else {
                        createContactPostalAddressForm.setPartyName(lastPartyName);
                    }
                });

        When("the user sets the postal address's contact mechanism to the last postal address added",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;

                    assertThat(partyContactMechanismSpec).isNotNull();

                    partyContactMechanismSpec.setContactMechanismName(persona.lastPostalAddressContactMechanismName);
                });

        When("the user sets the postal address's first name to {string}",
                (String firstName) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setFirstName(firstName);
                    } else {
                        edit.setFirstName(firstName);
                    }
                });

        When("the user sets the postal address's last name to {string}",
                (String lastName) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setLastName(lastName);
                    } else {
                        edit.setLastName(lastName);
                    }
                });

        When("the user sets the postal address's company name to {string}",
                (String companyName) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setCompanyName(companyName);
                    } else {
                        edit.setCompanyName(companyName);
                    }
                });

        When("the user sets the postal address's line 1 to {string}",
                (String address1) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setAddress1(address1);
                    } else {
                        edit.setAddress1(address1);
                    }
                });

        When("the user sets the postal address's line 2 to {string}",
                (String address2) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setAddress2(address2);
                    } else {
                        edit.setAddress2(address2);
                    }
                });

        When("the user sets the postal address's line 3 to {string}",
                (String address3) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setAddress3(address3);
                    } else {
                        edit.setAddress3(address3);
                    }
                });

        When("the user sets the postal address's city to {string}",
                (String city) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setCity(city);
                    } else {
                        edit.setCity(city);
                    }
                });

        When("the user sets the postal address's state to {string}",
                (String state) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setState(state);
                    } else {
                        edit.setState(state);
                    }
                });

        When("the user sets the postal address's postal code to {string}",
                (String postalCode) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setPostalCode(postalCode);
                    } else {
                        edit.setPostalCode(postalCode);
                    }
                });

        When("the user sets the postal address's country to {string}",
                (String country) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setCountryName(country);
                    } else {
                        edit.setCountryName(country);
                    }
                });

        When("the user's postal address {string} a commercial location",
                (String isCommercial) -> {
                    assertThat(isCommercial).isIn("is", "is not");

                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    isCommercial = Boolean.valueOf(isCommercial.equals("is")).toString();
                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setIsCommercial(isCommercial);
                    } else {
                        edit.setIsCommercial(isCommercial);
                    }
                });

        When("the user {string} allow solicitations to the postal address",
                (String allowSolicitation) -> {
                    assertThat(allowSolicitation).isIn("does", "does not");

                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    allowSolicitation = Boolean.valueOf(allowSolicitation.equals("does")).toString();
                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setAllowSolicitation(allowSolicitation);
                    } else {
                        edit.setAllowSolicitation(allowSolicitation);
                    }
                });

        When("the user sets the postal address's description to {string}",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContactPostalAddressForm = persona.createContactPostalAddressForm;
                    var edit = persona.contactPostalAddressEdit;

                    assertThat(createContactPostalAddressForm != null || edit != null).isTrue();

                    if(createContactPostalAddressForm != null) {
                        createContactPostalAddressForm.setDescription(description);
                    } else {
                        edit.setDescription(description);
                    }
                });
    }

}
