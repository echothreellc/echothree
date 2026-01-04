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
import com.echothree.control.user.offer.common.result.CreateSourceResult;
import com.echothree.control.user.offer.common.result.EditSourceResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class SourceSteps implements En {

    public SourceSteps() {
        When("^the user begins entering a new source$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSourceForm).isNull();
                    assertThat(persona.deleteSourceForm).isNull();
                    assertThat(persona.sourceSpec).isNull();

                    persona.createSourceForm = OfferUtil.getHome().getCreateSourceForm();
                });

        When("^the user adds the new source$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSourceForm).isNotNull();

                    var offerService = OfferUtil.getHome();
                    var createSourceForm = offerService.getCreateSourceForm();

                    createSourceForm.set(persona.createSourceForm.get());

                    var commandResult = offerService.createSource(persona.userVisitPK, createSourceForm);
                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateSourceResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastSourceName = commandResult.getHasErrors() ? null : result.getSourceName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createSourceForm = null;
                });

        When("^the user begins deleting a source$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSourceForm).isNull();
                    assertThat(persona.deleteSourceForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteSourceForm = OfferUtil.getHome().getDeleteSourceForm();
                });

        When("^the user deletes the source$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteSourceForm = persona.deleteSourceForm;

                    assertThat(deleteSourceForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().deleteSource(persona.userVisitPK, deleteSourceForm);

                    persona.deleteSourceForm = null;
                });

        When("^the user begins specifying a source to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createSourceForm).isNull();
                    assertThat(persona.deleteSourceForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.sourceSpec = OfferUtil.getHome().getSourceSpec();
                });

        When("^the user begins editing the source$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.sourceSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditSourceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = OfferUtil.getHome().editSource(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditSourceResult)executionResult.getResult();

                        persona.sourceEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the source$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.sourceSpec;
                    var edit = persona.sourceEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditSourceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = OfferUtil.getHome().editSource(persona.userVisitPK, commandForm);

                    persona.sourceSpec = null;
                    persona.sourceEdit = null;
                });

        When("^the user sets the source's offer name to \"([^\"]*)\"$",
                (String offerName) -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;

                    assertThat(createSourceForm).isNotNull();

                    createSourceForm.setOfferName(offerName);
                });

        When("^the user sets the source's offer name to the last offer added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;

                    assertThat(createSourceForm).isNotNull();

                    createSourceForm.setOfferName(persona.lastOfferName);
                });

        When("^the user sets the source's use name to \"([^\"]*)\"$",
                (String useName) -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;

                    assertThat(createSourceForm).isNotNull();

                    createSourceForm.setUseName(useName);
                });

        When("^the user sets the source's use name to the last use added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;

                    assertThat(createSourceForm).isNotNull();

                    createSourceForm.setUseName(persona.lastUseName);
                });

        When("^the user sets the source's source name to \"([^\"]*)\"$",
                (String sourceName) -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;
                    var deleteSourceForm = persona.deleteSourceForm;
                    var sourceSpec = persona.sourceSpec;

                    assertThat(createSourceForm != null || deleteSourceForm != null || sourceSpec != null).isTrue();

                    if(createSourceForm != null) {
                        createSourceForm.setSourceName(sourceName);
                    } else Objects.requireNonNullElse(deleteSourceForm, sourceSpec).setSourceName(sourceName);
                });

        When("^the user sets the source's source name to the last source added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;
                    var deleteSourceForm = persona.deleteSourceForm;
                    var sourceSpec = persona.sourceSpec;

                    assertThat(createSourceForm != null || deleteSourceForm != null || sourceSpec != null).isTrue();

                    var lastSourceName = persona.lastSourceName;
                    if(createSourceForm != null) {
                        createSourceForm.setSourceName(lastSourceName);
                    } else Objects.requireNonNullElse(deleteSourceForm, sourceSpec).setSourceName(lastSourceName);
                });

        When("^the user sets the source to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;
                    var sourceEdit = persona.sourceEdit;

                    assertThat(createSourceForm != null || sourceEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    Objects.requireNonNullElse(createSourceForm, sourceEdit).setIsDefault(isDefault);
                });

        When("^the user sets the source's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createSourceForm = persona.createSourceForm;
                    var sourceEdit = persona.sourceEdit;

                    assertThat(createSourceForm != null || sourceEdit != null).isTrue();

                    Objects.requireNonNullElse(createSourceForm, sourceEdit).setSortOrder(sortOrder);
                });
    }

}
