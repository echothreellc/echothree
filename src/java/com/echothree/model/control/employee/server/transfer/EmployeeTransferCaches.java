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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class EmployeeTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    EmployeeTypeTransferCache employeeTypeTransferCache;
    
    @Inject
    EmployeeTypeDescriptionTransferCache employeeTypeDescriptionTransferCache;
    
    @Inject
    EmployeeTransferCache employeeTransferCache;
    
    @Inject
    ResponsibilityTypeTransferCache responsibilityTypeTransferCache;
    
    @Inject
    ResponsibilityTypeDescriptionTransferCache responsibilityTypeDescriptionTransferCache;
    
    @Inject
    SkillTypeTransferCache skillTypeTransferCache;
    
    @Inject
    SkillTypeDescriptionTransferCache skillTypeDescriptionTransferCache;
    
    @Inject
    LeaveTypeTransferCache leaveTypeTransferCache;
    
    @Inject
    LeaveTypeDescriptionTransferCache leaveTypeDescriptionTransferCache;
    
    @Inject
    LeaveReasonTransferCache leaveReasonTransferCache;
    
    @Inject
    LeaveReasonDescriptionTransferCache leaveReasonDescriptionTransferCache;
    
    @Inject
    LeaveTransferCache leaveTransferCache;
    
    @Inject
    TerminationReasonTransferCache terminationReasonTransferCache;
    
    @Inject
    TerminationReasonDescriptionTransferCache terminationReasonDescriptionTransferCache;
    
    @Inject
    TerminationTypeTransferCache terminationTypeTransferCache;
    
    @Inject
    TerminationTypeDescriptionTransferCache terminationTypeDescriptionTransferCache;
    
    @Inject
    EmploymentTransferCache employmentTransferCache;
    
    @Inject
    PartyResponsibilityTransferCache partyResponsibilityTransferCache;
    
    @Inject
    PartySkillTransferCache partySkillTransferCache;

    /** Creates a new instance of EmployeeTransferCaches */
    protected EmployeeTransferCaches() {
        super();
    }
    
    public EmployeeTypeTransferCache getEmployeeTypeTransferCache() {
        return employeeTypeTransferCache;
    }
    
    public EmployeeTypeDescriptionTransferCache getEmployeeTypeDescriptionTransferCache() {
        return employeeTypeDescriptionTransferCache;
    }
    
    public EmployeeTransferCache getEmployeeTransferCache() {
        return employeeTransferCache;
    }
    
    public ResponsibilityTypeTransferCache getResponsibilityTypeTransferCache() {
        return responsibilityTypeTransferCache;
    }
    
    public ResponsibilityTypeDescriptionTransferCache getResponsibilityTypeDescriptionTransferCache() {
        return responsibilityTypeDescriptionTransferCache;
    }
    
    public SkillTypeTransferCache getSkillTypeTransferCache() {
        return skillTypeTransferCache;
    }
    
    public SkillTypeDescriptionTransferCache getSkillTypeDescriptionTransferCache() {
        return skillTypeDescriptionTransferCache;
    }
    
    public LeaveTypeTransferCache getLeaveTypeTransferCache() {
        return leaveTypeTransferCache;
    }

    public LeaveTypeDescriptionTransferCache getLeaveTypeDescriptionTransferCache() {
        return leaveTypeDescriptionTransferCache;
    }

    public LeaveReasonTransferCache getLeaveReasonTransferCache() {
        return leaveReasonTransferCache;
    }

    public LeaveReasonDescriptionTransferCache getLeaveReasonDescriptionTransferCache() {
        return leaveReasonDescriptionTransferCache;
    }

    public LeaveTransferCache getLeaveTransferCache() {
        return leaveTransferCache;
    }

    public TerminationReasonTransferCache getTerminationReasonTransferCache() {
        return terminationReasonTransferCache;
    }
    
    public TerminationReasonDescriptionTransferCache getTerminationReasonDescriptionTransferCache() {
        return terminationReasonDescriptionTransferCache;
    }
    
    public TerminationTypeTransferCache getTerminationTypeTransferCache() {
        return terminationTypeTransferCache;
    }
    
    public TerminationTypeDescriptionTransferCache getTerminationTypeDescriptionTransferCache() {
        return terminationTypeDescriptionTransferCache;
    }
    
    public EmploymentTransferCache getEmploymentTransferCache() {
        return employmentTransferCache;
    }

    public PartyResponsibilityTransferCache getPartyResponsibilityTransferCache() {
        return partyResponsibilityTransferCache;
    }
    
    public PartySkillTransferCache getPartySkillTransferCache() {
        return partySkillTransferCache;
    }
    
}
