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

import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class EmployeeTransferCaches
        extends BaseTransferCaches {
    
    protected EmployeeControl employeeControl;
    
    protected EmployeeTypeTransferCache employeeTypeTransferCache;
    protected EmployeeTypeDescriptionTransferCache employeeTypeDescriptionTransferCache;
    protected EmployeeTransferCache employeeTransferCache;
    protected ResponsibilityTypeTransferCache responsibilityTypeTransferCache;
    protected ResponsibilityTypeDescriptionTransferCache responsibilityTypeDescriptionTransferCache;
    protected SkillTypeTransferCache skillTypeTransferCache;
    protected SkillTypeDescriptionTransferCache skillTypeDescriptionTransferCache;
    protected LeaveTypeTransferCache leaveTypeTransferCache;
    protected LeaveTypeDescriptionTransferCache leaveTypeDescriptionTransferCache;
    protected LeaveReasonTransferCache leaveReasonTransferCache;
    protected LeaveReasonDescriptionTransferCache leaveReasonDescriptionTransferCache;
    protected LeaveTransferCache leaveTransferCache;
    protected TerminationReasonTransferCache terminationReasonTransferCache;
    protected TerminationReasonDescriptionTransferCache terminationReasonDescriptionTransferCache;
    protected TerminationTypeTransferCache terminationTypeTransferCache;
    protected TerminationTypeDescriptionTransferCache terminationTypeDescriptionTransferCache;
    protected EmploymentTransferCache employmentTransferCache;
    protected PartyResponsibilityTransferCache partyResponsibilityTransferCache;
    protected PartySkillTransferCache partySkillTransferCache;
    
    /** Creates a new instance of EmployeeTransferCaches */
    public EmployeeTransferCaches(EmployeeControl employeeControl) {
        super();
        
        this.employeeControl = employeeControl;
    }
    
    public EmployeeTypeTransferCache getEmployeeTypeTransferCache() {
        if(employeeTypeTransferCache == null)
            employeeTypeTransferCache = new EmployeeTypeTransferCache(employeeControl);
        
        return employeeTypeTransferCache;
    }
    
    public EmployeeTypeDescriptionTransferCache getEmployeeTypeDescriptionTransferCache() {
        if(employeeTypeDescriptionTransferCache == null)
            employeeTypeDescriptionTransferCache = new EmployeeTypeDescriptionTransferCache(employeeControl);
        
        return employeeTypeDescriptionTransferCache;
    }
    
    public EmployeeTransferCache getEmployeeTransferCache() {
        if(employeeTransferCache == null) {
            employeeTransferCache = new EmployeeTransferCache(employeeControl);
        }
        
        return employeeTransferCache;
    }
    
    public ResponsibilityTypeTransferCache getResponsibilityTypeTransferCache() {
        if(responsibilityTypeTransferCache == null) {
            responsibilityTypeTransferCache = new ResponsibilityTypeTransferCache(employeeControl);
        }
        
        return responsibilityTypeTransferCache;
    }
    
    public ResponsibilityTypeDescriptionTransferCache getResponsibilityTypeDescriptionTransferCache() {
        if(responsibilityTypeDescriptionTransferCache == null) {
            responsibilityTypeDescriptionTransferCache = new ResponsibilityTypeDescriptionTransferCache(employeeControl);
        }
        
        return responsibilityTypeDescriptionTransferCache;
    }
    
    public SkillTypeTransferCache getSkillTypeTransferCache() {
        if(skillTypeTransferCache == null) {
            skillTypeTransferCache = new SkillTypeTransferCache(employeeControl);
        }
        
        return skillTypeTransferCache;
    }
    
    public SkillTypeDescriptionTransferCache getSkillTypeDescriptionTransferCache() {
        if(skillTypeDescriptionTransferCache == null) {
            skillTypeDescriptionTransferCache = new SkillTypeDescriptionTransferCache(employeeControl);
        }
        
        return skillTypeDescriptionTransferCache;
    }
    
    public LeaveTypeTransferCache getLeaveTypeTransferCache() {
        if(leaveTypeTransferCache == null) {
            leaveTypeTransferCache = new LeaveTypeTransferCache(employeeControl);
        }

        return leaveTypeTransferCache;
    }

    public LeaveTypeDescriptionTransferCache getLeaveTypeDescriptionTransferCache() {
        if(leaveTypeDescriptionTransferCache == null) {
            leaveTypeDescriptionTransferCache = new LeaveTypeDescriptionTransferCache(employeeControl);
        }

        return leaveTypeDescriptionTransferCache;
    }

    public LeaveReasonTransferCache getLeaveReasonTransferCache() {
        if(leaveReasonTransferCache == null) {
            leaveReasonTransferCache = new LeaveReasonTransferCache(employeeControl);
        }

        return leaveReasonTransferCache;
    }

    public LeaveReasonDescriptionTransferCache getLeaveReasonDescriptionTransferCache() {
        if(leaveReasonDescriptionTransferCache == null) {
            leaveReasonDescriptionTransferCache = new LeaveReasonDescriptionTransferCache(employeeControl);
        }

        return leaveReasonDescriptionTransferCache;
    }

    public LeaveTransferCache getLeaveTransferCache() {
        if(leaveTransferCache == null) {
            leaveTransferCache = new LeaveTransferCache(employeeControl);
        }

        return leaveTransferCache;
    }

    public TerminationReasonTransferCache getTerminationReasonTransferCache() {
        if(terminationReasonTransferCache == null) {
            terminationReasonTransferCache = new TerminationReasonTransferCache(employeeControl);
        }
        
        return terminationReasonTransferCache;
    }
    
    public TerminationReasonDescriptionTransferCache getTerminationReasonDescriptionTransferCache() {
        if(terminationReasonDescriptionTransferCache == null) {
            terminationReasonDescriptionTransferCache = new TerminationReasonDescriptionTransferCache(employeeControl);
        }
        
        return terminationReasonDescriptionTransferCache;
    }
    
    public TerminationTypeTransferCache getTerminationTypeTransferCache() {
        if(terminationTypeTransferCache == null) {
            terminationTypeTransferCache = new TerminationTypeTransferCache(employeeControl);
        }
        
        return terminationTypeTransferCache;
    }
    
    public TerminationTypeDescriptionTransferCache getTerminationTypeDescriptionTransferCache() {
        if(terminationTypeDescriptionTransferCache == null) {
            terminationTypeDescriptionTransferCache = new TerminationTypeDescriptionTransferCache(employeeControl);
        }
        
        return terminationTypeDescriptionTransferCache;
    }
    
    public EmploymentTransferCache getEmploymentTransferCache() {
        if(employmentTransferCache == null) {
            employmentTransferCache = new EmploymentTransferCache(employeeControl);
        }

        return employmentTransferCache;
    }

    public PartyResponsibilityTransferCache getPartyResponsibilityTransferCache() {
        if(partyResponsibilityTransferCache == null) {
            partyResponsibilityTransferCache = new PartyResponsibilityTransferCache(employeeControl);
        }
        
        return partyResponsibilityTransferCache;
    }
    
    public PartySkillTransferCache getPartySkillTransferCache() {
        if(partySkillTransferCache == null) {
            partySkillTransferCache = new PartySkillTransferCache(employeeControl);
        }
        
        return partySkillTransferCache;
    }
    
}
