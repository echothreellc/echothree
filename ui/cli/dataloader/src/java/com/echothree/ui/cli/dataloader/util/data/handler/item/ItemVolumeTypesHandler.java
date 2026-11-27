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

public class ItemVolumeTypesHandler
        extends BaseHandler {

    ItemService itemService;

    /** Creates a new instance of ItemVolumeTypesHandler */
    public ItemVolumeTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
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
        if(localName.equals("itemVolumeType")) {
            var attrsMap = getAttrsMap(attrs);

            var outcome = editOrCreate(attrsMap, ItemSpecFactory.getItemVolumeTypeUniversalSpec(),
                    ItemFormFactory.getEditItemVolumeTypeForm(), itemService::editItemVolumeType,
                    ItemFormFactory.getCreateItemVolumeTypeForm(), itemService::createItemVolumeType,
                    ExecutionErrors.UnknownItemVolumeTypeName);

            if(outcome.successful()) {
                var itemVolumeTypeName = (String)attrsMap.get("ItemVolumeTypeName");
                if(outcome.lockedEdit() != null) {
                    itemVolumeTypeName = outcome.lockedEdit().getItemVolumeTypeName();
                } else if(outcome.createdRecord() != null) {
                    itemVolumeTypeName = outcome.createdRecord().getItemVolumeTypeName();
                }
                if(itemVolumeTypeName == null) {
                    throw new SAXException("Item volume type name is missing for child elements");
                }
                initialDataParser.pushHandler(new ItemVolumeTypeHandler(initialDataParser, this, itemVolumeTypeName));
            } else {
                skipCurrentElement();
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("itemVolumeTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
