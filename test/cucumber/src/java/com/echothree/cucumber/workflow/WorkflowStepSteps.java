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
import com.echothree.control.user.workflow.common.result.CreateWorkflowStepResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowStepSteps implements En {

    public WorkflowStepSteps() {
        When("^the user begins entering a new workflow step$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowStepForm).isNull();
                    assertThat(persona.deleteWorkflowStepForm).isNull();
                    assertThat(persona.workflowStepUniversalSpec).isNull();

                    persona.createWorkflowStepForm = WorkflowUtil.getHome().getCreateWorkflowStepForm();
                });

        When("^the user adds the new workflow step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;

                    assertThat(createWorkflowStepForm).isNotNull();

                    var commandResult = WorkflowUtil.getHome().createWorkflowStep(persona.userVisitPK, createWorkflowStepForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateWorkflowStepResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastWorkflowStepName = commandResult.getHasErrors() ? null : result.getWorkflowStepName();
                    persona.createWorkflowStepForm = null;
                });

        When("^the user begins deleting a workflow step$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowStepForm).isNull();
                    assertThat(persona.deleteWorkflowStepForm).isNull();
                    assertThat(persona.workflowStepUniversalSpec).isNull();

                    persona.deleteWorkflowStepForm = WorkflowUtil.getHome().getDeleteWorkflowStepForm();
                });

        When("^the user deletes the workflow step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteWorkflowStepForm = persona.deleteWorkflowStepForm;

                    assertThat(deleteWorkflowStepForm).isNotNull();

                    LastCommandResult.commandResult = WorkflowUtil.getHome().deleteWorkflowStep(persona.userVisitPK, deleteWorkflowStepForm);

                    persona.deleteWorkflowStepForm = null;
                });

        When("^the user begins specifying a workflow step to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowStepForm).isNull();
                    assertThat(persona.deleteWorkflowStepForm).isNull();
                    assertThat(persona.workflowStepUniversalSpec).isNull();

                    persona.workflowStepUniversalSpec = WorkflowUtil.getHome().getWorkflowStepUniversalSpec();
                });

        When("^the user begins editing the workflow step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowStepUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowStepForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WorkflowUtil.getHome().editWorkflowStep(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditWorkflowStepResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.workflowStepEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the workflow step$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowStepUniversalSpec;
                    var edit = persona.workflowStepEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowStepForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = WorkflowUtil.getHome().editWorkflowStep(persona.userVisitPK, commandForm);

                    persona.workflowStepUniversalSpec = null;
                    persona.workflowStepEdit = null;
                });

        When("^the user sets the workflow step's workflow name to \"([^\"]*)\"$",
                (String workflowName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;
                    var deleteWorkflowStepForm = persona.deleteWorkflowStepForm;
                    var workflowStepSpec = persona.workflowStepUniversalSpec;

                    assertThat(createWorkflowStepForm != null || deleteWorkflowStepForm != null || workflowStepSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowStepForm, () -> Objects.requireNonNullElse(deleteWorkflowStepForm, workflowStepSpec)).setWorkflowName(workflowName);
                });

        When("^the user sets the workflow step's workflow name to the last workflow added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowName = persona.lastWorkflowName;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;
                    var deleteWorkflowStepForm = persona.deleteWorkflowStepForm;
                    var workflowStepSpec = persona.workflowStepUniversalSpec;

                    assertThat(createWorkflowStepForm != null || deleteWorkflowStepForm != null || workflowStepSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowStepForm, () -> Objects.requireNonNullElse(deleteWorkflowStepForm, workflowStepSpec)).setWorkflowName(lastWorkflowName);
                });

        When("^the user sets the workflow step's name to \"([^\"]*)\"$",
                (String workflowStepName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;
                    var deleteWorkflowStepForm = persona.deleteWorkflowStepForm;
                    var workflowStepSpec = persona.workflowStepUniversalSpec;

                    assertThat(createWorkflowStepForm != null || deleteWorkflowStepForm != null || workflowStepSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowStepForm, () -> Objects.requireNonNullElse(deleteWorkflowStepForm, workflowStepSpec)).setWorkflowStepName(workflowStepName);
                });

        When("^the user sets the workflow step's name to the last workflow step added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowStepName = persona.lastWorkflowStepName;
                    var deleteWorkflowStepForm = persona.deleteWorkflowStepForm;
                    var workflowStepSpec = persona.workflowStepUniversalSpec;

                    assertThat(deleteWorkflowStepForm != null || workflowStepSpec != null).isTrue();

                    Objects.requireNonNullElse(deleteWorkflowStepForm, workflowStepSpec).setWorkflowStepName(lastWorkflowStepName);
                });

        When("^the user sets the workflow step's type to \"([^\"]*)\"$",
                (String workflowStepType) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;
                    var workflowStepEdit = persona.workflowStepEdit;

                    assertThat(createWorkflowStepForm != null || workflowStepEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowStepForm, workflowStepEdit).setWorkflowStepTypeName(workflowStepType);
                });

        When("^the user sets the workflow step to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;
                    var workflowStepEdit = persona.workflowStepEdit;

                    assertThat(createWorkflowStepForm != null || workflowStepEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    Objects.requireNonNullElse(createWorkflowStepForm, workflowStepEdit).setIsDefault(isDefault);
                });

        When("^the user sets the workflow step's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;
                    var workflowStepEdit = persona.workflowStepEdit;

                    assertThat(createWorkflowStepForm != null || workflowStepEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowStepForm, workflowStepEdit).setSortOrder(sortOrder);
                });

        When("^the user sets the workflow step's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowStepForm = persona.createWorkflowStepForm;
                    var workflowStepEdit = persona.workflowStepEdit;

                    assertThat(createWorkflowStepForm != null || workflowStepEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowStepForm, workflowStepEdit).setDescription(description);
                });
    }

}
