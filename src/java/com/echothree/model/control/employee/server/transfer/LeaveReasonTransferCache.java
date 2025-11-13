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

import com.echothree.model.control.employee.common.transfer.LeaveReasonTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LeaveReasonTransferCache
        extends BaseEmployeeTransferCache<LeaveReason, LeaveReasonTransfer> {
    
    /** Creates a new instance of LeaveReasonTransferCache */
    public LeaveReasonTransferCache(EmployeeControl employeeControl) {
        super(employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LeaveReasonTransfer getLeaveReasonTransfer(LeaveReason leaveReason) {
        var leaveReasonTransfer = get(leaveReason);
        
        if(leaveReasonTransfer == null) {
            var leaveReasonDetail = leaveReason.getLastDetail();
            var leaveReasonName = leaveReasonDetail.getLeaveReasonName();
            var isDefault = leaveReasonDetail.getIsDefault();
            var sortOrder = leaveReasonDetail.getSortOrder();
            var description = employeeControl.getBestLeaveReasonDescription(leaveReason, getLanguage(userVisit));
            
            leaveReasonTransfer = new LeaveReasonTransfer(leaveReasonName, isDefault, sortOrder, description);
            put(userVisit, leaveReason, leaveReasonTransfer);
        }
        
        return leaveReasonTransfer;
    }
    
}
