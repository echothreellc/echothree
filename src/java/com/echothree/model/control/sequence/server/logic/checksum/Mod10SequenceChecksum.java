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

package com.echothree.model.control.sequence.server.logic.checksum;

import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import java.nio.charset.StandardCharsets;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class Mod10SequenceChecksum
        implements SequenceChecksum {

    protected Mod10SequenceChecksum() {
        super();
    }

    public static Mod10SequenceChecksum getInstance() {
        return CDI.current().select(Mod10SequenceChecksum.class).get();
    }

    @Override
    public String calculate(String value) {
        var bytes = value.getBytes(StandardCharsets.UTF_8);
        var sum = 0;

        for(var i = 0; i < bytes.length; i++) {
            sum += (bytes [i] * (i + 1));
        }

        var modCharacter = new char[]{SequenceGeneratorLogic.NUMERIC_VALUES.charAt(sum % SequenceGeneratorLogic.NUMERIC_MAX_INDEX)};
        return new String(modCharacter);
    }

    @Override
    public String regexp() {
        return "[\\p{Digit}]";
    }

    @Override
    public boolean verify(String value) {
        var codePoints = value.codePoints().toArray();
        var length = codePoints.length;
        var valueWithoutChecksum = new String(codePoints, 0, length - 1);
        var checksumFromValue = new String(codePoints, length - 1, 1);
        var checksum = calculate(valueWithoutChecksum);

        return checksumFromValue.equals(checksum);
    }

}
