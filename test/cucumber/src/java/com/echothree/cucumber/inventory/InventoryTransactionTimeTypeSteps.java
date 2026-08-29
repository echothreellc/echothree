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
import com.echothree.control.user.inventory.common.result.CreateInventoryTransactionTimeTypeResult;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionTimeTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTransactionTimeTypeSteps implements En {

    public InventoryTransactionTimeTypeSteps() {
        When("^the user begins entering a new inventory transaction time type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionTimeTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionTimeTypeForm).isNull();
                    assertThat(persona.inventoryTransactionTimeTypeSpec).isNull();

                    persona.createInventoryTransactionTimeTypeForm = InventoryUtil.getHome().getCreateInventoryTransactionTimeTypeForm();
                });

        And("^the user adds the new inventory transaction time type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTimeTypeForm = persona.createInventoryTransactionTimeTypeForm;

                    assertThat(createInventoryTransactionTimeTypeForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryTransactionTimeType(persona.userVisitPK,
                            createInventoryTransactionTimeTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryTransactionTimeTypeName = commandResult.getHasErrors() ? null
                                : result.getInventoryTransactionTimeTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryTransactionTimeTypeForm = null;
                });

        When("^the user begins deleting an inventory transaction time type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionTimeTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionTimeTypeForm).isNull();
                    assertThat(persona.inventoryTransactionTimeTypeSpec).isNull();

                    persona.deleteInventoryTransactionTimeTypeForm = InventoryUtil.getHome().getDeleteInventoryTransactionTimeTypeForm();
                });

        And("^the user deletes the inventory transaction time type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionTimeTypeForm = persona.deleteInventoryTransactionTimeTypeForm;

                    assertThat(deleteInventoryTransactionTimeTypeForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryTransactionTimeType(persona.userVisitPK,
                            deleteInventoryTransactionTimeTypeForm);

                    persona.deleteInventoryTransactionTimeTypeForm = null;
                });

        When("^the user begins specifying an inventory transaction time type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionTimeTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionTimeTypeForm).isNull();
                    assertThat(persona.inventoryTransactionTimeTypeSpec).isNull();

                    persona.inventoryTransactionTimeTypeSpec = InventoryUtil.getHome().getInventoryTransactionTimeTypeUniversalSpec();
                });

        When("^the user begins editing the inventory transaction time type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionTimeTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionTimeTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionTimeType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryTransactionTimeTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory transaction time type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionTimeTypeSpec;
                    var edit = persona.inventoryTransactionTimeTypeEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionTimeTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionTimeType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryTransactionTimeTypeSpec = null;
                    persona.inventoryTransactionTimeTypeEdit = null;
                });

        And("^the user sets the inventory transaction time type's inventory transaction time type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTimeTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTimeTypeForm = persona.createInventoryTransactionTimeTypeForm;
                    var deleteInventoryTransactionTimeTypeForm = persona.deleteInventoryTransactionTimeTypeForm;
                    var inventoryTransactionTimeTypeSpec = persona.inventoryTransactionTimeTypeSpec;

                    assertThat(createInventoryTransactionTimeTypeForm != null || deleteInventoryTransactionTimeTypeForm != null
                            || inventoryTransactionTimeTypeSpec != null).isTrue();

                    if(createInventoryTransactionTimeTypeForm != null) {
                        createInventoryTransactionTimeTypeForm.setInventoryTransactionTimeTypeName(inventoryTransactionTimeTypeName);
                    } else if(deleteInventoryTransactionTimeTypeForm != null) {
                        deleteInventoryTransactionTimeTypeForm.setInventoryTransactionTimeTypeName(inventoryTransactionTimeTypeName);
                    } else {
                        inventoryTransactionTimeTypeSpec.setInventoryTransactionTimeTypeName(inventoryTransactionTimeTypeName);
                    }
                });

        And("^the user sets the inventory transaction time type's inventory transaction type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createForm = persona.createInventoryTransactionTimeTypeForm;
                    var deleteForm = persona.deleteInventoryTransactionTimeTypeForm;
                    var spec = persona.inventoryTransactionTimeTypeSpec;

                    assertThat(createForm != null || deleteForm != null || spec != null).isTrue();

                    if(createForm != null) {
                        createForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else if(deleteForm != null) {
                        deleteForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else {
                        spec.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    }
                });

        And("^the user sets the inventory transaction time type's inventory transaction time type name "
                + "to the last inventory transaction time type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTimeTypeForm = persona.createInventoryTransactionTimeTypeForm;
                    var deleteInventoryTransactionTimeTypeForm = persona.deleteInventoryTransactionTimeTypeForm;
                    var inventoryTransactionTimeTypeSpec = persona.inventoryTransactionTimeTypeSpec;

                    assertThat(createInventoryTransactionTimeTypeForm != null || deleteInventoryTransactionTimeTypeForm != null
                            || inventoryTransactionTimeTypeSpec != null).isTrue();

                    if(createInventoryTransactionTimeTypeForm != null) {
                        createInventoryTransactionTimeTypeForm.setInventoryTransactionTimeTypeName(persona.lastInventoryTransactionTimeTypeName);
                    } else if(deleteInventoryTransactionTimeTypeForm != null) {
                        deleteInventoryTransactionTimeTypeForm.setInventoryTransactionTimeTypeName(persona.lastInventoryTransactionTimeTypeName);
                    } else {
                        inventoryTransactionTimeTypeSpec.setInventoryTransactionTimeTypeName(persona.lastInventoryTransactionTimeTypeName);
                    }
                });

        And("^the user sets the inventory transaction time type's entity ref to the last inventory transaction time type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionTimeTypeForm = persona.deleteInventoryTransactionTimeTypeForm;
                    var inventoryTransactionTimeTypeSpec = persona.inventoryTransactionTimeTypeSpec;

                    assertThat(deleteInventoryTransactionTimeTypeForm != null || inventoryTransactionTimeTypeSpec != null).isTrue();
                    assertThat(persona.lastEntityRef).isNotNull();

                    if(deleteInventoryTransactionTimeTypeForm != null) {
                        deleteInventoryTransactionTimeTypeForm.setEntityRef(persona.lastEntityRef);
                    } else {
                        inventoryTransactionTimeTypeSpec.setEntityRef(persona.lastEntityRef);
                    }
                });

        And("^the user sets the inventory transaction time type's new inventory transaction time type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTimeTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryTransactionTimeTypeEdit = persona.inventoryTransactionTimeTypeEdit;

                    assertThat(inventoryTransactionTimeTypeEdit).isNotNull();

                    inventoryTransactionTimeTypeEdit.setInventoryTransactionTimeTypeName(inventoryTransactionTimeTypeName);
                });

        And("^the user sets the inventory transaction time type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTimeTypeForm = persona.createInventoryTransactionTimeTypeForm;
                    var inventoryTransactionTimeTypeEdit = persona.inventoryTransactionTimeTypeEdit;

                    assertThat(createInventoryTransactionTimeTypeForm != null || inventoryTransactionTimeTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryTransactionTimeTypeForm != null) {
                        createInventoryTransactionTimeTypeForm.setIsDefault(isDefault);
                    } else {
                        inventoryTransactionTimeTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory transaction time type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTimeTypeForm = persona.createInventoryTransactionTimeTypeForm;
                    var inventoryTransactionTimeTypeEdit = persona.inventoryTransactionTimeTypeEdit;

                    assertThat(createInventoryTransactionTimeTypeForm != null || inventoryTransactionTimeTypeEdit != null).isTrue();

                    if(createInventoryTransactionTimeTypeForm != null) {
                        createInventoryTransactionTimeTypeForm.setSortOrder(sortOrder);
                    } else {
                        inventoryTransactionTimeTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory transaction time type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionTimeTypeForm = persona.createInventoryTransactionTimeTypeForm;
                    var inventoryTransactionTimeTypeEdit = persona.inventoryTransactionTimeTypeEdit;

                    assertThat(createInventoryTransactionTimeTypeForm != null || inventoryTransactionTimeTypeEdit != null).isTrue();

                    if(createInventoryTransactionTimeTypeForm != null) {
                        createInventoryTransactionTimeTypeForm.setDescription(description);
                    } else {
                        inventoryTransactionTimeTypeEdit.setDescription(description);
                    }
                });

    }

}
