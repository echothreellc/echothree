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
import com.echothree.control.user.contact.common.result.CreateContactEmailAddressResult;
import com.echothree.control.user.contact.common.result.EditContactEmailAddressResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyEmailAddressSteps implements En {

    public PartyEmailAddressSteps() {
        When("^the user begins entering a new email address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactEmailAddressForm).isNull();
                    assertThat(persona.contactEmailAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.createContactEmailAddressForm = ContactUtil.getHome().getCreateContactEmailAddressForm();
                });

        When("^the user adds the new email address$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContactEmailAddressForm = persona.createContactEmailAddressForm;

                    assertThat(createContactEmailAddressForm).isNotNull();

                    var commandResult = ContactUtil.getHome().createContactEmailAddress(persona.userVisitPK, createContactEmailAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactEmailAddressResult)commandResult.getExecutionResult().getResult();

                    persona.lastEmailAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.createContactEmailAddressForm = null;
                });

        When("^the user deletes the last email address added$",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactEmailAddressForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactEmailAddressForm).isNull();
                    assertThat(persona.contactEmailAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    deleteContactEmailAddressForm.setContactMechanismName(persona.lastEmailAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactEmailAddressForm);
                });

        When("^the user begins specifying an email address to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactEmailAddressForm).isNull();
                    assertThat(persona.contactEmailAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.partyContactMechanismSpec = ContactUtil.getHome().getPartyContactMechanismSpec();
                });

        When("^the user begins editing the email address$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactEmailAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactEmailAddressResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactEmailAddressEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the email address$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;
                    var edit = persona.contactEmailAddressEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactEmailAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.partyContactMechanismSpec = null;
                    persona.contactEmailAddressEdit = null;
                });

        When("^the user sets the email address's party to the last party added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;
                    var createContactEmailAddressForm = persona.createContactEmailAddressForm;

                    assertThat(partyContactMechanismSpec != null || createContactEmailAddressForm != null).isTrue();

                    var lastPartyName = persona.lastPartyName;
                    if(partyContactMechanismSpec != null) {
                        partyContactMechanismSpec.setPartyName(lastPartyName);
                    } else {
                        createContactEmailAddressForm.setPartyName(lastPartyName);
                    }
                });

        When("^the user sets the email address's contact mechanism to the last email address added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;

                    assertThat(partyContactMechanismSpec).isNotNull();

                    partyContactMechanismSpec.setContactMechanismName(persona.lastEmailAddressContactMechanismName);
                });

        When("^the user (does|does not) allow solicitations to the email address$",
                (String allowSolicitation) -> {
                    var persona = CurrentPersona.persona;
                    var createContactEmailAddressForm = persona.createContactEmailAddressForm;
                    var edit = persona.contactEmailAddressEdit;

                    assertThat(createContactEmailAddressForm != null || edit != null).isTrue();

                    allowSolicitation = Boolean.valueOf(allowSolicitation.equals("does")).toString();
                    if(createContactEmailAddressForm != null) {
                        createContactEmailAddressForm.setAllowSolicitation(allowSolicitation);
                    } else {
                        edit.setAllowSolicitation(allowSolicitation);
                    }
                });

        When("^the user sets the email address's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContactEmailAddressForm = persona.createContactEmailAddressForm;
                    var edit = persona.contactEmailAddressEdit;

                    assertThat(createContactEmailAddressForm != null || edit != null).isTrue();

                    if(createContactEmailAddressForm != null) {
                        createContactEmailAddressForm.setDescription(description);
                    } else {
                        edit.setDescription(description);
                    }
                });

        When("^the user sets the email address's email address to \"([^\"]*)\"$",
                (String emailAddress) -> {
                    var persona = CurrentPersona.persona;
                    var createContactEmailAddressForm = persona.createContactEmailAddressForm;
                    var edit = persona.contactEmailAddressEdit;

                    assertThat(createContactEmailAddressForm != null || edit != null).isTrue();

                    if(createContactEmailAddressForm != null) {
                        createContactEmailAddressForm.setEmailAddress(emailAddress);
                    } else {
                        edit.setEmailAddress(emailAddress);
                    }
                });
    }

}
