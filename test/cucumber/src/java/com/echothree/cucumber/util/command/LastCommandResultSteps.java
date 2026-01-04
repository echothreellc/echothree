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

package com.echothree.cucumber.util.command;

import com.echothree.util.common.message.Messages;
import io.cucumber.java8.En;
import io.cucumber.java8.Scenario;
import static org.assertj.core.api.Assertions.assertThat;

public class LastCommandResultSteps implements En {

    private Scenario scenario;

    public LastCommandResultSteps() {
        Before((Scenario scenario) -> this.scenario = scenario);

        Then("^no error should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();

            if(commandResult.hasErrors()) {
                if(commandResult.hasSecurityMessages()) {
                    var securityResult = commandResult.getSecurityResult();
                    var securityErrors = securityResult.getSecurityMessages().get();

                    while(securityErrors.hasNext()) {
                        var message = securityErrors.next();

                        scenario.log(message.getKey() + ": " + message.getMessage());
                    }
                }
                
                if(commandResult.hasValidationErrors()) {
                    var validationResult = commandResult.getValidationResult();
                    var validationErrors = validationResult.getValidationMessages().get();

                    while(validationErrors.hasNext()) {
                        var message = validationErrors.next();

                        scenario.log(message.getKey() + ": " + message.getMessage());
                    }
                }
                
                if(commandResult.hasExecutionErrors()) {
                    var executionResult = commandResult.getExecutionResult();
                    var executionErrors = executionResult.getExecutionErrors().get();

                    while(executionErrors.hasNext()) {
                        var message = executionErrors.next();

                        scenario.log(message.getKey() + ": " + message.getMessage());
                    }
                }
            }

            assertThat(commandResult.hasErrors()).isFalse();
        });

        Then("^an error should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasErrors()).isTrue();
        });

        Then("^no validation error should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasValidationErrors()).isFalse();
        });

        Then("^a validation error should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasValidationErrors()).isTrue();
        });

        Then("^no execution warning should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasExecutionWarnings()).isFalse();
        });

        Then("^an execution warning should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasExecutionWarnings()).isTrue();
        });

        Then("^no execution error should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasExecutionErrors()).isFalse();
        });

        Then("^an execution error should occur$", () -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasExecutionErrors()).isTrue();
        });

        Then("^the execution error ([a-zA-Z0-9-_]*) should occur$", (String executionError) -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult).isNotNull();
            assertThat(commandResult.hasExecutionErrors()).isTrue();

            var executionResult = commandResult.getExecutionResult();
            var executionErrors = executionResult.getExecutionErrors();
            executionErrors.containsKey(Messages.EXECUTION_ERROR, executionError);
        });
    }

}
