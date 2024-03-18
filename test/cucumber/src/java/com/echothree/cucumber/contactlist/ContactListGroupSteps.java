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
import com.echothree.control.user.contactlist.common.result.CreateContactListGroupResult;
import com.echothree.control.user.contactlist.common.result.EditContactListGroupResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactListGroupSteps implements En {

    public ContactListGroupSteps() {
        When("^the user begins entering a new contact list group",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListGroupForm).isNull();
                    assertThat(persona.deleteContactListGroupForm).isNull();
                    assertThat(persona.contactListGroupSpec).isNull();

                    persona.createContactListGroupForm = ContactListUtil.getHome().getCreateContactListGroupForm();
                });

        When("^the user adds the new contact list group",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListGroupForm).isNotNull();

                    var contactListGroupService = ContactListUtil.getHome();
                    var createContactListGroupForm = contactListGroupService.getCreateContactListGroupForm();

                    createContactListGroupForm.set(persona.createContactListGroupForm.get());

                    var commandResult = contactListGroupService.createContactListGroup(persona.userVisitPK, createContactListGroupForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateContactListGroupResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastContactListGroupName = commandResult.getHasErrors() ? null : result.getContactListGroupName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createContactListGroupForm = null;
                });

        When("^the user begins deleting a contact list group",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListGroupForm).isNull();
                    assertThat(persona.deleteContactListGroupForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteContactListGroupForm = ContactListUtil.getHome().getDeleteContactListGroupForm();
                });

        When("^the user deletes the contact list group",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteContactListGroupForm = persona.deleteContactListGroupForm;

                    assertThat(deleteContactListGroupForm).isNotNull();

                    LastCommandResult.commandResult = ContactListUtil.getHome().deleteContactListGroup(persona.userVisitPK, deleteContactListGroupForm);

                    persona.deleteContactListGroupForm = null;
                });

        When("^the user begins specifying a contact list group to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContactListGroupForm).isNull();
                    assertThat(persona.deleteContactListGroupForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.contactListGroupSpec = ContactListUtil.getHome().getContactListGroupUniversalSpec();
                });

        When("^the user begins editing the contact list group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contactListGroupSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContactListUtil.getHome().getEditContactListGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactListUtil.getHome().editContactListGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditContactListGroupResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.contactListGroupEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the contact list group",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contactListGroupSpec;
                    var edit = persona.contactListGroupEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ContactListUtil.getHome().getEditContactListGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContactListUtil.getHome().editContactListGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.contactListGroupSpec = null;
                    persona.contactListGroupEdit = null;
                });


        When("^the user sets the contact list group's contact list group name to \"([^\"]*)\"$",
                (String contactListGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListGroupForm = persona.createContactListGroupForm;
                    var deleteContactListGroupForm = persona.deleteContactListGroupForm;
                    var contactListGroupSpec = persona.contactListGroupSpec;

                    assertThat(createContactListGroupForm != null || deleteContactListGroupForm != null || contactListGroupSpec != null).isTrue();

                    if(createContactListGroupForm != null) {
                        createContactListGroupForm.setContactListGroupName(contactListGroupName);
                    } else if(deleteContactListGroupForm != null) {
                        deleteContactListGroupForm.setContactListGroupName(contactListGroupName);
                    } else {
                        contactListGroupSpec.setContactListGroupName(contactListGroupName);
                    }
                });

        When("^the user sets the contact list group's new contact list group name to \"([^\"]*)\"$",
                (String contactListGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var contactListGroupEdit = persona.contactListGroupEdit;

                    assertThat(contactListGroupEdit).isNotNull();

                    contactListGroupEdit.setContactListGroupName(contactListGroupName);
                });

        When("^the user sets the contact list group to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListGroupForm = persona.createContactListGroupForm;
                    var contactListGroupEdit = persona.contactListGroupEdit;

                    assertThat(createContactListGroupForm != null || contactListGroupEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createContactListGroupForm != null) {
                        createContactListGroupForm.setIsDefault(isDefault);
                    } else {
                        contactListGroupEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the contact list group's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListGroupForm = persona.createContactListGroupForm;
                    var contactListGroupEdit = persona.contactListGroupEdit;

                    assertThat(createContactListGroupForm != null || contactListGroupEdit != null).isTrue();

                    if(createContactListGroupForm != null) {
                        createContactListGroupForm.setSortOrder(sortOrder);
                    } else {
                        contactListGroupEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the contact list group's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContactListGroupForm = persona.createContactListGroupForm;
                    var contactListGroupEdit = persona.contactListGroupEdit;

                    assertThat(createContactListGroupForm != null || contactListGroupEdit != null).isTrue();

                    if(createContactListGroupForm != null) {
                        createContactListGroupForm.setDescription(description);
                    } else {
                        contactListGroupEdit.setDescription(description);
                    }
                });
    }

}
