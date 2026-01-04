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

import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import java.io.Serializable;

public class ExecutionResult
        implements Serializable {
    
    private Messages executionWarnings;
    private Messages executionErrors;
    private BaseResult result;
    
    /** Creates a new instance of ExecutionResult */
    public ExecutionResult(Messages executionWarnings, Messages executionErrors, BaseResult result) {
        this.executionWarnings = executionWarnings;
        this.executionErrors = executionErrors;
        this.result = result;
    }
    
    public Messages getExecutionWarnings() {
        return executionWarnings;
    }
    
    public Messages getExecutionErrors() {
        return executionErrors;
    }
    
    public Boolean getHasLockErrors() {
        return executionErrors == null ? false
                : executionErrors.containsKeys(Messages.EXECUTION_ERROR, ExecutionErrors.EntityLockFailed.name(), ExecutionErrors.EntityLockStale.name());
    }
    
    public Boolean getHasWarnings() {
        return executionWarnings == null? false: executionWarnings.size(Messages.EXECUTION_WARNING) != 0;
    }
    
    public Boolean getHasErrors() {
        return executionErrors == null? false: executionErrors.size(Messages.EXECUTION_ERROR) != 0;
    }
    
    public Boolean getHasResults() {
        return result != null;
    }
    
    public BaseResult getResult() {
        return result;
    }
    
    @Override
    public String toString() {
        return "{ executionWarnings = " + executionWarnings + ", executionErrors = " +
                executionErrors + ", result = " + result + " }";
    }
    
}
