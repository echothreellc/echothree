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

package com.echothree.ui.cli.dataloader.util.data.handler.carrier;

import com.echothree.control.user.carrier.common.CarrierUtil;
import com.echothree.control.user.carrier.common.CarrierService;
import com.echothree.control.user.carrier.common.form.CarrierFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CarrierServiceHandler
        extends BaseHandler {
    CarrierService carrierService;
    String carrierName;
    String carrierServiceName;
    
    /** Creates a new instance of CarrierServiceHandler */
    public CarrierServiceHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String carrierName,
            String carrierServiceName) {
        super(initialDataParser, parentHandler);
        
        try {
            carrierService = CarrierUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.carrierName = carrierName;
        this.carrierServiceName = carrierServiceName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("carrierServiceDescription")) {
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
                var commandForm = CarrierFormFactory.getCreateCarrierServiceDescriptionForm();
                
                commandForm.setCarrierName(carrierName);
                commandForm.setCarrierServiceName(carrierServiceName);
                commandForm.setLanguageIsoName(attrLanguageIsoName);
                commandForm.setDescription(attrDescription);
                
                checkCommandResult(carrierService.createCarrierServiceDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("carrierServiceOption")) {
            String carrierOptionName = null;
            String isRecommended = null;
            String isRequired = null;
            String recommendedItemSelectorName = null;
            String requiredItemSelectorName = null;
            String recommendedOrderSelectorName = null;
            String requiredOrderSelectorName = null;
            String recommendedShipmentSelectorName = null;
            String requiredShipmentSelectorName = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("carrierOptionName"))
                    carrierOptionName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isRecommended"))
                    isRecommended = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isRequired"))
                    isRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("recommendedItemSelectorName"))
                    recommendedItemSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("requiredItemSelectorName"))
                    requiredItemSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("recommendedOrderSelectorName"))
                    recommendedOrderSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("requiredOrderSelectorName"))
                    requiredOrderSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("recommendedShipmentSelectorName"))
                    recommendedShipmentSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("requiredShipmentSelectorName"))
                    requiredShipmentSelectorName = attrs.getValue(i);
            }
            
            try {
                var commandForm = CarrierFormFactory.getCreateCarrierServiceOptionForm();
                
                commandForm.setCarrierName(carrierName);
                commandForm.setCarrierServiceName(carrierServiceName);
                commandForm.setCarrierOptionName(carrierOptionName);
                commandForm.setIsRecommended(isRecommended);
                commandForm.setIsRequired(isRequired);
                commandForm.setRecommendedItemSelectorName(recommendedItemSelectorName);
                commandForm.setRequiredItemSelectorName(requiredItemSelectorName);
                commandForm.setRecommendedOrderSelectorName(recommendedOrderSelectorName);
                commandForm.setRequiredOrderSelectorName(requiredOrderSelectorName);
                commandForm.setRecommendedShipmentSelectorName(recommendedShipmentSelectorName);
                commandForm.setRequiredShipmentSelectorName(requiredShipmentSelectorName);
                
                checkCommandResult(carrierService.createCarrierServiceOption(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("carrierService")) {
            initialDataParser.popHandler();
        }
    }
    
}
