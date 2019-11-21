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
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import static org.assertj.core.api.Assertions.assertThat;

public class LastCommandResult {

    private Scenario scenario;
    public static CommandResult commandResult;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Then("^no error should occur$")
    public void noErrorShouldOccur() {
//        if(commandResult.hasErrors()) {
//            var executionResult = commandResult.getExecutionResult();
//            var executionErrors = executionResult.getExecutionErrors().get();
//
//            while(executionErrors.hasNext()) {
//                var message = executionErrors.next();
//
//                scenario.write(message.getKey() + ": " + message.getMessage());
//            }
//        }

        assertThat(commandResult.hasErrors()).isFalse();
    }

    @Then("^an error should occur$")
    public void anErrorShouldOccur() {
        assertThat(commandResult.hasErrors()).isTrue();
    }

    @Then("^no validation error should occur$")
    public void noValidationErrorShouldOccur() {
        assertThat(commandResult.hasValidationErrors()).isFalse();
    }

    @Then("^a validation error should occur$")
    public void aValidationErrorShouldOccur() {
        assertThat(commandResult.hasValidationErrors()).isTrue();
    }

    @Then("^no execution warning should occur$")
    public void noExecutionWarningShouldOccur() {
        assertThat(commandResult.hasExecutionWarnings()).isFalse();
    }

    @Then("^an execution warning should occur$")
    public void anExecutionWarningShouldOccur() {
        assertThat(commandResult.hasExecutionWarnings()).isTrue();
    }

    @Then("^no execution error should occur$")
    public void noExecutionErrorShouldOccur() {
        assertThat(commandResult.hasExecutionErrors()).isFalse();
    }

    @Then("^an execution error should occur$")
    public void anExecutionErrorShouldOccur() {
        assertThat(commandResult.hasExecutionErrors()).isTrue();
    }

}
