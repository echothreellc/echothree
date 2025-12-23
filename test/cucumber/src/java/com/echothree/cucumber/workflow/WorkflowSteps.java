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
import com.echothree.control.user.workflow.common.result.CreateWorkflowResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowSteps implements En {

    public WorkflowSteps() {
        When("^the user begins entering a new workflow$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowForm).isNull();
                    assertThat(persona.deleteWorkflowForm).isNull();
                    assertThat(persona.workflowUniversalSpec).isNull();

                    persona.createWorkflowForm = WorkflowUtil.getHome().getCreateWorkflowForm();
                });

        When("^the user adds the new workflow$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowForm = persona.createWorkflowForm;

                    assertThat(createWorkflowForm).isNotNull();

                    var commandResult = WorkflowUtil.getHome().createWorkflow(persona.userVisitPK, createWorkflowForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateWorkflowResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastWorkflowName = commandResult.getHasErrors() ? null : result.getWorkflowName();
                    persona.createWorkflowForm = null;
                });

        When("^the user begins deleting a workflow$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowForm).isNull();
                    assertThat(persona.deleteWorkflowForm).isNull();
                    assertThat(persona.workflowUniversalSpec).isNull();

                    persona.deleteWorkflowForm = WorkflowUtil.getHome().getDeleteWorkflowForm();
                });

        When("^the user deletes the workflow$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteWorkflowForm = persona.deleteWorkflowForm;

                    assertThat(deleteWorkflowForm).isNotNull();

                    LastCommandResult.commandResult = WorkflowUtil.getHome().deleteWorkflow(persona.userVisitPK, deleteWorkflowForm);

                    persona.deleteWorkflowForm = null;
                });

        When("^the user begins specifying a workflow to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWorkflowForm).isNull();
                    assertThat(persona.deleteWorkflowForm).isNull();
                    assertThat(persona.workflowUniversalSpec).isNull();

                    persona.workflowUniversalSpec = WorkflowUtil.getHome().getWorkflowUniversalSpec();
                });

        When("^the user begins editing the workflow$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WorkflowUtil.getHome().editWorkflow(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditWorkflowResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.workflowEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the workflow$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.workflowUniversalSpec;
                    var edit = persona.workflowEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = WorkflowUtil.getHome().getEditWorkflowForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = WorkflowUtil.getHome().editWorkflow(persona.userVisitPK, commandForm);

                    persona.workflowUniversalSpec = null;
                    persona.workflowEdit = null;
                });
        
        When("^the user sets the workflow's name to \"([^\"]*)\"$",
                (String workflowName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowForm = persona.createWorkflowForm;
                    var deleteWorkflowForm = persona.deleteWorkflowForm;
                    var workflowUniversalSpec = persona.workflowUniversalSpec;

                    assertThat(createWorkflowForm != null || deleteWorkflowForm != null || workflowUniversalSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createWorkflowForm, () -> Objects.requireNonNullElse(deleteWorkflowForm, workflowUniversalSpec)).setWorkflowName(workflowName);
                });

        When("^the user sets the workflow's name to the last workflow added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowName = persona.lastWorkflowName;
                    var deleteWorkflowForm = persona.deleteWorkflowForm;
                    var workflowUniversalSpec = persona.workflowUniversalSpec;

                    assertThat(deleteWorkflowForm != null || workflowUniversalSpec != null).isTrue();

                    Objects.requireNonNullElse(deleteWorkflowForm, workflowUniversalSpec).setWorkflowName(lastWorkflowName);
                });

        When("^the user sets the workflow's selector kind to \"([^\"]*)\"$",
                (String selectorKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowForm = persona.createWorkflowForm;
                    var workflowEdit = persona.workflowEdit;

                    assertThat(createWorkflowForm != null || workflowEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowForm, workflowEdit).setSelectorKindName(selectorKindName);
                });

        When("^the user sets the workflow's selector type to \"([^\"]*)\"$",
                (String selectorTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowForm = persona.createWorkflowForm;
                    var workflowEdit = persona.workflowEdit;

                    assertThat(createWorkflowForm != null || workflowEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowForm, workflowEdit).setSelectorTypeName(selectorTypeName);
                });

        When("^the user sets the workflow's security role group to \"([^\"]*)\"$",
                (String securityRoleGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowForm = persona.createWorkflowForm;
                    var workflowEdit = persona.workflowEdit;

                    assertThat(createWorkflowForm != null || workflowEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowForm, workflowEdit).setSecurityRoleGroupName(securityRoleGroupName);
                });

        When("^the user sets the workflow's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowForm = persona.createWorkflowForm;
                    var workflowEdit = persona.workflowEdit;

                    assertThat(createWorkflowForm != null || workflowEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowForm, workflowEdit).setSortOrder(sortOrder);
                });

        When("^the user sets the workflow's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowForm = persona.createWorkflowForm;
                    var workflowEdit = persona.workflowEdit;

                    assertThat(createWorkflowForm != null || workflowEdit != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowForm, workflowEdit).setDescription(description);
                });
    }

}
