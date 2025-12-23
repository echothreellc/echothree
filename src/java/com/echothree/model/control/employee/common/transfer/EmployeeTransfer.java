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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;

public class EmployeeTransfer
        extends PartyTransfer {
    
    private String employeeName;
    private EmployeeTypeTransfer employeeType;
    private WorkflowEntityStatusTransfer employeeStatus;
    private WorkflowEntityStatusTransfer employeeAvailability;

    /** Creates a new instance of EmployeeTransfer */
    public EmployeeTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage,
            CurrencyTransfer preferredCurrency, TimeZoneTransfer preferredTimeZone, DateTimeFormatTransfer preferredDateTimeFormat,
            PersonTransfer person, ProfileTransfer profileTransfer, String employeeName, EmployeeTypeTransfer employeeType,
            WorkflowEntityStatusTransfer employeeStatus, WorkflowEntityStatusTransfer employeeAvailability) {
        super(partyName, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat,
                person, null, profileTransfer);

        this.employeeName = employeeName;
        this.employeeType = employeeType;
        this.employeeStatus = employeeStatus;
        this.employeeAvailability = employeeAvailability;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public EmployeeTypeTransfer getEmployeeType() {
        return employeeType;
    }
    
    public void setEmployeeType(EmployeeTypeTransfer employeeType) {
        this.employeeType = employeeType;
    }
    
    public WorkflowEntityStatusTransfer getEmployeeStatus() {
        return employeeStatus;
    }
    
    public void setEmployeeStatus(WorkflowEntityStatusTransfer employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
    
    public WorkflowEntityStatusTransfer getEmployeeAvailability() {
        return employeeAvailability;
    }
    
    public void setEmployeeAvailability(WorkflowEntityStatusTransfer employeeAvailability) {
        this.employeeAvailability = employeeAvailability;
    }

}
