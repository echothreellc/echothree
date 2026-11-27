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

package com.echothree.ui.cli.dataloader;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.form.AuthenticationFormFactory;
import com.echothree.control.user.authentication.common.result.GetUserVisitResult;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.form.CreateWorkflowForm;
import com.echothree.control.user.workflow.common.form.EditWorkflowForm;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.control.user.workflow.common.result.CreateWorkflowResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowSpecFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class DataLoaderEditLifecycleTest {

    private static final String WORKFLOW_NAME = "TEST_WORKFLOW";
    private static final UserVisitPK TEST_VISIT = new UserVisitPK(1L);

    @Test
    void createsOnlyForTheExpectedMissingRecordError() throws SAXException {
        var commands = new StubCommands(form -> rejection(ExecutionErrors.UnknownWorkflowName));
        var input = Map.<String, Object>of("WorkflowName", WORKFLOW_NAME, "Description", "New description",
                "FormName", "Supplied name");

        assertTrue(commands.load(input));
        assertEquals(List.of(EditMode.LOCK), commands.modes);
        assertEquals(1, commands.creates.size());
        var createForm = commands.creates.getFirst();
        assertEquals("CreateWorkflowForm", createForm.getFormName());
        assertEquals(WORKFLOW_NAME, createForm.getWorkflowName());
        assertEquals("New description", createForm.getDescription());

        commands = new StubCommands(form -> rejection(ExecutionErrors.UnknownWorkflowName, ExecutionErrors.EntityLockFailed));
        assertFalse(commands.load(input));
        assertEquals(List.of(EditMode.LOCK), commands.modes);
        assertTrue(commands.creates.isEmpty());

        commands = new StubCommands(form -> new CommandResult<>(null,
                new ValidationResult(new Messages().add("WorkflowName", new Message("Required"))),
                rejection(ExecutionErrors.UnknownWorkflowName).getExecutionResult()));
        assertFalse(commands.load(input));
        assertEquals(List.of(EditMode.LOCK), commands.modes);
        assertTrue(commands.creates.isEmpty());
    }

    @Test
    void discoversInheritedSpecAndEditFields() throws SAXException {
        var input = Map.<String, Object>of("Uuid", "12345678-1234-1234-1234-123456789012",
                "Description", "Added description", "PreferredClobMimeTypeName", "text/html");
        var commands = new StubCommands(form -> switch(form.getEditMode()) {
            case LOCK -> {
                assertEquals(input.get("Uuid"), form.getSpec().getUuid());
                assertNull(form.getSpec().get("Description"));
                yield lockedWorkflow();
            }
            case UPDATE -> {
                assertEquals("Added description", form.getEdit().getDescription());
                assertEquals("7", form.getEdit().getSortOrder());
                assertEquals(WORKFLOW_NAME, form.getEdit().getWorkflowName());
                assertNull(form.getEdit().getPreferredClobMimeTypeName());
                yield success();
            }
            default -> fail("Unexpected command " + form.getEditMode());
        });

        assertTrue(commands.load(input));
        assertEquals(List.of(EditMode.LOCK, EditMode.UPDATE), commands.modes);
    }

    @Test
    void abandonsAnUnchangedEdit() throws SAXException {
        var commands = new StubCommands(form -> switch(form.getEditMode()) {
            case LOCK -> lockedWorkflow();
            case ABANDON -> {
                assertNull(form.getEdit());
                yield success();
            }
            default -> fail("Unexpected command " + form.getEditMode());
        });

        assertTrue(commands.load(Map.of("WorkflowName", WORKFLOW_NAME, "SortOrder", "7")));
        assertEquals(List.of(EditMode.LOCK, EditMode.ABANDON), commands.modes);
    }

    @Test
    void abandonsARejectedUpdateThatMayStillHoldTheLock() throws SAXException {
        var commands = new StubCommands(form -> switch(form.getEditMode()) {
            case LOCK -> lockedWorkflow();
            case UPDATE -> rejection(ExecutionErrors.InvalidParameterCount);
            case ABANDON -> success();
            default -> fail("Unexpected command " + form.getEditMode());
        });

        assertFalse(commands.load(Map.of("WorkflowName", WORKFLOW_NAME, "SortOrder", "8")));
        assertEquals(List.of(EditMode.LOCK, EditMode.UPDATE, EditMode.ABANDON), commands.modes);
    }

    @Test
    void reportsLockContentionAndStaleUpdatesWithoutAbandoning() throws SAXException {
        var input = Map.<String, Object>of("WorkflowName", WORKFLOW_NAME, "SortOrder", "8");
        var commands = new StubCommands(form -> rejection(ExecutionErrors.EntityLockFailed));
        assertFalse(commands.load(input));
        assertEquals(List.of(EditMode.LOCK), commands.modes);
        assertTrue(commands.creates.isEmpty());

        commands = new StubCommands(form -> switch(form.getEditMode()) {
            case LOCK -> lockedWorkflow();
            case UPDATE -> rejection(ExecutionErrors.EntityLockStale);
            default -> fail("Unexpected command " + form.getEditMode());
        });
        assertFalse(commands.load(input));
        assertEquals(List.of(EditMode.LOCK, EditMode.UPDATE), commands.modes);
    }

    @Test
    void abandonsWhenASuccessfulLockDoesNotReturnAnEdit() {
        var commands = new StubCommands(form -> success());

        var failure = assertThrows(SAXException.class, () -> commands.load(Map.of("WorkflowName", WORKFLOW_NAME)));

        assertTrue(failure.getMessage().contains("no edit payload after LOCK"));
        assertEquals(List.of(EditMode.LOCK, EditMode.ABANDON), commands.modes);
    }

    @Test
    void preservesTheUpdateExceptionWhenAbandonAlsoThrows() {
        var original = new IllegalStateException("Update failed");

        for(var cleanup : List.of(original, new IllegalStateException("Abandon failed"))) {
            var commands = new StubCommands(form -> switch(form.getEditMode()) {
                case LOCK -> lockedWorkflow();
                case UPDATE -> throw original;
                case ABANDON -> throw cleanup;
                default -> fail("Unexpected command " + form.getEditMode());
            });
            var failure = assertThrows(IllegalStateException.class,
                    () -> commands.load(Map.of("WorkflowName", WORKFLOW_NAME, "SortOrder", "8")));

            assertSame(original, failure);
            assertEquals(original.equals(cleanup) ? List.of() : List.of(cleanup), List.of(failure.getSuppressed()));
            assertEquals(List.of(EditMode.LOCK, EditMode.UPDATE, EditMode.ABANDON), commands.modes);
        }
    }

    @Test
    @Tag("integration")
    void anotherPartyCanLockTheWorkflowAfterARejectedLoaderUpdate() throws Exception {
        var authenticationService = AuthenticationUtil.getHome();
        var workflowService = WorkflowUtil.getHome();
        var loaderVisit = authenticationService.getDataLoaderUserVisit();
        assertNotNull(loaderVisit);

        try {
            var visitResult = authenticationService.getUserVisit(AuthenticationFormFactory.getGetUserVisitForm());
            assertNoErrors(visitResult);
            var employeeVisit = visitResult.getResult(GetUserVisitResult.class).getUserVisitPK();
            assertNotNull(employeeVisit);
            try {
                var loginForm = AuthenticationFormFactory.getEmployeeLoginForm();
                loginForm.setUsername("test e");
                loginForm.setPassword("password");
                loginForm.setCompanyName("TEST_COMPANY");
                assertNoErrors(authenticationService.employeeLogin(employeeVisit, loginForm));

                var workflowName = "DL_LOCK_TEST_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
                var createForm = WorkflowFormFactory.getCreateWorkflowForm();
                createForm.setWorkflowName(workflowName);
                createForm.setSortOrder("7");
                assertNoErrors(workflowService.createWorkflow(loaderVisit, createForm));

                try {
                    var handler = new EditHandler(loaderVisit);
                    var modes = new ArrayList<EditMode>();
                    assertFalse(handler.load(Map.of("WorkflowName", workflowName, "SortOrder", "not-a-number"),
                            (visit, form) -> {
                                var mode = form.getEditMode();
                                modes.add(mode);
                                var result = workflowService.editWorkflow(visit, form);

                                if(mode == EditMode.UPDATE) {
                                    assertTrue(result.hasValidationErrors(), result::toString);
                                    var competingLock = workflowService.editWorkflow(employeeVisit,
                                            workflowEdit(workflowName, EditMode.LOCK));
                                    try {
                                        assertTrue(competingLock.containsExecutionError(ExecutionErrors.EntityLockFailed.name()),
                                                competingLock::toString);
                                    } finally {
                                        if(!competingLock.hasErrors()) {
                                            workflowService.editWorkflow(employeeVisit, workflowEdit(workflowName, EditMode.ABANDON));
                                        }
                                    }
                                }

                                return result;
                            }, (visit, form) -> fail("Unexpected create for an existing workflow")));
                    assertEquals(List.of(EditMode.LOCK, EditMode.UPDATE, EditMode.ABANDON), modes);

                    var acquiredLock = workflowService.editWorkflow(employeeVisit, workflowEdit(workflowName, EditMode.LOCK));
                    try {
                        assertNoErrors(acquiredLock);
                        assertEquals("7", acquiredLock.getResult().getEdit().getSortOrder());
                    } finally {
                        if(!acquiredLock.hasErrors()) {
                            assertNoErrors(workflowService.editWorkflow(employeeVisit, workflowEdit(workflowName, EditMode.ABANDON)));
                        }
                    }
                } finally {
                    try {
                        workflowService.editWorkflow(loaderVisit, workflowEdit(workflowName, EditMode.ABANDON));
                    } finally {
                        var deleteForm = WorkflowFormFactory.getDeleteWorkflowForm();
                        deleteForm.setWorkflowName(workflowName);
                        assertNoErrors(workflowService.deleteWorkflow(loaderVisit, deleteForm));
                    }
                }
            } finally {
                authenticationService.invalidateUserVisit(employeeVisit);
            }
        } finally {
            authenticationService.invalidateUserVisit(loaderVisit);
        }
    }

    private static void assertNoErrors(CommandResult<?> result) {
        assertFalse(result.hasErrors(), result::toString);
    }

    private static EditWorkflowForm workflowEdit(String workflowName, EditMode editMode) {
        var form = WorkflowFormFactory.getEditWorkflowForm();
        var spec = WorkflowSpecFactory.getWorkflowUniversalSpec();
        spec.setWorkflowName(workflowName);
        form.setSpec(spec);
        form.setEditMode(editMode);
        return form;
    }

    private static CommandResult<EditWorkflowResult> lockedWorkflow() {
        var edit = WorkflowEditFactory.getWorkflowEdit();
        edit.setWorkflowName(WORKFLOW_NAME);
        edit.setSortOrder("7");
        var result = WorkflowResultFactory.getEditWorkflowResult();
        result.setEdit(edit);
        return new CommandResult<>(null, null, new ExecutionResult<>(null, null, result));
    }

    private static CommandResult<EditWorkflowResult> success() {
        return new CommandResult<>(null, null, null);
    }

    private static CommandResult<EditWorkflowResult> rejection(ExecutionErrors... errors) {
        var messages = new Messages();
        for(var error : errors) {
            messages.add(Messages.EXECUTION_ERROR, new Message(error.name()));
        }
        return new CommandResult<>(null, null, new ExecutionResult<>(null, messages, null));
    }

    private static final class StubCommands {
        private final List<EditMode> modes = new ArrayList<>();
        private final List<CreateWorkflowForm> creates = new ArrayList<>();
        private final Function<EditWorkflowForm, CommandResult<EditWorkflowResult>> editCommand;

        private StubCommands(Function<EditWorkflowForm, CommandResult<EditWorkflowResult>> editCommand) {
            this.editCommand = editCommand;
        }

        private boolean load(Map<String, Object> attrs) throws SAXException {
            return new EditHandler(TEST_VISIT).load(attrs, (visit, form) -> {
                assertEquals(TEST_VISIT, visit);
                modes.add(form.getEditMode());
                return editCommand.apply(form);
            }, (visit, form) -> {
                assertEquals(TEST_VISIT, visit);
                creates.add(form);
                var result = WorkflowResultFactory.getCreateWorkflowResult();
                result.setWorkflowName(form.getWorkflowName());
                return new CommandResult<>(null, null, new ExecutionResult<>(null, null, result));
            });
        }
    }

    private static final class EditHandler extends BaseHandler {

        private EditHandler(UserVisitPK visit) {
            super(new InitialDataParser("/data-loader-edit-test.xml") {
                @Override
                public UserVisitPK getUserVisit() {
                    return visit;
                }
            }, null);
        }

        private boolean load(Map<String, Object> attrs, BiFunction<UserVisitPK, EditWorkflowForm, CommandResult<EditWorkflowResult>> editCommand,
                BiFunction<UserVisitPK, CreateWorkflowForm, CommandResult<CreateWorkflowResult>> createCommand) throws SAXException {
            return editOrCreate(attrs, WorkflowSpecFactory.getWorkflowUniversalSpec(),
                    WorkflowFormFactory.getEditWorkflowForm(), editCommand, WorkflowFormFactory.getCreateWorkflowForm(),
                    createCommand, ExecutionErrors.UnknownWorkflowName).successful();
        }

        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) {
        }

        @Override
        public void endElement(String namespaceURI, String localName, String qName) {
        }
    }
}
