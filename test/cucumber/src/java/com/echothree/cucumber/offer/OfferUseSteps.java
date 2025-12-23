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
import com.echothree.control.user.offer.common.result.EditOfferUseResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferUseSteps implements En {

    public OfferUseSteps() {
        When("^the user begins entering a new offer use$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferUseForm).isNull();
                    assertThat(persona.deleteOfferUseForm).isNull();
                    assertThat(persona.offerUseSpec).isNull();

                    persona.createOfferUseForm = OfferUtil.getHome().getCreateOfferUseForm();
                });

        When("^the user adds the new offer use$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferUseForm).isNotNull();

                    var offerUtil = OfferUtil.getHome();
                    var createOfferUseForm = offerUtil.getCreateOfferUseForm();

                    createOfferUseForm.set(persona.createOfferUseForm.get());

                    LastCommandResult.commandResult = offerUtil.createOfferUse(persona.userVisitPK, createOfferUseForm);

                    persona.createOfferUseForm = null;
                });

        When("^the user begins deleting an offer use$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferUseForm).isNull();
                    assertThat(persona.deleteOfferUseForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteOfferUseForm = OfferUtil.getHome().getDeleteOfferUseForm();
                });

        When("^the user deletes the offer use$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteOfferUseForm = persona.deleteOfferUseForm;

                    assertThat(deleteOfferUseForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().deleteOfferUse(persona.userVisitPK, deleteOfferUseForm);

                    persona.deleteOfferUseForm = null;
                });

        When("^the user begins specifying an offer use to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createOfferUseForm).isNull();
                    assertThat(persona.deleteOfferUseForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.offerUseSpec = OfferUtil.getHome().getOfferUseSpec();
                });

        When("^the user begins editing the offer use$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.offerUseSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditOfferUseForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = OfferUtil.getHome().editOfferUse(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditOfferUseResult)executionResult.getResult();

                        persona.offerUseEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the offer use$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.offerUseSpec;
                    var edit = persona.offerUseEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditOfferUseForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = OfferUtil.getHome().editOfferUse(persona.userVisitPK, commandForm);

                    persona.offerUseSpec = null;
                    persona.offerUseEdit = null;
                });

        When("^the user sets the offer use's offer name to \"([^\"]*)\"$",
                (String offerName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferUseForm = persona.createOfferUseForm;
                    var deleteOfferUseForm = persona.deleteOfferUseForm;
                    var offerUseSpec = persona.offerUseSpec;

                    assertThat(createOfferUseForm != null || deleteOfferUseForm != null
                            || offerUseSpec != null).isTrue();

                    if(createOfferUseForm != null) {
                        createOfferUseForm.setOfferName(offerName);
                    } else {
                        Objects.requireNonNullElse(deleteOfferUseForm, offerUseSpec).setOfferName(offerName);
                    }
                });

        When("^the user sets the offer use's offer name to the last offer added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createOfferUseForm = persona.createOfferUseForm;
                    var deleteOfferUseForm = persona.deleteOfferUseForm;
                    var offerUseSpec = persona.offerUseSpec;

                    assertThat(createOfferUseForm != null || deleteOfferUseForm != null
                            || offerUseSpec != null).isTrue();

                    if(createOfferUseForm != null) {
                        createOfferUseForm.setOfferName(persona.lastOfferName);
                    } else Objects.requireNonNullElse(deleteOfferUseForm, offerUseSpec).setOfferName(persona.lastOfferName);
                });

        When("^the user sets the offer use's use name to \"([^\"]*)\"$",
                (String useName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferUseForm = persona.createOfferUseForm;
                    var deleteOfferUseForm = persona.deleteOfferUseForm;
                    var offerUseSpec = persona.offerUseSpec;

                    assertThat(createOfferUseForm != null || deleteOfferUseForm != null
                            || offerUseSpec != null).isTrue();

                    if(createOfferUseForm != null) {
                        createOfferUseForm.setUseName(useName);
                    } else {
                        Objects.requireNonNullElse(deleteOfferUseForm, offerUseSpec).setUseName(useName);
                    }
                });

        When("^the user sets the offer use's use name to the last use added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createOfferUseForm = persona.createOfferUseForm;
                    var deleteOfferUseForm = persona.deleteOfferUseForm;
                    var offerUseSpec = persona.offerUseSpec;

                    assertThat(createOfferUseForm != null || deleteOfferUseForm != null
                            || offerUseSpec != null).isTrue();

                    if(createOfferUseForm != null) {
                        createOfferUseForm.setUseName(persona.lastUseName);
                    } else {
                        Objects.requireNonNullElse(deleteOfferUseForm, offerUseSpec).setUseName(persona.lastUseName);
                    }
                });

        When("^the user sets the offer use's sales order sequence name to \"([^\"]*)\"$",
                (String salesOrderSequenceName) -> {
                    var persona = CurrentPersona.persona;
                    var createOfferUseForm = persona.createOfferUseForm;
                    var offerUseEdit = persona.offerUseEdit;

                    assertThat(createOfferUseForm != null || offerUseEdit != null).isTrue();

                    Objects.requireNonNullElse(createOfferUseForm, offerUseEdit).setSalesOrderSequenceName(salesOrderSequenceName);
                });
    }

}
