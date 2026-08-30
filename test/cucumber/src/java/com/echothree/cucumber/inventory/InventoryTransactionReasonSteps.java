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
import com.echothree.control.user.inventory.common.result.CreateInventoryTransactionReasonResult;
import com.echothree.control.user.inventory.common.result.EditInventoryTransactionReasonResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTransactionReasonSteps implements En {

    public InventoryTransactionReasonSteps() {
        When("^the user begins entering a new inventory transaction reason$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionReasonForm).isNull();
                    assertThat(persona.deleteInventoryTransactionReasonForm).isNull();
                    assertThat(persona.inventoryTransactionReasonSpec).isNull();

                    persona.createInventoryTransactionReasonForm = InventoryUtil.getHome().getCreateInventoryTransactionReasonForm();
                });

        And("^the user adds the new inventory transaction reason",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionReasonForm = persona.createInventoryTransactionReasonForm;

                    assertThat(createInventoryTransactionReasonForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createInventoryTransactionReason(persona.userVisitPK,
                            createInventoryTransactionReasonForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastInventoryTransactionReasonName = commandResult.getHasErrors() ? null
                                : result.getInventoryTransactionReasonName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createInventoryTransactionReasonForm = null;
                });

        When("^the user begins deleting an inventory transaction reason$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionReasonForm).isNull();
                    assertThat(persona.deleteInventoryTransactionReasonForm).isNull();
                    assertThat(persona.inventoryTransactionReasonSpec).isNull();

                    persona.deleteInventoryTransactionReasonForm = InventoryUtil.getHome().getDeleteInventoryTransactionReasonForm();
                });

        And("^the user deletes the inventory transaction reason",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionReasonForm = persona.deleteInventoryTransactionReasonForm;

                    assertThat(deleteInventoryTransactionReasonForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteInventoryTransactionReason(persona.userVisitPK,
                            deleteInventoryTransactionReasonForm);

                    persona.deleteInventoryTransactionReasonForm = null;
                });

        When("^the user begins specifying an inventory transaction reason to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createInventoryTransactionReasonForm).isNull();
                    assertThat(persona.deleteInventoryTransactionReasonForm).isNull();
                    assertThat(persona.inventoryTransactionReasonSpec).isNull();

                    persona.inventoryTransactionReasonSpec = InventoryUtil.getHome().getInventoryTransactionReasonUniversalSpec();
                });

        When("^the user begins editing the inventory transaction reason$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionReasonSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionReasonForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionReason(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.inventoryTransactionReasonEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the inventory transaction reason",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.inventoryTransactionReasonSpec;
                    var edit = persona.inventoryTransactionReasonEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditInventoryTransactionReasonForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editInventoryTransactionReason(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.inventoryTransactionReasonSpec = null;
                    persona.inventoryTransactionReasonEdit = null;
                });

        And("^the user sets the inventory transaction reason's inventory transaction reason name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionReasonName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionReasonForm = persona.createInventoryTransactionReasonForm;
                    var deleteInventoryTransactionReasonForm = persona.deleteInventoryTransactionReasonForm;
                    var inventoryTransactionReasonSpec = persona.inventoryTransactionReasonSpec;

                    assertThat(createInventoryTransactionReasonForm != null || deleteInventoryTransactionReasonForm != null
                            || inventoryTransactionReasonSpec != null).isTrue();

                    if(createInventoryTransactionReasonForm != null) {
                        createInventoryTransactionReasonForm.setInventoryTransactionReasonName(inventoryTransactionReasonName);
                    } else if(deleteInventoryTransactionReasonForm != null) {
                        deleteInventoryTransactionReasonForm.setInventoryTransactionReasonName(inventoryTransactionReasonName);
                    } else {
                        inventoryTransactionReasonSpec.setInventoryTransactionReasonName(inventoryTransactionReasonName);
                    }
                });

        And("^the user sets the inventory transaction reason's inventory transaction type name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createForm = persona.createInventoryTransactionReasonForm;
                    var deleteForm = persona.deleteInventoryTransactionReasonForm;
                    var spec = persona.inventoryTransactionReasonSpec;

                    assertThat(createForm != null || deleteForm != null || spec != null).isTrue();

                    if(createForm != null) {
                        createForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else if(deleteForm != null) {
                        deleteForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    } else {
                        spec.setInventoryTransactionTypeName(inventoryTransactionTypeName);
                    }
                });

        And("^the user sets the inventory transaction reason's inventory transaction reason name "
                + "to the last inventory transaction reason added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionReasonForm = persona.createInventoryTransactionReasonForm;
                    var deleteInventoryTransactionReasonForm = persona.deleteInventoryTransactionReasonForm;
                    var inventoryTransactionReasonSpec = persona.inventoryTransactionReasonSpec;

                    assertThat(createInventoryTransactionReasonForm != null || deleteInventoryTransactionReasonForm != null
                            || inventoryTransactionReasonSpec != null).isTrue();

                    if(createInventoryTransactionReasonForm != null) {
                        createInventoryTransactionReasonForm.setInventoryTransactionReasonName(persona.lastInventoryTransactionReasonName);
                    } else if(deleteInventoryTransactionReasonForm != null) {
                        deleteInventoryTransactionReasonForm.setInventoryTransactionReasonName(persona.lastInventoryTransactionReasonName);
                    } else {
                        inventoryTransactionReasonSpec.setInventoryTransactionReasonName(persona.lastInventoryTransactionReasonName);
                    }
                });

        And("^the user sets the inventory transaction reason's entity ref to the last inventory transaction reason added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteInventoryTransactionReasonForm = persona.deleteInventoryTransactionReasonForm;
                    var inventoryTransactionReasonSpec = persona.inventoryTransactionReasonSpec;

                    assertThat(deleteInventoryTransactionReasonForm != null || inventoryTransactionReasonSpec != null).isTrue();
                    assertThat(persona.lastEntityRef).isNotNull();

                    if(deleteInventoryTransactionReasonForm != null) {
                        deleteInventoryTransactionReasonForm.setEntityRef(persona.lastEntityRef);
                    } else {
                        inventoryTransactionReasonSpec.setEntityRef(persona.lastEntityRef);
                    }
                });

        And("^the user sets the inventory transaction reason's new inventory transaction reason name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryTransactionReasonName) -> {
                    var persona = CurrentPersona.persona;
                    var inventoryTransactionReasonEdit = persona.inventoryTransactionReasonEdit;

                    assertThat(inventoryTransactionReasonEdit).isNotNull();

                    inventoryTransactionReasonEdit.setInventoryTransactionReasonName(inventoryTransactionReasonName);
                });

        And("^the user sets the inventory transaction reason to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionReasonForm = persona.createInventoryTransactionReasonForm;
                    var inventoryTransactionReasonEdit = persona.inventoryTransactionReasonEdit;

                    assertThat(createInventoryTransactionReasonForm != null || inventoryTransactionReasonEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createInventoryTransactionReasonForm != null) {
                        createInventoryTransactionReasonForm.setIsDefault(isDefault);
                    } else {
                        inventoryTransactionReasonEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the inventory transaction reason's inventory disposition name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryDispositionName) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionReasonForm = persona.createInventoryTransactionReasonForm;
                    var inventoryTransactionReasonEdit = persona.inventoryTransactionReasonEdit;

                    assertThat(createInventoryTransactionReasonForm != null || inventoryTransactionReasonEdit != null).isTrue();

                    if(createInventoryTransactionReasonForm != null) {
                        createInventoryTransactionReasonForm.setInventoryDispositionName(inventoryDispositionName);
                    } else {
                        inventoryTransactionReasonEdit.setInventoryDispositionName(inventoryDispositionName);
                    }
                });

        And("^the user sets the inventory transaction reason's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionReasonForm = persona.createInventoryTransactionReasonForm;
                    var inventoryTransactionReasonEdit = persona.inventoryTransactionReasonEdit;

                    assertThat(createInventoryTransactionReasonForm != null || inventoryTransactionReasonEdit != null).isTrue();

                    if(createInventoryTransactionReasonForm != null) {
                        createInventoryTransactionReasonForm.setSortOrder(sortOrder);
                    } else {
                        inventoryTransactionReasonEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the inventory transaction reason's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createInventoryTransactionReasonForm = persona.createInventoryTransactionReasonForm;
                    var inventoryTransactionReasonEdit = persona.inventoryTransactionReasonEdit;

                    assertThat(createInventoryTransactionReasonForm != null || inventoryTransactionReasonEdit != null).isTrue();

                    if(createInventoryTransactionReasonForm != null) {
                        createInventoryTransactionReasonForm.setDescription(description);
                    } else {
                        inventoryTransactionReasonEdit.setDescription(description);
                    }
                });

    }

}
