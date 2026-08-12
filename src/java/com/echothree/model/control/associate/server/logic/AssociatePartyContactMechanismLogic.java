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

import com.echothree.control.user.associate.common.spec.AssociatePartyContactMechanismUniversalSpec;
import com.echothree.model.control.associate.common.exception.CannotDeleteAssociatePartyContactMechanismInUseException;
import com.echothree.model.control.associate.common.exception.DuplicateAssociatePartyContactMechanismNameException;
import com.echothree.model.control.associate.common.exception.UnknownAssociatePartyContactMechanismNameException;
import com.echothree.model.control.associate.common.exception.UnknownDefaultAssociatePartyContactMechanismException;
import com.echothree.model.control.associate.common.exception.UnknownDefaultAssociateProgramException;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class AssociatePartyContactMechanismLogic
        extends BaseLogic {

    @Inject
    AssociateControl associateControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    AssociateLogic associateLogic;

    @Inject
    AssociateProgramLogic associateProgramLogic;

    protected AssociatePartyContactMechanismLogic() {
        super();
    }

    public static AssociatePartyContactMechanismLogic getInstance() {
        return CDI.current().select(AssociatePartyContactMechanismLogic.class).get();
    }

    public AssociatePartyContactMechanism createAssociatePartyContactMechanism(final ExecutionErrorAccumulator eea,
            final String associateProgramName, final String associateName,
            final String associatePartyContactMechanismName, final PartyContactMechanism partyContactMechanism,
            final Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var associate = associateLogic.getAssociateByName(eea, associateProgramName, associateName);
        AssociatePartyContactMechanism associatePartyContactMechanism = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            associatePartyContactMechanism = createAssociatePartyContactMechanism(eea, associate,
                    associatePartyContactMechanismName, partyContactMechanism, isDefault, sortOrder, createdBy);
        }

        return associatePartyContactMechanism;
    }

    public AssociatePartyContactMechanism createAssociatePartyContactMechanism(final ExecutionErrorAccumulator eea,
            final Associate associate, final String associatePartyContactMechanismName,
            final PartyContactMechanism partyContactMechanism, final Boolean isDefault, final Integer sortOrder,
            final BasePK createdBy) {
        var associatePartyContactMechanism = associateControl.getAssociatePartyContactMechanismByName(associate,
                associatePartyContactMechanismName);

        if(associatePartyContactMechanism == null) {
            associatePartyContactMechanism = associateControl.createAssociatePartyContactMechanism(associate,
                    associatePartyContactMechanismName, partyContactMechanism, isDefault, sortOrder, createdBy);
        } else {
            handleExecutionError(DuplicateAssociatePartyContactMechanismNameException.class, eea,
                    ExecutionErrors.DuplicateAssociatePartyContactMechanismName.name(),
                    associatePartyContactMechanismName);
        }

        return associatePartyContactMechanism;
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByName(
            final ExecutionErrorAccumulator eea, final Associate associate,
            final String associatePartyContactMechanismName, final EntityPermission entityPermission) {
        var associatePartyContactMechanism = associateControl.getAssociatePartyContactMechanismByName(associate,
                associatePartyContactMechanismName, entityPermission);

        if(associatePartyContactMechanism == null) {
            var associateDetail = associate.getLastDetail();

            handleExecutionError(UnknownAssociatePartyContactMechanismNameException.class, eea,
                    ExecutionErrors.UnknownAssociatePartyContactMechanismName.name(),
                    associateDetail.getAssociateProgram().getLastDetail().getAssociateProgramName(),
                    associateDetail.getAssociateName(), associatePartyContactMechanismName);
        }

        return associatePartyContactMechanism;
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByName(
            final ExecutionErrorAccumulator eea, final Associate associate,
            final String associatePartyContactMechanismName) {
        return getAssociatePartyContactMechanismByName(eea, associate, associatePartyContactMechanismName,
                EntityPermission.READ_ONLY);
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByNameForUpdate(
            final ExecutionErrorAccumulator eea, final Associate associate,
            final String associatePartyContactMechanismName) {
        return getAssociatePartyContactMechanismByName(eea, associate, associatePartyContactMechanismName,
                EntityPermission.READ_WRITE);
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByName(
            final ExecutionErrorAccumulator eea, final String associateProgramName, final String associateName,
            final String associatePartyContactMechanismName, final EntityPermission entityPermission) {
        var associate = associateLogic.getAssociateByName(eea, associateProgramName, associateName);
        AssociatePartyContactMechanism associatePartyContactMechanism = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            associatePartyContactMechanism = getAssociatePartyContactMechanismByName(eea, associate,
                    associatePartyContactMechanismName, entityPermission);
        }

        return associatePartyContactMechanism;
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByName(
            final ExecutionErrorAccumulator eea, final String associateProgramName, final String associateName,
            final String associatePartyContactMechanismName) {
        return getAssociatePartyContactMechanismByName(eea, associateProgramName, associateName,
                associatePartyContactMechanismName, EntityPermission.READ_ONLY);
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByNameForUpdate(
            final ExecutionErrorAccumulator eea, final String associateProgramName, final String associateName,
            final String associatePartyContactMechanismName) {
        return getAssociatePartyContactMechanismByName(eea, associateProgramName, associateName,
                associatePartyContactMechanismName, EntityPermission.READ_WRITE);
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByUniversalSpec(
            final ExecutionErrorAccumulator eea, final AssociatePartyContactMechanismUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var associateProgramName = universalSpec.getAssociateProgramName();
        var associateName = universalSpec.getAssociateName();
        var associatePartyContactMechanismName = universalSpec.getAssociatePartyContactMechanismName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(associateProgramName,
                associateName, associatePartyContactMechanismName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        AssociatePartyContactMechanism associatePartyContactMechanism = null;

        if(nameParameterCount < 4 && possibleEntitySpecs == 0) {
            AssociateProgram associateProgram = null;

            if(associateProgramName == null) {
                if(allowDefault) {
                    associateProgram = associateControl.getDefaultAssociateProgram();

                    if(associateProgram == null) {
                        handleExecutionError(UnknownDefaultAssociateProgramException.class, eea,
                                ExecutionErrors.UnknownDefaultAssociateProgram.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                associateProgram = associateProgramLogic.getAssociateProgramByName(eea, associateProgramName);
            }

            Associate associate = null;

            if(eea == null || !eea.hasExecutionErrors()) {
                if(associateName == null) {
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
                } else {
                    associate = associateLogic.getAssociateByName(eea, associateProgram, associateName);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                if(associatePartyContactMechanismName == null) {
                    if(allowDefault) {
                        associatePartyContactMechanism = associateControl
                                .getDefaultAssociatePartyContactMechanism(associate, entityPermission);

                        if(associatePartyContactMechanism == null) {
                            handleExecutionError(UnknownDefaultAssociatePartyContactMechanismException.class, eea,
                                    ExecutionErrors.UnknownDefaultAssociatePartyContactMechanism.name(),
                                    associateProgram.getLastDetail().getAssociateProgramName(), associateName);
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea,
                                ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    associatePartyContactMechanism = getAssociatePartyContactMechanismByName(eea, associate,
                            associatePartyContactMechanismName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.AssociatePartyContactMechanism.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                associatePartyContactMechanism = associateControl
                        .getAssociatePartyContactMechanismByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea,
                    ExecutionErrors.InvalidParameterCount.name());
        }

        return associatePartyContactMechanism;
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByUniversalSpec(
            final ExecutionErrorAccumulator eea, final AssociatePartyContactMechanismUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getAssociatePartyContactMechanismByUniversalSpec(eea, universalSpec, allowDefault,
                EntityPermission.READ_ONLY);
    }

    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByUniversalSpecForUpdate(
            final ExecutionErrorAccumulator eea, final AssociatePartyContactMechanismUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getAssociatePartyContactMechanismByUniversalSpec(eea, universalSpec, allowDefault,
                EntityPermission.READ_WRITE);
    }

    public void deleteAssociatePartyContactMechanism(final ExecutionErrorAccumulator eea,
            final AssociatePartyContactMechanism associatePartyContactMechanism, final BasePK deletedBy) {
        var associateReferralCount = associateControl
                .countAssociateReferralsByAssociatePartyContactMechanism(associatePartyContactMechanism);

        if(associateReferralCount == 0) {
            associateControl.deleteAssociatePartyContactMechanism(associatePartyContactMechanism, deletedBy);
        } else {
            var associatePartyContactMechanismDetail = associatePartyContactMechanism.getLastDetail();
            var associateDetail = associatePartyContactMechanismDetail.getAssociate().getLastDetail();

            handleExecutionError(CannotDeleteAssociatePartyContactMechanismInUseException.class, eea,
                    ExecutionErrors.CannotDeleteAssociatePartyContactMechanismInUse.name(),
                    associateDetail.getAssociateProgram().getLastDetail().getAssociateProgramName(),
                    associateDetail.getAssociateName(),
                    associatePartyContactMechanismDetail.getAssociatePartyContactMechanismName());
        }
    }

}
