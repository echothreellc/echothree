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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class InventoryAdjustmentTypeHandler
        extends BaseHandler {

    InventoryService inventoryService;
    String inventoryAdjustmentTypeName;
    
    /** Creates a new instance of InventoryAdjustmentTypeHandler */
    public InventoryAdjustmentTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String inventoryAdjustmentTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        inventoryService = lookupService(InventoryService.class, InventoryUtil::getHome);
        
        this.inventoryAdjustmentTypeName = inventoryAdjustmentTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("inventoryAdjustmentTypeDescription")) {
            var commandForm = InventoryFormFactory.getCreateInventoryAdjustmentTypeDescriptionForm();

            commandForm.setInventoryAdjustmentTypeName(inventoryAdjustmentTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm,
                    inventoryService.createInventoryAdjustmentTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("inventoryAdjustmentType")) {
            initialDataParser.popHandler();
        }
    }
    
}
