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

import com.echothree.model.control.sequence.common.SequenceEncoderTypes;
import com.echothree.model.control.sequence.common.exception.UnimplementedSequenceEncoderTypeException;
import com.echothree.model.control.sequence.common.exception.UnknownSequenceNameException;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.sequence.server.logic.encoder.NoneSequenceEncoder;
import com.echothree.model.control.sequence.server.logic.encoder.ReverseSequenceEncoder;
import com.echothree.model.control.sequence.server.logic.encoder.ReverseSwapSequenceEncoder;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDetail;
import com.echothree.model.data.sequence.server.entity.SequenceValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.SessionFactory;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class SequenceGeneratorLogic
        extends BaseLogic {

    private SequenceGeneratorLogic() {
        super();
    }
    
    private static class SequenceGeneratorLogicHolder {
        static SequenceGeneratorLogic instance = new SequenceGeneratorLogic();
    }
    
    public static SequenceGeneratorLogic getInstance() {
        return SequenceGeneratorLogicHolder.instance;
    }


    // --------------------------------------------------------------------------------
    //   Generation
    // --------------------------------------------------------------------------------

    private final static String numericValues = "0123456789";
    private final static int numericMaxIndex = numericValues.length() - 1;
    private final static String alphabeticValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static int alphabeticMaxIndex = alphabeticValues.length() - 1;
    private final static String alphanumericValues = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static int alphanumericMaxIndex = alphanumericValues.length() - 1;
    private final static int defaultChunkSize = 10;

    private final static ConcurrentMap<Long, Deque<String>> sequenceDeques = new ConcurrentHashMap<>();

    private int getChunkSize(SequenceTypeDetail sequenceTypeDetail, SequenceDetail sequenceDetail) {
        Integer chunkSize = sequenceDetail.getChunkSize();

        if(chunkSize == null) {
            chunkSize = sequenceTypeDetail.getChunkSize();
        }

        return chunkSize == null ? defaultChunkSize : chunkSize;
    }

    private String encode(SequenceTypeDetail sequenceTypeDetail, String value) {
        var sequenceEncoderTypeName = sequenceTypeDetail.getSequenceEncoderType().getSequenceEncoderTypeName();
        var sequenceEncoderType = SequenceEncoderTypes.valueOf(sequenceEncoderTypeName);
        String encodedValue;

        switch(sequenceEncoderType) {
            case NONE:
                encodedValue = NoneSequenceEncoder.getInstance().encode(value);
                break;
            case REVERSE:
                encodedValue = ReverseSequenceEncoder.getInstance().encode(value);
                break;
            case REVERSE_SWAP:
                encodedValue = ReverseSwapSequenceEncoder.getInstance().encode(value);
                break;
            default:
                throw new UnimplementedSequenceEncoderTypeException();
        }

        return encodedValue;
    }

    /**
     * @return A unique value for the sequence is returned. Null will be returned when the
     * sequence is exhausted, the length of the mask is not equal to the length of the
     * value, or an invalid character is encountered in the mask.
     */
    public String getNextSequenceValue(Sequence sequence) {
        String result = null;
        Long sequenceEntityId = sequence.getPrimaryKey().getEntityId();
        Deque<String> sequenceDeque = sequenceDeques.get(sequenceEntityId);

        if(sequenceDeque == null) {
            // Create a new sequenceDeque (aka. a LinkedList), and try to put it into sequenceDeques.
            // If it is already there, the new one is discarded, and the one that was already there
            // is returned.
            Deque<String> newSequenceDeque = new ArrayDeque<>();

            sequenceDeque = sequenceDeques.putIfAbsent(sequenceEntityId, newSequenceDeque);
            if(sequenceDeque == null) {
                sequenceDeque = newSequenceDeque;
            }
        }

        synchronized(sequenceDeque) {
            try {
                result = sequenceDeque.removeFirst();
            } catch (NoSuchElementException nsee1) {
                var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                Session sequenceSession = SessionFactory.getInstance().getSession();
                SequenceValue sequenceValue = sequenceControl.getSequenceValueForUpdateInSession(sequenceSession, sequence);

                if(sequenceValue != null) {
                    SequenceDetail sequenceDetail = sequence.getLastDetail();
                    SequenceTypeDetail sequenceTypeDetail = sequenceDetail.getSequenceType().getLastDetail();
                    String prefix = sequenceTypeDetail.getPrefix();
                    String suffix = sequenceTypeDetail.getSuffix();
                    int chunkSize = getChunkSize(sequenceTypeDetail, sequenceDetail);
                    String mask = sequenceDetail.getMask();
                    char maskChars[] = mask.toCharArray();
                    String value = sequenceValue.getValue();
                    int valueLength = value.length();
                    char valueChars[] = value.toCharArray();

                    // Mask and its value must be the same length.
                    if(valueLength == mask.length()) {
                        for(int i = 0; i < chunkSize; i++) {
                            // Step through the string from the right to the left.
                            boolean forceIncrement = false;

                            for(int index = valueLength - 1; index > -1; index--) {
                                char maskChar = maskChars[index];
                                char valueChar = valueChars[index];

                                switch(maskChar) {
                                    case '9': {
                                        int currentIndex = numericValues.indexOf(valueChar);
                                        if(currentIndex != -1) {
                                            int newCharIndex;
                                            if(currentIndex == numericMaxIndex) {
                                                newCharIndex = 0;
                                                forceIncrement = true;
                                            } else {
                                                newCharIndex = currentIndex + 1;
                                            }
                                            valueChars[index] = numericValues.charAt(newCharIndex);
                                        } else {
                                            value = null;
                                        }
                                    }
                                    break;
                                    case 'A': {
                                        int currentIndex = alphabeticValues.indexOf(valueChar);
                                        if(currentIndex != -1) {
                                            int newCharIndex;
                                            if(currentIndex == alphabeticMaxIndex) {
                                                newCharIndex = 0;
                                                forceIncrement = true;
                                            } else {
                                                newCharIndex = currentIndex + 1;
                                            }
                                            valueChars[index] = alphabeticValues.charAt(newCharIndex);
                                        } else {
                                            value = null;
                                        }
                                    }
                                    break;
                                    case 'Z': {
                                        int currentIndex = alphanumericValues.indexOf(valueChar);
                                        if(currentIndex != -1) {
                                            int newCharIndex;
                                            if(currentIndex == alphanumericMaxIndex) {
                                                newCharIndex = 0;
                                                forceIncrement = true;
                                            } else {
                                                newCharIndex = currentIndex + 1;
                                            }
                                            valueChars[index] = alphanumericValues.charAt(newCharIndex);
                                        } else {
                                            value = null;
                                        }
                                    }
                                    break;
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

                                // TODO: checksum
                                String checksum = ""; // placeholder

                                sequenceDeque.add(new StringBuilder().append(prefix != null ? prefix : "").append(encodedValue).append(checksum).append(suffix != null ? suffix : "").toString());
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
        Sequence sequence = getDefaultSequence(eea, sequenceType);

        return hasExecutionErrors(eea) ? null : getNextSequenceValue(eea, sequence);
    }

    public String getNextSequenceValue(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        Sequence sequence = getDefaultSequence(eea, sequenceTypeName);

        return hasExecutionErrors(eea) ? null : getNextSequenceValue(eea, sequence);
    }

    public Sequence getDefaultSequence(final ExecutionErrorAccumulator eea, final SequenceType sequenceType) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequence(sequenceType);

        if(sequence == null) {
            handleExecutionError(UnknownSequenceNameException.class, eea, ExecutionErrors.MissingDefaultSequence.name(), sequenceType.getLastDetail().getSequenceTypeName());
        }

        return sequence;
    }

    public Sequence getDefaultSequence(final ExecutionErrorAccumulator eea, final String sequenceTypeName) {
        SequenceType sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(eea, sequenceTypeName);
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
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
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
