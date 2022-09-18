// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.cucumber.search;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.SearchItemsResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchItemsSteps implements En {

    public SearchItemsSteps() {
        When("^the user begins entering a new item search$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.searchItemsForm).isNull();

                    persona.searchItemsForm = SearchUtil.getHome().getSearchItemsForm();
                });

        When("^the user executes the new item search$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var searchItemsForm = persona.searchItemsForm;

                    assertThat(searchItemsForm).isNotNull();

                    var commandResult = SearchUtil.getHome().searchItems(persona.userVisitPK, searchItemsForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (SearchItemsResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastSearchItemsCount = commandResult.getHasErrors() ? null : result.getCount();
                    persona.searchItemsForm = null;
                });

        When("^the user's search results contain \"([^\"]*)\" result(s|)$",
                (Long itemSearchCount, String discard1) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.lastSearchItemsCount).isEqualTo(itemSearchCount);
                });

        When("^the user sets the item search's search type to \"([^\"]*)\"$",
                (String searchTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var searchItemsForm = persona.searchItemsForm;

                    assertThat(searchItemsForm).isNotNull();

                    searchItemsForm.setSearchTypeName(searchTypeName);
                });

        When("^the user sets the item search's item name or alias to \"([^\"]*)\"$",
                (String itemNameOrAlias) -> {
                    var persona = CurrentPersona.persona;
                    var searchItemsForm = persona.searchItemsForm;

                    assertThat(searchItemsForm).isNotNull();

                    searchItemsForm.setItemNameOrAlias(itemNameOrAlias);
                });

        When("^the user sets the item search's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var searchItemsForm = persona.searchItemsForm;

                    assertThat(searchItemsForm).isNotNull();

                    searchItemsForm.setDescription(description);
                });
    }

}
