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

import com.echothree.control.user.inventory.common.InventoryService;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.form.InventoryFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class InventoryBucketTypeHandler
        extends BaseHandler {

    InventoryService inventoryService;
    String inventoryBucketTypeName;

    /** Creates a new instance of InventoryBucketTypeHandler */
    public InventoryBucketTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String inventoryBucketTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            inventoryService = InventoryUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.inventoryBucketTypeName = inventoryBucketTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("inventoryBucketTypeDescription")) {
            String attrLanguageIsoName = null;
            String attrDescription = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    attrLanguageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
            }
            
            try {
                var commandForm = InventoryFormFactory.getCreateInventoryBucketTypeDescriptionForm();
                
                commandForm.setInventoryBucketTypeName(inventoryBucketTypeName);
                commandForm.setLanguageIsoName(attrLanguageIsoName);
                commandForm.setDescription(attrDescription);
                
                checkCommandResult(inventoryService.createInventoryBucketTypeDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("inventoryBucketType")) {
            initialDataParser.popHandler();
        }
    }
    
}
