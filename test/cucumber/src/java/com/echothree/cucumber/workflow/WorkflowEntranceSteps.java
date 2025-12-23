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

package com.echothree.cucumber.workflow;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.CreateWorkflowEntranceResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowEntranceSteps implements En {

    public WorkflowEntranceSteps() {
        When("^the user begins entering a new workflow entrance",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowEntranceForm).isNull();
                    assertThat(persona.deleteWorkflowEntranceForm).isNull();
                    assertThat(persona.workflowEntranceUniversalSpec).isNull();

                    persona.createWorkflowEntranceForm = WorkflowUtil.getHome().getCreateWorkflowEntranceForm();
                });

        When("^the user adds the new workflow entrance",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntranceForm = persona.createWorkflowEntranceForm;

                    assertThat(createWorkflowEntranceForm).isNotNull();

                    var commandResult = WorkflowUtil.getHome().createWorkflowEntrance(persona.userVisitPK, createWorkflowEntranceForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateWorkflowEntranceResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastWorkflowEntranceName = commandResult.getHasErrors() ? null : result.getWorkflowEntranceName();
                    persona.createWorkflowEntranceForm = null;
                });

        When("^the user begins deleting a workflow entrance",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowEntranceForm).isNull();
                    assertThat(persona.deleteWorkflowEntranceForm).isNull();
                    assertThat(persona.workflowEntranceUniversalSpec).isNull();

                    persona.deleteWorkflowEntranceForm = WorkflowUtil.getHome().getDeleteWorkflowEntranceForm();
                });

        When("^the user deletes the workflow entrance",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteWorkflowEntranceForm = persona.deleteWorkflowEntranceForm;

                    assertThat(deleteWorkflowEntranceForm).isNotNull();

                    LastCommandResult.commandResult = WorkflowUtil.getHome().deleteWorkflowEntrance(persona.userVisitPK, deleteWorkflowEntranceForm);

                    persona.deleteWorkflowEntranceForm = null;
                });

        When("^the user begins specifying a workflow entrance to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowEntranceForm).isNull();
                    assertThat(persona.deleteWorkflowEntranceForm).isNull();
                    assertThat(persona.workflowEntranceUniversalSpec).isNull();

                    persona.workflowEntranceUniversalSpec = WorkflowUtil.getHome().getWorkflowEntranceUniversalSpec();
                });

        When("^the user begins editing the workflow entrance",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowEntranceUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowEntranceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WorkflowUtil.getHome().editWorkflowEntrance(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditWorkflowEntranceResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.workflowEntranceEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the workflow entrance",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowEntranceUniversalSpec;
                    var edit = persona.workflowEntranceEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowEntranceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = WorkflowUtil.getHome().editWorkflowEntrance(persona.userVisitPK, commandForm);

                    persona.workflowEntranceUniversalSpec = null;
                    persona.workflowEntranceEdit = null;
                });

        When("^the user sets the workflow entrance's workflow name to \"([^\"]*)\"$",
                (String workflowName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntranceForm = persona.createWorkflowEntranceForm;
                    var deleteWorkflowEntranceForm = persona.deleteWorkflowEntranceForm;
                    var workflowEntranceSpec = persona.workflowEntranceUniversalSpec;

                    assertThat(createWorkflowEntranceForm != null || deleteWorkflowEntranceForm != null || workflowEntranceSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowEntranceForm, () -> Objects.requireNonNullElse(deleteWorkflowEntranceForm, workflowEntranceSpec)).setWorkflowName(workflowName);
                });

        When("^the user sets the workflow entrance's workflow name to the last workflow added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowName = persona.lastWorkflowName;
                    var createWorkflowEntranceForm = persona.createWorkflowEntranceForm;
                    var deleteWorkflowEntranceForm = persona.deleteWorkflowEntranceForm;
                    var workflowEntranceSpec = persona.workflowEntranceUniversalSpec;

                    assertThat(createWorkflowEntranceForm != null || deleteWorkflowEntranceForm != null || workflowEntranceSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowEntranceForm, () -> Objects.requireNonNullElse(deleteWorkflowEntranceForm, workflowEntranceSpec)).setWorkflowName(lastWorkflowName);
                });

        When("^the user sets the workflow entrance's name to \"([^\"]*)\"$",
                (String workflowEntranceName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntranceForm = persona.createWorkflowEntranceForm;
                    var deleteWorkflowEntranceForm = persona.deleteWorkflowEntranceForm;
                    var workflowEntranceSpec = persona.workflowEntranceUniversalSpec;

                    assertThat(createWorkflowEntranceForm != null || deleteWorkflowEntranceForm != null || workflowEntranceSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowEntranceForm, () -> Objects.requireNonNullElse(deleteWorkflowEntranceForm, workflowEntranceSpec)).setWorkflowEntranceName(workflowEntranceName);
                });

        When("^the user sets the workflow entrance's name to the last workflow entrance added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowEntranceName = persona.lastWorkflowEntranceName;
                    var deleteWorkflowEntranceForm = persona.deleteWorkflowEntranceForm;
                    var workflowEntranceSpec = persona.workflowEntranceUniversalSpec;

                    assertThat(deleteWorkflowEntranceForm != null || workflowEntranceSpec != null).isTrue();

                    Objects.requireNonNullElse(deleteWorkflowEntranceForm, workflowEntranceSpec).setWorkflowEntranceName(lastWorkflowEntranceName);
                });

        When("^the user sets the workflow entrance to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntranceForm = persona.createWorkflowEntranceForm;
                    var workflowEntranceEdit = persona.workflowEntranceEdit;

                    assertThat(createWorkflowEntranceForm != null || workflowEntranceEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    Objects.requireNonNullElse(createWorkflowEntranceForm, workflowEntranceEdit).setIsDefault(isDefault);
                });

        When("^the user sets the workflow entrance's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntranceForm = persona.createWorkflowEntranceForm;
                    var workflowEntranceEdit = persona.workflowEntranceEdit;

                    assertThat(createWorkflowEntranceForm != null || workflowEntranceEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowEntranceForm, workflowEntranceEdit).setSortOrder(sortOrder);
                });

        When("^the user sets the workflow entrance's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntranceForm = persona.createWorkflowEntranceForm;
                    var workflowEntranceEdit = persona.workflowEntranceEdit;

                    assertThat(createWorkflowEntranceForm != null || workflowEntranceEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowEntranceForm, workflowEntranceEdit).setDescription(description);
                });
    }

}
