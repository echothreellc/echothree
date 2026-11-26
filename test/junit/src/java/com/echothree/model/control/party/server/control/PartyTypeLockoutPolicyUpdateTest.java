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
import com.echothree.model.data.party.common.pk.PartyTypeLockoutPolicyPK;
import com.echothree.model.data.party.common.pk.PartyTypeLockoutPolicyDetailPK;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicyDetail;
import com.echothree.model.data.party.server.factory.PartyTypeLockoutPolicyFactory;
import com.echothree.model.data.party.server.factory.PartyTypeLockoutPolicyDetailFactory;
import com.echothree.model.data.party.server.value.PartyTypeLockoutPolicyValue;
import com.echothree.model.data.party.server.value.PartyTypeLockoutPolicyDetailValue;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/** Exercises the actual Control method, stubbing only factories, clock, and event delivery. */
@ResourceLock("ValueCacheProvider")
public class PartyTypeLockoutPolicyUpdateTest {
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
        final PartyTypeLockoutPolicyPK policyPK = new PartyTypeLockoutPolicyPK(2L);
        final PartyTypePK parentPK = new PartyTypePK(1L);
        final List<String> operations = new ArrayList<>();
        final PartyTypeLockoutPolicyDetail oldDetail;
        final PartyTypeLockoutPolicy policy;
        PartyTypeLockoutPolicyDetail newDetail;
        PartyTypeLockoutPolicyDetailValue createdValue;

        UpdateControl() throws ReflectiveOperationException {
            session = PartyTypeLockoutPolicyUpdateTest.session();
            oldDetail = new PartyTypeLockoutPolicyDetail(new PartyTypeLockoutPolicyDetailValue(new PartyTypeLockoutPolicyDetailPK(3L), policyPK,
                    parentPK, 3, 1000L, true, 2000L, 0L, Session.MAX_TIME), EntityPermission.READ_WRITE) {
                @Override public void store() {
                    assertEquals(1000L, getThruTime());
                    operations.add("expire");
                }
            };
            policy = new PartyTypeLockoutPolicy(new PartyTypeLockoutPolicyValue(policyPK, oldDetail.getPrimaryKey(), oldDetail.getPrimaryKey()),
                    EntityPermission.READ_WRITE) {
                @Override public PartyTypeLockoutPolicyDetail getActiveDetailForUpdate() { return oldDetail; }
            };
            partyTypeLockoutPolicyFactory = new PartyTypeLockoutPolicyFactory() {
                @Override public PartyTypeLockoutPolicy getEntityFromPK(EntityPermission permission, PartyTypeLockoutPolicyPK selectedPK) {
                    assertEquals(EntityPermission.READ_WRITE, permission);
                    assertEquals(policyPK, selectedPK);
                    operations.add("load");
                    return policy;
                }
            };
            partyTypeLockoutPolicyDetailFactory = new PartyTypeLockoutPolicyDetailFactory() {
                @Override public PartyTypeLockoutPolicyDetail create(PartyTypeLockoutPolicyPK selectedPK, PartyTypePK selectedParentPK,
                        Integer lockoutFailureCount, Long resetFailureCountTime, Boolean manualLockoutReset, Long lockoutInactiveTime,
                        Long fromTime, Long thruTime) {
                    assertEquals(policyPK, selectedPK);
                    assertEquals(parentPK, selectedParentPK);
                    assertEquals(1000L, fromTime);
                    assertEquals(Session.MAX_TIME, thruTime);
                    operations.add("create");
                    createdValue = new PartyTypeLockoutPolicyDetailValue(new PartyTypeLockoutPolicyDetailPK(4L), selectedPK, selectedParentPK,
                            lockoutFailureCount, resetFailureCountTime, manualLockoutReset, lockoutInactiveTime, fromTime, thruTime);
                    newDetail = new PartyTypeLockoutPolicyDetail(createdValue, EntityPermission.READ_WRITE);
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

        PartyTypeLockoutPolicyDetailValue originalValue() { return oldDetail.getPartyTypeLockoutPolicyDetailValue().clone(); }
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = { 0L, 2000L, Long.MAX_VALUE })
    public void newHistoryVersionUsesSubmittedValuesAndKeepsParentAndEventOwner(Long configuredTime) throws Exception {
        for(var manualLockoutReset : new Boolean[] { false, true }) {
            var control = new UpdateControl();
            var value = control.originalValue();
            value.setManualLockoutReset(manualLockoutReset);
            value.setResetFailureCountTime(configuredTime);
            value.setLockoutInactiveTime(configuredTime);
            value.setLockoutFailureCount(configuredTime == null ? null : 0);
            value.setPartyTypePK(new PartyTypePK(500L)); // The owning PartyType cannot be reassigned through this update.
            control.updatePartyTypeLockoutPolicyFromValue(value, new PartyPK(99L));
            assertEquals(manualLockoutReset, control.createdValue.getManualLockoutReset());
            assertEquals(configuredTime, control.createdValue.getResetFailureCountTime());
            assertEquals(configuredTime, control.createdValue.getLockoutInactiveTime());
            assertEquals(configuredTime == null ? null : 0, control.createdValue.getLockoutFailureCount());
            assertEquals(control.parentPK, control.createdValue.getPartyTypePK());
            assertTrue(control.oldDetail.getManualLockoutReset());
            assertEquals(1000L, control.oldDetail.getResetFailureCountTime());
            assertEquals(2000L, control.oldDetail.getLockoutInactiveTime());
            assertEquals(3, control.oldDetail.getLockoutFailureCount());
            assertEquals(0L, control.oldDetail.getFromTime());
            assertEquals(List.of("load", "expire", "create", "event"), control.operations);
        }
    }

    @Test
    public void changingOnlyManualLockoutResetPersistsNewBoolean() throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        value.setManualLockoutReset(false);
        control.updatePartyTypeLockoutPolicyFromValue(value, new PartyPK(99L));
        assertFalse(control.createdValue.getManualLockoutReset());
        assertEquals(1000L, control.createdValue.getResetFailureCountTime());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = { 0L, 2000L, Long.MAX_VALUE })
    public void changingOnlyResetFailureCountTimePersistsNullAndZero(Long configuredTime) throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        value.setResetFailureCountTime(configuredTime);
        control.updatePartyTypeLockoutPolicyFromValue(value, new PartyPK(99L));
        assertTrue(control.createdValue.getManualLockoutReset());
        assertEquals(configuredTime, control.createdValue.getResetFailureCountTime());
    }

