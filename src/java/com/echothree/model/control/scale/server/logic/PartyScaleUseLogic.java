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

package com.echothree.model.control.scale.server.logic;

import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.scale.server.entity.PartyScaleUse;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyScaleUseLogic
        extends BaseLogic {

    protected PartyScaleUseLogic() {
        super();
    }

    public static PartyScaleUseLogic getInstance() {
        return CDI.current().select(PartyScaleUseLogic.class).get();
    }

    public PartyScaleUse getPartyScaleUse(final ExecutionErrorAccumulator ema, final Party party, final ScaleUseType scaleUseType,
            final BasePK createdBy) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var partyScaleUse = scaleControl.getPartyScaleUse(party, scaleUseType);

        if(partyScaleUse == null) {
            var scale = scaleControl.getDefaultScale();

            if(scale == null) {
                addExecutionError(ema, ExecutionErrors.MissingDefaultPartyScale.name());
            } else {
                partyScaleUse = scaleControl.createPartyScaleUse(party, scaleUseType, scale, createdBy);
            }
        }

        return partyScaleUse;
    }

    public PartyScaleUse getPartyScaleUseUsingNames(final ExecutionErrorAccumulator ema, final Party party, final String scaleUseTypeName,
            final BasePK createdBy) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);
        PartyScaleUse partyScaleUse = null;

        if(scaleUseType == null) {
            addExecutionError(ema, ExecutionErrors.UnknownScaleUseTypeName.name(), scaleUseTypeName);
        } else {
            partyScaleUse = getPartyScaleUse(ema, party, scaleUseType, createdBy);
        }

        return partyScaleUse;
    }

}
