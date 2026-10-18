// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.employee.server.logic;

import com.echothree.model.control.employee.common.exception.UnknownEmployeeTypeNameException;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmployeeTypeLogic
        extends BaseLogic {

    @Inject
    EmployeeControl employeeControl;

    protected EmployeeTypeLogic() {
        super();
    }
    
    public EmployeeType getEmployeeTypeByName(final ExecutionErrorAccumulator eea, final String employeeTypeName,
            final EntityPermission entityPermission) {
        var employeeType = employeeControl.getEmployeeTypeByName(employeeTypeName, entityPermission);

        if(employeeType == null) {
            handleExecutionError(UnknownEmployeeTypeNameException.class, eea, ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
        }

        return employeeType;
    }
    
    public EmployeeType getEmployeeTypeByName(final ExecutionErrorAccumulator eea, final String employeeTypeName) {
        return getEmployeeTypeByName(eea, employeeTypeName, EntityPermission.READ_ONLY);
    }

    public EmployeeType getEmployeeTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String employeeTypeName) {
        return getEmployeeTypeByName(eea, employeeTypeName, EntityPermission.READ_WRITE);
    }

}
