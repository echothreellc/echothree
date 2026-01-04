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

package com.echothree.ui.cli.dataloader.util.data.handler.contactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.ContactListService;
import com.echothree.control.user.contactlist.common.form.ContactListFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContactListFrequencyHandler
        extends BaseHandler {
    
    ContactListService contactListService;
    String contactListFrequencyName;
    
    /** Creates a new instance of ContactListFrequencyHandler */
    public ContactListFrequencyHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contactListFrequencyName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            contactListService = ContactListUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.contactListFrequencyName = contactListFrequencyName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("contactListFrequencyDescription")) {
            var commandForm = ContactListFormFactory.getCreateContactListFrequencyDescriptionForm();
            
            commandForm.setContactListFrequencyName(contactListFrequencyName);
            commandForm.set(getAttrsMap(attrs));
            
            contactListService.createContactListFrequencyDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("contactListFrequency")) {
            initialDataParser.popHandler();
        }
    }
    
}
