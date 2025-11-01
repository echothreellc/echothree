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

package com.echothree.model.control.printer.server.logic;

import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.printer.server.entity.PartyPrinterGroupUse;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyPrinterGroupUseLogic
        extends BaseLogic {

    protected PartyPrinterGroupUseLogic() {
        super();
    }

    public static PartyPrinterGroupUseLogic getInstance() {
        return CDI.current().select(PartyPrinterGroupUseLogic.class).get();
    }

    public PartyPrinterGroupUse getPartyPrinterGroupUse(final ExecutionErrorAccumulator ema, final Party party, final PrinterGroupUseType printerGroupUseType,
            final BasePK createdBy) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var partyPrinterGroupUse = printerControl.getPartyPrinterGroupUse(party, printerGroupUseType);

        if(partyPrinterGroupUse == null) {
            var printerGroup = printerControl.getDefaultPrinterGroup();

            if(printerGroup == null) {
                addExecutionError(ema, ExecutionErrors.MissingDefaultPartyPrinterGroup.name());
            } else {
                partyPrinterGroupUse = printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, printerGroup, createdBy);
            }
        }

        return partyPrinterGroupUse;
    }

    public PartyPrinterGroupUse getPartyPrinterGroupUseUsingNames(final ExecutionErrorAccumulator ema, final Party party, final String printerGroupUseTypeName,
            final BasePK createdBy) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(printerGroupUseTypeName);
        PartyPrinterGroupUse partyPrinterGroupUse = null;

        if(printerGroupUseType == null) {
            addExecutionError(ema, ExecutionErrors.UnknownPrinterGroupUseTypeName.name(), printerGroupUseTypeName);
        } else {
            partyPrinterGroupUse = getPartyPrinterGroupUse(ema, party, printerGroupUseType, createdBy);
        }

        return partyPrinterGroupUse;
    }

}
