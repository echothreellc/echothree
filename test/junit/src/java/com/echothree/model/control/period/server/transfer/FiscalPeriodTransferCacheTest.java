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

package com.echothree.model.control.period.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.period.common.transfer.*;
import com.echothree.model.control.period.server.PeriodTestEntities;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.period.server.entity.*;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import javax.enterprise.util.TypeLiteral;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import static org.junit.jupiter.api.Assertions.*;

@ResourceLock("CDIProvider")
@ResourceLock("ValueCacheProvider")
public class FiscalPeriodTransferCacheTest {

    private static class TestCdi extends CDI<Object> {
        final Session session;
        final CDIProvider previous;

        TestCdi(Session session) {
            this.session = session;
            previous = configuredProvider;
            setCDIProvider(() -> this);
        }

        void restore() {
            configuredProvider = previous;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Instance<U> select(Class<U> type, Annotation... qualifiers) {
            assertEquals(Session.class, type);
            return (Instance<U>)Proxy.newProxyInstance(Instance.class.getClassLoader(), new Class<?>[] { Instance.class },
                    (proxy, method, args) -> {
                        if(method.getName().equals("get")) {
                            return session;
                        }
                        throw new UnsupportedOperationException(method.getName());
                    });
        }

        @Override public BeanManager getBeanManager() { throw new UnsupportedOperationException(); }
        @Override public Instance<Object> select(Annotation... qualifiers) { throw new UnsupportedOperationException(); }
        @Override public <U> Instance<U> select(TypeLiteral<U> type, Annotation... qualifiers) { throw new UnsupportedOperationException(); }
        @Override public boolean isUnsatisfied() { return false; }
        @Override public boolean isAmbiguous() { return false; }
        @Override public void destroy(Object instance) { throw new UnsupportedOperationException(); }
        @Override public Object get() { throw new UnsupportedOperationException(); }
        @Override public Iterator<Object> iterator() { return java.util.Collections.singleton((Object)session).iterator(); }
    }

    private static class TestCache extends FiscalPeriodTransferCache {
        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        TestCache(PeriodControl control, WorkflowEntityStatusTransfer fiscalPeriodStatus) {
            periodControl = control;
            entityInstanceControl = new EntityInstanceControl() {
                @Override
                public EntityInstance getEntityInstanceByBasePK(BasePK pk) {
                    return null;
                }
            };
            workflowControl = new WorkflowControl() {
                @Override
                public WorkflowEntityStatusTransfer getWorkflowEntityStatusTransferByEntityInstanceUsingNames(
                        UserVisit visit, String name, EntityInstance instance) {
                    assertEquals("FISCAL_PERIOD_STATUS", name);
                    return fiscalPeriodStatus;
                }
            };
            // EntityInstance setup is unchanged and independently tested by the base cache.
            setIncludeEntityInstance(false);
        }

        @Override
        protected String formatTypicalDateTime(UserVisit visit, Long time) {
            return time.toString();
        }

        @Override
        protected Language getLanguage(UserVisit visit) {
            return null;
        }
    }

    private Session session() throws ReflectiveOperationException {
        var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
        var field = holder.getDeclaredField("instance");
        field.setAccessible(true);
        var original = field.get(null);
        try {
            field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
            return new Session();
        } finally {
            field.set(null, original);
        }
    }

    @Test
    public void cachedPeriodsAndParentsAreFiscalSubclassesIncludingNullableTypes() throws ReflectiveOperationException {
        var kind = PeriodTestEntities.kind(1L, "FISCAL");
        var parent = PeriodTestEntities.period(2L, kind, "PARENT", null, null);
        var type = PeriodTestEntities.type(3L, kind, "YEAR");
        var period = PeriodTestEntities.period(4L, kind, "PERIOD", type, parent);
        var fiscalPeriodStatus = new WorkflowEntityStatusTransfer(null, null, null, 0L, "0", null, null, null, null);
        var cache = new TestCache[1];
        var control = new PeriodControl() {
            @Override
            public PeriodKindTransfer getPeriodKindTransfer(UserVisit visit, PeriodKind value) {
                return new PeriodKindTransfer(value.getLastDetail().getPeriodKindName(), false, 0, "Fiscal");
            }

            @Override
            public PeriodTypeTransfer getPeriodTypeTransfer(UserVisit visit, PeriodType value) {
                return new PeriodTypeTransfer(getPeriodKindTransfer(visit, kind), value.getLastDetail().getPeriodTypeName(), false, 0, "Year");
            }

            @Override
            public String getBestPeriodDescription(Period value, Language language) {
                return value.getLastDetail().getPeriodName();
            }

            @Override
            public PeriodTransfer getPeriodTransfer(UserVisit visit, Period value) {
                return cache[0].getFiscalPeriodTransfer(visit, value);
            }
        };
        var cdi = new TestCdi(session());
        try {
            cache[0] = new TestCache(control, fiscalPeriodStatus);
            var transfer = assertInstanceOf(FiscalPeriodTransfer.class, cache[0].getFiscalPeriodTransfer(null, period));
            var parentTransfer = assertInstanceOf(FiscalPeriodTransfer.class, transfer.getParentPeriod());
            assertEquals("PERIOD", transfer.getPeriodName());
            assertEquals("PARENT", parentTransfer.getPeriodName());
            assertNull(parentTransfer.getPeriodType());
            assertSame(fiscalPeriodStatus, transfer.getFiscalPeriodStatus());
            assertSame(fiscalPeriodStatus, parentTransfer.getFiscalPeriodStatus());
            assertEquals(100L, transfer.getUnformattedStartTime());
            assertEquals("200", transfer.getEndTime());
            assertSame(transfer, cache[0].getFiscalPeriodTransfer(null, period));
        } finally {
            cdi.restore();
        }
    }

}
