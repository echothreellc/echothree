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
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferItemSteps implements En {

    public OfferItemSteps() {
        When("^the user begins entering a new offer item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferItemForm).isNull();
                    assertThat(persona.deleteOfferItemForm).isNull();

                    persona.createOfferItemForm = OfferUtil.getHome().getCreateOfferItemForm();
                });

        When("^the user adds the new offer item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemForm = persona.createOfferItemForm;

                    assertThat(createOfferItemForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().createOfferItem(persona.userVisitPK, createOfferItemForm);

                    persona.createOfferItemForm = null;
                });

        When("^the user begins deleting an offer item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferItemForm).isNull();
                    assertThat(persona.deleteOfferItemForm).isNull();

                    persona.deleteOfferItemForm = OfferUtil.getHome().getDeleteOfferItemForm();
                });

        When("^the user deletes the offer item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteOfferItemForm = persona.deleteOfferItemForm;

                    assertThat(deleteOfferItemForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().deleteOfferItem(persona.userVisitPK, deleteOfferItemForm);

                    persona.deleteOfferItemForm = null;
                });

        When("^the user sets the offer item's offer name to \"([^\"]*)\"$",
                (String offerName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemForm = persona.createOfferItemForm;
                    var deleteOfferItemForm = persona.deleteOfferItemForm;

                    assertThat(createOfferItemForm != null || deleteOfferItemForm != null).isTrue();

                    Objects.requireNonNullElse(createOfferItemForm, deleteOfferItemForm).setOfferName(offerName);
                });

        When("^the user sets the offer item's offer name to the last offer added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemForm = persona.createOfferItemForm;
                    var deleteOfferItemForm = persona.deleteOfferItemForm;

                    assertThat(createOfferItemForm != null || deleteOfferItemForm != null).isTrue();

                    Objects.requireNonNullElse(createOfferItemForm, deleteOfferItemForm).setOfferName(persona.lastOfferName);
                });

        When("^the user sets the offer item's item name to \"([^\"]*)\"$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemForm = persona.createOfferItemForm;
                    var deleteOfferItemForm = persona.deleteOfferItemForm;

                    assertThat(createOfferItemForm != null || deleteOfferItemForm != null).isTrue();

                    Objects.requireNonNullElse(createOfferItemForm, deleteOfferItemForm).setItemName(itemName);
                });

        When("^the user sets the offer item's item name to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createOfferItemForm = persona.createOfferItemForm;
                    var deleteOfferItemForm = persona.deleteOfferItemForm;

                    assertThat(createOfferItemForm != null || deleteOfferItemForm != null).isTrue();

                    Objects.requireNonNullElse(createOfferItemForm, deleteOfferItemForm).setItemName(persona.lastItemName);
                });
    }

}
