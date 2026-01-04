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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MessageTypeHandler
        extends BaseHandler {
    MessageService messageService;
    String componentVendorName;
    String entityTypeName;
    String messageTypeName;
    
    String messageName = null;
    String includeByDefault = null;
    String isDefault = null;
    String sortOrder = null;
    boolean inMessage = false;
    String mimeTypeName = null;
    String stringMessage = null;
    char []clobMessage = null;
    String description = null;
    
    /** Creates a new instance of MessageTypeHandler */
    public MessageTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName,
            String entityTypeName, String messageTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            messageService = MessageUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.messageTypeName = messageTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("messageTypeDescription")) {
            String languageIsoName = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = MessageFormFactory.getCreateMessageTypeDescriptionForm();
                
                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.setMessageTypeName(messageTypeName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                messageService.createMessageTypeDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("message")) {
            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("messageName"))
                    messageName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("includeByDefault"))
                    includeByDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("mimeTypeName"))
                    mimeTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("stringMessage"))
                    stringMessage = attrs.getValue(i);
            }
            
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
            commandForm.setMessageName(messageName);
            commandForm.setIncludeByDefault(includeByDefault);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setMimeTypeName(mimeTypeName);
            commandForm.setClobMessage(clobMessage == null? null: new String(clobMessage));
            commandForm.setStringMessage(stringMessage);
            commandForm.setDescription(description);
            
            messageService.createMessage(initialDataParser.getUserVisit(), commandForm);
            
            inMessage = false;
            messageName = null;
            includeByDefault = null;
            isDefault = null;
            sortOrder = null;
            mimeTypeName = null;
            clobMessage = null;
            stringMessage = null;
            description = null;
        } else if(localName.equals("messageType")) {
            initialDataParser.popHandler();
        }
    }
    
}
