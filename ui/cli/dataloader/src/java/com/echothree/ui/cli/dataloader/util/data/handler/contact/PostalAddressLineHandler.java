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

public class PostalAddressLineHandler
        extends BaseHandler {

    ContactService contactService;
    String postalAddressFormatName;
    String postalAddressLineSortOrder;
    
    /** Creates a new instance of PostalAddressLineHandler */
    public PostalAddressLineHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String postalAddressFormatName,
            String postalAddressLineSortOrder)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        contactService = lookupService(ContactService.class, ContactUtil::getHome);
        
        this.postalAddressFormatName = postalAddressFormatName;
        this.postalAddressLineSortOrder = postalAddressLineSortOrder;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("postalAddressLineElement")) {
            var form = ContactFormFactory.getCreatePostalAddressLineElementForm();

            form.setPostalAddressFormatName(postalAddressFormatName);
            form.setPostalAddressLineSortOrder(postalAddressLineSortOrder);
            form.set(getAttrsMap(attrs));

            contactService.createPostalAddressLineElement(initialDataParser.getUserVisit(), form);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("postalAddressLine")) {
            initialDataParser.popHandler();
        }
    }
    
}
