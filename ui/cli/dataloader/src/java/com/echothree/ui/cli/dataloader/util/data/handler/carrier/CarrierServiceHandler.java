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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CarrierServiceHandler
        extends BaseHandler {

    CarrierService carrierService;
    String carrierName;
    String carrierServiceName;
    
    /** Creates a new instance of CarrierServiceHandler */
    public CarrierServiceHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String carrierName,
            String carrierServiceName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        carrierService = lookupService(CarrierService.class, CarrierUtil::getHome);
        
        this.carrierName = carrierName;
        this.carrierServiceName = carrierServiceName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("carrierServiceDescription")) {
            var commandForm = CarrierFormFactory.getCreateCarrierServiceDescriptionForm();

            commandForm.setCarrierName(carrierName);
            commandForm.setCarrierServiceName(carrierServiceName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, carrierService.createCarrierServiceDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("carrierServiceOption")) {
            var commandForm = CarrierFormFactory.getCreateCarrierServiceOptionForm();

            commandForm.setCarrierName(carrierName);
            commandForm.setCarrierServiceName(carrierServiceName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, carrierService.createCarrierServiceOption(initialDataParser.getUserVisit(), commandForm));
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
