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

package com.echothree.util.common.form;

import com.echothree.util.common.message.Messages;
import java.io.Serializable;

public class ValidationResult
        implements Serializable {

    private Messages validationMessages;

    /** Creates a new instance of ValidationResult.
     */
    /** Creates a new instance of ValidationResult */
    public ValidationResult(Messages validationMessages) {
        this.validationMessages = validationMessages;
    }

    public boolean getHasErrors() {
        return validationMessages == null ? false : !validationMessages.isEmpty();
    }

    public Messages getValidationMessages() {
        return validationMessages;
    }

    @Override
    public String toString() {
        return "{ validationMessages = " + validationMessages + " }";
    }

}
