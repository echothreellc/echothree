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

public class CommandMessageHandler
        extends BaseHandler {

    CoreService coreService;
    String commandMessageTypeName;
    String commandMessageKey;
    
    /** Creates a new instance of CommandMessageHandler */
    public CommandMessageHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String commandMessageTypeName, String commandMessageKey)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.commandMessageTypeName = commandMessageTypeName;
        this.commandMessageKey = commandMessageKey;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("commandMessageTranslation")) {
            var commandForm = CoreFormFactory.getCreateCommandMessageTranslationForm();
            
            commandForm.setCommandMessageTypeName(commandMessageTypeName);
            commandForm.setCommandMessageKey(commandMessageKey);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createCommandMessageTranslation(initialDataParser.getUserVisit(), commandForm);
        } 
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("commandMessage")) {
            initialDataParser.popHandler();
        }
    }
    
}
