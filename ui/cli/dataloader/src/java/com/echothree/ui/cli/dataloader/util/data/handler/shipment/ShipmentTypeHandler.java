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

package com.echothree.ui.cli.dataloader.util.data.handler.shipment;

import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.common.ShipmentService;
import com.echothree.control.user.shipment.common.form.ShipmentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ShipmentTypeHandler
        extends BaseHandler {
    ShipmentService shipmentService;
    String shipmentTypeName;
    
    /** Creates a new instance of ShipmentTypeHandler */
    public ShipmentTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String shipmentTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            shipmentService = ShipmentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.shipmentTypeName = shipmentTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("shipmentTypeDescription")) {
            var commandForm = ShipmentFormFactory.getCreateShipmentTypeDescriptionForm();
            
            commandForm.setShipmentTypeName(shipmentTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(shipmentService.createShipmentTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("shipmentTimeType")) {
            var commandForm = ShipmentFormFactory.getCreateShipmentTimeTypeForm();

            commandForm.setShipmentTypeName(shipmentTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(shipmentService.createShipmentTimeType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new ShipmentTimeTypeHandler(initialDataParser, this, shipmentTypeName, commandForm.getShipmentTimeTypeName()));
        } else if(localName.equals("shipmentAliasType")) {
            var commandForm = ShipmentFormFactory.getCreateShipmentAliasTypeForm();
            
            commandForm.setShipmentTypeName(shipmentTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(shipmentService.createShipmentAliasType(initialDataParser.getUserVisit(), commandForm));
            
            initialDataParser.pushHandler(new ShipmentAliasTypeHandler(initialDataParser, this, shipmentTypeName,
                    commandForm.getShipmentAliasTypeName()));
        } else if(localName.equals("shipmentTypeShippingMethod")) {
            var commandForm = ShipmentFormFactory.getCreateShipmentTypeShippingMethodForm();
            
            commandForm.setShipmentTypeName(shipmentTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(shipmentService.createShipmentTypeShippingMethod(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("shipmentType")) {
            initialDataParser.popHandler();
        }
    }
    
}
