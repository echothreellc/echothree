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

package com.echothree.control.user.party.server.command;

import com.echothree.util.common.command.EditMode;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/** These cases also execute against the pre-refactor command for behavior comparison. */
public class EditPartyTypeAuditPolicyCompatibilityTest {
    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void lockFormatsEverySettingAndNullableTimePair(boolean populated) throws Exception {
        var command = new AuditPolicyCommandFixture(EditMode.LOCK, null);
        command.populated = populated;
        var result = command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        assertEquals(1, command.locks);
        var expected = new LinkedHashMap<String, String>();
        AuditPolicyCommandFixture.INITIAL.forEach((name, value) -> expected.put(name, populated || value instanceof Boolean ? value.toString() : null));
        expected.put("RetainUserVisitsTime", populated ? "1" : null);
        expected.put("RetainUserVisitsTimeUnitOfMeasureTypeName", populated ? "HOUR" : null);
        expected.forEach((name, value) -> assertEquals(value, result.getEdit().get(name), name));
    }

    @ParameterizedTest
    @ValueSource(strings = { "AuditCommands", "RetainUserVisitsTime" })
    public void changingEachSettingPreservesEveryOtherSetting(String field) throws Exception {
        var command = new AuditPolicyCommandFixture(EditMode.UPDATE, null);
        var original = AuditPolicyCommandFixture.INITIAL.get(field);
        Object updated = switch(original) {
            case Boolean booleanValue -> !booleanValue;
            case Long longValue -> 7200000L;
            case Integer integerValue -> 25;
            default -> throw new AssertionError(original);
        };
        command.set(field, updated.toString());
        command.validateRequest();
        command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        assertEquals(1, command.writes);
        assertEquals(1, command.unlocks);
        for(var entry : AuditPolicyCommandFixture.INITIAL.entrySet()) {
            var actual = AuditPolicyCommandFixture.read(command.savedValue, entry.getKey());
            var expected = entry.getKey().equals(field) ? updated : entry.getValue();
            assertEquals(expected, actual, entry.getKey());
        }
        assertEquals(command.partyType.getPrimaryKey(), command.savedValue.getPartyTypePK());
        assertEquals(command.policy.getPrimaryKey(), command.savedValue.getPartyTypeAuditPolicyPK());
    }

    @Test
    public void clearingAllOptionalSettingsPreservesRequiredBooleans() throws Exception {
        var command = new AuditPolicyCommandFixture(EditMode.UPDATE, null);
        command.clearOptionalEdit();
        command.validateRequest();
        command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        for(var entry : AuditPolicyCommandFixture.INITIAL.entrySet()) {
            assertEquals(entry.getValue() instanceof Boolean ? entry.getValue() : null,
                    AuditPolicyCommandFixture.read(command.savedValue, entry.getKey()), entry.getKey());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "MS", "SECOND", "MINUTE", "HOUR" })
    public void everyDurationConvertsThroughActualUnitEquivalents(String unit) throws Exception {
        var command = new AuditPolicyCommandFixture(EditMode.UPDATE, null);
        for(var field : AuditPolicyCommandFixture.TIME_FIELDS) {
            command.set(field, "2");
            command.set(field + "UnitOfMeasureTypeName", unit);
        }
        command.validateRequest();
        command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        long expected = switch(unit) {
            case "MS" -> 2L;
            case "SECOND" -> 2000L;
            case "MINUTE" -> 120000L;
            case "HOUR" -> 7200000L;
            default -> throw new AssertionError(unit);
        };
        for(var field : AuditPolicyCommandFixture.TIME_FIELDS) {
            assertEquals(expected, AuditPolicyCommandFixture.read(command.savedValue, field), field);
        }
    }

    @Test
    public void unchangedEditDoesNotModifyStoredValue() throws Exception {
        var command = new AuditPolicyCommandFixture(EditMode.UPDATE, null);
        command.validateRequest();
        command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        assertNotNull(command.savedValue);
        assertFalse(command.savedValue.hasBeenModified());
        assertEquals(0, command.writes);
    }
    @ParameterizedTest
    @ValueSource(strings = { "RetainUserVisitsTime" })
    public void clearingEachOptionalSettingPreservesEveryOtherSetting(String field) throws Exception {
        var command = new AuditPolicyCommandFixture(EditMode.UPDATE, null);
        command.set(field, null);
        if(AuditPolicyCommandFixture.TIME_FIELDS.contains(field)) {
            command.set(field + "UnitOfMeasureTypeName", null);
        }
        command.validateRequest();
        command.executeEdit();
        assertFalse(command.hasExecutionErrors());
        for(var entry : AuditPolicyCommandFixture.INITIAL.entrySet()) {
            assertEquals(entry.getKey().equals(field) ? null : entry.getValue(),
                    AuditPolicyCommandFixture.read(command.savedValue, entry.getKey()), entry.getKey());
        }
    }

    @ParameterizedTest
    @ValueSource(longs = { 0L, 1L, 999L, 1000L, 60000L, 3600000L, Long.MAX_VALUE })
    public void lockAndUpdateRoundTripEachTimeSettingWithoutLoss(long value) throws Exception {
        for(var field : AuditPolicyCommandFixture.TIME_FIELDS) {
            var locked = new AuditPolicyCommandFixture(EditMode.LOCK, null);
            locked.initialOverrides.put(field, value);
            var edit = locked.executeEdit().getEdit();
            var updated = new AuditPolicyCommandFixture(EditMode.UPDATE, null);
            updated.initialOverrides.put(field, value);
            for(var name : AuditPolicyCommandFixture.INITIAL.keySet()) {
                updated.set(name, (String)edit.get(name));
            }
            for(var name : AuditPolicyCommandFixture.TIME_FIELDS) {
                updated.set(name + "UnitOfMeasureTypeName", (String)edit.get(name + "UnitOfMeasureTypeName"));
            }
            updated.validateRequest();
            updated.executeEdit();
            assertFalse(updated.hasExecutionErrors());
            assertEquals(value, AuditPolicyCommandFixture.read(updated.savedValue, field));
            assertEquals(0, updated.writes);
        }
    }

}
