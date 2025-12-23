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

package com.echothree.cucumber.warehouse;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.CreateWarehouseTypeResult;
import com.echothree.control.user.warehouse.common.result.EditWarehouseTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseTypeSteps implements En {

    public WarehouseTypeSteps() {
        When("^the user begins entering a new warehouse type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWarehouseTypeForm).isNull();
                    assertThat(persona.deleteWarehouseTypeForm).isNull();
                    assertThat(persona.warehouseTypeSpec).isNull();

                    persona.createWarehouseTypeForm = WarehouseUtil.getHome().getCreateWarehouseTypeForm();
                });

        When("^the user adds the new warehouse type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseTypeForm = persona.createWarehouseTypeForm;

                    assertThat(createWarehouseTypeForm).isNotNull();

                    var commandResult = WarehouseUtil.getHome().createWarehouseType(persona.userVisitPK, createWarehouseTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateWarehouseTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastWarehouseTypeName = commandResult.getHasErrors() ? null : result.getWarehouseTypeName();
                    persona.createWarehouseTypeForm = null;
                });

        When("^the user begins deleting a warehouse type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWarehouseTypeForm).isNull();
                    assertThat(persona.deleteWarehouseTypeForm).isNull();
                    assertThat(persona.warehouseTypeSpec).isNull();

                    persona.deleteWarehouseTypeForm = WarehouseUtil.getHome().getDeleteWarehouseTypeForm();
                });

        When("^the user deletes the warehouse type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteWarehouseTypeForm = persona.deleteWarehouseTypeForm;

                    assertThat(deleteWarehouseTypeForm).isNotNull();

                    LastCommandResult.commandResult = WarehouseUtil.getHome().deleteWarehouseType(persona.userVisitPK, deleteWarehouseTypeForm);

                    persona.deleteWarehouseTypeForm = null;
                });

        When("^the user begins specifying a warehouse type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWarehouseTypeForm).isNull();
                    assertThat(persona.deleteWarehouseTypeForm).isNull();
                    assertThat(persona.warehouseTypeSpec).isNull();

                    persona.warehouseTypeSpec = WarehouseUtil.getHome().getWarehouseTypeSpec();
                });

        When("^the user begins editing the warehouse type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.warehouseTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditWarehouseTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WarehouseUtil.getHome().editWarehouseType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditWarehouseTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.warehouseTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the warehouse type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.warehouseTypeSpec;
                    var edit = persona.warehouseTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditWarehouseTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = WarehouseUtil.getHome().editWarehouseType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.warehouseTypeSpec = null;
                    persona.warehouseTypeEdit = null;
                });
        
        When("^the user sets the warehouse type's name to ([a-zA-Z0-9-_]*)$",
                (String warehouseTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseTypeForm = persona.createWarehouseTypeForm;
                    var deleteWarehouseTypeForm = persona.deleteWarehouseTypeForm;
                    var warehouseTypeSpec = persona.warehouseTypeSpec;

                    assertThat(createWarehouseTypeForm != null || deleteWarehouseTypeForm != null || warehouseTypeSpec != null).isTrue();

                    if(createWarehouseTypeForm != null) {
                        createWarehouseTypeForm.setWarehouseTypeName(warehouseTypeName);
                    } else if(deleteWarehouseTypeForm != null) {
                        deleteWarehouseTypeForm.setWarehouseTypeName(warehouseTypeName);
                    } else {
                        warehouseTypeSpec.setWarehouseTypeName(warehouseTypeName);
                    }
                });

        When("^the user sets the warehouse type's name to the last warehouse type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastWarehouseTypeName = persona.lastWarehouseTypeName;
                    var deleteWarehouseTypeForm = persona.deleteWarehouseTypeForm;
                    var warehouseTypeSpec = persona.warehouseTypeSpec;

                    assertThat(deleteWarehouseTypeForm != null || warehouseTypeSpec != null).isTrue();

                    if(deleteWarehouseTypeForm != null) {
                        deleteWarehouseTypeForm.setWarehouseTypeName(lastWarehouseTypeName);
                    } else {
                        warehouseTypeSpec.setWarehouseTypeName(lastWarehouseTypeName);
                    }
                });

        When("^the user sets the warehouse type's priority to \"([^\"]*)\"$",
                (String priority) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseTypeForm = persona.createWarehouseTypeForm;
                    var warehouseTypeEdit = persona.warehouseTypeEdit;

                    assertThat(createWarehouseTypeForm != null || warehouseTypeEdit != null).isTrue();

                    if(createWarehouseTypeForm != null) {
                        createWarehouseTypeForm.setPriority(priority);
                    } else {
                        warehouseTypeEdit.setPriority(priority);
                    }
                });

        When("^the user sets the warehouse type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseTypeForm = persona.createWarehouseTypeForm;
                    var warehouseTypeEdit = persona.warehouseTypeEdit;

                    assertThat(createWarehouseTypeForm != null || warehouseTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createWarehouseTypeForm != null) {
                        createWarehouseTypeForm.setIsDefault(isDefault);
                    } else {
                        warehouseTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the warehouse type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseTypeForm = persona.createWarehouseTypeForm;
                    var warehouseTypeEdit = persona.warehouseTypeEdit;

                    assertThat(createWarehouseTypeForm != null || warehouseTypeEdit != null).isTrue();

                    if(createWarehouseTypeForm != null) {
                        createWarehouseTypeForm.setSortOrder(sortOrder);
                    } else {
                        warehouseTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the warehouse type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseTypeForm = persona.createWarehouseTypeForm;
                    var warehouseTypeEdit = persona.warehouseTypeEdit;

                    assertThat(createWarehouseTypeForm != null || warehouseTypeEdit != null).isTrue();

                    if(createWarehouseTypeForm != null) {
                        createWarehouseTypeForm.setDescription(description);
                    } else {
                        warehouseTypeEdit.setDescription(description);
                    }
                });
    }

}
