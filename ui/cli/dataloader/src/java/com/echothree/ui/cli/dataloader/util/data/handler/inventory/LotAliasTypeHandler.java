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

package com.echothree.ui.cli.dataloader.util.data.handler.inventory;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.InventoryService;
import com.echothree.control.user.inventory.common.form.InventoryFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LotAliasTypeHandler
        extends BaseHandler {

    InventoryService inventoryService;
    String lotAliasTypeName;
    
    /** Creates a new instance of LotAliasTypeHandler */
    public LotAliasTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String lotAliasTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            inventoryService = InventoryUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }

        this.lotAliasTypeName = lotAliasTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("lotAliasTypeDescription")) {
            var commandForm = InventoryFormFactory.getCreateLotAliasTypeDescriptionForm();

            commandForm.setLotAliasTypeName(lotAliasTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(inventoryService.createLotAliasTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("lotAliasType")) {
            initialDataParser.popHandler();
        }
    }
    
}
