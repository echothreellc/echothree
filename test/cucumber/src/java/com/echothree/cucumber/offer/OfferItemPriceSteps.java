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

package com.echothree.cucumber.offer;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.EditOfferItemPriceResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferItemPriceSteps implements En {

    public OfferItemPriceSteps() {
        When("^the user begins entering a new offer item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferItemPriceForm).isNull();
                    assertThat(persona.deleteOfferItemPriceForm).isNull();
                    assertThat(persona.offerItemPriceSpec).isNull();

                    persona.createOfferItemPriceForm = OfferUtil.getHome().getCreateOfferItemPriceForm();
                });

        When("^the user adds the new offer item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferItemPriceForm).isNotNull();

                    var itemService = OfferUtil.getHome();
                    var createOfferItemPriceForm = itemService.getCreateOfferItemPriceForm();

                    createOfferItemPriceForm.set(persona.createOfferItemPriceForm.get());

                    LastCommandResult.commandResult = itemService.createOfferItemPrice(persona.userVisitPK, createOfferItemPriceForm);

                    persona.createOfferItemPriceForm = null;
                });

        When("^the user begins deleting an offer item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferItemPriceForm).isNull();
                    assertThat(persona.deleteOfferItemPriceForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteOfferItemPriceForm = OfferUtil.getHome().getDeleteOfferItemPriceForm();
                });

        When("^the user deletes the offer item price$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;

                    assertThat(deleteOfferItemPriceForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().deleteOfferItemPrice(persona.userVisitPK, deleteOfferItemPriceForm);

                    persona.deleteOfferItemPriceForm = null;
                });

        When("^the user begins specifying an offer item price to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferItemPriceForm).isNull();
                    assertThat(persona.deleteOfferItemPriceForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.offerItemPriceSpec = OfferUtil.getHome().getOfferItemPriceSpec();
                });

        When("^the user begins editing the offer item price",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.offerItemPriceSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditOfferItemPriceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = OfferUtil.getHome().editOfferItemPrice(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    if(!executionResult.getHasErrors()) {
                        var result = (EditOfferItemPriceResult)executionResult.getResult();

                        persona.offerItemPriceEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the offer item price",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.offerItemPriceSpec;
                    var edit = persona.offerItemPriceEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditOfferItemPriceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = OfferUtil.getHome().editOfferItemPrice(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.offerItemPriceSpec = null;
                    persona.offerItemPriceEdit = null;
                });

        When("^the user sets the offer item price's offer name to \"([^\"]*)\"$",
                (String offerName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;
                    var offerItemPriceSpec = persona.offerItemPriceSpec;

                    assertThat(createOfferItemPriceForm != null || deleteOfferItemPriceForm != null || offerItemPriceSpec != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setOfferName(offerName);
                    } else if(deleteOfferItemPriceForm != null) {
                        deleteOfferItemPriceForm.setOfferName(offerName);
                    } else {
                        offerItemPriceSpec.setOfferName(offerName);
                    }
                });

        When("^the user sets the offer item price's offer name to the last offer added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;
                    var offerItemPriceSpec = persona.offerItemPriceSpec;
                    var lastOfferName = persona.lastOfferName;

                    assertThat(createOfferItemPriceForm != null || deleteOfferItemPriceForm != null || offerItemPriceSpec != null).isTrue();
                    assertThat(lastOfferName).isNotNull();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setOfferName(lastOfferName);
                    } else if(deleteOfferItemPriceForm != null) {
                        deleteOfferItemPriceForm.setOfferName(lastOfferName);
                    } else {
                        offerItemPriceSpec.setOfferName(lastOfferName);
                    }
                });

        When("^the user sets the offer item price's item name to \"([^\"]*)\"$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;
                    var offerItemPriceSpec = persona.offerItemPriceSpec;

                    assertThat(createOfferItemPriceForm != null || deleteOfferItemPriceForm != null || offerItemPriceSpec != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setItemName(itemName);
                    } else if(deleteOfferItemPriceForm != null) {
                        deleteOfferItemPriceForm.setItemName(itemName);
                    } else {
                        offerItemPriceSpec.setItemName(itemName);
                    }
                });

        When("^the user sets the offer item price's item name to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;
                    var offerItemPriceSpec = persona.offerItemPriceSpec;
                    var lastItemName = persona.lastItemName;

                    assertThat(createOfferItemPriceForm != null || deleteOfferItemPriceForm != null || offerItemPriceSpec != null).isTrue();
                    assertThat(lastItemName).isNotNull();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setItemName(lastItemName);
                    } else if(deleteOfferItemPriceForm != null) {
                        deleteOfferItemPriceForm.setItemName(lastItemName);
                    } else {
                        offerItemPriceSpec.setItemName(lastItemName);
                    }
                });

        When("^the user sets the offer item price's inventory condition to \"([^\"]*)\"$",
                (String inventoryConditionName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;
                    var offerItemPriceSpec = persona.offerItemPriceSpec;

                    assertThat(createOfferItemPriceForm != null || deleteOfferItemPriceForm != null || offerItemPriceSpec != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setInventoryConditionName(inventoryConditionName);
                    } else if(deleteOfferItemPriceForm != null) {
                        deleteOfferItemPriceForm.setInventoryConditionName(inventoryConditionName);
                    } else {
                        offerItemPriceSpec.setInventoryConditionName(inventoryConditionName);
                    }
                });

        When("^the user sets the offer item price's unit of measure type to \"([^\"]*)\"$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;
                    var offerItemPriceSpec = persona.offerItemPriceSpec;

                    assertThat(createOfferItemPriceForm != null || deleteOfferItemPriceForm != null || offerItemPriceSpec != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else if(deleteOfferItemPriceForm != null) {
                        deleteOfferItemPriceForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        offerItemPriceSpec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the offer item price's currency to \"([^\"]*)\"$",
                (String currencyIsoName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var deleteOfferItemPriceForm = persona.deleteOfferItemPriceForm;
                    var offerItemPriceSpec = persona.offerItemPriceSpec;

                    assertThat(createOfferItemPriceForm != null || deleteOfferItemPriceForm != null || offerItemPriceSpec != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setCurrencyIsoName(currencyIsoName);
                    } else if(deleteOfferItemPriceForm != null) {
                        deleteOfferItemPriceForm.setCurrencyIsoName(currencyIsoName);
                    } else {
                        offerItemPriceSpec.setCurrencyIsoName(currencyIsoName);
                    }
                });

        When("^the user sets the offer item price's unit price to \"([^\"]*)\"$",
                (String unitPrice) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var offerItemPriceEdit = persona.offerItemPriceEdit;

                    assertThat(createOfferItemPriceForm != null || offerItemPriceEdit != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setUnitPrice(unitPrice);
                    } else {
                        offerItemPriceEdit.setUnitPrice(unitPrice);
                    }
                });

        When("^the user sets the offer item price's minimum unit price to \"([^\"]*)\"$",
                (String minimumUnitPrice) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var offerItemPriceEdit = persona.offerItemPriceEdit;

                    assertThat(createOfferItemPriceForm != null || offerItemPriceEdit != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setMinimumUnitPrice(minimumUnitPrice);
                    } else {
                        offerItemPriceEdit.setMinimumUnitPrice(minimumUnitPrice);
                    }
                });

        When("^the user sets the offer item price's maximum unit price to \"([^\"]*)\"$",
                (String maximumUnitPrice) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var offerItemPriceEdit = persona.offerItemPriceEdit;

                    assertThat(createOfferItemPriceForm != null || offerItemPriceEdit != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setMaximumUnitPrice(maximumUnitPrice);
                    } else {
                        offerItemPriceEdit.setMaximumUnitPrice(maximumUnitPrice);
                    }
                });

        When("^the user sets the offer item price's unit price increment to \"([^\"]*)\"$",
                (String unitPriceIncrement) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemPriceForm = persona.createOfferItemPriceForm;
                    var offerItemPriceEdit = persona.offerItemPriceEdit;

                    assertThat(createOfferItemPriceForm != null || offerItemPriceEdit != null).isTrue();

                    if(createOfferItemPriceForm != null) {
                        createOfferItemPriceForm.setUnitPriceIncrement(unitPriceIncrement);
                    } else {
                        offerItemPriceEdit.setUnitPriceIncrement(unitPriceIncrement);
                    }
                });
    }

}
