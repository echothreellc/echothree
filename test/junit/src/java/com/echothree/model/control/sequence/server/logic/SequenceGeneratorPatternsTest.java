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

package com.echothree.model.control.sequence.server.logic;

import com.echothree.model.control.sequence.server.logic.checksum.Mod10SequenceChecksum;
import com.echothree.model.control.sequence.server.logic.checksum.Mod36SequenceChecksum;
import com.google.re2j.Pattern;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SequenceGeneratorPatternsTest {

    private String patternFromMask(String mask) throws ReflectiveOperationException {
        var method = SequenceGeneratorLogic.class.getDeclaredMethod("getPatternFromMask", String.class);
        method.setAccessible(true);

        return method.invoke(new SequenceGeneratorLogic(), mask).toString();
    }

    private void assertAlphabet(String expression, String alphabet) {
        var pattern = Pattern.compile(expression);

        for(var character = 0; character < 256; character++) {
            var value = Character.toString(character);
            assertEquals(alphabet.contains(value), pattern.matcher(value).matches(), expression + ": " + character);
        }
        for(var value : List.of("", "AA", "00", "Ａ", "１", "١", "Ω", "\n")) {
            assertFalse(pattern.matcher(value).matches(), expression + ": " + value);
        }
    }

    @Test
    void maskPatternsAcceptOnlyTheSequenceAlphabet() throws ReflectiveOperationException {
        assertAlphabet(patternFromMask("9"), SequenceGeneratorLogic.NUMERIC_VALUES);
        assertAlphabet(patternFromMask("A"), SequenceGeneratorLogic.ALPHABETIC_VALUES);
        assertAlphabet(patternFromMask("Z"), SequenceGeneratorLogic.ALPHANUMERIC_VALUES);
    }

    @Test
    void checksumPatternsAcceptOnlyTheirAsciiAlphabet() {
        assertAlphabet(new Mod10SequenceChecksum() { }.regexp(), SequenceGeneratorLogic.NUMERIC_VALUES);
        assertAlphabet(new Mod36SequenceChecksum() { }.regexp(), SequenceGeneratorLogic.ALPHANUMERIC_VALUES);
    }

    @Test
    void mixedMaskAndChecksumPatternsMatchLiteralAffixes() throws ReflectiveOperationException {
        var prefix = "INV.[";
        var suffix = "]+";
        var body = "^" + Pattern.quote(prefix) + patternFromMask("9AZ") + Pattern.quote(suffix);
        var numericPattern = Pattern.compile(body + new Mod10SequenceChecksum() { }.regexp() + "$");
        var alphanumericPattern = Pattern.compile(body + new Mod36SequenceChecksum() { }.regexp() + "$");

        assertTrue(numericPattern.matcher("INV.[0AZ]+9").matches());
        assertFalse(numericPattern.matcher("INV.[0AZ]+Z").matches());
        assertTrue(alphanumericPattern.matcher("INV.[9Z0]+Z").matches());
        assertTrue(alphanumericPattern.matcher("INV.[9Z0]+0").matches());
        for(var value : List.of("INVx[0AZ]+9", "INV.[0aZ]+9", "INV.[0Az]+9", "INV.[１AZ]+9",
                "INV.[0AZ]+9extra", "INV.[0AZ]+9\n")) {
            assertFalse(numericPattern.matcher(value).matches(), value);
            assertFalse(alphanumericPattern.matcher(value).matches(), value);
        }
    }

}
