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

package com.echothree.model.control.employee.common.transfer;

import com.echothree.model.control.employee.common.transfer.EmployeeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class EmployeeResultTransfer
        extends BaseTransfer {
    
    private String partyName;
    private EmployeeTransfer employee;
    
    /** Creates a new instance of EmployeeResultTransfer */
    public EmployeeResultTransfer(String partyName, EmployeeTransfer employee) {
        this.partyName = partyName;
        this.employee = employee;
    }

    /**
     * Returns the partyName.
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets the partyName.
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    /**
     * Returns the employee.
     * @return the employee
     */
    public EmployeeTransfer getEmployee() {
        return employee;
    }

    /**
     * Sets the employee.
     * @param employee the employee to set
     */
    public void setEmployee(EmployeeTransfer employee) {
        this.employee = employee;
    }

}
