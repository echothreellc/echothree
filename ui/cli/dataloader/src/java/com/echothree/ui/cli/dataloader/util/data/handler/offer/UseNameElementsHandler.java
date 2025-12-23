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

package com.echothree.ui.cli.dataloader.util.data.handler.offer;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.OfferService;
import com.echothree.control.user.offer.common.form.OfferFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class UseNameElementsHandler
        extends BaseHandler {
    OfferService offerService;
    
    /** Creates a new instance of UseNameElementsHandler */
    public UseNameElementsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            offerService = OfferUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("useNameElement")) {
            String useNameElementName = null;
            String offset = null;
            String length = null;
            String validationPattern = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("useNameElementName"))
                    useNameElementName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("offset"))
                    offset = attrs.getValue(i);
                else if(attrs.getQName(i).equals("length"))
                    length = attrs.getValue(i);
                else if(attrs.getQName(i).equals("validationPattern"))
                    validationPattern = attrs.getValue(i);
            }
            
            try {
                var form = OfferFormFactory.getCreateUseNameElementForm();
                
                form.setUseNameElementName(useNameElementName);
                form.setOffset(offset);
                form.setLength(length);
                form.setValidationPattern(validationPattern);
                
                offerService.createUseNameElement(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new UseNameElementHandler(initialDataParser, this, useNameElementName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("useNameElements")) {
            initialDataParser.popHandler();
        }
    }
    
}
