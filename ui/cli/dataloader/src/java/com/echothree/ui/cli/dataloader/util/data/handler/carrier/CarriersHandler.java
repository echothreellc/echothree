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
import com.echothree.control.user.carrier.common.result.CreateCarrierResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CarriersHandler
        extends BaseHandler {

    CarrierService carrierService;

    /** Creates a new instance of CarriersHandler */
    public CarriersHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);

        try {
            carrierService = CarrierUtil.getHome();
        } catch(NamingException ne) {
        // TODO: Handle Exception
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("carrier")) {
            var commandForm = CarrierFormFactory.getCreateCarrierForm();

            commandForm.set(getAttrsMap(attrs));

            var commandResult = carrierService.createCarrier(initialDataParser.getUserVisit(), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateCarrierResult)executionResult.getResult();
                
                initialDataParser.pushHandler(new CarrierHandler(initialDataParser, this, result.getPartyName(), result.getCarrierName(), result.getEntityRef()));
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("carriers")) {
            initialDataParser.popHandler();
        }
    }

}
