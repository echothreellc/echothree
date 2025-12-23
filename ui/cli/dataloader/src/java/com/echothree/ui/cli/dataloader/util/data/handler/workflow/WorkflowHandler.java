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

package com.echothree.ui.cli.dataloader.util.data.handler.workflow;

import com.echothree.control.user.workflow.common.WorkflowService;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.control.user.workflow.common.result.EditWorkflowDescriptionResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepResult;
import com.echothree.control.user.workflow.common.spec.WorkflowSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkflowHandler
        extends BaseHandler {

    WorkflowService workflowService;

    String workflowName;
    
    /** Creates a new instance of WorkflowHandler */
    public WorkflowHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String workflowName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            workflowService = WorkflowUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.workflowName = workflowName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        switch(localName) {
            case "workflowDescription" -> {
                var spec = WorkflowSpecFactory.getWorkflowDescriptionSpec();
                var editForm = WorkflowFormFactory.getEditWorkflowDescriptionForm();

                spec.setWorkflowName(workflowName);
                spec.set(getAttrsMap(attrs));

                var commandAction = getCommandAction(spec);
                getLogger().debug("Found: {}", commandAction);
                if(commandAction == null || commandAction.equals("create")) {
                    var attrsMap = getAttrsMap(attrs);

                    editForm.setSpec(spec);
                    editForm.setEditMode(EditMode.LOCK);

                    var commandResult = workflowService.editWorkflowDescription(initialDataParser.getUserVisit(), editForm);

                    if(commandResult.hasErrors()) {
                        if(commandResult.containsExecutionError(ExecutionErrors.UnknownWorkflowDescription.name())) {
                            var createForm = WorkflowFormFactory.getCreateWorkflowDescriptionForm();

                            spec.setWorkflowName(workflowName);
                            createForm.set(spec.get());

                            getLogger().debug("Creating: {}", spec.getWorkflowName());
                            commandResult = workflowService.createWorkflowDescription(initialDataParser.getUserVisit(), createForm);

                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        } else {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditWorkflowDescriptionResult)executionResult.getResult();

                        getLogger().debug("Checking for modifications: {}", spec.getWorkflowName());
                        if(result != null) {
                            updateEditFormValues(editForm, attrsMap, result);

                            commandResult = workflowService.editWorkflowDescription(initialDataParser.getUserVisit(), editForm);
                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        }
                    }
                }
            }
            case "workflowStep" -> {
                var spec = WorkflowSpecFactory.getWorkflowStepUniversalSpec();
                var editForm = WorkflowFormFactory.getEditWorkflowStepForm();

                spec.setWorkflowName(workflowName);
                spec.set(getAttrsMap(attrs));

                var commandAction = getCommandAction(spec);
                getLogger().debug("Found: {}", commandAction);
                if(commandAction == null || commandAction.equals("create")) {
                    var attrsMap = getAttrsMap(attrs);

                    editForm.setSpec(spec);
                    editForm.setEditMode(EditMode.LOCK);

                    var commandResult = workflowService.editWorkflowStep(initialDataParser.getUserVisit(), editForm);

                    if(commandResult.hasErrors()) {
                        if(commandResult.containsExecutionError(ExecutionErrors.UnknownWorkflowStepName.name())) {
                            var createForm = WorkflowFormFactory.getCreateWorkflowStepForm();

                            spec.setWorkflowName(workflowName);
                            createForm.set(spec.get());

                            getLogger().debug("Creating: {}", spec.getWorkflowStepName());
                            commandResult = workflowService.createWorkflowStep(initialDataParser.getUserVisit(), createForm);

                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        } else {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditWorkflowStepResult)executionResult.getResult();

                        getLogger().debug("Checking for modifications: {}", spec.getWorkflowStepName());
                        if(result != null) {
                            updateEditFormValues(editForm, attrsMap, result);

                            commandResult = workflowService.editWorkflowStep(initialDataParser.getUserVisit(), editForm);
                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        }
                    }
                }

                initialDataParser.pushHandler(new WorkflowStepHandler(initialDataParser, this, workflowName,
                        spec.getWorkflowStepName()));
            }
            case "workflowDestination" -> {
                var spec = WorkflowSpecFactory.getWorkflowDestinationUniversalSpec();
                var editForm = WorkflowFormFactory.getEditWorkflowDestinationForm();

                spec.setWorkflowName(workflowName);
                spec.set(getAttrsMap(attrs));

                var commandAction = getCommandAction(spec);
                getLogger().debug("Found: {}", commandAction);
                if(commandAction == null || commandAction.equals("create")) {
                    var attrsMap = getAttrsMap(attrs);

                    editForm.setSpec(spec);
                    editForm.setEditMode(EditMode.LOCK);

                    var commandResult = workflowService.editWorkflowDestination(initialDataParser.getUserVisit(), editForm);

                    if(commandResult.hasErrors()) {
                        if(commandResult.containsExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name())) {
                            var createForm = WorkflowFormFactory.getCreateWorkflowDestinationForm();

                            spec.setWorkflowName(workflowName);
                            createForm.set(spec.get());

                            getLogger().debug("Creating: {}", spec.getWorkflowDestinationName());
                            commandResult = workflowService.createWorkflowDestination(initialDataParser.getUserVisit(), createForm);

                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        } else {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditWorkflowDestinationResult)executionResult.getResult();

                        getLogger().debug("Checking for modifications: {}", spec.getWorkflowDestinationName());
                        if(result != null) {
                            updateEditFormValues(editForm, attrsMap, result);

                            commandResult = workflowService.editWorkflowDestination(initialDataParser.getUserVisit(), editForm);
                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        }
                    }
                }

                initialDataParser.pushHandler(new WorkflowDestinationHandler(initialDataParser, this, workflowName,
                        spec.getWorkflowStepName(), spec.getWorkflowDestinationName()));
            }
            case "workflowEntrance" -> {
                var spec = WorkflowSpecFactory.getWorkflowEntranceUniversalSpec();
                var editForm = WorkflowFormFactory.getEditWorkflowEntranceForm();

                spec.setWorkflowName(workflowName);
                spec.set(getAttrsMap(attrs));

                var commandAction = getCommandAction(spec);
                getLogger().debug("Found: {}", commandAction);
                if(commandAction == null || commandAction.equals("create")) {
                    var attrsMap = getAttrsMap(attrs);

                    editForm.setSpec(spec);
                    editForm.setEditMode(EditMode.LOCK);

                    var commandResult = workflowService.editWorkflowEntrance(initialDataParser.getUserVisit(), editForm);

                    if(commandResult.hasErrors()) {
                        if(commandResult.containsExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name())) {
                            var createForm = WorkflowFormFactory.getCreateWorkflowEntranceForm();

                            spec.setWorkflowName(workflowName);
                            createForm.set(spec.get());

                            getLogger().debug("Creating: {}", spec.getWorkflowEntranceName());
                            commandResult = workflowService.createWorkflowEntrance(initialDataParser.getUserVisit(), createForm);

                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        } else {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditWorkflowEntranceResult)executionResult.getResult();

                        getLogger().debug("Checking for modifications: {}", spec.getWorkflowEntranceName());
                        if(result != null) {
                            updateEditFormValues(editForm, attrsMap, result);

                            commandResult = workflowService.editWorkflowEntrance(initialDataParser.getUserVisit(), editForm);
                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        }
                    }
                }

                initialDataParser.pushHandler(new WorkflowEntranceHandler(initialDataParser, this, workflowName,
                        spec.getWorkflowEntranceName()));
            }
            case "workflowEntityType" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowEntityTypeForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.set(getAttrsMap(attrs));

                workflowService.createWorkflowEntityType(initialDataParser.getUserVisit(), commandForm);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("workflow")) {
            initialDataParser.popHandler();
        }
    }
    
}
