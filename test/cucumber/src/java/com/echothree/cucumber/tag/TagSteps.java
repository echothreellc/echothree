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

package com.echothree.cucumber.tag;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.result.CreateTagResult;
import com.echothree.control.user.tag.common.result.EditTagResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class TagSteps implements En {

    public TagSteps() {
        When("^the user begins entering a new tag$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagForm).isNull();
                    assertThat(persona.deleteTagForm).isNull();
                    assertThat(persona.tagSpec).isNull();

                    persona.createTagForm = TagUtil.getHome().getCreateTagForm();
                });

        When("^the user adds the new tag$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagForm).isNotNull();

                    var tagService = TagUtil.getHome();
                    var createTagForm = tagService.getCreateTagForm();

                    createTagForm.set(persona.createTagForm.get());

                    var commandResult = tagService.createTag(persona.userVisitPK, createTagForm);
                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateTagResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastTagName = commandResult.getHasErrors() ? null : result.getTagName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createTagForm = null;
                });

        When("^the user begins deleting a tag$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagForm).isNull();
                    assertThat(persona.deleteTagForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteTagForm = TagUtil.getHome().getDeleteTagForm();
                });

        When("^the user deletes the tag$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteTagForm = persona.deleteTagForm;

                    assertThat(deleteTagForm).isNotNull();

                    LastCommandResult.commandResult = TagUtil.getHome().deleteTag(persona.userVisitPK, deleteTagForm);

                    persona.deleteTagForm = null;
                });

        When("^the user begins specifying a tag to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagForm).isNull();
                    assertThat(persona.deleteTagForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.tagSpec = TagUtil.getHome().getTagSpec();
                });

        When("^the user begins editing the tag",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.tagSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = TagUtil.getHome().getEditTagForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = TagUtil.getHome().editTag(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditTagResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.tagEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the tag",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.tagSpec;
                    var edit = persona.tagEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = TagUtil.getHome().getEditTagForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = TagUtil.getHome().editTag(persona.userVisitPK, commandForm);

                    persona.tagSpec = null;
                    persona.tagEdit = null;
                });

        When("^the user sets the tag's tag scope name to \"([^\"]*)\"$",
                (String tagScopeName) -> {
                    var persona = CurrentPersona.persona;
                    var createTagForm = persona.createTagForm;
                    var deleteTagForm = persona.deleteTagForm;
                    var tagSpec = persona.tagSpec;

                    assertThat(createTagForm != null || deleteTagForm != null || tagSpec != null).isTrue();

                    if(createTagForm != null) {
                        createTagForm.setTagScopeName(tagScopeName);
                    } else {
                        Objects.requireNonNullElse(deleteTagForm, tagSpec).setTagScopeName(tagScopeName);
                    }
                });

        When("^the user sets the tag's tag scope name to the last tag scope added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createTagForm = persona.createTagForm;
                    var deleteTagForm = persona.deleteTagForm;
                    var tagSpec = persona.tagSpec;

                    assertThat(createTagForm != null || deleteTagForm != null || tagSpec != null).isTrue();

                    if(createTagForm != null) {
                        createTagForm.setTagScopeName(persona.lastTagScopeName);
                    } else {
                        Objects.requireNonNullElse(deleteTagForm, tagSpec).setTagScopeName(persona.lastTagScopeName);
                    }
                });
        
        When("^the user sets the tag's tag name to \"([^\"]*)\"$",
                (String tagName) -> {
                    var persona = CurrentPersona.persona;
                    var createTagForm = persona.createTagForm;
                    var deleteTagForm = persona.deleteTagForm;
                    var tagSpec = persona.tagSpec;

                    assertThat(createTagForm != null || deleteTagForm != null || tagSpec != null).isTrue();

                    if(createTagForm != null) {
                        createTagForm.setTagName(tagName);
                    } else {
                        Objects.requireNonNullElse(deleteTagForm, tagSpec).setTagName(tagName);
                    }
                });

        When("^the user sets the tag's tag name to the last tag added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createTagForm = persona.createTagForm;
                    var deleteTagForm = persona.deleteTagForm;
                    var tagSpec = persona.tagSpec;

                    assertThat(createTagForm != null || deleteTagForm != null || tagSpec != null).isTrue();

                    if(createTagForm != null) {
                        createTagForm.setTagName(persona.lastTagName);
                    } else {
                        Objects.requireNonNullElse(deleteTagForm, tagSpec).setTagName(persona.lastTagName);
                    }
                });

        When("^the user sets the tag's new tag name to \"([^\"]*)\"$",
                (String tagName) -> {
                    var persona = CurrentPersona.persona;
                    var tagEdit = persona.tagEdit;

                    assertThat(tagEdit).isNotNull();

                    tagEdit.setTagName(tagName);
                });
    }

}
