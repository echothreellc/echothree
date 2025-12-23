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

package com.echothree.ui.cli.dataloader.util.data.handler.warehouse;

import com.echothree.control.user.inventory.common.InventoryService;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.form.InventoryFormFactory;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.warehouse.common.WarehouseService;
import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.form.WarehouseFormFactory;
import com.echothree.control.user.warehouse.common.result.CreateLocationResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WarehouseHandler
        extends BaseHandler {

    InventoryService inventoryService = InventoryUtil.getHome();
    PartyService partyService = PartyUtil.getHome();
    WarehouseService warehouseService = WarehouseUtil.getHome();
    String partyName;
    String warehouseName;
    String entityRef;

    /** Creates a new instance of WarehouseHandler */
    public WarehouseHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName, String warehouseName, String entityRef)
            throws NamingException {
        super(initialDataParser, parentHandler);

        this.partyName = partyName;
        this.warehouseName = warehouseName;
        this.entityRef = entityRef;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyAlias")) {
            var commandForm = PartyFormFactory.getCreatePartyAliasForm();

            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));

            partyService.createPartyAlias(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("inventoryLocationGroup")) {
            var commandForm = InventoryFormFactory.getCreateInventoryLocationGroupForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.set(getAttrsMap(attrs));

            inventoryService.createInventoryLocationGroup(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new InventoryLocationGroupHandler(initialDataParser, this, warehouseName, commandForm.getInventoryLocationGroupName()));
        } else if(localName.equals("locationType")) {
            var commandForm = WarehouseFormFactory.getCreateLocationTypeForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.set(getAttrsMap(attrs));

            warehouseService.createLocationType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new LocationTypeHandler(initialDataParser, this, warehouseName, commandForm.getLocationTypeName()));
        } else if(localName.equals("location")) {
            var commandForm = WarehouseFormFactory.getCreateLocationForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.set(getAttrsMap(attrs));

            var commandResult = warehouseService.createLocation(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateLocationResult)executionResult.getResult();

                initialDataParser.pushHandler(new LocationHandler(initialDataParser, this, warehouseName, result.getLocationName(), result.getEntityRef()));
            }
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
        if(localName.equals("warehouse")) {
            initialDataParser.popHandler();
        }
    }

}
