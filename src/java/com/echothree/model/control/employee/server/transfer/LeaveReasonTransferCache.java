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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.common.transfer.LeaveReasonTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveReasonDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LeaveReasonTransferCache
        extends BaseEmployeeTransferCache<LeaveReason, LeaveReasonTransfer> {
    
    /** Creates a new instance of LeaveReasonTransferCache */
    public LeaveReasonTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LeaveReasonTransfer getLeaveReasonTransfer(LeaveReason leaveReason) {
        LeaveReasonTransfer leaveReasonTransfer = get(leaveReason);
        
        if(leaveReasonTransfer == null) {
            LeaveReasonDetail leaveReasonDetail = leaveReason.getLastDetail();
            String leaveReasonName = leaveReasonDetail.getLeaveReasonName();
            Boolean isDefault = leaveReasonDetail.getIsDefault();
            Integer sortOrder = leaveReasonDetail.getSortOrder();
            String description = employeeControl.getBestLeaveReasonDescription(leaveReason, getLanguage());
            
            leaveReasonTransfer = new LeaveReasonTransfer(leaveReasonName, isDefault, sortOrder, description);
            put(leaveReason, leaveReasonTransfer);
        }
        
        return leaveReasonTransfer;
    }
    
}
