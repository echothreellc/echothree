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

package com.echothree.cucumber.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.CreateSelectorResult;
import com.echothree.control.user.selector.common.result.EditSelectorResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectorSteps implements En {

    public SelectorSteps() {
        When("^the user begins entering a new selector$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorForm).isNull();
                    assertThat(persona.deleteSelectorForm).isNull();
                    assertThat(persona.selectorSpec).isNull();

                    persona.createSelectorForm = SelectorUtil.getHome().getCreateSelectorForm();
                });

        And("^the user adds the new selector$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorForm = persona.createSelectorForm;

                    assertThat(createSelectorForm).isNotNull();

                    var commandResult = SelectorUtil.getHome().createSelector(persona.userVisitPK, createSelectorForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateSelectorResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastSelectorName = commandResult.getHasErrors() ? null : result.getSelectorName();
                    persona.createSelectorForm = null;
                });

        When("^the user begins deleting a selector$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorForm).isNull();
                    assertThat(persona.deleteSelectorForm).isNull();
                    assertThat(persona.selectorSpec).isNull();

                    persona.deleteSelectorForm = SelectorUtil.getHome().getDeleteSelectorForm();
                });

        And("^the user deletes the selector$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteSelectorForm = persona.deleteSelectorForm;

                    assertThat(deleteSelectorForm).isNotNull();

                    LastCommandResult.commandResult = SelectorUtil.getHome().deleteSelector(persona.userVisitPK, deleteSelectorForm);

                    persona.deleteSelectorForm = null;
                });

        When("^the user begins specifying a selector to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorForm).isNull();
                    assertThat(persona.deleteSelectorForm).isNull();
                    assertThat(persona.selectorSpec).isNull();

                    persona.selectorSpec = SelectorUtil.getHome().getSelectorSpec();
                });

        When("^the user begins editing the selector$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.selectorSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = SelectorUtil.getHome().getEditSelectorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = SelectorUtil.getHome().editSelector(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditSelectorResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.selectorEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the selector$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.selectorSpec;
                    var edit = persona.selectorEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = SelectorUtil.getHome().getEditSelectorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = SelectorUtil.getHome().editSelector(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.selectorSpec = null;
                    persona.selectorEdit = null;
                });

        And("^the user sets the selector's selector kind name to ([a-zA-Z0-9-_]*)$",
                (String selectorKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorForm = persona.createSelectorForm;
                    var deleteSelectorForm = persona.deleteSelectorForm;
                    var selectorSpec = persona.selectorSpec;

                    assertThat(createSelectorForm != null || deleteSelectorForm != null || selectorSpec != null).isTrue();

                    if(createSelectorForm != null) {
                        createSelectorForm.setSelectorKindName(selectorKindName);
                    } else if(deleteSelectorForm != null) {
                        deleteSelectorForm.setSelectorKindName(selectorKindName);
                    } else {
                        selectorSpec.setSelectorKindName(selectorKindName);
                    }
                });

        And("^the user sets the selector's selector type name to ([a-zA-Z0-9-_]*)$",
                (String selectorTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorForm = persona.createSelectorForm;
                    var deleteSelectorForm = persona.deleteSelectorForm;
                    var selectorSpec = persona.selectorSpec;

                    assertThat(createSelectorForm != null || deleteSelectorForm != null || selectorSpec != null).isTrue();

                    if(createSelectorForm != null) {
                        createSelectorForm.setSelectorTypeName(selectorTypeName);
                    } else if(deleteSelectorForm != null) {
                        deleteSelectorForm.setSelectorTypeName(selectorTypeName);
                    } else {
                        selectorSpec.setSelectorTypeName(selectorTypeName);
                    }
                });

        And("^the user sets the selector's name to ([a-zA-Z0-9-_]*)$",
                (String selectorName) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorForm = persona.createSelectorForm;
                    var deleteSelectorForm = persona.deleteSelectorForm;
                    var selectorSpec = persona.selectorSpec;

                    assertThat(createSelectorForm != null || deleteSelectorForm != null || selectorSpec != null).isTrue();

                    if(createSelectorForm != null) {
                        createSelectorForm.setSelectorName(selectorName);
                    } else if(deleteSelectorForm != null) {
                        deleteSelectorForm.setSelectorName(selectorName);
                    } else {
                        selectorSpec.setSelectorName(selectorName);
                    }
                });

        And("^the user sets the selector's name to the last selector added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastSelectorName = persona.lastSelectorName;
                    var deleteSelectorForm = persona.deleteSelectorForm;
                    var selectorSpec = persona.selectorSpec;

                    assertThat(deleteSelectorForm != null || selectorSpec != null).isTrue();

                    if(deleteSelectorForm != null) {
                        deleteSelectorForm.setSelectorName(lastSelectorName);
                    } else {
                        selectorSpec.setSelectorName(lastSelectorName);
                    }
                });

        And("^the user sets the selector to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorForm = persona.createSelectorForm;
                    var selectorEdit = persona.selectorEdit;

                    assertThat(createSelectorForm != null || selectorEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createSelectorForm != null) {
                        createSelectorForm.setIsDefault(isDefault);
                    } else {
                        selectorEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the selector's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorForm = persona.createSelectorForm;
                    var selectorEdit = persona.selectorEdit;

                    assertThat(createSelectorForm != null || selectorEdit != null).isTrue();

                    if(createSelectorForm != null) {
                        createSelectorForm.setSortOrder(sortOrder);
                    } else {
                        selectorEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the selector's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorForm = persona.createSelectorForm;
                    var selectorEdit = persona.selectorEdit;

                    assertThat(createSelectorForm != null || selectorEdit != null).isTrue();

                    if(createSelectorForm != null) {
                        createSelectorForm.setDescription(description);
                    } else {
                        selectorEdit.setDescription(description);
                    }
                });
    }

}
