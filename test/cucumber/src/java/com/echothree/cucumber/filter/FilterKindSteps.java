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

package com.echothree.cucumber.filter;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.CreateFilterKindResult;
import com.echothree.control.user.filter.common.result.EditFilterKindResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterKindSteps implements En {

    public FilterKindSteps() {
        When("^the user begins entering a new filter kind$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterKindForm).isNull();
                    assertThat(persona.deleteFilterKindForm).isNull();
                    assertThat(persona.filterKindSpec).isNull();

                    persona.createFilterKindForm = FilterUtil.getHome().getCreateFilterKindForm();
                });

        When("^the user adds the new filter kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterKindForm = persona.createFilterKindForm;

                    assertThat(createFilterKindForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilterKind(persona.userVisitPK, createFilterKindForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateFilterKindResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastFilterKindName = commandResult.getHasErrors() ? null : result.getFilterKindName();
                    persona.createFilterKindForm = null;
                });

        When("^the user begins deleting a filter kind$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterKindForm).isNull();
                    assertThat(persona.deleteFilterKindForm).isNull();
                    assertThat(persona.filterKindSpec).isNull();

                    persona.deleteFilterKindForm = FilterUtil.getHome().getDeleteFilterKindForm();
                });

        When("^the user deletes the filter kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterKindForm = persona.deleteFilterKindForm;

                    assertThat(deleteFilterKindForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilterKind(persona.userVisitPK, deleteFilterKindForm);

                    persona.deleteFilterKindForm = null;
                });

        When("^the user begins specifying a filter kind to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterKindForm).isNull();
                    assertThat(persona.deleteFilterKindForm).isNull();
                    assertThat(persona.filterKindSpec).isNull();

                    persona.filterKindSpec = FilterUtil.getHome().getFilterKindSpec();
                });

        When("^the user begins editing the filter kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterKindSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterKindForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = FilterUtil.getHome().editFilterKind(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditFilterKindResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.filterKindEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the filter kind$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterKindSpec;
                    var edit = persona.filterKindEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterKindForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = FilterUtil.getHome().editFilterKind(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.filterKindSpec = null;
                    persona.filterKindEdit = null;
                });
        
        When("^the user sets the filter kind's name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterKindForm = persona.createFilterKindForm;
                    var deleteFilterKindForm = persona.deleteFilterKindForm;
                    var filterKindSpec = persona.filterKindSpec;

                    assertThat(createFilterKindForm != null || deleteFilterKindForm != null || filterKindSpec != null).isTrue();

                    if(createFilterKindForm != null) {
                        createFilterKindForm.setFilterKindName(filterKindName);
                    } else if(deleteFilterKindForm != null) {
                        deleteFilterKindForm.setFilterKindName(filterKindName);
                    } else {
                        filterKindSpec.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter kind's name to the last filter kind added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastFilterKindName = persona.lastFilterKindName;
                    var deleteFilterKindForm = persona.deleteFilterKindForm;
                    var filterKindSpec = persona.filterKindSpec;

                    assertThat(deleteFilterKindForm != null || filterKindSpec != null).isTrue();

                    if(deleteFilterKindForm != null) {
                        deleteFilterKindForm.setFilterKindName(lastFilterKindName);
                    } else {
                        filterKindSpec.setFilterKindName(lastFilterKindName);
                    }
                });

        When("^the user sets the filter kind to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterKindForm = persona.createFilterKindForm;
                    var filterKindEdit = persona.filterKindEdit;

                    assertThat(createFilterKindForm != null || filterKindEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createFilterKindForm != null) {
                        createFilterKindForm.setIsDefault(isDefault);
                    } else {
                        filterKindEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the filter kind's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterKindForm = persona.createFilterKindForm;
                    var filterKindEdit = persona.filterKindEdit;

                    assertThat(createFilterKindForm != null || filterKindEdit != null).isTrue();

                    if(createFilterKindForm != null) {
                        createFilterKindForm.setSortOrder(sortOrder);
                    } else {
                        filterKindEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the filter kind's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterKindForm = persona.createFilterKindForm;
                    var filterKindEdit = persona.filterKindEdit;

                    assertThat(createFilterKindForm != null || filterKindEdit != null).isTrue();

                    if(createFilterKindForm != null) {
                        createFilterKindForm.setDescription(description);
                    } else {
                        filterKindEdit.setDescription(description);
                    }
                });
    }

}
