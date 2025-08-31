// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.warehouse.common.result.CreateLocationTypeResult;
import com.echothree.control.user.warehouse.common.result.EditLocationTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationTypeSteps implements En {

    public LocationTypeSteps() {
        When("^the user begins entering a new location type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationTypeForm).isNull();
                    assertThat(persona.deleteLocationTypeForm).isNull();
                    assertThat(persona.locationTypeSpec).isNull();

                    persona.createLocationTypeForm = WarehouseUtil.getHome().getCreateLocationTypeForm();
                });

        And("^the user adds the new location type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;

                    assertThat(createLocationTypeForm).isNotNull();

                    var commandResult = WarehouseUtil.getHome().createLocationType(persona.userVisitPK, createLocationTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateLocationTypeResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLocationTypeName = commandResult.getHasErrors() ? null : result.getLocationTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLocationTypeForm = null;
                });

        When("^the user begins deleting a location type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationTypeForm).isNull();
                    assertThat(persona.deleteLocationTypeForm).isNull();
                    assertThat(persona.locationTypeSpec).isNull();

                    persona.deleteLocationTypeForm = WarehouseUtil.getHome().getDeleteLocationTypeForm();
                });

        And("^the user deletes the location type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLocationTypeForm = persona.deleteLocationTypeForm;

                    assertThat(deleteLocationTypeForm).isNotNull();

                    LastCommandResult.commandResult = WarehouseUtil.getHome().deleteLocationType(persona.userVisitPK, deleteLocationTypeForm);

                    persona.deleteLocationTypeForm = null;
                });

        When("^the user begins specifying a location type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationTypeForm).isNull();
                    assertThat(persona.deleteLocationTypeForm).isNull();
                    assertThat(persona.locationTypeSpec).isNull();

                    persona.locationTypeSpec = WarehouseUtil.getHome().getLocationTypeSpec();
                });

        When("^the user begins editing the location type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.locationTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditLocationTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WarehouseUtil.getHome().editLocationType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditLocationTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.locationTypeEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the location type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.locationTypeSpec;
                    var edit = persona.locationTypeEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditLocationTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = WarehouseUtil.getHome().editLocationType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.locationTypeSpec = null;
                    persona.locationTypeEdit = null;
                });

        And("^the user sets the location type's warehouse name to \"([a-zA-Z0-9-_]*)\"$",
                (String warehouseName) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;
                    var deleteLocationTypeForm = persona.deleteLocationTypeForm;
                    var locationTypeSpec = persona.locationTypeSpec;

                    assertThat(createLocationTypeForm != null || deleteLocationTypeForm != null
                            || locationTypeSpec != null).isTrue();

                    if(createLocationTypeForm != null) {
                        createLocationTypeForm.setWarehouseName(warehouseName);
                    } else if(deleteLocationTypeForm != null) {
                        deleteLocationTypeForm.setWarehouseName(warehouseName);
                    } else {
                        locationTypeSpec.setWarehouseName(warehouseName);
                    }
                });

        And("^the user sets the location type's warehouse name to the last warehouse added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;
                    var deleteLocationTypeForm = persona.deleteLocationTypeForm;
                    var locationTypeSpec = persona.locationTypeSpec;

                    assertThat(createLocationTypeForm != null || deleteLocationTypeForm != null
                            || locationTypeSpec != null).isTrue();

                    if(createLocationTypeForm != null) {
                        createLocationTypeForm.setWarehouseName(persona.lastWarehouseName);
                    } else if(deleteLocationTypeForm != null) {
                        deleteLocationTypeForm.setWarehouseName(persona.lastWarehouseName);
                    } else {
                        locationTypeSpec.setWarehouseName(persona.lastWarehouseName);
                    }
                });

        And("^the user sets the location type's location type name to \"([a-zA-Z0-9-_]*)\"$",
                (String locationTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;
                    var deleteLocationTypeForm = persona.deleteLocationTypeForm;
                    var locationTypeSpec = persona.locationTypeSpec;

                    assertThat(createLocationTypeForm != null || deleteLocationTypeForm != null
                            || locationTypeSpec != null).isTrue();

                    if(createLocationTypeForm != null) {
                        createLocationTypeForm.setLocationTypeName(locationTypeName);
                    } else if(deleteLocationTypeForm != null) {
                        deleteLocationTypeForm.setLocationTypeName(locationTypeName);
                    } else {
                        locationTypeSpec.setLocationTypeName(locationTypeName);
                    }
                });

        And("^the user sets the location type's location type name to the last location type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;
                    var deleteLocationTypeForm = persona.deleteLocationTypeForm;
                    var locationTypeSpec = persona.locationTypeSpec;

                    assertThat(createLocationTypeForm != null || deleteLocationTypeForm != null
                            || locationTypeSpec != null).isTrue();

                    if(createLocationTypeForm != null) {
                        createLocationTypeForm.setLocationTypeName(persona.lastLocationTypeName);
                    } else if(deleteLocationTypeForm != null) {
                        deleteLocationTypeForm.setLocationTypeName(persona.lastLocationTypeName);
                    } else {
                        locationTypeSpec.setLocationTypeName(persona.lastLocationTypeName);
                    }
                });

        And("^the user sets the location type's new location type name to \"([a-zA-Z0-9-_]*)\"$",
                (String locationTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var locationTypeEdit = persona.locationTypeEdit;

                    assertThat(locationTypeEdit).isNotNull();

                    locationTypeEdit.setLocationTypeName(locationTypeName);
                });

        And("^the user sets the location type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;
                    var locationTypeEdit = persona.locationTypeEdit;

                    assertThat(createLocationTypeForm != null || locationTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createLocationTypeForm != null) {
                        createLocationTypeForm.setIsDefault(isDefault);
                    } else {
                        locationTypeEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the location type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;
                    var locationTypeEdit = persona.locationTypeEdit;

                    assertThat(createLocationTypeForm != null || locationTypeEdit != null).isTrue();

                    if(createLocationTypeForm != null) {
                        createLocationTypeForm.setSortOrder(sortOrder);
                    } else {
                        locationTypeEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the location type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationTypeForm = persona.createLocationTypeForm;
                    var locationTypeEdit = persona.locationTypeEdit;

                    assertThat(createLocationTypeForm != null || locationTypeEdit != null).isTrue();

                    if(createLocationTypeForm != null) {
                        createLocationTypeForm.setDescription(description);
                    } else {
                        locationTypeEdit.setDescription(description);
                    }
                });

    }

}
