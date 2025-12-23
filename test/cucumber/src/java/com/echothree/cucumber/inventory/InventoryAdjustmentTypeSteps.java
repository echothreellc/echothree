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
import com.echothree.control.user.inventory.common.result.CreateInventoryAdjustmentTypeResult;
import com.echothree.control.user.inventory.common.result.EditInventoryAdjustmentTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryAdjustmentTypeSteps implements En {

    public InventoryAdjustmentTypeSteps() {
        When("^the user begins entering a new inventory adjustment type",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryAdjustmentTypeForm).isNull();
                    assertThat(persona.deleteInventoryAdjustmentTypeForm).isNull();
                    assertThat(persona.inventoryAdjustmentTypeSpec).isNull();

                    persona.createInventoryAdjustmentTypeForm = InventoryUtil.getHome().getCreateInventoryAdjustmentTypeForm();
                });

        And("^the user adds the new inventory adjustment type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryAdjustmentTypeForm = persona.createInventoryAdjustmentTypeForm;

                    assertThat(createInventoryAdjustmentTypeForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryAdjustmentType(persona.userVisitPK, createInventoryAdjustmentTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateInventoryAdjustmentTypeResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryAdjustmentTypeName = commandResult.getHasErrors() ? null : result.getInventoryAdjustmentTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryAdjustmentTypeForm = null;
                });

        When("^the user begins deleting an inventory adjustment type",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryAdjustmentTypeForm).isNull();
                    assertThat(persona.deleteInventoryAdjustmentTypeForm).isNull();
                    assertThat(persona.inventoryAdjustmentTypeSpec).isNull();

                    persona.deleteInventoryAdjustmentTypeForm = InventoryUtil.getHome().getDeleteInventoryAdjustmentTypeForm();
                });

        And("^the user deletes the inventory adjustment type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryAdjustmentTypeForm = persona.deleteInventoryAdjustmentTypeForm;

                    assertThat(deleteInventoryAdjustmentTypeForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryAdjustmentType(persona.userVisitPK, deleteInventoryAdjustmentTypeForm);

                    persona.deleteInventoryAdjustmentTypeForm = null;
                });

        When("^the user begins specifying an inventory adjustment type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryAdjustmentTypeForm).isNull();
                    assertThat(persona.deleteInventoryAdjustmentTypeForm).isNull();
                    assertThat(persona.inventoryAdjustmentTypeSpec).isNull();

                    persona.inventoryAdjustmentTypeSpec = InventoryUtil.getHome().getInventoryAdjustmentTypeUniversalSpec();
                });

        When("^the user begins editing the inventory adjustment type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryAdjustmentTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryAdjustmentTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryAdjustmentType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditInventoryAdjustmentTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryAdjustmentTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory adjustment type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryAdjustmentTypeSpec;
                    var edit = persona.inventoryAdjustmentTypeEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryAdjustmentTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryAdjustmentType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryAdjustmentTypeSpec = null;
                    persona.inventoryAdjustmentTypeEdit = null;
                });

        And("^the user sets the inventory adjustment type's inventory adjustment type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryAdjustmentTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryAdjustmentTypeForm = persona.createInventoryAdjustmentTypeForm;
                    var deleteInventoryAdjustmentTypeForm = persona.deleteInventoryAdjustmentTypeForm;
                    var inventoryAdjustmentTypeSpec = persona.inventoryAdjustmentTypeSpec;

                    assertThat(createInventoryAdjustmentTypeForm != null || deleteInventoryAdjustmentTypeForm != null
                            || inventoryAdjustmentTypeSpec != null).isTrue();

                    if(createInventoryAdjustmentTypeForm != null) {
                        createInventoryAdjustmentTypeForm.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
                    } else if(deleteInventoryAdjustmentTypeForm != null) {
                        deleteInventoryAdjustmentTypeForm.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
                    } else {
                        inventoryAdjustmentTypeSpec.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
                    }
                });

        And("^the user sets the inventory adjustment type's inventory adjustment type name to the last inventory adjustment type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryAdjustmentTypeForm = persona.createInventoryAdjustmentTypeForm;
                    var deleteInventoryAdjustmentTypeForm = persona.deleteInventoryAdjustmentTypeForm;
                    var inventoryAdjustmentTypeSpec = persona.inventoryAdjustmentTypeSpec;

                    assertThat(createInventoryAdjustmentTypeForm != null || deleteInventoryAdjustmentTypeForm != null
                            || inventoryAdjustmentTypeSpec != null).isTrue();

                    if(createInventoryAdjustmentTypeForm != null) {
                        createInventoryAdjustmentTypeForm.setInventoryAdjustmentTypeName(persona.lastInventoryAdjustmentTypeName);
                    } else if(deleteInventoryAdjustmentTypeForm != null) {
                        deleteInventoryAdjustmentTypeForm.setInventoryAdjustmentTypeName(persona.lastInventoryAdjustmentTypeName);
                    } else {
                        inventoryAdjustmentTypeSpec.setInventoryAdjustmentTypeName(persona.lastInventoryAdjustmentTypeName);
                    }
                });

        And("^the user sets the inventory adjustment type's new inventory adjustment type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryAdjustmentTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryAdjustmentTypeEdit = persona.inventoryAdjustmentTypeEdit;

                    assertThat(inventoryAdjustmentTypeEdit).isNotNull();

                    inventoryAdjustmentTypeEdit.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
                });

        And("^the user sets the inventory adjustment type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryAdjustmentTypeForm = persona.createInventoryAdjustmentTypeForm;
                    var inventoryAdjustmentTypeEdit = persona.inventoryAdjustmentTypeEdit;

                    assertThat(createInventoryAdjustmentTypeForm != null || inventoryAdjustmentTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryAdjustmentTypeForm != null) {
                        createInventoryAdjustmentTypeForm.setIsDefault(isDefault);
                    } else {
                        inventoryAdjustmentTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory adjustment type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryAdjustmentTypeForm = persona.createInventoryAdjustmentTypeForm;
                    var inventoryAdjustmentTypeEdit = persona.inventoryAdjustmentTypeEdit;

                    assertThat(createInventoryAdjustmentTypeForm != null || inventoryAdjustmentTypeEdit != null).isTrue();

                    if(createInventoryAdjustmentTypeForm != null) {
                        createInventoryAdjustmentTypeForm.setSortOrder(sortOrder);
                    } else {
                        inventoryAdjustmentTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory adjustment type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryAdjustmentTypeForm = persona.createInventoryAdjustmentTypeForm;
                    var inventoryAdjustmentTypeEdit = persona.inventoryAdjustmentTypeEdit;

                    assertThat(createInventoryAdjustmentTypeForm != null || inventoryAdjustmentTypeEdit != null).isTrue();

                    if(createInventoryAdjustmentTypeForm != null) {
                        createInventoryAdjustmentTypeForm.setDescription(description);
                    } else {
                        inventoryAdjustmentTypeEdit.setDescription(description);
                    }
                });

    }

}
