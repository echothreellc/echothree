// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

public class VendorTypesHandler
        extends BaseHandler {
    VendorService vendorService = VendorUtil.getHome();
    
    /** Creates a new instance of VendorTypesHandler */
    public VendorTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws NamingException {
        super(initialDataParser, parentHandler);
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("vendorType")) {
            var commandForm = VendorFormFactory.getCreateVendorTypeForm();
            
            commandForm.set(getAttrsMap(attrs));
            
            vendorService.createVendorType(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new VendorTypeHandler(initialDataParser, this, commandForm.getVendorTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("vendorTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
