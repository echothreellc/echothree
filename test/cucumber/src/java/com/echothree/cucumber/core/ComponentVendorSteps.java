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

package com.echothree.cucumber.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.CreateComponentVendorResult;
import com.echothree.control.user.core.common.result.EditComponentVendorResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ComponentVendorSteps implements En {

    public ComponentVendorSteps() {
        When("^the user begins entering a new component vendor$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createComponentVendorForm).isNull();
                    assertThat(persona.deleteComponentVendorForm).isNull();
                    assertThat(persona.componentVendorSpec).isNull();

                    persona.createComponentVendorForm = CoreUtil.getHome().getCreateComponentVendorForm();
                });

        When("^the user adds the new component vendor$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createComponentVendorForm = persona.createComponentVendorForm;

                    assertThat(createComponentVendorForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createComponentVendor(persona.userVisitPK, createComponentVendorForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateComponentVendorResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastComponentVendorName = commandResult.getHasErrors() ? null : result.getComponentVendorName();
                    persona.createComponentVendorForm = null;
                });

        When("^the user begins deleting a component vendor$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createComponentVendorForm).isNull();
                    assertThat(persona.deleteComponentVendorForm).isNull();
                    assertThat(persona.componentVendorSpec).isNull();

                    persona.deleteComponentVendorForm = CoreUtil.getHome().getDeleteComponentVendorForm();
                });

        When("^the user deletes the component vendor$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteComponentVendorForm = persona.deleteComponentVendorForm;

                    assertThat(deleteComponentVendorForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteComponentVendor(persona.userVisitPK, deleteComponentVendorForm);

                    persona.deleteComponentVendorForm = null;
                });

        When("^the user begins specifying a component vendor to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createComponentVendorForm).isNull();
                    assertThat(persona.deleteComponentVendorForm).isNull();
                    assertThat(persona.componentVendorSpec).isNull();

                    persona.componentVendorSpec = CoreUtil.getHome().getComponentVendorSpec();
                });

        When("^the user begins editing the component vendor$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.componentVendorSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditComponentVendorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editComponentVendor(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditComponentVendorResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.componentVendorEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the component vendor$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.componentVendorSpec;
                    var edit = persona.componentVendorEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditComponentVendorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editComponentVendor(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.componentVendorSpec = null;
                    persona.componentVendorEdit = null;
                });
        
        When("^the user sets the component vendor's name to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createComponentVendorForm = persona.createComponentVendorForm;
                    var deleteComponentVendorForm = persona.deleteComponentVendorForm;
                    var componentVendorSpec = persona.componentVendorSpec;

                    assertThat(createComponentVendorForm != null || deleteComponentVendorForm != null || componentVendorSpec != null).isTrue();

                    if(createComponentVendorForm != null) {
                        createComponentVendorForm.setComponentVendorName(componentVendorName);
                    } else if(deleteComponentVendorForm != null) {
                        deleteComponentVendorForm.setComponentVendorName(componentVendorName);
                    } else {
                        componentVendorSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the component vendor's name to the last component vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastComponentVendorName = persona.lastComponentVendorName;
                    var deleteComponentVendorForm = persona.deleteComponentVendorForm;
                    var componentVendorSpec = persona.componentVendorSpec;

                    assertThat(deleteComponentVendorForm != null || componentVendorSpec != null).isTrue();

                    if(deleteComponentVendorForm != null) {
                        deleteComponentVendorForm.setComponentVendorName(lastComponentVendorName);
                    } else {
                        componentVendorSpec.setComponentVendorName(lastComponentVendorName);
                    }
                });

        When("^the user sets the component vendor's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createComponentVendorForm = persona.createComponentVendorForm;
                    var componentVendorEdit = persona.componentVendorEdit;

                    assertThat(createComponentVendorForm != null || componentVendorEdit != null).isTrue();

                    if(createComponentVendorForm != null) {
                        createComponentVendorForm.setDescription(description);
                    } else {
                        componentVendorEdit.setDescription(description);
                    }
                });
    }

}
