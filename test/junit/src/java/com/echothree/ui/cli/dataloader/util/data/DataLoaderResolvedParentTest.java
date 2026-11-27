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
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.client.test.UserVisitTestCase;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.CommandResult;
import java.io.StringReader;
import java.util.UUID;
import javax.xml.parsers.SAXParserFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
