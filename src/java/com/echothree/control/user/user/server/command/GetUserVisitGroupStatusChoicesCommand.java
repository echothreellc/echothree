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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.GetUserVisitGroupStatusChoicesForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetUserVisitGroupStatusChoicesCommand
        extends BaseSimpleCommand<GetUserVisitGroupStatusChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UserVisitGroupName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("DefaultUserVisitGroupStatusChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetUserVisitGroupStatusChoicesCommand */
    public GetUserVisitGroupStatusChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var result = UserResultFactory.getGetUserVisitGroupStatusChoicesResult();
        var userVisitGroupName = form.getUserVisitGroupName();
        var userVisitGroup = userControl.getUserVisitGroupByName(userVisitGroupName);
        
        if(userVisitGroupName == null || userVisitGroup != null) {
            var defaultUserVisitGroupStatusChoice = form.getDefaultUserVisitGroupStatusChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setUserVisitGroupStatusChoices(userControl.getUserVisitGroupStatusChoices(defaultUserVisitGroupStatusChoice,
                    getPreferredLanguage(), allowNullChoice, userVisitGroup, getPartyPK()));
        } else {
            addExecutionError(ExecutionErrors.UnknownUserVisitGroupName.name(), userVisitGroupName);
        }
        
        return result;
    }
    
}
