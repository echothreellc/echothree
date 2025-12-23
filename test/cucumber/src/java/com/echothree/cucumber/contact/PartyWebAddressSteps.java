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
import com.echothree.control.user.contact.common.result.CreateContactWebAddressResult;
import com.echothree.control.user.contact.common.result.EditContactWebAddressResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyWebAddressSteps implements En {

    public PartyWebAddressSteps() {
        When("^the user begins entering a new web address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactWebAddressForm).isNull();
                    assertThat(persona.contactWebAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.createContactWebAddressForm = ContactUtil.getHome().getCreateContactWebAddressForm();
                });

        When("^the user adds the new web address$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContactWebAddressForm = persona.createContactWebAddressForm;

                    assertThat(createContactWebAddressForm).isNotNull();

                    var commandResult = ContactUtil.getHome().createContactWebAddress(persona.userVisitPK, createContactWebAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactWebAddressResult)commandResult.getExecutionResult().getResult();

                    persona.lastWebAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.createContactWebAddressForm = null;
                });

        When("^the user deletes the last web address added$",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactWebAddressForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactWebAddressForm).isNull();
                    assertThat(persona.contactWebAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    deleteContactWebAddressForm.setContactMechanismName(persona.lastWebAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactWebAddressForm);
                });

        When("^the user begins specifying a web address to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactWebAddressForm).isNull();
                    assertThat(persona.contactWebAddressEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.partyContactMechanismSpec = ContactUtil.getHome().getPartyContactMechanismSpec();
                });

        When("^the user begins editing the web address$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactWebAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactWebAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactWebAddressResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactWebAddressEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the web address$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;
                    var edit = persona.contactWebAddressEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactWebAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactWebAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.partyContactMechanismSpec = null;
                    persona.contactWebAddressEdit = null;
                });

        When("^the user sets the web address's party to the last party added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;
                    var createContactWebAddressForm = persona.createContactWebAddressForm;

                    assertThat(partyContactMechanismSpec != null || createContactWebAddressForm != null).isTrue();

                    var lastPartyName = persona.lastPartyName;
                    if(partyContactMechanismSpec != null) {
                        partyContactMechanismSpec.setPartyName(lastPartyName);
                    } else {
                        createContactWebAddressForm.setPartyName(lastPartyName);
                    }
                });

        When("^the user sets the web address's contact mechanism to the last web address added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;

                    assertThat(partyContactMechanismSpec).isNotNull();

                    partyContactMechanismSpec.setContactMechanismName(persona.lastWebAddressContactMechanismName);
                });

        When("^the user sets the web address's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContactWebAddressForm = persona.createContactWebAddressForm;
                    var edit = persona.contactWebAddressEdit;

                    assertThat(createContactWebAddressForm != null || edit != null).isTrue();

                    if(createContactWebAddressForm != null) {
                        createContactWebAddressForm.setDescription(description);
                    } else {
                        edit.setDescription(description);
                    }
                });

        When("^the user sets the web address's url to \"([^\"]*)\"$",
                (String url) -> {
                    var persona = CurrentPersona.persona;
                    var createContactWebAddressForm = persona.createContactWebAddressForm;
                    var edit = persona.contactWebAddressEdit;

                    assertThat(createContactWebAddressForm != null || edit != null).isTrue();

                    if(createContactWebAddressForm != null) {
                        createContactWebAddressForm.setUrl(url);
                    } else {
                        edit.setUrl(url);
                    }
                });
    }

}
