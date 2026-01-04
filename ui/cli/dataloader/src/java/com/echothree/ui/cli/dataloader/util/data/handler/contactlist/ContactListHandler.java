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

public class ContactListHandler
        extends BaseHandler {
    ContactListService contactListService;
    String contactListName;
    
    /** Creates a new instance of ContactListHandler */
    public ContactListHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contactListName) {
        super(initialDataParser, parentHandler);
        
        try {
            contactListService = ContactListUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contactListName = contactListName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("contactListDescription")) {
            var commandForm = ContactListFormFactory.getCreateContactListDescriptionForm();
            
            commandForm.setContactListName(contactListName);
            commandForm.set(getAttrsMap(attrs));
            
            contactListService.createContactListDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("contactListContactMechanismPurpose")) {
            var commandForm = ContactListFormFactory.getCreateContactListContactMechanismPurposeForm();
            
            commandForm.setContactListName(contactListName);
            commandForm.set(getAttrsMap(attrs));
            
            contactListService.createContactListContactMechanismPurpose(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("contactList")) {
            initialDataParser.popHandler();
        }
    }
    
}
