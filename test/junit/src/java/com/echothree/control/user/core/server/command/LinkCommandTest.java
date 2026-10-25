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

}
