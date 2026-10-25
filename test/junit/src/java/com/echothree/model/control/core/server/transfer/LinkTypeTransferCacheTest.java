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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.server.graphql.LinkTypeObject;
import com.echothree.model.control.core.server.graphql.LinkObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.GraphQlExecutionContext;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.GraphQlSecurityCommand;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import org.jooq.Condition;
import java.util.IdentityHashMap;
import java.util.Map;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        final Map<Class<?>, Object> controls = new java.util.HashMap<>();
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
            var selected = type == Session.class ? session : controls.get(type);
            assertNotNull(selected, type.getName());
            return (Instance<U>)Proxy.newProxyInstance(Instance.class.getClassLoader(), new Class<?>[] { Instance.class },
                    (proxy, method, args) -> {
                        if(method.getName().equals("get")) {
                            return selected;
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

    private record LinkScope(EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
    }

    private static class TestCache extends LinkTypeTransferCache {
        final List<LinkScope> scopes = new ArrayList<>();
        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        TestCache() {
            setIncludeEntityInstance(false);
            linkControl = new LinkControl() {
                @Override
                public List<LinkTransfer> getLinkTransfers(UserVisit userVisit, LinkType linkType,
                        EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
                    scopes.add(new LinkScope(sourceEntityInstance, targetEntityInstance));
                    return List.of(new LinkTransfer(null, null, null));
                }
            };
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

    @Test
    public void linkInclusionIsOptional() throws ReflectiveOperationException {
        var cdi = new TestCdi(session());
        try {
            var cache = new TestCache();
            assertNull(cache.getTransfer(null, new MemoryLinkType(1L)).getLinks());
            assertTrue(cache.scopes.isEmpty());
        } finally {
            cdi.restore();
        }
    }

    @Test
    public void scopedLinkTransfersAreCachedSeparatelyByEndpointAndRole() throws ReflectiveOperationException {
        var session = session();
        session.setOptions(Set.of(CoreOptions.LinkTypeIncludeLinks));
        var cdi = new TestCdi(session);
        try {
            var cache = new TestCache();
            var linkType = new MemoryLinkType(1L);
            var firstEntityInstance = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(10L), new EntityTypePK(1L), 10L, null), EntityPermission.READ_ONLY);
            var secondEntityInstance = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(11L), new EntityTypePK(1L), 11L, null), EntityPermission.READ_ONLY);
            var unscoped = cache.getTransfer(null, linkType);
            var firstSource = cache.getTransfer(null, linkType, firstEntityInstance, null);
            var secondSource = cache.getTransfer(null, linkType, secondEntityInstance, null);
            var firstTarget = cache.getTransfer(null, linkType, null, firstEntityInstance);
            var both = cache.getTransfer(null, linkType, firstEntityInstance, secondEntityInstance);

            assertEquals(List.of(new LinkScope(null, null), new LinkScope(firstEntityInstance, null),
                    new LinkScope(secondEntityInstance, null), new LinkScope(null, firstEntityInstance),
                    new LinkScope(firstEntityInstance, secondEntityInstance)), cache.scopes);
            assertNotSame(unscoped, firstSource);
            assertNotSame(firstSource, secondSource);
            assertNotSame(firstSource, firstTarget);
            assertNotSame(firstSource, both);
            assertSame(unscoped, cache.getTransfer(null, linkType));
            assertSame(firstSource, cache.getTransfer(null, linkType, firstEntityInstance, null));
            assertSame(secondSource, cache.getTransfer(null, linkType, secondEntityInstance, null));
            assertSame(firstTarget, cache.getTransfer(null, linkType, null, firstEntityInstance));
            assertSame(both, cache.getTransfer(null, linkType, firstEntityInstance, secondEntityInstance));
            assertEquals(5, cache.scopes.size());
            assertEquals(1, unscoped.getLinks().getSize());
            assertEquals(1, firstSource.getLinks().getSize());
        } finally {
            cdi.restore();
        }
    }

    private static class TestLinkCache extends LinkTransferCache {
        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        TestLinkCache(Link link) {
            linkTypeControl = new LinkTypeControl() {
                @Override
                public LinkTypeTransfer getLinkTypeTransfer(UserVisit userVisit, LinkType linkType) {
                    var transfer = getTransfer(userVisit, link);
                    assertNull(transfer.getLinkType());
                    return new LinkTypeTransfer("TEST", null, null, null, null, null, false, null, 0, null, null, null);
                }
            };
            entityInstanceControl = new EntityInstanceControl() {
                @Override
                public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, EntityInstance entityInstance,
                        boolean includeEntityAppearance, boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
                    return new EntityInstanceTransfer(null, entityInstance.getEntityUniqueId(), null, null, null, null);
                }
            };
        }
    }

    @Test
    public void linkCacheBreaksCyclesBeforeFollowingLinkTypeAndEndpoints() throws ReflectiveOperationException {
        var sourceEntityInstance = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(10L), new EntityTypePK(1L), 10L, null), EntityPermission.READ_ONLY);
        var targetEntityInstance = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(11L), new EntityTypePK(1L), 11L, null), EntityPermission.READ_ONLY);
        var linkType = new MemoryLinkType(1L);
        var link = new Link(new LinkValue(new LinkPK(20L), linkType.getPrimaryKey(), sourceEntityInstance.getPrimaryKey(),
                targetEntityInstance.getPrimaryKey(), 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override public LinkType getLinkType() { return linkType; }
            @Override public EntityInstance getSourceEntityInstance() { return sourceEntityInstance; }
            @Override public EntityInstance getTargetEntityInstance() { return targetEntityInstance; }
        };
        var cdi = new TestCdi(session());
        try {
            var cache = new TestLinkCache(link);
            var transfer = cache.getTransfer(null, link);
            assertSame(transfer, cache.getTransfer(null, link));
            assertEquals("TEST", transfer.getLinkType().getLinkTypeName());
            assertEquals(10L, transfer.getSourceEntityInstance().getEntityUniqueId());
            assertEquals(11L, transfer.getTargetEntityInstance().getEntityUniqueId());
        } finally {
            cdi.restore();
        }
    }

    private static DataFetchingEnvironment environment(boolean allowed) {
        var executionContext = new GraphQlExecutionContext(null, null, null, null) {
            @Override
            public <F extends BaseForm> boolean hasAccess(Class<? extends GraphQlSecurityCommand<F>> command) {
                return allowed;
            }
        };
        return DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
                .arguments(Map.of())
                .graphQLContext(GraphQLContext.newContext().of(BaseGraphQl.GRAPHQL_EXECUTION_CONTEXT, executionContext).build())
                .build();
    }

    private static Link link(long identifier, LinkType linkType, EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
        return new Link(new LinkValue(new LinkPK(identifier), linkType.getPrimaryKey(), sourceEntityInstance.getPrimaryKey(),
                targetEntityInstance.getPrimaryKey(), 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override public LinkType getLinkType() { return linkType; }
            @Override public EntityInstance getSourceEntityInstance() { return sourceEntityInstance; }
            @Override public EntityInstance getTargetEntityInstance() { return targetEntityInstance; }
        };
    }

    private static EntityInstance graphQlEntityInstance(long identifier) {
        var componentVendorPK = new ComponentVendorPK(1L);
        var componentVendorDetail = new ComponentVendorDetail(new ComponentVendorDetailValue(new ComponentVendorDetailPK(1L),
                componentVendorPK, "ECHO_THREE", "Echo Three", 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
        var componentVendor = new ComponentVendor(new ComponentVendorValue(componentVendorPK, null, null), EntityPermission.READ_ONLY) {
            @Override public ComponentVendorDetail getLastDetail() { return componentVendorDetail; }
        };
        var entityTypePK = new EntityTypePK(1L);
        var entityTypeDetail = new EntityTypeDetail(new EntityTypeDetailValue(new EntityTypeDetailPK(1L), entityTypePK,
                componentVendorPK, "Item", true, null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override public ComponentVendor getComponentVendor() { return componentVendor; }
        };
        var entityType = new EntityType(new EntityTypeValue(entityTypePK, null, null), EntityPermission.READ_ONLY) {
            @Override public EntityTypeDetail getLastDetail() { return entityTypeDetail; }
        };
        return new EntityInstance(new EntityInstanceValue(new EntityInstancePK(identifier), entityTypePK, identifier, null), EntityPermission.READ_ONLY) {
            @Override public EntityType getEntityType() { return entityType; }
        };
    }

    @Test
    public void graphQlCountsAndListsUseTheSameStoredEndpointScope() throws ReflectiveOperationException {
        var linkType = new MemoryLinkType(1L);
        var firstEntityInstance = graphQlEntityInstance(10L);
        var secondEntityInstance = graphQlEntityInstance(11L);
        var thirdEntityInstance = graphQlEntityInstance(12L);
        var links = List.of(link(20L, linkType, firstEntityInstance, secondEntityInstance),
                link(21L, linkType, thirdEntityInstance, firstEntityInstance), link(22L, linkType, secondEntityInstance, thirdEntityInstance));
        var cdi = new TestCdi(session());
        cdi.controls.put(LinkControl.class, new LinkControl() {
            final IdentityHashMap<Condition, List<Link>> matches = new IdentityHashMap<>();
            Condition countedCondition;

            @Override
            public Condition getLinkFilter(LinkType requestedLinkType, EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
                var condition = super.getLinkFilter(requestedLinkType, sourceEntityInstance, targetEntityInstance);
                matches.put(condition, links.stream().filter(link -> link.getLinkTypePK().equals(requestedLinkType.getPrimaryKey())
                        && (sourceEntityInstance == null || sourceEntityInstance.getPrimaryKey().equals(link.getSourceEntityInstancePK()))
                        && (targetEntityInstance == null || targetEntityInstance.getPrimaryKey().equals(link.getTargetEntityInstancePK()))).toList());
                return condition;
            }

            @Override
            public long countLinks(Condition condition) {
                countedCondition = condition;
                return matches.get(condition).size();
            }

            @Override
            public List<Link> getLinks(Condition condition) {
                assertSame(countedCondition, condition);
                return matches.get(condition);
            }
        });
        try {
            var env = environment(true);
            var all = new LinkTypeObject(linkType).getLinks(env);
            assertEquals(3, all.getTotalCount());
            assertEquals(3, all.getEdges().size());
            var source = new LinkTypeObject(linkType, firstEntityInstance, null).getLinks(env);
            assertEquals(1, source.getTotalCount());
            assertEquals(10L, source.iterator().next().getSourceEntityInstance(env).getEntityUniqueId());
            assertEquals(11L, source.iterator().next().getTargetEntityInstance(env).getEntityUniqueId());
            var target = new LinkTypeObject(linkType, null, firstEntityInstance).getLinks(env);
            assertEquals(1, target.getTotalCount());
            assertEquals(12L, target.iterator().next().getSourceEntityInstance(env).getEntityUniqueId());
            assertEquals(10L, target.iterator().next().getTargetEntityInstance(env).getEntityUniqueId());
            assertEquals(1, new LinkTypeObject(linkType, firstEntityInstance, secondEntityInstance).getLinks(env).getTotalCount());
            assertEquals(0, new LinkTypeObject(linkType, secondEntityInstance, firstEntityInstance).getLinks(env).getTotalCount());
        } finally {
            cdi.restore();
        }
    }

    @Test
    public void graphQlSecurityDeniesLinkListsAndObjectFields() throws ReflectiveOperationException {
        var env = environment(false);
        var linkType = new MemoryLinkType(1L);
        var cdi = new TestCdi(session());
        try {
            // No LinkControl is registered: denied access must not attempt a query.
            assertEquals(0, new LinkTypeObject(linkType).getLinks(env).getTotalCount());
            var linkObject = new LinkObject(null);
            assertNull(linkObject.getLinkType(env));
            assertNull(linkObject.getSourceEntityInstance(env));
            assertNull(linkObject.getTargetEntityInstance(env));
        } finally {
            cdi.restore();
        }
    }

}
