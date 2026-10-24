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

public class LinkTypeSteps implements En {

    public LinkTypeSteps() {
        When("^the user begins entering a new link type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkTypeForm).isNull();
                    assertThat(persona.deleteLinkTypeForm).isNull();
                    assertThat(persona.linkTypeSpec).isNull();

                    persona.createLinkTypeForm = CoreUtil.getHome().getCreateLinkTypeForm();
                });

        And("^the user adds the new link type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkTypeForm = persona.createLinkTypeForm;

                    assertThat(createLinkTypeForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createLinkType(persona.userVisitPK, createLinkTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLinkTypeName = commandResult.getHasErrors() ? null : result.getLinkTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLinkTypeForm = null;
                });

        When("^the user begins deleting a link type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkTypeForm).isNull();
                    assertThat(persona.deleteLinkTypeForm).isNull();
                    assertThat(persona.linkTypeSpec).isNull();

                    persona.deleteLinkTypeForm = CoreUtil.getHome().getDeleteLinkTypeForm();
                });

        And("^the user deletes the link type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLinkTypeForm = persona.deleteLinkTypeForm;

                    assertThat(deleteLinkTypeForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkType(persona.userVisitPK, deleteLinkTypeForm);

                    persona.deleteLinkTypeForm = null;
                });

        When("^the user begins specifying a link type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkTypeForm).isNull();
                    assertThat(persona.deleteLinkTypeForm).isNull();
                    assertThat(persona.linkTypeSpec).isNull();

                    persona.linkTypeSpec = CoreUtil.getHome().getLinkTypeUniversalSpec();
                });

        When("^the user begins editing the link type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editLinkType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.linkTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the link type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkTypeSpec;
                    var edit = persona.linkTypeEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editLinkType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.linkTypeSpec = null;
                    persona.linkTypeEdit = null;
                });

        And("^the user sets the link type's link type name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkTypeForm = persona.createLinkTypeForm;
                    var deleteLinkTypeForm = persona.deleteLinkTypeForm;
                    var linkTypeSpec = persona.linkTypeSpec;

                    assertThat(createLinkTypeForm != null || deleteLinkTypeForm != null
                            || linkTypeSpec != null).isTrue();

                    if(createLinkTypeForm != null) {
                        createLinkTypeForm.setLinkTypeName(linkTypeName);
                    } else if(deleteLinkTypeForm != null) {
                        deleteLinkTypeForm.setLinkTypeName(linkTypeName);
                    } else {
                        linkTypeSpec.setLinkTypeName(linkTypeName);
                    }
                });

        And("^the user sets the link type's link type name to the last link type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkTypeForm = persona.createLinkTypeForm;
                    var deleteLinkTypeForm = persona.deleteLinkTypeForm;
                    var linkTypeSpec = persona.linkTypeSpec;

                    assertThat(createLinkTypeForm != null || deleteLinkTypeForm != null
                            || linkTypeSpec != null).isTrue();

                    if(createLinkTypeForm != null) {
                        createLinkTypeForm.setLinkTypeName(persona.lastLinkTypeName);
                    } else if(deleteLinkTypeForm != null) {
                        deleteLinkTypeForm.setLinkTypeName(persona.lastLinkTypeName);
                    } else {
                        linkTypeSpec.setLinkTypeName(persona.lastLinkTypeName);
                    }
                });

        And("^the user sets the link type's new link type name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var linkTypeEdit = persona.linkTypeEdit;

                    assertThat(linkTypeEdit).isNotNull();

                    linkTypeEdit.setLinkTypeName(linkTypeName);
                });

        And("^the user sets the link type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkTypeForm = persona.createLinkTypeForm;
                    var linkTypeEdit = persona.linkTypeEdit;

                    assertThat(createLinkTypeForm != null || linkTypeEdit != null).isTrue();

                    if(createLinkTypeForm != null) {
                        createLinkTypeForm.setSortOrder(sortOrder);
                    } else {
                        linkTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the link type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkTypeForm = persona.createLinkTypeForm;
                    var linkTypeEdit = persona.linkTypeEdit;

                    assertThat(createLinkTypeForm != null || linkTypeEdit != null).isTrue();

                    if(createLinkTypeForm != null) {
                        createLinkTypeForm.setDescription(description);
                    } else {
                        linkTypeEdit.setDescription(description);
                    }
                });

        And("^the user sets the link type's source component vendor name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setSourceComponentVendorName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setSourceComponentVendorName(value);
                    }
                });

        And("^the user sets the link type's source entity type name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setSourceEntityTypeName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setSourceEntityTypeName(value);
                    }
                });

        And("^the user sets the link type's target component vendor name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setTargetComponentVendorName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setTargetComponentVendorName(value);
                    }
                });

        And("^the user sets the link type's target entity type name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setTargetEntityTypeName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setTargetEntityTypeName(value);
                    }
                });

        And("^the user sets the link type's link cardinality name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setLinkCardinalityName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setLinkCardinalityName(value);
                    }
                });

        And("^the user sets the link type's link directionality name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setLinkDirectionalityName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setLinkDirectionalityName(value);
                    }
                });

        And("^the user sets the link type's is transitive to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setIsTransitive(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setIsTransitive(value);
                    }
                });

        And("^the user sets the link type's parent link type name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setParentLinkTypeName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setParentLinkTypeName(value);
                    }
                });

        And("^the user sets the link type's inverse link type name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setInverseLinkTypeName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setInverseLinkTypeName(value);
                    }
                });

        And("^the user sets the link type's guidance MIME type name to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setGuidanceMimeTypeName(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setGuidanceMimeTypeName(value);
                    }
                });

        And("^the user sets the link type's guidance to \"([^\"]*)\"$",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    if(persona.createLinkTypeForm != null) {
                        persona.createLinkTypeForm.setGuidance(value);
                    } else {
                        assertThat(persona.linkTypeEdit).isNotNull();
                        persona.linkTypeEdit.setGuidance(value);
                    }
                });

    }

}
