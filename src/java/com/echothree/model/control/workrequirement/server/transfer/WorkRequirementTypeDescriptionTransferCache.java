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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTypeDescriptionTransfer;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementTypeDescription;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkRequirementTypeDescriptionTransferCache
        extends BaseWorkRequirementDescriptionTransferCache<WorkRequirementTypeDescription, WorkRequirementTypeDescriptionTransfer> {

    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);

    /** Creates a new instance of WorkRequirementTypeDescriptionTransferCache */
    public WorkRequirementTypeDescriptionTransferCache() {
        super();
    }
    
    public WorkRequirementTypeDescriptionTransfer getWorkRequirementTypeDescriptionTransfer(UserVisit userVisit, WorkRequirementTypeDescription workRequirementTypeDescription) {
        var workRequirementTypeDescriptionTransfer = get(workRequirementTypeDescription);
        
        if(workRequirementTypeDescriptionTransfer == null) {
            var workRequirementTypeTransfer = workRequirementControl.getWorkRequirementTypeTransfer(userVisit, workRequirementTypeDescription.getWorkRequirementType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, workRequirementTypeDescription.getLanguage());
            var description = workRequirementTypeDescription.getDescription();
            
            workRequirementTypeDescriptionTransfer = new WorkRequirementTypeDescriptionTransfer(languageTransfer, workRequirementTypeTransfer,
                    description);
            put(userVisit, workRequirementTypeDescription, workRequirementTypeDescriptionTransfer);
        }
        
        return workRequirementTypeDescriptionTransfer;
    }
    
}
