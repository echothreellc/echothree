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
import com.echothree.cucumber.util.LastCommandResultSteps;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyTelephoneSteps implements En {

    public PartyTelephoneSteps() {
        When("^the user deletes the last telephone number added$",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactTelephoneForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    deleteContactTelephoneForm.setContactMechanismName(persona.lastTelephoneContactMechanismName);

                    LastCommandResultSteps.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactTelephoneForm);
                });

        When("^the user begins entering a new telephone number$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNull();

                    persona.contactTelephoneEdit = ContactUtil.getHome().getContactTelephoneEdit();
                });

        When("^the user (does|does not) allow solicitations to the telephone number$",
                (String allowSolicitation) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNotNull();

                    persona.contactTelephoneEdit.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the user sets the telephone number's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNotNull();

                    persona.contactTelephoneEdit.setDescription(description);
                });

        When("^the user sets the telephone number's country to \"([^\"]*)\"$",
                (String countryName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNotNull();

                    persona.contactTelephoneEdit.setCountryName(countryName);
                });

        When("^the user sets the telephone number's area code to \"([^\"]*)\"$",
                (String areaCode) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNotNull();

                    persona.contactTelephoneEdit.setAreaCode(areaCode);
                });

        When("^the user sets the telephone number's number to \"([^\"]*)\"$",
                (String telephoneNumber) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNotNull();

                    persona.contactTelephoneEdit.setTelephoneNumber(telephoneNumber);
                });

        When("^the user sets the telephone number's extension to \"([^\"]*)\"$",
                (String extension) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNotNull();

                    persona.contactTelephoneEdit.setTelephoneExtension(extension);
                });

        When("^the user adds the new telephone number$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNotNull();

                    var contactService = ContactUtil.getHome();
                    var createContactTelephoneForm = contactService.getCreateContactTelephoneForm();

                    createContactTelephoneForm.set(persona.contactTelephoneEdit.get());

                    var commandResult = contactService.createContactTelephone(persona.userVisitPK, createContactTelephoneForm);

                    LastCommandResultSteps.commandResult = commandResult;
                    var result = (CreateContactTelephoneResult)commandResult.getExecutionResult().getResult();

                    persona.lastTelephoneContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.contactTelephoneEdit = null;
                });

        When("^the user begins editing the last telephone number added$",
                () -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.contactTelephoneEdit).isNull();

                    spec.setContactMechanismName(persona.lastTelephoneContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactTelephone(persona.userVisitPK, commandForm);
                    LastCommandResultSteps.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactTelephoneResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactTelephoneEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the telephone number$",
                () -> {
                    var spec = ContactUtil.getHome().getPartyContactMechanismSpec();
                    var persona = CurrentPersona.persona;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(edit).isNotNull();

                    spec.setContactMechanismName(persona.lastTelephoneContactMechanismName);

                    var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactTelephone(persona.userVisitPK, commandForm);
                    LastCommandResultSteps.commandResult = commandResult;

                    persona.contactTelephoneEdit = null;
                });
    }

}
