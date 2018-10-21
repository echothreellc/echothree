// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.sequence.common.exception.UnknownSequenceTypeNameException;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.regex.Pattern;

public class SequenceTypeLogic
        extends BaseLogic {
    
    private SequenceTypeLogic() {
        super();
    }
    
    private static class SequenceLogicHolder {
        static SequenceTypeLogic instance = new SequenceTypeLogic();
    }
    
    public static SequenceTypeLogic getInstance() {
        return SequenceLogicHolder.instance;
    }
    
    public SequenceType getSequenceTypeByName(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        SequenceType sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);

        if(sequenceType == null) {
            handleExecutionError(UnknownSequenceTypeNameException.class, eea, ExecutionErrors.UnknownSequenceTypeName.name(), sequenceTypeName);
        }

        return sequenceType;
    }
    
    private StringBuilder getPatternFromMask(final String mask) {
        char maskChars[] = mask.toCharArray();
        int maskLength = maskChars.length;
        StringBuilder pattern = new StringBuilder();
        
        for(int index = 0 ; index < maskLength ; index++) {
            char maskChar = maskChars[index];
            
            switch(maskChar) {
                case '9': {
                    pattern.append("[\\p{Digit}]");
                }
                break;
                case 'A': {
                    pattern.append("\\p{Upper}");
                }
                break;
                case 'Z': {
                    pattern.append("[\\p{Upper}\\p{Digit}]");
                }
                break;
            }
        }
        
        return pattern;
    }
    
    private String getPattern(final Sequence sequence) {
        StringBuilder pattern = new StringBuilder("^");
        SequenceDetail sequenceDetail = sequence.getLastDetail();
        SequenceTypeDetail sequenceTypeDetail = sequenceDetail.getSequenceType().getLastDetail();
        String prefix = sequenceTypeDetail.getPrefix();
        String suffix = sequenceTypeDetail.getSuffix();
        
        if(prefix != null) {
            pattern.append(Pattern.quote(prefix));
        }
        
        pattern.append(getPatternFromMask(sequenceDetail.getMask()));
        
        if(suffix != null) {
            pattern.append(Pattern.quote(suffix));
        }
        
        // TODO: Account for a SequenceEncoderType.
        // TODO: Account for a SequenceChecksumType.
        
        return pattern.append('$').toString();
    }
    
    public SequenceType identifySequenceType(final String value) {
        SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        SequenceType result = null;
        List<SequenceType> sequenceTypes = sequenceControl.getSequenceTypes();
        
        for(SequenceType sequenceType : sequenceTypes) {
            List<Sequence> sequences = sequenceControl.getSequencesBySequenceType(sequenceType);
            
            for(Sequence sequence : sequences) {
                if(value.matches(getPattern(sequence))) {
                    result = sequenceType;
                    break;
                }
            }
            
            if(result != null) {
                break;
            }
        }
        
        return result;
    }
    
}
