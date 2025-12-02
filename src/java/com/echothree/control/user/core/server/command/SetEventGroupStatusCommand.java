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

import com.echothree.control.user.core.common.form.SetEventGroupStatusForm;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SetEventGroupStatusCommand
        extends BaseSimpleCommand<SetEventGroupStatusForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("EventGroupName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EventGroupStatusChoice", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetEventGroupStatusCommand */
    public SetEventGroupStatusCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var eventControl = Session.getModelController(EventControl.class);
        var eventGroupName = form.getEventGroupName();
        var eventGroup = eventControl.getEventGroupByName(eventGroupName);
        
        if(eventGroup != null) {
            var eventGroupStatusChoice = form.getEventGroupStatusChoice();

            eventControl.setEventGroupStatus(this, eventGroup, eventGroupStatusChoice, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownEventGroupName.name(), eventGroupName);
        }
        
        return null;
    }
    
}
