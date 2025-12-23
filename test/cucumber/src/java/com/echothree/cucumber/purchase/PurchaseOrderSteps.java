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

package com.echothree.cucumber.purchase;

import com.echothree.control.user.purchase.common.PurchaseUtil;
import com.echothree.control.user.purchase.common.result.CreatePurchaseOrderResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseOrderSteps implements En {

    public PurchaseOrderSteps() {
        When("^the user begins entering a new purchase order$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPurchaseOrderForm).isNull();
                    assertThat(persona.setPurchaseOrderStatusForm).isNull();
                    assertThat(persona.purchaseOrderSpec).isNull();

                    persona.createPurchaseOrderForm = PurchaseUtil.getHome().getCreatePurchaseOrderForm();
                });

        When("^the user adds the new purchase order$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;

                    assertThat(createPurchaseOrderForm).isNotNull();

                    var commandResult = PurchaseUtil.getHome().createPurchaseOrder(persona.userVisitPK, createPurchaseOrderForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreatePurchaseOrderResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastPurchaseOrderName = commandResult.getHasErrors() ? null : result.getOrderName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createPurchaseOrderForm = null;
                });

        When("^the user begins setting the status of a purchase order$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPurchaseOrderForm).isNull();
                    assertThat(persona.setPurchaseOrderStatusForm).isNull();
                    assertThat(persona.purchaseOrderSpec).isNull();

                    persona.setPurchaseOrderStatusForm = PurchaseUtil.getHome().getSetPurchaseOrderStatusForm();
                });

        When("^the user sets the status of the purchase order$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var setPurchaseOrderStatusForm = persona.setPurchaseOrderStatusForm;

                    assertThat(setPurchaseOrderStatusForm).isNotNull();

                    LastCommandResult.commandResult = PurchaseUtil.getHome().setPurchaseOrderStatus(persona.userVisitPK, setPurchaseOrderStatusForm);

                    persona.setPurchaseOrderStatusForm = null;
                });

        When("^the user begins specifying a purchase order to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPurchaseOrderForm).isNull();
                    assertThat(persona.setPurchaseOrderStatusForm).isNull();
                    assertThat(persona.purchaseOrderSpec).isNull();

                    persona.purchaseOrderSpec = PurchaseUtil.getHome().getPurchaseOrderSpec();
                });

