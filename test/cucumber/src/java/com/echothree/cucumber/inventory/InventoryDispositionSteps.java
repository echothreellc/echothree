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
import com.echothree.control.user.inventory.common.result.CreateInventoryDispositionResult;
import com.echothree.control.user.inventory.common.result.EditInventoryDispositionResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryDispositionSteps implements En {

    public InventoryDispositionSteps() {
        When("^the user begins entering a new inventory disposition$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryDispositionForm).isNull();
                    assertThat(persona.deleteInventoryDispositionForm).isNull();
                    assertThat(persona.inventoryDispositionSpec).isNull();

                    persona.createInventoryDispositionForm = InventoryUtil.getHome().getCreateInventoryDispositionForm();
                });

        And("^the user adds the new inventory disposition$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionForm = persona.createInventoryDispositionForm;

                    assertThat(createInventoryDispositionForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryDisposition(persona.userVisitPK,
                            createInventoryDispositionForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryDispositionName = commandResult.getHasErrors() ? null
                                : result.getInventoryDispositionName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryDispositionForm = null;
                });

        When("^the user begins deleting an inventory disposition$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryDispositionForm).isNull();
                    assertThat(persona.deleteInventoryDispositionForm).isNull();
                    assertThat(persona.inventoryDispositionSpec).isNull();

                    persona.deleteInventoryDispositionForm = InventoryUtil.getHome().getDeleteInventoryDispositionForm();
                });

        And("^the user deletes the inventory disposition$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryDispositionForm = persona.deleteInventoryDispositionForm;

                    assertThat(deleteInventoryDispositionForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryDisposition(persona.userVisitPK,
                            deleteInventoryDispositionForm);

                    persona.deleteInventoryDispositionForm = null;
                });

        When("^the user begins specifying an inventory disposition to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryDispositionForm).isNull();
                    assertThat(persona.deleteInventoryDispositionForm).isNull();
                    assertThat(persona.inventoryDispositionSpec).isNull();

                    persona.inventoryDispositionSpec = InventoryUtil.getHome().getInventoryDispositionUniversalSpec();
                });

        When("^the user begins editing the inventory disposition$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryDispositionSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryDispositionForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryDisposition(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryDispositionEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory disposition$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryDispositionSpec;
                    var edit = persona.inventoryDispositionEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryDispositionForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryDisposition(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryDispositionSpec = null;
                    persona.inventoryDispositionEdit = null;
                });

        And("^the user sets the inventory disposition's inventory disposition name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryDispositionName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionForm = persona.createInventoryDispositionForm;
                    var deleteInventoryDispositionForm = persona.deleteInventoryDispositionForm;
                    var inventoryDispositionSpec = persona.inventoryDispositionSpec;

                    assertThat(createInventoryDispositionForm != null || deleteInventoryDispositionForm != null
                            || inventoryDispositionSpec != null).isTrue();

                    if(createInventoryDispositionForm != null) {
                        createInventoryDispositionForm.setInventoryDispositionName(inventoryDispositionName);
                    } else if(deleteInventoryDispositionForm != null) {
                        deleteInventoryDispositionForm.setInventoryDispositionName(inventoryDispositionName);
                    } else {
                        inventoryDispositionSpec.setInventoryDispositionName(inventoryDispositionName);
                    }
                });

        And("^the user sets the inventory disposition's inventory transaction type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createForm = persona.createInventoryDispositionForm;
                    var deleteForm = persona.deleteInventoryDispositionForm;
                    var spec = persona.inventoryDispositionSpec;

                    assertThat(createForm != null || deleteForm != null || spec != null).isTrue();

                    if(createForm != null) {
                        createForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else if(deleteForm != null) {
                        deleteForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else {
                        spec.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    }
                });

        And("^the user sets the inventory disposition's inventory disposition name "
                + "to the last inventory disposition added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionForm = persona.createInventoryDispositionForm;
                    var deleteInventoryDispositionForm = persona.deleteInventoryDispositionForm;
                    var inventoryDispositionSpec = persona.inventoryDispositionSpec;

                    assertThat(createInventoryDispositionForm != null || deleteInventoryDispositionForm != null
                            || inventoryDispositionSpec != null).isTrue();

                    if(createInventoryDispositionForm != null) {
                        createInventoryDispositionForm.setInventoryDispositionName(persona.lastInventoryDispositionName);
                    } else if(deleteInventoryDispositionForm != null) {
                        deleteInventoryDispositionForm.setInventoryDispositionName(persona.lastInventoryDispositionName);
                    } else {
                        inventoryDispositionSpec.setInventoryDispositionName(persona.lastInventoryDispositionName);
                    }
                });

        And("^the user sets the inventory disposition's entity ref to the last inventory disposition added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryDispositionForm = persona.deleteInventoryDispositionForm;
                    var inventoryDispositionSpec = persona.inventoryDispositionSpec;

                    assertThat(deleteInventoryDispositionForm != null || inventoryDispositionSpec != null).isTrue();
                    assertThat(persona.lastEntityRef).isNotNull();

                    if(deleteInventoryDispositionForm != null) {
                        deleteInventoryDispositionForm.setEntityRef(persona.lastEntityRef);
                    } else {
                        inventoryDispositionSpec.setEntityRef(persona.lastEntityRef);
                    }
                });

        And("^the user sets the inventory disposition's new inventory disposition name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryDispositionName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryDispositionEdit = persona.inventoryDispositionEdit;

                    assertThat(inventoryDispositionEdit).isNotNull();

                    inventoryDispositionEdit.setInventoryDispositionName(inventoryDispositionName);
                });

        And("^the user sets the inventory disposition to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionForm = persona.createInventoryDispositionForm;
                    var inventoryDispositionEdit = persona.inventoryDispositionEdit;

                    assertThat(createInventoryDispositionForm != null || inventoryDispositionEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryDispositionForm != null) {
                        createInventoryDispositionForm.setIsDefault(isDefault);
                    } else {
                        inventoryDispositionEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory disposition's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionForm = persona.createInventoryDispositionForm;
                    var inventoryDispositionEdit = persona.inventoryDispositionEdit;

                    assertThat(createInventoryDispositionForm != null || inventoryDispositionEdit != null).isTrue();

                    if(createInventoryDispositionForm != null) {
                        createInventoryDispositionForm.setSortOrder(sortOrder);
                    } else {
                        inventoryDispositionEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory disposition's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryDispositionForm = persona.createInventoryDispositionForm;
                    var inventoryDispositionEdit = persona.inventoryDispositionEdit;

                    assertThat(createInventoryDispositionForm != null || inventoryDispositionEdit != null).isTrue();

                    if(createInventoryDispositionForm != null) {
                        createInventoryDispositionForm.setDescription(description);
                    } else {
                        inventoryDispositionEdit.setDescription(description);
                    }
                });

    }

}
