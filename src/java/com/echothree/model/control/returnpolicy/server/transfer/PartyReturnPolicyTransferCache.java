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

package com.echothree.model.control.returnpolicy.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.common.transfer.PartyReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.PartyReturnPolicyLogic;
import com.echothree.model.data.returnpolicy.server.entity.PartyReturnPolicy;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyReturnPolicyTransferCache
        extends BaseReturnPolicyTransferCache<PartyReturnPolicy, PartyReturnPolicyTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

    /** Creates a new instance of PartyReturnPolicyTransferCache */
    protected PartyReturnPolicyTransferCache() {
        super();
    }

    public PartyReturnPolicyTransfer getPartyReturnPolicyTransfer(UserVisit userVisit, PartyReturnPolicy partyReturnPolicy) {
        var partyReturnPolicyTransfer = get(partyReturnPolicy);

        if(partyReturnPolicyTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, partyReturnPolicy.getParty());
            var returnPolicy = returnPolicyControl.getReturnPolicyTransfer(userVisit, partyReturnPolicy.getReturnPolicy());

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyReturnPolicy.getPrimaryKey());
            var createdBy = getPartyPK(userVisit);
            var partyReturnPolicyStatusTransfer = PartyReturnPolicyLogic.getInstance().getPartyReturnPolicyStatusTransfer(userVisit, entityInstance, createdBy);

            partyReturnPolicyTransfer = new PartyReturnPolicyTransfer(party, returnPolicy, partyReturnPolicyStatusTransfer);
            put(userVisit, partyReturnPolicy, partyReturnPolicyTransfer, entityInstance);
        }

        return partyReturnPolicyTransfer;
    }

}