//        When("^the user begins editing the purchase order$",
//                () -> {
//                    var persona = CurrentPersona.persona;
//                    var spec = persona.purchaseOrderSpec;
//
//                    assertThat(spec).isNotNull();
//
//                    var commandForm = PurchaseUtil.getHome().getEditPurchaseOrderForm();
//
//                    commandForm.setSpec(spec);
//                    commandForm.setEditMode(EditMode.LOCK);
//
//                    var commandResult = PurchaseUtil.getHome().editPurchaseOrder(persona.userVisitPK, commandForm);
//                    LastCommandResult.commandResult = commandResult;
//
//                    var executionResult = commandResult.getExecutionResult();
//                    var result = (EditPurchaseOrderResult)executionResult.getResult();
//
//                    if(!executionResult.getHasErrors()) {
//                        persona.purchaseOrderEdit = result.getEdit();
//                    }
//                });
//
//        When("^the user finishes editing the purchase order$",
//                () -> {
//                    var persona = CurrentPersona.persona;
//                    var spec = persona.purchaseOrderSpec;
//                    var edit = persona.purchaseOrderEdit;
//
//                    assertThat(spec).isNotNull();
//                    assertThat(edit).isNotNull();
//
//                    var commandForm = PurchaseUtil.getHome().getEditPurchaseOrderForm();
//
//                    commandForm.setSpec(spec);
//                    commandForm.setEdit(edit);
//                    commandForm.setEditMode(EditMode.UPDATE);
//
//                    var commandResult = PurchaseUtil.getHome().editPurchaseOrder(persona.userVisitPK, commandForm);
//                    LastCommandResult.commandResult = commandResult;
//
//                    persona.purchaseOrderSpec = null;
//                    persona.purchaseOrderEdit = null;
//                });

        When("^the user sets the purchase order's vendor name to ([a-zA-Z0-9-_]*)$",
                (String vendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;

                    assertThat(createPurchaseOrderForm).isNotNull();

                    createPurchaseOrderForm.setVendorName(vendorName);
                });

        When("^the user sets the purchase order's purchase order name to the last purchase order added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var setPurchaseOrderStatusForm = persona.setPurchaseOrderStatusForm;
                    var purchaseOrderSpec = persona.purchaseOrderSpec;

                    assertThat(setPurchaseOrderStatusForm != null || purchaseOrderSpec != null).isTrue();

                    if(setPurchaseOrderStatusForm != null) {
                        setPurchaseOrderStatusForm.setOrderName(persona.lastPurchaseOrderName);
                    } else {
                        purchaseOrderSpec.setOrderName(persona.lastPurchaseOrderName);
                    }
                });

        When("^the user sets the purchase order's purchase order name to ([a-zA-Z0-9-_]*)$",
                (String purchaseOrderName) -> {
                    var persona = CurrentPersona.persona;
                    var setPurchaseOrderStatusForm = persona.setPurchaseOrderStatusForm;
                    var purchaseOrderSpec = persona.purchaseOrderSpec;

                    assertThat(setPurchaseOrderStatusForm != null || purchaseOrderSpec != null).isTrue();

                    if(setPurchaseOrderStatusForm != null) {
                        setPurchaseOrderStatusForm.setOrderName(purchaseOrderName);
                    } else {
                        purchaseOrderSpec.setOrderName(purchaseOrderName);
                    }
                });

        When("^the user sets the purchase order's status to ([a-zA-Z0-9-_]*)$",
                (String purchaseOrderStatusChoice) -> {
                    var persona = CurrentPersona.persona;
                    var setPurchaseOrderStatusForm = persona.setPurchaseOrderStatusForm;

                    assertThat(setPurchaseOrderStatusForm).isNotNull();

                    setPurchaseOrderStatusForm.setPurchaseOrderStatusChoice(purchaseOrderStatusChoice);
                });

        When("^the user sets the purchase order's term name to \"([^\"]*)\"$",
                (String termName) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;
                    var purchaseOrderEdit = persona.purchaseOrderEdit;

                    assertThat(createPurchaseOrderForm != null || purchaseOrderEdit != null).isTrue();

                    Objects.requireNonNullElse(createPurchaseOrderForm, purchaseOrderEdit).setTermName(termName);
                });

        When("^the user indicates the purchase order hold until complete should be (set|not set)$",
                (String holdUntilComplete) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;
                    var purchaseOrderEdit = persona.purchaseOrderEdit;

                    assertThat(createPurchaseOrderForm != null || purchaseOrderEdit != null).isTrue();

                    holdUntilComplete = Boolean.valueOf(holdUntilComplete.equals("set")).toString();
                    Objects.requireNonNullElse(createPurchaseOrderForm, purchaseOrderEdit).setHoldUntilComplete(holdUntilComplete);
                });

        When("^the user indicates the purchase order for allow backorders should be (set|not set)$",
                (String allowBackorders) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;
                    var purchaseOrderEdit = persona.purchaseOrderEdit;

                    assertThat(createPurchaseOrderForm != null || purchaseOrderEdit != null).isTrue();

                    allowBackorders = Boolean.valueOf(allowBackorders.equals("set")).toString();
                    Objects.requireNonNullElse(createPurchaseOrderForm, purchaseOrderEdit).setAllowBackorders(allowBackorders);
                });

        When("^the user indicates the purchase order for allow substitutions should be (set|not set)$",
                (String allowSubstitutions) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;
                    var purchaseOrderEdit = persona.purchaseOrderEdit;

                    assertThat(createPurchaseOrderForm != null || purchaseOrderEdit != null).isTrue();

                    allowSubstitutions = Boolean.valueOf(allowSubstitutions.equals("set")).toString();
                    Objects.requireNonNullElse(createPurchaseOrderForm, purchaseOrderEdit).setAllowSubstitutions(allowSubstitutions);
                });

        When("^the user indicates the purchase order for allow combining shipments should be (set|not set)$",
                (String allowCombiningShipments) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;
                    var purchaseOrderEdit = persona.purchaseOrderEdit;

                    assertThat(createPurchaseOrderForm != null || purchaseOrderEdit != null).isTrue();

                    allowCombiningShipments = Boolean.valueOf(allowCombiningShipments.equals("set")).toString();
                    Objects.requireNonNullElse(createPurchaseOrderForm, purchaseOrderEdit).setAllowCombiningShipments(allowCombiningShipments);
                });

        When("^the user sets the purchase order's reference to \"([^\"]*)\"$",
                (String reference) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;
                    var purchaseOrderEdit = persona.purchaseOrderEdit;

                    assertThat(createPurchaseOrderForm != null || purchaseOrderEdit != null).isTrue();

                    Objects.requireNonNullElse(createPurchaseOrderForm, purchaseOrderEdit).setReference(reference);
                });

        When("^the user sets the purchase order's free on board name to \"([^\"]*)\"$",
                (String freeOnBoardName) -> {
                    var persona = CurrentPersona.persona;
                    var createPurchaseOrderForm = persona.createPurchaseOrderForm;
                    var purchaseOrderEdit = persona.purchaseOrderEdit;

                    assertThat(createPurchaseOrderForm != null || purchaseOrderEdit != null).isTrue();

                    Objects.requireNonNullElse(createPurchaseOrderForm, purchaseOrderEdit).setFreeOnBoardName(freeOnBoardName);
                });
    }

}
