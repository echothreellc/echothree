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
import com.echothree.control.user.contact.common.result.CreateContactEmailAddressResult;
import com.echothree.control.user.contact.common.result.EditContactEmailAddressResult;
import com.echothree.cucumber.util.LastCommandResultSteps;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyEmailAddressSteps implements En {

    public PartyEmailAddressSteps() {
        When("^the user deletes the last email address added$",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactEmailAddressForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    deleteContactEmailAddressForm.setContactMechanismName(persona.lastEmailAddressContactMechanismName);

                    LastCommandResultSteps.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactEmailAddressForm);
                });

        When("^the user begins entering a new email address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactEmailAddressEdit).isNull();

                    persona.contactEmailAddressEdit = ContactUtil.getHome().getContactEmailAddressEdit();
                });

        When("^the user (does|does not) allow solicitations to the email address$",
                (String allowSolicitation) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactEmailAddressEdit).isNotNull();

                    persona.contactEmailAddressEdit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the user sets the email address's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactEmailAddressEdit).isNotNull();

                    persona.contactEmailAddressEdit.setDescription(description);
                });

        When("^the user sets the email address's email address to \"([^\"]*)\"$",
                (String emailAddress) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactEmailAddressEdit).isNotNull();

                    persona.contactEmailAddressEdit.setEmailAddress(emailAddress);
                });

        When("^the user adds the new email address$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactEmailAddressEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactEmailAddressForm = contactService.getCreateContactEmailAddressForm();

                    createContactEmailAddressForm.set(persona.contactEmailAddressEdit.get());

                    var commandResult = contactService.createContactEmailAddress(persona.userVisitPK, createContactEmailAddressForm);

                    LastCommandResultSteps.commandResult = commandResult;
                    var result = (CreateContactEmailAddressResult)commandResult.getExecutionResult().getResult();

                    persona.lastEmailAddressContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.contactEmailAddressEdit = null;
                });

        When("^the user begins editing the last email address added$",
                () -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactEmailAddressEdit).isNull();

                    spec.setContactMechanismName(persona.lastEmailAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactEmailAddress(persona.userVisitPK, commandForm);
                    LastCommandResultSteps.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactEmailAddressResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactEmailAddressEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the email address$",
                () -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;
                    var edit = persona.contactEmailAddressEdit;

                    assertThat(edit).isNotNull();

                    spec.setContactMechanismName(persona.lastEmailAddressContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactEmailAddressForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactEmailAddress(persona.userVisitPK, commandForm);
                    LastCommandResultSteps.commandResult = commandResult;

                    persona.contactEmailAddressEdit = null;
                });
    }

}
