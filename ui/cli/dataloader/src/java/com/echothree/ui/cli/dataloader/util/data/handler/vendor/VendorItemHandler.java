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

package com.echothree.ui.cli.dataloader.util.data.handler.vendor;

import com.echothree.control.user.vendor.common.VendorService;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.VendorFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class VendorItemHandler
        extends BaseHandler {

    VendorService vendorService = VendorUtil.getHome();

    String vendorName;
    String vendorItemName;
    
    /** Creates a new instance of VendorItemHandler */
    public VendorItemHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String vendorName,
            String vendorItemName)
            throws NamingException {
        super(initialDataParser, parentHandler);

        this.vendorName = vendorName;
        this.vendorItemName = vendorItemName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("vendorItemCost")) {
            var form = VendorFormFactory.getCreateVendorItemCostForm();
            
            form.setVendorName(vendorName);
            form.setVendorItemName(vendorItemName);
            form.set(getAttrsMap(attrs));
            
            vendorService.createVendorItemCost(initialDataParser.getUserVisit(), form);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("vendorItem")) {
            initialDataParser.popHandler();
        }
    }
    
}
