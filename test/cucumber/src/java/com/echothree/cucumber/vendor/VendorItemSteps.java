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
import com.echothree.control.user.vendor.common.result.EditVendorItemResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class VendorItemSteps implements En {

    public VendorItemSteps() {
        When("^the user begins entering a new vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorItemForm).isNull();
                    assertThat(persona.setVendorItemStatusForm).isNull();
                    assertThat(persona.deleteVendorItemForm).isNull();
                    assertThat(persona.vendorItemUniversalSpec).isNull();

                    persona.createVendorItemForm = VendorUtil.getHome().getCreateVendorItemForm();
                });

        When("^the user adds the new vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;

                    assertThat(createVendorItemForm).isNotNull();

                    LastCommandResult.commandResult = VendorUtil.getHome().createVendorItem(persona.userVisitPK, createVendorItemForm);

                    persona.createVendorItemForm = null;
                });

        When("^the user begins setting the status of a vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorItemForm).isNull();
                    assertThat(persona.setVendorItemStatusForm).isNull();
                    assertThat(persona.deleteVendorItemForm).isNull();
                    assertThat(persona.vendorItemUniversalSpec).isNull();

                    persona.setVendorItemStatusForm = VendorUtil.getHome().getSetVendorItemStatusForm();
                });

        When("^the user sets the status of the vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var setVendorItemStatusForm = persona.setVendorItemStatusForm;

                    assertThat(setVendorItemStatusForm).isNotNull();

                    LastCommandResult.commandResult = VendorUtil.getHome().setVendorItemStatus(persona.userVisitPK, setVendorItemStatusForm);

                    persona.setVendorItemStatusForm = null;
                });

        When("^the user begins deleting a vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorItemForm).isNull();
                    assertThat(persona.setVendorItemStatusForm).isNull();
                    assertThat(persona.deleteVendorItemForm).isNull();
                    assertThat(persona.vendorItemUniversalSpec).isNull();

                    persona.deleteVendorItemForm = VendorUtil.getHome().getDeleteVendorItemForm();
                });

        When("^the user deletes the vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteVendorItemForm = persona.deleteVendorItemForm;

                    assertThat(deleteVendorItemForm).isNotNull();

                    LastCommandResult.commandResult = VendorUtil.getHome().deleteVendorItem(persona.userVisitPK, deleteVendorItemForm);

                    persona.deleteVendorItemForm = null;
                });

        When("^the user begins specifying a vendor item to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorItemForm).isNull();
                    assertThat(persona.setVendorItemStatusForm).isNull();
                    assertThat(persona.deleteVendorItemForm).isNull();
                    assertThat(persona.vendorItemUniversalSpec).isNull();

                    persona.vendorItemUniversalSpec = VendorUtil.getHome().getVendorItemUniversalSpec();
                });

        When("^the user begins editing the vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorItemUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = VendorUtil.getHome().editVendorItem(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditVendorItemResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.vendorItemEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the vendor item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorItemUniversalSpec;
                    var edit = persona.vendorItemEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = VendorUtil.getHome().editVendorItem(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.vendorItemUniversalSpec = null;
                    persona.vendorItemEdit = null;
                });

        When("^the user sets the vendor item's item name to ([a-zA-Z0-9-_]*)$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;

                    assertThat(createVendorItemForm).isNotNull();

                    createVendorItemForm.setItemName(itemName);
                });

        When("^the user sets the vendor item's item name to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;

                    assertThat(createVendorItemForm).isNotNull();

                    createVendorItemForm.setItemName(persona.lastItemName);
                });

        When("^the user sets the vendor item's vendor name to ([a-zA-Z0-9-_]*)$",
                (String vendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;
                    var setVendorItemStatusForm = persona.setVendorItemStatusForm;
                    var deleteVendorItemForm = persona.deleteVendorItemForm;
                    var vendorItemSpec = persona.vendorItemUniversalSpec;

                    assertThat(createVendorItemForm != null || setVendorItemStatusForm != null || deleteVendorItemForm != null
                            || vendorItemSpec != null).isTrue();

                    if(createVendorItemForm != null) {
                        createVendorItemForm.setVendorName(vendorName);
                    } else if(setVendorItemStatusForm != null) {
                        setVendorItemStatusForm.setVendorName(vendorName);
                    } else if(deleteVendorItemForm != null) {
                        deleteVendorItemForm.setVendorName(vendorName);
                    } else {
                        vendorItemSpec.setVendorName(vendorName);
                    }
                });

        When("^the user sets the vendor item's vendor name to the last vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;
                    var setVendorItemStatusForm = persona.setVendorItemStatusForm;
                    var deleteVendorItemForm = persona.deleteVendorItemForm;
                    var vendorItemSpec = persona.vendorItemUniversalSpec;

                    assertThat(createVendorItemForm != null || setVendorItemStatusForm != null || deleteVendorItemForm != null
                            || vendorItemSpec != null).isTrue();

                    if(createVendorItemForm != null) {
                        createVendorItemForm.setVendorName(persona.lastVendorName);
                    } else if(setVendorItemStatusForm != null) {
                        setVendorItemStatusForm.setVendorName(persona.lastVendorName);
                    } else if(deleteVendorItemForm != null) {
                        deleteVendorItemForm.setVendorName(persona.lastVendorName);
                    } else {
                        vendorItemSpec.setVendorName(persona.lastVendorName);
                    }
                });

        When("^the user sets the vendor item's vendor item name to ([a-zA-Z0-9-_]*)$",
                (String vendorItemName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;
                    var setVendorItemStatusForm = persona.setVendorItemStatusForm;
                    var deleteVendorItemForm = persona.deleteVendorItemForm;
                    var vendorItemSpec = persona.vendorItemUniversalSpec;

                    assertThat(createVendorItemForm != null || setVendorItemStatusForm != null || deleteVendorItemForm != null
                            || vendorItemSpec != null).isTrue();

                    if(createVendorItemForm != null) {
                        createVendorItemForm.setVendorItemName(vendorItemName);
                    } else if(setVendorItemStatusForm != null) {
                        setVendorItemStatusForm.setVendorItemName(vendorItemName);
                    } else if(deleteVendorItemForm != null) {
                        deleteVendorItemForm.setVendorItemName(vendorItemName);
                    } else {
                        vendorItemSpec.setVendorItemName(vendorItemName);
                    }
                });

        When("^the user sets the vendor item's new vendor item name to ([a-zA-Z0-9-_]*)$",
                (String vendorItemName) -> {
                    var persona = CurrentPersona.persona;
                    var vendorItemEdit = persona.vendorItemEdit;

                    assertThat(vendorItemEdit).isNotNull();

                    vendorItemEdit.setVendorItemName(vendorItemName);
                });

        When("^the user sets the vendor item's status to ([a-zA-Z0-9-_]*)$",
                (String vendorItemStatusChoice) -> {
                    var persona = CurrentPersona.persona;
                    var setVendorItemStatusForm = persona.setVendorItemStatusForm;

                    assertThat(setVendorItemStatusForm).isNotNull();

                    setVendorItemStatusForm.setVendorItemStatusChoice(vendorItemStatusChoice);
                });

        When("^the user sets the vendor item's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;
                    var vendorItemEdit = persona.vendorItemEdit;

                    assertThat(createVendorItemForm != null || vendorItemEdit != null).isTrue();

                    if(createVendorItemForm != null) {
                        createVendorItemForm.setDescription(description);
                    } else {
                        vendorItemEdit.setDescription(description);
                    }
                });

        When("^the user sets the vendor item's priority to \"([^\"]*)\"$",
                (String priority) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;
                    var vendorItemEdit = persona.vendorItemEdit;

                    assertThat(createVendorItemForm != null || vendorItemEdit != null).isTrue();

                    if(createVendorItemForm != null) {
                        createVendorItemForm.setPriority(priority);
                    } else {
                        vendorItemEdit.setPriority(priority);
                    }
                });

        When("^the user sets the vendor item's cancellation policy to ([a-zA-Z0-9-_]*)$",
                (String cancellationPolicyName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;
                    var vendorItemEdit = persona.vendorItemEdit;

                    assertThat(createVendorItemForm != null || vendorItemEdit != null).isTrue();

                    if(createVendorItemForm != null) {
                        createVendorItemForm.setCancellationPolicyName(cancellationPolicyName);
                    } else {
                        vendorItemEdit.setCancellationPolicyName(cancellationPolicyName);
                    }
                });

        When("^the user sets the vendor item's return policy to ([a-zA-Z0-9-_]*)$",
                (String returnPolicyName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorItemForm = persona.createVendorItemForm;
                    var vendorItemEdit = persona.vendorItemEdit;

                    assertThat(createVendorItemForm != null || vendorItemEdit != null).isTrue();

                    if(createVendorItemForm != null) {
                        createVendorItemForm.setReturnPolicyName(returnPolicyName);
                    } else {
                        vendorItemEdit.setReturnPolicyName(returnPolicyName);
                    }
                });
    }

}
