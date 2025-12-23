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

package com.echothree.ui.cli.dataloader.util.data.handler.workrequirement;

import com.echothree.control.user.workrequirement.common.WorkRequirementUtil;
import com.echothree.control.user.workrequirement.common.WorkRequirementService;
import com.echothree.control.user.workrequirement.common.form.WorkRequirementFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkRequirementTypeHandler
        extends BaseHandler {
    WorkRequirementService workRequirementService;
    String workEffortTypeName;
    String workRequirementTypeName;
    
    /** Creates a new instance of WorkRequirementTypeHandler */
    public WorkRequirementTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String workEffortTypeName,
            String workRequirementTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            workRequirementService = WorkRequirementUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        this.workEffortTypeName = workEffortTypeName;
        this.workRequirementTypeName = workRequirementTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("workRequirementTypeDescription")) {
            try {
                var commandForm = WorkRequirementFormFactory.getCreateWorkRequirementTypeDescriptionForm();
                
                commandForm.setWorkEffortTypeName(workEffortTypeName);
                commandForm.setWorkRequirementTypeName(workRequirementTypeName);
                commandForm.set(getAttrsMap(attrs));
                
                workRequirementService.createWorkRequirementTypeDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("workRequirementType")) {
            initialDataParser.popHandler();
        }
    }
    
}
