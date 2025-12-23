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
import com.echothree.control.user.filter.common.result.CreateFilterTypeResult;
import com.echothree.control.user.filter.common.result.EditFilterTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterTypeSteps implements En {

    public FilterTypeSteps() {
        When("^the user begins entering a new filter type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterTypeForm).isNull();
                    assertThat(persona.deleteFilterTypeForm).isNull();
                    assertThat(persona.filterTypeSpec).isNull();

                    persona.createFilterTypeForm = FilterUtil.getHome().getCreateFilterTypeForm();
                });

        When("^the user adds the new filter type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterTypeForm = persona.createFilterTypeForm;

                    assertThat(createFilterTypeForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilterType(persona.userVisitPK, createFilterTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateFilterTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastFilterTypeName = commandResult.getHasErrors() ? null : result.getFilterTypeName();
                    persona.createFilterTypeForm = null;
                });

        When("^the user begins deleting a filter type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterTypeForm).isNull();
                    assertThat(persona.deleteFilterTypeForm).isNull();
                    assertThat(persona.filterTypeSpec).isNull();

                    persona.deleteFilterTypeForm = FilterUtil.getHome().getDeleteFilterTypeForm();
                });

        When("^the user deletes the filter type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterTypeForm = persona.deleteFilterTypeForm;

                    assertThat(deleteFilterTypeForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilterType(persona.userVisitPK, deleteFilterTypeForm);

                    persona.deleteFilterTypeForm = null;
                });

        When("^the user begins specifying a filter type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterTypeForm).isNull();
                    assertThat(persona.deleteFilterTypeForm).isNull();
                    assertThat(persona.filterTypeSpec).isNull();

                    persona.filterTypeSpec = FilterUtil.getHome().getFilterTypeSpec();
                });

        When("^the user begins editing the filter type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = FilterUtil.getHome().editFilterType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditFilterTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.filterTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the filter type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.filterTypeSpec;
                    var edit = persona.filterTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = FilterUtil.getHome().getEditFilterTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = FilterUtil.getHome().editFilterType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.filterTypeSpec = null;
                    persona.filterTypeEdit = null;
                });

        When("^the user sets the filter type's filter kind name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterTypeForm = persona.createFilterTypeForm;
                    var deleteFilterTypeForm = persona.deleteFilterTypeForm;
                    var filterTypeSpec = persona.filterTypeSpec;

                    assertThat(createFilterTypeForm != null || deleteFilterTypeForm != null || filterTypeSpec != null).isTrue();

                    if(createFilterTypeForm != null) {
                        createFilterTypeForm.setFilterKindName(filterKindName);
                    } else if(deleteFilterTypeForm != null) {
                        deleteFilterTypeForm.setFilterKindName(filterKindName);
                    } else {
                        filterTypeSpec.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter type's name to ([a-zA-Z0-9-_]*)$",
                (String filterTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterTypeForm = persona.createFilterTypeForm;
                    var deleteFilterTypeForm = persona.deleteFilterTypeForm;
                    var filterTypeSpec = persona.filterTypeSpec;

                    assertThat(createFilterTypeForm != null || deleteFilterTypeForm != null || filterTypeSpec != null).isTrue();

                    if(createFilterTypeForm != null) {
                        createFilterTypeForm.setFilterTypeName(filterTypeName);
                    } else if(deleteFilterTypeForm != null) {
                        deleteFilterTypeForm.setFilterTypeName(filterTypeName);
                    } else {
                        filterTypeSpec.setFilterTypeName(filterTypeName);
                    }
                });

        When("^the user sets the filter type's name to the last filter type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastFilterTypeName = persona.lastFilterTypeName;
                    var deleteFilterTypeForm = persona.deleteFilterTypeForm;
                    var filterTypeSpec = persona.filterTypeSpec;

                    assertThat(deleteFilterTypeForm != null || filterTypeSpec != null).isTrue();

                    if(deleteFilterTypeForm != null) {
                        deleteFilterTypeForm.setFilterTypeName(lastFilterTypeName);
                    } else {
                        filterTypeSpec.setFilterTypeName(lastFilterTypeName);
                    }
                });

        When("^the user sets the filter type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterTypeForm = persona.createFilterTypeForm;
                    var filterTypeEdit = persona.filterTypeEdit;

                    assertThat(createFilterTypeForm != null || filterTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createFilterTypeForm != null) {
                        createFilterTypeForm.setIsDefault(isDefault);
                    } else {
                        filterTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the filter type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterTypeForm = persona.createFilterTypeForm;
                    var filterTypeEdit = persona.filterTypeEdit;

                    assertThat(createFilterTypeForm != null || filterTypeEdit != null).isTrue();

                    if(createFilterTypeForm != null) {
                        createFilterTypeForm.setSortOrder(sortOrder);
                    } else {
                        filterTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the filter type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterTypeForm = persona.createFilterTypeForm;
                    var filterTypeEdit = persona.filterTypeEdit;

                    assertThat(createFilterTypeForm != null || filterTypeEdit != null).isTrue();

                    if(createFilterTypeForm != null) {
                        createFilterTypeForm.setDescription(description);
                    } else {
                        filterTypeEdit.setDescription(description);
                    }
                });
    }

}
