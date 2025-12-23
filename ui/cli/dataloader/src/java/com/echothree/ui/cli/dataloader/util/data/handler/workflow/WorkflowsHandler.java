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
import com.echothree.control.user.workflow.common.result.EditWorkflowResult;
import com.echothree.control.user.workflow.common.spec.WorkflowSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
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
            var spec = WorkflowSpecFactory.getWorkflowUniversalSpec();
            var editForm = WorkflowFormFactory.getEditWorkflowForm();

            spec.set(getAttrsMap(attrs));

            var commandAction = getCommandAction(spec);
            getLogger().debug("Found: {}", commandAction);
            if(commandAction == null || commandAction.equals("create")) {
                var attrsMap = getAttrsMap(attrs);

                editForm.setSpec(spec);
                editForm.setEditMode(EditMode.LOCK);

                var commandResult = workflowService.editWorkflow(initialDataParser.getUserVisit(), editForm);

                if(commandResult.hasErrors()) {
                    if(commandResult.containsExecutionError(ExecutionErrors.UnknownWorkflowName.name())) {
                        var createForm = WorkflowFormFactory.getCreateWorkflowForm();

                        createForm.set(spec.get());

                        getLogger().debug("Creating: {}", spec.getWorkflowName());
                        commandResult = workflowService.createWorkflow(initialDataParser.getUserVisit(), createForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditWorkflowResult)executionResult.getResult();

                    getLogger().debug("Checking for modifications: {}", spec.getWorkflowName());
                    if(result != null) {
                        updateEditFormValues(editForm, attrsMap, result);

                        commandResult = workflowService.editWorkflow(initialDataParser.getUserVisit(), editForm);
                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
            
            initialDataParser.pushHandler(new WorkflowHandler(initialDataParser, this, spec.getWorkflowName()));
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
