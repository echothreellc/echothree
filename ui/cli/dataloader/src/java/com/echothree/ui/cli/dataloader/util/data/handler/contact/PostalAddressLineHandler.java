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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PostalAddressLineHandler
        extends BaseHandler {
    ContactService contactService;
    String postalAddressFormatName;
    String postalAddressLineSortOrder;
    
    /** Creates a new instance of PostalAddressLineHandler */
    public PostalAddressLineHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String postalAddressFormatName,
            String postalAddressLineSortOrder) {
        super(initialDataParser, parentHandler);
        
        try {
            contactService = ContactUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.postalAddressFormatName = postalAddressFormatName;
        this.postalAddressLineSortOrder = postalAddressLineSortOrder;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("postalAddressLineElement")) {
            String postalAddressLineElementSortOrder = null;
            String postalAddressElementTypeName = null;
            String prefix = null;
            String alwaysIncludePrefix = null;
            String suffix = null;
            String alwaysIncludeSuffix = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("postalAddressLineElementSortOrder"))
                    postalAddressLineElementSortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalAddressElementTypeName"))
                    postalAddressElementTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("prefix"))
                    prefix = attrs.getValue(i);
                else if(attrs.getQName(i).equals("alwaysIncludePrefix"))
                    alwaysIncludePrefix = attrs.getValue(i);
                else if(attrs.getQName(i).equals("suffix"))
                    suffix = attrs.getValue(i);
                else if(attrs.getQName(i).equals("alwaysIncludeSuffix"))
                    alwaysIncludeSuffix = attrs.getValue(i);
            }
            
            try {
                var form = ContactFormFactory.getCreatePostalAddressLineElementForm();
                
                form.setPostalAddressFormatName(postalAddressFormatName);
                form.setPostalAddressLineSortOrder(postalAddressLineSortOrder);
                form.setPostalAddressLineElementSortOrder(postalAddressLineElementSortOrder);
                form.setPostalAddressElementTypeName(postalAddressElementTypeName);
                form.setPrefix(prefix);
                form.setAlwaysIncludePrefix(alwaysIncludePrefix);
                form.setSuffix(suffix);
                form.setAlwaysIncludeSuffix(alwaysIncludeSuffix);
                
                contactService.createPostalAddressLineElement(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
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
