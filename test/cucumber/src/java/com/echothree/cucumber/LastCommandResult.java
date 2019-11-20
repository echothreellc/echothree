// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.cucumber;

import com.echothree.util.common.command.CommandResult;
import cucumber.api.java.en.Then;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class LastCommandResult {

    public static CommandResult commandResult;

    @Then("^no error should occur$")
    public void noErrorShouldOccur() {
        assertThat(commandResult.hasErrors()).isFalse();
    }

    @Then("^an error should occur$")
    public void anErrorShouldOccur() {
        assertThat(commandResult.hasErrors()).isTrue();
    }

}
