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

package com.echothree.cucumber.message;

import com.echothree.control.user.message.common.MessageUtil;
import com.echothree.control.user.message.common.result.CreateMessageTypeResult;
import com.echothree.control.user.message.common.result.EditMessageTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageTypeSteps implements En {

    public MessageTypeSteps() {
        When("^the user begins entering a new message type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createMessageTypeForm).isNull();
                    assertThat(persona.deleteMessageTypeForm).isNull();
                    assertThat(persona.messageTypeSpec).isNull();

                    persona.createMessageTypeForm = MessageUtil.getHome().getCreateMessageTypeForm();
                });

        When("^the user adds the new message type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createMessageTypeForm = persona.createMessageTypeForm;

                    assertThat(createMessageTypeForm).isNotNull();

                    var commandResult = MessageUtil.getHome().createMessageType(persona.userVisitPK, createMessageTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateMessageTypeResult)executionResult.getResult();
                    persona.lastMessageTypeName = result.getMessageTypeName();

                    persona.createMessageTypeForm = null;
                });

        When("^the user begins deleting a message type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createMessageTypeForm).isNull();
                    assertThat(persona.deleteMessageTypeForm).isNull();
                    assertThat(persona.messageTypeSpec).isNull();

                    persona.deleteMessageTypeForm = MessageUtil.getHome().getDeleteMessageTypeForm();
                });

        When("^the user deletes the message type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteMessageTypeForm = persona.deleteMessageTypeForm;

                    assertThat(deleteMessageTypeForm).isNotNull();

                    LastCommandResult.commandResult = MessageUtil.getHome().deleteMessageType(persona.userVisitPK, deleteMessageTypeForm);

                    persona.deleteMessageTypeForm = null;
                });

        When("^the user begins specifying a message type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createMessageTypeForm).isNull();
                    assertThat(persona.deleteMessageTypeForm).isNull();
                    assertThat(persona.messageTypeSpec).isNull();

                    persona.messageTypeSpec = MessageUtil.getHome().getMessageTypeSpec();
                });

        When("^the user begins editing the message type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.messageTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = MessageUtil.getHome().getEditMessageTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = MessageUtil.getHome().editMessageType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditMessageTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.messageTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the message type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.messageTypeSpec;
                    var edit = persona.messageTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = MessageUtil.getHome().getEditMessageTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = MessageUtil.getHome().editMessageType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.messageTypeSpec = null;
                    persona.messageTypeEdit = null;
                });
        
        When("^the user sets the message type's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createMessageTypeForm = persona.createMessageTypeForm;
                    var deleteMessageTypeForm = persona.deleteMessageTypeForm;
                    var messageTypeSpec = persona.messageTypeSpec;

                    assertThat(createMessageTypeForm != null || deleteMessageTypeForm != null || messageTypeSpec != null).isTrue();

                    if(createMessageTypeForm != null) {
                        createMessageTypeForm.setComponentVendorName(componentVendorName);
                    } else if(deleteMessageTypeForm != null) {
                        deleteMessageTypeForm.setComponentVendorName(componentVendorName);
                    } else {
                        messageTypeSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the message type's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createMessageTypeForm = persona.createMessageTypeForm;
                    var deleteMessageTypeForm = persona.deleteMessageTypeForm;
                    var messageTypeSpec = persona.messageTypeSpec;

                    assertThat(createMessageTypeForm != null || deleteMessageTypeForm != null || messageTypeSpec != null).isTrue();

                    if(createMessageTypeForm != null) {
                        createMessageTypeForm.setEntityTypeName(entityTypeName);
                    } else if(deleteMessageTypeForm != null) {
                        deleteMessageTypeForm.setEntityTypeName(entityTypeName);
                    } else {
                        messageTypeSpec.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the message type's message type to ([a-zA-Z0-9-_]*)$",
                (String messageTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createMessageTypeForm = persona.createMessageTypeForm;
                    var deleteMessageTypeForm = persona.deleteMessageTypeForm;
                    var messageTypeSpec = persona.messageTypeSpec;

                    assertThat(createMessageTypeForm != null || deleteMessageTypeForm != null || messageTypeSpec != null).isTrue();

                    if(createMessageTypeForm != null) {
                        createMessageTypeForm.setMessageTypeName(messageTypeName);
                    } else if(deleteMessageTypeForm != null) {
                        deleteMessageTypeForm.setMessageTypeName(messageTypeName);
                    } else {
                        messageTypeSpec.setMessageTypeName(messageTypeName);
                    }
                });

        When("^the user sets the message type's mime type usage type to ([a-zA-Z0-9-_]*)$",
                (String mimeTypeUsageTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createMessageTypeForm = persona.createMessageTypeForm;

                    assertThat(createMessageTypeForm != null).isTrue();

                    createMessageTypeForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);
                });

        When("^the user sets the message type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createMessageTypeForm = persona.createMessageTypeForm;
                    var messageTypeEdit = persona.messageTypeEdit;

                    assertThat(createMessageTypeForm != null || messageTypeEdit != null).isTrue();

                    if(createMessageTypeForm != null) {
                        createMessageTypeForm.setSortOrder(sortOrder);
                    } else {
                        messageTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the message type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createMessageTypeForm = persona.createMessageTypeForm;
                    var messageTypeEdit = persona.messageTypeEdit;

                    assertThat(createMessageTypeForm != null || messageTypeEdit != null).isTrue();

                    if(createMessageTypeForm != null) {
                        createMessageTypeForm.setDescription(description);
                    } else {
                        messageTypeEdit.setDescription(description);
                    }
                });

        When("^the user sets the message type's name to the last message type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastMessageTypeName = persona.lastMessageTypeName;
                    var deleteMessageTypeForm = persona.deleteMessageTypeForm;
                    var messageTypeSpec = persona.messageTypeSpec;

                    assertThat(deleteMessageTypeForm != null || messageTypeSpec != null).isTrue();

                    if(deleteMessageTypeForm != null) {
                        deleteMessageTypeForm.setMessageTypeName(lastMessageTypeName);
                    } else {
                        messageTypeSpec.setMessageTypeName(lastMessageTypeName);
                    }
                });
    }

}
