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

package com.echothree.cucumber.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkDirectionalitySteps implements En {

    public LinkDirectionalitySteps() {
        When("^the user begins entering a new link directionality$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkDirectionalityForm).isNull();
                    assertThat(persona.deleteLinkDirectionalityForm).isNull();
                    assertThat(persona.linkDirectionalitySpec).isNull();

                    persona.createLinkDirectionalityForm = CoreUtil.getHome().getCreateLinkDirectionalityForm();
                });

        And("^the user adds the new link directionality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;

                    assertThat(createLinkDirectionalityForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createLinkDirectionality(persona.userVisitPK, createLinkDirectionalityForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLinkDirectionalityName = commandResult.getHasErrors() ? null : result.getLinkDirectionalityName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLinkDirectionalityForm = null;
                });

        When("^the user begins deleting a link directionality$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkDirectionalityForm).isNull();
                    assertThat(persona.deleteLinkDirectionalityForm).isNull();
                    assertThat(persona.linkDirectionalitySpec).isNull();

                    persona.deleteLinkDirectionalityForm = CoreUtil.getHome().getDeleteLinkDirectionalityForm();
                });

        And("^the user deletes the link directionality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLinkDirectionalityForm = persona.deleteLinkDirectionalityForm;

                    assertThat(deleteLinkDirectionalityForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkDirectionality(persona.userVisitPK, deleteLinkDirectionalityForm);

                    persona.deleteLinkDirectionalityForm = null;
                });

        When("^the user begins specifying a link directionality to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkDirectionalityForm).isNull();
                    assertThat(persona.deleteLinkDirectionalityForm).isNull();
                    assertThat(persona.linkDirectionalitySpec).isNull();

                    persona.linkDirectionalitySpec = CoreUtil.getHome().getLinkDirectionalityUniversalSpec();
                });

        When("^the user begins editing the link directionality$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkDirectionalitySpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkDirectionalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editLinkDirectionality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.linkDirectionalityEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the link directionality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkDirectionalitySpec;
                    var edit = persona.linkDirectionalityEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkDirectionalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editLinkDirectionality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.linkDirectionalitySpec = null;
                    persona.linkDirectionalityEdit = null;
                });

        And("^the user sets the link directionality's link directionality name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkDirectionalityName) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var deleteLinkDirectionalityForm = persona.deleteLinkDirectionalityForm;
                    var linkDirectionalitySpec = persona.linkDirectionalitySpec;

                    assertThat(createLinkDirectionalityForm != null || deleteLinkDirectionalityForm != null
                            || linkDirectionalitySpec != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setLinkDirectionalityName(linkDirectionalityName);
                    } else if(deleteLinkDirectionalityForm != null) {
                        deleteLinkDirectionalityForm.setLinkDirectionalityName(linkDirectionalityName);
                    } else {
                        linkDirectionalitySpec.setLinkDirectionalityName(linkDirectionalityName);
                    }
                });

        And("^the user sets the link directionality's link directionality name to the last link directionality added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var deleteLinkDirectionalityForm = persona.deleteLinkDirectionalityForm;
                    var linkDirectionalitySpec = persona.linkDirectionalitySpec;

                    assertThat(createLinkDirectionalityForm != null || deleteLinkDirectionalityForm != null
                            || linkDirectionalitySpec != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setLinkDirectionalityName(persona.lastLinkDirectionalityName);
                    } else if(deleteLinkDirectionalityForm != null) {
                        deleteLinkDirectionalityForm.setLinkDirectionalityName(persona.lastLinkDirectionalityName);
                    } else {
                        linkDirectionalitySpec.setLinkDirectionalityName(persona.lastLinkDirectionalityName);
                    }
                });

        And("^the user sets the link directionality's new link directionality name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkDirectionalityName) -> {
                    var persona = CurrentPersona.persona;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(linkDirectionalityEdit).isNotNull();

                    linkDirectionalityEdit.setLinkDirectionalityName(linkDirectionalityName);
                });

        And("^the user sets the link directionality to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(createLinkDirectionalityForm != null || linkDirectionalityEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setIsDefault(isDefault);
                    } else {
                        linkDirectionalityEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the link directionality's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(createLinkDirectionalityForm != null || linkDirectionalityEdit != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setSortOrder(sortOrder);
                    } else {
                        linkDirectionalityEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the link directionality's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(createLinkDirectionalityForm != null || linkDirectionalityEdit != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setDescription(description);
                    } else {
                        linkDirectionalityEdit.setDescription(description);
                    }
                });

    }

}
