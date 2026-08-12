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

import com.echothree.control.user.associate.common.spec.AssociateProgramUniversalSpec;
import com.echothree.model.control.associate.common.exception.CannotDeleteAssociateProgramInUseException;
import com.echothree.model.control.associate.common.exception.DuplicateAssociateProgramNameException;
import com.echothree.model.control.associate.common.exception.UnknownAssociateProgramNameException;
import com.echothree.model.control.associate.common.exception.UnknownDefaultAssociateProgramException;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class AssociateProgramLogic
        extends BaseLogic {

    @Inject
    AssociateControl associateControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected AssociateProgramLogic() {
        super();
    }

    public static AssociateProgramLogic getInstance() {
        return CDI.current().select(AssociateProgramLogic.class).get();
    }

    public AssociateProgram createAssociateProgram(final ExecutionErrorAccumulator eea, final String associateProgramName,
            final Sequence associateSequence, final Sequence associatePartyContactMechanismSequence,
            final Sequence associateReferralSequence, final Integer itemIndirectSalePercent,
            final Integer itemDirectSalePercent, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var associateProgram = associateControl.getAssociateProgramByName(associateProgramName);

        if(associateProgram == null) {
            associateProgram = associateControl.createAssociateProgram(associateProgramName, associateSequence,
                    associatePartyContactMechanismSequence, associateReferralSequence, itemIndirectSalePercent,
                    itemDirectSalePercent, isDefault, sortOrder, createdBy);

            if(description != null) {
                associateControl.createAssociateProgramDescription(associateProgram, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateAssociateProgramNameException.class, eea,
                    ExecutionErrors.DuplicateAssociateProgramName.name(), associateProgramName);
        }

        return associateProgram;
    }

    public AssociateProgram getAssociateProgramByName(final ExecutionErrorAccumulator eea,
            final String associateProgramName, final EntityPermission entityPermission) {
        var associateProgram = associateControl.getAssociateProgramByName(associateProgramName, entityPermission);

        if(associateProgram == null) {
            handleExecutionError(UnknownAssociateProgramNameException.class, eea,
                    ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
        }

        return associateProgram;
    }

    public AssociateProgram getAssociateProgramByName(final ExecutionErrorAccumulator eea,
            final String associateProgramName) {
        return getAssociateProgramByName(eea, associateProgramName, EntityPermission.READ_ONLY);
    }

    public AssociateProgram getAssociateProgramByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String associateProgramName) {
        return getAssociateProgramByName(eea, associateProgramName, EntityPermission.READ_WRITE);
    }

    public AssociateProgram getAssociateProgramByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AssociateProgramUniversalSpec universalSpec, final boolean allowDefault,
            final EntityPermission entityPermission) {
        AssociateProgram associateProgram = null;
        var associateProgramName = universalSpec.getAssociateProgramName();
        var parameterCount = (associateProgramName == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    associateProgram = associateControl.getDefaultAssociateProgram(entityPermission);

                    if(associateProgram == null) {
                        handleExecutionError(UnknownDefaultAssociateProgramException.class, eea,
                                ExecutionErrors.UnknownDefaultAssociateProgram.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(associateProgramName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.AssociateProgram.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        associateProgram = associateControl.getAssociateProgramByEntityInstance(entityInstance,
                                entityPermission);
                    }
                } else {
                    associateProgram = getAssociateProgramByName(eea, associateProgramName, entityPermission);
                }
            }
            default -> handleExecutionError(InvalidParameterCountException.class, eea,
                    ExecutionErrors.InvalidParameterCount.name());
        }

        return associateProgram;
    }

    public AssociateProgram getAssociateProgramByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AssociateProgramUniversalSpec universalSpec, final boolean allowDefault) {
        return getAssociateProgramByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public AssociateProgram getAssociateProgramByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final AssociateProgramUniversalSpec universalSpec, final boolean allowDefault) {
        return getAssociateProgramByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteAssociateProgram(final ExecutionErrorAccumulator eea, final AssociateProgram associateProgram,
            final BasePK deletedBy) {
        var associateCount = associateControl.countAssociatesByAssociateProgram(associateProgram);

        if(associateCount == 0) {
            associateControl.deleteAssociateProgram(associateProgram, deletedBy);
        } else {
            handleExecutionError(CannotDeleteAssociateProgramInUseException.class, eea,
                    ExecutionErrors.CannotDeleteAssociateProgramInUse.name(),
                    associateProgram.getLastDetail().getAssociateProgramName());
        }
    }

}
