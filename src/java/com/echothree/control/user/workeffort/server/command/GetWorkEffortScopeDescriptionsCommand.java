// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.workeffort.common.form.GetWorkEffortScopeDescriptionsForm;
import com.echothree.control.user.workeffort.common.result.GetWorkEffortScopeDescriptionsResult;
import com.echothree.control.user.workeffort.common.result.WorkEffortResultFactory;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWorkEffortScopeDescriptionsCommand
        extends BaseSimpleCommand<GetWorkEffortScopeDescriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetWorkEffortScopeDescriptionsCommand */
    public GetWorkEffortScopeDescriptionsCommand(UserVisitPK userVisitPK, GetWorkEffortScopeDescriptionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = Session.getModelController(WorkEffortControl.class);
        GetWorkEffortScopeDescriptionsResult result = WorkEffortResultFactory.getGetWorkEffortScopeDescriptionsResult();
        String workEffortTypeName = form.getWorkEffortTypeName();
        WorkEffortType workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            String workEffortScopeName = form.getWorkEffortScopeName();
            WorkEffortScope workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName);
            
            if(workEffortScope != null) {
                result.setWorkEffortScope(workEffortControl.getWorkEffortScopeTransfer(getUserVisit(), workEffortScope));
                result.setWorkEffortScopeDescriptions(workEffortControl.getWorkEffortScopeDescriptionTransfers(getUserVisit(), workEffortScope));
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkEffortScopeName.name(), workEffortScopeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
        
        return result;
    }
    
}