    @ParameterizedTest
    @ValueSource(strings = { "LockoutFailureCount", "ResetFailureCountTime", "ManualLockoutReset", "LockoutInactiveTime" })
    public void independentlyChangedSettingPersistsWithoutChangingOtherSettings(String field) throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        Object updated = switch(field) {
            case "LockoutFailureCount" -> 5;
            case "ManualLockoutReset" -> false;
            case "ResetFailureCountTime", "LockoutInactiveTime" -> 3000L;
            default -> throw new AssertionError(field);
        };
        var parameterType = updated.getClass();
        value.getClass().getMethod("set" + field, parameterType).invoke(value, updated);
        control.updatePartyTypeLockoutPolicyFromValue(value, new PartyPK(99L));
        var expected = new java.util.LinkedHashMap<String, Object>();
        expected.put("LockoutFailureCount", 3);
        expected.put("ResetFailureCountTime", 1000L);
        expected.put("ManualLockoutReset", true);
        expected.put("LockoutInactiveTime", 2000L);
        expected.put(field, updated);
        for(var entry : expected.entrySet()) {
            assertEquals(entry.getValue(), control.createdValue.getClass().getMethod("get" + entry.getKey()).invoke(control.createdValue), entry.getKey());
        }
    }

    @Test
    public void unchangedValueDoesNotExpireCreateOrSendEvent() throws Exception {
        var control = new UpdateControl();
        control.updatePartyTypeLockoutPolicyFromValue(control.originalValue(), new PartyPK(99L));
        assertTrue(control.operations.isEmpty());
        assertNull(control.createdValue);
        assertEquals(Session.MAX_TIME, control.oldDetail.getThruTime());
        assertEquals(control.oldDetail.getPrimaryKey(), control.policy.getActiveDetailPK());
        assertEquals(control.oldDetail.getPrimaryKey(), control.policy.getLastDetailPK());
    }
}
