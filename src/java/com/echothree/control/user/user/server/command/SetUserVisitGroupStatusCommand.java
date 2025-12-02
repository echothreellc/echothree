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

import com.echothree.control.user.user.common.form.SetUserVisitGroupStatusForm;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SetUserVisitGroupStatusCommand
        extends BaseSimpleCommand<SetUserVisitGroupStatusForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("UserVisitGroupName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UserVisitGroupStatusChoice", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetUserVisitGroupStatusCommand */
    public SetUserVisitGroupStatusCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var userVisitGroupName = form.getUserVisitGroupName();
        var userVisitGroup = userControl.getUserVisitGroupByName(userVisitGroupName);
        
        if(userVisitGroup != null) {
            var userVisitGroupStatusChoice = form.getUserVisitGroupStatusChoice();
            
            userControl.setUserVisitGroupStatus(this, userVisitGroup, userVisitGroupStatusChoice, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownUserVisitGroupName.name(), userVisitGroupName);
        }
        
        return null;
    }
    
}
