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

import com.echothree.model.control.sequence.common.SequenceChecksumTypes;
import com.echothree.model.control.sequence.common.SequenceEncoderTypes;
import com.echothree.model.control.sequence.common.exception.UnimplementedSequenceChecksumTypeException;
import com.echothree.model.control.sequence.common.exception.UnimplementedSequenceEncoderTypeException;
import com.echothree.model.control.sequence.common.exception.UnknownSequenceNameException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.checksum.Mod10SequenceChecksum;
import com.echothree.model.control.sequence.server.logic.checksum.Mod36SequenceChecksum;
import com.echothree.model.control.sequence.server.logic.checksum.NoneSequenceChecksum;
import com.echothree.model.control.sequence.server.logic.checksum.SequenceChecksum;
import com.echothree.model.control.sequence.server.logic.encoder.NoneSequenceEncoder;
import com.echothree.model.control.sequence.server.logic.encoder.ReverseSequenceEncoder;
import com.echothree.model.control.sequence.server.logic.encoder.ReverseSwapSequenceEncoder;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.SessionFactory;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SequenceGeneratorLogic
        extends BaseLogic {

    protected SequenceGeneratorLogic() {
        super();
    }

    public static SequenceGeneratorLogic getInstance() {
        return CDI.current().select(SequenceGeneratorLogic.class).get();
    }

    // --------------------------------------------------------------------------------
    //   Generation
    // --------------------------------------------------------------------------------

    public final static String NUMERIC_VALUES = "0123456789";
    public final static int NUMERIC_MAX_INDEX = NUMERIC_VALUES.length() - 1;
    public final static String ALPHABETIC_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final static int ALPHABETIC_MAX_INDEX = ALPHABETIC_VALUES.length() - 1;
    public final static String ALPHANUMERIC_VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final static int ALPHANUMERIC_MAX_INDEX = ALPHANUMERIC_VALUES.length() - 1;

    private final static int DEFAULT_CHUNK_SIZE = 10;

    private final static ConcurrentMap<Long, Deque<String>> sequenceDeques = new ConcurrentHashMap<>();

    private int getChunkSize(SequenceTypeDetail sequenceTypeDetail, SequenceDetail sequenceDetail) {
        var chunkSize = sequenceDetail.getChunkSize();

        if(chunkSize == null) {
            chunkSize = sequenceTypeDetail.getChunkSize();
        }

        return chunkSize == null ? DEFAULT_CHUNK_SIZE : chunkSize;
    }

    // If the SequenceEncoders are ever modified to do anything other than swap characters
    // around, getPattern(...) will need to have a special version of this. Right now, as it
    // is used to identify Sequences based on the different masks, those masks must be altered
    // to generate a regular expressions that's properly formatted for what's done here when
    // creating new encoded values during generation.
    private String encode(SequenceTypeDetail sequenceTypeDetail, String value) {
        var sequenceEncoderTypeName = sequenceTypeDetail.getSequenceEncoderType().getSequenceEncoderTypeName();
        var sequenceEncoderType = SequenceEncoderTypes.valueOf(sequenceEncoderTypeName);
        String encodedValue;

        switch(sequenceEncoderType) {
            case NONE -> encodedValue = NoneSequenceEncoder.getInstance().encode(value);
            case REVERSE -> encodedValue = ReverseSequenceEncoder.getInstance().encode(value);
            case REVERSE_SWAP -> encodedValue = ReverseSwapSequenceEncoder.getInstance().encode(value);
            default -> throw new UnimplementedSequenceEncoderTypeException();
        }

        return encodedValue;
    }

    private SequenceChecksum getSequenceChecksum(SequenceChecksumTypes sequenceChecksumType) {
        switch(sequenceChecksumType) {
            case NONE -> {
                return NoneSequenceChecksum.getInstance();
            }
            case MOD_10 -> {
                return Mod10SequenceChecksum.getInstance();
            }
            case MOD_36 -> {
                return Mod36SequenceChecksum.getInstance();
            }
            default -> throw new UnimplementedSequenceChecksumTypeException();
        }
    }

    private SequenceChecksum getSequenceChecksum(SequenceTypeDetail sequenceTypeDetail) {
        var sequenceChecksumType = sequenceTypeDetail.getSequenceChecksumType();
        SequenceChecksum result = null;

        // Needs to be very careful as the SequenceChecksumType may be null for a given SequenceTypeDetail.
        // If it is, we'll just leave sequenceChecksum null and return that.
        if(sequenceChecksumType != null) {
            var sequenceChecksumTypeName = sequenceChecksumType.getSequenceChecksumTypeName();

            result = getSequenceChecksum(SequenceChecksumTypes.valueOf(sequenceChecksumTypeName));
        }

        return result;
    }

    /**
     * Generate and return the next value for a given Sequence.
     * 
     * @return A unique value for the sequence is returned. Null will be returned when the
     * sequence is exhausted, the length of the mask is not equal to the length of the
     * value, or an invalid character is encountered in the mask.
     */
    public String getNextSequenceValue(Sequence sequence) {
        var sequenceEntityId = sequence.getPrimaryKey().getEntityId();
        var sequenceDeque = sequenceDeques.get(sequenceEntityId);
        String result = null;

        if(sequenceDeque == null) {
            // Create a new sequenceDeque (aka. a LinkedList), and try to put it into sequenceDeques.
            // If it is already there, the new one is discarded, and the one that was already there
            // is returned.
            var newSequenceDeque = new ArrayDeque<String>();

            sequenceDeque = sequenceDeques.putIfAbsent(sequenceEntityId, newSequenceDeque);
            if(sequenceDeque == null) {
                sequenceDeque = newSequenceDeque;
            }
        }

        synchronized(sequenceDeque) {
            try {
                result = sequenceDeque.removeFirst();
            } catch (NoSuchElementException nsee1) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                var sequenceSession = SessionFactory.getInstance().getSession();
                var sequenceValue = sequenceControl.getSequenceValueForUpdateInSession(sequenceSession, sequence);

                if(sequenceValue != null) {
                    var sequenceDetail = sequence.getLastDetail();
                    var sequenceTypeDetail = sequenceDetail.getSequenceType().getLastDetail();
                    var prefix = sequenceTypeDetail.getPrefix();
                    var suffix = sequenceTypeDetail.getSuffix();
                    var chunkSize = getChunkSize(sequenceTypeDetail, sequenceDetail);
                    var mask = sequenceDetail.getMask();
                    var maskChars = mask.toCharArray();
                    var value = sequenceValue.getValue();
                    var valueLength = value.length();
                    var valueChars = value.toCharArray();

                    // Mask and its value must be the same length.
                    if(valueLength == mask.length()) {
                        for(var i = 0; i < chunkSize; i++) {
                            // Step through the string from the right to the left.
                            var forceIncrement = false;

                            for(var index = valueLength - 1; index > -1; index--) {
                                var maskChar = maskChars[index];
                                var valueChar = valueChars[index];

                                switch(maskChar) {
                                    case '9' -> {
                                        var currentIndex = NUMERIC_VALUES.indexOf(valueChar);
                                        if(currentIndex != -1) {
                                            int newCharIndex;
                                            if(currentIndex == NUMERIC_MAX_INDEX) {
                                                newCharIndex = 0;
                                                forceIncrement = true;
                                            } else {
                                                newCharIndex = currentIndex + 1;
                                            }
                                            valueChars[index] = NUMERIC_VALUES.charAt(newCharIndex);
                                        } else {
                                            value = null;
                                        }
                                    }
                                    case 'A' -> {
                                        var currentIndex = ALPHABETIC_VALUES.indexOf(valueChar);
                                        if(currentIndex != -1) {
                                            int newCharIndex;
                                            if(currentIndex == ALPHABETIC_MAX_INDEX) {
                                                newCharIndex = 0;
                                                forceIncrement = true;
                                            } else {
                                                newCharIndex = currentIndex + 1;
                                            }
                                            valueChars[index] = ALPHABETIC_VALUES.charAt(newCharIndex);
                                        } else {
                                            value = null;
                                        }
                                    }
                                    case 'Z' -> {
                                        var currentIndex = ALPHANUMERIC_VALUES.indexOf(valueChar);
                                        if(currentIndex != -1) {
                                            int newCharIndex;
                                            if(currentIndex == ALPHANUMERIC_MAX_INDEX) {
                                                newCharIndex = 0;
                                                forceIncrement = true;
                                            } else {
                                                newCharIndex = currentIndex + 1;
                                            }
                                            valueChars[index] = ALPHANUMERIC_VALUES.charAt(newCharIndex);
                                        } else {
                                            value = null;
                                        }
                                    }
                                }

                                // If an error occurred, or we do not need to increment any other positions in
                                // the sequences value, exit.
                                if((value == null) || !forceIncrement) {
                                    break;
                                }

                                // If we reach the start of the sequences value, and have not yet exited, the
                                // sequence is at its maximum possible value, exit.
                                if(index == 0) {
                                    value = null;
                                }

                                forceIncrement = false;
                            }

                            if(value != null) {
                                value = new String(valueChars);

                                var encodedValue = encode(sequenceTypeDetail, value);

                                var intermediateValue = (prefix != null ? prefix : "") + encodedValue + (suffix != null ? suffix : "");
                                var checksum = getSequenceChecksum(sequenceTypeDetail).calculate(intermediateValue);

                                sequenceDeque.add(intermediateValue + checksum);
                            }
                        }

                        sequenceValue.setValue(value);

                        try {
                            result = sequenceDeque.removeFirst();
                        } catch (EmptyStackException ese2) {
                            // Shouldn't happen, if it does, result stays null
                        }
                    }
                }

                sequenceSession.close();
            }
        }

        return result;
    }

    public String getNextSequenceValue(final ExecutionErrorAccumulator eea, final Sequence sequence) {
        return getNextSequenceValue(sequence);
    }

    public String getNextSequenceValue(final ExecutionErrorAccumulator eea, final SequenceType sequenceType) {
        var sequence = getDefaultSequence(eea, sequenceType);

        return hasExecutionErrors(eea) ? null : getNextSequenceValue(eea, sequence);
    }

    public String getNextSequenceValue(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        var sequence = getDefaultSequence(eea, sequenceTypeName);

        return hasExecutionErrors(eea) ? null : getNextSequenceValue(eea, sequence);
    }

    public Sequence getDefaultSequence(final ExecutionErrorAccumulator eea, final SequenceType sequenceType) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequence(sequenceType);

        if(sequence == null) {
            handleExecutionError(UnknownSequenceNameException.class, eea, ExecutionErrors.MissingDefaultSequence.name(), sequenceType.getLastDetail().getSequenceTypeName());
        }

        return sequence;
    }

    public Sequence getDefaultSequence(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        var sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(eea, sequenceTypeName);
        Sequence sequence = null;

        if(!hasExecutionErrors(eea)) {
            sequence = getDefaultSequence(eea, sequenceType);
        }

        return sequence;
    }

    // --------------------------------------------------------------------------------
    //   Identification
    // --------------------------------------------------------------------------------

    private StringBuilder getPatternFromMask(final String mask) {
        var maskChars = mask.toCharArray();
        var pattern = new StringBuilder();

        for(var maskChar : maskChars) {
            switch(maskChar) {
                case '9' -> pattern.append("[\\p{Digit}]");
                case 'A' -> pattern.append("\\p{Upper}");
                case 'Z' -> pattern.append("[\\p{Upper}\\p{Digit}]");
            }
        }

        return pattern;
    }

    private String getPattern(final Sequence sequence) {
        var pattern = new StringBuilder("^");
        var sequenceDetail = sequence.getLastDetail();
        var sequenceTypeDetail = sequenceDetail.getSequenceType().getLastDetail();
        var prefix = sequenceTypeDetail.getPrefix();
        var suffix = sequenceTypeDetail.getSuffix();
        var mask = sequenceDetail.getMask();

        if(prefix != null) {
            pattern.append(Pattern.quote(prefix));
        }

        var encodedMask = encode(sequenceTypeDetail, mask);
        pattern.append(getPatternFromMask(encodedMask));

        if(suffix != null) {
            pattern.append(Pattern.quote(suffix));
        }

        pattern.append(getSequenceChecksum(sequenceTypeDetail).regexp());

        return pattern.append('$').toString();
    }

    public SequenceType identifySequenceType(final String value) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceTypes = sequenceControl.getSequenceTypes();
        SequenceType result = null;

        // Check all Sequence Types...
        for(var sequenceType : sequenceTypes) {
            // ...and each Sequence within them.
            for(var sequence : sequenceControl.getSequencesBySequenceType(sequenceType)) {
                // Check the regexp that's generated for this sequence against the value.
                if(value.matches(getPattern(sequence))) {
                    // If the regexp matches, check the checksum. If it matches, we've
                    // probably got a match. If not, continue looking for other matches.
                    if(verifyValue(sequenceType, value)) {
                        result = sequenceType;
                        break;
                    }
                }
            }

            // If the SequenceType was found, break out of the outer for loop as well.
            if(result != null) {
                break;
            }
        }

        return result;
    }

    // --------------------------------------------------------------------------------
    //   Verification
    // --------------------------------------------------------------------------------

    public boolean verifyValue(final SequenceType sequenceType, final String value) {
        var sequenceTypeDetail = sequenceType.getLastDetail();
        var sequenceChecksum = getSequenceChecksum(sequenceTypeDetail);

        return sequenceChecksum == null || sequenceChecksum.verify(value);
    }

}
