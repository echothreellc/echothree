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
import com.echothree.control.user.filter.common.result.CreateFilterAdjustmentResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterAdjustmentSteps implements En {

    public FilterAdjustmentSteps() {
        When("^the user begins entering a new filter adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterAdjustmentForm).isNull();
                    assertThat(persona.deleteFilterAdjustmentForm).isNull();
                    assertThat(persona.filterAdjustmentSpec).isNull();

                    persona.createFilterAdjustmentForm = FilterUtil.getHome().getCreateFilterAdjustmentForm();
                });

        When("^the user adds the new filter adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;

                    assertThat(createFilterAdjustmentForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilterAdjustment(persona.userVisitPK, createFilterAdjustmentForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateFilterAdjustmentResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastFilterAdjustmentName = commandResult.getHasErrors() ? null : result.getFilterAdjustmentName();
                    persona.createFilterAdjustmentForm = null;
                });

        When("^the user begins deleting a filter adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterAdjustmentForm).isNull();
                    assertThat(persona.deleteFilterAdjustmentForm).isNull();
                    assertThat(persona.filterAdjustmentSpec).isNull();

                    persona.deleteFilterAdjustmentForm = FilterUtil.getHome().getDeleteFilterAdjustmentForm();
                });

        When("^the user deletes the filter adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterAdjustmentForm = persona.deleteFilterAdjustmentForm;

                    assertThat(deleteFilterAdjustmentForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilterAdjustment(persona.userVisitPK, deleteFilterAdjustmentForm);

                    persona.deleteFilterAdjustmentForm = null;
                });

        When("^the user begins specifying a filter adjustment to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterAdjustmentForm).isNull();
                    assertThat(persona.deleteFilterAdjustmentForm).isNull();
                    assertThat(persona.filterAdjustmentSpec).isNull();

                    persona.filterAdjustmentSpec = FilterUtil.getHome().getFilterAdjustmentSpec();
                });

        When("^the user begins editing the filter adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterAdjustmentSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterAdjustmentForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = FilterUtil.getHome().editFilterAdjustment(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditFilterAdjustmentResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.filterAdjustmentEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the filter adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterAdjustmentSpec;
                    var edit = persona.filterAdjustmentEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterAdjustmentForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = FilterUtil.getHome().editFilterAdjustment(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.filterAdjustmentSpec = null;
                    persona.filterAdjustmentEdit = null;
                });

        When("^the user sets the filter adjustment's filter kind name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;
                    var deleteFilterAdjustmentForm = persona.deleteFilterAdjustmentForm;
                    var filterAdjustmentSpec = persona.filterAdjustmentSpec;

                    assertThat(createFilterAdjustmentForm != null || deleteFilterAdjustmentForm != null || filterAdjustmentSpec != null).isTrue();

                    if(createFilterAdjustmentForm != null) {
                        createFilterAdjustmentForm.setFilterKindName(filterKindName);
                    } else if(deleteFilterAdjustmentForm != null) {
                        deleteFilterAdjustmentForm.setFilterKindName(filterKindName);
                    } else {
                        filterAdjustmentSpec.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter adjustment's name to ([a-zA-Z0-9-_]*)$",
                (String filterAdjustmentName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;
                    var deleteFilterAdjustmentForm = persona.deleteFilterAdjustmentForm;
                    var filterAdjustmentSpec = persona.filterAdjustmentSpec;

                    assertThat(createFilterAdjustmentForm != null || deleteFilterAdjustmentForm != null || filterAdjustmentSpec != null).isTrue();

                    if(createFilterAdjustmentForm != null) {
                        createFilterAdjustmentForm.setFilterAdjustmentName(filterAdjustmentName);
                    } else if(deleteFilterAdjustmentForm != null) {
                        deleteFilterAdjustmentForm.setFilterAdjustmentName(filterAdjustmentName);
                    } else {
                        filterAdjustmentSpec.setFilterAdjustmentName(filterAdjustmentName);
                    }
                });

        When("^the user sets the filter adjustment's name to the last filter adjustment added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastFilterAdjustmentName = persona.lastFilterAdjustmentName;
                    var deleteFilterAdjustmentForm = persona.deleteFilterAdjustmentForm;
                    var filterAdjustmentSpec = persona.filterAdjustmentSpec;

                    assertThat(deleteFilterAdjustmentForm != null || filterAdjustmentSpec != null).isTrue();

                    if(deleteFilterAdjustmentForm != null) {
                        deleteFilterAdjustmentForm.setFilterAdjustmentName(lastFilterAdjustmentName);
                    } else {
                        filterAdjustmentSpec.setFilterAdjustmentName(lastFilterAdjustmentName);
                    }
                });

        When("^the user sets the filter adjustment's filter adjustment source name to ([a-zA-Z0-9-_]*)$",
                (String filterAdjustmentSourceName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;
                    var filterAdjustmentEdit = persona.filterAdjustmentEdit;

                    assertThat(createFilterAdjustmentForm != null || filterAdjustmentEdit != null).isTrue();

                    if(createFilterAdjustmentForm != null) {
                        createFilterAdjustmentForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
                    } else {
                        filterAdjustmentEdit.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
                    }
                });

        When("^the user sets the filter adjustment's filter adjustment type name to ([a-zA-Z0-9-_]*)$",
                (String filterAdjustmentTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;

                    assertThat(createFilterAdjustmentForm != null).isTrue();

                    createFilterAdjustmentForm.setFilterAdjustmentTypeName(filterAdjustmentTypeName);
                });

        When("^the user sets the filter adjustment to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;
                    var filterAdjustmentEdit = persona.filterAdjustmentEdit;

                    assertThat(createFilterAdjustmentForm != null || filterAdjustmentEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createFilterAdjustmentForm != null) {
                        createFilterAdjustmentForm.setIsDefault(isDefault);
                    } else {
                        filterAdjustmentEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the filter adjustment's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;
                    var filterAdjustmentEdit = persona.filterAdjustmentEdit;

                    assertThat(createFilterAdjustmentForm != null || filterAdjustmentEdit != null).isTrue();

                    if(createFilterAdjustmentForm != null) {
                        createFilterAdjustmentForm.setSortOrder(sortOrder);
                    } else {
                        filterAdjustmentEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the filter adjustment's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterAdjustmentForm = persona.createFilterAdjustmentForm;
                    var filterAdjustmentEdit = persona.filterAdjustmentEdit;

                    assertThat(createFilterAdjustmentForm != null || filterAdjustmentEdit != null).isTrue();

                    if(createFilterAdjustmentForm != null) {
                        createFilterAdjustmentForm.setDescription(description);
                    } else {
                        filterAdjustmentEdit.setDescription(description);
                    }
                });
    }

}
