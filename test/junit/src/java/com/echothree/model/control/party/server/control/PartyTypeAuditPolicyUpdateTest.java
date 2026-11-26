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
import com.echothree.model.data.party.common.pk.PartyTypeAuditPolicyPK;
import com.echothree.model.data.party.common.pk.PartyTypeAuditPolicyDetailPK;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicyDetail;
import com.echothree.model.data.party.server.factory.PartyTypeAuditPolicyFactory;
import com.echothree.model.data.party.server.factory.PartyTypeAuditPolicyDetailFactory;
import com.echothree.model.data.party.server.value.PartyTypeAuditPolicyValue;
import com.echothree.model.data.party.server.value.PartyTypeAuditPolicyDetailValue;
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
public class PartyTypeAuditPolicyUpdateTest {
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
        final PartyTypeAuditPolicyPK policyPK = new PartyTypeAuditPolicyPK(2L);
        final PartyTypePK parentPK = new PartyTypePK(1L);
        final List<String> operations = new ArrayList<>();
        final PartyTypeAuditPolicyDetail oldDetail;
        final PartyTypeAuditPolicy policy;
        PartyTypeAuditPolicyDetail newDetail;
        PartyTypeAuditPolicyDetailValue createdValue;

        UpdateControl() throws ReflectiveOperationException {
            session = PartyTypeAuditPolicyUpdateTest.session();
            oldDetail = new PartyTypeAuditPolicyDetail(new PartyTypeAuditPolicyDetailValue(new PartyTypeAuditPolicyDetailPK(3L), policyPK,
                    parentPK, true, 1000L, 0L, Session.MAX_TIME), EntityPermission.READ_WRITE) {
                @Override public void store() {
                    assertEquals(1000L, getThruTime());
                    operations.add("expire");
                }
            };
            policy = new PartyTypeAuditPolicy(new PartyTypeAuditPolicyValue(policyPK, oldDetail.getPrimaryKey(), oldDetail.getPrimaryKey()),
                    EntityPermission.READ_WRITE) {
                @Override public PartyTypeAuditPolicyDetail getActiveDetailForUpdate() { return oldDetail; }
            };
            partyTypeAuditPolicyFactory = new PartyTypeAuditPolicyFactory() {
                @Override public PartyTypeAuditPolicy getEntityFromPK(EntityPermission permission, PartyTypeAuditPolicyPK selectedPK) {
                    assertEquals(EntityPermission.READ_WRITE, permission);
                    assertEquals(policyPK, selectedPK);
                    operations.add("load");
                    return policy;
                }
            };
            partyTypeAuditPolicyDetailFactory = new PartyTypeAuditPolicyDetailFactory() {
                @Override public PartyTypeAuditPolicyDetail create(PartyTypeAuditPolicyPK selectedPK, PartyTypePK selectedParentPK,
                        Boolean auditCommands, Long retainUserVisitsTime, Long fromTime, Long thruTime) {
                    assertEquals(policyPK, selectedPK);
                    assertEquals(parentPK, selectedParentPK);
                    assertEquals(1000L, fromTime);
                    assertEquals(Session.MAX_TIME, thruTime);
                    operations.add("create");
                    createdValue = new PartyTypeAuditPolicyDetailValue(new PartyTypeAuditPolicyDetailPK(4L), selectedPK, selectedParentPK,
                            auditCommands, retainUserVisitsTime, fromTime, thruTime);
                    newDetail = new PartyTypeAuditPolicyDetail(createdValue, EntityPermission.READ_WRITE);
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

        PartyTypeAuditPolicyDetailValue originalValue() { return oldDetail.getPartyTypeAuditPolicyDetailValue().clone(); }
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = { 0L, 2000L, Long.MAX_VALUE })
    public void newHistoryVersionUsesSubmittedValuesAndKeepsParentAndEventOwner(Long retainedTime) throws Exception {
        for(var auditCommands : new Boolean[] { false, true }) {
            var control = new UpdateControl();
            var value = control.originalValue();
            value.setAuditCommands(auditCommands);
            value.setRetainUserVisitsTime(retainedTime);
            value.setPartyTypePK(new PartyTypePK(500L)); // The owning PartyType cannot be reassigned through this update.
            control.updatePartyTypeAuditPolicyFromValue(value, new PartyPK(99L));
            assertEquals(auditCommands, control.createdValue.getAuditCommands());
            assertEquals(retainedTime, control.createdValue.getRetainUserVisitsTime());
            assertEquals(control.parentPK, control.createdValue.getPartyTypePK());
            assertTrue(control.oldDetail.getAuditCommands());
            assertEquals(1000L, control.oldDetail.getRetainUserVisitsTime());
            assertEquals(0L, control.oldDetail.getFromTime());
            assertEquals(List.of("load", "expire", "create", "event"), control.operations);
        }
    }

    @Test
    public void changingOnlyAuditCommandsPersistsNewBoolean() throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        value.setAuditCommands(false);
        control.updatePartyTypeAuditPolicyFromValue(value, new PartyPK(99L));
        assertFalse(control.createdValue.getAuditCommands());
        assertEquals(1000L, control.createdValue.getRetainUserVisitsTime());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = { 0L, 2000L, Long.MAX_VALUE })
    public void changingOnlyRetentionPersistsValueIncludingNullAndZero(Long retainedTime) throws Exception {
        var control = new UpdateControl();
        var value = control.originalValue();
        value.setRetainUserVisitsTime(retainedTime);
        control.updatePartyTypeAuditPolicyFromValue(value, new PartyPK(99L));
        assertTrue(control.createdValue.getAuditCommands());
        assertEquals(retainedTime, control.createdValue.getRetainUserVisitsTime());
    }

    @Test
    public void unchangedValueDoesNotExpireCreateOrSendEvent() throws Exception {
        var control = new UpdateControl();
        control.updatePartyTypeAuditPolicyFromValue(control.originalValue(), new PartyPK(99L));
        assertTrue(control.operations.isEmpty());
        assertNull(control.createdValue);
        assertEquals(Session.MAX_TIME, control.oldDetail.getThruTime());
        assertEquals(control.oldDetail.getPrimaryKey(), control.policy.getActiveDetailPK());
        assertEquals(control.oldDetail.getPrimaryKey(), control.policy.getLastDetailPK());
    }
}
