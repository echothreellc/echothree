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

package com.echothree.service.graphql.internal.invoker;

import com.echothree.control.user.graphql.common.result.ExecuteGraphQlResult;
import com.echothree.control.user.graphql.common.result.GraphQlResultFactory;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.google.gson.JsonParser;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphQlQueryInvokerTest {

    private String response(CommandResult<ExecuteGraphQlResult> commandResult) throws Exception {
        // The separately built servlet classes are available on the runtime classpath.
        var type = Class.forName("com.echothree.service.graphql.internal.invoker.GraphQlQueryInvoker");
        var constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        var method = type.getDeclaredMethod("toJson", CommandResult.class);
        method.setAccessible(true);
        String result;

        try {
            result = (String)method.invoke(constructor.newInstance(), commandResult);
        } catch(InvocationTargetException e) {
            throw (RuntimeException)e.getCause();
        }
        return result;
    }

    private Messages messages(String property, String text) {
        var message = new Message("TestError");
        message.setMessage(text);
        return new Messages().add(property, message);
    }

    @Test
    public void existingResponseIsPreservedEvenWithCommandErrors() throws Exception {
        var result = GraphQlResultFactory.getExecuteGraphQlResult();
        var json = " \n{\"data\":{\"idle\":null},\"errors\":[{\"message\":\"saved\"}]}";
        result.setExecutionResult(json);
        var commandResult = new CommandResult<>(null, null, new ExecutionResult<>(null,
                messages(Messages.EXECUTION_ERROR, "Outer error"), result));
        assertEquals(json, response(commandResult));
    }

    @Test
    public void executionErrorsBecomeGraphQlErrorsWhenJsonIsMissing() throws Exception {
        var result = GraphQlResultFactory.getExecuteGraphQlResult();
        var text = "Invalid JSON: \"quoted\"\n100% invalid";
        var commandResult = new CommandResult<>(null, null, new ExecutionResult<>(
                messages(Messages.EXECUTION_WARNING, "Warning"), messages(Messages.EXECUTION_ERROR, text), result));
        var json = JsonParser.parseString(response(commandResult)).getAsJsonObject();
        assertEquals(1, json.getAsJsonArray("errors").size());
        assertEquals(text, json.getAsJsonArray("errors").get(0).getAsJsonObject().get("message").getAsString());
        assertFalse(json.has("data"));
        assertFalse(json.has("extensions"));
    }

    @Test
    public void jsonParsingDetailsAreSanitizedAndClassifiedAsInvalidSyntax() throws Exception {
        var message = new Message(ExecutionErrors.JsonParseError.name(), "private parser details");
        message.setMessage("com.google.gson.stream.MalformedJsonException: secret value at path $.private "
                + "https://github.com/google/gson/blob/main/Troubleshooting.md#malformed-json");
        var errors = new Messages().add(Messages.EXECUTION_ERROR, message);
        var result = GraphQlResultFactory.getExecuteGraphQlResult();
        var response = response(new CommandResult<>(null, null, new ExecutionResult<>(null, errors, result)));
        var json = JsonParser.parseString(response).getAsJsonObject();
        var error = json.getAsJsonArray("errors").get(0).getAsJsonObject();

        assertEquals("Invalid JSON in the request. Check for trailing commas or missing property names.",
                error.get("message").getAsString());
        assertEquals("InvalidSyntax", error.getAsJsonObject("extensions").get("classification").getAsString());
        assertFalse(response.contains("gson"));
        assertFalse(response.contains("secret"));
        assertFalse(response.contains("private"));
        assertFalse(response.contains("github"));
        assertFalse(json.has("data"));
    }

    @Test
    public void securityAndValidationErrorsWorkWithoutAResult() throws Exception {
        var security = new SecurityResult(messages(Messages.SECURITY_MESSAGE, "Access denied"));
        var validation = new ValidationResult(messages("Query", "Query is required"));
        var json = JsonParser.parseString(response(new CommandResult<>(security, validation, null))).getAsJsonObject();
        var errors = json.getAsJsonArray("errors");
        assertEquals(2, errors.size());
        assertEquals("Access denied", errors.get(0).getAsJsonObject().get("message").getAsString());
        assertEquals("Query is required", errors.get(1).getAsJsonObject().get("message").getAsString());
        assertFalse(json.has("data"));
    }

    @Test
    public void missingResponseWithoutErrorsStillSignalsAnInternalFailure() {
        assertThrows(IllegalStateException.class, () -> response(new CommandResult<>(null, null, null)));
        var result = GraphQlResultFactory.getExecuteGraphQlResult();
        assertThrows(IllegalStateException.class, () -> response(new CommandResult<>(null, null,
                new ExecutionResult<>(messages(Messages.EXECUTION_WARNING, "Warning"), null, result))));
    }
}
