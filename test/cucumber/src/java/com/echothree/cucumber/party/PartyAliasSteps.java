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

package com.echothree.cucumber.party;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.EditPartyAliasResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.AnonymousPersonas;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyAliasSteps implements En {

    public PartyAliasSteps() {
        When("^the user begins entering a new party alias$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyAliasForm).isNull();
                    assertThat(persona.deletePartyAliasForm).isNull();
                    assertThat(persona.partyAliasSpec).isNull();

                    persona.createPartyAliasForm = PartyUtil.getHome().getCreatePartyAliasForm();
                });

        When("^the user adds the new party alias",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasForm = persona.createPartyAliasForm;

                    assertThat(createPartyAliasForm).isNotNull();

                    LastCommandResult.commandResult = PartyUtil.getHome().createPartyAlias(persona.userVisitPK, createPartyAliasForm);

                    persona.createPartyAliasForm = null;
                });

        When("^the user begins deleting a party alias",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyAliasForm).isNull();
                    assertThat(persona.deletePartyAliasForm).isNull();
                    assertThat(persona.partyAliasSpec).isNull();

                    persona.deletePartyAliasForm = PartyUtil.getHome().getDeletePartyAliasForm();
                });

        When("^the user deletes the party alias",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deletePartyAliasForm = persona.deletePartyAliasForm;

                    assertThat(deletePartyAliasForm).isNotNull();

                    LastCommandResult.commandResult = PartyUtil.getHome().deletePartyAlias(persona.userVisitPK, deletePartyAliasForm);

                    persona.deletePartyAliasForm = null;
                });

        When("^the user begins specifying a party alias to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyAliasForm).isNull();
                    assertThat(persona.deletePartyAliasForm).isNull();
                    assertThat(persona.partyAliasSpec).isNull();

                    persona.partyAliasSpec = PartyUtil.getHome().getPartyAliasSpec();
                });

        When("^the user begins editing the party alias",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyAliasSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = PartyUtil.getHome().getEditPartyAliasForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = PartyUtil.getHome().editPartyAlias(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditPartyAliasResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.partyAliasEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the party alias",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.partyAliasSpec;
                    var edit = persona.partyAliasEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = PartyUtil.getHome().getEditPartyAliasForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = PartyUtil.getHome().editPartyAlias(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.partyAliasSpec = null;
                    persona.partyAliasEdit = null;
                });

        When("^the user sets the party alias type's party to ([a-zA-Z0-9-_]*)$",
                (String partyName) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasForm = persona.createPartyAliasForm;
                    var deletePartyAliasForm = persona.deletePartyAliasForm;
                    var partyAliasSpec = persona.partyAliasSpec;

                    assertThat(createPartyAliasForm != null || deletePartyAliasForm != null || partyAliasSpec != null).isTrue();

                    if(createPartyAliasForm != null) {
                        createPartyAliasForm.setPartyName(partyName);
                    } else if(deletePartyAliasForm != null) {
                        deletePartyAliasForm.setPartyName(partyName);
                    } else {
                        partyAliasSpec.setPartyName(partyName);
                    }
                });

        When("^the user sets the party alias's party to the last party added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastPartyName = persona.lastPartyName;
                    var createPartyAliasForm = persona.createPartyAliasForm;
                    var deletePartyAliasForm = persona.deletePartyAliasForm;
                    var partyAliasSpec = persona.partyAliasSpec;

                    assertThat(createPartyAliasForm != null || deletePartyAliasForm != null || partyAliasSpec != null).isTrue();

                    if(createPartyAliasForm != null) {
                        createPartyAliasForm.setPartyName(lastPartyName);
                    } else if(deletePartyAliasForm != null) {
                        deletePartyAliasForm.setPartyName(lastPartyName);
                    } else {
                        partyAliasSpec.setPartyName(lastPartyName);
                    }
                });

        When("^the user sets the party alias's party added by the anonymous user ([^\"]*)$",
                (String anonymous) -> {
                    var persona = CurrentPersona.persona;
                    var anonymousPersona = AnonymousPersonas.getPersona(anonymous);
                    var lastPartyName = anonymousPersona.lastPartyName;
                    var createPartyAliasForm = persona.createPartyAliasForm;
                    var deletePartyAliasForm = persona.deletePartyAliasForm;
                    var partyAliasSpec = persona.partyAliasSpec;

                    assertThat(createPartyAliasForm != null || deletePartyAliasForm != null || partyAliasSpec != null).isTrue();

                    if(createPartyAliasForm != null) {
                        createPartyAliasForm.setPartyName(lastPartyName);
                    } else if(deletePartyAliasForm != null) {
                        deletePartyAliasForm.setPartyName(lastPartyName);
                    } else {
                        partyAliasSpec.setPartyName(lastPartyName);
                    }
                });

        When("^the user sets the party alias type's party alias type to ([a-zA-Z0-9-_]*)$",
                (String partyAliasTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasForm = persona.createPartyAliasForm;
                    var deletePartyAliasForm = persona.deletePartyAliasForm;
                    var partyAliasSpec = persona.partyAliasSpec;

                    assertThat(createPartyAliasForm != null || deletePartyAliasForm != null || partyAliasSpec != null).isTrue();

                    if(createPartyAliasForm != null) {
                        createPartyAliasForm.setPartyAliasTypeName(partyAliasTypeName);
                    } else if(deletePartyAliasForm != null) {
                        deletePartyAliasForm.setPartyAliasTypeName(partyAliasTypeName);
                    } else {
                        partyAliasSpec.setPartyAliasTypeName(partyAliasTypeName);
                    }
                });

        When("^the user sets the party alias's party alias type to the last party alias type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastPartyAliasTypeName = persona.lastPartyAliasTypeName;
                    var createPartyAliasForm = persona.createPartyAliasForm;
                    var deletePartyAliasForm = persona.deletePartyAliasForm;
                    var partyAliasSpec = persona.partyAliasSpec;

                    assertThat(createPartyAliasForm != null || deletePartyAliasForm != null || partyAliasSpec != null).isTrue();

                    if(createPartyAliasForm != null) {
                        createPartyAliasForm.setPartyAliasTypeName(lastPartyAliasTypeName);
                    } else if(deletePartyAliasForm != null) {
                        deletePartyAliasForm.setPartyAliasTypeName(lastPartyAliasTypeName);
                    } else {
                        partyAliasSpec.setPartyAliasTypeName(lastPartyAliasTypeName);
                    }
                });

        When("^the user sets the party alias's alias to \"([^\"]*)\"$",
                (String alias) -> {
                    var persona = CurrentPersona.persona;
                    var createPartyAliasForm = persona.createPartyAliasForm;
                    var partyAliasEdit = persona.partyAliasEdit;

                    assertThat(createPartyAliasForm != null || partyAliasEdit != null).isTrue();

                    if(createPartyAliasForm != null) {
                        createPartyAliasForm.setAlias(alias);
                    } else {
                        partyAliasEdit.setAlias(alias);
                    }
                });

    }

}
