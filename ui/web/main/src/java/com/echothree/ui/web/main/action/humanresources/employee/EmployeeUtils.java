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

package com.echothree.ui.web.main.action.humanresources.employee;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.model.control.employee.common.transfer.EmployeeTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import javax.naming.NamingException;

public class EmployeeUtils {
    
    private EmployeeUtils() {
        super();
    }
    
    private static class EmployeeUtilsHolder {
        static EmployeeUtils instance = new EmployeeUtils();
    }
    
    public static EmployeeUtils getInstance() {
        return EmployeeUtilsHolder.instance;
    }

    public EmployeeTransfer getEmployee(UserVisitPK userVisitPK, String partyName, String employeeName)
            throws NamingException {
        EmployeeTransfer employee = null;
        var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

        commandForm.setPartyName(partyName);
        commandForm.setEmployeeName(employeeName);

        var commandResult = EmployeeUtil.getHome().getEmployee(userVisitPK, commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEmployeeResult)executionResult.getResult();

            employee = result.getEmployee();
        }

        return employee;
    }

}
