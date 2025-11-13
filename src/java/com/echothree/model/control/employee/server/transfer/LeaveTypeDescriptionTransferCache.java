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

import com.echothree.model.control.employee.common.transfer.LeaveTypeDescriptionTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.LeaveTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LeaveTypeDescriptionTransferCache
        extends BaseEmployeeDescriptionTransferCache<LeaveTypeDescription, LeaveTypeDescriptionTransfer> {
    
    /** Creates a new instance of LeaveTypeDescriptionTransferCache */
    public LeaveTypeDescriptionTransferCache(EmployeeControl employeeControl) {
        super(employeeControl);
    }
    
    public LeaveTypeDescriptionTransfer getLeaveTypeDescriptionTransfer(UserVisit userVisit, LeaveTypeDescription leaveTypeDescription) {
        var leaveTypeDescriptionTransfer = get(leaveTypeDescription);
        
        if(leaveTypeDescriptionTransfer == null) {
            var leaveTypeTransfer = employeeControl.getLeaveTypeTransfer(userVisit,
                    leaveTypeDescription.getLeaveType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, leaveTypeDescription.getLanguage());
            
            leaveTypeDescriptionTransfer = new LeaveTypeDescriptionTransfer(languageTransfer, leaveTypeTransfer, leaveTypeDescription.getDescription());
            put(userVisit, leaveTypeDescription, leaveTypeDescriptionTransfer);
        }
        
        return leaveTypeDescriptionTransfer;
    }
    
}
