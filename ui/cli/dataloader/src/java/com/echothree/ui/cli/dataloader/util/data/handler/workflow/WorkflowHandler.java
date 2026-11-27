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
import com.echothree.control.user.workflow.common.spec.WorkflowSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import java.util.Map;
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
            case "workflowDescription" -> loadWorkflowDescription(getWorkflowAttributes(attrs));
            case "workflowStep" -> loadWorkflowStep(getWorkflowAttributes(attrs));
            case "workflowDestination" -> loadWorkflowDestination(getWorkflowAttributes(attrs));
            case "workflowEntrance" -> loadWorkflowEntrance(getWorkflowAttributes(attrs));
            case "workflowEntityType" -> {
                var commandForm = WorkflowFormFactory.getCreateWorkflowEntityTypeForm();

                commandForm.setWorkflowName(workflowName);
                commandForm.set(getAttrsMap(attrs));

                checkCommandResult(commandForm, workflowService.createWorkflowEntityType(initialDataParser.getUserVisit(), commandForm));
            }
        }
    }
    
    private Map<String, Object> getWorkflowAttributes(Attributes attrs) {
        var attrsMap = getAttrsMap(attrs);
        attrsMap.putIfAbsent("WorkflowName", workflowName);

        return attrsMap;
    }

    private void loadWorkflowDescription(Map<String, Object> attrsMap) throws SAXException {
        if(shouldEdit(attrsMap)) {
            editOrCreate(attrsMap, WorkflowSpecFactory.getWorkflowDescriptionSpec(),
                    WorkflowFormFactory.getEditWorkflowDescriptionForm(), workflowService::editWorkflowDescription,
                    WorkflowFormFactory.getCreateWorkflowDescriptionForm(), workflowService::createWorkflowDescription,
                    ExecutionErrors.UnknownWorkflowDescription);
        }
    }

    private void loadWorkflowStep(Map<String, Object> attrsMap) throws SAXException {
        var stepWorkflowName = (String)attrsMap.get("WorkflowName");
        var workflowStepName = (String)attrsMap.get("WorkflowStepName");
        var processChildren = true;

        if(shouldEdit(attrsMap)) {
            var outcome = editOrCreate(attrsMap, WorkflowSpecFactory.getWorkflowStepUniversalSpec(),
                    WorkflowFormFactory.getEditWorkflowStepForm(), workflowService::editWorkflowStep,
                    WorkflowFormFactory.getCreateWorkflowStepForm(), workflowService::createWorkflowStep,
                    ExecutionErrors.UnknownWorkflowStepName);
            processChildren = outcome.successful();

            if(outcome.lockedEdit() != null) {
                workflowStepName = outcome.lockedEdit().getWorkflowStepName();
            } else if(outcome.createdRecord() != null) {
                stepWorkflowName = outcome.createdRecord().getWorkflowName();
                workflowStepName = outcome.createdRecord().getWorkflowStepName();
            }
        }

        if(processChildren) {
            if(workflowStepName == null) {
                throw new SAXException("Workflow step name is missing for child elements");
            }
            initialDataParser.pushHandler(new WorkflowStepHandler(initialDataParser, this, stepWorkflowName, workflowStepName));
        } else {
            skipCurrentElement();
        }
    }

    private void loadWorkflowDestination(Map<String, Object> attrsMap) throws SAXException {
        var destinationWorkflowName = (String)attrsMap.get("WorkflowName");
        var workflowStepName = (String)attrsMap.get("WorkflowStepName");
        var workflowDestinationName = (String)attrsMap.get("WorkflowDestinationName");
        var processChildren = true;

        if(shouldEdit(attrsMap)) {
            var outcome = editOrCreate(attrsMap, WorkflowSpecFactory.getWorkflowDestinationUniversalSpec(),
                    WorkflowFormFactory.getEditWorkflowDestinationForm(), workflowService::editWorkflowDestination,
                    WorkflowFormFactory.getCreateWorkflowDestinationForm(), workflowService::createWorkflowDestination,
                    ExecutionErrors.UnknownWorkflowDestinationName);
            processChildren = outcome.successful();

            if(outcome.lockedEdit() != null) {
                workflowDestinationName = outcome.lockedEdit().getWorkflowDestinationName();
            } else if(outcome.createdRecord() != null) {
                destinationWorkflowName = outcome.createdRecord().getWorkflowName();
                workflowStepName = outcome.createdRecord().getWorkflowStepName();
                workflowDestinationName = outcome.createdRecord().getWorkflowDestinationName();
            }
        }

        if(processChildren) {
            if(workflowStepName == null || workflowDestinationName == null) {
                throw new SAXException("Workflow step and destination names are required for destination child elements");
            }
            initialDataParser.pushHandler(new WorkflowDestinationHandler(initialDataParser, this,
                    destinationWorkflowName, workflowStepName, workflowDestinationName));
        } else {
            skipCurrentElement();
        }
    }

    private void loadWorkflowEntrance(Map<String, Object> attrsMap) throws SAXException {
        var entranceWorkflowName = (String)attrsMap.get("WorkflowName");
        var workflowEntranceName = (String)attrsMap.get("WorkflowEntranceName");
        var processChildren = true;

        if(shouldEdit(attrsMap)) {
            var outcome = editOrCreate(attrsMap, WorkflowSpecFactory.getWorkflowEntranceUniversalSpec(),
                    WorkflowFormFactory.getEditWorkflowEntranceForm(), workflowService::editWorkflowEntrance,
                    WorkflowFormFactory.getCreateWorkflowEntranceForm(), workflowService::createWorkflowEntrance,
                    ExecutionErrors.UnknownWorkflowEntranceName);
            processChildren = outcome.successful();

            if(outcome.lockedEdit() != null) {
                workflowEntranceName = outcome.lockedEdit().getWorkflowEntranceName();
            } else if(outcome.createdRecord() != null) {
                entranceWorkflowName = outcome.createdRecord().getWorkflowName();
                workflowEntranceName = outcome.createdRecord().getWorkflowEntranceName();
            }
        }

        if(processChildren) {
            if(workflowEntranceName == null) {
                throw new SAXException("Workflow entrance name is missing for child elements");
            }
            initialDataParser.pushHandler(new WorkflowEntranceHandler(initialDataParser, this, entranceWorkflowName, workflowEntranceName));
        } else {
            skipCurrentElement();
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
