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
import com.echothree.control.user.offer.common.result.CreateUseResult;
import com.echothree.control.user.offer.common.result.EditUseResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class UseSteps implements En {

    public UseSteps() {
        When("^the user begins entering a new use",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseForm).isNull();
                    assertThat(persona.deleteUseForm).isNull();
                    assertThat(persona.useSpec).isNull();

                    persona.createUseForm = OfferUtil.getHome().getCreateUseForm();
                });

        When("^the user adds the new use",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseForm).isNotNull();

                    var useService = OfferUtil.getHome();
                    var createUseForm = useService.getCreateUseForm();

                    createUseForm.set(persona.createUseForm.get());

                    var commandResult = useService.createUse(persona.userVisitPK, createUseForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateUseResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastUseName = commandResult.getHasErrors() ? null : result.getUseName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createUseForm = null;
                });

        When("^the user begins deleting an use",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseForm).isNull();
                    assertThat(persona.deleteUseForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteUseForm = OfferUtil.getHome().getDeleteUseForm();
                });

        When("^the user deletes the use",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteUseForm = persona.deleteUseForm;

                    assertThat(deleteUseForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().deleteUse(persona.userVisitPK, deleteUseForm);

                    persona.deleteUseForm = null;
                });

        When("^the user begins specifying an use to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseForm).isNull();
                    assertThat(persona.deleteUseForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.useSpec = OfferUtil.getHome().getUseSpec();
                });

        When("^the user begins editing the use$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.useSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditUseForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = OfferUtil.getHome().editUse(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditUseResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.useEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the use",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.useSpec;
                    var edit = persona.useEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditUseForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = OfferUtil.getHome().editUse(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.useSpec = null;
                    persona.useEdit = null;
                });


        When("^the user sets the use's use name to \"([^\"]*)\"$",
                (String useName) -> {
                    var persona = CurrentPersona.persona;
                    var createUseForm = persona.createUseForm;
                    var deleteUseForm = persona.deleteUseForm;
                    var useSpec = persona.useSpec;

                    assertThat(createUseForm != null || deleteUseForm != null || useSpec != null).isTrue();

                    if(createUseForm != null) {
                        createUseForm.setUseName(useName);
                    } else if(deleteUseForm != null) {
                        deleteUseForm.setUseName(useName);
                    } else {
                        useSpec.setUseName(useName);
                    }
                });

        When("^the user sets the use's new use name to \"([^\"]*)\"$",
                (String useName) -> {
                    var persona = CurrentPersona.persona;
                    var useEdit = persona.useEdit;

                    assertThat(useEdit).isNotNull();

                    useEdit.setUseName(useName);
                });

        When("^the user sets the use's use type name to \"([^\"]*)\"$",
                (String useTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createUseForm = persona.createUseForm;
                    var useEdit = persona.useEdit;

                    assertThat(createUseForm != null || useEdit != null).isTrue();

                    if(createUseForm != null) {
                        createUseForm.setUseTypeName(useTypeName);
                    } else {
                        useEdit.setUseTypeName(useTypeName);
                    }
                });

        When("^the user sets the use's use type name to the last use type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createUseForm = persona.createUseForm;
                    var useEdit = persona.useEdit;

                    assertThat(createUseForm != null || useEdit != null).isTrue();

                    if(createUseForm != null) {
                        createUseForm.setUseTypeName(persona.lastUseTypeName);
                    } else {
                        useEdit.setUseTypeName(persona.lastUseTypeName);
                    }
                });

        When("^the user sets the use to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createUseForm = persona.createUseForm;
                    var useEdit = persona.useEdit;

                    assertThat(createUseForm != null || useEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createUseForm != null) {
                        createUseForm.setIsDefault(isDefault);
                    } else {
                        useEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the use's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createUseForm = persona.createUseForm;
                    var useEdit = persona.useEdit;

                    assertThat(createUseForm != null || useEdit != null).isTrue();

                    if(createUseForm != null) {
                        createUseForm.setSortOrder(sortOrder);
                    } else {
                        useEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the use's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createUseForm = persona.createUseForm;
                    var useEdit = persona.useEdit;

                    assertThat(createUseForm != null || useEdit != null).isTrue();

                    if(createUseForm != null) {
                        createUseForm.setDescription(description);
                    } else {
                        useEdit.setDescription(description);
                    }
                });
    }

}
