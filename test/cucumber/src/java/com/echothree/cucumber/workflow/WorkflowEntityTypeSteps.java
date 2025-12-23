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
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowEntityTypeSteps implements En {

    public WorkflowEntityTypeSteps() {
        When("^the user begins entering a new workflow entity type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona).isNotNull();
                    assertThat(persona.createWorkflowEntityTypeForm).isNull();
                    assertThat(persona.deleteWorkflowEntityTypeForm).isNull();

                    persona.createWorkflowEntityTypeForm = WorkflowUtil.getHome().getCreateWorkflowEntityTypeForm();
                });

        When("^the user adds the new workflow entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntityTypeForm = persona.createWorkflowEntityTypeForm;

                    assertThat(createWorkflowEntityTypeForm).isNotNull();

                    LastCommandResult.commandResult = WorkflowUtil.getHome().createWorkflowEntityType(persona.userVisitPK, createWorkflowEntityTypeForm);

                    persona.createWorkflowEntityTypeForm = null;
                });

        When("^the user begins deleting a workflow entity type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona).isNotNull();
                    assertThat(persona.createWorkflowEntityTypeForm).isNull();
                    assertThat(persona.deleteWorkflowEntityTypeForm).isNull();

                    persona.deleteWorkflowEntityTypeForm = WorkflowUtil.getHome().getDeleteWorkflowEntityTypeForm();
                });

        When("^the user deletes the workflow entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteWorkflowEntityTypeForm = persona.deleteWorkflowEntityTypeForm;

                    assertThat(deleteWorkflowEntityTypeForm).isNotNull();

                    LastCommandResult.commandResult = WorkflowUtil.getHome().deleteWorkflowEntityType(persona.userVisitPK, deleteWorkflowEntityTypeForm);

                    persona.deleteWorkflowEntityTypeForm = null;
                });

        When("^the user sets the workflow entity type's workflow name to \"([^\"]*)\"$",
                (String workflowName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntityTypeForm = persona.createWorkflowEntityTypeForm;
                    var deleteWorkflowEntityTypeForm = persona.deleteWorkflowEntityTypeForm;

                    assertThat(createWorkflowEntityTypeForm != null || deleteWorkflowEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowEntityTypeForm, deleteWorkflowEntityTypeForm).setWorkflowName(workflowName);
                });

        When("^the user sets the workflow entity type's workflow name to the last workflow added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWorkflowName = persona.lastWorkflowName;
                    var createWorkflowEntityTypeForm = persona.createWorkflowEntityTypeForm;
                    var deleteWorkflowEntityTypeForm = persona.deleteWorkflowEntityTypeForm;

                    assertThat(createWorkflowEntityTypeForm != null || deleteWorkflowEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowEntityTypeForm, deleteWorkflowEntityTypeForm).setWorkflowName(lastWorkflowName);
                });

        When("^the user sets the workflow entity type's component vendor to \"([^\"]*)\"$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntityTypeForm = persona.createWorkflowEntityTypeForm;
                    var deleteWorkflowEntityTypeForm = persona.deleteWorkflowEntityTypeForm;

                    assertThat(createWorkflowEntityTypeForm != null || deleteWorkflowEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowEntityTypeForm, deleteWorkflowEntityTypeForm).setComponentVendorName(componentVendorName);
                });

        When("^the user sets the workflow entity type's entity type to \"([^\"]*)\"$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createWorkflowEntityTypeForm = persona.createWorkflowEntityTypeForm;
                    var deleteWorkflowEntityTypeForm = persona.deleteWorkflowEntityTypeForm;

                    assertThat(createWorkflowEntityTypeForm != null || deleteWorkflowEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createWorkflowEntityTypeForm, deleteWorkflowEntityTypeForm).setEntityTypeName(entityTypeName);
                });
    }

}
