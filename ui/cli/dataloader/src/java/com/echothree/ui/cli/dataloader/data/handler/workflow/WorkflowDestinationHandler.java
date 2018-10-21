// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.workflow;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.remote.WorkflowService;
import com.echothree.control.user.workflow.remote.form.CreateWorkflowDestinationDescriptionForm;
import com.echothree.control.user.workflow.remote.form.CreateWorkflowDestinationPartyTypeForm;
import com.echothree.control.user.workflow.remote.form.CreateWorkflowDestinationSelectorForm;
import com.echothree.control.user.workflow.remote.form.CreateWorkflowDestinationStepForm;
import com.echothree.control.user.workflow.remote.form.WorkflowFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
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
        if(localName.equals("workflowDestinationDescription")) {
            CreateWorkflowDestinationDescriptionForm commandForm = WorkflowFormFactory.getCreateWorkflowDestinationDescriptionForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowDestinationDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("workflowDestinationSelector")) {
            CreateWorkflowDestinationSelectorForm commandForm = WorkflowFormFactory.getCreateWorkflowDestinationSelectorForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowDestinationSelector(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("workflowDestinationPartyType")) {
            CreateWorkflowDestinationPartyTypeForm commandForm = WorkflowFormFactory.getCreateWorkflowDestinationPartyTypeForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowDestinationPartyType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new WorkflowDestinationPartyTypeHandler(initialDataParser, this, workflowName,
                    workflowStepName, workflowDestinationName, commandForm.getPartyTypeName()));
        } else if(localName.equals("workflowDestinationStep")) {
            CreateWorkflowDestinationStepForm commandForm = WorkflowFormFactory.getCreateWorkflowDestinationStepForm();
            
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
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("workflowDestination")) {
            initialDataParser.popHandler();
        }
    }
    
}
