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

package com.echothree.cucumber.party;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreatePartyAliasTypeResult;
import com.echothree.control.user.party.common.result.EditPartyAliasTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyAliasTypeSteps implements En {

    public PartyAliasTypeSteps() {
        When("^the user begins entering a new party alias type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyAliasTypeForm).isNull();
                    assertThat(persona.deletePartyAliasTypeForm).isNull();
                    assertThat(persona.partyAliasTypeSpec).isNull();

                    persona.createPartyAliasTypeForm = PartyUtil.getHome().getCreatePartyAliasTypeForm();
                });

        When("^the user adds the new party alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasTypeForm = persona.createPartyAliasTypeForm;

                    assertThat(createPartyAliasTypeForm).isNotNull();

                    var commandResult = PartyUtil.getHome().createPartyAliasType(persona.userVisitPK, createPartyAliasTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreatePartyAliasTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastPartyAliasTypeName = commandResult.getHasErrors() ? null : result.getPartyAliasTypeName();
                    persona.createPartyAliasTypeForm = null;
                });

        When("^the user begins deleting a party alias type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyAliasTypeForm).isNull();
                    assertThat(persona.deletePartyAliasTypeForm).isNull();
                    assertThat(persona.partyAliasTypeSpec).isNull();

                    persona.deletePartyAliasTypeForm = PartyUtil.getHome().getDeletePartyAliasTypeForm();
                });

        When("^the user deletes the party alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deletePartyAliasTypeForm = persona.deletePartyAliasTypeForm;

                    assertThat(deletePartyAliasTypeForm).isNotNull();

                    LastCommandResult.commandResult = PartyUtil.getHome().deletePartyAliasType(persona.userVisitPK, deletePartyAliasTypeForm);

                    persona.deletePartyAliasTypeForm = null;
                });

        When("^the user begins specifying a party alias type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyAliasTypeForm).isNull();
                    assertThat(persona.deletePartyAliasTypeForm).isNull();
                    assertThat(persona.partyAliasTypeSpec).isNull();

                    persona.partyAliasTypeSpec = PartyUtil.getHome().getPartyAliasTypeUniversalSpec();
                });

        When("^the user begins editing the party alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyAliasTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = PartyUtil.getHome().getEditPartyAliasTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = PartyUtil.getHome().editPartyAliasType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditPartyAliasTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.partyAliasTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the party alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyAliasTypeSpec;
                    var edit = persona.partyAliasTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = PartyUtil.getHome().getEditPartyAliasTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = PartyUtil.getHome().editPartyAliasType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.partyAliasTypeSpec = null;
                    persona.partyAliasTypeEdit = null;
                });

        When("^the user sets the party alias type's party type to ([a-zA-Z0-9-_]*)$",
                (String partyTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasTypeForm = persona.createPartyAliasTypeForm;
                    var deletePartyAliasTypeForm = persona.deletePartyAliasTypeForm;
                    var partyAliasTypeSpec = persona.partyAliasTypeSpec;

                    assertThat(createPartyAliasTypeForm != null || deletePartyAliasTypeForm != null || partyAliasTypeSpec != null).isTrue();

                    if(createPartyAliasTypeForm != null) {
                        createPartyAliasTypeForm.setPartyTypeName(partyTypeName);
                    } else if(deletePartyAliasTypeForm != null) {
                        deletePartyAliasTypeForm.setPartyTypeName(partyTypeName);
                    } else {
                        partyAliasTypeSpec.setPartyTypeName(partyTypeName);
                    }
                });

        When("^the user sets the party alias type's name to ([a-zA-Z0-9-_]*)$",
                (String partyAliasTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasTypeForm = persona.createPartyAliasTypeForm;
                    var deletePartyAliasTypeForm = persona.deletePartyAliasTypeForm;
                    var partyAliasTypeSpec = persona.partyAliasTypeSpec;

                    assertThat(createPartyAliasTypeForm != null || deletePartyAliasTypeForm != null || partyAliasTypeSpec != null).isTrue();

                    if(createPartyAliasTypeForm != null) {
                        createPartyAliasTypeForm.setPartyAliasTypeName(partyAliasTypeName);
                    } else if(deletePartyAliasTypeForm != null) {
                        deletePartyAliasTypeForm.setPartyAliasTypeName(partyAliasTypeName);
                    } else {
                        partyAliasTypeSpec.setPartyAliasTypeName(partyAliasTypeName);
                    }
                });

        When("^the user sets the party alias type's name to the last party alias type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastPartyAliasTypeName = persona.lastPartyAliasTypeName;
                    var deletePartyAliasTypeForm = persona.deletePartyAliasTypeForm;
                    var partyAliasTypeSpec = persona.partyAliasTypeSpec;

                    assertThat(deletePartyAliasTypeForm != null || partyAliasTypeSpec != null).isTrue();

                    if(deletePartyAliasTypeForm != null) {
                        deletePartyAliasTypeForm.setPartyAliasTypeName(lastPartyAliasTypeName);
                    } else {
                        partyAliasTypeSpec.setPartyAliasTypeName(lastPartyAliasTypeName);
                    }
                });

        When("^the user sets the party alias type's validation pattern to \"([^\"]*)\"$",
                (String validationPattern) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasTypeForm = persona.createPartyAliasTypeForm;
                    var partyAliasTypeEdit = persona.partyAliasTypeEdit;

                    assertThat(createPartyAliasTypeForm != null || partyAliasTypeEdit != null).isTrue();

                    if(createPartyAliasTypeForm != null) {
                        createPartyAliasTypeForm.setValidationPattern(validationPattern);
                    } else {
                        partyAliasTypeEdit.setValidationPattern(validationPattern);
                    }
                });

        When("^the user sets the party alias type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasTypeForm = persona.createPartyAliasTypeForm;
                    var partyAliasTypeEdit = persona.partyAliasTypeEdit;

                    assertThat(createPartyAliasTypeForm != null || partyAliasTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createPartyAliasTypeForm != null) {
                        createPartyAliasTypeForm.setIsDefault(isDefault);
                    } else {
                        partyAliasTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the party alias type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasTypeForm = persona.createPartyAliasTypeForm;
                    var partyAliasTypeEdit = persona.partyAliasTypeEdit;

                    assertThat(createPartyAliasTypeForm != null || partyAliasTypeEdit != null).isTrue();

                    if(createPartyAliasTypeForm != null) {
                        createPartyAliasTypeForm.setSortOrder(sortOrder);
                    } else {
                        partyAliasTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the party alias type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasTypeForm = persona.createPartyAliasTypeForm;
                    var partyAliasTypeEdit = persona.partyAliasTypeEdit;

                    assertThat(createPartyAliasTypeForm != null || partyAliasTypeEdit != null).isTrue();

                    if(createPartyAliasTypeForm != null) {
                        createPartyAliasTypeForm.setDescription(description);
                    } else {
                        partyAliasTypeEdit.setDescription(description);
                    }
                });
    }

}
