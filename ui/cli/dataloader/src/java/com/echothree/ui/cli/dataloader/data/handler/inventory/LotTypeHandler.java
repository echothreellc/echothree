// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.inventory;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.remote.InventoryService;
import com.echothree.control.user.inventory.remote.form.CreateLotAliasTypeForm;
import com.echothree.control.user.inventory.remote.form.CreateLotTimeTypeForm;
import com.echothree.control.user.inventory.remote.form.CreateLotTypeDescriptionForm;
import com.echothree.control.user.inventory.remote.form.InventoryFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LotTypeHandler
        extends BaseHandler {

    InventoryService inventoryService;
    String lotTypeName;
    
    /** Creates a new instance of LotTypeHandler */
    public LotTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String lotTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            inventoryService = InventoryUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.lotTypeName = lotTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("lotTypeDescription")) {
            CreateLotTypeDescriptionForm commandForm = InventoryFormFactory.getCreateLotTypeDescriptionForm();
            
            commandForm.setLotTypeName(lotTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(inventoryService.createLotTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("lotTimeType")) {
            CreateLotTimeTypeForm commandForm = InventoryFormFactory.getCreateLotTimeTypeForm();

            commandForm.setLotTypeName(lotTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(inventoryService.createLotTimeType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new LotTimeTypeHandler(initialDataParser, this, lotTypeName, commandForm.getLotTimeTypeName()));
        } else if(localName.equals("lotAliasType")) {
            CreateLotAliasTypeForm commandForm = InventoryFormFactory.getCreateLotAliasTypeForm();
            
            commandForm.setLotTypeName(lotTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(inventoryService.createLotAliasType(initialDataParser.getUserVisit(), commandForm));
            
            initialDataParser.pushHandler(new LotAliasTypeHandler(initialDataParser, this, lotTypeName, commandForm.getLotAliasTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("lotType")) {
            initialDataParser.popHandler();
        }
    }
    
}
