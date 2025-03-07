// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.warehouse.common.WarehouseService;
import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.form.WarehouseFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WarehouseTypeHandler
        extends BaseHandler {

    WarehouseService warehouseService;
    String warehouseTypeName;

    /** Creates a new instance of WarehouseTypeHandler */
    public WarehouseTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String warehouseTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            warehouseService = WarehouseUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.warehouseTypeName = warehouseTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("warehouseTypeDescription")) {
            var commandForm = WarehouseFormFactory.getCreateWarehouseTypeDescriptionForm();

            commandForm.setWarehouseTypeName(warehouseTypeName);
            commandForm.set(getAttrsMap(attrs));

            warehouseService.createWarehouseTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("warehouseType")) {
            initialDataParser.popHandler();
        }
    }

}
