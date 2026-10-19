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

package com.echothree.model.control.period.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.period.common.spec.PeriodSpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.period.common.pk.PeriodKindDetailPK;
import com.echothree.model.data.period.common.pk.PeriodKindPK;
import com.echothree.model.data.period.common.pk.PeriodTypePK;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodKindDetail;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.value.PeriodKindDetailValue;
import com.echothree.model.data.period.server.value.PeriodKindValue;
import com.echothree.model.data.period.server.value.PeriodTypeValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PeriodTypeLogicTest {

    private static final String KIND_NAME = "KIND";
    private static final String TYPE_NAME = "TYPE";

    private static PeriodKind kind() {
        var pk = new PeriodKindPK(1L);
        var detailPK = new PeriodKindDetailPK(2L);
        var detail = new PeriodKindDetail(new PeriodKindDetailValue(detailPK, pk, KIND_NAME, null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
        return new PeriodKind(new PeriodKindValue(pk, detailPK, detailPK), EntityPermission.READ_ONLY) {
            @Override
            public PeriodKindDetail getLastDetail() {
                return detail;
            }
        };
    }

    private static class MemoryControl extends PeriodControl {
        final PeriodKind kind = kind();
        final PeriodType type = new PeriodType(new PeriodTypeValue(new PeriodTypePK(3L), null, null), EntityPermission.READ_ONLY);
        EntityPermission permission;
        int entityLookups;
        int nameLookups;

        @Override
        public PeriodType getPeriodTypeByEntityInstance(EntityInstance instance, EntityPermission permission) {
            entityLookups++;
            this.permission = permission;
            return type;
        }

        @Override
        public PeriodType getPeriodTypeByName(PeriodKind kind, String name, EntityPermission permission) {
            nameLookups++;
            assertSame(this.kind, kind);
            this.permission = permission;
            return TYPE_NAME.equals(name) ? type : null;
        }
    }

    private static class InstanceLogic extends EntityInstanceLogic {
        boolean reject;
        int resolutions;

        @Override
        public EntityInstance getEntityInstance(ExecutionErrorAccumulator errors, UniversalEntitySpec spec,
                String vendorName, String... typeNames) {
            resolutions++;
            assertEquals(ComponentVendors.ECHO_THREE.name(), vendorName);
            assertArrayEquals(new String[] { EntityTypes.PeriodType.name() }, typeNames);
            if(reject) {
                errors.addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
            return null;
        }
    }

    private static class TestLogic extends PeriodTypeLogic {
        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        TestLogic(MemoryControl control, InstanceLogic instances) {
            periodControl = control;
            entityInstanceLogic = instances;
            periodKindLogic = new PeriodKindLogic() {
                @Override
                public PeriodKind getPeriodKindByName(ExecutionErrorAccumulator errors, String name) {
                    assertEquals(KIND_NAME, name);
                    return control.kind;
                }
            };
        }
    }

    @Test
    public void uuidAndEntityRefResolveWithEntityTypeAndRequestedPermission() {
        for(var permission : EntityPermission.values()) {
            for(var useUuid : new boolean[] { true, false }) {
                var control = new MemoryControl();
                var instances = new InstanceLogic();
                var logic = new TestLogic(control, instances);
                var spec = PeriodSpecFactory.getPeriodTypeUniversalSpec();
                if(useUuid) {
                    spec.setUuid("11111111-1111-1111-1111-111111111111");
                } else {
                    spec.setEntityRef(control.type.getPrimaryKey().getEntityRef());
                }
                var errors = new DummyExecutionErrorAccumulator();

                assertSame(control.type, logic.getPeriodTypeByUniversalSpec(errors, spec, false, permission));
                assertEquals(permission, control.permission);
                assertEquals(1, control.entityLookups);
                assertEquals(1, instances.resolutions);
                assertEquals(0, control.nameLookups);
                assertFalse(errors.hasExecutionErrors());
            }
        }
    }

    @Test
    public void mixedNamesAndIdsOrMultipleIdsAreRejectedBeforeLookup() {
        for(var withNames : new boolean[] { true, false }) {
            var control = new MemoryControl();
            var instances = new InstanceLogic();
            var logic = new TestLogic(control, instances);
            var spec = PeriodSpecFactory.getPeriodTypeUniversalSpec();
            spec.setUuid("11111111-1111-1111-1111-111111111111");
            if(withNames) {
                spec.setPeriodKindName(KIND_NAME);
                spec.setPeriodTypeName(TYPE_NAME);
            } else {
                spec.setEntityRef(control.type.getPrimaryKey().getEntityRef());
            }
            var errors = new DummyExecutionErrorAccumulator();

            assertNull(logic.getPeriodTypeByUniversalSpec(errors, spec, false));
            assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
            assertEquals(0, instances.resolutions);
            assertEquals(0, control.entityLookups);
            assertEquals(0, control.nameLookups);
        }
    }

    @Test
    public void namesStillResolveAndUnknownTypeIncludesCompleteKey() {
        var control = new MemoryControl();
        var logic = new TestLogic(control, new InstanceLogic());
        var spec = PeriodSpecFactory.getPeriodTypeUniversalSpec();
        spec.setPeriodKindName(KIND_NAME);
        spec.setPeriodTypeName(TYPE_NAME);
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(control.type, logic.getPeriodTypeByUniversalSpec(errors, spec, false));
        assertFalse(errors.hasExecutionErrors());

        spec.setPeriodTypeName("MISSING");
        assertNull(logic.getPeriodTypeByUniversalSpec(errors, spec, false));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownPeriodTypeName.name(), error.getKey());
        assertArrayEquals(new Object[] { KIND_NAME, "MISSING" }, error.getValues());
    }

    @Test
    public void failedEntityResolutionStopsTheEntityLookup() {
        var control = new MemoryControl();
        var instances = new InstanceLogic();
        instances.reject = true;
        var logic = new TestLogic(control, instances);
        var spec = PeriodSpecFactory.getPeriodTypeUniversalSpec();
        spec.setUuid("11111111-1111-1111-1111-111111111111");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getPeriodTypeByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, control.entityLookups);
    }

}
