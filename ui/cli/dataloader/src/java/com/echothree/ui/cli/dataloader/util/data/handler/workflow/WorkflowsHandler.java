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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkflowsHandler
        extends BaseHandler {

    WorkflowService workflowService;
    
    /** Creates a new instance of WorkflowsHandler */
    public WorkflowsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            workflowService = WorkflowUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("workflow")) {
            var attrsMap = getAttrsMap(attrs);
            var workflowName = (String)attrsMap.get("WorkflowName");
            var processChildren = true;

            if(shouldEdit(attrsMap)) {
                var outcome = editOrCreate(attrsMap, WorkflowSpecFactory.getWorkflowUniversalSpec(),
                        WorkflowFormFactory.getEditWorkflowForm(), workflowService::editWorkflow,
                        WorkflowFormFactory.getCreateWorkflowForm(), workflowService::createWorkflow, ExecutionErrors.UnknownWorkflowName);
                processChildren = outcome.successful();

                if(outcome.lockedEdit() != null) {
                    workflowName = outcome.lockedEdit().getWorkflowName();
                } else if(outcome.createdRecord() != null) {
                    workflowName = outcome.createdRecord().getWorkflowName();
                }
            } else if(workflowName == null) {
                var getForm = WorkflowFormFactory.getGetWorkflowForm();
                setFormFields(getForm, attrsMap);

                var commandResult = workflowService.getWorkflow(initialDataParser.getUserVisit(), getForm);
                checkCommandResult(getForm, commandResult);
                processChildren = !commandResult.hasErrors();
                var result = commandResult.getResult();
                if(processChildren && result != null && result.getWorkflow() != null) {
                    workflowName = result.getWorkflow().getWorkflowName();
                }
            }

            if(processChildren) {
                if(workflowName == null) {
                    throw new SAXException("Workflow name is missing for child elements");
                }
                initialDataParser.pushHandler(new WorkflowHandler(initialDataParser, this, workflowName));
            } else {
                skipCurrentElement();
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("workflows")) {
            initialDataParser.popHandler();
        }
    }
    
}
