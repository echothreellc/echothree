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

package com.echothree.cucumber.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.EditContentCollectionResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentCollectionSteps implements En {

    public ContentCollectionSteps() {
        When("^the user begins entering a new content collection$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCollectionForm).isNull();
                    assertThat(persona.contentCollectionSpec).isNull();

                    persona.createContentCollectionForm = ContentUtil.getHome().getCreateContentCollectionForm();
                });

        When("^the user adds the new content collection$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCollectionForm).isNotNull();

                    var contentUtil = ContentUtil.getHome();
                    var createContentCollectionForm = contentUtil.getCreateContentCollectionForm();

                    createContentCollectionForm.set(persona.createContentCollectionForm.get());

                    var commandResult = contentUtil.createContentCollection(persona.userVisitPK, createContentCollectionForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createContentCollectionForm = null;
                });

        When("^the user begins specifying a content collection to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCollectionForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.contentCollectionSpec = ContentUtil.getHome().getContentCollectionSpec();
                });

        When("^the user begins editing the content collection$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCollectionSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCollectionForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContentUtil.getHome().editContentCollection(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditContentCollectionResult)executionResult.getResult();

                        persona.contentCollectionEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the content collection$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCollectionSpec;
                    var edit = persona.contentCollectionEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCollectionForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ContentUtil.getHome().editContentCollection(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.contentCollectionSpec = null;
                    persona.contentCollectionEdit = null;
                });
    }

}
