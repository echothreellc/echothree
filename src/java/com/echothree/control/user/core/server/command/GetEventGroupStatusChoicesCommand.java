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

import com.echothree.control.user.core.common.form.GetEventGroupStatusChoicesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEventGroupStatusChoicesCommand
        extends BaseSimpleCommand<GetEventGroupStatusChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("EventGroupName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("DefaultEventGroupStatusChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetEventGroupStatusChoicesCommand */
    public GetEventGroupStatusChoicesCommand(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        var result = CoreResultFactory.getGetEventGroupStatusChoicesResult();
        var eventGroupName = form.getEventGroupName();
        var eventGroup = coreControl.getEventGroupByName(eventGroupName);
        
        if(eventGroupName == null || eventGroup != null) {
            var defaultEventGroupStatusChoice = form.getDefaultEventGroupStatusChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setEventGroupStatusChoices(coreControl.getEventGroupStatusChoices(defaultEventGroupStatusChoice,
                    getPreferredLanguage(), allowNullChoice, eventGroup, getPartyPK()));
        } else {
            addExecutionError(ExecutionErrors.UnknownEventGroupName.name(), eventGroupName);
        }
        
        return result;
    }
    
}
