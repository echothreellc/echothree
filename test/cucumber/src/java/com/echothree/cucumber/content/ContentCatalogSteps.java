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
import com.echothree.control.user.content.common.result.EditContentCatalogResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentCatalogSteps implements En {

    public ContentCatalogSteps() {
        When("^the user begins entering a new content catalog$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCatalogForm).isNull();
                    assertThat(persona.deleteContentCatalogForm).isNull();
                    assertThat(persona.contentCatalogSpec).isNull();

                    persona.createContentCatalogForm = ContentUtil.getHome().getCreateContentCatalogForm();
                });

        When("^the user adds the new content catalog$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCatalogForm).isNotNull();

                    var contentUtil = ContentUtil.getHome();
                    var createContentCatalogForm = contentUtil.getCreateContentCatalogForm();

                    createContentCatalogForm.set(persona.createContentCatalogForm.get());

                    var commandResult = contentUtil.createContentCatalog(persona.userVisitPK, createContentCatalogForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createContentCatalogForm = null;
                });

        When("^the user begins deleting a content catalog$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCatalogForm).isNull();
                    assertThat(persona.deleteContentCatalogForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteContentCatalogForm = ContentUtil.getHome().getDeleteContentCatalogForm();
                });

        When("^the user deletes the content catalog$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteContentCatalogForm = persona.deleteContentCatalogForm;

                    assertThat(deleteContentCatalogForm).isNotNull();

                    LastCommandResult.commandResult = ContentUtil.getHome().deleteContentCatalog(persona.userVisitPK, deleteContentCatalogForm);

                    persona.deleteContentCatalogForm = null;
                });

        When("^the user begins specifying a content catalog to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCatalogForm).isNull();
                    assertThat(persona.deleteContentCatalogForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.contentCatalogSpec = ContentUtil.getHome().getContentCatalogSpec();
                });

        When("^the user begins editing the content catalog$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCatalogSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCatalogForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContentUtil.getHome().editContentCatalog(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditContentCatalogResult)executionResult.getResult();

                        persona.contentCatalogEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the content catalog$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCatalogSpec;
                    var edit = persona.contentCatalogEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCatalogForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = ContentUtil.getHome().editContentCatalog(persona.userVisitPK, commandForm);

                    persona.contentCatalogSpec = null;
                    persona.contentCatalogEdit = null;
                });

        When("^the user sets the content catalog's content collection name to \"([^\"]*)\"$",
                (String contentCollectionName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var deleteContentCatalogForm = persona.deleteContentCatalogForm;
                    var contentCatalogSpec = persona.contentCatalogSpec;

                    assertThat(createContentCatalogForm != null || deleteContentCatalogForm != null
                            || contentCatalogSpec != null).isTrue();

                    if(createContentCatalogForm != null) {
                        createContentCatalogForm.setContentCollectionName(contentCollectionName);
                    } else if(deleteContentCatalogForm != null) {
                        deleteContentCatalogForm.setContentCollectionName(contentCollectionName);
                    } else {
                        contentCatalogSpec.setContentCollectionName(contentCollectionName);
                    }
                });

        When("^the user sets the content catalog's content catalog name to \"([^\"]*)\"$",
                (String contentCatalogName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var deleteContentCatalogForm = persona.deleteContentCatalogForm;
                    var contentCatalogSpec = persona.contentCatalogSpec;

                    assertThat(createContentCatalogForm != null || deleteContentCatalogForm != null
                            || contentCatalogSpec != null).isTrue();

                    if(createContentCatalogForm != null) {
                        createContentCatalogForm.setContentCatalogName(contentCatalogName);
                    } else if(deleteContentCatalogForm != null) {
                        deleteContentCatalogForm.setContentCatalogName(contentCatalogName);
                    } else {
                        contentCatalogSpec.setContentCatalogName(contentCatalogName);
                    }
                });

        When("^the user sets the content catalog's new content catalog name to \"([^\"]*)\"$",
                (String contentCatalogName) -> {
                    var persona = CurrentPersona.persona;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(contentCatalogEdit).isNotNull();

                    contentCatalogEdit.setContentCatalogName(contentCatalogName);
                });

        When("^the user sets the content catalog's default offer name to \"([^\"]*)\"$",
                (String defaultOfferName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCatalogForm, contentCatalogEdit).setDefaultOfferName(defaultOfferName);
                });

        When("^the user sets the content catalog's default offer name to the last offer added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCatalogForm, contentCatalogEdit).setDefaultOfferName(persona.lastOfferName);
                });

        When("^the user sets the content catalog's default use name to \"([^\"]*)\"$",
                (String defaultUseName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCatalogForm, contentCatalogEdit).setDefaultUseName(defaultUseName);
                });

        When("^the user sets the content catalog's default use name to the last use added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCatalogForm, contentCatalogEdit).setDefaultUseName(persona.lastUseName);
                });

        When("^the user sets the content catalog's default source name to \"([^\"]*)\"$",
                (String defaultSourceName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCatalogForm, contentCatalogEdit).setDefaultSourceName(defaultSourceName);
                });

        When("^the user sets the content catalog's default source name to the last source added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCatalogForm, contentCatalogEdit).setDefaultSourceName(persona.lastSourceName);
                });

        When("^the user sets the content catalog to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createContentCatalogForm != null) {
                        createContentCatalogForm.setIsDefault(isDefault);
                    } else {
                        contentCatalogEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the content catalog's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    if(createContentCatalogForm != null) {
                        createContentCatalogForm.setSortOrder(sortOrder);
                    } else {
                        contentCatalogEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the content catalog's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCatalogForm = persona.createContentCatalogForm;
                    var contentCatalogEdit = persona.contentCatalogEdit;

                    assertThat(createContentCatalogForm != null || contentCatalogEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCatalogForm, contentCatalogEdit).setDescription(description);
                });
    }

}
