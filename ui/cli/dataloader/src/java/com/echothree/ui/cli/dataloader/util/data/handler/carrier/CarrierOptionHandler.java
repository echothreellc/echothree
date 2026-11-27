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

public class CarrierOptionHandler
        extends BaseHandler {

    CarrierService carrierService;
    String carrierName;
    String carrierOptionName;
    
    /** Creates a new instance of CarrierOptionHandler */
    public CarrierOptionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String carrierName,
            String carrierOptionName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        carrierService = lookupService(CarrierService.class, CarrierUtil::getHome);
        
        this.carrierName = carrierName;
        this.carrierOptionName = carrierOptionName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("carrierOptionDescription")) {
            var commandForm = CarrierFormFactory.getCreateCarrierOptionDescriptionForm();

            commandForm.setCarrierName(carrierName);
            commandForm.setCarrierOptionName(carrierOptionName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, carrierService.createCarrierOptionDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("carrierOption")) {
            initialDataParser.popHandler();
        }
    }
    
}
