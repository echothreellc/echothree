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



import com.echothree.model.control.carrier.common.transfer.PartyCarrierAccountTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.carrier.server.entity.PartyCarrierAccount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyCarrierAccountTransferCache
        extends BaseCarrierTransferCache<PartyCarrierAccount, PartyCarrierAccountTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyCarrierAccountTransferCache */
    public PartyCarrierAccountTransferCache(UserVisit userVisit, CarrierControl carrierControl) {
        super(userVisit, carrierControl);
    }
    
    public PartyCarrierAccountTransfer getPartyCarrierAccountTransfer(PartyCarrierAccount partyCarrierAccount) {
        var partyCarrierAccountTransfer = get(partyCarrierAccount);
        
        if(partyCarrierAccountTransfer == null) {
            var partyCarrierAccountDetail = partyCarrierAccount.getLastDetail();
            var party = partyControl.getPartyTransfer(userVisit, partyCarrierAccountDetail.getParty());
            var carrier = carrierControl.getCarrierTransfer(userVisit, partyCarrierAccountDetail.getCarrierParty());
            var account = partyCarrierAccountDetail.getAccount();
            var alwaysUseThirdPartyBilling = partyCarrierAccountDetail.getAlwaysUseThirdPartyBilling();
            
            partyCarrierAccountTransfer = new PartyCarrierAccountTransfer(party, carrier, account, alwaysUseThirdPartyBilling);
            put(userVisit, partyCarrierAccount, partyCarrierAccountTransfer);
        }
        
        return partyCarrierAccountTransfer;
    }
    
}
