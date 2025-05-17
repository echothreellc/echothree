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

package com.echothree.model.control.associate.server.logic;

import com.echothree.control.user.associate.common.spec.AssociatePartyContactMechanismSpec;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class AssociateReferralLogic
        extends BaseLogic {

    private AssociateReferralLogic() {
        super();
    }

    private static class LogicHolder {
        static AssociateReferralLogic instance = new AssociateReferralLogic();
    }

    public static AssociateReferralLogic getInstance() {
        return LogicHolder.instance;
    }

    public void handleAssociateReferral(final Session session, final ExecutionErrorAccumulator eea, final AssociatePartyContactMechanismSpec spec,
            final UserVisit userVisit, final BasePK targetPK, final BasePK partyPK) {
        var associateName = spec.getAssociateName();
        AssociateReferral associateReferral;

        if(associateName != null) {
            var associateControl = Session.getModelController(AssociateControl.class);
            var associateProgramName = spec.getAssociateProgramName();
            var associateProgram = associateProgramName == null ? associateControl.getDefaultAssociateProgram() :
                associateControl.getAssociateProgramByName(associateProgramName);

            if(associateProgram != null) {
                var associate = associateControl.getAssociateByName(associateProgram, associateName);

                if(associate != null) {
                    var associatePartyContactMechanismName = spec.getAssociatePartyContactMechanismName();
                    var associatePartyContactMechanism = associatePartyContactMechanismName == null ?
                        associateControl.getDefaultAssociatePartyContactMechanism(associate) :
                        associateControl.getAssociatePartyContactMechanismByName(associate, associatePartyContactMechanismName);

                    if(associatePartyContactMechanismName != null && associatePartyContactMechanism == null) {
                        eea.addExecutionError(ExecutionErrors.UnknownAssociatePartyContactMechanismName.name(),
                                associateProgram.getLastDetail().getAssociateProgramName(),
                                associate.getLastDetail().getAssociateName(), associatePartyContactMechanismName);
                    } else {
                        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

                        associateReferral = associateControl.createAssociateReferral(associate, associatePartyContactMechanism,
                                entityInstanceControl.getEntityInstanceByBasePK(targetPK), session.START_TIME_LONG, partyPK);

                        userVisit.setAssociateReferral(associateReferral);
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.UnknownAssociateName.name(),
                            associateProgram.getLastDetail().getAssociateProgramName(), associateName);
                }
            } else {
                if(associateProgramName != null) {
                    eea.addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
                } else {
                    eea.addExecutionError(ExecutionErrors.MissingDefaultAssociateProgram.name());
                }
            }
        }
    }

    public AssociateReferral getAssociateReferral(final Session session, final UserVisit userVisit) {
        var associateReferral = userVisit == null ? null : userVisit.getAssociateReferral();

        // TODO: Check the time of the referral to see if it is still in effect.

        return associateReferral;
    }

}
