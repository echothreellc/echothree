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
import com.echothree.control.user.workflow.common.result.CreateWorkflowDestinationResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowDestinationSteps implements En {

    public WorkflowDestinationSteps() {
        When("^the user begins entering a new workflow destination",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowDestinationForm).isNull();
                    assertThat(persona.deleteWorkflowDestinationForm).isNull();
                    assertThat(persona.workflowDestinationUniversalSpec).isNull();

                    persona.createWorkflowDestinationForm = WorkflowUtil.getHome().getCreateWorkflowDestinationForm();
                });

        When("^the user adds the new workflow destination",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;

                    assertThat(createWorkflowDestinationForm).isNotNull();

                    var commandResult = WorkflowUtil.getHome().createWorkflowDestination(persona.userVisitPK, createWorkflowDestinationForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateWorkflowDestinationResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastWorkflowDestinationName = commandResult.getHasErrors() ? null : result.getWorkflowDestinationName();
                    persona.createWorkflowDestinationForm = null;
                });

        When("^the user begins deleting a workflow destination",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowDestinationForm).isNull();
                    assertThat(persona.deleteWorkflowDestinationForm).isNull();
                    assertThat(persona.workflowDestinationUniversalSpec).isNull();

                    persona.deleteWorkflowDestinationForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationForm();
                });

        When("^the user deletes the workflow destination",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteWorkflowDestinationForm = persona.deleteWorkflowDestinationForm;

                    assertThat(deleteWorkflowDestinationForm).isNotNull();

                    LastCommandResult.commandResult = WorkflowUtil.getHome().deleteWorkflowDestination(persona.userVisitPK, deleteWorkflowDestinationForm);

                    persona.deleteWorkflowDestinationForm = null;
                });

        When("^the user begins specifying a workflow destination to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowDestinationForm).isNull();
                    assertThat(persona.deleteWorkflowDestinationForm).isNull();
                    assertThat(persona.workflowDestinationUniversalSpec).isNull();

                    persona.workflowDestinationUniversalSpec = WorkflowUtil.getHome().getWorkflowDestinationUniversalSpec();
                });

        When("^the user begins editing the workflow destination",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowDestinationUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowDestinationForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WorkflowUtil.getHome().editWorkflowDestination(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditWorkflowDestinationResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.workflowDestinationEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the workflow destination",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowDestinationUniversalSpec;
                    var edit = persona.workflowDestinationEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowDestinationForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = WorkflowUtil.getHome().editWorkflowDestination(persona.userVisitPK, commandForm);

                    persona.workflowDestinationUniversalSpec = null;
                    persona.workflowDestinationEdit = null;
                });

        When("^the user sets the workflow destination's workflow name to \"([^\"]*)\"$",
                (String workflowName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var deleteWorkflowDestinationForm = persona.deleteWorkflowDestinationForm;
                    var workflowDestinationSpec = persona.workflowDestinationUniversalSpec;

                    assertThat(createWorkflowDestinationForm != null || deleteWorkflowDestinationForm != null || workflowDestinationSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowDestinationForm, () -> Objects.requireNonNullElse(deleteWorkflowDestinationForm, workflowDestinationSpec)).setWorkflowName(workflowName);
                });

        When("^the user sets the workflow destination's workflow name to the last workflow added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowName = persona.lastWorkflowName;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var deleteWorkflowDestinationForm = persona.deleteWorkflowDestinationForm;
                    var workflowDestinationSpec = persona.workflowDestinationUniversalSpec;

                    assertThat(createWorkflowDestinationForm != null || deleteWorkflowDestinationForm != null || workflowDestinationSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowDestinationForm, () -> Objects.requireNonNullElse(deleteWorkflowDestinationForm, workflowDestinationSpec)).setWorkflowName(lastWorkflowName);
                });

        When("^the user sets the workflow destination's workflow step name to \"([^\"]*)\"$",
                (String workflowStepName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var deleteWorkflowDestinationForm = persona.deleteWorkflowDestinationForm;
                    var workflowDestinationSpec = persona.workflowDestinationUniversalSpec;

                    assertThat(createWorkflowDestinationForm != null || deleteWorkflowDestinationForm != null || workflowDestinationSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowDestinationForm, () -> Objects.requireNonNullElse(deleteWorkflowDestinationForm, workflowDestinationSpec)).setWorkflowStepName(workflowStepName);
                });

        When("^the user sets the workflow destination's workflow step name to the last workflow step added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowStepName = persona.lastWorkflowStepName;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var deleteWorkflowDestinationForm = persona.deleteWorkflowDestinationForm;
                    var workflowDestinationSpec = persona.workflowDestinationUniversalSpec;

                    assertThat(createWorkflowDestinationForm != null || deleteWorkflowDestinationForm != null || workflowDestinationSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowDestinationForm, () -> Objects.requireNonNullElse(deleteWorkflowDestinationForm, workflowDestinationSpec)).setWorkflowStepName(lastWorkflowStepName);
                });

        When("^the user sets the workflow destination's name to \"([^\"]*)\"$",
                (String workflowDestinationName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var deleteWorkflowDestinationForm = persona.deleteWorkflowDestinationForm;
                    var workflowDestinationSpec = persona.workflowDestinationUniversalSpec;

                    assertThat(createWorkflowDestinationForm != null || deleteWorkflowDestinationForm != null || workflowDestinationSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowDestinationForm, () -> Objects.requireNonNullElse(deleteWorkflowDestinationForm, workflowDestinationSpec)).setWorkflowDestinationName(workflowDestinationName);
                });

        When("^the user sets the workflow destination's name to the last workflow destination added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowDestinationName = persona.lastWorkflowDestinationName;
                    var deleteWorkflowDestinationForm = persona.deleteWorkflowDestinationForm;
                    var workflowDestinationSpec = persona.workflowDestinationUniversalSpec;

                    assertThat(deleteWorkflowDestinationForm != null || workflowDestinationSpec != null).isTrue();

                    Objects.requireNonNullElse(deleteWorkflowDestinationForm, workflowDestinationSpec).setWorkflowDestinationName(lastWorkflowDestinationName);
                });

        When("^the user sets the workflow destination to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var workflowDestinationEdit = persona.workflowDestinationEdit;

                    assertThat(createWorkflowDestinationForm != null || workflowDestinationEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    Objects.requireNonNullElse(createWorkflowDestinationForm, workflowDestinationEdit).setIsDefault(isDefault);
                });

        When("^the user sets the workflow destination's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var workflowDestinationEdit = persona.workflowDestinationEdit;

                    assertThat(createWorkflowDestinationForm != null || workflowDestinationEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowDestinationForm, workflowDestinationEdit).setSortOrder(sortOrder);
                });

        When("^the user sets the workflow destination's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowDestinationForm = persona.createWorkflowDestinationForm;
                    var workflowDestinationEdit = persona.workflowDestinationEdit;

                    assertThat(createWorkflowDestinationForm != null || workflowDestinationEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowDestinationForm, workflowDestinationEdit).setDescription(description);
                });
    }

}
