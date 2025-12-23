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

package com.echothree.ui.cli.dataloader.util.data.handler.wishlist;

import com.echothree.control.user.wishlist.common.WishlistUtil;
import com.echothree.control.user.wishlist.common.WishlistService;
import com.echothree.control.user.wishlist.common.form.WishlistFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WishlistTypeHandler
        extends BaseHandler {
    WishlistService wishlistService;
    String wishlistTypeName;
    
    /** Creates a new instance of WishlistTypeHandler */
    public WishlistTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String wishlistTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            wishlistService = WishlistUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.wishlistTypeName = wishlistTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("wishlistTypeDescription")) {
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
                var form = WishlistFormFactory.getCreateWishlistTypeDescriptionForm();
                
                form.setWishlistTypeName(wishlistTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                wishlistService.createWishlistTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("wishlistPriority")) {
            String wishlistPriorityName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("wishlistPriorityName"))
                    wishlistPriorityName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var form = WishlistFormFactory.getCreateWishlistPriorityForm();
                
                form.setWishlistTypeName(wishlistTypeName);
                form.setWishlistPriorityName(wishlistPriorityName);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                wishlistService.createWishlistPriority(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new WishlistPriorityHandler(initialDataParser, this, wishlistTypeName, wishlistPriorityName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("wishlistType")) {
            initialDataParser.popHandler();
        }
    }
    
}
