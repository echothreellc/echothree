// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.cucumber.contactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.result.CreateContactListFrequencyResult;
import com.echothree.control.user.contactlist.common.result.EditContactListFrequencyResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactListFrequencySteps implements En {

    public ContactListFrequencySteps() {
        When("^the user begins entering a new contact list frequency",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListFrequencyForm).isNull();
                    assertThat(persona.deleteContactListFrequencyForm).isNull();
                    assertThat(persona.contactListFrequencySpec).isNull();

                    persona.createContactListFrequencyForm = ContactListUtil.getHome().getCreateContactListFrequencyForm();
                });

        When("^the user adds the new contact list frequency",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListFrequencyForm).isNotNull();

                    var contactListFrequencyService = ContactListUtil.getHome();
                    var createContactListFrequencyForm = contactListFrequencyService.getCreateContactListFrequencyForm();

                    createContactListFrequencyForm.set(persona.createContactListFrequencyForm.get());

                    var commandResult = contactListFrequencyService.createContactListFrequency(persona.userVisitPK, createContactListFrequencyForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactListFrequencyResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastContactListFrequencyName = commandResult.getHasErrors() ? null : result.getContactListFrequencyName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createContactListFrequencyForm = null;
                });

        When("^the user begins deleting a contact list frequency",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListFrequencyForm).isNull();
                    assertThat(persona.deleteContactListFrequencyForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.deleteContactListFrequencyForm = ContactListUtil.getHome().getDeleteContactListFrequencyForm();
                });

        When("^the user deletes the contact list frequency",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteContactListFrequencyForm = persona.deleteContactListFrequencyForm;

                    assertThat(deleteContactListFrequencyForm).isNotNull();

                    LastCommandResult.commandResult = ContactListUtil.getHome().deleteContactListFrequency(persona.userVisitPK, deleteContactListFrequencyForm);

                    persona.deleteContactListFrequencyForm = null;
                });

        When("^the user begins specifying a contact list frequency to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListFrequencyForm).isNull();
                    assertThat(persona.deleteContactListFrequencyForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.contactListFrequencySpec = ContactListUtil.getHome().getContactListFrequencyUniversalSpec();
                });

        When("^the user begins editing the contact list frequency$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contactListFrequencySpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContactListUtil.getHome().getEditContactListFrequencyForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactListUtil.getHome().editContactListFrequency(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactListFrequencyResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactListFrequencyEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the contact list frequency",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contactListFrequencySpec;
                    var edit = persona.contactListFrequencyEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ContactListUtil.getHome().getEditContactListFrequencyForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactListUtil.getHome().editContactListFrequency(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.contactListFrequencySpec = null;
                    persona.contactListFrequencyEdit = null;
                });


        When("^the user sets the contact list frequency's contact list frequency name to \"([^\"]*)\"$",
                (String contactListFrequencyName) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListFrequencyForm = persona.createContactListFrequencyForm;
                    var deleteContactListFrequencyForm = persona.deleteContactListFrequencyForm;
                    var contactListFrequencySpec = persona.contactListFrequencySpec;

                    assertThat(createContactListFrequencyForm != null || deleteContactListFrequencyForm != null || contactListFrequencySpec != null).isTrue();

                    if(createContactListFrequencyForm != null) {
                        createContactListFrequencyForm.setContactListFrequencyName(contactListFrequencyName);
                    } else if(deleteContactListFrequencyForm != null) {
                        deleteContactListFrequencyForm.setContactListFrequencyName(contactListFrequencyName);
                    } else {
                        contactListFrequencySpec.setContactListFrequencyName(contactListFrequencyName);
                    }
                });

        When("^the user sets the contact list frequency's new contact list frequency name to \"([^\"]*)\"$",
                (String contactListFrequencyName) -> {
                    var persona = CurrentPersona.persona;
                    var contactListFrequencyEdit = persona.contactListFrequencyEdit;

                    assertThat(contactListFrequencyEdit).isNotNull();

                    contactListFrequencyEdit.setContactListFrequencyName(contactListFrequencyName);
                });

        When("^the user sets the contact list frequency to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListFrequencyForm = persona.createContactListFrequencyForm;
                    var contactListFrequencyEdit = persona.contactListFrequencyEdit;

                    assertThat(createContactListFrequencyForm != null || contactListFrequencyEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createContactListFrequencyForm != null) {
                        createContactListFrequencyForm.setIsDefault(isDefault);
                    } else {
                        contactListFrequencyEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the contact list frequency's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListFrequencyForm = persona.createContactListFrequencyForm;
                    var contactListFrequencyEdit = persona.contactListFrequencyEdit;

                    assertThat(createContactListFrequencyForm != null || contactListFrequencyEdit != null).isTrue();

                    if(createContactListFrequencyForm != null) {
                        createContactListFrequencyForm.setSortOrder(sortOrder);
                    } else {
                        contactListFrequencyEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the contact list frequency's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListFrequencyForm = persona.createContactListFrequencyForm;
                    var contactListFrequencyEdit = persona.contactListFrequencyEdit;

                    assertThat(createContactListFrequencyForm != null || contactListFrequencyEdit != null).isTrue();

                    if(createContactListFrequencyForm != null) {
                        createContactListFrequencyForm.setDescription(description);
                    } else {
                        contactListFrequencyEdit.setDescription(description);
                    }
                });
    }

}
