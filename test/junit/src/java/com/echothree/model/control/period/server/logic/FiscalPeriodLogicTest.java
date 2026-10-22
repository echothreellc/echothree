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
import com.echothree.model.control.period.server.PeriodTestEntities;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.graphql.FiscalPeriodObject;
import com.echothree.model.control.period.server.graphql.PeriodObject;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.period.server.entity.*;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FiscalPeriodLogicTest {

    private static class Fixture extends FiscalPeriodLogic {
        final PeriodKind kind = PeriodTestEntities.kind(1L, "FISCAL");
        final PeriodKind otherKind = PeriodTestEntities.kind(2L, "OTHER");
        final PeriodType type = PeriodTestEntities.type(3L, kind, "YEAR");
        final Period parent = PeriodTestEntities.period(4L, kind, "PARENT", null, null);
        final Period period = PeriodTestEntities.period(5L, kind, "PERIOD", type, parent);
        Period resolvedPeriod = period;

        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        Fixture() {
            periodKindLogic = new PeriodKindLogic() {
                @Override
                public PeriodKind getPeriodKindByName(ExecutionErrorAccumulator errors, String name) {
                    assertEquals("FISCAL", name);
                    return kind;
                }
            };
            periodLogic = new PeriodLogic() {
                @Override
                public Period getPeriodByName(ExecutionErrorAccumulator errors, PeriodKind periodKind, String name, EntityPermission permission) {
                    assertSame(kind, periodKind);
                    if("PARENT".equals(name)) {
                        return parent;
                    } else if("PERIOD".equals(name)) {
                        return period;
                    }
                    errors.addExecutionError(ExecutionErrors.UnknownPeriodName.name(), "FISCAL", name);
                    return null;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator errors, UniversalEntitySpec spec, String vendor, String... types) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), vendor);
                    assertArrayEquals(new String[] { EntityTypes.Period.name() }, types);
                    return null;
                }
            };
            periodControl = new PeriodControl() {
                @Override
                public Period getPeriodByEntityInstance(EntityInstance instance, EntityPermission permission) {
                    return resolvedPeriod;
                }
            };
        }
    }

    @Test
    public void singlePeriodSupportsNamesAndIdButRejectsMixedIdentifiersAndWrongKind() {
        var fixture = new Fixture();
        var spec = PeriodSpecFactory.getFiscalPeriodUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        spec.setPeriodName("PERIOD");
        assertSame(fixture.period, fixture.getFiscalPeriodByUniversalSpec(errors, spec, EntityPermission.READ_ONLY));
        spec.setPeriodName(null);
        spec.setUuid("11111111-1111-1111-1111-111111111111");
        assertSame(fixture.period, fixture.getFiscalPeriodByUniversalSpec(errors, spec, EntityPermission.READ_ONLY));
        spec.setPeriodName("PERIOD");
        assertNull(fixture.getFiscalPeriodByUniversalSpec(errors, spec, EntityPermission.READ_ONLY));
        assertTrue(errors.hasExecutionErrors());

        spec.setPeriodName(null);
        errors = new DummyExecutionErrorAccumulator();
        fixture.resolvedPeriod = PeriodTestEntities.period(7L, fixture.otherKind, "OTHER_PERIOD", null, null);
        assertNull(fixture.getFiscalPeriodByUniversalSpec(errors, spec, EntityPermission.READ_ONLY));
        assertEquals(ExecutionErrors.InvalidPeriodKindName.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
    }

    @Test
    public void polymorphicObjectFactoryUsesTheKindEvenWhenTheTypeIsNull() {
        var fixture = new Fixture();
        var object = assertInstanceOf(FiscalPeriodObject.class, PeriodObject.toPeriodInterface(fixture.parent));
        assertEquals("PARENT", object.getPeriodName());
        assertEquals(100L, object.getStartTime().getUnformattedTime());
        assertEquals(200L, object.getEndTime().getUnformattedTime());
    }

}
