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

package com.echothree.model.control.core.server.control;

import com.echothree.model.data.core.common.pk.*;
import com.echothree.model.data.core.server.entity.*;
import com.echothree.model.data.core.server.factory.LinkFactory;
import com.echothree.model.data.core.server.value.*;
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
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import static org.junit.jupiter.api.Assertions.*;

@ResourceLock("ValueCacheProvider")
public class LinkControlTouchQueryTest {

    private static class CaptureFactory extends LinkFactory {
        ResultQuery<?> query;

        @Override
        public List<Link> getEntitiesFromQuery(EntityPermission entityPermission, ResultQuery<?> query) {
            assertEquals(EntityPermission.READ_ONLY, entityPermission);
            this.query = query;
            return List.of();
        }
    }

    private static class QuerySession extends Session {
        @Override
        public DSLContext getDslContext() {
            return DSL.using(SQLDialect.MYSQL);
        }

        @Override
        public Select<?> applyLimit(Select<?> query, Class<? extends BaseFactory<? extends BasePK, ? extends BaseEntity>> factory) {
            fail("Dependency touches must not inherit the originating command's pagination");
            return query;
        }
    }

    private static class QueryControl extends LinkControl {
        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        QueryControl(Session querySession, LinkFactory factory) {
            session = querySession;
            linkFactory = factory;
        }
    }

    private QuerySession session() throws ReflectiveOperationException {
        var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
        var field = holder.getDeclaredField("instance");
        field.setAccessible(true);
        var original = field.get(null);
        try {
            field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
            return new QuerySession();
        } finally {
            field.set(null, original);
        }
    }

    private String verifyCurrentUnpaginatedQuery(CaptureFactory factory) {
        var sql = factory.query.getSQL(ParamType.INLINED).replace("`", "").toLowerCase(Locale.ROOT);
        assertTrue(sql.contains("lnk_thrutime = " + Session.MAX_TIME), sql);
        assertFalse(sql.contains("order by"), sql);
        assertFalse(sql.contains("limit"), sql);
        assertFalse(sql.contains("offset"), sql);
        assertFalse(sql.contains("for update"), sql);
        assertFalse(sql.contains("join"), sql);
        return sql.substring(sql.indexOf("where"));
    }

    @Test
    public void endpointLookupMatchesEitherEndWithoutFilteringByLinkType() throws ReflectiveOperationException {
        var factory = new CaptureFactory();
        var control = new QueryControl(session(), factory);
        var entityInstance = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(44L), new EntityTypePK(1L), 11L, null), EntityPermission.READ_ONLY);

        control.getLinksByEntityInstanceForTouch(entityInstance);
        var condition = verifyCurrentUnpaginatedQuery(factory);

        assertTrue(condition.contains("lnk_sourceentityinstanceid = 44"), condition);
        assertTrue(condition.contains("lnk_targetentityinstanceid = 44"), condition);
        assertTrue(condition.contains(" or "), condition);
        assertFalse(condition.contains("lnk_lnktyp_linktypeid"), condition);
    }

    @Test
    public void linkTypeLookupMatchesOnlyTheSpecifiedType() throws ReflectiveOperationException {
        var factory = new CaptureFactory();
        var control = new QueryControl(session(), factory);
        var linkType = new LinkType(new LinkTypeValue(new LinkTypePK(55L), null, null), EntityPermission.READ_ONLY);

        control.getLinksByLinkTypeForTouch(linkType);
        var condition = verifyCurrentUnpaginatedQuery(factory);

        assertTrue(condition.contains("lnk_lnktyp_linktypeid = 55"), condition);
        assertFalse(condition.contains("lnk_sourceentityinstanceid"), condition);
        assertFalse(condition.contains("lnk_targetentityinstanceid"), condition);
        assertFalse(condition.contains(" or "), condition);
        assertFalse(condition.contains(" union "), condition);
    }

}
