// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.employee.common.transfer.EmploymentTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationReasonTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.employee.server.entity.Employment;
import com.echothree.model.data.employee.server.entity.EmploymentDetail;
import com.echothree.model.data.employee.server.entity.TerminationReason;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EmploymentTransferCache
        extends BaseEmployeeTransferCache<Employment, EmploymentTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of EmploymentTransferCache */
    public EmploymentTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public EmploymentTransfer getEmploymentTransfer(Employment employment) {
        EmploymentTransfer employmentTransfer = get(employment);
        
        if(employmentTransfer == null) {
            EmploymentDetail employmentDetail = employment.getLastDetail();
            String employmentName = employmentDetail.getEmploymentName();
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, employmentDetail.getParty());
            CompanyTransfer companyTransfer = partyControl.getCompanyTransfer(userVisit, employmentDetail.getCompanyParty());
            Long unformattedStartTime = employmentDetail.getStartTime();
            String startTime = formatTypicalDateTime(unformattedStartTime);
            Long unformattedEndTime = employmentDetail.getEndTime();
            String endTime = formatTypicalDateTime(unformattedEndTime);
            TerminationType terminationType = employmentDetail.getTerminationType();
            TerminationTypeTransfer terminationTypeTransfer = terminationType == null ? null : employeeControl.getTerminationTypeTransfer(userVisit, terminationType);
            TerminationReason terminationReason = employmentDetail.getTerminationReason();
            TerminationReasonTransfer terminationReasonTransfer = terminationReason == null ? null : employeeControl.getTerminationReasonTransfer(userVisit, terminationReason);
            
            employmentTransfer = new EmploymentTransfer(employmentName, partyTransfer, companyTransfer, unformattedStartTime, startTime, unformattedEndTime,
                    endTime, terminationTypeTransfer, terminationReasonTransfer);
            put(employment, employmentTransfer);
        }
        
        return employmentTransfer;
    }
    
}
