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
import com.echothree.control.user.offer.common.result.CreateUseTypeResult;
import com.echothree.control.user.offer.common.result.EditUseTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class UseTypeSteps implements En {

    public UseTypeSteps() {
        When("^the user begins entering a new use type",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseTypeForm).isNull();
                    assertThat(persona.deleteUseTypeForm).isNull();
                    assertThat(persona.useTypeSpec).isNull();

                    persona.createUseTypeForm = OfferUtil.getHome().getCreateUseTypeForm();
                });

        When("^the user adds the new use type",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseTypeForm).isNotNull();

                    var useTypeService = OfferUtil.getHome();
                    var createUseTypeForm = useTypeService.getCreateUseTypeForm();

                    createUseTypeForm.set(persona.createUseTypeForm.get());

                    var commandResult = useTypeService.createUseType(persona.userVisitPK, createUseTypeForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateUseTypeResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastUseTypeName = commandResult.getHasErrors() ? null : result.getUseTypeName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createUseTypeForm = null;
                });

        When("^the user begins deleting an use type",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseTypeForm).isNull();
                    assertThat(persona.deleteUseTypeForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteUseTypeForm = OfferUtil.getHome().getDeleteUseTypeForm();
                });

        When("^the user deletes the use type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteUseTypeForm = persona.deleteUseTypeForm;

                    assertThat(deleteUseTypeForm).isNotNull();

                    LastCommandResult.commandResult = OfferUtil.getHome().deleteUseType(persona.userVisitPK, deleteUseTypeForm);

                    persona.deleteUseTypeForm = null;
                });

        When("^the user begins specifying an use type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUseTypeForm).isNull();
                    assertThat(persona.deleteUseTypeForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.useTypeSpec = OfferUtil.getHome().getUseTypeUniversalSpec();
                });

        When("^the user begins editing the use type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.useTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditUseTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = OfferUtil.getHome().editUseType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditUseTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.useTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the use type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.useTypeSpec;
                    var edit = persona.useTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = OfferUtil.getHome().getEditUseTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = OfferUtil.getHome().editUseType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.useTypeSpec = null;
                    persona.useTypeEdit = null;
                });


        When("^the user sets the use type's use type name to \"([^\"]*)\"$",
                (String useTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createUseTypeForm = persona.createUseTypeForm;
                    var deleteUseTypeForm = persona.deleteUseTypeForm;
                    var useTypeSpec = persona.useTypeSpec;

                    assertThat(createUseTypeForm != null || deleteUseTypeForm != null || useTypeSpec != null).isTrue();

                    if(createUseTypeForm != null) {
                        createUseTypeForm.setUseTypeName(useTypeName);
                    } else if(deleteUseTypeForm != null) {
                        deleteUseTypeForm.setUseTypeName(useTypeName);
                    } else {
                        useTypeSpec.setUseTypeName(useTypeName);
                    }
                });

        When("^the user sets the use type's new use type name to \"([^\"]*)\"$",
                (String useTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var useTypeEdit = persona.useTypeEdit;

                    assertThat(useTypeEdit).isNotNull();

                    useTypeEdit.setUseTypeName(useTypeName);
                });

        When("^the user sets the use type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createUseTypeForm = persona.createUseTypeForm;
                    var useTypeEdit = persona.useTypeEdit;

                    assertThat(createUseTypeForm != null || useTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createUseTypeForm != null) {
                        createUseTypeForm.setIsDefault(isDefault);
                    } else {
                        useTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the use type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createUseTypeForm = persona.createUseTypeForm;
                    var useTypeEdit = persona.useTypeEdit;

                    assertThat(createUseTypeForm != null || useTypeEdit != null).isTrue();

                    if(createUseTypeForm != null) {
                        createUseTypeForm.setSortOrder(sortOrder);
                    } else {
                        useTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the use type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createUseTypeForm = persona.createUseTypeForm;
                    var useTypeEdit = persona.useTypeEdit;

                    assertThat(createUseTypeForm != null || useTypeEdit != null).isTrue();

                    if(createUseTypeForm != null) {
                        createUseTypeForm.setDescription(description);
                    } else {
                        useTypeEdit.setDescription(description);
                    }
                });
    }

}
