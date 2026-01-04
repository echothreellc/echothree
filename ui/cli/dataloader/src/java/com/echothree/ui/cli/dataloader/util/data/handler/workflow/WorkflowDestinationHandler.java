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

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.WorkflowService;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationDescriptionResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepDescriptionResult;
import com.echothree.control.user.workflow.common.spec.WorkflowSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkflowDestinationHandler
        extends BaseHandler {
    WorkflowService workflowService;
    String workflowName;
    String workflowStepName;
    String workflowDestinationName;
    
    /** Creates a new instance of WorkflowDestinationHandler */
    public WorkflowDestinationHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String workflowName,
            String workflowStepName, String workflowDestinationName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            workflowService = WorkflowUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.workflowName = workflowName;
        this.workflowStepName = workflowStepName;
        this.workflowDestinationName = workflowDestinationName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        switch(localName) {
            case "workflowDestinationDescription" -> {
                var spec = WorkflowSpecFactory.getWorkflowDestinationDescriptionSpec();
                var editForm = WorkflowFormFactory.getEditWorkflowDestinationDescriptionForm();

                spec.setWorkflowName(workflowName);
                spec.setWorkflowStepName(workflowStepName);
                spec.setWorkflowDestinationName(workflowDestinationName);
                spec.set(getAttrsMap(attrs));

                var commandAction = getCommandAction(spec);
                getLogger().debug("Found: {}", commandAction);
                if(commandAction == null || commandAction.equals("create")) {
                    var attrsMap = getAttrsMap(attrs);

                    editForm.setSpec(spec);
                    editForm.setEditMode(EditMode.LOCK);

                    var commandResult = workflowService.editWorkflowDestinationDescription(initialDataParser.getUserVisit(), editForm);

                    if(commandResult.hasErrors()) {
                        if(commandResult.containsExecutionError(ExecutionErrors.UnknownWorkflowDestinationDescription.name())) {
                            var createForm = WorkflowFormFactory.getCreateWorkflowDestinationDescriptionForm();

                            spec.setWorkflowName(workflowName);
                            spec.setWorkflowStepName(workflowStepName);
                            spec.setWorkflowDestinationName(workflowDestinationName);
                            createForm.set(spec.get());

                            getLogger().debug("Creating: {}", spec.getWorkflowName());
                            commandResult = workflowService.createWorkflowDestinationDescription(initialDataParser.getUserVisit(), createForm);

                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        } else {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditWorkflowDestinationDescriptionResult)executionResult.getResult();

                        getLogger().debug("Checking for modifications: {}", spec.getWorkflowName());
                        if(result != null) {
                            updateEditFormValues(editForm, attrsMap, result);

                            commandResult = workflowService.editWorkflowDestinationDescription(initialDataParser.getUserVisit(), editForm);
                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        }
                    }
                }
            }
            case "workflowDestinationSelector" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowDestinationSelectorForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.setWorkflowStepName(workflowStepName);
                commandForm.setWorkflowDestinationName(workflowDestinationName);
                commandForm.set(getAttrsMap(attrs));

                workflowService.createWorkflowDestinationSelector(initialDataParser.getUserVisit(), commandForm);
            }
            case "workflowDestinationPartyType" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowDestinationPartyTypeForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.setWorkflowStepName(workflowStepName);
                commandForm.setWorkflowDestinationName(workflowDestinationName);
                commandForm.set(getAttrsMap(attrs));

                workflowService.createWorkflowDestinationPartyType(initialDataParser.getUserVisit(), commandForm);

                initialDataParser.pushHandler(new WorkflowDestinationPartyTypeHandler(initialDataParser, this, workflowName,
                        workflowStepName, workflowDestinationName, commandForm.getPartyTypeName()));
            }
            case "workflowDestinationStep" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowDestinationStepForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.setWorkflowStepName(workflowStepName);
                commandForm.setWorkflowDestinationName(workflowDestinationName);
                commandForm.set(getAttrsMap(attrs));

                if(commandForm.getDestinationWorkflowName() == null) {
                    commandForm.setDestinationWorkflowName(workflowName);
                }

                workflowService.createWorkflowDestinationStep(initialDataParser.getUserVisit(), commandForm);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("workflowDestination")) {
            initialDataParser.popHandler();
        }
    }
    
}
