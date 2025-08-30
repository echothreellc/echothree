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
import com.echothree.control.user.warehouse.common.result.CreateWarehouseResult;
import com.echothree.control.user.warehouse.common.result.EditWarehouseResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseSteps
        implements En {

    public WarehouseSteps() {
        When("^the user begins entering a new warehouse$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWarehouseForm).isNull();
                    assertThat(persona.deleteWarehouseForm).isNull();
                    assertThat(persona.warehouseUniversalSpec).isNull();

                    persona.createWarehouseForm = WarehouseUtil.getHome().getCreateWarehouseForm();
                });

        And("^the user adds the new warehouse$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWarehouseForm).isNotNull();

                    var partyUtil = WarehouseUtil.getHome();
                    var createWarehouseForm = partyUtil.getCreateWarehouseForm();

                    createWarehouseForm.set(persona.createWarehouseForm.get());

                    var commandResult = partyUtil.createWarehouse(persona.userVisitPK, createWarehouseForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateWarehouseResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastWarehouseName = commandResult.getHasErrors() ? null : result.getWarehouseName();
                        persona.lastPartyName = commandResult.getHasErrors() ? null : result.getPartyName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createWarehouseForm = null;
                });

        When("^the user begins deleting a warehouse$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWarehouseForm).isNull();
                    assertThat(persona.deleteWarehouseForm).isNull();
                    assertThat(persona.warehouseUniversalSpec).isNull();

                    persona.deleteWarehouseForm = WarehouseUtil.getHome().getDeleteWarehouseForm();
                });

        And("^the user deletes the warehouse$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteWarehouseForm = persona.deleteWarehouseForm;

                    assertThat(deleteWarehouseForm).isNotNull();

                    LastCommandResult.commandResult = WarehouseUtil.getHome().deleteWarehouse(persona.userVisitPK, deleteWarehouseForm);

                    persona.deleteWarehouseForm = null;
                });

        When("^the user begins specifying a warehouse to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createWarehouseForm).isNull();
                    assertThat(persona.deleteWarehouseForm).isNull();
                    assertThat(persona.warehouseUniversalSpec).isNull();

                    persona.warehouseUniversalSpec = WarehouseUtil.getHome().getWarehouseUniversalSpec();
                });

        When("^the user begins editing the warehouse$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.warehouseUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditWarehouseForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WarehouseUtil.getHome().editWarehouse(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditWarehouseResult)executionResult.getResult();

                        persona.warehouseEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the warehouse$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.warehouseUniversalSpec;
                    var edit = persona.warehouseEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = WarehouseUtil.getHome().getEditWarehouseForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = WarehouseUtil.getHome().editWarehouse(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.warehouseUniversalSpec = null;
                    persona.warehouseEdit = null;
                });

        And("^the user sets the warehouse's warehouse name to \"([a-zA-Z0-9-_]*)\"$",
                (String warehouseName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var deleteWarehouseForm = persona.deleteWarehouseForm;
                    var warehouseSpec = persona.warehouseUniversalSpec;

                    assertThat(createWarehouseForm != null || deleteWarehouseForm != null || warehouseSpec != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setWarehouseName(warehouseName);
                    } else if(deleteWarehouseForm != null) {
                        deleteWarehouseForm.setWarehouseName(warehouseName);
                    } else {
                        warehouseSpec.setWarehouseName(warehouseName);
                    }
                });

        And("^the user sets the warehouse's warehouse name to the last warehouse added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var deleteWarehouseForm = persona.deleteWarehouseForm;
                    var warehouseSpec = persona.warehouseUniversalSpec;

                    assertThat(createWarehouseForm != null || deleteWarehouseForm != null || warehouseSpec != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setWarehouseName(persona.lastWarehouseName);
                    } else if(deleteWarehouseForm != null) {
                        deleteWarehouseForm.setWarehouseName(persona.lastWarehouseName);
                    } else {
                        warehouseSpec.setWarehouseName(persona.lastWarehouseName);
                    }
                });

        And("^the user sets the warehouse's new warehouse name to \"([a-zA-Z0-9-_]*)\"$",
                (String warehouseName) -> {
                    var persona = CurrentPersona.persona;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(warehouseEdit != null).isTrue();

                    warehouseEdit.setWarehouseName(warehouseName);
                });

        And("^the user sets the warehouse's warehouse type name to \"([^\"]*)\"$",
                (String warehouseType) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setWarehouseTypeName(warehouseType);
                    } else {
                        warehouseEdit.setWarehouseTypeName(warehouseType);
                    }
                });

        And("^the user sets the warehouse to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createWarehouseForm != null) {
                        createWarehouseForm.setIsDefault(isDefault);
                    } else {
                        warehouseEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the warehouse's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setSortOrder(sortOrder);
                    } else {
                        warehouseEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the warehouse's inventory move printer group name to \"([a-zA-Z0-9-_]*)\"$",
                (String inventoryMovePrinterGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setInventoryMovePrinterGroupName(inventoryMovePrinterGroupName);
                    } else {
                        warehouseEdit.setInventoryMovePrinterGroupName(inventoryMovePrinterGroupName);
                    }
                });

        And("^the user sets the warehouse's picklist printer group mame to \"([a-zA-Z0-9-_]*)\"$",
                (String picklistPrinterGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setPicklistPrinterGroupName(picklistPrinterGroupName);
                    } else {
                        warehouseEdit.setPicklistPrinterGroupName(picklistPrinterGroupName);
                    }
                });

        And("^the user sets the warehouse's packing list printer group name to \"([a-zA-Z0-9-_]*)\"$",
                (String packingListPrinterGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setPackingListPrinterGroupName(packingListPrinterGroupName);
                    } else {
                        warehouseEdit.setInventoryMovePrinterGroupName(packingListPrinterGroupName);
                    }
                });

        And("^the user sets the warehouse's shipping manifest printer group name to \"([a-zA-Z0-9-_]*)\"$",
                (String shippingManifestPrinterGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setShippingManifestPrinterGroupName(shippingManifestPrinterGroupName);
                    } else {
                        warehouseEdit.setShippingManifestPrinterGroupName(shippingManifestPrinterGroupName);
                    }
                });

        And("^the user sets the warehouse's name to \"([^\"]*)\"$",
                (String name) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setName(name);
                    } else {
                        warehouseEdit.setName(name);
                    }
                });

        And("^the user sets the warehouse's preferred language to \"([^\"]*)\"$",
                (String preferredLanguageIsoName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setPreferredLanguageIsoName(preferredLanguageIsoName);
                    } else {
                        warehouseEdit.setPreferredLanguageIsoName(preferredLanguageIsoName);
                    }
                });

        And("^the user sets the warehouse's preferred currency to \"([^\"]*)\"$",
                (String preferredCurrencyIsoName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
                    } else {
                        warehouseEdit.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
                    }
                });

        And("^the user sets the warehouse's preferred time zone to \"([^\"]*)\"$",
                (String preferredJavaTimeZoneName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
                    } else {
                        warehouseEdit.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
                    }
                });

        And("^the user sets the warehouse's preferred date time format to \"([^\"]*)\"$",
                (String preferredDateTimeFormatName) -> {
                    var persona = CurrentPersona.persona;
                    var createWarehouseForm = persona.createWarehouseForm;
                    var warehouseEdit = persona.warehouseEdit;

                    assertThat(createWarehouseForm != null || warehouseEdit != null).isTrue();

                    if(createWarehouseForm != null) {
                        createWarehouseForm.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
                    } else {
                        warehouseEdit.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
                    }
                });

    }

}
