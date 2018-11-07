// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.employee.common.transfer.TerminationReasonTransfer;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.data.employee.server.entity.TerminationReason;
import com.echothree.model.data.employee.server.entity.TerminationReasonDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TerminationReasonTransferCache
        extends BaseEmployeeTransferCache<TerminationReason, TerminationReasonTransfer> {
    
    /** Creates a new instance of TerminationReasonTransferCache */
    public TerminationReasonTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public TerminationReasonTransfer getTerminationReasonTransfer(TerminationReason terminationReason) {
        TerminationReasonTransfer terminationReasonTransfer = get(terminationReason);
        
        if(terminationReasonTransfer == null) {
            TerminationReasonDetail terminationReasonDetail = terminationReason.getLastDetail();
            String terminationReasonName = terminationReasonDetail.getTerminationReasonName();
            Boolean isDefault = terminationReasonDetail.getIsDefault();
            Integer sortOrder = terminationReasonDetail.getSortOrder();
            String description = employeeControl.getBestTerminationReasonDescription(terminationReason, getLanguage());
            
            terminationReasonTransfer = new TerminationReasonTransfer(terminationReasonName, isDefault, sortOrder, description);
            put(terminationReason, terminationReasonTransfer);
        }
        
        return terminationReasonTransfer;
    }
    
}
