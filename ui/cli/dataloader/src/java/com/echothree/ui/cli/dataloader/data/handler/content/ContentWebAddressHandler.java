// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.ContentService;
import com.echothree.control.user.content.common.form.ContentFormFactory;
import com.echothree.control.user.content.common.form.CreateContentWebAddressDescriptionForm;
import com.echothree.control.user.content.common.form.CreateContentWebAddressServerForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentWebAddressHandler
        extends BaseHandler {
    ContentService contentService;
    String contentWebAddressName;
    
    /** Creates a new instance of ContentWebAddressHandler */
    public ContentWebAddressHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentWebAddressName) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contentWebAddressName = contentWebAddressName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentWebAddressDescription")) {
            String languageIsoName = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                CreateContentWebAddressDescriptionForm createContentWebAddressDescriptionForm = ContentFormFactory.getCreateContentWebAddressDescriptionForm();
                
                createContentWebAddressDescriptionForm.setContentWebAddressName(contentWebAddressName);
                createContentWebAddressDescriptionForm.setLanguageIsoName(languageIsoName);
                createContentWebAddressDescriptionForm.setDescription(description);
                
                contentService.createContentWebAddressDescription(initialDataParser.getUserVisit(), createContentWebAddressDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("contentWebAddressServer")) {
            String serverName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("serverName"))
                    serverName = attrs.getValue(i);
            }
            
            try {
                CreateContentWebAddressServerForm createContentWebAddressServerForm = ContentFormFactory.getCreateContentWebAddressServerForm();
                
                createContentWebAddressServerForm.setContentWebAddressName(contentWebAddressName);
                createContentWebAddressServerForm.setServerName(serverName);
                
                contentService.createContentWebAddressServer(initialDataParser.getUserVisit(), createContentWebAddressServerForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentWebAddress")) {
            initialDataParser.popHandler();
        }
    }
    
}
