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

package com.echothree.ui.cli.dataloader.util.data.handler.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ApplicationHandler
        extends BaseHandler {
    
    CoreService coreService;
    String applicationName;
    
    /** Creates a new instance of ApplicationHandler */
    public ApplicationHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String applicationName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.applicationName = applicationName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("applicationDescription")) {
            var commandForm = CoreFormFactory.getCreateApplicationDescriptionForm();
            
            commandForm.setApplicationName(applicationName);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createApplicationDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("applicationEditor")) {
            var commandForm = CoreFormFactory.getCreateApplicationEditorForm();
            
            commandForm.setApplicationName(applicationName);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createApplicationEditor(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("applicationEditorUse")) {
            var commandForm = CoreFormFactory.getCreateApplicationEditorUseForm();
            
            commandForm.setApplicationName(applicationName);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createApplicationEditorUse(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new ApplicationEditorUseHandler(initialDataParser, this, applicationName, commandForm.getApplicationEditorUseName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("application")) {
            initialDataParser.popHandler();
        }
    }
    
}
