// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.offer.common.result.EditOfferResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferSteps implements En {

    public OfferSteps() {
        When("^the user begins entering a new offer$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferForm).isNull();
                    assertThat(persona.deleteOfferForm).isNull();
                    assertThat(persona.offerSpec).isNull();

                    persona.createOfferForm = OfferUtil.getHome().getCreateOfferForm();
                });

        When("^the user adds the new offer$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferForm).isNotNull();

                    var itemService = OfferUtil.getHome();
                    var createOfferForm = itemService.getCreateOfferForm();

                    createOfferForm.set(persona.createOfferForm.get());

                    var commandResult = itemService.createOffer(persona.userVisitPK, createOfferForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createOfferForm = null;
                });

        When("^the user begins deleting an offer$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferForm).isNull();
                    assertThat(persona.deleteOfferForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.deleteOfferForm = OfferUtil.getHome().getDeleteOfferForm();
                });

        When("^the user deletes the offer$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteOfferForm = persona.deleteOfferForm;

                    assertThat(deleteOfferForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().deleteOffer(persona.userVisitPK, deleteOfferForm);

                    persona.deleteOfferForm = null;
                });

        When("^the user begins specifying an offer to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferForm).isNull();
                    assertThat(persona.deleteOfferForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.offerSpec = OfferUtil.getHome().getOfferSpec();
                });

        When("^the user begins editing the offer",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.offerSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditOfferForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = OfferUtil.getHome().editOffer(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditOfferResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.offerEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the offer",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.offerSpec;
                    var edit = persona.offerEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditOfferForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = OfferUtil.getHome().editOffer(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.offerSpec = null;
                    persona.offerEdit = null;
                });


        When("^the user sets the offer's offer name to \"([^\"]*)\"$",
                (String offerName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;
                    var deleteOfferForm = persona.deleteOfferForm;
                    var offerSpec = persona.offerSpec;

                    assertThat(createOfferForm != null || deleteOfferForm != null || offerSpec != null).isTrue();

                    if(createOfferForm != null) {
                        createOfferForm.setOfferName(offerName);
                    } else if(deleteOfferForm != null) {
                        deleteOfferForm.setOfferName(offerName);
                    } else {
                        offerSpec.setOfferName(offerName);
                    }
                });

        When("^the user sets the offer's new offer name to \"([^\"]*)\"$",
                (String offerName) -> {
                    var persona = CurrentPersona.persona;
                    var offerEdit = persona.offerEdit;

                    assertThat(offerEdit).isNotNull();

                    offerEdit.setOfferName(offerName);
                });

        When("^the user sets the offer's company name to \"([^\"]*)\"$",
                (String partyCompanyName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;

                    assertThat(createOfferForm).isNotNull();

                    createOfferForm.setCompanyName(partyCompanyName);
                });

        When("^the user sets the offer's division name to \"([^\"]*)\"$",
                (String partyDivisionName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;

                    assertThat(createOfferForm).isNotNull();

                    createOfferForm.setDivisionName(partyDivisionName);
                });

        When("^the user sets the offer's department name to \"([^\"]*)\"$",
                (String partyDepartmentName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;

                    assertThat(createOfferForm).isNotNull();

                    createOfferForm.setDepartmentName(partyDepartmentName);
                });

        When("^the user sets the offer's sales order sequence name to \"([^\"]*)\"$",
                (String salesOrderSequenceName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;
                    var offerEdit = persona.offerEdit;

                    assertThat(createOfferForm != null || offerEdit != null).isTrue();

                    if(createOfferForm != null) {
                        createOfferForm.setSalesOrderSequenceName(salesOrderSequenceName);
                    } else {
                        offerEdit.setSalesOrderSequenceName(salesOrderSequenceName);
                    }
                });

        When("^the user sets the offer's offer item selector name to \"([^\"]*)\"$",
                (String offerItemSelectorName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;
                    var offerEdit = persona.offerEdit;

                    assertThat(createOfferForm != null || offerEdit != null).isTrue();

                    if(createOfferForm != null) {
                        createOfferForm.setOfferItemSelectorName(offerItemSelectorName);
                    } else {
                        offerEdit.setOfferItemSelectorName(offerItemSelectorName);
                    }
                });

        When("^the user sets the offer's offer item price filter name price to \"([^\"]*)\"$",
                (String offerItemPriceFilterName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;
                    var offerEdit = persona.offerEdit;

                    assertThat(createOfferForm != null || offerEdit != null).isTrue();

                    if(createOfferForm != null) {
                        createOfferForm.setOfferItemPriceFilterName(offerItemPriceFilterName);
                    } else {
                        offerEdit.setOfferItemPriceFilterName(offerItemPriceFilterName);
                    }
                });

        When("^the user sets the offer to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;
                    var offerEdit = persona.offerEdit;

                    assertThat(createOfferForm != null || offerEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createOfferForm != null) {
                        createOfferForm.setIsDefault(isDefault);
                    } else {
                        offerEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the offer's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;
                    var offerEdit = persona.offerEdit;

                    assertThat(createOfferForm != null || offerEdit != null).isTrue();

                    if(createOfferForm != null) {
                        createOfferForm.setSortOrder(sortOrder);
                    } else {
                        offerEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the offer's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferForm = persona.createOfferForm;
                    var offerEdit = persona.offerEdit;

                    assertThat(createOfferForm != null || offerEdit != null).isTrue();

                    if(createOfferForm != null) {
                        createOfferForm.setDescription(description);
                    } else {
                        offerEdit.setDescription(description);
                    }
                });
    }

}
