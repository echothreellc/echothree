// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.cucumber.inventory;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.CreateInventoryConditionResult;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryConditionSteps implements En {

    public InventoryConditionSteps() {
        When("^the user begins entering a new inventory condition",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryConditionForm).isNull();
                    assertThat(persona.deleteInventoryConditionForm).isNull();
                    assertThat(persona.inventoryConditionSpec).isNull();

                    persona.createInventoryConditionForm = InventoryUtil.getHome().getCreateInventoryConditionForm();
                });

        And("^the user adds the new inventory condition",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryConditionForm = persona.createInventoryConditionForm;

                    assertThat(createInventoryConditionForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryCondition(persona.userVisitPK, createInventoryConditionForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateInventoryConditionResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryConditionName = commandResult.getHasErrors() ? null : result.getInventoryConditionName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryConditionForm = null;
                });

        When("^the user begins deleting an inventory condition",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryConditionForm).isNull();
                    assertThat(persona.deleteInventoryConditionForm).isNull();
                    assertThat(persona.inventoryConditionSpec).isNull();

                    persona.deleteInventoryConditionForm = InventoryUtil.getHome().getDeleteInventoryConditionForm();
                });

        And("^the user deletes the inventory condition",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryConditionForm = persona.deleteInventoryConditionForm;

                    assertThat(deleteInventoryConditionForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryCondition(persona.userVisitPK, deleteInventoryConditionForm);

                    persona.deleteInventoryConditionForm = null;
                });

        When("^the user begins specifying an inventory condition to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryConditionForm).isNull();
                    assertThat(persona.deleteInventoryConditionForm).isNull();
                    assertThat(persona.inventoryConditionSpec).isNull();

                    persona.inventoryConditionSpec = InventoryUtil.getHome().getInventoryConditionUniversalSpec();
                });

        When("^the user begins editing the inventory condition",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryConditionSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryConditionForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryCondition(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditInventoryConditionResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryConditionEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory condition",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryConditionSpec;
                    var edit = persona.inventoryConditionEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryConditionForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryCondition(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryConditionSpec = null;
                    persona.inventoryConditionEdit = null;
                });

        And("^the user sets the inventory condition's inventory condition name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryConditionName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryConditionForm = persona.createInventoryConditionForm;
                    var deleteInventoryConditionForm = persona.deleteInventoryConditionForm;
                    var inventoryConditionSpec = persona.inventoryConditionSpec;

                    assertThat(createInventoryConditionForm != null || deleteInventoryConditionForm != null
                            || inventoryConditionSpec != null).isTrue();

                    if(createInventoryConditionForm != null) {
                        createInventoryConditionForm.setInventoryConditionName(inventoryConditionName);
                    } else if(deleteInventoryConditionForm != null) {
                        deleteInventoryConditionForm.setInventoryConditionName(inventoryConditionName);
                    } else {
                        inventoryConditionSpec.setInventoryConditionName(inventoryConditionName);
                    }
                });

        And("^the user sets the inventory condition's inventory condition name to the last inventory condition added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryConditionForm = persona.createInventoryConditionForm;
                    var deleteInventoryConditionForm = persona.deleteInventoryConditionForm;
                    var inventoryConditionSpec = persona.inventoryConditionSpec;

                    assertThat(createInventoryConditionForm != null || deleteInventoryConditionForm != null
                            || inventoryConditionSpec != null).isTrue();

                    if(createInventoryConditionForm != null) {
                        createInventoryConditionForm.setInventoryConditionName(persona.lastInventoryConditionName);
                    } else if(deleteInventoryConditionForm != null) {
                        deleteInventoryConditionForm.setInventoryConditionName(persona.lastInventoryConditionName);
                    } else {
                        inventoryConditionSpec.setInventoryConditionName(persona.lastInventoryConditionName);
                    }
                });

        And("^the user sets the inventory condition's new inventory condition name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryConditionName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryConditionEdit = persona.inventoryConditionEdit;

                    assertThat(inventoryConditionEdit).isNotNull();

                    inventoryConditionEdit.setInventoryConditionName(inventoryConditionName);
                });

        And("^the user sets the inventory condition to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryConditionForm = persona.createInventoryConditionForm;
                    var inventoryConditionEdit = persona.inventoryConditionEdit;

                    assertThat(createInventoryConditionForm != null || inventoryConditionEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryConditionForm != null) {
                        createInventoryConditionForm.setIsDefault(isDefault);
                    } else {
                        inventoryConditionEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory condition's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryConditionForm = persona.createInventoryConditionForm;
                    var inventoryConditionEdit = persona.inventoryConditionEdit;

                    assertThat(createInventoryConditionForm != null || inventoryConditionEdit != null).isTrue();

                    if(createInventoryConditionForm != null) {
                        createInventoryConditionForm.setSortOrder(sortOrder);
                    } else {
                        inventoryConditionEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory condition's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryConditionForm = persona.createInventoryConditionForm;
                    var inventoryConditionEdit = persona.inventoryConditionEdit;

                    assertThat(createInventoryConditionForm != null || inventoryConditionEdit != null).isTrue();

                    if(createInventoryConditionForm != null) {
                        createInventoryConditionForm.setDescription(description);
                    } else {
                        inventoryConditionEdit.setDescription(description);
                    }
                });

    }

}
