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

package com.echothree.ui.cli.dataloader.util.data.handler.workeffort;

import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.common.WorkEffortService;
import com.echothree.control.user.workeffort.common.form.WorkEffortFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkEffortScopeHandler
        extends BaseHandler {

    WorkEffortService workEffortService;
    String workEffortTypeName;
    String workEffortScopeName;
    
    /** Creates a new instance of WorkEffortScopeHandler */
    public WorkEffortScopeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String workEffortTypeName,
            String workEffortScopeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        workEffortService = lookupService(WorkEffortService.class, WorkEffortUtil::getHome);
        this.workEffortTypeName = workEffortTypeName;
        this.workEffortScopeName = workEffortScopeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("workEffortScopeDescription")) {
            var commandForm = WorkEffortFormFactory.getCreateWorkEffortScopeDescriptionForm();

            commandForm.setWorkEffortTypeName(workEffortTypeName);
            commandForm.setWorkEffortScopeName(workEffortScopeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, workEffortService.createWorkEffortScopeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("workEffortScope")) {
            initialDataParser.popHandler();
        }
    }
    
}
