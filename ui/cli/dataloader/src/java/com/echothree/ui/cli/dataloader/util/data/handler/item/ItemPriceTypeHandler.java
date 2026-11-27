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
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemPriceTypeHandler
        extends BaseHandler {

    ItemService itemService;
    String itemPriceTypeName;
    
    /** Creates a new instance of ItemPriceTypeHandler */
    public ItemPriceTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String itemPriceTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        itemService = lookupService(ItemService.class, ItemUtil::getHome);
        
        this.itemPriceTypeName = itemPriceTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("itemPriceTypeDescription")) {
                var createItemPriceTypeDescriptionForm = ItemFormFactory.getCreateItemPriceTypeDescriptionForm();

                createItemPriceTypeDescriptionForm.setItemPriceTypeName(itemPriceTypeName);
                createItemPriceTypeDescriptionForm.set(getAttrsMap(attrs));

                checkCommandResult(createItemPriceTypeDescriptionForm,
                        itemService.createItemPriceTypeDescription(initialDataParser.getUserVisit(), createItemPriceTypeDescriptionForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("itemPriceType")) {
            initialDataParser.popHandler();
        }
    }
    
}
