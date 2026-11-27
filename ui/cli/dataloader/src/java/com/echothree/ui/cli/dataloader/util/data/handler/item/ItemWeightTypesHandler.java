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

import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.item.common.spec.ItemSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemWeightTypesHandler
        extends BaseHandler {

    ItemService itemService;
    
    /** Creates a new instance of ItemWeightTypesHandler */
    public ItemWeightTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
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
        if(localName.equals("itemWeightType")) {
            var attrsMap = getAttrsMap(attrs);

            var outcome = editOrCreate(attrsMap, ItemSpecFactory.getItemWeightTypeUniversalSpec(),
                    ItemFormFactory.getEditItemWeightTypeForm(), itemService::editItemWeightType,
                    ItemFormFactory.getCreateItemWeightTypeForm(), itemService::createItemWeightType,
                    ExecutionErrors.UnknownItemWeightTypeName);

            if(outcome.successful()) {
                var itemWeightTypeName = (String)attrsMap.get("ItemWeightTypeName");
                if(outcome.lockedEdit() != null) {
                    itemWeightTypeName = outcome.lockedEdit().getItemWeightTypeName();
                } else if(outcome.createdRecord() != null) {
                    itemWeightTypeName = outcome.createdRecord().getItemWeightTypeName();
                }
                if(itemWeightTypeName == null) {
                    throw new SAXException("Item weight type name is missing for child elements");
                }
                initialDataParser.pushHandler(new ItemWeightTypeHandler(initialDataParser, this, itemWeightTypeName));
            } else {
                skipCurrentElement();
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("itemWeightTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
