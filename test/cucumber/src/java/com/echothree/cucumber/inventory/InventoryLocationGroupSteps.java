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
import com.echothree.control.user.inventory.common.result.CreateInventoryLocationGroupResult;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryLocationGroupSteps implements En {

    public InventoryLocationGroupSteps() {
        When("^the user begins entering a new inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryLocationGroupForm).isNull();
                    assertThat(persona.setInventoryLocationGroupStatusForm).isNull();
                    assertThat(persona.deleteInventoryLocationGroupForm).isNull();
                    assertThat(persona.inventoryLocationGroupSpec).isNull();

                    persona.createInventoryLocationGroupForm = InventoryUtil.getHome().getCreateInventoryLocationGroupForm();
                });

        And("^the user adds the new inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;

                    assertThat(createInventoryLocationGroupForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryLocationGroup(persona.userVisitPK, createInventoryLocationGroupForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateInventoryLocationGroupResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryLocationGroupName = commandResult.getHasErrors() ? null : result.getInventoryLocationGroupName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryLocationGroupForm = null;
                });

        When("^the user begins setting the status of an inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryLocationGroupForm).isNull();
                    assertThat(persona.setInventoryLocationGroupStatusForm).isNull();
                    assertThat(persona.deleteInventoryLocationGroupForm).isNull();
                    assertThat(persona.inventoryLocationGroupSpec).isNull();

                    persona.setInventoryLocationGroupStatusForm = InventoryUtil.getHome().getSetInventoryLocationGroupStatusForm();
                });

        And("^the user sets the status of the inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;
                    var setInventoryLocationGroupStatusForm = persona.setInventoryLocationGroupStatusForm;

                    assertThat(setInventoryLocationGroupStatusForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().setInventoryLocationGroupStatus(persona.userVisitPK, setInventoryLocationGroupStatusForm);

                    persona.setInventoryLocationGroupStatusForm = null;
                });

        When("^the user begins deleting an inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryLocationGroupForm).isNull();
                    assertThat(persona.setInventoryLocationGroupStatusForm).isNull();
                    assertThat(persona.deleteInventoryLocationGroupForm).isNull();
                    assertThat(persona.inventoryLocationGroupSpec).isNull();

                    persona.deleteInventoryLocationGroupForm = InventoryUtil.getHome().getDeleteInventoryLocationGroupForm();
                });

        And("^the user deletes the inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryLocationGroupForm = persona.deleteInventoryLocationGroupForm;

                    assertThat(deleteInventoryLocationGroupForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryLocationGroup(persona.userVisitPK, deleteInventoryLocationGroupForm);

                    persona.deleteInventoryLocationGroupForm = null;
                });

        When("^the user begins specifying an inventory location group to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryLocationGroupForm).isNull();
                    assertThat(persona.setInventoryLocationGroupStatusForm).isNull();
                    assertThat(persona.deleteInventoryLocationGroupForm).isNull();
                    assertThat(persona.inventoryLocationGroupSpec).isNull();

                    persona.inventoryLocationGroupSpec = InventoryUtil.getHome().getInventoryLocationGroupSpec();
                });

        When("^the user begins editing the inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryLocationGroupSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryLocationGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryLocationGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditInventoryLocationGroupResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryLocationGroupEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory location group",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryLocationGroupSpec;
                    var edit = persona.inventoryLocationGroupEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryLocationGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryLocationGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryLocationGroupSpec = null;
                    persona.inventoryLocationGroupEdit = null;
                });

        And("^the user sets the inventory location group's warehouse name to \"([a-zA-Z0-9-_]*)\"$",
                (String warehouseName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;
                    var setInventoryLocationGroupStatusForm = persona.setInventoryLocationGroupStatusForm;
                    var deleteInventoryLocationGroupForm = persona.deleteInventoryLocationGroupForm;
                    var inventoryLocationGroupSpec = persona.inventoryLocationGroupSpec;

                    assertThat(createInventoryLocationGroupForm != null || setInventoryLocationGroupStatusForm != null
                            || deleteInventoryLocationGroupForm != null || inventoryLocationGroupSpec != null).isTrue();

                    if(createInventoryLocationGroupForm != null) {
                        createInventoryLocationGroupForm.setWarehouseName(warehouseName);
                    } else if(setInventoryLocationGroupStatusForm != null) {
                        setInventoryLocationGroupStatusForm.setWarehouseName(warehouseName);
                    } else if(deleteInventoryLocationGroupForm != null) {
                        deleteInventoryLocationGroupForm.setWarehouseName(warehouseName);
                    } else {
                        inventoryLocationGroupSpec.setWarehouseName(warehouseName);
                    }
                });

        And("^the user sets the inventory location group's warehouse name to the last warehouse added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;
                    var setInventoryLocationGroupStatusForm = persona.setInventoryLocationGroupStatusForm;
                    var deleteInventoryLocationGroupForm = persona.deleteInventoryLocationGroupForm;
                    var inventoryLocationGroupSpec = persona.inventoryLocationGroupSpec;

                    assertThat(createInventoryLocationGroupForm != null || setInventoryLocationGroupStatusForm != null
                            || deleteInventoryLocationGroupForm != null || inventoryLocationGroupSpec != null).isTrue();

                    if(createInventoryLocationGroupForm != null) {
                        createInventoryLocationGroupForm.setWarehouseName(persona.lastWarehouseName);
                    } else if(setInventoryLocationGroupStatusForm != null) {
                        setInventoryLocationGroupStatusForm.setWarehouseName(persona.lastWarehouseName);
                    } else if(deleteInventoryLocationGroupForm != null) {
                        deleteInventoryLocationGroupForm.setWarehouseName(persona.lastWarehouseName);
                    } else {
                        inventoryLocationGroupSpec.setWarehouseName(persona.lastWarehouseName);
                    }
                });

        And("^the user sets the inventory location group's inventory location group name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryLocationGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;
                    var setInventoryLocationGroupStatusForm = persona.setInventoryLocationGroupStatusForm;
                    var deleteInventoryLocationGroupForm = persona.deleteInventoryLocationGroupForm;
                    var inventoryLocationGroupSpec = persona.inventoryLocationGroupSpec;

                    assertThat(createInventoryLocationGroupForm != null || setInventoryLocationGroupStatusForm != null
                            || deleteInventoryLocationGroupForm != null || inventoryLocationGroupSpec != null).isTrue();

                    if(createInventoryLocationGroupForm != null) {
                        createInventoryLocationGroupForm.setInventoryLocationGroupName(inventoryLocationGroupName);
                    } else if(setInventoryLocationGroupStatusForm != null) {
                        setInventoryLocationGroupStatusForm.setInventoryLocationGroupName(inventoryLocationGroupName);
                    } else if(deleteInventoryLocationGroupForm != null) {
                        deleteInventoryLocationGroupForm.setInventoryLocationGroupName(inventoryLocationGroupName);
                    } else {
                        inventoryLocationGroupSpec.setInventoryLocationGroupName(inventoryLocationGroupName);
                    }
                });

        And("^the user sets the inventory location group's inventory location group name to the last inventory location group added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;
                    var setInventoryLocationGroupStatusForm = persona.setInventoryLocationGroupStatusForm;
                    var deleteInventoryLocationGroupForm = persona.deleteInventoryLocationGroupForm;
                    var inventoryLocationGroupSpec = persona.inventoryLocationGroupSpec;

                    assertThat(createInventoryLocationGroupForm != null || setInventoryLocationGroupStatusForm != null
                            || deleteInventoryLocationGroupForm != null || inventoryLocationGroupSpec != null).isTrue();

                    if(createInventoryLocationGroupForm != null) {
                        createInventoryLocationGroupForm.setInventoryLocationGroupName(persona.lastInventoryLocationGroupName);
                    } else if(setInventoryLocationGroupStatusForm != null) {
                        setInventoryLocationGroupStatusForm.setInventoryLocationGroupName(persona.lastInventoryLocationGroupName);
                    } else if(deleteInventoryLocationGroupForm != null) {
                        deleteInventoryLocationGroupForm.setInventoryLocationGroupName(persona.lastInventoryLocationGroupName);
                    } else {
                        inventoryLocationGroupSpec.setInventoryLocationGroupName(persona.lastInventoryLocationGroupName);
                    }
                });

        And("^the user sets the inventory location group's new inventory location group name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryLocationGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryLocationGroupEdit = persona.inventoryLocationGroupEdit;

                    assertThat(inventoryLocationGroupEdit).isNotNull();

                    inventoryLocationGroupEdit.setInventoryLocationGroupName(inventoryLocationGroupName);
                });

        And("^the user sets the inventory location group's status to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryLocationGroupStatusChoice) -> {
                    var persona = CurrentPersona.persona;
                    var setInventoryLocationGroupStatusForm = persona.setInventoryLocationGroupStatusForm;

                    assertThat(setInventoryLocationGroupStatusForm).isNotNull();

                    setInventoryLocationGroupStatusForm.setInventoryLocationGroupStatusChoice(inventoryLocationGroupStatusChoice);
                });

        And("^the user sets the inventory location group to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;
                    var inventoryLocationGroupEdit = persona.inventoryLocationGroupEdit;

                    assertThat(createInventoryLocationGroupForm != null || inventoryLocationGroupEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryLocationGroupForm != null) {
                        createInventoryLocationGroupForm.setIsDefault(isDefault);
                    } else {
                        inventoryLocationGroupEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory location group's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;
                    var inventoryLocationGroupEdit = persona.inventoryLocationGroupEdit;

                    assertThat(createInventoryLocationGroupForm != null || inventoryLocationGroupEdit != null).isTrue();

                    if(createInventoryLocationGroupForm != null) {
                        createInventoryLocationGroupForm.setSortOrder(sortOrder);
                    } else {
                        inventoryLocationGroupEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory location group's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryLocationGroupForm = persona.createInventoryLocationGroupForm;
                    var inventoryLocationGroupEdit = persona.inventoryLocationGroupEdit;

                    assertThat(createInventoryLocationGroupForm != null || inventoryLocationGroupEdit != null).isTrue();

                    if(createInventoryLocationGroupForm != null) {
                        createInventoryLocationGroupForm.setDescription(description);
                    } else {
                        inventoryLocationGroupEdit.setDescription(description);
                    }
                });

    }

}
