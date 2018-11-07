// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.contact;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.ContactService;
import com.echothree.control.user.contact.common.form.ContactFormFactory;
import com.echothree.control.user.contact.common.form.CreateContactMechanismTypeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContactMechanismTypesHandler
        extends BaseHandler {
    ContactService contactService;
    
    /** Creates a new instance of ContactMechanismTypesHandler */
    public ContactMechanismTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            contactService = ContactUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("contactMechanismType")) {
            CreateContactMechanismTypeForm commandForm = ContactFormFactory.getCreateContactMechanismTypeForm();
            String contactMechanismTypeName = null;
            String parentContactMechanismTypeName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contactMechanismTypeName"))
                    contactMechanismTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("parentContactMechanismTypeName"))
                    parentContactMechanismTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            commandForm.setContactMechanismTypeName(contactMechanismTypeName);
            commandForm.setParentContactMechanismTypeName(parentContactMechanismTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            
            contactService.createContactMechanismType(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new ContactMechanismTypeHandler(initialDataParser, this, contactMechanismTypeName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("contactMechanismTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
