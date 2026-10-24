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

public class LinkCardinalitySteps implements En {

    public LinkCardinalitySteps() {
        When("^the user begins entering a new link cardinality$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkCardinalityForm).isNull();
                    assertThat(persona.deleteLinkCardinalityForm).isNull();
                    assertThat(persona.linkCardinalitySpec).isNull();

                    persona.createLinkCardinalityForm = CoreUtil.getHome().getCreateLinkCardinalityForm();
                });

        And("^the user adds the new link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;

                    assertThat(createLinkCardinalityForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createLinkCardinality(persona.userVisitPK, createLinkCardinalityForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLinkCardinalityName = commandResult.getHasErrors() ? null : result.getLinkCardinalityName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLinkCardinalityForm = null;
                });

        When("^the user begins deleting a link cardinality$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkCardinalityForm).isNull();
                    assertThat(persona.deleteLinkCardinalityForm).isNull();
                    assertThat(persona.linkCardinalitySpec).isNull();

                    persona.deleteLinkCardinalityForm = CoreUtil.getHome().getDeleteLinkCardinalityForm();
                });

        And("^the user deletes the link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLinkCardinalityForm = persona.deleteLinkCardinalityForm;

                    assertThat(deleteLinkCardinalityForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkCardinality(persona.userVisitPK, deleteLinkCardinalityForm);

                    persona.deleteLinkCardinalityForm = null;
                });

        When("^the user begins specifying a link cardinality to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkCardinalityForm).isNull();
                    assertThat(persona.deleteLinkCardinalityForm).isNull();
                    assertThat(persona.linkCardinalitySpec).isNull();

                    persona.linkCardinalitySpec = CoreUtil.getHome().getLinkCardinalityUniversalSpec();
                });

        When("^the user begins editing the link cardinality$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkCardinalitySpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkCardinalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editLinkCardinality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.linkCardinalityEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkCardinalitySpec;
                    var edit = persona.linkCardinalityEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkCardinalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editLinkCardinality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.linkCardinalitySpec = null;
                    persona.linkCardinalityEdit = null;
                });

        And("^the user sets the link cardinality's link cardinality name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkCardinalityName) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var deleteLinkCardinalityForm = persona.deleteLinkCardinalityForm;
                    var linkCardinalitySpec = persona.linkCardinalitySpec;

                    assertThat(createLinkCardinalityForm != null || deleteLinkCardinalityForm != null
                            || linkCardinalitySpec != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setLinkCardinalityName(linkCardinalityName);
                    } else if(deleteLinkCardinalityForm != null) {
                        deleteLinkCardinalityForm.setLinkCardinalityName(linkCardinalityName);
                    } else {
                        linkCardinalitySpec.setLinkCardinalityName(linkCardinalityName);
                    }
                });

        And("^the user sets the link cardinality's link cardinality name to the last link cardinality added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var deleteLinkCardinalityForm = persona.deleteLinkCardinalityForm;
                    var linkCardinalitySpec = persona.linkCardinalitySpec;

                    assertThat(createLinkCardinalityForm != null || deleteLinkCardinalityForm != null
                            || linkCardinalitySpec != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setLinkCardinalityName(persona.lastLinkCardinalityName);
                    } else if(deleteLinkCardinalityForm != null) {
                        deleteLinkCardinalityForm.setLinkCardinalityName(persona.lastLinkCardinalityName);
                    } else {
                        linkCardinalitySpec.setLinkCardinalityName(persona.lastLinkCardinalityName);
                    }
                });

        And("^the user sets the link cardinality's new link cardinality name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkCardinalityName) -> {
                    var persona = CurrentPersona.persona;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(linkCardinalityEdit).isNotNull();

                    linkCardinalityEdit.setLinkCardinalityName(linkCardinalityName);
                });

        And("^the user sets the link cardinality to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(createLinkCardinalityForm != null || linkCardinalityEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setIsDefault(isDefault);
                    } else {
                        linkCardinalityEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the link cardinality's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(createLinkCardinalityForm != null || linkCardinalityEdit != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setSortOrder(sortOrder);
                    } else {
                        linkCardinalityEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the link cardinality's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(createLinkCardinalityForm != null || linkCardinalityEdit != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setDescription(description);
                    } else {
                        linkCardinalityEdit.setDescription(description);
                    }
                });

    }

}
