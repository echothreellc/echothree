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
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WishlistsHandler
        extends BaseHandler {

    WishlistService wishlistService;
    String partyName;
    
    /** Creates a new instance of WishlistsHandler */
    public WishlistsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        wishlistService = lookupService(WishlistService.class, WishlistUtil::getHome);
        this.partyName = partyName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("wishlist")) {
            var wishlistTypeName = attrs.getValue("wishlistTypeName");
            var currencyIsoName = attrs.getValue("currencyIsoName");
            var sourceName = attrs.getValue("sourceName");
            
            initialDataParser.pushHandler(new WishlistHandler(initialDataParser, this, partyName, wishlistTypeName, currencyIsoName,
                    sourceName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("wishlists")) {
            initialDataParser.popHandler();
        }
    }
    
}
