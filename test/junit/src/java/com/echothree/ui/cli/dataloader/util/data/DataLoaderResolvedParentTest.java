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

package com.echothree.ui.cli.dataloader.util.data;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.index.common.IndexUtil;
import com.echothree.control.user.index.common.form.IndexFormFactory;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.SecurityFormFactory;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.SequenceFormFactory;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.client.test.UserVisitTestCase;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.CommandResult;
import java.awt.image.BufferedImage;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.xml.parsers.SAXParserFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
class DataLoaderResolvedParentTest
        extends UserVisitTestCase {

    @Test
    void loadsWorkflowDescriptionsUnderAnAlternateParentKey() throws Exception {
        var service = WorkflowUtil.getHome();
        var workflowName = newTestName();
        var createForm = WorkflowFormFactory.getCreateWorkflowForm();
        createForm.setWorkflowName(workflowName);
        createForm.setSortOrder("7");
        var workflow = requireSuccess(service.createWorkflow(userVisitPK, createForm));

        try {
            loadXml("""
                    <initialData>
                        <workflows>
                            <workflow entityRef="%s" sortOrder="8">
                                <workflowDescription languageIsoName="en" description="Loaded by entity reference"/>
                            </workflow>
                        </workflows>
                    </initialData>
                    """.formatted(workflow.getEntityRef()));

            var getDescriptionForm = WorkflowFormFactory.getGetWorkflowDescriptionForm();
            getDescriptionForm.setWorkflowName(workflowName);
            getDescriptionForm.setLanguageIsoName("en");
            var description = requireSuccess(service.getWorkflowDescription(userVisitPK, getDescriptionForm));
            assertEquals("Loaded by entity reference", description.getWorkflowDescription().getDescription());

            loadXml("""
                    <initialData>
                        <workflows>
                            <workflow uuid="%s" commandAction="none" sortOrder="9">
                                <workflowDescription languageIsoName="en" description="Loaded by UUID"/>
                            </workflow>
                        </workflows>
                    </initialData>
                    """.formatted(uuidOf(workflow.getEntityRef())));

            description = requireSuccess(service.getWorkflowDescription(userVisitPK, getDescriptionForm));
            assertEquals("Loaded by UUID", description.getWorkflowDescription().getDescription());
            var getWorkflowForm = WorkflowFormFactory.getGetWorkflowForm();
            getWorkflowForm.setWorkflowName(workflowName);
            assertEquals(Integer.valueOf(8), requireSuccess(service.getWorkflow(userVisitPK, getWorkflowForm)).getWorkflow().getSortOrder());
        } finally {
            var deleteForm = WorkflowFormFactory.getDeleteWorkflowForm();
            deleteForm.setWorkflowName(workflowName);
            assertNoErrors(service.deleteWorkflow(userVisitPK, deleteForm));
        }
    }

    @Test
    void reconcilesWorkflowStepsAndSkipsChildrenOnRejectedUpdate() throws Exception {
        var service = WorkflowUtil.getHome();
        var workflowName = newTestName();
        var createForm = WorkflowFormFactory.getCreateWorkflowForm();
        createForm.setWorkflowName(workflowName);
        createForm.setSortOrder("7");
        requireSuccess(service.createWorkflow(userVisitPK, createForm));

        try {
            var stepXml = """
                    <initialData>
                        <workflows>
                            <workflow workflowName="%s" commandAction="none">
                                <workflowStep workflowStepName="REVIEW" %s>
                                    <workflowStepDescription languageIsoName="en" description="%s"/>
                                </workflowStep>
                            </workflow>
                        </workflows>
                    </initialData>
                    """;
            loadXml(stepXml.formatted(workflowName,
                    "workflowStepTypeName=\"MANUAL_TRANSITION\" isDefault=\"true\" sortOrder=\"1\"", "Created with step"));

            var getDescriptionForm = WorkflowFormFactory.getGetWorkflowStepDescriptionForm();
            getDescriptionForm.setWorkflowName(workflowName);
            getDescriptionForm.setWorkflowStepName("REVIEW");
            getDescriptionForm.setLanguageIsoName("en");
            assertEquals("Created with step",
                    requireSuccess(service.getWorkflowStepDescription(userVisitPK, getDescriptionForm)).getWorkflowStepDescription().getDescription());

            loadXml(stepXml.formatted(workflowName, "sortOrder=\"2\"", "Updated with step"));
            var getStepForm = WorkflowFormFactory.getGetWorkflowStepForm();
            getStepForm.setWorkflowName(workflowName);
            getStepForm.setWorkflowStepName("REVIEW");
            var step = requireSuccess(service.getWorkflowStep(userVisitPK, getStepForm)).getWorkflowStep();
            assertEquals(Integer.valueOf(2), step.getSortOrder());
            assertTrue(step.getIsDefault());
            assertEquals("MANUAL_TRANSITION", step.getWorkflowStepType().getWorkflowStepTypeName());

            loadXml(stepXml.formatted(workflowName, "sortOrder=\"not-a-number\"", "Must not be loaded"));
            assertEquals("Updated with step",
                    requireSuccess(service.getWorkflowStepDescription(userVisitPK, getDescriptionForm)).getWorkflowStepDescription().getDescription());
            assertEquals(Integer.valueOf(2),
                    requireSuccess(service.getWorkflowStep(userVisitPK, getStepForm)).getWorkflowStep().getSortOrder());
        } finally {
            var deleteForm = WorkflowFormFactory.getDeleteWorkflowForm();
            deleteForm.setWorkflowName(workflowName);
            assertNoErrors(service.deleteWorkflow(userVisitPK, deleteForm));
        }
    }

    @Test
    void reconcilesWorkflowDestinationsAndEntrancesAndSkipsRejectedChildren() throws Exception {
        var service = WorkflowUtil.getHome();
        var workflowName = newTestName();
        var createForm = WorkflowFormFactory.getCreateWorkflowForm();
        createForm.setWorkflowName(workflowName);
        createForm.setSortOrder("7");
        requireSuccess(service.createWorkflow(userVisitPK, createForm));

        try {
            var initialXml = """
                    <initialData>
                        <workflows>
                            <workflow workflowName="%s" commandAction="none">
                                <workflowStep workflowStepName="REVIEW" workflowStepTypeName="MANUAL_TRANSITION" isDefault="true" sortOrder="1"/>
                                <workflowStep workflowStepName="APPROVED" workflowStepTypeName="MANUAL_TRANSITION" isDefault="false" sortOrder="2"/>
                                <workflowDestination workflowStepName="REVIEW" workflowDestinationName="NEXT" isDefault="true" sortOrder="1">
                                    <workflowDestinationDescription languageIsoName="en" description="From review"/>
                                </workflowDestination>
                                <workflowDestination workflowStepName="APPROVED" workflowDestinationName="NEXT" isDefault="false" sortOrder="2">
                                    <workflowDestinationDescription languageIsoName="en" description="From approved"/>
                                </workflowDestination>
                                <workflowEntrance workflowEntranceName="START" isDefault="true" sortOrder="1">
                                    <workflowEntranceDescription languageIsoName="en" description="Start here"/>
                                </workflowEntrance>
                            </workflow>
                        </workflows>
                    </initialData>
                    """.formatted(workflowName);
            loadXml(initialXml);
            loadXml(initialXml);

            var getDestinationForm = WorkflowFormFactory.getGetWorkflowDestinationForm();
            getDestinationForm.setWorkflowName(workflowName);
            getDestinationForm.setWorkflowStepName("REVIEW");
            getDestinationForm.setWorkflowDestinationName("NEXT");
            var getDestinationDescriptionForm = WorkflowFormFactory.getGetWorkflowDestinationDescriptionForm();
            getDestinationDescriptionForm.set(getDestinationForm.get());
            getDestinationDescriptionForm.setLanguageIsoName("en");
            assertEquals("From review", requireSuccess(service.getWorkflowDestinationDescription(userVisitPK, getDestinationDescriptionForm))
                    .getWorkflowDestinationDescription().getDescription());

            var getEntranceForm = WorkflowFormFactory.getGetWorkflowEntranceForm();
            getEntranceForm.setWorkflowName(workflowName);
            getEntranceForm.setWorkflowEntranceName("START");
            var getEntranceDescriptionForm = WorkflowFormFactory.getGetWorkflowEntranceDescriptionForm();
            getEntranceDescriptionForm.set(getEntranceForm.get());
            getEntranceDescriptionForm.setLanguageIsoName("en");
            assertEquals("Start here", requireSuccess(service.getWorkflowEntranceDescription(userVisitPK, getEntranceDescriptionForm))
                    .getWorkflowEntranceDescription().getDescription());

            var updateXml = """
                    <initialData>
                        <workflows>
                            <workflow workflowName="%s" commandAction="none">
                                <workflowDestination workflowStepName="REVIEW" workflowDestinationName="NEXT" sortOrder="%s">
                                    <workflowDestinationDescription languageIsoName="en" description="%s"/>
                                </workflowDestination>
                                <workflowEntrance workflowEntranceName="START" %s>
                                    <workflowEntranceDescription languageIsoName="en" %s/>
                                </workflowEntrance>
                            </workflow>
                        </workflows>
                    </initialData>
                    """;
            loadXml(updateXml.formatted(workflowName, "3", "Updated review destination",
                    "sortOrder=\"4\"", "description=\"Updated entrance\""));
            var destination = requireSuccess(service.getWorkflowDestination(userVisitPK, getDestinationForm)).getWorkflowDestination();
            assertEquals(Integer.valueOf(3), destination.getSortOrder());
            assertTrue(destination.getIsDefault());
            var entrance = requireSuccess(service.getWorkflowEntrance(userVisitPK, getEntranceForm)).getWorkflowEntrance();
            assertEquals(Integer.valueOf(4), entrance.getSortOrder());
            assertTrue(entrance.getIsDefault());
            assertEquals("Updated entrance", requireSuccess(service.getWorkflowEntranceDescription(userVisitPK, getEntranceDescriptionForm))
                    .getWorkflowEntranceDescription().getDescription());

            loadXml(updateXml.formatted(workflowName, "not-a-number", "Must not be loaded", "sortOrder=\"5\"", ""));
            assertEquals(Integer.valueOf(3),
                    requireSuccess(service.getWorkflowDestination(userVisitPK, getDestinationForm)).getWorkflowDestination().getSortOrder());
            assertEquals("Updated review destination",
                    requireSuccess(service.getWorkflowDestinationDescription(userVisitPK, getDestinationDescriptionForm))
                            .getWorkflowDestinationDescription().getDescription());
            getDestinationDescriptionForm.setWorkflowStepName("APPROVED");
            assertEquals("From approved", requireSuccess(service.getWorkflowDestinationDescription(userVisitPK, getDestinationDescriptionForm))
                    .getWorkflowDestinationDescription().getDescription());
            assertEquals(Integer.valueOf(5),
                    requireSuccess(service.getWorkflowEntrance(userVisitPK, getEntranceForm)).getWorkflowEntrance().getSortOrder());
            assertEquals("Updated entrance", requireSuccess(service.getWorkflowEntranceDescription(userVisitPK, getEntranceDescriptionForm))
                    .getWorkflowEntranceDescription().getDescription());

            loadXml(updateXml.formatted(workflowName, "3", "Updated review destination",
                    "commandAction=\"none\" sortOrder=\"6\"", "description=\"Updated through a read-only entrance\""));
            assertEquals(Integer.valueOf(5),
                    requireSuccess(service.getWorkflowEntrance(userVisitPK, getEntranceForm)).getWorkflowEntrance().getSortOrder());
            assertEquals("Updated through a read-only entrance",
                    requireSuccess(service.getWorkflowEntranceDescription(userVisitPK, getEntranceDescriptionForm))
                            .getWorkflowEntranceDescription().getDescription());
        } finally {
            var deleteForm = WorkflowFormFactory.getDeleteWorkflowForm();
            deleteForm.setWorkflowName(workflowName);
            assertNoErrors(service.deleteWorkflow(userVisitPK, deleteForm));
        }
    }

    @Test
    void loadsItemAliasTypeDescriptionUnderAnUnchangedParentByUuid() throws Exception {
        var service = ItemUtil.getHome();
        var itemAliasTypeName = newTestName();
        var createForm = ItemFormFactory.getCreateItemAliasTypeForm();
        createForm.setItemAliasTypeName(itemAliasTypeName);
        createForm.setItemAliasChecksumTypeName("NONE");
        createForm.setAllowMultiple("false");
        createForm.setIsDefault("false");
        createForm.setSortOrder("7");
        var aliasType = requireSuccess(service.createItemAliasType(userVisitPK, createForm));

        try {
            var descriptionXml = """
                    <initialData>
                        <itemAliasTypes>
                            <itemAliasType uuid="%s">
                                <itemAliasTypeDescription languageIsoName="en" %s/>
                            </itemAliasType>
                        </itemAliasTypes>
                    </initialData>
                    """;
            var uuid = uuidOf(aliasType.getEntityRef());
            loadXml(descriptionXml.formatted(uuid, "description=\"Alias type loaded by UUID\""));

            var getDescriptionForm = ItemFormFactory.getGetItemAliasTypeDescriptionForm();
            getDescriptionForm.setItemAliasTypeName(itemAliasTypeName);
            getDescriptionForm.setLanguageIsoName("en");
            var description = requireSuccess(service.getItemAliasTypeDescription(userVisitPK, getDescriptionForm));
            assertEquals("Alias type loaded by UUID", description.getItemAliasTypeDescription().getDescription());

            loadXml(descriptionXml.formatted(uuid, "description=\"Alias type updated by UUID\""));
            loadXml(descriptionXml.formatted(uuid, ""));
            description = requireSuccess(service.getItemAliasTypeDescription(userVisitPK, getDescriptionForm));
            assertEquals("Alias type updated by UUID", description.getItemAliasTypeDescription().getDescription());
        } finally {
            var deleteForm = ItemFormFactory.getDeleteItemAliasTypeForm();
            deleteForm.setItemAliasTypeName(itemAliasTypeName);
            assertNoErrors(service.deleteItemAliasType(userVisitPK, deleteForm));
        }
    }

    @Test
    void loadsCoreMetadataAndUpdatesDescriptionsOnRerun() throws Exception {
        var service = CoreUtil.getHome();
        var componentVendorName = newTestName();
        var xml = """
                <initialData>
                    <componentVendors>
                        <componentVendor componentVendorName="%s" description="%s">
                            <entityType entityTypeName="LoaderFixture" %s>
                                <entityTypeDescription languageIsoName="en" description="%s"/>
                            </entityType>
                        </componentVendor>
                    </componentVendors>
                </initialData>
                """;

        try {
            loadXml(xml.formatted(componentVendorName, "Loader test vendor",
                    "keepAllHistory=\"false\" isExtensible=\"false\" sortOrder=\"1\"", "Initial entity description"));
            loadXml(xml.formatted(componentVendorName, "Updated vendor",
                    "sortOrder=\"2\"", "Updated entity description"));

            var getVendorForm = CoreFormFactory.getGetComponentVendorForm();
            getVendorForm.setComponentVendorName(componentVendorName);
            assertEquals("Updated vendor", requireSuccess(service.getComponentVendor(userVisitPK, getVendorForm))
                    .getComponentVendor().getDescription());
            var getEntityTypeForm = CoreFormFactory.getGetEntityTypeForm();
            getEntityTypeForm.setComponentVendorName(componentVendorName);
            getEntityTypeForm.setEntityTypeName("LoaderFixture");
            assertEquals(Integer.valueOf(2), requireSuccess(service.getEntityType(userVisitPK, getEntityTypeForm))
                    .getEntityType().getSortOrder());
            var getDescriptionForm = CoreFormFactory.getGetEntityTypeDescriptionForm();
            getDescriptionForm.set(getEntityTypeForm.get());
            getDescriptionForm.setLanguageIsoName("en");
            assertEquals("Updated entity description", requireSuccess(service.getEntityTypeDescription(userVisitPK, getDescriptionForm))
                    .getEntityTypeDescription().getDescription());
        } finally {
            var deleteForm = CoreFormFactory.getDeleteComponentVendorForm();
            deleteForm.setComponentVendorName(componentVendorName);
            assertNoErrors(service.deleteComponentVendor(userVisitPK, deleteForm));
        }
    }

    @Test
    void skipsIndexFieldChangesWhenIndexTypeRejectsAnIncompleteAssociation() throws Exception {
        var service = IndexUtil.getHome();
        var indexTypeName = newTestName();
        var xml = """
                <initialData>
                    <indexTypes>
                        <indexType indexTypeName="%s" %s sortOrder="%s">
                            <indexTypeDescription languageIsoName="en" description="Loader test index"/>
                            <indexField indexFieldName="FIELD" %s>
                                <indexFieldDescription languageIsoName="en" description="%s"/>
                            </indexField>
                        </indexType>
                    </indexTypes>
                </initialData>
                """;
        var association = "componentVendorName=\"ECHO_THREE\" entityTypeName=\"Party\"";
        try {
            loadXml(xml.formatted(indexTypeName, association + " isDefault=\"false\"", "1",
                    "isDefault=\"true\" sortOrder=\"1\"", "Initial field"));
            loadXml(xml.formatted(indexTypeName, "componentVendorName=\"ECHO_THREE\"", "2", "sortOrder=\"2\"", "Must be skipped"));

            var getDescriptionForm = IndexFormFactory.getGetIndexFieldDescriptionForm();
            getDescriptionForm.setIndexTypeName(indexTypeName);
            getDescriptionForm.setIndexFieldName("FIELD");
            getDescriptionForm.setLanguageIsoName("en");
            assertEquals("Initial field", requireSuccess(service.getIndexFieldDescription(userVisitPK, getDescriptionForm))
                    .getIndexFieldDescription().getDescription());

            loadXml(xml.formatted(indexTypeName, association, "3", "sortOrder=\"3\"", "Updated field"));
            assertEquals("Updated field", requireSuccess(service.getIndexFieldDescription(userVisitPK, getDescriptionForm))
                    .getIndexFieldDescription().getDescription());
        } finally {
            var deleteForm = IndexFormFactory.getDeleteIndexTypeForm();
            deleteForm.setIndexTypeName(indexTypeName);
            assertNoErrors(service.deleteIndexType(userVisitPK, deleteForm));
        }
    }

    @Test
    void omittingSecurityRoleGroupParentMovesItBackToRoot() throws Exception {
        var service = SecurityUtil.getHome();
        var parentName = newTestName();
        var childName = newTestName();
        var xml = """
                <initialData>
                    <securityRoleGroups>
                        <securityRoleGroup securityRoleGroupName="%s" isDefault="false" sortOrder="1"/>
                        <securityRoleGroup securityRoleGroupName="%s" %s>
                            <securityRoleGroupDescription languageIsoName="en" description="Child group"/>
                            <securityRole securityRoleName="LOADER_TEST" isDefault="false" sortOrder="1">
                                <securityRoleDescription languageIsoName="en" description="%s"/>
                            </securityRole>
                        </securityRoleGroup>
                    </securityRoleGroups>
                </initialData>
                """;

        try {
            loadXml(xml.formatted(parentName, childName,
                    "parentSecurityRoleGroupName=\"%s\" isDefault=\"false\" sortOrder=\"1\"".formatted(parentName), "Initial role"));
            var getGroupForm = SecurityFormFactory.getGetSecurityRoleGroupForm();
            getGroupForm.setSecurityRoleGroupName(childName);
            assertEquals(parentName, requireSuccess(service.getSecurityRoleGroup(userVisitPK, getGroupForm))
                    .getSecurityRoleGroup().getParentSecurityRoleGroup().getSecurityRoleGroupName());

            loadXml(xml.formatted(parentName, childName, "sortOrder=\"2\"", "Updated role"));
            assertEquals("ROOT", requireSuccess(service.getSecurityRoleGroup(userVisitPK, getGroupForm))
                    .getSecurityRoleGroup().getParentSecurityRoleGroup().getSecurityRoleGroupName());
            var getDescriptionForm = SecurityFormFactory.getGetSecurityRoleDescriptionForm();
            getDescriptionForm.setSecurityRoleGroupName(childName);
            getDescriptionForm.setSecurityRoleName("LOADER_TEST");
            getDescriptionForm.setLanguageIsoName("en");
            assertEquals("Updated role", requireSuccess(service.getSecurityRoleDescription(userVisitPK, getDescriptionForm))
                    .getSecurityRoleDescription().getDescription());
        } finally {
            var deleteForm = SecurityFormFactory.getDeleteSecurityRoleGroupForm();
            deleteForm.setSecurityRoleGroupName(childName);
            try {
                assertNoErrors(service.deleteSecurityRoleGroup(userVisitPK, deleteForm));
            } finally {
                deleteForm.setSecurityRoleGroupName(parentName);
                assertNoErrors(service.deleteSecurityRoleGroup(userVisitPK, deleteForm));
            }
        }
    }

    @Test
    void updatesSequenceSuffixAndChunkSizeAndClearsAnOmittedPrefix() throws Exception {
        var service = SequenceUtil.getHome();
        var sequenceTypeName = newTestName();
        var xml = """
                <initialData>
                    <sequenceTypes>
                        <sequenceType sequenceTypeName="%s" %s>
                            <sequenceTypeDescription languageIsoName="en" description="%s"/>
                        </sequenceType>
                    </sequenceTypes>
                </initialData>
                """;
        try {
            loadXml(xml.formatted(sequenceTypeName, """
                    prefix="START" suffix="END" chunkSize="10" sequenceEncoderTypeName="NONE"
                    sequenceChecksumTypeName="MOD_10" isDefault="false" sortOrder="1"
                    """, "Initial sequence type"));
            loadXml(xml.formatted(sequenceTypeName, "suffix=\"LAST\" chunkSize=\"20\"", "Updated sequence type"));

            var getTypeForm = SequenceFormFactory.getGetSequenceTypeForm();
            getTypeForm.setSequenceTypeName(sequenceTypeName);
            var sequenceType = requireSuccess(service.getSequenceType(userVisitPK, getTypeForm)).getSequenceType();
            assertNull(sequenceType.getPrefix());
            assertEquals("LAST", sequenceType.getSuffix());
            assertEquals(Integer.valueOf(20), sequenceType.getChunkSize());
            assertEquals("Updated sequence type", sequenceType.getDescription());
        } finally {
            var deleteForm = SequenceFormFactory.getDeleteSequenceTypeForm();
            deleteForm.setSequenceTypeName(sequenceTypeName);
            assertNoErrors(service.deleteSequenceType(userVisitPK, deleteForm));
        }
    }

    @Test
    void createsAndUpdatesAnItemDescriptionFromBinaryFiles(@TempDir Path directory) throws Exception {
        var service = ItemUtil.getHome();
        var descriptionTypeName = newTestName();
        var createTypeForm = ItemFormFactory.getCreateItemDescriptionTypeForm();
        createTypeForm.setItemDescriptionTypeName(descriptionTypeName);
        createTypeForm.setMimeTypeUsageTypeName("IMAGE");
        createTypeForm.setUseParentIfMissing("false");
        createTypeForm.setCheckContentWebAddress("false");
        createTypeForm.setIncludeInIndex("false");
        createTypeForm.setIndexDefault("false");
        createTypeForm.setIsDefault("false");
        createTypeForm.setSortOrder("0");
        createTypeForm.setScaleFromParent("false");
        requireSuccess(service.createItemDescriptionType(userVisitPK, createTypeForm));

        try {
            var xml = """
                    <initialData>
                        <items>
                            <item itemName="MINIMAL" commandAction="none">
                                <itemDescription itemDescriptionTypeName="%s" languageIsoName="en" mimeTypeName="image/png"
                                        itemImageTypeName="LINE_ART" path="%s"/>
                            </item>
                        </items>
                    </initialData>
                    """;
            var firstImage = directory.resolve("first.png");
            var secondImage = directory.resolve("second.png");
            assertTrue(ImageIO.write(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), "png", firstImage.toFile()));
            assertTrue(ImageIO.write(new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB), "png", secondImage.toFile()));
            var getDescriptionForm = ItemFormFactory.getGetItemDescriptionForm();
            getDescriptionForm.setItemName("MINIMAL");
            getDescriptionForm.setItemDescriptionTypeName(descriptionTypeName);
            getDescriptionForm.setLanguageIsoName("en");
            getDescriptionForm.setOptions(Set.of(ItemOptions.ItemDescriptionIncludeBlob));

            loadXml(xml.formatted(descriptionTypeName, firstImage));
            assertArrayEquals(Files.readAllBytes(firstImage), requireSuccess(service.getItemDescription(userVisitPK, getDescriptionForm))
                    .getItemDescription().getBlobDescription().byteArrayValue());

            var missingImage = directory.resolve("missing.png");
            var failure = assertThrows(SAXException.class, () -> loadXml(xml.formatted(descriptionTypeName, missingImage)));
            assertTrue(failure.getMessage().contains(missingImage.toString()));
            loadXml(xml.formatted(descriptionTypeName, secondImage));
            assertArrayEquals(Files.readAllBytes(secondImage), requireSuccess(service.getItemDescription(userVisitPK, getDescriptionForm))
                    .getItemDescription().getBlobDescription().byteArrayValue());
        } finally {
            var deleteForm = ItemFormFactory.getDeleteItemDescriptionTypeForm();
            deleteForm.setItemDescriptionTypeName(descriptionTypeName);
            assertNoErrors(service.deleteItemDescriptionType(userVisitPK, deleteForm));
        }
    }

    private void loadXml(String xml) throws Exception {
        var parser = new InitialDataParser("resolved-parent-test.xml") {
            @Override
            public UserVisitPK getUserVisit() {
                return DataLoaderResolvedParentTest.this.userVisitPK;
            }
        };
        parser.setupHandlers();

        var factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        var reader = factory.newSAXParser().getXMLReader();
        reader.setContentHandler(parser);
        reader.setErrorHandler(parser);
        reader.parse(new InputSource(new StringReader(xml)));
    }

    private String uuidOf(String entityRef) throws Exception {
        var form = CoreFormFactory.getGenerateUuidForm();
        form.setEntityRef(entityRef);
        form.setForceRegeneration("false");
        var uuid = requireSuccess(CoreUtil.getHome().generateUuid(userVisitPK, form)).getUuid();
        assertNotNull(uuid);
        return uuid;
    }

    private static String newTestName() {
        return "DL_PARENT_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    private static void assertNoErrors(CommandResult<?> result) {
        assertFalse(result.hasErrors(), result::toString);
    }

    private static <R extends BaseResult> R requireSuccess(CommandResult<R> result) {
        assertNoErrors(result);
        var payload = result.getResult();
        assertNotNull(payload, result::toString);
        return payload;
    }
}
