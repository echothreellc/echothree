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

package com.echothree.ui.cli.dataloader.util.data.handler.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemsHandler
        extends BaseHandler {
    ItemService itemService = null;
    
    /** Creates a new instance of ItemsHandler */
    public ItemsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            itemService = ItemUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("item")) {
            String entityRef = null;
            String itemName = null;
            String companyName = null;
            String commandAction = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                switch (attrs.getQName(i)) {
                    case "itemName":
                        itemName = attrs.getValue(i);
                        break;
                    case "companyName":
                        companyName = attrs.getValue(i);
                        break;
                    case "commandAction":
                        commandAction = attrs.getValue(i);
                        break;
                }
            }
            
            if(commandAction == null || commandAction.equals("create")) {
                var commandForm = ItemFormFactory.getCreateItemForm();
                
                commandForm.set(getAttrsMap(attrs));

                var commandResult = itemService.createItem(initialDataParser.getUserVisit(), commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateItemResult)executionResult.getResult();
                
                itemName = result.getItemName();
                entityRef = result.getEntityRef();
            } else if(commandAction.equals("none")) {
                var commandForm = ItemFormFactory.getGetItemForm();
                
                commandForm.setItemName(itemName);

                var commandResult = itemService.getItem(initialDataParser.getUserVisit(), commandForm);
                
                if(!commandResult.hasErrors()) {
                    var executionResult = commandResult.getExecutionResult();
                    var result = (GetItemResult)executionResult.getResult();
                    var item = result.getItem();
                    
                    companyName = item.getCompany().getCompanyName();
                    entityRef = item.getEntityInstance().getEntityRef();
                }
            }
            
            if(entityRef != null) {
                initialDataParser.pushHandler(new ItemHandler(initialDataParser, this, itemName, companyName, entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("items")) {
            initialDataParser.popHandler();
        }
    }
    
}
