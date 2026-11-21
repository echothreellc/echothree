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

package com.echothree.util.server.validation;

import com.echothree.util.common.message.Messages;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.validation.fieldtype.RegularExpressionFieldType;
import com.google.re2j.Pattern;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfiguredPatternsTest {

    private static final List<String> SHIPPED_PATTERNS = List.of(
            "^[0-9][0-9][0-9]$",
            "^[A-Z][A-Z][A-Z]$",
            "^[A-Z][A-Z]$",
            "^([0-9]+)$",
            "^[0-9][0-9][0-9]([ -\\\\.]|)[0-9][0-9][0-9][0-9]$",
            "^[0-9][0-9][0-9][0-9][0-9](-|)(|[0-9][0-9][0-9][0-9])$",
            "^[A-Z][0-9][A-Z](-| )[0-9][A-Z][0-9]$",
            "^[0-9][0-9][0-9][0-9][0-9]$",
            "^[0-9 -\\\\.]*$",
            "^6011[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$",
            "^4[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]([0-9][0-9][0-9]|)$",
            "^5[1-5][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$",
            "^3(4|7)[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$",
            "^[A-Z0-9]$",
            "^[A-Z]$",
            "^[0-9][0-9]$",
            "^[A-Z][A-Z][A-Z][A-Z]$",
            "(.*\\.|^|.*|\\[('|\"))(c|C)lass(\\.|('|\")]|\\[).*"
    );

    private String validatePattern(String expression, Messages messages) {
        var definition = new FieldDefinition("Pattern", FieldType.REGULAR_EXPRESSION, true, null, null);
        var validator = new RegularExpressionFieldType(null, null, messages, expression,
                new String[]{"Pattern"}, definition);

        return validator.validate();
    }

    @Test
    public void acceptsSupportedPatternsAndRejectsJavaOnlyFeatures() {
        for(var expression : SHIPPED_PATTERNS) {
            var messages = new Messages();
            assertEquals(expression, validatePattern(expression, messages), expression);
            assertTrue(messages.isEmpty());
        }
        for(var expression : List.of("(?=a)a", "(?<=a)b", "(a)\\1", "a++", "[")) {
            var messages = new Messages();
            assertNull(validatePattern(expression, messages), expression);
            assertTrue(messages.containsKey("Pattern", Validator.ERROR_INVALID_FORMAT), expression);
        }
    }

    @Test
    public void shippedPatternsPreserveJavaMatchingResults() {
        var examples = List.of("", "123", "12345", "12345-6789", "K1A 0B1", "ABC", "ABCD",
                "515", "555-1212", "555.1212", "555 1212", "4111111111111111", "123\n",
                "class.name", "nested.class.name", "value[\"Class\"]", "ordinary", "é", "１２３");
        for(var expression : SHIPPED_PATTERNS) {
            var javaPattern = java.util.regex.Pattern.compile(expression);
            var linearPattern = Pattern.compile(expression);
            for(var example : examples) {
                assertEquals(javaPattern.matcher(example).matches(), linearPattern.matcher(example).matches(),
                        expression + ": " + example);
            }
            var random = new Random(8824);
            var alphabet = "ABCxyz01579 -._[]\"\\\r\n";
            for(int sample = 0; sample < 2_000; sample++) {
                var candidate = new StringBuilder();
                var length = random.nextInt(30);
                for(int index = 0; index < length; index++) {
                    candidate.append(alphabet.charAt(random.nextInt(alphabet.length())));
                }
                var value = candidate.toString();
                assertEquals(javaPattern.matcher(value).matches(), linearPattern.matcher(value).matches(), expression);
            }
        }
    }

    @Test
    public void nestedQuantifiersHandleLongNonMatchingInputs() {
        assertFalse(Pattern.matches("(a+)+$", "a".repeat(100_000) + "!"));
        assertFalse(Pattern.matches("(a|aa)+$", "a".repeat(100_000) + "!"));
    }

    @Test
    public void quotedSequenceAffixesRemainLiteral() {
        var prefix = "INV.[";
        var suffix = "]+";
        var expression = "^" + Pattern.quote(prefix) + "[0-9]+" + Pattern.quote(suffix) + "$";
        assertTrue(Pattern.matches(expression, "INV.[123]+"));
        assertFalse(Pattern.matches(expression, "INVx123"));
    }
}
