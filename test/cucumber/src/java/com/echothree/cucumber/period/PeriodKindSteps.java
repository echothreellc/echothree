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

package com.echothree.cucumber.period;

import com.echothree.control.user.period.common.PeriodUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PeriodKindSteps implements En {

    public PeriodKindSteps() {
        When("the user begins entering a new period kind",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPeriodKindForm).isNull();
                    assertThat(persona.deletePeriodKindForm).isNull();
                    assertThat(persona.periodKindSpec).isNull();

                    persona.createPeriodKindForm = PeriodUtil.getHome().getCreatePeriodKindForm();
                });

        And("the user adds the new period kind",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodKindForm = persona.createPeriodKindForm;

                    assertThat(createPeriodKindForm).isNotNull();

                    var commandResult = PeriodUtil.getHome().createPeriodKind(persona.userVisitPK, createPeriodKindForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastPeriodKindName = commandResult.getHasErrors() ? null : result.getPeriodKindName();
                    persona.createPeriodKindForm = null;
                });

        When("the user begins deleting a period kind",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPeriodKindForm).isNull();
                    assertThat(persona.deletePeriodKindForm).isNull();
                    assertThat(persona.periodKindSpec).isNull();

                    persona.deletePeriodKindForm = PeriodUtil.getHome().getDeletePeriodKindForm();
                });

        And("the user deletes the period kind",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deletePeriodKindForm = persona.deletePeriodKindForm;

                    assertThat(deletePeriodKindForm).isNotNull();

                    LastCommandResult.commandResult = PeriodUtil.getHome().deletePeriodKind(persona.userVisitPK, deletePeriodKindForm);

                    persona.deletePeriodKindForm = null;
                });

        When("the user begins specifying a period kind to edit",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPeriodKindForm).isNull();
                    assertThat(persona.deletePeriodKindForm).isNull();
                    assertThat(persona.periodKindSpec).isNull();

                    persona.periodKindSpec = PeriodUtil.getHome().getPeriodKindSpec();
                });

        When("the user begins editing the period kind",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.periodKindSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = PeriodUtil.getHome().getEditPeriodKindForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = PeriodUtil.getHome().editPeriodKind(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.periodKindEdit = result.getEdit();
                    }
                });

        And("the user finishes editing the period kind",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.periodKindSpec;
                    var edit = persona.periodKindEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = PeriodUtil.getHome().getEditPeriodKindForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = PeriodUtil.getHome().editPeriodKind(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.periodKindSpec = null;
                    persona.periodKindEdit = null;
                });
        
        And("the user sets the period kind's name to {string}",
                (String periodKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodKindForm = persona.createPeriodKindForm;
                    var deletePeriodKindForm = persona.deletePeriodKindForm;
                    var periodKindSpec = persona.periodKindSpec;

                    assertThat(createPeriodKindForm != null || deletePeriodKindForm != null || periodKindSpec != null).isTrue();

                    if(createPeriodKindForm != null) {
                        createPeriodKindForm.setPeriodKindName(periodKindName);
                    } else if(deletePeriodKindForm != null) {
                        deletePeriodKindForm.setPeriodKindName(periodKindName);
                    } else {
                        periodKindSpec.setPeriodKindName(periodKindName);
                    }
                });

        And("the user sets the period kind's name to the last period kind added",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastPeriodKindName = persona.lastPeriodKindName;
                    var deletePeriodKindForm = persona.deletePeriodKindForm;
                    var periodKindSpec = persona.periodKindSpec;

                    assertThat(deletePeriodKindForm != null || periodKindSpec != null).isTrue();

                    if(deletePeriodKindForm != null) {
                        deletePeriodKindForm.setPeriodKindName(lastPeriodKindName);
                    } else {
                        periodKindSpec.setPeriodKindName(lastPeriodKindName);
                    }
                });

        And("the user sets the period kind to {string} the default",
                (String isDefault) -> {
                    assertThat(isDefault).isIn("be", "not be");

                    var persona = CurrentPersona.persona;
                    var createPeriodKindForm = persona.createPeriodKindForm;
                    var periodKindEdit = persona.periodKindEdit;

                    assertThat(createPeriodKindForm != null || periodKindEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createPeriodKindForm != null) {
                        createPeriodKindForm.setIsDefault(isDefault);
                    } else {
                        periodKindEdit.setIsDefault(isDefault);
                    }
                });

        And("the user sets the period kind's sort order to {string}",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodKindForm = persona.createPeriodKindForm;
                    var periodKindEdit = persona.periodKindEdit;

                    assertThat(createPeriodKindForm != null || periodKindEdit != null).isTrue();

                    if(createPeriodKindForm != null) {
                        createPeriodKindForm.setSortOrder(sortOrder);
                    } else {
                        periodKindEdit.setSortOrder(sortOrder);
                    }
                });

        And("the user sets the period kind's description to {string}",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodKindForm = persona.createPeriodKindForm;
                    var periodKindEdit = persona.periodKindEdit;

                    assertThat(createPeriodKindForm != null || periodKindEdit != null).isTrue();

                    if(createPeriodKindForm != null) {
                        createPeriodKindForm.setDescription(description);
                    } else {
                        periodKindEdit.setDescription(description);
                    }
                });

        And("the user sets the period kind's workflow name to {string}",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodKindForm = persona.createPeriodKindForm;
                    var periodKindEdit = persona.periodKindEdit;

                    assertThat(createPeriodKindForm != null || periodKindEdit != null).isTrue();

                    if(createPeriodKindForm != null) {
                        createPeriodKindForm.setWorkflowName(value);
                    } else {
                        periodKindEdit.setWorkflowName(value);
                    }
                });

        And("the user sets the period kind's workflow entrance name to {string}",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodKindForm = persona.createPeriodKindForm;
                    var periodKindEdit = persona.periodKindEdit;

                    assertThat(createPeriodKindForm != null || periodKindEdit != null).isTrue();

                    if(createPeriodKindForm != null) {
                        createPeriodKindForm.setWorkflowEntranceName(value);
                    } else {
                        periodKindEdit.setWorkflowEntranceName(value);
                    }
                });
    }

}
