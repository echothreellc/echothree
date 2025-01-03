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

import com.echothree.model.control.employee.common.transfer.SkillTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SkillTypeTransferCache
        extends BaseEmployeeTransferCache<SkillType, SkillTypeTransfer> {
    
    /** Creates a new instance of SkillTypeTransferCache */
    public SkillTypeTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SkillTypeTransfer getSkillTypeTransfer(SkillType skillType) {
        var skillTypeTransfer = get(skillType);
        
        if(skillTypeTransfer == null) {
            var skillTypeDetail = skillType.getLastDetail();
            var skillTypeName = skillTypeDetail.getSkillTypeName();
            var isDefault = skillTypeDetail.getIsDefault();
            var sortOrder = skillTypeDetail.getSortOrder();
            var description = employeeControl.getBestSkillTypeDescription(skillType, getLanguage());
            
            skillTypeTransfer = new SkillTypeTransfer(skillTypeName, isDefault, sortOrder, description);
            put(skillType, skillTypeTransfer);
        }
        
        return skillTypeTransfer;
    }
    
}
