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

package com.echothree.model.control.workeffort.server.transfer;

import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeDescriptionTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortTypeDescription;

public class WorkEffortTypeDescriptionTransferCache
        extends BaseWorkEffortDescriptionTransferCache<WorkEffortTypeDescription, WorkEffortTypeDescriptionTransfer> {
    
    /** Creates a new instance of WorkEffortTypeDescriptionTransferCache */
    public WorkEffortTypeDescriptionTransferCache(UserVisit userVisit, WorkEffortControl workEffortControl) {
        super(userVisit, workEffortControl);
    }
    
    public WorkEffortTypeDescriptionTransfer getWorkEffortTypeDescriptionTransfer(WorkEffortTypeDescription workEffortTypeDescription) {
        var workEffortTypeDescriptionTransfer = get(workEffortTypeDescription);
        
        if(workEffortTypeDescriptionTransfer == null) {
            var workEffortTypeTransfer = workEffortControl.getWorkEffortTypeTransfer(userVisit, workEffortTypeDescription.getWorkEffortType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, workEffortTypeDescription.getLanguage());
            var description = workEffortTypeDescription.getDescription();
            
            workEffortTypeDescriptionTransfer = new WorkEffortTypeDescriptionTransfer(languageTransfer, workEffortTypeTransfer,
                    description);
            put(userVisit, workEffortTypeDescription, workEffortTypeDescriptionTransfer);
        }
        
        return workEffortTypeDescriptionTransfer;
    }
    
}
