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

package com.echothree.model.control.period.server.control;

import com.echothree.model.control.period.server.PeriodTestEntities;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.factory.PeriodFactory;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.BaseFactory;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import java.util.List;
import java.util.Locale;
import org.jooq.DSLContext;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import static org.junit.jupiter.api.Assertions.*;

@ResourceLock("ValueCacheProvider")
public class PeriodControlQueryTest {

    private static class CaptureFactory extends PeriodFactory {
        ResultQuery<?> query;

        @Override
        public List<Period> getEntitiesFromQuery(EntityPermission permission, ResultQuery<?> query) {
            assertEquals(EntityPermission.READ_ONLY, permission);
            this.query = query;
            return List.of();
        }
    }

    private static class QuerySession extends Session {
        final DSLContext context;

        QuerySession(DSLContext context) {
            this.context = context;
        }

        @Override
        public DSLContext getDslContext() {
            return context;
        }

        @Override
        public Select<?> applyLimit(Select<?> query, Class<? extends BaseFactory<? extends BasePK, ? extends BaseEntity>> factory) {
            assertEquals(PeriodFactory.class, factory);
            return query.$limit(DSL.val(2L)).$offset(DSL.val(1L));
        }
    }

    private static class QueryControl extends PeriodControl {
        @SuppressWarnings("UnnecessaryAssignment") // Supply the CDI dependency without a container.
        QueryControl(Session testSession, CaptureFactory factory) throws ReflectiveOperationException {
            session = testSession;
            var field = PeriodControl.class.getDeclaredField("periodFactory");
            field.setAccessible(true);
            field.set(this, factory);
        }
    }

    private QuerySession session(DSLContext context) throws ReflectiveOperationException {
        var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
        var field = holder.getDeclaredField("instance");
        field.setAccessible(true);
        var original = field.get(null);
        try {
            field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
            return new QuerySession(context);
        } finally {
            field.set(null, original);
        }
    }

    @Test
    public void countAndListUseEverySuppliedFilterAndOnlyListIsPaginated() throws ReflectiveOperationException {
        var kind = PeriodTestEntities.kind(11L, "FISCAL");
        var parent = PeriodTestEntities.period(22L, kind, "PARENT", null, null);
        var type = PeriodTestEntities.type(33L, kind, "YEAR");
        var countSql = new String[1];
        var connection = new MockConnection(context -> {
            countSql[0] = context.sql().replace("\"", "").toLowerCase(Locale.ROOT);
            var result = DSL.using(SQLDialect.POSTGRES).newResult(DSL.field("count", SQLDataType.BIGINT));
            result.add(DSL.using(SQLDialect.POSTGRES).newRecord(DSL.field("count", SQLDataType.BIGINT)).values(5L));
            return new MockResult[] { new MockResult(1, result) };
        });
        var context = DSL.using(connection, SQLDialect.POSTGRES);
        var factory = new CaptureFactory();
        var control = new QueryControl(session(context), factory);

        for(int mask = 0; mask < 4; mask++) {
            var selectedParent = (mask & 1) == 0 ? null : parent;
            var selectedType = (mask & 2) == 0 ? null : type;
            var periodSelection = new PeriodControl.PeriodSelection(kind, selectedParent, selectedType);

            assertEquals(5L, control.countPeriods(periodSelection));
            control.getPeriods(periodSelection);
            var listSql = factory.query.getSQL().replace("\"", "").toLowerCase(Locale.ROOT);
            for(var sql : List.of(countSql[0], listSql)) {
                assertTrue(sql.contains("prd_lastdetailid"), sql);
                assertTrue(sql.contains("prddt_prdk_periodkindid = ?"), sql);
                assertTrue(sql.contains("prddt_thrutime = ?"), sql);
                assertEquals(selectedParent != null, sql.contains("prddt_parentperiodid = ?"), sql);
                assertEquals(selectedType != null, sql.contains("prddt_prdt_periodtypeid = ?"), sql);
            }
            assertTrue(listSql.contains("offset"), listSql);
            assertTrue(listSql.contains("fetch next") || listSql.contains("limit"), listSql);
            assertFalse(countSql[0].contains("offset"));
            assertTrue(listSql.contains("order by"), listSql);
        }
    }

}
