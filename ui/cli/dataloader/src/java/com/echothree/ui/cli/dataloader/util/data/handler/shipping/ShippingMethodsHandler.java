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

package com.echothree.ui.cli.dataloader.util.data.handler.shipping;

import com.echothree.control.user.shipping.common.ShippingUtil;
import com.echothree.control.user.shipping.common.ShippingService;
import com.echothree.control.user.shipping.common.form.ShippingFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ShippingMethodsHandler
        extends BaseHandler {
    ShippingService shippingService;
    
    /** Creates a new instance of ShippingMethodsHandler */
    public ShippingMethodsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            shippingService = ShippingUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("shippingMethod")) {
            String shippingMethodName = null;
            String itemSelectorName = null;
            String sortOrder = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("shippingMethodName"))
                    shippingMethodName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("itemSelectorName"))
                    itemSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = ShippingFormFactory.getCreateShippingMethodForm();
                
                commandForm.setShippingMethodName(shippingMethodName);
                commandForm.setItemSelectorName(itemSelectorName);
                commandForm.setSortOrder(sortOrder);
                
                checkCommandResult(shippingService.createShippingMethod(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new ShippingMethodHandler(initialDataParser, this, shippingMethodName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("shippingMethods")) {
            initialDataParser.popHandler();
        }
    }
    
}
