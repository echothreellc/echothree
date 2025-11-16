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

import com.echothree.model.control.employee.common.transfer.PartyResponsibilityTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.employee.server.entity.PartyResponsibility;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyResponsibilityTransferCache
        extends BaseEmployeeTransferCache<PartyResponsibility, PartyResponsibilityTransfer> {

    EmployeeControl employeeControl = Session.getModelController(EmployeeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyResponsibilityTransferCache */
    public PartyResponsibilityTransferCache() {
        super();
    }
    
    public PartyResponsibilityTransfer getPartyResponsibilityTransfer(UserVisit userVisit, PartyResponsibility partyResponsibility) {
        var partyResponsibilityTransfer = get(partyResponsibility);
        
        if(partyResponsibilityTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, partyResponsibility.getParty());
            var responsibilityType = employeeControl.getResponsibilityTypeTransfer(userVisit, partyResponsibility.getResponsibilityType());
            
            partyResponsibilityTransfer = new PartyResponsibilityTransfer(party, responsibilityType);
            put(userVisit, partyResponsibility, partyResponsibilityTransfer);
        }
        
        return partyResponsibilityTransfer;
    }
    
}
