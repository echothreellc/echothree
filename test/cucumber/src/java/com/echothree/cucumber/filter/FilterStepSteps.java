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
import com.echothree.control.user.filter.common.result.CreateFilterStepResult;
import com.echothree.control.user.filter.common.result.EditFilterStepResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterStepSteps implements En {

    public FilterStepSteps() {
        When("^the user begins entering a new filter step$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepForm).isNull();
                    assertThat(persona.deleteFilterStepForm).isNull();
                    assertThat(persona.filterStepSpec).isNull();

                    persona.createFilterStepForm = FilterUtil.getHome().getCreateFilterStepForm();
                });

        When("^the user adds the new filter step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepForm = persona.createFilterStepForm;

                    assertThat(createFilterStepForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilterStep(persona.userVisitPK, createFilterStepForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateFilterStepResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastFilterStepName = commandResult.getHasErrors() ? null : result.getFilterStepName();
                    persona.createFilterStepForm = null;
                });

        When("^the user begins deleting a filter step$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepForm).isNull();
                    assertThat(persona.deleteFilterStepForm).isNull();
                    assertThat(persona.filterStepSpec).isNull();

                    persona.deleteFilterStepForm = FilterUtil.getHome().getDeleteFilterStepForm();
                });

        When("^the user deletes the filter step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterStepForm = persona.deleteFilterStepForm;

                    assertThat(deleteFilterStepForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilterStep(persona.userVisitPK, deleteFilterStepForm);

                    persona.deleteFilterStepForm = null;
                });

        When("^the user begins specifying a filter step to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepForm).isNull();
                    assertThat(persona.deleteFilterStepForm).isNull();
                    assertThat(persona.filterStepSpec).isNull();

                    persona.filterStepSpec = FilterUtil.getHome().getFilterStepSpec();
                });

        When("^the user begins editing the filter step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterStepSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterStepForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = FilterUtil.getHome().editFilterStep(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditFilterStepResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.filterStepEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the filter step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterStepSpec;
                    var edit = persona.filterStepEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterStepForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = FilterUtil.getHome().editFilterStep(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.filterStepSpec = null;
                    persona.filterStepEdit = null;
                });

        When("^the user sets the filter step's filter kind name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepForm = persona.createFilterStepForm;
                    var deleteFilterStepForm = persona.deleteFilterStepForm;
                    var filterStepSpec = persona.filterStepSpec;

                    assertThat(createFilterStepForm != null || deleteFilterStepForm != null || filterStepSpec != null).isTrue();

                    if(createFilterStepForm != null) {
                        createFilterStepForm.setFilterKindName(filterKindName);
                    } else if(deleteFilterStepForm != null) {
                        deleteFilterStepForm.setFilterKindName(filterKindName);
                    } else {
                        filterStepSpec.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter step's filter type name to ([a-zA-Z0-9-_]*)$",
                (String filterTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepForm = persona.createFilterStepForm;
                    var deleteFilterStepForm = persona.deleteFilterStepForm;
                    var filterStepSpec = persona.filterStepSpec;

                    assertThat(createFilterStepForm != null || deleteFilterStepForm != null || filterStepSpec != null).isTrue();

                    if(createFilterStepForm != null) {
                        createFilterStepForm.setFilterTypeName(filterTypeName);
                    } else if(deleteFilterStepForm != null) {
                        deleteFilterStepForm.setFilterTypeName(filterTypeName);
                    } else {
                        filterStepSpec.setFilterTypeName(filterTypeName);
                    }
                });

        When("^the user sets the filter step's filter name to ([a-zA-Z0-9-_]*)$",
                (String filterName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepForm = persona.createFilterStepForm;
                    var deleteFilterStepForm = persona.deleteFilterStepForm;
                    var filterStepSpec = persona.filterStepSpec;

                    assertThat(createFilterStepForm != null || deleteFilterStepForm != null || filterStepSpec != null).isTrue();

                    if(createFilterStepForm != null) {
                        createFilterStepForm.setFilterName(filterName);
                    } else if(deleteFilterStepForm != null) {
                        deleteFilterStepForm.setFilterName(filterName);
                    } else {
                        filterStepSpec.setFilterName(filterName);
                    }
                });

        When("^the user sets the filter step's name to ([a-zA-Z0-9-_]*)$",
                (String filterStepName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepForm = persona.createFilterStepForm;
                    var deleteFilterStepForm = persona.deleteFilterStepForm;
                    var filterStepSpec = persona.filterStepSpec;

                    assertThat(createFilterStepForm != null || deleteFilterStepForm != null || filterStepSpec != null).isTrue();

                    if(createFilterStepForm != null) {
                        createFilterStepForm.setFilterStepName(filterStepName);
                    } else if(deleteFilterStepForm != null) {
                        deleteFilterStepForm.setFilterStepName(filterStepName);
                    } else {
                        filterStepSpec.setFilterStepName(filterStepName);
                    }
                });

        When("^the user sets the filter step's name to the last filter step added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastFilterStepName = persona.lastFilterStepName;
                    var deleteFilterStepForm = persona.deleteFilterStepForm;
                    var filterStepSpec = persona.filterStepSpec;

                    assertThat(deleteFilterStepForm != null || filterStepSpec != null).isTrue();

                    if(deleteFilterStepForm != null) {
                        deleteFilterStepForm.setFilterStepName(lastFilterStepName);
                    } else {
                        filterStepSpec.setFilterStepName(lastFilterStepName);
                    }
                });

        When("^the user sets the filter step's filter item selector name to \"([^\"]*)\"$",
                (String filterItemSelectorName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepForm = persona.createFilterStepForm;
                    var filterStepEdit = persona.filterStepEdit;

                    assertThat(createFilterStepForm != null || filterStepEdit != null).isTrue();

                    if(createFilterStepForm != null) {
                        createFilterStepForm.setFilterItemSelectorName(filterItemSelectorName);
                    } else {
                        filterStepEdit.setFilterItemSelectorName(filterItemSelectorName);
                    }
                });

        When("^the user sets the filter step's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepForm = persona.createFilterStepForm;
                    var filterStepEdit = persona.filterStepEdit;

                    assertThat(createFilterStepForm != null || filterStepEdit != null).isTrue();

                    if(createFilterStepForm != null) {
                        createFilterStepForm.setDescription(description);
                    } else {
                        filterStepEdit.setDescription(description);
                    }
                });
    }

}
