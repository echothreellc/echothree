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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.*;
import com.echothree.model.control.core.server.control.*;
import com.echothree.model.data.core.common.pk.*;
import com.echothree.model.data.core.server.entity.*;
import com.echothree.model.data.core.server.value.*;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.EntityPermission;
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
public class LinkTypeTransferCacheTest {

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

    private static class MemoryLinkType extends LinkType {
        LinkType parentLinkType;
        LinkType inverseLinkType;

        MemoryLinkType(long identifier) {
            super(new LinkTypeValue(new LinkTypePK(identifier), null, null), EntityPermission.READ_ONLY);
        }

        @Override
        public LinkTypeDetail getLastDetail() {
            var value = new LinkTypeDetailValue(new LinkTypeDetailPK(getPrimaryKey().getEntityId() + 100L), getPrimaryKey(),
                    "TYPE_" + getPrimaryKey().getEntityId(), null, new EntityTypePK(1L), new EntityTypePK(1L),
                    new LinkCardinalityPK(1L), new LinkDirectionalityPK(1L), false, null, 1, 0L, Long.MAX_VALUE);
            return new LinkTypeDetail(value, EntityPermission.READ_ONLY) {
                @Override public LinkType getParentLinkType() { return parentLinkType; }
                @Override public LinkType getInverseLinkType() { return inverseLinkType; }
                @Override public EntityType getSourceEntityType() { return null; }
                @Override public EntityType getTargetEntityType() { return null; }
                @Override public LinkCardinality getLinkCardinality() { return null; }
                @Override public LinkDirectionality getLinkDirectionality() { return null; }
            };
        }
    }

    private static class TestCache extends LinkTypeTransferCache {
        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        TestCache() {
            setIncludeEntityInstance(false);
            entityTypeControl = new EntityTypeControl() {
                @Override public EntityTypeTransfer getEntityTypeTransfer(UserVisit userVisit, EntityType entityType) { return null; }
            };
            linkCardinalityControl = new LinkCardinalityControl() {
                @Override public LinkCardinalityTransfer getLinkCardinalityTransfer(UserVisit userVisit, LinkCardinality linkCardinality) { return null; }
            };
            linkDirectionalityControl = new LinkDirectionalityControl() {
                @Override public LinkDirectionalityTransfer getLinkDirectionalityTransfer(UserVisit userVisit, LinkDirectionality linkDirectionality) { return null; }
            };
            linkTypeControl = new LinkTypeControl() {
                @Override public LinkTypeTranslation getBestLinkTypeTranslation(LinkType linkType, Language language) { return null; }
                @Override public LinkTypeTransfer getLinkTypeTransfer(UserVisit userVisit, LinkType linkType) {
                    return getTransfer(userVisit, linkType);
                }
            };
        }

        @Override protected Language getLanguage(UserVisit userVisit) { return null; }
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
    public void reciprocalReferencesReuseCachedTransfersIncludingParentPaths() throws ReflectiveOperationException {
        var firstLinkType = new MemoryLinkType(1L);
        var secondLinkType = new MemoryLinkType(2L);
        firstLinkType.inverseLinkType = secondLinkType;
        secondLinkType.inverseLinkType = firstLinkType;
        secondLinkType.parentLinkType = firstLinkType;

        var cdi = new TestCdi(session());
        try {
            var cache = new TestCache();
            var firstTransfer = cache.getTransfer(null, firstLinkType);
            var secondTransfer = firstTransfer.getInverseLinkType();

            assertEquals("TYPE_2", secondTransfer.getLinkTypeName());
            assertSame(firstTransfer, secondTransfer.getInverseLinkType());
            assertSame(firstTransfer, secondTransfer.getParentLinkType());
            assertSame(firstTransfer, cache.getTransfer(null, firstLinkType));
            assertSame(secondTransfer, cache.getTransfer(null, secondLinkType));
            assertNull(firstTransfer.getParentLinkType());
        } finally {
            cdi.restore();
        }
    }

    @Test
    public void absentInverseRemainsNull() throws ReflectiveOperationException {
        var cdi = new TestCdi(session());
        try {
            assertNull(new TestCache().getTransfer(null, new MemoryLinkType(3L)).getInverseLinkType());
        } finally {
            cdi.restore();
        }
    }

}
