// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.inventory.common.InventoryService;
import com.echothree.control.user.inventory.common.form.CreateInventoryConditionDescriptionForm;
import com.echothree.control.user.inventory.common.form.CreateInventoryConditionUseForm;
import com.echothree.control.user.inventory.common.form.InventoryFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class InventoryConditionHandler
        extends BaseHandler {
    InventoryService inventoryService;
    String inventoryConditionName;
    
    /** Creates a new instance of InventoryConditionHandler */
    public InventoryConditionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String inventoryConditionName) {
        super(initialDataParser, parentHandler);
        
        try {
            inventoryService = InventoryUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.inventoryConditionName = inventoryConditionName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("inventoryConditionDescription")) {
            String languageIsoName = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                CreateInventoryConditionDescriptionForm form = InventoryFormFactory.getCreateInventoryConditionDescriptionForm();
                
                form.setInventoryConditionName(inventoryConditionName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                inventoryService.createInventoryConditionDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("inventoryConditionUse")) {
            String inventoryConditionUseTypeName = null;
            String isDefault = null;
            
            int attrCount = attrs.getLength();
            for(int i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("inventoryConditionUseTypeName"))
                    inventoryConditionUseTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
            }
            
            try {
                CreateInventoryConditionUseForm form = InventoryFormFactory.getCreateInventoryConditionUseForm();
                
                form.setInventoryConditionName(inventoryConditionName);
                form.setInventoryConditionUseTypeName(inventoryConditionUseTypeName);
                form.setIsDefault(isDefault);
                
                checkCommandResult(inventoryService.createInventoryConditionUse(initialDataParser.getUserVisit(), form));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("inventoryCondition")) {
            initialDataParser.popHandler();
        }
    }
    
}
