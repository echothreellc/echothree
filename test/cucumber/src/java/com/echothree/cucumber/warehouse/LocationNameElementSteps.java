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
import com.echothree.control.user.warehouse.common.result.CreateLocationNameElementResult;
import com.echothree.control.user.warehouse.common.result.EditLocationNameElementResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationNameElementSteps implements En {

    public LocationNameElementSteps() {
        When("^the user begins entering a new location name element$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationNameElementForm).isNull();
                    assertThat(persona.deleteLocationNameElementForm).isNull();
                    assertThat(persona.locationNameElementSpec).isNull();

                    persona.createLocationNameElementForm = WarehouseUtil.getHome().getCreateLocationNameElementForm();
                });

        And("^the user adds the new location name element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;

                    assertThat(createLocationNameElementForm).isNotNull();

                    var commandResult = WarehouseUtil.getHome().createLocationNameElement(persona.userVisitPK, createLocationNameElementForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateLocationNameElementResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLocationNameElementName = commandResult.getHasErrors() ? null : result.getLocationNameElementName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLocationNameElementForm = null;
                });

        When("^the user begins deleting a location name element$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationNameElementForm).isNull();
                    assertThat(persona.deleteLocationNameElementForm).isNull();
                    assertThat(persona.locationNameElementSpec).isNull();

                    persona.deleteLocationNameElementForm = WarehouseUtil.getHome().getDeleteLocationNameElementForm();
                });

        And("^the user deletes the location name element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLocationNameElementForm = persona.deleteLocationNameElementForm;

                    assertThat(deleteLocationNameElementForm).isNotNull();

                    LastCommandResult.commandResult = WarehouseUtil.getHome().deleteLocationNameElement(persona.userVisitPK, deleteLocationNameElementForm);

                    persona.deleteLocationNameElementForm = null;
                });

        When("^the user begins specifying a location name element to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationNameElementForm).isNull();
                    assertThat(persona.deleteLocationNameElementForm).isNull();
                    assertThat(persona.locationNameElementSpec).isNull();

                    persona.locationNameElementSpec = WarehouseUtil.getHome().getLocationNameElementSpec();
                });

        When("^the user begins editing the location name element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.locationNameElementSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditLocationNameElementForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WarehouseUtil.getHome().editLocationNameElement(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditLocationNameElementResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.locationNameElementEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the location name element$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.locationNameElementSpec;
                    var edit = persona.locationNameElementEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditLocationNameElementForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = WarehouseUtil.getHome().editLocationNameElement(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.locationNameElementSpec = null;
                    persona.locationNameElementEdit = null;
                });

        And("^the user sets the location name element's warehouse name to \"([a-zA-Z0-9-_]*)\"$",
                (String warehouseName) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var deleteLocationNameElementForm = persona.deleteLocationNameElementForm;
                    var locationNameElementSpec = persona.locationNameElementSpec;

                    assertThat(createLocationNameElementForm != null || deleteLocationNameElementForm != null
                            || locationNameElementSpec != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setWarehouseName(warehouseName);
                    } else if(deleteLocationNameElementForm != null) {
                        deleteLocationNameElementForm.setWarehouseName(warehouseName);
                    } else {
                        locationNameElementSpec.setWarehouseName(warehouseName);
                    }
                });

        And("^the user sets the location name element's warehouse name to the last warehouse added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var deleteLocationNameElementForm = persona.deleteLocationNameElementForm;
                    var locationNameElementSpec = persona.locationNameElementSpec;

                    assertThat(createLocationNameElementForm != null || deleteLocationNameElementForm != null
                            || locationNameElementSpec != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setWarehouseName(persona.lastWarehouseName);
                    } else if(deleteLocationNameElementForm != null) {
                        deleteLocationNameElementForm.setWarehouseName(persona.lastWarehouseName);
                    } else {
                        locationNameElementSpec.setWarehouseName(persona.lastWarehouseName);
                    }
                });

        And("^the user sets the location name element's location type name to \"([a-zA-Z0-9-_]*)\"$",
                (String locationTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var deleteLocationNameElementForm = persona.deleteLocationNameElementForm;
                    var locationNameElementSpec = persona.locationNameElementSpec;

                    assertThat(createLocationNameElementForm != null || deleteLocationNameElementForm != null
                            || locationNameElementSpec != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setLocationTypeName(locationTypeName);
                    } else if(deleteLocationNameElementForm != null) {
                        deleteLocationNameElementForm.setLocationTypeName(locationTypeName);
                    } else {
                        locationNameElementSpec.setLocationTypeName(locationTypeName);
                    }
                });

        And("^the user sets the location name element's location type name to the last location type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var deleteLocationNameElementForm = persona.deleteLocationNameElementForm;
                    var locationNameElementSpec = persona.locationNameElementSpec;

                    assertThat(createLocationNameElementForm != null || deleteLocationNameElementForm != null
                            || locationNameElementSpec != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setLocationTypeName(persona.lastLocationTypeName);
                    } else if(deleteLocationNameElementForm != null) {
                        deleteLocationNameElementForm.setLocationTypeName(persona.lastLocationTypeName);
                    } else {
                        locationNameElementSpec.setLocationTypeName(persona.lastLocationTypeName);
                    }
                });

        And("^the user sets the location name element's location name element name to \"([a-zA-Z0-9-_]*)\"$",
                (String locationNameElementName) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var deleteLocationNameElementForm = persona.deleteLocationNameElementForm;
                    var locationNameElementSpec = persona.locationNameElementSpec;

                    assertThat(createLocationNameElementForm != null || deleteLocationNameElementForm != null
                            || locationNameElementSpec != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setLocationNameElementName(locationNameElementName);
                    } else if(deleteLocationNameElementForm != null) {
                        deleteLocationNameElementForm.setLocationNameElementName(locationNameElementName);
                    } else {
                        locationNameElementSpec.setLocationNameElementName(locationNameElementName);
                    }
                });

        And("^the user sets the location name element's location name element name to the last location name element added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var deleteLocationNameElementForm = persona.deleteLocationNameElementForm;
                    var locationNameElementSpec = persona.locationNameElementSpec;

                    assertThat(createLocationNameElementForm != null || deleteLocationNameElementForm != null
                            || locationNameElementSpec != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setLocationNameElementName(persona.lastLocationNameElementName);
                    } else if(deleteLocationNameElementForm != null) {
                        deleteLocationNameElementForm.setLocationNameElementName(persona.lastLocationNameElementName);
                    } else {
                        locationNameElementSpec.setLocationNameElementName(persona.lastLocationNameElementName);
                    }
                });

        And("^the user sets the location name element's new location name element name to \"([a-zA-Z0-9-_]*)\"$",
                (String locationNameElementName) -> {
                    var persona = CurrentPersona.persona;
                    var locationNameElementEdit = persona.locationNameElementEdit;

                    assertThat(locationNameElementEdit).isNotNull();

                    locationNameElementEdit.setLocationNameElementName(locationNameElementName);
                });

        And("^the user sets the location name element's offset to \"([^\"]*)\"$",
                (String offset) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var locationNameElementEdit = persona.locationNameElementEdit;

                    assertThat(createLocationNameElementForm != null || locationNameElementEdit != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setOffset(offset);
                    } else {
                        locationNameElementEdit.setOffset(offset);
                    }
                });

        And("^the user sets the location name element's length to \"([^\"]*)\"$",
                (String length) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var locationNameElementEdit = persona.locationNameElementEdit;

                    assertThat(createLocationNameElementForm != null || locationNameElementEdit != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setLength(length);
                    } else {
                        locationNameElementEdit.setLength(length);
                    }
                });

        And("^the user sets the location name element's validation pattern to \"([^\"]*)\"$",
                (String validationPattern) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var locationNameElementEdit = persona.locationNameElementEdit;

                    assertThat(createLocationNameElementForm != null || locationNameElementEdit != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setValidationPattern(validationPattern);
                    } else {
                        locationNameElementEdit.setValidationPattern(validationPattern);
                    }
                });

        And("^the user sets the location name element's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationNameElementForm = persona.createLocationNameElementForm;
                    var locationNameElementEdit = persona.locationNameElementEdit;

                    assertThat(createLocationNameElementForm != null || locationNameElementEdit != null).isTrue();

                    if(createLocationNameElementForm != null) {
                        createLocationNameElementForm.setDescription(description);
                    } else {
                        locationNameElementEdit.setDescription(description);
                    }
                });
    }

}
