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

package com.echothree.model.control.workeffort.server.transfer;

import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeDescriptionTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScopeDescription;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkEffortScopeDescriptionTransferCache
        extends BaseWorkEffortDescriptionTransferCache<WorkEffortScopeDescription, WorkEffortScopeDescriptionTransfer> {

    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);

    /** Creates a new instance of WorkEffortScopeDescriptionTransferCache */
    protected WorkEffortScopeDescriptionTransferCache() {
        super();
    }
    
    public WorkEffortScopeDescriptionTransfer getWorkEffortScopeDescriptionTransfer(UserVisit userVisit, WorkEffortScopeDescription workEffortScopeDescription) {
        var workEffortScopeDescriptionTransfer = get(workEffortScopeDescription);
        
        if(workEffortScopeDescriptionTransfer == null) {
            var workEffortScopeTransfer = workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortScopeDescription.getWorkEffortScope());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, workEffortScopeDescription.getLanguage());
            var description = workEffortScopeDescription.getDescription();
            
            workEffortScopeDescriptionTransfer = new WorkEffortScopeDescriptionTransfer(languageTransfer, workEffortScopeTransfer,
                    description);
            put(userVisit, workEffortScopeDescription, workEffortScopeDescriptionTransfer);
        }
        
        return workEffortScopeDescriptionTransfer;
    }
    
}
