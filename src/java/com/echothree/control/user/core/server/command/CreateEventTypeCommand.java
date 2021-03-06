// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.CreateEventTypeForm;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateEventTypeCommand
        extends BaseSimpleCommand<CreateEventTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EventTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UpdateCreatedTime", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("UpdateModifiedTime", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("UpdateDeletedTime", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("UpdateVisitedTime", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("QueueToSubscribers", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("KeepHistory", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("MaximumHistory", FieldType.UNSIGNED_INTEGER, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEventTypeCommand */
    public CreateEventTypeCommand(UserVisitPK userVisitPK, CreateEventTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        String eventTypeName = form.getEventTypeName();
        EventType eventType = coreControl.getEventTypeByName(eventTypeName);
        
        if(eventType == null) {
            Boolean updateCreatedTime = Boolean.valueOf(form.getUpdateCreatedTime());
            Boolean updateModifiedTime = Boolean.valueOf(form.getUpdateModifiedTime());
            Boolean updateDeletedTime = Boolean.valueOf(form.getUpdateDeletedTime());
            Boolean updateVisitedTime = Boolean.valueOf(form.getUpdateVisitedTime());
            Boolean queueToSubscribers = Boolean.valueOf(form.getQueueToSubscribers());
            Boolean keepHistory = Boolean.valueOf(form.getKeepHistory());
            String maximumHistoryString = form.getMaximumHistory();
            Integer maximumHistory = maximumHistoryString == null? null: Integer.valueOf(maximumHistoryString);
            
            coreControl.createEventType(eventTypeName, updateCreatedTime, updateModifiedTime, updateDeletedTime, updateVisitedTime, queueToSubscribers,
                    keepHistory, maximumHistory);
        } else {
            addExecutionError(ExecutionErrors.DuplicateEventTypeName.name(), eventTypeName);
        }
        
        return null;
    }
    
}
