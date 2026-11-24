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
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.common.pk.*;
import com.echothree.model.data.core.server.entity.*;
import com.echothree.model.data.core.server.value.*;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.google.common.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class LinkTypeModificationSubscriberTest {

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

    private record Touch(BasePK target, EventTypes eventType, BasePK dependency, EventTypes dependencyEventType, BasePK createdBy) {}

    private static class Fixture {
        final List<Touch> touches = new ArrayList<>();
        final List<Throwable> failures = new ArrayList<>();
        final List<String> lookups = new ArrayList<>();
        final EventBus eventBus = new EventBus((exception, context) -> failures.add(exception));
        final LinkType child = linkType(101L);
        final LinkType grandchild = linkType(102L);
        final LinkType sibling = linkType(103L);
        final LinkType parent = linkType(100L);
        final EntityType endpoint = entityType("ECHO_THREE", "Item", 400L);
        final LinkCardinality cardinality = new LinkCardinality(new LinkCardinalityValue(new LinkCardinalityPK(200L), null, null), READ_ONLY);
        final LinkDirectionality directionality = new LinkDirectionality(new LinkDirectionalityValue(new LinkDirectionalityPK(300L), null, null), READ_ONLY);
        boolean cascade;
        Map<Long, List<LinkType>> relatedLinkTypes;

        Fixture() {
            var subscriber = new LinkTypeModificationSubscriber();
            subscriber.entityTypeControl = new EntityTypeControl() {
                @Override
                public EntityType getEntityTypeByEntityInstance(EntityInstance entityInstance) {
                    assertEquals(400L, entityInstance.getEntityUniqueId());
                    return endpoint;
                }
            };
            subscriber.linkCardinalityControl = new LinkCardinalityControl() {
                @Override
                public LinkCardinality getLinkCardinalityByEntityInstance(EntityInstance entityInstance) {
                    assertEquals(200L, entityInstance.getEntityUniqueId());
                    return cardinality;
                }
            };
            subscriber.linkDirectionalityControl = new LinkDirectionalityControl() {
                @Override
                public LinkDirectionality getLinkDirectionalityByEntityInstance(EntityInstance entityInstance) {
                    assertEquals(300L, entityInstance.getEntityUniqueId());
                    return directionality;
                }
            };
            subscriber.linkTypeControl = new LinkTypeControl() {
                @Override
                public LinkType getLinkTypeByEntityInstance(EntityInstance entityInstance) {
                    return linkType(entityInstance.getEntityUniqueId());
                }

                @Override
                public List<LinkType> getLinkTypesByParentOrInverseLinkTypeForTouch(LinkType parentLinkType) {
                    lookups.add("LinkType");
                    var identifier = parentLinkType.getPrimaryKey().getEntityId();
                    return relatedLinkTypes != null ? relatedLinkTypes.getOrDefault(identifier, List.of())
                            : identifier.equals(parent.getPrimaryKey().getEntityId()) ? List.of(child, sibling)
                            : cascade && identifier.equals(child.getPrimaryKey().getEntityId()) ? List.of(grandchild) : List.of();
                }

                @Override
                public List<LinkType> getLinkTypesByEntityTypeForTouch(EntityType entityType) {
                    assertSame(endpoint, entityType);
                    lookups.add("EntityType");
                    return List.of(child, sibling);
                }

                @Override
                public List<LinkType> getLinkTypesByLinkCardinalityForTouch(LinkCardinality linkCardinality) {
                    assertSame(cardinality, linkCardinality);
                    lookups.add("LinkCardinality");
                    return List.of(child, sibling);
                }

                @Override
                public List<LinkType> getLinkTypesByLinkDirectionalityForTouch(LinkDirectionality linkDirectionality) {
                    assertSame(directionality, linkDirectionality);
                    lookups.add("LinkDirectionality");
                    return List.of(child, sibling);
                }
            };
            subscriber.eventControl = new EventControl() {
                @Override
                public Event sendEvent(BasePK target, EventTypes eventType, BasePK dependency,
                        EventTypes dependencyEventType, BasePK createdBy) {
                    touches.add(new Touch(target, eventType, dependency, dependencyEventType, createdBy));
                    assertTrue(touches.size() <= 20, "Event propagation did not terminate");
                    if(cascade) {
                        eventBus.post(event(target.getComponentVendorName(), target.getEntityTypeName(), target.getEntityId(), eventType,
                                createdBy == null ? null : entityInstance(createdBy.getComponentVendorName(), createdBy.getEntityTypeName(), createdBy.getEntityId())));
                    }
                    return null;
                }
            };
            eventBus.register(subscriber);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "LinkType", "EntityType", "LinkCardinality", "LinkDirectionality" })
    public void modifyAndTouchPropagateToEveryDependentWithOriginalActorAndCause(String dependencyName) {
        var dependencyIdentifier = switch(dependencyName) {
            case "LinkType" -> 100L;
            case "EntityType" -> 400L;
            case "LinkCardinality" -> 200L;
            case "LinkDirectionality" -> 300L;
            default -> throw new AssertionError(dependencyName);
        };
        for(var incomingEventType : List.of(EventTypes.MODIFY, EventTypes.TOUCH)) {
            var fixture = new Fixture();
            var actor = entityInstance("ECHO_THREE", "Party", 500L);
            fixture.eventBus.post(event("ECHO_THREE", dependencyName, dependencyIdentifier, incomingEventType, actor));

            assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
            assertEquals(List.of(dependencyName), fixture.lookups);
            assertEquals(List.of(fixture.child.getPrimaryKey(), fixture.sibling.getPrimaryKey()),
                    fixture.touches.stream().map(Touch::target).toList());
            for(var touch : fixture.touches) {
                assertEquals(EventTypes.TOUCH, touch.eventType());
                assertEquals(new BasePK("ECHO_THREE", dependencyName, dependencyIdentifier), touch.dependency());
                assertEquals(incomingEventType, touch.dependencyEventType());
                assertEquals(new BasePK("ECHO_THREE", "Party", 500L), touch.createdBy());
            }
        }
    }

    @Test
    public void ignoresOtherVendorsEntitiesAndNonModificationEvents() {
        var fixture = new Fixture();
        for(var dependencyName : List.of("LinkType", "EntityType", "LinkCardinality", "LinkDirectionality")) {
            for(var eventType : List.of(EventTypes.CREATE, EventTypes.READ, EventTypes.DELETE)) {
                fixture.eventBus.post(event("ECHO_THREE", dependencyName, 100L, eventType, null));
            }
            fixture.eventBus.post(event("OTHER", dependencyName, 100L, EventTypes.MODIFY, null));
        }
        fixture.eventBus.post(event("ECHO_THREE", "Link", 100L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertTrue(fixture.lookups.isEmpty());
        assertTrue(fixture.touches.isEmpty());
    }

    @Test
    public void parentTouchCascadesToDescendantsAndAllowsSystemEvents() {
        var fixture = new Fixture();
        fixture.cascade = true;
        fixture.eventBus.post(event("ECHO_THREE", "LinkType", 100L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(List.of(fixture.child.getPrimaryKey(), fixture.sibling.getPrimaryKey(), fixture.grandchild.getPrimaryKey()),
                fixture.touches.stream().map(Touch::target).toList());
        assertEquals(EventTypes.TOUCH, fixture.touches.get(2).dependencyEventType());
        assertEquals(fixture.child.getPrimaryKey(), fixture.touches.get(2).dependency());
        assertTrue(fixture.touches.stream().allMatch(touch -> touch.createdBy() == null));
    }

    @Test
    public void leafLinkTypeDoesNotEmitAnyTouches() {
        var fixture = new Fixture();
        fixture.eventBus.post(event("ECHO_THREE", "LinkType", 999L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertTrue(fixture.touches.isEmpty());
    }

    @Test
    public void reciprocalInversesDoNotRetouchTheOriginAndIndependentUpdatesPropagateAgain() {
        var fixture = new Fixture();
        fixture.cascade = true;
        fixture.relatedLinkTypes = Map.of(100L, List.of(fixture.child), 101L, List.of(fixture.parent));
        var actor = entityInstance("ECHO_THREE", "Party", 500L);

        for(var incomingEventType : List.of(EventTypes.MODIFY, EventTypes.TOUCH)) {
            fixture.eventBus.post(event("ECHO_THREE", "LinkType", 100L, incomingEventType, actor));
        }
        fixture.eventBus.post(event("ECHO_THREE", "LinkType", 101L, EventTypes.MODIFY, actor));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(List.of(fixture.child.getPrimaryKey(), fixture.child.getPrimaryKey(), fixture.parent.getPrimaryKey()),
                fixture.touches.stream().map(Touch::target).toList());
        assertTrue(fixture.touches.stream().allMatch(touch -> touch.createdBy().equals(new BasePK("ECHO_THREE", "Party", 500L))));
    }

    @Test
    public void mixedParentAndInverseCyclesTouchEachReachableDependentOnce() {
        var fixture = new Fixture();
        fixture.cascade = true;
        fixture.relatedLinkTypes = Map.of(
                100L, List.of(fixture.child, fixture.sibling),
                101L, List.of(fixture.parent, fixture.grandchild),
                103L, List.of(fixture.child, fixture.grandchild),
                102L, List.of(fixture.sibling));
        fixture.eventBus.post(event("ECHO_THREE", "LinkType", 100L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(List.of(fixture.child.getPrimaryKey(), fixture.sibling.getPrimaryKey(), fixture.grandchild.getPrimaryKey()),
                fixture.touches.stream().map(Touch::target).toList());
        assertEquals(4, fixture.lookups.size());
        assertEquals(fixture.child.getPrimaryKey(), fixture.touches.get(2).dependency());
        assertEquals(EventTypes.TOUCH, fixture.touches.get(2).dependencyEventType());
    }

    @Test
    public void endpointChangesPropagateThroughInverseCyclesWithoutDuplicateTouches() {
        var fixture = new Fixture();
        fixture.cascade = true;
        fixture.relatedLinkTypes = Map.of(
                101L, List.of(fixture.sibling, fixture.grandchild),
                103L, List.of(fixture.child),
                102L, List.of(fixture.child));
        fixture.eventBus.post(event("ECHO_THREE", "EntityType", 400L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertEquals(List.of(fixture.child.getPrimaryKey(), fixture.sibling.getPrimaryKey(), fixture.grandchild.getPrimaryKey()),
                fixture.touches.stream().map(Touch::target).toList());
    }

    @Test
    public void selfReferencesDoNotCreateTouchLoops() {
        var fixture = new Fixture();
        fixture.cascade = true;
        fixture.relatedLinkTypes = Map.of(100L, List.of(fixture.parent));
        fixture.eventBus.post(event("ECHO_THREE", "LinkType", 100L, EventTypes.MODIFY, null));

        assertTrue(fixture.failures.isEmpty(), fixture.failures.toString());
        assertTrue(fixture.touches.isEmpty());
    }

}
