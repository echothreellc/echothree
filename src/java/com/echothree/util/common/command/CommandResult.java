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

package com.echothree.util.common.command;

import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.Messages;
import java.io.Serializable;

public class CommandResult
        implements Serializable {

    SecurityResult securityResult;
    ValidationResult validationResult;
    ExecutionResult executionResult;

    /** Creates a new instance of CommandResult */
    public CommandResult(SecurityResult securityResult, ValidationResult validationResult, ExecutionResult executionResult) {
        this.securityResult = securityResult;
        this.validationResult = validationResult;
        this.executionResult = executionResult;
    }

    public boolean hasSecurityMessages() {
        return securityResult == null ? false : securityResult.getHasMessages();
    }

    public boolean hasValidationErrors() {
        return validationResult == null ? false : validationResult.getHasErrors();
    }

    public boolean hasExecutionWarnings() {
        return executionResult == null ? false : executionResult.getHasWarnings();
    }

    public boolean hasExecutionErrors() {
        return executionResult == null ? false : executionResult.getHasErrors();
    }

    public boolean hasErrors() {
        return hasSecurityMessages() || hasValidationErrors() || hasExecutionErrors();
    }

    public Boolean getHasErrors() {
        return hasErrors();
    }

    public boolean hasWarnings() {
        return hasExecutionWarnings();
    }

    public Boolean getHasWarnings() {
        return hasWarnings();
    }

    public SecurityResult getSecurityResult() {
        return securityResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public ExecutionResult getExecutionResult() {
        return executionResult;
    }

    public boolean containsSecurityMessage(String key) {
        boolean result;

        if(securityResult != null) {
            var securityMessages = securityResult.getSecurityMessages();

            if(securityMessages != null) {
                result = securityMessages.containsKey(Messages.SECURITY_MESSAGE, key);
            } else {
                result = false;
            }
        } else {
            result = false;
        }

        return result;
    }

    public boolean containsExecutionWarning(String key) {
        boolean result;

        if(executionResult != null) {
            var executionWarnings = executionResult.getExecutionWarnings();

            if(executionWarnings != null) {
                result = executionWarnings.containsKey(Messages.EXECUTION_WARNING, key);
            } else {
                result = false;
            }
        } else {
            result = false;
        }

        return result;
    }

    public boolean containsExecutionError(String key) {
        boolean result;

        if(executionResult != null) {
            var executionErrors = executionResult.getExecutionErrors();

            if(executionErrors != null) {
                result = executionErrors.containsKey(Messages.EXECUTION_ERROR, key);
            } else {
                result = false;
            }
        } else {
            result = false;
        }

        return result;
    }

    @Override
    public String toString() {
        return "{ securityResult = " + securityResult + ", validationResult = " + validationResult + ", executionResult = " + executionResult + " }";
    }

}
