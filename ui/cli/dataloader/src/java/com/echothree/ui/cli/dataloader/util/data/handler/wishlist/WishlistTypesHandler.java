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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WishlistTypesHandler
        extends BaseHandler {

    WishlistService wishlistService;
    
    /** Creates a new instance of WishlistTypesHandler */
    public WishlistTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        wishlistService = lookupService(WishlistService.class, WishlistUtil::getHome);
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("wishlistType")) {
            var form = WishlistFormFactory.getCreateWishlistTypeForm();

            form.set(getAttrsMap(attrs));

            checkCommandResult(form, wishlistService.createWishlistType(initialDataParser.getUserVisit(), form));

            initialDataParser.pushHandler(new WishlistTypeHandler(initialDataParser, this, form.getWishlistTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("wishlistTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
