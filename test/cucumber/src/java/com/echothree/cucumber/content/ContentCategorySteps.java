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

package com.echothree.cucumber.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.EditContentCategoryResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentCategorySteps implements En {

    public ContentCategorySteps() {
        When("^the user begins entering a new content category$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryForm).isNull();
                    assertThat(persona.deleteContentCategoryForm).isNull();
                    assertThat(persona.contentCategorySpec).isNull();

                    persona.createContentCategoryForm = ContentUtil.getHome().getCreateContentCategoryForm();
                });

        When("^the user adds the new content category$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryForm).isNotNull();

                    var contentUtil = ContentUtil.getHome();
                    var createContentCategoryForm = contentUtil.getCreateContentCategoryForm();

                    createContentCategoryForm.set(persona.createContentCategoryForm.get());

                    var commandResult = contentUtil.createContentCategory(persona.userVisitPK, createContentCategoryForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createContentCategoryForm = null;
                });

        When("^the user begins deleting a content category$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryForm).isNull();
                    assertThat(persona.deleteContentCategoryForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteContentCategoryForm = ContentUtil.getHome().getDeleteContentCategoryForm();
                });

        When("^the user deletes the content category$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteContentCategoryForm = persona.deleteContentCategoryForm;

                    assertThat(deleteContentCategoryForm).isNotNull();

                    LastCommandResult.commandResult = ContentUtil.getHome().deleteContentCategory(persona.userVisitPK, deleteContentCategoryForm);

                    persona.deleteContentCategoryForm = null;
                });

        When("^the user begins specifying a content category to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryForm).isNull();
                    assertThat(persona.deleteContentCategoryForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.contentCategorySpec = ContentUtil.getHome().getContentCategorySpec();
                });

        When("^the user begins editing the content category$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCategorySpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCategoryForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContentUtil.getHome().editContentCategory(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditContentCategoryResult)executionResult.getResult();

                        persona.contentCategoryEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the content category$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCategorySpec;
                    var edit = persona.contentCategoryEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCategoryForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = ContentUtil.getHome().editContentCategory(persona.userVisitPK, commandForm);

                    persona.contentCategorySpec = null;
                    persona.contentCategoryEdit = null;
                });

        When("^the user sets the content category's content collection name to \"([^\"]*)\"$",
                (String contentCollectionName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var deleteContentCategoryForm = persona.deleteContentCategoryForm;
                    var contentCategorySpec = persona.contentCategorySpec;

                    assertThat(createContentCategoryForm != null || deleteContentCategoryForm != null
                            || contentCategorySpec != null).isTrue();

                    if(createContentCategoryForm != null) {
                        createContentCategoryForm.setContentCollectionName(contentCollectionName);
                    } else if(deleteContentCategoryForm != null) {
                        deleteContentCategoryForm.setContentCollectionName(contentCollectionName);
                    } else {
                        contentCategorySpec.setContentCollectionName(contentCollectionName);
                    }
                });

        When("^the user sets the content category's content catalog name to \"([^\"]*)\"$",
                (String contentCatalogName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var deleteContentCategoryForm = persona.deleteContentCategoryForm;
                    var contentCategorySpec = persona.contentCategorySpec;

                    assertThat(createContentCategoryForm != null || deleteContentCategoryForm != null
                            || contentCategorySpec != null).isTrue();

                    if(createContentCategoryForm != null) {
                        createContentCategoryForm.setContentCatalogName(contentCatalogName);
                    } else if(deleteContentCategoryForm != null) {
                        deleteContentCategoryForm.setContentCatalogName(contentCatalogName);
                    } else {
                        contentCategorySpec.setContentCatalogName(contentCatalogName);
                    }
                });

        When("^the user sets the content category's content category name to \"([^\"]*)\"$",
                (String contentCategoryName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var deleteContentCategoryForm = persona.deleteContentCategoryForm;
                    var contentCategorySpec = persona.contentCategorySpec;

                    assertThat(createContentCategoryForm != null || deleteContentCategoryForm != null
                            || contentCategorySpec != null).isTrue();

                    if(createContentCategoryForm != null) {
                        createContentCategoryForm.setContentCategoryName(contentCategoryName);
                    } else if(deleteContentCategoryForm != null) {
                        deleteContentCategoryForm.setContentCategoryName(contentCategoryName);
                    } else {
                        contentCategorySpec.setContentCategoryName(contentCategoryName);
                    }
                });

        When("^the user sets the content category's new content category name to \"([^\"]*)\"$",
                (String contentCategoryName) -> {
                    var persona = CurrentPersona.persona;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(contentCategoryEdit).isNotNull();

                    contentCategoryEdit.setContentCategoryName(contentCategoryName);
                });

        When("^the user sets the content category's parent content category name to \"([^\"]*)\"$",
                (String parentContentCategoryName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setParentContentCategoryName(parentContentCategoryName);
                });

        When("^the user sets the content category's default offer name to \"([^\"]*)\"$",
                (String defaultOfferName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setDefaultOfferName(defaultOfferName);
                });

        When("^the user sets the content category's default offer name to the last offer added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setDefaultOfferName(persona.lastOfferName);
                });

        When("^the user sets the content category's default use name to \"([^\"]*)\"$",
                (String defaultUseName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setDefaultUseName(defaultUseName);
                });

        When("^the user sets the content category's default use name to the last use added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setDefaultUseName(persona.lastUseName);
                });

        When("^the user sets the content category's default source name to \"([^\"]*)\"$",
                (String defaultSourceName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setDefaultSourceName(defaultSourceName);
                });

        When("^the user sets the content category's default source name to the last source added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setDefaultSourceName(persona.lastSourceName);
                });

        When("^the user sets the content category to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createContentCategoryForm != null) {
                        createContentCategoryForm.setIsDefault(isDefault);
                    } else {
                        contentCategoryEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the content category's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    if(createContentCategoryForm != null) {
                        createContentCategoryForm.setSortOrder(sortOrder);
                    } else {
                        contentCategoryEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the content category's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryForm = persona.createContentCategoryForm;
                    var contentCategoryEdit = persona.contentCategoryEdit;

                    assertThat(createContentCategoryForm != null || contentCategoryEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryForm, contentCategoryEdit).setDescription(description);
                });
    }

}
