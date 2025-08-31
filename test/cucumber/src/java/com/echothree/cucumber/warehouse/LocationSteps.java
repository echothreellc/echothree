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
import com.echothree.control.user.warehouse.common.result.CreateLocationResult;
import com.echothree.control.user.warehouse.common.result.EditLocationResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationSteps implements En {

    public LocationSteps() {
        When("^the user begins entering a new location",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationForm).isNull();
                    assertThat(persona.setLocationStatusForm).isNull();
                    assertThat(persona.deleteLocationForm).isNull();
                    assertThat(persona.locationSpec).isNull();

                    persona.createLocationForm = WarehouseUtil.getHome().getCreateLocationForm();
                });

        And("^the user adds the new location",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;

                    assertThat(createLocationForm).isNotNull();

                    var commandResult = WarehouseUtil.getHome().createLocation(persona.userVisitPK, createLocationForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateLocationResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLocationName = commandResult.getHasErrors() ? null : result.getLocationName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLocationForm = null;
                });

        When("^the user begins setting the status of a location",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationForm).isNull();
                    assertThat(persona.setLocationStatusForm).isNull();
                    assertThat(persona.deleteLocationForm).isNull();
                    assertThat(persona.locationSpec).isNull();

                    persona.setLocationStatusForm = WarehouseUtil.getHome().getSetLocationStatusForm();
                });

        And("^the user sets the status of the location",
                () -> {
                    var persona = CurrentPersona.persona;
                    var setLocationStatusForm = persona.setLocationStatusForm;

                    assertThat(setLocationStatusForm).isNotNull();

                    LastCommandResult.commandResult = WarehouseUtil.getHome().setLocationStatus(persona.userVisitPK, setLocationStatusForm);

                    persona.setLocationStatusForm = null;
                });

        When("^the user begins deleting a location",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationForm).isNull();
                    assertThat(persona.setLocationStatusForm).isNull();
                    assertThat(persona.deleteLocationForm).isNull();
                    assertThat(persona.locationSpec).isNull();

                    persona.deleteLocationForm = WarehouseUtil.getHome().getDeleteLocationForm();
                });

        And("^the user deletes the location",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLocationForm = persona.deleteLocationForm;

                    assertThat(deleteLocationForm).isNotNull();

                    LastCommandResult.commandResult = WarehouseUtil.getHome().deleteLocation(persona.userVisitPK, deleteLocationForm);

                    persona.deleteLocationForm = null;
                });

        When("^the user begins specifying a location to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLocationForm).isNull();
                    assertThat(persona.setLocationStatusForm).isNull();
                    assertThat(persona.deleteLocationForm).isNull();
                    assertThat(persona.locationSpec).isNull();

                    persona.locationSpec = WarehouseUtil.getHome().getLocationSpec();
                });

        When("^the user begins editing the location",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.locationSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditLocationForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WarehouseUtil.getHome().editLocation(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditLocationResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.locationEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the location",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.locationSpec;
                    var edit = persona.locationEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditLocationForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = WarehouseUtil.getHome().editLocation(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.locationSpec = null;
                    persona.locationEdit = null;
                });

        And("^the user sets the location's warehouse name to \"([a-zA-Z0-9-_]*)\"$",
                (String warehouseName) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var setLocationStatusForm = persona.setLocationStatusForm;
                    var deleteLocationForm = persona.deleteLocationForm;
                    var locationSpec = persona.locationSpec;

                    assertThat(createLocationForm != null || setLocationStatusForm != null
                            || deleteLocationForm != null || locationSpec != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setWarehouseName(warehouseName);
                    } else if(setLocationStatusForm != null) {
                        setLocationStatusForm.setWarehouseName(warehouseName);
                    } else if(deleteLocationForm != null) {
                        deleteLocationForm.setWarehouseName(warehouseName);
                    } else {
                        locationSpec.setWarehouseName(warehouseName);
                    }
                });

        And("^the user sets the location's warehouse name to the last warehouse added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var setLocationStatusForm = persona.setLocationStatusForm;
                    var deleteLocationForm = persona.deleteLocationForm;
                    var locationSpec = persona.locationSpec;

                    assertThat(createLocationForm != null || setLocationStatusForm != null
                            || deleteLocationForm != null || locationSpec != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setWarehouseName(persona.lastWarehouseName);
                    } else if(setLocationStatusForm != null) {
                        setLocationStatusForm.setWarehouseName(persona.lastWarehouseName);
                    } else if(deleteLocationForm != null) {
                        deleteLocationForm.setWarehouseName(persona.lastWarehouseName);
                    } else {
                        locationSpec.setWarehouseName(persona.lastWarehouseName);
                    }
                });

        And("^the user sets the location's location name to \"([a-zA-Z0-9-_]*)\"$",
                (String locationName) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var setLocationStatusForm = persona.setLocationStatusForm;
                    var deleteLocationForm = persona.deleteLocationForm;
                    var locationSpec = persona.locationSpec;

                    assertThat(createLocationForm != null || setLocationStatusForm != null
                            || deleteLocationForm != null || locationSpec != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setLocationName(locationName);
                    } else if(setLocationStatusForm != null) {
                        setLocationStatusForm.setLocationName(locationName);
                    } else if(deleteLocationForm != null) {
                        deleteLocationForm.setLocationName(locationName);
                    } else {
                        locationSpec.setLocationName(locationName);
                    }
                });

        And("^the user sets the location's location name to the last location added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var setLocationStatusForm = persona.setLocationStatusForm;
                    var deleteLocationForm = persona.deleteLocationForm;
                    var locationSpec = persona.locationSpec;

                    assertThat(createLocationForm != null || setLocationStatusForm != null
                            || deleteLocationForm != null || locationSpec != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setLocationName(persona.lastLocationName);
                    } else if(setLocationStatusForm != null) {
                        setLocationStatusForm.setLocationName(persona.lastLocationName);
                    } else if(deleteLocationForm != null) {
                        deleteLocationForm.setLocationName(persona.lastLocationName);
                    } else {
                        locationSpec.setLocationName(persona.lastLocationName);
                    }
                });

        And("^the user sets the location's new location name to \"([a-zA-Z0-9-_]*)\"$",
                (String locationName) -> {
                    var persona = CurrentPersona.persona;
                    var locationEdit = persona.locationEdit;

                    assertThat(locationEdit).isNotNull();

                    locationEdit.setLocationName(locationName);
                });

        And("^the user sets the location's status to \"([a-zA-Z0-9-_]*)\"$",
                (String locationStatusChoice) -> {
                    var persona = CurrentPersona.persona;
                    var setLocationStatusForm = persona.setLocationStatusForm;

                    assertThat(setLocationStatusForm).isNotNull();

                    setLocationStatusForm.setLocationStatusChoice(locationStatusChoice);
                });

        And("^the user sets the location's location type name to \"([^\"]*)\"$",
                (String locationType) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var locationEdit = persona.locationEdit;

                    assertThat(createLocationForm != null || locationEdit != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setLocationTypeName(locationType);
                    } else {
                        locationEdit.setLocationTypeName(locationType);
                    }
                });

        And("^the user sets the location's location use type name to \"([^\"]*)\"$",
                (String locationUseType) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var locationEdit = persona.locationEdit;

                    assertThat(createLocationForm != null || locationEdit != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setLocationUseTypeName(locationUseType);
                    } else {
                        locationEdit.setLocationUseTypeName(locationUseType);
                    }
                });

        And("^the user sets the location's velocity to \"([^\"]*)\"$",
                (String velocity) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var locationEdit = persona.locationEdit;

                    assertThat(createLocationForm != null || locationEdit != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setVelocity(velocity);
                    } else {
                        locationEdit.setVelocity(velocity);
                    }
                });

        And("^the user sets the location's inventory location group name to \"([^\"]*)\"$",
                (String velocity) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var locationEdit = persona.locationEdit;

                    assertThat(createLocationForm != null || locationEdit != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setInventoryLocationGroupName(velocity);
                    } else {
                        locationEdit.setInventoryLocationGroupName(velocity);
                    }
                });

        And("^the user sets the location's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLocationForm = persona.createLocationForm;
                    var locationEdit = persona.locationEdit;

                    assertThat(createLocationForm != null || locationEdit != null).isTrue();

                    if(createLocationForm != null) {
                        createLocationForm.setDescription(description);
                    } else {
                        locationEdit.setDescription(description);
                    }
                });

    }

}
