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

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.WarehouseService;
import com.echothree.control.user.warehouse.common.form.WarehouseFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LocationHandler
        extends BaseHandler {

    WarehouseService warehouseService;
    String warehouseName;
    String locationName;
    String entityRef;

    /** Creates a new instance of LocationHandler */
    public LocationHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String warehouseName, String locationName, String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            warehouseService = WarehouseUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.warehouseName = warehouseName;
        this.locationName = locationName;
        this.entityRef = entityRef;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("locationDescription")) {
            var commandForm = WarehouseFormFactory.getCreateLocationDescriptionForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.setLocationName(locationName);
            commandForm.set(getAttrsMap(attrs));

            warehouseService.createLocationDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("locationVolume")) {
            var commandForm = WarehouseFormFactory.getCreateLocationVolumeForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.setLocationName(locationName);
            commandForm.set(getAttrsMap(attrs));

            warehouseService.createLocationVolume(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("locationCapacity")) {
            var commandForm = WarehouseFormFactory.getCreateLocationCapacityForm();

            commandForm.setWarehouseName(warehouseName);
            commandForm.setLocationName(locationName);
            commandForm.set(getAttrsMap(attrs));

            warehouseService.createLocationCapacity(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("location")) {
            initialDataParser.popHandler();
        }
    }

}
