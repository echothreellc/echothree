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

package com.echothree.cucumber.inventory;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.CreateInventoryCostingMethodResult;
import com.echothree.control.user.inventory.common.result.EditInventoryCostingMethodResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryCostingMethodSteps implements En {

    public InventoryCostingMethodSteps() {
        When("^the user begins entering a new inventory costing method$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryCostingMethodForm).isNull();
                    assertThat(persona.deleteInventoryCostingMethodForm).isNull();
                    assertThat(persona.inventoryCostingMethodSpec).isNull();

                    persona.createInventoryCostingMethodForm = InventoryUtil.getHome().getCreateInventoryCostingMethodForm();
                });

        And("^the user adds the new inventory costing method",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryCostingMethodForm = persona.createInventoryCostingMethodForm;

                    assertThat(createInventoryCostingMethodForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryCostingMethod(persona.userVisitPK, createInventoryCostingMethodForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryCostingMethodName = commandResult.getHasErrors() ? null : result.getInventoryCostingMethodName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryCostingMethodForm = null;
                });

        When("^the user begins deleting an inventory costing method$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryCostingMethodForm).isNull();
                    assertThat(persona.deleteInventoryCostingMethodForm).isNull();
                    assertThat(persona.inventoryCostingMethodSpec).isNull();

                    persona.deleteInventoryCostingMethodForm = InventoryUtil.getHome().getDeleteInventoryCostingMethodForm();
                });

        And("^the user deletes the inventory costing method",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryCostingMethodForm = persona.deleteInventoryCostingMethodForm;

                    assertThat(deleteInventoryCostingMethodForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryCostingMethod(persona.userVisitPK, deleteInventoryCostingMethodForm);

                    persona.deleteInventoryCostingMethodForm = null;
                });

        When("^the user begins specifying an inventory costing method to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryCostingMethodForm).isNull();
                    assertThat(persona.deleteInventoryCostingMethodForm).isNull();
                    assertThat(persona.inventoryCostingMethodSpec).isNull();

                    persona.inventoryCostingMethodSpec = InventoryUtil.getHome().getInventoryCostingMethodUniversalSpec();
                });

        When("^the user begins editing the inventory costing method$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryCostingMethodSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryCostingMethodForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryCostingMethod(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryCostingMethodEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory costing method",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryCostingMethodSpec;
                    var edit = persona.inventoryCostingMethodEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryCostingMethodForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryCostingMethod(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryCostingMethodSpec = null;
                    persona.inventoryCostingMethodEdit = null;
                });

        And("^the user sets the inventory costing method's inventory costing method name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryCostingMethodName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryCostingMethodForm = persona.createInventoryCostingMethodForm;
                    var deleteInventoryCostingMethodForm = persona.deleteInventoryCostingMethodForm;
                    var inventoryCostingMethodSpec = persona.inventoryCostingMethodSpec;

                    assertThat(createInventoryCostingMethodForm != null || deleteInventoryCostingMethodForm != null
                            || inventoryCostingMethodSpec != null).isTrue();

                    if(createInventoryCostingMethodForm != null) {
                        createInventoryCostingMethodForm.setInventoryCostingMethodName(inventoryCostingMethodName);
                    } else if(deleteInventoryCostingMethodForm != null) {
                        deleteInventoryCostingMethodForm.setInventoryCostingMethodName(inventoryCostingMethodName);
                    } else {
                        inventoryCostingMethodSpec.setInventoryCostingMethodName(inventoryCostingMethodName);
                    }
                });

        And("^the user sets the inventory costing method's inventory costing method name to the last inventory costing method added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryCostingMethodForm = persona.createInventoryCostingMethodForm;
                    var deleteInventoryCostingMethodForm = persona.deleteInventoryCostingMethodForm;
                    var inventoryCostingMethodSpec = persona.inventoryCostingMethodSpec;

                    assertThat(createInventoryCostingMethodForm != null || deleteInventoryCostingMethodForm != null
                            || inventoryCostingMethodSpec != null).isTrue();

                    if(createInventoryCostingMethodForm != null) {
                        createInventoryCostingMethodForm.setInventoryCostingMethodName(persona.lastInventoryCostingMethodName);
                    } else if(deleteInventoryCostingMethodForm != null) {
                        deleteInventoryCostingMethodForm.setInventoryCostingMethodName(persona.lastInventoryCostingMethodName);
                    } else {
                        inventoryCostingMethodSpec.setInventoryCostingMethodName(persona.lastInventoryCostingMethodName);
                    }
                });

        And("^the user sets the inventory costing method's new inventory costing method name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryCostingMethodName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryCostingMethodEdit = persona.inventoryCostingMethodEdit;

                    assertThat(inventoryCostingMethodEdit).isNotNull();

                    inventoryCostingMethodEdit.setInventoryCostingMethodName(inventoryCostingMethodName);
                });

        And("^the user sets the inventory costing method to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryCostingMethodForm = persona.createInventoryCostingMethodForm;
                    var inventoryCostingMethodEdit = persona.inventoryCostingMethodEdit;

                    assertThat(createInventoryCostingMethodForm != null || inventoryCostingMethodEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryCostingMethodForm != null) {
                        createInventoryCostingMethodForm.setIsDefault(isDefault);
                    } else {
                        inventoryCostingMethodEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory costing method's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryCostingMethodForm = persona.createInventoryCostingMethodForm;
                    var inventoryCostingMethodEdit = persona.inventoryCostingMethodEdit;

                    assertThat(createInventoryCostingMethodForm != null || inventoryCostingMethodEdit != null).isTrue();

                    if(createInventoryCostingMethodForm != null) {
                        createInventoryCostingMethodForm.setSortOrder(sortOrder);
                    } else {
                        inventoryCostingMethodEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory costing method's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryCostingMethodForm = persona.createInventoryCostingMethodForm;
                    var inventoryCostingMethodEdit = persona.inventoryCostingMethodEdit;

                    assertThat(createInventoryCostingMethodForm != null || inventoryCostingMethodEdit != null).isTrue();

                    if(createInventoryCostingMethodForm != null) {
                        createInventoryCostingMethodForm.setDescription(description);
                    } else {
                        inventoryCostingMethodEdit.setDescription(description);
                    }
                });

    }

}
