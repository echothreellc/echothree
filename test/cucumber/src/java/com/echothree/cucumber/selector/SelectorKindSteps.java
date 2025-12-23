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
import com.echothree.control.user.selector.common.result.CreateSelectorKindResult;
import com.echothree.control.user.selector.common.result.EditSelectorKindResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectorKindSteps implements En {

    public SelectorKindSteps() {
        When("^the user begins entering a new selector kind$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorKindForm).isNull();
                    assertThat(persona.deleteSelectorKindForm).isNull();
                    assertThat(persona.selectorKindSpec).isNull();

                    persona.createSelectorKindForm = SelectorUtil.getHome().getCreateSelectorKindForm();
                });

        And("^the user adds the new selector kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorKindForm = persona.createSelectorKindForm;

                    assertThat(createSelectorKindForm).isNotNull();

                    var commandResult = SelectorUtil.getHome().createSelectorKind(persona.userVisitPK, createSelectorKindForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateSelectorKindResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastSelectorKindName = commandResult.getHasErrors() ? null : result.getSelectorKindName();
                    persona.createSelectorKindForm = null;
                });

        When("^the user begins deleting a selector kind$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorKindForm).isNull();
                    assertThat(persona.deleteSelectorKindForm).isNull();
                    assertThat(persona.selectorKindSpec).isNull();

                    persona.deleteSelectorKindForm = SelectorUtil.getHome().getDeleteSelectorKindForm();
                });

        And("^the user deletes the selector kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteSelectorKindForm = persona.deleteSelectorKindForm;

                    assertThat(deleteSelectorKindForm).isNotNull();

                    LastCommandResult.commandResult = SelectorUtil.getHome().deleteSelectorKind(persona.userVisitPK, deleteSelectorKindForm);

                    persona.deleteSelectorKindForm = null;
                });

        When("^the user begins specifying a selector kind to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSelectorKindForm).isNull();
                    assertThat(persona.deleteSelectorKindForm).isNull();
                    assertThat(persona.selectorKindSpec).isNull();

                    persona.selectorKindSpec = SelectorUtil.getHome().getSelectorKindSpec();
                });

        When("^the user begins editing the selector kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.selectorKindSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = SelectorUtil.getHome().getEditSelectorKindForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = SelectorUtil.getHome().editSelectorKind(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditSelectorKindResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.selectorKindEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the selector kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.selectorKindSpec;
                    var edit = persona.selectorKindEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = SelectorUtil.getHome().getEditSelectorKindForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = SelectorUtil.getHome().editSelectorKind(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.selectorKindSpec = null;
                    persona.selectorKindEdit = null;
                });
        
        And("^the user sets the selector kind's name to ([a-zA-Z0-9-_]*)$",
                (String selectorKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorKindForm = persona.createSelectorKindForm;
                    var deleteSelectorKindForm = persona.deleteSelectorKindForm;
                    var selectorKindSpec = persona.selectorKindSpec;

                    assertThat(createSelectorKindForm != null || deleteSelectorKindForm != null || selectorKindSpec != null).isTrue();

                    if(createSelectorKindForm != null) {
                        createSelectorKindForm.setSelectorKindName(selectorKindName);
                    } else if(deleteSelectorKindForm != null) {
                        deleteSelectorKindForm.setSelectorKindName(selectorKindName);
                    } else {
                        selectorKindSpec.setSelectorKindName(selectorKindName);
                    }
                });

        And("^the user sets the selector kind's name to the last selector kind added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastSelectorKindName = persona.lastSelectorKindName;
                    var deleteSelectorKindForm = persona.deleteSelectorKindForm;
                    var selectorKindSpec = persona.selectorKindSpec;

                    assertThat(deleteSelectorKindForm != null || selectorKindSpec != null).isTrue();

                    if(deleteSelectorKindForm != null) {
                        deleteSelectorKindForm.setSelectorKindName(lastSelectorKindName);
                    } else {
                        selectorKindSpec.setSelectorKindName(lastSelectorKindName);
                    }
                });

        And("^the user sets the selector kind to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorKindForm = persona.createSelectorKindForm;
                    var selectorKindEdit = persona.selectorKindEdit;

                    assertThat(createSelectorKindForm != null || selectorKindEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createSelectorKindForm != null) {
                        createSelectorKindForm.setIsDefault(isDefault);
                    } else {
                        selectorKindEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the selector kind's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorKindForm = persona.createSelectorKindForm;
                    var selectorKindEdit = persona.selectorKindEdit;

                    assertThat(createSelectorKindForm != null || selectorKindEdit != null).isTrue();

                    if(createSelectorKindForm != null) {
                        createSelectorKindForm.setSortOrder(sortOrder);
                    } else {
                        selectorKindEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the selector kind's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createSelectorKindForm = persona.createSelectorKindForm;
                    var selectorKindEdit = persona.selectorKindEdit;

                    assertThat(createSelectorKindForm != null || selectorKindEdit != null).isTrue();

                    if(createSelectorKindForm != null) {
                        createSelectorKindForm.setDescription(description);
                    } else {
                        selectorKindEdit.setDescription(description);
                    }
                });
    }

}
