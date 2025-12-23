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
import com.echothree.control.user.filter.common.result.CreateFilterResult;
import com.echothree.control.user.filter.common.result.EditFilterResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterSteps implements En {

    public FilterSteps() {
        When("^the user begins entering a new filter$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterForm).isNull();
                    assertThat(persona.deleteFilterForm).isNull();
                    assertThat(persona.filterSpec).isNull();

                    persona.createFilterForm = FilterUtil.getHome().getCreateFilterForm();
                });

        When("^the user adds the new filter$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;

                    assertThat(createFilterForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilter(persona.userVisitPK, createFilterForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateFilterResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastFilterName = commandResult.getHasErrors() ? null : result.getFilterName();
                    persona.createFilterForm = null;
                });

        When("^the user begins deleting a filter$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterForm).isNull();
                    assertThat(persona.deleteFilterForm).isNull();
                    assertThat(persona.filterSpec).isNull();

                    persona.deleteFilterForm = FilterUtil.getHome().getDeleteFilterForm();
                });

        When("^the user deletes the filter$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterForm = persona.deleteFilterForm;

                    assertThat(deleteFilterForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilter(persona.userVisitPK, deleteFilterForm);

                    persona.deleteFilterForm = null;
                });

        When("^the user begins specifying a filter to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterForm).isNull();
                    assertThat(persona.deleteFilterForm).isNull();
                    assertThat(persona.filterSpec).isNull();

                    persona.filterSpec = FilterUtil.getHome().getFilterSpec();
                });

        When("^the user begins editing the filter$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = FilterUtil.getHome().editFilter(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditFilterResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.filterEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the filter$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterSpec;
                    var edit = persona.filterEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = FilterUtil.getHome().editFilter(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.filterSpec = null;
                    persona.filterEdit = null;
                });

        When("^the user sets the filter's filter kind name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var deleteFilterForm = persona.deleteFilterForm;
                    var filterSpec = persona.filterSpec;

                    assertThat(createFilterForm != null || deleteFilterForm != null || filterSpec != null).isTrue();

                    if(createFilterForm != null) {
                        createFilterForm.setFilterKindName(filterKindName);
                    } else if(deleteFilterForm != null) {
                        deleteFilterForm.setFilterKindName(filterKindName);
                    } else {
                        filterSpec.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter's filter type name to ([a-zA-Z0-9-_]*)$",
                (String filterTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var deleteFilterForm = persona.deleteFilterForm;
                    var filterSpec = persona.filterSpec;

                    assertThat(createFilterForm != null || deleteFilterForm != null || filterSpec != null).isTrue();

                    if(createFilterForm != null) {
                        createFilterForm.setFilterTypeName(filterTypeName);
                    } else if(deleteFilterForm != null) {
                        deleteFilterForm.setFilterTypeName(filterTypeName);
                    } else {
                        filterSpec.setFilterTypeName(filterTypeName);
                    }
                });

        When("^the user sets the filter's name to ([a-zA-Z0-9-_]*)$",
                (String filterName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var deleteFilterForm = persona.deleteFilterForm;
                    var filterSpec = persona.filterSpec;

                    assertThat(createFilterForm != null || deleteFilterForm != null || filterSpec != null).isTrue();

                    if(createFilterForm != null) {
                        createFilterForm.setFilterName(filterName);
                    } else if(deleteFilterForm != null) {
                        deleteFilterForm.setFilterName(filterName);
                    } else {
                        filterSpec.setFilterName(filterName);
                    }
                });

        When("^the user sets the filter's name to the last filter added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastFilterName = persona.lastFilterName;
                    var deleteFilterForm = persona.deleteFilterForm;
                    var filterSpec = persona.filterSpec;

                    assertThat(deleteFilterForm != null || filterSpec != null).isTrue();

                    if(deleteFilterForm != null) {
                        deleteFilterForm.setFilterName(lastFilterName);
                    } else {
                        filterSpec.setFilterName(lastFilterName);
                    }
                });

        When("^the user sets the filter's initial filter adjustment name to \"([^\"]*)\"$",
                (String initialFilterAdjustmentName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var filterEdit = persona.filterEdit;

                    assertThat(createFilterForm != null || filterEdit != null).isTrue();

                    if(createFilterForm != null) {
                        createFilterForm.setInitialFilterAdjustmentName(initialFilterAdjustmentName);
                    } else {
                        filterEdit.setInitialFilterAdjustmentName(initialFilterAdjustmentName);
                    }
                });

        When("^the user sets the filter's filter item selector name to \"([^\"]*)\"$",
                (String filterItemSelectorName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var filterEdit = persona.filterEdit;

                    assertThat(createFilterForm != null || filterEdit != null).isTrue();

                    if(createFilterForm != null) {
                        createFilterForm.setFilterItemSelectorName(filterItemSelectorName);
                    } else {
                        filterEdit.setFilterItemSelectorName(filterItemSelectorName);
                    }
                });

        When("^the user sets the filter to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var filterEdit = persona.filterEdit;

                    assertThat(createFilterForm != null || filterEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createFilterForm != null) {
                        createFilterForm.setIsDefault(isDefault);
                    } else {
                        filterEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the filter's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var filterEdit = persona.filterEdit;

                    assertThat(createFilterForm != null || filterEdit != null).isTrue();

                    if(createFilterForm != null) {
                        createFilterForm.setSortOrder(sortOrder);
                    } else {
                        filterEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the filter's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterForm = persona.createFilterForm;
                    var filterEdit = persona.filterEdit;

                    assertThat(createFilterForm != null || filterEdit != null).isTrue();

                    if(createFilterForm != null) {
                        createFilterForm.setDescription(description);
                    } else {
                        filterEdit.setDescription(description);
                    }
                });
    }

}
