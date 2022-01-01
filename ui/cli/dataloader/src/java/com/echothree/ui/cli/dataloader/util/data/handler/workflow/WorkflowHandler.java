// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.control.user.workflow.common.form.CreateWorkflowDescriptionForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntityTypeForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowStepForm;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
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
        if(localName.equals("workflowDescription")) {
            CreateWorkflowDescriptionForm commandForm = WorkflowFormFactory.getCreateWorkflowDescriptionForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("workflowStep")) {
            CreateWorkflowStepForm commandForm = WorkflowFormFactory.getCreateWorkflowStepForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowStep(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new WorkflowStepHandler(initialDataParser, this, workflowName,
                    commandForm.getWorkflowStepName()));
        } else if(localName.equals("workflowDestination")) {
            CreateWorkflowDestinationForm commandForm = WorkflowFormFactory.getCreateWorkflowDestinationForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowDestination(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new WorkflowDestinationHandler(initialDataParser, this, workflowName, 
                    commandForm.getWorkflowStepName(), commandForm.getWorkflowDestinationName()));
        } else if(localName.equals("workflowEntrance")) {
            CreateWorkflowEntranceForm commandForm = WorkflowFormFactory.getCreateWorkflowEntranceForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowEntrance(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new WorkflowEntranceHandler(initialDataParser, this, workflowName,
                    commandForm.getWorkflowEntranceName()));
        } else if(localName.equals("workflowEntityType")) {
            CreateWorkflowEntityTypeForm commandForm = WorkflowFormFactory.getCreateWorkflowEntityTypeForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowEntityType(initialDataParser.getUserVisit(), commandForm);
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
