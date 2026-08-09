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
import com.echothree.control.user.inventory.common.result.CreateInventoryBucketTypeResult;
import com.echothree.control.user.inventory.common.result.EditInventoryBucketTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryBucketTypeSteps implements En {

    public InventoryBucketTypeSteps() {
        When("^the user begins entering a new inventory bucket type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryBucketTypeForm).isNull();
                    assertThat(persona.deleteInventoryBucketTypeForm).isNull();
                    assertThat(persona.inventoryBucketTypeSpec).isNull();

                    persona.createInventoryBucketTypeForm = InventoryUtil.getHome().getCreateInventoryBucketTypeForm();
                });

        And("^the user adds the new inventory bucket type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryBucketTypeForm = persona.createInventoryBucketTypeForm;

                    assertThat(createInventoryBucketTypeForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryBucketType(persona.userVisitPK, createInventoryBucketTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryBucketTypeName = commandResult.getHasErrors() ? null : result.getInventoryBucketTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryBucketTypeForm = null;
                });

        When("^the user begins deleting an inventory bucket type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryBucketTypeForm).isNull();
                    assertThat(persona.deleteInventoryBucketTypeForm).isNull();
                    assertThat(persona.inventoryBucketTypeSpec).isNull();

                    persona.deleteInventoryBucketTypeForm = InventoryUtil.getHome().getDeleteInventoryBucketTypeForm();
                });

        And("^the user deletes the inventory bucket type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryBucketTypeForm = persona.deleteInventoryBucketTypeForm;

                    assertThat(deleteInventoryBucketTypeForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryBucketType(persona.userVisitPK, deleteInventoryBucketTypeForm);

                    persona.deleteInventoryBucketTypeForm = null;
                });

        When("^the user begins specifying an inventory bucket type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryBucketTypeForm).isNull();
                    assertThat(persona.deleteInventoryBucketTypeForm).isNull();
                    assertThat(persona.inventoryBucketTypeSpec).isNull();

                    persona.inventoryBucketTypeSpec = InventoryUtil.getHome().getInventoryBucketTypeUniversalSpec();
                });

        When("^the user begins editing the inventory bucket type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryBucketTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryBucketTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryBucketType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryBucketTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory bucket type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryBucketTypeSpec;
                    var edit = persona.inventoryBucketTypeEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryBucketTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryBucketType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryBucketTypeSpec = null;
                    persona.inventoryBucketTypeEdit = null;
                });

        And("^the user sets the inventory bucket type's inventory bucket type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryBucketTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryBucketTypeForm = persona.createInventoryBucketTypeForm;
                    var deleteInventoryBucketTypeForm = persona.deleteInventoryBucketTypeForm;
                    var inventoryBucketTypeSpec = persona.inventoryBucketTypeSpec;

                    assertThat(createInventoryBucketTypeForm != null || deleteInventoryBucketTypeForm != null
                            || inventoryBucketTypeSpec != null).isTrue();

                    if(createInventoryBucketTypeForm != null) {
                        createInventoryBucketTypeForm.setInventoryBucketTypeName(inventoryBucketTypeName);
                    } else if(deleteInventoryBucketTypeForm != null) {
                        deleteInventoryBucketTypeForm.setInventoryBucketTypeName(inventoryBucketTypeName);
                    } else {
                        inventoryBucketTypeSpec.setInventoryBucketTypeName(inventoryBucketTypeName);
                    }
                });

        And("^the user sets the inventory bucket type's inventory bucket type name to the last inventory bucket type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryBucketTypeForm = persona.createInventoryBucketTypeForm;
                    var deleteInventoryBucketTypeForm = persona.deleteInventoryBucketTypeForm;
                    var inventoryBucketTypeSpec = persona.inventoryBucketTypeSpec;

                    assertThat(createInventoryBucketTypeForm != null || deleteInventoryBucketTypeForm != null
                            || inventoryBucketTypeSpec != null).isTrue();

                    if(createInventoryBucketTypeForm != null) {
                        createInventoryBucketTypeForm.setInventoryBucketTypeName(persona.lastInventoryBucketTypeName);
                    } else if(deleteInventoryBucketTypeForm != null) {
                        deleteInventoryBucketTypeForm.setInventoryBucketTypeName(persona.lastInventoryBucketTypeName);
                    } else {
                        inventoryBucketTypeSpec.setInventoryBucketTypeName(persona.lastInventoryBucketTypeName);
                    }
                });

        And("^the user sets the inventory bucket type's new inventory bucket type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryBucketTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryBucketTypeEdit = persona.inventoryBucketTypeEdit;

                    assertThat(inventoryBucketTypeEdit).isNotNull();

                    inventoryBucketTypeEdit.setInventoryBucketTypeName(inventoryBucketTypeName);
                });

        And("^the user sets the inventory bucket type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryBucketTypeForm = persona.createInventoryBucketTypeForm;
                    var inventoryBucketTypeEdit = persona.inventoryBucketTypeEdit;

                    assertThat(createInventoryBucketTypeForm != null || inventoryBucketTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryBucketTypeForm != null) {
                        createInventoryBucketTypeForm.setIsDefault(isDefault);
                    } else {
                        inventoryBucketTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory bucket type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryBucketTypeForm = persona.createInventoryBucketTypeForm;
                    var inventoryBucketTypeEdit = persona.inventoryBucketTypeEdit;

                    assertThat(createInventoryBucketTypeForm != null || inventoryBucketTypeEdit != null).isTrue();

                    if(createInventoryBucketTypeForm != null) {
                        createInventoryBucketTypeForm.setSortOrder(sortOrder);
                    } else {
                        inventoryBucketTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory bucket type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryBucketTypeForm = persona.createInventoryBucketTypeForm;
                    var inventoryBucketTypeEdit = persona.inventoryBucketTypeEdit;

                    assertThat(createInventoryBucketTypeForm != null || inventoryBucketTypeEdit != null).isTrue();

                    if(createInventoryBucketTypeForm != null) {
                        createInventoryBucketTypeForm.setDescription(description);
                    } else {
                        inventoryBucketTypeEdit.setDescription(description);
                    }
                });

    }

}
