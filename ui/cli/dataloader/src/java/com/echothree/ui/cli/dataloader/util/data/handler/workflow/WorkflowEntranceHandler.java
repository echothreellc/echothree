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
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceDescriptionResult;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepDescriptionResult;
import com.echothree.control.user.workflow.common.spec.WorkflowSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkflowEntranceHandler
        extends BaseHandler {
    WorkflowService workflowService;
    String workflowName;
    String workflowEntranceName;
    
    /** Creates a new instance of WorkflowEntranceHandler */
    public WorkflowEntranceHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String workflowName, String workflowEntranceName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            workflowService = WorkflowUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.workflowName = workflowName;
        this.workflowEntranceName = workflowEntranceName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        switch(localName) {
            case "workflowEntranceDescription" -> {
                var spec = WorkflowSpecFactory.getWorkflowEntranceDescriptionSpec();
                var editForm = WorkflowFormFactory.getEditWorkflowEntranceDescriptionForm();

                spec.setWorkflowName(workflowName);
                spec.setWorkflowEntranceName(workflowEntranceName);
                spec.set(getAttrsMap(attrs));

                var commandAction = getCommandAction(spec);
                getLogger().debug("Found: {}", commandAction);
                if(commandAction == null || commandAction.equals("create")) {
                    var attrsMap = getAttrsMap(attrs);

                    editForm.setSpec(spec);
                    editForm.setEditMode(EditMode.LOCK);

                    var commandResult = workflowService.editWorkflowEntranceDescription(initialDataParser.getUserVisit(), editForm);

                    if(commandResult.hasErrors()) {
                        if(commandResult.containsExecutionError(ExecutionErrors.UnknownWorkflowEntranceDescription.name())) {
                            var createForm = WorkflowFormFactory.getCreateWorkflowEntranceDescriptionForm();

                            spec.setWorkflowName(workflowName);
                            spec.setWorkflowEntranceName(workflowEntranceName);
                            createForm.set(spec.get());

                            getLogger().debug("Creating: {}", spec.getWorkflowName());
                            commandResult = workflowService.createWorkflowEntranceDescription(initialDataParser.getUserVisit(), createForm);

                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        } else {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditWorkflowEntranceDescriptionResult)executionResult.getResult();

                        getLogger().debug("Checking for modifications: {}", spec.getWorkflowName());
                        if(result != null) {
                            updateEditFormValues(editForm, attrsMap, result);

                            commandResult = workflowService.editWorkflowEntranceDescription(initialDataParser.getUserVisit(), editForm);
                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        }
                    }
                }
            }
            case "workflowEntranceSelector" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowEntranceSelectorForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.setWorkflowEntranceName(workflowEntranceName);
                commandForm.set(getAttrsMap(attrs));

                workflowService.createWorkflowEntranceSelector(initialDataParser.getUserVisit(), commandForm);
            }
            case "workflowEntrancePartyType" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowEntrancePartyTypeForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.setWorkflowEntranceName(workflowEntranceName);
                commandForm.set(getAttrsMap(attrs));

                workflowService.createWorkflowEntrancePartyType(initialDataParser.getUserVisit(), commandForm);

                initialDataParser.pushHandler(new WorkflowEntrancePartyTypeHandler(initialDataParser, this, workflowName,
                        workflowEntranceName, commandForm.getPartyTypeName()));
            }
            case "workflowEntranceStep" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowEntranceStepForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.setWorkflowEntranceName(workflowEntranceName);
                commandForm.set(getAttrsMap(attrs));

                if(commandForm.getEntranceWorkflowName() == null) {
                    commandForm.setEntranceWorkflowName(workflowName);
                }

                workflowService.createWorkflowEntranceStep(initialDataParser.getUserVisit(), commandForm);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("workflowEntrance")) {
            initialDataParser.popHandler();
        }
    }
    
}
