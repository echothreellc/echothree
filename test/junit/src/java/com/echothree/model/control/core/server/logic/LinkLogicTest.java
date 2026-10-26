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

package com.echothree.model.control.core.server.logic;

import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.model.control.core.common.exception.UnknownLinkTypeNameException;
import com.echothree.model.control.core.common.exception.UnknownEntityRefException;
import com.echothree.model.control.core.common.exception.UnknownUuidException;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.model.control.core.common.LinkCardinalities;
import com.echothree.model.control.core.common.LinkDirectionalities;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.common.pk.LinkCardinalityPK;
import com.echothree.model.data.core.common.pk.LinkDirectionalityPK;
import com.echothree.model.data.core.common.pk.LinkCardinalityDetailPK;
import com.echothree.model.data.core.common.pk.LinkDirectionalityDetailPK;
import com.echothree.model.data.core.common.pk.LinkPK;
import com.echothree.model.data.core.common.pk.LinkTypeDetailPK;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.core.server.entity.LinkCardinalityDetail;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.entity.LinkDirectionalityDetail;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.LinkTypeDetail;
import com.echothree.model.data.core.server.value.EntityInstanceValue;
import com.echothree.model.data.core.server.value.LinkTypeDetailValue;
import com.echothree.model.data.core.server.value.LinkTypeValue;
import com.echothree.model.data.core.server.value.LinkCardinalityValue;
import com.echothree.model.data.core.server.value.LinkCardinalityDetailValue;
import com.echothree.model.data.core.server.value.LinkDirectionalityValue;
import com.echothree.model.data.core.server.value.LinkDirectionalityDetailValue;
import com.echothree.model.data.core.server.value.LinkValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Predicate;
import org.jooq.Condition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkLogicTest {

    private static final EntityTypePK SOURCE_ENTITY_TYPE_PK = new EntityTypePK(1L);
    private static final EntityTypePK TARGET_ENTITY_TYPE_PK = new EntityTypePK(2L);

    private static EntityInstance entityInstance(long identifier, EntityTypePK entityTypePK) {
        return new EntityInstance(new EntityInstanceValue(new EntityInstancePK(identifier), entityTypePK, identifier, null),
                EntityPermission.READ_ONLY);
    }

    private static LinkType linkType() {
        return linkType(3L, LinkCardinalities.MANY_TO_MANY.name(), LinkDirectionalities.DIRECTED.name());
    }

    private static LinkType linkType(long identifier, String cardinalityName, String directionalityName) {
        return linkType(identifier, cardinalityName, directionalityName, LinkDirectionalities.SYMMETRIC.name().equals(directionalityName));
    }

    private static LinkType linkType(long identifier, String cardinalityName, String directionalityName, boolean sameEndpointTypes) {
        var linkTypePK = new LinkTypePK(identifier);
        var linkCardinalityPK = new LinkCardinalityPK(6L);
        var linkCardinalityDetail = new LinkCardinalityDetail(new LinkCardinalityDetailValue(new LinkCardinalityDetailPK(8L),
                linkCardinalityPK, cardinalityName, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
        var linkCardinality = new LinkCardinality(new LinkCardinalityValue(linkCardinalityPK, null, null), EntityPermission.READ_ONLY) {
            @Override
            public LinkCardinalityDetail getLastDetail() {
                return linkCardinalityDetail;
            }
        };
        var linkDirectionalityPK = new LinkDirectionalityPK(7L);
        var linkDirectionalityDetail = new LinkDirectionalityDetail(new LinkDirectionalityDetailValue(new LinkDirectionalityDetailPK(9L),
                linkDirectionalityPK, directionalityName, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
        var linkDirectionality = new LinkDirectionality(new LinkDirectionalityValue(linkDirectionalityPK, null, null), EntityPermission.READ_ONLY) {
            @Override
            public LinkDirectionalityDetail getLastDetail() {
                return linkDirectionalityDetail;
            }
        };
        var targetEntityTypePK = sameEndpointTypes ? SOURCE_ENTITY_TYPE_PK : TARGET_ENTITY_TYPE_PK;
        var linkTypeDetail = new LinkTypeDetail(new LinkTypeDetailValue(new LinkTypeDetailPK(4L), linkTypePK,
                "TEST_" + identifier, null, SOURCE_ENTITY_TYPE_PK, targetEntityTypePK, linkCardinalityPK,
                linkDirectionalityPK, false, null, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override
            public LinkCardinality getLinkCardinality() {
                return linkCardinality;
            }
            @Override
            public LinkDirectionality getLinkDirectionality() {
                return linkDirectionality;
            }
        };

        return new LinkType(new LinkTypeValue(linkTypePK, null, null), EntityPermission.READ_ONLY) {
            @Override
            public LinkTypeDetail getLastDetail() {
                return linkTypeDetail;
            }
        };
    }

    private static class MemoryControl extends LinkControl {
        Link link;
        final List<Link> links = new ArrayList<>();
        final List<EntityInstancePK> locks = new ArrayList<>();
        final IdentityHashMap<Condition, Predicate<Link>> filters = new IdentityHashMap<>();
        EntityPermission entityPermission;
        int creates;
        int deletes;

        @Override
        public Link getLink(LinkType linkType, EntityInstance sourceEntityInstance,
                EntityInstance targetEntityInstance, EntityPermission entityPermission) {
            this.entityPermission = entityPermission;
            return links.stream().filter(existingLink -> existingLink.getLinkTypePK().equals(linkType.getPrimaryKey())
                    && existingLink.getSourceEntityInstancePK().equals(sourceEntityInstance.getPrimaryKey())
                    && existingLink.getTargetEntityInstancePK().equals(targetEntityInstance.getPrimaryKey())).findFirst().orElse(null);
        }

        @Override
        public Condition getLinkFilter(LinkType linkType, EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
            var condition = super.getLinkFilter(linkType, sourceEntityInstance, targetEntityInstance);
            filters.put(condition, existingLink -> (linkType == null || existingLink.getLinkTypePK().equals(linkType.getPrimaryKey()))
                    && (sourceEntityInstance == null || existingLink.getSourceEntityInstancePK().equals(sourceEntityInstance.getPrimaryKey()))
                    && (targetEntityInstance == null || existingLink.getTargetEntityInstancePK().equals(targetEntityInstance.getPrimaryKey())));
            return condition;
        }

        @Override
        public List<Link> getLinksForUpdate(Condition condition) {
            return links.stream().filter(filters.get(condition)).toList();
        }

        @Override
        public Link createLink(LinkType linkType, EntityInstance sourceEntityInstance,
                EntityInstance targetEntityInstance, BasePK createdBy) {
            creates++;
            link = new Link(new LinkValue(new LinkPK((long)creates + 5L), linkType.getPrimaryKey(), sourceEntityInstance.getPrimaryKey(),
                    targetEntityInstance.getPrimaryKey(), 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
            links.add(link);
            return link;
        }

        @Override
        public void deleteLink(Link link, BasePK deletedBy) {
            deletes++;
            links.remove(link);
            this.link = null;
        }
    }

    @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
    private static LinkLogic logic(MemoryControl memoryLinkControl) {
        var linkLogic = new LinkLogic();
        linkLogic.linkControl = memoryLinkControl;
        linkLogic.entityInstanceControl = new EntityInstanceControl() {
            @Override
            public EntityInstance getEntityInstanceByPKForUpdate(EntityInstancePK entityInstancePK) {
                memoryLinkControl.locks.add(entityInstancePK);
                return null;
            }
        };
        return linkLogic;
    }

    @Test
    public void createsLinkAndRejectsDuplicateWithoutCreatingAgain() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        var errors = new DummyExecutionErrorAccumulator();
        var link = linkLogic.createLink(errors, linkType(), sourceEntityInstance, targetEntityInstance, null);

        assertNotNull(link);
        assertFalse(errors.hasExecutionErrors());
        assertSame(link, linkLogic.createLink(errors, linkType(), sourceEntityInstance, targetEntityInstance, null));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(1, linkControl.creates);
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR,
                ExecutionErrors.DuplicateLink.name()));
    }

    @Test
    public void rejectsWrongSourceAndTargetTypesBeforePersistence() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(linkLogic.createLink(errors, linkType(), targetEntityInstance, targetEntityInstance, null));
        assertTrue(errors.hasExecutionErrors());
        errors = new DummyExecutionErrorAccumulator();
        assertNull(linkLogic.createLink(errors, linkType(), sourceEntityInstance, sourceEntityInstance, null));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, linkControl.creates);
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR,
                ExecutionErrors.InvalidLinkEndpoints.name()));
    }

    @Test
    public void reportsUnknownLinkAndDoesNotDelete() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(linkLogic.getLink(errors, linkType(), sourceEntityInstance, targetEntityInstance));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(EntityPermission.READ_ONLY, linkControl.entityPermission);
        linkLogic.deleteLink(new DummyExecutionErrorAccumulator(), linkType(), sourceEntityInstance, targetEntityInstance, null);
        assertEquals(EntityPermission.READ_WRITE, linkControl.entityPermission);
        assertEquals(0, linkControl.deletes);
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR,
                ExecutionErrors.UnknownLink.name()));
    }

    @Test
    public void deletesExistingLinkUsingWritePermission() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        linkLogic.createLink(null, linkType(), sourceEntityInstance, targetEntityInstance, null);
        var errors = new DummyExecutionErrorAccumulator();

        linkLogic.deleteLink(errors, linkType(), sourceEntityInstance, targetEntityInstance, null);

        assertFalse(errors.hasExecutionErrors());
        assertEquals(EntityPermission.READ_WRITE, linkControl.entityPermission);
        assertEquals(1, linkControl.deletes);
        assertNull(linkControl.link);
    }

    @Test
    public void directedCardinalityRestrictsTheCorrectEndpoint() {
        for(var cardinality : LinkCardinalities.values()) {
            for(var reuseSource : List.of(true, false)) {
                var linkControl = new MemoryControl();
                var linkLogic = logic(linkControl);
                var linkType = linkType(3L, cardinality.name(), LinkDirectionalities.DIRECTED.name());
                var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
                var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
                linkLogic.createLink(null, linkType, sourceEntityInstance, targetEntityInstance, null);
                var errors = new DummyExecutionErrorAccumulator();
                var secondLink = linkLogic.createLink(errors, linkType,
                        reuseSource ? sourceEntityInstance : entityInstance(12L, SOURCE_ENTITY_TYPE_PK),
                        reuseSource ? entityInstance(13L, TARGET_ENTITY_TYPE_PK) : targetEntityInstance, null);
                var rejected = cardinality == LinkCardinalities.ONE_TO_ONE
                        || (reuseSource ? cardinality == LinkCardinalities.MANY_TO_ONE : cardinality == LinkCardinalities.ONE_TO_MANY);

                assertEquals(rejected, errors.hasExecutionErrors(), cardinality + ": reuseSource=" + reuseSource);
                assertEquals(rejected ? 1 : 2, linkControl.creates);
                if(rejected) {
                    assertNull(secondLink);
                    assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR,
                            ExecutionErrors.LinkCardinalityViolation.name()));
                } else {
                    assertNotNull(secondLink);
                }
            }
        }
    }

    @Test
    public void symmetricLinksHaveOneRowAndSupportReverseLookupAndDeletion() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var linkType = linkType(3L, LinkCardinalities.MANY_TO_MANY.name(), LinkDirectionalities.SYMMETRIC.name());
        var firstEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var secondEntityInstance = entityInstance(11L, SOURCE_ENTITY_TYPE_PK);
        var link = linkLogic.createLink(null, linkType, firstEntityInstance, secondEntityInstance, null);

        assertSame(link, linkLogic.getLink(null, linkType, secondEntityInstance, firstEntityInstance));
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(link, linkLogic.createLink(errors, linkType, secondEntityInstance, firstEntityInstance, null));
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR, ExecutionErrors.DuplicateLink.name()));
        assertEquals(1, linkControl.links.size());
        linkLogic.deleteLink(null, linkType, secondEntityInstance, firstEntityInstance, null);
        assertEquals(EntityPermission.READ_WRITE, linkControl.entityPermission);
        assertTrue(linkControl.links.isEmpty());
        assertNotNull(linkLogic.createLink(null, linkType, secondEntityInstance, firstEntityInstance, null));
    }

    @Test
    public void symmetricOneToOneChecksBothRolesAtBothEndpoints() {
        for(var firstIdentifier : List.of(10L, 11L, 12L)) {
            for(var secondIdentifier : List.of(10L, 11L, 12L)) {
                if(firstIdentifier == 12L || secondIdentifier == 12L) {
                    var linkControl = new MemoryControl();
                    var linkLogic = logic(linkControl);
                    var linkType = linkType(3L, LinkCardinalities.ONE_TO_ONE.name(), LinkDirectionalities.SYMMETRIC.name());
                    linkLogic.createLink(null, linkType, entityInstance(10L, SOURCE_ENTITY_TYPE_PK),
                            entityInstance(11L, SOURCE_ENTITY_TYPE_PK), null);
                    var errors = new DummyExecutionErrorAccumulator();
                    var link = linkLogic.createLink(errors, linkType, entityInstance(firstIdentifier, SOURCE_ENTITY_TYPE_PK),
                            entityInstance(secondIdentifier, SOURCE_ENTITY_TYPE_PK), null);

                    assertEquals(firstIdentifier != 12L || secondIdentifier != 12L, errors.hasExecutionErrors());
                    assertEquals(errors.hasExecutionErrors(), link == null);
                }
            }
        }
    }

    @Test
    public void symmetricManyToManyAllowsMultiplePartnersInEitherRole() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var linkType = linkType(3L, LinkCardinalities.MANY_TO_MANY.name(), LinkDirectionalities.SYMMETRIC.name());
        var firstEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var secondEntityInstance = entityInstance(11L, SOURCE_ENTITY_TYPE_PK);
        var thirdEntityInstance = entityInstance(12L, SOURCE_ENTITY_TYPE_PK);

        assertNotNull(linkLogic.createLink(null, linkType, firstEntityInstance, secondEntityInstance, null));
        assertNotNull(linkLogic.createLink(null, linkType, thirdEntityInstance, firstEntityInstance, null));
        assertNotNull(linkLogic.createLink(null, linkType, secondEntityInstance, thirdEntityInstance, null));
        assertEquals(3, linkControl.links.size());
    }

    @Test
    public void directedLinksDoNotResolveOrDeleteInReverseAndMayCoexist() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var linkType = linkType(3L, LinkCardinalities.MANY_TO_MANY.name(), LinkDirectionalities.DIRECTED.name(), true);
        var firstEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var secondEntityInstance = entityInstance(11L, SOURCE_ENTITY_TYPE_PK);
        var firstLink = linkLogic.createLink(null, linkType, firstEntityInstance, secondEntityInstance, null);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(linkLogic.getLink(errors, linkType, secondEntityInstance, firstEntityInstance));
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR, ExecutionErrors.UnknownLink.name()));
        linkLogic.deleteLink(new DummyExecutionErrorAccumulator(), linkType, secondEntityInstance, firstEntityInstance, null);
        assertEquals(0, linkControl.deletes);
        var reverseLink = linkLogic.createLink(null, linkType, secondEntityInstance, firstEntityInstance, null);
        assertNotEquals(firstLink, reverseLink);
        assertEquals(2, linkControl.links.size());
    }

    @Test
    public void cardinalityIsPerLinkTypeAndDeletionFreesTheEndpoint() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var firstLinkType = linkType(3L, LinkCardinalities.ONE_TO_ONE.name(), LinkDirectionalities.DIRECTED.name());
        var secondLinkType = linkType(4L, LinkCardinalities.ONE_TO_ONE.name(), LinkDirectionalities.DIRECTED.name());
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        var otherTargetEntityInstance = entityInstance(12L, TARGET_ENTITY_TYPE_PK);

        linkLogic.createLink(null, firstLinkType, sourceEntityInstance, targetEntityInstance, null);
        assertNotNull(linkLogic.createLink(null, secondLinkType, sourceEntityInstance, otherTargetEntityInstance, null));
        linkLogic.deleteLink(null, firstLinkType, sourceEntityInstance, targetEntityInstance, null);
        assertNotNull(linkLogic.createLink(null, firstLinkType, sourceEntityInstance, otherTargetEntityInstance, null));
    }

    @Test
    public void locksEndpointsInStableOrderAndLocksSelfLinksOnce() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var linkType = linkType(3L, LinkCardinalities.MANY_TO_MANY.name(), LinkDirectionalities.SYMMETRIC.name());
        var firstEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var secondEntityInstance = entityInstance(11L, SOURCE_ENTITY_TYPE_PK);

        linkLogic.createLink(null, linkType, secondEntityInstance, firstEntityInstance, null);
        assertEquals(List.of(firstEntityInstance.getPrimaryKey(), secondEntityInstance.getPrimaryKey()), linkControl.locks);
        linkControl.locks.clear();
        linkLogic.createLink(null, linkType, firstEntityInstance, firstEntityInstance, null);
        assertEquals(List.of(firstEntityInstance.getPrimaryKey()), linkControl.locks);
    }

    @Test
    public void rejectsUnknownMetadataAndAsymmetricCardinalityForSymmetricLinks() {
        for(var names : List.of(List.of("UNKNOWN", "DIRECTED"), List.of("ONE_TO_ONE", "UNKNOWN"),
                List.of("ONE_TO_MANY", "SYMMETRIC"), List.of("MANY_TO_ONE", "SYMMETRIC"))) {
            var linkControl = new MemoryControl();
            var linkLogic = logic(linkControl);
            var linkType = linkType(3L, names.get(0), names.get(1));
            var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
            var targetEntityInstance = entityInstance(11L, linkType.getLastDetail().getTargetEntityTypePK());
            var errors = new DummyExecutionErrorAccumulator();

            assertNull(linkLogic.createLink(errors, linkType, sourceEntityInstance, targetEntityInstance, null));
            assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR, ExecutionErrors.InvalidLinkTypeEndpoints.name()));
            assertEquals(0, linkControl.creates);
            assertTrue(linkControl.locks.isEmpty());
        }
    }

    private static class DefinitionLogic extends LinkLogic {
        final List<String> resolutions = new ArrayList<>();
        final EntityInstance sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        final EntityInstance targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);

        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        DefinitionLogic() {
            linkTypeLogic = new LinkTypeLogic() {
                @Override
                public LinkType getLinkTypeByName(ExecutionErrorAccumulator eea, String linkTypeName) {
                    LinkType linkType = null;
                    if("MISSING".equals(linkTypeName)) {
                        handleExecutionError(UnknownLinkTypeNameException.class, eea, ExecutionErrors.UnknownLinkTypeName.name(), linkTypeName);
                    } else {
                        linkType = linkType();
                    }
                    return linkType;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstanceByEntityRef(ExecutionErrorAccumulator eea, String entityRef) {
                    resolutions.add(entityRef);
                    EntityInstance entityInstance = switch(entityRef) {
                        case "ECHO_THREE.Item.10" -> sourceEntityInstance;
                        case "ECHO_THREE.Party.11" -> targetEntityInstance;
                        default -> null;
                    };
                    if(entityInstance == null) {
                        handleExecutionError(UnknownEntityRefException.class, eea, ExecutionErrors.UnknownEntityRef.name(), entityRef);
                    }
                    return entityInstance;
                }

                @Override
                public EntityInstance getEntityInstanceByUuid(ExecutionErrorAccumulator eea, String uuid) {
                    resolutions.add(uuid);
                    EntityInstance entityInstance = switch(uuid) {
                        case "00000000-0000-0000-0000-000000000010" -> sourceEntityInstance;
                        case "00000000-0000-0000-0000-000000000011" -> targetEntityInstance;
                        default -> null;
                    };
                    if(entityInstance == null) {
                        handleExecutionError(UnknownUuidException.class, eea, ExecutionErrors.UnknownUuid.name(), uuid);
                    }
                    return entityInstance;
                }
            };
        }
    }

    @Test
    public void commandDefinitionsAcceptAllEntityRefAndUuidCombinations() {
        for(var sourceUsesUuid : List.of(false, true)) {
            for(var targetUsesUuid : List.of(false, true)) {
                var logic = new DefinitionLogic();
                var form = CoreFormFactory.getCreateLinkForm();
                form.setLinkTypeName("TEST");
                if(sourceUsesUuid) {
                    form.setSourceUuid("00000000-0000-0000-0000-000000000010");
                } else {
                    form.setSourceEntityRef("ECHO_THREE.Item.10");
                }
                if(targetUsesUuid) {
                    form.setTargetUuid("00000000-0000-0000-0000-000000000011");
                } else {
                    form.setTargetEntityRef("ECHO_THREE.Party.11");
                }
                var errors = new DummyExecutionErrorAccumulator();
                var definition = logic.getDefinition(errors, form, false);
                assertFalse(errors.hasExecutionErrors());
                assertSame(logic.sourceEntityInstance, definition.sourceEntityInstance());
                assertSame(logic.targetEntityInstance, definition.targetEntityInstance());
                assertEquals(2, logic.resolutions.size());
            }
        }
    }

    @Test
    public void commandDefinitionsRequireExactlyOneIdentifierForEachEndpoint() {
        for(var sourceInvalid : List.of(false, true)) {
            for(var bothIdentifiers : List.of(false, true)) {
                var logic = new DefinitionLogic();
                var form = CoreFormFactory.getGetLinkForm();
                form.setLinkTypeName("TEST");
                if(sourceInvalid) {
                    form.setTargetEntityRef("ECHO_THREE.Party.11");
                    if(bothIdentifiers) {
                        form.setSourceEntityRef("ECHO_THREE.Item.10");
                        form.setSourceUuid("00000000-0000-0000-0000-000000000010");
                    }
                } else {
                    form.setSourceEntityRef("ECHO_THREE.Item.10");
                    if(bothIdentifiers) {
                        form.setTargetEntityRef("ECHO_THREE.Party.11");
                        form.setTargetUuid("00000000-0000-0000-0000-000000000011");
                    }
                }
                var errors = new DummyExecutionErrorAccumulator();
                assertNull(logic.getDefinition(errors, form, false));
                assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR, ExecutionErrors.InvalidParameterCount.name()));
            }
        }
    }

    @Test
    public void listDefinitionsSupportEveryCombinationOfOptionalFilters() {
        for(var includeType : List.of(false, true)) {
            for(var includeSource : List.of(false, true)) {
                for(var includeTarget : List.of(false, true)) {
                    var logic = new DefinitionLogic();
                    var form = CoreFormFactory.getGetLinksForm();
                    if(includeType) form.setLinkTypeName("TEST");
                    if(includeSource) form.setSourceUuid("00000000-0000-0000-0000-000000000010");
                    if(includeTarget) form.setTargetEntityRef("ECHO_THREE.Party.11");
                    var errors = new DummyExecutionErrorAccumulator();
                    var definition = logic.getDefinition(errors, form, true);
                    assertFalse(errors.hasExecutionErrors());
                    assertEquals(includeType, definition.linkType() != null);
                    assertEquals(includeSource, definition.sourceEntityInstance() != null);
                    assertEquals(includeTarget, definition.targetEntityInstance() != null);
                }
            }
        }
    }

    @Test
    public void unknownCommandIdentifiersStopDependentResolution() {
        var logic = new DefinitionLogic();
        var form = CoreFormFactory.getDeleteLinkForm();
        form.setLinkTypeName("MISSING");
        form.setSourceEntityRef("ECHO_THREE.Item.10");
        form.setTargetEntityRef("ECHO_THREE.Party.11");
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getDefinition(errors, form, false));
        assertTrue(logic.resolutions.isEmpty());
        form.setLinkTypeName("TEST");
        form.setSourceEntityRef("MISSING");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getDefinition(errors, form, false));
        assertEquals(List.of("MISSING"), logic.resolutions);
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR, ExecutionErrors.UnknownEntityRef.name()));
    }

    @Test
    public void listDefinitionsRejectAmbiguousEndpointIdentifiers() {
        for(var source : List.of(false, true)) {
            var form = CoreFormFactory.getGetLinksForm();
            if(source) {
                form.setSourceEntityRef("ECHO_THREE.Item.10");
                form.setSourceUuid("00000000-0000-0000-0000-000000000010");
            } else {
                form.setTargetEntityRef("ECHO_THREE.Party.11");
                form.setTargetUuid("00000000-0000-0000-0000-000000000011");
            }
            var errors = new DummyExecutionErrorAccumulator();
            assertNull(new DefinitionLogic().getDefinition(errors, form, true));
            assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR, ExecutionErrors.InvalidParameterCount.name()));
        }
    }

}
