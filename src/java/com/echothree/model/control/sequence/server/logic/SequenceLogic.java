// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.exception.DuplicateSequenceNameException;
import com.echothree.model.control.sequence.common.exception.InvalidValueLengthException;
import com.echothree.model.control.sequence.common.exception.UnknownSequenceNameException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SequenceLogic
        extends BaseLogic {
    
    private SequenceLogic() {
        super();
    }
    
    private static class SequenceLogicHolder {
        static SequenceLogic instance = new SequenceLogic();
    }
    
    public static SequenceLogic getInstance() {
        return SequenceLogicHolder.instance;
    }
    
    public Sequence createSequence(final ExecutionErrorAccumulator eea, final String sequenceTypeName, final String sequenceName,
            final String value, final String mask, final Integer chunkSize, final Boolean isDefault, final Integer sortOrder,
            final String description, final Language language, final BasePK createdBy) {
        SequenceType sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(eea, sequenceTypeName);
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
                        char maskChars[] = mask.toCharArray();
                        int maskLength = mask.length();
                        StringBuilder valueBuilder = new StringBuilder(maskLength);

                        for(int index = 0; index < maskLength; index++) {
                            char maskChar = maskChars[index];

                            switch(maskChar) {
                                case '9': {
                                    valueBuilder.append('0');
                                }
                                break;
                                case 'A': {
                                    valueBuilder.append('A');
                                }
                                break;
                                case 'Z': {
                                    valueBuilder.append('0');
                                }
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
    
    public Sequence getSequenceByName(final ExecutionErrorAccumulator eea, final SequenceType sequenceType, final String sequenceName) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getSequenceByName(sequenceType, sequenceName);

        if(sequence == null) {
            handleExecutionError(UnknownSequenceNameException.class, eea, ExecutionErrors.UnknownSequenceName.name(), sequenceType.getLastDetail().getSequenceTypeName(),
                    sequenceName);
        }

        return sequence;
    }
    
}
