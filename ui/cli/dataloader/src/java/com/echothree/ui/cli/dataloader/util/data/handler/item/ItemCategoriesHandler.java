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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemCategoriesHandler
        extends BaseHandler {
    ItemService itemService;
    
    /** Creates a new instance of ItemCategoriesHandler */
    public ItemCategoriesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            itemService = ItemUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("itemCategory")) {
            String itemCategoryName = null;
            String parentItemCategoryName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("itemCategoryName"))
                    itemCategoryName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("parentItemCategoryName"))
                    parentItemCategoryName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var createItemCategoryForm = ItemFormFactory.getCreateItemCategoryForm();
                
                createItemCategoryForm.setItemCategoryName(itemCategoryName);
                createItemCategoryForm.setParentItemCategoryName(parentItemCategoryName);
                createItemCategoryForm.setIsDefault(isDefault);
                createItemCategoryForm.setSortOrder(sortOrder);
                
                itemService.createItemCategory(initialDataParser.getUserVisit(), createItemCategoryForm);
                
                initialDataParser.pushHandler(new ItemCategoryHandler(initialDataParser, this, itemCategoryName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("itemCategories")) {
            initialDataParser.popHandler();
        }
    }
    
}
