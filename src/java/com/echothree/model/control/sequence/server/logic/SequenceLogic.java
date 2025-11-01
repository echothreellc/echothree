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

import com.echothree.control.user.sequence.common.spec.SequenceUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.exception.DuplicateSequenceNameException;
import com.echothree.model.control.sequence.common.exception.InvalidValueLengthException;
import com.echothree.model.control.sequence.common.exception.UnknownDefaultSequenceException;
import com.echothree.model.control.sequence.common.exception.UnknownDefaultSequenceTypeException;
import com.echothree.model.control.sequence.common.exception.UnknownSequenceNameException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SequenceLogic
        extends BaseLogic {

    protected SequenceLogic() {
        super();
    }

    public static SequenceLogic getInstance() {
        return CDI.current().select(SequenceLogic.class).get();
    }
    
    public Sequence createSequence(final ExecutionErrorAccumulator eea, final String sequenceTypeName, final String sequenceName,
            final String value, final String mask, final Integer chunkSize, final Boolean isDefault, final Integer sortOrder,
            final String description, final Language language, final BasePK createdBy) {
        var sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(eea, sequenceTypeName);
        Sequence sequence = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            sequence = createSequence(eea, sequenceType, sequenceName, value, mask, chunkSize, isDefault, sortOrder, description,
                    language, createdBy);
        }
        
        return sequence;
    }    
    
    public Sequence createSequence(final ExecutionErrorAccumulator eea, final SequenceType sequenceType, String sequenceName,
            String value, final String mask, final Integer chunkSize, final Boolean isDefault, final Integer sortOrder,
            final String description, final Language language, final BasePK createdBy) {
        Sequence sequence = null;
        
        if(sequenceName == null) {
            sequenceName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(eea, SequenceTypes.SEQUENCE.name());
        }
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var sequenceControl = Session.getModelController(SequenceControl.class);

            sequence = sequenceControl.getSequenceByName(sequenceType, sequenceName);
    
            if(sequence == null) {
                if(value == null || value.length() == mask.length()) {
                    sequence = sequenceControl.createSequence(sequenceType, sequenceName, mask, chunkSize, isDefault, sortOrder, createdBy);

                    if(value == null) {
                        var maskChars = mask.toCharArray();
                        var maskLength = mask.length();
                        var valueBuilder = new StringBuilder(maskLength);

                        for(var index = 0; index < maskLength; index++) {
                            var maskChar = maskChars[index];

                            switch(maskChar) {
                                case '9':
                                case 'Z':
                                    valueBuilder.append('0');
                                    break;
                                case 'A':
                                    valueBuilder.append('A');
                                    break;
                            }
                        }

                        value = valueBuilder.toString();
                    }

                    sequenceControl.createSequenceValue(sequence, value);

                    if(description != null) {
                        sequenceControl.createSequenceDescription(sequence, language, description, createdBy);
                    }
                } else {
                    handleExecutionError(InvalidValueLengthException.class, eea, ExecutionErrors.InvalidValueLength.name());
                }
            } else {
                handleExecutionError(DuplicateSequenceNameException.class, eea, ExecutionErrors.DuplicateSequenceName.name(),
                        sequenceType.getLastDetail().getSequenceTypeName(), sequenceName);
            }
        }
        
        return sequence;
    }

    public Sequence getSequenceByName(final ExecutionErrorAccumulator eea, final SequenceType sequenceType, final String sequenceName,
            final EntityPermission entityPermission) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getSequenceByName(sequenceType, sequenceName, entityPermission);

        if(sequence == null) {
            handleExecutionError(UnknownSequenceNameException.class, eea, ExecutionErrors.UnknownSequenceName.name(),
                    sequenceType.getLastDetail().getSequenceTypeName(), sequenceName);
        }

        return sequence;
    }

    public Sequence getSequenceByName(final ExecutionErrorAccumulator eea, final SequenceType sequenceType, final String sequenceName) {
        return getSequenceByName(eea, sequenceType, sequenceName, EntityPermission.READ_ONLY);
    }

    public Sequence getSequenceByNameForUpdate(final ExecutionErrorAccumulator eea, final SequenceType sequenceType, final String sequenceName) {
        return getSequenceByName(eea, sequenceType, sequenceName, EntityPermission.READ_WRITE);
    }

    public Sequence getSequenceByName(final ExecutionErrorAccumulator eea, final String sequenceTypeName, final String sequenceName,
            final EntityPermission entityPermission) {
        var sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(eea, sequenceTypeName);
        Sequence sequence = null;

        if(!eea.hasExecutionErrors()) {
            sequence = getSequenceByName(eea, sequenceType, sequenceName, entityPermission);
        }

        return sequence;
    }

    public Sequence getSequenceByName(final ExecutionErrorAccumulator eea, final String sequenceTypeName, final String sequenceName) {
        return getSequenceByName(eea, sequenceTypeName, sequenceName, EntityPermission.READ_ONLY);
    }

    public Sequence getSequenceByNameForUpdate(final ExecutionErrorAccumulator eea, final String sequenceTypeName, final String sequenceName) {
        return getSequenceByName(eea, sequenceTypeName, sequenceName, EntityPermission.READ_WRITE);
    }

    public Sequence getSequenceByUniversalSpec(final ExecutionErrorAccumulator eea, final SequenceUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceTypeName = universalSpec.getSequenceTypeName();
        var sequenceName = universalSpec.getSequenceName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(sequenceTypeName, sequenceName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        Sequence sequence = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            SequenceType sequenceType = null;
            
            if(sequenceTypeName == null) {
                if(allowDefault) {
                    sequenceType = sequenceControl.getDefaultSequenceType();

                    if(sequenceType == null) {
                        handleExecutionError(UnknownDefaultSequenceTypeException.class, eea, ExecutionErrors.UnknownDefaultSequenceType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(eea, sequenceTypeName);
            }

            if(!eea.hasExecutionErrors()) {
                if(sequenceName == null) {
                    if(allowDefault) {
                        sequence = sequenceControl.getDefaultSequence(sequenceType, entityPermission);

                        if(sequence == null) {
                            handleExecutionError(UnknownDefaultSequenceException.class, eea, ExecutionErrors.UnknownDefaultSequence.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    sequence = getSequenceByName(eea, sequenceType, sequenceName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Sequence.name());

            if(!eea.hasExecutionErrors()) {
                sequence = sequenceControl.getSequenceByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return sequence;
    }

    public Sequence getSequenceByUniversalSpec(final ExecutionErrorAccumulator eea, final SequenceUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSequenceByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Sequence getSequenceByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final SequenceUniversalSpec universalSpec,
            boolean allowDefault) {
        return getSequenceByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
