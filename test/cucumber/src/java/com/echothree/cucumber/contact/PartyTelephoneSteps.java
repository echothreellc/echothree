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
import com.echothree.control.user.contact.common.result.CreateContactTelephoneResult;
import com.echothree.control.user.contact.common.result.EditContactTelephoneResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyTelephoneSteps implements En {

    public PartyTelephoneSteps() {
        When("^the user begins entering a new telephone number$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactTelephoneForm).isNull();
                    assertThat(persona.contactTelephoneEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.createContactTelephoneForm = ContactUtil.getHome().getCreateContactTelephoneForm();
                });

        When("^the user adds the new telephone number$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;

                    assertThat(createContactTelephoneForm).isNotNull();

                    var commandResult = ContactUtil.getHome().createContactTelephone(persona.userVisitPK, createContactTelephoneForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactTelephoneResult)commandResult.getExecutionResult().getResult();

                    persona.lastTelephoneContactMechanismName = commandResult.getHasErrors() ? null : result.getContactMechanismName();
                    persona.createContactTelephoneForm = null;
                });

        When("^the user deletes the last telephone number added$",
                () -> {
                    var contactService = ContactUtil.getHome();
                    var deleteContactTelephoneForm = contactService.getDeleteContactMechanismForm();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactTelephoneForm).isNull();
                    assertThat(persona.contactTelephoneEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    deleteContactTelephoneForm.setContactMechanismName(persona.lastTelephoneContactMechanismName);

                    LastCommandResult.commandResult = contactService.deleteContactMechanism(persona.userVisitPK, deleteContactTelephoneForm);
                });

        When("^the user begins specifying a telephone number to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactTelephoneForm).isNull();
                    assertThat(persona.contactTelephoneEdit).isNull();
                    assertThat(persona.partyContactMechanismSpec).isNull();

                    persona.partyContactMechanismSpec = ContactUtil.getHome().getPartyContactMechanismSpec();
                });

        When("^the user begins editing the telephone number$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editContactTelephone(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactTelephoneResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactTelephoneEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the telephone number$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyContactMechanismSpec;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = ContactUtil.getHome().getEditContactTelephoneForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactUtil.getHome().editContactTelephone(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.partyContactMechanismSpec = null;
                    persona.contactTelephoneEdit = null;
                });

        When("^the user sets the telephone number's party to the last party added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;

                    assertThat(partyContactMechanismSpec != null || createContactTelephoneForm != null).isTrue();

                    var lastPartyName = persona.lastPartyName;
                    if(partyContactMechanismSpec != null) {
                        partyContactMechanismSpec.setPartyName(lastPartyName);
                    } else {
                        createContactTelephoneForm.setPartyName(lastPartyName);
                    }
                });

        When("^the user sets the telephone number's contact mechanism to the last telephone number added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var partyContactMechanismSpec = persona.partyContactMechanismSpec;

                    assertThat(partyContactMechanismSpec).isNotNull();

                    partyContactMechanismSpec.setContactMechanismName(persona.lastTelephoneContactMechanismName);
                });

        When("^the user (does|does not) allow solicitations to the telephone number$",
                (String allowSolicitation) -> {
                    var persona = CurrentPersona.persona;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(createContactTelephoneForm != null || edit != null).isTrue();

                    allowSolicitation = Boolean.valueOf(allowSolicitation.equals("does")).toString();
                    if(createContactTelephoneForm != null) {
                        createContactTelephoneForm.setAllowSolicitation(allowSolicitation);
                    } else {
                        edit.setAllowSolicitation(allowSolicitation);
                    }
                });

        When("^the user sets the telephone number's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(createContactTelephoneForm != null || edit != null).isTrue();

                    if(createContactTelephoneForm != null) {
                        createContactTelephoneForm.setDescription(description);
                    } else {
                        edit.setDescription(description);
                    }
                });

        When("^the user sets the telephone number's country to \"([^\"]*)\"$",
                (String countryName) -> {
                    var persona = CurrentPersona.persona;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(createContactTelephoneForm != null || edit != null).isTrue();

                    if(createContactTelephoneForm != null) {
                        createContactTelephoneForm.setCountryName(countryName);
                    } else {
                        edit.setCountryName(countryName);
                    }
                });

        When("^the user sets the telephone number's area code to \"([^\"]*)\"$",
                (String areaCode) -> {
                    var persona = CurrentPersona.persona;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(createContactTelephoneForm != null || edit != null).isTrue();

                    if(createContactTelephoneForm != null) {
                        createContactTelephoneForm.setAreaCode(areaCode);
                    } else {
                        edit.setAreaCode(areaCode);
                    }
                });

        When("^the user sets the telephone number's number to \"([^\"]*)\"$",
                (String telephoneNumber) -> {
                    var persona = CurrentPersona.persona;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(createContactTelephoneForm != null || edit != null).isTrue();

                    if(createContactTelephoneForm != null) {
                        createContactTelephoneForm.setTelephoneNumber(telephoneNumber);
                    } else {
                        edit.setTelephoneNumber(telephoneNumber);
                    }
                });

        When("^the user sets the telephone number's extension to \"([^\"]*)\"$",
                (String telephoneExtension) -> {
                    var persona = CurrentPersona.persona;
                    var createContactTelephoneForm = persona.createContactTelephoneForm;
                    var edit = persona.contactTelephoneEdit;

                    assertThat(createContactTelephoneForm != null || edit != null).isTrue();

                    if(createContactTelephoneForm != null) {
                        createContactTelephoneForm.setTelephoneExtension(telephoneExtension);
                    } else {
                        edit.setTelephoneExtension(telephoneExtension);
                    }
                });
    }

}
