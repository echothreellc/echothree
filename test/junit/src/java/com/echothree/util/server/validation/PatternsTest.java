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

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatternsTest {

    // Freeze the old expressions to check the accepted language of each simplification.
    private static final Map<String, Pattern> LEGACY_PATTERNS = Map.ofEntries(
            Map.entry("Option", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$")),
            Map.entry("TableNameSingular", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$")),
            Map.entry("EntityName", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$")),
            Map.entry("EntityName2", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+:([a-z]|[A-Z]|[0-9]|-|_)+$")),
            Map.entry("EntityNames", Pattern.compile("^(|([a-z]|[A-Z]|[0-9]|-|_)+:)*([a-z]|[A-Z]|[0-9]|-|_)+$")),
            Map.entry("EntityRef", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_|\\.)+\\.([a-z]|[A-Z]|[0-9]|-|_|\\.)+\\.([0-9])+$")),
            Map.entry("HostName", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|\\.)+$")),
            Map.entry("DomainName", Pattern.compile("^([0-9a-z\\-])+\\.(com|net|int|org|mil|edu|gov|[a-z][a-z]|aero|biz|coop|info|museum|name|pro)$")),
            Map.entry("Tag", Pattern.compile("^([a-z]|[0-9]|-|_| )+$")),
            Map.entry("TimeZoneName", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_|\\.|/)+$")),
            Map.entry("Key", Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$"))
    );

    private void comparePatterns(String candidate) throws Exception {
        for(var entry : LEGACY_PATTERNS.entrySet()) {
            var currentPattern = (Pattern)Patterns.class.getField(entry.getKey()).get(null);
            var expected = entry.getValue().matcher(candidate).matches();
            var actual = currentPattern.matcher(candidate).matches();

            assertEquals(expected, actual, () -> entry.getKey() + " changed acceptance for " + candidate);
        }
    }

    private void compareCombinations(String prefix, int remainingLength) throws Exception {
        comparePatterns(prefix);
        if(remainingLength > 0) {
            for(char character : new char[]{'a', 'Z', '0', '_', '-', ':', '.', '/', '+', ' '}) {
                compareCombinations(prefix + character, remainingLength - 1);
            }
        }
    }

    @Test
    public void exhaustiveShortInputsPreserveAcceptance() throws Exception {
        compareCombinations("", 5);
    }

    @Test
    public void unicodeAndLineTerminatorsPreserveAcceptance() throws Exception {
        for(int character = Character.MIN_VALUE; character <= Character.MAX_VALUE; character++) {
            var value = String.valueOf((char)character);
            comparePatterns(value);
            comparePatterns("a" + value + ":b");
            comparePatterns("a" + value + "/b");
        }
        for(var ending : List.of("\n", "\r", "\r\n", "\u0085", "\u2028", "\u2029")) {
            comparePatterns("abc" + ending);
            comparePatterns("abc.com" + ending);
            comparePatterns("a.b.1" + ending);
        }
    }

    @Test
    public void structuredAndRandomInputsPreserveAcceptance() throws Exception {
        var examples = List.of("abc", "abc:def", "a:b:c", "a::b", ":a", "a:",
                "a.b.1", "a..b.123", "..1", "...1", "a.b.1!", "a.b.-1",
                "example.com", "example.museum", "EXAMPLE.com", "example.invalid",
                "text/plain", "application/vnd.api+json", "a.+b/c+d", ".+/x",
                ".+.+/x", "a.b/c", "a+b/c", "a./b", "a+/b", "a/b/c",
                "America/Chicago", "lower case tag", "UPPER CASE TAG");
        for(var example : examples) {
            comparePatterns(example);
        }
        var random = new Random(8845);
        var alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-.+:/ !@\r\n\t";
        for(int sample = 0; sample < 10_000; sample++) {
            var candidate = new StringBuilder();
            var length = random.nextInt(100);
            for(int index = 0; index < length; index++) {
                candidate.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
            comparePatterns(candidate.toString());
            comparePatterns(candidate + ".com");
            comparePatterns(candidate + "/plain");
        }
    }

    @Test
    public void longInputsDoNotOverflowTheStack() throws Exception {
        var longName = "a".repeat(100_000);
        for(var patternName : List.of("Option", "TableNameSingular", "EntityName", "HostName",
                "Tag", "TimeZoneName", "Key")) {
            var pattern = (Pattern)Patterns.class.getField(patternName).get(null);
            assertTrue(pattern.matcher(longName).matches(), patternName);
            assertFalse(pattern.matcher(longName + "!").matches(), patternName);
        }
        assertTrue(Patterns.EntityName2.matcher(longName + ":b").matches());
        assertTrue(Patterns.EntityNames.matcher("a:".repeat(50_000) + "b").matches());
        assertFalse(Patterns.EntityNames.matcher("a:".repeat(50_000) + "!").matches());
        assertTrue(Patterns.DomainName.matcher(longName + ".com").matches());
        assertTrue(Patterns.MimeType.matcher("a" + ".+".repeat(50_000) + "/plain").matches());
        assertFalse(Patterns.MimeType.matcher("a" + ".+".repeat(50_000) + "/!").matches());
        assertTrue(Patterns.EntityRef.matcher(longName + ".b.123").matches());
    }

    @Test
    public void acceptsBareMediaTypeNamesWithValidPunctuation() {
        for(var value : List.of("text/plain", "Application/JSON", "application/vnd.api+json",
                "a.b/c", "a+b/c", "a!#$&^_.+-/b!#$&^_.+-", "x-custom/unregistered", "1/2")) {
            assertTrue(Patterns.MimeType.matcher(value).matches(), value);
        }
        for(char character : "!#$&^_.+-".toCharArray()) {
            assertTrue(Patterns.MimeType.matcher("a" + character + "/b").matches());
            assertTrue(Patterns.MimeType.matcher("a/b" + character).matches());
        }
    }

    @Test
    public void rejectsParametersAndMalformedMediaTypeNames() {
        for(var value : List.of("", "text", "/plain", "text/", "text/plain/extra",
                "text/plain; charset=UTF-8", "text /plain", "text/ plain", "text/pla\tin",
                "text/plain\r\n", "tést/plain", ".text/plain", "text/+json", "*/*",
                "text/pl@in", "text/pl=ain", "text/pl:ain", "text/pl(ain)", "text/pl[ain]")) {
            assertFalse(Patterns.MimeType.matcher(value).matches(), value);
        }
    }
}
