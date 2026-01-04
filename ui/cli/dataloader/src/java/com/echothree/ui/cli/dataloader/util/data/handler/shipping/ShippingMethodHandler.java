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

public class ShippingMethodHandler
        extends BaseHandler {
    ShippingService shippingService;
    String shippingMethodName;
    
    /** Creates a new instance of ShippingMethodHandler */
    public ShippingMethodHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String shippingMethodName) {
        super(initialDataParser, parentHandler);
        
        try {
            shippingService = ShippingUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.shippingMethodName = shippingMethodName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("shippingMethodDescription")) {
            String attrLanguageIsoName = null;
            String attrDescription = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    attrLanguageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
            }
            
            try {
                var commandForm = ShippingFormFactory.getCreateShippingMethodDescriptionForm();
                
                commandForm.setShippingMethodName(shippingMethodName);
                commandForm.setLanguageIsoName(attrLanguageIsoName);
                commandForm.setDescription(attrDescription);
                
                checkCommandResult(shippingService.createShippingMethodDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("shippingMethodCarrierService")) {
            String carrierName = null;
            String carrierServiceName = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("carrierName"))
                    carrierName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("carrierServiceName"))
                    carrierServiceName = attrs.getValue(i);
            }
            
            try {
                var commandForm = ShippingFormFactory.getCreateShippingMethodCarrierServiceForm();
                
                commandForm.setShippingMethodName(shippingMethodName);
                commandForm.setCarrierName(carrierName);
                commandForm.setCarrierServiceName(carrierServiceName);
                
                checkCommandResult(shippingService.createShippingMethodCarrierService(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("shippingMethod")) {
            initialDataParser.popHandler();
        }
    }
    
}
