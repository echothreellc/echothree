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

import com.echothree.model.control.employee.common.transfer.EmployeeTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EmployeeTypeTransferCache
        extends BaseEmployeeTransferCache<EmployeeType, EmployeeTypeTransfer> {
    
    /** Creates a new instance of EmployeeTypeTransferCache */
    public EmployeeTypeTransferCache(EmployeeControl employeeControl) {
        super(employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public EmployeeTypeTransfer getEmployeeTypeTransfer(EmployeeType employeeType) {
        var employeeTypeTransfer = get(employeeType);
        
        if(employeeTypeTransfer == null) {
            var employeeTypeDetail = employeeType.getLastDetail();
            var employeeTypeName = employeeTypeDetail.getEmployeeTypeName();
            var isDefault = employeeTypeDetail.getIsDefault();
            var sortOrder = employeeTypeDetail.getSortOrder();
            var description = employeeControl.getBestEmployeeTypeDescription(employeeType, getLanguage(userVisit));
            
            employeeTypeTransfer = new EmployeeTypeTransfer(employeeTypeName, isDefault, sortOrder, description);
            put(userVisit, employeeType, employeeTypeTransfer);
        }
        
        return employeeTypeTransfer;
    }
    
}
