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

package com.echothree.model.control.associate.server.logic;

import com.echothree.control.user.associate.common.spec.AssociatePartyContactMechanismSpec;
import com.echothree.control.user.associate.common.spec.AssociateReferralUniversalSpec;
import com.echothree.model.control.associate.common.exception.DuplicateAssociateReferralNameException;
import com.echothree.model.control.associate.common.exception.UnknownAssociateReferralNameException;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class AssociateReferralLogic
        extends BaseLogic {

    @Inject
    AssociateControl associateControl;

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected AssociateReferralLogic() {
        super();
    }

    public static AssociateReferralLogic getInstance() {
        return CDI.current().select(AssociateReferralLogic.class).get();
    }

    public AssociateReferral createAssociateReferral(final ExecutionErrorAccumulator eea,
            final String associateReferralName, final Associate associate,
            final AssociatePartyContactMechanism associatePartyContactMechanism,
            final EntityInstance targetEntityInstance, final Long associateReferralTime, final BasePK createdBy) {
        var associateReferral = associateControl.getAssociateReferralByName(associateReferralName);

        if(associateReferral == null) {
            associateReferral = associateControl.createAssociateReferral(associateReferralName, associate,
                    associatePartyContactMechanism, targetEntityInstance, associateReferralTime, createdBy);
        } else {
            handleExecutionError(DuplicateAssociateReferralNameException.class, eea,
                    ExecutionErrors.DuplicateAssociateReferralName.name(), associateReferralName);
        }

        return associateReferral;
    }

    public AssociateReferral createAssociateReferral(final Associate associate,
            final AssociatePartyContactMechanism associatePartyContactMechanism,
            final EntityInstance targetEntityInstance, final Long associateReferralTime, final BasePK createdBy) {
        return associateControl.createAssociateReferral(associate, associatePartyContactMechanism, targetEntityInstance,
                associateReferralTime, createdBy);
    }

    public AssociateReferral getAssociateReferralByName(final ExecutionErrorAccumulator eea,
            final String associateReferralName, final EntityPermission entityPermission) {
        var associateReferral = associateControl.getAssociateReferralByName(associateReferralName, entityPermission);

        if(associateReferral == null) {
            handleExecutionError(UnknownAssociateReferralNameException.class, eea,
                    ExecutionErrors.UnknownAssociateReferralName.name(), associateReferralName);
        }

        return associateReferral;
    }

    public AssociateReferral getAssociateReferralByName(final ExecutionErrorAccumulator eea,
            final String associateReferralName) {
        return getAssociateReferralByName(eea, associateReferralName, EntityPermission.READ_ONLY);
    }

    public AssociateReferral getAssociateReferralByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String associateReferralName) {
        return getAssociateReferralByName(eea, associateReferralName, EntityPermission.READ_WRITE);
    }

    public AssociateReferral getAssociateReferralByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AssociateReferralUniversalSpec universalSpec, final EntityPermission entityPermission) {
        AssociateReferral associateReferral = null;
        var associateReferralName = universalSpec.getAssociateReferralName();
        var parameterCount = (associateReferralName == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(associateReferralName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.AssociateReferral.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        associateReferral = associateControl.getAssociateReferralByEntityInstance(entityInstance,
                                entityPermission);
                    }
                } else {
                    associateReferral = getAssociateReferralByName(eea, associateReferralName, entityPermission);
                }
            }
            default -> handleExecutionError(InvalidParameterCountException.class, eea,
                    ExecutionErrors.InvalidParameterCount.name());
        }

        return associateReferral;
    }

    public AssociateReferral getAssociateReferralByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AssociateReferralUniversalSpec universalSpec) {
        return getAssociateReferralByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public AssociateReferral getAssociateReferralByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final AssociateReferralUniversalSpec universalSpec) {
        return getAssociateReferralByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteAssociateReferral(final AssociateReferral associateReferral, final BasePK deletedBy) {
        associateControl.deleteAssociateReferral(associateReferral, deletedBy);
    }

    public void handleAssociateReferral(final Session session, final ExecutionErrorAccumulator eea, final AssociatePartyContactMechanismSpec spec,
            final UserVisit userVisit, final BasePK targetPK, final BasePK partyPK) {
        var associateName = spec.getAssociateName();
        AssociateReferral associateReferral;

        if(associateName != null) {
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
                        associateReferral = associateControl.createAssociateReferral(associate, associatePartyContactMechanism,
                                entityInstanceControl.getEntityInstanceByBasePK(targetPK), session.getStartTime(), partyPK);

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
