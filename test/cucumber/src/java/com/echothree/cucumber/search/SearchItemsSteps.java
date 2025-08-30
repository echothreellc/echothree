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

        When("^the user begins recording an action on a result of the item search$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemSearchResultActionForm).isNull();

                    persona.createItemSearchResultActionForm = SearchUtil.getHome().getCreateItemSearchResultActionForm();
                });

        When("^the user records an action on a result of the item search$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemSearchResultActionForm = persona.createItemSearchResultActionForm;

                    assertThat(createItemSearchResultActionForm).isNotNull();

                    var commandResult = SearchUtil.getHome().createItemSearchResultAction(persona.userVisitPK, createItemSearchResultActionForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.createItemSearchResultActionForm = null;
                });

        When("^the user begins clearing the item results$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.clearItemResultsForm).isNull();

                    persona.clearItemResultsForm = SearchUtil.getHome().getClearItemResultsForm();
                });

        When("^the user clears the item results$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var clearItemResultsForm = persona.clearItemResultsForm;

                    assertThat(clearItemResultsForm).isNotNull();

                    var commandResult = SearchUtil.getHome().clearItemResults(persona.userVisitPK, clearItemResultsForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.clearItemResultsForm = null;
                });

        And("^the user's search results contain \"([^\"]*)\" result(s|)$",
                (Long itemSearchCount, String discard1) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.lastSearchItemsCount).isEqualTo(itemSearchCount);
                });

        And("^the user sets the item search's search type to \"([^\"]*)\"$",
                (String searchTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var searchItemsForm = persona.searchItemsForm;

                    assertThat(searchItemsForm).isNotNull();

                    searchItemsForm.setSearchTypeName(searchTypeName);
                });

        And("^the user sets the item search's item name or alias to \"([^\"]*)\"$",
                (String itemNameOrAlias) -> {
                    var persona = CurrentPersona.persona;
                    var searchItemsForm = persona.searchItemsForm;

                    assertThat(searchItemsForm).isNotNull();

                    searchItemsForm.setItemNameOrAlias(itemNameOrAlias);
                });

        And("^the user sets the item search's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var searchItemsForm = persona.searchItemsForm;

                    assertThat(searchItemsForm).isNotNull();

                    searchItemsForm.setDescription(description);
                });

        And("^the user sets the item search result action's search type to \"([^\"]*)\"$",
                (String searchTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemSearchResultActionForm = persona.createItemSearchResultActionForm;

                    assertThat(createItemSearchResultActionForm).isNotNull();

                    createItemSearchResultActionForm.setSearchTypeName(searchTypeName);
                });

        And("^the user sets the item search result action's action to \"([^\"]*)\"$",
                (String searchResultActionTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemSearchResultActionForm = persona.createItemSearchResultActionForm;

                    assertThat(createItemSearchResultActionForm).isNotNull();

                    createItemSearchResultActionForm.setSearchResultActionTypeName(searchResultActionTypeName);
                });

        And("^the user sets the item search result action's item to \"([^\"]*)\"$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemSearchResultActionForm = persona.createItemSearchResultActionForm;

                    assertThat(createItemSearchResultActionForm).isNotNull();

                    createItemSearchResultActionForm.setItemName(itemName);
                });

        And("^the user sets the clear item results' search type to \"([^\"]*)\"$",
                (String searchTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var clearItemResultsForm = persona.clearItemResultsForm;

                    assertThat(clearItemResultsForm).isNotNull();

                    clearItemResultsForm.setSearchTypeName(searchTypeName);
                });

    }

}
