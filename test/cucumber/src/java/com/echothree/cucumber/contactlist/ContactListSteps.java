// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.control.user.contactlist.common.result.CreateContactListResult;
import com.echothree.control.user.contactlist.common.result.EditContactListResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactListSteps implements En {

    public ContactListSteps() {
        When("^the user begins entering a new contact list",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListForm).isNull();
                    assertThat(persona.deleteContactListForm).isNull();
                    assertThat(persona.contactListSpec).isNull();

                    persona.createContactListForm = ContactListUtil.getHome().getCreateContactListForm();
                });

        When("^the user adds the new contact list",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListForm).isNotNull();

                    var contactListService = ContactListUtil.getHome();
                    var createContactListForm = contactListService.getCreateContactListForm();

                    createContactListForm.set(persona.createContactListForm.get());

                    var commandResult = contactListService.createContactList(persona.userVisitPK, createContactListForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactListResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastContactListName = commandResult.getHasErrors() ? null : result.getContactListName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createContactListForm = null;
                });

        When("^the user begins deleting an contact list",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListForm).isNull();
                    assertThat(persona.deleteContactListForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteContactListForm = ContactListUtil.getHome().getDeleteContactListForm();
                });

        When("^the user deletes the contact list",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteContactListForm = persona.deleteContactListForm;

                    assertThat(deleteContactListForm).isNotNull();

                    LastCommandResult.commandResult = ContactListUtil.getHome().deleteContactList(persona.userVisitPK, deleteContactListForm);

                    persona.deleteContactListForm = null;
                });

        When("^the user begins specifying an contact list to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListForm).isNull();
                    assertThat(persona.deleteContactListForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.contactListSpec = ContactListUtil.getHome().getContactListUniversalSpec();
                });

        When("^the user begins editing the contact list$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contactListSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContactListUtil.getHome().getEditContactListForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactListUtil.getHome().editContactList(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactListResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactListEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the contact list",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contactListSpec;
                    var edit = persona.contactListEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ContactListUtil.getHome().getEditContactListForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactListUtil.getHome().editContactList(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.contactListSpec = null;
                    persona.contactListEdit = null;
                });


        When("^the user sets the contact list's contact list name to \"([^\"]*)\"$",
                (String contactListName) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListForm = persona.createContactListForm;
                    var deleteContactListForm = persona.deleteContactListForm;
                    var contactListSpec = persona.contactListSpec;

                    assertThat(createContactListForm != null || deleteContactListForm != null || contactListSpec != null).isTrue();

                    if(createContactListForm != null) {
                        createContactListForm.setContactListName(contactListName);
                    } else if(deleteContactListForm != null) {
                        deleteContactListForm.setContactListName(contactListName);
                    } else {
                        contactListSpec.setContactListName(contactListName);
                    }
                });

        When("^the user sets the contact list's new contact list name to \"([^\"]*)\"$",
                (String contactListName) -> {
                    var persona = CurrentPersona.persona;
                    var contactListEdit = persona.contactListEdit;

                    assertThat(contactListEdit).isNotNull();

                    contactListEdit.setContactListName(contactListName);
                });

        When("^the user sets the contact list to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListForm = persona.createContactListForm;
                    var contactListEdit = persona.contactListEdit;

                    assertThat(createContactListForm != null || contactListEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createContactListForm != null) {
                        createContactListForm.setIsDefault(isDefault);
                    } else {
                        contactListEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the contact list's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListForm = persona.createContactListForm;
                    var contactListEdit = persona.contactListEdit;

                    assertThat(createContactListForm != null || contactListEdit != null).isTrue();

                    if(createContactListForm != null) {
                        createContactListForm.setSortOrder(sortOrder);
                    } else {
                        contactListEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the contact list's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListForm = persona.createContactListForm;
                    var contactListEdit = persona.contactListEdit;

                    assertThat(createContactListForm != null || contactListEdit != null).isTrue();

                    if(createContactListForm != null) {
                        createContactListForm.setDescription(description);
                    } else {
                        contactListEdit.setDescription(description);
                    }
                });
    }

}
