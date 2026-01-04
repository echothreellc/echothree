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

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.InventoryService;
import com.echothree.control.user.inventory.common.form.InventoryFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class InventoryLocationGroupHandler
        extends BaseHandler {
    InventoryService inventoryService;
    String warehouseName;
    String inventoryLocationGroupName;
    
    /** Creates a new instance of InventoryLocationGroupHandler */
    public InventoryLocationGroupHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String warehouseName,
            String inventoryLocationGroupName) {
        super(initialDataParser, parentHandler);
        
        try {
            inventoryService = InventoryUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.warehouseName = warehouseName;
        this.inventoryLocationGroupName = inventoryLocationGroupName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("inventoryLocationGroupDescription")) {
            String languageIsoName = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var form = InventoryFormFactory.getCreateInventoryLocationGroupDescriptionForm();
                
                form.setWarehouseName(warehouseName);
                form.setInventoryLocationGroupName(inventoryLocationGroupName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                inventoryService.createInventoryLocationGroupDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("inventoryLocationGroupVolume")) {
            String heightUnitOfMeasureTypeName = null;
            String height  = null;
            String widthUnitOfMeasureTypeName = null;
            String width = null;
            String depthUnitOfMeasureTypeName = null;
            String depth = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("heightUnitOfMeasureTypeName"))
                    heightUnitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("height"))
                    height = attrs.getValue(i);
                else if(attrs.getQName(i).equals("widthUnitOfMeasureTypeName"))
                    widthUnitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("width"))
                    width = attrs.getValue(i);
                else if(attrs.getQName(i).equals("depthUnitOfMeasureTypeName"))
                    depthUnitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("depth"))
                    depth = attrs.getValue(i);
            }
            
            try {
                var form = InventoryFormFactory.getCreateInventoryLocationGroupVolumeForm();
                
                form.setWarehouseName(warehouseName);
                form.setInventoryLocationGroupName(inventoryLocationGroupName);
                form.setHeightUnitOfMeasureTypeName(heightUnitOfMeasureTypeName);
                form.setHeight(height);
                form.setWidthUnitOfMeasureTypeName(widthUnitOfMeasureTypeName);
                form.setWidth(width);
                form.setDepthUnitOfMeasureTypeName(depthUnitOfMeasureTypeName);
                form.setDepth(depth);
                
                inventoryService.createInventoryLocationGroupVolume(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("inventoryLocationGroupCapacity")) {
            String unitOfMeasureKindName = null;
            String unitOfMeasureTypeName = null;
            String capacity  = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("unitOfMeasureKindName"))
                    unitOfMeasureKindName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unitOfMeasureTypeName"))
                    unitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("capacity"))
                    capacity = attrs.getValue(i);
            }
            
            try {
                var form = InventoryFormFactory.getCreateInventoryLocationGroupCapacityForm();
                
                form.setWarehouseName(warehouseName);
                form.setInventoryLocationGroupName(inventoryLocationGroupName);
                form.setUnitOfMeasureKindName(unitOfMeasureKindName);
                form.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                form.setCapacity(capacity);
                
                inventoryService.createInventoryLocationGroupCapacity(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("inventoryLocationGroup")) {
            initialDataParser.popHandler();
        }
    }
    
}
