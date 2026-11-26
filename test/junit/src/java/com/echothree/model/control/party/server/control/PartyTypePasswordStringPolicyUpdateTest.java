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

package com.echothree.model.control.party.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.common.pk.PartyTypePasswordStringPolicyPK;
import com.echothree.model.data.party.common.pk.PartyTypePasswordStringPolicyDetailPK;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicyDetail;
import com.echothree.model.data.party.server.factory.PartyTypePasswordStringPolicyFactory;
import com.echothree.model.data.party.server.factory.PartyTypePasswordStringPolicyDetailFactory;
import com.echothree.model.data.party.server.value.PartyTypePasswordStringPolicyValue;
import com.echothree.model.data.party.server.value.PartyTypePasswordStringPolicyDetailValue;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/** Exercises the actual Control method, stubbing only factories, clock, and event delivery. */
@ResourceLock("ValueCacheProvider")
public class PartyTypePasswordStringPolicyUpdateTest {
    private static final Map<String, Object> INITIAL = initialValues();
    private static final List<String> REQUIRED_BOOLEANS = List.of("ForceChangeAfterCreate", "ForceChangeAfterReset", "AllowChange");

    private static Map<String, Object> initialValues() {
        var values = new LinkedHashMap<String, Object>();
        values.put("ForceChangeAfterCreate", true);
        values.put("ForceChangeAfterReset", false);
        values.put("AllowChange", true);
        values.put("PasswordHistory", 5);
        values.put("MinimumPasswordLifetime", 1000L);
        values.put("MaximumPasswordLifetime", 2000000L);
        values.put("ExpirationWarningTime", 1000000L);
        values.put("ExpiredLoginsPermitted", 2);
        values.put("MinimumLength", 8);
        values.put("MaximumLength", 64);
        values.put("RequiredDigitCount", 1);
        values.put("RequiredLetterCount", 2);
        values.put("RequiredUpperCaseCount", 1);
        values.put("RequiredLowerCaseCount", 1);
        values.put("MaximumRepeated", 3);
        values.put("MinimumCharacterTypes", 2);
        return values;
    }

    static Stream<String> settingNames() { return INITIAL.keySet().stream(); }
    static Stream<String> optionalSettingNames() {
        return INITIAL.keySet().stream().filter(name -> !REQUIRED_BOOLEANS.contains(name));
    }

    private static void setValue(PartyTypePasswordStringPolicyDetailValue value, String settingName, Object settingValue)
            throws ReflectiveOperationException {
        var parameterType = value.getClass().getMethod("get" + settingName).getReturnType();
        value.getClass().getMethod("set" + settingName, parameterType).invoke(value, settingValue);
    }

    private static void assertSettings(Object detail, Map<String, Object> expected) throws ReflectiveOperationException {
        for(var entry : expected.entrySet()) {
            assertEquals(entry.getValue(), detail.getClass().getMethod("get" + entry.getKey()).invoke(detail), entry.getKey());
        }
    }

    private static Object changedValue(String settingName) {
        return switch(INITIAL.get(settingName)) {
            case Boolean booleanValue -> !booleanValue;
            case Integer integerValue -> integerValue + 100;
            case Long longValue -> longValue + 9000000000L;
            default -> throw new AssertionError(settingName);
        };
    }

    private static class UpdateSession extends Session {
        @Override public long getStartTime() { return 1000L; }
    }

    private static UpdateSession session() throws ReflectiveOperationException {
        var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
        var field = holder.getDeclaredField("instance");
        field.setAccessible(true);
        var original = field.get(null);
        try {
            field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
            return new UpdateSession();
        } finally {
            field.set(null, original);
        }
    }

    @SuppressWarnings("UnnecessaryAssignment")
    private static class UpdateControl extends PartyControl {
        final PartyTypePasswordStringPolicyPK policyPK = new PartyTypePasswordStringPolicyPK(2L);
        final PartyTypePK parentPK = new PartyTypePK(1L);
        final List<String> operations = new ArrayList<>();
        final PartyTypePasswordStringPolicyDetail oldDetail;
        final PartyTypePasswordStringPolicy policy;
        PartyTypePasswordStringPolicyDetail newDetail;
        PartyTypePasswordStringPolicyDetailValue createdValue;

