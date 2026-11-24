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

package com.echothree.model.control.core.server.eventbus;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.common.pk.*;
import com.echothree.model.data.core.server.entity.*;
import com.echothree.model.data.core.server.value.*;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.google.common.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class LinkModificationSubscriberTest {

    private static final EntityPermission READ_ONLY = EntityPermission.READ_ONLY;

    private static EntityType entityType(String componentVendorName, String entityTypeName) {
        return entityType(componentVendorName, entityTypeName, 3L);
    }

    private static EntityType entityType(String componentVendorName, String entityTypeName, long identifier) {
        var componentVendorPK = new ComponentVendorPK(1L);
        var componentVendorDetail = new ComponentVendorDetail(new ComponentVendorDetailValue(
                new ComponentVendorDetailPK(2L), componentVendorPK, componentVendorName, componentVendorName, 0L, Long.MAX_VALUE), READ_ONLY);
        var componentVendor = new ComponentVendor(new ComponentVendorValue(componentVendorPK, null, null), READ_ONLY) {
            @Override
            public ComponentVendorDetail getLastDetail() {
                return componentVendorDetail;
            }
        };
        var entityTypePK = new EntityTypePK(identifier);
        var entityTypeDetail = new EntityTypeDetail(new EntityTypeDetailValue(new EntityTypeDetailPK(4L), entityTypePK,
                componentVendorPK, entityTypeName, false, 0L, false, 0, 0L, Long.MAX_VALUE), READ_ONLY) {
            @Override
            public ComponentVendor getComponentVendor() {
                return componentVendor;
            }
        };
        return new EntityType(new EntityTypeValue(entityTypePK, null, null), READ_ONLY) {
            @Override
            public EntityTypeDetail getLastDetail() {
                return entityTypeDetail;
            }
        };
    }

    private static EntityInstance entityInstance(String componentVendorName, String entityTypeName, long identifier) {
        var entityType = entityType(componentVendorName, entityTypeName);
        return new EntityInstance(new EntityInstanceValue(new EntityInstancePK(identifier + 1000L), entityType.getPrimaryKey(),
                identifier, null), READ_ONLY) {
            @Override
            public EntityType getEntityType() {
                return entityType;
            }
        };
    }

    private static SentEvent event(String componentVendorName, String entityTypeName, long identifier,
            EventTypes eventTypeName, EntityInstance createdBy) {
        var entityInstance = entityInstance(componentVendorName, entityTypeName, identifier);
        var eventType = new EventType(new EventTypeValue(new EventTypePK(5L), eventTypeName.name()), READ_ONLY);
        return new SentEvent(new Event(new EventValue(new EventPK(6L), null, 0L, 0, entityInstance.getPrimaryKey(),
                eventType.getPrimaryKey(), null, null, createdBy == null ? null : createdBy.getPrimaryKey()), READ_ONLY) {
            @Override
            public EntityInstance getEntityInstance() {
                return entityInstance;
            }

            @Override
            public EventType getEventType() {
                return eventType;
            }

            @Override
            public EntityInstance getCreatedBy() {
                return createdBy;
            }
        });
    }

    private static LinkType linkType(long identifier) {
        return new LinkType(new LinkTypeValue(new LinkTypePK(identifier), null, null), READ_ONLY);
    }

    private static Link link(long identifier, long linkTypeIdentifier, EntityInstance source, EntityInstance target) {
        return new Link(new LinkValue(new LinkPK(identifier), new LinkTypePK(linkTypeIdentifier), source.getPrimaryKey(),
                target.getPrimaryKey(), 0L, Long.MAX_VALUE), READ_ONLY);
    }

    private record Touch(BasePK target, BasePK dependency, EventTypes dependencyEventType, BasePK createdBy) {}
    private record Lookup(EntityInstance entityInstance, LinkType linkType) {}

    private static class Fixture {
        final List<Touch> touches = new ArrayList<>();
        final List<Throwable> failures = new ArrayList<>();
        final List<Lookup> lookups = new ArrayList<>();
        final EventBus eventBus = new EventBus((exception, context) -> failures.add(exception));
        final EntityInstance source = entityInstance("ECHO_THREE", "Item", 11L);
        final EntityInstance target = entityInstance("ECHO_THREE", "Party", 12L);
        final EntityInstance otherTarget = entityInstance("ECHO_THREE", "Party", 13L);
        List<Link> links = List.of(link(101L, 201L, source, target), link(102L, 201L, source, otherTarget));
        Map<Long, List<LinkType>> relatedLinkTypes = Map.of();
        final LinkTypeControl linkTypeControl = new LinkTypeControl() {
            @Override
            public LinkType getLinkTypeByEntityInstance(EntityInstance entityInstance) {
                return linkType(entityInstance.getEntityUniqueId());
            }

            @Override
            public List<LinkType> getLinkTypesByParentOrInverseLinkTypeForTouch(LinkType linkType) {
                return relatedLinkTypes.getOrDefault(linkType.getPrimaryKey().getEntityId(), List.of());
            }
        };
        final EventControl eventControl = new EventControl() {
            @Override
            public Event sendEvent(BasePK target, EventTypes eventType, BasePK dependency,
                    EventTypes dependencyEventType, BasePK createdBy) {
                assertEquals(EventTypes.TOUCH, eventType);
                touches.add(new Touch(target, dependency, dependencyEventType, createdBy));
                assertTrue(touches.size() <= 20, "Event propagation did not terminate");
                eventBus.post(event(target.getComponentVendorName(), target.getEntityTypeName(), target.getEntityId(), eventType,
                        createdBy == null ? null : entityInstance(createdBy.getComponentVendorName(), createdBy.getEntityTypeName(), createdBy.getEntityId())));
                return null;
            }
        };

        Fixture() {
            var subscriber = new LinkModificationSubscriber();
            subscriber.linkTypeControl = linkTypeControl;
            subscriber.eventControl = eventControl;
            subscriber.linkControl = new LinkControl() {
                @Override
                public List<Link> getLinksByEntityInstanceForTouch(EntityInstance entityInstance) {
                    lookups.add(new Lookup(entityInstance, null));
                    var entityInstancePK = entityInstance.getPrimaryKey();
                    return links.stream().filter(link -> link.getSourceEntityInstancePK().equals(entityInstancePK)
                            || link.getTargetEntityInstancePK().equals(entityInstancePK)).toList();
                }

                @Override
                public List<Link> getLinksByLinkTypeForTouch(LinkType linkType) {
                    lookups.add(new Lookup(null, linkType));
                    return links.stream().filter(link -> link.getLinkTypePK().equals(linkType.getPrimaryKey())).toList();
                }
            };
            eventBus.register(subscriber);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "SOURCE", "TARGET", "LINK_TYPE" })
    public void modificationsAndTouchesPropagateForAllThreeDependenciesWithTheirActorAndCause(String dependency) {
        for(var eventType : List.of(EventTypes.MODIFY, EventTypes.TOUCH)) {
            var fixture = new Fixture();
            var dependencyEntityInstance = switch(dependency) {
                case "SOURCE" -> fixture.source;
                case "TARGET" -> fixture.target;
                case "LINK_TYPE" -> entityInstance("ECHO_THREE", "LinkType", 201L);
                default -> throw new AssertionError(dependency);
            };
            var dependencyPK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(dependencyEntityInstance);
            var actor = entityInstance("ECHO_THREE", "Party", 500L);
            fixture.eventBus.post(event(dependencyPK.getComponentVendorName(), dependencyPK.getEntityTypeName(),
                    dependencyPK.getEntityId(), eventType, actor));

            assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
            var expected = dependency.equals("TARGET") ? List.of(new LinkPK(101L)) : List.of(new LinkPK(101L), new LinkPK(102L));
            assertEquals(expected, fixture.touches.stream().map(Touch::target).toList());
            assertEquals(dependency.equals("LINK_TYPE"), fixture.lookups.stream().anyMatch(lookup -> lookup.linkType() != null));
            for(var touch : fixture.touches) {
                assertEquals(dependencyPK, touch.dependency());
                assertEquals(eventType, touch.dependencyEventType());
                assertEquals(new BasePK("ECHO_THREE", "Party", 500L), touch.createdBy());
            }
        }
    }

    @Test
    public void matchingTheTypeAndBothEndpointsStillTouchesTheLinkOnce() {
        var fixture = new Fixture();
        var dependency = entityInstance("ECHO_THREE", "LinkType", 201L);
        fixture.links = List.of(link(101L, 201L, dependency, dependency));
        fixture.eventBus.post(event("ECHO_THREE", "LinkType", 201L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(List.of(new LinkPK(101L)), fixture.touches.stream().map(Touch::target).toList());
        assertNull(fixture.touches.getFirst().createdBy());
    }

    @Test
    public void endpointsFromOtherComponentVendorsAlsoPropagate() {
        var fixture = new Fixture();
        var dependency = entityInstance("OTHER", "Item", 31L);
        fixture.links = List.of(link(101L, 201L, dependency, fixture.target));
        fixture.eventBus.post(event("OTHER", "Item", 31L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(List.of(new LinkPK(101L)), fixture.touches.stream().map(Touch::target).toList());
        assertEquals(new BasePK("OTHER", "Item", 31L), fixture.touches.getFirst().dependency());
        assertNull(fixture.lookups.getFirst().linkType());
    }

    @Test
    public void foreignLinkTypeNamesDoNotMatchCoreLinkTypes() {
        var fixture = new Fixture();
        fixture.eventBus.post(event("OTHER", "LinkType", 201L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertTrue(fixture.touches.isEmpty());
        assertNull(fixture.lookups.getFirst().linkType());
    }

    @Test
    public void linkCyclesTerminateAndIndependentChangesPropagateAgain() {
        var fixture = new Fixture();
        var first = entityInstance("ECHO_THREE", "Link", 101L);
        var second = entityInstance("ECHO_THREE", "Link", 102L);
        fixture.links = List.of(link(101L, 201L, second, fixture.target), link(102L, 201L, first, fixture.target));

        for(var eventType : List.of(EventTypes.MODIFY, EventTypes.TOUCH)) {
            fixture.eventBus.post(event("ECHO_THREE", "Link", 101L, eventType, null));
        }

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(List.of(new LinkPK(102L), new LinkPK(102L)), fixture.touches.stream().map(Touch::target).toList());
        assertTrue(fixture.touches.stream().allMatch(touch -> touch.dependency().equals(new LinkPK(101L))));
    }

    @Test
    public void selfReferencesDoNotRetouchTheOrigin() {
        var fixture = new Fixture();
        var self = entityInstance("ECHO_THREE", "Link", 101L);
        fixture.links = List.of(link(101L, 201L, self, self));
        fixture.eventBus.post(event("ECHO_THREE", "Link", 101L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertTrue(fixture.touches.isEmpty());
    }

    @Test
    public void linkTypeCascadesReachLinksUsingBothSubscribersWithoutDuplicates() {
        var fixture = new Fixture();
        fixture.relatedLinkTypes = Map.of(201L, List.of(linkType(202L)), 202L, List.of(linkType(201L)));
        var originalLinkType = entityInstance("ECHO_THREE", "LinkType", 201L);
        fixture.links = List.of(link(101L, 201L, fixture.source, fixture.target),
                link(102L, 202L, fixture.source, fixture.target), link(103L, 202L, originalLinkType, fixture.target));
        var linkTypeSubscriber = new LinkTypeModificationSubscriber();
        linkTypeSubscriber.linkTypeControl = fixture.linkTypeControl;
        linkTypeSubscriber.eventControl = fixture.eventControl;
        fixture.eventBus.register(linkTypeSubscriber);
        fixture.eventBus.post(event("ECHO_THREE", "LinkType", 201L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(4, fixture.touches.size());
        assertEquals(Set.of(new LinkPK(101L), new LinkPK(102L), new LinkPK(103L), new LinkTypePK(202L)),
                Set.copyOf(fixture.touches.stream().map(Touch::target).toList()));
    }

    @Test
    public void ignoresCreateReadAndDeleteEventsWithoutLookingUpDependencies() {
        var fixture = new Fixture();
        for(var eventType : List.of(EventTypes.CREATE, EventTypes.READ, EventTypes.DELETE)) {
            fixture.eventBus.post(event("ECHO_THREE", "Item", 11L, eventType, null));
            fixture.eventBus.post(event("ECHO_THREE", "LinkType", 201L, eventType, null));
        }

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertTrue(fixture.lookups.isEmpty());
        assertTrue(fixture.touches.isEmpty());
    }

}
