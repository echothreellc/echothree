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

package com.echothree.cucumber.comment;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.result.CreateCommentTypeResult;
import com.echothree.control.user.comment.common.result.EditCommentTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentTypeSteps implements En {

    public CommentTypeSteps() {
        When("^the user begins entering a new comment type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCommentTypeForm).isNull();
                    assertThat(persona.deleteCommentTypeForm).isNull();
                    assertThat(persona.commentTypeSpec).isNull();

                    persona.createCommentTypeForm = CommentUtil.getHome().getCreateCommentTypeForm();
                });

        When("^the user adds the new comment type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;

                    assertThat(createCommentTypeForm).isNotNull();

                    var commandResult = CommentUtil.getHome().createCommentType(persona.userVisitPK, createCommentTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateCommentTypeResult)executionResult.getResult();
                    persona.lastCommentTypeName = result.getCommentTypeName();

                    persona.createCommentTypeForm = null;
                });

        When("^the user begins deleting a comment type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCommentTypeForm).isNull();
                    assertThat(persona.deleteCommentTypeForm).isNull();
                    assertThat(persona.commentTypeSpec).isNull();

                    persona.deleteCommentTypeForm = CommentUtil.getHome().getDeleteCommentTypeForm();
                });

        When("^the user deletes the comment type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteCommentTypeForm = persona.deleteCommentTypeForm;

                    assertThat(deleteCommentTypeForm).isNotNull();

                    LastCommandResult.commandResult = CommentUtil.getHome().deleteCommentType(persona.userVisitPK, deleteCommentTypeForm);

                    persona.deleteCommentTypeForm = null;
                });

        When("^the user begins specifying a comment type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCommentTypeForm).isNull();
                    assertThat(persona.deleteCommentTypeForm).isNull();
                    assertThat(persona.commentTypeSpec).isNull();

                    persona.commentTypeSpec = CommentUtil.getHome().getCommentTypeSpec();
                });

        When("^the user begins editing the comment type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.commentTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CommentUtil.getHome().getEditCommentTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CommentUtil.getHome().editCommentType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditCommentTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.commentTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the comment type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.commentTypeSpec;
                    var edit = persona.commentTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CommentUtil.getHome().getEditCommentTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CommentUtil.getHome().editCommentType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.commentTypeSpec = null;
                    persona.commentTypeEdit = null;
                });
        
        When("^the user sets the comment type's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;
                    var deleteCommentTypeForm = persona.deleteCommentTypeForm;
                    var commentTypeSpec = persona.commentTypeSpec;

                    assertThat(createCommentTypeForm != null || deleteCommentTypeForm != null || commentTypeSpec != null).isTrue();

                    if(createCommentTypeForm != null) {
                        createCommentTypeForm.setComponentVendorName(componentVendorName);
                    } else if(deleteCommentTypeForm != null) {
                        deleteCommentTypeForm.setComponentVendorName(componentVendorName);
                    } else {
                        commentTypeSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the comment type's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;
                    var deleteCommentTypeForm = persona.deleteCommentTypeForm;
                    var commentTypeSpec = persona.commentTypeSpec;

                    assertThat(createCommentTypeForm != null || deleteCommentTypeForm != null || commentTypeSpec != null).isTrue();

                    if(createCommentTypeForm != null) {
                        createCommentTypeForm.setEntityTypeName(entityTypeName);
                    } else if(deleteCommentTypeForm != null) {
                        deleteCommentTypeForm.setEntityTypeName(entityTypeName);
                    } else {
                        commentTypeSpec.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the comment type's comment type to ([a-zA-Z0-9-_]*)$",
                (String commentTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;
                    var deleteCommentTypeForm = persona.deleteCommentTypeForm;
                    var commentTypeSpec = persona.commentTypeSpec;

                    assertThat(createCommentTypeForm != null || deleteCommentTypeForm != null || commentTypeSpec != null).isTrue();

                    if(createCommentTypeForm != null) {
                        createCommentTypeForm.setCommentTypeName(commentTypeName);
                    } else if(deleteCommentTypeForm != null) {
                        deleteCommentTypeForm.setCommentTypeName(commentTypeName);
                    } else {
                        commentTypeSpec.setCommentTypeName(commentTypeName);
                    }
                });

        When("^the user sets the comment type's comment sequence to ([a-zA-Z0-9-_]*)$",
                (String commentSequenceName) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;
                    var commentTypeEdit = persona.commentTypeEdit;

                    assertThat(createCommentTypeForm != null || commentTypeEdit != null).isTrue();

                    if(createCommentTypeForm != null) {
                        createCommentTypeForm.setCommentSequenceName(commentSequenceName);
                    } else {
                        commentTypeEdit.setCommentSequenceName(commentSequenceName);
                    }
                });

        When("^the user sets the comment type's workflow to ([a-zA-Z0-9-_]*)$",
                (String workflowName) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;

                    assertThat(createCommentTypeForm != null).isTrue();

                    createCommentTypeForm.setWorkflowName(workflowName);
                });

        When("^the user sets the comment type's workflow entrance to ([a-zA-Z0-9-_]*)$",
                (String workflowEntranceName) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;

                    assertThat(createCommentTypeForm != null).isTrue();

                    createCommentTypeForm.setWorkflowEntranceName(workflowEntranceName);
                });

        When("^the user sets the comment type's mime type usage type to ([a-zA-Z0-9-_]*)$",
                (String mimeTypeUsageTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;

                    assertThat(createCommentTypeForm != null).isTrue();

                    createCommentTypeForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);
                });

        When("^the user sets the comment type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;
                    var commentTypeEdit = persona.commentTypeEdit;

                    assertThat(createCommentTypeForm != null || commentTypeEdit != null).isTrue();

                    if(createCommentTypeForm != null) {
                        createCommentTypeForm.setSortOrder(sortOrder);
                    } else {
                        commentTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the comment type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createCommentTypeForm = persona.createCommentTypeForm;
                    var commentTypeEdit = persona.commentTypeEdit;

                    assertThat(createCommentTypeForm != null || commentTypeEdit != null).isTrue();

                    if(createCommentTypeForm != null) {
                        createCommentTypeForm.setDescription(description);
                    } else {
                        commentTypeEdit.setDescription(description);
                    }
                });

        When("^the user sets the comment type's name to the last comment type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastCommentTypeName = persona.lastCommentTypeName;
                    var deleteCommentTypeForm = persona.deleteCommentTypeForm;
                    var commentTypeSpec = persona.commentTypeSpec;

                    assertThat(deleteCommentTypeForm != null || commentTypeSpec != null).isTrue();

                    if(deleteCommentTypeForm != null) {
                        deleteCommentTypeForm.setCommentTypeName(lastCommentTypeName);
                    } else {
                        commentTypeSpec.setCommentTypeName(lastCommentTypeName);
                    }
                });
    }

}
