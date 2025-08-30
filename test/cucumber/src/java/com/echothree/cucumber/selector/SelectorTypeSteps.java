// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.cucumber.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.CreateSelectorTypeResult;
import com.echothree.control.user.selector.common.result.EditSelectorTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectorTypeSteps implements En {

    public SelectorTypeSteps() {
        When("^the user begins entering a new selector type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorTypeForm).isNull();
                    assertThat(persona.deleteSelectorTypeForm).isNull();
                    assertThat(persona.selectorTypeSpec).isNull();

                    persona.createSelectorTypeForm = SelectorUtil.getHome().getCreateSelectorTypeForm();
                });

        And("^the user adds the new selector type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorTypeForm = persona.createSelectorTypeForm;

                    assertThat(createSelectorTypeForm).isNotNull();

                    var commandResult = SelectorUtil.getHome().createSelectorType(persona.userVisitPK, createSelectorTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateSelectorTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastSelectorTypeName = commandResult.getHasErrors() ? null : result.getSelectorTypeName();
                    persona.createSelectorTypeForm = null;
                });

        When("^the user begins deleting a selector type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorTypeForm).isNull();
                    assertThat(persona.deleteSelectorTypeForm).isNull();
                    assertThat(persona.selectorTypeSpec).isNull();

                    persona.deleteSelectorTypeForm = SelectorUtil.getHome().getDeleteSelectorTypeForm();
                });

        And("^the user deletes the selector type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteSelectorTypeForm = persona.deleteSelectorTypeForm;

                    assertThat(deleteSelectorTypeForm).isNotNull();

                    LastCommandResult.commandResult = SelectorUtil.getHome().deleteSelectorType(persona.userVisitPK, deleteSelectorTypeForm);

                    persona.deleteSelectorTypeForm = null;
                });

        When("^the user begins specifying a selector type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorTypeForm).isNull();
                    assertThat(persona.deleteSelectorTypeForm).isNull();
                    assertThat(persona.selectorTypeSpec).isNull();

                    persona.selectorTypeSpec = SelectorUtil.getHome().getSelectorTypeSpec();
                });

        When("^the user begins editing the selector type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.selectorTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = SelectorUtil.getHome().getEditSelectorTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = SelectorUtil.getHome().editSelectorType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditSelectorTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.selectorTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the selector type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.selectorTypeSpec;
                    var edit = persona.selectorTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = SelectorUtil.getHome().getEditSelectorTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = SelectorUtil.getHome().editSelectorType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.selectorTypeSpec = null;
                    persona.selectorTypeEdit = null;
                });

        And("^the user sets the selector type's selector kind name to ([a-zA-Z0-9-_]*)$",
                (String selectorKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorTypeForm = persona.createSelectorTypeForm;
                    var deleteSelectorTypeForm = persona.deleteSelectorTypeForm;
                    var selectorTypeSpec = persona.selectorTypeSpec;

                    assertThat(createSelectorTypeForm != null || deleteSelectorTypeForm != null || selectorTypeSpec != null).isTrue();

                    if(createSelectorTypeForm != null) {
                        createSelectorTypeForm.setSelectorKindName(selectorKindName);
                    } else if(deleteSelectorTypeForm != null) {
                        deleteSelectorTypeForm.setSelectorKindName(selectorKindName);
                    } else {
                        selectorTypeSpec.setSelectorKindName(selectorKindName);
                    }
                });

        And("^the user sets the selector type's name to ([a-zA-Z0-9-_]*)$",
                (String selectorTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorTypeForm = persona.createSelectorTypeForm;
                    var deleteSelectorTypeForm = persona.deleteSelectorTypeForm;
                    var selectorTypeSpec = persona.selectorTypeSpec;

                    assertThat(createSelectorTypeForm != null || deleteSelectorTypeForm != null || selectorTypeSpec != null).isTrue();

                    if(createSelectorTypeForm != null) {
                        createSelectorTypeForm.setSelectorTypeName(selectorTypeName);
                    } else if(deleteSelectorTypeForm != null) {
                        deleteSelectorTypeForm.setSelectorTypeName(selectorTypeName);
                    } else {
                        selectorTypeSpec.setSelectorTypeName(selectorTypeName);
                    }
                });

        And("^the user sets the selector type's name to the last selector type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastSelectorTypeName = persona.lastSelectorTypeName;
                    var deleteSelectorTypeForm = persona.deleteSelectorTypeForm;
                    var selectorTypeSpec = persona.selectorTypeSpec;

                    assertThat(deleteSelectorTypeForm != null || selectorTypeSpec != null).isTrue();

                    if(deleteSelectorTypeForm != null) {
                        deleteSelectorTypeForm.setSelectorTypeName(lastSelectorTypeName);
                    } else {
                        selectorTypeSpec.setSelectorTypeName(lastSelectorTypeName);
                    }
                });

        And("^the user sets the selector type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorTypeForm = persona.createSelectorTypeForm;
                    var selectorTypeEdit = persona.selectorTypeEdit;

                    assertThat(createSelectorTypeForm != null || selectorTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createSelectorTypeForm != null) {
                        createSelectorTypeForm.setIsDefault(isDefault);
                    } else {
                        selectorTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the selector type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorTypeForm = persona.createSelectorTypeForm;
                    var selectorTypeEdit = persona.selectorTypeEdit;

                    assertThat(createSelectorTypeForm != null || selectorTypeEdit != null).isTrue();

                    if(createSelectorTypeForm != null) {
                        createSelectorTypeForm.setSortOrder(sortOrder);
                    } else {
                        selectorTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the selector type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorTypeForm = persona.createSelectorTypeForm;
                    var selectorTypeEdit = persona.selectorTypeEdit;

                    assertThat(createSelectorTypeForm != null || selectorTypeEdit != null).isTrue();

                    if(createSelectorTypeForm != null) {
                        createSelectorTypeForm.setDescription(description);
                    } else {
                        selectorTypeEdit.setDescription(description);
                    }
                });
    }

}
