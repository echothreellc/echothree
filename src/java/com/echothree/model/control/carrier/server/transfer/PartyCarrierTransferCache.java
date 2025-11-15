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

package com.echothree.model.control.carrier.server.transfer;



import com.echothree.model.control.carrier.common.transfer.PartyCarrierTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.carrier.server.entity.PartyCarrier;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyCarrierTransferCache
        extends BaseCarrierTransferCache<PartyCarrier, PartyCarrierTransfer> {

    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyCarrierTransferCache */
    public PartyCarrierTransferCache() {
        super();
    }
    
    public PartyCarrierTransfer getPartyCarrierTransfer(UserVisit userVisit, PartyCarrier partyCarrier) {
        var partyCarrierTransfer = get(partyCarrier);
        
        if(partyCarrierTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, partyCarrier.getParty());
            var carrier = carrierControl.getCarrierTransfer(userVisit, partyCarrier.getCarrierParty());
            
            partyCarrierTransfer = new PartyCarrierTransfer(party, carrier);
            put(userVisit, partyCarrier, partyCarrierTransfer);
        }
        
        return partyCarrierTransfer;
    }
    
}
