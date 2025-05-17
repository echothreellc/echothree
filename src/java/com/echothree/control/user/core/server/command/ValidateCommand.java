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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.ValidateForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.validation.Validator;
import java.util.List;

public class ValidateCommand
        extends BaseSimpleCommand<ValidateForm> {
    
    /** Creates a new instance of ValidateCommand */
    public ValidateCommand() {
        super(null, null, false);
    }

    protected List<FieldDefinition> getFormFieldDefinitions() {
        return form.getFormFieldDefinitions();
    }

    @Override
    protected ValidationResult validate(Validator validator) {
        return validator.validate(form == null ? null : form.getBaseForm(), getFormFieldDefinitions());
    }

    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getValidateResult();

        result.setBaseForm(form.getBaseForm());

        return result;
    }
    
}
