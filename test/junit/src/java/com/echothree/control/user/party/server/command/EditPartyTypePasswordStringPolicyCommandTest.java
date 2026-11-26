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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class EditPartyTypePasswordStringPolicyCommandTest {
    static IntStream timeMasks() { return IntStream.range(0, 64); }

    private static void assertError(PasswordStringPolicyCommandFixture command, ExecutionErrors expected, Object... values) {
        var errors = command.getExecutionErrors().get(Messages.EXECUTION_ERROR);
        var error = errors.next();
        assertEquals(expected.name(), error.getKey());
        assertArrayEquals(values, error.getValues());
        assertFalse(errors.hasNext());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7 })
    public void acceptsExactlyOnePartyTypeIdentifierAndUsesPolicyPermission(int mask) throws Exception {
        for(var mode : EditMode.values()) {
            var command = new PasswordStringPolicyCommandFixture(mode, null);
            command.identifierMask(mask);
            command.executeEdit();
            var valid = Integer.bitCount(mask) == 1;
            if(valid) {
                assertFalse(command.hasExecutionErrors());
                assertEquals(1, command.parentLookups);
                assertEquals(List.of(mode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY), command.policyPermissions);
                assertSame(command.policy, command.getLockEntity(command.policy));
            } else {
                assertError(command, ExecutionErrors.InvalidParameterCount);
                assertEquals(0, command.parentLookups);
                assertEquals(0, command.policyLookups);
                assertEquals(0, command.locks);
                assertEquals(0, command.unlocks);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "UnknownUuid", "DeletedUuid", "UnknownEntityRef", "DeletedEntityRef", "InvalidEntityType", "InvalidVendor" })
    public void identifierResolutionFailureStopsPolicyLookup(String errorKey) throws Exception {
        var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, "entity:" + errorKey);
        command.identifierMask(errorKey.endsWith("EntityRef") ? 4 : 2);
        command.executeEdit();
        if(errorKey.startsWith("Invalid")) {
            assertError(command, ExecutionErrors.InvalidEntityType, "InvalidVendor".equals(errorKey) ? "OTHER" : "ECHO_THREE",
                    "InvalidVendor".equals(errorKey) ? "PartyType" : "PartyTypePasswordStringPolicy");
        } else {
            assertError(command, ExecutionErrors.valueOf(errorKey), errorKey.endsWith("EntityRef")
                    ? PasswordStringPolicyCommandFixture.ENTITY_REF : PasswordStringPolicyCommandFixture.UUID);
        }
        assertEquals(0, command.parentLookups);
        assertEquals(0, command.policyLookups);
        assertEquals(0, command.writes);
    }

    @ParameterizedTest
    @ValueSource(strings = { "parent", "policy", "kind" })
    public void missingDependenciesStopWorkAndKeepAvailableResult(String failure) throws Exception {
        for(var mode : EditMode.values()) {
            var command = new PasswordStringPolicyCommandFixture(mode, failure);
            command.executeEdit();
            if("kind".equals(failure) && mode == EditMode.ABANDON) {
                assertFalse(command.hasExecutionErrors());
                assertEquals(1, command.unlocks);
                assertEquals(0, command.kindLookups);
            } else {
                var expected = switch(failure) {
                    case "parent" -> ExecutionErrors.UnknownPartyTypeName;
                    case "policy" -> ExecutionErrors.UnknownPartyTypePasswordStringPolicy;
                    case "kind" -> ExecutionErrors.UnknownTimeUnitOfMeasureKind;
                    default -> throw new AssertionError(failure);
                };
                assertError(command, expected, "kind".equals(failure) ? new Object[0] : new Object[] { "CUSTOMER" });
                assertEquals(0, command.locks);
                assertEquals("kind".equals(failure) ? 1 : 0, command.transfers);
            }
            assertEquals(0, command.writes);
        }
    }

    @ParameterizedTest
    @MethodSource("timeMasks")
    public void everyTimeValueAndUnitPresenceCombinationIsCheckedBeforeLock(int mask) throws Exception {
        var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
        var valid = true;
        for(var index = 0; index < 3; index++) {
            var valuePresent = (mask & (1 << (index * 2))) != 0;
            var unitPresent = (mask & (1 << (index * 2 + 1))) != 0;
            var field = PasswordStringPolicyCommandFixture.TIME_FIELDS.get(index);
            command.set(field, valuePresent ? "5" : null);
            command.set(field + "UnitOfMeasureTypeName", unitPresent ? "MS" : null);
            valid &= valuePresent == unitPresent;
        }
        command.executeEdit();
        if(valid) {
            assertFalse(command.hasExecutionErrors());
            assertEquals(1, command.locks);
            for(var index = 0; index < 3; index++) {
                assertEquals((mask & (1 << (index * 2))) == 0 ? null : 5L,
                        PasswordStringPolicyCommandFixture.read(command.savedValue, PasswordStringPolicyCommandFixture.TIME_FIELDS.get(index)));
            }
        } else {
            assertError(command, ExecutionErrors.InvalidParameterCount);
            assertTrue(command.unitLookups.isEmpty());
            assertEquals(0, command.locks);
            assertEquals(0, command.writes);
            assertEquals(1, command.transfers);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2 })
    public void unknownTimeUnitReportsMatchingFieldErrorAndCompleteKey(int fieldIndex) throws Exception {
        var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
        command.set(PasswordStringPolicyCommandFixture.TIME_FIELDS.get(fieldIndex) + "UnitOfMeasureTypeName", "UNKNOWN");
        command.executeEdit();
        var error = switch(fieldIndex) {
            case 0 -> ExecutionErrors.UnknownMinimumPasswordLifetimeUnitOfMeasureTypeName;
            case 1 -> ExecutionErrors.UnknownMaximumPasswordLifetimeUnitOfMeasureTypeName;
            case 2 -> ExecutionErrors.UnknownExpirationWarningTimeUnitOfMeasureTypeName;
            default -> throw new AssertionError(fieldIndex);
        };
        assertError(command, error, "TIME", "UNKNOWN");
        assertEquals(fieldIndex + 1, command.unitLookups.size());
        assertEquals(0, command.locks);
        assertEquals(0, command.writes);
    }

    @ParameterizedTest
    @ValueSource(strings = { "ForceChangeAfterCreate", "ForceChangeAfterReset", "AllowChange" })
    public void requiredBooleansRejectMissingAndInvalidValues(String field) throws Exception {
        for(var invalid : new String[] { null, "invalid" }) {
            var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
            command.verifyValidationErrorResult(field, invalid);
            assertEquals(1, command.transfers);
            assertEquals(0, command.locks);
            assertEquals(0, command.writes);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "PasswordHistory", "ExpiredLoginsPermitted", "MinimumLength", "MaximumLength", "RequiredDigitCount",
        "RequiredLetterCount", "RequiredUpperCaseCount", "RequiredLowerCaseCount", "MaximumRepeated", "MinimumCharacterTypes" })
    public void integerSettingsValidateLimitsWithoutTruncation(String field) throws Exception {
        for(var value : new String[] { "0", "2147483647" }) {
            var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
            command.set(field, value);
            command.validateRequest();
            command.executeEdit();
            assertFalse(command.hasExecutionErrors());
            assertEquals(Integer.valueOf(value), PasswordStringPolicyCommandFixture.read(command.savedValue, field));
        }
        for(var value : new String[] { "-1", "2147483648", "invalid" }) {
            var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
            command.verifyValidationErrorResult(field, value);
            assertEquals(0, command.writes);
            assertEquals(0, command.locks);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "MinimumPasswordLifetime", "MaximumPasswordLifetime", "ExpirationWarningTime" })
    public void timeSettingsValidateLongLimitsAndKeepZero(String field) throws Exception {
        for(var value : new String[] { "0", "9223372036854775807" }) {
            var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
            command.set(field, value);
            command.validateRequest();
            command.executeEdit();
            assertFalse(command.hasExecutionErrors());
            assertEquals(Long.valueOf(value), PasswordStringPolicyCommandFixture.read(command.savedValue, field));
        }
        for(var value : new String[] { "-1", "9223372036854775808", "invalid" }) {
            var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
            command.verifyValidationErrorResult(field, value);
            assertEquals(0, command.writes);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "present", "parent", "policy" })
    public void invalidEditRetainsResultWithoutDereferencingMissingPolicy(String failure) throws Exception {
        var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, failure);
        command.verifyValidationErrorResult("MinimumLength", "invalid");
        assertEquals("present".equals(failure) ? 1 : 0, command.transfers);
        assertEquals(0, command.writes);
    }

    @ParameterizedTest
    @ValueSource(strings = { "LOCK", "UPDATE" })
    public void lockFailureReturnsCurrentDataWithoutWriting(String modeName) throws Exception {
        var mode = EditMode.valueOf(modeName);
        var command = new PasswordStringPolicyCommandFixture(mode, null);
        command.lockSucceeds = false;
        command.executeEdit();
        assertError(command, mode == EditMode.LOCK ? ExecutionErrors.EntityLockFailed : ExecutionErrors.EntityLockStale);
        assertEquals(1, command.transfers);
        assertEquals(0, command.unlocks);
        assertEquals(0, command.writes);
    }

    @Test
    public void persistenceFailureStillUnlocksPolicy() throws Exception {
        var command = new PasswordStringPolicyCommandFixture(EditMode.UPDATE, null);
        command.failUpdate = true;
        assertThrows(IllegalStateException.class, command::executeEdit);
        assertEquals(1, command.unlocks);
    }
}
