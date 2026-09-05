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

package com.echothree.control.user.workeffort.server.command;

import com.echothree.control.user.workeffort.common.form.GetWorkEffortTypeForm;
import com.echothree.control.user.workeffort.common.result.WorkEffortResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetWorkEffortTypeCommand
        extends BaseSingleEntityCommand<WorkEffortType, GetWorkEffortTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    WorkEffortControl workEffortControl;

    /** Creates a new instance of GetWorkEffortTypeCommand */
    public GetWorkEffortTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected WorkEffortType getEntity() {
        var workEffortTypeName = form.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            sendEvent(workEffortType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }

        return workEffortType;
    }

    @Override
    protected BaseResult getResult(WorkEffortType workEffortType) {
        var result = WorkEffortResultFactory.getGetWorkEffortTypeResult();

        if(workEffortType != null) {
            result.setWorkEffortType(workEffortControl.getWorkEffortTypeTransfer(getUserVisit(), workEffortType));
        }
        
        return result;
    }
    
}
