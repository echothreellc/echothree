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

package com.echothree.model.control.shipment.server.logic;

import com.echothree.model.control.shipment.common.exception.UnknownPartyFreeOnBoardException;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.shipment.server.entity.PartyFreeOnBoard;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyFreeOnBoardLogic
        extends BaseLogic {

    protected PartyFreeOnBoardLogic() {
        super();
    }

    public static PartyFreeOnBoardLogic getInstance() {
        return CDI.current().select(PartyFreeOnBoardLogic.class).get();
    }
    
    public PartyFreeOnBoard getPartyFreeOnBoard(final ExecutionErrorAccumulator eea, final Party party) {
        var partyFreeOnBoardControl = Session.getModelController(PartyFreeOnBoardControl.class);
        var partyFreeOnBoard = partyFreeOnBoardControl.getPartyFreeOnBoard(party);

        if(partyFreeOnBoard == null) {
            handleExecutionError(UnknownPartyFreeOnBoardException.class, eea, ExecutionErrors.UnknownPartyFreeOnBoard.name(), party.getLastDetail().getPartyName());
        }

        return partyFreeOnBoard;
    }

}
