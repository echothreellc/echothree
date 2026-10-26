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

package com.echothree.control.user.core.server.command;

import com.echothree.model.data.core.common.pk.LinkDirectionalityPK;

import com.echothree.model.data.core.common.pk.LinkCardinalityPK;

import com.echothree.model.data.core.server.value.LinkDirectionalityValue;

import com.echothree.model.data.core.server.value.LinkCardinalityValue;

import com.echothree.model.data.core.server.value.EntityTypeValue;

import com.echothree.model.data.core.server.entity.LinkDirectionality;

import com.echothree.model.data.core.server.entity.LinkCardinality;

import com.echothree.model.data.core.server.entity.EntityType;

import com.echothree.model.control.core.server.control.LinkDirectionalityControl;

import com.echothree.model.control.core.server.control.LinkCardinalityControl;

import com.echothree.model.control.core.server.control.EntityTypeControl;

import com.echothree.model.control.core.common.transfer.LinkDirectionalityTransfer;

import com.echothree.model.control.core.common.transfer.LinkCardinalityTransfer;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;

import com.echothree.control.user.core.common.result.GetLinkTypesResult;

import com.echothree.control.user.core.common.result.GetLinksResult;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.LinkTypeTransfer;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.value.EntityInstanceValue;
import com.echothree.model.data.core.server.value.LinkTypeValue;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
import java.util.Collection;
import org.junit.jupiter.api.parallel.ResourceLock;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.result.CreateLinkResult;
import com.echothree.control.user.core.common.spec.LinkSpec;
import com.echothree.model.control.core.common.transfer.LinkTransfer;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.control.core.server.logic.LinkLogic;
import com.echothree.model.data.core.common.pk.LinkPK;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.value.LinkValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.control.user.core.common.form.CreateLinkForm;
import com.echothree.control.user.core.common.form.DeleteLinkForm;
import com.echothree.control.user.core.common.form.GetLinkForm;
import com.echothree.control.user.core.common.form.GetLinksForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@ResourceLock("ValueCacheProvider")
public class LinkCommandTest {

