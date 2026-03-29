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

public class FilterEntranceStepSteps implements En {

    public FilterEntranceStepSteps() {
        When("^the user begins entering a new filter entrance step$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterEntranceStepForm).isNull();
                    assertThat(persona.deleteFilterEntranceStepForm).isNull();

                    persona.createFilterEntranceStepForm = FilterUtil.getHome().getCreateFilterEntranceStepForm();
                });

        When("^the user adds the new filter entrance step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createFilterEntranceStepForm = persona.createFilterEntranceStepForm;

                    assertThat(createFilterEntranceStepForm).isNotNull();

                    var commandResult = FilterUtil.getHome().createFilterEntranceStep(persona.userVisitPK, createFilterEntranceStepForm);
                    LastCommandResult.commandResult = commandResult;

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();

                    persona.createFilterEntranceStepForm = null;
                });

        When("^the user begins deleting a filter entrance step$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createFilterEntranceStepForm).isNull();
                    assertThat(persona.deleteFilterEntranceStepForm).isNull();

                    persona.deleteFilterEntranceStepForm = FilterUtil.getHome().getDeleteFilterEntranceStepForm();
                });

        When("^the user deletes the filter entrance step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteFilterEntranceStepForm = persona.deleteFilterEntranceStepForm;

                    assertThat(deleteFilterEntranceStepForm).isNotNull();

                    LastCommandResult.commandResult = FilterUtil.getHome().deleteFilterEntranceStep(persona.userVisitPK, deleteFilterEntranceStepForm);

                    persona.deleteFilterEntranceStepForm = null;
                });

        When("^the user sets the filter entrance step's filter kind name to ([a-zA-Z0-9-_]*)$",
                (String filterKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterEntranceStepForm = persona.createFilterEntranceStepForm;
                    var deleteFilterEntranceStepForm = persona.deleteFilterEntranceStepForm;

                    assertThat(createFilterEntranceStepForm != null || deleteFilterEntranceStepForm != null).isTrue();

                    if(createFilterEntranceStepForm != null) {
                        createFilterEntranceStepForm.setFilterKindName(filterKindName);
                    } else {
                        deleteFilterEntranceStepForm.setFilterKindName(filterKindName);
                    }
                });

        When("^the user sets the filter entrance step's filter type name to ([a-zA-Z0-9-_]*)$",
                (String filterTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterEntranceStepForm = persona.createFilterEntranceStepForm;
                    var deleteFilterEntranceStepForm = persona.deleteFilterEntranceStepForm;

                    assertThat(createFilterEntranceStepForm != null || deleteFilterEntranceStepForm != null).isTrue();

                    if(createFilterEntranceStepForm != null) {
                        createFilterEntranceStepForm.setFilterTypeName(filterTypeName);
                    } else {
                        deleteFilterEntranceStepForm.setFilterTypeName(filterTypeName);
                    }
                });

        When("^the user sets the filter entrance step's filter name to ([a-zA-Z0-9-_]*)$",
                (String filterName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterEntranceStepForm = persona.createFilterEntranceStepForm;
                    var deleteFilterEntranceStepForm = persona.deleteFilterEntranceStepForm;

                    assertThat(createFilterEntranceStepForm != null || deleteFilterEntranceStepForm != null).isTrue();

                    if(createFilterEntranceStepForm != null) {
                        createFilterEntranceStepForm.setFilterName(filterName);
                    } else {
                        deleteFilterEntranceStepForm.setFilterName(filterName);
                    }
                });

        When("^the user sets the filter entrance step's filter step name to ([a-zA-Z0-9-_]*)$",
                (String filterStepName) -> {
                    var persona = CurrentPersona.persona;
                    var createFilterEntranceStepForm = persona.createFilterEntranceStepForm;
                    var deleteFilterEntranceStepForm = persona.deleteFilterEntranceStepForm;

                    assertThat(createFilterEntranceStepForm != null || deleteFilterEntranceStepForm != null).isTrue();

                    if(createFilterEntranceStepForm != null) {
                        createFilterEntranceStepForm.setFilterStepName(filterStepName);
                    } else {
                        deleteFilterEntranceStepForm.setFilterStepName(filterStepName);
                    }
                });
    }

}
