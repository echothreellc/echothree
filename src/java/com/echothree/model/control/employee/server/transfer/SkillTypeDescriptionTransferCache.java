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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.common.transfer.SkillTypeDescriptionTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.SkillTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SkillTypeDescriptionTransferCache
        extends BaseEmployeeDescriptionTransferCache<SkillTypeDescription, SkillTypeDescriptionTransfer> {
    
    /** Creates a new instance of SkillTypeDescriptionTransferCache */
    public SkillTypeDescriptionTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
    }
    
    public SkillTypeDescriptionTransfer getSkillTypeDescriptionTransfer(SkillTypeDescription skillTypeDescription) {
        var skillTypeDescriptionTransfer = get(skillTypeDescription);
        
        if(skillTypeDescriptionTransfer == null) {
            var skillTypeTransfer = employeeControl.getSkillTypeTransfer(userVisit,
                    skillTypeDescription.getSkillType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, skillTypeDescription.getLanguage());
            
            skillTypeDescriptionTransfer = new SkillTypeDescriptionTransfer(languageTransfer, skillTypeTransfer, skillTypeDescription.getDescription());
            put(skillTypeDescription, skillTypeDescriptionTransfer);
        }
        
        return skillTypeDescriptionTransfer;
    }
    
}
