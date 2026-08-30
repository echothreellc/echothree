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
import com.echothree.control.user.inventory.common.result.CreateInventoryDispositionAdjustmentResult;
import com.echothree.control.user.inventory.common.result.EditInventoryDispositionAdjustmentResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryDispositionAdjustmentSteps implements En {

    public InventoryDispositionAdjustmentSteps() {
        When("^the user begins entering a new inventory disposition adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryDispositionAdjustmentForm).isNull();
                    assertThat(persona.deleteInventoryDispositionAdjustmentForm).isNull();
                    assertThat(persona.inventoryDispositionAdjustmentSpec).isNull();

                    persona.createInventoryDispositionAdjustmentForm = InventoryUtil.getHome().getCreateInventoryDispositionAdjustmentForm();
                });

        And("^the user adds the new inventory disposition adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionAdjustmentForm = persona.createInventoryDispositionAdjustmentForm;

                    assertThat(createInventoryDispositionAdjustmentForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryDispositionAdjustment(persona.userVisitPK,
                            createInventoryDispositionAdjustmentForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryDispositionAdjustmentName = commandResult.getHasErrors() ? null
                                : result.getInventoryDispositionAdjustmentName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryDispositionAdjustmentForm = null;
                });

        When("^the user begins deleting an inventory disposition adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryDispositionAdjustmentForm).isNull();
                    assertThat(persona.deleteInventoryDispositionAdjustmentForm).isNull();
                    assertThat(persona.inventoryDispositionAdjustmentSpec).isNull();

                    persona.deleteInventoryDispositionAdjustmentForm = InventoryUtil.getHome().getDeleteInventoryDispositionAdjustmentForm();
                });

        And("^the user deletes the inventory disposition adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryDispositionAdjustmentForm = persona.deleteInventoryDispositionAdjustmentForm;

                    assertThat(deleteInventoryDispositionAdjustmentForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryDispositionAdjustment(persona.userVisitPK,
                            deleteInventoryDispositionAdjustmentForm);

                    persona.deleteInventoryDispositionAdjustmentForm = null;
                });

        When("^the user begins specifying an inventory disposition adjustment to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryDispositionAdjustmentForm).isNull();
                    assertThat(persona.deleteInventoryDispositionAdjustmentForm).isNull();
                    assertThat(persona.inventoryDispositionAdjustmentSpec).isNull();

                    persona.inventoryDispositionAdjustmentSpec = InventoryUtil.getHome().getInventoryDispositionAdjustmentUniversalSpec();
                });

        When("^the user begins editing the inventory disposition adjustment$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryDispositionAdjustmentSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryDispositionAdjustmentForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryDispositionAdjustment(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryDispositionAdjustmentEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory disposition adjustment",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryDispositionAdjustmentSpec;
                    var edit = persona.inventoryDispositionAdjustmentEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryDispositionAdjustmentForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryDispositionAdjustment(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryDispositionAdjustmentSpec = null;
                    persona.inventoryDispositionAdjustmentEdit = null;
                });

        And("^the user sets the inventory disposition adjustment's inventory disposition adjustment name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryDispositionAdjustmentName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionAdjustmentForm = persona.createInventoryDispositionAdjustmentForm;
                    var deleteInventoryDispositionAdjustmentForm = persona.deleteInventoryDispositionAdjustmentForm;
                    var inventoryDispositionAdjustmentSpec = persona.inventoryDispositionAdjustmentSpec;

                    assertThat(createInventoryDispositionAdjustmentForm != null || deleteInventoryDispositionAdjustmentForm != null
                            || inventoryDispositionAdjustmentSpec != null).isTrue();

                    if(createInventoryDispositionAdjustmentForm != null) {
                        createInventoryDispositionAdjustmentForm.setInventoryDispositionAdjustmentName(inventoryDispositionAdjustmentName);
                    } else if(deleteInventoryDispositionAdjustmentForm != null) {
                        deleteInventoryDispositionAdjustmentForm.setInventoryDispositionAdjustmentName(inventoryDispositionAdjustmentName);
                    } else {
                        inventoryDispositionAdjustmentSpec.setInventoryDispositionAdjustmentName(inventoryDispositionAdjustmentName);
                    }
                });

        And("^the user sets the inventory disposition adjustment's inventory transaction type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createForm = persona.createInventoryDispositionAdjustmentForm;
                    var deleteForm = persona.deleteInventoryDispositionAdjustmentForm;
                    var spec = persona.inventoryDispositionAdjustmentSpec;

                    assertThat(createForm != null || deleteForm != null || spec != null).isTrue();

                    if(createForm != null) {
                        createForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else if(deleteForm != null) {
                        deleteForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else {
                        spec.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    }
                });

        And("^the user sets the inventory disposition adjustment's inventory disposition adjustment name "
                + "to the last inventory disposition adjustment added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionAdjustmentForm = persona.createInventoryDispositionAdjustmentForm;
                    var deleteInventoryDispositionAdjustmentForm = persona.deleteInventoryDispositionAdjustmentForm;
                    var inventoryDispositionAdjustmentSpec = persona.inventoryDispositionAdjustmentSpec;

                    assertThat(createInventoryDispositionAdjustmentForm != null || deleteInventoryDispositionAdjustmentForm != null
                            || inventoryDispositionAdjustmentSpec != null).isTrue();

                    if(createInventoryDispositionAdjustmentForm != null) {
                        createInventoryDispositionAdjustmentForm.setInventoryDispositionAdjustmentName(persona.lastInventoryDispositionAdjustmentName);
                    } else if(deleteInventoryDispositionAdjustmentForm != null) {
                        deleteInventoryDispositionAdjustmentForm.setInventoryDispositionAdjustmentName(persona.lastInventoryDispositionAdjustmentName);
                    } else {
                        inventoryDispositionAdjustmentSpec.setInventoryDispositionAdjustmentName(persona.lastInventoryDispositionAdjustmentName);
                    }
                });

        And("^the user sets the inventory disposition adjustment's entity ref to the last inventory disposition adjustment added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryDispositionAdjustmentForm = persona.deleteInventoryDispositionAdjustmentForm;
                    var inventoryDispositionAdjustmentSpec = persona.inventoryDispositionAdjustmentSpec;

                    assertThat(deleteInventoryDispositionAdjustmentForm != null || inventoryDispositionAdjustmentSpec != null).isTrue();
                    assertThat(persona.lastEntityRef).isNotNull();

                    if(deleteInventoryDispositionAdjustmentForm != null) {
                        deleteInventoryDispositionAdjustmentForm.setEntityRef(persona.lastEntityRef);
                    } else {
                        inventoryDispositionAdjustmentSpec.setEntityRef(persona.lastEntityRef);
                    }
                });

        And("^the user sets the inventory disposition adjustment's new inventory disposition adjustment name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryDispositionAdjustmentName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryDispositionAdjustmentEdit = persona.inventoryDispositionAdjustmentEdit;

                    assertThat(inventoryDispositionAdjustmentEdit).isNotNull();

                    inventoryDispositionAdjustmentEdit.setInventoryDispositionAdjustmentName(inventoryDispositionAdjustmentName);
                });

        And("^the user sets the inventory disposition adjustment to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionAdjustmentForm = persona.createInventoryDispositionAdjustmentForm;
                    var inventoryDispositionAdjustmentEdit = persona.inventoryDispositionAdjustmentEdit;

                    assertThat(createInventoryDispositionAdjustmentForm != null || inventoryDispositionAdjustmentEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryDispositionAdjustmentForm != null) {
                        createInventoryDispositionAdjustmentForm.setIsDefault(isDefault);
                    } else {
                        inventoryDispositionAdjustmentEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory disposition adjustment's inventory disposition name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryDispositionName) -> {
            var persona = CurrentPersona.persona;
                    var createInventoryDispositionAdjustmentForm = persona.createInventoryDispositionAdjustmentForm;
                    var deleteInventoryDispositionAdjustmentForm = persona.deleteInventoryDispositionAdjustmentForm;
                    var inventoryDispositionAdjustmentSpec = persona.inventoryDispositionAdjustmentSpec;

                    assertThat(createInventoryDispositionAdjustmentForm != null || deleteInventoryDispositionAdjustmentForm != null
                            || inventoryDispositionAdjustmentSpec != null).isTrue();

                    if(createInventoryDispositionAdjustmentForm != null) {
                        createInventoryDispositionAdjustmentForm.setInventoryDispositionName(inventoryDispositionName);
                    } else if(deleteInventoryDispositionAdjustmentForm != null) {
                        deleteInventoryDispositionAdjustmentForm.setInventoryDispositionName(inventoryDispositionName);
                    } else {
                        inventoryDispositionAdjustmentSpec.setInventoryDispositionName(inventoryDispositionName);
                    }
                });

        And("^the user sets the inventory disposition adjustment's inventory adjustment type name to \"([a-zA-Z0-9-_]*)\"$",
                (String name) -> {
                    var persona = CurrentPersona.persona;
                    var createForm = persona.createInventoryDispositionAdjustmentForm;
                    var edit = persona.inventoryDispositionAdjustmentEdit;
                    assertThat(createForm != null || edit != null).isTrue();
                    if(createForm != null) createForm.setInventoryAdjustmentTypeName(name);
                    else edit.setInventoryAdjustmentTypeName(name);
                });

        And("^the user sets the inventory disposition adjustment's inventory bucket type name to \"([a-zA-Z0-9-_]*)\"$",
                (String name) -> {
                    var persona = CurrentPersona.persona;
                    var createForm = persona.createInventoryDispositionAdjustmentForm;
                    var edit = persona.inventoryDispositionAdjustmentEdit;
                    assertThat(createForm != null || edit != null).isTrue();
                    if(createForm != null) createForm.setInventoryBucketTypeName(name);
                    else edit.setInventoryBucketTypeName(name);
                });

        And("^the user sets the inventory disposition adjustment's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionAdjustmentForm = persona.createInventoryDispositionAdjustmentForm;
                    var inventoryDispositionAdjustmentEdit = persona.inventoryDispositionAdjustmentEdit;

                    assertThat(createInventoryDispositionAdjustmentForm != null || inventoryDispositionAdjustmentEdit != null).isTrue();

                    if(createInventoryDispositionAdjustmentForm != null) {
                        createInventoryDispositionAdjustmentForm.setSortOrder(sortOrder);
                    } else {
                        inventoryDispositionAdjustmentEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory disposition adjustment's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionAdjustmentForm = persona.createInventoryDispositionAdjustmentForm;
                    var inventoryDispositionAdjustmentEdit = persona.inventoryDispositionAdjustmentEdit;

                    assertThat(createInventoryDispositionAdjustmentForm != null || inventoryDispositionAdjustmentEdit != null).isTrue();

                    if(createInventoryDispositionAdjustmentForm != null) {
                        createInventoryDispositionAdjustmentForm.setDescription(description);
                    } else {
                        inventoryDispositionAdjustmentEdit.setDescription(description);
                    }
                });

    }

}
