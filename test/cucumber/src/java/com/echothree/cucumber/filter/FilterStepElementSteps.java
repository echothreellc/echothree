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
import com.echothree.control.user.filter.common.result.CreateFilterStepElementResult;
import com.echothree.control.user.filter.common.result.CreateFilterStepResult;
import com.echothree.control.user.filter.common.result.EditFilterStepElementResult;
import com.echothree.control.user.filter.common.result.EditFilterStepResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterStepElementSteps implements En {

    public FilterStepElementSteps() {
        When("^the user begins entering a new filter step element$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepElementForm).isNull();
                    assertThat(persona.deleteFilterStepElementForm).isNull();
                    assertThat(persona.filterStepElementSpec).isNull();

                    persona.createFilterStepElementForm = FilterUtil.getHome().getCreateFilterStepElementForm();
                });

        When("^the user adds the new filter step element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;

                    assertThat(createFilterStepElementForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilterStepElement(persona.userVisitPK, createFilterStepElementForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateFilterStepElementResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastFilterStepElementName = commandResult.getHasErrors() ? null : result.getFilterStepElementName();
                    persona.createFilterStepElementForm = null;
                });

        When("^the user begins deleting a filter step element$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepElementForm).isNull();
                    assertThat(persona.deleteFilterStepElementForm).isNull();
                    assertThat(persona.filterStepElementSpec).isNull();

                    persona.deleteFilterStepElementForm = FilterUtil.getHome().getDeleteFilterStepElementForm();
                });

        When("^the user deletes the filter step element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterStepElementForm = persona.deleteFilterStepElementForm;

                    assertThat(deleteFilterStepElementForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilterStepElement(persona.userVisitPK, deleteFilterStepElementForm);

                    persona.deleteFilterStepElementForm = null;
                });

        When("^the user begins specifying a filter step element to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepElementForm).isNull();
                    assertThat(persona.deleteFilterStepElementForm).isNull();
                    assertThat(persona.filterStepElementSpec).isNull();

                    persona.filterStepElementSpec = FilterUtil.getHome().getFilterStepElementSpec();
                });

        When("^the user begins editing the filter step element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterStepElementSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterStepElementForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = FilterUtil.getHome().editFilterStepElement(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditFilterStepElementResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.filterStepElementEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the filter step element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterStepElementSpec;
                    var edit = persona.filterStepElementEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterStepElementForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = FilterUtil.getHome().editFilterStepElement(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.filterStepElementSpec = null;
                    persona.filterStepElementEdit = null;
                });

        When("^the user sets the filter step element's filter kind name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var deleteFilterStepElementForm = persona.deleteFilterStepElementForm;
                    var filterStepElementSpec = persona.filterStepElementSpec;

                    assertThat(createFilterStepElementForm != null || deleteFilterStepElementForm != null || filterStepElementSpec != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setFilterKindName(filterKindName);
                    } else if(deleteFilterStepElementForm != null) {
                        deleteFilterStepElementForm.setFilterKindName(filterKindName);
                    } else {
                        filterStepElementSpec.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter step element's filter type name to ([a-zA-Z0-9-_]*)$",
                (String filterTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var deleteFilterStepElementForm = persona.deleteFilterStepElementForm;
                    var filterStepElementSpec = persona.filterStepElementSpec;

                    assertThat(createFilterStepElementForm != null || deleteFilterStepElementForm != null || filterStepElementSpec != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setFilterTypeName(filterTypeName);
                    } else if(deleteFilterStepElementForm != null) {
                        deleteFilterStepElementForm.setFilterTypeName(filterTypeName);
                    } else {
                        filterStepElementSpec.setFilterTypeName(filterTypeName);
                    }
                });

        When("^the user sets the filter step element's filter name to ([a-zA-Z0-9-_]*)$",
                (String filterName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var deleteFilterStepElementForm = persona.deleteFilterStepElementForm;
                    var filterStepElementSpec = persona.filterStepElementSpec;

                    assertThat(createFilterStepElementForm != null || deleteFilterStepElementForm != null || filterStepElementSpec != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setFilterName(filterName);
                    } else if(deleteFilterStepElementForm != null) {
                        deleteFilterStepElementForm.setFilterName(filterName);
                    } else {
                        filterStepElementSpec.setFilterName(filterName);
                    }
                });

        When("^the user sets the filter step element's filter step name to ([a-zA-Z0-9-_]*)$",
                (String filterStepName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var deleteFilterStepElementForm = persona.deleteFilterStepElementForm;
                    var filterStepElementSpec = persona.filterStepElementSpec;

                    assertThat(createFilterStepElementForm != null || deleteFilterStepElementForm != null || filterStepElementSpec != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setFilterStepName(filterStepName);
                    } else if(deleteFilterStepElementForm != null) {
                        deleteFilterStepElementForm.setFilterStepName(filterStepName);
                    } else {
                        filterStepElementSpec.setFilterStepName(filterStepName);
                    }
                });

        When("^the user sets the filter step element's name to ([a-zA-Z0-9-_]*)$",
                (String filterStepElementName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var deleteFilterStepElementForm = persona.deleteFilterStepElementForm;
                    var filterStepElementSpec = persona.filterStepElementSpec;

                    assertThat(createFilterStepElementForm != null || deleteFilterStepElementForm != null || filterStepElementSpec != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setFilterStepElementName(filterStepElementName);
                    } else if(deleteFilterStepElementForm != null) {
                        deleteFilterStepElementForm.setFilterStepElementName(filterStepElementName);
                    } else {
                        filterStepElementSpec.setFilterStepElementName(filterStepElementName);
                    }
                });

        When("^the user sets the filter step element's name to the last filter step element added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastFilterStepElementName = persona.lastFilterStepElementName;
                    var deleteFilterStepElementForm = persona.deleteFilterStepElementForm;
                    var filterStepElementSpec = persona.filterStepElementSpec;

                    assertThat(deleteFilterStepElementForm != null || filterStepElementSpec != null).isTrue();

                    if(deleteFilterStepElementForm != null) {
                        deleteFilterStepElementForm.setFilterStepElementName(lastFilterStepElementName);
                    } else {
                        filterStepElementSpec.setFilterStepElementName(lastFilterStepElementName);
                    }
                });

        When("^the user sets the filter step element's filter item selector name to \"([^\"]*)\"$",
                (String filterItemSelectorName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var filterStepElementEdit = persona.filterStepElementEdit;

                    assertThat(createFilterStepElementForm != null || filterStepElementEdit != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setFilterItemSelectorName(filterItemSelectorName);
                    } else {
                        filterStepElementEdit.setFilterItemSelectorName(filterItemSelectorName);
                    }
                });

        When("^the user sets the filter step element's filter adjustment name to \"([^\"]*)\"$",
                (String filterAdjustmentName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var filterStepElementEdit = persona.filterStepElementEdit;

                    assertThat(createFilterStepElementForm != null || filterStepElementEdit != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setFilterAdjustmentName(filterAdjustmentName);
                    } else {
                        filterStepElementEdit.setFilterAdjustmentName(filterAdjustmentName);
                    }
                });

        When("^the user sets the filter step element's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepElementForm = persona.createFilterStepElementForm;
                    var filterStepElementEdit = persona.filterStepElementEdit;

                    assertThat(createFilterStepElementForm != null || filterStepElementEdit != null).isTrue();

                    if(createFilterStepElementForm != null) {
                        createFilterStepElementForm.setDescription(description);
                    } else {
                        filterStepElementEdit.setDescription(description);
                    }
                });
    }

}
