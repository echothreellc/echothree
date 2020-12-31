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

import com.echothree.model.control.employee.common.transfer.EmployeeTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EmployeeTypeTransferCache
        extends BaseEmployeeTransferCache<EmployeeType, EmployeeTypeTransfer> {
    
    /** Creates a new instance of EmployeeTypeTransferCache */
    public EmployeeTypeTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public EmployeeTypeTransfer getEmployeeTypeTransfer(EmployeeType employeeType) {
        EmployeeTypeTransfer employeeTypeTransfer = get(employeeType);
        
        if(employeeTypeTransfer == null) {
            EmployeeTypeDetail employeeTypeDetail = employeeType.getLastDetail();
            String employeeTypeName = employeeTypeDetail.getEmployeeTypeName();
            Boolean isDefault = employeeTypeDetail.getIsDefault();
            Integer sortOrder = employeeTypeDetail.getSortOrder();
            String description = employeeControl.getBestEmployeeTypeDescription(employeeType, getLanguage());
            
            employeeTypeTransfer = new EmployeeTypeTransfer(employeeTypeName, isDefault, sortOrder, description);
            put(employeeType, employeeTypeTransfer);
        }
        
        return employeeTypeTransfer;
    }
    
}
