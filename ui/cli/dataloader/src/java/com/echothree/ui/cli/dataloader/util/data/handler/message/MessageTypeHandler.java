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

package com.echothree.ui.cli.dataloader.util.data.handler.message;

import com.echothree.control.user.message.common.MessageUtil;
import com.echothree.control.user.message.common.MessageService;
import com.echothree.control.user.message.common.form.MessageFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import java.util.Map;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MessageTypeHandler
        extends BaseHandler {

    MessageService messageService;
    String componentVendorName;
    String entityTypeName;
    String messageTypeName;
    
    private Map<String, Object> messageAttrs;
    boolean inMessage = false;
    char []clobMessage = null;
    
    /** Creates a new instance of MessageTypeHandler */
    public MessageTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName,
            String entityTypeName, String messageTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        messageService = lookupService(MessageService.class, MessageUtil::getHome);
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.messageTypeName = messageTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("messageTypeDescription")) {
            var commandForm = MessageFormFactory.getCreateMessageTypeDescriptionForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setMessageTypeName(messageTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, messageService.createMessageTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("message")) {
            messageAttrs = getAttrsMap(attrs);
            messageAttrs.keySet().removeAll(Set.of("ComponentVendorName", "EntityTypeName", "MessageTypeName", "ClobMessage", "Description"));
            
            inMessage = true;
        }
    }
    
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        if(inMessage) {
            var oldLength = clobMessage != null? clobMessage.length: 0;
            var newClob = new char[oldLength + length];
            
            if(clobMessage != null)
                System.arraycopy(clobMessage, 0, newClob, 0, clobMessage.length);
            System.arraycopy(ch, start, newClob, oldLength, length);
            clobMessage = newClob;
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("message")) {
            var commandForm = MessageFormFactory.getCreateMessageForm();
            
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setMessageTypeName(messageTypeName);
            commandForm.setClobMessage(clobMessage == null? null: new String(clobMessage));
            commandForm.set(messageAttrs);
            
            checkCommandResult(commandForm, messageService.createMessage(initialDataParser.getUserVisit(), commandForm));
            
            inMessage = false;
            messageAttrs = null;
            clobMessage = null;
        } else if(localName.equals("messageType")) {
            initialDataParser.popHandler();
        }
    }
    
}
