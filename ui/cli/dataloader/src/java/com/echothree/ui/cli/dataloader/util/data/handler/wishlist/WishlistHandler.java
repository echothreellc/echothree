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

public class WishlistHandler
        extends BaseHandler {
    WishlistService wishlistService;
    String partyName;
    String wishlistTypeName;
    String currencyIsoName;
    String sourceName;
    
    /** Creates a new instance of WishlistHandler */
    public WishlistHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName, String wishlistTypeName,
            String currencyIsoName, String sourceName) {
        super(initialDataParser, parentHandler);
        
        try {
            wishlistService = WishlistUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        this.partyName = partyName;
        this.wishlistTypeName = wishlistTypeName;
        this.currencyIsoName = currencyIsoName;
        this.sourceName = sourceName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("wishlistLine")) {
            String itemName = null;
            String inventoryConditionName = null;
            String wishlistPriorityName = null;
            String comment = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("itemName"))
                    itemName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("inventoryConditionName"))
                    inventoryConditionName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("wishlistPriorityName"))
                    wishlistPriorityName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("comment"))
                    comment = attrs.getValue(i);
            }
            
            try {
                var form = WishlistFormFactory.getCreateWishlistLineForm();
                
                form.setPartyName(partyName);
                form.setWishlistTypeName(wishlistTypeName);
                form.setCurrencyIsoName(currencyIsoName);
                form.setSourceName(sourceName);
                form.setItemName(itemName);
                form.setInventoryConditionName(inventoryConditionName);
                form.setWishlistPriorityName(wishlistPriorityName);
                form.setComment(comment);
                
                wishlistService.createWishlistLine(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("wishlist")) {
            initialDataParser.popHandler();
        }
    }
    
}
