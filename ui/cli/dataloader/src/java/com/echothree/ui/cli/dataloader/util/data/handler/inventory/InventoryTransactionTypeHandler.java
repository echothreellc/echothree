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

public class InventoryTransactionTypeHandler
        extends BaseHandler {

    InventoryService inventoryService;
    String inventoryTransactionTypeName;
    
    /** Creates a new instance of InventoryTransactionTypeHandler */
    public InventoryTransactionTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String inventoryTransactionTypeName) {
        super(initialDataParser, parentHandler);

        try {
            inventoryService = InventoryUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.inventoryTransactionTypeName = inventoryTransactionTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("inventoryTransactionTypeDescription")) {
            var commandForm = InventoryFormFactory.getCreateInventoryTransactionTypeDescriptionForm();

            commandForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(inventoryService.createInventoryTransactionTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("inventoryTransactionTimeType")) {
            var commandForm = InventoryFormFactory.getCreateInventoryTransactionTimeTypeForm();

            commandForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(inventoryService.createInventoryTransactionTimeType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new InventoryTransactionTimeTypeHandler(initialDataParser, this,
                    inventoryTransactionTypeName, commandForm.getInventoryTransactionTimeTypeName()));
        } else if(localName.equals("inventoryTransactionRoleType")) {
            var commandForm = InventoryFormFactory.getCreateInventoryTransactionRoleTypeForm();

            commandForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(inventoryService.createInventoryTransactionRoleType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new InventoryTransactionRoleTypeHandler(initialDataParser, this,
                    inventoryTransactionTypeName, commandForm.getInventoryTransactionRoleTypeName()));
        } else if(localName.equals("inventoryDisposition")) {
            var commandForm = InventoryFormFactory.getCreateInventoryDispositionForm();

            commandForm.setInventoryTransactionTypeName(inventoryTransactionTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(inventoryService.createInventoryDisposition(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new InventoryDispositionHandler(initialDataParser, this,
                    inventoryTransactionTypeName, commandForm.getInventoryDispositionName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("inventoryTransactionType")) {
            initialDataParser.popHandler();
        }
    }
    
}
