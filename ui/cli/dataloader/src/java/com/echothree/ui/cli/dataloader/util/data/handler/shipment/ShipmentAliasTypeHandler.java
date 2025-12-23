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

public class ShipmentAliasTypeHandler
        extends BaseHandler {
    ShipmentService shipmentService;
    String shipmentTypeName;
    String shipmentAliasTypeName;
    
    /** Creates a new instance of ShipmentAliasTypeHandler */
    public ShipmentAliasTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String shipmentTypeName, String shipmentAliasTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            shipmentService = ShipmentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.shipmentTypeName = shipmentTypeName;
        this.shipmentAliasTypeName = shipmentAliasTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("shipmentAliasTypeDescription")) {
            var commandForm = ShipmentFormFactory.getCreateShipmentAliasTypeDescriptionForm();
            String attrLanguageIsoName = null;
            String attrDescription = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    attrLanguageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
            }
            
            commandForm.setShipmentTypeName(shipmentTypeName);
            commandForm.setShipmentAliasTypeName(shipmentAliasTypeName);
            commandForm.setLanguageIsoName(attrLanguageIsoName);
            commandForm.setDescription(attrDescription);
            
            checkCommandResult(shipmentService.createShipmentAliasTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("shipmentAliasType")) {
            initialDataParser.popHandler();
        }
    }
    
}
