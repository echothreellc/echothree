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

package com.echothree.model.control.sequence.server.logic;

import com.echothree.control.user.sequence.common.spec.SequenceTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.sequence.common.exception.DuplicateSequenceTypeNameException;
import com.echothree.model.control.sequence.common.exception.UnknownDefaultSequenceTypeException;
import com.echothree.model.control.sequence.common.exception.UnknownSequenceTypeNameException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SequenceTypeLogic
        extends BaseLogic {

    protected SequenceTypeLogic() {
        super();
    }

    public static SequenceTypeLogic getInstance() {
        return CDI.current().select(SequenceTypeLogic.class).get();
    }

    public SequenceType createSequenceType(final ExecutionErrorAccumulator eea, final String sequenceTypeName,
            final String prefix, final String suffix, final SequenceEncoderType sequenceEncoderType,
            final SequenceChecksumType sequenceChecksumType, final Integer chunkSize, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);

        if(sequenceType == null) {
            sequenceType = sequenceControl.createSequenceType(sequenceTypeName, prefix, suffix, sequenceEncoderType,
                    sequenceChecksumType, chunkSize, isDefault, sortOrder, createdBy);

            if(description != null) {
                sequenceControl.createSequenceTypeDescription(sequenceType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateSequenceTypeNameException.class, eea, ExecutionErrors.DuplicateSequenceTypeName.name(), sequenceTypeName);
        }

        return sequenceType;
    }

    public SequenceType getSequenceTypeByName(final ExecutionErrorAccumulator eea, final String sequenceTypeName,
            final EntityPermission entityPermission) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName, entityPermission);

        if(sequenceType == null) {
            handleExecutionError(UnknownSequenceTypeNameException.class, eea, ExecutionErrors.UnknownSequenceTypeName.name(), sequenceTypeName);
        }

        return sequenceType;
    }

    public SequenceType getSequenceTypeByName(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        return getSequenceTypeByName(eea, sequenceTypeName, EntityPermission.READ_ONLY);
    }

    public SequenceType getSequenceTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        return getSequenceTypeByName(eea, sequenceTypeName, EntityPermission.READ_WRITE);
    }

    public SequenceType getSequenceTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SequenceTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SequenceType sequenceType = null;
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceTypeName = universalSpec.getSequenceTypeName();
        var parameterCount = (sequenceTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    sequenceType = sequenceControl.getDefaultSequenceType(entityPermission);

                    if(sequenceType == null) {
                        handleExecutionError(UnknownDefaultSequenceTypeException.class, eea, ExecutionErrors.UnknownDefaultSequenceType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(sequenceTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SequenceType.name());

                    if(!eea.hasExecutionErrors()) {
                        sequenceType = sequenceControl.getSequenceTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    sequenceType = getSequenceTypeByName(eea, sequenceTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return sequenceType;
    }

    public SequenceType getSequenceTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SequenceTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSequenceTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SequenceType getSequenceTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SequenceTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSequenceTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }
    
}
