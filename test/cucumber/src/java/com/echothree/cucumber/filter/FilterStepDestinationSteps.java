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
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterStepDestinationSteps implements En {

    public FilterStepDestinationSteps() {
        When("^the user begins entering a new filter step destination$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepDestinationForm).isNull();
                    assertThat(persona.deleteFilterStepDestinationForm).isNull();

                    persona.createFilterStepDestinationForm = FilterUtil.getHome().getCreateFilterStepDestinationForm();
                });

        When("^the user adds the new filter step destination$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepDestinationForm = persona.createFilterStepDestinationForm;

                    assertThat(createFilterStepDestinationForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilterStepDestination(persona.userVisitPK, createFilterStepDestinationForm);
                    LastCommandResult.commandResult = commandResult;

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();

                    persona.createFilterStepDestinationForm = null;
                });

        When("^the user begins deleting a filter step destination$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterStepDestinationForm).isNull();
                    assertThat(persona.deleteFilterStepDestinationForm).isNull();

                    persona.deleteFilterStepDestinationForm = FilterUtil.getHome().getDeleteFilterStepDestinationForm();
                });

        When("^the user deletes the filter step destination$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterStepDestinationForm = persona.deleteFilterStepDestinationForm;

                    assertThat(deleteFilterStepDestinationForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilterStepDestination(persona.userVisitPK, deleteFilterStepDestinationForm);

                    persona.deleteFilterStepDestinationForm = null;
                });

        When("^the user sets the filter step destination's filter kind name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepDestinationForm = persona.createFilterStepDestinationForm;
                    var deleteFilterStepDestinationForm = persona.deleteFilterStepDestinationForm;

                    assertThat(createFilterStepDestinationForm != null || deleteFilterStepDestinationForm != null).isTrue();

                    if(createFilterStepDestinationForm != null) {
                        createFilterStepDestinationForm.setFilterKindName(filterKindName);
                    } else {
                        deleteFilterStepDestinationForm.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter step destination's filter type name to ([a-zA-Z0-9-_]*)$",
                (String filterTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepDestinationForm = persona.createFilterStepDestinationForm;
                    var deleteFilterStepDestinationForm = persona.deleteFilterStepDestinationForm;

                    assertThat(createFilterStepDestinationForm != null || deleteFilterStepDestinationForm != null).isTrue();

                    if(createFilterStepDestinationForm != null) {
                        createFilterStepDestinationForm.setFilterTypeName(filterTypeName);
                    } else {
                        deleteFilterStepDestinationForm.setFilterTypeName(filterTypeName);
                    }
                });

        When("^the user sets the filter step destination's filter name to ([a-zA-Z0-9-_]*)$",
                (String filterName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepDestinationForm = persona.createFilterStepDestinationForm;
                    var deleteFilterStepDestinationForm = persona.deleteFilterStepDestinationForm;

                    assertThat(createFilterStepDestinationForm != null || deleteFilterStepDestinationForm != null).isTrue();

                    if(createFilterStepDestinationForm != null) {
                        createFilterStepDestinationForm.setFilterName(filterName);
                    } else {
                        deleteFilterStepDestinationForm.setFilterName(filterName);
                    }
                });

        When("^the user sets the filter step destination's from filter step name to ([a-zA-Z0-9-_]*)$",
                (String fromFilterStepName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepDestinationForm = persona.createFilterStepDestinationForm;
                    var deleteFilterStepDestinationForm = persona.deleteFilterStepDestinationForm;

                    assertThat(createFilterStepDestinationForm != null || deleteFilterStepDestinationForm != null).isTrue();

                    if(createFilterStepDestinationForm != null) {
                        createFilterStepDestinationForm.setFromFilterStepName(fromFilterStepName);
                    } else {
                        deleteFilterStepDestinationForm.setFromFilterStepName(fromFilterStepName);
                    }
                });

        When("^the user sets the filter step destination's to filter step name to ([a-zA-Z0-9-_]*)$",
                (String toFilterStepName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterStepDestinationForm = persona.createFilterStepDestinationForm;
                    var deleteFilterStepDestinationForm = persona.deleteFilterStepDestinationForm;

                    assertThat(createFilterStepDestinationForm != null || deleteFilterStepDestinationForm != null).isTrue();

                    if(createFilterStepDestinationForm != null) {
                        createFilterStepDestinationForm.setToFilterStepName(toFilterStepName);
                    } else {
                        deleteFilterStepDestinationForm.setToFilterStepName(toFilterStepName);
                    }
                });
    }

}
