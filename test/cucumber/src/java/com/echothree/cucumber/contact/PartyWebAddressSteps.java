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
import com.echothree.control.user.contact.common.result.CreateContactWebAddressResult;
import com.echothree.control.user.contact.common.result.EditContactWebAddressResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyWebAddressSteps implements En {

    public PartyWebAddressSteps() {
        When("^the user deletes the last web address added$",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactWebAddressForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    deleteContactWebAddressForm.setContactMechanismName(persona.lastWebAddressContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactWebAddressForm);
                });

        When("^the user begins entering a new web address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactWebAddressEdit).isNull();

                    persona.contactWebAddressEdit = ContactUtil.getHome().getContactWebAddressEdit();
                });

        When("^the user sets the web address's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactWebAddressEdit).isNotNull();

                    persona.contactWebAddressEdit.setDescription(description);
                });

        When("^the user sets the web address's url to \"([^\"]*)\"$",
                (String url) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactWebAddressEdit).isNotNull();

                    persona.contactWebAddressEdit.setUrl(url);
                });

        When("^the user adds the new web address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactWebAddressEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactWebAddressForm = contactService.getCreateContactWebAddressForm();

                    createContactWebAddressForm.set(persona.contactWebAddressEdit.get());

                    var commandResult = contactService.createContactWebAddress(persona.userVisitPK, createContactWebAddressForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactWebAddressResult)commandResult.getExecutionResult().getResult();

                    persona.lastWebAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.contactWebAddressEdit = null;
                });

        When("^the user begins editing the last web address added$",
                () -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactWebAddressEdit).isNull();

                    spec.setContactMechanismName(persona.lastWebAddressContactMechanismName);

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
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;
                    var edit = persona.contactWebAddressEdit;

                    assertThat(edit).isNotNull();

                    spec.setContactMechanismName(persona.lastWebAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactWebAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactWebAddress(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.contactWebAddressEdit = null;
                });
    }

}
