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
import com.echothree.control.user.inventory.common.result.CreateInventoryTransactionRoleTypeResult;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionRoleTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTransactionRoleTypeSteps implements En {

    public InventoryTransactionRoleTypeSteps() {
        When("^the user begins entering a new inventory transaction role type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionRoleTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionRoleTypeForm).isNull();
                    assertThat(persona.inventoryTransactionRoleTypeSpec).isNull();

                    persona.createInventoryTransactionRoleTypeForm = InventoryUtil.getHome().getCreateInventoryTransactionRoleTypeForm();
                });

        And("^the user adds the new inventory transaction role type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionRoleTypeForm = persona.createInventoryTransactionRoleTypeForm;

                    assertThat(createInventoryTransactionRoleTypeForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryTransactionRoleType(persona.userVisitPK,
                            createInventoryTransactionRoleTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryTransactionRoleTypeName = commandResult.getHasErrors() ? null
                                : result.getInventoryTransactionRoleTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryTransactionRoleTypeForm = null;
                });

        When("^the user begins deleting an inventory transaction role type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionRoleTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionRoleTypeForm).isNull();
                    assertThat(persona.inventoryTransactionRoleTypeSpec).isNull();

                    persona.deleteInventoryTransactionRoleTypeForm = InventoryUtil.getHome().getDeleteInventoryTransactionRoleTypeForm();
                });

        And("^the user deletes the inventory transaction role type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionRoleTypeForm = persona.deleteInventoryTransactionRoleTypeForm;

                    assertThat(deleteInventoryTransactionRoleTypeForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryTransactionRoleType(persona.userVisitPK,
                            deleteInventoryTransactionRoleTypeForm);

                    persona.deleteInventoryTransactionRoleTypeForm = null;
                });

        When("^the user begins specifying an inventory transaction role type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionRoleTypeForm).isNull();
                    assertThat(persona.deleteInventoryTransactionRoleTypeForm).isNull();
                    assertThat(persona.inventoryTransactionRoleTypeSpec).isNull();

                    persona.inventoryTransactionRoleTypeSpec = InventoryUtil.getHome().getInventoryTransactionRoleTypeUniversalSpec();
                });

        When("^the user begins editing the inventory transaction role type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionRoleTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionRoleTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionRoleType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryTransactionRoleTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory transaction role type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionRoleTypeSpec;
                    var edit = persona.inventoryTransactionRoleTypeEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionRoleTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionRoleType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryTransactionRoleTypeSpec = null;
                    persona.inventoryTransactionRoleTypeEdit = null;
                });

        And("^the user sets the inventory transaction role type's inventory transaction role type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionRoleTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionRoleTypeForm = persona.createInventoryTransactionRoleTypeForm;
                    var deleteInventoryTransactionRoleTypeForm = persona.deleteInventoryTransactionRoleTypeForm;
                    var inventoryTransactionRoleTypeSpec = persona.inventoryTransactionRoleTypeSpec;

                    assertThat(createInventoryTransactionRoleTypeForm != null || deleteInventoryTransactionRoleTypeForm != null
                            || inventoryTransactionRoleTypeSpec != null).isTrue();

                    if(createInventoryTransactionRoleTypeForm != null) {
                        createInventoryTransactionRoleTypeForm.setInventoryTransactionRoleTypeName(inventoryTransactionRoleTypeName);
                    } else if(deleteInventoryTransactionRoleTypeForm != null) {
                        deleteInventoryTransactionRoleTypeForm.setInventoryTransactionRoleTypeName(inventoryTransactionRoleTypeName);
                    } else {
                        inventoryTransactionRoleTypeSpec.setInventoryTransactionRoleTypeName(inventoryTransactionRoleTypeName);
                    }
                });

        And("^the user sets the inventory transaction role type's inventory transaction type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createForm = persona.createInventoryTransactionRoleTypeForm;
                    var deleteForm = persona.deleteInventoryTransactionRoleTypeForm;
                    var spec = persona.inventoryTransactionRoleTypeSpec;

                    assertThat(createForm != null || deleteForm != null || spec != null).isTrue();

                    if(createForm != null) {
                        createForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else if(deleteForm != null) {
                        deleteForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else {
                        spec.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    }
                });

        And("^the user sets the inventory transaction role type's inventory transaction role type name "
                + "to the last inventory transaction role type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionRoleTypeForm = persona.createInventoryTransactionRoleTypeForm;
                    var deleteInventoryTransactionRoleTypeForm = persona.deleteInventoryTransactionRoleTypeForm;
                    var inventoryTransactionRoleTypeSpec = persona.inventoryTransactionRoleTypeSpec;

                    assertThat(createInventoryTransactionRoleTypeForm != null || deleteInventoryTransactionRoleTypeForm != null
                            || inventoryTransactionRoleTypeSpec != null).isTrue();

                    if(createInventoryTransactionRoleTypeForm != null) {
                        createInventoryTransactionRoleTypeForm.setInventoryTransactionRoleTypeName(persona.lastInventoryTransactionRoleTypeName);
                    } else if(deleteInventoryTransactionRoleTypeForm != null) {
                        deleteInventoryTransactionRoleTypeForm.setInventoryTransactionRoleTypeName(persona.lastInventoryTransactionRoleTypeName);
                    } else {
                        inventoryTransactionRoleTypeSpec.setInventoryTransactionRoleTypeName(persona.lastInventoryTransactionRoleTypeName);
                    }
                });

        And("^the user sets the inventory transaction role type's entity ref to the last inventory transaction role type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionRoleTypeForm = persona.deleteInventoryTransactionRoleTypeForm;
                    var inventoryTransactionRoleTypeSpec = persona.inventoryTransactionRoleTypeSpec;

                    assertThat(deleteInventoryTransactionRoleTypeForm != null || inventoryTransactionRoleTypeSpec != null).isTrue();
                    assertThat(persona.lastEntityRef).isNotNull();

                    if(deleteInventoryTransactionRoleTypeForm != null) {
                        deleteInventoryTransactionRoleTypeForm.setEntityRef(persona.lastEntityRef);
                    } else {
                        inventoryTransactionRoleTypeSpec.setEntityRef(persona.lastEntityRef);
                    }
                });

        And("^the user sets the inventory transaction role type's new inventory transaction role type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionRoleTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryTransactionRoleTypeEdit = persona.inventoryTransactionRoleTypeEdit;

                    assertThat(inventoryTransactionRoleTypeEdit).isNotNull();

                    inventoryTransactionRoleTypeEdit.setInventoryTransactionRoleTypeName(inventoryTransactionRoleTypeName);
                });

        And("^the user sets the inventory transaction role type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionRoleTypeForm = persona.createInventoryTransactionRoleTypeForm;
                    var inventoryTransactionRoleTypeEdit = persona.inventoryTransactionRoleTypeEdit;

                    assertThat(createInventoryTransactionRoleTypeForm != null || inventoryTransactionRoleTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryTransactionRoleTypeForm != null) {
                        createInventoryTransactionRoleTypeForm.setIsDefault(isDefault);
                    } else {
                        inventoryTransactionRoleTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory transaction role type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionRoleTypeForm = persona.createInventoryTransactionRoleTypeForm;
                    var inventoryTransactionRoleTypeEdit = persona.inventoryTransactionRoleTypeEdit;

                    assertThat(createInventoryTransactionRoleTypeForm != null || inventoryTransactionRoleTypeEdit != null).isTrue();

                    if(createInventoryTransactionRoleTypeForm != null) {
                        createInventoryTransactionRoleTypeForm.setSortOrder(sortOrder);
                    } else {
                        inventoryTransactionRoleTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory transaction role type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionRoleTypeForm = persona.createInventoryTransactionRoleTypeForm;
                    var inventoryTransactionRoleTypeEdit = persona.inventoryTransactionRoleTypeEdit;

                    assertThat(createInventoryTransactionRoleTypeForm != null || inventoryTransactionRoleTypeEdit != null).isTrue();

                    if(createInventoryTransactionRoleTypeForm != null) {
                        createInventoryTransactionRoleTypeForm.setDescription(description);
                    } else {
                        inventoryTransactionRoleTypeEdit.setDescription(description);
                    }
                });

    }

}
