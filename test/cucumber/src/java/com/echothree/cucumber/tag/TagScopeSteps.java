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
import com.echothree.control.user.tag.common.result.CreateTagScopeResult;
import com.echothree.control.user.tag.common.result.EditTagScopeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class TagScopeSteps implements En {

    public TagScopeSteps() {
        When("^the user begins entering a new tag scope$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagScopeForm).isNull();
                    assertThat(persona.deleteTagScopeForm).isNull();
                    assertThat(persona.tagScopeSpec).isNull();

                    persona.createTagScopeForm = TagUtil.getHome().getCreateTagScopeForm();
                });

        When("^the user adds the new tag scope$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagScopeForm).isNotNull();

                    var tagScopeService = TagUtil.getHome();
                    var createTagScopeForm = tagScopeService.getCreateTagScopeForm();

                    createTagScopeForm.set(persona.createTagScopeForm.get());

                    var commandResult = tagScopeService.createTagScope(persona.userVisitPK, createTagScopeForm);
                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateTagScopeResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastTagScopeName = commandResult.getHasErrors() ? null : result.getTagScopeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createTagScopeForm = null;
                });

        When("^the user begins deleting a tag scope$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagScopeForm).isNull();
                    assertThat(persona.deleteTagScopeForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteTagScopeForm = TagUtil.getHome().getDeleteTagScopeForm();
                });

        When("^the user deletes the tag scope$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteTagScopeForm = persona.deleteTagScopeForm;

                    assertThat(deleteTagScopeForm).isNotNull();

                    LastCommandResult.commandResult = TagUtil.getHome().deleteTagScope(persona.userVisitPK, deleteTagScopeForm);

                    persona.deleteTagScopeForm = null;
                });

        When("^the user begins specifying a tag scope to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagScopeForm).isNull();
                    assertThat(persona.deleteTagScopeForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.tagScopeSpec = TagUtil.getHome().getTagScopeSpec();
                });

        When("^the user begins editing the tag scope",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.tagScopeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = TagUtil.getHome().getEditTagScopeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = TagUtil.getHome().editTagScope(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditTagScopeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.tagScopeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the tag scope",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.tagScopeSpec;
                    var edit = persona.tagScopeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = TagUtil.getHome().getEditTagScopeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = TagUtil.getHome().editTagScope(persona.userVisitPK, commandForm);

                    persona.tagScopeSpec = null;
                    persona.tagScopeEdit = null;
                });


        When("^the user sets the tag scope's tag scope name to \"([^\"]*)\"$",
                (String tagScopeName) -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeForm = persona.createTagScopeForm;
                    var deleteTagScopeForm = persona.deleteTagScopeForm;
                    var tagScopeSpec = persona.tagScopeSpec;

                    assertThat(createTagScopeForm != null || deleteTagScopeForm != null || tagScopeSpec != null).isTrue();

                    if(createTagScopeForm != null) {
                        createTagScopeForm.setTagScopeName(tagScopeName);
                    } else {
                        Objects.requireNonNullElse(deleteTagScopeForm, tagScopeSpec).setTagScopeName(tagScopeName);
                    }
                });

        When("^the user sets the tag scope's tag scope name to the last tag scope added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeForm = persona.createTagScopeForm;
                    var deleteTagScopeForm = persona.deleteTagScopeForm;
                    var tagScopeSpec = persona.tagScopeSpec;

                    assertThat(createTagScopeForm != null || deleteTagScopeForm != null || tagScopeSpec != null).isTrue();

                    if(createTagScopeForm != null) {
                        createTagScopeForm.setTagScopeName(persona.lastTagScopeName);
                    } else {
                        Objects.requireNonNullElse(deleteTagScopeForm, tagScopeSpec).setTagScopeName(persona.lastTagScopeName);
                    }
                });

        When("^the user sets the tag scope's new tag scope name to \"([^\"]*)\"$",
                (String tagScopeName) -> {
                    var persona = CurrentPersona.persona;
                    var tagScopeEdit = persona.tagScopeEdit;

                    assertThat(tagScopeEdit).isNotNull();

                    tagScopeEdit.setTagScopeName(tagScopeName);
                });
        
        When("^the user sets the tag scope to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeForm = persona.createTagScopeForm;
                    var tagScopeEdit = persona.tagScopeEdit;

                    assertThat(createTagScopeForm != null || tagScopeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    Objects.requireNonNullElse(createTagScopeForm, tagScopeEdit).setIsDefault(isDefault);
                });

        When("^the user sets the tag scope's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeForm = persona.createTagScopeForm;
                    var tagScopeEdit = persona.tagScopeEdit;

                    assertThat(createTagScopeForm != null || tagScopeEdit != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeForm, tagScopeEdit).setSortOrder(sortOrder);
                });

        When("^the user sets the tag scope's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeForm = persona.createTagScopeForm;
                    var tagScopeEdit = persona.tagScopeEdit;

                    assertThat(createTagScopeForm != null || tagScopeEdit != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeForm, tagScopeEdit).setDescription(description);
                });
    }

}
