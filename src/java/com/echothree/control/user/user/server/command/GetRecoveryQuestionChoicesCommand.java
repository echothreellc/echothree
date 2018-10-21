// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.remote.form.GetRecoveryQuestionChoicesForm;
import com.echothree.control.user.user.remote.result.GetRecoveryQuestionChoicesResult;
import com.echothree.control.user.user.remote.result.UserResultFactory;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetRecoveryQuestionChoicesCommand
        extends BaseSimpleCommand<GetRecoveryQuestionChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("DefaultRecoveryQuestionChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetRecoveryQuestionChoicesCommand */
    public GetRecoveryQuestionChoicesCommand(UserVisitPK userVisitPK, GetRecoveryQuestionChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        UserControl userControl = getUserControl();
        GetRecoveryQuestionChoicesResult result = UserResultFactory.getGetRecoveryQuestionChoicesResult();
        String defaultRecoveryQuestionChoice = form.getDefaultRecoveryQuestionChoice();
        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
        
        result.setRecoveryQuestionChoices(userControl.getRecoveryQuestionChoices(defaultRecoveryQuestionChoice,
                getPreferredLanguage(), allowNullChoice));
        
        return result;
    }
    
}