    @Test
    public void commandsRequireTheirOwnLinkSecurityRole() throws ReflectiveOperationException {
        for(var entry : Map.of(CreateLinkCommand.class, "Create", GetLinkCommand.class, "Review",
                GetLinksCommand.class, "List", DeleteLinkCommand.class, "Delete").entrySet()) {
            var field = entry.getKey().getDeclaredField("COMMAND_SECURITY_DEFINITION");
            field.setAccessible(true);
            var security = (CommandSecurityDefinition)field.get(null);
            var employee = security.getPartyTypeDefinitions().stream()
                    .filter(definition -> PartyTypes.EMPLOYEE.name().equals(definition.getPartyTypeName())).findFirst().orElseThrow();
            assertEquals(1, employee.getSecurityRoleDefinitions().size());
            assertEquals(SecurityRoleGroups.Link.name(), employee.getSecurityRoleDefinitions().getFirst().getSecurityRoleGroupName());
            assertEquals(entry.getValue(), employee.getSecurityRoleDefinitions().getFirst().getSecurityRoleName());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void endpointFieldsAllowAlternativeIdentifiersAndListsAllowOptionalType() throws ReflectiveOperationException {
        for(var command : List.of(CreateLinkCommand.class, GetLinkCommand.class, GetLinksCommand.class, DeleteLinkCommand.class)) {
            var field = command.getDeclaredField("FORM_FIELD_DEFINITIONS");
            field.setAccessible(true);
            var definitions = ((List<FieldDefinition>)field.get(null)).stream()
                    .collect(Collectors.toMap(FieldDefinition::getFieldName, definition -> definition));
            assertEquals(5, definitions.size());
            assertEquals(command != GetLinksCommand.class, definitions.get("LinkTypeName").getIsRequired());
            for(var endpoint : List.of("Source", "Target")) {
                assertEquals(FieldType.ENTITY_REF, definitions.get(endpoint + "EntityRef").getFieldType());
                assertEquals(FieldType.UUID, definitions.get(endpoint + "Uuid").getFieldType());
                assertFalse(definitions.get(endpoint + "EntityRef").getIsRequired());
                assertFalse(definitions.get(endpoint + "Uuid").getIsRequired());
            }
        }
        assertTrue(BaseSingleEntityCommand.class.isAssignableFrom(GetLinkCommand.class));
        assertTrue(BasePaginatedMultipleEntitiesCommand.class.isAssignableFrom(GetLinksCommand.class));
    }

    @Test
    public void serviceExposesAllFourCommands() throws ReflectiveOperationException {
        assertNotNull(CoreService.class.getMethod("createLink", UserVisitPK.class, CreateLinkForm.class));
        assertNotNull(CoreService.class.getMethod("getLink", UserVisitPK.class, GetLinkForm.class));
        assertNotNull(CoreService.class.getMethod("getLinks", UserVisitPK.class, GetLinksForm.class));
        assertNotNull(CoreService.class.getMethod("deleteLink", UserVisitPK.class, DeleteLinkForm.class));
    }

    @Test
    public void initialSecurityDefinesAndGrantsAllFourRoles() throws Exception {
        var projectDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath();
        while(projectDirectory != null && !projectDirectory.resolve("ui/cli/dataloader/src/xml/InitialSecurity.xml").toFile().isFile()) {
            projectDirectory = projectDirectory.getParent();
        }
        assertNotNull(projectDirectory);
        var document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(projectDirectory.resolve("ui/cli/dataloader/src/xml/InitialSecurity.xml").toFile());
        var xpath = XPathFactory.newInstance().newXPath();
        var group = "//securityRoleGroup[@securityRoleGroupName='Link']";
        assertEquals("CoreComponent", xpath.evaluate(group + "/@parentSecurityRoleGroupName", document));
        assertEquals("4", xpath.evaluate("count(" + group + "/securityRole)", document));
        for(var role : List.of("Create", "Review", "List", "Delete")) {
            assertEquals("1", xpath.evaluate("count(" + group + "/securityRole[@securityRoleName='" + role + "'])", document));
            assertEquals("1", xpath.evaluate("count(//partySecurityRoleTemplateRole[@securityRoleGroupName='Link'][@securityRoleName='" + role + "'])", document));
        }
    }

    private static class TestCreateCommand extends CreateLinkCommand {
        int userVisitLookups;

        TestCreateCommand() {
            form = CoreFormFactory.getCreateLinkForm();
        }

        @Override
        public PartyPK getPartyPK() {
            return null;
        }

        @Override
        public UserVisit getUserVisit() {
            userVisitLookups++;
            return null;
        }
    }

    @Test
    @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
    public void createPopulatesReturnedLinkEvenWhenLogicReportsAnError() {
        var command = new TestCreateCommand();
        var linkTransfer = new LinkTransfer(null, null, null);
        var link = new Link(new LinkValue(new LinkPK(1L), new LinkTypePK(2L), new EntityInstancePK(3L),
                new EntityInstancePK(4L), 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
        command.linkLogic = new LinkLogic() {
            @Override
            public Definition getDefinition(ExecutionErrorAccumulator eea, LinkSpec spec, boolean allowPartial) {
                assertFalse(allowPartial);
                return new Definition(null, null, null);
            }

            @Override
            public Link createLink(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
                    EntityInstance targetEntityInstance, BasePK createdBy) {
                eea.addExecutionError(ExecutionErrors.DuplicateLink.name());
                return link;
            }
        };
        command.linkControl = new LinkControl() {
            @Override
            public LinkTransfer getLinkTransfer(UserVisit userVisit, Link returnedLink) {
                assertSame(link, returnedLink);
                return linkTransfer;
            }
        };

        var result = (CreateLinkResult)command.execute();
        assertTrue(command.hasExecutionErrors());
        assertSame(linkTransfer, result.getLink());
        assertEquals(1, command.userVisitLookups);
    }

    @Test
    @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
    public void createDoesNotPersistWhenEndpointResolutionFails() {
        var command = new TestCreateCommand();
        command.linkLogic = new LinkLogic() {
            @Override
            public Definition getDefinition(ExecutionErrorAccumulator eea, LinkSpec spec, boolean allowPartial) {
                eea.addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                return null;
            }

            @Override
            public Link createLink(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
                    EntityInstance targetEntityInstance, BasePK createdBy) {
                fail("Creation must not run after failed endpoint resolution");
                return null;
            }
        };
        var result = (CreateLinkResult)command.execute();
        assertTrue(command.hasExecutionErrors());
        assertNull(result.getLink());
        assertEquals(0, command.userVisitLookups);
    }

    private static class TestGetCommand extends GetLinkCommand {
        TestGetCommand() {
            form = CoreFormFactory.getGetLinkForm();
        }

        @Override
        public PartyPK getPartyPK() {
            throw new AssertionError("A Description-style Link lookup must not send an EntityInstance READ event");
        }
    }

    @Test
    @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
    public void getReturnsLinkWithoutRequiringItsOwnEntityInstance() {
        var command = new TestGetCommand();
        var link = new Link(new LinkValue(new LinkPK(1L), new LinkTypePK(2L), new EntityInstancePK(3L),
                new EntityInstancePK(4L), 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
        command.linkLogic = new LinkLogic() {
            @Override
            public Definition getDefinition(ExecutionErrorAccumulator eea, LinkSpec spec, boolean allowPartial) {
                assertFalse(allowPartial);
                return new Definition(null, null, null);
            }

            @Override
            public Link getLink(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
                    EntityInstance targetEntityInstance) {
                return link;
            }
        };

        assertSame(link, command.getEntity());
        assertFalse(command.hasExecutionErrors());
    }

    private static class TestListCommand extends GetLinksCommand {
        Session testSession() {
            return session;
        }

        int userVisitLookups;

        TestListCommand() throws ReflectiveOperationException {
            var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
            var field = holder.getDeclaredField("instance");
            field.setAccessible(true);
            var previous = field.get(null);
            try {
                field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
                session = new Session();
            } finally {
                field.set(null, previous);
            }
            form = CoreFormFactory.getGetLinksForm();
        }

        @Override
        public UserVisit getUserVisit() {
            userVisitLookups++;
            return null;
        }
    }

    @Test
    @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
    public void listsReturnTransfersForEachSpecifiedFilterEvenWhenNoLinksMatch() throws ReflectiveOperationException {
        var linkType = new LinkType(new LinkTypeValue(new LinkTypePK(1L), null, null), EntityPermission.READ_ONLY);
        var source = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(2L), new EntityTypePK(1L), 2L, null), EntityPermission.READ_ONLY);
        var target = new EntityInstance(new EntityInstanceValue(new EntityInstancePK(3L), new EntityTypePK(1L), 3L, null), EntityPermission.READ_ONLY);
        var linkTypeTransfer = new LinkTypeTransfer("TEST", null, null, null, null, null, false, null, 0, null, null, null);
        var sourceTransfer = new EntityInstanceTransfer(null, 2L, null, null, null, null);
        var targetTransfer = new EntityInstanceTransfer(null, 3L, null, null, null, null);

        for(var includeType : List.of(false, true)) {
            for(var includeSource : List.of(false, true)) {
                for(var includeTarget : List.of(false, true)) {
                    var command = new TestListCommand();
                    command.linkLogic = new LinkLogic() {
                        @Override
                        public Definition getDefinition(ExecutionErrorAccumulator eea, LinkSpec spec, boolean allowPartial) {
                            assertTrue(allowPartial);
                            return new Definition(includeType ? linkType : null, includeSource ? source : null, includeTarget ? target : null);
                        }
                    };
                    command.linkTypeControl = new LinkTypeControl() {
                        @Override
                        public LinkTypeTransfer getLinkTypeTransfer(UserVisit userVisit, LinkType entity) {
                            assertTrue(includeType);
                            assertSame(linkType, entity);
                            return linkTypeTransfer;
                        }
                    };
                    command.entityInstanceControl = new EntityInstanceControl() {
                        @Override
                        public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, EntityInstance entity,
                                boolean includeAppearance, boolean includeVisit, boolean includeNames, boolean includeUuid) {
                            assertTrue(entity.equals(source) ? includeSource : includeTarget);
                            return entity.equals(source) ? sourceTransfer : targetTransfer;
                        }
                    };
                    command.linkControl = new LinkControl() {
                        @Override
                        public List<LinkTransfer> getLinkTransfers(UserVisit userVisit, Collection<Link> links) {
                            assertTrue(links.isEmpty());
                            return List.of();
                        }
                    };
                    command.handleForm();
                    var result = (GetLinksResult)command.getResult(List.of());
                    assertSame(includeType ? linkTypeTransfer : null, result.getLinkType());
                    assertSame(includeSource ? sourceTransfer : null, result.getSourceEntityInstance());
                    assertSame(includeTarget ? targetTransfer : null, result.getTargetEntityInstance());
                    assertTrue(result.getLinks().isEmpty());
                    assertEquals(1, command.userVisitLookups);
                }
            }
        }
    }

    @Test
    public void failedListsDoNotPopulateFilterTransfers() throws ReflectiveOperationException {
        var command = new TestListCommand();
        var result = (GetLinksResult)command.getResult(null);
        assertNull(result.getLinkType());
        assertNull(result.getSourceEntityInstance());
        assertNull(result.getTargetEntityInstance());
        assertNull(result.getLinks());
        assertEquals(0, command.userVisitLookups);
    }

    private static class TestLinkTypesCommand extends GetLinkTypesCommand {
        int userVisitLookups;

        TestLinkTypesCommand(Session testSession) {
            session = testSession;
        }

        @Override
        public UserVisit getUserVisit() {
            userVisitLookups++;
            return null;
        }

        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        void useEntityTypeControl(EntityTypeControl control) {
            entityTypeControl = control;
        }
    }

    @Test
    @SuppressWarnings("UnnecessaryAssignment")
    public void linkTypeListsReturnSpecifiedFilterTransfersEvenWhenEmpty() throws ReflectiveOperationException {
        var testSession = new TestListCommand().testSession();
        var linkType = new LinkType(new LinkTypeValue(new LinkTypePK(1L), null, null), EntityPermission.READ_ONLY);
        var entityType = new EntityType(
                new EntityTypeValue(new EntityTypePK(1L), null, null), EntityPermission.READ_ONLY);
        var cardinality = new LinkCardinality(
                new LinkCardinalityValue(new LinkCardinalityPK(1L), null, null), EntityPermission.READ_ONLY);
        var directionality = new LinkDirectionality(
                new LinkDirectionalityValue(new LinkDirectionalityPK(1L), null, null), EntityPermission.READ_ONLY);
        var typeTransfer = new LinkTypeTransfer("TEST", null, null, null, null, null, false, null, 0, null, null, null);
        var entityTransfer = new EntityTypeTransfer();
        var cardinalityTransfer = new LinkCardinalityTransfer("TEST", false, 0, null);
        var directionalityTransfer = new LinkDirectionalityTransfer("TEST", false, 0, null);
        var names = List.of("parentLinkType", "sourceEntityType", "targetEntityType", "linkCardinality", "linkDirectionality", "inverseLinkType");
        var entities = List.of(linkType, entityType, entityType, cardinality, directionality, linkType);
        var transfers = List.of(typeTransfer, entityTransfer, entityTransfer, cardinalityTransfer, directionalityTransfer, typeTransfer);

        for(int filters = 0; filters < 64; filters++) {
            var command = new TestLinkTypesCommand(testSession);
            command.linkTypeControl = new LinkTypeControl() {
                @Override
                public LinkTypeTransfer getLinkTypeTransfer(UserVisit userVisit, LinkType entity) {
                    assertSame(linkType, entity);
                    return typeTransfer;
                }

                @Override
                public List<LinkTypeTransfer> getLinkTypeTransfers(UserVisit userVisit, Collection<LinkType> returnedEntities) {
                    assertTrue(returnedEntities.isEmpty());
                    return List.of();
                }
            };
            command.useEntityTypeControl(new EntityTypeControl() {
                @Override
                public EntityTypeTransfer getEntityTypeTransfer(UserVisit userVisit,
                        EntityType entity) {
                    assertSame(entityType, entity);
                    return entityTransfer;
                }
            });
            command.linkCardinalityControl = new LinkCardinalityControl() {
                @Override
                public LinkCardinalityTransfer getLinkCardinalityTransfer(UserVisit userVisit,
                        LinkCardinality entity) {
                    assertSame(cardinality, entity);
                    return cardinalityTransfer;
                }
            };
            command.linkDirectionalityControl = new LinkDirectionalityControl() {
                @Override
                public LinkDirectionalityTransfer getLinkDirectionalityTransfer(UserVisit userVisit,
                        LinkDirectionality entity) {
                    assertSame(directionality, entity);
                    return directionalityTransfer;
                }
            };
            for(int index = 0; index < names.size(); index++) {
                var field = GetLinkTypesCommand.class.getDeclaredField(names.get(index));
                field.setAccessible(true);
                field.set(command, (filters & (1 << index)) == 0 ? null : entities.get(index));
            }
            var result = (GetLinkTypesResult)command.getResult(List.of());
            var actualTransfers = new Object[]{result.getParentLinkType(), result.getSourceEntityType(), result.getTargetEntityType(),
                    result.getLinkCardinality(), result.getLinkDirectionality(), result.getInverseLinkType()};
            for(int index = 0; index < names.size(); index++) {
                assertSame((filters & (1 << index)) == 0 ? null : transfers.get(index), actualTransfers[index], names.get(index));
            }
            assertTrue(result.getLinkTypes().isEmpty());
            assertEquals(1, command.userVisitLookups);
        }
    }

}
