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

import com.echothree.control.user.associate.common.spec.AssociateUniversalSpec;
import com.echothree.model.control.associate.common.exception.CannotDeleteAssociateInUseException;
import com.echothree.model.control.associate.common.exception.DuplicateAssociateNameException;
import com.echothree.model.control.associate.common.exception.UnknownAssociateNameException;
import com.echothree.model.control.associate.common.exception.UnknownDefaultAssociateProgramException;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Party;
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
public class AssociateLogic
        extends BaseLogic {

    @Inject
    AssociateControl associateControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    AssociateProgramLogic associateProgramLogic;

    protected AssociateLogic() {
        super();
    }

    public static AssociateLogic getInstance() {
        return CDI.current().select(AssociateLogic.class).get();
    }

    public Associate createAssociate(final ExecutionErrorAccumulator eea, final String associateProgramName,
            final String associateName, final Party party, final String description, final MimeType summaryMimeType,
            final String summary, final BasePK createdBy) {
        var associateProgram = associateProgramLogic.getAssociateProgramByName(eea, associateProgramName);
        Associate associate = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            associate = createAssociate(eea, associateProgram, associateName, party, description, summaryMimeType,
                    summary, createdBy);
        }

        return associate;
    }

    public Associate createAssociate(final ExecutionErrorAccumulator eea, final AssociateProgram associateProgram,
            final String associateName, final Party party, final String description, final MimeType summaryMimeType,
            final String summary, final BasePK createdBy) {
        var associate = associateControl.getAssociateByName(associateProgram, associateName);

        if(associate == null) {
            associate = associateControl.createAssociate(associateProgram, associateName, party, description,
                    summaryMimeType, summary, createdBy);
        } else {
            handleExecutionError(DuplicateAssociateNameException.class, eea,
                    ExecutionErrors.DuplicateAssociateName.name(), associateName);
        }

        return associate;
    }

    public Associate getAssociateByName(final ExecutionErrorAccumulator eea, final AssociateProgram associateProgram,
            final String associateName, final EntityPermission entityPermission) {
        var associate = associateControl.getAssociateByName(associateProgram, associateName, entityPermission);

        if(associate == null) {
            handleExecutionError(UnknownAssociateNameException.class, eea, ExecutionErrors.UnknownAssociateName.name(),
                    associateProgram.getLastDetail().getAssociateProgramName(), associateName);
        }

        return associate;
    }

    public Associate getAssociateByName(final ExecutionErrorAccumulator eea, final AssociateProgram associateProgram,
            final String associateName) {
        return getAssociateByName(eea, associateProgram, associateName, EntityPermission.READ_ONLY);
    }

    public Associate getAssociateByNameForUpdate(final ExecutionErrorAccumulator eea,
            final AssociateProgram associateProgram, final String associateName) {
        return getAssociateByName(eea, associateProgram, associateName, EntityPermission.READ_WRITE);
    }

    public Associate getAssociateByName(final ExecutionErrorAccumulator eea, final String associateProgramName,
            final String associateName, final EntityPermission entityPermission) {
        var associateProgram = associateProgramLogic.getAssociateProgramByName(eea, associateProgramName);
        Associate associate = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            associate = getAssociateByName(eea, associateProgram, associateName, entityPermission);
        }

        return associate;
    }

    public Associate getAssociateByName(final ExecutionErrorAccumulator eea, final String associateProgramName,
            final String associateName) {
        return getAssociateByName(eea, associateProgramName, associateName, EntityPermission.READ_ONLY);
    }

    public Associate getAssociateByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String associateProgramName, final String associateName) {
        return getAssociateByName(eea, associateProgramName, associateName, EntityPermission.READ_WRITE);
    }

    public Associate getAssociateByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AssociateUniversalSpec universalSpec, final boolean allowDefault,
            final EntityPermission entityPermission) {
        var associateProgramName = universalSpec.getAssociateProgramName();
        var associateName = universalSpec.getAssociateName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(associateProgramName,
                associateName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        Associate associate = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
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

            if(eea == null || !eea.hasExecutionErrors()) {
                if(associateName == null) {
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
                } else {
                    associate = getAssociateByName(eea, associateProgram, associateName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Associate.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                associate = associateControl.getAssociateByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea,
                    ExecutionErrors.InvalidParameterCount.name());
        }

        return associate;
    }

    public Associate getAssociateByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AssociateUniversalSpec universalSpec, final boolean allowDefault) {
        return getAssociateByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Associate getAssociateByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final AssociateUniversalSpec universalSpec, final boolean allowDefault) {
        return getAssociateByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteAssociate(final ExecutionErrorAccumulator eea, final Associate associate,
            final BasePK deletedBy) {
        var associatePartyContactMechanismCount = associateControl
                .countAssociatePartyContactMechanismsByAssociate(associate);
        var associateReferralCount = associateControl.countAssociateReferralsByAssociate(associate);

        if(associatePartyContactMechanismCount == 0 && associateReferralCount == 0) {
            associateControl.deleteAssociate(associate, deletedBy);
        } else {
            var associateDetail = associate.getLastDetail();

            handleExecutionError(CannotDeleteAssociateInUseException.class, eea,
                    ExecutionErrors.CannotDeleteAssociateInUse.name(),
                    associateDetail.getAssociateProgram().getLastDetail().getAssociateProgramName(),
                    associateDetail.getAssociateName());
        }
    }

}
