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
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CarrierHandler
        extends BaseHandler {

    CarrierService carrierService;
    String partyName;
    String carrierName;
    String entityRef;

    /** Creates a new instance of CarrierHandler */
    public CarrierHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName, String carrierName, String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            carrierService = CarrierUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.partyName = partyName;
        this.carrierName = carrierName;
        this.entityRef = entityRef;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("carrierService")) {
            var commandForm = CarrierFormFactory.getCreateCarrierServiceForm();

            commandForm.setCarrierName(carrierName);
            commandForm.set(getAttrsMap(attrs));

            carrierService.createCarrierService(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new CarrierServiceHandler(initialDataParser, this, carrierName, commandForm.getCarrierServiceName()));
        } else if(localName.equals("carrierOption")) {
            var commandForm = CarrierFormFactory.getCreateCarrierOptionForm();

            commandForm.setCarrierName(carrierName);
            commandForm.set(getAttrsMap(attrs));

            carrierService.createCarrierOption(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new CarrierOptionHandler(initialDataParser, this, carrierName, commandForm.getCarrierOptionName()));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("contactMechanisms")) {
            initialDataParser.pushHandler(new ContactMechanismsHandler(initialDataParser, this, partyName));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("carrier")) {
            initialDataParser.popHandler();
        }
    }

}
