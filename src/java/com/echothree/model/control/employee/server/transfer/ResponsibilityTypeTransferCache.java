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

import com.echothree.model.control.employee.common.transfer.ResponsibilityTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ResponsibilityTypeTransferCache
        extends BaseEmployeeTransferCache<ResponsibilityType, ResponsibilityTypeTransfer> {

    EmployeeControl employeeControl = Session.getModelController(EmployeeControl.class);

    /** Creates a new instance of ResponsibilityTypeTransferCache */
    protected ResponsibilityTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public ResponsibilityTypeTransfer getResponsibilityTypeTransfer(UserVisit userVisit, ResponsibilityType responsibilityType) {
        var responsibilityTypeTransfer = get(responsibilityType);
        
        if(responsibilityTypeTransfer == null) {
            var responsibilityTypeDetail = responsibilityType.getLastDetail();
            var responsibilityTypeName = responsibilityTypeDetail.getResponsibilityTypeName();
            var isDefault = responsibilityTypeDetail.getIsDefault();
            var sortOrder = responsibilityTypeDetail.getSortOrder();
            var description = employeeControl.getBestResponsibilityTypeDescription(responsibilityType, getLanguage(userVisit));
            
            responsibilityTypeTransfer = new ResponsibilityTypeTransfer(responsibilityTypeName, isDefault, sortOrder, description);
            put(userVisit, responsibilityType, responsibilityTypeTransfer);
        }
        
        return responsibilityTypeTransfer;
    }
    
}
