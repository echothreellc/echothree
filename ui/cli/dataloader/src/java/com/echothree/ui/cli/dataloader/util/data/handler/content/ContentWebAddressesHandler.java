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

package com.echothree.ui.cli.dataloader.util.data.handler.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.ContentService;
import com.echothree.control.user.content.common.form.ContentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentWebAddressesHandler
        extends BaseHandler {
    ContentService contentService;
    
    /** Creates a new instance of ContentWebAddressesHandler */
    public ContentWebAddressesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentWebAddress")) {
            String contentWebAddressName = null;
            String contentCollectionName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentWebAddressName"))
                    contentWebAddressName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("contentCollectionName"))
                    contentCollectionName = attrs.getValue(i);
            }
            
            try {
                var createContentWebAddressForm = ContentFormFactory.getCreateContentWebAddressForm();
                
                createContentWebAddressForm.setContentWebAddressName(contentWebAddressName);
                createContentWebAddressForm.setContentCollectionName(contentCollectionName);
                
                contentService.createContentWebAddress(initialDataParser.getUserVisit(), createContentWebAddressForm);
                
                initialDataParser.pushHandler(new ContentWebAddressHandler(initialDataParser, this, contentWebAddressName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentWebAddresses")) {
            initialDataParser.popHandler();
        }
    }
    
}
