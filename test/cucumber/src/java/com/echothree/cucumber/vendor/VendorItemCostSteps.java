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

package com.echothree.cucumber.vendor;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.EditVendorItemCostResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class VendorItemCostSteps implements En {

    public VendorItemCostSteps() {
        When("^the user begins entering a new vendor item cost$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorItemCostForm).isNull();
                    assertThat(persona.deleteVendorItemCostForm).isNull();
                    assertThat(persona.vendorItemCostSpec).isNull();

                    persona.createVendorItemCostForm = VendorUtil.getHome().getCreateVendorItemCostForm();
                });

        When("^the user adds the new vendor item cost$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemCostForm = persona.createVendorItemCostForm;

                    assertThat(createVendorItemCostForm).isNotNull();

                    LastCommandResult.commandResult = VendorUtil.getHome().createVendorItemCost(persona.userVisitPK, createVendorItemCostForm);

                    persona.createVendorItemCostForm = null;
                });

        When("^the user begins deleting a vendor item cost$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorItemCostForm).isNull();
                    assertThat(persona.deleteVendorItemCostForm).isNull();
                    assertThat(persona.vendorItemCostSpec).isNull();

                    persona.deleteVendorItemCostForm = VendorUtil.getHome().getDeleteVendorItemCostForm();
                });

        When("^the user deletes the vendor item cost$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteVendorItemCostForm = persona.deleteVendorItemCostForm;

                    assertThat(deleteVendorItemCostForm).isNotNull();

                    LastCommandResult.commandResult = VendorUtil.getHome().deleteVendorItemCost(persona.userVisitPK, deleteVendorItemCostForm);

                    persona.deleteVendorItemCostForm = null;
                });

        When("^the user begins specifying a vendor item cost to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorItemCostForm).isNull();
                    assertThat(persona.deleteVendorItemCostForm).isNull();
                    assertThat(persona.vendorItemCostSpec).isNull();

                    persona.vendorItemCostSpec = VendorUtil.getHome().getVendorItemCostSpec();
                });

        When("^the user begins editing the vendor item cost$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorItemCostSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorItemCostForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = VendorUtil.getHome().editVendorItemCost(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditVendorItemCostResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.vendorItemCostEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the vendor item cost$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorItemCostSpec;
                    var edit = persona.vendorItemCostEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorItemCostForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = VendorUtil.getHome().editVendorItemCost(persona.userVisitPK, commandForm);

                    persona.vendorItemCostSpec = null;
                    persona.vendorItemCostEdit = null;
                });

        When("^the user sets the vendor item cost's vendor name to ([a-zA-Z0-9-_]*)$",
                (String vendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemCostForm = persona.createVendorItemCostForm;
                    var deleteVendorItemCostForm = persona.deleteVendorItemCostForm;
                    var vendorItemCostSpec = persona.vendorItemCostSpec;

                    assertThat(createVendorItemCostForm != null || deleteVendorItemCostForm != null || vendorItemCostSpec != null).isTrue();

                    if(createVendorItemCostForm != null) {
                        createVendorItemCostForm.setVendorName(vendorName);
                    } else if(deleteVendorItemCostForm != null) {
                        deleteVendorItemCostForm.setVendorName(vendorName);
                    } else {
                        vendorItemCostSpec.setVendorName(vendorName);
                    }
                });

        When("^the user sets the vendor item cost's vendor name to the last vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemCostForm = persona.createVendorItemCostForm;
                    var deleteVendorItemCostForm = persona.deleteVendorItemCostForm;
                    var vendorItemCostSpec = persona.vendorItemCostSpec;

                    assertThat(createVendorItemCostForm != null || deleteVendorItemCostForm != null || vendorItemCostSpec != null).isTrue();

                    if(createVendorItemCostForm != null) {
                        createVendorItemCostForm.setVendorName(persona.lastVendorName);
                    } else if(deleteVendorItemCostForm != null) {
                        deleteVendorItemCostForm.setVendorName(persona.lastVendorName);
                    } else {
                        vendorItemCostSpec.setVendorName(persona.lastVendorName);
                    }
                });

        When("^the user sets the vendor item cost's vendor item name to ([a-zA-Z0-9-_]*)$",
                (String vendorItemName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemCostForm = persona.createVendorItemCostForm;
                    var deleteVendorItemCostForm = persona.deleteVendorItemCostForm;
                    var vendorItemCostSpec = persona.vendorItemCostSpec;

                    assertThat(createVendorItemCostForm != null || deleteVendorItemCostForm != null || vendorItemCostSpec != null).isTrue();

                    if(createVendorItemCostForm != null) {
                        createVendorItemCostForm.setVendorItemName(vendorItemName);
                    } else if(deleteVendorItemCostForm != null) {
                        deleteVendorItemCostForm.setVendorItemName(vendorItemName);
                    } else {
                        vendorItemCostSpec.setVendorItemName(vendorItemName);
                    }
                });

        When("^the user sets the vendor item cost's inventory condition name to ([a-zA-Z0-9-_]*)$",
                (String inventoryConditionName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemCostForm = persona.createVendorItemCostForm;
                    var deleteVendorItemCostForm = persona.deleteVendorItemCostForm;
                    var vendorItemCostSpec = persona.vendorItemCostSpec;

                    assertThat(createVendorItemCostForm != null || deleteVendorItemCostForm != null || vendorItemCostSpec != null).isTrue();

                    if(createVendorItemCostForm != null) {
                        createVendorItemCostForm.setInventoryConditionName(inventoryConditionName);
                    } else if(deleteVendorItemCostForm != null) {
                        deleteVendorItemCostForm.setInventoryConditionName(inventoryConditionName);
                    } else {
                        vendorItemCostSpec.setInventoryConditionName(inventoryConditionName);
                    }
                });

        When("^the user sets the vendor item cost's unit of measure type name to ([a-zA-Z0-9-_]*)$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemCostForm = persona.createVendorItemCostForm;
                    var deleteVendorItemCostForm = persona.deleteVendorItemCostForm;
                    var vendorItemCostSpec = persona.vendorItemCostSpec;

                    assertThat(createVendorItemCostForm != null || deleteVendorItemCostForm != null || vendorItemCostSpec != null).isTrue();

                    if(createVendorItemCostForm != null) {
                        createVendorItemCostForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else if(deleteVendorItemCostForm != null) {
                        deleteVendorItemCostForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        vendorItemCostSpec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the vendor item cost's unit cost to ([^\"]*)$",
                (String unitCost) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemCostForm = persona.createVendorItemCostForm;
                    var vendorItemCostEdit = persona.vendorItemCostEdit;

                    assertThat(createVendorItemCostForm != null || vendorItemCostEdit != null).isTrue();

                    if(createVendorItemCostForm != null) {
                        createVendorItemCostForm.setUnitCost(unitCost);
                    } else {
                        vendorItemCostEdit.setUnitCost(unitCost);
                    }
                });
    }

}
