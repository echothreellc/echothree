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

package com.echothree.model.control.search.server.search;

import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class AttributeQueryDecimalTest {

    private static class TestParser extends AttributeQueryParserUtils {
        TestParser(DummyExecutionErrorAccumulator errors) {
            super(errors, null, null, null, null, null, null, null, null);
        }
        Integer latitude(String input) { return latitudeValueOf(input); }
        Integer longitude(String input) { return longitudeValueOf(input); }
    }

    @ParameterizedTest
    @ValueSource(strings = {"text", "1a2", "1.2.3", "1.0000001", "1-", "1,000", "999999999999999999999"})
    public void searchReportsInvalidInputWithoutLeakingParserExceptions(String input) {
        var errors = new DummyExecutionErrorAccumulator();
        var parser = new TestParser(errors);
        assertNull(parser.latitude(input));
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR, ExecutionErrors.InvalidFormat.name()));
        assertNull(parser.longitude(input));
    }

    @Test
    public void validCoordinatesScaleExactlyAndMarkersUseDomainBounds() {
        var errors = new DummyExecutionErrorAccumulator();
        var parser = new TestParser(errors);
        assertEquals(-1, parser.latitude("-0.000001"));
        assertEquals(90000000, parser.latitude("MAX_VALUE"));
        assertEquals(-90000000, parser.latitude("MIN_VALUE"));
        assertEquals(180000000, parser.longitude("MAX_VALUE"));
        assertEquals(-180000000, parser.longitude("MIN_VALUE"));
        assertFalse(errors.hasExecutionErrors());
    }
}
