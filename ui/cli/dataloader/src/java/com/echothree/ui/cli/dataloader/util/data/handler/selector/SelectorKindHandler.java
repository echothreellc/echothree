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

package com.echothree.ui.cli.dataloader.util.data.handler.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.SelectorService;
import com.echothree.control.user.selector.common.form.SelectorFormFactory;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.WorkflowService;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SelectorKindHandler
        extends BaseHandler {
    SelectorService selectorService;
    WorkflowService workflowService;
    String selectorKindName;
    
    /** Creates a new instance of SelectorKindHandler */
    public SelectorKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String selectorKindName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            selectorService = SelectorUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        try {
            workflowService = WorkflowUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.selectorKindName = selectorKindName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("selectorKindDescription")) {
            var commandForm = SelectorFormFactory.getCreateSelectorKindDescriptionForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.set(getAttrsMap(attrs));

            selectorService.createSelectorKindDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("selectorNodeTypeUse")) {
            var commandForm = SelectorFormFactory.getCreateSelectorNodeTypeUseForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.set(getAttrsMap(attrs));

            selectorService.createSelectorNodeTypeUse(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("workflowSelectorKind")) {
            var commandForm = WorkflowFormFactory.getCreateWorkflowSelectorKindForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.set(getAttrsMap(attrs));

            workflowService.createWorkflowSelectorKind(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("selectorType")) {
            var commandForm = SelectorFormFactory.getCreateSelectorTypeForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.set(getAttrsMap(attrs));

            selectorService.createSelectorType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new SelectorTypeHandler(initialDataParser, this, selectorKindName, commandForm.getSelectorTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("selectorKind")) {
            initialDataParser.popHandler();
        }
    }
    
}
