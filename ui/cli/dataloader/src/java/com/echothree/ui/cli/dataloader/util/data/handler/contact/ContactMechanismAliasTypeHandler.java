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

package com.echothree.ui.cli.dataloader.util.data.handler.contact;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.ContactService;
import com.echothree.control.user.contact.common.form.ContactFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContactMechanismAliasTypeHandler
        extends BaseHandler {

    ContactService contactService;
    String contactMechanismAliasTypeName;
    
    /** Creates a new instance of ContactMechanismAliasTypeHandler */
    public ContactMechanismAliasTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contactMechanismAliasTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        contactService = lookupService(ContactService.class, ContactUtil::getHome);
        
        this.contactMechanismAliasTypeName = contactMechanismAliasTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contactMechanismAliasTypeDescription")) {
            var createContactMechanismAliasTypeDescriptionForm = ContactFormFactory.getCreateContactMechanismAliasTypeDescriptionForm();

            createContactMechanismAliasTypeDescriptionForm.setContactMechanismAliasTypeName(contactMechanismAliasTypeName);
            createContactMechanismAliasTypeDescriptionForm.set(getAttrsMap(attrs));

            checkCommandResult(createContactMechanismAliasTypeDescriptionForm,
                    contactService.createContactMechanismAliasTypeDescription(initialDataParser.getUserVisit(), createContactMechanismAliasTypeDescriptionForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contactMechanismAliasType")) {
            initialDataParser.popHandler();
        }
    }
    
}
