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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.common.transfer.LeaveTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.entity.LeaveTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LeaveTypeTransferCache
        extends BaseEmployeeTransferCache<LeaveType, LeaveTypeTransfer> {
    
    /** Creates a new instance of LeaveTypeTransferCache */
    public LeaveTypeTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LeaveTypeTransfer getLeaveTypeTransfer(LeaveType leaveType) {
        LeaveTypeTransfer leaveTypeTransfer = get(leaveType);
        
        if(leaveTypeTransfer == null) {
            LeaveTypeDetail leaveTypeDetail = leaveType.getLastDetail();
            String leaveTypeName = leaveTypeDetail.getLeaveTypeName();
            Boolean isDefault = leaveTypeDetail.getIsDefault();
            Integer sortOrder = leaveTypeDetail.getSortOrder();
            String description = employeeControl.getBestLeaveTypeDescription(leaveType, getLanguage());
            
            leaveTypeTransfer = new LeaveTypeTransfer(leaveTypeName, isDefault, sortOrder, description);
            put(leaveType, leaveTypeTransfer);
        }
        
        return leaveTypeTransfer;
    }
    
}