        UpdateControl() throws ReflectiveOperationException {
            session = PartyTypePasswordStringPolicyUpdateTest.session();
            oldDetail = new PartyTypePasswordStringPolicyDetail(new PartyTypePasswordStringPolicyDetailValue(new PartyTypePasswordStringPolicyDetailPK(3L), policyPK,
                    parentPK, true, false, true, 5, 1000L, 2000000L, 1000000L, 2, 8, 64,
                    1, 2, 1, 1, 3, 2, 0L, Session.MAX_TIME), EntityPermission.READ_WRITE) {
                @Override public void store() {
                    assertEquals(1000L, getThruTime());
                    operations.add("expire");
                }
            };
            policy = new PartyTypePasswordStringPolicy(new PartyTypePasswordStringPolicyValue(policyPK, oldDetail.getPrimaryKey(), oldDetail.getPrimaryKey()),
                    EntityPermission.READ_WRITE) {
                @Override public PartyTypePasswordStringPolicyDetail getActiveDetailForUpdate() { return oldDetail; }
            };
            partyTypePasswordStringPolicyFactory = new PartyTypePasswordStringPolicyFactory() {
                @Override public PartyTypePasswordStringPolicy getEntityFromPK(EntityPermission permission, PartyTypePasswordStringPolicyPK selectedPK) {
                    assertEquals(EntityPermission.READ_WRITE, permission);
                    assertEquals(policyPK, selectedPK);
                    operations.add("load");
                    return policy;
                }
            };
            partyTypePasswordStringPolicyDetailFactory = new PartyTypePasswordStringPolicyDetailFactory() {
                @Override public PartyTypePasswordStringPolicyDetail create(PartyTypePasswordStringPolicyPK selectedPK, PartyTypePK selectedParentPK,
                        Boolean forceChangeAfterCreate, Boolean forceChangeAfterReset, Boolean allowChange,
                        Integer passwordHistory, Long minimumPasswordLifetime, Long maximumPasswordLifetime,
                        Long expirationWarningTime, Integer expiredLoginsPermitted, Integer minimumLength,
                        Integer maximumLength, Integer requiredDigitCount, Integer requiredLetterCount,
                        Integer requiredUpperCaseCount, Integer requiredLowerCaseCount, Integer maximumRepeated,
                        Integer minimumCharacterTypes,
                        Long fromTime, Long thruTime) {
                    assertEquals(policyPK, selectedPK);
                    assertEquals(parentPK, selectedParentPK);
                    assertEquals(1000L, fromTime);
                    assertEquals(Session.MAX_TIME, thruTime);
                    operations.add("create");
                    createdValue = new PartyTypePasswordStringPolicyDetailValue(new PartyTypePasswordStringPolicyDetailPK(4L), selectedPK, selectedParentPK,
                            forceChangeAfterCreate, forceChangeAfterReset, allowChange, passwordHistory,
                            minimumPasswordLifetime, maximumPasswordLifetime, expirationWarningTime, expiredLoginsPermitted,
                            minimumLength, maximumLength, requiredDigitCount, requiredLetterCount, requiredUpperCaseCount,
                            requiredLowerCaseCount, maximumRepeated, minimumCharacterTypes, fromTime, thruTime);
                    newDetail = new PartyTypePasswordStringPolicyDetail(createdValue, EntityPermission.READ_WRITE);
                    return newDetail;
                }
            };
        }

        @Override protected Event sendEvent(BasePK basePK, EventTypes eventType, BasePK relatedBasePK, EventTypes relatedEventType, BasePK createdByBasePK) {
            assertEquals(policyPK, basePK);
            assertEquals(EventTypes.MODIFY, eventType);
            assertNull(relatedBasePK);
            assertNull(relatedEventType);
            assertEquals(new PartyPK(99L), createdByBasePK);
            assertEquals(newDetail.getPrimaryKey(), policy.getActiveDetailPK());
            assertEquals(newDetail.getPrimaryKey(), policy.getLastDetailPK());
            operations.add("event");
            return null;
        }

        PartyTypePasswordStringPolicyDetailValue originalValue() { return oldDetail.getPartyTypePasswordStringPolicyDetailValue().clone(); }
    }

