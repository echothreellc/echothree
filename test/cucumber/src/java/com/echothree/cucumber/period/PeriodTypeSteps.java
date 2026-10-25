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

public class PeriodTypeSteps implements En {

    public PeriodTypeSteps() {
        When("the user begins entering a new period type",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPeriodTypeForm).isNull();
                    assertThat(persona.deletePeriodTypeForm).isNull();
                    assertThat(persona.periodTypeSpec).isNull();

                    persona.createPeriodTypeForm = PeriodUtil.getHome().getCreatePeriodTypeForm();
                });

        And("the user adds the new period type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;

                    assertThat(createPeriodTypeForm).isNotNull();

                    var commandResult = PeriodUtil.getHome().createPeriodType(persona.userVisitPK, createPeriodTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastPeriodTypeName = commandResult.getHasErrors() ? null : result.getPeriodTypeName();
                    persona.createPeriodTypeForm = null;
                });

        When("the user begins deleting a period type",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPeriodTypeForm).isNull();
                    assertThat(persona.deletePeriodTypeForm).isNull();
                    assertThat(persona.periodTypeSpec).isNull();

                    persona.deletePeriodTypeForm = PeriodUtil.getHome().getDeletePeriodTypeForm();
                });

        And("the user deletes the period type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deletePeriodTypeForm = persona.deletePeriodTypeForm;

                    assertThat(deletePeriodTypeForm).isNotNull();

                    LastCommandResult.commandResult = PeriodUtil.getHome().deletePeriodType(persona.userVisitPK, deletePeriodTypeForm);

                    persona.deletePeriodTypeForm = null;
                });

        When("the user begins specifying a period type to edit",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPeriodTypeForm).isNull();
                    assertThat(persona.deletePeriodTypeForm).isNull();
                    assertThat(persona.periodTypeSpec).isNull();

                    persona.periodTypeSpec = PeriodUtil.getHome().getPeriodTypeSpec();
                });

        When("the user begins editing the period type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.periodTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = PeriodUtil.getHome().getEditPeriodTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = PeriodUtil.getHome().editPeriodType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.periodTypeEdit = result.getEdit();
                    }
                });

        And("the user finishes editing the period type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.periodTypeSpec;
                    var edit = persona.periodTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = PeriodUtil.getHome().getEditPeriodTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = PeriodUtil.getHome().editPeriodType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.periodTypeSpec = null;
                    persona.periodTypeEdit = null;
                });

        And("the user sets the period type's period kind name to {string}",
                (String periodKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var deletePeriodTypeForm = persona.deletePeriodTypeForm;
                    var periodTypeSpec = persona.periodTypeSpec;

                    assertThat(createPeriodTypeForm != null || deletePeriodTypeForm != null || periodTypeSpec != null).isTrue();

                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setPeriodKindName(periodKindName);
                    } else if(deletePeriodTypeForm != null) {
                        deletePeriodTypeForm.setPeriodKindName(periodKindName);
                    } else {
                        periodTypeSpec.setPeriodKindName(periodKindName);
                    }
                });

        And("the user sets the period type's name to {string}",
                (String periodTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var deletePeriodTypeForm = persona.deletePeriodTypeForm;
                    var periodTypeSpec = persona.periodTypeSpec;

                    assertThat(createPeriodTypeForm != null || deletePeriodTypeForm != null || periodTypeSpec != null).isTrue();

                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setPeriodTypeName(periodTypeName);
                    } else if(deletePeriodTypeForm != null) {
                        deletePeriodTypeForm.setPeriodTypeName(periodTypeName);
                    } else {
                        periodTypeSpec.setPeriodTypeName(periodTypeName);
                    }
                });

        And("the user sets the period type's name to the last period type added",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastPeriodTypeName = persona.lastPeriodTypeName;
                    var deletePeriodTypeForm = persona.deletePeriodTypeForm;
                    var periodTypeSpec = persona.periodTypeSpec;

                    assertThat(deletePeriodTypeForm != null || periodTypeSpec != null).isTrue();

                    if(deletePeriodTypeForm != null) {
                        deletePeriodTypeForm.setPeriodTypeName(lastPeriodTypeName);
                    } else {
                        periodTypeSpec.setPeriodTypeName(lastPeriodTypeName);
                    }
                });

        And("the user sets the period type to {string} the default",
                (String isDefault) -> {
                    assertThat(isDefault).isIn("be", "not be");

                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var periodTypeEdit = persona.periodTypeEdit;

                    assertThat(createPeriodTypeForm != null || periodTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setIsDefault(isDefault);
                    } else {
                        periodTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("the user sets the period type's sort order to {string}",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var periodTypeEdit = persona.periodTypeEdit;

                    assertThat(createPeriodTypeForm != null || periodTypeEdit != null).isTrue();

                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setSortOrder(sortOrder);
                    } else {
                        periodTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("the user sets the period type's description to {string}",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var periodTypeEdit = persona.periodTypeEdit;

                    assertThat(createPeriodTypeForm != null || periodTypeEdit != null).isTrue();

                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setDescription(description);
                    } else {
                        periodTypeEdit.setDescription(description);
                    }
                });

        And("the user sets the period type's parent period type name to {string}",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var periodTypeEdit = persona.periodTypeEdit;

                    assertThat(createPeriodTypeForm != null || periodTypeEdit != null).isTrue();

                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setParentPeriodTypeName(value);
                    } else {
                        periodTypeEdit.setParentPeriodTypeName(value);
                    }
                });

        And("the user sets the period type's workflow name to {string}",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var periodTypeEdit = persona.periodTypeEdit;

                    assertThat(createPeriodTypeForm != null || periodTypeEdit != null).isTrue();

                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setWorkflowName(value);
                    } else {
                        periodTypeEdit.setWorkflowName(value);
                    }
                });

        And("the user sets the period type's workflow entrance name to {string}",
                (String value) -> {
                    var persona = CurrentPersona.persona;
                    var createPeriodTypeForm = persona.createPeriodTypeForm;
                    var periodTypeEdit = persona.periodTypeEdit;

                    assertThat(createPeriodTypeForm != null || periodTypeEdit != null).isTrue();

                    if(createPeriodTypeForm != null) {
                        createPeriodTypeForm.setWorkflowEntranceName(value);
                    } else {
                        periodTypeEdit.setWorkflowEntranceName(value);
                    }
                });

        Then("the period type's parent period type name should be {string}",
                (String parentPeriodTypeName) -> {
                    var periodTypeEdit = CurrentPersona.persona.periodTypeEdit;

                    assertThat(periodTypeEdit).isNotNull();
                    assertThat(periodTypeEdit.getParentPeriodTypeName()).isEqualTo(parentPeriodTypeName);
                });
    }

}
