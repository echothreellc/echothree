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
import com.echothree.control.user.inventory.common.result.CreateInventoryTransactionTypeResult;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTransactionTypeSteps implements En {

    public InventoryTransactionTypeSteps() {
        When("^the user begins entering a new inventory transaction type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionTypeForm).isNull();
                    assertThat(persona.inventoryTransactionTypeSpec).isNull();

                    persona.createInventoryTransactionTypeForm = InventoryUtil.getHome().getCreateInventoryTransactionTypeForm();
                });

        And("^the user adds the new inventory transaction type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTypeForm = persona.createInventoryTransactionTypeForm;

                    assertThat(createInventoryTransactionTypeForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryTransactionType(persona.userVisitPK,
                            createInventoryTransactionTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateInventoryTransactionTypeResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryTransactionTypeName = commandResult.getHasErrors() ? null : result.getInventoryTransactionTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryTransactionTypeForm = null;
                });

        When("^the user begins deleting an inventory transaction type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionTypeForm).isNull();
                    assertThat(persona.inventoryTransactionTypeSpec).isNull();

                    persona.deleteInventoryTransactionTypeForm = InventoryUtil.getHome().getDeleteInventoryTransactionTypeForm();
                });

        And("^the user deletes the inventory transaction type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionTypeForm = persona.deleteInventoryTransactionTypeForm;

                    assertThat(deleteInventoryTransactionTypeForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryTransactionType(persona.userVisitPK,
                            deleteInventoryTransactionTypeForm);

                    persona.deleteInventoryTransactionTypeForm = null;
                });

        When("^the user begins specifying an inventory transaction type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionTypeForm).isNull();
                    assertThat(persona.inventoryTransactionTypeSpec).isNull();

                    persona.inventoryTransactionTypeSpec = InventoryUtil.getHome().getInventoryTransactionTypeUniversalSpec();
                });

        When("^the user begins editing the inventory transaction type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditInventoryTransactionTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryTransactionTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory transaction type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionTypeSpec;
                    var edit = persona.inventoryTransactionTypeEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryTransactionTypeSpec = null;
                    persona.inventoryTransactionTypeEdit = null;
                });

        And("^the user sets the inventory transaction type's inventory transaction type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTypeForm = persona.createInventoryTransactionTypeForm;
                    var deleteInventoryTransactionTypeForm = persona.deleteInventoryTransactionTypeForm;
                    var inventoryTransactionTypeSpec = persona.inventoryTransactionTypeSpec;

                    assertThat(createInventoryTransactionTypeForm != null || deleteInventoryTransactionTypeForm != null
                            || inventoryTransactionTypeSpec != null).isTrue();

                    if(createInventoryTransactionTypeForm != null) {
                        createInventoryTransactionTypeForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else if(deleteInventoryTransactionTypeForm != null) {
                        deleteInventoryTransactionTypeForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else {
                        inventoryTransactionTypeSpec.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    }
                });

        And("^the user sets the inventory transaction type's inventory transaction type name to the last inventory transaction type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTypeForm = persona.createInventoryTransactionTypeForm;
                    var deleteInventoryTransactionTypeForm = persona.deleteInventoryTransactionTypeForm;
                    var inventoryTransactionTypeSpec = persona.inventoryTransactionTypeSpec;

                    assertThat(createInventoryTransactionTypeForm != null || deleteInventoryTransactionTypeForm != null
                            || inventoryTransactionTypeSpec != null).isTrue();

                    if(createInventoryTransactionTypeForm != null) {
                        createInventoryTransactionTypeForm.setInventoryTransactionTypeName(persona.lastInventoryTransactionTypeName);
                    } else if(deleteInventoryTransactionTypeForm != null) {
                        deleteInventoryTransactionTypeForm.setInventoryTransactionTypeName(persona.lastInventoryTransactionTypeName);
                    } else {
                        inventoryTransactionTypeSpec.setInventoryTransactionTypeName(persona.lastInventoryTransactionTypeName);
                    }
                });

        And("^the user sets the inventory transaction type's entity ref to the last inventory transaction added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionTypeForm = persona.deleteInventoryTransactionTypeForm;
                    var inventoryTransactionTypeSpec = persona.inventoryTransactionTypeSpec;

                    assertThat(deleteInventoryTransactionTypeForm != null || inventoryTransactionTypeSpec != null).isTrue();
                    assertThat(persona.lastEntityRef).isNotNull();

                    if(deleteInventoryTransactionTypeForm != null) {
                        deleteInventoryTransactionTypeForm.setEntityRef(persona.lastEntityRef);
                    } else {
                        inventoryTransactionTypeSpec.setEntityRef(persona.lastEntityRef);
                    }
                });

        And("^the user sets the inventory transaction type's new inventory transaction type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryTransactionTypeEdit = persona.inventoryTransactionTypeEdit;

                    assertThat(inventoryTransactionTypeEdit).isNotNull();

                    inventoryTransactionTypeEdit.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                });

        And("^the user sets the inventory transaction type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTypeForm = persona.createInventoryTransactionTypeForm;
                    var inventoryTransactionTypeEdit = persona.inventoryTransactionTypeEdit;

                    assertThat(createInventoryTransactionTypeForm != null || inventoryTransactionTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryTransactionTypeForm != null) {
                        createInventoryTransactionTypeForm.setIsDefault(isDefault);
                    } else {
                        inventoryTransactionTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory transaction type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTypeForm = persona.createInventoryTransactionTypeForm;
                    var inventoryTransactionTypeEdit = persona.inventoryTransactionTypeEdit;

                    assertThat(createInventoryTransactionTypeForm != null || inventoryTransactionTypeEdit != null).isTrue();

                    if(createInventoryTransactionTypeForm != null) {
                        createInventoryTransactionTypeForm.setSortOrder(sortOrder);
                    } else {
                        inventoryTransactionTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory transaction type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTypeForm = persona.createInventoryTransactionTypeForm;
                    var inventoryTransactionTypeEdit = persona.inventoryTransactionTypeEdit;

                    assertThat(createInventoryTransactionTypeForm != null || inventoryTransactionTypeEdit != null).isTrue();

                    if(createInventoryTransactionTypeForm != null) {
                        createInventoryTransactionTypeForm.setDescription(description);
                    } else {
                        inventoryTransactionTypeEdit.setDescription(description);
                    }
                });

    }

}
