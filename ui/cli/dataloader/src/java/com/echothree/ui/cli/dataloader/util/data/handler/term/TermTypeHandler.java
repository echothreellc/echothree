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

package com.echothree.ui.cli.dataloader.util.data.handler.term;

import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.TermService;
import com.echothree.control.user.term.common.form.TermFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TermTypeHandler
        extends BaseHandler {
    TermService termService;
    String termTypeName;
    
    /** Creates a new instance of TermTypeHandler */
    public TermTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String termTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            termService = TermUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.termTypeName = termTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("termTypeDescription")) {
            String languageIsoName = null;
            String description = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = TermFormFactory.getCreateTermTypeDescriptionForm();
                
                commandForm.setTermTypeName(termTypeName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                checkCommandResult(termService.createTermTypeDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("termType")) {
            initialDataParser.popHandler();
        }
    }
    
}
