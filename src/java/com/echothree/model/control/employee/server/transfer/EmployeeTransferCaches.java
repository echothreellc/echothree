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
import javax.enterprise.inject.spi.CDI;

public class EmployeeTransferCaches
        extends BaseTransferCaches {
    
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
    public EmployeeTransferCaches() {
        super();
    }
    
    public EmployeeTypeTransferCache getEmployeeTypeTransferCache() {
        if(employeeTypeTransferCache == null)
            employeeTypeTransferCache = CDI.current().select(EmployeeTypeTransferCache.class).get();
        
        return employeeTypeTransferCache;
    }
    
    public EmployeeTypeDescriptionTransferCache getEmployeeTypeDescriptionTransferCache() {
        if(employeeTypeDescriptionTransferCache == null)
            employeeTypeDescriptionTransferCache = CDI.current().select(EmployeeTypeDescriptionTransferCache.class).get();
        
        return employeeTypeDescriptionTransferCache;
    }
    
    public EmployeeTransferCache getEmployeeTransferCache() {
        if(employeeTransferCache == null) {
            employeeTransferCache = CDI.current().select(EmployeeTransferCache.class).get();
        }
        
        return employeeTransferCache;
    }
    
    public ResponsibilityTypeTransferCache getResponsibilityTypeTransferCache() {
        if(responsibilityTypeTransferCache == null) {
            responsibilityTypeTransferCache = CDI.current().select(ResponsibilityTypeTransferCache.class).get();
        }
        
        return responsibilityTypeTransferCache;
    }
    
    public ResponsibilityTypeDescriptionTransferCache getResponsibilityTypeDescriptionTransferCache() {
        if(responsibilityTypeDescriptionTransferCache == null) {
            responsibilityTypeDescriptionTransferCache = CDI.current().select(ResponsibilityTypeDescriptionTransferCache.class).get();
        }
        
        return responsibilityTypeDescriptionTransferCache;
    }
    
    public SkillTypeTransferCache getSkillTypeTransferCache() {
        if(skillTypeTransferCache == null) {
            skillTypeTransferCache = CDI.current().select(SkillTypeTransferCache.class).get();
        }
        
        return skillTypeTransferCache;
    }
    
    public SkillTypeDescriptionTransferCache getSkillTypeDescriptionTransferCache() {
        if(skillTypeDescriptionTransferCache == null) {
            skillTypeDescriptionTransferCache = CDI.current().select(SkillTypeDescriptionTransferCache.class).get();
        }
        
        return skillTypeDescriptionTransferCache;
    }
    
    public LeaveTypeTransferCache getLeaveTypeTransferCache() {
        if(leaveTypeTransferCache == null) {
            leaveTypeTransferCache = CDI.current().select(LeaveTypeTransferCache.class).get();
        }

        return leaveTypeTransferCache;
    }

    public LeaveTypeDescriptionTransferCache getLeaveTypeDescriptionTransferCache() {
        if(leaveTypeDescriptionTransferCache == null) {
            leaveTypeDescriptionTransferCache = CDI.current().select(LeaveTypeDescriptionTransferCache.class).get();
        }

        return leaveTypeDescriptionTransferCache;
    }

    public LeaveReasonTransferCache getLeaveReasonTransferCache() {
        if(leaveReasonTransferCache == null) {
            leaveReasonTransferCache = CDI.current().select(LeaveReasonTransferCache.class).get();
        }

        return leaveReasonTransferCache;
    }

    public LeaveReasonDescriptionTransferCache getLeaveReasonDescriptionTransferCache() {
        if(leaveReasonDescriptionTransferCache == null) {
            leaveReasonDescriptionTransferCache = CDI.current().select(LeaveReasonDescriptionTransferCache.class).get();
        }

        return leaveReasonDescriptionTransferCache;
    }

    public LeaveTransferCache getLeaveTransferCache() {
        if(leaveTransferCache == null) {
            leaveTransferCache = CDI.current().select(LeaveTransferCache.class).get();
        }

        return leaveTransferCache;
    }

    public TerminationReasonTransferCache getTerminationReasonTransferCache() {
        if(terminationReasonTransferCache == null) {
            terminationReasonTransferCache = CDI.current().select(TerminationReasonTransferCache.class).get();
        }
        
        return terminationReasonTransferCache;
    }
    
    public TerminationReasonDescriptionTransferCache getTerminationReasonDescriptionTransferCache() {
        if(terminationReasonDescriptionTransferCache == null) {
            terminationReasonDescriptionTransferCache = CDI.current().select(TerminationReasonDescriptionTransferCache.class).get();
        }
        
        return terminationReasonDescriptionTransferCache;
    }
    
    public TerminationTypeTransferCache getTerminationTypeTransferCache() {
        if(terminationTypeTransferCache == null) {
            terminationTypeTransferCache = CDI.current().select(TerminationTypeTransferCache.class).get();
        }
        
        return terminationTypeTransferCache;
    }
    
    public TerminationTypeDescriptionTransferCache getTerminationTypeDescriptionTransferCache() {
        if(terminationTypeDescriptionTransferCache == null) {
            terminationTypeDescriptionTransferCache = CDI.current().select(TerminationTypeDescriptionTransferCache.class).get();
        }
        
        return terminationTypeDescriptionTransferCache;
    }
    
    public EmploymentTransferCache getEmploymentTransferCache() {
        if(employmentTransferCache == null) {
            employmentTransferCache = CDI.current().select(EmploymentTransferCache.class).get();
        }

        return employmentTransferCache;
    }

    public PartyResponsibilityTransferCache getPartyResponsibilityTransferCache() {
        if(partyResponsibilityTransferCache == null) {
            partyResponsibilityTransferCache = CDI.current().select(PartyResponsibilityTransferCache.class).get();
        }
        
        return partyResponsibilityTransferCache;
    }
    
    public PartySkillTransferCache getPartySkillTransferCache() {
        if(partySkillTransferCache == null) {
            partySkillTransferCache = CDI.current().select(PartySkillTransferCache.class).get();
        }
        
        return partySkillTransferCache;
    }
    
}
