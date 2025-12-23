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

package com.echothree.ui.cli.dataloader.util.data.handler.associate;

import com.echothree.control.user.associate.common.AssociateUtil;
import com.echothree.control.user.associate.common.AssociateService;
import com.echothree.control.user.associate.common.form.AssociateFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class AssociateProgramHandler
        extends BaseHandler {
    AssociateService associateService;
    String associateProgramName;
    
    /** Creates a new instance of AssociateProgramHandler */
    public AssociateProgramHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String associateProgramName) {
        super(initialDataParser, parentHandler);
        
        try {
            associateService = AssociateUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.associateProgramName = associateProgramName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("associateProgramDescription")) {
            var commandForm = AssociateFormFactory.getCreateAssociateProgramDescriptionForm();
            String attrLanguageIsoName = null;
            String attrDescription = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    attrLanguageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
            }
            
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setLanguageIsoName(attrLanguageIsoName);
            commandForm.setDescription(attrDescription);
            
            checkCommandResult(associateService.createAssociateProgramDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("associateProgram")) {
            initialDataParser.popHandler();
        }
    }
    
}
