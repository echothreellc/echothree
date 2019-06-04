// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.associate.server.AssociateControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class AssociateReferralLogic {

    private AssociateReferralLogic() {
        super();
    }

    private static class AssociateReferralLogicHolder {
        static AssociateReferralLogic instance = new AssociateReferralLogic();
    }

    public static AssociateReferralLogic getInstance() {
        return AssociateReferralLogicHolder.instance;
    }
    
    public void handleAssociateReferral(final Session session, final ExecutionErrorAccumulator eea, final AssociatePartyContactMechanismSpec spec,
            final UserVisit userVisit, final BasePK targetPK, final BasePK partyPK) {
        AssociateReferral associateReferral = null;
        String associateName = spec.getAssociateName();
        
        if(associateName != null) {
            var associateControl = (AssociateControl)Session.getModelController(AssociateControl.class);
            String associateProgramName = spec.getAssociateProgramName();
            AssociateProgram associateProgram = associateProgramName == null? associateControl.getDefaultAssociateProgram():
                associateControl.getAssociateProgramByName(associateProgramName);
            
            if(associateProgram != null) {
                Associate associate = associateControl.getAssociateByName(associateProgram, associateName);
                
                if(associate != null) {
                    String associatePartyContactMechanismName = spec.getAssociatePartyContactMechanismName();
                    AssociatePartyContactMechanism associatePartyContactMechanism = associatePartyContactMechanismName == null?
                        associateControl.getDefaultAssociatePartyContactMechanism(associate):
                        associateControl.getAssociatePartyContactMechanismByName(associate, associatePartyContactMechanismName);
                    
                    if(associatePartyContactMechanismName != null && associatePartyContactMechanism == null) {
                        eea.addExecutionError(ExecutionErrors.UnknownAssociatePartyContactMechanismName.name(), associateProgramName, associateName,
                                associatePartyContactMechanismName);
                    } else {
                        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                        
                        associateReferral = associateControl.createAssociateReferral(associate, associatePartyContactMechanism,
                                coreControl.getEntityInstanceByBasePK(targetPK), session.START_TIME_LONG, partyPK);
                        
                        userVisit.setAssociateReferral(associateReferral);
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.UnknownAssociateName.name(), associateProgramName, associateName);
                }
            } else {
                if(associateProgramName == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
                } else {
                    eea.addExecutionError(ExecutionErrors.MissingDefaultAssociateProgram.name());
                }
            }
        }
    }
    
}