    @ParameterizedTest
    @MethodSource("settingNames")
    public void eachSettingPersistsWithoutChangingOtherSettings(String settingName) throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        var updated = changedValue(settingName);
        setValue(value, settingName, updated);
        control.updatePartyTypePasswordStringPolicyFromValue(value, new PartyPK(99L));
        var expected = new LinkedHashMap<>(INITIAL);
        expected.put(settingName, updated);
        assertSettings(control.createdValue, expected);
        assertSettings(control.oldDetail, INITIAL);
        assertEquals(List.of("load", "expire", "create", "event"), control.operations);
    }

    @ParameterizedTest
    @MethodSource("optionalSettingNames")
    public void eachOptionalSettingCanBeClearedIndependently(String settingName) throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        setValue(value, settingName, null);
        control.updatePartyTypePasswordStringPolicyFromValue(value, new PartyPK(99L));
        var expected = new LinkedHashMap<>(INITIAL);
        expected.put(settingName, null);
        assertSettings(control.createdValue, expected);
        assertSettings(control.oldDetail, INITIAL);
    }

    @ParameterizedTest
    @MethodSource("optionalSettingNames")
    public void numericSettingsPreserveZeroAndStorageMaximum(String settingName) throws Exception {
        for(var maximum : new boolean[] { false, true }) {
            Object configuredValue = switch(INITIAL.get(settingName)) {
                case Integer integerValue -> maximum ? Integer.MAX_VALUE : 0;
                case Long longValue -> maximum ? Long.MAX_VALUE : 0L;
                default -> throw new AssertionError(settingName);
            };
            var control = new UpdateControl();
            var value = control.originalValue();
            setValue(value, settingName, configuredValue);
            control.updatePartyTypePasswordStringPolicyFromValue(value, new PartyPK(99L));
            var expected = new LinkedHashMap<>(INITIAL);
            expected.put(settingName, configuredValue);
            assertSettings(control.createdValue, expected);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7 })
    public void allRequiredBooleanCombinationsPersist(int mask) throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        var expected = new LinkedHashMap<>(INITIAL);
        // Ensure a replacement is created even when the Boolean combination is unchanged.
        value.setPasswordHistory(6);
        expected.put("PasswordHistory", 6);
        for(var index = 0; index < REQUIRED_BOOLEANS.size(); index++) {
            var name = REQUIRED_BOOLEANS.get(index);
            var configuredValue = (mask & (1 << index)) != 0;
            setValue(value, name, configuredValue);
            expected.put(name, configuredValue);
        }
        control.updatePartyTypePasswordStringPolicyFromValue(value, new PartyPK(99L));
        assertSettings(control.createdValue, expected);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void combinedChangesKeepOwnerHistoryAndEventTarget(boolean clearOptional) throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        var expected = new LinkedHashMap<String, Object>();
        for(var name : INITIAL.keySet()) {
            var updated = clearOptional && !REQUIRED_BOOLEANS.contains(name) ? null : changedValue(name);
            setValue(value, name, updated);
            expected.put(name, updated);
        }
        value.setPartyTypePK(new PartyTypePK(500L));
        control.updatePartyTypePasswordStringPolicyFromValue(value, new PartyPK(99L));
        assertSettings(control.createdValue, expected);
        assertSettings(control.oldDetail, INITIAL);
        assertEquals(control.parentPK, control.createdValue.getPartyTypePK());
        assertEquals(control.policyPK, control.createdValue.getPartyTypePasswordStringPolicyPK());
        assertEquals(0L, control.oldDetail.getFromTime());
        assertEquals(1000L, control.oldDetail.getThruTime());
        assertEquals(1000L, control.createdValue.getFromTime());
        assertEquals(Session.MAX_TIME, control.createdValue.getThruTime());
        assertEquals(control.newDetail.getPrimaryKey(), control.policy.getActiveDetailPK());
        assertEquals(control.newDetail.getPrimaryKey(), control.policy.getLastDetailPK());
        assertEquals(List.of("load", "expire", "create", "event"), control.operations);
    }

    @Test
    public void unchangedValuesDoNotLoadExpireCreateOrSendEvents() throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        for(var entry : INITIAL.entrySet()) {
            setValue(value, entry.getKey(), entry.getValue());
        }
        assertFalse(value.hasBeenModified());
        control.updatePartyTypePasswordStringPolicyFromValue(value, new PartyPK(99L));
        assertTrue(control.operations.isEmpty());
        assertNull(control.createdValue);
        assertSettings(control.oldDetail, INITIAL);
        assertEquals(Session.MAX_TIME, control.oldDetail.getThruTime());
        assertEquals(control.oldDetail.getPrimaryKey(), control.policy.getActiveDetailPK());
        assertEquals(control.oldDetail.getPrimaryKey(), control.policy.getLastDetailPK());
    }
}
